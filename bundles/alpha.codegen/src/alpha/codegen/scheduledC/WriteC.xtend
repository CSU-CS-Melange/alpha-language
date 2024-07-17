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
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.CommonExtensions.toArrayList
import alpha.model.transformation.ChangeOfBasis
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLDimType
import alpha.codegen.ProgramPrinter

class WriteC extends CodeGeneratorBase {
	
	/** The next ID to use as a statement macro. */
	protected var int nextStatementId = 0
	
	/** Converts Alpha expressions to simpleC expressions. */
	protected val ScheduledExprConverter exprConverter
	
	/** Takes an Alpha System and a PRDG and generates a schedule for them */
	protected val Scheduler scheduler
	//ISLSchedule schedule
	
	protected val PRDG prdg
	
	new(SystemBody systemBody, AlphaNameChecker nameChecker, PRDG prdg, ScheduledTypeGenerator typeGenerator, Scheduler scheduler, boolean cycleDetection) {
		super(systemBody, nameChecker, typeGenerator, cycleDetection)

		this.exprConverter = new ScheduledExprConverter(typeGenerator, nameChecker, program, scheduler)
		this.prdg = prdg
		this.scheduler = scheduler
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
		val returnType = typeGenerator.getAlphaValueType(equation.variable)
		val evalName = nameChecker.getVariableReadName(equation.variable)
		val evalBuilder = program.startFunction(true, false, returnType, "eval_" + evalName)
		 	
		println("Expr Domain: " + equation.variable.name)
		println("Prdg Domains: " + this.scheduler.schedule)

		// Add a function parameter for each index of the variable's domain.
		val indexNames = equation.expr.contextDomain.indexNames
		indexNames.forEach[evalBuilder.addParameter(typeGenerator.indexType, it)]
		
		exprConverter.target = "Y_reduce1_body"
		val computeValue = exprConverter.convertExpr(equation.expr)
		val computeAndStore = Factory.assignmentStmt(equation.identityAccess(false), computeValue)
		
		evalBuilder.addStatement(computeAndStore)
		program.addFunction(evalBuilder.instance)
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
		systemBody.system.outputs.forEach[evaluateAllPoints]
		entryPoint.addEmptyLine
	}
	
	/** Evaluates all the points within an output variable. */
	def protected evaluateAllPoints(Variable variable) {
		// Get a unique name for the macro that is called inside the loop.
		var String macroName
		do {
			macroName = "S" + nextStatementId
			nextStatementId += 1
		} while (nameChecker.isGlobalOrKeyword(macroName))
		
		// Add a macro that simply calls the "eval" function.
		// The loop nest generated by ISL will call this macro.
		val evalName = "eval_" + nameChecker.getVariableReadName(variable)
		val callEval = Factory.callExpr(evalName, variable.domain.indexNames)
		val macro = Factory.macroStmt(macroName, variable.domain.indexNames, callEval)
		entryPoint.addStatement(macro)
				
		// We will have ISL create a loop nest that visits all points in their lexicographic order.
		// Any loop variables used need to also be declared.
		val islAST = LoopGenerator.generateLoops(macroName, variable.domain, scheduler.getMacroSchedule(variable.name))
		println("islAST: " + islAST.copy)
		val loopResult = ASTConverter.convert(islAST)
		println("Result: " + loopResult.declarations)
		val loopVariables = loopResult.declarations
			.map[Factory.variableDecl(typeGenerator.indexType, it)]
			.toArrayList
					
		entryPoint.addVariable(loopVariables)
			.addStatement(loopResult.statements)
		
		// Undefine the macro now that we're done with it.
		entryPoint.addStatement(Factory.undefStmt(macroName))
	}

	
	def static convert(AlphaSystem system, BaseDataType valueType, boolean normalize) {
		if (system.systemBodies.length != 1) {
			throw new IllegalArgumentException("Systems must have exactly one body to be converted directly to WriteC code.")
		}
		val duplicate = system.copyAE
		val body = duplicate.systemBodies.get(0)
		val prdg = PRDGGenerator.apply(system)
		
				
		var scheduler = new FoutrierScheduler(prdg)
		println("Schedule: " + scheduler.schedule)
		println("Schedule Maps: " + scheduler.schedule.map)
		println("Schedule Domain: " + scheduler.schedule.domain)
				
		var alteredSystem = system.copyAE

		for(Variable local : alteredSystem.locals) {
			for(ISLMap map : scheduler.schedule.map.maps) {
				if(map.getTupleName(ISLDimType.isl_dim_in) == local.name) {
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

		println("Map Data: " + scheduler.schedule.map.maps.get(0).getTupleName(ISLDimType.isl_dim_in))	
		println("PRDG: \n" + prdg.show())
		return (new WriteC(alteredSystem.systemBodies.get(0), new AlphaNameChecker(false), prdg, new ScheduledTypeGenerator(valueType, false), scheduler, false)).convertSystemBody
		
	}
	
	def static ISLMultiAff toMultiAff(ISLMap map) {
		val local = map.copy
		val pma = local.toPWMultiAff
		val piece = pma.getPiece(0)
		piece.maff
	}
	
}