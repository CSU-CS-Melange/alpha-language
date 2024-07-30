package alpha.model.transformation

import alpha.model.AlphaSystem
import alpha.model.StandardEquation
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.VariableExpression
import alpha.model.factory.AlphaUserFactory
import alpha.model.util.AbstractAlphaCompleteVisitor
import org.eclipse.emf.ecore.util.EcoreUtil
import fr.irisa.cairn.jnimap.isl.ISLSet
import alpha.model.DependenceExpression

class Tiler extends AbstractAlphaCompleteVisitor {
	
	Variable target
	int[] tileSizes
	
	private new(AlphaSystem system, Variable variable, int[] tileSizes) {
		this.target = variable
		this.tileSizes = tileSizes 
	}
	
	static def void apply(AlphaSystem system, Variable variable, int[] tileSizes) {
//		val tiler = new Tiler(system, variable, tileSizes)
//		system.accept(tiler)
	}
	
	override inVariable(Variable variable) {
		if (variable != target) return;
		
		val newDom = null as ISLSet
		variable.domainExpr = AlphaUserFactory.createJNIDomain(newDom)
		
	}
	
	override inStandardEquation(StandardEquation se) {
		if (se.variable == target) {
			val newExpr = null as DependenceExpression
			se.expr = newExpr
		}
	}
	
	override outVariableExpression(VariableExpression ve) {
		if (ve.variable == target) {
			val newExpr = null as DependenceExpression
			EcoreUtil.replace(ve, newExpr)
			newExpr.expr = ve
		}
	}
		
	override inUseEquation(UseEquation ue) {
		super.inUseEquation(ue)	
	}
	
}