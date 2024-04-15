package alpha.codegen.util

import static extension alpha.model.util.AffineFunctionOperations.*
import static extension alpha.model.matrix.factory.MatrixUserFactory.createMatrixRow
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import alpha.model.matrix.Matrix
import alpha.model.matrix.MatrixRow


class ISLPrintingUtils {
	
	/*
	 * checks whether a constraint involves at least one
	 *  index dimension
	 *  «»
	 */
	def static boolean involvesAtLeastOneDim(ISLConstraint c, ISLDimType dim_type) {
		val dim = c.getNbDims(dim_type)
		(0..<dim).map[c.involvesDims(dim_type, it, 1)].reduce(v0,v1 | (v0||v1))
	}

	def static String toCString(ISLConstraint c) {
		'''«c.aff.toString(ISL_FORMAT.C).replace(' ', '')»«if (c.isEquality) '==' else '>='»0'''
	}
	
	def static String indexConstraintsToConditionals(ISLSet set) {
		set.basicSets.map['''(«constraints.filter[c | c.involvesAtLeastOneDim(ISLDimType.isl_dim_out)].map['''(«toCString»)'''].join(' && ')»)'''].join(' || ')
	}
	
	def static String paramConstraintsToConditionals(ISLSet set) {
		set.basicSets.map['''(«constraints.filter[c | c.involvesAtLeastOneDim(ISLDimType.isl_dim_param)].map['''(«toCString»)'''].join(' && ')»)'''].join(' || ')
	}
	
	def static String toCString(ISLMultiAff maff) {
		val dim = maff.dim(ISLDimType.isl_dim_out)
		if (dim == 0)
			return ""
		'''(«maff.affs.map[toString(ISL_FORMAT.C).replace(' ', '')].join(',')»)'''
	}
	
	/*
	 * input:  [A, B] -> { [i, j, k] -> [(i)] }
	 * output: [A, B] -> { [i, j, k] -> [(A), (B), (i)] }
	 */
	def static ISLMultiAff moveParamsToArgs(ISLMultiAff maff) {
		val matrix = maff.toMatrix
		
		val numParams = matrix.getNbParams
		val paramRows = matrix.getRowsInRange(0, numParams)
		matrix.rows.addAll(numParams, paramRows)
		
		matrix.toMultiAff
	}
	
	def static MatrixRow[] getRowsInRange(Matrix m, int a, int b) {
		m.rows.filter[a<=m.rows.indexOf(it) && m.rows.indexOf(it)<b]
		      .map[getValues.createMatrixRow]
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}