package alpha.abft.codegen;

import alpha.model.AlphaSystem;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class Makefile {
  public static String generateMakefile(final AlphaSystem system, final AlphaSystem systemV1, final AlphaSystem systemV2, final int[] tileSizes) {
    String _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      String _name = system.getName();
      _builder.append(_name);
      _builder.append(".");
      String _join = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(tileSizes)), ".");
      _builder.append(_join);
      final String name = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("FLAGS := -O3 -lm");
      _builder_1.newLine();
      _builder_1.append("all: bin/");
      _builder_1.append(name);
      _builder_1.append(".time bin/");
      _builder_1.append(name);
      _builder_1.append(".inj bin/");
      _builder_1.append(name);
      _builder_1.append(".complexity");
      _builder_1.newLineIfNotEmpty();
      _builder_1.newLine();
      _builder_1.append("mkbin:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("mkdir -p bin");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("src/time.o: src/time.c mkbin");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("gcc -c $(FLAGS) -o $@ $<");
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("src/");
      String _name_1 = system.getName();
      _builder_1.append(_name_1);
      _builder_1.append(".complexity.o: src/");
      String _name_2 = system.getName();
      _builder_1.append(_name_2);
      _builder_1.append(".c mkbin");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      _builder_1.append("gcc -c $(FLAGS) -o $@ $< -D");
      _builder_1.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY, "\t");
      _builder_1.newLineIfNotEmpty();
      {
        if ((systemV1 != null)) {
          _builder_1.append("src/");
          String _name_3 = systemV1.getName();
          _builder_1.append(_name_3);
          _builder_1.append(".complexity.o: src/");
          String _name_4 = systemV1.getName();
          _builder_1.append(_name_4);
          _builder_1.append(".c mkbin");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          _builder_1.append("gcc -c $(FLAGS) -o $@ $< -D");
          _builder_1.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY, "\t");
          _builder_1.newLineIfNotEmpty();
        }
      }
      {
        if ((systemV2 != null)) {
          _builder_1.append("src/");
          String _name_5 = systemV2.getName();
          _builder_1.append(_name_5);
          _builder_1.append(".complexity.o: src/");
          String _name_6 = systemV2.getName();
          _builder_1.append(_name_6);
          _builder_1.append(".c mkbin");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          _builder_1.append("gcc -c $(FLAGS) -o $@ $< -D");
          _builder_1.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY, "\t");
          _builder_1.newLineIfNotEmpty();
        }
      }
      _builder_1.append("bin/");
      _builder_1.append(name);
      _builder_1.append(".complexity: src/");
      String _name_7 = system.getName();
      _builder_1.append(_name_7);
      _builder_1.append("-wrapper.c src/");
      String _name_8 = system.getName();
      _builder_1.append(_name_8);
      _builder_1.append(".complexity.o src/time.o");
      CharSequence _xifexpression = null;
      if ((systemV1 != null)) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append(" ");
        _builder_2.append("src/");
        String _name_9 = systemV1.getName();
        _builder_2.append(_name_9, " ");
        _builder_2.append(".complexity.o");
        _xifexpression = _builder_2;
      }
      _builder_1.append(_xifexpression);
      CharSequence _xifexpression_1 = null;
      if ((systemV2 != null)) {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append(" ");
        _builder_3.append("src/");
        String _name_10 = systemV2.getName();
        _builder_3.append(_name_10, " ");
        _builder_3.append(".complexity.o");
        _xifexpression_1 = _builder_3;
      }
      _builder_1.append(_xifexpression_1);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      _builder_1.append("gcc $(FLAGS) -o $@ $^ -D");
      _builder_1.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("src/");
      String _name_11 = system.getName();
      _builder_1.append(_name_11);
      _builder_1.append(".time.o: src/");
      String _name_12 = system.getName();
      _builder_1.append(_name_12);
      _builder_1.append(".c mkbin");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      _builder_1.append("gcc -c $(FLAGS) -o $@ $< -D");
      _builder_1.append(SystemCodeGen.TIMING, "\t");
      _builder_1.newLineIfNotEmpty();
      {
        if ((systemV1 != null)) {
          _builder_1.append("src/");
          String _name_13 = systemV1.getName();
          _builder_1.append(_name_13);
          _builder_1.append(".time.o: src/");
          String _name_14 = systemV1.getName();
          _builder_1.append(_name_14);
          _builder_1.append(".c mkbin");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          _builder_1.append("gcc -c $(FLAGS) -o $@ $< -D");
          _builder_1.append(SystemCodeGen.TIMING, "\t");
          _builder_1.newLineIfNotEmpty();
        }
      }
      {
        if ((systemV2 != null)) {
          _builder_1.append("src/");
          String _name_15 = systemV2.getName();
          _builder_1.append(_name_15);
          _builder_1.append(".time.o: src/");
          String _name_16 = systemV2.getName();
          _builder_1.append(_name_16);
          _builder_1.append(".c mkbin");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          _builder_1.append("gcc -c $(FLAGS) -o $@ $< -D");
          _builder_1.append(SystemCodeGen.TIMING, "\t");
          _builder_1.newLineIfNotEmpty();
        }
      }
      _builder_1.append("bin/");
      _builder_1.append(name);
      _builder_1.append(".time: src/");
      String _name_17 = system.getName();
      _builder_1.append(_name_17);
      _builder_1.append("-wrapper.c src/");
      String _name_18 = system.getName();
      _builder_1.append(_name_18);
      _builder_1.append(".time.o src/time.o");
      CharSequence _xifexpression_2 = null;
      if ((systemV1 != null)) {
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append(" ");
        _builder_4.append("src/");
        String _name_19 = systemV1.getName();
        _builder_4.append(_name_19, " ");
        _builder_4.append(".time.o");
        _xifexpression_2 = _builder_4;
      }
      _builder_1.append(_xifexpression_2);
      CharSequence _xifexpression_3 = null;
      if ((systemV2 != null)) {
        StringConcatenation _builder_5 = new StringConcatenation();
        _builder_5.append(" ");
        _builder_5.append("src/");
        String _name_20 = systemV2.getName();
        _builder_5.append(_name_20, " ");
        _builder_5.append(".time.o");
        _xifexpression_3 = _builder_5;
      }
      _builder_1.append(_xifexpression_3);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      _builder_1.append("gcc $(FLAGS) -o $@ $^ -D");
      _builder_1.append(SystemCodeGen.TIMING, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.newLine();
      _builder_1.newLine();
      {
        if ((systemV1 != null)) {
          _builder_1.append("src/");
          String _name_21 = systemV1.getName();
          _builder_1.append(_name_21);
          _builder_1.append(".inj.o: src/");
          String _name_22 = systemV1.getName();
          _builder_1.append(_name_22);
          _builder_1.append(".c mkbin");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          _builder_1.append("gcc -c $(FLAGS) -o $@ $< -D");
          _builder_1.append(SystemCodeGen.ERROR_INJECTION, "\t");
          _builder_1.newLineIfNotEmpty();
        }
      }
      {
        if ((systemV2 != null)) {
          _builder_1.append("src/");
          String _name_23 = systemV2.getName();
          _builder_1.append(_name_23);
          _builder_1.append(".inj.o: src/");
          String _name_24 = systemV2.getName();
          _builder_1.append(_name_24);
          _builder_1.append(".c mkbin");
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\t");
          _builder_1.append("gcc -c $(FLAGS) -o $@ $<  -D");
          _builder_1.append(SystemCodeGen.ERROR_INJECTION, "\t");
          _builder_1.newLineIfNotEmpty();
        }
      }
      _builder_1.append("bin/");
      _builder_1.append(name);
      _builder_1.append(".inj: src/");
      String _name_25 = system.getName();
      _builder_1.append(_name_25);
      _builder_1.append("-wrapper.c src/");
      String _name_26 = system.getName();
      _builder_1.append(_name_26);
      _builder_1.append(".o src/time.o");
      CharSequence _xifexpression_4 = null;
      if ((systemV1 != null)) {
        StringConcatenation _builder_6 = new StringConcatenation();
        _builder_6.append(" ");
        _builder_6.append("src/");
        String _name_27 = systemV1.getName();
        _builder_6.append(_name_27, " ");
        _builder_6.append(".inj.o");
        _xifexpression_4 = _builder_6;
      }
      _builder_1.append(_xifexpression_4);
      CharSequence _xifexpression_5 = null;
      if ((systemV2 != null)) {
        StringConcatenation _builder_7 = new StringConcatenation();
        _builder_7.append(" ");
        _builder_7.append("src/");
        String _name_28 = systemV2.getName();
        _builder_7.append(_name_28, " ");
        _builder_7.append(".inj.o");
        _xifexpression_5 = _builder_7;
      }
      _builder_1.append(_xifexpression_5);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      _builder_1.append("gcc $(FLAGS) -o $@ $^ -D");
      _builder_1.append(SystemCodeGen.ERROR_INJECTION, "\t");
      _builder_1.newLineIfNotEmpty();
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.newLine();
      _builder_1.append("clean:");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("rm -f src/*.o bin/*");
      _builder_1.newLine();
      final String content = _builder_1.toString();
      _xblockexpression = content;
    }
    return _xblockexpression;
  }
}
