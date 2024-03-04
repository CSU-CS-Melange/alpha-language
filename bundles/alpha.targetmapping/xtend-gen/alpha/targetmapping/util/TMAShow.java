package alpha.targetmapping.util;

import alpha.model.AlphaScheduleTarget;
import alpha.model.StandardEquation;
import alpha.model.UseEquation;
import alpha.model.util.AlphaPrintingUtil;
import alpha.targetmapping.AbstractBandExpression;
import alpha.targetmapping.BandExpression;
import alpha.targetmapping.BandPiece;
import alpha.targetmapping.ContextExpression;
import alpha.targetmapping.ExtensionExpression;
import alpha.targetmapping.ExtensionTarget;
import alpha.targetmapping.FilterExpression;
import alpha.targetmapping.GuardExpression;
import alpha.targetmapping.IsolateSpecification;
import alpha.targetmapping.JNIDomainCalculatorForTM;
import alpha.targetmapping.ScheduleTargetRestrictDomain;
import alpha.targetmapping.TargetMapping;
import alpha.targetmapping.TargetMappingForSystemBody;
import alpha.targetmapping.TargetMappingNode;
import alpha.targetmapping.TileBandExpression;
import alpha.targetmapping.TilingSpecification;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Prints TargetMapping in array notation.
 * 
 * The array notation names all dimensions (schedule + schedule target).
 * The switch is modified to keep track of the context information,
 * similar to how it is done in {@link JNIDomainCalculatorForTM},
 * and adds default dimension names when they were not given in the
 * original TargetMapping.
 */
@SuppressWarnings("all")
public class TMAShow extends TMShow {
  protected Map<AlphaScheduleTarget, Stack<List<String>>> indexNameContexts;

  protected Stack<List<String>> scheduleDimNameContextHistory = new Stack<List<String>>();

  protected Stack<Integer> bandSizeHistory = new Stack<Integer>();

  private final TargetMappingNode haltTarget;

  private TMAShow() {
    this.haltTarget = null;
  }

  private TMAShow(final TargetMappingNode target) {
    this.haltTarget = target;
  }

  public static <T extends TargetMappingNode> String print(final T tmn) {
    String _xblockexpression = null;
    {
      final TMAShow ashow = new TMAShow();
      ashow.parameterContext = TargetMappingUtil.getTargetSystem(tmn).getParameterDomain();
      if ((!((tmn instanceof TargetMapping) || (tmn instanceof TargetMappingForSystemBody)))) {
        final TMAShow contextCollector = new TMAShow(tmn);
        contextCollector.doSwitch(TargetMappingUtil.getContainerTM(tmn));
        ashow.indexNameContexts = contextCollector.indexNameContexts;
        ashow.scheduleDimNameContextHistory = contextCollector.scheduleDimNameContextHistory;
        ashow.bandSizeHistory = contextCollector.bandSizeHistory;
      }
      _xblockexpression = ashow.doSwitch(tmn).toString();
    }
    return _xblockexpression;
  }

  @Override
  public CharSequence doSwitch(final EObject obj) {
    CharSequence _xifexpression = null;
    if (((this.haltTarget != null) && (this.haltTarget == obj))) {
      StringConcatenation _builder = new StringConcatenation();
      _xifexpression = _builder;
    } else {
      _xifexpression = super.doSwitch(obj);
    }
    return _xifexpression;
  }

  @Override
  public String printDomain(final ContextExpression ce) {
    return AlphaPrintingUtil.toAShowString(ce.getContextDomain(), this.parameterContext, this.scheduleDimNameContextHistory.peek());
  }

  @Override
  public String printDomain(final ScheduleTargetRestrictDomain strd) {
    String _xifexpression = null;
    boolean _isEmpty = strd.getRestrictDomain().complement().isEmpty();
    if (_isEmpty) {
      StringConcatenation _builder = new StringConcatenation();
      _xifexpression = _builder.toString();
    } else {
      String _aShowString = AlphaPrintingUtil.toAShowString(strd.getRestrictDomain(), this.parameterContext, this.indexNameContexts.get(strd.getScheduleTarget()).peek());
      _xifexpression = (" " + _aShowString);
    }
    return _xifexpression;
  }

  @Override
  public String printDomain(final GuardExpression ge) {
    return AlphaPrintingUtil.toAShowString(ge.getGuardDomain(), this.parameterContext, this.scheduleDimNameContextHistory.peek());
  }

  @Override
  public String printDomain(final IsolateSpecification is) {
    return AlphaPrintingUtil.toAShowString(is.getIsolateDomain(), this.parameterContext, this.scheduleDimNameContextHistory.peek());
  }

  @Override
  public String printSchedule(final BandPiece bp) {
    return AlphaPrintingUtil.toAShowString(bp.getPartialSchedule(), this.indexNameContexts.get(bp.getPieceDomain().getScheduleTarget()).peek());
  }

  @Override
  public CharSequence printSchedule(final TilingSpecification ts) {
    CharSequence _xifexpression = null;
    boolean _isIdentity = ts.getLoopSchedule().isIdentity();
    if (_isIdentity) {
      StringConcatenation _builder = new StringConcatenation();
      _xifexpression = _builder;
    } else {
      _xifexpression = AlphaPrintingUtil.toAShowString(ts.getLoopSchedule(), this.scheduleDimNameContextHistory.peek());
    }
    return _xifexpression;
  }

  @Override
  public String printExtensionMap(final ISLMap map) {
    return AlphaPrintingUtil.toShowString(map);
  }

  @Override
  public CharSequence printDimNames(final ScheduleTargetRestrictDomain strd) {
    CharSequence _xblockexpression = null;
    {
      final Stack<List<String>> history = this.indexNameContexts.get(strd.getScheduleTarget());
      int _size = history.size();
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        final List<String> top = history.pop();
        final List<String> topm1 = history.peek();
        history.push(top);
        boolean _equals = top.equals(topm1);
        if (_equals) {
          StringConcatenation _builder = new StringConcatenation();
          return _builder;
        }
      }
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("[");
      String _join = IterableExtensions.join(this.indexNameContexts.get(strd.getScheduleTarget()).peek(), ",");
      _builder_1.append(_join);
      _builder_1.append("]");
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }

  @Override
  public CharSequence printDimNames(final BandExpression be) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("[");
    String _join = IterableExtensions.join(this.getLocalContext(be.bandSize()), ",");
    _builder.append(_join);
    _builder.append("]");
    return _builder;
  }

  @Override
  public CharSequence printDimNames(final TileBandExpression tbe) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("[");
    String _join = IterableExtensions.join(this.getLocalContext(tbe.bandSize()), ",");
    _builder.append(_join);
    _builder.append("]");
    return _builder;
  }

  @Override
  public CharSequence printDimNames(final ExtensionTarget et) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("[");
    String _join = IterableExtensions.join(this.indexNameContexts.get(et).peek(), ",");
    _builder.append(_join);
    _builder.append("]");
    return _builder;
  }

  /**
   * Schedule dim names are successively declared in case of nested band expressions.
   */
  private List<String> getLocalContext(final int currentBandSize) {
    List<String> _xblockexpression = null;
    {
      final List<String> globalContext = this.scheduleDimNameContextHistory.peek();
      int _size = globalContext.size();
      int _minus = (_size - currentBandSize);
      _xblockexpression = globalContext.subList(_minus, globalContext.size());
    }
    return _xblockexpression;
  }

  @Override
  public CharSequence caseTargetMappingForSystemBody(final TargetMappingForSystemBody object) {
    CharSequence _xblockexpression = null;
    {
      HashMap<AlphaScheduleTarget, Stack<List<String>>> _hashMap = new HashMap<AlphaScheduleTarget, Stack<List<String>>>();
      this.indexNameContexts = _hashMap;
      ArrayList<String> _arrayList = new ArrayList<String>();
      this.scheduleDimNameContextHistory.push(_arrayList);
      this.bandSizeHistory.push(Integer.valueOf(0));
      List<AlphaScheduleTarget> _allContentsOfType = EcoreUtil2.<AlphaScheduleTarget>getAllContentsOfType(object.getTargetBody(), AlphaScheduleTarget.class);
      for (final AlphaScheduleTarget target : _allContentsOfType) {
        {
          final Stack<List<String>> stack = new Stack<List<String>>();
          final List<String> context = this.findIndexNames(target);
          stack.push(context);
          this.indexNameContexts.put(target, stack);
        }
      }
      _xblockexpression = super.caseTargetMappingForSystemBody(object);
    }
    return _xblockexpression;
  }

  @Override
  public CharSequence caseFilterExpression(final FilterExpression object) {
    EList<ScheduleTargetRestrictDomain> _filterDomains = object.getFilterDomains();
    for (final ScheduleTargetRestrictDomain fd : _filterDomains) {
      this.indexNameContexts.get(fd.getScheduleTarget()).push(fd.getRestrictDomain().getIndexNames());
    }
    final CharSequence res = super.caseFilterExpression(object);
    EList<ScheduleTargetRestrictDomain> _filterDomains_1 = object.getFilterDomains();
    for (final ScheduleTargetRestrictDomain fd_1 : _filterDomains_1) {
      this.indexNameContexts.get(fd_1.getScheduleTarget()).pop();
    }
    return res;
  }

  @Override
  public CharSequence caseBandExpression(final BandExpression object) {
    this.registerScheduleDimNames(object);
    final CharSequence res = super.caseBandExpression(object);
    this.bandSizeHistory.pop();
    this.scheduleDimNameContextHistory.pop();
    return res;
  }

  @Override
  public CharSequence caseTileBandExpression(final TileBandExpression object) {
    this.registerScheduleDimNames(object);
    final CharSequence res = super.caseTileBandExpression(object);
    this.bandSizeHistory.pop();
    this.scheduleDimNameContextHistory.pop();
    return res;
  }

  private Integer registerScheduleDimNames(final AbstractBandExpression abe) {
    Integer _xblockexpression = null;
    {
      final Function2<Integer, Integer, Integer> _function = (Integer p1, Integer p2) -> {
        return Integer.valueOf(((p1).intValue() + (p2).intValue()));
      };
      final Integer priorBands = IterableExtensions.<Integer>reduce(this.bandSizeHistory, _function);
      final List<String> additionalDims = this.findScheduleDimNames(abe, (priorBands).intValue());
      final List<String> prevContext = this.scheduleDimNameContextHistory.peek();
      final ArrayList<String> newContext = new ArrayList<String>(prevContext);
      newContext.addAll(additionalDims);
      this.scheduleDimNameContextHistory.push(newContext);
      _xblockexpression = this.bandSizeHistory.push(Integer.valueOf(abe.bandSize()));
    }
    return _xblockexpression;
  }

  @Override
  public CharSequence caseExtensionExpression(final ExtensionExpression object) {
    EList<ExtensionTarget> _extensionTargets = object.getExtensionTargets();
    for (final ExtensionTarget et : _extensionTargets) {
      {
        final Stack<List<String>> contexts = new Stack<List<String>>();
        int _size = et.getIndexNames().size();
        int _nbOutputs = et.getExtensionMap().getNbOutputs();
        boolean _equals = (_size == _nbOutputs);
        if (_equals) {
          contexts.add(et.getIndexNames());
        } else {
          contexts.add(null);
        }
        this.indexNameContexts.put(et, contexts);
      }
    }
    final CharSequence res = super.caseExtensionExpression(object);
    EList<ExtensionTarget> _extensionTargets_1 = object.getExtensionTargets();
    for (final ExtensionTarget et_1 : _extensionTargets_1) {
      this.indexNameContexts.remove(et_1);
    }
    return res;
  }

  /**
   * Helper to find index names
   */
  private List<String> _findIndexNames(final AlphaScheduleTarget target) {
    return null;
  }

  private List<String> _findIndexNames(final StandardEquation eq) {
    List<String> _xifexpression = null;
    if ((eq.getIndexNames().isEmpty() && (eq.getVariable().getDomain().getNbIndices() > 0))) {
      _xifexpression = eq.getVariable().getDomain().getIndexNames();
    } else {
      _xifexpression = eq.getIndexNames();
    }
    return _xifexpression;
  }

  private List<String> _findIndexNames(final UseEquation eq) {
    return eq.getInstantiationDomain().getIndexNames();
  }

  private List<String> findScheduleDimNames(final AbstractBandExpression abe, final int priorBands) {
    List<String> _xblockexpression = null;
    {
      final int bandSize = abe.bandSize();
      List<String> _xifexpression = null;
      int _bandSize = abe.bandSize();
      int _size = abe.getScheduleDimensionNames().size();
      boolean _equals = (_bandSize == _size);
      if (_equals) {
        _xifexpression = abe.getScheduleDimensionNames();
      } else {
        final Function1<Integer, String> _function = (Integer i) -> {
          return TargetMappingUtil.DEFAULT_SCHEDULE_DIMENSION_NAME_PROVIDER.apply(i);
        };
        _xifexpression = IterableExtensions.<String>toList(IterableExtensions.<Integer, String>map(new ExclusiveRange(priorBands, (priorBands + bandSize), true), _function));
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  private List<String> findIndexNames(final AlphaScheduleTarget eq) {
    if (eq instanceof StandardEquation) {
      return _findIndexNames((StandardEquation)eq);
    } else if (eq instanceof UseEquation) {
      return _findIndexNames((UseEquation)eq);
    } else if (eq != null) {
      return _findIndexNames(eq);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(eq).toString());
    }
  }
}
