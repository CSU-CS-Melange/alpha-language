package alpha.model.transformation;

import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.IndexExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.VariableExpression;
import java.util.Arrays;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * This transformation implements a form of constant propagation in Alpha.
 * Equations that match the following:
 *   X = SimpleExpression
 * where
 *   SimpleExpression := SimpleExpression |
 *                       VariableExpression | ConstantExpression | IndexExpression
 *                       RestrictExpression : SimpleExpression |
 *                       DependenceExpression @ SimpleExpression
 * are all considered to be simple and are inlined.
 * 
 * Inlined VariableExpressions are wrapped by a RestrictExpression that restricts
 * the domain to the domain of the inlined Variable.
 * 
 * This transformation also calls {@link RemoveUnusedEquations} to remove the inlined equations.
 * 
 * It is recommended to call {@link Normalize} and {@link SimplifyExpressions}
 * before using this transformation to maximize effectiveness.
 */
@SuppressWarnings("all")
public class PropagateSimpleEquations {
  private PropagateSimpleEquations() {
  }

  public static void apply(final AlphaSystem system) {
    final Consumer<SystemBody> _function = new Consumer<SystemBody>() {
      public void accept(final SystemBody body) {
        PropagateSimpleEquations.apply(body);
      }
    };
    system.getSystemBodies().forEach(_function);
  }

  public static void apply(final SystemBody body) {
    final Function1<StandardEquation, Boolean> _function = new Function1<StandardEquation, Boolean>() {
      public Boolean apply(final StandardEquation eq) {
        return Boolean.valueOf(PropagateSimpleEquations.isSimple(eq.getExpr()));
      }
    };
    final Iterable<StandardEquation> simpleEquations = IterableExtensions.<StandardEquation>filter(body.getStandardEquations(), _function);
    if ((simpleEquations != null)) {
      final Consumer<StandardEquation> _function_1 = new Consumer<StandardEquation>() {
        public void accept(final StandardEquation seq) {
          SubstituteByDef.apply(body, seq.getVariable());
        }
      };
      simpleEquations.forEach(_function_1);
      RemoveUnusedEquations.apply(body.getSystem());
    }
  }

  private static boolean _isSimple(final AlphaExpression expr) {
    return false;
  }

  private static boolean _isSimple(final VariableExpression expr) {
    return true;
  }

  private static boolean _isSimple(final ConstantExpression expr) {
    return true;
  }

  private static boolean _isSimple(final IndexExpression expr) {
    return true;
  }

  private static boolean _isSimple(final RestrictExpression expr) {
    return PropagateSimpleEquations.isSimple(expr.getExpr());
  }

  private static boolean _isSimple(final DependenceExpression expr) {
    return PropagateSimpleEquations.isSimple(expr.getExpr());
  }

  private static boolean isSimple(final AlphaExpression expr) {
    if (expr instanceof ConstantExpression) {
      return _isSimple((ConstantExpression)expr);
    } else if (expr instanceof DependenceExpression) {
      return _isSimple((DependenceExpression)expr);
    } else if (expr instanceof IndexExpression) {
      return _isSimple((IndexExpression)expr);
    } else if (expr instanceof RestrictExpression) {
      return _isSimple((RestrictExpression)expr);
    } else if (expr instanceof VariableExpression) {
      return _isSimple((VariableExpression)expr);
    } else if (expr != null) {
      return _isSimple(expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
}
