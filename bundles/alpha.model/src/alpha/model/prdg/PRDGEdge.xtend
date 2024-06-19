package alpha.model.prdg

import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLMultiAff

class PRDGEdge {
	PRDGNode source
	PRDGNode dest
	ISLSet domain
	ISLMultiAff function
	
	new(PRDGNode source, PRDGNode dest, ISLSet domain, ISLMultiAff func) {
		this.source = source
		this.dest = dest
		this.domain = domain
		this.function = func
	}
	
	override String toString() {
		this.source.getName + " -> " + this.dest.getName + ": " + this.function.toString + "@" + this.domain.toString()
	}
}