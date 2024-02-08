package alpha.model.tests.util;

import alpha.model.util.AlphaUtil;
import alpha.model.util.FaceLattice;
import alpha.model.util.Facet;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLVertex;
import fr.irisa.cairn.jnimap.isl.ISLVertices;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class FaceLatticeTest {
  /**
   * Asserts the correct number of layers in the lattice,
   * and that each layer has the correct number of nodes.
   * 
   * @param lattice    The lattice to check.
   * @param faceCounts The number of nodes to expect in each layer, sorted from 0-face to N-faces.
   */
  private static void assertFaceCounts(final FaceLattice lattice, final int... faceCounts) {
    final ArrayList<ArrayList<Facet>> latticeStorage = lattice.getLattice();
    Assert.assertEquals(faceCounts.length, latticeStorage.size());
    int _length = faceCounts.length;
    final Consumer<Integer> _function = (Integer dim) -> {
      Assert.assertEquals(latticeStorage.get((dim).intValue()).size(), faceCounts[(dim).intValue()]);
    };
    new ExclusiveRange(0, _length, true).forEach(_function);
  }

  /**
   * Asserts that a face exists in the lattice, and that it has the correct child faces.
   * Reminder: a child node saturates all the inequalities of its parents, plus one more.
   * 
   * @param lattice               The lattice to check.
   * @param saturatedInequalities The inequalities that the desired face saturates.
   * @param addedInequalities     The additional inequalities that the children can saturate (one per child).
   */
  private static void assertFaceHasChildren(final FaceLattice lattice, final List<Integer> saturatedInequalities, final int... addedInequalities) {
    final Facet face = FaceLatticeTest.getFaceBySaturatedInequalities(lattice, ((int[])Conversions.unwrapArray(saturatedInequalities, int.class)));
    Assert.assertNotNull(face);
    final Iterable<Facet> children = lattice.getChildren(face);
    Assert.assertEquals(addedInequalities.length, IterableExtensions.size(children));
    for (final int addedInequality : addedInequalities) {
      {
        final ArrayList<Integer> childInequalities = new ArrayList<Integer>(saturatedInequalities);
        childInequalities.add(Integer.valueOf(addedInequality));
        final Function1<Facet, Boolean> _function = (Facet child) -> {
          return Boolean.valueOf(FaceLatticeTest.faceSaturatesInequalities(child, ((int[])Conversions.unwrapArray(childInequalities, int.class))));
        };
        Assert.assertTrue(IterableExtensions.<Facet>exists(children, _function));
      }
    }
  }

  /**
   * Asserts that the root of the lattice is present and has the correct children.
   * 
   * @param lattice  The lattice to check.
   * @param children The inequalities that the children must saturate (one per child).
   */
  private static void assertRootHasChildren(final FaceLattice lattice, final int... children) {
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList()), children);
  }

  /**
   * Asserts that the lattice contains the indicated vertices.
   * 
   * @param lattice  The lattice to check.
   * @param vertices A list of lists, where each sub-list indicates the saturated inequalities that make up a vertex.
   */
  private static void assertVerticesExist(final FaceLattice lattice, final List<Integer>... vertices) {
    final Consumer<List<Integer>> _function = (List<Integer> vertex) -> {
      FaceLatticeTest.assertFaceHasChildren(lattice, vertex, new int[] {});
    };
    ((List<List<Integer>>)Conversions.doWrapArray(vertices)).forEach(_function);
  }

  /**
   * Performs the assertions needed to check that the vertices computed by the face lattice
   * exactly match the vertices that ISL calculates for the same set.
   */
  private static void assertVerticesMatchISL(final FaceLattice lattice) {
    boolean _isEmpty = lattice.getRootInfo().isEmpty();
    if (_isEmpty) {
      ArrayList<ArrayList<Facet>> _elvis = null;
      ArrayList<ArrayList<Facet>> _lattice = lattice.getLattice();
      if (_lattice != null) {
        _elvis = _lattice;
      } else {
        ArrayList<ArrayList<Facet>> _arrayList = new ArrayList<ArrayList<Facet>>();
        _elvis = _arrayList;
      }
      final ArrayList<ArrayList<Facet>> nonNullLattice = _elvis;
      final Function1<ArrayList<Facet>, Boolean> _function = (ArrayList<Facet> layer) -> {
        return Boolean.valueOf(layer.isEmpty());
      };
      final boolean isEmpty = IterableExtensions.<ArrayList<Facet>>forall(nonNullLattice, _function);
      Assert.assertTrue(isEmpty);
      return;
    }
    final Function1<Facet, ISLVertices> _function_1 = (Facet facet) -> {
      return facet.toBasicSet().computeVertices();
    };
    final List<ISLVertices> latticeVertexSets = IterableExtensions.<ISLVertices>toList(ListExtensions.<Facet, ISLVertices>map(ListExtensions.<Facet>reverseView(lattice.getLattice().get(0)), _function_1));
    final Consumer<ISLVertices> _function_2 = (ISLVertices vertexSet) -> {
      Assert.assertEquals(1, vertexSet.getNbVertices());
    };
    latticeVertexSets.forEach(_function_2);
    final Function1<ISLVertices, ISLVertex> _function_3 = (ISLVertices vertexSet) -> {
      return vertexSet.getVertexAt(0);
    };
    List<ISLVertex> _map = ListExtensions.<ISLVertices, ISLVertex>map(latticeVertexSets, _function_3);
    final ArrayList<ISLVertex> latticeVertices = new ArrayList<ISLVertex>(_map);
    final ISLVertices islVerticesList = lattice.getRootInfo().toBasicSet().computeVertices();
    int _nbVertices = islVerticesList.getNbVertices();
    final Function1<Integer, ISLVertex> _function_4 = (Integer idx) -> {
      return islVerticesList.getVertexAt((idx).intValue());
    };
    final List<ISLVertex> islVertices = IterableExtensions.<ISLVertex>toList(IterableExtensions.<Integer, ISLVertex>map(new ExclusiveRange(0, _nbVertices, true), _function_4));
    Assert.assertEquals(islVertices.size(), latticeVertices.size());
    while (((!latticeVertices.isEmpty()) && (!islVertices.isEmpty()))) {
      {
        final ISLVertex latticeVertex = latticeVertices.remove(0);
        final Function1<ISLVertex, Boolean> _function_5 = (ISLVertex islVertex) -> {
          return Boolean.valueOf(FaceLatticeTest.areVerticesEqual(latticeVertex, islVertex));
        };
        final ISLVertex islVertexMatch = IterableExtensions.<ISLVertex>findFirst(islVertices, _function_5);
        Assert.assertNotNull(islVertexMatch);
        islVertices.remove(islVertexMatch);
      }
    }
    Assert.assertTrue(latticeVertices.isEmpty());
    Assert.assertTrue(islVertices.isEmpty());
  }

  /**
   * Determines if two vertices are equal.
   */
  private static boolean areVerticesEqual(final ISLVertex v1, final ISLVertex v2) {
    final ISLBasicSet d1NoDivParam = v1.getDomain().removeDivsInvolvingDims(ISLDimType.isl_dim_param, 0, v1.getDomain().getSpace().getNbParams());
    final ISLBasicSet d2NoDivParam = v2.getDomain().removeDivsInvolvingDims(ISLDimType.isl_dim_param, 0, v2.getDomain().getSpace().getNbParams());
    final boolean domainsEqual = d1NoDivParam.isEqual(d2NoDivParam);
    final boolean exprsEqual = v1.getExpr().isPlainEqual(v2.getExpr());
    return (domainsEqual && exprsEqual);
  }

  /**
   * Performs all assertions needed to fully test any set which is just a single vertex, or 0D simplex.
   */
  private static void assertVertexSet(final String setDescriptor) {
    final FaceLattice lattice = FaceLatticeTest.makeLattice(setDescriptor);
    Assert.assertTrue(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 1);
    FaceLatticeTest.assertVerticesExist(lattice, new List[] {});
  }

  /**
   * Performs all assertions needed to fully test any line segment, which is a 1D simplex.
   */
  private static void assertLineSegment(final String setDescriptor) {
    final FaceLattice lattice = FaceLatticeTest.makeLattice(setDescriptor);
    Assert.assertTrue(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 2, 1);
    IntegerRange _upTo = new IntegerRange(0, 1);
    FaceLatticeTest.assertRootHasChildren(lattice, ((int[])Conversions.unwrapArray(_upTo, int.class)));
    FaceLatticeTest.assertVerticesExist(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))));
  }

  /**
   * Performs all assertions needed to fully test any 2D simplex.
   */
  private static void assertSimplex2d(final String setDescriptor) {
    final FaceLattice lattice = FaceLatticeTest.makeLattice(setDescriptor);
    Assert.assertTrue(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 3, 3, 1);
    IntegerRange _upTo = new IntegerRange(0, 2);
    FaceLatticeTest.assertRootHasChildren(lattice, ((int[])Conversions.unwrapArray(_upTo, int.class)));
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), 1, 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), 0, 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2))), 0, 1);
    FaceLatticeTest.assertVerticesExist(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2))));
  }

  /**
   * Performs all assertions needed to fully test any 3D simplex.
   */
  private static void assertSimplex3d(final String setDescriptor) {
    final FaceLattice lattice = FaceLatticeTest.makeLattice(setDescriptor);
    Assert.assertTrue(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 4, 6, 4, 1);
    IntegerRange _upTo = new IntegerRange(0, 3);
    FaceLatticeTest.assertRootHasChildren(lattice, ((int[])Conversions.unwrapArray(_upTo, int.class)));
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), 1, 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), 0, 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2))), 0, 1, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(3))), 0, 1, 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1))), 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2))), 1, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(3))), 1, 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2))), 0, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(3))), 0, 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2), Integer.valueOf(3))), 0, 1);
    FaceLatticeTest.assertVerticesExist(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(3))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(3))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3))));
  }

  /**
   * Performs all assertions needed to fully test any 4D simplex.
   */
  private static void assertSimplex4d(final String setDescriptor) {
    final FaceLattice lattice = FaceLatticeTest.makeLattice(setDescriptor);
    Assert.assertTrue(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 5, 10, 10, 5, 1);
    IntegerRange _upTo = new IntegerRange(0, 4);
    FaceLatticeTest.assertRootHasChildren(lattice, ((int[])Conversions.unwrapArray(_upTo, int.class)));
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), 1, 2, 3, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), 0, 2, 3, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2))), 0, 1, 3, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(3))), 0, 1, 2, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(4))), 0, 1, 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1))), 2, 3, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2))), 1, 3, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(3))), 1, 2, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(4))), 1, 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2))), 0, 3, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(3))), 0, 2, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(4))), 0, 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2), Integer.valueOf(3))), 0, 1, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2), Integer.valueOf(4))), 0, 1, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(3), Integer.valueOf(4))), 0, 1, 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2))), 3, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(3))), 2, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(4))), 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(3))), 1, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(4))), 1, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(3), Integer.valueOf(4))), 1, 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3))), 0, 4);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(4))), 0, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(4))), 0, 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4))), 0, 1);
    FaceLatticeTest.assertVerticesExist(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(4))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(4))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4))));
  }

  /**
   * Indicates whether a face saturates the indicated inequalities (no more, no fewer).
   */
  private static boolean faceSaturatesInequalities(final Facet face, final int... inequalities) {
    return (((face.getSaturatedInequalityIndices().size() == inequalities.length) && face.getSaturatedInequalityIndices().containsAll(((Collection<?>)Conversions.doWrapArray(inequalities)))) && ((List<Integer>)Conversions.doWrapArray(inequalities)).containsAll(face.getSaturatedInequalityIndices()));
  }

  /**
   * Gets the face from the lattice which saturates the indicated inequalities, or <code>null</code> if no such face exists.
   */
  private static Facet getFaceBySaturatedInequalities(final FaceLattice lattice, final int... saturatedInequalities) {
    final Function1<Facet, Boolean> _function = (Facet face) -> {
      return Boolean.valueOf(FaceLatticeTest.faceSaturatesInequalities(face, saturatedInequalities));
    };
    return IterableExtensions.<Facet>findFirst(lattice.getAllFaces(), _function);
  }

  /**
   * Creates a face lattice from a desired set.
   */
  private static FaceLattice makeLattice(final String setDescriptor) {
    final ISLBasicSet root = ISLUtil.toISLBasicSet(setDescriptor).removeRedundancies();
    final FaceLattice lattice = FaceLattice.create(root);
    FaceLatticeTest.assertVerticesMatchISL(lattice);
    return lattice;
  }

  /**
   * Creates a list of FaceLattice.Label enum values from an integer list
   */
  private static FaceLattice.Label[] toLabels(final int[] labels) {
    final Function1<Integer, FaceLattice.Label> _function = (Integer l) -> {
      FaceLattice.Label _switchResult = null;
      if (l != null) {
        switch (l) {
          case 1:
            _switchResult = FaceLattice.Label.POS;
            break;
          case (-1):
            _switchResult = FaceLattice.Label.NEG;
            break;
          case 0:
            _switchResult = FaceLattice.Label.ZERO;
            break;
        }
      }
      return _switchResult;
    };
    return ((FaceLattice.Label[])Conversions.unwrapArray(ListExtensions.<Integer, FaceLattice.Label>map(((List<Integer>)Conversions.doWrapArray(labels)), _function), FaceLattice.Label.class));
  }

  @Test
  public void testConstruction() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j,k]: 0<=i,j,k and N<2i+j+3k and i+j+k<2N+3}");
    Assert.assertTrue((lattice != null));
  }

  @Test
  public void testEmptySet() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("{[i,j]: 0<i<j and j<0}");
    Assert.assertFalse(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice);
  }

  @Test
  public void testLineSegment_1() {
    FaceLatticeTest.assertLineSegment("[N]->{[i]: 0<=i<=N}");
  }

  @Test
  public void testLineSegment_2() {
    FaceLatticeTest.assertLineSegment("[N]->{[i,j]: 0<=i<=N and j=i}");
  }

  @Test
  public void testLineSegment_3() {
    FaceLatticeTest.assertLineSegment("{[i,j,k]: 0<=i,j,k and i=j and j=k and k<=50}");
  }

  @Test
  public void testVertexSet_1() {
    FaceLatticeTest.assertVertexSet("{[i,j]: 0=i and 0<=j<=i}");
  }

  @Test
  public void testVertexSet_2() {
    FaceLatticeTest.assertVertexSet("[N]->{[i,j]: 0<=i<=j<=N<=0}");
  }

  @Test
  public void testVertexSet_3() {
    FaceLatticeTest.assertVertexSet("{[i]: i=0}");
  }

  @Test
  public void testSimplex2d_1() {
    FaceLatticeTest.assertSimplex2d("[N]->{[i,j]: N>=5 and 0<=i,j and i+j<=N}");
  }

  @Test
  public void testSimplex2d_2() {
    FaceLatticeTest.assertSimplex2d("[N]->{[i,j,k]: 0<=i,j and k=0 and i+j<N}");
  }

  @Test
  public void testSimplex2d_3() {
    FaceLatticeTest.assertSimplex2d("[N]->{[i,j,k]: 0<=i,j and k=i-15 and i+j<N}");
  }

  @Test
  public void testSimplex3d_1() {
    FaceLatticeTest.assertSimplex3d("[N]->{[i,j,k]: 0<=i,j,k and i+j+k<=N}");
  }

  @Test
  public void testSimplex4d_1() {
    FaceLatticeTest.assertSimplex4d("[N]->{[i,j,k,l]: 0<i<j<k<l<N}");
  }

  @Test
  public void testNonSimplex_1() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N,M]->{[i,j]: N>=5 and 0<=i,j and i+j<=N and j<=M}");
    Assert.assertFalse(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 5, 4, 1);
    IntegerRange _upTo = new IntegerRange(0, 3);
    FaceLatticeTest.assertRootHasChildren(lattice, ((int[])Conversions.unwrapArray(_upTo, int.class)));
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), 1, 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), 0, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2))), 0, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(3))), 0, 1, 2);
    FaceLatticeTest.assertVerticesExist(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(3))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(3))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2), Integer.valueOf(3))));
  }

  @Test
  public void testNonSimplex_2() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j]: 0<i<20 and i<j<N}");
    Assert.assertFalse(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 5, 4, 1);
    IntegerRange _upTo = new IntegerRange(0, 3);
    FaceLatticeTest.assertRootHasChildren(lattice, ((int[])Conversions.unwrapArray(_upTo, int.class)));
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), 2, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2))), 0, 1, 3);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(3))), 0, 1, 2);
    FaceLatticeTest.assertVerticesExist(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(3))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(3))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2), Integer.valueOf(3))));
  }

  @Test
  public void testUnbounded_1() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("{[i,j]}");
    Assert.assertFalse(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 0, 0, 1);
    FaceLatticeTest.assertRootHasChildren(lattice);
  }

  @Test
  public void testUnbounded_2() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j]: 0<=i<=N}");
    Assert.assertFalse(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 0, 2, 1);
    FaceLatticeTest.assertRootHasChildren(lattice, 0, 1);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))));
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))));
  }

  @Test
  public void testUnbounded_3() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("{[i,j,k]: 0<=i<=j and k=i+j}");
    Assert.assertFalse(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 1, 2, 1);
    FaceLatticeTest.assertRootHasChildren(lattice, 0, 1);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), 1);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), 0);
    FaceLatticeTest.assertVerticesExist(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1))));
  }

  @Test
  public void testUnbounded_4() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j]: 0<=i<=N and i<=j}");
    Assert.assertFalse(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 2, 3, 1);
    IntegerRange _upTo = new IntegerRange(0, 2);
    FaceLatticeTest.assertRootHasChildren(lattice, ((int[])Conversions.unwrapArray(_upTo, int.class)));
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), 2);
    FaceLatticeTest.assertFaceHasChildren(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2))), 0, 1);
    FaceLatticeTest.assertVerticesExist(lattice, Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(2))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2))));
  }

  @Test
  public void testUnbounded_5() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("{[i,j]: i=4}");
    Assert.assertFalse(lattice.isSimplicial());
    FaceLatticeTest.assertFaceCounts(lattice, 0, 1);
    FaceLatticeTest.assertRootHasChildren(lattice);
  }

  @Test
  public void testNormalVector_1() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("{[i,j]: 0<=i,j and i+j<100}");
    final Iterable<Facet> facets = lattice.getChildren(lattice.getRootInfo());
    final Function1<Facet, ISLAff> _function = (Facet f) -> {
      return f.getNormalVector();
    };
    final Iterable<ISLAff> norms = IterableExtensions.<Facet, ISLAff>map(facets, _function);
    final ISLAff v1 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[0];
    final ISLAff v2 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[1];
    final ISLAff v3 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[2];
    int _nbParams = v1.getSpace().getNbParams();
    boolean _equals = (_nbParams == 0);
    Assert.assertTrue(_equals);
    Assert.assertEquals(v1.toString(), "{ [i, j] -> [(i)] }");
    Assert.assertEquals(v2.toString(), "{ [i, j] -> [(j)] }");
    Assert.assertEquals(v3.toString(), "{ [i, j] -> [(-i - j)] }");
  }

  @Test
  public void testNormalVector_2() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j]: 0<=i,j and i+j<N+17}");
    final Iterable<Facet> facets = lattice.getChildren(lattice.getRootInfo());
    final Function1<Facet, ISLAff> _function = (Facet f) -> {
      return f.getNormalVector();
    };
    final Iterable<ISLAff> norms = IterableExtensions.<Facet, ISLAff>map(facets, _function);
    final ISLAff v1 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[0];
    final ISLAff v2 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[1];
    final ISLAff v3 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[2];
    int _nbParams = v1.getSpace().getNbParams();
    boolean _equals = (_nbParams == 0);
    Assert.assertTrue(_equals);
    Assert.assertEquals(v1.toString(), "{ [i, j] -> [(i)] }");
    Assert.assertEquals(v2.toString(), "{ [i, j] -> [(j)] }");
    Assert.assertEquals(v3.toString(), "{ [i, j] -> [(-i - j)] }");
  }

  @Test
  public void testNormalVector_3() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j,k]: 0<=i,j,k and N<2i+j+3k and i+j+k<2N+3}");
    final Iterable<Facet> facets = lattice.getChildren(lattice.getRootInfo());
    final Function1<Facet, ISLAff> _function = (Facet f) -> {
      return f.getNormalVector();
    };
    final Iterable<ISLAff> norms = IterableExtensions.<Facet, ISLAff>map(facets, _function);
    final ISLAff v1 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[0];
    final ISLAff v2 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[1];
    final ISLAff v3 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[2];
    final ISLAff v4 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[3];
    final ISLAff v5 = ((ISLAff[])Conversions.unwrapArray(norms, ISLAff.class))[4];
    int _nbParams = v1.getSpace().getNbParams();
    boolean _equals = (_nbParams == 0);
    Assert.assertTrue(_equals);
    Assert.assertEquals(v1.toString(), "{ [i, j, k] -> [(i)] }");
    Assert.assertEquals(v2.toString(), "{ [i, j, k] -> [(j)] }");
    Assert.assertEquals(v3.toString(), "{ [i, j, k] -> [(k)] }");
    Assert.assertEquals(v4.toString(), "{ [i, j, k] -> [(-i - j - k)] }");
    Assert.assertEquals(v5.toString(), "{ [i, j, k] -> [(2i + j + 3k)] }");
  }

  @Test
  public void testLabelInducingConstraint_1() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j] : 0<=i,j and i+j<N }");
    final ISLSpace space = lattice.getRootInfo().getSpace();
    final ISLAff aff = ISLUtil.toISLAff("{[i,j]->[2i+7j]}");
    final ISLConstraint opConstraint = lattice.toLabelInducingConstraint(aff, space, FaceLattice.Label.POS);
    final ISLConstraint iopConstraint = lattice.toLabelInducingConstraint(aff, space, FaceLattice.Label.NEG);
    final ISLConstraint invConstraint = lattice.toLabelInducingConstraint(aff, space, FaceLattice.Label.ZERO);
    String _string = opConstraint.toString();
    boolean _equals = Objects.equal(_string, "[N] -> { [i, j] : -1 + 2i + 7j >= 0 }");
    Assert.assertTrue(_equals);
    String _string_1 = iopConstraint.toString();
    boolean _equals_1 = Objects.equal(_string_1, "[N] -> { [i, j] : -1 - 2i - 7j >= 0 }");
    Assert.assertTrue(_equals_1);
    String _string_2 = invConstraint.toString();
    boolean _equals_2 = Objects.equal(_string_2, "[N] -> { [i, j] : 2i + 7j = 0 }");
    Assert.assertTrue(_equals_2);
  }

  @Test
  public void testLabelingDomain_1() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j]: 0<=i,j and i+j<N}");
    final Facet face = lattice.getRootInfo();
    final int nbParams = face.getSpace().getNbParams();
    final Pair<FaceLattice.Label[], ISLBasicSet> ld1 = lattice.getLabelingDomain(face, FaceLatticeTest.toLabels(((int[])Conversions.unwrapArray(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1))), int.class))));
    final Pair<FaceLattice.Label[], ISLBasicSet> ld2 = lattice.getLabelingDomain(face, FaceLatticeTest.toLabels(((int[])Conversions.unwrapArray(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf((-1)), Integer.valueOf((-1)), Integer.valueOf((-1)))), int.class))));
    final Pair<FaceLattice.Label[], ISLBasicSet> ld3 = lattice.getLabelingDomain(face, FaceLatticeTest.toLabels(((int[])Conversions.unwrapArray(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0))), int.class))));
    final Pair<FaceLattice.Label[], ISLBasicSet> ld4 = lattice.getLabelingDomain(face, FaceLatticeTest.toLabels(((int[])Conversions.unwrapArray(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf((-1)))), int.class))));
    Assert.assertTrue(AlphaUtil.isTrivial(ld1.getValue()));
    Assert.assertTrue(AlphaUtil.isTrivial(ld2.getValue()));
    Assert.assertTrue(AlphaUtil.isTrivial(ld3.getValue()));
    Assert.assertFalse(AlphaUtil.isTrivial(ld4.getValue()));
    Assert.assertEquals(ld4.getValue().toString(), "[N] -> { [i, j] : j = 0 and i > 0 }");
  }

  @Test
  public void testEnumerateLabels_1() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j,k]: 0<=i,j,k and i+j+k<N}");
    final List<FaceLattice.Label> validLabels1 = Collections.<FaceLattice.Label>unmodifiableList(CollectionLiterals.<FaceLattice.Label>newArrayList(FaceLattice.Label.POS, FaceLattice.Label.NEG, FaceLattice.Label.ZERO));
    final List<FaceLattice.Label> validLabels2 = Collections.<FaceLattice.Label>unmodifiableList(CollectionLiterals.<FaceLattice.Label>newArrayList(FaceLattice.Label.POS, FaceLattice.Label.ZERO));
    final List<List<FaceLattice.Label>> l1 = lattice.enumerateAllPossibleLabelings(((FaceLattice.Label[])Conversions.unwrapArray(validLabels1, FaceLattice.Label.class)), 2);
    final List<List<FaceLattice.Label>> l2 = lattice.enumerateAllPossibleLabelings(((FaceLattice.Label[])Conversions.unwrapArray(validLabels2, FaceLattice.Label.class)), 2);
    Assert.assertEquals(l1.size(), (3 * 3));
    Assert.assertEquals(l2.size(), (2 * 2));
    final List<List<FaceLattice.Label>> l3 = lattice.enumerateAllPossibleLabelings(((FaceLattice.Label[])Conversions.unwrapArray(validLabels1, FaceLattice.Label.class)), 5);
    final List<List<FaceLattice.Label>> l4 = lattice.enumerateAllPossibleLabelings(((FaceLattice.Label[])Conversions.unwrapArray(validLabels2, FaceLattice.Label.class)), 5);
    Assert.assertEquals(l3.size(), ((((3 * 3) * 3) * 3) * 3));
    Assert.assertEquals(l4.size(), ((((2 * 2) * 2) * 2) * 2));
  }

  @Test
  public void testEnumerateLabels_2() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j,k]: 0<=i,j,k and i+j+k<N}");
    final List<FaceLattice.Label> validLabels = Collections.<FaceLattice.Label>unmodifiableList(CollectionLiterals.<FaceLattice.Label>newArrayList(FaceLattice.Label.POS, FaceLattice.Label.ZERO));
    final List<List<FaceLattice.Label>> l2Facets = lattice.enumerateAllPossibleLabelings(((FaceLattice.Label[])Conversions.unwrapArray(validLabels, FaceLattice.Label.class)), 2);
    final List<List<FaceLattice.Label>> l3Facets = lattice.enumerateAllPossibleLabelings(((FaceLattice.Label[])Conversions.unwrapArray(validLabels, FaceLattice.Label.class)), 3);
    Assert.assertEquals(l2Facets.size(), 4);
    Assert.assertEquals(l3Facets.size(), 8);
  }

  @Test
  public void testToDigitArray_1() {
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(13, 10, 4))).toString(), "[0, 0, 1, 3]");
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(1345, 10, 4))).toString(), "[1, 3, 4, 5]");
  }

  @Test
  public void testToDigitArray_2() {
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(13, 16, 2))).toString(), "[0, 13]");
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(17, 16, 2))).toString(), "[1, 1]");
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(0, 16, 2))).toString(), "[0, 0]");
    try {
      Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(256, 16, 2))).toString(), "[1, 0, 0]");
      Assert.fail("It is not be possible to represent 256 in base 16 with 2 digits");
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        Assert.assertTrue(true);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  @Test
  public void testToDigitArray_3() {
    try {
      FaceLattice.toPaddedDigitArray(256, 16, 2);
      Assert.fail("It is not be possible to represent 256 in base 16 with 2 digits");
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        Assert.assertTrue(true);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  @Test
  public void testToDigitArray_4() {
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(12345, 16, 4))).toString(), "[3, 0, 3, 9]");
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(12345, 27, 3))).toString(), "[16, 25, 6]");
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(12346, 27, 5))).toString(), "[0, 0, 16, 25, 7]");
    try {
      FaceLattice.toPaddedDigitArray(12345, 10, 4);
      Assert.fail("It is not be possible to represent 12345 in base 10 with 4 digits");
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        Assert.assertTrue(true);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  @Test
  public void testToDigitArray_5() {
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(15, 3, 3))).toString(), "[1, 2, 0]");
    Assert.assertEquals(((List<Integer>)Conversions.doWrapArray(FaceLattice.toPaddedDigitArray(15, 3, 10))).toString(), "[0, 0, 0, 0, 0, 0, 0, 1, 2, 0]");
  }

  @Test
  public void testFeasibleReuse_1() {
    final FaceLattice lattice = FaceLatticeTest.makeLattice("[N]->{[i,j]: 0<=i<=N and i<=j<2i}");
    final Facet face = lattice.getRootInfo();
    final Iterable<Facet> facets = lattice.getChildren(face);
    final int nbParams = face.getSpace().getNbParams();
    final ISLBasicSet reuseSpace = ISLUtil.toISLBasicSet("[N]->{[i,j]: j=0}");
    final Function1<List<FaceLattice.Label>, Pair<FaceLattice.Label[], ISLBasicSet>> _function = (List<FaceLattice.Label> label) -> {
      return lattice.getLabelingDomain(face, ((FaceLattice.Label[])Conversions.unwrapArray(label, FaceLattice.Label.class)));
    };
    final Function1<Pair<FaceLattice.Label[], ISLBasicSet>, Pair<FaceLattice.Label[], ISLBasicSet>> _function_1 = (Pair<FaceLattice.Label[], ISLBasicSet> ld) -> {
      FaceLattice.Label[] _key = ld.getKey();
      ISLBasicSet _intersect = ld.getValue().intersect(reuseSpace.copy());
      return Pair.<FaceLattice.Label[], ISLBasicSet>of(_key, _intersect);
    };
    final Function1<Pair<FaceLattice.Label[], ISLBasicSet>, Boolean> _function_2 = (Pair<FaceLattice.Label[], ISLBasicSet> ld) -> {
      boolean _isTrivial = AlphaUtil.isTrivial(ld.getValue());
      return Boolean.valueOf((!_isTrivial));
    };
    final Iterable<Pair<FaceLattice.Label[], ISLBasicSet>> labelings = IterableExtensions.<Pair<FaceLattice.Label[], ISLBasicSet>>filter(ListExtensions.<Pair<FaceLattice.Label[], ISLBasicSet>, Pair<FaceLattice.Label[], ISLBasicSet>>map(ListExtensions.<List<FaceLattice.Label>, Pair<FaceLattice.Label[], ISLBasicSet>>map(lattice.enumerateAllPossibleLabelings(FaceLatticeTest.toLabels(((int[])Conversions.unwrapArray(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf((-1)), Integer.valueOf(0), Integer.valueOf(1))), int.class))), IterableExtensions.size(facets)), _function), _function_1), _function_2);
    final Function1<Pair<FaceLattice.Label[], ISLBasicSet>, String> _function_3 = (Pair<FaceLattice.Label[], ISLBasicSet> l) -> {
      return ((List<FaceLattice.Label>)Conversions.doWrapArray(l.getKey())).toString();
    };
    final String labelingKeys = IterableExtensions.<Pair<FaceLattice.Label[], ISLBasicSet>, String>map(labelings, _function_3).toString();
    Assert.assertTrue(labelingKeys.contains(((List<FaceLattice.Label>)Conversions.doWrapArray(FaceLatticeTest.toLabels(((int[])Conversions.unwrapArray(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf((-1)), Integer.valueOf(1), Integer.valueOf(1))), int.class))))).toString()));
    Assert.assertTrue(labelingKeys.contains(((List<FaceLattice.Label>)Conversions.doWrapArray(FaceLatticeTest.toLabels(((int[])Conversions.unwrapArray(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf((-1)), Integer.valueOf((-1)))), int.class))))).toString()));
  }
}
