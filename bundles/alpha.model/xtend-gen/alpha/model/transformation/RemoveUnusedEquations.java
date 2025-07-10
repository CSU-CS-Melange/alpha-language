package alpha.model.transformation;

import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.Equation;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.ArrayExtensions;
import org.eclipse.xtext.xbase.lib.CollectionExtensions;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Removes local equations/variables that are defined
 * but are never used in a system.
 */
@SuppressWarnings("all")
public class RemoveUnusedEquations {
  private RemoveUnusedEquations() {
  }

  public static void apply(final AlphaRoot root) {
    final Consumer<AlphaSystem> _function = new Consumer<AlphaSystem>() {
      public void accept(final AlphaSystem s) {
        RemoveUnusedEquations.apply(s);
      }
    };
    root.getSystems().forEach(_function);
  }

  public static void apply(final AlphaSystem system) {
    final HashMap<Variable, Boolean> map = new HashMap<Variable, Boolean>();
    final Function1<VariableExpression, Boolean> _function = new Function1<VariableExpression, Boolean>() {
      public Boolean apply(final VariableExpression ve) {
        return ve.getVariable().isLocal();
      }
    };
    final Consumer<VariableExpression> _function_1 = new Consumer<VariableExpression>() {
      public void accept(final VariableExpression ve) {
        map.put(ve.getVariable(), Boolean.valueOf(true));
      }
    };
    IterableExtensions.<VariableExpression>filter(EcoreUtil2.<VariableExpression>getAllContentsOfType(system, VariableExpression.class), _function).forEach(_function_1);
    final Predicate<Variable> _function_2 = new Predicate<Variable>() {
      public boolean test(final Variable v) {
        boolean _containsKey = map.containsKey(v);
        return (!_containsKey);
      }
    };
    final Object[] unusedVars = system.getLocals().stream().filter(_function_2).toArray();
    final Function1<StandardEquation, Boolean> _function_3 = new Function1<StandardEquation, Boolean>() {
      public Boolean apply(final StandardEquation eq) {
        return Boolean.valueOf(ArrayExtensions.contains(unusedVars, eq.getVariable()));
      }
    };
    final Iterable<StandardEquation> unusedEqs = IterableExtensions.<StandardEquation>filter(EcoreUtil2.<StandardEquation>getAllContentsOfType(system, StandardEquation.class), _function_3);
    system.getLocals().removeAll(((Collection<?>)Conversions.doWrapArray(unusedVars)));
    final Consumer<SystemBody> _function_4 = new Consumer<SystemBody>() {
      public void accept(final SystemBody body) {
        CollectionExtensions.<Equation>removeAll(body.getEquations(), unusedEqs);
      }
    };
    system.getSystemBodies().forEach(_function_4);
  }
}
