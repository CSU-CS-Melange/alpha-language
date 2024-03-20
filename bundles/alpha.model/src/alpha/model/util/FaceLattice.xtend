package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import java.util.ArrayList
import java.util.Collection
import java.util.LinkedList
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Constructs the face lattice of a given <code>ISLBasicSet</code>.
 * Usage: to create the face lattice, call the static function: <code>FaceLattice.create(root)</code>.
 */
class FaceLattice {
	////////////////////////////////////////////////////////////
	// Fields and Properties
	////////////////////////////////////////////////////////////
	
	/** The information about the set which forms the root of the lattice. */
	@Accessors(PUBLIC_GETTER)
	val Facet rootInfo
	
	/**
	 * The storage of the lattice itself.
	 * The index of each layer (the outermost list) is the dimensionality.
	 * I.e., <code>lattice[2]</code> contains all the 2D faces of the lattice.
	 * Each layer is a list of all the sets which are in that layer.
	 */
	 @Accessors(PUBLIC_GETTER)
	val ArrayList<ArrayList<Facet>> lattice
	
	
	////////////////////////////////////////////////////////////
	// Construction
	////////////////////////////////////////////////////////////
	
	/** Constructs a new, empty lattice. */
	private new(Facet rootInfo) { 
		this.rootInfo = rootInfo
		lattice = new ArrayList<ArrayList<Facet>>
	}
	
	/** Creates a new face lattice for the given set. */
	def static create(ISLBasicSet root) {
		return create(root, true)
	}
	
	/**
	 * Creates a new face lattice for the given set.
	 * 
	 * @param   root        The set to use as the root of the lattice.  
	 * @param   fullLattice If <code>true</code>, generate the full lattice.
	 *                      Otherwise, only generate the partial lattice from saturating exactly one inequality.
	 * @returns An instance of the face lattice (or partial lattice) for the root.
	 */
	def static create(ISLBasicSet root, boolean fullLattice) {
		// Set up the face lattice which is rooted at the given set.
		val rootInfo = new Facet(root)
		val lattice = new FaceLattice(rootInfo)
		
		// The lattice will be populated by iterating through the power set of all constraints.
		// A queue of constraint combinations to check will be used to avoid recursion.
		// We will start with the top-level face which doesn't saturate any constraints (i.e., the root).
		val toSaturate = new LinkedList<ArrayList<Integer>>
		toSaturate.add(new ArrayList<Integer>)

		// While there are combinations of constraints left, try creating faces.
		while (!toSaturate.empty) {
			// Get the next set of constraints and try constructing a face from them.
			val currentConstraints = toSaturate.remove
			val isValidFace = lattice.checkAddFace(currentConstraints)
			
			// If the face was valid and the maximum number of constraints haven't been saturated,
			// queue up more sets of constraints to try saturating.
			// Skip this if we're only generating the partial lattice.
			val hasChildren = fullLattice && isValidFace && (currentConstraints.size < rootInfo.dimensionality)
			if (hasChildren) {
				// The next constraint sets are created by adding a single constraint to this set.
				// To avoid duplicating faces, only try saturating constraints whose index is
				// greater than the largest index of constraint already saturated.
				// Since the arrays are already sorted, we can just grab the last element and add 1.
				// If the current set of constraints is empty, any constraint is valid to saturate.
				val minimumIndex = currentConstraints.empty ? 0 : (currentConstraints.last + 1)
				for (constraint: minimumIndex ..< rootInfo.indexInequalityCount) {
					val nextSet = new ArrayList<Integer>(currentConstraints)
					nextSet.add(constraint)
					toSaturate.add(nextSet)
				}
			}
		}
		
		// There may be some redundant facets, so remove them.
		lattice.removeRedundancies
		
		return lattice
	}
	
	
	////////////////////////////////////////////////////////////
	// Public Methods
	////////////////////////////////////////////////////////////
	
	/** Gets the set of all children of the indicated face. */
	def getChildren(Facet face) {
		// If there are no layers below this face's layer,
		// or if this face's layer doesn't even exist,
		// it must not have any children.
		val faceLayer = Integer.max(0, face.dimensionality)
		if ((faceLayer == 0) || (faceLayer >= lattice.size)) {
			return new ArrayList<Facet>
		}
		
		// All children of the given face must be on the layer below it.
		return lattice.get(faceLayer - 1).filter[node | node.isChildOf(face)]
	}
	
	/** Returns the set of all faces, not organized by dimension. */
	def getAllFaces() {
		return lattice.flatten
	}
	
	/** Indicates whether or not the set used as the root of the lattice is simplicial (hyper-triangular) or not. */
	def isSimplicial() {
		// Empty and unbounded sets can never be null.
		if (rootInfo.isEmpty) {
			return false
		}
		if (!rootInfo.isBounded) {
			return false
		}
		
		// A set of dimensionality n is a simplex if and only if it has exactly n+1 vertices.
		// Note: dimensionality is calculated such that it indicates the fewest dimensions needed
		// to express the set. I.e., if the set is a 2D surface in 3D space, the dimensionality will be 2.
		val zeroFaces = lattice.get(0)
		if (zeroFaces === null) {
			return false
		}
		if (zeroFaces.size != rootInfo.dimensionality + 1) {
			return false
		}
		return true
	}
	
	/** Returns <code>true</code> if the root has at least one thick face, and <code>false</code> otherwise. */
	def hasThickFaces() {
		return rootInfo.thickEqualities.nbRows > 0
	}
	
	////////////////////////////////////////////////////////////
	// Private Methods
	////////////////////////////////////////////////////////////
	
	/**
	 * Checks if a face is valid to add to the lattice, and adds it if so.
	 * @returns Returns <code>true</code> if the face was valid, didn't exist already, and was added.
	 * 			Otherwise, returns <code>false</code>.
	 */
	def private checkAddFace(ArrayList<Integer> toSaturate) {
		// Create the proposed face by saturating the indicated inequality constraints,
		// and check whether or not this creates a valid face.
		val face = Facet.createFace(rootInfo, toSaturate)
		if (!face.isValidFace(rootInfo)) {
			return false
		}
		
		// Make sure there is a layer of the lattice for this new face.
		// Lattice layer indices match the dimensionality of the faces in that layer.
		// Be careful to avoid out of bounds accesses!
		val layerIndex = Integer.max(0, face.dimensionality)
		while (lattice.size <= layerIndex) {
			lattice.add(new ArrayList<Facet>)
		}
		val layer = lattice.get(layerIndex)
		
		// Make sure the face doesn't already exist.
		if (layer.exists[other | face.isDuplicateOf(other)]) {
			return false
		}
		
		layer.add(face)
		return true
	}
	
	/** Removes all redundant facets from the entire lattice. */
	def private removeRedundancies() {
		(0 ..< lattice.size).forEach[dimension | removeRedundancies(dimension)]
	}
	
	/** Removes all redundant facets from a specific layer of the lattice. */
	def private removeRedundancies(int dimension) {
		if ((dimension < 0) || (dimension >= lattice.size)) {
			return
		}
		val currentLayer = lattice.get(dimension)
		
		// When saturating an inequality, the dimensionality of the facet will decrease by at least one.
		// If it decreases by more than one, then there were other inequalities which were also saturated.
		// However, the way saturated inequalities is tracked here does not account for this.
		// To detect this scenario, we check if the number of intentionally saturated inequalities
		// is less than the reduction in dimensionality.
		// That is, if we start with a k-dimensional polyhedron and saturate d inequalities,
		// if the facet's dimensionality is less than (k-d), then additional inequalities must have been saturated.
		// In other words, this scenario happens if the difference in the root's dimension and a facet's dimension
		// is greater than the number of intentionally saturated inequalities.
		val expectedSaturations = rootInfo.dimensionality - dimension
		val facetsWithAdditionalSaturations = new ArrayList<Facet>(
			currentLayer
			.filter[facet | facet.saturatedInequalityIndices.size < expectedSaturations]
			.toList)
		if (facetsWithAdditionalSaturations.empty) {
			return
		}
		
		// The result of this scenario is that the lattice currently contains multiple facets
		// which represent the same set of points. For any of the facets we've found already,
		// this will be all supersets of that facet at the current layer.
		// We can then take the union of all the supersets and add a new facet of that union.
		facetsWithAdditionalSaturations
			.map[facet | facet.getUnionOfSupersets(currentLayer)]
			.forEach[toSaturate | checkAddFace(toSaturate)]
		
		// We need to remove any facets which are a subset of another facet.
		// Get the saturated inequalities for each facet,
		// then find the list of all facets whose inequalities are subsets of that list.
		// Flatten this and remove duplicates.
		// Wrap this in an ArrayList to avoid recomputation.
		val toRemove = new ArrayList<Facet>(
			currentLayer
			.filter[facet | currentLayer.exists[other | facet.isStrictSubsetOf(other)]]
			.toList)
		currentLayer.removeAll(toRemove)
	}
	
	/**
	 * Gets all facets which are a superset of the given one,
	 * returning the union of their saturated inequalities.
	 */
	def private static getUnionOfSupersets(Facet facet, Collection<Facet> toSearch) {
		return new ArrayList<Integer>(
			toSearch
			.filter[other | facet.isStrictSubsetOf(other)]
			.map[superset | superset.saturatedInequalityIndices]
			.flatten
			.toSet)
	}
}
