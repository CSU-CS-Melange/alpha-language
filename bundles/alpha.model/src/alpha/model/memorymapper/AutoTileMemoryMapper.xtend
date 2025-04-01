package alpha.model.memorymapper

import alpha.model.tiler.Tiler
import alpha.model.scheduler.Scheduler
import alpha.model.prdg.PRDG
import java.util.HashMap
import alpha.model.prdg.PRDGNode
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLMap
import static extension alpha.model.util.ISLUtil.*
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLPoint
import alpha.model.tiler.DTiler
import fr.irisa.cairn.jnimap.isl.ISLConstraint

/**
 * Creates a tiled memory map that minimizes memory usage
 * and leverages the locality created by a tiling.
 * Tiles may be overwritten in memory as soon as their 'lifetime' has expired
 * The lifetime of a tile is the timestamp at which it is last read minus
 * the timestamp at which it is first written to
 * 
 * Only attempts to optimize the first dimension of time for now
 */
class AutoTileMemoryMapper {
	var HashMap<String, ISLMap> memoryMaps
	val Tiler tiler
	val PRDG prdg
	val Scheduler scheduler
	
	new(Tiler tiler, PRDG prdg, Scheduler scheduler) {
		this.tiler = tiler
		this.prdg = prdg
		this.scheduler = scheduler
		
		generateMemoryMaps
	}
	
	
	def private void generateMemoryMaps() {
		this.prdg.nodes.forEach[
			val ISLSet lifespan = maxLifespan
			
			if(lifespan.hasUpperBound(ISLDimType.isl_dim_set, 0)) {
				val tiledLifespan = lifespan.apply(tiler.tileMap)
				val tileWidth = tiledLifespan.getUpperBound(ISLDimType.isl_dim_set, 0) //TODO: implement getUpperBound
			} else {
				//If no optimizations are possible, just apply a tiled memory map
				val tiledMap = scheduler.getScheduleMap(name).clearInputTupleName
					.applyRange(tiler.tileMap)
				memoryMaps.put(name, tiledMap)
			}
		]
	}
	
	//Seems to work well. Now, what do do with this?
	//if bounded, mod first tile index with ceil(first dimension / tilesize ) + 1
	// ?
	def ISLSet maxLifespan(PRDGNode variable) {
		var domain = variable.domain.copy
		
		//If the outermost tile size is known, we can exclude the 'top'
		//of the domain, as it will never be overwritten.
		if(this.tiler instanceof DTiler) {
			domain = domain.intersect(
				domain.copy.apply(
					scheduler.getScheduleMap(variable.name).clearInputTupleName
						.toMultiAff.getAff(0)
						.scale((tiler as DTiler).getTileSize(0)).negate
						.affToVector.buildTranslationMaff
						.toMap
				)
			)
		}
		
		val ISLMap untileMap = tiler.untileMap.intersectRange(
			domain.copy.apply(
				scheduler.getScheduleMap(variable.name).clearInputTupleName
			)
		).simpleHull.toMap
		
		val dependences = prdg.edges.filter[dest == variable]
		
		val ISLPWMultiAff birth = untileMap.copy.lexMin.toPWMultiAff
		
		val Iterable<ISLPWMultiAff> lastUses = dependences.map[
			untileMap.copy.applyRange(
				getMap.applyDomain(scheduler.getScheduleMap(source.name).clearInputTupleName)
					.applyRange(scheduler.getScheduleMap(dest.name).clearInputTupleName)
					.reverse
			).lexMax
		].map[toPWMultiAff]
		
		
		val ISLMap lifespan = lastUses.map[sub(birth.copy).toMap]
			.reduce[a, b | a.union(b)]
			.simpleHull.toMap
			
		return lifespan.getRange.lexMax.simpleHull.toSet
	}
}