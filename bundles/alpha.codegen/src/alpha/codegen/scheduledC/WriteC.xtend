package alpha.codegen.scheduledC

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
import alpha.model.prdg.PRDG
import alpha.model.prdg.PRDGGenerator
import alpha.model.scheduler.FoutrierScheduler
import alpha.model.scheduler.Scheduler
import alpha.model.transformation.ChangeOfBasis
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.CommonExtensions.toArrayList
import alpha.model.transformation.Normalize
import alpha.model.transformation.reduction.NormalizeReduction
import java.util.List
import javax.lang.model.type.UnionType
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLScheduleNode
import java.util.ArrayList
import alpha.model.util.AShow
import alpha.model.transformation.StandardizeNames
import java.util.Map
import java.util.HashMap
import org.eclipse.xtext.validation.impl.AssignmentQuantityIntervalProvider
import alpha.codegen.AssignmentStmt

class WriteC extends CodeGeneratorBase {
	
	/** The next ID to use as a statement macro. */
	protected var int nextStatementId = 0
	
	/** Converts Alpha expressions to simpleC expressions. */
	protected val ScheduledExprConverter exprConverter
	
	/** Takes an Alpha System and a PRDG and generates a schedule for them */
	protected val Scheduler scheduler
	//ISLSchedule schedule
	
	protected val PRDG prdg
	
	/** Tells the code generator to add the inline keyword to the evaluate function */
	protected val boolean inlineFunction
	
	/** Tells the code generator to manually inline the function (replace the function call with the actual function body */
	protected val boolean inlineCode
	
	protected var Map<String, AssignmentStmt> variableStatements
	
	new(SystemBody systemBody, AlphaNameChecker nameChecker, PRDG prdg, ScheduledTypeGenerator typeGenerator, 
		Scheduler scheduler, boolean cycleDetection, boolean inlineFunction,
		boolean inlineCode
	) {
		super(systemBody, nameChecker, typeGenerator, cycleDetection)

		this.exprConverter = new ScheduledExprConverter(typeGenerator, nameChecker, program, scheduler)
		this.prdg = prdg
		this.scheduler = scheduler
		this.inlineFunction = inlineFunction
		this.inlineCode = inlineCode
		this.variableStatements = new HashMap()
	}
	
	/** Normalizes the system body and standardizes all names prior to conversion. */
	/** Overide the preprocess step to not normalize reductions */
	override void preprocess() {
		Normalize.apply(systemBody)
		//NormalizeReduction.apply(systemBody)
		StandardizeNames.apply(systemBody)
	}
	
	override declareMemoryMacro(Variable variable) {
		val name = nameChecker.getVariableStorageName(variable)
		val domain = variable.domain
		
		val rank = MemoryUtils.rank(domain)
		val accessExpression = PolynomialConverter.convert(rank)
		val macroReplacement = Factory.arrayAccessExpr(name, accessExpression)
		val macroStmt = Factory.macroStmt(name, domain.indexNames, macroReplacement)
		program.addMemoryMacro(macroStmt)
	}
	
	override declareFlagMemoryMacro(Variable variable) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override declareEvaluation(StandardEquation equation) {
		// Start building a static, non-inlined function.
		val returnType = Factory.dataType(BaseDataType.VOID)
		val evalName = nameChecker.getVariableReadName(equation.variable)
		val evalBuilder = program.startFunction(true, this.inlineFunction, returnType, "eval_" + evalName)
		
		//val evalBuilder = program.startFunction(true, this.inlineFunction, returnType, evalName)

//		println("Prdg Domains: " + this.scheduler.schedule)

		// Add a function parameter for each index of the variable's domain.
		val indexNames = equation.expr.contextDomain.indexNames
		indexNames.forEach[evalBuilder.addParameter(typeGenerator.indexType, it)]
		
		/** TODO: Expand to multiple edges */
		exprConverter.target = equation.name

		val computeValue = exprConverter.convertExpr(equation.expr)
		val computeAndStore = Factory.assignmentStmt(equation.identityAccess(false), computeValue)
		
		exprConverter.target = ""
		
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
		val name = nameChecker.getVariableStorageName(variable)
		val dataType = typeGenerator.getAlphaVariableType(variable)
		
		allocatedVariables.add(name)
		
		// Call "malloc" to allocate memory and assign it to the variable.
		val cardinalityExpr = variable.domain.cardinalityExpr
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


		// We will have ISL create a loop nest that visits all points in their lexicographic order.
		// Any loop variables used need to also be declared.
		
		//val scheduleMaps = convertToUnionMap(scheduler.schedule.map.copy.maps.filter[ map | !map.getDimNames(ISLDimType.isl_dim_in).head.contains("result")].toList)
		var ISLUnionMap scheduleMaps
		for(map : scheduler.schedule.map.maps) {
			for(variable : variables) {
				var name = map.copy.inputTupleName
				println("Name: " + name)
				if(name == variable.name) {
					if(scheduleMaps === null) {
						scheduleMaps = map.copy.toUnionMap
					} else {
						scheduleMaps = scheduleMaps.addMap(map.copy)
					}
				}
			}
		}

		println("Maps: " + scheduleMaps.copy)
		scheduleMaps = scheduleMaps.intersectDomain(this.scheduler.schedule.domain)
		var ISLUnionMap namedScheduleMaps
		for(map : scheduleMaps.maps) {
			println("Name: " + map.copy.inputTupleName)
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
		println("Schedule Maps")
		this.scheduler.schedule.map.maps.forEach[map | println(map)]
		val islAST = LoopGenerator.generateLoops(scheduler.schedule.domain.params, namedScheduleMaps)
		val tempAST = LoopGenerator.generateLoops(scheduler.schedule.domain.params, this.scheduler.schedule)
		
		println("Unedited C output: " + tempAST.toCString)
		
		val loopResult = ASTConverter.convert(islAST)

		val loopVariables = loopResult.declarations
			.map[Factory.variableDecl(typeGenerator.indexType, it)]
			.toArrayList
					
		entryPoint.addVariable(loopVariables)
			.addStatement(loopResult.statements)
		
	}
	
	def static convert(AlphaSystem system, BaseDataType valueType, boolean normalize, boolean inlineFunction, boolean inlineCode) {
		if (system.systemBodies.length != 1) {
			throw new IllegalArgumentException("Systems must have exactly one body to be converted directly to WriteC code.")
		}				
		var alteredSystem = system.copyAE
		Normalize.apply(alteredSystem)
		//NormalizeReduction.apply(alteredSystem)
		println(AShow.print(alteredSystem))
		
		val prdg = PRDGGenerator.apply(alteredSystem)
		var scheduler = new FoutrierScheduler(prdg)

		for(Variable local : alteredSystem.locals) {
			for(ISLMap map : scheduler.schedule.map.maps) {
				if(map.getTupleName(ISLDimType.isl_dim_out) == local.name) {
					ChangeOfBasis.apply(alteredSystem, local, toMultiAff(map))
				}
			}
		}
		
		for(Variable input : alteredSystem.inputs) {
			for(ISLMap map : scheduler.schedule.map.maps) {
				if(map.getTupleName(ISLDimType.isl_dim_in) == input.name) {
					ChangeOfBasis.apply(alteredSystem, input, toMultiAff(map))
				}	
			}
		}

		//alteredSystem.systemBodies.get(0).equations.forEach[x | println("Equation: " + x.name)]	
		//alteredSystem.systemBodies.get(0).standardEquations.forEach[x | println("SE Orig: " + x.expr)])
		println("PRDG: \n" + prdg.show())
		return (new WriteC(alteredSystem.systemBodies.get(0), new AlphaNameChecker(false), 
			prdg, new ScheduledTypeGenerator(valueType, false), scheduler, false, inlineFunction, inlineCode
		)).convertSystemBody
		
	}
	
	def static ISLUnionMap convertToUnionMap(List<ISLMap> maps) {
		var ISLUnionMap unionMap
		for(map : maps) {
			unionMap = unionMap === null ? map.toUnionMap : unionMap.addMap(map)
		}
		unionMap
	}
	
	def static ISLMultiAff toMultiAff(ISLMap map) {
		val local = map.copy
		val pma = local.toPWMultiAff
		val piece = pma.getPiece(0)
		piece.maff
	}
	
}