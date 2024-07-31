package alpha.model.scheduler

import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

import static extension alpha.model.util.ISLUtil.toISLUnionMap
import static extension alpha.model.util.ISLUtil.toISLUnionSet

class ManualScheduler implements Scheduler {
	ISLUnionMap maps
	ISLUnionSet domains
	
	new(String maps, String domains) {
		this.maps = toISLUnionMap(maps)
		this.domains = toISLUnionSet(domains)
	}
	
	override getScheduleMap(String variable) {
		this.maps.maps.filter(map | map.inputTupleName == variable).head.copy
	}
	
	override getScheduleDomain(String macro) {
		this.domains.sets.filter(set | set.tupleName == macro).head.copy
	}
	
	override getMaps() {
		this.maps
	}
	
	override getDomains() {
		this.domains
	}
	
}