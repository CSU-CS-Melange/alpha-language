package alpha.abft.analysis

import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors

class Convolution {
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val ISLSet domain
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val int radius
	
	@Accessors(PUBLIC_GETTER, PROTECTED_SETTER)
	val Map<List<Integer>,Double> coeffs
	
	new(ISLSet domain, int radius, Map<List<Integer>,Double> coeffs) {
		this.domain = domain
		this.radius = radius
		this.coeffs = coeffs
	}
	
}