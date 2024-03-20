package alpha.model.util;

import alpha.model.matrix.MatrixOperations;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.MapExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Contains useful information about an <code>ISLBasicSet</code>.
 * Intended for use with the <code>FaceLattice</code> class.
 * Allows for inequalities within the set to be saturated,
 * and can check whether two sets have a child/parent relationship
 * based on which inequalities have been saturated.
 * 
 * Usage:
 * <code>new(basicSet)</code>:
 *     Get the information of the given set
 *     without saturating any of the inequalities.
 * 
 * <code>new(basicSet, saturatedInequalityIndices)</code>:
 *     Get the information of the given set
 *     after saturating the specified inequalities.
 * 
 * <code>SetInfo.createFace(ancestor, toSaturate)</code>:
 *     Get the information of the set created
 *     by saturating the indicated inequalities in the ancestor,
 *     keeping any previously saturated inequalities as saturated.
 */
@Data
@SuppressWarnings("all")
public class Facet {
  /**
   * The dimensionality of the set.
   */
  private final int dimensionality;

  /**
   * The equality constraints defining the set.
   */
  private final ISLMatrix equalities;

  /**
   * The pairs of inequality constraints that are effectively saturated
   */
  private final ISLMatrix thickEqualities;

  /**
   * The number of index variables in the set's space.
   */
  private final int indexCount;

  /**
   * The inequality constraints which involve at least one index variable.
   */
  private final ISLMatrix indexInequalities;

  /**
   * The number of index inequalities.
   */
  private final int indexInequalityCount;

  /**
   * Whether or not the set is bounded, or unbounded.
   */
  private final boolean isBounded;

  /**
   * Whether or not the set is empty, or contains at least one point.
   */
  private final boolean isEmpty;

  /**
   * The number of equality constraints which involve only parameter variables.
   */
  private final int parameterEqualityCount;

  /**
   * The inequality constraints which only involve parameter variables.
   */
  private final ISLMatrix parameterInequalities;

  /**
   * The indices of the inequalities which were saturated to form this set.
   */
  private final ArrayList<Integer> saturatedInequalityIndices;

  /**
   * The space that the set resides in.
   */
  private final ISLSpace space;

  /**
   * Extract the information from the given set, assuming that no constraints were saturated to form it.
   */
  public Facet(final ISLBasicSet basicSet) {
    this(basicSet, new ArrayList<Integer>(0));
  }

  /**
   * Extract the information from the given set.
   */
  public Facet(final ISLBasicSet basicSet, final ArrayList<Integer> saturatedInequalityIndices) {
    try {
      final ISLBasicSet basicSetNoRedundancies = basicSet.copy().removeRedundancies();
      this.space = basicSetNoRedundancies.getSpace();
      this.equalities = DomainOperations.toISLEqualityMatrix(basicSetNoRedundancies);
      this.indexCount = basicSetNoRedundancies.dim(ISLDimType.isl_dim_set);
      final ISLMatrix allInequalities = Facet.getInequalities(basicSetNoRedundancies, this.indexCount, true);
      final Pair<ISLMatrix, ISLMatrix> inequalities = Facet.separateEffectivelySaturatedInequalities(allInequalities, this.space);
      this.indexInequalities = inequalities.getKey();
      this.thickEqualities = inequalities.getValue();
      int _nbRows = this.thickEqualities.getNbRows();
      int _modulo = (_nbRows % 2);
      boolean _notEquals = (_modulo != 0);
      if (_notEquals) {
        throw new Exception("Failed to create lattice, there should be an even number of (or zero) effectively saturated constraints");
      }
      this.indexInequalityCount = this.indexInequalities.getNbRows();
      this.isBounded = basicSetNoRedundancies.bounded();
      this.isEmpty = basicSetNoRedundancies.isEmpty();
      this.parameterEqualityCount = Facet.countParameterConstraints(this.equalities, this.indexCount);
      this.parameterInequalities = Facet.getInequalities(basicSetNoRedundancies, this.indexCount, false);
      this.saturatedInequalityIndices = saturatedInequalityIndices;
      this.dimensionality = ISLUtil.dimensionality(basicSet);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * ISLMatrix buildFromLongMatrix throws an exception if the input matrix has no rows
   */
  public static ISLMatrix buildFromLongMatrixEvenIfEmpty(final long[][] matrix, final ISLSpace space) {
    int _length = matrix.length;
    boolean _equals = (_length == 0);
    if (_equals) {
      return DomainOperations.toISLEqualityMatrix(ISLBasicSet.buildUniverse(space.copy()));
    }
    return ISLMatrix.buildFromLongMatrix(matrix);
  }

  /**
   * Saturates the given index inequalities from the ancestor to form a potential face.
   */
  public static Facet createFace(final Facet ancestor, final ArrayList<Integer> toSaturate) {
    final Function1<Integer, Boolean> _function = (Integer row) -> {
      return Boolean.valueOf(toSaturate.contains(row));
    };
    final Function2<ISLMatrix, Integer, ISLMatrix> _function_1 = (ISLMatrix mat, Integer row) -> {
      return mat.dropRows((row).intValue(), 1);
    };
    final ISLMatrix saturatedInequalities = IterableExtensions.<Integer, ISLMatrix>fold(IterableExtensions.<Integer>reject(new ExclusiveRange(ancestor.indexInequalityCount, 0, false), _function), ancestor.indexInequalities.copy(), _function_1);
    final ISLMatrix equalities = ancestor.equalities.copy().concat(saturatedInequalities);
    int _nbRows = ancestor.indexInequalities.getNbRows();
    final Function1<Integer, Boolean> _function_2 = (Integer row) -> {
      return Boolean.valueOf(toSaturate.contains(row));
    };
    final Function2<ISLMatrix, Integer, ISLMatrix> _function_3 = (ISLMatrix mat, Integer row) -> {
      return mat.dropRows((row).intValue(), 1);
    };
    final ISLMatrix indexInequalities = IterableExtensions.<Integer, ISLMatrix>fold(IterableExtensions.<Integer>filter(new ExclusiveRange(_nbRows, 0, false), _function_2), ancestor.indexInequalities.copy(), _function_3);
    final ISLMatrix inequalities = indexInequalities.concat(ancestor.parameterInequalities.copy()).concat(ancestor.thickEqualities.copy());
    final ISLBasicSet basicSet = ISLBasicSet.fromConstraintMatrices(
      ancestor.space.copy(), equalities, inequalities, 
      ISLDimType.isl_dim_param, ISLDimType.isl_dim_set, 
      ISLDimType.isl_dim_div, ISLDimType.isl_dim_cst).removeRedundancies();
    return new Facet(basicSet, toSaturate);
  }

  /**
   * Returns <code>true</code> if this set is a child face of the given set, and <code>false</code> otherwise.
   */
  public boolean isChildOf(final Facet other) {
    int _size = this.saturatedInequalityIndices.size();
    int _size_1 = other.saturatedInequalityIndices.size();
    boolean _lessEqualsThan = (_size <= _size_1);
    if (_lessEqualsThan) {
      return false;
    }
    final Function1<Integer, Boolean> _function = (Integer it) -> {
      return Boolean.valueOf(this.saturatedInequalityIndices.contains(it));
    };
    boolean _forall = IterableExtensions.<Integer>forall(other.saturatedInequalityIndices, _function);
    boolean _not = (!_forall);
    if (_not) {
      return false;
    }
    final Function1<Integer, Boolean> _function_1 = (Integer it) -> {
      return Boolean.valueOf(other.saturatedInequalityIndices.contains(it));
    };
    final int notSaturatedCount = IterableExtensions.size(IterableExtensions.<Integer>reject(this.saturatedInequalityIndices, _function_1));
    if ((notSaturatedCount < 1)) {
      return false;
    }
    return true;
  }

  /**
   * Returns <code>true</code> if this set has the same saturated inequalities as the other.
   * Otherwise, returns <code>false</code>.
   */
  public boolean isDuplicateOf(final Facet other) {
    return (this.saturatedInequalityIndices.containsAll(other.saturatedInequalityIndices) && other.saturatedInequalityIndices.containsAll(this.saturatedInequalityIndices));
  }

  /**
   * Returns <code>true</code> if this set is of the same dimension as the other,
   * and the inequalities saturated by this facet is a subset of the other.
   * Otherwise, returns <code>false</code>.
   */
  public boolean isStrictSubsetOf(final Facet other) {
    return (((this.dimensionality == other.dimensionality) && (this.saturatedInequalityIndices.size() < other.saturatedInequalityIndices.size())) && other.saturatedInequalityIndices.containsAll(this.saturatedInequalityIndices));
  }

  /**
   * Returns <code>true</code> if this set is a valid face of the given set, and <code>false</code> otherwise.
   */
  public boolean isValidFace(final Facet startingSetInfo) {
    if (this.isEmpty) {
      return false;
    }
    if ((this.parameterEqualityCount > startingSetInfo.parameterEqualityCount)) {
      return false;
    }
    return true;
  }

  /**
   * Creates a basic set from this information.
   */
  public ISLBasicSet toBasicSet() {
    final ISLMatrix allInequalities = this.indexInequalities.copy().concat(this.parameterInequalities.copy()).concat(this.thickEqualities.copy());
    return ISLBasicSet.fromConstraintMatrices(
      this.space.copy(), this.equalities.copy(), allInequalities, 
      ISLDimType.isl_dim_param, ISLDimType.isl_dim_set, 
      ISLDimType.isl_dim_div, ISLDimType.isl_dim_cst).removeRedundancies();
  }

  /**
   * Returns <code>true</code> if the facet has at least one thick face, and <code>false</code> otherwise.
   */
  public boolean hasThickFaces() {
    int _nbRows = this.thickEqualities.getNbRows();
    return (_nbRows > 0);
  }

  /**
   * Returns a string indicating which inequalities were saturated to form this face.
   */
  @Override
  public String toString() {
    String _join = IterableExtensions.join(this.saturatedInequalityIndices, ",");
    String _plus = ("{" + _join);
    return (_plus + "}");
  }

  /**
   * Checks if the row of a constraint matrix has a non-zero coefficient for at least one index dimension.
   */
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

  /**
   * Counts the number of constraints which involve only parameter variables.
   */
  private static int countParameterConstraints(final ISLMatrix matrix, final int indexCount) {
    int _nbRows = matrix.getNbRows();
    final Function1<Integer, Boolean> _function = (Integer row) -> {
      return Boolean.valueOf(Facet.constraintInvolvesIndex(matrix, (row).intValue(), indexCount));
    };
    return IterableExtensions.size(IterableExtensions.<Integer>reject(new ExclusiveRange(0, _nbRows, true), _function));
  }

  /**
   * Gets a matrix of inequality constraints from a set.
   * Retrieves either only the constraints which involve at least one index variable,
   * or only the constraints which only involve parameter variables.
   */
  private static ISLMatrix getInequalities(final ISLBasicSet startingSet, final int indexCount, final boolean getIndexConstraints) {
    final ISLMatrix inequalities = DomainOperations.toISLInequalityMatrix(startingSet);
    int _nbRows = inequalities.getNbRows();
    final Function1<Integer, Boolean> _function = (Integer row) -> {
      boolean _constraintInvolvesIndex = Facet.constraintInvolvesIndex(inequalities, (row).intValue(), indexCount);
      return Boolean.valueOf((getIndexConstraints != _constraintInvolvesIndex));
    };
    final Function2<ISLMatrix, Integer, ISLMatrix> _function_1 = (ISLMatrix mat, Integer row) -> {
      return mat.dropRows((row).intValue(), 1);
    };
    return IterableExtensions.<Integer, ISLMatrix>fold(IterableExtensions.<Integer>toList(IterableExtensions.<Integer>filter(new ExclusiveRange(_nbRows, 0, false), _function)), inequalities, _function_1);
  }

  /**
   * Separates the effectively saturated inequalities from true unsaturated inequalities
   */
  private static Pair<ISLMatrix, ISLMatrix> separateEffectivelySaturatedInequalities(final ISLMatrix allInequalities, final ISLSpace space) {
    ISLMatrix _copy = allInequalities.copy();
    int _nbParams = space.getNbParams();
    int _nbIndices = space.getNbIndices();
    int _plus = (_nbParams + _nbIndices);
    final long[][] matrix = _copy.dropCols(_plus, 1).toLongMatrix();
    int _length = matrix.length;
    final Function1<Integer, String> _function = (Integer rowIdx) -> {
      return MatrixOperations.toString(matrix[(rowIdx).intValue()]);
    };
    final Function1<Integer, Long> _function_1 = (Integer rowIdx) -> {
      return Long.valueOf((rowIdx).intValue());
    };
    final HashMap<String, Long> idxMap = CommonExtensions.<String, Long>toHashMap(MapExtensions.<String, Integer, Long>mapValues(IterableExtensions.<String, Integer>toMap(new ExclusiveRange(0, _length, true), _function), _function_1));
    final long[][] negMatrix = MatrixOperations.scalarMultiplication(matrix, (-1));
    int _length_1 = matrix.length;
    final Function1<Integer, String> _function_2 = (Integer rowIdx) -> {
      return MatrixOperations.toString(negMatrix[(rowIdx).intValue()]);
    };
    final Function2<Integer, String, Boolean> _function_3 = (Integer rowIdx, String rowStr) -> {
      return Boolean.valueOf(idxMap.containsKey(rowStr));
    };
    final Set<Integer> colinearConstraints = CommonExtensions.<Integer, String>toHashMap(MapExtensions.<Integer, String>filter(IterableExtensions.<Integer, String>toInvertedMap(new ExclusiveRange(0, _length_1, true), _function_2), _function_3)).keySet();
    final long[][] longMatrix = allInequalities.toLongMatrix();
    int _length_2 = matrix.length;
    final Function1<Integer, Boolean> _function_4 = (Integer i) -> {
      return Boolean.valueOf(colinearConstraints.contains(i));
    };
    final Function1<Integer, long[]> _function_5 = (Integer i) -> {
      return longMatrix[(i).intValue()];
    };
    final ISLMatrix thickEqualities = Facet.buildFromLongMatrixEvenIfEmpty(((long[][])Conversions.unwrapArray(IterableExtensions.<Integer, long[]>map(IterableExtensions.<Integer>filter(new ExclusiveRange(0, _length_2, true), _function_4), _function_5), long[].class)), space);
    int _length_3 = matrix.length;
    final Function1<Integer, Boolean> _function_6 = (Integer i) -> {
      return Boolean.valueOf(colinearConstraints.contains(i));
    };
    final Function1<Integer, long[]> _function_7 = (Integer i) -> {
      return longMatrix[(i).intValue()];
    };
    final ISLMatrix unsaturatedInequalities = Facet.buildFromLongMatrixEvenIfEmpty(((long[][])Conversions.unwrapArray(IterableExtensions.<Integer, long[]>map(IterableExtensions.<Integer>reject(new ExclusiveRange(0, _length_3, true), _function_6), _function_7), long[].class)), space);
    return Pair.<ISLMatrix, ISLMatrix>of(unsaturatedInequalities, thickEqualities);
  }

  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.dimensionality;
    result = prime * result + ((this.equalities== null) ? 0 : this.equalities.hashCode());
    result = prime * result + ((this.thickEqualities== null) ? 0 : this.thickEqualities.hashCode());
    result = prime * result + this.indexCount;
    result = prime * result + ((this.indexInequalities== null) ? 0 : this.indexInequalities.hashCode());
    result = prime * result + this.indexInequalityCount;
    result = prime * result + (this.isBounded ? 1231 : 1237);
    result = prime * result + (this.isEmpty ? 1231 : 1237);
    result = prime * result + this.parameterEqualityCount;
    result = prime * result + ((this.parameterInequalities== null) ? 0 : this.parameterInequalities.hashCode());
    result = prime * result + ((this.saturatedInequalityIndices== null) ? 0 : this.saturatedInequalityIndices.hashCode());
    return prime * result + ((this.space== null) ? 0 : this.space.hashCode());
  }

  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Facet other = (Facet) obj;
    if (other.dimensionality != this.dimensionality)
      return false;
    if (this.equalities == null) {
      if (other.equalities != null)
        return false;
    } else if (!this.equalities.equals(other.equalities))
      return false;
    if (this.thickEqualities == null) {
      if (other.thickEqualities != null)
        return false;
    } else if (!this.thickEqualities.equals(other.thickEqualities))
      return false;
    if (other.indexCount != this.indexCount)
      return false;
    if (this.indexInequalities == null) {
      if (other.indexInequalities != null)
        return false;
    } else if (!this.indexInequalities.equals(other.indexInequalities))
      return false;
    if (other.indexInequalityCount != this.indexInequalityCount)
      return false;
    if (other.isBounded != this.isBounded)
      return false;
    if (other.isEmpty != this.isEmpty)
      return false;
    if (other.parameterEqualityCount != this.parameterEqualityCount)
      return false;
    if (this.parameterInequalities == null) {
      if (other.parameterInequalities != null)
        return false;
    } else if (!this.parameterInequalities.equals(other.parameterInequalities))
      return false;
    if (this.saturatedInequalityIndices == null) {
      if (other.saturatedInequalityIndices != null)
        return false;
    } else if (!this.saturatedInequalityIndices.equals(other.saturatedInequalityIndices))
      return false;
    if (this.space == null) {
      if (other.space != null)
        return false;
    } else if (!this.space.equals(other.space))
      return false;
    return true;
  }

  @Pure
  public int getDimensionality() {
    return this.dimensionality;
  }

  @Pure
  public ISLMatrix getEqualities() {
    return this.equalities;
  }

  @Pure
  public ISLMatrix getThickEqualities() {
    return this.thickEqualities;
  }

  @Pure
  public int getIndexCount() {
    return this.indexCount;
  }

  @Pure
  public ISLMatrix getIndexInequalities() {
    return this.indexInequalities;
  }

  @Pure
  public int getIndexInequalityCount() {
    return this.indexInequalityCount;
  }

  @Pure
  public boolean isBounded() {
    return this.isBounded;
  }

  @Pure
  public boolean isEmpty() {
    return this.isEmpty;
  }

  @Pure
  public int getParameterEqualityCount() {
    return this.parameterEqualityCount;
  }

  @Pure
  public ISLMatrix getParameterInequalities() {
    return this.parameterInequalities;
  }

  @Pure
  public ArrayList<Integer> getSaturatedInequalityIndices() {
    return this.saturatedInequalityIndices;
  }

  @Pure
  public ISLSpace getSpace() {
    return this.space;
  }
}
