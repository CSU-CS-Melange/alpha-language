package alpha.model.tiler;

import alpha.model.exception.CausalityViolationException;
import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGEdge;
import alpha.model.scheduler.Scheduler;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Collections;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Checks whether a particular schedule is tiling-legal.
 * For this to be the case, all dependence vectors
 * (from producer to consumer) must lie in the positive orthant
 * when transformed by the space-time map.
 */
@SuppressWarnings("all")
public class AdmitsTiling {
  /**
   * Returns true if a schedule admits a tiling
   */
  public static boolean check(final PRDG prdg, final Scheduler scheduler, final Set<Integer> tiledDims) {
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge it) -> {
      return Boolean.valueOf(AdmitsTiling.tilingLegal(it, scheduler, tiledDims, false));
    };
    return IterableExtensions.<PRDGEdge>forall(prdg.getEdges(), _function);
  }

  public static boolean check(final PRDG prdg, final Scheduler scheduler) {
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      return Boolean.valueOf(true);
    };
    int _nbOutputs = IterableExtensions.<ISLMap>findFirst(scheduler.getMaps().getMaps(), _function).getNbOutputs();
    return AdmitsTiling.check(prdg, scheduler, IterableExtensions.<Integer>toSet(new ExclusiveRange(0, _nbOutputs, true)));
  }

  /**
   * Throws an error if a schedule does not admit a tiling,
   * with information on where the tiling fails.
   */
  public static void verify(final PRDG prdg, final Scheduler scheduler, final Set<Integer> tiledDims) {
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge it) -> {
      return Boolean.valueOf(AdmitsTiling.tilingLegal(it, scheduler, tiledDims, true));
    };
    IterableExtensions.<PRDGEdge>forall(prdg.getEdges(), _function);
  }

  public static void verify(final PRDG prdg, final Scheduler scheduler) {
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      return Boolean.valueOf(true);
    };
    int _nbOutputs = IterableExtensions.<ISLMap>findFirst(scheduler.getMaps().getMaps(), _function).getNbOutputs();
    AdmitsTiling.verify(prdg, scheduler, IterableExtensions.<Integer>toSet(new ExclusiveRange(0, _nbOutputs, true)));
  }

  /**
   * Returns a set containing the dimensions of the schedule
   * which are tileable
   */
  public static Set<Integer> tileableDims(final PRDG prdg, final Scheduler scheduler) {
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      return Boolean.valueOf(true);
    };
    int _nbOutputs = IterableExtensions.<ISLMap>findFirst(scheduler.getMaps().getMaps(), _function).getNbOutputs();
    final Function1<Integer, Boolean> _function_1 = (Integer it) -> {
      return Boolean.valueOf(AdmitsTiling.check(prdg, scheduler, IterableExtensions.<Integer>toSet(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(it)))));
    };
    return IterableExtensions.<Integer>toSet(IterableExtensions.<Integer>filter(new ExclusiveRange(0, _nbOutputs, true), _function_1));
  }

  /**
   * Returns false (or throws an error)
   * if a given PRDG edge makes the tiling illegal.
   */
  private static boolean tilingLegal(final PRDGEdge edge, final Scheduler scheduler, final Set<Integer> tiledDims, final boolean noisy) {
    final ISLMultiAff sourceTimestamp = ISLUtil.toMultiAff(scheduler.getAnonymousMap(edge.getSource().getName()));
    final ISLMultiAff destTimestamp = ISLUtil.toMultiAff(edge.getMap().applyRange(scheduler.getAnonymousMap(edge.getDest().getName())).lexMax());
    final Function1<Integer, ISLSet> _function = (Integer dim) -> {
      final ISLSet violationSet = ISLSet.buildLTSet(sourceTimestamp.getAff((dim).intValue()).copy(), destTimestamp.getAff((dim).intValue()).copy()).intersect(edge.getDomain());
      if ((noisy && (!violationSet.isEmpty()))) {
        InputOutput.<String>println("Intra-tile causality is invalid for the given schedule.");
        ISLMap _map = edge.getMap();
        ISLSet _domain = edge.getDomain();
        throw new CausalityViolationException(_map, sourceTimestamp, destTimestamp, _domain, (dim).intValue());
      }
      return violationSet;
    };
    final Iterable<ISLSet> violationSets = IterableExtensions.<Integer, ISLSet>map(tiledDims, _function);
    final Function1<ISLSet, Boolean> _function_1 = (ISLSet it) -> {
      return Boolean.valueOf(it.isEmpty());
    };
    return IterableExtensions.<ISLSet>forall(violationSets, _function_1);
  }
}
