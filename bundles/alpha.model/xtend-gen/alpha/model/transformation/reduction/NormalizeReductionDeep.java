package alpha.model.transformation.reduction;

import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaVisitable;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * Applies NormalizeReduction to all reduce expressions within the tree.
 * Ensures that even nested reductions are moved to their own equations.
 */
@SuppressWarnings("all")
public class NormalizeReductionDeep extends AbstractAlphaCompleteVisitor {
  /**
   * The list of reduce expressions to call NormalizeReduction on.
   * Constructing a list and then applying NormalizeReduction after walking the AST
   * prevents concurrent modification exceptions.
   */
  private final ArrayList<ReduceExpression> targetREs = CollectionLiterals.<ReduceExpression>newArrayList();

  /**
   * Applies NormalizeReduction to all descendants of an equation, system body, system, etc.
   */
  public static void apply(final AlphaVisitable av) {
    final NormalizeReductionDeep visitor = new NormalizeReductionDeep();
    visitor.accept(av);
    visitor.normalizeTargets();
  }

  /**
   * Applies NormalizeReduction to all descendants of an expression.
   */
  public static void apply(final AlphaExpressionVisitable ave) {
    final NormalizeReductionDeep visitor = new NormalizeReductionDeep();
    visitor.accept(ave);
    visitor.normalizeTargets();
  }

  /**
   * Normalizes all of the target reduce expressions.
   * Calling this after walking the AST prevents concurrent modification exceptions.
   */
  protected void normalizeTargets() {
    final Consumer<ReduceExpression> _function = (ReduceExpression re) -> {
      NormalizeReduction.apply(re);
    };
    this.targetREs.forEach(_function);
  }

  /**
   * If the reduce expression isn't already the top-level expression
   * of an equation, add it to the list of reductions to normalize.
   */
  @Override
  public void outReduceExpression(final ReduceExpression re) {
    EObject _eContainer = re.eContainer();
    if ((_eContainer instanceof Equation)) {
      return;
    }
    this.targetREs.add(re);
  }
}
