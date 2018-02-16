/*
 * generated by Xtext 2.13.0
 */
package alpha.model.ui.labeling

import alpha.model.AlphaSystem
import alpha.model.ArgReduceExpression
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryCalculatorExpression
import alpha.model.BinaryExpression
import alpha.model.CaseExpression
import alpha.model.ConstantExpression
import alpha.model.DependenceExpression
import alpha.model.ExternalArgReduceExpression
import alpha.model.ExternalMultiArgExpression
import alpha.model.ExternalReduceExpression
import alpha.model.IfExpression
import alpha.model.IndexExpression
import alpha.model.MultiArgExpression
import alpha.model.PolyhedralObject
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.SelectExpression
import alpha.model.StandardEquation
import alpha.model.UnaryExpression
import alpha.model.Variable
import alpha.model.VariableExpression
import com.google.inject.Inject
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider
import alpha.model.JNIFunctionInArrayNotation

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class AlphaLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	// Labels and icons can be computed like this:
	
//	def text(Greeting ele) {
//		'A greeting to ' + ele.name
//	}
//
//	def image(Greeting ele) {
//		'Greeting.gif'
//	}

	def text(Variable v) {
		v.name + ' : ' + v.domain.toString
	}
	
	def text(AlphaSystem system) {
		system.name + ' ' + system.parameterDomain.toString
	}
	
	def text(PolyhedralObject pobj) {
		pobj.name  + ' = ' + pobj.ISLObject.toString
	}
	
	def text(BinaryCalculatorExpression bce) {
		bce.operator.literal
	}
	
	def text(StandardEquation seq) {
		seq.variable.name + ' = '
	}
	
	
	def text(RestrictExpression re) {
		if (re.domainExpr !== null) {
			re.domainExpr.plainToString
		} else {
			'restrict with null domain'
		}
	}
	def text(AutoRestrictExpression are) {
		'auto'
	}
	def text(CaseExpression cexpr) {
		if (cexpr.name !== null && cexpr.name.length > 0) {
			'case (' + cexpr.name ')'
		} else {
			'case'
		}
	}
	def text(IfExpression ifexpr) {
		'if-then-else'
	}
	def text(DependenceExpression dep) {
		if (dep.function instanceof JNIFunctionInArrayNotation) {
				(dep.expr as VariableExpression).variable.name + dep.function.plainToString 
		} else {
			if (dep.expr instanceof VariableExpression) {
				dep.function.plainToString + ' @ ' + (dep.expr as VariableExpression).variable.name
			} else {
				dep.function.plainToString
			}	
		}
	}
	def text(ReduceExpression reduce) {
		'reduce ' + reduce.operator.literal
	}
	def text(ExternalReduceExpression reduce) {
		'reduce ' + reduce.externalFunction.name
	}
	def text(ArgReduceExpression reduce) {
		'argreduce ' + reduce.operator.literal
	}
	def text(ExternalArgReduceExpression reduce) {
		'argreduce ' + reduce.externalFunction.name
	}
	def text(UnaryExpression ue) {
		ue.operator.literal
	}
	def text(BinaryExpression be) {
		be.operator.literal
	}
	def text(MultiArgExpression mae) {
		mae.operator.literal
	}
	def text(ExternalMultiArgExpression emae) {
		emae.externalFunction.name
	}
	def text(SelectExpression se) {
		'select'
	}
	def text(IndexExpression ie) {
		'val ' + ie.function.plainToString
	}
	def text(VariableExpression ve) {
		ve.variable.name
	}
	def text(ConstantExpression ce) {
		ce
	}
}