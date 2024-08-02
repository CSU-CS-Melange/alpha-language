package alpha.abft.codegen;

import alpha.abft.ABFT;
import alpha.abft.codegen.util.AlphaSchedule;
import alpha.abft.codegen.util.MemoryMap;
import alpha.abft.codegen.util.StatementVisitor;
import alpha.commands.UtilityBase;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.CaseExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
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
    return BenchmarkInstance.baselineMemoryMap(system);
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
      _builder.append(I, "  ");
      _builder.append("; ");
      _builder.append(C1, "  ");
      _builder.append("; ");
      _builder.append(C2, "  ");
      _builder.append("; ");
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
      _builder.append("      \t");
      _builder.append("{ ");
      _builder.append(C1, "      \t");
      _builder.append("->[tt]; ");
      _builder.append(C2, "      \t");
      _builder.append("->[tt-1]; ");
      _builder.append(I, "      \t");
      _builder.append("->[tt]; ");
      _builder.append(Y1, "      \t");
      _builder.append("->[t/");
      _builder.append(TT, "      \t");
      _builder.append("]; ");
      _builder.append(Y2, "      \t");
      _builder.append("->[t/");
      _builder.append(TT, "      \t");
      _builder.append("] }, \\");
      _builder.newLineIfNotEmpty();
      _builder.append("      \t");
      _builder.append("{ ");
      _builder.append(C1, "      \t");
      _builder.append("->[");
      _builder.append(TT, "      \t");
      _builder.append("tt]; ");
      _builder.append(C2, "      \t");
      _builder.append("->[");
      _builder.append(TT, "      \t");
      _builder.append("tt-");
      _builder.append(TT, "      \t");
      _builder.append("]; ");
      _builder.append(I, "      \t");
      _builder.append("->[");
      _builder.append(TT, "      \t");
      _builder.append("tt]; ");
      _builder.append(Y1, "      \t");
      _builder.append("->[t]; ");
      _builder.append(Y2, "      \t");
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
      _builder.append(Y1, "        ");
      _builder.append("; ");
      _builder.append(Y2, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("- filter: \"{ ");
      _builder.append(C1, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("- filter: \"{ ");
      _builder.append(C2, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      _builder.append("        ");
      _builder.append("- filter: \"{ ");
      _builder.append(I, "        ");
      _builder.append(" }\"");
      _builder.newLineIfNotEmpty();
      final ISLSchedule islSchedule = ISLUtil.toISLSchedule(_builder);
      _xblockexpression = new AlphaSchedule(islSchedule, exprStmtMap);
    }
    return _xblockexpression;
  }

  public static MemoryMap v2MemoryMap(final AlphaSystem system) {
    final Function1<Variable, Boolean> _function = (Variable it) -> {
      return Boolean.valueOf(it.getName().contains("C2_NR"));
    };
    final Function2<MemoryMap, Variable, MemoryMap> _function_1 = (MemoryMap mm, Variable name) -> {
      return mm.setMemoryMap(name, "C2");
    };
    return IterableExtensions.<Variable, MemoryMap>fold(IterableExtensions.<Variable>filter(system.getLocals(), _function), 
      BenchmarkInstance.baselineMemoryMap(system), _function_1);
  }

  public static AlphaSchedule v2Schedule(final AlphaSystem system, final int[] tileSizes) {
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
      final Function1<ISLSet, String> _function_2 = (ISLSet it) -> {
        return it.toString();
      };
      final Consumer<String> _function_3 = (String d) -> {
        InputOutput.<String>println(d);
      };
      IterableExtensions.<String>sort(ListExtensions.<ISLSet, String>map(domain.getSets(), _function_2)).forEach(_function_3);
      final Function1<ISLSet, Boolean> _function_4 = (ISLSet s) -> {
        return Boolean.valueOf(s.getTupleName().contains("S_W_"));
      };
      final Function1<ISLSet, String> _function_5 = (ISLSet s) -> {
        String _tupleName = s.getTupleName();
        String _plus = (_tupleName + "[");
        String _join = IterableExtensions.join(s.getIndexNames(), ",");
        String _plus_1 = (_plus + _join);
        return (_plus_1 + "]");
      };
      final Iterable<String> Ws = IterableExtensions.<ISLSet, String>map(IterableExtensions.<ISLSet>filter(domain.getSets(), _function_4), _function_5);
      final String kernelW = BenchmarkInstance.stmt(domain, "S_kernelW_0");
      final String combosW = BenchmarkInstance.stmt(domain, "S_combosW_0");
      final String allW0 = BenchmarkInstance.stmt(domain, "S_allW_0");
      final String allW1 = BenchmarkInstance.stmt(domain, "S_allW_1");
      final String C1 = BenchmarkInstance.stmt(domain, "S_C1_0");
      final String I = BenchmarkInstance.stmt(domain, "S_I_0");
      final String INR = BenchmarkInstance.stmt(domain, "S_I_NR_0");
      AlphaExpression _expr = UtilityBase.GetEquation(body, "C2").getExpr();
      final int nbC2s = ((CaseExpression) _expr).getExprs().size();
      final Function1<Integer, String> _function_6 = (Integer i) -> {
        return BenchmarkInstance.stmt(domain, ("S_C2_" + i));
      };
      final ArrayList<String> C2Xs = CommonExtensions.<String>toArrayList(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, nbC2s, true), _function_6));
      final Function1<StandardEquation, Boolean> _function_7 = (StandardEquation eq) -> {
        return Boolean.valueOf(eq.getVariable().getName().contains("C2_NR"));
      };
      final Function1<StandardEquation, String> _function_8 = (StandardEquation it) -> {
        String _name = it.getName();
        String _plus = ("S_" + _name);
        return (_plus + "_0");
      };
      final Function1<String, String> _function_9 = (String it) -> {
        return BenchmarkInstance.stmt(domain, it);
      };
      final ArrayList<String> C2NRXs = CommonExtensions.<String>toArrayList(IterableExtensions.<String, String>map(IterableExtensions.<StandardEquation, String>map(IterableExtensions.<StandardEquation>filter(body.getStandardEquations(), _function_7), _function_8), _function_9));
      final String Y0 = BenchmarkInstance.stmt(domain, "S_Y_0");
      final String Y1 = BenchmarkInstance.stmt(domain, "S_Y_1");
      final String Y2 = BenchmarkInstance.stmt(domain, "S_Y_2");
      final String paramStr = ABFT.buildParamStr(outVar);
      final int TT = tileSizes[0];
      final String C2XsStr = IterableExtensions.join(C2Xs, ";");
      final String C2NRXsStr = IterableExtensions.join(C2NRXs, ";");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(Y1);
      _builder.append("->[t/");
      _builder.append(TT);
      _builder.append("]");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(Y2);
      _builder_1.append("->[t/");
      _builder_1.append(TT);
      _builder_1.append("]");
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(C1);
      _builder_2.append("->[tt]");
      StringConcatenation _builder_3 = new StringConcatenation();
      final Function1<String, String> _function_10 = (String it) -> {
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append(it);
        _builder_4.append("->[tt]");
        return _builder_4.toString();
      };
      String _join = IterableExtensions.join(ListExtensions.<String, String>map(C2Xs, _function_10), ";");
      _builder_3.append(_join);
      StringConcatenation _builder_4 = new StringConcatenation();
      final Function1<String, String> _function_11 = (String it) -> {
        StringConcatenation _builder_5 = new StringConcatenation();
        _builder_5.append(it);
        _builder_5.append("->[tt-1]");
        return _builder_5.toString();
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<String, String>map(C2NRXs, _function_11), ";");
      _builder_4.append(_join_1);
      StringConcatenation _builder_5 = new StringConcatenation();
      _builder_5.append(I);
      _builder_5.append("->[tt]; ");
      _builder_5.append(INR);
      _builder_5.append("->[tt]");
      final String band1 = IterableExtensions.join(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_builder.toString(), _builder_1.toString(), _builder_2.toString(), _builder_3.toString(), _builder_4.toString(), _builder_5.toString())), ";");
      StringConcatenation _builder_6 = new StringConcatenation();
      _builder_6.append(Y1);
      _builder_6.append("->[t]");
      StringConcatenation _builder_7 = new StringConcatenation();
      _builder_7.append(Y2);
      _builder_7.append("->[t]");
      StringConcatenation _builder_8 = new StringConcatenation();
      _builder_8.append(C1);
      _builder_8.append("->[");
      _builder_8.append(TT);
      _builder_8.append("tt]");
      StringConcatenation _builder_9 = new StringConcatenation();
      final Function1<String, String> _function_12 = (String c2x) -> {
        StringConcatenation _builder_10 = new StringConcatenation();
        _builder_10.append(c2x);
        _builder_10.append("->[");
        _builder_10.append(TT);
        _builder_10.append("tt]");
        return _builder_10.toString();
      };
      String _join_2 = IterableExtensions.join(ListExtensions.<String, String>map(C2Xs, _function_12), ";");
      _builder_9.append(_join_2);
      StringConcatenation _builder_10 = new StringConcatenation();
      final Function1<String, String> _function_13 = (String c2nrx) -> {
        StringConcatenation _builder_11 = new StringConcatenation();
        _builder_11.append(c2nrx);
        _builder_11.append("->[");
        _builder_11.append(TT);
        _builder_11.append("tt-w]");
        return _builder_11.toString();
      };
      String _join_3 = IterableExtensions.join(ListExtensions.<String, String>map(C2NRXs, _function_13), ";");
      _builder_10.append(_join_3);
      StringConcatenation _builder_11 = new StringConcatenation();
      _builder_11.append(I);
      _builder_11.append("->[");
      _builder_11.append(TT);
      _builder_11.append("tt]; ");
      _builder_11.append(INR);
      _builder_11.append("->[");
      _builder_11.append(TT);
      _builder_11.append("tt]");
      final String band2 = IterableExtensions.join(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_builder_6.toString(), _builder_7.toString(), _builder_8.toString(), _builder_9.toString(), _builder_10.toString(), _builder_11.toString())), ";");
      StringConcatenation _builder_12 = new StringConcatenation();
      _builder_12.append("domain: \"");
      String _string = domain.toString();
      _builder_12.append(_string);
      _builder_12.append("\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("child:");
      _builder_12.newLine();
      _builder_12.append("  ");
      _builder_12.append("sequence:");
      _builder_12.newLine();
      _builder_12.append("  ");
      _builder_12.append("- filter: \"{ ");
      String _join_4 = IterableExtensions.join(Ws, ";");
      _builder_12.append(_join_4, "  ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("  ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(kernelW, "  ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("  ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(combosW, "  ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("  ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(allW0, "  ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("  ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(allW1, "  ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("  ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(Y0, "  ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("  ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(I, "  ");
      _builder_12.append("; ");
      _builder_12.append(INR, "  ");
      _builder_12.append("; ");
      _builder_12.append(C1, "  ");
      _builder_12.append("; ");
      _builder_12.append(C2NRXsStr, "  ");
      _builder_12.append("; ");
      _builder_12.append(C2XsStr, "  ");
      _builder_12.append("; ");
      _builder_12.append(Y1, "  ");
      _builder_12.append("; ");
      _builder_12.append(Y2, "  ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("    ");
      _builder_12.append("child:");
      _builder_12.newLine();
      _builder_12.append("      ");
      _builder_12.append("schedule: \"");
      _builder_12.append(paramStr, "      ");
      _builder_12.append("->[\\");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("      \t");
      _builder_12.append("{ ");
      _builder_12.append(band1, "      \t");
      _builder_12.append(" }, \\");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("      \t");
      _builder_12.append("{ ");
      _builder_12.append(band2, "      \t");
      _builder_12.append(" } \\");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("      ");
      _builder_12.append("]\"");
      _builder_12.newLine();
      _builder_12.append("      ");
      _builder_12.append("child:");
      _builder_12.newLine();
      _builder_12.append("        ");
      _builder_12.append("sequence:");
      _builder_12.newLine();
      _builder_12.append("        ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(Y1, "        ");
      _builder_12.append("; ");
      _builder_12.append(Y2, "        ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("        ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(C1, "        ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("        ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(C2NRXsStr, "        ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("          ");
      _builder_12.append("child:");
      _builder_12.newLine();
      _builder_12.append("            ");
      _builder_12.append("schedule: \"");
      _builder_12.append(paramStr, "            ");
      _builder_12.append("->[\\");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("              ");
      _builder_12.append("{ ");
      final Function1<String, String> _function_14 = (String it) -> {
        StringConcatenation _builder_13 = new StringConcatenation();
        _builder_13.append(it);
        _builder_13.append("->[p]");
        return _builder_13.toString();
      };
      String _join_5 = IterableExtensions.join(ListExtensions.<String, String>map(C2NRXs, _function_14), ";");
      _builder_12.append(_join_5, "              ");
      _builder_12.append(" } \\");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("            ");
      _builder_12.append("]\"");
      _builder_12.newLine();
      _builder_12.append("        ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(C2XsStr, "        ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("          ");
      _builder_12.append("child:");
      _builder_12.newLine();
      _builder_12.append("            ");
      _builder_12.append("schedule: \"");
      _builder_12.append(paramStr, "            ");
      _builder_12.append("->[\\");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("              ");
      _builder_12.append("{ ");
      final Function1<String, String> _function_15 = (String it) -> {
        StringConcatenation _builder_13 = new StringConcatenation();
        _builder_13.append(it);
        _builder_13.append("->[p]");
        return _builder_13.toString();
      };
      String _join_6 = IterableExtensions.join(ListExtensions.<String, String>map(C2Xs, _function_15), ";");
      _builder_12.append(_join_6, "              ");
      _builder_12.append(" } \\");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("            ");
      _builder_12.append("]\"");
      _builder_12.newLine();
      _builder_12.append("        ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(INR, "        ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      _builder_12.append("        ");
      _builder_12.append("- filter: \"{ ");
      _builder_12.append(I, "        ");
      _builder_12.append(" }\"");
      _builder_12.newLineIfNotEmpty();
      final String islSchedule = _builder_12.toString();
      ISLSchedule _iSLSchedule = ISLUtil.toISLSchedule(islSchedule);
      _xblockexpression = new AlphaSchedule(_iSLSchedule, exprStmtMap);
    }
    return _xblockexpression;
  }
}
