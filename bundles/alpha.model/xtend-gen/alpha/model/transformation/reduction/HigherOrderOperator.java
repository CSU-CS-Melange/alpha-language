package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.PolynomialIndexExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.analysis.reduction.ShareSpaceAnalysis;
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.matrix.MatrixOperations;
import alpha.model.transformation.Normalize;
import alpha.model.util.AffineFunctionOperations;
import alpha.model.util.AlphaOperatorUtil;
import alpha.model.util.AlphaUtil;
import fr.irisa.cairn.jnimap.barvinok.BarvinokFunctions;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import java.util.List;
import org.eclipse.xtext.EcoreUtil2;

/**
 * HigherOrderOperator is one of the transformations that can be applied with
 * a reuse vector overlapping the kernel of the projection function.
 * 
 * When repeated application of the reduction operator over the same
 * value has an associated higher order operator, then the corresponding
 * slice of the reduction may be replaced with an application of the
 * higher order operator.
 * 
 * Given a reduction of the form:
 *   reduce(op, f, E)
 * and a projection f_c, where ker(f_c) = ker(f) /\ reuse(E),
 * the transformation produces:
 *  reduce(op, f', op2(f_c, E))
 * where
 *  - f' is the domain of f projected by f_c
 *  - and op2 is a higher order operator.
 * 
 * Examples of higher order operators include:
 *   - reduce(+, (i->), X[])  => count(DE) * X[]
 *   - reduce(*, (i->), X[] => power(X[], count(DE))
 * where count(DE) is the number of integer points in the
 * context domain of reduction body.
 * 
 * The above examples are when ker(f) = reuse(E), in which case
 * the reduction is entirely replaced. In general, partial slices
 * of each reduction is replaced. The original reduction is viewed
 * as a composition of two reductions:
 *   - reduce(op, f', reduce(op, f_c, E))
 * and the inner reduction now has ker(f_c) = reuse(E).
 * For sum/product, the corresponding higher order operators require:
 *   - E' where the domain of E is projected by f_c
 *   - count(DE), the number of integer points in the inner reduction,
 * parameterized by outer dimensions. This is computed by constructing
 * a relation R = [\vec(i)] - > [f_c(\vec(i))] : \vec(i) \in X_E
 * where X_E is the context domain of E, and computing the cardinality
 * after reversing the relation.
 * 
 * It is also possible to reduce O(n) reduction to O(logn) for any reduction
 * operator, but this transformation is not supported.
 * 
 * FIXME Current implementation only supports addition => product.
 */
@SuppressWarnings("all")
public class HigherOrderOperator {
  public static void apply(final AbstractReduceExpression are) {
    HigherOrderOperator.transform(are);
  }

  /**
   * Legality test that should match the one in transform.
   * Exposed to be used by SimplifyingReductionExploration.
   * 
   * It is quite similar to Idempotence, may be it can be merged somewhere.
   */
  public static boolean testLegality(final AbstractReduceExpression are, final ShareSpaceAnalysisResult SSAR) {
    boolean _hasHigherOrderOperator = AlphaOperatorUtil.hasHigherOrderOperator(are.getOperator());
    boolean _not = (!_hasHigherOrderOperator);
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
    boolean _hasHigherOrderOperator = AlphaOperatorUtil.hasHigherOrderOperator(are.getOperator());
    boolean _not = (!_hasHigherOrderOperator);
    if (_not) {
      throw new IllegalArgumentException("[HigherOrderOperator] Higher order operator for the operator of the specified reduction is not available.");
    }
    Normalize.apply(are.getBody());
    final ShareSpaceAnalysisResult SSAR = ShareSpaceAnalysis.apply(are);
    final long[][] areSS = SSAR.getShareSpace(are.getBody());
    if ((areSS == null)) {
      throw new IllegalArgumentException("[HigherOrderOperator] The body of the specified reduction does not have any reuse.");
    }
    final long[][] kerFp = MatrixOperations.transpose(AffineFunctionOperations.computeKernel(are.getProjection()));
    final long[][] kerFc = MatrixOperations.kernelIntersection(kerFp, areSS);
    if ((kerFc == null)) {
      throw new IllegalArgumentException("[HigherOrderOperator] The intersection of the share space of the reduction body and kernel of the projection is empty.");
    }
    final List<String> params = AlphaUtil.getContainerSystem(are).getParameterDomain().getParamNames();
    final List<String> indices = are.getBody().getContextDomain().getIndexNames();
    final ISLMultiAff Fc = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, kerFc);
    final ISLMultiAff Fpprime = AffineFunctionOperations.projectFunctionDomain(are.getProjection(), Fc.copy());
    final BinaryExpression replacementBody = HigherOrderOperator.constructHigherOrderOperation(are, Fc);
    AlphaExpression _xifexpression = null;
    int _nbInputs = Fpprime.getNbInputs();
    int _nbOutputs = Fpprime.getNbOutputs();
    boolean _equals = (_nbInputs == _nbOutputs);
    if (_equals) {
      _xifexpression = replacementBody;
    } else {
      _xifexpression = ReductionUtil.constructConcreteReduction(are, are.getOperator(), Fpprime, replacementBody);
    }
    final AlphaExpression replacement = _xifexpression;
    EcoreUtil2.replace(are, replacement);
    AlphaInternalStateConstructor.recomputeContextDomain(replacement);
    Normalize.apply(replacement);
  }

  private static BinaryExpression constructHigherOrderOperation(final AbstractReduceExpression are, final ISLMultiAff Fc) {
    BinaryExpression _xblockexpression = null;
    {
      final ISLPWQPolynomial card = BarvinokFunctions.card(Fc.copy().toMap().intersectDomain(are.getBody().getContextDomain()).reverse());
      ReductionUtil.projectReductionBody(are, Fc.copy());
      BinaryExpression _switchResult = null;
      REDUCTION_OP _operator = are.getOperator();
      if (_operator != null) {
        switch (_operator) {
          case SUM:
            BinaryExpression _xblockexpression_1 = null;
            {
              final PolynomialIndexExpression pie = AlphaUserFactory.createPolynomialIndexExpression(card);
              _xblockexpression_1 = AlphaUserFactory.createBinaryExpression(BINARY_OP.MUL, pie, 
                are.getBody());
            }
            _switchResult = _xblockexpression_1;
            break;
          default:
            _switchResult = null;
            break;
        }
      } else {
        _switchResult = null;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
}
