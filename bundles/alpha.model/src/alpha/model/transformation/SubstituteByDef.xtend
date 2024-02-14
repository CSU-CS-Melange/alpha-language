package alpha.model.transformation

import alpha.model.AlphaExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaSystem
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.VariableExpression
import alpha.model.factory.AlphaUserFactory
import alpha.model.util.AbstractAlphaCompleteVisitor
import org.eclipse.emf.ecore.util.EcoreUtil
import alpha.model.util.AlphaUtil

/**
 * Replaces a VariableExpression with the definition of the corresponding StandardEquation.
 * Variables defined through UseEquations cannot be substituted.
 * 
 * When the target equation is specified, the substitution only happens in the definition
 * of the target. Otherwise, all occurrences in an AlphaSystem (or SystemBody) are substituted.
 * 
 * Given
 *  X = Y;
 *  Y = E;
 * substituting Y in X gives:
 *  X = D : E
 * where D is the domain of Y. 
 * 
 */
class SubstituteByDef extends AbstractAlphaCompleteVisitor  {
	protected StandardEquation substituteEquation
	
	private new(StandardEquation substituteEquation) {
		this.substituteEquation = substituteEquation
	}
	
	static def void apply(AlphaSystem system, Variable substituteVar) {
		system.systemBodies.forEach[body|apply(body, substituteVar)]
	}
	
	static def void apply(AlphaSystem system, StandardEquation targetEq, Variable substituteVar) {
		system.systemBodies.forEach[body|apply(targetEq, substituteVar)]
	}
	
	static def void apply(SystemBody body, Variable substituteVar) {
		val subEq = body.getStandardEquation(substituteVar)
		val visitor = new SubstituteByDef(subEq)
		
		visitor.accept(body)
	}
	
	static def void apply(StandardEquation targetEq, Variable substituteVar) {
		
		val body = AlphaUtil.getContainerSystemBody(targetEq)
		val subEq = body.getStandardEquation(substituteVar)
		val visitor = new SubstituteByDef(subEq)
		
		visitor.accept(targetEq)
	}
	
	static def void apply(AlphaExpression targetExpr, Variable substituteVar) {
		
		val body = AlphaUtil.getContainerSystemBody(targetExpr)
		val subEq = body.getStandardEquation(substituteVar)
		val visitor = new SubstituteByDef(subEq)
		
		visitor.accept(targetExpr)
	}
	
	override visitUseEquation(UseEquation ue) {
		//stop visiting
		return
	}
	
	override outVariableExpression(VariableExpression ve) {
		try {
			val b = ve.variable == substituteEquation.variable
		} catch (Exception e) {
			println
		}
		if (ve.variable == substituteEquation.variable) {
			val substituteExpr = AlphaUserFactory.createRestrictExpression(substituteEquation.variable.domain, EcoreUtil.copy(substituteEquation.expr))
			EcoreUtil.replace(ve, substituteExpr)
			AlphaInternalStateConstructor.recomputeContextDomain(substituteExpr)
		}
	}

	
}