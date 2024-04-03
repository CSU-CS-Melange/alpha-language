package alpha.codegen.util

import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.util.CommonExtensions.toArrayList

class MemoryUtils {
	/**
	 * Returns a piecewise quasi-affine polynomial indicating
	 * the number points in the given set (i.e., its cardinality).
	 * 
	 * @param domain The domain to get the cardinality of. This set is not destroyed ("isl_keep").
	 * @returns A piecewise quasi-affine polynomial that represents the cardinality of the given domain.
	 */
	def static ISLPWQPolynomial card(ISLSet domain) {
		BarvinokBindings.card(domain.copy)
	}
	
	/**
	 * Returns a piecewise quasi-affine polynomial which can compute the "rank" of a point.
	 * The "rank" is defined as the number of points which are lexicographically
	 * less than or equal to that point.
	 * 
	 * For example, the set "[N] -> {[i]: 0 <= i <= N}" (which forms a line from 0 to N)
	 * would return the polynomial "[N,i] -> { (1 + 1) : 0 <= i <= N }".
	 * 
	 * The intended use case of this is for determining the index of a point 
	 * within a linearized array of our set. This produces a 1-based indexing.
	 * Due to a limitation within ISL, the polynomial cannot be coerced into a 0-based indexing
	 * while guaranteeing correctness. If you need 1-based indexing (e.g., for printing C code),
	 * simply add a "-1" to the end of the polynomial *after* printing it out.
	 * Subtracting a polynomial that represents the constant 1 will cause the domain to be
	 * restricted such that the polynomial does not return 0.
	 * 
	 * @param domain The domain to get the ranking polynomial for. This set is not destroyed ("isl_keep").
	 * @returns A piecewise quasi-affine polynomial that can be used to compute the rank of a point within the given domain.
	 */
	def static ISLPWQPolynomial rank(ISLSet domain) {
		// At a high level, we will simply construct a set which represents
		// all points lexicographically smaller or equal to some desired point.
		// We intersect this with the original domain to get only the points we care about.
		// The cardinality of this set will be a polynomial which indicates the rank of our desired point.
		
		// To get the rank of our desired point, we need to add that point as a parameter.
		// We'll do this by simply moving the indices of our given domain to be parameters.
		// This also moves the constraints to be parameter constraints.
		// We then introduce new indices that represent the original ones.
		// We don't need to re-add the original constraints, as intersecting
		// this set with the original domain does this for us.

		// The introduced parameters will all be named like "i0", "i1", etc.
		// However, we don't want duplicate names, so we skip any that already exist.
		// Note: lazy evaluation means this won't actually generate all Integer.MAX_VALUE names.
		val existingNames = #[domain.paramNames, domain.indexNames].flatten.toArrayList
		val names = (0 ..< Integer.MAX_VALUE)
			.map["i" + it]
			.reject[existingNames.contains(it)]
			.take(domain.nbIndices)
			.toList
		val ISLSet orderingBase = domain.copy
			.moveIndicesToParameters
			.addIndices(names)
			
		// If a point X is "lexicographically less than or equal to" the point Y,
		// if the first index at which they differ is index i, then X[i] < Y[i].
		// This also means that, for all j<i, X[j] = Y[j].
		// Note: this definition also allows X=Y.
		// To describe this as a set, we need to take the union of several sets.
		// There is one set per index i where we encode X[i] < Y[i] and X[j] = Y[j] for all j<i,
		// plus one more set to encode X=Y.
		// Here, the points in Y are encoded in the newly moved parameters
		// (the ones that used to be indices), and the points in X are the newly introduced indices.
		// There may be some redundancies, but the Barvinok library will simplify them.
		val equalTo = (0 ..< orderingBase.nbIndices).fold(orderingBase.copy, [d, i | d.addTotalOrderEquality(domain.nbParams, i)])
		val lessThanEqualTo = (0 ..< orderingBase.nbIndices)
			.map[i | createOrderingForIndex(orderingBase, domain.nbParams, i)]
			.fold(equalTo, [d1, d2 | d1.union(d2)])
		
		// By intersecting the lexicographic ordering with our original domain,
		// we get the set of points in our domain that are lexicographically less than
		// or equal to our desired point, which is specified in the parameters.
		// The cardinality of this set is 
		return lessThanEqualTo.intersect(domain.copy).card
	}
	
	/**
	 * Constructs a set that's a copy of the given one, but with added constraints such that
	 * index i is less than the parameter for that index,
	 * and all indices j<i are equal to their parameter.
	 */
	def private static createOrderingForIndex(ISLSet domain, int originalParamCount, int index) {
		(0 ..< index)
			.fold(domain.copy, [d, i | d.addTotalOrderEquality(originalParamCount, i)])
			.addTotalOrderInequality(originalParamCount, index)
	}
	
	/** Constructs an equality constraint that index i equals the parameter for that index. */
	def private static addTotalOrderEquality(ISLSet domain, int originalParamCount, int index) {
		val constraint = ISLConstraint.buildEquality(domain.space)
			.setCoefficient(ISLDimType.isl_dim_param, originalParamCount + index, 1)
			.setCoefficient(ISLDimType.isl_dim_set, index, -1)
		
		return domain.addConstraint(constraint)
	}
	
	/** Constructs an inequality that index i is less than the parameter for that index. */
	def private static addTotalOrderInequality(ISLSet domain, int originalParamCount, int index) {
		val constraint = ISLConstraint.buildInequality(domain.space)
			.setCoefficient(ISLDimType.isl_dim_param, originalParamCount + index, 1)
			.setCoefficient(ISLDimType.isl_dim_set, index, -1)
			.setConstant(-1)
		
		return domain.addConstraint(constraint)
	}
}