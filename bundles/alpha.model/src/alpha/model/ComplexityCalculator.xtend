package alpha.model

import alpha.model.util.AbstractAlphaCompleteVisitor
import fr.irisa.cairn.jnimap.isl.ISLDimType

import static extension alpha.model.util.AlphaUtil.getContainerSystem
import static extension alpha.model.util.ISLUtil.dimensionality

/**
 * This class reports the overall complexity of the given node in the AST.
 * It simply visits every node in the tree and computes the dimensionality
 * (i.e., number of free dimensions) of its context domain. Then it reports
 * the max dimensionality encountered.
 * 
 * This is primarily intended to be used by the simplifying reductions 
 * implementation where we simply need to visit all standard equations and 
 * reduce expression. It currently only handles programs with a single parameter
 * and throws an exception otherwise. 
 * 
 */

class ComplexityCalculator extends AbstractAlphaCompleteVisitor {

	int maxComplexity

	def static int complexity(AlphaVisitable av) {
		val visitor = new ComplexityCalculator
		visitor.accept(av)
		return visitor.maxComplexity
	}
	
	def updateComplexity(int c) {
		if (c > maxComplexity)
			maxComplexity = c
	}
	
	override outStandardEquation(StandardEquation eq) {
		updateComplexity(eq.variable.domain.dimensionality)
	}
	
	override outUseEquation(UseEquation eq) {
		throw new UnsupportedOperationException('Complexity calculation of UseEquations not yet implemented')
	}
	
	override outReduceExpression(ReduceExpression re) {
		if (re.fractalSimplify)
			updateComplexity(1)
		else
			updateComplexity(re.body.contextDomain.dimensionality)
	}
}