package alpha.codegen.isl

import alpha.codegen.Factory
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT

import static extension alpha.model.util.CommonExtensions.toArrayList
import java.util.Arrays
import java.util.stream.Stream
import java.util.Comparator

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
		multiAff.convertMultiAff(true)
	}
	def static convertMultiAff(ISLMultiAff multiAff, boolean explicitParentheses) {
		return multiAff.affs.map[convertAff(explicitParentheses)].toArrayList
	}
	
	/** Converts a single affine expression to a single C expression. */
	def static convertAff(ISLAff aff) {
		aff.convertAff(true)
	}
	def static convertAff(ISLAff aff, boolean explicitParentheses) {
		var expr = aff.toString(ISL_FORMAT.C)
		if (explicitParentheses) {
			expr = expr.replaceAll('(\\b[_a-zA-Z]\\w*\\b)', '($1)')
			expr = expr.replaceAll('\\(floord\\)', 'floord')
		}
		return Factory.customExpr("(" + expr + ")")
	}
}