package alpha.codegen

/**
 * Prints the simplified C AST into a C program.
 * 
 * The intended entry point is the "print(Program)" method.
 * However, any of the methods can be used.
 * 
 * There are four main kinds of methods:
 * 1. The "printExpr" methods prints any node inheriting from "Expression".
 * 2. The "printStmt" methods prints any node inheriting from "Statement".
 * 3. There are a few methods like "printDeclaration" which are for printing
 *    parts of a node or for printing nodes in a specific way.
 * 4. Most everything else is handled with a "print" method.
 */
class ProgramPrinter {
	/** Throws an exception to indicate a non-implemented function was reached. */
	def static CharSequence fault() {
		throw new Exception("Not implemented yet.")
	}
	

	////////////////////////////////////////////////
	// Programs and Includes
	////////////////////////////////////////////////
	
	def static print(Program program) '''
		«program.headerComment.printStmt»
		
		«FOR include : program.includes»
		«include.print»
		«ENDFOR»
		
		// Function Macros
		«FOR macro : program.functionMacros»
		«macro.printStmt»
		«ENDFOR»
		
		// Global Variables
		«FOR variable : program.globalVariables»
		«variable.print»
		«ENDFOR»
		
		// Memory Macros
		«FOR macro : program.memoryMacros»
		«macro.printStmt»
		«ENDFOR»
		
		// Function Declarations
		«FOR func : program.functions»
		«func.printDeclaration»
		«ENDFOR»
		
		«FOR func : program.functions»
		«func.printDefinition»
		
		«ENDFOR»
		
		// Undefine the Memory and Function Macros
		«FOR macro : program.memoryMacros»
		«macro.undefine»
		«ENDFOR»
		«FOR macro : program.functionMacros»
		«macro.undefine»
		«ENDFOR»
	'''
	
	def static print(Include include) {
		if (include.useQuotes) {
			return '''#include "«include.file»"'''
		} else {
			return '''#include <«include.file»>'''
		}
	}
	

	////////////////////////////////////////////////
	// Variables and Data Types
	////////////////////////////////////////////////
	
	def static print(BaseDataType type) {
		return switch type {
			case VOID: '''void'''
			case CHAR: '''char'''
			case INT: '''int'''
			case LONG: '''long'''
			case FLOAT: '''float'''
			case DOUBLE: '''double'''
			default: fault
		}
	}
	
	def static print(DataType type) {
		val stars = (0 ..< type.indirectionLevel).map['*'].join
		return '''«type.baseType.print»«stars»'''
	}
	
	def static print(VariableDecl decl) '''
		«decl.printStatic»«decl.dataType.print» «decl.name»;
	'''
	
	def static printStatic(VariableDecl decl) {
		if (decl.isStatic) {
			return '''static '''
		} else {
			return ''''''
		}
	}

	
	////////////////////////////////////////////////
	// Functions
	////////////////////////////////////////////////
	
	def static printDeclaration(Function function) '''
		«function.printSignature»;
	'''
	
	def static printDefinition(Function function) '''
		«function.printSignature» {
			«FOR decl : function.declarations»
			«decl.print»
			«ENDFOR»
			
			«FOR stmt : function.statements»
			«stmt.printStmt»
			«ENDFOR»
		}
	'''
	
	def static printSignature(Function function)
		'''«function.printStatic»«function.printInline»«function.returnType.print» «function.name»(«function.printParameters»)'''
	
	def static printInline(Function function) {
		if (function.isInline) {
			return '''inline '''
		} else {
			return ''''''
		}
	}
	
	def static printStatic(Function function) {
		if (function.isStatic) {
			return '''static '''
		} else {
			return ''''''
		}
	}
	
	def static printParameters(Function function) {
		return function.parameters.map[print].join(", ")
	}
	
	def static print(Parameter parameter) '''«parameter.dataType.print» «parameter.name»'''


	////////////////////////////////////////////////
	// Statements
	////////////////////////////////////////////////
	
	def static dispatch printStmt(EmptyLineStmt stmt) {
		return "\n"
	}
	
	def static dispatch printStmt(CommentStmt comment) '''
		«FOR line : comment.commentLines»
		// «line»
		«ENDFOR»
	'''
	
	def static dispatch printStmt(ExpressionStmt stmt) '''
		«stmt.expression.printExpr»;
	'''
	
	def static dispatch printStmt(MacroStmt stmt) {
		return '''#define «stmt.name»(«stmt.arguments.join(",")») «stmt.replacement.printExpr»'''
	}
	
	def static undefine(MacroStmt stmt) '''
		#undef «stmt.name»
	'''
	
	def static dispatch printStmt(UndefStmt stmt) '''
		#undef «stmt.name»
	'''
	
	def static dispatch CharSequence printStmt(IfStmt stmt) '''
		«stmt.ifBranch.printIf»
		«FOR elif : stmt.elseIfBranches»
		«elif.printElseIf»
		«ENDFOR»
		«IF stmt.elseBranch !== null»
		«stmt.elseBranch.printElse»
		«ENDIF»
	'''
	
	def static printIf(ConditionalBranch branch) '''
		if («branch.conditional.printExpr») {
			«FOR stmt : branch.body»
			«stmt.printStmt»
			«ENDFOR»
		}
	'''
	
	def static printElseIf(ConditionalBranch branch) '''
		else if («branch.conditional.printExpr») {
			«FOR stmt : branch.body»
			«stmt.printStmt»
			«ENDFOR»
		}
	'''
	
	def static printElse(Branch branch) '''
		else {
			«FOR stmt : branch.body»
			«stmt.printStmt»
			«ENDFOR»
		}
	'''
	
	def static dispatch CharSequence printStmt(LoopStmt loop) '''
		for («loop.loopVariable» = «loop.initializer.printExpr»; «loop.conditional.printExpr»; «loop.loopVariable» += «loop.incrementBy.printExpr») {
			«FOR stmt : loop.body»
			«stmt.printStmt»
			«ENDFOR»
		}
	'''
	
	def static dispatch printStmt(AssignmentStmt stmt) '''
		«stmt.printExpr»;
	'''
	
	def static dispatch printStmt(ReturnStmt stmt) '''
		return «stmt.expression.printExpr»;
	'''
	
	def static dispatch printStmt(Statement stmt) {
		fault
	}


	////////////////////////////////////////////////
	// Expressions
	////////////////////////////////////////////////
	
	def static dispatch printExpr(CustomExpr expr)
		'''«expr.expression»'''
	
	def static dispatch CharSequence printExpr(AssignmentStmt expr)
		'''«expr.left.printExpr» «expr.assignType.print» «expr.right.printExpr»'''
	
	def static print(AssignmentOperator op) {
		return switch op {
			case STANDARD: '''='''
			case PLUS: '''+='''
			case TIMES: '''*='''
			default: fault
		}
	}
	
	def static dispatch CharSequence printExpr(ParenthesizedExpr expr)
		'''(«expr.expression.printExpr»)'''
	
	def static dispatch CharSequence printExpr(CastExpr expr)
		'''(«expr.dataType.print»)(«expr.expression.printExpr»)'''
		
	def static dispatch CharSequence printExpr(ArrayAccessExpr expr){
		if(expr.indexExpressions.size == 0)
			'''«expr.variableName»[0]'''
		else
			'''«expr.variableName»«FOR index : expr.indexExpressions»[«index.printExpr»]«ENDFOR»'''
	}
	
	def static dispatch CharSequence printExpr(CallExpr expr)
		'''«expr.functionName»(«expr.arguments.map[printExpr].join(',')»)'''
	
	def static dispatch CharSequence printExpr(UnaryExpr expr)
		'''«expr.operator.print»(«expr.expression.printExpr»)'''
		
	def static print(UnaryOperator op) {
		return switch op {
			case NEGATE: '''-'''
			case NOT: '''!'''
			default: fault
		}
	}
	
	def static dispatch CharSequence printExpr(BinaryExpr expr) {
		if (expr.operator.isInfix) {
			return '''(«expr.left.printExpr») «expr.operator.print» («expr.right.printExpr»)'''
		} else {
			return '''«expr.operator.print»(«expr.left.printExpr», «expr.right.printExpr»)'''
		}
	}
	
	def static isInfix(BinaryOperator op) {
		return switch op {
			case MIN,
			case MAX: false
			default: true
		}
	}
	
	def static print(BinaryOperator op) {
		return switch op {
			case PLUS: "+"
			case MINUS: "-"
			case TIMES: "*"
			case DIVIDE: "/"
			case MODULUS: "%"
			case MIN: "min"
			case MAX: "max"
			case AND: "&&"
			case OR: "||"
			case LT: "<"
			case LEQ: "<="
			case GT: ">"
			case GEQ: ">="
			case EQ: "=="
			case NEQ: "!="
			default: fault
		}
	}
	
	def static dispatch CharSequence printExpr(TernaryExpr expr)
		'''(«expr.conditional.printExpr») ? («expr.thenExpr.printExpr») : («expr.elseExpr.printExpr»)'''
	
	def static dispatch printExpr(Expression expr) {
		fault
	}
}