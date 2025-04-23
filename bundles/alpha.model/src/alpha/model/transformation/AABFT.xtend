package alpha.model.transformation

import alpha.model.AlphaSystem
import alpha.model.Variable
import alpha.model.VariableExpression
import alpha.model.StandardEquation
import alpha.model.ReduceExpression
import alpha.model.REDUCTION_OP
import alpha.model.BINARY_OP
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.transformation.SubstituteByDef
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import static extension alpha.model.factory.AlphaUserFactory.*
import static extension alpha.model.util.ISLUtil.*
import static extension alpha.model.util.AlphaUtil.*


class AABFT extends AbstractAlphaCompleteVisitor{
	static Boolean DEBUG = false
	AlphaSystem sys
	
	private new(AlphaSystem system){
		sys = system 
	}

	static def void apply(AlphaSystem system){
		val aabft = new AABFT(system)
		system.accept(aabft)	

	}
	
	/**
	 * Visits standard equations (Outputs) in the AlphaZ system.
	 * @param se - An AlphaZ StandardEquation
	 */	
	override void inStandardEquation(StandardEquation se){
		val v		= se.variable
		val indices = v.domain.indexNames	
		
		checkDim(v, false)

		indices.forEach[index |
			makeChecksum(v, sys, index, indices.toString)
		]
		
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
	 * @param v - The variable being checked
	 * @param verbose - Flag for printing non-error information about the variable
	 */
	static def void checkDim(Variable v, Boolean verbose){
		val name  = v.name
		val dim   = v.domain.nbIndices
		
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
			if(verbose) println(msg)
		}
		else if(check == 1){
			var sb = new StringBuilder(msg)
			sb.insert(index, "(vector) ")
			msg = sb.toString()
			if(verbose) println(msg)
		}
		else{
			if(verbose) println(msg)
		}
	}
	
	
	/**
	 * Utility function to generate checksum variable name template.
	 * @param v - base variable being checksummed
	 * @param index - the index name of the variable
	 * 
	 * @return formatted name template
	 */
	static def String getNameTemplate(Variable v, String index){
		return String.format("check_%s_%s_", v.name, index)
	}
	
	/**
	 * Generates checksum, adds it to AlphaZ system, and returns variable and it's ReduceExpression.
	 * @param sys - AlphaZ system
	 * @param name - checksum name template
	 * @param domain - the domain of the checksum variable
	 * @param b_maff - base MultiAff
	 * @param p_maff - projected MultiAff
	 * @param base - base variable
	 * 
	 * @return Pair containing the checksum variable (key) and its ReduceExpression (value)
	 */
	static def Pair<Variable, ReduceExpression> addChecksum(AlphaSystem sys, String name, ISLSet domain, ISLMultiAff b_maff, ISLMultiAff p_maff, Variable base){
		val check	= createVariable(name+"0", domain)
		
		val varExp	= createVariableExpression(base)
		val depExp	= createDependenceExpression(b_maff, varExp)
		val redExp	= createReduceExpression(REDUCTION_OP.SUM, p_maff, depExp)
		
		val stdEq	= createStandardEquation(check, redExp)
		
		if(DEBUG){
			sys.outputs += check
		}
		else{
			sys.locals	+= check
		}
			
		
		sys.systemBodies.get(0).equations += stdEq
		
		return check -> redExp
	}
	
	/**
	 * Generates checksum comparator, adds it to AlphaZ system, and returns the generated variable.
	 * @param sys - AlphaZ system
	 * @param name - checksum name template
	 * @param redExp - the ReduceExpression of the primary checksum variable
	 * @param base - base variable
	 * 
	 * @return The checksum comparator variable
	 */
	static def Variable addComparator(AlphaSystem sys, String name, ISLSet domain, ReduceExpression redExp, Variable base){
		val comp	= createVariable(name+"1", domain)
		
		if(DEBUG){
			sys.outputs += comp
		}
		else{
			sys.locals	+= comp
		}
		
		val stdEq	= createStandardEquation(comp, redExp.copyAE)
		
		sys.systemBodies.get(0).equations += stdEq
		SubstituteByDef.apply(sys, stdEq, base)
		
		return comp
	}
	
	/**
	 * Adds the checksum invariant to the AlphaZ system.
	 * @param invariant - Declared AlphaZ variable
	 * @param prime - Primary checksum variable
	 * @param comp - Comparison checksum variable
	 * 
	 * @return Standard equation of the checksum invariant
	 */
	static def void addInvariant(AlphaSystem sys, String name, ISLSet domain, Variable prime, Variable comp){
		val inv 	= createVariable(name+'inv', domain)
		
		val prod 	= createVariableExpression(prime)
		val value	= createVariableExpression(comp)
		val diff	= createBinaryExpression(BINARY_OP.SUB, prod.copyAE, value)
		val expr	= createBinaryExpression(BINARY_OP.DIV, diff, prod)
		
		val stdEx	= createStandardEquation(inv, expr)	
		
		sys.outputs += inv
		sys.systemBodies.get(0).equations += stdEx
		
	}
	
	/**
	 * Generates the domain definition of the checksum variable.
	 * @param v - the base variable being checksummed
	 * @param index - the current index iterator along which the checksum is produced
	 * 
	 * @return The checksum variable's domain as an ISLSet
	 */
	static def ISLSet getDomain(Variable v, String index){
		val dim		= v.domain.nbIndices
		val p_dim	= dim-1
		
		var domain = if(p_dim == 0) "[N] -> {[]	 :	  	 }" else String.format('[N] -> {[%s]: 0<=%s<N}', index, index)
		
		return domain.toISLSet
	}
	
	/**
	 * Generates the ISL MultiAff definitions for the checksum variable.
	 * @param v - the base variable being checksummed
	 * @param currentIndex - the current index iterator along which the checksum is produced
	 * @param indices - the indices associated with the base variable 
	 * 
	 * @return Pair containing the base MultiAff (key) and the projection MultiAff (value)
	 */
	static def Pair<ISLMultiAff, ISLMultiAff> getMultiAffs(Variable v, String currentIndex, String indices){
		val dim		= v.domain.nbIndices
		val p_dim	= dim-1
		
		// define base variable multiaff
		val b_maff = String.format("[N] -> {%s -> %s}", indices, indices).toISLMultiAff
		
		// define projection multiaff
		
		val pmStr = if(p_dim==0) String.format("[N] -> {%s ->[]}", indices) else String.format("[N] -> {%s -> [%s]}", indices, currentIndex)
		val p_maff = pmStr.toISLMultiAff
				
		return b_maff -> p_maff
	}
	
	static def void makeChecksum(Variable v, AlphaSystem s, String index, String indices){
		// generate checksum variable name template
		val name	= getNameTemplate(v, index)
				
		// generate checksum domain
		val domain	= getDomain(v, index)
		
		// generate MultiAffs
		var maffs	= getMultiAffs(v, index, indices)
		val b_maff	= maffs.getKey()
		val p_maff	= maffs.getValue()
			
			
		// generates checksum
		val check = addChecksum(s, name, domain, b_maff, p_maff, v)
		val redExp = check.getValue()
		val prime = check.getKey()
		
		// generates comparison checksum
		val checkComp = addComparator(s, name, domain, redExp, v)
			
		// generates checksum invariant
		addInvariant(s, name, domain, prime, checkComp)
	}

	
}