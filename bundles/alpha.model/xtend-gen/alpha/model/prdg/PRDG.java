package alpha.model.prdg;

import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PRDG {
  private Set<PRDGNode> nodes;

  private Set<PRDGEdge> edges;

  private ISLUnionSet domains;

  private ISLUnionMap islPRDG;

  public PRDG() {
    HashSet<PRDGNode> _hashSet = new HashSet<PRDGNode>();
    this.nodes = _hashSet;
    HashSet<PRDGEdge> _hashSet_1 = new HashSet<PRDGEdge>();
    this.edges = _hashSet_1;
  }

  public PRDGNode getNode(final String name) {
    final Function1<PRDGNode, Boolean> _function = (PRDGNode it) -> {
      return Boolean.valueOf(it.getName().equals(name));
    };
    return IterableExtensions.<PRDGNode>findFirst(this.nodes, _function);
  }

  public Set<PRDGNode> getNodes() {
    return this.nodes;
  }

  public Set<PRDGEdge> getEdges() {
    return this.edges;
  }

  public boolean addNode(final PRDGNode node) {
    boolean _xifexpression = false;
    boolean _contains = this.nodes.contains(node);
    boolean _not = (!_contains);
    if (_not) {
      _xifexpression = this.nodes.add(node);
    }
    return _xifexpression;
  }

  public Set<PRDGNode> setNodes(final Set<PRDGNode> nodes) {
    return this.nodes = nodes;
  }

  public boolean addEdge(final PRDGEdge edge) {
    boolean _xifexpression = false;
    boolean _contains = this.edges.contains(edge);
    boolean _not = (!_contains);
    if (_not) {
      _xifexpression = this.edges.add(edge);
    }
    return _xifexpression;
  }

  public Set<PRDGEdge> setEdges(final Set<PRDGEdge> edges) {
    return this.edges = edges;
  }

  public ISLUnionSet generateDomains() {
    ISLUnionSet _xblockexpression = null;
    {
      if ((this.domains != null)) {
        return this.domains;
      }
      for (final PRDGNode node : this.nodes) {
        {
          ISLSet domain = node.getDomain().copy();
          domain = domain.setTupleName(node.getName());
          if ((this.domains == null)) {
            this.domains = domain.copy().toUnionSet();
          } else {
            this.domains = this.domains.copy().union(domain.toUnionSet());
          }
        }
      }
      if ((this.domains == null)) {
        throw new NullPointerException();
      }
      _xblockexpression = this.domains;
    }
    return _xblockexpression;
  }

  public ISLUnionMap generateISLPRDG() {
    ISLUnionMap _xblockexpression = null;
    {
      if ((this.islPRDG != null)) {
        return this.islPRDG;
      }
      if ((this.domains != null)) {
        this.generateDomains();
      }
      this.islPRDG = ISLMap.buildEmpty(ISLSpace.copySpaceParamsForMap(this.domains.getSpace().copy())).toUnionMap();
      Set<PRDGEdge> _edges = this.getEdges();
      for (final PRDGEdge edge : _edges) {
        {
          ISLMap map = edge.getMap();
          map = map.setTupleName(ISLDimType.isl_dim_out, edge.getDest().getName());
          map = map.setTupleName(ISLDimType.isl_dim_in, edge.getSource().getName());
          this.islPRDG = this.islPRDG.union(map.copy().toUnionMap());
        }
      }
      _xblockexpression = this.islPRDG;
    }
    return _xblockexpression;
  }

  @Override
  public boolean equals(final Object other) {
    boolean _xifexpression = false;
    if ((other instanceof PRDG)) {
      _xifexpression = (this.nodes.equals(((PRDG)other).getNodes()) && this.edges.equals(((PRDG)other).getEdges()));
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }

  @Override
  public int hashCode() {
    int _hashCode = this.nodes.hashCode();
    int _hashCode_1 = this.edges.hashCode();
    int _multiply = (37 * _hashCode_1);
    return (_hashCode + _multiply);
  }

  @Override
  public String toString() {
    String _join = IterableExtensions.join(this.nodes, "\n\t");
    String _plus = ("Nodes: \n\t" + _join);
    String _plus_1 = (_plus + "\nEdges: \n\t");
    String _join_1 = IterableExtensions.join(this.edges, "\n\t");
    return (_plus_1 + _join_1);
  }
}
