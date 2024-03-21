package alpha.codegen.factory;

import alpha.codegen.ArrayVariable;
import alpha.codegen.BaseVariable;
import alpha.codegen.CodegenFactory;
import alpha.codegen.DataType;
import alpha.codegen.EvalFunction;
import alpha.codegen.Function;
import alpha.codegen.FunctionBody;
import alpha.codegen.GlobalMemoryMacro;
import alpha.codegen.MemoryAllocation;
import alpha.codegen.MemoryMacro;
import alpha.codegen.Polynomial;
import alpha.codegen.PolynomialPiece;
import alpha.codegen.PolynomialTerm;
import alpha.codegen.Program;
import alpha.codegen.ReduceFunction;
import alpha.codegen.StatementMacro;
import alpha.codegen.VariableType;
import alpha.codegen.util.CodegenUtil;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.util.AlphaUtil;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLTerm;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionExtensions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class Factory {
  private static CodegenFactory factory = CodegenFactory.eINSTANCE;

  public static Program createProgram() {
    Program _xblockexpression = null;
    {
      final Program program = Factory.factory.createProgram();
      program.getIncludes();
      program.getCommonMacros();
      program.getGlobalVariables();
      program.getFunctions();
      _xblockexpression = program;
    }
    return _xblockexpression;
  }

  public static Function createFunction(final DataType returnType, final String name, final BaseVariable[] scalarArguments, final ArrayVariable[] arrayArguments, final ArrayVariable[] localVariables, final FunctionBody body) {
    Function _xblockexpression = null;
    {
      final Function function = Factory.factory.createFunction();
      function.setReturnType(returnType);
      function.setName(name);
      CollectionExtensions.<BaseVariable>addAll(function.getScalarArgs(), scalarArguments);
      CollectionExtensions.<ArrayVariable>addAll(function.getArrayArgs(), arrayArguments);
      function.setBody(body);
      _xblockexpression = function;
    }
    return _xblockexpression;
  }

  public static Function createFunction(final DataType returnType, final String name, final BaseVariable[] scalarArguments, final ArrayVariable[] arrayArguments, final ArrayVariable[] localVariables, final MemoryMacro[] memoryMacros, final FunctionBody body) {
    Function _xblockexpression = null;
    {
      final Function function = Factory.createFunction(returnType, name, scalarArguments, arrayArguments, localVariables, body);
      CollectionExtensions.<MemoryMacro>addAll(function.getMemoryMacros(), memoryMacros);
      _xblockexpression = function;
    }
    return _xblockexpression;
  }

  public static MemoryMacro createMemoryMacro(final ArrayVariable variable, final ISLMap map) {
    MemoryMacro _xblockexpression = null;
    {
      final MemoryMacro macro = Factory.factory.createMemoryMacro();
      macro.setVariable(variable);
      ISLMap _xifexpression = null;
      if ((map != null)) {
        _xifexpression = map;
      } else {
        _xifexpression = variable.getAlphaVariable().getDomain().identity();
      }
      macro.setMap(_xifexpression);
      macro.setAllocation(Factory.createMemoryAllocation(variable, macro));
      _xblockexpression = macro;
    }
    return _xblockexpression;
  }

  public static MemoryMacro createMemoryMacro(final ArrayVariable variable) {
    return Factory.createMemoryMacro(variable, null);
  }

  public static String toßtring(final ISLTerm t, final ISLSpace s) {
    String _xblockexpression = null;
    {
      final int P = t.dim(ISLDimType.isl_dim_param);
      final Function1<Integer, String> _function = (Integer it) -> {
        return s.getDimName(ISLDimType.isl_dim_param, (it).intValue());
      };
      final Iterable<String> params = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, P, true), _function);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("(");
      long _numerator = t.getNumerator();
      _builder.append(_numerator);
      _builder.append("/");
      long _denominator = t.getDenominator();
      _builder.append(_denominator);
      _builder.append(") ");
      final Function1<Integer, Integer> _function_1 = (Integer it) -> {
        return Integer.valueOf(t.getExponent(ISLDimType.isl_dim_param, (it).intValue()));
      };
      Iterable<Integer> _map = IterableExtensions.<Integer, Integer>map(new ExclusiveRange(0, P, true), _function_1);
      _builder.append(_map);
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }

  public static MemoryAllocation createMemoryAllocation(final ArrayVariable variable, final MemoryMacro macro) {
    MemoryAllocation _xblockexpression = null;
    {
      final MemoryAllocation allocation = Factory.factory.createMemoryAllocation();
      allocation.setMacro(macro);
      allocation.setVariable(variable);
      allocation.setDomain(variable.getAlphaVariable().getDomain());
      _xblockexpression = allocation;
    }
    return _xblockexpression;
  }

  public static FunctionBody createFunctionBody(final StatementMacro[] statementMacros, final ISLASTNode node) {
    FunctionBody _xblockexpression = null;
    {
      final FunctionBody body = Factory.factory.createFunctionBody();
      CollectionExtensions.<StatementMacro>addAll(body.getStatementMacros(), statementMacros);
      body.setISLASTNode(node);
      _xblockexpression = body;
    }
    return _xblockexpression;
  }

  public static EvalFunction createEvalFunction(final ArrayVariable evalVar, final ArrayVariable flagVar, final BaseVariable[] scalarArguments, final StandardEquation equation, final MemoryMacro[] localMemoryMacros) {
    EvalFunction _xblockexpression = null;
    {
      final EvalFunction function = Factory.factory.createEvalFunction();
      function.setReturnType(evalVar.getElemType());
      CollectionExtensions.<BaseVariable>addAll(function.getScalarArgs(), scalarArguments);
      String _name = evalVar.getName();
      String _plus = ("eval_" + _name);
      function.setName(_plus);
      function.setVariable(evalVar);
      function.setFlagVariable(flagVar);
      function.setEquation(equation);
      CollectionExtensions.<MemoryMacro>addAll(function.getMemoryMacros(), localMemoryMacros);
      _xblockexpression = function;
    }
    return _xblockexpression;
  }

  public static ReduceFunction createReduceFunction(final String name, final ReduceExpression reduceExpr, final BaseVariable reduceVar, final String macroName, final FunctionBody body, final BaseVariable[] scalarArguments) {
    ReduceFunction _xblockexpression = null;
    {
      final ReduceFunction function = Factory.factory.createReduceFunction();
      function.setReturnType(reduceVar.getElemType());
      function.setName(name);
      CollectionExtensions.<BaseVariable>addAll(function.getScalarArgs(), scalarArguments);
      function.setBody(body);
      function.setReduceExpr(reduceExpr);
      function.setReduceVar(reduceVar);
      function.setMacroName(macroName);
      _xblockexpression = function;
    }
    return _xblockexpression;
  }

  public static ArrayVariable createArrayVariable(final Variable v) {
    ArrayVariable _xblockexpression = null;
    {
      final ArrayVariable cv = Factory.factory.createArrayVariable();
      cv.setName(v.getName());
      cv.setElemType(CodegenUtil.dataType(v));
      cv.setNumDims(AlphaUtil.numDims(v));
      cv.setAlphaVariable(v);
      Boolean _isInput = v.isInput();
      if ((_isInput).booleanValue()) {
        cv.setType(VariableType.INPUT);
      } else {
        Boolean _isOutput = v.isOutput();
        if ((_isOutput).booleanValue()) {
          cv.setType(VariableType.OUTPUT);
        } else {
          cv.setType(VariableType.LOCAL);
        }
      }
      cv.setMemoryMacro(Factory.createGlobalMemoryMacro(cv));
      _xblockexpression = cv;
    }
    return _xblockexpression;
  }

  public static GlobalMemoryMacro createGlobalMemoryMacro(final String left, final String right) {
    GlobalMemoryMacro _xblockexpression = null;
    {
      final GlobalMemoryMacro gmm = Factory.factory.createGlobalMemoryMacro();
      gmm.setLeft(left);
      gmm.setRight(right);
      _xblockexpression = gmm;
    }
    return _xblockexpression;
  }

  public static GlobalMemoryMacro createGlobalMemoryMacro(final ArrayVariable cv) {
    GlobalMemoryMacro _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      String _name = cv.getName();
      _builder.append(_name);
      _builder.append("(");
      String _join = IterableExtensions.join(AlphaUtil.indices(cv.getAlphaVariable()), ",");
      _builder.append(_join);
      _builder.append(")");
      final String left = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name_1 = cv.getName();
      _builder_1.append(_name_1);
      _builder_1.append("[");
      String _join_1 = IterableExtensions.join(AlphaUtil.indices(cv.getAlphaVariable()), "][");
      _builder_1.append(_join_1);
      _builder_1.append("]");
      final String right = _builder_1.toString();
      _xblockexpression = Factory.createGlobalMemoryMacro(left, right);
    }
    return _xblockexpression;
  }

  public static ArrayVariable createArrayVariableForFlag(final Variable v) {
    ArrayVariable _xblockexpression = null;
    {
      final ArrayVariable cv = Factory.createArrayVariable(v);
      String _name = v.getName();
      String _plus = ("_flag_" + _name);
      cv.setName(_plus);
      cv.setElemType(DataType.CHAR);
      cv.setFlagVariable(true);
      cv.setAlphaVariable(v);
      cv.setMemoryMacro(Factory.createGlobalMemoryMacro(cv));
      _xblockexpression = cv;
    }
    return _xblockexpression;
  }

  public static Polynomial createPolynomial(final ISLPWQPolynomial pwqp) {
    Polynomial _xblockexpression = null;
    {
      final Polynomial ret = Factory.factory.createPolynomial();
      ret.setIslPolynomial(pwqp);
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static PolynomialPiece createPolynomialPiece(final ISLQPolynomialPiece piece) {
    PolynomialPiece _xblockexpression = null;
    {
      final PolynomialPiece ret = Factory.factory.createPolynomialPiece();
      ret.setIslPiece(piece);
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static PolynomialTerm createPolynomialTerm(final ISLTerm term) {
    PolynomialTerm _xblockexpression = null;
    {
      final PolynomialTerm ret = Factory.factory.createPolynomialTerm();
      ret.setIslTerm(term);
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }
}
