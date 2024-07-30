package alpha.abft.codegen.util

import fr.irisa.cairn.jnimap.isl.IISLScheduleNodeVisitor
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

class ScheduleVisitor implements IISLScheduleNodeVisitor {
	
	def defaultVisit(ISLScheduleNode obj) {
		if (!obj.hasChildren) { return }
		for (i : 0..<obj.getNbChildren) {
			obj.child(i).accept(this)
		}
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
		obj.defaultVisit
	}
	
	override visitISLScheduleSetNode(ISLScheduleSetNode obj) {
		obj.defaultVisit
	}
	
}