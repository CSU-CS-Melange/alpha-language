/**
 * generated by Xtext 2.13.0
 */
package alpha.model.ui.labeling;

import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.ArgReduceExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryCalculatorExpression;
import alpha.model.BinaryExpression;
import alpha.model.CalculatorExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.ExternalArgReduceExpression;
import alpha.model.ExternalMultiArgExpression;
import alpha.model.ExternalReduceExpression;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.JNIFunctionInArrayNotation;
import alpha.model.MultiArgExpression;
import alpha.model.PolyhedralObject;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.SelectExpression;
import alpha.model.StandardEquation;
import alpha.model.UnaryExpression;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import com.google.inject.Inject;
import fr.irisa.cairn.jnimap.isl.jni.JNIISLMultiAff;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
@SuppressWarnings("all")
public class AlphaLabelProvider extends DefaultEObjectLabelProvider {
  @Inject
  public AlphaLabelProvider(final AdapterFactoryLabelProvider delegate) {
    super(delegate);
  }
  
  public String text(final Variable v) {
    String _name = v.getName();
    String _plus = (_name + " : ");
    String _string = v.getDomain().toString();
    return (_plus + _string);
  }
  
  public String text(final AlphaSystem system) {
    String _name = system.getName();
    String _plus = (_name + " ");
    String _string = system.getParameterDomain().toString();
    return (_plus + _string);
  }
  
  public String text(final PolyhedralObject pobj) {
    String _name = pobj.getName();
    String _plus = (_name + " = ");
    String _string = pobj.getISLObject().toString();
    return (_plus + _string);
  }
  
  public String text(final BinaryCalculatorExpression bce) {
    return bce.getOperator().getLiteral();
  }
  
  public String text(final StandardEquation seq) {
    String _name = seq.getVariable().getName();
    return (_name + " = ");
  }
  
  public String text(final RestrictExpression re) {
    String _xifexpression = null;
    CalculatorExpression _domainExpr = re.getDomainExpr();
    boolean _tripleNotEquals = (_domainExpr != null);
    if (_tripleNotEquals) {
      _xifexpression = re.getDomainExpr().plainToString();
    } else {
      _xifexpression = "restrict with null domain";
    }
    return _xifexpression;
  }
  
  public String text(final AutoRestrictExpression are) {
    return "auto";
  }
  
  public String text(final CaseExpression cexpr) {
    String _xifexpression = null;
    if (((cexpr.getName() != null) && (cexpr.getName().length() > 0))) {
      String _xblockexpression = null;
      {
        String _name = cexpr.getName();
        /* ("case (" + _name); */
        _xblockexpression = ")";
      }
      _xifexpression = _xblockexpression;
    } else {
      _xifexpression = "case";
    }
    return _xifexpression;
  }
  
  public String text(final IfExpression ifexpr) {
    return "if-then-else";
  }
  
  public String text(final DependenceExpression dep) {
    String _xifexpression = null;
    JNIISLMultiAff _function = dep.getFunction();
    if ((_function instanceof JNIFunctionInArrayNotation)) {
      AlphaExpression _expr = dep.getExpr();
      String _name = ((VariableExpression) _expr).getVariable().getName();
      String _plainToString = dep.getFunctionExpr().plainToString();
      _xifexpression = (_name + _plainToString);
    } else {
      String _xifexpression_1 = null;
      AlphaExpression _expr_1 = dep.getExpr();
      if ((_expr_1 instanceof VariableExpression)) {
        String _plainToString_1 = dep.getFunctionExpr().plainToString();
        String _plus = (_plainToString_1 + " @ ");
        AlphaExpression _expr_2 = dep.getExpr();
        String _name_1 = ((VariableExpression) _expr_2).getVariable().getName();
        _xifexpression_1 = (_plus + _name_1);
      } else {
        _xifexpression_1 = dep.getFunctionExpr().plainToString();
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  public String text(final ReduceExpression reduce) {
    String _literal = reduce.getOperator().getLiteral();
    return ("reduce " + _literal);
  }
  
  public String text(final ExternalReduceExpression reduce) {
    String _name = reduce.getExternalFunction().getName();
    return ("reduce " + _name);
  }
  
  public String text(final ArgReduceExpression reduce) {
    String _literal = reduce.getOperator().getLiteral();
    return ("argreduce " + _literal);
  }
  
  public String text(final ExternalArgReduceExpression reduce) {
    String _name = reduce.getExternalFunction().getName();
    return ("argreduce " + _name);
  }
  
  public String text(final UnaryExpression ue) {
    return ue.getOperator().getLiteral();
  }
  
  public String text(final BinaryExpression be) {
    return be.getOperator().getLiteral();
  }
  
  public String text(final MultiArgExpression mae) {
    return mae.getOperator().getLiteral();
  }
  
  public String text(final ExternalMultiArgExpression emae) {
    return emae.getExternalFunction().getName();
  }
  
  public String text(final SelectExpression se) {
    return "select";
  }
  
  public String text(final IndexExpression ie) {
    String _plainToString = ie.getFunctionExpr().plainToString();
    return ("val " + _plainToString);
  }
  
  public String text(final VariableExpression ve) {
    return ve.getVariable().getName();
  }
  
  public ConstantExpression text(final ConstantExpression ce) {
    return ce;
  }
}
