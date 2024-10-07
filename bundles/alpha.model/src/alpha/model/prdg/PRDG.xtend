package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLBasicMap
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.polylib.PolyLibMatrix
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron
import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedList
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import java.util.stream.IntStream

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
	
	def boolean respectsScheduleSpace(String variable, long[] ls) {
		var feasibleSpace = new FeasibleSpace(this)
		var set = feasibleSpace.space
		println("PRDG: " + set)
		var variables = feasibleSpace.variables
		var indices = feasibleSpace.variableIndices.get(variable)
		println("Variable Indices: " + indices)
		var indexMappings = feasibleSpace.indexMappings
		var matrix = PolyLibMatrix.createFromLongMatrix(set.toPolyLibArray)
		var rays = PolyLibPolyhedron.buildFromConstraints(matrix, 10).builRaysVertices
		var edge = this.edges.filter[x | x.source.name == (variable + "_reduce0_result") && x.dest.name == (variable + "_reduce0_body")].head
		var b_row = new ArrayList()
		for(aff : edge.function.affs) {
			var long value = 0
			for(var i = 0; i < ls.length; i++) {
				value += ls.get(i) * aff.getCoefficientVal(ISLDimType.isl_dim_in, i).asLong
			}
			value += aff.getConstant
			b_row.add(value)
		}

//		println(rays)
		println("Vector: ")
		ls.forEach[i | print(i + " ")]
		println()
		println(b_row)
//		PolyLibMatrix.
		for(var row = 0; row < rays.nbRows; row++) {
			//Test if its a vertex and skip if it is
			if(rays.getAt(row, rays.nbColumns - 1) == 1) {
				
			}
			var long value = 0
			var hasVariable = false
			for(var i = 0; i < b_row.size; i++) {
				for(constraint : variables.get(variable)) {
					if(row == 0) {
						println("Info: ")
						println(b_row)
						println(constraint)
					}
					value += constraint.value.get(indices.get(i)).value * b_row.get(i)
							* rays.getAt(row, indexMappings.get(constraint.key))
					println(value)
					if(rays.getAt(row, indexMappings.get(constraint.key)) != 0) hasVariable = true			
				}
			}
			if(value >= 0 && hasVariable) {
				println("Respects Space")
				return true
			}
		}
		false
	}
	
}


