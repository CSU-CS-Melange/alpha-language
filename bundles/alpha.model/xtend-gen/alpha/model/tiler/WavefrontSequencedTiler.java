package alpha.model.tiler;

import alpha.model.scheduler.Scheduler;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class WavefrontSequencedTiler extends DTiler {
  private ISLMap tileDimsMap;

  private ISLMap iteratorMap;

  public WavefrontSequencedTiler(final List<Integer> tileSizes, final ISLSpace scheduleSpace, final int startDim, final int endDim) {
    super(tileSizes, scheduleSpace, startDim, endDim);
    final int nTiles = ((endDim - startDim) + 1);
    final List<ISLAff> affs = ISLUtil.toMultiAff(this.tileMap).getAffs();
    ArrayList<ISLAff> newAffs = new ArrayList<ISLAff>();
    final Function2<ISLAff, ISLAff, ISLAff> _function = (ISLAff a, ISLAff b) -> {
      return a.copy().add(b.copy());
    };
    ISLAff _reduce = IterableExtensions.<ISLAff>reduce(affs.subList(0, nTiles), _function);
    newAffs.add(_reduce);
    List<ISLAff> _subList = affs.subList(0, (nTiles - 1));
    Iterables.<ISLAff>addAll(newAffs, _subList);
    final Function2<ISLAff, ISLAff, ISLAff> _function_1 = (ISLAff a, ISLAff b) -> {
      return a.copy().add(b.copy());
    };
    ISLAff _reduce_1 = IterableExtensions.<ISLAff>reduce(affs.subList(nTiles, (nTiles * 2)), _function_1);
    newAffs.add(_reduce_1);
    this.tileDimsMap = ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(newAffs)).toMap();
    int _size = affs.size();
    int _minus = (_size - 1);
    this.iteratorMap = ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(affs.subList(nTiles, _minus))).toMap();
    int _endDim = this.endDim;
    this.endDim = (_endDim + 1);
  }

  @Override
  public ISLUnionMap tileSchedule(final ISLUnionMap umap) {
    int _size = umap.getMaps().size();
    final Function1<Integer, ISLMap> _function = (Integer it) -> {
      return ISLAff.buildValOnDomain(umap.getMaps().get((it).intValue()).getDomain().getSpace().toLocalSpace(), (it).intValue()).toMultiAff().toMap();
    };
    final ISLUnionMap sequencingMap = ISLUtil.convertToUnionMap(IterableExtensions.<ISLMap>toList(IterableExtensions.<Integer, ISLMap>map(new ExclusiveRange(0, _size, true), _function)));
    final Function1<ISLMap, ISLMap> _function_1 = (ISLMap it) -> {
      return this.tileSchedule(it, sequencingMap);
    };
    ISLUnionMap tiledMaps = ISLUtil.convertToUnionMap(ListExtensions.<ISLMap, ISLMap>map(umap.getMaps(), _function_1));
    return tiledMaps;
  }

  private ISLMap tileSchedule(final ISLMap map, final ISLUnionMap sequencingMap) {
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      String _inputTupleName_1 = map.getInputTupleName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, _inputTupleName_1));
    };
    final ISLMap sequencedMap = IterableExtensions.<ISLMap>findFirst(sequencingMap.getMaps(), _function).copy();
    final Function<Integer, String> _function_1 = (Integer it) -> {
      return this.indexName((it).intValue());
    };
    return ISLUtil.<ISLMap>setDimNames(map.copy().applyRange(this.tileDimsMap.copy()).rangeProduct(sequencedMap).rangeProduct(map.copy().applyRange(this.iteratorMap.copy())).flatten(), ISLUtil.Dims.OUT, _function_1);
  }

  @Override
  public ISLSet getApproximateOutset(final ISLUnionSet domains) {
    final Function<Integer, String> _function = (Integer it) -> {
      return this.indexName((it).intValue());
    };
    return ISLUtil.<ISLSet>setDimNames(this.getOutset(domains), ISLUtil.Dims.OUT, _function);
  }

  private String indexName(final int i) {
    if ((i == 0)) {
      return "tw";
    } else {
      int _nbOutputs = this.tileDimsMap.getNbOutputs();
      boolean _lessThan = (i < _nbOutputs);
      if (_lessThan) {
        return ("t" + Integer.valueOf(i));
      } else {
        int _nbOutputs_1 = this.tileDimsMap.getNbOutputs();
        int _minus = (i - _nbOutputs_1);
        return ("c" + Integer.valueOf(_minus));
      }
    }
  }

  public WavefrontSequencedTiler(final List<Integer> tileSizes, final Scheduler scheduler, final int startDim, final int endDim) {
    this(tileSizes, scheduler.getMaps().getMaps().get(0).getRange().getSpace(), startDim, endDim);
  }
}
