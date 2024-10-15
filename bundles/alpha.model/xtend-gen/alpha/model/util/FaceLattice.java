package alpha.model.util;

import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.eclipse.xtext.xbase.lib.Pure;

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
  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final ArrayList<ArrayList<Face>> lattice;

  /**
   * Creates the face lattice of the given set if it is convex
   */
  public static FaceLattice create(final ISLSet root) {
    try {
      FaceLattice _xblockexpression = null;
      {
        int _nbBasicSets = root.getNbBasicSets();
        boolean _notEquals = (_nbBasicSets != 1);
        if (_notEquals) {
          throw new Exception("Face lattice construction expects the input set to be convex");
        }
        ISLBasicSet _basicSetAt = root.getBasicSetAt(0);
        _xblockexpression = new FaceLattice(_basicSetAt);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Creates the face lattice of the given set.
   */
  public static FaceLattice create(final ISLBasicSet root) {
    return new FaceLattice(root);
  }

  /**
   * Internal construct to build the face lattice of the given set.
   */
  protected FaceLattice(final ISLBasicSet root) {
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

  public String prettyPrint() {
    String _xblockexpression = null;
    {
      final Face root = this.getRoot();
      final int d = root.getDimensionality();
      final ArrayList<String> ret = CollectionLiterals.<String>newArrayList();
      ret.add("Lattice:");
      final Procedure2<ArrayList<Face>, Integer> _function = (ArrayList<Face> faces, Integer i) -> {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("  ");
        _builder.append((d - (i).intValue()), "  ");
        _builder.append("-faces: ");
        String _join = IterableExtensions.join(faces, ", ");
        _builder.append(_join, "  ");
        ret.add(_builder.toString());
      };
      IterableExtensions.<ArrayList<Face>>forEach(ListExtensions.<ArrayList<Face>>reverse(this.lattice), _function);
      ret.add("Constraints:");
      final Function1<Map.Entry<Integer, ISLConstraint>, String> _function_1 = (Map.Entry<Integer, ISLConstraint> it) -> {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("  ");
        Integer _key = it.getKey();
        _builder.append(_key, "  ");
        _builder.append(": ");
        ISLConstraint _value = it.getValue();
        _builder.append(_value, "  ");
        return _builder.toString();
      };
      Iterable<String> _map = IterableExtensions.<Map.Entry<Integer, ISLConstraint>, String>map(root.getUnsaturatedConstraints().entrySet(), _function_1);
      Iterables.<String>addAll(ret, _map);
      ret.add("Faces:");
      final Function1<Face, String> _function_2 = (Face it) -> {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("  ");
        String _string = it.toString();
        _builder.append(_string, "  ");
        _builder.append(": ");
        ISLBasicSet _basicSet = it.toBasicSet();
        _builder.append(_basicSet, "  ");
        return _builder.toString();
      };
      Iterable<String> _map_1 = IterableExtensions.<Face, String>map(Iterables.<Face>concat(this.lattice), _function_2);
      Iterables.<String>addAll(ret, _map_1);
      _xblockexpression = IterableExtensions.join(ret, "\n");
    }
    return _xblockexpression;
  }

  @Pure
  public ArrayList<ArrayList<Face>> getLattice() {
    return this.lattice;
  }
}
