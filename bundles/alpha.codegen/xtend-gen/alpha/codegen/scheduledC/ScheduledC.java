package alpha.codegen.scheduledC;

import alpha.codegen.ArrayAccessExpr;
import alpha.codegen.AssignmentStmt;
import alpha.codegen.BaseDataType;
import alpha.codegen.CallExpr;
import alpha.codegen.CastExpr;
import alpha.codegen.CustomExpr;
import alpha.codegen.DataType;
import alpha.codegen.Expression;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.Factory;
import alpha.codegen.FunctionBuilder;
import alpha.codegen.MacroStmt;
import alpha.codegen.ParenthesizedExpr;
import alpha.codegen.Program;
import alpha.codegen.Statement;
import alpha.codegen.VariableDecl;
import alpha.codegen.alphaBase.AlphaNameChecker;
import alpha.codegen.alphaBase.CodeGeneratorBase;
import alpha.codegen.isl.ASTConversionResult;
import alpha.codegen.isl.ASTConverter;
import alpha.codegen.isl.AffineConverter;
import alpha.codegen.isl.LoopGenerator;
import alpha.codegen.isl.MemoryUtils;
import alpha.codegen.isl.PolynomialConverter;
import alpha.model.AlphaSystem;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.memorymapper.MemoryMapper;
import alpha.model.scheduler.Scheduler;
import alpha.model.transformation.ChangeOfBasis;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.StandardizeNames;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings;
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
import org.eclipse.xtext.xbase.lib.IterableExtensions;
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

  protected final MemoryMapper mapper;

  /**
   * Tells the code generator to add the inline keyword to the evaluate function
   */
  protected final boolean inlineFunction;

  /**
   * Tells the code generator to manually inline the function (replace the function call with the actual function body
   */
  protected final boolean inlineCode;

  protected Map<String, AssignmentStmt> variableStatements;

  public ScheduledC(final SystemBody systemBody, final AlphaNameChecker nameChecker, final ScheduledTypeGenerator typeGenerator, final Scheduler scheduler, final MemoryMapper mapper, final boolean cycleDetection, final boolean inlineFunction, final boolean inlineCode) {
    super(systemBody, nameChecker, typeGenerator, cycleDetection);
    ScheduledExprConverter _scheduledExprConverter = new ScheduledExprConverter(typeGenerator, nameChecker, this.program, scheduler, mapper);
    this.exprConverter = _scheduledExprConverter;
    this.scheduler = scheduler;
    this.inlineFunction = inlineFunction;
    this.inlineCode = inlineCode;
    HashMap<String, AssignmentStmt> _hashMap = new HashMap<String, AssignmentStmt>();
    this.variableStatements = _hashMap;
    this.mapper = mapper;
  }

  /**
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

  public static ISLPWQPolynomial card(final ISLSet domain) {
    return BarvinokBindings.card(domain.copy());
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
    final ISLMap mappedDomain = memoryMap.copy().intersectDomain(domain.copy()).<IISLSingleSpaceMapMethods>renameInputs(names).<ISLMap>setTupleName(ISLDimType.isl_dim_in, memoryName);
    final ISLPWQPolynomial rank = MemoryUtils.rank(domain);
    final ParenthesizedExpr accessExpression = PolynomialConverter.convert(rank);
    final ArrayAccessExpr macroReplacement = Factory.arrayAccessExpr(storageName, accessExpression);
    final MacroStmt macroStmt = Factory.macroStmt(memoryName, ((String[])Conversions.unwrapArray(domain.getIndexNames(), String.class)), macroReplacement);
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
    final FunctionBuilder evalBuilder = this.program.startFunction(true, this.inlineFunction, returnType, ("eval_" + evalName));
    final List<String> indexNames = equation.getExpr().getContextDomain().getIndexNames();
    final Consumer<String> _function = (String it) -> {
      evalBuilder.addParameter(this.typeGenerator.getIndexType(), it);
    };
    indexNames.forEach(_function);
    this.exprConverter.setTarget(equation.getName());
    final Expression computeValue = this.exprConverter.convertExpr(equation.getExpr());
    final AssignmentStmt computeAndStore = Factory.assignmentStmt(this.identityAccess(equation, false), computeValue);
    this.exprConverter.setTarget("");
    evalBuilder.addStatement(computeAndStore);
    if ((!this.inlineCode)) {
      this.program.addFunction(evalBuilder.getInstance());
    } else {
      this.variableStatements.put(equation.getName(), computeAndStore);
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
    final ParenthesizedExpr cardinalityExpr = this.getCardinalityExpr(variable.getDomain().apply(this.mapper.getMemoryMap(variable)));
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

  protected ParenthesizedExpr getCardinalityExpr(final ISLSet domain) {
    final ISLPWQPolynomial cardinalityPolynomial = BarvinokBindings.card(domain);
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
      ISLUnionMap scheduleMaps = null;
      List<ISLMap> _maps = this.scheduler.getMaps().getMaps();
      for (final ISLMap map : _maps) {
        for (final Variable variable : variables) {
          {
            String name = map.copy().getInputTupleName();
            String _name = variable.getName();
            boolean _equals = Objects.equal(name, _name);
            if (_equals) {
              if ((scheduleMaps == null)) {
                scheduleMaps = map.copy().toUnionMap();
              } else {
                scheduleMaps = scheduleMaps.copy().addMap(map.copy());
              }
            }
          }
        }
      }
      scheduleMaps = scheduleMaps.intersectDomain(this.scheduler.getDomains());
      ISLUnionMap namedScheduleMaps = null;
      List<ISLMap> _maps_1 = scheduleMaps.getMaps();
      for (final ISLMap map_1 : _maps_1) {
        {
          final String name = map_1.copy().getInputTupleName();
          ISLMap newMap = map_1.copy();
          if (this.inlineCode) {
            String macroName = null;
            do {
              {
                macroName = ("S" + Integer.valueOf(this.nextStatementId));
                int _nextStatementId = this.nextStatementId;
                this.nextStatementId = (_nextStatementId + 1);
              }
            } while(this.nameChecker.isGlobalOrKeyword(macroName));
            final Function1<Variable, Boolean> _function = (Variable x) -> {
              String _name = x.getName();
              return Boolean.valueOf(Objects.equal(_name, name));
            };
            final Variable variable_1 = IterableExtensions.<Variable>head(IterableExtensions.<Variable>filter(variables, _function));
            final MacroStmt macro = Factory.macroStmt(macroName, ((String[])Conversions.unwrapArray(variable_1.getDomain().getIndexNames(), String.class)), this.variableStatements.get(name));
            this.entryPoint.addStatement(macro);
            newMap = newMap.<ISLMap>setInputTupleName(macroName);
          } else {
            newMap = newMap.<ISLMap>setInputTupleName(("eval_" + name));
          }
          if ((namedScheduleMaps == null)) {
            namedScheduleMaps = newMap.copy().toUnionMap();
          } else {
            namedScheduleMaps = namedScheduleMaps.addMap(newMap);
          }
        }
      }
      final ISLASTNode islAST = LoopGenerator.generateLoops(this.scheduler.getDomains().params(), namedScheduleMaps);
      final ASTConversionResult loopResult = ASTConverter.convert(islAST);
      final Function1<String, VariableDecl> _function = (String it) -> {
        return Factory.variableDecl(this.typeGenerator.getIndexType(), it);
      };
      final ArrayList<VariableDecl> loopVariables = CommonExtensions.<VariableDecl>toArrayList(ListExtensions.<String, VariableDecl>map(loopResult.getDeclarations(), _function));
      _xblockexpression = this.entryPoint.addVariable(((VariableDecl[])Conversions.unwrapArray(loopVariables, VariableDecl.class))).addStatement(((Statement[])Conversions.unwrapArray(loopResult.getStatements(), Statement.class)));
    }
    return _xblockexpression;
  }

  public static Program convert(final AlphaSystem system, final BaseDataType valueType, final Scheduler scheduler, final MemoryMapper mapper, final boolean normalize, final boolean inlineFunction, final boolean inlineCode) {
    int _length = ((Object[])Conversions.unwrapArray(system.getSystemBodies(), Object.class)).length;
    boolean _notEquals = (_length != 1);
    if (_notEquals) {
      throw new IllegalArgumentException("Systems must have exactly one body to be converted directly to WriteC code.");
    }
    AlphaSystem alteredSystem = AlphaUtil.<AlphaSystem>copyAE(system);
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
    AlphaNameChecker _alphaNameChecker = new AlphaNameChecker(false);
    ScheduledTypeGenerator _scheduledTypeGenerator = new ScheduledTypeGenerator(valueType, false);
    return new ScheduledC(_get, _alphaNameChecker, _scheduledTypeGenerator, scheduler, mapper, false, inlineFunction, inlineCode).convertSystemBody();
  }
}
