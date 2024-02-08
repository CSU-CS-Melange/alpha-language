package alpha.model.util;

import alpha.model.matrix.MatrixOperations;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicMap;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class DomainOperations {
  public static ISLMatrix toISLEqualityMatrix(final ISLBasicMap bmap) {
    return bmap.getEqualityMatrix(
      ISLDimType.isl_dim_param, 
      ISLDimType.isl_dim_in, 
      ISLDimType.isl_dim_out, 
      ISLDimType.isl_dim_div, 
      ISLDimType.isl_dim_cst);
  }

  public static ISLMatrix toISLEqualityMatrix(final ISLMap map) {
    ISLMatrix _xblockexpression = null;
    {
      int _nbBasicMaps = map.getNbBasicMaps();
      boolean _notEquals = (_nbBasicMaps != 1);
      if (_notEquals) {
        throw new IllegalArgumentException("[DomainOperations] toISLEqualityMatrix assumes a single polyhedron.");
      }
      final ISLBasicMap bmap = map.getBasicMapAt(0);
      _xblockexpression = DomainOperations.toISLEqualityMatrix(bmap);
    }
    return _xblockexpression;
  }

  public static ISLMatrix toISLInequalityMatrix(final ISLBasicMap bmap) {
    return bmap.getInequalityMatrix(
      ISLDimType.isl_dim_param, 
      ISLDimType.isl_dim_in, 
      ISLDimType.isl_dim_out, 
      ISLDimType.isl_dim_div, 
      ISLDimType.isl_dim_cst);
  }

  public static ISLMatrix toISLInequalityMatrix(final ISLMap map) {
    ISLMatrix _xblockexpression = null;
    {
      int _nbBasicMaps = map.getNbBasicMaps();
      boolean _notEquals = (_nbBasicMaps != 1);
      if (_notEquals) {
        throw new IllegalArgumentException("[DomainOperations] toISLEqualityMatrix assumes a single polyhedron.");
      }
      final ISLBasicMap bmap = map.getBasicMapAt(0);
      _xblockexpression = DomainOperations.toISLInequalityMatrix(bmap);
    }
    return _xblockexpression;
  }

  public static ISLMatrix toISLEqualityMatrix(final ISLBasicSet bset) {
    return bset.getEqualityMatrix(
      ISLDimType.isl_dim_param, 
      ISLDimType.isl_dim_set, 
      ISLDimType.isl_dim_div, 
      ISLDimType.isl_dim_cst);
  }

  public static ISLMatrix toISLEqualityMatrix(final ISLSet set) {
    ISLMatrix _xblockexpression = null;
    {
      int _nbBasicSets = set.getNbBasicSets();
      boolean _notEquals = (_nbBasicSets != 1);
      if (_notEquals) {
        throw new IllegalArgumentException("[DomainOperations] toISLEqualityMatrix assumes a single polyhedron.");
      }
      final ISLBasicSet bset = set.getBasicSetAt(0);
      _xblockexpression = DomainOperations.toISLEqualityMatrix(bset);
    }
    return _xblockexpression;
  }

  public static ISLMatrix toISLInequalityMatrix(final ISLBasicSet bset) {
    return bset.getInequalityMatrix(
      ISLDimType.isl_dim_param, 
      ISLDimType.isl_dim_set, 
      ISLDimType.isl_dim_div, 
      ISLDimType.isl_dim_cst);
  }

  public static ISLMatrix toISLInequalityMatrix(final ISLSet set) {
    ISLMatrix _xblockexpression = null;
    {
      int _nbBasicSets = set.getNbBasicSets();
      boolean _notEquals = (_nbBasicSets != 1);
      if (_notEquals) {
        throw new IllegalArgumentException("[DomainOperations] toISLInequalityMatrix assumes a single polyhedron.");
      }
      final ISLBasicSet bset = set.getBasicSetAt(0);
      _xblockexpression = DomainOperations.toISLInequalityMatrix(bset);
    }
    return _xblockexpression;
  }

  public static long[][] kernelOfLinearPart(final ISLBasicSet bset) {
    int _dim = bset.dim(ISLDimType.isl_dim_div);
    boolean _notEquals = (_dim != 0);
    if (_notEquals) {
      throw new IllegalArgumentException("[DomainOperations] kernelOfLinearPart assumes a domain with out div dimensions.");
    }
    final ISLMatrix ineqMat = DomainOperations.toISLInequalityMatrix(bset);
    final ISLMatrix eqMat = DomainOperations.toISLEqualityMatrix(bset);
    final long[][] ineqArray = DomainOperations.toLongArray(ineqMat, true);
    final long[][] eqArray = DomainOperations.toLongArrayFromEqualityMatrix(eqMat, true);
    final long[][] array = MatrixOperations.rowBind(ineqArray, eqArray);
    return MatrixOperations.nullspace(array);
  }

  public static long[][] kernelOfLinearPart(final ISLSet set) {
    long[][] _xblockexpression = null;
    {
      int _nbBasicSets = set.getNbBasicSets();
      boolean _notEquals = (_nbBasicSets != 1);
      if (_notEquals) {
        throw new IllegalArgumentException("[DomainOperations] kernelOfLinearPart assumes a single polyhedron.");
      }
      final ISLBasicSet bset = set.getBasicSetAt(0);
      _xblockexpression = DomainOperations.kernelOfLinearPart(bset);
    }
    return _xblockexpression;
  }

  public static long[][] toLongArray(final ISLMatrix mat, final boolean linearPartOnly) {
    long[][] _xblockexpression = null;
    {
      ISLMatrix ret = mat;
      if (linearPartOnly) {
        int _nbCols = mat.getNbCols();
        int _minus = (_nbCols - 1);
        ret = ret.dropCols(_minus, 1);
      }
      _xblockexpression = ret.toLongMatrix();
    }
    return _xblockexpression;
  }

  public static long[][] toLongArrayFromEqualityMatrix(final ISLMatrix mat, final boolean linearPartOnly) {
    long[][] _xblockexpression = null;
    {
      final long[][] pos = DomainOperations.toLongArray(mat, linearPartOnly);
      int _length = pos.length;
      boolean _equals = (_length == 0);
      if (_equals) {
        return pos;
      }
      final long[][] array = MatrixOperations.rowBind(pos, MatrixOperations.scalarMultiplication(pos, (-1)));
      _xblockexpression = array;
    }
    return _xblockexpression;
  }

  /**
   * Returns an ISLBasicSet in the given space spanned by the basis vectors in kernel
   */
  public static ISLBasicSet toBasicSetFromKernel(final long[][] kernel, final ISLSpace space) {
    if (((kernel.length == 0) || ((kernel[0]).length == 0))) {
      return ISLBasicSet.buildUniverse(space.copy());
    }
    int _length = (kernel[0]).length;
    int _nbParams = space.getNbParams();
    int _nbIndices = space.getNbIndices();
    int _plus = (_nbParams + _nbIndices);
    boolean _notEquals = (_length != _plus);
    if (_notEquals) {
      throw new IllegalArgumentException("The kernel and space dimensions do not match");
    }
    final List<String> params = space.getParamNames();
    final List<String> indices = space.getIndexNames();
    final Function1<ISLAff, ISLConstraint> _function = (ISLAff aff) -> {
      return aff.toEqualityConstraint();
    };
    final Function2<ISLBasicSet, ISLConstraint, ISLBasicSet> _function_1 = (ISLBasicSet set, ISLConstraint constraint) -> {
      return set.addConstraint(constraint);
    };
    final ISLBasicSet set = IterableExtensions.<ISLConstraint, ISLBasicSet>fold(ListExtensions.<ISLAff, ISLConstraint>map(AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, kernel).getAffs(), _function), ISLBasicSet.buildUniverse(space.copy()), _function_1);
    return set;
  }
}
