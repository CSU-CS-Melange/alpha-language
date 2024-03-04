package alpha.codegen.util;

import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;

@SuppressWarnings("all")
public class MemoryUtils {
  public static ISLPWQPolynomial card(final ISLSet domain) {
    return BarvinokBindings.card(domain.copy());
  }
}
