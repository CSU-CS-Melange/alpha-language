package alpha.codegen.polynomial

import alpha.codegen.Polynomial
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT

import static extension fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial._toString

class PolynomialPrinter {
	protected static val format = ISL_FORMAT.C.ordinal
	
	static def print(Polynomial polynomial, String variableName) '''
		// «polynomial.islPolynomial»
		long «variableName» = «polynomial.print»
	'''

	static def print(Polynomial polynomial) '''(«polynomial.islPolynomial._toString(format)»)'''
	
	static def printMinusOne(Polynomial polynomial) '''(«polynomial.print» - 1)'''
}