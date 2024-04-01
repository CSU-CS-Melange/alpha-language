package alpha.codegen.polynomial

import static extension alpha.codegen.util.ISLPrintingUtils.*
import alpha.codegen.util.CodegenSwitch
import alpha.codegen.Polynomial
import alpha.codegen.PolynomialPiece
import alpha.codegen.PolynomialTerm

class PolynomialPrinter extends CodegenSwitch<CharSequence> {
	
	String variableName
	
	def static print(Polynomial polynomial, String variableName) {
		val printer = new PolynomialPrinter(variableName)
		printer.doSwitch(polynomial).toString
	}
	
	new(String variableName) {
		this.variableName = '_card_' + variableName
	}
	
	def casePolynomial(Polynomial p) {
		'''
			«IF variableName !== null»
			// «p.islPolynomial»
			long «variableName»;
			«ENDIF»
			«p.pieces.map[doSwitch].join(' else ')»
		'''
	}
	
	def isLast(PolynomialPiece piece) {
		val numPieces = piece.polynomial.pieces.size
		piece.polynomial.pieces.indexOf(piece) == numPieces - 1
	}
	
	def casePolynomialPiece(PolynomialPiece piece) {
		// don't display the 'if' for the last piece
		'''
		«if (!piece.isLast) 'if '»(«piece.getSet.paramConstraintsToConditionals») {
		  «variableName» = «piece.terms.map['''(«doSwitch»)'''].join('+')»;
		}'''
	}
	
	def casePolynomialTerm(PolynomialTerm term) {
		'''«term.value»'''
	}
	
	
}