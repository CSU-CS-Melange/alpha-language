package alpha.codegen.postprocessing

import alpha.codegen.EmptyLineStmt
import alpha.codegen.CommentStmt
import alpha.codegen.ExpressionStmt
import alpha.codegen.MacroStmt
import alpha.codegen.UndefStmt
import alpha.codegen.IfStmt
import alpha.codegen.LoopStmt
import alpha.codegen.AssignmentStmt
import alpha.codegen.ReturnStmt
import alpha.codegen.Statement
import alpha.codegen.Branch
import alpha.codegen.ConditionalBranch
import alpha.codegen.Program
import alpha.codegen.Function
import alpha.codegen.Include
import alpha.codegen.VariableDecl

abstract class CStatementVisitor {
	def dispatch void visit(Void v)						{}
	def dispatch void visit(Program program)			{program.visitProgram}
	
	def dispatch void visit(Function func)				{func.visitFunction}
	
	def dispatch void visit(Include incl)				{incl.visitInclude}
	
	def dispatch void visit(VariableDecl decl)			{decl.visitVariableDecl}
	
	def dispatch void visit(Statement stmt) 			{stmt.visitStatement}
	def dispatch void visit(EmptyLineStmt stmt) 		{stmt.visitEmptyLineStmt}
	def dispatch void visit(CommentStmt stmt) 			{stmt.visitCommentStmt}
	def dispatch void visit(ExpressionStmt stmt) 		{stmt.visitExpressionStmt}
	def dispatch void visit(MacroStmt stmt) 			{stmt.visitMacroStmt}
	def dispatch void visit(UndefStmt stmt) 			{stmt.visitUndefStmt}
	def dispatch void visit(IfStmt stmt) 				{stmt.visitIfStmt}
	def dispatch void visit(LoopStmt stmt) 				{stmt.visitLoopStmt}
	def dispatch void visit(AssignmentStmt stmt) 		{stmt.visitAssignmentStmt}
	def dispatch void visit(ReturnStmt stmt) 			{stmt.visitReturnStmt}
	
	def dispatch void visit(Branch branch)				{branch.visitBranch}
	def dispatch void visit(ConditionalBranch branch)	{branch.visitConditionalBranch}
	
	
	def void visitProgram(Program program) {
		program.includes.forEach[visit]
		program.globalVariables.forEach[visit]
		program.functions.forEach[visit]
	}
	
	def void visitFunction(Function func) {
		func.inFunction
		func.declarations.forEach[visit]
		func.statements.forEach[visit]
		func.outFunction
	}
	
	def void visitInclude(Include incl) {
		incl.inInclude
		incl.outInclude
	}
	
	def void visitVariableDecl(VariableDecl decl) {
		decl.inVariableDecl
		decl.outVariableDecl
	}
	
	def void visitStatement(Statement stmt) {
		stmt.inStatement
		stmt.outStatement
	}
	
	def void visitEmptyLineStmt(EmptyLineStmt stmt) {
		stmt.inEmptyLineStmt
		stmt.outEmptyLineStmt
	}
	
	def void visitCommentStmt(CommentStmt stmt) {
		stmt.inCommentStmt
		stmt.outCommentStmt
	}
	
	def void visitExpressionStmt(ExpressionStmt stmt) {
		stmt.inExpressionStmt
		stmt.outExpressionStmt
	}
	
	def void visitMacroStmt(MacroStmt stmt) {
		stmt.inMacroStmt
		stmt.outMacroStmt
	}
	
	def void visitUndefStmt(UndefStmt stmt) {
		stmt.inUndefStmt
		stmt.outUndefStmt
	}
	
	def void visitIfStmt(IfStmt stmt) {
		stmt.inIfStmt
		stmt.ifBranch.visit
		stmt.elseIfBranches.forEach[visit]
		stmt.elseBranch.visit
		stmt.outIfStmt
	}
	
	def void visitLoopStmt(LoopStmt stmt) {
		stmt.inLoopStmt
		stmt.body.forEach[visit]
		stmt.outLoopStmt
	}
	
	def void visitAssignmentStmt(AssignmentStmt stmt) {
		stmt.inAssignmentStmt
		stmt.outAssignmentStmt
	}
	
	def void visitReturnStmt(ReturnStmt stmt) {
		stmt.inReturnStmt
		stmt.outReturnStmt
	}
	
	def void visitBranch(Branch branch) {
		branch.inBranch
		branch.body.forEach[visit]
		branch.outBranch
	}
	
	def void visitConditionalBranch(ConditionalBranch branch) {
		branch.inConditionalBranch
		branch.body.forEach[visit]
		branch.outConditionalBranch
	}
	
	
	def void inFunction(Function func) {}
	def void inInclude(Include incl) {}
	def void inVariableDecl(VariableDecl decl) {}
	def void inStatement(Statement statement) {}
	def void inEmptyLineStmt(EmptyLineStmt stmt) {}
	def void inCommentStmt(CommentStmt stmt) {}
	def void inExpressionStmt(ExpressionStmt stmt) {}
	def void inMacroStmt(MacroStmt stmt) {}
	def void inUndefStmt(UndefStmt stmt) {}
	def void inIfStmt(IfStmt stmt) {}
	def void inLoopStmt(LoopStmt stmt) {}
	def void inAssignmentStmt(AssignmentStmt stmt) {}
	def void inReturnStmt(ReturnStmt stmt) {}
	def void inBranch(Branch branch) {}
	def void inConditionalBranch(ConditionalBranch branch) {}
	
	
	def void outFunction(Function func) {}
	def void outInclude(Include incl) {}
	def void outVariableDecl(VariableDecl decl) {}
	def void outStatement(Statement statement) {}
	def void outEmptyLineStmt(EmptyLineStmt stmt) {}
	def void outCommentStmt(CommentStmt stmt) {}
	def void outExpressionStmt(ExpressionStmt stmt) {}
	def void outMacroStmt(MacroStmt stmt) {}
	def void outUndefStmt(UndefStmt stmt) {}
	def void outIfStmt(IfStmt stmt) {}
	def void outLoopStmt(LoopStmt stmt) {}
	def void outAssignmentStmt(AssignmentStmt stmt) {}
	def void outReturnStmt(ReturnStmt stmt) {}
	def void outBranch(Branch branch) {}
	def void outConditionalBranch(ConditionalBranch branch) {}
}