package alpha.model.tests.transformations

import alpha.loader.AlphaLoader
import alpha.model.transformation.automation.SimplifyingReductionOptimalSimplificationAlgorithm
import org.junit.Test

import static org.junit.Assert.*

import static extension alpha.model.util.AlphaUtil.*

class OptimalSimplificationAlgorithmTest {
	
	@Test
	def testOSRA_1() {
		val root = AlphaLoader.loadAlpha('resources/src-valid/simplifying-reductions/simplices.alpha');
		val systemName = 'ex_2d'
		val system = root.firstPackage.getSystem(systemName)
		
		val optimizer = SimplifyingReductionOptimalSimplificationAlgorithm.apply(system)
		val roots = optimizer.getOptimizedPrograms
		
		assertEquals(roots.size, 2)
	}
	
	@Test
	def testOSRA_2() {
		val root = AlphaLoader.loadAlpha('resources/src-valid/simplifying-reductions/simplices.alpha');
		val systemName = 'ex_3d'
		val system = root.firstPackage.getSystem(systemName)
		
		val optimizer = SimplifyingReductionOptimalSimplificationAlgorithm.apply(system)
		val roots = optimizer.getOptimizedPrograms
		
		assertEquals(roots.size, 12)
	}
	
	
	
//  The following 4d examples takes a very long time to execute
//	@Test
//	def testOSRA_3() {
//		val root = AlphaLoader.loadAlpha('resources/src-valid/simplifying-reductions/simplices.alpha');
//		val systemName = 'ex_4d'
//		val system = root.firstPackage.getSystem(systemName)
//		
//		val optimizer = SimplifyingReductionOptimalSimplificationAlgorithm.apply(system)
//		val roots = optimizer.getOptimizedPrograms
//		
//		assertEquals(roots.size, 12)
//	}
	
}