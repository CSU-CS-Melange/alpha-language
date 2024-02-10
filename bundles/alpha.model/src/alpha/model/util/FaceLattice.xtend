package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import java.util.ArrayList
import java.util.LinkedList
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.HashSet
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLDimType

import static extension alpha.model.util.AlphaUtil.renameDims
import java.util.List

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
	ArrayList<ArrayList<Facet>> lattice
	
	/** The way each facet can be labeled by one particular choice of reuse */
	enum Label {
		POS,
		NEG,
		ZERO
	}
	
	////////////////////////////////////////////////////////////
	// Static Helper Methods
	////////////////////////////////////////////////////////////
	
	/** Converts decimal value to base radix and converts to a list of N digits padded with leading zeros */
	def static int[] toPaddedDigitArray(int value, int radix, int N) {
		val radixValue = Integer.toString(value, radix)
		val digitArray = radixValue.toCharArray.map[v | Integer.parseInt(v.toString, radix)]
		val padSize = N - digitArray.size
		if (padSize < 0) {
			throw new Exception("Value " + value + " in radix " + radix + " requires more than " + N + " digits") 
		}
		val pad = (0..<padSize).map[0]
		
		return pad + digitArray
	}
	
	////////////////////////////////////////////////////////////
	// Construction
	////////////////////////////////////////////////////////////
	
	/** Constructs a new, empty lattice. */
	private new() { 
		rootInfo = null
		lattice = new ArrayList<ArrayList<Facet>>
	}
	
	/** Creates a new face lattice for the given set. */
	def static create(ISLSet root) {
		if (root.nbBasicSets > 1)
			throw new Exception("Face lattice construction can only be done for a single basic set")
		root.getBasicSetAt(0).create
	}
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
	
	/** Returns the domain D such that any vector within induces a particular labeling among the facets.
	 *  Here, the word "face" refers to a node in the lattice and "facets" (with a 't') as the direct
	 *  children of that particular "face".
	 *  face:
	 *  - facet1
	 *  - facet2
	 *  - facet3
	 *  ...
	 *  There are 3 possible labels for a facet: POS,NEG,ZERO.
	 *  Each facet is said to be either an POS-facet, an NEG-facet, or an ZERO-facet.
	 *  
	 *  By definition, the linear space of each facet differs from its face by a single inequality constraint
	 *  "c" (ISLConstraint). The index coefficients of "c" represent the normal vector "v" (ISLAff) to the facet.
	 *  
	 *  A particular facet can be made an:
	 *  - POS-facet:  new constraint with coefficients of "v" that >0
	 *  - NEG-facet:  new constraint with coefficients of "v" that <0
	 *  - ZERO-facet: new constraint with coefficients of "v" that =0
	 * 
	 */
	def getLabelingDomain(Facet face, Label[] labeling) {
		val facets = getChildren(face)
		val nbFacets = facets.size
		if (nbFacets != labeling.size) {
			throw new Exception("Must specify a label for every facet to get a labeling domain")
		}
		
		// For each facet, build the constraint that induces its desired labeling
		val normalVectors = facets.map[f | f.getNormalVector(face)]
		val constraints = (0..<nbFacets).map[i | normalVectors.get(i).toLabelInducingConstraint(face.space, labeling.get(i))]
		
		// Add all label inducing constraints to the current face's domain
		var domain = ISLBasicSet.buildUniverse(face.space.copy)
		for (constraint : constraints) {
			domain = domain.addConstraint(constraint)
		}
		
		// Finally drop any constraints involving parameters
		// The param dim information is still needed to be able to pass a reuse vector from this
		// space to simplification (without having to re-add this information later)
		domain = domain.dropConstraintsInvolvingDims(ISLDimType.isl_dim_param, 0, face.space.nbParams)
		
		return labeling -> domain.removeRedundancies
	}
	
	/** Creates a constraint in the specified "space" that induces a labeling "label" */
	def ISLConstraint toLabelInducingConstraint(ISLAff vector, ISLSpace space, Label label) {
		// The normal vectors are "just vectors", they have no parameter or constant information
		// so that must be added back in here.
		val vectorInAffineSpace = vector.copy.addDims(ISLDimType.isl_dim_param, space.nbParams)
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
	
	/** Creates the set of all possible label combinations */
	def List<List<Label>> enumerateAllPossibleLabelings(Label[] validLabels, int nbFacets) {
		// computes "a" (num valid labels) raised to the "b" (num facets) power
		val numCombos = (0..<nbFacets).map[validLabels.size].reduce[v1,v2 | v1*v2]
		//val numCombos = Math.pow(validLabels.size, nbFacets).intValue
		
		val labelings = (0..<numCombos).map[value | 
			toPaddedDigitArray(value, validLabels.size, nbFacets).map[i | validLabels.get(i)].toList
		].toList
		
		labelings
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














