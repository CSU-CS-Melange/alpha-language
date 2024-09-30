package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.analysis.reduction.ShareSpaceAnalysis
import alpha.model.matrix.MatrixOperations
import alpha.model.transformation.Normalize
import alpha.model.util.AffineFunctionOperations
import alpha.model.util.AlphaOperatorUtil
import alpha.model.util.AlphaUtil
import org.eclipse.xtext.EcoreUtil2
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult
import fr.irisa.cairn.jnimap.isl.ISLDimType

/**
 * Idempotence is one of the transformations that can be applied with
 * a reuse vector overlapping the kernel of the projection function.
 * 
 * When repeated application of the reduction operator over the same
 * value does not change the output (i.e., when the operator is idempotent),
 * the domain of the reduction body may be restricted to avoid repeated operations.
 * 
 * Given a reduction of the form:
 *   reduce(op, f, E)
 * and a projection f_c, where ker(f_c) = ker(f) /\ reuse(E),
 * the transformation produces:
 *  reduce(op, f', E')
 * where
 *  - f' is the domain of f projected by f_c
 *  - and E' is the E re-defined to the space of the domain of f'
 * 
 * This re-definition is done by visiting all DependenceExpressions in E
 * and projecting the domain of the dependence function by f_c.
 */
class Idempotence {
	
	static def apply(AbstractReduceExpression are) {
		transform(are)
	}

	/**
	 * Legality test that should match the one in transform.
	 * Exposed to be used by SimplifyingReductionExploration.
	 * 
	 * Since it is tricky to share the same code, the check code is copy & pasted.
	 * It would be better if they can be merged.
	 */	
	static def testLegality(AbstractReduceExpression are, ShareSpaceAnalysisResult SSAR) {
		if (!AlphaOperatorUtil.isIdempotent(are.operator)) {
			return false;
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
		if (!AlphaOperatorUtil.isIdempotent(are.operator)) {
			throw new IllegalArgumentException("[Idempotence] The operator of the specified reduction is not idempotent.");
		}
		//Idempotence requires the body to be normalized
		// this is for projecting all dependences by f_c
		Normalize.apply(are.body)
		
		val SSAR = ShareSpaceAnalysis.apply(are);
		val areSS = SSAR.getShareSpace(are.body)
		if (areSS === null)
			throw new IllegalArgumentException("[Idempotence] The body of the specified reduction does not have any reuse.");
		
		val kerFp = MatrixOperations.transpose(AffineFunctionOperations.computeKernel(are.projection))
		val kerFc = MatrixOperations.kernelIntersection(kerFp, areSS)
		
		if (kerFc === null)
			throw new IllegalArgumentException("[Idempotence] The intersection of the share space of the reduction body and kernel of the projection is empty.");
			
		val params = AlphaUtil.getContainerSystem(are).parameterDomain.paramNames
		val indices = are.body.contextDomain.indexNames
		val Fc = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, kerFc)
		
		val Fpprime = AffineFunctionOperations.projectFunctionDomain(are.projection, Fc.copy)
		
		ReductionUtil.projectReductionBody(are, Fc.copy)
		
		val replacement = if (Fpprime.getNbInputs == Fpprime.getNbOutputs) {
			are.body
		} else {
			ReductionUtil.constructConcreteReduction(are, are.operator, Fpprime, are.body)
		}

		EcoreUtil2.replace(are, replacement)
		AlphaInternalStateConstructor.recomputeContextDomain(replacement)
		Normalize.apply(replacement)
	}
}