package alpha.abft.transformation

import alpha.loader.AlphaLoader
import alpha.model.util.Show

import static extension alpha.model.util.ISLUtil.toISLSet
import static extension alpha.model.factory.AlphaUserFactory.createVariable

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
	
	static def void main(String[] args) {
		
		/////////////////////////////////////////////////////////////
		// Reading and inspecting an Alpha program
		/////////////////////////////////////////////////////////////
		
		/*
		 * You can read alpha programs from source files with AlphaLoader.
		 * On success, this returns an AlphaRoot object.
		 * See alpha.model > model > alpha.xcore for the IR structure.
		 */
		val root = AlphaLoader.loadAlpha('resources/blas/matmult.alpha')
		
		/* A root contains a list of AlphaSystem objects */
		val system = root.systems.get(0)
		println(system.name)
		
		/* 
		 * The system holds input/output/local variables Variable domains are 
		 * ISLSet objects, see the isl bindings for all of the available functions 
		 * and/or entry points (the package is called fr.irisa.cairn.jnimap.isl).
		 */ 
		system.inputs.forEach[v  | println('input: '  + v.name + ' : ' + v.domain)]
		system.outputs.forEach[v | println('output: ' + v.name + ' : ' + v.domain)]
		system.locals.forEach[v | println('local: ' + v.name + ' : ' + v.domain)]
		
		/* The system contains a list of SystemBody objects */
		val systemBody = system.systemBodies.get(0)
		
		/* 
		 * A system body contains a list of AlphaEquation objects.
		 * Note that there are two types of equations: StandardEquation and UseEquation
		 * You only need to worry about StandardEquations where the LHS and RHS
		 * are regular alpha expressions. UseEquations are used to call other systems.
		 */
		val equation = systemBody.standardEquations.get(0)
		
		/* 
		 * A standard equation contains a variable refenence (for the LHS) and an
		 * AlphaExpression object.
		 */
		println('equation var: ' + equation.variable.name)
		val expr = equation.expr
		
		/* 
		 * Note that if you want to pretty print nodes in the AST, you should use the
		 * alpha.model.util.Show class
		 */ 
		println('equation expr: ' + Show.print(expr))
		
		
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
		
		val domain = '[N] -> {[i,j,k] : 0<=k<=j<=i<=N}'.toISLSet
		val myNewVarName = createVariable("newVar", domain)
		
		/*
		 * Now we can put this variable in the system, as a local for example. Another note 
		 * about xtend's expressivity. The "locals" property of AlphaSystem is a "list". From the
		 * perspective of writing xtend, it doesn't matter if it is actually an ArrayList<Variable>
		 * or LinkedList<Variable> or a primitive Variable[]. These are all treated as the "same"
		 * type here. And xtend overloads many of the operators, so you don't need to explicitly call
		 * ".add(...)", you can just use "+=". This is achieved under the hooldby xtend's type
		 * inference mechanism.
		 */
		system.locals += myNewVarName
		
		println(Show.print(system))
		
		/*
		 * You would create and insert new equations in the same way on the system body 
		 * (i.e., systemBody.equations += ...). There are factory methods for creating equations and
		 * any expressions you may need. Take a look at the call signatures for more info.
		 */
		
	}
	
	
	
	
	
	
	
	
	
	
	
}