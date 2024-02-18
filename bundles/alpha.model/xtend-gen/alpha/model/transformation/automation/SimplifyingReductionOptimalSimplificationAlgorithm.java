package alpha.model.transformation.automation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
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
import alpha.model.util.Show;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
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

    protected SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep step;

    protected boolean result;

    public DynamicProgrammingContext(final ProgramState state) {
      this.state = state;
      this.result = true;
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
          _xifexpression_1 = String.format(" to %s", eqVarName);
        } else {
          _xifexpression_1 = "";
        }
        final String toEqStr = _xifexpression_1;
        _xblockexpression = String.format("Apply SimplifyingReduction%s with: %s", toEqStr, MatrixOperations.toString(this.reuseDepNoParams));
      }
      return _xblockexpression;
    }

    public long[] getReuseDepNoParams() {
      return this.reuseDepNoParams;
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

  public static boolean THROTTLE = true;

  public static int THROTTLE_LIMIT = 1;

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
    return SROSA;
  }

  /**
   * Entry point to the algorithm
   */
  private String run() {
    String _xblockexpression = null;
    {
      final ProgramState state = this.preprocessing();
      this.debug("After Preprocessing", state);
      final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext DPcontext = new SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext(state);
      final List<ProgramState> optimizedStates = this.exploreDPcontext(DPcontext);
      final ProgramState optimizedState = optimizedStates.get(0);
      InputOutput.<String>println("Complexity per equation:");
      final Function1<Equation, Boolean> _function = (Equation eq) -> {
        return Boolean.valueOf((eq instanceof StandardEquation));
      };
      final Function1<Equation, StandardEquation> _function_1 = (Equation eq) -> {
        return ((StandardEquation) eq);
      };
      final Consumer<StandardEquation> _function_2 = (StandardEquation eq) -> {
        AlphaExpression _expr = eq.getExpr();
        if ((_expr instanceof ReduceExpression)) {
          AlphaExpression _expr_1 = eq.getExpr();
          int _dimensionality = ISLUtil.dimensionality(((ReduceExpression) _expr_1).getBody().getContextDomain());
          String _plus = (Integer.valueOf(_dimensionality) + "D -> ");
          String _name = eq.getVariable().getName();
          String _plus_1 = (_plus + _name);
          InputOutput.<String>println(_plus_1);
        } else {
          int _dimensionality_1 = ISLUtil.dimensionality(eq.getVariable().getDomain());
          String _plus_2 = (Integer.valueOf(_dimensionality_1) + "D -> ");
          String _name_1 = eq.getVariable().getName();
          String _plus_3 = (_plus_2 + _name_1);
          InputOutput.<String>println(_plus_3);
        }
      };
      IterableExtensions.<Equation, StandardEquation>map(IterableExtensions.<Equation>filter(this.getBody(optimizedState).getEquations(), _function), _function_1).forEach(_function_2);
      InputOutput.println();
      int _complexity = SimplifyingReductionOptimalSimplificationAlgorithm.complexity(this.getBody(optimizedState));
      String _plus = ("Optimized complexity: " + Integer.valueOf(_complexity));
      String _plus_1 = (_plus + "D");
      InputOutput.<String>println(_plus_1);
      _xblockexpression = InputOutput.<String>println(Show.<AlphaSystem>print(this.getSystem(optimizedState)));
    }
    return _xblockexpression;
  }

  public static int complexity(final SystemBody body) {
    final Function1<Equation, Boolean> _function = (Equation eq) -> {
      return Boolean.valueOf((eq instanceof StandardEquation));
    };
    final Function1<Equation, StandardEquation> _function_1 = (Equation eq) -> {
      return ((StandardEquation) eq);
    };
    final Function1<StandardEquation, Integer> _function_2 = (StandardEquation eq) -> {
      int _xifexpression = (int) 0;
      AlphaExpression _expr = eq.getExpr();
      if ((_expr instanceof ReduceExpression)) {
        AlphaExpression _expr_1 = eq.getExpr();
        _xifexpression = ISLUtil.dimensionality(((ReduceExpression) _expr_1).getBody().getContextDomain());
      } else {
        _xifexpression = ISLUtil.dimensionality(eq.getVariable().getDomain());
      }
      return Integer.valueOf(_xifexpression);
    };
    return (int) IterableExtensions.<Integer>max(IterableExtensions.<StandardEquation, Integer>map(IterableExtensions.<Equation, StandardEquation>map(IterableExtensions.<Equation>filter(body.getEquations(), _function), _function_1), _function_2));
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

  /**
   * The algorithm optimizes each equation one by one. There are some
   * cases where the order and choice of reuse vectors influences schedulability,
   * but this is not considered in the current implementation.
   */
  private List<ProgramState> exploreDPcontext(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext DPcontext) {
    final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context = DPcontext.copy();
    while (this.hasNext(context)) {
      {
        final StandardEquation eq = this.getNext(context);
        String _name = eq.getVariable().getName();
        boolean _equals = Objects.equal(_name, "Y_neg_NR1");
        if (_equals) {
          InputOutput.println();
        }
        final List<ProgramState> optimizations = this.optimizeEquation(context, eq);
        final Function1<StandardEquation, String> _function = (StandardEquation it) -> {
          return it.getVariable().getName();
        };
        final String remaining = ListExtensions.<StandardEquation, String>map(this.getAll(context), _function).toString();
        final String explored = IterableExtensions.<String>toList(context.exploredEquations).toString();
        String _name_1 = eq.getVariable().getName();
        String _plus = ("optimized equation: " + _name_1);
        InputOutput.<String>println(_plus);
        InputOutput.<String>println(("          explored: " + explored));
        InputOutput.<String>println(("         remaining: " + remaining));
        boolean _isEmpty = optimizations.isEmpty();
        if (_isEmpty) {
          InputOutput.<String>println("--> no further optimizations");
          return optimizations;
        }
        int _size = optimizations.size();
        String _plus_1 = ("--> # of optimizations: " + Integer.valueOf(_size));
        InputOutput.<String>println(_plus_1);
        InputOutput.<String>print("");
        final Consumer<ProgramState> _function_1 = (ProgramState os) -> {
          final Function1<Equation, Boolean> _function_2 = (Equation e) -> {
            return Boolean.valueOf((e instanceof StandardEquation));
          };
          final Function1<Equation, StandardEquation> _function_3 = (Equation e) -> {
            return ((StandardEquation) e);
          };
          final Consumer<StandardEquation> _function_4 = (StandardEquation e) -> {
            String _xifexpression = null;
            String _name_2 = e.getVariable().getName();
            String _name_3 = eq.getVariable().getName();
            boolean _equals_1 = Objects.equal(_name_2, _name_3);
            if (_equals_1) {
              _xifexpression = "  <-- this step";
            } else {
              _xifexpression = "";
            }
            String flag = _xifexpression;
            int _dimensionality = this.dimensionality(e);
            String _plus_2 = ("    (" + Integer.valueOf(_dimensionality));
            String _plus_3 = (_plus_2 + "D) ");
            String _name_4 = e.getVariable().getName();
            String _plus_4 = (_plus_3 + _name_4);
            String _plus_5 = (_plus_4 + flag);
            InputOutput.<String>println(_plus_5);
          };
          IterableExtensions.<Equation, StandardEquation>map(IterableExtensions.<Equation>filter(this.getBody(os).getEquations(), _function_2), _function_3).forEach(_function_4);
          InputOutput.println();
        };
        optimizations.forEach(_function_1);
        InputOutput.println();
        InputOutput.println();
        context.state = optimizations.get(0);
      }
    }
    return Collections.<ProgramState>unmodifiableList(CollectionLiterals.<ProgramState>newArrayList(context.state));
  }

  private int dimensionality(final StandardEquation equ) {
    return this.dimensionality(equ, equ.getExpr());
  }

  private int _dimensionality(final StandardEquation equ, final ReduceExpression re) {
    return ISLUtil.dimensionality(re.getBody().getContextDomain());
  }

  private int _dimensionality(final StandardEquation equ, final AlphaExpression ae) {
    return equ.getVariable().getDomain().getNbIndices();
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
   * 
   * Returns true if equation was completely optimized (all dimensions are reuse have been
   * exploited), or false otherwise
   */
  private List<ProgramState> optimizeEquation(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context, final StandardEquation eq) {
    AlphaExpression _expr = eq.getExpr();
    boolean _not = (!(_expr instanceof ReduceExpression));
    if (_not) {
      this.debug(String.format("Not an Equation with ReduceExpression: %s", eq.getVariable().getName()));
      context.markFinishedEquation(eq);
      return Collections.<ProgramState>unmodifiableList(CollectionLiterals.<ProgramState>newArrayList(context.state));
    }
    AlphaExpression _expr_1 = eq.getExpr();
    boolean _isOptimallySimplified = this.isOptimallySimplified(((ReduceExpression) _expr_1));
    if (_isOptimallySimplified) {
      this.debug(String.format("Finished Exploring Equation: %s", eq.getVariable().getName()));
      context.markFinishedEquation(eq);
      return Collections.<ProgramState>unmodifiableList(CollectionLiterals.<ProgramState>newArrayList(context.state));
    }
    final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext childContext = context.copy();
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
    AlphaExpression _expr_2 = targetEq.getExpr();
    boolean _not_1 = (!(_expr_2 instanceof ReduceExpression));
    if (_not_1) {
      this.debug(String.format("Finished Exploring Equation: %s", eq.getVariable().getName()));
      childContext.markFinishedEquation(eq);
      this.exploreDPcontext(childContext);
      context.markFinishedEquation(eq);
      return Collections.<ProgramState>unmodifiableList(CollectionLiterals.<ProgramState>newArrayList(childContext.state));
    }
    AlphaExpression _expr_3 = targetEq.getExpr();
    final ReduceExpression targetRE = ((ReduceExpression) _expr_3);
    String _name = eq.getVariable().getName();
    boolean _equals = Objects.equal(_name, "Y_neg_NR1");
    if (_equals) {
      InputOutput.println();
    }
    final List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep> candidates = this.enumerateCandidates(targetRE);
    this.debug(String.format("Number of DP step candidates: %d", Integer.valueOf(candidates.size())));
    final LinkedList<ProgramState> allOptimizations = CollectionLiterals.<ProgramState>newLinkedList();
    for (final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep step : candidates) {
      {
        this.debug(String.format("Applying Step: %s", step.description()));
        final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext child = childContext.copy();
        child.step = step;
        this.applyDPStep(child, step);
        final List<ProgramState> optimizations = this.exploreDPcontext(child);
        allOptimizations.addAll(optimizations);
      }
    }
    context.markFinishedEquation(eq);
    boolean _isEmpty = allOptimizations.isEmpty();
    if (_isEmpty) {
      InputOutput.println();
    }
    return allOptimizations;
  }

  /**
   * Returns true if the dimensionality reduce expression's body's context domain is the same
   * as the dimensionality of the LHS of the container equation.
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
          String _print = Show.<ReduceExpression>print(re);
          String _plus = ("Reduction has not been normalized: " + _print);
          throw new Exception(_plus);
        }
        Equation _containerEquation = AlphaUtil.getContainerEquation(re);
        final StandardEquation eq = ((StandardEquation) _containerEquation);
        final int lhsDim = eq.getVariable().getDomain().getNbIndices();
        final int rhsDim = ISLUtil.dimensionality(re.getBody().getContextDomain());
        _xblockexpression = (lhsDim >= rhsDim);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
  protected List<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep> enumerateCandidates(final AbstractReduceExpression targetRE) {
    final int nbParams = targetRE.getExpressionDomain().getNbParams();
    final ShareSpaceAnalysisResult SSAR = ShareSpaceAnalysis.apply(targetRE);
    final LinkedList<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep> candidates = new LinkedList<SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingStep>();
    Equation _containerEquation = AlphaUtil.getContainerEquation(targetRE);
    final int answerDim = ISLUtil.dimensionality(((StandardEquation) _containerEquation).getVariable().getDomain());
    final int bodyDim = ISLUtil.dimensionality(targetRE.getBody().getContextDomain());
    if ((bodyDim > answerDim)) {
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
    LinkedList<Pair<ISLMultiAff, ISLMultiAff>> _generateDecompositionCandidates = SimplifyingReductions.generateDecompositionCandidates(SSAR, targetRE);
    for (final Pair<ISLMultiAff, ISLMultiAff> pair : _generateDecompositionCandidates) {
      ISLMultiAff _key = pair.getKey();
      ISLMultiAff _value = pair.getValue();
      SimplifyingReductionOptimalSimplificationAlgorithm.StepReductionDecomposition _stepReductionDecomposition = new SimplifyingReductionOptimalSimplificationAlgorithm.StepReductionDecomposition(targetRE, _key, _value);
      candidates.add(_stepReductionDecomposition);
    }
    if (SimplifyingReductionOptimalSimplificationAlgorithm.THROTTLE) {
      final int nbCandidates = candidates.size();
      int _xifexpression = (int) 0;
      if ((SimplifyingReductionOptimalSimplificationAlgorithm.THROTTLE_LIMIT < nbCandidates)) {
        _xifexpression = SimplifyingReductionOptimalSimplificationAlgorithm.THROTTLE_LIMIT;
      } else {
        _xifexpression = nbCandidates;
      }
      return candidates.subList(0, _xifexpression);
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

  private List<StandardEquation> getAll(final SimplifyingReductionOptimalSimplificationAlgorithm.DynamicProgrammingContext context) {
    final Function1<StandardEquation, Boolean> _function = (StandardEquation e) -> {
      boolean _contains = context.excludedEquations.contains(e.getVariable().getName());
      return Boolean.valueOf((!_contains));
    };
    return IterableExtensions.<StandardEquation>toList(IterableExtensions.<StandardEquation>filter(this.getBody(context.state).getStandardEquations(), _function));
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

  private int dimensionality(final StandardEquation equ, final AlphaExpression re) {
    if (re instanceof ReduceExpression) {
      return _dimensionality(equ, (ReduceExpression)re);
    } else if (re != null) {
      return _dimensionality(equ, re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(equ, re).toString());
    }
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
