package alpha.targetmapping;

import alpha.model.AlphaScheduleTarget;
import alpha.model.StandardEquation;
import alpha.model.UseEquation;
import alpha.targetmapping.TargetmappingPackage;
import alpha.targetmapping.util.AbstractTargetMappingVisitor;
import alpha.targetmapping.util.TargetMappingUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.IISLSingleSpaceMapMethods;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLIdentifier;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiPWAff;
import fr.irisa.cairn.jnimap.isl.ISLMultiUnionPWAff;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLScheduleBandNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleExtensionNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleFilterNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleGuardNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleMarkNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleSequenceNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleSetNode;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionSetList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * ConstructISLScheduleTree is responsible for translating {@link ScheduleTreeExpression}
 * to ISLSchedule, i.e., schedule tree in ISL. The translation assumes that the
 * input TargetMapping is valid: all JNIDomains are successfully computed, and
 * syntactic restrictions (e.g., placement of ExtensionExpression) are respected.
 * 
 * Because the ISL implementation is only using structs (no classes) manipulating the
 * tree is a bit confusing. ISLSchedule and ISLScheduleNode objects represent the
 * tree under construction in a particular state, and the reference is meaningless as
 * soon as an operation is performed. (isl take/keep applies as usual)
 * 
 * A sub-tree may be constructed in a top-down manner for the most part, but
 * accessing other parts of the tree requires traversing the tree. Consistency between
 * the TargetMapping structure and ISLSchedule is maintained using the path vector,
 * which keeps track of where you are in the ISLSchedule.
 */
@SuppressWarnings("all")
public class ConstructISLScheduleTree extends AbstractTargetMappingVisitor {
  private ConstructISLScheduleTree() {
  }

  private ISLSchedule schedule;

  private List<Integer> path;

  /**
   * This visitor maintains an up-to-date copy of ISLSchedule, and keeps
   * track of the path to the node where the insertion of the next
   * node in the TM tree should be inserted as the tree is traversed.
   */
  public ISLScheduleNode getCurrentNode() {
    ISLScheduleNode node = this.schedule.getRoot();
    for (final Integer p : this.path) {
      node = node.getChild((p).intValue());
    }
    return node;
  }

  public static ISLSchedule construct(final TargetMappingForSystemBody tm) {
    final ConstructISLScheduleTree visitor = new ConstructISLScheduleTree();
    visitor.accept(tm);
    return visitor.schedule;
  }

  @Override
  public void inTargetMappingForSystemBody(final TargetMappingForSystemBody tm) {
    super.inTargetMappingForSystemBody(tm);
    final List<AlphaScheduleTarget> targets = EcoreUtil2.<AlphaScheduleTarget>getAllContentsOfType(tm.getTargetBody(), AlphaScheduleTarget.class);
    final Function1<AlphaScheduleTarget, ISLUnionSet> _function = (AlphaScheduleTarget t) -> {
      return this.constructNamedSet(t).toUnionSet();
    };
    final Function2<ISLUnionSet, ISLUnionSet, ISLUnionSet> _function_1 = (ISLUnionSet s1, ISLUnionSet s2) -> {
      return s1.union(s2);
    };
    ISLUnionSet domain = IterableExtensions.<ISLUnionSet>reduce(ListExtensions.<AlphaScheduleTarget, ISLUnionSet>map(targets, _function), _function_1);
    this.schedule = ISLSchedule.buildFromDomain(domain);
    LinkedList<Integer> _linkedList = new LinkedList<Integer>();
    this.path = _linkedList;
    this.path.add(Integer.valueOf(0));
    ScheduleTreeExpression _scheduleTreeRoot = tm.getScheduleTreeRoot();
    boolean _not = (!(_scheduleTreeRoot instanceof ContextExpression));
    if (_not) {
      final ISLSet paramDomain = tm.getTargetBody().getSystem().getParameterDomain();
      final ISLSet contextDomain = paramDomain.addDims(ISLDimType.isl_dim_set, 0);
      this.schedule = this.schedule.insertContext(contextDomain);
      this.path.add(Integer.valueOf(0));
    }
  }

  @Override
  public void inContextExpression(final ContextExpression ce) {
    final ISLSet pDom = TargetMappingUtil.getTargetSystem(ce).getParameterDomain();
    this.schedule = this.schedule.insertContext(ce.getContextDomain().intersectParams(pDom));
    this.path.add(Integer.valueOf(0));
  }

  @Override
  public void outContextExpression(final ContextExpression ce) {
    int _size = this.path.size();
    int _minus = (_size - 1);
    this.path.remove(_minus);
  }

  @Override
  public void visitSequenceExpression(final SequenceExpression se) {
    final Function1<ScheduleTreeExpression, Boolean> _function = (ScheduleTreeExpression c) -> {
      return Boolean.valueOf((c instanceof FilterExpression));
    };
    final Iterable<ScheduleTreeExpression> filters = IterableExtensions.<ScheduleTreeExpression>filter(se.getChildren(), _function);
    final Function1<ScheduleTreeExpression, ISLUnionSet> _function_1 = (ScheduleTreeExpression fe) -> {
      return this.constructDomain(((FilterExpression) fe));
    };
    final Iterable<ISLUnionSet> domains = IterableExtensions.<ScheduleTreeExpression, ISLUnionSet>map(filters, _function_1);
    ISLUnionSetList unionSetList = ISLUnionSetList.build(ISLContext.getInstance(), IterableExtensions.size(domains));
    for (final ISLUnionSet d : domains) {
      unionSetList = unionSetList.add(d);
    }
    ISLScheduleSequenceNode seqNode = this.getCurrentNode().insertSequence(unionSetList);
    final Collection<Object> extensions = EcoreUtil2.<Object>getObjectsByType(se.getChildren(), TargetmappingPackage.Literals.EXTENSION_EXPRESSION);
    int _size = extensions.size();
    boolean _greaterThan = (_size > 1);
    if (_greaterThan) {
      for (final Object ex : extensions) {
        {
          final ISLUnionMap exMap = this.constructNamedExtensionMap(((ExtensionExpression) ex));
          final ISLScheduleExtensionNode exNode = ISLScheduleExtensionNode.buildFromExtensionMap(exMap);
          int _indexOf = se.getChildren().indexOf(ex);
          boolean _equals = (_indexOf == 0);
          if (_equals) {
            final ISLScheduleNode graftedNode = seqNode.graftBefore(exNode);
            seqNode = ((ISLScheduleSequenceNode) graftedNode);
          } else {
            int _indexOf_1 = se.getChildren().indexOf(ex);
            int _size_1 = se.getChildren().size();
            int _minus = (_size_1 - 1);
            boolean _equals_1 = (_indexOf_1 == _minus);
            if (_equals_1) {
              final ISLScheduleNode graftedNode_1 = seqNode.graftAfter(exNode);
              seqNode = ((ISLScheduleSequenceNode) graftedNode_1);
            } else {
              throw new RuntimeException("ExtensionExpression is not at the beginning/end of sequence");
            }
          }
        }
      }
      ISLScheduleNode _parent = seqNode.parent().parent();
      seqNode = ((ISLScheduleSequenceNode) _parent).spliceChild(1);
      this.path.add(Integer.valueOf(0));
    }
    this.schedule = seqNode.getSchedule();
    final int depth = this.path.size();
    this.path.add(Integer.valueOf(0));
    EList<ScheduleTreeExpression> _children = se.getChildren();
    for (final ScheduleTreeExpression c : _children) {
      {
        this.path.set(depth, Integer.valueOf(se.getChildren().indexOf(c)));
        c.accept(this);
      }
    }
    this.path.remove(depth);
    int _size_1 = extensions.size();
    boolean _greaterThan_1 = (_size_1 > 0);
    if (_greaterThan_1) {
      this.path.remove((depth - 1));
    }
  }

  @Override
  public void visitSetExpression(final SetExpression se) {
    final Function1<ScheduleTreeExpression, ISLUnionSet> _function = (ScheduleTreeExpression c) -> {
      return this.constructDomain(((FilterExpression) c));
    };
    final List<ISLUnionSet> domains = ListExtensions.<ScheduleTreeExpression, ISLUnionSet>map(se.getChildren(), _function);
    ISLUnionSetList unionSetList = ISLUnionSetList.build(ISLContext.getInstance(), domains.size());
    for (final ISLUnionSet d : domains) {
      unionSetList = unionSetList.add(d);
    }
    final ISLScheduleSetNode seqNode = this.getCurrentNode().insertSet(unionSetList);
    this.schedule = seqNode.getSchedule();
    final int depth = this.path.size();
    this.path.add(Integer.valueOf(0));
    EList<ScheduleTreeExpression> _children = se.getChildren();
    for (final ScheduleTreeExpression c : _children) {
      {
        this.path.set(depth, Integer.valueOf(se.getChildren().indexOf(c)));
        c.accept(this);
      }
    }
    this.path.remove(depth);
  }

  @Override
  public void inFilterExpression(final FilterExpression fe) {
    if (((fe.eContainer() instanceof SetExpression) || (fe.eContainer() instanceof SequenceExpression))) {
      this.path.add(Integer.valueOf(0));
      return;
    }
    final ISLUnionSet filterDomain = this.constructDomain(fe);
    final ISLScheduleFilterNode filterNode = this.getCurrentNode().insertFilter(filterDomain);
    this.schedule = filterNode.getSchedule();
    this.path.add(Integer.valueOf(0));
  }

  @Override
  public void outFilterExpression(final FilterExpression fe) {
    int _size = this.path.size();
    int _minus = (_size - 1);
    this.path.remove(_minus);
  }

  @Override
  public void inGuardExpression(final GuardExpression ge) {
    final ISLScheduleGuardNode guardNode = this.getCurrentNode().insertGuard(ge.getGuardDomain());
    this.schedule = guardNode.getSchedule();
    this.path.add(Integer.valueOf(0));
  }

  @Override
  public void outGuardExpression(final GuardExpression ge) {
    int _size = this.path.size();
    int _minus = (_size - 1);
    this.path.remove(_minus);
  }

  @Override
  public void inMarkExpression(final MarkExpression me) {
    final ISLScheduleMarkNode markNode = this.getCurrentNode().insertMark(ISLIdentifier.alloc(ISLContext.getInstance(), me.getIdentifier()));
    this.schedule = markNode.getSchedule();
    this.path.add(Integer.valueOf(0));
  }

  @Override
  public void outMarkExpression(final MarkExpression me) {
    int _size = this.path.size();
    int _minus = (_size - 1);
    this.path.remove(_minus);
  }

  @Override
  public void inBandExpression(final BandExpression be) {
    final Function1<BandPiece, ISLMultiUnionPWAff> _function = (BandPiece it) -> {
      return this.constructPartialSchedule(it);
    };
    final Function2<ISLMultiUnionPWAff, ISLMultiUnionPWAff, ISLMultiUnionPWAff> _function_1 = (ISLMultiUnionPWAff p1, ISLMultiUnionPWAff p2) -> {
      return p1.unionAdd(p2);
    };
    final ISLMultiUnionPWAff partialSchedule = IterableExtensions.<ISLMultiUnionPWAff>reduce(ListExtensions.<BandPiece, ISLMultiUnionPWAff>map(be.getBandPieces(), _function), _function_1);
    ISLScheduleBandNode bandNode = this.getCurrentNode().insertPartialSchedule(partialSchedule);
    Iterable<ISLLoopTypeSpecification> _filter = Iterables.<ISLLoopTypeSpecification>filter(be.getLoopTypeSpecifications(), ISLLoopTypeSpecification.class);
    for (final ISLLoopTypeSpecification lts : _filter) {
      bandNode = bandNode.setASTLoopType(lts.getDimension(), lts.getLoopType());
    }
    IsolateSpecification _isolateSpecification = be.getIsolateSpecification();
    boolean _tripleNotEquals = (_isolateSpecification != null);
    if (_tripleNotEquals) {
      final ISLMap map = be.getIsolateSpecification().getIsolateMap();
      ISLSet _wrap = map.wrap();
      final ISLSet isolateOption = _wrap.setTupleName("isolate");
      bandNode = bandNode.setASTBuildOptions(isolateOption.toUnionSet());
      Iterable<ISLLoopTypeSpecification> _filter_1 = Iterables.<ISLLoopTypeSpecification>filter(be.getIsolateSpecification().getLoopTypeSpecifications(), ISLLoopTypeSpecification.class);
      for (final ISLLoopTypeSpecification lts_1 : _filter_1) {
        bandNode = bandNode.setIsolateASTLoopType(lts_1.getDimension(), lts_1.getLoopType());
      }
    }
    this.schedule = bandNode.getSchedule();
    this.path.add(Integer.valueOf(0));
  }

  @Override
  public void outBandExpression(final BandExpression be) {
    int _size = this.path.size();
    int _minus = (_size - 1);
    this.path.remove(_minus);
  }

  @Override
  public void inExtensionExpression(final ExtensionExpression ee) {
    this.path.add(Integer.valueOf(0));
  }

  @Override
  public void outExtensionExpression(final ExtensionExpression ee) {
    int _size = this.path.size();
    int _minus = (_size - 1);
    this.path.remove(_minus);
  }

  private ISLSet _constructNamedSet(final StandardEquation eq) {
    final ISLSet vDom = eq.getVariable().getDomain();
    return vDom.setTupleName(eq.getName());
  }

  private ISLSet _constructNamedSet(final UseEquation eq) {
    throw new UnsupportedOperationException();
  }

  private ISLSet _constructNamedSet(final ExtensionTarget et) {
    final ISLSet range = et.getExtensionMap().getRange();
    return range.setTupleName(et.getName());
  }

  private ISLSet constructNamedSet(final ScheduleTargetRestrictDomain strd) {
    ISLSet _restrictDomain = strd.getRestrictDomain();
    return _restrictDomain.setTupleName(strd.getScheduleTarget().getName());
  }

  private ISLUnionSet constructDomain(final FilterExpression fe) {
    final Function1<ScheduleTargetRestrictDomain, ISLUnionSet> _function = (ScheduleTargetRestrictDomain it) -> {
      return this.constructNamedSet(it).toUnionSet();
    };
    final Function2<ISLUnionSet, ISLUnionSet, ISLUnionSet> _function_1 = (ISLUnionSet s1, ISLUnionSet s2) -> {
      return s1.union(s2);
    };
    return IterableExtensions.<ISLUnionSet>reduce(ListExtensions.<ScheduleTargetRestrictDomain, ISLUnionSet>map(fe.getFilterDomains(), _function), _function_1);
  }

  private ISLMultiUnionPWAff constructPartialSchedule(final BandPiece bp) {
    ISLMultiUnionPWAff _xblockexpression = null;
    {
      final ISLSet pieceDomain = this.constructNamedSet(bp.getPieceDomain());
      ISLMultiPWAff partialSchedule = bp.getPartialSchedule().toMultiPWAff();
      partialSchedule = partialSchedule.setTupleName(ISLDimType.isl_dim_in, bp.getPieceDomain().getScheduleTarget().getName());
      partialSchedule = partialSchedule.intersectDomain(pieceDomain);
      _xblockexpression = partialSchedule.toMultiUnionPWAff();
    }
    return _xblockexpression;
  }

  private ISLUnionMap constructNamedExtensionMap(final ExtensionExpression ee) {
    final Function1<ExtensionTarget, ISLUnionMap> _function = (ExtensionTarget it) -> {
      return this.constructNamedExtensionMap(it);
    };
    final Function2<ISLUnionMap, ISLUnionMap, ISLUnionMap> _function_1 = (ISLUnionMap p1, ISLUnionMap p2) -> {
      return p1.union(p2);
    };
    return IterableExtensions.<ISLUnionMap>reduce(ListExtensions.<ExtensionTarget, ISLUnionMap>map(ee.getExtensionTargets(), _function), _function_1);
  }

  private ISLUnionMap constructNamedExtensionMap(final ExtensionTarget et) {
    ISLMap _extensionMap = et.getExtensionMap();
    IISLSingleSpaceMapMethods stExMap = _extensionMap.setOutputTupleName(et.getName());
    return ((ISLMap) stExMap).toUnionMap();
  }

  private ISLSet constructNamedSet(final AlphaScheduleTarget eq) {
    if (eq instanceof StandardEquation) {
      return _constructNamedSet((StandardEquation)eq);
    } else if (eq instanceof UseEquation) {
      return _constructNamedSet((UseEquation)eq);
    } else if (eq instanceof ExtensionTarget) {
      return _constructNamedSet((ExtensionTarget)eq);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(eq).toString());
    }
  }
}
