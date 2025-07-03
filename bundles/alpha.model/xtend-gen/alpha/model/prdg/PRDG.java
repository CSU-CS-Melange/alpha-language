package alpha.model.prdg;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
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
        return this.domains.copy();
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
      _xblockexpression = this.domains.copy();
    }
    return _xblockexpression;
  }

  /**
   * Collapses all reduction nodes.
   * Useful for simplifying schedule generation
   * when not explicitly scheduling reductions.
   */
  public void inlineReductions() {
    while (IterableExtensions.<PRDGNode>exists(this.nodes, ((Function1<PRDGNode, Boolean>) (PRDGNode it) -> {
      return Boolean.valueOf(it.isReductionNode());
    }))) {
      {
        final Function1<PRDGNode, Boolean> _function = (PRDGNode it) -> {
          return Boolean.valueOf(it.isReductionNode());
        };
        final PRDGNode reduction = IterableExtensions.<PRDGNode>findFirst(this.nodes, _function);
        final Function1<PRDGEdge, Boolean> _function_1 = (PRDGEdge it) -> {
          PRDGNode _dest = it.getDest();
          return Boolean.valueOf(Objects.equal(_dest, reduction));
        };
        final PRDGEdge e1 = IterableExtensions.<PRDGEdge>findFirst(this.edges, _function_1);
        final Function1<PRDGEdge, Boolean> _function_2 = (PRDGEdge it) -> {
          PRDGNode _source = it.getSource();
          return Boolean.valueOf(Objects.equal(_source, reduction));
        };
        final Function1<PRDGEdge, PRDGEdge> _function_3 = (PRDGEdge e2) -> {
          PRDGNode _source = e1.getSource();
          PRDGNode _dest = e2.getDest();
          ISLMap _applyRange = e1.getMap().applyRange(e2.getMap());
          return new PRDGEdge(_source, _dest, _applyRange);
        };
        final Iterable<PRDGEdge> newEdges = IterableExtensions.<PRDGEdge, PRDGEdge>map(IterableExtensions.<PRDGEdge>filter(this.edges, _function_2), _function_3);
        final Function1<PRDGEdge, Boolean> _function_4 = (PRDGEdge it) -> {
          return Boolean.valueOf((Objects.equal(it.getDest(), reduction) || Objects.equal(it.getSource(), reduction)));
        };
        Iterable<PRDGEdge> _reject = IterableExtensions.<PRDGEdge>reject(this.edges, _function_4);
        this.edges = IterableExtensions.<PRDGEdge>toSet(Iterables.<PRDGEdge>concat(_reject, newEdges));
        this.nodes.remove(reduction);
      }
    }
  }

  public ISLUnionMap generateISLPRDG() {
    ISLUnionMap _xblockexpression = null;
    {
      if ((this.islPRDG != null)) {
        return this.islPRDG.copy();
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
      _xblockexpression = this.islPRDG.copy();
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
