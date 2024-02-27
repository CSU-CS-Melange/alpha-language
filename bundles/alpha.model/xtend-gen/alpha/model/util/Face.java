package alpha.model.util;

import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Consumer;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.MapExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Represents a face which can be used to construct a face lattice.
 * 
 * Intended Construction:
 * <code>new(basicSet)</code>:
 * 		Construct a face from a basic set outside the context of a lattice.
 * 
 * <code>new(basicSet, lattice)</code>:
 * 		Construct a face from a basic set in the context of a lattice.
 * 
 * <code>new(face)</code>:
 * 		Copy constructor. Creates only shallow copies of internal lists and maps.
 */
@Data
@SuppressWarnings("all")
public class Face {
  /**
   * A map from a standardized constraint index to each unsaturated constraint.
   */
  private final HashMap<Integer, ISLConstraint> unsaturatedConstraints;

  /**
   * A list of the constraints which are considered to be saturated
   */
  private final ArrayList<ISLConstraint> saturatedConstraints;

  /**
   * The space in which this face resides.
   */
  private final ISLSpace space;

  /**
   * The number of unsaturated constraints that the original (root) face started with.
   */
  private final int originalConstraintCount;

  /**
   * The lattice that this face is in, if one exists. May be null.
   */
  private final FaceLattice2 lattice;

  /**
   * Constructs a new face which has no parents.
   */
  public Face(final ISLBasicSet basicSet) {
    this(basicSet, null);
  }

  /**
   * Creates a face to use as the root of a face lattice.
   */
  public Face(final ISLBasicSet basicSet, final FaceLattice2 lattice) {
    final ISLBasicSet cleanBasicSet = basicSet.copy().removeRedundancies();
    final Function1<ISLConstraint, Boolean> _function = (ISLConstraint c) -> {
      return Boolean.valueOf(Face.isConsideredSaturated(c, cleanBasicSet));
    };
    final Pair<ArrayList<ISLConstraint>, ArrayList<ISLConstraint>> separatedConstraints = CommonExtensions.<ISLConstraint>splitBy(cleanBasicSet.getConstraints(), _function);
    this.saturatedConstraints = separatedConstraints.getKey();
    this.unsaturatedConstraints = CommonExtensions.<ISLConstraint>toIndexHashMap(separatedConstraints.getValue());
    this.space = cleanBasicSet.getSpace();
    this.originalConstraintCount = this.unsaturatedConstraints.size();
    this.lattice = lattice;
  }

  /**
   * Copy constructor. Makes a shallow copy of all internal lists.
   */
  public Face(final Face other) {
    this(
      new HashMap<Integer, ISLConstraint>(other.unsaturatedConstraints), 
      new ArrayList<ISLConstraint>(other.saturatedConstraints), 
      other.space, 
      other.originalConstraintCount, 
      other.lattice);
  }

  /**
   * Constructor for all fields.
   */
  public Face(final HashMap<Integer, ISLConstraint> unsaturatedConstraints, final ArrayList<ISLConstraint> saturatedConstraints, final ISLSpace space, final int originalConstraintCount, final FaceLattice2 lattice) {
    this.unsaturatedConstraints = unsaturatedConstraints;
    this.saturatedConstraints = saturatedConstraints;
    this.space = space;
    this.originalConstraintCount = originalConstraintCount;
    this.lattice = lattice;
  }

  public static ArrayList<Face> removeDuplicates(final Face... faces) {
    final Function1<Face, String> _function = (Face face) -> {
      return face.toString();
    };
    return CommonExtensions.<Face>toArrayList(IterableExtensions.<String, Face>toMap(((Iterable<? extends Face>)Conversions.doWrapArray(faces)), _function).values());
  }

  /**
   * Generates the list of direct children to this face.
   * Children are defined as having one fewer dimension than this face.
   */
  public ArrayList<Face> generateChildren() {
    final int currentDimension = this.getDimensionality();
    final Function1<Integer, Face> _function = (Integer it) -> {
      return this.saturateConstraint((it).intValue());
    };
    final Function1<Face, Boolean> _function_1 = (Face face) -> {
      int _dimensionality = face.getDimensionality();
      return Boolean.valueOf((_dimensionality == (currentDimension - 1)));
    };
    return Face.removeDuplicates(((Face[])Conversions.unwrapArray(IterableExtensions.<Face>filter(IterableExtensions.<Integer, Face>map(this.unsaturatedConstraints.keySet(), _function), _function_1), Face.class)));
  }

  /**
   * Gets the dimensionality of this face.
   */
  public int getDimensionality() {
    return ISLUtil.dimensionality(this.toBasicSet());
  }

  /**
   * Constructs a new face by saturating an additional constraint compared to this face.
   */
  public Face saturateConstraint(final int idx) {
    boolean _containsKey = this.unsaturatedConstraints.containsKey(Integer.valueOf(idx));
    boolean _not = (!_containsKey);
    if (_not) {
      throw new IllegalArgumentException("Cannot saturate a constraint which isn\'t unsaturated.");
    }
    final Face other = new Face(this);
    other.moveConstraintToSaturated(idx);
    final ISLBasicSet basicSet = other.toBasicSet();
    final Function2<Integer, ISLConstraint, Boolean> _function = (Integer i, ISLConstraint c) -> {
      return Boolean.valueOf(ISLUtil.isEffectivelySaturated(c, basicSet));
    };
    final ArrayList<Integer> alsoSaturated = CommonExtensions.<Integer>toArrayList(MapExtensions.<Integer, ISLConstraint>filter(other.unsaturatedConstraints, _function).keySet());
    final Consumer<Integer> _function_1 = (Integer i) -> {
      other.moveConstraintToSaturated((i).intValue());
    };
    alsoSaturated.forEach(_function_1);
    return other;
  }

  /**
   * Constructs the basic set which represents this face.
   */
  public ISLBasicSet toBasicSet() {
    final ISLBasicSet universe = ISLBasicSet.buildUniverse(this.space.copy());
    Collection<ISLConstraint> _values = this.unsaturatedConstraints.values();
    final Function1<ISLConstraint, ISLConstraint> _function = (ISLConstraint c) -> {
      return c.copy();
    };
    final Function2<ISLBasicSet, ISLConstraint, ISLBasicSet> _function_1 = (ISLBasicSet s, ISLConstraint c) -> {
      return s.addConstraint(c);
    };
    return IterableExtensions.<ISLConstraint, ISLBasicSet>fold(IterableExtensions.<ISLConstraint, ISLConstraint>map(Iterables.<ISLConstraint>concat(Collections.<Collection<ISLConstraint>>unmodifiableList(CollectionLiterals.<Collection<ISLConstraint>>newArrayList(_values, this.saturatedConstraints))), _function), universe, _function_1).removeRedundancies();
  }

  /**
   * Returns a string indicating which constraints were saturated to form this face.
   */
  @Override
  public String toString() {
    final Function1<Integer, Boolean> _function = (Integer idx) -> {
      return Boolean.valueOf(this.unsaturatedConstraints.containsKey(idx));
    };
    final String saturatedIndexes = IterableExtensions.join(IterableExtensions.<Integer>reject(new ExclusiveRange(0, this.originalConstraintCount, true), _function), ",");
    return (("{" + saturatedIndexes) + "}");
  }

  /**
   * Determines whether a constraint is considered to be saturated for the given set.
   */
  protected static boolean isConsideredSaturated(final ISLConstraint c, final ISLBasicSet s) {
    return ((c.isEquality() || (!c.involvesDims(ISLDimType.isl_dim_set, 0, s.getNbIndices()))) || ISLUtil.isEffectivelySaturated(c, s));
  }

  /**
   * Moves a constraint from the "unsaturated" list to the "saturated" list.
   */
  protected boolean moveConstraintToSaturated(final int idx) {
    boolean _xblockexpression = false;
    {
      final ISLConstraint equality = ISLUtil.toEqualityConstraint(this.unsaturatedConstraints.remove(Integer.valueOf(idx)));
      _xblockexpression = this.saturatedConstraints.add(equality);
    }
    return _xblockexpression;
  }

  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.unsaturatedConstraints== null) ? 0 : this.unsaturatedConstraints.hashCode());
    result = prime * result + ((this.saturatedConstraints== null) ? 0 : this.saturatedConstraints.hashCode());
    result = prime * result + ((this.space== null) ? 0 : this.space.hashCode());
    result = prime * result + this.originalConstraintCount;
    return prime * result + ((this.lattice== null) ? 0 : this.lattice.hashCode());
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
    Face other = (Face) obj;
    if (this.unsaturatedConstraints == null) {
      if (other.unsaturatedConstraints != null)
        return false;
    } else if (!this.unsaturatedConstraints.equals(other.unsaturatedConstraints))
      return false;
    if (this.saturatedConstraints == null) {
      if (other.saturatedConstraints != null)
        return false;
    } else if (!this.saturatedConstraints.equals(other.saturatedConstraints))
      return false;
    if (this.space == null) {
      if (other.space != null)
        return false;
    } else if (!this.space.equals(other.space))
      return false;
    if (other.originalConstraintCount != this.originalConstraintCount)
      return false;
    if (this.lattice == null) {
      if (other.lattice != null)
        return false;
    } else if (!this.lattice.equals(other.lattice))
      return false;
    return true;
  }

  @Pure
  public HashMap<Integer, ISLConstraint> getUnsaturatedConstraints() {
    return this.unsaturatedConstraints;
  }

  @Pure
  public ArrayList<ISLConstraint> getSaturatedConstraints() {
    return this.saturatedConstraints;
  }

  @Pure
  public ISLSpace getSpace() {
    return this.space;
  }

  @Pure
  public int getOriginalConstraintCount() {
    return this.originalConstraintCount;
  }

  @Pure
  public FaceLattice2 getLattice() {
    return this.lattice;
  }
}
