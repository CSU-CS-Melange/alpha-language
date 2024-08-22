package alpha.codegen

import java.util.Collection
import java.util.HashMap
import java.util.HashSet

import static extension alpha.model.util.CommonExtensions.toArrayList

/**
 * Used for checking if a variable declaration exists in a list
 * and adding it to that list if it doesn't.
 */
class NameChecker {
	/** The set of all names declared at the global scope. */
	protected val HashSet<String> globalNames
	
	/** If true, a local variable is not allowed to have the same name as a global variable. */
	protected val boolean preventShadowing
	
	/**
	 * The set of reserved keywords that aren't allowed to be used as names ever.
	 * See: https://en.cppreference.com/w/c/keyword
	 * See: https://en.cppreference.com/w/cpp/keyword
	 */
	protected val HashSet<String> reservedKeywords
	
	/** Creates a new name checker that prevents shadowing global variables. */
	new() { this(true) }
	
	/** Creates a new name checker. */
	new(boolean preventShadowing) {
		globalNames = newHashSet
		this.preventShadowing = preventShadowing
		
		// See: https://en.cppreference.com/w/c/keyword
		// See: https://en.cppreference.com/w/cpp/keyword
		reservedKeywords = newHashSet(
			"alignas", "alignof", "and", "and_eq", "asm", "atomic_cancel", "atomic_commit", "atomic_noexcept",
			"auto", "bitand", "bitor", "bool", "break", "case", "catch", "char", "char16_t", "char32_t",
			"char8_t", "class", "compl", "concept", "const", "consteval", "constexpr", "constinit", "const_cast",
			"continue", "co_await", "co_return", "co_yield", "decltype", "default", "delete", "do", "double",
			"dynamic_cast", "else", "enum", "explicit", "export", "extern", "false", "float", "for", "friend",
			"goto", "if", "inline", "int", "long", "mutable", "namespace", "new", "noexcept", "not", "not_eq",
			"nullptr", "operator", "or", "or_eq", "private", "protected", "public", "reflexpr", "register",
			"reinterpret_cast", "requires", "restrict", "return", "short", "signed", "sizeof", "static",
			"static_assert", "static_cast", "struct", "switch", "synchronized", "template", "this", "thread_local",
			"throw", "true", "try", "typedef", "typeid", "typename", "typeof", "typeof_unqual", "union", "unsigned",
			"using", "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq", "_Alignas", "_Alignof",
			"_Atomic", "_BitInt", "_Bool", "_Complex", "_Decimal128", "_Decimal32", "_Decimal64", "_Generic",
			"_Imaginary", "_Noreturn", "_Static_assert", "_Thread_local")
	}
	
	
	/////////////////////////////////////////////////////////////////
	// Checking Existence of Names
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Returns true if any of the given names exist in the global scope
	 * or are a reserved keyword. Otherwise, returns false.
	 */
	def isGlobalOrKeyword(String... names) {
		return names.exists[globalNames.contains(it) || reservedKeywords.contains(it)]
	}
	
	
	/////////////////////////////////////////////////////////////////
	// Recording Names
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Checks if all the given global variables are unique and records them if so.
	 * If any have already been declared, a NameConflictException is thrown.
	 */
	def checkAddGlobal(String... names) {
		for (name : names) {
			if (globalNames.contains(name)) {
				throw new NameConflictException(name)
			}
			
			if (reservedKeywords.contains(name)) {
				throw new ReservedKeywordException(name)
			}
			globalNames.add(name)
		}
	}
	
	/**
	 * Checks if a local variable declaration is unique,
	 * and adds it to the given list of declarations if so.
	 * 
	 * If the name already exists in the global scope and shadowing is disallowed,
	 * or if the name exists in the local scope with a different data type,
	 * a NameConflictException is thrown.
	 * 
	 * If the variable already exists at the local scope with the same data type,
	 * the duplicate declaration is silently ignored. 
	 */
	def checkAddLocal(VariableDecl variable, Collection<VariableDecl> existingLocals) {
		// Reserved keywords are never allowed.
		if (reservedKeywords.contains(variable.name)) {
			throw new ReservedKeywordException(variable.name)
		}
		
		// If shadowing is disallowed, then the variable's name cannot match any of the global names.
		if (preventShadowing && globalNames.contains(variable.name)) {
			throw new NameConflictException(variable.name)
		}
		
		// Check if there are any existing local variables with the same name.
		// If not, then this variable is unique, so add it and return.
		val sameName = existingLocals.filter[it.hasSameNameAs(variable)].toArrayList
		if (sameName.isNullOrEmpty) {
			existingLocals.add(variable)
			return true
		}
		
		// If there are any variables with the same name but a different type,
		// throw an exception.
		if (sameName.exists[it.hasDifferentTypeThan(variable)]) {
			throw new NameConflictException(variable.name)
		}
		
		// This is only reached if the new declaration is an exact duplicate.
		// In this case, ignore it.
		return false
	}
	
	/** Returns true if the two variables have the same name, and false otherwise. */
	def protected static hasSameNameAs(VariableDecl first, VariableDecl second) {
		return first.name == second.name
	}
	
	/** Returns true if the two variables have different types, and false otherwise. */
	def protected static hasDifferentTypeThan(VariableDecl first, VariableDecl second) {
		return first.dataType.baseType != second.dataType.baseType
			|| first.dataType.indirectionLevel != second.dataType.indirectionLevel
	}
	
	
	/////////////////////////////////////////////////////////////////
	// Generating New Names
	/////////////////////////////////////////////////////////////////
	
	/**
	 * Repeatedly appends the given suffix to the base name until it is unique.
	 * If the base name is already unique, the suffix is not added.
	 * 
	 * Note: the name returned is NOT added to the list of global names,
	 * as that would be done when the name is actually declared.
	 * This is just to get a name that's unique to use in a declaration.
	 */
	def getUniqueGlobalName(String baseName, String suffix) {
		var toAdd = baseName
		while (globalNames.contains(toAdd)) {
			toAdd += suffix
		}
		return toAdd
	}
	
	/**
	 * Repeatedly appends the given suffix to the base name until it is unique.
	 * If the base name is already unique, the suffix is not added.
	 * 
	 * Note: the name returned is NOT added to the list of global names,
	 * as that would be done when the name is actually declared.
	 * This is just to get a name that's unique to use in a declaration.
	 */
	def getUniqueLocalName(Collection<String> localNames, String baseName, String suffix) {
		var toAdd = baseName
		while (globalNames.contains(toAdd) || localNames.contains(toAdd) || reservedKeywords.contains(toAdd)) {
			toAdd += suffix
		}
		return toAdd
	}
	
	/**
	 * Gets a unique name for the given key by prepending the suffix,
	 * then repeatedly appending underscores until the name is unique.
	 * This newly generated name is then added to the given storage,
	 * mapped to the given key.
	 * If the key was already present in the storage, that name is
	 * returned instead of generating a new one.
	 */
	def protected getUniqueName(String prefix, String key, HashMap<String, String> storage) {
		// If the storage doesn't contain the key, create a new name
		// by prepending the prefix and appending "_" until it's unique.
		if (!storage.containsKey(key)) {
			val uniqueName = getUniqueLocalName(storage.values, prefix + key, "_")
			storage.put(key, uniqueName)
		}
		return storage.get(key)
	}
}