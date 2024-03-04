package alpha.codegen.polynomial

import alpha.codegen.PolynomialVisitable
import alpha.codegen.PolynomialVisitor
import alpha.codegen.Polynomial
import alpha.codegen.PolynomialPiece
import alpha.codegen.PolynomialTerm

interface DefaultPolynomialVisitor extends PolynomialVisitor {
	
	def defaultIn(PolynomialVisitable v) {}
	def defaultOut(PolynomialVisitable v) {}
	
	override visitPolynomial(Polynomial polynomial) {
		inPolynomial(polynomial)
		polynomial.pieces.forEach[p | p.accept(this)]
		outPolynomial(polynomial)
	}
	
	override visitPolynomialPiece(PolynomialPiece piece) {
		inPolynomialPiece(piece)
		piece.terms.forEach[p | p.accept(this)]
		outPolynomialPiece(piece)
	}
	
	override visitPolynomialTerm(PolynomialTerm term) {
		inPolynomialTerm(term)
		outPolynomialTerm(term)
	}
	
	override inPolynomial(Polynomial polynomial) {
		defaultIn(polynomial)
	}
	
	override inPolynomialPiece(PolynomialPiece piece) {
		defaultIn(piece)
	}
	
	override inPolynomialTerm(PolynomialTerm term) {
		defaultIn(term)
	}
	
	override outPolynomial(Polynomial polynomial) {
		defaultOut(polynomial)
	}
	
	override outPolynomialPiece(PolynomialPiece piece) {
		defaultOut(piece)
	}
	
	override outPolynomialTerm(PolynomialTerm term) {
		defaultOut(term)
	}
}