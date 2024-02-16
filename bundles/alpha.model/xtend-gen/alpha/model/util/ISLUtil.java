package alpha.model.util;

import alpha.model.matrix.MatrixOperations;
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicMap;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLTerm;
import java.util.List;
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
    final ISLPWQPolynomial pwqp = ISLUtil.card(set);
    final int nbParams = pwqp.getSpace().getNbParams();
    final Function1<ISLQPolynomialPiece, ISLQPolynomial> _function = (ISLQPolynomialPiece it) -> {
      return it.getQp();
    };
    final Function1<ISLQPolynomial, Integer> _function_1 = (ISLQPolynomial o) -> {
      Integer _xblockexpression = null;
      {
        final Function1<ISLTerm, Integer> _function_2 = (ISLTerm t) -> {
          final Function1<Integer, Integer> _function_3 = (Integer i) -> {
            return Integer.valueOf(t.getExponent(ISLDimType.isl_dim_param, (i).intValue()));
          };
          final Function2<Integer, Integer, Integer> _function_4 = (Integer v1, Integer v2) -> {
            return Integer.valueOf(((v1).intValue() + (v2).intValue()));
          };
          return IterableExtensions.<Integer>reduce(IterableExtensions.<Integer, Integer>map(new ExclusiveRange(0, nbParams, true), _function_3), _function_4);
        };
        final List<Integer> termDegrees = ListExtensions.<ISLTerm, Integer>map(o.getTerms(), _function_2);
        final Function2<Integer, Integer, Integer> _function_3 = (Integer v1, Integer v2) -> {
          Integer _xifexpression = null;
          boolean _greaterThan = (v1.compareTo(v2) > 0);
          if (_greaterThan) {
            _xifexpression = v1;
          } else {
            _xifexpression = v2;
          }
          return _xifexpression;
        };
        final Integer maxDegree = IterableExtensions.<Integer>reduce(termDegrees, _function_3);
        _xblockexpression = maxDegree;
      }
      return _xblockexpression;
    };
    final List<Integer> degrees = ListExtensions.<ISLQPolynomial, Integer>map(ListExtensions.<ISLQPolynomialPiece, ISLQPolynomial>map(pwqp.getPieces(), _function), _function_1);
    final Function2<Integer, Integer, Integer> _function_2 = (Integer v1, Integer v2) -> {
      Integer _xifexpression = null;
      boolean _greaterThan = (v1.compareTo(v2) > 0);
      if (_greaterThan) {
        _xifexpression = v1;
      } else {
        _xifexpression = v2;
      }
      return _xifexpression;
    };
    final Integer dim = IterableExtensions.<Integer>reduce(degrees, _function_2);
    return dim;
  }

  public static ISLPWQPolynomial card(final ISLSet set) {
    return BarvinokBindings.card(set);
  }
}
