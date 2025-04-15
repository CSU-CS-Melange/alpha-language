package alpha.model.scheduler

import alpha.model.Variable
import alpha.model.prdg.PRDG
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSchedule.JNIISLSchedulingOptions
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap

class PlutoScheduler implements Scheduler {
	ISLSchedule schedule
	PRDG prdg
	
	new(PRDG prdg) {
		this.prdg = prdg
		this.generateSchedule
	}
	
	def generateSchedule() {
		var ISLUnionSet domains = this.prdg.generateDomains
		var islPRDG = this.prdg.generateISLPRDG
		this.schedule = ISLSchedule.computeSchedule(domains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL)
	}
	
	override ISLSet getScheduleDomain(String variable) {
		schedule.domain.sets.findFirst[tupleName == variable].copy
	}

	override ISLMap getScheduleMap(String variable) {
		schedule.map.maps.findFirst[inputTupleName == variable].copy
	}
	
	override ISLUnionMap getMaps() {
		this.schedule.map.copy
	}
	
	override ISLUnionSet getDomains() {
		this.schedule.domain.copy
	}
	
	override ISLMap getAnonymousMap(String variable) {
 		return getScheduleMap(variable).clearInputTupleName
	}
}