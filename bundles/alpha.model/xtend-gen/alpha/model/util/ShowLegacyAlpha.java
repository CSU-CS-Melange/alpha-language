package alpha.model.util;

import alpha.model.AlphaExpression;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.CalculatorExpression;
import alpha.model.CaseExpression;
import alpha.model.ConvolutionExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.JNIDomain;
import alpha.model.PolynomialIndexExpression;
import alpha.model.RestrictExpression;
import alpha.model.SelectExpression;
import alpha.model.SystemBody;
import alpha.model.UnaryExpression;
import alpha.model.UseEquation;
import alpha.model.Variable;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Prints the Alpha program in Show notation of the older syntax used in
 * AlphaZ @ CSU.
 * 
 * The input program should not use:
 *   - FuzzyVariable/Function
 *   - While domains
 *   - PolynomialIndexExpression
 *   - SelectExpression
 *   - ConvolutionExpression
 * 
 * In addition:
 *   - a single system body must be specified for systems with multiple bodies
 *   - all package and external function declarations are removed
 */
@SuppressWarnings("all")
public class ShowLegacyAlpha {
  public static class ShowLegacyAlphaForSystem extends Show {
    protected SystemBody targetBody;

    /**
     * The data type to use for the Alpha variables.
     */
    private final String valueType;

    public ShowLegacyAlphaForSystem(final SystemBody targetBody, final String valueType) {
      this.targetBody = targetBody;
      this.valueType = valueType;
    }

    protected String printDomain(final ISLSet set) {
      return AlphaPrintingUtil.toLegacyAlphaString(set);
    }

    protected String printParameterDomain(final JNIDomain dom) {
      return AlphaPrintingUtil.toLegacyAlphaStringParameterDomain(dom.getISLSet());
    }

    protected String printVariableDeclarationDomain(final ISLSet set) {
      return AlphaPrintingUtil.toLegacyAlphaString(set);
    }

    public CharSequence caseAlphaSystem(final AlphaSystem s) {
      CharSequence _xblockexpression = null;
      {
        CalculatorExpression _whileDomainExpr = s.getWhileDomainExpr();
        boolean _tripleNotEquals = (_whileDomainExpr != null);
        if (_tripleNotEquals) {
          throw new IllegalArgumentException("[ShowLegacyAlpha] While domains are not allowed.");
        }
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
            _builder.append("input");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            final Function1<Variable, CharSequence> _function = new Function1<Variable, CharSequence>() {
              public CharSequence apply(final Variable it) {
                return ShowLegacyAlphaForSystem.this.doSwitch(it);
              }
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
            _builder.append("output");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            final Function1<Variable, CharSequence> _function_1 = new Function1<Variable, CharSequence>() {
              public CharSequence apply(final Variable it) {
                return ShowLegacyAlphaForSystem.this.doSwitch(it);
              }
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
            _builder.append("local");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            final Function1<Variable, CharSequence> _function_2 = new Function1<Variable, CharSequence>() {
              public CharSequence apply(final Variable it) {
                return ShowLegacyAlphaForSystem.this.doSwitch(it);
              }
            };
            String _join_2 = IterableExtensions.join(ListExtensions.<Variable, CharSequence>map(s.getLocals(), _function_2), "\n");
            _builder.append(_join_2, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        _builder.append("let");
        _builder.newLine();
        _builder.append("\t\t");
        final Function1<Equation, CharSequence> _function_3 = new Function1<Equation, CharSequence>() {
          public CharSequence apply(final Equation it) {
            return ShowLegacyAlphaForSystem.this.doSwitch(it);
          }
        };
        String _join_3 = IterableExtensions.join(ListExtensions.<Equation, CharSequence>map(this.targetBody.getEquations(), _function_3), "\n\n");
        _builder.append(_join_3, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append(".");
        _builder.newLine();
        _xblockexpression = _builder;
      }
      return _xblockexpression;
    }

    public CharSequence caseDependenceExpression(final DependenceExpression de) {
      StringConcatenation _builder = new StringConcatenation();
      String _printFunction = this.printFunction(de.getFunction());
      _builder.append(_printFunction);
      _builder.append("@(");
      CharSequence _doSwitch = this.doSwitch(de.getExpr());
      _builder.append(_doSwitch);
      _builder.append(")");
      return _builder;
    }

    public CharSequence caseVariable(final Variable v) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(this.valueType);
      _builder.append(" ");
      String _name = v.getName();
      _builder.append(_name);
      _builder.append(" ");
      String _printVariableDeclarationDomain = this.printVariableDeclarationDomain(v.getDomain());
      _builder.append(_printVariableDeclarationDomain);
      _builder.append(";");
      return _builder;
    }

    public CharSequence caseUseEquation(final UseEquation ue) {
      throw new UnsupportedOperationException("[ShowLegacyAlpha] Use equations are not yet supported.");
    }

    public CharSequence caseRestrictExpression(final RestrictExpression re) {
      return this.caseRestrict(re, re.getRestrictDomain(), re.getExpr());
    }

    public CharSequence caseAutoRestrictExpression(final AutoRestrictExpression are) {
      return this.caseRestrict(are, are.getInferredDomain(), are.getExpr());
    }

    private CharSequence caseRestrict(final AlphaExpression expr, final ISLSet domain, final AlphaExpression child) {
      CharSequence _xblockexpression = null;
      {
        final String domStr = this.printDomain(domain);
        CharSequence _xifexpression = null;
        if (((expr.eContainer() instanceof UnaryExpression) || (expr.eContainer() instanceof BinaryExpression))) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("(");
          _builder.append(domStr);
          _builder.append(" : ");
          CharSequence _doSwitch = this.doSwitch(child);
          _builder.append(_doSwitch);
          _builder.append(")");
          _xifexpression = _builder;
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(domStr);
          _builder_1.append(" : ");
          CharSequence _doSwitch_1 = this.doSwitch(child);
          _builder_1.append(_doSwitch_1);
          _xifexpression = _builder_1;
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    }

    public CharSequence caseCaseExpression(final CaseExpression ce) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("case ");
      _builder.newLine();
      _builder.append("\t");
      final Function1<AlphaExpression, CharSequence> _function = new Function1<AlphaExpression, CharSequence>() {
        public CharSequence apply(final AlphaExpression e) {
          return ShowLegacyAlphaForSystem.this.doSwitch(e);
        }
      };
      String _join = IterableExtensions.<AlphaExpression>join(ce.getExprs(), "", ";\n", ";", _function);
      _builder.append(_join, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("esac");
      return _builder;
    }

    public CharSequence casePolynomialIndexExpression(final PolynomialIndexExpression pie) {
      throw new IllegalArgumentException("[ShowLegacyAlpha] PolynomialIndexExpression is not allowed.");
    }

    public CharSequence caseSelectExpression(final SelectExpression se) {
      throw new IllegalArgumentException("[ShowLegacyAlpha] SelectExpression is not allowed.");
    }

    public CharSequence caseConvolutionExpression(final ConvolutionExpression ce) {
      throw new IllegalArgumentException("[ShowLegacyAlpha] ConvolutionExpression is not allowed.");
    }
  }

  private static final String defaultValueType = "float";

  public static String print(final AlphaRoot root) {
    return ShowLegacyAlpha.print(root, ShowLegacyAlpha.defaultValueType);
  }

  public static String print(final AlphaRoot root, final String valueType) {
    final Function1<AlphaSystem, CharSequence> _function = new Function1<AlphaSystem, CharSequence>() {
      public CharSequence apply(final AlphaSystem s) {
        return ShowLegacyAlpha.print(s, valueType);
      }
    };
    return IterableExtensions.<AlphaSystem>join(root.getSystems(), "\n", _function);
  }

  public static String print(final AlphaSystem system) {
    return ShowLegacyAlpha.print(system, ShowLegacyAlpha.defaultValueType);
  }

  public static String print(final AlphaSystem system, final String valueType) {
    String _xblockexpression = null;
    {
      int _size = system.getSystemBodies().size();
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        throw new IllegalArgumentException("[ShowLegacyAlpha] A system with multiple bodies are not supported.");
      }
      SystemBody _get = system.getSystemBodies().get(0);
      final ShowLegacyAlpha.ShowLegacyAlphaForSystem show = new ShowLegacyAlpha.ShowLegacyAlphaForSystem(_get, valueType);
      _xblockexpression = show.doSwitch(system).toString();
    }
    return _xblockexpression;
  }

  public static String print(final SystemBody systemBody) {
    return ShowLegacyAlpha.print(systemBody, ShowLegacyAlpha.defaultValueType);
  }

  public static String print(final SystemBody systemBody, final String valueType) {
    String _xblockexpression = null;
    {
      final ShowLegacyAlpha.ShowLegacyAlphaForSystem show = new ShowLegacyAlpha.ShowLegacyAlphaForSystem(systemBody, valueType);
      _xblockexpression = show.doSwitch(systemBody.getSystem()).toString();
    }
    return _xblockexpression;
  }
}
