package alpha.model.util;

import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.Set;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
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
   * The parent node of this facet in the lattice.
   */
  private final Facet ancestor;

  /**
   * Extract the information from the given set, assuming that no constraints were saturated to form it.
   */
  public Facet(final ISLBasicSet basicSet) {
    this(basicSet, new ArrayList<Integer>(0), null);
  }

  /**
   * Extract the information from the given set.
   */
  public Facet(final ISLBasicSet basicSet, final ArrayList<Integer> saturatedInequalityIndices, final Facet ancestor) {
    this.equalities = DomainOperations.toISLEqualityMatrix(basicSet);
    this.indexCount = basicSet.dim(ISLDimType.isl_dim_set);
    this.indexInequalities = Facet.getInequalities(basicSet, this.indexCount, true);
    this.indexInequalityCount = this.indexInequalities.getNbRows();
    this.isBounded = basicSet.bounded();
    this.isEmpty = basicSet.isEmpty();
    this.parameterEqualityCount = Facet.countParameterConstraints(this.equalities, this.indexCount);
    this.parameterInequalities = Facet.getInequalities(basicSet, this.indexCount, false);
    this.saturatedInequalityIndices = saturatedInequalityIndices;
    this.space = basicSet.getSpace();
    this.ancestor = ancestor;
    this.dimensionality = Facet.dimensionality(this.equalities, this.indexCount);
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
    final ISLMatrix inequalities = indexInequalities.concat(ancestor.parameterInequalities.copy());
    final ISLBasicSet basicSet = ISLBasicSet.fromConstraintMatrices(
      ancestor.space.copy(), equalities, inequalities, 
      ISLDimType.isl_dim_param, ISLDimType.isl_dim_set, 
      ISLDimType.isl_dim_div, ISLDimType.isl_dim_cst).removeRedundancies();
    return new Facet(basicSet, toSaturate, ancestor);
  }

  /**
   * Returns <code>true</code> if this set is a child face of the given set, and <code>false</code> otherwise.
   */
  public boolean isChildOf(final Facet other) {
    int _size = this.saturatedInequalityIndices.size();
    int _size_1 = other.saturatedInequalityIndices.size();
    int _plus = (1 + _size_1);
    boolean _notEquals = (_size != _plus);
    if (_notEquals) {
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
    if ((notSaturatedCount != 1)) {
      return false;
    }
    return true;
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
    final ISLMatrix allInequalities = this.indexInequalities.copy().concat(this.parameterInequalities.copy());
    return ISLBasicSet.fromConstraintMatrices(
      this.space.copy(), this.equalities.copy(), allInequalities, 
      ISLDimType.isl_dim_param, ISLDimType.isl_dim_set, 
      ISLDimType.isl_dim_div, ISLDimType.isl_dim_cst).removeRedundancies();
  }

  /**
   * Returns the normal vector (ISLAff) of the inequality characterizing the facet in its parent.
   */
  public ISLAff getNormalVector() {
    final ISLSpace identityMapSpace = this.space.copy().toMapSpaceFromSetSpace();
    final ISLMultiAff maff = AffineFactorizer.toExpression(ISLUtil.transpose(this.ancestor.indexInequalities), identityMapSpace);
    final ISLAff normalVector = maff.getAff((this.getCharacteristicInequalityIndex()).intValue()).dropDims(ISLDimType.isl_dim_param, 0, this.space.getNbParams()).setConstant(0);
    return normalVector;
  }

  /**
   * Returns the row index of the inequality characterizing the facet in its parent.
   */
  public Integer getCharacteristicInequalityIndex() {
    final Set<Integer> indices = IterableExtensions.<Integer>toSet(this.saturatedInequalityIndices);
    indices.removeAll(IterableExtensions.<Integer>toSet(this.ancestor.saturatedInequalityIndices));
    final Integer ineqIndex = IterableExtensions.<Integer>toList(indices).get(0);
    return ineqIndex;
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
   * Returns the dimensionality of a set using the equality constraints and number of index variables.
   */
  private static int dimensionality(final ISLMatrix equalities, final int indexCount) {
    int _nbRows = equalities.getNbRows();
    final Function1<Integer, Boolean> _function = (Integer row) -> {
      return Boolean.valueOf(Facet.constraintInvolvesIndex(equalities, (row).intValue(), indexCount));
    };
    final Function2<ISLMatrix, Integer, ISLMatrix> _function_1 = (ISLMatrix mat, Integer row) -> {
      return mat.dropRows((row).intValue(), 1);
    };
    final int linearlyIndependentIndexEqualities = IterableExtensions.<Integer, ISLMatrix>fold(IterableExtensions.<Integer>reject(new ExclusiveRange(_nbRows, 0, false), _function), equalities.copy(), _function_1).rank();
    return (indexCount - linearlyIndependentIndexEqualities);
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

  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.dimensionality;
    result = prime * result + ((this.equalities== null) ? 0 : this.equalities.hashCode());
    result = prime * result + this.indexCount;
    result = prime * result + ((this.indexInequalities== null) ? 0 : this.indexInequalities.hashCode());
    result = prime * result + this.indexInequalityCount;
    result = prime * result + (this.isBounded ? 1231 : 1237);
    result = prime * result + (this.isEmpty ? 1231 : 1237);
    result = prime * result + this.parameterEqualityCount;
    result = prime * result + ((this.parameterInequalities== null) ? 0 : this.parameterInequalities.hashCode());
    result = prime * result + ((this.saturatedInequalityIndices== null) ? 0 : this.saturatedInequalityIndices.hashCode());
    result = prime * result + ((this.space== null) ? 0 : this.space.hashCode());
    return prime * result + ((this.ancestor== null) ? 0 : this.ancestor.hashCode());
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
    if (this.ancestor == null) {
      if (other.ancestor != null)
        return false;
    } else if (!this.ancestor.equals(other.ancestor))
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

  @Pure
  public Facet getAncestor() {
    return this.ancestor;
  }
}
