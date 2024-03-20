package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMatrix
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.ArrayList
import org.eclipse.xtend.lib.annotations.Data

import static extension alpha.model.matrix.MatrixOperations.*
import static extension alpha.model.util.CommonExtensions.toHashMap
import static extension alpha.model.util.DomainOperations.toISLEqualityMatrix
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension fr.irisa.cairn.jnimap.isl.ISLMatrix.buildFromLongMatrix

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
	
	/** The pairs of inequality constraints that are effectively saturated */
	ISLMatrix thickEqualities
	
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
	
	
	////////////////////////////////////////////////////////////
	// Construction
	////////////////////////////////////////////////////////////
	
	/** Extract the information from the given set, assuming that no constraints were saturated to form it. */
	new(ISLBasicSet basicSet) { this(basicSet, new ArrayList<Integer>(0)) }
	
	/** Extract the information from the given set. */
	new(ISLBasicSet basicSet, ArrayList<Integer> saturatedInequalityIndices) {
		val basicSetNoRedundancies = basicSet.copy.removeRedundancies
		
		space = basicSetNoRedundancies.space 
		equalities = DomainOperations.toISLEqualityMatrix(basicSetNoRedundancies)
		indexCount = basicSetNoRedundancies.dim(ISLDimType.isl_dim_set)
		
		val allInequalities = getInequalities(basicSetNoRedundancies, indexCount, true)
		val inequalities = separateEffectivelySaturatedInequalities(allInequalities, space)
		indexInequalities = inequalities.key
		thickEqualities = inequalities.value
		
		if (thickEqualities.nbRows % 2 != 0) {
			throw new Exception('Failed to create lattice, there should be an even number of (or zero) effectively saturated constraints')
		}
		
		indexInequalityCount = indexInequalities.nbRows
		isBounded = basicSetNoRedundancies.bounded
		isEmpty = basicSetNoRedundancies.empty
		parameterEqualityCount = countParameterConstraints(equalities, indexCount)
		parameterInequalities = getInequalities(basicSetNoRedundancies, indexCount, false)
		this.saturatedInequalityIndices = saturatedInequalityIndices
		
		dimensionality = basicSet.dimensionality
	}
	
	/** 
	 * ISLMatrix buildFromLongMatrix throws an exception if the input matrix has no rows
	 */
	def static buildFromLongMatrixEvenIfEmpty(long[][] matrix, ISLSpace space) {
		if (matrix.length == 0) {
			return ISLBasicSet.buildUniverse(space.copy).toISLEqualityMatrix
		}
		return matrix.buildFromLongMatrix
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
		// then combining it with the ancestor's parameter inequality constraints and effectively
		// saturated inequality constraints.
		val indexInequalities =
			(ancestor.indexInequalities.nbRows >.. 0)
			.filter[row | toSaturate.contains(row)]
			.fold(ancestor.indexInequalities.copy, [mat, row | mat.dropRows(row, 1)])
		val inequalities = indexInequalities.concat(ancestor.parameterInequalities.copy)
		                                    .concat(ancestor.thickEqualities.copy)
		
		// Construct a basic set from these matrices, plus info from the ancestor,
		// and use that to extract the desired information.
		val basicSet = ISLBasicSet.fromConstraintMatrices(
				ancestor.space.copy, equalities, inequalities,
				ISLDimType.isl_dim_param, ISLDimType.isl_dim_set,
				ISLDimType.isl_dim_div, ISLDimType.isl_dim_cst)
			.removeRedundancies
		return new Facet(basicSet, toSaturate)
	}
	
	
	////////////////////////////////////////////////////////////
	// Public Methods
	////////////////////////////////////////////////////////////
	
	/** Returns <code>true</code> if this set is a child face of the given set, and <code>false</code> otherwise. */
	def isChildOf(Facet other) {
		// This set must saturate more constraints than the other set.
		if (saturatedInequalityIndices.size <= other.saturatedInequalityIndices.size) {
			return false
		}
		
		// All constraints saturated by the other set must also be saturated in this set.
		if (!other.saturatedInequalityIndices.forall[saturatedInequalityIndices.contains(it)]) {
			return false
		}
		
		// There must only at least one constraint in this set which is not saturated by the other set.
		val notSaturatedCount = saturatedInequalityIndices.reject[other.saturatedInequalityIndices.contains(it)].size
		if (notSaturatedCount < 1) {
			return false
		}
		
		return true
	}
	
	/**
	 * Returns <code>true</code> if this set has the same saturated inequalities as the other.
	 * Otherwise, returns <code>false</code>.
	 */
	def isDuplicateOf(Facet other) {
		return this.saturatedInequalityIndices.containsAll(other.saturatedInequalityIndices)
			&& other.saturatedInequalityIndices.containsAll(this.saturatedInequalityIndices)
	}
	
	/**
	 * Returns <code>true</code> if this set is of the same dimension as the other,
	 * and the inequalities saturated by this facet is a subset of the other.
	 * Otherwise, returns <code>false</code>.
	 */
	def isStrictSubsetOf(Facet other) {
		return this.dimensionality == other.dimensionality
			&& this.saturatedInequalityIndices.size < other.saturatedInequalityIndices.size
			&& other.saturatedInequalityIndices.containsAll(this.saturatedInequalityIndices)
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
		                                            .concat(thickEqualities.copy)
		return ISLBasicSet.fromConstraintMatrices(
				space.copy, equalities.copy, allInequalities,
				ISLDimType.isl_dim_param, ISLDimType.isl_dim_set,
				ISLDimType.isl_dim_div, ISLDimType.isl_dim_cst)
			.removeRedundancies
	}
	
	/** Returns <code>true</code> if the facet has at least one thick face, and <code>false</code> otherwise. */
	def hasThickFaces() {
		return thickEqualities.nbRows > 0
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
	
	/** Separates the effectively saturated inequalities from true unsaturated inequalities */
	def private static separateEffectivelySaturatedInequalities(ISLMatrix allInequalities, ISLSpace space) {
		val matrix = allInequalities.copy.dropCols(space.nbParams + space.nbIndices, 1).toLongMatrix

		// Use a hash map to get the index of a particular vector in matrix
		val idxMap = (0 ..< matrix.length)
			.toMap[rowIdx | matrix.get(rowIdx).toString]
			.mapValues[rowIdx | Long.valueOf(rowIdx)]
			.toHashMap
		
		// Identify the indices of any parallel constraints. For each vector in matrix, check
		// to see if its negation exists (i.e., is a key in idxMap). Label such vectors as 
		// having a colinear partner.
		val negMatrix = matrix.scalarMultiplication(-1)
		
		val colinearConstraints = (0 ..< matrix.length)
			.toInvertedMap[rowIdx | negMatrix.get(rowIdx).toString]
			.filter[rowIdx, rowStr | idxMap.containsKey(rowStr)]
			.toHashMap
			.keySet
		
		// Construct the matrix of all colinear inequalities. The GR06 paper defines pairs of
		// colinear inequalities as thick equalities (or thick faces).
		val longMatrix = allInequalities.toLongMatrix
		val thickEqualities = (0..<matrix.length)
			.filter[i | colinearConstraints.contains(i)]
			.map[i | longMatrix.get(i)]
			.buildFromLongMatrixEvenIfEmpty(space)
		
		// Construct the matrix of remaining true unsaturated inequalities
		val unsaturatedInequalities = (0..<matrix.length)
			.reject[i | colinearConstraints.contains(i)]
			.map[i | longMatrix.get(i)]
			.buildFromLongMatrixEvenIfEmpty(space)
		
		return unsaturatedInequalities -> thickEqualities 
	}
	
}