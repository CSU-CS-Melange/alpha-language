package alpha.model.transformation;

import alpha.model.AlphaCompleteVisitable;
import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.DependenceExpression;
import alpha.model.IndexExpression;
import alpha.model.IntegerExpression;
import alpha.model.ReduceExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaOperatorUtil;
import alpha.model.util.AlphaUtil;
import alpha.model.util.Show;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Simplification of Alpha programs.
 * 
 * This visitor has a similar structure to Normalize. The main difference is that
 * the transformations in Simplify are not necessary for a program to be in normal form.
 * Normalize also implements transformations that are not strictly necessary for
 * normal form, but eliminate errors. For instance, removing empty case branches
 * is necessary to avoid errors after Normalize.
 * 
 * The simplification rules implemented are:
 *  - remove BinaryExpression when one of the operand is identity
 *  - replace IndexExpression with ConstantExpression when applicable
 *  - replace ReduceExpression with its body when it has scalar domain
 */
@SuppressWarnings("all")
public class SimplifyExpressions extends AbstractAlphaCompleteVisitor {
  private static boolean DEBUG = false;

  protected String debug(final String ruleID, final String rule) {
    String _xifexpression = null;
    if (SimplifyExpressions.DEBUG) {
      _xifexpression = InputOutput.<String>println(((ruleID + ": ") + rule));
    }
    return _xifexpression;
  }

  protected String debug(final AlphaExpression expr) {
    String _xifexpression = null;
    if (SimplifyExpressions.DEBUG) {
      _xifexpression = InputOutput.<String>println(Show.<AlphaSystem>print(AlphaUtil.getContainerSystem(expr)));
    }
    return _xifexpression;
  }

  protected void warning(final String message) {
    System.err.println(String.format("[Simplify] %s.", message));
  }

  protected SimplifyExpressions() {
  }

  public static void apply(final AlphaCompleteVisitable acv) {
    if ((acv instanceof AlphaVisitable)) {
      SimplifyExpressions.apply(((AlphaVisitable) acv));
    } else {
      if ((acv instanceof AlphaExpressionVisitable)) {
        SimplifyExpressions.apply(((AlphaExpressionVisitable) acv));
      } else {
        throw new RuntimeException(("This should be unreachable code. Got: " + acv));
      }
    }
  }

  public static void apply(final AlphaVisitable av) {
    final SimplifyExpressions visitor = new SimplifyExpressions();
    av.accept(visitor);
  }

  public static void apply(final AlphaExpressionVisitable aev) {
    final SimplifyExpressions visitor = new SimplifyExpressions();
    aev.accept(visitor);
  }

  @Override
  public void outBinaryExpression(final BinaryExpression be) {
    if (((AlphaOperatorUtil.isIdentity(be.getOperator(), be.getLeft()) && (!Objects.equal(be.getOperator(), BINARY_OP.DIV))) && (!Objects.equal(be.getOperator(), BINARY_OP.MOD)))) {
      EcoreUtil.replace(be, be.getRight());
    } else {
      boolean _isIdentity = AlphaOperatorUtil.isIdentity(be.getOperator(), be.getRight());
      if (_isIdentity) {
        EcoreUtil.replace(be, be.getLeft());
      }
    }
  }

  @Override
  public void outIndexExpression(final IndexExpression ie) {
    if (((ie.getFunction().getNbOutputs() == 1) && ie.getFunction().getAff(0).isConstant())) {
      final ISLVal v = ie.getFunction().getAff(0).eval(ie.getExpressionDomain().copy().samplePoint());
      final IntegerExpression intExpr = AlphaUserFactory.createIntegerExpression(Long.valueOf(v.asLong()).intValue());
      EcoreUtil.replace(ie, intExpr);
    }
  }

  @Override
  public void outReduceExpression(final ReduceExpression re) {
    ISLMap map = re.getProjection().toMap();
    map = map.intersectDomain(re.getBody().getContextDomain());
    map = map.intersectRange(re.getContextDomain());
    map = map.reverse();
    boolean _isSingleValued = map.isSingleValued();
    if (_isSingleValued) {
      final ISLPWMultiAff pwMultiAff = map.toPWMultiAff();
      int _nbPieces = pwMultiAff.getNbPieces();
      boolean _equals = (_nbPieces == 1);
      if (_equals) {
        final ISLMultiAff multiAff = pwMultiAff.getPiece(0).getMaff();
        final DependenceExpression depExpr = AlphaUserFactory.createDependenceExpression(multiAff, re.getBody());
        EcoreUtil.replace(re, depExpr);
        AlphaInternalStateConstructor.recomputeContextDomain(depExpr);
      } else {
      }
    }
  }
}
