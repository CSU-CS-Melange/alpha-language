package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.ArrayList
import java.util.HashMap
import org.eclipse.xtend.lib.annotations.Data

import static extension alpha.model.util.AlphaUtil.renameDims
import static extension alpha.model.util.CommonExtensions.permutations
import static extension alpha.model.util.CommonExtensions.splitBy
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.CommonExtensions.toIndexHashMap
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension alpha.model.util.ISLUtil.isEffectivelySaturated
import static extension alpha.model.util.ISLUtil.nullSpace
import static extension alpha.model.util.ISLUtil.toEqualityConstraint
import fr.irisa.cairn.jnimap.isl.ISLSet

/**
 * Represents a face which can be used to construct a face lattice.
 * 
 * Intended Construction:
 * <code>new(basicSet)</code>:
 * 		Construct a face from a basic set outside the context of a lattice.
 * 
 * <code>new(basicSet, lattice)</code>:
 * 		Construct a face from a basic set in the context of a lattice.
 * 
 * <code>new(face)</code>:
 * 		Copy constructor. Creates only shallow copies of internal lists and maps.
 */
@Data
class Face {
	/** The way each facet can be labeled by one particular choice of reuse. */
	enum Label {
		POS,
		NEG,
		ZERO
	}
	
	enum Boundary {
		WEAK,
		STRONG,
		NON
	}
	
	////////////////////////////////////////////////////////////
	// Fields and Properties
	////////////////////////////////////////////////////////////
	
	/** A map from a standardized constraint index to each unsaturated constraint. */
	val HashMap<Integer, ISLConstraint> unsaturatedConstraints
	
	/** A list of the constraints which are considered to be saturated */
	val ArrayList<ISLConstraint> saturatedConstraints
	
	/** The space in which this face resides. */
	val ISLSpace space
	
	/** The number of unsaturated constraints that the original (root) face started with. */
	val int originalConstraintCount
	
	/** The lattice that this face is in, if one exists. May be null. */
	val FaceLattice lattice
	
	
	////////////////////////////////////////////////////////////
	// Construction
	////////////////////////////////////////////////////////////
	
	/** Constructs a new face which has no parents. */
	new(ISLBasicSet basicSet) {
		this(basicSet, null)
	}
	
	/** Creates a face to use as the root of a face lattice. */
	new(ISLBasicSet basicSet, FaceLattice lattice) {
		val cleanBasicSet = basicSet.copy.removeRedundancies
		val separatedConstraints = cleanBasicSet.constraints.splitBy[c | c.isConsideredSaturated(cleanBasicSet)]
		
		this.saturatedConstraints = separatedConstraints.key
		this.unsaturatedConstraints = separatedConstraints.value.toIndexHashMap
		this.space = cleanBasicSet.space
		this.originalConstraintCount = unsaturatedConstraints.size
		this.lattice = lattice
	}
	
	/** Copy constructor. Makes a shallow copy of all internal lists. */
	new(Face other) {
		this( 
			new HashMap<Integer, ISLConstraint>(other.unsaturatedConstraints),
			new ArrayList<ISLConstraint>(other.saturatedConstraints),
			other.space,
			other.originalConstraintCount,
			other.lattice
		)
	}
	
	/** Constructor for all fields. */
	new(
		HashMap<Integer, ISLConstraint> unsaturatedConstraints,
		ArrayList<ISLConstraint> saturatedConstraints,
		ISLSpace space,
		int originalConstraintCount,
		FaceLattice lattice
	) {
		this.unsaturatedConstraints = unsaturatedConstraints
		this.saturatedConstraints = saturatedConstraints
		this.space = space
		this.originalConstraintCount = originalConstraintCount
		this.lattice = lattice
	}
	
	
	////////////////////////////////////////////////////////////
	// Public Methods
	////////////////////////////////////////////////////////////
	
	/** Enumerates the set of all possible label combinations. */
	static def enumerateAllPossibleLabelings(int nbFacets, boolean includeNeg) {
		val labels = includeNeg ? #[Label.ZERO, Label.POS, Label.NEG] : #[Label.POS, Label.ZERO]
		return labels.permutations(nbFacets).filter[isValid]
	}
	
	/** Returns True if labeling contains at least one POS and NEG label, or false otherwise */
	static def isValid(Label... labeling) {
		return labeling.exists[it == Label.POS] && labeling.exists[it == Label.NEG]
	}
	
	/**
	 * Generates the list of direct children to this face.
	 * Children are defined as having one fewer dimension than this face.
	 */
	def generateChildren() {
		// Find the set of children by saturating one additional inequality,
		// removing any where the dimensionality is reduced by more than one,
		// and removing all duplicates.
		val currentDimension = dimensionality
		return unsaturatedConstraints.keySet
			.map[saturateConstraint]
			.filter[face | face.dimensionality == currentDimension - 1]
			.removeDuplicates
	}
	
	/** Gets the dimensionality of this face. */
	def getDimensionality() {
		return toBasicSet.dimensionality
	}
	
	/** 
	 * Returns the domain D such that any vector within induces a particular labeling among the facets.
	 * Here, the word "face" refers to a node in the lattice and "facets" (with a 't') as the direct
	 * children of that particular "face".
	 * face:
	 * - facet1
	 * - facet2
	 * - facet3
	 * ...
	 * There are 3 possible labels for a facet: POS,NEG,ZERO.
	 * Each facet is said to be either an POS-facet, an NEG-facet, or an ZERO-facet.
	 * 
	 * By definition, the linear space of each facet differs from its face by a single inequality constraint
	 * "c" (ISLConstraint). The index coefficients of "c" represent the normal vector "v" (ISLAff) to the facet.
	 * 
	 * A particular facet can be made an:
	 * - POS-facet:  new constraint with coefficients of "v" that >0
	 * - NEG-facet:  new constraint with coefficients of "v" that <0
	 * - ZERO-facet: new constraint with coefficients of "v" that =0
	 */
	def getLabelingDomain(Label... labeling) {
		val facets = generateChildren
		if (facets.size != labeling.size) {
			throw new IllegalArgumentException("Must specify a label for every facet to get a labeling domain.")
		}
		
		// For each child facet, build the constraint that induces its desired labeling.
		// Then, take the linear space of this face, add all those constraints,
		// drop any constraints involving parameters, and remove redundancies.
		val domain = facets
			.map[child | child.getNormalVector(this)]
			.zipWith(labeling)
			.map[pair | pair.key.toLabelInducingConstraint(pair.value)]
			.fold(toLinearSpace, [s, c | s.addConstraint(c)])
			.dropConstraintsInvolvingDims(ISLDimType.isl_dim_param, 0, space.nbParams)
			.removeRedundancies
			
		return labeling -> domain
	}
	
	def toLabelInducingConstraint(ISLAff vector, Label label) {
		// The normal vectors are "just vectors", they have no parameter or constant information
		// so that must be added back in here.
		val vectorInAffineSpace = vector
			.copy
			.addDims(ISLDimType.isl_dim_param, space.nbParams)
		    .renameDims(ISLDimType.isl_dim_param, space.paramNames)
		
		// POS- and NEG-constraints must be strictly greater than or less than zero so the constant
		// value for these must be specified as -1, since ISLAff.toInequalityConstraint creates
		// assumes a context of >=.
		switch label {
			case Label.POS  : vectorInAffineSpace.addConstant(-1).toInequalityConstraint
			case Label.NEG : vectorInAffineSpace.negate.addConstant(-1).toInequalityConstraint
			case Label.ZERO : vectorInAffineSpace.addConstant(0).toEqualityConstraint
			default : throw new Exception("Label " + label + " is not supported")
		}
	}
	
	/**
	 * Returns the normal vector (as a single affine expression)
	 * of the inequality characterizing this face in the context of its parent.
	 */
	def getNormalVector(Face parent) {
		// Get any inequality which is unsaturated in the parent, but saturated here.
		val characteristicInequalityIndex =
			parent.unsaturatedConstraints.keySet
			.reject[idx | this.unsaturatedConstraints.containsKey(idx)]
			.head
		val characteristicInequality = parent.unsaturatedConstraints.get(characteristicInequalityIndex)
		
		// Convert it to an affine expression, then drop any parameters and constants.
		return characteristicInequality
			.copy
			.aff
			.dropDims(ISLDimType.isl_dim_param, 0, space.nbParams)
			.setConstant(0)
	}
	
	/** Constructs a new face by saturating an additional constraint compared to this face. */
	def saturateConstraint(int idx) {
		if (!unsaturatedConstraints.containsKey(idx)) {
			throw new IllegalArgumentException("Cannot saturate a constraint which isn't unsaturated.")
		}
		
		// Make a copy of this face and have it move the desired constraint to be saturated.
		val other = new Face(this)
		other.moveConstraintToSaturated(idx)
		
		// If any other constraints became saturated, move those to the saturated list also.
		// This is done in two steps to avoid concurrent modification.
		val basicSet = other.toBasicSet
		val alsoSaturated = other.unsaturatedConstraints
			.filter[i, c | c.isEffectivelySaturated(basicSet)]
			.keySet
			.toArrayList
		alsoSaturated.forEach[i | other.moveConstraintToSaturated(i)]
			
		return other
	}
	
	/** Constructs the basic set which represents this face. */
	def toBasicSet() {
		val universe = ISLBasicSet.buildUniverse(space.copy)
		return #[unsaturatedConstraints.values, saturatedConstraints]
			.flatten
			.map[c | c.copy]
			.fold(universe, [s, c | s.addConstraint(c)])
			.removeRedundancies
	}
	
	/**
	 * Creates the (potentially unbounded) linear space of this facet
	 * from the union of saturated constraints.
	 */
	def toLinearSpace() {
		// The constant terms and parameter coefficients of each constraint must be "removed" by setting them to 0.
		val universe = ISLBasicSet.buildUniverse(space.copy)
		val nbParams = space.dim(ISLDimType.isl_dim_param)
		return saturatedConstraints
			.map[c | c.copy.setConstant(0)]
			.map[c | (0..<nbParams).map[i | c.copy.setCoefficient(ISLDimType.isl_dim_param, i, 0)]]
			.flatten
			.fold(universe, [s, c | s.addConstraint(c)])
	}
	
	/** Returns the boundary classification of the face. */
	def boundaryLabel(ISLSet accumulationSpace) {
		val lp = toLinearSpace.toSet
		
		val accDims = accumulationSpace.dimensionality
		val intDims = accumulationSpace.copy.intersect(lp).dimensionality
		
		if (intDims == accDims) {
			return Boundary.STRONG
		} else if (0 < intDims && intDims < accDims) {
			return Boundary.WEAK
		} else {
			return Boundary.NON
		}
	} 
	
	
	/** Returns a string indicating which constraints were saturated to form this face. */
	override toString() {
		val saturatedIndexes = (0 ..< originalConstraintCount)
			.reject[idx | unsaturatedConstraints.containsKey(idx)]
			.join(",")
		return "{" + saturatedIndexes + "}"
	}
	
	////////////////////////////////////////////////////////////
	// Local Methods
	////////////////////////////////////////////////////////////
	
	/** Determines whether a constraint is considered to be saturated for the given set. */
	protected static def isConsideredSaturated(ISLConstraint c, ISLBasicSet s) {
		return c.isEquality
			|| !c.involvesDims(ISLDimType.isl_dim_set, 0, s.nbIndices)
			|| c.isEffectivelySaturated(s)
	}
	
	/** Moves a constraint from the "unsaturated" list to the "saturated" list. */
	protected def moveConstraintToSaturated(int idx) {
		val equality = unsaturatedConstraints.remove(idx).toEqualityConstraint
		saturatedConstraints += equality
	}
	
	/** Removes duplicate faces from a list of faces. */
	protected static def removeDuplicates(Face... faces) {
		// Duplicates are removed by mapping each child face by their string representation,
		// which is a list of the constraints which were saturated to make that face.
		// If there is a duplicate, they will have the same string representation,
		// and the "toMap" function will only keep one of them (doesn't matter which).
		return faces.toMap[face | face.toString].values.toArrayList
	}
}
