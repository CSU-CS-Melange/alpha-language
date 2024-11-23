package alpha.codegen.scheduledC

import alpha.codegen.alphaBase.TypeGeneratorBase
import alpha.codegen.BaseDataType
import alpha.model.Variable
import alpha.codegen.Factory

class ScheduledTypeGenerator extends TypeGeneratorBase {
		/** The type of values stored by Alpha variables. */
	protected val BaseDataType valueType
	
	/**
	 * Constructs a new instance of this class.
	 * @param baseType The type of values stored by Alpha variables.
	 * @param oldAlphaZCompatible If true, compatibility with the older AlphaZ system will be maintained.
	 *     If false, all memory will be linearized.
	 */
	new(BaseDataType valueType, boolean oldAlphaZCompatible) {
		this.valueType = valueType
	}
	
	/** Gets the base type of values stored by Alpha variables. */
	def getAlphaValueBaseType() {
		return valueType
	}
	
	/** Gets the data type stored inside Alpha variables. */
	override getAlphaValueType(Variable variable) {
		getAlphaValueType
	}
	
	/** Gets the data type stored inside Alpha variables. */
	def getAlphaValueType() {
		Factory.dataType(valueType)
	}
	
	/** Gets the data type of the Alpha variable itself. */
	override getAlphaVariableType(Variable variable) {
		Factory.dataType(valueType, variable.indirectionLevel)
	}
	
	/**
	 * Gets the level of indirection to use for the given variable.
	 * If we need compatibility with the older AlphaZ,
	 * we use one level of indirection per index in the
	 * variable's domain, with a minimum of 1.
	 * Otherwise, assume linearized memory (i.e., 1).
	 */
	protected def getIndirectionLevel(Variable variable) {
		return 1
	}
	
	/** Gets the data type of the Flag variable itself. */
	override getFlagVariableType(Variable variable) {
		Factory.dataType(BaseDataType.CHAR, 1)
	}
}