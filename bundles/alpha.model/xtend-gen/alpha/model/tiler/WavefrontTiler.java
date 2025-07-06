package alpha.model.tiler;

import alpha.model.scheduler.Scheduler;
import alpha.model.util.ISLUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class WavefrontTiler extends DTiler {
  public WavefrontTiler(final List<Integer> tileSizes, final ISLSpace scheduleSpace, final int startDim, final int endDim) {
    super(tileSizes, scheduleSpace, startDim, endDim);
    final int nTiles = ((endDim - startDim) + 1);
    final List<ISLAff> affs = ISLUtil.toMultiAff(this.tileMap).getAffs();
    ArrayList<ISLAff> newAffs = new ArrayList<ISLAff>();
    final Function2<ISLAff, ISLAff, ISLAff> _function = (ISLAff a, ISLAff b) -> {
      return a.copy().add(b.copy());
    };
    ISLAff _reduce = IterableExtensions.<ISLAff>reduce(affs.subList(0, nTiles), _function);
    newAffs.add(_reduce);
    final Function1<Integer, ISLAff> _function_1 = (Integer dim) -> {
      return affs.get(dim).copy();
    };
    Iterable<ISLAff> _map = IterableExtensions.<Integer, ISLAff>map(new ExclusiveRange(0, (nTiles - 1), true), _function_1);
    Iterables.<ISLAff>addAll(newAffs, _map);
    List<ISLAff> _subList = affs.subList(nTiles, affs.size());
    Iterables.<ISLAff>addAll(newAffs, _subList);
    this.tileMap = ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(newAffs)).toMap();
    this.tileMap = this.tileMap.setDimName(ISLUtil.Dims.OUT, 0, "tw");
    final Function2<ISLMap, Integer, ISLMap> _function_2 = (ISLMap map, Integer i) -> {
      return map.setDimName(ISLUtil.Dims.OUT, (i).intValue(), ("t" + i));
    };
    this.tileMap = IterableExtensions.<Integer, ISLMap>fold(new ExclusiveRange(1, nTiles, true), this.tileMap, _function_2);
    int _size = newAffs.size();
    final Function2<ISLMap, Integer, ISLMap> _function_3 = (ISLMap map, Integer i) -> {
      return map.setDimName(ISLUtil.Dims.OUT, (i).intValue(), ("c" + Integer.valueOf(((i).intValue() - nTiles))));
    };
    this.tileMap = IterableExtensions.<Integer, ISLMap>fold(new ExclusiveRange(nTiles, _size, true), this.tileMap, _function_3);
  }

  public WavefrontTiler(final List<Integer> tileSizes, final Scheduler scheduler, final int startDim, final int endDim) {
    this(tileSizes, scheduler.getMaps().getMaps().get(0).getRange().getSpace(), startDim, endDim);
  }
}
