package alpha.model.util;

import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.Arrays;

@SuppressWarnings("all")
public class ISLValUtil {
  public static ISLVal asVal(final Number a) {
    return ISLVal.buildFromLong(ISLContext.getInstance(), a.longValue());
  }

  protected static ISLVal _operator_plus(final ISLVal a, final ISLVal b) {
    return a.add(b);
  }

  protected static ISLVal _operator_plus(final Number a, final ISLVal b) {
    ISLVal _asVal = ISLValUtil.asVal(a);
    return ISLValUtil.operator_plus(_asVal, b);
  }

  protected static ISLVal _operator_plus(final ISLVal a, final Number b) {
    ISLVal _asVal = ISLValUtil.asVal(b);
    return ISLValUtil.operator_plus(a, _asVal);
  }

  protected static ISLVal _operator_multiply(final ISLVal a, final ISLVal b) {
    return a.mul(b);
  }

  protected static ISLVal _operator_multiply(final Number a, final ISLVal b) {
    ISLVal _asVal = ISLValUtil.asVal(a);
    return ISLValUtil.operator_multiply(_asVal, b);
  }

  protected static ISLVal _operator_multiply(final ISLVal a, final Number b) {
    ISLVal _asVal = ISLValUtil.asVal(b);
    return ISLValUtil.operator_multiply(a, _asVal);
  }

  protected static ISLVal _operator_minus(final ISLVal a, final ISLVal b) {
    return a.sub(b);
  }

  protected static ISLVal _operator_minus(final Number a, final ISLVal b) {
    ISLVal _asVal = ISLValUtil.asVal(a);
    return ISLValUtil.operator_minus(_asVal, b);
  }

  protected static ISLVal _operator_minus(final ISLVal a, final Number b) {
    ISLVal _asVal = ISLValUtil.asVal(b);
    return ISLValUtil.operator_minus(a, _asVal);
  }

  protected static ISLVal _operator_divide(final ISLVal a, final ISLVal b) {
    return a.div(b);
  }

  protected static ISLVal _operator_divide(final Number a, final ISLVal b) {
    ISLVal _asVal = ISLValUtil.asVal(a);
    return ISLValUtil.operator_divide(_asVal, b);
  }

  protected static ISLVal _operator_divide(final ISLVal a, final Number b) {
    ISLVal _asVal = ISLValUtil.asVal(b);
    return ISLValUtil.operator_divide(a, _asVal);
  }

  public static ISLVal operator_minus(final ISLVal a) {
    return a.neg();
  }

  public static ISLVal operator_plus(final Object a, final Object b) {
    if (a instanceof ISLVal
         && b instanceof ISLVal) {
      return _operator_plus((ISLVal)a, (ISLVal)b);
    } else if (a instanceof ISLVal
         && b instanceof Number) {
      return _operator_plus((ISLVal)a, (Number)b);
    } else if (a instanceof Number
         && b instanceof ISLVal) {
      return _operator_plus((Number)a, (ISLVal)b);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(a, b).toString());
    }
  }

  public static ISLVal operator_multiply(final Object a, final Object b) {
    if (a instanceof ISLVal
         && b instanceof ISLVal) {
      return _operator_multiply((ISLVal)a, (ISLVal)b);
    } else if (a instanceof ISLVal
         && b instanceof Number) {
      return _operator_multiply((ISLVal)a, (Number)b);
    } else if (a instanceof Number
         && b instanceof ISLVal) {
      return _operator_multiply((Number)a, (ISLVal)b);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(a, b).toString());
    }
  }

  public static ISLVal operator_minus(final Object a, final Object b) {
    if (a instanceof ISLVal
         && b instanceof ISLVal) {
      return _operator_minus((ISLVal)a, (ISLVal)b);
    } else if (a instanceof ISLVal
         && b instanceof Number) {
      return _operator_minus((ISLVal)a, (Number)b);
    } else if (a instanceof Number
         && b instanceof ISLVal) {
      return _operator_minus((Number)a, (ISLVal)b);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(a, b).toString());
    }
  }

  public static ISLVal operator_divide(final Object a, final Object b) {
    if (a instanceof ISLVal
         && b instanceof ISLVal) {
      return _operator_divide((ISLVal)a, (ISLVal)b);
    } else if (a instanceof ISLVal
         && b instanceof Number) {
      return _operator_divide((ISLVal)a, (Number)b);
    } else if (a instanceof Number
         && b instanceof ISLVal) {
      return _operator_divide((Number)a, (ISLVal)b);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(a, b).toString());
    }
  }
}
