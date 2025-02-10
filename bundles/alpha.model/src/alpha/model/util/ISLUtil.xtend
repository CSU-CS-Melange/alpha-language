package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLAffList
import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMatrix
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLPoint
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.isl.ISLVal
import java.util.ArrayList
import java.util.List

import static extension alpha.model.matrix.MatrixOperations.scalarMultiplication
import static extension alpha.model.matrix.MatrixOperations.transpose
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

	/** Creates an ISLMap from a string */
	def static toISLMap(String descriptor) {
		ISLMap.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLAff from a string */
	def static toISLAff(String descriptor) {
		ISLAff.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLMultiAff from a string */
	def static toISLMultiAff(String descriptor) {
		ISLMultiAff.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLUnionMap from a string */
	def static toISLUnionMap(String descriptor) {
		ISLUnionMap.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLUnionSet from a string */
	def static toISLUnionSet(String descriptor) {
		ISLUnionSet.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Creates an ISLSchedule from a string */
	def static toISLSchedule(String descriptor) {
		ISLSchedule.buildFromString(ISLContext.instance, descriptor)
	}
	
	
	/** Creates an ISLConstraint from a string */
	def static toISLConstraint(String descriptor) {
		val set = ISLBasicSet.buildFromString(ISLContext.instance, descriptor)
		if (set.constraints.size != 1)
			throw new Exception('Cannot create an ISLConstraint from a set with multiple constraints')
		set.getConstraintAt(0)
	}
	
	/** Creates an ISLPWQPolynomial from a string */
	def static toISLPWQPolynomial(String descriptor) {
		ISLPWQPolynomial.buildFromString(ISLContext.instance, descriptor)
	}
	
	/** Transposes an ISLMatrix */
	def static transpose(ISLMatrix matrix) {
		ISLMatrix.buildFromLongMatrix(matrix.toLongMatrix.transpose)
	}
	
	/** Returns the integer point closest to the origin in set without parameter context */
	def static long[] integerPointClosestToOrigin(ISLBasicSet set) {
		set.copy.samplePoint.coordinates.subList(set.nbParams, set.nbParams + set.nbIndices)
	}
	
	/** Checks if this is a function from an empty domain to an empty range. */
	def static isNoneToNone(ISLMultiAff aff) {
		return (aff.nbInputs == 0) && (aff.nbOutputs == 0)
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
	
	def static isNonTrivial(ISLSet set) {
		!(set.isTrivial)
	}
	
	/** Returns true if c is effectively saturated per Theorem 1 in GR06, and false otherwise */
	def static boolean isEffectivelySaturated(ISLConstraint c, ISLBasicSet P) {
		if (c.isEquality) {
			return true
		}
		
		// get the maximum constant term among all constraints in P
		val tau = P.getConstraints
				.map[constant]
				.map[v | v<0 ? -1*v : v]
				.reduce[v1, v2 | v1 + v2]
				.intValue
		
		// construct the effective inverse of c per bullet 1 of Theorem 1 in GR06
		val cPrime = c.aff.negate
			.setConstant(c.constant.intValue + tau)
			.toInequalityConstraint
			.toBasicSet
		
		return (cPrime.intersect(P.copy)).isEqual(P.copy)
	}
	
	/** Converts a constraint into an equality constraint with the same coefficients and constant. */
	def static toEqualityConstraint(ISLConstraint constraint) {
		val space = constraint.space
		var equality = ISLConstraint.buildEquality(space.copy)
		
		// Copy all of the coefficients.
		val dimTypes = #[ISLDimType.isl_dim_param, ISLDimType.isl_dim_in, ISLDimType.isl_dim_out, ISLDimType.isl_dim_div]
		for (dimType : dimTypes) {
			val count = space.dim(dimType)
			for (i : 0 ..< count) {
				val coeff = constraint.getCoefficientVal(dimType, i)
				equality = equality.setCoefficient(dimType, i, coeff)
			}
		}

		// Copy the constsant.
		equality = equality.setConstant(constraint.constant)
		
		return equality
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
		
		val nonZeros = vec.reject[v|v==0]
		if (nonZeros.size >0 && nonZeros.toList.get(0) < 0) {
			return vec.scalarMultiplication(-1)
		}
		
		return vec
	}
	
	/**
	 * Determines the number of effective dimensions for the set.
	 * For example, if the set represents a 2D object embedded in 3D space,
	 * this will indicate that the set is 2D.
	 */
	def static int dimensionality(ISLSet set) {
		set.copy.computeDivs.basicSets.map[dimensionality].max
	}
	
	def static int dimensionality(ISLBasicSet set) {
		// The empty set has no effectively saturated constraints
		// but can have any number of indices,
		// which would cause this computation to fail.
		if (set.isEmpty) {
			return 0
		}
		
		val effectivelySaturatedCount =
			set.constraints
			.filter[c | c.involvesDims(ISLDimType.isl_dim_out, 0, set.space.nbOutputs)]
			.filter[c | c.isEffectivelySaturated(set)]
			.map[aff.toLinearUnitVector.toString]
			.toSet
			.size
		
		return set.nbIndices - effectivelySaturatedCount
	}
	
	/**
	 * Returns the ISLBasicSet characterizing the null space of the multiAff
	 */
	def static ISLSet nullSpace(ISLMultiAff maff) {
		maff.affs.fold(
			ISLBasicSet.buildUniverse(maff.space.domain.copy),
			[ret, c | ret.addConstraint(c.toEqualityConstraint)]
		).toSet
	}
	
	/**
	 * Returns true if the set is a lower dimensional polyhedron embedded in a higher
	 * dimension space, or false otherwise
	 */
	def static boolean isEmbedding(ISLSet set) {
		val nbIndices = set.dim(ISLDimType.isl_dim_set)
		set.dimensionality < nbIndices
	}
	
	/**
	 * Returns the linearly independent basis vectors of the (non-parametric) subspace in which a set lies
	 * Basis vectors are given as ISLPoints
	 */
	def static List<ISLPoint> getBasisVectors(ISLSet set) {
		var vectors = new ArrayList<ISLPoint>()
		var ISLSet workingSet = set.copy.affineHull.toSet
		val dim = workingSet.dimensionality
		for(var i = 0; i < dim; i++) {
			val ISLPoint basisVector = workingSet.copy.getLexNextMap(set.dim(ISLDimType.isl_dim_out)).deltas.samplePoint
			vectors += basisVector
			workingSet = workingSet.intersect(basisVector.copy.getOrthogonalPlane)
		}
		return vectors
	}
	
	/**
	 * Returns the ISLBasicSet that is the subspace spanned by a list of vectors
	 * Vectors do not necessarily need to be linearly independent
	 * but should be zero in the parameters
	 */
	def static ISLSet getSpan(Iterable<ISLPoint> basisVectors) {
		val ISLSet basisSet = basisVectors.map[a | a.toSet].reduce[a, b | a.union(b)]
		val zeroVector = ISLSet.buildUniverse(basisSet.getSpace.copy).samplePoint
		return basisSet.union(zeroVector.toSet).affineHull.toSet
	}
	
	/**
	 * Gets the n-1 dimensional plane (non-parametrically) orthogonal to a vector
	 */
	def static ISLSet getOrthogonalPlane(ISLPoint vector) {
		val localSpace = vector.getSpace.copy.toLocalSpace
	 	var projectAff = ISLAff.buildZero(localSpace.copy)
	 	for(var i = 0; i < localSpace.dim(ISLDimType.isl_dim_out); i++) {
	 		projectAff = projectAff.add(
	 			ISLAff.buildVarOnDomain(localSpace.copy, ISLDimType.isl_dim_out, i).scale(
	 				vector.getCoordinateVal(ISLDimType.isl_dim_out, i)
	 			)
	 		)
	 	}
	 	
		val ISLConstraint constraint = projectAff.toEqualityConstraint
	 	return ISLSet.buildUniverse(vector.getSpace.copy).addConstraint(constraint)
	}
	
	/**
	 * Builds a maff that computes the vector projection along a vector
	 */
	def static ISLMultiAff buildProjectionMaff(ISLPoint vector) {
		val localSpace = vector.getSpace.copy.toLocalSpace
		var rejectAffList = new ArrayList<ISLAff>
	 	
	 	for(var i = 0; i < localSpace.dim(ISLDimType.isl_dim_out); i++) {
	 		var rejectAff = ISLAff.buildZero(localSpace.copy)
	 		
		 	for(var j = 0; j < localSpace.dim(ISLDimType.isl_dim_out); j++) {
		 		rejectAff = rejectAff.add(
		 			ISLAff.buildVarOnDomain(localSpace.copy, ISLDimType.isl_dim_out, j).scale(
		 				vector.getCoordinateVal(ISLDimType.isl_dim_out, j).copy
		 			)
		 		)
		 	}
		 	
		 	rejectAff = rejectAff.scale(vector.getCoordinateVal(ISLDimType.isl_dim_out, i).copy)
		 	rejectAffList += rejectAff
	 	}
	 	
	 	return rejectAffList.convertToMultiAff
	}
	
	/**
	 * Builds a maff that computes the vector rejection along a vector
	 * The vector rejection is the projection onto a plane orthogonal to the
	 * given vector.
	 */
	def static ISLMultiAff buildRejectionMaff(ISLPoint vector) {
		return ISLSet.buildUniverse(vector.getSpace.copy).identity.toMultiAff
			.sub(vector.buildProjectionMaff)
	}
	
	/**
	 * Returns a MultiAff that translates points along the given vector
	 */
	def static ISLMultiAff buildTranslationMaff(ISLPoint vector) {
		ISLMap.buildFromDomainAndRange(
			ISLSet.buildUniverse(vector.getSpace.copy),
			vector.copy.toSet
		).toMultiAff.add(
			ISLSet.buildUniverse(vector.getSpace.copy).identity.toMultiAff
		)
	}
	
	
	/*************************************** 
	 *	         Conversion Methods        * 
	 ***************************************/
	 
	
	/**
	 * Generates a union map out of a list of ISLMaps
	 */
	def static ISLUnionMap convertToUnionMap(List<ISLMap> maps) {
		var ISLUnionMap unionMap
		for(map : maps) {
			unionMap = unionMap === null ? map.toUnionMap : unionMap.addMap(map)
		}
		unionMap
	}
	
	/**
	 * Converts an ISLMap to an ISLMultiAffine map as there is no default way
	 */
	def static ISLMultiAff toMultiAff(ISLMap map) {
		val local = map.copy
		val pma = local.toPWMultiAff
		val piece = pma.getPiece(0)
		piece.maff
	}
	
	/**
	 * Generates a MultiAff out of a list of ISLAffs 
	 */
	def static ISLMultiAff convertToMultiAff(List<ISLAff> affs) {
		var ISLAffList affList = ISLAffList.build(ISLContext.getInstance, 0)
		var space = affs.get(0).getSpace.copy.addDims(
			ISLDimType.isl_dim_out,
			affs.size()-1
		)

		for(aff : affs) {affList = affList.add(aff)}

		ISLMultiAff.buildFromAffList(space, affList)
	}
	
	/*************************************** 
	 *	  Lexical Equality Set Methods     * 
	 ***************************************/
	 
	 
}