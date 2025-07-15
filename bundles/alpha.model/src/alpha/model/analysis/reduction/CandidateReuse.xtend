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
import alpha.model.prdg.DependenceCone
import alpha.model.StandardEquation
import alpha.model.util.PolyLibUtil
import fr.irisa.cairn.jnimap.isl.ISLPoint
import fr.irisa.cairn.jnimap.isl.ISLVal

import static alpha.model.transformation.reduction.SimplifyingReductions.testLegality
import static alpha.model.util.Face.enumerateAllPossibleLabelings

import static extension alpha.model.util.AffineFunctionOperations.createUniformFunction
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.DomainOperations.toBasicSetFromKernel
import static extension alpha.model.util.ISLUtil.dimensionality
import static extension alpha.model.util.ISLUtil.integerPointClosestToOrigin
import static extension alpha.model.util.ISLUtil.isTrivial
import static extension alpha.model.util.ISLUtil.nullSpace
import fr.irisa.cairn.jnimap.isl.ISLBasicSet

class CandidateReuse {

	public static boolean DEBUG = true;

	private static def void debug(String msg) {
		if (DEBUG)
			println("[CandidateReuse] " + msg)
	}

	AbstractReduceExpression are
	ShareSpaceAnalysisResult SSAR
	long[] reuseVectorWithIdenticalAnswers
	DependenceCone cone

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

	new(AbstractReduceExpression are, ShareSpaceAnalysisResult SSAR, DependenceCone cone) {
		this.are = are
		this.SSAR = SSAR
		this.vectors = new LinkedList<long[]>()
		this.hasIdenticalAnswers = false
		this.cone = cone

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
		val zeros = (0 ..< nbParams).map[0L]
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

		var tempSpace = areSS.toBasicSetFromKernel(are.body.contextDomain.space)
		var parent = are.eContainer
		while (parent.eClass instanceof StandardEquation) {
			parent = parent.eContainer
		}
		val parentName = (parent as StandardEquation).variable.name

		// construct reuse space
		// Filtering for invalid reuse based off of the dependence cone if the cone is not empty
		var ISLSet initReuseSpace
		if (cone !== null) {
			initReuseSpace = cone.intersectReuseSpace(tempSpace.copy, parentName).toSet
			if(initReuseSpace.empty) {
				initReuseSpace = generateRays(tempSpace.copy)
				println("Empty Space: " + initReuseSpace.copy)
				initReuseSpace.basicSets.map[x | x.integerPointClosestToOrigin].forEach[x | println(x.toString)]
				initReuseSpace = tempSpace.copy.toSet
			} else {
				println("Non-empty Space: " + initReuseSpace.copy)
			}
		} else {
			initReuseSpace = tempSpace.copy.toSet
		}

		println("Space: " + initReuseSpace.copy)
		println("Num Steps: " + initReuseSpace.nbBasicSets)
		val reuseSpace = initReuseSpace.copy

		// construct face lattice
		val face = are.facet
		debug('(candidateReuse) Lp = ' + face.toLinearSpace.toString)
		val facets = face.generateChildren.toList

		if (facets.size == 0)
			return;
		

		// enumerate all valid labelings
		val labelings = enumerateAllPossibleLabelings(facets.size, true).toList
		val labelingInducingDomains = labelings.map[l|face.getLabelingDomain(l)]
												// find the labelings that have none-empty domains
												.reject[ld|ld.value.isTrivial]
												// Intersect with our reuse space
												.map[ld|ld.key -> reuseSpace.copy.basicSets.map[set | set.copy.intersect(ld.value.copy)].findFirst[set|!set.copy.empty]]
												.reject[ld|ld.value === null]
												.reject [ld | ld.value.isTrivial]
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
	
	def private synchronized ISLSet generateRays(ISLBasicSet re) {
		val reuseSpace = re.projectOut(ISLDimType.isl_dim_param, 0, re.nbParams)
		var poly = PolyLibUtil.fromISLBasicSet(reuseSpace.copy)
		var rays = poly.copy.builRaysVertices
		var output = ISLSet.buildEmpty(reuseSpace.space)
			
		for (var i = 0; i < rays.nbRows; i++) {
//			println("Ray")
//			for(var j = 0; j < rays.nbColumns; j++) {
//				print(rays.getAt(i, j))
//			}
//			println()
			if (rays.getAt(i, 0) == 0) {
				var ISLPoint point = ISLPoint.buildZero(output.space)
				for (var j = 1; j < rays.nbColumns - 1; j++) {
					point = point.copy.setCoordinate(ISLDimType.isl_dim_out, j - 1,
						ISLVal.buildFromLong(reuseSpace.copy.context, rays.getAt(i, j)))
				}
				output = output.union(point.toSet)
				if (rays.getAt(i, rays.nbColumns - 1) == 0) {
					point = ISLPoint.buildZero(output.space)
					
					for (var j = 1; j < rays.nbColumns - 1; j++) {
						point = point.copy.setCoordinate(ISLDimType.isl_dim_out, j - 1,
							ISLVal.buildFromLong(reuseSpace.copy.context, -rays.getAt(i, j)))
					}
					output = output.union(point.toSet)
				}
			}
		}
		rays.free
		poly.free
		output = output.addParams(newArrayList("N"))
		return output
	}
	
	def private ISLBasicSet generateRay(ISLBasicSet set, ISLBasicSet re) {
		val reuseSpace = re.projectOut(ISLDimType.isl_dim_param, 0, re.nbParams)
		var poly = PolyLibUtil.fromISLBasicSet(reuseSpace.copy)
		var rays = poly.builRaysVertices
			
		for (var i = 0; i < rays.nbRows; i++) {
//			println("Ray")
//			for(var j = 0; j < rays.nbColumns; j++) {
//				print(rays.getAt(i, j))
//			}
//			println()
			if (rays.getAt(i, 0) == 0) {
				var ISLPoint point = ISLPoint.buildZero(set.space)
				for (var j = 1; j < rays.nbColumns - 1; j++) {
					point = point.copy.setCoordinate(ISLDimType.isl_dim_out, j - 1,
						ISLVal.buildFromLong(reuseSpace.copy.context, rays.getAt(i, j)))
				}
				
				val finalPoint = point.copy	
				if(finalPoint.copy.toBasicSet.isSubset(set.copy)) {
					return finalPoint.toBasicSet
				}
				if (rays.getAt(i, rays.nbColumns - 1) == 0) {
					point = ISLPoint.buildZero(set.space)
					
					for (var j = 1; j < rays.nbColumns - 1; j++) {
						point = point.copy.setCoordinate(ISLDimType.isl_dim_out, j - 1,
							ISLVal.buildFromLong(reuseSpace.copy.context, -rays.getAt(i, j)))
					}
					val finalPointRev = point.copy
					if(finalPointRev.copy.toBasicSet.isSubset(set.copy)) {						
						return finalPointRev.toBasicSet
					}
				}
			}
		}
		return ISLBasicSet.buildEmpty(set.space)
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
