package alpha.abft.codegen;

import alpha.model.AlphaSystem;
import com.google.common.collect.Iterables;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class Makefile {
  public static String generateMakefile(final AlphaSystem system, final List<List<Integer>> sizes) {
    String _xblockexpression = null;
    {
      final Function1<List<Integer>, String> _function = (List<Integer> it) -> {
        return IterableExtensions.join(it, "_");
      };
      final List<String> sizeSuffixes = ListExtensions.<List<Integer>, String>map(sizes, _function);
      final String name = system.getName();
      final Function1<String, String> _function_1 = (String s) -> {
        StringConcatenation _builder = new StringConcatenation();
        String _name = system.getName();
        _builder.append(_name);
        _builder.append("_abft_v1_");
        _builder.append(s);
        return _builder.toString();
      };
      final List<String> v1DepTargets = ListExtensions.<String, String>map(sizeSuffixes, _function_1);
      final Function1<String, String> _function_2 = (String s) -> {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(s);
        _builder.append(".o: src/");
        _builder.append(s);
        _builder.append(".c src/time.o mkdir");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("gcc -c -o src/$@ $<");
        _builder.newLine();
        return _builder.toString();
      };
      final List<String> v1Targets = ListExtensions.<String, String>map(v1DepTargets, _function_2);
      final Function1<String, String> _function_3 = (String s) -> {
        StringConcatenation _builder = new StringConcatenation();
        String _name = system.getName();
        _builder.append(_name);
        _builder.append("_abft_v2_");
        _builder.append(s);
        return _builder.toString();
      };
      final List<String> v2DepTargets = ListExtensions.<String, String>map(sizeSuffixes, _function_3);
      final Function1<String, String> _function_4 = (String s) -> {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(s);
        _builder.append(".o: src/");
        _builder.append(s);
        _builder.append(".c src/time.o mkdir");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("gcc -c -o src/$@ $<");
        _builder.newLine();
        return _builder.toString();
      };
      final List<String> v2Targets = ListExtensions.<String, String>map(v2DepTargets, _function_4);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("all: bin/");
      _builder.append(name);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("mkdir:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("mkdir -p bin");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("src/time.o: src/time.c");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("gcc -c -o $@ $<");
      _builder.newLine();
      _builder.newLine();
      _builder.append(name);
      _builder.append(".o: src/");
      _builder.append(name);
      _builder.append(".c mkdir");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("gcc -c -o src/$@ $<");
      _builder.newLine();
      _builder.newLine();
      String _join = IterableExtensions.join(v1Targets, "\n\n");
      _builder.append(_join);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      String _join_1 = IterableExtensions.join(v2Targets, "\n\n");
      _builder.append(_join_1);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("bin/");
      _builder.append(name);
      _builder.append(": src/");
      _builder.append(name);
      _builder.append("-wrapper.c src/");
      _builder.append(name);
      _builder.append(".o ");
      final Function1<String, String> _function_5 = (String t) -> {
        return (("src/" + t) + ".o");
      };
      String _join_2 = IterableExtensions.join(IterableExtensions.<String, String>map(Iterables.<String>concat(v1DepTargets, v2DepTargets), _function_5), " ");
      _builder.append(_join_2);
      _builder.append(" src/time.o mkdir");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("gcc -o $@ src/");
      _builder.append(name, "\t");
      _builder.append("-wrapper.c src/");
      _builder.append(name, "\t");
      _builder.append(".o ");
      final Function1<String, String> _function_6 = (String t) -> {
        return (("src/" + t) + ".o");
      };
      String _join_3 = IterableExtensions.join(IterableExtensions.<String, String>map(Iterables.<String>concat(v1DepTargets, v2DepTargets), _function_6), " ");
      _builder.append(_join_3, "\t");
      _builder.append(" src/time.o");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("clean:");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("rm -f src/*.o bin/*");
      _builder.newLine();
      final String content = _builder.toString();
      _xblockexpression = content;
    }
    return _xblockexpression;
  }
}
