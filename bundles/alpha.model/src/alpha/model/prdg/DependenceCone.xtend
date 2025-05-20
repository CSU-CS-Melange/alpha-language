package alpha.model.prdg

import alpha.model.util.ISLUtil
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron
import java.util.ArrayList
import java.util.HashMap
import org.eclipse.xtend.lib.annotations.Accessors

import static alpha.model.util.ISLUtil.*
import fr.irisa.cairn.jnimap.isl.ISLMap
import java.util.List

/* 
 * The class represents the Dependence Cone, built out of a PRDG
 * It is used mainly by automatic simplifying reductions to help
 * simplify reductions in the case of dependent reductions
 */
class DependenceCone {
	var HashMap<String, Integer> indices
	var HashMap<String, Integer> params
	var HashMap<String, Integer> numVars
	@Accessors(PUBLIC_GETTER)
	var ISLSet cone 
	var HashMap<String, ISLMultiAff> projectionFunctions
	var List<ISLBasicSet> sets
	
	new (HashMap<String, Integer> indices, HashMap<String, Integer> params, HashMap<String, Integer> numVars, ISLSet cone, HashMap<String, ISLMultiAff> projs, List<ISLBasicSet> sets) {
		this.indices = indices
		this.params = params
		this.numVars = numVars
		this.cone = cone.copy
		println(cone.copy)
		this.projectionFunctions = projs	
		this.sets = sets
	}
	
	/**
	 * Takes a program dependence graph and creates a cone representing the dependence space 
	 * 
	 * @param depedences
	 * @return 
	 */
	new (PRDG dependences) {
		indices = newHashMap
		params = newHashMap
		numVars = newHashMap
		projectionFunctions = newHashMap
		var num = dependences.nodes.filter[node | !node.reductionNode].fold(0, [sum, node | sum + node.domain.copy.nbParams + node.domain.copy.nbIndices])
		cone = ISLSet.buildEmpty(ISLSpace.allocSetSpace(0, num))
		this.sets = newArrayList()
		var current = 0
		
		for(edge : dependences.edges.filter[node | !node.dest.name.equals("A")]) {
			if(edge.dest.name.contains("reduce") && edge.dest.name.contains("result")) {
				var bodyToResultEdge = dependences.edges.filter(e | e.source.name == edge.dest.name).get(0)
				var edgeFunction = ISLUtil.toMultiAff(edge.function.copy.toMap.reverse)
				var proj = edgeFunction.pullback(bodyToResultEdge.function.copy)
				println(proj.copy)
				projectionFunctions.put(edge.source.name, proj)
			} else if(!edge.source.isReductionNode && !edge.dest.isReductionNode) {
				if(!indices.containsKey(edge.source.name)) {
					indices.put(edge.source.name, current)
					params.put(edge.source.name, edge.source.domain.nbParams)
					numVars.put(edge.source.name, edge.source.domain.nbIndices)
					current += edge.source.domain.nbIndices + edge.source.domain.nbParams
				}
				if(!indices.containsKey(edge.dest.name)) {
					indices.put(edge.dest.name, current)
					params.put(edge.dest.name, edge.dest.domain.nbParams)
					numVars.put(edge.dest.name, edge.dest.domain.nbIndices)
					current += edge.dest.domain.nbIndices + edge.dest.domain.nbParams
				}
				val map = edge.function.copy.toBasicMap
				val numAdditional = edge.function.nbOutputs

				var ISLBasicSet domain = map.copy.toBasicSet.intersect(
					edge.source.domain.copy.simpleHull.addDims(ISLDimType.isl_dim_out, numAdditional))

				domain = liftDomain(domain.copy, edge.source.name, edge.dest.name, edge.dest.domain.copy)
				sets.add(domain.copy)
				var union = cone.copy.union(domain.toSet)
				
				cone = union.copy
			}
			
		}
	}
	
	/**
	 * Takes a reuseSpace of possible reuse vectors and 
	 * 
	 * @param self
	 * @param reuseSpace
	 * @param variable
	 * @return 
	 */
	 //TODO: Check direction of reuse vector
	def ISLBasicSet intersectReuseSpace(ISLBasicSet reuseSpace, String variable) {
		println("Variable: " + variable)
		mergeShareSpace(reuseSpace, variable, [one, two | one.intersect(two)])
	}
	
	//TODO: Figure out the subtraction space as it will involve potentially multiple basic sets
	def ISLBasicSet subtractInvalidReuse(ISLBasicSet reuseSpace, String variable) {
//		mergeShareSpace(reuseSpace, variable, [one, two | one.toSet.subtract(two.toSet).basicSets.get(0)])
		
		mergeShareSpace(reuseSpace, variable, [one, two | one.intersect(two)])
		
	}
	
	def private ISLBasicSet mergeShareSpace(ISLBasicSet reuseSpace, String alphaVar, (ISLBasicSet, ISLBasicSet) => ISLBasicSet f) {
		println("Cone: " + cone.copy)
		println("Variable: " + alphaVar)
		println("Indices: " + indices)
		println("start index: " + indices.get(alphaVar))
		println("Share Space: " + reuseSpace.copy)
		var variable = alphaVar
		if(!indices.keySet.contains(variable) && variable.contains("_NR")) {
			variable = variable.split("_NR").get(0)
		}
		println("Updated Var: " + variable)
		val projection = projectionFunctions.get(variable).copy
		println("Share Space: " + reuseSpace.copy)
		val projectedSpace = reuseSpace.copy.apply(projection.copy.toBasicMap)
		val projectedCone = projectOnto(variable)
//		val invertedCone = invertSet(projectedCone.copy)
		println("Projected Space: " + projectedSpace.copy)
		println("Projected Cone: " + projectedCone.copy)
		val correctParams = projectedCone.moveDims(ISLDimType.isl_dim_param, 0, ISLDimType.isl_dim_out, 0, params.get(variable))
		println("Corrected: " + correctParams.copy)
		val ISLBasicSet updatedParams = correctParams.renameParams(projectedSpace.copy.paramNames)
		val ISLBasicSet updatedNames = updatedParams.renameIndices(projectedSpace.copy.indexNames)
		println("Updated Names: " + updatedNames.copy)

		val reverseProjected = updatedNames.copy.apply(projection.toBasicMap.reverse)
		println("Reverse Projection: " + reverseProjected)
		val combination = f.apply(reverseProjected.copy, reuseSpace)
		println("Intersection: " + combination.copy)
		val fin = combination.copy.eliminate(ISLDimType.isl_dim_param, 0, reverseProjected.copy.nbParams)
//		val reversed = invertSet(fin)
		
		println("Final: " + reverseProjected.copy)
		println("Final (no params): " + fin)
//		println("Reverse: " + reversed)
		fin
	}
	
	def DependenceCone addVarMapping(String variable, String newVar) {
		var newInd = indices
		newInd.put(newVar, indices.get(variable))
		var newVars = numVars
		newVars.put(newVar, numVars.get(variable))
		var newParams = params
		newParams.put(newVar, params.get(variable))
		var newProjs = projectionFunctions
		newProjs.put(newVar, projectionFunctions.get(variable))
		new DependenceCone(newInd, newParams, newVars, cone.copy, projectionFunctions, sets)
	}

	def DependenceCone addDependence(String variable, long[] vector) {
		println("Adding a new dependence vector to the cone")
		println(variable)
		var newInd = indices
		newInd.put(variable + "_pos", indices.get(variable))
		var newVars = numVars
		newVars.put(variable + "_pos", numVars.get(variable))
		var newParams = params
		newParams.put(variable + "_pos", params.get(variable))
		var newProjs = projectionFunctions
		newProjs.put(variable + "_pos", projectionFunctions.get(variable))
		var ArrayList<Long> vect
		var ISLConstraint newConstraint = ISLConstraint.buildInequality(cone.copy.space)
		if(projectionFunctions.containsKey(variable)) {
			var ArrayList<Long> newVect = new ArrayList<Long>()
			val f = projectionFunctions.get(variable)
//			val f = ISLUtil.toISLMultiAff("[N] -> {[i, j] -> [i + j + 1]}")
			println(f.copy)
			for(var i = 0; i < f.affs.size; i++) {
				println(f.affs.get(i))
				var Long sum = 0L
				for(var j = 0; j < vector.length; j++) {
					println(f.copy.affs.get(i).space)
					sum = sum + (f.copy.affs.get(i).getCoefficientVal(ISLDimType.isl_dim_in, j).asLong * vector.get(j))
				}
				newVect.add(sum)
			}
			vect = newVect
		} else {
			for(var i = 0; i < vector.size; i++) {
				vect.add(vector.get(i))
			}
		}
			
//		}
//		println(projectionFunctions)
//		val projectedVector = projectionFunctions.get(variable).copy
		for(var i = 0; i < vect.size; i++) {
			newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, indices.get(variable) + i, vect.get(i).intValue)
		}
		println("New Constraint: " + newConstraint.copy)
		println
		for(var i = 0; i < vect.size; i++) {
			print(vect.get(i) + " ")
		}
		println
		new DependenceCone(newInd, newParams, newVars, cone.copy, newProjs, sets)
	}
	
	def ISLBasicSet projectOnto(String variable){
		println("Projection: " + variable)
		println("Indices: " + indices.get(variable))
		println("Index: " + indices)
		println("Keys: " +  indices.filter[k, v| k != variable && v != indices.get(variable)])
		println("Cone: " + cone.copy)
		var HashMap<String, Integer> newIndices = newHashMap
		var oldIndices = indices.filter[k, v| k != variable && v != indices.get(variable)].entrySet
		for(entry : oldIndices) {
			if(!newIndices.containsValue(entry.value)) {
				newIndices.put(entry.key, entry.value)
			}
		} 
		
		//		map.intersectRange()
		var output = newIndices.entrySet.sortWith(x, y | y.value - x.value)
			.map[x | x.key].fold(cone.copy, [updated, key | updated.copy.projectOut(ISLDimType.isl_dim_out, indices.get(key), numVars.get(key) + params.get(key))])
//			.keySet.fold(cone.copy, [updated, key | updated.copy.projectOut(ISLDimType.isl_dim_out, indices.get(key), numVars.get(variable) + params.get(variable))])

		val ind = newIndices
//		println("newIndices: " + ind)
//		println("Sets:")
//		sets.forEach[x | println(x.copy)]
//		println("New Sets:")
		var newSets = sets.map[set | 
			ind.entrySet.sortWith(x, y | y.value - x.value)
				.map[x | x.key].fold(set.copy, [updated, key | updated.copy.projectOut(ISLDimType.isl_dim_out, indices.get(key), numVars.get(key) + params.get(key))])]
		newSets.forEach[x | println(x.copy)]
//		println("Projected Output: " + output.copy)	
//		println("Output: " + output.copy.convexHull)
		var testSet = ISLUtil.toISLBasicSet("{ [i0, i1] :  }")
//		println("Test Empty: " + testSet.copy.isPlainUniverse)
//		println("Test: " + testSet.union(ISLUtil.toISLBasicSet("{ [i0, i1] : i0 > 0 and i1 >= i0 }")))
		var out = newSets.fold(ISLSet.buildEmpty(ISLSpace.allocSetSpace(0, numVars.get(variable) + params.get(variable))), [acc, set | 
			if(!set.copy.isPlainUniverse) { set.copy.toSet.union(acc) } else { acc }
		]).convexHull
//		println("Out: " + out)
		out
	}
	
	def private ISLBasicSet liftDomain(ISLBasicSet set, String sourceVar, String destVar, ISLSet destDomain) {
		var ISLBasicSet newDomain = ISLBasicSet.buildUniverse(cone.copy.space)
//		println("Var: " + this.indices)
//		println("output: " + newDomain.copy)
		val constraints = set.copy.constraints
		val nbCons = constraints.size

		for(var constraint = 0; constraint < nbCons; constraint++) {
			var newConstraint = ISLConstraint.buildInequality(newDomain.copy.space)
//			println("Old Constraint: " + constraints.get(constraint))
			if(constraints.get(constraint).equality) {
				newConstraint = ISLConstraint.buildEquality(newDomain.copy.space)
			}
			for(var ind = 0; ind < this.numVars.get(sourceVar); ind++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(sourceVar) + ind, constraints.get(constraint).getCoefficient(ISLDimType.isl_dim_out, ind).intValue)
			}
			for(var ind = 0; ind < this.numVars.get(destVar); ind++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(destVar) + ind, constraints.get(constraint).getCoefficient(ISLDimType.isl_dim_out, ind + this.numVars.get(sourceVar)).intValue)
			}
			for(var param = 0; param < this.params.get(sourceVar); param++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(sourceVar) + this.numVars.get(sourceVar) + param, 
					constraints.get(constraint).getCoefficient(ISLDimType.isl_dim_param, param).intValue)
			}
			newConstraint = newConstraint.copy.setConstant(constraints.get(constraint).constant)
//			println("Constraint: " + newConstraint.copy)
			newDomain = newDomain.copy.addConstraint(newConstraint)
//			println("output: " + newDomain.copy)
			
		}
		
		val destConstraints = destDomain.copy.simpleHull.constraints
//		println("HERE")
		for(var constraint = 0; constraint < destConstraints.size; constraint++) {
			var newConstraint = ISLConstraint.buildInequality(newDomain.copy.space)
//			println("Old Constraint: " + destConstraints.get(constraint))
			if(destConstraints.get(constraint).equality) {
				newConstraint = ISLConstraint.buildEquality(newDomain.copy.space)
			}
			for(var ind = 0; ind < this.numVars.get(destVar); ind++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(destVar) + ind, destConstraints.get(constraint).getCoefficient(ISLDimType.isl_dim_out, ind).intValue)
			}
			for(var param = 0; param < this.params.get(destVar); param++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(destVar) + this.numVars.get(destVar) + param, 
					destConstraints.get(constraint).getCoefficient(ISLDimType.isl_dim_param, param).intValue)
			}
			newConstraint = newConstraint.copy.setConstant(constraints.get(constraint).constant)
//			println("Constraint: " + newConstraint.copy)
			newDomain = newDomain.copy.addConstraint(newConstraint)
//			println("output: " + newDomain.copy)
			
		}
//		println("output: " + newDomain.copy)
		newDomain
	}

}