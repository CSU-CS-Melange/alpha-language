package alpha.model.prdg

import alpha.model.AlphaSystem
import alpha.model.StandardEquation
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace

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
	
	/**
	 * Gets the Variable which corresponds to this node.
	 * In the case of reductions, this is the variable which contains it.
	 */
	def Variable getOriginVariable(AlphaSystem sys) {
		if(reductionNode) {
			val varName = name.substring(0,this.name.lastIndexOf("_reduce"))
			return sys.getVariable(varName)
		} else return sys.getVariable(name)
	}
	
	/**
	 * Gets the standard equation that this node originates from.
	 */
	def StandardEquation getOriginEquation(AlphaSystem sys) {
		return sys.systemBodies.map[ body | 
			body.getStandardEquation(getOriginVariable(sys))
		].findFirst[ se | se !== null]
	}
	
	override boolean equals(Object other) { 
		if(other instanceof PRDGNode) name.equals(other.getName)
		else false
	}
	override int hashCode() {name.hashCode()}
}