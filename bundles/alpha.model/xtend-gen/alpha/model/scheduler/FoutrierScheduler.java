package alpha.model.scheduler;

import alpha.model.prdg.PRDG;
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

  public FoutrierScheduler(final PRDG prdg) {
    this.prdg = prdg;
    this.generateSchedule();
  }

  public ISLSchedule generateSchedule() {
    ISLSchedule _xblockexpression = null;
    {
      ISLUnionSet domains = this.prdg.generateDomains();
      ISLUnionMap islPRDG = this.prdg.generateISLPRDG();
      _xblockexpression = this.schedule = ISLSchedule.computeSchedule(domains, islPRDG, ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER);
    }
    return _xblockexpression;
  }

  @Override
  public ISLSet getScheduleDomain(final String variable) {
    final Function1<ISLSet, Boolean> _function = (ISLSet set) -> {
      String _tupleName = set.getTupleName();
      return Boolean.valueOf(Objects.equal(_tupleName, variable));
    };
    return IterableExtensions.<ISLSet>head(IterableExtensions.<ISLSet>filter(this.schedule.getDomain().getSets(), _function)).copy();
  }

  @Override
  public ISLMap getScheduleMap(final String variable) {
    final Function1<ISLMap, Boolean> _function = (ISLMap map) -> {
      String _inputTupleName = map.getInputTupleName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, variable));
    };
    return IterableExtensions.<ISLMap>head(IterableExtensions.<ISLMap>filter(this.schedule.getMap().getMaps(), _function)).copy();
  }

  @Override
  public ISLUnionMap getMaps() {
    return this.schedule.getMap().copy();
  }

  @Override
  public ISLUnionSet getDomains() {
    return this.schedule.getDomain().copy();
  }
}
