package alpha.model.scheduler;

import alpha.model.prdg.PRDG;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;

@SuppressWarnings("all")
public class PlutoScheduler extends Scheduler {
  private ISLSchedule schedule;

  private PRDG prdg;

  public PlutoScheduler(final PRDG prdg) {
    this.prdg = prdg;
    this.generateSchedule();
  }

  public ISLSchedule generateSchedule() {
    ISLSchedule _xblockexpression = null;
    {
      ISLUnionSet domain = this.prdg.generateDomains();
      ISLUnionMap islPRDG = this.prdg.generateISLPRDG();
      _xblockexpression = this.schedule = ISLSchedule.computeSchedule(domain, islPRDG, ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL);
    }
    return _xblockexpression;
  }

  @Override
  public ISLSchedule getSchedule() {
    return this.schedule;
  }
}
