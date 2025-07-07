package alpha.model.scheduler;

import alpha.model.AlphaSystem;
import alpha.model.prdg.PRDG;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class HybridScheduler extends Scheduler {
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
      final ISLSchedule foutrierSchedule = ISLSchedule.computeSchedule(islDomains.copy(), islPRDG.copy(), ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER);
      final ISLSchedule plutoSchedule = ISLSchedule.computeSchedule(islDomains, islPRDG, ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL);
      this.domains = foutrierSchedule.getDomain();
      final int timeDims = ISLUtil.countTimeDimensions(system, foutrierSchedule.getMap());
      final Function1<ISLMap, String> _function = (ISLMap it) -> {
        return it.getInputTupleName();
      };
      final List<String> varNames = ListExtensions.<ISLMap, String>map(foutrierSchedule.getMap().getMaps(), _function);
      final Function1<String, ISLMap> _function_1 = (String name) -> {
        final Function1<ISLMap, Boolean> _function_2 = (ISLMap it) -> {
          String _inputTupleName = it.getInputTupleName();
          return Boolean.valueOf(Objects.equal(_inputTupleName, name));
        };
        final List<ISLAff> timeAffs = ISLUtil.toMultiAff(IterableExtensions.<ISLMap>findFirst(foutrierSchedule.getMap().getMaps(), _function_2)).getAffs().subList(0, timeDims);
        final Function1<ISLMap, Boolean> _function_3 = (ISLMap it) -> {
          String _inputTupleName = it.getInputTupleName();
          return Boolean.valueOf(Objects.equal(_inputTupleName, name));
        };
        final List<ISLAff> spaceAffs = ISLUtil.toMultiAff(IterableExtensions.<ISLMap>findFirst(plutoSchedule.getMap().getMaps(), _function_3)).getAffs();
        return ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(Iterables.<ISLAff>concat(timeAffs, spaceAffs))).toMap().<ISLMap>setInputTupleName(name);
      };
      _xblockexpression = this.spacetimeMap = ISLUtil.convertToUnionMap(IterableExtensions.<ISLMap>toList(ListExtensions.<String, ISLMap>map(varNames, _function_1)));
    }
    return _xblockexpression;
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
