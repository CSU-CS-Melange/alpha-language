package alpha.model.transformation;

import alpha.model.AlphaSystem;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class SubstituteAll {
  public static void apply(final AlphaSystem system, final boolean substituteReductions) {
    try {
      int _size = system.getSystemBodies().size();
      boolean _notEquals = (_size != 1);
      if (_notEquals) {
        throw new Exception("Only systems with 1 body are supported.");
      }
      final SystemBody systemBody = system.getSystemBodies().get(0);
      while ((SubstituteAll.getNextSubstitute(systemBody, substituteReductions) != null)) {
        {
          StandardEquation toSubstitute = SubstituteAll.getNextSubstitute(systemBody, substituteReductions);
          SubstituteByDef.apply(systemBody, toSubstitute.getVariable());
          RemoveUnusedEquations.apply(system);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static StandardEquation getNextSubstitute(final SystemBody body, final boolean substituteReductions) {
    final Function1<StandardEquation, Boolean> _function = (StandardEquation it) -> {
      return SubstituteAll.isLocalEquation(it);
    };
    final Function1<StandardEquation, Boolean> _function_1 = (StandardEquation it) -> {
      return Boolean.valueOf(SubstituteAll.isSelfReferential(it));
    };
    final Function1<StandardEquation, Boolean> _function_2 = (StandardEquation it) -> {
      return Boolean.valueOf(SubstituteAll.isReduction(it, substituteReductions));
    };
    return IterableExtensions.<StandardEquation>head(IterableExtensions.<StandardEquation>reject(IterableExtensions.<StandardEquation>reject(IterableExtensions.<StandardEquation>filter(Iterables.<StandardEquation>filter(body.getEquations(), StandardEquation.class), _function), _function_1), _function_2));
  }

  public static Boolean isLocalEquation(final StandardEquation eq) {
    return eq.getVariable().isLocal();
  }

  public static boolean isSelfReferential(final StandardEquation eq) {
    final Function1<VariableExpression, Variable> _function = (VariableExpression it) -> {
      return it.getVariable();
    };
    final Function1<Variable, Boolean> _function_1 = (Variable it) -> {
      return Boolean.valueOf(it.getName().equals(eq.getVariable().getName()));
    };
    return IteratorExtensions.<Variable>exists(IteratorExtensions.<VariableExpression, Variable>map(Iterators.<VariableExpression>filter(eq.getExpr().eAllContents(), VariableExpression.class), _function), _function_1);
  }

  public static boolean isReduction(final StandardEquation eq, final boolean substituteReductions) {
    return ((!substituteReductions) && (eq.getExpr() instanceof ReduceExpression));
  }
}
