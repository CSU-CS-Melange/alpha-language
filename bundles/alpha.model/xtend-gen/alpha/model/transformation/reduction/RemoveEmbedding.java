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
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class RemoveEmbedding {
  /**
   * Apply the transformation:
   * reduce(op, fp, E) -> h @ reduce(op, fp, D : E)
   * 
   * where:
   * - h is the transitive closure of the uniform dependence of case 2
   * - D is the POS-face of the residual reduction induced by rho
   */
  public static void apply(final AbstractReduceExpression reduceExpr, final ISLMultiAff rho) {
    try {
      final ISLSpace resultSpace = reduceExpr.getContextDomain().getSpace();
      final ISLMultiAff fp = reduceExpr.getProjection();
      final ISLMultiAff reuseDep = AffineFunctionOperations.negateUniformFunction(rho);
      final SimplifyingReductions.BasicElements basicElements = SimplifyingReductions.computeBasicElements(reduceExpr, reuseDep);
      final ISLSet Dadd = basicElements.Dadd;
      final ISLMultiAff uniformReuseDependence = SimplifyingReductions.constructDependenceFunctionInAnswerSpace(resultSpace, fp, reuseDep);
      InputOutput.<String>println(("Uniform dep for reusing prev answers: " + uniformReuseDependence));
      final ISLSet computedAnswers = Dadd;
      InputOutput.<String>println(("Computed answers: " + computedAnswers));
      final JNIPtrBoolean isExact = new JNIPtrBoolean();
      final ISLMap closure = uniformReuseDependence.copy().toMap().transitiveClosure(isExact);
      if ((!isExact.value)) {
        throw new Exception("Transitive closure should be exact, something went wrong");
      }
      final ISLMap intersected = closure.intersectRange(computedAnswers);
      final ISLMultiAff h = intersected.copy().toPWMultiAff().getPiece(0).getMaff();
      InputOutput.<String>println(("Intersect with Y: " + intersected));
      InputOutput.<ISLMultiAff>println(h);
      final ReduceExpression newReduceExpr = SimplifyingReductions.createXadd(reduceExpr, basicElements);
      final DependenceExpression depExpr = AlphaUserFactory.createDependenceExpression(h, newReduceExpr);
      EcoreUtil.replace(reduceExpr, depExpr);
      AlphaInternalStateConstructor.recomputeContextDomain(depExpr);
      Normalize.apply(depExpr);
      InputOutput.println();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
