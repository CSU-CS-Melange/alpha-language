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
	
	override getMacroSchedule(String macro) {
		val domain = prdg.getNode(macro).getDomain
		domain.space
			.addDims(ISLDimType.isl_dim_in, domain.nbIndices)
			.buildIdentity
			.toMap
	}
	
	override getSchedule() {
		if (this.schedules === null) {
			this.generateSchedule
		}
		this.schedules
	}

}