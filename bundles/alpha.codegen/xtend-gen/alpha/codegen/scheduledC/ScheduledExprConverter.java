package alpha.codegen.scheduledC;

import alpha.codegen.AssignmentStmt;
import alpha.codegen.BinaryExpr;
import alpha.codegen.BinaryOperator;
import alpha.codegen.CallExpr;
import alpha.codegen.CustomExpr;
import alpha.codegen.Expression;
import alpha.codegen.Factory;
import alpha.codegen.Function;
import alpha.codegen.FunctionBuilder;
import alpha.codegen.MacroStmt;
import alpha.codegen.Parameter;
import alpha.codegen.ParenthesizedExpr;
import alpha.codegen.ProgramBuilder;
import alpha.codegen.Statement;
import alpha.codegen.alphaBase.AlphaBaseHelpers;
import alpha.codegen.alphaBase.AlphaNameChecker;
import alpha.codegen.alphaBase.ExprConverter;
import alpha.codegen.isl.ASTConversionResult;
import alpha.codegen.isl.ASTConverter;
import alpha.codegen.isl.LoopGenerator;
import alpha.model.AlphaExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.MultiArgExpression;
import alpha.model.PolynomialIndexExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.UnaryExpression;
import alpha.model.VariableExpression;
import alpha.model.memorymapper.MemoryMapper;
import alpha.model.scheduler.Scheduler;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Converts Alpha expressions to simpleC expressions.
 * Adds support for reduce expressions.
 */
@SuppressWarnings("all")
public class ScheduledExprConverter extends ExprConverter {
  /**
   * The name of the reduction variable inside of reduce functions.
   */
  protected static final String reduceVarName = "reduceVar";

  /**
   * The program being built.
   */
  protected final ProgramBuilder program;

  /**
   * Generates data types compatible with WriteC.
   */
  protected final ScheduledTypeGenerator typeGenerator;

  /**
   * Generates the schedule to be used by the ISL LoopGenerator
   */
  protected final Scheduler scheduler;

  /**
   * Represents the current reduction target for schedule lookup
   */
  protected String reductionTarget;

  /**
   * Represents the number of reductions for the current reduction target for schedule lookup
   */
  protected int reductionTargetNumber = 0;

  protected MemoryMapper mapper;

  /**
   * A counter for the number of reductions that have been created.
   * This is used for determining the names of functions and macros which will be emitted.
   */
  protected int nextReductionId = 0;

  /**
   * Constructs a new converter for expressions.
   */
  public ScheduledExprConverter(final ScheduledTypeGenerator typeGenerator, final AlphaNameChecker nameChecker, final ProgramBuilder program, final Scheduler scheduler, final MemoryMapper mapper) {
    super(typeGenerator, nameChecker);
    this.program = program;
    this.typeGenerator = typeGenerator;
    this.scheduler = scheduler;
    this.reductionTarget = "";
    this.mapper = mapper;
  }

  public String setTarget(final String reductionTarget) {
    String _xblockexpression = null;
    {
      boolean _notEquals = (!Objects.equal(this.reductionTarget, reductionTarget));
      if (_notEquals) {
        this.reductionTargetNumber = 0;
      }
      _xblockexpression = this.reductionTarget = reductionTarget;
    }
    return _xblockexpression;
  }

  /**
   * Converts an Alpha reduce expression into the appropriate C AST nodes.
   * A new function is created and added to the program which computes the reduction,
   * and the appropriate function call expression is returned.
   */
  protected Expression _convertExpr(final ReduceExpression expr) {
    final Function reduceFunction = this.createReduceFunction(this.program, expr, this.reductionTarget);
    this.program.addFunction(reduceFunction);
    List<String> _paramNames = expr.getContextDomain().getParamNames();
    List<String> _indexNames = expr.getContextDomain().getIndexNames();
    final Iterable<String> callArguments = Iterables.<String>concat(Collections.<List<String>>unmodifiableList(CollectionLiterals.<List<String>>newArrayList(_paramNames, _indexNames)));
    return Factory.callExpr(reduceFunction.getName(), ((String[])Conversions.unwrapArray(callArguments, String.class)));
  }

  /**
   * Creates the function which evaluates the reduction at a specific output point.
   */
  protected Function createReduceFunction(final ProgramBuilder program, final ReduceExpression expr, final String variableName) {
    String reduceFunctionName = null;
    String reducePointMacroName = null;
    String accumulateMacroName = null;
    do {
      {
        reduceFunctionName = ("reduce" + Integer.valueOf(this.nextReductionId));
        reducePointMacroName = ("RP" + Integer.valueOf(this.nextReductionId));
        accumulateMacroName = ("R" + Integer.valueOf(this.nextReductionId));
        int _nextReductionId = this.nextReductionId;
        this.nextReductionId = (_nextReductionId + 1);
      }
    } while(program.getNameChecker().isGlobalOrKeyword(reduceFunctionName, reducePointMacroName, accumulateMacroName));
    final FunctionBuilder function = program.startFunction(true, false, this.typeGenerator.getAlphaValueType(), reduceFunctionName);
    final AssignmentStmt initializeStmt = Factory.assignmentStmt(ScheduledExprConverter.reduceVarName, AlphaBaseHelpers.getReductionInitialValue(this.typeGenerator.getAlphaValueBaseType(), expr.getOperator()));
    function.addVariable(this.typeGenerator.getAlphaValueType(), ScheduledExprConverter.reduceVarName).addStatement(initializeStmt);
    final MacroStmt reducePointMacro = this.createReducePointMacro(reducePointMacroName, program, expr);
    final MacroStmt accumulateMacro = this.createAccumulationMacro(accumulateMacroName, expr, reducePointMacro);
    function.addStatement(reducePointMacro, accumulateMacro);
    final String reduceBodyName = (((variableName + "_reduce") + Integer.valueOf(this.reductionTargetNumber)) + "_body");
    this.reductionTargetNumber++;
    ISLSet generatedDomain = this.createReduceLoopDomain(expr);
    generatedDomain = generatedDomain.setTupleName(reduceBodyName);
    ISLSet _elvis = null;
    ISLSet _scheduleDomain = this.scheduler.getScheduleDomain(reduceBodyName);
    if (_scheduleDomain != null) {
      _elvis = _scheduleDomain;
    } else {
      ISLSet _copy = generatedDomain.copy();
      _elvis = _copy;
    }
    ISLSet loopDomain = _elvis;
    loopDomain = loopDomain.setTupleName(reduceBodyName);
    loopDomain = loopDomain.intersect(generatedDomain);
    ISLMap _elvis_1 = null;
    ISLMap _scheduleMap = this.scheduler.getScheduleMap(reduceBodyName);
    if (_scheduleMap != null) {
      _elvis_1 = _scheduleMap;
    } else {
      ISLMap _identity = loopDomain.copy().identity();
      _elvis_1 = _identity;
    }
    ISLMap scheduleMap = _elvis_1;
    final ISLASTNode islAST = LoopGenerator.generateLoops(accumulateMacro.getName(), 
      loopDomain.copy(), 
      scheduleMap.copy().intersectDomain(loopDomain.copy()).copy());
    final Function1<String, Parameter> _function = (String it) -> {
      return this.toParameter(it);
    };
    function.addParameter(((Parameter[])Conversions.unwrapArray(ListExtensions.<String, Parameter>map(loopDomain.copy().getParamNames(), _function), Parameter.class)));
    final ASTConversionResult loopResult = ASTConverter.convert(islAST);
    final Consumer<String> _function_1 = (String it) -> {
      function.addVariable(this.typeGenerator.getIndexType(), it);
    };
    loopResult.getDeclarations().forEach(_function_1);
    function.addStatement(((Statement[])Conversions.unwrapArray(loopResult.getStatements(), Statement.class)));
    function.addUndefine(reducePointMacro, accumulateMacro).addReturn(ScheduledExprConverter.reduceVarExpr());
    return function.getInstance();
  }

  /**
   * Constructs the domain which will represent the loop nest that isl will produce.
   */
  protected ISLSet createReduceLoopDomain(final ReduceExpression reduceExpr) {
    ISLSet pointsToReduce = reduceExpr.getBody().getContextDomain().copy();
    final HashSet<String> existingNames = CollectionLiterals.<String>newHashSet();
    existingNames.addAll(pointsToReduce.getParamNames());
    existingNames.addAll(pointsToReduce.getIndexNames());
    final ISLSet outputDomain = reduceExpr.getContextDomain();
    int _nbIndices = outputDomain.getNbIndices();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbIndices, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final String outputName = outputDomain.getIndexName((i).intValue());
        final String parameterName = this.nameChecker.getUniqueLocalName(existingNames, outputName, "p");
        existingNames.add(parameterName);
        pointsToReduce = pointsToReduce.<ISLSet>addParams(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(parameterName)));
        final ISLAff outputExpr = reduceExpr.getProjection().getAff((i).intValue());
        final ISLConstraint outputConstraint = ScheduledExprConverter.constrainAddedParameter(pointsToReduce.getSpace(), outputExpr);
        pointsToReduce = pointsToReduce.addConstraint(outputConstraint);
      }
    }
    return pointsToReduce;
  }

  /**
   * Adds an equality constraint to the recently added parameter
   * (i.e., the parameter with the largest index).
   * This parameter is set equal to the given affine expression
   * (which comes from the reduction's projection function).
   */
  protected static ISLConstraint constrainAddedParameter(final ISLSpace reductionSpace, final ISLAff outputExpr) {
    int _nbParams = reductionSpace.getNbParams();
    final int parameterIndex = (_nbParams - 1);
    ISLConstraint equality = ISLConstraint.buildEquality(reductionSpace.copy()).setCoefficient(ISLDimType.isl_dim_param, parameterIndex, (-1));
    int _nbParams_1 = outputExpr.getNbParams();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbParams_1, true);
    for (final Integer paramIdx : _doubleDotLessThan) {
      {
        final ISLVal coefficient = outputExpr.getCoefficientVal(ISLDimType.isl_dim_param, (paramIdx).intValue());
        equality = equality.setCoefficient(ISLDimType.isl_dim_param, (paramIdx).intValue(), coefficient);
      }
    }
    int _nbInputs = outputExpr.getNbInputs();
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _nbInputs, true);
    for (final Integer inIdx : _doubleDotLessThan_1) {
      {
        final ISLVal coefficient = outputExpr.getCoefficientVal(ISLDimType.isl_dim_in, (inIdx).intValue());
        equality = equality.setCoefficient(ISLDimType.isl_dim_set, (inIdx).intValue(), coefficient);
      }
    }
    return equality;
  }

  /**
   * Gets an expression representing the reduce variable.
   */
  protected static CustomExpr reduceVarExpr() {
    return Factory.customExpr(ScheduledExprConverter.reduceVarName);
  }

  /**
   * Constructs a parameter for the reduce function.
   */
  protected Parameter toParameter(final String name) {
    return Factory.parameter(this.typeGenerator.getIndexType(), name);
  }

  /**
   * Constructs the macro that evaluates a point within the reduction body.
   */
  protected MacroStmt createReducePointMacro(final String macroName, final ProgramBuilder program, final ReduceExpression expr) {
    final List<String> arguments = expr.getBody().getContextDomain().getIndexNames();
    final Expression replacement = this.convertExpr(expr.getBody());
    return Factory.macroStmt(macroName, ((String[])Conversions.unwrapArray(arguments, String.class)), replacement);
  }

  /**
   * Constructs the macro used to accumulate points of the reduction body into the reduce variable.
   */
  protected MacroStmt createAccumulationMacro(final String macroName, final ReduceExpression expr, final MacroStmt reducePointMacro) {
    final Function1<String, ParenthesizedExpr> _function = (String it) -> {
      return Factory.parenthesizedExpr(it);
    };
    final List<ParenthesizedExpr> reducePointArguments = ListExtensions.<String, ParenthesizedExpr>map(expr.getBody().getContextDomain().getIndexNames(), _function);
    final CallExpr reducePointCall = Factory.callExpr(reducePointMacro.getName(), ((Expression[])Conversions.unwrapArray(reducePointArguments, Expression.class)));
    final BinaryOperator operator = AlphaBaseHelpers.getOperator(expr.getOperator());
    final BinaryExpr accumulateExpr = Factory.binaryExpr(operator, ScheduledExprConverter.reduceVarExpr(), reducePointCall);
    final AssignmentStmt accumulateStmt = Factory.assignmentStmt(ScheduledExprConverter.reduceVarExpr(), accumulateExpr);
    return Factory.macroStmt(macroName, ((String[])Conversions.unwrapArray(expr.getBody().getContextDomain().getIndexNames(), String.class)), accumulateStmt);
  }

  public Expression convertExpr(final AlphaExpression expr) {
    if (expr instanceof ReduceExpression) {
      return _convertExpr((ReduceExpression)expr);
    } else if (expr instanceof AutoRestrictExpression) {
      return _convertExpr((AutoRestrictExpression)expr);
    } else if (expr instanceof BinaryExpression) {
      return _convertExpr((BinaryExpression)expr);
    } else if (expr instanceof CaseExpression) {
      return _convertExpr((CaseExpression)expr);
    } else if (expr instanceof ConstantExpression) {
      return _convertExpr((ConstantExpression)expr);
    } else if (expr instanceof DependenceExpression) {
      return _convertExpr((DependenceExpression)expr);
    } else if (expr instanceof IfExpression) {
      return _convertExpr((IfExpression)expr);
    } else if (expr instanceof IndexExpression) {
      return _convertExpr((IndexExpression)expr);
    } else if (expr instanceof MultiArgExpression) {
      return _convertExpr((MultiArgExpression)expr);
    } else if (expr instanceof PolynomialIndexExpression) {
      return _convertExpr((PolynomialIndexExpression)expr);
    } else if (expr instanceof RestrictExpression) {
      return _convertExpr((RestrictExpression)expr);
    } else if (expr instanceof UnaryExpression) {
      return _convertExpr((UnaryExpression)expr);
    } else if (expr instanceof VariableExpression) {
      return _convertExpr((VariableExpression)expr);
    } else if (expr != null) {
      return _convertExpr(expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
}
