package alpha.model.util;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaCompleteVisitable;
import alpha.model.AlphaConstant;
import alpha.model.AlphaElement;
import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaPackage;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.AlphaSystemElement;
import alpha.model.ArgReduceExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BINARY_OP;
import alpha.model.BinaryCalculatorExpression;
import alpha.model.BinaryExpression;
import alpha.model.BooleanExpression;
import alpha.model.CALCULATOR_BINARY_OP;
import alpha.model.CALCULATOR_UNARY_OP;
import alpha.model.CalculatorExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.ConvolutionExpression;
import alpha.model.DefinedObject;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.ExternalArgReduceExpression;
import alpha.model.ExternalFunction;
import alpha.model.ExternalMultiArgExpression;
import alpha.model.ExternalReduceExpression;
import alpha.model.FuzzyDependenceExpression;
import alpha.model.IfExpression;
import alpha.model.Imports;
import alpha.model.IndexExpression;
import alpha.model.IntegerExpression;
import alpha.model.JNIDomain;
import alpha.model.JNIDomainInArrayNotation;
import alpha.model.JNIFunctionInArrayNotation;
import alpha.model.MultiArgExpression;
import alpha.model.PolynomialIndexExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.RealExpression;
import alpha.model.RectangularDomain;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.SelectExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UNARY_OP;
import alpha.model.UnaryCalculatorExpression;
import alpha.model.UnaryExpression;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableDomain;
import alpha.model.VariableExpression;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Arrays;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Prints the Alpha program in Show notation. The show notation
 * is mostly context-free and each node can be printed on its own.
 * The only exception is when printing the domains, the parameter
 * domain of the enclosing system is used to simplify the constraints.
 * 
 * For now, Show will completely remove the calculator expression.
 */
@SuppressWarnings("all")
public class Show extends ModelSwitch<CharSequence> {
  protected ISLSet parameterContext = null;

  public static <T extends AlphaCompleteVisitable> String print(final T av) {
    String _xblockexpression = null;
    {
      final Show show = new Show();
      if (((av instanceof AlphaSystemElement) || (av instanceof AlphaExpressionVisitable))) {
        show.parameterContext = AlphaUtil.getContainerSystem(av).getParameterDomain();
      }
      _xblockexpression = show.doSwitch(av).toString();
    }
    return _xblockexpression;
  }

  /**
   * CalculatorExpressions are printed differently depending on the context.
   */
  protected String printParameterDomain(final JNIDomain dom) {
    return AlphaPrintingUtil.toShowStringParameterDomain(dom.getISLSet());
  }

  protected String printSystemBodyDomain(final JNIDomain dom) {
    return AlphaPrintingUtil.toShowStringSystemBodyDomain(dom.getISLSet());
  }

  protected String printVariableDeclarationDomain(final ISLSet set) {
    return AlphaPrintingUtil.toShowString(set, this.parameterContext);
  }

  protected String printUECallParams(final JNIFunctionInArrayNotation f) {
    return f.plainToString();
  }

  protected String printInstantiationDomain(final ISLSet set) {
    return AlphaPrintingUtil.toShowString(set, this.parameterContext);
  }

  protected String printWhileDomain(final ISLSet set) {
    return AlphaPrintingUtil.toShowString(set, this.parameterContext);
  }

  protected String printDomain(final ISLSet set) {
    return AlphaPrintingUtil.toShowString(set, this.parameterContext);
  }

  protected String printFunction(final ISLMultiAff f) {
    return AlphaPrintingUtil.toShowString(f);
  }

  protected String printRelation(final ISLMap rel) {
    return AlphaPrintingUtil.toShowString(rel);
  }

  protected String printPolynomial(final ISLPWQPolynomial p) {
    return AlphaPrintingUtil.toShowString(p);
  }

  protected CharSequence printSubsystemCallParams(final JNIFunctionInArrayNotation f, final ISLSet instantiationDomain) {
    CharSequence _xblockexpression = null;
    {
      final ISLMultiAff maff = f.getISLMultiAff();
      StringConcatenation _builder = new StringConcatenation();
      String _aShowString = AlphaPrintingUtil.toAShowString(maff, instantiationDomain.getIndexNames());
      _builder.append(_aShowString);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  /**
   * override
   */
  public CharSequence caseAlphaRoot(final AlphaRoot root) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<Imports, CharSequence> _function = (Imports it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<Imports, CharSequence>map(root.getImports(), _function), "\n");
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    final Function1<AlphaElement, CharSequence> _function_1 = (AlphaElement it) -> {
      return this.doSwitch(it);
    };
    String _join_1 = IterableExtensions.join(ListExtensions.<AlphaElement, CharSequence>map(root.getElements(), _function_1), "\n");
    _builder.append(_join_1);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseAlphaConstant(final AlphaConstant ac) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("constant ");
    String _name = ac.getName();
    _builder.append(_name);
    _builder.append(" = ");
    Integer _value = ac.getValue();
    _builder.append(_value);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseExternalFunction(final ExternalFunction ef) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("external ");
    String _name = ef.getName();
    _builder.append(_name);
    _builder.append("(");
    Integer _cardinality = ef.getCardinality();
    _builder.append(_cardinality);
    _builder.append(")");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseImports(final Imports i) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import ");
    String _importedNamespace = i.getImportedNamespace();
    _builder.append(_importedNamespace);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseAlphaPackage(final AlphaPackage ap) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _name = ap.getName();
    _builder.append(_name);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    final Function1<AlphaElement, CharSequence> _function = (AlphaElement it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<AlphaElement, CharSequence>map(ap.getElements(), _function), "\n");
    _builder.append(_join, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseAlphaSystem(final AlphaSystem s) {
    CharSequence _xblockexpression = null;
    {
      this.parameterContext = s.getParameterDomain();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("affine ");
      String _name = s.getName();
      _builder.append(_name);
      _builder.append(" ");
      String _printParameterDomain = this.printParameterDomain(s.getParameterDomainExpr());
      _builder.append(_printParameterDomain);
      _builder.newLineIfNotEmpty();
      {
        boolean _isEmpty = s.getInputs().isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          _builder.append("\t");
          _builder.append("inputs");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t");
          final Function1<Variable, CharSequence> _function = (Variable it) -> {
            return this.doSwitch(it);
          };
          String _join = IterableExtensions.join(ListExtensions.<Variable, CharSequence>map(s.getInputs(), _function), "\n");
          _builder.append(_join, "\t\t");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _isEmpty_1 = s.getOutputs().isEmpty();
        boolean _not_1 = (!_isEmpty_1);
        if (_not_1) {
          _builder.append("\t");
          _builder.append("outputs");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t");
          final Function1<Variable, CharSequence> _function_1 = (Variable it) -> {
            return this.doSwitch(it);
          };
          String _join_1 = IterableExtensions.join(ListExtensions.<Variable, CharSequence>map(s.getOutputs(), _function_1), "\n");
          _builder.append(_join_1, "\t\t");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _isEmpty_2 = s.getLocals().isEmpty();
        boolean _not_2 = (!_isEmpty_2);
        if (_not_2) {
          _builder.append("\t");
          _builder.append("locals");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("\t");
          final Function1<Variable, CharSequence> _function_2 = (Variable it) -> {
            return this.doSwitch(it);
          };
          String _join_2 = IterableExtensions.join(ListExtensions.<Variable, CharSequence>map(s.getLocals(), _function_2), "\n");
          _builder.append(_join_2, "\t\t");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        CalculatorExpression _whileDomainExpr = s.getWhileDomainExpr();
        boolean _tripleNotEquals = (_whileDomainExpr != null);
        if (_tripleNotEquals) {
          _builder.append("\t");
          _builder.append("over ");
          String _printInstantiationDomain = this.printInstantiationDomain(s.getWhileDomain());
          _builder.append(_printInstantiationDomain, "\t");
          _builder.append(" while (");
          CharSequence _doSwitch = this.doSwitch(s.getTestExpression());
          _builder.append(_doSwitch, "\t");
          _builder.append(")");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      final Function1<SystemBody, CharSequence> _function_3 = (SystemBody it) -> {
        return this.doSwitch(it);
      };
      String _join_3 = IterableExtensions.join(ListExtensions.<SystemBody, CharSequence>map(s.getSystemBodies(), _function_3), "\n");
      _builder.append(_join_3, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append(".");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  /**
   * override
   */
  public CharSequence caseSystemBody(final SystemBody sysBody) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = sysBody.getEquations().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        {
          JNIDomain _parameterDomainExpr = sysBody.getParameterDomainExpr();
          boolean _tripleNotEquals = (_parameterDomainExpr != null);
          if (_tripleNotEquals) {
            _builder.append("when ");
            String _printSystemBodyDomain = this.printSystemBodyDomain(sysBody.getParameterDomainExpr());
            _builder.append(_printSystemBodyDomain);
            _builder.append(" ");
          }
        }
        _builder.append("let");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        final Function1<Equation, CharSequence> _function = (Equation it) -> {
          return this.doSwitch(it);
        };
        String _join = IterableExtensions.join(ListExtensions.<Equation, CharSequence>map(sysBody.getEquations(), _function), "\n\n");
        _builder.append(_join, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseVariable(final Variable v) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = v.getName();
    _builder.append(_name);
    _builder.append(" : ");
    String _printVariableDeclarationDomain = this.printVariableDeclarationDomain(v.getDomain());
    _builder.append(_printVariableDeclarationDomain);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseStandardEquation(final StandardEquation se) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = se.getVariable().getName();
    _builder.append(_name);
    _builder.append(" = ");
    CharSequence _doSwitch = this.doSwitch(se.getExpr());
    _builder.append(_doSwitch);
    _builder.append(";");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseUseEquation(final UseEquation ue) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      if (((ue.getInstantiationDomainExpr() != null) && (ue.getInstantiationDomain().getNbIndices() > 0))) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("over ");
        String _printInstantiationDomain = this.printInstantiationDomain(ue.getInstantiationDomain());
        _builder.append(_printInstantiationDomain);
        _builder.append(" : ");
        _xifexpression = _builder.toString();
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1.toString();
      }
      final String idom = _xifexpression;
      final CharSequence callParam = this.printSubsystemCallParams(ue.getCallParamsExpr(), ue.getInstantiationDomain());
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(idom);
      _builder_2.append("(");
      final Function1<AlphaExpression, CharSequence> _function = (AlphaExpression it) -> {
        return this.doSwitch(it);
      };
      String _join = IterableExtensions.join(ListExtensions.<AlphaExpression, CharSequence>map(ue.getOutputExprs(), _function), ", ");
      _builder_2.append(_join);
      _builder_2.append(") = ");
      String _name = ue.getSystem().getName();
      _builder_2.append(_name);
      _builder_2.append(callParam);
      _builder_2.append("(");
      final Function1<AlphaExpression, CharSequence> _function_1 = (AlphaExpression it) -> {
        return this.doSwitch(it);
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<AlphaExpression, CharSequence>map(ue.getInputExprs(), _function_1), ", ");
      _builder_2.append(_join_1);
      _builder_2.append(");");
      _xblockexpression = _builder_2;
    }
    return _xblockexpression;
  }

  /**
   * override
   */
  public CharSequence caseIfExpression(final IfExpression ie) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if ");
    CharSequence _doSwitch = this.doSwitch(ie.getCondExpr());
    _builder.append(_doSwitch);
    _builder.append(" then ");
    CharSequence _doSwitch_1 = this.doSwitch(ie.getThenExpr());
    _builder.append(_doSwitch_1);
    _builder.append(" else ");
    CharSequence _doSwitch_2 = this.doSwitch(ie.getElseExpr());
    _builder.append(_doSwitch_2);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseRestrictExpression(final RestrictExpression re) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      if (((re.getDomainExpr() instanceof JNIDomain) || (re.getDomainExpr() instanceof JNIDomainInArrayNotation))) {
        _xifexpression = this.printDomain(re.getRestrictDomain());
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("{");
        String _printDomain = this.printDomain(re.getRestrictDomain());
        _builder.append(_printDomain);
        _builder.append("}");
        _xifexpression = _builder.toString();
      }
      final String domStr = _xifexpression;
      CharSequence _xifexpression_1 = null;
      if (((re.eContainer() instanceof UnaryExpression) || (re.eContainer() instanceof BinaryExpression))) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("(");
        _builder_1.append(domStr);
        _builder_1.append(" : ");
        CharSequence _doSwitch = this.doSwitch(re.getExpr());
        _builder_1.append(_doSwitch);
        _builder_1.append(")");
        _xifexpression_1 = _builder_1;
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append(domStr);
        _builder_2.append(" : ");
        CharSequence _doSwitch_1 = this.doSwitch(re.getExpr());
        _builder_2.append(_doSwitch_1);
        _xifexpression_1 = _builder_2;
      }
      _xblockexpression = _xifexpression_1;
    }
    return _xblockexpression;
  }

  /**
   * override
   */
  public CharSequence caseAutoRestrictExpression(final AutoRestrictExpression are) {
    CharSequence _xifexpression = null;
    if (((are.eContainer() instanceof UnaryExpression) || (are.eContainer() instanceof BinaryExpression))) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("(auto : ");
      CharSequence _doSwitch = this.doSwitch(are.getExpr());
      _builder.append(_doSwitch);
      _builder.append(")");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("auto : ");
      CharSequence _doSwitch_1 = this.doSwitch(are.getExpr());
      _builder_1.append(_doSwitch_1);
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }

  /**
   * override
   */
  public CharSequence caseCaseExpression(final CaseExpression ce) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("case ");
    {
      String _name = ce.getName();
      boolean _tripleNotEquals = (_name != null);
      if (_tripleNotEquals) {
        String _name_1 = ce.getName();
        _builder.append(_name_1);
      }
    }
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    final Function1<AlphaExpression, CharSequence> _function = (AlphaExpression e) -> {
      return this.doSwitch(e);
    };
    String _join = IterableExtensions.<AlphaExpression>join(ce.getExprs(), "", ";\n", ";", _function);
    _builder.append(_join, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseDependenceExpression(final DependenceExpression de) {
    CharSequence _xifexpression = null;
    if ((((((de.getExpr() instanceof IfExpression) || (de.getExpr() instanceof RestrictExpression)) || (de.getExpr() instanceof AutoRestrictExpression)) || (de.getExpr() instanceof UnaryExpression)) || (de.getExpr() instanceof BinaryExpression))) {
      StringConcatenation _builder = new StringConcatenation();
      String _printFunction = this.printFunction(de.getFunction());
      _builder.append(_printFunction);
      _builder.append("@(");
      CharSequence _doSwitch = this.doSwitch(de.getExpr());
      _builder.append(_doSwitch);
      _builder.append(")");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _printFunction_1 = this.printFunction(de.getFunction());
      _builder_1.append(_printFunction_1);
      _builder_1.append("@");
      CharSequence _doSwitch_1 = this.doSwitch(de.getExpr());
      _builder_1.append(_doSwitch_1);
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }

  /**
   * override
   */
  public CharSequence caseIndexExpression(final IndexExpression ie) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("val");
    String _printFunction = this.printFunction(ie.getFunction());
    _builder.append(_printFunction);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence casePolynomialIndexExpression(final PolynomialIndexExpression pie) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("val");
    String _printPolynomial = this.printPolynomial(pie.getPolynomial().gist(pie.getContextDomain()));
    _builder.append(_printPolynomial);
    return _builder;
  }

  /**
   * override
   */
  public String caseReduceExpression(final ReduceExpression re) {
    return this.printAbstractReduceExpression(re);
  }

  /**
   * override
   */
  public String caseExternalReduceExpression(final ExternalReduceExpression ere) {
    return this.printAbstractReduceExpression(ere);
  }

  /**
   * override
   */
  public String caseArgReduceExpression(final ArgReduceExpression re) {
    return this.printAbstractReduceExpression(re);
  }

  /**
   * override
   */
  public String caseExternalArgReduceExpression(final ExternalArgReduceExpression ere) {
    return this.printAbstractReduceExpression(ere);
  }

  protected CharSequence _printReduceExpression(final ReduceExpression re, final CharSequence proj, final CharSequence body) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("reduce(");
    String _printReductionOP = this.printReductionOP(re.getOperator());
    _builder.append(_printReductionOP);
    _builder.append(", ");
    _builder.append(proj);
    _builder.append(", ");
    _builder.append(body);
    _builder.append(")");
    return _builder;
  }

  protected CharSequence _printReduceExpression(final ExternalReduceExpression ere, final CharSequence proj, final CharSequence body) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("reduce(");
    String _name = ere.getExternalFunction().getName();
    _builder.append(_name);
    _builder.append(", ");
    _builder.append(proj);
    _builder.append(", ");
    _builder.append(body);
    _builder.append(")");
    return _builder;
  }

  protected CharSequence _printReduceExpression(final ArgReduceExpression are, final CharSequence proj, final CharSequence body) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("argreduce(");
    String _printReductionOP = this.printReductionOP(are.getOperator());
    _builder.append(_printReductionOP);
    _builder.append(", ");
    _builder.append(proj);
    _builder.append(", ");
    _builder.append(body);
    _builder.append(")");
    return _builder;
  }

  protected CharSequence _printReduceExpression(final ExternalArgReduceExpression aere, final CharSequence proj, final CharSequence body) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("argreduce(");
    String _name = aere.getExternalFunction().getName();
    _builder.append(_name);
    _builder.append(", ");
    _builder.append(proj);
    _builder.append(", ");
    _builder.append(body);
    _builder.append(")");
    return _builder;
  }

  protected String printProjectionFunction(final ISLMultiAff maff) {
    return AlphaPrintingUtil.toShowString(maff);
  }

  protected CharSequence printReductionBody(final AlphaExpression expr) {
    return this.doSwitch(expr);
  }

  protected String printReductionOP(final REDUCTION_OP op) {
    if (op != null) {
      switch (op) {
        case SUM:
          return "+";
        case DIFF:
          return "--";
        case PROD:
          return "*";
        default:
          return op.getLiteral();
      }
    } else {
      return op.getLiteral();
    }
  }

  protected String printAbstractReduceExpression(final AbstractReduceExpression are) {
    String _xblockexpression = null;
    {
      final String proj = this.printProjectionFunction(are.getProjection());
      final CharSequence body = this.printReductionBody(are.getBody());
      _xblockexpression = this.printReduceExpression(are, proj, body).toString();
    }
    return _xblockexpression;
  }

  /**
   * override
   */
  public CharSequence caseConvolutionExpression(final ConvolutionExpression ce) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("conv(");
    String _printDomain = this.printDomain(ce.getKernelDomain());
    _builder.append(_printDomain);
    _builder.append(", ");
    CharSequence _doSwitch = this.doSwitch(ce.getKernelExpression());
    _builder.append(_doSwitch);
    _builder.append(", ");
    CharSequence _doSwitch_1 = this.doSwitch(ce.getDataExpression());
    _builder.append(_doSwitch_1);
    _builder.append(")");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseSelectExpression(final SelectExpression se) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("select ");
    String _printRelation = this.printRelation(se.getSelectRelation());
    _builder.append(_printRelation);
    _builder.append(" from ");
    CharSequence _doSwitch = this.doSwitch(se.getExpr());
    _builder.append(_doSwitch);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseBinaryExpression(final BinaryExpression be) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    CharSequence _doSwitch = this.doSwitch(be.getLeft());
    _builder.append(_doSwitch);
    _builder.append(" ");
    BINARY_OP _operator = be.getOperator();
    _builder.append(_operator);
    _builder.append(" ");
    CharSequence _doSwitch_1 = this.doSwitch(be.getRight());
    _builder.append(_doSwitch_1);
    _builder.append(")");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseMultiArgExpression(final MultiArgExpression mae) {
    StringConcatenation _builder = new StringConcatenation();
    REDUCTION_OP _operator = mae.getOperator();
    _builder.append(_operator);
    _builder.append("(");
    final Function1<AlphaExpression, CharSequence> _function = (AlphaExpression it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<AlphaExpression, CharSequence>map(mae.getExprs(), _function), ", ");
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseExternalMultiArgExpression(final ExternalMultiArgExpression emae) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = emae.getExternalFunction().getName();
    _builder.append(_name);
    _builder.append("(");
    final Function1<AlphaExpression, CharSequence> _function = (AlphaExpression it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<AlphaExpression, CharSequence>map(emae.getExprs(), _function), ", ");
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseUnaryExpression(final UnaryExpression ue) {
    CharSequence _xifexpression = null;
    if ((((ue.getExpr() instanceof ConstantExpression) || (ue.getExpr() instanceof DependenceExpression)) || (ue.getExpr() instanceof FuzzyDependenceExpression))) {
      StringConcatenation _builder = new StringConcatenation();
      UNARY_OP _operator = ue.getOperator();
      _builder.append(_operator);
      _builder.append(" (");
      CharSequence _doSwitch = this.doSwitch(ue.getExpr());
      _builder.append(_doSwitch);
      _builder.append(")");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      UNARY_OP _operator_1 = ue.getOperator();
      _builder_1.append(_operator_1);
      _builder_1.append(" ");
      CharSequence _doSwitch_1 = this.doSwitch(ue.getExpr());
      _builder_1.append(_doSwitch_1);
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }

  /**
   * override
   */
  public String caseVariableExpression(final VariableExpression ve) {
    return ve.getVariable().getName();
  }

  /**
   * override
   */
  public String caseBooleanExpression(final BooleanExpression be) {
    return be.getValue().toString();
  }

  /**
   * override
   */
  public String caseIntegerExpression(final IntegerExpression ie) {
    return ie.getValue().toString();
  }

  /**
   * override
   */
  public String caseRealExpression(final RealExpression re) {
    return re.getValue().toString();
  }

  /**
   * override
   */
  public CharSequence caseBinaryCalculatorExpression(final BinaryCalculatorExpression bce) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    CharSequence _doSwitch = this.doSwitch(bce.getLeft());
    _builder.append(_doSwitch);
    _builder.append(" ");
    CALCULATOR_BINARY_OP _operator = bce.getOperator();
    _builder.append(_operator);
    _builder.append(" ");
    CharSequence _doSwitch_1 = this.doSwitch(bce.getRight());
    _builder.append(_doSwitch_1);
    _builder.append(")");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseUnaryCalculatorExpression(final UnaryCalculatorExpression uce) {
    StringConcatenation _builder = new StringConcatenation();
    CALCULATOR_UNARY_OP _operator = uce.getOperator();
    _builder.append(_operator);
    _builder.append(" ");
    CharSequence _doSwitch = this.doSwitch(uce.getExpr());
    _builder.append(_doSwitch);
    _builder.append(")");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseVariableDomain(final VariableDomain vd) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{ ");
    String _name = vd.getVariable().getName();
    _builder.append(_name);
    _builder.append(" }");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseRectangularDomain(final RectangularDomain rd) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      if (((rd.getIndexNames() != null) && (((Object[])Conversions.unwrapArray(rd.getIndexNames(), Object.class)).length == ((Object[])Conversions.unwrapArray(rd.getUpperBounds(), Object.class)).length))) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("as [");
        String _join = IterableExtensions.join(rd.getIndexNames(), ",");
        _builder.append(_join);
        _builder.append("]");
        _xifexpression = _builder.toString();
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1.toString();
      }
      final String inames = _xifexpression;
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("[");
      String _join_1 = IterableExtensions.join(rd.getUpperBounds(), ",");
      _builder_2.append(_join_1);
      _builder_2.append("] ");
      _builder_2.append(inames);
      _xblockexpression = _builder_2;
    }
    return _xblockexpression;
  }

  /**
   * override
   */
  public String caseDefinedObject(final DefinedObject dobj) {
    return dobj.getObject().getName();
  }

  protected CharSequence printReduceExpression(final AbstractReduceExpression aere, final CharSequence proj, final CharSequence body) {
    if (aere instanceof ExternalArgReduceExpression) {
      return _printReduceExpression((ExternalArgReduceExpression)aere, proj, body);
    } else if (aere instanceof ExternalReduceExpression) {
      return _printReduceExpression((ExternalReduceExpression)aere, proj, body);
    } else if (aere instanceof ArgReduceExpression) {
      return _printReduceExpression((ArgReduceExpression)aere, proj, body);
    } else if (aere instanceof ReduceExpression) {
      return _printReduceExpression((ReduceExpression)aere, proj, body);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(aere, proj, body).toString());
    }
  }
}
