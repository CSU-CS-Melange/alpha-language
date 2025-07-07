package alpha.model.tiler;

import alpha.model.AlphaSystem;
import alpha.model.scheduler.ScheduleVerifier;
import alpha.model.scheduler.Scheduler;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class TileVerifier {
  public static void verify(final AlphaSystem sys, final Scheduler scheduler, final Tiler tiler) {
    final List<ISLMap> maps = tiler.tileSchedule(scheduler.getMaps()).getMaps();
    ScheduleVerifier verifier = new ScheduleVerifier(maps);
    verifier.accept(sys);
  }

  public static void verifyWavefront(final AlphaSystem sys, final Scheduler scheduler, final Tiler tiler, final int timeDims) {
    final Function1<ISLMap, ISLMap> _function = (ISLMap it) -> {
      return ISLUtil.convertToMultiAff(ISLUtil.toMultiAff(it).getAffs().subList(0, timeDims)).toMap();
    };
    final List<ISLMap> maps = ListExtensions.<ISLMap, ISLMap>map(tiler.tileSchedule(scheduler.getMaps()).getMaps(), _function);
    ScheduleVerifier verifier = new ScheduleVerifier(maps);
    verifier.accept(sys);
  }
}
