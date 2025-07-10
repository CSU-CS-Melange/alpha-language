package alpha.model.transformation;

import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaVisitable;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.IndexExpression;
import alpha.model.JNIFunction;
import alpha.model.MultiArgExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.UnaryExpression;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.issue.AlphaIssue;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AffineFactorizer;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.MapExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Raises up dependence functions through the AST of a given expression.
 * This is done by factorizing dependence functions and bringing the "common factor" terms
 * up through the AST as high as they can be.
 * Some of these rules can be thought of as the opposite of the Normalize rules.
 * 
 * One use case is to expose the maximum amount of reuse, enabling automatic reuse vector selection
 * for the Simlpifying Reductions optimization.
 * 
 * List of Rules:
 * Constant, Variable, and Index Expressions:
 *     These rules effectively just wrap these expressions in an appropriate dependence expression.
 *     Note: for constants and variables, this only applies if it isn't already inside a dependence expression.
 * 
 *     (X) goes to (f @ X) where X is a constant and f is a map from the context domain to a zero-dimensional range.
 *     (X) goes to (f @ X) where X is a variable and f is the identity function.
 *     val[f] goes to f@(val(i->i))
 * 
 * Dependence Expressions:
 *     This rule simply merges nested dependence expressions.
 * 
 *     (f1 @ f2 @ E) goes to (f @ E) where f=f1 @ f2
 * 
 * Restrict Expressions:
 *     This rule pulls a dependence expression outside of a restrict expression.
 *     Note: this does not apply if the restrict expression is the direct child of a reduce expression.
 * 
 *     D:(f@E) goes to f1@(D1: f2@E) where D=Preimage(D1,f1) and f=f1 @ f2
 * 
 * Auto-Restrict Expressions:
 *     The "LiftAutoRestrict" transformation is automatically applied
 *     whenever an Auto-Restrict Expression is found.
 *     This is done during "inCaseExpression", that way they're removed before navigating
 *     the children of the case expression it's in (as all auto-restricts must be inside a case).
 * 
 * Unary Expressions:
 *     This rule simply pulls a dependence function out of the unary operation.
 * 
 *     op(f @ E) goes to f@(op E)
 * 
 * Binary, Multi-Arg, and Case Expressions:
 *     These rules require all children to be dependence expressions.
 *     They factorize the child functions, wrap the parent in a dependence expression of the common factor,
 *     and replace the child dependence expressions with the remaining terms.
 *     Each original function is thus the composition of the common factor and the correct remaining term.
 * 
 *     ((f1 @ E1) op (f2 @ E2)) goes to ((f')@((f1' @ E1) op (f2' @ E2))) where f1 = f' @ f1' and f2 = f' @ f2'
 *     op(f1@E1, f2@E2, ...) goes to (f')@ op(f1'@E1, f2'@E2, ...) where fn = f' @ fn'
 *     case {f1@E1, f2@E2, ...} goes to (f')@ case{f1'@E1, f2'@E2, ...} where fn = f' @ fn'
 * 
 * Reduce Expressions:
 *     The current implementation does not pull dependence expressions out of reduce expressions.
 *     However, the child of any raised dependence expressions in reduction bodies may be separated
 *     into a new variable. This is controlled by the hoistFromReduce flag in apply. If hoistFromReduce
 *     is passed as true, then the following rule is applied.
 * 
 *     reduce(op, f, g@E) goes to reduce(op, f, g@V) where V is a new local variable defined as V=E
 */
@SuppressWarnings("all")
public class RaiseDependence extends AbstractAlphaCompleteVisitor {
  /**
   * Dependence expressions raised in the body of a reduction may be hoisted into a
   * separate equation. This flag controls when to do this. See outReduceExpression
   * and reduceExpressionRules.
   */
  private final boolean hoistFromReduce;

  /**
   * Protected constructor to restrict access to the instance methods.
   */
  protected RaiseDependence(final boolean hoistFromReduce) {
    this.hoistFromReduce = hoistFromReduce;
  }

  /**
   * Applies dependence raising to the AST of the given visitable expression.
   */
  public static void apply(final AlphaExpressionVisitable visitable) {
    RaiseDependence.apply(visitable, false);
  }

  public static void apply(final AlphaExpressionVisitable visitable, final boolean hoistFromReduce) {
    new RaiseDependence(hoistFromReduce).accept(visitable);
  }

  /**
   * Applies dependence raising to the AST of the given visitable object (system).
   */
  public static void apply(final AlphaVisitable av) {
    RaiseDependence.apply(av, false);
  }

  public static void apply(final AlphaVisitable av, final boolean hoistFromReduce) {
    new RaiseDependence(hoistFromReduce).accept(av);
  }

  /**
   * Wraps a constant expression in a dependence expression,
   * assuming it's not already wrapped.
   * 
   * From:  X
   * To:    f @ X
   * Where: X is a constant, and f is a map from the context domain to a zero-dimensional range.
   */
  public void outConstantExpression(final ConstantExpression ce) {
    EObject _eContainer = ce.eContainer();
    if ((_eContainer instanceof DependenceExpression)) {
      return;
    }
    final DependenceExpression toEmpty = AlphaUserFactory.createDependenceExpression(ISLMultiAff.buildZero(ce.getContextDomain().getSpace()));
    EcoreUtil.replace(ce, toEmpty);
    toEmpty.setExpr(ce);
    AlphaInternalStateConstructor.recomputeContextDomain(toEmpty);
  }

  /**
   * Wraps a variable expression in a dependence expression,
   * assuming it's not already wrapped.
   * 
   * From:  X
   * To:    f @ X
   * Where: f is the identity function
   */
  public void outVariableExpression(final VariableExpression ve) {
    EObject _eContainer = ve.eContainer();
    if ((_eContainer instanceof DependenceExpression)) {
      return;
    }
    final DependenceExpression identity = AlphaUserFactory.createDependenceExpression(ISLMultiAff.buildIdentity(ISLSpace.idMapDimFromSetDim(ve.getContextDomain().getSpace())));
    EcoreUtil.replace(ve, identity);
    identity.setExpr(ve);
    AlphaInternalStateConstructor.recomputeContextDomain(identity);
  }

  /**
   * Moves the affine map outside of an index ("val") expression.
   * Since an index expression requires an affine function,
   * the new function is just the identity.
   * 
   * From: val(f)
   * To:   f @ (val(i->i))
   */
  public void outIndexExpression(final IndexExpression ie) {
    final IndexExpression identityIndex = AlphaUserFactory.createIndexExpression(ISLMultiAff.buildIdentity(ISLSpace.idMapDimFromSetDim(ie.getFunction().getSpace().range())));
    final DependenceExpression wrappingDependence = AlphaUserFactory.createDependenceExpression(ie.getFunction(), identityIndex);
    EcoreUtil.replace(ie, wrappingDependence);
    AlphaInternalStateConstructor.recomputeContextDomain(wrappingDependence);
  }

  /**
   * Applies the binary expression rules.
   */
  public void outDependenceExpression(final DependenceExpression de) {
    this.dependenceExpressionRule(de, de.getExpr());
  }

  /**
   * Merge nested dependence expressions.
   * 
   * From:  f1 @ (f2 @ E)
   * To:    f @ E
   * Where: f = f1 @ f2
   */
  protected List<AlphaIssue> _dependenceExpressionRule(final DependenceExpression outerDe, final DependenceExpression innerDe) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final ISLMultiAff f1 = outerDe.getFunction();
      final ISLMultiAff f2 = innerDe.getFunction();
      final ISLMultiAff f = f2.pullback(f1);
      final DependenceExpression newDe = AlphaUserFactory.createDependenceExpression(f, innerDe.getExpr());
      EcoreUtil.replace(outerDe, newDe);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(newDe);
    }
    return _xblockexpression;
  }

  /**
   * No matching dependence expression rule: do nothing.
   */
  protected List<AlphaIssue> _dependenceExpressionRule(final DependenceExpression de, final AlphaExpression inner) {
    return null;
  }

  /**
   * Applies the restrict expression rules.
   */
  public void outRestrictExpression(final RestrictExpression re) {
    this.restrictExpressionRule(re, re.getExpr());
  }

  /**
   * Pulls a dependence expression outside of a restrict expression.
   * 
   * From:  D:(f@E)
   * To:    f1@(D1: f2@E)
   * Where: D=Preimage(D1,f1) and f=f1 @ f2
   */
  protected List<AlphaIssue> _restrictExpressionRule(final RestrictExpression re, final DependenceExpression de) {
    List<AlphaIssue> _xblockexpression = null;
    {
      EObject _eContainer = re.eContainer();
      if ((_eContainer instanceof ReduceExpression)) {
        return CollectionLiterals.<AlphaIssue>newArrayList();
      }
      final Function1<ISLBasicSet, List<ISLConstraint>> _function = new Function1<ISLBasicSet, List<ISLConstraint>>() {
        public List<ISLConstraint> apply(final ISLBasicSet it) {
          return it.getConstraints();
        }
      };
      final Function1<ISLConstraint, ISLMultiAff> _function_1 = new Function1<ISLConstraint, ISLMultiAff>() {
        public ISLMultiAff apply(final ISLConstraint it) {
          return it.getAff().toMultiAff();
        }
      };
      final ArrayList<ISLMultiAff> toFactorize = CommonExtensions.<ISLMultiAff>toArrayList(IterableExtensions.<ISLConstraint, ISLMultiAff>map(IterableExtensions.<ISLBasicSet, ISLConstraint>flatMap(re.getRestrictDomain().getBasicSets(), _function), _function_1));
      final ISLMultiAff dependenceFunction = de.getFunction();
      toFactorize.add(dependenceFunction);
      final Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> factorizationResult = AffineFactorizer.factorizeExpressions(((ISLMultiAff[])Conversions.unwrapArray(toFactorize, ISLMultiAff.class)));
      final ISLMultiAff remainingDependence = factorizationResult.getValue().get(dependenceFunction);
      de.setFunctionExpr(AlphaUserFactory.createJNIFunction(remainingDependence));
      final ISLMultiAff commonFactor = factorizationResult.getKey();
      final ISLSet updatedDomain = re.getRestrictDomain().apply(commonFactor.copy().toMap());
      re.setDomainExpr(AlphaUserFactory.createJNIDomain(updatedDomain));
      final DependenceExpression wrappingDependence = AlphaUserFactory.createDependenceExpression(commonFactor);
      EcoreUtil.replace(re, wrappingDependence);
      wrappingDependence.setExpr(re);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(wrappingDependence);
    }
    return _xblockexpression;
  }

  /**
   * No matching restrict expression rule: do nothing.
   */
  protected List<AlphaIssue> _restrictExpressionRule(final RestrictExpression re, final AlphaExpression expr) {
    return null;
  }

  /**
   * Applies the unary expression rules.
   */
  public void outUnaryExpression(final UnaryExpression ue) {
    this.unaryExpressionRule(ue, ue.getExpr());
  }

  /**
   * Pull up the dependence expressions within a unary expression.
   * 
   * From:  op (f @ E)
   * To:    f @ (op E)
   */
  protected List<AlphaIssue> _unaryExpressionRule(final UnaryExpression ue, final DependenceExpression de) {
    List<AlphaIssue> _xblockexpression = null;
    {
      EcoreUtil.replace(de, de.getExpr());
      EcoreUtil.replace(ue, de);
      de.setExpr(ue);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(de);
    }
    return _xblockexpression;
  }

  /**
   * No matching unary expression rule: do nothing.
   */
  protected List<AlphaIssue> _unaryExpressionRule(final UnaryExpression ue, final AlphaExpression expr) {
    return null;
  }

  /**
   * Applies the binary expression rules.
   */
  public void outBinaryExpression(final BinaryExpression be) {
    this.binaryExpressionRule(be, be.getLeft(), be.getRight());
  }

  /**
   * Pull out a common factor from dependence expressions within a binary expression.
   * 
   * From:  f1 @ E1 op f2 @ E2
   * To:    (f')@((f1' @ E1) op (f2' @ E2))
   * Where: f1 = f' @ f1' and f2 = f' @ f2'
   */
  protected List<AlphaIssue> _binaryExpressionRule(final BinaryExpression be, final DependenceExpression left, final DependenceExpression right) {
    return RaiseDependence.factorizeChildDependences(be, left, right);
  }

  /**
   * No matching binary expression rule: do nothing.
   */
  protected List<AlphaIssue> _binaryExpressionRule(final BinaryExpression be, final AlphaExpression left, final AlphaExpression right) {
    return null;
  }

  /**
   * Pull out a common factor from dependence expressions within a multi-arg expression.
   * 
   * From:  op (f1@E1, f2@E2, ...)
   * To:    (f')@ op(f1'@E1, f2'@E2, ...)
   * Where: fn = f' @ fn'
   */
  public void outMultiArgExpression(final MultiArgExpression me) {
    final EList<AlphaExpression> children = me.getExprs();
    final Function1<AlphaExpression, Boolean> _function = new Function1<AlphaExpression, Boolean>() {
      public Boolean apply(final AlphaExpression child) {
        return Boolean.valueOf((child instanceof DependenceExpression));
      }
    };
    boolean _forall = IterableExtensions.<AlphaExpression>forall(children, _function);
    if (_forall) {
      final Function1<AlphaExpression, DependenceExpression> _function_1 = new Function1<AlphaExpression, DependenceExpression>() {
        public DependenceExpression apply(final AlphaExpression child) {
          return ((DependenceExpression) child);
        }
      };
      RaiseDependence.factorizeChildDependences(me, ((DependenceExpression[])Conversions.unwrapArray(ListExtensions.<AlphaExpression, DependenceExpression>map(children, _function_1), DependenceExpression.class)));
    }
  }

  /**
   * Applies the <code>LiftAutoRestrict</code> transformation if needed.
   * This is done when going into a case statement so that the Auto-Restrict Expressions
   * are removed prior to visiting the children.
   */
  public void inCaseExpression(final CaseExpression ce) {
    final Function1<AlphaExpression, Boolean> _function = new Function1<AlphaExpression, Boolean>() {
      public Boolean apply(final AlphaExpression child) {
        return Boolean.valueOf((child instanceof AutoRestrictExpression));
      }
    };
    final boolean hasAutoRestrict = IterableExtensions.<AlphaExpression>exists(ce.getExprs(), _function);
    if (hasAutoRestrict) {
      LiftAutoRestrict.apply(ce);
    }
  }

  /**
   * Pull out a common factor from dependence expressions within a case expression.
   * 
   * From:  case {f1@E1, f2@E2, ...}
   * To:    (f')@ case{f1'@E1, f2'@E2, ...}
   * Where: fn = f' @ fn'
   */
  public void outCaseExpression(final CaseExpression ce) {
    final EList<AlphaExpression> children = ce.getExprs();
    final Function1<AlphaExpression, Boolean> _function = new Function1<AlphaExpression, Boolean>() {
      public Boolean apply(final AlphaExpression child) {
        return Boolean.valueOf((child instanceof DependenceExpression));
      }
    };
    boolean _forall = IterableExtensions.<AlphaExpression>forall(children, _function);
    if (_forall) {
      final Function1<AlphaExpression, DependenceExpression> _function_1 = new Function1<AlphaExpression, DependenceExpression>() {
        public DependenceExpression apply(final AlphaExpression child) {
          return ((DependenceExpression) child);
        }
      };
      RaiseDependence.factorizeChildDependences(ce, ((DependenceExpression[])Conversions.unwrapArray(ListExtensions.<AlphaExpression, DependenceExpression>map(children, _function_1), DependenceExpression.class)));
    }
  }

  /**
   * Factorizes a set of dependence expressions which are the direct children of some parent.
   * 
   * From:  parent(f1@E1, f2@E2, ...)
   * To:    (f')@(parent(f1'@E1, f2'@E2, ...))
   * Where: fn = f' @ fn'
   */
  protected static List<AlphaIssue> factorizeChildDependences(final AlphaExpression parent, final DependenceExpression... children) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final Function1<DependenceExpression, JNIFunction> _function = new Function1<DependenceExpression, JNIFunction>() {
        public JNIFunction apply(final DependenceExpression child) {
          return child.getFunctionExpr();
        }
      };
      final Function1<JNIFunction, ISLMultiAff> _function_1 = new Function1<JNIFunction, ISLMultiAff>() {
        public ISLMultiAff apply(final JNIFunction expr) {
          return expr.getISLMultiAff();
        }
      };
      final HashMap<JNIFunction, ISLMultiAff> dependenceExpressionToFunction = CommonExtensions.<JNIFunction, ISLMultiAff>toHashMap(IterableExtensions.<JNIFunction, ISLMultiAff>toInvertedMap(ListExtensions.<DependenceExpression, JNIFunction>map(((List<DependenceExpression>)Conversions.doWrapArray(children)), _function), _function_1));
      final Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> factorizationResult = AffineFactorizer.factorizeExpressions(((ISLMultiAff[])Conversions.unwrapArray(dependenceExpressionToFunction.values(), ISLMultiAff.class)));
      final ISLMultiAff commonFactor = factorizationResult.getKey();
      final Function1<ISLMultiAff, JNIFunction> _function_2 = new Function1<ISLMultiAff, JNIFunction>() {
        public JNIFunction apply(final ISLMultiAff multiAff) {
          return AlphaUserFactory.createJNIFunction(multiAff);
        }
      };
      final Map<ISLMultiAff, JNIFunction> remainingTermsMap = MapExtensions.<ISLMultiAff, ISLMultiAff, JNIFunction>mapValues(factorizationResult.getValue(), _function_2);
      final Function1<ISLMultiAff, JNIFunction> _function_3 = new Function1<ISLMultiAff, JNIFunction>() {
        public JNIFunction apply(final ISLMultiAff originalTerm) {
          return remainingTermsMap.get(originalTerm);
        }
      };
      final BiConsumer<JNIFunction, JNIFunction> _function_4 = new BiConsumer<JNIFunction, JNIFunction>() {
        public void accept(final JNIFunction original, final JNIFunction replacement) {
          EcoreUtil.replace(original, replacement);
        }
      };
      MapExtensions.<JNIFunction, ISLMultiAff, JNIFunction>mapValues(dependenceExpressionToFunction, _function_3).forEach(_function_4);
      final DependenceExpression wrappingDependence = AlphaUserFactory.createDependenceExpression(commonFactor);
      EcoreUtil.replace(parent, wrappingDependence);
      wrappingDependence.setExpr(parent);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(wrappingDependence);
    }
    return _xblockexpression;
  }

  /**
   * Separate the child of a top level dependence expression in the reduction body if hoisting
   * is specified.
   */
  public void outReduceExpression(final ReduceExpression re) {
    if (this.hoistFromReduce) {
      this.reduceExpressionRules(re, re.getBody());
    }
  }

  protected List<AlphaIssue> _reduceExpressionRules(final ReduceExpression reduce, final RestrictExpression restrict) {
    try {
      List<AlphaIssue> _xblockexpression = null;
      {
        AlphaExpression _expr = restrict.getExpr();
        boolean _not = (!(_expr instanceof DependenceExpression));
        if (_not) {
          throw new Exception("After dependence raising, we did not find a dependence expression we expected. Something is wrong.");
        }
        AlphaExpression _expr_1 = restrict.getExpr();
        final DependenceExpression de = ((DependenceExpression) _expr_1);
        this.isolate(de);
        _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(reduce);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Pull out a common factor from dependence expressions within a case expression.
   * 
   * From:  reduce(op, f, g@E)
   * To:    reduce(op, f, g@V)
   * Where: V is a new local variable, V=E, defined as over the context domain of E
   */
  protected List<AlphaIssue> _reduceExpressionRules(final ReduceExpression re, final DependenceExpression de) {
    List<AlphaIssue> _xblockexpression = null;
    {
      this.isolate(de);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(re);
    }
    return _xblockexpression;
  }

  public List<AlphaIssue> isolate(final DependenceExpression de) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final ISLSet domain = de.getExpr().getContextDomain().computeDivs();
      Equation _containerEquation = AlphaUtil.getContainerEquation(de);
      final String varName = ((StandardEquation) _containerEquation).getVariable().getName();
      final Variable variable = AlphaUserFactory.createVariable((varName + "_body"), domain.copy());
      EList<Variable> _locals = AlphaUtil.getContainerSystem(de).getLocals();
      _locals.add(variable);
      final StandardEquation eq = AlphaUserFactory.createStandardEquation(variable, de.getExpr());
      EList<Equation> _equations = AlphaUtil.getContainerSystemBody(de).getEquations();
      _equations.add(eq);
      final VariableExpression ve = AlphaUserFactory.createVariableExpression(variable);
      de.setExpr(ve);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(eq);
    }
    return _xblockexpression;
  }

  protected List<AlphaIssue> _reduceExpressionRules(final ReduceExpression re, final AlphaExpression ae) {
    return null;
  }

  protected List<AlphaIssue> dependenceExpressionRule(final DependenceExpression outerDe, final AlphaExpression innerDe) {
    if (innerDe instanceof DependenceExpression) {
      return _dependenceExpressionRule(outerDe, (DependenceExpression)innerDe);
    } else if (innerDe != null) {
      return _dependenceExpressionRule(outerDe, innerDe);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(outerDe, innerDe).toString());
    }
  }

  protected List<AlphaIssue> restrictExpressionRule(final RestrictExpression re, final AlphaExpression de) {
    if (de instanceof DependenceExpression) {
      return _restrictExpressionRule(re, (DependenceExpression)de);
    } else if (de != null) {
      return _restrictExpressionRule(re, de);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re, de).toString());
    }
  }

  protected List<AlphaIssue> unaryExpressionRule(final UnaryExpression ue, final AlphaExpression de) {
    if (de instanceof DependenceExpression) {
      return _unaryExpressionRule(ue, (DependenceExpression)de);
    } else if (de != null) {
      return _unaryExpressionRule(ue, de);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(ue, de).toString());
    }
  }

  protected List<AlphaIssue> binaryExpressionRule(final BinaryExpression be, final AlphaExpression left, final AlphaExpression right) {
    if (left instanceof DependenceExpression
         && right instanceof DependenceExpression) {
      return _binaryExpressionRule(be, (DependenceExpression)left, (DependenceExpression)right);
    } else if (left != null
         && right != null) {
      return _binaryExpressionRule(be, left, right);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(be, left, right).toString());
    }
  }

  public List<AlphaIssue> reduceExpressionRules(final ReduceExpression re, final AlphaExpression de) {
    if (de instanceof DependenceExpression) {
      return _reduceExpressionRules(re, (DependenceExpression)de);
    } else if (de instanceof RestrictExpression) {
      return _reduceExpressionRules(re, (RestrictExpression)de);
    } else if (de != null) {
      return _reduceExpressionRules(re, de);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re, de).toString());
    }
  }
}
