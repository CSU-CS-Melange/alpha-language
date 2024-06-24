package alpha.codegen.alphaBase

import alpha.codegen.Expression
import alpha.codegen.Factory
import alpha.codegen.TernaryExprBuilder
import alpha.codegen.isl.AffineConverter
import alpha.codegen.isl.ConditionalConverter
import alpha.codegen.isl.PolynomialConverter
import alpha.model.AlphaExpression
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryExpression
import alpha.model.CaseExpression
import alpha.model.ConstantExpression
import alpha.model.DependenceExpression
import alpha.model.IfExpression
import alpha.model.IndexExpression
import alpha.model.MultiArgExpression
import alpha.model.PolynomialIndexExpression
import alpha.model.RestrictExpression
import alpha.model.UnaryExpression
import alpha.model.VariableExpression
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT

import static extension alpha.model.util.CommonExtensions.toArrayList

/**
 * Converts Alpha expressions to simpleC expressions.
 * Not all Alpha expressions are currently handled and will throw an exception if encountered.
 * Most notably, this includes reduce expressions.
 */
class ExprConverter {
	/** Generates the simpleC data types for various Alpha structures. */
	protected val TypeGeneratorBase typeGenerator
	
	/** The name checker to use for retrieving variable/parameter names from. */
	protected val AlphaNameChecker nameChecker
	
	/** Constructs a new converter for expressions. */
	new(TypeGeneratorBase typeGenerator, AlphaNameChecker nameChecker) {
		this.typeGenerator = typeGenerator
		this.nameChecker = nameChecker
	}
	
	
	/////////////////////////////////////////////////////////////////
	// Variable and Dependence Expressions
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Translates a variable expression into a variable access.
	 * Note: variable expressions inside dependence expressions are handled
	 * by the dependence expression converter, not here.
	 * If this is reached, then the variable is implicitly being accessed by the identity function.
	 */
	def dispatch Expression convertExpr(VariableExpression expr) {
		// Emit a call to this variable's indexing function/macro
		// using the indices themselves as the arguments (i.e., the identity function).
		val name = nameChecker.getVariableReadName(expr.variable)
		val indexExprs = expr.contextDomain.indexNames.map[Factory.customExpr(it)]
		return Factory.callExpr(name, indexExprs)
	}
	
	/** Converts dependence expressions into simpleC expressions. */
	def dispatch Expression convertExpr(DependenceExpression expr) {
		convertDependence(expr, expr.expr)
	}
	
	/** If the child is a constant, then simply emit that constant. */
	def dispatch convertDependence(DependenceExpression parent, ConstantExpression child) {
		convertExpr(child)
	}
	
	/** If the child is a variable, read the value indexed by the dependence function. */
	def dispatch convertDependence(DependenceExpression parent, VariableExpression child) {
		// We read the variable by calling a function of the variable's "read name"
		// with the indexing expression indicated by the dependence.
		val name = nameChecker.getVariableReadName(child.variable)
		val indexExprs = AffineConverter.convertMultiAff(parent.function)
		return Factory.callExpr(name, indexExprs)
	}
	
	/** Default case for child expressions that aren't supported yet. */
	def dispatch convertDependence(DependenceExpression parent, AlphaExpression child) {
		throw new Exception("Not implemented yet!")
	}
	
	
	///////////////////////////////////////////////
	// Restrict, Case, and If Expression Rules
	///////////////////////////////////////////////
	
	/**
	 * Alpha "restrict" expressions don't need to be wrapped in conditionals,
	 * as the context domain within Alpha ensures the expression is only accessed where appropriate.
	 * For restrict expressions inside "case" or "reduce" expressions,
	 * the conditional checking should be handled by the conversion of the "case" or "reduce.  
	 */
	def dispatch Expression convertExpr(RestrictExpression re) {
		re.expr.convertExpr
	}
	
	/**
	 * Alpha "auto-restrict" expressions don't need to be wrapped in conditionals,
	 * as the context domain within Alpha ensures the expression is only accessed where appropriate.
	 * For auto-restrict expressions inside "case" or "reduce" expressions,
	 * the conditional checking should be handled by the conversion of the "case" or "reduce.  
	 */
	def dispatch Expression convertExpr(AutoRestrictExpression re) {
		re.expr.convertExpr
	}
	
	/** Converts an Alpha "case" expression into a C ternary expression. */
	def dispatch Expression convertExpr(CaseExpression ce) {
		// Case expressions with no cases, or with multiple auto-restricts, are invalid.
		if (ce.exprs.size <= 0) {
			throw new IllegalArgumentException("Alpha case expression found with no cases.")
		}
		val autoRestricts = ce.exprs.filter(typeof(AutoRestrictExpression)).toArrayList
		if (autoRestricts.size > 1) {
			throw new IllegalArgumentException("Alpha case expression found with more than one auto-restrict case.")
		}
		
		// If there's only one case, then there are no conditions we need to check,
		// so simply output whatever that case is.
		if (ce.exprs.size == 1) {
			return ce.exprs.get(0).convertExpr
		}
		
		// We need to build a ternary expression with conditionals for all remaining expressions.
		// The first and last expressions are handled separately.
		// The first is needed for creating the ternary expression builder,
		// and the last one is needed for getting the final expression out.
		
		// The "last" case is either the auto-restrict case (if there is one),
		// or whatever happens to be last in the list of cases.
		val lastCase = autoRestricts.size == 1 ? autoRestricts.get(0) : ce.exprs.last
		val remainingCases = ce.exprs.reject[it === lastCase]
		
		// The "first" case is whatever remaining case happens to be first.
		val firstCase = remainingCases.head
		val builder = TernaryExprBuilder.start(firstCase.createConditional, convertExpr(firstCase))

		// The "middle" cases (not first or last) are all handled the same.
		remainingCases.tail.forEach[builder.addCase(it.createConditional, convertExpr(it))]
		
		// For the "last" case, we don't need a conditional.
		// AlphaZ has already ensured that all cases were covered.
		return builder.elseCase(lastCase.convertExpr)
	}
	
	/** Creates a conditional expression to ensure a restrict case is being respected. */
	def dispatch Expression createConditional(RestrictExpression re) {
		return ConditionalConverter.convert(re.restrictDomain)
	}
	
	/**
	 * Creates a conditional expression to ensure this expression (which is neither a restrict
	 * nor an auto-restrict) is only evaluated within its context domain.
	 */
	def dispatch Expression createConditional(AlphaExpression expr) {
		return ConditionalConverter.convert(expr.contextDomain)
	}
	
	def dispatch Expression convertExpr(IfExpression expr) {
		val conditional = convertExpr(expr.condExpr)
		val thenExpr = expr.thenExpr.convertExpr
		val elseExpr = expr.elseExpr.convertExpr
		return Factory.ternaryExpr(conditional, thenExpr, elseExpr)
	}
	
	
	///////////////////////////////////////////////
	// Index and Constant Expression Rules
	///////////////////////////////////////////////
	
	/**
	 * Index expressions in Alpha convert indices into values.
	 * Thus, we just need to output the expression itself.
	 */
	def dispatch Expression convertExpr(IndexExpression ie) {
		// The ISLMultiAff for an index expression should only contain a single ISLAff
		// since there is only one output dimension
		val exprLiteral = ISLAff._toString(ie.function.getAff(0), ISL_FORMAT.C.ordinal())
		return Factory.customExpr(exprLiteral)
	}
	
	def dispatch Expression convertExpr(PolynomialIndexExpression expr) {
		return PolynomialConverter.convert(expr.polynomial)
	}
	
	/** Constants in Alpha simply map to the same constant in C. */
	def dispatch Expression convertExpr(ConstantExpression ce) {
		return Factory.customExpr(ce.valueString)
	}
	
	
	///////////////////////////////////////////////
	// Operator Expression Rules
	///////////////////////////////////////////////
	
	/** There is a 1-to-1 matching between Alpha and C unary expressions. */
	def dispatch Expression convertExpr(UnaryExpression ue) {
		val op = AlphaBaseHelpers.getOperator(ue.operator)
		val expr = convertExpr(ue.expr)
		return Factory.unaryExpr(op, expr)
	}
	
	def dispatch Expression convertExpr(BinaryExpression be) {
		val left = convertExpr(be.left)
		val right = convertExpr(be.right)
		val op = AlphaBaseHelpers.getOperator(be.operator)
		return Factory.binaryExpr(op, left, right)
	}
	
	/** Multi-arg expressions are converted into a tree of nested binary expressions. */
	def dispatch Expression convertExpr(MultiArgExpression expr) {
		val op = AlphaBaseHelpers.getOperator(expr.operator)
		val children = expr.exprs.map[convertExpr]
		return Factory.binaryExprTree(op, children)
	}
	
	
	///////////////////////////////////////////////
	// Missing Rules
	///////////////////////////////////////////////
	
	/** Default case to catch unknown expression types. */
	def dispatch Expression convertExpr(AlphaExpression expr) {
		throw new Exception("Not implemented yet!")
	}
}
