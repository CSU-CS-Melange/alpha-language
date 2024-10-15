package alpha.model.transformation

import alpha.model.AlphaCompleteVisitable
import alpha.model.AlphaExpression
import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaVisitable
import alpha.model.BINARY_OP
import alpha.model.BinaryExpression
import alpha.model.IndexExpression
import alpha.model.ReduceExpression
import alpha.model.factory.AlphaUserFactory
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.util.AlphaOperatorUtil
import alpha.model.util.AlphaUtil
import alpha.model.util.Show
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * Simplification of Alpha programs.
 * 
 * This visitor has a similar structure to Normalize. The main difference is that
 * the transformations in Simplify are not necessary for a program to be in normal form.
 * Normalize also implements transformations that are not strictly necessary for
 * normal form, but eliminate errors. For instance, removing empty case branches 
 * is necessary to avoid errors after Normalize.
 * 
 * The simplification rules implemented are:
 *  - remove BinaryExpression when one of the operand is identity
 *  - replace IndexExpression with ConstantExpression when applicable
 *  - replace ReduceExpression with its body when it has scalar domain
 */
class SimplifyExpressions extends AbstractAlphaCompleteVisitor {
	
	
	static boolean DEBUG = false
	
	protected def debug(String ruleID, String rule) {
		if (DEBUG) println(ruleID + ": " + rule)
	}
	
	protected def debug(AlphaExpression expr) {
		if (DEBUG) println(Show.print(AlphaUtil.getContainerSystem(expr))); 
	}
	
	protected def warning(String message) {
			System.err.println(String.format("[Simplify] %s.", message))
	}
	
	protected new() {}

	static def void apply(AlphaCompleteVisitable acv) {
		if (acv instanceof AlphaVisitable) {
			apply(acv as AlphaVisitable)
		} else if (acv instanceof AlphaExpressionVisitable) {
			apply(acv as AlphaExpressionVisitable)
		} else {
			throw new RuntimeException("This should be unreachable code. Got: " + acv);
		}
	}
	
	static def void apply(AlphaVisitable av) {
		val visitor = new SimplifyExpressions();
		av.accept(visitor);
	}
	static def void apply(AlphaExpressionVisitable aev) {
		val visitor = new SimplifyExpressions();
		aev.accept(visitor);
	}
	
	override outBinaryExpression(BinaryExpression be) {
		if (AlphaOperatorUtil.isIdentity(be.operator, be.left) && be.operator != BINARY_OP.DIV && be.operator != BINARY_OP.MOD) {
			EcoreUtil.replace(be, be.right);
		} else if (AlphaOperatorUtil.isIdentity(be.operator, be.right)) {
			EcoreUtil.replace(be, be.left);
		}
	}
	
	override outIndexExpression(IndexExpression ie) {
		if (ie.function.nbOutputs == 1 && ie.function.getAff(0).isConstant) {
			val v = ie.function.getAff(0).eval(ie.expressionDomain.copy.samplePoint)
			val intExpr = AlphaUserFactory.createIntegerExpression(v.asLong.intValue);
			EcoreUtil.replace(ie, intExpr)
		}
	}
	
	override outReduceExpression(ReduceExpression re) {
		var map = re.projection.toMap
		map = map.intersectDomain(re.body.contextDomain)
		map = map.intersectRange(re.contextDomain)
		map = map.reverse

		if (map.isSingleValued) {
			val pwMultiAff = map.toPWMultiAff
			
			//TODO it can be extended to multiple pieces using case
			if (pwMultiAff.nbPieces == 1) {
				val multiAff = pwMultiAff.getPiece(0).getMaff
				val depExpr = AlphaUserFactory.createDependenceExpression(multiAff, re.body)
				EcoreUtil.replace(re, depExpr)
				
				AlphaInternalStateConstructor.recomputeContextDomain(depExpr)
			} else {
//				warning("Scalar Reduction with multiple pieces detected. It is not simplified with the current implementation.")
			}
		}
	}
	
}