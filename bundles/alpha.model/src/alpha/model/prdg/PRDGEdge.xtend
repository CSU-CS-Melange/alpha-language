package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet

class PRDGEdge {
	PRDGNode source
	PRDGNode dest
	ISLSet domain
	ISLMultiAff function
//	JNIFunction function
	
	new(PRDGNode source, PRDGNode dest, ISLSet domain, ISLMultiAff func) {
		this.source = source
		this.dest = dest
		this.domain = domain
		this.function = func
	}
	
	def ISLMultiAff getFunction() {
		this.function
	}
	
	def ISLSet getDomain() {
		this.domain
	}
	
	def PRDGNode getSource() {
		this.source
	}
	
	def PRDGNode getDest() {
		this.dest
	}
	
	override String toString() {
		this.source.getName + " -> " + this.dest.getName + ": " + this.function.toString + "@" + this.domain.toString()
	}
}