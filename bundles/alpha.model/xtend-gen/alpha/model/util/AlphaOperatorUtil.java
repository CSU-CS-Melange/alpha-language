package alpha.model.util;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.BooleanExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.IntegerExpression;
import alpha.model.MultiArgExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.RealExpression;
import alpha.model.factory.AlphaUserFactory;
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

  public static boolean hasInverse(final REDUCTION_OP op) {
    boolean _switchResult = false;
    if (op != null) {
      switch (op) {
        case SUM:
        case PROD:
          _switchResult = true;
          break;
        default:
          _switchResult = false;
          break;
      }
    } else {
      _switchResult = false;
    }
    return _switchResult;
  }

  public static boolean hasNoInverse(final REDUCTION_OP op) {
    boolean _hasInverse = AlphaOperatorUtil.hasInverse(op);
    return (!_hasInverse);
  }

  public static BINARY_OP reductionOPtoBinaryInverseOP(final REDUCTION_OP op) {
    BINARY_OP _switchResult = null;
    if (op != null) {
      switch (op) {
        case PROD:
          _switchResult = BINARY_OP.DIV;
          break;
        case SUM:
          _switchResult = BINARY_OP.SUB;
          break;
        case EX:
          throw new RuntimeException("[AlphaOperatorUtil] ExternalFunctions cannot be used as BinaryOP.");
        default:
          throw new RuntimeException("[AlphaOperatorUtil] Operator does not have an inverse.");
      }
    } else {
      throw new RuntimeException("[AlphaOperatorUtil] Operator does not have an inverse.");
    }
    return _switchResult;
  }

  public static boolean isIdentity(final BINARY_OP op, final AlphaExpression expr) {
    boolean _switchResult = false;
    if (op != null) {
      switch (op) {
        case MIN:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Float.valueOf(Float.POSITIVE_INFINITY));
          break;
        case MAX:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Float.valueOf(Float.NEGATIVE_INFINITY));
          break;
        case MUL:
        case DIV:
        case MOD:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Integer.valueOf(1));
          break;
        case ADD:
        case SUB:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Integer.valueOf(0));
          break;
        case AND:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Boolean.valueOf(true));
          break;
        case OR:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Boolean.valueOf(false));
          break;
        default:
          _switchResult = false;
          break;
      }
    } else {
      _switchResult = false;
    }
    return _switchResult;
  }

  public static boolean isAbsorbing(final BINARY_OP op, final AlphaExpression expr) {
    boolean _switchResult = false;
    if (op != null) {
      switch (op) {
        case MIN:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Float.valueOf(Float.NEGATIVE_INFINITY));
          break;
        case MAX:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Float.valueOf(Float.POSITIVE_INFINITY));
          break;
        case MUL:
        case DIV:
        case MOD:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Integer.valueOf(0));
          break;
        case ADD:
        case SUB:
          _switchResult = (AlphaOperatorUtil.valueIs(expr, Float.valueOf(Float.POSITIVE_INFINITY)) || AlphaOperatorUtil.valueIs(expr, Float.valueOf(Float.NEGATIVE_INFINITY)));
          break;
        case AND:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Boolean.valueOf(false));
          break;
        case OR:
          _switchResult = AlphaOperatorUtil.valueIs(expr, Boolean.valueOf(true));
          break;
        default:
          _switchResult = false;
          break;
      }
    } else {
      _switchResult = false;
    }
    return _switchResult;
  }

  public static ConstantExpression createIdentityExpression(final REDUCTION_OP op) {
    ConstantExpression _switchResult = null;
    if (op != null) {
      switch (op) {
        case MIN:
          _switchResult = AlphaUserFactory.createRealExpression(Float.POSITIVE_INFINITY);
          break;
        case MAX:
          _switchResult = AlphaUserFactory.createRealExpression(Float.NEGATIVE_INFINITY);
          break;
        case PROD:
          _switchResult = AlphaUserFactory.createRealExpression(1);
          break;
        case SUM:
          _switchResult = AlphaUserFactory.createRealExpression(0);
          break;
        case AND:
          _switchResult = AlphaUserFactory.createBooleanExpression(true);
          break;
        case OR:
          _switchResult = AlphaUserFactory.createBooleanExpression(false);
          break;
        case XOR:
          _switchResult = AlphaUserFactory.createBooleanExpression(false);
          break;
        case EX:
          throw new RuntimeException("[AlphaOperatorUtil] ExternalFunctions have no identity.");
        default:
          break;
      }
    }
    return _switchResult;
  }

  /**
   * Returns whether an AlphaExpression has a constant value equal to a given number or boolean
   * Recurses inside DependenceExpressions, because Alpha syntax allows (requires?)
   * constants to be wrapped inside them
   */
  protected static boolean _valueIs(final AlphaExpression expr, final Object other) {
    return false;
  }

  protected static boolean _valueIs(final DependenceExpression expr, final Object other) {
    return AlphaOperatorUtil.valueIs(expr.getExpr(), other);
  }

  protected static boolean _valueIs(final IntegerExpression expr, final Object other) {
    Integer _value = expr.getValue();
    return Objects.equal(_value, other);
  }

  protected static boolean _valueIs(final RealExpression expr, final Object other) {
    Float _value = expr.getValue();
    return Objects.equal(_value, other);
  }

  protected static boolean _valueIs(final BooleanExpression expr, final Object other) {
    Boolean _value = expr.getValue();
    return Objects.equal(_value, other);
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
   * Returns true if the operator is idempotent, i.e., x op x = x
   */
  public static boolean isIdempotent(final REDUCTION_OP op) {
    boolean _switchResult = false;
    if (op != null) {
      switch (op) {
        case MIN:
        case MAX:
        case AND:
        case OR:
          _switchResult = true;
          break;
        default:
          _switchResult = false;
          break;
      }
    } else {
      _switchResult = false;
    }
    return _switchResult;
  }

  /**
   * Returns true if the operator has a higher order operator.
   */
  public static boolean hasHigherOrderOperator(final REDUCTION_OP op) {
    boolean _switchResult = false;
    if (op != null) {
      switch (op) {
        case SUM:
          _switchResult = true;
          break;
        default:
          _switchResult = false;
          break;
      }
    } else {
      _switchResult = false;
    }
    return _switchResult;
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

  public static boolean valueIs(final AlphaExpression expr, final Object other) {
    if (expr instanceof BooleanExpression) {
      return _valueIs((BooleanExpression)expr, other);
    } else if (expr instanceof IntegerExpression) {
      return _valueIs((IntegerExpression)expr, other);
    } else if (expr instanceof RealExpression) {
      return _valueIs((RealExpression)expr, other);
    } else if (expr instanceof DependenceExpression) {
      return _valueIs((DependenceExpression)expr, other);
    } else if (expr != null) {
      return _valueIs(expr, other);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr, other).toString());
    }
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
