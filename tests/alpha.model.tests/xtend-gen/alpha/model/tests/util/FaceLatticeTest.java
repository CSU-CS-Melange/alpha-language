package alpha.model.tests.util;

import alpha.model.util.FaceLattice;
import alpha.model.util.SetInfo;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLVertex;
import fr.irisa.cairn.jnimap.isl.ISLVertices;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
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
    final ArrayList<ArrayList<SetInfo>> latticeStorage = lattice.getLattice();
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
    final SetInfo face = FaceLatticeTest.getFaceBySaturatedInequalities(lattice, ((int[])Conversions.unwrapArray(saturatedInequalities, int.class)));
    Assert.assertNotNull(face);
    final Iterable<SetInfo> children = lattice.getChildren(face);
    Assert.assertEquals(addedInequalities.length, IterableExtensions.size(children));
    for (final int addedInequality : addedInequalities) {
      {
        final ArrayList<Integer> childInequalities = new ArrayList<Integer>(saturatedInequalities);
        childInequalities.add(Integer.valueOf(addedInequality));
        final Function1<SetInfo, Boolean> _function = (SetInfo child) -> {
          return Boolean.valueOf(FaceLatticeTest.faceSaturatesInequalities(child, ((int[])Conversions.unwrapArray(childInequalities, int.class))));
        };
        Assert.assertTrue(IterableExtensions.<SetInfo>exists(children, _function));
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
      ArrayList<ArrayList<SetInfo>> _elvis = null;
      ArrayList<ArrayList<SetInfo>> _lattice = lattice.getLattice();
      if (_lattice != null) {
        _elvis = _lattice;
      } else {
        ArrayList<ArrayList<SetInfo>> _arrayList = new ArrayList<ArrayList<SetInfo>>();
        _elvis = _arrayList;
      }
      final ArrayList<ArrayList<SetInfo>> nonNullLattice = _elvis;
      final Function1<ArrayList<SetInfo>, Boolean> _function = (ArrayList<SetInfo> layer) -> {
        return Boolean.valueOf(layer.isEmpty());
      };
      final boolean isEmpty = IterableExtensions.<ArrayList<SetInfo>>forall(nonNullLattice, _function);
      Assert.assertTrue(isEmpty);
      return;
    }
    final Function1<SetInfo, ISLVertices> _function_1 = (SetInfo setInfo) -> {
      return setInfo.toBasicSet().computeVertices();
    };
    final List<ISLVertices> latticeVertexSets = IterableExtensions.<ISLVertices>toList(ListExtensions.<SetInfo, ISLVertices>map(ListExtensions.<SetInfo>reverseView(lattice.getLattice().get(0)), _function_1));
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
    final boolean domainsEqual = v1.getDomain().isEqual(v2.getDomain());
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
  private static boolean faceSaturatesInequalities(final SetInfo face, final int... inequalities) {
    return (((face.getSaturatedInequalityIndices().size() == inequalities.length) && face.getSaturatedInequalityIndices().containsAll(((Collection<?>)Conversions.doWrapArray(inequalities)))) && ((List<Integer>)Conversions.doWrapArray(inequalities)).containsAll(face.getSaturatedInequalityIndices()));
  }

  /**
   * Gets the face from the lattice which saturates the indicated inequalities, or <code>null</code> if no such face exists.
   */
  private static SetInfo getFaceBySaturatedInequalities(final FaceLattice lattice, final int... saturatedInequalities) {
    final Function1<SetInfo, Boolean> _function = (SetInfo face) -> {
      return Boolean.valueOf(FaceLatticeTest.faceSaturatesInequalities(face, saturatedInequalities));
    };
    return IterableExtensions.<SetInfo>findFirst(lattice.getAllFaces(), _function);
  }

  /**
   * Creates a face lattice from a desired set.
   */
  private static FaceLattice makeLattice(final String setDescriptor) {
    final ISLBasicSet root = ISLBasicSet.buildFromString(ISLContext.getInstance(), setDescriptor).removeRedundancies();
    final FaceLattice lattice = FaceLattice.create(root);
    FaceLatticeTest.assertVerticesMatchISL(lattice);
    return lattice;
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
}
