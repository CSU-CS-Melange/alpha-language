package alpha.abft

import alpha.loader.AlphaLoader

import alpha.model.util.Show
import alpha.model.util.AShow

import static extension alpha.model.util.ISLUtil.*
import static extension alpha.model.factory.AlphaUserFactory.*
import static extension alpha.model.util.AlphaUtil.*

import alpha.model.transformation.SubstituteByDef
import alpha.model.transformation.Normalize

import alpha.model.transformation.reduction.ReductionComposition
import alpha.model.transformation.automation.OptimalSimplifyingReductions

import alpha.model.AlphaSystem
import alpha.model.AlphaInternalStateConstructor
import alpha.model.REDUCTION_OP
import alpha.model.AlphaExpression
import alpha.model.Variable
import alpha.model.BINARY_OP
import alpha.model.AlphaModelSaver
//import alpha.codegen.demandDriven.WriteC
//import alpha.codegen.BaseDataType
//import alpha.codegen.ProgramPrinter
import java.util.Scanner
import java.io.File
import java.util.ArrayList
import alpha.codegen.demandDriven.WriteC
import alpha.codegen.ProgramPrinter
import alpha.codegen.BaseDataType

/**
 * This class augments the input program by inserting checksums over the
 * output variables. We need a checksum expression pair (i.e., two independent
 * ways to compute the checksum) for each free dimension of each output variable.
 * Then for each pair, we need another variable to store/compute their difference.
 * 
 * For example, given an output variable with "d" free dimensions, such as:
 * 
 *   ...
 *   outputs
 *     O[i,j] : {[i,j] : ...}
 *     ...
 *   let
 * 	   // equations  
 *   ...
 * 
 * the default behavior inserts 3*2*d = 6*d new equations
 * 
 *   ...
 *   outputs
 *     O[i,j] : {[i,j] : ...}
 *     ...
 *   locals
 *     O_C_i_0 : {[i] : ...}
 *     O_C_i_1 : {[i] : ...}
 * 
 *     O_C_j_0 : {[j] : ...}
 *     O_C_j_1 : {[j] : ...}
 * 
 *     Inv_C_i : {[i] : ...}
 *     Inv_C_j : {[j] : ...}
 *   let
 * 	   // equations
 * 
 *     O_C_i_0[i] = reduce(+, [j], O[i,j]);
 *     O_C_i_1[i] = reduce(+, [j], O[i,j]);  // substitute and run simplification on this
 * 
 *     O_C_j_0[j] = reduce(+, [i], O[i,j]);
 *     O_C_j_1[j] = reduce(+, [i], O[i,j]);  // substitute and run simplification on this
 * 
 *     Inv_C_i[i] = (O_C_i_0 - O_C_i_1) / O_C_i_0;
 *     Inv_C_j[j] = (O_C_j_0 - O_C_j_1) / O_C_j_0;
 *   ...
 * 
 */

class InsertChecksums {
	static def void runOSR(AlphaSystem system) {
		val limit = 1
		val targetComplexity = 2
		val debug = true
		val trySplit = false
		val opts = OptimalSimplifyingReductions.apply(system, limit, targetComplexity, trySplit, debug).optimizations
		val states = opts.get(targetComplexity)
	
		println('\n\n\nSimplifications:\n')
	
		states.map[s | states.indexOf(s) -> s].forEach[pair |
			val state = pair.value
//			val stateSystem = state.root.systems.get(0)
			println()
			println(state.show)
//	      val simplificationOutDir = '''«outDir»/«system.name»/simplifications/v«pair.key»'''
//	      stateSystem.generateWriteC(simplificationOutDir)
//	      AlphaModelSaver.writeToFile('''«simplificationOutDir»/«system.name».alpha''', state.show.toString)
		]
	}
		
	static def AlphaExpression createM2VChecksumExpression(Variable v, String maff_str, String fp_str){
		// Generate MultiAff
		var maff = maff_str.toISLMultiAff
		
		// Create variable expression from variable
		var var_exp = createVariableExpression(v)
		
		// Create dependence expression
		var dep_exp = createDependenceExpression(maff, var_exp)
		
		// Create projection MultiAff
		var fp_maff = fp_str.toISLMultiAff
		
		// Create base reduction expression
		var red_exp = createReduceExpression(REDUCTION_OP.SUM, fp_maff, dep_exp)
		
		return red_exp
	}
	
	static def AlphaExpression createV2SChecksumExpression(Variable v, String maff_str, String fp_str){
		println("--------------------------------------")
		// Generate MultiAff
		var maff = maff_str.toISLMultiAff
//		println("maff: " + maff)
		
		// Create variable expression from variable
		var var_exp = createVariableExpression(v)
//		println("var_exp: " + var_exp)
		
		// Create dependence expression
		var dep_exp = createDependenceExpression(maff, var_exp)
//		println("dep_exp: " + dep_exp)
		
		// Create projection MultiAff
		var fp_maff = fp_str.toISLMultiAff
//		println("fp_maff: " + fp_maff)
		
		// Create base reduction expression
		var red_exp = createReduceExpression(REDUCTION_OP.SUM, fp_maff, dep_exp)
//		println("red_exp: " + red_exp)
		
		return red_exp
	}
	
	static def AlphaExpression createInvariantExpression(Variable v_0, Variable v_1){
		var c_prod = createVariableExpression(v_0)
		
		var c_val = createVariableExpression(v_1)
		
		var diff = createBinaryExpression(BINARY_OP.SUB, c_prod.copyAE, c_val)
		
		var quot = createBinaryExpression(BINARY_OP.DIV, diff, c_prod)
		
		return quot
	}
	
	static def void ABFT_matmult(AlphaSystem system){
		/* The system contains a list of SystemBody objects */
		
		val systemBody = system.systemBodies.get(0)
		
		/* 
		 * A system body contains a list of AlphaEquation objects.
		 * Note that there are two types of equations: StandardEquation and UseEquation
		 * You only need to worry about StandardEquations where the LHS and RHS
		 * are regular alpha expressions. UseEquations are used to call other systems.
		 */
//		val equation = systemBody.standardEquations.get(0)
		
		/* 
		 * A standard equation contains a variable refenence (for the LHS) and an
		 * AlphaExpression object.
		 */
//		println('equation var: ' + equation.variable.name)
//		val expr = equation.expr
		
		/* 
		 * Note that if you want to pretty print nodes in the AST, you should use the
		 * alpha.model.util.Show class
		 */ 
//		println('equation expr: ' + Show.print(expr))
		
		
		/////////////////////////////////////////////////////////////
		// Manipulating the AST
		/////////////////////////////////////////////////////////////
		
		/*
		 * The alpha.model.factory.AlphaUserFactory should/can be used to create anything
		 * you want to insert into the AST.
		 * All of the methods in the factory are static, and you can import then with
		 * xtend as "static extension methods" which makes them easier to use write.
		 * 
		 * For example, let's create a new local Variable. To do this, we need to give it
		 * a name (String) and a domain (ISLSet).
		 * 
		 * There are helper methods in the alpha.model.util.ISLUtil class that can be used
		 * to get isl objects. Take a look at the "toISLXXX" functions. These are all also
		 * static, and you can import them as static extensions.
		 * 
		 * A quick note on the xtend syntax here. Any static function in xtend can be called
		 * either by putting all of its arguments in parentheses or with "." chaining syntax 
		 * where the object preceding the "." corresponds to the first argument.
		 * This is a matter of personal preference, but it makes writing a lot of things much
		 * more concise. Instead, for example, of writing:
		 * 
		 *   val domain = alpha.model.util.AlphaUtil.toISLSet("xyz")
		 * 
		 * we can just write:
		 * 
		 *   val domain = "xyz".toISLSet
		 * 
		 * Also note that xtend does type inference, so we don't need to explicitly write the
		 * class type, we can just use the "val" keyword.
		 */

		// Insert checksum variables into program
		
		// Define variable domains
		val row_domain = '[N] -> {[i]: 0<=i<N}'.toISLSet
		val col_domain = '[N] -> {[j]: 0<=j<N}'.toISLSet
		
		val c_maff = "[N] -> {[i,j] -> [i,j]}"
		val fp_maff_r = "[N] -> {[i,j] -> [i]}"
		val fp_maff_c = "[N] -> {[i,j] -> [j]}"
		
		// Define checksum invariants
		val Inv_C_r = createVariable("Inv_C_r", row_domain)
		val Inv_C_c = createVariable("Inv_C_c", col_domain)
		
		/*
		 * Now we can put this variable in the system, as a local for example. Another note 
		 * about xtend's expressivity. The "locals" property of AlphaSystem is a "list". From the
		 * perspective of writing xtend, it doesn't matter if it is actually an ArrayList<Variable>
		 * or LinkedList<Variable> or a primitive Variable[]. These are all treated as the "same"
		 * type here. And xtend overloads many of the operators, so you don't need to explicitly call
		 * ".add(...)", you can just use "+=". This is achieved under the hooldby xtend's type
		 * inference mechanism.
		 */
		
		// Add checksum invariant variables to system outputs
		system.outputs += Inv_C_r
		system.outputs += Inv_C_c
		
		
		// Define column checksums
		val C_r_0 = createVariable("C_r_0", row_domain)
		val C_r_1 = createVariable("C_r_1", row_domain)
		
		// Define row checksums
		val C_c_0 = createVariable("C_c_0", col_domain)
		val C_c_1 = createVariable("C_c_1", col_domain)
		
		// Add checksum variables to system locals
		system.locals += C_r_0
		system.locals += C_r_1
		system.locals += C_c_0
		system.locals += C_c_1
		
		
		// Generate equations for checksums

		// Get product matrix from system
		val c = system.outputs.findFirst[v | v.name == 'C']
				
		// Get reduction expression for row checksums
		val c_red_exp_r = createM2VChecksumExpression(c, c_maff, fp_maff_r)
		
		// Generate equations for row checksum (two copies)
		val cr0_eq = createStandardEquation(C_r_0, c_red_exp_r)
		val cr1_eq = createStandardEquation(C_r_1, c_red_exp_r.copyAE)						
			
		// Add row checksum equations to system body
		systemBody.equations += cr0_eq
		systemBody.equations += cr1_eq
		
		// Substitute validation row checksum equation with definition
		SubstituteByDef.apply(system, cr1_eq, c)
		
		
		// Get reduction expression for column checksums
		val c_red_exp_c = createM2VChecksumExpression(c, c_maff, fp_maff_c)
		
		// Generate equations for column checksum (two copies)
		val cc0_eq = createStandardEquation(C_c_0, c_red_exp_c)
		val cc1_eq = createStandardEquation(C_c_1, c_red_exp_c.copyAE)						
			
		// Add column checksum equations to system body
		systemBody.equations += cc0_eq
		systemBody.equations += cc1_eq
		
		// Substitute "validation" column checksum equation with definition
		SubstituteByDef.apply(system, cc1_eq, c)
		
		
		// Define row checksum invariant
		val c_r_inv_exp = createInvariantExpression(C_r_0, C_r_1)
		val c_inv_r = createStandardEquation(Inv_C_r, c_r_inv_exp)
		
		
		// Define column checksum invariant
		val c_c_inv_exp = createInvariantExpression(C_c_0, C_c_1)
		val c_inv_c = createStandardEquation(Inv_C_c, c_c_inv_exp)
		
		
		// Add checksum invariants to system equations
		systemBody.equations += c_inv_r
		systemBody.equations += c_inv_c
	}
	
	static def void ABFT_sub(AlphaSystem system){
		val systemBody = system.systemBodies.get(0)
		
		// Define variable domains
		val c_domain = '{[]: }'.toISLSet
		val x_maff = "{[i] -> [i]}"
		val	c_maff = "{[i] -> []}"
		
		// Define checksum invariant
		val Inv_x_c = createVariable("Inv_x_c", c_domain)
		
		// Add checksum invariant variable to system outputs
		system.outputs += Inv_x_c
		
		// Define column checksums
		val x_c_0 = createVariable("x_c_0", c_domain)
		val x_c_1 = createVariable("x_c_1", c_domain)
		
		// Add checksum variables to system locals
		system.locals += x_c_0
		system.locals += x_c_1
		
		// Generate equations for checksums

		// Get product vector from system
		val x = system.outputs.findFirst[v | v.name == 'x']
		
		// Get reduction expression for column checksums
		val x_red_exp_c = createV2SChecksumExpression(x, x_maff, c_maff)
		
		// Generate equations for column checksum (two copies)
		val xc0_eq = createStandardEquation(x_c_0, x_red_exp_c)
		val xc1_eq = createStandardEquation(x_c_1, x_red_exp_c.copyAE)
		
		// Add column checksum equations to system body
		systemBody.equations += xc0_eq
		systemBody.equations += xc1_eq	
		
		// Substitute "validation" column checksum equation with definition
		SubstituteByDef.apply(system, xc1_eq, x)
		
		// Define column checksum invariant
		val x_c_inv_exp = createInvariantExpression(x_c_0, x_c_1)
		val x_inv_c = createStandardEquation(Inv_x_c, x_c_inv_exp)
				
		// Add checksum invariant to system equations
		systemBody.equations += x_inv_c	
	}
	
	static def void ABFT_lud(AlphaSystem system){
		val systemBody = system.systemBodies.get(0)
		
		// Define variable domains
		val c_domain = '{[]: }'.toISLSet
		val x_maff = "{[i] -> [i]}"
		val	c_maff = "{[i] -> []}"
		
		// Define checksum invariant
		val Inv_x_c = createVariable("Inv_x_c", c_domain)
		
		// Add checksum invariant variable to system outputs
		system.outputs += Inv_x_c
		
		// Define column checksums
		val x_c_0 = createVariable("x_c_0", c_domain)
		val x_c_1 = createVariable("x_c_1", c_domain)
		
		// Add checksum variables to system locals
		system.locals += x_c_0
		system.locals += x_c_1
		
		// Generate equations for checksums

		// Get product vector from system
		val x = system.outputs.findFirst[v | v.name == 'x']
		
		// Get reduction expression for column checksums
		val x_red_exp_c = createV2SChecksumExpression(x, x_maff, c_maff)
		
		// Generate equations for column checksum (two copies)
		val xc0_eq = createStandardEquation(x_c_0, x_red_exp_c)
		val xc1_eq = createStandardEquation(x_c_1, x_red_exp_c.copyAE)
		
		// Add column checksum equations to system body
		systemBody.equations += xc0_eq
		systemBody.equations += xc1_eq	
		
		// Substitute "validation" column checksum equation with definition
		SubstituteByDef.apply(system, xc1_eq, x)
		
		// Define column checksum invariant
		val x_c_inv_exp = createInvariantExpression(x_c_0, x_c_1)
		val x_inv_c = createStandardEquation(Inv_x_c, x_c_inv_exp)
				
		// Add checksum invariant to system equations
		systemBody.equations += x_inv_c	
	}
	
	static def void main(String[] args) {
		
		/////////////////////////////////////////////////////////////
		// Reading and inspecting an Alpha program
		/////////////////////////////////////////////////////////////
		
		/*
		 * You can read alpha programs from source files with AlphaLoader.
		 * On success, this returns an AlphaRoot object.
		 * See alpha.model > model > alpha.xcore for the IR structure.
		 */
		val in_dir   = 'resources/blas/'
		val out_dir  = 'resources/auto/'
		
		val mm_names = #['matmult', 'matrixmult', 'mat_mult', 'matrix_mult', 'matrix_multiplication']
		val fb_names = #['fsub', 'bsub', 'forward_sub', 'back_sub', 'forward_substitution', 'back_substitution', 'fs', 'bs']
		val lu_names = #['lud', 'lu_decomp', 'lu_decomposition', 'l_u_decomposition', 'l_u_d']
		
			// Prompt user for input file
		val scanner = new Scanner(System.in)
		print("Enter input alpha file in \'" + in_dir + "\': ")
		var sys_name = scanner.nextLine()
		scanner.close()
		
		if(!sys_name.endsWith('.alpha')){
			sys_name = sys_name.concat('.alpha')
		}
		
		// Define input/output files		
		val in_file = in_dir + sys_name
		val out_file = out_dir + sys_name
		
				
		// Check if input file exists
		val file = new File(in_file)
		if(!file.exists() || file.isDirectory()){
			println("ERROR:  \'" + in_file + "\' does not exist. Exiting...")
			System.exit(1)
		}
		else{
			println("Reading \'" + in_file + "\'")
		}
		println()
		
				 
		val root = AlphaLoader.loadAlpha(in_file)
		
		/* A root contains a list of AlphaSystem objects */
		val system = root.systems.get(0)
		
		// Update system name
		
		println(system.name)
		
		/* 
		 * The system holds input/output/local variables Variable domains are 
		 * ISLSet objects, see the isl bindings for all of the available functions 
		 * and/or entry points (the package is called fr.irisa.cairn.jnimap.isl).
		 */ 
		system.inputs.forEach[v  | println('input: '  + v.name + ' : ' + v.domain)]
		system.outputs.forEach[v | println('output: ' + v.name + ' : ' + v.domain)]
		system.locals.forEach[v | println('local: ' + v.name + ' : ' + v.domain)]
		
		// Apply ABFT to system based on name
		if(mm_names.contains(system.name)){
			println("Applying ABFT to Matrix Multiplication...")
			ABFT_matmult(system)	
		}
		else if(fb_names.contains(system.name)){
			println("Applying ABFT to Forward/Back Substitution...")
			ABFT_sub(system)
		}
		else if(lu_names.contains(system.name)){
			println("Applying ABFT to LU Decomposition...")
		}
		else{
			print("Unknown system. Exiting...")
			System.exit(2)
		}
		system.name = system.name + '_aabft'
		
		println("-------------------\nBase system:\n")
		println(Show.print(system))
		
		Normalize.apply(system)
		
		ReductionComposition.apply(system)
		AlphaInternalStateConstructor.recomputeContextDomain(system)
//		system.runOSR	
		println("-------------------\nNormalized system:\n")	
		println(AShow.print(system))
		
		// Save new alpha program
		println("Model saved to \'" + out_file + "\'")
//		AlphaModelSaver.writeToFile(out_file, AShow.print(system))
		AlphaModelSaver.ASave(root, out_file)
		println("Done")
		
		val program = WriteC.convert(system, BaseDataType.FLOAT, true)
		
		val code = ProgramPrinter.print(program).toString
 
		println(code)
 
 
//		system.runOSR	
		
		
		/*
		 * You would create and insert new equations in the same way on the system body 
		 * (i.e., systemBody.equations += ...). There are factory methods for creating equations and
		 * any expressions you may need. Take a look at the call signatures for more info.
		 */
		
	}	
}