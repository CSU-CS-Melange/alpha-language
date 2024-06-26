package alpha.codegen.writeC

import alpha.codegen.BaseDataType
import alpha.codegen.Expression
import alpha.codegen.Factory
import alpha.codegen.ProgramBuilder
import alpha.codegen.TernaryExprBuilder
import alpha.codegen.isl.ConditionalConverter
import alpha.codegen.isl.PolynomialConverter
import alpha.model.AlphaExpression
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryExpression
import alpha.model.CaseExpression
import alpha.model.ConstantExpression
import alpha.model.DependenceExpression
import alpha.model.ExternalArgReduceExpression
import alpha.model.ExternalFuzzyArgReduceExpression
import alpha.model.ExternalFuzzyReduceExpression
import alpha.model.ExternalMultiArgExpression
import alpha.model.ExternalReduceExpression
import alpha.model.IfExpression
import alpha.model.IndexExpression
import alpha.model.MultiArgExpression
import alpha.model.PolynomialIndexExpression
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.UnaryExpression
import alpha.model.VariableExpression
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT

import static extension alpha.model.util.CommonExtensions.toArrayList

/**
 * A collection of methods that convert Alpha expressions to C expression AST nodes.
 * The C expression node that represents the Alpha expression will be returned
 * so it can be added to the AST appropriately.
 * 
 * The conversion of some expressions requires adding new elements to the program,
 * thus requiring a program builder to be supplied.
 * For example, reduce expressions need to add new functions for computing the reduction,
 * then returns a call expression to that function.  
 */
class ExprConverter {
	/** The data type to use for Alpha values. */
	protected val BaseDataType alphaValueType
	
	/** The converter to use for reduce expressions. */
	protected val ReduceExprConverter reduceExprConverter
	
	/** The converter to use for dependence expressions. */
	protected val DependenceExprConverter dependenceExprConverter
	
	/** Constructs a new converter for reduce expressions. */
	new(BaseDataType alphaValueType) {
		this.alphaValueType = alphaValueType
		this.reduceExprConverter = new ReduceExprConverter(alphaValueType, this)
		this.dependenceExprConverter = new DependenceExprConverter(this)
	}
	
	
	///////////////////////////////////////////////
	// External Functions are Not Allowed Yet
	///////////////////////////////////////////////
	
	def protected static Expression externalNotSupported() {
		throw new Exception("Expressions that use external functions are not currently supported.")
	}
	
	def dispatch Expression convertExpr(ProgramBuilder program, ExternalReduceExpression expr) { externalNotSupported }
	def dispatch Expression convertExpr(ProgramBuilder program, ExternalArgReduceExpression expr) { externalNotSupported }
	def dispatch Expression convertExpr(ProgramBuilder program, ExternalMultiArgExpression expr) { externalNotSupported }
	def dispatch Expression convertExpr(ProgramBuilder program, ExternalFuzzyReduceExpression expr) { externalNotSupported }
	def dispatch Expression convertExpr(ProgramBuilder program, ExternalFuzzyArgReduceExpression expr) { externalNotSupported }
	
	
	///////////////////////////////////////////////
	// Actual Rules
	///////////////////////////////////////////////
	
	/**
	 * Alpha "restrict" expressions don't need to be wrapped in conditionals,
	 * as the context domain within Alpha ensures the expression is only accessed where appropriate.
	 * For restrict expressions inside "case" or "reduce" expressions,
	 * the conditional checking should be handled by the conversion of the "case" or "reduce.  
	 */
	def dispatch Expression convertExpr(ProgramBuilder program, RestrictExpression re) {
		program.convertExpr(re.expr)
	}
	
	/**
	 * Alpha "auto-restrict" expressions don't need to be wrapped in conditionals,
	 * as the context domain within Alpha ensures the expression is only accessed where appropriate.
	 * For auto-restrict expressions inside "case" or "reduce" expressions,
	 * the conditional checking should be handled by the conversion of the "case" or "reduce.  
	 */
	def dispatch Expression convertExpr(ProgramBuilder program, AutoRestrictExpression re) {
		program.convertExpr(re.expr)
	}
	
	/** Converts an Alpha "case" expression into a C ternary expression. */
	def dispatch Expression convertExpr(ProgramBuilder program, CaseExpression ce) {
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
			return program.convertExpr(ce.exprs.get(0))
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
		val builder = TernaryExprBuilder.start(firstCase.createConditional, program.convertExpr(firstCase))

		// The "middle" cases (not first or last) are all handled the same.
		remainingCases.tail.forEach[builder.addCase(it.createConditional, program.convertExpr(it))]
		
		// For the "last" case, we don't need a conditional.
		// AlphaZ has already ensured that all cases were covered.
		return builder.elseCase(program.convertExpr(lastCase))
	}
	
	/** Creates a conditional expression to ensure a restrict case is being respected. */
	def static dispatch Expression createConditional(RestrictExpression re) {
		return ConditionalConverter.convert(re.restrictDomain)
	}
	
	/**
	 * Creates a conditional expression to ensure this expression (which is neither a restrict
	 * nor an auto-restrict) is only evaluated within its context domain.
	 */
	def static dispatch Expression createConditional(AlphaExpression expr) {
		return ConditionalConverter.convert(expr.contextDomain)
	}
	
	/** Dependence expression conversion is handled by a separate class. */
	def dispatch Expression convertExpr(ProgramBuilder program, DependenceExpression expr) {
		return dependenceExprConverter.convertExpr(program, expr)
	}
	
	def dispatch Expression convertExpr(ProgramBuilder program, IfExpression expr) {
		val conditional = program.convertExpr(expr.condExpr)
		val thenExpr = program.convertExpr(expr.thenExpr)
		val elseExpr = program.convertExpr(expr.elseExpr)
		return Factory.ternaryExpr(conditional, thenExpr, elseExpr)
	}
	
	/**
	 * Index expressions in Alpha convert indices into values.
	 * Thus, we just need to output the expression itself.
	 */
	def dispatch Expression convertExpr(ProgramBuilder program, IndexExpression ie) {
		// The ISLMultiAff for an index expression should only contain a single ISLAff
		// since there is only one output dimension
		val exprLiteral = ISLAff._toString(ie.function.getAff(0), ISL_FORMAT.C.ordinal())
		return Factory.customExpr(exprLiteral)
	}
	
	def dispatch Expression convertExpr(ProgramBuilder program, PolynomialIndexExpression expr) {
		return PolynomialConverter.convert(expr.polynomial)
	}
	
	/** Reduce expression conversion is handled by a separate class. */
	def dispatch Expression convertExpr(ProgramBuilder program, ReduceExpression expr) {
		return reduceExprConverter.convertExpr(program, expr)
	}
	
	/**
	 * Translates a variable expression into a variable access.
	 * Note: variable expressions inside dependence expressions are handled
	 * by the dependence expression converter, not here.
	 * If this is reached, then the variable is implicitly being accessed by the identity function.
	 */
	def dispatch Expression convertExpr(ProgramBuilder program, VariableExpression expr) {
		// Emit a call to this variable's indexing function/macro
		// using the indices themselves as the arguments (i.e., the identity function).
		val indexExprs = expr.contextDomain.indexNames.map[Factory.customExpr(it)]
		
		// If the variable is an input, we can directly use the memory macro.
		// Otherwise, we access it through its "eval" function
		// to ensure the point has been evaluated.
		if (expr.variable.isInput) {
			return Factory.callExpr(expr.variable.name, indexExprs)
		} else {
			val accessFunction = Common.getEvalName(expr.variable)
			return Factory.callExpr(accessFunction, indexExprs)	
		}
	}
	
	/** Constants in Alpha simply map to the same constant in C. */
	def dispatch Expression convertExpr(ProgramBuilder program, ConstantExpression ce) {
		return Factory.customExpr(ce.valueString)
	}
	
	/** There is a 1-to-1 matching between Alpha and C unary expressions. */
	def dispatch Expression convertExpr(ProgramBuilder program, UnaryExpression ue) {
		val op = Common.getOperator(ue.operator)
		val expr = program.convertExpr(ue.expr)
		return Factory.unaryExpr(op, expr)
	}
	
	def dispatch Expression convertExpr(ProgramBuilder program, BinaryExpression be) {
		val left = program.convertExpr(be.left)
		val right = program.convertExpr(be.right)
		val op = Common.getOperator(be.operator)
		return Factory.binaryExpr(op, left, right)
	}
	
	/** Multi-arg expressions are converted into a tree of nested binary expressions. */
	def dispatch Expression convertExpr(ProgramBuilder program, MultiArgExpression expr) {
		val op = Common.getOperator(expr.operator)
		val children = expr.exprs.map[program.convertExpr(it)]
		return Factory.binaryExprTree(op, children)
	}
	
	/** Default case to catch unknown expression types. */
	def dispatch Expression convertExpr(ProgramBuilder program, AlphaExpression expr) {
		throw new Exception("Not implemented yet!")
	}
}