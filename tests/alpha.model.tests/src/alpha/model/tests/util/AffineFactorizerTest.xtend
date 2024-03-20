package alpha.model.tests.util

import alpha.model.util.AffineFactorizer
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMatrix
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import org.junit.Test

import static org.junit.Assert.*

import static extension alpha.model.util.AffineFunctionOperations.*

class AffineFactorizerTest {
	////////////////////////////////////////////////////////////
	// Helper Methods
	////////////////////////////////////////////////////////////

	def private static stringToMultiAff(String str) {
		return ISLMultiAff.buildFromString(ISLContext.instance, str)
	}

	def private static stringToMatrix(String str) {
		return AffineFactorizer.expressionToMatrix(stringToMultiAff(str))		
	}

	def private static stringsToMultiAffs(String... strs) {
		// Wrapping this in an ArrayList prevents the lazy evaluation from
		// recreating the expressions each time they're accessed.
		return new ArrayList<ISLMultiAff>(strs.map[stringToMultiAff])
	}

	def private static mergeExpressionsTest(String expectedOutput, String... inputs) {
		val inputAffs = stringsToMultiAffs(inputs)
		val expectedAff = stringToMultiAff(expectedOutput)
		val actualAff = inputAffs.mergeExpressions
		assertTrue(expectedAff.isPlainEqual(actualAff))
	}
	
	def private static assertMatrixIsCorrect(List<List<Integer>> expected, ISLMatrix actual) {
		// Check that the dimensions are correct.
		val rows = expected.size
		val cols = expected.get(0).size
		assertEquals("Wrong number of rows", rows, actual.nbRows)
		assertEquals("Wrong number of cols", cols, actual.nbCols)
		
		// Check each row/column.
		for (row : 0 ..< rows) {
			for (col : 0 ..< cols) {
				val message = "Wrong value at row " + row + ", col " + col
				assertEquals(message, expected.get(row).get(col), actual.getElement(row, col))
			}
		}
	}
	
	def private static assertPlainEqual(ISLMultiAff expected, ISLMultiAff actual) {
		assertTrue(expected.isPlainEqual(actual))
	}

	def private static assertPlainEqual(String message, ISLMultiAff expected, ISLMultiAff actual) {
		assertTrue(message, expected.isPlainEqual(actual))
	}
	
	def private static getRemainingTerm(Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> result, int index, ISLMultiAff... inputs) {
		return result.value.get(inputs.get(index))
	}
	
	def private static assertFactorizationIsCorrect(int expectedInnerDims, String... inputs) {
		val expressions = stringsToMultiAffs(inputs)
		val result = AffineFactorizer.factorizeExpressions(expressions)

		val actualInnerDims = result.key.dim(ISLDimType.isl_dim_out)
		assertEquals("Wrong number of inner dimensions.", expectedInnerDims, actualInnerDims)
		
		// Check that each input expression can be reconstructed as the composition of
		// the remaining term for that input with the common factor.
		for (i: 0 ..< inputs.length) {
			val expr = expressions.get(i)
			val remainingTerm = result.value.get(expr)
			val reconstructed = remainingTerm.pullback(result.key.copy)
			assertPlainEqual("Remaining term " + i + " is wrong.", expr, reconstructed)
		}
	}

	////////////////////////////////////////////////////////////
	// ISL Bindings
	////////////////////////////////////////////////////////////
	
	@Test
	def dropCols() {
		var mat = stringToMatrix("{ [i,j,k] -> [k,i,j] }")
		assertEquals(3, mat.nbCols)
		mat = mat.dropCols(2,1)
		assertEquals(2, mat.nbCols)
	}

	@Test
	def hermiteNormalForm() {
		// This test ensures that the HNF computation not only works,
		// but that it works correctly with the affine factorizer's
		// expression-to-matrix function.
		// Note: ISL's HNF computation is column-oriented as of writing,
		// so if this test fails, double check that this is still the case
		// and that the affine factorizer is written to expect this. 

		val input = stringToMatrix("{ [i,j,k] -> [i+k,i+k+j]}")
		val hnf_result = input.leftHermite
		val h = hnf_result.h
		val u = hnf_result.u
		val q = hnf_result.q
		
		val hExpected = #[
			#[1, 0],
			#[0, 1],
			#[1, 0],
			#[0, 0]
		]
		val uExpected = #[
			#[1, -1],
			#[0,  1]
		]
		val qExpected = #[
			#[1, 1],
			#[0, 1]
		]
		
		assertMatrixIsCorrect(hExpected, h)
		assertMatrixIsCorrect(uExpected, u)
		assertMatrixIsCorrect(qExpected, q)
	}


	////////////////////////////////////////////////////////////
	// Merging Expressions Into One
	////////////////////////////////////////////////////////////
	
	@Test
	def nameOutputs_01() {
		val input = stringsToMultiAffs(
			"[N] -> { [i,j] -> [j,i] }",
			"[N] -> { [i,j] -> [i+j,i-j] }"
		)
		val result = AffineFactorizer.nameExpressionOutputs(input)
		
		// Ensure the names are all correct.
		assertEquals("orig_out_0", result.get(input.get(0)).space.getDimName(ISLDimType.isl_dim_out, 0))
		assertEquals("orig_out_1", result.get(input.get(0)).space.getDimName(ISLDimType.isl_dim_out, 1))
		assertEquals("orig_out_2", result.get(input.get(1)).space.getDimName(ISLDimType.isl_dim_out, 0))
		assertEquals("orig_out_3", result.get(input.get(1)).space.getDimName(ISLDimType.isl_dim_out, 1))
		
		// During development, lazy evaluation caused names to be re-generated
		// each time the objects are accessed, so double-check that this isn't an issue.
		assertEquals("orig_out_0", result.get(input.get(0)).space.getDimName(ISLDimType.isl_dim_out, 0))
		assertEquals("orig_out_1", result.get(input.get(0)).space.getDimName(ISLDimType.isl_dim_out, 1))
		assertEquals("orig_out_2", result.get(input.get(1)).space.getDimName(ISLDimType.isl_dim_out, 0))
		assertEquals("orig_out_3", result.get(input.get(1)).space.getDimName(ISLDimType.isl_dim_out, 1))
	}
	
	@Test
	def mergeExpressions_oneInput() {
		val inputs = #["[N] -> { [x,y,z] -> [x+y,z] }"]
		val expectedOutput = inputs.get(0)
		mergeExpressionsTest(expectedOutput, inputs)
	}
	
	@Test
	def mergeExpressions_twoInputs() {
		val inputs = #[
			"[N] -> { [p,q,r] -> [p+q,q+r] }",
			"[N] -> { [p,q,r] -> [r,N-q,p+2] }"
		]
		val expectedOutput = "[N] -> { [p,q,r] -> [p+q,q+r,r,N-q,p+2] }"
		mergeExpressionsTest(expectedOutput, inputs)
	}

	@Test
	def mergeExpressions_threeInputs() {
		val inputs = #[
			"[N] -> { [i,j,k,l] -> [j,k] }",
			"[N] -> { [i,j,k,l] -> [k-j] }",
			"[N] -> { [i,j,k,l] -> [j-k, i-j-k+l] }"
		]
		val expectedOutput = "[N] -> { [i,j,k,l] -> [j,k,k-j,j-k,i-j-k+l] }"
		mergeExpressionsTest(expectedOutput, inputs)
	}
	
	
	////////////////////////////////////////////////////////////
	// Expression to Matrix Conversion
	////////////////////////////////////////////////////////////
		
	@Test
	def expressionToMatrix_01() {
		val input = stringToMultiAff("{ [i,j] -> [0, 0] }")
		val result = AffineFactorizer.expressionToMatrix(input)
		
		// The matrix should have 3 rows (2 inputs + 1 constant) and 2 columns.
		assertEquals(3, result.nbRows)
		assertEquals(2, result.nbCols)
		
		// All of the coefficients should be zero.
		for (row : 0 ..< 3) {
			for (col : 0 ..< 2) {
				assertEquals(0, result.getElement(row, col))
			}
		}
	}
	
	@Test
	def expressionToMatrix_02() {
		val input = stringToMultiAff("[N] -> { [i,j] -> [2N -3j +4i +1, 7-2i+3j-4N] }")
		val result = AffineFactorizer.expressionToMatrix(input)
		
		// The matrix should have 4 rows (1 param + 2 inputs + 1 constant) and 2 columns.
		assertEquals(4, result.nbRows)
		assertEquals(2, result.nbCols)
		
		// Check all the coefficients.
		assertEquals(2, result.getElement(0,0))     // Eq 1, N
		assertEquals(4, result.getElement(1,0))     // Eq 1, i
		assertEquals(-3, result.getElement(2,0))    // Eq 1, j
		assertEquals(1, result.getElement(3,0))     // Eq 1, constant
		
		assertEquals(-4, result.getElement(0,1))    // Eq 2, N
		assertEquals(-2, result.getElement(1,1))    // Eq 2, i
		assertEquals(3, result.getElement(2,1))     // Eq 2, j
		assertEquals(7, result.getElement(3,1))     // Eq 2, constant
	}
	
	@Test
	def expressionToMatrix_03() {
		val input = stringToMultiAff("{ [] -> [7] }")
		val result = AffineFactorizer.expressionToMatrix(input)
		
		// This should be a 1x1 matrix with coefficient 7.
		assertEquals(1, result.nbRows)
		assertEquals(1, result.nbCols)

		assertEquals(7, result.getElement(0,0))
	}
	
	
	////////////////////////////////////////////////////////////
	// Hermite Decomposition
	////////////////////////////////////////////////////////////

	@Test
	def reduceHermiteDimensionality_01() {
		// Expecting nothing to be dropped.
		val hOriginal = ISLMatrix.buildFromLongMatrix(#[
			#[0, 0],
			#[1, 1],
			#[2, 0]
		])
		val qOriginal = ISLMatrix.buildFromLongMatrix(#[
			#[1, 1],
			#[2, 2]
		])
		
		val result = AffineFactorizer.reduceHermiteDimensionality(hOriginal, qOriginal)
		
		val expectedH = #[ #[0, 0], #[1, 1], #[2, 0] ]
		val expectedQ = #[ #[1, 1], #[2, 2] ]
		
		assertMatrixIsCorrect(expectedH, result.key)
		assertMatrixIsCorrect(expectedQ, result.value)
	}
	
	@Test
	def reduceHermiteDimensionality_02() {
		// Expecting 1 column to be dropped from H, and 1 row from Q.
		val hOriginal = ISLMatrix.buildFromLongMatrix(#[
			#[0, 0],
			#[1, 0],
			#[2, 0]
		])
		val qOriginal = ISLMatrix.buildFromLongMatrix(#[
			#[1, 1],
			#[2, 2]
		])
		
		val result = AffineFactorizer.reduceHermiteDimensionality(hOriginal, qOriginal)
		
		val expectedH = #[ #[0], #[1], #[2] ]
		val expectedQ = #[ #[1, 1] ]
		
		assertMatrixIsCorrect(expectedH, result.key)
		assertMatrixIsCorrect(expectedQ, result.value)
	}
	
	@Test
	def reduceHermiteDimensionality_03() {
		// Expecting 2 columns to be dropped from H, and 2 rows from Q.
		val hOriginal = ISLMatrix.buildFromLongMatrix(#[
			#[0, 0, 0],
			#[1, 0, 0],
			#[2, 0, 0]
		])
		val qOriginal = ISLMatrix.buildFromLongMatrix(#[
			#[1, 1],
			#[2, 2],
			#[3, 3]
		])
		
		val result = AffineFactorizer.reduceHermiteDimensionality(hOriginal, qOriginal)
		
		val expectedH = #[ #[0], #[1], #[2] ]
		val expectedQ = #[ #[1, 1] ]
		
		assertMatrixIsCorrect(expectedH, result.key)
		assertMatrixIsCorrect(expectedQ, result.value)
	}
	
	@Test
	def hermiteMatrixDecomposition_01() {
		val original = ISLMatrix.buildFromLongMatrix(#[
			#[1, 0, 0],
			#[0, 1, 0],
			#[0, 0, 1]
		])
		val decomposeResult = AffineFactorizer.hermiteMatrixDecomposition(original)
		val hActual = decomposeResult.key
		val qActual = decomposeResult.value
		
		val bothExpected = #[
			#[1, 0, 0],
			#[0, 1, 0],
			#[0, 0, 1]
		]
		
		assertMatrixIsCorrect(bothExpected, hActual)
		assertMatrixIsCorrect(bothExpected, qActual)
	}
	
	@Test
	def hermiteMatrixDecomposition_02() {
		val original = ISLMatrix.buildFromLongMatrix(#[
			#[1, 1, 1],
			#[0, 1, 0],
			#[1, 1, 1]
		])
		val decomposeResult = AffineFactorizer.hermiteMatrixDecomposition(original)
		val hActual = decomposeResult.key
		val qActual = decomposeResult.value
		
		val hExpected = #[
			#[1, 0],
			#[0, 1],
			#[1, 0]
		]
		val qExpected = #[
			#[1, 1, 1],
			#[0, 1, 0]
		]
		
		assertMatrixIsCorrect(hExpected, hActual)
		assertMatrixIsCorrect(qExpected, qActual)
	}
	
	
	////////////////////////////////////////////////////////////
	// Matrix to Expression Conversion
	////////////////////////////////////////////////////////////
	
	@Test
	def matrixToExpression_01() {
		val matrix = ISLMatrix.buildFromLongMatrix(#[
			#[0, 0],
			#[0, 0],
			#[0, 0]
		])
		val expected = stringToMultiAff("{ [i,j] -> [0, 0] }")

		val result = AffineFactorizer.toExpression(matrix, expected.space)
		assertPlainEqual(expected, result)
	}
	
	@Test
	def matrixToExpression_02() {
		val matrix = ISLMatrix.buildFromLongMatrix(#[
			#[ 2, -4],
			#[ 4, -2],
			#[-3,  3],
			#[ 1,  7]
		])
		val expected = stringToMultiAff("[N] -> { [i,j] -> [2N -3j +4i +1, 7-2i+3j-4N] }")

		val result = AffineFactorizer.toExpression(matrix, expected.space)
		assertPlainEqual(expected, result)
	}
	
	@Test
	def matrixToExpression_03() {
		val matrix = ISLMatrix.buildFromLongMatrix(#[ #[7] ])
		val expected = stringToMultiAff("{ [] -> [7] }")

		val result = AffineFactorizer.toExpression(matrix, expected.space)
		assertPlainEqual(expected, result)
	}
	
	
	////////////////////////////////////////////////////////////
	// Expression Decomposition
	////////////////////////////////////////////////////////////
	
	@Test
	def hermiteExpressionDecomposition_constants() {
		// This type of expression is how AlphaZ handles constants.
		// For example, the constant 17 is expressed as: (i,j->)@17
		val original = stringToMultiAff("[N] -> { [i,j,k] -> [] }")
		val decomposed = AffineFactorizer.hermiteExpressionDecomposition(original)
		val hActual = decomposed.key
		val qActual = decomposed.value
		
		val hExpected = stringToMultiAff("[N] -> { [i,j,k] -> [] }")
		val qExpected = stringToMultiAff("{ [] -> [] }")
		
		assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual))
		assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual))
	}
	
	@Test
	def hermiteExpressionDecomposition_01() {
		// Nothing is expected to happen here.
		val original = stringToMultiAff("[N] -> { [i,j,k] -> [i,j,k] }")
		val decomposed = AffineFactorizer.hermiteExpressionDecomposition(original)
		val hActual = decomposed.key
		val qActual = decomposed.value
		
		val hExpected = stringToMultiAff("[N] -> { [i,j,k] -> [i,j,k] }")
		val qExpected = stringToMultiAff("{ [i,j,k] -> [i,j,k] }")
		
		assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual))
		assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual))
	}
	
	@Test
	def hermiteExpressionDecomposition_02() {
		// There is a decomposition here, but it doesn't remove any dimensions.
		val original = stringToMultiAff("[N] -> { [i,j,k] -> [i+k,i+j+k] }")
		val decomposed = AffineFactorizer.hermiteExpressionDecomposition(original)
		val hActual = decomposed.key
		val qActual = decomposed.value
		
		val hExpected = stringToMultiAff("[N] -> { [i,j,k] -> [i+k,j] }")
		val qExpected = stringToMultiAff("{ [x,y] -> [x,x+y] }")
		
		assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual))
		assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual))
	}
	
	@Test
	def hermiteExpressionDecomposition_03() {
		// This is a decomposition to a lower dimensional space.
		val original = stringToMultiAff("[N] -> { [i,j,k] -> [i+k,i+j+k,i+k] }")
		val decomposed = AffineFactorizer.hermiteExpressionDecomposition(original)
		val hActual = decomposed.key
		val qActual = decomposed.value
		
		val hExpected = stringToMultiAff("[N] -> { [i,j,k] -> [i+k,j] }")
		val qExpected = stringToMultiAff("{ [x,y] -> [x,x+y,x] }")
		
		assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual))
		assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual))
	}
	
	@Test
	def hermiteExpressionDecomposition_04() {
		// This should completely remove everything.
		val original = stringToMultiAff("[N] -> { [i,j,k] -> [0,0,0,0] }")
		val decomposed = AffineFactorizer.hermiteExpressionDecomposition(original)
		val hActual = decomposed.key
		val qActual = decomposed.value
		
		val hExpected = stringToMultiAff("[N] -> { [i,j,k] -> [0] }")
		val qExpected = stringToMultiAff("{ [i] -> [i,0,0,0] }")
		
		assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual))
		assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual))
	}
	
	
	////////////////////////////////////////////////////////////
	// Full Factorization
	////////////////////////////////////////////////////////////
	
	@Test
	def factorizeExpressions_nothing01() {
		val input = stringToMultiAff("{ [] -> [] }")
		val result = AffineFactorizer.factorizeExpressions(input)
		
		val allExpected = stringToMultiAff("{ [] -> [] }")
		val remainingActual = result.getRemainingTerm(0, input)
		
		assertPlainEqual("Common factor is wrong.", allExpected, result.key)
		assertEquals("Number of remaining terms is wrong.", 1, result.value.size)
		assertPlainEqual("Remaining term is wrong.", allExpected, remainingActual)
	}
	
	@Test
	def factorizeExpressions_nothing02() {
		val inputs = stringsToMultiAffs(
			"{ [] -> [] }",
			"{ [] -> [] }",
			"{ [] -> [] }")
		val result = AffineFactorizer.factorizeExpressions(inputs)
		
		val allExpected = stringToMultiAff("{ [] -> [] }")
		
		assertPlainEqual("Common factor is wrong.", allExpected, result.key)
		assertEquals("Number of remaining terms is wrong.", 3, result.value.size)
		assertPlainEqual("Remaining term 0 is wrong.", allExpected, result.getRemainingTerm(0, inputs))
		assertPlainEqual("Remaining term 1 is wrong.", allExpected, result.getRemainingTerm(1, inputs))
		assertPlainEqual("Remaining term 2 is wrong.", allExpected, result.getRemainingTerm(2, inputs))
	}
	
	@Test
	def factorizeExpressions_oneConstant() {
		// This is the format for a single constant.
		val input = stringToMultiAff("[N] -> { [i,j] -> [] }")
		val result = AffineFactorizer.factorizeExpressions(input)
		
		val commonExpected = stringToMultiAff("[N] -> { [i,j] -> [] }")
		val remainingExpected = stringToMultiAff("{ [] -> [] }")
		val remainingActual = result.getRemainingTerm(0, input)
		
		assertPlainEqual("Common factor is wrong.", commonExpected, result.key)
		assertEquals("Number of remaining terms is wrong.", 1, result.value.size)		
		assertPlainEqual("Remaining term is wrong.", remainingExpected, remainingActual)
	}

	@Test
	def factorizeExpressions_multipleConstants() {
		// This is the format for a single constant.
		val inputs = stringsToMultiAffs(
			"[N] -> { [i,j] -> [] }",
			"[N] -> { [i,j] -> [] }",
			"[N] -> { [i,j] -> [] }")
		val result = AffineFactorizer.factorizeExpressions(inputs)
		
		val commonExpected = stringToMultiAff("[N] -> { [i,j] -> [] }")
		val remainingExpected = stringToMultiAff("{ [] -> [] }")
		
		assertPlainEqual("Common factor is wrong.", commonExpected, result.key)
		assertEquals("Number of remaining terms is wrong.", 3, result.value.size)
		assertPlainEqual("Remaining term 0 is wrong.", remainingExpected, result.getRemainingTerm(0, inputs))
		assertPlainEqual("Remaining term 1 is wrong.", remainingExpected, result.getRemainingTerm(1, inputs))
		assertPlainEqual("Remaining term 2 is wrong.", remainingExpected, result.getRemainingTerm(2, inputs))
	}

	@Test
	def factorizeExpressions_01() {
		val expectedInnerDimensions = 3
		assertFactorizationIsCorrect(expectedInnerDimensions,
			"[N] -> { [i,j,k] -> [i,j,k] }")
	}
	
	@Test
	def factorizeExpressions_02() {
		val expectedInnerDimensions = 3
		assertFactorizationIsCorrect(expectedInnerDimensions,
			"[N] -> { [i,j,k] -> [k,j,i] }")
	}
	
	@Test
	def factorizeExpressions_03() {
		val expectedInnerDimensions = 5
		assertFactorizationIsCorrect(expectedInnerDimensions,
			"[N] -> { [i,j,k] -> [k,j,i,N,4] }")
	}
	
	@Test
	def factorizeExpressions_04() {
		// The inner dimensions roughly map to i, j, k, and N,
		// but they also have +/- constants in there so we don't
		// need to directly express them.
		val expectedInnerDimensions = 4
		assertFactorizationIsCorrect(expectedInnerDimensions,
			"[N] -> { [i,j,k] -> [i+j, N+k] }",
			"[N] -> { [i,j,k] -> [j+k, N+j+3] }")
	}
	
	@Test
	def factorizeExpressions_05() {
		// The inner dimensions are: N+4, M, i+k, j
		val expectedInnerDimensions = 4
		assertFactorizationIsCorrect(expectedInnerDimensions,
			"[N,M] -> { [i,j,k] -> [i+k, N-j+4] }",
			"[N,M] -> { [i,j,k] -> [M+N+4, N+M+4] }",
			"[N,M] -> { [i,j,k] -> [] }",
			"[N,M] -> { [i,j,k] -> [i+k+M] }"
		)
	}
}