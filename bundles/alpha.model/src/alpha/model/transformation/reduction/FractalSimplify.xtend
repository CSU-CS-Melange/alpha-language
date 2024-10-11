package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression

import static extension alpha.model.util.ISLUtil.dimensionality
import static extension alpha.model.analysis.reduction.ReductionUtil.isSimilar

/**
 * Given two reductions whose bodies are homothetic scalings of each other, express the larger 
 * reduction as a recursive subsystem of itself.
 * 
 * 1) define a new system 
 */

class FractalSimplify {
	
	public static boolean DEBUG = true
	
	private static def void debug(String msg) {
		if (DEBUG)
			println("[FractalSimplify] " + msg)
	}
	
	def static void apply(AbstractReduceExpression smallerReduceExpr, AbstractReduceExpression reduceExpr) {
		if (!reduceExpr.isSimilar(smallerReduceExpr) || reduceExpr.body.contextDomain.dimensionality != 2) return;
		
		transform(reduceExpr, smallerReduceExpr)
	}
	
	/**
	 * Assumes that the two reduce expressions are a homothetic scaling of eachother
	 */
	def static void transform(AbstractReduceExpression smallerReduceExpr, AbstractReduceExpression reduceExpr) {
		
		debug('asdf')
		throw new Exception('fractal transform not yet implemented')
			
	}
	
}