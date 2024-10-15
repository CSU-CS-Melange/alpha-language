package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.CaseExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.matrix.MatrixOperations;
import alpha.model.transformation.Normalize;
import alpha.model.util.AffineFunctionOperations;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.Face;
import alpha.model.util.ISLUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * This class carries out the analysis required for splitting from the max
 * simplification paper (https://arxiv.org/abs/2309.11826). This is primarily
 * intended to be used with OptimalSimplifyingReductions.
 * 
 * 1) "covered" (d-2)-faces:
 *    Given an input reduce expression with a d-dimensional body, we need
 *    to compute the (d-2)-dimensional (d-2)-faces of the body that are
 *    covered per definition 4.8 in the max simplification paper.
 * 
 *    Making splits thru covered (d-2)-faces is an optimization. The usefulness
 *    of such splits is that it will result in two non-empty pieces. We could
 *    simply try making splits thru all of (d-2)-faces and only keep the ones
 *    that result in two non-empty pieces. This is what the current implementation
 *    does. Detecting covered edges is not currently implemented.
 * 
 * 2) Given a reduction with a 1D REUSE space and a (d-2)-face of the reduction
 *    body, we need to construct a new constraint that saturates the (d-2)-face
 *    and the 1D space.
 *    We can do this in ISL by taking the linear space of the (d-2)-face and then
 *    applying the transitive closure of the ILSMultiAff characterizing the null
 *    space of the highest dependence expression in reduction body.
 * 
 * 3) Given a reduction with a 1D ACCUMULATION space and a (d-2)-face of the reduction
 *    body, we need to construct a new constraint that saturates the (d-2)-face
 *    and the 1D space.
 *    We can do this in ISL by taking the linear space of the (d-2)-face and then
 *    applying the transitive closure of the ILSMultiAff characterizing the projection
 *    function.
 */
@SuppressWarnings("all")
public class SplitReduction {
  public static boolean DEBUG = false;

  private static void debug(final String msg) {
    if (SplitReduction.DEBUG) {
      InputOutput.<String>println(("[SplitReduction] " + msg));
    }
  }

  /**
   * Transforms the input reduction body into two pieces.
   * Requires that the context domain of the body be a single polyhedron.
   * This is achieved simply replacing the body with a case expression involving
   * two branches. Each branch has the same expression, but are restricted to
   * one side of the split.
   * 
   * Inputs:
   * 		are: reduction expression to be split
   *    split: an equality constraint
   * 
   * Let DE be the context domain of the expression E.
   * Let DS be the half-space defined by the inequality form of the constraint split
   * Let DS' be the negation of DS (i.e., the opposite half-space)
   * 
   * before: reduce(op, f, E)
   * after:  reduce(op, f, case {DS : E; DS' : E; })
   * 
   * Note that the reference to are is no longer contained in the AST since the last
   * step involves calling PermutationCaseReduce.
   */
  public static void apply(final AbstractReduceExpression are, final ISLConstraint split) {
    try {
      int _nbBasicSets = are.getBody().getContextDomain().getNbBasicSets();
      boolean _greaterThan = (_nbBasicSets > 1);
      if (_greaterThan) {
        throw new Exception("Cannot split a reduction body with multiple basic sets");
      }
      if ((split == null)) {
        SplitReduction.apply(are);
        return;
      }
      final Pair<ISLConstraint, ISLConstraint> constraints = SplitReduction.inequalityConstraints(split.getAff());
      final ISLSet DS = constraints.getKey().toBasicSet().toSet();
      final ISLSet DSp = constraints.getValue().toBasicSet().toSet();
      final CaseExpression caseExpr = AlphaUserFactory.createCaseExpression();
      EList<AlphaExpression> _exprs = caseExpr.getExprs();
      RestrictExpression _createRestrictExpression = AlphaUserFactory.createRestrictExpression(DS, AlphaUtil.<AlphaExpression>copyAE(are.getBody()));
      _exprs.add(_createRestrictExpression);
      EList<AlphaExpression> _exprs_1 = caseExpr.getExprs();
      RestrictExpression _createRestrictExpression_1 = AlphaUserFactory.createRestrictExpression(DSp, AlphaUtil.<AlphaExpression>copyAE(are.getBody()));
      _exprs_1.add(_createRestrictExpression_1);
      EcoreUtil.replace(are.getBody(), caseExpr);
      AlphaInternalStateConstructor.recomputeContextDomain(are);
      Normalize.apply(are);
      PermutationCaseReduce.apply(are);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Given an ISLAff, construct two inequality ISLConstraints (>= and <)
   */
  public static Pair<ISLConstraint, ISLConstraint> inequalityConstraints(final ISLAff splitAff) {
    final ISLConstraint DS = splitAff.copy().toInequalityConstraint();
    long _constant = splitAff.getConstant();
    final int const_ = Long.valueOf((_constant - 1)).intValue();
    final ISLConstraint DSp = splitAff.copy().negate().setConstant(const_).toInequalityConstraint();
    return Pair.<ISLConstraint, ISLConstraint>of(DS, DSp);
  }

  public static ISLSet toSet(final ISLConstraint[] constraints, final ISLSpace space) {
    final Function2<ISLSet, ISLConstraint, ISLSet> _function = (ISLSet ret, ISLConstraint c) -> {
      return ret.union(c.toBasicSet().toSet());
    };
    return IterableExtensions.<ISLConstraint, ISLSet>fold(((Iterable<ISLConstraint>)Conversions.doWrapArray(constraints)), ISLSet.buildUniverse(space.copy()), _function);
  }

  /**
   * Returns true if the body domain is 2D and there exist a pair of opposing edges with an
   * overlapping component in the answer domain.
   * 
   * Returns true if there exists the pair (Fi,Fj) s.t. both:
   *   1) fp(Fi) \cap fp(Fj) != empty
   *   2) (rho.dot(vi))*(rho.dot(vj)<0
   * or false, otherwise.
   */
  public static boolean requiresFractalSplits(final AbstractReduceExpression are) {
    final Face face = are.getFacet();
    int _dimensionality = face.getDimensionality();
    boolean _notEquals = (_dimensionality != 2);
    if (_notEquals) {
      return false;
    }
    final ArrayList<Face> edges = face.generateChildren();
    int _size = edges.size();
    final Function1<Integer, Iterable<Pair<Face, Face>>> _function = (Integer i) -> {
      int _size_1 = edges.size();
      final Function1<Integer, Pair<Face, Face>> _function_1 = (Integer j) -> {
        Face _get = edges.get((i).intValue());
        Face _get_1 = edges.get((j).intValue());
        return Pair.<Face, Face>of(_get, _get_1);
      };
      return IterableExtensions.<Integer, Pair<Face, Face>>map(new ExclusiveRange(((i).intValue() + 1), _size_1, true), _function_1);
    };
    final ArrayList<Pair<Face, Face>> edgePairs = CommonExtensions.<Pair<Face, Face>>toArrayList(IterableExtensions.<Integer, Pair<Face, Face>>flatMap(new ExclusiveRange(0, _size, true), _function));
    final ISLMap fp = are.getProjection().toMap();
    try {
      final List<Long> rho = AffineFunctionOperations.getConstantVectorNoParams(SplitReduction.construct1DBasis(SplitReduction.getReuseMaff(are.getBody())));
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        InputOutput.println();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    final ISLMultiAff a = SplitReduction.getReuseMaff(are.getBody());
    final ISLMultiAff b = SplitReduction.construct1DBasis(a.copy());
    final List<Long> c = AffineFunctionOperations.getConstantVectorNoParams(b.copy());
    final List<Long> rho = AffineFunctionOperations.getConstantVectorNoParams(SplitReduction.construct1DBasis(SplitReduction.getReuseMaff(are.getBody())));
    final Function2<ISLSet, Face, ISLSet> _function_1 = (ISLSet ret, Face v) -> {
      return ret.union(v.toSet());
    };
    final ISLSet vertices = IterableExtensions.<Face, ISLSet>fold(((Iterable<Face>)Conversions.doWrapArray(face.getVertices())), 
      ISLSet.buildEmpty(are.getBody().getContextDomain().getSpace()), _function_1);
    final HashMap<Face, ISLSet> disjointEdges = CollectionLiterals.<Face, ISLSet>newHashMap();
    final Consumer<Face> _function_2 = (Face e) -> {
      disjointEdges.put(e, e.toSet().subtract(vertices.copy()));
    };
    edges.forEach(_function_2);
    final Function1<Pair<Face, Face>, Boolean> _function_3 = (Pair<Face, Face> it) -> {
      boolean _xblockexpression = false;
      {
        final ISLSet fi = disjointEdges.get(it.getKey()).copy();
        final ISLSet fj = disjointEdges.get(it.getValue()).copy();
        _xblockexpression = fi.apply(fp.copy()).intersect(fj.apply(fp.copy())).isEmpty();
      }
      return Boolean.valueOf(_xblockexpression);
    };
    final Function1<Pair<Face, Face>, Boolean> _function_4 = (Pair<Face, Face> it) -> {
      boolean _xblockexpression = false;
      {
        final long[] vi = ISLUtil.toLinearUnitVector(it.getKey().getNormalVector(face));
        final long[] vj = ISLUtil.toLinearUnitVector(it.getValue().getNormalVector(face));
        long _dot = CommonExtensions.dot(vi, ((long[])Conversions.unwrapArray(rho, long.class)));
        long _dot_1 = CommonExtensions.dot(vj, ((long[])Conversions.unwrapArray(rho, long.class)));
        long _multiply = (_dot * _dot_1);
        _xblockexpression = (_multiply < 0);
      }
      return Boolean.valueOf(_xblockexpression);
    };
    return IterableExtensions.<Pair<Face, Face>>exists(IterableExtensions.<Pair<Face, Face>>reject(edgePairs, _function_3), _function_4);
  }

  /**
   * Splits the basic sets of domain into pieces on each side of aff
   * 
   * For example, given the following:
   *   domain: [N]->{[i] : 0<=i<N}
   *   aff: [N]->{[i,j]->[i-10]}
   * 
   * the following ISLSet with 2 ISLBasicSets is returned:
   *   [N]->{[i] : 0<=i<10; [i] : 10<=i<N}
   * 
   * Coalescing the output set should yield the same input set
   */
  public static ISLSet separateBasicSets(final ISLSet domain, final ISLAff aff) {
    try {
      final Pair<ISLConstraint, ISLConstraint> constraints = SplitReduction.inequalityConstraints(aff);
      final ISLConstraint upperBound = constraints.getKey();
      final ISLConstraint lowerBound = constraints.getValue();
      final ISLSet upperPiece = domain.copy().intersect(upperBound.toBasicSet().toSet());
      final ISLSet lowerPiece = domain.copy().intersect(lowerBound.toBasicSet().toSet());
      final ISLSet ret = upperPiece.union(lowerPiece);
      boolean _isEqual = ret.copy().coalesce().isEqual(domain.copy());
      boolean _not = (!_isEqual);
      if (_not) {
        throw new Exception("failed to separate domain");
      }
      return ret;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Transforms the reduction body into as many pieces as possible by making invariant
   * splits (i.e., splits containing the reuse space) thru all (d-2)-faces.
   * 
   * Assumes the input reduction body is 2-dimensional
   */
  public static void apply(final AbstractReduceExpression are) {
    try {
      final Face face = are.getFacet();
      int _dimensionality = face.getDimensionality();
      boolean _notEquals = (_dimensionality != 2);
      if (_notEquals) {
        return;
      }
      Equation _containerEquation = AlphaUtil.getContainerEquation(are);
      final StandardEquation eq = ((StandardEquation) _containerEquation);
      if ((!(eq instanceof StandardEquation))) {
        throw new Exception("Reduce expression container must be a standard equation");
      }
      final StandardEquation stdEq = ((StandardEquation) eq);
      final Function1<ISLConstraint, ISLAff> _function = (ISLConstraint it) -> {
        return it.getAff();
      };
      final Function1<ISLAff, ISLAff> _function_1 = (ISLAff it) -> {
        ISLAff _xblockexpression = null;
        {
          SplitReduction.debug(("found split aff: " + it));
          _xblockexpression = it;
        }
        return _xblockexpression;
      };
      final Function2<ISLSet, ISLAff, ISLSet> _function_2 = (ISLSet ret, ISLAff aff) -> {
        return SplitReduction.separateBasicSets(ret, aff);
      };
      ISLSet branchDomains = IterableExtensions.<ISLAff, ISLSet>fold(CommonExtensions.<ISLAff>toArrayList(ListExtensions.<ISLAff, ISLAff>map(ListExtensions.<ISLConstraint, ISLAff>map(((List<ISLConstraint>)Conversions.doWrapArray(SplitReduction.enumerateCandidateSplits(are))), _function), _function_1)), 
        are.getBody().getContextDomain().copy(), _function_2);
      final CaseExpression caseExpr = AlphaUserFactory.createCaseExpression();
      final Consumer<ISLBasicSet> _function_3 = (ISLBasicSet domain) -> {
        EList<AlphaExpression> _exprs = caseExpr.getExprs();
        RestrictExpression _createRestrictExpression = AlphaUserFactory.createRestrictExpression(domain.copy().toSet(), AlphaUtil.<AlphaExpression>copyAE(are.getBody()));
        _exprs.add(_createRestrictExpression);
        SplitReduction.debug(("created case branch: " + domain));
      };
      branchDomains.getBasicSets().forEach(_function_3);
      EcoreUtil.replace(are.getBody(), caseExpr);
      AlphaInternalStateConstructor.recomputeContextDomain(are);
      Normalize.apply(are);
      PermutationCaseReduce.apply(are);
      NormalizeReduction.apply(stdEq);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Returns the list of candidate splits that separate the reduction body into
   * two non-empty pieces.
   * These splits are guaranteed to introduce new strong-boundary or strong-invariant
   * faces by definition since they are constructed from the accumulation space and
   * reuse space accordingly. This requires that the accumulation space and/or the
   * reuse space have precisely as 1D null space. If these spaces are larger then
   * candidate splits can be constructed.
   * This method is primarily intended to be called by OptimalSimplifyingReductions
   * which systematically applies ReductionDecomposition and DependenceRaising, so this
   * should result in the existence of valid splits at some point in the exploration.
   */
  public static ISLConstraint[] enumerateCandidateSplits(final AbstractReduceExpression are) {
    ISLConstraint[] _xblockexpression = null;
    {
      if (SplitReduction.DEBUG) {
        final Equation eq = AlphaUtil.getContainerEquation(are);
        StandardEquation _xifexpression = null;
        if ((eq instanceof StandardEquation)) {
          _xifexpression = ((StandardEquation) eq);
        } else {
          _xifexpression = null;
        }
        final StandardEquation stdEq = _xifexpression;
        String _xifexpression_1 = null;
        if ((stdEq != null)) {
          _xifexpression_1 = stdEq.getVariable().getName();
        } else {
          _xifexpression_1 = (("" + ": ") + are);
        }
        String _plus = ("enumerating splits for Equation " + _xifexpression_1);
        SplitReduction.debug(_plus);
      }
      final ISLMultiAff fp = are.getProjection();
      final ISLMultiAff fd = SplitReduction.getReuseMaff(are.getBody());
      _xblockexpression = SplitReduction.enumerateCandidateSplits(are.getFacet(), fp, fd);
    }
    return _xblockexpression;
  }

  public static ISLConstraint[] enumerateCandidateSplits(final Face bodyFace, final ISLMultiAff fp, final ISLMultiAff fd) {
    final ArrayList<ISLConstraint> splits = CollectionLiterals.<ISLConstraint>newArrayList();
    final ISLBasicSet bodyDomain = bodyFace.toBasicSet();
    final int bodyDim = bodyFace.getDimensionality();
    if ((bodyDim <= 1)) {
      return ((ISLConstraint[])Conversions.unwrapArray(splits, ISLConstraint.class));
    }
    final Function1<Face, ISLBasicSet> _function = (Face it) -> {
      return it.toBasicSet();
    };
    final List<ISLBasicSet> faces = ListExtensions.<Face, ISLBasicSet>map(bodyFace.getLattice().getFaces((bodyDim - 2)), _function);
    final ISLMultiAff accVec = SplitReduction.construct1DBasis(fp);
    if ((accVec != null)) {
      final Function1<ISLBasicSet, ISLConstraint> _function_1 = (ISLBasicSet it) -> {
        return SplitReduction.constructSplit(it.copy(), accVec);
      };
      final Function1<ISLConstraint, Boolean> _function_2 = (ISLConstraint s) -> {
        return Boolean.valueOf((s == null));
      };
      Iterables.<ISLConstraint>addAll(splits, IterableExtensions.<ISLConstraint>reject(ListExtensions.<ISLBasicSet, ISLConstraint>map(faces, _function_1), _function_2));
    }
    ISLMultiAff _xifexpression = null;
    if ((fd != null)) {
      _xifexpression = SplitReduction.construct1DBasis(fd);
    } else {
      _xifexpression = null;
    }
    final ISLMultiAff reuseVec = _xifexpression;
    if ((reuseVec != null)) {
      final Function1<ISLBasicSet, ISLConstraint> _function_3 = (ISLBasicSet it) -> {
        return SplitReduction.constructSplit(it.copy(), reuseVec);
      };
      final Function1<ISLConstraint, Boolean> _function_4 = (ISLConstraint s) -> {
        return Boolean.valueOf((s == null));
      };
      Iterables.<ISLConstraint>addAll(splits, IterableExtensions.<ISLConstraint>reject(ListExtensions.<ISLBasicSet, ISLConstraint>map(faces, _function_3), _function_4));
    }
    final Function1<ISLConstraint, Boolean> _function_5 = (ISLConstraint s) -> {
      return Boolean.valueOf(SplitReduction.isUseful(s, bodyDomain));
    };
    final Iterable<ISLConstraint> usefulSplits = IterableExtensions.<ISLConstraint>filter(splits, _function_5);
    final Consumer<ISLConstraint> _function_6 = (ISLConstraint s) -> {
      String _string = s.toString();
      String _plus = ("(enumerateCandidateSplits) " + _string);
      SplitReduction.debug(_plus);
    };
    usefulSplits.forEach(_function_6);
    return ((ISLConstraint[])Conversions.unwrapArray(usefulSplits, ISLConstraint.class));
  }

  /**
   * Returns true if the constraint splits the set into two non-empty pieces, or
   * false otherwise.
   */
  public static boolean isUseful(final ISLConstraint split, final ISLBasicSet bset) {
    final ISLSet splitSets = bset.copy().toSet().subtract(split.copy().toBasicSet().toSet());
    final int nbFreeDims = ISLUtil.dimensionality(bset);
    final Function1<ISLBasicSet, Boolean> _function = (ISLBasicSet s) -> {
      int _dimensionality = ISLUtil.dimensionality(s);
      return Boolean.valueOf((_dimensionality == nbFreeDims));
    };
    final Function2<Boolean, Boolean, Boolean> _function_1 = (Boolean v1, Boolean v2) -> {
      return Boolean.valueOf(((v1).booleanValue() && (v2).booleanValue()));
    };
    final Boolean sameNbFreeDims = IterableExtensions.<Boolean>reduce(ListExtensions.<ISLBasicSet, Boolean>map(splitSets.getBasicSets(), _function), _function_1);
    int _nbBasicSets = splitSets.getNbBasicSets();
    final boolean has2Pieces = (_nbBasicSets == 2);
    return (has2Pieces && (sameNbFreeDims).booleanValue());
  }

  /**
   * This function "extends" the set infinitely along the direction of vec via
   * the transitive closure of vec's ISLMap representation. The extended set is
   * guaranteed to have a single equality constraint by construction.
   */
  public static ISLConstraint constructSplit(final ISLBasicSet edge, final ISLMultiAff vec) {
    try {
      ISLConstraint _xblockexpression = null;
      {
        final int nbOut = edge.dim(ISLDimType.isl_dim_out);
        final ISLBasicSet setNoParams = edge.copy().dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, edge.dim(ISLDimType.isl_dim_out));
        JNIPtrBoolean exact = new JNIPtrBoolean();
        final ISLMap map = vec.copy().toMap().transitiveClosure(exact);
        if ((!exact.value)) {
          throw new Exception("transitive closure should be exact, something is wrong");
        }
        int _nbBasicMaps = map.getNbBasicMaps();
        boolean _notEquals = (_nbBasicMaps != 1);
        if (_notEquals) {
          throw new Exception("transitive closure should have a single basic map");
        }
        final ISLBasicSet hyperplane = AlphaUtil.renameIndices(setNoParams.copy().apply(map.getBasicMapAt(0)), setNoParams.getIndexNames());
        final Function1<ISLConstraint, Boolean> _function = (ISLConstraint it) -> {
          return Boolean.valueOf(it.isEquality());
        };
        final Function1<ISLConstraint, Boolean> _function_1 = (ISLConstraint it) -> {
          return Boolean.valueOf(it.involvesDims(ISLDimType.isl_dim_out, 0, nbOut));
        };
        final Iterable<ISLConstraint> eqConstraints = IterableExtensions.<ISLConstraint>filter(IterableExtensions.<ISLConstraint>filter(hyperplane.getConstraints(), _function), _function_1);
        int _size = IterableExtensions.size(eqConstraints);
        boolean _notEquals_1 = (_size != 1);
        if (_notEquals_1) {
          return null;
        }
        final ISLConstraint splitConstraint = ((ISLConstraint[])Conversions.unwrapArray(eqConstraints, ISLConstraint.class))[0];
        _xblockexpression = splitConstraint;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Returns the 1D vector that spans the null space of the input ISLMultiAff.
   * maff is required to have exactly one fewer output dimension than input
   * dimensions.
   */
  public static ISLMultiAff construct1DBasis(final ISLMultiAff maff) {
    final int nbIn = maff.dim(ISLDimType.isl_dim_in);
    final int nbOut = maff.dim(ISLDimType.isl_dim_out);
    final int nbParam = maff.dim(ISLDimType.isl_dim_param);
    final long[][] fullKernel = AffineFunctionOperations.computeKernel(maff.copy().dropDims(ISLDimType.isl_dim_param, 0, nbParam));
    final long[][] kernel = MatrixOperations.submatrixColumn(fullKernel, 0, 0);
    final long[][] mat = MatrixOperations.columnBind(MatrixOperations.columnBindToFront(MatrixOperations.createIdentity(nbIn, nbIn), nbParam), kernel);
    final ISLMultiAff outMaff = MatrixOperations.toMatrix(mat, maff.getSpace().getParamNames(), maff.getSpace().getInputNames(), false, true).toMultiAff();
    return outMaff;
  }

  /**
   * Returns the ISLSet characterizing the null-space of the dependence function in
   * the reduction body.
   * These dispatch methods should match eventually since OptimalSimplifyingReductions
   * systematically calls ReductionDecomposition and RaiseDependence
   */
  protected static ISLMultiAff _getReuseMaff(final RestrictExpression re) {
    return SplitReduction.getReuseMaff(re, re.getExpr());
  }

  protected static ISLMultiAff _getReuseMaff(final RestrictExpression re, final DependenceExpression de) {
    return de.getFunction();
  }

  protected static ISLMultiAff _getReuseMaff(final RestrictExpression re, final AlphaExpression ae) {
    return null;
  }

  protected static ISLMultiAff _getReuseMaff(final DependenceExpression de) {
    return de.getFunction();
  }

  protected static ISLMultiAff _getReuseMaff(final AlphaExpression ae) {
    return null;
  }

  public static ISLMultiAff getReuseMaff(final AlphaExpression de) {
    if (de instanceof DependenceExpression) {
      return _getReuseMaff((DependenceExpression)de);
    } else if (de instanceof RestrictExpression) {
      return _getReuseMaff((RestrictExpression)de);
    } else if (de != null) {
      return _getReuseMaff(de);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(de).toString());
    }
  }

  public static ISLMultiAff getReuseMaff(final RestrictExpression re, final AlphaExpression de) {
    if (de instanceof DependenceExpression) {
      return _getReuseMaff(re, (DependenceExpression)de);
    } else if (de != null) {
      return _getReuseMaff(re, de);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re, de).toString());
    }
  }
}
