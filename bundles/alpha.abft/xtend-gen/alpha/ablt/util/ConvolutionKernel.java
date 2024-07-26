package alpha.ablt.util;

import alpha.model.AlphaExpression;
import alpha.model.Variable;
import alpha.model.util.AShow;
import alpha.model.util.AffineFunctionOperations;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * This class is a simple wrapper around a multi-dimensional list to
 * represent a convolution kernel.
 * 
 * For example, the following weighted sum:
 * 
 *   Y[i] = 0.332[] * X[i-1] + 0.333[] * X[i] + 0.334[] * X[i+1]
 * 
 * can be thought of as the convolution on X by the kernel [0.332, 0.333, 0.334]
 * over the context domain of the expression on the right-hand side.
 * 
 * Each convolution kernel is defined by two pieces of information:
 * 1) the convolution kernel
 * 2) the convolution domain
 */
@SuppressWarnings("all")
public class ConvolutionKernel {
  private Map<ISLMultiAff, Float> kernel;

  private Set<Variable> variables;

  private AlphaExpression expr;

  private Boolean valid;

  public ConvolutionKernel(final AlphaExpression expr) {
    this.kernel = CollectionLiterals.<ISLMultiAff, Float>newHashMap();
    this.variables = CollectionLiterals.<Variable>newHashSet();
    this.expr = expr;
  }

  public boolean build(final ISLMultiAff maff, final Variable variable, final float value) {
    boolean _xblockexpression = false;
    {
      this.variables.add(variable);
      this.kernel.put(maff, Float.valueOf(value));
      _xblockexpression = true;
    }
    return _xblockexpression;
  }

  public Boolean isValid() {
    Boolean _xblockexpression = null;
    {
      if ((this.valid == null)) {
        this.valid = Boolean.valueOf(((this.variables.size() == 1) && (IterableExtensions.<Boolean>reduce(IterableExtensions.<ISLMultiAff, Boolean>map(this.kernel.keySet(), ((Function1<ISLMultiAff, Boolean>) (ISLMultiAff maff) -> {
          return Boolean.valueOf(AffineFunctionOperations.isUniform(maff));
        })), ((Function2<Boolean, Boolean, Boolean>) (Boolean v1, Boolean v2) -> {
          return Boolean.valueOf(((v1).booleanValue() && (v2).booleanValue()));
        }))).booleanValue()));
      }
      _xblockexpression = this.valid;
    }
    return _xblockexpression;
  }

  public ISLSet domain() {
    ISLSet _xblockexpression = null;
    {
      if ((this.valid).booleanValue()) {
        return this.expr.getContextDomain();
      }
      _xblockexpression = ISLSet.buildEmpty(this.expr.getContextDomain().getSpace());
    }
    return _xblockexpression;
  }

  @Override
  public String toString() {
    return AShow.print(this.expr);
  }
}
