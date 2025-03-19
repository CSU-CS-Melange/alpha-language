package alpha.model.transformation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaCompleteVisitable;
import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.CaseExpression;
import alpha.model.ConvolutionExpression;
import alpha.model.DependenceExpression;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.ModelPackage;
import alpha.model.MultiArgExpression;
import alpha.model.RestrictExpression;
import alpha.model.UnaryExpression;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.issue.AlphaIssue;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaExpressionUtil;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import alpha.model.util.Show;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Normalization of Alpha programs.
 * 
 * Each StandardEquation in an Alpha program should satisfy the following in its normal form:
 *   - the parent of CaseExpression should be StandardEquation or ReduceExpression
 *   - the parent of RestrictExpression should be StandarEquation, ReduceExpression, or CaseExpression
 *   - the parent of VariableExpression should be DependenceExpression
 *   - the child of DependenceExpression should be VariableExpression or ConstantExpression
 * 
 * The same applies to each input expression in an UseEquation.
 * 
 * The normalization rules defined and implemented here should transform any input Alpha program into its normal form.
 * The rules have mainly two objectives:
 *   - DependenceExpressions are propagated downwards
 *   - RestrictExpressions are propagated upwards (except when its child is a CaseExpression)
 * 
 * Due to the direction of propagation, most rules for dependences matches with dependence as the parent,
 * but many rules involving restrict matches with other expressions as parents (e.g., binOp or unaryOp).
 * 
 * Note that the implementation in this class may not be complete yet, especially w.r.t. the new expressions.
 * In addition, this implementation do not use term-rewriting, and relies on bottom-up traversal
 * of the AST with occasional repeated traversal of sub-trees. This may create situations where multiple
 * calls are required to reach the normal form (which is a bug and should be fixed). Some of the rules
 * include revisiting of the sub-trees, which is probably not done optimally. It is tricky to avoid
 * concurrent modifications and violations of containment in EMF. In some cases, some nodes in the AST
 * that are no longer valid due to expressions moving around are visited, which is caught by the validity check
 * at the beginning of outXXX methods for each expression.
 * 
 * 
 * There is also a two-level notion of the normal form resulting from the extensions to Alpha
 * The extensions are mostly aimed at improving the readability of Alpha programs by adding short-hands for
 * describing polyhedral objects and adding structure. The "shallow" normalize, which is the default behavior,
 * keeps most of the extended syntax elements intact. Specifically:
 *  - uses of polyhedral objects defined at the top of a system are preserved
 *  - CalculatorExpressions in equations are also kept
 *  - named CaseExpressions are not flattened
 *  - AutoRestrictExpressions are preserved
 * 
 * The "deep" normalize will remove the extended syntax by replacing polyhedral objects and auto keywords
 *  with explicit (and self-contained) polyhedral domains/functions. CaseExpressions are completely flattened
 *  and names are removed. This form is suited for compatibility with AlphaZ@CSU and
 *  for back-end processing (e.g., code generator) where readability is not important.
 */
@SuppressWarnings("all")
public class Normalize extends AbstractAlphaCompleteVisitor {
  /**
   * Rules collected from AlphaZ implementation using Tom/Gom
   *  Removal/creation of identity functions and empty case branches were
   *  implemented separately.
   * 
   *  debug("rule1", "dep@(A op B) -> dep@A op dep@B", "");
   *  debug("rule2a", "((dom : A) op B) -> dom : (A op B)", "");
   *  debug("rule2b", "(A op (dom : B)) -> dom : (A op B)", "");
   *  debug("rule3", "depA@depB@E -> (depB o depA)@E", "");
   *  debug("rule4", "domA : domB : E -> (domA intersection domB)@E", "");
   *  debug("rule5", "dep@(dom : E)-> dom.preimage(dep) : (dep@E)", "");
   *  debug("rule6", "dep@(op E)-> op (dep@E)", "");
   *  debug("rule7", "(op dom : E)-> dom : (op E)", "");
   *  debug("rule8a", "dep @ f(op, E)-> f(op, dep@E)", "");
   *  debug("rule8b", "dep @ exf(op, E)-> exf(op, dep@E)", "");
   *  debug("rule9", "dep@(val(f) -> val(f o dep)", "");
   * 	debug("rule10", "case E1; case E2 esac; E3; esac -> case E1; E2; E3; esac", "");
   * 	debug("rule11a", "case E1; esac op E2 -> case E1 op E2; esac", "");
   * 	debug("rule11b", "E1 op case E2; esac -> case E1 op E2; esac", "");
   * 	debug("rule12", "op case E; esac -> case op E; esac", "");
   * 	debug("rule13", "f(op, case E; esac) -> case f(op, E) esac;", "");
   *  debug("rule14", "dep case E; esac -> case dep E; esac", "");
   * 	debug("rule15", "dom : case E; esac -> case dom : E; esac", "");
   * 	debug("rule16", "dep @ if (cond, then, else) -> if(dep@cond, dep@then, dep@else) ", "");
   * 	debug("rule17a", "if(dom : cond, then, else) -> dom : if (cond, then, else)", "");
   * 	debug("rule17b", "if(cond, dom : then, else) -> dom : if (cond, then, else)", "");
   * 	debug("rule17c", "if(cond, then, dom : else) -> dom : if (cond, then, else)", "");
   * 	debug("rule18a", "if(case E; esac, then, else) -> esac if (E, then, else); case", "");
   * 	debug("rule18b", "if(cond, case E; esac, else) -> esac if (cond, E, else); case", "");
   * 	debug("rule18c", "if(cond, then, case E; esac) -> esac if (cond, then, E); case", "");
   * 	debug("rule19", "exFunc(op, dom : E) -> dom : exFunc(op, E)", "");
   * 
   * New Rule:
   *   reduce(op1, f1, D : reduce(op2, f2, E)) -> reduce(op1, f1, reduce(op2, f2, f2^-1(D) : E))
   *      - this rule was added to expose nested reductions without restrict in between;
   *        restrict cannot be pushed out from reductions in general, and restrict are
   *        usually not pushed downwards. This rule is an exception.
   * 
   * Rules for New Syntax:
   *   D : auto : E -> auto : E
   *   auto : auto : E -> auto : E
   *   auto : D : E -> auto : E
   *   f @ conv (kernel, weight, data) -> conv(kernel, f' @ weight, f' @ data) (only when f is bijective)
   * 
   * Additional Rules:
   *  - remove restrict expression when it is redundant (expression domain is unchanged by the restrict)
   *  - remove branches of case expressions that have empty context domain
   *  - replaces case with its child if it only has a branch
   *  - inserts identity dependence expression where implied
   */
  private final boolean DEEP;

  public static boolean DEBUG = false;

  protected String debug(final String ruleID, final String rule) {
    String _xifexpression = null;
    if (Normalize.DEBUG) {
      _xifexpression = InputOutput.<String>println(((ruleID + ": ") + rule));
    }
    return _xifexpression;
  }

  protected String debug(final AlphaExpression expr) {
    String _xifexpression = null;
    if (Normalize.DEBUG) {
      _xifexpression = InputOutput.<String>println(Show.<AlphaSystem>print(AlphaUtil.getContainerSystem(expr)));
    }
    return _xifexpression;
  }

  /**
   * Default constructor uses shallow normalize
   */
  protected Normalize() {
    this.DEEP = false;
  }

  protected Normalize(final boolean isDeepNormalize) {
    this.DEEP = isDeepNormalize;
  }

  public static void apply(final AlphaCompleteVisitable acv) {
    Normalize.apply(acv, false);
    AlphaInternalStateConstructor.recomputeContextDomain(acv);
  }

  public static void apply(final AlphaCompleteVisitable acv, final boolean isDeepNormalize) {
    if ((acv instanceof AlphaVisitable)) {
      Normalize.apply(((AlphaVisitable) acv), isDeepNormalize);
    } else {
      if ((acv instanceof AlphaExpressionVisitable)) {
        Normalize.apply(((AlphaExpressionVisitable) acv), isDeepNormalize);
      } else {
        throw new RuntimeException(("This should be unreachable code. Got: " + acv));
      }
    }
  }

  public static void apply(final AlphaVisitable av) {
    Normalize.apply(av, false);
  }

  public static void apply(final AlphaVisitable av, final boolean isDeepNormalize) {
    final Normalize visitor = new Normalize(isDeepNormalize);
    av.accept(visitor);
  }

  public static void apply(final AlphaExpressionVisitable aev) {
    Normalize.apply(aev, false);
  }

  public static void apply(final AlphaExpressionVisitable aev, final boolean isDeepNormalize) {
    final Normalize visitor = new Normalize(isDeepNormalize);
    aev.accept(visitor);
  }

  private void _reapply(final AlphaVisitable av) {
    this.accept(av);
  }

  private void _reapply(final AlphaExpressionVisitable aev) {
    this.accept(aev);
  }

  private boolean invalidState(final EObject obj) {
    EObject _eContainer = obj.eContainer();
    return (_eContainer == null);
  }

  @Override
  public void outDependenceExpression(final DependenceExpression de) {
    boolean _invalidState = this.invalidState(de);
    if (_invalidState) {
      return;
    }
    this.dependenceExpressionRules(de, de.getExpr());
    if ((ISLUtil.isNoneToNone(de.getFunction()) || de.getFunction().isIdentity())) {
      AlphaExpression _expr = de.getExpr();
      if ((_expr instanceof VariableExpression)) {
        return;
      }
      this.debug("identity", "f @ E = E if f = I");
      EcoreUtil.replace(de, de.getExpr());
    }
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final DependenceExpression innerDep) {
    String _xblockexpression = null;
    {
      this.debug("composition", "f1 @ f2 @ E -> (f2 o f1) @ E");
      final ISLMultiAff composedF = innerDep.getFunction().pullback(de.getFunction());
      de.setFunctionExpr(AlphaUserFactory.createJNIFunction(composedF));
      EcoreUtil.replace(de.getExpr(), innerDep.getExpr());
      _xblockexpression = this.debug(de);
    }
    return _xblockexpression;
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final IndexExpression innerIE) {
    String _xblockexpression = null;
    {
      this.debug("composition-val", "f1 @ val(f2) -> val(f2 o f1)");
      final ISLMultiAff composedF = innerIE.getFunction().pullback(de.getFunction());
      innerIE.setFunctionExpr(AlphaUserFactory.createJNIFunction(composedF));
      EcoreUtil.replace(de, innerIE);
      _xblockexpression = this.debug(innerIE);
    }
    return _xblockexpression;
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final RestrictExpression innerRE) {
    this.debug("preimage", "f @ D : E -> f-1(D) : f@E");
    final ISLSet preimage = innerRE.getRestrictDomain().preimage(de.getFunction());
    innerRE.setDomainExpr(AlphaUserFactory.createJNIDomain(preimage));
    innerRE.setExpr(AlphaUserFactory.createDependenceExpression(de.getFunction(), innerRE.getExpr()));
    EcoreUtil.replace(de, innerRE);
    this.debug(innerRE);
    AlphaInternalStateConstructor.recomputeContextDomain(innerRE);
    this.reapply(innerRE);
    return null;
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final BinaryExpression binExpr) {
    this.debug("push-dep BinExpr", "f @ (A op B) -> (f @ A op f @ B)");
    EcoreUtil.replace(de, binExpr);
    final DependenceExpression newL = AlphaUserFactory.createDependenceExpression(de.getFunction(), binExpr.getLeft());
    final DependenceExpression newR = AlphaUserFactory.createDependenceExpression(de.getFunction(), binExpr.getRight());
    binExpr.setLeft(newL);
    binExpr.setRight(newR);
    this.debug(binExpr);
    AlphaInternalStateConstructor.recomputeContextDomain(binExpr);
    this.reapply(binExpr);
    return null;
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final UnaryExpression ue) {
    this.debug("push-dep UnaryExpr", "f @ (op E) -> (op f@E)");
    EcoreUtil.replace(de, ue);
    final DependenceExpression newExpr = AlphaUserFactory.createDependenceExpression(de.getFunction(), ue.getExpr());
    ue.setExpr(newExpr);
    this.debug(ue);
    AlphaInternalStateConstructor.recomputeContextDomain(ue);
    this.reapply(ue);
    return null;
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final MultiArgExpression mae) {
    this.debug("push-dep MAExpr", "f @ (op E) -> (op f@E)");
    this.propagateDownwards(de, mae);
    this.debug(mae);
    this.reapply(mae);
    return null;
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final CaseExpression ce) {
    this.debug("push-dep CaseExpr", "f @ (op E) -> (op f@E)");
    this.propagateDownwards(de, ce);
    this.debug(ce);
    AlphaInternalStateConstructor.recomputeContextDomain(ce);
    this.reapply(ce);
    return null;
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final IfExpression ie) {
    this.debug("push-dep IfExpr", "f @ if (E1, E2, E3) -> if (f@E1, f@E2, f@E3)");
    EcoreUtil.replace(de, ie);
    final DependenceExpression newC = AlphaUserFactory.createDependenceExpression(de.getFunction(), ie.getCondExpr());
    final DependenceExpression newT = AlphaUserFactory.createDependenceExpression(de.getFunction(), ie.getThenExpr());
    final DependenceExpression newE = AlphaUserFactory.createDependenceExpression(de.getFunction(), ie.getElseExpr());
    ie.setCondExpr(newC);
    ie.setThenExpr(newT);
    ie.setElseExpr(newE);
    this.debug(ie);
    AlphaInternalStateConstructor.recomputeContextDomain(ie);
    this.reapply(ie);
    return null;
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final ConvolutionExpression ce) {
    boolean _isBijective = de.getFunction().toMap().isBijective();
    boolean _not = (!_isBijective);
    if (_not) {
      return "";
    }
    this.debug("push-dep ConvExpr", "f @ conv (kernel, weight, data) -> conv(kernel, f\' @ weight, f\' @ data)");
    EcoreUtil.replace(de, ce);
    final ISLMultiAff newMaff = AlphaExpressionUtil.extendMultiAffWithIdentityDimensions(de.getFunction(), ce.getKernelDomain().getNbIndices());
    final DependenceExpression newKernelExpr = AlphaUserFactory.createDependenceExpression(newMaff.copy(), ce.getKernelExpression());
    final DependenceExpression newDataExpr = AlphaUserFactory.createDependenceExpression(newMaff, ce.getDataExpression());
    ce.setKernelExpression(newKernelExpr);
    ce.setDataExpression(newDataExpr);
    this.debug(ce);
    AlphaInternalStateConstructor.recomputeContextDomain(ce);
    this.reapply(ce);
    return null;
  }

  protected String _dependenceExpressionRules(final DependenceExpression de, final AlphaExpression expr) {
    return null;
  }

  public static boolean childOfCaseExpression(final AlphaExpression expr) {
    EObject _eContainer = expr.eContainer();
    return (_eContainer instanceof CaseExpression);
  }

  public static boolean childOfRestrictExpression(final AlphaExpression expr) {
    EObject _eContainer = expr.eContainer();
    return (_eContainer instanceof RestrictExpression);
  }

  @Override
  public void outRestrictExpression(final RestrictExpression re) {
    boolean _invalidState = this.invalidState(re);
    if (_invalidState) {
      return;
    }
    this.restrictExpressionRules(re, re.getExpr());
    boolean _invalidState_1 = this.invalidState(re);
    if (_invalidState_1) {
      return;
    }
    AlphaInternalStateConstructor.recomputeContextDomain(re);
    if (((re.getExpressionDomain().isEqual(re.getExpr().getExpressionDomain()) && (!Normalize.childOfCaseExpression(re))) && (!Normalize.childOfRestrictExpression(re)))) {
      this.debug("redundant restrict", "D : E -> E");
      EcoreUtil.replace(re, re.getExpr());
    }
  }

  protected String _restrictExpressionRules(final RestrictExpression re, final RestrictExpression innerRE) {
    String _xblockexpression = null;
    {
      this.debug("merge restrict", "D1 : D2 : E -> (D1 and D2) : E");
      final ISLSet intersection = re.getRestrictDomain().intersect(innerRE.getRestrictDomain());
      re.setDomainExpr(AlphaUserFactory.createJNIDomain(intersection));
      EcoreUtil.replace(re.getExpr(), innerRE.getExpr());
      _xblockexpression = this.debug(re);
    }
    return _xblockexpression;
  }

  protected String _restrictExpressionRules(final RestrictExpression re, final AutoRestrictExpression are) {
    String _xblockexpression = null;
    {
      this.debug("merge auto-restrict", "D : auto : E -> auto : E");
      EcoreUtil.replace(re, are);
      _xblockexpression = this.debug(are);
    }
    return _xblockexpression;
  }

  protected String _restrictExpressionRules(final RestrictExpression re, final CaseExpression ce) {
    this.debug("push restrict", "D : case { E1; E2; ... } -> case { D:E1; D:E2; ... }");
    this.propagateDownwards(re, ce);
    this.debug(ce);
    AlphaInternalStateConstructor.recomputeContextDomain(ce);
    this.reapply(ce);
    return null;
  }

  protected String _restrictExpressionRules(final RestrictExpression re, final AbstractReduceExpression are) {
    EObject _eContainer = re.eContainer();
    if ((_eContainer instanceof AbstractReduceExpression)) {
      this.debug("push restrict", "reduce (op1, f1, D : reduce(op2, f2, E)) -> reduce(op1, f1, reduce(op2, f2, f2^-1(D) : E))");
      final ISLSet preimage = re.getRestrictDomain().preimage(are.getProjection());
      final RestrictExpression restrictExpr = AlphaUserFactory.createRestrictExpression(preimage, are.getBody());
      are.setBody(restrictExpr);
      EcoreUtil.replace(re, are);
      this.debug(are);
      AlphaInternalStateConstructor.recomputeContextDomain(are);
      this.reapply(are);
    }
    return null;
  }

  protected String _restrictExpressionRules(final RestrictExpression re, final AlphaExpression expr) {
    return null;
  }

  @Override
  public void outBinaryExpression(final BinaryExpression be) {
    boolean _invalidState = this.invalidState(be);
    if (_invalidState) {
      return;
    }
    this.binaryExpressionRules(be, be.getLeft(), be.getRight());
  }

  protected void _binaryExpressionRules(final BinaryExpression be, final CaseExpression ceLeft, final CaseExpression ceRight) {
    EObject _eContainer = be.eContainer();
    final AlphaCompleteVisitable origContainer = ((AlphaCompleteVisitable) _eContainer);
    final Function1<AlphaExpression, ISLSet> _function = (AlphaExpression it) -> {
      return it.getContextDomain();
    };
    final Function1<ISLSet, List<ISLSet>> _function_1 = (ISLSet l) -> {
      final Function1<AlphaExpression, ISLSet> _function_2 = (AlphaExpression it) -> {
        return it.getContextDomain();
      };
      final Function1<ISLSet, ISLSet> _function_3 = (ISLSet r) -> {
        return l.copy().intersect(r);
      };
      return ListExtensions.<ISLSet, ISLSet>map(ListExtensions.<AlphaExpression, ISLSet>map(ceRight.getExprs(), _function_2), _function_3);
    };
    final Function1<ISLSet, Boolean> _function_2 = (ISLSet it) -> {
      return Boolean.valueOf(it.isEmpty());
    };
    final Iterable<ISLSet> newBranchDomains = IterableExtensions.<ISLSet>reject(Iterables.<ISLSet>concat(ListExtensions.<ISLSet, List<ISLSet>>map(ListExtensions.<AlphaExpression, ISLSet>map(ceLeft.getExprs(), _function), _function_1)), _function_2);
    final Function1<ISLSet, RestrictExpression> _function_3 = (ISLSet domain) -> {
      return AlphaUserFactory.createRestrictExpression(domain);
    };
    final Iterable<RestrictExpression> emptyRestrictExprs = IterableExtensions.<ISLSet, RestrictExpression>map(newBranchDomains, _function_3);
    final CaseExpression newCE = AlphaUserFactory.createCaseExpression();
    EList<AlphaExpression> _exprs = newCE.getExprs();
    final Function1<RestrictExpression, RestrictExpression> _function_4 = (RestrictExpression re) -> {
      RestrictExpression _xblockexpression = null;
      {
        final Function1<AlphaExpression, Boolean> _function_5 = (AlphaExpression e) -> {
          return Boolean.valueOf(re.getRestrictDomain().isSubset(e.getExpressionDomain()));
        };
        AlphaExpression left = EcoreUtil.<AlphaExpression>copy(IterableExtensions.<AlphaExpression>findFirst(ceLeft.getExprs(), _function_5));
        final Function1<AlphaExpression, Boolean> _function_6 = (AlphaExpression e) -> {
          return Boolean.valueOf(re.getRestrictDomain().isSubset(e.getExpressionDomain()));
        };
        AlphaExpression right = EcoreUtil.<AlphaExpression>copy(IterableExtensions.<AlphaExpression>findFirst(ceRight.getExprs(), _function_6));
        if ((left instanceof RestrictExpression)) {
          left = ((RestrictExpression)left).getExpr();
        }
        if ((right instanceof RestrictExpression)) {
          right = ((RestrictExpression)right).getExpr();
        }
        if (((left != null) && (right != null))) {
          re.setExpr(AlphaUserFactory.createBinaryExpression(be.getOperator(), left, right));
        } else {
          AlphaExpression _xifexpression = null;
          if ((left != null)) {
            _xifexpression = left;
          } else {
            _xifexpression = right;
          }
          re.setExpr(_xifexpression);
        }
        _xblockexpression = re;
      }
      return _xblockexpression;
    };
    Iterable<RestrictExpression> _map = IterableExtensions.<RestrictExpression, RestrictExpression>map(emptyRestrictExprs, _function_4);
    Iterables.<AlphaExpression>addAll(_exprs, _map);
    EcoreUtil.replace(be, newCE);
    AlphaInternalStateConstructor.recomputeContextDomain(newCE);
    this.reapply(origContainer);
  }

  protected void _binaryExpressionRules(final BinaryExpression be, final AlphaExpression aeLeft, final CaseExpression ceRight) {
    EObject _eContainer = be.eContainer();
    final AlphaCompleteVisitable origContainer = ((AlphaCompleteVisitable) _eContainer);
    this.binaryExpressionRules(be, ceRight);
    EObject _eContainer_1 = be.eContainer();
    boolean _notEquals = (!Objects.equal(origContainer, _eContainer_1));
    if (_notEquals) {
      this.reapply(origContainer);
    }
  }

  protected void _binaryExpressionRules(final BinaryExpression be, final AlphaExpression aeLeft, final AlphaExpression aeRight) {
    EObject _eContainer = be.eContainer();
    final AlphaCompleteVisitable origContainer = ((AlphaCompleteVisitable) _eContainer);
    this.binaryExpressionRules(be, aeLeft);
    EObject _eContainer_1 = be.eContainer();
    boolean _notEquals = (!Objects.equal(origContainer, _eContainer_1));
    if (_notEquals) {
      this.reapply(origContainer);
    }
  }

  protected List<AlphaIssue> _binaryExpressionRules(final BinaryExpression be, final RestrictExpression re) {
    List<AlphaIssue> _xblockexpression = null;
    {
      this.debug("pull-restrict BinExpr", "(D : A op B) -> D : (A op B) || (A op D : B) -> D : (A op B)");
      EcoreUtil.replace(re, re.getExpr());
      EcoreUtil.replace(be, re);
      re.setExpr(be);
      this.debug(be);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(be);
    }
    return _xblockexpression;
  }

  protected List<AlphaIssue> _binaryExpressionRules(final BinaryExpression be, final CaseExpression ce) {
    List<AlphaIssue> _xblockexpression = null;
    {
      EReference _eContainmentFeature = ce.eContainmentFeature();
      final boolean isLeft = Objects.equal(_eContainmentFeature, ModelPackage.Literals.BINARY_EXPRESSION__LEFT);
      if ((Normalize.DEBUG && isLeft)) {
        this.debug("pull-case BinExpr", "case { E1; E2; ... } op E -> case { E1 op E; E2 op E; ... }");
      } else {
        this.debug("pull-case BinExpr", "E op case { E1; E2; ... } -> case { E op E1; E op E2; ... }");
      }
      final CaseExpression newCE = AlphaUserFactory.createCaseExpression();
      EcoreUtil.replace(be, newCE);
      int _size = ce.getExprs().size();
      final ArrayList<AlphaExpression> children = new ArrayList<AlphaExpression>(_size);
      final Consumer<AlphaExpression> _function = (AlphaExpression e) -> {
        children.add(e);
      };
      ce.getExprs().forEach(_function);
      if (isLeft) {
        final Consumer<AlphaExpression> _function_1 = (AlphaExpression e) -> {
          newCE.getExprs().add(AlphaUserFactory.createBinaryExpression(be.getOperator(), e, EcoreUtil.<AlphaExpression>copy(be.getRight())));
        };
        children.forEach(_function_1);
      } else {
        final Consumer<AlphaExpression> _function_2 = (AlphaExpression e) -> {
          newCE.getExprs().add(AlphaUserFactory.createBinaryExpression(be.getOperator(), EcoreUtil.<AlphaExpression>copy(be.getLeft()), e));
        };
        children.forEach(_function_2);
      }
      this.debug(newCE);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(newCE);
    }
    return _xblockexpression;
  }

  protected List<AlphaIssue> _binaryExpressionRules(final BinaryExpression be, final AlphaExpression expr) {
    return null;
  }

  @Override
  public void outUnaryExpression(final UnaryExpression ue) {
    boolean _invalidState = this.invalidState(ue);
    if (_invalidState) {
      return;
    }
    this.unaryExpressionRules(ue, ue.getExpr());
  }

  protected String _unaryExpressionRules(final UnaryExpression ue, final RestrictExpression innerRE) {
    String _xblockexpression = null;
    {
      this.debug("pull-restrict UnaryExpr", "(op D : E) -> D : (op E)");
      final AlphaExpression E = innerRE.getExpr();
      EcoreUtil.replace(ue, innerRE);
      innerRE.setExpr(ue);
      ue.setExpr(E);
      _xblockexpression = this.debug(ue);
    }
    return _xblockexpression;
  }

  protected String _unaryExpressionRules(final UnaryExpression ue, final CaseExpression ce) {
    this.debug("pull-case UnaryExpr", "op case { E1; E2; ... }-> case { op E1; op E2; ... }");
    this.propagateDownwards(ue, ce);
    this.debug(ce);
    AlphaInternalStateConstructor.recomputeContextDomain(ce);
    this.reapply(ce);
    return null;
  }

  protected String _unaryExpressionRules(final UnaryExpression ue, final AlphaExpression expr) {
    return null;
  }

  @Override
  public void outMultiArgExpression(final MultiArgExpression mae) {
    boolean _invalidState = this.invalidState(mae);
    if (_invalidState) {
      return;
    }
    for (int i = 0; (i < mae.getExprs().size()); i++) {
      {
        final CaseExpression nextExpr = this.multiArgExpressionRules(mae, mae.getExprs().get(i));
        if ((nextExpr != null)) {
          this.reapply(nextExpr);
          return;
        }
      }
    }
    final Function1<AlphaExpression, Boolean> _function = (AlphaExpression e) -> {
      return Boolean.valueOf((e instanceof RestrictExpression));
    };
    boolean _exists = IterableExtensions.<AlphaExpression>exists(mae.getExprs(), _function);
    if (_exists) {
      this.debug("pull-restrict MAExpr", "f(op, D1 : E1, D2 : E2) -> (D1 and D2 ...) : f(op, E1, E2, ...)");
      final Function1<RestrictExpression, ISLSet> _function_1 = (RestrictExpression e) -> {
        return e.getRestrictDomain();
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_2 = (ISLSet d1, ISLSet d2) -> {
        return d1.intersect(d2);
      };
      ISLSet intersection = IterableExtensions.<ISLSet>reduce(IterableExtensions.<RestrictExpression, ISLSet>map(Iterables.<RestrictExpression>filter(mae.getExprs(), RestrictExpression.class), _function_1), _function_2);
      final RestrictExpression hoistedRE = AlphaUserFactory.createRestrictExpression(intersection);
      EcoreUtil.replace(mae, hoistedRE);
      hoistedRE.setExpr(mae);
      final Consumer<RestrictExpression> _function_3 = (RestrictExpression e) -> {
        EcoreUtil.replace(e, e.getExpr());
      };
      Iterables.<RestrictExpression>filter(mae.getExprs(), RestrictExpression.class).forEach(_function_3);
      this.debug(hoistedRE);
      AlphaInternalStateConstructor.recomputeContextDomain(hoistedRE);
      this.reapply(hoistedRE);
      return;
    }
  }

  protected CaseExpression _multiArgExpressionRules(final MultiArgExpression mae, final CaseExpression ce) {
    final int index = mae.getExprs().indexOf(ce);
    if ((index <= (-1))) {
      throw new RuntimeException("Unexpected case at normalize rule for multi-arg expressions. The child index cannot be found. ");
    }
    this.debug("pull-case MAExpr", "f(op, case { E1; E2; ... }) -> case { f(op, E1); f(op, E2); ...}");
    int _size = ce.getExprs().size();
    final ArrayList<AlphaExpression> children = new ArrayList<AlphaExpression>(_size);
    final Consumer<AlphaExpression> _function = (AlphaExpression e) -> {
      children.add(e);
    };
    ce.getExprs().forEach(_function);
    ce.getExprs().clear();
    final Consumer<AlphaExpression> _function_1 = (AlphaExpression e) -> {
      final MultiArgExpression newMAE = EcoreUtil.<MultiArgExpression>copy(mae);
      EcoreUtil.replace(newMAE.getExprs().get(index), e);
      ce.getExprs().add(newMAE);
    };
    children.forEach(_function_1);
    EcoreUtil.replace(mae, ce);
    this.debug(ce);
    AlphaInternalStateConstructor.recomputeContextDomain(ce);
    return ce;
  }

  protected CaseExpression _multiArgExpressionRules(final MultiArgExpression mae, final AlphaExpression expr) {
    return null;
  }

  @Override
  public void outCaseExpression(final CaseExpression ce) {
    boolean _invalidState = this.invalidState(ce);
    if (_invalidState) {
      return;
    }
    final Predicate<AlphaExpression> _function = (AlphaExpression e) -> {
      return ((e instanceof CaseExpression) && (this.DEEP || (!(((CaseExpression) e).isNamed()).booleanValue())));
    };
    final Predicate<AlphaExpression> canFlatten = _function;
    boolean _exists = IterableExtensions.<AlphaExpression>exists(ce.getExprs(), new Function1<AlphaExpression, Boolean>() {
        public Boolean apply(AlphaExpression p) {
          return canFlatten.test(p);
        }
    });
    if (_exists) {
      this.debug("flatten-case", "case { E1; case { E2; E3; ...}; E4 ...} -> case{ E1; E2; E3; ...; E4; ... }");
      final LinkedList<AlphaExpression> children = new LinkedList<AlphaExpression>();
      final Consumer<AlphaExpression> _function_1 = (AlphaExpression e) -> {
        boolean _test = canFlatten.test(e);
        if (_test) {
          EList<AlphaExpression> _exprs = ((CaseExpression) e).getExprs();
          for (final AlphaExpression expr : _exprs) {
            AlphaExpression _xifexpression = null;
            if ((expr instanceof AutoRestrictExpression)) {
              _xifexpression = LiftAutoRestrict.apply(((AutoRestrictExpression)expr));
            } else {
              _xifexpression = expr;
            }
            children.add(_xifexpression);
          }
          children.addAll(((CaseExpression) e).getExprs());
        } else {
          children.add(e);
        }
      };
      ce.getExprs().forEach(_function_1);
      ce.getExprs().clear();
      ce.getExprs().addAll(children);
      this.debug(ce);
    }
    final LinkedList<AlphaExpression> emptyExprs = new LinkedList<AlphaExpression>();
    final Consumer<AlphaExpression> _function_2 = (AlphaExpression e) -> {
      boolean _isEmpty = e.getContextDomain().isEmpty();
      if (_isEmpty) {
        emptyExprs.add(e);
      }
    };
    ce.getExprs().forEach(_function_2);
    ce.getExprs().removeAll(emptyExprs);
    int _size = ce.getExprs().size();
    boolean _equals = (_size == 1);
    if (_equals) {
      EcoreUtil.replace(ce, ce.getExprs().get(0));
    }
  }

  @Override
  public void outIfExpression(final IfExpression ie) {
    boolean _invalidState = this.invalidState(ie);
    if (_invalidState) {
      return;
    }
    final Function1<AlphaExpression, Boolean> _function = (AlphaExpression e) -> {
      final AlphaExpression nextExpr = this.ifExpressionRules(ie, e);
      if ((nextExpr != null)) {
        this.reapply(nextExpr);
        return true;
      }
      return false;
    };
    final Function1<AlphaExpression, Boolean> rule = _function;
    Boolean _apply = rule.apply(ie.getCondExpr());
    if ((_apply).booleanValue()) {
      return;
    }
    Boolean _apply_1 = rule.apply(ie.getThenExpr());
    if ((_apply_1).booleanValue()) {
      return;
    }
    Boolean _apply_2 = rule.apply(ie.getElseExpr());
    if ((_apply_2).booleanValue()) {
      return;
    }
  }

  protected AlphaExpression _ifExpressionRules(final IfExpression ie, final RestrictExpression re) {
    if (Normalize.DEBUG) {
      EReference _eContainmentFeature = re.eContainmentFeature();
      final boolean isCond = Objects.equal(_eContainmentFeature, ModelPackage.Literals.IF_EXPRESSION__COND_EXPR);
      EReference _eContainmentFeature_1 = re.eContainmentFeature();
      final boolean isThen = Objects.equal(_eContainmentFeature_1, ModelPackage.Literals.IF_EXPRESSION__THEN_EXPR);
      EReference _eContainmentFeature_2 = re.eContainmentFeature();
      final boolean isElse = Objects.equal(_eContainmentFeature_2, ModelPackage.Literals.IF_EXPRESSION__ELSE_EXPR);
      if (isCond) {
        this.debug("pull-restrict IfExpr", "if (D : E1, E2, E3) -> D : if (E1, E2, E3)");
      } else {
        if (isThen) {
          this.debug("pull-restrict IfExpr", "if (E1, D : E2, E3) -> D : if (E1, E2, E3)");
        } else {
          if (isElse) {
            this.debug("pull-restrict IfExpr", "if (E1, E2, D : E3) -> D : if (E1, E2, E3)");
          }
        }
      }
    }
    EcoreUtil.replace(re, re.getExpr());
    EcoreUtil.replace(ie, re);
    re.setExpr(ie);
    this.debug(re);
    AlphaInternalStateConstructor.recomputeContextDomain(re);
    return re;
  }

  protected AlphaExpression _ifExpressionRules(final IfExpression ie, final CaseExpression ce) {
    EReference _eContainmentFeature = ce.eContainmentFeature();
    final boolean isCond = Objects.equal(_eContainmentFeature, ModelPackage.Literals.IF_EXPRESSION__COND_EXPR);
    EReference _eContainmentFeature_1 = ce.eContainmentFeature();
    final boolean isThen = Objects.equal(_eContainmentFeature_1, ModelPackage.Literals.IF_EXPRESSION__THEN_EXPR);
    EReference _eContainmentFeature_2 = ce.eContainmentFeature();
    final boolean isElse = Objects.equal(_eContainmentFeature_2, ModelPackage.Literals.IF_EXPRESSION__ELSE_EXPR);
    if (Normalize.DEBUG) {
      if (isCond) {
        this.debug("pull-case IfExpr", "if (case { E1; E2; ... }, E10, E11) -> case { if (E1, E10, E11); if (E2, E10, E11); ... }");
      } else {
        if (isThen) {
          this.debug("pull-case IfExpr", "if (E10, case { E1; E2; ... }, E11) -> case { if (E10, E1, E11); if (E10, E2, E11); ... }");
        } else {
          if (isElse) {
            this.debug("pull-case IfExpr", "if (E10, E11, case { E1; E2; ... }) -> case { if (E10, E11, E1); if (E10, E11, E2); ... }");
          }
        }
      }
    }
    final CaseExpression newCE = AlphaUserFactory.createCaseExpression();
    EcoreUtil.replace(ie, newCE);
    int _size = ce.getExprs().size();
    final ArrayList<AlphaExpression> children = new ArrayList<AlphaExpression>(_size);
    final Consumer<AlphaExpression> _function = (AlphaExpression e) -> {
      children.add(e);
    };
    ce.getExprs().forEach(_function);
    if (isCond) {
      final Consumer<AlphaExpression> _function_1 = (AlphaExpression e) -> {
        newCE.getExprs().add(AlphaUserFactory.createIfExpression(e, EcoreUtil.<AlphaExpression>copy(ie.getThenExpr()), EcoreUtil.<AlphaExpression>copy(ie.getElseExpr())));
      };
      children.forEach(_function_1);
    } else {
      if (isThen) {
        final Consumer<AlphaExpression> _function_2 = (AlphaExpression e) -> {
          newCE.getExprs().add(AlphaUserFactory.createIfExpression(EcoreUtil.<AlphaExpression>copy(ie.getCondExpr()), e, EcoreUtil.<AlphaExpression>copy(ie.getElseExpr())));
        };
        children.forEach(_function_2);
      } else {
        if (isElse) {
          final Consumer<AlphaExpression> _function_3 = (AlphaExpression e) -> {
            newCE.getExprs().add(AlphaUserFactory.createIfExpression(EcoreUtil.<AlphaExpression>copy(ie.getCondExpr()), EcoreUtil.<AlphaExpression>copy(ie.getThenExpr()), e));
          };
          children.forEach(_function_3);
        }
      }
    }
    this.debug(newCE);
    AlphaInternalStateConstructor.recomputeContextDomain(newCE);
    return newCE;
  }

  protected AlphaExpression _ifExpressionRules(final IfExpression ie, final AlphaExpression expr) {
    return null;
  }

  @Override
  public void outAutoRestrictExpression(final AutoRestrictExpression are) {
    AlphaExpression _expr = are.getExpr();
    if ((_expr instanceof RestrictExpression)) {
      AlphaExpression _expr_1 = are.getExpr();
      EcoreUtil.replace(are.getExpr(), ((RestrictExpression) _expr_1).getExpr());
    }
    AlphaExpression _expr_2 = are.getExpr();
    if ((_expr_2 instanceof AutoRestrictExpression)) {
      AlphaExpression _expr_3 = are.getExpr();
      EcoreUtil.replace(are.getExpr(), ((AutoRestrictExpression) _expr_3).getExpr());
    }
  }

  /**
   * Helper function to propagate dependence/restrict expressions downwards.
   * 
   * It is a recurring pattern for several expressions to take the function/domain
   * in the outer dependence/restrict surrounding case/multi-arg expressions and
   * apply them to all of their children. This method centralizes this operation
   * to avoid redundant code.
   */
  private void propagateDownwards(final AlphaExpression outer, final AlphaExpression inner) {
    EcoreUtil.replace(outer, inner);
    final EList<AlphaExpression> exprsEList = this.getChildrenEList(inner);
    int _size = exprsEList.size();
    final ArrayList<AlphaExpression> children = new ArrayList<AlphaExpression>(_size);
    final Consumer<AlphaExpression> _function = (AlphaExpression e) -> {
      children.add(e);
    };
    exprsEList.forEach(_function);
    exprsEList.clear();
    final Consumer<AlphaExpression> _function_1 = (AlphaExpression e) -> {
      exprsEList.add(this.wrapExpression(outer, e));
    };
    children.forEach(_function_1);
  }

  private EList<AlphaExpression> _getChildrenEList(final CaseExpression ce) {
    return ce.getExprs();
  }

  private EList<AlphaExpression> _getChildrenEList(final MultiArgExpression mae) {
    return mae.getExprs();
  }

  private AlphaExpression _wrapExpression(final RestrictExpression wrapper, final AlphaExpression expr) {
    return AlphaUserFactory.createRestrictExpression(wrapper.getRestrictDomain(), expr);
  }

  private AlphaExpression _wrapExpression(final DependenceExpression wrapper, final AlphaExpression expr) {
    return AlphaUserFactory.createDependenceExpression(wrapper.getFunction(), expr);
  }

  private AlphaExpression _wrapExpression(final UnaryExpression wrapper, final AlphaExpression expr) {
    return AlphaUserFactory.createUnaryExpression(wrapper.getOperator(), expr);
  }

  @Override
  public void outVariableExpression(final VariableExpression ve) {
    EObject _eContainer = ve.eContainer();
    boolean _not = (!(_eContainer instanceof DependenceExpression));
    if (_not) {
      this.debug("implicit DepExpr", "V -> I @ V");
      final ISLMultiAff identityMaff = ISLUtil.toMultiAff(ve.getVariable().getDomain().copy().toIdentityMap());
      VariableExpression newVe = AlphaUserFactory.createVariableExpression(ve.getVariable());
      newVe.setContextDomain(ve.getContextDomain().copy());
      newVe.setExpressionDomain(ve.getExpressionDomain().copy());
      DependenceExpression de = AlphaUserFactory.createDependenceExpression(identityMaff, newVe);
      de.setContextDomain(ve.getContextDomain().copy());
      de.setExpressionDomain(ve.getExpressionDomain().copy());
      EcoreUtil.replace(ve, de);
    }
  }

  private void reapply(final AlphaCompleteVisitable aev) {
    if (aev instanceof AlphaExpressionVisitable) {
      _reapply((AlphaExpressionVisitable)aev);
      return;
    } else if (aev instanceof AlphaVisitable) {
      _reapply((AlphaVisitable)aev);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(aev).toString());
    }
  }

  protected String dependenceExpressionRules(final DependenceExpression de, final AlphaExpression binExpr) {
    if (binExpr instanceof BinaryExpression) {
      return _dependenceExpressionRules(de, (BinaryExpression)binExpr);
    } else if (binExpr instanceof CaseExpression) {
      return _dependenceExpressionRules(de, (CaseExpression)binExpr);
    } else if (binExpr instanceof ConvolutionExpression) {
      return _dependenceExpressionRules(de, (ConvolutionExpression)binExpr);
    } else if (binExpr instanceof DependenceExpression) {
      return _dependenceExpressionRules(de, (DependenceExpression)binExpr);
    } else if (binExpr instanceof IfExpression) {
      return _dependenceExpressionRules(de, (IfExpression)binExpr);
    } else if (binExpr instanceof IndexExpression) {
      return _dependenceExpressionRules(de, (IndexExpression)binExpr);
    } else if (binExpr instanceof MultiArgExpression) {
      return _dependenceExpressionRules(de, (MultiArgExpression)binExpr);
    } else if (binExpr instanceof RestrictExpression) {
      return _dependenceExpressionRules(de, (RestrictExpression)binExpr);
    } else if (binExpr instanceof UnaryExpression) {
      return _dependenceExpressionRules(de, (UnaryExpression)binExpr);
    } else if (binExpr != null) {
      return _dependenceExpressionRules(de, binExpr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(de, binExpr).toString());
    }
  }

  protected String restrictExpressionRules(final RestrictExpression re, final AlphaExpression are) {
    if (are instanceof AbstractReduceExpression) {
      return _restrictExpressionRules(re, (AbstractReduceExpression)are);
    } else if (are instanceof AutoRestrictExpression) {
      return _restrictExpressionRules(re, (AutoRestrictExpression)are);
    } else if (are instanceof CaseExpression) {
      return _restrictExpressionRules(re, (CaseExpression)are);
    } else if (are instanceof RestrictExpression) {
      return _restrictExpressionRules(re, (RestrictExpression)are);
    } else if (are != null) {
      return _restrictExpressionRules(re, are);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re, are).toString());
    }
  }

  protected void binaryExpressionRules(final BinaryExpression be, final AlphaExpression ceLeft, final AlphaExpression ceRight) {
    if (ceLeft instanceof CaseExpression
         && ceRight instanceof CaseExpression) {
      _binaryExpressionRules(be, (CaseExpression)ceLeft, (CaseExpression)ceRight);
      return;
    } else if (ceLeft != null
         && ceRight instanceof CaseExpression) {
      _binaryExpressionRules(be, ceLeft, (CaseExpression)ceRight);
      return;
    } else if (ceLeft != null
         && ceRight != null) {
      _binaryExpressionRules(be, ceLeft, ceRight);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(be, ceLeft, ceRight).toString());
    }
  }

  protected List<AlphaIssue> binaryExpressionRules(final BinaryExpression be, final AlphaExpression ce) {
    if (ce instanceof CaseExpression) {
      return _binaryExpressionRules(be, (CaseExpression)ce);
    } else if (ce instanceof RestrictExpression) {
      return _binaryExpressionRules(be, (RestrictExpression)ce);
    } else if (ce != null) {
      return _binaryExpressionRules(be, ce);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(be, ce).toString());
    }
  }

  protected String unaryExpressionRules(final UnaryExpression ue, final AlphaExpression ce) {
    if (ce instanceof CaseExpression) {
      return _unaryExpressionRules(ue, (CaseExpression)ce);
    } else if (ce instanceof RestrictExpression) {
      return _unaryExpressionRules(ue, (RestrictExpression)ce);
    } else if (ce != null) {
      return _unaryExpressionRules(ue, ce);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(ue, ce).toString());
    }
  }

  protected CaseExpression multiArgExpressionRules(final MultiArgExpression mae, final AlphaExpression ce) {
    if (ce instanceof CaseExpression) {
      return _multiArgExpressionRules(mae, (CaseExpression)ce);
    } else if (ce != null) {
      return _multiArgExpressionRules(mae, ce);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(mae, ce).toString());
    }
  }

  protected AlphaExpression ifExpressionRules(final IfExpression ie, final AlphaExpression ce) {
    if (ce instanceof CaseExpression) {
      return _ifExpressionRules(ie, (CaseExpression)ce);
    } else if (ce instanceof RestrictExpression) {
      return _ifExpressionRules(ie, (RestrictExpression)ce);
    } else if (ce != null) {
      return _ifExpressionRules(ie, ce);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(ie, ce).toString());
    }
  }

  private EList<AlphaExpression> getChildrenEList(final AlphaExpression ce) {
    if (ce instanceof CaseExpression) {
      return _getChildrenEList((CaseExpression)ce);
    } else if (ce instanceof MultiArgExpression) {
      return _getChildrenEList((MultiArgExpression)ce);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(ce).toString());
    }
  }

  private AlphaExpression wrapExpression(final AlphaExpression wrapper, final AlphaExpression expr) {
    if (wrapper instanceof DependenceExpression) {
      return _wrapExpression((DependenceExpression)wrapper, expr);
    } else if (wrapper instanceof RestrictExpression) {
      return _wrapExpression((RestrictExpression)wrapper, expr);
    } else if (wrapper instanceof UnaryExpression) {
      return _wrapExpression((UnaryExpression)wrapper, expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(wrapper, expr).toString());
    }
  }
}
