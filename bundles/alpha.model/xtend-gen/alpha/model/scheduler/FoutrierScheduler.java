package alpha.model.scheduler;

import alpha.model.prdg.PRDG;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class FoutrierScheduler implements Scheduler {
  private ISLSchedule schedule;

  private PRDG prdg;

  private ISLUnionMap umap;

  public FoutrierScheduler(final PRDG prdg) {
    this.prdg = prdg;
    this.generateSchedule();
  }

  public ISLUnionMap generateSchedule() {
    ISLUnionMap _xblockexpression = null;
    {
      ISLUnionSet domains = this.prdg.generateDomains();
      ISLUnionMap islPRDG = this.prdg.generateISLPRDG();
      this.schedule = ISLSchedule.computeSchedule(domains, islPRDG, ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER);
      _xblockexpression = this.umap = ISLUtil.liftSpacetimeFactors(this.schedule.getMap());
    }
    return _xblockexpression;
  }

  @Override
  public ISLSet getScheduleDomain(final String variable) {
    final Function1<ISLSet, Boolean> _function = (ISLSet it) -> {
      String _tupleName = it.getTupleName();
      return Boolean.valueOf(Objects.equal(_tupleName, variable));
    };
    ISLSet _findFirst = IterableExtensions.<ISLSet>findFirst(this.schedule.getDomain().getSets(), _function);
    ISLSet _copy = null;
    if (_findFirst!=null) {
      _copy=_findFirst.copy();
    }
    return _copy;
  }

  @Override
  public ISLMap getScheduleMap(final String variable) {
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, variable));
    };
    ISLMap _findFirst = IterableExtensions.<ISLMap>findFirst(this.schedule.getMap().getMaps(), _function);
    ISLMap _copy = null;
    if (_findFirst!=null) {
      _copy=_findFirst.copy();
    }
    return _copy;
  }

  @Override
  public ISLUnionMap getMaps() {
    return this.umap.copy();
  }

  @Override
  public ISLUnionSet getDomains() {
    return this.schedule.getDomain().copy();
  }

  @Override
  public ISLMap getAnonymousMap(final String variable) {
    return this.getScheduleMap(variable).clearInputTupleName();
  }
}
