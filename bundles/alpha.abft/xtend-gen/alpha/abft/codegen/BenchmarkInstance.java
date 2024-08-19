package alpha.abft.codegen;

import alpha.abft.ABFT;
import alpha.abft.codegen.util.MemoryMap;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.CaseExpression;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import com.google.common.base.Objects;
import java.util.List;
import org.eclipse.emf.common.util.EList;
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

  public static MemoryMap v3MemoryMap(final AlphaSystem system) {
    return BenchmarkInstance.baselineMemoryMap(system);
  }

  public static CharSequence baselineSchedule(final AlphaSystem system) {
    CharSequence _xblockexpression = null;
    {
      final Function1<StandardEquation, Boolean> _function = (StandardEquation it) -> {
        String _name = it.getVariable().getName();
        return Boolean.valueOf(Objects.equal(_name, "Y"));
      };
      AlphaExpression _expr = IterableExtensions.<StandardEquation>findFirst(system.getSystemBodies().get(0).getStandardEquations(), _function).getExpr();
      final EList<AlphaExpression> yCaseBranches = ((CaseExpression) _expr).getExprs();
      int _size = yCaseBranches.size();
      final Function1<Integer, String> _function_1 = (Integer i) -> {
        return (("Y_cb" + i) + "\'");
      };
      final Iterable<String> yStmts = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size, true), _function_1);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("domain: \"domain\'\"");
      _builder.newLine();
      _builder.append("child:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("sequence:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      String _join = IterableExtensions.join(yStmts, "; ");
      _builder.append(_join, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("child:");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("schedule: \"params\'->[\\");
      _builder.newLine();
      _builder.append("      \t");
      _builder.append("{ ");
      String _join_1 = IterableExtensions.join(yStmts, "->[t]; ");
      _builder.append(_join_1, "      \t");
      _builder.append("->[t] } \\");
      _builder.newLineIfNotEmpty();
      _builder.append("      ");
      _builder.append("]\"");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  public static CharSequence v1Schedule(final AlphaSystem system, final int[] tileSizes) {
    CharSequence _xblockexpression = null;
    {
      final int TT = tileSizes[0];
      int _size = ((List<Integer>)Conversions.doWrapArray(tileSizes)).size();
      final Function1<Integer, Integer> _function = (Integer i) -> {
        return Integer.valueOf(tileSizes[(i).intValue()]);
      };
      final List<Integer> TSs = IterableExtensions.<Integer>toList(IterableExtensions.<Integer, Integer>map(new ExclusiveRange(1, _size, true), _function));
      final Function1<StandardEquation, Boolean> _function_1 = (StandardEquation it) -> {
        String _name = it.getVariable().getName();
        return Boolean.valueOf(Objects.equal(_name, "Y"));
      };
      AlphaExpression _expr = IterableExtensions.<StandardEquation>findFirst(system.getSystemBodies().get(0).getStandardEquations(), _function_1).getExpr();
      final EList<AlphaExpression> yCaseBranches = ((CaseExpression) _expr).getExprs();
      int _size_1 = yCaseBranches.size();
      final Function1<Integer, String> _function_2 = (Integer i) -> {
        return (("Y_cb" + i) + "\'");
      };
      final Iterable<String> yStmts = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size_1, true), _function_2);
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
      _builder.append("- filter: \"{ I\'; C1\'; C2\'; ");
      String _join = IterableExtensions.join(yStmts, "; ");
      _builder.append(_join, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("child:");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("schedule: \"params\'->[\\");
      _builder.newLine();
      _builder.append("      \t");
      _builder.append("{ C1\'->[tt]; C2\'->[tt-1]; I\'->[tt]; ");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("->[t/");
      _builder_1.append(TT);
      _builder_1.append("]; ");
      String _join_1 = IterableExtensions.join(yStmts, _builder_1);
      _builder.append(_join_1, "      \t");
      _builder.append("->[t/");
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
      _builder.append("tt]; ");
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("->[t]; ");
      String _join_2 = IterableExtensions.join(yStmts, _builder_2);
      _builder.append(_join_2, "      \t");
      _builder.append("->[t] } \\");
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
      _builder.append("- filter: \"{ ");
      String _join_3 = IterableExtensions.join(yStmts, "; ");
      _builder.append(_join_3, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("- filter: \"{ C1\'; C2\' }\"");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("- filter: \"{ I\' }\"");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
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
      final Function1<StandardEquation, Boolean> _function_2 = (StandardEquation it) -> {
        String _name = it.getVariable().getName();
        return Boolean.valueOf(Objects.equal(_name, "Y"));
      };
      AlphaExpression _expr = IterableExtensions.<StandardEquation>findFirst(system.getSystemBodies().get(0).getStandardEquations(), _function_2).getExpr();
      final EList<AlphaExpression> yCaseBranches = ((CaseExpression) _expr).getExprs();
      int _size = yCaseBranches.size();
      final Function1<Integer, String> _function_3 = (Integer i) -> {
        return (("Y_cb" + i) + "\'");
      };
      final Iterable<String> yStmts = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size, true), _function_3);
      final Function1<StandardEquation, Boolean> _function_4 = (StandardEquation it) -> {
        String _name = it.getVariable().getName();
        return Boolean.valueOf(Objects.equal(_name, "C2"));
      };
      AlphaExpression _expr_1 = IterableExtensions.<StandardEquation>findFirst(system.getSystemBodies().get(0).getStandardEquations(), _function_4).getExpr();
      final EList<AlphaExpression> c2CaseBranches = ((CaseExpression) _expr_1).getExprs();
      int _size_1 = c2CaseBranches.size();
      final Function1<Integer, String> _function_5 = (Integer i) -> {
        return (("C2_cb" + i) + "\'");
      };
      final Iterable<String> c2cbes = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size_1, true), _function_5);
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
      String _join = IterableExtensions.join(yStmts, "; \\\n");
      _builder.append(_join, "      ");
      _builder.append("; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("      ");
      _builder.append("C1\'; \\");
      _builder.newLine();
      _builder.append("      ");
      String _join_1 = IterableExtensions.join(c2nrs, "; \\\n");
      _builder.append(_join_1, "      ");
      _builder.append("; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("      ");
      String _join_2 = IterableExtensions.join(c2cbes, "; \\\n");
      _builder.append(_join_2, "      ");
      _builder.append("; \\");
      _builder.newLineIfNotEmpty();
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
      String _join_3 = IterableExtensions.join(yStmts, "->[t]; \\\n");
      _builder.append(_join_3, "          ");
      _builder.append("->[t]; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("          ");
      _builder.append("C1\'->[");
      _builder.append(TT, "          ");
      _builder.append("tt]; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("          ");
      final Function1<String, String> _function_6 = (String c2cbe) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(c2cbe);
        _builder_1.append("->[");
        _builder_1.append(TT);
        _builder_1.append("tt]");
        return _builder_1.toString();
      };
      String _join_4 = IterableExtensions.join(IterableExtensions.<String, String>map(c2cbes, _function_6), "; \\\n");
      _builder.append(_join_4, "          ");
      _builder.append("; \\");
      _builder.newLineIfNotEmpty();
      _builder.append("          ");
      final Function1<String, String> _function_7 = (String c2nr) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(c2nr);
        _builder_1.append("->[");
        _builder_1.append(TT);
        _builder_1.append("tt-w]");
        return _builder_1.toString();
      };
      String _join_5 = IterableExtensions.join(IterableExtensions.<String, String>map(c2nrs, _function_7), "; \\\n");
      _builder.append(_join_5, "          ");
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
      _builder.append("- filter: \"{ ");
      String _join_6 = IterableExtensions.join(yStmts, "; ");
      _builder.append(_join_6, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("- filter: \"{ C1\'; ");
      String _join_7 = IterableExtensions.join(c2nrs, "; ");
      _builder.append(_join_7, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("- filter: \"{ ");
      String _join_8 = IterableExtensions.join(c2cbes, "; ");
      _builder.append(_join_8, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
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

  public static CharSequence v3Schedule(final AlphaSystem system, final int[] tileSizes) {
    CharSequence _xblockexpression = null;
    {
      final int H = tileSizes[0];
      final int L = tileSizes[1];
      final Function1<StandardEquation, Boolean> _function = (StandardEquation it) -> {
        String _name = it.getVariable().getName();
        return Boolean.valueOf(Objects.equal(_name, "Y"));
      };
      AlphaExpression _expr = IterableExtensions.<StandardEquation>findFirst(system.getSystemBodies().get(0).getStandardEquations(), _function).getExpr();
      final EList<AlphaExpression> yCaseBranches = ((CaseExpression) _expr).getExprs();
      int _size = yCaseBranches.size();
      final Function1<Integer, String> _function_1 = (Integer i) -> {
        return (("Y_cb" + i) + "\'");
      };
      final Iterable<String> yStmts = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size, true), _function_1);
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
      _builder.append("- filter: \"{ WExt\' }\"");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ Wi\' }\"");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ C1\'; C2\'; I\'; ");
      String _join = IterableExtensions.join(yStmts, "; ");
      _builder.append(_join, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("child:");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("schedule: \"params\'->[\\");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("{ C1\'->[");
      _builder.append(H, "        ");
      _builder.append("tt]; C2\'->[t]; I\'->[");
      _builder.append(H, "        ");
      _builder.append("tt]; ");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("->[t]; ");
      String _join_1 = IterableExtensions.join(yStmts, _builder_1);
      _builder.append(_join_1, "        ");
      _builder.append("->[t] } \\");
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
      _builder.append("- filter: \"{ ");
      String _join_2 = IterableExtensions.join(yStmts, "; ");
      _builder.append(_join_2, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("- filter: \"{ C1\'; C2\' }\"");
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
