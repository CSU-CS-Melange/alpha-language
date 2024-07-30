package alpha.abft.codegen.util

import fr.irisa.cairn.jnimap.isl.ISLSchedule
import java.util.HashMap
import alpha.model.AlphaExpression

class AlphaSchedule {
	
	val ISLSchedule islSchedule
	val HashMap<String, AlphaExpression> exprStmtMap
	
	new(ISLSchedule islSchedule, HashMap<String, AlphaExpression> exprStmtMap) {
		this.islSchedule = islSchedule
		this.exprStmtMap = exprStmtMap
	}
	
	def schedule() {
		islSchedule
	}
	
	def exprStmtMap() {
		exprStmtMap
	}
}