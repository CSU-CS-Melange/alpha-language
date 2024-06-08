package alpha.model.analysis.reduction

import alpha.model.util.Face
import alpha.model.util.Face.Label
import fr.irisa.cairn.jnimap.isl.ISLMultiAff

import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.nullSpace

class ReductionUtil {
	
	/** 
	 * Returns true if face is a boundary face under the projection function, or false otherwise
	 * per Theorem 6 of GR06.
	 */
	def static boolean isBoundary(Face face, ISLMultiAff projection) {
		val lp = face.toLinearSpace.toSet
		
		val accumulationSpace = projection.nullSpace
		
		return accumulationSpace.isSubset(lp)
	}
	
	/**
	 * Returns true if all POS and NEG faces are boundaries, or false otherwise
	 */
	def static boolean hasAllZeroNonBoundaries(Label[] labeling, Face[] facets, ISLMultiAff fp) {
		labeling.zipWith(facets).forEach[lf | 
			val label = lf.key
			val facet = lf.value
			println(label + ', ' + if (facet.isBoundary(fp)) {'isBoundary'} else {' '})
		]
		if (labeling.toString == '[ZERO, ZERO, ZERO, NEG, POS]')
			println
		println
		return labeling.zipWith(facets).forall[lf | lf.key == Label.ZERO || lf.value.isBoundary(fp)]
	}
	
	
}


