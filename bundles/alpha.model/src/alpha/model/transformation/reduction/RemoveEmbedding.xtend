package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.util.AffineFunctionOperations
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean

import static alpha.model.factory.AlphaUserFactory.createDependenceExpression
import org.eclipse.emf.ecore.util.EcoreUtil
import alpha.model.AlphaInternalStateConstructor
import alpha.model.transformation.Normalize
import fr.irisa.cairn.jnimap.isl.ISLSet

class RemoveEmbedding {
	
	public static boolean DEBUG = true;
	
	private static def void debug(String msg) {
		if (DEBUG)
			println("[RemoveEmbedding] " + msg)
	}
	
	/**
	 * Apply the transformation:
	 * reduce(op, fp, E) -> h @ reduce(op, fp, D : E)
	 * 
	 * where:
	 * - h is the transitive closure of the uniform dependence of case 2
	 * - D is the POS-face of the residual reduction induced by rho
	 */
	def static apply(AbstractReduceExpression reduceExpr, ISLMultiAff rho) {
		
		val resultSpace = reduceExpr.contextDomain.space
		val fp = reduceExpr.projection
		val reuseDep = AffineFunctionOperations.negateUniformFunction(rho)
		val basicElements = SimplifyingReductions.computeBasicElements(reduceExpr, reuseDep)
		val Dadd = basicElements.Dadd
		
		// Compute the uniform dependence for reusing previous answers per GR06 Thm 5
		val uniformReuseDependence = SimplifyingReductions.constructDependenceFunctionInAnswerSpace(resultSpace, fp, reuseDep)
		
		// Intersect the range of the closure with Dadd to get the affine dependence that 
		// reuses the answers from the POS-face.
		val h = uniformReuseDependence.transitiveClosureAt(Dadd)
		
		val newReduceExpr = SimplifyingReductions.createXadd(reduceExpr, basicElements)
		val depExpr = createDependenceExpression(h, newReduceExpr)
		
		EcoreUtil.replace(reduceExpr, depExpr)
		AlphaInternalStateConstructor.recomputeContextDomain(depExpr)
		Normalize.apply(depExpr)
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
	
}