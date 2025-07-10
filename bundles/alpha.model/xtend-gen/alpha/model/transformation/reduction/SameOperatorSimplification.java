package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.MultiArgExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.RestrictExpression;
import alpha.model.util.AlphaOperatorUtil;
import alpha.model.util.ModelSwitch;
import com.google.common.base.Objects;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Automatically applies HoistOutOfReduction whenever it applies.
 * 
 * The transformation takes a reduction, and performs a depth-first
 * traversal to locate a candidate expression to hoist out. There can
 * only be RestrictExpressions from the reduction to BinaryExpression
 * or MultiArgExpression, and the traversal stops when another type
 * is encountered.
 * 
 * Once an expression is hoisted out, the same algorithm is applied
 * to the residual reductions until convergence.
 */
@SuppressWarnings("all")
public class SameOperatorSimplification {
  private static class TargetIdentifier extends ModelSwitch<AlphaExpression> {
    private AbstractReduceExpression reduction;

    /**
     * The Switch is used to find the BinaryExpression or MultiArgExpression
     * immediately enclosed by the reduction, only allowing RestrictExpression
     * to be on the path.
     * 
     * Expect the enclosing reduction to be on the path (either the first
     * expression or after restrict). Silently ignores all expressions
     * that cannot be verified to be a legal target for HoistOutOfReduction.
     */
    public AlphaExpression defaultCase(final EObject eObject) {
      return null;
    }

    public AlphaExpression caseAbstractReduceExpression(final AbstractReduceExpression object) {
      AlphaExpression _xifexpression = null;
      if ((this.reduction == null)) {
        AlphaExpression _xblockexpression = null;
        {
          this.reduction = object;
          _xblockexpression = this.doSwitch(object.getBody());
        }
        _xifexpression = _xblockexpression;
      } else {
        _xifexpression = null;
      }
      return _xifexpression;
    }

    /**
     * override
     */
    public AlphaExpression caseRestrictExpression(final RestrictExpression object) {
      return this.doSwitch(object.getExpr());
    }

    /**
     * override
     */
    public AlphaExpression caseBinaryExpression(final BinaryExpression object) {
      if ((this.reduction == null)) {
        return null;
      }
      BINARY_OP _reductionOPtoBinaryOP = AlphaOperatorUtil.reductionOPtoBinaryOP(this.reduction.getOperator());
      BINARY_OP _operator = object.getOperator();
      boolean _notEquals = (!Objects.equal(_reductionOPtoBinaryOP, _operator));
      if (_notEquals) {
        return null;
      }
      return object;
    }

    /**
     * override
     */
    public AlphaExpression caseMultiArgExpression(final MultiArgExpression object) {
      if ((this.reduction == null)) {
        return null;
      }
      REDUCTION_OP _operator = this.reduction.getOperator();
      REDUCTION_OP _operator_1 = object.getOperator();
      boolean _notEquals = (!Objects.equal(_operator, _operator_1));
      if (_notEquals) {
        return null;
      }
      return object;
    }
  }

  /**
   * Applies SameOperatorSimplification to the specified expression.
   * 
   * Returns the number of times HoistOutOfReduction was applied.
   */
  public static int apply(final AbstractReduceExpression targetReduce) {
    final SameOperatorSimplification SOS = new SameOperatorSimplification();
    return SOS.transform(targetReduce);
  }

  private int transform(final AbstractReduceExpression are) {
    final AlphaExpression targetExpr = new SameOperatorSimplification.TargetIdentifier().doSwitch(are);
    if ((targetExpr == null)) {
      return 0;
    }
    return this.applyHoisting(targetExpr);
  }

  private int _applyHoisting(final BinaryExpression bexpr) {
    final BinaryExpression hoistedExpr = HoistOutOfReduction.apply(bexpr);
    final AlphaExpression targetLeft = new SameOperatorSimplification.TargetIdentifier().doSwitch(hoistedExpr.getLeft());
    final AlphaExpression targetRight = new SameOperatorSimplification.TargetIdentifier().doSwitch(hoistedExpr.getRight());
    int _xifexpression = (int) 0;
    if ((targetLeft != null)) {
      _xifexpression = this.applyHoisting(targetLeft);
    } else {
      _xifexpression = 0;
    }
    final int leftCount = _xifexpression;
    int _xifexpression_1 = (int) 0;
    if ((targetRight != null)) {
      _xifexpression_1 = this.applyHoisting(targetRight);
    } else {
      _xifexpression_1 = 0;
    }
    final int rightCount = _xifexpression_1;
    return (leftCount + rightCount);
  }

  private int _applyHoisting(final MultiArgExpression mae) {
    final MultiArgExpression hoistedExpr = HoistOutOfReduction.apply(mae);
    final List<AlphaExpression> exprs = IterableExtensions.<AlphaExpression>toList(hoistedExpr.getExprs());
    int count = 0;
    for (final AlphaExpression expr : exprs) {
      {
        final AlphaExpression target = new SameOperatorSimplification.TargetIdentifier().doSwitch(expr);
        int _count = count;
        int _xifexpression = (int) 0;
        if ((target != null)) {
          _xifexpression = this.applyHoisting(target);
        } else {
          _xifexpression = 0;
        }
        count = (_count + _xifexpression);
      }
    }
    return count;
  }

  private int applyHoisting(final AlphaExpression bexpr) {
    if (bexpr instanceof BinaryExpression) {
      return _applyHoisting((BinaryExpression)bexpr);
    } else if (bexpr instanceof MultiArgExpression) {
      return _applyHoisting((MultiArgExpression)bexpr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(bexpr).toString());
    }
  }
}
