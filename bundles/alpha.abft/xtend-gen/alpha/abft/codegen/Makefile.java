package alpha.abft.codegen;

import alpha.model.AlphaSystem;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class Makefile {
  public static CharSequence generateMakefile(final AlphaSystem inputSystem, final AlphaSystem v1System, final AlphaSystem v2System) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("all: baseline v1 v2");
    _builder.newLine();
    _builder.newLine();
    _builder.append("baseline: ");
    String _name = inputSystem.getName();
    _builder.append(_name);
    _builder.append(".c");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("gcc -o $@ $^");
    _builder.newLine();
    _builder.newLine();
    _builder.append("v1: ");
    String _name_1 = v1System.getName();
    _builder.append(_name_1);
    _builder.append(".c");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("gcc -o $@ $^");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("v2: ");
    String _name_2 = v2System.getName();
    _builder.append(_name_2);
    _builder.append(".c");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("gcc -o $@ $^");
    _builder.newLine();
    _builder.newLine();
    _builder.append("clean:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("rm -f ");
    String _name_3 = inputSystem.getName();
    _builder.append(_name_3, "\t");
    _builder.append(" ");
    String _name_4 = v1System.getName();
    _builder.append(_name_4, "\t");
    _builder.append(" ");
    String _name_5 = v2System.getName();
    _builder.append(_name_5, "\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
