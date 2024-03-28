package alpha.targetmapping;

import alpha.model.AffineFuzzyVariableUse;
import alpha.model.AlphaSystem;
import alpha.model.ArgReduceExpression;
import alpha.model.CalculatorExpression;
import alpha.model.CalculatorExpressionEvaluator;
import alpha.model.DependenceExpression;
import alpha.model.IndexExpression;
import alpha.model.JNIFunction;
import alpha.model.JNIFunctionInArrayNotation;
import alpha.model.ReduceExpression;
import alpha.model.UseEquation;
import alpha.model.issue.CalculatorExpressionIssue;
import alpha.model.util.AlphaUtil;
import alpha.targetmapping.util.TargetMappingUtil;
import fr.irisa.cairn.jnimap.isl.ISLFactory;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class CalculatorExpressionEvaluatorForTM extends CalculatorExpressionEvaluator {
  private final int currentBandSize;

  private final int bandIdOffset;

  /**
   * These static method should shadow the ones in parent class.
   */
  public static List<CalculatorExpressionIssue> calculate(final CalculatorExpression expr) {
    return CalculatorExpressionEvaluatorForTM.calculate(expr, null);
  }

  public static List<CalculatorExpressionIssue> calculate(final CalculatorExpression expr, final List<String> indexNameContext) {
    return CalculatorExpressionEvaluatorForTM.calculate(expr, indexNameContext, (-1), 0);
  }

  public static List<CalculatorExpressionIssue> calculate(final CalculatorExpression expr, final List<String> indexNameContext, final int currentBandSize, final int bandIdOffset) {
    final CalculatorExpressionEvaluatorForTM calc = new CalculatorExpressionEvaluatorForTM(indexNameContext, currentBandSize, bandIdOffset);
    calc.testSystemConsistency(expr);
    int _size = calc.issues.size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      return calc.issues;
    }
    expr.accept(calc);
    return calc.issues;
  }

  protected CalculatorExpressionEvaluatorForTM(final List<String> indexNameContext, final int currentBandSize, final int bandIdOffset) {
    super(indexNameContext);
    this.currentBandSize = currentBandSize;
    this.bandIdOffset = bandIdOffset;
  }

  @Override
  public AlphaSystem getReferredSystem(final CalculatorExpression expr) {
    EObject _eContainer = expr.eContainer();
    return TargetMappingUtil.getTargetSystem(((TargetMappingNode) _eContainer));
  }

  protected StringBuffer _parseJNIFunctionInContext(final JNIFunctionInArrayNotation jniFunction, final TargetMappingNode parent) {
    return this.parseJNIFunctionAsFunction(jniFunction);
  }

  protected void _parseJNIFunction(final JNIIdentityFunction jniFunction) {
    List<String> _xifexpression = null;
    if ((this.indexNameContext != null)) {
      _xifexpression = this.indexNameContext;
    } else {
      final Function1<Integer, String> _function = (Integer i) -> {
        return TargetMappingUtil.DEFAULT_SCHEDULE_DIMENSION_NAME_PROVIDER.apply(i);
      };
      _xifexpression = IterableExtensions.<String>toList(IterableExtensions.<Integer, String>map(new ExclusiveRange(this.bandIdOffset, (this.bandIdOffset + this.currentBandSize), true), _function));
    }
    final List<String> dimNames = _xifexpression;
    try {
      final ISLMultiAff jnimaff = ISLFactory.islMultiAff(
        AlphaUtil.toContextFreeISLString(this.getReferredSystem(jniFunction), 
          String.format("{ %s -> %s }", dimNames, dimNames)));
      jniFunction.setISLMultiAff(jnimaff);
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException re = (RuntimeException)_t;
        String _xifexpression_1 = null;
        String _message = re.getMessage();
        boolean _tripleEquals = (_message == null);
        if (_tripleEquals) {
          _xifexpression_1 = re.getClass().getName();
        } else {
          _xifexpression_1 = re.getMessage();
        }
        final String msg = _xifexpression_1;
        this.registerIssue(msg, jniFunction);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  protected StringBuffer parseJNIFunctionInContext(final JNIFunctionInArrayNotation jniFunction, final EObject parent) {
    if (parent instanceof ArgReduceExpression) {
      return _parseJNIFunctionInContext(jniFunction, (ArgReduceExpression)parent);
    } else if (parent instanceof ReduceExpression) {
      return _parseJNIFunctionInContext(jniFunction, (ReduceExpression)parent);
    } else if (parent instanceof DependenceExpression) {
      return _parseJNIFunctionInContext(jniFunction, (DependenceExpression)parent);
    } else if (parent instanceof IndexExpression) {
      return _parseJNIFunctionInContext(jniFunction, (IndexExpression)parent);
    } else if (parent instanceof UseEquation) {
      return _parseJNIFunctionInContext(jniFunction, (UseEquation)parent);
    } else if (parent instanceof AffineFuzzyVariableUse) {
      return _parseJNIFunctionInContext(jniFunction, (AffineFuzzyVariableUse)parent);
    } else if (parent instanceof TargetMappingNode) {
      return _parseJNIFunctionInContext(jniFunction, (TargetMappingNode)parent);
    } else if (parent != null) {
      return _parseJNIFunctionInContext(jniFunction, parent);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(jniFunction, parent).toString());
    }
  }

  protected void parseJNIFunction(final JNIFunction jniFunction) {
    if (jniFunction instanceof JNIFunctionInArrayNotation) {
      _parseJNIFunction((JNIFunctionInArrayNotation)jniFunction);
      return;
    } else if (jniFunction instanceof JNIIdentityFunction) {
      _parseJNIFunction((JNIIdentityFunction)jniFunction);
      return;
    } else if (jniFunction != null) {
      _parseJNIFunction(jniFunction);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(jniFunction).toString());
    }
  }
}
