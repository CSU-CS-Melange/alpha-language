package alpha.model.util;

import alpha.model.matrix.MatrixOperations;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Constructs the face lattice of a given <code>ISLBasicSet</code>.
 * Usage: to create the face lattice, call the static function: <code>FaceLattice.create(root)</code>.
 */
@SuppressWarnings("all")
public class FaceLattice {
  /**
   * The way each facet can be labeled by one particular choice of reuse
   */
  public enum Label {
    POS,

    NEG,

    ZERO;
  }

  /**
   * The information about the set which forms the root of the lattice.
   */
  @Accessors(AccessorType.PUBLIC_GETTER)
  public Facet rootInfo;

  /**
   * The storage of the lattice itself.
   * The index of each layer (the outermost list) is the dimensionality.
   * I.e., <code>lattice[2]</code> contains all the 2D faces of the lattice.
   * Each layer is a list of all the sets which are in that layer.
   */
  @Accessors(AccessorType.PUBLIC_GETTER)
  public ArrayList<ArrayList<Facet>> lattice;

  /**
   * Converts decimal value to base radix and converts to a list of N digits padded with leading zeros
   */
  public static int[] toPaddedDigitArray(final int value, final int radix, final int N) {
    try {
      final String radixValue = Integer.toString(value, radix);
      final Function1<Character, Integer> _function = (Character v) -> {
        return Integer.valueOf(Integer.parseInt(v.toString(), radix));
      };
      final List<Integer> digitArray = ListExtensions.<Character, Integer>map(((List<Character>)Conversions.doWrapArray(radixValue.toCharArray())), _function);
      int _size = digitArray.size();
      final int padSize = (N - _size);
      if ((padSize < 0)) {
        throw new Exception((((((("Value " + Integer.valueOf(value)) + " in radix ") + Integer.valueOf(radix)) + " requires more than ") + Integer.valueOf(N)) + " digits"));
      }
      final Function1<Integer, Integer> _function_1 = (Integer it) -> {
        return Integer.valueOf(0);
      };
      final Iterable<Integer> pad = IterableExtensions.<Integer, Integer>map(new ExclusiveRange(0, padSize, true), _function_1);
      return ((int[])Conversions.unwrapArray(Iterables.<Integer>concat(pad, digitArray), int.class));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Constructs a new, empty lattice.
   */
  private FaceLattice() {
    this.rootInfo = null;
    ArrayList<ArrayList<Facet>> _arrayList = new ArrayList<ArrayList<Facet>>();
    this.lattice = _arrayList;
  }

  /**
   * Creates a new face lattice for the given set.
   */
  public static FaceLattice create(final ISLSet root) {
    try {
      FaceLattice _xblockexpression = null;
      {
        int _nbBasicSets = root.getNbBasicSets();
        boolean _greaterThan = (_nbBasicSets > 1);
        if (_greaterThan) {
          throw new Exception("Face lattice construction can only be done for a single basic set");
        }
        _xblockexpression = FaceLattice.create(root.getBasicSetAt(0));
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static FaceLattice create(final ISLSet root, final boolean fullLattice) {
    try {
      FaceLattice _xblockexpression = null;
      {
        int _nbBasicSets = root.getNbBasicSets();
        boolean _greaterThan = (_nbBasicSets > 1);
        if (_greaterThan) {
          throw new Exception("Face lattice construction can only be done for a single basic set");
        }
        _xblockexpression = FaceLattice.create(root.getBasicSetAt(0), fullLattice);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static FaceLattice create(final ISLBasicSet root) {
    return FaceLattice.create(root, true);
  }

  /**
   * Creates a new face lattice for the given set.
   * 
   * @param   root        The set to use as the root of the lattice.
   * @param   fullLattice If <code>true</code>, generate the full lattice.
   *                      Otherwise, only generate the partial lattice from saturating exactly one inequality.
   * @returns An instance of the face lattice (or partial lattice) for the root.
   */
  public static FaceLattice create(final ISLBasicSet root, final boolean fullLattice) {
    final FaceLattice lattice = new FaceLattice();
    final Facet rootInfo = new Facet(root, lattice);
    lattice.rootInfo = rootInfo;
    final LinkedList<ArrayList<Integer>> toSaturate = new LinkedList<ArrayList<Integer>>();
    ArrayList<Integer> _arrayList = new ArrayList<Integer>();
    toSaturate.add(_arrayList);
    while ((!toSaturate.isEmpty())) {
      {
        final ArrayList<Integer> currentConstraints = toSaturate.remove();
        final boolean isValidFace = lattice.checkAddFace(currentConstraints);
        final boolean hasChildren = ((fullLattice && isValidFace) && (currentConstraints.size() < rootInfo.getDimensionality()));
        if (hasChildren) {
          int _xifexpression = (int) 0;
          boolean _isEmpty = currentConstraints.isEmpty();
          if (_isEmpty) {
            _xifexpression = 0;
          } else {
            Integer _last = IterableExtensions.<Integer>last(currentConstraints);
            _xifexpression = ((_last).intValue() + 1);
          }
          final int minimumIndex = _xifexpression;
          int _indexInequalityCount = rootInfo.getIndexInequalityCount();
          ExclusiveRange _doubleDotLessThan = new ExclusiveRange(minimumIndex, _indexInequalityCount, true);
          for (final Integer constraint : _doubleDotLessThan) {
            {
              final ArrayList<Integer> nextSet = new ArrayList<Integer>(currentConstraints);
              nextSet.add(constraint);
              toSaturate.add(nextSet);
            }
          }
        }
      }
    }
    lattice.removeRedundancies();
    return lattice;
  }

  /**
   * Gets the set of all children of the indicated face.
   */
  public Iterable<Facet> getChildren(final Facet face) {
    final int faceLayer = Integer.max(0, face.getDimensionality());
    if (((faceLayer == 0) || (faceLayer >= this.lattice.size()))) {
      return new ArrayList<Facet>();
    }
    final Function1<Facet, Boolean> _function = (Facet node) -> {
      return Boolean.valueOf(node.isChildOf(face));
    };
    return IterableExtensions.<Facet>filter(this.lattice.get((faceLayer - 1)), _function);
  }

  /**
   * Returns the set of all faces, not organized by dimension.
   */
  public Iterable<Facet> getAllFaces() {
    return Iterables.<Facet>concat(this.lattice);
  }

  /**
   * Indicates whether or not the set used as the root of the lattice is simplicial (hyper-triangular) or not.
   */
  public boolean isSimplicial() {
    boolean _isEmpty = this.rootInfo.isEmpty();
    if (_isEmpty) {
      return false;
    }
    boolean _isBounded = this.rootInfo.isBounded();
    boolean _not = (!_isBounded);
    if (_not) {
      return false;
    }
    final ArrayList<Facet> zeroFaces = this.lattice.get(0);
    if ((zeroFaces == null)) {
      return false;
    }
    int _size = zeroFaces.size();
    int _dimensionality = this.rootInfo.getDimensionality();
    int _plus = (_dimensionality + 1);
    boolean _notEquals = (_size != _plus);
    if (_notEquals) {
      return false;
    }
    return true;
  }

  /**
   * Returns the domain D such that any vector within induces a particular labeling among the facets.
   *  Here, the word "face" refers to a node in the lattice and "facets" (with a 't') as the direct
   *  children of that particular "face".
   *  face:
   *  - facet1
   *  - facet2
   *  - facet3
   *  ...
   *  There are 3 possible labels for a facet: POS,NEG,ZERO.
   *  Each facet is said to be either an POS-facet, an NEG-facet, or an ZERO-facet.
   * 
   *  By definition, the linear space of each facet differs from its face by a single inequality constraint
   *  "c" (ISLConstraint). The index coefficients of "c" represent the normal vector "v" (ISLAff) to the facet.
   * 
   *  A particular facet can be made an:
   *  - POS-facet:  new constraint with coefficients of "v" that >0
   *  - NEG-facet:  new constraint with coefficients of "v" that <0
   *  - ZERO-facet: new constraint with coefficients of "v" that =0
   */
  public Pair<FaceLattice.Label[], ISLBasicSet> getLabelingDomain(final Facet face, final FaceLattice.Label[] labeling) {
    try {
      final Iterable<Facet> facets = this.getChildren(face);
      final int nbFacets = IterableExtensions.size(facets);
      int _size = ((List<FaceLattice.Label>)Conversions.doWrapArray(labeling)).size();
      boolean _notEquals = (nbFacets != _size);
      if (_notEquals) {
        throw new Exception("Must specify a label for every facet to get a labeling domain");
      }
      final Function1<Facet, ISLAff> _function = (Facet f) -> {
        return f.getNormalVector(face);
      };
      final Iterable<ISLAff> normalVectors = IterableExtensions.<Facet, ISLAff>map(facets, _function);
      final Function1<Integer, ISLConstraint> _function_1 = (Integer i) -> {
        return this.toLabelInducingConstraint(((ISLAff[])Conversions.unwrapArray(normalVectors, ISLAff.class))[(i).intValue()], face.getSpace(), labeling[(i).intValue()]);
      };
      final Iterable<ISLConstraint> constraints = IterableExtensions.<Integer, ISLConstraint>map(new ExclusiveRange(0, nbFacets, true), _function_1);
      ISLBasicSet domain = ISLBasicSet.buildUniverse(face.getSpace().copy());
      for (final ISLConstraint constraint : constraints) {
        domain = domain.addConstraint(constraint);
      }
      domain = domain.dropConstraintsInvolvingDims(ISLDimType.isl_dim_param, 0, face.getSpace().getNbParams());
      ISLBasicSet _removeRedundancies = domain.removeRedundancies();
      return Pair.<FaceLattice.Label[], ISLBasicSet>of(labeling, _removeRedundancies);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Creates a constraint in the specified "space" that induces a labeling "label"
   */
  public ISLConstraint toLabelInducingConstraint(final ISLAff vector, final ISLSpace space, final FaceLattice.Label label) {
    try {
      ISLConstraint _xblockexpression = null;
      {
        final ISLAff vectorInAffineSpace = AlphaUtil.renameDims(vector.copy().addDims(ISLDimType.isl_dim_param, space.getNbParams()), ISLDimType.isl_dim_param, space.getParamNames());
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
   * Creates the set of all possible label combinations
   */
  public List<List<FaceLattice.Label>> enumerateAllPossibleLabelings(final FaceLattice.Label[] validLabels, final int nbFacets) {
    List<List<FaceLattice.Label>> _xblockexpression = null;
    {
      final Function1<Integer, Integer> _function = (Integer it) -> {
        return Integer.valueOf(((List<FaceLattice.Label>)Conversions.doWrapArray(validLabels)).size());
      };
      final Function2<Integer, Integer, Integer> _function_1 = (Integer v1, Integer v2) -> {
        return Integer.valueOf(((v1).intValue() * (v2).intValue()));
      };
      final Integer numCombos = IterableExtensions.<Integer>reduce(IterableExtensions.<Integer, Integer>map(new ExclusiveRange(0, nbFacets, true), _function), _function_1);
      final Function1<Integer, List<FaceLattice.Label>> _function_2 = (Integer value) -> {
        final Function1<Integer, FaceLattice.Label> _function_3 = (Integer i) -> {
          return validLabels[(i).intValue()];
        };
        return IterableExtensions.<FaceLattice.Label>toList(ListExtensions.<Integer, FaceLattice.Label>map(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray((value).intValue(), ((List<FaceLattice.Label>)Conversions.doWrapArray(validLabels)).size(), nbFacets))), _function_3));
      };
      final List<List<FaceLattice.Label>> labelings = IterableExtensions.<List<FaceLattice.Label>>toList(IterableExtensions.<Integer, List<FaceLattice.Label>>map(new ExclusiveRange(0, (numCombos).intValue(), true), _function_2));
      _xblockexpression = labelings;
    }
    return _xblockexpression;
  }

  /**
   * Identifies the labels induced by a particular vector
   */
  public FaceLattice.Label[] getLabeling(final Facet facet, final long[] reuseVector) {
    final Iterable<Facet> children = this.getChildren(facet);
    final Function1<Facet, long[]> _function = (Facet f) -> {
      return f.getLongNormalVector(facet);
    };
    final Iterable<long[]> normalVectors = IterableExtensions.<Facet, long[]>map(children, _function);
    final Function1<long[], FaceLattice.Label> _function_1 = (long[] normalVector) -> {
      try {
        FaceLattice.Label _xblockexpression = null;
        {
          final long dotProduct = MatrixOperations.innerProduct(reuseVector, normalVector);
          FaceLattice.Label _switchResult = null;
          boolean _matched = false;
          if ((dotProduct > 0)) {
            _matched=true;
            _switchResult = FaceLattice.Label.POS;
          }
          if (!_matched) {
            if ((dotProduct < 0)) {
              _matched=true;
              _switchResult = FaceLattice.Label.NEG;
            }
          }
          if (!_matched) {
            if ((dotProduct == 0)) {
              _matched=true;
              _switchResult = FaceLattice.Label.ZERO;
            }
          }
          if (!_matched) {
            throw new Exception(("Failed to get labeling for " + reuseVector));
          }
          _xblockexpression = _switchResult;
        }
        return _xblockexpression;
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    final Iterable<FaceLattice.Label> labelings = IterableExtensions.<long[], FaceLattice.Label>map(normalVectors, _function_1);
    return ((FaceLattice.Label[])Conversions.unwrapArray(labelings, FaceLattice.Label.class));
  }

  /**
   * Returns <code>true</code> if the root has at least one thick face, and <code>false</code> otherwise.
   */
  public boolean hasThickFaces() {
    int _nbRows = this.rootInfo.getThickEqualities().getNbRows();
    return (_nbRows > 0);
  }

  /**
   * Checks if a face is valid to add to the lattice, and adds it if so.
   * @returns Returns <code>true</code> if the face was valid, didn't exist already, and was added.
   * 			Otherwise, returns <code>false</code>.
   */
  private boolean checkAddFace(final ArrayList<Integer> toSaturate) {
    final Facet face = Facet.createFace(this.rootInfo, toSaturate);
    boolean _isValidFace = face.isValidFace(this.rootInfo);
    boolean _not = (!_isValidFace);
    if (_not) {
      return false;
    }
    final int layerIndex = Integer.max(0, face.getDimensionality());
    while ((this.lattice.size() <= layerIndex)) {
      ArrayList<Facet> _arrayList = new ArrayList<Facet>();
      this.lattice.add(_arrayList);
    }
    final ArrayList<Facet> layer = this.lattice.get(layerIndex);
    final Function1<Facet, Boolean> _function = (Facet other) -> {
      return Boolean.valueOf(face.isDuplicateOf(other));
    };
    boolean _exists = IterableExtensions.<Facet>exists(layer, _function);
    if (_exists) {
      return false;
    }
    layer.add(face);
    return true;
  }

  /**
   * Removes all redundant facets from the entire lattice.
   */
  private void removeRedundancies() {
    int _size = this.lattice.size();
    final Consumer<Integer> _function = (Integer dimension) -> {
      this.removeRedundancies((dimension).intValue());
    };
    new ExclusiveRange(0, _size, true).forEach(_function);
  }

  /**
   * Removes all redundant facets from a specific layer of the lattice.
   */
  private void removeRedundancies(final int dimension) {
    if (((dimension < 0) || (dimension >= this.lattice.size()))) {
      return;
    }
    final ArrayList<Facet> currentLayer = this.lattice.get(dimension);
    int _dimensionality = this.rootInfo.getDimensionality();
    final int expectedSaturations = (_dimensionality - dimension);
    final Function1<Facet, Boolean> _function = (Facet facet) -> {
      int _size = facet.getSaturatedInequalityIndices().size();
      return Boolean.valueOf((_size < expectedSaturations));
    };
    List<Facet> _list = IterableExtensions.<Facet>toList(IterableExtensions.<Facet>filter(currentLayer, _function));
    final ArrayList<Facet> facetsWithAdditionalSaturations = new ArrayList<Facet>(_list);
    boolean _isEmpty = facetsWithAdditionalSaturations.isEmpty();
    if (_isEmpty) {
      return;
    }
    final Function1<Facet, ArrayList<Integer>> _function_1 = (Facet facet) -> {
      return FaceLattice.getUnionOfSupersets(facet, currentLayer);
    };
    final Consumer<ArrayList<Integer>> _function_2 = (ArrayList<Integer> toSaturate) -> {
      this.checkAddFace(toSaturate);
    };
    ListExtensions.<Facet, ArrayList<Integer>>map(facetsWithAdditionalSaturations, _function_1).forEach(_function_2);
    final Function1<Facet, Boolean> _function_3 = (Facet facet) -> {
      final Function1<Facet, Boolean> _function_4 = (Facet other) -> {
        return Boolean.valueOf(facet.isStrictSubsetOf(other));
      };
      return Boolean.valueOf(IterableExtensions.<Facet>exists(currentLayer, _function_4));
    };
    List<Facet> _list_1 = IterableExtensions.<Facet>toList(IterableExtensions.<Facet>filter(currentLayer, _function_3));
    final ArrayList<Facet> toRemove = new ArrayList<Facet>(_list_1);
    currentLayer.removeAll(toRemove);
  }

  /**
   * Gets all facets which are a superset of the given one,
   * returning the union of their saturated inequalities.
   */
  private static ArrayList<Integer> getUnionOfSupersets(final Facet facet, final Collection<Facet> toSearch) {
    final Function1<Facet, Boolean> _function = (Facet other) -> {
      return Boolean.valueOf(facet.isStrictSubsetOf(other));
    };
    final Function1<Facet, ArrayList<Integer>> _function_1 = (Facet superset) -> {
      return superset.getSaturatedInequalityIndices();
    };
    Set<Integer> _set = IterableExtensions.<Integer>toSet(Iterables.<Integer>concat(IterableExtensions.<Facet, ArrayList<Integer>>map(IterableExtensions.<Facet>filter(toSearch, _function), _function_1)));
    return new ArrayList<Integer>(_set);
  }

  @Pure
  public Facet getRootInfo() {
    return this.rootInfo;
  }

  @Pure
  public ArrayList<ArrayList<Facet>> getLattice() {
    return this.lattice;
  }
}
