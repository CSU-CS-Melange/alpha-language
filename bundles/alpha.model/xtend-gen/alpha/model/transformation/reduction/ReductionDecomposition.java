package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.issue.AlphaIssue;
import fr.irisa.cairn.jnimap.isl.ISLErrorException;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.JNIISLTools;
import java.util.List;
import java.util.function.Supplier;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * ReductionDecomposition splits a reduction into two.
 * A reduction of the form:
 *   reduce(op, f, E)
 * is transformed into:
 *   reduce(op, f2, reduce(op, f1, E))
 * where f = f2 o f1.
 */
@SuppressWarnings("all")
public class ReductionDecomposition {
  private ReductionDecomposition() {
  }

  /**
   * Applies ReductionDecomposition using the input functions.
   * Input f1 becomes the projection function of the inner reduction.
   */
  public static List<AlphaIssue> apply(final AbstractReduceExpression are, final ISLMultiAff f1, final ISLMultiAff f2) {
    List<AlphaIssue> _xblockexpression = null;
    {
      Boolean _xtrycatchfinallyexpression = null;
      try {
        final Supplier<Boolean> _function = new Supplier<Boolean>() {
          public Boolean get() {
            return Boolean.valueOf(f2.copy().pullback(f1.copy()).isPlainEqual(are.getProjection()));
          }
        };
        _xtrycatchfinallyexpression = JNIISLTools.<Boolean>recordError(_function);
      } catch (final Throwable _t) {
        if (_t instanceof ISLErrorException) {
          final ISLErrorException isle = (ISLErrorException)_t;
          throw new IllegalArgumentException(("[ReductionDecomposition] ISL error while testing for validity of input functions: " + isle.islErrorMessage));
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      final Boolean validDecomposition = _xtrycatchfinallyexpression;
      if ((!(validDecomposition).booleanValue())) {
        throw new IllegalArgumentException("[ReductionDecomposition] The composition of input functions must match the original projection. ");
      }
      final AbstractReduceExpression innerARE = ReductionUtil.constructConcreteReduction(are, are.getOperator(), f1, are.getBody());
      are.setBody(innerARE);
      are.setProjectionExpr(AlphaUserFactory.createJNIFunction(f2));
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(innerARE);
    }
    return _xblockexpression;
  }
}
