package alpha.targetmapping.scratch;

import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.DependenceExpression;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
public class PRDGBuilder extends AbstractAlphaCompleteVisitor {
  private PRDG prdg;

  private PRDGNode currentNode;

  private Set<Variable> inputs;

  public static PRDG build(final AlphaSystem system) {
    PRDG _xblockexpression = null;
    {
      final PRDGBuilder visitor = new PRDGBuilder();
      system.accept(visitor);
      _xblockexpression = visitor.prdg;
    }
    return _xblockexpression;
  }

  public PRDGBuilder() {
    PRDG _pRDG = new PRDG();
    this.prdg = _pRDG;
    HashSet<Variable> _hashSet = new HashSet<Variable>();
    this.inputs = _hashSet;
  }

  @Override
  public void inAlphaSystem(final AlphaSystem system) {
    this.inputs.addAll(system.getInputs());
    EList<Variable> _outputs = system.getOutputs();
    EList<Variable> _locals = system.getLocals();
    Iterable<Variable> _plus = Iterables.<Variable>concat(_outputs, _locals);
    for (final Variable variable : _plus) {
      this.prdg.addNode(variable);
    }
  }

  @Override
  public void inStandardEquation(final StandardEquation se) {
    this.currentNode = this.prdg.getNode(se.getVariable());
  }

  @Override
  public void outStandardEquation(final StandardEquation se) {
    this.currentNode = null;
  }

  @Override
  public void visitVariableExpression(final VariableExpression ve) {
    boolean _contains = this.inputs.contains(ve.getVariable());
    if (_contains) {
      return;
    }
    EObject _eContainer = ve.eContainer();
    this.variableExpressionRules(ve, ((AlphaExpression) _eContainer));
  }

  protected Boolean _variableExpressionRules(final VariableExpression ve, final DependenceExpression de) {
    boolean _xblockexpression = false;
    {
      Variable _variable = ve.getVariable();
      final PRDGNode node = new PRDGNode(_variable);
      ISLMultiAff _function = de.getFunction();
      ISLSet _contextDomain = de.getContextDomain();
      final PRDGEdge edge = new PRDGEdge(this.currentNode, node, _function, _contextDomain);
      _xblockexpression = this.prdg.addEdge(edge);
    }
    return Boolean.valueOf(_xblockexpression);
  }

  protected Boolean _variableExpressionRules(final VariableExpression ve, final AlphaExpression ae) {
    return null;
  }

  public Boolean variableExpressionRules(final VariableExpression ve, final AlphaExpression de) {
    if (de instanceof DependenceExpression) {
      return _variableExpressionRules(ve, (DependenceExpression)de);
    } else if (de != null) {
      return _variableExpressionRules(ve, de);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(ve, de).toString());
    }
  }
}
