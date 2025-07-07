package alpha.model.tiler

import alpha.model.AlphaSystem
import alpha.model.scheduler.ScheduleVerifier
import alpha.model.scheduler.Scheduler

import static extension alpha.model.util.ISLUtil.*

class TileVerifier {
	//Verifies the tiling with respect to a schedule
	def static void verify(AlphaSystem sys, Scheduler scheduler, Tiler tiler) {
		val maps = tiler.tileSchedule(scheduler.maps).maps
		var verifier = new ScheduleVerifier(maps)
		verifier.accept(sys)
	}
	
	//Verifies that the tiling is valid for a wavefront schedule
	def static void verifyWavefront(AlphaSystem sys, Scheduler scheduler, Tiler tiler, int timeDims) {
		val maps = tiler.tileSchedule(scheduler.maps).maps.map[
			toMultiAff.affs.subList(0, timeDims).convertToMultiAff.toMap
		]
		var verifier = new ScheduleVerifier(maps)
		verifier.accept(sys)
	}
	
}