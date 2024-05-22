package alpha.model

import alpha.model.util.AbstractAlphaCompleteVisitor
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import fr.irisa.cairn.jnimap.isl.ISLSet

class PointsVisitedCalculator extends AbstractAlphaCompleteVisitor {
	var ISLPWQPolynomial complexity = null
	
	def static ISLPWQPolynomial complexity(AlphaVisitable av) {
		val calculator = new PointsVisitedCalculator
		calculator.accept(av)
		return calculator.complexity
	}
	
	override outStandardEquation(StandardEquation eq) {
		if (eq.expr instanceof ReduceExpression) {
			val reduce = eq.expr as ReduceExpression
			reduce.body.contextDomain.addComplexity
		} else {
			eq.expr.contextDomain.addComplexity
		}
	}
	
	def addComplexity(ISLSet domain) {
		val pointsVisited = BarvinokBindings.card(domain)
		if (complexity === null) {
			complexity = pointsVisited
		} else {
			complexity = complexity.add(pointsVisited)
		}
	}
	
	override outUseEquation(UseEquation eq) {
		throw new Exception("Use equations are not supported yet.")
	}
	
	override outReduceExpression(ReduceExpression expr) {
		// Make sure the reduce expression is a child of a standard equation.
		if (!(expr.eContainer instanceof StandardEquation)) {
			throw new Exception("Reduce expressions must be the direct child of a standard equation.")
		}
	}
}