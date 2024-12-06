package alpha.codegen.alphaBase;

import alpha.codegen.BinaryOperator;
import alpha.codegen.CustomExpr;
import alpha.codegen.Expression;
import alpha.codegen.Factory;
import alpha.codegen.TernaryExprBuilder;
import alpha.codegen.UnaryOperator;
import alpha.codegen.isl.AffineConverter;
import alpha.codegen.isl.ConditionalConverter;
import alpha.codegen.isl.PolynomialConverter;
import alpha.model.AlphaExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.MultiArgExpression;
import alpha.model.PolynomialIndexExpression;
import alpha.model.RestrictExpression;
import alpha.model.UnaryExpression;
import alpha.model.VariableExpression;
import alpha.model.util.CommonExtensions;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Converts Alpha expressions to simpleC expressions.
 * Not all Alpha expressions are currently handled and will throw an exception if encountered.
 * Most notably, this includes reduce expressions.
 */
@SuppressWarnings("all")
public class ExprConverter {
  /**
   * Generates the simpleC data types for various Alpha structures.
   */
  protected final TypeGeneratorBase typeGenerator;

  /**
   * The name checker to use for retrieving variable/parameter names from.
   */
  protected final AlphaNameChecker nameChecker;

  /**
   * Constructs a new converter for expressions.
   */
  public ExprConverter(final TypeGeneratorBase typeGenerator, final AlphaNameChecker nameChecker) {
    this.typeGenerator = typeGenerator;
    this.nameChecker = nameChecker;
  }

  /**
   * Translates a variable expression into a variable access.
   * Note: variable expressions inside dependence expressions are handled
   * by the dependence expression converter, not here.
   * If this is reached, then the variable is implicitly being accessed by the identity function.
   */
  protected Expression _convertExpr(final VariableExpression expr) {
    final String name = this.nameChecker.getVariableReadName(expr.getVariable());
    final Function1<String, CustomExpr> _function = (String it) -> {
      return Factory.customExpr(it);
    };
    final List<CustomExpr> indexExprs = ListExtensions.<String, CustomExpr>map(expr.getContextDomain().getIndexNames(), _function);
    return Factory.callExpr(name, ((Expression[])Conversions.unwrapArray(indexExprs, Expression.class)));
  }

  /**
   * Converts dependence expressions into simpleC expressions.
   */
  protected Expression _convertExpr(final DependenceExpression expr) {
    return this.convertDependence(expr, expr.getExpr());
  }

  /**
   * If the child is a constant, then simply emit that constant.
   */
  protected Expression _convertDependence(final DependenceExpression parent, final ConstantExpression child) {
    return this.convertExpr(child);
  }

  /**
   * If the child is a variable, read the value indexed by the dependence function.
   */
  protected Expression _convertDependence(final DependenceExpression parent, final VariableExpression child) {
    final String name = this.nameChecker.getVariableReadName(child.getVariable());
    final ArrayList<CustomExpr> indexExprs = AffineConverter.convertMultiAff(parent.getFunction());
    return Factory.callExpr(name, ((Expression[])Conversions.unwrapArray(indexExprs, Expression.class)));
  }

  /**
   * Default case for child expressions that aren't supported yet.
   */
  protected Expression _convertDependence(final DependenceExpression parent, final AlphaExpression child) {
    try {
      throw new Exception("Not implemented yet!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Alpha "restrict" expressions don't need to be wrapped in conditionals,
   * as the context domain within Alpha ensures the expression is only accessed where appropriate.
   * For restrict expressions inside "case" or "reduce" expressions,
   * the conditional checking should be handled by the conversion of the "case" or "reduce.
   */
  protected Expression _convertExpr(final RestrictExpression re) {
    return this.convertExpr(re.getExpr());
  }

  /**
   * Alpha "auto-restrict" expressions don't need to be wrapped in conditionals,
   * as the context domain within Alpha ensures the expression is only accessed where appropriate.
   * For auto-restrict expressions inside "case" or "reduce" expressions,
   * the conditional checking should be handled by the conversion of the "case" or "reduce.
   */
  protected Expression _convertExpr(final AutoRestrictExpression re) {
    return this.convertExpr(re.getExpr());
  }

  /**
   * Converts an Alpha "case" expression into a C ternary expression.
   */
  protected Expression _convertExpr(final CaseExpression ce) {
    int _size = ce.getExprs().size();
    boolean _lessEqualsThan = (_size <= 0);
    if (_lessEqualsThan) {
      throw new IllegalArgumentException("Alpha case expression found with no cases.");
    }
    final ArrayList<AutoRestrictExpression> autoRestricts = CommonExtensions.<AutoRestrictExpression>toArrayList(Iterables.<AutoRestrictExpression>filter(ce.getExprs(), AutoRestrictExpression.class));
    int _size_1 = autoRestricts.size();
    boolean _greaterThan = (_size_1 > 1);
    if (_greaterThan) {
      throw new IllegalArgumentException("Alpha case expression found with more than one auto-restrict case.");
    }
    int _size_2 = ce.getExprs().size();
    boolean _equals = (_size_2 == 1);
    if (_equals) {
      return this.convertExpr(ce.getExprs().get(0));
    }
    AlphaExpression _xifexpression = null;
    int _size_3 = autoRestricts.size();
    boolean _equals_1 = (_size_3 == 1);
    if (_equals_1) {
      _xifexpression = autoRestricts.get(0);
    } else {
      _xifexpression = IterableExtensions.<AlphaExpression>last(ce.getExprs());
    }
    final AlphaExpression lastCase = _xifexpression;
    final Function1<AlphaExpression, Boolean> _function = (AlphaExpression it) -> {
      return Boolean.valueOf((it == lastCase));
    };
    final Iterable<AlphaExpression> remainingCases = IterableExtensions.<AlphaExpression>reject(ce.getExprs(), _function);
    final AlphaExpression firstCase = IterableExtensions.<AlphaExpression>head(remainingCases);
    final TernaryExprBuilder builder = TernaryExprBuilder.start(this.createConditional(firstCase), this.convertExpr(firstCase));
    final Consumer<AlphaExpression> _function_1 = (AlphaExpression it) -> {
      builder.addCase(this.createConditional(it), this.convertExpr(it));
    };
    IterableExtensions.<AlphaExpression>tail(remainingCases).forEach(_function_1);
    return builder.elseCase(this.convertExpr(lastCase));
  }

  /**
   * Creates a conditional expression to ensure a restrict case is being respected.
   */
  protected Expression _createConditional(final RestrictExpression re) {
    return ConditionalConverter.convert(re.getRestrictDomain());
  }

  /**
   * Creates a conditional expression to ensure this expression (which is neither a restrict
   * nor an auto-restrict) is only evaluated within its context domain.
   */
  protected Expression _createConditional(final AlphaExpression expr) {
    return ConditionalConverter.convert(expr.getContextDomain());
  }

  protected Expression _convertExpr(final IfExpression expr) {
    final Expression conditional = this.convertExpr(expr.getCondExpr());
    final Expression thenExpr = this.convertExpr(expr.getThenExpr());
    final Expression elseExpr = this.convertExpr(expr.getElseExpr());
    return Factory.ternaryExpr(conditional, thenExpr, elseExpr);
  }

  /**
   * Index expressions in Alpha convert indices into values.
   * Thus, we just need to output the expression itself.
   */
  protected Expression _convertExpr(final IndexExpression ie) {
    final String exprLiteral = ISLAff._toString(ie.getFunction().getAff(0), ISL_FORMAT.C.ordinal());
    return Factory.customExpr(exprLiteral);
  }

  protected Expression _convertExpr(final PolynomialIndexExpression expr) {
    return PolynomialConverter.convert(expr.getPolynomial());
  }

  /**
   * Constants in Alpha simply map to the same constant in C.
   */
  protected Expression _convertExpr(final ConstantExpression ce) {
    return Factory.customExpr(ce.valueString());
  }

  /**
   * There is a 1-to-1 matching between Alpha and C unary expressions.
   */
  protected Expression _convertExpr(final UnaryExpression ue) {
    final UnaryOperator op = AlphaBaseHelpers.getOperator(ue.getOperator());
    final Expression expr = this.convertExpr(ue.getExpr());
    return Factory.unaryExpr(op, expr);
  }

  protected Expression _convertExpr(final BinaryExpression be) {
    final Expression left = this.convertExpr(be.getLeft());
    final Expression right = this.convertExpr(be.getRight());
    final BinaryOperator op = AlphaBaseHelpers.getOperator(be.getOperator());
    return Factory.binaryExpr(op, left, right);
  }

  /**
   * Multi-arg expressions are converted into a tree of nested binary expressions.
   */
  protected Expression _convertExpr(final MultiArgExpression expr) {
    final BinaryOperator op = AlphaBaseHelpers.getOperator(expr.getOperator());
    final Function1<AlphaExpression, Expression> _function = (AlphaExpression it) -> {
      return this.convertExpr(it);
    };
    final List<Expression> children = ListExtensions.<AlphaExpression, Expression>map(expr.getExprs(), _function);
    return Factory.binaryExprTree(op, ((Expression[])Conversions.unwrapArray(children, Expression.class)));
  }

  /**
   * Default case to catch unknown expression types.
   */
  protected Expression _convertExpr(final AlphaExpression expr) {
    try {
      throw new Exception("Not implemented yet!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public Expression convertExpr(final AlphaExpression re) {
    if (re instanceof AutoRestrictExpression) {
      return _convertExpr((AutoRestrictExpression)re);
    } else if (re instanceof BinaryExpression) {
      return _convertExpr((BinaryExpression)re);
    } else if (re instanceof CaseExpression) {
      return _convertExpr((CaseExpression)re);
    } else if (re instanceof ConstantExpression) {
      return _convertExpr((ConstantExpression)re);
    } else if (re instanceof DependenceExpression) {
      return _convertExpr((DependenceExpression)re);
    } else if (re instanceof IfExpression) {
      return _convertExpr((IfExpression)re);
    } else if (re instanceof IndexExpression) {
      return _convertExpr((IndexExpression)re);
    } else if (re instanceof MultiArgExpression) {
      return _convertExpr((MultiArgExpression)re);
    } else if (re instanceof PolynomialIndexExpression) {
      return _convertExpr((PolynomialIndexExpression)re);
    } else if (re instanceof RestrictExpression) {
      return _convertExpr((RestrictExpression)re);
    } else if (re instanceof UnaryExpression) {
      return _convertExpr((UnaryExpression)re);
    } else if (re instanceof VariableExpression) {
      return _convertExpr((VariableExpression)re);
    } else if (re != null) {
      return _convertExpr(re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re).toString());
    }
  }

  public Expression convertDependence(final DependenceExpression parent, final AlphaExpression child) {
    if (child instanceof ConstantExpression) {
      return _convertDependence(parent, (ConstantExpression)child);
    } else if (child instanceof VariableExpression) {
      return _convertDependence(parent, (VariableExpression)child);
    } else if (child != null) {
      return _convertDependence(parent, child);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(parent, child).toString());
    }
  }

  public Expression createConditional(final AlphaExpression re) {
    if (re instanceof RestrictExpression) {
      return _createConditional((RestrictExpression)re);
    } else if (re != null) {
      return _createConditional(re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re).toString());
    }
  }
}
