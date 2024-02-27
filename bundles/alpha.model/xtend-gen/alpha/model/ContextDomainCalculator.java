package alpha.model;

import alpha.model.issue.AlphaIssue;
import alpha.model.issue.AlphaIssueFactory;
import alpha.model.issue.UnexpectedISLErrorIssue;
import alpha.model.util.AbstractAlphaExpressionVisitor;
import alpha.model.util.AlphaExpressionUtil;
import alpha.model.util.AlphaUtil;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Computes the context domain for AlphaExpressions. The context domains
 * define the set where the given expression needs to be evaluated to
 * compute all the outputs. It is computed top-down starting from the
 * root (equations) after computing all the expression domains.
 * 
 * The context domain is essentially the intersection of the expression domain
 * with the context domain of its parent. The parent context may undergo some
 * transformations depending on the expression, where the notable ones are:
 *   - DependenceExpression takes the image of its parent context by the dependence function
 *   - ReduceExpression takes the pre-image of its parent context by the projection function
 */
@SuppressWarnings("all")
public class ContextDomainCalculator extends AbstractAlphaExpressionVisitor {
  private List<AlphaIssue> issues = new LinkedList<AlphaIssue>();

  protected static List<AlphaIssue> _calculate(final AlphaVisitable node) {
    final ContextDomainCalculator calc = new ContextDomainCalculator();
    calc.visit(node);
    return calc.issues;
  }

  protected static List<AlphaIssue> _calculate(final AlphaExpressionVisitable expr) {
    final ContextDomainCalculator calc = new ContextDomainCalculator();
    expr.accept(calc);
    return calc.issues;
  }

  private void registerIssue(final String errMsg, final AlphaNode node) {
    UnexpectedISLErrorIssue _unexpectedISLErrorIssue = new UnexpectedISLErrorIssue(errMsg, node, null);
    this.issues.add(_unexpectedISLErrorIssue);
  }

  private void registerIssue(final AlphaIssue issue) {
    this.issues.add(issue);
  }

  @Override
  public void inAutoRestrictExpression(final AutoRestrictExpression are) {
    EObject _eContainer = are.eContainer();
    boolean _tripleEquals = (_eContainer == null);
    if (_tripleEquals) {
      throw new RuntimeException("Uncontained AlphaExpression");
    }
    ISLSet _expressionDomain = are.getExpressionDomain();
    boolean _tripleEquals_1 = (_expressionDomain == null);
    if (_tripleEquals_1) {
      return;
    }
    EObject _eContainer_1 = are.eContainer();
    boolean _not = (!(_eContainer_1 instanceof CaseExpression));
    if (_not) {
      this.issues.add(AlphaIssueFactory.autoRestrictNotInCase(are));
      return;
    }
    EObject _eContainer_2 = are.eContainer();
    final CaseExpression parentCase = ((CaseExpression) _eContainer_2);
    long _count = AlphaExpressionUtil.<AutoRestrictExpression>getChildrenOfType(parentCase, AutoRestrictExpression.class).count();
    boolean _greaterThan = (_count > 1);
    if (_greaterThan) {
      this.issues.add(AlphaIssueFactory.multipleAutoRestrict(are));
      return;
    }
    boolean _testNonNullExpressionDomain = AlphaExpressionUtil.testNonNullExpressionDomain(parentCase.getExprs().stream());
    boolean _not_1 = (!_testNonNullExpressionDomain);
    if (_not_1) {
      return;
    }
    final Consumer<AlphaIssue> _function = (AlphaIssue i) -> {
      this.registerIssue(i);
    };
    final ISLSet parentContext = AlphaExpressionUtil.parentContext(are, parentCase, _function);
    if ((parentContext == null)) {
      return;
    }
    ISLSet inferredDomain = null;
    int _length = ((Object[])Conversions.unwrapArray(parentCase.getExprs(), Object.class)).length;
    boolean _equals = (_length == 1);
    if (_equals) {
      inferredDomain = parentContext.intersect(are.getExpressionDomain());
    } else {
      final Function1<AlphaExpression, Boolean> _function_1 = (AlphaExpression e) -> {
        return Boolean.valueOf((!(e instanceof AutoRestrictExpression)));
      };
      final Function1<AlphaExpression, ISLSet> _function_2 = (AlphaExpression it) -> {
        return it.getExpressionDomain();
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_3 = (ISLSet p1, ISLSet p2) -> {
        return p1.union(p2);
      };
      final ISLSet otherExprDomain = IterableExtensions.<ISLSet>reduce(IterableExtensions.<AlphaExpression, ISLSet>map(IterableExtensions.<AlphaExpression>filter(parentCase.getExprs(), _function_1), _function_2), _function_3);
      inferredDomain = parentContext.subtract(otherExprDomain).intersect(are.getExpressionDomain());
    }
    boolean _isEmpty = inferredDomain.isEmpty();
    if (_isEmpty) {
      this.issues.add(AlphaIssueFactory.emptyAutoRestrict(are));
    }
    are.setInferredDomain(inferredDomain);
    are.setContextDomain(inferredDomain.copy().computeDivs());
  }

  @Override
  public void inAlphaExpression(final AlphaExpression ae) {
    EObject _eContainer = ae.eContainer();
    boolean _tripleEquals = (_eContainer == null);
    if (_tripleEquals) {
      throw new RuntimeException("Uncontained AlphaExpression");
    }
    ISLSet _expressionDomain = ae.getExpressionDomain();
    boolean _tripleEquals_1 = (_expressionDomain == null);
    if (_tripleEquals_1) {
      return;
    }
    EObject _eContainer_1 = ae.eContainer();
    final Consumer<AlphaIssue> _function = (AlphaIssue i) -> {
      this.registerIssue(i);
    };
    final ISLSet parentContext = AlphaExpressionUtil.parentContext(ae, ((AlphaCompleteVisitable) _eContainer_1), _function);
    if ((parentContext == null)) {
      return;
    }
    final ISLSet processedContext = this.processContext(ae.eContainer(), parentContext);
    if ((processedContext == null)) {
      return;
    }
    boolean _isEqual = processedContext.getSpace().isEqual(ae.getExpressionDomain().getSpace());
    boolean _not = (!_isEqual);
    if (_not) {
      this.issues.add(AlphaIssueFactory.incompatibleContextAndExpressionDomain(ae));
      return;
    }
    final Supplier<ISLSet> _function_1 = () -> {
      return processedContext.intersect(ae.getExpressionDomain());
    };
    final ISLSet context = this.<ISLSet>runISLoperations(ae, _function_1);
    ae.setContextDomain(context.computeDivs());
  }

  private ISLSet _processContext(final DependenceExpression expr, final ISLSet context) {
    final Supplier<ISLSet> _function = () -> {
      return context.apply(expr.getFunction().toMap());
    };
    return this.<ISLSet>runISLoperations(expr, _function);
  }

  private ISLSet _processContext(final SelectExpression expr, final ISLSet context) {
    final Supplier<ISLSet> _function = () -> {
      return context.apply(expr.getSelectRelation());
    };
    return this.<ISLSet>runISLoperations(expr, _function);
  }

  private ISLSet _processContext(final ReduceExpression expr, final ISLSet context) {
    final Supplier<ISLSet> _function = () -> {
      return context.preimage(expr.getProjection());
    };
    return this.<ISLSet>runISLoperations(expr, _function);
  }

  private ISLSet _processContext(final ArgReduceExpression expr, final ISLSet context) {
    final Supplier<ISLSet> _function = () -> {
      return context.preimage(expr.getProjection());
    };
    return this.<ISLSet>runISLoperations(expr, _function);
  }

  private ISLSet _processContext(final ConvolutionExpression expr, final ISLSet context) {
    final Supplier<ISLSet> _function = () -> {
      return context.flatProduct(expr.getKernelDomain());
    };
    return this.<ISLSet>runISLoperations(expr, _function);
  }

  private ISLSet _processContext(final AlphaNode expr, final ISLSet context) {
    return context;
  }

  private ISLSet _processContext(final EObject expr, final ISLSet context) {
    return null;
  }

  private <T extends Object> T runISLoperations(final AlphaExpression expr, final Supplier<T> r) {
    boolean _testNonNullExpressionDomain = AlphaExpressionUtil.testNonNullExpressionDomain(AlphaExpressionUtil.<AlphaExpression>getChildrenOfType(expr, AlphaExpression.class));
    if (_testNonNullExpressionDomain) {
      final Consumer<String> _function = (String err) -> {
        this.registerIssue(err, expr);
      };
      return AlphaUtil.<T>callISLwithErrorHandling(r, _function);
    }
    return null;
  }

  public static List<AlphaIssue> calculate(final AlphaCompleteVisitable expr) {
    if (expr instanceof AlphaExpressionVisitable) {
      return _calculate((AlphaExpressionVisitable)expr);
    } else if (expr instanceof AlphaVisitable) {
      return _calculate((AlphaVisitable)expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }

  private ISLSet processContext(final EObject expr, final ISLSet context) {
    if (expr instanceof ArgReduceExpression) {
      return _processContext((ArgReduceExpression)expr, context);
    } else if (expr instanceof ReduceExpression) {
      return _processContext((ReduceExpression)expr, context);
    } else if (expr instanceof ConvolutionExpression) {
      return _processContext((ConvolutionExpression)expr, context);
    } else if (expr instanceof DependenceExpression) {
      return _processContext((DependenceExpression)expr, context);
    } else if (expr instanceof SelectExpression) {
      return _processContext((SelectExpression)expr, context);
    } else if (expr instanceof AlphaNode) {
      return _processContext((AlphaNode)expr, context);
    } else if (expr != null) {
      return _processContext(expr, context);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr, context).toString());
    }
  }
}
