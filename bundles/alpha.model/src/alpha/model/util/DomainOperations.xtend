package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLMatrix
import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLDimType
import alpha.model.matrix.MatrixOperations
import fr.irisa.cairn.jnimap.isl.ISLSpace

import static extension fr.irisa.cairn.jnimap.isl.ISLBasicSet.buildUniverse

class DomainOperations {
	
	def static ISLMatrix toISLEqualityMatrix(ISLBasicMap bmap) {
		bmap.getEqualityMatrix(
				ISLDimType.isl_dim_param, 
				ISLDimType.isl_dim_in,
				ISLDimType.isl_dim_out,
				ISLDimType.isl_dim_div, 
				ISLDimType.isl_dim_cst)
	}
	
	def static ISLMatrix toISLEqualityMatrix(ISLMap map) {
		if (map.getNbBasicMaps() != 1)
			throw new IllegalArgumentException("[DomainOperations] toISLEqualityMatrix assumes a single polyhedron.");
		
		val bmap = map.getBasicMapAt(0);
		
		toISLEqualityMatrix(bmap);
	}
	
	def static ISLMatrix toISLInequalityMatrix(ISLBasicMap bmap) {
		bmap.getInequalityMatrix(
				ISLDimType.isl_dim_param, 
				ISLDimType.isl_dim_in,
				ISLDimType.isl_dim_out,
				ISLDimType.isl_dim_div, 
				ISLDimType.isl_dim_cst)
	}
	
	def static ISLMatrix toISLInequalityMatrix(ISLMap map) {
		if (map.getNbBasicMaps() != 1)
			throw new IllegalArgumentException("[DomainOperations] toISLEqualityMatrix assumes a single polyhedron.");
		
		val bmap = map.getBasicMapAt(0);
		
		toISLInequalityMatrix(bmap)
	}
	
	def static ISLMatrix toISLEqualityMatrix(ISLBasicSet bset) {
		bset.getEqualityMatrix(
				ISLDimType.isl_dim_param, 
				ISLDimType.isl_dim_set,
				ISLDimType.isl_dim_div, 
				ISLDimType.isl_dim_cst)
	}
	
	def static ISLMatrix toISLEqualityMatrix(ISLSet set) {
		if (set.getNbBasicSets() != 1)
			throw new IllegalArgumentException("[DomainOperations] toISLEqualityMatrix assumes a single polyhedron.");
		
		val bset = set.getBasicSetAt(0);
		
		toISLEqualityMatrix(bset)
	}
	
	def static ISLMatrix toISLInequalityMatrix(ISLBasicSet bset) {
		bset.getInequalityMatrix(
				ISLDimType.isl_dim_param, 
				ISLDimType.isl_dim_set,
				ISLDimType.isl_dim_div, 
				ISLDimType.isl_dim_cst)
	}
	
	def static ISLMatrix toISLInequalityMatrix(ISLSet set) {
		if (set.getNbBasicSets() != 1)
			throw new IllegalArgumentException("[DomainOperations] toISLInequalityMatrix assumes a single polyhedron.");
		
		val bset = set.getBasicSetAt(0);
		
		toISLInequalityMatrix(bset)
	}
	
	def static long[][] kernelOfLinearPart(ISLBasicSet bset) {
		if (bset.dim(ISLDimType.isl_dim_div) != 0)
			throw new IllegalArgumentException("[DomainOperations] kernelOfLinearPart assumes a domain with out div dimensions.");

		val ineqMat = toISLInequalityMatrix(bset);
		val eqMat = toISLEqualityMatrix(bset);
		val ineqArray = toLongArray(ineqMat, true);
		val eqArray = toLongArrayFromEqualityMatrix(eqMat, true);
		
		val array = MatrixOperations.rowBind(ineqArray, eqArray);
		
		return MatrixOperations.nullspace(array)
	}
	
	def static long[][] kernelOfLinearPart(ISLSet set) {
		if (set.getNbBasicSets != 1)
			throw new IllegalArgumentException("[DomainOperations] kernelOfLinearPart assumes a single polyhedron.");
		
		val bset = set.getBasicSetAt(0);
		
		kernelOfLinearPart(bset)
	}
	
	def static long[][] toLongArray(ISLMatrix mat, boolean linearPartOnly) {
		var ret = mat
		if (linearPartOnly) {
			ret = ret.dropCols(mat.nbCols-1, 1)
		}
		ret.toLongMatrix
	}
	
	def static long[][] toLongArrayFromEqualityMatrix(ISLMatrix mat, boolean linearPartOnly) {
		val pos = toLongArray(mat, linearPartOnly);
		
		if (pos.length == 0)
			return pos;
		
		val array = MatrixOperations.rowBind(pos, MatrixOperations.scalarMultiplication(pos, -1));
		
		array
	}
	
	/** Returns an ISLBasicSet in the given space spanned by the basis vectors in kernel */
	def static ISLBasicSet toBasicSetFromKernel(long[][] kernel, ISLSpace space) {
		if (kernel.length == 0 || kernel.get(0).length == 0 ) {
			return space.copy.buildUniverse
		}
			
		if (kernel.get(0).length != space.nbParams + space.nbIndices) {
			throw new IllegalArgumentException("The kernel and space dimensions do not match")
		}

		val params = space.paramNames
		val indices = space.indexNames
		
		val set = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, kernel)
			.affs
			.map[aff | aff.toEqualityConstraint]
			.fold(space.copy.buildUniverse, [set, constraint | set.addConstraint(constraint)])
		
		return set
	}
	
	
	
	
}