package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLSet

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
	def ISLSet getDomain() { domain }
	def boolean isReductionNode() { reductionNode }
}