package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.analysis.reduction.ReductionUtil;
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
      throw new Exception("fractal transform not yet implemented");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
