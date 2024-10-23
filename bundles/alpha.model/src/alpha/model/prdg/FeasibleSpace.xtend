package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron
import fr.irisa.cairn.jnimap.polylib.PolyLibMatrix
import alpha.model.util.AffineFunctionOperations
import fr.irisa.cairn.jnimap.polylib.PolyLibVector

class FeasibleSpace {
 	@Accessors(PUBLIC_GETTER)
	var HashMap<String, HashMap<String, String>> variables 
 	@Accessors(PUBLIC_GETTER)
 	var HashMap<String, List<String>> variableIndices
 	@Accessors(PUBLIC_GETTER)
 	var HashMap<String, Integer> indexMappings
 	@Accessors(PUBLIC_GETTER)
 	var HashMap<String, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>> mappings
 	var ISLBasicMap space
 	var String variableName
 	var int variableCount
 	var int muCount
	var int lambdaCount
 	
 	new(PRDG prdg) {
 		variables = newHashMap
		variableIndices = newHashMap
		muCount = 0
		variableCount = 0
		variableName = "i"
		indexMappings = newHashMap
		mappings = newHashMap
		
		var dom = PolyLibMatrix.createFromLongMatrix(prdg.nodes.get(0).domain.copy.basicSets.get(0).toPolyLibArray)
		println("Domain: \n" + dom)
		println("Map: "+ prdg.edges.get(0).function.copy.toBasicMap.toBasicSet)
		var map = PolyLibMatrix.createFromLongMatrix(
			AffineFunctionOperations.toMatrix(prdg.edges.get(0).function.copy).toArray
		)
		println("Map: \n" + map)
//		println("Map * Domain: \n" + )
		for(node : prdg.nodes) {
			if(!(node.name.contains("_body") || node.name.contains("_result"))) {
				var domain = node.domain.copy.coalesce
				this.addDomain(domain, node.name)
			}
		}

		for(edge : prdg.edges) { 
			if(edge.source.name.contains("_body")) {
				var fun = edge.function.copy.toMap.intersectDomain(edge.source.domain.copy)
				var constant = "l" + lambdaCount
				lambdaCount++
				var dependencies = newArrayList
				for(piece : fun.copy.toPWMultiAff.pieces) {
					for(set : piece.set.basicSets) {
						for(constraint : set.constraints) {
							var lambda = "l" + lambdaCount
							lambdaCount++
							dependencies.add(new Pair(lambda, constraint))
						}
					}
				}
				
				var array = new ArrayList()
				array.add(new Pair(edge.dest.name, new Dependence(edge.function, constant, dependencies)))
				mappings.merge(edge.source.name, new Pair(null, array),
					[ existing, n | 
						var arr = n.value;
						arr.addAll(existing.value)
						new Pair(existing.key, arr)
					])
			} else if(edge.dest.name.contains("_result")) {
				var body = prdg.edges.filter(x | x.source.name == edge.dest.name).head
				mappings.merge(body.dest.name, 
					new Pair(new Pair(edge.source.name, body.function),
					new ArrayList), [existing, n | 
						var x = existing ?: n
						new Pair(n.key, x.value)
					])
			} else if(!(edge.dest.name.contains("_body") || edge.source.name.contains("_result"))) {
				var fun = edge.function.copy.toMap.intersectDomain(edge.source.domain.copy)
				var constant = "l" + lambdaCount
				lambdaCount++
				var dependencies = newArrayList
				for(piece : fun.copy.toPWMultiAff.pieces) {
					for(set : piece.set.basicSets) {
						for(constraint : set.constraints) {
							var lambda = "l" + lambdaCount
							lambdaCount++
							dependencies.add(new Pair(lambda, constraint.copy))
						}
					}
				}
				var array = new ArrayList()
				array.add(new Pair(edge.dest.name, new Dependence(edge.function.copy, constant, dependencies)))
				mappings.merge(edge.source.name, 
					new Pair(new Pair(edge.dest.name, 
						ISLMultiAff.buildIdentity(edge.function.copy.space)
					), array),
					[ existing, n | 
						var arr = n.value;
						arr.addAll(existing.value)
						new Pair(n.key, arr)
					])
			}
		}
		space = ISLBasicMap.buildUniverse(ISLSpace.alloc(0, 0, 0))
		var muIndex = 0
		
		for(entry : variables.entrySet) {
			space = space.addOutputs(entry.value.values.toList)
			for(key : entry.value.values) {
				indexMappings.put(key, muIndex)
				var constraint = ISLConstraint.buildInequality(space.space)
				constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, muIndex, 1)
				space = space.addConstraint(constraint)
				muIndex++
				
			}
		}
		var lambdaIndex = 0
		for(variable : mappings.keySet) {
			for(dependence : mappings.get(variable).value){
				var lambdas = new ArrayList()
				lambdas.add(dependence.value.constantLambda)
				lambdas.addAll(dependence.value.lambdas.map[pair | pair.key ])
				space = space.addInputs(lambdas)
				for(key : lambdas) {
					indexMappings.put(key, lambdaIndex)
					lambdaIndex++
					var constraint = ISLConstraint.buildInequality(space.space)
					constraint = constraint.setCoefficient(ISLDimType.isl_dim_in, indexMappings.get(key), 1)
					space = space.addConstraint(constraint)
				}
			}
		}
			
		val universeConstraint = ISLConstraint.buildInequality(space.space)
		mappings.forEach[reductionNode, value |
 			value.value.forEach[ dependence |
 				var constantConstraint = ISLConstraint.buildInequality(space.space)
				constantConstraint = constantConstraint.updateConstraint(value.key.key, 1, "constant", 1)
	 			constantConstraint = updateConstraint(constantConstraint.copy, dependence.key, -1, "constant", 1)
 				val projections = value.key.value.copy.affs;
 				val reads = dependence.value.dependence.copy.affs;
 				constantConstraint = (0..<projections.size).fold(constantConstraint, [constraint, leftIndex |
					updateConstraint(constraint, value.key.key, 1, 
									variableIndices.get(value.key.key).get(leftIndex), 
									projections.get(leftIndex)
										.constantVal.copy.asLong.intValue)
					])
 				constantConstraint = (0..<reads.size).fold(constantConstraint, [constraint, rightIndex | 
					updateConstraint(constraint, dependence.key, -1, 
						variableIndices.get(dependence.key).get(rightIndex), 
						reads.get(rightIndex).constantVal.copy.asLong.intValue)
 					])
 				
 				if(!constantConstraint.isEqual(universeConstraint)) {
 					constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_in, 
 						indexMappings.get(dependence.value.constantLambda), -1)
 					constantConstraint = (constantConstraint.constant = -1)
 					space = space.addConstraint(constantConstraint)
 				}

 				space = (0..<dependence.value.dependence.copy.nbInputs).fold(space, [localSpace, index |
 					var constraint = ISLConstraint.buildInequality(localSpace.space)
 					constraint = (0..<projections.size).fold(constraint, [localConstraint, leftIndex |
 						updateConstraint(localConstraint, value.key.key, 1, 
										variableIndices.get(value.key.key).get(leftIndex), 
										projections.get(leftIndex)
											.getCoefficientVal(ISLDimType.isl_dim_in, index).copy.asLong.intValue)
					])
					
					constraint = (0..<reads.size).fold(constraint, [localConstraint, rightIndex |
						updateConstraint(localConstraint, dependence.key, -1, 
							variableIndices.get(dependence.key).get(rightIndex), 
							reads.get(rightIndex).getCoefficientVal(ISLDimType.isl_dim_in, index).copy.asLong.intValue)
					])
					
					constraint = dependence.value.lambdas.fold(constraint, [localConstraint, mapping |
						localConstraint.setCoefficient(ISLDimType.isl_dim_in, indexMappings.get(mapping.key), 
							mapping.value.getCoefficient(ISLDimType.isl_dim_out, index).intValue)
					])
	 				var output = localSpace
	 				if(!constraint.isEqual(universeConstraint)) {
 						constraint = (constraint.constant = -1)
 						output = localSpace.addConstraint(constraint)
 					} 
					output
 				])
			]
 		]
		space = space.copy.removeRedundancies
//		println(space.copy.range()	)	
 	}
 	
 	def private ISLConstraint updateConstraint(ISLConstraint constraint, String variable, int direction, String index, int coefficient) {
		var old = constraint.copy.getCoefficient(ISLDimType.isl_dim_out, 
			indexMappings.get(variables.get(variable).get(index))
		).intValue
		constraint.copy.setCoefficient(ISLDimType.isl_dim_out,
			indexMappings.get(variables.get(variable).get(index)),
 			old + direction * coefficient)
 	}
 	
 	def ISLBasicSet getSpace() {
 		space.copy.getRange
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
 	
 	def private HashMap<String, String> buildDomainCoefficients(ISLSet domain, String label, Integer count) {
		var constraints = newHashMap
		var labelCount = count		
		constraints.put("constant", label + labelCount)
		labelCount++
		for(index : domain.indexNames) {
			constraints.put(index, label + labelCount)
			labelCount++
		}
		constraints
	}
	
	def static PolyLibMatrix matMult(PolyLibMatrix x, PolyLibMatrix y) {
		var n = x.nbRows
		var m = y.nbColumns
		var output = PolyLibMatrix.allocate(n, m)
		for(var i = 0; i < n; i++) {
			for(var j = 0; j < m; j++) {
				for(var k = 0; k < y.nbRows; k++) {
					println("i, j, k: " + i + ", " + j + ", " + k)
					output.setAt(i, j, output.getAt(i, j) + x.getAt(i, k) * y.getAt(k, j))
				}				
			}
		}
		output
	}
}

class Dependence {
 	@Accessors(PUBLIC_GETTER)
	var ISLMultiAff dependence
	@Accessors(PUBLIC_GETTER)
	var String constantLambda
	@Accessors(PUBLIC_GETTER)
	var List<Pair<String, ISLConstraint>> lambdas
	
	new(ISLMultiAff dep, String const, List<Pair<String, ISLConstraint>> l) {
		dependence = dep
		constantLambda = const
		lambdas = l
	}
	
	override toString() {
		println("Dep: " + dependence + ", Constant: " + constantLambda + ", Lambdas: " + lambdas)
	}
	
}