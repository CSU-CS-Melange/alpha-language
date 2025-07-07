package alpha.model.tiler;

import alpha.model.scheduler.Scheduler;
import alpha.model.util.ISLUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class DTiler implements Tiler {
  protected ISLMap tileMap;

  private int startDim;

  private int endDim;

  private List<Integer> tileSizes;

  /**
   * Creates a D-Tiler with rectangular tiles of the given size,
   * from the start to the end dimension, inclusive.
   */
  public DTiler(final List<Integer> tileSizes, final ISLSpace scheduleSpace, final int startDim, final int endDim) {
    this.tileSizes = tileSizes;
    this.startDim = startDim;
    this.endDim = endDim;
    final int scheduleDim = scheduleSpace.dim(ISLDimType.isl_dim_out);
    final int bandDim = ((endDim - startDim) + 1);
    this.argumentCheck(tileSizes, scheduleSpace, startDim, endDim, scheduleDim, bandDim);
    final ISLSpace space = scheduleSpace.copy();
    final Function1<Integer, ISLAff> _function = (Integer i) -> {
      return ISLAff.buildVarOnDomain(space.copy().toLocalSpace(), ISLUtil.Dims.OUT, (i).intValue()).scaleDown((tileSizes.get(((i).intValue() - startDim))).intValue()).floor();
    };
    Iterable<ISLAff> tileAffs = IterableExtensions.<Integer, ISLAff>map(new IntegerRange(startDim, endDim), _function);
    final Function1<Integer, ISLAff> _function_1 = (Integer i) -> {
      return ISLAff.buildVarOnDomain(space.copy().toLocalSpace(), ISLUtil.Dims.OUT, (i).intValue());
    };
    Iterable<ISLAff> iterAffs = IterableExtensions.<Integer, ISLAff>map(new ExclusiveRange(0, scheduleDim, true), _function_1);
    this.tileMap = ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(Iterables.<ISLAff>concat(tileAffs, iterAffs))).toMap();
  }

  public void argumentCheck(final List<Integer> tileSizes, final ISLSpace scheduleSpace, final int startDim, final int endDim, final int scheduleDim, final int bandDim) {
    final Function1<Integer, Boolean> _function = (Integer size) -> {
      return Boolean.valueOf(((size).intValue() <= 0));
    };
    boolean _exists = IterableExtensions.<Integer>exists(tileSizes, _function);
    if (_exists) {
      throw new IllegalArgumentException("Tiles cannot have non-positive size.");
    }
    if (((((startDim < 0) || (endDim < 0)) || (startDim >= scheduleDim)) || (endDim >= scheduleDim))) {
      throw new IllegalArgumentException("Tiled dimensions are out of range.");
    }
    if ((startDim > endDim)) {
      throw new IllegalArgumentException("endDim must be greater than startDim.");
    }
    int _size = tileSizes.size();
    boolean _notEquals = (_size != bandDim);
    if (_notEquals) {
      throw new IllegalArgumentException("The size of tileSizes must match the number of tiled dimensions.");
    }
  }

  public DTiler(final List<Integer> tileSizes, final Scheduler scheduler, final int startDim, final int endDim) {
    this(tileSizes, scheduler.getMaps().getMaps().get(0).getRange().getSpace(), startDim, endDim);
  }

  @Override
  public ISLUnionMap tileSchedule(final ISLUnionMap maps) {
    return maps.applyRange(this.tileMap.copy().toUnionMap());
  }

  @Override
  public ISLMap tileSchedule(final ISLMap map) {
    return map.applyRange(this.tileMap.copy());
  }

  @Override
  public ISLMap getUntileMap() {
    ISLMap _copy = this.tileMap.copy();
    int _size = this.getTiledDims().size();
    int _dim = this.tileMap.dim(ISLDimType.isl_dim_out);
    int _size_1 = this.getTiledDims().size();
    int _minus = (_dim - _size_1);
    return _copy.projectOut(
      ISLDimType.isl_dim_out, _size, _minus).reverse();
  }

  @Override
  public Set<Integer> getTiledDims() {
    return IntStream.rangeClosed(this.startDim, this.endDim).boxed().collect(Collectors.<Integer>toSet());
  }

  @Override
  public ISLSet getApproximateOutset(final ISLUnionSet domains) {
    return this.getOutset(domains);
  }

  @Override
  public boolean fixedTileSizes() {
    return true;
  }

  /**
   * Uses implicit ISL methods to get the exact output.
   * This takes exponential time in the worst case.
   */
  public ISLSet getOutset(final ISLUnionSet domains) {
    final Function1<ISLSet, ISLSet> _function = (ISLSet it) -> {
      return it.clearTupleName();
    };
    final Function1<ISLSet, ISLSet> _function_1 = (ISLSet it) -> {
      return it.apply(this.tileMap.copy());
    };
    final Function1<ISLSet, ISLSet> _function_2 = (ISLSet it) -> {
      int _nbOutputs = this.tileMap.getNbOutputs();
      int _minus = (_nbOutputs - this.endDim);
      int _minus_1 = (_minus - 1);
      return it.projectOut(ISLUtil.Dims.OUT, (this.endDim + 1), _minus_1);
    };
    final Function1<ISLSet, ISLSet> _function_3 = (ISLSet it) -> {
      return it.projectOut(ISLUtil.Dims.OUT, 0, this.startDim);
    };
    final Function2<ISLSet, ISLSet, ISLSet> _function_4 = (ISLSet a, ISLSet b) -> {
      return a.union(b);
    };
    return IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLSet, ISLSet>map(ListExtensions.<ISLSet, ISLSet>map(ListExtensions.<ISLSet, ISLSet>map(ListExtensions.<ISLSet, ISLSet>map(domains.copy().getSets(), _function), _function_1), _function_2), _function_3), _function_4).simpleHull().toSet();
  }

  @Override
  public int getTileSize(final int dim) {
    return (this.tileSizes.get((dim - this.startDim))).intValue();
  }
}
