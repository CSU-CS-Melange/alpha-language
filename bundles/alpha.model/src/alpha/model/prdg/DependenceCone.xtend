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

/* 
 * The class represents the Dependence Cone, built out of a PRDG
 * It is used mainly by automatic simplifying reductions to help
 * simplify reductions in the case of dependent reductions
 */
class DependenceCone {
	
	// A useful variable, set to true to debug output
	public static boolean DEBUG = false;

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
	@Accessors(PUBLIC_GETTER)
	var List<ISLBasicSet> sets
	val ISLSpace space
	
	/**
	 * Takes all the individual items necessary for a dependence cone and builds a new one
	 * 
	 * @param indices
	 * @param params
	 * @param variables
	 * @param projectionFunctions
	 * @param sets
	 * @param space
	 */
	new (HashMap<String, Integer> indices, HashMap<String, Integer> params, HashMap<String, Integer> variables, HashMap<String, ISLMultiAff> projs, List<ISLBasicSet> sets, ISLSpace space) {
		this.indices = indices
		this.params = params
		this.variables = variables
		this.projectionFunctions = projs	
		this.sets = sets
		this.space = space
	}
	
	def private static boolean isReduction(PRDGNode node) {
		 (node.name.contains("_result") && node.name.contains("_reduce")) || (node.name.contains("_body") && node.name.contains("_reduce"))
	}
	
	def private static HashMap<String, Integer> copyMap(HashMap<String, Integer> map) {
		map.entrySet.fold(newHashMap, [newMap, value | newMap.put(value.key, value.value); newMap])
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
		variables = newHashMap
		projectionFunctions = newHashMap
		var num = dependences.nodes.filter[node | !isReduction(node)]
			.fold(0, [sum, node | sum + node.domain.copy.nbParams + node.domain.copy.nbIndices])
		space = ISLSpace.allocSetSpace(0, num)
		this.sets = newArrayList()
		var current = 0
		
		for(node : dependences.nodes) {
			if(!isReduction(node)) {
				if(!indices.containsKey(node.name)) {
					indices.put(node.name, current)
					params.put(node.name, node.domain.nbParams)
					variables.put(node.name, node.domain.nbIndices)
					current += node.domain.nbIndices + node.domain.nbParams
				}
			}	
		}

		for(edge : dependences.edges) {
			if(isReduction(edge.dest) && edge.dest.name.contains("result")) {
				val bodyToResultEdge = dependences.edges.filter(e | e.source.name == edge.dest.name).get(0)
				
				var edgeFunction = ISLUtil.toMultiAff(edge.function.copy.toMap.reverse)
				var proj = edgeFunction.pullback(bodyToResultEdge.function.copy)
				projectionFunctions.put(edge.source.name, proj)
				for(e : dependences.edges.filter[e | e.source.name == bodyToResultEdge.dest.name]) {
					if(indices.containsKey(e.dest.name)) {
						val numAdditional = proj.nbInputs
						
						var names = proj.copy.toMap.outputNames + e.function.copy.toBasicMap.inputNames
						var domOne = e.function.copy.toBasicMap.toBasicSet.addDims(ISLDimType.isl_dim_out, proj.copy.nbOutputs)
						var domTwo = proj.copy.toBasicMap.toBasicSet.intersect(edge.source.domain.copy.simpleHull.addDims(ISLDimType.isl_dim_out, numAdditional))
							.addDims(ISLDimType.isl_dim_out, e.function.copy.nbOutputs)
	
						var domain = domOne.copy.intersect(domTwo.copy).projectOut(ISLDimType.isl_dim_out, 0, e.function.copy.nbInputs)
						domain = liftDomain(domain.copy, edge.source.name, e.dest.name, e.dest.domain.copy)
						sets.add(domain.copy)
						
					}
				}
			} else if(!isReduction(edge.dest) && !isReduction(edge.source)) {
				if(indices.containsKey(edge.dest.name) && indices.containsKey(edge.source.name)) {
				
				val map = edge.function.copy.toBasicMap
				val numAdditional = edge.function.nbOutputs
				var ISLBasicSet domain = map.copy.toBasicSet.intersect(
					edge.source.domain.copy.simpleHull.addDims(ISLDimType.isl_dim_out, numAdditional))

				domain = liftDomain(domain.copy, edge.source.name, edge.dest.name, edge.dest.domain.copy)
				
				sets.add(domain.copy)

				}
			}
			
		}
		debug("Indices: " + indices.toString)
		debug("Set: ")

		sets.forEach[ set | debug(set.copy.toString) ]
		
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
	def ISLSet intersectReuseSpace(ISLBasicSet reuseSpace, String variable) {
		mergeShareSpace(reuseSpace, variable, [one, two | one.intersect(two).toSet])
	}
	
	/**
	 * Takes a reuseSpace of possible reuse vectors and restricts it by subtracting the projection
	 * of the dependence cone
	 * 
	 * @param self
	 * @param reuseSpace
	 * @param variable
	 * @return 
	 */
	def ISLSet subtractInvalidReuse(ISLBasicSet reuseSpace, String variable) {
		mergeShareSpace(reuseSpace, variable, [one, two | one.toSet.subtract(two.toSet)])
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
	def private ISLSet mergeShareSpace(ISLBasicSet reuseSpace, String alphaVar, (ISLBasicSet, ISLBasicSet) => ISLSet f) {
		var variable = alphaVar
		if(!indices.keySet.contains(variable) && variable.contains("_NR")) {
			var vars = variable.split("_")
			variable = vars.take(vars.length - 1).join("_")
		}
		val projection = projectionFunctions.get(variable).copy
		val projectedSpace = reuseSpace.copy.apply(projection.copy.toBasicMap)
		val projectedCone = projectOnto(variable)
		val correctParams = projectedCone.copy.moveDims(ISLDimType.isl_dim_param, 0, ISLDimType.isl_dim_out, variables.get(variable), params.get(variable))
		val ISLBasicSet updatedParams = correctParams.renameParams(projectedSpace.copy.paramNames)
		val ISLBasicSet updatedNames = updatedParams.renameIndices(projectedSpace.copy.indexNames)

		val reverseProjected = updatedNames.copy.apply(projection.toBasicMap.reverse)
		val combination = f.apply(reuseSpace, reverseProjected.copy)

		val output = combination.copy.eliminate(ISLDimType.isl_dim_param, 0, reverseProjected.copy.nbParams)	
		debug("Restricted Reuse Space: " + output.copy.toString)
		output	
	}
	
	def DependenceCone addVarMapping(String variable) {
		var lookup = variable
		if(!indices.keySet.contains(variable) && variable.contains("_NR")) {
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
		var newProjs = projectionFunctions.entrySet.fold(newHashMap, [acc, x | acc.put(x.key, x.value.copy); acc])
		newProjs.put(variable + "_pos", projectionFunctions.get(lookup))
		newProjs.put(variable + "_neg", projectionFunctions.get(lookup))
		new DependenceCone(newInd, newParams, newVars, projectionFunctions, sets, space.copy)
	}

	def DependenceCone addDependence(String variable, long[] vector) {
		var lookup = variable
		if(!indices.keySet.contains(variable) && variable.contains("_NR")) {
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
		var newProjs = projectionFunctions.entrySet.fold(newHashMap, [acc, x | acc.put(x.key, x.value.copy); acc])
		newProjs.put(variable + "_pos", projectionFunctions.get(lookup))
		newProjs.put(variable + "_neg", projectionFunctions.get(lookup))
		var ArrayList<Long> vect
		var ISLConstraint newConstraint = ISLConstraint.buildInequality(space.copy)
		if(projectionFunctions.containsKey(lookup)) {
			var ArrayList<Long> newVect = new ArrayList<Long>()
			val f = projectionFunctions.get(lookup)
			for(var i = 0; i < f.affs.size; i++) {
				var Long sum = 0L
				for(var j = 0; j < vector.length; j++) {
					sum = sum + (f.copy.affs.get(i).getCoefficientVal(ISLDimType.isl_dim_in, j).asLong * -vector.get(j))
				}
				newVect.add(sum)
			}
			vect = newVect
		} else {
			vect = newArrayList
			vect = vector.fold(vect, [acc, x | acc.add(-x); acc])			
		}
		debug("Adding Dependence: [" + vect.fold("", [acc, x | acc + " " + x.toString]) + " ] (Projected) to variable: " + variable)
		
			
		for(var i = 0; i < vect.size; i++) {
			newConstraint = newConstraint.copy.setCoefficient(ISLDimType.isl_dim_out, indices.get(lookup) + i, vect.get(i).intValue)
		}
		val finalConstraint = newConstraint.copy
		var newSets = sets.fold(newArrayList, [acc, x | acc.add(x.copy.addConstraint(finalConstraint.copy)); acc])
		var newDomain = ISLBasicSet.buildUniverse(space.copy)
		newDomain = newDomain.copy.addConstraint(finalConstraint.copy)
		newSets.add(newDomain.copy)
		new DependenceCone(newInd, newParams, newVars, newProjs, newSets, space.copy)
	}
	
	def ISLBasicSet projectOnto(String variable){
		var HashMap<String, Integer> newIndices = newHashMap
		var oldIndices = indices.filter[k, v| k != variable && v != indices.get(variable)].entrySet
		for(entry : oldIndices) {
			if(!newIndices.containsValue(entry.value)) {
				newIndices.put(entry.key, entry.value)
			}
		} 

		val ind = newIndices
		var newSets = sets.map[set | 
			ind.entrySet.sortWith(x, y | y.value - x.value)
				.map[x | x.key].fold(set.copy, [updated, key | updated.copy.projectOut(ISLDimType.isl_dim_out, indices.get(key), variables.get(key) + params.get(key))])]
		var out = newSets.fold(ISLSet.buildEmpty(ISLSpace.allocSetSpace(0, variables.get(variable) + params.get(variable))), [acc, set | 
			if(!set.copy.isPlainUniverse) { set.copy.toSet.union(acc) } else { acc }
		]).convexHull
		out
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
}