package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLMap

class PRDGEdge {
	PRDGNode source
	PRDGNode dest
	ISLSet domain
	ISLMap map
//	JNIFunction function
	
	new(PRDGNode source, PRDGNode dest, ISLSet domain, ISLMap map) {
		this.source = source
		this.dest = dest
		this.domain = domain
		this.map = map
	}
	
	def ISLMap getMap() {
		this.map.copy
	}
	
	def ISLSet getDomain() {
		this.domain.copy
	}
	
	def PRDGNode getSource() {
		this.source
	}
	
	def PRDGNode getDest() {
		this.dest
	}
	
	//Whether this edge is from the result of a reduction to its body
	def boolean isReductionEdge() {this.dest.isReductionNode && this.source.isReductionNode}
	
	override String toString() {
		this.source.getName + " -> " + this.dest.getName + ": " + this.map.toString + "@" + this.domain.toString()
	}
	
	override boolean equals(Object other) { 
		if(other instanceof PRDGEdge) 
			return source.equals(other.getSource) 
				&& dest.equals(other.getDest) 
				&& domain.isPlainEqual(other.getDomain)
				&& map.isPlainEqual(other.map)
		else false
	}
	
	override int hashCode() {(source.getName + dest.getName).hashCode}
}