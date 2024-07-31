package alpha.model.scheduler;

import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ManualScheduler implements Scheduler {
  private ISLUnionMap maps;

  private ISLUnionSet domains;

  public ManualScheduler(final String maps, final String domains) {
    this.maps = ISLUtil.toISLUnionMap(maps);
    this.domains = ISLUtil.toISLUnionSet(domains);
  }

  @Override
  public ISLMap getScheduleMap(final String variable) {
    final Function1<ISLMap, Boolean> _function = (ISLMap map) -> {
      String _inputTupleName = map.getInputTupleName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, variable));
    };
    return IterableExtensions.<ISLMap>head(IterableExtensions.<ISLMap>filter(this.maps.getMaps(), _function)).copy();
  }

  @Override
  public ISLSet getScheduleDomain(final String macro) {
    final Function1<ISLSet, Boolean> _function = (ISLSet set) -> {
      String _tupleName = set.getTupleName();
      return Boolean.valueOf(Objects.equal(_tupleName, macro));
    };
    return IterableExtensions.<ISLSet>head(IterableExtensions.<ISLSet>filter(this.domains.getSets(), _function)).copy();
  }

  @Override
  public ISLUnionMap getMaps() {
    return this.maps;
  }

  @Override
  public ISLUnionSet getDomains() {
    return this.domains;
  }
}
