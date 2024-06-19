package alpha.model.prdg

import java.util.List
import java.util.LinkedList

class PRDG {
	List<PRDGNode> nodes
	List<PRDGEdge> edges
	
	new() {
		nodes = new LinkedList()
		edges = new LinkedList()
	}

	def PRDGNode getNode(String name) {
		nodes.filter[ node | node.getName().equals(name) ].get(0)
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
	
}
