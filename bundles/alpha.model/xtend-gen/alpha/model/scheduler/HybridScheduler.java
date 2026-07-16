package alpha.model.scheduler;

import alpha.model.AlphaSystem;
import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGEdge;
import alpha.model.prdg.PRDGNode;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class HybridScheduler extends Scheduler {
  private Iterable<ISLMap> feautrierMaps;

  private Iterable<ISLMap> plutoMaps;

  private int timeDims;

  private ISLUnionMap spacetimeMap;

  private ISLUnionSet domains;

  private PRDG prdg;

  private boolean doSplit;

  public HybridScheduler(final AlphaSystem system, final PRDG prdg, final boolean doSplit) {
    this.prdg = prdg;
    this.doSplit = doSplit;
    this.generateSchedule(system);
  }

  public HybridScheduler(final AlphaSystem system, final PRDG prdg) {
    this.prdg = prdg;
    this.doSplit = false;
    this.generateSchedule(system);
  }

  public HybridScheduler(final AlphaSystem system, final PRDG prdg, final Iterable<ISLMap> originalScheduleMaps) {
    this.prdg = prdg;
    this.doSplit = true;
    this.feautrierMaps = originalScheduleMaps;
    this.generateSchedule(system);
  }

  public ISLUnionMap generateSchedule(final AlphaSystem system) {
    ISLUnionMap _xblockexpression = null;
    {
      ISLUnionSet islDomains = this.prdg.generateDomains();
      final ISLUnionMap islPRDG = this.prdg.generateISLPRDG();
      final ISLSchedule feautrierSchedule = ISLSchedule.computeSchedule(islDomains.copy(), islPRDG.copy(), ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER);
      final ISLSchedule plutoSchedule = ISLSchedule.computeSchedule(islDomains, islPRDG, ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL);
      if ((this.feautrierMaps == null)) {
        this.feautrierMaps = feautrierSchedule.getMap().getMaps();
      }
      this.plutoMaps = plutoSchedule.getMap().getMaps();
      this.domains = feautrierSchedule.getDomain();
      this.timeDims = ISLUtil.countTimeDimensions(system, feautrierSchedule.getMap());
      final Comparator<PRDGNode> _function = (PRDGNode a, PRDGNode b) -> {
        int _xifexpression = (int) 0;
        boolean _isReductionNode = a.isReductionNode();
        if (_isReductionNode) {
          _xifexpression = 0;
        } else {
          _xifexpression = (-1);
        }
        int _xifexpression_1 = (int) 0;
        boolean _isReductionNode_1 = b.isReductionNode();
        if (_isReductionNode_1) {
          _xifexpression_1 = 0;
        } else {
          _xifexpression_1 = 1;
        }
        return (_xifexpression + _xifexpression_1);
      };
      final Function1<Pair<Integer, PRDGNode>, ISLMap> _function_1 = (Pair<Integer, PRDGNode> it) -> {
        return this.generateHybridMap((it.getKey()).intValue(), it.getValue());
      };
      _xblockexpression = this.spacetimeMap = ISLUtil.liftSpacetimeFactors(ISLUtil.convertToUnionMap(IterableExtensions.<ISLMap>toList(IterableExtensions.<Pair<Integer, PRDGNode>, ISLMap>map(IterableExtensions.<PRDGNode>indexed(IterableExtensions.<PRDGNode>sortWith(this.prdg.getNodes(), _function)), _function_1))));
    }
    return _xblockexpression;
  }

  private ISLMap generateHybridMap(final int index, final PRDGNode node) {
    final boolean splitReduction = (node.isReductionNode() && this.doSplit);
    PRDGEdge _xifexpression = null;
    if (splitReduction) {
      _xifexpression = this.dominantDependence(this.prdg, node.getName());
    } else {
      _xifexpression = null;
    }
    final PRDGEdge dominantEdge = _xifexpression;
    if ((splitReduction && (dominantEdge == null))) {
      throw new IllegalStateException(
        (((((("Error when computing split schedule: reduction node has no dominant dependence\n" + "for node \'") + node) + "\'\n") + "and PRDG: \n") + this.prdg) + "\n"));
    }
    String _xifexpression_1 = null;
    if (splitReduction) {
      _xifexpression_1 = dominantEdge.getDest().getName();
    } else {
      _xifexpression_1 = node.getName();
    }
    final String mapName = _xifexpression_1;
    final int scheduleOffset = index;
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, mapName));
    };
    List<ISLAff> timeAffs = ISLUtil.toMultiAff(IterableExtensions.<ISLMap>findFirst(this.feautrierMaps, _function).copy().clearInputTupleName()).getAffs().subList(0, this.timeDims);
    if (splitReduction) {
      timeAffs = ISLUtil.convertToMultiAff(timeAffs).pullback(ISLUtil.toMultiAff(dominantEdge.getMap())).getAffs();
    }
    final Function1<ISLMap, Boolean> _function_1 = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      String _name = node.getName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, _name));
    };
    final List<ISLAff> spaceAffs = ISLUtil.toMultiAff(IterableExtensions.<ISLMap>findFirst(this.plutoMaps, _function_1).copy().clearInputTupleName()).getAffs();
    final Function1<ISLSet, Boolean> _function_2 = (ISLSet it) -> {
      String _tupleName = it.getTupleName();
      String _name = node.getName();
      return Boolean.valueOf(Objects.equal(_tupleName, _name));
    };
    ISLAff _buildValOnDomain = ISLAff.buildValOnDomain(IterableExtensions.<ISLSet>findFirst(this.domains.getSets(), _function_2).copy().clearTupleName().getSpace().toLocalSpace(), scheduleOffset);
    Iterable<ISLAff> _plus = Iterables.<ISLAff>concat(timeAffs, Collections.<ISLAff>unmodifiableList(CollectionLiterals.<ISLAff>newArrayList(_buildValOnDomain)));
    final Iterable<ISLAff> hybridAffs = Iterables.<ISLAff>concat(_plus, spaceAffs);
    ISLMap hybridMap = ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(hybridAffs)).toMap();
    return hybridMap.<ISLMap>setInputTupleName(node.getName());
  }

  private PRDGEdge dominantDependence(final PRDG prdg, final String name) {
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge it) -> {
      String _name = it.getSource().getName();
      return Boolean.valueOf(Objects.equal(_name, name));
    };
    final Function2<PRDGEdge, PRDGEdge, PRDGEdge> _function_1 = (PRDGEdge a, PRDGEdge b) -> {
      return this.dominantEdge(a, b);
    };
    return IterableExtensions.<PRDGEdge>reduce(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function), _function_1);
  }

  private PRDGEdge dominantEdge(final PRDGEdge a, final PRDGEdge b) {
    if ((a == null)) {
      return b;
    }
    if ((b == null)) {
      return a;
    }
    final ISLSet sourceSet = a.getSource().getDomain().copy();
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      String _name = a.getDest().getName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, _name));
    };
    final ISLMap timestampA = IterableExtensions.<ISLMap>findFirst(this.feautrierMaps, _function).copy().clearInputTupleName();
    final Function1<ISLMap, Boolean> _function_1 = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      String _name = b.getDest().getName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, _name));
    };
    final ISLMap timestampB = IterableExtensions.<ISLMap>findFirst(this.feautrierMaps, _function_1).copy().clearInputTupleName();
    final ISLAff availA = ISLUtil.toMultiAff(a.getMap().applyRange(timestampA)).getAffs().get(0);
    final ISLAff availB = ISLUtil.toMultiAff(b.getMap().applyRange(timestampB)).getAffs().get(0);
    boolean _isSubset = sourceSet.copy().isSubset(ISLSet.buildGESet(availA.copy(), availB.copy()));
    if (_isSubset) {
      return a;
    } else {
      boolean _isSubset_1 = sourceSet.isSubset(ISLSet.buildGESet(availB, availA));
      if (_isSubset_1) {
        return b;
      } else {
        return null;
      }
    }
  }

  @Override
  public ISLUnionMap getMaps() {
    return this.spacetimeMap.copy();
  }

  @Override
  public ISLUnionSet getDomains() {
    return this.domains.copy();
  }

  @Override
  protected ISLSchedule getSchedule() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
