package alpha.model.tests.scheduling

import org.junit.Test
import static org.junit.Assert.*
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import alpha.model.prdg.DependenceCone
import alpha.model.AlphaModelLoader
import alpha.model.prdg.PRDGGenerator
import alpha.model.util.ISLUtil
import fr.irisa.cairn.jnimap.isl.ISLBasicSet

class DependenceConeTest {
	def static DependenceCone buildCone(String file) {
		val root = AlphaModelLoader.loadModel(file)
		val sys = root.systems.get(0)
		val prdg = PRDGGenerator.apply(sys)
		new DependenceCone(prdg)
	}
	
	@Test
	def void testUniformDependenceConeGeneration() {
		var DependenceCone cone = buildCone("resources/src-valid/simplifying-reductions/uniformDependence.alpha")
		val ISLBasicSet coneSpace = ISLUtil.toISLBasicSet("[N] -> {[i, j] : 0 <= i < N and 0 <= j < N} ")
		
		assertTrue(coneSpace.isEqual(cone.cone))
	} 
	
	@Test
	def void testAffineDependenceConeGeneration() {
		var DependenceCone cone = buildCone("resources/src-valid/simplifying-reductions/affineDependence.alpha")
		val ISLBasicSet coneSpace = ISLUtil.toISLBasicSet("[N] -> {[i, j] : 0 <= i < N and 0 <= j < N} ")
		
		assertTrue(coneSpace.isEqual(cone.cone))
	} 
	
	@Test
	def void testConeIntersectsReuseSpace() {
		var DependenceCone cone = buildCone("resources/src-valid/simplifying-reductions/uniformDependence.alpha")
		
	}
	
	@Test
	def void testConeDoesntInteresectReuseSpace() {
		var DependenceCone cone = buildCone("resources/src-valid/simplifying-reductions/uniformDependence.alpha")
		val ISLSet output = ISLSet.buildEmpty(ISLSpace.allocSetSpace(1, 0))
		
	}
	
	@Test
	def void testSubtractionNoIntersection() {
		var DependenceCone cone = buildCone("resources/src-valid/simplifying-reductions/uniformDependence.alpha")
		
	}
	
	@Test
	def void testSubtractionIntersection() {
		var DependenceCone cone = buildCone("resources/src-valid/simplifying-reductions/uniformDependence.alpha")
		
	}
}