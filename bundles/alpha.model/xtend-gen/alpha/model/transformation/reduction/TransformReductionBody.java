package alpha.model.transformation.reduction;

import alpha.model.AlphaInternalStateConstructor;
import alpha.model.DependenceExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AffineFunctionOperations;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Applies TransformReductionBody, a transformation that transforms the domain of the reduction body,
 * without changing the domain of other variables. It is a variation of Change of Basis.
 * 
 * Given ReduceExpression reduce(op, fp, E) and ISLMultiAff T, transforms the reduction to
 * reduce(op, Tinv@fp, image(E) : Tinv@E)
 * The domain is transformed to the image by T, and specified as restrict. All variable accesses are composed
 * with the inverse of T, so that other variables are unchanged.
 * 
 * This transformation should be followed by Normalize to propagate the dependency and restrict expression introduced
 */
@SuppressWarnings("all")
public class TransformReductionBody {
  public static void apply(final ReduceExpression reduce, final ISLMultiAff T) {
    TransformReductionBody.transformReductionBody(reduce, T);
  }

  public static void transformReductionBody(final ReduceExpression reduceExpr, final ISLMultiAff T) {
    if ((T == null)) {
      throw new RuntimeException("Transformation T is not given.");
    }
    final ISLSet bodyContextDomain = reduceExpr.getBody().getContextDomain().copy();
    ISLMultiAff Tinv = null;
    try {
      Tinv = AffineFunctionOperations.inverseInContext(T, bodyContextDomain, null);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        throw new RuntimeException("Given transformation is not bijective.");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    final DependenceExpression depExpr = AlphaUserFactory.createDependenceExpression(Tinv, reduceExpr.getBody());
    final RestrictExpression restrictExpr = AlphaUserFactory.createRestrictExpression(bodyContextDomain.copy().apply(T.copy().toMap()), depExpr);
    EcoreUtil.replace(reduceExpr.getBody(), restrictExpr);
    AlphaInternalStateConstructor.recomputeContextDomain(reduceExpr);
  }
}
