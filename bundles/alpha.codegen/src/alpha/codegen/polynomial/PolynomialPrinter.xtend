package alpha.codegen.polynomial

import alpha.codegen.Polynomial
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT

import static extension fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial._toString
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial

class PolynomialPrinter {
	protected static val format = ISL_FORMAT.C.ordinal
	
	static def print(Polynomial polynomial, String variableName) '''
		// «polynomial.islPolynomial»
		long «variableName» = «polynomial.print»
	'''

	static def print(ISLPWQPolynomial polynomial) '''(«polynomial._toString(format)»)'''
	
	static def print(Polynomial polynomial) {
		print(polynomial.islPolynomial)
	}
	
	static def printMinusOne(Polynomial polynomial) '''(«polynomial.print» - 1)'''
}