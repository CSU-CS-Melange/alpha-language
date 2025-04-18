package alpha.model.transformation

import alpha.model.AlphaSystem
import alpha.model.Variable
import alpha.model.AlphaExpression
import alpha.model.VariableExpression
import alpha.model.StandardEquation
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.transformation.SubstituteByDef
import fr.irisa.cairn.jnimap.isl.ISLSet
import static extension alpha.model.factory.AlphaUserFactory.*
import static extension alpha.model.util.ISLUtil.*
import static extension alpha.model.util.AlphaUtil.*
import alpha.model.REDUCTION_OP
import alpha.model.BINARY_OP

//import alpha.model.util.Show

class AABFT extends AbstractAlphaCompleteVisitor{
	AlphaSystem sys
	
	private new(AlphaSystem system){
		sys = system 
	}

	static def void apply(AlphaSystem system){
//		println("--------------\nInput system:")
//		println(Show.print(system))
		val aabft = new AABFT(system)
		system.accept(aabft)	
//		println("--------------\nOutput system:")
//		println(Show.print(system))
	}
	
	/**
	 * Visits standard equations (Outputs) in the AlphaZ system.
	 * @param se - An AlphaZ StandardEquation
	 */	
	override void inStandardEquation(StandardEquation se){
		val v		= se.variable
		val e		= se.expr
		val name	= se.variable.name
		val dim		= se.variable.domain.nbIndices
		val indices = se.variable.domain.indexNames	
		
		checkDim(name, dim)
//		duplicateVariable(v, sys, e)

		indices.forEach[index |
			makeChecksum(v, sys, index, indices.toString)
		]
		
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
	
	/**
	 * Visits variable expressions (Inputs) in the AlphaZ system.
	 * @param ve - An AlphaZ VariableExpression
	 */
	override void inVariableExpression(VariableExpression ve){
//		val v		= ve.variable		
//		val name	= ve.variable.name
//		val dim		= ve.variable.domain.nbIndices
//		
////		checkDim(name, dim)
////		duplicateVariable(v, sys)

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
	
	/**
	 * Utility function. Checks dimensions of variable domain to ensure a checksum (T-1 dimensions) could be generated.
	 * @param name - The name of the variable
	 * @param dim - The number of dimensions in the variable's domain
	 */
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
		
	/**
	 * Duplicates variable expression (input) variable and inserts into AlphaZ system.
	 * @param v - An AlphaZ Variable
	 * @param s - An AlphaZ System
	 * @returns The new AlphaZ Variable that was added to the system. 
	 */
	static def Variable duplicateVariable(Variable v, AlphaSystem s){
		val baseName	= v.name
		val baseDomain	= v.domain
		val newName		= baseName+'_2'
		val newDomain	= baseDomain.copy()
		val newVar		= createVariable(newName, newDomain)

		switch (getVariableGroup(v)) {
			case 1: {
				s.inputs += newVar
			}
			case 2: {
				s.locals += newVar
			}
			case 3: {
				s.outputs += newVar
			}
			default: {
				println("Base variable not found in Alpha system.")
				return null
			}
		}
		
		return newVar
	}
	/**
	 * Duplicates standard equation (output) variable and inserts into AlphaZ system.
	 * Overloaded function that calls variable expression version.
	 * @param v - An AlphaZ Variable
	 * @param s - An AlphaZ System
	 * @param e - An AlphaZ AlphaExpression
	 */
	static def void duplicateVariable(Variable v, AlphaSystem s, AlphaExpression e){
		val newVar		= duplicateVariable(v, s)
		// TODO: Fix conversion from StandardEquation to ReductionExpression
		if(newVar !== null && false){
			val newEq	= createStandardEquation(newVar, e)
		
			s.systemBodies.get(0).equations += newEq
			SubstituteByDef.apply(s, newEq, newVar)
		}
	}

	/**
	 * Utility function. Gets the system group of a variable.
	 * @param v - An AlphaZ Variable
	 * @returns An integer representing the variable's group:
	 * 1 = input,
	 * 2 = local,
	 * 3 = output,
	 * 0 = none
	 */
	static def int getVariableGroup(Variable v){
		if(v.isInput){
			return 1
		}
		else if(v.isLocal){
			return 2
		}
		else if(v.isOutput){
			return 3
		}
		else{
			return 0
		}
	}
	
	static def void makeChecksum(Variable v, AlphaSystem s, String index, String indices){
		// create domain
		// TODO: Domain generator function
		val domain = String.format('[N] -> {[%s]: 0<=%s<N}', index, index).toISLSet
		println("domain: " + domain)
		
		// create checksum variable name
		// TODO: name generator function
		val name = String.format("check_%s_%s_", v.name, index)
		
		// create primary checksum variable
		// TODO: prime checksum generator function
		val checkVarPrime = createVariable(name+"0", domain)
		s.locals += checkVarPrime
		println("check var: " + checkVarPrime.name)
		
		// define base variable multiaff
		val maff = String.format("[N] -> {%s -> %s}", indices, indices).toISLMultiAff
		println("variable maff: '" + maff)	
				
		// define projection multiaff
		val fp_maff = String.format("[N] -> {%s -> [%s]}", indices, index).toISLMultiAff
				
		// create checksum variable		
		val checkExp = createVariableExpression(v)
		val checkDep = createDependenceExpression(maff, checkExp)
		val checkRed = createReduceExpression(REDUCTION_OP.SUM, fp_maff, checkDep)
		
		// add checksum equation to system body 
		val checkPrimeStdEq = createStandardEquation(checkVarPrime, checkRed)
		s.systemBodies.get(0).equations += checkPrimeStdEq
		
		
		// create comparison checksum variable
		// TODO: comparison generator function
		val checkVarComp = createVariable(name+"1", domain)
		s.locals += checkVarComp
		println("check var: " + checkVarComp.name)
		
		// add comparison checksum equation to system body and substitute by definition
		val checkCompStdEq = createStandardEquation(checkVarComp, checkRed.copyAE)
		s.systemBodies.get(0).equations += checkCompStdEq
		SubstituteByDef.apply(s, checkCompStdEq, v)
			
		// TODO: Checksum invariant
		val checkInv = createVariable(name+'inv', domain)
		s.outputs += checkInv
		
		val checkInvProd = createVariableExpression(checkVarPrime)
		val checkInvVal  = createVariableExpression(checkVarComp)
		val checkInvDiff = createBinaryExpression(BINARY_OP.SUB, checkInvProd.copyAE, checkInvVal)
		val checkInvExp  = createBinaryExpression(BINARY_OP.DIV, checkInvDiff, checkInvProd)
		
		val checkInvEq   = createStandardEquation(checkInv, checkInvExp)
		
		s.systemBodies.get(0).equations += checkInvEq
		
		
	}
}