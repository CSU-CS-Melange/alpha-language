package alpha.model.analysis.reduction

import alpha.model.AbstractReduceExpression
import java.util.Arrays

import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.toLinearUnitVector

class ReductionUtil {
	
	/**
	 * Returns true if the body of re1 is similar to the body re2, or false otherwise
	 * Two bodies are said to be similar if they both have the same number of facets and
	 * all facets have the same linear space
	 */
	def static boolean isSimilar(AbstractReduceExpression re1, AbstractReduceExpression re2) {
			
		val vecs1 = re1.facet.generateChildren.map[getNormalVector(re1.facet)].map[toLinearUnitVector]
		val vecs2 = re2.facet.generateChildren.map[getNormalVector(re2.facet)].map[toLinearUnitVector]
		
		if (vecs1.size != vecs2.size) 
			return false;
		
		vecs1.zipWith(vecs2).forall[Arrays.equals(key, value)]
	}
	
}