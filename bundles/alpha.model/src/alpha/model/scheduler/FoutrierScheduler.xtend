package alpha.model.scheduler

import alpha.model.Variable
import alpha.model.prdg.PRDG
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSchedule.JNIISLSchedulingOptions
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

class FoutrierScheduler implements Scheduler {
	ISLSchedule schedule
	PRDG prdg
	
	new(PRDG prdg) {
		this.prdg = prdg
		this.generateSchedule
	}
	
	def generateSchedule() {
		var ISLUnionSet domains = this.prdg.generateDomains
		var islPRDG = this.prdg.generateISLPRDG
		this.schedule = ISLSchedule.computeSchedule(domains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER)
	}
	
	override getScheduleDomain(String variable) {
		this.schedule.domain.sets.filter(set | set.tupleName == variable).head.copy
	}

	override getScheduleMap(String variable) {
		this.schedule.map.maps.filter(map | map.inputTupleName == variable).head.copy
		
	}
	
	override getMaps() {
		this.schedule.map.copy
	}
	
	override getDomains() {
		this.schedule.domain.copy
	}

}