package alpha.model.analysis.reduction

import alpha.model.AbstractReduceExpression
import java.util.Arrays

import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.toLinearUnitVector
import alpha.model.util.Face
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import alpha.model.util.FaceLattice
import fr.irisa.cairn.jnimap.isl.ISLSet

class ReductionUtil {
	
	/**
	 * Returns true if the body of re1 is similar to the body re2, or false otherwise
	 * Two bodies are said to be similar if they both have the same number of facets and
	 * all facets have the same linear space
	 */
	def static boolean isSimilar(AbstractReduceExpression re1, AbstractReduceExpression re2) {
		re1.facet.isSimilar(re2.facet)
	}
	
	def static boolean isSimilar(ISLSet set1, ISLSet set2) {
		FaceLattice.create(set1).root.isSimilar(FaceLattice.create(set2).root)		
	}
	
	def static boolean isSimilar(ISLBasicSet bset1, ISLBasicSet bset2) {
		bset1.copy.toSet.isSimilar(bset2.copy.toSet)
	}
	
	def static boolean isSimilar(Face face1, Face face2) {
			
		val vecs1 = face1.generateChildren.map[getNormalVector(face1)].map[toLinearUnitVector]
		val vecs2 = face2.generateChildren.map[getNormalVector(face2)].map[toLinearUnitVector]
		
		if (vecs1.size != vecs2.size) 
			return false;
		
		vecs1.zipWith(vecs2).forall[Arrays.equals(key, value)]
	}
	
}