package alpha.model.memorymapper

import alpha.model.memorymapper.MemoryMapper
import alpha.model.Variable

class IdentityMemoryMapper implements MemoryMapper {
	
	override getMemoryMap(Variable variable) {
		variable.domain.copy.identity
	}
	
	override getDestination(Variable variable) {
		variable.name
	}
	
}