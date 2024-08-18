package alpha.abft;

import alpha.abft.analysis.ConvolutionDetector;
import alpha.abft.util.ConvolutionKernel;
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
import alpha.model.transformation.Normalize;
import alpha.model.transformation.reduction.NormalizeReduction;
import alpha.model.util.AShow;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
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
public class ABFT {
  private static HashMap<String, AlphaRoot> loadOrGet = CollectionLiterals.<String, AlphaRoot>newHashMap();

  public static void main(final String[] args) {
    ABFT.insertChecksumV3("star3d2t1r", new int[] { 16, 10 });
  }

  public static void insert_checksums(final String systemName, final int[] tileSizes) {
    ABFT.insertChecksumV1(systemName, tileSizes);
    ABFT.insertChecksumV2(systemName, tileSizes);
  }

  public static void insertChecksumV1(final String systemName, final int[] tileSizes) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(systemName);
    _builder.append(".alpha");
    final AlphaSystem system = ABFT.loadSystem(_builder.toString(), systemName);
    ABFT.insertChecksumV1(system, tileSizes);
    ABFT.save(system);
  }

  public static void insertChecksumV2(final String systemName, final int[] tileSizes) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(systemName);
    _builder.append(".alpha");
    final AlphaSystem system = ABFT.loadSystem(_builder.toString(), systemName);
    ABFT.insertChecksumV2(system, tileSizes);
    ABFT.save(system);
  }

  public static void insertChecksumV3(final String systemName, final int[] tileSizes) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(systemName);
    _builder.append(".alpha");
    final AlphaSystem system = ABFT.loadSystem(_builder.toString(), systemName);
    ABFT.insertChecksumV3(system, tileSizes);
    ABFT.save(system);
  }

  public static AlphaSystem insertChecksumV1(final AlphaSystem system, final int[] tileSizes) {
    return ABFT.insertChecksumV1(system, tileSizes, true);
  }

  public static AlphaSystem insertChecksumV1(final AlphaSystem system, final int[] tileSizes, final boolean renameSystem) {
    AlphaSystem _xblockexpression = null;
    {
      final SystemBody systemBody = system.getSystemBodies().get(0);
      final Variable outputVar = system.getOutputs().get(0);
      final Pair<Integer, Map<List<Integer>, Double>> convolutionKernel = ABFT.identify_convolution(system);
      final Integer radius = convolutionKernel.getKey();
      final Map<List<Integer>, Double> kernel = convolutionKernel.getValue();
      final ISLSet checksumDomain = ABFT.buildChecksumDomain(outputVar, tileSizes, (radius).intValue(), false);
      final ISLSet weightsDomain = ABFT.buildWeightsDomain(outputVar, (radius).intValue());
      final ISLSet kernelDomain = ABFT.buildWeightsDomain(outputVar, (2 * (radius).intValue()));
      final ISLSet patchDomain = ABFT.buildPatchDomain(weightsDomain, tileSizes, (radius).intValue());
      final ISLSet C2Domain = ABFT.buildC2Domain(checksumDomain, kernelDomain, false);
      final Variable IVar = AlphaUserFactory.createVariable("I", checksumDomain.copy());
      final Variable WVar = AlphaUserFactory.createVariable("W", weightsDomain.copy());
      final Variable patchVar = AlphaUserFactory.createVariable("patch", patchDomain.copy());
      final Variable C1Var = AlphaUserFactory.createVariable("C1", checksumDomain.copy());
      final Variable C2Var = AlphaUserFactory.createVariable("C2", C2Domain.copy());
      system.getLocals().addAll(Collections.<Variable>unmodifiableList(CollectionLiterals.<Variable>newArrayList(WVar, C1Var, C2Var, IVar, patchVar)));
      ABFT.addWeightsEquation(systemBody, WVar, kernel);
      ABFT.addPatchEquation(systemBody, patchVar, WVar, tileSizes);
      ABFT.addC1Equation(systemBody, C1Var, outputVar, WVar, tileSizes, (radius).intValue(), false);
      ABFT.addV1C2Equation(systemBody, C2Var, outputVar, WVar, patchVar, tileSizes, (radius).intValue());
      ABFT.addIEquation(systemBody, IVar, C1Var, C2Var, WVar, false);
      if (renameSystem) {
        ABFT.rename(system, tileSizes, "v1");
      }
      Normalize.apply(system);
      NormalizeReduction.apply(system);
      _xblockexpression = system;
    }
    return _xblockexpression;
  }

  public static AlphaSystem insertChecksumV2(final AlphaSystem system, final int[] tileSizes) {
    return ABFT.insertChecksumV2(system, tileSizes, true);
  }

  public static AlphaSystem insertChecksumV2(final AlphaSystem system, final int[] tileSizes, final boolean renameSystem) {
    AlphaSystem _xblockexpression = null;
    {
      final SystemBody systemBody = system.getSystemBodies().get(0);
      final Variable outputVar = system.getOutputs().get(0);
      final Pair<Integer, Map<List<Integer>, Double>> convolutionKernel = ABFT.identify_convolution(system);
      final Integer radius = convolutionKernel.getKey();
      final Map<List<Integer>, Double> kernel = convolutionKernel.getValue();
      final int TT = tileSizes[0];
      final Consumer<Integer> _function = (Integer TS) -> {
        try {
          if ((((2 * (radius).intValue()) * TT) >= (TS).intValue())) {
            final int max_tt = Integer.valueOf((((TS).intValue() / 2) / (radius).intValue())).intValue();
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("Invalid tile sizes for V2, [");
            String _join = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(tileSizes)), ",");
            _builder.append(_join);
            _builder.append("]. Given a radius of ");
            _builder.append(radius);
            _builder.append(" and spatial tile size ");
            _builder.append(TS);
            _builder.append(", the maximum time tile size can be ");
            _builder.append(max_tt);
            throw new Exception(_builder.toString());
          }
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      };
      ABFT.spatialSizes(tileSizes).forEach(_function);
      final ISLSet checksumDomain = ABFT.buildChecksumDomain(outputVar, tileSizes, (radius).intValue(), true);
      final ISLSet weightsDomain = ABFT.buildWeightsDomain(outputVar, (radius).intValue());
      final ISLSet kernelDomain = ABFT.buildWeightsDomain(outputVar, (2 * (radius).intValue()));
      final ISLSet allWeightsDomain = ABFT.buildAllWeightsDomain(outputVar, TT);
      final ISLSet C2Domain = ABFT.buildC2Domain(checksumDomain, kernelDomain, true);
      final Variable IVar = AlphaUserFactory.createVariable("I", checksumDomain.copy());
      final Variable WVar = AlphaUserFactory.createVariable("W", weightsDomain.copy());
      final Variable C1Var = AlphaUserFactory.createVariable("C1", checksumDomain.copy());
      final Variable C2Var = AlphaUserFactory.createVariable("C2", C2Domain.copy());
      final Variable kernelWVar = AlphaUserFactory.createVariable("WKernel", kernelDomain.copy());
      final Variable combosWVar = AlphaUserFactory.createVariable("WCombos", kernelDomain.copy());
      final Variable allWVar = AlphaUserFactory.createVariable("WAll", allWeightsDomain.copy());
      system.getLocals().addAll(Collections.<Variable>unmodifiableList(CollectionLiterals.<Variable>newArrayList(WVar, C1Var, C2Var, IVar, kernelWVar, combosWVar, allWVar)));
      ABFT.addWeightsEquation(systemBody, WVar, kernel);
      ABFT.addKernelWEquation(systemBody, kernelWVar, weightsDomain);
      ABFT.addCombosWEquation(systemBody, combosWVar, kernelWVar, WVar);
      ABFT.addAllWEquation(systemBody, allWVar, combosWVar);
      ABFT.addC1Equation(systemBody, C1Var, outputVar, WVar, tileSizes, (radius).intValue(), true);
      ABFT.addV2C2Equation(systemBody, C2Var, outputVar, WVar, allWVar, combosWVar, tileSizes, (radius).intValue());
      ABFT.addIEquation(systemBody, IVar, C1Var, C2Var, WVar, true);
      if (renameSystem) {
        int _size = ((List<Integer>)Conversions.doWrapArray(tileSizes)).size();
        final Function1<Integer, Integer> _function_1 = (Integer i) -> {
          int _get = tileSizes[(i).intValue()];
          return Integer.valueOf((_get - ((2 * TT) * (radius).intValue())));
        };
        final Iterable<Integer> TSs = IterableExtensions.<Integer, Integer>map(new ExclusiveRange(1, _size, true), _function_1);
        final Iterable<Integer> _tileSizes = Iterables.<Integer>concat(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(TT))), TSs);
        ABFT.rename(system, ((int[])Conversions.unwrapArray(_tileSizes, int.class)), "v2");
      }
      Normalize.apply(system);
      NormalizeReduction.apply(system);
      _xblockexpression = system;
    }
    return _xblockexpression;
  }

  public static AlphaSystem insertChecksumV3(final AlphaSystem system, final int[] tileSizes) {
    return ABFT.insertChecksumV3(system, tileSizes, true);
  }

  public static AlphaSystem insertChecksumV3(final AlphaSystem system, final int[] tileSizes, final boolean renameSystem) {
    AlphaSystem _xblockexpression = null;
    {
      final SystemBody systemBody = system.getSystemBodies().get(0);
      final Variable outputVar = system.getOutputs().get(0);
      final int H = tileSizes[0];
      final int L = tileSizes[1];
      List<ConvolutionKernel> _apply = ConvolutionDetector.apply(system);
      ConvolutionKernel _get = null;
      if (_apply!=null) {
        _get=_apply.get(0);
      }
      final ConvolutionKernel convolutionKernel = _get;
      final int spaceTileDim = 1;
      final ISLSet checksumDomain = ABFT.buildChecksumDomainV3(outputVar, convolutionKernel, H, L, spaceTileDim);
      final ISLSet weightsDomain = ABFT.buildWeightsDomainV3(outputVar, convolutionKernel);
      final ISLSet kernelDomain = ABFT.buildWeightsDomainV3(outputVar, convolutionKernel, 2);
      final Variable IVar = AlphaUserFactory.createVariable("I", checksumDomain.copy());
      final Variable WVar = AlphaUserFactory.createVariable("W", weightsDomain.copy());
      final Variable CVar = AlphaUserFactory.createVariable("C1", checksumDomain.copy());
      final Variable WExtVar = AlphaUserFactory.createVariable("WExt", kernelDomain.copy());
      final Variable WiVar = AlphaUserFactory.createVariable("Wi", kernelDomain.copy());
      final Variable CiVar = AlphaUserFactory.createVariable("C2", checksumDomain.copy());
      system.getLocals().addAll(Collections.<Variable>unmodifiableList(CollectionLiterals.<Variable>newArrayList(IVar, WVar, WExtVar, WiVar, CVar, CiVar)));
      ABFT.addWeightsEquation(systemBody, WVar, convolutionKernel);
      ABFT.addKernelWEquation(systemBody, WExtVar, weightsDomain);
      ABFT.addWiEquationV3(systemBody, WiVar, WExtVar, WVar, spaceTileDim);
      ABFT.addCEquationV3(systemBody, CVar, outputVar, WVar, convolutionKernel, spaceTileDim, H, L);
      ABFT.addC2EquationV3(systemBody, CiVar, outputVar, WVar, convolutionKernel, spaceTileDim, H, L);
      ABFT.addIEquation(systemBody, IVar, CVar, CiVar, WVar, false);
      if (renameSystem) {
        ABFT.rename(system, new int[] { H, L }, "v3");
      }
      Normalize.apply(system);
      NormalizeReduction.apply(system);
      _xblockexpression = system;
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addC2EquationV3(final SystemBody systemBody, final Variable CVar, final Variable stencilVar, final Variable WVar, final ConvolutionKernel convolutionKernel, final int spaceTileDim, final int H, final int L) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(CVar, AlphaUserFactory.createZeroExpression(CVar.getDomain().getSpace()));
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addCEquationV3(final SystemBody systemBody, final Variable CVar, final Variable stencilVar, final Variable WVar, final ConvolutionKernel convolutionKernel, final int spaceTileDim, final int H, final int L) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> CIndexNames = CVar.getDomain().getIndexNames();
      final List<String> stencilIndexNames = stencilVar.getDomain().getIndexNames();
      final String paramStr = ABFT.buildParamStr(CVar);
      String _get = stencilIndexNames.get(spaceTileDim);
      final Iterable<String> bodyIndices = Iterables.<String>concat(CIndexNames, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_get)));
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
      final String ti = CIndexNames.get(spaceTileDim);
      final String i = stencilIndexNames.get(spaceTileDim);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(paramStr);
      _builder_2.append("->{");
      _builder_2.append(bodyIndexStr);
      _builder_2.append(" : ");
      _builder_2.append(L);
      _builder_2.append(ti);
      _builder_2.append("<=");
      _builder_2.append(i);
      _builder_2.append("<");
      _builder_2.append(L);
      _builder_2.append(ti);
      _builder_2.append("+");
      _builder_2.append(L);
      _builder_2.append("}");
      final ISLSet bodyDom = ISLUtil.toISLSet(_builder_2.toString());
      String _get_1 = CIndexNames.get(0);
      String _plus = (Integer.valueOf(H) + _get_1);
      int _size = stencilIndexNames.size();
      final Function1<Integer, String> _function = (Integer it) -> {
        return stencilIndexNames.get((it).intValue());
      };
      Iterable<String> _map = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
      final String varAcc = IterableExtensions.join(Iterables.<String>concat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_plus)), _map), ",");
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append(paramStr);
      _builder_3.append("->{");
      _builder_3.append(bodyIndexStr);
      _builder_3.append("->[");
      _builder_3.append(varAcc);
      _builder_3.append("]}");
      final ISLMultiAff stencilVarMaff = ISLUtil.toISLMultiAff(_builder_3.toString());
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

  public static List<AlphaIssue> addWiEquationV3(final SystemBody systemBody, final Variable WiVar, final Variable WExtVar, final Variable WVar, final int spaceTileDim) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final String paramStr = ABFT.buildParamStr(WiVar);
      final List<String> indexNames = WiVar.getDomain().getIndexNames();
      final String pqrName = ((String[])Conversions.unwrapArray(ABFT.getKernelNamesV3(WiVar.getDomain()), String.class))[spaceTileDim];
      final String domainStr = IterableExtensions.join(Iterables.<String>concat(indexNames, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(pqrName))), ",");
      final String rangeStr = IterableExtensions.join(WiVar.getDomain().getIndexNames(), ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      _builder.append(domainStr);
      _builder.append("]->[");
      _builder.append(rangeStr);
      _builder.append("]}");
      final ISLMultiAff projection = ISLUtil.toISLMultiAff(_builder.toString());
      final Function1<String, String> _function = (String n) -> {
        String _xifexpression = null;
        int _indexOf = indexNames.indexOf(n);
        boolean _equals = (_indexOf == spaceTileDim);
        if (_equals) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(n);
          _builder_1.append("-");
          _builder_1.append(pqrName);
          _xifexpression = _builder_1.toString();
        } else {
          _xifexpression = n;
        }
        return _xifexpression;
      };
      final String WExtMaffStr = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function), ",");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{[");
      _builder_1.append(domainStr);
      _builder_1.append("]->[");
      _builder_1.append(WExtMaffStr);
      _builder_1.append("]}");
      final ISLMultiAff WExtMaff = ISLUtil.toISLMultiAff(_builder_1.toString());
      final DependenceExpression WExtDepExpr = AlphaUserFactory.createDependenceExpression(WExtMaff, AlphaUserFactory.createVariableExpression(WExtVar));
      final Function1<String, Object> _function_1 = (String n) -> {
        Object _switchResult = null;
        int _indexOf = indexNames.indexOf(n);
        boolean _matched = false;
        if (Objects.equal(_indexOf, 0)) {
          _matched=true;
          _switchResult = n;
        }
        if (!_matched) {
          if (Objects.equal(_indexOf, spaceTileDim)) {
            _matched=true;
            _switchResult = pqrName;
          }
        }
        if (!_matched) {
          _switchResult = Integer.valueOf(0);
        }
        return ((Object)_switchResult);
      };
      final String WMaffRange = IterableExtensions.join(ListExtensions.<String, Object>map(indexNames, _function_1), ",");
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(paramStr);
      _builder_2.append("->{[");
      _builder_2.append(domainStr);
      _builder_2.append("]->[");
      _builder_2.append(WMaffRange);
      _builder_2.append("]}");
      final ISLMultiAff WMaff = ISLUtil.toISLMultiAff(_builder_2.toString());
      final DependenceExpression WDepExpr = AlphaUserFactory.createDependenceExpression(WMaff, AlphaUserFactory.createVariableExpression(WVar));
      final BinaryExpression be = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL);
      be.setLeft(WExtDepExpr);
      be.setRight(WDepExpr);
      final ReduceExpression reduceExpr = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, projection, be);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(WiVar, reduceExpr);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static Iterable<String> getKernelNamesV3(final ISLSet kernelDomain) {
    Iterable<String> _xblockexpression = null;
    {
      final int nbKernel = kernelDomain.dim(ISLDimType.isl_dim_out);
      final Function1<Integer, String> _function = (Integer i) -> {
        return Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("h", "p", "q", "r")).get((i).intValue());
      };
      _xblockexpression = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, nbKernel, true), _function);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addWeightsEquation(final SystemBody systemBody, final Variable variable, final ConvolutionKernel convolutionKernel) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> indexNames = variable.getDomain().getIndexNames();
      final ISLSpace space = variable.getDomain().getSpace();
      final CaseExpression ce = AlphaUserFactory.createCaseExpression();
      EList<AlphaExpression> _exprs = ce.getExprs();
      final Function1<Map.Entry<ISLMultiAff, Float>, RestrictExpression> _function = (Map.Entry<ISLMultiAff, Float> it) -> {
        RestrictExpression _xblockexpression_1 = null;
        {
          final ISLMultiAff maff = it.getKey();
          final Float coeff = it.getValue();
          final Function1<ISLAff, Integer> _function_1 = (ISLAff it_1) -> {
            return Integer.valueOf(Long.valueOf(it_1.getConstant()).intValue());
          };
          final List<Integer> indexValues = IterableExtensions.<Integer>toList(ListExtensions.<ISLAff, Integer>map(maff.getAffs(), _function_1));
          _xblockexpression_1 = ABFT.createWeightRestrictBranchV3(space, ((String[])Conversions.unwrapArray(indexNames, String.class)), ((Integer[])Conversions.unwrapArray(indexValues, Integer.class)), coeff.floatValue());
        }
        return _xblockexpression_1;
      };
      Iterable<RestrictExpression> _map = IterableExtensions.<Map.Entry<ISLMultiAff, Float>, RestrictExpression>map(convolutionKernel.getKernel().entrySet(), _function);
      Iterables.<AlphaExpression>addAll(_exprs, _map);
      final Function1<AlphaExpression, RestrictExpression> _function_1 = (AlphaExpression e) -> {
        return ((RestrictExpression) e);
      };
      final Function1<RestrictExpression, ISLSet> _function_2 = (RestrictExpression re) -> {
        return re.getRestrictDomain();
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_3 = (ISLSet d1, ISLSet d2) -> {
        return d1.copy().union(d2.copy());
      };
      final ISLSet branchDoms = IterableExtensions.<ISLSet>reduce(ListExtensions.<RestrictExpression, ISLSet>map(ListExtensions.<AlphaExpression, RestrictExpression>map(ce.getExprs(), _function_1), _function_2), _function_3);
      final ISLSet WVarDom = variable.getDomain().copy();
      boolean _isEmpty = WVarDom.subtract(branchDoms).isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        final AutoRestrictExpression defaultBranch = AlphaUserFactory.createAutoRestrictExpression();
        defaultBranch.setExpr(AlphaUserFactory.createZeroExpression(space));
        EList<AlphaExpression> _exprs_1 = ce.getExprs();
        _exprs_1.add(defaultBranch);
      }
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(variable, ce);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static RestrictExpression createWeightRestrictBranchV3(final ISLSpace space, final String[] indexNames, final Integer[] indexValues, final float coeff) {
    RestrictExpression _xblockexpression = null;
    {
      ABFT.createRealDepExpr(space, coeff);
      final String paramStr = ABFT.buildParamStr(space);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      String _join = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(indexNames)), ",");
      _builder.append(_join);
      _builder.append("]");
      final String indexStr = _builder.toString();
      final Function1<Pair<String, Integer>, String> _function = (Pair<String, Integer> it) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _key = it.getKey();
        _builder_1.append(_key);
        _builder_1.append("=");
        Integer _value = it.getValue();
        _builder_1.append(_value);
        return _builder_1.toString();
      };
      final String constraints = IterableExtensions.join(ListExtensions.<Pair<String, Integer>, String>map(CommonExtensions.<String, Integer>zipWith(((Iterable<String>)Conversions.doWrapArray(indexNames)), ((Iterable<Integer>)Conversions.doWrapArray(indexValues))), _function), " and ");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{");
      _builder_1.append(indexStr);
      _builder_1.append(" : ");
      _builder_1.append(constraints);
      _builder_1.append("}");
      final ISLSet restrictDomain = ISLUtil.toISLSet(_builder_1.toString());
      final RestrictExpression re = AlphaUserFactory.createRestrictExpression(restrictDomain);
      re.setExpr(ABFT.createRealDepExpr(space, coeff));
      _xblockexpression = re;
    }
    return _xblockexpression;
  }

  public static ISLSet buildWeightsDomainV3(final Variable variable, final ConvolutionKernel convolutionKernel) {
    return ABFT.buildWeightsDomainV3(variable, convolutionKernel, 1);
  }

  public static ISLSet buildWeightsDomainV3(final Variable variable, final ConvolutionKernel convolutionKernel, final int scale) {
    ISLSet _xblockexpression = null;
    {
      final List<String> indexNames = variable.getDomain().getIndexNames();
      int _size = indexNames.size();
      final Function1<Integer, String> _function = (Integer i) -> {
        return indexNames.get((i).intValue());
      };
      final Iterable<String> spaceIndexNames = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
      final Long radius = convolutionKernel.radius();
      final Long timeDepth = convolutionKernel.timeDepth();
      final String t = indexNames.get(0);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("-");
      _builder.append(((radius).longValue() * scale));
      _builder.append("<=");
      String _join = IterableExtensions.join(spaceIndexNames, ",");
      _builder.append(_join);
      _builder.append("<=");
      _builder.append(((radius).longValue() * scale));
      final String spaceConstraints = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("-");
      _builder_1.append(timeDepth);
      _builder_1.append("<=");
      _builder_1.append(t);
      _builder_1.append("<0");
      final String timeConstraints = _builder_1.toString();
      StringConcatenation _builder_2 = new StringConcatenation();
      String _buildParamStr = ABFT.buildParamStr(variable);
      _builder_2.append(_buildParamStr);
      _builder_2.append("->{[");
      String _join_1 = IterableExtensions.join(indexNames, ",");
      _builder_2.append(_join_1);
      _builder_2.append("] : ");
      _builder_2.append(timeConstraints);
      _builder_2.append(" and ");
      _builder_2.append(spaceConstraints);
      _builder_2.append("}");
      final String setStr = _builder_2.toString();
      _xblockexpression = ISLUtil.toISLSet(setStr);
    }
    return _xblockexpression;
  }

  public static ISLSet buildChecksumDomainV3(final Variable variable, final ConvolutionKernel convolutionKernel, final int H, final int L, final int spaceTileDim) {
    ISLSet _xblockexpression = null;
    {
      final ISLSet domain = variable.getDomain().copy().intersect(convolutionKernel.domain().copy());
      final ISLVal HVal = ISLVal.buildRationalValue(ISLContext.getInstance(), H, 1);
      final ISLVal LVal = ISLVal.buildRationalValue(ISLContext.getInstance(), L, 1);
      int _size = domain.getIndexNames().size();
      final Function1<Integer, String> _function = (Integer i) -> {
        String _xblockexpression_1 = null;
        {
          final String name = domain.getIndexNames().get((i).intValue());
          String _xifexpression = null;
          if ((((i).intValue() == 0) || ((i).intValue() == spaceTileDim))) {
            _xifexpression = ("t" + name);
          } else {
            _xifexpression = domain.getIndexNames().get((i).intValue());
          }
          _xblockexpression_1 = _xifexpression;
        }
        return _xblockexpression_1;
      };
      final List<String> indexNames = IterableExtensions.<String>toList(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size, true), _function));
      final Function1<ISLConstraint, Boolean> _function_1 = (ISLConstraint c) -> {
        return Boolean.valueOf(c.involvesDims(ISLDimType.isl_dim_out, 0, 1));
      };
      final Function1<ISLConstraint, Boolean> involvesTime = _function_1;
      final Function2<ISLConstraint, Integer, Boolean> _function_2 = (ISLConstraint c, Integer i) -> {
        return Boolean.valueOf(c.involvesDims(ISLDimType.isl_dim_out, i, 1));
      };
      final Function2<ISLConstraint, Integer, Boolean> involvesSpace = _function_2;
      final Function2<ISLConstraint, Integer, Boolean> _function_3 = (ISLConstraint c, Integer i) -> {
        return Boolean.valueOf(((involvesTime.apply(c)).booleanValue() || (involvesSpace.apply(c, Integer.valueOf(i))).booleanValue()));
      };
      final Function2<ISLConstraint, Integer, Boolean> involvesEither = _function_3;
      final Function1<ISLBasicSet, ISLSet> _function_4 = (ISLBasicSet bs) -> {
        ISLSet _xblockexpression_1 = null;
        {
          final Function1<ISLConstraint, Boolean> _function_5 = (ISLConstraint c) -> {
            return involvesTime.apply(c);
          };
          final Function1<ISLConstraint, ISLConstraint> _function_6 = (ISLConstraint c) -> {
            ISLConstraint _xblockexpression_2 = null;
            {
              final ISLVal coeffVal = c.getCoefficientVal(ISLDimType.isl_dim_out, 0);
              _xblockexpression_2 = c.copy().setCoefficient(ISLDimType.isl_dim_out, 0, coeffVal.mul(HVal.copy()));
            }
            return _xblockexpression_2;
          };
          final Iterable<ISLConstraint> timeConstraints = IterableExtensions.<ISLConstraint, ISLConstraint>map(IterableExtensions.<ISLConstraint>filter(bs.getConstraints(), _function_5), _function_6);
          final Function1<ISLConstraint, Boolean> _function_7 = (ISLConstraint c) -> {
            return involvesSpace.apply(c, Integer.valueOf(spaceTileDim));
          };
          final Function1<ISLConstraint, ISLConstraint> _function_8 = (ISLConstraint c) -> {
            ISLConstraint _xblockexpression_2 = null;
            {
              final ISLVal coeffVal = c.getCoefficientVal(ISLDimType.isl_dim_out, spaceTileDim);
              _xblockexpression_2 = c.copy().setCoefficient(ISLDimType.isl_dim_out, spaceTileDim, coeffVal.mul(HVal.copy()));
            }
            return _xblockexpression_2;
          };
          final Iterable<ISLConstraint> spaceConstraints = IterableExtensions.<ISLConstraint, ISLConstraint>map(IterableExtensions.<ISLConstraint>filter(bs.getConstraints(), _function_7), _function_8);
          final Function1<ISLConstraint, Boolean> _function_9 = (ISLConstraint c) -> {
            return involvesEither.apply(c, Integer.valueOf(spaceTileDim));
          };
          final Iterable<ISLConstraint> remainingConstraints = IterableExtensions.<ISLConstraint>reject(bs.getConstraints(), _function_9);
          Iterable<ISLConstraint> _plus = Iterables.<ISLConstraint>concat(timeConstraints, spaceConstraints);
          final Function1<ISLConstraint, ISLBasicSet> _function_10 = (ISLConstraint it) -> {
            return it.toBasicSet();
          };
          final Function2<ISLBasicSet, ISLBasicSet, ISLBasicSet> _function_11 = (ISLBasicSet b1, ISLBasicSet b2) -> {
            return b1.intersect(b2);
          };
          _xblockexpression_1 = IterableExtensions.<ISLBasicSet>reduce(IterableExtensions.<ISLConstraint, ISLBasicSet>map(Iterables.<ISLConstraint>concat(_plus, remainingConstraints), _function_10), _function_11).toSet();
        }
        return _xblockexpression_1;
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_5 = (ISLSet s1, ISLSet s2) -> {
        return s1.union(s2);
      };
      final ISLSet tiledDomain = IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLBasicSet, ISLSet>map(domain.getBasicSets(), _function_4), _function_5);
      _xblockexpression = AlphaUtil.renameIndices(tiledDomain, indexNames);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addPatchEquation(final SystemBody systemBody, final Variable patchVar, final Variable WVar, final int[] tileSizes) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final String paramStr = ABFT.buildParamStr(patchVar);
      final List<String> indexNames = patchVar.getDomain().getIndexNames();
      final String indexNamesStr = IterableExtensions.join(indexNames, ",");
      final List<String> spatialIndexNames = WVar.getDomain().getIndexNames();
      final Iterable<Integer> TS = ABFT.spatialSizes(tileSizes);
      final Iterable<String> pqrNames = ABFT.getKernelNames(WVar.getDomain());
      final String bodyDomainStr = IterableExtensions.join(Iterables.<String>concat(indexNames, pqrNames), ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      _builder.append(bodyDomainStr);
      _builder.append("]->[");
      _builder.append(indexNamesStr);
      _builder.append("]}");
      final ISLMultiAff projection = ISLUtil.toISLMultiAff(_builder.toString());
      final Function1<Pair<String, String>, String> _function = (Pair<String, String> it) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _key = it.getKey();
        _builder_1.append(_key);
        _builder_1.append("-");
        String _value = it.getValue();
        _builder_1.append(_value);
        return _builder_1.toString();
      };
      final String patchAccsStr = IterableExtensions.join(ListExtensions.<Pair<String, String>, String>map(CommonExtensions.<String, String>zipWith(spatialIndexNames, pqrNames), _function), ",");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{[");
      _builder_1.append(bodyDomainStr);
      _builder_1.append("]->[w-1,");
      _builder_1.append(patchAccsStr);
      _builder_1.append("]}");
      final ISLMultiAff patchMaff = ISLUtil.toISLMultiAff(_builder_1.toString());
      final DependenceExpression patchDepExpr = AlphaUserFactory.createDependenceExpression(patchMaff, AlphaUserFactory.createVariableExpression(patchVar));
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(paramStr);
      _builder_2.append("->{[");
      _builder_2.append(bodyDomainStr);
      _builder_2.append("]->[");
      String _join = IterableExtensions.join(pqrNames, ",");
      _builder_2.append(_join);
      _builder_2.append("]}");
      final ISLMultiAff WMaff = ISLUtil.toISLMultiAff(_builder_2.toString());
      final DependenceExpression WDepExpr = AlphaUserFactory.createDependenceExpression(WMaff, AlphaUserFactory.createVariableExpression(WVar));
      final BinaryExpression be = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL, patchDepExpr, WDepExpr);
      final ReduceExpression reduceExpr = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, projection, be);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append(paramStr);
      _builder_3.append("->{[");
      _builder_3.append(indexNamesStr);
      _builder_3.append("] : w>0}");
      final ISLSet posWBranchDom = ISLUtil.toISLSet(_builder_3.toString());
      final RestrictExpression posWBranch = AlphaUserFactory.createRestrictExpression(posWBranchDom.copy(), reduceExpr);
      final Function1<Pair<String, Integer>, String> _function_1 = (Pair<String, Integer> it) -> {
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("0<=");
        String _key = it.getKey();
        _builder_4.append(_key);
        _builder_4.append("<");
        Integer _value = it.getValue();
        _builder_4.append(_value);
        return _builder_4.toString();
      };
      final String constraints = IterableExtensions.join(ListExtensions.<Pair<String, Integer>, String>map(CommonExtensions.<String, Integer>zipWith(spatialIndexNames, TS), _function_1), " and ");
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append(paramStr);
      _builder_4.append("->{[");
      _builder_4.append(indexNamesStr);
      _builder_4.append("] : w=0 and ");
      _builder_4.append(constraints);
      _builder_4.append("}");
      final ISLSet zeroWBranchDom = ISLUtil.toISLSet(_builder_4.toString());
      final RestrictExpression zeroWBranch = AlphaUserFactory.createRestrictExpression(zeroWBranchDom.copy());
      zeroWBranch.setExpr(AlphaUserFactory.createPositiveOneExpression(zeroWBranchDom.getSpace()));
      final AutoRestrictExpression defaultBranch = AlphaUserFactory.createAutoRestrictExpression(AlphaUserFactory.createZeroExpression(zeroWBranchDom.getSpace()));
      final CaseExpression ce = AlphaUserFactory.createCaseExpression();
      EList<AlphaExpression> _exprs = ce.getExprs();
      _exprs.add(posWBranch);
      EList<AlphaExpression> _exprs_1 = ce.getExprs();
      _exprs_1.add(zeroWBranch);
      EList<AlphaExpression> _exprs_2 = ce.getExprs();
      _exprs_2.add(defaultBranch);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(patchVar, ce);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addIEquation(final SystemBody systemBody, final Variable IVar, final Variable C1Var, final Variable C2Var, final Variable WVar, final boolean forV2) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> C1IndexNames = C1Var.getDomain().getIndexNames();
      final List<String> C2IndexNames = C2Var.getDomain().getIndexNames();
      final String paramStr = ABFT.buildParamStr(C2Var);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      String _join = IterableExtensions.join(C2IndexNames, ",");
      _builder.append(_join);
      _builder.append("]->[");
      String _join_1 = IterableExtensions.join(C1IndexNames, ",");
      _builder.append(_join_1);
      _builder.append("]}");
      final ISLMultiAff projection = ISLUtil.toISLMultiAff(_builder.toString());
      final ReduceExpression reduceExpr = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, projection, ABFT.createIdentityDepExpr(C2Var));
      final BinaryExpression subExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.SUB);
      AlphaExpression _xifexpression = null;
      if (forV2) {
        _xifexpression = reduceExpr;
      } else {
        _xifexpression = ABFT.createIdentityDepExpr(C2Var);
      }
      subExpr.setLeft(_xifexpression);
      subExpr.setRight(ABFT.createIdentityDepExpr(C1Var));
      final BinaryExpression divExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.DIV);
      divExpr.setLeft(subExpr);
      divExpr.setRight(ABFT.createIdentityDepExpr(C1Var));
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(IVar, divExpr);
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
      final String paramStr = ABFT.buildParamStr(variable.getDomain().getSpace());
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

  public static List<AlphaIssue> addV1C2Equation(final SystemBody systemBody, final Variable CVar, final Variable stencilVar, final Variable WVar, final Variable patchVar, final int[] tileSizes, final int radius) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> CIndexNames = CVar.getDomain().getIndexNames();
      final String paramStr = ABFT.buildParamStr(CVar);
      final Iterable<String> pqrNames = ABFT.getKernelNames(WVar.getDomain());
      final String bodyIndexStr = IterableExtensions.join(Iterables.<String>concat(CIndexNames, pqrNames), ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      _builder.append(bodyIndexStr);
      _builder.append("]->[");
      String _join = IterableExtensions.join(CIndexNames, ",");
      _builder.append(_join);
      _builder.append("]}");
      final ISLMultiAff projection = ISLUtil.toISLMultiAff(_builder.toString());
      final String tt = CIndexNames.get(0);
      final int TT = tileSizes[0];
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{[");
      _builder_1.append(bodyIndexStr);
      _builder_1.append("]->[");
      _builder_1.append(TT);
      _builder_1.append(",");
      String _join_1 = IterableExtensions.join(pqrNames, ",");
      _builder_1.append(_join_1);
      _builder_1.append("]}");
      final ISLMultiAff patchMaff = ISLUtil.toISLMultiAff(_builder_1.toString());
      final DependenceExpression patchDepExpr = AlphaUserFactory.createDependenceExpression(patchMaff, AlphaUserFactory.createVariableExpression(patchVar));
      int _size = CIndexNames.size();
      final Function1<Integer, String> _function = (Integer i) -> {
        return CIndexNames.get((i).intValue());
      };
      final Iterable<String> spatialTileNames = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
      final Function1<Pair<Pair<Integer, String>, String>, String> _function_1 = (Pair<Pair<Integer, String>, String> it) -> {
        String _xblockexpression_1 = null;
        {
          final Integer TS = it.getKey().getKey();
          final String ts = it.getKey().getValue();
          final String pqrName = it.getValue();
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append(TS);
          _builder_2.append(ts);
          _builder_2.append("+");
          _builder_2.append(pqrName);
          _xblockexpression_1 = _builder_2.toString();
        }
        return _xblockexpression_1;
      };
      final String spatialAccStr = IterableExtensions.join(ListExtensions.<Pair<Pair<Integer, String>, String>, String>map(CommonExtensions.<Pair<Integer, String>, String>zipWith(CommonExtensions.<Integer, String>zipWith(ABFT.spatialSizes(tileSizes), spatialTileNames), pqrNames), _function_1), ",");
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(TT);
      _builder_2.append(tt);
      _builder_2.append("-");
      _builder_2.append(TT);
      _builder_2.append(",");
      _builder_2.append(spatialAccStr);
      final String stencilVarAccStr = _builder_2.toString();
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append(paramStr);
      _builder_3.append("->{[");
      _builder_3.append(bodyIndexStr);
      _builder_3.append("]->[");
      _builder_3.append(stencilVarAccStr);
      _builder_3.append("]}");
      final ISLMultiAff stencilVarMaff = ISLUtil.toISLMultiAff(_builder_3.toString());
      final DependenceExpression stencilVarDepExpr = AlphaUserFactory.createDependenceExpression(stencilVarMaff, AlphaUserFactory.createVariableExpression(stencilVar));
      final BinaryExpression be = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL, patchDepExpr, stencilVarDepExpr);
      final ReduceExpression reduceExpr = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, projection, be);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(CVar, reduceExpr);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addV2C2Equation(final SystemBody systemBody, final Variable CVar, final Variable stencilVar, final Variable WVar, final Variable allWVar, final Variable combosWVar, final int[] tileSizes, final int radius) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> CIndexNames = CVar.getDomain().getIndexNames();
      final String paramStr = ABFT.buildParamStr(CVar);
      final List<String> spatialIndexNames = WVar.getDomain().getIndexNames();
      String _get = CIndexNames.get(0);
      int _get_1 = tileSizes[0];
      final Pair<String, Integer> timeContext = Pair.<String, Integer>of(_get, Integer.valueOf(_get_1));
      final ArrayList<Pair<Pair<String, String>, Pair<Integer, Integer>>> spatialContext = ABFT.buildSpatialContext(tileSizes, radius, ((String[])Conversions.unwrapArray(CIndexNames, String.class)), ((String[])Conversions.unwrapArray(spatialIndexNames, String.class)), true);
      final Iterable<String> pqrNames = ABFT.getKernelNames(WVar.getDomain());
      final CaseExpression ce = AlphaUserFactory.createCaseExpression();
      final Consumer<ArrayList<Integer>> _function = (ArrayList<Integer> pqrOrthant) -> {
        EList<AlphaExpression> _exprs = ce.getExprs();
        RestrictExpression _createC2ReductionBranch = ABFT.createC2ReductionBranch(paramStr, stencilVar, allWVar, ((String[])Conversions.unwrapArray(CIndexNames, String.class)), ((String[])Conversions.unwrapArray(spatialIndexNames, String.class)), ((String[])Conversions.unwrapArray(pqrNames, String.class)), ((Integer[])Conversions.unwrapArray(pqrOrthant, Integer.class)), spatialContext, timeContext);
        _exprs.add(_createC2ReductionBranch);
      };
      CommonExtensions.<Integer>permutations(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf((-1)), Integer.valueOf(1), Integer.valueOf(0))), IterableExtensions.size(pqrNames)).forEach(_function);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      String _join = IterableExtensions.join(CIndexNames, ",");
      _builder.append(_join);
      _builder.append("]->[");
      String _join_1 = IterableExtensions.join(pqrNames, ",");
      _builder.append(_join_1);
      _builder.append("]}");
      final ISLMultiAff combosWMaff = ISLUtil.toISLMultiAff(_builder.toString());
      final DependenceExpression combosWDepExpr = AlphaUserFactory.createDependenceExpression(combosWMaff);
      combosWDepExpr.setExpr(AlphaUserFactory.createVariableExpression(combosWVar));
      final BinaryExpression be = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL);
      be.setLeft(combosWDepExpr);
      be.setRight(ce);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(CVar, be);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static RestrictExpression createC2ReductionBranch(final String paramStr, final Variable stencilVar, final Variable allWVar, final String[] CIndexNames, final String[] spatialIndexNames, final String[] pqrNames, final Integer[] pqrOrthant, final List<Pair<Pair<String, String>, Pair<Integer, Integer>>> spatialContext, final Pair<String, Integer> timeContext) {
    RestrictExpression _xblockexpression = null;
    {
      final Function1<Pair<String, Integer>, Boolean> _function = (Pair<String, Integer> it) -> {
        Integer _value = it.getValue();
        return Boolean.valueOf(((_value).intValue() == 0));
      };
      final Function1<Pair<String, Integer>, String> _function_1 = (Pair<String, Integer> it) -> {
        return it.getKey();
      };
      final Iterable<String> accumulationDims = IterableExtensions.<Pair<String, Integer>, String>map(IterableExtensions.<Pair<String, Integer>>filter(CommonExtensions.<String, Integer>zipWith(((Iterable<String>)Conversions.doWrapArray(spatialIndexNames)), ((Iterable<Integer>)Conversions.doWrapArray(pqrOrthant))), _function), _function_1);
      Iterable<String> _plus = Iterables.<String>concat(((Iterable<? extends String>)Conversions.doWrapArray(CIndexNames)), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("w")));
      final Iterable<String> domainIndexNames = Iterables.<String>concat(_plus, accumulationDims);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      String _join = IterableExtensions.join(domainIndexNames, ",");
      _builder.append(_join);
      _builder.append("]");
      final String projectionDomainStr = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("[");
      String _join_1 = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(CIndexNames)), ",");
      _builder_1.append(_join_1);
      _builder_1.append("]");
      final String projectionRangeStr = _builder_1.toString();
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(paramStr);
      _builder_2.append("->{");
      _builder_2.append(projectionDomainStr);
      _builder_2.append("->");
      _builder_2.append(projectionRangeStr);
      _builder_2.append("}");
      final ISLMultiAff projection = ISLUtil.toISLMultiAff(_builder_2.toString());
      final String tt = timeContext.getKey();
      final Integer TT = timeContext.getValue();
      String _xifexpression = null;
      final Function1<Integer, Boolean> _function_2 = (Integer v) -> {
        return Boolean.valueOf(((v).intValue() == 0));
      };
      final Function2<Boolean, Boolean, Boolean> _function_3 = (Boolean v1, Boolean v2) -> {
        return Boolean.valueOf(((v1).booleanValue() && (v2).booleanValue()));
      };
      Boolean _reduce = IterableExtensions.<Boolean>reduce(ListExtensions.<Integer, Boolean>map(((List<Integer>)Conversions.doWrapArray(pqrOrthant)), _function_2), _function_3);
      if ((boolean) _reduce) {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("w=");
        _builder_3.append(TT);
        _xifexpression = _builder_3.toString();
      } else {
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("1<=w<=");
        _builder_4.append(TT);
        _xifexpression = _builder_4.toString();
      }
      final String wRangeConstraint = _xifexpression;
      StringConcatenation _builder_5 = new StringConcatenation();
      _builder_5.append(TT);
      _builder_5.append(tt);
      _builder_5.append("-w");
      final List<String> timeExpr = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_builder_5.toString()));
      final ArrayList<Pair<String, Integer>> pqrContext = CommonExtensions.<String, Integer>zipWith(((Iterable<String>)Conversions.doWrapArray(pqrNames)), ((Iterable<Integer>)Conversions.doWrapArray(pqrOrthant)));
      final Function1<Pair<Pair<String, Integer>, Pair<Pair<String, String>, Pair<Integer, Integer>>>, String> _function_4 = (Pair<Pair<String, Integer>, Pair<Pair<String, String>, Pair<Integer, Integer>>> it) -> {
        String _xblockexpression_1 = null;
        {
          final Pair<String, Integer> pqrc = it.getKey();
          final String pqrName = pqrc.getKey();
          final Integer pqrValue = pqrc.getValue();
          final Pair<Pair<String, String>, Pair<Integer, Integer>> sc = it.getValue();
          final String ts = sc.getKey().getKey();
          final String s = sc.getKey().getValue();
          final Integer coeff = sc.getValue().getKey();
          final Integer TS = sc.getValue().getValue();
          String _switchResult = null;
          boolean _matched = false;
          if (((pqrValue).intValue() < 0)) {
            _matched=true;
            StringConcatenation _builder_6 = new StringConcatenation();
            _builder_6.append(coeff);
            _builder_6.append(ts);
            _builder_6.append("+");
            _builder_6.append(pqrName);
            _builder_6.append("+w");
            _switchResult = _builder_6.toString();
          }
          if (!_matched) {
            if (((pqrValue).intValue() > 0)) {
              _matched=true;
              StringConcatenation _builder_7 = new StringConcatenation();
              _builder_7.append(coeff);
              _builder_7.append(ts);
              _builder_7.append("+");
              _builder_7.append(pqrName);
              _builder_7.append("+w+");
              _builder_7.append(TS);
              _builder_7.append("-1-2w");
              _switchResult = _builder_7.toString();
            }
          }
          if (!_matched) {
            if (((pqrValue).intValue() == 0)) {
              _matched=true;
              StringConcatenation _builder_8 = new StringConcatenation();
              _builder_8.append(s);
              _switchResult = _builder_8.toString();
            }
          }
          _xblockexpression_1 = _switchResult;
        }
        return _xblockexpression_1;
      };
      final List<String> spaceExprs = ListExtensions.<Pair<Pair<String, Integer>, Pair<Pair<String, String>, Pair<Integer, Integer>>>, String>map(CommonExtensions.<Pair<String, Integer>, Pair<Pair<String, String>, Pair<Integer, Integer>>>zipWith(pqrContext, spatialContext), _function_4);
      final Iterable<String> rangeExprs = Iterables.<String>concat(timeExpr, spaceExprs);
      StringConcatenation _builder_6 = new StringConcatenation();
      _builder_6.append(paramStr);
      _builder_6.append("->{[");
      String _join_2 = IterableExtensions.join(domainIndexNames, ",");
      _builder_6.append(_join_2);
      _builder_6.append("]->[");
      String _join_3 = IterableExtensions.join(rangeExprs, ",");
      _builder_6.append(_join_3);
      _builder_6.append("]}");
      final ISLMultiAff stencilVarMaff = ISLUtil.toISLMultiAff(_builder_6.toString());
      final DependenceExpression stencilVarDepExpr = AlphaUserFactory.createDependenceExpression(stencilVarMaff);
      stencilVarDepExpr.setExpr(AlphaUserFactory.createVariableExpression(stencilVar));
      StringConcatenation _builder_7 = new StringConcatenation();
      _builder_7.append(paramStr);
      _builder_7.append("->{[");
      String _join_4 = IterableExtensions.join(domainIndexNames, ",");
      _builder_7.append(_join_4);
      _builder_7.append("]->[w]}");
      final ISLMultiAff allWVarMaff = ISLUtil.toISLMultiAff(_builder_7.toString());
      final DependenceExpression allWVarDepExpr = AlphaUserFactory.createDependenceExpression(allWVarMaff);
      allWVarDepExpr.setExpr(AlphaUserFactory.createVariableExpression(allWVar));
      final BinaryExpression be = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL);
      be.setLeft(allWVarDepExpr);
      be.setRight(stencilVarDepExpr);
      final Function1<Pair<Pair<String, String>, Pair<Integer, Integer>>, Boolean> _function_5 = (Pair<Pair<String, String>, Pair<Integer, Integer>> it) -> {
        return Boolean.valueOf(IterableExtensions.contains(accumulationDims, it.getKey().getValue()));
      };
      final Function1<Pair<Pair<String, String>, Pair<Integer, Integer>>, String> _function_6 = (Pair<Pair<String, String>, Pair<Integer, Integer>> it) -> {
        String _xblockexpression_1 = null;
        {
          final String ts = it.getKey().getKey();
          final String s = it.getKey().getValue();
          final Integer coeff = it.getValue().getKey();
          final Integer TS = it.getValue().getValue();
          StringConcatenation _builder_8 = new StringConcatenation();
          _builder_8.append(coeff);
          _builder_8.append(ts);
          _builder_8.append("+w<=");
          _builder_8.append(s);
          _builder_8.append("<");
          _builder_8.append(coeff);
          _builder_8.append(ts);
          _builder_8.append("+");
          _builder_8.append(TS);
          _builder_8.append("-w");
          _xblockexpression_1 = _builder_8.toString();
        }
        return _xblockexpression_1;
      };
      final List<String> constraints = IterableExtensions.<String>toList(IterableExtensions.<Pair<Pair<String, String>, Pair<Integer, Integer>>, String>map(IterableExtensions.<Pair<Pair<String, String>, Pair<Integer, Integer>>>filter(spatialContext, _function_5), _function_6));
      constraints.add(0, wRangeConstraint);
      StringConcatenation _builder_8 = new StringConcatenation();
      _builder_8.append(paramStr);
      _builder_8.append("->{");
      _builder_8.append(projectionDomainStr);
      _builder_8.append(" : ");
      String _join_5 = IterableExtensions.join(constraints, " and ");
      _builder_8.append(_join_5);
      _builder_8.append("}");
      final String bodyDomainStr = _builder_8.toString();
      final ISLSet bodyDomain = ISLUtil.toISLSet(bodyDomainStr);
      final RestrictExpression body = AlphaUserFactory.createRestrictExpression(bodyDomain.copy());
      body.setExpr(be);
      final ReduceExpression reduceExpr = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, projection, body);
      final ISLSet branchDom = ABFT.createC2BranchDomain(paramStr, CIndexNames, pqrNames, pqrOrthant);
      final RestrictExpression branch = AlphaUserFactory.createRestrictExpression(branchDom);
      branch.setExpr(reduceExpr);
      _xblockexpression = branch;
    }
    return _xblockexpression;
  }

  public static ISLSet createC2BranchDomain(final String paramStr, final String[] domainIndexNames, final String[] pqrNames, final Integer[] pqrOrthant) {
    ISLSet _xblockexpression = null;
    {
      final Function1<Pair<String, Integer>, String> _function = (Pair<String, Integer> it) -> {
        String _switchResult = null;
        Integer _value = it.getValue();
        boolean _matched = false;
        Integer _value_1 = it.getValue();
        boolean _lessThan = ((_value_1).intValue() < 0);
        if (_lessThan) {
          _matched=true;
          StringConcatenation _builder = new StringConcatenation();
          String _key = it.getKey();
          _builder.append(_key);
          _builder.append("<0");
          _switchResult = _builder.toString();
        }
        if (!_matched) {
          Integer _value_2 = it.getValue();
          boolean _greaterThan = ((_value_2).intValue() > 0);
          if (_greaterThan) {
            _matched=true;
            StringConcatenation _builder_1 = new StringConcatenation();
            String _key_1 = it.getKey();
            _builder_1.append(_key_1);
            _builder_1.append(">0");
            _switchResult = _builder_1.toString();
          }
        }
        if (!_matched) {
          Integer _value_3 = it.getValue();
          boolean _equals = ((_value_3).intValue() == 0);
          if (_equals) {
            _matched=true;
            StringConcatenation _builder_2 = new StringConcatenation();
            String _key_2 = it.getKey();
            _builder_2.append(_key_2);
            _builder_2.append("=0");
            _switchResult = _builder_2.toString();
          }
        }
        return _switchResult;
      };
      final String constraints = IterableExtensions.join(ListExtensions.<Pair<String, Integer>, String>map(CommonExtensions.<String, Integer>zipWith(((Iterable<String>)Conversions.doWrapArray(pqrNames)), ((Iterable<Integer>)Conversions.doWrapArray(pqrOrthant))), _function), " and ");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      String _join = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(domainIndexNames)), ",");
      _builder.append(_join);
      _builder.append("] : ");
      _builder.append(constraints);
      _builder.append("}");
      final ISLSet set = ISLUtil.toISLSet(_builder.toString());
      _xblockexpression = set;
    }
    return _xblockexpression;
  }

  public static ArrayList<Pair<Pair<String, String>, Pair<Integer, Integer>>> buildSpatialContext(final int[] tileSizes, final int radius, final String[] CIndexNames, final String[] spatialIndexNames, final boolean forV2) {
    ArrayList<Pair<Pair<String, String>, Pair<Integer, Integer>>> _xblockexpression = null;
    {
      final int TT = tileSizes[0];
      int _size = ((List<Integer>)Conversions.doWrapArray(tileSizes)).size();
      final Function1<Integer, String> _function = (Integer i) -> {
        return CIndexNames[(i).intValue()];
      };
      final Iterable<String> spatialTileIndexNames = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
      final Iterable<Integer> spatialTileSizes = ABFT.spatialSizes(tileSizes);
      final Function1<Integer, Integer> _function_1 = (Integer TS) -> {
        Integer _xifexpression = null;
        if (forV2) {
          _xifexpression = Integer.valueOf(((TS).intValue() - ((2 * radius) * TT)));
        } else {
          _xifexpression = TS;
        }
        return _xifexpression;
      };
      final Iterable<Integer> spatialTileCoeffs = IterableExtensions.<Integer, Integer>map(spatialTileSizes, _function_1);
      final ArrayList<Pair<String, String>> spatialNames = CommonExtensions.<String, String>zipWith(spatialTileIndexNames, ((Iterable<String>)Conversions.doWrapArray(spatialIndexNames)));
      final ArrayList<Pair<Integer, Integer>> spatialSizes = CommonExtensions.<Integer, Integer>zipWith(spatialTileCoeffs, spatialTileSizes);
      _xblockexpression = CommonExtensions.<Pair<String, String>, Pair<Integer, Integer>>zipWith(spatialNames, spatialSizes);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addC1Equation(final SystemBody systemBody, final Variable CVar, final Variable stencilVar, final Variable WVar, final int[] tileSizes, final int radius, final boolean forV2) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> CIndexNames = CVar.getDomain().getIndexNames();
      final String paramStr = ABFT.buildParamStr(CVar);
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
      final String constraints = IterableExtensions.join(ListExtensions.<Pair<Pair<String, String>, Pair<Integer, Integer>>, String>map(ABFT.buildSpatialContext(tileSizes, radius, ((String[])Conversions.unwrapArray(CIndexNames, String.class)), ((String[])Conversions.unwrapArray(spatialIndexNames, String.class)), forV2), _function), " and ");
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

  public static List<AlphaIssue> addAllWEquation(final SystemBody systemBody, final Variable allWVar, final Variable combosWVar) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final String paramStr = ABFT.buildParamStr(allWVar);
      final ISLSpace space = allWVar.getDomain().getSpace();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[w] : w=1}");
      final ISLSet oneBranchDom = ISLUtil.toISLSet(_builder.toString());
      final RestrictExpression oneBranch = AlphaUserFactory.createRestrictExpression(oneBranchDom);
      oneBranch.setExpr(AlphaUserFactory.createPositiveOneExpression(space));
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{[w]->[w-1]}");
      final ISLMultiAff allWMaff = ISLUtil.toISLMultiAff(_builder_1.toString());
      final DependenceExpression allWDepExpr = AlphaUserFactory.createDependenceExpression(allWMaff);
      allWDepExpr.setExpr(AlphaUserFactory.createVariableExpression(allWVar));
      final int nbDim = combosWVar.getDomain().getSpace().dim(ISLDimType.isl_dim_out);
      final Function1<Integer, Integer> _function = (Integer it) -> {
        return Integer.valueOf(0);
      };
      final String combosWRange = IterableExtensions.join(IterableExtensions.<Integer, Integer>map(new ExclusiveRange(0, nbDim, true), _function), ",");
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(paramStr);
      _builder_2.append("->{[w]->[");
      _builder_2.append(combosWRange);
      _builder_2.append("]}");
      final ISLMultiAff combosWMaff = ISLUtil.toISLMultiAff(_builder_2.toString());
      final DependenceExpression combosWDepExpr = AlphaUserFactory.createDependenceExpression(combosWMaff);
      combosWDepExpr.setExpr(AlphaUserFactory.createVariableExpression(combosWVar));
      final BinaryExpression be = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL);
      be.setLeft(allWDepExpr);
      be.setRight(combosWDepExpr);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append(paramStr);
      _builder_3.append("->{[w] : w>1}");
      final ISLSet defaultBranchDom = ISLUtil.toISLSet(_builder_3.toString());
      final RestrictExpression defaultBranch = AlphaUserFactory.createRestrictExpression(defaultBranchDom);
      defaultBranch.setExpr(be);
      final CaseExpression ce = AlphaUserFactory.createCaseExpression();
      EList<AlphaExpression> _exprs = ce.getExprs();
      _exprs.add(oneBranch);
      EList<AlphaExpression> _exprs_1 = ce.getExprs();
      _exprs_1.add(defaultBranch);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(allWVar, ce);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addCombosWEquation(final SystemBody systemBody, final Variable combosWVar, final Variable kernelWVar, final Variable WVar) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final String paramStr = ABFT.buildParamStr(combosWVar);
      final List<String> indexNames = combosWVar.getDomain().getIndexNames();
      final Iterable<String> kernelNames = ABFT.getKernelNames(kernelWVar.getDomain());
      final String domainStr = IterableExtensions.join(Iterables.<String>concat(indexNames, kernelNames), ",");
      final String rangeStr = IterableExtensions.join(combosWVar.getDomain().getIndexNames(), ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      _builder.append(domainStr);
      _builder.append("]->[");
      _builder.append(rangeStr);
      _builder.append("]}");
      final ISLMultiAff projection = ISLUtil.toISLMultiAff(_builder.toString());
      final Function1<Pair<String, String>, String> _function = (Pair<String, String> it) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _key = it.getKey();
        _builder_1.append(_key);
        _builder_1.append("-");
        String _value = it.getValue();
        _builder_1.append(_value);
        return _builder_1.toString();
      };
      final String kernelWMaffRangeStr = IterableExtensions.join(ListExtensions.<Pair<String, String>, String>map(CommonExtensions.<String, String>zipWith(indexNames, kernelNames), _function), ",");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{[");
      _builder_1.append(domainStr);
      _builder_1.append("]->[");
      _builder_1.append(kernelWMaffRangeStr);
      _builder_1.append("]}");
      final ISLMultiAff kernelWMaff = ISLUtil.toISLMultiAff(_builder_1.toString());
      final DependenceExpression kernelWDepExpr = AlphaUserFactory.createDependenceExpression(kernelWMaff);
      kernelWDepExpr.setExpr(AlphaUserFactory.createVariableExpression(kernelWVar));
      final String WMaffRangeStr = IterableExtensions.join(kernelNames, ",");
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(paramStr);
      _builder_2.append("->{[");
      _builder_2.append(domainStr);
      _builder_2.append("]->[");
      _builder_2.append(WMaffRangeStr);
      _builder_2.append("]}");
      final ISLMultiAff WMaff = ISLUtil.toISLMultiAff(_builder_2.toString());
      final DependenceExpression WDepExpr = AlphaUserFactory.createDependenceExpression(WMaff);
      WDepExpr.setExpr(AlphaUserFactory.createVariableExpression(WVar));
      final BinaryExpression be = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL);
      be.setLeft(kernelWDepExpr);
      be.setRight(WDepExpr);
      final ReduceExpression reduceExpr = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, projection, be);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(combosWVar, reduceExpr);
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

  public static List<AlphaIssue> addWeightsEquation(final SystemBody systemBody, final Variable variable, final Map<List<Integer>, Double> kernel) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> indexNames = variable.getDomain().getIndexNames();
      final ISLSpace space = variable.getDomain().getSpace();
      final CaseExpression ce = AlphaUserFactory.createCaseExpression();
      EList<AlphaExpression> _exprs = ce.getExprs();
      final Function1<List<Integer>, RestrictExpression> _function = (List<Integer> indexValues) -> {
        RestrictExpression _xblockexpression_1 = null;
        {
          final Double coeff = kernel.get(indexValues);
          _xblockexpression_1 = ABFT.createWeightRestrictBranch(space, ((String[])Conversions.unwrapArray(indexNames, String.class)), ((Integer[])Conversions.unwrapArray(indexValues, Integer.class)), coeff.floatValue());
        }
        return _xblockexpression_1;
      };
      Iterable<RestrictExpression> _map = IterableExtensions.<List<Integer>, RestrictExpression>map(kernel.keySet(), _function);
      Iterables.<AlphaExpression>addAll(_exprs, _map);
      final Function1<AlphaExpression, RestrictExpression> _function_1 = (AlphaExpression e) -> {
        return ((RestrictExpression) e);
      };
      final Function1<RestrictExpression, ISLSet> _function_2 = (RestrictExpression re) -> {
        return re.getRestrictDomain();
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_3 = (ISLSet d1, ISLSet d2) -> {
        return d1.copy().union(d2.copy());
      };
      final ISLSet branchDoms = IterableExtensions.<ISLSet>reduce(ListExtensions.<RestrictExpression, ISLSet>map(ListExtensions.<AlphaExpression, RestrictExpression>map(ce.getExprs(), _function_1), _function_2), _function_3);
      final ISLSet WVarDom = variable.getDomain().copy();
      boolean _isEmpty = WVarDom.subtract(branchDoms).isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        final AutoRestrictExpression defaultBranch = AlphaUserFactory.createAutoRestrictExpression();
        defaultBranch.setExpr(AlphaUserFactory.createZeroExpression(space));
        EList<AlphaExpression> _exprs_1 = ce.getExprs();
        _exprs_1.add(defaultBranch);
      }
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(variable, ce);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static RestrictExpression createWeightRestrictBranch(final ISLSpace space, final String[] indexNames, final Integer[] indexValues, final float coeff) {
    RestrictExpression _xblockexpression = null;
    {
      ABFT.createRealDepExpr(space, coeff);
      final String paramStr = ABFT.buildParamStr(space);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      String _join = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(indexNames)), ",");
      _builder.append(_join);
      _builder.append("]");
      final String indexStr = _builder.toString();
      final Function1<Pair<String, Integer>, String> _function = (Pair<String, Integer> it) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _key = it.getKey();
        _builder_1.append(_key);
        _builder_1.append("=");
        Integer _value = it.getValue();
        _builder_1.append(_value);
        return _builder_1.toString();
      };
      final String constraints = IterableExtensions.join(ListExtensions.<Pair<String, Integer>, String>map(CommonExtensions.<String, Integer>zipWith(((Iterable<String>)Conversions.doWrapArray(indexNames)), ((Iterable<Integer>)Conversions.doWrapArray(indexValues))), _function), " and ");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{");
      _builder_1.append(indexStr);
      _builder_1.append(" : ");
      _builder_1.append(constraints);
      _builder_1.append("}");
      final ISLSet restrictDomain = ISLUtil.toISLSet(_builder_1.toString());
      final RestrictExpression re = AlphaUserFactory.createRestrictExpression(restrictDomain);
      re.setExpr(ABFT.createRealDepExpr(space, coeff));
      _xblockexpression = re;
    }
    return _xblockexpression;
  }

  public static DependenceExpression createRealDepExpr(final ISLSpace space, final float value) {
    return AlphaUserFactory.createDependenceExpression(ISLUtil.createConstantMaff(space), AlphaUserFactory.createRealExpression(value));
  }

  public static Iterable<String> getKernelNames(final ISLSet kernelDomain) {
    Iterable<String> _xblockexpression = null;
    {
      final int nbKernel = kernelDomain.dim(ISLDimType.isl_dim_out);
      final Function1<Integer, String> _function = (Integer i) -> {
        return Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("p", "q", "r")).get((i).intValue());
      };
      _xblockexpression = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, nbKernel, true), _function);
    }
    return _xblockexpression;
  }

  public static ISLSet buildC2Domain(final ISLSet baseDomain, final ISLSet kernelDomain, final boolean forV2) {
    ISLSet _xblockexpression = null;
    {
      if ((!forV2)) {
        return baseDomain.copy();
      }
      final int nbKernel = kernelDomain.dim(ISLDimType.isl_dim_out);
      final int nbBase = baseDomain.dim(ISLDimType.isl_dim_out);
      final Iterable<String> kernelNames = ABFT.getKernelNames(kernelDomain);
      final List<String> baseNames = baseDomain.getIndexNames();
      final List<String> combinedNames = IterableExtensions.<String>toList(Iterables.<String>concat(baseNames, kernelNames));
      final ISLSet extBaseDomain = AlphaUtil.renameIndices(
        baseDomain.copy().insertDims(ISLDimType.isl_dim_out, nbBase, nbKernel), combinedNames);
      final ISLSet extKernelDomain = AlphaUtil.renameIndices(
        kernelDomain.copy().insertDims(ISLDimType.isl_dim_out, 0, nbBase), combinedNames);
      _xblockexpression = extBaseDomain.intersect(extKernelDomain);
    }
    return _xblockexpression;
  }

  public static ISLSet buildAllWeightsDomain(final Variable variable, final int TT) {
    ISLSet _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      String _buildParamStr = ABFT.buildParamStr(variable);
      _builder.append(_buildParamStr);
      _builder.append("->{[w] : 1<=w<=");
      _builder.append(TT);
      _builder.append("}");
      final String setStr = _builder.toString();
      _xblockexpression = ISLUtil.toISLSet(setStr);
    }
    return _xblockexpression;
  }

  public static ISLSet buildPatchDomain(final ISLSet WDomain, final int[] tileSizes, final int radius) {
    ISLSet _xblockexpression = null;
    {
      final List<String> indexNames = WDomain.getIndexNames();
      final String paramStr = ABFT.buildParamStr(WDomain.getSpace());
      final int TT = tileSizes[0];
      final Iterable<Integer> TS = ABFT.spatialSizes(tileSizes);
      final Function1<Pair<String, Integer>, String> _function = (Pair<String, Integer> it) -> {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("-");
        _builder.append(radius);
        _builder.append("-");
        _builder.append(radius);
        _builder.append("w<=");
        String _key = it.getKey();
        _builder.append(_key);
        _builder.append("<");
        Integer _value = it.getValue();
        _builder.append(_value);
        _builder.append("+");
        _builder.append(radius);
        _builder.append("+");
        _builder.append(radius);
        _builder.append("w");
        return _builder.toString();
      };
      final String constraints = IterableExtensions.join(ListExtensions.<Pair<String, Integer>, String>map(CommonExtensions.<String, Integer>zipWith(indexNames, TS), _function), " and ");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[w,");
      String _join = IterableExtensions.join(indexNames, ",");
      _builder.append(_join);
      _builder.append("] : 0<=w<=");
      _builder.append(TT);
      _builder.append(" and ");
      _builder.append(constraints);
      _builder.append("}");
      final String setStr = _builder.toString();
      _xblockexpression = ISLUtil.toISLSet(setStr);
    }
    return _xblockexpression;
  }

  public static ISLSet buildWeightsDomain(final Variable variable, final int radius) {
    ISLSet _xblockexpression = null;
    {
      final List<String> indexNames = variable.getDomain().getIndexNames();
      int _size = indexNames.size();
      final Function1<Integer, String> _function = (Integer i) -> {
        return indexNames.get((i).intValue());
      };
      final Iterable<String> spaceIndexNames = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      String _join = IterableExtensions.join(spaceIndexNames, ",");
      _builder.append(_join);
      _builder.append("]");
      final String indexStr = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("-");
      _builder_1.append(radius);
      _builder_1.append("<=");
      String _join_1 = IterableExtensions.join(spaceIndexNames, ",");
      _builder_1.append(_join_1);
      _builder_1.append("<=");
      _builder_1.append(radius);
      final String constraints = _builder_1.toString();
      StringConcatenation _builder_2 = new StringConcatenation();
      String _buildParamStr = ABFT.buildParamStr(variable);
      _builder_2.append(_buildParamStr);
      _builder_2.append("->{");
      _builder_2.append(indexStr);
      _builder_2.append(" : ");
      _builder_2.append(constraints);
      _builder_2.append("}");
      final String setStr = _builder_2.toString();
      _xblockexpression = ISLUtil.toISLSet(setStr);
    }
    return _xblockexpression;
  }

  public static ISLSet buildChecksumDomain(final Variable variable, final int[] tileSizes, final int radius, final boolean forV2) {
    ISLSet _xblockexpression = null;
    {
      final Function1<String, String> _function = (String i) -> {
        return ("t" + i);
      };
      final List<String> indexNames = ListExtensions.<String, String>map(variable.getDomain().getIndexNames(), _function);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      String _join = IterableExtensions.join(indexNames, ",");
      _builder.append(_join);
      _builder.append("]");
      final String indexStr = _builder.toString();
      final int TT = tileSizes[0];
      final String tt = indexNames.get(0);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(TT);
      _builder_1.append("<");
      _builder_1.append(TT);
      _builder_1.append(tt);
      _builder_1.append(" and ");
      _builder_1.append(TT);
      _builder_1.append(tt);
      _builder_1.append("+");
      _builder_1.append(TT);
      _builder_1.append("<=T");
      final String timeConstraints = _builder_1.toString();
      int _size = indexNames.size();
      final Function1<Integer, String> _function_1 = (Integer i) -> {
        return indexNames.get((i).intValue());
      };
      final Iterable<String> spaceIndexNames = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function_1);
      final Iterable<Integer> spaceTileSizes = ABFT.spatialSizes(tileSizes);
      final Function1<Pair<Integer, String>, String> _function_2 = (Pair<Integer, String> it) -> {
        String _xblockexpression_1 = null;
        {
          Integer _xifexpression = null;
          if (forV2) {
            Integer _key = it.getKey();
            _xifexpression = Integer.valueOf(((_key).intValue() - ((2 * radius) * TT)));
          } else {
            _xifexpression = it.getKey();
          }
          final Integer TSlb = _xifexpression;
          final Integer TSub = it.getKey();
          final String ts = it.getValue();
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("1<");
          _builder_2.append(TSlb);
          _builder_2.append(ts);
          _builder_2.append(" and ");
          _builder_2.append(TSlb);
          _builder_2.append(ts);
          _builder_2.append("+");
          _builder_2.append(TSub);
          _builder_2.append("<N");
          _xblockexpression_1 = _builder_2.toString();
        }
        return _xblockexpression_1;
      };
      final String spaceConstraints = IterableExtensions.join(ListExtensions.<Pair<Integer, String>, String>map(CommonExtensions.<Integer, String>zipWith(spaceTileSizes, spaceIndexNames), _function_2), " and ");
      StringConcatenation _builder_2 = new StringConcatenation();
      String _buildParamStr = ABFT.buildParamStr(variable);
      _builder_2.append(_buildParamStr);
      _builder_2.append("->{");
      _builder_2.append(indexStr);
      _builder_2.append(" : ");
      _builder_2.append(timeConstraints);
      _builder_2.append(" and ");
      _builder_2.append(spaceConstraints);
      _builder_2.append("}");
      final String setStr = _builder_2.toString();
      _xblockexpression = ISLUtil.toISLSet(setStr);
    }
    return _xblockexpression;
  }

  public static String buildParamStr(final Variable variable) {
    return ABFT.buildParamStr(variable.getDomain().getSpace());
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
    final Iterable<Integer> Ls = ABFT.spatialSizes(tileSizes);
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
      AlphaRoot root = ABFT.loadOrGet.get(file);
      if ((root == null)) {
        root = AlphaLoader.loadAlpha(("resources/inputs/" + file));
        ABFT.loadOrGet.put(file, root);
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
