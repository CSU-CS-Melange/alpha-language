package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.polylib.PolyLibMatrix
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron
import java.util.ArrayList
import java.util.List

import static alpha.model.util.ISLUtil.*

class PolyLibUtil {
	
   	/**
	 * Takes a PolyLibMatrix and a corresponding ISLSpace
	 * and builds an ISLSet of that Matrix in that space
	 * Treating each row as a constraint as defined
	 * in the PolyLib Documentation
	 * 
	 * @param poly
	 * @param setSpace
	 * @return 
	 */
    static def ISLSet toISLSet(PolyLibMatrix matrix, ISLSpace setSpace) {
    	val List<ISLConstraint> constraints = new ArrayList()
    	for(var constraint = 0; constraint < matrix.nbRows; constraint++) {
    		var ISLConstraint islConstraint
    		if(matrix.getAt(constraint, 0) == 0) {
    			islConstraint = ISLConstraint.buildEquality(setSpace.copy)
    		} else {
    			islConstraint = ISLConstraint.buildInequality(setSpace.copy) 
    		}
    		for(var col = 1; col < matrix.nbColumns - 1; col++) {
    			if(col - 1 >= setSpace.nbOutputs) {
    				islConstraint = islConstraint.setCoefficient(ISLDimType.isl_dim_param, col - setSpace.nbOutputs - 1, matrix.getAt(constraint.intValue, col).intValue)
    			} else {
    				islConstraint = islConstraint.setCoefficient(ISLDimType.isl_dim_out, col - 1, matrix.getAt(constraint.intValue, col).intValue)
    			}
    		}
    		islConstraint = islConstraint.setConstant(matrix.getAt(constraint, matrix.nbColumns - 1))
    		constraints.add(islConstraint)
    	}
    	constraints.fold(ISLSet.buildUniverse(setSpace), [acc, constraint | acc.addConstraint(constraint)])    
    }
    
   	/**
	 * Takes a PolyLibPolyhedron and a corresponding ISLSpace
	 * and builds an ISLSet of that polyhedron in that space
	 * 
	 * @param poly
	 * @param setSpace
	 * @return 
	 */
	static def ISLSet toISLSet(PolyLibPolyhedron poly, ISLSpace setSpace) {
    	var PolyLibMatrix m = PolyLibMatrix.buildFromConstraints(poly);
    	toISLSet(m, setSpace)
    }
    
	/**
	 * Converts ISLMap into PolyLibMatrix. The matrix is [A|b] of the
	 * Ax + b representation of the function. 
	 * 
	 * @param map
	 * @return 
	 */
	static def PolyLibMatrix toPolyLibMatrix(ISLMap map) {
		val maff = toMultiAff(map.copy)
		val int nbParam = map.nbParams
		val int nbIndices = map.nbInputs

		var output = PolyLibMatrix.allocate(map.nbOutputs, map.nbInputs + map.nbParams + 1)
						
		for (var row = 0; row < maff.affs.size; row++) {
			val aff = maff.affs.get(row)
			//Only handles affine
			if (aff.getDenominator() != 1) throw new RuntimeException("Quasi-Affine Functions are not handled");
			if (aff.dim(ISLDimType.isl_dim_div) > 0) throw new RuntimeException("Quasi-Affine Functions are not handled");

			// indices first
			for (var i = 0; i < nbIndices; i++) {
				output.setAt(row, i, aff.getCoefficientVal(ISLDimType.isl_dim_in, i).asLong)
			}

			// parameters next
			for (var pi = 0; pi < nbParam; pi++) {
				output.setAt(row, nbIndices + pi, aff.getCoefficientVal(ISLDimType.isl_dim_param, pi).asLong)
			}

			// constant
			output.setAt(row, nbIndices + nbParam, aff.getConstantVal().asLong())		
		}
		
		output
	}
	
	/**
	 * Converts PolyLibPolyhedron into its dual space as an ISLSet (in the space setSpace).
	 * This function ignores the params and just builds the space with the indices 
	 * (you will have to manipulate the space yourself to get the params back in
	 * 
	 * @param poly
	 * @param setSpace
	 * @return 
	 */
	static def ISLSet toDualISLSet(PolyLibPolyhedron poly, ISLSpace setSpace) {
    	val rays = poly.builRaysVertices
	  	val List<ISLConstraint> constraints = new ArrayList()
	  	  
    	for(var ray = 0; ray < rays.nbRows; ray++) {
    		for(var col = 0; col < rays.nbColumns; col++) {
    				print(rays.getAt(ray, col) + " ")
    		}
    		println()
    	}
    	for(var ray = 0; ray < rays.nbRows; ray++) {
    		var ISLConstraint islConstraint = ISLConstraint.buildInequality(setSpace.copy) 

    		if(rays.getAt(ray, 0) == 1 && rays.getAt(ray, rays.nbColumns - 1) == 0) {
    			for(var col = 1; col < rays.nbColumns - 1; col++) {
    				print(rays.getAt(ray, col) + " ")
	    			if(!(col - 1 >= setSpace.nbOutputs)) {
	    				islConstraint = islConstraint.setCoefficient(ISLDimType.isl_dim_out, col - 1, rays.getAt(ray, col).intValue)
	    			}
    			}
    		}
    		println()
    		println(islConstraint)
    		constraints.add(islConstraint)
    	}
    	constraints.fold(ISLSet.buildUniverse(setSpace.copy), [acc, constraint | acc.addConstraint(constraint)]).simplify
    } 
    
    static def PolyLibPolyhedron fromISLBasicSet(ISLBasicSet set) {
    	var PolyLibMatrix constraints = PolyLibMatrix.allocate(set.copy.nbConstraints, set.copy.nbConstants + set.copy.nbIndices + set.copy.nbParams + 1)
    	var constraintNum = 0
		for(constraint : set.copy.constraints) {
			if(!constraint.equality) {
				constraints.setAt(constraintNum, 0, 1)
			}
			for(var index = 0; index < set.copy.nbIndices; index++) {
				constraints.setAt(constraintNum, index + 1, constraint.copy.getCoefficient(ISLDimType.isl_dim_out, index))
			}
			for(var param = 0; param < set.copy.nbParams; param++) {
				constraints.setAt(constraintNum, set.copy.nbIndices + param + 1, constraint.copy.getCoefficient(ISLDimType.isl_dim_param, param))
			}
			constraints.setAt(constraintNum, set.copy.nbIndices + set.copy.nbParams + 1, constraint.copy.getConstant())
			constraintNum++
		}
    	for(var i = 0; i < constraints.nbRows; i++) {
       		for(var j = 0; j < constraints.nbColumns; j++){
       			print(constraints.getAt(i, j) + " ")
       		}
       		println()
       	}
    	PolyLibPolyhedron.buildFromConstraints(constraints, 10)
    }
    
    static def List<List<Long>> toArray(PolyLibMatrix mat) {
    	var output = newArrayList
    	var row = newArrayList
    	for(var i = 0; i < mat.nbRows; i++) {
    		for(var j = 0; j < mat.nbColumns; j++) {
    			row.add(mat.getAt(i, j))
    		}
    		output.add(row)
    		row = newArrayList
    	}
    	output
    }
    
    static def print(PolyLibMatrix mat) {
    	for(var i = 0; i < mat.nbRows; i++) {
    		for(var j = 0; j < mat.nbColumns; j++) {
    			print(mat.getAt(i, j) + " ")
    		}
    		println()
    	}
    }
    
}