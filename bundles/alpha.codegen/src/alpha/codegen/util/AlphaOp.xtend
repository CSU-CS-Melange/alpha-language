package alpha.codegen.util

import alpha.codegen.C_UNARY_OP
import alpha.codegen.C_BINARY_OP
import alpha.codegen.C_REDUCTION_OP
import alpha.model.UNARY_OP
import alpha.model.BINARY_OP
import alpha.model.REDUCTION_OP
import alpha.model.UnaryExpression
import alpha.model.BinaryExpression
import alpha.model.MultiArgExpression
import alpha.model.ReduceExpression

class AlphaOp {
	
	def static C_UNARY_OP toCUnaryOp(UNARY_OP op) {
		switch op {
			case UNARY_OP.NOT : C_UNARY_OP.NOT
			case UNARY_OP.NEGATE : C_UNARY_OP.NEGATE
			default : throw new Exception(op + ' operator does not have a C_UNARY_OP definition') 
		}
	}
	
	def static C_BINARY_OP toCBinaryOp(BINARY_OP op) {
		switch op {
			case BINARY_OP.MAX : C_BINARY_OP.MAX
			case BINARY_OP.MUL : C_BINARY_OP.MUL
			case BINARY_OP.DIV : C_BINARY_OP.DIV
			case BINARY_OP.MOD : C_BINARY_OP.MOD
			case BINARY_OP.ADD : C_BINARY_OP.ADD
			case BINARY_OP.SUB : C_BINARY_OP.SUB
			case BINARY_OP.AND : C_BINARY_OP.AND
			case BINARY_OP.OR : C_BINARY_OP.OR
			case BINARY_OP.XOR : C_BINARY_OP.XOR
			case BINARY_OP.EQ : C_BINARY_OP.EQ
			case BINARY_OP.NE : C_BINARY_OP.NE
			case BINARY_OP.GE : C_BINARY_OP.GE
			case BINARY_OP.GT : C_BINARY_OP.GT
			case BINARY_OP.LE : C_BINARY_OP.LE
			case BINARY_OP.LT : C_BINARY_OP.LT
			default : throw new Exception(op + ' operator does not have a C_BINARY_OP definition')
		}
	}
	
	def static C_REDUCTION_OP toCReductionOp(REDUCTION_OP op) {
		switch op {
			case REDUCTION_OP.MIN : C_REDUCTION_OP.MIN
			case REDUCTION_OP.MAX : C_REDUCTION_OP.MAX
			case REDUCTION_OP.PROD : C_REDUCTION_OP.PROD
			case REDUCTION_OP.SUM : C_REDUCTION_OP.SUM
			case REDUCTION_OP.AND : C_REDUCTION_OP.AND
			case REDUCTION_OP.OR : C_REDUCTION_OP.OR
			case REDUCTION_OP.XOR : C_REDUCTION_OP.XOR
			default : throw new Exception(op + ' operator does not have a C_REDUCTION_OP definition')
		}
	}
	
	def static C_UNARY_OP cOperator(UnaryExpression ue) {
		ue.operator.toCUnaryOp
	}
	
	def static C_BINARY_OP cOperator(BinaryExpression be) {
		be.operator.toCBinaryOp
	}
	
	def static C_REDUCTION_OP cOperator(MultiArgExpression mae) {
		mae.operator.toCReductionOp
	}
	
	def static C_REDUCTION_OP cOperator(ReduceExpression re) {
		re.operator.toCReductionOp
	}
}