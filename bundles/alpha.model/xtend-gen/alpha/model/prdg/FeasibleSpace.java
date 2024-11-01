package alpha.model.prdg;

import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.polylib.PolyLibMatrix;
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron;
import fr.irisa.cairn.jnimap.polylib.PolylibPrettyPrinter;
import java.util.HashMap;
import java.util.List;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class FeasibleSpace {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private HashMap<String, HashMap<String, String>> variables;

  @Accessors(AccessorType.PUBLIC_GETTER)
  private HashMap<String, List<String>> variableIndices;

  @Accessors(AccessorType.PUBLIC_GETTER)
  private HashMap<String, Integer> indexMappings;

  @Accessors(AccessorType.PUBLIC_GETTER)
  private HashMap<String, Pair<Pair<String, Pair<PolyLibPolyhedron, PolyLibMatrix>>, List<Pair<String, PolyLibMatrix>>>> mappings;

  private PolyLibPolyhedron space;

  public FeasibleSpace(final PRDG prdg) {
    this.variables = CollectionLiterals.<String, HashMap<String, String>>newHashMap();
    this.variableIndices = CollectionLiterals.<String, List<String>>newHashMap();
    this.indexMappings = CollectionLiterals.<String, Integer>newHashMap();
    this.mappings = CollectionLiterals.<String, Pair<Pair<String, Pair<PolyLibPolyhedron, PolyLibMatrix>>, List<Pair<String, PolyLibMatrix>>>>newHashMap();
    final Function1<PRDGNode, Boolean> _function = (PRDGNode node) -> {
      return Boolean.valueOf((!(node.getName().contains("_body") || node.getName().contains("_result"))));
    };
    final Function2<Integer, PRDGNode, Integer> _function_1 = (Integer dims, PRDGNode node) -> {
      int _nbIndices = node.getDomain().copy().getNbIndices();
      int _plus = ((dims).intValue() + _nbIndices);
      int _nbParams = node.getDomain().copy().getNbParams();
      int _plus_1 = (_plus + _nbParams);
      return Integer.valueOf((_plus_1 + 1));
    };
    Integer numberDimensions = IterableExtensions.<PRDGNode, Integer>fold(IterableExtensions.<PRDGNode>filter(prdg.getNodes(), _function), Integer.valueOf(0), _function_1);
    final Function1<PRDGNode, Boolean> _function_2 = (PRDGNode node) -> {
      return Boolean.valueOf((!(node.getName().contains("_body") || node.getName().contains("_result"))));
    };
    final Function1<PRDGNode, Pair<String, Integer>> _function_3 = (PRDGNode node) -> {
      String _name = node.getName();
      int _nbIndices = node.getDomain().copy().getNbIndices();
      int _nbParams = node.getDomain().copy().getNbParams();
      int _plus = (_nbIndices + _nbParams);
      int _plus_1 = (_plus + 1);
      return new Pair<String, Integer>(_name, Integer.valueOf(_plus_1));
    };
    Iterable<Pair<String, Integer>> _map = IterableExtensions.<PRDGNode, Pair<String, Integer>>map(IterableExtensions.<PRDGNode>filter(prdg.getNodes(), _function_2), _function_3);
    HashMap<String, Integer> _newHashMap = CollectionLiterals.<String, Integer>newHashMap();
    Pair<HashMap<String, Integer>, Integer> _pair = new Pair<HashMap<String, Integer>, Integer>(_newHashMap, Integer.valueOf(0));
    final Function2<Pair<HashMap<String, Integer>, Integer>, Pair<String, Integer>, Pair<HashMap<String, Integer>, Integer>> _function_4 = (Pair<HashMap<String, Integer>, Integer> map, Pair<String, Integer> dep) -> {
      Pair<HashMap<String, Integer>, Integer> _xblockexpression = null;
      {
        map.getKey().put(dep.getKey(), map.getValue());
        HashMap<String, Integer> _key = map.getKey();
        Integer _value = map.getValue();
        Integer _value_1 = dep.getValue();
        int _plus = ((_value).intValue() + (_value_1).intValue());
        _xblockexpression = new Pair<HashMap<String, Integer>, Integer>(_key, Integer.valueOf(_plus));
      }
      return _xblockexpression;
    };
    this.indexMappings = IterableExtensions.<Pair<String, Integer>, Pair<HashMap<String, Integer>, Integer>>fold(_map, _pair, _function_4).getKey();
    InputOutput.<String>println(("Number of dimensions: " + numberDimensions));
    InputOutput.<String>println(("Variables: " + this.indexMappings));
    this.space = PolyLibPolyhedron.buildUniversePolyedron((numberDimensions).intValue());
    List<PRDGEdge> _edges = prdg.getEdges();
    String _plus = ("Edges: " + _edges);
    InputOutput.<String>println(_plus);
    final Function1<PRDGEdge, Boolean> _function_5 = (PRDGEdge edge) -> {
      return Boolean.valueOf(edge.getSource().getName().contains("_body"));
    };
    final Function1<PRDGEdge, List<Dependence>> _function_6 = (PRDGEdge edge) -> {
      final Function1<PRDGEdge, Boolean> _function_7 = (PRDGEdge x) -> {
        String _name = x.getDest().getName();
        final Function1<PRDGEdge, Boolean> _function_8 = (PRDGEdge n) -> {
          String _name_1 = n.getDest().getName();
          String _name_2 = edge.getSource().getName();
          return Boolean.valueOf(Objects.equal(_name_1, _name_2));
        };
        String _name_1 = IterableExtensions.<PRDGEdge>findFirst(prdg.getEdges(), _function_8).getSource().getName();
        return Boolean.valueOf(Objects.equal(_name, _name_1));
      };
      final Function1<PRDGEdge, Boolean> _function_8 = (PRDGEdge x) -> {
        String _name = x.getDest().getName();
        String _name_1 = edge.getSource().getName();
        return Boolean.valueOf(Objects.equal(_name, _name_1));
      };
      return Dependence.buildReductionDependence(edge, 
        IterableExtensions.<PRDGEdge>toList(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function_7)), 
        IterableExtensions.<PRDGEdge>toList(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function_8)));
    };
    List<Dependence> dependences = IterableExtensions.<Dependence>toList(IterableExtensions.<PRDGEdge, Dependence>flatMap(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function_5), _function_6));
    final Function1<PRDGEdge, Boolean> _function_7 = (PRDGEdge edge) -> {
      boolean _isReductionEdge = FeasibleSpace.isReductionEdge(edge);
      return Boolean.valueOf((!_isReductionEdge));
    };
    final Function2<List<Dependence>, PRDGEdge, List<Dependence>> _function_8 = (List<Dependence> dependenceList, PRDGEdge edge) -> {
      List<Dependence> _xblockexpression = null;
      {
        dependenceList.add(Dependence.buildNormalDependence(edge));
        _xblockexpression = dependenceList;
      }
      return _xblockexpression;
    };
    dependences = IterableExtensions.<PRDGEdge, List<Dependence>>fold(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function_7), dependences, _function_8);
    InputOutput.<String>println("Space Dims: ");
    final Function2<PolyLibPolyhedron, Dependence, PolyLibPolyhedron> _function_9 = (PolyLibPolyhedron intermediateSpace, Dependence dependence) -> {
      return intermediateSpace.domainAddConstraints(dependence.buildConstraint(intermediateSpace, this.indexMappings), 10);
    };
    this.space = IterableExtensions.<Dependence, PolyLibPolyhedron>fold(dependences, this.space, _function_9);
    InputOutput.<String>println(("Self Dependences: " + dependences));
    this.space = this.space.simplify(10);
    InputOutput.<String>println("Rays: ");
    InputOutput.<String>println(PolylibPrettyPrinter.asString(this.space.builRaysVertices()));
    this.space.print();
  }

  private static boolean isReductionEdge(final PRDGEdge edge) {
    return (((edge.getSource().getName().contains("_body") || edge.getSource().getName().contains("_result")) || edge.getDest().getName().contains("_body")) || edge.getDest().getName().contains("_result"));
  }

  public PolyLibPolyhedron getSpace() {
    return this.space;
  }

  @Pure
  public HashMap<String, HashMap<String, String>> getVariables() {
    return this.variables;
  }

  @Pure
  public HashMap<String, List<String>> getVariableIndices() {
    return this.variableIndices;
  }

  @Pure
  public HashMap<String, Integer> getIndexMappings() {
    return this.indexMappings;
  }

  @Pure
  public HashMap<String, Pair<Pair<String, Pair<PolyLibPolyhedron, PolyLibMatrix>>, List<Pair<String, PolyLibMatrix>>>> getMappings() {
    return this.mappings;
  }
}
