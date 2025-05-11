package alpha.model.transformation

import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.AlphaSystem
import alpha.model.StandardEquation
import alpha.model.VariableExpression
import alpha.model.Variable
import java.util.List
import java.util.ArrayList

/**
 * Based on the transformation of the same name in AlphaZ V1.
 * Inlines all variable equations that do not have self-dependences
 * and do not define input or output variables.
 */
class InlineAll extends AbstractAlphaCompleteVisitor {
	var boolean selfDependence
	var Variable currentVar
	var List<Variable> validVars
	
	static def void apply(AlphaSystem system) {
		var canInline = true
		
		while(canInline) {
			val visitor = new InlineAll(system)
			visitor.accept(system)
			
			if(!visitor.validVars.empty) {
				println(visitor.validVars)
				SubstituteByDef.apply(system, visitor.validVars.get(0))
				RemoveUnusedEquations.apply(system)
			} else canInline = false
		}
	}
	
	new(AlphaSystem system) {
		validVars = new ArrayList<Variable>
	}
	
	override void inStandardEquation(StandardEquation se) {
		currentVar = se.variable
		selfDependence = false
	}
	
	override void outStandardEquation(StandardEquation se) {
		if(!selfDependence && !currentVar.isOutput && !currentVar.isInput) 
			validVars.add(currentVar)
	}
	
	override void outVariableExpression(VariableExpression ve) {
		if(ve.variable.name == currentVar.name) selfDependence = true
	}
}