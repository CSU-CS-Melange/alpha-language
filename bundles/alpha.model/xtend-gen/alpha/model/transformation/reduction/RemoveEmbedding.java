package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.DependenceExpression;
import alpha.model.ReduceExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.transformation.Normalize;
import alpha.model.util.AffineFunctionOperations;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAffPiece;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean;
import java.util.List;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class RemoveEmbedding {
  public static boolean DEBUG = false;

  private static void debug(final String msg) {
    if (RemoveEmbedding.DEBUG) {
      InputOutput.<String>println(("[RemoveEmbedding] " + msg));
    }
  }

  /**
   * Apply the transformation:
   * reduce(op, fp, E) -> h @ reduce(op, fp, D : E)
   * 
   * where:
   * - h is the transitive closure of the uniform dependence of case 2
   * - D is the POS-face of the residual reduction induced by rho
   */
  public static void apply(final AbstractReduceExpression reduceExpr, final ISLMultiAff rho) {
    final ISLSpace resultSpace = reduceExpr.getContextDomain().getSpace();
    final ISLMultiAff fp = reduceExpr.getProjection();
    final ISLMultiAff reuseDep = AffineFunctionOperations.negateUniformFunction(rho);
    final SimplifyingReductions.BasicElements basicElements = SimplifyingReductions.computeBasicElements(reduceExpr, reuseDep);
    final ISLSet Dadd = basicElements.Dadd;
    final ISLMultiAff uniformReuseDependence = SimplifyingReductions.constructDependenceFunctionInAnswerSpace(resultSpace, fp, reuseDep);
    final ISLMultiAff h = RemoveEmbedding.transitiveClosureAt(uniformReuseDependence, Dadd);
    final ReduceExpression newReduceExpr = SimplifyingReductions.createXadd(reduceExpr, basicElements);
    final DependenceExpression depExpr = AlphaUserFactory.createDependenceExpression(h, newReduceExpr);
    EcoreUtil.replace(reduceExpr, depExpr);
    AlphaInternalStateConstructor.recomputeContextDomain(depExpr);
    Normalize.apply(depExpr);
  }

  /**
   * Returns the affine dependence of the transitive closure of maff with the given range
   */
  public static ISLMultiAff transitiveClosureAt(final ISLMultiAff maff, final ISLSet range) {
    try {
      ISLMultiAff _xblockexpression = null;
      {
        final JNIPtrBoolean isExact = new JNIPtrBoolean();
        final ISLMap closure = maff.copy().toMap().transitiveClosure(isExact);
        if ((!isExact.value)) {
          throw new Exception("Transitive closure should be exact, something went wrong");
        }
        final List<ISLPWMultiAffPiece> pieces = closure.intersectRange(range).toPWMultiAff().getPieces();
        int _size = pieces.size();
        boolean _notEquals = (_size != 1);
        if (_notEquals) {
          throw new Exception("Transitive closure should only contain a single piece, something went wrong");
        }
        _xblockexpression = pieces.get(0).getMaff();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
