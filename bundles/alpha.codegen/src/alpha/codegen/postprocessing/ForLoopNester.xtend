package alpha.codegen.postprocessing

import alpha.codegen.isl.ASTConversionResult
import alpha.codegen.CallExpr
import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy
import alpha.codegen.ExpressionStmt
import alpha.codegen.Branch
import alpha.codegen.Statement
import alpha.codegen.ConditionalBranch
import alpha.codegen.IfStmt
import alpha.codegen.LoopStmt
import org.eclipse.emf.common.util.EList
import alpha.codegen.CustomExpr

class ForLoopNester extends CExpressionVisitor {
	ASTConversionResult innerNest
	String toReplace
	
	
	new(ASTConversionResult innerNest, String toReplace) {
		this.innerNest = innerNest
		this.toReplace = toReplace
	}
	
	def static apply(ASTConversionResult outerNest, ASTConversionResult innerNest, String toReplace) {
		val nester = new ForLoopNester(innerNest, toReplace)
		nester.visitLoopNest(outerNest)
	}
	
	def protected void visitLoopNest(ASTConversionResult loopNest) {
		loopNest.statements.forEach[visit]
	}
	
	override void outBranch(Branch branch) {
		val newStatements = branch.body
			.flatMap[replaceStatement]
		
		branch.body.clear()
		branch.body.addAll(newStatements)
	}
	
	override void outConditionalBranch(ConditionalBranch branch) {
		val newStatements = branch.body
			.flatMap[replaceStatement]
		
		branch.body.clear()
		branch.body.addAll(newStatements)
	}
	
	override void outLoopStmt(LoopStmt stmt) {
		
		val newStatements = stmt.body
			.flatMap[replaceStatement]
			.toList
		
		stmt.body.clear()
		stmt.body.addAll(newStatements)
	}
	
	def protected Iterable<Statement> replaceStatement(Statement stmt) {
		if(stmt instanceof ExpressionStmt) {
			if(stmt.expression instanceof CustomExpr) {
				val s = (stmt.expression as CustomExpr).expression
				val functionName = s.substring(0, s.indexOf("("))
				if(functionName == toReplace) {
					return innerNest.statements
				}
			}
		}
		
		return #[stmt]
	}
}