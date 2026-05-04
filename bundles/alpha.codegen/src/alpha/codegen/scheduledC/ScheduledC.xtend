package alpha.codegen.scheduledC

import alpha.codegen.ArrayAccessExpr
import alpha.codegen.AssignmentStmt
import alpha.codegen.BaseDataType
import alpha.codegen.BinaryOperator
import alpha.codegen.CodegenOptions
import alpha.codegen.DataType
import alpha.codegen.Factory
import alpha.codegen.alphaBase.AlphaBaseHelpers
import alpha.codegen.alphaBase.AlphaNameChecker
import alpha.codegen.alphaBase.CodeGeneratorBase
import alpha.codegen.isl.ASTConversionResult
import alpha.codegen.isl.ASTConverter
import alpha.codegen.isl.AffineConverter
import alpha.codegen.isl.LoopGenerator
import alpha.codegen.isl.MemoryUtils
import alpha.codegen.isl.PolynomialConverter
import alpha.codegen.postprocessing.ForLoopNester
import alpha.codegen.postprocessing.OmpPragmaInserter
import alpha.model.AlphaSystem
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.memorymapper.MemoryMapper
import alpha.model.scheduler.Scheduler
import alpha.model.tiler.Tiler
import alpha.model.transformation.ChangeOfBasis
import alpha.model.transformation.Normalize
import alpha.model.transformation.StandardizeNames
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import java.util.HashMap
import java.util.List
import java.util.Map

import static extension alpha.codegen.alphaBase.AlphaBaseHelpers.getOperator
import static extension alpha.model.util.AlphaUtil.*
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.ISLUtil.*
import alpha.model.util.ISLUtil
import alpha.codegen.MacroStmt
import alpha.codegen.FunctionBuilder

class ScheduledC extends CodeGeneratorBase {
	
	/** The next ID to use as a statement macro. */
	protected var int nextStatementId = 0
	
	/** Converts Alpha expressions to simpleC expressions. */
	protected val ScheduledExprConverter exprConverter
	
	/** An object that returns the schedule of the outputted C code */
	protected val Scheduler scheduler
	
	/** An object that contains memory maps to apply to each variable */
	protected val MemoryMapper mapper
	
	/** An optional object that contains a tile map */
	protected val Tiler tiler
	
	/** The variable assignemnt statements that have been generated, for use in macro generation */
	protected var Map<String, AssignmentStmt> variableStatements
	
	new(SystemBody systemBody, ScheduledTypeGenerator typeGen, AlphaNameChecker nameChecker, Scheduler scheduler, CodegenOptions options) {
		super(systemBody, nameChecker, typeGen, options)

		this.tiler = options.tiler
		this.mapper = options.mapper
		this.scheduler = scheduler
		this.exprConverter = new ScheduledExprConverter(typeGen, nameChecker, program, scheduler, options)
		this.variableStatements = new HashMap()
	}
	
	/** 
	 * Normalizes the system body and standardizes all names prior to conversion. 
	 * Overide the preprocess step to not normalize reductions 
	 */
	override void preprocess() {
		Normalize.apply(systemBody)
		StandardizeNames.apply(systemBody)
	}
	
	/** Constructs an equality constraint that index i equals the parameter for that index. */
	def private static addTotalOrderEquality(ISLSet domain, int originalParamCount, int index) {
		val constraint = ISLConstraint.buildEquality(domain.space)
			.setCoefficient(ISLDimType.isl_dim_param, originalParamCount + index, 1)
			.setCoefficient(ISLDimType.isl_dim_set, index, -1)
		
		return domain.addConstraint(constraint)
	}
	
	/** Constructs an inequality that index i is less than the parameter for that index. */
	def private static addTotalOrderInequality(ISLSet domain, int originalParamCount, int index) {
		val constraint = ISLConstraint.buildInequality(domain.space)
			.setCoefficient(ISLDimType.isl_dim_param, originalParamCount + index, 1)
			.setCoefficient(ISLDimType.isl_dim_set, index, -1)
			.setConstant(-1)
		
		return domain.addConstraint(constraint)
	}
	
	def static createOrderingForIndex(ISLSet domain, ISLMap map, int originalParamCount, int index, String name) {
		(0 ..< index)
			.fold(domain.copy, [d, i | d.addTotalOrderEquality( originalParamCount, i)])
			.addTotalOrderInequality( originalParamCount, index)
	}
	
	override declareMemoryMacro(Variable variable) {
		// Create the basic memory macro
		// This macro will map from a point to the correct array access
		val name = nameChecker.getVariableStorageName(variable)
		val memoryName = "mem_" + name
		var ISLMap memoryMap = mapper.getMemoryMap(variable)
		var storageName = mapper.getDestination(variable) ?: nameChecker.getVariableStorageName(variable)
		var ISLSet domain = variable.domain.copy
		val names = domain.indexNames;
		
		memoryMap = (0..memoryMap.dim(ISLDimType.isl_dim_out)-1).fold(memoryMap, [ map, i |
			map.setDimName(ISLDimType.isl_dim_out, i, "i" + i);
		])
		
		val ISLMap mappedDomain = memoryMap
			.copy
			.intersectDomain(domain.copy)
			.renameInputs(names)
			.setTupleName(ISLDimType.isl_dim_in, memoryName)

		val memoryDomain = domain.copy.apply(memoryMap.copy)
		
		val rank = options.polyhedralMemory ? 
			MemoryUtils.rank(memoryDomain) : MemoryUtils.boxRank(memoryDomain)
		val accessExpression = PolynomialConverter.convert(rank)
		val ArrayAccessExpr macroReplacement = Factory.arrayAccessExpr(storageName, accessExpression)
		val macroStmt = Factory.macroStmt(memoryName, memoryDomain.indexNames, macroReplacement)
		
		// The mapped macro statement applies any memory map and then
		// calls the appropriate mem_ macro
		val indexExprs = AffineConverter.convertMultiAff(toMultiAff(mappedDomain))
		val statement = Factory.callExpr(memoryName, indexExprs)
		val mappedMacroStatement = Factory.macroStmt(name, domain.indexNames, statement)
		
		
		program.addMemoryMacro(macroStmt)
		program.addMemoryMacro(mappedMacroStatement)
	}
	
	override declareReductionMemoryMacro(ReduceExpression re) {
		val variable = (re.getContainerEquation as StandardEquation).variable
		
		val name = re.getReductionName
		val memoryName = "mem_" + name
		var ISLMap memoryMap = re.projectionExpr.ISLMultiAff.toMap
			.applyRange(mapper.getMemoryMap(variable))
		var storageName = name
		var ISLSet domain = re.body.contextDomain.copy
		val names = re.body.contextDomain.indexNames
		
		memoryMap = (0..memoryMap.dim(ISLDimType.isl_dim_out)-1).fold(memoryMap, [ map, i |
			map.setDimName(ISLDimType.isl_dim_out, i, "i" + i);
		])
		
		val ISLMap mappedDomain = memoryMap
			.copy
			.intersectDomain(domain.copy)
			.renameInputs(names)
			.setTupleName(ISLDimType.isl_dim_in, memoryName)

		val memoryDomain = domain.copy.apply(memoryMap.copy)
		
		val rank = options.polyhedralMemory ? 
			MemoryUtils.rank(memoryDomain) : MemoryUtils.boxRank(memoryDomain)
		val accessExpression = PolynomialConverter.convert(rank)
		val ArrayAccessExpr macroReplacement = Factory.arrayAccessExpr(storageName, accessExpression)
		val macroStmt = Factory.macroStmt(memoryName, memoryDomain.indexNames, macroReplacement)
		
		// The mapped macro statement applies any memory map and then
		// calls the appropriate mem_ macro
		val indexExprs = AffineConverter.convertMultiAff(toMultiAff(mappedDomain))
		val statement = Factory.callExpr(memoryName, indexExprs)
		val mappedMacroStatement = Factory.macroStmt(name, domain.indexNames, statement)
		
		
		program.addMemoryMacro(macroStmt)
		program.addMemoryMacro(mappedMacroStatement)
	}
	
	override declareFlagMemoryMacro(Variable variable) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override declareEvaluation(StandardEquation equation) {
		// Start building a static, non-inlined function.
		val returnType = Factory.dataType(BaseDataType.VOID)
		val evalName = nameChecker.getVariableReadName(equation.variable)
		
		// Add a function parameter for each index of the variable's domain.
		val indexNames = equation.expr.contextDomain.indexNames
		
		exprConverter.target = equation.name

		val computeValue = exprConverter.convertExpr(equation.expr)
		val computeAndStore = Factory.assignmentStmt(equation.identityAccess(false), computeValue)
		
		exprConverter.target = ""
		
		declareStatementEvaluation(evalName, returnType, indexNames, computeAndStore)
	}
	
	override declareReductionEvaluation(ReduceExpression re) {
		val returnType = Factory.dataType(BaseDataType.VOID)
		val evalName = re.reductionName
		
		val indexNames = re.body.contextDomain.indexNames
		
		exprConverter.target = evalName

		val memValue = Factory.callExpr(evalName, indexNames)
		
		val op = re.operator.getOperator
		val computeValue = Factory.binaryExpr(op, memValue, exprConverter.convertExpr(re.body))
		val computeAndStore = Factory.assignmentStmt(Factory.callExpr(evalName, indexNames), computeValue)
		
		exprConverter.target = ""
		
		declareStatementEvaluation(evalName, returnType, indexNames, computeAndStore)
	}
	
	def protected void declareStatementEvaluation(String evalName, DataType returnType, Iterable<String> indexNames, AssignmentStmt computeAndStore) {
		val evalBuilder = program.startFunction(true, options.inlineFunction, returnType, "eval_" + evalName)
		
		indexNames.forEach[evalBuilder.addParameter(typeGenerator.indexType, it)]
		
		// If we want to inline the code fully then we won't add a new function
		// Instead we will store the variable assignment generated using the expression converter
		// To be used later when generating code in the evaluateAllPoints function
		evalBuilder.addStatement(computeAndStore)
		if(!options.inlineCode) {
			program.addFunction(evalBuilder.instance)
		} else {
			variableStatements.put(evalName, computeAndStore)
		}
	}
		
	/** Gets the expression used to access a variable (or its flag). */
	def protected identityAccess(StandardEquation equation, boolean accessFlags) {
		return Factory.callExpr(equation.variable.name, equation.expr.contextDomain.indexNames)
	}
	
	override declareEvaluation(UseEquation equation) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	/** Allocates memory for a standard variable. */
	override allocateVariable(Variable variable) {
		// Note: only local variables will be allocated, so we don't
		// need to worry about compatibility with old AlphaZ system.
		val name = this.mapper.getDestination(variable) ?: nameChecker.getVariableStorageName(variable)
		val dataType = typeGenerator.getAlphaVariableType(variable)
		
		allocatedVariables.add(name)
		
		val mappedDomain = variable.domain.apply(mapper.getMemoryMap(variable))
		
		allocateMemory(name, dataType, mappedDomain)
	}
	
	/** Allocates memory for a reduction body. */
	override allocateReduction(ReduceExpression re) {
		val variable = (re.getContainerEquation as StandardEquation).variable
		val domain = re.body.contextDomain.copy
		
		val name = re.reductionName
		val dataType = typeGenerator.getAlphaVariableType(variable)
		
		val memoryMap = re.projectionExpr.ISLMultiAff.toMap
			.applyRange(mapper.getMemoryMap(variable))
		val mappedDomain = domain.apply(memoryMap)
		
		allocateMemory(name, dataType, mappedDomain)
	}
	
	/** Helper for allocating reduction and standard variables */
	def protected allocateMemory(String name, DataType dataType, ISLSet mappedDomain) {
		allocatedVariables.add(name)
		
		val cardinalityExpr = mappedDomain.cardinalityExpr
		val mallocCall = Factory.mallocCall(dataType, cardinalityExpr)
		val mallocAssignment = Factory.assignmentStmt(name, mallocCall)
		entryPoint.addStatement(mallocAssignment)
		
		// Call our custom "checkMalloc" macro function to check if malloc succeeded
		// and terminate the program if it didn't.
		val nameStringExpr = Factory.customExpr('''"«name»"''')
		val mallocCheckCall = Factory.callStmt("mallocCheck", Factory.customExpr(name), nameStringExpr)
		entryPoint.addStatement(mallocCheckCall)
	}
	
	override initializeReduction(ReduceExpression re) {
		val variable = (re.getContainerEquation as StandardEquation).variable
		val domain = re.body.contextDomain.copy
		
		val memoryMap = re.projectionExpr.ISLMultiAff.toMap
			.applyRange(mapper.getMemoryMap(variable))
		
		val cardinalityExpr = domain.apply(memoryMap).cardinalityExpr
		
		val conditional = Factory.binaryExpr(BinaryOperator.LT, Factory.customExpr("i"), cardinalityExpr)
		
		val memExpr = Factory.arrayAccessExpr(re.reductionName, "i")
		val initializeStmt = Factory.assignmentStmt(memExpr, AlphaBaseHelpers.getReductionInitialValue(options.valueType, re.operator))

		val loop = Factory.loopStmt("i", Factory.customExpr("0"), conditional, Factory.customExpr("1"), initializeStmt)
		
		
		entryPoint.addVariable(Factory.variableDecl(typeGenerator.indexType, "i"))
		entryPoint.addStatement(loop)
	}
	
	def protected getCardinalityExpr(ISLSet domain) {
		val cardinalityPolynomial = options.polyhedralMemory ?
			MemoryUtils.card(domain) : MemoryUtils.boxCard(domain)
		return PolynomialConverter.convert(cardinalityPolynomial)
	}
	
	override allocateFlagsVariable(Variable variable) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override performEvaluations() {
		entryPoint.addComment("Evaluate all the outputs.")
		var variables = systemBody.system.locals
		variables.addAll(systemBody.system.outputs)
		evaluateAllPoints(variables)
		entryPoint.addEmptyLine
	}
	
	/** Evaluates all the points within an output variable. */
	def protected evaluateAllPoints(List<Variable> variables) {
		var Iterable<String> scheduledVars = variables.map[name]
		
		if(options.scheduledReductions)	{
			scheduledVars = scheduledVars 
				+ systemBody.getContainedReductions.map[reductionName].toList
		}
		
		// We first get all the maps for all the variables from the schedule
		var ISLUnionMap scheduleMaps = scheduledVars
			.map[scheduler.getScheduleMap(it)]
			.toList.convertToUnionMap
		
		// Then we get the domains for all the variables in the schedule
		scheduleMaps = scheduleMaps.intersectDomain(this.scheduler.domains)
		
		
		if(options.inlineCode && tiler === null) {
			scheduleMaps.maps
				.flatMap[buildEvalMacros(variables)]
				.forEach[entryPoint.addStatement(it)]
		} 

		var ISLUnionMap namedScheduleMaps 
		namedScheduleMaps = scheduleMaps.maps
			.map[simplify]
			.map[setInputTupleName("sEval_" + getInputTupleName)]
			.toList.convertToUnionMap
		
		var ASTConversionResult loopResult
		
		//Generate all the loops for variables
		if(tiler !== null) {
			//generate the tile loops
			val tileMaps = tiler.getApproximateOutset(scheduler.ranges)
				.setTupleName("iteratorLoop")
				.toIdentityMap
				.toUnionMap
				
			val tileAST = LoopGenerator.generateLoops(tileMaps.copy.params, tileMaps)
			loopResult = ASTConverter.convert(tileAST)
			
			//generate the iterator loops and put them in a separate function
			val iterMaps = tiler.getParameterizedIterators(namedScheduleMaps.copy.intersectRange(scheduler.ranges))
			
			val iterContext = iterMaps.copy.params
				.dropConstraintsInvolvingDims(ISLUtil.Dims.PARAM, 0, tiler.tiledDims.size)
			
			val iterAST = LoopGenerator.generateLoops(iterContext, iterMaps)
			val iterResult = ASTConverter.convert(iterAST)
			
			val iterFunction = FunctionBuilder.start(BaseDataType.VOID, "iteratorLoop", nameChecker);
			
			val tileParameters = loopResult.declarations
				.toSet
				.map[Factory.parameter(typeGenerator.indexType, it)]
				.toArrayList
			val iterVariables = iterResult.declarations
				.map[Factory.variableDecl(typeGenerator.indexType, it)]
				.toArrayList

			scheduleMaps.maps
				.flatMap[buildEvalMacros(variables)]
				.forEach[iterFunction.addStatement(it)]
				
			iterFunction.addParameter(tileParameters)
				.addVariable(iterVariables)
				.addStatement(iterResult.statements)
			
			program.addFunction(iterFunction.instance)
			
			/*ForLoopNester.apply(loopResult, iterResult, "_")
			loopResult.declarations.addAll(iterResult.declarations)*/
		} else {
			val islAST = LoopGenerator.generateLoops(scheduler.domains.params, namedScheduleMaps)
					
			loopResult = ASTConverter.convert(islAST)
		}
	
			
		//Add parallel pragmas in the appropriate places, if enabled.
		if(options.ompPragmas) {
			val timeDims = systemBody.containerSystem
				.countTimeDimensions(scheduler.maps)
				
			OmpPragmaInserter.apply(loopResult, timeDims)
		}
		
		val loopVariables = loopResult.declarations
			.map[Factory.variableDecl(typeGenerator.indexType, it)]
			.toArrayList
					
		entryPoint.addVariable(loopVariables)
			.addStatement(loopResult.statements)
	}
	
	def protected Iterable<MacroStmt> buildEvalMacros(ISLMap map, Iterable<Variable> variables) {
		val variable = variables.findFirst[v | v.name == map.inputTupleName]
		val evalName = "eval_" + map.inputTupleName
		val shieldedEvalName = "sEval_" + map.inputTupleName
		
		val indexNames = variable !== null ? variable.domain.indexNames
			: systemBody.getReductionByName(map.inputTupleName).body.contextDomain.indexNames
		val parenthesizedIndices = indexNames.map[Factory.parenthesizedExpr(it)]
		
		val evalExpr = variableStatements.get(map.inputTupleName)
		val shieldedExpr = Factory.callExpr(evalName, parenthesizedIndices)
		
		val eval = Factory.macroStmt(evalName, indexNames, evalExpr)
		val shieldedEval = Factory.macroStmt(shieldedEvalName, indexNames, shieldedExpr)
		
		return #[eval, shieldedEval]
	}
	
	def static convert(AlphaSystem system, Scheduler scheduler, CodegenOptions options) {
		if (system.systemBodies.length != 1) {
			throw new IllegalArgumentException("Systems must have exactly one body to be converted directly to WriteC code.")
		}
		var alteredRoot = system.getContainerRoot.copyAE				
		var alteredSystem = alteredRoot.getSystem(system.name)
		Normalize.apply(alteredSystem)

		for(Variable local : alteredSystem.locals) {
			for(ISLMap map : scheduler.maps.maps) {
				if(map.getTupleName(ISLDimType.isl_dim_out) == local.name) {
					ChangeOfBasis.apply(alteredSystem, local, toMultiAff(map))
				}
			}
		}
		
		for(Variable input : alteredSystem.inputs) {
			for(ISLMap map : scheduler.maps.maps) {
				if(map.getTupleName(ISLDimType.isl_dim_in) == input.name) {
					ChangeOfBasis.apply(alteredSystem, input, toMultiAff(map))
				}	
			}
		}
		
		return (new ScheduledC(
			alteredSystem.systemBodies.get(0), new ScheduledTypeGenerator(options.valueType, false), 
			new AlphaNameChecker(false), scheduler, options
		)).convertSystemBody
	}
}