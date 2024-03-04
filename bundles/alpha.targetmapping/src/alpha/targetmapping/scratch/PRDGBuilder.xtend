package alpha.targetmapping.scratch

import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.StandardEquation
import alpha.model.AlphaSystem
import alpha.model.DependenceExpression
import alpha.model.VariableExpression
import alpha.model.AlphaExpression
import alpha.model.Variable
import java.util.Set
import java.util.HashSet

class PRDGBuilder extends AbstractAlphaCompleteVisitor {
	
	PRDG prdg
	PRDGNode currentNode
	Set<Variable> inputs
	
	static def build(AlphaSystem system) {
		val visitor = new PRDGBuilder
		system.accept(visitor)
		visitor.prdg
	}
	
	new() {
		prdg = new PRDG
		inputs = new HashSet<Variable>
	}
	
	override inAlphaSystem(AlphaSystem system) {
		inputs.addAll(system.inputs)
		for (variable : system.outputs + system.locals) {
			prdg.addNode(variable)	
		}
	}
	
	override inStandardEquation(StandardEquation se) {
		currentNode = prdg.getNode(se.variable)
	}
	override outStandardEquation(StandardEquation se) {
		currentNode = null
	}
	
	override visitVariableExpression(VariableExpression ve) {
		if (inputs.contains(ve.variable)) {
			return 
		}
		variableExpressionRules(ve, ve.eContainer as AlphaExpression)
	}
	
	def dispatch variableExpressionRules(VariableExpression ve, DependenceExpression de) {
		val node = new PRDGNode(ve.variable)
		val edge = new PRDGEdge(currentNode, node, de.function, de.contextDomain)
		prdg.addEdge(edge)
	}
	
	def dispatch variableExpressionRules(VariableExpression ve, AlphaExpression ae) {
		
	}
}