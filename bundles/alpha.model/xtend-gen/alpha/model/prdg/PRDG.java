package alpha.model.prdg;

import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import fr.irisa.cairn.jnimap.polylib.PolyLibMatrix;
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.MapExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class PRDG {
  private List<PRDGNode> nodes;

  private List<PRDGEdge> edges;

  private ISLUnionSet domains;

  private ISLUnionMap islPRDG;

  public PRDG() {
    LinkedList<PRDGNode> _linkedList = new LinkedList<PRDGNode>();
    this.nodes = _linkedList;
    LinkedList<PRDGEdge> _linkedList_1 = new LinkedList<PRDGEdge>();
    this.edges = _linkedList_1;
  }

  public PRDGNode getNode(final String name) {
    PRDGNode _xblockexpression = null;
    {
      final Function1<PRDGNode, Boolean> _function = (PRDGNode node) -> {
        return Boolean.valueOf(node.getName().equals(name));
      };
      Iterable<PRDGNode> nodes = IterableExtensions.<PRDGNode>filter(this.nodes, _function);
      PRDGNode _xifexpression = null;
      final Iterable<PRDGNode> _converted_nodes = (Iterable<PRDGNode>)nodes;
      int _length = ((Object[])Conversions.unwrapArray(_converted_nodes, Object.class)).length;
      boolean _greaterThan = (_length > 0);
      if (_greaterThan) {
        final Iterable<PRDGNode> _converted_nodes_1 = (Iterable<PRDGNode>)nodes;
        _xifexpression = ((PRDGNode[])Conversions.unwrapArray(_converted_nodes_1, PRDGNode.class))[0];
      } else {
        _xifexpression = null;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  public List<PRDGNode> getNodes() {
    return this.nodes;
  }

  public List<PRDGEdge> getEdges() {
    return this.edges;
  }

  public List<String> show() {
    List<String> _xblockexpression = null;
    {
      InputOutput.<String>println("Nodes: ");
      final Function1<PRDGNode, String> _function = (PRDGNode node) -> {
        return node.toString();
      };
      InputOutput.<List<String>>println(ListExtensions.<PRDGNode, String>map(this.nodes, _function));
      InputOutput.<String>println("Edges: ");
      final Function1<PRDGEdge, String> _function_1 = (PRDGEdge edge) -> {
        return edge.toString();
      };
      _xblockexpression = InputOutput.<List<String>>println(ListExtensions.<PRDGEdge, String>map(this.edges, _function_1));
    }
    return _xblockexpression;
  }

  public List<PRDGNode> addNodes(final List<PRDGNode> names) {
    return this.nodes = names;
  }

  public boolean addEdge(final PRDGEdge edge) {
    return this.edges.add(edge);
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
      if ((this.domains == null)) {
        this.generateDomains();
      }
      this.islPRDG = ISLMap.buildEmpty(ISLSpace.copySpaceParamsForMap(this.domains.copy().getSpace().copy())).toUnionMap();
      List<PRDGEdge> _edges = this.getEdges();
      for (final PRDGEdge edge : _edges) {
        {
          ISLMultiAff map1 = edge.getFunction().copy();
          ISLSet set = edge.getDomain().copy();
          ISLMap map2 = map1.copy().toMap().simplify().intersectDomain(set.copy());
          if ((map2 == null)) {
            System.out.println(("map1 = " + map1));
            System.out.println(("set = " + set));
            throw new RuntimeException("Problem while intersecting domain");
          }
          if ((edge.getSource().isReductionNode() && edge.getDest().isReductionNode())) {
            map2 = map2.reverse();
          }
          map2 = map2.setTupleName(ISLDimType.isl_dim_out, edge.getDest().getName());
          map2 = map2.setTupleName(ISLDimType.isl_dim_in, edge.getSource().getName());
          this.islPRDG = this.islPRDG.union(map2.copy().toUnionMap());
        }
      }
      _xblockexpression = this.islPRDG;
    }
    return _xblockexpression;
  }

  public static ISLConstraint generateSelfCancelConstraint(final String index, final ISLSpace space, final List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> variable, final HashMap<String, Integer> index_mappings, final int coefficient) {
    ISLConstraint _xblockexpression = null;
    {
      ISLConstraint constraint = ISLConstraint.buildInequality(space);
      for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> dependence : variable) {
        {
          String mu = dependence.getKey();
          Integer dependence_value = dependence.getValue().get(index).getValue();
          if (((dependence_value).intValue() != 0)) {
            constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, 
              (index_mappings.get(mu)).intValue(), 
              (-((dependence_value).intValue() * coefficient)));
          }
        }
      }
      constraint = constraint.setConstant((-1));
      _xblockexpression = constraint;
    }
    return _xblockexpression;
  }

  public boolean respectsScheduleSpace(final String variable, final long[] ls) {
    boolean _xblockexpression = false;
    {
      FeasibleSpace feasibleSpace = new FeasibleSpace(this);
      ISLBasicSet set = feasibleSpace.getSpace().copy();
      HashMap<String, HashMap<String, String>> variables = feasibleSpace.getVariables();
      HashMap<String, Integer> indexMappings = feasibleSpace.getIndexMappings();
      List<String> variableIndices = feasibleSpace.getVariableIndices().get(variable);
      PolyLibMatrix matrix = PolyLibMatrix.createFromLongMatrix(set.toPolyLibArray());
      PolyLibMatrix rays = PolyLibPolyhedron.buildFromConstraints(matrix, 10).builRaysVertices();
      ArrayList<Long> b_row = new ArrayList<Long>();
      final Function2<String, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>, Boolean> _function = (String key, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> value) -> {
        String _key = value.getKey().getKey();
        return Boolean.valueOf(Objects.equal(_key, variable));
      };
      Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> function = IterableExtensions.<Map.Entry<String, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>>>head(MapExtensions.<String, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>>filter(feasibleSpace.getMappings(), _function).entrySet()).getValue();
      List<ISLAff> _affs = function.getKey().getValue().getAffs();
      for (final ISLAff aff : _affs) {
        {
          long value = 0;
          for (int i = 0; (i < ls.length); i++) {
            long _value = value;
            long _get = ls[i];
            long _asLong = aff.getCoefficientVal(ISLDimType.isl_dim_in, i).asLong();
            long _multiply = (_get * _asLong);
            value = (_value + _multiply);
          }
          long _value = value;
          long _constant = aff.getConstant();
          value = (_value + _constant);
          b_row.add(Long.valueOf(value));
        }
      }
      InputOutput.<String>println(("Variables: " + variables));
      for (int row = 0; (row < rays.getNbRows()); row++) {
        if (((rays.getAt(row, (rays.getNbColumns() - 1)) == 0) && (rays.getAt(row, 0) == 1))) {
          long value = 0;
          boolean hasVariable = false;
          for (int i = 0; (i < b_row.size()); i++) {
            {
              Integer _get = indexMappings.get(variables.get(variable).get(variableIndices.get(i)));
              int _plus = ((_get).intValue() + 1);
              long exact = rays.getAt(row, _plus);
              long _value = value;
              Long _get_1 = b_row.get(i);
              long _multiply = ((_get_1).longValue() * exact);
              value = (_value + _multiply);
              if ((exact != 0)) {
                hasVariable = true;
              }
            }
          }
          if ((value != 0)) {
            InputOutput.<String>print("Reuse: ");
            final Consumer<Long> _function_1 = (Long x) -> {
              String _plus = (x + " ");
              InputOutput.<String>print(_plus);
            };
            ((List<Long>)Conversions.doWrapArray(ls)).forEach(_function_1);
            InputOutput.println();
            InputOutput.<String>println(("Value: " + Long.valueOf(value)));
          }
          if (((value >= 0) && hasVariable)) {
            return true;
          }
        }
      }
      _xblockexpression = false;
    }
    return _xblockexpression;
  }
}
