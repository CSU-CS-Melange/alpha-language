package alpha.model.util;

import alpha.model.AlphaSystem;
import alpha.model.exception.CausalityViolationException;
import alpha.model.matrix.MatrixOperations;
import alpha.model.scheduler.ManualScheduler;
import alpha.model.scheduler.ScheduleVerifier;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLAffList;
import fr.irisa.cairn.jnimap.isl.ISLBasicMap;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWAff;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAffPiece;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLPoint;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class ISLUtil {
  /**
   * ISL DimType Shorthand
   */
  public static class Dims {
    public static ISLDimType CONST = ISLDimType.isl_dim_cst;

    public static ISLDimType PARAM = ISLDimType.isl_dim_param;

    public static ISLDimType IN = ISLDimType.isl_dim_in;

    public static ISLDimType OUT = ISLDimType.isl_dim_out;

    public static ISLDimType SET = ISLDimType.isl_dim_set;

    public static ISLDimType DIV = ISLDimType.isl_dim_div;

    public static ISLDimType ALL = ISLDimType.isl_dim_all;
  }

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
   * Creates an ISLMap from a string
   */
  public static ISLMap toISLMap(final String descriptor) {
    return ISLMap.buildFromString(ISLContext.getInstance(), descriptor);
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
   * Creates an ISLUnionMap from a string
   */
  public static ISLUnionMap toISLUnionMap(final String descriptor) {
    return ISLUnionMap.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Creates an ISLUnionSet from a string
   */
  public static ISLUnionSet toISLUnionSet(final String descriptor) {
    return ISLUnionSet.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Creates an ISLSchedule from a string
   */
  public static ISLSchedule toISLSchedule(final String descriptor) {
    return ISLSchedule.buildFromString(ISLContext.getInstance(), descriptor);
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
   * Determines the number of dimensions of the polyhedron proper.
   * For example, if the set represents a 2D object embedded in 3D space,
   * this will indicate that the set is 2D.
   * Paramentric dimension is not counted.
   */
  public static int polyDimension(final ISLSet set) {
    return ISLUtil.polyDimension(set.copy().simpleHull());
  }

  public static int polyDimension(final ISLBasicSet set) {
    boolean _isEmpty = set.isEmpty();
    if (_isEmpty) {
      return 0;
    }
    final Function1<ISLConstraint, Boolean> _function = (ISLConstraint it) -> {
      return Boolean.valueOf(it.involvesDims(ISLUtil.Dims.SET, 0, set.dim(ISLUtil.Dims.SET)));
    };
    final Function1<ISLConstraint, Boolean> _function_1 = (ISLConstraint it) -> {
      return Boolean.valueOf(it.isEquality());
    };
    final int flatDims = IterableExtensions.size(IterableExtensions.<ISLConstraint>filter(IterableExtensions.<ISLConstraint>filter(set.copy().detectEqualities().removeRedundancies().getConstraints(), _function), _function_1));
    int _dim = set.dim(ISLUtil.Dims.SET);
    return (_dim - flatDims);
  }

  /**
   * Determines the number of effective *unbounded* dimensions for the set.
   * dimensions along which a polyhedron has a bounded width are not considered.
   */
  public static int dimensionality(final ISLSet set) {
    return ISLUtil.dimensionality(set.copy().simpleHull());
  }

  public static int dimensionality(final ISLBasicSet set) {
    boolean _isEmpty = set.isEmpty();
    if (_isEmpty) {
      return 0;
    }
    final Function1<ISLConstraint, Boolean> _function = (ISLConstraint it) -> {
      return Boolean.valueOf(it.involvesDims(ISLDimType.isl_dim_out, 0, set.getSpace().getNbOutputs()));
    };
    final Function1<ISLConstraint, Boolean> _function_1 = (ISLConstraint it) -> {
      return Boolean.valueOf(ISLUtil.isEffectivelySaturated(it, set));
    };
    final Iterable<ISLConstraint> effectivelySaturatedConstraints = IterableExtensions.<ISLConstraint>filter(IterableExtensions.<ISLConstraint>filter(set.getConstraints(), _function), _function_1);
    final Function1<ISLConstraint, ISLConstraint> _function_2 = (ISLConstraint it) -> {
      return it.copy().setConstant(0);
    };
    final Function2<ISLBasicSet, ISLConstraint, ISLBasicSet> _function_3 = (ISLBasicSet s, ISLConstraint c) -> {
      return s.addConstraint(c);
    };
    final ISLBasicSet saturatedSpace = IterableExtensions.<ISLConstraint, ISLBasicSet>fold(IterableExtensions.<ISLConstraint, ISLConstraint>map(effectivelySaturatedConstraints, _function_2), ISLBasicSet.buildUniverse(set.getSpace().copy()), _function_3).projectOut(ISLUtil.Dims.PARAM, 0, set.dim(ISLUtil.Dims.PARAM));
    return ISLUtil.polyDimension(saturatedSpace);
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
   * Returns the linearly independent basis vectors of the (non-parametric) subspace in which a set lies
   * Basis vectors are given as ISLPoints
   */
  public static List<ISLPoint> getBasisVectors(final ISLSet set) {
    ArrayList<ISLPoint> vectors = new ArrayList<ISLPoint>();
    ISLSet workingSet = set.copy().affineHull().toSet();
    workingSet = workingSet.apply(ISLUtil.buildTranslationMaff(workingSet.copy().samplePoint()).toMap().reverse());
    final int dim = ISLUtil.polyDimension(workingSet);
    for (int i = 0; (i < dim); i++) {
      {
        final ISLPoint basisVector = workingSet.copy().getLexNextMap(set.dim(ISLDimType.isl_dim_out)).deltas().samplePoint();
        vectors.add(basisVector);
        workingSet = workingSet.intersect(ISLUtil.getOrthogonalPlane(basisVector.copy()));
      }
    }
    return vectors;
  }

  /**
   * Returns the ISLBasicSet that is the subspace spanned by a list of vectors
   * Vectors do not necessarily need to be linearly independent
   * but should be zero in the parameters
   */
  public static ISLSet getSpan(final Iterable<ISLPoint> basisVectors) {
    final Function1<ISLPoint, ISLSet> _function = (ISLPoint a) -> {
      return a.toSet();
    };
    final Function2<ISLSet, ISLSet, ISLSet> _function_1 = (ISLSet a, ISLSet b) -> {
      return a.union(b);
    };
    final ISLSet basisSet = IterableExtensions.<ISLSet>reduce(IterableExtensions.<ISLPoint, ISLSet>map(basisVectors, _function), _function_1);
    final ISLPoint zeroVector = ISLSet.buildUniverse(basisSet.getSpace().copy()).samplePoint();
    return basisSet.union(zeroVector.toSet()).affineHull().toSet();
  }

  public static ISLPoint affToVector(final ISLAff aff) {
    ISLPoint vector = ISLSet.buildUniverse(aff.getDomainSpace().copy()).samplePoint();
    for (int i = 0; (i < aff.dim(ISLDimType.isl_dim_in)); i++) {
      vector = vector.setCoordinate(
        ISLDimType.isl_dim_set, i, 
        aff.getCoefficientVal(ISLDimType.isl_dim_in, i));
    }
    return vector;
  }

  /**
   * Gets the n-1 dimensional plane (non-parametrically) orthogonal to a vector
   */
  public static ISLSet getOrthogonalPlane(final ISLPoint vector) {
    final ISLLocalSpace localSpace = vector.getSpace().copy().toLocalSpace();
    ISLAff projectAff = ISLAff.buildZero(localSpace.copy());
    for (int i = 0; (i < localSpace.dim(ISLDimType.isl_dim_out)); i++) {
      projectAff = projectAff.add(
        ISLAff.buildVarOnDomain(localSpace.copy(), ISLDimType.isl_dim_out, i).scale(
          vector.getCoordinateVal(ISLDimType.isl_dim_out, i)));
    }
    final ISLConstraint constraint = projectAff.toEqualityConstraint();
    return ISLSet.buildUniverse(vector.getSpace().copy()).addConstraint(constraint);
  }

  /**
   * Builds a maff that computes the vector projection along a vector
   */
  public static ISLMultiAff buildProjectionMaff(final ISLPoint vector) {
    final ISLLocalSpace localSpace = vector.getSpace().copy().toLocalSpace();
    ArrayList<ISLAff> rejectAffList = new ArrayList<ISLAff>();
    for (int i = 0; (i < localSpace.dim(ISLDimType.isl_dim_out)); i++) {
      {
        ISLAff rejectAff = ISLAff.buildZero(localSpace.copy());
        for (int j = 0; (j < localSpace.dim(ISLDimType.isl_dim_out)); j++) {
          rejectAff = rejectAff.add(
            ISLAff.buildVarOnDomain(localSpace.copy(), ISLDimType.isl_dim_out, j).scale(
              vector.getCoordinateVal(ISLDimType.isl_dim_out, j).copy()));
        }
        rejectAff = rejectAff.scale(vector.getCoordinateVal(ISLDimType.isl_dim_out, i).copy());
        rejectAffList.add(rejectAff);
      }
    }
    return ISLUtil.convertToMultiAff(rejectAffList);
  }

  /**
   * Builds a maff that computes the vector rejection along a vector
   * The vector rejection is the projection onto a plane orthogonal to the
   * given vector.
   */
  public static ISLMultiAff buildRejectionMaff(final ISLPoint vector) {
    return ISLUtil.toMultiAff(ISLSet.buildUniverse(vector.getSpace().copy()).identity()).sub(ISLUtil.buildProjectionMaff(vector));
  }

  /**
   * Returns a MultiAff that translates points along the given vector
   */
  public static ISLMultiAff buildTranslationMaff(final ISLPoint vector) {
    return ISLUtil.toMultiAff(ISLMap.buildFromDomainAndRange(
      ISLSet.buildUniverse(vector.getSpace().copy()), 
      vector.copy().toSet())).add(
      ISLUtil.toMultiAff(ISLSet.buildUniverse(vector.getSpace().copy()).identity()));
  }

  /**
   * Returns the parametrized box that bounds the given set.
   */
  public static ISLBasicSet boundingBox(final ISLSet set) {
    int _dim = set.dim(ISLDimType.isl_dim_set);
    final Function1<Integer, Iterable<ISLConstraint>> _function = (Integer dim) -> {
      Iterable<ISLConstraint> _xblockexpression = null;
      {
        final ISLPoint axis = ISLPoint.buildZero(set.getSpace().copy()).add(ISLDimType.isl_dim_set, (dim).intValue(), 1);
        final Function1<ISLConstraint, Boolean> _function_1 = (ISLConstraint it) -> {
          return Boolean.valueOf(it.involvesDims(ISLDimType.isl_dim_set, (dim).intValue(), 1));
        };
        _xblockexpression = IterableExtensions.<ISLConstraint>filter(set.copy().apply(ISLUtil.buildProjectionMaff(axis.copy()).toMap()).getBasicSets().get(0).getConstraints(), _function_1);
      }
      return _xblockexpression;
    };
    final Iterable<ISLConstraint> boxConstraints = Iterables.<ISLConstraint>concat(IterableExtensions.<Integer, Iterable<ISLConstraint>>map(new ExclusiveRange(0, _dim, true), _function));
    final Function2<ISLBasicSet, ISLConstraint, ISLBasicSet> _function_1 = (ISLBasicSet s, ISLConstraint c) -> {
      return s.addConstraint(c);
    };
    return IterableExtensions.<ISLConstraint, ISLBasicSet>fold(boxConstraints, ISLBasicSet.buildUniverse(set.getSpace().copy()), _function_1);
  }

  /**
   * Returns a piecwise maff, each dimension of which yields the parametrized
   * width of the set's bounding box in the corresponding dimension.
   * 
   * Importantly, we define width as the number of points in any dimension,
   * so if the bounding box is flat in a dimension, it is of width 1, not 0.
   */
  public static ISLPWMultiAff boundingBoxWidths(final ISLSet set) {
    final ISLSet box = ISLUtil.boundingBox(set.copy()).toSet();
    int _dim = set.dim(ISLDimType.isl_dim_out);
    final Function1<Integer, ISLAff> _function = (Integer it) -> {
      return ISLAff.buildValOnDomain(set.getSpace().copy().toLocalSpace(), 1);
    };
    final ISLMultiAff ones = ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(IterableExtensions.<Integer, ISLAff>map(new ExclusiveRange(0, _dim, true), _function)));
    final ISLSet shiftedBox = set.copy().apply(ISLUtil.toMultiAff(set.copy().identity()).add(ones).toMap());
    return shiftedBox.lexMaxAsPWMultiAff().sub(box.lexMinAsPWMultiAff());
  }

  /**
   * Returns the upper bound of an index in a set.
   * Parameters are projected out.
   * Returns Infinity if there is no bound.
   */
  public static ISLVal getUpperBound(final ISLSet set, final ISLDimType dimType, final int dim) {
    final ISLPoint axis = ISLPoint.buildZero(set.getSpace().copy()).add(dimType, dim, 1);
    final ISLSet projectedSet = set.copy().projectOut(ISLDimType.isl_dim_param, 0, set.dim(ISLDimType.isl_dim_param)).apply(ISLUtil.buildProjectionMaff(axis.copy()).toMap());
    boolean _hasUpperBound = projectedSet.hasUpperBound(dimType, dim);
    boolean _not = (!_hasUpperBound);
    if (_not) {
      return ISLVal.buildFromString(ISLContext.getInstance(), "Infinity");
    } else {
      return projectedSet.lexMax().samplePoint().getCoordinateVal(dimType, dim);
    }
  }

  /**
   * Generates a union set out of a list of ISLSets
   * The method in ISLUnionSet is bugged
   */
  public static ISLUnionSet convertToUnionSet(final List<ISLSet> sets) {
    ISLUnionSet _xblockexpression = null;
    {
      ISLUnionSet unionSet = null;
      for (final ISLSet set : sets) {
        ISLUnionSet _xifexpression = null;
        if ((unionSet == null)) {
          _xifexpression = set.toUnionSet();
        } else {
          _xifexpression = unionSet.addSet(set);
        }
        unionSet = _xifexpression;
      }
      _xblockexpression = unionSet;
    }
    return _xblockexpression;
  }

  /**
   * Generates a union map out of a list of ISLMaps
   */
  public static ISLUnionMap convertToUnionMap(final List<ISLMap> maps) {
    ISLUnionMap _xblockexpression = null;
    {
      ISLUnionMap unionMap = null;
      for (final ISLMap map : maps) {
        ISLUnionMap _xifexpression = null;
        if ((unionMap == null)) {
          _xifexpression = map.toUnionMap();
        } else {
          _xifexpression = unionMap.addMap(map);
        }
        unionMap = _xifexpression;
      }
      _xblockexpression = unionMap;
    }
    return _xblockexpression;
  }

  /**
   * Generates a MultiAff out of a list of ISLAffs
   */
  public static ISLMultiAff convertToMultiAff(final List<ISLAff> affs) {
    ISLMultiAff _xblockexpression = null;
    {
      ISLAffList affList = ISLAffList.build(ISLContext.getInstance(), 0);
      ISLSpace _copy = affs.get(0).getSpace().copy();
      int _size = affs.size();
      int _minus = (_size - 1);
      ISLSpace space = _copy.addDims(
        ISLDimType.isl_dim_out, _minus);
      for (final ISLAff aff : affs) {
        affList = affList.add(aff);
      }
      _xblockexpression = ISLMultiAff.buildFromAffList(space, affList);
    }
    return _xblockexpression;
  }

  /**
   * Converts an ISLMap to an ISLMultiAffine map as there is no default way
   */
  public static ISLMultiAff toMultiAff(final ISLMap map) {
    ISLMultiAff _xblockexpression = null;
    {
      final ISLMap local = map.copy();
      final ISLPWMultiAff pma = local.toPWMultiAff();
      final ISLPWMultiAffPiece piece = pma.getPiece(0);
      _xblockexpression = piece.getMaff();
    }
    return _xblockexpression;
  }

  /**
   * Converts a piecewise aff into a piecewise quasi-polynomial.
   */
  public static ISLPWQPolynomial toPWQPolynomial(final ISLPWAff pwAff) {
    final Function1<ISLPWMultiAffPiece, ISLPWQPolynomial> _function = (ISLPWMultiAffPiece piece) -> {
      ISLPWQPolynomial _xblockexpression = null;
      {
        final ISLQPolynomial qPoly = ISLQPolynomial.buildFromAff(piece.getMaff().getAff(0));
        _xblockexpression = ISLPWQPolynomial.build(piece.getSet(), qPoly);
      }
      return _xblockexpression;
    };
    final Function2<ISLPWQPolynomial, ISLPWQPolynomial, ISLPWQPolynomial> _function_1 = (ISLPWQPolynomial a, ISLPWQPolynomial b) -> {
      return a.addDisjoint(b);
    };
    return IterableExtensions.<ISLPWQPolynomial>reduce(ListExtensions.<ISLPWMultiAffPiece, ISLPWQPolynomial>map(pwAff.copy().toPWMultiAff().getPieces(), _function), _function_1);
  }

  /**
   * Attempts to factor out any extant constant factors from each dimension of
   * a spacetime map. If, for example, the target of a spacetime map  for one variable
   * was [2i-2j+1], and the other variable targets were factorable by 2,
   * this would be transformed into [i-j, 1].
   * This transformation preserves the lexicographical ordering of points,
   */
  public static ISLUnionMap liftSpacetimeFactors(final ISLUnionMap spacetimeMap) {
    final int nDims = spacetimeMap.getMaps().get(0).dim(ISLDimType.isl_dim_out);
    final Function1<Integer, Iterable<ISLAff>> _function = (Integer i) -> {
      final Function1<ISLMap, ISLAff> _function_1 = (ISLMap stMap) -> {
        return ISLUtil.toMultiAff(stMap.copy()).getAff(i);
      };
      return ListExtensions.<ISLMap, ISLAff>map(spacetimeMap.getMaps(), _function_1);
    };
    final Iterable<Iterable<ISLAff>> mapDimensions = IterableExtensions.<Integer, Iterable<ISLAff>>map(new IntegerRange(0, (nDims - 1)), _function);
    final Function1<Iterable<ISLAff>, ISLVal> _function_1 = (Iterable<ISLAff> dimension) -> {
      final Function1<ISLAff, ISLVal> _function_2 = (ISLAff aff) -> {
        int _dim = aff.dim(ISLDimType.isl_dim_in);
        int _minus = (_dim - 1);
        final Function1<Integer, ISLVal> _function_3 = (Integer i) -> {
          return aff.getCoefficientVal(ISLDimType.isl_dim_in, i);
        };
        final Function1<ISLVal, Boolean> _function_4 = (ISLVal a) -> {
          boolean _isZero = a.isZero();
          return Boolean.valueOf((!_isZero));
        };
        final Function2<ISLVal, ISLVal, ISLVal> _function_5 = (ISLVal a, ISLVal b) -> {
          return a.copy().abs().gcd(b.copy().abs());
        };
        return IterableExtensions.<ISLVal>reduce(IterableExtensions.<ISLVal>filter(IterableExtensions.<Integer, ISLVal>map(new IntegerRange(0, _minus), _function_3), _function_4), _function_5);
      };
      final Function1<ISLVal, Boolean> _function_3 = (ISLVal a) -> {
        return Boolean.valueOf((a != null));
      };
      final Function2<ISLVal, ISLVal, ISLVal> _function_4 = (ISLVal a, ISLVal b) -> {
        return a.copy().abs().gcd(b.copy().abs());
      };
      return IterableExtensions.<ISLVal>reduce(IterableExtensions.<ISLVal>filter(IterableExtensions.<ISLAff, ISLVal>map(dimension, _function_2), _function_3), _function_4);
    };
    final Iterable<ISLVal> dimensionFactors = IterableExtensions.<Iterable<ISLAff>, ISLVal>map(mapDimensions, _function_1);
    final Function1<ISLMap, ISLMap> _function_2 = (ISLMap stMap) -> {
      final ISLMultiAff stMaff = ISLUtil.toMultiAff(stMap.copy());
      final Function1<Integer, List<ISLAff>> _function_3 = (Integer i) -> {
        final ISLVal factor = ((ISLVal[])Conversions.unwrapArray(dimensionFactors, ISLVal.class))[i];
        final ISLAff aff = stMaff.getAff(i);
        if (((factor == null) || (factor.copy().asLong() <= 1))) {
          ISLAff _copy = stMaff.getAff(i).copy();
          return Collections.<ISLAff>unmodifiableList(CollectionLiterals.<ISLAff>newArrayList(_copy));
        } else {
          ISLAff _setConstant = aff.copy().scaleDown(factor.copy()).setConstant(aff.getConstantVal().copy().div(factor.copy()).floor());
          ISLAff _setConstant_1 = aff.copy().scale(0).setConstant(aff.getConstantVal().copy().mod(factor.copy()));
          return Collections.<ISLAff>unmodifiableList(CollectionLiterals.<ISLAff>newArrayList(_setConstant, _setConstant_1));
        }
      };
      return ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(Iterables.<ISLAff>concat(IterableExtensions.<Integer, List<ISLAff>>map(new IntegerRange(0, (nDims - 1)), _function_3)))).toMap().<ISLMap>setInputTupleName(stMap.getInputTupleName());
    };
    return ISLUtil.convertToUnionMap(ListExtensions.<ISLMap, ISLMap>map(spacetimeMap.getMaps(), _function_2));
  }

  /**
   * Counts the number of time dimensions in a spacetime map
   * I.e. the minimal first few dimensions of the map that
   * satisfy all of the causality restraints.
   */
  public static int countTimeDimensions(final AlphaSystem sys, final ISLUnionMap spacetimeMap) {
    final int nDims = spacetimeMap.getMaps().get(0).dim(ISLDimType.isl_dim_out);
    for (int i = 0; (i < nDims); i++) {
      {
        final int nTimeDims = (i + 1);
        final Function1<ISLMap, ISLMap> _function = (ISLMap stMap) -> {
          return ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(ISLUtil.toMultiAff(stMap.copy()).getAffs().subList(0, nTimeDims))).toMap();
        };
        final ISLUnionMap timeMap = ISLUtil.convertToUnionMap(ListExtensions.<ISLMap, ISLMap>map(spacetimeMap.getMaps(), _function));
        ISLUnionSet _domain = spacetimeMap.getDomain();
        final ManualScheduler scheduler = new ManualScheduler(timeMap, _domain);
        boolean validSchedule = true;
        try {
          ScheduleVerifier.verify(sys, scheduler);
        } catch (final Throwable _t) {
          if (_t instanceof CausalityViolationException) {
            validSchedule = false;
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
        if (validSchedule) {
          return nTimeDims;
        }
      }
    }
    return (-1);
  }

  /**
   * Builds the set of points where aff1 is lexicographically equal to aff2
   */
  public static ISLSet buildLexEQSet(final ISLMultiAff aff1, final ISLMultiAff aff2) {
    ISLSet set = null;
    for (int i = 0; (i < aff1.getAffs().size()); i++) {
      {
        final ISLSet EQSet = ISLSet.buildEQSet(aff1.getAffs().get(i).copy(), aff2.getAffs().get(i).copy());
        ISLSet _xifexpression = null;
        if ((set == null)) {
          _xifexpression = EQSet;
        } else {
          _xifexpression = set.intersect(EQSet);
        }
        set = _xifexpression;
      }
    }
    return set;
  }

  public static ISLSet buildLexGTSet(final ISLMultiAff aff1, final ISLMultiAff aff2) {
    ISLSet lexSet = null;
    ISLSet set = null;
    for (int i = 0; (i < aff1.getAffs().size()); i++) {
      {
        final ISLSet EQSet = ISLSet.buildEQSet(aff1.getAffs().get(i).copy(), aff2.getAffs().get(i).copy());
        final ISLSet GTSet = ISLSet.buildGTSet(aff1.getAffs().get(i).copy(), aff2.getAffs().get(i).copy());
        ISLSet _xifexpression = null;
        if ((lexSet == null)) {
          _xifexpression = GTSet;
        } else {
          _xifexpression = GTSet.intersect(lexSet.copy());
        }
        final ISLSet GESet = _xifexpression;
        ISLSet _xifexpression_1 = null;
        if ((set == null)) {
          _xifexpression_1 = GESet;
        } else {
          _xifexpression_1 = set.union(GESet);
        }
        set = _xifexpression_1;
        ISLSet _xifexpression_2 = null;
        if ((lexSet == null)) {
          _xifexpression_2 = EQSet;
        } else {
          _xifexpression_2 = lexSet.intersect(EQSet);
        }
        lexSet = _xifexpression_2;
      }
    }
    return set.simplify();
  }

  public static ISLSet buildLexGESet(final ISLMultiAff aff1, final ISLMultiAff aff2) {
    return ISLUtil.buildLexEQSet(aff1, aff2).union(ISLUtil.buildLexGTSet(aff1, aff2)).simplify();
  }

  public static ISLSet buildLexLTSet(final ISLMultiAff aff1, final ISLMultiAff aff2) {
    return ISLUtil.buildLexGTSet(aff2, aff1);
  }

  public static ISLSet buildLexLESet(final ISLMultiAff aff1, final ISLMultiAff aff2) {
    return ISLUtil.buildLexGESet(aff2, aff1);
  }

  public static ISLSet buildLexNESet(final ISLMultiAff aff1, final ISLMultiAff aff2) {
    return ISLUtil.buildLexEQSet(aff1, aff2).complement().simplify();
  }
}
