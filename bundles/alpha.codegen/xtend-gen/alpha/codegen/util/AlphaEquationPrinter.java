package alpha.codegen.util;

import alpha.codegen.C_BINARY_OP;
import alpha.codegen.C_UNARY_OP;
import alpha.codegen.Program;
import alpha.model.AlphaExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.BooleanExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.ConvolutionExpression;
import alpha.model.DependenceExpression;
import alpha.model.FuzzyDependenceExpression;
import alpha.model.FuzzyIndexExpression;
import alpha.model.FuzzyReduceExpression;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.IntegerExpression;
import alpha.model.MultiArgExpression;
import alpha.model.PolynomialIndexExpression;
import alpha.model.RealExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.SelectExpression;
import alpha.model.StandardEquation;
import alpha.model.UnaryExpression;
import alpha.model.VariableExpression;
import alpha.model.util.ModelSwitch;
import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Prints an Alpha expression as C code, intended to be used by the
 * pretty printers in alpha.codegen.show. Each alpha.model.Variable is
 * associated with an alpha.codegen.ArrayVariable in the program and
 * the codegen variables must be used at VariableExpression nodes.
 * Assumes all expressions have been normalized.
 * 
 *  @author lnarmour
 */
@SuppressWarnings("all")
public class AlphaEquationPrinter extends ModelSwitch<CharSequence> {
  private Program program;

  private String lhs;

  public static String printStandardEquation(final StandardEquation equation, final Program program) {
    String _xblockexpression = null;
    {
      final AlphaEquationPrinter show = new AlphaEquationPrinter(equation, program);
      _xblockexpression = show.doSwitch(equation).toString();
    }
    return _xblockexpression;
  }

  public static String printExpression(final AlphaExpression expression, final Program program) {
    String _xblockexpression = null;
    {
      final AlphaEquationPrinter show = new AlphaEquationPrinter(program);
      _xblockexpression = show.doSwitch(expression).toString();
    }
    return _xblockexpression;
  }

  protected AlphaEquationPrinter(final Program program) {
    this.lhs = null;
    this.program = program;
  }

  public AlphaEquationPrinter(final StandardEquation equ, final Program program) {
    this.lhs = program.getGlobalVariable(equ.getVariable()).identityAccess();
    this.program = program;
  }

  public static String fault(final String msg) {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(msg);
      _builder.append(" CString expression printer not yet supported");
      throw new Exception(_builder.toString());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static String fault(final AlphaExpression ae) {
    return AlphaEquationPrinter.fault(ae.getClass().getName());
  }

  public CharSequence caseStandardEquation(final StandardEquation se) {
    return this.seRules(se, se.getExpr());
  }

  protected CharSequence _seRules(final StandardEquation se, final CaseExpression ce) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(ce);
    _builder.append(_doSwitch);
    return _builder;
  }

  protected CharSequence _seRules(final StandardEquation se, final RestrictExpression re) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    String _indexConstraintsToConditionals = ISLPrintingUtils.indexConstraintsToConditionals(re.getRestrictDomain());
    _builder.append(_indexConstraintsToConditionals);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(this.lhs, "  ");
    _builder.append(" =  ");
    CharSequence _doSwitch = this.doSwitch(re.getExpr());
    _builder.append(_doSwitch, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }

  protected CharSequence _seRules(final StandardEquation se, final AlphaExpression ae) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.lhs);
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(ae);
    _builder.append(_doSwitch);
    return _builder;
  }

  public CharSequence caseCaseExpression(final CaseExpression ce) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<AlphaExpression, CharSequence> _function = (AlphaExpression expr) -> {
      return this.ceRules(ce, expr);
    };
    String _join = IterableExtensions.join(ListExtensions.<AlphaExpression, CharSequence>map(ce.getExprs(), _function), " else ");
    _builder.append(_join);
    return _builder;
  }

  protected CharSequence _ceRules(final CaseExpression ce, final RestrictExpression re) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    String _indexConstraintsToConditionals = ISLPrintingUtils.indexConstraintsToConditionals(re.getRestrictDomain());
    _builder.append(_indexConstraintsToConditionals);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(this.lhs, "  ");
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(re.getExpr());
    _builder.append(_doSwitch, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }

  protected CharSequence _ceRules(final CaseExpression ce, final AutoRestrictExpression re) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    String _indexConstraintsToConditionals = ISLPrintingUtils.indexConstraintsToConditionals(re.getInferredDomain());
    _builder.append(_indexConstraintsToConditionals);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append(this.lhs, "  ");
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(re.getExpr());
    _builder.append(_doSwitch, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }

  protected CharSequence _ceRules(final CaseExpression ce, final AlphaExpression ae) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(this.lhs, "  ");
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(ae);
    _builder.append(_doSwitch, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }

  public String caseIfExpression(final IfExpression ie) {
    return AlphaEquationPrinter.fault(ie);
  }

  public CharSequence caseDependenceExpression(final DependenceExpression de) {
    return this.depExprRules(de, de.getExpr());
  }

  protected CharSequence _depExprRules(final DependenceExpression de, final ConstantExpression ce) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(de.getExpr());
    _builder.append(_doSwitch);
    String _cString = ISLPrintingUtils.toCString(de.getFunction());
    _builder.append(_cString);
    return _builder;
  }

  protected CharSequence _depExprRules(final DependenceExpression de, final VariableExpression ve) {
    CharSequence _xifexpression = null;
    Boolean _isOutput = ve.getVariable().isOutput();
    if ((_isOutput).booleanValue()) {
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _doSwitch = this.doSwitch(de.getExpr());
      _builder.append(_doSwitch);
      String _cString = ISLPrintingUtils.toCString(ISLPrintingUtils.moveParamsToArgs(de.getFunction()));
      _builder.append(_cString);
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      CharSequence _doSwitch_1 = this.doSwitch(de.getExpr());
      _builder_1.append(_doSwitch_1);
      String _cString_1 = ISLPrintingUtils.toCString(de.getFunction());
      _builder_1.append(_cString_1);
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }

  protected CharSequence _depExprRules(final DependenceExpression de, final AlphaExpression ae) {
    return AlphaEquationPrinter.fault(de);
  }

  public String caseFuzzyDependenceExpression(final FuzzyDependenceExpression fde) {
    return AlphaEquationPrinter.fault(fde);
  }

  public String caseReduceExpression(final ReduceExpression re) {
    final String reduceFunctionName = this.program.getReduceFunctions().get(re).getName();
    List<String> _paramNames = re.getContextDomain().getParamNames();
    List<String> _indexNames = re.getContextDomain().getIndexNames();
    final String arguments = IterableExtensions.join(Iterables.<String>concat(Collections.<List<String>>unmodifiableList(CollectionLiterals.<List<String>>newArrayList(_paramNames, _indexNames))), ",");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(reduceFunctionName);
    _builder.append("(");
    _builder.append(arguments);
    _builder.append(");");
    return _builder.toString();
  }

  public CharSequence caseRestrictExpression(final RestrictExpression re) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(re.getExpr());
    _builder.append(_doSwitch);
    return _builder;
  }

  public String caseFuzzyReduceExpression(final FuzzyReduceExpression fre) {
    return AlphaEquationPrinter.fault(fre);
  }

  public String caseConvolutionExpression(final ConvolutionExpression ce) {
    return AlphaEquationPrinter.fault(ce);
  }

  public CharSequence caseUnaryExpression(final UnaryExpression ue) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    C_UNARY_OP _cOperator = AlphaOp.cOperator(ue);
    _builder.append(_cOperator);
    CharSequence _doSwitch = this.doSwitch(ue.getExpr());
    _builder.append(_doSwitch);
    _builder.append(")");
    return _builder;
  }

  public CharSequence caseBinaryExpression(final BinaryExpression be) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    CharSequence _doSwitch = this.doSwitch(be.getLeft());
    _builder.append(_doSwitch);
    _builder.append(") ");
    C_BINARY_OP _cOperator = AlphaOp.cOperator(be);
    _builder.append(_cOperator);
    _builder.append(" (");
    CharSequence _doSwitch_1 = this.doSwitch(be.getRight());
    _builder.append(_doSwitch_1);
    _builder.append(")");
    return _builder;
  }

  public CharSequence caseMultiArgExpression(final MultiArgExpression mae) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    final Function1<AlphaExpression, String> _function = (AlphaExpression it) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("(");
      CharSequence _doSwitch = this.doSwitch(it);
      _builder_1.append(_doSwitch);
      _builder_1.append(")");
      return _builder_1.toString();
    };
    String _join = IterableExtensions.join(ListExtensions.<AlphaExpression, String>map(mae.getExprs(), _function), AlphaOp.cOperator(mae).toString());
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  public String caseSelectExpression(final SelectExpression se) {
    return AlphaEquationPrinter.fault(se);
  }

  public CharSequence caseIndexExpression(final IndexExpression ie) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    String _replaceAll = ie.getFunctionExpr().plainToString().replaceAll("[\\[\\]]", "");
    _builder.append(_replaceAll);
    _builder.append(")");
    return _builder;
  }

  public String casePolynomialIndexExpression(final PolynomialIndexExpression pie) {
    return AlphaEquationPrinter.fault(pie);
  }

  public String caseFuzzyIndexExpression(final FuzzyIndexExpression fie) {
    return AlphaEquationPrinter.fault(fie);
  }

  public CharSequence caseVariableExpression(final VariableExpression ve) {
    StringConcatenation _builder = new StringConcatenation();
    String _readName = this.program.getGlobalVariable(ve.getVariable()).readName();
    _builder.append(_readName);
    return _builder;
  }

  public CharSequence caseIntegerExpression(final IntegerExpression ie) {
    StringConcatenation _builder = new StringConcatenation();
    Integer _value = ie.getValue();
    _builder.append(_value);
    return _builder;
  }

  public CharSequence caseRealExpression(final RealExpression re) {
    StringConcatenation _builder = new StringConcatenation();
    Float _value = re.getValue();
    _builder.append(_value);
    return _builder;
  }

  public CharSequence caseBooleanExpression(final BooleanExpression be) {
    StringConcatenation _builder = new StringConcatenation();
    int _xifexpression = (int) 0;
    Boolean _value = be.getValue();
    if ((_value).booleanValue()) {
      _xifexpression = 1;
    } else {
      _xifexpression = 0;
    }
    _builder.append(_xifexpression);
    return _builder;
  }

  public CharSequence seRules(final StandardEquation se, final AlphaExpression ce) {
    if (ce instanceof CaseExpression) {
      return _seRules(se, (CaseExpression)ce);
    } else if (ce instanceof RestrictExpression) {
      return _seRules(se, (RestrictExpression)ce);
    } else if (ce != null) {
      return _seRules(se, ce);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(se, ce).toString());
    }
  }

  public CharSequence ceRules(final CaseExpression ce, final AlphaExpression re) {
    if (re instanceof AutoRestrictExpression) {
      return _ceRules(ce, (AutoRestrictExpression)re);
    } else if (re instanceof RestrictExpression) {
      return _ceRules(ce, (RestrictExpression)re);
    } else if (re != null) {
      return _ceRules(ce, re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(ce, re).toString());
    }
  }

  public CharSequence depExprRules(final DependenceExpression de, final AlphaExpression ce) {
    if (ce instanceof ConstantExpression) {
      return _depExprRules(de, (ConstantExpression)ce);
    } else if (ce instanceof VariableExpression) {
      return _depExprRules(de, (VariableExpression)ce);
    } else if (ce != null) {
      return _depExprRules(de, ce);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(de, ce).toString());
    }
  }
}
