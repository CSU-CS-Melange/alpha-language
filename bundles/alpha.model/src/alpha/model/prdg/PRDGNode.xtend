package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace

class PRDGNode {
	String name
	ISLSet domain
	boolean reductionNode

	new(String name, ISLSet domain) {
		this.name = name
		this.domain = domain
		this.reductionNode = false
	}
	
	new(String name, ISLSet domain, boolean reduction) {
		this.name = name
		this.domain = domain
		this.reductionNode = reduction
	}
	
	def String getName() { name }
	override String toString() { name + ", " + domain.toString() } 
	def ISLSet getDomain() { domain.copy }
	def ISLSpace getSpace() { getDomain.getSpace.copy }
	def ISLLocalSpace getLocalSpace() { getSpace.copy.toLocalSpace }
	def boolean isReductionNode() { reductionNode }
	override boolean equals(Object other) { 
		if(other instanceof PRDGNode) name.equals(other.getName)
		else false
	}
	override int hashCode() {name.hashCode()}
}