package alpha.model.scheduler;

import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public abstract class Scheduler {
  /**
   * Returns the generated ISLSchedule object, if it exists.
   */
  protected abstract ISLSchedule getSchedule();

  /**
   * Returns a schedule map for a specific variable.
   */
  public ISLMap getScheduleMap(final String variable) {
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, variable));
    };
    ISLMap _findFirst = IterableExtensions.<ISLMap>findFirst(this.getMaps().getMaps(), _function);
    ISLMap _copy = null;
    if (_findFirst!=null) {
      _copy=_findFirst.copy();
    }
    return _copy;
  }

  /**
   * Returns the domain for a specific variable.
   */
  public ISLSet getScheduleDomain(final String variable) {
    final Function1<ISLSet, Boolean> _function = (ISLSet it) -> {
      String _tupleName = it.getTupleName();
      return Boolean.valueOf(Objects.equal(_tupleName, variable));
    };
    ISLSet _findFirst = IterableExtensions.<ISLSet>findFirst(this.getDomains().getSets(), _function);
    ISLSet _copy = null;
    if (_findFirst!=null) {
      _copy=_findFirst.copy();
    }
    return _copy;
  }

  /**
   * Returns all of the schedule maps as a single ISLUnionMap object.
   * The inputTupleName of each map corresponds to the scheduled variable.
   */
  public ISLUnionMap getMaps() {
    return this.getSchedule().getMap().copy();
  }

  /**
   * Returns all of the domains as a single ISLUnionSet object.
   * The inputTupleName of each domain corresponds to the respective variable.
   */
  public ISLUnionSet getDomains() {
    return this.getSchedule().getDomain().copy();
  }

  /**
   * Returns the images of the domains under the schedule map as a single ISLUnionSet object.
   * The inputTupleName of each range corresponds to the respective variable.
   */
  public ISLUnionSet getRanges() {
    return this.getDomains().apply(this.getMaps());
  }

  /**
   * Returns a schedule map for a specific variable, with the inputTupleName removed.
   */
  public ISLMap getAnonymousMap(final String variable) {
    return this.getScheduleMap(variable).clearInputTupleName();
  }
}
