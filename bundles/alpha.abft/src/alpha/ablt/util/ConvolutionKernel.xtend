package alpha.ablt.util

import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import java.util.Map
import java.util.Set

import static extension alpha.model.util.AffineFunctionOperations.isUniform
import alpha.model.AlphaExpression
import alpha.model.util.AShow
import fr.irisa.cairn.jnimap.isl.ISLSet

/**
 * This class is a simple wrapper around a multi-dimensional list to 
 * represent a convolution kernel.
 * 
 * For example, the following weighted sum:
 * 
 *   Y[i] = 0.332[] * X[i-1] + 0.333[] * X[i] + 0.334[] * X[i+1]
 * 
 * can be thought of as the convolution on X by the kernel [0.332, 0.333, 0.334]
 * over the context domain of the expression on the right-hand side.
 * 
 * Each convolution kernel is defined by two pieces of information:
 * 1) the convolution kernel
 * 2) the convolution domain
 * 
 */

class ConvolutionKernel {
	
	Map<ISLMultiAff, Float> kernel
	Set<Variable> variables
	AlphaExpression expr
	Boolean valid
	
	new(AlphaExpression expr) {
		kernel = newHashMap
		variables = newHashSet
		this.expr = expr
	}
	
	def build(ISLMultiAff maff, Variable variable, float value) {
		variables.add(variable)
		kernel.put(maff, value)
		true
	}
	
	def isValid() {
		if (valid === null) {			
			valid = (variables.size == 1) && kernel.keySet.map[maff | maff.isUniform].reduce[v1, v2 | v1 && v2]
		}
		valid
	}
	
	def domain() {
		if (valid) {
			return expr.contextDomain
		}
		ISLSet.buildEmpty(expr.contextDomain.space)
	}
	
	override toString() {
		AShow.print(expr)
	}
}