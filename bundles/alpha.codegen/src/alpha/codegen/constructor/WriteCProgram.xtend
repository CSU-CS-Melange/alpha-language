package alpha.codegen.constructor

import static extension alpha.model.util.AlphaUtil.*
import static extension alpha.codegen.util.CodegenUtil.*
import static extension alpha.codegen.factory.Factory.*
import alpha.model.AlphaSystem
import alpha.codegen.Program
import alpha.targetmapping.TargetMapping
import alpha.codegen.ArrayVariable
import alpha.codegen.DataType
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLASTBuild
import alpha.model.StandardEquation
import java.util.Map
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

class WriteCProgram extends BaseProgram {
	
	protected Map<Variable, ArrayVariable> flagCVs
	
	def static Program build(AlphaSystem system, TargetMapping tm) {
		if (system.systemBodies.size > 1)
			throw new Exception("No support yet for programs with multiple system bodies")
		
		val visitor = new WriteCProgram(tm)
		system.accept(visitor)
		visitor.program
	}
	
	new(TargetMapping tm) {
		super()
		flagCVs = newHashMap
		program = createProgram
		this.tm = tm
	}
	
	def ISLUnionSet outputStatementsDomain(AlphaSystem s) {
		s.outputs.map[domain.setTupleName('''S«s.outputs.indexOf(it)»''').toUnionSet]
		         .reduce(d0,d1 | d0.copy.union(d1.copy))
	}
	
	override inAlphaSystem(AlphaSystem s) {
		super.inAlphaSystem(s)
		
		(s.locals + s.outputs).forEach[v | flagCVs.put(v, v.createArrayVariableForFlag)]
		
		// statement macros
		val statementMacros = s.outputs.map[statementMacro(
			'''S«s.outputs.indexOf(it)»(«indices.join(',')»)''',
			'''eval_«name»(«(domain.paramNames + domain.indexNames).join(', ')»)'''
		)]
		
		// schedule tree node
		val schedule = ISLSchedule.buildFromDomain(s.outputStatementsDomain)
		val build = ISLASTBuild.buildFromContext(schedule.domain.copy.params)
		val node = build.generate(schedule.copy)
		
		val body = createFunctionBody(statementMacros, node)

		val paramArgs = s.paramScalarVariables
		val arrayArgs = (inputCVs + outputCVs).values
				
		val function = createFunction(DataType.VOID, s.name, paramArgs, arrayArgs, localCVs.values, body)

		program.globalVariables.addAll(arrayArgs + localCVs.values + flagCVs.values)
		program.functions.add(function)
	}
	
	
	override inStandardEquation(StandardEquation se) {
		if (!se.variable.isOutput)
			return                 
		
		val evalVar = outputCVs.get(se.variable)
		val flagVar = flagCVs.get(se.variable)
		val scalarArgs = se.getContainerSystem.paramScalarVariables + se.variable.indexScalarVariables
		val localMemoryMacros = localCVs.values.map[createMemoryMacro]
		
		
		val function = createEvalFunction(evalVar, flagVar, scalarArgs, se, localMemoryMacros)
		
		program.functions.add(function)
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}