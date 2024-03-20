package alpha.model.tests.transformations

import alpha.model.AlphaModelLoader
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryExpression
import alpha.model.CaseExpression
import alpha.model.ConstantExpression
import alpha.model.DependenceExpression
import alpha.model.IndexExpression
import alpha.model.MultiArgExpression
import alpha.model.RestrictExpression
import alpha.model.StandardEquation
import alpha.model.UnaryExpression
import alpha.model.VariableExpression
import alpha.model.transformation.RaiseDependence
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import org.junit.Test

import static org.junit.Assert.*

import static extension alpha.commands.Utility.*
import static extension alpha.commands.UtilityBase.*
import static extension alpha.model.util.CommonExtensions.toHashMap

class RaiseDependenceTest {
	/** The path to the Alpha file for these unit tests. */
	static val alphaFile = "resources/src-valid/transformation-tests/raise-dependence/raiseDependence.alpha";
	
	/** Gets the desired equation for these unit tests. */
	static def getEquation(String system, String equation) {
		return AlphaModelLoader.loadModel(alphaFile)
			.GetSystem(system)
			.GetSystemBody
			.GetEquation(equation)
	}
	
	
	////////////////////////////////////////////////////////////
	// Constant, Variable, and Index Expression Rules
	////////////////////////////////////////////////////////////
	
	@Test
	def wrapConstantExpression_01() {
		// Wrap a variable in a dependence expression of the identity function.
		val equation = getEquation("wrapConstantExpression_01", "X")
		RaiseDependence.apply(equation.expr)
		
		// The equation should now have a dependence function at the top level
		// which contains a constant expression.
		assertTrue(equation.expr instanceof DependenceExpression)
		val topDependence = equation.expr as DependenceExpression
		assertTrue(topDependence.expr instanceof ConstantExpression)
	}
	
	@Test
	def wrapVariableExpression_01() {
		// Wrap a variable in a dependence expression of the identity function.
		val equation = getEquation("wrapVariableExpression_01", "X")
		RaiseDependence.apply(equation.expr)
		
		// The equation should now have a dependence function at the top level
		// which contains a variable expression.
		assertTrue(equation.expr instanceof DependenceExpression)
		val topDependence = equation.expr as DependenceExpression
		assertTrue(topDependence.expr instanceof VariableExpression)
	}
	
	@Test
	def wrapVariableExpression_02() {
		// Wrap a variable in a dependence expression of the identity function.
		val equation = getEquation("wrapVariableExpression_02", "X")
		RaiseDependence.apply(equation.expr)
		
		// The equation should now have a dependence function at the top level
		// which contains a variable expression.
		assertTrue(equation.expr instanceof DependenceExpression)
		val topDependence = equation.expr as DependenceExpression
		assertTrue(topDependence.expr instanceof VariableExpression)
	}
	
	@Test
	def wrapIndexExpression_01() {
		// Save the original dependence function inside the index expression,
		// then apply dependence raising.
		val equation = getEquation("wrapIndexExpression_01", "X")
		val originalFunction = (equation.expr as IndexExpression).function
		RaiseDependence.apply(equation.expr)
		
		// The equation should now be a dependence expression with the original function.
		// Inside that should be an index expression with an identity function.
		assertTrue(equation.expr instanceof DependenceExpression)
		val dependence = equation.expr as DependenceExpression
		
		assertTrue(originalFunction.isPlainEqual(dependence.function))
		
		assertTrue(dependence.expr instanceof IndexExpression)
		val index = dependence.expr as IndexExpression
		
		assertTrue(index.function.isIdentity)
	}
	
	@Test
	def wrapIndexExpression_02() {
		// The expected dependence function at the end should be the composition
		// of the wrapping dependence function and the function inside the index expression.
		val equation = getEquation("wrapIndexExpression_02", "X")
		val originalDependence = equation.expr as DependenceExpression
		val originalIndex = originalDependence.expr as IndexExpression
		val expectedFunction = originalIndex.function.pullback(originalDependence.function)
		
		RaiseDependence.apply(equation.expr)
		
		// The equation should now be a dependence expression with the original function.
		// Inside that should be an index expression with an identity function.
		assertTrue(equation.expr instanceof DependenceExpression)
		val dependence = equation.expr as DependenceExpression
		
		assertTrue(expectedFunction.isPlainEqual(dependence.function))
		
		assertTrue(dependence.expr instanceof IndexExpression)
		val index = dependence.expr as IndexExpression
		
		assertTrue(index.function.isIdentity)
	}
	
	
	////////////////////////////////////////////////////////////
	// Dependence Expression Rules
	////////////////////////////////////////////////////////////
	
	@Test
	def nestedDependenceFunction_01() {
		// From:  f1 @ (f2 @ X)
		// To:    f @ X
		// Where: f = f1 @ f2
		
		// Get the equation for the top-level dependence expression under test,
		// extract the nested dependence functions, then apply dependence raising.
		val equation = getEquation("nestedDependenceFunction_01", "X")
		val originalDependence = equation.expr as DependenceExpression
		val f1 = originalDependence.function
		val f2 = (originalDependence.expr as DependenceExpression).function
		
		RaiseDependence.apply(equation.expr)
		
		// The equation should now have a dependence function at the top level
		// which contains a variable expression.
		assertTrue(equation.expr instanceof DependenceExpression)
		val topDependence = equation.expr as DependenceExpression
		assertTrue(topDependence.expr instanceof VariableExpression)
		
		// Make sure that f = f1 @ f2.
		val f = topDependence.function
		assertTrue(f.isPlainEqual(f2.pullback(f1)))
	}
	
	
	////////////////////////////////////////////////////////////
	// Restrict Expression Rules
	////////////////////////////////////////////////////////////
	
	@Test
	def restrictExpression_01() {
		// From:  case { D:(f@E); }
		// To:    f1 @ case { f2 @ (f(D) : E); }
		// Where: f = f1@f2
		
		val equation = getEquation("restrictExpression_01", "X")
		
		val originalCase = equation.expr as CaseExpression
		val originalRestrict = originalCase.exprs.get(0) as RestrictExpression
		val originalDependence = originalRestrict.expr as DependenceExpression
		
		val expectedDependenceFunction = originalDependence.function.copy
		val expectedRestrictDomain = originalRestrict.restrictDomain.copy.apply(expectedDependenceFunction.copy.toMap)
		
		assertSingleRestrictionCorrect(equation, expectedRestrictDomain, expectedDependenceFunction)
	}
	
	@Test
	def autoRestrictExpression_01() {
		// From:  case { auto: f @ E }
		// To:    f1 @ case { f2 @ (D:E) }
		// Where: f = f1@f2 and D is a restriction to the context domain.
		
		val equation = getEquation("autoRestrictExpression_01", "X")
		
		val originalCase = equation.expr as CaseExpression
		val originalRestrict = originalCase.exprs.get(0) as AutoRestrictExpression
		val originalDependence = originalRestrict.expr as DependenceExpression
		
		val expectedDependenceFunction = originalDependence.function.copy
		val expectedRestrictDomain = originalDependence.expr.contextDomain.copy
		
		assertSingleRestrictionCorrect(equation, expectedRestrictDomain, expectedDependenceFunction)
	}
	
	static def assertSingleRestrictionCorrect(StandardEquation equation, ISLSet expectedRestrictDomain, ISLMultiAff expectedDependenceFunction) {
		RaiseDependence.apply(equation.expr)
		
		// The equation should now be a dependence expression (f1) at the top level.
		// Inside that should be a case expression with a single dependence expression (f2).
		// Inside there is a restrict expression with the expected domain.
		assertTrue(equation.expr instanceof DependenceExpression)
		val outerDependence = equation.expr as DependenceExpression
		val f1 = outerDependence.function
		
		assertTrue(outerDependence.expr instanceof CaseExpression)
		val updatedCase = outerDependence.expr as CaseExpression
		
		assertEquals(1, updatedCase.exprs.size)
		assertTrue(updatedCase.exprs.get(0) instanceof DependenceExpression)
		val innerDependence = updatedCase.exprs.get(0) as DependenceExpression
		val f2 = innerDependence.function
		
		val updatedRestrict = innerDependence.expr as RestrictExpression
		
		// Check that the restrict domain is correct and that f = f1@f2
		assertTrue(updatedRestrict.restrictDomain.isEqual(expectedRestrictDomain))
		assertTrue(expectedDependenceFunction.isPlainEqual(f2.pullback(f1)))
		
	}
	
	@Test
	def autoRestrictExpression_02() {
		// From:  case { D1: f1@E1; auto: f2 @ E2 }
		// To:    f' @ case { f1' @ (f1(D1): E1); f2' @ (D2: E2) }
		// Where: fn = f'@fn' and D2 is a restriction to the context domain.
		
		// Get the original equation and extract d1, d2, f1, and f2.
		val equation = getEquation("autoRestrictExpression_02", "X")
		
		val originalCase = equation.expr as CaseExpression
		
		val originalRestrict1 = originalCase.exprs.get(0) as RestrictExpression
		val originalDependence1 = originalRestrict1.expr as DependenceExpression
		
		val originalRestrict2 = originalCase.exprs.get(1) as AutoRestrictExpression
		val originalDependence2 = originalRestrict2.expr as DependenceExpression
		
		val d1 = originalRestrict1.restrictDomain.copy
		val d2 = originalDependence2.contextDomain.copy
		val f1 = originalDependence1.function.copy
		val f2 = originalDependence2.function.copy
		
		// Apply dependence raising and check that the AST is correct.
		RaiseDependence.apply(equation.expr)
		
		assertTrue(equation.expr instanceof DependenceExpression)
		val outerDependence = equation.expr as DependenceExpression
		
		assertTrue(outerDependence.expr instanceof CaseExpression)
		val updatedCase = outerDependence.expr as CaseExpression
		
		assertEquals(2, updatedCase.exprs.size)
		assertTrue(updatedCase.exprs.get(0) instanceof DependenceExpression)
		assertTrue(updatedCase.exprs.get(1) instanceof DependenceExpression)
		val updatedDep1 = updatedCase.exprs.get(0) as DependenceExpression
		val updatedDep2 = updatedCase.exprs.get(1) as DependenceExpression
		
		assertTrue(updatedDep1.expr instanceof RestrictExpression)
		val updatedRestrict1 = updatedDep1.expr as RestrictExpression
		val updatedRestrict2 = updatedDep2.expr as RestrictExpression
		
		// Extract out the actual domains and dependence functions.
		val d1Prime = updatedRestrict1.restrictDomain
		val d2Prime = updatedRestrict2.restrictDomain
		
		val fPrime = outerDependence.function.copy
		val f1Prime = updatedDep1.function.copy
		val f2Prime = updatedDep2.function.copy
		
		// Check that everything is still correct.
		assertTrue(d1Prime.isEqual(d1.apply(f1.copy.toMap)))
		assertTrue(d2Prime.isEqual(d2.apply(f2.copy.toMap)))
		
		assertTrue(f1.isPlainEqual(f1Prime.pullback(fPrime.copy)))
		assertTrue(f2.isPlainEqual(f2Prime.pullback(fPrime)))
	}
	
	
	////////////////////////////////////////////////////////////
	// Unary Expression Rules
	////////////////////////////////////////////////////////////
	
	@Test
	def unaryExpression_01() {
		// From:  op (f @ E)
		// To:    f @ (op E)
		
		// Get the equation for the unary expression under test,
		// extract the dependence function, and apply dependence raising.
		val equation = getEquation("unaryExpression_01", "X")
		val originalUnaryExpr = equation.expr as UnaryExpression
		val f = (originalUnaryExpr.expr as DependenceExpression).function
		
		RaiseDependence.apply(equation.expr)
		
		// Make sure the equation now has a dependence expression at the top level
		// and that its function is the same as the original one.
		assertTrue(equation.expr instanceof DependenceExpression)
		val newDependence = equation.expr as DependenceExpression
		assertTrue(f.isPlainEqual(newDependence.function))
	}
	
	
	////////////////////////////////////////////////////////////
	// Binary Expression Rules
	////////////////////////////////////////////////////////////
	
	@Test
	def simpleBinaryExpression_01() {
		// From:  f1@A op f2@B
		// To:    (f')@(f1'@A op f2'@B)
		// Where: f1 = f' @ f1' and f2 = f' @ f2'
		
		// Get the equation for the binary expression under test,
		// extract the original f1 and f2, then apply dependence raising.
		val equation = getEquation("simpleBinaryExpression_01", "X")
		val originalBinaryExpr = equation.expr as BinaryExpression 
		val f1 = (originalBinaryExpr.left as DependenceExpression).function
		val f2 = (originalBinaryExpr.right as DependenceExpression).function
		
		assertBinaryEquationCorrect(equation, f1, f2)
	}
	
	@Test
	def binaryExpressionBecomesNested_01() {
		// From:  f1@(f2@A op f3@B)
		// To:    f1'@(f2'@A op f3'@B)
		// Where: f1@f2 = f1'@f2' and f1@f3 = f1'@f3'
		
		// There is a dependence function around a binary expression.
		// The binary expression will have a dependence function raised,
		// resulting in nested dependence expressions.
		// These nested dependences should be merged, resulting in
		// a single dependence expression containing a binary expression,
		// which in turn contains two dependence expressions.
		
		// Get the equation for the top-level dependence expression under test,
		// extract the nested dependence functions, then apply dependence raising.
		val equation = getEquation("binaryExpressionBecomesNested_01", "X")
		val originalDependence = equation.expr as DependenceExpression
		val f1 = originalDependence.function
		
		val originalBinaryExpr = originalDependence.expr as BinaryExpression
		val f2 = (originalBinaryExpr.left as DependenceExpression).function
		val f3 = (originalBinaryExpr.right as DependenceExpression).function
		
		// We want f1@f2 = f1'@f2' and f1@f3 = f1'@f3'
		val firstExpected = f2.pullback(f1.copy)
		val secondExpected = f3.pullback(f1)
		assertBinaryEquationCorrect(equation, firstExpected, secondExpected)
	}
	
	@Test
	def binaryExpressionBecomesNested_02() {
		// From:  f1@(f2@A op B)
		// To:    f1'@(f2'@A op f3'@B)
		// Where: f1@f2 = f1'@f2' and f1 = f1'@f3'
		
		// This is effectively the same case as "binaryExpressionBecomesNested_01",
		// except f3 is the identity function.
		
		// Get the equation for the top-level dependence expression under test,
		// extract the nested dependence functions, then apply dependence raising.
		val equation = getEquation("binaryExpressionBecomesNested_02", "X")
		val originalDependence = equation.expr as DependenceExpression
		val f1 = originalDependence.function
		
		val originalBinaryExpr = originalDependence.expr as BinaryExpression
		val f2 = (originalBinaryExpr.left as DependenceExpression).function
		
		// We want f1@f2 = f1'@f2' and f1 = f1'@f3'
		val firstExpected = f2.pullback(f1.copy)
		val secondExpected = f1
		assertBinaryEquationCorrect(equation, firstExpected, secondExpected)
	}
	
	@Test
	def binaryExpressionBecomesNested_03() {
		// From:  f1@(A op f3@B)
		// To:    f1'@(f2'@A op f3'@B)
		// Where: f1 = f1'@f2' and f1@f3 = f1'@f3'
		
		// This is effectively the same case as "binaryExpressionBecomesNested_01",
		// except f2 is the identity function.
		
		// Get the equation for the top-level dependence expression under test,
		// extract the nested dependence functions, then apply dependence raising.
		val equation = getEquation("binaryExpressionBecomesNested_03", "X")
		val originalDependence = equation.expr as DependenceExpression
		val f1 = originalDependence.function
		
		val originalBinaryExpr = originalDependence.expr as BinaryExpression
		val f3 = (originalBinaryExpr.right as DependenceExpression).function
		
		// We want f1 = f1'@f2' and f1@f3 = f1'@f3'
		val firstExpected = f1.copy
		val secondExpected = f3.pullback(f1)
		assertBinaryEquationCorrect(equation, firstExpected, secondExpected)
	}
	
	@Test
	def binaryExpressionBecomesNested_04() {
		// From:  f1@(A op B)
		// To:    f1'@(f2'@A op f3'@B)
		// Where: f1 = f1'@f2' and f1 = f1'@f3'
		
		// This is effectively the same case as "binaryExpressionBecomesNested_01",
		// except f2 is the identity function.
		
		// Get the equation for the top-level dependence expression under test,
		// extract the nested dependence functions, then apply dependence raising.
		val equation = getEquation("binaryExpressionBecomesNested_04", "X")
		val originalDependence = equation.expr as DependenceExpression
		val f1 = originalDependence.function
		
		// We want f1 = f1'@f2' and f1 = f1'@f3'
		val firstExpected = f1.copy
		val secondExpected = f1
		assertBinaryEquationCorrect(equation, firstExpected, secondExpected)
	}
	
	static def assertBinaryEquationCorrect(StandardEquation equation, ISLMultiAff firstExpected, ISLMultiAff secondExpected) {
		// Apply dependence raising.
		RaiseDependence.apply(equation.expr)
		
		// The equation should now have a dependence function at the top level,
		// a binary expression in that, and dependence expressions as the left
		// and right terms of the binary expression.
		assertTrue(equation.expr instanceof DependenceExpression)
		val topDependence = equation.expr as DependenceExpression
		val f1Prime = topDependence.function
		
		assertTrue(topDependence.expr instanceof BinaryExpression)
		val binaryExpr = topDependence.expr as BinaryExpression
		
		assertTrue(binaryExpr.left instanceof DependenceExpression)
		assertTrue(binaryExpr.right instanceof DependenceExpression)
		val f2Prime = (binaryExpr.left as DependenceExpression).function
		val f3Prime = (binaryExpr.right as DependenceExpression).function
		
		// Make sure that f1' @ f2' and f1' @ f3' are the expected values.
		val firstActual = f2Prime.pullback(f1Prime.copy)
		assertTrue(firstExpected.isPlainEqual(firstActual))

		val secondActual = f3Prime.pullback(f1Prime)
		assertTrue(secondExpected.isPlainEqual(secondActual))
	}
	
	
	////////////////////////////////////////////////////////////
	// Multi-Arg and Case Expression Rules
	////////////////////////////////////////////////////////////
	
	@Test
	def multiArgTest_01() {
		// From:  op(f1@A1, f2@A2, f3@A3)
		// To:    (f')@ op(f1'@A1, f2'@A2, f3'@A3)
		// Where: fn = f' @ fn' for n=1,2,3
		
		// Get the equation for the expression under test,
		// extract the original dependence functions (mapped by their inner expression),
		// then apply dependence raising.
		val equation = getEquation("multiArgTest_01", "X")
		val expectedFunctions = (equation.expr as MultiArgExpression).exprs
			.map[expr | expr as DependenceExpression]
			.toMap[expr | expr.expr]
			.mapValues[expr | expr.function]
			.toHashMap

		RaiseDependence.apply(equation.expr)
		
		// The equation should now have a dependence function at the top level,
		// a multi-arg expression in that, and dependence expressions as the children.
		assertTrue(typeof(DependenceExpression).isInstance(equation.expr))
		val topDependence = equation.expr as DependenceExpression
		val commonFactor = topDependence.function
		
		assertTrue(typeof(MultiArgExpression).isInstance(topDependence.expr))
		val multiArgExpr = topDependence.expr as MultiArgExpression
		
		multiArgExpr.exprs.forEach[child | assertTrue(child instanceof DependenceExpression)]
		val remainingTerms = multiArgExpr.exprs
			.map[child | child as DependenceExpression]
			.toMap[child | child.expr]
			.mapValues[child | child.function]
			.toHashMap
		
		// Make sure that fn = f' @ fn' for all n.
		for (innerExpr : expectedFunctions.keySet) {
			val expected = expectedFunctions.get(innerExpr)
			val actual = remainingTerms.get(innerExpr).pullback(commonFactor.copy())
			assertTrue(expected.isPlainEqual(actual))
		}
	}
	
	@Test
	def caseTest_01() {
		// From:  case {f1@E1, f2@E2, ...}
		// To:    (f')@ case{f1'@E1, f2'@E2, ...}
		// Where: fn = f' @ fn'
		
		// Get the equation for the expression under test,
		// extract the original dependence functions (mapped by their inner expression),
		// then apply dependence raising.
		val equation = getEquation("caseTest_01", "X")
		val expectedFunctions = (equation.expr as CaseExpression).exprs
			.map[expr | expr as DependenceExpression]
			.toMap[expr | expr.expr]
			.mapValues[expr | expr.function]
			.toHashMap

		RaiseDependence.apply(equation.expr)
		
		// The equation should now have a dependence function at the top level,
		// a case expression in that, and dependence expressions as the children.
		assertTrue(typeof(DependenceExpression).isInstance(equation.expr))
		val topDependence = equation.expr as DependenceExpression
		val commonFactor = topDependence.function
		
		assertTrue(typeof(CaseExpression).isInstance(topDependence.expr))
		val caseExpr = topDependence.expr as CaseExpression
		
		caseExpr.exprs.forEach[child | assertTrue(child instanceof DependenceExpression)]
		val remainingTerms = caseExpr.exprs
			.map[child | child as DependenceExpression]
			.toMap[child | child.expr]
			.mapValues[child | child.function]
			.toHashMap
		
		// Make sure that fn = f' @ fn' for all n.
		for (innerExpr : expectedFunctions.keySet) {
			val expected = expectedFunctions.get(innerExpr)
			val actual = remainingTerms.get(innerExpr).pullback(commonFactor.copy())
			assertTrue(expected.isPlainEqual(actual))
		}
	}
}