package alpha.model;

import alpha.model.AffineFuzzyVariableUse;
import alpha.model.AlphaFunctionExpression;
import alpha.model.AlphaNode;
import alpha.model.AlphaSystem;
import alpha.model.ArgReduceExpression;
import alpha.model.BinaryCalculatorExpression;
import alpha.model.CALCULATOR_BINARY_OP;
import alpha.model.CALCULATOR_UNARY_OP;
import alpha.model.CalculatorExpression;
import alpha.model.DefinedObject;
import alpha.model.DependenceExpression;
import alpha.model.IndexExpression;
import alpha.model.JNIDomain;
import alpha.model.JNIDomainInArrayNotation;
import alpha.model.JNIFunction;
import alpha.model.JNIFunctionInArrayNotation;
import alpha.model.JNIPolynomial;
import alpha.model.JNIPolynomialInArrayNotation;
import alpha.model.JNIRelation;
import alpha.model.ModelPackage;
import alpha.model.POLY_OBJECT_TYPE;
import alpha.model.RectangularDomain;
import alpha.model.ReduceExpression;
import alpha.model.UnaryCalculatorExpression;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableDomain;
import alpha.model.exception.OutOfContextArrayNotationException;
import alpha.model.issue.AlphaIssue;
import alpha.model.issue.CalculatorExpressionIssue;
import alpha.model.util.AlphaUtil;
import alpha.model.util.DefaultCalculatorExpressionVisitor;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.jni.ISLFactory;
import fr.irisa.cairn.jnimap.isl.jni.JNIISLMap;
import fr.irisa.cairn.jnimap.isl.jni.JNIISLMultiAff;
import fr.irisa.cairn.jnimap.isl.jni.JNIISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.jni.JNIISLSet;
import fr.irisa.cairn.jnimap.runtime.JNIObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * This class is responsible for constructing ISL objects for:<ul>
 *   <li>{@link JNIDomain}</li>
 *   <li>{@link JNIFunction}</li>
 *   <li>{@link JNIRelation}</li>
 * </ul>
 * which also involves evaluating operations over these objects.
 * 
 * The evaluation is done by first converting the textual specification to ISL objects,
 * and then calling the appropriate ISL functions. The list of index names must be provided
 * as context for textual specification in ArrayNotation.
 */
@SuppressWarnings("all")
public class CalculatorExpressionEvaluator extends EObjectImpl implements DefaultCalculatorExpressionVisitor {
  private List<CalculatorExpressionIssue> issues = new LinkedList<CalculatorExpressionIssue>();
  
  private List<String> indexNameContext;
  
  protected CalculatorExpressionEvaluator(final List<String> indexNameContext) {
    this.indexNameContext = indexNameContext;
  }
  
  public static List<CalculatorExpressionIssue> calculate(final CalculatorExpression expr) {
    return CalculatorExpressionEvaluator.calculate(expr, null);
  }
  
  public static List<CalculatorExpressionIssue> calculate(final CalculatorExpression expr, final List<String> indexNameContext) {
    final CalculatorExpressionEvaluator calc = new CalculatorExpressionEvaluator(indexNameContext);
    expr.accept(calc);
    return calc.issues;
  }
  
  private boolean registerIssue(final String msg, final AlphaNode node) {
    EObject _eContainer = node.eContainer();
    EStructuralFeature _eContainingFeature = node.eContainingFeature();
    CalculatorExpressionIssue _calculatorExpressionIssue = new CalculatorExpressionIssue(AlphaIssue.TYPE.ERROR, msg, _eContainer, _eContainingFeature);
    return this.issues.add(_calculatorExpressionIssue);
  }
  
  @Override
  public void visitUnaryCalculatorExpression(final UnaryCalculatorExpression expr) {
    DefaultCalculatorExpressionVisitor.super.visitUnaryCalculatorExpression(expr);
    JNIObject _iSLObject = expr.getExpr().getISLObject();
    boolean _tripleEquals = (_iSLObject == null);
    if (_tripleEquals) {
      return;
    }
    final JNIObject obj = expr.getExpr().getISLObject();
    try {
      final Supplier<JNIObject> _function = () -> {
        return this.evaluateUnaryOperation(expr.getOperator(), obj);
      };
      final Consumer<String> _function_1 = (String err) -> {
        CALCULATOR_UNARY_OP _operator = expr.getOperator();
        String _plus = ("Unary operation \'" + _operator);
        String _plus_1 = (_plus + "\' is undefined for ");
        POLY_OBJECT_TYPE _type = expr.getExpr().getType();
        String _plus_2 = (_plus_1 + _type);
        this.registerIssue(_plus_2, expr);
      };
      final JNIObject res = AlphaUtil.<JNIObject>callISLwithErrorHandling(_function, _function_1);
      expr.setZ__internal_cache_islObject(res);
    } catch (final Throwable _t) {
      if (_t instanceof UnsupportedOperationException) {
        CALCULATOR_UNARY_OP _operator = expr.getOperator();
        String _plus = ("Unary operation \'" + _operator);
        String _plus_1 = (_plus + "\' is undefined for ");
        POLY_OBJECT_TYPE _type = expr.getExpr().getType();
        String _plus_2 = (_plus_1 + _type);
        this.registerIssue(_plus_2, expr.getExpr());
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  private JNIObject _evaluateUnaryOperation(final CALCULATOR_UNARY_OP op, final JNIISLSet set) {
    if (op != null) {
      switch (op) {
        case COMPLEMENT:
          return set.complement();
        case POLYHEDRAL_HULL:
          return set.polyhedralHull().toSet();
        case AFFINE_HULL:
          return set.affineHull().toSet();
        default:
          throw new UnsupportedOperationException();
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }
  
  private JNIObject _evaluateUnaryOperation(final CALCULATOR_UNARY_OP op, final JNIISLMap map) {
    if (op != null) {
      switch (op) {
        case AFFINE_HULL:
          return map.affineHull().toMap();
        case POLYHEDRAL_HULL:
          return map.polyhedralHull().toMap();
        case GET_DOMAIN:
          return map.getDomain();
        case GET_RANGE:
          return map.getRange();
        case COMPLEMENT:
          return map.complement();
        case REVERSE:
          return map.reverse();
        default:
          break;
      }
    }
    return null;
  }
  
  private JNIObject _evaluateUnaryOperation(final CALCULATOR_UNARY_OP op, final JNIISLMultiAff fun) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public void visitBinaryCalculatorExpression(final BinaryCalculatorExpression expr) {
    DefaultCalculatorExpressionVisitor.super.visitBinaryCalculatorExpression(expr);
    if (((((expr.getLeft() == null) || (expr.getRight() == null)) || (expr.getLeft().getISLObject() == null)) || 
      (expr.getRight().getISLObject() == null))) {
      return;
    }
    final JNIObject left = expr.getLeft().getISLObject();
    final JNIObject right = expr.getRight().getISLObject();
    try {
      final Supplier<JNIObject> _function = () -> {
        return this.evaluateBinaryOperation(expr.getOperator(), left, right);
      };
      final Consumer<String> _function_1 = (String err) -> {
        CALCULATOR_BINARY_OP _operator = expr.getOperator();
        String _plus = ("Operation " + _operator);
        String _plus_1 = (_plus + "failed: ");
        String _plus_2 = (_plus_1 + err);
        this.registerIssue(_plus_2, expr);
      };
      final JNIObject res = AlphaUtil.<JNIObject>callISLwithErrorHandling(_function, _function_1);
      expr.setZ__internal_cache_islObject(res);
    } catch (final Throwable _t) {
      if (_t instanceof UnsupportedOperationException) {
        CALCULATOR_BINARY_OP _operator = expr.getOperator();
        String _plus = ("Binary operation \'" + _operator);
        String _plus_1 = (_plus + "\' is undefined for ");
        POLY_OBJECT_TYPE _type = expr.getLeft().getType();
        String _plus_2 = (_plus_1 + _type);
        String _plus_3 = (_plus_2 + " -> ");
        POLY_OBJECT_TYPE _type_1 = expr.getRight().getType();
        String _plus_4 = (_plus_3 + _type_1);
        this.registerIssue(_plus_4, expr);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  private JNIObject _evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIISLSet left, final JNIISLSet right) {
    if (op != null) {
      switch (op) {
        case INTERSECT:
          return left.intersect(right);
        case UNION:
          return left.union(right);
        case CROSS_PRODUCT:
          return left.flatProduct(right);
        case SET_DIFFERENCE:
          return left.subtract(right);
        default:
          throw new UnsupportedOperationException();
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }
  
  private JNIObject _evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIISLSet left, final JNIISLMap right) {
    throw new UnsupportedOperationException();
  }
  
  private JNIObject _evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIISLSet left, final JNIISLMultiAff right) {
    throw new UnsupportedOperationException();
  }
  
  private JNIObject _evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIISLMap left, final JNIISLSet right) {
    if (op != null) {
      switch (op) {
        case INTERSECT:
          return left.intersectDomain(right);
        case SET_DIFFERENCE:
          return left.subtractDomain(right);
        case JOIN:
          return right.apply(left);
        case INTERSECT_RANGE:
          return left.intersectRange(right);
        case SUBTRACT_RANGE:
          return left.subtractRange(right);
        default:
          throw new UnsupportedOperationException();
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }
  
  private JNIObject _evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIISLMap left, final JNIISLMap right) {
    if (op != null) {
      switch (op) {
        case INTERSECT:
          return left.intersect(right);
        case UNION:
          return left.union(right);
        case CROSS_PRODUCT:
          return left.flatProduct(right);
        case SET_DIFFERENCE:
          return left.subtract(right);
        case JOIN:
          return right.applyDomain(left);
        default:
          throw new UnsupportedOperationException();
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }
  
  private JNIObject _evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIISLMap left, final JNIISLMultiAff right) {
    return this.evaluateBinaryOperation(op, left, right.toMap());
  }
  
  private JNIObject _evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIISLMultiAff left, final JNIISLSet right) {
    return this.evaluateBinaryOperation(op, left.toMap(), right);
  }
  
  private JNIObject _evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIISLMultiAff left, final JNIISLMap right) {
    return this.evaluateBinaryOperation(op, left.toMap(), right);
  }
  
  private JNIObject _evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIISLMultiAff left, final JNIISLMultiAff right) {
    if (op != null) {
      switch (op) {
        case CROSS_PRODUCT:
          return left.flatRangeProduct(right);
        case JOIN:
          return right.pullback(left);
        default:
          throw new UnsupportedOperationException();
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }
  
  /**
   * Parsing domains in Alpha to ISLSets
   * 
   * ArrayNotation requires the index names to be inferred from the context. Once the constraints excluding parameters
   * are computed (or it is what should be specified for AShow notation) the set is parsed with ISL.
   */
  @Override
  public void visitJNIDomain(final JNIDomain jniDomain) {
    try {
      JNIISLSet jniset = CalculatorExpressionEvaluator.parseDomain(AlphaUtil.getContainerSystem(jniDomain), this.parseJNIDomain(jniDomain));
      jniDomain.setISLSet(jniset);
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException re = (RuntimeException)_t;
        String _xifexpression = null;
        String _message = re.getMessage();
        boolean _tripleEquals = (_message == null);
        if (_tripleEquals) {
          _xifexpression = re.getClass().getName();
        } else {
          _xifexpression = re.getMessage();
        }
        final String msg = _xifexpression;
        this.registerIssue(msg, jniDomain);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Parses a domain in the context of the given system.
   */
  public static JNIISLSet parseDomain(final AlphaSystem system, final String domainStr) {
    JNIISLSet jniset = ISLFactory.islSet(AlphaUtil.toContextFreeISLString(system, domainStr));
    final JNIISLSet pdom = AlphaUtil.getParameterDomain(system);
    return jniset.intersectParams(pdom.copy());
  }
  
  private String _parseJNIDomain(final JNIDomain jniDomain) {
    return jniDomain.getIslString();
  }
  
  private String _parseJNIDomain(final JNIDomainInArrayNotation jniDomain) {
    String _xblockexpression = null;
    {
      if ((this.indexNameContext == null)) {
        String _islString = jniDomain.getIslString();
        String _plus = ("Empty context found when trying to parse JNIDomain: " + _islString);
        throw new OutOfContextArrayNotationException(_plus);
      }
      _xblockexpression = String.format("{ [%s] : %s }", IterableExtensions.join(this.indexNameContext, ","), jniDomain.getIslString());
    }
    return _xblockexpression;
  }
  
  /**
   * Parsing relations in Alpha as ISLMaps
   * 
   * There is no ArrayNotation for relations, and thus that only preprocessing is to add parameter names.
   */
  @Override
  public void visitJNIRelation(final JNIRelation jniRelation) {
    try {
      final JNIISLSet pdom = AlphaUtil.getParameterDomain(jniRelation);
      JNIISLMap jnimap = ISLFactory.islMap(AlphaUtil.toContextFreeISLString(AlphaUtil.getContainerSystem(jniRelation), jniRelation.getIslString()));
      jnimap = jnimap.intersectParams(pdom.copy());
      jniRelation.setISLMap(jnimap);
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException re = (RuntimeException)_t;
        String _xifexpression = null;
        String _message = re.getMessage();
        boolean _tripleEquals = (_message == null);
        if (_tripleEquals) {
          _xifexpression = re.getClass().getName();
        } else {
          _xifexpression = re.getMessage();
        }
        final String msg = _xifexpression;
        this.registerIssue(msg, jniRelation);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Parsing functions in Alpha as JNIISLMultiAffs
   * 
   * The functions in Alpha are written in two formats that are both different from ISL syntax.
   * The Show notation is only a different way to write ISLMAffs, and are parsed after simple conversion.
   * The ArrayNotation requires the index names to be inferred from the context.
   * 
   * Furthermore, ArrayNotation is used for projection functions in reductions, but with a different semantics.
   */
  @Override
  public void visitJNIFunction(final JNIFunction jniFunction) {
    this.parseJNIFunction(jniFunction);
  }
  
  /**
   * Parsing Alpha functions in Show notation
   * 
   * Functions of the form (i,j->i+j) are converted to ISL syntax: { [i,j]->[i+j] }
   */
  protected Boolean _parseJNIFunction(final JNIFunction jniFunction) {
    boolean _xtrycatchfinallyexpression = false;
    try {
      String _xifexpression = null;
      String _indexList = jniFunction.getAlphaFunction().getIndexList();
      boolean _tripleNotEquals = (_indexList != null);
      if (_tripleNotEquals) {
        _xifexpression = jniFunction.getAlphaFunction().getIndexList();
      } else {
        _xifexpression = "";
      }
      final String indexNames = _xifexpression;
      final Function1<AlphaFunctionExpression, CharSequence> _function = (AlphaFunctionExpression e) -> {
        return e.getISLString();
      };
      final String expr = IterableExtensions.<AlphaFunctionExpression>join(jniFunction.getAlphaFunction().getExprs(), ",", _function);
      final JNIISLMultiAff jnimaff = CalculatorExpressionEvaluator.parseAffineFunction(AlphaUtil.getContainerSystem(jniFunction), indexNames, expr);
      jniFunction.setISLMultiAff(jnimaff);
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException re = (RuntimeException)_t;
        boolean _xblockexpression = false;
        {
          String _xifexpression_1 = null;
          String _message = re.getMessage();
          boolean _tripleEquals = (_message == null);
          if (_tripleEquals) {
            _xifexpression_1 = re.getClass().getName();
          } else {
            _xifexpression_1 = re.getMessage();
          }
          final String msg = _xifexpression_1;
          _xblockexpression = this.registerIssue(msg, jniFunction);
        }
        _xtrycatchfinallyexpression = _xblockexpression;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return Boolean.valueOf(_xtrycatchfinallyexpression);
  }
  
  /**
   * Parses an affine function in the context of the given system.
   * This method is exposed for use in scripts.
   */
  public static JNIISLMultiAff parseAffineFunction(final AlphaSystem system, final String fStr) {
    JNIISLMultiAff _xblockexpression = null;
    {
      String str = fStr;
      str = str.replace("(", " ");
      str = str.replace(")", " ");
      final String[] splitStr = str.split("->");
      int _size = ((List<String>)Conversions.doWrapArray(splitStr)).size();
      boolean _notEquals = (_size != 2);
      if (_notEquals) {
        throw new IllegalArgumentException((("Input does not match the format for AffineFunctions: " + fStr) + " expecting \"(<index names> -> <affine expressions>)\""));
      }
      _xblockexpression = CalculatorExpressionEvaluator.parseAffineFunction(system, splitStr[0], splitStr[1]);
    }
    return _xblockexpression;
  }
  
  /**
   * Method responsible for parsing affine functions.
   * All parsing of affine functions, expect for dependences in ArrayNotation, are done through this method.
   */
  private static JNIISLMultiAff parseAffineFunction(final AlphaSystem system, final String lhsStr, final String rhsStr) {
    final StringBuffer completed = new StringBuffer("{ [");
    completed.append(lhsStr);
    completed.append("] -> [");
    completed.append(rhsStr);
    completed.append("] }");
    return ISLFactory.islMultiAff(AlphaUtil.toContextFreeISLString(system, completed.toString()));
  }
  
  /**
   * Parsing Alpha functions in ArrayNotation
   * 
   * This function uses another dispatch to select either
   *   parseJNIFunctionAsProjection, or
   *   parseJNIFunctionAsFunction
   * depending on its parent node.
   */
  protected Boolean _parseJNIFunction(final JNIFunctionInArrayNotation jniFunction) {
    boolean _xtrycatchfinallyexpression = false;
    try {
      EObject _eContainer = jniFunction.eContainer();
      final JNIISLMultiAff jnimaff = ISLFactory.islMultiAff(
        AlphaUtil.toContextFreeISLString(AlphaUtil.getContainerSystem(jniFunction), 
          this.parseJNIFunctionInContext(jniFunction, ((AlphaNode) _eContainer)).toString()));
      jniFunction.setISLMultiAff(jnimaff);
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException re = (RuntimeException)_t;
        boolean _xblockexpression = false;
        {
          String _xifexpression = null;
          String _message = re.getMessage();
          boolean _tripleEquals = (_message == null);
          if (_tripleEquals) {
            _xifexpression = re.getClass().getName();
          } else {
            _xifexpression = re.getMessage();
          }
          final String msg = _xifexpression;
          _xblockexpression = this.registerIssue(msg, jniFunction);
        }
        _xtrycatchfinallyexpression = _xblockexpression;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return Boolean.valueOf(_xtrycatchfinallyexpression);
  }
  
  /**
   * ArrayNotation is parsed as projection. In this case, the additional indices expressed are treated as the canonical projection dimensions.
   *   For example, reduce(op, [x,y], ...) in the context [i,j] gives (i,j,x,y->i,j) as the projection function.
   */
  private StringBuffer parseJNIFunctionAsProjection(final JNIFunctionInArrayNotation jniFunction) {
    if ((this.indexNameContext == null)) {
      String _format = String.format("ArrayNotation [%s] does not have the necessary context (index names) to be interpreted.", IterableExtensions.join(jniFunction.getArrayNotation(), ","));
      throw new OutOfContextArrayNotationException(_format);
    }
    final StringBuffer funStr = new StringBuffer("{ [");
    EList<String> _arrayNotation = jniFunction.getArrayNotation();
    funStr.append(IterableExtensions.join(Iterables.<String>concat(this.indexNameContext, _arrayNotation), ","));
    funStr.append("] -> [");
    funStr.append(IterableExtensions.join(this.indexNameContext, ","));
    funStr.append("] }");
    return funStr;
  }
  
  /**
   * ArrayNotation is parsed as function. The indexing expression simply becomes the RHS of ISLMAff, while the LHS is determined by the context.
   */
  private StringBuffer parseJNIFunctionAsFunction(final JNIFunctionInArrayNotation jniFunction) {
    if ((this.indexNameContext == null)) {
      String _format = String.format("ArrayNotation [%s] does not have the necessary context (index names) to be interpreted.", IterableExtensions.join(jniFunction.getArrayNotation(), ","));
      throw new OutOfContextArrayNotationException(_format);
    }
    final StringBuffer funStr = new StringBuffer("{ [");
    funStr.append(IterableExtensions.join(this.indexNameContext, ","));
    funStr.append("] -> [");
    funStr.append(IterableExtensions.join(jniFunction.getArrayNotation(), ","));
    funStr.append("] }");
    return funStr;
  }
  
  protected StringBuffer _parseJNIFunctionInContext(final JNIFunctionInArrayNotation jniFunction, final ReduceExpression parent) {
    return this.parseJNIFunctionAsProjection(jniFunction);
  }
  
  protected StringBuffer _parseJNIFunctionInContext(final JNIFunctionInArrayNotation jniFunction, final ArgReduceExpression parent) {
    return this.parseJNIFunctionAsProjection(jniFunction);
  }
  
  protected StringBuffer _parseJNIFunctionInContext(final JNIFunctionInArrayNotation jniFunction, final UseEquation parent) {
    return this.parseJNIFunctionAsFunction(jniFunction);
  }
  
  protected StringBuffer _parseJNIFunctionInContext(final JNIFunctionInArrayNotation jniFunction, final DependenceExpression parent) {
    return this.parseJNIFunctionAsFunction(jniFunction);
  }
  
  protected StringBuffer _parseJNIFunctionInContext(final JNIFunctionInArrayNotation jniFunction, final IndexExpression parent) {
    return this.parseJNIFunctionAsFunction(jniFunction);
  }
  
  protected StringBuffer _parseJNIFunctionInContext(final JNIFunctionInArrayNotation jniFunction, final AffineFuzzyVariableUse parent) {
    return this.parseJNIFunctionAsFunction(jniFunction);
  }
  
  @Override
  public void visitJNIPolynomial(final JNIPolynomial jniPolynomial) {
    try {
      JNIISLPWQPolynomial jniPWQP = CalculatorExpressionEvaluator.parsePolynomial(AlphaUtil.getContainerSystem(jniPolynomial), this.parseJNIPolynomial(jniPolynomial));
      jniPolynomial.setISLPWQPolynomial(jniPWQP);
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException re = (RuntimeException)_t;
        String _xifexpression = null;
        String _message = re.getMessage();
        boolean _tripleEquals = (_message == null);
        if (_tripleEquals) {
          _xifexpression = re.getClass().getName();
        } else {
          _xifexpression = re.getMessage();
        }
        final String msg = _xifexpression;
        this.registerIssue(msg, jniPolynomial);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Parses a polynomial in the context of the given system.
   */
  public static JNIISLPWQPolynomial parsePolynomial(final AlphaSystem system, final String pwqpStr) {
    JNIISLPWQPolynomial jniPWQP = ISLFactory.islPWQPolynomial(AlphaUtil.toContextFreeISLString(system, pwqpStr));
    final JNIISLSet pdom = AlphaUtil.getParameterDomain(system);
    return jniPWQP.intersectParams(pdom.copy());
  }
  
  /**
   * Dispatch method to construct the input islString for both Show and AShow notation.
   * 
   * The output of this method does not have the parameter part. It is added later at parsePolynomial.
   */
  private String _parseJNIPolynomial(final JNIPolynomial jniPolynomial) {
    return jniPolynomial.getIslString();
  }
  
  private String _parseJNIPolynomial(final JNIPolynomialInArrayNotation jniPolynomial) {
    if ((this.indexNameContext == null)) {
      String _islString = jniPolynomial.getIslString();
      String _plus = ("Empty context found when trying to parse JNIPolynomial: " + _islString);
      throw new OutOfContextArrayNotationException(_plus);
    }
    final String contextString = String.format("[%s]->", IterableExtensions.join(this.indexNameContext, ","));
    final StringBuffer pwqpStr = new StringBuffer("{ ");
    final Function1<String, String> _function = (String s) -> {
      return (contextString + s);
    };
    pwqpStr.append(IterableExtensions.join(ListExtensions.<String, String>map(jniPolynomial.getArrayNotation(), _function), "; "));
    pwqpStr.append(" }");
    return pwqpStr.toString();
  }
  
  @Override
  public void visitVariableDomain(final VariableDomain vdom) {
    Variable _variable = vdom.getVariable();
    boolean _tripleNotEquals = (_variable != null);
    if (_tripleNotEquals) {
      vdom.getVariable().getDomain();
    }
  }
  
  @Override
  public void visitRectangularDomain(final RectangularDomain rdom) {
    final int dim = rdom.getUpperBounds().size();
    final ArrayList<String> dimNames = new ArrayList<String>(dim);
    if (((rdom.getIndexNames() != null) && (rdom.getIndexNames().size() == dim))) {
      dimNames.addAll(rdom.getIndexNames());
    } else {
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, dim, true);
      for (final Integer d : _doubleDotLessThan) {
        dimNames.add(("i" + d));
      }
      if (((rdom.getUpperBounds() != null) && (rdom.getIndexNames().size() > 0))) {
        CalculatorExpressionIssue _calculatorExpressionIssue = new CalculatorExpressionIssue(
          AlphaIssue.TYPE.WARNING, 
          "Length of the index names do not match the domain. Specified names are unused.", rdom, 
          ModelPackage.Literals.RECTANGULAR_DOMAIN__INDEX_NAMES);
        this.issues.add(_calculatorExpressionIssue);
      }
    }
    try {
      final JNIISLSet pdom = AlphaUtil.getParameterDomain(rdom);
      final StringBuffer completed = new StringBuffer("[");
      completed.append(String.join(",", pdom.getParametersNames()));
      completed.append("] -> { [");
      completed.append(String.join(",", dimNames));
      completed.append("] :");
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, dim, true);
      for (final Integer d_1 : _doubleDotLessThan_1) {
        {
          if (((d_1).intValue() > 0)) {
            completed.append(" && ");
          }
          String _xifexpression = null;
          int _length = ((Object[])Conversions.unwrapArray(rdom.getUpperBounds(), Object.class)).length;
          int _length_1 = ((Object[])Conversions.unwrapArray(rdom.getLowerBounds(), Object.class)).length;
          boolean _equals = (_length == _length_1);
          if (_equals) {
            _xifexpression = rdom.getLowerBounds().get((d_1).intValue());
          } else {
            _xifexpression = "0";
          }
          final String lb = _xifexpression;
          String _get = dimNames.get((d_1).intValue());
          String _plus = ((lb + "<=") + _get);
          String _plus_1 = (_plus + "<");
          String _get_1 = rdom.getUpperBounds().get((d_1).intValue());
          String _plus_2 = (_plus_1 + _get_1);
          completed.append(_plus_2);
        }
      }
      completed.append("}");
      JNIISLSet jniset = ISLFactory.islSet(completed.toString());
      jniset = jniset.intersectParams(pdom.copy());
      rdom.setISLSet(jniset);
    } catch (final Throwable _t) {
      if (_t instanceof RuntimeException) {
        final RuntimeException re = (RuntimeException)_t;
        this.registerIssue(re.getMessage(), rdom);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Override
  public void visitDefinedObject(final DefinedObject dobj) {
    if ((dobj != null)) {
      dobj.getISLObject();
    }
  }
  
  private JNIObject evaluateUnaryOperation(final CALCULATOR_UNARY_OP op, final JNIObject map) {
    if (map instanceof JNIISLMap) {
      return _evaluateUnaryOperation(op, (JNIISLMap)map);
    } else if (map instanceof JNIISLMultiAff) {
      return _evaluateUnaryOperation(op, (JNIISLMultiAff)map);
    } else if (map instanceof JNIISLSet) {
      return _evaluateUnaryOperation(op, (JNIISLSet)map);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(op, map).toString());
    }
  }
  
  private JNIObject evaluateBinaryOperation(final CALCULATOR_BINARY_OP op, final JNIObject left, final JNIObject right) {
    if (left instanceof JNIISLMap
         && right instanceof JNIISLMap) {
      return _evaluateBinaryOperation(op, (JNIISLMap)left, (JNIISLMap)right);
    } else if (left instanceof JNIISLMap
         && right instanceof JNIISLMultiAff) {
      return _evaluateBinaryOperation(op, (JNIISLMap)left, (JNIISLMultiAff)right);
    } else if (left instanceof JNIISLMap
         && right instanceof JNIISLSet) {
      return _evaluateBinaryOperation(op, (JNIISLMap)left, (JNIISLSet)right);
    } else if (left instanceof JNIISLMultiAff
         && right instanceof JNIISLMap) {
      return _evaluateBinaryOperation(op, (JNIISLMultiAff)left, (JNIISLMap)right);
    } else if (left instanceof JNIISLMultiAff
         && right instanceof JNIISLMultiAff) {
      return _evaluateBinaryOperation(op, (JNIISLMultiAff)left, (JNIISLMultiAff)right);
    } else if (left instanceof JNIISLMultiAff
         && right instanceof JNIISLSet) {
      return _evaluateBinaryOperation(op, (JNIISLMultiAff)left, (JNIISLSet)right);
    } else if (left instanceof JNIISLSet
         && right instanceof JNIISLMap) {
      return _evaluateBinaryOperation(op, (JNIISLSet)left, (JNIISLMap)right);
    } else if (left instanceof JNIISLSet
         && right instanceof JNIISLMultiAff) {
      return _evaluateBinaryOperation(op, (JNIISLSet)left, (JNIISLMultiAff)right);
    } else if (left instanceof JNIISLSet
         && right instanceof JNIISLSet) {
      return _evaluateBinaryOperation(op, (JNIISLSet)left, (JNIISLSet)right);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(op, left, right).toString());
    }
  }
  
  private String parseJNIDomain(final JNIDomain jniDomain) {
    if (jniDomain instanceof JNIDomainInArrayNotation) {
      return _parseJNIDomain((JNIDomainInArrayNotation)jniDomain);
    } else if (jniDomain != null) {
      return _parseJNIDomain(jniDomain);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(jniDomain).toString());
    }
  }
  
  protected Boolean parseJNIFunction(final JNIFunction jniFunction) {
    if (jniFunction instanceof JNIFunctionInArrayNotation) {
      return _parseJNIFunction((JNIFunctionInArrayNotation)jniFunction);
    } else if (jniFunction != null) {
      return _parseJNIFunction(jniFunction);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(jniFunction).toString());
    }
  }
  
  protected StringBuffer parseJNIFunctionInContext(final JNIFunctionInArrayNotation jniFunction, final AlphaNode parent) {
    if (parent instanceof ArgReduceExpression) {
      return _parseJNIFunctionInContext(jniFunction, (ArgReduceExpression)parent);
    } else if (parent instanceof ReduceExpression) {
      return _parseJNIFunctionInContext(jniFunction, (ReduceExpression)parent);
    } else if (parent instanceof DependenceExpression) {
      return _parseJNIFunctionInContext(jniFunction, (DependenceExpression)parent);
    } else if (parent instanceof IndexExpression) {
      return _parseJNIFunctionInContext(jniFunction, (IndexExpression)parent);
    } else if (parent instanceof UseEquation) {
      return _parseJNIFunctionInContext(jniFunction, (UseEquation)parent);
    } else if (parent instanceof AffineFuzzyVariableUse) {
      return _parseJNIFunctionInContext(jniFunction, (AffineFuzzyVariableUse)parent);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(jniFunction, parent).toString());
    }
  }
  
  private String parseJNIPolynomial(final JNIPolynomial jniPolynomial) {
    if (jniPolynomial instanceof JNIPolynomialInArrayNotation) {
      return _parseJNIPolynomial((JNIPolynomialInArrayNotation)jniPolynomial);
    } else if (jniPolynomial != null) {
      return _parseJNIPolynomial(jniPolynomial);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(jniPolynomial).toString());
    }
  }
}
