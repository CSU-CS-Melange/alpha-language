package alpha.model.prdg

import static alpha.model.util.PolyLibUtil.*
import java.util.HashMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import alpha.model.util.ISLUtil
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock
import org.eclipse.xtext.xbase.lib.Functions.Function2
import jdk.nashorn.internal.objects.annotations.Getter
import org.eclipse.xtend.lib.annotations.Accessors
import alpha.model.util.PolyLibUtil
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron
import fr.irisa.cairn.jnimap.polylib.PolyLibMatrix
import fr.irisa.cairn.jnimap.isl.ISLBasicMap

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
	var ISLBasicSet cone 
	var HashMap<String, ISLMultiAff> projectionFunctions
	
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
		cone = ISLBasicSet.buildEmpty(ISLSpace.allocSetSpace(0, 0))
		for(edge : dependences.edges.filter[node | !node.dest.name.equals("A")]) {
			println(edge.source.name + " -> " + edge.dest.name + ": " + edge.function.copy)
			println("Edge: " + edge.isReductionEdge)
			println("Source: " + edge.source.isReductionNode)
			println("Dest: " + edge.dest.isReductionNode)
			if(edge.dest.name.contains("reduce") && edge.dest.name.contains("result")) {
				var bodyToResultEdge = dependences.edges.filter(e | e.source.name == edge.dest.name).get(0)
				var edgeFunction = ISLUtil.toMultiAff(edge.function.copy.toMap.reverse)
				var proj = edgeFunction.pullback(bodyToResultEdge.function.copy)
				println(proj.copy)
				projectionFunctions.put(edge.source.name, proj)
			} else if(!edge.source.isReductionNode && !edge.dest.isReductionNode) {
				if(!indices.containsKey(edge.source.name)) {
					indices.put(edge.source.name, cone.nbIndices)
					params.put(edge.source.name, edge.source.domain.nbParams)
					numVars.put(edge.source.name, edge.source.domain.nbIndices)
					cone = cone.copy.addDims(ISLDimType.isl_dim_out, edge.source.domain.nbIndices + edge.source.domain.nbParams)

				}
				if(!indices.containsKey(edge.dest.name)) {
					indices.put(edge.dest.name, cone.nbIndices)
					params.put(edge.dest.name, edge.dest.domain.nbParams)
					numVars.put(edge.dest.name, edge.dest.domain.nbIndices)
					cone = cone.copy.addDims(ISLDimType.isl_dim_out, edge.dest.domain.nbIndices + edge.dest.domain.nbParams)
				}
//				if(edge.dest.name == "Y") {
//					var ISLConstraint constraint = ISLConstraint.buildInequality(cone.copy.space)
//					constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, indices.get("Y") + 1, -1)
//					constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, indices.get("X") + 1, 1)
//					constraint = constraint.setConstant(-1)
//					println(constraint)
//					println("HERE")
//					cone = cone.addConstraint(constraint)
//				}
				println("Function: " + edge.function.copy.toMap)
				val map = edge.function.copy.toBasicMap
				val numAdditional = edge.function.nbOutputs
				println("Map: " + map.copy)
				var ISLBasicSet domain = map.copy.toBasicSet.intersect(
					edge.source.domain.copy.simpleHull.addDims(ISLDimType.isl_dim_out, numAdditional))
				println("domain: " + domain.copy)
//					val dependenceMatrix = PolyLibMatrix.buildFromConstraints(PolyLibPolyhedron.createFromLongMatrix(domain.copy.toPolyLibArray))
//					println(PolyLibUtil.toISLSet(dependenceMatrix, domain.space))
				println("Cone: " + cone.copy)
				domain = liftDomain(domain.copy, edge.source.name, edge.dest.name, edge.dest.domain.copy)
				println("Updated Domain: " + domain.copy)
				var union = cone.copy.union(domain)
				println("Unioin: " + union.copy)
				cone = union.simpleHull
			}
			
		}
				println(cone)
		
		val array = cone.copy.toPolyLibArray
		for(var i = 0; i < array.size; i++) {
			for(var j = 0; j < array.get(0).size; j++) {
				print(" " + array.get(i, j))
			}
			println()
		}
		println("A")
		val poly = PolyLibPolyhedron.createFromLongMatrix(cone.copy.toPolyLibArray)
		val rays = poly.builRaysVertices
		for(var i = 0; i < rays.nbRows; i++) {
			for(var j = 0; j < rays.nbColumns; j++) {
				print(" " + rays.getAt(i, j))
			}
			println()
		}
		println(indices)
//		cone = PolyLibUtil.toDualISLSet(rays, cone.copy.space).getBasicSetAt(0)
		println(cone)
		removeParams
		println("No Params: " + cone.copy)
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
		mergeShareSpace(reuseSpace, variable, [one, two | one.toSet.subtract(two.toSet).basicSets.get(0)])
	}
	
	def private ISLBasicSet mergeShareSpace(ISLBasicSet reuseSpace, String variable, (ISLBasicSet, ISLBasicSet) => ISLBasicSet f) {
		println("Cone: " + cone.copy)
		println("Variable: " + variable)
		println("start index: " + indices.get(variable))
		val projection = projectionFunctions.get(variable).copy
		println("Share Space: " + reuseSpace.copy)
		val projectedSpace = reuseSpace.copy.apply(projection.copy.toBasicMap)
		val projectedCone = projectOnto(variable)
		println("Projected Space: " + projectedSpace.copy)
		println("Projected Cone: " + projectedCone.copy)
		val correctParams = projectedCone.moveDims(ISLDimType.isl_dim_param, 0, ISLDimType.isl_dim_out, 0, params.get(variable))
		println("Corrected: " + correctParams.copy)
		val ISLBasicSet updatedParams = correctParams.renameParams(projectedSpace.copy.paramNames)
		val ISLBasicSet updatedNames = updatedParams.renameIndices(projectedSpace.copy.indexNames)
		println("Updated Names: " + updatedNames.copy)
		val combination = f.apply(updatedNames, projectedSpace)
		println("Intersection: " + combination.copy)
		val reverseProjected = combination.apply(projection.toBasicMap.reverse)
		println("Final: " + reverseProjected.copy)
		reverseProjected
	}
	
	def private removeParams() {
		val orderedKeys = indices.entrySet.sortWith(x, y | y.value - x.value)
		println(orderedKeys)
		cone = orderedKeys.fold(cone, [acc, key | cone = cone.copy.projectOut(ISLDimType.isl_dim_out, key.value + numVars.get(key.key), params.get(key.key)); cone ])
	}
	
	def addDependence(String variable, Long[] vector) {
		var ISLConstraint newConstraint = ISLConstraint.buildInequality(cone.copy.space)
		val projectedVector = projectionFunctions.get(variable).copy
		for(var i = 0; i < vector.length; i++) {
			newConstraint = newConstraint.copy.setCoefficient()
		}
	}
	def private ISLBasicSet projectOnto(String variable){
		var output = indices.filter[k, _| k != variable]
			.keySet.fold(cone.copy, [updated, key | updated.copy.projectOut(ISLDimType.isl_dim_out, indices.get(key), numVars.get(variable) + params.get(variable))])
		println("Output: " + output.copy)	
		output
	}
	
	def private ISLBasicSet liftDomain(ISLBasicSet set, String sourceVar, String destVar, ISLSet destDomain) {
		var ISLBasicSet newDomain = ISLBasicSet.buildUniverse(cone.copy.space)
		println("Var: " + this.indices)
		println("output: " + newDomain.copy)
		val constraints = set.copy.constraints
		val nbCons = constraints.size

		for(var constraint = 0; constraint < nbCons; constraint++) {
			var newConstraint = ISLConstraint.buildInequality(newDomain.copy.space)
			println("Old Constraint: " + constraints.get(constraint))
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
			println("Constraint: " + newConstraint.copy)
			newDomain = newDomain.copy.addConstraint(newConstraint)
			println("output: " + newDomain.copy)
			
		}
		
		val destConstraints = destDomain.copy.simpleHull.constraints
		println("HERE")
		for(var constraint = 0; constraint < destConstraints.size; constraint++) {
			var newConstraint = ISLConstraint.buildInequality(newDomain.copy.space)
			println("Old Constraint: " + destConstraints.get(constraint))
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
			println("Constraint: " + newConstraint.copy)
			newDomain = newDomain.copy.addConstraint(newConstraint)
			println("output: " + newDomain.copy)
			
		}
		println("output: " + newDomain.copy)
		newDomain
	}
}