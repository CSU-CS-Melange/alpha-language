package alpha.codegen.constructor

import static extension alpha.codegen.util.CodegenUtil.*
import static extension alpha.codegen.factory.Factory.*
import alpha.model.AlphaSystem
import alpha.codegen.Program
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.targetmapping.TargetMapping
import alpha.codegen.ArrayVariable
import java.util.Map
import alpha.model.Variable
import alpha.model.transformation.Normalize

abstract class BaseProgram extends AbstractAlphaCompleteVisitor {
	
	protected Program program
	protected TargetMapping tm
	protected Map<Variable, ArrayVariable> inputCVs
	protected Map<Variable, ArrayVariable> localCVs
	protected Map<Variable, ArrayVariable> outputCVs
	
	new() {
		inputCVs = newHashMap
		localCVs = newHashMap
		outputCVs = newHashMap
	}
	
	public def ArrayVariable getArrayVariable(Variable v) {
		if (v.isInput)
			inputCVs.get(v)
		else if (v.isLocal)
			localCVs.get(v)
		else if (v.isOutput)
			outputCVs.get(v)
	}
	
	def defaultIncludes() {
		val includes = #[
			'stdio',
			'stdlib',
			'stdbool',
			'math',
			'string',
			'limits',
			'float'
		]
		program.includes.addAll(includes.map[it.toInclude])
	}
	
	def defaultGlobalMacros() {
		val macros = #[
			#['max(a,b)', '((a)>(b)?(a):(b))'],
			#['min(a,b)', '((a)>(b)?(b):(a))'],
			#['ceild(a,b)', '(int)ceil(((double)(a))/((double)(b)))'],
			#['floord(a,b)', '(int)floor(((double)(a))/((double)(b)))'],
			#['mod(a,b)', '((a)%(b))'],
			#['mallocCheck(v,s)', 'if ((v) == NULL) { printf("Failed to allocate memory for variable: %s\\n", s); exit(-1); }']
		]
		program.commonMacros.addAll(macros.map[globalMacro(it.get(0), it.get(1))])
	}
	
	override inAlphaSystem(AlphaSystem s) {
		Normalize.apply(s)
		
		program.system = s
		defaultIncludes
		defaultGlobalMacros
		
		s.inputs.forEach[v | inputCVs.put(v, v.createArrayVariable)]
		s.locals.forEach[v | localCVs.put(v, v.createArrayVariable)]
		s.outputs.forEach[v | outputCVs.put(v, v.createArrayVariable)]
	}
	
	
	
}