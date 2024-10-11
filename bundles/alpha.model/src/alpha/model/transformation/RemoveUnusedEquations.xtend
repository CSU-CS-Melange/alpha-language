package alpha.model.transformation

import alpha.model.AlphaRoot
import alpha.model.AlphaSystem
import alpha.model.Variable
import alpha.model.VariableExpression
import java.util.HashMap
import org.eclipse.xtext.EcoreUtil2
import alpha.model.StandardEquation

import static extension alpha.model.util.AlphaUtil.getContainerEquation

/**
 * Removes local equations/variables that are defined 
 * but are never used in a system.
 */
class RemoveUnusedEquations {
	
	private new() {}
	
	
	static def void apply(AlphaRoot root) {
		root.systems.forEach[s|apply(s)]
	}
	
	static def void apply(AlphaSystem system) {
		var int nbEquationsBefore
		var int nbEquationsAfter
		do {
			nbEquationsBefore = EcoreUtil2.getAllContentsOfType(system, StandardEquation).size
			system.transform
			nbEquationsAfter = EcoreUtil2.getAllContentsOfType(system, StandardEquation).size
		} while(nbEquationsBefore != nbEquationsAfter)
	
	}
	static def void transform(AlphaSystem system) {
		val map = new HashMap<Variable, Boolean>();
		
		EcoreUtil2.getAllContentsOfType(system, VariableExpression).
			filter[ve|ve.variable.isLocal].
			forEach[ve|
				val eq = ve.getContainerEquation as StandardEquation
				if (eq.variable != ve.variable)
					map.put(ve.variable, true)
			]

		val unusedVars = system.locals.stream.filter[v|!map.containsKey(v)].toArray
		val unusedEqs = EcoreUtil2.getAllContentsOfType(system, StandardEquation).filter[eq|unusedVars.contains(eq.variable)]

		system.locals.removeAll(unusedVars)
		system.systemBodies.forEach[body|body.equations.removeAll(unusedEqs)]
	}
}