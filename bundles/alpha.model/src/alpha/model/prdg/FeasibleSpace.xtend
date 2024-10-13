package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLDimType
import java.util.HashMap
import java.util.ArrayList
import java.util.List
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import org.eclipse.xtend.lib.annotations.Accessors
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import java.util.stream.IntStream
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLMultiAff

class FeasibleSpace {
	@Accessors(PUBLIC_GETTER)
	var HashMap<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> variables 
 	@Accessors(PUBLIC_GETTER)
 	var HashMap<String, List<String>> variableIndices
 	@Accessors(PUBLIC_GETTER)
 	var HashMap<String, Integer> indexMappings
 	@Accessors(PUBLIC_GETTER)
 	var HashMap<String, Pair<Pair<String, ISLMultiAff>, List<Pair<String, ISLMultiAff>>>> reductionMappings
 	var ISLBasicMap space
 	var String variableName
 	var int variableCount
 	var int muCount
 	@Accessors(PUBLIC_GETTER)
	var HashMap<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> dependenceMappings
	var HashMap<Pair<String, String>, List<Pair<String, HashMap<String, Integer>>>> functionMappings
	var int lambdaCount
 	
 	new(PRDG prdg) {
 		variables = newHashMap
		variableIndices = newHashMap
		muCount = 0
		variableCount = 0
		variableName = "i"
		dependenceMappings = newHashMap
		functionMappings = newHashMap
		indexMappings = newHashMap
		reductionMappings = newHashMap
		
		for(node : prdg.nodes) {
			if(!(node.name.contains("_body") || node.name.contains("_result"))) {
				var domain = node.domain.copy.coalesce
				this.addDomain(domain, node.name)
			}
		}
		println("Variables: " + this.variables)

		for(edge : prdg.edges.reverse) { 
			if(edge.source.name.contains("_body")) {
				var array = new ArrayList()
				array.add(new Pair(edge.dest.name, edge.function))
				reductionMappings.merge(edge.source.name, new Pair(null, array),
					[existing, n | 
						var x = existing ?: n
						new Pair(x.key, n.value)
					])
			} else if(edge.dest.name.contains("_result")) {
				var body = prdg.edges.filter(x | x.source.name == edge.dest.name).head
				reductionMappings.merge(body.dest.name, 
					new Pair(new Pair(edge.source.name, body.function),
					new ArrayList), [existing, n | 
						var x = existing ?: n
						new Pair(n.key, x.value)
					])
			} else if(!(edge.dest.name.contains("_body") || edge.source.name.contains("_result"))) {
				var function = edge.function.copy.toMap.simplify.intersectDomain(edge.domain.copy)
				this.addEdgeConstraints(edge.source.name, edge.dest.name, function)
			}
		}
		println("Reductions: " + reductionMappings)

		space = ISLBasicMap.buildUniverse(ISLSpace.alloc(0, 0, 0))
		var muIndex = 0
		for(entry : variables.entrySet) {
			space = space.addOutputs(entry.value.map[x | x.key])
			for(pair : entry.value) {
				indexMappings.put(pair.key, muIndex)
				space = space.addConstraint(singleGreaterThan(muIndex, 
				space.copy.space, ISLDimType.isl_dim_out))
				muIndex++
			}
		}
		
		var lambdaIndex = 0
		for(entry : dependenceMappings.entrySet) {
			space = space.addInputs(entry.value.map[x | x.key])
			for(pair : entry.value) {
				indexMappings.put(pair.key, lambdaIndex)
				space = space.addConstraint(singleGreaterThan(lambdaIndex, 
					space.copy.space, ISLDimType.isl_dim_in))
				lambdaIndex++
			}
		}
//		println("Variables: ")
//		variables.forEach[x, y | println(x + ": "); y.forEach[z | println(z)]]
//		println("Lambas: ")
//		dependenceMappings.forEach[x, y | println(x + ":"); y.forEach[z | println(z)]]
		for(entry : dependenceMappings.entrySet) {
			if(entry.key.key == entry.key.value) {
				this.selfDependenceConstraint(entry.key.key, entry.value)
			} else {
				this.interVariableConstraint(entry.key, entry.value)
			}
		}	
		
		reductionMappings.forEach[reductionNode, value |
 			value.value.forEach[ dependence |
 				var constantConstraint = ISLConstraint.buildInequality(space.space)
				constantConstraint = updateConstraint(constantConstraint, value.key.key, 1, "constant", 1)
				println("Constant Constraint: " + constantConstraint)
	 			constantConstraint = updateConstraint(constantConstraint, dependence.key, -1, "constant", 1)
				println("Constant Constraint: " + constantConstraint)
 				val projections = value.key.value.copy.affs;
 				val reads = dependence.value.copy.affs;
 				reads.forEach[x | println(x)]
 				(0..<projections.size).forEach[leftIndex |
 					(0..<reads.size).forEach[rightIndex | 
 						(0..<dependence.value.copy.nbInputs).forEach[index |
							var constraint = ISLConstraint.buildInequality(space.space)
							println("projection: " + projections.get(leftIndex))
							println("Reads: " + reads.get(rightIndex))
							constraint = updateConstraint(constraint, value.key.key, 1, 
								variableIndices.get(value.key.key).get(leftIndex), 
								projections.get(leftIndex).getCoefficientVal(ISLDimType.isl_dim_in, index).copy.asLong.intValue)
							constraint = updateConstraint(constraint, dependence.key, -1, 
								variableIndices.get(dependence.key).get(rightIndex), 
								reads.get(rightIndex).getCoefficientVal(ISLDimType.isl_dim_in, index).copy.asLong.intValue)
							println("Index: " + index + ", Projection coefficient: " + projections.get(rightIndex).getCoefficientVal(ISLDimType.isl_dim_in, index))								
							println("Index: " + index + ", Read coefficient: " + reads.get(rightIndex).getCoefficientVal(ISLDimType.isl_dim_in, index))
							println("Constraint: " + constraint)							
	 					]
 					] 		
 				]
 			]
 		]
		space.copy.removeRedundancies
//		println(space)	
 	}
 	
 	def private ISLConstraint updateConstraint(ISLConstraint constraint, String variable, int direction, String index, int coefficient) {
 		variables.get(variable).fold(constraint.copy, 
 			[c, map |
 				var old = c.getCoefficient(ISLDimType.isl_dim_out, indexMappings.get(map.key)).intValue
				c.copy.setCoefficient(ISLDimType.isl_dim_out,
					indexMappings.get(map.key),
 					old + direction * coefficient * map.value.get(index).value)
			])
 	}
 	
 	def private selfDependenceConstraint(String variable, List<Pair<String, 
 		HashMap<String, Pair<Integer, Integer>>>> dependences) {
 		
 		var selfDependence = variables.get(variable)
 		var mapping = functionMappings.get(new Pair(variable, variable))
		var constantConstraint = ISLConstraint.buildInequality(this.space.space)
 		for(map : mapping) {
 			var constraint = ISLConstraint.buildInequality(this.space.space)
 			for(dependence : selfDependence) {
 				if(map.value.get("constant") != 0 && dependence.value.get(map.key) != 0) {
	 				var value = constantConstraint.copy.getCoefficient(ISLDimType.isl_dim_out,
	 					indexMappings.get(dependence.key))
					constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_out,
						indexMappings.get(dependence.key),
						value.intValue - (dependence.value.get(map.key).value *
						map.value.get("constant")))
 				}
 				for(index : variableIndices.get(variable)) {
 					var value = constraint.getCoefficient(ISLDimType.isl_dim_out, indexMappings.get(dependence.key))
 					constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, 
 						indexMappings.get(dependence.key),
 						(value.intValue - (dependence.value.get(index).value * map.value.get(index))))	 				
 				}
 				var value = constraint.getCoefficient(ISLDimType.isl_dim_out, indexMappings.get(dependence.key))
 				constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, 
	 				indexMappings.get(dependence.key),
 					value.intValue + dependence.value.get(map.key).value)

 			}
// 			println("Self Dependence Constraint: " + constraint)
			this.space = this.space.addConstraint(constraint)
 		}
 		constantConstraint = constantConstraint.setConstant(-1)
// 		println("Constant Constraint: " + constantConstraint)
 		this.space = this.space.addConstraint(constantConstraint)
 	}
 	
 	def private interVariableConstraint(Pair<String, String> sourceDest, List<Pair<String, 
 		HashMap<String, Pair<Integer, Integer>>>> dependences) {
 		var constantConstraint = ISLConstraint.buildEquality(space.copy.space)
		constantConstraint = constantConstraint.setConstant(-1)
		for(constraint : dependences) {
			constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_in,
				indexMappings.get(constraint.key),
				-constraint.value.get("constant").value)
		}
		for(constraint : variables.get(sourceDest.key)) {
			constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_out,
				indexMappings.get(constraint.key),
				constraint.value.get("constant").value)
		}
		for(constraint : variables.get(sourceDest.value)) {
			constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_out,
				indexMappings.get(constraint.key),
				-constraint.value.get("constant").value)
		}
//		println(constantConstraint)
		space = space.addConstraint(constantConstraint)
//		println("Variables Source: " + variableIndices.get(sourceDest.key))
//		println("Variables Dest: " + variableIndices.get(sourceDest.value))
		for(index : variableIndices.get(sourceDest.key)) {
			var lambdaConstraint = ISLConstraint.buildEquality(space.copy.space)
			lambdaConstraint = (lambdaConstraint.constant = -1)
			for(constraint : variables.get(sourceDest.key)) {
//				println("Constraint: " + constraint)
				lambdaConstraint = lambdaConstraint.setCoefficient(ISLDimType.isl_dim_out,
					indexMappings.get(constraint.key),
					constraint.value.get(index).value)
			}
			//This checks for parameters
			if(variableIndices.get(sourceDest.value).contains(index)) {
				for(constraint : variables.get(sourceDest.value)) {
//				println("Constraint: " + constraint)
					lambdaConstraint = lambdaConstraint.setCoefficient(ISLDimType.isl_dim_out,
						indexMappings.get(constraint.key),
						-constraint.value.get(index).value)
				}
			}
//			println("Function Mappings: " + functionMappings)
//			println("Updated Lambda: " + lambdaConstraint)
			for(constraint : variables.get(sourceDest.value)) {
//				println("Constraint: " + constraint)
				for(mapping : functionMappings.get(sourceDest)) {
//					println("Map: " + mapping)
					var value = lambdaConstraint
						.getCoefficient(ISLDimType.isl_dim_out, indexMappings.get(constraint.key)).intValue
					value -= constraint.value.get(mapping.key).value * mapping.value.get(index)
					lambdaConstraint = lambdaConstraint.setCoefficient(ISLDimType.isl_dim_out,
						indexMappings.get(constraint.key),
						value)
				}
			}
			for(constraint : dependences) {
				lambdaConstraint = lambdaConstraint.setCoefficient(ISLDimType.isl_dim_in,
					indexMappings.get(constraint.key),
					-constraint.value.get(index).value)
			}
//			println("Final Constraint: " + lambdaConstraint)
			space = space.addConstraint(lambdaConstraint)
		}
	}
 	
 	def ISLBasicSet getSpace() {
 		space.copy.getRange
 	}

	def private static ISLConstraint singleGreaterThan(Integer index, ISLSpace space, ISLDimType dim) {
		var constraint = ISLConstraint.buildInequality(space)
		constraint = constraint.setCoefficient(dim, index, 1)
		constraint
	}
	
 	def addDomain(ISLSet domain, String variable) {
 		var indexCount = domain.nbIndices
		var indices = newArrayList
		for(var i = 0; i < indexCount; i++) {
			indices.add(variableName + variableCount)
			variableCount++
		}
		
		var updatedDomain = domain.renameIndices(indices)
		updatedDomain = updatedDomain.moveParametersToIndices
		val coefficients = this.buildDomainCoefficients(updatedDomain.copy, "m", muCount)
		muCount += coefficients.size
		variables.put(variable, coefficients)
		variableIndices.put(variable, updatedDomain.indexNames)
 	}
 	
 	def addEdgeConstraints(String source, String target, ISLMap f) {
 		println("Source: " + source + ", Target: " + target)
 		var sourceTarget = new Pair(source, target)
 		var function = f.copy
		function = function.renameInputs(variableIndices.get(source))
		function = function.renameOutputs(variableIndices.get(target))
 		function = function.moveParamsToInputs
		var functionCoefficients = buildFunctionCoefficients(function.copy, variableIndices.get(target))
		functionMappings.put(sourceTarget, functionCoefficients)
		var domain = function.copy.toSet
		domain = domain.moveParametersToIndices
		var dependenceCoefficients = buildDomainCoefficients(domain, "l", lambdaCount)
		dependenceMappings.put(sourceTarget, dependenceCoefficients)
		lambdaCount += dependenceCoefficients.size
 	}
 	
 	def private List<Pair<String, HashMap<String, Integer>>> buildFunctionCoefficients(ISLMap function, 
		List<String> output_indices
	) {
		var function_coefficients = new ArrayList
		var output_index = 0
		for(map : function.copy.toPWMultiAff.pieces) {
			for(piece : map.maff.affs) {
//				println("Piece: " + piece)
				var function_coefficient = newHashMap 
				var params = piece.paramNames 
				for(var i = 0; i < piece.nbParams; i++) {
					var Integer value = piece.getCoefficientVal(ISLDimType.isl_dim_param, i).asLong.intValue  
					function_coefficient.put(params.get(i), value)
				}
				var inputs = piece.inputNames
				for(var i = 0; i < piece.nbInputs; i++) {
					var Integer value = piece.getCoefficientVal(ISLDimType.isl_dim_in, i).asLong.intValue  
					function_coefficient.put(inputs.get(i), value)
				}
				function_coefficient.put("constant", piece.getConstant().intValue)	
				function_coefficients.add(new Pair(output_indices.get(output_index), function_coefficient))
				output_index++
			}
		}
		function_coefficients
	}
	
 	def private List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> buildDomainCoefficients(ISLSet domain, String label, Integer count) {
		var constraints = new ArrayList
		var label_count = count		
		for(basic_set : domain.basicSets) {
			var indices = basic_set.copy.indexNames
			var constant_coefficients = newHashMap
			for(var i = 0; i < indices.size; i++) {
				constant_coefficients.put(indices.get(i), new Pair(i, 0))
			}
			constant_coefficients.put("constant", new Pair(0, 1))
			constraints.add(new Pair(label + label_count, constant_coefficients))
			label_count++
			for(constraint : basic_set.removeRedundancies.constraints) {
				var coefficients = newHashMap
				for(var i = 0; i < indices.size; i++) {
					var coefficient = constraint.copy.getCoefficient(ISLDimType.isl_dim_out, i)
					coefficients.put(indices.get(i), new Pair(i, coefficient.intValue))
				}
				coefficients.put("constant", new Pair(0, constraint.copy.getConstant().intValue))
				constraints.add(new Pair(label + label_count, coefficients))
				label_count++
			}
		}
		constraints
	}
	
}