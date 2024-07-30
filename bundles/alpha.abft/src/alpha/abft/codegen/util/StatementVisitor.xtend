package alpha.abft.codegen.util

import alpha.model.AlphaExpression
import alpha.model.AlphaNode
import alpha.model.AlphaSystem
import alpha.model.AutoRestrictExpression
import alpha.model.CaseExpression
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.StandardEquation
import alpha.model.Variable
import alpha.model.util.AbstractAlphaCompleteVisitor
import java.util.HashMap

import static extension alpha.model.util.AlphaUtil.getContainerEquation
import java.util.List

class StatementVisitor extends AbstractAlphaCompleteVisitor {
	
	val HashMap<Variable,List<AlphaExpression>> exprs = newHashMap
	
	def static apply(AlphaSystem system) {
		val visitor = new StatementVisitor
		system.accept(visitor)
		visitor.exprs
	}
	
	override outAlphaExpression(AlphaExpression ae) {
		val parent = ae.eContainer as AlphaNode
		rules(parent, ae)
	}
	
	def dispatch rules(StandardEquation se, ReduceExpression e) {}
	def dispatch rules(StandardEquation se, CaseExpression e) {}
	def dispatch rules(StandardEquation se, RestrictExpression re) { re.expr.addExpr }
	def dispatch rules(StandardEquation se, AlphaExpression e) { e.addExpr }
	
	def dispatch rules(CaseExpression se, AutoRestrictExpression re) { re.expr.addExpr }
	def dispatch rules(CaseExpression se, RestrictExpression re) { re.expr.addExpr }
	def dispatch rules(CaseExpression ce, AlphaExpression e) { e.addExpr }
	
	def dispatch rules(ReduceExpression se, RestrictExpression re) { re.expr.addExpr }
	def dispatch rules(ReduceExpression re, AlphaExpression e) { e.addExpr }
	
	def dispatch rules(AlphaNode an, AlphaExpression ae) {}
	
	def addExpr(AlphaExpression ae) {
		val variable = (ae.getContainerEquation as StandardEquation).variable
		val childExprs = exprs.get(variable) ?: newLinkedList
		childExprs += ae
		exprs.put(variable, childExprs)
	}
}