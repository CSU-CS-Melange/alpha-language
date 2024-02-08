package alpha.model.util

import alpha.model.AlphaExpression
import alpha.model.BINARY_OP
import alpha.model.BinaryExpression
import alpha.model.BooleanExpression
import alpha.model.IntegerExpression
import alpha.model.MultiArgExpression
import alpha.model.REDUCTION_OP
import alpha.model.RealExpression
import alpha.model.AbstractReduceExpression

class AlphaOperatorUtil {
	
	
	static def BINARY_OP reductionOPtoBinaryOP(REDUCTION_OP op) {
		switch (op) {
			case MIN: { BINARY_OP.MIN }
			case MAX: { BINARY_OP.MAX }
			case PROD: { BINARY_OP.MUL }
			case SUM: { BINARY_OP.ADD } 
			case AND: { BINARY_OP.AND }
			case OR: { BINARY_OP.OR }
			case XOR: { BINARY_OP.XOR }
			case EX: {
				throw new RuntimeException("[AlphaOperatorUtil] ExternalFunctions cannot be used as BinaryOP.");
			}
			
		}
	}
	
	static def boolean hasInverse(REDUCTION_OP op) {
		try {
			val invOp = reductionOPtoBinaryInverseOP(op)
			return true
		} catch (Exception e) {
			return false
		}
	}
	
	static def BINARY_OP reductionOPtoBinaryInverseOP(REDUCTION_OP op) {
		switch (op) {
			case MIN,
			case MAX, 
			case AND,
			case OR,
			case XOR: { 
				throw new RuntimeException("[AlphaOperatorUtil] Operator does not have an inverse.");
			}
			case PROD: { BINARY_OP.DIV }
			case SUM: { BINARY_OP.SUB }
			case EX: {
				throw new RuntimeException("[AlphaOperatorUtil] ExternalFunctions cannot be used as BinaryOP.");
			}
			
		}
	}
	
	static def boolean isIdentity(BINARY_OP op, AlphaExpression expr) {
		switch (op) {
			case MIN,
			case MAX: {
				//FIXME should add inf and -inf
				return false
			}
			case MUL,
			case DIV,
			case MOD: {
				if (expr instanceof IntegerExpression) {
					return expr.value == 1
				}
				if (expr instanceof RealExpression) {
					return expr.value == 1
				}
			}
			case ADD,
			case SUB: {
				if (expr instanceof IntegerExpression) {
					return expr.value == 0
				}
				if (expr instanceof RealExpression) {
					return expr.value == 0
				}
			}
			case AND: {
				if (expr instanceof BooleanExpression) {
					return expr.value
				}
			}
			case OR: {
				if (expr instanceof BooleanExpression) {
					return !expr.value
				}
			}
			default: {
				return false
			}
			
		}
		
		false
	}
	
	/**
	 * Tests if op1 distributes over op2.
	 * 
	 * isDistributiveOver(BINARY_OP.MUL, BINARY_OP.ADD) is true
	 * but
	 * isDistributiveOver(BINARY_OP.ADD, BINARY_OP.MUL) is false
	 */
	static def isDistributiveOver(BINARY_OP op1, BINARY_OP op2) {
		if (op1==BINARY_OP.MUL && op2==BINARY_OP.ADD) return true
		if (op1==BINARY_OP.MAX && op2==BINARY_OP.MIN) return true
		if (op1==BINARY_OP.MIN && op2==BINARY_OP.MAX) return true
		if (op1==BINARY_OP.ADD && op2==BINARY_OP.MAX) return true
		if (op1==BINARY_OP.ADD && op2==BINARY_OP.MIN) return true
		
		
		false	
	}
	static def isDistributiveOver(BINARY_OP op1, REDUCTION_OP op2) {
		return isDistributiveOver(op1, reductionOPtoBinaryOP(op2))
	}
	static def isDistributiveOver(REDUCTION_OP op1, REDUCTION_OP op2) {
		return isDistributiveOver(reductionOPtoBinaryOP(op1), reductionOPtoBinaryOP(op2))
	}
	
	/**
	 * Returns true if the operator is idempotent, i.e., x op x = x
	 * 
	 */
	static def isIdempotent(REDUCTION_OP op) {
		switch (op) {
			case MIN,
			case MAX,
			case AND,
			case OR: {
				true
			}
			default: {
				false
			}
		}
	}
	
	/**
	 * Returns true if the operator has a higher order operator.
	 * 
	 */
	static def hasHigherOrderOperator(REDUCTION_OP op) {
		switch (op) {
			case SUM: {
				true
			}
			default: {
				false
			}
		}
	}
	
	/**
	 * Expects BinaryExpression, MultiArgExpression, or AbstractReduceExpression and returns
	 * BINARY_OP after converting the OP if it was MultiArgExpression/AbstractReduceExpression.
	 * 
	 */
	static def dispatch getBinaryOP(AlphaExpression expr) { 
		throw new IllegalArgumentException("[AlphaOperatorUtil] Expecting BinaryExpression or MultiArgExpression.");
	}
	static def dispatch getBinaryOP(BinaryExpression expr) { expr.operator	}
	static def dispatch getBinaryOP(MultiArgExpression expr) { reductionOPtoBinaryOP(expr.operator) }
	static def dispatch getBinaryOP(AbstractReduceExpression expr) { reductionOPtoBinaryOP(expr.operator) }
}