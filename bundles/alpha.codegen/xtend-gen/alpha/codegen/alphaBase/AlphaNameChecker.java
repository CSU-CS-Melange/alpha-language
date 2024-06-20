package alpha.codegen.alphaBase;

import alpha.codegen.NameChecker;
import alpha.model.Variable;
import java.util.HashMap;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * Assists with name checking for Alpha variables and system parameters.
 */
@SuppressWarnings("all")
public class AlphaNameChecker extends NameChecker {
  /**
   * Stores the names of eval functions associated with each Alpha variable.
   */
  protected final HashMap<String, String> evalNames = CollectionLiterals.<String, String>newHashMap();

  /**
   * Stores the names of flag variables associated with each Alpha variable.
   */
  protected final HashMap<String, String> flagNames = CollectionLiterals.<String, String>newHashMap();

  /**
   * Stores the entry point's argument name associated with each Alpha variable or system parameter.
   */
  protected final HashMap<String, String> argumentNames = CollectionLiterals.<String, String>newHashMap();

  /**
   * If true, then the code being generated will use eval functions to read from local and output variables.
   * Otherwise, these values will be read directly from where they're stored.
   */
  protected final boolean demandDrivenReads;

  /**
   * Constructs a new instance of this class.
   * 
   * @param demandDrivenReads If true, then the code being generated will use eval functions to read from
   * local and output variables. Otherwise, these values will be read directly from where they're stored.
   */
  public AlphaNameChecker(final boolean demandDrivenReads) {
    this.demandDrivenReads = demandDrivenReads;
  }

  /**
   * Gets the name of the flag variable associated with the given Alpha variable.
   */
  public String getFlagName(final Variable variable) {
    return this.getUniqueName("_flag_", variable.getName(), this.flagNames);
  }

  /**
   * Gets the name of the entry point argument associated with the given system parameter.
   */
  public String getParameterArgumentName(final String systemParameter) {
    return this.getUniqueName("_local_", systemParameter, this.argumentNames);
  }

  /**
   * Gets the simpleC global variable name associated with the given system parameter.
   */
  public String getParameterStorageName(final String systemParameter) {
    return systemParameter;
  }

  /**
   * Gets the name of the entry point argument associated with the given Alpha variable.
   */
  public String getVariableArgumentName(final Variable variable) {
    return this.getUniqueName("_local_", variable.getName(), this.argumentNames);
  }

  /**
   * Gets the name to use when reading the given Alpha variable.
   * For inputs, this will always be the global variable where they're stored.
   * If the code is not demand driven, the same goes for outputs and locals.
   * For demand driven code, the outputs and locals will be read
   * from special functions that ensure the value has been evaluated.
   */
  public String getVariableReadName(final Variable variable) {
    String _xblockexpression = null;
    {
      if (((!this.demandDrivenReads) || (variable.isInput()).booleanValue())) {
        return this.getVariableStorageName(variable);
      }
      _xblockexpression = this.getUniqueName("eval_", variable.getName(), this.evalNames);
    }
    return _xblockexpression;
  }

  /**
   * Gets the simpleC global variable name associated with the given Alpha variable.
   */
  public String getVariableStorageName(final Variable variable) {
    return variable.getName();
  }
}
