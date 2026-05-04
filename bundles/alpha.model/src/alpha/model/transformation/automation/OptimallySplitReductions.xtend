package alpha.model.transformation.automation

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaSystem
import alpha.model.prdg.PRDG
import alpha.model.prdg.PRDGEdge
import alpha.model.prdg.PRDGGenerator
import alpha.model.prdg.PRDGNode
import alpha.model.scheduler.FoutrierScheduler
import alpha.model.scheduler.Scheduler
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.transformation.reduction.SplitReduction
import alpha.model.util.ISLUtil.Dims
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.util.ISLUtil.*
import alpha.model.util.Show

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
	 * Applies the optimal splitting process to a system.
	 */
	def static void apply(AlphaSystem sys) {
		// NormalizeReduction must be applied so that reduction PRDGNodes can be mapped back
		// to the corresponding ReduceExpression.
		NormalizeReduction.apply(sys)
		val PRDG prdg = PRDGGenerator.apply(sys)
		
		val reductionEdges = prdg.getEdges.filter[source.isReductionNode && !dest.isReductionNode]
		val splittableEdges = reductionEdges.filter[ 
			reductionEdges.exists[other | it != other && getSource == other.getSource]
		]
		
		// Only extend the PRDG if there are reductions that can be split
		if(splittableEdges.empty) return
		
		val extendedPrdg = extendPRDG(prdg, splittableEdges)
		val scheduler = new FoutrierScheduler(extendedPrdg)
		
		split(sys, scheduler, extendedPrdg)
	}
	
	def static private PRDG extendPRDG(PRDG prdg, Iterable<PRDGEdge> splittableEdges) {
		val reductionBodyNodes = splittableEdges.map[source]
		
		//For each reduction, construct the necessary dummy nodes/edges
		val dummyNodes = reductionBodyNodes.map[setupDummyNodes(prdg, it)]
		val dummyEdges = dummyNodes.map[setupDummyEdges(prdg, it)]
		
		val extendedPrdg = new PRDG
		extendedPrdg.setNodes((prdg.nodes + dummyNodes.flatten).toSet)
		extendedPrdg.setEdges((prdg.edges + dummyEdges.flatten).toSet)
		
		return extendedPrdg
	}
	
	def static private Iterable<DummyNode> setupDummyNodes(PRDG prdg, PRDGNode node) {
		//The cross product of the domain with itself, plus one index 'c'
		//The scheduling coefficient of 'c' will be positive, and can be used as a constant bias term
		val ISLSet productDomain = node.getDomain
			.flatProduct(node.getDomain)
			.flatProduct(toISLSet("[]->{[c] : }"))
		
		//Restrict to pairs of points in the same slice
		val ISLMap writeMap = prdg.getEdges.findFirst[isReductionEdge && dest == node]
			.map.copy.reverse

		val ISLMap firstWriteMap = firstPointMap(productDomain).applyRange(writeMap.copy)
		val ISLMap secondWriteMap = secondPointMap(productDomain).applyRange(writeMap.copy)
		
		val ISLSet writeLexEQSet = buildLexEQSet(
			firstWriteMap.toMultiAff,
			secondWriteMap.toMultiAff
		)
		val ISLSet domain = productDomain.intersect(writeLexEQSet).simplify
		
		//Instantiate and add one node for every dependence
		val dependenceEdges = getReadEdges(prdg, node)
		
		val dummyNodes = (0 ..< dependenceEdges.size)
			.map[ i | new DummyNode(node.getName + "_DUMMY" + i, domain) ]
			
		return dummyNodes
	}
	
	/**
	 * Sets up the dummy edges using the techniques outlined in the paper (see wiki)
	 */
	def static private Iterable<PRDGEdge> setupDummyEdges(PRDG prdg, Iterable<DummyNode> dummyNodes) {
		val reductionNode = dummyNodes.findFirst[true]
			.correspondingReductionNode(prdg)
			
		val readEdges = getReadEdges(prdg, reductionNode)
		
		(0 ..< readEdges.size).flatMap[ i |
			setupScheduleMatchingEdges(prdg, readEdges.get(i), dummyNodes.get(i))
			 + setupPeakBoundingEdges(prdg, reductionNode, dummyNodes.get(i))
		]
	}
	
	/**
	 * Force the dummy node to have a schedule corresponding to the real one
	 * i.e. the lambdas for each dimension in _R_DUMMYi should be the same as
	 * the corresponding dimension in R
	 */
	def static private Iterable<PRDGEdge> setupScheduleMatchingEdges(PRDG prdg, PRDGEdge readEdge, PRDGNode dummyNode) {
		val PRDGNode readNode = readEdge.dest
		val ISLMap EQMap = dummyNode.getDomain.firstPointMap.toMultiAff
			.add(dummyNode.getDomain.secondPointMap.toMultiAff)
			.toMap
			.applyRange(readEdge.getMap)
		
		//_R_DUMMYi[x,x',c=1] -> V[r(x+x')]
		val ISLSet GEDomain = withCEqualTo(EQMap.getDomain, 1)
		val ISLMap GEMap = EQMap.copy.intersectDomain(GEDomain)
		val GEEdge = new PRDGEdge(dummyNode, readNode, GEMap)

		//V[r(2x)] -> _R_DUMMY[x,x,c=-1]
		val ISLSet LEDomain = withCEqualTo(EQMap.getDomain, -1)
		val ISLMap LEMap = EQMap.copy.reverse.intersectRange(LEDomain)
		val LEEdge = new PRDGEdge(readNode, dummyNode, LEMap)
		
		return #[LEEdge, GEEdge]
	}
	
	/**
	 * Apply constraints that guarantee the peak is bounded.
	 * for all i, R_RESULT[2*w(x)] -> _R_DUMMYi[x, x', c]
	 * with addtl. constraint x_i-x'_i >= c
	 */
	def static private Iterable<PRDGEdge> setupPeakBoundingEdges(PRDG prdg, PRDGNode node, PRDGNode dummyNode) {
		val domain = dummyNode.getDomain
		
		val writeEdge = prdg.getEdges.findFirst[isReductionEdge && dest == node]
		val writeMap = writeEdge.getMap.copy.reverse
				
		val int halfDim = Math.floor(domain.dim(Dims.OUT) / 2) as int
		
		val cAff = ISLAff.buildVarOnDomain(dummyNode.getLocalSpace, Dims.OUT, halfDim*2)
		val firstPlusSecondPoint = domain.firstPointMap.toMultiAff
			.add(domain.secondPointMap.toMultiAff)
			.toMap
			.applyRange(writeMap.copy)
		
		val diffAffs = (0 ..< halfDim).map[ i |
			ISLAff.buildVarOnDomain(dummyNode.localSpace, Dims.OUT, i)
				.sub(ISLAff.buildVarOnDomain(dummyNode.getLocalSpace, Dims.OUT, i+halfDim))
		]
		
		diffAffs.filter[domain.copy.apply(it.copy.toMultiAff.toMap).isSingleton]
			.map[ ISLSet.buildGESet(it, cAff.copy) ]
			.map[ intersect(domain.copy) ]
			.map[ firstPlusSecondPoint.copy.intersectDomain(it) ]
			.map[ reverse ]
			.map[ new PRDGEdge(writeEdge.source, dummyNode, it) ]
	}
	
	/**
	 * Generates the map [x, x', c] -> [x]
	 */
	def static private ISLMap firstPointMap(ISLSet productDomain) {
		val int halfDim = Math.floor(productDomain.dim(Dims.OUT) / 2) as int
		return productDomain.copy.identity
			.projectOut(Dims.OUT, halfDim, halfDim)
			.projectOut(Dims.OUT, halfDim, 1)
	}
	
	/**
	 * Generates the map [x, x', c] -> [x']
	 */
	def static private ISLMap secondPointMap(ISLSet productDomain) {
		val int halfDim = Math.floor(productDomain.dim(ISLDimType.isl_dim_out) / 2) as int
		return productDomain.copy.identity
			.projectOut(Dims.OUT, 0, halfDim)
			.projectOut(Dims.OUT, halfDim, 1)
	}
	
	/**
	 * Creates a set that constrains c (the last dimension of _R_DUMMY)
	 * to be equal to an integer value
	 */
	def static private ISLSet withCEqualTo(ISLSet productDomain, int c) {
		val int dim = productDomain.dim(Dims.OUT)
		val cConstraint = ISLConstraint.buildEquality(productDomain.getSpace.copy)
				.setCoefficient(Dims.OUT, dim-1, -1)
				.setConstant(c)
		
		return productDomain.copy.addConstraint(cConstraint)
	}
	
	def static private Iterable<PRDGEdge> getReadEdges(PRDG prdg, PRDGNode node) {
		 return prdg.getEdges
		 	.filter[ source == node ]
		 	.filter[ prdg.getNodes.contains(dest) ]
	} 
	
	/***********************************
	 *          Splitting              *
	 ***********************************/
	
	/**
	 * Splits every reduction in a system according to read function dominance.
	 */ 
	static private def split(AlphaSystem sys, Scheduler scheduler, PRDG prdg) {
		val realScheduleMaps = scheduler.maps.maps.reject[
			prdg.getNode(getInputTupleName) instanceof DummyNode
		].toList.convertToUnionMap
		
		val nTimeDims = countTimeDimensions(sys, realScheduleMaps)
		
		prdg.getNodes
			.filter[isReductionNode]
			.filter[hasMultipleDependences(prdg)]
			.forEach[splitNode(sys, scheduler, prdg, nTimeDims)]
		null
	}
	
	/**
	 * Splits a single reduction such that each piece is dominated by one read function.
	 */
	private static def void splitNode(PRDGNode node, AlphaSystem sys, Scheduler scheduler, PRDG prdg, int nTimeDims) {
		val AbstractReduceExpression are = node.getOriginEquation(sys).expr as AbstractReduceExpression
		
		//Convert the schedules on the dummy nodes to the domains of the real node
		//Then take only the time dimensions, and reduce each dim by the gcd
		val Iterable<ISLMultiAff> timeMaffs = prdg.correspondingDummyNodes(node)
			.map[processDummySchedule(node, scheduler)]
			.map[toMultiAff.affs.subList(0, nTimeDims)]
			.map[reduceAffs]
			.map[toList.convertToMultiAff]
		
		//Splits reduction body according to the dominant read function
		SplitReduction.applyDominanceSplit(are, timeMaffs)
	}
	
	/**
	 * Returns whether the given node has multiple dependences.
	 * If not, it should not be split.
	 */
	private static def boolean hasMultipleDependences(PRDGNode node, PRDG prdg) {
		prdg.getEdges
			.filter[source == node]
			.size > 1
	}
	
	/**
	 * Gets the DummyNodes in a PRDG that correspond to a given real reduction node.
	 */
	private static def Iterable<DummyNode> correspondingDummyNodes(PRDG prdg, PRDGNode reductionNode) {
		prdg.getNodes
			.filter[ it instanceof DummyNode ]
			.filter[ reductionNode.name == name.substring(0, name.lastIndexOf("_DUMMY")) ]
			.map[ it as DummyNode ]
	}
	
	/**
	 * Gets the real reduction PRDGNode in a PRDG that corresponds to a given dummy node.
	 */
	private static def PRDGNode correspondingReductionNode(DummyNode dummyNode, PRDG prdg) {
		prdg.getNodes
			.reject[ it instanceof DummyNode ] 
			.findFirst[ name == dummyNode.name.substring(0, dummyNode.name.lastIndexOf("_DUMMY")) ]
	}
	
	/**
	 * Converts the virtual schedule of the dummy nodes into a schedule on the corresponding reduction node
	 * The tuple name is not present on the processed schedule
	 */
	private static def ISLMap processDummySchedule(PRDGNode dummyNode, PRDGNode reductionNode, Scheduler scheduler) {
		val identMaff = reductionNode.getDomain.identity
		
		val productMap = identMaff.copy
			.rangeProduct(identMaff.copy)
			.rangeProduct(ISLAff.buildZero(reductionNode.getLocalSpace).toMultiAff.toMap).flatten
		
		val rawSchedule = scheduler.getAnonymousMap(dummyNode.name)
			
		return productMap.applyRange(rawSchedule)
	}
	
	/**
	 * Scales a list of affs down by their common scalar factor.
	 */
	private static def Iterable<ISLAff> reduceAffs(Iterable<ISLAff> affs) {
		val coeffs = affs.flatMap[
			(0 ..< dim(Dims.IN)).map[ i | getCoefficientVal(Dims.IN, i)]
		]
		
		val gcd = coeffs.reject[ it == 0 ]
			.map[abs]
			.reduce[a, b| a.gcd(b)]
			
		return affs.map[copy.scaleDown(gcd)]
	}
}