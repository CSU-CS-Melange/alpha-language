package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMatrix
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.matrix.MatrixOperations.transpose
import static extension alpha.model.matrix.MatrixOperations.scalarMultiplication
import static extension alpha.model.util.DomainOperations.*

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
	
	def static dimensionality(ISLSet set) {
		set.getBasicSets.map[bset | bset.dimensionality].reduce(d1,d2 | d1>d2 ? d1 : d2)
	}
	
	def static dimensionality(ISLBasicSet set) {
		val setNoRedundancies = set.copy.removeRedundancies
		val eqMatrix = setNoRedundancies.toISLEqualityMatrix
		
		// Take the set of equality constraints,
		// drop any rows which do not involve at least one index,
		// then compute the rank of the remaining matrix.
		val linearlyIndependentIndexEqualities =
			(eqMatrix.nbRows >.. 0)
			.reject[row | constraintInvolvesIndex(eqMatrix, row, set.nbIndices)]
			.fold(eqMatrix.copy(), [mat, row | mat.dropRows(row, 1)])
			.rank
	
		val ineqMatrix = setNoRedundancies.toISLInequalityMatrix
		                                  .dropCols(set.nbParams + set.nbIndices, 1)
		val ineqRows = (0..<ineqMatrix.nbRows).map[r | ineqMatrix.toLongMatrix.get(r)].toList
		var thickEqualities = 0 
		for (r : 0..<ineqMatrix.nbRows) {
			val row = ineqRows.get(r).scalarMultiplication(-1).toString
			val ineqRowsSet = (r+1..<ineqMatrix.nbRows).map[i | ineqRows.get(i).toString].toSet
			if (ineqRowsSet.contains(row))
				thickEqualities += 1
		}
		
		return set.nbIndices - linearlyIndependentIndexEqualities - thickEqualities
	}
	
	def private static constraintInvolvesIndex(ISLMatrix matrix, int row, int indexCount) {
		// The constraint matrix puts columns in the order: parameters, indexes, constant.
		// Thus, the exclusive ending index is the number of columns minus one (for the constant column).
		// Since we want to check each index, the first column to check is the end minus the number of indexes.
		val endExclusive = matrix.nbCols - 1
		val start = endExclusive - indexCount

		// The constraint at this row involves an index if any of the columns in that range
		// have a non-zero coefficient value.
		return (start ..< endExclusive).exists[col | matrix.getElement(row, col) != 0]
	}
	
}