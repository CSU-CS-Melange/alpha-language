package alpha.codegen.scheduledC;

import alpha.codegen.ArrayAccessExpr;
import alpha.codegen.AssignmentStmt;
import alpha.codegen.BaseDataType;
import alpha.codegen.BinaryExpr;
import alpha.codegen.BinaryOperator;
import alpha.codegen.CallExpr;
import alpha.codegen.CastExpr;
import alpha.codegen.CodegenOptions;
import alpha.codegen.CustomExpr;
import alpha.codegen.DataType;
import alpha.codegen.Expression;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.Factory;
import alpha.codegen.FunctionBuilder;
import alpha.codegen.LoopStmt;
import alpha.codegen.MacroStmt;
import alpha.codegen.ParenthesizedExpr;
import alpha.codegen.Program;
import alpha.codegen.Statement;
import alpha.codegen.VariableDecl;
import alpha.codegen.alphaBase.AlphaBaseHelpers;
import alpha.codegen.alphaBase.AlphaNameChecker;
import alpha.codegen.alphaBase.CodeGeneratorBase;
import alpha.codegen.isl.ASTConversionResult;
import alpha.codegen.isl.ASTConverter;
import alpha.codegen.isl.AffineConverter;
import alpha.codegen.isl.LoopGenerator;
import alpha.codegen.isl.MemoryUtils;
import alpha.codegen.isl.PolynomialConverter;
import alpha.codegen.postprocessing.ForLoopNester;
import alpha.codegen.postprocessing.OmpPragmaInserter;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.memorymapper.MemoryMapper;
import alpha.model.scheduler.Scheduler;
import alpha.model.tiler.Tiler;
import alpha.model.transformation.ChangeOfBasis;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.StandardizeNames;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.IISLSingleSpaceMapMethods;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class ScheduledC extends CodeGeneratorBase {
  /**
   * The next ID to use as a statement macro.
   */
  protected int nextStatementId = 0;

  /**
   * Converts Alpha expressions to simpleC expressions.
   */
  protected final ScheduledExprConverter exprConverter;

  /**
   * An object that returns the schedule of the outputted C code
   */
  protected final Scheduler scheduler;

  /**
   * An object that contains memory maps to apply to each variable
   */
  protected final MemoryMapper mapper;

  /**
   * An optional object that contains a tile map
   */
  protected final Tiler tiler;

  /**
   * The variable assignemnt statements that have been generated, for use in macro generation
   */
  protected Map<String, AssignmentStmt> variableStatements;

  public ScheduledC(final SystemBody systemBody, final ScheduledTypeGenerator typeGen, final AlphaNameChecker nameChecker, final Scheduler scheduler, final CodegenOptions options) {
    super(systemBody, nameChecker, typeGen, options);
    this.tiler = options.getTiler();
    this.mapper = options.getMapper();
    this.scheduler = scheduler;
    ScheduledExprConverter _scheduledExprConverter = new ScheduledExprConverter(typeGen, nameChecker, this.program, scheduler, options);
    this.exprConverter = _scheduledExprConverter;
    HashMap<String, AssignmentStmt> _hashMap = new HashMap<String, AssignmentStmt>();
    this.variableStatements = _hashMap;
  }

  /**
   * Normalizes the system body and standardizes all names prior to conversion.
   * Overide the preprocess step to not normalize reductions
   */
  @Override
  public void preprocess() {
    Normalize.apply(this.systemBody);
    StandardizeNames.apply(this.systemBody);
  }

  /**
   * Constructs an equality constraint that index i equals the parameter for that index.
   */
  private static ISLSet addTotalOrderEquality(final ISLSet domain, final int originalParamCount, final int index) {
    final ISLConstraint constraint = ISLConstraint.buildEquality(domain.getSpace()).setCoefficient(ISLDimType.isl_dim_param, (originalParamCount + index), 1).setCoefficient(ISLDimType.isl_dim_set, index, (-1));
    return domain.addConstraint(constraint);
  }

  /**
   * Constructs an inequality that index i is less than the parameter for that index.
   */
  private static ISLSet addTotalOrderInequality(final ISLSet domain, final int originalParamCount, final int index) {
    final ISLConstraint constraint = ISLConstraint.buildInequality(domain.getSpace()).setCoefficient(ISLDimType.isl_dim_param, (originalParamCount + index), 1).setCoefficient(ISLDimType.isl_dim_set, index, (-1)).setConstant((-1));
    return domain.addConstraint(constraint);
  }

  public static ISLSet createOrderingForIndex(final ISLSet domain, final ISLMap map, final int originalParamCount, final int index, final String name) {
    final Function2<ISLSet, Integer, ISLSet> _function = (ISLSet d, Integer i) -> {
      return ScheduledC.addTotalOrderEquality(d, originalParamCount, (i).intValue());
    };
    return ScheduledC.addTotalOrderInequality(IterableExtensions.<Integer, ISLSet>fold(new ExclusiveRange(0, index, true), domain.copy(), _function), originalParamCount, index);
  }

  @Override
  public void declareMemoryMacro(final Variable variable) {
    final String name = this.nameChecker.getVariableStorageName(variable);
    final String memoryName = ("mem_" + name);
    ISLMap memoryMap = this.mapper.getMemoryMap(variable);
    String _elvis = null;
    String _destination = this.mapper.getDestination(variable);
    if (_destination != null) {
      _elvis = _destination;
    } else {
      String _variableStorageName = this.nameChecker.getVariableStorageName(variable);
      _elvis = _variableStorageName;
    }
    String storageName = _elvis;
    ISLSet domain = variable.getDomain().copy();
    final List<String> names = domain.getIndexNames();
    int _dim = memoryMap.dim(ISLDimType.isl_dim_out);
    int _minus = (_dim - 1);
    final Function2<ISLMap, Integer, ISLMap> _function = (ISLMap map, Integer i) -> {
      return map.setDimName(ISLDimType.isl_dim_out, (i).intValue(), ("i" + i));
    };
    memoryMap = IterableExtensions.<Integer, ISLMap>fold(new IntegerRange(0, _minus), memoryMap, _function);
    final ISLMap mappedDomain = memoryMap.copy().intersectDomain(domain.copy()).<IISLSingleSpaceMapMethods>renameInputs(names).<ISLMap>setTupleName(ISLDimType.isl_dim_in, memoryName);
    final ISLSet memoryDomain = domain.copy().apply(memoryMap.copy());
    ISLPWQPolynomial _xifexpression = null;
    boolean _polyhedralMemory = this.options.getPolyhedralMemory();
    if (_polyhedralMemory) {
      _xifexpression = MemoryUtils.rank(memoryDomain);
    } else {
      _xifexpression = MemoryUtils.boxRank(memoryDomain);
    }
    final ISLPWQPolynomial rank = _xifexpression;
    final ParenthesizedExpr accessExpression = PolynomialConverter.convert(rank);
    final ArrayAccessExpr macroReplacement = Factory.arrayAccessExpr(storageName, accessExpression);
    final MacroStmt macroStmt = Factory.macroStmt(memoryName, ((String[])Conversions.unwrapArray(memoryDomain.getIndexNames(), String.class)), macroReplacement);
    final ArrayList<CustomExpr> indexExprs = AffineConverter.convertMultiAff(ISLUtil.toMultiAff(mappedDomain));
    final CallExpr statement = Factory.callExpr(memoryName, ((Expression[])Conversions.unwrapArray(indexExprs, Expression.class)));
    final MacroStmt mappedMacroStatement = Factory.macroStmt(name, ((String[])Conversions.unwrapArray(domain.getIndexNames(), String.class)), statement);
    this.program.addMemoryMacro(macroStmt);
    this.program.addMemoryMacro(mappedMacroStatement);
  }

  @Override
  public void declareReductionMemoryMacro(final ReduceExpression re) {
    Equation _containerEquation = AlphaUtil.getContainerEquation(re);
    final Variable variable = ((StandardEquation) _containerEquation).getVariable();
    final String name = AlphaUtil.getReductionName(re);
    final String memoryName = ("mem_" + name);
    ISLMap memoryMap = re.getProjectionExpr().getISLMultiAff().toMap().applyRange(this.mapper.getMemoryMap(variable));
    String storageName = name;
    ISLSet domain = re.getBody().getContextDomain().copy();
    final List<String> names = re.getBody().getContextDomain().getIndexNames();
    int _dim = memoryMap.dim(ISLDimType.isl_dim_out);
    int _minus = (_dim - 1);
    final Function2<ISLMap, Integer, ISLMap> _function = (ISLMap map, Integer i) -> {
      return map.setDimName(ISLDimType.isl_dim_out, (i).intValue(), ("i" + i));
    };
    memoryMap = IterableExtensions.<Integer, ISLMap>fold(new IntegerRange(0, _minus), memoryMap, _function);
    final ISLMap mappedDomain = memoryMap.copy().intersectDomain(domain.copy()).<IISLSingleSpaceMapMethods>renameInputs(names).<ISLMap>setTupleName(ISLDimType.isl_dim_in, memoryName);
    final ISLSet memoryDomain = domain.copy().apply(memoryMap.copy());
    ISLPWQPolynomial _xifexpression = null;
    boolean _polyhedralMemory = this.options.getPolyhedralMemory();
    if (_polyhedralMemory) {
      _xifexpression = MemoryUtils.rank(memoryDomain);
    } else {
      _xifexpression = MemoryUtils.boxRank(memoryDomain);
    }
    final ISLPWQPolynomial rank = _xifexpression;
    final ParenthesizedExpr accessExpression = PolynomialConverter.convert(rank);
    final ArrayAccessExpr macroReplacement = Factory.arrayAccessExpr(storageName, accessExpression);
    final MacroStmt macroStmt = Factory.macroStmt(memoryName, ((String[])Conversions.unwrapArray(memoryDomain.getIndexNames(), String.class)), macroReplacement);
    final ArrayList<CustomExpr> indexExprs = AffineConverter.convertMultiAff(ISLUtil.toMultiAff(mappedDomain));
    final CallExpr statement = Factory.callExpr(memoryName, ((Expression[])Conversions.unwrapArray(indexExprs, Expression.class)));
    final MacroStmt mappedMacroStatement = Factory.macroStmt(name, ((String[])Conversions.unwrapArray(domain.getIndexNames(), String.class)), statement);
    this.program.addMemoryMacro(macroStmt);
    this.program.addMemoryMacro(mappedMacroStatement);
  }

  @Override
  public void declareFlagMemoryMacro(final Variable variable) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public void declareEvaluation(final StandardEquation equation) {
    final DataType returnType = Factory.dataType(BaseDataType.VOID);
    final String evalName = this.nameChecker.getVariableReadName(equation.getVariable());
    final List<String> indexNames = equation.getExpr().getContextDomain().getIndexNames();
    this.exprConverter.setTarget(equation.getName());
    final Expression computeValue = this.exprConverter.convertExpr(equation.getExpr());
    final AssignmentStmt computeAndStore = Factory.assignmentStmt(this.identityAccess(equation, false), computeValue);
    this.exprConverter.setTarget("");
    this.declareStatementEvaluation(evalName, returnType, indexNames, computeAndStore);
  }

  @Override
  public void declareReductionEvaluation(final ReduceExpression re) {
    final DataType returnType = Factory.dataType(BaseDataType.VOID);
    final String evalName = AlphaUtil.getReductionName(re);
    final List<String> indexNames = re.getBody().getContextDomain().getIndexNames();
    this.exprConverter.setTarget(evalName);
    final CallExpr memValue = Factory.callExpr(evalName, ((String[])Conversions.unwrapArray(indexNames, String.class)));
    final BinaryOperator op = AlphaBaseHelpers.getOperator(re.getOperator());
    final BinaryExpr computeValue = Factory.binaryExpr(op, memValue, this.exprConverter.convertExpr(re.getBody()));
    final AssignmentStmt computeAndStore = Factory.assignmentStmt(Factory.callExpr(evalName, ((String[])Conversions.unwrapArray(indexNames, String.class))), computeValue);
    this.exprConverter.setTarget("");
    this.declareStatementEvaluation(evalName, returnType, indexNames, computeAndStore);
  }

  protected void declareStatementEvaluation(final String evalName, final DataType returnType, final Iterable<String> indexNames, final AssignmentStmt computeAndStore) {
    final FunctionBuilder evalBuilder = this.program.startFunction(true, this.options.getInlineFunction(), returnType, ("eval_" + evalName));
    final Consumer<String> _function = (String it) -> {
      evalBuilder.addParameter(this.typeGenerator.getIndexType(), it);
    };
    indexNames.forEach(_function);
    evalBuilder.addStatement(computeAndStore);
    boolean _inlineCode = this.options.getInlineCode();
    boolean _not = (!_inlineCode);
    if (_not) {
      this.program.addFunction(evalBuilder.getInstance());
    } else {
      this.variableStatements.put(evalName, computeAndStore);
    }
  }

  /**
   * Gets the expression used to access a variable (or its flag).
   */
  protected CallExpr identityAccess(final StandardEquation equation, final boolean accessFlags) {
    return Factory.callExpr(equation.getVariable().getName(), ((String[])Conversions.unwrapArray(equation.getExpr().getContextDomain().getIndexNames(), String.class)));
  }

  @Override
  public void declareEvaluation(final UseEquation equation) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  /**
   * Allocates memory for a standard variable.
   */
  @Override
  public void allocateVariable(final Variable variable) {
    String _elvis = null;
    String _destination = this.mapper.getDestination(variable);
    if (_destination != null) {
      _elvis = _destination;
    } else {
      String _variableStorageName = this.nameChecker.getVariableStorageName(variable);
      _elvis = _variableStorageName;
    }
    final String name = _elvis;
    final DataType dataType = this.typeGenerator.getAlphaVariableType(variable);
    this.allocatedVariables.add(name);
    final ISLSet mappedDomain = variable.getDomain().apply(this.mapper.getMemoryMap(variable));
    this.allocateMemory(name, dataType, mappedDomain);
  }

  /**
   * Allocates memory for a reduction body.
   */
  @Override
  public void allocateReduction(final ReduceExpression re) {
    Equation _containerEquation = AlphaUtil.getContainerEquation(re);
    final Variable variable = ((StandardEquation) _containerEquation).getVariable();
    final ISLSet domain = re.getBody().getContextDomain().copy();
    final String name = AlphaUtil.getReductionName(re);
    final DataType dataType = this.typeGenerator.getAlphaVariableType(variable);
    final ISLMap memoryMap = re.getProjectionExpr().getISLMultiAff().toMap().applyRange(this.mapper.getMemoryMap(variable));
    final ISLSet mappedDomain = domain.apply(memoryMap);
    this.allocateMemory(name, dataType, mappedDomain);
  }

  /**
   * Helper for allocating reduction and standard variables
   */
  protected FunctionBuilder allocateMemory(final String name, final DataType dataType, final ISLSet mappedDomain) {
    FunctionBuilder _xblockexpression = null;
    {
      this.allocatedVariables.add(name);
      final ParenthesizedExpr cardinalityExpr = this.getCardinalityExpr(mappedDomain);
      final CastExpr mallocCall = Factory.mallocCall(dataType, cardinalityExpr);
      final AssignmentStmt mallocAssignment = Factory.assignmentStmt(name, mallocCall);
      this.entryPoint.addStatement(mallocAssignment);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("\"");
      _builder.append(name);
      _builder.append("\"");
      final CustomExpr nameStringExpr = Factory.customExpr(_builder.toString());
      final ExpressionStmt mallocCheckCall = Factory.callStmt("mallocCheck", Factory.customExpr(name), nameStringExpr);
      _xblockexpression = this.entryPoint.addStatement(mallocCheckCall);
    }
    return _xblockexpression;
  }

  @Override
  public void initializeReduction(final ReduceExpression re) {
    Equation _containerEquation = AlphaUtil.getContainerEquation(re);
    final Variable variable = ((StandardEquation) _containerEquation).getVariable();
    final ISLSet domain = re.getBody().getContextDomain().copy();
    final ISLMap memoryMap = re.getProjectionExpr().getISLMultiAff().toMap().applyRange(this.mapper.getMemoryMap(variable));
    final ParenthesizedExpr cardinalityExpr = this.getCardinalityExpr(domain.apply(memoryMap));
    final BinaryExpr conditional = Factory.binaryExpr(BinaryOperator.LT, Factory.customExpr("i"), cardinalityExpr);
    final ArrayAccessExpr memExpr = Factory.arrayAccessExpr(AlphaUtil.getReductionName(re), "i");
    final AssignmentStmt initializeStmt = Factory.assignmentStmt(memExpr, AlphaBaseHelpers.getReductionInitialValue(this.options.getValueType(), re.getOperator()));
    final LoopStmt loop = Factory.loopStmt("i", Factory.customExpr("0"), conditional, Factory.customExpr("1"), initializeStmt);
    this.entryPoint.addVariable(Factory.variableDecl(this.typeGenerator.getIndexType(), "i"));
    this.entryPoint.addStatement(loop);
  }

  protected ParenthesizedExpr getCardinalityExpr(final ISLSet domain) {
    ISLPWQPolynomial _xifexpression = null;
    boolean _polyhedralMemory = this.options.getPolyhedralMemory();
    if (_polyhedralMemory) {
      _xifexpression = MemoryUtils.card(domain);
    } else {
      _xifexpression = MemoryUtils.boxCard(domain);
    }
    final ISLPWQPolynomial cardinalityPolynomial = _xifexpression;
    return PolynomialConverter.convert(cardinalityPolynomial);
  }

  @Override
  public void allocateFlagsVariable(final Variable variable) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public void performEvaluations() {
    this.entryPoint.addComment("Evaluate all the outputs.");
    EList<Variable> variables = this.systemBody.getSystem().getLocals();
    variables.addAll(this.systemBody.getSystem().getOutputs());
    this.evaluateAllPoints(variables);
    this.entryPoint.addEmptyLine();
  }

  /**
   * Evaluates all the points within an output variable.
   */
  protected FunctionBuilder evaluateAllPoints(final List<Variable> variables) {
    FunctionBuilder _xblockexpression = null;
    {
      final Function1<Variable, String> _function = (Variable it) -> {
        return it.getName();
      };
      Iterable<String> scheduledVars = ListExtensions.<Variable, String>map(variables, _function);
      boolean _scheduledReductions = this.options.getScheduledReductions();
      if (_scheduledReductions) {
        final Function1<ReduceExpression, String> _function_1 = (ReduceExpression it) -> {
          return AlphaUtil.getReductionName(it);
        };
        List<String> _list = IteratorExtensions.<String>toList(IteratorExtensions.<ReduceExpression, String>map(AlphaUtil.getContainedReductions(this.systemBody), _function_1));
        Iterable<String> _plus = Iterables.<String>concat(scheduledVars, _list);
        scheduledVars = _plus;
      }
      final Function1<String, ISLMap> _function_2 = (String it) -> {
        return this.scheduler.getScheduleMap(it);
      };
      ISLUnionMap scheduleMaps = ISLUtil.convertToUnionMap(IterableExtensions.<ISLMap>toList(IterableExtensions.<String, ISLMap>map(scheduledVars, _function_2)));
      scheduleMaps = scheduleMaps.intersectDomain(this.scheduler.getDomains());
      boolean _inlineCode = this.options.getInlineCode();
      if (_inlineCode) {
        final List<ISLMap> maps = scheduleMaps.getMaps();
        final Function1<ISLMap, Boolean> _function_3 = (ISLMap it) -> {
          final Function1<Variable, Boolean> _function_4 = (Variable v) -> {
            String _name = v.getName();
            String _inputTupleName = it.getInputTupleName();
            return Boolean.valueOf(Objects.equal(_name, _inputTupleName));
          };
          return Boolean.valueOf(IterableExtensions.<Variable>exists(variables, _function_4));
        };
        final Function1<ISLMap, MacroStmt> _function_4 = (ISLMap it) -> {
          String _inputTupleName = it.getInputTupleName();
          String _plus_1 = ("eval_" + _inputTupleName);
          final Function1<Variable, Boolean> _function_5 = (Variable v) -> {
            String _name = v.getName();
            String _inputTupleName_1 = it.getInputTupleName();
            return Boolean.valueOf(Objects.equal(_name, _inputTupleName_1));
          };
          return Factory.macroStmt(_plus_1, ((String[])Conversions.unwrapArray(IterableExtensions.<Variable>findFirst(variables, _function_5).getDomain().getIndexNames(), String.class)), this.variableStatements.get(it.getInputTupleName()));
        };
        Iterable<MacroStmt> _map = IterableExtensions.<ISLMap, MacroStmt>map(IterableExtensions.<ISLMap>filter(maps, _function_3), _function_4);
        final Function1<ISLMap, Boolean> _function_5 = (ISLMap it) -> {
          final Function1<Variable, Boolean> _function_6 = (Variable v) -> {
            String _name = v.getName();
            String _inputTupleName = it.getInputTupleName();
            return Boolean.valueOf(Objects.equal(_name, _inputTupleName));
          };
          return Boolean.valueOf(IterableExtensions.<Variable>exists(variables, _function_6));
        };
        final Function1<ISLMap, MacroStmt> _function_6 = (ISLMap it) -> {
          String _inputTupleName = it.getInputTupleName();
          String _plus_1 = ("eval_" + _inputTupleName);
          return Factory.macroStmt(_plus_1, ((String[])Conversions.unwrapArray(AlphaUtil.getReductionByName(this.systemBody, it.getInputTupleName()).getBody().getContextDomain().getIndexNames(), String.class)), this.variableStatements.get(it.getInputTupleName()));
        };
        Iterable<MacroStmt> _map_1 = IterableExtensions.<ISLMap, MacroStmt>map(IterableExtensions.<ISLMap>reject(maps, _function_5), _function_6);
        Iterable<MacroStmt> macros = Iterables.<MacroStmt>concat(_map, _map_1);
        final Consumer<MacroStmt> _function_7 = (MacroStmt it) -> {
          this.entryPoint.addStatement(it);
        };
        macros.forEach(_function_7);
      }
      ISLUnionMap namedScheduleMaps = null;
      final Function1<ISLMap, ISLMap> _function_8 = (ISLMap it) -> {
        return it.simplify();
      };
      final Function1<ISLMap, ISLMap> _function_9 = (ISLMap it) -> {
        String _inputTupleName = it.getInputTupleName();
        String _plus_1 = ("eval_" + _inputTupleName);
        return it.<ISLMap>setInputTupleName(_plus_1);
      };
      namedScheduleMaps = ISLUtil.convertToUnionMap(IterableExtensions.<ISLMap>toList(ListExtensions.<ISLMap, ISLMap>map(ListExtensions.<ISLMap, ISLMap>map(scheduleMaps.getMaps(), _function_8), _function_9)));
      ASTConversionResult loopResult = null;
      if ((this.tiler != null)) {
        final ISLUnionMap tileMaps = this.tiler.getApproximateOutset(this.scheduler.getRanges()).setTupleName("_").toIdentityMap().toUnionMap();
        final ISLASTNode tileAST = LoopGenerator.generateLoops(tileMaps.copy().params(), tileMaps);
        loopResult = ASTConverter.convert(tileAST);
        final ISLUnionMap iterMaps = this.tiler.getParameterizedIterators(namedScheduleMaps);
        final ISLASTNode iterAST = LoopGenerator.generateLoops(iterMaps.copy().params(), iterMaps);
        final ASTConversionResult iterResult = ASTConverter.convert(iterAST);
        ForLoopNester.apply(loopResult, iterResult, "_");
        loopResult.getDeclarations().addAll(iterResult.getDeclarations());
      } else {
        final ISLASTNode islAST = LoopGenerator.generateLoops(this.scheduler.getDomains().params(), namedScheduleMaps);
        loopResult = ASTConverter.convert(islAST);
      }
      boolean _ompPragmas = this.options.getOmpPragmas();
      if (_ompPragmas) {
        final int timeDims = ISLUtil.countTimeDimensions(AlphaUtil.getContainerSystem(this.systemBody), this.scheduler.getMaps());
        OmpPragmaInserter.apply(loopResult, timeDims);
      }
      final Function1<String, VariableDecl> _function_10 = (String it) -> {
        return Factory.variableDecl(this.typeGenerator.getIndexType(), it);
      };
      final ArrayList<VariableDecl> loopVariables = CommonExtensions.<VariableDecl>toArrayList(ListExtensions.<String, VariableDecl>map(loopResult.getDeclarations(), _function_10));
      _xblockexpression = this.entryPoint.addVariable(((VariableDecl[])Conversions.unwrapArray(loopVariables, VariableDecl.class))).addStatement(((Statement[])Conversions.unwrapArray(loopResult.getStatements(), Statement.class)));
    }
    return _xblockexpression;
  }

  public static Program convert(final AlphaSystem system, final Scheduler scheduler, final CodegenOptions options) {
    int _length = ((Object[])Conversions.unwrapArray(system.getSystemBodies(), Object.class)).length;
    boolean _notEquals = (_length != 1);
    if (_notEquals) {
      throw new IllegalArgumentException("Systems must have exactly one body to be converted directly to WriteC code.");
    }
    AlphaRoot alteredRoot = AlphaUtil.<AlphaRoot>copyAE(AlphaUtil.getContainerRoot(system));
    AlphaSystem alteredSystem = alteredRoot.getSystem(system.getName());
    Normalize.apply(alteredSystem);
    EList<Variable> _locals = alteredSystem.getLocals();
    for (final Variable local : _locals) {
      List<ISLMap> _maps = scheduler.getMaps().getMaps();
      for (final ISLMap map : _maps) {
        String _tupleName = map.getTupleName(ISLDimType.isl_dim_out);
        String _name = local.getName();
        boolean _equals = Objects.equal(_tupleName, _name);
        if (_equals) {
          ChangeOfBasis.apply(alteredSystem, local, ISLUtil.toMultiAff(map));
        }
      }
    }
    EList<Variable> _inputs = alteredSystem.getInputs();
    for (final Variable input : _inputs) {
      List<ISLMap> _maps_1 = scheduler.getMaps().getMaps();
      for (final ISLMap map_1 : _maps_1) {
        String _tupleName_1 = map_1.getTupleName(ISLDimType.isl_dim_in);
        String _name_1 = input.getName();
        boolean _equals_1 = Objects.equal(_tupleName_1, _name_1);
        if (_equals_1) {
          ChangeOfBasis.apply(alteredSystem, input, ISLUtil.toMultiAff(map_1));
        }
      }
    }
    SystemBody _get = alteredSystem.getSystemBodies().get(0);
    BaseDataType _valueType = options.getValueType();
    ScheduledTypeGenerator _scheduledTypeGenerator = new ScheduledTypeGenerator(_valueType, false);
    AlphaNameChecker _alphaNameChecker = new AlphaNameChecker(false);
    return new ScheduledC(_get, _scheduledTypeGenerator, _alphaNameChecker, scheduler, options).convertSystemBody();
  }
}
