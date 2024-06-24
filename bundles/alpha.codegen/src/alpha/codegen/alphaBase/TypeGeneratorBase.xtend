package alpha.codegen.alphaBase

import alpha.codegen.BaseDataType
import alpha.codegen.DataType
import alpha.codegen.Factory
import alpha.model.Variable

/**
 * Gets the simpleC data types associated with various Alpha structures.
 */
abstract class TypeGeneratorBase {
	/** Gets the data type of indices. */
	def DataType getIndexType() {
		Factory.dataType(BaseDataType.LONG)
	}
	
	/** Gets the data type stored inside Alpha variables. */
	def DataType getAlphaValueType(Variable variable)
	
	/** Gets the data type of the Alpha variable itself. */
	def DataType getAlphaVariableType(Variable variable)
	
	/** Gets the data type stored inside flag variables. */
	def DataType getFlagValueType(Variable variable) {
		Factory.dataType(BaseDataType.CHAR)
	}
	
	/** Gets the data type of the Flag variable itself. */
	def DataType getFlagVariableType(Variable variable)
}