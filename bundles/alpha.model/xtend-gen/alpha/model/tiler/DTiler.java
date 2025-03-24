package alpha.model.tiler;

import alpha.model.scheduler.Scheduler;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class DTiler implements Tiler {
  private ISLMap tileMap;

  private int startDim;

  private int endDim;

  /**
   * Creates a D-Tiler with rectangular tiles of the given size,
   * from the start to the end dimension, inclusive.
   */
  public DTiler(final List<Integer> tileSizes, final ISLSpace scheduleSpace, final int startDim, final int endDim) {
    final int scheduleDim = scheduleSpace.dim(ISLDimType.isl_dim_out);
    final int bandDim = ((endDim - startDim) + 1);
    this.argumentCheck(tileSizes, scheduleSpace, startDim, endDim, scheduleDim, bandDim);
    final ISLSpace space = scheduleSpace.copy();
    ArrayList<ISLAff> affs = new ArrayList<ISLAff>();
    for (int i = 0; (i < scheduleDim); i++) {
      {
        ISLAff aff = ISLAff.buildVarOnDomain(space.copy().toLocalSpace(), ISLDimType.isl_dim_out, i);
        if (((i >= startDim) && (i <= endDim))) {
          aff = aff.scaleDown((tileSizes.get((i - startDim))).intValue()).floor();
        }
        affs.add(aff);
      }
    }
    for (int i = 0; (i < bandDim); i++) {
      {
        ISLAff aff = ISLAff.buildVarOnDomain(space.copy().toLocalSpace(), ISLDimType.isl_dim_out, (i + startDim));
        affs.add(aff);
      }
    }
    this.startDim = startDim;
    this.endDim = endDim;
    this.tileMap = ISLUtil.convertToMultiAff(affs).toMap();
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
  public ISLMap getTileMap() {
    return this.tileMap.copy();
  }

  @Override
  public Set<Integer> getTiledDims() {
    return IntStream.rangeClosed(this.startDim, this.endDim).boxed().collect(Collectors.<Integer>toSet());
  }

  @Override
  public ISLSet getApproximateOutset(final ISLSet domain) {
    return this.getOutset(domain);
  }

  /**
   * Uses implicit ISL methods to get the exact output.
   * This takes exponential time in the worst case.
   */
  public ISLSet getOutset(final ISLSet domain) {
    ISLSet _apply = domain.copy().apply(this.getTileMap());
    int _nbOutputs = this.tileMap.getNbOutputs();
    int _minus = (_nbOutputs - this.endDim);
    int _minus_1 = (_minus - 1);
    return _apply.projectOut(ISLDimType.isl_dim_out, (this.endDim + 1), _minus_1).projectOut(ISLDimType.isl_dim_out, 0, this.startDim);
  }
}
