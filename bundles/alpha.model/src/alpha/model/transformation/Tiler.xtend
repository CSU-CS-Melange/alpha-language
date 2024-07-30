package alpha.model.transformation

import alpha.model.AlphaSystem
import alpha.model.DependenceExpression
import alpha.model.StandardEquation
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.VariableExpression
import alpha.model.factory.AlphaUserFactory
import alpha.model.util.AbstractAlphaCompleteVisitor
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension alpha.model.util.ISLUtil.toISLSet

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
		
		val paramStr = '[' + variable.domain.copy.params.paramNames.join(',') + ']'
		val pointIndexNames = variable.domain.indexNames
		val tileIndexNames = pointIndexNames.map[i | 't' + i]
		val indexNames = pointIndexNames + tileIndexNames
		
		val constraints = variable.
		
		val newDom = '''«paramStr»->{«indexNames.join(',')» : «constraints»}'''.toString.toISLSet
		
		
		
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