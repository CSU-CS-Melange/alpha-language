package alpha.model.util;

import alpha.model.AlphaCompleteVisitable;
import alpha.model.AlphaConstant;
import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.ArgReduceExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.ConstantExpression;
import alpha.model.ConvolutionExpression;
import alpha.model.DependenceExpression;
import alpha.model.ExternalArgReduceExpression;
import alpha.model.ExternalFunction;
import alpha.model.ExternalMultiArgExpression;
import alpha.model.ExternalReduceExpression;
import alpha.model.FuzzyDependenceExpression;
import alpha.model.FuzzyIndexExpression;
import alpha.model.FuzzyVariable;
import alpha.model.IndexExpression;
import alpha.model.JNIDomain;
import alpha.model.MultiArgExpression;
import alpha.model.PolyhedralObject;
import alpha.model.PolynomialIndexExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.SelectExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UnaryExpression;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

/**
 * PrintAST is a dump of the Alpha program, mainly used for debugging.
 */
@SuppressWarnings("all")
public class PrintAST extends AbstractAlphaCompleteVisitor {
  private String indent = "";

  protected static final String INDENT_WITH_SIBILING = "   |";

  protected static final String INDENT_LAST_CHILD = "    ";

  protected StringBuffer _output = new StringBuffer();

  protected List<Integer> nodeIdTracker = new LinkedList<Integer>();

  protected int depth = 0;

  protected static String _print(final AlphaVisitable node) {
    final PrintAST printer = new PrintAST();
    node.accept(printer);
    return printer._output.toString();
  }

  protected static String _print(final AlphaExpressionVisitable node) {
    final PrintAST printer = new PrintAST();
    node.accept(printer);
    return printer._output.toString();
  }

  protected StringBuffer printInt(final String prefix, final long v) {
    String _plus = (Long.valueOf(v) + "");
    return this.printStr(prefix, _plus);
  }

  protected StringBuffer printReal(final String prefix, final double v) {
    String _plus = (Double.valueOf(v) + "");
    return this.printStr(prefix, _plus);
  }

  protected StringBuffer printStr(final Object... objs) {
    StringBuffer _xblockexpression = null;
    {
      this._output.append(this.indent);
      final Consumer<Object> _function = new Consumer<Object>() {
        public void accept(final Object o) {
          PrintAST.this._output.append(o);
        }
      };
      ((List<Object>)Conversions.doWrapArray(objs)).forEach(_function);
      _xblockexpression = this._output.append("\n");
    }
    return _xblockexpression;
  }

  protected StringBuffer printId() {
    StringBuffer _xblockexpression = null;
    {
      final StringBuffer id = new StringBuffer();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, this.depth, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          int _length = id.length();
          boolean _greaterThan = (_length > 0);
          if (_greaterThan) {
            id.append(",");
          }
          id.append(this.nodeIdTracker.get((i).intValue()));
        }
      }
      _xblockexpression = this._output.append((((this.indent + "nodeId = (") + id) + ")\n"));
    }
    return _xblockexpression;
  }

  public void defaultIn(final AlphaVisitable av) {
    this.defaultInImpl(av);
  }

  public void defaultIn(final AlphaExpressionVisitable aev) {
    this.defaultInImpl(aev);
  }

  private String _defaultInImpl(final EObject obj) {
    String _xblockexpression = null;
    {
      this.printStr("_", obj.eClass().getName());
      String _indent = this.indent;
      _xblockexpression = this.indent = (_indent + PrintAST.INDENT_WITH_SIBILING);
    }
    return _xblockexpression;
  }

  private String _defaultInImpl(final AlphaExpression obj) {
    String _xblockexpression = null;
    {
      String _name = obj.eClass().getName();
      String _plus = (_name + " ID:");
      EList<Integer> _expressionID = obj.getExpressionID();
      String _plus_1 = (_plus + _expressionID);
      this.printStr("_", _plus_1);
      String _indent = this.indent;
      _xblockexpression = this.indent = (_indent + PrintAST.INDENT_WITH_SIBILING);
    }
    return _xblockexpression;
  }

  public void defaultOut(final AlphaVisitable av) {
    this._defaultOut(av);
  }

  public void defaultOut(final AlphaExpressionVisitable aev) {
    this._defaultOut(aev);
  }

  private String _defaultOut(final EObject obj) {
    int _length = this.indent.length();
    int _length_1 = PrintAST.INDENT_WITH_SIBILING.length();
    int _minus = (_length - _length_1);
    return this.indent = this.indent.substring(0, _minus);
  }

  public void inAlphaSystem(final AlphaSystem system) {
    this.defaultIn(system);
    this.printStr("_", system.getName());
  }

  public void outAlphaSystem(final AlphaSystem system) {
    this.defaultOut(system);
  }

  public void inSystemBody(final SystemBody sysBody) {
    this.defaultIn(sysBody);
    JNIDomain _parameterDomainExpr = sysBody.getParameterDomainExpr();
    boolean _tripleNotEquals = (_parameterDomainExpr != null);
    if (_tripleNotEquals) {
      this.printStr("_", sysBody.getParameterDomain());
    } else {
      this.printStr("_", "else");
    }
  }

  public void outSystemBody(final SystemBody sysBody) {
    this.defaultOut(sysBody);
  }

  public void inExternalFunction(final ExternalFunction ef) {
    this.defaultIn(ef);
    String _name = ef.getName();
    Integer _cardinality = ef.getCardinality();
    String _plus = (_cardinality + ")");
    this.printStr("+--", _name, "(", _plus);
  }

  public void visitAlphaConstant(final AlphaConstant ac) {
    this.defaultIn(ac);
    String _name = ac.getName();
    Integer _value = ac.getValue();
    String _plus = (_value + "");
    this.printStr("+--", _name, "=", _plus);
  }

  public void outVariable(final Variable v) {
    this.defaultIn(v);
    this.printStr("+-- ", v.getName());
    this.printStr("+-- ", v.getDomain());
    this.defaultOut(v);
  }

  public void outFuzzyVariable(final FuzzyVariable v) {
    this.defaultIn(v);
    this.printStr("+-- ", v.getName());
    this.printStr("+-- ", v.getDomain());
    this.printStr("+-- ", v.getRange());
    this.defaultOut(v);
  }

  public void visitPolyhedralObject(final PolyhedralObject pobj) {
    this.defaultIn(pobj);
    this.printStr("+-- ", pobj.getName());
    this.printStr("+-- ", pobj.getType().name());
    this.printStr("+-- ", pobj.getISLObject());
    this.defaultOut(pobj);
  }

  public void inStandardEquation(final StandardEquation se) {
    this.defaultIn(se);
    this.printStr("+-- ", se.getVariable().getName());
  }

  public void inUseEquation(final UseEquation ue) {
    this.defaultIn(ue);
    this.printStr("+-- ", ue.getSystem().getName());
    ISLSet _instantiationDomain = ue.getInstantiationDomain();
    boolean _tripleNotEquals = (_instantiationDomain != null);
    if (_tripleNotEquals) {
      this.printStr("+-- ", ue.getInstantiationDomain());
    }
  }

  public void inAlphaExpression(final AlphaExpression ae) {
    this.defaultIn(ae);
    this.printStr("+-- ", "expDomain: ", ae.getExpressionDomain());
    this.printStr("+-- ", "ctxDomain: ", ae.getContextDomain());
  }

  public void outAlphaExpression(final AlphaExpression ae) {
    this.defaultOut(ae);
  }

  public void inRestrictExpression(final RestrictExpression re) {
    this.inAlphaExpression(re);
    this.printStr("+-- ", re.getDomainExpr().getISLObject());
  }

  public void inAutoRestrictExpression(final AutoRestrictExpression are) {
    this.inAlphaExpression(are);
    ISLSet _inferredDomain = are.getInferredDomain();
    boolean _tripleNotEquals = (_inferredDomain != null);
    if (_tripleNotEquals) {
      this.printStr("+-- ", are.getInferredDomain());
    }
  }

  public void inDependenceExpression(final DependenceExpression de) {
    this.inAlphaExpression(de);
    ISLMultiAff _function = de.getFunction();
    String _plus = (_function + "; ");
    String _showString = AlphaPrintingUtil.toShowString(de.getFunction());
    String _plus_1 = (_plus + _showString);
    this.printStr("+-- ", _plus_1);
  }

  public void inFuzzyDependenceExpression(final FuzzyDependenceExpression fde) {
    this.inAlphaExpression(fde);
    this.printStr("+-- ", fde.getDependenceRelation());
    this.printStr("+-- ", fde.getDependenceRelation().getDomain());
  }

  public void inVariableExpression(final VariableExpression ve) {
    this.inAlphaExpression(ve);
    this.printStr("+-- ", ve.getVariable().getName());
  }

  public void inIndexExpression(final IndexExpression ie) {
    this.inAlphaExpression(ie);
    this.printStr("+-- ", ie.getFunction());
  }

  public void inPolynomialIndexExpression(final PolynomialIndexExpression pie) {
    this.inAlphaExpression(pie);
    this.printStr("+-- ", pie.getPolynomial());
  }

  public void inFuzzyIndexExpression(final FuzzyIndexExpression fie) {
    this.inAlphaExpression(fie);
    this.printStr("+-- ", fie.getDependenceRelation());
  }

  public void inConstantExpression(final ConstantExpression ce) {
    this.inAlphaExpression(ce);
    this.printStr("+-- ", ce.valueString());
  }

  public void inUnaryExpression(final UnaryExpression ue) {
    this.inAlphaExpression(ue);
    this.printStr("+-- ", ue.getOperator());
  }

  public void inBinaryExpression(final BinaryExpression be) {
    this.inAlphaExpression(be);
    this.printStr("+-- ", be.getOperator());
  }

  public void inReduceExpression(final ReduceExpression re) {
    this.inAlphaExpression(re);
    this.printStr("+-- ", re.getOperator());
    this.printStr("+-- ", re.getProjection());
  }

  public void inExternalReduceExpression(final ExternalReduceExpression ere) {
    this.inAlphaExpression(ere);
    this.printStr("+-- ", ere.getExternalFunction().getName());
    this.printStr("+-- ", ere.getProjection());
  }

  public void inArgReduceExpression(final ArgReduceExpression are) {
    this.inAlphaExpression(are);
    this.printStr("+-- ", are.getOperator());
    this.printStr("+-- ", are.getProjection());
  }

  public void inExternalArgReduceExpression(final ExternalArgReduceExpression eare) {
    this.inAlphaExpression(eare);
    this.printStr("+-- ", eare.getOperator());
    this.printStr("+-- ", eare.getProjection());
  }

  public void inConvolutionExpression(final ConvolutionExpression ce) {
    this.inAlphaExpression(ce);
    this.printStr("+-- ", ce.getKernelDomain());
  }

  public void inMultiArgExpression(final MultiArgExpression mae) {
    this.inAlphaExpression(mae);
    this.printStr("+-- ", mae.getOperator());
  }

  public void inExternalMultiArgExpression(final ExternalMultiArgExpression emae) {
    this.inAlphaExpression(emae);
    this.printStr("+-- ", emae.getExternalFunction().getName());
  }

  public void inSelectExpression(final SelectExpression se) {
    this.inAlphaExpression(se);
    this.printStr("+-- ", se.getSelectRelation());
  }

  public static String print(final AlphaCompleteVisitable node) {
    if (node instanceof AlphaExpressionVisitable) {
      return _print((AlphaExpressionVisitable)node);
    } else if (node instanceof AlphaVisitable) {
      return _print((AlphaVisitable)node);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(node).toString());
    }
  }

  private String defaultInImpl(final EObject obj) {
    if (obj instanceof AlphaExpression) {
      return _defaultInImpl((AlphaExpression)obj);
    } else if (obj != null) {
      return _defaultInImpl(obj);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(obj).toString());
    }
  }
}
