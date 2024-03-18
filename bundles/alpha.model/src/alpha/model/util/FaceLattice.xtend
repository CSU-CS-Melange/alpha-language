package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import java.util.ArrayList

import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.Face.removeDuplicates

/**
 * Represents and constructs an entire face lattice from a basic set.
 */
class FaceLattice {
	/**
	 * The storage of the lattice itself.
	 * The index of each layer (the outermost list) is the dimensionality.
	 * I.e., <code>lattice[2]</code> contains all the 2D faces of the lattice.
	 * Each layer is a list of all the sets which are in that layer.
	 */
	val ArrayList<ArrayList<Face>> lattice
	
	/** Constructs the face lattice of the given set. */
	new(ISLBasicSet root) {
		val rootFace = new Face(root, this)
		val dimensionality = rootFace.dimensionality
		
		// Initialize the lattice data structure with the root.
		lattice = (0 .. dimensionality).map[null].toArrayList
		lattice.set(dimensionality, #[rootFace].toArrayList)
		
		// Populate all of the lower dimensions.
		for (dim : dimensionality >.. 0) {
			val current = lattice.get(dim + 1)
				.flatMap[parent | parent.generateChildren]
				.removeDuplicates
			lattice.set(dim, current)
		}
	}
	
	/**
	 * Gets the set of faces of the given dimensionality.
	 * 0-dimension is vertices, 1-dimension is lines, etc.
	 * Throws an IllegalArgumentException if you provide an invalid dimension.
	 */
	def getFaces(int dimension) {
		if ((dimension < 0) || (dimension >= lattice.size)) {
			throw new IllegalArgumentException("There is no dimension '" + dimension + "' in the lattice.")
		}
		return lattice.get(dimension).unmodifiableView
	}
	
	/**
	 * Returns the face representing the original set provided.
	 */
	def getRoot() {
		return getFaces(lattice.size - 1).get(0)
	}
	
	/**
	 * Gets all 0-faces (vertices).
	 */
	def getVertices() {
		return getFaces(0)
	}
	
	def getLattice() {
		return lattice
	}
}