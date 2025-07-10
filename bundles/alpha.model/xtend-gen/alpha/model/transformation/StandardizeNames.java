package alpha.model.transformation;

import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaVisitable;
import alpha.model.DependenceExpression;
import alpha.model.IndexExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Standardizes the names of indices in most context domains,
 * expression domains, dependence functions, and restrict domains
 * throughout the provided AST (or sub-tree).
 * 
 * Within all of these, input dimensions are renamed to "i0", "i1", etc.
 * based on their position in the space.
 */
@SuppressWarnings("all")
public class StandardizeNames extends AbstractAlphaCompleteVisitor {
  /**
   * Protected constructor to force "apply" to be the entry point.
   */
  protected StandardizeNames() {
  }

  /**
   * Applies name standardization to an Alpha expression.
   */
  public static void apply(final AlphaExpressionVisitable visitable) {
    new StandardizeNames().accept(visitable);
  }

  /**
   * Applies name standardization to an Alpha system, equation, etc.
   */
  public static void apply(final AlphaVisitable visitable) {
    new StandardizeNames().accept(visitable);
  }

  /**
   * Checks whether the given list of names are valid index names for the given variable.
   */
  protected static boolean areValidNames(final List<String> indexNames, final Variable variable) {
    return ((indexNames != null) && (indexNames.size() == variable.getDomain().getNbIndices()));
  }

  /**
   * Gets the index names to use from a parent equation.
   */
  protected static List<String> _getIndexNames(final StandardEquation eq) {
    final Variable variable = eq.getVariable();
    final EList<String> equationNames = eq.getIndexNames();
    boolean _areValidNames = StandardizeNames.areValidNames(equationNames, variable);
    if (_areValidNames) {
      return equationNames;
    }
    final List<String> variableNames = variable.getDomain().getIndexNames();
    boolean _areValidNames_1 = StandardizeNames.areValidNames(variableNames, variable);
    if (_areValidNames_1) {
      return variableNames;
    }
    return AlphaUtil.defaultDimNames(variable.getDomain());
  }

  /**
   * Gets the index names to use from a parent dependence expression.
   */
  protected static List<String> _getIndexNames(final DependenceExpression expr) {
    return expr.getFunction().getSpace().getOutputNames();
  }

  /**
   * Gets the index names to use from a parent reduce expression.
   */
  protected static List<String> _getIndexNames(final ReduceExpression expr) {
    return expr.getProjection().getSpace().getInputNames();
  }

  /**
   * Gets the index names to use from a parent expression.
   */
  protected static List<String> _getIndexNames(final AlphaExpression expr) {
    return expr.getContextDomain().getIndexNames();
  }

  /**
   * Default rule if none of the other cases matched.
   */
  protected static List<String> _getIndexNames(final Object obj) {
    try {
      throw new Exception("Cannot get the index names to use from the given parent.");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Renames the outputs for a mutli-affine expression.
   * If the output is exactly equal to one of the inputs, the name of that input is used.
   * Otherwise, a default name based on the index of the output is used.
   */
  protected static ISLMultiAff renameOutputs(final ISLMultiAff multiAff) {
    int _nbOutputs = multiAff.getNbOutputs();
    final Function1<Integer, String> _function = new Function1<Integer, String>() {
      public String apply(final Integer it) {
        return StandardizeNames.getOutputName(multiAff, (it).intValue());
      }
    };
    final ArrayList<String> outputNames = CommonExtensions.<String>toArrayList(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _nbOutputs, true), _function));
    return AlphaUtil.renameFirstOutputs(multiAff, outputNames);
  }

  /**
   * Gets the name to use for a specific output of the given multi-affine expression.
   */
  protected static String getOutputName(final ISLMultiAff multiAff, final int outputIndex) {
    final ISLAff aff = multiAff.getAff(outputIndex);
    final String defaultName = ("o" + Integer.valueOf(outputIndex));
    ISLVal _constantVal = aff.getConstantVal();
    boolean _notEquals = (!Objects.equal(_constantVal, Integer.valueOf(0)));
    if (_notEquals) {
      return defaultName;
    }
    int _nbInputs = aff.getNbInputs();
    final Function1<Integer, ISLVal> _function = new Function1<Integer, ISLVal>() {
      public ISLVal apply(final Integer it) {
        return aff.getCoefficientVal(ISLDimType.isl_dim_in, (it).intValue());
      }
    };
    final ArrayList<ISLVal> coefficients = CommonExtensions.<ISLVal>toArrayList(IterableExtensions.<Integer, ISLVal>map(new ExclusiveRange(0, _nbInputs, true), _function));
    final Function1<ISLVal, Boolean> _function_1 = new Function1<ISLVal, Boolean>() {
      public Boolean apply(final ISLVal it) {
        return Boolean.valueOf(((!Objects.equal(it, Integer.valueOf(0))) || (!Objects.equal(it, Integer.valueOf(1)))));
      }
    };
    boolean _exists = IterableExtensions.<ISLVal>exists(coefficients, _function_1);
    if (_exists) {
      return defaultName;
    }
    final Function1<ISLVal, Boolean> _function_2 = new Function1<ISLVal, Boolean>() {
      public Boolean apply(final ISLVal it) {
        return Boolean.valueOf(Objects.equal(it, Integer.valueOf(1)));
      }
    };
    int _size = IterableExtensions.size(IterableExtensions.<ISLVal>filter(coefficients, _function_2));
    boolean _notEquals_1 = (_size != 1);
    if (_notEquals_1) {
      return defaultName;
    }
    return aff.getInputName(coefficients.indexOf(Integer.valueOf(1)));
  }

  /**
   * Renames the indices of the context domain and expression domain of the expression.
   */
  public void inAlphaExpression(final AlphaExpression expr) {
    final List<String> indexNames = StandardizeNames.getIndexNames(expr.eContainer());
    expr.setContextDomain(expr.getContextDomain().<ISLSet>renameIndices(indexNames));
    expr.setExpressionDomain(expr.getExpressionDomain().<ISLSet>renameIndices(indexNames));
  }

  /**
   * Renames the indices of the context domain, expression domain, and restrict domain
   * for the restrict expression.
   */
  public void inRestrictExpression(final RestrictExpression expr) {
    final List<String> indexNames = StandardizeNames.getIndexNames(expr.eContainer());
    expr.setContextDomain(expr.getContextDomain().<ISLSet>renameIndices(indexNames));
    expr.setExpressionDomain(expr.getExpressionDomain().<ISLSet>renameIndices(indexNames));
    expr.setDomainExpr(AlphaUserFactory.createJNIDomain(AlphaUtil.renameIndices(expr.getRestrictDomain(), indexNames)));
  }

  /**
   * Renames the indices of the context domain and expression domain of the expression.
   * Also renames the inputs and outputs of the dependence function.
   */
  public void inDependenceExpression(final DependenceExpression expr) {
    final List<String> indexNames = StandardizeNames.getIndexNames(expr.eContainer());
    expr.setContextDomain(expr.getContextDomain().<ISLSet>renameIndices(indexNames));
    expr.setExpressionDomain(expr.getExpressionDomain().<ISLSet>renameIndices(indexNames));
    expr.setFunctionExpr(AlphaUserFactory.createJNIFunction(StandardizeNames.renameOutputs(AlphaUtil.renameIndices(expr.getFunction(), indexNames))));
  }

  /**
   * Renames the indices of the context domain and expression domain of the expression.
   * Also renames the inputs and outputs of the index function.
   */
  public void inIndexExpression(final IndexExpression expr) {
    final List<String> indexNames = StandardizeNames.getIndexNames(expr.eContainer());
    expr.setContextDomain(expr.getContextDomain().<ISLSet>renameIndices(indexNames));
    expr.setExpressionDomain(expr.getExpressionDomain().<ISLSet>renameIndices(indexNames));
    expr.setFunctionExpr(AlphaUserFactory.createJNIFunction(StandardizeNames.renameOutputs(AlphaUtil.renameIndices(expr.getFunction(), indexNames))));
  }

  /**
   * Renames the indices of the context domain and expression domain of the expression.
   * Also renames the inputs and outputs of the projection function.
   */
  public void inReduceExpression(final ReduceExpression expr) {
    final List<String> indexNames = StandardizeNames.getIndexNames(expr.eContainer());
    expr.setContextDomain(expr.getContextDomain().<ISLSet>renameIndices(indexNames));
    expr.setExpressionDomain(expr.getExpressionDomain().<ISLSet>renameIndices(indexNames));
    expr.setProjectionExpr(AlphaUserFactory.createJNIFunction(StandardizeNames.renameOutputs(AlphaUtil.renameFirstIndices(expr.getProjection(), indexNames))));
  }

  protected static List<String> getIndexNames(final Object expr) {
    if (expr instanceof ReduceExpression) {
      return _getIndexNames((ReduceExpression)expr);
    } else if (expr instanceof DependenceExpression) {
      return _getIndexNames((DependenceExpression)expr);
    } else if (expr instanceof StandardEquation) {
      return _getIndexNames((StandardEquation)expr);
    } else if (expr instanceof AlphaExpression) {
      return _getIndexNames((AlphaExpression)expr);
    } else if (expr != null) {
      return _getIndexNames(expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
}
