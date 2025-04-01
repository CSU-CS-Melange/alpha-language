package alpha.model.memorymapper;

import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGEdge;
import alpha.model.prdg.PRDGNode;
import alpha.model.scheduler.Scheduler;
import alpha.model.tiler.DTiler;
import alpha.model.tiler.Tiler;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.HashMap;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Creates a tiled memory map that minimizes memory usage
 * and leverages the locality created by a tiling.
 * Tiles may be overwritten in memory as soon as their 'lifetime' has expired
 * The lifetime of a tile is the timestamp at which it is last read minus
 * the timestamp at which it is first written to
 * 
 * Only attempts to optimize the first dimension of time for now
 */
@SuppressWarnings("all")
public class AutoTileMemoryMapper {
  private HashMap<String, ISLMap> memoryMaps;

  private final Tiler tiler;

  private final PRDG prdg;

  private final Scheduler scheduler;

  public AutoTileMemoryMapper(final Tiler tiler, final PRDG prdg, final Scheduler scheduler) {
    this.tiler = tiler;
    this.prdg = prdg;
    this.scheduler = scheduler;
    this.generateMemoryMaps();
  }

  private void generateMemoryMaps() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method getUpperBound(ISLDimType, int) is undefined for the type ISLSet");
  }

  public ISLSet maxLifespan(final PRDGNode variable) {
    ISLSet domain = variable.getDomain().copy();
    if ((this.tiler instanceof DTiler)) {
      domain = domain.intersect(
        domain.copy().apply(
          ISLUtil.buildTranslationMaff(ISLUtil.affToVector(ISLUtil.toMultiAff(this.scheduler.getScheduleMap(variable.getName()).clearInputTupleName()).getAff(0).scale(((DTiler) this.tiler).getTileSize(0)).negate())).toMap()));
    }
    final ISLMap untileMap = this.tiler.getUntileMap().intersectRange(
      domain.copy().apply(
        this.scheduler.getScheduleMap(variable.getName()).clearInputTupleName())).simpleHull().toMap();
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge it) -> {
      PRDGNode _dest = it.getDest();
      return Boolean.valueOf(Objects.equal(_dest, variable));
    };
    final Iterable<PRDGEdge> dependences = IterableExtensions.<PRDGEdge>filter(this.prdg.getEdges(), _function);
    final ISLPWMultiAff birth = untileMap.copy().lexMin().toPWMultiAff();
    final Function1<PRDGEdge, ISLMap> _function_1 = (PRDGEdge it) -> {
      return untileMap.copy().applyRange(
        it.getMap().applyDomain(this.scheduler.getScheduleMap(it.getSource().getName()).clearInputTupleName()).applyRange(this.scheduler.getScheduleMap(it.getDest().getName()).clearInputTupleName()).reverse()).lexMax();
    };
    final Function1<ISLMap, ISLPWMultiAff> _function_2 = (ISLMap it) -> {
      return it.toPWMultiAff();
    };
    final Iterable<ISLPWMultiAff> lastUses = IterableExtensions.<ISLMap, ISLPWMultiAff>map(IterableExtensions.<PRDGEdge, ISLMap>map(dependences, _function_1), _function_2);
    final Function1<ISLPWMultiAff, ISLMap> _function_3 = (ISLPWMultiAff it) -> {
      return it.sub(birth.copy()).toMap();
    };
    final Function2<ISLMap, ISLMap, ISLMap> _function_4 = (ISLMap a, ISLMap b) -> {
      return a.union(b);
    };
    final ISLMap lifespan = IterableExtensions.<ISLMap>reduce(IterableExtensions.<ISLPWMultiAff, ISLMap>map(lastUses, _function_3), _function_4).simpleHull().toMap();
    return lifespan.getRange().lexMax().simpleHull().toSet();
  }
}
