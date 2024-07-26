package alpha.model.analysis.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.MultiArgExpression;
import alpha.model.RestrictExpression;
import alpha.model.SystemBody;
import alpha.model.UnaryExpression;
import alpha.model.VariableExpression;
import alpha.model.matrix.MatrixOperations;
import alpha.model.util.AbstractAlphaExpressionVisitor;
import alpha.model.util.AffineFunctionOperations;
import alpha.model.util.AlphaUtil;
import alpha.model.util.DomainOperations;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.eclipse.xtext.xbase.lib.Conversions;

/**
 * Share space is the sub space spanned by set of operations that reuse the same value.
 * See Section 5.2 of the Simplifying Reductions paper for details.
 * 
 * This implementation is incomplete:
 *  - All variables (not just inputs) are assumed to have empty share space.
 *  It is not difficult to compute share space of other equations as necessary,
 *  but this is currently not implemented.
 *  - The share space of a reduction is assumed to be empty. It does check
 *  if the body of a reduction has non-empty share space before projection,
 *  but does not perform the projection and issues a warning. This case
 *  should not be encountered unless the expression domain is unbounded.
 */
@SuppressWarnings("all")
public class ShareSpaceAnalysis extends AbstractAlphaExpressionVisitor {
  protected Map<AlphaExpression, long[][]> shareSpace;

  private ShareSpaceAnalysis() {
    TreeMap<AlphaExpression, long[][]> _treeMap = new TreeMap<AlphaExpression, long[][]>(new Comparator<AlphaExpression>() {
      @Override
      public int compare(final AlphaExpression o1, final AlphaExpression o2) {
        final int idcmp = o1.getExpressionID().toString().compareTo(o2.getExpressionID().toString());
        if ((idcmp != 0)) {
          return idcmp;
        }
        final Equation eq1 = AlphaUtil.getContainerEquation(o1);
        final SystemBody body1 = eq1.getSystemBody();
        final Equation eq2 = AlphaUtil.getContainerEquation(o2);
        final SystemBody body2 = eq2.getSystemBody();
        boolean _notEquals = (!Objects.equal(body1, body2));
        if (_notEquals) {
          throw new RuntimeException("Should not be comparing expressions across SystemBody.");
        }
        int _indexOf = body1.getEquations().indexOf(eq1);
        int _indexOf_1 = body1.getEquations().indexOf(eq2);
        return (_indexOf - _indexOf_1);
      }
    });
    this.shareSpace = _treeMap;
  }

  public static ShareSpaceAnalysisResult apply(final AlphaExpression expr) {
    final ShareSpaceAnalysis SSA = new ShareSpaceAnalysis();
    SSA.accept(expr);
    return new ShareSpaceAnalysisResult(SSA.shareSpace);
  }

  private void warning(final String message) {
    System.err.println(("[ShareSpaceAnalysis] " + message));
  }

  public static boolean isConstantInContext(final AlphaExpression ae) {
    boolean _xblockexpression = false;
    {
      final int dim = ae.getContextDomain().dim(ISLDimType.isl_dim_out);
      final ShareSpaceAnalysisResult SSAR = ShareSpaceAnalysis.apply(ae);
      final long[][] shareSpace = SSAR.getShareSpace(ae);
      _xblockexpression = ((shareSpace != null) && (((List<long[]>)Conversions.doWrapArray(shareSpace)).size() == dim));
    }
    return _xblockexpression;
  }

  @Override
  public void outConstantExpression(final ConstantExpression ce) {
    this.shareSpace.put(ce, null);
  }

  @Override
  public void outVariableExpression(final VariableExpression ve) {
    this.shareSpace.put(ve, null);
  }

  @Override
  public void outUnaryExpression(final UnaryExpression ue) {
    this.shareSpace.put(ue, this.shareSpace.get(ue.getExpr()));
  }

  @Override
  public void outRestrictExpression(final RestrictExpression re) {
    this.shareSpace.put(re, this.shareSpace.get(re.getExpr()));
  }

  @Override
  public void outAutoRestrictExpression(final AutoRestrictExpression are) {
    this.shareSpace.put(are, this.shareSpace.get(are.getExpr()));
  }

  @Override
  public void outBinaryExpression(final BinaryExpression be) {
    final long[][] SE = this.intersectShareSpaces(be.getLeft(), be.getRight());
    this.shareSpace.put(be, SE);
  }

  @Override
  public void outIfExpression(final IfExpression ie) {
    final long[][] SE = this.intersectShareSpaces(ie.getCondExpr(), ie.getThenExpr(), ie.getElseExpr());
    this.shareSpace.put(ie, SE);
  }

  @Override
  public void outMultiArgExpression(final MultiArgExpression mae) {
    final long[][] SE = this.intersectShareSpaces(mae.getExprs());
    this.shareSpace.put(mae, SE);
  }

  @Override
  public void outCaseExpression(final CaseExpression ce) {
    final long[][] SE = this.intersectShareSpaces(ce.getExprs());
    this.shareSpace.put(ce, SE);
  }

  @Override
  public void outIndexExpression(final IndexExpression ie) {
    final long[][] kernel = AffineFunctionOperations.computeKernel(ie.getFunction());
    int _length = kernel.length;
    boolean _equals = (_length == 0);
    if (_equals) {
      this.shareSpace.put(ie, null);
    } else {
      this.shareSpace.put(ie, MatrixOperations.transpose(kernel));
    }
  }

  @Override
  public void outDependenceExpression(final DependenceExpression de) {
    final long[][] SEp = this.shareSpace.get(de.getExpr());
    ISLMultiAff _xifexpression = null;
    if ((SEp == null)) {
      _xifexpression = de.getFunction();
    } else {
      ISLMultiAff _xblockexpression = null;
      {
        final List<String> params = AlphaUtil.getParameterDomain(de).getParamNames();
        final List<String> indices = de.getContextDomain().getIndexNames();
        final ISLMultiAff maff = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, SEp);
        _xblockexpression = maff.pullback(de.getFunction());
      }
      _xifexpression = _xblockexpression;
    }
    final ISLMultiAff f = _xifexpression;
    final long[][] kernel = AffineFunctionOperations.computeKernel(f);
    if (((kernel == null) || (kernel.length == 0))) {
      this.shareSpace.put(de, null);
    } else {
      this.shareSpace.put(de, MatrixOperations.transpose(kernel));
    }
  }

  @Override
  public void outAbstractReduceExpression(final AbstractReduceExpression are) {
    if (((are.getExpressionDomain().dim(ISLDimType.isl_dim_div) > 0) || 
      (are.getExpressionDomain().getNbBasicSets() > 1))) {
      this.shareSpace.put(are, null);
      this.warning("Reduction body is not a single polyhedron or contains div dimensions. Share space is set to empty.");
      return;
    }
    final long[][] SEp = this.shareSpace.get(are.getBody());
    final long[][] kerQ = MatrixOperations.transpose(DomainOperations.kernelOfLinearPart(are.getBody().getExpressionDomain()));
    final long[][] intersection = MatrixOperations.kernelIntersection(SEp, kerQ);
    if ((intersection != null)) {
      this.warning("Expression of a reduction has non-empty share space. This is only possible when the expression domain has non-empty linearlity space. The share space of the ReduceExpression is set to empty.");
    }
    this.shareSpace.put(are, null);
  }

  @Override
  public void defaultOut(final AlphaExpressionVisitable expr) {
    throw new UnsupportedOperationException(("[ShareSpaceAnalyais] Unsupported Expression: " + expr));
  }

  private long[][] intersectShareSpaces(final AlphaExpression... exprs) {
    return this.intersectShareSpaces(Arrays.<AlphaExpression>asList(exprs));
  }

  private long[][] intersectShareSpaces(final List<AlphaExpression> exprs) {
    long[][] SE = null;
    for (final AlphaExpression expr : exprs) {
      long[][] _xifexpression = null;
      if ((SE == null)) {
        _xifexpression = this.shareSpace.get(expr);
      } else {
        _xifexpression = MatrixOperations.kernelIntersection(SE, this.shareSpace.get(expr));
      }
      SE = _xifexpression;
    }
    return SE;
  }
}
