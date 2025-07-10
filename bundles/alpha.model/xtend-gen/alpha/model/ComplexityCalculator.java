package alpha.model;

import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLDimType;

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
    final AlphaSystem system = AlphaUtil.getContainerSystem(av);
    if (((system != null) && (system.getParameterDomain().dim(ISLDimType.isl_dim_param) > 1))) {
      throw new UnsupportedOperationException("Current complexity calculator only works for systems containing a single parameter.");
    }
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

  public void outStandardEquation(final StandardEquation eq) {
    this.updateComplexity(ISLUtil.dimensionality(eq.getVariable().getDomain()));
  }

  public void outUseEquation(final UseEquation eq) {
    throw new UnsupportedOperationException("Complexity calculation of UseEquations not yet implemented");
  }

  public void outReduceExpression(final ReduceExpression re) {
    this.updateComplexity(ISLUtil.dimensionality(re.getBody().getContextDomain()));
  }
}
