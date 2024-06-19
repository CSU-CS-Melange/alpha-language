package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLSet

class PRDGNode {
	String name
	ISLSet domain

	new(String name, ISLSet domain) {
		this.name = name
		this.domain = domain
	}
	
	def String getName() { name }
	override String toString() { name + ", " + domain.toString() } 
}