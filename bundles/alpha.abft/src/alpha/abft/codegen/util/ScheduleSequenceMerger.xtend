package alpha.abft.codegen.util

import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLScheduleBandNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleContextNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleDomainNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleExpansionNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleExtensionNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleFilterNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleGuardNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleLeafNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleMarkNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleSequenceNode
import fr.irisa.cairn.jnimap.isl.ISLScheduleSetNode
import java.util.Set


class ScheduleSequenceMerger extends ScheduleVisitor {
	
	Set<ISLScheduleFilterNode> mergeTargets
	ISLScheduleFilterNode newFilterNode
	ISLScheduleNode parent
	ISLSchedule newSchedule
	
	new (Set<ISLScheduleFilterNode> nodes) {
		nodes.assertSameParent
		mergeTargets = nodes
		parent = nodes.get(0).ancestor(1)
		newSchedule = null
	}
	
	def assertSameParent(Set<ISLScheduleFilterNode> nodes) {
		val parents = nodes.map[it.copy.ancestor(1) as ISLScheduleNode]
		val ok = parents.map[it.isSamePositionInTheSameSchedule(parents.get(0))].reduce(v0,v1|v0==v1 && v0==true)
		if (!ok) {
			throw new Exception("filter nodes must have the same parent in order to be merged")
		}
	}
	
	def static ISLScheduleNode merge(Set<ISLScheduleFilterNode> nodes) {
		val visitor = new ScheduleSequenceMerger(nodes)
		visitor.parent.accept(visitor)
		visitor.newFilterNode
	}
	
	override visitISLScheduleBandNode(ISLScheduleBandNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleContextNode(ISLScheduleContextNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleDomainNode(ISLScheduleDomainNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleExpansionNode(ISLScheduleExpansionNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleExtensionNode(ISLScheduleExtensionNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleFilterNode(ISLScheduleFilterNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleGuardNode(ISLScheduleGuardNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleLeafNode(ISLScheduleLeafNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleMarkNode(ISLScheduleMarkNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleNode(ISLScheduleNode obj) {
		obj.defaultVisit
	}
	
	override visitISLScheduleSequenceNode(ISLScheduleSequenceNode obj) {
		if (!obj.isSamePositionInTheSameSchedule(parent)) { 
			super.visitISLScheduleSequenceNode(obj)
			return
		}
		
		println
	} 
	
	override visitISLScheduleSetNode(ISLScheduleSetNode obj) {
		obj.defaultVisit
	}
	
}