package alpha.model.util;

import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
  private Facet rootInfo;

  /**
   * The storage of the lattice itself.
   * The index of each layer (the outermost list) is the dimensionality.
   * I.e., <code>lattice[2]</code> contains all the 2D faces of the lattice.
   * Each layer is a list of all the sets which are in that layer.
   */
  @Accessors(AccessorType.PUBLIC_GETTER)
  private ArrayList<ArrayList<Facet>> lattice;

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
  private FaceLattice(final Facet rootInfo) {
    this.rootInfo = rootInfo;
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

  public static FaceLattice create(final ISLBasicSet root) {
    final Facet rootInfo = new Facet(root);
    final FaceLattice lattice = new FaceLattice(rootInfo);
    final LinkedList<ArrayList<Integer>> toSaturate = new LinkedList<ArrayList<Integer>>();
    ArrayList<Integer> _arrayList = new ArrayList<Integer>();
    toSaturate.add(_arrayList);
    while ((!toSaturate.isEmpty())) {
      {
        final ArrayList<Integer> currentConstraints = toSaturate.remove();
        final boolean isValidFace = lattice.checkAddFace(currentConstraints);
        final boolean hasChildren = (isValidFace && (currentConstraints.size() < rootInfo.getDimensionality()));
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
        return f.getNormalVector();
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
   * Checks if a face is valid to add to the lattice, and adds it if so.
   * @returns Returns <code>true</code> if the face was valid and added, and <code>false</code> otherwise.
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
    this.lattice.get(layerIndex).add(face);
    return true;
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
