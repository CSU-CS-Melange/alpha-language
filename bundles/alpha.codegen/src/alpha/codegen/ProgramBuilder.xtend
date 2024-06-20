package alpha.codegen

/**
 * A builder to aid in the construction of C programs.
 * 
 * To start building, use the static "start" method.
 * To get the final result, use the "getInstance()" method.
 * 
 * Each region of the program (header comment, includes, etc.)
 * can be added to at any point. The various regions are printed
 * in a deterministic order regardless of when you populated them.
 * However, elements within each region will appear in the order
 * you added them.
 */
class ProgramBuilder {
	////////////////////////////////////////////////
	// Program Instance
	////////////////////////////////////////////////
	
	/** The program instance being built. */
	protected val Program instance
	
	/** A name checker to ensure all names are unique. */
	protected val NameChecker nameChecker
	
	/** Retrieves the instance of the program which was built. */
	def getInstance() {
		return instance
	}
	
	/** Retrieves the name checker being used for building this program. */
	def getNameChecker() {
		return nameChecker
	}
	

	////////////////////////////////////////////////
	// Builder Construction
	////////////////////////////////////////////////
	
	/** Starts building a new program using the given name checker. */
	static def start(NameChecker nameChecker) {
		return new ProgramBuilder(nameChecker)
	}
	
	/** Protected constructor. */
	protected new(NameChecker nameChecker) {
		val factory = CodegenFactory.eINSTANCE
		instance = factory.createProgram
		instance.headerComment = factory.createCommentStmt
		this.nameChecker = nameChecker
	}
	

	////////////////////////////////////////////////
	// Program Building
	////////////////////////////////////////////////
	
	/** Adds lines to the program's header comment. */
	def addHeaderComment(String... commentLines) {
		instance.headerComment.addAll(commentLines.filterNull)
		return this
	}
	
	/** Adds #include directives to the program. */
	def addInclude(Include... includes) {
		instance.includes.addAll(includes.filterNull)
		return this
	}
	
	/**
	 * Adds macros to the "function macros" section of the program.
	 * Intended to be used for defining common functions like min(a,b) or floor(a).
	 * If any of the names are duplicates of anything else in the global scope,
	 * a NameConflictException will be thrown.
	 */
	def addFunctionMacro(MacroStmt... macros) {
		val filtered = macros.filterNull
		if (!filtered.empty) {
			// If the name is a duplicate, then an exception will be thrown here.
			filtered.forEach[nameChecker.checkAddGlobal(it.name)]
			instance.functionMacros.addAll(filtered)
		}
		return this
	}
	
	/**
	 * Declares a new global variable.
	 * If the variable to add is an exact duplicate of an existing one,
	 * it will be silently skipped.
	 * If it has the same name as an existing one, but different type,
	 * an IllegalArgumentException will be thrown.
	 * Otherwise, the variable will be added.
	 */
	def addGlobalVariable(boolean isStatic, DataType dataType, String name) {
		val decl = Factory.variableDecl(isStatic, dataType, name)
		return addGlobalVariable(decl)
	}
	
	/**
	 * Adds global variable declarations.
	 * If any of the names are duplicates of anything else in the global scope,
	 * a NameConflictException will be thrown.
	 */
	def addGlobalVariable(VariableDecl... variables) {
		val filtered = variables.filterNull
		if (!filtered.empty) {
			// If the name is a duplicate, then an exception will be thrown here.
			filtered.forEach[nameChecker.checkAddGlobal(it.name)]
			instance.globalVariables.addAll(filtered)
		}
		return this
	}
	
	/**
	 * Adds macros to the "memory macros" section of the program.
	 * Intended to be used for defining how the global variables are accessed.
	 * Name checking is not performed, as we often want the memory macro's name
	 * to match a global variable's name.
	 */
	def addMemoryMacro(MacroStmt... macros) {
		instance.memoryMacros.addAll(macros.filterNull)
		return this
	}
	
	/**
	 * Adds functions to the program.
	 * If any of the names are duplicates of anything else in the global scope,
	 * a NameConflictException will be thrown.
	 */
	def addFunction(Function... functions) {
		val filtered = functions.filterNull
		if (!filtered.empty) {
			// If the name is a duplicate, then an exception will be thrown here.
			filtered.forEach[nameChecker.checkAddGlobal(it.name)]
			instance.functions.addAll(filtered)
		}
		return this
	}
	
	/** Starts building a function using this program's name checker. */
	def startFunction(BaseDataType returnType, String name) {
		return FunctionBuilder.start(returnType, name, nameChecker)
	}
	
	/** Starts building a function using this program's name checker. */
	def startFunction(boolean isStatic, boolean isInline, DataType returnType, String name) {
		return FunctionBuilder.start(isStatic, isInline, returnType, name, nameChecker)
	}
}