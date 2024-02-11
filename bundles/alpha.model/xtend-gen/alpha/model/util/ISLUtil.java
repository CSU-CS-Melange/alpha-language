package alpha.model.util;

import alpha.model.matrix.MatrixOperations;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicMap;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.List;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class ISLUtil {
  /**
   * Creates an ISLBasicSet from a string
   */
  public static ISLBasicSet toISLBasicSet(final String descriptor) {
    return ISLBasicSet.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Creates an ISLSet from a string
   */
  public static ISLSet toISLSet(final String descriptor) {
    return ISLSet.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Creates an ISLBasicMap from a string
   */
  public static ISLBasicMap toISLBasicMap(final String descriptor) {
    return ISLBasicMap.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Creates an ISLBasicSet from a string
   */
  public static ISLAff toISLAff(final String descriptor) {
    return ISLAff.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Transposes an ISLMatrix
   */
  public static ISLMatrix transpose(final ISLMatrix matrix) {
    return ISLMatrix.buildFromLongMatrix(MatrixOperations.transpose(matrix.toLongMatrix()));
  }

  /**
   * Returns the integer point closest to the origin in set without parameter context
   */
  public static long[] integerPointClosestToOrigin(final ISLBasicSet set) {
    List<Long> _coordinates = set.copy().samplePoint().getCoordinates();
    int _nbParams = set.getNbParams();
    int _nbParams_1 = set.getNbParams();
    int _nbIndices = set.getNbIndices();
    int _plus = (_nbParams_1 + _nbIndices);
    return ((long[])Conversions.unwrapArray(_coordinates.subList(_nbParams, _plus), long.class));
  }

  public static boolean isTrivial(final ISLBasicSet set) {
    boolean _xblockexpression = false;
    {
      ISLBasicSet origin = ISLBasicSet.buildUniverse(set.getSpace().copy());
      int _nbIndices = set.getSpace().getNbIndices();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbIndices, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          final ISLAff aff = ISLAff.buildZero(set.getSpace().copy().toLocalSpace());
          origin = origin.addConstraint(aff.setCoefficient(ISLDimType.isl_dim_in, (i).intValue(), 1).toEqualityConstraint());
        }
      }
      _xblockexpression = set.copy().toSet().subtract(origin.toSet()).isEmpty();
    }
    return _xblockexpression;
  }

  public static boolean isTrivial(final ISLSet set) {
    boolean _xblockexpression = false;
    {
      ISLSet origin = ISLSet.buildUniverse(set.getSpace().copy());
      int _nbIndices = set.getSpace().getNbIndices();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbIndices, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          final ISLAff aff = ISLAff.buildZero(set.getSpace().copy().toLocalSpace());
          origin = origin.addConstraint(aff.setCoefficient(ISLDimType.isl_dim_in, (i).intValue(), 1).toEqualityConstraint());
        }
      }
      _xblockexpression = set.subtract(origin).isEmpty();
    }
    return _xblockexpression;
  }

  public static Integer dimensionality(final ISLSet set) {
    final Function1<ISLBasicSet, Integer> _function = (ISLBasicSet bset) -> {
      return Integer.valueOf(ISLUtil.dimensionality(bset));
    };
    final Function2<Integer, Integer, Integer> _function_1 = (Integer d1, Integer d2) -> {
      Integer _xifexpression = null;
      boolean _greaterThan = (d1.compareTo(d2) > 0);
      if (_greaterThan) {
        _xifexpression = d1;
      } else {
        _xifexpression = d2;
      }
      return _xifexpression;
    };
    return IterableExtensions.<Integer>reduce(ListExtensions.<ISLBasicSet, Integer>map(set.getBasicSets(), _function), _function_1);
  }

  public static int dimensionality(final ISLBasicSet set) {
    final ISLBasicSet setNoRedundancies = set.copy().removeRedundancies();
    final ISLMatrix eqMatrix = DomainOperations.toISLEqualityMatrix(setNoRedundancies);
    int _nbRows = eqMatrix.getNbRows();
    final Function1<Integer, Boolean> _function = (Integer row) -> {
      return Boolean.valueOf(ISLUtil.constraintInvolvesIndex(eqMatrix, (row).intValue(), set.getNbIndices()));
    };
    final Function2<ISLMatrix, Integer, ISLMatrix> _function_1 = (ISLMatrix mat, Integer row) -> {
      return mat.dropRows((row).intValue(), 1);
    };
    final int linearlyIndependentIndexEqualities = IterableExtensions.<Integer, ISLMatrix>fold(IterableExtensions.<Integer>reject(new ExclusiveRange(_nbRows, 0, false), _function), eqMatrix.copy(), _function_1).rank();
    ISLMatrix _iSLInequalityMatrix = DomainOperations.toISLInequalityMatrix(setNoRedundancies);
    int _nbParams = set.getNbParams();
    int _nbIndices = set.getNbIndices();
    int _plus = (_nbParams + _nbIndices);
    final ISLMatrix ineqMatrix = _iSLInequalityMatrix.dropCols(_plus, 1);
    int _nbRows_1 = ineqMatrix.getNbRows();
    final Function1<Integer, long[]> _function_2 = (Integer r) -> {
      return ineqMatrix.toLongMatrix()[(r).intValue()];
    };
    final List<long[]> ineqRows = IterableExtensions.<long[]>toList(IterableExtensions.<Integer, long[]>map(new ExclusiveRange(0, _nbRows_1, true), _function_2));
    int thickEqualities = 0;
    int _nbRows_2 = ineqMatrix.getNbRows();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbRows_2, true);
    for (final Integer r : _doubleDotLessThan) {
      {
        final String row = ((List<Long>)Conversions.doWrapArray(MatrixOperations.scalarMultiplication(ineqRows.get((r).intValue()), (-1)))).toString();
        int _nbRows_3 = ineqMatrix.getNbRows();
        final Function1<Integer, String> _function_3 = (Integer i) -> {
          return ((List<Long>)Conversions.doWrapArray(ineqRows.get((i).intValue()))).toString();
        };
        final Set<String> ineqRowsSet = IterableExtensions.<String>toSet(IterableExtensions.<Integer, String>map(new ExclusiveRange(((r).intValue() + 1), _nbRows_3, true), _function_3));
        boolean _contains = ineqRowsSet.contains(row);
        if (_contains) {
          int _thickEqualities = thickEqualities;
          thickEqualities = (_thickEqualities + 1);
        }
      }
    }
    int _nbIndices_1 = set.getNbIndices();
    int _minus = (_nbIndices_1 - linearlyIndependentIndexEqualities);
    return (_minus - thickEqualities);
  }

  private static boolean constraintInvolvesIndex(final ISLMatrix matrix, final int row, final int indexCount) {
    int _nbCols = matrix.getNbCols();
    final int endExclusive = (_nbCols - 1);
    final int start = (endExclusive - indexCount);
    final Function1<Integer, Boolean> _function = (Integer col) -> {
      long _element = matrix.getElement(row, (col).intValue());
      return Boolean.valueOf((_element != 0));
    };
    return IterableExtensions.<Integer>exists(new ExclusiveRange(start, endExclusive, true), _function);
  }
}
