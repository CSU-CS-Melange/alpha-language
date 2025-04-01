package alpha.model.scheduler

import alpha.model.Variable
import alpha.model.prdg.PRDG
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSchedule.JNIISLSchedulingOptions
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.util.ISLUtil.*

class HybridScheduler implements Scheduler {
	ISLSchedule foutrierSchedule
	ISLSchedule plutoSchedule
	PRDG prdg
	int timeDims
	
	new(PRDG prdg) {
		this.prdg = prdg
		this.generateSchedule
	}
	
	def generateSchedule() {
		var ISLUnionSet domains = this.prdg.generateDomains
		var islPRDG = this.prdg.generateISLPRDG
		this.foutrierSchedule = ISLSchedule.computeSchedule(domains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER)
		this.plutoSchedule = ISLSchedule.computeSchedule(domains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL)
		//TODO: calculate timeDims
	}
	
	override ISLSet getScheduleDomain(String variable) {
		this.foutrierSchedule.domain.sets.filter(set | set.tupleName == variable).head.copy
	}

	override ISLMap getScheduleMap(String variable) {
		this.maps.maps.filter(map | map.inputTupleName == variable).head.copy
		
	}
	
	override ISLUnionMap getMaps() {
		//vTODO: Fuse schedules
		this.foutrierSchedule.map.copy
	}
	
	override ISLUnionSet getDomains() {
		this.foutrierSchedule.domain.copy
	}

}