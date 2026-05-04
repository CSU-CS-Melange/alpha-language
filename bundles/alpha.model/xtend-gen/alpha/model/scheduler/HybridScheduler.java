package alpha.model.scheduler;

import alpha.model.AlphaSystem;
import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGEdge;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class HybridScheduler extends Scheduler {
  private Iterable<ISLMap> feautrierMaps;

  private Iterable<ISLMap> plutoMaps;

  private int timeDims;

  private ISLUnionMap spacetimeMap;

  private ISLUnionSet domains;

  private PRDG prdg;

  public HybridScheduler(final AlphaSystem system, final PRDG prdg) {
    this.prdg = prdg;
    this.generateSchedule(system);
  }

  public ISLUnionMap generateSchedule(final AlphaSystem system) {
    ISLUnionMap _xblockexpression = null;
    {
      ISLUnionSet islDomains = this.prdg.generateDomains();
      final ISLUnionMap islPRDG = this.prdg.generateISLPRDG();
      final ISLSchedule feautrierSchedule = ISLSchedule.computeSchedule(islDomains.copy(), islPRDG.copy(), ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER);
      final ISLSchedule plutoSchedule = ISLSchedule.computeSchedule(islDomains, islPRDG, ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL);
      this.feautrierMaps = feautrierSchedule.getMap().getMaps();
      this.plutoMaps = plutoSchedule.getMap().getMaps();
      this.domains = feautrierSchedule.getDomain();
      this.timeDims = ISLUtil.countTimeDimensions(system, feautrierSchedule.getMap());
      final Function1<ISLMap, String> _function = (ISLMap it) -> {
        return it.getInputTupleName();
      };
      final Function1<String, ISLMap> _function_1 = (String it) -> {
        return this.generateHybridMap(it);
      };
      _xblockexpression = this.spacetimeMap = ISLUtil.convertToUnionMap(IterableExtensions.<ISLMap>toList(IterableExtensions.<String, ISLMap>map(IterableExtensions.<ISLMap, String>map(this.feautrierMaps, _function), _function_1)));
    }
    return _xblockexpression;
  }

  private ISLMap generateHybridMap(final String name) {
    final boolean isReduction = this.prdg.getNode(name).isReductionNode();
    PRDGEdge _xifexpression = null;
    if (isReduction) {
      _xifexpression = this.dominantDependence(this.prdg, name);
    } else {
      _xifexpression = null;
    }
    final PRDGEdge dominantEdge = _xifexpression;
    String _xifexpression_1 = null;
    if (isReduction) {
      _xifexpression_1 = dominantEdge.getDest().getName();
    } else {
      _xifexpression_1 = name;
    }
    final String mapName = _xifexpression_1;
    int _xifexpression_2 = (int) 0;
    if (isReduction) {
      _xifexpression_2 = 1;
    } else {
      _xifexpression_2 = 0;
    }
    final int scheduleOffset = _xifexpression_2;
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, mapName));
    };
    List<ISLAff> timeAffs = ISLUtil.toMultiAff(IterableExtensions.<ISLMap>findFirst(this.feautrierMaps, _function).copy().clearInputTupleName()).getAffs().subList(0, this.timeDims);
    if (isReduction) {
      timeAffs = ISLUtil.convertToMultiAff(timeAffs).pullback(ISLUtil.toMultiAff(dominantEdge.getMap())).getAffs();
    }
    final Function1<ISLMap, Boolean> _function_1 = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, name));
    };
    final List<ISLAff> spaceAffs = ISLUtil.toMultiAff(IterableExtensions.<ISLMap>findFirst(this.plutoMaps, _function_1).copy().clearInputTupleName()).getAffs();
    final Function1<ISLSet, Boolean> _function_2 = (ISLSet it) -> {
      String _tupleName = it.getTupleName();
      return Boolean.valueOf(Objects.equal(_tupleName, name));
    };
    ISLAff _buildValOnDomain = ISLAff.buildValOnDomain(IterableExtensions.<ISLSet>findFirst(this.domains.getSets(), _function_2).copy().clearTupleName().getSpace().toLocalSpace(), scheduleOffset);
    Iterable<ISLAff> _plus = Iterables.<ISLAff>concat(timeAffs, Collections.<ISLAff>unmodifiableList(CollectionLiterals.<ISLAff>newArrayList(_buildValOnDomain)));
    final Iterable<ISLAff> hybridAffs = Iterables.<ISLAff>concat(_plus, spaceAffs);
    ISLMap hybridMap = ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(hybridAffs)).toMap();
    return hybridMap.<ISLMap>setInputTupleName(name);
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
    final ISLSet sourceSet = a.getSource().getDomain();
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
    final ISLMultiAff availA = ISLUtil.toMultiAff(a.getMap().applyRange(timestampA));
    final ISLMultiAff availB = ISLUtil.toMultiAff(b.getMap().applyRange(timestampB));
    PRDGEdge _xifexpression = null;
    boolean _isSubset = sourceSet.isSubset(ISLUtil.buildLexGESet(availA, availB));
    if (_isSubset) {
      _xifexpression = a;
    } else {
      _xifexpression = b;
    }
    return _xifexpression;
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
