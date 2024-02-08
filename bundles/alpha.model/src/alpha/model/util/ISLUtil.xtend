package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMatrix
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.matrix.MatrixOperations.transpose

class ISLUtil {
	
	/** Creates an ISLBasicSet from a string */
	def static toISLBasicSet(String descriptor) {
		ISLBasicSet.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLSet from a string */
	def static toISLSet(String descriptor) {
		ISLSet.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLBasicMap from a string */
	def static toISLBasicMap(String descriptor) {
		ISLBasicMap.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLBasicSet from a string */
	def static toISLAff(String descriptor) {
		ISLAff.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Transposes an ISLMatrix */
	def static transpose(ISLMatrix matrix) {
		ISLMatrix.buildFromLongMatrix(matrix.toLongMatrix.transpose)
	}
	
	/** Returns the integer point closest to the origin in set without parameter context */
	def static long[] integerPointClosestToOrigin(ISLBasicSet set) {
		set.copy.samplePoint.coordinates.subList(set.nbParams, set.nbParams + set.nbIndices)
	}
	
	def static isTrivial(ISLBasicSet set) {
		var origin = ISLBasicSet.buildUniverse(set.space.copy)
		for (i : 0..<set.space.nbIndices) {
			val aff = ISLAff.buildZero(set.space.copy.toLocalSpace)
			origin = origin.addConstraint(aff.setCoefficient(ISLDimType.isl_dim_in, i, 1).toEqualityConstraint)
		}
		
		set.copy.toSet.subtract(origin.toSet).isEmpty
	}
	
	def static isTrivial(ISLSet set) {
		var origin = ISLSet.buildUniverse(set.space.copy)
		for (i : 0..<set.space.nbIndices) {
			val aff = ISLAff.buildZero(set.space.copy.toLocalSpace)
			origin = origin.addConstraint(aff.setCoefficient(ISLDimType.isl_dim_in, i, 1).toEqualityConstraint)
		}
		
		set.subtract(origin).isEmpty
	}
	
}