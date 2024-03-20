package alpha.codegen.constructor;

import alpha.codegen.ArrayVariable;
import alpha.codegen.BaseVariable;
import alpha.codegen.DataType;
import alpha.codegen.EvalFunction;
import alpha.codegen.Function;
import alpha.codegen.FunctionBody;
import alpha.codegen.GlobalVariable;
import alpha.codegen.MemoryMacro;
import alpha.codegen.Program;
import alpha.codegen.ReduceFunction;
import alpha.codegen.StatementMacro;
import alpha.codegen.factory.Factory;
import alpha.codegen.util.CodegenUtil;
import alpha.model.AlphaSystem;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.util.AlphaUtil;
import alpha.targetmapping.TargetMapping;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLASTBuild;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.MapExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class WriteCProgram extends BaseProgram {
  protected Map<Variable, ArrayVariable> flagCVs;

  private int reductionNumber = 0;

  public static Program build(final AlphaSystem system, final TargetMapping tm) {
    try {
      Program _xblockexpression = null;
      {
        int _size = system.getSystemBodies().size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
          throw new Exception("No support yet for programs with multiple system bodies");
        }
        final WriteCProgram visitor = new WriteCProgram(tm);
        system.accept(visitor);
        _xblockexpression = visitor.program;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public WriteCProgram(final TargetMapping tm) {
    super();
    this.flagCVs = CollectionLiterals.<Variable, ArrayVariable>newHashMap();
    this.program = Factory.createProgram();
    this.tm = tm;
  }

  public ISLUnionSet outputStatementsDomain(final AlphaSystem s) {
    final Function1<Variable, ISLUnionSet> _function = (Variable it) -> {
      ISLSet _domain = it.getDomain();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("S");
      int _indexOf = s.getOutputs().indexOf(it);
      _builder.append(_indexOf);
      return _domain.setTupleName(_builder.toString()).toUnionSet();
    };
    final Function2<ISLUnionSet, ISLUnionSet, ISLUnionSet> _function_1 = (ISLUnionSet d0, ISLUnionSet d1) -> {
      return d0.copy().union(d1.copy());
    };
    return IterableExtensions.<ISLUnionSet>reduce(ListExtensions.<Variable, ISLUnionSet>map(s.getOutputs(), _function), _function_1);
  }

  @Override
  public void inAlphaSystem(final AlphaSystem s) {
    super.inAlphaSystem(s);
    EList<Variable> _locals = s.getLocals();
    EList<Variable> _outputs = s.getOutputs();
    final Consumer<Variable> _function = (Variable v) -> {
      this.flagCVs.put(v, Factory.createArrayVariableForFlag(v));
    };
    Iterables.<Variable>concat(_locals, _outputs).forEach(_function);
    final Function1<Variable, StatementMacro> _function_1 = (Variable it) -> {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("S");
      int _indexOf = s.getOutputs().indexOf(it);
      _builder.append(_indexOf);
      _builder.append("(");
      String _join = IterableExtensions.join(AlphaUtil.indices(it), ",");
      _builder.append(_join);
      _builder.append(")");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("eval_");
      String _name = it.getName();
      _builder_1.append(_name);
      _builder_1.append("(");
      List<String> _paramNames = it.getDomain().getParamNames();
      List<String> _indexNames = it.getDomain().getIndexNames();
      String _join_1 = IterableExtensions.join(Iterables.<String>concat(_paramNames, _indexNames), ", ");
      _builder_1.append(_join_1);
      _builder_1.append(")");
      return CodegenUtil.statementMacro(_builder.toString(), _builder_1.toString());
    };
    final List<StatementMacro> statementMacros = ListExtensions.<Variable, StatementMacro>map(s.getOutputs(), _function_1);
    final ISLSchedule schedule = ISLSchedule.buildFromDomain(this.outputStatementsDomain(s));
    final ISLASTBuild build = ISLASTBuild.buildFromContext(schedule.getDomain().copy().params());
    final ISLASTNode node = build.generate(schedule.copy());
    final FunctionBody body = Factory.createFunctionBody(((StatementMacro[])Conversions.unwrapArray(statementMacros, StatementMacro.class)), node);
    final List<BaseVariable> paramArgs = CodegenUtil.paramScalarVariables(s);
    final Collection<ArrayVariable> arrayArgs = MapExtensions.union(this.inputCVs, this.outputCVs).values();
    final Function function = Factory.createFunction(DataType.VOID, s.getName(), ((BaseVariable[])Conversions.unwrapArray(paramArgs, BaseVariable.class)), ((ArrayVariable[])Conversions.unwrapArray(arrayArgs, ArrayVariable.class)), ((ArrayVariable[])Conversions.unwrapArray(this.localCVs.values(), ArrayVariable.class)), body);
    EList<GlobalVariable> _globalVariables = this.program.getGlobalVariables();
    Collection<ArrayVariable> _values = this.localCVs.values();
    Iterable<ArrayVariable> _plus = Iterables.<ArrayVariable>concat(arrayArgs, _values);
    Collection<ArrayVariable> _values_1 = this.flagCVs.values();
    Iterable<GlobalVariable> _plus_1 = Iterables.<GlobalVariable>concat(_plus, _values_1);
    Iterables.<GlobalVariable>addAll(_globalVariables, _plus_1);
    this.program.getFunctions().add(function);
  }

  @Override
  public void inStandardEquation(final StandardEquation se) {
    Boolean _isOutput = se.getVariable().isOutput();
    boolean _not = (!(_isOutput).booleanValue());
    if (_not) {
      return;
    }
    final ArrayVariable evalVar = this.outputCVs.get(se.getVariable());
    final ArrayVariable flagVar = this.flagCVs.get(se.getVariable());
    List<BaseVariable> _paramScalarVariables = CodegenUtil.paramScalarVariables(AlphaUtil.getContainerSystem(se));
    List<BaseVariable> _indexScalarVariables = CodegenUtil.indexScalarVariables(se.getVariable());
    final Iterable<BaseVariable> scalarArgs = Iterables.<BaseVariable>concat(_paramScalarVariables, _indexScalarVariables);
    final Function1<ArrayVariable, MemoryMacro> _function = (ArrayVariable it) -> {
      return Factory.createMemoryMacro(it);
    };
    final Iterable<MemoryMacro> localMemoryMacros = IterableExtensions.<ArrayVariable, MemoryMacro>map(this.localCVs.values(), _function);
    final EvalFunction function = Factory.createEvalFunction(evalVar, flagVar, ((BaseVariable[])Conversions.unwrapArray(scalarArgs, BaseVariable.class)), se, ((MemoryMacro[])Conversions.unwrapArray(localMemoryMacros, MemoryMacro.class)));
    this.program.getFunctions().add(function);
  }

  @Override
  public void inReduceExpression(final ReduceExpression re) {
    String _string = Integer.valueOf(this.reductionNumber).toString();
    final String macroName = ("R" + _string);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("reduce");
    _builder.append(this.reductionNumber);
    final String reduceFunctionName = _builder.toString();
    int _reductionNumber = this.reductionNumber;
    this.reductionNumber = (_reductionNumber + 1);
    final BaseVariable reduceVar = CodegenUtil.baseVariable("reduceVariable", DataType.FLOAT);
    final ISLSet loopDomain = this.createReduceLoopDomain(re);
    final ISLASTNode astNode = this.createReduceLoopASTNode(loopDomain, macroName);
    final FunctionBody functionBody = Factory.createFunctionBody(new StatementMacro[] {}, astNode);
    final Function1<String, BaseVariable> _function = (String param) -> {
      return CodegenUtil.baseVariable(param, DataType.LONG);
    };
    final List<BaseVariable> params = ListExtensions.<String, BaseVariable>map(loopDomain.getParamNames(), _function);
    final ReduceFunction function = Factory.createReduceFunction(reduceFunctionName, re, reduceVar, macroName, functionBody, ((BaseVariable[])Conversions.unwrapArray(params, BaseVariable.class)));
    this.program.getFunctions().add(function);
    HashMap<ReduceExpression, ReduceFunction> _reduceFunctions = this.program.getReduceFunctions();
    boolean _tripleEquals = (_reduceFunctions == null);
    if (_tripleEquals) {
      this.program.setReduceFunctions(CollectionLiterals.<ReduceExpression, ReduceFunction>newHashMap());
    }
    this.program.getReduceFunctions().put(re, function);
  }

  public ISLASTNode createReduceLoopASTNode(final ISLSet loopDomain, final String macroName) {
    final ISLSet context = loopDomain.copy().params();
    final ISLUnionMap schedule = ISLMultiAff.buildIdentity(loopDomain.getSpace().addDims(ISLDimType.isl_dim_in, loopDomain.getNbIndices())).toMap().intersectDomain(loopDomain.copy()).setTupleName(ISLDimType.isl_dim_in, macroName).toUnionMap();
    return ISLASTBuild.buildFromContext(context).generate(schedule);
  }

  public ISLSet createReduceLoopDomain(final ReduceExpression reduceExpr) {
    ISLSet loopDomain = reduceExpr.getBody().getContextDomain().copy();
    final int originalParamCount = loopDomain.getNbParams();
    List<String> _paramNames = loopDomain.getParamNames();
    List<String> _indexNames = loopDomain.getIndexNames();
    final ArrayList<String> existingNames = WriteCProgram.<String>toArrayList(((String[])Conversions.unwrapArray(Iterables.<String>concat(Collections.<List<String>>unmodifiableList(CollectionLiterals.<List<String>>newArrayList(_paramNames, _indexNames))), String.class)));
    final ArrayList<String> addedNames = new ArrayList<String>();
    List<String> _indexNames_1 = reduceExpr.getContextDomain().getIndexNames();
    for (final String toAdd : _indexNames_1) {
      {
        String updatedName = toAdd;
        do {
          String _updatedName = updatedName;
          updatedName = (_updatedName + "p");
        } while(existingNames.contains(updatedName));
        addedNames.add(updatedName);
        existingNames.add(updatedName);
        loopDomain = loopDomain.<ISLSet>addParams(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(updatedName)));
      }
    }
    Iterable<Pair<Integer, ISLAff>> _indexed = IterableExtensions.<ISLAff>indexed(reduceExpr.getProjection().getAffs());
    for (final Pair<Integer, ISLAff> idxAndAff : _indexed) {
      {
        final Integer idx = idxAndAff.getKey();
        final ISLAff aff = idxAndAff.getValue();
        ISLConstraint equality = ISLConstraint.buildEquality(loopDomain.getSpace()).setCoefficient(ISLDimType.isl_dim_param, ((idx).intValue() + originalParamCount), (-1));
        int _nbParams = aff.getNbParams();
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbParams, true);
        for (final Integer paramIdx : _doubleDotLessThan) {
          {
            final ISLVal coefficient = aff.getCoefficientVal(ISLDimType.isl_dim_param, (paramIdx).intValue());
            equality = equality.setCoefficient(ISLDimType.isl_dim_param, (paramIdx).intValue(), coefficient);
          }
        }
        int _nbInputs = aff.getNbInputs();
        ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _nbInputs, true);
        for (final Integer inIdx : _doubleDotLessThan_1) {
          {
            final ISLVal coefficient = aff.getCoefficientVal(ISLDimType.isl_dim_in, (inIdx).intValue());
            equality = equality.setCoefficient(ISLDimType.isl_dim_set, (inIdx).intValue(), coefficient);
          }
        }
        equality = equality.setConstant(aff.getConstantVal());
        loopDomain = loopDomain.addConstraint(equality);
      }
    }
    return loopDomain;
  }

  public static <T extends Object> ArrayList<T> toArrayList(final T... src) {
    return new ArrayList<T>((Collection<? extends T>)Conversions.doWrapArray(src));
  }
}
