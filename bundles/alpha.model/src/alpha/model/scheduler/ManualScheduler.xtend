package alpha.model.scheduler

import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

import static extension alpha.model.util.ISLUtil.toISLUnionMap
import static extension alpha.model.util.ISLUtil.toISLUnionSet

class ManualScheduler implements Scheduler {
	ISLUnionMap maps
	ISLUnionSet domains
	ISLSchedule schedule
	
	new(String maps, String domains) {
		this.maps = toISLUnionMap(maps)
		this.domains = toISLUnionSet(domains)
		this.schedule = ISLSchedule.buildFromDomain(this.domains).insertPartialSchedule(this.maps.copy.toMultiUnionPWAff)
	}
	
	override getScheduleMap(String variable) {
		this.schedule.map.maps.filter(map | map.inputTupleName == variable).head.copy
	}
	
	override getScheduleDomain(String macro) {
		this.schedule.domain.sets.filter(set | set.tupleName == macro).head.copy
	}
	
	override getSchedule() {
		this.schedule.copy
	}
	
}