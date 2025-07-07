package alpha.model.scheduler;

import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;

@SuppressWarnings("all")
public class ManualScheduler extends Scheduler {
  private ISLUnionMap maps;

  private ISLUnionSet domains;

  public ManualScheduler(final ISLUnionMap maps, final ISLUnionSet domains) {
    this.maps = maps;
    this.domains = domains;
  }

  public ManualScheduler(final ISLSchedule schedule) {
    this.maps = schedule.getMap();
    this.domains = schedule.getDomain();
  }

  @Override
  public ISLUnionMap getMaps() {
    return this.maps.copy();
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
