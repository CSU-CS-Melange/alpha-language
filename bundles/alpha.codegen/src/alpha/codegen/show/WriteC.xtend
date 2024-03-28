package alpha.codegen.show

import static extension alpha.codegen.util.ISLPrintingUtils.*
import alpha.codegen.Visitable
import alpha.codegen.Function
import alpha.codegen.EvalFunction
import alpha.codegen.util.AlphaEquationPrinter
import alpha.codegen.ReduceFunction
import alpha.codegen.DataType
import alpha.codegen.C_REDUCTION_OP
import alpha.model.REDUCTION_OP
import alpha.model.ReduceExpression

class WriteC extends Base {
	public static boolean DEBUG = true	
	
	static def <T extends Visitable> print(T v) {
		val show = new WriteC();
		
		show.doSwitch(v).toString()
	}
	
	def caseFunction(Function f) '''
		«f.signature» {
		  // parameter checking
		  if (!(«f.system.parameterDomain.paramConstraintsToConditionals»)) {
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
		    printf("There is a self dependence on «ef.variable.name» at («ef.variable.indices.map['%ld'].join(',')»)\n", «ef.variable.indices.join(',')»);
		    exit(-1);
		  }
		  
		  return «ef.variable.identityAccess»;
		}
	'''
	
	def caseReduceFunction(ReduceFunction rf) '''
		«rf.signature» {
			«rf.reduceVar.localDefinition» = «getReductionInitializer(rf.reduceExpr.operator, rf.reduceVar.elemType)»;
			{
				#define «rf.reduceMacroLeftSide» «rf.reduceMacroRightSide»
				«rf.body.doSwitch»
			}
			return «rf.reduceVar.name»;
		}
	'''
	
	override signature(Function f) {
		'''«f.returnType» «f.name»(«f.args.map[localDefinition].join(', ')»)'''
	}
	
	def getReduceMacroLeftSide(ReduceFunction rf) {
		'''«rf.macroName»(«rf.reduceExpr.body.contextDomain.indexNames.join(',')»)'''
	}
	
	def getReduceMacroRightSide(ReduceFunction rf) {
		val operator = rf.reduceExpr.operator
		val reduceVar = rf.reduceVar.name
		val addedExpression = AlphaEquationPrinter.printExpression(rf.reduceExpr.body, rf.program)
		
		switch operator {
			case MIN: '''«reduceVar» = min(«reduceVar», («addedExpression»))'''
			case MAX: '''«reduceVar» = max(«reduceVar», («addedExpression»))'''
			case SUM: '''«reduceVar» += («addedExpression»)'''
			case PROD: '''«reduceVar» *= («addedExpression»)'''
			case AND: '''«reduceVar» &= («addedExpression»)'''
			case OR: '''«reduceVar» |= («addedExpression»)'''
			default: throw new Exception("Cannot generate code for reduction operator: " + operator.toString)
		}
	}
	
	def getReductionInitializer(REDUCTION_OP operator, DataType type) {
		switch operator {
			case MIN: type.negativeInfinityValue
			case MAX: type.infinityValue
			case SUM: type.zeroValue
			case PROD: type.oneValue
			case AND: "true"
			case OR: "false"
			default: throw new Exception("There is no initializer for reduction operator: " + operator)
		}
	}
	
	def getZeroValue(DataType type) {
		switch type {
			case DataType.INT: "0"
			case DataType.LONG: "0L"
			case DataType.FLOAT: "0.0f"
			case DataType.DOUBLE: "0.0"
			default: throw new Exception("There is no '0' value for type: " + type)
		}
	}
	
	def getOneValue(DataType type) {
		switch type {
			case DataType.INT: "1"
			case DataType.LONG: "1L"
			case DataType.FLOAT: "1.0f"
			case DataType.DOUBLE: "1.0"
			default: throw new Exception("There is no '1' value for type: " + type)
		}
	}
	
	def getInfinityValue(DataType type) {
		switch type {
			case DataType.INT: "INT_MAX"
			case DataType.LONG: "LONG_MAX"
			case DataType.FLOAT: "FLT_MAX"
			case DataType.DOUBLE: "DBL_MAX"
			default: throw new Exception("There is no infinity value for type: " + type)
		}
	}
	
	def getNegativeInfinityValue(DataType type) {
		switch type {
			case DataType.INT: "INT_MIN"
			case DataType.LONG: "LONG_MIN"
			case DataType.FLOAT: "FLT_MIN"
			case DataType.DOUBLE: "DBL_MIN"
			default: throw new Exception("There is no negative infinity value for type: " + type)
		}
	}
}