package alpha.abft.codegen;

import alpha.model.AlphaSystem;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class WrapperCodeGen {
  public static CharSequence generateWrapperCode(final AlphaSystem system) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// ");
    String _name = system.getName();
    _builder.append(_name);
    _builder.append("-wrapper.c");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
