package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLContext

class ISLUtil {
	
	/** Creates an ISLBasicSet from a string */
	def static toISLBasicSet(String descriptor) {
		ISLBasicSet.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLBasicMap from a string */
	def static toISLBasicMap(String descriptor) {
		ISLBasicMap.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLBasicSet from a string */
	def static toISLAff(String descriptor) {
		ISLAff.buildFromString(ISLContext.instance, descriptor)
	}
	
}