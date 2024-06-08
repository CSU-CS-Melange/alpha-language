package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.util.AffineFunctionOperations
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean

import static alpha.model.factory.AlphaUserFactory.createDependenceExpression
import org.eclipse.emf.ecore.util.EcoreUtil
import alpha.model.AlphaInternalStateConstructor
import alpha.model.transformation.Normalize

class RemoveEmbedding {
	
	/**
	 * Apply the transformation:
	 * reduce(op, fp, E) -> h @ reduce(op, fp, D : E)
	 * 
	 * where:
	 * - h is the transitive closure of the uniform dependence of case 2
	 * - D is the POS-face of the residual reduction induced by rho
	 */
	def static apply(AbstractReduceExpression reduceExpr, ISLMultiAff rho) {
//		// Inputs
//		val yDomain = "[N]->{[i,k]: 0<=i<=N and 0<=k<=4N and i+k>=N}".toISLSet
//		val resultSpace = yDomain.copy.space
//		val projection = "[N]->{[i,j,k]->[i,k]}".toISLMultiAff
//		val rho = "[N]->{[i,j,k]->[i,j,k+1]}".toISLMultiAff
//		val posFace = "[N]->{[i,j,k]: 0<=i,j and i+j+k>=N and 0<=k<=4N and i+j<=N and i+k=N}".toISLSet
		
		val resultSpace = reduceExpr.contextDomain.space
		val fp = reduceExpr.projection
		val reuseDep = AffineFunctionOperations.negateUniformFunction(rho)
		val basicElements = SimplifyingReductions.computeBasicElements(reduceExpr, reuseDep)
		val Dadd = basicElements.Dadd
		
		
		
		// Compute the uniform dependence for reusing previous answers per GR06 Thm 5
		val uniformReuseDependence = SimplifyingReductions.constructDependenceFunctionInAnswerSpace(resultSpace, fp, reuseDep)
		println("Uniform dep for reusing prev answers: " + uniformReuseDependence)
		
		// Compute the image of the pos face by the reduction's projection function.
		// This is the set of answers being computed by "case 1" (the reduction over the pos face).
//		val computedAnswers = posFace.apply(fp.copy.toMap)
		val computedAnswers = Dadd
		println("Computed answers: " + computedAnswers)
		
		// Take the transitive closure of the uniform dependence.
		val isExact = new JNIPtrBoolean
		val closure = uniformReuseDependence.copy.toMap.transitiveClosure(isExact)
		if (!isExact.value)
			throw new Exception('Transitive closure should be exact, something went wrong')
		
		// Intersect the range of the closure with Dadd
		// to get the affine dependence that reuses the answers from the POS-face.
		val intersected = closure.intersectRange(computedAnswers)
		val h = intersected.copy.toPWMultiAff
			.getPiece(0)
			.maff
		println("Intersect with Y: " + intersected)
		println(h)
		
		val newReduceExpr = SimplifyingReductions.createXadd(reduceExpr, basicElements)
		val depExpr = createDependenceExpression(h, newReduceExpr)
		
		EcoreUtil.replace(reduceExpr, depExpr)
		AlphaInternalStateConstructor.recomputeContextDomain(depExpr)
		Normalize.apply(depExpr)
		
		println
	}
	
}