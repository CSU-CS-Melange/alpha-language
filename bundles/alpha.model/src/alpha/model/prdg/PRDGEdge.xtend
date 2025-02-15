package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLMap

class PRDGEdge {
	PRDGNode source
	PRDGNode dest
	ISLMap map
	
	new(PRDGNode source, PRDGNode dest, ISLSet domain, ISLMap map) {
		this.source = source
		this.dest = dest
		this.map = map.copy.intersectDomain(domain.copy)
	}
	
	new(PRDGNode source, PRDGNode dest, ISLMap map) {
		this.source = source
		this.dest = dest
		this.map = map
	}
	
	def ISLMap getMap() {
		this.map.copy
	}
	
	def ISLSet getDomain() {
		this.map.getDomain.copy
	}
	
	def ISLSet getRange() {
		this.map.getRange.copy
	}
	
	def PRDGNode getSource() {
		this.source
	}
	
	def PRDGNode getDest() {
		this.dest
	}
	
	//Whether this edge is from a variable to a reduction
	def boolean isReductionEdge() {this.dest.isReductionNode}
	
	override String toString() {
		this.source.getName + " -> " + this.dest.getName + ": " + this.map.toString
	}
	
	override boolean equals(Object other) { 
		if(other instanceof PRDGEdge) 
			return source.equals(other.getSource) 
				&& dest.equals(other.getDest)
				&& map.isPlainEqual(other.map)
		else false
	}
	
	override int hashCode() {(source.getName + dest.getName).hashCode}
}