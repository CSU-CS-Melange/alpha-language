package alpha.codegen.postprocessing

import alpha.codegen.Factory
import alpha.codegen.LoopStmt
import alpha.codegen.Statement
import alpha.codegen.isl.ASTConversionResult
import java.util.HashSet
import java.util.Set
import java.util.ArrayList
import java.util.List
import alpha.codegen.IfStmt
import alpha.codegen.ConditionalBranch
import org.eclipse.emf.common.util.EList

class OmpPragmaInserter extends CExpressionVisitor {
	int timeDim
	int loopDepth
	Set<String> publicVars
	Set<String> loopVars
	
	new(int timeDim) {
		this.timeDim = timeDim
		loopDepth = 0
		publicVars = new HashSet<String>
		loopVars = new HashSet<String>
	}
	
	def static apply(ASTConversionResult loopNest, int timeDim) {
		val inserter = new OmpPragmaInserter(timeDim)
		inserter.visitLoopNest(loopNest)
	}
	
	def protected void visitLoopNest(ASTConversionResult loopNest) {
		loopVars += loopNest.declarations
		if(timeDim == 0) 
			loopNest.statements.add(0, loopVars.buildMacro)
		
		loopNest.statements.forEach[visit]
	}
	
	override void inConditionalBranch(ConditionalBranch stmt) {
		if(loopDepth == timeDim) {
			stmt.body.appendPragmas
		}
	}
	
	override void inLoopStmt(LoopStmt stmt) {
		loopDepth++
		publicVars.add(stmt.loopVariable)
		
		if(loopDepth == timeDim) 
			stmt.body.appendPragmas
	}
	
	override void outLoopStmt(LoopStmt stmt) {
		loopDepth--
		publicVars.remove(stmt.loopVariable)
	}
	
	def protected void appendPragmas(EList<Statement> stmts) {
		val newStatements = new ArrayList<Statement>
			
		for(Statement statement : stmts) {
			if(statement instanceof LoopStmt) 
				newStatements += privateVars.buildMacro
			newStatements += statement
		}
		
		stmts.clear()
		stmts.addAll(newStatements)
	}
	
	def protected Statement buildMacro(Iterable<String> privateVars) {
		return Factory.customStmt("#pragma omp parallel for private(" + privateVars.join(",") + ")")
	}
	
	def protected getPrivateVars() {
		return loopVars.reject[publicVars.contains(it)]
	}
}