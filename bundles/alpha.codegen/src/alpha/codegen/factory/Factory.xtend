package alpha.codegen.factory

import alpha.codegen.ArrayVariable
import alpha.codegen.BaseVariable
import alpha.codegen.CodegenFactory
import alpha.codegen.DataType
import alpha.codegen.EvalFunction
import alpha.codegen.Function
import alpha.codegen.FunctionBody
import alpha.codegen.GlobalMemoryMacro
import alpha.codegen.MemoryAllocation
import alpha.codegen.MemoryMacro
import alpha.codegen.Polynomial
import alpha.codegen.PolynomialPiece
import alpha.codegen.PolynomialTerm
import alpha.codegen.Program
import alpha.codegen.StatementMacro
import alpha.codegen.VariableType
import alpha.model.StandardEquation
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLASTNode
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLTerm

import static extension alpha.codegen.util.CodegenUtil.*
import static extension alpha.model.util.AlphaUtil.*

class Factory {
	
	static CodegenFactory factory = CodegenFactory.eINSTANCE
	
	def static Program createProgram() {
		val program = factory.createProgram
		program.includes
		program.commonMacros
		program.globalVariables
		program.functions
		program
	}
	
	def static Function createFunction(DataType returnType, String name, BaseVariable[] scalarArguments, ArrayVariable[] arrayArguments, ArrayVariable[] localVariables, FunctionBody body) {
		val function = factory.createFunction
		function.returnType = returnType
		function.name = name
		function.scalarArgs.addAll(scalarArguments)
		function.arrayArgs.addAll(arrayArguments)
		
		function.body = body
	
		function
	}
	
	def static Function createFunction(DataType returnType, String name, BaseVariable[] scalarArguments, ArrayVariable[] arrayArguments, ArrayVariable[] localVariables, MemoryMacro[] memoryMacros, FunctionBody body) {
		val function = createFunction(returnType, name, scalarArguments, arrayArguments, localVariables, body)
		function.memoryMacros.addAll(memoryMacros)
		
		function
	}
	
	def static MemoryMacro createMemoryMacro(ArrayVariable variable, ISLMap map) {
		val macro = factory.createMemoryMacro
		macro.variable = variable
		macro.map = if (map !== null) map else variable.alphaVariable.domain.identity
		
		macro.allocation = variable.createMemoryAllocation(macro)
		
		macro
	}
	
	def static MemoryMacro createMemoryMacro(ArrayVariable variable) {
		createMemoryMacro(variable, null)
	}
	
	
	def static String toßtring(ISLTerm t, ISLSpace s) {
		val P = t.dim(ISLDimType.isl_dim_param)
		val params = (0..<P).map[s.getDimName(ISLDimType.isl_dim_param, it)]
		
		
		'''(«t.numerator»/«t.denominator») «(0..<P).map[t.getExponent(ISLDimType.isl_dim_param, it)]»'''
	}
	
	def static MemoryAllocation createMemoryAllocation(ArrayVariable variable, MemoryMacro macro) {
		val allocation = factory.createMemoryAllocation
		allocation.macro = macro
		allocation.variable = variable
		
		allocation.domain = variable.alphaVariable.domain
		
		allocation
	}
	
	
	def static FunctionBody createFunctionBody(StatementMacro[] statementMacros, ISLASTNode node) {
		val body = factory.createFunctionBody
		body.statementMacros.addAll(statementMacros)
		body.ISLASTNode = node
		body
	}
	
	def static EvalFunction createEvalFunction(ArrayVariable evalVar, ArrayVariable flagVar, BaseVariable[] scalarArguments, StandardEquation equation, MemoryMacro[] localMemoryMacros) {
		val function = factory.createEvalFunction		
		function.returnType = evalVar.elemType
		function.scalarArgs.addAll(scalarArguments)
		function.name = 'eval_' + evalVar.name
		function.variable = evalVar
		function.flagVariable = flagVar
		function.equation = equation
		function.memoryMacros.addAll(localMemoryMacros)
				
		function
	}
	
	
	def static ArrayVariable createArrayVariable(Variable v) {
		val cv = factory.createArrayVariable
		cv.name = v.name
		cv.elemType = v.dataType
		cv.numDims = v.numDims
		cv.alphaVariable = v
		if (v.isInput)
			cv.type = VariableType.INPUT
		else if (v.isOutput)
			cv.type = VariableType.OUTPUT
		else
			cv.type = VariableType.LOCAL
		cv.memoryMacro = createGlobalMemoryMacro(cv)
		cv
	}

	def static GlobalMemoryMacro createGlobalMemoryMacro(String left, String right) {
		val gmm = factory.createGlobalMemoryMacro
		gmm.left = left
		gmm.right = right
		gmm
	}
	
	def static GlobalMemoryMacro createGlobalMemoryMacro(ArrayVariable cv) {
		val left = '''«cv.name»(«cv.alphaVariable.indices.join(',')»)'''
		val right = '''«cv.name»[«cv.alphaVariable.indices.join('][')»]'''
		createGlobalMemoryMacro(left, right)
	}
	
	def static ArrayVariable createArrayVariableForFlag(Variable v) {
		val cv = v.createArrayVariable
		cv.name = '_flag_'+v.name
		cv.elemType = DataType.CHAR
		cv.flagVariable = true
		cv.alphaVariable = v
		cv
	}
	
	
	
	
	
	
	
	// polynomial factory methods
	
	def static Polynomial createPolynomial(ISLPWQPolynomial pwqp) {
		val ret = factory.createPolynomial
		ret.islPolynomial = pwqp
		ret
	}
	
	def static PolynomialPiece createPolynomialPiece(ISLQPolynomialPiece piece) {
		val ret = factory.createPolynomialPiece
		ret.islPiece = piece
		ret
	}
	
	def static PolynomialTerm createPolynomialTerm(ISLTerm term) {
		val ret = factory.createPolynomialTerm
		ret.islTerm = term
		ret
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}