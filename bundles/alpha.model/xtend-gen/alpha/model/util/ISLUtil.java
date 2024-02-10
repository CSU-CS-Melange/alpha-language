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
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

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
}
