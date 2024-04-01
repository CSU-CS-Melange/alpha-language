package alpha.codegen.polynomial;

import alpha.codegen.Polynomial;
import alpha.codegen.PolynomialPiece;
import alpha.codegen.PolynomialTerm;
import alpha.codegen.util.ISLPrintingUtils;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class PolynomialExprPrinter {
  public static CharSequence print(final Polynomial polynomial) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    final Function1<PolynomialPiece, CharSequence> _function = (PolynomialPiece it) -> {
      return PolynomialExprPrinter.print(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<PolynomialPiece, CharSequence>map(polynomial.getPieces(), _function), " : ");
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  /**
   * Prints the polynomial with an extra "-1" at the very end.
   * The intended use case is to change the polynomials used for indexing a variable
   * from 1-based indexing to 0-based indexing, as this is difficult (impossible?)
   * to do within the ISLPWQPolynomial data structure natively.
   */
  public static CharSequence printMinusOne(final Polynomial polynomial) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    CharSequence _print = PolynomialExprPrinter.print(polynomial);
    _builder.append(_print);
    _builder.append(" - 1)");
    return _builder;
  }

  public static CharSequence print(final PolynomialPiece piece) {
    boolean _isLast = PolynomialExprPrinter.isLast(piece);
    if (_isLast) {
      return PolynomialExprPrinter.printTerms(piece);
    } else {
      return PolynomialExprPrinter.printWithCondition(piece);
    }
  }

  public static CharSequence printWithCondition(final PolynomialPiece piece) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    String _paramConstraintsToConditionals = ISLPrintingUtils.paramConstraintsToConditionals(piece.getSet());
    _builder.append(_paramConstraintsToConditionals);
    _builder.append(") ? ");
    CharSequence _printTerms = PolynomialExprPrinter.printTerms(piece);
    _builder.append(_printTerms);
    return _builder;
  }

  public static CharSequence printTerms(final PolynomialPiece piece) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    final Function1<PolynomialTerm, CharSequence> _function = (PolynomialTerm it) -> {
      return PolynomialExprPrinter.print(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<PolynomialTerm, CharSequence>map(piece.getTerms(), _function), "+");
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  public static CharSequence print(final PolynomialTerm term) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    String _value = term.value();
    _builder.append(_value);
    _builder.append(")");
    return _builder;
  }

  public static boolean isLast(final PolynomialPiece piece) {
    boolean _xblockexpression = false;
    {
      final int numPieces = piece.getPolynomial().getPieces().size();
      int _indexOf = piece.getPolynomial().getPieces().indexOf(piece);
      _xblockexpression = (_indexOf == (numPieces - 1));
    }
    return _xblockexpression;
  }
}
