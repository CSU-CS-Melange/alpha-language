package alpha.model.transformation;

import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaUtil;
import com.google.common.base.Objects;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Replaces a VariableExpression with the definition of the corresponding StandardEquation.
 * Variables defined through UseEquations cannot be substituted.
 * 
 * When the target equation is specified, the substitution only happens in the definition
 * of the target. Otherwise, all occurrences in an AlphaSystem (or SystemBody) are substituted.
 * 
 * Given
 *  X = Y;
 *  Y = E;
 * substituting Y in X gives:
 *  X = D : E
 * where D is the domain of Y.
 */
@SuppressWarnings("all")
public class SubstituteByDef extends AbstractAlphaCompleteVisitor {
  protected StandardEquation substituteEquation;

  private SubstituteByDef(final StandardEquation substituteEquation) {
    this.substituteEquation = substituteEquation;
  }

  public static void apply(final AlphaSystem system, final Variable substituteVar) {
    final Consumer<SystemBody> _function = (SystemBody body) -> {
      SubstituteByDef.apply(body, substituteVar);
    };
    system.getSystemBodies().forEach(_function);
  }

  public static void apply(final AlphaSystem system, final StandardEquation targetEq, final Variable substituteVar) {
    final Consumer<SystemBody> _function = (SystemBody body) -> {
      SubstituteByDef.apply(targetEq, substituteVar);
    };
    system.getSystemBodies().forEach(_function);
  }

  public static void apply(final SystemBody body, final Variable substituteVar) {
    final StandardEquation subEq = body.getStandardEquation(substituteVar);
    final SubstituteByDef visitor = new SubstituteByDef(subEq);
    visitor.accept(body);
  }

  public static void apply(final StandardEquation targetEq, final Variable substituteVar) {
    final SystemBody body = AlphaUtil.getContainerSystemBody(targetEq);
    final StandardEquation subEq = body.getStandardEquation(substituteVar);
    final SubstituteByDef visitor = new SubstituteByDef(subEq);
    visitor.accept(targetEq);
  }

  public static void apply(final AlphaExpression targetExpr, final Variable substituteVar) {
    final SystemBody body = AlphaUtil.getContainerSystemBody(targetExpr);
    final StandardEquation subEq = body.getStandardEquation(substituteVar);
    final SubstituteByDef visitor = new SubstituteByDef(subEq);
    visitor.accept(targetExpr);
  }

  @Override
  public void visitUseEquation(final UseEquation ue) {
    return;
  }

  @Override
  public void outVariableExpression(final VariableExpression ve) {
    try {
      Variable _variable = ve.getVariable();
      Variable _variable_1 = this.substituteEquation.getVariable();
      final boolean b = Objects.equal(_variable, _variable_1);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        InputOutput.println();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    Variable _variable = ve.getVariable();
    Variable _variable_1 = this.substituteEquation.getVariable();
    boolean _equals = Objects.equal(_variable, _variable_1);
    if (_equals) {
      final RestrictExpression substituteExpr = AlphaUserFactory.createRestrictExpression(this.substituteEquation.getVariable().getDomain(), EcoreUtil.<AlphaExpression>copy(this.substituteEquation.getExpr()));
      EcoreUtil.replace(ve, substituteExpr);
      AlphaInternalStateConstructor.recomputeContextDomain(substituteExpr);
    }
  }
}
