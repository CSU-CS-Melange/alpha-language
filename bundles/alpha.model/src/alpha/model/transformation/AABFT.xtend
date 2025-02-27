package alpha.model.transformation

import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.StandardEquation
import alpha.model.AlphaSystem
import alpha.model.VariableExpression

class AABFT extends AbstractAlphaCompleteVisitor{
	
	static def void apply(AlphaSystem system){
		val aabft = new AABFT()
		system.accept(aabft)
		println("done")
			
		
	}
	
	override void inVariableExpression(VariableExpression ve){
		println(ve.variable.name)
		println(ve.variable.domain)
		println("ve done")
	}
	
	override void inStandardEquation(StandardEquation se){
		println(se.variable.name)
		println(se.variable.domain)
		println(se.expr)
		println("se done")
	}
	
	
}