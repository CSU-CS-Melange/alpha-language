package alpha.model.tests.transformations

import alpha.model.AlphaModelLoader
import alpha.model.transformation.Normalize
import org.junit.Test

import static extension alpha.commands.Utility.*
import static extension alpha.commands.UtilityBase.*
import static extension org.junit.Assert.*
import alpha.model.CaseExpression

class NormalizeTest {
	@Test
	def pullRestrictThenFlattenCase() {
		// This test originates from an issue where Normalize
		// would throw a Concurrent Modification Exception
		// when trying to normalize an Alpha program such as this.

		val filePath = "resources/src-valid/transformation-tests/normalize/pullRestrictThenFlattenCase.alpha"
		val equation =
			AlphaModelLoader.loadModel(filePath)
			.getSystem("testCase")
			.GetSystemBody
			.GetEquation("Y")
			
		Normalize.apply(equation)
		
		// We expect the equation to now be a case expression with three children.
		assertTrue(equation.expr instanceof CaseExpression)
		val caseExpr = equation.expr as CaseExpression
		assertEquals(3, caseExpr.exprs.size)
	}
}