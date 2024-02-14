package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.Equation;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaUtil;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * NormalizeReduction moves a reduction to a separate equation.
 * The main purpose is to have an isolated equation for applying transformations
 * on the reduction (e.g., {@link SimplifyingReductions})
 */
@SuppressWarnings("all")
public class NormalizeReduction extends AbstractAlphaCompleteVisitor {
  private List<AbstractReduceExpression> targetREs = new LinkedList<AbstractReduceExpression>();

  private List<StandardEquation> newEqs = new LinkedList<StandardEquation>();

  public static Function<StandardEquation, String> defineNormalizeReductionEquationName = ((Function<StandardEquation, String>) (StandardEquation se) -> {
    String _xblockexpression = null;
    {
      final String origName = se.getVariable().getName();
      String NRname = (origName + "_NR");
      _xblockexpression = AlphaUtil.duplicateNameResolverWithCounter().apply(se.getSystemBody().getSystem(), NRname);
    }
    return _xblockexpression;
  });

  /**
   * Applies the transformation to the specified expression.
   * 
   * Returns the equation that contains the extracted ReduceExpression
   */
  public static StandardEquation apply(final AbstractReduceExpression are) {
    StandardEquation _xifexpression = null;
    Equation _containerEquation = AlphaUtil.getContainerEquation(are);
    if ((_containerEquation instanceof StandardEquation)) {
      _xifexpression = NormalizeReduction.transform(are);
    } else {
      throw new IllegalArgumentException("[NormalizeReduction] Expecting reductions within StandardEquations.");
    }
    return _xifexpression;
  }

  /**
   * Applies the transformation to all applicable expressions in the given input.
   * 
   * When there are nested reductions, only the top level AbstractReduceExpression is transformed.
   * 
   * @returns number of applications of the transformation
   */
  public static int apply(final AlphaVisitable av) {
    final NormalizeReduction visitor = new NormalizeReduction();
    visitor.accept(av);
    final Consumer<AbstractReduceExpression> _function = (AbstractReduceExpression are) -> {
      NormalizeReduction.transform(are);
    };
    visitor.targetREs.forEach(_function);
    return visitor.targetREs.size();
  }

  public static List<AbstractReduceExpression> applyAndReport(final AlphaVisitable av) {
    final NormalizeReduction visitor = new NormalizeReduction();
    visitor.accept(av);
    final Consumer<AbstractReduceExpression> _function = (AbstractReduceExpression are) -> {
      NormalizeReduction.transform(are);
    };
    visitor.targetREs.forEach(_function);
    return visitor.targetREs;
  }

  private static StandardEquation transform(final AbstractReduceExpression are) {
    Equation _containerEquation = AlphaUtil.getContainerEquation(are);
    final StandardEquation equation = ((StandardEquation) _containerEquation);
    final SystemBody systemBody = equation.getSystemBody();
    final AlphaSystem system = systemBody.getSystem();
    final String newEqName = NormalizeReduction.defineNormalizeReductionEquationName.apply(equation);
    final Variable newVar = AlphaUserFactory.createVariable(newEqName, are.getContextDomain());
    system.getLocals().add(newVar);
    final VariableExpression varExpr = AlphaUserFactory.createVariableExpression(newVar);
    EcoreUtil.replace(are, varExpr);
    final StandardEquation newEq = AlphaUserFactory.createStandardEquation(newVar, are);
    systemBody.getEquations().add(newEq);
    AlphaInternalStateConstructor.recomputeContextDomain(equation);
    AlphaInternalStateConstructor.recomputeContextDomain(newEq);
    return newEq;
  }

  /**
   * Skip UseEquations. It is not expected to have reductions in UseEquation inputs.
   */
  @Override
  public void visitUseEquation(final UseEquation ue) {
    return;
  }

  @Override
  public void visitAbstractReduceExpression(final AbstractReduceExpression are) {
    EObject _eContainer = are.eContainer();
    boolean _not = (!(_eContainer instanceof Equation));
    if (_not) {
      this.targetREs.add(are);
    } else {
      super.visitAbstractReduceExpression(are);
    }
  }
}
