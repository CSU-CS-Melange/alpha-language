package alpha.model.transformation.automation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.VariableExpression;
import alpha.model.analysis.reduction.ShareSpaceAnalysis;
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult;
import alpha.model.issue.AlphaIssue;
import alpha.model.matrix.MatrixOperations;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.RaiseDependenceAndIsolate;
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
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import alpha.model.util.Show;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Implements Algorithm 2 in the Simplifying Reductions paper.
 */
@SuppressWarnings("all")
public class OptimalSimplifyingReductions {
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
      return OptimalSimplifyingReductions.complexity(this.body);
    }

    public CharSequence show() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("// Complexity: ");
      int _complexity = this.complexity();
      _builder.append(_complexity);
      _builder.append("D");
      _builder.newLineIfNotEmpty();
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
      String _join = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size, true), _function), "\n");
      _builder.append(_join);
      _builder.newLineIfNotEmpty();
      String _print = Show.<AlphaSystem>print(this.body.getSystem());
      _builder.append(_print);
      _builder.newLineIfNotEmpty();
      return _builder;
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

  public static class StepBeginEquation extends OptimalSimplifyingReductions.DynamicProgrammingStep {
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

  public static class StepSimplifyingReduction extends OptimalSimplifyingReductions.DynamicProgrammingStep {
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
        _xblockexpression = String.format("Apply ReductionDecomposition%s with %s o %s", toEqStr, this._outer, this._inner);
      }
      return _xblockexpression;
    }
  }

  public static class StepRaiseDependenceAndIsolate extends OptimalSimplifyingReductions.DynamicProgrammingStep {
    public StepRaiseDependenceAndIsolate(final AbstractReduceExpression targetRE) {
      super(targetRE);
    }

    @Override
    public String description() {
      return "Apply RaiseDependenceAndIsolate";
    }
  }

  public static class ThrottleException extends Exception {
  }

  public static boolean DEBUG = true;

  private static boolean THROTTLE = true;

  private static int THROTTLE_LIMIT = 10;

  private static long optimizationNum;

  private static String saveDirectory = "resources/opt";

  protected AlphaRoot root;

  protected AlphaSystem system;

  protected SystemBody systemBody;

  protected int systemBodyID;

  protected String originalSystemName;

  private static void debug(final String msg) {
    if (OptimalSimplifyingReductions.DEBUG) {
      InputOutput.<String>println(("[OSR] " + msg));
    }
  }

  protected final Map<Integer, List<OptimalSimplifyingReductions.State>> optimizations;

  protected OptimalSimplifyingReductions(final SystemBody originalSystemBody) {
    this.root = EcoreUtil.<AlphaRoot>copy(AlphaUtil.getContainerRoot(originalSystemBody));
    this.system = this.root.getSystem(originalSystemBody.getSystem().getFullyQualifiedName());
    this.systemBodyID = originalSystemBody.getSystem().getSystemBodies().indexOf(originalSystemBody);
    this.systemBody = this.system.getSystemBodies().get(this.systemBodyID);
    this.optimizations = CollectionLiterals.<Integer, List<OptimalSimplifyingReductions.State>>newHashMap();
    this.originalSystemName = this.system.getName();
    OptimalSimplifyingReductions.optimizationNum = 0;
  }

  public static OptimalSimplifyingReductions apply(final AlphaSystem system) {
    OptimalSimplifyingReductions _xifexpression = null;
    int _size = system.getSystemBodies().size();
    boolean _equals = (_size == 1);
    if (_equals) {
      _xifexpression = OptimalSimplifyingReductions.apply(system.getSystemBodies().get(0));
    } else {
      throw new IllegalArgumentException("A SystemBody must be specified for an AlphaSystem with multiple bodies.");
    }
    return _xifexpression;
  }

  public static OptimalSimplifyingReductions apply(final SystemBody body) {
    final OptimalSimplifyingReductions osr = new OptimalSimplifyingReductions(body);
    osr.run();
    return osr;
  }

  protected OptimalSimplifyingReductions.State preprocessing() {
    ReductionComposition.apply(this.systemBody);
    SplitUnionIntoCase.apply(this.systemBody);
    PermutationCaseReduce.apply(this.systemBody);
    NormalizeReduction.apply(this.systemBody);
    Normalize.apply(this.systemBody);
    InputOutput.<String>println("After preprocessing:");
    InputOutput.<String>println(Show.<SystemBody>print(this.systemBody));
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
   * Entry point to the algorithm
   */
  private void run() {
    final OptimalSimplifyingReductions.State state = this.preprocessing();
    try {
      this.optimizeUnexploredEquations(state);
    } catch (final Throwable _t) {
      if (_t instanceof OptimalSimplifyingReductions.ThrottleException) {
        InputOutput.<String>println((("Throttled search to stop after " + Integer.valueOf(OptimalSimplifyingReductions.THROTTLE_LIMIT)) + " results"));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    InputOutput.println();
    final Function1<Integer, Boolean> _function = (Integer k) -> {
      int _size = this.optimizations.get(k).size();
      return Boolean.valueOf((_size == 0));
    };
    final Function1<Integer, Pair<Integer, List<OptimalSimplifyingReductions.State>>> _function_1 = (Integer k) -> {
      List<OptimalSimplifyingReductions.State> _get = this.optimizations.get(k);
      return Pair.<Integer, List<OptimalSimplifyingReductions.State>>of(k, _get);
    };
    final List<Pair<Integer, List<OptimalSimplifyingReductions.State>>> foundOptimizations = IterableExtensions.<Pair<Integer, List<OptimalSimplifyingReductions.State>>>toList(IterableExtensions.<Integer, Pair<Integer, List<OptimalSimplifyingReductions.State>>>map(IterableExtensions.<Integer>reject(this.optimizations.keySet(), _function), _function_1));
    final Consumer<Pair<Integer, List<OptimalSimplifyingReductions.State>>> _function_2 = (Pair<Integer, List<OptimalSimplifyingReductions.State>> keyOpts) -> {
      final List<OptimalSimplifyingReductions.State> opts = keyOpts.getValue();
      final Consumer<OptimalSimplifyingReductions.State> _function_3 = (OptimalSimplifyingReductions.State it) -> {
        InputOutput.<CharSequence>println(it.show());
      };
      opts.forEach(_function_3);
    };
    foundOptimizations.forEach(_function_2);
    InputOutput.println();
    final Consumer<Pair<Integer, List<OptimalSimplifyingReductions.State>>> _function_3 = (Pair<Integer, List<OptimalSimplifyingReductions.State>> keyOpts) -> {
      final Integer key = keyOpts.getKey();
      final List<OptimalSimplifyingReductions.State> opts = keyOpts.getValue();
      int _size = opts.size();
      String _plus = ((("Number of " + key) + "D optimizations: ") + Integer.valueOf(_size));
      InputOutput.<String>println(_plus);
    };
    foundOptimizations.forEach(_function_3);
  }

  private boolean addToOptimzations(final OptimalSimplifyingReductions.State state) {
    boolean _xblockexpression = false;
    {
      final List<OptimalSimplifyingReductions.State> opts = this.optimizations.get(Integer.valueOf(state.complexity()));
      _xblockexpression = opts.add(state);
    }
    return _xblockexpression;
  }

  private boolean hasUnexploredEquations(final SystemBody body) {
    final Function1<StandardEquation, Boolean> _function = (StandardEquation it) -> {
      return it.getExplored();
    };
    int _size = IterableExtensions.size(IterableExtensions.<StandardEquation>reject(body.getStandardEquations(), _function));
    return (_size > 0);
  }

  private StandardEquation nextUnexploredEquation(final SystemBody body) {
    final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
      Boolean _explored = eq.getExplored();
      return Boolean.valueOf((!(_explored).booleanValue()));
    };
    return IterableExtensions.<StandardEquation>findFirst(body.getStandardEquations(), _function);
  }

  private void optimizeUnexploredEquations(final OptimalSimplifyingReductions.State state) {
    try {
      while (this.hasUnexploredEquations(state.body)) {
        {
          final StandardEquation eq = this.nextUnexploredEquation(state.body);
          String _name = eq.getVariable().getName();
          String _plus = ("optimizing equation " + _name);
          OptimalSimplifyingReductions.debug(_plus);
          OptimalSimplifyingReductions.debug(state.show().toString());
          this.optimizeEquation(eq, state);
        }
      }
      final int stateComplexity = state.complexity();
      if ((stateComplexity <= 3)) {
        OptimalSimplifyingReductions.optimizationNum++;
        InputOutput.<String>print(("\rnumber of 3D optimizations found: " + Long.valueOf(OptimalSimplifyingReductions.optimizationNum)));
        this.addToOptimzations(state);
        if ((OptimalSimplifyingReductions.saveDirectory != null)) {
          final String fileName = String.format("%s/%s.v%03d.alpha", OptimalSimplifyingReductions.saveDirectory, state.body.getSystem().getName(), Long.valueOf(OptimalSimplifyingReductions.optimizationNum));
          final String stateStr = state.show().toString();
          OptimalSimplifyingReductions.writeToFile(stateStr, fileName);
        }
        InputOutput.<CharSequence>println(state.show());
        if ((OptimalSimplifyingReductions.THROTTLE && (OptimalSimplifyingReductions.optimizationNum >= OptimalSimplifyingReductions.THROTTLE_LIMIT))) {
          throw new OptimalSimplifyingReductions.ThrottleException();
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  private void optimizeEquation(final StandardEquation eq, final OptimalSimplifyingReductions.State state) {
    this.optimizeEquation(eq, eq.getExpr(), state);
    return;
  }

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
    final LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep> candidates = this.enumerateCandidates(((ReduceExpression) _expr));
    final Consumer<OptimalSimplifyingReductions.DynamicProgrammingStep> _function = (OptimalSimplifyingReductions.DynamicProgrammingStep c) -> {
      String _description = c.description();
      String _plus = ("candidate: " + _description);
      OptimalSimplifyingReductions.debug(_plus);
    };
    candidates.forEach(_function);
    InputOutput.println();
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

  private static boolean isNotReduceExpr(final AlphaExpression expr) {
    return (!(expr instanceof ReduceExpression));
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
          String _print = Show.<Equation>print(AlphaUtil.getContainerEquation(re));
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

  private boolean shouldRaiseDependence(final AbstractReduceExpression targetRE) {
    return ((!(targetRE.getBody() instanceof DependenceExpression)) && 
      (!(targetRE.getBody() instanceof VariableExpression)));
  }

  /**
   * Creates a list of possible transformations that are valid steps in the DP.
   */
  protected LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep> enumerateCandidates(final AbstractReduceExpression targetRE) {
    final int nbParams = targetRE.getExpressionDomain().getNbParams();
    final ShareSpaceAnalysisResult SSAR = ShareSpaceAnalysis.apply(targetRE);
    final LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep> candidates = new LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep>();
    Equation _containerEquation = AlphaUtil.getContainerEquation(targetRE);
    final int answerDim = ISLUtil.dimensionality(((StandardEquation) _containerEquation).getVariable().getDomain());
    final int bodyDim = ISLUtil.dimensionality(targetRE.getBody().getContextDomain());
    if ((bodyDim > answerDim)) {
      final LinkedList<long[]> vectors = SimplifyingReductions.generateCandidateReuseVectors(targetRE, SSAR);
      for (final long[] vec : vectors) {
        OptimalSimplifyingReductions.StepSimplifyingReduction _stepSimplifyingReduction = new OptimalSimplifyingReductions.StepSimplifyingReduction(targetRE, vec, nbParams);
        candidates.add(_stepSimplifyingReduction);
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
    final ArrayList<Pair<ISLMultiAff, ISLMultiAff>> x = CommonExtensions.<Pair<ISLMultiAff, ISLMultiAff>>toArrayList(SimplifyingReductions.generateDecompositionCandidates(SSAR, targetRE));
    for (final Pair<ISLMultiAff, ISLMultiAff> pair : x) {
      ISLMultiAff _key = pair.getKey();
      ISLMultiAff _value = pair.getValue();
      OptimalSimplifyingReductions.StepReductionDecomposition _stepReductionDecomposition = new OptimalSimplifyingReductions.StepReductionDecomposition(targetRE, _key, _value);
      candidates.add(_stepReductionDecomposition);
    }
    boolean _shouldRaiseDependence = this.shouldRaiseDependence(targetRE);
    if (_shouldRaiseDependence) {
      OptimalSimplifyingReductions.StepRaiseDependenceAndIsolate _stepRaiseDependenceAndIsolate = new OptimalSimplifyingReductions.StepRaiseDependenceAndIsolate(targetRE);
      candidates.add(_stepRaiseDependenceAndIsolate);
    }
    return candidates;
  }

  protected List<AlphaIssue> _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepSimplifyingReduction step) {
    SimplifyingReductions.apply(re, step.reuseDepNoParams);
    return null;
  }

  protected List<AlphaIssue> _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepIdempotence step) {
    Idempotence.apply(re);
    return null;
  }

  protected List<AlphaIssue> _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepHigherOrderOperator step) {
    HigherOrderOperator.apply(re);
    return null;
  }

  protected List<AlphaIssue> _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepReductionDecomposition step) {
    ReductionDecomposition.apply(re, step.innerProjection, step.outerProjection);
    NormalizeReduction.apply(AlphaUtil.getContainerEquation(re));
    Normalize.apply(this.systemBody);
    return null;
  }

  protected List<AlphaIssue> _applyDPStep(final ReduceExpression re, final OptimalSimplifyingReductions.StepRaiseDependenceAndIsolate step) {
    List<AlphaIssue> _xblockexpression = null;
    {
      RaiseDependenceAndIsolate.apply(re);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(this.systemBody);
    }
    return _xblockexpression;
  }

  protected List<AlphaIssue> _applyDPStep(final AlphaExpression ae, final OptimalSimplifyingReductions.DynamicProgrammingStep step) {
    return null;
  }

  public static void writeToFile(final String blob, final String fileName) {
    try {
      FileWriter _fileWriter = new FileWriter(fileName);
      final BufferedWriter writer = new BufferedWriter(_fileWriter);
      writer.write(blob);
      writer.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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

  protected List<AlphaIssue> applyDPStep(final AlphaExpression re, final OptimalSimplifyingReductions.DynamicProgrammingStep step) {
    if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepHigherOrderOperator) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepHigherOrderOperator)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepIdempotence) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepIdempotence)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepRaiseDependenceAndIsolate) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepRaiseDependenceAndIsolate)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepReductionDecomposition) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepReductionDecomposition)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepSimplifyingReduction) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepSimplifyingReduction)step);
    } else if (re != null
         && step != null) {
      return _applyDPStep(re, step);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re, step).toString());
    }
  }
}
