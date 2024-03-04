package alpha.codegen.polynomial;

import alpha.codegen.Polynomial;
import alpha.codegen.PolynomialPiece;
import alpha.codegen.PolynomialTerm;
import alpha.codegen.PolynomialVisitable;
import alpha.codegen.PolynomialVisitor;
import java.util.function.Consumer;

@SuppressWarnings("all")
public interface DefaultPolynomialVisitor extends PolynomialVisitor {
  default Object defaultIn(final PolynomialVisitable v) {
    return null;
  }

  default Object defaultOut(final PolynomialVisitable v) {
    return null;
  }

  @Override
  default void visitPolynomial(final Polynomial polynomial) {
    this.inPolynomial(polynomial);
    final Consumer<PolynomialPiece> _function = (PolynomialPiece p) -> {
      p.accept(this);
    };
    polynomial.getPieces().forEach(_function);
    this.outPolynomial(polynomial);
  }

  @Override
  default void visitPolynomialPiece(final PolynomialPiece piece) {
    this.inPolynomialPiece(piece);
    final Consumer<PolynomialTerm> _function = (PolynomialTerm p) -> {
      p.accept(this);
    };
    piece.getTerms().forEach(_function);
    this.outPolynomialPiece(piece);
  }

  @Override
  default void visitPolynomialTerm(final PolynomialTerm term) {
    this.inPolynomialTerm(term);
    this.outPolynomialTerm(term);
  }

  @Override
  default void inPolynomial(final Polynomial polynomial) {
    this.defaultIn(polynomial);
  }

  @Override
  default void inPolynomialPiece(final PolynomialPiece piece) {
    this.defaultIn(piece);
  }

  @Override
  default void inPolynomialTerm(final PolynomialTerm term) {
    this.defaultIn(term);
  }

  @Override
  default void outPolynomial(final Polynomial polynomial) {
    this.defaultOut(polynomial);
  }

  @Override
  default void outPolynomialPiece(final PolynomialPiece piece) {
    this.defaultOut(piece);
  }

  @Override
  default void outPolynomialTerm(final PolynomialTerm term) {
    this.defaultOut(term);
  }
}
