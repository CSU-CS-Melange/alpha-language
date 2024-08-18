package alpha.abft;

import alpha.codegen.ProgramPrinter;
import alpha.codegen.isl.PolynomialConverter;
import alpha.loader.AlphaLoader;
import alpha.model.AlphaExpression;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.transformation.reduction.NormalizeReduction;
import alpha.model.util.AShow;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import java.util.Arrays;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class Scratch {
  private static boolean verbose = true;

  public static void main(final String[] args) {
    try {
      final AlphaRoot root = AlphaLoader.loadAlpha("resources/scratch/star3d1r.alpha");
      final AlphaSystem system = root.getSystems().get(0);
      NormalizeReduction.apply(system);
      Scratch.pprint(system, "input:");
      InputOutput.<CharSequence>println(Scratch.computeComplexity(system));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static CharSequence computeComplexity(final AlphaSystem system) {
    CharSequence _xblockexpression = null;
    {
      final SystemBody systemBody = system.getSystemBodies().get(0);
      final Function1<Variable, Boolean> _function = (Variable it) -> {
        String _name = it.getName();
        return Boolean.valueOf(Objects.equal(_name, "Y"));
      };
      final Variable stencilVar = IterableExtensions.<Variable>findFirst(system.getVariables(), _function);
      final ISLPWQPolynomial stencilVarPoints = Scratch.computeComplexity(systemBody, stencilVar);
      final Function1<StandardEquation, ISLPWQPolynomial> _function_1 = (StandardEquation it) -> {
        return Scratch.computeComplexity(systemBody, it.getVariable());
      };
      final Function2<ISLPWQPolynomial, ISLPWQPolynomial, ISLPWQPolynomial> _function_2 = (ISLPWQPolynomial p1, ISLPWQPolynomial p2) -> {
        return p1.add(p2);
      };
      final ISLPWQPolynomial checksumPoints = IterableExtensions.<ISLPWQPolynomial>reduce(ListExtensions.<StandardEquation, ISLPWQPolynomial>map(systemBody.getStandardEquations(), _function_1), _function_2);
      final String versionStr = "v3";
      final String stencilVarCard = ProgramPrinter.printExpr(PolynomialConverter.convert(stencilVarPoints)).toString().replaceAll("([-]*[0-9][0-9]*)", "((double)$1)");
      final String checksumsCard = ProgramPrinter.printExpr(PolynomialConverter.convert(checksumPoints)).toString().replaceAll("([-]*[0-9][0-9]*)", "((double)$1)");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#undef ceild");
      _builder.newLine();
      _builder.append("#undef floord");
      _builder.newLine();
      _builder.append("#define ceild(n,d)  (double)ceil(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.append("#define floord(n,d) (double)floor(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.newLine();
      _builder.append("double ");
      String _name = stencilVar.getName();
      _builder.append(_name);
      _builder.append("_card = ");
      _builder.append(stencilVarCard);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("double total_card = ");
      _builder.append(checksumsCard);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("double expected_overhead = total_card / ");
      String _name_1 = stencilVar.getName();
      _builder.append(_name_1);
      _builder.append("_card;");
      _builder.newLineIfNotEmpty();
      _builder.append("printf(\"");
      _builder.append(versionStr);
      _builder.append("(");
      String _name_2 = stencilVar.getName();
      _builder.append(_name_2);
      _builder.append(",C,overhead): %0.0lf,%0.0lf,%0.2lf\\n\", ");
      String _name_3 = stencilVar.getName();
      _builder.append(_name_3);
      _builder.append("_card, total_card, expected_overhead);");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("#undef ceild");
      _builder.newLine();
      _builder.append("#undef floord");
      _builder.newLine();
      _builder.append("#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.append("#define floord(n,d) (int)floor(((double)(n))/((double)(d)))");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  public static ISLPWQPolynomial computeComplexity(final SystemBody body, final Variable variable) {
    ISLPWQPolynomial _xblockexpression = null;
    {
      final Function1<StandardEquation, Boolean> _function = (StandardEquation e) -> {
        Variable _variable = e.getVariable();
        return Boolean.valueOf(Objects.equal(_variable, variable));
      };
      final StandardEquation eq = IterableExtensions.<StandardEquation>findFirst(body.getStandardEquations(), _function);
      _xblockexpression = Scratch.computeComplexity(body, variable, eq.getExpr());
    }
    return _xblockexpression;
  }

  protected static ISLPWQPolynomial _computeComplexity(final SystemBody body, final Variable variable, final ReduceExpression re) {
    return Scratch.computeComplexity(body, variable, re.getBody());
  }

  protected static ISLPWQPolynomial _computeComplexity(final SystemBody body, final Variable variable, final AlphaExpression ae) {
    return BarvinokBindings.card(ae.getContextDomain());
  }

  public static String pprint(final AlphaVisitable av, final String msg) {
    String _xifexpression = null;
    if (Scratch.verbose) {
      String _xblockexpression = null;
      {
        InputOutput.<String>println(msg);
        _xblockexpression = InputOutput.<String>println(AShow.print(av));
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }

  public static ISLPWQPolynomial computeComplexity(final SystemBody body, final Variable variable, final AlphaExpression re) {
    if (re instanceof ReduceExpression) {
      return _computeComplexity(body, variable, (ReduceExpression)re);
    } else if (re != null) {
      return _computeComplexity(body, variable, re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(body, variable, re).toString());
    }
  }
}
