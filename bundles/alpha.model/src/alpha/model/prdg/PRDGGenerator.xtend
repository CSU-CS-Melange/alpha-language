package alpha.model.prdg

import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.AlphaSystem
import alpha.model.DependenceExpression
import alpha.model.transformation.Normalize
import alpha.model.StandardEquation
import alpha.model.util.AShow
import alpha.model.VariableExpression
import alpha.model.ReduceExpression
import alpha.model.AbstractReduceExpression
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLAff

class PRDGGenerator extends AbstractAlphaCompleteVisitor {
	PRDG prdg = new PRDG()
	var PRDGNode src
	var ISLSet domain
	var ISLMultiAff func
	
	static def PRDG apply(AlphaSystem system) {
		val generator = new PRDGGenerator()
		Normalize.apply(system)
		system.accept(generator)
		generator.prdg
	}

	override void inStandardEquation(StandardEquation se) {
		println("SE: " + se.getVariable.getDomain)
//		this.func = ISLMultiAff.buildIdentity(se.getVariable.getDomain.getSpace);
		this.src = new PRDGNode(se.getVariable.getName, se.getVariable.getDomain)
	}

	override void outStandardEquation(StandardEquation se) {
		this.src = null
	}

	override void inDependenceExpression(DependenceExpression de) {
		this.func = de.getFunction
		this.domain = de.getContextDomain
	}
	
	override void inVariableExpression(VariableExpression ve) {
		var target = new PRDGNode(ve.toString, ve.getVariable.getDomain.copy)
		var dom = this.domain ?: ve.getContextDomain
		val edge = new PRDGEdge(src, target, dom.copy, func)
		prdg.addEdge(edge)
		
	}
	
	
	override void outDependenceExpression(DependenceExpression de) {
		this.domain = null		
	}
	
	override void inAlphaSystem(AlphaSystem system) {
		var variables = system.getVariables()
		prdg.addNodes(variables.filter[ v | !v.isInput() ].map[ v | new PRDGNode(v.name, v.getDomain())].toList())
	}

}