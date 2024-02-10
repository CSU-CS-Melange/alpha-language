package alpha.model.tests

import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import java.util.List
import org.junit.Test

import static org.junit.Assert.*

import static extension alpha.model.util.ISLUtil.*
import static extension alpha.model.util.DomainOperations.toBasicSetFromKernel
import static extension fr.irisa.cairn.jnimap.isl.ISLBasicSet.buildUniverse

class DomainOperationsTest {
	
	// Miscellaneous helper functions
	
	/** Convert a nested list representation to primitive array */
	def static long[][] toLongArray(List<List<Integer>> arr) {
		arr.map[row | row.map[v | v.longValue] as long[]] as long[][]
	}
	
	/** Asserts that two sets are equal */
	def static assertEquals(ISLBasicSet bset1, ISLBasicSet bset2) {
		val set1 = bset1.toSet
		val set2 = bset2.toSet
		val diff1 = set1.copy.subtract(set2.copy)
		val diff2 = set2.copy.subtract(set1.copy)
		
		assertTrue(diff1.isEmpty)
		assertTrue(diff2.isEmpty)
	}
	
	// Tests
	
	@Test
	def testToBasicSetFromKernel_1() {
		val space = '[N]->{[i,j]}'.toISLBasicSet.space
		val kernel = #[#[]].toLongArray
		
		val set = kernel.toBasicSetFromKernel(space)
		
		assertEquals(set, space.buildUniverse)
	}
	
	@Test
	def testToBasicSetFromKernel_2() {
		val space = '[N]->{[i,j]}'.toISLBasicSet.space
		val kernel1 = #[#[0, 0, 0, 0]].toLongArray
		val kernel2 = #[#[0]].toLongArray
		
		try {
		    val set = kernel1.toBasicSetFromKernel(space)
		    fail("There are more columns than there are params + indices");
		} catch (IllegalArgumentException e) {
			assertTrue(true)
		}
		try {
		    val set = kernel2.toBasicSetFromKernel(space)
		    fail("There are fewer columns than there are params + indices");
		} catch (IllegalArgumentException e) {
			assertTrue(true)
		}
	}
		
	@Test
	def testToBasicSetFromKernel_3() {
		val space = '[N]->{[i,j]}'.toISLBasicSet.space
		val kernel = #[#[0, 0, 0]].toLongArray
		
		val set = kernel.toBasicSetFromKernel(space)
		
		assertEquals(set, '[N]->{[i,j]: i=0 and j=0}'.toISLBasicSet)
	}
	
	@Test
	def testToBasicSetFromKernel_4() {
		val space = '[N]->{[i,j]}'.toISLBasicSet.space
		val kernel = #[#[0, 1, 0]].toLongArray
		
		val set = kernel.toBasicSetFromKernel(space)
		
		assertEquals(set, '[N]->{[i,j]: j=0}'.toISLBasicSet)
	}
	
	@Test
	def testToBasicSetFromKernel_5() {
		val space = '[N]->{[i,j]}'.toISLBasicSet.space
		val kernel = #[#[0, 1, 1]].toLongArray
		
		val set = kernel.toBasicSetFromKernel(space)
		
		assertEquals(set, '[N]->{[i,j]: i=j}'.toISLBasicSet)
	}
	
	@Test
	def testToBasicSetFromKernel_6() {
		val space = '[N]->{[i,j,k]}'.toISLBasicSet.space
		val kernel = #[
			#[0, 1, 1, 0],
			#[0, 0, 0, 1]
		].toLongArray
		
		val set =  kernel.toBasicSetFromKernel(space)
		
		assertEquals(set, '[N]->{[i,j,k]: i=j}'.toISLBasicSet)
	}
	
	@Test
	def testToBasicSetFromKernel_7() {
		val space = '[N]->{[i,j,k]}'.toISLBasicSet.space
		val kernel = #[
			#[0, 1, 1, 0],
			#[0, 0, 1, 0]
		].toLongArray
		
		val set = kernel.toBasicSetFromKernel(space)
		
		assertEquals(set, '[N]->{[i,j,k]: k=0}'.toISLBasicSet)
	}
	
	@Test
	def testToBasicSetFromKernel_8() {
		val space = '[N]->{[i,j]}'.toISLBasicSet.space
		val kernel = #[
			#[0, 1, 0],
			#[0, 0, 1]
		].toLongArray
		
		val set = kernel.toBasicSetFromKernel(space)
		
		assertEquals(set, space.buildUniverse)
	}
}
