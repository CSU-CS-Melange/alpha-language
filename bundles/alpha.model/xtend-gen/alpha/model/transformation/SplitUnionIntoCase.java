package alpha.model.transformation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.AutoRestrictExpression;
import alpha.model.CaseExpression;
import alpha.model.RestrictExpression;
import alpha.model.SystemBody;
import alpha.model.factory.AlphaUserFactory;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Transforms a RestrictExpression with restrict domain being unions of polyhedra
 * in to a CaseExpression forcing restrict domains to be a single polyhedron.
 * 
 * The transformation takes a RestrictExpression:
 *   {D1 \/ D2} : E
 * and transforms it to:
 *   case {
 *      D1 : E;
 *      D2 : E;
 *   }
 * 
 * If the restrict domain is a union of more than two polyhedra, the number
 * of branches in the resulting CaseExpression increases accordingly.
 */
@SuppressWarnings("all")
public class SplitUnionIntoCase {
  private SplitUnionIntoCase() {
  }

  /**
   * Apply the transformation to all RestrictExpressions in an AlphaSystem.
   * Silently ignores any RestrictExpressions where it is not applicable.
   * 
   * @returns number of applications of the transformation
   */
  public static Integer apply(final AlphaSystem system) {
    final Function1<SystemBody, Integer> _function = (SystemBody b) -> {
      return SplitUnionIntoCase.apply(b);
    };
    final Function2<Integer, Integer, Integer> _function_1 = (Integer p1, Integer p2) -> {
      return Integer.valueOf(((p1).intValue() + (p2).intValue()));
    };
    return IterableExtensions.<Integer>reduce(ListExtensions.<SystemBody, Integer>map(system.getSystemBodies(), _function), _function_1);
  }

  /**
   * Apply the transformation to all RestrictExpressions in a SystemBody.
   * Silently ignores any RestrictExpressions where it is not applicable.
   * 
   * @returns number of applications of the transformation
   */
  public static Integer apply(final SystemBody body) {
    List<RestrictExpression> _allContentsOfType = EcoreUtil2.<RestrictExpression>getAllContentsOfType(body, RestrictExpression.class);
    List<AutoRestrictExpression> _allContentsOfType_1 = EcoreUtil2.<AutoRestrictExpression>getAllContentsOfType(body, AutoRestrictExpression.class);
    final Function1<AlphaExpression, Integer> _function = (AlphaExpression are) -> {
      return Integer.valueOf(SplitUnionIntoCase.transform(are));
    };
    final Function2<Integer, Integer, Integer> _function_1 = (Integer p1, Integer p2) -> {
      return Integer.valueOf(((p1).intValue() + (p2).intValue()));
    };
    return IterableExtensions.<Integer>reduce(IterableExtensions.<AlphaExpression, Integer>map(Iterables.<AlphaExpression>concat(_allContentsOfType, _allContentsOfType_1), _function), _function_1);
  }

  /**
   * Applies SplitUnionIntoCase to the specified restrict expression.
   * This method throws an exception when it is not applicable to the specified expression.
   */
  public static void apply(final RestrictExpression re) {
    int _transform = SplitUnionIntoCase.transform(re);
    boolean _lessThan = (_transform < 1);
    if (_lessThan) {
      throw new IllegalArgumentException("[SplitUnionIntoCase] Target RestrictExpression must have unions of polyhedra as its restrict domain.");
    }
  }

  /**
   * Applies SplitUnionIntoCase to the specified restrict expression.
   * This method throws an exception when it is not applicable to the specified expression.
   */
  public static void apply(final AutoRestrictExpression are) {
    int _transform = SplitUnionIntoCase.transform(are);
    boolean _lessThan = (_transform < 1);
    if (_lessThan) {
      throw new IllegalArgumentException("[SplitUnionIntoCase] Target AutoRestrictExpression must have unions of polyhedra as its (inferred) restrict domain.");
    }
  }

  public static void apply(final AbstractReduceExpression are) {
    final AlphaExpression body = are.getBody();
    if ((body instanceof RestrictExpression)) {
      SplitUnionIntoCase.apply(((RestrictExpression)body));
      return;
    }
    final RestrictExpression re = AlphaUserFactory.createRestrictExpression(body.getContextDomain(), EcoreUtil.<AlphaExpression>copy(body));
    EcoreUtil.replace(body, re);
    AlphaInternalStateConstructor.recomputeContextDomain(are);
    SplitUnionIntoCase.apply(re);
  }

  /**
   * Implementation of the transformation.
   */
  private static int transform(final AlphaExpression parent, final ISLSet restrictDomain, final AlphaExpression child) {
    final CaseExpression caseExpr = AlphaUserFactory.createCaseExpression();
    final ISLSet disjointDomains = restrictDomain.makeDisjoint();
    int _nbBasicSets = disjointDomains.getNbBasicSets();
    boolean _equals = (_nbBasicSets == 1);
    if (_equals) {
      return 0;
    }
    final Consumer<ISLBasicSet> _function = (ISLBasicSet bs) -> {
      final RestrictExpression re = AlphaUserFactory.createRestrictExpression(bs.toSet(), EcoreUtil.<AlphaExpression>copy(child));
      caseExpr.getExprs().add(re);
    };
    disjointDomains.getBasicSets().forEach(_function);
    EcoreUtil.replace(parent, caseExpr);
    AlphaInternalStateConstructor.recomputeContextDomain(caseExpr);
    return 1;
  }

  private static int _transform(final RestrictExpression re) {
    return SplitUnionIntoCase.transform(re, re.getRestrictDomain(), re.getExpr());
  }

  private static int _transform(final AutoRestrictExpression re) {
    return SplitUnionIntoCase.transform(re, re.getInferredDomain(), re.getExpr());
  }

  private static int transform(final AlphaExpression re) {
    if (re instanceof AutoRestrictExpression) {
      return _transform((AutoRestrictExpression)re);
    } else if (re instanceof RestrictExpression) {
      return _transform((RestrictExpression)re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re).toString());
    }
  }
}
