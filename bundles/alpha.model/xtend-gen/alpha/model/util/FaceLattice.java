package alpha.model.util;

import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Represents and constructs an entire face lattice from a basic set.
 */
@SuppressWarnings("all")
public class FaceLattice {
  /**
   * The storage of the lattice itself.
   * The index of each layer (the outermost list) is the dimensionality.
   * I.e., <code>lattice[2]</code> contains all the 2D faces of the lattice.
   * Each layer is a list of all the sets which are in that layer.
   */
  private final ArrayList<ArrayList<Face>> lattice;

  /**
   * Constructs the face lattice of the given set.
   */
  public FaceLattice(final ISLBasicSet root) {
    final Face rootFace = new Face(root, this);
    final int dimensionality = rootFace.getDimensionality();
    final Function1<Integer, ArrayList<Face>> _function = (Integer it) -> {
      return null;
    };
    this.lattice = CommonExtensions.<ArrayList<Face>>toArrayList(IterableExtensions.<Integer, ArrayList<Face>>map(new IntegerRange(0, dimensionality), _function));
    this.lattice.set(dimensionality, CommonExtensions.<Face>toArrayList(Collections.<Face>unmodifiableList(CollectionLiterals.<Face>newArrayList(rootFace))));
    ExclusiveRange _greaterThanDoubleDot = new ExclusiveRange(dimensionality, 0, false);
    for (final Integer dim : _greaterThanDoubleDot) {
      {
        final Function1<Face, ArrayList<Face>> _function_1 = (Face parent) -> {
          return parent.generateChildren();
        };
        final ArrayList<Face> current = Face.removeDuplicates(((Face[])Conversions.unwrapArray(IterableExtensions.<Face, Face>flatMap(this.lattice.get(((dim).intValue() + 1)), _function_1), Face.class)));
        this.lattice.set((dim).intValue(), current);
      }
    }
  }

  /**
   * Gets the set of faces of the given dimensionality.
   * 0-dimension is vertices, 1-dimension is lines, etc.
   * Throws an IllegalArgumentException if you provide an invalid dimension.
   */
  public List<Face> getFaces(final int dimension) {
    if (((dimension < 0) || (dimension >= this.lattice.size()))) {
      throw new IllegalArgumentException((("There is no dimension \'" + Integer.valueOf(dimension)) + "\' in the lattice."));
    }
    return Collections.<Face>unmodifiableList(this.lattice.get(dimension));
  }

  /**
   * Returns the face representing the original set provided.
   */
  public Face getRoot() {
    int _size = this.lattice.size();
    int _minus = (_size - 1);
    return this.getFaces(_minus).get(0);
  }

  /**
   * Gets all 0-faces (vertices).
   */
  public List<Face> getVertices() {
    return this.getFaces(0);
  }

  public ArrayList<ArrayList<Face>> getLattice() {
    return this.lattice;
  }
}
