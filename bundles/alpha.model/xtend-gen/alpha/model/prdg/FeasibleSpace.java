package alpha.model.prdg;

import alpha.model.util.AffineFunctionOperations;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.IISLSingleSpaceSetMethods;
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
import fr.irisa.cairn.jnimap.polylib.PolyLibMatrix;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
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
  private HashMap<String, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>> mappings;

  private ISLBasicMap space;

  private String variableName;

  private int variableCount;

  private int muCount;

  private int lambdaCount;

  public FeasibleSpace(final PRDG prdg) {
    this.variables = CollectionLiterals.<String, HashMap<String, String>>newHashMap();
    this.variableIndices = CollectionLiterals.<String, List<String>>newHashMap();
    this.muCount = 0;
    this.variableCount = 0;
    this.variableName = "i";
    this.indexMappings = CollectionLiterals.<String, Integer>newHashMap();
    this.mappings = CollectionLiterals.<String, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>>newHashMap();
    PolyLibMatrix dom = PolyLibMatrix.createFromLongMatrix(prdg.getNodes().get(0).getDomain().copy().getBasicSets().get(0).toPolyLibArray());
    InputOutput.<String>println(("Domain: \n" + dom));
    ISLBasicSet _basicSet = prdg.getEdges().get(0).getFunction().copy().toBasicMap().toBasicSet();
    String _plus = ("Map: " + _basicSet);
    InputOutput.<String>println(_plus);
    PolyLibMatrix map = PolyLibMatrix.createFromLongMatrix(
      AffineFunctionOperations.toMatrix(prdg.getEdges().get(0).getFunction().copy()).toArray());
    InputOutput.<String>println(("Map: \n" + map));
    List<PRDGNode> _nodes = prdg.getNodes();
    for (final PRDGNode node : _nodes) {
      boolean _not = (!(node.getName().contains("_body") || node.getName().contains("_result")));
      if (_not) {
        ISLSet domain = node.getDomain().copy().coalesce();
        this.addDomain(domain, node.getName());
      }
    }
    List<PRDGEdge> _edges = prdg.getEdges();
    for (final PRDGEdge edge : _edges) {
      boolean _contains = edge.getSource().getName().contains("_body");
      if (_contains) {
        ISLMap fun = edge.getFunction().copy().toMap().intersectDomain(edge.getSource().getDomain().copy());
        String constant = ("l" + Integer.valueOf(this.lambdaCount));
        this.lambdaCount++;
        ArrayList<Pair<String, ISLConstraint>> dependencies = CollectionLiterals.<Pair<String, ISLConstraint>>newArrayList();
        List<ISLPWMultiAffPiece> _pieces = fun.copy().toPWMultiAff().getPieces();
        for (final ISLPWMultiAffPiece piece : _pieces) {
          List<ISLBasicSet> _basicSets = piece.getSet().getBasicSets();
          for (final ISLBasicSet set : _basicSets) {
            List<ISLConstraint> _constraints = set.getConstraints();
            for (final ISLConstraint constraint : _constraints) {
              {
                String lambda = ("l" + Integer.valueOf(this.lambdaCount));
                this.lambdaCount++;
                Pair<String, ISLConstraint> _pair = new Pair<String, ISLConstraint>(lambda, constraint);
                dependencies.add(_pair);
              }
            }
          }
        }
        ArrayList<Pair<String, Dependence>> array = new ArrayList<Pair<String, Dependence>>();
        String _name = edge.getDest().getName();
        ISLMultiAff _function = edge.getFunction();
        Dependence _dependence = new Dependence(_function, constant, dependencies);
        Pair<String, Dependence> _pair = new Pair<String, Dependence>(_name, _dependence);
        array.add(_pair);
        String _name_1 = edge.getSource().getName();
        Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> _pair_1 = new Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>(null, array);
        final BiFunction<Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>> _function_1 = (Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> existing, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> n) -> {
          Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> _xblockexpression = null;
          {
            List<Pair<String, Dependence>> arr = n.getValue();
            arr.addAll(existing.getValue());
            Pair<String, ISLMultiAff> _key = existing.getKey();
            _xblockexpression = new Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>(_key, arr);
          }
          return _xblockexpression;
        };
        this.mappings.merge(_name_1, _pair_1, _function_1);
      } else {
        boolean _contains_1 = edge.getDest().getName().contains("_result");
        if (_contains_1) {
          final Function1<PRDGEdge, Boolean> _function_2 = (PRDGEdge x) -> {
            String _name_2 = x.getSource().getName();
            String _name_3 = edge.getDest().getName();
            return Boolean.valueOf(Objects.equal(_name_2, _name_3));
          };
          PRDGEdge body = IterableExtensions.<PRDGEdge>head(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function_2));
          String _name_2 = body.getDest().getName();
          String _name_3 = edge.getSource().getName();
          ISLMultiAff _function_3 = body.getFunction();
          Pair<String, ISLMultiAff> _pair_2 = new Pair<String, ISLMultiAff>(_name_3, _function_3);
          ArrayList<Pair<String, Dependence>> _arrayList = new ArrayList<Pair<String, Dependence>>();
          Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> _pair_3 = new Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>(_pair_2, _arrayList);
          final BiFunction<Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>> _function_4 = (Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> existing, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> n) -> {
            Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> _xblockexpression = null;
            {
              Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> _elvis = null;
              if (existing != null) {
                _elvis = existing;
              } else {
                _elvis = n;
              }
              Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> x = _elvis;
              Pair<String, ISLMultiAff> _key = n.getKey();
              List<Pair<String, Dependence>> _value = x.getValue();
              _xblockexpression = new Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>(_key, _value);
            }
            return _xblockexpression;
          };
          this.mappings.merge(_name_2, _pair_3, _function_4);
        } else {
          boolean _not_1 = (!(edge.getDest().getName().contains("_body") || edge.getSource().getName().contains("_result")));
          if (_not_1) {
            ISLMap fun_1 = edge.getFunction().copy().toMap().intersectDomain(edge.getSource().getDomain().copy());
            String constant_1 = ("l" + Integer.valueOf(this.lambdaCount));
            this.lambdaCount++;
            ArrayList<Pair<String, ISLConstraint>> dependencies_1 = CollectionLiterals.<Pair<String, ISLConstraint>>newArrayList();
            List<ISLPWMultiAffPiece> _pieces_1 = fun_1.copy().toPWMultiAff().getPieces();
            for (final ISLPWMultiAffPiece piece_1 : _pieces_1) {
              List<ISLBasicSet> _basicSets_1 = piece_1.getSet().getBasicSets();
              for (final ISLBasicSet set_1 : _basicSets_1) {
                List<ISLConstraint> _constraints_1 = set_1.getConstraints();
                for (final ISLConstraint constraint_1 : _constraints_1) {
                  {
                    String lambda = ("l" + Integer.valueOf(this.lambdaCount));
                    this.lambdaCount++;
                    ISLConstraint _copy = constraint_1.copy();
                    Pair<String, ISLConstraint> _pair_4 = new Pair<String, ISLConstraint>(lambda, _copy);
                    dependencies_1.add(_pair_4);
                  }
                }
              }
            }
            ArrayList<Pair<String, Dependence>> array_1 = new ArrayList<Pair<String, Dependence>>();
            String _name_4 = edge.getDest().getName();
            ISLMultiAff _copy = edge.getFunction().copy();
            Dependence _dependence_1 = new Dependence(_copy, constant_1, dependencies_1);
            Pair<String, Dependence> _pair_4 = new Pair<String, Dependence>(_name_4, _dependence_1);
            array_1.add(_pair_4);
            String _name_5 = edge.getSource().getName();
            String _name_6 = edge.getDest().getName();
            ISLMultiAff _buildIdentity = ISLMultiAff.buildIdentity(edge.getFunction().copy().getSpace());
            Pair<String, ISLMultiAff> _pair_5 = new Pair<String, ISLMultiAff>(_name_6, _buildIdentity);
            Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> _pair_6 = new Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>(_pair_5, array_1);
            final BiFunction<Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>> _function_5 = (Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> existing, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> n) -> {
              Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> _xblockexpression = null;
              {
                List<Pair<String, Dependence>> arr = n.getValue();
                arr.addAll(existing.getValue());
                Pair<String, ISLMultiAff> _key = n.getKey();
                _xblockexpression = new Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>(_key, arr);
              }
              return _xblockexpression;
            };
            this.mappings.merge(_name_5, _pair_6, _function_5);
          }
        }
      }
    }
    this.space = ISLBasicMap.buildUniverse(ISLSpace.alloc(0, 0, 0));
    int muIndex = 0;
    Set<Map.Entry<String, HashMap<String, String>>> _entrySet = this.variables.entrySet();
    for (final Map.Entry<String, HashMap<String, String>> entry : _entrySet) {
      {
        this.space = this.space.<ISLBasicMap>addOutputs(IterableExtensions.<String>toList(entry.getValue().values()));
        Collection<String> _values = entry.getValue().values();
        for (final String key : _values) {
          {
            this.indexMappings.put(key, Integer.valueOf(muIndex));
            ISLConstraint constraint_2 = ISLConstraint.buildInequality(this.space.getSpace());
            constraint_2 = constraint_2.setCoefficient(ISLDimType.isl_dim_out, muIndex, 1);
            this.space = this.space.addConstraint(constraint_2);
            muIndex++;
          }
        }
      }
    }
    int lambdaIndex = 0;
    Set<String> _keySet = this.mappings.keySet();
    for (final String variable : _keySet) {
      List<Pair<String, Dependence>> _value = this.mappings.get(variable).getValue();
      for (final Pair<String, Dependence> dependence : _value) {
        {
          ArrayList<String> lambdas = new ArrayList<String>();
          lambdas.add(dependence.getValue().getConstantLambda());
          final Function1<Pair<String, ISLConstraint>, String> _function_6 = (Pair<String, ISLConstraint> pair) -> {
            return pair.getKey();
          };
          lambdas.addAll(ListExtensions.<Pair<String, ISLConstraint>, String>map(dependence.getValue().getLambdas(), _function_6));
          this.space = this.space.<ISLBasicMap>addInputs(lambdas);
          for (final String key : lambdas) {
            {
              this.indexMappings.put(key, Integer.valueOf(lambdaIndex));
              lambdaIndex++;
              ISLConstraint constraint_2 = ISLConstraint.buildInequality(this.space.getSpace());
              constraint_2 = constraint_2.setCoefficient(ISLDimType.isl_dim_in, (this.indexMappings.get(key)).intValue(), 1);
              this.space = this.space.addConstraint(constraint_2);
            }
          }
        }
      }
    }
    final ISLConstraint universeConstraint = ISLConstraint.buildInequality(this.space.getSpace());
    final BiConsumer<String, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>> _function_6 = (String reductionNode, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>> value) -> {
      final Consumer<Pair<String, Dependence>> _function_7 = (Pair<String, Dependence> dependence_1) -> {
        ISLConstraint constantConstraint = ISLConstraint.buildInequality(this.space.getSpace());
        constantConstraint = this.updateConstraint(constantConstraint, value.getKey().getKey(), 1, "constant", 1);
        constantConstraint = this.updateConstraint(constantConstraint.copy(), dependence_1.getKey(), (-1), "constant", 1);
        final List<ISLAff> projections = value.getKey().getValue().copy().getAffs();
        final List<ISLAff> reads = dependence_1.getValue().getDependence().copy().getAffs();
        int _size = projections.size();
        final Function2<ISLConstraint, Integer, ISLConstraint> _function_8 = (ISLConstraint constraint_2, Integer leftIndex) -> {
          return this.updateConstraint(constraint_2, value.getKey().getKey(), 1, 
            this.variableIndices.get(value.getKey().getKey()).get((leftIndex).intValue()), 
            Long.valueOf(projections.get((leftIndex).intValue()).getConstantVal().copy().asLong()).intValue());
        };
        constantConstraint = IterableExtensions.<Integer, ISLConstraint>fold(new ExclusiveRange(0, _size, true), constantConstraint, _function_8);
        int _size_1 = reads.size();
        final Function2<ISLConstraint, Integer, ISLConstraint> _function_9 = (ISLConstraint constraint_2, Integer rightIndex) -> {
          return this.updateConstraint(constraint_2, dependence_1.getKey(), (-1), 
            this.variableIndices.get(dependence_1.getKey()).get((rightIndex).intValue()), 
            Long.valueOf(reads.get((rightIndex).intValue()).getConstantVal().copy().asLong()).intValue());
        };
        constantConstraint = IterableExtensions.<Integer, ISLConstraint>fold(new ExclusiveRange(0, _size_1, true), constantConstraint, _function_9);
        boolean _isEqual = constantConstraint.isEqual(universeConstraint);
        boolean _not_2 = (!_isEqual);
        if (_not_2) {
          constantConstraint = constantConstraint.setCoefficient(ISLDimType.isl_dim_in, 
            (this.indexMappings.get(dependence_1.getValue().getConstantLambda())).intValue(), (-1));
          constantConstraint = constantConstraint.setConstant((-1));
          this.space = this.space.addConstraint(constantConstraint);
        }
        int _nbInputs = dependence_1.getValue().getDependence().copy().getNbInputs();
        final Function2<ISLBasicMap, Integer, ISLBasicMap> _function_10 = (ISLBasicMap localSpace, Integer index) -> {
          ISLBasicMap _xblockexpression = null;
          {
            ISLConstraint constraint_2 = ISLConstraint.buildInequality(localSpace.getSpace());
            int _size_2 = projections.size();
            final Function2<ISLConstraint, Integer, ISLConstraint> _function_11 = (ISLConstraint localConstraint, Integer leftIndex) -> {
              return this.updateConstraint(localConstraint, value.getKey().getKey(), 1, 
                this.variableIndices.get(value.getKey().getKey()).get((leftIndex).intValue()), 
                Long.valueOf(projections.get((leftIndex).intValue()).getCoefficientVal(ISLDimType.isl_dim_in, (index).intValue()).copy().asLong()).intValue());
            };
            constraint_2 = IterableExtensions.<Integer, ISLConstraint>fold(new ExclusiveRange(0, _size_2, true), constraint_2, _function_11);
            int _size_3 = reads.size();
            final Function2<ISLConstraint, Integer, ISLConstraint> _function_12 = (ISLConstraint localConstraint, Integer rightIndex) -> {
              return this.updateConstraint(localConstraint, dependence_1.getKey(), (-1), 
                this.variableIndices.get(dependence_1.getKey()).get((rightIndex).intValue()), 
                Long.valueOf(reads.get((rightIndex).intValue()).getCoefficientVal(ISLDimType.isl_dim_in, (index).intValue()).copy().asLong()).intValue());
            };
            constraint_2 = IterableExtensions.<Integer, ISLConstraint>fold(new ExclusiveRange(0, _size_3, true), constraint_2, _function_12);
            final Function2<ISLConstraint, Pair<String, ISLConstraint>, ISLConstraint> _function_13 = (ISLConstraint localConstraint, Pair<String, ISLConstraint> mapping) -> {
              return localConstraint.setCoefficient(ISLDimType.isl_dim_in, (this.indexMappings.get(mapping.getKey())).intValue(), 
                Long.valueOf(mapping.getValue().getCoefficient(ISLDimType.isl_dim_out, (index).intValue())).intValue());
            };
            constraint_2 = IterableExtensions.<Pair<String, ISLConstraint>, ISLConstraint>fold(dependence_1.getValue().getLambdas(), constraint_2, _function_13);
            ISLBasicMap output = localSpace;
            boolean _isEqual_1 = constraint_2.isEqual(universeConstraint);
            boolean _not_3 = (!_isEqual_1);
            if (_not_3) {
              constraint_2 = constraint_2.setConstant((-1));
              output = localSpace.addConstraint(constraint_2);
            }
            _xblockexpression = output;
          }
          return _xblockexpression;
        };
        this.space = IterableExtensions.<Integer, ISLBasicMap>fold(new ExclusiveRange(0, _nbInputs, true), this.space, _function_10);
      };
      value.getValue().forEach(_function_7);
    };
    this.mappings.forEach(_function_6);
    this.space = this.space.copy().removeRedundancies();
  }

  private ISLConstraint updateConstraint(final ISLConstraint constraint, final String variable, final int direction, final String index, final int coefficient) {
    ISLConstraint _xblockexpression = null;
    {
      int old = Long.valueOf(constraint.copy().getCoefficient(ISLDimType.isl_dim_out, 
        (this.indexMappings.get(this.variables.get(variable).get(index))).intValue())).intValue();
      _xblockexpression = constraint.copy().setCoefficient(ISLDimType.isl_dim_out, 
        (this.indexMappings.get(this.variables.get(variable).get(index))).intValue(), 
        (old + (direction * coefficient)));
    }
    return _xblockexpression;
  }

  public ISLBasicSet getSpace() {
    return this.space.copy().getRange();
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
      final HashMap<String, String> coefficients = this.buildDomainCoefficients(updatedDomain.<ISLSet>copy(), "m", Integer.valueOf(this.muCount));
      int _muCount = this.muCount;
      int _size = coefficients.size();
      this.muCount = (_muCount + _size);
      this.variables.put(variable, coefficients);
      _xblockexpression = this.variableIndices.put(variable, updatedDomain.getIndexNames());
    }
    return _xblockexpression;
  }

  private HashMap<String, String> buildDomainCoefficients(final ISLSet domain, final String label, final Integer count) {
    HashMap<String, String> _xblockexpression = null;
    {
      HashMap<String, String> constraints = CollectionLiterals.<String, String>newHashMap();
      Integer labelCount = count;
      constraints.put("constant", (label + labelCount));
      labelCount++;
      List<String> _indexNames = domain.getIndexNames();
      for (final String index : _indexNames) {
        {
          constraints.put(index, (label + labelCount));
          labelCount++;
        }
      }
      _xblockexpression = constraints;
    }
    return _xblockexpression;
  }

  public static PolyLibMatrix matMult(final PolyLibMatrix x, final PolyLibMatrix y) {
    PolyLibMatrix _xblockexpression = null;
    {
      int n = x.getNbRows();
      int m = y.getNbColumns();
      PolyLibMatrix output = PolyLibMatrix.allocate(n, m);
      for (int i = 0; (i < n); i++) {
        for (int j = 0; (j < m); j++) {
          for (int k = 0; (k < y.getNbRows()); k++) {
            {
              InputOutput.<String>println(((((("i, j, k: " + Integer.valueOf(i)) + ", ") + Integer.valueOf(j)) + ", ") + Integer.valueOf(k)));
              long _at = output.getAt(i, j);
              long _at_1 = x.getAt(i, k);
              long _at_2 = y.getAt(k, j);
              long _multiply = (_at_1 * _at_2);
              long _plus = (_at + _multiply);
              output.setAt(i, j, _plus);
            }
          }
        }
      }
      _xblockexpression = output;
    }
    return _xblockexpression;
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
  public HashMap<String, Pair<Pair<String, ISLMultiAff>, List<Pair<String, Dependence>>>> getMappings() {
    return this.mappings;
  }
}
