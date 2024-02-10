package alpha.model.util

import org.eclipse.xtend.lib.annotations.Data
import fr.irisa.cairn.jnimap.isl.ISLMatrix
import java.util.ArrayList
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLDimType

import static extension alpha.model.util.ISLUtil.transpose
import static extension fr.irisa.cairn.jnimap.isl.ISLMatrix.buildFromLongMatrix
import static extension alpha.model.util.AlphaUtil.renameSpaceInputs
import static extension alpha.model.util.AlphaUtil.renameSpaceOutputs
import static extension alpha.model.util.AlphaUtil.renameSpaceParams
import static extension alpha.model.util.DomainOperations.toISLInequalityMatrix
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLAff

/**
 * Contains useful information about an <code>ISLBasicSet</code>.
 * Intended for use with the <code>FaceLattice</code> class.
 * Allows for inequalities within the set to be saturated,
 * and can check whether two sets have a child/parent relationship
 * based on which inequalities have been saturated.
 * 
 * Usage:
 * <code>new(basicSet)</code>:
 *     Get the information of the given set
 *     without saturating any of the inequalities.
 * 
 * <code>new(basicSet, saturatedInequalityIndices)</code>:
 *     Get the information of the given set
 *     after saturating the specified inequalities.
 * 
 * <code>SetInfo.createFace(ancestor, toSaturate)</code>:
 *     Get the information of the set created
 *     by saturating the indicated inequalities in the ancestor,
 *     keeping any previously saturated inequalities as saturated.
 */
@Data
class Facet {
	////////////////////////////////////////////////////////////
	// Fields and Properties
	////////////////////////////////////////////////////////////

	/** The dimensionality of the set. */
	int dimensionality
	
	/** The equality constraints defining the set. */
	ISLMatrix equalities
	
	/** The number of index variables in the set's space. */
	int indexCount
	
	/** The inequality constraints which involve at least one index variable. */
	ISLMatrix indexInequalities
	
	/** The number of index inequalities. */
	int indexInequalityCount
	
	/** Whether or not the set is bounded, or unbounded. */
	boolean isBounded
	
	/** Whether or not the set is empty, or contains at least one point. */
	boolean isEmpty
	
	/** The number of equality constraints which involve only parameter variables. */
	int parameterEqualityCount
	
	/** The inequality constraints which only involve parameter variables. */
	ISLMatrix parameterInequalities
	
	/** The indices of the inequalities which were saturated to form this set. */
	ArrayList<Integer> saturatedInequalityIndices
	
	/** The space that the set resides in. */
	ISLSpace space
	
	FaceLattice lattice
	
	////////////////////////////////////////////////////////////
	// Construction
	////////////////////////////////////////////////////////////
	
	/** Extract the information from the given set, assuming that no constraints were saturated to form it. */
	new(ISLBasicSet basicSet, FaceLattice lattice) { this(basicSet, new ArrayList<Integer>(0), lattice) }
	
	/** Extract the information from the given set. */
	new(ISLBasicSet basicSet, ArrayList<Integer> saturatedInequalityIndices, FaceLattice lattice) {
		equalities = DomainOperations.toISLEqualityMatrix(basicSet)
		indexCount = basicSet.dim(ISLDimType.isl_dim_set)
		indexInequalities = getInequalities(basicSet, indexCount, true)
		indexInequalityCount = indexInequalities.nbRows
		isBounded = basicSet.bounded
		isEmpty = basicSet.empty
		parameterEqualityCount = countParameterConstraints(equalities, indexCount)
		parameterInequalities = getInequalities(basicSet, indexCount, false)
		this.saturatedInequalityIndices = saturatedInequalityIndices
		space = basicSet.space 
		dimensionality = dimensionality(equalities, indexCount)
		this.lattice = lattice
	}
	
	/** Saturates the given index inequalities from the ancestor to form a potential face. */
	def static createFace(Facet ancestor, ArrayList<Integer> toSaturate) {
		// Create the new equalities matrix by taking the ancestor's index inequality constraints,
		// dropping any which are not to be saturated (leaving only the ones to saturate),
		// then combining it with the ancestor's equality constraints.
		val saturatedInequalities =
			(ancestor.indexInequalityCount >.. 0)
			.reject[row | toSaturate.contains(row)]
			.fold(ancestor.indexInequalities.copy, [mat, row | mat.dropRows(row, 1)])
		val equalities = ancestor.equalities.copy.concat(saturatedInequalities)
		
		// Create the new inequalities matrix by taking the acestor's index inequality constraints
		// and dropping any which were saturated, leaving only the non-saturated ones,
		// then combining it with the ancestor's parameter inequality constraints.
		val indexInequalities =
			(ancestor.indexInequalities.nbRows >.. 0)
			.filter[row | toSaturate.contains(row)]
			.fold(ancestor.indexInequalities.copy, [mat, row | mat.dropRows(row, 1)])
		val inequalities = indexInequalities.concat(ancestor.parameterInequalities.copy)
		
		// Construct a basic set from these matrices, plus info from the ancestor,
		// and use that to extract the desired information.
		val basicSet = ISLBasicSet.fromConstraintMatrices(
				ancestor.space.copy, equalities, inequalities,
				ISLDimType.isl_dim_param, ISLDimType.isl_dim_set,
				ISLDimType.isl_dim_div, ISLDimType.isl_dim_cst)
			.removeRedundancies
		return new Facet(basicSet, toSaturate, ancestor.lattice)
	}
	
	
	////////////////////////////////////////////////////////////
	// Public Methods
	////////////////////////////////////////////////////////////
	
	/** Returns <code>true</code> if this set is a child face of the given set, and <code>false</code> otherwise. */
	def isChildOf(Facet other) {
		// This set must saturate exactly one more constraint than the other set.
		if (saturatedInequalityIndices.size != 1 + other.saturatedInequalityIndices.size) {
			return false
		}
		
		// All constraints saturated by the other set must also be saturated in this set.
		if (!other.saturatedInequalityIndices.forall[saturatedInequalityIndices.contains(it)]) {
			return false
		}
		
		// There must only be exactly one constraint in this set which is not saturated by the other set.
		val notSaturatedCount = saturatedInequalityIndices.reject[other.saturatedInequalityIndices.contains(it)].size
		if (notSaturatedCount != 1) {
			return false
		}
		
		return true
	}
	
	/** Returns <code>true</code> if this set is a valid face of the given set, and <code>false</code> otherwise. */
	def isValidFace(Facet startingSetInfo) {
		// Faces cannot be empty.
		if (isEmpty) {
			return false
		}

		// Faces cannot have more equality constraints involving only parameters than the starting set.
		if (parameterEqualityCount > startingSetInfo.parameterEqualityCount) {
			return false
		}
					
		return true
	}
	
	/** Creates a basic set from this information. */
	def toBasicSet() {
		val allInequalities = indexInequalities.copy.concat(parameterInequalities.copy)
		return ISLBasicSet.fromConstraintMatrices(
				space.copy, equalities.copy, allInequalities,
				ISLDimType.isl_dim_param, ISLDimType.isl_dim_set,
				ISLDimType.isl_dim_div, ISLDimType.isl_dim_cst)
			.removeRedundancies
	}
	
	/** Creates the potentially unbounded linear space of this facet 
	 *  from the union of saturated constraints */
	def toLinearSpace() {
		val ineqMatrix = ISLBasicSet.buildUniverse(space.copy).toISLInequalityMatrix
		return ISLBasicSet.fromConstraintMatrices(
				space.copy, equalities.copy, ineqMatrix,
				ISLDimType.isl_dim_param, ISLDimType.isl_dim_set,
				ISLDimType.isl_dim_div, ISLDimType.isl_dim_cst)
			.removeRedundancies
	}
	
	/** Returns the normal vector (ISLAff) of the inequality characterizing the facet in its parent. */
	def getNormalVector(Facet parent) {
		val vecSpace = ISLSpace.alloc(space.context, space.nbParams, space.nbIndices, 1)
		                     .renameSpaceInputs(parent.space.indexNames)
		                     .renameSpaceOutputs
		                     .renameSpaceParams(parent.space.paramNames)
		val inequality = getCharacteristicInequality(parent)
		
		val normalVector = AffineFactorizer.toExpression(inequality.transpose, vecSpace).getAff(0)
		
		// get the component of this vector in the parents's domain
		// This may not be 100% correct yet
		val vector = normalVector.copy
		val ineq = vector.copy.toInequalityConstraint.toBasicSet
		val lp = parent.toLinearSpace
		val matrix = ineq.copy.intersect(lp.copy).toISLInequalityMatrix
		val componentInParent = AffineFactorizer.toExpression(matrix.transpose, vecSpace)
		                                        .getAff(0)
		                                        .dropDims(ISLDimType.isl_dim_param, 0, space.nbParams)
		                                        .setConstant(0)
//		println(normalVector)
//		println(componentInParent)
//		println
		// vectors should have no param dims or constant values
		return componentInParent
	}
	
	/** Returns the row index of the inequality characterizing the facet in its parent.
	 *  If more than one inequality is saturated relative to its parent then the first
	 *  one is returned.
	 * */
	def getCharacteristicInequalityIndex(Facet parent) {
		val indices = saturatedInequalityIndices.toSet
		indices.removeAll(parent.saturatedInequalityIndices.toSet)
		val ineqIndex = indices.toList.get(0)
		return ineqIndex
	}
	
	def getCharacteristicInequality(Facet parent) {
		val idx = getCharacteristicInequalityIndex(parent)
		val row = lattice.rootInfo.indexInequalities.toLongMatrix.get(idx)
		val matrix = #[row].buildFromLongMatrix
		return matrix
	}
	
	/** Returns a string indicating which inequalities were saturated to form this face. */
	override toString() { return "{" + saturatedInequalityIndices.join(',') + "}" }
	
	
	////////////////////////////////////////////////////////////
	// Private Methods
	////////////////////////////////////////////////////////////
	
	/** Checks if the row of a constraint matrix has a non-zero coefficient for at least one index dimension. */
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
	
	/** Counts the number of constraints which involve only parameter variables. */
	def private static countParameterConstraints(ISLMatrix matrix, int indexCount) {
		return (0 ..< matrix.nbRows)
			.reject[row | constraintInvolvesIndex(matrix, row, indexCount)]
			.size
	}
	
	/** Returns the dimensionality of a set using the equality constraints and number of index variables. */
	def private static dimensionality(ISLMatrix equalities, int indexCount) {
		// The dimensionality of a set is defined as the number of index variables
		// minus the number of linearly independent equality constraints which involve
		// at least one index variable (the rank of said matrix).
		
		// Take the set of equality constraints,
		// drop any rows which do not involve at least one constraint,
		// then compute the rank of the remaining matrix.
		val linearlyIndependentIndexEqualities =
			(equalities.nbRows >.. 0)
			.reject[row | constraintInvolvesIndex(equalities, row, indexCount)]
			.fold(equalities.copy(), [mat, row | mat.dropRows(row, 1)])
			.rank

		return indexCount - linearlyIndependentIndexEqualities
	}
	
	/**
	 * Gets a matrix of inequality constraints from a set.
	 * Retrieves either only the constraints which involve at least one index variable,
	 * or only the constraints which only involve parameter variables.
	 */
	def private static getInequalities(ISLBasicSet startingSet, int indexCount, boolean getIndexConstraints) {
		// Start with a list of all rows of the inequality matrix.
		// This must be in reverse order so dropping rows doesn't affect how they are numbered. 
		// Skip any rows that we want to keep.
		// For example, if we want to keep the index rows, skip it if the row contains an index.
		// Finally, starting with the original matrix, drop all necessary rows.
		val inequalities = DomainOperations.toISLInequalityMatrix(startingSet)
		return (inequalities.nbRows >.. 0)
			.filter[row | getIndexConstraints != constraintInvolvesIndex(inequalities, row, indexCount)]
			.toList
			.fold(inequalities, [mat, row | mat.dropRows(row, 1)])
	}
}