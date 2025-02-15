package alpha.model.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaSystem
import alpha.model.CaseExpression
import alpha.model.RestrictExpression
import alpha.model.StandardEquation
import alpha.model.Variable
import alpha.model.factory.AlphaUserFactory
import alpha.model.util.AlphaOperatorUtil
import alpha.model.util.AlphaUtil
import static extension alpha.model.util.ISLUtil.*
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLPoint
import fr.irisa.cairn.jnimap.isl.ISLSet
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * Serializes a reduction along given reuse function(s).
 * 
 * This will modify the container system of the given ReduceExpression.
 * 
 * This class is not given enough information to automatically decompose
 * reductions, so it must serialize all dimensions of the reduction at once.
 * Users can manually apply DecomposeReduction beforehand if they only wish to
 * partially serialize the reduction.
 */
class SerializeReduction {
	/**
	 * Applies a 1D serialization. Can only be used on reductions of rank 1.
	 */
	static def void apply(AbstractReduceExpression are, ISLMultiAff reuseDep) {
		val Variable writeVar = (AlphaUtil.getContainerEquation(are) as StandardEquation).variable
		val String newName = AlphaUtil.duplicateNameResolver.apply(
			AlphaUtil.getContainerSystem(are),
			writeVar.name + "_reduction",
			"_"
		)
		apply(are, reuseDep, newName)
	}
	
	/**
	 * Serializes a reduction using an arbitrary set of basis vectors as reuse dependences.
	 * This is not guaranteed to be a 'good' serialization, but it will certainly be valid.
	 */
	static def void applyAuto(AbstractReduceExpression are) {
		var nullSpace = are.projectionExpr.getISLMultiAff.copy.nullSpace
		SerializeReduction.applyAll(are, nullSpace.getBasisVectors.map[vec | vec.buildTranslationMaff])
	}
	
	static def void apply(AbstractReduceExpression are, ISLMultiAff reuseDep, String newName) {
		checkArguments(are, #[reuseDep], newName)
		serialize(are, reuseDep, newName)
	}
	
	static def void applyAll(AbstractReduceExpression are, Iterable<ISLMultiAff> partialReuseDeps) {
		checkArguments(are, partialReuseDeps, "")
		
		//extend reuseDeps to span the whole nullspace if it isn't long enough.
		var reuseDeps = partialReuseDeps.map[a | a]
		var nullSpace = are.projectionExpr.getISLMultiAff.copy.nullSpace
		if(reuseDeps.size <  nullSpace.copy.dimensionality) {
			val Iterable<ISLPoint> reuseVectors = reuseDeps.map[dep | dep.copy.toMap.deltas.samplePoint]
			
			for(var i = 0; i < reuseVectors.size; i++) {
				nullSpace = nullSpace.apply(reuseVectors.get(i).copy.buildRejectionMaff.toMap)
			}
			
			reuseDeps = reuseDeps + nullSpace.getBasisVectors.map[vec | vec.buildTranslationMaff]
		}
		
		val Variable writeVar = (AlphaUtil.getContainerEquation(are) as StandardEquation).variable
		
		//Serialize the reduction one dependence at a time.
		for(var i = 0; i < reuseDeps.length-1; i++)  {
			val String newName = AlphaUtil.duplicateNameResolver.apply(
				AlphaUtil.getContainerSystem(are),
				writeVar.name + "_reduction",
				i.toString
			)
			val writeMaff = are.projectionExpr.getISLMultiAff
			
			val reuseDep = reuseDeps.get(i)
			val ISLMultiAff f1 = buildRejectionMaff(reuseDep.copy.toMap.deltas.samplePoint)
			val ISLMultiAff f2 = writeMaff.copy.toMap.applyDomain(f1.copy.toMap).toMultiAff
			
			ReductionDecomposition.apply(are, f1, f2)
			
			serialize(are.body as AbstractReduceExpression, reuseDep, newName)
		}
		
		val String newName = AlphaUtil.duplicateNameResolver.apply(
			AlphaUtil.getContainerSystem(are),
			writeVar.name + "_reduction",
			(reuseDeps.length-1).toString
		)
			
		serialize(are, reuseDeps.get(reuseDeps.length-1), newName)
	}
	
	/**
	 * The main serialize method, which all public-facing methods eventually call.
	 */
	private static def void serialize(AbstractReduceExpression are, ISLMultiAff reuseDep, String newName) {
		var AlphaSystem sys = AlphaUtil.getContainerSystem(are)		
		val systemBody = AlphaUtil.getContainerSystemBody(are)
		val ISLSet body = are.body.getContextDomain
		val ISLMultiAff writeMaff = are.projectionExpr.getISLMultiAff
		var AlphaExpression coreExpr = are.body 
		if(coreExpr instanceof RestrictExpression) coreExpr = coreExpr.expr
		
		val Variable reductionVar = AlphaUserFactory.createVariable(newName, body.copy)
		sys.locals.add(reductionVar)

		/*
		 * The 'top' set of points are what is read by the write variable.
		 * The 'bottom' set of points do not read any other points in the serialized reduction.
		 */
		val ISLSet top = body.copy.subtract(body.copy.apply(reuseDep.copy.toMap)).simplify
		val ISLSet bottom = body.copy.subtract(body.copy.apply(reuseDep.copy.toMap.reverse)).simplify
		
		val CaseExpression writeCaseExpr = AlphaUserFactory.createCaseExpression()
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
			
			val readExpr = AlphaUserFactory.createVariableExpression(reductionVar)
			
			/*
			 * Replaces the ReduceExpression with a simple DependenceExpression if it is
			 * single valued. Even if a ReduceExpression remains by the end, it is guaranteed
			 * to be bounded.
			 */
			var AlphaExpression dependenceExpr
			if(shadowProject.copy.reverse.isSingleValued) {
				dependenceExpr = AlphaUserFactory.createDependenceExpression(
					shadowProject.copy.reverse.toMultiAff,
					readExpr
				)
			}
			else {
				dependenceExpr = AlphaUserFactory.createReduceExpression(
					are.operator,
					writeMaff.copy,
					AlphaUserFactory.createRestrictExpression(
						top.copy,
						readExpr
					)
				)
			}
			
			writeCaseExpr.exprs += AlphaUserFactory.createRestrictExpression(shadow, dependenceExpr)
		}
		
		EcoreUtil.replace(are, writeCaseExpr)
	
		/*
		 * Generates the Exprs that the new StandardEquation will use.
		 */
		val readCaseExpr = AlphaUserFactory.createCaseExpression()
		val selfDepExpr = AlphaUserFactory.createDependenceExpression(
			reuseDep.copy, 
			AlphaUserFactory.createVariableExpression(reductionVar)
		)
		
		/*
		 * The 'bottom' points only read from the read variable.
		 * The rest read from the read variable, and also their fellow points using
		 * reuseDep as a uniform dependence.
		 */
		readCaseExpr.exprs += AlphaUserFactory.createRestrictExpression(
			bottom.copy, 
			EcoreUtil.copy(coreExpr)
		)
		readCaseExpr.exprs += AlphaUserFactory.createRestrictExpression(
			body.copy.subtract(bottom.copy), 
			AlphaUserFactory.createBinaryExpression(
				AlphaOperatorUtil.reductionOPtoBinaryOP(are.operator),
				EcoreUtil.copy(coreExpr),
				selfDepExpr
			)
		)
		
		val standardEq = AlphaUserFactory.createStandardEquation(reductionVar, readCaseExpr)
		systemBody.equations += standardEq
		
		AlphaInternalStateConstructor.recomputeContextDomain(sys)
	}
	
	/**
	 * Sanity check!
	 */
	static def private void checkArguments(AbstractReduceExpression are, Iterable<ISLMultiAff> reuseDeps, String newName) {
		val ISLMultiAff writeMaff = are.projectionExpr.getISLMultiAff
		val nullSpace = writeMaff.copy.nullSpace
		
		val Iterable<ISLPoint> reuseVectors = reuseDeps.map[dep | dep.copy.toMap.deltas.samplePoint]
		if(!reuseVectors.forall[vector | vector.copy.toSet.isSubset(nullSpace.copy)]) {
			throw new IllegalArgumentException("[SerializeReduction] Reuse dependences: " + reuseDeps +
				"\ndo not all reside in the nullspace of the projection function: " + are	)
		}
		
		val dimensionality = reuseVectors.getSpan.dimensionality
		if(dimensionality < reuseVectors.size) {
			throw new IllegalArgumentException("[SerializeReduction] Reuse dependences: " + reuseDeps +
				"\ndo not form a linearly independent set.")
		}
		
		if(dimensionality < nullSpace.copy.dimensionality) {
			throw new IllegalArgumentException("[SerializeReduction] Reuse dependences " + reuseDeps + 
				" are insufficient to serialize the the given reduction: " + are)
		}
		
		var AlphaSystem sys = AlphaUtil.getContainerSystem(are)
		if(sys === null) {
			throw new IllegalArgumentException("[SerializeReduction] Reduction Expression has no containing system.")
		}
		
		if(sys.getVariable(newName) !== null) {
			throw new IllegalArgumentException("[SerializeReduction] Variable with name " + newName + " already exists in the system.")
		}
	}
}