package alpha.model.util;

import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
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
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final Facet rootInfo;

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
  private FaceLattice(final Facet rootInfo) {
    this.rootInfo = rootInfo;
    ArrayList<ArrayList<Facet>> _arrayList = new ArrayList<ArrayList<Facet>>();
    this.lattice = _arrayList;
  }

  /**
   * Creates a new face lattice for the given set.
   */
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
    final Facet rootInfo = new Facet(root);
    final FaceLattice lattice = new FaceLattice(rootInfo);
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
