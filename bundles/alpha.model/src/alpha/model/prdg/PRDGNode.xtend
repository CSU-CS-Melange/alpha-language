package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace
import org.eclipse.emf.common.util.EList
import alpha.model.AlphaNode

class PRDGNode {
	String name
	ISLSet domain
	boolean reductionNode
	AlphaNode origin

	new(String name, ISLSet domain) {
		this.name = name
		this.domain = domain
		this.reductionNode = false
		this.origin = null
	}
	
	new(String name, ISLSet domain, boolean reduction) {
		this.name = name
		this.domain = domain
		this.reductionNode = reduction
		this.origin = null
	}
	
	new(String name, ISLSet domain, boolean reduction, AlphaNode origin) {
		this.name = name
		this.domain = domain
		this.reductionNode = reduction
		this.origin = origin
	}
	
	def String getName() { name }
	def AlphaNode getOrigin() { origin }
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