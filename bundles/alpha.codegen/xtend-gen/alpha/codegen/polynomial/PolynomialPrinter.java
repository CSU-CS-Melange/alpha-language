package alpha.codegen.polynomial;

import alpha.codegen.Polynomial;
import alpha.codegen.PolynomialPiece;
import alpha.codegen.PolynomialTerm;
import alpha.codegen.util.CodegenSwitch;
import alpha.codegen.util.ISLPrintingUtils;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class PolynomialPrinter extends CodegenSwitch<CharSequence> {
  private String variableName;

  public static String print(final Polynomial polynomial, final String variableName) {
    String _xblockexpression = null;
    {
      final PolynomialPrinter printer = new PolynomialPrinter(variableName);
      _xblockexpression = printer.doSwitch(polynomial).toString();
    }
    return _xblockexpression;
  }

  public PolynomialPrinter(final String variableName) {
    this.variableName = ("_card_" + variableName);
  }

  public CharSequence casePolynomial(final Polynomial p) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((this.variableName != null)) {
        _builder.append("// ");
        ISLPWQPolynomial _islPolynomial = p.getIslPolynomial();
        _builder.append(_islPolynomial);
        _builder.newLineIfNotEmpty();
        _builder.append("long ");
        _builder.append(this.variableName);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    final Function1<PolynomialPiece, CharSequence> _function = (PolynomialPiece it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<PolynomialPiece, CharSequence>map(p.getPieces(), _function), " else ");
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  public boolean isLast(final PolynomialPiece piece) {
    boolean _xblockexpression = false;
    {
      final int numPieces = piece.getPolynomial().getPieces().size();
      int _indexOf = piece.getPolynomial().getPieces().indexOf(piece);
      _xblockexpression = (_indexOf == (numPieces - 1));
    }
    return _xblockexpression;
  }

  public CharSequence casePolynomialPiece(final PolynomialPiece piece) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    boolean _isLast = this.isLast(piece);
    boolean _not = (!_isLast);
    if (_not) {
      _xifexpression = "if ";
    }
    _builder.append(_xifexpression);
    _builder.append("(");
    String _paramConstraintsToConditionals = ISLPrintingUtils.paramConstraintsToConditionals(piece.getSet());
    _builder.append(_paramConstraintsToConditionals);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(this.variableName, "  ");
    _builder.append(" = ");
    final Function1<PolynomialTerm, String> _function = (PolynomialTerm it) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("(");
      CharSequence _doSwitch = this.doSwitch(it);
      _builder_1.append(_doSwitch);
      _builder_1.append(")");
      return _builder_1.toString();
    };
    String _join = IterableExtensions.join(ListExtensions.<PolynomialTerm, String>map(piece.getTerms(), _function), "+");
    _builder.append(_join, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }

  public CharSequence casePolynomialTerm(final PolynomialTerm term) {
    StringConcatenation _builder = new StringConcatenation();
    String _value = term.value();
    _builder.append(_value);
    return _builder;
  }
}
