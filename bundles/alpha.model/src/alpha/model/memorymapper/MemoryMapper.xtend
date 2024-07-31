package alpha.model.memorymapper

import fr.irisa.cairn.jnimap.isl.ISLMap
import alpha.model.Variable

interface MemoryMapper {
	def ISLMap getMemoryMap(Variable variable)
}