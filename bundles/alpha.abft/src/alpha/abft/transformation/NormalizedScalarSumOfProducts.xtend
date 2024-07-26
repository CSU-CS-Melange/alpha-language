package alpha.abft.transformation

import alpha.model.AlphaExpression
import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaVisitable
import alpha.model.BINARY_OP
import alpha.model.BinaryExpression
import alpha.model.MultiArgExpression
import alpha.model.REDUCTION_OP
import alpha.model.util.AShow
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.util.ISLUtil
import org.eclipse.emf.ecore.util.EcoreUtil

import static alpha.model.factory.AlphaUserFactory.createDependenceExpression
import static alpha.model.factory.AlphaUserFactory.createIdentityProdExpression
import static alpha.model.factory.AlphaUserFactory.createMultiArgExpression
import static alpha.model.factory.AlphaUserFactory.createNegatedExpression
import static alpha.model.factory.AlphaUserFactory.createStandardEquation
import static alpha.model.factory.AlphaUserFactory.createVariable
import static alpha.model.factory.AlphaUserFactory.createVariableExpression

import static extension alpha.model.analysis.reduction.ShareSpaceAnalysis.isConstantInContext
import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.AlphaUtil.getContainerRoot
import static extension alpha.model.util.AlphaUtil.getContainerSystemBody
import static extension alpha.model.util.AlphaUtil.isEquivalent
import static extension alpha.model.util.AlphaUtil.createConstantExprDomain

/**
 * This class attempts to identify the presence of scalar sums of products
 * and put them into the following normal form as a MultiArgExpression (mae):
 * 
 * sum(c1[]*(f1@X), c2[]*(f2@X), c3[]*(f3@X), ...)
 * 
 * where each expr in the mae is a binary expression with a constant on the
 * left-hand side and a dependence expression on the right-hand side. Also, 
 * each dependence expression must be a uniform dependence.
 * 
 * For example, the following expression:
 * 
 *   x[i] + 2*x[i+1] + 0.5*x[i] - x[i-2]
 * 
 * is transformed into:
 * 
 *   sum(-1*x[i-2], (1+0.5)*x[i], 2*x[i+1])
 * 
 */

class NormalizedScalarSumOfProducts extends AbstractAlphaCompleteVisitor {
	
	static boolean DEBUG = true
	static int constantCount = 0
	
	
	static def void debug(String msg) {
		println(msg)
	}
	
	
	static def void apply(AlphaVisitable av) {
		val visitor = new NormalizedScalarSumOfProducts
		
		while (av.eAllContents.exists[e | e instanceof BinaryExpression]) {
			av.accept(visitor)		
		}
	}
	static def void apply(AlphaExpressionVisitable aev) {
		val visitor = new NormalizedScalarSumOfProducts
		aev.accept(visitor)
	}
	
	
	
	
	override outBinaryExpression(BinaryExpression be) {
		switch (be.operator) {
			case BINARY_OP.ADD,
			case BINARY_OP.SUB : be.toSumMultiArg
			case BINARY_OP.MUL : be.toProdMultiArg
			default: {}
		}
	}
	
	def static toSumMultiArg(BinaryExpression be) {
		val mae = createMultiArgExpression(REDUCTION_OP.SUM)
		
		mae.exprs += be.left.copyAE
		
		val rhs = be.right.copyAE
		if (be.operator == BINARY_OP.SUB) {
			mae.exprs += createNegatedExpression(rhs)
		} else {
			mae.exprs += rhs
		}
		
		val dbg = AShow.print(be)
		
		EcoreUtil.replace(be, mae)
		AlphaInternalStateConstructor.recomputeContextDomain(mae)
		
		debug(dbg + ' -> ' + AShow.print(mae))
	}
	
	def toProdMultiArg(BinaryExpression be) {
		val mae = createMultiArgExpression(REDUCTION_OP.PROD)
		
		mae.addExprs(be, be.left)
		mae.addExprs(be, be.right)
		
		val dbg = AShow.print(be)
		
		EcoreUtil.replace(be, mae)
		AlphaInternalStateConstructor.recomputeContextDomain(mae)
		
		debug(dbg + ' -> ' + AShow.print(mae))
	}
	
	def dispatch addExprs(MultiArgExpression newMae, BinaryExpression be, MultiArgExpression child) {
		if (be.operator.isEquivalent(child.operator)) {
			newMae.exprs.addAll(child.exprs.map[copyAE])
		} else {
			newMae.exprs += child.copyAE
		}
	}
	def dispatch addExprs(MultiArgExpression newMae, BinaryExpression be, AlphaExpression child) {
		newMae.exprs += child.copyAE
	}
	
	private def static isMultiArgWith(AlphaExpression ae, REDUCTION_OP op) {
		ae instanceof MultiArgExpression && (ae as MultiArgExpression).operator == op
	}
	
	override outMultiArgExpression(MultiArgExpression mae) {
		
		val system = mae.getContainerSystemBody.system
		
		val exprs = mae.exprs.reject[e | e.isMultiArgWith(mae.operator)].map[e | e.copyAE].toList

		mae.exprs.filter[e | e.isMultiArgWith(mae.operator)]
			.map[e | e.copyAE as MultiArgExpression]
			.forEach[nestedMae | exprs.addAll(nestedMae.exprs)]
		
		val newMae = createMultiArgExpression(mae.operator)
		newMae.exprs += exprs
		
		EcoreUtil.replace(mae, newMae)
		AlphaInternalStateConstructor.recomputeContextDomain(newMae)

		// Return if mae is not a summation		
		if (newMae.operator != REDUCTION_OP.SUM) return;
		
		// Replace any remaining non-prod maes with the identity prod(1[], e)
		newMae.exprs
			.reject[e | e.isMultiArgWith(REDUCTION_OP.PROD)]
			.forEach[e | EcoreUtil.replace(e, createIdentityProdExpression(e.copyAE))]
		AlphaInternalStateConstructor.recomputeContextDomain(newMae)
		
		// All expressions should be multiArgs at this point
		newMae.exprs.reject[e | e instanceof MultiArgExpression]
			.forEach[throw new Exception('There is an unexpected alpha expression.')]
		val maes = newMae.exprs.map[e | e as MultiArgExpression]
		
		// Combine any constant expressions
		println(AShow.print(system))
		println
		maes.forEach[e | flattenConstantExprs(e)]
		
		println
//		
//		// Factor any common terms
//		newMae.factorCommonTerms
		
		 
	}
	
	/**
	 * assumes that 
	 */
	private def static factorCommonTerms(MultiArgExpression mae) {
		
		
	}
	
	
	/** 
	 * Hoists any constant terms within the mae into their own variable, then replaces
	 * the constant terms in the mae with this new variable.
	 * 
	 * For example, given:
	 * 
	 *   prod(e1, e2, e3, ...)
	 * 
	 * and suppose that e1 and e2 are the only constant exprs. The following new local
	 * variable will be introduced:
	 * 
	 *   locals:
	 *     _cX = {}
	 *   ...
	 *     _cX = prod(e1, e2)
	 * 
	 * and the input mae will be transformed into the following:
	 * 
	 *   prod(_cX[], e3, ...)
	 */
	private def static flattenConstantExprs(MultiArgExpression mae) {
		val constExprs = mae.exprs.filter[constantInContext].toSet
		if (constExprs.size == 1) return;
		
		val otherExprs = mae.exprs.reject[e | constExprs.contains(e)].toList
		
		val systemBody = mae.getContainerSystemBody
		
		// Create new variable and equation for the combined constant
		val variable = createVariable('_c' + constantCount, createConstantExprDomain(mae.contextDomain.space))
		constantCount += 1
		val constMae = createMultiArgExpression(mae.operator)
		constMae.exprs += constExprs.map[copyAE]
		systemBody.system.locals += variable
		val eq = createStandardEquation(variable, constMae)
		systemBody.equations += eq
		AlphaInternalStateConstructor.recomputeContextDomain(eq)

		// Create a new multiArg expression that references the newly created constant
		val depExpr = createDependenceExpression(ISLUtil.createConstantMaff(mae.contextDomain.space))
		depExpr.expr = createVariableExpression(variable)
		val newMae = createMultiArgExpression(mae.operator)
		newMae.exprs += depExpr
		newMae.exprs += otherExprs
		
//		EcoreUtil.replace(mae, newMae)
//		AlphaInternalStateConstructor.compute(newMae.getContainerRoot)	
	}
	
	
	
}