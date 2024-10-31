package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.BINARY_OP
import alpha.model.analysis.reduction.ShareSpaceAnalysis
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult
import alpha.model.factory.AlphaUserFactory
import alpha.model.matrix.MatrixOperations
import alpha.model.transformation.Normalize
import alpha.model.util.AffineFunctionOperations
import alpha.model.util.AlphaOperatorUtil
import alpha.model.util.AlphaUtil
import fr.irisa.cairn.jnimap.barvinok.BarvinokFunctions
import org.eclipse.xtext.EcoreUtil2
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff

/**
 * HigherOrderOperator is one of the transformations that can be applied with
 * a reuse vector overlapping the kernel of the projection function.
 * 
 * When repeated application of the reduction operator over the same
 * value has an associated higher order operator, then the corresponding
 * slice of the reduction may be replaced with an application of the
 * higher order operator.
 * 
 * Given a reduction of the form:
 *   reduce(op, f, E)
 * and a projection f_c, where ker(f_c) = ker(f) /\ reuse(E),
 * the transformation produces:
 *  reduce(op, f', op2(f_c, E))
 * where
 *  - f' is the domain of f projected by f_c
 *  - and op2 is a higher order operator.
 * 
 * Examples of higher order operators include:
 *   - reduce(+, (i->), X[])  => count(DE) * X[]
 *   - reduce(*, (i->), X[] => power(X[], count(DE))
 * where count(DE) is the number of integer points in the 
 * context domain of reduction body.
 * 
 * The above examples are when ker(f) = reuse(E), in which case 
 * the reduction is entirely replaced. In general, partial slices 
 * of each reduction is replaced. The original reduction is viewed 
 * as a composition of two reductions:
 *   - reduce(op, f', reduce(op, f_c, E)) 
 * and the inner reduction now has ker(f_c) = reuse(E).
 * For sum/product, the corresponding higher order operators require:
 *   - E' where the domain of E is projected by f_c
 *   - count(DE), the number of integer points in the inner reduction,
 * parameterized by outer dimensions. This is computed by constructing 
 * a relation R = [\vec(i)] - > [f_c(\vec(i))] : \vec(i) \in X_E
 * where X_E is the context domain of E, and computing the cardinality 
 * after reversing the relation.
 * 
 * It is also possible to reduce O(n) reduction to O(logn) for any reduction
 * operator, but this transformation is not supported.
 * 
 * FIXME Current implementation only supports addition => product. 
 */
class HigherOrderOperator {
	
	static def apply(AbstractReduceExpression are) {
		transform(are)
	}
	
	/**
	 * Legality test that should match the one in transform. 
	 * Exposed to be used by SimplifyingReductionExploration.
	 * 
	 * It is quite similar to Idempotence, may be it can be merged somewhere.
	 */
	static def testLegality(AbstractReduceExpression are, ShareSpaceAnalysisResult SSAR) {
		if (!AlphaOperatorUtil.hasHigherOrderOperator(are.operator)) {
			return false
		}
		
		val areSS = SSAR.getShareSpace(are.body)
		if (areSS === null) {
			return false
		}
		
		val kerFp = MatrixOperations.transpose(AffineFunctionOperations.computeKernel(are.projection))
		val kerFc = MatrixOperations.kernelIntersection(kerFp, areSS)
		
		if (kerFc === null) {
			return false
		}
			
		return true
	}
	
	private static def transform(AbstractReduceExpression are) {
		if (!AlphaOperatorUtil.hasHigherOrderOperator(are.operator)) {
			throw new IllegalArgumentException("[HigherOrderOperator] Higher order operator for the operator of the specified reduction is not available.");
		}
		//HigherOrderOperator requires the body to be normalized
		// this is for projecting all dependences by f_c
		Normalize.apply(are.body)
		
		val SSAR = ShareSpaceAnalysis.apply(are);
		val areSS = SSAR.getShareSpace(are.body)
		if (areSS === null)
			throw new IllegalArgumentException("[HigherOrderOperator] The body of the specified reduction does not have any reuse.");
		
		val kerFp = MatrixOperations.transpose(AffineFunctionOperations.computeKernel(are.projection))
		val kerFc = MatrixOperations.kernelIntersection(kerFp, areSS)
		
		if (kerFc === null)
			throw new IllegalArgumentException("[HigherOrderOperator] The intersection of the share space of the reduction body and kernel of the projection is empty.");
			
		val params = AlphaUtil.getContainerSystem(are).parameterDomain.paramNames
		val indices = are.body.contextDomain.indexNames
		val Fc = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, kerFc)
		
		val Fpprime = AffineFunctionOperations.projectFunctionDomain(are.projection, Fc.copy)
		
		val replacementBody = constructHigherOrderOperation(are, Fc)
		
		val replacement = if (Fpprime.getNbInputs == Fpprime.getNbOutputs) {
			replacementBody
		} else {
			ReductionUtil.constructConcreteReduction(are, are.operator, Fpprime, replacementBody)
		}

		EcoreUtil2.replace(are, replacement)
		AlphaInternalStateConstructor.recomputeContextDomain(replacement)
		Normalize.apply(replacement)
	}
	
	private static def constructHigherOrderOperation(AbstractReduceExpression are, ISLMultiAff Fc) {
		val card = BarvinokFunctions.card(Fc.copy.toMap.intersectDomain(are.body.contextDomain).reverse)
		
		ReductionUtil.projectReductionBody(are, Fc.copy)
		
		switch (are.operator) {
			case SUM: {
				val pie = AlphaUserFactory.createPolynomialIndexExpression(card)
				AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL,
					pie,
					are.body
				)
			}
			default: {
				null
			}
		}
	}
}