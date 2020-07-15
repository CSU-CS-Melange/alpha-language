/*
 * generated by Xtext 2.18.0.M3
 */
package alpha.targetmapping.scoping

import alpha.model.AlphaScheduleTarget
import alpha.targetmapping.ExtensionExpression
import alpha.targetmapping.ExtensionTarget
import alpha.targetmapping.FilterExpression
import alpha.targetmapping.ScheduleTargetRestrictDomain
import alpha.targetmapping.ScopingEntity
import alpha.targetmapping.TargetMapping
import alpha.targetmapping.TargetMappingForSystemBody
import alpha.targetmapping.TargetMappingNode
import alpha.targetmapping.TargetmappingPackage
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class TargetMappingScopeProvider extends AbstractTargetMappingScopeProvider {

		override getScope(EObject context, EReference reference) {
			
			//SystemBody
			if (context instanceof TargetMappingForSystemBody && reference == TargetmappingPackage.Literals.TARGET_MAPPING_FOR_SYSTEM_BODY__TARGET_BODY) {
				val tm = context.eContainer as TargetMapping
				val sbodies = tm.targetSystem.systemBodies
				return Scopes.scopeFor(sbodies, [sb|QualifiedName.create(sbodies.indexOf(sb)+"")], IScope.NULLSCOPE)
			}
			
			//AlphaScheduleTarget
			if (context instanceof ScheduleTargetRestrictDomain && reference == TargetmappingPackage.Literals.SCHEDULE_TARGET_RESTRICT_DOMAIN__SCHEDULE_TARGET) {
				
				//find the relevant scoping entity
				//  must skip FilterExpression/ExtensionExpression immediately surrounding the context
				val scopingEntity = 
					if (context.eContainer instanceof FilterExpression || context.eContainer instanceof ExtensionExpression) 
						EcoreUtil2.getContainerOfType(context.eContainer.eContainer, ScopingEntity)
					else
						EcoreUtil2.getContainerOfType(context, ScopingEntity)
				return constructScope(scopingEntity);
			}
//
//			if ((context instanceof SpaceTimeMapping || context instanceof MemoryMapping) && reference == TargetmappingPackage.Literals.ABSTRACT_MAPPING__SCHEDULE_TARGET) {
//				val tm = EcoreUtil2.getRootContainer(context) as TargetMapping
//				val scope = EcoreUtil2.getAllContentsOfType(tm.targetSystem, Variable)
//				return Scopes.scopeFor(scope.filter[eq|eq.name!==null], [eq|QualifiedName.create(eq.name)], IScope.NULLSCOPE)
//			}	
			
			super.getScope(context, reference)
			
		}
		
		private def dispatch constructScope(TargetMapping tm) {
			return findRootScope(tm).scheduleTargetsToScope
		}
		
		private def dispatch constructScope(TargetMappingForSystemBody tm) {
			return findRootScope(tm).scheduleTargetsToScope
		}
		
		private def dispatch constructScope(FilterExpression fe) {
			val scope = findRootScope(fe)
				
			val validTargets = fe.filterDomains.map[fd|fd.scheduleTarget.name]
			val filteredScope = scope.filter[t|validTargets.contains(t.name)]
			return filteredScope.scheduleTargetsToScope
		}

		private def dispatch constructScope(ExtensionExpression ee) {
			return (ee.extensionTargets as  Iterable<? extends AlphaScheduleTarget>).scheduleTargetsToScope
		}
		
		private def List<AlphaScheduleTarget> findRootScope(TargetMappingNode tmn) {
			if (tmn instanceof TargetMapping) {
				return EcoreUtil2.getAllContentsOfType(tmn.targetSystem, AlphaScheduleTarget)
			}
			if (tmn instanceof TargetMappingForSystemBody) {
				if (tmn.targetBody !== null)
					return EcoreUtil2.getAllContentsOfType(tmn.targetBody, AlphaScheduleTarget)
				else
					return findRootScope(tmn.eContainer as TargetMapping);
			}
			
			if (tmn.eContainer === null)
				throw new RuntimeException("Uncontained TargetMappingNode.");
			
			return findRootScope(tmn.eContainer as TargetMappingNode);
		}
		
		private def scheduleTargetsToScope(Iterable<? extends AlphaScheduleTarget> scope) {
			Scopes.scopeFor(scope.filter[eq|eq.name!==null], [eq|QualifiedName.create(eq.name)], IScope.NULLSCOPE)
		}
		
}
