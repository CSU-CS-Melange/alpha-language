package alpha.abft.codegen.util

import fr.irisa.cairn.jnimap.isl.IISLASTNodeVisitor
import fr.irisa.cairn.jnimap.isl.ISLASTBlockNode
import fr.irisa.cairn.jnimap.isl.ISLASTForNode
import fr.irisa.cairn.jnimap.isl.ISLASTIfNode
import fr.irisa.cairn.jnimap.isl.ISLASTMarkNode
import fr.irisa.cairn.jnimap.isl.ISLASTNode
import fr.irisa.cairn.jnimap.isl.ISLASTUnscannedNode
import fr.irisa.cairn.jnimap.isl.ISLASTUserNode
import java.util.List
import fr.irisa.cairn.jnimap.isl.ISLASTNodeList

class ISLASTNodeVisitor implements IISLASTNodeVisitor {
	
	String code
	int indentLevel
	
	def String indent() {
		(0..<indentLevel).map['  '].join('')
	}
	
	def static List<ISLASTNode> toList(ISLASTNodeList nodeList) {
		(0..<nodeList.getNbNodes).map[nodeList.get(it).copy].toList
	}
	
	new() {
		indentLevel = 0
	}
	
	def ISLASTNodeVisitor genC(ISLASTNode node) {
		node.accept(this)
		this
	}
	
	def String toCode() {
		code
	}
	
	def inISLASTBlockNode(ISLASTBlockNode obj) {}	
	override visitISLASTBlockNode(ISLASTBlockNode obj) {
		obj.inISLASTBlockNode
		for (child : obj.children.toList) {
			child.accept(this)
		}
		obj.outISLASTBlockNode
	}
	def outISLASTBlockNode(ISLASTBlockNode obj) {}
	
	
	def inISLASTForNode(ISLASTForNode obj) {
		code = '''
			«code»
			«indent»for (int «obj.iterator» = «obj.init»; «obj.cond»; «obj.iterator» += «obj.inc») {
		'''
	}
	override visitISLASTForNode(ISLASTForNode obj) {
		obj.inISLASTForNode
		indentLevel += 1
		obj.body.accept(this)
		indentLevel -= 1
		obj.outISLASTForNode
	}
	def outISLASTForNode(ISLASTForNode obj) {
		code = '''
			«code»
			«indent»}
		'''
	}
	
	def inISLASTIfNode(ISLASTIfNode obj) {
		code = '''
			«code»
			«indent»if («obj.cond») {
		'''
	}
	override visitISLASTIfNode(ISLASTIfNode obj) {
		obj.inISLASTIfNode
		indentLevel += 1
		obj.then.accept(this)
		indentLevel -= 1
		if (obj.hasElse == 1) {
			code = '''
				«code»
				«indent»} else {
			'''
			indentLevel += 1
			obj.getElse.accept(this)
			indentLevel -= 1
		}
		obj.outISLASTIfNode
	}
	def outISLASTIfNode(ISLASTIfNode obj) {
		code = '''
			«code»
			«indent»}
		'''
	}
	
	def inISLASTMarkNode(ISLASTMarkNode obj) {}
	override visitISLASTMarkNode(ISLASTMarkNode obj) {
		obj.inISLASTMarkNode
		obj.outISLASTMarkNode
	}
	def outISLASTMarkNode(ISLASTMarkNode obj) {}
	
	def inISLASTNode(ISLASTNode obj) {}
	override visitISLASTNode(ISLASTNode obj) {
		obj.inISLASTNode
		obj.outISLASTNode
	}
	def outISLASTNode(ISLASTNode obj) {}
	
	def inISLASTUnscannedNode(ISLASTUnscannedNode obj) {}
	override visitISLASTUnscannedNode(ISLASTUnscannedNode obj) {
		obj.inISLASTUnscannedNode
		obj.outISLASTUnscannedNode
	}
	def outISLASTUnscannedNode(ISLASTUnscannedNode obj) {}
	
	def inISLASTUserNode(ISLASTUserNode obj) {}
	override visitISLASTUserNode(ISLASTUserNode obj) {
		obj.inISLASTUserNode
		obj.outISLASTUserNode
	}
	def outISLASTUserNode(ISLASTUserNode obj) {
		code = '''
			«code»
			«indent»«obj.expression»;
		'''
	}
	
}