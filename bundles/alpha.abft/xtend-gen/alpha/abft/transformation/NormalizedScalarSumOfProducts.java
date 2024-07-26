package alpha.abft.transformation;

import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.MultiArgExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.analysis.reduction.ShareSpaceAnalysis;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AShow;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * This class attempts to identify the presence of scalar sums of products
 * and put them into the following normal form as a MultiArgExpression (mae):
 * 
 * sum(c1[]*(f1@X), c2[]*(f2@X), c3[]*(f3@X), ...)
 * 
 * where each expr in the mae is a binary expression with a constant on the
 * left-hand side and a dependence expression on the right-hand side. Also,
 * each dependence expression must be a uniform dependence.
 * 
 * For example, the following expression:
 * 
 *   x[i] + 2*x[i+1] + 0.5*x[i] - x[i-2]
 * 
 * is transformed into:
 * 
 *   sum(-1*x[i-2], (1+0.5)*x[i], 2*x[i+1])
 */
@SuppressWarnings("all")
public class NormalizedScalarSumOfProducts extends AbstractAlphaCompleteVisitor {
  private static boolean DEBUG = true;

  private static int constantCount = 0;

  public static void debug(final String msg) {
    InputOutput.<String>println(msg);
  }

  public static void apply(final AlphaVisitable av) {
    final NormalizedScalarSumOfProducts visitor = new NormalizedScalarSumOfProducts();
    while (IteratorExtensions.<EObject>exists(av.eAllContents(), ((Function1<EObject, Boolean>) (EObject e) -> {
      return Boolean.valueOf((e instanceof BinaryExpression));
    }))) {
      av.accept(visitor);
    }
  }

  public static void apply(final AlphaExpressionVisitable aev) {
    final NormalizedScalarSumOfProducts visitor = new NormalizedScalarSumOfProducts();
    aev.accept(visitor);
  }

  @Override
  public void outBinaryExpression(final BinaryExpression be) {
    BINARY_OP _operator = be.getOperator();
    if (_operator != null) {
      switch (_operator) {
        case ADD:
        case SUB:
          NormalizedScalarSumOfProducts.toSumMultiArg(be);
          break;
        case MUL:
          this.toProdMultiArg(be);
          break;
        default:
          break;
      }
    } else {
    }
  }

  public static void toSumMultiArg(final BinaryExpression be) {
    final MultiArgExpression mae = AlphaUserFactory.createMultiArgExpression(REDUCTION_OP.SUM);
    EList<AlphaExpression> _exprs = mae.getExprs();
    AlphaExpression _copyAE = AlphaUtil.<AlphaExpression>copyAE(be.getLeft());
    _exprs.add(_copyAE);
    final AlphaExpression rhs = AlphaUtil.<AlphaExpression>copyAE(be.getRight());
    BINARY_OP _operator = be.getOperator();
    boolean _equals = Objects.equal(_operator, BINARY_OP.SUB);
    if (_equals) {
      EList<AlphaExpression> _exprs_1 = mae.getExprs();
      BinaryExpression _createNegatedExpression = AlphaUserFactory.createNegatedExpression(rhs);
      _exprs_1.add(_createNegatedExpression);
    } else {
      EList<AlphaExpression> _exprs_2 = mae.getExprs();
      _exprs_2.add(rhs);
    }
    final String dbg = AShow.print(be);
    EcoreUtil.replace(be, mae);
    AlphaInternalStateConstructor.recomputeContextDomain(mae);
    String _print = AShow.print(mae);
    String _plus = ((dbg + " -> ") + _print);
    NormalizedScalarSumOfProducts.debug(_plus);
  }

  public void toProdMultiArg(final BinaryExpression be) {
    final MultiArgExpression mae = AlphaUserFactory.createMultiArgExpression(REDUCTION_OP.PROD);
    this.addExprs(mae, be, be.getLeft());
    this.addExprs(mae, be, be.getRight());
    final String dbg = AShow.print(be);
    EcoreUtil.replace(be, mae);
    AlphaInternalStateConstructor.recomputeContextDomain(mae);
    String _print = AShow.print(mae);
    String _plus = ((dbg + " -> ") + _print);
    NormalizedScalarSumOfProducts.debug(_plus);
  }

  protected boolean _addExprs(final MultiArgExpression newMae, final BinaryExpression be, final MultiArgExpression child) {
    boolean _xifexpression = false;
    boolean _isEquivalent = AlphaUtil.isEquivalent(be.getOperator(), child.getOperator());
    if (_isEquivalent) {
      final Function1<AlphaExpression, AlphaExpression> _function = (AlphaExpression it) -> {
        return AlphaUtil.<AlphaExpression>copyAE(it);
      };
      _xifexpression = newMae.getExprs().addAll(ListExtensions.<AlphaExpression, AlphaExpression>map(child.getExprs(), _function));
    } else {
      EList<AlphaExpression> _exprs = newMae.getExprs();
      MultiArgExpression _copyAE = AlphaUtil.<MultiArgExpression>copyAE(child);
      _xifexpression = _exprs.add(_copyAE);
    }
    return _xifexpression;
  }

  protected boolean _addExprs(final MultiArgExpression newMae, final BinaryExpression be, final AlphaExpression child) {
    EList<AlphaExpression> _exprs = newMae.getExprs();
    AlphaExpression _copyAE = AlphaUtil.<AlphaExpression>copyAE(child);
    return _exprs.add(_copyAE);
  }

  private static boolean isMultiArgWith(final AlphaExpression ae, final REDUCTION_OP op) {
    return ((ae instanceof MultiArgExpression) && Objects.equal(((MultiArgExpression) ae).getOperator(), op));
  }

  @Override
  public void outMultiArgExpression(final MultiArgExpression mae) {
    final AlphaSystem system = AlphaUtil.getContainerSystemBody(mae).getSystem();
    final Function1<AlphaExpression, Boolean> _function = (AlphaExpression e) -> {
      return Boolean.valueOf(NormalizedScalarSumOfProducts.isMultiArgWith(e, mae.getOperator()));
    };
    final Function1<AlphaExpression, AlphaExpression> _function_1 = (AlphaExpression e) -> {
      return AlphaUtil.<AlphaExpression>copyAE(e);
    };
    final List<AlphaExpression> exprs = IterableExtensions.<AlphaExpression>toList(IterableExtensions.<AlphaExpression, AlphaExpression>map(IterableExtensions.<AlphaExpression>reject(mae.getExprs(), _function), _function_1));
    final Function1<AlphaExpression, Boolean> _function_2 = (AlphaExpression e) -> {
      return Boolean.valueOf(NormalizedScalarSumOfProducts.isMultiArgWith(e, mae.getOperator()));
    };
    final Function1<AlphaExpression, MultiArgExpression> _function_3 = (AlphaExpression e) -> {
      AlphaExpression _copyAE = AlphaUtil.<AlphaExpression>copyAE(e);
      return ((MultiArgExpression) _copyAE);
    };
    final Consumer<MultiArgExpression> _function_4 = (MultiArgExpression nestedMae) -> {
      exprs.addAll(nestedMae.getExprs());
    };
    IterableExtensions.<AlphaExpression, MultiArgExpression>map(IterableExtensions.<AlphaExpression>filter(mae.getExprs(), _function_2), _function_3).forEach(_function_4);
    final MultiArgExpression newMae = AlphaUserFactory.createMultiArgExpression(mae.getOperator());
    EList<AlphaExpression> _exprs = newMae.getExprs();
    Iterables.<AlphaExpression>addAll(_exprs, exprs);
    EcoreUtil.replace(mae, newMae);
    AlphaInternalStateConstructor.recomputeContextDomain(newMae);
    REDUCTION_OP _operator = newMae.getOperator();
    boolean _notEquals = (!Objects.equal(_operator, REDUCTION_OP.SUM));
    if (_notEquals) {
      return;
    }
    final Function1<AlphaExpression, Boolean> _function_5 = (AlphaExpression e) -> {
      return Boolean.valueOf(NormalizedScalarSumOfProducts.isMultiArgWith(e, REDUCTION_OP.PROD));
    };
    final Consumer<AlphaExpression> _function_6 = (AlphaExpression e) -> {
      EcoreUtil.replace(e, AlphaUserFactory.createIdentityProdExpression(AlphaUtil.<AlphaExpression>copyAE(e)));
    };
    IterableExtensions.<AlphaExpression>reject(newMae.getExprs(), _function_5).forEach(_function_6);
    AlphaInternalStateConstructor.recomputeContextDomain(newMae);
    final Function1<AlphaExpression, Boolean> _function_7 = (AlphaExpression e) -> {
      return Boolean.valueOf((e instanceof MultiArgExpression));
    };
    final Consumer<AlphaExpression> _function_8 = (AlphaExpression it) -> {
      try {
        throw new Exception("There is an unexpected alpha expression.");
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    IterableExtensions.<AlphaExpression>reject(newMae.getExprs(), _function_7).forEach(_function_8);
    final Function1<AlphaExpression, MultiArgExpression> _function_9 = (AlphaExpression e) -> {
      return ((MultiArgExpression) e);
    };
    final List<MultiArgExpression> maes = ListExtensions.<AlphaExpression, MultiArgExpression>map(newMae.getExprs(), _function_9);
    InputOutput.<String>println(AShow.print(system));
    InputOutput.println();
    final Consumer<MultiArgExpression> _function_10 = (MultiArgExpression e) -> {
      NormalizedScalarSumOfProducts.flattenConstantExprs(e);
    };
    maes.forEach(_function_10);
    InputOutput.println();
  }

  /**
   * assumes that
   */
  private static Object factorCommonTerms(final MultiArgExpression mae) {
    return null;
  }

  /**
   * Hoists any constant terms within the mae into their own variable, then replaces
   * the constant terms in the mae with this new variable.
   * 
   * For example, given:
   * 
   *   prod(e1, e2, e3, ...)
   * 
   * and suppose that e1 and e2 are the only constant exprs. The following new local
   * variable will be introduced:
   * 
   *   locals:
   *     _cX = {}
   *   ...
   *     _cX = prod(e1, e2)
   * 
   * and the input mae will be transformed into the following:
   * 
   *   prod(_cX[], e3, ...)
   */
  private static void flattenConstantExprs(final MultiArgExpression mae) {
    final Function1<AlphaExpression, Boolean> _function = (AlphaExpression it) -> {
      return Boolean.valueOf(ShareSpaceAnalysis.isConstantInContext(it));
    };
    final Set<AlphaExpression> constExprs = IterableExtensions.<AlphaExpression>toSet(IterableExtensions.<AlphaExpression>filter(mae.getExprs(), _function));
    int _size = constExprs.size();
    boolean _equals = (_size == 1);
    if (_equals) {
      return;
    }
    final Function1<AlphaExpression, Boolean> _function_1 = (AlphaExpression e) -> {
      return Boolean.valueOf(constExprs.contains(e));
    };
    final List<AlphaExpression> otherExprs = IterableExtensions.<AlphaExpression>toList(IterableExtensions.<AlphaExpression>reject(mae.getExprs(), _function_1));
    final SystemBody systemBody = AlphaUtil.getContainerSystemBody(mae);
    final Variable variable = AlphaUserFactory.createVariable(("_c" + Integer.valueOf(NormalizedScalarSumOfProducts.constantCount)), AlphaUtil.createConstantExprDomain(mae.getContextDomain().getSpace()));
    int _constantCount = NormalizedScalarSumOfProducts.constantCount;
    NormalizedScalarSumOfProducts.constantCount = (_constantCount + 1);
    final MultiArgExpression constMae = AlphaUserFactory.createMultiArgExpression(mae.getOperator());
    EList<AlphaExpression> _exprs = constMae.getExprs();
    final Function1<AlphaExpression, AlphaExpression> _function_2 = (AlphaExpression it) -> {
      return AlphaUtil.<AlphaExpression>copyAE(it);
    };
    Iterable<AlphaExpression> _map = IterableExtensions.<AlphaExpression, AlphaExpression>map(constExprs, _function_2);
    Iterables.<AlphaExpression>addAll(_exprs, _map);
    EList<Variable> _locals = systemBody.getSystem().getLocals();
    _locals.add(variable);
    final StandardEquation eq = AlphaUserFactory.createStandardEquation(variable, constMae);
    EList<Equation> _equations = systemBody.getEquations();
    _equations.add(eq);
    AlphaInternalStateConstructor.recomputeContextDomain(eq);
    final DependenceExpression depExpr = AlphaUserFactory.createDependenceExpression(ISLUtil.createConstantMaff(mae.getContextDomain().getSpace()));
    depExpr.setExpr(AlphaUserFactory.createVariableExpression(variable));
    final MultiArgExpression newMae = AlphaUserFactory.createMultiArgExpression(mae.getOperator());
    EList<AlphaExpression> _exprs_1 = newMae.getExprs();
    _exprs_1.add(depExpr);
    EList<AlphaExpression> _exprs_2 = newMae.getExprs();
    Iterables.<AlphaExpression>addAll(_exprs_2, otherExprs);
  }

  public boolean addExprs(final MultiArgExpression newMae, final BinaryExpression be, final AlphaExpression child) {
    if (child instanceof MultiArgExpression) {
      return _addExprs(newMae, be, (MultiArgExpression)child);
    } else if (child != null) {
      return _addExprs(newMae, be, child);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(newMae, be, child).toString());
    }
  }
}
