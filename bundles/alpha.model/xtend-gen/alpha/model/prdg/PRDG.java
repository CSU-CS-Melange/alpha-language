package alpha.model.prdg;

import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicMap;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAffPiece;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
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

  public static List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> buildDomainCoefficients(final ISLSet domain, final String label, final Integer count) {
    ArrayList<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _xblockexpression = null;
    {
      ArrayList<Pair<String, HashMap<String, Pair<Integer, Integer>>>> constraints = new ArrayList<Pair<String, HashMap<String, Pair<Integer, Integer>>>>();
      Integer label_count = count;
      List<ISLBasicSet> _basicSets = domain.getBasicSets();
      for (final ISLBasicSet basic_set : _basicSets) {
        {
          List<String> indices = basic_set.copy().getIndexNames();
          HashMap<String, Pair<Integer, Integer>> constant_coefficients = CollectionLiterals.<String, Pair<Integer, Integer>>newHashMap();
          for (int i = 0; (i < indices.size()); i++) {
            String _get = indices.get(i);
            Pair<Integer, Integer> _pair = new Pair<Integer, Integer>(Integer.valueOf(i), Integer.valueOf(0));
            constant_coefficients.put(_get, _pair);
          }
          Pair<Integer, Integer> _pair = new Pair<Integer, Integer>(Integer.valueOf(0), Integer.valueOf(1));
          constant_coefficients.put("constant", _pair);
          Pair<String, HashMap<String, Pair<Integer, Integer>>> _pair_1 = new Pair<String, HashMap<String, Pair<Integer, Integer>>>((label + label_count), constant_coefficients);
          constraints.add(_pair_1);
          label_count++;
          List<ISLConstraint> _constraints = basic_set.removeRedundancies().getConstraints();
          for (final ISLConstraint constraint : _constraints) {
            {
              HashMap<String, Pair<Integer, Integer>> coefficients = CollectionLiterals.<String, Pair<Integer, Integer>>newHashMap();
              for (int i = 0; (i < indices.size()); i++) {
                {
                  long coefficient = constraint.copy().getCoefficient(ISLDimType.isl_dim_out, i);
                  String _get = indices.get(i);
                  int _intValue = Long.valueOf(coefficient).intValue();
                  Pair<Integer, Integer> _pair_2 = new Pair<Integer, Integer>(Integer.valueOf(i), Integer.valueOf(_intValue));
                  coefficients.put(_get, _pair_2);
                }
              }
              int _intValue = Long.valueOf(constraint.copy().getConstant()).intValue();
              Pair<Integer, Integer> _pair_2 = new Pair<Integer, Integer>(Integer.valueOf(0), Integer.valueOf(_intValue));
              coefficients.put("constant", _pair_2);
              Pair<String, HashMap<String, Pair<Integer, Integer>>> _pair_3 = new Pair<String, HashMap<String, Pair<Integer, Integer>>>((label + label_count), coefficients);
              constraints.add(_pair_3);
              label_count++;
            }
          }
        }
      }
      _xblockexpression = constraints;
    }
    return _xblockexpression;
  }

  public static List<Pair<String, HashMap<String, Integer>>> buildFunctionCoefficients(final ISLMap function, final List<String> output_indices) {
    ArrayList<Pair<String, HashMap<String, Integer>>> _xblockexpression = null;
    {
      ArrayList<Pair<String, HashMap<String, Integer>>> function_coefficients = new ArrayList<Pair<String, HashMap<String, Integer>>>();
      int output_index = 0;
      List<ISLPWMultiAffPiece> _pieces = function.copy().toPWMultiAff().getPieces();
      for (final ISLPWMultiAffPiece map : _pieces) {
        {
          final Consumer<ISLAff> _function = (ISLAff x) -> {
            InputOutput.<ISLAff>println(x);
          };
          map.getMaff().getAffs().forEach(_function);
          List<ISLAff> _affs = map.getMaff().getAffs();
          for (final ISLAff piece : _affs) {
            {
              HashMap<String, Integer> function_coefficient = CollectionLiterals.<String, Integer>newHashMap();
              List<String> params = piece.getParamNames();
              for (int i = 0; (i < piece.getNbParams()); i++) {
                {
                  Integer value = Integer.valueOf(Long.valueOf(piece.getCoefficientVal(ISLDimType.isl_dim_param, i).asLong()).intValue());
                  function_coefficient.put(params.get(i), value);
                }
              }
              List<String> inputs = piece.getInputNames();
              for (int i = 0; (i < piece.getNbInputs()); i++) {
                {
                  Integer value = Integer.valueOf(Long.valueOf(piece.getCoefficientVal(ISLDimType.isl_dim_in, i).asLong()).intValue());
                  function_coefficient.put(inputs.get(i), value);
                }
              }
              function_coefficient.put("constant", Integer.valueOf(Long.valueOf(piece.getConstant()).intValue()));
              String _get = output_indices.get(output_index);
              Pair<String, HashMap<String, Integer>> _pair = new Pair<String, HashMap<String, Integer>>(_get, function_coefficient);
              function_coefficients.add(_pair);
              output_index++;
            }
          }
        }
      }
      _xblockexpression = function_coefficients;
    }
    return _xblockexpression;
  }

  public PolyLibPolyhedron generateFeasibleSpace() {
    PolyLibPolyhedron _xblockexpression = null;
    {
      HashMap<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> variables = CollectionLiterals.<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>newHashMap();
      HashMap<String, List<String>> variable_indices = CollectionLiterals.<String, List<String>>newHashMap();
      int mu_count = 0;
      int variable_count = 0;
      String variable_name = "i";
      for (final PRDGNode node : this.nodes) {
        {
          ISLSet domain = node.getDomain().copy().coalesce();
          int index_count = domain.getNbIndices();
          ArrayList<String> indices = CollectionLiterals.<String>newArrayList();
          for (int i = 0; (i < index_count); i++) {
            {
              indices.add((variable_name + Integer.valueOf(variable_count)));
              variable_count++;
            }
          }
          domain = domain.<ISLSet>renameIndices(indices);
          domain = domain.<ISLSet>moveParametersToIndices();
          final List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> coefficients = PRDG.buildDomainCoefficients(domain.copy(), "m", Integer.valueOf(mu_count));
          int _mu_count = mu_count;
          int _size = coefficients.size();
          mu_count = (_mu_count + _size);
          variables.put(node.getName(), coefficients);
          variable_indices.put(node.getName(), domain.getIndexNames());
        }
      }
      HashMap<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> dependence_mappings = CollectionLiterals.<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>newHashMap();
      HashMap<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> self_dependence_mappings = CollectionLiterals.<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>newHashMap();
      HashMap<Pair<String, String>, List<Pair<String, HashMap<String, Integer>>>> function_mappings = CollectionLiterals.<Pair<String, String>, List<Pair<String, HashMap<String, Integer>>>>newHashMap();
      int lambda_count = 0;
      for (final PRDGEdge edge : this.edges) {
        {
          String _name = edge.getSource().getName();
          String _name_1 = edge.getDest().getName();
          Pair<String, String> source_target = new Pair<String, String>(_name, _name_1);
          ISLMap function = edge.getFunction().copy().toMap().simplify().intersectDomain(edge.getDomain().copy());
          function = function.<ISLMap>renameInputs(variable_indices.get(edge.getSource().getName()));
          function = function.<ISLMap>renameOutputs(variable_indices.get(edge.getDest().getName()));
          InputOutput.<List<String>>println(edge.getDest().getDomain().copy().getIndexNames());
          List<Pair<String, HashMap<String, Integer>>> function_coefficients = PRDG.buildFunctionCoefficients(function.copy(), variable_indices.get(edge.getDest().getName()));
          function_mappings.put(source_target, function_coefficients);
          ISLSet domain = function.copy().toSet();
          domain = domain.<ISLSet>moveParametersToIndices();
          InputOutput.<String>println(("Relational Domain" + domain));
          List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> dependence_coefficients = PRDG.buildDomainCoefficients(domain, "l", Integer.valueOf(lambda_count));
          if ((Objects.equal(edge.getSource().getName(), edge.getDest().getName()) && function.isSingleValued())) {
            InputOutput.<Boolean>println(Boolean.valueOf(function.isSingleValued()));
            self_dependence_mappings.put(source_target, dependence_coefficients);
          } else {
            dependence_mappings.put(source_target, dependence_coefficients);
            int _lambda_count = lambda_count;
            int _size = dependence_coefficients.size();
            lambda_count = (_lambda_count + _size);
          }
        }
      }
      ISLBasicMap feasible_space = ISLBasicMap.buildUniverse(ISLSpace.alloc(0, 0, 0));
      HashMap<String, Integer> index_mappings = CollectionLiterals.<String, Integer>newHashMap();
      int mu_index = 0;
      Set<Map.Entry<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>> _entrySet = variables.entrySet();
      for (final Map.Entry<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> entry : _entrySet) {
        {
          final Function1<Pair<String, HashMap<String, Pair<Integer, Integer>>>, String> _function = (Pair<String, HashMap<String, Pair<Integer, Integer>>> x) -> {
            return x.getKey();
          };
          feasible_space = feasible_space.<ISLBasicMap>addOutputs(ListExtensions.<Pair<String, HashMap<String, Pair<Integer, Integer>>>, String>map(entry.getValue(), _function));
          List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _value = entry.getValue();
          for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> pair : _value) {
            {
              index_mappings.put(pair.getKey(), Integer.valueOf(mu_index));
              feasible_space = feasible_space.addConstraint(
                PRDG.singleGreaterThan(Integer.valueOf(mu_index), 
                  feasible_space.copy().getSpace(), ISLDimType.isl_dim_out));
              mu_index++;
            }
          }
        }
      }
      int lambda_index = 0;
      Set<Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>> _entrySet_1 = dependence_mappings.entrySet();
      for (final Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> entry_1 : _entrySet_1) {
        {
          final Function1<Pair<String, HashMap<String, Pair<Integer, Integer>>>, String> _function = (Pair<String, HashMap<String, Pair<Integer, Integer>>> x) -> {
            return x.getKey();
          };
          feasible_space = feasible_space.<ISLBasicMap>addInputs(ListExtensions.<Pair<String, HashMap<String, Pair<Integer, Integer>>>, String>map(entry_1.getValue(), _function));
          List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _value = entry_1.getValue();
          for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> pair : _value) {
            {
              index_mappings.put(pair.getKey(), Integer.valueOf(lambda_index));
              feasible_space = feasible_space.addConstraint(
                PRDG.singleGreaterThan(Integer.valueOf(lambda_index), 
                  feasible_space.copy().getSpace(), ISLDimType.isl_dim_in));
              lambda_index++;
            }
          }
          Pair<String, String> _key = entry_1.getKey();
          String _plus = ("Dependence: " + _key);
          InputOutput.<String>println(_plus);
          InputOutput.<String>println("functions: ");
          final Consumer<Pair<String, HashMap<String, Integer>>> _function_1 = (Pair<String, HashMap<String, Integer>> x) -> {
            InputOutput.<Pair<String, HashMap<String, Integer>>>println(x);
          };
          function_mappings.get(entry_1.getKey()).forEach(_function_1);
          InputOutput.<String>println("Dependence Constraints: ");
          final Consumer<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _function_2 = (Pair<String, HashMap<String, Pair<Integer, Integer>>> x) -> {
            InputOutput.<Pair<String, HashMap<String, Pair<Integer, Integer>>>>println(x);
          };
          entry_1.getValue().forEach(_function_2);
        }
      }
      Set<Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>> _entrySet_2 = self_dependence_mappings.entrySet();
      for (final Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> entry_2 : _entrySet_2) {
        {
          final List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> variable = variables.get(entry_2.getKey().getKey());
          List<Pair<String, HashMap<String, Integer>>> functions = function_mappings.get(entry_2.getKey());
          InputOutput.<List<Pair<String, HashMap<String, Integer>>>>println(function_mappings.get(entry_2.getKey()));
          InputOutput.<List<String>>println(variable_indices.get(entry_2.getKey().getKey()));
          for (int i = 0; (i < functions.size()); i++) {
            {
              Pair<String, HashMap<String, Integer>> _get = functions.get(i);
              String _plus = ("Function: " + _get);
              InputOutput.<String>println(_plus);
              Integer _get_1 = functions.get(i).getValue().get("constant");
              boolean _notEquals = ((_get_1).intValue() != 0);
              if (_notEquals) {
                feasible_space = feasible_space.addConstraint(
                  PRDG.generateSelfCancelConstraint(variable_indices.get(entry_2.getKey().getKey()).get(i), 
                    feasible_space.copy().getSpace(), variable, index_mappings, (functions.get(i).getValue().get("constant")).intValue()));
              }
              HashMap<String, List<String>> target_variables = variable_indices;
              List<String> _get_2 = variable_indices.get(entry_2.getKey().getKey());
              for (final String index : _get_2) {
                InputOutput.<String>println(index);
              }
            }
          }
        }
      }
      Set<Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>> _entrySet_3 = dependence_mappings.entrySet();
      for (final Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> entry_3 : _entrySet_3) {
        {
          Pair<String, String> _key = entry_3.getKey();
          String _plus = ("Mapping: " + _key);
          InputOutput.<String>println(_plus);
          ISLConstraint constant_constraint = ISLConstraint.buildEquality(feasible_space.copy().getSpace());
          InputOutput.<String>println("Constraints: ");
          constant_constraint = constant_constraint.setConstant((-1));
          InputOutput.<String>println("Lambda Constraints");
          List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _value = entry_3.getValue();
          for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint : _value) {
            {
              InputOutput.<Pair<String, HashMap<String, Pair<Integer, Integer>>>>println(constraint);
              Integer _get = index_mappings.get(constraint.getKey());
              Integer _value_1 = constraint.getValue().get("constant").getValue();
              int _minus = (-(_value_1).intValue());
              constant_constraint = constant_constraint.setCoefficient(ISLDimType.isl_dim_in, (_get).intValue(), _minus);
            }
          }
          InputOutput.<String>println("Source Constraints:");
          List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _get = variables.get(entry_3.getKey().getKey());
          for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_1 : _get) {
            {
              InputOutput.<Pair<String, HashMap<String, Pair<Integer, Integer>>>>println(constraint_1);
              constant_constraint = constant_constraint.setCoefficient(ISLDimType.isl_dim_out, 
                (index_mappings.get(constraint_1.getKey())).intValue(), 
                (constraint_1.getValue().get("constant").getValue()).intValue());
            }
          }
          InputOutput.<String>println("Dest constraints:");
          List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _get_1 = variables.get(entry_3.getKey().getValue());
          for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_2 : _get_1) {
            {
              InputOutput.<Pair<String, HashMap<String, Pair<Integer, Integer>>>>println(constraint_2);
              Integer _get_2 = index_mappings.get(constraint_2.getKey());
              Integer _value_1 = constraint_2.getValue().get("constant").getValue();
              int _minus = (-(_value_1).intValue());
              constant_constraint = constant_constraint.setCoefficient(ISLDimType.isl_dim_out, (_get_2).intValue(), _minus);
            }
          }
          InputOutput.<String>println("Constant Constraint:");
          InputOutput.<ISLConstraint>println(constant_constraint);
          feasible_space = feasible_space.addConstraint(constant_constraint);
          List<String> _get_2 = variable_indices.get(entry_3.getKey().getKey());
          for (final String index : _get_2) {
            {
              InputOutput.<String>println(("Index: " + index));
              ISLConstraint lambda_constraint = ISLConstraint.buildEquality(feasible_space.copy().getSpace());
              lambda_constraint = lambda_constraint.setConstant((-1));
              InputOutput.<String>println("Constraint:");
              List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _get_3 = variables.get(entry_3.getKey().getKey());
              for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_3 : _get_3) {
                {
                  InputOutput.<Pair<String, HashMap<String, Pair<Integer, Integer>>>>println(constraint_3);
                  lambda_constraint = lambda_constraint.setCoefficient(ISLDimType.isl_dim_out, 
                    (index_mappings.get(constraint_3.getKey())).intValue(), 
                    (constraint_3.getValue().get(index).getValue()).intValue());
                }
              }
              InputOutput.<ISLConstraint>println(lambda_constraint);
              InputOutput.<String>println("Dest Constraint");
              List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _get_4 = variables.get(entry_3.getKey().getValue());
              for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_4 : _get_4) {
                {
                  InputOutput.<Pair<String, HashMap<String, Pair<Integer, Integer>>>>println(constraint_4);
                  List<Pair<String, HashMap<String, Integer>>> _get_5 = function_mappings.get(entry_3.getKey());
                  for (final Pair<String, HashMap<String, Integer>> mapping : _get_5) {
                    {
                      InputOutput.<Pair<String, HashMap<String, Integer>>>println(mapping);
                      InputOutput.<String>println(index);
                      int value = Long.valueOf(lambda_constraint.getCoefficient(ISLDimType.isl_dim_out, (index_mappings.get(constraint_4.getKey())).intValue())).intValue();
                      int _value_1 = value;
                      Integer _value_2 = constraint_4.getValue().get(mapping.getKey()).getValue();
                      Integer _get_6 = mapping.getValue().get(index);
                      int _multiply = ((_value_2).intValue() * (_get_6).intValue());
                      value = (_value_1 - _multiply);
                      lambda_constraint = lambda_constraint.setCoefficient(ISLDimType.isl_dim_out, 
                        (index_mappings.get(constraint_4.getKey())).intValue(), value);
                    }
                  }
                  InputOutput.<ISLConstraint>println(lambda_constraint);
                }
              }
              InputOutput.<String>println("Lambda Constraints:");
              List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _value_1 = entry_3.getValue();
              for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_5 : _value_1) {
                {
                  InputOutput.<Pair<String, HashMap<String, Pair<Integer, Integer>>>>println(constraint_5);
                  Integer _get_5 = index_mappings.get(constraint_5.getKey());
                  Integer _value_2 = constraint_5.getValue().get(index).getValue();
                  int _minus = (-(_value_2).intValue());
                  lambda_constraint = lambda_constraint.setCoefficient(ISLDimType.isl_dim_in, (_get_5).intValue(), _minus);
                }
              }
              InputOutput.<ISLConstraint>println(lambda_constraint);
              feasible_space = feasible_space.addConstraint(lambda_constraint);
            }
          }
        }
      }
      InputOutput.<HashMap<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>>println(variables);
      InputOutput.<HashMap<String, List<String>>>println(variable_indices);
      InputOutput.<ISLBasicMap>println(feasible_space.copy());
      _xblockexpression = PolyLibPolyhedron.createFromLongMatrix(feasible_space.getRange().toPolyLibArray());
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

  public static ISLConstraint singleGreaterThan(final Integer index, final ISLSpace space, final ISLDimType dim) {
    ISLConstraint _xblockexpression = null;
    {
      ISLConstraint constraint = ISLConstraint.buildInequality(space);
      constraint = constraint.setCoefficient(dim, (index).intValue(), 1);
      _xblockexpression = constraint;
    }
    return _xblockexpression;
  }
}
