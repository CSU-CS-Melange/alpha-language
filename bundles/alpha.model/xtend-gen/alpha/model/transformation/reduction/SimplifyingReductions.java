package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.CaseExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.matrix.MatrixOperations;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.PropagateSimpleEquations;
import alpha.model.transformation.SimplifyExpressions;
import alpha.model.transformation.SplitUnionIntoCase;
import alpha.model.util.AffineFunctionOperations;
import alpha.model.util.AlphaOperatorUtil;
import alpha.model.util.AlphaUtil;
import alpha.model.util.DomainOperations;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPoint;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Implementation of Theorem 5 in the original Simplifying Reductions paper.
 */
@SuppressWarnings("all")
public class SimplifyingReductions {
  /**
   * 'Struct' for storing basic elements (domains and functions)
   * used in the transformation. The primary purpose of this class
   * is to separate legality tests with the transformation.
   */
  protected static class BasicElements {
    protected long[][] kerQ;

    protected ISLMultiAff reuseDir;

    protected ISLMultiAff reuseDepProjected;

    protected ISLSet origDE;

    protected ISLSet DEp;

    protected ISLSet Dadd;

    protected ISLSet Dsub;

    protected ISLSet Dint;

    protected BINARY_OP invOP;
  }

  public static boolean DEBUG = false;

  /**
   * Setting this variable to true disables all the
   * post processing simplifications. Intended to be
   * used for debugging purposes or to check the
   * result of SR against the theorem in the paper.
   */
  public static boolean DISABLE_POST_PROCESSING = false;

  public static Function<SimplifyingReductions, String> defineXaddEquationName = ((Function<SimplifyingReductions, String>) (SimplifyingReductions sr) -> {
    String _xblockexpression = null;
    {
      final String origName = sr.reductionEquation.getVariable().getName();
      String XaddName = (origName + "_pos");
      _xblockexpression = AlphaUtil.duplicateNameResolver().apply(sr.containerSystem, XaddName, "_");
    }
    return _xblockexpression;
  });

  public static Function<SimplifyingReductions, String> defineXsubEquationName = ((Function<SimplifyingReductions, String>) (SimplifyingReductions sr) -> {
    String _xblockexpression = null;
    {
      final String origName = sr.reductionEquation.getVariable().getName();
      String XaddName = (origName + "_neg");
      _xblockexpression = AlphaUtil.duplicateNameResolver().apply(sr.containerSystem, XaddName, "_");
    }
    return _xblockexpression;
  });

  private static void debug(final String msg) {
    if (SimplifyingReductions.DEBUG) {
      InputOutput.<String>println(("[SimplifyingReductions] " + msg));
    }
  }

  private final ReduceExpression targetReduce;

  private final StandardEquation reductionEquation;

  private final ISLMultiAff reuseDep;

  private final AlphaSystem containerSystem;

  private final SystemBody containerSystemBody;

  private SimplifyingReductions(final ReduceExpression reduce, final ISLMultiAff reuseDep) {
    this.targetReduce = reduce;
    Equation _containerEquation = AlphaUtil.getContainerEquation(reduce);
    this.reductionEquation = ((StandardEquation) _containerEquation);
    this.reuseDep = reuseDep;
    this.containerSystem = AlphaUtil.getContainerSystem(this.targetReduce);
    this.containerSystemBody = AlphaUtil.getContainerSystemBody(this.targetReduce);
  }

  public static void apply(final ReduceExpression reduce, final ISLMultiAff reuseDep) {
    final SimplifyingReductions sr = new SimplifyingReductions(reduce, reuseDep);
    sr.simplify();
  }

  public static void apply(final ReduceExpression reduce, final int[] reuseDepNoParams) {
    final Function1<Integer, Long> _function = (Integer v) -> {
      return Long.valueOf(((long) (v).intValue()));
    };
    SimplifyingReductions.apply(reduce, ((long[])Conversions.unwrapArray(ListExtensions.<Integer, Long>map(((List<Integer>)Conversions.doWrapArray(reuseDepNoParams)), _function), long.class)));
  }

  public static void apply(final ReduceExpression reduce, final long[] reuseDepNoParams) {
    SimplifyingReductions.apply(reduce, SimplifyingReductions.longVecToMultiAff(reduce, reuseDepNoParams));
  }

  public static long[] asLongVector(final ISLMultiAff reuseDep) {
    final Function1<ISLAff, Long> _function = (ISLAff aff) -> {
      return Long.valueOf(aff.getConstant());
    };
    return ((long[])Conversions.unwrapArray(ListExtensions.<ISLAff, Long>map(reuseDep.getAffs(), _function), long.class));
  }

  protected static ReduceExpression createXadd(final AbstractReduceExpression reduceExpr, final SimplifyingReductions.BasicElements BE) {
    final ISLSet restrictDom = BE.origDE.copy().subtract(BE.DEp.copy());
    final RestrictExpression restrictExpr = AlphaUserFactory.createRestrictExpression(restrictDom, EcoreUtil.<AlphaExpression>copy(reduceExpr.getBody()));
    return AlphaUserFactory.createReduceExpression(reduceExpr.getOperator(), reduceExpr.getProjection(), restrictExpr);
  }

  protected void simplify() {
    final SimplifyingReductions.BasicElements BE = SimplifyingReductions.computeBasicElements(this.targetReduce, this.reuseDep);
    final String XaddName = SimplifyingReductions.defineXaddEquationName.apply(this);
    ReduceExpression Xadd = ((ReduceExpression) null);
    {
      Xadd = SimplifyingReductions.createXadd(this.targetReduce, BE);
      AlphaExpression _body = Xadd.getBody();
      final ISLSet restrictDom = ((RestrictExpression) _body).getRestrictDomain();
      final ISLSet XaddDom = restrictDom.copy().apply(this.targetReduce.getProjection().toMap());
      final Variable XaddVar = AlphaUserFactory.createVariable(XaddName, XaddDom);
      final StandardEquation XaddEq = AlphaUserFactory.createStandardEquation(XaddVar, Xadd);
      this.containerSystem.getLocals().add(XaddVar);
      this.containerSystemBody.getEquations().add(XaddEq);
      AlphaInternalStateConstructor.recomputeContextDomain(XaddEq);
    }
    final String XsubName = SimplifyingReductions.defineXsubEquationName.apply(this);
    ReduceExpression Xsub = ((ReduceExpression) null);
    boolean _isEmpty = BE.Dsub.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      final ISLSet restrictDom = BE.DEp.copy().subtract(BE.origDE.copy());
      final ISLSet DintPreimage = BE.Dint.copy().preimage(this.targetReduce.getProjection());
      final DependenceExpression depExpr = AlphaUserFactory.createDependenceExpression(this.reuseDep.copy(), EcoreUtil.<AlphaExpression>copy(this.targetReduce.getBody()));
      final RestrictExpression innerRestrict = AlphaUserFactory.createRestrictExpression(restrictDom, depExpr);
      final RestrictExpression outerRestrict = AlphaUserFactory.createRestrictExpression(DintPreimage, innerRestrict);
      Xsub = AlphaUserFactory.createReduceExpression(this.targetReduce.getOperator(), this.targetReduce.getProjection(), outerRestrict);
      final ISLSet XsubDom = restrictDom.copy().apply(this.targetReduce.getProjection().toMap()).intersect(BE.Dint.copy());
      final Variable XsubVar = AlphaUserFactory.createVariable(XsubName, XsubDom);
      final StandardEquation XsubEq = AlphaUserFactory.createStandardEquation(XsubVar, Xsub);
      this.containerSystem.getLocals().add(XsubVar);
      this.containerSystemBody.getEquations().add(XsubEq);
      AlphaInternalStateConstructor.recomputeContextDomain(XsubEq);
    }
    final CaseExpression mainCaseExpr = AlphaUserFactory.createCaseExpression();
    final BINARY_OP binaryOp = AlphaOperatorUtil.reductionOPtoBinaryOP(this.targetReduce.getOperator());
    final VariableExpression XaddRef = AlphaUserFactory.createVariableExpression(this.containerSystem.getVariable(XaddName));
    final VariableExpression Xref = AlphaUserFactory.createVariableExpression(this.reductionEquation.getVariable());
    final DependenceExpression reuseExpr = AlphaUserFactory.createDependenceExpression(BE.reuseDepProjected.copy(), Xref);
    {
      final ISLSet restrictDom_1 = BE.Dadd.copy().subtract(BE.Dint.copy());
      final RestrictExpression branch1expr = AlphaUserFactory.createRestrictExpression(restrictDom_1, EcoreUtil.<VariableExpression>copy(XaddRef));
      mainCaseExpr.getExprs().add(branch1expr);
    }
    {
      final ISLSet restrictDom_1 = BE.Dint.copy().subtract(BE.Dadd.copy().union(BE.Dsub.copy()));
      final RestrictExpression branch2expr = AlphaUserFactory.createRestrictExpression(restrictDom_1, EcoreUtil.<DependenceExpression>copy(reuseExpr));
      mainCaseExpr.getExprs().add(branch2expr);
    }
    {
      final ISLSet restrictDom_1 = BE.Dadd.copy().intersect(BE.Dint.copy().subtract(BE.Dsub.copy()));
      final BinaryExpression binaryExpr = AlphaUserFactory.createBinaryExpression(binaryOp, EcoreUtil.<VariableExpression>copy(XaddRef), EcoreUtil.<DependenceExpression>copy(reuseExpr));
      final RestrictExpression branch3expr = AlphaUserFactory.createRestrictExpression(restrictDom_1, binaryExpr);
      mainCaseExpr.getExprs().add(branch3expr);
    }
    boolean _isEmpty_1 = BE.Dsub.isEmpty();
    boolean _not_1 = (!_isEmpty_1);
    if (_not_1) {
      final VariableExpression XsubRef = AlphaUserFactory.createVariableExpression(this.containerSystem.getVariable(XsubName));
      {
        final ISLSet restrictDom_1 = BE.Dsub.copy().intersect(BE.Dint.copy().subtract(BE.Dadd.copy()));
        final BinaryExpression binaryExpr = AlphaUserFactory.createBinaryExpression(BE.invOP, EcoreUtil.<DependenceExpression>copy(reuseExpr), EcoreUtil.<VariableExpression>copy(XsubRef));
        final RestrictExpression branch4expr = AlphaUserFactory.createRestrictExpression(restrictDom_1, binaryExpr);
        mainCaseExpr.getExprs().add(branch4expr);
      }
      {
        final ISLSet restrictDom_1 = BE.Dadd.copy().intersect(BE.Dint.copy().intersect(BE.Dsub.copy()));
        final BinaryExpression binaryExprAdd = AlphaUserFactory.createBinaryExpression(binaryOp, XaddRef, reuseExpr);
        final BinaryExpression binaryExprSub = AlphaUserFactory.createBinaryExpression(BE.invOP, binaryExprAdd, XsubRef);
        final RestrictExpression branch5expr = AlphaUserFactory.createRestrictExpression(restrictDom_1, binaryExprSub);
        mainCaseExpr.getExprs().add(branch5expr);
      }
    }
    EcoreUtil.replace(this.targetReduce, mainCaseExpr);
    AlphaInternalStateConstructor.recomputeContextDomain(this.reductionEquation);
    if ((!SimplifyingReductions.DISABLE_POST_PROCESSING)) {
      PropagateSimpleEquations.apply(this.containerSystemBody);
      Normalize.apply(this.containerSystemBody);
      if ((Xadd != null)) {
        SimplifyingReductions.splitReduction(Xadd);
      }
      if ((Xsub != null)) {
        SimplifyingReductions.splitReduction(Xsub);
      }
      SimplifyExpressions.apply(this.containerSystemBody);
      Normalize.apply(this.containerSystemBody);
    }
    AlphaInternalStateConstructor.recomputeContextDomain(this.containerSystemBody);
  }

  /**
   * Splits a reduction with a non-convex body into cases of reductions
   * with convex bodies.
   */
  public static void splitReduction(final ReduceExpression re) {
    int _nbBasicSets = re.getContextDomain().getNbBasicSets();
    boolean _equals = (_nbBasicSets == 1);
    if (_equals) {
      return;
    }
    final SystemBody body = AlphaUtil.getContainerSystemBody(re);
    SplitUnionIntoCase.apply(re);
    PermutationCaseReduce.apply(re);
    AlphaInternalStateConstructor.recomputeContextDomain(body);
  }

  /**
   * Computes BasicElements while performing all the legality tests.
   */
  public static SimplifyingReductions.BasicElements computeBasicElements(final AbstractReduceExpression reduce, final ISLMultiAff reuseDep) {
    final SimplifyingReductions.BasicElements BE = new SimplifyingReductions.BasicElements();
    int _nbBasicSets = reduce.getContextDomain().getNbBasicSets();
    boolean _greaterThan = (_nbBasicSets > 1);
    if (_greaterThan) {
      throw new RuntimeException("The context of the reduction body must be a single polyhedron.");
    }
    int _nbIndices = reduce.getBody().getContextDomain().getNbIndices();
    int _nbInputs = reuseDep.getNbInputs();
    boolean _notEquals = (_nbIndices != _nbInputs);
    if (_notEquals) {
      throw new RuntimeException("Given reuse dependence does not match the dimensionality of the reduction body.");
    }
    BE.kerQ = DomainOperations.kernelOfLinearPart(reduce.getBody().getContextDomain());
    if ((BE.kerQ != null)) {
      throw new RuntimeException("The body of the target ReduceExpression has non-empty ker(Q); kernel of the linear part of the domain. This case is currently not handled.");
    }
    BE.reuseDir = AffineFunctionOperations.negateUniformFunction(reuseDep);
    BE.reuseDepProjected = SimplifyingReductions.constructDependenceFunctionInAnswerSpace(reduce.getContextDomain().getSpace(), reduce.getProjection(), reuseDep);
    boolean _isIdentity = BE.reuseDepProjected.isIdentity();
    if (_isIdentity) {
      throw new RuntimeException("The reuse dependence is in the kernel of the projection function.");
    }
    BE.reuseDir = AffineFunctionOperations.negateUniformFunction(reuseDep);
    BE.origDE = reduce.getBody().getContextDomain();
    BE.DEp = BE.origDE.copy().apply(BE.reuseDir.toMap());
    BE.Dadd = BE.origDE.copy().subtract(BE.DEp.copy()).apply(reduce.getProjection().toMap()).intersect(reduce.getContextDomain());
    BE.Dsub = BE.DEp.copy().subtract(BE.origDE.copy()).apply(reduce.getProjection().toMap()).intersect(reduce.getContextDomain());
    BE.Dint = BE.origDE.copy().intersect(BE.DEp.copy()).apply(reduce.getProjection().toMap()).intersect(reduce.getContextDomain());
    boolean _isEmpty = BE.Dint.isEmpty();
    if (_isEmpty) {
      throw new RuntimeException("Initialization domain is empty; input reuse vector is invalid.");
    }
    boolean _isEmpty_1 = BE.Dsub.isEmpty();
    boolean _not = (!_isEmpty_1);
    if (_not) {
      BE.invOP = AlphaOperatorUtil.reductionOPtoBinaryInverseOP(reduce.getOperator());
    }
    return BE;
  }

  public static boolean testLegality(final AbstractReduceExpression reduce, final int[] reuseDepNoParams) {
    final Function1<Integer, Long> _function = (Integer v) -> {
      return Long.valueOf(((long) (v).intValue()));
    };
    return SimplifyingReductions.testLegality(reduce, ((long[])Conversions.unwrapArray(ListExtensions.<Integer, Long>map(((List<Integer>)Conversions.doWrapArray(reuseDepNoParams)), _function), long.class)));
  }

  public static boolean testLegality(final AbstractReduceExpression reduce, final long[] reuseDepNoParams) {
    return SimplifyingReductions.testLegality(reduce, SimplifyingReductions.longVecToMultiAff(reduce, reuseDepNoParams));
  }

  public static boolean testLegality(final AbstractReduceExpression reduce, final ISLMultiAff reuseDep) {
    try {
      SimplifyingReductions.computeBasicElements(reduce, reuseDep);
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException re = (RuntimeException)_t;
        SimplifyingReductions.debug(re.getMessage());
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return true;
  }

  /**
   * The reuse dependence is specified in the space of reduction body,
   * but the actual dependence will be among the reduction instance.
   * Thus, the projection of the reuse must be computed.
   * 
   * In this method, this process is implemented as evaluating the
   * constant vector representing the uniform reuse dependence by
   * the projection function, and then reconstructing the uniform
   * function from the result of the evaluation.
   */
  public static ISLMultiAff constructDependenceFunctionInAnswerSpace(final ISLSpace variableDomainSpace, final ISLMultiAff projection, final ISLMultiAff reuseDep) {
    final List<Long> b = AffineFunctionOperations.getConstantVector(reuseDep);
    final int nbParams = reuseDep.getDomainSpace().getNbParams();
    ISLPoint point = ISLPoint.buildZero(reuseDep.getDomainSpace());
    int _size = b.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer d : _doubleDotLessThan) {
      {
        ISLDimType _xifexpression = null;
        if (((d).intValue() < nbParams)) {
          _xifexpression = ISLDimType.isl_dim_param;
        } else {
          _xifexpression = ISLDimType.isl_dim_set;
        }
        final ISLDimType dimType = _xifexpression;
        Integer _xifexpression_1 = null;
        if (((d).intValue() < nbParams)) {
          _xifexpression_1 = d;
        } else {
          _xifexpression_1 = Integer.valueOf(((d).intValue() - nbParams));
        }
        final Integer pos = _xifexpression_1;
        final ISLVal v = ISLVal.buildFromLong(ISLContext.getInstance(), (b.get((d).intValue())).longValue());
        point = point.setCoordinate(dimType, (pos).intValue(), v);
      }
    }
    int _nbOutputs = projection.getNbOutputs();
    int _plus = (nbParams + _nbOutputs);
    final ArrayList<Long> projectedB = new ArrayList<Long>(_plus);
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, nbParams, true);
    for (final Integer d_1 : _doubleDotLessThan_1) {
      projectedB.add(Long.valueOf(0l));
    }
    List<ISLAff> _affs = projection.getAffs();
    for (final ISLAff aff : _affs) {
      projectedB.add(Long.valueOf(aff.eval(point.copy()).asLong()));
    }
    final ISLSpace space = ISLSpace.idMapDimFromSetDim(variableDomainSpace.copy());
    return AffineFunctionOperations.createUniformFunction(space, projectedB);
  }

  /**
   * Creates a list of Pair<ISLMultiAff, ISLMultiAff> that are valid projection functions for decomposing
   * the given reduction. The first element is the projection for the inner reduction.
   * 
   * The candidates are created using two methods:
   *  1. Boundary Constraints. For each constraint c in the domain of the reduction body,
   * a function f such that ker(f) = ker(original projection) \intersect ker(c) becomes a candidate.
   *  2. Distributivity. When the reduction body has share space RE,
   * a function f such that ker(f) = ker(original projection) \intersect RE becomes a candidate.
   */
  public static LinkedList<Pair<ISLMultiAff, ISLMultiAff>> generateDecompositionCandidates(final ShareSpaceAnalysisResult SSAR, final AbstractReduceExpression targetRE) {
    final List<Map.Entry<AlphaExpression, long[][]>> exprREs = SSAR.getExpressionsWithReuse();
    final long[][] kerF = MatrixOperations.transpose(AffineFunctionOperations.computeKernel(targetRE.getProjection()));
    final TreeSet<long[][]> kerFps = new TreeSet<long[][]>(new Comparator<long[][]>() {
      @Override
      public int compare(final long[][] o1, final long[][] o2) {
        final String str1 = MatrixOperations.toString(o1);
        final String str2 = MatrixOperations.toString(o2);
        return str1.compareTo(str2);
      }
    });
    final Function1<Map.Entry<AlphaExpression, long[][]>, Boolean> _function = (Map.Entry<AlphaExpression, long[][]> exprRE) -> {
      int _nbIndices = exprRE.getKey().getContextDomain().getNbIndices();
      int _nbIndices_1 = targetRE.getBody().getContextDomain().getNbIndices();
      return Boolean.valueOf((_nbIndices == _nbIndices_1));
    };
    Iterable<Map.Entry<AlphaExpression, long[][]>> _filter = IterableExtensions.<Map.Entry<AlphaExpression, long[][]>>filter(exprREs, _function);
    for (final Map.Entry<AlphaExpression, long[][]> exprRE : _filter) {
      {
        final long[][] intersection = MatrixOperations.kernelIntersection(exprRE.getValue(), kerF);
        if ((intersection != null)) {
          kerFps.add(intersection);
        }
      }
    }
    final List<ISLConstraint> constraints = targetRE.getBody().getContextDomain().getBasicSetAt(0).getConstraints();
    for (final ISLConstraint c : constraints) {
      {
        final long[][] kerC = MatrixOperations.transpose(DomainOperations.kernelOfLinearPart(c.copy().toBasicSet()));
        final long[][] ker = MatrixOperations.kernelIntersection(kerF, kerC);
        if ((ker != null)) {
          kerFps.add(ker);
        }
      }
    }
    final LinkedList<Pair<ISLMultiAff, ISLMultiAff>> candidates = new LinkedList<Pair<ISLMultiAff, ISLMultiAff>>();
    final List<String> params = targetRE.getBody().getExpressionDomain().getParamNames();
    List<String> indices = targetRE.getBody().getExpressionDomain().getIndexNames();
    if ((indices == null)) {
      indices = AlphaUtil.defaultDimNames(targetRE.getBody().getExpressionDomain().getNbIndices());
    }
    for (final long[][] RE : kerFps) {
      {
        final ISLMultiAff Fp = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, RE);
        int _nbOutputs = Fp.getNbOutputs();
        int _nbOutputs_1 = targetRE.getProjection().getNbOutputs();
        boolean _greaterThan = (_nbOutputs > _nbOutputs_1);
        if (_greaterThan) {
          final ISLMultiAff Fpp = AffineFunctionOperations.projectFunctionDomain(targetRE.getProjection(), Fp.copy());
          Pair<ISLMultiAff, ISLMultiAff> _pair = new Pair<ISLMultiAff, ISLMultiAff>(Fp, Fpp);
          candidates.add(_pair);
        }
      }
    }
    return candidates;
  }

  private static ISLMultiAff longVecToMultiAff(final AbstractReduceExpression reduce, final long[] reuseDepNoParams) {
    ISLMultiAff _xblockexpression = null;
    {
      final ISLSpace space = ISLSpace.idMapDimFromSetDim(reduce.getBody().getExpressionDomain().getSpace().copy());
      final long[] reuseDep = MatrixOperations.bindVector(new long[space.getNbParams()], reuseDepNoParams);
      _xblockexpression = AffineFunctionOperations.createUniformFunction(space.copy(), reuseDep);
    }
    return _xblockexpression;
  }
}
