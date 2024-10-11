package alpha.model.transformation.automation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.ComplexityCalculator;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.analysis.reduction.CandidateReuse;
import alpha.model.analysis.reduction.ReductionUtil;
import alpha.model.analysis.reduction.ShareSpaceAnalysis;
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult;
import alpha.model.matrix.MatrixOperations;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.SplitUnionIntoCase;
import alpha.model.transformation.reduction.Distributivity;
import alpha.model.transformation.reduction.FractalSimplify;
import alpha.model.transformation.reduction.HigherOrderOperator;
import alpha.model.transformation.reduction.Idempotence;
import alpha.model.transformation.reduction.NormalizeReduction;
import alpha.model.transformation.reduction.PermutationCaseReduce;
import alpha.model.transformation.reduction.ReductionComposition;
import alpha.model.transformation.reduction.ReductionDecomposition;
import alpha.model.transformation.reduction.RemoveIdenticalAnswers;
import alpha.model.transformation.reduction.SameOperatorSimplification;
import alpha.model.transformation.reduction.SimplifyingReductions;
import alpha.model.transformation.reduction.SplitReduction;
import alpha.model.util.AlphaOperatorUtil;
import alpha.model.util.AlphaUtil;
import alpha.model.util.Face;
import alpha.model.util.ISLUtil;
import alpha.model.util.Show;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Implements Algorithm 2 in the Simplifying Reductions paper. The current
 * implementation does not prune the search space. It simply explores all possible
 * combinations of reduction decomposition, dependence raising, and simplification
 * until termination or it finds the specified (via throttleLimit) number of
 * optimized versions, whichever comes first.
 */
@SuppressWarnings("all")
public class OptimalSimplifyingReductions {
  public static abstract class DynamicProgrammingStep {
    protected final EList<Integer> nodeID;

    protected AbstractReduceExpression re;

    public DynamicProgrammingStep(final AbstractReduceExpression targetRE) {
      this.nodeID = targetRE.getNodeID();
      this.re = targetRE;
    }

    public String toEqStr() {
      String _xblockexpression = null;
      {
        final Equation eq = AlphaUtil.getContainerEquation(this.re);
        String _xifexpression = null;
        if ((eq instanceof StandardEquation)) {
          _xifexpression = ((StandardEquation) eq).getVariable().getName();
        } else {
          _xifexpression = null;
        }
        final String eqVarName = _xifexpression;
        String _xifexpression_1 = null;
        if ((eqVarName != null)) {
          _xifexpression_1 = String.format(" to %s", eqVarName);
        } else {
          _xifexpression_1 = "";
        }
        _xblockexpression = _xifexpression_1;
      }
      return _xblockexpression;
    }

    public abstract String description();
  }

  public static class StepBeginEquation extends OptimalSimplifyingReductions.DynamicProgrammingStep {
    public StepBeginEquation(final AbstractReduceExpression targetRE) {
      super(targetRE);
    }

    @Override
    public String description() {
      return String.format("Optimize equation %s", this.toEqStr());
    }
  }

  public static class StepSimplifyingReduction extends OptimalSimplifyingReductions.DynamicProgrammingStep {
    private long[] reuseDepNoParams;

    public StepSimplifyingReduction(final AbstractReduceExpression targetRE, final long[] reuseDepNoParams, final int nbParams) {
      super(targetRE);
      this.reuseDepNoParams = reuseDepNoParams;
    }

    @Override
    public String description() {
      return String.format("Apply SimplifyingReduction%s with: %s", this.toEqStr(), MatrixOperations.toString(this.reuseDepNoParams));
    }

    public long[] getReuseDepNoParams() {
      return this.reuseDepNoParams;
    }
  }

  public static class StepIdempotence extends OptimalSimplifyingReductions.DynamicProgrammingStep {
    public StepIdempotence(final AbstractReduceExpression targetRE) {
      super(targetRE);
    }

    @Override
    public String description() {
      return String.format("Apply Idempotence");
    }
  }

  public static class StepHigherOrderOperator extends OptimalSimplifyingReductions.DynamicProgrammingStep {
    public StepHigherOrderOperator(final AbstractReduceExpression targetRE) {
      super(targetRE);
    }

    @Override
    public String description() {
      return String.format("Apply HigherOrderOperator");
    }
  }

  public static class StepReductionDecomposition extends OptimalSimplifyingReductions.DynamicProgrammingStep {
    private ISLMultiAff innerProjection;

    private ISLMultiAff outerProjection;

    public StepReductionDecomposition(final AbstractReduceExpression targetRE, final ISLMultiAff innerF, final ISLMultiAff outerF) {
      super(targetRE);
      this.innerProjection = innerF;
      this.outerProjection = outerF;
    }

    @Override
    public String description() {
      return String.format("Apply ReductionDecomposition%s with %s o %s", this.toEqStr(), this.outerProjection, this.innerProjection);
    }
  }

  public static class StepSplitReduction extends OptimalSimplifyingReductions.DynamicProgrammingStep {
    private ISLConstraint split;

    public StepSplitReduction(final AbstractReduceExpression targetRE) {
      super(targetRE);
    }

    public StepSplitReduction(final AbstractReduceExpression targetRE, final ISLConstraint split) {
      super(targetRE);
      this.split = split;
    }

    @Override
    public String description() {
      String _xifexpression = null;
      if ((this.split != null)) {
        _xifexpression = String.format("Apply SplitReduction%s with %s", this.toEqStr(), this.split);
      } else {
        _xifexpression = String.format("Apply SplitReduction%s", this.toEqStr());
      }
      return _xifexpression;
    }
  }

  public static class StepRemoveIndenticalAnswers extends OptimalSimplifyingReductions.DynamicProgrammingStep {
    private ISLMultiAff identicalAnswerBasis;

    private ISLSet identicalAnswerDomain;

    public StepRemoveIndenticalAnswers(final AbstractReduceExpression targetRE, final ISLMultiAff identicalAnswerBasis, final ISLSet identicalAnswerDomain) {
      super(targetRE);
      this.identicalAnswerBasis = identicalAnswerBasis;
      this.identicalAnswerDomain = identicalAnswerDomain;
    }

    @Override
    public String description() {
      return String.format("Apply RemoveIdenticalAnswers %s with %s", this.toEqStr(), this.identicalAnswerBasis);
    }
  }

  public static class StepFractalSimplify extends OptimalSimplifyingReductions.DynamicProgrammingStep {
    private AbstractReduceExpression largerReduceExpr;

    public StepFractalSimplify(final AbstractReduceExpression targetRE, final AbstractReduceExpression largerReduceExpr) {
      super(targetRE);
      this.largerReduceExpr = largerReduceExpr;
    }

    @Override
    public String description() {
      String _xblockexpression = null;
      {
        Equation _containerEquation = AlphaUtil.getContainerEquation(this.largerReduceExpr);
        final String largerEqName = ((StandardEquation) _containerEquation).getVariable().getName();
        _xblockexpression = String.format("Apply FractalSimplify%s with %s", this.toEqStr(), largerEqName);
      }
      return _xblockexpression;
    }
  }

  /**
   * Exception used to indicate that the exploration should stop. The current implementation
   * does not implement any pruning of the search space. If/when pruning is introduced, this
   * should probably be removed.
   */
  public static class ThrottleException extends Exception {
  }

  /**
   * This class is used to maintain the correspondence between the current state of the program
   * and the history of dynamic programming steps taken to get there. This is the main object
   * that is passed around during the recursive simplification.
   */
  public static class State {
    private SystemBody body;

    private List<OptimalSimplifyingReductions.DynamicProgrammingStep> steps;

    public State(final SystemBody body, final List<OptimalSimplifyingReductions.DynamicProgrammingStep> steps) {
      this.body = body;
      this.steps = steps;
    }

    public AlphaRoot root() {
      return AlphaUtil.getContainerRoot(this.body);
    }

    public int complexity() {
      return ComplexityCalculator.complexity(this.body);
    }

    public String showSteps() {
      int _size = this.steps.size();
      final Function1<Integer, String> _function = (Integer i) -> {
        String _xblockexpression = null;
        {
          final Function1<Integer, String> _function_1 = (Integer it) -> {
            return "+--";
          };
          String _join = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, (i).intValue(), true), _function_1));
          String _plus = ("// " + _join);
          final String indent = (_plus + "+-- ");
          String _description = this.steps.get((i).intValue()).description();
          _xblockexpression = (indent + _description);
        }
        return _xblockexpression;
      };
      return IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size, true), _function), "\n");
    }

    public CharSequence show() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("// Complexity: ");
      int _complexity = this.complexity();
      _builder.append(_complexity);
      _builder.append("D");
      _builder.newLineIfNotEmpty();
      String _showSteps = this.showSteps();
      _builder.append(_showSteps);
      _builder.newLineIfNotEmpty();
      String _print = Show.<AlphaSystem>print(this.body.getSystem());
      _builder.append(_print);
      _builder.newLineIfNotEmpty();
      return _builder;
    }
  }

  private void debug(final String msg) {
    if (this.verbose) {
      InputOutput.<String>println(("[OptimalSimplifyingReductions] " + msg));
    }
  }

  protected AlphaRoot root;

  protected AlphaSystem system;

  protected SystemBody systemBody;

  protected int systemBodyID;

  protected String originalSystemName;

  protected int initialComplexity;

  protected boolean throttle;

  protected int throttleLimit;

  protected long optimizationNum;

  protected int targetComplexity;

  protected boolean trySplitting;

  protected boolean verbose;

  /**
   * This maps contains the simplified versions of the program obtained
   * during exploration. Simplified versions are grouped by complexity.
   * The program versions of complexity O(N^k) reside in the list mapped to
   * by the key k.
   */
  public Map<Integer, List<OptimalSimplifyingReductions.State>> optimizations;

  /**
   * Data structure to keep track of 2-faces used to detect fractal simplification
   */
  private Map<AbstractReduceExpression, Face> explored2Faces;

  /**
   * Creates an OSR instance and initializes exploration space parameters
   */
  protected OptimalSimplifyingReductions(final AlphaSystem system, final int limit, final int complexity, final boolean trySplitting, final boolean verbose) {
    int _size = system.getSystemBodies().size();
    boolean _greaterThan = (_size > 1);
    if (_greaterThan) {
      throw new IllegalArgumentException("AlphaSystems with multiple system bodies is not yet supported.");
    }
    this.root = EcoreUtil.<AlphaRoot>copy(AlphaUtil.getContainerRoot(system));
    this.system = this.root.getSystem(system.getFullyQualifiedName());
    this.systemBodyID = 0;
    this.systemBody = this.system.getSystemBodies().get(this.systemBodyID);
    this.optimizations = CollectionLiterals.<Integer, List<OptimalSimplifyingReductions.State>>newHashMap();
    this.originalSystemName = this.system.getName();
    this.optimizationNum = 0;
    this.initialComplexity = ComplexityCalculator.complexity(this.systemBody);
    this.throttle = (limit > 0);
    this.throttleLimit = limit;
    this.targetComplexity = complexity;
    this.trySplitting = trySplitting;
    this.verbose = verbose;
    this.explored2Faces = CollectionLiterals.<AbstractReduceExpression, Face>newHashMap();
  }

  /**
   * Entry points to the optimal simplification algorithm.
   * If no limit is specified, then it will explore all possible simplifications.
   */
  public static OptimalSimplifyingReductions apply(final AlphaSystem system, final int limit, final int targetComplexity, final boolean trySplitting, final boolean verbose) {
    final OptimalSimplifyingReductions osr = new OptimalSimplifyingReductions(system, limit, targetComplexity, trySplitting, verbose);
    osr.run();
    return osr;
  }

  /**
   * Preprocess the input program. After preprocessing all reductions are
   * normalized and the body of every reduction is a single convex polyhedron
   * (i.e., an ISLSet with a single ISLBasicSet). This is achieved via
   * SplitUnionIntoCase, PermutationCaseReduce, and NormalizeReduction.
   * 
   * The State object is a wrapper around the AlphaRoot object and used to
   * maintain a correspondence between the sequence of dynamic programming steps
   * performed and transformed program.
   */
  protected OptimalSimplifyingReductions.State preprocessing() {
    ReductionComposition.apply(this.systemBody);
    SplitUnionIntoCase.apply(this.systemBody);
    PermutationCaseReduce.apply(this.systemBody);
    NormalizeReduction.apply(this.systemBody);
    Normalize.apply(this.systemBody);
    this.debug("After preprocessing:");
    this.debug(Show.<SystemBody>print(this.systemBody));
    LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep> _newLinkedList = CollectionLiterals.<OptimalSimplifyingReductions.DynamicProgrammingStep>newLinkedList();
    final OptimalSimplifyingReductions.State state = new OptimalSimplifyingReductions.State(this.systemBody, _newLinkedList);
    int _complexity = state.complexity();
    final Consumer<Integer> _function = (Integer i) -> {
      this.optimizations.put(i, CollectionLiterals.<OptimalSimplifyingReductions.State>newLinkedList());
    };
    new ExclusiveRange(0, _complexity, true).forEach(_function);
    return state;
  }

  /**
   * Entry point to the algorithm. The current implementation maintains a list
   * of unexplored equations for each program state. Given an initial preprocessed
   * program state, we begin by optimizing equations one at a time.
   */
  private void run() {
    final OptimalSimplifyingReductions.State state = this.preprocessing();
    try {
      this.optimizeUnexploredEquations(state);
    } catch (final Throwable _t) {
      if (_t instanceof OptimalSimplifyingReductions.ThrottleException) {
        this.debug((("Throttled search to stop after " + Integer.valueOf(this.throttleLimit)) + " results"));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  /**
   * optimizeUnexploredEquations and optimizeEquation together carry out the recursive simplification
   * algorithm. The given program state is optimized while it has unexplored equations. Each equation
   * has a boolean flag property to indicate whether it has been explored. Any new equations that
   * are introduced during the optimization are effectively queued because they have not yet been
   * marked as explored.
   * 
   * After the program state has been optimized (i.e., all equations have been explored), if the
   * state complexity is smaller than the initialComplexity then the state is added to the list of
   * optimized versions.
   */
  private void optimizeUnexploredEquations(final OptimalSimplifyingReductions.State state) {
    try {
      while (this.hasUnexploredEquations(state.body)) {
        {
          final StandardEquation eq = this.nextUnexploredEquation(state.body);
          String _name = eq.getVariable().getName();
          String _plus = ("optimizing equation " + _name);
          this.debug(_plus);
          this.debug(state.show().toString());
          this.optimizeEquation(eq, eq.getExpr(), state);
        }
      }
      final int stateComplexity = state.complexity();
      if ((stateComplexity == this.targetComplexity)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("[alpha]: found simplification/v");
        _builder.append(this.optimizationNum);
        _builder.append("/");
        String _name = this.system.getName();
        _builder.append(_name);
        _builder.append(".alpha");
        InputOutput.<String>println(_builder.toString());
        this.optimizationNum++;
        this.addToOptimzations(state);
        if ((this.throttle && (this.optimizationNum >= this.throttleLimit))) {
          throw new OptimalSimplifyingReductions.ThrottleException();
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * optimizeEquation is implemented as a dispatch method, because only equations involving
   * reduce expressions need to be recursively explored.
   */
  private void _optimizeEquation(final StandardEquation eq, final ReduceExpression re, final OptimalSimplifyingReductions.State state) {
    final SystemBody containerSystemBody = AlphaUtil.getContainerSystemBody(eq);
    boolean _isOptimallySimplified = this.isOptimallySimplified(re);
    if (_isOptimallySimplified) {
      eq.setExplored();
      return;
    }
    final SystemBody body = AlphaUtil.getContainerSystemBody(eq);
    while ((!this.sideEffectFreeTransformations(body, eq.getVariable().getName()))) {
    }
    final StandardEquation targetEq = body.getStandardEquation(eq.getVariable().getName());
    boolean _isNotReduceExpr = OptimalSimplifyingReductions.isNotReduceExpr(targetEq.getExpr());
    if (_isNotReduceExpr) {
      eq.setExplored();
      return;
    }
    AlphaExpression _expr = targetEq.getExpr();
    final List<? extends OptimalSimplifyingReductions.DynamicProgrammingStep> candidates = this.enumerateCandidates(((ReduceExpression) _expr));
    final Consumer<OptimalSimplifyingReductions.DynamicProgrammingStep> _function = (OptimalSimplifyingReductions.DynamicProgrammingStep c) -> {
      String _description = c.description();
      String _plus = ("candidate: " + _description);
      this.debug(_plus);
    };
    candidates.forEach(_function);
    for (final OptimalSimplifyingReductions.DynamicProgrammingStep step : candidates) {
      {
        final AlphaRoot optimizedRoot = EcoreUtil.<AlphaRoot>copy(AlphaUtil.getContainerRoot(containerSystemBody));
        final SystemBody optimizedBody = optimizedRoot.getSystem(this.originalSystemName).getSystemBodies().get(this.systemBodyID);
        final StandardEquation optimizedEq = this.getEquation(optimizedRoot, targetEq.getName());
        this.applyDPStep(optimizedEq.getExpr(), step);
        optimizedEq.setExplored(Boolean.valueOf(OptimalSimplifyingReductions.isNotReduceExpr(optimizedEq.getExpr())));
        final LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep> steps = CollectionLiterals.<OptimalSimplifyingReductions.DynamicProgrammingStep>newLinkedList();
        steps.addAll(state.steps);
        steps.add(step);
        final OptimalSimplifyingReductions.State newState = new OptimalSimplifyingReductions.State(optimizedBody, steps);
        this.optimizeUnexploredEquations(newState);
      }
    }
    eq.setExplored();
  }

  private void _optimizeEquation(final StandardEquation eq, final AlphaExpression ae, final OptimalSimplifyingReductions.State state) {
    eq.setExplored(Boolean.valueOf(true));
  }

  /**
   * Side effect free transformations are repeatedly applied until convergence.
   */
  protected boolean sideEffectFreeTransformations(final SystemBody body, final String eqName) {
    final StandardEquation eq = body.getStandardEquation(eqName);
    SplitUnionIntoCase.apply(body);
    PermutationCaseReduce.apply(body);
    final int nrCount = NormalizeReduction.apply(body);
    Normalize.apply(body);
    if ((nrCount > 0)) {
      return false;
    }
    AlphaExpression _expr = eq.getExpr();
    boolean _not = (!(_expr instanceof AbstractReduceExpression));
    if (_not) {
      return true;
    }
    AlphaExpression _expr_1 = eq.getExpr();
    int _apply = SameOperatorSimplification.apply(((ReduceExpression) _expr_1));
    boolean _greaterThan = (_apply > 0);
    if (_greaterThan) {
      return false;
    }
    AlphaExpression _expr_2 = eq.getExpr();
    int _apply_1 = Distributivity.apply(((ReduceExpression) _expr_2));
    boolean _greaterThan_1 = (_apply_1 > 0);
    if (_greaterThan_1) {
      return false;
    }
    return true;
  }

  /**
   * Return true if the dimensionality of the reduction body is bigger than the dimensionality
   * of the variable on the LHS of the containing equation, and false otherwise
   */
  private boolean shouldSimplify(final AbstractReduceExpression are) {
    Equation _containerEquation = AlphaUtil.getContainerEquation(are);
    final int answerDim = ISLUtil.dimensionality(((StandardEquation) _containerEquation).getVariable().getDomain());
    final int bodyDim = ISLUtil.dimensionality(are.getBody().getContextDomain());
    return (bodyDim > answerDim);
  }

  /**
   * Return true if shouldSimplify returns true and the reduction operator does not admit an inverse
   */
  private boolean shouldSplit(final AbstractReduceExpression are, final boolean shouldSimplify) {
    return ((this.trySplitting && shouldSimplify) && AlphaOperatorUtil.hasNoInverse(are.getOperator()));
  }

  /**
   * Tests if this reduce expression needs to be fractal simplified. If all of the
   * following hold for the reduction body D:
   *   1) the reduction operator does not admit an inverse
   *   2) D is 2-dimensional
   *   3) D is geometrically similar to a previously encountered reduction (re') body
   * 
   * Then the similar reduction re' is returned, else null is returned.
   */
  private AbstractReduceExpression fractalSimplification(final AbstractReduceExpression are) {
    Equation _containerEquation = AlphaUtil.getContainerEquation(are);
    final String varName = ((StandardEquation) _containerEquation).getVariable().getName();
    this.debug((("Testing if equation " + varName) + " needs fractal simplification"));
    final Face facet = are.getFacet();
    boolean _hasInverse = AlphaOperatorUtil.hasInverse(are.getOperator());
    if (_hasInverse) {
      return null;
    }
    boolean _equals = Objects.equal(varName, "Y_NR4");
    if (_equals) {
      InputOutput.println();
    }
    final Function1<Map.Entry<AbstractReduceExpression, Face>, Boolean> _function = (Map.Entry<AbstractReduceExpression, Face> es) -> {
      return Boolean.valueOf(ReductionUtil.isSimilar(are, es.getKey()));
    };
    final Map.Entry<AbstractReduceExpression, Face> similarExplored2Face = IterableExtensions.<Map.Entry<AbstractReduceExpression, Face>>findFirst(this.explored2Faces.entrySet(), _function);
    if ((similarExplored2Face != null)) {
      final AbstractReduceExpression largerReduceExpr = similarExplored2Face.getKey();
      Equation _containerEquation_1 = AlphaUtil.getContainerEquation(similarExplored2Face.getKey());
      final String similarVarName = ((StandardEquation) _containerEquation_1).getVariable().getName();
      this.debug(((varName + " is similar to previously encountered reduction ") + similarVarName));
      return largerReduceExpr;
    }
    this.explored2Faces.put(are, facet);
    this.debug((varName + " is NOT similar to any previously encountered reductions"));
    return null;
  }

  /**
   * Creates a list of possible transformations that are valid steps in the DP
   */
  protected List<? extends OptimalSimplifyingReductions.DynamicProgrammingStep> enumerateCandidates(final AbstractReduceExpression targetRE) {
    final int nbParams = targetRE.getExpressionDomain().getNbParams();
    final ShareSpaceAnalysisResult SSAR = ShareSpaceAnalysis.apply(targetRE);
    final LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep> candidates = new LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep>();
    final boolean shouldSimplify = this.shouldSimplify(targetRE);
    if (shouldSimplify) {
      final CandidateReuse candidateReuse = new CandidateReuse(targetRE, SSAR);
      boolean _isHasIdenticalAnswers = candidateReuse.isHasIdenticalAnswers();
      if (_isHasIdenticalAnswers) {
        final ISLMultiAff maff = candidateReuse.identicalAnswerBasis();
        final ISLSet domain = candidateReuse.getIdenticalAnswerDomain();
        OptimalSimplifyingReductions.StepRemoveIndenticalAnswers _stepRemoveIndenticalAnswers = new OptimalSimplifyingReductions.StepRemoveIndenticalAnswers(targetRE, maff, domain);
        return Collections.<OptimalSimplifyingReductions.StepRemoveIndenticalAnswers>unmodifiableList(CollectionLiterals.<OptimalSimplifyingReductions.StepRemoveIndenticalAnswers>newArrayList(_stepRemoveIndenticalAnswers));
      } else {
        final Function1<long[], OptimalSimplifyingReductions.StepSimplifyingReduction> _function = (long[] vec) -> {
          return new OptimalSimplifyingReductions.StepSimplifyingReduction(targetRE, vec, nbParams);
        };
        candidates.addAll(ListExtensions.<long[], OptimalSimplifyingReductions.StepSimplifyingReduction>map(candidateReuse.getVectors(), _function));
      }
    }
    final AbstractReduceExpression largerRE = this.fractalSimplification(targetRE);
    if ((largerRE != null)) {
      OptimalSimplifyingReductions.StepFractalSimplify _stepFractalSimplify = new OptimalSimplifyingReductions.StepFractalSimplify(targetRE, largerRE);
      return Collections.<OptimalSimplifyingReductions.StepFractalSimplify>unmodifiableList(CollectionLiterals.<OptimalSimplifyingReductions.StepFractalSimplify>newArrayList(_stepFractalSimplify));
    }
    if ((this.shouldSplit(targetRE, shouldSimplify) && candidates.isEmpty())) {
      boolean _requiresFractalSplits = SplitReduction.requiresFractalSplits(targetRE);
      if (_requiresFractalSplits) {
        OptimalSimplifyingReductions.StepSplitReduction _stepSplitReduction = new OptimalSimplifyingReductions.StepSplitReduction(targetRE);
        candidates.add(_stepSplitReduction);
      } else {
        final ISLConstraint[] splits = SplitReduction.enumerateCandidateSplits(targetRE);
        final Function1<ISLConstraint, OptimalSimplifyingReductions.StepSplitReduction> _function_1 = (ISLConstraint split) -> {
          return new OptimalSimplifyingReductions.StepSplitReduction(targetRE, split);
        };
        candidates.addAll(ListExtensions.<ISLConstraint, OptimalSimplifyingReductions.StepSplitReduction>map(((List<ISLConstraint>)Conversions.doWrapArray(splits)), _function_1));
      }
    }
    boolean _testLegality = Idempotence.testLegality(targetRE, SSAR);
    if (_testLegality) {
      OptimalSimplifyingReductions.StepIdempotence _stepIdempotence = new OptimalSimplifyingReductions.StepIdempotence(targetRE);
      candidates.add(_stepIdempotence);
    }
    boolean _testLegality_1 = HigherOrderOperator.testLegality(targetRE, SSAR);
    if (_testLegality_1) {
      OptimalSimplifyingReductions.StepHigherOrderOperator _stepHigherOrderOperator = new OptimalSimplifyingReductions.StepHigherOrderOperator(targetRE);
      candidates.add(_stepHigherOrderOperator);
    }
    final LinkedList<Pair<ISLMultiAff, ISLMultiAff>> decompositionCandidates = SimplifyingReductions.generateDecompositionCandidates(SSAR, targetRE);
    for (final Pair<ISLMultiAff, ISLMultiAff> pair : decompositionCandidates) {
      ISLMultiAff _key = pair.getKey();
      ISLMultiAff _value = pair.getValue();
      OptimalSimplifyingReductions.StepReductionDecomposition _stepReductionDecomposition = new OptimalSimplifyingReductions.StepReductionDecomposition(targetRE, _key, _value);
      candidates.add(_stepReductionDecomposition);
    }
    return candidates;
  }

  protected Integer _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepSimplifyingReduction step) {
    SimplifyingReductions.apply(re, step.reuseDepNoParams);
    return null;
  }

  protected Integer _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepIdempotence step) {
    Idempotence.apply(re);
    return null;
  }

  protected Integer _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepHigherOrderOperator step) {
    HigherOrderOperator.apply(re);
    return null;
  }

  protected Integer _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepReductionDecomposition step) {
    ReductionDecomposition.apply(re, step.innerProjection.copy(), step.outerProjection.copy());
    NormalizeReduction.apply(AlphaUtil.getContainerEquation(re));
    Normalize.apply(this.systemBody);
    return null;
  }

  protected Integer _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepSplitReduction step) {
    int _xblockexpression = (int) 0;
    {
      final Equation equation = AlphaUtil.getContainerEquation(re);
      SplitReduction.apply(re, step.split);
      _xblockexpression = NormalizeReduction.apply(equation);
    }
    return Integer.valueOf(_xblockexpression);
  }

  protected Integer _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepRemoveIndenticalAnswers step) {
    RemoveIdenticalAnswers.transform(re, step.identicalAnswerBasis, step.identicalAnswerDomain);
    return null;
  }

  protected Integer _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepFractalSimplify step) {
    FractalSimplify.transform(re, step.largerReduceExpr);
    return null;
  }

  protected Integer _applyDPStep(final AlphaExpression ae, final OptimalSimplifyingReductions.DynamicProgrammingStep step) {
    return null;
  }

  private StandardEquation getEquation(final AlphaRoot root, final String name) {
    try {
      StandardEquation _xblockexpression = null;
      {
        final Function1<Equation, Boolean> _function = (Equation eq) -> {
          return Boolean.valueOf((eq instanceof StandardEquation));
        };
        final Function1<Equation, StandardEquation> _function_1 = (Equation eq) -> {
          return ((StandardEquation) eq);
        };
        final Function1<StandardEquation, Boolean> _function_2 = (StandardEquation eq) -> {
          String _name = eq.getName();
          return Boolean.valueOf(Objects.equal(_name, name));
        };
        final Iterable<StandardEquation> eqs = IterableExtensions.<StandardEquation>filter(IterableExtensions.<Equation, StandardEquation>map(IterableExtensions.<Equation>filter(root.getSystem(this.originalSystemName).getSystemBodies().get(this.systemBodyID).getEquations(), _function), _function_1), _function_2);
        int _size = IterableExtensions.size(eqs);
        boolean _notEquals = (_size != 1);
        if (_notEquals) {
          throw new Exception("failed to get expression in copied root, this should not happen");
        }
        _xblockexpression = ((StandardEquation[])Conversions.unwrapArray(eqs, StandardEquation.class))[0];
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Returns true if the dimensionality reduce expression's body's context domain is the same
   * as the dimensionality of the variable on the LHS of the container equation.
   * The parent of the expression is guaranteed to be a Standard Equation since NormalizeReduction
   * has been systematically called.
   */
  protected boolean isOptimallySimplified(final ReduceExpression re) {
    try {
      boolean _xblockexpression = false;
      {
        EObject _eContainer = re.eContainer();
        boolean _not = (!(_eContainer instanceof StandardEquation));
        if (_not) {
          String _print = Show.<Equation>print(AlphaUtil.getContainerEquation(re));
          String _plus = ("Reduction has not been normalized: " + _print);
          throw new Exception(_plus);
        }
        Equation _containerEquation = AlphaUtil.getContainerEquation(re);
        final StandardEquation eq = ((StandardEquation) _containerEquation);
        final int lhsDim = ISLUtil.dimensionality(eq.getVariable().getDomain());
        final int rhsDim = ISLUtil.dimensionality(re.getBody().getContextDomain());
        _xblockexpression = (lhsDim >= rhsDim);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Returns true if expr is not a ReduceExpression
   */
  private static boolean isNotReduceExpr(final AlphaExpression expr) {
    return (!(expr instanceof ReduceExpression));
  }

  /**
   * Add the state to the appropriate list of optimizations
   */
  private boolean addToOptimzations(final OptimalSimplifyingReductions.State state) {
    boolean _xblockexpression = false;
    {
      final List<OptimalSimplifyingReductions.State> opts = this.optimizations.get(Integer.valueOf(state.complexity()));
      _xblockexpression = opts.add(state);
    }
    return _xblockexpression;
  }

  /**
   * Returns true if the system body has at least one unexplored equation
   */
  private boolean hasUnexploredEquations(final SystemBody body) {
    final Function1<StandardEquation, Boolean> _function = (StandardEquation it) -> {
      return it.getExplored();
    };
    int _size = IterableExtensions.size(IterableExtensions.<StandardEquation>reject(body.getStandardEquations(), _function));
    return (_size > 0);
  }

  /**
   * Returns the first equation in the system body marked as unexplored
   */
  private StandardEquation nextUnexploredEquation(final SystemBody body) {
    final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
      Boolean _explored = eq.getExplored();
      return Boolean.valueOf((!(_explored).booleanValue()));
    };
    return IterableExtensions.<StandardEquation>findFirst(body.getStandardEquations(), _function);
  }

  private void optimizeEquation(final StandardEquation eq, final AlphaExpression re, final OptimalSimplifyingReductions.State state) {
    if (re instanceof ReduceExpression) {
      _optimizeEquation(eq, (ReduceExpression)re, state);
      return;
    } else if (re != null) {
      _optimizeEquation(eq, re, state);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(eq, re, state).toString());
    }
  }

  protected Integer applyDPStep(final AlphaExpression re, final OptimalSimplifyingReductions.DynamicProgrammingStep step) {
    if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepFractalSimplify) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepFractalSimplify)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepHigherOrderOperator) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepHigherOrderOperator)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepIdempotence) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepIdempotence)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepReductionDecomposition) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepReductionDecomposition)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepRemoveIndenticalAnswers) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepRemoveIndenticalAnswers)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepSimplifyingReduction) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepSimplifyingReduction)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepSplitReduction) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepSplitReduction)step);
    } else if (re != null
         && step != null) {
      return _applyDPStep(re, step);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re, step).toString());
    }
  }
}
