package alpha.model.scheduler

import alpha.model.prdg.PRDG
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSchedule.JNIISLSchedulingOptions
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

class PlutoScheduler extends Scheduler {
	ISLSchedule schedule
	PRDG prdg
	
	new(PRDG prdg) {
		this.prdg = prdg
		this.generateSchedule
	}
	
	def generateSchedule() {
		var ISLUnionSet domain = this.prdg.generateDomains
		var islPRDG = this.prdg.generateISLPRDG
		this.schedule = ISLSchedule.computeSchedule(domain, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL)
	}
	
	override getSchedule() {return schedule}
}