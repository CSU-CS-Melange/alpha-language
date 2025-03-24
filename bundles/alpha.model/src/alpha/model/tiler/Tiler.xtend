package alpha.model.tiler

import fr.irisa.cairn.jnimap.isl.ISLMap
import java.util.Set
import fr.irisa.cairn.jnimap.isl.ISLSet

interface Tiler {
	def ISLMap getTileMap()
	def Set<Integer> getTiledDims()
	def ISLSet getApproximateOutset(ISLSet domain)
}