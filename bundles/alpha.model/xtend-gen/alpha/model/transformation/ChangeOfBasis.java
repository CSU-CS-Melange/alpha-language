package alpha.model.transformation;

import alpha.model.AlphaSystem;
import alpha.model.DependenceExpression;
import alpha.model.StandardEquation;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AffineFunctionOperations;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

@SuppressWarnings("all")
public class ChangeOfBasis extends AbstractAlphaCompleteVisitor {
  private ChangeOfBasis(final AlphaSystem system, final Variable variable, final ISLMultiAff f) {
    this.CoBfunction = f;
    this.target = variable;
    this.CoBfunctionInverse = AffineFunctionOperations.inverseInContext(this.CoBfunction, variable.getDomain(), null);
  }

  private ISLMultiAff CoBfunction;

  private ISLMultiAff CoBfunctionInverse;

  private Variable target;

  public static void apply(final AlphaSystem system, final Variable variable, final ISLMultiAff f) {
    final ChangeOfBasis CoB = new ChangeOfBasis(system, variable, f);
    system.accept(CoB);
  }

  @Override
  public void inVariable(final Variable variable) {
    boolean _equals = Objects.equal(variable, this.target);
    if (_equals) {
      final ISLSet newDom = variable.getDomain().apply(this.CoBfunction.copy().toMap());
      variable.setDomainExpr(AlphaUserFactory.createJNIDomain(newDom));
    }
  }

  @Override
  public void inStandardEquation(final StandardEquation se) {
    Variable _variable = se.getVariable();
    boolean _equals = Objects.equal(_variable, this.target);
    if (_equals) {
      final DependenceExpression newExpr = AlphaUserFactory.createDependenceExpression(this.CoBfunctionInverse.copy(), se.getExpr());
      se.setExpr(newExpr);
    }
  }

  @Override
  public void outVariableExpression(final VariableExpression ve) {
    Variable _variable = ve.getVariable();
    boolean _equals = Objects.equal(_variable, this.target);
    if (_equals) {
      final DependenceExpression newExpr = AlphaUserFactory.createDependenceExpression(this.CoBfunction.copy());
      EcoreUtil.replace(ve, newExpr);
      newExpr.setExpr(ve);
    }
  }

  @Override
  public void inUseEquation(final UseEquation ue) {
    super.inUseEquation(ue);
  }
}
