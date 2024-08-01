package alpha.abft.codegen;

import alpha.model.AlphaSystem;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class Makefile {
  public static CharSequence generateMakefile(final AlphaSystem inputSystem, final AlphaSystem v1System, final AlphaSystem v2System) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("all: bin/");
    String _name = inputSystem.getName();
    _builder.append(_name);
    _builder.append(" bin/");
    String _name_1 = v1System.getName();
    _builder.append(_name_1);
    _builder.append(" bin/");
    String _name_2 = v2System.getName();
    _builder.append(_name_2);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("mkdir:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("mkdir -p bin");
    _builder.newLine();
    _builder.newLine();
    _builder.append("bin/");
    String _name_3 = inputSystem.getName();
    _builder.append(_name_3);
    _builder.append(": src/");
    String _name_4 = inputSystem.getName();
    _builder.append(_name_4);
    _builder.append(".c mkdir");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("gcc -o $@ $<");
    _builder.newLine();
    _builder.newLine();
    _builder.append("bin/");
    String _name_5 = v1System.getName();
    _builder.append(_name_5);
    _builder.append(": src/");
    String _name_6 = v1System.getName();
    _builder.append(_name_6);
    _builder.append(".c mkdir");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("gcc -o $@ $<");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("bin/");
    String _name_7 = v2System.getName();
    _builder.append(_name_7);
    _builder.append(": src/");
    String _name_8 = v2System.getName();
    _builder.append(_name_8);
    _builder.append(".c mkdir");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("gcc -o $@ $<");
    _builder.newLine();
    _builder.newLine();
    _builder.append("clean:");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("rm -f bin/*");
    _builder.newLine();
    return _builder;
  }
}
