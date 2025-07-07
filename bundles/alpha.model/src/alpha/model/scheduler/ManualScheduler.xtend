package alpha.model.scheduler

import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

class ManualScheduler extends Scheduler {
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
	
	override getMaps() {
		this.maps.copy
	}
	
	override getDomains() {
		this.domains.copy
	}
	
	override protected getSchedule() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}