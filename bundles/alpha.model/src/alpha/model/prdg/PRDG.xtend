package alpha.model.prdg

import java.util.List
import java.util.LinkedList
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron
import fr.irisa.cairn.jnimap.isl.ISLConstraint

import static extension alpha.model.util.ISLUtil.toISLMap
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import java.util.ArrayList
import java.util.HashMap
import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.polylib.PolylibPrettyPrinter

class PRDG {
	List<PRDGNode> nodes
	List<PRDGEdge> edges
	ISLUnionSet domains
	ISLUnionMap islPRDG
	
	new() {
		nodes = new LinkedList()
		edges = new LinkedList()
	}

	def PRDGNode getNode(String name) {
		var nodes = nodes.filter[ node | node.name.equals(name) ]
		if(nodes.length > 0) {
			nodes.get(0)
		} else {
			null
		}
	}
	
	def List<PRDGNode> getNodes() {
		this.nodes
	}
	
	def List<PRDGEdge> getEdges() {
		this.edges
	}
	
	
	def show() {
		println("Nodes: ")
		println(nodes.map[ node | node.toString() ])
		println("Edges: ")
		println(edges.map[ edge | edge.toString() ])
	}
	
	def addNodes(List<PRDGNode> names) {
		nodes = names
	}
	
	def addEdge(PRDGEdge edge) {
		edges.add(edge)
	}
	
	def ISLUnionSet generateDomains() {
		if (this.domains !== null) {
			return this.domains
		}
		
		for (PRDGNode node : this.nodes) {
			var domain = node.domain.copy
			domain = domain.setTupleName(node.name)
			if (domains === null) {
				this.domains = domain.copy.toUnionSet
			} else {
				this.domains = domains.copy.union(domain.toUnionSet)	
			} 
		}
		if (domains === null) {
			throw new NullPointerException();
		}
		this.domains
	}
	
	// This function converts from our map structure to union map that
	// ISL can use to schedule based off of the causality described by the 
	// PRDG
	def ISLUnionMap generateISLPRDG() {
		if (this.islPRDG !== null) {
			return this.islPRDG
		}
		if (this.domains === null) {
			this.generateDomains
		}
		
		this.islPRDG = ISLMap.buildEmpty(ISLSpace.copySpaceParamsForMap(this.domains.copy.getSpace.copy)).toUnionMap
		for (PRDGEdge edge : this.getEdges) {
			var map1 = edge.function.copy
			var set = edge.domain.copy
			var map2 = map1.copy.toMap.simplify.intersectDomain(set.copy)			
			if (map2 === null) {
				System.out.println("map1 = " + map1)
				System.out.println("set = " + set)
				throw new RuntimeException("Problem while intersecting domain")
			}
			
			if(edge.source.isReductionNode && edge.dest.isReductionNode) {
				map2 = map2.reverse

			}	
			map2 = map2.setTupleName(ISLDimType.isl_dim_out, edge.dest.name)
			map2 = map2.setTupleName(ISLDimType.isl_dim_in, edge.source.name)
					
			this.islPRDG = islPRDG.union(map2.copy.toUnionMap)
		}		
		
		this.islPRDG
	}
	
	def static List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> 
	buildDomainCoefficients(ISLSet domain, String label, Integer count) {
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

	def static List<Pair<String, HashMap<String, Integer>>> buildFunctionCoefficients(ISLMap function, 
		List<String> output_indices
	) {
		var function_coefficients = new ArrayList
		var output_index = 0
		for(map : function.copy.toPWMultiAff.pieces) {
			map.maff.affs.forEach[x | println(x)]
			for(piece : map.maff.affs) {
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
	
	
	
	def PolyLibPolyhedron generateFeasibleSpace() {
		var variables = newHashMap
		var variable_indices = newHashMap
		var mu_count = 0
		var variable_count = 0
		var variable_name = "i"
		for(node : this.nodes) {
			var domain = node.domain.copy.coalesce
			var index_count = domain.nbIndices
			var indices = newArrayList
			for(var i = 0; i < index_count; i++) {
				indices.add(variable_name + variable_count)
				variable_count++
			}
			domain = domain.renameIndices(indices)
			domain = domain.moveParametersToIndices
			val coefficients = buildDomainCoefficients(domain.copy, "m", mu_count)
			mu_count += coefficients.size
			variables.put(node.name, coefficients)
			variable_indices.put(node.name, domain.indexNames)
		}
		var dependence_mappings = newHashMap
		var self_dependence_mappings = newHashMap
		var function_mappings = newHashMap
		var lambda_count = 0
		for(edge : this.edges) { 
			var source_target = new Pair(edge.source.name, edge.dest.name)
			var function = edge.function.copy.toMap.simplify.intersectDomain(edge.domain.copy)
			function = function.renameInputs(variable_indices.get(edge.source.name))
			function = function.renameOutputs(variable_indices.get(edge.dest.name))
			println(edge.dest.domain.copy.indexNames)
			var function_coefficients = buildFunctionCoefficients(function.copy, variable_indices.get(edge.dest.name))
			function_mappings.put(source_target, function_coefficients)
			var domain = function.copy.toSet
			domain = domain.moveParametersToIndices
			println("Relational Domain" + domain)
			var dependence_coefficients = buildDomainCoefficients(domain, "l", lambda_count)
			//TODO Check if isSingleValued means uniform this should then be an and
			if(edge.source.name == edge.dest.name && function.isSingleValued) {
				println(function.isSingleValued)
				self_dependence_mappings.put(source_target, dependence_coefficients)
			} else {
				dependence_mappings.put(source_target, dependence_coefficients)
				lambda_count += dependence_coefficients.size
			}
		}
		
		var feasible_space = ISLBasicMap.buildUniverse(ISLSpace.alloc(0, 0, 0))
		var index_mappings = newHashMap
		var mu_index = 0
		for(entry : variables.entrySet) {
			feasible_space = feasible_space.addOutputs(entry.value.map[x | x.key])
			for(pair : entry.value) {
				index_mappings.put(pair.key, mu_index)
				feasible_space = feasible_space.addConstraint(singleGreaterThan(mu_index, 
					feasible_space.copy.space, ISLDimType.isl_dim_out))
				mu_index++
			}
		}

		var lambda_index = 0
		for(entry : dependence_mappings.entrySet) {
			feasible_space = feasible_space.addInputs(entry.value.map[x | x.key])
			for(pair : entry.value) {
				index_mappings.put(pair.key, lambda_index)
				feasible_space = feasible_space.addConstraint(singleGreaterThan(lambda_index, 
					feasible_space.copy.space, ISLDimType.isl_dim_in))
				lambda_index++
			}
			println("Dependence: " + entry.key)
			println("functions: ")
			function_mappings.get(entry.key).forEach[x | println(x)]
			println("Dependence Constraints: ")
			entry.value.forEach[x | println(x)]
		}
		
		for(entry : self_dependence_mappings.entrySet) {
			val variable = variables.get(entry.key.key)
			var functions = function_mappings.get(entry.key)
			println(function_mappings.get(entry.key))
			println(variable_indices.get(entry.key.key))
			for(var i = 0; i < functions.size; i++) {
				println("Function: " + functions.get(i))
				if(functions.get(i).value.get("constant") != 0) {
					feasible_space = feasible_space
						.addConstraint(generateSelfCancelConstraint(variable_indices.get(entry.key.key).get(i),
							feasible_space.copy.space, variable, index_mappings, functions.get(i).value.get("constant")))
				}
				//TODO Figure out Linear Part
				var target_variables = variable_indices
				for(index : variable_indices.get(entry.key.key)) {
					println(index)
				}
				
			}
		}
		
		for(entry : dependence_mappings.entrySet) {
			println("Mapping: " + entry.key)
			var constant_constraint = ISLConstraint.buildEquality(feasible_space.copy.space)
			println("Constraints: ")
			constant_constraint = constant_constraint.setConstant(-1)
			println("Lambda Constraints") 
			for(constraint : entry.value) {
				println(constraint)
				constant_constraint = constant_constraint.setCoefficient(ISLDimType.isl_dim_in,
					index_mappings.get(constraint.key),
					-constraint.value.get("constant").value
				)
			}
			println("Source Constraints:")
			for(constraint : variables.get(entry.key.key)) {
				println(constraint)
				constant_constraint = constant_constraint.setCoefficient(ISLDimType.isl_dim_out,
					index_mappings.get(constraint.key),
					constraint.value.get("constant").value
				)
			}
			println("Dest constraints:")
			for(constraint : variables.get(entry.key.value)) {
				println(constraint)
				constant_constraint = constant_constraint.setCoefficient(ISLDimType.isl_dim_out,
					index_mappings.get(constraint.key),
					-constraint.value.get("constant").value
				)
			}
			println("Constant Constraint:")
			println(constant_constraint)
			feasible_space = feasible_space.addConstraint(constant_constraint)

			for(index : variable_indices.get(entry.key.key)) {
				println("Index: " + index)
				var lambda_constraint = ISLConstraint.buildEquality(feasible_space.copy.space)
				lambda_constraint = (lambda_constraint.constant = -1)
				println("Constraint:")
				for(constraint : variables.get(entry.key.key)) {
					println(constraint)
					lambda_constraint = lambda_constraint.setCoefficient(ISLDimType.isl_dim_out,
						index_mappings.get(constraint.key),
						constraint.value.get(index).value)
				}
				println(lambda_constraint)
				println("Dest Constraint")
				for(constraint : variables.get(entry.key.value)) {
					println(constraint)
					for(mapping : function_mappings.get(entry.key)) {
						println(mapping)
						println(index)
						var value = lambda_constraint
							.getCoefficient(ISLDimType.isl_dim_out, index_mappings.get(constraint.key)).intValue
						value -= constraint.value.get(mapping.key).value * mapping.value.get(index) 
						lambda_constraint = lambda_constraint.setCoefficient(ISLDimType.isl_dim_out,
							index_mappings.get(constraint.key),
							value)
					}
					println(lambda_constraint)
				}

				println("Lambda Constraints:")
				for(constraint : entry.value) {
					println(constraint)
					lambda_constraint = lambda_constraint.setCoefficient(ISLDimType.isl_dim_in,
						index_mappings.get(constraint.key),
						-constraint.value.get(index).value)
				}
				println(lambda_constraint)
				feasible_space = feasible_space.addConstraint(lambda_constraint)
			}
		}	

//		println(dependence_mappings)
//		println(function_mappings)
//		println(index_mappings)
		println(variables)
		println(variable_indices)		
//		println(self_dependence_mappings)
		println(feasible_space.copy)


		PolyLibPolyhedron.createFromLongMatrix(feasible_space.getRange.toPolyLibArray)
	}
	
	def static ISLConstraint generateSelfCancelConstraint(String index, ISLSpace space,
		List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> variable,
		HashMap<String, Integer> index_mappings, int coefficient
	) {
		var constraint = ISLConstraint.buildInequality(space)
		for(dependence : variable) {
			var mu = dependence.key
			var dependence_value = dependence.value.get(index).value 
			if(dependence_value != 0) {
				constraint = constraint.setCoefficient(ISLDimType.isl_dim_out,
					index_mappings.get(mu),
					-(dependence_value * coefficient))
			}
		}
		constraint = (constraint.constant = -1)
		constraint		
	}
	
	def static ISLConstraint singleGreaterThan(Integer index, ISLSpace space, ISLDimType dim) {
		var constraint = ISLConstraint.buildInequality(space)
		constraint = constraint.setCoefficient(dim, index, 1)
		constraint
	}
}
