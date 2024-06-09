package alpha.model.util;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
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
   * The way each facet can be labeled by one particular choice of reuse.
   */
  public enum Label {
    POS,

    NEG,

    ZERO;
  }

  public enum Boundary {
    WEAK,

    STRONG,

    NON;
  }

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
  private final FaceLattice lattice;

  /**
   * Constructs a new face which has no parents.
   */
  public Face(final ISLBasicSet basicSet) {
    this(basicSet, null);
  }

  /**
   * Creates a face to use as the root of a face lattice.
   */
  public Face(final ISLBasicSet basicSet, final FaceLattice lattice) {
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
  public Face(final HashMap<Integer, ISLConstraint> unsaturatedConstraints, final ArrayList<ISLConstraint> saturatedConstraints, final ISLSpace space, final int originalConstraintCount, final FaceLattice lattice) {
    this.unsaturatedConstraints = unsaturatedConstraints;
    this.saturatedConstraints = saturatedConstraints;
    this.space = space;
    this.originalConstraintCount = originalConstraintCount;
    this.lattice = lattice;
  }

  /**
   * Enumerates the set of all possible label combinations.
   */
  public static Iterable<ArrayList<Face.Label>> enumerateAllPossibleLabelings(final int nbFacets, final boolean includeNeg) {
    List<Face.Label> _xifexpression = null;
    if (includeNeg) {
      _xifexpression = Collections.<Face.Label>unmodifiableList(CollectionLiterals.<Face.Label>newArrayList(Face.Label.ZERO, Face.Label.POS, Face.Label.NEG));
    } else {
      _xifexpression = Collections.<Face.Label>unmodifiableList(CollectionLiterals.<Face.Label>newArrayList(Face.Label.POS, Face.Label.ZERO));
    }
    final List<Face.Label> labels = _xifexpression;
    final Function1<ArrayList<Face.Label>, Boolean> _function = (ArrayList<Face.Label> it) -> {
      return Boolean.valueOf(Face.isValid(((Face.Label[])Conversions.unwrapArray(it, Face.Label.class))));
    };
    return IterableExtensions.<ArrayList<Face.Label>>filter(CommonExtensions.<Face.Label>permutations(labels, nbFacets), _function);
  }

  /**
   * Returns True if labeling contains at least one POS and NEG label, or false otherwise
   */
  public static boolean isValid(final Face.Label... labeling) {
    return (IterableExtensions.<Face.Label>exists(((Iterable<Face.Label>)Conversions.doWrapArray(labeling)), ((Function1<Face.Label, Boolean>) (Face.Label it) -> {
      return Boolean.valueOf(Objects.equal(it, Face.Label.POS));
    })) && IterableExtensions.<Face.Label>exists(((Iterable<Face.Label>)Conversions.doWrapArray(labeling)), ((Function1<Face.Label, Boolean>) (Face.Label it) -> {
      return Boolean.valueOf(Objects.equal(it, Face.Label.NEG));
    })));
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
   * Returns the domain D such that any vector within induces a particular labeling among the facets.
   * Here, the word "face" refers to a node in the lattice and "facets" (with a 't') as the direct
   * children of that particular "face".
   * face:
   * - facet1
   * - facet2
   * - facet3
   * ...
   * There are 3 possible labels for a facet: POS,NEG,ZERO.
   * Each facet is said to be either an POS-facet, an NEG-facet, or an ZERO-facet.
   * 
   * By definition, the linear space of each facet differs from its face by a single inequality constraint
   * "c" (ISLConstraint). The index coefficients of "c" represent the normal vector "v" (ISLAff) to the facet.
   * 
   * A particular facet can be made an:
   * - POS-facet:  new constraint with coefficients of "v" that >0
   * - NEG-facet:  new constraint with coefficients of "v" that <0
   * - ZERO-facet: new constraint with coefficients of "v" that =0
   */
  public Pair<Face.Label[], ISLBasicSet> getLabelingDomain(final Face.Label... labeling) {
    final ArrayList<Face> facets = this.generateChildren();
    int _size = facets.size();
    int _size_1 = ((List<Face.Label>)Conversions.doWrapArray(labeling)).size();
    boolean _notEquals = (_size != _size_1);
    if (_notEquals) {
      throw new IllegalArgumentException("Must specify a label for every facet to get a labeling domain.");
    }
    final Function1<Face, ISLAff> _function = (Face child) -> {
      return child.getNormalVector(this);
    };
    final Function1<Pair<ISLAff, Face.Label>, ISLConstraint> _function_1 = (Pair<ISLAff, Face.Label> pair) -> {
      return this.toLabelInducingConstraint(pair.getKey(), pair.getValue());
    };
    final Function2<ISLBasicSet, ISLConstraint, ISLBasicSet> _function_2 = (ISLBasicSet s, ISLConstraint c) -> {
      return s.addConstraint(c);
    };
    final ISLBasicSet domain = IterableExtensions.<ISLConstraint, ISLBasicSet>fold(ListExtensions.<Pair<ISLAff, Face.Label>, ISLConstraint>map(CommonExtensions.<ISLAff, Face.Label>zipWith(ListExtensions.<Face, ISLAff>map(facets, _function), ((Iterable<Face.Label>)Conversions.doWrapArray(labeling))), _function_1), this.toLinearSpace(), _function_2).dropConstraintsInvolvingDims(ISLDimType.isl_dim_param, 0, this.space.getNbParams()).removeRedundancies();
    return Pair.<Face.Label[], ISLBasicSet>of(labeling, domain);
  }

  public ISLConstraint toLabelInducingConstraint(final ISLAff vector, final Face.Label label) {
    try {
      ISLConstraint _xblockexpression = null;
      {
        final ISLAff vectorInAffineSpace = AlphaUtil.renameDims(vector.copy().addDims(ISLDimType.isl_dim_param, this.space.getNbParams()), ISLDimType.isl_dim_param, ((String[])Conversions.unwrapArray(this.space.getParamNames(), String.class)));
        ISLConstraint _switchResult = null;
        if (label != null) {
          switch (label) {
            case POS:
              _switchResult = vectorInAffineSpace.addConstant((-1)).toInequalityConstraint();
              break;
            case NEG:
              _switchResult = vectorInAffineSpace.negate().addConstant((-1)).toInequalityConstraint();
              break;
            case ZERO:
              _switchResult = vectorInAffineSpace.addConstant(0).toEqualityConstraint();
              break;
            default:
              throw new Exception((("Label " + label) + " is not supported"));
          }
        } else {
          throw new Exception((("Label " + label) + " is not supported"));
        }
        _xblockexpression = _switchResult;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Returns the normal vector (as a single affine expression)
   * of the inequality characterizing this face in the context of its parent.
   */
  public ISLAff getNormalVector(final Face parent) {
    final Function1<Integer, Boolean> _function = (Integer idx) -> {
      return Boolean.valueOf(this.unsaturatedConstraints.containsKey(idx));
    };
    final Integer characteristicInequalityIndex = IterableExtensions.<Integer>head(IterableExtensions.<Integer>reject(parent.unsaturatedConstraints.keySet(), _function));
    final ISLConstraint characteristicInequality = parent.unsaturatedConstraints.get(characteristicInequalityIndex);
    return characteristicInequality.copy().getAff().dropDims(ISLDimType.isl_dim_param, 0, this.space.getNbParams()).setConstant(0);
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
   * Creates the (potentially unbounded) linear space of this facet
   * from the union of saturated constraints.
   */
  public ISLBasicSet toLinearSpace() {
    final ISLBasicSet universe = ISLBasicSet.buildUniverse(this.space.copy());
    final int nbParams = this.space.dim(ISLDimType.isl_dim_param);
    final Function1<ISLConstraint, ISLConstraint> _function = (ISLConstraint c) -> {
      return c.copy().setConstant(0);
    };
    final Function1<ISLConstraint, Iterable<ISLConstraint>> _function_1 = (ISLConstraint c) -> {
      final Function1<Integer, ISLConstraint> _function_2 = (Integer i) -> {
        return c.copy().setCoefficient(ISLDimType.isl_dim_param, (i).intValue(), 0);
      };
      return IterableExtensions.<Integer, ISLConstraint>map(new ExclusiveRange(0, nbParams, true), _function_2);
    };
    final Function2<ISLBasicSet, ISLConstraint, ISLBasicSet> _function_2 = (ISLBasicSet s, ISLConstraint c) -> {
      return s.addConstraint(c);
    };
    return IterableExtensions.<ISLConstraint, ISLBasicSet>fold(Iterables.<ISLConstraint>concat(ListExtensions.<ISLConstraint, Iterable<ISLConstraint>>map(ListExtensions.<ISLConstraint, ISLConstraint>map(this.saturatedConstraints, _function), _function_1)), universe, _function_2);
  }

  /**
   * Returns the boundary classification of the face.
   */
  public Face.Boundary boundaryLabel(final ISLSet accumulationSpace) {
    final ISLSet lp = this.toLinearSpace().toSet();
    final int accDims = ISLUtil.dimensionality(accumulationSpace);
    final int intDims = ISLUtil.dimensionality(accumulationSpace.copy().intersect(lp));
    if ((intDims == accDims)) {
      return Face.Boundary.STRONG;
    } else {
      if (((0 < intDims) && (intDims < accDims))) {
        return Face.Boundary.WEAK;
      } else {
        return Face.Boundary.NON;
      }
    }
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

  /**
   * Removes duplicate faces from a list of faces.
   */
  protected static ArrayList<Face> removeDuplicates(final Face... faces) {
    final Function1<Face, String> _function = (Face face) -> {
      return face.toString();
    };
    return CommonExtensions.<Face>toArrayList(IterableExtensions.<String, Face>toMap(((Iterable<? extends Face>)Conversions.doWrapArray(faces)), _function).values());
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
  public FaceLattice getLattice() {
    return this.lattice;
  }
}
