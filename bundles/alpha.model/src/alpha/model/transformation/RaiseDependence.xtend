package alpha.model.transformation

import alpha.model.AlphaExpression
import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaVisitable
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryExpression
import alpha.model.CaseExpression
import alpha.model.ConstantExpression
import alpha.model.DependenceExpression
import alpha.model.IndexExpression
import alpha.model.MultiArgExpression
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.StandardEquation
import alpha.model.UnaryExpression
import alpha.model.VariableExpression
import alpha.model.util.AbstractAlphaCompleteVisitor
import org.eclipse.emf.ecore.util.EcoreUtil

import static alpha.model.factory.AlphaUserFactory.createStandardEquation
import static alpha.model.factory.AlphaUserFactory.createVariable
import static alpha.model.factory.AlphaUserFactory.createVariableExpression

import static extension alpha.model.factory.AlphaUserFactory.createDependenceExpression
import static extension alpha.model.factory.AlphaUserFactory.createIndexExpression
import static extension alpha.model.factory.AlphaUserFactory.createJNIDomain
import static extension alpha.model.factory.AlphaUserFactory.createJNIFunction
import static extension alpha.model.util.AffineFactorizer.factorizeExpressions
import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.AlphaUtil.getContainerSystem
import static extension alpha.model.util.AlphaUtil.getContainerSystemBody
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.CommonExtensions.toHashMap
import static extension fr.irisa.cairn.jnimap.isl.ISLMultiAff.buildIdentity
import static extension fr.irisa.cairn.jnimap.isl.ISLMultiAff.buildZero
import static extension fr.irisa.cairn.jnimap.isl.ISLSpace.idMapDimFromSetDim

/**
 * Raises up dependence functions through the AST of a given expression.
 * This is done by factorizing dependence functions and bringing the "common factor" terms
 * up through the AST as high as they can be.
 * Some of these rules can be thought of as the opposite of the Normalize rules.
 * 
 * One use case is to expose the maximum amount of reuse, enabling automatic reuse vector selection
 * for the Simlpifying Reductions optimization.
 * 
 * List of Rules:
 * Constant, Variable, and Index Expressions:
 *     These rules effectively just wrap these expressions in an appropriate dependence expression.
 *     Note: for constants and variables, this only applies if it isn't already inside a dependence expression.
 * 
 *     (X) goes to (f @ X) where X is a constant and f is a map from the context domain to a zero-dimensional range.
 *     (X) goes to (f @ X) where X is a variable and f is the identity function.
 *     val[f] goes to f@(val(i->i))
 * 
 * Dependence Expressions:
 *     This rule simply merges nested dependence expressions.
 * 
 *     (f1 @ f2 @ E) goes to (f @ E) where f=f1 @ f2
 * 
 * Restrict Expressions:
 *     This rule pulls a dependence expression outside of a restrict expression.
 * 
 *     D:(f@E) goes to f1@(D1: f2@E) where D=Preimage(D1,f1) and f=f1 @ f2
 * 
 * Auto-Restrict Expressions:
 *     The "LiftAutoRestrict" transformation is automatically applied
 *     whenever an Auto-Restrict Expression is found.
 *     This is done during "inCaseExpression", that way they're removed before navigating
 *     the children of the case expression it's in (as all auto-restricts must be inside a case). 
 * 
 * Unary Expressions:
 *     This rule simply pulls a dependence function out of the unary operation.
 * 
 *     op(f @ E) goes to f@(op E)
 * 
 * Binary, Multi-Arg, and Case Expressions:
 *     These rules require all children to be dependence expressions.
 *     They factorize the child functions, wrap the parent in a dependence expression of the common factor,
 *     and replace the child dependence expressions with the remaining terms.
 *     Each original function is thus the composition of the common factor and the correct remaining term.
 * 
 *     ((f1 @ E1) op (f2 @ E2)) goes to ((f')@((f1' @ E1) op (f2' @ E2))) where f1 = f' @ f1' and f2 = f' @ f2'
 *     op(f1@E1, f2@E2, ...) goes to (f')@ op(f1'@E1, f2'@E2, ...) where fn = f' @ fn'
 *     case {f1@E1, f2@E2, ...} goes to (f')@ case{f1'@E1, f2'@E2, ...} where fn = f' @ fn'
 * 
 * Reduce Expressions:
 *     The current implementation does not pull dependence expressions out of reduce expressions.
 *     However, the child of any raised dependence expressions in reduction bodies may be separated
 *     into a new variable. This is controlled by hoistFromReduce flag in apply. If hoistFromReduce
 *     is passed as true, then the following rule is applied.
 * 
 *     reduce(op, f, g@E) goes to reduce(op, f, g@V) where V is a new local variable defined as V=E
 */
class RaiseDependence extends AbstractAlphaCompleteVisitor {
	
	/** Dependence expressions raised in the body of a reduction may be hoisted into a  
	 *  separate equation. This flag controls when to do this. See outReduceExpression
	 *  and reduceExpressionRules.
	 */
	protected boolean hoistFromReduce = false
	
	/** Protected constructor to restrict access to the instance methods. */
	protected new() {}
	
	protected new(boolean hoistFromReduce) {
		this()
		this.hoistFromReduce = hoistFromReduce
	}
	
	/** Applies dependence raising to the AST of the given visitable expression. */
	static def apply(AlphaExpressionVisitable visitable) {
		apply(visitable, false)
	}
	
	static def apply(AlphaExpressionVisitable visitable, boolean hoistFromReduce) {
		new RaiseDependence(hoistFromReduce).accept(visitable)
	}
	
	/** Applies dependence raising to the AST of the given visitable object (system). */
	static def void apply(AlphaVisitable av) {
		apply(av, false)
	}
	
	static def void apply(AlphaVisitable av, boolean hoistFromReduce) {
		new RaiseDependence(hoistFromReduce).accept(av)
	}
	
	////////////////////////////////////////////////////////////
	// Constant, Variable, and Index Expression Rules
	////////////////////////////////////////////////////////////
	
	/**
	 * Wraps a constant expression in a dependence expression,
	 * assuming it's not already wrapped.
	 * 
	 * From:  X
	 * To:    f @ X
	 * Where: X is a constant, and f is a map from the context domain to a zero-dimensional range.
	 */
	override outConstantExpression(ConstantExpression ce) {
		// Only need to wrap the expression if it isn't inside a dependence function.
		if (ce.eContainer instanceof DependenceExpression) {
			return
		}
		
		// Wrap the current expression with a dependence expression of the empty function.
		// Note: the constant expression is only put inside the dependence after replacing it
		// in the AST, otherwise we get a stack overflow.
		val toEmpty = ce.contextDomain.space.buildZero.createDependenceExpression
		EcoreUtil.replace(ce, toEmpty)
		
		toEmpty.expr = ce
		AlphaInternalStateConstructor.recomputeContextDomain(toEmpty)
	}
	
	/**
	 * Wraps a variable expression in a dependence expression,
	 * assuming it's not already wrapped.
	 * 
	 * From:  X
	 * To:    f @ X
	 * Where: f is the identity function
	 */
	override outVariableExpression(VariableExpression ve) {
		// Only need to wrap the expression with an identity dependence function
		// if the expression isn't inside a dependence function.
		if (ve.eContainer instanceof DependenceExpression) {
			return
		}
		
		// Wrap the current expression with a dependence expression of the identity function.
		// Note: the constant expression is only put inside the dependence after replacing it
		// in the AST, otherwise we get a stack overflow.
		val identity = ve.contextDomain.space.idMapDimFromSetDim.buildIdentity.createDependenceExpression
		EcoreUtil.replace(ve, identity)
		
		identity.expr = ve
		AlphaInternalStateConstructor.recomputeContextDomain(identity)
	}
	
	/**
	 * Moves the affine map outside of an index ("val") expression.
	 * Since an index expression requires an affine function,
	 * the new function is just the identity.
	 * 
	 * From: val(f)
	 * To:   f @ (val(i->i))
	 */
	override outIndexExpression(IndexExpression ie) {
		// We want to replace the index expression's affine function with an identity function
		// and wrap the index expression with a dependence expression
		// containing the original affine function.
		val identityIndex = ie.function.space.range.idMapDimFromSetDim.buildIdentity.createIndexExpression
		val wrappingDependence = createDependenceExpression(ie.function, identityIndex)
		EcoreUtil.replace(ie, wrappingDependence)
		AlphaInternalStateConstructor.recomputeContextDomain(wrappingDependence)
	}
	
	
	////////////////////////////////////////////////////////////
	// Dependence Expression Rules
	////////////////////////////////////////////////////////////
	
	/** Applies the binary expression rules. */
	override outDependenceExpression(DependenceExpression de) {
		dependenceExpressionRule(de, de.expr)
	}
	
	/**
	 * Merge nested dependence expressions.
	 * 
	 * From:  f1 @ (f2 @ E)
	 * To:    f @ E
	 * Where: f = f1 @ f2
	 */
	protected def dispatch dependenceExpressionRule(DependenceExpression outerDe, DependenceExpression innerDe) {
		val f1 = outerDe.function
		val f2 = innerDe.function
		val f = f2.pullback(f1)
		
		val newDe = createDependenceExpression(f, innerDe.expr)
		EcoreUtil.replace(outerDe, newDe)
		AlphaInternalStateConstructor.recomputeContextDomain(newDe)
	}
	
	/** No matching dependence expression rule: do nothing. */
	protected def dispatch dependenceExpressionRule(DependenceExpression de, AlphaExpression inner) { }
	
	
	////////////////////////////////////////////////////////////
	// Restrict and Auto-Restrict Expression Rules
	////////////////////////////////////////////////////////////
	 
	 /** Applies the restrict expression rules. */
	 override outRestrictExpression(RestrictExpression re) {
	 	restrictExpressionRule(re, re.expr)
	 }
	 
	 /**
	  * Pulls a dependence expression outside of a restrict expression.
	  * 
	  * From:  D:(f@E)
	  * To:    f1@(D1: f2@E)
	  * Where: D=Preimage(D1,f1) and f=f1 @ f2
	  */
	 protected def dispatch restrictExpressionRule(RestrictExpression re, DependenceExpression de) {
		// Convert the restrict domain into a set of affine expressions representing the constraints.
		// For factorization with the dependence function, we need these as multi-affine expressions.
		val toFactorize = re.restrictDomain.basicSets
			.flatMap[it.constraints]
			.map[it.aff.toMultiAff]
			.toArrayList
		
		// Add the dependence expression to this list, then factorize everything.
		val dependenceFunction = de.function
		toFactorize.add(dependenceFunction)
		val factorizationResult = toFactorize.factorizeExpressions
		
		// Replace the dependence expression with the appropriate remaining term.
		val remainingDependence = factorizationResult.value.get(dependenceFunction)
		de.functionExpr = remainingDependence.createJNIFunction
		
		// Apply the common factor to the restrict domain.
		val commonFactor = factorizationResult.key
		val updatedDomain = re.restrictDomain.apply(commonFactor.copy.toMap)
		re.domainExpr = updatedDomain.createJNIDomain
		
		// Create a new dependence expression for the common factor
		// and wrap the restrict expression with it.
		val wrappingDependence = createDependenceExpression(commonFactor)
		EcoreUtil.replace(re, wrappingDependence)
		wrappingDependence.expr = re
		
		// Fix the context domains.
		AlphaInternalStateConstructor.recomputeContextDomain(wrappingDependence)
	 }
	 
	/** No matching restrict expression rule: do nothing. */
	 protected def dispatch restrictExpressionRule(RestrictExpression re, AlphaExpression expr) { }
	 
	 
	////////////////////////////////////////////////////////////
	// Unary Expression Rules
	////////////////////////////////////////////////////////////
	
	/** Applies the unary expression rules. */
	override outUnaryExpression(UnaryExpression ue) {
		unaryExpressionRule(ue, ue.expr)
	}
	
	/**
	 * Pull up the dependence expressions within a unary expression.
	 * 
	 * From:  op (f @ E)
	 * To:    f @ (op E)
	 */
	protected def dispatch unaryExpressionRule(UnaryExpression ue, DependenceExpression de) {
		// Replace the dependence expression with its child,
		// which moves the child up to be inside the unary expression. 
		EcoreUtil.replace(de, de.expr)
		
		// Replace the unary expression with the dependence expression,
		// then put the unary expression inside the dependence.
		EcoreUtil.replace(ue, de)
		de.expr = ue
		
		// Recompute the context domains.
		AlphaInternalStateConstructor.recomputeContextDomain(de)
	}
	
	/** No matching unary expression rule: do nothing. */
	protected def dispatch unaryExpressionRule(UnaryExpression ue, AlphaExpression expr) { }
	
	
	////////////////////////////////////////////////////////////
	// Binary, Multi-Arg, and Case Expressions
	////////////////////////////////////////////////////////////
	
	/** Applies the binary expression rules. */
	override outBinaryExpression(BinaryExpression be) {
		binaryExpressionRule(be, be.left, be.right)
	}
	
	/**
	 * Pull out a common factor from dependence expressions within a binary expression.
	 * 
	 * From:  f1 @ E1 op f2 @ E2
	 * To:    (f')@((f1' @ E1) op (f2' @ E2))
	 * Where: f1 = f' @ f1' and f2 = f' @ f2'
	 */
	protected def dispatch binaryExpressionRule(BinaryExpression be, DependenceExpression left, DependenceExpression right) {
		factorizeChildDependences(be, left, right)
	}
	
	/** No matching binary expression rule: do nothing. */
	protected def dispatch binaryExpressionRule(BinaryExpression be, AlphaExpression left, AlphaExpression right) { }
	
	
	/**
	 * Pull out a common factor from dependence expressions within a multi-arg expression.
	 * 
	 * From:  op (f1@E1, f2@E2, ...)
	 * To:    (f')@ op(f1'@E1, f2'@E2, ...)
	 * Where: fn = f' @ fn'
	 */
	override outMultiArgExpression(MultiArgExpression me) {
		// We only try to apply the rule if all the children are dependence expressions.
		val children = me.exprs
		if (children.forall[child | child instanceof DependenceExpression]) {
			factorizeChildDependences(me, children.map[child | child as DependenceExpression])
		}
	}
	
	/**
	 * Applies the <code>LiftAutoRestrict</code> transformation if needed.
	 * This is done when going into a case statement so that the Auto-Restrict Expressions
	 * are removed prior to visiting the children.
	 */
	override inCaseExpression(CaseExpression ce) {
		val hasAutoRestrict = ce.exprs.exists[child | child instanceof AutoRestrictExpression]
		if (hasAutoRestrict) {
			LiftAutoRestrict.apply(ce)
		}
	}
	
	/**
	 * Pull out a common factor from dependence expressions within a case expression.
	 * 
	 * From:  case {f1@E1, f2@E2, ...}
	 * To:    (f')@ case{f1'@E1, f2'@E2, ...}
	 * Where: fn = f' @ fn'
	 */
	override outCaseExpression(CaseExpression ce) {
		// We only try to apply the rule if all the children are dependence expressions.
		val children = ce.exprs
		if (children.forall[child | child instanceof DependenceExpression]) {
			factorizeChildDependences(ce, children.map[child | child as DependenceExpression])
		}
	}
	
	/**
	 * Factorizes a set of dependence expressions which are the direct children of some parent.
	 * 
	 * From:  parent(f1@E1, f2@E2, ...)
	 * To:    (f')@(parent(f1'@E1, f2'@E2, ...))
	 * Where: fn = f' @ fn'
	 */
	protected static def factorizeChildDependences(AlphaExpression parent, DependenceExpression... children) {
		// Create a mapping from each child's dependence function expression
		// to the ISL object that represents it.
		// This must be done first, as we need to use the dependence function objects
		// when determining what child gets what new expression.
		val dependenceExpressionToFunction = children
			.map[child | child.functionExpr]
			.toInvertedMap[expr | expr.ISLMultiAff]
			.toHashMap
			
		// Factorize the dependence functions and convert them to JNI function objects.
		val factorizationResult = dependenceExpressionToFunction.values.factorizeExpressions
		val commonFactor = factorizationResult.key
		val remainingTermsMap = factorizationResult.value.mapValues[multiAff | multiAff.createJNIFunction]
		
		// Replace each dependence expression with the remaining term.
		dependenceExpressionToFunction
			.mapValues[originalTerm | remainingTermsMap.get(originalTerm)]
			.forEach[original, replacement | EcoreUtil.replace(original, replacement)]
			
		// Make a new dependence function, replace the original parent with it,
		// then put the original parent inside the dependence function.
		// This order of operations prevents stack overflow exceptions.
		val wrappingDependence = createDependenceExpression(commonFactor)
		EcoreUtil.replace(parent, wrappingDependence)
		wrappingDependence.expr = parent
		
		// Finally, update the context domains for the entire subtree rooted at the wrapping dependence.
		AlphaInternalStateConstructor.recomputeContextDomain(wrappingDependence)
	}
	
	/**
	 * Separate the child of a top level dependence expression in the reduction body if hoisting
	 * is specified.
	 */
	override outReduceExpression(ReduceExpression re) {
		if (hoistFromReduce) {
			reduceExpressionRules(re, re.body)
		}
	}
	
	/**
	 * Pull out a common factor from dependence expressions within a case expression.
	 * 
	 * From:  reduce(op, f, g@E)
	 * To:    reduce(op, f, g@V)
	 * Where: V is a new local variable, V=E, defined as over the context domain of E
	 */
	def dispatch reduceExpressionRules(ReduceExpression re, DependenceExpression de) {
		// Add a new local variable V
		val domain = de.expr.contextDomain.computeDivs
		val varName = (re.getContainerEquation as StandardEquation).variable.name
		val variable = createVariable(varName + '_body', domain.copy)
		re.getContainerSystem.locals += variable
		
		// Add an equation for V=E
		val eq = createStandardEquation(variable, de.expr)
		re.getContainerSystemBody.equations += eq
		
		// Reference the variable in de.expr
		val ve = createVariableExpression(variable)
		de.expr = ve
		
		// Recompute context domain
		AlphaInternalStateConstructor.recomputeContextDomain(re)
	}
	def dispatch reduceExpressionRules(ReduceExpression re, AlphaExpression ae) {
		// do nothing
	}
}