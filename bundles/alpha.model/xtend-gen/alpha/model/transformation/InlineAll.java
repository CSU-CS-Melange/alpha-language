package alpha.model.transformation;

import alpha.model.AlphaSystem;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 * Based on the transformation of the same name in AlphaZ V1.
 * Inlines all variable equations that do not have self-dependences
 * and do not define input or output variables.
 */
@SuppressWarnings("all")
public class InlineAll extends AbstractAlphaCompleteVisitor {
  private boolean selfDependence;

  private Variable currentVar;

  private List<Variable> validVars;

  public static void apply(final AlphaSystem system) {
    boolean canInline = true;
    while (canInline) {
      {
        final InlineAll visitor = new InlineAll(system);
        visitor.accept(system);
        boolean _isEmpty = visitor.validVars.isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          SubstituteByDef.apply(system, visitor.validVars.get(0));
          Normalize.apply(system);
          RemoveUnusedEquations.apply(system);
        } else {
          canInline = false;
        }
      }
    }
  }

  public InlineAll(final AlphaSystem system) {
    ArrayList<Variable> _arrayList = new ArrayList<Variable>();
    this.validVars = _arrayList;
  }

  @Override
  public void inStandardEquation(final StandardEquation se) {
    this.currentVar = se.getVariable();
    this.selfDependence = false;
  }

  @Override
  public void outStandardEquation(final StandardEquation se) {
    if ((((!this.selfDependence) && (!(this.currentVar.isOutput()).booleanValue())) && (!(this.currentVar.isInput()).booleanValue()))) {
      this.validVars.add(this.currentVar);
    }
  }

  @Override
  public void outVariableExpression(final VariableExpression ve) {
    String _name = ve.getVariable().getName();
    String _name_1 = this.currentVar.getName();
    boolean _equals = Objects.equal(_name, _name_1);
    if (_equals) {
      this.selfDependence = true;
    }
  }
}
