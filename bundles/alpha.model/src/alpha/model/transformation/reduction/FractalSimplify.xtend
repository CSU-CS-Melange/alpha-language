package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression

import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.AlphaUtil.getContainerSystemBody
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension alpha.model.analysis.reduction.ReductionUtil.isSimilar
import alpha.model.util.Show
import org.eclipse.emf.ecore.util.EcoreUtil
import alpha.model.AlphaInternalStateConstructor
import alpha.model.transformation.RemoveUnusedEquations

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
	 * these reduce exprs belong to different systems
	 */
	def static void transform(AbstractReduceExpression smallerReduceExpr, AbstractReduceExpression reduceExpr) {
		
		// create new special reduce expr (copy of original with fractal flag set to be used by codegen)
		val fractalReduceExpr = reduceExpr.copyAE
		fractalReduceExpr.fractalSimplify = true
		
		// identify the parent/larger reduce expr in the system body of smallerReduceExpr
		val variableName = reduceExpr.getContainerEquation.name
		val targetSystemBody = smallerReduceExpr.getContainerSystemBody
		val equationInTargetSystemBody = targetSystemBody.standardEquations.findFirst[eq | eq.variable.name == variableName]
		if (equationInTargetSystemBody === null)
			throw new Exception('Failed to fractal transform, could not identify original variable')
		val targetReduceExpr = equationInTargetSystemBody.expr
		
		// replace with the fractal reduce expr
		EcoreUtil.replace(targetReduceExpr, fractalReduceExpr)
		AlphaInternalStateConstructor.recomputeContextDomain(equationInTargetSystemBody)
		
		RemoveUnusedEquations.apply(targetSystemBody.system)
	}
	
}