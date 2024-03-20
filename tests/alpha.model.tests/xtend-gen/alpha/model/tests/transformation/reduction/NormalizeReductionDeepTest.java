package alpha.model.tests.transformation.reduction;

import alpha.commands.Utility;
import alpha.commands.UtilityBase;
import alpha.model.AlphaExpression;
import alpha.model.AlphaModelLoader;
import alpha.model.AlphaSystem;
import alpha.model.BinaryExpression;
import alpha.model.DependenceExpression;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.VariableExpression;
import alpha.model.transformation.reduction.NormalizeReductionDeep;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class NormalizeReductionDeepTest {
  /**
   * The path to the Alpha file for these unit tests.
   */
  private static final String alphaFile = "resources/src-valid/transformation-reduction-tests/normalize-reduction-deep/normalizeReductionDeep.alpha";

  /**
   * Gets the desired system for these unit tests.
   */
  public static AlphaSystem getSystem(final String system) {
    try {
      return UtilityBase.GetSystem(AlphaModelLoader.loadModel(NormalizeReductionDeepTest.alphaFile), system);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Gets the desired equation for these unit tests.
   */
  public static StandardEquation getEquation(final String system, final String equation) {
    return UtilityBase.GetEquation(Utility.GetSystemBody(NormalizeReductionDeepTest.getSystem(system)), equation);
  }

  /**
   * Tests that applying NormalizeReductionDeep to a system
   * won't move a top-level reduction into a separate equation.
   */
  @Test
  public void topLevelReduction_01() {
    final AlphaSystem system = NormalizeReductionDeepTest.getSystem("topLevelReduction");
    final StandardEquation equation = UtilityBase.GetEquation(Utility.GetSystemBody(system), "X");
    AlphaExpression _expr = equation.getExpr();
    final ReduceExpression reduction = ((ReduceExpression) _expr);
    NormalizeReductionDeep.apply(system);
    Assert.assertEquals(equation, reduction.eContainer());
  }

  /**
   * Tests that applying NormalizeReductionDeep to an equation
   * won't move a top-level reduction into a separate equation.
   */
  @Test
  public void topLevelReduction_02() {
    final StandardEquation equation = NormalizeReductionDeepTest.getEquation("topLevelReduction", "X");
    AlphaExpression _expr = equation.getExpr();
    final ReduceExpression reduction = ((ReduceExpression) _expr);
    NormalizeReductionDeep.apply(equation);
    Assert.assertEquals(equation, reduction.eContainer());
  }

  /**
   * Tests that applying NormalizeReductionDeep to a top-level reduction
   * won't move it into a separate equation.
   */
  @Test
  public void topLevelReduction_03() {
    final StandardEquation equation = NormalizeReductionDeepTest.getEquation("topLevelReduction", "X");
    AlphaExpression _expr = equation.getExpr();
    final ReduceExpression reduction = ((ReduceExpression) _expr);
    NormalizeReductionDeep.apply(reduction);
    Assert.assertEquals(equation, reduction.eContainer());
  }

  /**
   * Tests that applying NormalizeReductionDeep will move a reduction
   * that's inside a dependence expression to its own equation.
   */
  @Test
  public void reductionInsideDependence_01() {
    final SystemBody systemBody = Utility.GetSystemBody(NormalizeReductionDeepTest.getSystem("reductionInsideDependence"));
    final StandardEquation originalEquation = UtilityBase.GetEquation(systemBody, "X");
    AlphaExpression _expr = originalEquation.getExpr();
    final DependenceExpression dependence = ((DependenceExpression) _expr);
    AlphaExpression _expr_1 = dependence.getExpr();
    final ReduceExpression reduction = ((ReduceExpression) _expr_1);
    NormalizeReductionDeep.apply(reduction);
    Assert.assertEquals(dependence, originalEquation.getExpr());
    AlphaExpression _expr_2 = dependence.getExpr();
    Assert.assertTrue((_expr_2 instanceof VariableExpression));
    AlphaExpression _expr_3 = dependence.getExpr();
    final VariableExpression variable = ((VariableExpression) _expr_3);
    final String newEquationName = variable.getVariable().getName();
    final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
      String _name = eq.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_name, newEquationName));
    };
    final StandardEquation newEquation = IterableExtensions.<StandardEquation>head(IterableExtensions.<StandardEquation>filter(Iterables.<StandardEquation>filter(systemBody.getEquations(), StandardEquation.class), _function));
    Assert.assertNotNull(newEquation);
    Assert.assertEquals(newEquation, reduction.eContainer());
  }

  /**
   * Tests that applying NormalizeReductionDeep to a system
   * will move a nested reduction to its own equation.
   */
  @Test
  public void nestedReduction_01() {
    final AlphaSystem system = NormalizeReductionDeepTest.getSystem("nestedReduction_01");
    final SystemBody systemBody = Utility.GetSystemBody(system);
    final StandardEquation originalEquation = UtilityBase.GetEquation(systemBody, "X");
    AlphaExpression _expr = originalEquation.getExpr();
    final ReduceExpression outerReduction = ((ReduceExpression) _expr);
    AlphaExpression _body = outerReduction.getBody();
    final ReduceExpression innerReduction = ((ReduceExpression) _body);
    NormalizeReductionDeep.apply(system);
    Assert.assertEquals(originalEquation, outerReduction.eContainer());
    AlphaExpression _body_1 = outerReduction.getBody();
    Assert.assertTrue((_body_1 instanceof VariableExpression));
    AlphaExpression _body_2 = outerReduction.getBody();
    final VariableExpression variable = ((VariableExpression) _body_2);
    final String newEquationName = variable.getVariable().getName();
    final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
      String _name = eq.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_name, newEquationName));
    };
    final StandardEquation newEquation = IterableExtensions.<StandardEquation>head(IterableExtensions.<StandardEquation>filter(Iterables.<StandardEquation>filter(systemBody.getEquations(), StandardEquation.class), _function));
    Assert.assertNotNull(newEquation);
    Assert.assertEquals(newEquation, innerReduction.eContainer());
  }

  /**
   * Tests that applying NormalizeReductionDeep to an outer reduction
   * will move a nested reduction to its own equation.
   */
  @Test
  public void nestedReduction_02() {
    final SystemBody systemBody = Utility.GetSystemBody(NormalizeReductionDeepTest.getSystem("nestedReduction_01"));
    final StandardEquation originalEquation = UtilityBase.GetEquation(systemBody, "X");
    AlphaExpression _expr = originalEquation.getExpr();
    final ReduceExpression outerReduction = ((ReduceExpression) _expr);
    AlphaExpression _body = outerReduction.getBody();
    final ReduceExpression innerReduction = ((ReduceExpression) _body);
    NormalizeReductionDeep.apply(outerReduction);
    Assert.assertEquals(originalEquation, outerReduction.eContainer());
    AlphaExpression _body_1 = outerReduction.getBody();
    Assert.assertTrue((_body_1 instanceof VariableExpression));
    AlphaExpression _body_2 = outerReduction.getBody();
    final VariableExpression variable = ((VariableExpression) _body_2);
    final String newEquationName = variable.getVariable().getName();
    final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
      String _name = eq.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_name, newEquationName));
    };
    final StandardEquation newEquation = IterableExtensions.<StandardEquation>head(IterableExtensions.<StandardEquation>filter(Iterables.<StandardEquation>filter(systemBody.getEquations(), StandardEquation.class), _function));
    Assert.assertNotNull(newEquation);
    Assert.assertEquals(newEquation, innerReduction.eContainer());
  }

  /**
   * Tests that applying NormalizeReductionDeep to an inner reduction
   * will move it to its own equation.
   */
  @Test
  public void nestedReduction_03() {
    final SystemBody systemBody = Utility.GetSystemBody(NormalizeReductionDeepTest.getSystem("nestedReduction_01"));
    final StandardEquation originalEquation = UtilityBase.GetEquation(systemBody, "X");
    AlphaExpression _expr = originalEquation.getExpr();
    final ReduceExpression outerReduction = ((ReduceExpression) _expr);
    AlphaExpression _body = outerReduction.getBody();
    final ReduceExpression innerReduction = ((ReduceExpression) _body);
    NormalizeReductionDeep.apply(innerReduction);
    Assert.assertEquals(originalEquation, outerReduction.eContainer());
    AlphaExpression _body_1 = outerReduction.getBody();
    Assert.assertTrue((_body_1 instanceof VariableExpression));
    AlphaExpression _body_2 = outerReduction.getBody();
    final VariableExpression variable = ((VariableExpression) _body_2);
    final String newEquationName = variable.getVariable().getName();
    final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
      String _name = eq.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_name, newEquationName));
    };
    final StandardEquation newEquation = IterableExtensions.<StandardEquation>head(IterableExtensions.<StandardEquation>filter(Iterables.<StandardEquation>filter(systemBody.getEquations(), StandardEquation.class), _function));
    Assert.assertNotNull(newEquation);
    Assert.assertEquals(newEquation, innerReduction.eContainer());
  }

  /**
   * Tests that applying NormalizeReductionDeep to the outermost reduction
   * of a triply nested reduction will move all three reductions to their
   * own equations.
   */
  @Test
  public void nestedReduction_04() {
    final SystemBody systemBody = Utility.GetSystemBody(NormalizeReductionDeepTest.getSystem("nestedReduction_02"));
    final StandardEquation originalEquation = UtilityBase.GetEquation(systemBody, "X");
    AlphaExpression _expr = originalEquation.getExpr();
    final ReduceExpression outerReduction = ((ReduceExpression) _expr);
    AlphaExpression _body = outerReduction.getBody();
    final ReduceExpression middleReduction = ((ReduceExpression) _body);
    AlphaExpression _body_1 = middleReduction.getBody();
    final ReduceExpression innerReduction = ((ReduceExpression) _body_1);
    NormalizeReductionDeep.apply(outerReduction);
    Assert.assertEquals(originalEquation, outerReduction.eContainer());
    AlphaExpression _body_2 = outerReduction.getBody();
    Assert.assertTrue((_body_2 instanceof VariableExpression));
    AlphaExpression _body_3 = outerReduction.getBody();
    final VariableExpression outerVariable = ((VariableExpression) _body_3);
    final String middleEquationName = outerVariable.getVariable().getName();
    final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
      String _name = eq.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_name, middleEquationName));
    };
    final StandardEquation middleEquation = IterableExtensions.<StandardEquation>head(IterableExtensions.<StandardEquation>filter(Iterables.<StandardEquation>filter(systemBody.getEquations(), StandardEquation.class), _function));
    Assert.assertNotNull(middleEquation);
    Assert.assertEquals(middleEquation, middleReduction.eContainer());
    AlphaExpression _body_4 = middleReduction.getBody();
    Assert.assertTrue((_body_4 instanceof VariableExpression));
    AlphaExpression _body_5 = middleReduction.getBody();
    final VariableExpression middleVariable = ((VariableExpression) _body_5);
    final String innerEquationName = middleVariable.getVariable().getName();
    final Function1<StandardEquation, Boolean> _function_1 = (StandardEquation eq) -> {
      String _name = eq.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_name, innerEquationName));
    };
    final StandardEquation innerEquation = IterableExtensions.<StandardEquation>head(IterableExtensions.<StandardEquation>filter(Iterables.<StandardEquation>filter(systemBody.getEquations(), StandardEquation.class), _function_1));
    Assert.assertNotNull(innerEquation);
    Assert.assertEquals(innerEquation, innerReduction.eContainer());
  }

  /**
   * Tests that applying NormalizeReductionDeep to the outermost reduction
   * of a triply nested reduction will move all three reductions to their
   * own equations. Both inner reductions are nested inside binary expressions.
   */
  @Test
  public void nestedReduction_05() {
    final SystemBody systemBody = Utility.GetSystemBody(NormalizeReductionDeepTest.getSystem("nestedReduction_03"));
    final StandardEquation originalEquation = UtilityBase.GetEquation(systemBody, "X");
    AlphaExpression _expr = originalEquation.getExpr();
    final ReduceExpression outerReduction = ((ReduceExpression) _expr);
    AlphaExpression _body = outerReduction.getBody();
    final BinaryExpression outerBinary = ((BinaryExpression) _body);
    AlphaExpression _right = outerBinary.getRight();
    final ReduceExpression middleReduction = ((ReduceExpression) _right);
    AlphaExpression _body_1 = middleReduction.getBody();
    final BinaryExpression middleBinary = ((BinaryExpression) _body_1);
    AlphaExpression _right_1 = middleBinary.getRight();
    final ReduceExpression innerReduction = ((ReduceExpression) _right_1);
    NormalizeReductionDeep.apply(outerReduction);
    Assert.assertEquals(originalEquation, outerReduction.eContainer());
    Assert.assertEquals(outerReduction.getBody(), outerBinary);
    AlphaExpression _right_2 = outerBinary.getRight();
    Assert.assertTrue((_right_2 instanceof VariableExpression));
    AlphaExpression _right_3 = outerBinary.getRight();
    final VariableExpression outerVariable = ((VariableExpression) _right_3);
    final String middleEquationName = outerVariable.getVariable().getName();
    final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
      String _name = eq.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_name, middleEquationName));
    };
    final StandardEquation middleEquation = IterableExtensions.<StandardEquation>head(IterableExtensions.<StandardEquation>filter(Iterables.<StandardEquation>filter(systemBody.getEquations(), StandardEquation.class), _function));
    Assert.assertNotNull(middleEquation);
    Assert.assertEquals(middleEquation, middleReduction.eContainer());
    Assert.assertEquals(middleReduction, middleBinary.eContainer());
    AlphaExpression _right_4 = middleBinary.getRight();
    Assert.assertTrue((_right_4 instanceof VariableExpression));
    AlphaExpression _right_5 = middleBinary.getRight();
    final VariableExpression middleVariable = ((VariableExpression) _right_5);
    final String innerEquationName = middleVariable.getVariable().getName();
    final Function1<StandardEquation, Boolean> _function_1 = (StandardEquation eq) -> {
      String _name = eq.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_name, innerEquationName));
    };
    final StandardEquation innerEquation = IterableExtensions.<StandardEquation>head(IterableExtensions.<StandardEquation>filter(Iterables.<StandardEquation>filter(systemBody.getEquations(), StandardEquation.class), _function_1));
    Assert.assertNotNull(innerEquation);
    Assert.assertEquals(innerEquation, innerReduction.eContainer());
  }
}
