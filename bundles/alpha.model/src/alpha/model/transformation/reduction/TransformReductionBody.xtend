package alpha.model.transformation.reduction

import alpha.model.ReduceExpression
import alpha.model.util.AffineFunctionOperations
import fr.irisa.cairn.jnimap.isl.ISLMultiAff

import static alpha.model.factory.AlphaUserFactory.createDependenceExpression
import static alpha.model.factory.AlphaUserFactory.createRestrictExpression
import org.eclipse.emf.ecore.util.EcoreUtil
import alpha.model.AlphaInternalStateConstructor

/**
 * Applies TransformReductionBody, a transformation that transforms the domain of the reduction body,
 * without changing the domain of other variables. It is a variation of Change of Basis.
 * 
 * Given ReduceExpression reduce(op, fp, E) and ISLMultiAff T, transforms the reduction to
 * reduce(op, Tinv@fp, image(E) : Tinv@E)
 * The domain is transformed to the image by T, and specified as restrict. All variable accesses are composed
 * with the inverse of T, so that other variables are unchanged.
 * 
 * This transformation should be followed by Normalize to propagate the dependency and restrict expression introduced
 *
 */

class TransformReductionBody {
	
	def static void apply(ReduceExpression reduce, ISLMultiAff T) {
		transformReductionBody(reduce, T);
	}
	
	def static void transformReductionBody(ReduceExpression reduceExpr, ISLMultiAff T) {
		if (T === null) {
			throw new RuntimeException("Transformation T is not given.");
		}
		
		val bodyContextDomain = reduceExpr.body.contextDomain.copy
		
		//check if T is bijective in context
		var ISLMultiAff Tinv;
		try {
			Tinv = AffineFunctionOperations.inverseInContext(T, bodyContextDomain, null)
		} catch (Exception e) {
			throw new RuntimeException("Given transformation is not bijective.");
		}
		
		val depExpr = createDependenceExpression(Tinv, reduceExpr.body);
		val restrictExpr = createRestrictExpression(bodyContextDomain.copy.apply(T.copy.toMap), depExpr);
		EcoreUtil.replace(reduceExpr.body, restrictExpr)
		AlphaInternalStateConstructor.recomputeContextDomain(reduceExpr)
//		
//		//compute the T-1@f and set it as projection function
//	    AffineFunction newProjection = reduce.getProjection().compose(Tinv);
//	    reduce.setProjection(newProjection);
//		
//		AffineSystem syst;
//		if (dep.getContainerEquation() instanceof StandardEquation)
//			syst = ((StandardEquation)dep.getContainerEquation()).getContainerSystem();
//		else
//			syst = ((UseEquation)dep.getContainerEquation()).getContainerSystem();
//		
//		ExpressionDomainCalculator.computeExpressionDomain(syst);
//		ContextDomainCalculator.computeContextDomain(syst);
	}
	
}