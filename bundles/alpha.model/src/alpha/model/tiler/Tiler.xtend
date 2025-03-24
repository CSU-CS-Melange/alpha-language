package alpha.model.tiler

import fr.irisa.cairn.jnimap.isl.ISLMap
import java.util.Set
import fr.irisa.cairn.jnimap.isl.ISLSet

interface Tiler {
	def ISLMap getTileMap()
	
	/*
	 * Returns a map that takes in the indices of a tile, and outputs
	 * the corresponding region in spacetime
	 */
	def ISLMap getUntileMap()
	def Set<Integer> getTiledDims()
	def ISLSet getApproximateOutset(ISLSet domain)
}