package alpha.abft.analysis

import alpha.ablt.util.ConvolutionKernel
import alpha.model.AlphaExpression
import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaVisitable
import alpha.model.BinaryExpression
import alpha.model.DependenceExpression
import alpha.model.IntegerExpression
import alpha.model.RealExpression
import alpha.model.VariableExpression
import alpha.model.util.AShow
import alpha.model.util.AbstractAlphaCompleteVisitor
import java.util.List

/**
 * This class tries to identify the presence of convolutions in the program.
 * This is done by pattern matching on scalar sums of products (SoPs) of the form:
 * 
 *   c1[]*(f1@X) + c2[]*(f2@X) + ... + cN[]*(fN@X))
 * 
 * where each term is a BinaryExpression of a constant and the same variable.
 * All of the variable accesses (e.g., f1, f2, etc.) must be uniform functions.
 * 
 * The NormalizedScalarSumOfProducts transformation should be used before this
 * to expose any SoPs.
 * 
 */
class ConvolutionDetector extends AbstractAlphaCompleteVisitor {
	
	List<ConvolutionKernel> convolutions
	
	new() {
		convolutions = newLinkedList
	}
	
	/////////////////////////////////////////////////////////
	// Entry points
	/////////////////////////////////////////////////////////
	
	static def apply(AlphaVisitable av) {
		val visitor = new ConvolutionDetector
		av.accept(visitor)
		
		return visitor.convolutions
	}
	
	static def apply(AlphaExpressionVisitable aev) {
		val visitor = new ConvolutionDetector
		aev.accept(visitor)
		
		return visitor.convolutions
	}
	
	
	override visitBinaryExpression(BinaryExpression be) {
		inBinaryExpression(be)
		matchConvolution(be)
	}
	
	/*
	 * Matches on binary expression trees where each term is a product.
	 * And each product is a constant times a variable read by a uniform
	 * function. The relative position of the terms does not matter.
	 */
	def matchConvolution(BinaryExpression be) {
		val kernel = new ConvolutionKernel(be)
		val matched = convolutionRules(kernel, be.left, be.right)
		if (matched && kernel.isValid) {
			
			convolutions.add(kernel)
		}
	}
	
	// be be
	def dispatch boolean convolutionRules(ConvolutionKernel kernel, BinaryExpression lbe, BinaryExpression rbe) {
		convolutionRules(kernel, lbe.left, lbe.right) && convolutionRules(kernel, rbe.left, rbe.right) 
	}
	
	// dep dep
	def dispatch boolean convolutionRules(ConvolutionKernel kernel, DependenceExpression left, DependenceExpression right) {
		convolutionRules(kernel, left, left.expr, right, right.expr)
	}
	def dispatch boolean convolutionRules(ConvolutionKernel kernel, DependenceExpression left, VariableExpression ve, DependenceExpression right, RealExpression re) {
		kernel.build(left.function, ve.variable, re.value)
	}
	def dispatch boolean convolutionRules(ConvolutionKernel kernel, DependenceExpression left, VariableExpression ve, DependenceExpression right, IntegerExpression ie) {
		kernel.build(left.function, ve.variable, ie.value)
	}
	def dispatch boolean convolutionRules(ConvolutionKernel kernel, DependenceExpression left, RealExpression re, DependenceExpression right, VariableExpression ve) {
		kernel.build(right.function, ve.variable, re.value)
	}
	def dispatch boolean convolutionRules(ConvolutionKernel kernel, DependenceExpression left, IntegerExpression ie, DependenceExpression right, VariableExpression ve) {
		kernel.build(right.function, ve.variable, ie.value)
	}
	def dispatch boolean convolutionRules(ConvolutionKernel kernel, DependenceExpression left, AlphaExpression lae, DependenceExpression right, AlphaExpression rae) {
		false
	}
	
	// ae ae
	def dispatch convolutionRules(ConvolutionKernel kernel, AlphaExpression left, AlphaExpression right) { 
		false
	}
	
}