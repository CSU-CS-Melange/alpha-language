package alpha.codegen.demandDriven;

import alpha.codegen.BaseDataType;
import alpha.codegen.DataType;
import alpha.codegen.Factory;
import alpha.codegen.alphaBase.TypeGeneratorBase;
import alpha.model.Variable;

/**
 * Gets the simpleC data types associated with various Alpha structures.
 * If compatibility is needed with the older AlphaZ system,
 * inputs and outputs are allocated as multi-dimensional arrays,
 * with one dimension per index in the variable domain (minimum of 1).
 * Otherwise, everything else uses linearized memory.
 */
@SuppressWarnings("all")
public class WriteCTypeGenerator extends TypeGeneratorBase {
  /**
   * The type of values stored by Alpha variables.
   */
  protected final BaseDataType valueType;

  /**
   * If true, compatibility with the older AlphaZ system will be maintained.
   * If false, all memory will be linearized.
   */
  protected final boolean oldAlphaZCompatible;

  /**
   * Constructs a new instance of this class.
   * @param baseType The type of values stored by Alpha variables.
   * @param oldAlphaZCompatible If true, compatibility with the older AlphaZ system will be maintained.
   *     If false, all memory will be linearized.
   */
  public WriteCTypeGenerator(final BaseDataType valueType, final boolean oldAlphaZCompatible) {
    this.valueType = valueType;
    this.oldAlphaZCompatible = oldAlphaZCompatible;
  }

  /**
   * Gets the base type of values stored by Alpha variables.
   */
  public BaseDataType getAlphaValueBaseType() {
    return this.valueType;
  }

  /**
   * Gets the data type stored inside Alpha variables.
   */
  @Override
  public DataType getAlphaValueType(final Variable variable) {
    return this.getAlphaValueType();
  }

  /**
   * Gets the data type stored inside Alpha variables.
   */
  public DataType getAlphaValueType() {
    return Factory.dataType(this.valueType);
  }

  /**
   * Gets the data type of the Alpha variable itself.
   */
  @Override
  public DataType getAlphaVariableType(final Variable variable) {
    return Factory.dataType(this.valueType, this.getIndirectionLevel(variable));
  }

  /**
   * Gets the level of indirection to use for the given variable.
   * If we need compatibility with the older AlphaZ,
   * we use one level of indirection per index in the
   * variable's domain, with a minimum of 1.
   * Otherwise, assume linearized memory (i.e., 1).
   */
  protected int getIndirectionLevel(final Variable variable) {
    if ((this.oldAlphaZCompatible && ((variable.isInput()).booleanValue() || (variable.isOutput()).booleanValue()))) {
      return Integer.max(1, variable.getDomain().getNbIndices());
    }
    return 1;
  }

  /**
   * Gets the data type of the Flag variable itself.
   */
  @Override
  public DataType getFlagVariableType(final Variable variable) {
    return Factory.dataType(BaseDataType.CHAR, 1);
  }
}
