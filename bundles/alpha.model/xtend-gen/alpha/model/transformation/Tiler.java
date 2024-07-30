package alpha.model.transformation;

import alpha.model.AlphaSystem;
import alpha.model.DependenceExpression;
import alpha.model.StandardEquation;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

@SuppressWarnings("all")
public class Tiler extends AbstractAlphaCompleteVisitor {
  private Variable target;

  private int[] tileSizes;

  private Tiler(final AlphaSystem system, final Variable variable, final int[] tileSizes) {
    this.target = variable;
    this.tileSizes = tileSizes;
  }

  public static void apply(final AlphaSystem system, final Variable variable, final int[] tileSizes) {
  }

  @Override
  public void inVariable(final Variable variable) {
    boolean _notEquals = (!Objects.equal(variable, this.target));
    if (_notEquals) {
      return;
    }
    final ISLSet newDom = ((ISLSet) null);
    variable.setDomainExpr(AlphaUserFactory.createJNIDomain(newDom));
  }

  @Override
  public void inStandardEquation(final StandardEquation se) {
    Variable _variable = se.getVariable();
    boolean _equals = Objects.equal(_variable, this.target);
    if (_equals) {
      final DependenceExpression newExpr = ((DependenceExpression) null);
      se.setExpr(newExpr);
    }
  }

  @Override
  public void outVariableExpression(final VariableExpression ve) {
    Variable _variable = ve.getVariable();
    boolean _equals = Objects.equal(_variable, this.target);
    if (_equals) {
      final DependenceExpression newExpr = ((DependenceExpression) null);
      EcoreUtil.replace(ve, newExpr);
      newExpr.setExpr(ve);
    }
  }

  @Override
  public void inUseEquation(final UseEquation ue) {
    super.inUseEquation(ue);
  }
}
