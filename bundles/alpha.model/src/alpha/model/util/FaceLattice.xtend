package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import java.util.ArrayList
import java.util.LinkedList
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.HashSet

/**
 * Constructs the face lattice of a given <code>ISLBasicSet</code>.
 * Usage: to create the face lattice, call the static function: <code>FaceLattice.create(root)</code>.
 */
class FaceLattice {
	////////////////////////////////////////////////////////////
	// Fields and Properties
	////////////////////////////////////////////////////////////
	
	/** The information about the set which forms the root of the lattice. */
	@Accessors(PUBLIC_GETTER, PRIVATE_SETTER)
	var Facet rootInfo
	
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
	private new() { 
		rootInfo = null
		lattice = new ArrayList<ArrayList<Facet>>
	}
	
	/** Creates a new face lattice for the given set. */
	def static create(ISLBasicSet root) {
		// Set up the face lattice which is rooted at the given set.
		val lattice = new FaceLattice
		val rootInfo = new Facet(root, lattice)
		lattice.rootInfo = rootInfo
		
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
			val hasChildren = isValidFace && (currentConstraints.size < rootInfo.dimensionality)
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
	
	
	////////////////////////////////////////////////////////////
	// Private Methods
	////////////////////////////////////////////////////////////
	
	/**
	 * Checks if a face is valid to add to the lattice, and adds it if so.
	 * @returns Returns <code>true</code> if the face was valid and added, and <code>false</code> otherwise.
	 */
	def private checkAddFace(ArrayList<Integer> toSaturate) {
		// Create the proposed face by saturating the indicated inequality constraints,
		// and check whether or not this creates a valid face.
		val face = Facet.createFace(rootInfo, toSaturate)
		if (!face.isValidFace(rootInfo)) {
			return false
		}
		
		// Lattice layer indices match the dimensionality of the faces in that layer.
		// Be careful to avoid out of bounds accesses!
		val layerIndex = Integer.max(0, face.dimensionality)
		while (lattice.size <= layerIndex) {
			lattice.add(new ArrayList<Facet>)
		}
		lattice.get(layerIndex).add(face)
		return true
	}
	
	def private removeRedundancies() {
		val layersWithStrangeKFacets = (0..<lattice.size).filter[k |
			val kFacets = lattice.get(k)
			val expectedSaturations = rootInfo.dimensionality - k
			
			val strangeKFacets = kFacets.filter[kFacet | kFacet.saturatedInequalityIndices.size != expectedSaturations]
			!strangeKFacets.empty
		]
		for (k : layersWithStrangeKFacets) {
			val kFacets = lattice.get(k)
			val expectedSaturations = rootInfo.dimensionality - k
			
			val strangeKFacets = kFacets.filter[kFacet | kFacet.saturatedInequalityIndices.size != expectedSaturations]
			
			val duplicateKFacetsForStrangeKFacets = strangeKFacets.map[ strangeKFacet |
				kFacets.filter[kFacet | kFacet.saturatedInequalityIndices.toSet.containsAll(strangeKFacet.saturatedInequalityIndices.toSet)]
			]
			
			val duplicateIndices = duplicateKFacetsForStrangeKFacets.reduce[fs1, fs2 | fs1 + fs2]
			                                                        .map[kFacet | kFacet.saturatedInequalityIndices.toSet]
			                                                        .toSet
			
			val uniqueKFacetsIndices = duplicateKFacetsForStrangeKFacets.map[duplicateKFacetsForStrangeKFacet |
				val union = new HashSet<Integer>
				for (kFacet : duplicateKFacetsForStrangeKFacet) {
					union.addAll(kFacet.saturatedInequalityIndices)
				}
				return union
			].toSet
			
			val uniqueKFacets = uniqueKFacetsIndices.map[ids |
				val idsArr = new ArrayList<Integer>
				idsArr.addAll(ids) 
				Facet.createFace(rootInfo, idsArr)
			]
			
			kFacets.removeAll(kFacets.filter[kFacet | duplicateIndices.contains(kFacet.saturatedInequalityIndices.toSet)])
			kFacets.addAll(uniqueKFacets)
			
//			println(k + '           : ' + kFacets)
//			println(k + '  (strange): ' + strangeKFacets)
//			println(k + '      (dup): ' + duplicateKFacetsForStrangeKFacets)
//			println(k + '    (dupId): ' + duplicateIndices)
//			println(k + ' (uniqueId): ' + uniqueKFacetsIndices)
//			println(k + '   (unique): ' + uniqueKFacets)
//			println()
			
			
		}
	}
}














