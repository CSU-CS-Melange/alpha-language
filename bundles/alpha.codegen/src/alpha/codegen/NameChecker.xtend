package alpha.codegen

import java.util.Collection
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
	
	/** Creates a new name checker that prevents shadowing global variables. */
	new() { this(true) }
	
	/** Creates a new name checker. */
	new(boolean preventShadowing) {
		globalNames = newHashSet
		this.preventShadowing = preventShadowing
	}
	
	/**
	 * Returns true if any of the given names exist in the global scope.
	 * Otherwise, returns false.
	 */
	def globalNameExists(String... names) {
		return names.exists[globalNames.contains(it)]
	}
	
	/**
	 * Checks if all the given global variables are unique and records them if so.
	 * If any have already been declared, a NameConflictException is thrown.
	 */
	def checkAddGlobal(String... names) {
		for (name : names) {
			if (globalNames.contains(name)) {
				throw new NameConflictException(name)
			}
			globalNames.add(name)
		}
	}
	
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
		while (globalNames.contains(toAdd) || localNames.contains(toAdd)) {
			toAdd += suffix
		}
		return toAdd
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
}