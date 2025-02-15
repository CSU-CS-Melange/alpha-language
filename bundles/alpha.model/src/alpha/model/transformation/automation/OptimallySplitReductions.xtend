package alpha.model.transformation.automation

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaSystem
import alpha.model.AlphaVisitable
import alpha.model.Equation
import alpha.model.ReduceExpression
import alpha.model.prdg.PRDG
import alpha.model.prdg.PRDGEdge
import alpha.model.prdg.PRDGGenerator
import alpha.model.prdg.PRDGNode
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.transformation.reduction.SerializeReduction
import alpha.model.transformation.reduction.SplitReduction
import alpha.model.util.AbstractAlphaCompleteVisitor
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSchedule.JNIISLSchedulingOptions
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import java.util.ArrayList
import java.util.List
import java.util.Map
import java.util.Set

import static extension alpha.model.util.ISLUtil.*
import alpha.model.util.Show
import fr.irisa.cairn.jnimap.isl.ISLVal

class OptimallySplitReductions {
	
	/**
	 * A helper class to represent the 'dummy nodes' that are introduced to a PRDG
	 * in order to impose the necessary constraints on the system.
	 * Each dummy node corresponds to a dependence expression in a reduction.
	 * The schedules on these nodes are used to split the corresponding
	 * reductions optimally.
	 * 
	 * Functions exactly the same as a regular PRDGNode, but can be distinguished from
	 * actual nodes using 'instanceof'.
	 */
	private static class DummyNode extends PRDGNode {
		new(String name, ISLSet domain) {
			super(name, domain, false)
		}
	}
	
	/**
	 * A helper visitor that serializes each piece of a reduction.
	 * `expr` should have each piece of the reduction as its descendants.
	 * `reuseDepMap` maps domains to multi-reuse dependences. If the domain of 
	 * a reduction is a subset of one of the keys of `reuseDepMap`, then it will
	 * be serialized according to the respective value.
	 */
	private static class ReductionSerializer extends AbstractAlphaCompleteVisitor {		
		Map<ISLSet, Iterable<ISLMultiAff>> reuseDepMap
		
		def static apply(AlphaVisitable expr, Map<ISLSet, Iterable<ISLMultiAff>> reuseDepMap) {
			val serializer = new ReductionSerializer(reuseDepMap)
			expr.accept(serializer)
		}
		
		new(Map<ISLSet, Iterable<ISLMultiAff>> reuseDepMap) {
			this.reuseDepMap = reuseDepMap
		}
		
		override void outReduceExpression(ReduceExpression reduceExpression) {
			val domain = reduceExpression.body.getContextDomain
			val superDomain = reuseDepMap.keySet.findFirst[key | domain.copy.isSubset(key.copy)]
			val reuseDeps = reuseDepMap.get(superDomain)
			
			SerializeReduction.applyAll(reduceExpression, reuseDeps)
		}
	}
	
	static def apply(AlphaSystem sys) {
		// NormalizeReduction must be applied so that reduction PRDGNodes can be mapped back
		// to the corresponding ReduceExpression.
		NormalizeReduction.apply(sys)
		val PRDG prdg = PRDGGenerator.apply(sys)
		
		// Only extend the PRDG if there are reductions that can be split
		val Set<PRDGEdge> reductionEdges = prdg.getEdges.filter[edge | edge.source.isReductionNode && !edge.dest.isReductionNode].toSet
		val Set<PRDGEdge> splittableEdges = reductionEdges.filter[edge | 
			reductionEdges.exists[other | edge != other && edge.getSource == other.getSource]
		].toSet
		
		if(!splittableEdges.empty) {
			extendPRDG(prdg, splittableEdges)
		}
		
		//Collapse the NR nodes for a cleaner schedule.
		
		var ISLUnionSet domains = prdg.generateDomains
		var islPRDG = prdg.generateISLPRDG
		val schedule = ISLSchedule.computeSchedule(domains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER)
		
		val nTimeDims = countTimeDimensions(
			sys, 
			schedule.getMap.maps.filter[map | 
				!(prdg.getNode(map.getInputTupleName) instanceof DummyNode)
			].toList.convertToUnionMap
		)
		
		// Factoring out constants from the spacetime map makes the serialization process
		// much less complex.
		val ISLUnionMap timeMap = liftSpacetimeFactors(convertToUnionMap(
			prdg.nodes.filter[node | node instanceof DummyNode].map[ node |
				val reductionNode = prdg.correspondingReductionNode(node as DummyNode)
				return processDummySchedule(schedule, reductionNode, node).toMultiAff
					.affs.subList(0, nTimeDims)
					.convertToMultiAff.toMap
			].toList
		))
		
		split(sys, timeMap, prdg)
	}
	
	
	def static private void extendPRDG(PRDG prdg, Set<PRDGEdge> splittableEdges) {
		val Set<PRDGNode> reductionBodyNodes = splittableEdges.map[edge | edge.source].toSet
		
		//For each reduction, construct the necessary dummy nodes/edges
		reductionBodyNodes.forEach[node |
			val dummyNodes = setupDummyNodes(prdg, node)
			setupDummyEdges(prdg, node, dummyNodes)
		]
	}
	
	def static private List<PRDGNode> setupDummyNodes(PRDG prdg, PRDGNode node) {
		var dummyNodes = new ArrayList<PRDGNode>
		//The cross product of the domain with itself, plus one index 'c'
		//The scheduling coefficient of 'c' will be positive, and can be used as a constant bias term
		val ISLSet productDomain =  node.getDomain
			.flatProduct(node.getDomain)
			.flatProduct(toISLSet("[]->{[c] : }"))
		
		//Restrict to pairs of points in the same slice
		val ISLMap writeMap = prdg.getEdges.filter[edge | edge.isReductionEdge && edge.dest == node]
			.get(0).getMap.copy.reverse

		val ISLMap firstWriteMap = firstPointMap(productDomain).applyRange(writeMap.copy)
		val ISLMap secondWriteMap = secondPointMap(productDomain).applyRange(writeMap.copy)
		
		val ISLSet writeLexEQSet = buildLexEQSet(
			firstWriteMap.toMultiAff,
			secondWriteMap.toMultiAff
		)
		val ISLSet domain = productDomain.intersect(writeLexEQSet).simplify
		
		//Instantiate and add one node for every dependence
		val dependenceEdges = getReadEdges(prdg, node)
		for(var i = 0; i < dependenceEdges.size; i++) {
			var dummyNode = new DummyNode(node.getName + "_DUMMY" + i, domain)
			prdg.addNode(dummyNode)
			dummyNodes.add(dummyNode)
		}
		return dummyNodes
	}
	
	/**
	 * Sets up the dummy edges using the techniques outlined in the paper (see wiki)
	 */
	def static private void setupDummyEdges(PRDG prdg, PRDGNode node, List<PRDGNode> dummyNodes) {
		val readEdges = getReadEdges(prdg, node)
		for(var i = 0; i < readEdges.size; i++) {
			setupScheduleMatchingEdges(prdg, readEdges.get(i), dummyNodes.get(i))
			setupPeakBoundingEdges(prdg, node, dummyNodes.get(i))
		}
	}
	
	/**
	 * Force the dummy node to have a schedule corresponding to the real one
	 * i.e. the lambdas for each dimension in _R_DUMMYi should be the same as
	 * the corresponding dimension in R
	 */
	def static private setupScheduleMatchingEdges(PRDG prdg, PRDGEdge readEdge, PRDGNode dummyNode) {
		val PRDGNode readNode = readEdge.dest
		val ISLMap EQMap = dummyNode.getDomain.firstPointMap.toMultiAff
			.add(dummyNode.getDomain.secondPointMap.toMultiAff)
			.toMap
			.applyRange(readEdge.getMap)
		
		//_R_DUMMYi[x,x',c=1] -> V[r(x+x')]
		val ISLSet GEDomain = withCEqualTo(ISLSet.buildUniverse(dummyNode.getSpace), 1)
		val ISLMap GEMap = EQMap.copy.intersectDomain(GEDomain)
		val GEEdge = new PRDGEdge(dummyNode, readNode, GEMap)
		
		prdg.addEdge(GEEdge)
		
		//V[r(2x)] -> _R_DUMMY[x,x,c=-1]
		val ISLSet LEDomain = withCEqualTo(ISLSet.buildUniverse(dummyNode.getSpace), -1)
		val ISLMap LEMap = EQMap.copy.reverse.intersectRange(LEDomain)
		val LEEdge = new PRDGEdge(readNode, dummyNode, LEMap)
		
		prdg.addEdge(LEEdge)
	}
	
	/**
	 * Apply constraints that guarantee the peak is bounded.
	 * for all i, R_RESULT[2*w(x)] -> _R_DUMMYi[x, x', c]
	 * with addtl. constraint x_i-x'_i >= c
	 */
	def static private setupPeakBoundingEdges(PRDG prdg, PRDGNode node, PRDGNode dummyNode) {
		val PRDGNode resultNode = prdg.getEdges.filter[edge | edge.isReductionEdge && edge.dest == node].get(0).getSource
		val ISLMap writeMap = prdg.getEdges.filter[edge | edge.isReductionEdge && edge.dest == node]
			.get(0).getMap.copy.reverse
				
		val int halfDim = Math.floor(dummyNode.getDomain.dim(ISLDimType.isl_dim_out) / 2) as int
		for(var i = 0; i < halfDim; i++) {
			//Skip building edge if distance along dim i is always zero
			val distAff = ISLAff.buildVarOnDomain(dummyNode.getLocalSpace, ISLDimType.isl_dim_out, i)
				.sub(ISLAff.buildVarOnDomain(dummyNode.getLocalSpace, ISLDimType.isl_dim_out, i+halfDim))
				
			val boolean distAffIsZero = ISLSet.buildEQSet(distAff.copy, ISLAff.buildZero(dummyNode.getLocalSpace))
				.intersect(dummyNode.getDomain)
				.isPlainEqual(dummyNode.getDomain)
			
			if(!distAffIsZero) {
				//Get ISLSet for x_i-x'_i >= c
				val cAff = ISLAff.buildVarOnDomain(dummyNode.getLocalSpace, ISLDimType.isl_dim_out, halfDim*2)
				val cDistConstraint = ISLSet.buildGESet(distAff, cAff)
				val constrainedDomain = dummyNode.getDomain.intersect(cDistConstraint)
			
				//Intersect domain of _R_DUMMY with aforementioned constraint
				val ISLMap iBoundedMap = dummyNode.getDomain.firstPointMap.toMultiAff
					.add(dummyNode.getDomain.secondPointMap.toMultiAff)
					.toMap
					.applyRange(writeMap.copy)
					.intersectDomain(constrainedDomain)
					.reverse
				val PRDGEdge iBoundedEdge = new PRDGEdge(resultNode, dummyNode, iBoundedMap)
				
				prdg.addEdge(iBoundedEdge)
			}
		}
	}
	
	/**
	 * Generates the map [x, x', c] -> [x]
	 */
	def static private ISLMap firstPointMap(ISLSet productDomain) {
		val int halfDim = Math.floor(productDomain.dim(ISLDimType.isl_dim_out) / 2) as int
		return productDomain.copy.identity
			.projectOut(ISLDimType.isl_dim_out, halfDim, halfDim)
			.projectOut(ISLDimType.isl_dim_out, halfDim, 1)
	}
	
	/**
	 * Generates the map [x, x', c] -> [x']
	 */
	def static private ISLMap secondPointMap(ISLSet productDomain) {
		val int halfDim = Math.floor(productDomain.dim(ISLDimType.isl_dim_out) / 2) as int
		return productDomain.copy.identity
			.projectOut(ISLDimType.isl_dim_out, 0, halfDim)
			.projectOut(ISLDimType.isl_dim_out, halfDim, 1)
	}
	
	/**
	 * Creates a set that constrains c (the last dimension of _R_DUMMY)
	 * to be equal to an integer value
	 */
	def static private ISLSet withCEqualTo(ISLSet productDomain, int c) {
		val int dim = productDomain.dim(ISLDimType.isl_dim_out)
		
		return productDomain.copy.addConstraint(
			ISLConstraint.buildEquality(productDomain.getSpace.copy)
				.setCoefficient(ISLDimType.isl_dim_out, dim-1, -1)
				.setConstant(c)
		)
	}
	
	def static private List<PRDGEdge> getReadEdges(PRDG prdg, PRDGNode node) {
		 return prdg.getEdges.filter[edge | edge.source == node && prdg.getNodes.contains(edge.dest)].toList
	} 
	
	def static private void split(AlphaSystem sys, ISLUnionMap timeMap, PRDG prdg) {
		for( PRDGNode node : prdg.getNodes.filter[node | node.isReductionNode]) {
			val Iterable<DummyNode> dummyNodes = prdg.correspondingDummyNodes(node)
			val Iterable<ISLMultiAff> maffs = dummyNodes.map[dummyNode | 
				timeMap.maps.findFirst[map | map.inputTupleName == dummyNode.name].copy.clearInputTupleName.toMultiAff
			]
			val AbstractReduceExpression are = node.getOriginEquation(sys).expr as AbstractReduceExpression
			val int nullspaceDim = are.getProjection.copy.nullSpace.dimensionality
			
			//Projects points onto the kernel of the write function
			val ISLMultiAff projMaff = are.getProjection.copy.nullSpace.getBasisVectors.map[vector | 
				buildProjectionMaff(vector)
			].reduce[m1, m2 | m1.add(m2)]
			
			//Splits reduction body according to the dominant read function
			val Iterable<ISLSet> splitPieces = SplitReduction.applyDominanceSplit(are, maffs)
			
			//Reference must be re-retrieved
			val Equation equation = node.getOriginEquation(sys)
			
			//Reduce any common factors in each dimension of the maff to avoid a complexity explosion
			val Iterable<ISLMultiAff> reducedMaffs = maffs.map[maff | 
				maff.getAffs.map[aff | 
					val factor = 
						(0..aff.dim(ISLDimType.isl_dim_in)-1).map[ int i |
						aff.getCoefficientVal(ISLDimType.isl_dim_in, i)
						].filter[ ISLVal a |
							!a.isZero
						].reduce[ ISLVal a, ISLVal b | 
							a.copy.abs.gcd(b.copy.abs)
						]
					factor === null ? aff.copy : aff.copy.setConstant(0).scaleDown(factor)
				].convertToMultiAff
			]
			
			//Next, convert the maffs into lists of reuseDeps
			val Iterable<Iterable<ISLMultiAff>> reuseDepsList = reducedMaffs.map[maff | 
				val ISLLocalSpace localSpace = maff.copy.toMap.getDomain.getSpace.toLocalSpace
				val reuseDeps = maff.getAffs.map[aff | 
					val depMaff = 
						(0..aff.dim(ISLDimType.isl_dim_in)-1).map[i | 
							ISLAff.buildValOnDomain(localSpace.copy, aff.getCoefficientVal(ISLDimType.isl_dim_in, i))
								.negate
						].toList.convertToMultiAff
					
					projMaff.copy.pullback(depMaff.copy)
						.add(ISLMultiAff.buildIdentity(depMaff.getSpace.copy))
				].filter[ dep | 
					!dep.isIdentity
				]
				return reuseDeps.toList.subList(0, Math.min(nullspaceDim, reuseDeps.size))
			]
			
			val depsMap = (0..splitPieces.size-1).toMap(
				[i|splitPieces.get(i)],
				[i|reuseDepsList.get(i)]
			)

			ReductionSerializer.apply(equation, depsMap)
		}
	}
	
	/**
	 * Gets the DummyNodes in a PRDG that correspond to a given real reduction node.
	 */
	private static def Iterable<DummyNode> correspondingDummyNodes(PRDG prdg, PRDGNode reductionNode) {
		prdg.getNodes.filter[ node | 
			node instanceof DummyNode && reductionNode.name == node.name.substring(0, node.name.lastIndexOf("_DUMMY"))
		].map[ node | node as DummyNode]
	}
	
	/**
	 * Gets the real reduction PRDGNode in a PRDG that corresponds to a given dummy node.
	 */
	private static def PRDGNode correspondingReductionNode(PRDG prdg, DummyNode dummyNode) {
		prdg.getNodes.findFirst[ node | 
			!(node instanceof DummyNode) && node.name == dummyNode.name.substring(0, dummyNode.name.lastIndexOf("_DUMMY"))
		]
	}
	
	/**
	 * Converts the virtual schedule of the dummy nodes into a schedule on the corresponding reduction node
	 * The tuple name is not present on the processed schedule
	 */
	private static def ISLMap processDummySchedule(ISLSchedule schedule, PRDGNode reductionNode, PRDGNode dummyNode) {
		val productMap = reductionNode.getDomain.identity
			.rangeProduct(reductionNode.getDomain.identity)
			.rangeProduct(ISLAff.buildZero(reductionNode.getLocalSpace).toMultiAff.toMap).flatten
		
		val rawSchedule = schedule.map.maps.filter(map | map.inputTupleName == dummyNode.name).head.copy.clearInputTupleName
			
		return productMap.applyRange(rawSchedule).setInputTupleName(dummyNode.name)
	}
}