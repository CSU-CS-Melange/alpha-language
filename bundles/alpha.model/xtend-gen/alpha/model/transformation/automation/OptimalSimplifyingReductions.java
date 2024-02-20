package alpha.model.transformation.automation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
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
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import alpha.model.util.Show;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
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
 * 
 * This implementation is incomplete in the following way:
 *  - The input program is assumed to be bounded. There are
 * a number of places in the original paper that discuss
 * a linear space (named L_P) = rays in vertex representation.
 * Cases where this linear space is meaningful are not supported.
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

    public String show() {
      String _xblockexpression = null;
      {
        int _complexity = OptimalSimplifyingReductions.complexity(this.body);
        String _plus = ("Complexity: " + Integer.valueOf(_complexity));
        String _plus_1 = (_plus + "D");
        InputOutput.<String>println(_plus_1);
        int _size = this.steps.size();
        final Consumer<Integer> _function = (Integer i) -> {
          final Function1<Integer, String> _function_1 = (Integer it) -> {
            return "+--";
          };
          String _join = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, (i).intValue(), true), _function_1));
          final String indent = (_join + "+-- ");
          String _description = this.steps.get((i).intValue()).description();
          String _plus_2 = (indent + _description);
          InputOutput.<String>println(_plus_2);
        };
        new ExclusiveRange(0, _size, true).forEach(_function);
        _xblockexpression = InputOutput.<String>println(Show.<AlphaSystem>print(this.body.getSystem()));
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
      return String.format("Apply ReductionDecomposition with %s o %s", this._outer, this._inner);
    }
  }

  public static boolean DEBUG = false;

  public static boolean THROTTLE = true;

  public static int THROTTLE_LIMIT = 2;

  protected AlphaRoot root;

  protected AlphaSystem system;

  protected SystemBody systemBody;

  protected int systemBodyID;

  protected String originalSystemName;

  protected long optimizationNum;

  protected final List<OptimalSimplifyingReductions.State> optimizations;

  protected OptimalSimplifyingReductions(final SystemBody originalSystemBody) {
    this.root = EcoreUtil.<AlphaRoot>copy(AlphaUtil.getContainerRoot(originalSystemBody));
    this.system = this.root.getSystem(originalSystemBody.getSystem().getFullyQualifiedName());
    this.systemBodyID = originalSystemBody.getSystem().getSystemBodies().indexOf(originalSystemBody);
    this.systemBody = this.system.getSystemBodies().get(this.systemBodyID);
    this.optimizations = CollectionLiterals.<OptimalSimplifyingReductions.State>newLinkedList();
    this.originalSystemName = this.system.getName();
    this.optimizationNum = 0;
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

  /**
   * Entry point to the algorithm
   */
  private void run() {
    this.preprocessing();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, 3, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        InputOutput.<String>println(("--> pass " + i));
        int _size = this.optimizations.size();
        String _plus = ("    opts " + Integer.valueOf(_size));
        InputOutput.<String>println(_plus);
        this.transform();
      }
    }
    int _size = this.optimizations.size();
    String _plus = ("Number of optimizations: " + Integer.valueOf(_size));
    InputOutput.<String>println(_plus);
    final Consumer<OptimalSimplifyingReductions.State> _function = (OptimalSimplifyingReductions.State it) -> {
      it.show();
    };
    this.optimizations.forEach(_function);
    InputOutput.println();
  }

  private void transform() {
    final LinkedList<OptimalSimplifyingReductions.State> systemsToProcess = CollectionLiterals.<OptimalSimplifyingReductions.State>newLinkedList();
    systemsToProcess.addAll(this.optimizations);
    while ((this.optimizations.size() > 0)) {
      this.optimizations.remove(0);
    }
    final Consumer<OptimalSimplifyingReductions.State> _function = (OptimalSimplifyingReductions.State state) -> {
      final Function1<StandardEquation, Boolean> _function_1 = (StandardEquation it) -> {
        return it.getExplored();
      };
      final Consumer<StandardEquation> _function_2 = (StandardEquation eq) -> {
        this.optimizeEquation(eq, state);
      };
      IterableExtensions.<StandardEquation>reject(EcoreUtil2.<StandardEquation>getAllContentsOfType(state.body, StandardEquation.class), _function_1).forEach(_function_2);
    };
    systemsToProcess.forEach(_function);
  }

  protected boolean preprocessing() {
    boolean _xblockexpression = false;
    {
      ReductionComposition.apply(this.systemBody);
      SplitUnionIntoCase.apply(this.systemBody);
      PermutationCaseReduce.apply(this.systemBody);
      NormalizeReduction.apply(this.systemBody);
      Normalize.apply(this.systemBody);
      LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep> _newLinkedList = CollectionLiterals.<OptimalSimplifyingReductions.DynamicProgrammingStep>newLinkedList();
      final OptimalSimplifyingReductions.State state = new OptimalSimplifyingReductions.State(this.systemBody, _newLinkedList);
      _xblockexpression = this.optimizations.add(state);
    }
    return _xblockexpression;
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
      OptimalSimplifyingReductions.State _state = new OptimalSimplifyingReductions.State(containerSystemBody, state.steps);
      this.optimizations.add(_state);
      return;
    }
    final SystemBody body = AlphaUtil.getContainerSystemBody(eq);
    while ((!this.sideEffectFreeTransformations(body, eq.getVariable().getName()))) {
    }
    final StandardEquation targetEq = body.getStandardEquation(eq.getVariable().getName());
    boolean _isNotReduceExpr = OptimalSimplifyingReductions.isNotReduceExpr(targetEq.getExpr());
    if (_isNotReduceExpr) {
      eq.setExplored();
      OptimalSimplifyingReductions.State _state_1 = new OptimalSimplifyingReductions.State(containerSystemBody, state.steps);
      this.optimizations.add(_state_1);
      return;
    }
    AlphaExpression _expr = targetEq.getExpr();
    final List<OptimalSimplifyingReductions.DynamicProgrammingStep> candidates = this.enumerateCandidates(((ReduceExpression) _expr));
    for (final OptimalSimplifyingReductions.DynamicProgrammingStep step : candidates) {
      {
        String _description = step.description();
        String _plus = ("    step: " + _description);
        InputOutput.<String>println(_plus);
        final AlphaRoot optimizedRoot = EcoreUtil.<AlphaRoot>copy(AlphaUtil.getContainerRoot(containerSystemBody));
        final SystemBody optimizedBody = optimizedRoot.getSystem(this.originalSystemName).getSystemBodies().get(this.systemBodyID);
        final StandardEquation optimzedEq = this.getEquation(optimizedRoot, targetEq.getName());
        this.applyDPStep(optimzedEq.getExpr(), step);
        final LinkedList<OptimalSimplifyingReductions.DynamicProgrammingStep> steps = CollectionLiterals.<OptimalSimplifyingReductions.DynamicProgrammingStep>newLinkedList();
        steps.addAll(state.steps);
        steps.add(step);
        OptimalSimplifyingReductions.State _state_2 = new OptimalSimplifyingReductions.State(optimizedBody, steps);
        this.optimizations.add(_state_2);
      }
    }
  }

  private void _optimizeEquation(final StandardEquation eq, final AlphaExpression ae, final OptimalSimplifyingReductions.State state) {
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
  protected List<OptimalSimplifyingReductions.DynamicProgrammingStep> enumerateCandidates(final AbstractReduceExpression targetRE) {
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
    LinkedList<Pair<ISLMultiAff, ISLMultiAff>> _generateDecompositionCandidates = SimplifyingReductions.generateDecompositionCandidates(SSAR, targetRE);
    for (final Pair<ISLMultiAff, ISLMultiAff> pair : _generateDecompositionCandidates) {
      ISLMultiAff _key = pair.getKey();
      ISLMultiAff _value = pair.getValue();
      OptimalSimplifyingReductions.StepReductionDecomposition _stepReductionDecomposition = new OptimalSimplifyingReductions.StepReductionDecomposition(targetRE, _key, _value);
      candidates.add(_stepReductionDecomposition);
    }
    if (OptimalSimplifyingReductions.THROTTLE) {
      final int nbCandidates = candidates.size();
      int _xifexpression = (int) 0;
      if ((OptimalSimplifyingReductions.THROTTLE_LIMIT < nbCandidates)) {
        _xifexpression = OptimalSimplifyingReductions.THROTTLE_LIMIT;
      } else {
        _xifexpression = nbCandidates;
      }
      return candidates.subList(0, _xifexpression);
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
    return ReductionDecomposition.apply(re, step.innerProjection, step.outerProjection);
  }

  protected List<AlphaIssue> _applyDPStep(final AlphaExpression ae, final OptimalSimplifyingReductions.DynamicProgrammingStep step) {
    return null;
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

  protected List<AlphaIssue> applyDPStep(final AlphaExpression re, final OptimalSimplifyingReductions.DynamicProgrammingStep step) {
    if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepHigherOrderOperator) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepHigherOrderOperator)step);
    } else if (re instanceof ReduceExpression
         && step instanceof OptimalSimplifyingReductions.StepIdempotence) {
      return _applyDPStep((ReduceExpression)re, (OptimalSimplifyingReductions.StepIdempotence)step);
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
