package alpha.codegen.alphaBase

import alpha.codegen.BaseDataType
import alpha.codegen.Factory
import alpha.codegen.FunctionBuilder
import alpha.codegen.IfStmtBuilder
import alpha.codegen.Program
import alpha.codegen.ProgramBuilder
import alpha.codegen.UnaryOperator
import alpha.codegen.isl.ConditionalConverter
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.transformation.Normalize
import alpha.model.transformation.StandardizeNames
import alpha.model.transformation.reduction.NormalizeReduction
import java.util.ArrayList

abstract class CodeGeneratorBase {
	/////////////////////////////////////////////////////////////////
	// Fields
	/////////////////////////////////////////////////////////////////
	
	/** The system body being converted into a "simpleC" AST. */
	protected val SystemBody systemBody
	
	/** The name checker to use to ensure no duplicate names are produced. */
	protected val AlphaNameChecker nameChecker
	
	/** Generates data types for Alpha variables. */
	protected val TypeGeneratorBase typeGenerator
	
	/** If true, the flags variables used for cycle detection will be generated. Otherwise, the won't be. */
	protected val boolean cycleDetection
	
	/** The builder for the program representing this system body. */
	protected val ProgramBuilder program
	
	/** The builder for the entry point to the program. */
	protected val FunctionBuilder entryPoint
	
	/** The list of all variables allocated by the entry point function which need to be freed before returning. */
	protected val ArrayList<String> allocatedVariables
	
	/////////////////////////////////////////////////////////////////
	// Construction
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new instance of this class.
	 * 
	 * @param systemBody    The system body to convert into a "simpleC" AST. This may be modified.
	 * @param nameChecker   The name checker to use to ensure no duplicate names are produced.
	 * @param typeGenerator Generates data types for Alpha variables.
	 * @param cycleDetection If true, the flags variables used for cycle detection will be generated. Otherwise, the won't be.
	 * @throws IllegalArgumentException If the link from the system body to the parent Alpha system is null. This can happen
	 *         if the "alpha.model.util.AlphaUtil.copyAE" function was used to copy only the system body, and not the entire system.
	 */
	new(SystemBody systemBody, AlphaNameChecker nameChecker, TypeGeneratorBase typeGenerator, boolean cycleDetection) {
		this.systemBody = systemBody
		this.nameChecker = nameChecker
		this.typeGenerator = typeGenerator
		this.cycleDetection = cycleDetection
		
		this.program = ProgramBuilder.start(nameChecker)
		this.entryPoint = program.startFunction(BaseDataType.VOID, systemBody.system.name)
		this.allocatedVariables = newArrayList
	}
	
	/////////////////////////////////////////////////////////////////
	// Top-Level Convert Methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Converts the given system into a "simpleC" Program instance.
	 * 
	 * @throws An UnsupportedOperationException is thrown if the system cannot
	 *         be converted. The reason for this should be populated into the
	 *         exception message.
	 */
	def Program convertSystemBody() throws UnsupportedOperationException {
		// Preprocess the system body so it can be converted to a simpleC AST,
		// then extract all the variables from the equations.
		preprocess

		// Add the defaults to the program.
		addDefaultHeaderComment
		addDefaultIncludes
		addDefaultFunctionMacros
		
		// Declare global variables for all Alpha parameters and variables.
		// Declare memory macros for all Alpha variables.
		val system = systemBody.system
		val parameters = system.parameterDomain.paramNames
		parameters.forEach[declareParameter]
		system.variables.forEach[declareGlobalVariable]
		system.variables.forEach[declareMemoryMacro]
		
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
	
	/** Adds the entry point to the program. */
	def void addEntryPoint() {
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
	
	
	/////////////////////////////////////////////////////////////////
	// Setup and Default Elements
	/////////////////////////////////////////////////////////////////
	
	/** Normalizes the system body and standardizes all names prior to conversion. */
	def void preprocess() {
		Normalize.apply(systemBody)
		NormalizeReduction.apply(systemBody)
		StandardizeNames.apply(systemBody)
	}
	
	/** Adds the header comment lines to add to the program. */
	def void addDefaultHeaderComment() {
		program.addHeaderComment("This code was auto-generated with AlphaZ.")
	}
	
	/** Adds the "#include" directives to add to the program. */
	def void addDefaultIncludes() {
		program.addInclude(#[
			"float.h",
			"limits.h",
			"math.h",
			"stdbool.h",
			"stdio.h",
			"stdlib.h",
			"string.h"
		].map[Factory.include(it)])
	}
	
	/** Adds the "#define" function macros to add to the program. */
	def void addDefaultFunctionMacros() {
		program.addFunctionMacro(#[
			Factory.macroStmt("ceild", #["n", "d"], "((int)ceil(((double)(n))/((double)(d))))"),
			Factory.macroStmt("floord", #["n", "d"], "((int)floor(((double)(n))/((double)(d))))"),
			Factory.macroStmt("div", #["a", "b"], "(ceild((a),(b)))"),
			Factory.macroStmt("max", #["a", "b"], "(((a)>(b))?(a):(b))"),
			Factory.macroStmt("min", #["a", "b"], "(((a)<(b))?(a):(b))"),
			Factory.macroStmt("mallocCheck", #["v", "s"], "if ((v) == NULL) { printf(\"Failed to allocate memory for variable: %s\\n\", (s)); exit(-1); }")
		])
	}
	
	
	/////////////////////////////////////////////////////////////////////////////
	// Global Variables and Memory Macros
	/////////////////////////////////////////////////////////////////////////////
	
	/** Declares a global variable for the given parameter. */
	def void declareParameter(String systemParameter) {
		val name = nameChecker.getParameterStorageName(systemParameter)
		program.addGlobalVariable(true, typeGenerator.indexType, name)
	}
	
	/** Declares a global variable for the given Alpha variable. */
	def void declareGlobalVariable(Variable variable) {
		val dataType = typeGenerator.getAlphaVariableType(variable)
		val name = nameChecker.getVariableStorageName(variable)
		program.addGlobalVariable(true, dataType, name)
	}
	
	/** Declares a memory macro for the given Alpha variable. */
	def void declareMemoryMacro(Variable variable)
	
	/**
	 * Declares a global variable for the "flags" variable
	 * associated with the given Alpha variable.
	 */
	def void declareFlagVariable(Variable variable) {
		val dataType = typeGenerator.getFlagVariableType(variable)
		val name = nameChecker.getFlagName(variable)
		program.addGlobalVariable(true, dataType, name)
	}
	
	/**
	 * Declares a memory macro for the "flags" variable
	 * associated with the given Alpha variable.
	 */
	def void declareFlagMemoryMacro(Variable variable)
	
	
	/////////////////////////////////////////////////////////////////////////////
	// Evaluation Function Declarations
	/////////////////////////////////////////////////////////////////////////////
	
	/** Declares whatever is needed (macro, function, etc.) for evaluating the given equation. */
	def void declareEvaluation(StandardEquation equation)
	
	/** Declares whatever is needed (macro, function, etc.) for evaluating the given equation. */
	def void declareEvaluation(UseEquation equation)
	
	
	/////////////////////////////////////////////////////////////////////////////
	// Entry Point Function Declaration
	/////////////////////////////////////////////////////////////////////////////
	
	/** Creates a function argument for the given Alpha system parameter. */
	def void declareSystemParameterArgument(String systemParameter) {
		val dataType = typeGenerator.indexType
		val name = nameChecker.getParameterArgumentName(systemParameter)
		entryPoint.addParameter(dataType, name)
	}
	
	/** Creates a function argument for the given Alpha variable. */
	def void declareSystemVariableArgument(Variable variable) {
		val dataType = typeGenerator.getAlphaVariableType(variable)
		val name = nameChecker.getVariableArgumentName(variable)
		entryPoint.addParameter(dataType, name)
	}
	
	/**
	 * Creates a statement to copy the Alpha system parameter from the
	 * entry point's argument to the associated global variable.
	 */
	def void copySystemParameter(String systemParameter) {
		val argName = nameChecker.getParameterArgumentName(systemParameter)
		val storageName = nameChecker.getParameterStorageName(systemParameter)
		val assignment = Factory.assignmentStmt(storageName, argName)
		entryPoint.addStatement(assignment)
	}
	
	/**
	 * Copies the Alpha variable from the entry point's argument
	 * to the associated global variable.
	 */
	def void copySystemVariable(Variable variable) {
		val argName = nameChecker.getVariableArgumentName(variable)
		val storageName = nameChecker.getVariableStorageName(variable)
		val assignment = Factory.assignmentStmt(storageName, argName)
		entryPoint.addStatement(assignment)
	}
	
	/**
	 * Creates the statements that check if the values of the Alpha system parameters
	 * are valid for this system body.
	 */
	def void checkParameters() {
		// If none of the basic sets in the parameter domain have any constraints
		// (i.e., the entire set is unconstrained), there is no need to produce an
		// "if" statement to check if we're in the domain.
		if (systemBody.parameterDomain.basicSets.forall[it.nbConstraints == 0]) {
			return
		}
		
		entryPoint.addComment("Check parameter validity.")
		
		val isValid = ConditionalConverter.convert(systemBody.parameterDomain)
		val parameterCheck = IfStmtBuilder
			.start(Factory.unaryExpr(UnaryOperator.NOT, isValid))
			.addStatement(
				Factory.callStmt("printf", "\"The value of the parameters are invalid.\\n\""),
				Factory.callStmt("exit", "-1")
			)
			.instance
		entryPoint.addStatement(parameterCheck)
		
		entryPoint.addEmptyLine
	}
	
	/** Allocates memory for the given variable. */
	def void allocateVariable(Variable variable)
	
	/** Allocates memory for the flags variable associated with the given variable. */
	def void allocateFlagsVariable(Variable variable)
	
	/**
	 * Generates the loops of the entry point which perform all the evaluations
	 * necessary for the Alpha system to do what it is intended to do.
	 */
	def void performEvaluations()
	
	/** Frees all allocated variables. */
	def void freeAllocatedVariables() {
		entryPoint.addComment("Free all allocated memory.")
		
		val freeStmts = allocatedVariables.map[Factory.callStmt("free", it)]
		entryPoint.addStatement(freeStmts)
	}
}