package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaSystem
import alpha.model.BINARY_OP
import alpha.model.CaseExpression
import alpha.model.RestrictExpression
import alpha.model.StandardEquation
import alpha.model.Variable
import static extension alpha.model.util.AlphaOperatorUtil.*
import static extension alpha.model.util.AlphaUtil.*
import alpha.model.util.FaceLattice
import alpha.model.util.JavaUtil
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLPoint
import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.ArrayList
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension alpha.model.factory.AlphaUserFactory.*
import static extension alpha.model.util.ISLUtil.*
import fr.irisa.cairn.jnimap.isl.ISLDimType

/**
 * Serializes a reduction along given reuse function(s).
 * 
 * This will modify the container system of the given ReduceExpression.
 * 
 * Three methods are included:
 * - apply: serializes a reduction of rank 1 with a single accumulation vector.
 * - applyAuto: serializes a reduction with automatically generated accumulation vectors.
 * - applySequential: serializes a reduction of rank n with up to n accumulation vectors
 * 		(more accumulation vectors are automatically generated as needed).
 * - applyOneShot: serializes a reduction of any rank with a single accumulation vector.
 */
class SerializeReduction {
	/**
	 * Applies a 1D serialization. Can only be used on reductions of rank 1.
	 */
	static def void apply(AbstractReduceExpression are, ISLMultiAff accumulationMaff) {
		apply(are, accumulationMaff, generateReductionName(are))
	}
	
	static def void apply(AbstractReduceExpression are, ISLMultiAff accumulationMaff, String newName) {
		checkArguments(are, #[accumulationMaff], newName, false)
		serialize(are, accumulationMaff, newName)
	}
	
	/**
	 * Serializes a reduction using an arbitrary set of basis vectors as directions of accumulation.
	 * This is not guaranteed to be a 'good' serialization, but it will certainly be valid.
	 */
	static def void applyAuto(AbstractReduceExpression are) {
		var nullSpace = are.projectionExpr.getISLMultiAff.copy.nullSpace
		SerializeReduction.applySequential(are, nullSpace.basisVectors.map[vec | vec.buildTranslationMaff])
	}
	
	static def void applyAuto(AbstractReduceExpression are, boolean oneShot) {
		if(!oneShot) {
			applyAuto(are)
			return
		} 
		var nullSpace = are.projectionExpr.getISLMultiAff.copy.nullSpace
		SerializeReduction.applyOneShot(are, nullSpace.basisVectors.get(0).buildTranslationMaff)
	}
	
	/**
	 * Applies a series of reductions, creating a new temporary variable for each.
	 * Avoids a potentially exponential number of domains, at the cost of
	 * potential schedule bloat when fed to FoutrierScheduler
	 */
	static def void applySequential(AbstractReduceExpression are, Iterable<ISLMultiAff> partialaccumulationMaffs) {
		checkArguments(are, partialaccumulationMaffs, "", false)
		
		//extend accumulationMaffs to span the whole nullspace if it isn't long enough.
		var accumulationMaffs = partialaccumulationMaffs.map[a | a]
		var nullSpace = are.projectionExpr.getISLMultiAff.copy.nullSpace
		if(accumulationMaffs.size <  nullSpace.copy.dimensionality) {
			val Iterable<ISLPoint> accumulationVectors = accumulationMaffs.map[accumulationMaff | accumulationMaff.copy.toMap.deltas.samplePoint]
			
			for(var i = 0; i < accumulationVectors.size; i++) {
				nullSpace = nullSpace.apply(accumulationVectors.get(i).copy.buildRejectionMaff.toMap)
			}
			
			accumulationMaffs = accumulationMaffs + nullSpace.basisVectors.map[vec | vec.buildTranslationMaff]
		}
		
		val Variable writeVar = (are.containerEquation as StandardEquation).variable
		
		//Serialize the reduction one dependence at a time.
		for(var i = 0; i < accumulationMaffs.length-1; i++)  {
			val String newName = duplicateNameResolver.apply(
				are.containerSystem,
				writeVar.name + "_reduction",
				i.toString
			)
			val writeMaff = are.projectionExpr.getISLMultiAff
			
			val accumulationMaff = accumulationMaffs.get(i)
			val ISLMultiAff f1 = buildRejectionMaff(accumulationMaff.copy.toMap.deltas.samplePoint)
			val ISLMultiAff f2 = writeMaff.copy.toMap.applyDomain(f1.copy.toMap).toMultiAff
			
			ReductionDecomposition.apply(are, f1, f2)
			
			serialize(are.body as AbstractReduceExpression, accumulationMaff, newName)
		}
		
		val String newName = duplicateNameResolver.apply(
			are.containerSystem,
			writeVar.name + "_reduction",
			(accumulationMaffs.length-1).toString
		)
			
		serialize(are, accumulationMaffs.get(accumulationMaffs.length-1), newName)
	}
	
	/**
	 * Returns the variable created by the serialization process.
	 */
	static def Variable applyOneShot(AbstractReduceExpression are, ISLMultiAff accumulationMaff) {
		return applyOneShot(are, accumulationMaff, generateReductionName(are))
	}
	
	static def Variable applyOneShot(AbstractReduceExpression are, ISLMultiAff accumulationMaff, String newName) {
		checkArguments(are, #[accumulationMaff], newName, true)
		return serializeOneShot(are, accumulationMaff, newName)
	}
	
	private static def String generateReductionName(AbstractReduceExpression are) {
		val Variable writeVar = (are.containerEquation as StandardEquation).variable
		return duplicateNameResolver.apply(
			are.containerSystem,
			writeVar.name + "_reduction",
			"_"
		)
	}
	
	/**
	 * Serialize a reduction using only one accumulation vector
	 * May have an exponential number of domains
	 * But avoids schedule bloat from having more variables than needed
	 */
	private static def Variable serializeOneShot(AbstractReduceExpression are, ISLMultiAff accumulationMaff, String newName) {
		val ISLSet body = are.body.getContextDomain	
		val ISLMultiAff writeMaff = are.projectionExpr.getISLMultiAff

		var AlphaExpression coreExpr = are.body 
		if(coreExpr instanceof RestrictExpression) coreExpr = coreExpr.expr
		
		var sys = are.getContainerSystem
		val Variable reductionVar = createVariable(newName, body.copy)
		sys.locals.add(reductionVar)
		
		/*
		 * The basin is the set of points whose flow along accumulationMaff does not exit the reduction body
		 * The top is the set of points which do flow outside the body
		 */
		val ISLSet basin = body.copy.intersect(body.copy.apply(accumulationMaff.copy.toMap.reverse))
		val ISLSet top = body.copy.subtract(basin.copy).simplify
		val ISLSet bottom = body.copy.subtract(body.copy.apply(accumulationMaff.copy.toMap)).simplify
		
		val FaceLattice lattice = FaceLattice.create(body.getBasicSetAt(0).copy)
		
		/*
		 * We create a new set of vectors to flow along once information reaches the top
		 * These vectors are defined by the vector along each edge that becomes accumulationMaff
		 * when projected onto it
		 * So each edgeFlowVec is assigned the same timestamp as accumulationMaff
		 */
		val ISLSet accumulationMaffProjPreimage = accumulationMaff.copy.toMap.deltas.preimage(
			accumulationMaff.copy.toMap.deltas.samplePoint.buildProjectionMaff
		)
		val int nExtraDims = body.dimensionality - writeMaff.copy.nullSpace.dimensionality
		
		val topRidges = lattice.getFaces(1 + nExtraDims)
			.map[toBasicSet.toSet]
			.filter[copy.isSubset(top.copy)]
		val sliceEdgeSpaces = topRidges
			.map[getBasisVectors.getSpan]
			.map[intersect(writeMaff.copy.nullSpace)]
		val edgeFlowVecs = sliceEdgeSpaces
			.map[intersect(accumulationMaffProjPreimage.copy)]
			.reject[isEmpty]
			.map[samplePoint]

		// A set of actually useful edge flow vectors is produced, along with the domains they accumulate.
		val infoFlowMaps = edgeFlowVecs
			.map[buildTranslationMaff.toMap]
			.map[intersectDomain(top.copy.intersect(top.copy.apply(it.copy.reverse)))]
			.reject[getDomain().isEmpty]
			 + #[accumulationMaff.copy.toMap.intersectDomain(basin)]
			 
		// The peak is the remaining set of unaccumulated points
		val peak = infoFlowMaps.fold(top.copy,
			[peak, map | peak.subtract(map.getDomain)]
		)	 
		
		/*
		 * Now convert the flow maps (flow vectors plus the domains they accumulate)
		 * into actual dependences, and insert them into the program
		 */
		var CaseExpression cases = infoFlowToCases(
			infoFlowMaps, 
			are.operator.reductionOPtoBinaryOP,
			reductionVar,
			coreExpr
		)
		cases.exprs += createRestrictExpression(bottom, coreExpr.copyAE)
		are.containerSystemBody.equations += createStandardEquation(reductionVar, cases)
		
		/*
		 * The peak (hopefully bounded, potentially not) is now accumulated with a reduction
		 * (or directly written, if singular) into the write variable
		 */
		var dependenceExpr = generateDependenceExpression(are, reductionVar, peak)
		EcoreUtil.replace(are, dependenceExpr)
		
		AlphaInternalStateConstructor.recomputeContextDomain(sys)
		
		return reductionVar
	}
	
	/**
	 * Takes flow information (domains plus the Maff along which they accumulate)
	 * and turns it into dependences
	 * Because the ranges of flow maps can intersect, the number of domains one needs to consider
	 * becomes exponential wrt. dimension
	 * 
	 * O(2^d) for realistic cases
	 * O(2^(2^d)) worst case
	 */
	private static def CaseExpression infoFlowToCases(Iterable<ISLMap> infoFlowMaps, BINARY_OP op, Variable v, AlphaExpression coreExpr) {
		val CaseExpression cases = createCaseExpression()
		
		JavaUtil.powerSet(infoFlowMaps.toSet).forEach[ wantMaps | 
			val unwantMaps = infoFlowMaps.filter[map | !wantMaps.contains(map)]
			
			//Bottom of the reduction is handled separately
			if(wantMaps.size < 1) return;
			
			var range = wantMaps.map[map | map.getRange]
				.reduce[a, b | a.copy.intersect(b.copy)]
			if(!unwantMaps.empty) range = range.subtract(
					unwantMaps.map[map | map.getRange]
					.reduce[a, b | a.copy.union(b.copy)]
				)
			if(range.isEmpty) return;
			
			//Generate cases for each combination of flow ranges
			cases.exprs += createRestrictExpression(
				range,
				createNaryExpression(
					op,
					wantMaps.map[ map | 
						createDependenceExpression(
							map.copy.reverse.toMultiAff,
							createVariableExpression(v)
						)
					] + #[coreExpr.copyAE]
				)
			)
		]
		
		return cases
	}
	
	
	/**
	 * The main serialize method, which apply and applyAll eventually call.
	 */
	private static def void serialize(AbstractReduceExpression are, ISLMultiAff accumulationMaff, String newName) {
		var AlphaSystem sys = are.containerSystem	
		val systemBody = are.containerSystemBody
		val ISLSet body = are.body.getContextDomain
		val ISLMultiAff writeMaff = are.projectionExpr.getISLMultiAff
		var AlphaExpression coreExpr = are.body 
		if(coreExpr instanceof RestrictExpression) coreExpr = coreExpr.expr
		
		val Variable reductionVar = createVariable(newName, body.copy)
		sys.locals.add(reductionVar)

		/*
		 * The 'top' set of points are what is read by the write variable.
		 * The 'bottom' set of points do not read any other points in the serialized reduction.
		 */
		val ISLSet top = body.copy.subtract(body.copy.apply(accumulationMaff.copy.toMap)).simplify
		val ISLSet bottom = body.copy.subtract(body.copy.apply(accumulationMaff.copy.toMap.reverse)).simplify
		
		val CaseExpression writeCaseExpr = createCaseExpression()
		/*
		 * Create a dependence from the write variable to each top 'facet' of the serialized reduction.
		 * Typically, there is only one such facet.
		 */
		var ISLSet coveredDomain = ISLSet.buildEmpty(body.copy.apply(writeMaff.copy.toMap).getSpace)
		for( ISLBasicSet basicFacet : top.getBasicSets ) {
			val ISLSet facet = basicFacet.copy.toSet
			val ISLMap shadowProject = writeMaff.copy.toMap.intersectDomain(facet.copy)
			val ISLSet shadow = facet.copy.apply(shadowProject.copy).subtract(coveredDomain.copy)
			coveredDomain = coveredDomain.union(shadow.copy)
			
			val readExpr = createVariableExpression(reductionVar)
			
			/*
			 * Replaces the ReduceExpression with a simple DependenceExpression if it is
			 * single valued. Even if a ReduceExpression remains by the end, it is guaranteed
			 * to be bounded.
			 */
			var AlphaExpression dependenceExpr
			if(shadowProject.copy.reverse.isSingleValued) {
				dependenceExpr = createDependenceExpression(
					shadowProject.copy.reverse.toMultiAff,
					readExpr
				)
			}
			else {
				dependenceExpr = createReduceExpression(
					are.operator,
					writeMaff.copy,
					createRestrictExpression(
						top.copy,
						readExpr
					)
				)
			}
			
			writeCaseExpr.exprs += createRestrictExpression(shadow, dependenceExpr)
		}
		
		EcoreUtil.replace(are, writeCaseExpr)
	
		/*
		 * Generates the Exprs that the new StandardEquation will use.
		 */
		val readCaseExpr = createCaseExpression()
		val selfDepExpr = createDependenceExpression(
			accumulationMaff.copy, 
			createVariableExpression(reductionVar)
		)
		
		/*
		 * The 'bottom' points only read from the read variable.
		 * The rest read from the read variable, and also their fellow points using
		 * reuseDep as a uniform dependence.
		 */
		readCaseExpr.exprs += createRestrictExpression(
			bottom.copy, 
			EcoreUtil.copy(coreExpr)
		)
		readCaseExpr.exprs += createRestrictExpression(
			body.copy.subtract(bottom.copy), 
			createBinaryExpression(
				are.operator.reductionOPtoBinaryOP,
				EcoreUtil.copy(coreExpr),
				selfDepExpr
			)
		)
		
		val standardEq = createStandardEquation(reductionVar, readCaseExpr)
		systemBody.equations += standardEq
		
		AlphaInternalStateConstructor.recomputeContextDomain(sys)
	}
	
	/**
	 * Returns a dependence expression to replace a serialized reduction
	 * This will be a simple dependence expression if the peak of the
	 * serialized reduction is singular. Otherwise, it will be another
	 * reduction expression, with a smaller body.
	 */
	private static def AlphaExpression generateDependenceExpression(AbstractReduceExpression are, Variable newVariable, ISLSet peak) {
		val writeMaff = are.projectionExpr.getISLMultiAff
		val shadowMap = writeMaff.copy.toMap.reverse.intersectRange(peak.copy)
		
		val variableExpr = createVariableExpression(newVariable)
		if(shadowMap.isSingleValued) {
			return createDependenceExpression(shadowMap.toMultiAff, variableExpr)
		} else { 
			val restrictExpr = createRestrictExpression(peak, variableExpr)
			return createReduceExpression(are.operator, writeMaff, restrictExpr)
		}
	}
	
	/**
	 * Sanity check!
	 */
	static def private void checkArguments(AbstractReduceExpression are, Iterable<ISLMultiAff> accumulationMaffs, String newName, boolean oneShot) {
		val ISLMultiAff writeMaff = are.projectionExpr.getISLMultiAff
		val nullSpace = writeMaff.copy.nullSpace
		
		val Iterable<ISLPoint> accumulationVectors = accumulationMaffs.map[accumulationMaff | accumulationMaff.copy.toMap.deltas.samplePoint]
		if(!accumulationVectors.forall[vector | vector.copy.toSet.isSubset(nullSpace.copy)]) {
			throw new IllegalArgumentException("[SerializeReduction] Accumulation directions: " + accumulationMaffs +
				"\ndo not all reside in the nullspace of the projection function: " + are	)
		}
		
		val dimensionality = accumulationVectors.getSpan.dimensionality
		if(dimensionality < accumulationVectors.size) {
			throw new IllegalArgumentException("[SerializeReduction] Accumulation directions: " + accumulationMaffs +
				"\ndo not form a linearly independent set.")
		}
		
		if(dimensionality < nullSpace.copy.dimensionality && !oneShot) {
			throw new IllegalArgumentException("[SerializeReduction] Accumulation directions " + accumulationMaffs + 
				" are insufficient to serialize the the given reduction: " + are)
		}
		
		var AlphaSystem sys = are.containerSystem
		if(sys === null) {
			throw new IllegalArgumentException("[SerializeReduction] Reduction Expression has no containing system.")
		}
		
		if(sys.getVariable(newName) !== null) {
			throw new IllegalArgumentException("[SerializeReduction] Variable with name " + newName + " already exists in the system.")
		}
	}
}