package alpha.codegen.alphaBase;

import alpha.codegen.BaseDataType;
import alpha.codegen.DataType;
import alpha.codegen.Factory;
import alpha.model.Variable;

/**
 * Gets the simpleC data types associated with various Alpha structures.
 */
@SuppressWarnings("all")
public abstract class TypeGeneratorBase {
  /**
   * Gets the data type of indices.
   */
  public DataType getIndexType() {
    return Factory.dataType(BaseDataType.LONG);
  }

  /**
   * Gets the data type stored inside Alpha variables.
   */
  public abstract DataType getAlphaValueType(final Variable variable);

  /**
   * Gets the data type of the Alpha variable itself.
   */
  public abstract DataType getAlphaVariableType(final Variable variable);

  /**
   * Gets the data type stored inside flag variables.
   */
  public DataType getFlagValueType(final Variable variable) {
    return Factory.dataType(BaseDataType.CHAR);
  }

  /**
   * Gets the data type of the Flag variable itself.
   */
  public abstract DataType getFlagVariableType(final Variable variable);
}
