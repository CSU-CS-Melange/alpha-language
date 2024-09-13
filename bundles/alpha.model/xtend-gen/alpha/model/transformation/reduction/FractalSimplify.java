package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.analysis.reduction.ReductionUtil;
import alpha.model.util.AShow;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Given two reductions whose bodies are homothetic scalings of each other, express the larger
 * reduction as a recursive subsystem of itself.
 * 
 * 1) define a new system
 */
@SuppressWarnings("all")
public class FractalSimplify {
  public static boolean DEBUG = true;

  private static void debug(final String msg) {
    if (FractalSimplify.DEBUG) {
      InputOutput.<String>println(("[FractalSimplify] " + msg));
    }
  }

  public static void apply(final AbstractReduceExpression smallerReduceExpr, final AbstractReduceExpression reduceExpr) {
    if (((!ReductionUtil.isSimilar(reduceExpr, smallerReduceExpr)) || (ISLUtil.dimensionality(reduceExpr.getBody().getContextDomain()) != 2))) {
      return;
    }
    FractalSimplify.transform(reduceExpr, smallerReduceExpr);
  }

  /**
   * Assumes that the two reduce expressions are a homothetic scaling of eachother
   */
  public static void transform(final AbstractReduceExpression smallerReduceExpr, final AbstractReduceExpression reduceExpr) {
    try {
      FractalSimplify.debug("asdf");
      String _print = AShow.print(AlphaUtil.getContainerSystem(smallerReduceExpr));
      String _plus = ("smallerReduceExpr:\n" + _print);
      FractalSimplify.debug(_plus);
      String _print_1 = AShow.print(AlphaUtil.getContainerSystem(reduceExpr));
      String _plus_1 = ("reduceExpr:\n" + _print_1);
      FractalSimplify.debug(_plus_1);
      throw new Exception("fractal transform not yet implemented");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
