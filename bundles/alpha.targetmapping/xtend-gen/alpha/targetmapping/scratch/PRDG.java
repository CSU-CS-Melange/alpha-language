package alpha.targetmapping.scratch;

import alpha.model.Variable;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class PRDG {
  private List<PRDGEdge> edges;

  private Map<Variable, PRDGNode> nodes;

  public PRDG() {
    ArrayList<PRDGEdge> _arrayList = new ArrayList<PRDGEdge>();
    this.edges = _arrayList;
    HashMap<Variable, PRDGNode> _hashMap = new HashMap<Variable, PRDGNode>();
    this.nodes = _hashMap;
  }

  public PRDGNode addNode(final Variable variable) {
    PRDGNode _pRDGNode = new PRDGNode(variable);
    return this.addNode(_pRDGNode);
  }

  public PRDGNode addNode(final PRDGNode node) {
    return this.nodes.put(node.getVariable(), node);
  }

  public PRDGNode getNode(final Variable variable) {
    return this.nodes.get(variable);
  }

  public boolean addEdge(final PRDGEdge edge) {
    return this.edges.add(edge);
  }

  public ISLUnionSet getDomain() {
    final Function1<PRDGNode, ISLUnionSet> _function = (PRDGNode it) -> {
      return it.getDomain().copy().toUnionSet();
    };
    final Function2<ISLUnionSet, ISLUnionSet, ISLUnionSet> _function_1 = (ISLUnionSet v0, ISLUnionSet v1) -> {
      return v0.copy().union(v1);
    };
    return IterableExtensions.<ISLUnionSet>reduce(IterableExtensions.<PRDGNode, ISLUnionSet>map(this.nodes.values(), _function), _function_1);
  }

  public ISLUnionMap getDependencies() {
    final Function1<PRDGEdge, ISLUnionMap> _function = (PRDGEdge it) -> {
      return it.getMap().copy().toUnionMap();
    };
    final Function2<ISLUnionMap, ISLUnionMap, ISLUnionMap> _function_1 = (ISLUnionMap v0, ISLUnionMap v1) -> {
      return v0.copy().union(v1);
    };
    return IterableExtensions.<ISLUnionMap>reduce(ListExtensions.<PRDGEdge, ISLUnionMap>map(this.edges, _function), _function_1);
  }

  public ISLSchedule computeSchedule() {
    return this.computeSchedule(0);
  }

  public ISLSchedule computeSchedule(final int algo) {
    return ISLSchedule.computeSchedule(this.getDomain(), this.getDependencies(), algo);
  }

  public String prettyPrint() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("nodes = [");
    _builder.newLine();
    _builder.append("  ");
    String _join = IterableExtensions.join(this.nodes.values(), ",\n");
    _builder.append(_join, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("],");
    _builder.newLine();
    _builder.append("edges = [");
    _builder.newLine();
    _builder.append("  ");
    String _join_1 = IterableExtensions.join(this.edges, ",\n");
    _builder.append(_join_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("]");
    _builder.newLine();
    return _builder.toString();
  }
}
