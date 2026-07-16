package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaSystem
import alpha.model.CaseExpression
import alpha.model.SystemBody
import alpha.model.factory.AlphaUserFactory
import alpha.model.transformation.Normalize
import alpha.model.util.AlphaExpressionUtil
import alpha.model.util.AlphaOperatorUtil
import java.util.LinkedList
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.EcoreUtil2
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import alpha.model.util.ISLUtil.Dims
import alpha.model.util.ISLUtil

/**
 * Permutation Case-Reduce is a transformation that moves a CaseExpression
 * out of a reduction body.
 * 
 * Given a reduction of the form:
 *   reduce(op, f, case { E1; E2; } ) 
 * it returns
 *   case {
 *      D1  :  reduce(op, f, E1);
 *      D12 : (reduce(op, f, E1) op reduce(op, f, E2));
 *      D2  :  reduce(op, f, E2);
 *   }
 * 
 *  When the original CaseExpression has more than two branches,
 *  the E2 in the above is a CaseExpression with one less branch,
 *  where the first branch is "peeled out". Then, the transformation 
 *  is recursively applied to reductions over E2 until it no longer
 *  is a CaseExpression.
 */
class PermutationCaseReduce {
	
	
	private new() {}
	
	/**
	 * Apply the transformation to all AbstractReduceExpressions in an AlphaSystem.
	 * Silently ignores any AbstractReduceExpression where it is not applicable. 
	 * 
	 * @returns number of applications of the transformation
	 */	
	static def apply(AlphaSystem system) {
		system.systemBodies.map[b|apply(b)].reduce[p1,p2|p1+p2]
	}
	
	/**
	 * Apply the transformation to all AbstractReduceExpressions in a SystemBody.
	 * Silently ignores any AbstractReduceExpression where it is not applicable.
	 * 
	 * @returns number of applications of the transformation
	 */
	static def apply(SystemBody body) {
		EcoreUtil2.getAllContentsOfType(body, AbstractReduceExpression).map[are|transform(are)].reduce[p1,p2|p1+p2]
	}
	
	/**
	 * Applies Permutation Case Reduce to the specified reduction.
	 * This method throws an exception when it is not applicable to the specified expression.
	 */
	static def apply(AbstractReduceExpression are) {
		if (transform(are) < 1) {
			throw new IllegalArgumentException("[PermutationCaseReduce] Target AbstractReduceExpression must have a CaseExpression as its body.");
		}
	}
	
	/**
	 * Implementation of the transformation.
	 */
	private static def int transform(AbstractReduceExpression are) {
		if (!(are.body instanceof CaseExpression)) return 0;
		
		val innerCase = are.body as CaseExpression
		
		val E1 = innerCase.exprs.get(0)
		val E2list = innerCase.exprs.subList(1, innerCase.exprs.size)
		val recurse = (E2list.size > 1)
		
		val E2 = if (E2list.size == 1) {
			AlphaExpressionUtil.filterAutoRestrict.apply(E2list.get(0))
		} else {
			val E = AlphaUserFactory.createCaseExpression
			val domUnion = E2list.map[e|e.expressionDomain].reduce[d1, d2|d1.union(d2)]
			E.expressionDomain = domUnion
			E.exprs.addAll(E2list.map[e|AlphaExpressionUtil.filterAutoRestrict.apply(e)])
			E
		}
		
		val fDE1 = E1.expressionDomain.apply(are.projection.toMap)
		val fDE2 = E2.expressionDomain.apply(are.projection.toMap)
		
		val D12 = fDE1.copy.intersect(fDE2.copy)
		val D1 = fDE1.copy.subtract(fDE2.copy)
		val D2 = fDE2.copy.subtract(fDE1.copy)
		val D0 = are.contextDomain.copy.subtract(fDE1).subtract(fDE2)
		
		val outerCase = AlphaUserFactory.createCaseExpression
		val recurseTarget = new LinkedList<AlphaExpression>
		
		val newE1 = if (!D1.isEmpty) {
			val X1 = ReductionUtil.constructConcreteReduction(are, are.operator, are.projection, EcoreUtil.copy(E1))
			AlphaUserFactory.createRestrictExpression(D1, X1);
		}
		
		val newE2 = if (!D2.isEmpty) {
			val X2 = ReductionUtil.constructConcreteReduction(are, are.operator, are.projection, EcoreUtil.copy(E2))
			recurseTarget.add(X2)
			AlphaUserFactory.createRestrictExpression(D2, X2);
		}
		
		val newE12 = if (!D12.isEmpty) {
			val X1 = ReductionUtil.constructConcreteReduction(are, are.operator, are.projection, E1)
			val X2 = ReductionUtil.constructConcreteReduction(are, are.operator, are.projection, E2)
			recurseTarget.add(X2)
			val X12 = AlphaUserFactory.createBinaryExpression(AlphaOperatorUtil.reductionOPtoBinaryOP(are.operator), X1, X2)
			AlphaUserFactory.createRestrictExpression(D12, X12);
		}
		
		val newE0 = if(!D0.isEmpty) {
			val id = AlphaOperatorUtil.createIdentityExpression(are.operator)
			val universe = ISLSet.buildUniverse(D0.copy.space)
			val zero_dim = universe.copy.projectOut(Dims.SET, 0, universe.dim(Dims.SET))
			val maff = ISLMap.buildFromDomainAndRange(universe, zero_dim)
			val X0 = AlphaUserFactory.createDependenceExpression(ISLUtil.toMultiAff(maff), id)
			AlphaUserFactory.createRestrictExpression(D0, X0);
		}
		
		if (newE1 !== null)
			outerCase.exprs.add(newE1)
		if (newE12 !== null)
			outerCase.exprs.add(newE12)
		if (newE2 !== null)
			outerCase.exprs.add(newE2)
		if (newE0 !== null)
			outerCase.exprs.add(newE0)
		
		println(newE0)
		
		EcoreUtil.replace(are, outerCase)
		AlphaInternalStateConstructor.recomputeContextDomain(outerCase)
		
		var int Tcount = 1;
		
		if (recurse)
			for (expr : recurseTarget) {
				Tcount += transform(expr as AbstractReduceExpression)
			}
		
		Normalize.apply(outerCase)
		
		return Tcount;
	}
}