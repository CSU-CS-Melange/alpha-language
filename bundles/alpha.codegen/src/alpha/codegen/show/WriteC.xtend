package alpha.codegen.show

import static extension alpha.codegen.util.ISLPrintingUtils.*
import alpha.codegen.Visitable
import alpha.codegen.Function
import alpha.codegen.EvalFunction
import alpha.codegen.util.AlphaEquationPrinter

class WriteC extends Base {
	public static boolean DEBUG = true	
	
	static def <T extends Visitable> print(T v) {
		val show = new WriteC();
		
		show.doSwitch(v).toString()
	}
	
	def caseFunction(Function f) '''
		«f.signature» {
		  // parameter checking
		  if («f.system.parameterDomain.paramConstraintsToConditionals») {
		    printf("The value of parameters are not valid.\n");
		    exit(-1);
		  }
		
		  // copy to global
		  «f.arrayArgs.map['''«it.writeName» = «it.localName»;'''].join('\n')»
		
		  // local memory allocation and macros
		  «f.memoryMacros.map[doSwitch].join('\n')»
		
		  // statements
		  «f.body.doSwitch»
		}
	'''
	
	def caseEvalFunction(EvalFunction ef) '''
		«ef.signature» {
		  // check flag
		  if («ef.flagVariable.identityAccess» == 'N') {
		    «ef.flagVariable.identityAccess» = 'I';
		    // Body for «ef.variable.name»
		    «AlphaEquationPrinter.printStandardEquation(ef.equation, ef.program)»
		    «ef.flagVariable.identityAccess» = 'F';
		  } else if («ef.flagVariable.identityAccess» == 'I') {
		    printf("There is a self dependence on «ef.variable.name» at («ef.variable.indices.map['%d'].join(',')»)\n", «ef.variable.indices.join(',')»);
		  }
		  
		  return «ef.variable.identityAccess»;
		}
	'''
	
	override signature(Function f) {
		'''«f.returnType» «f.name»(«f.args.map[localDefinition].join(', ')»)'''
	}
	
	
}