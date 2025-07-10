package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.DependenceExpression;
import alpha.model.MultiArgExpression;
import alpha.model.RestrictExpression;
import alpha.model.util.AlphaOperatorUtil;
import alpha.model.util.ModelSwitch;
import com.google.common.base.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Automatically applies FactorOutFromReduction whenever it applies.
 * 
 * The transformation takes a reduction, and performs a depth-first
 * traversal to locate a candidate expression to hoist out. There can
 * only be RestrictExpression/BinaryExpression/MultiArgExpression,
 * along the path to a DependenceExpression that would be factored out.
 * 
 * Once an expression is factored out, the same algorithm is applied
 * to the residual reductions until convergence.
 */
@SuppressWarnings("all")
public class Distributivity {
  private static class TargetIdentifier extends ModelSwitch<AlphaExpression> {
    private AbstractReduceExpression reduction = null;

    private BINARY_OP enclosingOperatorOP = null;

    /**
     * The Switch is used to find the DependenceExpression enclosed in a compatible
     * BinaryExpression or MultiArgExpression.
     * 
     * Expect the enclosing reduction to be on the path (either the first
     * expression or after restrict). Silently ignores all expressions
     * that cannot be verified to be a legal target for FactorOutOfReduction.
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
    public AlphaExpression caseDependenceExpression(final DependenceExpression object) {
      DependenceExpression _xblockexpression = null;
      {
        if (((this.reduction == null) || (this.enclosingOperatorOP == null))) {
          return null;
        }
        try {
          FactorOutFromReduction.testLegality(this.reduction, this.enclosingOperatorOP, object);
        } catch (final Throwable _t) {
          if (_t instanceof IllegalArgumentException) {
            return null;
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
        _xblockexpression = object;
      }
      return _xblockexpression;
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
      boolean _isDistributiveOver = AlphaOperatorUtil.isDistributiveOver(object.getOperator(), this.reduction.getOperator());
      boolean _not = (!_isDistributiveOver);
      if (_not) {
        return null;
      }
      if ((this.enclosingOperatorOP == null)) {
        this.enclosingOperatorOP = object.getOperator();
      } else {
        BINARY_OP _operator = object.getOperator();
        boolean _notEquals = (!Objects.equal(this.enclosingOperatorOP, _operator));
        if (_notEquals) {
          return null;
        }
      }
      final AlphaExpression leftRes = this.doSwitch(object.getLeft());
      if ((leftRes != null)) {
        return leftRes;
      }
      return this.doSwitch(object.getRight());
    }

    /**
     * override
     */
    public AlphaExpression caseMultiArgExpression(final MultiArgExpression object) {
      if ((this.reduction == null)) {
        return null;
      }
      boolean _isDistributiveOver = AlphaOperatorUtil.isDistributiveOver(object.getOperator(), this.reduction.getOperator());
      boolean _not = (!_isDistributiveOver);
      if (_not) {
        return null;
      }
      if ((this.enclosingOperatorOP == null)) {
        this.enclosingOperatorOP = AlphaOperatorUtil.reductionOPtoBinaryOP(object.getOperator());
      } else {
        BINARY_OP _reductionOPtoBinaryOP = AlphaOperatorUtil.reductionOPtoBinaryOP(object.getOperator());
        boolean _notEquals = (!Objects.equal(this.enclosingOperatorOP, _reductionOPtoBinaryOP));
        if (_notEquals) {
          return null;
        }
      }
      EList<AlphaExpression> _exprs = object.getExprs();
      for (final AlphaExpression expr : _exprs) {
        {
          final AlphaExpression res = this.doSwitch(expr);
          if ((res != null)) {
            return res;
          }
        }
      }
      return null;
    }
  }

  private Distributivity() {
  }

  /**
   * Applies Distributivity to the specified expression.
   * 
   * Returns the number of times FactorOutFromReduction was applied.
   */
  public static int apply(final AbstractReduceExpression targetReduce) {
    final AlphaExpression target = new Distributivity.TargetIdentifier().doSwitch(targetReduce);
    if ((target == null)) {
      return 0;
    }
    final BinaryExpression binExpr = FactorOutFromReduction.apply(((DependenceExpression) target));
    AlphaExpression _right = binExpr.getRight();
    final AbstractReduceExpression nextReduce = ((AbstractReduceExpression) _right);
    int _apply = Distributivity.apply(nextReduce);
    return (1 + _apply);
  }
}
