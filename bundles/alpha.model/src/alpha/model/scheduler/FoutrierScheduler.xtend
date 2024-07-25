package alpha.model.scheduler

import alpha.model.prdg.PRDG
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSchedule.JNIISLSchedulingOptions
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

import static extension fr.irisa.cairn.jnimap.isl.ISLMultiAff.buildIdentity

class FoutrierScheduler implements Scheduler {
	ISLSchedule schedules
	PRDG prdg
	
	new(PRDG prdg) {
		this.prdg = prdg
		this.generateSchedule
	}
	
	override generateSchedule() {
		var ISLUnionSet domains = this.prdg.generateDomains
		var islPRDG = this.prdg.generateISLPRDG
		this.schedules = ISLSchedule.computeSchedule(domains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER)
	}
	
	override getScheduleDomain(String macro) {
		this.schedule.domain.sets.filter(set | set.tupleName == macro).head
	}
	
	override getSchedule() {
		if (this.schedules === null) {
			this.generateSchedule
		}
		this.schedules
	}
	
	override getScheduleMap(String variable) {
		this.schedule.map.maps.filter(map | map.inputTupleName == variable).head
		
	}

}