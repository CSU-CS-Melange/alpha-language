package alpha.codegen.alphaBase

/**
 * The status of a "flag" variable, which indicates
 * whether a value has been evaluated already,
 * is still being evaluated (i.e., cyclic dependency),
 * or has not been evaluated yet.
 */
enum FlagStatus {
	/** Indicates that a value has not been evaluated yet. */
	NOT_EVALUATED,
	
	/**
	 * Indicates that a value is still being evaluated.
	 * If found, this means the value has a cyclic dependency on itself.
	 */
	IN_PROGRESS,
	
	/** Indicates that a value was previously evaluated and is ready for use. */
	EVALUATED
}