package alpha.codegen.sheduledC

import alpha.codegen.alphaBase.CodeGeneratorBase
import alpha.model.Variable
import alpha.model.StandardEquation
import alpha.model.UseEquation
import alpha.model.SystemBody
import alpha.codegen.alphaBase.AlphaNameChecker
import alpha.codegen.alphaBase.TypeGeneratorBase

class WriteC extends CodeGeneratorBase {
	
	new(SystemBody systemBody, AlphaNameChecker nameChecker, TypeGeneratorBase typeGenerator, boolean cycleDetection) {
		super(systemBody, nameChecker, typeGenerator, cycleDetection)
	}
	
	override declareMemoryMacro(Variable variable) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override declareFlagMemoryMacro(Variable variable) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override declareEvaluation(StandardEquation equation) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override declareEvaluation(UseEquation equation) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override allocateVariable(Variable variable) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override allocateFlagsVariable(Variable variable) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override performEvaluations() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}