package alpha.model.prdg

import java.util.List
import java.util.LinkedList
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

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
		nodes.filter[ node | node.name.equals(name) ].get(0)
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
	
	// This function converst from our map structure to union map that
	// ISL can use to schedule based off of the causality described by the 
	// PRDG
	def ISLUnionMap generateISLPRDG() {
		if (this.islPRDG !== null) {
			return this.islPRDG
		}
		if (this.domains !== null) {
			this.generateDomains
		}
		
		this.islPRDG = ISLMap.buildEmpty(ISLSpace.copySpaceParamsForMap(domains.getSpace.copy)).toUnionMap
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
	
}
