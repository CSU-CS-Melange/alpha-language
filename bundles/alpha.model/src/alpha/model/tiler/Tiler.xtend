package alpha.model.tiler

import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import java.util.Set

interface Tiler {
	def ISLUnionMap tileSchedule(ISLUnionMap maps)
	def ISLMap tileSchedule(ISLMap map)
	
	/*
	 * Returns a map that takes in the indices of a tile, and outputs
	 * the corresponding region in spacetime
	 */
	def ISLMap getUntileMap()
	def Set<Integer> getTiledDims()
	def ISLSet getApproximateOutset(ISLUnionSet domains)
	
	def ISLUnionMap getParameterizedIterators(ISLUnionMap maps)
	
	//Whether or not the implementation makes use of fixed tile sizes
	//if so, it should implement getTileSize
	def boolean fixedTileSizes()
	def int getTileSize(int dim)
}