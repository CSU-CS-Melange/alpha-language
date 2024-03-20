package alpha.model.tests.transformation.reduction

import alpha.model.AlphaModelLoader
import alpha.model.BinaryExpression
import alpha.model.DependenceExpression
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.VariableExpression
import alpha.model.transformation.reduction.NormalizeReductionDeep
import org.junit.Test

import static org.junit.Assert.*

import static extension alpha.commands.Utility.*
import static extension alpha.commands.UtilityBase.*

class NormalizeReductionDeepTest {
	/** The path to the Alpha file for these unit tests. */
	static val alphaFile = "resources/src-valid/transformation-reduction-tests/normalize-reduction-deep/normalizeReductionDeep.alpha";
	
	/** Gets the desired system for these unit tests. */
	static def getSystem(String system) {
		return AlphaModelLoader.loadModel(alphaFile).GetSystem(system)
	}
	
	/** Gets the desired equation for these unit tests. */
	static def getEquation(String system, String equation) {
		return getSystem(system).GetSystemBody().GetEquation(equation)
	}
	
	/**
	 * Tests that applying NormalizeReductionDeep to a system
	 * won't move a top-level reduction into a separate equation.
	 */
	@Test
	def topLevelReduction_01() {
		val system = getSystem("topLevelReduction")
		val equation = system.GetSystemBody().GetEquation("X")
		val reduction = equation.expr as ReduceExpression
		
		NormalizeReductionDeep.apply(system)
		
		// The reduction should still be directly inside the equation.
		assertEquals(equation, reduction.eContainer) 
	}
	
	/**
	 * Tests that applying NormalizeReductionDeep to an equation
	 * won't move a top-level reduction into a separate equation.
	 */
	@Test
	def topLevelReduction_02() {
		val equation = getEquation("topLevelReduction", "X")
		val reduction = equation.expr as ReduceExpression
		
		NormalizeReductionDeep.apply(equation)
		
		// The reduction should still be directly inside the equation.
		assertEquals(equation, reduction.eContainer) 
	}
	
	/**
	 * Tests that applying NormalizeReductionDeep to a top-level reduction
	 * won't move it into a separate equation.
	 */
	@Test
	def topLevelReduction_03() {
		val equation = getEquation("topLevelReduction", "X")
		val reduction = equation.expr as ReduceExpression
		
		NormalizeReductionDeep.apply(reduction)
		
		// The reduction should still be directly inside the equation.
		assertEquals(equation, reduction.eContainer) 
	}
	
	/**
	 * Tests that applying NormalizeReductionDeep will move a reduction
	 * that's inside a dependence expression to its own equation.
	 */
	@Test
	def reductionInsideDependence_01() {
		val systemBody = getSystem("reductionInsideDependence").GetSystemBody()
		val originalEquation = systemBody.GetEquation("X")
		val dependence = originalEquation.expr as DependenceExpression
		val reduction = dependence.expr as ReduceExpression
		
		NormalizeReductionDeep.apply(reduction)
		
		// The original equation should still contain the same dependence,
		// but the dependence should now contain a variable expression.
		assertEquals(dependence, originalEquation.expr)
		assertTrue(dependence.expr instanceof VariableExpression)
		val variable = dependence.expr as VariableExpression
		
		// We should now have a new equation that the variable points to
		// which contains the original reduction.
		val newEquationName = variable.variable.name
		val newEquation = systemBody.equations
			.filter(typeof(StandardEquation))
			.filter[eq | eq.variable.name == newEquationName]
			.head
			
		assertNotNull(newEquation)
		assertEquals(newEquation, reduction.eContainer)
	}
	
	/**
	 * Tests that applying NormalizeReductionDeep to a system
	 * will move a nested reduction to its own equation.
	 */
	 @Test
	 def nestedReduction_01() {
		val system = getSystem("nestedReduction_01")
		val systemBody = system.GetSystemBody
		val originalEquation = systemBody.GetEquation("X")
		val outerReduction = originalEquation.expr as ReduceExpression
		val innerReduction = outerReduction.body as ReduceExpression
		
		NormalizeReductionDeep.apply(system)
		
		// The outer reduction should still be directly inside the equation,
		// but its body should now be a variable expression.
		assertEquals(originalEquation, outerReduction.eContainer)
		assertTrue(outerReduction.body instanceof VariableExpression)
		val variable = outerReduction.body as VariableExpression
		
		// We should now have a new equation that the variable points to
		// which contains the inner reduction.
		val newEquationName = variable.variable.name
		val newEquation = systemBody.equations
			.filter(typeof(StandardEquation))
			.filter[eq | eq.variable.name == newEquationName]
			.head
			
		assertNotNull(newEquation)
		assertEquals(newEquation, innerReduction.eContainer)
	 }
	
	/**
	 * Tests that applying NormalizeReductionDeep to an outer reduction
	 * will move a nested reduction to its own equation.
	 */
	 @Test
	 def nestedReduction_02() {
		val systemBody = getSystem("nestedReduction_01").GetSystemBody
		val originalEquation = systemBody.GetEquation("X")
		val outerReduction = originalEquation.expr as ReduceExpression
		val innerReduction = outerReduction.body as ReduceExpression
		
		NormalizeReductionDeep.apply(outerReduction)
		
		// The outer reduction should still be directly inside the equation,
		// but its body should now be a variable expression.
		assertEquals(originalEquation, outerReduction.eContainer)
		assertTrue(outerReduction.body instanceof VariableExpression)
		val variable = outerReduction.body as VariableExpression
		
		// We should now have a new equation that the variable points to
		// which contains the inner reduction.
		val newEquationName = variable.variable.name
		val newEquation = systemBody.equations
			.filter(typeof(StandardEquation))
			.filter[eq | eq.variable.name == newEquationName]
			.head
			
		assertNotNull(newEquation)
		assertEquals(newEquation, innerReduction.eContainer)
	 }
	
	/**
	 * Tests that applying NormalizeReductionDeep to an inner reduction
	 * will move it to its own equation.
	 */
	 @Test
	 def nestedReduction_03() {
		val systemBody = getSystem("nestedReduction_01").GetSystemBody
		val originalEquation = systemBody.GetEquation("X")
		val outerReduction = originalEquation.expr as ReduceExpression
		val innerReduction = outerReduction.body as ReduceExpression
		
		NormalizeReductionDeep.apply(innerReduction)
		
		// The outer reduction should still be directly inside the equation,
		// but its body should now be a variable expression.
		assertEquals(originalEquation, outerReduction.eContainer)
		assertTrue(outerReduction.body instanceof VariableExpression)
		val variable = outerReduction.body as VariableExpression
		
		// We should now have a new equation that the variable points to
		// which contains the inner reduction.
		val newEquationName = variable.variable.name
		val newEquation = systemBody.equations
			.filter(typeof(StandardEquation))
			.filter[eq | eq.variable.name == newEquationName]
			.head
			
		assertNotNull(newEquation)
		assertEquals(newEquation, innerReduction.eContainer)
	 }
	 
	 /**
	  * Tests that applying NormalizeReductionDeep to the outermost reduction
	  * of a triply nested reduction will move all three reductions to their
	  * own equations.
	  */
	 @Test
	 def nestedReduction_04() {
	 	val systemBody = getSystem("nestedReduction_02").GetSystemBody
	 	val originalEquation = systemBody.GetEquation("X")
	 	
	 	val outerReduction = originalEquation.expr as ReduceExpression
	 	val middleReduction = outerReduction.body as ReduceExpression
	 	val innerReduction = middleReduction.body as ReduceExpression
	 	
		NormalizeReductionDeep.apply(outerReduction)
		
		// The outer reduction should still be directly inside the equation,
		// but its body should now be a variable expression.
		assertEquals(originalEquation, outerReduction.eContainer)
		assertTrue(outerReduction.body instanceof VariableExpression)
		val outerVariable = outerReduction.body as VariableExpression
		
		// We should now have a new equation that the variable points to
		// which contains the middle reduction,
		// which should also contain a variable expression now.
		val middleEquationName = outerVariable.variable.name
		val middleEquation = systemBody.equations
			.filter(typeof(StandardEquation))
			.filter[eq | eq.variable.name == middleEquationName]
			.head
		
		assertNotNull(middleEquation)
		assertEquals(middleEquation, middleReduction.eContainer)
		assertTrue(middleReduction.body instanceof VariableExpression)
		val middleVariable = middleReduction.body as VariableExpression
		
		// Finally, the inner reduction should be in its own equation.
		val innerEquationName = middleVariable.variable.name
		val innerEquation = systemBody.equations
			.filter(typeof(StandardEquation))
			.filter[eq | eq.variable.name == innerEquationName]
			.head
		
		assertNotNull(innerEquation)
		assertEquals(innerEquation, innerReduction.eContainer)
	 }
	 
	 /**
	  * Tests that applying NormalizeReductionDeep to the outermost reduction
	  * of a triply nested reduction will move all three reductions to their
	  * own equations. Both inner reductions are nested inside binary expressions.
	  */
	 @Test
	 def nestedReduction_05() {
	 	val systemBody = getSystem("nestedReduction_03").GetSystemBody
	 	val originalEquation = systemBody.GetEquation("X")
	 	
	 	val outerReduction = originalEquation.expr as ReduceExpression
	 	val outerBinary = outerReduction.body as BinaryExpression
	 	
	 	val middleReduction = outerBinary.right as ReduceExpression
	 	val middleBinary = middleReduction.body as BinaryExpression
	 	
	 	val innerReduction = middleBinary.right as ReduceExpression
	 	
		NormalizeReductionDeep.apply(outerReduction)
		
		// The outer reduction should still be directly inside the equation,
		// and it should still contain the same binary expression.
		// However, the right-hand side of that binary expression should now be
		// a variable expression (instead of the middle reduction).
		assertEquals(originalEquation, outerReduction.eContainer)
		assertEquals(outerReduction.body, outerBinary)
		assertTrue(outerBinary.right instanceof VariableExpression)
		val outerVariable = outerBinary.right as VariableExpression
		
		// We should now have a new equation that the variable points to.
		// This equation should point to the middle reduction.
		// The reduction should still contain the same binary expression,
		// but the right-hand side of it should be another variable expression.
		val middleEquationName = outerVariable.variable.name
		val middleEquation = systemBody.equations
			.filter(typeof(StandardEquation))
			.filter[eq | eq.variable.name == middleEquationName]
			.head
		
		assertNotNull(middleEquation)
		assertEquals(middleEquation, middleReduction.eContainer)
		assertEquals(middleReduction, middleBinary.eContainer)
		assertTrue(middleBinary.right instanceof VariableExpression)
		val middleVariable = middleBinary.right as VariableExpression
		
		// Finally, the inner reduction should be in its own equation.
		val innerEquationName = middleVariable.variable.name
		val innerEquation = systemBody.equations
			.filter(typeof(StandardEquation))
			.filter[eq | eq.variable.name == innerEquationName]
			.head
		
		assertNotNull(innerEquation)
		assertEquals(innerEquation, innerReduction.eContainer)
	 }
}