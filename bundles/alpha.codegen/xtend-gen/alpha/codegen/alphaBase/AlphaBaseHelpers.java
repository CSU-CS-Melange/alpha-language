package alpha.codegen.alphaBase;

import alpha.codegen.BaseDataType;
import alpha.codegen.BinaryOperator;
import alpha.codegen.CustomExpr;
import alpha.codegen.Factory;
import alpha.codegen.UnaryOperator;
import alpha.model.BINARY_OP;
import alpha.model.REDUCTION_OP;
import alpha.model.UNARY_OP;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Various helper methods for the alpha.codegen.alphaBase package.
 */
@SuppressWarnings("all")
public class AlphaBaseHelpers {
  /**
   * Gets the expression representing a value of a "flag" variable.
   */
  public static CustomExpr toExpr(final FlagStatus status) {
    String _switchResult = null;
    if (status != null) {
      switch (status) {
        case NOT_EVALUATED:
          _switchResult = "\'N\'";
          break;
        case IN_PROGRESS:
          _switchResult = "\'I\'";
          break;
        case EVALUATED:
          _switchResult = "\'F\'";
          break;
        default:
          String _string = status.toString();
          String _plus = ("Unknown flag status: " + _string);
          throw new IllegalArgumentException(_plus);
      }
    } else {
      String _string = status.toString();
      String _plus = ("Unknown flag status: " + _string);
      throw new IllegalArgumentException(_plus);
    }
    return Factory.customExpr(_switchResult);
  }

  /**
   * Translates an Alpha unary operator to the C equivalent.
   */
  public static UnaryOperator getOperator(final UNARY_OP op) {
    try {
      UnaryOperator _switchResult = null;
      if (op != null) {
        switch (op) {
          case NOT:
            _switchResult = UnaryOperator.NOT;
            break;
          case NEGATE:
            _switchResult = UnaryOperator.NEGATE;
            break;
          default:
            throw new Exception((("Cannot convert Alpha unary operator \'" + op) + "\' to a C operator."));
        }
      } else {
        throw new Exception((("Cannot convert Alpha unary operator \'" + op) + "\' to a C operator."));
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Translates an Alpha binary operator to the C equivalent.
   */
  public static BinaryOperator getOperator(final BINARY_OP op) {
    try {
      BinaryOperator _switchResult = null;
      if (op != null) {
        switch (op) {
          case MIN:
            _switchResult = BinaryOperator.MIN;
            break;
          case MAX:
            _switchResult = BinaryOperator.MAX;
            break;
          case MUL:
            _switchResult = BinaryOperator.TIMES;
            break;
          case DIV:
            _switchResult = BinaryOperator.DIVIDE;
            break;
          case ADD:
            _switchResult = BinaryOperator.PLUS;
            break;
          case SUB:
            _switchResult = BinaryOperator.MINUS;
            break;
          case AND:
            _switchResult = BinaryOperator.AND;
            break;
          case OR:
            _switchResult = BinaryOperator.OR;
            break;
          case EQ:
            _switchResult = BinaryOperator.EQ;
            break;
          case NE:
            _switchResult = BinaryOperator.NEQ;
            break;
          case GE:
            _switchResult = BinaryOperator.GEQ;
            break;
          case GT:
            _switchResult = BinaryOperator.GT;
            break;
          case LE:
            _switchResult = BinaryOperator.LEQ;
            break;
          case LT:
            _switchResult = BinaryOperator.LT;
            break;
          default:
            throw new Exception((("Cannot convert Alpha binary operator \'" + op) + "\' to a C operator."));
        }
      } else {
        throw new Exception((("Cannot convert Alpha binary operator \'" + op) + "\' to a C operator."));
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Translates an Alpha reduction operator to the equivalent C binary operator.
   */
  public static BinaryOperator getOperator(final REDUCTION_OP op) {
    try {
      BinaryOperator _switchResult = null;
      if (op != null) {
        switch (op) {
          case MIN:
            _switchResult = BinaryOperator.MIN;
            break;
          case MAX:
            _switchResult = BinaryOperator.MAX;
            break;
          case PROD:
            _switchResult = BinaryOperator.TIMES;
            break;
          case SUM:
            _switchResult = BinaryOperator.PLUS;
            break;
          case AND:
            _switchResult = BinaryOperator.AND;
            break;
          case OR:
            _switchResult = BinaryOperator.OR;
            break;
          default:
            throw new Exception((("Cannot convert Alpha reduction operator \'" + op) + "\' to a C operator."));
        }
      } else {
        throw new Exception((("Cannot convert Alpha reduction operator \'" + op) + "\' to a C operator."));
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Gets the expression for the initial value to use for the reduction variable
   * that gets accumulated into while computing the reduction.
   * Note: since the current implementation only uses floats to represent data,
   * there are no initial values for the logical operations (AND, OR, etc.).
   */
  public static CustomExpr getReductionInitialValue(final BaseDataType dataType, final REDUCTION_OP op) {
    try {
      CustomExpr _switchResult = null;
      if (op != null) {
        switch (op) {
          case MIN:
            _switchResult = AlphaBaseHelpers.getMaxValue(dataType);
            break;
          case MAX:
            _switchResult = AlphaBaseHelpers.getMinValue(dataType);
            break;
          case PROD:
            _switchResult = AlphaBaseHelpers.getOneValue(dataType);
            break;
          case SUM:
            _switchResult = AlphaBaseHelpers.getZeroValue(dataType);
            break;
          default:
            throw new Exception((("There is no initial value for reduction operator \'" + op) + "\'."));
        }
      } else {
        throw new Exception((("There is no initial value for reduction operator \'" + op) + "\'."));
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Gets the expression for the maximum value of a data type.
   */
  public static CustomExpr getMaxValue(final BaseDataType dataType) {
    try {
      String _switchResult = null;
      if (dataType != null) {
        switch (dataType) {
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
            throw new Exception((("There is no maximum value for type \'" + dataType) + "\'."));
        }
      } else {
        throw new Exception((("There is no maximum value for type \'" + dataType) + "\'."));
      }
      return Factory.customExpr(_switchResult);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Gets the expression for the minimum value of a data type.
   */
  public static CustomExpr getMinValue(final BaseDataType dataType) {
    try {
      String _switchResult = null;
      if (dataType != null) {
        switch (dataType) {
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
            throw new Exception((("There is no maximum value for type \'" + dataType) + "\'."));
        }
      } else {
        throw new Exception((("There is no maximum value for type \'" + dataType) + "\'."));
      }
      return Factory.customExpr(_switchResult);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Gets the expression for 1 for the given data type.
   */
  public static CustomExpr getOneValue(final BaseDataType dataType) {
    try {
      String _switchResult = null;
      if (dataType != null) {
        switch (dataType) {
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
            throw new Exception((("There is no maximum value for type \'" + dataType) + "\'."));
        }
      } else {
        throw new Exception((("There is no maximum value for type \'" + dataType) + "\'."));
      }
      return Factory.customExpr(_switchResult);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Gets the expression for 0 for the given data type.
   */
  public static CustomExpr getZeroValue(final BaseDataType dataType) {
    try {
      String _switchResult = null;
      if (dataType != null) {
        switch (dataType) {
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
            throw new Exception((("There is no maximum value for type \'" + dataType) + "\'."));
        }
      } else {
        throw new Exception((("There is no maximum value for type \'" + dataType) + "\'."));
      }
      return Factory.customExpr(_switchResult);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
