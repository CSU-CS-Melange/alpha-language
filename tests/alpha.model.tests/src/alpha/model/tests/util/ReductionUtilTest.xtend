package alpha.model.tests.util

import alpha.model.DependenceExpression
import alpha.model.ReduceExpression
import alpha.model.transformation.reduction.RemoveEmbedding
import org.junit.Test

import static alpha.commands.UtilityBase.GetEquation
import static alpha.commands.UtilityBase.GetSystem
import static alpha.model.tests.util.AlphaTestUtil.*
import static org.junit.Assert.*

import static extension alpha.model.ComplexityCalculator.complexity
import static extension alpha.model.analysis.reduction.ReductionUtil.isBoundary
import static extension alpha.model.tests.util.FaceTest.makeFace
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension alpha.model.util.ISLUtil.nullSpace
import static extension alpha.model.util.ISLUtil.toISLMultiAff
import static extension alpha.model.util.ISLUtil.toISLSet

class ReductionUtilTest {
	
	@Test
	def testNullSpace_01() {	
		val maff = '[N]->{[i,j,k]->[i,j]}'.toISLMultiAff
		val nullSpace = maff.nullSpace
		
		assertEquals(nullSpace.dimensionality, 1)
	}
	
	@Test
	def testNullSpace_02() {	
		val maff = '[N]->{[i,j,k]->[i,j-k]}'.toISLMultiAff
		val nullSpace = maff.nullSpace
		
		val set = '[N]->{[i,j,k] : i=0 and j=k}'.toISLSet
		
		assertEquals(nullSpace.copy.dimensionality, 1)
		assertTrue(nullSpace.isEqual(set))
	}
	
	@Test
	def testNullSpace_03() {	
		val maff = '[N]->{[i,j,k]->[i,j-k,k]}'.toISLMultiAff
		val nullSpace = maff.nullSpace
		
		assertEquals(nullSpace.copy.dimensionality, 0)
		assertTrue(nullSpace.isSingleton) // i.e., [0,0,0]
	}
	
	@Test
	def testBoundary_01() {
		val fp = '[N]->{[i,j]->[i]}'.toISLMultiAff
		
		val face1 = '[N]->{[i,j] : i=j}'.makeFace
		val face2 = '[N]->{[i,j] : j=2}'.makeFace
		val face3 = '[N]->{[i,j] : i=N}'.makeFace
		val face4 = '[N]->{[i,j] : i=0}'.makeFace
		val face5 = '[N]->{[i,j] : i=2}'.makeFace
		val face6 = '[N]->{[i,j] : i=17}'.makeFace
		
		assertFalse(face1.isBoundary(fp))
		assertFalse(face2.isBoundary(fp))
		assertTrue(face3.isBoundary(fp))
		assertTrue(face4.isBoundary(fp))
		assertTrue(face5.isBoundary(fp))
		assertTrue(face6.isBoundary(fp))
	}
	
	@Test
	def testRemoveRedundancy_01() {
		val root = loadValidFile('transformation-reduction-tests/identical-answers/identicalAnswers.alpha')
		val system = GetSystem(root, 'ex1')
		val body = system.systemBodies.get(0)
		
		val reduceExpr = GetEquation(body, 'Z').expr as ReduceExpression
		assertEquals(system.complexity, 3)
		
		val rho = '[N]->{[i,j,k]->[i,j,k+1]}'.toISLMultiAff
		RemoveEmbedding.apply(reduceExpr, rho)
		
		assertEquals(system.complexity, 2)
	}
	
	
}