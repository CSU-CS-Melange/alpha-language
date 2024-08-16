package alpha.model.prdg

import alpha.model.AlphaSystem
import alpha.model.DependenceExpression
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.VariableExpression
import alpha.model.transformation.Normalize
import alpha.model.util.AbstractAlphaCompleteVisitor
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.Stack

import static extension alpha.model.util.AlphaUtil.copyAE
import fr.irisa.cairn.jnimap.isl.ISLMap

class PRDGGenerator extends AbstractAlphaCompleteVisitor {
	PRDG prdg = new PRDG()
	var Stack<PRDGNode> sources
	var Stack<ISLSet> domains
	var Stack<ISLMultiAff> functions
	var int numberReductions
	val boolean includeInputs
	
	static def PRDG apply(AlphaSystem system) {
		val generator = new PRDGGenerator(false)
		val copy = system.copyAE
		Normalize.apply(copy)
		copy.accept(generator)
		generator.prdg
	}
	
	new(boolean includeInputs) {
		this.includeInputs = includeInputs
		this.functions = new Stack()
		this.domains = new Stack()
		this.sources = new Stack()
	}
	
	override void inAlphaSystem(AlphaSystem system) {
		var variables = system.variables
		prdg.addNodes(variables.filter[ v | !v.isInput || includeInputs].map[ v | new PRDGNode(v.name, v.domain.copy)].toList)
	}

	override void inStandardEquation(StandardEquation standardEquation) {		
		this.functions.push(ISLMultiAff.buildIdentity(standardEquation.variable.domain.copy.identity.space))
		this.sources.push(new PRDGNode(standardEquation.variable.name, standardEquation.variable.domain.copy))
		this.numberReductions = 0
	}

	override void outStandardEquation(StandardEquation se) {
		this.sources.pop
		this.functions.pop
		this.numberReductions = 0
	}

	override void inDependenceExpression(DependenceExpression dependenceExpression) {
		this.functions.push(dependenceExpression.function.copy.pullback(this.functions.peek.copy))
		this.domains.push(dependenceExpression.contextDomain.copy)
	}
	
	override void outDependenceExpression(DependenceExpression dependenceExpression) { 
		this.functions.pop
		this.domains.pop
	}
	
	override void visitVariableExpression(VariableExpression ve) {
		var target = new PRDGNode(ve.variable.name, ve.variable.domain)
		val dom = !this.domains.empty() ? this.domains.peek.copy : ve.contextDomain.copy
		val fun = this.functions.peek.copy
		val edge = new PRDGEdge(this.sources.peek, target, dom.copy, fun)
		prdg.addEdge(edge)
		
	}
	
	override void inReduceExpression(ReduceExpression reduceExpression) {

		//Names for the new reduction nodes
		var reductionName = this.sources.peek.name + "_reduce" + this.numberReductions + "_result"
		val bodyName = this.sources.peek.name + "_reduce" + this.numberReductions + "_body"
		this.numberReductions++
		
		
		this.prdg.nodes.add(new PRDGNode(reductionName, reduceExpression.contextDomain.copy, true))
		
		//Dependence from use to the result
		val useToRes = this.functions.peek.copy

		//When the parent is a dependence expression, the context domain of that dependence is what we want
		//otherwise, assume identity dependence and use the context domain of the variable
		val dom = !this.domains.empty() ? this.domains.peek.copy : reduceExpression.contextDomain.copy
		this.prdg.addEdge(new PRDGEdge(this.sources.peek, prdg.getNode(reductionName), dom, useToRes))
		
		//Node for body
		this.prdg.nodes.add(new PRDGNode(bodyName, reduceExpression.body.contextDomain.copy, true))

		//Dependence from result to body
		val ISLMultiAff resToBody = reduceExpression.projection.copy
		
		prdg.addEdge(new PRDGEdge(prdg.getNode(reductionName), prdg.getNode(bodyName), reduceExpression.body.contextDomain.copy, resToBody))

		//Inside the reduction, dependence is from the body
		this.sources.push(this.prdg.getNode(bodyName))
		//Inside the reduction, dependence starts from identity to the reduction body
		this.functions.push(ISLMultiAff.buildIdentity(reduceExpression.body.contextDomain.copy.identity.space))
		

	}
	
	override void outReduceExpression(ReduceExpression reduceExpression) {
		this.sources.pop
		this.functions.pop
	}
}