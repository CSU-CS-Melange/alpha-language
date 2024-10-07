package alpha.model.prdg;

import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.IISLSingleSpaceSetMethods;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicMap;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAffPiece;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class FeasibleSpace {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private HashMap<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> variables;

  @Accessors(AccessorType.PUBLIC_GETTER)
  private HashMap<String, List<String>> variableIndices;

  @Accessors(AccessorType.PUBLIC_GETTER)
  private HashMap<String, Integer> indexMappings;

  private ISLBasicMap space;

  private String variableName;

  private int variableCount;

  private int muCount;

  @Accessors(AccessorType.PUBLIC_GETTER)
  private HashMap<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> dependenceMappings;

  private HashMap<Pair<String, String>, List<Pair<String, HashMap<String, Integer>>>> functionMappings;

  private int lambdaCount;

  public FeasibleSpace(final PRDG prdg) {
    this.variables = CollectionLiterals.<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>newHashMap();
    this.variableIndices = CollectionLiterals.<String, List<String>>newHashMap();
    this.muCount = 0;
    this.variableCount = 0;
    this.variableName = "i";
    this.dependenceMappings = CollectionLiterals.<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>newHashMap();
    this.functionMappings = CollectionLiterals.<Pair<String, String>, List<Pair<String, HashMap<String, Integer>>>>newHashMap();
    this.indexMappings = CollectionLiterals.<String, Integer>newHashMap();
    List<PRDGNode> _nodes = prdg.getNodes();
    for (final PRDGNode node : _nodes) {
      {
        ISLSet domain = node.getDomain().copy().coalesce();
        this.addDomain(domain, node.getName());
      }
    }
    List<PRDGEdge> _edges = prdg.getEdges();
    for (final PRDGEdge edge : _edges) {
      {
        ISLMap function = edge.getFunction().copy().toMap().simplify().intersectDomain(edge.getDomain().copy());
        this.addEdgeConstraints(edge.getSource().getName(), edge.getDest().getName(), function);
      }
    }
    this.space = ISLBasicMap.buildUniverse(ISLSpace.alloc(0, 0, 0));
    int muIndex = 0;
    Set<Map.Entry<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>> _entrySet = this.variables.entrySet();
    for (final Map.Entry<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> entry : _entrySet) {
      {
        final Function1<Pair<String, HashMap<String, Pair<Integer, Integer>>>, String> _function = (Pair<String, HashMap<String, Pair<Integer, Integer>>> x) -> {
          return x.getKey();
        };
        this.space = this.space.<ISLBasicMap>addOutputs(ListExtensions.<Pair<String, HashMap<String, Pair<Integer, Integer>>>, String>map(entry.getValue(), _function));
        List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _value = entry.getValue();
        for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> pair : _value) {
          {
            this.indexMappings.put(pair.getKey(), Integer.valueOf(muIndex));
            this.space = this.space.addConstraint(
              FeasibleSpace.singleGreaterThan(Integer.valueOf(muIndex), 
                this.space.copy().getSpace(), ISLDimType.isl_dim_out));
            muIndex++;
          }
        }
      }
    }
    int lambdaIndex = 0;
    Set<Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>> _entrySet_1 = this.dependenceMappings.entrySet();
    for (final Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> entry_1 : _entrySet_1) {
      {
        final Function1<Pair<String, HashMap<String, Pair<Integer, Integer>>>, String> _function = (Pair<String, HashMap<String, Pair<Integer, Integer>>> x) -> {
          return x.getKey();
        };
        this.space = this.space.<ISLBasicMap>addInputs(ListExtensions.<Pair<String, HashMap<String, Pair<Integer, Integer>>>, String>map(entry_1.getValue(), _function));
        List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _value = entry_1.getValue();
        for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> pair : _value) {
          {
            this.indexMappings.put(pair.getKey(), Integer.valueOf(lambdaIndex));
            this.space = this.space.addConstraint(
              FeasibleSpace.singleGreaterThan(Integer.valueOf(lambdaIndex), 
                this.space.copy().getSpace(), ISLDimType.isl_dim_in));
            lambdaIndex++;
          }
        }
      }
    }
    Set<Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>>> _entrySet_2 = this.dependenceMappings.entrySet();
    for (final Map.Entry<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> entry_2 : _entrySet_2) {
      String _key = entry_2.getKey().getKey();
      String _value = entry_2.getKey().getValue();
      boolean _equals = Objects.equal(_key, _value);
      if (_equals) {
        this.selfDependenceConstraint(entry_2.getKey().getKey(), entry_2.getValue());
      } else {
        this.interVariableConstraint(entry_2.getKey(), entry_2.getValue());
      }
    }
    this.space.copy().removeRedundancies();
  }

  private ISLBasicMap selfDependenceConstraint(final String variable, final List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> dependences) {
    ISLBasicMap _xblockexpression = null;
    {
      List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> selfDependence = this.variables.get(variable);
      Pair<String, String> _pair = new Pair<String, String>(variable, variable);
      List<Pair<String, HashMap<String, Integer>>> mapping = this.functionMappings.get(_pair);
      ISLConstraint constantConstraint = ISLConstraint.buildInequality(this.space.getSpace());
      for (final Pair<String, HashMap<String, Integer>> map : mapping) {
        {
          ISLConstraint constraint = ISLConstraint.buildInequality(this.space.getSpace());
          for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> dependence : selfDependence) {
            {
              if ((((map.getValue().get("constant")).intValue() != 0) && (!Objects.equal(dependence.getValue().get(map.getKey()), Integer.valueOf(0))))) {
                long value = constantConstraint.copy().getCoefficient(ISLDimType.isl_dim_out, 
                  (this.indexMappings.get(dependence.getKey())).intValue());
                Integer _get = this.indexMappings.get(dependence.getKey());
                int _intValue = Long.valueOf(value).intValue();
                Integer _value = dependence.getValue().get(map.getKey()).getValue();
                Integer _get_1 = map.getValue().get("constant");
                int _multiply = ((_value).intValue() * (_get_1).intValue());
                int _minus = (_intValue - _multiply);
                constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_out, (_get).intValue(), _minus);
              }
              List<String> _get_2 = this.variableIndices.get(variable);
              for (final String index : _get_2) {
                {
                  long value_1 = constraint.getCoefficient(ISLDimType.isl_dim_out, (this.indexMappings.get(dependence.getKey())).intValue());
                  Integer _get_3 = this.indexMappings.get(dependence.getKey());
                  int _intValue_1 = Long.valueOf(value_1).intValue();
                  Integer _value_1 = dependence.getValue().get(index).getValue();
                  Integer _get_4 = map.getValue().get(index);
                  int _multiply_1 = ((_value_1).intValue() * (_get_4).intValue());
                  int _minus_1 = (_intValue_1 - _multiply_1);
                  constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, (_get_3).intValue(), _minus_1);
                }
              }
              long value_1 = constraint.getCoefficient(ISLDimType.isl_dim_out, (this.indexMappings.get(dependence.getKey())).intValue());
              Integer _get_3 = this.indexMappings.get(dependence.getKey());
              int _intValue_1 = Long.valueOf(value_1).intValue();
              Integer _value_1 = dependence.getValue().get(map.getKey()).getValue();
              int _plus = (_intValue_1 + (_value_1).intValue());
              constraint = constraint.setCoefficient(ISLDimType.isl_dim_out, (_get_3).intValue(), _plus);
            }
          }
          this.space = this.space.addConstraint(constraint);
        }
      }
      constantConstraint = constantConstraint.setConstant((-1));
      _xblockexpression = this.space = this.space.addConstraint(constantConstraint);
    }
    return _xblockexpression;
  }

  private void interVariableConstraint(final Pair<String, String> sourceDest, final List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> dependences) {
    ISLConstraint constantConstraint = ISLConstraint.buildEquality(this.space.copy().getSpace());
    constantConstraint = constantConstraint.setConstant((-1));
    for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint : dependences) {
      Integer _get = this.indexMappings.get(constraint.getKey());
      Integer _value = constraint.getValue().get("constant").getValue();
      int _minus = (-(_value).intValue());
      constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_in, (_get).intValue(), _minus);
    }
    List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _get_1 = this.variables.get(sourceDest.getKey());
    for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_1 : _get_1) {
      constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_out, 
        (this.indexMappings.get(constraint_1.getKey())).intValue(), 
        (constraint_1.getValue().get("constant").getValue()).intValue());
    }
    List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _get_2 = this.variables.get(sourceDest.getValue());
    for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_2 : _get_2) {
      Integer _get_3 = this.indexMappings.get(constraint_2.getKey());
      Integer _value_1 = constraint_2.getValue().get("constant").getValue();
      int _minus_1 = (-(_value_1).intValue());
      constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_out, (_get_3).intValue(), _minus_1);
    }
    this.space = this.space.addConstraint(constantConstraint);
    List<String> _get_4 = this.variableIndices.get(sourceDest.getKey());
    for (final String index : _get_4) {
      {
        ISLConstraint lambdaConstraint = ISLConstraint.buildEquality(this.space.copy().getSpace());
        lambdaConstraint = lambdaConstraint.setConstant((-1));
        List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _get_5 = this.variables.get(sourceDest.getKey());
        for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_3 : _get_5) {
          lambdaConstraint = lambdaConstraint.setCoefficient(ISLDimType.isl_dim_out, 
            (this.indexMappings.get(constraint_3.getKey())).intValue(), 
            (constraint_3.getValue().get(index).getValue()).intValue());
        }
        List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> _get_6 = this.variables.get(sourceDest.getValue());
        for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_4 : _get_6) {
          List<Pair<String, HashMap<String, Integer>>> _get_7 = this.functionMappings.get(sourceDest);
          for (final Pair<String, HashMap<String, Integer>> mapping : _get_7) {
            {
              int value = Long.valueOf(lambdaConstraint.getCoefficient(ISLDimType.isl_dim_out, (this.indexMappings.get(constraint_4.getKey())).intValue())).intValue();
              int _value_2 = value;
              Integer _value_3 = constraint_4.getValue().get(mapping.getKey()).getValue();
              Integer _get_8 = mapping.getValue().get(index);
              int _multiply = ((_value_3).intValue() * (_get_8).intValue());
              value = (_value_2 - _multiply);
              lambdaConstraint = lambdaConstraint.setCoefficient(ISLDimType.isl_dim_out, 
                (this.indexMappings.get(constraint_4.getKey())).intValue(), value);
            }
          }
        }
        for (final Pair<String, HashMap<String, Pair<Integer, Integer>>> constraint_5 : dependences) {
          Integer _get_8 = this.indexMappings.get(constraint_5.getKey());
          Integer _value_2 = constraint_5.getValue().get(index).getValue();
          int _minus_2 = (-(_value_2).intValue());
          lambdaConstraint = lambdaConstraint.setCoefficient(ISLDimType.isl_dim_in, (_get_8).intValue(), _minus_2);
        }
        this.space = this.space.addConstraint(lambdaConstraint);
      }
    }
  }

  public ISLBasicSet getSpace() {
    return this.space.copy().getRange();
  }

  private static ISLConstraint singleGreaterThan(final Integer index, final ISLSpace space, final ISLDimType dim) {
    ISLConstraint _xblockexpression = null;
    {
      ISLConstraint constraint = ISLConstraint.buildInequality(space);
      constraint = constraint.setCoefficient(dim, (index).intValue(), 1);
      _xblockexpression = constraint;
    }
    return _xblockexpression;
  }

  public List<String> addDomain(final ISLSet domain, final String variable) {
    List<String> _xblockexpression = null;
    {
      int indexCount = domain.getNbIndices();
      ArrayList<String> indices = CollectionLiterals.<String>newArrayList();
      for (int i = 0; (i < indexCount); i++) {
        {
          indices.add((this.variableName + Integer.valueOf(this.variableCount)));
          this.variableCount++;
        }
      }
      IISLSingleSpaceSetMethods updatedDomain = domain.<IISLSingleSpaceSetMethods>renameIndices(indices);
      updatedDomain = updatedDomain.<IISLSingleSpaceSetMethods>moveParametersToIndices();
      final List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> coefficients = this.buildDomainCoefficients(updatedDomain.<ISLSet>copy(), "m", Integer.valueOf(this.muCount));
      int _muCount = this.muCount;
      int _size = coefficients.size();
      this.muCount = (_muCount + _size);
      this.variables.put(variable, coefficients);
      _xblockexpression = this.variableIndices.put(variable, updatedDomain.getIndexNames());
    }
    return _xblockexpression;
  }

  public int addEdgeConstraints(final String source, final String target, final ISLMap f) {
    int _xblockexpression = (int) 0;
    {
      Pair<String, String> sourceTarget = new Pair<String, String>(source, target);
      ISLMap function = f.copy();
      function = function.<ISLMap>renameInputs(this.variableIndices.get(source));
      function = function.<ISLMap>renameOutputs(this.variableIndices.get(target));
      function = function.<ISLMap>moveParamsToInputs();
      List<Pair<String, HashMap<String, Integer>>> functionCoefficients = this.buildFunctionCoefficients(function.copy(), this.variableIndices.get(target));
      this.functionMappings.put(sourceTarget, functionCoefficients);
      ISLSet domain = function.copy().toSet();
      domain = domain.<ISLSet>moveParametersToIndices();
      List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> dependenceCoefficients = this.buildDomainCoefficients(domain, "l", Integer.valueOf(this.lambdaCount));
      this.dependenceMappings.put(sourceTarget, dependenceCoefficients);
      int _lambdaCount = this.lambdaCount;
      int _size = dependenceCoefficients.size();
      _xblockexpression = this.lambdaCount = (_lambdaCount + _size);
    }
    return _xblockexpression;
  }

  private List<Pair<String, HashMap<String, Integer>>> buildFunctionCoefficients(final ISLMap function, final List<String> output_indices) {
    ArrayList<Pair<String, HashMap<String, Integer>>> _xblockexpression = null;
    {
      ArrayList<Pair<String, HashMap<String, Integer>>> function_coefficients = new ArrayList<Pair<String, HashMap<String, Integer>>>();
      int output_index = 0;
      List<ISLPWMultiAffPiece> _pieces = function.copy().toPWMultiAff().getPieces();
      for (final ISLPWMultiAffPiece map : _pieces) {
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
      _xblockexpression = function_coefficients;
    }
    return _xblockexpression;
  }

  private List<Pair<String, HashMap<String, Pair<Integer, Integer>>>> buildDomainCoefficients(final ISLSet domain, final String label, final Integer count) {
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

  @Pure
  public HashMap<String, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> getVariables() {
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
  public HashMap<Pair<String, String>, List<Pair<String, HashMap<String, Pair<Integer, Integer>>>>> getDependenceMappings() {
    return this.dependenceMappings;
  }
}
