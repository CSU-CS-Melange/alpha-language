package alpha.model.transformation

import alpha.model.AlphaSystem
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.VariableExpression

class SubstituteAll {
	def static apply(AlphaSystem system, boolean substituteReductions) {
		if (system.systemBodies.size != 1) throw new Exception("Only systems with 1 body are supported.")
		val systemBody = system.systemBodies.get(0)
		
		while(systemBody.getNextSubstitute(substituteReductions) !== null) {
			var toSubstitute = systemBody.getNextSubstitute(substituteReductions)
			SubstituteByDef.apply(systemBody, toSubstitute.variable)
			RemoveUnusedEquations.apply(system)
		}
	}
	
	def static getNextSubstitute(SystemBody body, boolean substituteReductions) {
		body.equations
			.filter(typeof(StandardEquation))
			.filter[isLocalEquation]
			.reject[isSelfReferential]
			.reject[isReduction(substituteReductions)]
			.head
	}
	
	def static isLocalEquation(StandardEquation eq) {
		eq.variable.isLocal
	}
	
	def static isSelfReferential(StandardEquation eq) {
		eq.expr.eAllContents.filter(typeof(VariableExpression)).map[it.variable].exists[it.name.equals(eq.variable.name)]
	}
	
	def static isReduction(StandardEquation eq, boolean substituteReductions) {
		(!substituteReductions) && (eq.expr instanceof ReduceExpression)
	}
}