package alpha.model

import alpha.model.issue.AlphaIssue
import alpha.model.issue.AlphaIssueFactory
import alpha.model.issue.UnexpectedISLErrorIssue
import alpha.model.util.AbstractAlphaExpressionVisitor
import alpha.model.util.AlphaExpressionUtil
import java.util.LinkedList
import java.util.List
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject

import static alpha.model.util.AlphaUtil.callISLwithErrorHandling
import static alpha.model.util.AlphaExpressionUtil.parentContext
import fr.irisa.cairn.jnimap.isl.ISLSet

/**
 * Computes the context domain for AlphaExpressions. The context domains
 * define the set where the given expression needs to be evaluated to 
 * compute all the outputs. It is computed top-down starting from the 
 * root (equations) after computing all the expression domains.
 *
 * The context domain is essentially the intersection of the expression domain
 * with the context domain of its parent. The parent context may undergo some
 * transformations depending on the expression, where the notable ones are:
 *   - DependenceExpression takes the image of its parent context by the dependence function
 *   - ReduceExpression takes the pre-image of its parent context by the projection function
 * 
 * 
 */
class ContextDomainCalculator extends AbstractAlphaExpressionVisitor {

	List<AlphaIssue> issues = new LinkedList

	static def dispatch List<AlphaIssue> calculate(AlphaVisitable node) {
		val calc = new ContextDomainCalculator();

		calc.visit(node)
		
		return calc.issues;
	}

	static def dispatch List<AlphaIssue> calculate(AlphaExpressionVisitable expr) {
		val calc = new ContextDomainCalculator();

		expr.accept(calc);

		return calc.issues;
	}

	private def void registerIssue(String errMsg, AlphaNode node) {
		issues.add(new UnexpectedISLErrorIssue(errMsg, node, null));
	}
	
	private def void registerIssue(AlphaIssue issue) {
		issues.add(issue);
	}
	
	
	override inAutoRestrictExpression(AutoRestrictExpression are) {
		if (are.eContainer === null) throw new RuntimeException("Uncontained AlphaExpression");
		if (are.expressionDomain === null) return;
		
		if (!(are.eContainer instanceof CaseExpression)) {
			//throw new RuntimeException("AutoRestrict can only be direct child of CaseExpression.");
			issues.add(AlphaIssueFactory.autoRestrictNotInCase(are));
			return;
		}
		
		val parentCase = are.eContainer as CaseExpression;
		if (AlphaExpressionUtil.getChildrenOfType(parentCase, AutoRestrictExpression).count > 1) {
			issues.add(AlphaIssueFactory.multipleAutoRestrict(are));
			return;
		}
		
		if (!AlphaExpressionUtil.testNonNullExpressionDomain(parentCase.exprs.stream)) {
			return;
		}
//		AlphaExpressionUtil.pa
		val parentContext = parentContext(are, parentCase, [i|registerIssue(i)]);
		if (parentContext === null) return;

		var ISLSet inferredDomain
		
		if (parentCase.exprs.length == 1) {
			inferredDomain = parentContext.intersect(are.expressionDomain)
		} else {
			val otherExprDomain = parentCase.exprs.filter[e|!(e instanceof AutoRestrictExpression)].map[expressionDomain].reduce[p1, p2|p1.union(p2)]
			inferredDomain = parentContext.subtract(otherExprDomain).intersect(are.expressionDomain)
		}
		
		if (inferredDomain.isEmpty) {
			issues.add(AlphaIssueFactory.emptyAutoRestrict(are));
		}
		
		are.inferredDomain =  inferredDomain;
		are.contextDomain = inferredDomain.copy.computeDivs;
	}

	override inAlphaExpression(AlphaExpression ae) {
		if (ae.eContainer === null) throw new RuntimeException("Uncontained AlphaExpression");
		if (ae.expressionDomain === null) return;

		val parentContext = parentContext(ae, ae.eContainer as AlphaCompleteVisitable, [i|registerIssue(i)]);

		if (parentContext === null) {
			// If parent context domain is not defined, then do not report this as an issue, 
			//  but gracefully continue without computing context domain
			// Only the source of the error should report it. 
			return;
		}
		
		val processedContext = ae.eContainer.processContext(parentContext)
		
		if (processedContext === null) return;
		if (!processedContext.space.isEqual(ae.expressionDomain.space)) {
			issues.add(AlphaIssueFactory.incompatibleContextAndExpressionDomain(ae));
			return;
		}
		
		val context = runISLoperations(ae, [processedContext.intersect(ae.expressionDomain)]);
		ae.contextDomain = context.computeDivs
	}

	
	private dispatch def processContext(DependenceExpression expr, ISLSet context) {
		return runISLoperations(expr, [context.apply(expr.function.toMap)]);
	}
	
	private dispatch def processContext(SelectExpression expr, ISLSet context) {
		return runISLoperations(expr, [context.apply(expr.selectRelation)]);
	}

	private dispatch def processContext(ReduceExpression expr, ISLSet context) {
		return runISLoperations(expr, [context.preimage(expr.projection)]);
	}

	private dispatch def processContext(ArgReduceExpression expr, ISLSet context) {
		return runISLoperations(expr, [context.preimage(expr.projection)]);
	}
	
	private dispatch def processContext(ConvolutionExpression expr, ISLSet context) {
		return runISLoperations(expr, [context.flatProduct(expr.kernelDomain)]);
	}

	// default is not do anything
	private dispatch def processContext(AlphaNode expr, ISLSet context) {
		return context
	}
	private dispatch def processContext(EObject expr, ISLSet context) {
		return null;
	}
	
	
	private def <T> T runISLoperations(AlphaExpression expr, Supplier<T> r) {
		if (AlphaExpressionUtil.testNonNullExpressionDomain(AlphaExpressionUtil.getChildrenOfType(expr, AlphaExpression))) 
				return callISLwithErrorHandling(r, [err|registerIssue(err, expr)]);
	}
}
