package alpha.abft;

import alpha.abft.analysis.ConvolutionDetector;
import alpha.abft.util.ConvolutionKernel;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
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
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class ABFTv3 extends ABFTv1 {
  public static void main(final String[] args) {
    ABFTv3.insertChecksum("star1d1r", new int[] { 5, 10 });
  }

  public static void insertChecksum(final String systemName, final int[] tileSizes) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(systemName);
    _builder.append(".alpha");
    final AlphaSystem system = ABFTv1.loadSystem(_builder.toString(), systemName);
    ABFTv3.insertChecksum(system, tileSizes);
    ABFTv1.pprint(system, "ABFT augmented system:");
    ABFTv1.save(system);
  }

  public static AlphaSystem insertChecksum(final AlphaSystem system, final int[] tileSizes) {
    return ABFTv3.insertChecksum(system, tileSizes, true);
  }

  public static AlphaSystem insertChecksum(final AlphaSystem system, final int[] tileSizes, final boolean renameSystem) {
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
      Pair<Integer, Integer> _mappedTo = Pair.<Integer, Integer>of(Integer.valueOf(0), Integer.valueOf(H));
      Pair<Integer, Integer> _mappedTo_1 = Pair.<Integer, Integer>of(Integer.valueOf(spaceTileDim), Integer.valueOf(L));
      final ISLSet checksumCDomain = ABFTv3.buildChecksumDomain(outputVar, convolutionKernel, new Pair[] { _mappedTo, _mappedTo_1 });
      Pair<Integer, Integer> _mappedTo_2 = Pair.<Integer, Integer>of(Integer.valueOf(spaceTileDim), Integer.valueOf(L));
      final ISLSet checksumCiDomain = ABFTv3.buildChecksumDomain(outputVar, convolutionKernel, new Pair[] { _mappedTo_2 });
      final ISLSet weightsDomain = ABFTv3.buildWeightsDomainV3(outputVar, convolutionKernel);
      final ISLSet kernelDomain = ABFTv3.buildWeightsDomainV3(outputVar, convolutionKernel, 2);
      final Variable IVar = AlphaUserFactory.createVariable("I", checksumCDomain.copy());
      final Variable WVar = AlphaUserFactory.createVariable("W", weightsDomain.copy());
      final Variable CVar = AlphaUserFactory.createVariable("C1", checksumCDomain.copy());
      final Variable WExtVar = AlphaUserFactory.createVariable("WExt", kernelDomain.copy());
      final Variable WiVar = AlphaUserFactory.createVariable("Wi", kernelDomain.copy());
      final Variable CiVar = AlphaUserFactory.createVariable("C2", checksumCiDomain.copy());
      system.getLocals().addAll(Collections.<Variable>unmodifiableList(CollectionLiterals.<Variable>newArrayList(IVar, WVar, WExtVar, WiVar, CVar, CiVar)));
      ABFTv3.addWeightsEquation(systemBody, WVar, convolutionKernel);
      ABFTv1.addKernelWEquation(systemBody, WExtVar, weightsDomain);
      ABFTv3.addWiEquation(systemBody, WiVar, WExtVar, WVar, spaceTileDim);
      ABFTv3.addCEquation(systemBody, CVar, outputVar, WVar, convolutionKernel, spaceTileDim, H, L);
      ABFTv3.addCiEquation(systemBody, CiVar, outputVar, WVar, WiVar, convolutionKernel, spaceTileDim, H, L);
      ABFTv3.addIEquation(systemBody, IVar, CVar, CiVar, WVar, H);
      ABFTv1.save(system);
      if (renameSystem) {
        ABFTv1.rename(system, new int[] { H, L }, "v3");
      }
      Normalize.apply(system);
      NormalizeReduction.apply(system);
      _xblockexpression = system;
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addCiEquation(final SystemBody systemBody, final Variable CVar, final Variable stencilVar, final Variable WVar, final Variable WiVar, final ConvolutionKernel convolutionKernel, final int spaceTileDim, final int H, final int L) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final CaseExpression ce = AlphaUserFactory.createCaseExpression();
      final ISLSet centerDomain = ABFTv3.createCiCenterDomain(CVar, convolutionKernel, spaceTileDim);
      final BinaryExpression CiBoundaryExpr = ABFTv3.createCiCenterExpr(CVar, WVar, WiVar, stencilVar, spaceTileDim, H, L);
      final ReduceExpression CiStencilReduceExpr = ABFTv3.createCiStencilReduceExpr(CVar, WVar, WiVar, spaceTileDim);
      final BinaryExpression be = AlphaUserFactory.createBinaryExpression(BINARY_OP.ADD, CiBoundaryExpr, CiStencilReduceExpr);
      final RestrictExpression re = AlphaUserFactory.createRestrictExpression(centerDomain, be);
      List<String> _indexNames = CVar.getDomain().getIndexNames();
      String _get = stencilVar.getDomain().getIndexNames().get(spaceTileDim);
      final Iterable<String> contextIndexNames = Iterables.<String>concat(_indexNames, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_get)));
      final DependenceExpression stencilVarExpr = ABFTv3.createStencilVarExpr(stencilVar, ((String[])Conversions.unwrapArray(contextIndexNames, String.class)));
      final ReduceExpression reduceExpr = ABFTv3.createChecksumReduceExpression(CVar, stencilVar, spaceTileDim, H, L, stencilVarExpr);
      final ISLSet boundaryDomain = ABFTv3.createCiBoundaryDomain(CVar, convolutionKernel, spaceTileDim);
      final RestrictExpression re2 = AlphaUserFactory.createRestrictExpression(boundaryDomain, reduceExpr);
      EList<AlphaExpression> _exprs = ce.getExprs();
      _exprs.add(re);
      EList<AlphaExpression> _exprs_1 = ce.getExprs();
      _exprs_1.add(re2);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(CVar, ce);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static BinaryExpression createCiCenterExpr(final Variable CVar, final Variable WVar, final Variable WiVar, final Variable stencilVar, final int spaceTileDim, final int H, final int L) {
    BinaryExpression _xblockexpression = null;
    {
      final List<String> indexNames = CVar.getDomain().getIndexNames();
      final String paramStr = ABFTv1.buildParamStr(CVar);
      final List<String> kernelNames = IterableExtensions.<String>toList(ABFTv3.getKernelNames(WVar.getDomain()));
      BinaryExpression _xblockexpression_1 = null;
      {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(paramStr);
        _builder.append("->{[");
        String _join = IterableExtensions.join(indexNames, ",");
        _builder.append(_join);
        _builder.append("]->[");
        final Function1<String, String> _function = (String n) -> {
          String _xifexpression = null;
          boolean _equals = Objects.equal(n, "t");
          if (_equals) {
            _xifexpression = "t-1";
          } else {
            _xifexpression = n;
          }
          return _xifexpression;
        };
        String _join_1 = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function), ",");
        _builder.append(_join_1);
        _builder.append("]}");
        final ISLMultiAff CMaff = ISLUtil.toISLMultiAff(_builder.toString());
        final DependenceExpression CDepExpr = AlphaUserFactory.createDependenceExpression(CMaff, AlphaUserFactory.createVariableExpression(CVar));
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(paramStr);
        _builder_1.append("->{[");
        String _join_2 = IterableExtensions.join(kernelNames, ",");
        _builder_1.append(_join_2);
        _builder_1.append("]->[");
        final Function1<String, Integer> _function_1 = (String n) -> {
          int _xifexpression = (int) 0;
          boolean _equals = Objects.equal(n, "h");
          if (_equals) {
            _xifexpression = (-1);
          } else {
            _xifexpression = 0;
          }
          return Integer.valueOf(_xifexpression);
        };
        String _join_3 = IterableExtensions.join(ListExtensions.<String, Integer>map(kernelNames, _function_1), ",");
        _builder_1.append(_join_3);
        _builder_1.append("]}");
        final ISLMultiAff WxMaff = ISLUtil.toISLMultiAff(_builder_1.toString());
        final DependenceExpression WiDepExpr = AlphaUserFactory.createDependenceExpression(WxMaff.copy(), AlphaUserFactory.createVariableExpression(WiVar));
        final DependenceExpression WDepExpr = AlphaUserFactory.createDependenceExpression(WxMaff.copy(), AlphaUserFactory.createVariableExpression(WVar));
        final BinaryExpression WBinExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.SUB, WiDepExpr, WDepExpr);
        _xblockexpression_1 = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL, CDepExpr, WBinExpr);
      }
      final BinaryExpression CWExpr = _xblockexpression_1;
      final String pqrName = kernelNames.get(spaceTileDim);
      String _get = kernelNames.get(spaceTileDim);
      final Iterable<String> bodyIndexNames = Iterables.<String>concat(indexNames, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("h", _get)));
      final String bodyStr = IterableExtensions.join(bodyIndexNames, ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      _builder.append(bodyStr);
      _builder.append("]->[");
      String _join = IterableExtensions.join(indexNames, ",");
      _builder.append(_join);
      _builder.append("]}");
      final String projection = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{[");
      _builder_1.append(bodyStr);
      _builder_1.append("]->[");
      final Function1<String, Object> _function = (String n) -> {
        Object _xifexpression = null;
        boolean _contains = Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(spaceTileDim))).contains(Integer.valueOf(kernelNames.indexOf(n)));
        if (_contains) {
          _xifexpression = n;
        } else {
          _xifexpression = Integer.valueOf(0);
        }
        return ((Object)_xifexpression);
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<String, Object>map(kernelNames, _function), ",");
      _builder_1.append(_join_1);
      _builder_1.append("]}");
      final ISLMultiAff WMaff = ISLUtil.toISLMultiAff(_builder_1.toString());
      final DependenceExpression WExpr = AlphaUserFactory.createDependenceExpression(WMaff, AlphaUserFactory.createVariableExpression(WVar));
      final DependenceExpression stencilVarExprL1 = ABFTv3.createStencilVarCiExpr(stencilVar, ((String[])Conversions.unwrapArray(indexNames, String.class)), ((String[])Conversions.unwrapArray(bodyIndexNames, String.class)), ((String[])Conversions.unwrapArray(kernelNames, String.class)), spaceTileDim, L);
      final DependenceExpression stencilVarExprL2 = ABFTv3.createStencilVarCiExpr(stencilVar, ((String[])Conversions.unwrapArray(indexNames, String.class)), ((String[])Conversions.unwrapArray(bodyIndexNames, String.class)), ((String[])Conversions.unwrapArray(kernelNames, String.class)), spaceTileDim, L, L);
      final DependenceExpression stencilVarExprR1 = ABFTv3.createStencilVarCiExpr(stencilVar, ((String[])Conversions.unwrapArray(indexNames, String.class)), ((String[])Conversions.unwrapArray(bodyIndexNames, String.class)), ((String[])Conversions.unwrapArray(kernelNames, String.class)), spaceTileDim, L, (-1));
      final DependenceExpression stencilVarExprR2 = ABFTv3.createStencilVarCiExpr(stencilVar, ((String[])Conversions.unwrapArray(indexNames, String.class)), ((String[])Conversions.unwrapArray(bodyIndexNames, String.class)), ((String[])Conversions.unwrapArray(kernelNames, String.class)), spaceTileDim, L, (L - 1));
      ReduceExpression _xblockexpression_2 = null;
      {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append(paramStr);
        _builder_2.append("->{[");
        String _join_2 = IterableExtensions.join(bodyIndexNames, ",");
        _builder_2.append(_join_2);
        _builder_2.append("] : ");
        _builder_2.append(pqrName);
        _builder_2.append("<0}");
        final ISLSet domain = ISLUtil.toISLSet(_builder_2.toString());
        final BinaryExpression stencilVarDiffExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.SUB, stencilVarExprL1, stencilVarExprL2);
        final BinaryExpression WYBinExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL, AlphaUtil.<DependenceExpression>copyAE(WExpr), stencilVarDiffExpr);
        final RestrictExpression body = AlphaUserFactory.createRestrictExpression(domain, WYBinExpr);
        _xblockexpression_2 = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, ISLUtil.toISLMultiAff(projection), body);
      }
      final ReduceExpression negReduceExpr = _xblockexpression_2;
      ReduceExpression _xblockexpression_3 = null;
      {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append(paramStr);
        _builder_2.append("->{[");
        String _join_2 = IterableExtensions.join(bodyIndexNames, ",");
        _builder_2.append(_join_2);
        _builder_2.append("] : ");
        _builder_2.append(pqrName);
        _builder_2.append(">0}");
        final ISLSet domain = ISLUtil.toISLSet(_builder_2.toString());
        final BinaryExpression stencilVarDiffExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.SUB, stencilVarExprR2, stencilVarExprR1);
        final BinaryExpression WYBinExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL, AlphaUtil.<DependenceExpression>copyAE(WExpr), stencilVarDiffExpr);
        final RestrictExpression body = AlphaUserFactory.createRestrictExpression(domain, WYBinExpr);
        _xblockexpression_3 = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, ISLUtil.toISLMultiAff(projection), body);
      }
      final ReduceExpression posReduceExpr = _xblockexpression_3;
      final BinaryExpression diffReduceExpr = AlphaUserFactory.createBinaryExpression(BINARY_OP.ADD, negReduceExpr, posReduceExpr);
      _xblockexpression = AlphaUserFactory.createBinaryExpression(BINARY_OP.ADD, CWExpr, diffReduceExpr);
    }
    return _xblockexpression;
  }

  public static DependenceExpression createStencilVarCiExpr(final Variable stencilVar, final String[] indexNames, final String[] bodyIndexNames, final String[] kernelNames, final int spaceTileDim, final int L) {
    return ABFTv3.createStencilVarCiExpr(stencilVar, indexNames, bodyIndexNames, kernelNames, spaceTileDim, L, 0);
  }

  public static DependenceExpression createStencilVarCiExpr(final Variable stencilVar, final String[] indexNames, final String[] bodyIndexNames, final String[] kernelNames, final int spaceTileDim, final int L, final int offset) {
    DependenceExpression _xblockexpression = null;
    {
      int _size = ((List<String>)Conversions.doWrapArray(indexNames)).size();
      final Function1<Integer, String> _function = (Integer i) -> {
        String _xblockexpression_1 = null;
        {
          final String cIndexName = indexNames[(i).intValue()];
          final String pqrName = kernelNames[(i).intValue()];
          String _switchResult = null;
          boolean _matched = false;
          if (Objects.equal(i, 0)) {
            _matched=true;
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(cIndexName);
            _builder.append("+");
            _builder.append(pqrName);
            _switchResult = _builder.toString();
          }
          if (!_matched) {
            if (Objects.equal(i, spaceTileDim)) {
              _matched=true;
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(L);
              _builder_1.append(cIndexName);
              _builder_1.append("+");
              String _xifexpression = null;
              if ((offset != 0)) {
                _xifexpression = (Integer.valueOf(offset) + "+");
              }
              _builder_1.append(_xifexpression);
              _builder_1.append(pqrName);
              _switchResult = _builder_1.toString();
            }
          }
          if (!_matched) {
            _switchResult = cIndexName;
          }
          _xblockexpression_1 = _switchResult;
        }
        return _xblockexpression_1;
      };
      final String varAcc = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size, true), _function), ",");
      StringConcatenation _builder = new StringConcatenation();
      String _buildParamStr = ABFTv1.buildParamStr(stencilVar);
      _builder.append(_buildParamStr);
      _builder.append("->{[");
      String _join = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(bodyIndexNames)), ",");
      _builder.append(_join);
      _builder.append("]->[");
      _builder.append(varAcc);
      _builder.append("]}");
      final ISLMultiAff stencilVarMaff = ISLUtil.toISLMultiAff(_builder.toString());
      _xblockexpression = AlphaUserFactory.createDependenceExpression(stencilVarMaff, AlphaUserFactory.createVariableExpression(stencilVar));
    }
    return _xblockexpression;
  }

  public static ReduceExpression createCiStencilReduceExpr(final Variable CVar, final Variable WVar, final Variable WiVar, final int spaceTileDim) {
    ReduceExpression _xblockexpression = null;
    {
      final String paramStr = ABFTv1.buildParamStr(CVar);
      final List<String> indexNames = CVar.getDomain().getIndexNames();
      final List<String> kernelNames = IterableExtensions.<String>toList(ABFTv3.getKernelNames(WVar.getDomain()));
      final Function1<String, Object> _function = (String e) -> {
        Object _xifexpression = null;
        int _indexOf = kernelNames.indexOf(e);
        boolean _equals = (_indexOf == spaceTileDim);
        if (_equals) {
          _xifexpression = Integer.valueOf(0);
        } else {
          _xifexpression = e;
        }
        return ((Object)_xifexpression);
      };
      final List<Object> pqrExprs = ListExtensions.<String, Object>map(kernelNames, _function);
      final String pqrStr = IterableExtensions.join(pqrExprs, ",");
      final Function1<Object, Boolean> _function_1 = (Object e) -> {
        return Boolean.valueOf(Objects.equal(e, Integer.valueOf(0)));
      };
      Iterable<Object> _reject = IterableExtensions.<Object>reject(pqrExprs, _function_1);
      final String domainStr = IterableExtensions.join(Iterables.<Object>concat(indexNames, _reject), ",");
      final String rangeStr = IterableExtensions.join(CVar.getDomain().getIndexNames(), ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      _builder.append(domainStr);
      _builder.append("]->[");
      _builder.append(rangeStr);
      _builder.append("]}");
      final String projection = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(paramStr);
      _builder_1.append("->{[");
      _builder_1.append(domainStr);
      _builder_1.append("]->[");
      _builder_1.append(pqrStr);
      _builder_1.append("]}");
      final ISLMultiAff WMaff = ISLUtil.toISLMultiAff(_builder_1.toString());
      final DependenceExpression WDepExpr = AlphaUserFactory.createDependenceExpression(WMaff, AlphaUserFactory.createVariableExpression(WVar));
      final Function1<Pair<String, Object>, String> _function_2 = (Pair<String, Object> it) -> {
        String _xblockexpression_1 = null;
        {
          final String indexName = it.getKey();
          final Object pqrExpr = ((Object)it.getValue());
          String _xifexpression = null;
          int _indexOf = indexNames.indexOf(indexName);
          boolean _equals = (_indexOf == spaceTileDim);
          if (_equals) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append(indexName);
            _xifexpression = _builder_2.toString();
          } else {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append(indexName);
            _builder_3.append("+");
            _builder_3.append(((Object)pqrExpr));
            _xifexpression = _builder_3.toString();
          }
          _xblockexpression_1 = _xifexpression;
        }
        return _xblockexpression_1;
      };
      final String CiAcc = IterableExtensions.join(ListExtensions.<Pair<String, Object>, String>map(CommonExtensions.<String, Object>zipWith(indexNames, pqrExprs), _function_2), ",");
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(paramStr);
      _builder_2.append("->{[");
      _builder_2.append(domainStr);
      _builder_2.append("]->[");
      _builder_2.append(CiAcc);
      _builder_2.append("]}");
      final ISLMultiAff CiMaff = ISLUtil.toISLMultiAff(_builder_2.toString());
      final DependenceExpression CiDepExpr = AlphaUserFactory.createDependenceExpression(CiMaff, AlphaUserFactory.createVariableExpression(CVar));
      final BinaryExpression body = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL, WDepExpr, CiDepExpr);
      _xblockexpression = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, ISLUtil.toISLMultiAff(projection), body);
    }
    return _xblockexpression;
  }

  public static ISLSet createCiBoundaryDomain(final Variable CVar, final ConvolutionKernel convolutionKernel, final int spaceTileDim) {
    ISLSet _xblockexpression = null;
    {
      final ISLSet universe = ISLSet.buildUniverse(CVar.getDomain().getSpace().copy());
      final String centerDomainStr = ABFTv3.createCiCenterDomain(CVar, convolutionKernel, spaceTileDim).toString();
      final ISLSet centerDomain = ISLSet.buildFromString(ISLContext.getInstance(), centerDomainStr);
      final String paramStr = ABFTv1.buildParamStr(CVar);
      final List<String> indexNames = CVar.getDomain().getIndexNames();
      final Function1<ISLBasicSet, List<ISLConstraint>> _function = (ISLBasicSet it) -> {
        return it.getConstraints();
      };
      final Function1<ISLConstraint, ISLAff> _function_1 = (ISLConstraint it) -> {
        return it.getAff();
      };
      final Function1<ISLAff, ISLAff> _function_2 = (ISLAff it) -> {
        return it.negate();
      };
      final Function1<ISLAff, String> _function_3 = (ISLAff aff) -> {
        String _string = aff.toString(ISL_FORMAT.C);
        return (_string + " > 0");
      };
      final String constraints = IterableExtensions.join(IterableExtensions.<ISLAff, String>map(IterableExtensions.<ISLAff, ISLAff>map(IterableExtensions.<ISLConstraint, ISLAff>map(IterableExtensions.<ISLBasicSet, ISLConstraint>flatMap(centerDomain.copy().getBasicSets(), _function), _function_1), _function_2), _function_3), " or ");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      String _join = IterableExtensions.join(indexNames, ",");
      _builder.append(_join);
      _builder.append("] : ");
      _builder.append(constraints);
      _builder.append(" }");
      _xblockexpression = ISLUtil.toISLSet(_builder.toString());
    }
    return _xblockexpression;
  }

  public static ISLSet createCiCenterDomain(final Variable CVar, final ConvolutionKernel convolutionKernel, final int spaceTileDim) {
    ISLSet _xblockexpression = null;
    {
      final int nbDims = CVar.getDomain().dim(ISLDimType.isl_dim_out);
      final ISLVal radius = ISLVal.buildRationalValue(ISLContext.getInstance(), (convolutionKernel.radius()).longValue(), 1);
      final ISLVal timeDepth = ISLVal.buildRationalValue(ISLContext.getInstance(), (convolutionKernel.timeDepth()).longValue(), 1);
      final ISLVal oneVal = ISLVal.buildRationalValue(ISLContext.getInstance(), 1, 1);
      final Function1<ISLConstraint, Boolean> _function = (ISLConstraint c) -> {
        return Boolean.valueOf(c.involvesDims(ISLDimType.isl_dim_out, 0, 1));
      };
      final Function1<ISLConstraint, Boolean> involvesTime = _function;
      final Function2<ISLConstraint, Integer, Boolean> _function_1 = (ISLConstraint c, Integer dim) -> {
        return Boolean.valueOf(c.isLowerBound(ISLDimType.isl_dim_out, dim));
      };
      final Function2<ISLConstraint, Integer, Boolean> isLB = _function_1;
      final Function2<ISLConstraint, Integer, Boolean> _function_2 = (ISLConstraint c, Integer dim) -> {
        return Boolean.valueOf(c.isUpperBound(ISLDimType.isl_dim_out, dim));
      };
      final Function2<ISLConstraint, Integer, Boolean> isUB = _function_2;
      final Function1<ISLBasicSet, ISLSet> _function_3 = (ISLBasicSet it) -> {
        ISLSet _xblockexpression_1 = null;
        {
          final Function1<ISLConstraint, Boolean> _function_4 = (ISLConstraint c) -> {
            return Boolean.valueOf(((involvesTime.apply(c)).booleanValue() && (isLB.apply(c, Integer.valueOf(0))).booleanValue()));
          };
          final Function1<ISLConstraint, ISLConstraint> _function_5 = (ISLConstraint c) -> {
            ISLConstraint _xblockexpression_2 = null;
            {
              final ISLVal coeff = c.getCoefficientVal(ISLDimType.isl_dim_out, 0);
              final ISLVal const_ = c.getConstantVal();
              _xblockexpression_2 = c.setConstant(const_.sub(coeff.mul(timeDepth.copy())));
            }
            return _xblockexpression_2;
          };
          final ArrayList<ISLConstraint> timeConstraints = CommonExtensions.<ISLConstraint>toArrayList(IterableExtensions.<ISLConstraint, ISLConstraint>map(IterableExtensions.<ISLConstraint>filter(it.getConstraints(), _function_4), _function_5));
          final Function1<ISLConstraint, Boolean> _function_6 = (ISLConstraint c) -> {
            return involvesTime.apply(c);
          };
          final Function1<ISLConstraint, ISLConstraint> _function_7 = (ISLConstraint c) -> {
            ISLConstraint _xblockexpression_2 = null;
            {
              final ISLVal const_ = c.getConstantVal();
              _xblockexpression_2 = c.setConstant(const_.sub(radius.copy()).sub(oneVal.copy()));
            }
            return _xblockexpression_2;
          };
          final ArrayList<ISLConstraint> spaceConstraints = CommonExtensions.<ISLConstraint>toArrayList(IterableExtensions.<ISLConstraint, ISLConstraint>map(IterableExtensions.<ISLConstraint>reject(it.getConstraints(), _function_6), _function_7));
          final Function1<ISLConstraint, ISLBasicSet> _function_8 = (ISLConstraint it_1) -> {
            return it_1.toBasicSet();
          };
          final Function2<ISLBasicSet, ISLBasicSet, ISLBasicSet> _function_9 = (ISLBasicSet b1, ISLBasicSet b2) -> {
            return b1.intersect(b2);
          };
          _xblockexpression_1 = IterableExtensions.<ISLBasicSet>reduce(IterableExtensions.<ISLConstraint, ISLBasicSet>map(Iterables.<ISLConstraint>concat(timeConstraints, spaceConstraints), _function_8), _function_9).dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, nbDims).toSet();
        }
        return _xblockexpression_1;
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_4 = (ISLSet s1, ISLSet s2) -> {
        return s1.union(s2);
      };
      final ISLSet domain = IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLBasicSet, ISLSet>map(CVar.getDomain().getBasicSets(), _function_3), _function_4).dropConstraintsInvolvingDims(ISLDimType.isl_dim_out, spaceTileDim, 1);
      _xblockexpression = domain;
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addWiEquation(final SystemBody systemBody, final Variable WiVar, final Variable WExtVar, final Variable WVar, final int spaceTileDim) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final String paramStr = ABFTv1.buildParamStr(WiVar);
      final List<String> indexNames = WiVar.getDomain().getIndexNames();
      final String pqrName = ((String[])Conversions.unwrapArray(ABFTv3.getKernelNames(WiVar.getDomain()), String.class))[spaceTileDim];
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

  public static Iterable<String> getKernelNames(final ISLSet kernelDomain) {
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

  public static List<AlphaIssue> addCEquation(final SystemBody systemBody, final Variable CVar, final Variable stencilVar, final Variable WVar, final ConvolutionKernel convolutionKernel, final int spaceTileDim, final int H, final int L) {
    List<AlphaIssue> _xblockexpression = null;
    {
      List<String> _indexNames = CVar.getDomain().getIndexNames();
      String _get = stencilVar.getDomain().getIndexNames().get(spaceTileDim);
      final Iterable<String> contextIndexNames = Iterables.<String>concat(_indexNames, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_get)));
      final DependenceExpression stencilVarExpr = ABFTv3.createStencilVarExpr(stencilVar, ((String[])Conversions.unwrapArray(contextIndexNames, String.class)), H);
      final ReduceExpression reduceExpr = ABFTv3.createChecksumReduceExpression(CVar, stencilVar, spaceTileDim, H, L, stencilVarExpr);
      final StandardEquation equ = AlphaUserFactory.createStandardEquation(CVar, reduceExpr);
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(equ);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(equ);
    }
    return _xblockexpression;
  }

  public static DependenceExpression createStencilVarExpr(final Variable stencilVar, final String[] contextIndexNames) {
    DependenceExpression _xblockexpression = null;
    {
      final List<String> stencilIndexNames = stencilVar.getDomain().getIndexNames();
      String _get = contextIndexNames[0];
      int _size = stencilIndexNames.size();
      final Function1<Integer, String> _function = (Integer it) -> {
        return stencilIndexNames.get((it).intValue());
      };
      Iterable<String> _map = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
      final String varAcc = IterableExtensions.join(Iterables.<String>concat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_get)), _map), ",");
      StringConcatenation _builder = new StringConcatenation();
      String _buildParamStr = ABFTv1.buildParamStr(stencilVar);
      _builder.append(_buildParamStr);
      _builder.append("->{[");
      String _join = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(contextIndexNames)), ",");
      _builder.append(_join);
      _builder.append("]->[");
      _builder.append(varAcc);
      _builder.append("]}");
      final String stencilVarMaff = _builder.toString();
      _xblockexpression = AlphaUserFactory.createDependenceExpression(ISLUtil.toISLMultiAff(stencilVarMaff), AlphaUserFactory.createVariableExpression(stencilVar));
    }
    return _xblockexpression;
  }

  public static DependenceExpression createStencilVarExpr(final Variable stencilVar, final String[] contextIndexNames, final int H) {
    DependenceExpression _xblockexpression = null;
    {
      final List<String> stencilIndexNames = stencilVar.getDomain().getIndexNames();
      String _get = contextIndexNames[0];
      String _plus = (Integer.valueOf(H) + _get);
      int _size = stencilIndexNames.size();
      final Function1<Integer, String> _function = (Integer it) -> {
        return stencilIndexNames.get((it).intValue());
      };
      Iterable<String> _map = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
      final String varAcc = IterableExtensions.join(Iterables.<String>concat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_plus)), _map), ",");
      StringConcatenation _builder = new StringConcatenation();
      String _buildParamStr = ABFTv1.buildParamStr(stencilVar);
      _builder.append(_buildParamStr);
      _builder.append("->{[");
      String _join = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(contextIndexNames)), ",");
      _builder.append(_join);
      _builder.append("]->[");
      _builder.append(varAcc);
      _builder.append("]}");
      final ISLMultiAff stencilVarMaff = ISLUtil.toISLMultiAff(_builder.toString());
      _xblockexpression = AlphaUserFactory.createDependenceExpression(stencilVarMaff, AlphaUserFactory.createVariableExpression(stencilVar));
    }
    return _xblockexpression;
  }

  public static ReduceExpression createChecksumReduceExpression(final Variable CVar, final Variable stencilVar, final int spaceTileDim, final int H, final int L, final AlphaExpression stencilVarExpr) {
    ReduceExpression _xblockexpression = null;
    {
      final List<String> CIndexNames = CVar.getDomain().getIndexNames();
      final List<String> stencilIndexNames = stencilVar.getDomain().getIndexNames();
      final String paramStr = ABFTv1.buildParamStr(CVar);
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
      final RestrictExpression restrictExpr = AlphaUserFactory.createRestrictExpression(bodyDom);
      restrictExpr.setExpr(stencilVarExpr);
      _xblockexpression = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, projection, restrictExpr);
    }
    return _xblockexpression;
  }

  public static List<AlphaIssue> addIEquation(final SystemBody systemBody, final Variable IVar, final Variable C1Var, final Variable C2Var, final Variable WVar, final int H) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final List<String> indexNames = IVar.getDomain().getIndexNames();
      int _size = indexNames.size();
      final Function1<Integer, String> _function = (Integer i) -> {
        return indexNames.get((i).intValue());
      };
      final String spaceIdxStr = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function), ",");
      final String paramStr = ABFTv1.buildParamStr(C2Var);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[tt,");
      _builder.append(spaceIdxStr);
      _builder.append("]->[");
      _builder.append(H);
      _builder.append("tt,");
      _builder.append(spaceIdxStr);
      _builder.append("]}");
      final ISLMultiAff C2Maff = ISLUtil.toISLMultiAff(_builder.toString());
      final DependenceExpression C2Expr = AlphaUserFactory.createDependenceExpression(C2Maff, AlphaUserFactory.createVariableExpression(C2Var));
      _xblockexpression = ABFTv1.addIEquation(systemBody, IVar, C1Var, C2Var, WVar, C2Expr);
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
          _xblockexpression_1 = ABFTv3.createWeightRestrictBranchV3(space, ((String[])Conversions.unwrapArray(indexNames, String.class)), ((Integer[])Conversions.unwrapArray(indexValues, Integer.class)), coeff.floatValue());
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
      ABFTv1.createRealDepExpr(space, coeff);
      final String paramStr = ABFTv1.buildParamStr(space);
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
      re.setExpr(ABFTv1.createRealDepExpr(space, coeff));
      _xblockexpression = re;
    }
    return _xblockexpression;
  }

  public static ISLSet buildWeightsDomainV3(final Variable variable, final ConvolutionKernel convolutionKernel) {
    return ABFTv3.buildWeightsDomainV3(variable, convolutionKernel, 1);
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
      String _buildParamStr = ABFTv1.buildParamStr(variable);
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

  public static ISLSet buildChecksumDomain(final Variable variable, final ConvolutionKernel convolutionKernel, final Pair<Integer, Integer>[] tileSizePairs) {
    ISLSet _xblockexpression = null;
    {
      final ISLSet domain = variable.getDomain().copy().intersect(convolutionKernel.domain().copy());
      final Function1<Pair<Integer, Integer>, Integer> _function = (Pair<Integer, Integer> it) -> {
        return it.getValue();
      };
      final Function1<Integer, ISLVal> _function_1 = (Integer s) -> {
        return ISLVal.buildRationalValue(ISLContext.getInstance(), (s).intValue(), 1);
      };
      final List<ISLVal> sizeVals = ListExtensions.<Integer, ISLVal>map(ListExtensions.<Pair<Integer, Integer>, Integer>map(((List<Pair<Integer, Integer>>)Conversions.doWrapArray(tileSizePairs)), _function), _function_1);
      int _size = domain.getIndexNames().size();
      final Function1<Integer, String> _function_2 = (Integer i) -> {
        String _xblockexpression_1 = null;
        {
          final String name = domain.getIndexNames().get((i).intValue());
          String _xifexpression = null;
          final Function1<Pair<Integer, Integer>, Integer> _function_3 = (Pair<Integer, Integer> it) -> {
            return it.getKey();
          };
          boolean _contains = ListExtensions.<Pair<Integer, Integer>, Integer>map(((List<Pair<Integer, Integer>>)Conversions.doWrapArray(tileSizePairs)), _function_3).contains(i);
          if (_contains) {
            _xifexpression = ("t" + name);
          } else {
            _xifexpression = domain.getIndexNames().get((i).intValue());
          }
          _xblockexpression_1 = _xifexpression;
        }
        return _xblockexpression_1;
      };
      final List<String> indexNames = IterableExtensions.<String>toList(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size, true), _function_2));
      final Function2<ISLConstraint, Integer, Boolean> _function_3 = (ISLConstraint c, Integer i) -> {
        return Boolean.valueOf(c.involvesDims(ISLDimType.isl_dim_out, i, 1));
      };
      final Function2<ISLConstraint, Integer, Boolean> involvesDim = _function_3;
      final Function1<ISLConstraint, Boolean> _function_4 = (ISLConstraint c) -> {
        final Function1<Pair<Integer, Integer>, Integer> _function_5 = (Pair<Integer, Integer> it) -> {
          return it.getKey();
        };
        final Function1<Integer, Boolean> _function_6 = (Integer i) -> {
          return involvesDim.apply(c, i);
        };
        final Function2<Boolean, Boolean, Boolean> _function_7 = (Boolean v1, Boolean v2) -> {
          return Boolean.valueOf(((v1).booleanValue() || (v2).booleanValue()));
        };
        return IterableExtensions.<Boolean>reduce(ListExtensions.<Integer, Boolean>map(ListExtensions.<Pair<Integer, Integer>, Integer>map(((List<Pair<Integer, Integer>>)Conversions.doWrapArray(tileSizePairs)), _function_5), _function_6), _function_7);
      };
      final Function1<ISLConstraint, Boolean> involvesAny = _function_4;
      final Function1<ISLBasicSet, ISLSet> _function_5 = (ISLBasicSet bs) -> {
        ISLSet _xblockexpression_1 = null;
        {
          final Function1<Pair<Integer, Integer>, Iterable<ISLConstraint>> _function_6 = (Pair<Integer, Integer> p) -> {
            Iterable<ISLConstraint> _xblockexpression_2 = null;
            {
              final Integer tileDim = p.getKey();
              final ISLVal tileSize = ISLVal.buildRationalValue(ISLContext.getInstance(), (p.getValue()).intValue(), 1);
              final Function1<ISLConstraint, Boolean> _function_7 = (ISLConstraint c) -> {
                return involvesDim.apply(c, tileDim);
              };
              final Function1<ISLConstraint, ISLConstraint> _function_8 = (ISLConstraint c) -> {
                ISLConstraint _xblockexpression_3 = null;
                {
                  final ISLVal coeffVal = c.getCoefficientVal(ISLDimType.isl_dim_out, (tileDim).intValue());
                  final ISLConstraint tc = c.copy().setCoefficient(ISLDimType.isl_dim_out, (tileDim).intValue(), coeffVal.mul(tileSize.copy()));
                  boolean _isUpperBound = c.isUpperBound(ISLDimType.isl_dim_out, (tileDim).intValue());
                  boolean _not = (!_isUpperBound);
                  if (_not) {
                    return tc;
                  }
                  final ISLVal const_ = tc.getConstantVal();
                  _xblockexpression_3 = tc.setConstant(const_.sub(tileSize.copy()));
                }
                return _xblockexpression_3;
              };
              _xblockexpression_2 = IterableExtensions.<ISLConstraint, ISLConstraint>map(IterableExtensions.<ISLConstraint>filter(bs.getConstraints(), _function_7), _function_8);
            }
            return _xblockexpression_2;
          };
          final ArrayList<ISLConstraint> tiledConstraints = CommonExtensions.<ISLConstraint>toArrayList(Iterables.<ISLConstraint>concat(ListExtensions.<Pair<Integer, Integer>, Iterable<ISLConstraint>>map(((List<Pair<Integer, Integer>>)Conversions.doWrapArray(tileSizePairs)), _function_6)));
          final Function1<ISLConstraint, Boolean> _function_7 = (ISLConstraint c) -> {
            return involvesAny.apply(c);
          };
          final ArrayList<ISLConstraint> remainingConstraints = CommonExtensions.<ISLConstraint>toArrayList(IterableExtensions.<ISLConstraint>reject(bs.getConstraints(), _function_7));
          final Function1<ISLConstraint, ISLBasicSet> _function_8 = (ISLConstraint it) -> {
            return it.toBasicSet();
          };
          final Function2<ISLBasicSet, ISLBasicSet, ISLBasicSet> _function_9 = (ISLBasicSet b1, ISLBasicSet b2) -> {
            return b1.intersect(b2);
          };
          _xblockexpression_1 = IterableExtensions.<ISLBasicSet>reduce(IterableExtensions.<ISLConstraint, ISLBasicSet>map(Iterables.<ISLConstraint>concat(tiledConstraints, remainingConstraints), _function_8), _function_9).toSet();
        }
        return _xblockexpression_1;
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_6 = (ISLSet s1, ISLSet s2) -> {
        return s1.union(s2);
      };
      final ISLSet tiledDomain = IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLBasicSet, ISLSet>map(domain.getBasicSets(), _function_5), _function_6);
      _xblockexpression = AlphaUtil.renameIndices(tiledDomain, indexNames);
    }
    return _xblockexpression;
  }
}
