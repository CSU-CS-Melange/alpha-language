package alpha.model.scheduler

import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

import static alpha.model.util.ISLUtil.*

class ManualScheduler implements Scheduler {
	ISLUnionMap maps
	ISLUnionSet domains
	
	new(String maps, String domains) {
		this.maps = toISLUnionMap(maps)
		this.domains = toISLUnionSet(domains)
	}
	
		
	new(String scheduleString) {
		val schedule = toISLSchedule(scheduleString)
		println("BLAH: " + schedule)
		this.maps = schedule.map
		this.maps.maps.forEach[x | println("Sched: " + x)]
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