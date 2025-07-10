package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.REDUCTION_OP;
import alpha.model.SystemBody;
import alpha.model.factory.AlphaUserFactory;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * ReductionComposition fuses two AbstractReduceExpressions.
 * 
 * The transformation takes a chain of reductions of the form:
 *   reduce(op, g, reduce(op, f, E))
 * and transforms it into:
 *   reduce(op, g o f, E)
 * 
 * Note that the operator must be the same for both of the reductions.
 */
@SuppressWarnings("all")
public class ReductionComposition {
  private ReductionComposition() {
  }

  /**
   * Apply the transformation to all AbstractReduceExpressions in an AlphaSystem.
   * Silently ignores any AbstractReduceExpression where composition is not applicable.
   * 
   * @returns number of applications of the transformation
   */
  public static Integer apply(final AlphaSystem system) {
    final Function1<SystemBody, Integer> _function = new Function1<SystemBody, Integer>() {
      public Integer apply(final SystemBody b) {
        return ReductionComposition.apply(b);
      }
    };
    final Function2<Integer, Integer, Integer> _function_1 = new Function2<Integer, Integer, Integer>() {
      public Integer apply(final Integer p1, final Integer p2) {
        return Integer.valueOf(((p1).intValue() + (p2).intValue()));
      }
    };
    return IterableExtensions.<Integer>reduce(ListExtensions.<SystemBody, Integer>map(system.getSystemBodies(), _function), _function_1);
  }

  /**
   * Apply the transformation to all AbstractReduceExpressions in a SystemBody.
   * Silently ignores any AbstractReduceExpression where composition is not applicable.
   * 
   * @returns number of applications of the transformation
   */
  public static Integer apply(final SystemBody body) {
    final Function1<AbstractReduceExpression, Integer> _function = new Function1<AbstractReduceExpression, Integer>() {
      public Integer apply(final AbstractReduceExpression are) {
        return Integer.valueOf(ReductionComposition.transform(are));
      }
    };
    final Function2<Integer, Integer, Integer> _function_1 = new Function2<Integer, Integer, Integer>() {
      public Integer apply(final Integer p1, final Integer p2) {
        return Integer.valueOf(((p1).intValue() + (p2).intValue()));
      }
    };
    return IterableExtensions.<Integer>reduce(ListExtensions.<AbstractReduceExpression, Integer>map(EcoreUtil2.<AbstractReduceExpression>getAllContentsOfType(body, AbstractReduceExpression.class), _function), _function_1);
  }

  /**
   * Composes two AbstractReduceExpressions into one.
   * This method throws an exception when composition is not applicable to the specified expression.
   */
  public static void apply(final AbstractReduceExpression are) {
    int _transform = ReductionComposition.transform(are);
    boolean _notEquals = (_transform != 1);
    if (_notEquals) {
      throw new IllegalArgumentException("[ReductionComposition] Target AbstractReduceExpression must have another reduction with the same operator as its body.");
    }
  }

  /**
   * Implementation of the transformation.
   */
  private static int transform(final AbstractReduceExpression are) {
    boolean _testLegality = ReductionComposition.testLegality(are);
    boolean _not = (!_testLegality);
    if (_not) {
      return 0;
    }
    AlphaExpression _body = are.getBody();
    final AbstractReduceExpression innerARE = ((AbstractReduceExpression) _body);
    final ISLMultiAff composedProj = are.getProjection().pullback(innerARE.getProjection());
    are.setProjectionExpr(AlphaUserFactory.createJNIFunction(composedProj));
    are.setBody(innerARE.getBody());
    return 1;
  }

  /**
   * Tests if ReductionComposition is applicable.
   * Exposed to be used by SimplifyReductionExploration.
   */
  public static boolean testLegality(final AbstractReduceExpression are) {
    AlphaExpression _body = are.getBody();
    boolean _not = (!(_body instanceof AbstractReduceExpression));
    if (_not) {
      return false;
    }
    AlphaExpression _body_1 = are.getBody();
    final AbstractReduceExpression innerARE = ((AbstractReduceExpression) _body_1);
    REDUCTION_OP _operator = are.getOperator();
    REDUCTION_OP _operator_1 = innerARE.getOperator();
    boolean _notEquals = (!Objects.equal(_operator, _operator_1));
    if (_notEquals) {
      return false;
    }
    return true;
  }
}
