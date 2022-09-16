package alpha.codegen.basic.statement;

import alpha.codegen.basic.targetMapping.BasicAffineSchedulePiece;
import alpha.codegen.basic.targetMapping.BasicTargetMapping;
import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.ArgReduceExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.BooleanExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.ExternalArgReduceExpression;
import alpha.model.ExternalMultiArgExpression;
import alpha.model.ExternalReduceExpression;
import alpha.model.FuzzyDependenceExpression;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.IntegerExpression;
import alpha.model.MultiArgExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.RealExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.UNARY_OP;
import alpha.model.UnaryExpression;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.util.AlphaPrintingUtil;
import alpha.model.util.ModelSwitch;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Basic statement generator for C.
 * 
 * This class is far from finished. The code was mostly copied from
 * Show implementation, and minimal tweaks were made to handle simple
 * cases to illustrate the flow.
 * 
 * The role of statement generators is to convert Alpha expressions
 * to backend code (C in this class). A few things to be careful are:
 *  - differences in how operators are implemented; some Alpha OPs may require libraries in C
 *  - correspondence between loop iterators and generated code
 *  - Alpha specific expressions like reductions
 * None of the above are currently handled properly in this implementation.
 */
@SuppressWarnings("all")
public class BasicCStatementGenerator extends ModelSwitch<CharSequence> {
  protected BasicTargetMapping targetMapping;

  protected ISLSet parameterContext = null;

  protected ISLSet statementContext = null;

  public BasicCStatementGenerator(final BasicTargetMapping targetMapping) {
    this.targetMapping = targetMapping;
    this.parameterContext = targetMapping.getSystemBody().getParameterDomain();
  }

  protected static CharSequence memAccess(final Variable v) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = v.getName();
    _builder.append(_name);
    _builder.append("_mem");
    return _builder;
  }

  protected static String printMemAccessFunction(final ISLMultiAff maff) {
    if ((maff == null)) {
      return null;
    }
    final Function1<ISLAff, CharSequence> _function = (ISLAff a) -> {
      return AlphaPrintingUtil.toAlphaString(a);
    };
    final String rhs = IterableExtensions.<ISLAff>join(maff.getAffs(), ",", _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    _builder.append(rhs);
    _builder.append(")");
    return _builder.toString();
  }

  public Map<BasicAffineSchedulePiece, CharSequence> generate() {
    HashMap<BasicAffineSchedulePiece, CharSequence> ret = new HashMap<BasicAffineSchedulePiece, CharSequence>();
    EList<StandardEquation> _standardEquations = this.targetMapping.getSystemBody().getStandardEquations();
    for (final StandardEquation eq : _standardEquations) {
      List<BasicAffineSchedulePiece> _schedulePieces = this.targetMapping.getSchedulePieces(eq);
      for (final BasicAffineSchedulePiece piece : _schedulePieces) {
        {
          this.statementContext = piece.getDomain();
          ret.put(piece, this.doSwitch(eq.getExpr()));
        }
      }
    }
    return ret;
  }

  protected String printFunction(final ISLMultiAff f) {
    return AlphaPrintingUtil.toAShowString(f);
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
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(re.getExpr());
    _builder.append(_doSwitch);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseAutoRestrictExpression(final AutoRestrictExpression are) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(are.getExpr());
    _builder.append(_doSwitch);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseCaseExpression(final CaseExpression ce) {
    CharSequence _xblockexpression = null;
    {
      AlphaExpression onlyExpr = ((AlphaExpression) null);
      EList<AlphaExpression> _exprs = ce.getExprs();
      for (final AlphaExpression expr : _exprs) {
        boolean _isDisjoint = expr.getContextDomain().isDisjoint(this.statementContext);
        boolean _not = (!_isDisjoint);
        if (_not) {
          if ((onlyExpr == null)) {
            onlyExpr = expr;
          } else {
            throw new RuntimeException("Case Expression with branches are not supported.");
          }
        }
      }
      if ((onlyExpr == null)) {
        throw new RuntimeException("Unexpected case.");
      }
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _doSwitch = this.doSwitch(onlyExpr);
      _builder.append(_doSwitch);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  /**
   * override
   */
  public CharSequence caseDependenceExpression(final DependenceExpression de) {
    CharSequence _xifexpression = null;
    if (((de.getExpr() instanceof ConstantExpression) || (de.getExpr() instanceof VariableExpression))) {
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _doSwitch = this.doSwitch(de.getExpr());
      _builder.append(_doSwitch);
      String _printMemAccessFunction = BasicCStatementGenerator.printMemAccessFunction(de.getFunction());
      _builder.append(_printMemAccessFunction);
      _xifexpression = _builder;
    } else {
      throw new RuntimeException("Expecting Normalized Alpha.");
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
  public CharSequence caseVariableExpression(final VariableExpression ve) {
    return BasicCStatementGenerator.memAccess(ve.getVariable());
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
