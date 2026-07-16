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
import fr.irisa.cairn.jnimap.isl.ISLSet
import alpha.model.prdg.PRDGNode

class HybridScheduler extends Scheduler {
	Iterable<ISLMap> feautrierMaps
	Iterable<ISLMap> plutoMaps
	int timeDims
	ISLUnionMap spacetimeMap
	ISLUnionSet domains
	PRDG prdg
	boolean doSplit
	
	new(AlphaSystem system, PRDG prdg, boolean doSplit) {
		this.prdg = prdg
		this.doSplit = doSplit
		this.generateSchedule(system)
	}
	
	new(AlphaSystem system, PRDG prdg) {
		this.prdg = prdg
		this.doSplit = false
		this.generateSchedule(system)
	}
	
	new(AlphaSystem system, PRDG prdg, Iterable<ISLMap> originalScheduleMaps) {
		this.prdg = prdg
		this.doSplit = true
		feautrierMaps = originalScheduleMaps
		this.generateSchedule(system)
	}
	
	def generateSchedule(AlphaSystem system) {
		var ISLUnionSet islDomains = prdg.generateDomains
		val islPRDG = prdg.generateISLPRDG
		val feautrierSchedule = ISLSchedule.computeSchedule(islDomains.copy, islPRDG.copy, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER)
		val plutoSchedule = ISLSchedule.computeSchedule(islDomains, islPRDG, JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_ISL)
		
		if(feautrierMaps === null) feautrierMaps = feautrierSchedule.map.maps
		plutoMaps = plutoSchedule.map.maps
		domains = feautrierSchedule.domain
		timeDims = system.countTimeDimensions(feautrierSchedule.map)
		
		spacetimeMap = prdg.nodes
			.sortWith[a, b | (a.isReductionNode ? 0 : -1) + (b.isReductionNode ? 0 : 1)]
			.indexed
			.map[generateHybridMap(it.key, it.value)]
			.toList.convertToUnionMap
			.liftSpacetimeFactors
	}
	
	def private ISLMap generateHybridMap(int index, PRDGNode node) {
		val splitReduction = node.isReductionNode && doSplit
		
		val dominantEdge = splitReduction ? prdg.dominantDependence(node.name) : null
		
		if(splitReduction && dominantEdge === null) {
			throw new IllegalStateException("Error when computing split schedule: reduction node has no dominant dependence\n"
				+ "for node '" + node + "'\n"
				+ "and PRDG: \n" + prdg + "\n"
			)
		}
		
		val mapName = splitReduction ? dominantEdge.dest.name : node.name
		val scheduleOffset = index
			
		var timeAffs = feautrierMaps.findFirst[inputTupleName == mapName].copy.clearInputTupleName
			.toMultiAff.affs.subList(0, timeDims)
		
		if(splitReduction) {
			timeAffs = timeAffs.convertToMultiAff.pullback(dominantEdge.map.toMultiAff).affs
		}

		val spaceAffs = plutoMaps.findFirst[inputTupleName == node.name].copy.clearInputTupleName
			.toMultiAff.affs
		
		val hybridAffs = timeAffs
			+ #[ISLAff.buildValOnDomain(domains.sets.findFirst[tupleName == node.name].copy.clearTupleName.space.toLocalSpace, scheduleOffset)]
			+ spaceAffs
		
		var hybridMap = hybridAffs.toList.convertToMultiAff.toMap
		
			
		return hybridMap
			.setInputTupleName(node.name)
	}
	
	def private PRDGEdge dominantDependence(PRDG prdg, String name) {
		prdg.edges.filter[source.name == name]
			.reduce[a, b | dominantEdge(a,b)]
	}
	
	def private PRDGEdge dominantEdge(PRDGEdge a, PRDGEdge b) {
		if(a === null) return b;
		if(b === null) return a;
		
		val sourceSet = a.source.domain.copy
		val timestampA = feautrierMaps.findFirst[inputTupleName == a.dest.name].copy.clearInputTupleName
		val timestampB = feautrierMaps.findFirst[inputTupleName == b.dest.name].copy.clearInputTupleName
		
		val availA = a.map.applyRange(timestampA).toMultiAff.getAffs.get(0)
		val availB = b.map.applyRange(timestampB).toMultiAff.getAffs.get(0)
		
		if(sourceSet.copy.isSubset(ISLSet.buildGESet(availA.copy, availB.copy)))
			return a
		else if(sourceSet.isSubset(ISLSet.buildGESet(availB, availA)))
			return b
		else
			return null
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