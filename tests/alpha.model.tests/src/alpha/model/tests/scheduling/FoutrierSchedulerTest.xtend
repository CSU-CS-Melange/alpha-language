package alpha.model.tests.scheduling

import alpha.model.AlphaModelLoader
import alpha.model.AlphaSystem
import alpha.model.prdg.PRDG
import alpha.model.prdg.PRDGGenerator
import alpha.model.scheduler.ScheduleVerifier
import org.junit.Test
import alpha.model.scheduler.Scheduler
import alpha.model.scheduler.FoutrierScheduler
import alpha.model.scheduler.ManualScheduler
import alpha.model.transformation.Normalize

class FoutrierSchedulerTest {
	var AlphaSystem sys
	var PRDG prdg
	
	def readFile(String file) {
		var root = AlphaModelLoader.loadModel(file)
		sys = root.systems.get(0)
		Normalize.apply(sys)
		prdg = PRDGGenerator.apply(sys)
	}
	
	def assertScheduleValid() {
		ScheduleVerifier.verify(sys, new FoutrierScheduler(prdg))	
	}
	
	@Test
	def void testDot() {
		readFile("resources/src-valid/basic/matmult.unit/dot.alpha")
		assertScheduleValid
	}
	
	@Test
	def void testBPMax() {
		readFile("resources/src-valid/kernels/bpmax.alpha")
		assertScheduleValid
	}
	
	@Test
	def void cholesky() {
		readFile("resources/src-valid/kernels/cholesky.alpha")
		assertScheduleValid
	}
}