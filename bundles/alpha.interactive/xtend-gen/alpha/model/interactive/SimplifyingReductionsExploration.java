package alpha.model.interactive;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.CaseExpression;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.analysis.reduction.ShareSpaceAnalysis;
import alpha.model.analysis.reduction.ShareSpaceAnalysisResult;
import alpha.model.matrix.MatrixOperations;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.SimplifyExpressions;
import alpha.model.transformation.SplitUnionIntoCase;
import alpha.model.transformation.SubstituteByDef;
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
import alpha.model.util.AlphaPrintingUtil;
import alpha.model.util.AlphaUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.barvinok.BarvinokFunctions;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomial;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Interactive exploration of Simplifying Reductions.
 * 
 * This is designed to be an interactive alternative to the optimality
 * algorithm in the paper based on dynamic programming. Instead of
 * using DP to enumerate the possible space, the user navigates through
 * the exploration space. The user may also rollback and explore a
 * different path when necessary.
 * 
 * It does not fully implement the original algorithm in the SR paper.
 * There are a few steps that are mostly for reductions with unbounded
 * domain, which have not been implemented.
 */
@SuppressWarnings("all")
public class SimplifyingReductionsExploration extends AbstractInteractiveExploration {
  private enum STATE {
    INITIAL,

    REDUCTION_SELECTED,

    SIDE_EFFECT_FREE_TRANSFORMATIONS,

    DP_STEP_TRANSFORMATIONS,

    EXIT;
  }

  private static abstract class ExplorationStep {
    public abstract String description();
  }

  private static class StepSelectReduction extends SimplifyingReductionsExploration.ExplorationStep {
    private AbstractReduceExpression are;

    public StepSelectReduction(final AbstractReduceExpression are) {
      this.are = are;
    }

    @Override
    public String description() {
      return String.format("%s (in %s)", AShow.print(this.are), SimplifyingReductionsExploration.getEquationName(AlphaUtil.getContainerEquation(this.are)));
    }
  }

  private static class StepSimplifyingReduction extends SimplifyingReductionsExploration.ExplorationStep {
    private long[] reuseDepNoParams;

    public StepSimplifyingReduction(final long[] reuseDepNoParams) {
      this.reuseDepNoParams = reuseDepNoParams;
    }

    @Override
    public String description() {
      return String.format("Apply SimplifyingReduction with: %s", MatrixOperations.toString(this.reuseDepNoParams));
    }
  }

  private static class StepIdempotence extends SimplifyingReductionsExploration.ExplorationStep {
    @Override
    public String description() {
      return String.format("Apply Idempotence");
    }
  }

  private static class StepHigherOrderOperator extends SimplifyingReductionsExploration.ExplorationStep {
    @Override
    public String description() {
      return String.format("Apply HigherOrderOperator");
    }
  }

  private static class StepReductionComposition extends SimplifyingReductionsExploration.ExplorationStep {
    @Override
    public String description() {
      return "Apply ReductionComposition";
    }
  }

  private static class StepReductionDecomposition extends SimplifyingReductionsExploration.ExplorationStep {
    private ISLMultiAff innerProjection;

    private ISLMultiAff outerProjection;

    public StepReductionDecomposition(final ISLMultiAff innerF, final ISLMultiAff outerF) {
      this.innerProjection = innerF;
      this.outerProjection = outerF;
    }

    @Override
    public String description() {
      return String.format("Apply ReductionDecomposition with %s o %s", this.outerProjection, this.innerProjection);
    }
  }

  private static class StepInlineVariable extends SimplifyingReductionsExploration.ExplorationStep {
    private Variable variable;

    public StepInlineVariable(final Variable v) {
      this.variable = v;
    }

    @Override
    public String description() {
      return String.format("Inline %s", this.variable.getName());
    }
  }

  private static class StepSimplifyExpressions extends SimplifyingReductionsExploration.ExplorationStep {
    @Override
    public String description() {
      return "Apply SimplifyExpression to current SystemBody";
    }
  }

  private static class StepSplitUnionIntoCase extends SimplifyingReductionsExploration.ExplorationStep {
    @Override
    public String description() {
      return "Apply SplitUnionIntoCase to current SystemBody";
    }
  }

  private static class StepPrintSystemBody extends SimplifyingReductionsExploration.ExplorationStep {
    @Override
    public String description() {
      return String.format("Print current SystemBody");
    }
  }

  private static class StepPrintCardinality extends SimplifyingReductionsExploration.ExplorationStep {
    @Override
    public String description() {
      return String.format("Print Cardinality of the reduction bodies");
    }
  }

  private static class StepPrintShareSpace extends SimplifyingReductionsExploration.ExplorationStep {
    private ShareSpaceAnalysisResult SSAR;

    public StepPrintShareSpace(final ShareSpaceAnalysisResult SSAR) {
      this.SSAR = SSAR;
    }

    @Override
    public String description() {
      return String.format("Print Share Space");
    }
  }

  private static class StepBacktrack extends SimplifyingReductionsExploration.ExplorationStep {
    @Override
    public String description() {
      return String.format("Revert to previous state.");
    }
  }

  private static class StepFinish extends SimplifyingReductionsExploration.ExplorationStep {
    @Override
    public String description() {
      return String.format("Finish exploration.");
    }
  }

  protected SimplifyingReductionsExploration.STATE state = SimplifyingReductionsExploration.STATE.INITIAL;

  protected AbstractReduceExpression targetRE;

  protected List<String> commandHistory;

  private SimplifyingReductionsExploration(final String filepath) {
    super(filepath);
  }

  public static final void start(final String filepath) {
    final SimplifyingReductionsExploration sre = new SimplifyingReductionsExploration(filepath);
    AbstractInteractiveExploration.SKIP_SINGLE_CHOICE_QUESTIONS = true;
    sre.run();
  }

  @Override
  public void mainLoop() {
    try {
      AbstractInteractiveExploration.SKIP_SINGLE_CHOICE_QUESTIONS = false;
      while ((!Objects.equal(this.state, SimplifyingReductionsExploration.STATE.EXIT))) {
        try {
          LinkedList<String> _linkedList = new LinkedList<String>();
          this.commandHistory = _linkedList;
          final SimplifyingReductionsExploration.STATE state = this.state;
          if (state != null) {
            switch (state) {
              case INITIAL:
                this.selectReduceExpression();
                break;
              case REDUCTION_SELECTED:
                this.expressionDomainCheck();
                break;
              case SIDE_EFFECT_FREE_TRANSFORMATIONS:
                this.sideEffectFreeTransformations();
                break;
              case DP_STEP_TRANSFORMATIONS:
                this.StepTransformations();
                break;
              case EXIT:
                break;
              default:
                break;
            }
          }
          boolean _isEmpty = this.commandHistory.isEmpty();
          boolean _not = (!_isEmpty);
          if (_not) {
            this.setProperty("state", this.state);
            this.setProperty("targetRE", this.targetRE);
            this.recordState(this.commandHistory);
          }
        } catch (final Throwable _t) {
          if (_t instanceof RuntimeException) {
            final RuntimeException re = (RuntimeException)_t;
            Class<? extends RuntimeException> _class = re.getClass();
            String _plus = ("Exception: " + _class);
            this.errStream.println(_plus);
            this.errStream.println(re.getMessage());
            re.printStackTrace();
            this.errStream.println("Restoring previous state.");
            this.restoreState();
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
      }
      this.outStream.println("Final program:");
      this.outStream.println(AShow.print(this.getCurrentSystem()));
      this.outStream.println("");
      this.outStream.print("Press enter/return to output transformation script");
      this.inStream.readLine();
      this.outStream.println();
      this.outStream.println("import groovy.transform.BaseScript");
      this.outStream.println("@BaseScript alpha.commands.groovy.AlphaScript alphaScript");
      this.outStream.println();
      List<String> _commandSequence = this.getCommandSequence();
      for (final String c : _commandSequence) {
        this.outStream.println(c);
      }
      this.outStream.println();
      this.outStream.println("CheckProgram(root)");
      this.outStream.println("AShow(system)");
      this.outStream.println();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Lists all reductions in the selected SystemBody to work with.
   */
  private Object selectReduceExpression() {
    Object _xblockexpression = null;
    {
      final SystemBody body = this.getCurrentBody();
      final List<AbstractReduceExpression> candidates = EcoreUtil2.<AbstractReduceExpression>getAllContentsOfType(body, AbstractReduceExpression.class);
      final LinkedList<SimplifyingReductionsExploration.ExplorationStep> options = new LinkedList<SimplifyingReductionsExploration.ExplorationStep>();
      final Function1<AbstractReduceExpression, SimplifyingReductionsExploration.StepSelectReduction> _function = (AbstractReduceExpression e) -> {
        return new SimplifyingReductionsExploration.StepSelectReduction(e);
      };
      options.addAll(ListExtensions.<AbstractReduceExpression, SimplifyingReductionsExploration.StepSelectReduction>map(candidates, _function));
      SimplifyingReductionsExploration.StepPrintSystemBody _stepPrintSystemBody = new SimplifyingReductionsExploration.StepPrintSystemBody();
      options.add(_stepPrintSystemBody);
      SimplifyingReductionsExploration.StepPrintCardinality _stepPrintCardinality = new SimplifyingReductionsExploration.StepPrintCardinality();
      options.add(_stepPrintCardinality);
      SimplifyingReductionsExploration.StepSimplifyExpressions _stepSimplifyExpressions = new SimplifyingReductionsExploration.StepSimplifyExpressions();
      options.add(_stepSimplifyExpressions);
      SimplifyingReductionsExploration.StepSplitUnionIntoCase _stepSplitUnionIntoCase = new SimplifyingReductionsExploration.StepSplitUnionIntoCase();
      options.add(_stepSplitUnionIntoCase);
      SimplifyingReductionsExploration.StepBacktrack _stepBacktrack = new SimplifyingReductionsExploration.StepBacktrack();
      options.add(_stepBacktrack);
      SimplifyingReductionsExploration.StepFinish _stepFinish = new SimplifyingReductionsExploration.StepFinish();
      options.add(_stepFinish);
      this.outStream.println("");
      this.outStream.println("Select a ReduceExpression to work on:");
      int _size = options.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          final SimplifyingReductionsExploration.ExplorationStep opt = options.get((i).intValue());
          this.outStream.println(String.format("%d: %s", i, opt.description()));
        }
      }
      final int selected = this.acceptInteger(0, options.size());
      _xblockexpression = this.performAction(options.get(selected));
    }
    return _xblockexpression;
  }

  /**
   * Once a reduction is selected, the basic assumptions must be checked.
   *  - The expression domain of the reduction body is a single polyhedron.
   *  - The ReduceExpression has trivial share space.
   * 
   * The latter is currently not checked; it only happens when the domain of
   * reduction body is unbounded.
   */
  private void expressionDomainCheck() {
    try {
      AlphaExpression _body = this.targetRE.getBody();
      if ((_body instanceof CaseExpression)) {
        final String getExprCommand = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
        this.outStream.println("");
        this.outStream.println("Selected reduction has a CaseExpression as its body. Applying PermutationCaseReduce.");
        this.outStream.print("Press enter/return to continue...");
        this.inStream.readLine();
        PermutationCaseReduce.apply(this.targetRE);
        this.state = SimplifyingReductionsExploration.STATE.INITIAL;
        this.targetRE = null;
        this.commandHistory.add(String.format("PermutationCaseReduce(%s)", getExprCommand));
        return;
      }
      int _nbBasicSets = this.targetRE.getBody().getExpressionDomain().getNbBasicSets();
      boolean _greaterThan = (_nbBasicSets > 1);
      if (_greaterThan) {
        final String getExprCommand_1 = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
        final String getExprCommandBody = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE.getBody());
        this.outStream.println("");
        this.outStream.println("The expression domain of reduction body is not a single polyhedron. Applying SplitUnionIntoCase + PermutationCaseReduce.");
        this.outStream.print("Press enter/return to continue...");
        this.inStream.readLine();
        AlphaExpression _body_1 = this.targetRE.getBody();
        boolean _matched = false;
        if (_body_1 instanceof RestrictExpression) {
          _matched=true;
          AlphaExpression _body_2 = this.targetRE.getBody();
          SplitUnionIntoCase.apply(((RestrictExpression) _body_2));
          PermutationCaseReduce.apply(this.targetRE);
        }
        if (!_matched) {
          if (_body_1 instanceof AutoRestrictExpression) {
            _matched=true;
            AlphaExpression _body_2 = this.targetRE.getBody();
            SplitUnionIntoCase.apply(((AutoRestrictExpression) _body_2));
            PermutationCaseReduce.apply(this.targetRE);
          }
        }
        if (!_matched) {
          throw new UnsupportedOperationException("Expecting the body of reduction to be a restrict. The program may not be normalized.");
        }
        this.state = SimplifyingReductionsExploration.STATE.INITIAL;
        this.targetRE = null;
        this.commandHistory.add(String.format("SplitUnionIntoCase(%s)", getExprCommandBody));
        this.commandHistory.add(String.format("PermutationCaseReduce(%s)", getExprCommand_1));
        return;
      }
      this.state = SimplifyingReductionsExploration.STATE.SIDE_EFFECT_FREE_TRANSFORMATIONS;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Apply side-effect free SR enhancing transformations.
   * This step is completely automatic.
   */
  private void sideEffectFreeTransformations() {
    try {
      final String getExprCommand = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
      final int sosCount = SameOperatorSimplification.apply(this.targetRE);
      if ((sosCount > 0)) {
        this.outStream.println("");
        this.outStream.println(String.format("Applied SameOperatorSimplification: %s", AShow.print(this.targetRE)));
        this.outStream.print("Press enter/return to continue...");
        this.inStream.readLine();
        this.state = SimplifyingReductionsExploration.STATE.INITIAL;
        this.targetRE = null;
        this.commandHistory.add(String.format("SameOperatorSimplification(%s)", getExprCommand));
        return;
      }
      final int distCount = Distributivity.apply(this.targetRE);
      if ((distCount > 0)) {
        this.outStream.println("");
        this.outStream.println(String.format("Applied Distributivity: %s", AShow.print(this.targetRE)));
        this.outStream.print("Press enter/return to continue...");
        this.inStream.readLine();
        this.state = SimplifyingReductionsExploration.STATE.INITIAL;
        this.targetRE = null;
        this.commandHistory.add(String.format("Distributivity(%s)", getExprCommand));
        return;
      }
      this.outStream.println("No side-effect free transformations to apply.");
      this.state = SimplifyingReductionsExploration.STATE.DP_STEP_TRANSFORMATIONS;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Lists all possible DP step that can be taken.
   */
  private Object StepTransformations() {
    Object _xblockexpression = null;
    {
      final ShareSpaceAnalysisResult SSAR = ShareSpaceAnalysis.apply(this.targetRE);
      final LinkedList<SimplifyingReductionsExploration.ExplorationStep> options = new LinkedList<SimplifyingReductionsExploration.ExplorationStep>();
      final LinkedList<long[]> vectors = SimplifyingReductions.generateCandidateReuseVectors(this.targetRE, SSAR);
      for (final long[] vec : vectors) {
        SimplifyingReductionsExploration.StepSimplifyingReduction _stepSimplifyingReduction = new SimplifyingReductionsExploration.StepSimplifyingReduction(vec);
        options.add(_stepSimplifyingReduction);
      }
      boolean _testLegality = Idempotence.testLegality(this.targetRE, SSAR);
      if (_testLegality) {
        SimplifyingReductionsExploration.StepIdempotence _stepIdempotence = new SimplifyingReductionsExploration.StepIdempotence();
        options.add(_stepIdempotence);
      }
      boolean _testLegality_1 = HigherOrderOperator.testLegality(this.targetRE, SSAR);
      if (_testLegality_1) {
        SimplifyingReductionsExploration.StepHigherOrderOperator _stepHigherOrderOperator = new SimplifyingReductionsExploration.StepHigherOrderOperator();
        options.add(_stepHigherOrderOperator);
      }
      boolean _testLegality_2 = ReductionComposition.testLegality(this.targetRE);
      if (_testLegality_2) {
        SimplifyingReductionsExploration.StepReductionComposition _stepReductionComposition = new SimplifyingReductionsExploration.StepReductionComposition();
        options.add(_stepReductionComposition);
      }
      LinkedList<Pair<ISLMultiAff, ISLMultiAff>> _generateDecompositionCandidates = SimplifyingReductions.generateDecompositionCandidates(SSAR, this.targetRE);
      for (final Pair<ISLMultiAff, ISLMultiAff> pair : _generateDecompositionCandidates) {
        ISLMultiAff _key = pair.getKey();
        ISLMultiAff _value = pair.getValue();
        SimplifyingReductionsExploration.StepReductionDecomposition _stepReductionDecomposition = new SimplifyingReductionsExploration.StepReductionDecomposition(_key, _value);
        options.add(_stepReductionDecomposition);
      }
      Iterables.<SimplifyingReductionsExploration.ExplorationStep>addAll(options, SimplifyingReductionsExploration.findInlineCandidates(this.targetRE));
      SimplifyingReductionsExploration.StepPrintSystemBody _stepPrintSystemBody = new SimplifyingReductionsExploration.StepPrintSystemBody();
      options.add(_stepPrintSystemBody);
      SimplifyingReductionsExploration.StepPrintCardinality _stepPrintCardinality = new SimplifyingReductionsExploration.StepPrintCardinality();
      options.add(_stepPrintCardinality);
      SimplifyingReductionsExploration.StepPrintShareSpace _stepPrintShareSpace = new SimplifyingReductionsExploration.StepPrintShareSpace(SSAR);
      options.add(_stepPrintShareSpace);
      SimplifyingReductionsExploration.StepBacktrack _stepBacktrack = new SimplifyingReductionsExploration.StepBacktrack();
      options.add(_stepBacktrack);
      SimplifyingReductionsExploration.StepFinish _stepFinish = new SimplifyingReductionsExploration.StepFinish();
      options.add(_stepFinish);
      this.outStream.println("");
      this.outStream.println("Select an action:");
      int _size = options.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          final SimplifyingReductionsExploration.ExplorationStep opt = options.get((i).intValue());
          this.outStream.println(String.format("%d: %s", i, opt.description()));
        }
      }
      final int selected = this.acceptInteger(0, options.size());
      _xblockexpression = this.performAction(options.get(selected));
    }
    return _xblockexpression;
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepSelectReduction step) {
    this.targetRE = step.are;
    this.state = SimplifyingReductionsExploration.STATE.REDUCTION_SELECTED;
    this.outStream.println(String.format("TargetReduction: %s", AShow.print(this.targetRE)));
    return null;
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepSimplifyingReduction step) {
    boolean _xblockexpression = false;
    {
      EObject _eContainer = this.targetRE.eContainer();
      boolean _not = (!(_eContainer instanceof StandardEquation));
      if (_not) {
        final String getExprCommand = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
        final StandardEquation eq = NormalizeReduction.apply(this.targetRE);
        this.commandHistory.add(String.format("NormalizeReduction(%s)", getExprCommand));
        AlphaExpression _expr = eq.getExpr();
        this.targetRE = ((AbstractReduceExpression) _expr);
      }
      final String getExprCommand_1 = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
      SimplifyingReductions.apply(((ReduceExpression) this.targetRE), step.reuseDepNoParams);
      this.outStream.println(String.format("Applied SimplifyingReductions."));
      this.state = SimplifyingReductionsExploration.STATE.INITIAL;
      this.targetRE = null;
      _xblockexpression = this.commandHistory.add(String.format("SimplifyingReductions(%s, \"%s\")", getExprCommand_1, MatrixOperations.toString(step.reuseDepNoParams)));
    }
    return Boolean.valueOf(_xblockexpression);
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepIdempotence step) {
    try {
      boolean _xblockexpression = false;
      {
        final String getExprCommand = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
        Idempotence.apply(this.targetRE);
        this.outStream.println("");
        this.outStream.println(String.format("Applied Idempotence: %s", AShow.print(this.targetRE)));
        this.outStream.print("Press enter/return to continue...");
        this.inStream.readLine();
        this.state = SimplifyingReductionsExploration.STATE.INITIAL;
        this.targetRE = null;
        _xblockexpression = this.commandHistory.add(String.format("Idempotence(%s)", getExprCommand));
      }
      return Boolean.valueOf(_xblockexpression);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepHigherOrderOperator step) {
    try {
      boolean _xblockexpression = false;
      {
        final String getExprCommand = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
        HigherOrderOperator.apply(this.targetRE);
        this.outStream.println("");
        this.outStream.println(String.format("Applied HigherOrderOperator: %s", AShow.print(this.targetRE)));
        this.outStream.print("Press enter/return to continue...");
        this.inStream.readLine();
        this.state = SimplifyingReductionsExploration.STATE.INITIAL;
        this.targetRE = null;
        _xblockexpression = this.commandHistory.add(String.format("HigherOrderOperator(%s)", getExprCommand));
      }
      return Boolean.valueOf(_xblockexpression);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepReductionComposition step) {
    try {
      boolean _xblockexpression = false;
      {
        final String getExprCommand = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
        ReductionComposition.apply(this.targetRE);
        Normalize.apply(this.getCurrentBody());
        this.outStream.println("");
        this.outStream.println(String.format("Applied ReductionComposition: %s", AShow.print(this.targetRE)));
        this.outStream.print("Press enter/return to continue...");
        this.inStream.readLine();
        this.state = SimplifyingReductionsExploration.STATE.SIDE_EFFECT_FREE_TRANSFORMATIONS;
        this.commandHistory.add(String.format("ReductionComposition(%s)", getExprCommand));
        _xblockexpression = this.commandHistory.add(String.format("Normalize(body)"));
      }
      return Boolean.valueOf(_xblockexpression);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepReductionDecomposition step) {
    try {
      boolean _xblockexpression = false;
      {
        final String getExprCommand = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
        ReductionDecomposition.apply(this.targetRE, step.innerProjection, step.outerProjection);
        Normalize.apply(this.getCurrentBody());
        this.outStream.println("");
        this.outStream.println(String.format("Applied ReductionDecomposition: %s", AShow.print(this.targetRE)));
        this.outStream.print("Press enter/return to continue...");
        this.inStream.readLine();
        this.state = SimplifyingReductionsExploration.STATE.INITIAL;
        this.targetRE = null;
        this.commandHistory.add(String.format("ReductionDecomposition(%s, \"%s\", \"%s\")", getExprCommand, 
          AlphaPrintingUtil.toShowString(step.innerProjection), 
          AlphaPrintingUtil.toShowString(step.outerProjection)));
        _xblockexpression = this.commandHistory.add(String.format("Normalize(body)"));
      }
      return Boolean.valueOf(_xblockexpression);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepInlineVariable step) {
    try {
      boolean _xblockexpression = false;
      {
        final String getExprCommand = SimplifyingReductionsExploration.getExpressionCommandString(this.targetRE);
        SubstituteByDef.apply(this.targetRE, step.variable);
        Normalize.apply(this.targetRE);
        this.outStream.println("");
        this.outStream.println(String.format("Applied SubstituteByDef: %s", AShow.print(this.targetRE)));
        this.outStream.print("Press enter/return to continue...");
        this.inStream.readLine();
        this.state = SimplifyingReductionsExploration.STATE.SIDE_EFFECT_FREE_TRANSFORMATIONS;
        this.commandHistory.add(String.format("SubstituteByDef(%s, \"%s\")", getExprCommand, step.variable.getName()));
        _xblockexpression = this.commandHistory.add(String.format("Normalize(%s)", getExprCommand));
      }
      return Boolean.valueOf(_xblockexpression);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepSimplifyExpressions step) {
    boolean _xblockexpression = false;
    {
      SimplifyExpressions.apply(this.getCurrentBody());
      Normalize.apply(this.getCurrentBody());
      this.outStream.println("");
      this.outStream.println(String.format("Applied SimplifyExpressions"));
      this.commandHistory.add(String.format("SimplifyExpressions(body)"));
      _xblockexpression = this.commandHistory.add(String.format("Normalize(body)"));
    }
    return Boolean.valueOf(_xblockexpression);
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepSplitUnionIntoCase step) {
    boolean _xblockexpression = false;
    {
      SplitUnionIntoCase.apply(this.getCurrentBody());
      this.outStream.println("");
      this.outStream.println(String.format("Applied SplitUnionIntoCase"));
      _xblockexpression = this.commandHistory.add(String.format("SplitUnionIntoCase(body)"));
    }
    return Boolean.valueOf(_xblockexpression);
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepPrintSystemBody step) {
    this.outStream.println(AShow.print(this.getCurrentBody()));
    return null;
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepPrintCardinality step) {
    final List<AbstractReduceExpression> candidates = EcoreUtil2.<AbstractReduceExpression>getAllContentsOfType(this.getCurrentBody(), AbstractReduceExpression.class);
    for (final AbstractReduceExpression candidate : candidates) {
      int _nbBasicSets = candidate.getBody().getContextDomain().getNbBasicSets();
      boolean _greaterThan = (_nbBasicSets > 1);
      if (_greaterThan) {
        this.outStream.println(String.format("N/A; context domain involves union. : card(%s)", AShow.print(candidate)));
      } else {
        final ISLPWQPolynomial card = BarvinokFunctions.card(candidate.getBody().getContextDomain());
        final ISLQPolynomial qp = card.getPieceAt(0).getQp();
        this.outStream.println(String.format("%s : card(%s)", AlphaPrintingUtil.toShowString(qp), AShow.print(candidate)));
      }
    }
    return null;
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepPrintShareSpace step) {
    this.outStream.println(step.SSAR);
    return null;
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepBacktrack step) {
    do {
      this.rollbackState();
    } while((((!Objects.equal(this.state, SimplifyingReductionsExploration.STATE.INITIAL)) && (!Objects.equal(this.state, SimplifyingReductionsExploration.STATE.REDUCTION_SELECTED))) && (!Objects.equal(this.state, SimplifyingReductionsExploration.STATE.DP_STEP_TRANSFORMATIONS))));
    return null;
  }

  private Object _performAction(final SimplifyingReductionsExploration.StepFinish step) {
    return this.state = SimplifyingReductionsExploration.STATE.EXIT;
  }

  private static String getExpressionCommandString(final AlphaExpression target) {
    final CharSequence eqName = SimplifyingReductionsExploration.getEquationName(AlphaUtil.getContainerEquation(target));
    final EList<Integer> exprID = target.getExpressionID();
    return String.format("GetExpression(body, \"%s\", \"%s\")", eqName, exprID);
  }

  @Override
  protected void initProperties() {
    this.setProperty("state", SimplifyingReductionsExploration.STATE.INITIAL);
  }

  @Override
  protected void reflectProperties() {
    SimplifyingReductionsExploration.STATE _xifexpression = null;
    Object _property = this.getProperty("state");
    boolean _tripleNotEquals = (_property != null);
    if (_tripleNotEquals) {
      Object _property_1 = this.getProperty("state");
      _xifexpression = ((SimplifyingReductionsExploration.STATE) _property_1);
    } else {
      _xifexpression = SimplifyingReductionsExploration.STATE.INITIAL;
    }
    this.state = _xifexpression;
    AbstractReduceExpression _xifexpression_1 = null;
    Object _property_2 = this.getProperty("targetRE");
    boolean _tripleNotEquals_1 = (_property_2 != null);
    if (_tripleNotEquals_1) {
      Object _property_3 = this.getProperty("targetRE");
      _xifexpression_1 = ((AbstractReduceExpression) _property_3);
    } else {
      _xifexpression_1 = null;
    }
    this.targetRE = _xifexpression_1;
    if (((this.targetRE != null) && (AlphaUtil.getContainerSystemBody(this.targetRE) != this.getCurrentBody()))) {
      throw new RuntimeException("Main loop state is inconsistent.");
    }
  }

  private static Iterable<SimplifyingReductionsExploration.StepInlineVariable> findInlineCandidates(final AbstractReduceExpression are) {
    final Function1<VariableExpression, Boolean> _function = (VariableExpression ve) -> {
      return Boolean.valueOf(((ve.getVariable().isLocal()).booleanValue() || (ve.getVariable().isOutput()).booleanValue()));
    };
    final Function1<VariableExpression, SimplifyingReductionsExploration.StepInlineVariable> _function_1 = (VariableExpression ve) -> {
      Variable _variable = ve.getVariable();
      return new SimplifyingReductionsExploration.StepInlineVariable(_variable);
    };
    return IterableExtensions.<VariableExpression, SimplifyingReductionsExploration.StepInlineVariable>map(IterableExtensions.<VariableExpression>filter(EcoreUtil2.<VariableExpression>getAllContentsOfType(are, VariableExpression.class), _function), _function_1);
  }

  private static CharSequence _getEquationName(final StandardEquation eq) {
    return eq.getVariable().getName();
  }

  private static CharSequence _getEquationName(final UseEquation eq) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("UseEquation");
    return _builder;
  }

  private Object performAction(final SimplifyingReductionsExploration.ExplorationStep step) {
    if (step instanceof SimplifyingReductionsExploration.StepBacktrack) {
      return _performAction((SimplifyingReductionsExploration.StepBacktrack)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepFinish) {
      return _performAction((SimplifyingReductionsExploration.StepFinish)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepHigherOrderOperator) {
      return _performAction((SimplifyingReductionsExploration.StepHigherOrderOperator)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepIdempotence) {
      return _performAction((SimplifyingReductionsExploration.StepIdempotence)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepInlineVariable) {
      return _performAction((SimplifyingReductionsExploration.StepInlineVariable)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepPrintCardinality) {
      return _performAction((SimplifyingReductionsExploration.StepPrintCardinality)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepPrintShareSpace) {
      return _performAction((SimplifyingReductionsExploration.StepPrintShareSpace)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepPrintSystemBody) {
      return _performAction((SimplifyingReductionsExploration.StepPrintSystemBody)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepReductionComposition) {
      return _performAction((SimplifyingReductionsExploration.StepReductionComposition)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepReductionDecomposition) {
      return _performAction((SimplifyingReductionsExploration.StepReductionDecomposition)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepSelectReduction) {
      return _performAction((SimplifyingReductionsExploration.StepSelectReduction)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepSimplifyExpressions) {
      return _performAction((SimplifyingReductionsExploration.StepSimplifyExpressions)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepSimplifyingReduction) {
      return _performAction((SimplifyingReductionsExploration.StepSimplifyingReduction)step);
    } else if (step instanceof SimplifyingReductionsExploration.StepSplitUnionIntoCase) {
      return _performAction((SimplifyingReductionsExploration.StepSplitUnionIntoCase)step);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(step).toString());
    }
  }

  private static CharSequence getEquationName(final Equation eq) {
    if (eq instanceof StandardEquation) {
      return _getEquationName((StandardEquation)eq);
    } else if (eq instanceof UseEquation) {
      return _getEquationName((UseEquation)eq);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(eq).toString());
    }
  }
}
