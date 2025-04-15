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
	
	//Whether or not the implementation makes use of fixed tile sizes
	//if so, it should implement getTileSize
	def boolean fixedTileSizes()
	def int getTileSize(int dim)
}