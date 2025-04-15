package alpha.model.scheduler

import alpha.model.Variable
import alpha.model.prdg.PRDG
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSchedule.JNIISLSchedulingOptions
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import alpha.model.util.ISLUtil
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet

class FoutrierScheduler implements Scheduler {
	ISLSchedule schedule
	PRDG prdg
	ISLUnionMap umap
	
	new(PRDG prdg) {
		this.prdg = prdg
		this.generateSchedule
	}
	
	def generateSchedule() {
		var ISLUnionSet domains = this.prdg.generateDomains
		var islPRDG = this.prdg.generateISLPRDG
		this.schedule = ISLSchedule.computeSchedule(domains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER)
		this.umap = ISLUtil.liftSpacetimeFactors(schedule.map)
	}
	
	override ISLSet getScheduleDomain(String variable) {
		this.schedule.domain.sets.filter(set | set.tupleName == variable).head.copy
	}

	override ISLMap getScheduleMap(String variable) {
		umap.maps.filter(map | map.inputTupleName == variable).head.copy
	}
	
	override ISLUnionMap getMaps() {
		umap.copy
	}
	
	override ISLUnionSet getDomains() {
		this.schedule.domain.copy
	}
	
 	override ISLMap getAnonymousMap(String variable) {
 		return getScheduleMap(variable).clearInputTupleName
 	}
}