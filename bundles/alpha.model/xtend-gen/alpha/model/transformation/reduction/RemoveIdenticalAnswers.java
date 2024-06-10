package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.analysis.reduction.CandidateReuse;
import alpha.model.analysis.reduction.ShareSpaceAnalysis;
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.transformation.Normalize;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AffineFunctionOperations;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import alpha.model.util.Show;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAffPiece;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Removes the computation of identical answers in any ReduceExpressions where applicable.
 * This happens when there is non-trivial reuse in the reduction body AND there exists a
 * labeling such that all POS- and NEG-faces can be made boundary faces per Theorem 6 of
 * GR06. This class performs the following transformation on reductions where applicable:
 * 
 *   reduce(op, fp, E) -> h @ reduce(op, fp, D : E)
 * 
 * where h is the affine dependence "pulled out" of the reduction body characterizing
 * identical answers computed in the body. D is the union of boundary faces of the
 * reduction body. This can be viewed as a special case of simplification.
 * 
 * 
 * As a simple example, take the following system,
 * -------------------------------------------------------
 *   inputs
 *     X : {[i] : 0<=i<=N}
 *   outputs
 *     Y : {[i] : 0<=i<=N}
 *   let
 *     Y[i] = reduce(+, (i,j->i), {: 0<=i,j<=N } : X[j])
 * -------------------------------------------------------
 * which computes the same value for each element of Y.
 * 
 *  In this example, consider the candidate reuse vector <i,j> = <1,0>. This choice of
 * reuse results in the following labeling on the four faces:
 *   1) {: i=0} -> POS-face		(boundary)
 *   2) {: j=0} -> ZERO-face
 *   3) {: i=N} -> NEG-face		(boundary)
 *   4) {: j=N} -> ZERO-face
 * 
 * Simplifying this reduction along <1,0> per Theorem 5 of GR06 results in
 * the following:
 * -------------------------------------------------------
 *   inputs
 *     X : {[i] : 0<=i<=N}
 *   outputs
 *     Y : {[i] : 0<=i<=N}
 *   let
 *     Y[i] = case {
 *       {: i=0} : reduce(+, (i,j->i), {: i=0 and 0<=j<=N } : X[j]);
 *       {: i>0} : Y[i-1];
 *     }
 * -------------------------------------------------------
 * 
 * The equation for Y depends recursively on itself, but since none of the other branches
 * from Theorem 5 are present, it is valid to express Y as the following instead:
 * 
 *    Y[i] = (i->0)@reduce(+, (i,j->i), {: i=0 and 0<=j<=N } : X[j]);
 */
@SuppressWarnings("all")
public class RemoveIdenticalAnswers extends AbstractAlphaCompleteVisitor {
  public static boolean DEBUG = false;

  private static void debug(final String msg) {
    if (RemoveIdenticalAnswers.DEBUG) {
      InputOutput.<String>println(("[RemoveEmbedding] " + msg));
    }
  }

  /**
   * Tries to remove identical answer dimensions for all reduce expressions
   */
  public static void apply(final AlphaSystem system) {
    final RemoveIdenticalAnswers visitor = new RemoveIdenticalAnswers();
    system.accept(visitor);
  }

  /**
   * Tries to remove identical answer dimensions from reduceExpr
   */
  public static void apply(final AbstractReduceExpression reduceExpr) {
    try {
      throw new Exception();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Override
  public void outReduceExpression(final ReduceExpression reduceExpr) {
    final CandidateReuse candidateReuse = new CandidateReuse(reduceExpr);
    boolean _isHasIdenticalAnswers = candidateReuse.isHasIdenticalAnswers();
    boolean _not = (!_isHasIdenticalAnswers);
    if (_not) {
      return;
    }
    final ISLMultiAff rho = candidateReuse.identicalAnswerBasis();
    RemoveIdenticalAnswers.transform(reduceExpr, rho, candidateReuse.getIdenticalAnswerDomain());
  }

  /**
   * Apply the transformation:
   * reduce(op, fp, E) -> h @ reduce(op, fp, D : E)
   * 
   * where:
   * - h is the transitive closure of the uniform dependence of case 2
   * - D is the POS-face of the residual reduction induced by rho
   */
  public static void transform(final AbstractReduceExpression reduceExpr, final ISLMultiAff rho) {
    RemoveIdenticalAnswers.transform(reduceExpr, rho, ISLSet.buildEmpty(reduceExpr.getBody().getContextDomain().getSpace()));
  }

  public static void transform(final AbstractReduceExpression reduceExpr, final ISLMultiAff rho, final ISLSet decompositionDomain) {
    try {
      AbstractReduceExpression targetReduceExpr = reduceExpr;
      final Equation eq = AlphaUtil.getContainerEquation(targetReduceExpr);
      final AlphaSystem system = AlphaUtil.getContainerSystem(eq);
      if ((RemoveIdenticalAnswers.canBeDecomposed(reduceExpr) && (!decompositionDomain.isEmpty()))) {
        RemoveIdenticalAnswers.debug(("decompositionDomain: " + decompositionDomain));
        final ShareSpaceAnalysisResult SSAR = ShareSpaceAnalysis.apply(targetReduceExpr);
        final LinkedList<Pair<ISLMultiAff, ISLMultiAff>> decompositions = SimplifyingReductions.generateDecompositionCandidates(SSAR, targetReduceExpr);
        final Consumer<Pair<ISLMultiAff, ISLMultiAff>> _function = (Pair<ISLMultiAff, ISLMultiAff> d) -> {
          String _string = d.toString();
          String _plus = ("decomposition " + _string);
          RemoveIdenticalAnswers.debug(_plus);
        };
        decompositions.forEach(_function);
        final Function1<Pair<ISLMultiAff, ISLMultiAff>, Boolean> _function_1 = (Pair<ISLMultiAff, ISLMultiAff> it) -> {
          return Boolean.valueOf(ISLUtil.nullSpace(it.getKey()).isEqual(decompositionDomain));
        };
        final Pair<ISLMultiAff, ISLMultiAff> decomposition = IterableExtensions.<Pair<ISLMultiAff, ISLMultiAff>>findFirst(decompositions, _function_1);
        if ((decomposition == null)) {
          throw new Exception("A valid decomposition should exist, something went wrong.");
        }
        final ISLMultiAff innerProjection = decomposition.getKey();
        final ISLMultiAff outerProjection = decomposition.getValue();
        String _print = Show.<AlphaSystem>print(system);
        String _plus = ("Before reduction decomposition\n" + _print);
        RemoveIdenticalAnswers.debug(_plus);
        ReductionDecomposition.apply(targetReduceExpr, innerProjection.copy(), outerProjection.copy());
        AlphaExpression _body = targetReduceExpr.getBody();
        targetReduceExpr = ((ReduceExpression) _body);
        String _print_1 = Show.<AlphaSystem>print(system);
        String _plus_1 = ("After reduction decomposition\n" + _print_1);
        RemoveIdenticalAnswers.debug(_plus_1);
      }
      final ISLSpace resultSpace = targetReduceExpr.getContextDomain().getSpace();
      final ISLMultiAff fp = targetReduceExpr.getProjection();
      final ISLMultiAff reuseDep = AffineFunctionOperations.negateUniformFunction(rho);
      final SimplifyingReductions.BasicElements basicElements = SimplifyingReductions.computeBasicElements(targetReduceExpr, reuseDep);
      final ISLSet Dadd = basicElements.Dadd;
      final ISLMultiAff uniformReuseDependence = SimplifyingReductions.constructDependenceFunctionInAnswerSpace(resultSpace, fp, reuseDep);
      final ISLMultiAff h = RemoveIdenticalAnswers.transitiveClosureAt(uniformReuseDependence, Dadd);
      final ReduceExpression newReduceExpr = SimplifyingReductions.createXadd(targetReduceExpr, basicElements);
      final DependenceExpression depExpr = AlphaUserFactory.createDependenceExpression(h.copy(), newReduceExpr);
      EcoreUtil.replace(targetReduceExpr, depExpr);
      AlphaInternalStateConstructor.recomputeContextDomain(depExpr);
      Normalize.apply(depExpr);
      String _print_2 = Show.<AlphaSystem>print(system);
      String _plus_2 = ("reduce(op, fp, E) -> h@reduce(op, fp, D:E)\n" + _print_2);
      RemoveIdenticalAnswers.debug(_plus_2);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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

  /**
   * Returns the dimensionality of the accumulation space (null space of fp) of the reduction
   */
  public static int accumulationDimensionality(final AbstractReduceExpression reduceExpr) {
    return ISLUtil.dimensionality(ISLUtil.nullSpace(reduceExpr.getProjection()));
  }

  /**
   * Returns true if the accumulation space is multidimensional, or false otherwise
   */
  public static boolean canBeDecomposed(final AbstractReduceExpression reduceExpr) {
    int _accumulationDimensionality = RemoveIdenticalAnswers.accumulationDimensionality(reduceExpr);
    return (_accumulationDimensionality > 1);
  }
}
