package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.analysis.reduction.ShareSpaceAnalysis;
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult;
import alpha.model.matrix.MatrixOperations;
import alpha.model.transformation.Normalize;
import alpha.model.util.AffineFunctionOperations;
import alpha.model.util.AlphaOperatorUtil;
import alpha.model.util.AlphaUtil;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import java.util.List;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Idempotence is one of the transformations that can be applied with
 * a reuse vector overlapping the kernel of the projection function.
 * 
 * When repeated application of the reduction operator over the same
 * value does not change the output (i.e., when the operator is idempotent),
 * the domain of the reduction body may be restricted to avoid repeated operations.
 * 
 * Given a reduction of the form:
 *   reduce(op, f, E)
 * and a projection f_c, where ker(f_c) = ker(f) /\ reuse(E),
 * the transformation produces:
 *  reduce(op, f', E')
 * where
 *  - f' is the domain of f projected by f_c
 *  - and E' is the E re-defined to the space of the domain of f'
 * 
 * This re-definition is done by visiting all DependenceExpressions in E
 * and projecting the domain of the dependence function by f_c.
 */
@SuppressWarnings("all")
public class Idempotence {
  public static void apply(final AbstractReduceExpression are) {
    Idempotence.transform(are);
  }

  /**
   * Legality test that should match the one in transform.
   * Exposed to be used by SimplifyingReductionExploration.
   * 
   * Since it is tricky to share the same code, the check code is copy & pasted.
   * It would be better if they can be merged.
   */
  public static boolean testLegality(final AbstractReduceExpression are, final ShareSpaceAnalysisResult SSAR) {
    boolean _isIdempotent = AlphaOperatorUtil.isIdempotent(are.getOperator());
    boolean _not = (!_isIdempotent);
    if (_not) {
      return false;
    }
    final long[][] areSS = SSAR.getShareSpace(are.getBody());
    if ((areSS == null)) {
      return false;
    }
    final long[][] kerFp = MatrixOperations.transpose(AffineFunctionOperations.computeKernel(are.getProjection()));
    final long[][] kerFc = MatrixOperations.kernelIntersection(kerFp, areSS);
    if ((kerFc == null)) {
      return false;
    }
    return true;
  }

  private static void transform(final AbstractReduceExpression are) {
    boolean _isIdempotent = AlphaOperatorUtil.isIdempotent(are.getOperator());
    boolean _not = (!_isIdempotent);
    if (_not) {
      throw new IllegalArgumentException("[Idempotence] The operator of the specified reduction is not idempotent.");
    }
    Normalize.apply(are.getBody());
    final ShareSpaceAnalysisResult SSAR = ShareSpaceAnalysis.apply(are);
    final long[][] areSS = SSAR.getShareSpace(are.getBody());
    if ((areSS == null)) {
      throw new IllegalArgumentException("[Idempotence] The body of the specified reduction does not have any reuse.");
    }
    final long[][] kerFp = MatrixOperations.transpose(AffineFunctionOperations.computeKernel(are.getProjection()));
    final long[][] kerFc = MatrixOperations.kernelIntersection(kerFp, areSS);
    if ((kerFc == null)) {
      throw new IllegalArgumentException("[Idempotence] The intersection of the share space of the reduction body and kernel of the projection is empty.");
    }
    final List<String> params = AlphaUtil.getContainerSystem(are).getParameterDomain().getParamNames();
    final List<String> indices = are.getBody().getContextDomain().getIndexNames();
    final ISLMultiAff Fc = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, kerFc);
    final ISLMultiAff Fpprime = AffineFunctionOperations.projectFunctionDomain(are.getProjection(), Fc.copy());
    ReductionUtil.projectReductionBody(are, Fc.copy());
    AlphaExpression _xifexpression = null;
    int _nbInputs = Fpprime.getNbInputs();
    int _nbOutputs = Fpprime.getNbOutputs();
    boolean _equals = (_nbInputs == _nbOutputs);
    if (_equals) {
      _xifexpression = are.getBody();
    } else {
      _xifexpression = ReductionUtil.constructConcreteReduction(are, are.getOperator(), Fpprime, are.getBody());
    }
    final AlphaExpression replacement = _xifexpression;
    EcoreUtil2.replace(are, replacement);
    AlphaInternalStateConstructor.recomputeContextDomain(replacement);
    Normalize.apply(replacement);
  }
}
