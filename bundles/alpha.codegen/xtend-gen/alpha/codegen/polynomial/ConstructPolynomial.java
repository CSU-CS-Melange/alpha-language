package alpha.codegen.polynomial;

import alpha.codegen.Polynomial;
import alpha.codegen.PolynomialPiece;
import alpha.codegen.PolynomialTerm;
import alpha.codegen.factory.Factory;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece;
import fr.irisa.cairn.jnimap.isl.ISLTerm;
import java.util.List;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class ConstructPolynomial extends EObjectImpl implements DefaultPolynomialVisitor {
  public static Polynomial toPolynomial(final ISLPWQPolynomial pwqp) {
    Polynomial _xblockexpression = null;
    {
      final ConstructPolynomial visitor = new ConstructPolynomial();
      final Polynomial polynomial = Factory.createPolynomial(pwqp);
      polynomial.accept(visitor);
      _xblockexpression = polynomial;
    }
    return _xblockexpression;
  }

  @Override
  public void inPolynomial(final Polynomial polynomial) {
    final Function1<ISLQPolynomialPiece, PolynomialPiece> _function = (ISLQPolynomialPiece it) -> {
      return Factory.createPolynomialPiece(it);
    };
    final List<PolynomialPiece> pieces = ListExtensions.<ISLQPolynomialPiece, PolynomialPiece>map(polynomial.getIslPolynomial().getPieces(), _function);
    polynomial.getPieces().addAll(pieces);
  }

  @Override
  public void inPolynomialPiece(final PolynomialPiece piece) {
    final Function1<ISLTerm, PolynomialTerm> _function = (ISLTerm it) -> {
      return Factory.createPolynomialTerm(it);
    };
    final List<PolynomialTerm> terms = ListExtensions.<ISLTerm, PolynomialTerm>map(piece.getIslPiece().getQp().getTerms(), _function);
    piece.getTerms().addAll(terms);
  }
}
