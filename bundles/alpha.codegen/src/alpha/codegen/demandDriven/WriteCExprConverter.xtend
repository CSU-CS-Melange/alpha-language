package alpha.codegen.demandDriven

import alpha.codegen.Expression
import alpha.codegen.Factory
import alpha.codegen.MacroStmt
import alpha.codegen.ProgramBuilder
import alpha.codegen.alphaBase.AlphaBaseHelpers
import alpha.codegen.alphaBase.AlphaNameChecker
import alpha.codegen.alphaBase.ExprConverter
import alpha.codegen.isl.ASTConverter
import alpha.codegen.isl.LoopGenerator
import alpha.model.ReduceExpression
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSpace

/**
 * Converts Alpha expressions to simpleC expressions.
 * Adds support for reduce expressions.
 */
class WriteCExprConverter extends ExprConverter {
	/** The name of the reduction variable inside of reduce functions. */
	protected static val reduceVarName = "reduceVar"
	
	/** The program being built. */
	protected val ProgramBuilder program
	
	/** Generates data types compatible with WriteC. */
	protected val WriteCTypeGenerator typeGenerator
	
	/**
	 * A counter for the number of reductions that have been created.
	 * This is used for determining the names of functions and macros which will be emitted.
	 */
	protected var nextReductionId = 0
	
	/** Constructs a new converter for expressions. */
	new(WriteCTypeGenerator typeGenerator, AlphaNameChecker nameChecker, ProgramBuilder program) {
		super(typeGenerator, nameChecker)
		this.program = program
		this.typeGenerator = typeGenerator
	}
	
	/**
	 * Converts an Alpha reduce expression into the appropriate C AST nodes.
	 * A new function is created and added to the program which computes the reduction,
	 * and the appropriate function call expression is returned.
	 */
	def dispatch Expression convertExpr(ReduceExpression expr) {
		// Create the reduce function and add it to the program.
		val reduceFunction = createReduceFunction(program, expr)
		program.addFunction(reduceFunction)
		
		// Return a call to the reduce function.
		val callArguments = #[expr.contextDomain.paramNames, expr.contextDomain.indexNames].flatten
		return Factory.callExpr(reduceFunction.name, callArguments)
	}
	
	/** Creates the function which evaluates the reduction at a specific output point. */
	def protected createReduceFunction(ProgramBuilder program, ReduceExpression expr) {
		// Generate unique names for the reduce function, the reduce point macro,
		// and the accumulation macro. These names are just a prefix plus a common ID number.
		// Keep incrementing that number until the names are unique.
		var String reduceFunctionName
		var String reducePointMacroName
		var String accumulateMacroName
		do {
			reduceFunctionName = "reduce" + nextReductionId
			reducePointMacroName = "RP" + nextReductionId
			accumulateMacroName = "R" + nextReductionId
			nextReductionId += 1
		} while(program.nameChecker.isGlobalOrKeyword(reduceFunctionName, reducePointMacroName, accumulateMacroName))
		
		// Start building the reduce function.
		// The return type is the value type of the variable that the reduce expression writes to.
		val function = program.startFunction(true, false, typeGenerator.alphaValueType, reduceFunctionName)
		
		// Create the "reduction variable", which is what the reduction will accumulate into.
		// This needs to be initialized to the correct value for the reduction operator.
		val initializeStmt = Factory.assignmentStmt(reduceVarName, AlphaBaseHelpers.getReductionInitialValue(typeGenerator.alphaValueBaseType, expr.operator))
		function.addVariable(typeGenerator.alphaValueType, reduceVarName)
			.addStatement(initializeStmt)
			
		// Create the macros that evaluate points within the reduction body
		// and accumulate them into the reduce variable.
		val reducePointMacro = createReducePointMacro(reducePointMacroName, program, expr)
		val accumulateMacro = createAccumulationMacro(accumulateMacroName, expr, reducePointMacro)
		function.addStatement(reducePointMacro, accumulateMacro)
		
		// Use isl to determine what points need to be reduced and how they get reduced.
		val loopDomain = expr.createReduceLoopDomain
		val islAST = LoopGenerator.generateLoops(accumulateMacro.name, loopDomain)
		
		// The size parameters for the loop domain need to be added as function parameters.
		function.addParameter(loopDomain.paramNames.map[toParameter])
		
		// Add declarations for all the loop variables and add the loops themselves to the function.
		val loopResult = ASTConverter.convert(islAST)
		loopResult.declarations.forEach[function.addVariable(typeGenerator.indexType, it)]
		function.addStatement(loopResult.statements)
			
		// Undefine the macros, then have the function return the reduce variable.
		function.addUndefine(reducePointMacro, accumulateMacro)
			.addReturn(reduceVarExpr)
			
		return function.instance
	}
	
	/** Constructs the domain which will represent the loop nest that isl will produce. */
	def protected createReduceLoopDomain(ReduceExpression reduceExpr) {
		// We will use ISL to create the loop nest for the reduction.
		// This needs two things: the domain of points to iterate over,
		// and a map to the time at which they are computed at.
		//
		// When the reduce function is called, we want a specific value within the domain
		// where the reduction outputs its results to (i.e., the context domain of the reduce expression).
		// To allow constraining the reduction body to only the points needed for a particular output,
		// we take the reduction body's context domain and add a parameter for each output dimension.
		// Then, since the output point is determined by the reduction's projection function,
		// we equate each of the output parameters to the appropriate output dimension of the projection function.
		// This will give us a set which is parameterized by the output domain,
		// which in turn allows isl to produce the loops that produce only that one output.
		
		// We will start with the entire reduction body, then add parameters and constraints to it.
		var pointsToReduce = reduceExpr.body.contextDomain.copy
		
		// When we create new names for the domain's parameters,
		// we want to ensure they are unique.
		val existingNames = newHashSet
		existingNames.addAll(pointsToReduce.paramNames)
		existingNames.addAll(pointsToReduce.indexNames)
		
		// For each index in the reduction's output domain,
		// add a new parameter representing that output dimension to the set of points to reduce.
		// Then, set that new parameter equal to its expression in the reduction's projection function.
		// This will ensure we only reduce the set of points which are going to the desired output point.
		val outputDomain = reduceExpr.contextDomain
		for (i: 0 ..< outputDomain.nbIndices) {
			// Get a unique parameter name which will represent that output,
			// record it as an existing name, and add it to the set of points to reduce.
			val outputName = outputDomain.getIndexName(i)
			val parameterName = nameChecker.getUniqueLocalName(existingNames, outputName, "p")
			existingNames.add(parameterName)
			pointsToReduce = pointsToReduce.addParams(#[parameterName])
			
			// Construct an equality constraint for that new parameter
			// according to the expression which computes that index.
			// Add the constraint to the set of points to reduce.
			val outputExpr = reduceExpr.projection.getAff(i)
			val outputConstraint = constrainAddedParameter(pointsToReduce.space, outputExpr)
			pointsToReduce = pointsToReduce.addConstraint(outputConstraint)
		}
		
		return pointsToReduce
	}
	
	/**
	 * Adds an equality constraint to the recently added parameter
	 * (i.e., the parameter with the largest index).
	 * This parameter is set equal to the given affine expression
	 * (which comes from the reduction's projection function).
	 */
	def protected static constrainAddedParameter(ISLSpace reductionSpace, ISLAff outputExpr) {
		// Since we're constraining the parameter that was just added,
		// we know the parameter index we're constraining is the last one.
		val parameterIndex = reductionSpace.nbParams - 1
		
		// The equality constraint we want to add is of the form: outputExpr - newParameter = 0.
		// Thus, let's start with a fresh equality constraint and set the coefficient of
		// the new parameter to be -1.
		var equality = ISLConstraint
			.buildEquality(reductionSpace.copy)
			.setCoefficient(ISLDimType.isl_dim_param, parameterIndex, -1)
		
		// Next, we will copy all the coefficients from the output expression.
		// Since all new parameters are added to the end of the reduction space,
		// the parameters and indices for the output expression are still aligned
		// with the reduction space.
		for (paramIdx: 0 ..< outputExpr.nbParams) {
			val coefficient = outputExpr.getCoefficientVal(ISLDimType.isl_dim_param, paramIdx)
			equality = equality.setCoefficient(ISLDimType.isl_dim_param, paramIdx, coefficient)
		}
		for (inIdx: 0 ..< outputExpr.nbInputs) {
			val coefficient = outputExpr.getCoefficientVal(ISLDimType.isl_dim_in, inIdx)
			equality = equality.setCoefficient(ISLDimType.isl_dim_set, inIdx, coefficient)
		}
		
		return equality
	}
	
	/** Gets an expression representing the reduce variable. */
	def protected static reduceVarExpr() {
		return Factory.customExpr(reduceVarName)
	}
	
	/** Constructs a parameter for the reduce function. */
	def protected toParameter(String name) {
		return Factory.parameter(typeGenerator.indexType, name)
	}
	
	/** Constructs the macro that evaluates a point within the reduction body. */
	def protected createReducePointMacro(String macroName, ProgramBuilder program, ReduceExpression expr) {
		val arguments = expr.body.contextDomain.indexNames
		val replacement = convertExpr(expr.body)
		return Factory.macroStmt(macroName, arguments, replacement)
	}
	
	/** Constructs the macro used to accumulate points of the reduction body into the reduce variable. */
	def protected createAccumulationMacro(String macroName, ReduceExpression expr, MacroStmt reducePointMacro) {
		// Construct a call to the reduce point macro.
		// Since isl's call to this macro doesn't parenthesize the inputs,
		// and since the macro for the reduction body might not be parenthesized,
		// parenthesize all the arguments to the call.
		val reducePointArguments = expr.body.contextDomain.indexNames.map[Factory.parenthesizedExpr(it)]
		val reducePointCall = Factory.callExpr(reducePointMacro.name, reducePointArguments)
		
		// Construct a binary expression to add the reduce variable to the point which was reduced,
		// then wrap that in an assignment statement.
		val operator = AlphaBaseHelpers.getOperator(expr.operator)
		val accumulateExpr = Factory.binaryExpr(operator, reduceVarExpr, reducePointCall)
		val accumulateStmt = Factory.assignmentStmt(reduceVarExpr, accumulateExpr)
		
		return Factory.macroStmt(macroName, expr.body.contextDomain.indexNames, accumulateStmt)
	}
}