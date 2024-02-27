package alpha.model.transformation;

import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaVisitable;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.issue.AlphaIssue;
import alpha.model.util.AlphaUtil;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;

@SuppressWarnings("all")
public class RaiseDependenceAndIsolate extends RaiseDependence {
  public static void apply(final AlphaExpressionVisitable visitable) {
    new RaiseDependenceAndIsolate().accept(visitable);
  }

  public static void apply(final AlphaVisitable av) {
    new RaiseDependenceAndIsolate().accept(av);
  }

  @Override
  public void outReduceExpression(final ReduceExpression re) {
    this.reduceExpressionRules(re, re.getBody());
  }

  protected List<AlphaIssue> _reduceExpressionRules(final ReduceExpression re, final DependenceExpression de) {
    List<AlphaIssue> _xblockexpression = null;
    {
      final ISLSet domain = de.getExpr().getContextDomain().computeDivs();
      Equation _containerEquation = AlphaUtil.getContainerEquation(re);
      final String varName = ((StandardEquation) _containerEquation).getVariable().getName();
      final Variable variable = AlphaUserFactory.createVariable((varName + "_body"), domain.copy());
      EList<Variable> _locals = AlphaUtil.getContainerSystem(re).getLocals();
      _locals.add(variable);
      final StandardEquation eq = AlphaUserFactory.createStandardEquation(variable, de.getExpr());
      EList<Equation> _equations = AlphaUtil.getContainerSystemBody(re).getEquations();
      _equations.add(eq);
      final VariableExpression ve = AlphaUserFactory.createVariableExpression(variable);
      de.setExpr(ve);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(AlphaUtil.getContainerSystemBody(re));
    }
    return _xblockexpression;
  }

  protected List<AlphaIssue> _reduceExpressionRules(final ReduceExpression re, final AlphaExpression ae) {
    return null;
  }

  public List<AlphaIssue> reduceExpressionRules(final ReduceExpression re, final AlphaExpression de) {
    if (de instanceof DependenceExpression) {
      return _reduceExpressionRules(re, (DependenceExpression)de);
    } else if (de != null) {
      return _reduceExpressionRules(re, de);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re, de).toString());
    }
  }
}
