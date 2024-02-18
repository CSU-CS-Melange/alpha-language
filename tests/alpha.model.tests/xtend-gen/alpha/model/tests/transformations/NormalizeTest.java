package alpha.model.tests.transformations;

import alpha.commands.Utility;
import alpha.commands.UtilityBase;
import alpha.model.AlphaExpression;
import alpha.model.AlphaModelLoader;
import alpha.model.CaseExpression;
import alpha.model.StandardEquation;
import alpha.model.transformation.Normalize;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class NormalizeTest {
  @Test
  public void pullRestrictThenFlattenCase() {
    try {
      final String filePath = "resources/src-valid/transformation-tests/normalize/pullRestrictThenFlattenCase.alpha";
      final StandardEquation equation = UtilityBase.GetEquation(Utility.GetSystemBody(AlphaModelLoader.loadModel(filePath).getSystem("testCase")), "Y");
      Normalize.apply(equation);
      AlphaExpression _expr = equation.getExpr();
      Assert.assertTrue((_expr instanceof CaseExpression));
      AlphaExpression _expr_1 = equation.getExpr();
      final CaseExpression caseExpr = ((CaseExpression) _expr_1);
      Assert.assertEquals(3, caseExpr.getExprs().size());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
