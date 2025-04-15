package alpha.model.tiler

import alpha.model.exception.CausalityViolationException
import alpha.model.prdg.PRDG
import alpha.model.prdg.PRDGEdge
import alpha.model.scheduler.Scheduler
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.util.ISLUtil.*
import java.util.Set

/**
 * Checks whether a particular schedule is tiling-legal.
 * For this to be the case, all dependence vectors 
 * (from producer to consumer) must lie in the positive orthant
 * when transformed by the space-time map.
 */
class AdmitsTiling {
	/**
	 * Returns true if a schedule admits a tiling
	 */
	def static boolean check(PRDG prdg, Scheduler scheduler, Set<Integer> tiledDims) {
		prdg.edges.forall[tilingLegal(scheduler, tiledDims, false)]
	}
	
	def static boolean check(PRDG prdg, Scheduler scheduler) {
		check(prdg, scheduler, (0 ..< scheduler.maps.getNbOutputs).toSet)
	}
	
	/**
	 * Throws an error if a schedule does not admit a tiling,
	 * with information on where the tiling fails.
	 */
	def static void verify(PRDG prdg, Scheduler scheduler, Set<Integer> tiledDims) {
		prdg.edges.forall[tilingLegal(scheduler, tiledDims, true)]
	}
	
	def static void verify(PRDG prdg, Scheduler scheduler) {
		verify(prdg, scheduler, (0 ..< scheduler.maps.getNbOutputs).toSet)
	}
	
	/**
	 * Returns false (or throws an error)
	 * if a given PRDG edge makes the tiling illegal.
	 */
	def static private boolean tilingLegal(PRDGEdge edge, Scheduler scheduler, Set<Integer> tiledDims, boolean noisy) {
		//TODO: Fix this
		val sourceTimestamp = scheduler.getAnonymousMap(edge.source.name).toMultiAff
		val destTimestamp = edge.map.applyRange(scheduler.getAnonymousMap(edge.dest.name)).lexMax.toMultiAff
		
		val violationSets = tiledDims.map[ dim | 
			val violationSet = ISLSet.buildLTSet(sourceTimestamp.getAff(dim), destTimestamp.getAff(dim))
				.intersect(edge.domain)
			
			if(noisy && !violationSet.isEmpty) {
				println("Intra-tile causality is invalid for the given schedule.")
				throw new CausalityViolationException(edge.map, sourceTimestamp, destTimestamp, edge.domain, dim)
			}
			
			return violationSet
		]
		
		return violationSets.forall[isEmpty]
	}	
}