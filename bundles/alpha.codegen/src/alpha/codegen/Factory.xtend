package alpha.codegen

/**
 * A collection of static helper methods for creating AST nodes for the simplified C AST.
 * Some methods are used as a short-hand for populating the AST nodes with some
 * default information and indicate as such.
 * 
 * For more information on what these AST nodes represent and how to populate them,
 * see "model/simpleC.xcore".
 */
class Factory {
	/** The eCore instance of the factory for creating new object instances. */
	protected static val factory = CodegenFactory.eINSTANCE

	////////////////////////////////////////////////
	// Programs and Includes
	////////////////////////////////////////////////
	
	/** Creates a "#include" directive using angle brackets (&lt; and &gt;). */
	def static include(String file) {
		include(file, false)
	}
	
	def static include(String file, boolean useQuotes) {
		val include = factory.createInclude
		include.file = file
		include.useQuotes = useQuotes
		return include
	} 
	
	
	////////////////////////////////////////////////
	// Variables and Data Types
	////////////////////////////////////////////////
	
	/** Creates a non-pointer data type. */
	def static dataType(BaseDataType baseType) {
		return dataType(baseType, 0)
	}
	
	def static dataType(BaseDataType baseType, int indirectionLevel) {
		val dataType = factory.createDataType
		dataType.baseType = baseType
		dataType.indirectionLevel = indirectionLevel
		return dataType
	}
	
	def static variableDecl(DataType dataType, String name) {
		return variableDecl(false, dataType, name)
	}
	
	def static variableDecl(boolean isStatic, DataType dataType, String name) {
		val variableDecl = factory.createVariableDecl
		variableDecl.isStatic = isStatic
		variableDecl.dataType = dataType
		variableDecl.name = name
		return variableDecl
	}


	////////////////////////////////////////////////
	// Functions
	////////////////////////////////////////////////
	
	def static parameter(DataType dataType, String name) {
		val parameter = factory.createParameter
		parameter.dataType = dataType
		parameter.name = name
		return parameter
	}


	////////////////////////////////////////////////
	// Statements
	////////////////////////////////////////////////
	
	def static emptyLineStmt() {
		return factory.createEmptyLineStmt
	}
	
	def static commentStmt(String... commentLines) {
		val comment = factory.createCommentStmt
		comment.addAll(commentLines)
		return comment
	}
	
	def static expressionStmt(Expression expression) {
		val stmt = factory.createExpressionStmt
		stmt.expression = expression
		return stmt
	}
	
	def static macroStmt(String name, String[] arguments, String replacement) {
		val replacementExpr = replacement.customExpr
		return macroStmt(name, arguments, replacementExpr)
	}
	
	def static macroStmt(String name, String[] arguments, Expression replacement) {
		val macro = factory.createMacroStmt
		macro.name = name
		macro.arguments.addAll(arguments)
		macro.replacement = replacement
		return macro
	}
	
	def static undefStmt(String name) {
		val undef = factory.createUndefStmt
		undef.name = name
		return undef
	}
	
	def static loopStmt(String loopVariable, Expression initializer, Expression conditional, Expression incrementBy, Statement... body) {
		val stmt = factory.createLoopStmt
		stmt.loopVariable = loopVariable
		stmt.initializer = initializer
		stmt.conditional = conditional
		stmt.incrementBy = incrementBy
		stmt.body.addAll(body)
		return stmt
	}
	
	def static assignmentStmt(String left, String right) {
		return assignmentStmt(left.customExpr, right.customExpr)
	}
	
	def static assignmentStmt(String left, Expression right) {
		return assignmentStmt(left.customExpr, right)
	}
	
	def static assignmentStmt(Expression left, Expression right) {
		return assignmentStmt(left, AssignmentOperator.STANDARD, right)
	}
	
	def static assignmentStmt(Expression left, AssignmentOperator assignType, Expression right) {
		val stmt = factory.createAssignmentStmt
		stmt.left = left
		stmt.assignType = assignType
		stmt.right = right
		return stmt
	}
	
	def static returnStmt(Expression expression) {
		val stmt = factory.createReturnStmt
		stmt.expression = expression
		return stmt
	}
	
	def static customStmt(String stmt) {
		return stmt.customExpr.expressionStmt
	}
	
	def static callStmt(String functionName, String... arguments) {
		return callExpr(functionName, arguments).expressionStmt
	}
	
	def static callStmt(String functionName, Expression... arguments) {
		return callExpr(functionName, arguments).expressionStmt
	}
	
	/** Creates a call to "exit". */
	def static exitCall(int exitCode) {
		return callStmt("exit", customExpr(exitCode.toString))
	}
	
	/**
	 * Creates a call to "printf".
	 * The format string is used exactly as-provided,
	 * so make sure you wrap it in quotation mark characters.
	 */
	def static printfCall(String format, String... arguments) {
		val allArgs = newArrayList
		allArgs.add(format)
		allArgs.addAll(arguments)
		return callStmt("printf", allArgs)
	}


	////////////////////////////////////////////////
	// Expressions
	////////////////////////////////////////////////
	
	def static customExpr(String expression) {
		val expr = factory.createCustomExpr
		expr.expression = expression
		return expr
	}
	
	/** Creates a custom expression wrapped in a parenthesized expression. */
	def static parenthesizedExpr(String expression) {
		return expression.customExpr.parenthesizedExpr
	}
	
	def static parenthesizedExpr(Expression expression) {
		val expr = factory.createParenthesizedExpr
		expr.expression = expression
		return expr
	}
	
	def static castExpr(DataType dataType, Expression expression) {
		val expr = factory.createCastExpr
		expr.dataType = dataType
		expr.expression = expression
		return expr
	}
	
	/** Indexes an array using custom expressions for the indices. */
	def static arrayAccessExpr(String variableName, String... indexExpressions) {
		return arrayAccessExpr(variableName, indexExpressions.map[customExpr])
	}
	
	def static arrayAccessExpr(String variableName, Expression... indexExpressions) {
		val expr = factory.createArrayAccessExpr
		expr.variableName = variableName
		expr.indexExpressions.addAll(indexExpressions)
		return expr
	}
	
	def static callExpr(String functionName, String... arguments) {
		return callExpr(functionName, arguments.map[customExpr])
	}
	
	def static callExpr(String functionName, Expression... arguments) {
		val expr = factory.createCallExpr
		expr.functionName = functionName
		expr.arguments.addAll(arguments)
		return expr
	}
	
	/**
	 * Creates a call to which allocates the desired number of bytes,
	 * then casts it as the appropriate data type.
	 */
	def static mallocCall(DataType dataType, Expression amount) {
		// First, we need to call "sizeof" on the data type to know
		// how large each element being allocated is.
		// We need to remove one level of indirection first.
		val sizeofType = Factory.dataType(dataType.baseType, dataType.indirectionLevel - 1)
		val dataTypeExpr = customExpr(ProgramPrinter.print(sizeofType))
		val sizeofCall = callExpr("sizeof", dataTypeExpr)
		
		// Next, we multiply that by the number of elements being allocated
		// and pass that in to "malloc".
		val bytesAllocated = binaryExpr(BinaryOperator.TIMES, sizeofCall, amount)
		val mallocCall = callExpr("malloc", bytesAllocated)
		
		// Finally, cast it as the correct data type.
		return castExpr(dataType, mallocCall)
	}
	
	/**
	 * Creates a call to which allocates the desired number of bytes,
	 * then casts it as the appropriate data type.
	 */
	def static callocCall(DataType dataType, Expression amount) {
		// First, we need to call "sizeof" on the data type to know
		// how large each element being allocated is.
		// We need to remove one level of indirection first.
		val sizeofType = Factory.dataType(dataType.baseType, dataType.indirectionLevel - 1)
		val dataTypeExpr = customExpr(ProgramPrinter.print(sizeofType))
		val sizeofCall = callExpr("sizeof", dataTypeExpr)
		val args = #[amount, sizeofCall]
		
		val mallocCall = callExpr("calloc", args)
		
		// Finally, cast it as the correct data type.
		return castExpr(dataType, mallocCall)
	}
	
	def static unaryExpr(UnaryOperator operator, Expression expression) {
		val expr = factory.createUnaryExpr
		expr.operator = operator
		expr.expression = expression
		return expr
	}
	
	/** Creates a tree of binary expressions which use the same operator. */
	static def binaryExprTree(BinaryOperator operator, Expression... exprs) {
		return exprs.tail.fold(exprs.head, [left, right | binaryExpr(operator, left, right)]) 
	}
	
	def static binaryExpr(BinaryOperator operator, Expression left, Expression right) {
		val expr = factory.createBinaryExpr
		expr.operator = operator
		expr.left = left
		expr.right = right
		return expr
	}
	
	def static ternaryExpr(Expression conditional, Expression thenExpr, Expression elseExpr) {
		val expr = factory.createTernaryExpr
		expr.conditional = conditional
		expr.thenExpr = thenExpr
		expr.elseExpr = elseExpr
		return expr
	}
}