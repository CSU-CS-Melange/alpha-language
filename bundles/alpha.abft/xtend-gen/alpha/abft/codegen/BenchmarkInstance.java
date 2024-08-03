package alpha.abft.codegen;

import alpha.abft.ABFT;
import alpha.abft.codegen.util.MemoryMap;
import alpha.model.AlphaSystem;
import alpha.model.Variable;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class BenchmarkInstance {
  public static MemoryMap baselineMemoryMap(final AlphaSystem system) {
    return BenchmarkInstance.yMod2MemoryMap(system);
  }

  public static MemoryMap v1MemoryMap(final AlphaSystem system) {
    return BenchmarkInstance.baselineMemoryMap(system);
  }

  public static MemoryMap v2MemoryMap(final AlphaSystem system) {
    final Function1<Variable, Boolean> _function = (Variable it) -> {
      return Boolean.valueOf(it.getName().startsWith("C2_NR"));
    };
    final Function2<MemoryMap, Variable, MemoryMap> _function_1 = (MemoryMap mm, Variable name) -> {
      return mm.setMemoryMap(name, "C2");
    };
    return IterableExtensions.<Variable, MemoryMap>fold(IterableExtensions.<Variable>filter(system.getLocals(), _function), 
      BenchmarkInstance.baselineMemoryMap(system), _function_1);
  }

  public static CharSequence baselineSchedule() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("domain: \"domain\'\"");
    _builder.newLine();
    _builder.append("child:");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("sequence:");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("- filter: \"{ Y\' }\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("child:");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("schedule: \"params\'->[\\");
    _builder.newLine();
    _builder.append("      \t");
    _builder.append("{ Y\'->[t] } \\");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("]\"");
    _builder.newLine();
    return _builder;
  }

  public static CharSequence v1Schedule(final int TT) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("domain: \"domain\'\"");
    _builder.newLine();
    _builder.append("child:");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("sequence:");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("- filter: \"{ W\' }\"");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("- filter: \"{ patch\'; patch_NR\' }\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("child:");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("schedule: \"params\'->[\\");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{ patch\'->[w]; patch_NR\'->[w]} \\");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("]\"");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("- filter: \"{ I\'; C1\'; C2\'; Y\' }\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("child:");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("schedule: \"params\'->[\\");
    _builder.newLine();
    _builder.append("      \t");
    _builder.append("{ C1\'->[tt]; C2\'->[tt-1]; I\'->[tt]; Y\'->[t/");
    _builder.append(TT, "      \t");
    _builder.append("] }, \\");
    _builder.newLineIfNotEmpty();
    _builder.append("      \t");
    _builder.append("{ C1\'->[");
    _builder.append(TT, "      \t");
    _builder.append("tt]; C2\'->[");
    _builder.append(TT, "      \t");
    _builder.append("tt-");
    _builder.append(TT, "      \t");
    _builder.append("]; I\'->[");
    _builder.append(TT, "      \t");
    _builder.append("tt]; Y\'->[t] } \\");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("]\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("child:");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("sequence:");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("- filter: \"{ Y\' }\"");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("- filter: \"{ C1\' }\"");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("- filter: \"{ C2\' }\"");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("- filter: \"{ I\' }\"");
    _builder.newLine();
    return _builder;
  }

  public static CharSequence v2Schedule(final AlphaSystem system, final int TT) {
    CharSequence _xblockexpression = null;
    {
      final Function1<Variable, Boolean> _function = (Variable it) -> {
        return Boolean.valueOf(it.getName().startsWith("C2_NR"));
      };
      final Function1<Variable, String> _function_1 = (Variable it) -> {
        String _name = it.getName();
        return (_name + "\'");
      };
      final Iterable<String> c2nrs = IterableExtensions.<Variable, String>map(IterableExtensions.<Variable>filter(system.getLocals(), _function), _function_1);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("domain: \"domain\'\"");
      _builder.newLine();
      _builder.append("child:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("sequence:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ W\' }\"");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ WKernel\' }\"");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ WCombos\' }\"");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ WAll\' }\"");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("child:");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("schedule: \"params\'->[\\");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("{ WAll\'->[w] } \\");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("]\"");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ \\");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("Y\'; \\");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("C1\'; \\");
      _builder.newLine();
      _builder.append("      ");
      String _join = IterableExtensions.join(c2nrs, "; \\\n");
      _builder.append(_join, "      ");
      _builder.append("; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("      ");
      _builder.append("C2\'; \\");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("I_NR\'; \\");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("I\' \\");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("}\"");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("child:");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("schedule: \"params\'->[\\");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("{ \\");
      _builder.newLine();
      _builder.append("          ");
      _builder.append("Y\'->[t]; \\");
      _builder.newLine();
      _builder.append("          ");
      _builder.append("C1\'->[");
      _builder.append(TT, "          ");
      _builder.append("tt]; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("          ");
      _builder.append("C2\'->[");
      _builder.append(TT, "          ");
      _builder.append("tt]; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("          ");
      final Function1<String, String> _function_2 = (String c2nr) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(c2nr);
        _builder_1.append("->[");
        _builder_1.append(TT);
        _builder_1.append("tt-w]");
        return _builder_1.toString();
      };
      String _join_1 = IterableExtensions.join(IterableExtensions.<String, String>map(c2nrs, _function_2), "; \\\n");
      _builder.append(_join_1, "          ");
      _builder.append("; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("          ");
      _builder.append("I\'->[");
      _builder.append(TT, "          ");
      _builder.append("tt]; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("          ");
      _builder.append("I_NR\'->[");
      _builder.append(TT, "          ");
      _builder.append("tt] \\");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("} \\");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("]\"");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("child:");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("sequence:");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("- filter: \"{ Y\' }\"");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("- filter: \"{ C1\' }\"");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("- filter: \"{ \\");
      _builder.newLine();
      _builder.append("            ");
      String _join_2 = IterableExtensions.join(c2nrs, "; \\\n");
      _builder.append(_join_2, "            ");
      _builder.append(" \\");
      _builder.newLineIfNotEmpty();
      _builder.append("          ");
      _builder.append("}\"");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("- filter: \"{ C2\' }\"");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("- filter: \"{ I_NR\' }\"");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("- filter: \"{ I\' }\"");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  /**
   * Returns the memory map for Y%2 accessing
   */
  public static MemoryMap yMod2MemoryMap(final AlphaSystem system) {
    final Variable outVar = system.getOutputs().get(0);
    final List<String> indexNames = system.getOutputs().get(0).getDomain().getIndexNames();
    int _size = indexNames.size();
    final Function1<Integer, String> _function = (Integer i) -> {
      return indexNames.get((i).intValue());
    };
    final String spatialIndexStr = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function), ",");
    final String paramStr = ABFT.buildParamStr(outVar);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(paramStr);
    _builder.append("->{[t,");
    _builder.append(spatialIndexStr);
    _builder.append("]->[t mod 2,");
    _builder.append(spatialIndexStr);
    _builder.append("]}");
    final String outMap = _builder.toString();
    return new MemoryMap(system).setMemoryMap("Y", "Y", outMap, ((String[])Conversions.unwrapArray(outVar.getDomain().getIndexNames(), String.class)));
  }
}
