package alpha.model.scheduler;

import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ManualScheduler implements Scheduler {
  private ISLUnionMap maps;

  private ISLUnionSet domains;

  public ManualScheduler(final String maps, final String domains) {
    this.maps = ISLUtil.toISLUnionMap(maps);
    this.domains = ISLUtil.toISLUnionSet(domains);
  }

  public ManualScheduler(final String scheduleString) {
    final ISLSchedule schedule = ISLUtil.toISLSchedule(scheduleString);
    InputOutput.<String>println(("BLAH: " + schedule));
    this.maps = schedule.getMap();
    final Consumer<ISLMap> _function = (ISLMap x) -> {
      InputOutput.<String>println(("Sched: " + x));
    };
    this.maps.getMaps().forEach(_function);
    this.domains = schedule.getDomain();
  }

  @Override
  public ISLMap getScheduleMap(final String variable) {
    ISLMap _xblockexpression = null;
    {
      ISLMap _elvis = null;
      final Function1<ISLMap, Boolean> _function = (ISLMap map) -> {
        String _inputTupleName = map.getInputTupleName();
        return Boolean.valueOf(Objects.equal(_inputTupleName, variable));
      };
      ISLMap _head = IterableExtensions.<ISLMap>head(IterableExtensions.<ISLMap>filter(this.maps.getMaps(), _function));
      if (_head != null) {
        _elvis = _head;
      } else {
        _elvis = null;
      }
      final ISLMap map = _elvis;
      ISLMap _xifexpression = null;
      if ((map == null)) {
        _xifexpression = null;
      } else {
        _xifexpression = map.copy();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  @Override
  public ISLSet getScheduleDomain(final String variable) {
    ISLSet _xblockexpression = null;
    {
      ISLSet _elvis = null;
      final Function1<ISLSet, Boolean> _function = (ISLSet set) -> {
        String _tupleName = set.getTupleName();
        return Boolean.valueOf(Objects.equal(_tupleName, variable));
      };
      ISLSet _head = IterableExtensions.<ISLSet>head(IterableExtensions.<ISLSet>filter(this.domains.getSets(), _function));
      if (_head != null) {
        _elvis = _head;
      } else {
        _elvis = null;
      }
      final ISLSet domain = _elvis;
      ISLSet _xifexpression = null;
      if ((domain == null)) {
        _xifexpression = null;
      } else {
        _xifexpression = domain.copy();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  @Override
  public ISLUnionMap getMaps() {
    return this.maps.copy();
  }

  @Override
  public ISLUnionSet getDomains() {
    return this.domains.copy();
  }
}
