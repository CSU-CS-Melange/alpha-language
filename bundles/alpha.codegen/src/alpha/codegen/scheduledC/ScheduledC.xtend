package alpha.codegen.scheduledC

import alpha.codegen.AssignmentStmt
import alpha.codegen.BaseDataType
import alpha.codegen.Factory
import alpha.codegen.alphaBase.AlphaNameChecker
import alpha.codegen.alphaBase.CodeGeneratorBase
import alpha.codegen.isl.ASTConverter
import alpha.codegen.isl.LoopGenerator
import alpha.codegen.isl.MemoryUtils
import alpha.codegen.isl.PolynomialConverter
import alpha.model.AlphaSystem
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.memorymapper.MemoryMapper
import alpha.model.scheduler.Scheduler
import alpha.model.transformation.ChangeOfBasis
import alpha.model.transformation.Normalize
import alpha.model.transformation.StandardizeNames
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import java.util.HashMap
import java.util.List
import java.util.Map

import static alpha.model.util.ISLUtil.*

import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.CommonExtensions.toArrayList
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import alpha.codegen.isl.AffineConverter

class ScheduledC extends CodeGeneratorBase {
	
	/** The next ID to use as a statement macro. */
	protected var int nextStatementId = 0
	
	/** Converts Alpha expressions to simpleC expressions. */
	protected val ScheduledExprConverter exprConverter
	
	/** An object that returns the schedule of the outputted C code */
	protected val Scheduler scheduler
	
	protected val MemoryMapper mapper
		
	/** Tells the code generator to add the inline keyword to the evaluate function */
	protected val boolean inlineFunction
	
	/** Tells the code generator to manually inline the function (replace the function call with the actual function body */
	protected val boolean inlineCode
	
	/**  */
	
	protected var Map<String, AssignmentStmt> variableStatements
	
	new(SystemBody systemBody, AlphaNameChecker nameChecker, ScheduledTypeGenerator typeGenerator, 
		Scheduler scheduler, MemoryMapper mapper, boolean cycleDetection, boolean inlineFunction,
		boolean inlineCode
	) {
		super(systemBody, nameChecker, typeGenerator, cycleDetection)

		this.exprConverter = new ScheduledExprConverter(typeGenerator, nameChecker, program, scheduler, mapper)
		this.scheduler = scheduler
		this.inlineFunction = inlineFunction
		this.inlineCode = inlineCode
		this.variableStatements = new HashMap()
		this.mapper = mapper
	}
	
	/** Normalizes the system body and standardizes all names prior to conversion. */
	/** Overide the preprocess step to not normalize reductions */
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
	
	def static ISLPWQPolynomial card(ISLSet domain) {
		BarvinokBindings.card(domain.copy)
	}
	
	
	override declareMemoryMacro(Variable variable) {
		// Create the basic memory macro
		// This macro will map from a point to the correct array access
		val name = nameChecker.getVariableStorageName(variable)
		val memoryName = "mem_" + name
		var ISLMap memoryMap = mapper.getMemoryMap(variable)
		var storageName = mapper.getDestination(variable) ?: nameChecker.getVariableStorageName(variable)
		var ISLSet domain = variable.domain.copy
		val names = domain.indexNames
		
		val ISLMap mappedDomain = memoryMap
			.copy
			.intersectDomain(domain.copy)
			.renameInputs(names)
			.setTupleName(ISLDimType.isl_dim_in, memoryName)

		
		val rank = MemoryUtils.rank(domain)
		val accessExpression = PolynomialConverter.convert(rank)
		val macroReplacement = Factory.arrayAccessExpr(storageName, accessExpression)
		val macroStmt = Factory.macroStmt(memoryName, domain.indexNames, macroReplacement)
		
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
		val evalBuilder = program.startFunction(true, this.inlineFunction, returnType, "eval_" + evalName)
		
		// Add a function parameter for each index of the variable's domain.
		val indexNames = equation.expr.contextDomain.indexNames
		indexNames.forEach[evalBuilder.addParameter(typeGenerator.indexType, it)]
		
		/** TODO: Expand to multiple edges */
		exprConverter.target = equation.name

		val computeValue = exprConverter.convertExpr(equation.expr)
		val computeAndStore = Factory.assignmentStmt(equation.identityAccess(false), computeValue)
		
		exprConverter.target = ""
		
		// If we want to inline the code fully then we won't add a new function
		// Instead we will store the variable assignment generated using the expression converter
		// To be used later when generating code in the evaluateAllPoints function
		evalBuilder.addStatement(computeAndStore)
		if(!inlineCode) {
			program.addFunction(evalBuilder.instance)
		} else {
			variableStatements.put(equation.name, computeAndStore)
		}
	}
		
	/** Gets the expression used to access a variable (or its flag). */
	def protected identityAccess(StandardEquation equation, boolean accessFlags) {
		return Factory.callExpr(equation.variable.name, equation.expr.contextDomain.indexNames)
	}
	
	override declareEvaluation(UseEquation equation) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override allocateVariable(Variable variable) {
		// Note: only local variables will be allocated, so we don't
		// need to worry about compatibility with old AlphaZ system.
		val name = this.mapper.getDestination(variable) ?: nameChecker.getVariableStorageName(variable)
		val dataType = typeGenerator.getAlphaVariableType(variable)
		
		allocatedVariables.add(name)
		
		// Call "malloc" to allocate memory and assign it to the variable.
		val cardinalityExpr = variable.domain.apply(mapper.getMemoryMap(variable)).cardinalityExpr
		val mallocCall = Factory.mallocCall(dataType, cardinalityExpr)
		val mallocAssignment = Factory.assignmentStmt(name, mallocCall)
		entryPoint.addStatement(mallocAssignment)
		
		// Call our custom "checkMalloc" macro function to check if malloc succeeded
		// and terminate the program if it didn't.
		val nameStringExpr = Factory.customExpr('''"«name»"''')
		val mallocCheckCall = Factory.callStmt("mallocCheck", Factory.customExpr(name), nameStringExpr)
		entryPoint.addStatement(mallocCheckCall)
		
		
	}
	
	def protected getCardinalityExpr(ISLSet domain) {
		val cardinalityPolynomial = BarvinokBindings.card(domain)
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
		// We first get all the maps for all the variables from the schedule
		var ISLUnionMap scheduleMaps
		for(map : scheduler.maps.maps) {
			for(variable : variables) {
				var name = map.copy.inputTupleName
				if(name == variable.name) {
					if(scheduleMaps === null) {
						scheduleMaps = map.copy.toUnionMap
					} else {
						scheduleMaps = scheduleMaps.copy.addMap(map.copy)
					}
				}
			}
		}
		// Then we get the domains for all the variables in the schedule
		scheduleMaps = scheduleMaps.intersectDomain(this.scheduler.domains)
		
		// This next loop goes through and makes sure that the indices and input names
		// are correct in ISL, so the isl codegenerator calls the correct code
		var ISLUnionMap namedScheduleMaps
		for(map : scheduleMaps.maps) {
			val name = map.copy.inputTupleName
			var newMap = map.copy
			if(inlineCode) {
				var String macroName
				do {
					macroName = "S" + nextStatementId
					nextStatementId += 1
				} while (nameChecker.isGlobalOrKeyword(macroName))
				val variable = variables.filter[x | x.name == name].head
				val macro = Factory.macroStmt(macroName, variable.domain.indexNames, variableStatements.get(name))
				entryPoint.addStatement(macro)
				newMap = newMap.setInputTupleName(macroName)
			} else {
				newMap = newMap.setInputTupleName("eval_" + name)
			}
			if(namedScheduleMaps === null) {
				namedScheduleMaps = newMap.copy.toUnionMap
			} else {
				namedScheduleMaps = namedScheduleMaps.addMap(newMap)
			}

		}
		
		//Generate all the loops for variables
		val islAST = LoopGenerator.generateLoops(scheduler.domains.params, namedScheduleMaps)
				
		val loopResult = ASTConverter.convert(islAST)

		val loopVariables = loopResult.declarations
			.map[Factory.variableDecl(typeGenerator.indexType, it)]
			.toArrayList
					
		entryPoint.addVariable(loopVariables)
			.addStatement(loopResult.statements)
		
	}
	
	def static convert(AlphaSystem system, BaseDataType valueType, Scheduler scheduler, MemoryMapper mapper, 
		boolean normalize, boolean inlineFunction, boolean inlineCode
	) {
		if (system.systemBodies.length != 1) {
			throw new IllegalArgumentException("Systems must have exactly one body to be converted directly to WriteC code.")
		}				
		var alteredSystem = system.copyAE
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
		
		return (new ScheduledC(alteredSystem.systemBodies.get(0), new AlphaNameChecker(false), 
			 new ScheduledTypeGenerator(valueType, false), scheduler, mapper, false, inlineFunction, inlineCode
		)).convertSystemBody
		
	}

	
}