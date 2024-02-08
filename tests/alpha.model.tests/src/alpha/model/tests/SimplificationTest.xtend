package alpha.model.tests

import org.junit.Test

import static org.junit.Assert.*

import static extension alpha.model.util.ISLUtil.*

class SimplificationTest {
	
	@Test
	def testReuseIntegerPoint_1() {
		val reuse = '[N]->{[i,j]: i>0 and j=0}'.toISLBasicSet
		assertEquals(reuse.integerPointClosestToOrigin.toString, '[1, 0]')
	}
	
	@Test
	def testReuseIntegerPoint_2() {
		val reuse = '[N]->{[i,j]: i<0 and j=0}'.toISLBasicSet
		assertEquals(reuse.integerPointClosestToOrigin.toString, '[-1, 0]')
	}

	@Test
	def testReuseIntegerPoint_3() {
		val reuse = '[N]->{[i,j]: i>0 and j>0}'.toISLBasicSet
		assertEquals(reuse.integerPointClosestToOrigin.toString, '[1, 1]')
	}

	@Test
	def testReuseIntegerPoint_4() {
		val reuse = '[N]->{[i,j]: i<0 and j<0}'.toISLBasicSet
		assertEquals(reuse.integerPointClosestToOrigin.toString, '[-1, -1]')
	}

	@Test
	def testReuseIntegerPoint_5() {
		val reuse = '[N]->{[i,j]: i<0 and j>0}'.toISLBasicSet
		assertEquals(reuse.integerPointClosestToOrigin.toString, '[-1, 1]')
	}

	@Test
	def testReuseIntegerPoint_6() {
		val reuse = '[N]->{[i,j,k]: i<0 and j>7i and k<-4i+4}'.toISLBasicSet
		assertEquals(reuse.integerPointClosestToOrigin.toString, '[-1, 0, 0]')
	}

	@Test
	def testReuseIntegerPoint_7() {
		val reuse = '[N,M,L]->{[i,j]: i<0 and j>0}'.toISLBasicSet
		assertEquals(reuse.integerPointClosestToOrigin.toString, '[-1, 1]')
	}
	
	@Test
	def testIsTrivial_1() {
		assertFalse('[N]->{[i,j,k]}'.toISLBasicSet.isTrivial)
		assertTrue('[N]->{[i,j,k] : i=0 and j=0 and k=0 }'.toISLBasicSet.isTrivial)
		assertFalse('[N]->{[i,j,k] : i=0 and j=0 and (k=0 or k=1)}'.toISLSet.isTrivial)
		assertTrue('[N]->{[i,j,k] : 1=0}'.toISLSet.isTrivial)
	}

	
}