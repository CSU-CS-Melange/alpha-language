package alpha.model.util;

import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionExtensions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Constructs the face lattice of a given <code>ISLBasicSet</code>.
 * Usage: to create the face lattice, call the static function: <code>FaceLattice.create(root)</code>.
 */
@SuppressWarnings("all")
public class FaceLattice {
  /**
   * The information about the set which forms the root of the lattice.
   */
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PRIVATE_SETTER })
  private Facet rootInfo;

  /**
   * The storage of the lattice itself.
   * The index of each layer (the outermost list) is the dimensionality.
   * I.e., <code>lattice[2]</code> contains all the 2D faces of the lattice.
   * Each layer is a list of all the sets which are in that layer.
   */
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final ArrayList<ArrayList<Facet>> lattice;

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
  public static FaceLattice create(final ISLBasicSet root) {
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

  private void removeRedundancies() {
    int _size = this.lattice.size();
    final Function1<Integer, Boolean> _function = (Integer k) -> {
      boolean _xblockexpression = false;
      {
        final ArrayList<Facet> kFacets = this.lattice.get((k).intValue());
        int _dimensionality = this.rootInfo.getDimensionality();
        final int expectedSaturations = (_dimensionality - (k).intValue());
        final Function1<Facet, Boolean> _function_1 = (Facet kFacet) -> {
          int _size_1 = kFacet.getSaturatedInequalityIndices().size();
          return Boolean.valueOf((_size_1 != expectedSaturations));
        };
        final Iterable<Facet> strangeKFacets = IterableExtensions.<Facet>filter(kFacets, _function_1);
        boolean _isEmpty = IterableExtensions.isEmpty(strangeKFacets);
        _xblockexpression = (!_isEmpty);
      }
      return Boolean.valueOf(_xblockexpression);
    };
    final Iterable<Integer> layersWithStrangeKFacets = IterableExtensions.<Integer>filter(new ExclusiveRange(0, _size, true), _function);
    for (final Integer k : layersWithStrangeKFacets) {
      {
        final ArrayList<Facet> kFacets = this.lattice.get((k).intValue());
        int _dimensionality = this.rootInfo.getDimensionality();
        final int expectedSaturations = (_dimensionality - (k).intValue());
        final Function1<Facet, Boolean> _function_1 = (Facet kFacet) -> {
          int _size_1 = kFacet.getSaturatedInequalityIndices().size();
          return Boolean.valueOf((_size_1 != expectedSaturations));
        };
        final Iterable<Facet> strangeKFacets = IterableExtensions.<Facet>filter(kFacets, _function_1);
        final Function1<Facet, Iterable<Facet>> _function_2 = (Facet strangeKFacet) -> {
          final Function1<Facet, Boolean> _function_3 = (Facet kFacet) -> {
            return Boolean.valueOf(IterableExtensions.<Integer>toSet(kFacet.getSaturatedInequalityIndices()).containsAll(IterableExtensions.<Integer>toSet(strangeKFacet.getSaturatedInequalityIndices())));
          };
          return IterableExtensions.<Facet>filter(kFacets, _function_3);
        };
        final Iterable<Iterable<Facet>> duplicateKFacetsForStrangeKFacets = IterableExtensions.<Facet, Iterable<Facet>>map(strangeKFacets, _function_2);
        final Function2<Iterable<Facet>, Iterable<Facet>, Iterable<Facet>> _function_3 = (Iterable<Facet> fs1, Iterable<Facet> fs2) -> {
          return Iterables.<Facet>concat(fs1, fs2);
        };
        final Function1<Facet, Set<Integer>> _function_4 = (Facet kFacet) -> {
          return IterableExtensions.<Integer>toSet(kFacet.getSaturatedInequalityIndices());
        };
        final Set<Set<Integer>> duplicateIndices = IterableExtensions.<Set<Integer>>toSet(IterableExtensions.<Facet, Set<Integer>>map(IterableExtensions.<Iterable<Facet>>reduce(duplicateKFacetsForStrangeKFacets, _function_3), _function_4));
        final Function1<Iterable<Facet>, HashSet<Integer>> _function_5 = (Iterable<Facet> duplicateKFacetsForStrangeKFacet) -> {
          final HashSet<Integer> union = new HashSet<Integer>();
          for (final Facet kFacet : duplicateKFacetsForStrangeKFacet) {
            union.addAll(kFacet.getSaturatedInequalityIndices());
          }
          return union;
        };
        final Set<HashSet<Integer>> uniqueKFacetsIndices = IterableExtensions.<HashSet<Integer>>toSet(IterableExtensions.<Iterable<Facet>, HashSet<Integer>>map(duplicateKFacetsForStrangeKFacets, _function_5));
        final Function1<HashSet<Integer>, Facet> _function_6 = (HashSet<Integer> ids) -> {
          Facet _xblockexpression = null;
          {
            final ArrayList<Integer> idsArr = new ArrayList<Integer>();
            idsArr.addAll(ids);
            _xblockexpression = Facet.createFace(this.rootInfo, idsArr);
          }
          return _xblockexpression;
        };
        final Iterable<Facet> uniqueKFacets = IterableExtensions.<HashSet<Integer>, Facet>map(uniqueKFacetsIndices, _function_6);
        final Function1<Facet, Boolean> _function_7 = (Facet kFacet) -> {
          return Boolean.valueOf(duplicateIndices.contains(IterableExtensions.<Integer>toSet(kFacet.getSaturatedInequalityIndices())));
        };
        CollectionExtensions.<Facet>removeAll(kFacets, IterableExtensions.<Facet>filter(kFacets, _function_7));
        Iterables.<Facet>addAll(kFacets, uniqueKFacets);
      }
    }
  }

  @Pure
  public Facet getRootInfo() {
    return this.rootInfo;
  }

  private void setRootInfo(final Facet rootInfo) {
    this.rootInfo = rootInfo;
  }

  @Pure
  public ArrayList<ArrayList<Facet>> getLattice() {
    return this.lattice;
  }
}
