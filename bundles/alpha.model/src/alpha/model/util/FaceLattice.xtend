package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.ArrayList
import org.eclipse.xtend.lib.annotations.Accessors

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
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val ArrayList<ArrayList<Face>> lattice
	
	/** Creates the face lattice of the given set if it is convex */
	def static create(ISLSet root) {
		if (root.nbBasicSets != 1) {
			throw new Exception("Face lattice construction expects the input set to be convex")
		}
		new FaceLattice(root.getBasicSetAt(0))
	}
	
	/** Creates the face lattice of the given set. */
	def static create(ISLBasicSet root) {
		new FaceLattice(root)
	}
	
	/** Internal construct to build the face lattice of the given set. */
	protected new(ISLBasicSet root) {
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
	
	def prettyPrint() {
		val root = getRoot
		val d = root.dimensionality
		val ret = newArrayList
		ret += 'Lattice:'
		lattice.reverse.forEach[faces,i | 
			ret += '''  «d-i»-faces: «faces.join(', ')»'''
		]
		ret += 'Constraints:'
		ret += root.unsaturatedConstraints.entrySet.map['''  «key»: «value»''']
		ret += 'Faces:'
		ret += lattice.flatten.map['''  «toString»: «toBasicSet»''']
		
		ret.join('\n')
	}
}


