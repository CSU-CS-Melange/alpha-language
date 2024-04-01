package alpha.codegen.polynomial

import alpha.codegen.Polynomial
import alpha.codegen.PolynomialPiece
import alpha.codegen.PolynomialTerm

import static extension alpha.codegen.util.ISLPrintingUtils.paramConstraintsToConditionals

class PolynomialExprPrinter {
	static def CharSequence print(Polynomial polynomial) 
		'''(«polynomial.pieces.map[print].join(' : ')»)'''
		
	/**
	 * Prints the polynomial with an extra "-1" at the very end.
	 * The intended use case is to change the polynomials used for indexing a variable
	 * from 1-based indexing to 0-based indexing, as this is difficult (impossible?)
	 * to do within the ISLPWQPolynomial data structure natively.
	 */
	static def CharSequence printMinusOne(Polynomial polynomial)
		'''(«polynomial.print» - 1)'''
		
	static def CharSequence print(PolynomialPiece piece) {
		if (piece.isLast) {
			return piece.printTerms
		} else {
			return piece.printWithCondition
		}
	}
	
	static def CharSequence printWithCondition(PolynomialPiece piece)
		'''(«piece.getSet.paramConstraintsToConditionals») ? «piece.printTerms»'''
		
	static def CharSequence printTerms(PolynomialPiece piece)
		'''(«piece.terms.map[print].join('+')»)'''
		
	static def CharSequence print(PolynomialTerm term)
		'''(«term.value»)'''
		
	static def isLast(PolynomialPiece piece) {
		val numPieces = piece.polynomial.pieces.size
		piece.polynomial.pieces.indexOf(piece) == numPieces - 1
	}
}