package alpha.model.transformation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaSystem;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.transformation.reduction.NormalizeReduction;
import alpha.model.transformation.reduction.PermutationCaseReduce;
import alpha.model.util.AlphaUtil;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Splits any reductions whose bodies are not convex into cases of reductions
 * with convex bodies.
 */
@SuppressWarnings("all")
public class SplitReduction {
  public static int counter = 0;

  public static Object apply(final AlphaSystem system) {
    final Consumer<SystemBody> _function = (SystemBody it) -> {
      SplitReduction.apply(it);
    };
    system.getSystemBodies().forEach(_function);
    return null;
  }

  public static void apply(final SystemBody body) {
    SplitReduction.applyAndReport(body);
  }

  public static void applyAndReport(final SystemBody body) {
    SplitReduction.transform(body);
  }

  public static List<AbstractReduceExpression> applyAndReport(final ReduceExpression re) {
    return SplitReduction.transform(re);
  }

  private static void transform(final SystemBody body) {
    int _counter = SplitReduction.counter;
    SplitReduction.counter = (_counter + 1);
    final AlphaSystem system = AlphaUtil.getContainerSystem(body);
    NormalizeReduction.apply(body);
    final ArrayList<StandardEquation> nonConvexEqus = new ArrayList<StandardEquation>();
    final Function1<ReduceExpression, Boolean> _function = (ReduceExpression it) -> {
      return Boolean.valueOf(SplitReduction.hasConvexBody(it));
    };
    final Function1<ReduceExpression, StandardEquation> _function_1 = (ReduceExpression e) -> {
      Equation _containerEquation = AlphaUtil.getContainerEquation(e);
      return ((StandardEquation) _containerEquation);
    };
    Iterables.<StandardEquation>addAll(nonConvexEqus, 
      IterableExtensions.<ReduceExpression, StandardEquation>map(IterableExtensions.<ReduceExpression>reject(EcoreUtil2.<ReduceExpression>getAllContentsOfType(body, ReduceExpression.class), _function), _function_1));
    SplitUnionIntoCase.apply(body);
    PermutationCaseReduce.apply(body);
    NormalizeReduction.apply(body);
    final Consumer<StandardEquation> _function_2 = (StandardEquation equ) -> {
      SubstituteByDef.apply(body, equ.getVariable());
    };
    nonConvexEqus.forEach(_function_2);
    RemoveUnusedEquations.apply(system);
    Normalize.apply(body);
  }

  private static List<AbstractReduceExpression> transform(final ReduceExpression re) {
    final Equation eq = AlphaUtil.getContainerEquation(re);
    SplitUnionIntoCase.apply(re);
    PermutationCaseReduce.apply(re);
    final List<AbstractReduceExpression> newEqs = NormalizeReduction.applyAndReport(eq);
    return newEqs;
  }

  public static boolean hasConvexBody(final ReduceExpression re) {
    int _nbBasicSets = re.getBody().getContextDomain().getNbBasicSets();
    return (_nbBasicSets == 1);
  }

  public static boolean hasNonConvexReduceExpressions(final SystemBody body) {
    boolean _xblockexpression = false;
    {
      final Function1<ReduceExpression, Boolean> _function = (ReduceExpression it) -> {
        return Boolean.valueOf(SplitReduction.hasConvexBody(it));
      };
      final Iterable<ReduceExpression> nonConvexREs = IterableExtensions.<ReduceExpression>reject(EcoreUtil2.<ReduceExpression>getAllContentsOfType(body, ReduceExpression.class), _function);
      int _size = IterableExtensions.size(nonConvexREs);
      _xblockexpression = (_size > 0);
    }
    return _xblockexpression;
  }
}
