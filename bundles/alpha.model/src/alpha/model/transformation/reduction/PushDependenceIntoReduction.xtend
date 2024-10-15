package alpha.model.transformation.reduction

import alpha.model.AlphaExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.DependenceExpression
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.VariableExpression
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.EcoreUtil2

import static alpha.model.factory.AlphaUserFactory.*

import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.AlphaUtil.getContainerEquation
import static extension alpha.model.util.AlphaUtil.getContainerSystem
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.toISLSet

class PushDependenceIntoReduction {
	
	static def void apply(DependenceExpression de) {
		
		de.transform(de.expr)
		
	}
	
	
	static def dispatch transform(DependenceExpression de, ReduceExpression re) {
		if (!(de.eContainer instanceof StandardEquation))
			return null;
		
		val system = de.getContainerSystem
		val eq = de.getContainerEquation as StandardEquation
		
		val maff = de.function
		val indexNames = de.contextDomain.indexNames
		val paramNames = de.contextDomain.paramNames
		
		val D = maff.affs.zipWith(indexNames).fold(
			ISLSet.buildUniverse(de.contextDomain.space.copy),
			[ret, aff_name |
				val aff = aff_name.key
				val name = aff_name.value
				val set = '''
					[«paramNames.join(',')»]->{
						[«indexNames.join(',')»] : «name» = «aff.toString(ISL_FORMAT.C)»
					}
				'''.toString.toISLSet
				ret.intersect(set)
			]
		)
		
//		// restrict variable domain by D
//		val newVariable = createVariable(eq.variable.name, eq.variable.domain.copy.intersect(D.copy))
//		EcoreUtil.replace(eq.variable, newVariable)
//		
//		/*
//		 * Given:
//		 *   V1 = f@E1
//		 *   ...V1
//		 * 
//		 * Produce:
//		 *   V1 = E1
//		 *   ...f@V1
//		 */
//		 EcoreUtil.replace(de, de.expr.copyAE)
//		 
	//		 EcoreUtil2.getAllContentsOfType(system, VariableExpression)
	//		 	.filter[variable.name == eq.variable.name].forEach[ve |
	//		 		val newDe = createDependenceExpression(maff.copy, ve.copyAE)
	//		 		EcoreUtil.replace(ve, newDe)
	//		 	]
		
		AlphaInternalStateConstructor.recomputeContextDomain(system)
	}
	static def dispatch transform(DependenceExpression de, AlphaExpression ae) {}
}