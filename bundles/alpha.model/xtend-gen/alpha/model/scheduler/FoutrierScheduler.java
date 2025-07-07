package alpha.model.scheduler;

import alpha.model.prdg.PRDG;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;

@SuppressWarnings("all")
public class FoutrierScheduler extends Scheduler {
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
  public ISLSchedule getSchedule() {
    return this.schedule;
  }
}
