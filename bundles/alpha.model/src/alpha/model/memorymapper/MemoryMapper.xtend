package alpha.model.memorymapper

import fr.irisa.cairn.jnimap.isl.ISLMap
import alpha.model.Variable

interface MemoryMapper {
	//Returns the ISL Map for a variable that 
	//will be used to generate the macro for array accesses
	def ISLMap getMemoryMap(Variable variable)
	
	// A function that returns what variable in memory 
	// the original variable will access
	// (for coalescing multiple variables into one memory
	// location)
	def String getDestination(Variable variable)
}