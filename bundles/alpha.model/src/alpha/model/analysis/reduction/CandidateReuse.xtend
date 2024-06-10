package alpha.model.analysis.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.util.Face
import alpha.model.util.Face.Boundary
import alpha.model.util.Face.Label
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.LinkedList
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

import static alpha.model.transformation.reduction.SimplifyingReductions.testLegality
import static alpha.model.util.Face.enumerateAllPossibleLabelings

import static extension alpha.model.util.AffineFunctionOperations.createUniformFunction
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.DomainOperations.toBasicSetFromKernel
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension alpha.model.util.ISLUtil.integerPointClosestToOrigin
import static extension alpha.model.util.ISLUtil.isTrivial
import static extension alpha.model.util.ISLUtil.nullSpace

class CandidateReuse {
	
	public static boolean DEBUG = false;
	
	private static def void debug(String msg) {
		if (DEBUG)
			println("[CandidateReuse] " + msg)
	}
	
	
	AbstractReduceExpression are
	ShareSpaceAnalysisResult SSAR
	long[] reuseVectorWithIdenticalAnswers
	
	@Accessors(PUBLIC_GETTER)
	ISLSet identicalAnswerDomain
	
	@Accessors(PUBLIC_GETTER)
	List<long[]> vectors
	
	@Accessors(PUBLIC_GETTER)
	boolean hasIdenticalAnswers
	
	new(AbstractReduceExpression are) {
		this(are, ShareSpaceAnalysis.apply(are))
	}
	new(AbstractReduceExpression are, ShareSpaceAnalysisResult SSAR) {
		this.are = are
		this.SSAR = SSAR
		this.vectors = new LinkedList<long[]>()
		this.hasIdenticalAnswers = false
		
		generateCandidateReuseVectors
	}
	
	
	/** 
	 * Returns the reuse vector spanning the dimension along which identical
	 * answers are computed
	 */
	def ISLMultiAff identicalAnswerBasis() {
		if (reuseVectorWithIdenticalAnswers === null)
			return null
		
		// create the ISLMultiAff from the reuseVectorWithIdenticalAnswers
		// must add implicit parameter columns first
		val space = are.body.contextDomain.copy.toIdentityMap.space
		val nbParams = space.dim(ISLDimType.isl_dim_param)
		val zeros = (0..<nbParams).map[0L]
		val rho = space.createUniformFunction(zeros + reuseVectorWithIdenticalAnswers)

		return rho
	}
	
	/**
	 * Creates a list of ISLMultiAff that are valid reuse vectors given the share space.
	 * Exposed to be used by SimplifyingReductionExploration.
	 */
	private def generateCandidateReuseVectors() {
		val areSS = SSAR.getShareSpace(are.body)
		if (areSS === null)
			return;
		
		// construct reuse space
		val reuseSpace = areSS.toBasicSetFromKernel(are.body.contextDomain.space)
		
		// construct face lattice
		val face = are.facet
		debug('(candidateReuse) Lp = ' + face.toLinearSpace.toString)
		val facets = face.generateChildren.toList
		
		if (facets.size == 0)
			return;
		
		// enumerate all valid labelings
		val labelings = enumerateAllPossibleLabelings(facets.size, true).toList
		
		// find the labelings that have none-empty domains
		val labelingInducingDomains = labelings.map[l | face.getLabelingDomain(l)]
		                                       .reject[ld | ld.value.isTrivial]
		                                       .map[ld | ld.key -> ld.value.intersect(reuseSpace.copy)]
		                                       .reject[ld | ld.value.isTrivial]
		                                       .toList
		
		// select the reuse vector for each labeling domain (closest to the origin)
		val candidateReuseVectors = labelingInducingDomains.map[ld | ld.key -> ld.value.integerPointClosestToOrigin]
		val validReuseVectors = candidateReuseVectors.filter[lv | testLegality(are, lv.value)]
		
		if (DEBUG) {
			for (f : facets) {
				debug('facet-' + facets.indexOf(f) + ': ' + f.toBasicSet)
			}
		}
		
		for (labelingAndReuse : validReuseVectors) {
			val labeling = labelingAndReuse.key
			val reuseVector = labelingAndReuse.value
			
			debug('labeling ' + labeling.toString + ' induced by ' + reuseVector.toString)
			val accumulationSpace = are.projection.nullSpace
			identicalAnswerDomain = labeling.computeIdenticalAnswerDomain(facets, accumulationSpace)
			if (!identicalAnswerDomain.isEmpty) {
				debug('results in identical answers')
				hasIdenticalAnswers = true
				reuseVectorWithIdenticalAnswers = reuseVector
				return
			}
			vectors.add(reuseVector)
		}
	}
	
	/**
	 * Returns true if all POS and NEG faces can be simultaneously transformed into strong boundaries
	 * 
	 * A face is a weak boundary if its linear space has a non-trivial intersection with the accumulation
	 * space. A weak boundary can be made into a strong boundary with an appropriate choice of reduction 
	 * decomposition. Multiple weak boundary face can simultaneously be made into strong boundaries if the
	 * combined intersection of their linear spaces with the accumulation space is at least 1-dimensional.
	 * 
	 */
	def static ISLSet computeIdenticalAnswerDomain(Label[] labeling, Face[] facets, ISLSet accumulationSpace) {

		val emptyDomain = ISLSet.buildEmpty(accumulationSpace.space)

		debug('---')
		debug('accumulation ' + accumulationSpace)
		debug('---')
		
		// get all POS- and NEG-faces in the given labeling
		val nonZeroFacets = facets.zipWith(labeling)
			.reject[faceLabel | faceLabel.value == Label.ZERO]
			.map[faceLabel | faceLabel.key]
		
		// return empty set if there is at least one Boundary.NON POS- or NEG-face
		val nonZeroNonBoundaryFacets = nonZeroFacets
			.filter[boundaryLabel(accumulationSpace) == Boundary.NON]
		if (!nonZeroNonBoundaryFacets.isEmpty)
			return emptyDomain

		nonZeroFacets.forEach[f | debug('relevant facet-' + f)]

		// compute intersection of POS- and NEG-face linear spaces with accumulation space
		val universe = ISLSet.buildUniverse(accumulationSpace.space)
		val commonWeakSpace = nonZeroFacets.map[toLinearSpace.toSet]
			.fold(universe.copy, [ret, lp | ret.intersect(lp)])
		
		val domain = commonWeakSpace.copy.intersect(accumulationSpace.copy)
		if (domain.dimensionality > 0)
			return domain
		
		return emptyDomain	
	}
}