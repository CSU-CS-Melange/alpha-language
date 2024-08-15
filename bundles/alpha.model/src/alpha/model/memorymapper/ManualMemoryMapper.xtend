package alpha.model.memorymapper

import alpha.model.memorymapper.MemoryMapper
import alpha.model.Variable
import java.util.Map
import fr.irisa.cairn.jnimap.isl.ISLMap
import java.util.HashMap

import static alpha.model.util.ISLUtil.*


class ManualMemoryMapper implements MemoryMapper {
	
	Map<String, MemoryMap> maps
	
	new(Map<String, String> maps, Map<String, String> destinations) {
		this.maps = new HashMap()
		maps.forEach[ name, map | this.maps.put(name, new MemoryMap(destinations.get(name), toISLMap(map)))]
	}
	
	override getMemoryMap(Variable variable) {
		if(this.maps.get(variable.name) !== null){
			this.maps.get(variable.name).map.copy
		} else {
			variable.domain.copy.identity
		}
	}
	
	override getDestination(Variable variable) {
		if(this.maps.get(variable.name) !== null){
			this.maps.get(variable.name).destination
		} else {
			null
		}
	}
	
}