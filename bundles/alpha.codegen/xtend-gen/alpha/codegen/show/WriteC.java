package alpha.codegen.show;

import alpha.codegen.ArrayVariable;
import alpha.codegen.BaseVariable;
import alpha.codegen.DataType;
import alpha.codegen.EvalFunction;
import alpha.codegen.Function;
import alpha.codegen.ReduceFunction;
import alpha.codegen.Visitable;
import alpha.codegen.util.AlphaEquationPrinter;
import alpha.codegen.util.ISLPrintingUtils;
import alpha.model.REDUCTION_OP;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class WriteC extends Base {
  public static boolean DEBUG = true;

  public static <T extends Visitable> String print(final T v) {
    String _xblockexpression = null;
    {
      final WriteC show = new WriteC();
      _xblockexpression = show.doSwitch(v).toString();
    }
    return _xblockexpression;
  }

  public CharSequence caseFunction(final Function f) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _signature = this.signature(f);
    _builder.append(_signature);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("// copy to global");
    _builder.newLine();
    _builder.append("  ");
    final Function1<BaseVariable, String> _function = (BaseVariable it) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name = it.getName();
      _builder_1.append(_name);
      _builder_1.append(" = ");
      CharSequence _localName = this.localName(it);
      _builder_1.append(_localName);
      _builder_1.append(";");
      return _builder_1.toString();
    };
    String _join = IterableExtensions.join(ListExtensions.<BaseVariable, String>map(f.getScalarArgs(), _function), "\n");
    _builder.append(_join, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    final Function1<ArrayVariable, String> _function_1 = (ArrayVariable it) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _writeName = it.writeName();
      _builder_1.append(_writeName);
      _builder_1.append(" = ");
      CharSequence _localName = this.localName(it);
      _builder_1.append(_localName);
      _builder_1.append(";");
      return _builder_1.toString();
    };
    String _join_1 = IterableExtensions.join(ListExtensions.<ArrayVariable, String>map(f.getArrayArgs(), _function_1), "\n");
    _builder.append(_join_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("// parameter checking");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("if (!(");
    String _paramConstraintsToConditionals = ISLPrintingUtils.paramConstraintsToConditionals(f.system().getParameterDomain());
    _builder.append(_paramConstraintsToConditionals, "  ");
    _builder.append(")) {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("printf(\"The value of parameters are not valid.\\n\");");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("exit(-1);");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("// statements");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _doSwitch = this.doSwitch(f.getBody());
    _builder.append(_doSwitch, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public CharSequence caseEvalFunction(final EvalFunction ef) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _signature = this.signature(ef);
    _builder.append(_signature);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("// check flag");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("if (");
    String _identityAccess = ef.getFlagVariable().identityAccess();
    _builder.append(_identityAccess, "  ");
    _builder.append(" == \'N\') {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _identityAccess_1 = ef.getFlagVariable().identityAccess();
    _builder.append(_identityAccess_1, "    ");
    _builder.append(" = \'I\';");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("// Body for ");
    String _name = ef.getVariable().getName();
    _builder.append(_name, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _printStandardEquation = AlphaEquationPrinter.printStandardEquation(ef.getEquation(), ef.getProgram());
    _builder.append(_printStandardEquation, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _identityAccess_2 = ef.getFlagVariable().identityAccess();
    _builder.append(_identityAccess_2, "    ");
    _builder.append(" = \'F\';");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("} else if (");
    String _identityAccess_3 = ef.getFlagVariable().identityAccess();
    _builder.append(_identityAccess_3, "  ");
    _builder.append(" == \'I\') {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("printf(\"There is a self dependence on ");
    String _name_1 = ef.getVariable().getName();
    _builder.append(_name_1, "    ");
    _builder.append(" at (");
    final Function1<String, String> _function = (String it) -> {
      return "%ld";
    };
    String _join = IterableExtensions.join(ListExtensions.<String, String>map(ef.getVariable().indices(), _function), ",");
    _builder.append(_join, "    ");
    _builder.append(")\\n\", ");
    String _join_1 = IterableExtensions.join(ef.getVariable().indices(), ",");
    _builder.append(_join_1, "    ");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("exit(-1);");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("return ");
    String _identityAccess_4 = ef.getVariable().identityAccess();
    _builder.append(_identityAccess_4, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public CharSequence caseReduceFunction(final ReduceFunction rf) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _signature = this.signature(rf);
    _builder.append(_signature);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _localDefinition = this.localDefinition(rf.getReduceVar());
    _builder.append(_localDefinition, "\t");
    _builder.append(" = ");
    String _reductionInitializer = this.getReductionInitializer(rf.getReduceExpr().getOperator(), rf.getReduceVar().getElemType());
    _builder.append(_reductionInitializer, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("#define ");
    CharSequence _reduceMacroLeftSide = this.getReduceMacroLeftSide(rf);
    _builder.append(_reduceMacroLeftSide, "\t\t");
    _builder.append(" ");
    CharSequence _reduceMacroRightSide = this.getReduceMacroRightSide(rf);
    _builder.append(_reduceMacroRightSide, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    CharSequence _doSwitch = this.doSwitch(rf.getBody());
    _builder.append(_doSwitch, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return ");
    String _name = rf.getReduceVar().getName();
    _builder.append(_name, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  @Override
  public CharSequence signature(final Function f) {
    StringConcatenation _builder = new StringConcatenation();
    DataType _returnType = f.getReturnType();
    _builder.append(_returnType);
    _builder.append(" ");
    String _name = f.getName();
    _builder.append(_name);
    _builder.append("(");
    final Function1<BaseVariable, CharSequence> _function = (BaseVariable it) -> {
      return this.localDefinition(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<BaseVariable, CharSequence>map(f.args(), _function), ", ");
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  public CharSequence getReduceMacroLeftSide(final ReduceFunction rf) {
    StringConcatenation _builder = new StringConcatenation();
    String _macroName = rf.getMacroName();
    _builder.append(_macroName);
    _builder.append("(");
    String _join = IterableExtensions.join(rf.getReduceExpr().getBody().getContextDomain().getIndexNames(), ",");
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  public CharSequence getReduceMacroRightSide(final ReduceFunction rf) {
    try {
      CharSequence _xblockexpression = null;
      {
        final REDUCTION_OP operator = rf.getReduceExpr().getOperator();
        final String reduceVar = rf.getReduceVar().getName();
        final String addedExpression = AlphaEquationPrinter.printExpression(rf.getReduceExpr().getBody(), rf.getProgram());
        CharSequence _switchResult = null;
        if (operator != null) {
          switch (operator) {
            case MIN:
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(reduceVar);
              _builder.append(" = min(");
              _builder.append(reduceVar);
              _builder.append(", (");
              _builder.append(addedExpression);
              _builder.append("))");
              _switchResult = _builder;
              break;
            case MAX:
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(reduceVar);
              _builder_1.append(" = max(");
              _builder_1.append(reduceVar);
              _builder_1.append(", (");
              _builder_1.append(addedExpression);
              _builder_1.append("))");
              _switchResult = _builder_1;
              break;
            case SUM:
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append(reduceVar);
              _builder_2.append(" += (");
              _builder_2.append(addedExpression);
              _builder_2.append(")");
              _switchResult = _builder_2;
              break;
            case PROD:
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append(reduceVar);
              _builder_3.append(" *= (");
              _builder_3.append(addedExpression);
              _builder_3.append(")");
              _switchResult = _builder_3;
              break;
            case AND:
              StringConcatenation _builder_4 = new StringConcatenation();
              _builder_4.append(reduceVar);
              _builder_4.append(" &= (");
              _builder_4.append(addedExpression);
              _builder_4.append(")");
              _switchResult = _builder_4;
              break;
            case OR:
              StringConcatenation _builder_5 = new StringConcatenation();
              _builder_5.append(reduceVar);
              _builder_5.append(" |= (");
              _builder_5.append(addedExpression);
              _builder_5.append(")");
              _switchResult = _builder_5;
              break;
            default:
              String _string = operator.toString();
              String _plus = ("Cannot generate code for reduction operator: " + _string);
              throw new Exception(_plus);
          }
        } else {
          String _string = operator.toString();
          String _plus = ("Cannot generate code for reduction operator: " + _string);
          throw new Exception(_plus);
        }
        _xblockexpression = _switchResult;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String getReductionInitializer(final REDUCTION_OP operator, final DataType type) {
    try {
      String _switchResult = null;
      if (operator != null) {
        switch (operator) {
          case MIN:
            _switchResult = this.getNegativeInfinityValue(type);
            break;
          case MAX:
            _switchResult = this.getInfinityValue(type);
            break;
          case SUM:
            _switchResult = this.getZeroValue(type);
            break;
          case PROD:
            _switchResult = this.getOneValue(type);
            break;
          case AND:
            _switchResult = "true";
            break;
          case OR:
            _switchResult = "false";
            break;
          default:
            throw new Exception(("There is no initializer for reduction operator: " + operator));
        }
      } else {
        throw new Exception(("There is no initializer for reduction operator: " + operator));
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String getZeroValue(final DataType type) {
    try {
      String _switchResult = null;
      if (type != null) {
        switch (type) {
          case INT:
            _switchResult = "0";
            break;
          case LONG:
            _switchResult = "0L";
            break;
          case FLOAT:
            _switchResult = "0.0f";
            break;
          case DOUBLE:
            _switchResult = "0.0";
            break;
          default:
            throw new Exception(("There is no \'0\' value for type: " + type));
        }
      } else {
        throw new Exception(("There is no \'0\' value for type: " + type));
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String getOneValue(final DataType type) {
    try {
      String _switchResult = null;
      if (type != null) {
        switch (type) {
          case INT:
            _switchResult = "1";
            break;
          case LONG:
            _switchResult = "1L";
            break;
          case FLOAT:
            _switchResult = "1.0f";
            break;
          case DOUBLE:
            _switchResult = "1.0";
            break;
          default:
            throw new Exception(("There is no \'1\' value for type: " + type));
        }
      } else {
        throw new Exception(("There is no \'1\' value for type: " + type));
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String getInfinityValue(final DataType type) {
    try {
      String _switchResult = null;
      if (type != null) {
        switch (type) {
          case INT:
            _switchResult = "INT_MAX";
            break;
          case LONG:
            _switchResult = "LONG_MAX";
            break;
          case FLOAT:
            _switchResult = "FLT_MAX";
            break;
          case DOUBLE:
            _switchResult = "DBL_MAX";
            break;
          default:
            throw new Exception(("There is no infinity value for type: " + type));
        }
      } else {
        throw new Exception(("There is no infinity value for type: " + type));
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String getNegativeInfinityValue(final DataType type) {
    try {
      String _switchResult = null;
      if (type != null) {
        switch (type) {
          case INT:
            _switchResult = "INT_MIN";
            break;
          case LONG:
            _switchResult = "LONG_MIN";
            break;
          case FLOAT:
            _switchResult = "FLT_MIN";
            break;
          case DOUBLE:
            _switchResult = "DBL_MIN";
            break;
          default:
            throw new Exception(("There is no negative infinity value for type: " + type));
        }
      } else {
        throw new Exception(("There is no negative infinity value for type: " + type));
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
