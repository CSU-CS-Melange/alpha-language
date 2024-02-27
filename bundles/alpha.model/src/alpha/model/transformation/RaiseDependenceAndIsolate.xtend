package alpha.model.transformation

import alpha.model.AlphaExpression
import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaVisitable
import alpha.model.DependenceExpression
import alpha.model.ReduceExpression
import alpha.model.StandardEquation

import static alpha.model.factory.AlphaUserFactory.*

import static extension alpha.model.util.AlphaUtil.*

class RaiseDependenceAndIsolate extends RaiseDependence {
	
	static def apply(AlphaExpressionVisitable visitable) {
		new RaiseDependenceAndIsolate().accept(visitable)
	}
	
	static def void apply(AlphaVisitable av) {
		new RaiseDependenceAndIsolate().accept(av)
	}
	
	override outReduceExpression(ReduceExpression re) {
		reduceExpressionRules(re, re.body)
	}
	
	def dispatch reduceExpressionRules(ReduceExpression re, DependenceExpression de) {
		val domain = de.expr.contextDomain.computeDivs
		val varName = (re.getContainerEquation as StandardEquation).variable.name
		
		// add new variable
		val variable = createVariable(varName + '_body', domain.copy)
		re.getContainerSystem.locals += variable
		
		// add an equation for it
		val eq = createStandardEquation(variable, de.expr)
		re.getContainerSystemBody.equations += eq
		
		// reference the variable in de.expr
		val ve = createVariableExpression(variable)
		//EcoreUtil.replace(de.expr, ve)
		de.expr = ve
		
		// recompute context domain
		AlphaInternalStateConstructor.recomputeContextDomain(re.getContainerSystemBody)
	}
	def dispatch reduceExpressionRules(ReduceExpression re, AlphaExpression ae) {
		// do nothing
	}
}