package alpha.model.transformation.reduction

import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaVisitable
import alpha.model.Equation
import alpha.model.ReduceExpression
import alpha.model.util.AbstractAlphaCompleteVisitor
import java.util.ArrayList

/**
 * Applies NormalizeReduction to all reduce expressions within the tree.
 * Ensures that even nested reductions are moved to their own equations.
 */
class NormalizeReductionDeep extends AbstractAlphaCompleteVisitor {
	/**
	 * The list of reduce expressions to call NormalizeReduction on.
	 * Constructing a list and then applying NormalizeReduction after walking the AST
	 * prevents concurrent modification exceptions.
	 */
	val ArrayList<ReduceExpression> targetREs = newArrayList
	
	/**
	 * Applies NormalizeReduction to all descendants of an equation, system body, system, etc.
	 */
	static def apply(AlphaVisitable av) {
		val visitor = new NormalizeReductionDeep
		visitor.accept(av)
		visitor.normalizeTargets
	}
	
	/**
	 * Applies NormalizeReduction to all descendants of an expression.
	 */
	static def apply(AlphaExpressionVisitable ave) {
		val visitor = new NormalizeReductionDeep
		visitor.accept(ave)
		visitor.normalizeTargets
	}
	
	/**
	 * Normalizes all of the target reduce expressions.
	 * Calling this after walking the AST prevents concurrent modification exceptions.
	 */
	protected def normalizeTargets() {
		targetREs.forEach[re | NormalizeReduction.apply(re)]
	}
	
	/**
	 * If the reduce expression isn't already the top-level expression
	 * of an equation, add it to the list of reductions to normalize.
	 */
	override outReduceExpression(ReduceExpression re) {
		// No need to create a new equation for the reduction if it's already
		// the top-level expression of an equation.
		if (re.eContainer instanceof Equation) {
			return
		}
		targetREs += re
	}
}