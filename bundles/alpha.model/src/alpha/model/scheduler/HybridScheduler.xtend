package alpha.model.scheduler

import alpha.model.AlphaSystem
import alpha.model.prdg.PRDG
import alpha.model.prdg.PRDGEdge
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSchedule.JNIISLSchedulingOptions
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

import static extension alpha.model.util.ISLUtil.*

class HybridScheduler extends Scheduler {
	Iterable<ISLMap> feautrierMaps
	Iterable<ISLMap> plutoMaps
	int timeDims
	ISLUnionMap spacetimeMap
	ISLUnionSet domains
	PRDG prdg
	
	new(AlphaSystem system, PRDG prdg) {
		this.prdg = prdg
		this.generateSchedule(system)
	}
	
	def generateSchedule(AlphaSystem system) {
		var ISLUnionSet islDomains = prdg.generateDomains
		val islPRDG = prdg.generateISLPRDG
		val feautrierSchedule = ISLSchedule.computeSchedule(islDomains.copy, islPRDG.copy, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER)
		val plutoSchedule = ISLSchedule.computeSchedule(islDomains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL)
		
		feautrierMaps = feautrierSchedule.map.maps
		plutoMaps = plutoSchedule.map.maps
		domains = feautrierSchedule.domain
		timeDims = system.countTimeDimensions(feautrierSchedule.map)
		
		spacetimeMap = feautrierMaps.map[inputTupleName]
			.map[generateHybridMap]
			.toList.convertToUnionMap
	}
	
	def private ISLMap generateHybridMap(String name) {
		val isReduction = prdg.getNode(name).isReductionNode
		
		val dominantEdge = isReduction ? prdg.dominantDependence(name) : null
		val mapName = isReduction ? dominantEdge.dest.name : name
		val scheduleOffset = isReduction ? 1 : 0
			
		var timeAffs = feautrierMaps.findFirst[inputTupleName == mapName].copy.clearInputTupleName
			.toMultiAff.affs.subList(0, timeDims)
		
		if(isReduction) {
			timeAffs = timeAffs.convertToMultiAff.pullback(dominantEdge.map.toMultiAff).affs
		}

		val spaceAffs = plutoMaps.findFirst[inputTupleName == name].copy.clearInputTupleName
			.toMultiAff.affs
		
		val hybridAffs = timeAffs
			+ #[ISLAff.buildValOnDomain(domains.sets.findFirst[tupleName == name].copy.clearTupleName.space.toLocalSpace, scheduleOffset)]
			+ spaceAffs
		
		var hybridMap = hybridAffs.toList.convertToMultiAff.toMap
		
			
		return hybridMap
			.setInputTupleName(name)
	}
	
	def private PRDGEdge dominantDependence(PRDG prdg, String name) {
		prdg.edges.filter[source.name == name]
			.reduce[a, b | dominantEdge(a,b)]
	}
	
	def private PRDGEdge dominantEdge(PRDGEdge a, PRDGEdge b) {
		val sourceSet = a.source.domain
		val timestampA = feautrierMaps.findFirst[inputTupleName == a.dest.name].copy.clearInputTupleName
		val timestampB = feautrierMaps.findFirst[inputTupleName == b.dest.name].copy.clearInputTupleName
		
		val availA = a.map.applyRange(timestampA).toMultiAff
		val availB = b.map.applyRange(timestampB).toMultiAff
		
		return sourceSet.isSubset(buildLexGESet(availA, availB)) ? a : b
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