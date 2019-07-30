package alpha.model.transformation

import alpha.model.AlphaExpression
import alpha.model.AlphaSystem
import alpha.model.AutoRestrictExpression
import alpha.model.RestrictExpression
import alpha.model.SystemBody
import alpha.model.factory.AlphaUserFactory
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.EcoreUtil2
import alpha.model.AlphaInternalStateConstructor
import fr.irisa.cairn.jnimap.isl.ISLSet

/**
 * Transforms a RestrictExpression with restrict domain being unions of polyhedra
 * in to a CaseExpression forcing restrict domains to be a single polyhedron.
 * 
 * The transformation takes a RestrictExpression:
 *   {D1 \/ D2} : E
 * and transforms it to:
 *   case {
 *      D1 : E;
 *      D2 : E;
 *   }
 * 
 * If the restrict domain is a union of more than two polyhedra, the number 
 * of branches in the resulting CaseExpression increases accordingly.
 */
class SplitUnionIntoCase {
	
	
	private new() {}
	
	/**
	 * Apply the transformation to all RestrictExpressions in an AlphaSystem.
	 * Silently ignores any RestrictExpressions where it is not applicable.
	 * 
	 * @returns number of applications of the transformation 
	 */	
	static def apply(AlphaSystem system) {
		system.systemBodies.map[b|apply(b)].reduce[p1,p2|p1+p2]
	}
	
	/**
	 * Apply the transformation to all RestrictExpressions in a SystemBody.
	 * Silently ignores any RestrictExpressions where it is not applicable.
	 * 
	 * @returns number of applications of the transformation
	 */
	static def apply(SystemBody body) {
		(EcoreUtil2.getAllContentsOfType(body, RestrictExpression) + 
		 EcoreUtil2.getAllContentsOfType(body, AutoRestrictExpression)
		).map[are|transform(are)].reduce[p1, p2|p1+p2]
	}
	
	/**
	 * Applies SplitUnionIntoCase to the specified restrict expression.
	 * This method throws an exception when it is not applicable to the specified expression.
	 */
	static def apply(RestrictExpression re) {
		if (transform(re) < 1) {
			throw new IllegalArgumentException("[SplitUnionIntoCase] Target RestrictExpression must have unions of polyhedra as its restrict domain.");
		}
	}
	
	/**
	 * Applies SplitUnionIntoCase to the specified restrict expression.
	 * This method throws an exception when it is not applicable to the specified expression.
	 */
	static def apply(AutoRestrictExpression are) {
		if (transform(are) < 1) {
			throw new IllegalArgumentException("[SplitUnionIntoCase] Target AutoRestrictExpression must have unions of polyhedra as its (inferred) restrict domain.");
		}
	}
	
	
	/**
	 * Implementation of the transformation.
	 */
	private static def transform(AlphaExpression parent, ISLSet restrictDomain, AlphaExpression child) {
		val caseExpr = AlphaUserFactory.createCaseExpression
		
		val disjointDomains = restrictDomain.makeDisjoint
		
		if (disjointDomains.nbBasicSets == 1) return 0;
		
		disjointDomains.basicSets.forEach[bs|
			val re = AlphaUserFactory.createRestrictExpression(bs.toSet, EcoreUtil.copy(child))
			caseExpr.exprs.add(re)
		]
		
		EcoreUtil.replace(parent, caseExpr)
		AlphaInternalStateConstructor.recomputeContextDomain(caseExpr)
		
		return 1;
	}
	private static dispatch def int transform(RestrictExpression re) {
		return transform(re, re.restrictDomain, re.expr);
	}
	private static dispatch def int transform(AutoRestrictExpression re) {
		return transform(re, re.inferredDomain, re.expr);
	}
}