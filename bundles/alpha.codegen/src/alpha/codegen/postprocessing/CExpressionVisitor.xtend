package alpha.codegen.postprocessing

import alpha.codegen.Expression
import alpha.codegen.CustomExpr
import alpha.codegen.ParenthesizedExpr
import alpha.codegen.CastExpr
import alpha.codegen.ArrayAccessExpr
import alpha.codegen.CallExpr
import alpha.codegen.UnaryExpr
import alpha.codegen.BinaryExpr
import alpha.codegen.TernaryExpr
import alpha.codegen.ExpressionStmt
import alpha.codegen.MacroStmt
import alpha.codegen.ConditionalBranch
import alpha.codegen.LoopStmt
import alpha.codegen.AssignmentStmt
import alpha.codegen.ReturnStmt
import alpha.codegen.Program

class CExpressionVisitor extends CStatementVisitor {
	def dispatch void visit(Expression expr)			{expr.visitExpression}
	def dispatch void visit(CustomExpr expr) 			{expr.visitCustomExpr}
	def dispatch void visit(ParenthesizedExpr expr) 	{expr.visitParenthesizedExpr}
	def dispatch void visit(CastExpr expr)				{expr.visitCastExpr}
	def dispatch void visit(ArrayAccessExpr expr) 		{expr.visitArrayAccessExpr}
	def dispatch void visit(CallExpr expr) 				{expr.visitCallExpr}
	def dispatch void visit(UnaryExpr expr) 			{expr.visitUnaryExpr}
	def dispatch void visit(BinaryExpr expr) 			{expr.visitBinaryExpr}
	def dispatch void visit(TernaryExpr expr) 			{expr.visitTernaryExpr}
	
	override void visitProgram(Program program) {
		program.headerComment.visit
		program.includes.forEach[visit]
		program.functionMacros.forEach[visit]
		program.globalVariables.forEach[visit]
		program.memoryMacros.forEach[visit]
		program.functions.forEach[visit]
	}
	
	override void visitExpressionStmt(ExpressionStmt stmt) {
		stmt.inExpressionStmt
		stmt.expression.visit
		stmt.outExpressionStmt
	}
	
	override void visitMacroStmt(MacroStmt stmt) {
		stmt.inMacroStmt
		stmt.replacement.visit
		stmt.outMacroStmt
	}
	
	override void visitLoopStmt(LoopStmt stmt) {
		stmt.inLoopStmt
		stmt.initializer.visit
		stmt.conditional.visit
		stmt.incrementBy.visit
		stmt.body.forEach[visit]
		stmt.outLoopStmt
	}
	
	override void visitAssignmentStmt(AssignmentStmt stmt) {
		stmt.inAssignmentStmt
		stmt.left.visit
		stmt.right.visit
		stmt.outAssignmentStmt
	}
	
	override void visitReturnStmt(ReturnStmt stmt) {
		stmt.inReturnStmt
		stmt.expression.visit
		stmt.outReturnStmt
	}
	
	override void visitConditionalBranch(ConditionalBranch branch) {
		branch.inConditionalBranch
		branch.conditional.visit
		branch.body.forEach[visit]
		branch.outConditionalBranch
	}
	
	
	def void visitExpression(Expression expr) {
		expr.inExpression
		expr.outExpression
	}
	
	def void visitCustomExpr(CustomExpr expr) {
		expr.inCustomExpr
		expr.outCustomExpr
	}
	
	def void visitParenthesizedExpr(ParenthesizedExpr expr) 	{
		expr.inParenthesizedExpr
		expr.expression.visit
		expr.outParenthesizedExpr
	}
	
	def void visitCastExpr(CastExpr expr)						{
		expr.inCastExpr
		expr.expression.visit
		expr.outCastExpr
	}
	
	def void visitArrayAccessExpr(ArrayAccessExpr expr) 		{
		expr.inArrayAccessExpr
		expr.indexExpressions.forEach[visit]
		expr.outArrayAccessExpr
	}
	
	def void visitCallExpr(CallExpr expr) 						{
		expr.inCallExpr
		expr.arguments.forEach[visit]
		expr.outCallExpr
	}
	
	def void visitUnaryExpr(UnaryExpr expr) 					{
		expr.inUnaryExpr
		expr.expression.visit
		expr.outUnaryExpr
	}
	
	def void visitBinaryExpr(BinaryExpr expr) 					{
		expr.inBinaryExpr
		expr.left.visit
		expr.right.visit
		expr.outBinaryExpr
	}
	
	def void visitTernaryExpr(TernaryExpr expr) 				{
		expr.inTernaryExpr
		expr.conditional.visit
		expr.thenExpr.visit
		expr.elseExpr.visit
		expr.outTernaryExpr
	}
	
	
	def void inExpression(Expression expr) 						{}
	def void inCustomExpr(CustomExpr expr) 						{}
	def void inParenthesizedExpr(ParenthesizedExpr expr) 		{}
	def void inCastExpr(CastExpr expr)							{}
	def void inArrayAccessExpr(ArrayAccessExpr expr) 			{}
	def void inCallExpr(CallExpr expr) 							{}
	def void inUnaryExpr(UnaryExpr expr) 						{}
	def void inBinaryExpr(BinaryExpr expr) 						{}
	def void inTernaryExpr(TernaryExpr expr) 					{}
	
	
	def void outExpression(Expression expr) 					{}
	def void outCustomExpr(CustomExpr expr) 					{}
	def void outParenthesizedExpr(ParenthesizedExpr expr) 		{}
	def void outCastExpr(CastExpr expr)							{}
	def void outArrayAccessExpr(ArrayAccessExpr expr) 			{}
	def void outCallExpr(CallExpr expr) 						{}
	def void outUnaryExpr(UnaryExpr expr) 						{}
	def void outBinaryExpr(BinaryExpr expr) 					{}
	def void outTernaryExpr(TernaryExpr expr) 					{}
}