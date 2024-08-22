package alpha.model.scheduler;

import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;

@SuppressWarnings("all")
public interface Scheduler {
  ISLMap getScheduleMap(final String variable);

  ISLSet getScheduleDomain(final String variable);

  ISLUnionMap getMaps();

  ISLUnionSet getDomains();
}
