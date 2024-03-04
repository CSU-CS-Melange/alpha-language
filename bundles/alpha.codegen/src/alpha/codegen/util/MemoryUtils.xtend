package alpha.codegen.util

import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial

class MemoryUtils {
	
	def static ISLPWQPolynomial card(ISLSet domain) {
		BarvinokBindings.card(domain.copy)
	}
	
	
	
}