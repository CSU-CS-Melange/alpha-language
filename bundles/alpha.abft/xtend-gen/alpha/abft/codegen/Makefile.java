package alpha.abft.codegen;

import alpha.model.AlphaSystem;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class Makefile {
  public static CharSequence generateMakefile(final AlphaSystem system) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("all: system");
    _builder.newLine();
    return _builder;
  }
}
