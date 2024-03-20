package alpha.model.tests.transformations;

import alpha.commands.Utility;
import alpha.commands.UtilityBase;
import alpha.model.AlphaExpression;
import alpha.model.AlphaModelLoader;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.IndexExpression;
import alpha.model.MultiArgExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.UnaryExpression;
import alpha.model.VariableExpression;
import alpha.model.transformation.RaiseDependence;
import alpha.model.util.CommonExtensions;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.MapExtensions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class RaiseDependenceTest {
  /**
   * The path to the Alpha file for these unit tests.
   */
  private static final String alphaFile = "resources/src-valid/transformation-tests/raise-dependence/raiseDependence.alpha";

  /**
   * Gets the desired equation for these unit tests.
   */
  public static StandardEquation getEquation(final String system, final String equation) {
    try {
      return UtilityBase.GetEquation(Utility.GetSystemBody(UtilityBase.GetSystem(AlphaModelLoader.loadModel(RaiseDependenceTest.alphaFile), system)), equation);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void wrapConstantExpression_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("wrapConstantExpression_01", "X");
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr = equation.getExpr();
    Assert.assertTrue((_expr instanceof DependenceExpression));
    AlphaExpression _expr_1 = equation.getExpr();
    final DependenceExpression topDependence = ((DependenceExpression) _expr_1);
    AlphaExpression _expr_2 = topDependence.getExpr();
    Assert.assertTrue((_expr_2 instanceof ConstantExpression));
  }

  @Test
  public void wrapVariableExpression_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("wrapVariableExpression_01", "X");
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr = equation.getExpr();
    Assert.assertTrue((_expr instanceof DependenceExpression));
    AlphaExpression _expr_1 = equation.getExpr();
    final DependenceExpression topDependence = ((DependenceExpression) _expr_1);
    AlphaExpression _expr_2 = topDependence.getExpr();
    Assert.assertTrue((_expr_2 instanceof VariableExpression));
  }

  @Test
  public void wrapVariableExpression_02() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("wrapVariableExpression_02", "X");
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr = equation.getExpr();
    Assert.assertTrue((_expr instanceof DependenceExpression));
    AlphaExpression _expr_1 = equation.getExpr();
    final DependenceExpression topDependence = ((DependenceExpression) _expr_1);
    AlphaExpression _expr_2 = topDependence.getExpr();
    Assert.assertTrue((_expr_2 instanceof VariableExpression));
  }

  @Test
  public void wrapIndexExpression_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("wrapIndexExpression_01", "X");
    AlphaExpression _expr = equation.getExpr();
    final ISLMultiAff originalFunction = ((IndexExpression) _expr).getFunction();
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr_1 = equation.getExpr();
    Assert.assertTrue((_expr_1 instanceof DependenceExpression));
    AlphaExpression _expr_2 = equation.getExpr();
    final DependenceExpression dependence = ((DependenceExpression) _expr_2);
    Assert.assertTrue(originalFunction.isPlainEqual(dependence.getFunction()));
    AlphaExpression _expr_3 = dependence.getExpr();
    Assert.assertTrue((_expr_3 instanceof IndexExpression));
    AlphaExpression _expr_4 = dependence.getExpr();
    final IndexExpression index = ((IndexExpression) _expr_4);
    Assert.assertTrue(index.getFunction().isIdentity());
  }

  @Test
  public void wrapIndexExpression_02() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("wrapIndexExpression_02", "X");
    AlphaExpression _expr = equation.getExpr();
    final DependenceExpression originalDependence = ((DependenceExpression) _expr);
    AlphaExpression _expr_1 = originalDependence.getExpr();
    final IndexExpression originalIndex = ((IndexExpression) _expr_1);
    final ISLMultiAff expectedFunction = originalIndex.getFunction().pullback(originalDependence.getFunction());
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr_2 = equation.getExpr();
    Assert.assertTrue((_expr_2 instanceof DependenceExpression));
    AlphaExpression _expr_3 = equation.getExpr();
    final DependenceExpression dependence = ((DependenceExpression) _expr_3);
    Assert.assertTrue(expectedFunction.isPlainEqual(dependence.getFunction()));
    AlphaExpression _expr_4 = dependence.getExpr();
    Assert.assertTrue((_expr_4 instanceof IndexExpression));
    AlphaExpression _expr_5 = dependence.getExpr();
    final IndexExpression index = ((IndexExpression) _expr_5);
    Assert.assertTrue(index.getFunction().isIdentity());
  }

  @Test
  public void nestedDependenceFunction_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("nestedDependenceFunction_01", "X");
    AlphaExpression _expr = equation.getExpr();
    final DependenceExpression originalDependence = ((DependenceExpression) _expr);
    final ISLMultiAff f1 = originalDependence.getFunction();
    AlphaExpression _expr_1 = originalDependence.getExpr();
    final ISLMultiAff f2 = ((DependenceExpression) _expr_1).getFunction();
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr_2 = equation.getExpr();
    Assert.assertTrue((_expr_2 instanceof DependenceExpression));
    AlphaExpression _expr_3 = equation.getExpr();
    final DependenceExpression topDependence = ((DependenceExpression) _expr_3);
    AlphaExpression _expr_4 = topDependence.getExpr();
    Assert.assertTrue((_expr_4 instanceof VariableExpression));
    final ISLMultiAff f = topDependence.getFunction();
    Assert.assertTrue(f.isPlainEqual(f2.pullback(f1)));
  }

  @Test
  public void restrictExpression_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("restrictExpression_01", "X");
    AlphaExpression _expr = equation.getExpr();
    final CaseExpression originalCase = ((CaseExpression) _expr);
    AlphaExpression _get = originalCase.getExprs().get(0);
    final RestrictExpression originalRestrict = ((RestrictExpression) _get);
    AlphaExpression _expr_1 = originalRestrict.getExpr();
    final DependenceExpression originalDependence = ((DependenceExpression) _expr_1);
    final ISLMultiAff expectedDependenceFunction = originalDependence.getFunction().copy();
    final ISLSet expectedRestrictDomain = originalRestrict.getRestrictDomain().copy().apply(expectedDependenceFunction.copy().toMap());
    RaiseDependenceTest.assertSingleRestrictionCorrect(equation, expectedRestrictDomain, expectedDependenceFunction);
  }

  @Test
  public void autoRestrictExpression_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("autoRestrictExpression_01", "X");
    AlphaExpression _expr = equation.getExpr();
    final CaseExpression originalCase = ((CaseExpression) _expr);
    AlphaExpression _get = originalCase.getExprs().get(0);
    final AutoRestrictExpression originalRestrict = ((AutoRestrictExpression) _get);
    AlphaExpression _expr_1 = originalRestrict.getExpr();
    final DependenceExpression originalDependence = ((DependenceExpression) _expr_1);
    final ISLMultiAff expectedDependenceFunction = originalDependence.getFunction().copy();
    final ISLSet expectedRestrictDomain = originalDependence.getExpr().getContextDomain().copy();
    RaiseDependenceTest.assertSingleRestrictionCorrect(equation, expectedRestrictDomain, expectedDependenceFunction);
  }

  public static void assertSingleRestrictionCorrect(final StandardEquation equation, final ISLSet expectedRestrictDomain, final ISLMultiAff expectedDependenceFunction) {
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr = equation.getExpr();
    Assert.assertTrue((_expr instanceof DependenceExpression));
    AlphaExpression _expr_1 = equation.getExpr();
    final DependenceExpression outerDependence = ((DependenceExpression) _expr_1);
    final ISLMultiAff f1 = outerDependence.getFunction();
    AlphaExpression _expr_2 = outerDependence.getExpr();
    Assert.assertTrue((_expr_2 instanceof CaseExpression));
    AlphaExpression _expr_3 = outerDependence.getExpr();
    final CaseExpression updatedCase = ((CaseExpression) _expr_3);
    Assert.assertEquals(1, updatedCase.getExprs().size());
    AlphaExpression _get = updatedCase.getExprs().get(0);
    Assert.assertTrue((_get instanceof DependenceExpression));
    AlphaExpression _get_1 = updatedCase.getExprs().get(0);
    final DependenceExpression innerDependence = ((DependenceExpression) _get_1);
    final ISLMultiAff f2 = innerDependence.getFunction();
    AlphaExpression _expr_4 = innerDependence.getExpr();
    final RestrictExpression updatedRestrict = ((RestrictExpression) _expr_4);
    Assert.assertTrue(updatedRestrict.getRestrictDomain().isEqual(expectedRestrictDomain));
    Assert.assertTrue(expectedDependenceFunction.isPlainEqual(f2.pullback(f1)));
  }

  @Test
  public void autoRestrictExpression_02() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("autoRestrictExpression_02", "X");
    AlphaExpression _expr = equation.getExpr();
    final CaseExpression originalCase = ((CaseExpression) _expr);
    AlphaExpression _get = originalCase.getExprs().get(0);
    final RestrictExpression originalRestrict1 = ((RestrictExpression) _get);
    AlphaExpression _expr_1 = originalRestrict1.getExpr();
    final DependenceExpression originalDependence1 = ((DependenceExpression) _expr_1);
    AlphaExpression _get_1 = originalCase.getExprs().get(1);
    final AutoRestrictExpression originalRestrict2 = ((AutoRestrictExpression) _get_1);
    AlphaExpression _expr_2 = originalRestrict2.getExpr();
    final DependenceExpression originalDependence2 = ((DependenceExpression) _expr_2);
    final ISLSet d1 = originalRestrict1.getRestrictDomain().copy();
    final ISLSet d2 = originalDependence2.getContextDomain().copy();
    final ISLMultiAff f1 = originalDependence1.getFunction().copy();
    final ISLMultiAff f2 = originalDependence2.getFunction().copy();
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr_3 = equation.getExpr();
    Assert.assertTrue((_expr_3 instanceof DependenceExpression));
    AlphaExpression _expr_4 = equation.getExpr();
    final DependenceExpression outerDependence = ((DependenceExpression) _expr_4);
    AlphaExpression _expr_5 = outerDependence.getExpr();
    Assert.assertTrue((_expr_5 instanceof CaseExpression));
    AlphaExpression _expr_6 = outerDependence.getExpr();
    final CaseExpression updatedCase = ((CaseExpression) _expr_6);
    Assert.assertEquals(2, updatedCase.getExprs().size());
    AlphaExpression _get_2 = updatedCase.getExprs().get(0);
    Assert.assertTrue((_get_2 instanceof DependenceExpression));
    AlphaExpression _get_3 = updatedCase.getExprs().get(1);
    Assert.assertTrue((_get_3 instanceof DependenceExpression));
    AlphaExpression _get_4 = updatedCase.getExprs().get(0);
    final DependenceExpression updatedDep1 = ((DependenceExpression) _get_4);
    AlphaExpression _get_5 = updatedCase.getExprs().get(1);
    final DependenceExpression updatedDep2 = ((DependenceExpression) _get_5);
    AlphaExpression _expr_7 = updatedDep1.getExpr();
    Assert.assertTrue((_expr_7 instanceof RestrictExpression));
    AlphaExpression _expr_8 = updatedDep1.getExpr();
    final RestrictExpression updatedRestrict1 = ((RestrictExpression) _expr_8);
    AlphaExpression _expr_9 = updatedDep2.getExpr();
    final RestrictExpression updatedRestrict2 = ((RestrictExpression) _expr_9);
    final ISLSet d1Prime = updatedRestrict1.getRestrictDomain();
    final ISLSet d2Prime = updatedRestrict2.getRestrictDomain();
    final ISLMultiAff fPrime = outerDependence.getFunction().copy();
    final ISLMultiAff f1Prime = updatedDep1.getFunction().copy();
    final ISLMultiAff f2Prime = updatedDep2.getFunction().copy();
    Assert.assertTrue(d1Prime.isEqual(d1.apply(f1.copy().toMap())));
    Assert.assertTrue(d2Prime.isEqual(d2.apply(f2.copy().toMap())));
    Assert.assertTrue(f1.isPlainEqual(f1Prime.pullback(fPrime.copy())));
    Assert.assertTrue(f2.isPlainEqual(f2Prime.pullback(fPrime)));
  }

  @Test
  public void unaryExpression_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("unaryExpression_01", "X");
    AlphaExpression _expr = equation.getExpr();
    final UnaryExpression originalUnaryExpr = ((UnaryExpression) _expr);
    AlphaExpression _expr_1 = originalUnaryExpr.getExpr();
    final ISLMultiAff f = ((DependenceExpression) _expr_1).getFunction();
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr_2 = equation.getExpr();
    Assert.assertTrue((_expr_2 instanceof DependenceExpression));
    AlphaExpression _expr_3 = equation.getExpr();
    final DependenceExpression newDependence = ((DependenceExpression) _expr_3);
    Assert.assertTrue(f.isPlainEqual(newDependence.getFunction()));
  }

  @Test
  public void simpleBinaryExpression_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("simpleBinaryExpression_01", "X");
    AlphaExpression _expr = equation.getExpr();
    final BinaryExpression originalBinaryExpr = ((BinaryExpression) _expr);
    AlphaExpression _left = originalBinaryExpr.getLeft();
    final ISLMultiAff f1 = ((DependenceExpression) _left).getFunction();
    AlphaExpression _right = originalBinaryExpr.getRight();
    final ISLMultiAff f2 = ((DependenceExpression) _right).getFunction();
    RaiseDependenceTest.assertBinaryEquationCorrect(equation, f1, f2);
  }

  @Test
  public void binaryExpressionBecomesNested_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("binaryExpressionBecomesNested_01", "X");
    AlphaExpression _expr = equation.getExpr();
    final DependenceExpression originalDependence = ((DependenceExpression) _expr);
    final ISLMultiAff f1 = originalDependence.getFunction();
    AlphaExpression _expr_1 = originalDependence.getExpr();
    final BinaryExpression originalBinaryExpr = ((BinaryExpression) _expr_1);
    AlphaExpression _left = originalBinaryExpr.getLeft();
    final ISLMultiAff f2 = ((DependenceExpression) _left).getFunction();
    AlphaExpression _right = originalBinaryExpr.getRight();
    final ISLMultiAff f3 = ((DependenceExpression) _right).getFunction();
    final ISLMultiAff firstExpected = f2.pullback(f1.copy());
    final ISLMultiAff secondExpected = f3.pullback(f1);
    RaiseDependenceTest.assertBinaryEquationCorrect(equation, firstExpected, secondExpected);
  }

  @Test
  public void binaryExpressionBecomesNested_02() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("binaryExpressionBecomesNested_02", "X");
    AlphaExpression _expr = equation.getExpr();
    final DependenceExpression originalDependence = ((DependenceExpression) _expr);
    final ISLMultiAff f1 = originalDependence.getFunction();
    AlphaExpression _expr_1 = originalDependence.getExpr();
    final BinaryExpression originalBinaryExpr = ((BinaryExpression) _expr_1);
    AlphaExpression _left = originalBinaryExpr.getLeft();
    final ISLMultiAff f2 = ((DependenceExpression) _left).getFunction();
    final ISLMultiAff firstExpected = f2.pullback(f1.copy());
    final ISLMultiAff secondExpected = f1;
    RaiseDependenceTest.assertBinaryEquationCorrect(equation, firstExpected, secondExpected);
  }

  @Test
  public void binaryExpressionBecomesNested_03() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("binaryExpressionBecomesNested_03", "X");
    AlphaExpression _expr = equation.getExpr();
    final DependenceExpression originalDependence = ((DependenceExpression) _expr);
    final ISLMultiAff f1 = originalDependence.getFunction();
    AlphaExpression _expr_1 = originalDependence.getExpr();
    final BinaryExpression originalBinaryExpr = ((BinaryExpression) _expr_1);
    AlphaExpression _right = originalBinaryExpr.getRight();
    final ISLMultiAff f3 = ((DependenceExpression) _right).getFunction();
    final ISLMultiAff firstExpected = f1.copy();
    final ISLMultiAff secondExpected = f3.pullback(f1);
    RaiseDependenceTest.assertBinaryEquationCorrect(equation, firstExpected, secondExpected);
  }

  @Test
  public void binaryExpressionBecomesNested_04() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("binaryExpressionBecomesNested_04", "X");
    AlphaExpression _expr = equation.getExpr();
    final DependenceExpression originalDependence = ((DependenceExpression) _expr);
    final ISLMultiAff f1 = originalDependence.getFunction();
    final ISLMultiAff firstExpected = f1.copy();
    final ISLMultiAff secondExpected = f1;
    RaiseDependenceTest.assertBinaryEquationCorrect(equation, firstExpected, secondExpected);
  }

  public static void assertBinaryEquationCorrect(final StandardEquation equation, final ISLMultiAff firstExpected, final ISLMultiAff secondExpected) {
    RaiseDependence.apply(equation.getExpr());
    AlphaExpression _expr = equation.getExpr();
    Assert.assertTrue((_expr instanceof DependenceExpression));
    AlphaExpression _expr_1 = equation.getExpr();
    final DependenceExpression topDependence = ((DependenceExpression) _expr_1);
    final ISLMultiAff f1Prime = topDependence.getFunction();
    AlphaExpression _expr_2 = topDependence.getExpr();
    Assert.assertTrue((_expr_2 instanceof BinaryExpression));
    AlphaExpression _expr_3 = topDependence.getExpr();
    final BinaryExpression binaryExpr = ((BinaryExpression) _expr_3);
    AlphaExpression _left = binaryExpr.getLeft();
    Assert.assertTrue((_left instanceof DependenceExpression));
    AlphaExpression _right = binaryExpr.getRight();
    Assert.assertTrue((_right instanceof DependenceExpression));
    AlphaExpression _left_1 = binaryExpr.getLeft();
    final ISLMultiAff f2Prime = ((DependenceExpression) _left_1).getFunction();
    AlphaExpression _right_1 = binaryExpr.getRight();
    final ISLMultiAff f3Prime = ((DependenceExpression) _right_1).getFunction();
    final ISLMultiAff firstActual = f2Prime.pullback(f1Prime.copy());
    Assert.assertTrue(firstExpected.isPlainEqual(firstActual));
    final ISLMultiAff secondActual = f3Prime.pullback(f1Prime);
    Assert.assertTrue(secondExpected.isPlainEqual(secondActual));
  }

  @Test
  public void multiArgTest_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("multiArgTest_01", "X");
    AlphaExpression _expr = equation.getExpr();
    final Function1<AlphaExpression, DependenceExpression> _function = (AlphaExpression expr) -> {
      return ((DependenceExpression) expr);
    };
    final Function1<DependenceExpression, AlphaExpression> _function_1 = (DependenceExpression expr) -> {
      return expr.getExpr();
    };
    final Function1<DependenceExpression, ISLMultiAff> _function_2 = (DependenceExpression expr) -> {
      return expr.getFunction();
    };
    final HashMap<AlphaExpression, ISLMultiAff> expectedFunctions = CommonExtensions.<AlphaExpression, ISLMultiAff>toHashMap(MapExtensions.<AlphaExpression, DependenceExpression, ISLMultiAff>mapValues(IterableExtensions.<AlphaExpression, DependenceExpression>toMap(ListExtensions.<AlphaExpression, DependenceExpression>map(((MultiArgExpression) _expr).getExprs(), _function), _function_1), _function_2));
    RaiseDependence.apply(equation.getExpr());
    Assert.assertTrue(DependenceExpression.class.isInstance(equation.getExpr()));
    AlphaExpression _expr_1 = equation.getExpr();
    final DependenceExpression topDependence = ((DependenceExpression) _expr_1);
    final ISLMultiAff commonFactor = topDependence.getFunction();
    Assert.assertTrue(MultiArgExpression.class.isInstance(topDependence.getExpr()));
    AlphaExpression _expr_2 = topDependence.getExpr();
    final MultiArgExpression multiArgExpr = ((MultiArgExpression) _expr_2);
    final Consumer<AlphaExpression> _function_3 = (AlphaExpression child) -> {
      Assert.assertTrue((child instanceof DependenceExpression));
    };
    multiArgExpr.getExprs().forEach(_function_3);
    final Function1<AlphaExpression, DependenceExpression> _function_4 = (AlphaExpression child) -> {
      return ((DependenceExpression) child);
    };
    final Function1<DependenceExpression, AlphaExpression> _function_5 = (DependenceExpression child) -> {
      return child.getExpr();
    };
    final Function1<DependenceExpression, ISLMultiAff> _function_6 = (DependenceExpression child) -> {
      return child.getFunction();
    };
    final HashMap<AlphaExpression, ISLMultiAff> remainingTerms = CommonExtensions.<AlphaExpression, ISLMultiAff>toHashMap(MapExtensions.<AlphaExpression, DependenceExpression, ISLMultiAff>mapValues(IterableExtensions.<AlphaExpression, DependenceExpression>toMap(ListExtensions.<AlphaExpression, DependenceExpression>map(multiArgExpr.getExprs(), _function_4), _function_5), _function_6));
    Set<AlphaExpression> _keySet = expectedFunctions.keySet();
    for (final AlphaExpression innerExpr : _keySet) {
      {
        final ISLMultiAff expected = expectedFunctions.get(innerExpr);
        final ISLMultiAff actual = remainingTerms.get(innerExpr).pullback(commonFactor.copy());
        Assert.assertTrue(expected.isPlainEqual(actual));
      }
    }
  }

  @Test
  public void caseTest_01() {
    final StandardEquation equation = RaiseDependenceTest.getEquation("caseTest_01", "X");
    AlphaExpression _expr = equation.getExpr();
    final Function1<AlphaExpression, DependenceExpression> _function = (AlphaExpression expr) -> {
      return ((DependenceExpression) expr);
    };
    final Function1<DependenceExpression, AlphaExpression> _function_1 = (DependenceExpression expr) -> {
      return expr.getExpr();
    };
    final Function1<DependenceExpression, ISLMultiAff> _function_2 = (DependenceExpression expr) -> {
      return expr.getFunction();
    };
    final HashMap<AlphaExpression, ISLMultiAff> expectedFunctions = CommonExtensions.<AlphaExpression, ISLMultiAff>toHashMap(MapExtensions.<AlphaExpression, DependenceExpression, ISLMultiAff>mapValues(IterableExtensions.<AlphaExpression, DependenceExpression>toMap(ListExtensions.<AlphaExpression, DependenceExpression>map(((CaseExpression) _expr).getExprs(), _function), _function_1), _function_2));
    RaiseDependence.apply(equation.getExpr());
    Assert.assertTrue(DependenceExpression.class.isInstance(equation.getExpr()));
    AlphaExpression _expr_1 = equation.getExpr();
    final DependenceExpression topDependence = ((DependenceExpression) _expr_1);
    final ISLMultiAff commonFactor = topDependence.getFunction();
    Assert.assertTrue(CaseExpression.class.isInstance(topDependence.getExpr()));
    AlphaExpression _expr_2 = topDependence.getExpr();
    final CaseExpression caseExpr = ((CaseExpression) _expr_2);
    final Consumer<AlphaExpression> _function_3 = (AlphaExpression child) -> {
      Assert.assertTrue((child instanceof DependenceExpression));
    };
    caseExpr.getExprs().forEach(_function_3);
    final Function1<AlphaExpression, DependenceExpression> _function_4 = (AlphaExpression child) -> {
      return ((DependenceExpression) child);
    };
    final Function1<DependenceExpression, AlphaExpression> _function_5 = (DependenceExpression child) -> {
      return child.getExpr();
    };
    final Function1<DependenceExpression, ISLMultiAff> _function_6 = (DependenceExpression child) -> {
      return child.getFunction();
    };
    final HashMap<AlphaExpression, ISLMultiAff> remainingTerms = CommonExtensions.<AlphaExpression, ISLMultiAff>toHashMap(MapExtensions.<AlphaExpression, DependenceExpression, ISLMultiAff>mapValues(IterableExtensions.<AlphaExpression, DependenceExpression>toMap(ListExtensions.<AlphaExpression, DependenceExpression>map(caseExpr.getExprs(), _function_4), _function_5), _function_6));
    Set<AlphaExpression> _keySet = expectedFunctions.keySet();
    for (final AlphaExpression innerExpr : _keySet) {
      {
        final ISLMultiAff expected = expectedFunctions.get(innerExpr);
        final ISLMultiAff actual = remainingTerms.get(innerExpr).pullback(commonFactor.copy());
        Assert.assertTrue(expected.isPlainEqual(actual));
      }
    }
  }
}
