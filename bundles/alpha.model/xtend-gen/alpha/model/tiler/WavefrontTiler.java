package alpha.model.tiler;

import alpha.model.scheduler.Scheduler;
import alpha.model.util.ISLUtil;
import alpha.model.util.ISLValUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class WavefrontTiler extends DTiler {
  public WavefrontTiler(final List<Integer> tileSizes, final ISLSpace scheduleSpace, final int startDim, final int endDim) {
    super(tileSizes, scheduleSpace, startDim, endDim);
  }

  /**
   * Converts a d dimensional schedule map to a 2d dimensional wavefront tile map
   */
  @Override
  public ISLUnionMap tileSchedule(final ISLUnionMap umap) {
    final Function1<ISLMap, ISLMap> _function = (ISLMap it) -> {
      return this.tileSchedule(it);
    };
    ISLUnionMap tiledMaps = ISLUtil.convertToUnionMap(ListExtensions.<ISLMap, ISLMap>map(umap.getMaps(), _function));
    return tiledMaps;
  }

  /**
   * Converts a single variable's d dimensional schedule map to a 2d dimensional wavefront tile map
   */
  @Override
  public ISLMap tileSchedule(final ISLMap map) {
    final Function<Integer, String> _function = (Integer it) -> {
      return this.indexName((it).intValue());
    };
    return ISLUtil.<ISLMap>setDimNames(map.copy().applyRange(this.getTileMaff().toMap()).rangeProduct(map.copy()).flatten(), ISLUtil.Dims.OUT, _function);
  }

  /**
   * Maps a point in the schedule to its corresponding tile index.
   */
  private ISLMultiAff getTileMaff() {
    final ISLMultiAff maff = this.getPreTileMaff();
    int _size = this.tileSizes.size();
    final Function1<Integer, ISLAff> _function = (Integer it) -> {
      return maff.getAff((it).intValue()).scaleDown((this.tileSizes.get((it).intValue())).intValue()).floor();
    };
    return this.getWavefrontMaff().pullback(
      ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(IterableExtensions.<Integer, ISLAff>map(new ExclusiveRange(0, _size, true), _function))));
  }

  @Override
  public ISLSet getApproximateOutset(final ISLUnionSet ranges) {
    final Function1<ISLSet, ISLSet> _function = (ISLSet it) -> {
      return it.clearTupleName();
    };
    final Function2<ISLSet, ISLSet, ISLSet> _function_1 = (ISLSet a, ISLSet b) -> {
      return a.union(b);
    };
    final Function1<ISLBasicSet, ISLSet> _function_2 = (ISLBasicSet it) -> {
      return this.applyWavefront(it);
    };
    final Function2<ISLSet, ISLSet, ISLSet> _function_3 = (ISLSet a, ISLSet b) -> {
      return a.union(b);
    };
    final ISLSet outset = IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLBasicSet, ISLSet>map(IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLSet, ISLSet>map(ranges.copy().getSets(), _function), _function_1).apply(this.getPreTileMaff().toMap()).getBasicSets(), _function_2), _function_3);
    return outset;
  }

  private ISLSet applyWavefront(final ISLBasicSet bset) {
    final Function1<ISLConstraint, ISLConstraint> _function = (ISLConstraint it) -> {
      return this.scaleDownConstraint(it);
    };
    final Function1<ISLConstraint, Iterable<ISLConstraint>> _function_1 = (ISLConstraint it) -> {
      return this.extendConstraint(it);
    };
    final Function<Integer, String> _function_2 = (Integer it) -> {
      return this.indexName((it).intValue());
    };
    return ISLUtil.<ISLSet>setDimNames(ISLUtil.convertToSet(IterableExtensions.<ISLConstraint, ISLConstraint>flatMap(ListExtensions.<ISLConstraint, ISLConstraint>map(bset.getConstraints(), _function), _function_1)).apply(this.getWavefrontMaff().toMap()), ISLUtil.Dims.OUT, _function_2);
  }

  private ISLMultiAff getWavefrontMaff() {
    int _size = this.tileSizes.size();
    final Function1<Integer, ISLAff> _function = (Integer it) -> {
      return ISLAff.buildVarOnDomain(this.getPreTileMaff().toMap().getRange().getSpace().toLocalSpace(), ISLUtil.Dims.SET, (it).intValue());
    };
    Iterable<ISLAff> idAffs = IterableExtensions.<Integer, ISLAff>map(new ExclusiveRange(0, _size, true), _function);
    final Function2<ISLAff, ISLAff, ISLAff> _function_1 = (ISLAff a, ISLAff b) -> {
      return a.copy().add(b.copy());
    };
    ISLAff waveAff = IterableExtensions.<ISLAff>reduce(idAffs, _function_1);
    List<ISLAff> _list = IterableExtensions.<ISLAff>toList(idAffs);
    int _size_1 = IterableExtensions.size(idAffs);
    int _minus = (_size_1 - 1);
    List<ISLAff> _subList = _list.subList(0, _minus);
    return ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(Iterables.<ISLAff>concat(Collections.<ISLAff>unmodifiableList(CollectionLiterals.<ISLAff>newArrayList(waveAff)), _subList)));
  }

  /**
   * A map that isolates the dimensions in the tiling band.
   * Will be the identity map in the case of a full tiling.
   */
  private ISLMultiAff getPreTileMaff() {
    final Function1<Integer, ISLAff> _function = (Integer it) -> {
      return ISLAff.buildVarOnDomain(this.scheduleSpace.copy().toLocalSpace(), ISLUtil.Dims.SET, (it).intValue());
    };
    Iterable<ISLAff> idAffs = IterableExtensions.<Integer, ISLAff>map(new IntegerRange(this.startDim, this.endDim), _function);
    return ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(idAffs));
  }

  @Override
  public ISLUnionMap getParameterizedIterators(final ISLUnionMap maps) {
    ISLUnionMap _xblockexpression = null;
    {
      final Function<Integer, String> _function = (Integer it) -> {
        return this.indexName((it).intValue());
      };
      final ISLSet tileConstraintSet = ISLUtil.<ISLMap>setDimNames(this.getTileMaff().toMap(), ISLUtil.Dims.OUT, _function).moveDims(ISLUtil.Dims.PARAM, 0, ISLUtil.Dims.OUT, 0, this.tileSizes.size()).getDomain();
      final Function1<ISLMap, ISLMap> _function_1 = (ISLMap it) -> {
        return it.alignParams(tileConstraintSet.getSpace().copy());
      };
      final Function1<ISLMap, ISLMap> _function_2 = (ISLMap it) -> {
        return it.intersectRange(tileConstraintSet.copy());
      };
      final Function1<ISLMap, ISLMap> _function_3 = (ISLMap it) -> {
        final Function<Integer, String> _function_4 = (Integer it_1) -> {
          return ("c" + it_1);
        };
        return ISLUtil.<ISLMap>setDimNames(it, ISLUtil.Dims.OUT, _function_4);
      };
      _xblockexpression = ISLUtil.convertToUnionMap(IterableExtensions.<ISLMap>toList(ListExtensions.<ISLMap, ISLMap>map(ListExtensions.<ISLMap, ISLMap>map(ListExtensions.<ISLMap, ISLMap>map(maps.getMaps(), _function_1), _function_2), _function_3)));
    }
    return _xblockexpression;
  }

  /**
   * Transforms a constraint such that it includes the tile origin of any tiles it originally touched.
   */
  private Iterable<ISLConstraint> extendConstraint(final ISLConstraint con) {
    boolean _isEquality = con.isEquality();
    if (_isEquality) {
      final Function1<ISLConstraint, Iterable<ISLConstraint>> _function = (ISLConstraint it) -> {
        return this.extendConstraint(it);
      };
      return IterableExtensions.<ISLConstraint, ISLConstraint>flatMap(ISLUtil.toInequalityConstraints(con), _function);
    }
    int _nbDims = con.getNbDims(ISLUtil.Dims.SET);
    final Function1<Integer, ISLVal> _function_1 = (Integer it) -> {
      return con.getCoefficientVal(ISLUtil.Dims.SET, (it).intValue());
    };
    final Function1<ISLVal, ISLVal> _function_2 = (ISLVal it) -> {
      return it.abs();
    };
    final Iterable<ISLVal> betas = IterableExtensions.<ISLVal, ISLVal>map(IterableExtensions.<Integer, ISLVal>map(new ExclusiveRange(0, _nbDims, true), _function_1), _function_2);
    int _nbDims_1 = con.getNbDims(ISLUtil.Dims.SET);
    final Function1<Integer, ISLVal> _function_3 = (Integer it) -> {
      return ((ISLVal[])Conversions.unwrapArray(betas, ISLVal.class))[(it).intValue()];
    };
    final Function2<ISLVal, ISLVal, ISLVal> _function_4 = (ISLVal a, ISLVal b) -> {
      return ISLValUtil.operator_plus(a, b);
    };
    final ISLVal bias = IterableExtensions.<ISLVal>reduce(IterableExtensions.<Integer, ISLVal>map(new ExclusiveRange(0, _nbDims_1, true), _function_3), _function_4).add(con.getConstantVal());
    ISLConstraint _setConstant = con.setConstant(bias);
    return Collections.<ISLConstraint>unmodifiableList(CollectionLiterals.<ISLConstraint>newArrayList(_setConstant));
  }

  private ISLConstraint scaleDownConstraint(final ISLConstraint con) {
    int _nbDims = con.getNbDims(ISLUtil.Dims.SET);
    final Function2<ISLConstraint, Integer, ISLConstraint> _function = (ISLConstraint c, Integer i) -> {
      ISLVal _coefficientVal = c.getCoefficientVal(ISLUtil.Dims.SET, (i).intValue());
      Integer _get = this.tileSizes.get((i).intValue());
      ISLVal _multiply = ISLValUtil.operator_multiply(_coefficientVal, _get);
      return c.setCoefficient(ISLUtil.Dims.SET, (i).intValue(), _multiply);
    };
    return IterableExtensions.<Integer, ISLConstraint>fold(new ExclusiveRange(0, _nbDims, true), con.copy(), _function);
  }

  public WavefrontTiler(final List<Integer> tileSizes, final Scheduler scheduler, final int startDim, final int endDim) {
    this(tileSizes, scheduler.getMaps().getMaps().get(0).getRange().getSpace(), startDim, endDim);
  }
}
