package alpha.abft.codegen;

import alpha.abft.ABFT;
import alpha.abft.codegen.util.AlphaSchedule;
import alpha.abft.codegen.util.MemoryMap;
import alpha.abft.codegen.util.StatementVisitor;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.CaseExpression;
import alpha.model.RestrictExpression;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class BenchmarkInstance {
  public static Iterable<String> spatialIndexNames(final AlphaSystem system) {
    final List<String> indexNames = system.getOutputs().get(0).getDomain().getIndexNames();
    int _size = indexNames.size();
    final Function1<Integer, String> _function = (Integer i) -> {
      return indexNames.get((i).intValue());
    };
    return IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
  }

  public static String stmt(final ISLUnionSet uset, final String name) {
    String _xblockexpression = null;
    {
      final Function1<ISLSet, Boolean> _function = (ISLSet s) -> {
        String _tupleName = s.getTupleName();
        return Boolean.valueOf(Objects.equal(_tupleName, name));
      };
      final ISLSet set = IterableExtensions.<ISLSet>findFirst(uset.getSets(), _function);
      String _tupleName = set.getTupleName();
      String _plus = (_tupleName + "[");
      String _join = IterableExtensions.join(set.getIndexNames(), ",");
      String _plus_1 = (_plus + _join);
      _xblockexpression = (_plus_1 + "]");
    }
    return _xblockexpression;
  }

  public static MemoryMap baselineMemoryMap(final AlphaSystem system) {
    final Variable outVar = system.getOutputs().get(0);
    final List<String> indexNames = outVar.getDomain().getIndexNames();
    final String spatialIndexStr = IterableExtensions.join(BenchmarkInstance.spatialIndexNames(system), ",");
    final String paramStr = ABFT.buildParamStr(outVar);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(paramStr);
    _builder.append("->{[t,");
    _builder.append(spatialIndexStr);
    _builder.append("]->[t mod 2,");
    _builder.append(spatialIndexStr);
    _builder.append("]}");
    final String outMap = _builder.toString();
    return new MemoryMap(system).setMemoryMap("Y", "Y", outMap, ((String[])Conversions.unwrapArray(indexNames, String.class)));
  }

  public static AlphaSchedule baselineSchedule(final AlphaSystem system) {
    AlphaSchedule _xblockexpression = null;
    {
      final SystemBody body = system.getSystemBodies().get(0);
      AlphaExpression _expr = body.getStandardEquations().get(0).getExpr();
      final CaseExpression ce = ((CaseExpression) _expr);
      final Function1<AlphaExpression, RestrictExpression> _function = (AlphaExpression e) -> {
        return ((RestrictExpression) e);
      };
      final Function1<RestrictExpression, AlphaExpression> _function_1 = (RestrictExpression it) -> {
        return it.getExpr();
      };
      final List<AlphaExpression> exprs = ListExtensions.<RestrictExpression, AlphaExpression>map(ListExtensions.<AlphaExpression, RestrictExpression>map(ce.getExprs(), _function), _function_1);
      final ISLSpace space = ce.getContextDomain().getSpace();
      final Function1<AlphaExpression, ISLSet> _function_2 = (AlphaExpression it) -> {
        return it.getContextDomain().copy();
      };
      ISLUnionSet _unionSet = ISLSet.buildUniverse(space.copy()).toUnionSet();
      Pair<Integer, ISLUnionSet> _mappedTo = Pair.<Integer, ISLUnionSet>of(Integer.valueOf(0), _unionSet);
      final Function2<Pair<Integer, ISLUnionSet>, ISLSet, Pair<Integer, ISLUnionSet>> _function_3 = (Pair<Integer, ISLUnionSet> ret, ISLSet d) -> {
        Pair<Integer, ISLUnionSet> _xblockexpression_1 = null;
        {
          final Integer i = ret.getKey();
          final ISLUnionSet set = ret.getValue();
          ISLUnionSet _union = d.setTupleName(("S" + i)).toUnionSet().union(set);
          _xblockexpression_1 = Pair.<Integer, ISLUnionSet>of(Integer.valueOf(((i).intValue() + 1)), _union);
        }
        return _xblockexpression_1;
      };
      final ISLUnionSet domain = IterableExtensions.<ISLSet, Pair<Integer, ISLUnionSet>>fold(ListExtensions.<AlphaExpression, ISLSet>map(exprs, _function_2), _mappedTo, _function_3).getValue();
      final String S0 = BenchmarkInstance.stmt(domain, "S0");
      final String S1 = BenchmarkInstance.stmt(domain, "S1");
      final String S2 = BenchmarkInstance.stmt(domain, "S2");
      ISLContext _context = domain.getContext();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("domain: \"");
      String _string = domain.toString();
      _builder.append(_string);
      _builder.append("\"");
      _builder.newLineIfNotEmpty();
      _builder.append("child:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("sequence:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      _builder.append(S0, "  ");
      _builder.append("}\"");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      _builder.append(S1, "  ");
      _builder.append("; ");
      _builder.append(S2, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("child:");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("schedule: \"[T,N]->[\\");
      _builder.newLine();
      _builder.append("      \t");
      _builder.append("{ ");
      _builder.append(S1, "      \t");
      _builder.append("->[t]; ");
      _builder.append(S2, "      \t");
      _builder.append("->[t] } \\");
      _builder.newLineIfNotEmpty();
      _builder.append("      ");
      _builder.append("]\"");
      _builder.newLine();
      final ISLSchedule islSchedule = ISLSchedule.buildFromString(_context, _builder.toString());
      final HashMap<String, AlphaExpression> exprStmtMap = CollectionLiterals.<String, AlphaExpression>newHashMap();
      final Consumer<AlphaExpression> _function_4 = (AlphaExpression e) -> {
        final int i = exprs.indexOf(e);
        exprStmtMap.put(("S" + Integer.valueOf(i)), e);
      };
      exprs.forEach(_function_4);
      _xblockexpression = new AlphaSchedule(islSchedule, exprStmtMap);
    }
    return _xblockexpression;
  }

  public static MemoryMap v1MemoryMap(final AlphaSystem system) {
    return new MemoryMap(system);
  }

  public static AlphaSchedule v1Schedule(final AlphaSystem system, final int[] tileSizes) {
    AlphaSchedule _xblockexpression = null;
    {
      final SystemBody body = system.getSystemBodies().get(0);
      final Variable outVar = system.getOutputs().get(0);
      final HashMap<Variable, List<AlphaExpression>> exprs = StatementVisitor.apply(system);
      final HashMap<String, AlphaExpression> exprStmtMap = CollectionLiterals.<String, AlphaExpression>newHashMap();
      final ISLSpace space = system.getParameterDomain().getSpace();
      final Function1<Map.Entry<Variable, List<AlphaExpression>>, ISLUnionSet> _function = (Map.Entry<Variable, List<AlphaExpression>> it) -> {
        ISLUnionSet _xblockexpression_1 = null;
        {
          final Variable variable = it.getKey();
          final List<AlphaExpression> childExprs = it.getValue();
          ISLUnionSet _unionSet = ISLSet.buildEmpty(space.copy()).toUnionSet();
          Pair<Integer, ISLUnionSet> _mappedTo = Pair.<Integer, ISLUnionSet>of(Integer.valueOf(0), _unionSet);
          final Function2<Pair<Integer, ISLUnionSet>, AlphaExpression, Pair<Integer, ISLUnionSet>> _function_1 = (Pair<Integer, ISLUnionSet> kv, AlphaExpression expr) -> {
            Pair<Integer, ISLUnionSet> _xblockexpression_2 = null;
            {
              final Integer i = kv.getKey();
              final ISLUnionSet set = kv.getValue();
              StringConcatenation _builder = new StringConcatenation();
              _builder.append("S_");
              String _name = variable.getName();
              _builder.append(_name);
              _builder.append("_");
              Integer _key = kv.getKey();
              _builder.append(_key);
              final String name = _builder.toString();
              exprStmtMap.put(name, expr);
              ISLUnionSet _union = expr.getContextDomain().copy().setTupleName(name).toUnionSet().union(set);
              _xblockexpression_2 = Pair.<Integer, ISLUnionSet>of(Integer.valueOf(((i).intValue() + 1)), _union);
            }
            return _xblockexpression_2;
          };
          _xblockexpression_1 = IterableExtensions.<AlphaExpression, Pair<Integer, ISLUnionSet>>fold(childExprs, _mappedTo, _function_1).getValue();
        }
        return _xblockexpression_1;
      };
      final Function2<ISLUnionSet, ISLUnionSet, ISLUnionSet> _function_1 = (ISLUnionSet d1, ISLUnionSet d2) -> {
        return d1.union(d2);
      };
      final ISLUnionSet domain = IterableExtensions.<ISLUnionSet>reduce(IterableExtensions.<Map.Entry<Variable, List<AlphaExpression>>, ISLUnionSet>map(exprs.entrySet(), _function), _function_1);
      final Consumer<ISLSet> _function_2 = (ISLSet d) -> {
        InputOutput.<ISLSet>println(d);
      };
      domain.getSets().forEach(_function_2);
      final Function1<ISLSet, Boolean> _function_3 = (ISLSet s) -> {
        return Boolean.valueOf(s.getTupleName().contains("S_W_"));
      };
      final Function1<ISLSet, String> _function_4 = (ISLSet s) -> {
        String _tupleName = s.getTupleName();
        String _plus = (_tupleName + "[");
        String _join = IterableExtensions.join(s.getIndexNames(), ",");
        String _plus_1 = (_plus + _join);
        return (_plus_1 + "]");
      };
      final Iterable<String> Ws = IterableExtensions.<ISLSet, String>map(IterableExtensions.<ISLSet>filter(domain.getSets(), _function_3), _function_4);
      final String patch0 = BenchmarkInstance.stmt(domain, "S_patch_0");
      final String patch1 = BenchmarkInstance.stmt(domain, "S_patch_1");
      final String patch2 = BenchmarkInstance.stmt(domain, "S_patch_2");
      final String patchNR = BenchmarkInstance.stmt(domain, "S_patch_NR_0");
      final String C1 = BenchmarkInstance.stmt(domain, "S_C1_0");
      final String C2 = BenchmarkInstance.stmt(domain, "S_C2_0");
      final String I = BenchmarkInstance.stmt(domain, "S_I_0");
      final String Y0 = BenchmarkInstance.stmt(domain, "S_Y_0");
      final String Y1 = BenchmarkInstance.stmt(domain, "S_Y_1");
      final String Y2 = BenchmarkInstance.stmt(domain, "S_Y_2");
      final String paramStr = ABFT.buildParamStr(outVar);
      final int TT = tileSizes[0];
      final int TI = tileSizes[1];
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("domain: \"");
      String _string = domain.toString();
      _builder.append(_string);
      _builder.append("\"");
      _builder.newLineIfNotEmpty();
      _builder.append("child:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("sequence:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      String _join = IterableExtensions.join(Ws, ";");
      _builder.append(_join, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      _builder.append(patch0, "  ");
      _builder.append("; ");
      _builder.append(patch1, "  ");
      _builder.append("; ");
      _builder.append(patch2, "  ");
      _builder.append("; ");
      _builder.append(patchNR, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("child:");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("schedule: \"");
      _builder.append(paramStr, "      ");
      _builder.append("->[\\");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("{ ");
      _builder.append(patch0, "        ");
      _builder.append("->[w]; ");
      _builder.append(patch1, "        ");
      _builder.append("->[w]; ");
      _builder.append(patch2, "        ");
      _builder.append("->[w]; ");
      _builder.append(patchNR, "        ");
      _builder.append("->[w]} \\");
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
      _builder.append(patchNR, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("- filter: \"{ ");
      _builder.append(patch0, "        ");
      _builder.append("; ");
      _builder.append(patch1, "        ");
      _builder.append("; ");
      _builder.append(patch2, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      _builder.append(Y0, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      _builder.append(Y1, "  ");
      _builder.append("; ");
      _builder.append(Y2, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("child:");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("schedule: \"");
      _builder.append(paramStr, "      ");
      _builder.append("->[\\");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("{ ");
      _builder.append(Y1, "        ");
      _builder.append("->[t]; ");
      _builder.append(Y2, "        ");
      _builder.append("->[t] }, \\");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("{ ");
      _builder.append(Y1, "        ");
      _builder.append("->[i]; ");
      _builder.append(Y2, "        ");
      _builder.append("->[i] }, \\");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("{ ");
      _builder.append(Y1, "        ");
      _builder.append("->[j]; ");
      _builder.append(Y2, "        ");
      _builder.append("->[j] } \\");
      _builder.newLineIfNotEmpty();
      _builder.append("      ");
      _builder.append("]\"");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      _builder.append(C1, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      _builder.append(C2, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("- filter: \"{ ");
      _builder.append(I, "  ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.newLine();
      final ISLSchedule islSchedule = ISLUtil.toISLSchedule(_builder);
      _xblockexpression = new AlphaSchedule(islSchedule, exprStmtMap);
    }
    return _xblockexpression;
  }

  public static MemoryMap v2MemoryMap(final AlphaSystem system) {
    try {
      throw new Exception("Not yet implemented");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static AlphaSchedule v2Schedule(final AlphaSystem system, final int[] tileSizes) {
    try {
      throw new Exception("Not yet implemented");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
