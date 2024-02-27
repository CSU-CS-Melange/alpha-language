package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.ArrayList
import java.util.HashMap
import org.eclipse.xtend.lib.annotations.Data

import static extension alpha.model.util.CommonExtensions.splitBy
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.CommonExtensions.toIndexHashMap
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension alpha.model.util.ISLUtil.isEffectivelySaturated
import static extension alpha.model.util.ISLUtil.toEqualityConstraint

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
	val FaceLattice2 lattice
	
	
	////////////////////////////////////////////////////////////
	// Construction
	////////////////////////////////////////////////////////////
	
	/** Constructs a new face which has no parents. */
	new(ISLBasicSet basicSet) {
		this(basicSet, null)
	}
	
	/** Creates a face to use as the root of a face lattice. */
	new(ISLBasicSet basicSet, FaceLattice2 lattice) {
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
		FaceLattice2 lattice
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
	
	static def removeDuplicates(Face... faces) {
		// Duplicates are removed by mapping each child face by their string representation,
		// which is a list of the constraints which were saturated to make that face.
		// If there is a duplicate, they will have the same string representation,
		// and the "toMap" function will only keep one of them (doesn't matter which).
		return faces.toMap[face | face.toString].values.toArrayList
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
}