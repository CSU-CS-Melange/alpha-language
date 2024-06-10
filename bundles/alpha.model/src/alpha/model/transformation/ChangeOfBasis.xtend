package alpha.model.transformation

import alpha.model.AlphaSystem
import alpha.model.StandardEquation
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.VariableExpression
import alpha.model.factory.AlphaUserFactory
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.util.AffineFunctionOperations
import org.eclipse.emf.ecore.util.EcoreUtil
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff

class ChangeOfBasis extends AbstractAlphaCompleteVisitor {
	
	private new(AlphaSystem system, Variable variable, ISLMultiAff f) {
		CoBfunction = f;
		target = variable;
		
		CoBfunctionInverse = AffineFunctionOperations.inverseInContext(CoBfunction, variable.domain, null)
	}
	
	ISLMultiAff CoBfunction;
	ISLMultiAff CoBfunctionInverse;
	Variable target;
	
	static def void apply(AlphaSystem system, Variable variable, ISLMultiAff f) {
		val CoB = new ChangeOfBasis(system, variable, f);
		system.accept(CoB);
	}
	
	override inVariable(Variable variable) {
		if (variable == target) {
			val newDom = variable.domain.apply(CoBfunction.copy.toMap)
			variable.domainExpr = AlphaUserFactory.createJNIDomain(newDom)
		}
	}
	
	override inStandardEquation(StandardEquation se) {
		if (se.variable == target) {
			val newExpr = AlphaUserFactory.createDependenceExpression(CoBfunctionInverse.copy, se.expr);
			se.expr = newExpr
		}
	}
	
	override outVariableExpression(VariableExpression ve) {
		if (ve.variable == target) {
			val newExpr = AlphaUserFactory.createDependenceExpression(CoBfunction.copy);
			EcoreUtil.replace(ve, newExpr);
			newExpr.expr = ve
		}
	}
		
	override inUseEquation(UseEquation ue) {
		super.inUseEquation(ue)	
	}
	
}