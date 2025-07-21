package alpha.model.prdg

import alpha.model.util.ISLUtil
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.ArrayList
import java.util.HashMap
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.List
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron
import alpha.model.util.PolyLibUtil

import static extension alpha.model.util.ISLUtil.*
import java.util.Set
import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLPoint
import fr.irisa.cairn.jnimap.isl.ISLVal

/* 
 * The class represents the Dependence Cone, built out of a PRDG
 * It is used mainly by automatic simplifying reductions to help
 * simplify reductions in the case of dependent reductions
 */
class DependenceCone {

	// A useful variable, set to true to debug output
	public static boolean DEBUG = true;

	private static def void debug(String msg) {
		if (DEBUG)
			println("[DependenceCone] " + msg)
	}

	/*
	 * In our dependence cone we build up all our variable dependences in a single
	 * ISLSpace. The indices represent which index the variable
	 * starts at, the params and variables are maps representing the number of parameters
	 * and variables each program variable has. The projection functions are the projection
	 * function from the reduction space to the variable (aka the write function).
	 * The actual dependences are represented by the sets. Which define the different
	 * sets for the dependence polyhedron. They are kept as taking their union simplified some
	 * important information. To generate the new reuse space we take the union of the 
	 * reuse space with each one and then union the results. The space represents the ISLSpace
	 * of all the variables and params
	 */
	var HashMap<String, Integer> indices
	var HashMap<String, Integer> params
	var HashMap<String, Integer> variables
	var HashMap<String, ISLMultiAff> projectionFunctions
	val ISLSpace space
	@Accessors(PUBLIC_GETTER)
	var Set<ISLBasicSet> sets
	
	/**
	 * Takes all the individual items necessary for a dependence cone and builds a new one
	 * 
	 * @param indices
	 * @param params
	 * @param variables
	 * @param projectionFunctions
	 * @param space
	 * @param set
	 */
	new(HashMap<String, Integer> indices, HashMap<String, Integer> params, HashMap<String, Integer> variables,
		HashMap<String, ISLMultiAff> projs, ISLSpace space, Set<ISLBasicSet> sets) {
		this.indices = indices
		this.params = params
		this.variables = variables
		this.projectionFunctions = projs
		this.space = space
		this.sets = sets
	}

	def private static boolean isReduction(PRDGNode node) {
		(node.name.contains("_result") && node.name.contains("_reduce")) ||
			(node.name.contains("_body") && node.name.contains("_reduce"))
	}

	def private static HashMap<String, Integer> copyMap(HashMap<String, Integer> map) {
		map.entrySet.fold(newHashMap, [newMap, value|newMap.put(value.key, value.value); newMap])
	}

	/**
	 * Takes a program dependence graph and creates a cone representing the dependence space 
	 * 
	 * @param depedences
	 * @return 
	 */
	new(PRDG dependences) {
		indices = newHashMap
		params = newHashMap
		variables = newHashMap
		projectionFunctions = newHashMap
		var num = dependences.nodes.filter[node|!isReduction(node)].fold(0, [ sum, node |
			sum + node.domain.copy.nbParams + node.domain.copy.nbIndices
		])
		space = ISLSpace.allocSetSpace(0, num)
		var current = 0
		this.sets = newHashSet

		for (node : dependences.nodes) {
			if (!isReduction(node)) {
				if (!indices.containsKey(node.name)) {
					indices.put(node.name, current)
					params.put(node.name, node.domain.nbParams)
					variables.put(node.name, node.domain.nbIndices)
					current += node.domain.nbIndices + node.domain.nbParams
				}
			}
		}

		for (edge : dependences.edges) {
			if (isReduction(edge.dest) && edge.dest.name.contains("result")) {
				val bodyToResultEdge = dependences.edges.filter(e|e.source.name == edge.dest.name).get(0)
				var edgeFunction = ISLUtil.toMultiAff(edge.function.copy.toMap.reverse)
				var proj = edgeFunction.pullback(bodyToResultEdge.function.copy)
				
				for (e : dependences.edges.filter[e|e.source.name == bodyToResultEdge.dest.name]) {
					var List<ISLAff> affs = newArrayList
					affs.addAll(proj.affs)
					affs.addAll(e.function.affs)
					projectionFunctions.put(edge.source.name, proj)					
					if (indices.containsKey(e.dest.name)) {
						val numAdditional = proj.nbInputs
						var domOne = e.function.copy.toBasicMap.toBasicSet.addDims(ISLDimType.isl_dim_out, proj.copy.nbOutputs)
						var domTwo = proj.copy.toBasicMap.toBasicSet.intersect(edge.source.domain.copy.simpleHull.addDims(ISLDimType.isl_dim_out, numAdditional))
							.addDims(ISLDimType.isl_dim_out, e.function.copy.nbOutputs)
						var domain = domOne.copy.intersect(domTwo.copy).projectOut(ISLDimType.isl_dim_out, 0, e.function.copy.nbInputs)
						domain = liftDomain(domain.copy, edge.source.name, e.dest.name, e.dest.domain.copy)
						sets.add(domain.copy)
					}
				}
			} else if (!isReduction(edge.dest) && !isReduction(edge.source)) {
				if (indices.containsKey(edge.dest.name) && indices.containsKey(edge.source.name)) {
					val numAdditional = edge.function.nbOutputs
					val map = edge.function.copy.toBasicMap

					var ISLBasicSet domain = map.copy.toBasicSet.intersect(

					edge.source.domain.copy.simpleHull.addDims(ISLDimType.isl_dim_out, numAdditional))
					domain = liftDomain(domain.copy, edge.source.name, edge.dest.name, edge.dest.domain.copy)
					sets.add(domain.copy)					
				}
			}

		}
		debug("Indices: " + indices.toString)
		debug("Set: " + sets)

	}

	/**
	 * Takes a reuseSpace of possible reuse vectors and restricts it by intersecting it with the projection
	 * of the dependence cone
	 * 
	 * @param self
	 * @param reuseSpace
	 * @param variable
	 * @return 
	 */
	def ISLBasicSet intersectReuseSpace(ISLBasicSet reuseSpace, String variable) {
		mergeShareSpace(reuseSpace, this.sets, variable, [one, two|one.intersect(two)])
	}

	/**
	 *	The function that 
	 * 
	 * @param self
	 * @param reuseSpace
	 * @param alphaVar
	 * @param function
	 * @return 
	 */
	def private ISLBasicSet mergeShareSpace(ISLBasicSet reuseSpace, Set<ISLBasicSet> intersectionSets, String alphaVar, (ISLBasicSet, ISLBasicSet) => ISLBasicSet f) {
		var variable = alphaVar
		if(!indices.keySet.contains(variable) && variable.contains("_NR")) {
			var vars = variable.split("_")
			variable = vars.take(vars.length - 1).join("_")
		}
		debug("Variable: " + variable)
		println(projectionFunctions)
		if(!projectionFunctions.containsKey(variable)) {
			//If we don't have any projection functions the reduction is not dependent
			//Return the original reuse space
			return reuseSpace.copy
		}
		val projection = projectionFunctions.get(variable).copy
		val projectedSpace = reuseSpace.copy.apply(projection.copy.toBasicMap)
		val projectedCone = projectOnto(variable, intersectionSets)
//		val correctParams = projectedCone.copy.moveDims(ISLDimType.isl_dim_param, 0, ISLDimType.isl_dim_out, variables.get(variable), params.get(variable))
		val ISLBasicSet correctParams = projectedCone.invertSet
		val ISLBasicSet updatedParams = correctParams.renameParams(projectedSpace.copy.paramNames)
		val ISLBasicSet updatedNames = updatedParams.renameIndices(projectedSpace.copy.indexNames)
		val reverseProjected = updatedNames.copy.apply(projection.toBasicMap.reverse)
		val combination = f.apply(reuseSpace, reverseProjected.copy)

		val output = combination.copy.eliminate(ISLDimType.isl_dim_param, 0, reverseProjected.copy.nbParams)	
		debug("Restricted Reuse Space: " + output.copy.toString)
		output	
	}
	
	def boolean inNegativeCone(ISLBasicSet ray, String alphaVar) {
		var variable = alphaVar
		if(!indices.keySet.contains(variable) && variable.contains("_NR")) {
			var vars = variable.split("_")
			variable = vars.take(vars.length - 1).join("_")
		}
		debug("Variable: " + variable)
		println(projectionFunctions)
		println("Point: " + ray.copy)
		if(!projectionFunctions.containsKey(variable)) {
			//If we don't have any projection functions the reduction is not dependent
			//Return the original reuse space
			return false
		}
		val projection = projectionFunctions.get(variable).copy
		val ISLBasicSet rayWithParams = ray.addParams(projection.copy.domainSpace.paramNames)
		val projectedSpace = rayWithParams.copy.apply(projection.copy.toBasicMap)
		println("Projected Space: " + projectedSpace.copy)
		val projectedCone = projectOnto(variable, this.sets)
		println("Projected Cone: " + projectedCone)
		val ISLBasicSet updatedParams = projectedCone.renameParams(projectedSpace.copy.paramNames)
		val ISLBasicSet updatedNames = updatedParams.renameIndices(projectedSpace.copy.indexNames)
		println("Projection: " + projectedSpace.copy)
		println("Updated: " + updatedNames.copy)
	
		println("Subset: " + projectedSpace.copy.isSubset(updatedNames.copy))
		projectedSpace.copy.isSubset(updatedNames.copy)
	}

	/*
	 * During reduction simplification a number of new variables are often introduced
	 * This handles that introduction and replicates the dependences from which the new
	 * variable was derived
	 */
	def DependenceCone addVarMapping(String variable) {
		var lookup = variable
		if (!indices.keySet.contains(variable) && variable.contains("_NR")) {
			var vars = variable.split("_")
			lookup = vars.take(vars.length - 1).join("_")
		}
		var newInd = copyMap(indices)
		newInd.put(variable + "_pos", indices.get(lookup))
		newInd.put(variable + "_neg", indices.get(lookup))

		var newVars = copyMap(variables)
		newVars.put(variable + "_pos", variables.get(lookup))
		newVars.put(variable + "_neg", variables.get(lookup))

		var newParams = copyMap(params)
		newParams.put(variable + "_pos", params.get(lookup))
		newParams.put(variable + "_neg", params.get(lookup))
		var newProjs = projectionFunctions.entrySet.fold(newHashMap, [acc, x|acc.put(x.key, x.value.copy); acc])
		newProjs.putAll(newProjs.entrySet.filter[entry | entry.key == variable].toMap([x | variable + "_pos"], [x | x.value]))
		newProjs.putAll(newProjs.entrySet.filter[entry | entry.key == variable].toMap([x | variable + "_neg"], [x | x.value]))
		new DependenceCone(newInd, newParams, newVars, newProjs, space.copy, this.sets)
	}

	/*
	 * 
	 */
	def DependenceCone addDependence(String variable, long[] vector) {
		var lookup = variable
		if (!indices.keySet.contains(variable) && variable.contains("_NR")) {
			var vars = variable.split("_")
			lookup = vars.take(vars.length - 1).join("_")
		}
		var newInd = copyMap(indices)
		newInd.put(variable + "_pos", indices.get(lookup))
		newInd.put(variable + "_neg", indices.get(lookup))

		var newVars = copyMap(variables)
		newVars.put(variable + "_pos", variables.get(lookup))
		newVars.put(variable + "_neg", variables.get(lookup))

		var newParams = copyMap(params)
		newParams.put(variable + "_pos", params.get(lookup))
		newParams.put(variable + "_neg", params.get(lookup))

		var newProjs = projectionFunctions.entrySet.fold(newHashMap, [acc, x|acc.put(x.key, x.value.copy); acc])
		newProjs.putAll(newProjs.entrySet.filter[entry | entry.key == variable].toMap([x | variable + "_pos"], [x | x.value]))
		newProjs.putAll(newProjs.entrySet.filter[entry | entry.key == variable].toMap([x | variable + "_neg"], [x | x.value]))
		
		
		val finalConstraint = newConstraints(vector, variable)
		
		var newSets = sets.fold(newHashSet, [acc, set | 
			acc.add(finalConstraint.fold(set.copy, [accTwo, entry | accTwo.copy.addConstraint(entry.copy); accTwo]))
			acc
		])
		new DependenceCone(newInd, newParams, newVars, newProjs, space.copy, newSets)
	}

	def ISLBasicSet projectOnto(String variable) {
		projectOnto(variable, sets)
	}

	def ISLBasicSet projectOnto(String variable, Set<ISLBasicSet> origsets) {
		var HashMap<String, Integer> newIndices = newHashMap
		var oldIndices = indices.filter[k, v|k != variable && v != indices.get(variable)].entrySet
		for (entry : oldIndices) {
			if (!newIndices.containsValue(entry.value)) {
				newIndices.put(entry.key, entry.value)
			}
		}

		val ind = newIndices
		var newSets = origsets.map[set | 
			ind.entrySet.sortWith(x, y | y.value - x.value)
				.map[x | x.key].fold(set.copy, [updated, key | updated.copy.projectOut(ISLDimType.isl_dim_out, indices.get(key), variables.get(key) + params.get(key))])]
		var out = newSets.fold(ISLSet.buildEmpty(ISLSpace.allocSetSpace(0, variables.get(variable) + params.get(variable))), [acc, set | 
			if(!set.copy.isPlainUniverse) { set.copy.toSet.union(acc) } else { acc }
		])
		out = out.moveDims(ISLDimType.isl_dim_param, 0, ISLDimType.isl_dim_out, variables.get(variable), params.get(variable))
		out.convexHull
	}
	
	def boolean isMinimal(long[] vecOne, long[] vecTwo, String variable) {
		
		val firstConstraints = newConstraints(vecOne, variable)
		val secondConstraints = newConstraints(vecTwo, variable)
		sets.exists[set | 
			val setOne = firstConstraints.fold(set.copy, [acc, entry | acc.copy.addConstraint(entry.copy); acc])
			val setTwo = secondConstraints.fold(set.copy, [acc, entry | acc.copy.addConstraint(entry.copy); acc])
			setOne.copy.isSubset(setTwo.copy)
		]
	}
	
	def private Set<ISLConstraint> newConstraints(long[] vec, String variable) {
		val projections = projectionFunctions.filter[key, value | key == variable]
		projections.entrySet.fold(newHashSet, [acc, proj |
			var ArrayList<Integer> newVect = new ArrayList<Integer>()
			val f = proj.value.copy
			for(var i = 0; i < f.affs.size; i++) {
				var Integer sum = 0
				for(var j = 0; j < vec.length; j++) {
					sum = sum + (f.copy.affs.get(i).getCoefficientVal(ISLDimType.isl_dim_in, j).asLong.intValue * -vec.get(j).intValue)
				}
				newVect.add(sum)
			}
			var ISLConstraint newConstraint = ISLConstraint.buildInequality(space.copy)
			for(var i = 0; i < newVect.size; i++) {
				var value = newVect.get(i)
				var sign = -1
				if(newVect.get(i) < 0) {
					value = -value
					sign = 1
				}
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, indices.get(variable) + i, sign * value.intValue)
			}
			acc.add(newConstraint)
			acc
		]).toSet 
	}
	
	def private ISLBasicSet liftDomain(ISLBasicSet set, String sourceVar, String destVar, ISLSet destDomain) {
		var ISLBasicSet newDomain = ISLBasicSet.buildUniverse(space.copy)
		val constraints = set.copy.constraints
		val nbCons = constraints.size

		for(var constraint = 0; constraint < nbCons; constraint++) {
			var newConstraint = ISLConstraint.buildInequality(newDomain.copy.space)
			if(constraints.get(constraint).equality) {
				newConstraint = ISLConstraint.buildEquality(newDomain.copy.space)
			}
			
			for(var ind = 0; ind < this.variables.get(sourceVar); ind++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(sourceVar) + ind, constraints.get(constraint).getCoefficient(ISLDimType.isl_dim_out, ind).intValue)
			}

			for(var ind = 0; ind < this.variables.get(destVar); ind++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(destVar) + ind, constraints.get(constraint).getCoefficient(ISLDimType.isl_dim_out, ind + this.variables.get(sourceVar)).intValue)
			}

			for(var param = 0; param < constraints.get(constraint).copy.space.nbParams; param++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(sourceVar) + this.variables.get(sourceVar) + param, 
					constraints.get(constraint).getCoefficient(ISLDimType.isl_dim_param, param).intValue)
			}
			newConstraint = newConstraint.copy.setConstant(constraints.get(constraint).constant)
			newDomain = newDomain.copy.addConstraint(newConstraint)			
		}



		val destConstraints = destDomain.copy.simpleHull.constraints
		for(var constraint = 0; constraint < destConstraints.size; constraint++) {
			var newConstraint = ISLConstraint.buildInequality(newDomain.copy.space)
			if(destConstraints.get(constraint).equality) {
				newConstraint = ISLConstraint.buildEquality(newDomain.copy.space)
			}

			for(var ind = 0; ind < this.variables.get(destVar); ind++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(destVar) + ind, destConstraints.get(constraint).getCoefficient(ISLDimType.isl_dim_out, ind).intValue)
			}

			for(var param = 0; param < this.params.get(destVar); param++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, 
					this.indices.get(destVar) + this.variables.get(destVar) + param, 
					destConstraints.get(constraint).getCoefficient(ISLDimType.isl_dim_param, param).intValue)
			}

			newConstraint = newConstraint.copy.setConstant(destConstraints.get(constraint).constant)
			newDomain = newDomain.copy.addConstraint(newConstraint)			
		}
		newDomain
	}

	
	
	def private ISLBasicSet liftDomain(ISLBasicSet set, String variable) {
		var ISLBasicSet newDomain = ISLBasicSet.buildUniverse(space.copy)
		val constraints = set.copy.constraints
		val nbCons = constraints.size

		for (var constraint = 0; constraint < nbCons; constraint++) {
			var newConstraint = ISLConstraint.buildInequality(newDomain.copy.space)
			if (constraints.get(constraint).equality) {
				newConstraint = ISLConstraint.buildEquality(newDomain.copy.space)
			}
			for (var ind = 0; ind < this.variables.get(variable); ind++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out,
					this.indices.get(variable) + ind,
					constraints.get(constraint).getCoefficient(ISLDimType.isl_dim_out, ind).intValue)
			}
			for (var param = 0; param < constraints.get(constraint).copy.space.nbParams; param++) {
				newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out,
					this.indices.get(variable) + this.variables.get(variable) + param,
					constraints.get(constraint).getCoefficient(ISLDimType.isl_dim_param, param).intValue)
			}
			newConstraint = newConstraint.copy.setConstant(constraints.get(constraint).constant)
			newDomain = newDomain.copy.addConstraint(newConstraint)
		}
		newDomain
	}
}
