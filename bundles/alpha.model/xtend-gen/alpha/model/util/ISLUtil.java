package alpha.model.util;

import alpha.model.matrix.MatrixOperations;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicMap;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
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
   * Creates an ISLAff from a string
   */
  public static ISLAff toISLAff(final String descriptor) {
    return ISLAff.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Creates an ISLMultiAff from a string
   */
  public static ISLMultiAff toISLMultiAff(final String descriptor) {
    return ISLMultiAff.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Creates an ISLConstraint from a string
   */
  public static ISLConstraint toISLConstraint(final String descriptor) {
    try {
      ISLConstraint _xblockexpression = null;
      {
        final ISLBasicSet set = ISLBasicSet.buildFromString(ISLContext.getInstance(), descriptor);
        int _size = set.getConstraints().size();
        boolean _notEquals = (_size != 1);
        if (_notEquals) {
          throw new Exception("Cannot create an ISLConstraint from a set with multiple constraints");
        }
        _xblockexpression = set.getConstraintAt(0);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Creates an ISLPWQPolynomial from a string
   */
  public static ISLPWQPolynomial toISLPWQPolynomial(final String descriptor) {
    return ISLPWQPolynomial.buildFromString(ISLContext.getInstance(), descriptor);
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

  /**
   * Checks if this is a function from an empty domain to an empty range.
   */
  public static boolean isNoneToNone(final ISLMultiAff aff) {
    return ((aff.getNbInputs() == 0) && (aff.getNbOutputs() == 0));
  }

  public static boolean isTrivial(final ISLBasicSet set) {
    return ISLUtil.isTrivial(set.copy().toSet());
  }

  public static boolean isTrivial(final ISLSet set) {
    boolean _xblockexpression = false;
    {
      final ISLAff zero = ISLAff.buildZero(set.getSpace().copy().toLocalSpace());
      int _nbIndices = set.getSpace().getNbIndices();
      final Function1<Integer, ISLConstraint> _function = (Integer i) -> {
        return zero.copy().setCoefficient(ISLDimType.isl_dim_in, (i).intValue(), 1).toEqualityConstraint();
      };
      final Iterable<ISLConstraint> constraints = IterableExtensions.<Integer, ISLConstraint>map(new ExclusiveRange(0, _nbIndices, true), _function);
      final Function2<ISLSet, ISLConstraint, ISLSet> _function_1 = (ISLSet s, ISLConstraint c) -> {
        return s.addConstraint(c);
      };
      final ISLSet origin = IterableExtensions.<ISLConstraint, ISLSet>fold(constraints, ISLSet.buildUniverse(set.getSpace().copy()), _function_1);
      _xblockexpression = set.copy().subtract(origin).isEmpty();
    }
    return _xblockexpression;
  }

  public static boolean isNonTrivial(final ISLSet set) {
    boolean _isTrivial = ISLUtil.isTrivial(set);
    return (!_isTrivial);
  }

  /**
   * Returns true if c is effectively saturated per Theorem 1 in GR06, and false otherwise
   */
  public static boolean isEffectivelySaturated(final ISLConstraint c, final ISLBasicSet P) {
    boolean _isEquality = c.isEquality();
    if (_isEquality) {
      return true;
    }
    final Function1<ISLConstraint, Long> _function = (ISLConstraint it) -> {
      return Long.valueOf(it.getConstant());
    };
    final Function1<Long, Long> _function_1 = (Long v) -> {
      Long _xifexpression = null;
      if (((v).longValue() < 0)) {
        _xifexpression = Long.valueOf(((-1) * (v).longValue()));
      } else {
        _xifexpression = v;
      }
      return _xifexpression;
    };
    final Function2<Long, Long, Long> _function_2 = (Long v1, Long v2) -> {
      return Long.valueOf(((v1).longValue() + (v2).longValue()));
    };
    final int tau = IterableExtensions.<Long>reduce(ListExtensions.<Long, Long>map(ListExtensions.<ISLConstraint, Long>map(P.getConstraints(), _function), _function_1), _function_2).intValue();
    ISLAff _negate = c.getAff().negate();
    int _intValue = Long.valueOf(c.getConstant()).intValue();
    int _plus = (_intValue + tau);
    final ISLBasicSet cPrime = _negate.setConstant(_plus).toInequalityConstraint().toBasicSet();
    return cPrime.intersect(P.copy()).isEqual(P.copy());
  }

  /**
   * Converts a constraint into an equality constraint with the same coefficients and constant.
   */
  public static ISLConstraint toEqualityConstraint(final ISLConstraint constraint) {
    final ISLSpace space = constraint.getSpace();
    ISLConstraint equality = ISLConstraint.buildEquality(space.copy());
    final List<ISLDimType> dimTypes = Collections.<ISLDimType>unmodifiableList(CollectionLiterals.<ISLDimType>newArrayList(ISLDimType.isl_dim_param, ISLDimType.isl_dim_in, ISLDimType.isl_dim_out, ISLDimType.isl_dim_div));
    for (final ISLDimType dimType : dimTypes) {
      {
        final int count = space.dim(dimType);
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, count, true);
        for (final Integer i : _doubleDotLessThan) {
          {
            final ISLVal coeff = constraint.getCoefficientVal(dimType, (i).intValue());
            equality = equality.setCoefficient(dimType, (i).intValue(), coeff);
          }
        }
      }
    }
    equality = equality.setConstant(constraint.getConstant());
    return equality;
  }

  /**
   * Given the ISLAff of an effectively saturated constraint return a long[] of the linear part
   * the first non-zero value is guaranteed to be positive
   */
  public static long[] toLinearUnitVector(final ISLAff aff) {
    int _nbParams = aff.getNbParams();
    int _nbInputs = aff.getNbInputs();
    final int constantCol = (_nbParams + _nbInputs);
    final long[] vec = DomainOperations.toISLEqualityMatrix(aff.toEqualityConstraint().toBasicSet()).dropCols(constantCol, 1).toLongMatrix()[0];
    final Function1<Long, Boolean> _function = (Long v) -> {
      return Boolean.valueOf(((v).longValue() == 0));
    };
    final Iterable<Long> nonZeros = IterableExtensions.<Long>reject(((Iterable<Long>)Conversions.doWrapArray(vec)), _function);
    if (((IterableExtensions.size(nonZeros) > 0) && ((IterableExtensions.<Long>toList(nonZeros).get(0)).longValue() < 0))) {
      return MatrixOperations.scalarMultiplication(vec, (-1));
    }
    return vec;
  }

  /**
   * Determines the number of effective dimensions for the set.
   * For example, if the set represents a 2D object embedded in 3D space,
   * this will indicate that the set is 2D.
   */
  public static int dimensionality(final ISLSet set) {
    final Function1<ISLBasicSet, Integer> _function = (ISLBasicSet it) -> {
      return Integer.valueOf(ISLUtil.dimensionality(it));
    };
    return (int) IterableExtensions.<Integer>max(ListExtensions.<ISLBasicSet, Integer>map(set.copy().computeDivs().getBasicSets(), _function));
  }

  public static int dimensionality(final ISLBasicSet set) {
    boolean _isEmpty = set.isEmpty();
    if (_isEmpty) {
      return 0;
    }
    final Function1<ISLConstraint, Boolean> _function = (ISLConstraint c) -> {
      return Boolean.valueOf(c.involvesDims(ISLDimType.isl_dim_out, 0, set.getSpace().getNbOutputs()));
    };
    final Function1<ISLConstraint, Boolean> _function_1 = (ISLConstraint c) -> {
      return Boolean.valueOf(ISLUtil.isEffectivelySaturated(c, set));
    };
    final Function1<ISLConstraint, String> _function_2 = (ISLConstraint it) -> {
      return ((List<Long>)Conversions.doWrapArray(ISLUtil.toLinearUnitVector(it.getAff()))).toString();
    };
    final int effectivelySaturatedCount = IterableExtensions.<String>toSet(IterableExtensions.<ISLConstraint, String>map(IterableExtensions.<ISLConstraint>filter(IterableExtensions.<ISLConstraint>filter(set.getConstraints(), _function), _function_1), _function_2)).size();
    int _nbIndices = set.getNbIndices();
    return (_nbIndices - effectivelySaturatedCount);
  }

  /**
   * Returns the ISLBasicSet characterizing the null space of the multiAff
   */
  public static ISLSet nullSpace(final ISLMultiAff maff) {
    final Function2<ISLBasicSet, ISLAff, ISLBasicSet> _function = (ISLBasicSet ret, ISLAff c) -> {
      return ret.addConstraint(c.toEqualityConstraint());
    };
    return IterableExtensions.<ISLAff, ISLBasicSet>fold(maff.getAffs(), 
      ISLBasicSet.buildUniverse(maff.getSpace().domain().copy()), _function).toSet();
  }

  /**
   * Returns true if the set is a lower dimensional polyhedron embedded in a higher
   * dimension space, or false otherwise
   */
  public static boolean isEmbedding(final ISLSet set) {
    boolean _xblockexpression = false;
    {
      final int nbIndices = set.dim(ISLDimType.isl_dim_set);
      int _dimensionality = ISLUtil.dimensionality(set);
      _xblockexpression = (_dimensionality < nbIndices);
    }
    return _xblockexpression;
  }
}
