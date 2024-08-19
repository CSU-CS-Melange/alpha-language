package alpha.abft;

import alpha.commands.UtilityBase;
import alpha.loader.AlphaLoader;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaModelSaver;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.AutoRestrictExpression;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.CaseExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.REDUCTION_OP;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.issue.AlphaIssue;
import alpha.model.util.AShow;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class ABFTv1 {
  private static HashMap<String, AlphaRoot> loadOrGet = CollectionLiterals.<String, AlphaRoot>newHashMap();

  public static List<AlphaIssue> addC1Equation(final SystemBody systemBody, final Variable CVar, final Variable stencilVar, final Variable WVar, final int[] tileSizes, final int radius) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> CIndexNames = CVar.getDomain().getIndexNames();
      final List<String> spatialIndexNames = WVar.getDomain().getIndexNames();
      final ArrayList<Pair<Pair<String, String>, Pair<Integer, Integer>>> spatialContext = ABFTv1.buildSpatialContext(tileSizes, radius, ((String[])Conversions.unwrapArray(CIndexNames, String.class)), ((String[])Conversions.unwrapArray(spatialIndexNames, String.class)));
      _xblockexpression = ABFTv1.addC1Equation(systemBody, CVar, stencilVar, WVar, tileSizes, radius, spatialContext);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addC1Equation(final SystemBody systemBody, final Variable CVar, final Variable stencilVar, final Variable WVar, final int[] tileSizes, final int radius, final List<Pair<Pair<String, String>, Pair<Integer, Integer>>> spatialContext) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> CIndexNames = CVar.getDomain().getIndexNames();
      final String paramStr = ABFTv1.buildParamStr(CVar);
      final List<String> spatialIndexNames = WVar.getDomain().getIndexNames();
      final Iterable<String> bodyIndices = Iterables.<String>concat(CIndexNames, spatialIndexNames);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      String _join = IterableExtensions.join(bodyIndices, ",");
      _builder.append(_join);
      _builder.append("]");
      final String bodyIndexStr = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{");
      _builder_1.append(bodyIndexStr);
      _builder_1.append("->[");
      String _join_1 = IterableExtensions.join(CIndexNames, ",");
      _builder_1.append(_join_1);
      _builder_1.append("]}");
      final ISLMultiAff projection = ISLUtil.toISLMultiAff(_builder_1.toString());
      final Function1<Pair<Pair<String, String>, Pair<Integer, Integer>>, String> _function = (Pair<Pair<String, String>, Pair<Integer, Integer>> it) -> {
        String _xblockexpression_1 = null;
        {
          final String ts = it.getKey().getKey();
          final String s = it.getKey().getValue();
          final Integer coeff = it.getValue().getKey();
          final Integer TS = it.getValue().getValue();
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append(coeff);
          _builder_2.append(ts);
          _builder_2.append("<=");
          _builder_2.append(s);
          _builder_2.append("<");
          _builder_2.append(coeff);
          _builder_2.append(ts);
          _builder_2.append("+");
          _builder_2.append(TS);
          _xblockexpression_1 = _builder_2.toString();
        }
        return _xblockexpression_1;
      };
      final String constraints = IterableExtensions.join(ListExtensions.<Pair<Pair<String, String>, Pair<Integer, Integer>>, String>map(spatialContext, _function), " and ");
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(paramStr);
      _builder_2.append("->{");
      _builder_2.append(bodyIndexStr);
      _builder_2.append(" : ");
      _builder_2.append(constraints);
      _builder_2.append("}");
      final ISLSet bodyDom = ISLUtil.toISLSet(_builder_2.toString());
      final int TT = tileSizes[0];
      final String tt = CIndexNames.get(0);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append(TT);
      _builder_3.append(tt);
      _builder_3.append(",");
      String _join_2 = IterableExtensions.join(spatialIndexNames, ",");
      _builder_3.append(_join_2);
      final String stencilVarMaffRangeStr = _builder_3.toString();
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append(paramStr);
      _builder_4.append("->{");
      _builder_4.append(bodyIndexStr);
      _builder_4.append("->[");
      _builder_4.append(stencilVarMaffRangeStr);
      _builder_4.append("]}");
      final ISLMultiAff stencilVarMaff = ISLUtil.toISLMultiAff(_builder_4.toString());
      final DependenceExpression stencilVarDepExpr = AlphaUserFactory.createDependenceExpression(stencilVarMaff);
      stencilVarDepExpr.setExpr(AlphaUserFactory.createVariableExpression(stencilVar));
      final RestrictExpression restrictExpr = AlphaUserFactory.createRestrictExpression(bodyDom);
      restrictExpr.setExpr(stencilVarDepExpr);
      final ReduceExpression reduceExpr = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, projection, restrictExpr);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(CVar, reduceExpr);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addIEquation(final SystemBody systemBody, final Variable IVar, final Variable C1Var, final Variable C2Var, final Variable WVar) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final DependenceExpression C2Expr = ABFTv1.createIdentityDepExpr(C2Var);
      _xblockexpression = ABFTv1.addIEquation(systemBody, IVar, C1Var, C2Var, WVar, C2Expr);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addIEquation(final SystemBody systemBody, final Variable IVar, final Variable C1Var, final Variable C2Var, final Variable WVar, final AlphaExpression C2Expr) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final BinaryExpression subExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.SUB);
      subExpr.setLeft(C2Expr);
      subExpr.setRight(ABFTv1.createIdentityDepExpr(C1Var));
      final BinaryExpression divExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.DIV);
      divExpr.setLeft(subExpr);
      divExpr.setRight(ABFTv1.createIdentityDepExpr(C1Var));
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(IVar, divExpr);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addKernelWEquation(final SystemBody systemBody, final Variable variable, final ISLSet oneDomain) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final ISLSpace space = variable.getDomain().getSpace();
      final RestrictExpression oneBranch = AlphaUserFactory.createRestrictExpression(oneDomain.copy());
      oneBranch.setExpr(AlphaUserFactory.createPositiveOneExpression(space));
      final AutoRestrictExpression defaultBranch = AlphaUserFactory.createAutoRestrictExpression();
      defaultBranch.setExpr(AlphaUserFactory.createZeroExpression(space));
      final CaseExpression ce = AlphaUserFactory.createCaseExpression();
      EList<AlphaExpression> _exprs = ce.getExprs();
      _exprs.add(oneBranch);
      EList<AlphaExpression> _exprs_1 = ce.getExprs();
      _exprs_1.add(defaultBranch);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(variable, ce);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static DependenceExpression createIdentityDepExpr(final Variable variable) {
    DependenceExpression _xblockexpression = null;
    {
      final List<String> indexNames = variable.getDomain().getIndexNames();
      final String paramStr = ABFTv1.buildParamStr(variable.getDomain().getSpace());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      String _join = IterableExtensions.join(indexNames, ",");
      _builder.append(_join);
      _builder.append("]->[");
      String _join_1 = IterableExtensions.join(indexNames, ",");
      _builder.append(_join_1);
      _builder.append("]}");
      final ISLMultiAff maff = ISLUtil.toISLMultiAff(_builder.toString());
      final DependenceExpression de = AlphaUserFactory.createDependenceExpression(maff);
      de.setExpr(AlphaUserFactory.createVariableExpression(variable));
      _xblockexpression = de;
    }
    return _xblockexpression;
  }

  public static DependenceExpression createRealDepExpr(final ISLSpace space, final float value) {
    return AlphaUserFactory.createDependenceExpression(ISLUtil.createConstantMaff(space), AlphaUserFactory.createRealExpression(value));
  }

  public static ArrayList<Pair<Pair<String, String>, Pair<Integer, Integer>>> buildSpatialContext(final int[] tileSizes, final int radius, final String[] CIndexNames, final String[] spatialIndexNames) {
    return ABFTv1.buildSpatialContext(tileSizes, radius, CIndexNames, spatialIndexNames, 0);
  }

  public static ArrayList<Pair<Pair<String, String>, Pair<Integer, Integer>>> buildSpatialContext(final int[] tileSizes, final int radius, final String[] CIndexNames, final String[] spatialIndexNames, final int tileSizeOffset) {
    ArrayList<Pair<Pair<String, String>, Pair<Integer, Integer>>> _xblockexpression = null;
    {
      final int TT = tileSizes[0];
      int _size = ((List<Integer>)Conversions.doWrapArray(tileSizes)).size();
      final Function1<Integer, String> _function = (Integer i) -> {
        return CIndexNames[(i).intValue()];
      };
      final Iterable<String> spatialTileIndexNames = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
      final Iterable<Integer> spatialTileSizes = ABFTv1.spatialSizes(tileSizes);
      final Function1<Integer, Integer> _function_1 = (Integer TS) -> {
        return Integer.valueOf(((TS).intValue() - tileSizeOffset));
      };
      final Iterable<Integer> spatialTileCoeffs = IterableExtensions.<Integer, Integer>map(spatialTileSizes, _function_1);
      final ArrayList<Pair<String, String>> spatialNames = CommonExtensions.<String, String>zipWith(spatialTileIndexNames, ((Iterable<String>)Conversions.doWrapArray(spatialIndexNames)));
      final ArrayList<Pair<Integer, Integer>> spatialSizes = CommonExtensions.<Integer, Integer>zipWith(spatialTileCoeffs, spatialTileSizes);
      _xblockexpression = CommonExtensions.<Pair<String, String>, Pair<Integer, Integer>>zipWith(spatialNames, spatialSizes);
    }
    return _xblockexpression;
  }

  public static String buildParamStr(final Variable variable) {
    return ABFTv1.buildParamStr(variable.getDomain().getSpace());
  }

  public static String buildParamStr(final ISLSpace space) {
    String _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      String _join = IterableExtensions.join(space.getParamNames(), ",");
      _builder.append(_join);
      _builder.append("]");
      final String paramStr = _builder.toString();
      _xblockexpression = paramStr;
    }
    return _xblockexpression;
  }

  /**
   * Hardcoded for now, but this is where convolution detection should happen
   * Returns the pair (convolution radius)->(map of the convolution kernel)
   */
  public static Pair<Integer, Map<List<Integer>, Double>> identify_convolution(final AlphaSystem system) {
    Pair<Integer, Map<List<Integer>, Double>> _switchResult = null;
    String _get = system.getName().split("_")[0];
    if (_get != null) {
      switch (_get) {
        case "star1d1r":
          Pair<List<Integer>, Double> _mappedTo = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf((-1)))), Double.valueOf(0.3332));
          Pair<List<Integer>, Double> _mappedTo_1 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), Double.valueOf(0.3333));
          Pair<List<Integer>, Double> _mappedTo_2 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), Double.valueOf(0.33));
          _switchResult = Pair.<Integer, Map<List<Integer>, Double>>of(Integer.valueOf(1), Collections.<List<Integer>, Double>unmodifiableMap(CollectionLiterals.<List<Integer>, Double>newHashMap(_mappedTo, _mappedTo_1, _mappedTo_2)));
          break;
        case "star2d1r":
          Pair<List<Integer>, Double> _mappedTo_3 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0))), Double.valueOf(0.5002));
          Pair<List<Integer>, Double> _mappedTo_4 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf((-1)), Integer.valueOf(0))), Double.valueOf(0.1247));
          Pair<List<Integer>, Double> _mappedTo_5 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(0))), Double.valueOf(0.1249));
          Pair<List<Integer>, Double> _mappedTo_6 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf((-1)))), Double.valueOf(0.1250));
          Pair<List<Integer>, Double> _mappedTo_7 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1))), Double.valueOf(0.1251));
          _switchResult = Pair.<Integer, Map<List<Integer>, Double>>of(Integer.valueOf(1), Collections.<List<Integer>, Double>unmodifiableMap(CollectionLiterals.<List<Integer>, Double>newHashMap(_mappedTo_3, _mappedTo_4, _mappedTo_5, _mappedTo_6, _mappedTo_7)));
          break;
        case "star3d1r":
          Pair<List<Integer>, Double> _mappedTo_8 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0))), Double.valueOf(0.2500));
          Pair<List<Integer>, Double> _mappedTo_9 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf((-1)), Integer.valueOf(0), Integer.valueOf(0))), Double.valueOf(0.1248));
          Pair<List<Integer>, Double> _mappedTo_10 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0))), Double.valueOf(0.1249));
          Pair<List<Integer>, Double> _mappedTo_11 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf((-1)), Integer.valueOf(0))), Double.valueOf(0.1250));
          Pair<List<Integer>, Double> _mappedTo_12 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0))), Double.valueOf(0.1250));
          Pair<List<Integer>, Double> _mappedTo_13 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf((-1)))), Double.valueOf(0.1251));
          Pair<List<Integer>, Double> _mappedTo_14 = Pair.<List<Integer>, Double>of(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1))), Double.valueOf(0.1252));
          _switchResult = Pair.<Integer, Map<List<Integer>, Double>>of(Integer.valueOf(1), Collections.<List<Integer>, Double>unmodifiableMap(CollectionLiterals.<List<Integer>, Double>newHashMap(_mappedTo_8, _mappedTo_9, _mappedTo_10, _mappedTo_11, _mappedTo_12, _mappedTo_13, _mappedTo_14)));
          break;
      }
    }
    return _switchResult;
  }

  public static void rename(final AlphaSystem system, final int[] tileSizes, final String vX) {
    final int H = tileSizes[0];
    final Iterable<Integer> Ls = ABFTv1.spatialSizes(tileSizes);
    StringConcatenation _builder = new StringConcatenation();
    String _name = system.getName();
    _builder.append(_name);
    _builder.append("_abft_");
    _builder.append(vX);
    _builder.append("_");
    _builder.append(H);
    _builder.append("_");
    String _join = IterableExtensions.join(Ls, "_");
    _builder.append(_join);
    system.setName(_builder.toString());
  }

  public static void save(final AlphaSystem system) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("resources/generated/");
    String _name = system.getName();
    _builder.append(_name);
    _builder.append(".alpha");
    final String file = _builder.toString();
    AlphaModelSaver.ASave(AlphaUtil.getContainerRoot(system), file);
    InputOutput.<String>println(("Saved to file " + file));
  }

  public static Iterable<Integer> spatialSizes(final int[] tileSizes) {
    int _size = ((List<Integer>)Conversions.doWrapArray(tileSizes)).size();
    final Function1<Integer, Integer> _function = (Integer i) -> {
      return Integer.valueOf(tileSizes[(i).intValue()]);
    };
    return IterableExtensions.<Integer, Integer>map(new ExclusiveRange(1, _size, true), _function);
  }

  public static AlphaSystem loadSystem(final String file, final String systemName) {
    try {
      AlphaRoot root = ABFTv1.loadOrGet.get(file);
      if ((root == null)) {
        root = AlphaLoader.loadAlpha(("resources/inputs/" + file));
        ABFTv1.loadOrGet.put(file, root);
      }
      final AlphaSystem system = UtilityBase.GetSystem(AlphaUtil.<AlphaRoot>copyAE(root), systemName);
      return system;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static String pprint(final AlphaVisitable av, final String msg) {
    String _xblockexpression = null;
    {
      InputOutput.<String>println((msg + ":"));
      _xblockexpression = InputOutput.<String>println(AShow.print(av));
    }
    return _xblockexpression;
  }
}
