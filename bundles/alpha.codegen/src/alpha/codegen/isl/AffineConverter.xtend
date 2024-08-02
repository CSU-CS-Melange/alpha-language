package alpha.codegen.isl

import alpha.codegen.Factory
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT

import static extension alpha.model.util.CommonExtensions.toArrayList

/**
 * Converts isl affine expressions to C expressions.
 * Note: some conversions produce a single C expression,
 * while others may produce a list of expressions.
 */
class AffineConverter {
	/**
	 * Converts a multi-affine expression to a list of C expressions,
	 * one for each output dimension.
	 */
	def static convertMultiAff(ISLMultiAff multiAff) {
		return multiAff.affs.map[convertAff].toArrayList
	}
	
	/** Converts a single affine expression to a single C expression. */
	def static convertAff(ISLAff aff) {
		val literal = aff.inputNames.fold(
			aff.toString(ISL_FORMAT.C),
			[ret, index | ret.replace(index, '(' + index + ')')]
		)
		return Factory.customExpr("(" + literal + ")")
	}
}