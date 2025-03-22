package alpha.model.transformation

import alpha.model.AlphaSystem
import alpha.model.StandardEquation
import alpha.model.VariableExpression
import alpha.model.util.AbstractAlphaCompleteVisitor

class AABFT extends AbstractAlphaCompleteVisitor{
	
	static def void apply(AlphaSystem system){
		val aabft = new AABFT()
		system.accept(aabft)
		println("done")	
	}
		
	override void inStandardEquation(StandardEquation se){
		val name = se.variable.name
		val dim = se.variable.domain.nbIndices
		
		checkDim(name, dim)
		
		/*
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
		* 
		*/
	}
	
	override void inVariableExpression(VariableExpression ve){
		val name = ve.variable.name
		val dim = ve.variable.domain.nbIndices
		
		checkDim(name, dim)

		/*
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
		* 
		*/
	}
	
		static def checkDim(String name, int dim){
		val check = dim-1
		
		var msg = "The variable \'" + name + "\' has a (" + dim + ")-dimensional domain, which would produce a "+ check +"-dimensional checksum."
		val index = 83
		
		if(check < 0){
			println("ERROR: Invalid variable detected. " + msg)
			System.exit(1)
		}
		else if(check == 0){
			var sb = new StringBuilder(msg)
			sb.insert(index, "(scalar) ")
			msg = sb.toString()
			println(msg)
		}
		else if(check == 1){
			var sb = new StringBuilder(msg)
			sb.insert(index, "(vector) ")
			msg = sb.toString()
			println(msg)
		}
		else{
			println(msg)
		}
	}
}