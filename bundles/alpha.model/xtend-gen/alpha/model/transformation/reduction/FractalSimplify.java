package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.analysis.reduction.ReductionUtil;
import alpha.model.transformation.RemoveUnusedEquations;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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
   * these reduce exprs belong to different systems
   */
  public static void transform(final AbstractReduceExpression smallerReduceExpr, final AbstractReduceExpression reduceExpr) {
    try {
      final AbstractReduceExpression fractalReduceExpr = AlphaUtil.<AbstractReduceExpression>copyAE(reduceExpr);
      fractalReduceExpr.setFractalSimplify(Boolean.valueOf(true));
      final String variableName = AlphaUtil.getContainerEquation(reduceExpr).getName();
      final SystemBody targetSystemBody = AlphaUtil.getContainerSystemBody(smallerReduceExpr);
      final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
        String _name = eq.getVariable().getName();
        return Boolean.valueOf(Objects.equal(_name, variableName));
      };
      final StandardEquation equationInTargetSystemBody = IterableExtensions.<StandardEquation>findFirst(targetSystemBody.getStandardEquations(), _function);
      if ((equationInTargetSystemBody == null)) {
        throw new Exception("Failed to fractal transform, could not identify original variable");
      }
      final AlphaExpression targetReduceExpr = equationInTargetSystemBody.getExpr();
      EcoreUtil.replace(targetReduceExpr, fractalReduceExpr);
      AlphaInternalStateConstructor.recomputeContextDomain(equationInTargetSystemBody);
      RemoveUnusedEquations.apply(targetSystemBody.getSystem());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
