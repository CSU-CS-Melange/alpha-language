package alpha.model.scheduler

import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

class ManualScheduler implements Scheduler {
	ISLUnionMap maps
	ISLUnionSet domains
	
	new(ISLUnionMap maps, ISLUnionSet domains) {
		this.maps = maps
		this.domains = domains
	}
	
	new(ISLSchedule schedule) {
		this.maps = schedule.map
		this.domains = schedule.domain
	}
	
	override getScheduleMap(String variable) {
		val map = this.maps.maps.filter(map | map.inputTupleName == variable).head ?: null
		if(map === null) {
			null
		} else {
			map.copy
		}
	}
	
	override getScheduleDomain(String variable) {
		val domain = this.domains.sets.filter(set | set.tupleName == variable).head ?: null
		if(domain === null) {
			null
		} else {
			domain.copy
		}
	}
	
	override getMaps() {
		this.maps.copy
	}
	
	override getDomains() {
		this.domains.copy
	}
}