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
		cone = ISLBasicSet.buildUniverse(ISLSpace.allocSetSpace(0, 0))
		for(edge : dependences.edges) {
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
					numVars.put(edge.source.name, edge.source.domain.nbParams + edge.source.domain.nbIndices)
					cone = cone.copy.addDims(ISLDimType.isl_dim_out, edge.source.domain.nbIndices + edge.source.domain.nbParams)
				}
				if(!indices.containsKey(edge.dest.name)) {
					indices.put(edge.dest.name, cone.nbIndices)
					params.put(edge.dest.name, edge.dest.domain.nbParams)
					numVars.put(edge.dest.name, edge.dest.domain.nbParams + edge.dest.domain.nbIndices)
					cone = cone.copy.addDims(ISLDimType.isl_dim_out, edge.dest.domain.nbIndices + edge.dest.domain.nbParams)
				}
				if(edge.dest.name == "Y") {
					var ISLConstraint constraint = ISLConstraint.buildInequality(cone.copy.space)
					constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, indices.get("Y") + 1, -1)
					constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, indices.get("X") + 1, 1)
					constraint = constraint.setConstant(-1)
					println(constraint)
					println("HERE")
					cone = cone.addConstraint(constraint)
				}
			}
		}
		println(indices)
		println(cone)
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
	
	def private ISLBasicSet projectOnto(String variable){
		var output = indices.filter[k, _| k != variable]
			.keySet.fold(cone.copy, [updated, key | updated.copy.projectOut(ISLDimType.isl_dim_out, indices.get(key), numVars.get(variable))])
		println("Output: " + output.copy)	
		output
	}
}