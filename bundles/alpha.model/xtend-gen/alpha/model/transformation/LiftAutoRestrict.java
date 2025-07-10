package alpha.model.transformation;

import alpha.model.AlphaCompleteVisitable;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaVisitable;
import alpha.model.AutoRestrictExpression;
import alpha.model.RestrictExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Replaces AutoRestrictExpression with RestrictExpression
 * containing the inferred domain.
 */
@SuppressWarnings("all")
public class LiftAutoRestrict extends AbstractAlphaCompleteVisitor {
  /**
   * Lifts the target AutoRestictExpression
   */
  public static RestrictExpression apply(final AutoRestrictExpression are) {
    return LiftAutoRestrict.transform(are);
  }

  /**
   * Lifts any AutoRestrictExpression in the sub-tree
   * starting from the given node.
   */
  public static void apply(final AlphaCompleteVisitable acv) {
    final LiftAutoRestrict visitor = new LiftAutoRestrict();
    if ((acv instanceof AlphaVisitable)) {
      visitor.accept(((AlphaVisitable) acv));
    }
    if ((acv instanceof AlphaExpressionVisitable)) {
      visitor.accept(((AlphaExpressionVisitable) acv));
    }
  }

  private static RestrictExpression transform(final AutoRestrictExpression are) {
    final RestrictExpression re = AlphaUserFactory.createRestrictExpression(are.getInferredDomain(), are.getExpr());
    re.setZ__internal_cache_exprDom(are.getExpressionDomain());
    re.setZ__internal_cache_contextDom(are.getContextDomain());
    EcoreUtil2.replace(are, re);
    return re;
  }

  public void outAutoRestrictExpression(final AutoRestrictExpression are) {
    LiftAutoRestrict.transform(are);
  }
}
