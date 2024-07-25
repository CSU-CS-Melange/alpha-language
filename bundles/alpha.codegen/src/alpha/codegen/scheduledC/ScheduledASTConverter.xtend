package alpha.codegen.scheduledC

import alpha.codegen.isl.ASTConverter
import alpha.codegen.isl.ASTConversionResult
import fr.irisa.cairn.jnimap.isl.ISLASTBlockNode
import fr.irisa.cairn.jnimap.isl.ISLASTForNode
import alpha.codegen.Factory

class ScheduledASTConverter extends ASTConverter {
		/** A "block" node just contains more nodes within it. */
	def static dispatch ASTConversionResult convert(ISLASTBlockNode node) {
		val childrenList = node.getChildren()
		val results = (0 ..< childrenList.nbNodes)
			.map[childrenList.get(it).convert]

		val declarations = results.flatMap[it.declarations]
		val statements = results.flatMap[it.statements]
		
		return new ASTConversionResult(declarations, statements)
	}
	
		/** A "for" node corresponds to a loop statement. */
	def static dispatch ASTConversionResult convert(ISLASTForNode node) {
		// Get the information about this loop.
		val loopVariable = node.iterator.toCString
		val initializer = node.init.customExpr
		val conditional = node.cond.customExpr
		val incrementBy = node.inc.customExpr
		
		// Convert the contents of the loop and use it to construct this loop.
		// Put the current loop variable at the start of the list of declared variables,
		// since this one will be encountered before any child declarations.
		val bodyResult = node.body.convert
				
		val declarations = newArrayList(loopVariable)
		declarations.addAll(bodyResult.declarations)
				
		val statement = Factory.loopStmt(loopVariable, initializer, conditional, incrementBy, bodyResult.statements)
		
		return new ASTConversionResult(declarations, statement)
	}
}