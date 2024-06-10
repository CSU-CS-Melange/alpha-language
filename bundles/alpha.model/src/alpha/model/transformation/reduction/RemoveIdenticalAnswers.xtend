package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaSystem
import alpha.model.ReduceExpression
import alpha.model.analysis.reduction.CandidateReuse
import alpha.model.analysis.reduction.ShareSpaceAnalysis
import alpha.model.transformation.Normalize
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.util.AffineFunctionOperations
import alpha.model.util.Show
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean
import org.eclipse.emf.ecore.util.EcoreUtil

import static alpha.model.factory.AlphaUserFactory.createDependenceExpression

import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.AlphaUtil.getContainerSystem
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension alpha.model.util.ISLUtil.nullSpace

/**
 * 
 * Removes the computation of identical answers in any ReduceExpressions where applicable. 
 * This happens when there is non-trivial reuse in the reduction body AND there exists a 
 * labeling such that all POS- and NEG-faces can be made boundary faces per Theorem 6 of 
 * GR06. This class performs the following transformation on reductions where applicable:
 * 
 *   reduce(op, fp, E) -> h @ reduce(op, fp, D : E)
 * 
 * where h is the affine dependence "pulled out" of the reduction body characterizing
 * identical answers computed in the body. D is the union of boundary faces of the 
 * reduction body. This can be viewed as a special case of simplification.
 * 
 * 
 * As a simple example, take the following system,
 * -------------------------------------------------------
 *   inputs
 *     X : {[i] : 0<=i<=N}
 *   outputs
 *     Y : {[i] : 0<=i<=N}
 *   let
 *     Y[i] = reduce(+, (i,j->i), {: 0<=i,j<=N } : X[j])
 * -------------------------------------------------------
 * which computes the same value for each element of Y.
 * 
 *  In this example, consider the candidate reuse vector <i,j> = <1,0>. This choice of 
 * reuse results in the following labeling on the four faces:
 *   1) {: i=0} -> POS-face		(boundary)
 *   2) {: j=0} -> ZERO-face
 *   3) {: i=N} -> NEG-face		(boundary)
 *   4) {: j=N} -> ZERO-face
 * 
 * Simplifying this reduction along <1,0> per Theorem 5 of GR06 results in
 * the following:
 * -------------------------------------------------------
 *   inputs
 *     X : {[i] : 0<=i<=N}
 *   outputs
 *     Y : {[i] : 0<=i<=N}
 *   let
 *     Y[i] = case {
 *       {: i=0} : reduce(+, (i,j->i), {: i=0 and 0<=j<=N } : X[j]);
 *       {: i>0} : Y[i-1];
 *     }
 * -------------------------------------------------------
 * 
 * The equation for Y depends recursively on itself, but since none of the other branches
 * from Theorem 5 are present, it is valid to express Y as the following instead:
 * 
 *    Y[i] = (i->0)@reduce(+, (i,j->i), {: i=0 and 0<=j<=N } : X[j]); 
 */

class RemoveIdenticalAnswers extends AbstractAlphaCompleteVisitor {
	
	public static boolean DEBUG = false;
	
	private static def void debug(String msg) {
		if (DEBUG)
			println("[RemoveEmbedding] " + msg)
	}
	
	
	/** Tries to remove identical answer dimensions for all reduce expressions */
	def static apply(AlphaSystem system) {
		val visitor = new RemoveIdenticalAnswers
		system.accept(visitor)
	}
	
	/** Tries to remove identical answer dimensions from reduceExpr */
	def static apply(AbstractReduceExpression reduceExpr) {
		throw new Exception
	}


	override outReduceExpression(ReduceExpression reduceExpr) {
		val candidateReuse = new CandidateReuse(reduceExpr)
		if (!candidateReuse.hasIdenticalAnswers)
			return
			
		val rho = candidateReuse.identicalAnswerBasis
		transform(reduceExpr, rho, candidateReuse.getIdenticalAnswerDomain)
	}
	
	/**
	 * Apply the transformation:
	 * reduce(op, fp, E) -> h @ reduce(op, fp, D : E)
	 * 
	 * where:
	 * - h is the transitive closure of the uniform dependence of case 2
	 * - D is the POS-face of the residual reduction induced by rho
	 */
	def static transform(AbstractReduceExpression reduceExpr, ISLMultiAff rho) {
		transform(reduceExpr, rho, ISLSet.buildEmpty(reduceExpr.body.contextDomain.space))
	}
	def static transform(AbstractReduceExpression reduceExpr, ISLMultiAff rho, ISLSet decompositionDomain) {
		
		var targetReduceExpr = reduceExpr
		val eq = targetReduceExpr.getContainerEquation
		val system = eq.getContainerSystem
		
		// decompositionDomain is the inner accumulation space along which to do the
		// decomposition
		if (reduceExpr.canBeDecomposed && !decompositionDomain.isEmpty) {
			debug('decompositionDomain: ' + decompositionDomain)
			val SSAR = ShareSpaceAnalysis.apply(targetReduceExpr)
			val decompositions = SimplifyingReductions.generateDecompositionCandidates(SSAR, targetReduceExpr)
			
			decompositions.forEach[d | debug('decomposition ' + d.toString)]
			
			val decomposition = decompositions
				.findFirst[key.nullSpace.isEqual(decompositionDomain)]
			
			if (decomposition === null) {
				throw new Exception('A valid decomposition should exist, something went wrong.')
			}
			
			val innerProjection = decomposition.key
			val outerProjection = decomposition.value
			
			debug('Before reduction decomposition\n' + Show.print(system))
			ReductionDecomposition.apply(targetReduceExpr, innerProjection.copy, outerProjection.copy)
			targetReduceExpr = targetReduceExpr.body as ReduceExpression
			debug('After reduction decomposition\n' + Show.print(system))
			
		}
		
		val resultSpace = targetReduceExpr.contextDomain.space
		val fp = targetReduceExpr.projection
		val reuseDep = AffineFunctionOperations.negateUniformFunction(rho)
		
		val basicElements = SimplifyingReductions.computeBasicElements(targetReduceExpr, reuseDep)
		val Dadd = basicElements.Dadd
		
		// Compute the uniform dependence for reusing previous answers per GR06 Thm 5
		val uniformReuseDependence = SimplifyingReductions.constructDependenceFunctionInAnswerSpace(resultSpace, fp, reuseDep)
		
		// Intersect the range of the closure with Dadd to get the affine dependence that 
		// reuses the answers from the POS-face.
		val h = uniformReuseDependence.transitiveClosureAt(Dadd)
		
		val newReduceExpr = SimplifyingReductions.createXadd(targetReduceExpr, basicElements)
		
		
		val depExpr = createDependenceExpression(h.copy, newReduceExpr)
		
		EcoreUtil.replace(targetReduceExpr, depExpr)
		AlphaInternalStateConstructor.recomputeContextDomain(depExpr)
		Normalize.apply(depExpr)
		
		debug('reduce(op, fp, E) -> h@reduce(op, fp, D:E)\n' + Show.print(system))
	}
	
	/** 
	 * Returns the affine dependence of the transitive closure of maff with the given range
	 */
	def static ISLMultiAff transitiveClosureAt(ISLMultiAff maff, ISLSet range) {
		// Take the transitive closure of the uniform dependence.
		val isExact = new JNIPtrBoolean
		val closure = maff.copy.toMap.transitiveClosure(isExact)
		if (!isExact.value)
			throw new Exception('Transitive closure should be exact, something went wrong')
		 
		val pieces = closure.intersectRange(range)
			.toPWMultiAff
			.pieces
		
		if (pieces.size != 1)
			throw new Exception('Transitive closure should only contain a single piece, something went wrong')

		pieces.get(0).maff
	}
	
	/** 
	 * Returns the dimensionality of the accumulation space (null space of fp) of the reduction
	 */
	def static int accumulationDimensionality(AbstractReduceExpression reduceExpr) {
		reduceExpr.projection.nullSpace.dimensionality
	}
	
	/** 
	 * Returns true if the accumulation space is multidimensional, or false otherwise 
	 */
	def static boolean canBeDecomposed(AbstractReduceExpression reduceExpr) {
		reduceExpr.accumulationDimensionality > 1
	}
	
}