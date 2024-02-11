package alpha.model.transformation.automation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaCompleteVisitable;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaNode;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.analysis.reduction.ShareSpaceAnalysis;
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult;
import alpha.model.issue.AlphaIssue;
import alpha.model.matrix.MatrixOperations;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.SplitUnionIntoCase;
import alpha.model.transformation.reduction.Distributivity;
import alpha.model.transformation.reduction.HigherOrderOperator;
import alpha.model.transformation.reduction.Idempotence;
import alpha.model.transformation.reduction.NormalizeReduction;
import alpha.model.transformation.reduction.PermutationCaseReduce;
import alpha.model.transformation.reduction.ReductionComposition;
import alpha.model.transformation.reduction.ReductionDecomposition;
import alpha.model.transformation.reduction.SameOperatorSimplification;
import alpha.model.transformation.reduction.SimplifyingReductions;
import alpha.model.util.AShow;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Implements Algorithm 2 in the Simplifying Reductions paper.
 * 
 * This implementation is incomplete in the following way:
 *  - The input program is assumed to be bounded. There are
 * a number of places in the original paper that discuss
 * a linear space (named L_P) = rays in vertex representation.
 * Cases where this linear space is meaningful are not supported.
 */
@SuppressWarnings("all")
public class SimplifyingReductionOptimalSimplificationAlgorithm {
  protected static class DynamicProgrammingContext {
    protected ProgramState state;

    protected Set<String> excludedEquations = new TreeSet<String>();

    protected Set<String> exploredEquations = new TreeSet<String>();

    protected SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext parent;

    protected LinkedList<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext> children;

    protected SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep step;

    public DynamicProgrammingContext(final ProgramState state) {
      this.state = state;
      LinkedList<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext> _linkedList = new LinkedList<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext>();
      this.children = _linkedList;
    }

    public DynamicProgrammingContext(final ProgramState state, final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep step) {
      this(state);
      this.step = step;
    }

    protected boolean markFinishedEquation(final String eqName) {
      boolean _xblockexpression = false;
      {
        this.excludedEquations.add(eqName);
        _xblockexpression = this.exploredEquations.add(eqName);
      }
      return _xblockexpression;
    }

    protected boolean markFinishedEquation(final StandardEquation eq) {
      boolean _xblockexpression = false;
      {
        this.excludedEquations.add(eq.getVariable().getName());
        _xblockexpression = this.exploredEquations.add(eq.getVariable().getName());
      }
      return _xblockexpression;
    }

    public SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext copy() {
      ProgramState _copy = this.state.copy();
      final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext copy = new SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext(_copy);
      for (final String ee : this.excludedEquations) {
        copy.excludedEquations.add(ee);
      }
      return copy;
    }

    public boolean isLeaf() {
      return this.children.isEmpty();
    }

    public List<ProgramState> leafStates() {
      boolean _isLeaf = this.isLeaf();
      if (_isLeaf) {
        return Collections.<ProgramState>unmodifiableList(CollectionLiterals.<ProgramState>newArrayList(this.state));
      }
      final ArrayList<ProgramState> states = new ArrayList<ProgramState>();
      final Consumer<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext> _function = (SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext c) -> {
        states.addAll(c.leafStates());
      };
      this.children.forEach(_function);
      return states;
    }

    /**
     * Gives the list of leafs and the steps it took to get to each leaf state
     */
    public List<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>> stepsToLeafStates() {
      ArrayList<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>> _xblockexpression = null;
      {
        boolean _isLeaf = this.isLeaf();
        if (_isLeaf) {
          Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>> _mappedTo = Pair.<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>of(this.state, Collections.<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>unmodifiableList(CollectionLiterals.<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>newArrayList(this.step)));
          return Collections.<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>>unmodifiableList(CollectionLiterals.<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>>newArrayList(_mappedTo));
        }
        final ArrayList<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>> ret = new ArrayList<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>>();
        for (final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext child : this.children) {
          {
            final Function1<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>, Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>> _function = (Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>> pair) -> {
              ProgramState _key = pair.getKey();
              List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep> _value = pair.getValue();
              List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep> _list = IterableExtensions.<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>toList(Iterables.<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>concat(Collections.<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>unmodifiableList(CollectionLiterals.<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>newArrayList(this.step)), _value));
              return Pair.<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>of(_key, _list);
            };
            final List<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>> steps = ListExtensions.<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>, Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>>map(child.stepsToLeafStates(), _function);
            ret.addAll(steps);
          }
        }
        _xblockexpression = ret;
      }
      return _xblockexpression;
    }

    public boolean addChild(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext child) {
      boolean _xblockexpression = false;
      {
        child.parent = this;
        _xblockexpression = this.children.add(child);
      }
      return _xblockexpression;
    }
  }

  public static abstract class DynamicProgrammingStep {
    protected final EList<Integer> nodeID;

    protected AbstractReduceExpression re;

    public DynamicProgrammingStep(final AbstractReduceExpression targetRE) {
      this.nodeID = targetRE.getNodeID();
      this.re = targetRE;
    }

    public abstract String description();
  }

  public static class StepBeginEquation extends SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep {
    public StepBeginEquation(final AbstractReduceExpression targetRE) {
      super(targetRE);
    }

    @Override
    public String description() {
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
          _xifexpression_1 = String.format("to %s", eqVarName);
        } else {
          _xifexpression_1 = "";
        }
        final String toEqStr = _xifexpression_1;
        _xblockexpression = String.format("Optimize equation %s", toEqStr);
      }
      return _xblockexpression;
    }
  }

  public static class StepSimplifyingReduction extends SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep {
    private long[] reuseDepNoParams;

    public StepSimplifyingReduction(final AbstractReduceExpression targetRE, final long[] reuseDepNoParams, final int nbParams) {
      super(targetRE);
      this.reuseDepNoParams = reuseDepNoParams;
    }

    @Override
    public String description() {
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
          _xifexpression_1 = String.format("to %s", eqVarName);
        } else {
          _xifexpression_1 = "";
        }
        final String toEqStr = _xifexpression_1;
        _xblockexpression = String.format("Apply SimplifyingReduction%s with: %s", toEqStr, MatrixOperations.toString(this.reuseDepNoParams));
      }
      return _xblockexpression;
    }
  }

  public static class StepIdempotence extends SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep {
    public StepIdempotence(final AbstractReduceExpression targetRE) {
      super(targetRE);
    }

    @Override
    public String description() {
      return String.format("Apply Idempotence");
    }
  }

  public static class StepHigherOrderOperator extends SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep {
    public StepHigherOrderOperator(final AbstractReduceExpression targetRE) {
      super(targetRE);
    }

    @Override
    public String description() {
      return String.format("Apply HigherOrderOperator");
    }
  }

  public static class StepReductionDecomposition extends SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep {
    private ISLMultiAff innerProjection;

    private ISLMultiAff outerProjection;

    private ISLMultiAff _inner;

    private ISLMultiAff _outer;

    public StepReductionDecomposition(final AbstractReduceExpression targetRE, final ISLMultiAff innerF, final ISLMultiAff outerF) {
      super(targetRE);
      this.innerProjection = innerF;
      this.outerProjection = outerF;
      this._inner = innerF.copy();
      this._outer = outerF.copy();
    }

    @Override
    public String description() {
      return String.format("Apply ReductionDecomposition with %s o %s", this._outer, this._inner);
    }
  }

  public static boolean DEBUG = false;

  public static boolean DO_DECOMPOSITION_WITH_SIDE_EFFECTS = false;

  private void debug(final String content) {
    if (SimplifyingReductionOptimalSimplificationAlgorithm.DEBUG) {
      System.out.println(("[SROptimalSimplification] " + content));
    }
  }

  private void debug(final String content, final ProgramState state) {
    this.debug(content);
    if (SimplifyingReductionOptimalSimplificationAlgorithm.DEBUG) {
      System.out.println(AShow.print(this.getBody(state)));
    }
  }

  protected final AlphaRoot originalProgram;

  protected final String systemName;

  protected final int systemBodyID;

  protected List<AlphaRoot> optimizedPrograms;

  protected Map<AlphaRoot, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>> pathsToOptimizedPrograms;

  public List<AlphaRoot> getOptimizedPrograms() {
    return this.optimizedPrograms;
  }

  public Map<AlphaRoot, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>> getPathsToOptimizedPrograms() {
    return this.pathsToOptimizedPrograms;
  }

  protected SimplifyingReductionOptimalSimplificationAlgorithm(final SystemBody body) {
    this.originalProgram = AlphaUtil.getContainerRoot(body);
    this.systemName = body.getSystem().getFullyQualifiedName();
    this.systemBodyID = body.getSystem().getSystemBodies().indexOf(body);
  }

  public static SimplifyingReductionOptimalSimplificationAlgorithm apply(final AlphaSystem system) {
    SimplifyingReductionOptimalSimplificationAlgorithm _xifexpression = null;
    int _size = system.getSystemBodies().size();
    boolean _equals = (_size == 1);
    if (_equals) {
      _xifexpression = SimplifyingReductionOptimalSimplificationAlgorithm.apply(system.getSystemBodies().get(0));
    } else {
      throw new IllegalArgumentException("A SystemBody must be specified for an AlphaSystem with multiple bodies.");
    }
    return _xifexpression;
  }

  public static SimplifyingReductionOptimalSimplificationAlgorithm apply(final SystemBody body) {
    final SimplifyingReductionOptimalSimplificationAlgorithm SROSA = new SimplifyingReductionOptimalSimplificationAlgorithm(body);
    SROSA.run();
    AlphaInternalStateConstructor.recomputeContextDomain(((AlphaCompleteVisitable[])Conversions.unwrapArray(SROSA.optimizedPrograms, AlphaCompleteVisitable.class)));
    return SROSA;
  }

  /**
   * Entry point to the algorithm
   */
  private void run() {
    final ProgramState state = this.preprocessing();
    this.debug("After Preprocessing", state);
    final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext DPcontext = new SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext(state);
    this.exploreDPcontext(DPcontext);
    this.debug("After DP", DPcontext.state);
    final List<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>> statesSteps = DPcontext.stepsToLeafStates();
    HashMap<AlphaRoot, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>> _hashMap = new HashMap<AlphaRoot, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>();
    this.pathsToOptimizedPrograms = _hashMap;
    final Consumer<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>> _function = (Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>> stateSteps) -> {
      final AlphaRoot root = stateSteps.getKey().root;
      final List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep> steps = stateSteps.getValue();
      this.pathsToOptimizedPrograms.put(root, steps);
    };
    statesSteps.forEach(_function);
    final Function1<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>, AlphaRoot> _function_1 = (Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>> stateSteps) -> {
      return stateSteps.getKey().root;
    };
    this.optimizedPrograms = ListExtensions.<Pair<ProgramState, List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>>, AlphaRoot>map(statesSteps, _function_1);
    InputOutput.println();
  }

  /**
   * Preprocessing involves exposing equations with reductions
   * being the immediate child expression, and making sure
   * that the expression domain of each reduction body is a
   * single polyhedron. These are the main assumptions made
   * in the SR algorithm.
   */
  protected ProgramState preprocessing() {
    ProgramState _xblockexpression = null;
    {
      final AlphaRoot copyProg = EcoreUtil.<AlphaRoot>copy(this.originalProgram);
      final AlphaSystem copySystem = copyProg.getSystem(this.systemName);
      final SystemBody copyBody = copySystem.getSystemBodies().get(this.systemBodyID);
      ReductionComposition.apply(copyBody);
      SplitUnionIntoCase.apply(copyBody);
      PermutationCaseReduce.apply(copyBody);
      NormalizeReduction.apply(copyBody);
      Normalize.apply(copyBody);
      _xblockexpression = new ProgramState(copyProg);
    }
    return _xblockexpression;
  }

  private String INDENT = "";

  /**
   * The algorithm optimizes each equation one by one. There are some
   * cases where the order and choice of reuse vectors influences schedulability,
   * but this is not considered in the current implementation.
   */
  private void exploreDPcontext(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext DPcontext) {
    final String old_INDENT = this.INDENT;
    this.INDENT = ("+-- " + this.INDENT);
    while (this.hasNext(DPcontext)) {
      {
        final StandardEquation eq = this.getNext(DPcontext);
        this.debug(String.format("Optimizing Equation: %s", eq.getVariable().getName()));
        String _xifexpression = null;
        if ((DPcontext.step == null)) {
          _xifexpression = "";
        } else {
          _xifexpression = DPcontext.step.description();
        }
        final String stepStr = _xifexpression;
        InputOutput.<String>println(String.format("%sequ %s (%s)", this.INDENT, eq.getVariable().getName(), stepStr));
        this.optimizeEquation(DPcontext, eq);
      }
    }
    this.INDENT = old_INDENT;
  }

  /**
   * Optimization of an equation. This mostly corresponds to Algorithm 2.
   * 
   * The most tricky part of the algorithm is dealing with the number of equations
   * that keeps changing. NormalizeReduction must be systematically applied in
   * the automated setting, which makes it difficult to optimize a single equation.
   * In this implementation, there is a data structure named DynamicProgrammingContext
   * that keeps track of the equations that should be explored. This is used to only optimize
   * an equation and new equations that are introduced during the process of optimizing this equation.
   */
  private void optimizeEquation(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context, final StandardEquation eq) {
    AlphaExpression _expr = eq.getExpr();
    boolean _not = (!(_expr instanceof ReduceExpression));
    if (_not) {
      this.debug(String.format("Not an Equation with ReduceExpression: %s", eq.getVariable().getName()));
      context.markFinishedEquation(eq);
      return;
    }
    final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext childContext = context.copy();
    childContext.parent = context;
    final Function1<StandardEquation, Boolean> _function = (StandardEquation e) -> {
      return Boolean.valueOf((!Objects.equal(e, eq)));
    };
    final Function1<StandardEquation, String> _function_1 = (StandardEquation e) -> {
      return e.getVariable().getName();
    };
    Iterables.<String>addAll(childContext.excludedEquations, IterableExtensions.<StandardEquation, String>map(IterableExtensions.<StandardEquation>filter(this.getBody(context.state).getStandardEquations(), _function), _function_1));
    while ((!this.sideEffectFreeTransformations(this.getBody(childContext.state), eq.getVariable().getName()))) {
    }
    this.debug("After Side-Effect Free Transformations", childContext.state);
    final StandardEquation targetEq = this.getBody(childContext.state).getStandardEquation(eq.getVariable().getName());
    AlphaExpression _expr_1 = targetEq.getExpr();
    boolean _not_1 = (!(_expr_1 instanceof ReduceExpression));
    if (_not_1) {
      this.debug(String.format("Finished Exploring Equation: %s", eq.getVariable().getName()));
      childContext.markFinishedEquation(eq);
      this.exploreDPcontext(childContext);
      if ((childContext.state.nbSR > context.state.nbSR)) {
        context.state = childContext.state;
        this.excludeExploredEquationsInChildContext(context, childContext);
      }
      context.markFinishedEquation(eq);
      return;
    }
    AlphaExpression _expr_2 = targetEq.getExpr();
    final LinkedList<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep> candidates = this.enumerateCandidates(((ReduceExpression) _expr_2));
    this.debug(String.format("Number of DP step candidates: %d", Integer.valueOf(candidates.size())));
    boolean _isEmpty = candidates.isEmpty();
    if (_isEmpty) {
      this.debug(String.format("Finished Exploring Equation: %s", eq.getVariable().getName()));
      childContext.markFinishedEquation(eq);
      this.exploreDPcontext(childContext);
      if ((childContext.state.nbSR > context.state.nbSR)) {
        context.state = childContext.state;
        this.excludeExploredEquationsInChildContext(context, childContext);
      }
      context.markFinishedEquation(eq);
      return;
    }
    for (final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep step : candidates) {
      {
        this.debug(String.format("Applying Step: %s", step.description()));
        final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext child = childContext.copy();
        child.step = step;
        this.applyDPStep(child, step);
        this.exploreDPcontext(child);
        context.children.add(child);
      }
    }
    context.markFinishedEquation(eq);
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
   * Creates a list of possible transformations that are valid steps in the DP.
   */
  protected LinkedList<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep> enumerateCandidates(final AbstractReduceExpression targetRE) {
    final int nbParams = targetRE.getExpressionDomain().getNbParams();
    final ShareSpaceAnalysisResult SSAR = ShareSpaceAnalysis.apply(targetRE);
    final LinkedList<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep> candidates = new LinkedList<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>();
    final Integer answerDimensionality = ISLUtil.dimensionality(targetRE.getContextDomain());
    final Integer reBodyDimensionality = ISLUtil.dimensionality(targetRE.getBody().getContextDomain());
    boolean _lessThan = (answerDimensionality.compareTo(reBodyDimensionality) < 0);
    if (_lessThan) {
      final LinkedList<long[]> vectors = SimplifyingReductions.generateCandidateReuseVectors(targetRE, SSAR);
      for (final long[] vec : vectors) {
        SimplifyingReductionOptimalSimplificationAlgorithm.StepSimplifyingReduction _stepSimplifyingReduction = new SimplifyingReductionOptimalSimplificationAlgorithm.StepSimplifyingReduction(targetRE, vec, nbParams);
        candidates.add(_stepSimplifyingReduction);
      }
    }
    boolean _testLegality = Idempotence.testLegality(targetRE, SSAR);
    if (_testLegality) {
      SimplifyingReductionOptimalSimplificationAlgorithm.StepIdempotence _stepIdempotence = new SimplifyingReductionOptimalSimplificationAlgorithm.StepIdempotence(targetRE);
      candidates.add(_stepIdempotence);
    }
    boolean _testLegality_1 = HigherOrderOperator.testLegality(targetRE, SSAR);
    if (_testLegality_1) {
      SimplifyingReductionOptimalSimplificationAlgorithm.StepHigherOrderOperator _stepHigherOrderOperator = new SimplifyingReductionOptimalSimplificationAlgorithm.StepHigherOrderOperator(targetRE);
      candidates.add(_stepHigherOrderOperator);
    }
    if (SimplifyingReductionOptimalSimplificationAlgorithm.DO_DECOMPOSITION_WITH_SIDE_EFFECTS) {
      LinkedList<Pair<ISLMultiAff, ISLMultiAff>> _generateDecompositionCandidates = SimplifyingReductions.generateDecompositionCandidates(SSAR, targetRE);
      for (final Pair<ISLMultiAff, ISLMultiAff> pair : _generateDecompositionCandidates) {
        ISLMultiAff _key = pair.getKey();
        ISLMultiAff _value = pair.getValue();
        SimplifyingReductionOptimalSimplificationAlgorithm.StepReductionDecomposition _stepReductionDecomposition = new SimplifyingReductionOptimalSimplificationAlgorithm.StepReductionDecomposition(targetRE, _key, _value);
        candidates.add(_stepReductionDecomposition);
      }
    }
    return candidates;
  }

  private boolean hasNext(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context) {
    final Function1<StandardEquation, Boolean> _function = (StandardEquation e) -> {
      boolean _contains = context.excludedEquations.contains(e.getVariable().getName());
      return Boolean.valueOf((!_contains));
    };
    return IterableExtensions.<StandardEquation>exists(this.getBody(context.state).getStandardEquations(), _function);
  }

  private StandardEquation getNext(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context) {
    final Function1<StandardEquation, Boolean> _function = (StandardEquation e) -> {
      boolean _contains = context.excludedEquations.contains(e.getVariable().getName());
      return Boolean.valueOf((!_contains));
    };
    return IterableExtensions.<StandardEquation>findFirst(this.getBody(context.state).getStandardEquations(), _function);
  }

  private void excludeExploredEquationsInChildContext(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context, final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext childContext) {
    final Function1<String, Boolean> _function = (String e) -> {
      StandardEquation _standardEquation = this.getBody(context.state).getStandardEquation(e);
      return Boolean.valueOf((_standardEquation != null));
    };
    final Consumer<String> _function_1 = (String e) -> {
      context.markFinishedEquation(e);
    };
    IterableExtensions.<String>filter(childContext.exploredEquations, _function).forEach(_function_1);
  }

  protected Object _applyDPStep(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context, final SimplifyingReductionOptimalSimplificationAlgorithm.StepSimplifyingReduction step) {
    int _xblockexpression = (int) 0;
    {
      AlphaNode _node = context.state.root.getNode(step.nodeID);
      final ReduceExpression re = ((ReduceExpression) _node);
      SimplifyingReductions.apply(re, step.reuseDepNoParams);
      int _nbSR = context.state.nbSR;
      _xblockexpression = context.state.nbSR = (_nbSR + 1);
    }
    return Integer.valueOf(_xblockexpression);
  }

  protected Object _applyDPStep(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context, final SimplifyingReductionOptimalSimplificationAlgorithm.StepIdempotence step) {
    int _xblockexpression = (int) 0;
    {
      AlphaNode _node = context.state.root.getNode(step.nodeID);
      final ReduceExpression re = ((ReduceExpression) _node);
      Idempotence.apply(re);
      int _nbSR = context.state.nbSR;
      _xblockexpression = context.state.nbSR = (_nbSR + 1);
    }
    return Integer.valueOf(_xblockexpression);
  }

  protected Object _applyDPStep(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context, final SimplifyingReductionOptimalSimplificationAlgorithm.StepHigherOrderOperator step) {
    int _xblockexpression = (int) 0;
    {
      AlphaNode _node = context.state.root.getNode(step.nodeID);
      final ReduceExpression re = ((ReduceExpression) _node);
      HigherOrderOperator.apply(re);
      int _nbSR = context.state.nbSR;
      _xblockexpression = context.state.nbSR = (_nbSR + 1);
    }
    return Integer.valueOf(_xblockexpression);
  }

  protected Object _applyDPStep(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context, final SimplifyingReductionOptimalSimplificationAlgorithm.StepReductionDecomposition step) {
    List<AlphaIssue> _xblockexpression = null;
    {
      AlphaNode _node = context.state.root.getNode(step.nodeID);
      final ReduceExpression re = ((ReduceExpression) _node);
      _xblockexpression = ReductionDecomposition.apply(re, step.innerProjection, step.outerProjection);
    }
    return _xblockexpression;
  }

  private AlphaSystem getSystem(final ProgramState state) {
    return state.root.getSystem(this.systemName);
  }

  private SystemBody getBody(final ProgramState state) {
    return this.getSystem(state).getSystemBodies().get(this.systemBodyID);
  }

  protected Object applyDPStep(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context, final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep step) {
    if (step instanceof SimplifyingReductionOptimalSimplificationAlgorithm.StepHigherOrderOperator) {
      return _applyDPStep(context, (SimplifyingReductionOptimalSimplificationAlgorithm.StepHigherOrderOperator)step);
    } else if (step instanceof SimplifyingReductionOptimalSimplificationAlgorithm.StepIdempotence) {
      return _applyDPStep(context, (SimplifyingReductionOptimalSimplificationAlgorithm.StepIdempotence)step);
    } else if (step instanceof SimplifyingReductionOptimalSimplificationAlgorithm.StepReductionDecomposition) {
      return _applyDPStep(context, (SimplifyingReductionOptimalSimplificationAlgorithm.StepReductionDecomposition)step);
    } else if (step instanceof SimplifyingReductionOptimalSimplificationAlgorithm.StepSimplifyingReduction) {
      return _applyDPStep(context, (SimplifyingReductionOptimalSimplificationAlgorithm.StepSimplifyingReduction)step);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(context, step).toString());
    }
  }
}
