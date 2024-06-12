package alpha.model;

import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.ISLUtil;

/**
 * This class reports the overall complexity of the given node in the AST.
 * It simply visits every node in the tree and computes the dimensionality
 * (i.e., number of free dimensions) of its context domain. Then it reports
 * the max dimensionality encountered.
 * 
 * This is primarily intended to be used by the simplifying reductions
 * implementation where we simply need to visit all standard equations and
 * reduce expression. It currently only handles programs with a single parameter
 * and throws an exception otherwise.
 */
@SuppressWarnings("all")
public class ComplexityCalculator extends AbstractAlphaCompleteVisitor {
  private int maxComplexity;

  public static int complexity(final AlphaVisitable av) {
    final ComplexityCalculator visitor = new ComplexityCalculator();
    visitor.accept(av);
    return visitor.maxComplexity;
  }

  public int updateComplexity(final int c) {
    int _xifexpression = (int) 0;
    if ((c > this.maxComplexity)) {
      _xifexpression = this.maxComplexity = c;
    }
    return _xifexpression;
  }

  @Override
  public void outStandardEquation(final StandardEquation eq) {
    this.updateComplexity(ISLUtil.dimensionality(eq.getVariable().getDomain()));
  }

  @Override
  public void outUseEquation(final UseEquation eq) {
    throw new UnsupportedOperationException("Complexity calculation of UseEquations not yet implemented");
  }

  @Override
  public void outReduceExpression(final ReduceExpression re) {
    this.updateComplexity(ISLUtil.dimensionality(re.getBody().getContextDomain()));
  }
}
