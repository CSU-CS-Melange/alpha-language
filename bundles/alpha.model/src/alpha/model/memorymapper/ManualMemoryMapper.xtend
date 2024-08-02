package alpha.model.memorymapper

import alpha.model.memorymapper.MemoryMapper
import alpha.model.Variable
import java.util.Map
import fr.irisa.cairn.jnimap.isl.ISLMap
import java.util.HashMap

import static alpha.model.util.ISLUtil.*


class ManualMemoryMapper implements MemoryMapper {
	
	Map<String, ISLMap> maps
	
	new(Map<String, String> maps) {
		this.maps = new HashMap()
		maps.forEach[ name, map | this.maps.put(name, toISLMap(map))]
	}
	
	override getMemoryMap(Variable variable) {
		if(this.maps.get(variable.name) !== null){
			this.maps.get(variable.name).copy
		} else {
			variable.domain.copy.identity
		}
	}
}