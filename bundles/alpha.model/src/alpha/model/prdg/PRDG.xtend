package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import java.util.Set
import java.util.HashSet

class PRDG {
	Set<PRDGNode> nodes
	Set<PRDGEdge> edges
	ISLUnionSet domains
	ISLUnionMap islPRDG
	
	new() {
		nodes = new HashSet()
		edges = new HashSet()
	}

	def PRDGNode getNode(String name) {
		nodes.findFirst[ it.name.equals(name) ]
	}
	
	def Set<PRDGNode> getNodes() {
		this.nodes
	}
	
	def Set<PRDGEdge> getEdges() {
		this.edges
	}
	
	def addNode(PRDGNode node) {
		if(!nodes.contains(node)) nodes.add(node)
	}
	
	def setNodes(Set<PRDGNode> nodes) {
		this.nodes = nodes
	}
	
	def addEdge(PRDGEdge edge) {
		if(!edges.contains(edge)) edges.add(edge)
	}
	
	def setEdges(Set<PRDGEdge> edges) {
		this.edges = edges
	}
	
	def ISLUnionSet generateDomains() {
		if (this.domains !== null) {
			return this.domains.copy
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
		this.domains.copy
	}
	
	/**
	 * Collapses all reduction nodes.
	 * Since the schedule for reduction nodes goes unused, there is no reason
	 * not to call this function at the time of writing this comment.
	 */
	def void inlineReductions() {
		while(nodes.exists[isReductionNode]) {
			val reduction = nodes.findFirst[isReductionNode]
			val e1 = edges.findFirst[dest == reduction]
			
			val newEdges = edges.filter[source == reduction]
				.map[e2 | new PRDGEdge(e1.source, e2.dest, e1.map.applyRange(e2.map))]
			
			edges = (
				edges.reject[dest == reduction || source == reduction] +
				newEdges
			).toSet
			
			nodes.remove(reduction)
		}
	}
	
	// This function converst from our map structure to union map that
	// ISL can use to schedule based off of the causality described by the 
	// PRDG
	def ISLUnionMap generateISLPRDG() {
		if (this.islPRDG !== null) {
			return this.islPRDG.copy
		}
		if (this.domains !== null) {
			this.generateDomains
		}
		
		this.islPRDG = ISLMap.buildEmpty(ISLSpace.copySpaceParamsForMap(domains.getSpace.copy)).toUnionMap
		for (PRDGEdge edge : this.getEdges) {
			var map = edge.getMap
			
			map = map.setTupleName(ISLDimType.isl_dim_out, edge.dest.name)
			map = map.setTupleName(ISLDimType.isl_dim_in, edge.source.name)
					
			this.islPRDG = islPRDG.union(map.copy.toUnionMap)
		}		
		
		this.islPRDG.copy
	}
	
	override boolean equals(Object other) {
		if(other instanceof PRDG) nodes.equals(other.getNodes) && edges.equals(other.getEdges)
		else false
	}
	
	override int hashCode() {nodes.hashCode() + 37 * edges.hashCode()}
	
	override String toString() {
		return "Nodes: \n\t"
		 + nodes.join("\n\t")
		 + "\nEdges: \n\t"
		 + edges.join("\n\t")
	}
}
