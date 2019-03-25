package alpha.model.util;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.BooleanExpression;
import alpha.model.IntegerExpression;
import alpha.model.MultiArgExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.RealExpression;
import com.google.common.base.Objects;
import java.util.Arrays;

@SuppressWarnings("all")
public class AlphaOperatorUtil {
  public static BINARY_OP reductionOPtoBinaryOP(final REDUCTION_OP op) {
    BINARY_OP _switchResult = null;
    if (op != null) {
      switch (op) {
        case MIN:
          _switchResult = BINARY_OP.MIN;
          break;
        case MAX:
          _switchResult = BINARY_OP.MAX;
          break;
        case PROD:
          _switchResult = BINARY_OP.MUL;
          break;
        case SUM:
          _switchResult = BINARY_OP.ADD;
          break;
        case AND:
          _switchResult = BINARY_OP.AND;
          break;
        case OR:
          _switchResult = BINARY_OP.OR;
          break;
        case XOR:
          _switchResult = BINARY_OP.XOR;
          break;
        case EX:
          throw new RuntimeException("[AlphaOperatorUtil] ExternalFunctions cannot be used as BinaryOP.");
        default:
          break;
      }
    }
    return _switchResult;
  }
  
  public static BINARY_OP reductionOPtoBinaryInverseOP(final REDUCTION_OP op) {
    BINARY_OP _switchResult = null;
    if (op != null) {
      switch (op) {
        case MIN:
        case MAX:
        case AND:
        case OR:
        case XOR:
          throw new RuntimeException("[AlphaOperatorUtil] Operator does not have an inverse.");
        case PROD:
          _switchResult = BINARY_OP.DIV;
          break;
        case SUM:
          _switchResult = BINARY_OP.SUB;
          break;
        case EX:
          throw new RuntimeException("[AlphaOperatorUtil] ExternalFunctions cannot be used as BinaryOP.");
        default:
          break;
      }
    }
    return _switchResult;
  }
  
  public static boolean isIdentity(final BINARY_OP op, final AlphaExpression expr) {
    boolean _xblockexpression = false;
    {
      if (op != null) {
        switch (op) {
          case MIN:
          case MAX:
            return false;
          case MUL:
          case DIV:
          case MOD:
            if ((expr instanceof IntegerExpression)) {
              int _value = ((IntegerExpression)expr).getValue();
              return (_value == 1);
            }
            if ((expr instanceof RealExpression)) {
              float _value_1 = ((RealExpression)expr).getValue();
              return (_value_1 == 1);
            }
            break;
          case ADD:
          case SUB:
            if ((expr instanceof IntegerExpression)) {
              int _value_2 = ((IntegerExpression)expr).getValue();
              return (_value_2 == 0);
            }
            if ((expr instanceof RealExpression)) {
              float _value_3 = ((RealExpression)expr).getValue();
              return (_value_3 == 0);
            }
            break;
          case AND:
            if ((expr instanceof BooleanExpression)) {
              return ((BooleanExpression)expr).isValue();
            }
            break;
          case OR:
            if ((expr instanceof BooleanExpression)) {
              boolean _isValue = ((BooleanExpression)expr).isValue();
              return (!_isValue);
            }
            break;
          default:
            return false;
        }
      } else {
        return false;
      }
      _xblockexpression = false;
    }
    return _xblockexpression;
  }
  
  /**
   * Tests if op1 distributes over op2.
   * 
   * isDistributiveOver(BINARY_OP.MUL, BINARY_OP.ADD) is true
   * but
   * isDistributiveOver(BINARY_OP.ADD, BINARY_OP.MUL) is false
   */
  public static boolean isDistributiveOver(final BINARY_OP op1, final BINARY_OP op2) {
    boolean _xblockexpression = false;
    {
      if ((Objects.equal(op1, BINARY_OP.MUL) && Objects.equal(op2, BINARY_OP.ADD))) {
        return true;
      }
      if ((Objects.equal(op1, BINARY_OP.MAX) && Objects.equal(op2, BINARY_OP.MIN))) {
        return true;
      }
      if ((Objects.equal(op1, BINARY_OP.MIN) && Objects.equal(op2, BINARY_OP.MAX))) {
        return true;
      }
      if ((Objects.equal(op1, BINARY_OP.ADD) && Objects.equal(op2, BINARY_OP.MAX))) {
        return true;
      }
      if ((Objects.equal(op1, BINARY_OP.ADD) && Objects.equal(op2, BINARY_OP.MIN))) {
        return true;
      }
      _xblockexpression = false;
    }
    return _xblockexpression;
  }
  
  public static boolean isDistributiveOver(final BINARY_OP op1, final REDUCTION_OP op2) {
    return AlphaOperatorUtil.isDistributiveOver(op1, AlphaOperatorUtil.reductionOPtoBinaryOP(op2));
  }
  
  public static boolean isDistributiveOver(final REDUCTION_OP op1, final REDUCTION_OP op2) {
    return AlphaOperatorUtil.isDistributiveOver(AlphaOperatorUtil.reductionOPtoBinaryOP(op1), AlphaOperatorUtil.reductionOPtoBinaryOP(op2));
  }
  
  /**
   * Expects BinaryExpression, MultiArgExpression, or AbstractReduceExpression and returns
   * BINARY_OP after converting the OP if it was MultiArgExpression/AbstractReduceExpression.
   */
  protected static BINARY_OP _getBinaryOP(final AlphaExpression expr) {
    throw new IllegalArgumentException("[AlphaOperatorUtil] Expecting BinaryExpression or MultiArgExpression.");
  }
  
  protected static BINARY_OP _getBinaryOP(final BinaryExpression expr) {
    return expr.getOperator();
  }
  
  protected static BINARY_OP _getBinaryOP(final MultiArgExpression expr) {
    return AlphaOperatorUtil.reductionOPtoBinaryOP(expr.getOperator());
  }
  
  protected static BINARY_OP _getBinaryOP(final AbstractReduceExpression expr) {
    return AlphaOperatorUtil.reductionOPtoBinaryOP(expr.getOperator());
  }
  
  public static BINARY_OP getBinaryOP(final AlphaExpression expr) {
    if (expr instanceof AbstractReduceExpression) {
      return _getBinaryOP((AbstractReduceExpression)expr);
    } else if (expr instanceof BinaryExpression) {
      return _getBinaryOP((BinaryExpression)expr);
    } else if (expr instanceof MultiArgExpression) {
      return _getBinaryOP((MultiArgExpression)expr);
    } else if (expr != null) {
      return _getBinaryOP(expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
}
