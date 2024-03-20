package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMatrix
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.matrix.MatrixOperations.scalarMultiplication
import static extension alpha.model.matrix.MatrixOperations.transpose
import static extension alpha.model.util.DomainOperations.*

import static extension java.lang.Math.abs

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
		set.copy.toSet.isTrivial
	}
	
	def static isTrivial(ISLSet set) {
		val zero = ISLAff.buildZero(set.space.copy.toLocalSpace)
		val constraints =
			(0 ..< set.space.nbIndices)
			.map[i | zero.copy.setCoefficient(ISLDimType.isl_dim_in, i, 1).toEqualityConstraint]
		val origin = constraints.fold(ISLSet.buildUniverse(set.space.copy), [s, c | s.addConstraint(c)])
		
		set.copy.subtract(origin).isEmpty
	}
	
	/** Returns true if c is effectively saturated per Theorem 1 in GR06, and false otherwise */
	def static boolean isEffectivelySaturated(ISLConstraint c, ISLBasicSet P) {
		if (c.isEquality) {
			return true
		}
		
		// get the maximum constant term among all constraints in P
		val tau = P.getConstraints.map[constant].max.intValue
		
		// construct the effective inverse of c per bullet 1 of Theorem 1 in GR06
		val cPrime = c.aff.negate
			.setConstant(c.constant.intValue + tau)
			.toInequalityConstraint
			.toBasicSet
		
		return (cPrime.intersect(P.copy)).isEqual(P)
	}
	
	
	/** 
	 * Given the ISLAff of an effectively saturated constraint return a long[] of the linear part
	 * the first non-zero value is guaranteed to be positive
	 */
	def static long[] toLinearUnitVector(ISLAff aff) {
		val constantCol = aff.nbParams + aff.nbInputs
		val vec = aff.toEqualityConstraint.toBasicSet.toISLEqualityMatrix
			.dropCols(constantCol, 1)
			.toLongMatrix.get(0)
		
		if (vec.reject[v|v==0].toList.get(0) < 0) {
			return vec.scalarMultiplication(-1)
		}
		
		return vec
	}
	
	def static int dimensionality(ISLBasicSet set) {
		val effectivelySaturatedCount =
			set.constraints
			.filter[c | c.involvesDims(ISLDimType.isl_dim_out, 0, set.space.nbOutputs)]
			.filter[c | c.isEffectivelySaturated(set)]
			.map[aff.toLinearUnitVector.toString]
			.toSet
			.size
		
		return set.nbIndices - effectivelySaturatedCount
	}
}