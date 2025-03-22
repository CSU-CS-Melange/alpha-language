package alpha.model.transformation

import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.StandardEquation
import alpha.model.AlphaSystem
import alpha.model.VariableExpression
import fr.irisa.cairn.jnimap.isl.ISLDimType

class AABFT extends AbstractAlphaCompleteVisitor{
	
	static def void apply(AlphaSystem system){
		val aabft = new AABFT()
		system.accept(aabft)
		println("done")
			
		
	}
	
	override void inVariableExpression(VariableExpression ve){
		print("name: ")
		println(ve.variable.name)
		
		print("domain: ")
		println(ve.variable.domain)
		
		print("basic sets: ")
		println(ve.variable.domain.nbBasicSets)
		
		print("constants: ")
		println(ve.variable.domain.nbConstants)
		
		print("divs: ")
		println(ve.variable.domain.nbDivs)
		
		print("indices: ")
		println(ve.variable.domain.nbIndices)
		
		print("params: ")
		println(ve.variable.domain.nbParams)
		
		print("points: ")
		println(ve.variable.domain.nbPoints)
		
		println("ve done")
	}
	
	override void inStandardEquation(StandardEquation se){
		print("name: ")
		println(se.variable.name)
		
		print("domain: ")
		println(se.variable.domain)
		
		print("basic sets: ")
		println(se.variable.domain.nbBasicSets)
		
		print("constants: ")
		println(se.variable.domain.nbConstants)
		
		print("divs: ")
		println(se.variable.domain.nbDivs)
		
		print("indices: ")
		println(se.variable.domain.nbIndices)
		
		print("params: ")
		println(se.variable.domain.nbParams)
		
		print("points: ")
		println(se.variable.domain.nbPoints)
		
		print("expr: ")
		println(se.expr)
		
		println("se done")
	}
	
	
}