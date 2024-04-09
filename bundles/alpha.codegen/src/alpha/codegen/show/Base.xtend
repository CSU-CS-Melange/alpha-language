package alpha.codegen.show

import alpha.codegen.ArrayVariable
import alpha.codegen.BaseVariable
import alpha.codegen.EvalFunction
import alpha.codegen.Function
import alpha.codegen.FunctionBody
import alpha.codegen.GlobalVariable
import alpha.codegen.Include
import alpha.codegen.Macro
import alpha.codegen.MemoryAllocation
import alpha.codegen.Node
import alpha.codegen.Program
import alpha.codegen.ReduceFunction
import alpha.codegen.Visitable
import alpha.codegen.Visitor
import alpha.codegen.util.CodegenSwitch
import org.eclipse.emf.ecore.EObject

class Base extends CodegenSwitch<CharSequence> {
	
	
	static def <T extends Visitable> print(T v) {
		val show = new Base();
		
		show.doSwitch(v).toString()
	}
	
	def caseNode(Node cn) {
		''''''
	}
	
	override caseVisitable(Visitable v) {
		''''''
	}
	
	override caseVisitor(Visitor v) {
		''''''
	}
	
	def caseInclude(Include i) {
		'''#include <«i.name».h>'''
	}
	
	def caseMacro(Macro m) {
		'''#define «m.left» «m.right»'''
	}
	
	def dispatch localDefinition(BaseVariable v) {
		'''«v.dataType» «v.localName»'''
	}
	
	def dispatch localDefinition(ArrayVariable v) {
		'''«v.dataType» «v.localName»'''
	}
	
	def dispatch localName(BaseVariable v) {
		'''«v.name»'''
	}
	
	def dispatch localName(GlobalVariable v) {
		'''_local_«v.name»'''
	}
	
	def dispatch localName(ArrayVariable v) {
		'''_local_«v.name»'''
		
	}
	
	def caseBaseVariable(BaseVariable v) {
		'''«v.dataType» «v.localName»'''
	}
	
	def caseMemoryAllocation(MemoryAllocation ma) {
		'''
		{
		  «FOR i : 0..<ma.variable.numDims»
		  	int D«i» = ...
		  «ENDFOR»
		  
		  // TODO - mallocs for local memory reflecting memory maps
		  
		}
		'''
	}
	
	def caseFunctionBody(FunctionBody fb) {
		'''
			«fb.statementMacros.map[doSwitch].join('\n')»
			«fb.ISLASTNode.toCString»
		'''
	}
	
	def signature(Function f) {
		'''«f.returnType» «f.name»(«f.args.map[doSwitch].join(', ')»)'''
	}
	
	def declaration(BaseVariable v) {
		'''«v.dataType» «v.name»'''
	}
	
	def caseProgram(Program p) '''
		«p.includes.map[doSwitch].join('\n')»
		
		// common macros
		«p.commonMacros.map[doSwitch].join('\n')»
		
		// global variable declarations
		«p.globalVariables.map['''static «it.declaration»;'''].join('\n')»
		
		// memory macros
		«p.globalVariables.filter[it.hasMemoryMacro].map[memoryMacro.doSwitch].join('\n')»
		
		// local function declarations
		«p.functions.filter[f | f instanceof EvalFunction].map[it.signature + ';'].join('\n')»
		«p.functions.filter[f | f instanceof ReduceFunction].map[it.signature + ';'].join('\n')»
		
		«p.functions.map[doSwitch].join('\n')»
	'''
	
	override defaultCase(EObject o) {''''''}
}