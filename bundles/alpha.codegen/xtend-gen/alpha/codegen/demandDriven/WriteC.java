package alpha.codegen.demandDriven;

import alpha.codegen.ArrayAccessExpr;
import alpha.codegen.AssignmentStmt;
import alpha.codegen.BaseDataType;
import alpha.codegen.BinaryExpr;
import alpha.codegen.BinaryOperator;
import alpha.codegen.CallExpr;
import alpha.codegen.CastExpr;
import alpha.codegen.CustomExpr;
import alpha.codegen.DataType;
import alpha.codegen.Expression;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.Factory;
import alpha.codegen.FunctionBuilder;
import alpha.codegen.IfStmt;
import alpha.codegen.IfStmtBuilder;
import alpha.codegen.MacroStmt;
import alpha.codegen.ParenthesizedExpr;
import alpha.codegen.Program;
import alpha.codegen.ProgramBuilder;
import alpha.codegen.Statement;
import alpha.codegen.VariableDecl;
import alpha.codegen.alphaBase.AlphaBaseHelpers;
import alpha.codegen.alphaBase.AlphaNameChecker;
import alpha.codegen.alphaBase.CodeGeneratorBase;
import alpha.codegen.alphaBase.FlagStatus;
import alpha.codegen.isl.ASTConversionResult;
import alpha.codegen.isl.ASTConverter;
import alpha.codegen.isl.LoopGenerator;
import alpha.codegen.isl.MemoryUtils;
import alpha.codegen.isl.PolynomialConverter;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Generates demand-driven code that performs cycle detection.
 * The intended entry point is the static "convert" method,
 * which handles the entire conversion process.
 */
@SuppressWarnings("all")
public class WriteC extends CodeGeneratorBase {
  /**
   * If true, compatibility with the older AlphaZ system will be maintained.
   * If false, all memory will be linearized.
   */
  protected final boolean oldAlphaZCompatible;

  /**
   * Converts Alpha expressions to simpleC expressions.
   */
  protected final WriteCExprConverter exprConverter;

  /**
   * The next ID to use as a statement macro.
   */
  protected int nextStatementId = 0;

  public static Program convert(final AlphaSystem system, final BaseDataType valueType, final boolean oldAlphaZCompatible) {
    int _length = ((Object[])Conversions.unwrapArray(system.getSystemBodies(), Object.class)).length;
    boolean _notEquals = (_length != 1);
    if (_notEquals) {
      throw new IllegalArgumentException("Systems must have exactly one body to be converted directly to WriteC code.");
    }
    final AlphaSystem duplicate = AlphaUtil.<AlphaSystem>copyAE(system);
    final SystemBody body = duplicate.getSystemBodies().get(0);
    return WriteC.convert(body, valueType, oldAlphaZCompatible);
  }

  /**
   * Converts an Alpha system body to a simpleC AST.
   * @param systemBody The system body to convert into a simpleC AST.
   * @param valueType The type of values stored by Alpha variables.
   * @param oldAlphaZCompatible If true, compatibility with the older AlphaZ system will be maintained.
   *     If false, all memory will be linearized.
   */
  public static Program convert(final SystemBody systemBody, final BaseDataType valueType, final boolean oldAlphaZCompatible) {
    return new WriteC(systemBody, valueType, oldAlphaZCompatible).convertSystemBody();
  }

  /**
   * Constructs a new instance of this class.
   * @param systemBody The system body to convert into a simpleC AST.
   * @param valueType The type of values stored by Alpha variables.
   * @param oldAlphaZCompatible If true, compatibility with the older AlphaZ system will be maintained.
   *     If false, all memory will be linearized.
   */
  public WriteC(final SystemBody systemBody, final BaseDataType valueType, final boolean oldAlphaZCompatible) {
    this(systemBody, valueType, oldAlphaZCompatible, new WriteCTypeGenerator(valueType, oldAlphaZCompatible));
  }

  /**
   * This is a workaround because the WriteCTypeChecker needs to be passed both to the parent class's
   * constructor and the expression converter, but Xtend wouldn't allow that type checker to be
   * created before "super(...)" is called.
   */
  private WriteC(final SystemBody systemBody, final BaseDataType valueType, final boolean oldAlphaZCompatible, final WriteCTypeGenerator typeGenerator) {
    super(systemBody, new AlphaNameChecker(true), typeGenerator, true);
    this.oldAlphaZCompatible = oldAlphaZCompatible;
    WriteCExprConverter _writeCExprConverter = new WriteCExprConverter(typeGenerator, this.nameChecker, this.program);
    this.exprConverter = _writeCExprConverter;
  }

  /**
   * Allocates memory for the given variable.
   */
  @Override
  public void declareMemoryMacro(final Variable variable) {
    final String name = this.nameChecker.getVariableStorageName(variable);
    if ((this.oldAlphaZCompatible && ((variable.isInput()).booleanValue() || (variable.isOutput()).booleanValue()))) {
      this.declareCompatibleMemoryMacro(name, variable.getDomain());
    } else {
      this.declareLinearMemoryMacro(name, variable.getDomain());
    }
  }

  /**
   * Allocates memory for the flags variable associated with the given variable.
   */
  @Override
  public void declareFlagMemoryMacro(final Variable variable) {
    final String name = this.nameChecker.getFlagName(variable);
    this.declareLinearMemoryMacro(name, variable.getDomain());
  }

  /**
   * Declares a memory macro for accessing a linearized array of memory
   * as if it's a multi-dimensional variable. The macro's name is the
   * same as the variable's name.
   */
  protected ProgramBuilder declareLinearMemoryMacro(final String variableName, final ISLSet domain) {
    ProgramBuilder _xblockexpression = null;
    {
      final ISLPWQPolynomial rank = MemoryUtils.rank(domain);
      final ParenthesizedExpr accessExpression = PolynomialConverter.convert(rank);
      final ArrayAccessExpr macroReplacement = Factory.arrayAccessExpr(variableName, accessExpression);
      final MacroStmt macroStmt = Factory.macroStmt(variableName, ((String[])Conversions.unwrapArray(domain.getIndexNames(), String.class)), macroReplacement);
      _xblockexpression = this.program.addMemoryMacro(macroStmt);
    }
    return _xblockexpression;
  }

  /**
   * Declares a memory macro for accessing memory allocated by the older AlphaZ system.
   * The macro's name is the same as the variable's name.
   * Note: this is known to not work if the given domain is allowed to have negative indices,
   * but this is how the older AlphaZ generated its code.
   */
  protected ProgramBuilder declareCompatibleMemoryMacro(final String variableName, final ISLSet domain) {
    ProgramBuilder _xblockexpression = null;
    {
      final ArrayAccessExpr arrayAccess = Factory.arrayAccessExpr(variableName, ((String[])Conversions.unwrapArray(domain.getIndexNames(), String.class)));
      final MacroStmt macro = Factory.macroStmt(variableName, ((String[])Conversions.unwrapArray(domain.getIndexNames(), String.class)), arrayAccess);
      _xblockexpression = this.program.addMemoryMacro(macro);
    }
    return _xblockexpression;
  }

  /**
   * Throws an exception, as UseEquations are not supported.
   */
  @Override
  public void declareEvaluation(final UseEquation equation) {
    throw new UnsupportedOperationException("Use equations are not currently supported.");
  }

  /**
   * Declares the "eval" function used to evaluate each variable per the given equation.
   */
  @Override
  public void declareEvaluation(final StandardEquation equation) {
    final DataType returnType = this.typeGenerator.getAlphaValueType(equation.getVariable());
    final String evalName = this.nameChecker.getVariableReadName(equation.getVariable());
    final FunctionBuilder evalBuilder = this.program.startFunction(true, false, returnType, evalName);
    final List<String> indexNames = equation.getExpr().getContextDomain().getIndexNames();
    final Consumer<String> _function = (String it) -> {
      evalBuilder.addParameter(this.typeGenerator.getIndexType(), it);
    };
    indexNames.forEach(_function);
    final IfStmt flagCheckingBlock = this.getFlagCheckingBlock(equation);
    evalBuilder.addComment("Check the flags.").addStatement(flagCheckingBlock).addEmptyLine().addReturn(this.identityAccess(equation, false));
    Boolean _involvesFractalReduction = this.involvesFractalReduction(equation.getVariable());
    boolean _not = (!(_involvesFractalReduction).booleanValue());
    if (_not) {
      this.program.addFunction(evalBuilder.getInstance());
    }
  }

  /**
   * Constructs the "if" statement block that checks the status of the flags variable and
   * evaluates the variable if needed, or reports a self-dependence if detected.
   */
  protected IfStmt getFlagCheckingBlock(final StandardEquation equation) {
    final Expression computeValue = this.exprConverter.convertExpr(equation.getExpr());
    final AssignmentStmt computeAndStore = Factory.assignmentStmt(this.identityAccess(equation, false), computeValue);
    return IfStmtBuilder.start(this.ifFlagEquals(equation, FlagStatus.NOT_EVALUATED)).addStatement(
      this.setFlagTo(equation, FlagStatus.IN_PROGRESS), computeAndStore, 
      this.setFlagTo(equation, FlagStatus.EVALUATED)).startElseIf(this.ifFlagEquals(equation, FlagStatus.IN_PROGRESS)).addStatement(
      WriteC.getSelfDependencePrintfStmt(equation), 
      Factory.exitCall((-1))).getInstance();
  }

  /**
   * Gets the expression used to access a variable (or its flag).
   */
  protected CallExpr identityAccess(final StandardEquation equation, final boolean accessFlags) {
    String _xifexpression = null;
    if (accessFlags) {
      _xifexpression = this.nameChecker.getFlagName(equation.getVariable());
    } else {
      _xifexpression = equation.getVariable().getName();
    }
    final String name = _xifexpression;
    return Factory.callExpr(name, ((String[])Conversions.unwrapArray(equation.getExpr().getContextDomain().getIndexNames(), String.class)));
  }

  /**
   * Gets the expression to check if a flags variable is set to a given value.
   */
  protected BinaryExpr ifFlagEquals(final StandardEquation equation, final FlagStatus flagStatus) {
    return Factory.binaryExpr(BinaryOperator.EQ, this.identityAccess(equation, true), AlphaBaseHelpers.toExpr(flagStatus));
  }

  /**
   * Gets the statement that sets a flags variable to a given value.
   */
  protected AssignmentStmt setFlagTo(final StandardEquation equation, final FlagStatus flagStatus) {
    return Factory.assignmentStmt(this.identityAccess(equation, true), AlphaBaseHelpers.toExpr(flagStatus));
  }

  /**
   * Gets the "printf" statement that tells the user a self dependence was found (and where it was).
   */
  protected static ExpressionStmt getSelfDependencePrintfStmt(final StandardEquation equation) {
    int _nbIndices = equation.getExpr().getContextDomain().getNbIndices();
    final Function1<Integer, String> _function = (Integer it) -> {
      return "%ld";
    };
    final String locationFormat = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _nbIndices, true), _function), ",");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"There is a self dependence on ");
    String _name = equation.getVariable().getName();
    _builder.append(_name);
    _builder.append(" at (");
    _builder.append(locationFormat);
    _builder.append(")\\n\"");
    final String message = _builder.toString();
    return Factory.printfCall(message, ((String[])Conversions.unwrapArray(equation.getExpr().getContextDomain().getIndexNames(), String.class)));
  }

  /**
   * Allocates memory for the given variable.
   */
  @Override
  public void allocateVariable(final Variable variable) {
    final String name = this.nameChecker.getVariableStorageName(variable);
    final DataType dataType = this.typeGenerator.getAlphaVariableType(variable);
    this.allocateLinearMemory(name, variable.getDomain(), dataType);
  }

  /**
   * Allocates memory for the flags variable associated with the given variable.
   */
  @Override
  public void allocateFlagsVariable(final Variable variable) {
    final String name = this.nameChecker.getFlagName(variable);
    final DataType dataType = this.typeGenerator.getFlagVariableType(variable);
    this.allocateLinearMemory(name, variable.getDomain(), dataType);
    final CustomExpr nameExpr = Factory.customExpr(name);
    final CustomExpr initialValue = AlphaBaseHelpers.toExpr(FlagStatus.NOT_EVALUATED);
    final ParenthesizedExpr cardinalityExpr = WriteC.getCardinalityExpr(variable.getDomain());
    final ExpressionStmt memsetCall = Factory.callStmt("memset", nameExpr, initialValue, cardinalityExpr);
    this.entryPoint.addStatement(memsetCall);
  }

  /**
   * Adds statements to the entry point function which allocate linearized for a variable.
   */
  protected void allocateLinearMemory(final String name, final ISLSet domain, final DataType dataType) {
    this.allocatedVariables.add(name);
    final ParenthesizedExpr cardinalityExpr = WriteC.getCardinalityExpr(domain);
    final CastExpr mallocCall = Factory.mallocCall(dataType, cardinalityExpr);
    final AssignmentStmt mallocAssignment = Factory.assignmentStmt(name, mallocCall);
    this.entryPoint.addStatement(mallocAssignment);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"");
    _builder.append(name);
    _builder.append("\"");
    final CustomExpr nameStringExpr = Factory.customExpr(_builder.toString());
    final ExpressionStmt mallocCheckCall = Factory.callStmt("mallocCheck", Factory.customExpr(name), nameStringExpr);
    this.entryPoint.addStatement(mallocCheckCall);
  }

  /**
   * Gets the simpleC expression for the cardinality of the given domain.
   */
  public static ParenthesizedExpr getCardinalityExpr(final ISLSet domain) {
    final ISLPWQPolynomial cardinalityPolynomial = BarvinokBindings.card(domain);
    return PolynomialConverter.convert(cardinalityPolynomial);
  }

  protected Boolean involvesFractalReduction(final Variable variable) {
    Boolean _xblockexpression = null;
    {
      final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
        Variable _variable = eq.getVariable();
        return Boolean.valueOf(Objects.equal(_variable, variable));
      };
      final StandardEquation equation = IterableExtensions.<StandardEquation>findFirst(this.systemBody.getStandardEquations(), _function);
      if ((equation == null)) {
        return Boolean.valueOf(false);
      }
      final AlphaExpression expr = equation.getExpr();
      if ((!(expr instanceof ReduceExpression))) {
        return Boolean.valueOf(false);
      }
      final ReduceExpression re = ((ReduceExpression) expr);
      _xblockexpression = re.getFractalSimplify();
    }
    return _xblockexpression;
  }

  /**
   * Generates the loops of the entry point that evaluate all points of each output.
   */
  @Override
  public void performEvaluations() {
    final Function1<Variable, Boolean> _function = (Variable it) -> {
      return this.involvesFractalReduction(it);
    };
    final Iterable<Variable> fractalOutputs = IterableExtensions.<Variable>filter(this.systemBody.getSystem().getOutputs(), _function);
    final Function1<Variable, Boolean> _function_1 = (Variable it) -> {
      return this.involvesFractalReduction(it);
    };
    final Iterable<Variable> nonFractalOutputs = IterableExtensions.<Variable>reject(this.systemBody.getSystem().getOutputs(), _function_1);
    final Consumer<Variable> _function_2 = (Variable it) -> {
      this.evaluateFractalPoints(it);
    };
    fractalOutputs.forEach(_function_2);
    final Consumer<Variable> _function_3 = (Variable it) -> {
      this.evaluateAllPoints(it);
    };
    nonFractalOutputs.forEach(_function_3);
    this.entryPoint.addEmptyLine();
  }

  protected FunctionBuilder evaluateFractalPoints(final Variable variable) {
    try {
      FunctionBuilder _xblockexpression = null;
      {
        this.entryPoint.addComment("Evaluate fractal reduction outputs");
        final String fractalReduceName = this.getFractalReduceFunctionName(variable);
        final Function1<StandardEquation, Boolean> _function = (StandardEquation eq) -> {
          Variable _variable = eq.getVariable();
          return Boolean.valueOf(Objects.equal(_variable, variable));
        };
        final StandardEquation eq = IterableExtensions.<StandardEquation>findFirst(this.systemBody.getStandardEquations(), _function);
        final List<VariableExpression> varExprs = EcoreUtil2.<VariableExpression>getAllContentsOfType(eq.getExpr(), VariableExpression.class);
        int _size = varExprs.size();
        boolean _notEquals = (_size != 1);
        if (_notEquals) {
          throw new Exception("This implementation only supports a single variable expression in the reduction body");
        }
        final String rhsVar = varExprs.get(0).getVariable().getName();
        final List<String> params = this.systemBody.getSystem().getParameterDomain().getParamNames();
        final String lhsVar = variable.getName();
        String _name = variable.getName();
        final String lhsVarCopy = (_name + "_copy");
        Iterable<String> _plus = Iterables.<String>concat(params, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(lhsVar, lhsVarCopy, rhsVar)));
        final CallExpr callFractalReduce = Factory.callExpr(fractalReduceName, ((String[])Conversions.unwrapArray(_plus, String.class)));
        final ExpressionStmt exprStmt = Factory.expressionStmt(callFractalReduce);
        _xblockexpression = this.entryPoint.addStatement(exprStmt);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  protected String getFractalReduceFunctionName(final Variable variable) {
    String _name = variable.getName();
    return ("freduce_" + _name);
  }

  /**
   * Evaluates all the points within an output variable.
   */
  protected FunctionBuilder evaluateAllPoints(final Variable variable) {
    FunctionBuilder _xblockexpression = null;
    {
      this.entryPoint.addComment("Evaluate all non fractal reduction outputs.");
      String macroName = null;
      do {
        {
          macroName = ("S" + Integer.valueOf(this.nextStatementId));
          int _nextStatementId = this.nextStatementId;
          this.nextStatementId = (_nextStatementId + 1);
        }
      } while(this.nameChecker.isGlobalOrKeyword(macroName));
      final String evalName = this.nameChecker.getVariableReadName(variable);
      final CallExpr callEval = Factory.callExpr(evalName, ((String[])Conversions.unwrapArray(variable.getDomain().getIndexNames(), String.class)));
      final MacroStmt macro = Factory.macroStmt(macroName, ((String[])Conversions.unwrapArray(variable.getDomain().getIndexNames(), String.class)), callEval);
      this.entryPoint.addStatement(macro);
      final ISLASTNode islAST = LoopGenerator.generateLoops(macroName, variable.getDomain());
      final ASTConversionResult loopResult = ASTConverter.convert(islAST);
      final Function1<String, VariableDecl> _function = (String it) -> {
        return Factory.variableDecl(this.typeGenerator.getIndexType(), it);
      };
      final ArrayList<VariableDecl> loopVariables = CommonExtensions.<VariableDecl>toArrayList(ListExtensions.<String, VariableDecl>map(loopResult.getDeclarations(), _function));
      this.entryPoint.addVariable(((VariableDecl[])Conversions.unwrapArray(loopVariables, VariableDecl.class))).addStatement(((Statement[])Conversions.unwrapArray(loopResult.getStatements(), Statement.class)));
      _xblockexpression = this.entryPoint.addStatement(Factory.undefStmt(macroName));
    }
    return _xblockexpression;
  }

  @Override
  public void addEntryPoint() {
    final AlphaSystem system = this.systemBody.getSystem();
    final List<String> parameters = system.getParameterDomain().getParamNames();
    final Consumer<String> _function = (String it) -> {
      this.declareSystemParameterArgument(it);
    };
    parameters.forEach(_function);
    final Consumer<Variable> _function_1 = (Variable it) -> {
      this.declareSystemVariableArgument(it);
    };
    system.getInputs().forEach(_function_1);
    final Consumer<Variable> _function_2 = (Variable it) -> {
      this.declareSystemVariableArgument(it);
    };
    system.getOutputs().forEach(_function_2);
    this.entryPoint.addComment("Copy arguments to the global variables.");
    final Consumer<String> _function_3 = (String it) -> {
      this.copySystemParameter(it);
    };
    parameters.forEach(_function_3);
    final Consumer<Variable> _function_4 = (Variable it) -> {
      this.copySystemVariable(it);
    };
    system.getInputs().forEach(_function_4);
    final Consumer<Variable> _function_5 = (Variable it) -> {
      this.copySystemVariable(it);
    };
    system.getOutputs().forEach(_function_5);
    this.entryPoint.addEmptyLine();
    this.checkParameters();
    this.entryPoint.addComment("Allocate memory for local storage.");
    final Consumer<Variable> _function_6 = (Variable it) -> {
      this.allocateVariable(it);
    };
    system.getLocals().forEach(_function_6);
    final Function1<Variable, Boolean> _function_7 = (Variable it) -> {
      return this.involvesFractalReduction(it);
    };
    final Function1<Variable, Variable> _function_8 = (Variable it) -> {
      return AlphaUtil.<Variable>copyAE(it);
    };
    final Function1<Variable, Variable> _function_9 = (Variable v) -> {
      Variable _xblockexpression = null;
      {
        String _name = v.getName();
        String _plus = (_name + "_copy");
        v.setName(_plus);
        _xblockexpression = v;
      }
      return _xblockexpression;
    };
    final Consumer<Variable> _function_10 = (Variable it) -> {
      this.allocateVariable(it);
    };
    IterableExtensions.<Variable, Variable>map(IterableExtensions.<Variable, Variable>map(IterableExtensions.<Variable>filter(this.systemBody.getSystem().getVariables(), _function_7), _function_8), _function_9).forEach(_function_10);
    this.entryPoint.addEmptyLine();
    this.entryPoint.addComment("Allocate and initialize flag variables.");
    if (this.cycleDetection) {
      final Consumer<Variable> _function_11 = (Variable it) -> {
        this.allocateFlagsVariable(it);
      };
      system.getOutputs().forEach(_function_11);
      final Consumer<Variable> _function_12 = (Variable it) -> {
        this.allocateFlagsVariable(it);
      };
      system.getLocals().forEach(_function_12);
    }
    this.entryPoint.addEmptyLine();
    this.performEvaluations();
    this.freeAllocatedVariables();
    this.program.addFunction(this.entryPoint.getInstance());
  }

  @Override
  public Program convertSystemBody() throws UnsupportedOperationException {
    this.preprocess();
    this.addDefaultHeaderComment();
    this.addDefaultIncludes();
    this.addDefaultFunctionMacros();
    final AlphaSystem system = this.systemBody.getSystem();
    final List<String> parameters = system.getParameterDomain().getParamNames();
    final Function1<Variable, Boolean> _function = (Variable it) -> {
      return this.involvesFractalReduction(it);
    };
    final Function1<Variable, Variable> _function_1 = (Variable it) -> {
      return AlphaUtil.<Variable>copyAE(it);
    };
    final Function1<Variable, Variable> _function_2 = (Variable v) -> {
      Variable _xblockexpression = null;
      {
        String _name = v.getName();
        String _plus = (_name + "_copy");
        v.setName(_plus);
        _xblockexpression = v;
      }
      return _xblockexpression;
    };
    final Iterable<Variable> fractalVars = IterableExtensions.<Variable, Variable>map(IterableExtensions.<Variable, Variable>map(IterableExtensions.<Variable>filter(system.getVariables(), _function), _function_1), _function_2);
    final Consumer<String> _function_3 = (String it) -> {
      this.declareParameter(it);
    };
    parameters.forEach(_function_3);
    EList<Variable> _variables = system.getVariables();
    final Consumer<Variable> _function_4 = (Variable it) -> {
      this.declareGlobalVariable(it);
    };
    Iterables.<Variable>concat(_variables, fractalVars).forEach(_function_4);
    EList<Variable> _variables_1 = system.getVariables();
    final Consumer<Variable> _function_5 = (Variable it) -> {
      this.declareMemoryMacro(it);
    };
    Iterables.<Variable>concat(_variables_1, fractalVars).forEach(_function_5);
    if (this.cycleDetection) {
      final Consumer<Variable> _function_6 = (Variable it) -> {
        this.declareFlagVariable(it);
      };
      system.getOutputs().forEach(_function_6);
      final Consumer<Variable> _function_7 = (Variable it) -> {
        this.declareFlagMemoryMacro(it);
      };
      system.getOutputs().forEach(_function_7);
      final Consumer<Variable> _function_8 = (Variable it) -> {
        this.declareFlagVariable(it);
      };
      system.getLocals().forEach(_function_8);
      final Consumer<Variable> _function_9 = (Variable it) -> {
        this.declareFlagMemoryMacro(it);
      };
      system.getLocals().forEach(_function_9);
    }
    final Consumer<StandardEquation> _function_10 = (StandardEquation it) -> {
      this.declareEvaluation(it);
    };
    this.systemBody.getStandardEquations().forEach(_function_10);
    final Consumer<UseEquation> _function_11 = (UseEquation it) -> {
      this.declareEvaluation(it);
    };
    this.systemBody.getUseEquations().forEach(_function_11);
    this.addEntryPoint();
    return this.program.getInstance();
  }
}
