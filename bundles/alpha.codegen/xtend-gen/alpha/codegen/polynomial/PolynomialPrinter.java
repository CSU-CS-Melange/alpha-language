package alpha.codegen.polynomial;

import alpha.codegen.Polynomial;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class PolynomialPrinter {
  protected static final int format = ISL_FORMAT.C.ordinal();

  public static CharSequence print(final Polynomial polynomial, final String variableName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// ");
    ISLPWQPolynomial _islPolynomial = polynomial.getIslPolynomial();
    _builder.append(_islPolynomial);
    _builder.newLineIfNotEmpty();
    _builder.append("long ");
    _builder.append(variableName);
    _builder.append(" = ");
    CharSequence _print = PolynomialPrinter.print(polynomial);
    _builder.append(_print);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  public static CharSequence print(final Polynomial polynomial) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    String __toString = ISLPWQPolynomial._toString(polynomial.getIslPolynomial(), PolynomialPrinter.format);
    _builder.append(__toString);
    _builder.append(")");
    return _builder;
  }

  public static CharSequence printMinusOne(final Polynomial polynomial) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    CharSequence _print = PolynomialPrinter.print(polynomial);
    _builder.append(_print);
    _builder.append(" - 1)");
    return _builder;
  }
}
