package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaVisitable
import alpha.model.Equation
import alpha.model.StandardEquation
import alpha.model.UseEquation
import alpha.model.factory.AlphaUserFactory
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.util.AlphaUtil
import java.util.LinkedList
import java.util.List
import java.util.function.Function
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * NormalizeReduction moves a reduction to a separate equation.
 * The main purpose is to have an isolated equation for applying transformations
 * on the reduction (e.g., {@link SimplifyingReductions})
 */
class NormalizeReduction extends AbstractAlphaCompleteVisitor {
	
	List<AbstractReduceExpression> targetREs = new LinkedList()
	List<StandardEquation> newEqs = new LinkedList()
	
	public static Function<StandardEquation, String> defineNormalizeReductionEquationName = [se|
		val origName = se.variable.name
		var NRname = origName + "_NR"

		AlphaUtil.duplicateNameResolverWithCounter.apply(se.systemBody.system, NRname)
	]
	
	/**
	 * Applies the transformation to the specified expression.
	 * 
	 * Returns the equation that contains the extracted ReduceExpression 
	 */
	static def apply(AbstractReduceExpression are) {
		if (AlphaUtil.getContainerEquation(are) instanceof StandardEquation) {
			transform(are)
		} else {
			throw new IllegalArgumentException("[NormalizeReduction] Expecting reductions within StandardEquations.")
		}
	}
	
	/**
	 * Applies the transformation to all applicable expressions in the given input.
	 * 
	 * When there are nested reductions, only the top level AbstractReduceExpression is transformed. 
	 * 
	 * @returns number of applications of the transformation
	 */
	static def apply(AlphaVisitable av) {
		val visitor = new NormalizeReduction()
		visitor.accept(av)
		
		visitor.targetREs.forEach[are|transform(are)]
		
		return visitor.targetREs.size
	}
	static def applyAndReport(AlphaVisitable av) {
		val visitor = new NormalizeReduction()
		visitor.accept(av)
		
		visitor.targetREs.forEach[are|transform(are)]
		
		return visitor.targetREs
	}
	
	private static def transform(AbstractReduceExpression are) {
		val equation = AlphaUtil.getContainerEquation(are) as StandardEquation
		val systemBody = equation.systemBody
		val system = systemBody.system
		
		val newEqName = defineNormalizeReductionEquationName.apply(equation)
		val newVar = AlphaUserFactory.createVariable(newEqName, are.contextDomain)
		
		system.locals.add(newVar)
		
		val varExpr = AlphaUserFactory.createVariableExpression(newVar)
		
		EcoreUtil.replace(are, varExpr)
		
		val newEq = AlphaUserFactory.createStandardEquation(newVar, are)
		
		systemBody.equations.add(newEq)
		
		AlphaInternalStateConstructor.recomputeContextDomain(equation)
		AlphaInternalStateConstructor.recomputeContextDomain(newEq)
		
		return newEq
	}
	
	/**
	 * Skip UseEquations. It is not expected to have reductions in UseEquation inputs.
	 */
	override visitUseEquation(UseEquation ue) {
		return;
	}
	
	override visitAbstractReduceExpression(AbstractReduceExpression are) {
		//Collects target reductions.
		//Does not transform on the fly to avoid concurrent mod
		//The visitor steps after first valid target, and does not fully normalize nested reductions.
		if (!(are.eContainer instanceof Equation))
			targetREs.add(are)
		else {
			super.visitAbstractReduceExpression(are)
		}
	}
	
}