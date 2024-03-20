package alpha.model.tests.util

import alpha.model.util.FaceLattice
import alpha.model.util.Facet
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLVertex
import java.util.ArrayList
import java.util.List
import org.junit.Test

import static org.junit.Assert.*

import static extension alpha.model.util.ISLUtil.*

class FaceLatticeTest {
	////////////////////////////////////////////////////////////
	// Assertions for Specific Aspects of the Face Lattice
	////////////////////////////////////////////////////////////
	
	/**
	 * Asserts the correct number of layers in the lattice,
	 * and that each layer has the correct number of nodes.
	 * 
	 * @param lattice    The lattice to check.
	 * @param faceCounts The number of nodes to expect in each layer, sorted from 0-face to N-faces.
	 */
	def private static assertFaceCounts(FaceLattice lattice, int... faceCounts) {
		val latticeStorage = lattice.lattice
		assertEquals(faceCounts.length, latticeStorage.size)
		(0 ..< faceCounts.length).forEach[dim | assertEquals(latticeStorage.get(dim).size, faceCounts.get(dim))]
	}
	
	def private static assertFaceHasNoChildren(FaceLattice lattice, List<Integer> saturatedInequalities) {
		val face = getFaceBySaturatedInequalities(lattice, saturatedInequalities)
		assertNotNull(face)

		val children = lattice.getChildren(face)
		assertEquals(0, children.size)
	}
	
	/**
	 * Asserts that a face exists in the lattice, and that it has the correct child faces.
	 * In this case, a child node saturates all the inequalities of its parents, plus exactly one more.
	 * 
	 * @param lattice               The lattice to check.
	 * @param saturatedInequalities The inequalities that the desired face saturates.
	 * @param addedInequalities     The additional inequalities that the children can saturate (one per child).
	 */
	def private static assertFaceHasChildren(FaceLattice lattice, List<Integer> saturatedInequalities, int... addedInequalities) {
		val wrappedAddedInequalities = addedInequalities.map[index | #[index]]
		assertFaceHasChildren(lattice, saturatedInequalities, wrappedAddedInequalities)
	}
	
	/**
	 * Asserts that a face exists in the lattice, and that it has the correct child faces.
	 * Reminder: a child node saturates all the inequalities of its parents, plus one more.
	 * 
	 * @param lattice               The lattice to check.
	 * @param saturatedInequalities The inequalities that the desired face saturates.
	 * @param addedInequalities     The list additional inequalities that the children can saturate (one list per child).
	 */
	def private static assertFaceHasChildren(FaceLattice lattice, List<Integer> saturatedInequalities, List<Integer>... addedInequalities) {
		val face = getFaceBySaturatedInequalities(lattice, saturatedInequalities)
		assertNotNull(face)

		val children = lattice.getChildren(face)
		assertEquals(addedInequalities.length, children.size)
		
		for (addedInequality: addedInequalities) {
			val childInequalities = new ArrayList<Integer>(saturatedInequalities)
			childInequalities.addAll(addedInequality)
			
			assertTrue(children.exists[child | faceSaturatesInequalities(child, childInequalities)])
		}
	}
	
	/**
	 * Asserts that the root of the lattice is present and has the correct children.
	 * 
	 * @param lattice  The lattice to check.
	 * @param children The inequalities that the children must saturate (one per child).
	 */
	def private static assertRootHasChildren(FaceLattice lattice, int... children) {
		assertFaceHasChildren(lattice, #[], children)
	}
	
	/**
	 * Asserts that the lattice contains the indicated vertices.
	 * 
	 * @param lattice  The lattice to check.
	 * @param vertices A list of lists, where each sub-list indicates the saturated inequalities that make up a vertex.
	 */
	def private static assertVerticesExist(FaceLattice lattice, List<Integer>... vertices) {
		vertices.forEach[vertex | assertFaceHasNoChildren(lattice, vertex)]
	}
	
	/**
	 * Performs the assertions needed to check that the vertices computed by the face lattice
	 * exactly match the vertices that ISL calculates for the same set.
	 */
	def private static assertVerticesMatchISL(FaceLattice lattice) {
		// This assertion only works for input sets without thick faces
		if (lattice.hasThickFaces) {
			return
		}
		
		// If the root set is empty, just check that the lattice is empty.
		if (lattice.rootInfo.empty) {
			val nonNullLattice = lattice.lattice ?: new ArrayList
			val isEmpty = nonNullLattice.forall(layer | layer.empty)
			assertTrue(isEmpty)
			return
		}   
		
		// The "vertices" in the lattice are actually stored as ISL sets.
		// Have ISL compute the list of vertices for each of these sets
		// and check that there is only one vertex.
		val latticeVertexSets =
			lattice.lattice.get(0).reverseView
			.map[facet | facet.toBasicSet.computeVertices]
			.toList
		latticeVertexSets.forEach[vertexSet | assertEquals(1, vertexSet.nbVertices)]

		// Get a list containing only those vertices.
		// Note: to avoid them being removed from the actual lattice,
		// a copy of the list must be made.
		val latticeVertices = new ArrayList(
			latticeVertexSets
			.map[vertexSet | vertexSet.getVertexAt(0)])
			
		// Have ISL compute the expressions for the vertices of the original set.
		val islVerticesList = lattice.rootInfo.toBasicSet.computeVertices
		val islVertices =
			(0 ..< islVerticesList.nbVertices)
			.map[idx | islVerticesList.getVertexAt(idx)]
			.toList
		
		// Make sure the number of vertices are equal.
		assertEquals(islVertices.size, latticeVertices.size)
		
		// To make sure there is a 1-to-1 mapping between the vertices produced
		// by the lattice and the vertices produced by ISL,
		// remove vertices from a list one at a time,
		// then remove a matching vertex from the other list,
		// reporting a failure if a match was not found.
		while(!latticeVertices.empty && !islVertices.empty) {
			val latticeVertex = latticeVertices.remove(0)
			val islVertexMatch = islVertices.findFirst[islVertex | areVerticesEqual(latticeVertex, islVertex)]
			assertNotNull(islVertexMatch)
			islVertices.remove(islVertexMatch)
		}
		assertTrue(latticeVertices.empty)
		assertTrue(islVertices.empty)
	}
	
	/** Determines if two vertices are equal. */
	def private static areVerticesEqual(ISLVertex v1, ISLVertex v2) {
		val domainsEqual = v1.domain.isEqual(v2.domain)
		val exprsEqual = v1.expr.isPlainEqual(v2.expr)
		return domainsEqual && exprsEqual
	}
	
	////////////////////////////////////////////////////////////
	// Assertions for Checking Entire Lattices
	////////////////////////////////////////////////////////////

	/** Performs all assertions needed to fully test any set which is just a single vertex, or 0D simplex. */
	def private static assertVertexSet(String setDescriptor) {
		val lattice = makeLattice(setDescriptor)
		assertTrue(lattice.isSimplicial)
		assertFaceCounts(lattice, 1)
		assertVerticesExist(lattice, #[])
	}
	
	/** Performs all assertions needed to fully test any line segment, which is a 1D simplex. */
	def private static assertLineSegment(String setDescriptor) {
		val lattice = makeLattice(setDescriptor)
		assertTrue(lattice.isSimplicial)
		assertFaceCounts(lattice, 2, 1)
		assertRootHasChildren(lattice, 0..1)
		assertVerticesExist(lattice, #[0], #[1])
	}
	
	/** Performs all assertions needed to fully test any 2D simplex. */
	def private static assertSimplex2d(String setDescriptor) {
		val lattice = makeLattice(setDescriptor)
		assertTrue(lattice.isSimplicial)
		assertFaceCounts(lattice, 3, 3, 1)
		assertRootHasChildren(lattice, 0..2)
		
		// Check all the 1-faces.
		assertFaceHasChildren(lattice, #[0], 1, 2)
		assertFaceHasChildren(lattice, #[1], 0, 2)
		assertFaceHasChildren(lattice, #[2], 0, 1)
		
		assertVerticesExist(lattice, #[0,1], #[0,2], #[1,2])
	}
	
	/** Performs all assertions needed to fully test any 3D simplex. */
	def private static assertSimplex3d(String setDescriptor) {
		val lattice = makeLattice(setDescriptor)
		assertTrue(lattice.isSimplicial)
		assertFaceCounts(lattice, 4, 6, 4, 1)
		assertRootHasChildren(lattice, 0..3)
		
		// Check all the 2-faces.
		assertFaceHasChildren(lattice, #[0], 1, 2, 3)
		assertFaceHasChildren(lattice, #[1], 0, 2, 3)
		assertFaceHasChildren(lattice, #[2], 0, 1, 3)
		assertFaceHasChildren(lattice, #[3], 0, 1, 2)
		
		// Check all the 1-faces.
		assertFaceHasChildren(lattice, #[0,1], 2, 3)
		assertFaceHasChildren(lattice, #[0,2], 1, 3)
		assertFaceHasChildren(lattice, #[0,3], 1, 2)
		assertFaceHasChildren(lattice, #[1,2], 0, 3)
		assertFaceHasChildren(lattice, #[1,3], 0, 2)
		assertFaceHasChildren(lattice, #[2,3], 0, 1)
		
		assertVerticesExist(lattice, #[0,1,2], #[0,1,3], #[0,2,3], #[1,2,3])
	}
	
	/** Performs all assertions needed to fully test any 4D simplex. */
	def private static assertSimplex4d(String setDescriptor) {
		val lattice = makeLattice(setDescriptor)
		assertTrue(lattice.isSimplicial)
		assertFaceCounts(lattice, 5, 10, 10, 5, 1)
		assertRootHasChildren(lattice, 0..4)
		
		// Check all the 3-faces.
		assertFaceHasChildren(lattice, #[0], 1, 2, 3, 4)
		assertFaceHasChildren(lattice, #[1], 0, 2, 3, 4)
		assertFaceHasChildren(lattice, #[2], 0, 1, 3, 4)
		assertFaceHasChildren(lattice, #[3], 0, 1, 2, 4)
		assertFaceHasChildren(lattice, #[4], 0, 1, 2, 3)
		
		// Check all the 2-faces.
		assertFaceHasChildren(lattice, #[0,1], 2, 3, 4)
		assertFaceHasChildren(lattice, #[0,2], 1, 3, 4)
		assertFaceHasChildren(lattice, #[0,3], 1, 2, 4)
		assertFaceHasChildren(lattice, #[0,4], 1, 2, 3)
		assertFaceHasChildren(lattice, #[1,2], 0, 3, 4)
		assertFaceHasChildren(lattice, #[1,3], 0, 2, 4)
		assertFaceHasChildren(lattice, #[1,4], 0, 2, 3)
		assertFaceHasChildren(lattice, #[2,3], 0, 1, 4)
		assertFaceHasChildren(lattice, #[2,4], 0, 1, 3)
		assertFaceHasChildren(lattice, #[3,4], 0, 1, 2)
		
		// Check all the 1-faces.
		assertFaceHasChildren(lattice, #[0,1,2], 3, 4)
		assertFaceHasChildren(lattice, #[0,1,3], 2, 4)
		assertFaceHasChildren(lattice, #[0,1,4], 2, 3)
		assertFaceHasChildren(lattice, #[0,2,3], 1, 4)
		assertFaceHasChildren(lattice, #[0,2,4], 1, 3)
		assertFaceHasChildren(lattice, #[0,3,4], 1, 2)
		assertFaceHasChildren(lattice, #[1,2,3], 0, 4)
		assertFaceHasChildren(lattice, #[1,2,4], 0, 3)
		assertFaceHasChildren(lattice, #[1,3,4], 0, 2)
		assertFaceHasChildren(lattice, #[2,3,4], 0, 1)
		
		assertVerticesExist(lattice, #[0,1,2,3], #[0,1,2,4], #[0,1,3,4], #[0,2,3,4], #[1,2,3,4])
	}
	
	////////////////////////////////////////////////////////////
	// Miscellaneous Helper Methods
	////////////////////////////////////////////////////////////
	
	/** Indicates whether a face saturates the indicated inequalities (no more, no fewer). */
	def private static faceSaturatesInequalities(Facet face, int... inequalities) {
		// The saturated inequalities in the face must be exactly the same as the given inequalities.
		return face.saturatedInequalityIndices.size == inequalities.length
			&& face.saturatedInequalityIndices.containsAll(inequalities)
			&& inequalities.containsAll(face.saturatedInequalityIndices)
	}
	
	/** Gets the face from the lattice which saturates the indicated inequalities, or <code>null</code> if no such face exists. */
	def private static getFaceBySaturatedInequalities(FaceLattice lattice, int... saturatedInequalities) {
		return lattice.allFaces.findFirst[face | faceSaturatesInequalities(face, saturatedInequalities)]
	}
	
	/** Creates a face lattice from a desired set. */
	def private static makeLattice(String setDescriptor) {
		val root = ISLBasicSet.buildFromString(ISLContext.instance, setDescriptor).removeRedundancies()
		val lattice = FaceLattice.create(root)
		assertVerticesMatchISL(lattice)
		return lattice
	}
	
	////////////////////////////////////////////////////////////
	// Unit Tests
	////////////////////////////////////////////////////////////

	@Test
	def testEmptySet() {
		val lattice = makeLattice("{[i,j]: 0<i<j and j<0}")
		assertFalse(lattice.isSimplicial)
		assertFaceCounts(lattice)  // Don't include any face counts, as there should also be 0 layers.
	}
	
	@Test
	def testLineSegment_1() {
		assertLineSegment("[N]->{[i]: 0<=i<=N}")
	}
	
	@Test
	def testLineSegment_2() {
		assertLineSegment("[N]->{[i,j]: 0<=i<=N and j=i}")
	}
	
	@Test
	def testLineSegment_3() {
		assertLineSegment("[N]->{[i,j,k]: 0<=i,j,k and i=j and j=k and k<=N}")
	}
	
	@Test
	def testVertexSet_1() {
		assertVertexSet("{[i,j]: 0=i and 0<=j<=i}")
	}
	
	@Test
	def testVertexSet_2() {
		assertVertexSet("[N]->{[i,j]: 0<=i<=j<=N<=0}")
	}
	
	@Test
	def testVertexSet_3() {
		assertVertexSet("{[i]: i=0}")
	}
	
	@Test
	def testSimplex2d_1() {
		assertSimplex2d("[N]->{[i,j]: N>=5 and 0<=i,j and i+j<=N}")
	}
	
	@Test
	def testSimplex2d_2() {
		assertSimplex2d("[N]->{[i,j,k]: 0<=i,j and k=0 and i+j<N}")
	}
	
	@Test
	def testSimplex2d_3() {
		assertSimplex2d("[N]->{[i,j,k]: 0<=i,j and k=i-15 and i+j<N}")
	}
	
	@Test
	def testSimplex3d_1() {
		assertSimplex3d("[N]->{[i,j,k]: 0<=i,j,k and i+j+k<=N}")
	}
	
	@Test
	def testSimplex4d_1() {
		assertSimplex4d("[N]->{[i,j,k,l]: 0<i<j<k<l<N}")
	}
	
	@Test
	def testNonSimplex_1() {
		val lattice = makeLattice("[N,M]->{[i,j]: N>=5 and 0<=i,j and i+j<=N and j<=M}")
		assertFalse(lattice.isSimplicial)
		assertFaceCounts(lattice, 5, 4, 1)
		assertRootHasChildren(lattice, 0..3)
		
		// Check that all the 1-faces exist.
		assertFaceHasChildren(lattice, #[0], 1, 2, 3)
		assertFaceHasChildren(lattice, #[1], 0, 3)
		assertFaceHasChildren(lattice, #[2], 0, 3)
		assertFaceHasChildren(lattice, #[3], 0, 1, 2)
		
		assertVerticesExist(lattice, #[0,1], #[0,2], #[0,3], #[1,3], #[2,3])
	}
	
	@Test
	def testNonSimplex_2() {
		val lattice = makeLattice("[N,M]->{[i,j]: 0<i<M and i<j<2N}")
		assertFalse(lattice.isSimplicial)
		assertFaceCounts(lattice, 5, 4, 1)
		assertRootHasChildren(lattice, 0..3)
		
		// Check all the 1-faces.
		assertFaceHasChildren(lattice, #[0], 2, 3)
		assertFaceHasChildren(lattice, #[1], 2, 3)
		assertFaceHasChildren(lattice, #[2], 0, 1, 3)
		assertFaceHasChildren(lattice, #[3], 0, 1, 2)
		
		assertVerticesExist(lattice, #[0,2], #[0,3], #[1,2], #[1,3], #[2,3])
	}

	@Test
	def testUnbounded_1() {
		val lattice = makeLattice("{[i,j]}")
		assertFalse(lattice.isSimplicial)
		assertFaceCounts(lattice, 0, 0, 1)
		assertRootHasChildren(lattice)  // Don't include any children, as the root has none.
	}
	
	@Test
	def testUnbounded_2() {
		val lattice = makeLattice("[N]->{[i,j]: 0<=i<=N}")
		assertFalse(lattice.isSimplicial)
		assertFaceCounts(lattice, 0, 2, 1)
		assertRootHasChildren(lattice, 0, 1)
		
		// Check that the 1-faces have no children.
		assertFaceHasNoChildren(lattice, #[0])
		assertFaceHasNoChildren(lattice, #[1])
	}
	
	@Test
	def testUnbounded_3() {
		val lattice = makeLattice("{[i,j,k]: 0<=i<=j and k=i+j}")
		assertFalse(lattice.isSimplicial)
		assertFaceCounts(lattice, 1, 2, 1)
		assertRootHasChildren(lattice, 0, 1)
		
		// Check the 1-faces.
		assertFaceHasChildren(lattice, #[0], 1)
		assertFaceHasChildren(lattice, #[1], 0)
		
		assertVerticesExist(lattice, #[0,1])
	}
	
	@Test
	def testUnbounded_4() {
		val lattice = makeLattice("[N]->{[i,j]: 0<=i<=N and i<=j}")
		assertFalse(lattice.isSimplicial)
		assertFaceCounts(lattice, 2, 3, 1)
		assertRootHasChildren(lattice, 0..2)
		
		// Check the 1-faces.
		assertFaceHasChildren(lattice, #[0], 2)
		assertFaceHasChildren(lattice, #[1], 2)
		assertFaceHasChildren(lattice, #[2], 0, 1)
		
		assertVerticesExist(lattice, #[0,2], #[1,2])
	}
	
	@Test
	def testUnbounded_5() {
		val lattice = makeLattice("{[i,j]: i=4}")
		assertFalse(lattice.isSimplicial)
		assertFaceCounts(lattice, 0, 1)
		assertRootHasChildren(lattice)  // Don't include any children, as the root has none.
	}
	
	@Test
	def squarePyramidTest() {
		val lattice = makeLattice("[N]->{[i,j,k]: 0<=i<=k and 0<=j<=k and 0<=k<=N}")
		assertFalse(lattice.isSimplicial)
		assertFaceCounts(lattice, 5, 8, 5, 1)
		assertRootHasChildren(lattice, 0..4)
		
		// Check the 2-faces.
		assertFaceHasChildren(lattice, #[0], 1, 3, 4)
		assertFaceHasChildren(lattice, #[1], 0, 2, 4)
		assertFaceHasChildren(lattice, #[2], 1, 3, 4)
		assertFaceHasChildren(lattice, #[3], 0, 2, 4)
		assertFaceHasChildren(lattice, #[4], 0, 1, 2, 3)
		
		// Check the 1-faces.
		assertFaceHasChildren(lattice, #[0,1], #[#[4], #[2,3]])
		assertFaceHasChildren(lattice, #[0,3], #[#[4], #[1,2]])
		assertFaceHasChildren(lattice, #[0,4], 1, 3)
		assertFaceHasChildren(lattice, #[1,2], #[#[4], #[0,3]])
		assertFaceHasChildren(lattice, #[1,4], 0, 2)
		assertFaceHasChildren(lattice, #[2,3], #[#[4], #[0,1]])
		assertFaceHasChildren(lattice, #[2,4], 1, 3)
		assertFaceHasChildren(lattice, #[3,4], 0, 2)
		
		assertVerticesExist(lattice, #[0,1,4], #[0,1,2,3], #[0,3,4], #[1,2,4], #[2,3,4])
	}
	
	@Test
	def testThickEquality_1() {
		val lattice = makeLattice("[N]->{[i,j]: 0<=i<2 and -N+10<j<N}")
		val dim = lattice.rootInfo.dimensionality
		
		assertEquals(dim, 1)
	}
	
	@Test
	def testThickEquality_2() {
		val lattice = makeLattice("[N]->{[i,j,k]: 0<=k<=-N+i+j and k<=2N-2i+j and -5+2N+i-2j<=k<=2N+i-2j}")
		val dim = lattice.rootInfo.dimensionality
		assertEquals(dim, 2)
		assertFaceCounts(lattice, 3, 3, 1)
		
		val facets = lattice.getChildren(lattice.rootInfo)
		facets.forEach[f | assertTrue(f.hasThickFaces)]
	}
	
	@Test
	def testThickEquality_3() {
		val set1 = "[N]->{[i,j,k,l]: N>=11 and i<=3 and 2+i<=j<=N and i<=k<=-2+2i and -2+i+j<=l<i+j}".toISLBasicSet
		val set2 = "[N]->{[i,j]: N>=11 and 0<=i<=N and i<j and j<i+5}".toISLBasicSet
		
		assertEquals(set1.dimensionality, 1)
		assertEquals(set2.dimensionality, 1)
	}
}