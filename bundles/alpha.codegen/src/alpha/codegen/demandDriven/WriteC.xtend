package alpha.codegen.demandDriven

import alpha.codegen.BaseDataType
import alpha.codegen.BinaryOperator
import alpha.codegen.DataType
import alpha.codegen.Factory
import alpha.codegen.IfStmtBuilder
import alpha.codegen.alphaBase.AlphaBaseHelpers
import alpha.codegen.alphaBase.AlphaNameChecker
import alpha.codegen.alphaBase.CodeGeneratorBase
import alpha.codegen.alphaBase.FlagStatus
import alpha.codegen.isl.ASTConverter
import alpha.codegen.isl.LoopGenerator
import alpha.codegen.isl.MemoryUtils
import alpha.codegen.isl.PolynomialConverter
import alpha.model.AlphaSystem
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.UseEquation
import alpha.model.Variable
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.CommonExtensions.toArrayList
import alpha.model.ReduceExpression
import alpha.model.VariableExpression
import org.eclipse.xtext.EcoreUtil2
import alpha.codegen.Program

/**
 * Generates demand-driven code that performs cycle detection.
 * The intended entry point is the static "convert" method,
 * which handles the entire conversion process.
 */
class WriteC extends CodeGeneratorBase {
	/**
	 * If true, compatibility with the older AlphaZ system will be maintained.
	 * If false, all memory will be linearized.
	 */
	protected val boolean oldAlphaZCompatible
	
	/** Converts Alpha expressions to simpleC expressions. */
	protected val WriteCExprConverter exprConverter
	
	/** The next ID to use as a statement macro. */
	protected var int nextStatementId = 0
	
	
	/////////////////////////////////////////////////////////////
	// Intended Entry Points
	/////////////////////////////////////////////////////////////
	
	def static convert(AlphaSystem system, BaseDataType valueType, boolean oldAlphaZCompatible) {
		if (system.systemBodies.length != 1) {
			throw new IllegalArgumentException("Systems must have exactly one body to be converted directly to WriteC code.")
		}
		val duplicate = system.copyAE
		val body = duplicate.systemBodies.get(0)
		return convert(body, valueType, oldAlphaZCompatible)
	}
	
	/**
	 * Converts an Alpha system body to a simpleC AST.
	 * @param systemBody The system body to convert into a simpleC AST.
	 * @param valueType The type of values stored by Alpha variables.
	 * @param oldAlphaZCompatible If true, compatibility with the older AlphaZ system will be maintained.
	 *     If false, all memory will be linearized.
	 */
	def static convert(SystemBody systemBody, BaseDataType valueType, boolean oldAlphaZCompatible) {
		return (new WriteC(systemBody, valueType, oldAlphaZCompatible)).convertSystemBody
	}
	
	
	/////////////////////////////////////////////////////////////
	// Constructors
	/////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new instance of this class.
	 * @param systemBody The system body to convert into a simpleC AST.
	 * @param valueType The type of values stored by Alpha variables.
	 * @param oldAlphaZCompatible If true, compatibility with the older AlphaZ system will be maintained.
	 *     If false, all memory will be linearized.
	 */
	new(SystemBody systemBody, BaseDataType valueType, boolean oldAlphaZCompatible) {
		this(systemBody, valueType, oldAlphaZCompatible, new WriteCTypeGenerator(valueType, oldAlphaZCompatible))
	}
	
	/**
	 * This is a workaround because the WriteCTypeChecker needs to be passed both to the parent class's
	 * constructor and the expression converter, but Xtend wouldn't allow that type checker to be
	 * created before "super(...)" is called.
	 */
	private new(SystemBody systemBody, BaseDataType valueType, boolean oldAlphaZCompatible, WriteCTypeGenerator typeGenerator) {
		super(systemBody, new AlphaNameChecker(true), typeGenerator, true)
		this.oldAlphaZCompatible = oldAlphaZCompatible
		this.exprConverter = new WriteCExprConverter(typeGenerator, nameChecker, program)
	}
	
	
	/////////////////////////////////////////////////////////////
	// Memory Macros
	/////////////////////////////////////////////////////////////
	
	/** Allocates memory for the given variable. */
	override declareMemoryMacro(Variable variable) {
		val name = nameChecker.getVariableStorageName(variable)
		
		// If we need compatibility with the older AlphaZ, then
		// inputs and outputs need to be accessed that way.
		// Otherwise, use linearized memory.
		if (oldAlphaZCompatible && (variable.isInput || variable.isOutput)) {
			declareCompatibleMemoryMacro(name, variable.domain)
		} else {
			declareLinearMemoryMacro(name, variable.domain)
		}
	}
	
	/** Allocates memory for the flags variable associated with the given variable. */
	override declareFlagMemoryMacro(Variable variable) {
		// Flag variables don't need to worry about compatibility
		// with the older AlphaZ system.
		val name = nameChecker.getFlagName(variable)
		declareLinearMemoryMacro(name, variable.domain)
	}
	
	/**
	 * Declares a memory macro for accessing a linearized array of memory
	 * as if it's a multi-dimensional variable. The macro's name is the
	 * same as the variable's name.
	 */
	def protected declareLinearMemoryMacro(String variableName, ISLSet domain) {
		val rank = MemoryUtils.rank(domain)
		val accessExpression = PolynomialConverter.convert(rank)
		val macroReplacement = Factory.arrayAccessExpr(variableName, accessExpression)
		val macroStmt = Factory.macroStmt(variableName, domain.indexNames, macroReplacement)
		program.addMemoryMacro(macroStmt)
	}
	
	/**
	 * Declares a memory macro for accessing memory allocated by the older AlphaZ system.
	 * The macro's name is the same as the variable's name.
	 * Note: this is known to not work if the given domain is allowed to have negative indices,
	 * but this is how the older AlphaZ generated its code.
	 */
	def protected declareCompatibleMemoryMacro(String variableName, ISLSet domain) {
		val arrayAccess = Factory.arrayAccessExpr(variableName, domain.indexNames)
		val macro = Factory.macroStmt(variableName, domain.indexNames, arrayAccess)
		program.addMemoryMacro(macro)
	}
	
	
	/////////////////////////////////////////////////////////////
	// Eval Functions
	/////////////////////////////////////////////////////////////
	
	/** Throws an exception, as UseEquations are not supported. */
	override declareEvaluation(UseEquation equation) {
		throw new UnsupportedOperationException("Use equations are not currently supported.")
	}
	
	/** Declares the "eval" function used to evaluate each variable per the given equation. */
	override declareEvaluation(StandardEquation equation) {
		
		// Start building a static, non-inlined function.
		val returnType = typeGenerator.getAlphaValueType(equation.variable)
		val evalName = nameChecker.getVariableReadName(equation.variable)
		val evalBuilder = program.startFunction(true, false, returnType, evalName)
		
		// Add a function parameter for each index of the variable's domain.
		val indexNames = equation.expr.contextDomain.indexNames
		indexNames.forEach[evalBuilder.addParameter(typeGenerator.indexType, it)]
		
		// Add the "if" statement that checks the flag variable and evaluates
		// the actual variable if needed, then return the value of the variable.
		val flagCheckingBlock = equation.getFlagCheckingBlock
		evalBuilder.addComment("Check the flags.")
			.addStatement(flagCheckingBlock)
			.addEmptyLine
			.addReturn(equation.identityAccess(false))
		
		// Fractal simplifications are handled specially
		if (!equation.variable.involvesFractalReduction) {
			program.addFunction(evalBuilder.instance)
		}
	}
	
	/**
	 * Constructs the "if" statement block that checks the status of the flags variable and
	 * evaluates the variable if needed, or reports a self-dependence if detected.
	 */
	def protected getFlagCheckingBlock(StandardEquation equation) {
		// For readability of this code, here is the statement that actually assigns
		// the variable being computed.
		val computeValue = exprConverter.convertExpr(equation.expr)
		val computeAndStore = Factory.assignmentStmt(equation.identityAccess(false), computeValue)
		
		// If the flags indicate the value hasn't been evaluated yet:
		//     Set the flag to "in progress"
		//     Evaluate the variable and save its value
		//     Set the flag to "evaluated"
		// Else if the flag indicates the value is "in progress":
		//     Let the user know there is a self dependence
		//     Kill the program
		return IfStmtBuilder
			.start(equation.ifFlagEquals(FlagStatus.NOT_EVALUATED))
			.addStatement(
				equation.setFlagTo(FlagStatus.IN_PROGRESS),
				computeAndStore,
				equation.setFlagTo(FlagStatus.EVALUATED)
			)
			.startElseIf(equation.ifFlagEquals(FlagStatus.IN_PROGRESS))
			.addStatement(
				equation.selfDependencePrintfStmt,
				Factory.exitCall(-1)
			)
			.instance
	}
	
	/** Gets the expression used to access a variable (or its flag). */
	def protected identityAccess(StandardEquation equation, boolean accessFlags) {
		val name = accessFlags ? nameChecker.getFlagName(equation.variable) : equation.variable.name
		return Factory.callExpr(name, equation.expr.contextDomain.indexNames)
	}
	
	/** Gets the expression to check if a flags variable is set to a given value. */
	def protected ifFlagEquals(StandardEquation equation, FlagStatus flagStatus) {
		return Factory.binaryExpr(BinaryOperator.EQ, equation.identityAccess(true), AlphaBaseHelpers.toExpr(flagStatus))
	}
	
	/** Gets the statement that sets a flags variable to a given value. */
	def protected setFlagTo(StandardEquation equation, FlagStatus flagStatus) {
		Factory.assignmentStmt(equation.identityAccess(true), AlphaBaseHelpers.toExpr(flagStatus))
	}
	
	/** Gets the "printf" statement that tells the user a self dependence was found (and where it was). */
	def protected static getSelfDependencePrintfStmt(StandardEquation equation) {
		val locationFormat = (0 ..< equation.expr.contextDomain.nbIndices).map["%ld"].join(",")
		val message = '''"There is a self dependence on «equation.variable.name» at («locationFormat»)\n"'''
		return Factory.printfCall(message, equation.expr.contextDomain.indexNames)
	}
	
	
	/////////////////////////////////////////////////////////////
	// Memory Allocation
	/////////////////////////////////////////////////////////////
	
	/** Allocates memory for the given variable. */
	override allocateVariable(Variable variable) {
		// Note: only local variables will be allocated, so we don't
		// need to worry about compatibility with old AlphaZ system.
		val name = nameChecker.getVariableStorageName(variable)
		val dataType = typeGenerator.getAlphaVariableType(variable)
		allocateLinearMemory(name, variable.domain, dataType)
	}
	
	/** Allocates memory for the flags variable associated with the given variable. */
	override allocateFlagsVariable(Variable variable) {
		val name = nameChecker.getFlagName(variable)
		val dataType = typeGenerator.getFlagVariableType(variable)
		allocateLinearMemory(name, variable.domain, dataType)
		
		// After allocating the flag variable, use "memset" to initialize all the values.
		val nameExpr = Factory.customExpr(name)
		val initialValue = AlphaBaseHelpers.toExpr(FlagStatus.NOT_EVALUATED)
		val cardinalityExpr = variable.domain.cardinalityExpr
		val memsetCall = Factory.callStmt("memset", nameExpr, initialValue, cardinalityExpr)
		entryPoint.addStatement(memsetCall)
	}
	
	/** Adds statements to the entry point function which allocate linearized for a variable. */
	def protected void allocateLinearMemory(String name, ISLSet domain, DataType dataType) {
		allocatedVariables.add(name)
		
		// Call "malloc" to allocate memory and assign it to the variable.
		val cardinalityExpr = domain.cardinalityExpr
		val mallocCall = Factory.mallocCall(dataType, cardinalityExpr)
		val mallocAssignment = Factory.assignmentStmt(name, mallocCall)
		entryPoint.addStatement(mallocAssignment)
		
		// Call our custom "checkMalloc" macro function to check if malloc succeeded
		// and terminate the program if it didn't.
		val nameStringExpr = Factory.customExpr('''"«name»"''')
		val mallocCheckCall = Factory.callStmt("mallocCheck", Factory.customExpr(name), nameStringExpr)
		entryPoint.addStatement(mallocCheckCall)
	}
	
	/** Gets the simpleC expression for the cardinality of the given domain. */
	def static getCardinalityExpr(ISLSet domain) {
		val cardinalityPolynomial = BarvinokBindings.card(domain)
		return PolynomialConverter.convert(cardinalityPolynomial)
	}
	
	
	/////////////////////////////////////////////////////////////
	// Entry Point's Main Loops
	/////////////////////////////////////////////////////////////
	
	def protected involvesFractalReduction(Variable variable) {
		val equation = systemBody.standardEquations.findFirst[eq | eq.variable == variable]
		if (equation === null)
			return false
		val expr = equation.expr
		if (!(expr instanceof ReduceExpression))
			return false
		val re = expr as ReduceExpression
		re.fractalSimplify
	}
	
	/**
	 * Generates the loops of the entry point that evaluate all points of each output.
	 */
	override performEvaluations() {
		val fractalOutputs = systemBody.system.outputs.filter[involvesFractalReduction]
		val nonFractalOutputs = systemBody.system.outputs.reject[involvesFractalReduction]
		fractalOutputs.forEach[evaluateFractalPoints]
		nonFractalOutputs.forEach[evaluateAllPoints]
		entryPoint.addEmptyLine
	}
	
	def protected evaluateFractalPoints(Variable variable) {
		entryPoint.addComment('Evaluate fractal reduction outputs')
		// Add a statement that simply calls the "freduce" function.
		val fractalReduceName = getFractalReduceFunctionName(variable)
		
		val eq = systemBody.standardEquations.findFirst[eq | eq.variable == variable]
		val varExprs = EcoreUtil2.getAllContentsOfType(eq.expr, VariableExpression)
		if (varExprs.size != 1)
			throw new Exception('This implementation only supports a single variable expression in the reduction body') 
		val rhsVar = varExprs.get(0).variable.name 
		val params = systemBody.system.parameterDomain.paramNames
		
		val lhsVar = variable.name
		val lhsVarCopy = variable.name + '_copy'
		
		val callFractalReduce = Factory.callExpr(fractalReduceName, params + #[lhsVar, lhsVarCopy, rhsVar])
		val exprStmt = Factory.expressionStmt(callFractalReduce)
		entryPoint.addStatement(exprStmt)
	}
	
	def protected getFractalReduceFunctionName(Variable variable) {
		'freduce_' + variable.name
	}
	
	/** Evaluates all the points within an output variable. */
	def protected evaluateAllPoints(Variable variable) {
		entryPoint.addComment("Evaluate all non fractal reduction outputs.")
		// Get a unique name for the macro that is called inside the loop.
		var String macroName
		do {
			macroName = "S" + nextStatementId
			nextStatementId += 1
		} while (nameChecker.isGlobalOrKeyword(macroName))
		
		// Add a macro that simply calls the "eval" function.
		// The loop nest generated by ISL will call this macro.
		val evalName = nameChecker.getVariableReadName(variable)
		val callEval = Factory.callExpr(evalName, variable.domain.indexNames)
		val macro = Factory.macroStmt(macroName, variable.domain.indexNames, callEval)
		entryPoint.addStatement(macro)
		
		// We will have ISL create a loop nest that visits all points in their lexicographic order.
		// Any loop variables used need to also be declared.
		val islAST = LoopGenerator.generateLoops(macroName, variable.domain)
		val loopResult = ASTConverter.convert(islAST)
		val loopVariables = loopResult.declarations
			.map[Factory.variableDecl(typeGenerator.indexType, it)]
			.toArrayList
		
		entryPoint.addVariable(loopVariables)
			.addStatement(loopResult.statements)
		
		// Undefine the macro now that we're done with it.
		entryPoint.addStatement(Factory.undefStmt(macroName))
	}
	
	override void addEntryPoint() {
		// For all Alpha parameters and variables provided by the wrapper
		// program (i.e., inputs and outputs), declare an argument for it
		// and add a statement that copies it to the global variable.
		val system = systemBody.system
		val parameters = system.parameterDomain.paramNames
		parameters.forEach[declareSystemParameterArgument]		
		system.inputs.forEach[declareSystemVariableArgument]
		system.outputs.forEach[declareSystemVariableArgument]
		
		entryPoint.addComment("Copy arguments to the global variables.")
		parameters.forEach[copySystemParameter]
		system.inputs.forEach[copySystemVariable]
		system.outputs.forEach[copySystemVariable]
		entryPoint.addEmptyLine
		
		// Add statements to verify that the Alpha system parameters
		// are valid for the system body.
		checkParameters
		
		// Add statements to allocate memory for local variables.
		// If cycle detection is being done, also allocate the flags variables
		// for all variables computed by the system body (i.e., outputs and locals).
		entryPoint.addComment("Allocate memory for local storage.")
		system.locals.forEach[allocateVariable]
		systemBody.system.variables.filter[involvesFractalReduction]
			.map[copyAE]
			.map[v | 
				v.name = v.name + '_copy'
				v
			]
			.forEach[allocateVariable]
		entryPoint.addEmptyLine
		
		entryPoint.addComment("Allocate and initialize flag variables.")
		if (cycleDetection) {
			system.outputs.forEach[allocateFlagsVariable]
			system.locals.forEach[allocateFlagsVariable]
		}
		entryPoint.addEmptyLine
		
		// Add the statements that cause all necessary evaluation to occur.
		performEvaluations
		
		// Finally, free all allocated variables and add the entry point to the program.
		freeAllocatedVariables
		program.addFunction(entryPoint.instance)
	} 
	
	override Program convertSystemBody() throws UnsupportedOperationException {
		// Preprocess the system body so it can be converted to a simpleC AST,
		// then extract all the variables from the equations.
		preprocess

		// Add the defaults to the program.
		addDefaultHeaderComment
		addDefaultIncludes
		addDefaultFunctionMacros
		
		// Declare memory macros for all Alpha variables.
		val system = systemBody.system
		val parameters = system.parameterDomain.paramNames
		val fractalVars = system.variables.filter[involvesFractalReduction]
			.map[copyAE]
			.map[v | 
				v.name = v.name + '_copy'
				v
			]
		// Declare global variables for all Alpha parameters and variables.
		parameters.forEach[declareParameter]
		(system.variables + fractalVars).forEach[declareGlobalVariable]
		(system.variables + fractalVars).forEach[declareMemoryMacro]
		
		// If cycle detection is being performed, generate the flag variables
		// and memory macros for the Alpha variables being computed.
		// That is, outputs and locals.
		if (cycleDetection) {
			system.outputs.forEach[declareFlagVariable]
			system.outputs.forEach[declareFlagMemoryMacro]
			
			system.locals.forEach[declareFlagVariable]
			system.locals.forEach[declareFlagMemoryMacro]
		}
		
		// Declare how to evaluate each equation.
		systemBody.standardEquations.forEach[declareEvaluation]
		systemBody.useEquations.forEach[declareEvaluation]
		
		// Add the entry point to the program, then we're done!
		addEntryPoint
		return program.instance
	}
}