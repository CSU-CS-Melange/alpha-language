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
	 * less than that point.
	 * We will compute this by building the set of such points, then using the Barvinok
	 * library to compute the cardinality of such a set.
	 * This will return a piecewise quasi-affine polynomial.
	 * 
	 * Note: the polynomial returned may look like it's undefined for the lex min of the given set.
	 * However, this is not actually the case.
	 * These polynomials are defined over the universe, and will evaluate to zero
	 * if the point you're asking for lies outside the domains of each piece.
	 * In other words: there is an implicit "default" case that always evaluates to zero
	 * if the point being evaluated lies outside all the domains of all the pieces of the polynomial.
	 * 
	 * For example, the set "[N] -> {[i]: 0 <= i <= N}" (which forms a line from 0 to N)
	 * would return the polynomial "[N,i] -> { i : 0 < i <= N }".
	 * Per the previous paragraph, this is still valid for i=0,
	 * as it falls under the implicit extra case that always evaluates to zero.
	 * 
	 * The intended use case of this is for determining the index of a point 
	 * within a linearized array of our set. This produces a 0-based indexing.
	 * Per the second paragraph, it may look like this polynomial is not defined for index 0,
	 * but it actually is due to the implicit default case always evaluating to 0
	 * if the point you're evaluating at is not in any of the domains of the pieces.
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

		val ISLSet extendedSet = domain.copy
			.moveIndicesToParameters
			.addIndices(names)
			
		// If a point X is "lexicographically less than" the point Y,
		// if the first index at which they differ is index i, then X[i] < Y[i], and for all j<i, X[j] = Y[j].
		// Note: if X=Y, then X is not lexicographically less than Y.
		// To describe this as a set, we take the union of one set per index,
		// where each set encodes the above constraints for that particular index.
		// In this terminology, the point Y are encoded in the newly moved parameters
		// (the ones that used to be indices), and the point X is encoded in the newly introduced indices.
		val lessThan = (0 ..< domain.nbIndices)
			.map[i | createOrderingForIndex(extendedSet, domain.nbParams, i)]
			.reduce([d1, d2 | d1.union(d2)])
		
		// By intersecting the lexicographic ordering with our original domain,
		// we get the set of points in our domain that are lexicographically less than
		// our desired point, which is specified in the parameters.
		// The cardinality of this set is thus the rank of our desired point.
		return lessThan.intersect(domain.copy).card
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