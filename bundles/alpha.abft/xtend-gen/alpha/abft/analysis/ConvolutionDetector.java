package alpha.abft.analysis;

import alpha.abft.util.ConvolutionKernel;
import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaVisitable;
import alpha.model.BinaryExpression;
import alpha.model.DependenceExpression;
import alpha.model.IntegerExpression;
import alpha.model.RealExpression;
import alpha.model.VariableExpression;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import java.util.Arrays;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * This class tries to identify the presence of convolutions in the program.
 * This is done by pattern matching on scalar sums of products (SoPs) of the form:
 * 
 *   c1[]*(f1@X) + c2[]*(f2@X) + ... + cN[]*(fN@X))
 * 
 * where each term is a BinaryExpression of a constant and the same variable.
 * All of the variable accesses (e.g., f1, f2, etc.) must be uniform functions.
 * 
 * The NormalizedScalarSumOfProducts transformation should be used before this
 * to expose any SoPs.
 */
@SuppressWarnings("all")
public class ConvolutionDetector extends AbstractAlphaCompleteVisitor {
  private List<ConvolutionKernel> convolutions;

  public ConvolutionDetector() {
    this.convolutions = CollectionLiterals.<ConvolutionKernel>newLinkedList();
  }

  public static List<ConvolutionKernel> apply(final AlphaVisitable av) {
    final ConvolutionDetector visitor = new ConvolutionDetector();
    av.accept(visitor);
    return visitor.convolutions;
  }

  public static List<ConvolutionKernel> apply(final AlphaExpressionVisitable aev) {
    final ConvolutionDetector visitor = new ConvolutionDetector();
    aev.accept(visitor);
    return visitor.convolutions;
  }

  @Override
  public void visitBinaryExpression(final BinaryExpression be) {
    this.inBinaryExpression(be);
    this.matchConvolution(be);
  }

  /**
   * Matches on binary expression trees where each term is a product.
   * And each product is a constant times a variable read by a uniform
   * function. The relative position of the terms does not matter.
   */
  public boolean matchConvolution(final BinaryExpression be) {
    boolean _xblockexpression = false;
    {
      final ConvolutionKernel kernel = new ConvolutionKernel(be);
      final boolean matched = this.convolutionRules(kernel, be.getLeft(), be.getRight());
      boolean _xifexpression = false;
      if ((matched && (kernel.isValid()).booleanValue())) {
        _xifexpression = this.convolutions.add(kernel);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  protected boolean _convolutionRules(final ConvolutionKernel kernel, final BinaryExpression lbe, final BinaryExpression rbe) {
    return (this.convolutionRules(kernel, lbe.getLeft(), lbe.getRight()) && this.convolutionRules(kernel, rbe.getLeft(), rbe.getRight()));
  }

  protected boolean _convolutionRules(final ConvolutionKernel kernel, final DependenceExpression left, final DependenceExpression right) {
    return this.convolutionRules(kernel, left, left.getExpr(), right, right.getExpr());
  }

  protected boolean _convolutionRules(final ConvolutionKernel kernel, final DependenceExpression left, final VariableExpression ve, final DependenceExpression right, final RealExpression re) {
    return kernel.build(left.getFunction(), ve.getVariable(), (re.getValue()).floatValue());
  }

  protected boolean _convolutionRules(final ConvolutionKernel kernel, final DependenceExpression left, final VariableExpression ve, final DependenceExpression right, final IntegerExpression ie) {
    return kernel.build(left.getFunction(), ve.getVariable(), (ie.getValue()).intValue());
  }

  protected boolean _convolutionRules(final ConvolutionKernel kernel, final DependenceExpression left, final RealExpression re, final DependenceExpression right, final VariableExpression ve) {
    return kernel.build(right.getFunction(), ve.getVariable(), (re.getValue()).floatValue());
  }

  protected boolean _convolutionRules(final ConvolutionKernel kernel, final DependenceExpression left, final IntegerExpression ie, final DependenceExpression right, final VariableExpression ve) {
    return kernel.build(right.getFunction(), ve.getVariable(), (ie.getValue()).intValue());
  }

  protected boolean _convolutionRules(final ConvolutionKernel kernel, final DependenceExpression left, final AlphaExpression lae, final DependenceExpression right, final AlphaExpression rae) {
    return false;
  }

  protected boolean _convolutionRules(final ConvolutionKernel kernel, final AlphaExpression left, final AlphaExpression right) {
    return false;
  }

  public boolean convolutionRules(final ConvolutionKernel kernel, final AlphaExpression lbe, final AlphaExpression rbe) {
    if (lbe instanceof BinaryExpression
         && rbe instanceof BinaryExpression) {
      return _convolutionRules(kernel, (BinaryExpression)lbe, (BinaryExpression)rbe);
    } else if (lbe instanceof DependenceExpression
         && rbe instanceof DependenceExpression) {
      return _convolutionRules(kernel, (DependenceExpression)lbe, (DependenceExpression)rbe);
    } else if (lbe != null
         && rbe != null) {
      return _convolutionRules(kernel, lbe, rbe);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(kernel, lbe, rbe).toString());
    }
  }

  public boolean convolutionRules(final ConvolutionKernel kernel, final DependenceExpression left, final AlphaExpression ie, final DependenceExpression right, final AlphaExpression ve) {
    if (ie instanceof IntegerExpression
         && ve instanceof VariableExpression) {
      return _convolutionRules(kernel, left, (IntegerExpression)ie, right, (VariableExpression)ve);
    } else if (ie instanceof RealExpression
         && ve instanceof VariableExpression) {
      return _convolutionRules(kernel, left, (RealExpression)ie, right, (VariableExpression)ve);
    } else if (ie instanceof VariableExpression
         && ve instanceof IntegerExpression) {
      return _convolutionRules(kernel, left, (VariableExpression)ie, right, (IntegerExpression)ve);
    } else if (ie instanceof VariableExpression
         && ve instanceof RealExpression) {
      return _convolutionRules(kernel, left, (VariableExpression)ie, right, (RealExpression)ve);
    } else if (ie != null
         && ve != null) {
      return _convolutionRules(kernel, left, ie, right, ve);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(kernel, left, ie, right, ve).toString());
    }
  }
}
