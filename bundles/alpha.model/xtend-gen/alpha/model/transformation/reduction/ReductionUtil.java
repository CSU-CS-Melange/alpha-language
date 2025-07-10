package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.ArgReduceExpression;
import alpha.model.DependenceExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AffineFunctionOperations;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Arrays;
import java.util.function.Consumer;
import org.eclipse.xtext.EcoreUtil2;

@SuppressWarnings("all")
public class ReductionUtil {
  /**
   * Dispatch methods for constructing reductions with the appropriate concrete type.
   */
  protected static AbstractReduceExpression _constructConcreteReduction(final ReduceExpression base, final REDUCTION_OP op, final ISLMultiAff projection, final AlphaExpression body) {
    return AlphaUserFactory.createReduceExpression(op, projection, body);
  }

  protected static AbstractReduceExpression _constructConcreteReduction(final ArgReduceExpression base, final REDUCTION_OP op, final ISLMultiAff projection, final AlphaExpression body) {
    return AlphaUserFactory.createArgReduceExpression(op, projection, body);
  }

  /**
   * Takes a reduction and a function that have overlapping nullspace as the reduction body
   * and projects the entire reduction by the given function.
   * 
   * Given a function Fc
   *   - reduce(op, f, E)
   * where ker(Fc) \in reuse(E), returns
   *   - reduce(op, f', E')
   * where
   *   - f = f' o Fc
   *   - E' = E with all domains and (domain of) dependences projected by Fc.
   * 
   * Assumes that input is normalized, and Fc satisfies the requirement.
   */
  public static void projectReductionBody(final AbstractReduceExpression are, final ISLMultiAff Fc) {
    final Consumer<DependenceExpression> _function = new Consumer<DependenceExpression>() {
      public void accept(final DependenceExpression de) {
        final ISLMultiAff projectedDep = AffineFunctionOperations.projectFunctionDomain(de.getFunction(), Fc.copy());
        final DependenceExpression newDepExpr = AlphaUserFactory.createDependenceExpression(projectedDep, de.getExpr());
        EcoreUtil2.replace(de, newDepExpr);
      }
    };
    EcoreUtil2.<DependenceExpression>getAllContentsOfType(are, DependenceExpression.class).forEach(_function);
    final Consumer<RestrictExpression> _function_1 = new Consumer<RestrictExpression>() {
      public void accept(final RestrictExpression re) {
        final ISLSet projectedDom = re.getRestrictDomain().apply(Fc.copy().toMap());
        final RestrictExpression newRestrictExpr = AlphaUserFactory.createRestrictExpression(projectedDom, re.getExpr());
        EcoreUtil2.replace(re, newRestrictExpr);
      }
    };
    EcoreUtil2.<RestrictExpression>getAllContentsOfType(are, RestrictExpression.class).forEach(_function_1);
  }

  public static AbstractReduceExpression constructConcreteReduction(final AbstractReduceExpression base, final REDUCTION_OP op, final ISLMultiAff projection, final AlphaExpression body) {
    if (base instanceof ArgReduceExpression) {
      return _constructConcreteReduction((ArgReduceExpression)base, op, projection, body);
    } else if (base instanceof ReduceExpression) {
      return _constructConcreteReduction((ReduceExpression)base, op, projection, body);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(base, op, projection, body).toString());
    }
  }
}
