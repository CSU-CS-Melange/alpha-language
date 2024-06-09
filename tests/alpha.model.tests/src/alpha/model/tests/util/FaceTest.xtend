package alpha.model.tests.util

import alpha.model.util.Face
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLContext
import org.junit.Test

import static org.junit.Assert.*

class FaceTest {
	////////////////////////////////////////////////////////////
	// Face Creation
	////////////////////////////////////////////////////////////
	
	/** Creates a face from the desired set. */
	static def makeFace(String setDescriptor) {
		val set = ISLBasicSet.buildFromString(ISLContext.instance, setDescriptor).removeRedundancies
		return new Face(set)
	}
	
	/** The empty set. */
	public static val emptySetString = "{[i,j]: 0<i<j and j<0}"
	static def emptySet() { makeFace(emptySetString) }
	
	/** A set containing a single point. */
	public static val vertexString = "{[i,j]: i=0 and j=1}"
	static def vertex() { makeFace(vertexString)}
	
	/** A line which is infinite in both directions. */
	public static val infiniteLineString = "{[i]:}"
	static def infiniteLine() { makeFace(infiniteLineString)}
	
	/** A ray, or a line which has one point and extends infinitely in one direction. */
	public static val rayString = "{[i]: 0<=i}"
	static def ray() { makeFace(rayString)}
	
	/** A line segment which is defined between two constants. */
	public static val constantLineSegmentString = "{[i]: 0<=i<=10}"
	static def constantLineSegment() { makeFace(constantLineSegmentString)}
	
	/** A line segment from 0 to N. */
	public static val parameterizedLineSegmentString = "[N]->{[i]: 0<=i<N}"
	static def parameterizedLineSegment() { makeFace(parameterizedLineSegmentString)}
	
	
	////////////////////////////////////////////////////////////
	// Unit Tests
	////////////////////////////////////////////////////////////
	
	/** Checks that the number of children of a face is correct. */
	static def assertChildCount(Face face, int expectedChildren) {
		assertEquals(expectedChildren, face.generateChildren.size)
	}

	@Test def testChildCount_01() { emptySet.assertChildCount(0) }
	@Test def testChildCount_02() { vertex.assertChildCount(0) }
	@Test def testChildCount_03() { infiniteLine.assertChildCount(0) }
	@Test def testChildCount_04() { ray.assertChildCount(1) }
	@Test def testChildCount_05() { constantLineSegment.assertChildCount(0) }
	@Test def testChildCount_06() { parameterizedLineSegment.assertChildCount(2) }
	
	/** Checks that the dimensionality of a face is correct. */
	static def assertDimensionality(Face face, int expectedDimensionality) {
		assertEquals(expectedDimensionality, face.dimensionality)
	}
	
	@Test def testDimensionality_01() { emptySet.assertDimensionality(0) }
	@Test def testDimensionality_02() { vertex.assertDimensionality(0) }
	@Test def testDimensionality_03() { infiniteLine.assertDimensionality(1) }
	@Test def testDimensionality_04() { ray.assertDimensionality(1) }
	@Test def testDimensionality_05() { parameterizedLineSegment.assertDimensionality(1) }
	
	/**
	 * If a line segment is bounded by two constants, the constraints are effectively saturated,
	 * resulting in the face being reported as being effectively the same as a vertex.
	 */
	@Test def testDimensionality_06() { constantLineSegment.assertDimensionality(0) }
	
	static def assertBasicSetMatches(String descriptor) {
		val basicSet = ISLBasicSet.buildFromString(ISLContext.instance, descriptor)
		val face = makeFace(descriptor)
		assertTrue(basicSet.isEqual(face.toBasicSet))
	}
	
	@Test def testBasicSetMatches_01() { emptySetString.assertBasicSetMatches }
	@Test def testBasicSetMatches_02() { vertexString.assertBasicSetMatches }
	@Test def testBasicSetMatches_03() { infiniteLineString.assertBasicSetMatches }
	@Test def testBasicSetMatches_04() { rayString.assertBasicSetMatches }
	@Test def testBasicSetMatches_05() { constantLineSegmentString.assertBasicSetMatches }
	@Test def testBasicSetMatches_06() { parameterizedLineSegmentString.assertBasicSetMatches }
	
	@Test
	def testChildren_01() {
		// Build a face from a known set.
		val descriptor = "[N] -> {[i]: 0<=i<=N and N>=1}"
		val face = makeFace(descriptor)
		
		// We are expecting two child faces: i=0 and i=N.
		val children = face.generateChildren
		assertEquals(2, children.size)
		
		// We don't care what order they are in, so just check that both expected children exist.
		val iEqualsZero = ISLBasicSet.buildFromString(ISLContext.instance, "[N] -> {[i]: i=0 and N>=1}")
		assertTrue(children.exists[it.toBasicSet.isEqual(iEqualsZero.copy)])
		
		val iEqualsN = ISLBasicSet.buildFromString(ISLContext.instance, "[N] -> {[i]: i=N and N>=1}")
		assertTrue(children.exists[it.toBasicSet.isEqual(iEqualsN.copy)])
	}
	
	@Test
	def testToLinearSpace_01() {
		val descriptor = "[N] -> {[i,j]: 0<=i,j<=N}"
		val face = makeFace(descriptor)
		
		val lps = face.generateChildren.map[toLinearSpace]
		
		assertEquals(lps.get(0).toString, '[N] -> { [i, j] : i = 0 }')
		assertEquals(lps.get(1).toString, '[N] -> { [i, j] : i = 0 }')
		assertEquals(lps.get(2).toString, '[N] -> { [i, j] : j = 0 }')
		assertEquals(lps.get(3).toString, '[N] -> { [i, j] : j = 0 }')
	}
}