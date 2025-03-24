package alpha.model.tests.scheduling

import org.junit.Test
import static org.junit.Assert.*
import alpha.model.prdg.PRDG
import alpha.model.AlphaSystem
import alpha.model.AlphaModelLoader
import alpha.model.prdg.PRDGGenerator
import alpha.model.scheduler.FoutrierScheduler
import alpha.model.scheduler.Scheduler
import alpha.model.tiler.Tiler
import java.util.ArrayList
import java.util.Collections
import alpha.model.tiler.DTiler
import fr.irisa.cairn.jnimap.isl.ISLMap

//TODO: make these tests more robust once the FoutrierScheduler tests are merged

class DTilerTest {
	var PRDG prdg
	var AlphaSystem sys
	var Scheduler scheduler
	var Tiler tiler
	
	def readFile(String file) {
		var root = AlphaModelLoader.loadModel(file)
		sys = root.systems.get(0)
		prdg = PRDGGenerator.apply(sys)
		scheduler = new FoutrierScheduler(prdg)
	}
	
	def assertBijection() {
		scheduler.getMaps.getMaps.forEach[map | 
			assertTrue(tiler.getTileMap.isBijective)
		]
	}
	
	def cubicTile(int width, int startDim, int endDim) {
		val tileSizes = new ArrayList<Integer>(Collections.nCopies(endDim-startDim+1, width))
		tiler = new DTiler(tileSizes, scheduler, startDim, endDim)
	}
	
	@Test
	def void testDot() {
		readFile("resources/src-valid/basic/matmult.unit/dot.alpha")
		cubicTile(64, 1, 1)
		assertBijection
	}
	
	@Test
	def void testBPMax() {
		readFile("resources/src-valid/kernels/bpmax.alpha")
		cubicTile(64, 0, 5)
		assertBijection
	}
	
	@Test
	def void testCholesky() {
		readFile("resources/src-valid/kernels/cholesky.alpha")
		cubicTile(64, 0, 2)
		assertBijection
	}
}