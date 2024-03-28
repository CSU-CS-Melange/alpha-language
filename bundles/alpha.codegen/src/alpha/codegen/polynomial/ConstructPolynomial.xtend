package alpha.codegen.polynomial

import static extension alpha.codegen.factory.Factory.*
import alpha.codegen.polynomial.DefaultPolynomialVisitor
import org.eclipse.emf.ecore.impl.EObjectImpl
import alpha.codegen.Polynomial
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import alpha.codegen.PolynomialPiece

class ConstructPolynomial extends EObjectImpl implements DefaultPolynomialVisitor {
	
	def static Polynomial toPolynomial(ISLPWQPolynomial pwqp) {
		val visitor = new ConstructPolynomial
		val polynomial = createPolynomial(pwqp)
		
		polynomial.accept(visitor)
		polynomial
	}
	
	override inPolynomial(Polynomial polynomial) {
		val pieces = polynomial.islPolynomial.pieces.map[createPolynomialPiece]
		polynomial.pieces.addAll(pieces)
	}
	
	override inPolynomialPiece(PolynomialPiece piece) {
		val terms = piece.islPiece.qp.terms.map[createPolynomialTerm]
		piece.terms.addAll(terms)
	}
	
}