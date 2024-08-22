package alpha.model.memorymapper

import fr.irisa.cairn.jnimap.isl.ISLMap

class MemoryMap {
	val String destination
	val ISLMap map
	
	new(String dest, ISLMap map) {
		this.destination = dest
		this.map = map
	}
	
	def ISLMap getMap() {
		this.map	
	}
	
	def String getDestination() {
		this.destination
	}
}