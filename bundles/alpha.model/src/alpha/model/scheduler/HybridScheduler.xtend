package alpha.model.scheduler

import alpha.model.AlphaSystem
import alpha.model.prdg.PRDG
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSchedule.JNIISLSchedulingOptions
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

import static extension alpha.model.util.ISLUtil.*

class HybridScheduler extends Scheduler {
	ISLUnionMap spacetimeMap
	ISLUnionSet domains
	PRDG prdg
	
	new(AlphaSystem system, PRDG prdg) {
		this.prdg = prdg
		this.generateSchedule(system)
	}
	
	def generateSchedule(AlphaSystem system) {
		var ISLUnionSet islDomains = this.prdg.generateDomains
		val islPRDG = this.prdg.generateISLPRDG
		val foutrierSchedule = ISLSchedule.computeSchedule(islDomains.copy, islPRDG.copy, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER)
		val plutoSchedule = ISLSchedule.computeSchedule(islDomains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL)
		domains = foutrierSchedule.domain
		
		val timeDims = system.countTimeDimensions(foutrierSchedule.map)
		
		val varNames = foutrierSchedule.map.maps.map[inputTupleName]
		spacetimeMap = varNames.map[name |
			val timeAffs = foutrierSchedule.map.maps.findFirst[inputTupleName == name]
				.toMultiAff.affs
				.subList(0, timeDims)
			val spaceAffs = plutoSchedule.map.maps.findFirst[inputTupleName == name]
				.toMultiAff.affs
				
			return (timeAffs + spaceAffs)
				.toList.convertToMultiAff
				.toMap.setInputTupleName(name)
		].toList.convertToUnionMap
	}
	
	override ISLUnionMap getMaps() {
		spacetimeMap.copy
	}
	
	override ISLUnionSet getDomains() {
		domains.copy
	}
	
	override protected getSchedule() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}