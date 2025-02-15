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
 * This class is not given enough information to automatically decompose
 * reductions, so it must serialize all dimensions of the reduction at once.
 * Users can manually apply DecomposeReduction beforehand if they only wish to
 * partially serialize the reduction.
 * 
 */
class SerializeReduction { //TODO: allow partial serialization
	static def void apply(AbstractReduceExpression reduce, ISLMultiAff reuseDep) {
		val Variable writeVar = (AlphaUtil.getContainerEquation(reduce) as StandardEquation).variable
		val String newName = AlphaUtil.duplicateNameResolver.apply(
			AlphaUtil.getContainerSystem(reduce),
			writeVar.name + "_reduction",
			"_"
		)
		apply(reduce, reuseDep, newName)
	}
	
	static def void apply(AbstractReduceExpression reduce, ISLMultiAff reuseDep, String newName) {
		checkArguments(reduce, #[reuseDep], newName)
		serialize(reduce, reuseDep, newName)
	}
	
	static def void applyAll(AbstractReduceExpression reduce, Iterable<ISLMultiAff> partialReuseDeps) {
		checkArguments(reduce, partialReuseDeps, "")
		
		//extend reuseDeps to span the whole nullspace if it isn't long enough.
		var reuseDeps = partialReuseDeps.map[a | a]
		var nullSpace = reduce.projectionExpr.getISLMultiAff.copy.nullSpace
		if(reuseDeps.size <  nullSpace.copy.dimensionality) {
			val Iterable<ISLPoint> reuseVectors = reuseDeps.map[dep | dep.copy.toMap.deltas.samplePoint]
			
			for(var i = 0; i < reuseVectors.size; i++) {
				nullSpace = nullSpace.apply(reuseVectors.get(i).copy.buildRejectionMaff.toMap)
			}
			
			reuseDeps = reuseDeps + nullSpace.getBasisVectors.map[vec | vec.buildTranslationMaff]
		}
		
		val Variable writeVar = (AlphaUtil.getContainerEquation(reduce) as StandardEquation).variable
		
		for(var i = 0; i < reuseDeps.length-1; i++)  {
			val String newName = AlphaUtil.duplicateNameResolver.apply(
				AlphaUtil.getContainerSystem(reduce),
				writeVar.name + "_reduction",
				i.toString
			)
			val writeMaff = reduce.projectionExpr.getISLMultiAff
			
			val reuseDep = reuseDeps.get(i)
			val ISLMultiAff f1 = buildRejectionMaff(reuseDep.copy.toMap.deltas.samplePoint)
			val ISLMultiAff f2 = writeMaff.copy.toMap.applyDomain(f1.copy.toMap).toMultiAff
			
			ReductionDecomposition.apply(reduce, f1, f2)
			
			serialize(reduce.body as AbstractReduceExpression, reuseDep, newName)
		}
		
		val String newName = AlphaUtil.duplicateNameResolver.apply(
			AlphaUtil.getContainerSystem(reduce),
			writeVar.name + "_reduction",
			(reuseDeps.length-1).toString
		)
			
		serialize(reduce, reuseDeps.get(reuseDeps.length-1), newName)
	}
	
	private static def void serialize(AbstractReduceExpression reduce, ISLMultiAff reuseDep, String newName) {
		var AlphaSystem sys = AlphaUtil.getContainerSystem(reduce)		
		val systemBody = AlphaUtil.getContainerSystemBody(reduce)
		val ISLSet body = reduce.body.getContextDomain
		val ISLMultiAff writeMaff = reduce.projectionExpr.getISLMultiAff
		var AlphaExpression coreExpr = reduce.body 
		if(coreExpr instanceof RestrictExpression) coreExpr = coreExpr.expr
		
		val Variable reductionVar = AlphaUserFactory.createVariable(newName, body.copy)
		sys.locals.add(reductionVar)

		val ISLSet top = body.copy.subtract(body.copy.apply(reuseDep.copy.toMap)).simplify
		val ISLSet bottom = body.copy.subtract(body.copy.apply(reuseDep.copy.toMap.reverse)).simplify
		
		val CaseExpression writeCaseExpr = AlphaUserFactory.createCaseExpression()
		
		var ISLSet coveredDomain = ISLSet.buildEmpty(body.copy.apply(writeMaff.copy.toMap).getSpace)
		for( ISLBasicSet basicFacet : top.getBasicSets ) {
			val ISLSet facet = basicFacet.copy.toSet
			val ISLMap shadowProject = writeMaff.copy.toMap.intersectDomain(facet.copy)
			val ISLSet shadow = facet.copy.apply(shadowProject.copy).subtract(coveredDomain.copy)
			coveredDomain = coveredDomain.union(shadow.copy)
			
			val readExpr = AlphaUserFactory.createVariableExpression(reductionVar)
			
			var AlphaExpression dependenceExpr
			if(shadowProject.copy.reverse.isSingleValued) {
				dependenceExpr = AlphaUserFactory.createDependenceExpression(
					shadowProject.copy.reverse.toMultiAff,
					readExpr
				)
			}
			else {
				dependenceExpr = AlphaUserFactory.createReduceExpression(
					reduce.operator,
					writeMaff.copy,
					AlphaUserFactory.createRestrictExpression(
						top.copy,
						readExpr
					)
				)
			}
			
			writeCaseExpr.exprs += AlphaUserFactory.createRestrictExpression(shadow, dependenceExpr)
		}
		
		EcoreUtil.replace(reduce, writeCaseExpr)
	
		val readCaseExpr = AlphaUserFactory.createCaseExpression()
		val selfDepExpr = AlphaUserFactory.createDependenceExpression(
			reuseDep.copy, 
			AlphaUserFactory.createVariableExpression(reductionVar)
		)
		 
		readCaseExpr.exprs += AlphaUserFactory.createRestrictExpression(
			bottom.copy, 
			EcoreUtil.copy(coreExpr)
		)
		readCaseExpr.exprs += AlphaUserFactory.createRestrictExpression(
			body.copy.subtract(bottom.copy), 
			AlphaUserFactory.createBinaryExpression(
				AlphaOperatorUtil.reductionOPtoBinaryOP(reduce.operator),
				EcoreUtil.copy(coreExpr),
				selfDepExpr
			)
		)
		
		val standardEq = AlphaUserFactory.createStandardEquation(reductionVar, readCaseExpr)
		systemBody.equations += standardEq
		
		AlphaInternalStateConstructor.recomputeContextDomain(sys)
	}
	
	static def private void checkArguments(AbstractReduceExpression reduce, Iterable<ISLMultiAff> reuseDeps, String newName) {
		val ISLMultiAff writeMaff = reduce.projectionExpr.getISLMultiAff
		val nullSpace = writeMaff.copy.nullSpace
		
		val Iterable<ISLPoint> reuseVectors = reuseDeps.map[dep | dep.copy.toMap.deltas.samplePoint]
		if(!reuseVectors.forall[vector | vector.copy.toSet.isSubset(nullSpace.copy)]) {
			throw new IllegalArgumentException("[SerializeReduction] Reuse dependences: " + reuseDeps +
				"\ndo not all reside in the nullspace of the projection function: " + reduce	)
		}
		
		val dimensionality = reuseVectors.getSpan.dimensionality
		if(dimensionality < reuseVectors.size) {
			throw new IllegalArgumentException("[SerializeReduction] Reuse dependences: " + reuseDeps +
				"\ndo not form a linearly independent set.")
		}
		
		var AlphaSystem sys = AlphaUtil.getContainerSystem(reduce)
		if(sys === null) {
			throw new IllegalArgumentException("[SerializeReduction] Reduction Expression has no containing system.")
		}
		
		if(sys.getVariable(newName) !== null) {
			throw new IllegalArgumentException("[SerializeReduction] Variable with name " + newName + " already exists in the system.")
		}
	}
}