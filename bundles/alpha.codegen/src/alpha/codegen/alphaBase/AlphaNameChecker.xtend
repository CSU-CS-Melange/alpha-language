package alpha.codegen.alphaBase

import alpha.codegen.NameChecker
import alpha.model.Variable
import java.util.HashMap

/** Assists with name checking for Alpha variables and system parameters. */
class AlphaNameChecker extends NameChecker {
	/** Stores the names of eval functions associated with each Alpha variable. */
	protected val HashMap<String, String> evalNames = newHashMap
	
	/** Stores the names of flag variables associated with each Alpha variable. */
	protected val HashMap<String, String> flagNames = newHashMap
	
	/** Stores the entry point's argument name associated with each Alpha variable or system parameter. */
	protected val HashMap<String, String> argumentNames = newHashMap
	
	/**
	 * If true, then the code being generated will use eval functions to read from local and output variables.
	 * Otherwise, these values will be read directly from where they're stored.
	 */
	protected val boolean demandDrivenReads
	
	/**
	 * Constructs a new instance of this class.
	 * 
	 * @param demandDrivenReads If true, then the code being generated will use eval functions to read from
	 * local and output variables. Otherwise, these values will be read directly from where they're stored.
	 */
	new(boolean demandDrivenReads) {
		this.demandDrivenReads = demandDrivenReads
	}
	
	/** Gets the name of the flag variable associated with the given Alpha variable. */
	def String getFlagName(Variable variable) {
		return getUniqueName("_flag_", variable.name, flagNames)
	}

	/** Gets the name of the entry point argument associated with the given system parameter. */
	def String getParameterArgumentName(String systemParameter) {
		getUniqueName("_local_", systemParameter, argumentNames)
	}
	
	/** Gets the simpleC global variable name associated with the given system parameter. */
	def String getParameterStorageName(String systemParameter) {
		return systemParameter
	}

	/** Gets the name of the entry point argument associated with the given Alpha variable. */
	def String getVariableArgumentName(Variable variable) {
		getUniqueName("_local_", variable.name, argumentNames)
	}
	
	/**
	 * Gets the name to use when reading the given Alpha variable.
	 * For inputs, this will always be the global variable where they're stored.
	 * If the code is not demand driven, the same goes for outputs and locals.
	 * For demand driven code, the outputs and locals will be read
	 * from special functions that ensure the value has been evaluated.
	 */
	def String getVariableReadName(Variable variable) {
		// If we aren't using eval functions, or if the variable is an input,
		// then we read values directly from where they're stored.
		if ((!demandDrivenReads) || variable.isInput) {
			return getVariableStorageName(variable)
		}
		
		getUniqueName("eval_", variable.name, evalNames)
	}
	
	/** Gets the simpleC global variable name associated with the given Alpha variable. */
	def String getVariableStorageName(Variable variable){
		return variable.name
	}
}