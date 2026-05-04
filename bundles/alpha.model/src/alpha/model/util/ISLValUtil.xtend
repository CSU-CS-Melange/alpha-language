package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLVal
import fr.irisa.cairn.jnimap.isl.ISLContext

class ISLValUtil {
	static def ISLVal asVal(Number a) {
		return ISLVal.buildFromLong(ISLContext.instance, a.longValue)
	}
	
	//Addition
	static dispatch def ISLVal operator_plus(ISLVal a, ISLVal b) {
		a.add(b)
	}
	
	static dispatch def ISLVal operator_plus(Number a, ISLVal b) {
		a.asVal + b
	}
	
	static dispatch def ISLVal operator_plus(ISLVal a, Number b) {
		a + b.asVal
	}
	
	//Multiplication
	static dispatch def ISLVal operator_multiply(ISLVal a, ISLVal b) {
		a.mul(b)
	}
	
	static dispatch def ISLVal operator_multiply(Number a, ISLVal b) {
		a.asVal * b
	}
	
	static dispatch def ISLVal operator_multiply(ISLVal a, Number b) {
		a * b.asVal
	}
	
	//Subtraction
	static dispatch def ISLVal operator_minus(ISLVal a, ISLVal b) {
		a.sub(b)
	}
	
	static dispatch def ISLVal operator_minus(Number a, ISLVal b) {
		a.asVal - b
	}
	
	static dispatch def ISLVal operator_minus(ISLVal a, Number b) {
		a - b.asVal
	}
	
	//Division
	static dispatch def ISLVal operator_divide(ISLVal a, ISLVal b) {
		a.div(b)
	}
	
	static dispatch def ISLVal operator_divide(Number a, ISLVal b) {
		a.asVal / b
	}
	
	static dispatch def ISLVal operator_divide(ISLVal a, Number b) {
		a / b.asVal
	}
	
	//Negation
	static def ISLVal operator_minus(ISLVal a) {
		return a.neg
	}
}