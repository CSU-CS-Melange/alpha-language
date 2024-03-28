package alpha.codegen.util;

import alpha.codegen.C_BINARY_OP;
import alpha.codegen.C_REDUCTION_OP;
import alpha.codegen.C_UNARY_OP;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.MultiArgExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.ReduceExpression;
import alpha.model.UNARY_OP;
import alpha.model.UnaryExpression;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class AlphaOp {
  public static C_UNARY_OP toCUnaryOp(final UNARY_OP op) {
    try {
      C_UNARY_OP _switchResult = null;
      if (op != null) {
        switch (op) {
          case NOT:
            _switchResult = C_UNARY_OP.NOT;
            break;
          case NEGATE:
            _switchResult = C_UNARY_OP.NEGATE;
            break;
          default:
            String _plus = (op + " operator does not have a C_UNARY_OP definition");
            throw new Exception(_plus);
        }
      } else {
        String _plus = (op + " operator does not have a C_UNARY_OP definition");
        throw new Exception(_plus);
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static C_BINARY_OP toCBinaryOp(final BINARY_OP op) {
    try {
      C_BINARY_OP _switchResult = null;
      if (op != null) {
        switch (op) {
          case MAX:
            _switchResult = C_BINARY_OP.MAX;
            break;
          case MUL:
            _switchResult = C_BINARY_OP.MUL;
            break;
          case DIV:
            _switchResult = C_BINARY_OP.DIV;
            break;
          case MOD:
            _switchResult = C_BINARY_OP.MOD;
            break;
          case ADD:
            _switchResult = C_BINARY_OP.ADD;
            break;
          case SUB:
            _switchResult = C_BINARY_OP.SUB;
            break;
          case AND:
            _switchResult = C_BINARY_OP.AND;
            break;
          case OR:
            _switchResult = C_BINARY_OP.OR;
            break;
          case XOR:
            _switchResult = C_BINARY_OP.XOR;
            break;
          case EQ:
            _switchResult = C_BINARY_OP.EQ;
            break;
          case NE:
            _switchResult = C_BINARY_OP.NE;
            break;
          case GE:
            _switchResult = C_BINARY_OP.GE;
            break;
          case GT:
            _switchResult = C_BINARY_OP.GT;
            break;
          case LE:
            _switchResult = C_BINARY_OP.LE;
            break;
          case LT:
            _switchResult = C_BINARY_OP.LT;
            break;
          default:
            String _plus = (op + " operator does not have a C_BINARY_OP definition");
            throw new Exception(_plus);
        }
      } else {
        String _plus = (op + " operator does not have a C_BINARY_OP definition");
        throw new Exception(_plus);
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static C_REDUCTION_OP toCReductionOp(final REDUCTION_OP op) {
    try {
      C_REDUCTION_OP _switchResult = null;
      if (op != null) {
        switch (op) {
          case MIN:
            _switchResult = C_REDUCTION_OP.MIN;
            break;
          case MAX:
            _switchResult = C_REDUCTION_OP.MAX;
            break;
          case PROD:
            _switchResult = C_REDUCTION_OP.PROD;
            break;
          case SUM:
            _switchResult = C_REDUCTION_OP.SUM;
            break;
          case AND:
            _switchResult = C_REDUCTION_OP.AND;
            break;
          case OR:
            _switchResult = C_REDUCTION_OP.OR;
            break;
          case XOR:
            _switchResult = C_REDUCTION_OP.XOR;
            break;
          default:
            String _plus = (op + " operator does not have a C_REDUCTION_OP definition");
            throw new Exception(_plus);
        }
      } else {
        String _plus = (op + " operator does not have a C_REDUCTION_OP definition");
        throw new Exception(_plus);
      }
      return _switchResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static C_UNARY_OP cOperator(final UnaryExpression ue) {
    return AlphaOp.toCUnaryOp(ue.getOperator());
  }

  public static C_BINARY_OP cOperator(final BinaryExpression be) {
    return AlphaOp.toCBinaryOp(be.getOperator());
  }

  public static C_REDUCTION_OP cOperator(final MultiArgExpression mae) {
    return AlphaOp.toCReductionOp(mae.getOperator());
  }

  public static C_REDUCTION_OP cOperator(final ReduceExpression re) {
    return AlphaOp.toCReductionOp(re.getOperator());
  }
}
