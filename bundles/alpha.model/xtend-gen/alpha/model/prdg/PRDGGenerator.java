package alpha.model.prdg;

import alpha.model.AlphaSystem;
import alpha.model.DependenceExpression;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.transformation.Normalize;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaUtil;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.List;
import java.util.Stack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PRDGGenerator extends AbstractAlphaCompleteVisitor {
  private PRDG prdg = new PRDG();

  private Stack<PRDGNode> sources;

  private Stack<ISLSet> domains;

  private Stack<ISLMultiAff> functions;

  private int numberReductions;

  private final boolean includeInputs;

  public static PRDG apply(final AlphaSystem system) {
    PRDG _xblockexpression = null;
    {
      final PRDGGenerator generator = new PRDGGenerator(false);
      final AlphaSystem copy = AlphaUtil.<AlphaSystem>copyAE(system);
      Normalize.apply(copy);
      copy.accept(generator);
      _xblockexpression = generator.prdg;
    }
    return _xblockexpression;
  }

  public PRDGGenerator(final boolean includeInputs) {
    this.includeInputs = includeInputs;
    Stack<ISLMultiAff> _stack = new Stack<ISLMultiAff>();
    this.functions = _stack;
    Stack<ISLSet> _stack_1 = new Stack<ISLSet>();
    this.domains = _stack_1;
    Stack<PRDGNode> _stack_2 = new Stack<PRDGNode>();
    this.sources = _stack_2;
  }

  @Override
  public void inAlphaSystem(final AlphaSystem system) {
    EList<Variable> variables = system.getVariables();
    final Function1<Variable, Boolean> _function = (Variable v) -> {
      return Boolean.valueOf(((!(v.isInput()).booleanValue()) || this.includeInputs));
    };
    final Function1<Variable, PRDGNode> _function_1 = (Variable v) -> {
      String _name = v.getName();
      ISLSet _copy = v.getDomain().copy();
      return new PRDGNode(_name, _copy);
    };
    this.prdg.addNodes(IterableExtensions.<PRDGNode>toList(IterableExtensions.<Variable, PRDGNode>map(IterableExtensions.<Variable>filter(variables, _function), _function_1)));
  }

  @Override
  public void inStandardEquation(final StandardEquation standardEquation) {
    this.functions.push(ISLMultiAff.buildIdentity(standardEquation.getVariable().getDomain().copy().identity().getSpace()));
    String _name = standardEquation.getVariable().getName();
    ISLSet _copy = standardEquation.getVariable().getDomain().copy();
    PRDGNode _pRDGNode = new PRDGNode(_name, _copy);
    this.sources.push(_pRDGNode);
    this.numberReductions = 0;
  }

  @Override
  public void outStandardEquation(final StandardEquation se) {
    this.sources.pop();
    this.functions.pop();
    this.numberReductions = 0;
  }

  @Override
  public void inDependenceExpression(final DependenceExpression dependenceExpression) {
    this.functions.push(dependenceExpression.getFunction().copy().pullback(this.functions.peek().copy()));
    this.domains.push(dependenceExpression.getContextDomain().copy());
  }

  @Override
  public void outDependenceExpression(final DependenceExpression dependenceExpression) {
    this.functions.pop();
    this.domains.pop();
  }

  @Override
  public void visitVariableExpression(final VariableExpression ve) {
    String _name = ve.getVariable().getName();
    ISLSet _domain = ve.getVariable().getDomain();
    PRDGNode target = new PRDGNode(_name, _domain);
    ISLSet _xifexpression = null;
    boolean _empty = this.domains.empty();
    boolean _not = (!_empty);
    if (_not) {
      _xifexpression = this.domains.peek().copy();
    } else {
      _xifexpression = ve.getContextDomain().copy();
    }
    final ISLSet dom = _xifexpression;
    final ISLMultiAff fun = this.functions.peek().copy();
    PRDGNode _peek = this.sources.peek();
    ISLSet _copy = dom.copy();
    final PRDGEdge edge = new PRDGEdge(_peek, target, _copy, fun);
    this.prdg.addEdge(edge);
  }

  @Override
  public void inReduceExpression(final ReduceExpression reduceExpression) {
    String _name = this.sources.peek().getName();
    String _plus = (_name + "_reduce");
    String _plus_1 = (_plus + Integer.valueOf(this.numberReductions));
    String reductionName = (_plus_1 + "_result");
    String _name_1 = this.sources.peek().getName();
    String _plus_2 = (_name_1 + "_reduce");
    String _plus_3 = (_plus_2 + Integer.valueOf(this.numberReductions));
    final String bodyName = (_plus_3 + "_body");
    this.numberReductions++;
    List<PRDGNode> _nodes = this.prdg.getNodes();
    ISLSet _copy = reduceExpression.getContextDomain().copy();
    PRDGNode _pRDGNode = new PRDGNode(reductionName, _copy, true);
    _nodes.add(_pRDGNode);
    final ISLMultiAff useToRes = this.functions.peek().copy();
    ISLSet _xifexpression = null;
    boolean _empty = this.domains.empty();
    boolean _not = (!_empty);
    if (_not) {
      _xifexpression = this.domains.peek().copy();
    } else {
      _xifexpression = reduceExpression.getContextDomain().copy();
    }
    final ISLSet dom = _xifexpression;
    PRDGNode _peek = this.sources.peek();
    PRDGNode _node = this.prdg.getNode(reductionName);
    PRDGEdge _pRDGEdge = new PRDGEdge(_peek, _node, dom, useToRes);
    this.prdg.addEdge(_pRDGEdge);
    List<PRDGNode> _nodes_1 = this.prdg.getNodes();
    ISLSet _copy_1 = reduceExpression.getBody().getContextDomain().copy();
    PRDGNode _pRDGNode_1 = new PRDGNode(bodyName, _copy_1, true);
    _nodes_1.add(_pRDGNode_1);
    final ISLMultiAff resToBody = reduceExpression.getProjection().copy();
    PRDGNode _node_1 = this.prdg.getNode(reductionName);
    PRDGNode _node_2 = this.prdg.getNode(bodyName);
    ISLSet _copy_2 = reduceExpression.getBody().getContextDomain().copy();
    PRDGEdge _pRDGEdge_1 = new PRDGEdge(_node_1, _node_2, _copy_2, resToBody);
    this.prdg.addEdge(_pRDGEdge_1);
    this.sources.push(this.prdg.getNode(bodyName));
    this.functions.push(ISLMultiAff.buildIdentity(reduceExpression.getBody().getContextDomain().copy().identity().getSpace()));
  }

  @Override
  public void outReduceExpression(final ReduceExpression reduceExpression) {
    this.sources.pop();
    this.functions.pop();
  }
}
