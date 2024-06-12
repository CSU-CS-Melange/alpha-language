package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.DependenceExpression
import alpha.model.RestrictExpression
import alpha.model.StandardEquation
import alpha.model.transformation.Normalize
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean
import org.eclipse.emf.ecore.util.EcoreUtil

import static alpha.model.factory.AlphaUserFactory.createCaseExpression
import static alpha.model.factory.AlphaUserFactory.createRestrictExpression
import static alpha.model.util.AlphaUtil.*

import static extension alpha.model.matrix.MatrixOperations.*
import static extension alpha.model.util.AffineFunctionOperations.*
import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.CommonExtensions.dot
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.ISLUtil.toLinearUnitVector

/**
 * This class carries out the analysis required for splitting from the max 
 * simplification paper (https://arxiv.org/abs/2309.11826). This is primarily
 * intended to be used with OptimalSimplifyingReductions.
 * 
 * 1) "covered" (d-2)-faces:
 *    Given an input reduce expression with a d-dimensional body, we need 
 *    to compute the (d-2)-dimensional (d-2)-faces of the body that are 
 *    covered per definition 4.8 in the max simplification paper.
 * 
 *    Making splits thru covered (d-2)-faces is an optimization. The usefulness
 *    of such splits is that it will result in two non-empty pieces. We could
 *    simply try making splits thru all of (d-2)-faces and only keep the ones
 *    that result in two non-empty pieces. This is what the current implementation
 *    does. Detecting covered edges is not currently implemented.
 * 
 * 2) Given a reduction with a 1D REUSE space and a (d-2)-face of the reduction 
 *    body, we need to construct a new constraint that saturates the (d-2)-face 
 *    and the 1D space.
 *    We can do this in ISL by taking the linear space of the (d-2)-face and then
 *    applying the transitive closure of the ILSMultiAff characterizing the null
 *    space of the highest dependence expression in reduction body. 
 * 
 * 3) Given a reduction with a 1D ACCUMULATION space and a (d-2)-face of the reduction 
 *    body, we need to construct a new constraint that saturates the (d-2)-face 
 *    and the 1D space.
 *    We can do this in ISL by taking the linear space of the (d-2)-face and then
 *    applying the transitive closure of the ILSMultiAff characterizing the projection
 *    function.
 *  
 * 
 */
class SplitReduction {
	
	public static boolean DEBUG = false
	
	private static def void debug(String msg) {
		if (DEBUG)
			println("[SplitReduction] " + msg)
	}
	
	/** 
	 * Transforms the input reduction body into two pieces.
	 * Requires that the context domain of the body be a single polyhedron.
	 * This is achieved simply replacing the body with a case expression involving
	 * two branches. Each branch has the same expression, but are restricted to 
	 * one side of the split.
	 * 
	 * Inputs:
	 * 		are: reduction expression to be split
	 *    split: an equality constraint 
	 * 
	 * Let DE be the context domain of the expression E.
	 * Let DS be the half-space defined by the inequality form of the constraint split
	 * Let DS' be the negation of DS (i.e., the opposite half-space)
	 * 
	 * before: reduce(op, f, E)
	 * after:  reduce(op, f, case {DS : E; DS' : E; })
	 * 
	 * Note that the reference to are is no longer contained in the AST since the last
	 * step involves calling PermutationCaseReduce.
	 */
	static def apply(AbstractReduceExpression are, ISLConstraint split) {
		if (are.body.contextDomain.nbBasicSets > 1)
			throw new Exception("Cannot split a reduction body with multiple basic sets")
		
		// if no split is specified then make all possible splits simultaneously
		if (split === null) {
			apply(are)
			return
		}
		
		val constraints = inequalityConstraints(split.aff)
		val DS =  constraints.key.toBasicSet.toSet
		val DSp = constraints.value.toBasicSet.toSet
		
		val caseExpr = createCaseExpression()
		caseExpr.exprs += createRestrictExpression(DS, are.body.copyAE)
		caseExpr.exprs += createRestrictExpression(DSp, are.body.copyAE)
		
		EcoreUtil.replace(are.body, caseExpr)
		AlphaInternalStateConstructor.recomputeContextDomain(are)
		Normalize.apply(are)
		PermutationCaseReduce.apply(are)
	}
	
	/** Given an ISLAff, construct two inequality ISLConstraints (>= and <) */
	static def Pair<ISLConstraint, ISLConstraint> inequalityConstraints(ISLAff splitAff) {
		val DS =  splitAff.copy.toInequalityConstraint
		val const = (splitAff.getConstant - 1).intValue
		val DSp = splitAff.copy.negate.setConstant(const)
						   .toInequalityConstraint
		return DS -> DSp
	}
	
	static def ISLSet toSet(ISLConstraint[] constraints, ISLSpace space) {
		constraints.fold(ISLSet.buildUniverse(space.copy), [ret, c | ret.union(c.toBasicSet.toSet)])
	}
	
	/** 
	 * Returns true if the body domain is 2D and there exist a pair of opposing edges with an
	 * overlapping component in the answer domain.
	 * 
	 * Returns true if there exists the pair (Fi,Fj) s.t. both:
	 *   1) fp(Fi) \cap fp(Fj) != empty
	 *   2) (rho.dot(vi))*(rho.dot(vj)<0
	 * or false, otherwise.
	 */
	static def boolean requiresFractalSplits(AbstractReduceExpression are) {
		val face = are.facet
		if (face.dimensionality != 2) return false;
		
		val edges = face.generateChildren
		val edgePairs = (0..<edges.size).flatMap[i | (i+1..<edges.size).map[j | edges.get(i)->edges.get(j)]]
			.toArrayList
		val fp = are.projection.toMap
		val rho = are.body.getReuseMaff.construct1DBasis.getConstantVectorNoParams

		val vertices = face.getVertices.fold(
			ISLSet.buildEmpty(are.body.contextDomain.space),
			[ret, v | ret.union(v.toSet)]
		)

		val disjointEdges = newHashMap
		edges.forEach[e | disjointEdges.put(e, e.toSet.subtract(vertices.copy))]
		
		return edgePairs
			.reject[
				val fi = disjointEdges.get(key).copy
				val fj = disjointEdges.get(value).copy
				fi.apply(fp.copy).intersect(fj.apply(fp.copy)).isEmpty
			].exists[
				val vi = key.getNormalVector(face).toLinearUnitVector
				val vj = value.getNormalVector(face).toLinearUnitVector
				vi.dot(rho) * vj.dot(rho) < 0
			]
	}
	
	/**
	 * Splits the basic sets of domain into pieces on each side of aff
	 * 
	 * For example, given the following:
	 *   domain: [N]->{[i] : 0<=i<N}
	 *   aff: [N]->{[i,j]->[i-10]}
	 * 
	 * the following ISLSet with 2 ISLBasicSets is returned:
	 *   [N]->{[i] : 0<=i<10; [i] : 10<=i<N}
	 * 
	 * Coalescing the output set should yield the same input set
	 */
	static def ISLSet separateBasicSets(ISLSet domain, ISLAff aff) {
		
		val constraints = aff.inequalityConstraints
		val upperBound = constraints.key
		val lowerBound = constraints.value
		
		val upperPiece = domain.copy.intersect(upperBound.toBasicSet.toSet)
		val lowerPiece = domain.copy.intersect(lowerBound.toBasicSet.toSet)
		
		val ret = upperPiece.union(lowerPiece)
		
		if (!ret.copy.coalesce.isEqual(domain.copy)) {
			throw new Exception('failed to separate domain')
		}
		return ret
	}
	
	/**
	 * Transforms the reduction body into as many pieces as possible by making invariant
	 * splits (i.e., splits containing the reuse space) thru all (d-2)-faces. 
	 * 
	 * Assumes the input reduction body is 2-dimensional
	 */
	static def void apply(AbstractReduceExpression are) {
		val face = are.facet
		
		if (face.dimensionality != 2) return;
		
		val eq = are.getContainerEquation as StandardEquation
		if (!(eq instanceof StandardEquation))
			throw new Exception('Reduce expression container must be a standard equation')
		val stdEq = eq as StandardEquation
			
		var branchDomains = enumerateCandidateSplits(are)
			.map[aff]
			.map[
				debug('found split aff: ' + it)
				it
			].toArrayList
			.fold(
				are.body.contextDomain.copy,
				[ret, aff | ret.separateBasicSets(aff)]
			)
		
		val caseExpr = createCaseExpression()
		branchDomains.basicSets.forEach[domain |
			caseExpr.exprs += createRestrictExpression(domain.copy.toSet, are.body.copyAE)
			debug('created case branch: ' + domain)
		]
		
		EcoreUtil.replace(are.body, caseExpr)
		AlphaInternalStateConstructor.recomputeContextDomain(are)
		Normalize.apply(are)
		PermutationCaseReduce.apply(are)
		NormalizeReduction.apply(stdEq)
	}
	
	/** 
	 * Returns the list of candidate splits that separate the reduction body into 
	 * two non-empty pieces.
	 * These splits are guaranteed to introduce new strong-boundary or strong-invariant
	 * faces by definition since they are constructed from the accumulation space and
	 * reuse space accordingly. This requires that the accumulation space and/or the
	 * reuse space have precisely as 1D null space. If these spaces are larger then 
	 * candidate splits can be constructed.
	 * This method is primarily intended to be called by OptimalSimplifyingReductions 
	 * which systematically applies ReductionDecomposition and DependenceRaising, so this 
	 * should result in the existence of valid splits at some point in the exploration. 
	 */
	static def ISLConstraint[] enumerateCandidateSplits(AbstractReduceExpression are) {
		
		if (DEBUG) { 		
			val eq = are.getContainerEquation
			val stdEq = if (eq instanceof StandardEquation) eq as StandardEquation else null
			debug('enumerating splits for Equation ' + if (stdEq !== null) stdEq.variable.name else '' + ': ' + are)
		}
		
		val splits = newArrayList
		
		val bodyFace = are.facet
		val bodyDim = bodyFace.dimensionality
		if (bodyDim <= 1) {
			return splits
		}
		
		val bodyDomain = bodyFace.toBasicSet
		val faces = bodyFace.lattice.getFaces(bodyDim - 2).map[toBasicSet]
		
		// Construct splits that saturate the accumulation space
		val accVec = are.projection.construct1DBasis
		if (accVec !== null)
			splits.addAll(faces.map[copy.constructSplit(accVec)].reject[s | s === null])
		
		// Construct splits that saturate the reuse space
		val reuseMaff = are.body.getReuseMaff
		val reuseVec = if (reuseMaff !== null) reuseMaff.construct1DBasis else null
		if (reuseVec !== null)
			splits.addAll(faces.map[copy.constructSplit(reuseVec)].reject[s | s === null])
		
		// Remove the splits that don't separate the reduction body into two pieces.
		// This is not necessary if "faces" is populated with only the "covered" (d-2)-faces.
		// This is left for future optimization.
		val usefulSplits = splits.filter[s | s.isUseful(bodyDomain)]
		
		usefulSplits.forEach[s | debug('(enumerateCandidateSplits) ' + s.toString)]
		
		return usefulSplits
	}
	
	/** 
	 * Returns true if the constraint splits the set into two non-empty pieces, or
	 * false otherwise.
	 */
	static def isUseful(ISLConstraint split, ISLBasicSet bset) {
		bset.copy.toSet.subtract(split.copy.toBasicSet.toSet).nbBasicSets == 2
	}
	
	/** 
	 * This function "extends" the set infinitely along the direction of vec via
	 * the transitive closure of vec's ISLMap representation. The extended set is 
	 * guaranteed to have a single equality constraint by construction.
	 */
	static def ISLConstraint constructSplit(ISLBasicSet edge, ISLMultiAff vec) {
		val nbOut = edge.dim(ISLDimType.isl_dim_out)
		val setNoParams = edge.copy.dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, edge.dim(ISLDimType.isl_dim_out))
		
		var exact = new JNIPtrBoolean
		val map = vec.copy.toMap.transitiveClosure(exact)
		if (!exact.value)
			throw new Exception('transitive closure should be exact, something is wrong')
		if (map.nbBasicMaps != 1)
			throw new Exception('transitive closure should have a single basic map')
		
		val hyperplane = renameIndices(setNoParams.copy.apply(map.getBasicMapAt(0)), setNoParams.indexNames)
			
		val eqConstraints = hyperplane.constraints
			.filter[isEquality]
			.filter[involvesDims(ISLDimType.isl_dim_out, 0, nbOut)]
		if (eqConstraints.size != 1) {
			/* 
			 * If execution reaches here, then the space spanned by vec saturates the edge.
			 * For example, imagine we have a 1D edge in Z^3 corresponding to the k-axis. 
			 *  - extending this edge by <1,0,0> (along i) results in a 2D hyperplane (i.e., ik-plane) 
			 *  - extending this edge by <0,0,1> (along k) results in the same 1D edge
			 * 
			 * We can detect when this happens by counting the number of equality constraints in
			 * the generated hyperplane. This is an instance of an uncovered edge. Ideally, we 
			 * will only use covered (per definition 4.8) edges to construct splits. But the 
			 * current implementation simply tries to construct splits from all edges, ignoring
			 * the ones that don't have any meaning, such as these. 
			 */
			return null
		}
		
		val splitConstraint = eqConstraints.get(0)
		splitConstraint
	}
	
	/** 
	 * Returns the 1D vector that spans the null space of the input ISLMultiAff.
	 * maff is required to have exactly one fewer output dimension than input
	 * dimensions.
	 */
	static def ISLMultiAff construct1DBasis(ISLMultiAff maff) {
		val nbIn = maff.dim(ISLDimType.isl_dim_in)
		val nbOut = maff.dim(ISLDimType.isl_dim_out)
		val nbParam = maff.dim(ISLDimType.isl_dim_param)
		
		if (nbIn - nbOut != 1) {
			return null
		}
		
		val kernel = maff.copy
			.dropDims(ISLDimType.isl_dim_param, 0, nbParam)
			.computeKernel
			
		if (kernel.transpose.length != 1)
			throw new Exception("Input maff does not have a 1D null space.")
		
		val mat = columnBind(columnBindToFront(createIdentity(nbIn, nbIn), nbParam), kernel)
		
		val outMaff = mat.toMatrix(maff.space.paramNames, maff.space.inputNames, false, true)
						.toMultiAff
		
		return outMaff
	}
	
	/** 
	 * Returns the ISLSet characterizing the null-space of the dependence function in 
	 * the reduction body.
	 * These dispatch methods should match eventually since OptimalSimplifyingReductions
	 * systematically calls ReductionDecomposition and RaiseDependence
	 * */
	static def dispatch getReuseMaff(RestrictExpression re) { getReuseMaff(re, re.expr)}
	static def dispatch getReuseMaff(RestrictExpression re, DependenceExpression de) { de.function }
	static def dispatch getReuseMaff(RestrictExpression re, AlphaExpression ae) { null }
	static def dispatch getReuseMaff(DependenceExpression de) { de.function }
	static def dispatch getReuseMaff(AlphaExpression ae) { null }
	
}