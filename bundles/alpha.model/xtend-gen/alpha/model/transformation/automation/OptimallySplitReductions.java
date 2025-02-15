package alpha.model.transformation.automation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGEdge;
import alpha.model.prdg.PRDGGenerator;
import alpha.model.prdg.PRDGNode;
import alpha.model.transformation.reduction.NormalizeReduction;
import alpha.model.transformation.reduction.SerializeReduction;
import alpha.model.transformation.reduction.SplitReduction;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPoint;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class OptimallySplitReductions {
  /**
   * A helper class to represent the 'dummy nodes' that are introduced to a PRDG
   * in order to impose the necessary constraints on the system.
   * Each dummy node corresponds to a dependence expression in a reduction.
   * The schedules on these nodes are used to split the corresponding
   * reductions optimally.
   * 
   * Functions exactly the same as a regular PRDGNode, but can be distinguished from
   * actual nodes using 'instanceof'.
   */
  private static class DummyNode extends PRDGNode {
    public DummyNode(final String name, final ISLSet domain) {
      super(name, domain, false);
    }
  }

  /**
   * A helper visitor that serializes each piece of a reduction.
   * `expr` should have each piece of the reduction as its descendants.
   * `reuseDepMap` maps domains to multi-reuse dependences. If the domain of
   * a reduction is a subset of one of the keys of `reuseDepMap`, then it will
   * be serialized according to the respective value.
   */
  private static class ReductionSerializer extends AbstractAlphaCompleteVisitor {
    private Map<ISLSet, Iterable<ISLMultiAff>> reuseDepMap;

    public static void apply(final AlphaVisitable expr, final Map<ISLSet, Iterable<ISLMultiAff>> reuseDepMap) {
      final OptimallySplitReductions.ReductionSerializer serializer = new OptimallySplitReductions.ReductionSerializer(reuseDepMap);
      expr.accept(serializer);
    }

    public ReductionSerializer(final Map<ISLSet, Iterable<ISLMultiAff>> reuseDepMap) {
      this.reuseDepMap = reuseDepMap;
    }

    @Override
    public void outReduceExpression(final ReduceExpression reduceExpression) {
      final ISLSet domain = reduceExpression.getBody().getContextDomain();
      final Function1<ISLSet, Boolean> _function = (ISLSet key) -> {
        return Boolean.valueOf(domain.copy().isSubset(key.copy()));
      };
      final ISLSet superDomain = IterableExtensions.<ISLSet>findFirst(this.reuseDepMap.keySet(), _function);
      final Iterable<ISLMultiAff> reuseDeps = this.reuseDepMap.get(superDomain);
      SerializeReduction.applyAll(reduceExpression, reuseDeps);
    }
  }

  public static void apply(final AlphaSystem sys) {
    NormalizeReduction.apply(sys);
    final PRDG prdg = PRDGGenerator.apply(sys);
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge edge) -> {
      return Boolean.valueOf((edge.getSource().isReductionNode() && (!edge.getDest().isReductionNode())));
    };
    final Set<PRDGEdge> reductionEdges = IterableExtensions.<PRDGEdge>toSet(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function));
    final Function1<PRDGEdge, Boolean> _function_1 = (PRDGEdge edge) -> {
      final Function1<PRDGEdge, Boolean> _function_2 = (PRDGEdge other) -> {
        return Boolean.valueOf(((!Objects.equal(edge, other)) && Objects.equal(edge.getSource(), other.getSource())));
      };
      return Boolean.valueOf(IterableExtensions.<PRDGEdge>exists(reductionEdges, _function_2));
    };
    final Set<PRDGEdge> splittableEdges = IterableExtensions.<PRDGEdge>toSet(IterableExtensions.<PRDGEdge>filter(reductionEdges, _function_1));
    boolean _isEmpty = splittableEdges.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      OptimallySplitReductions.extendPRDG(prdg, splittableEdges);
    }
    ISLUnionSet domains = prdg.generateDomains();
    ISLUnionMap islPRDG = prdg.generateISLPRDG();
    final ISLSchedule schedule = ISLSchedule.computeSchedule(domains, islPRDG, ISLSchedule.JNIISLSchedulingOptions.ISL_SCHEDULE_ALGORITHM_FEAUTRIER);
    final Function1<ISLMap, Boolean> _function_2 = (ISLMap map) -> {
      PRDGNode _node = prdg.getNode(map.getInputTupleName());
      return Boolean.valueOf((!(_node instanceof OptimallySplitReductions.DummyNode)));
    };
    final int nTimeDims = ISLUtil.countTimeDimensions(sys, 
      ISLUtil.convertToUnionMap(IterableExtensions.<ISLMap>toList(IterableExtensions.<ISLMap>filter(schedule.getMap().getMaps(), _function_2))));
    final Function1<PRDGNode, Boolean> _function_3 = (PRDGNode node) -> {
      return Boolean.valueOf((node instanceof OptimallySplitReductions.DummyNode));
    };
    final Function1<PRDGNode, ISLMap> _function_4 = (PRDGNode node) -> {
      final PRDGNode reductionNode = OptimallySplitReductions.correspondingReductionNode(prdg, ((OptimallySplitReductions.DummyNode) node));
      return ISLUtil.convertToMultiAff(ISLUtil.toMultiAff(OptimallySplitReductions.processDummySchedule(schedule, reductionNode, node)).getAffs().subList(0, nTimeDims)).toMap();
    };
    final ISLUnionMap timeMap = ISLUtil.liftSpacetimeFactors(
      ISLUtil.convertToUnionMap(
        IterableExtensions.<ISLMap>toList(IterableExtensions.<PRDGNode, ISLMap>map(IterableExtensions.<PRDGNode>filter(prdg.getNodes(), _function_3), _function_4))));
    OptimallySplitReductions.split(sys, timeMap, prdg);
  }

  private static void extendPRDG(final PRDG prdg, final Set<PRDGEdge> splittableEdges) {
    final Function1<PRDGEdge, PRDGNode> _function = (PRDGEdge edge) -> {
      return edge.getSource();
    };
    final Set<PRDGNode> reductionBodyNodes = IterableExtensions.<PRDGNode>toSet(IterableExtensions.<PRDGEdge, PRDGNode>map(splittableEdges, _function));
    final Consumer<PRDGNode> _function_1 = (PRDGNode node) -> {
      final List<PRDGNode> dummyNodes = OptimallySplitReductions.setupDummyNodes(prdg, node);
      OptimallySplitReductions.setupDummyEdges(prdg, node, dummyNodes);
    };
    reductionBodyNodes.forEach(_function_1);
  }

  private static List<PRDGNode> setupDummyNodes(final PRDG prdg, final PRDGNode node) {
    ArrayList<PRDGNode> dummyNodes = new ArrayList<PRDGNode>();
    final ISLSet productDomain = node.getDomain().flatProduct(node.getDomain()).flatProduct(ISLUtil.toISLSet("[]->{[c] : }"));
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge edge) -> {
      return Boolean.valueOf((edge.isReductionEdge() && Objects.equal(edge.getDest(), node)));
    };
    final ISLMap writeMap = (((PRDGEdge[])Conversions.unwrapArray(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function), PRDGEdge.class))[0]).getMap().copy().reverse();
    final ISLMap firstWriteMap = OptimallySplitReductions.firstPointMap(productDomain).applyRange(writeMap.copy());
    final ISLMap secondWriteMap = OptimallySplitReductions.secondPointMap(productDomain).applyRange(writeMap.copy());
    final ISLSet writeLexEQSet = ISLUtil.buildLexEQSet(
      ISLUtil.toMultiAff(firstWriteMap), 
      ISLUtil.toMultiAff(secondWriteMap));
    final ISLSet domain = productDomain.intersect(writeLexEQSet).simplify();
    final List<PRDGEdge> dependenceEdges = OptimallySplitReductions.getReadEdges(prdg, node);
    for (int i = 0; (i < dependenceEdges.size()); i++) {
      {
        String _name = node.getName();
        String _plus = (_name + "_DUMMY");
        String _plus_1 = (_plus + Integer.valueOf(i));
        OptimallySplitReductions.DummyNode dummyNode = new OptimallySplitReductions.DummyNode(_plus_1, domain);
        prdg.addNode(dummyNode);
        dummyNodes.add(dummyNode);
      }
    }
    return dummyNodes;
  }

  /**
   * Sets up the dummy edges using the techniques outlined in the paper (see wiki)
   */
  private static void setupDummyEdges(final PRDG prdg, final PRDGNode node, final List<PRDGNode> dummyNodes) {
    final List<PRDGEdge> readEdges = OptimallySplitReductions.getReadEdges(prdg, node);
    for (int i = 0; (i < readEdges.size()); i++) {
      {
        OptimallySplitReductions.setupScheduleMatchingEdges(prdg, readEdges.get(i), dummyNodes.get(i));
        OptimallySplitReductions.setupPeakBoundingEdges(prdg, node, dummyNodes.get(i));
      }
    }
  }

  /**
   * Force the dummy node to have a schedule corresponding to the real one
   * i.e. the lambdas for each dimension in _R_DUMMYi should be the same as
   * the corresponding dimension in R
   */
  private static boolean setupScheduleMatchingEdges(final PRDG prdg, final PRDGEdge readEdge, final PRDGNode dummyNode) {
    boolean _xblockexpression = false;
    {
      final PRDGNode readNode = readEdge.getDest();
      final ISLMap EQMap = ISLUtil.toMultiAff(OptimallySplitReductions.firstPointMap(dummyNode.getDomain())).add(ISLUtil.toMultiAff(OptimallySplitReductions.secondPointMap(dummyNode.getDomain()))).toMap().applyRange(readEdge.getMap());
      final ISLSet GEDomain = OptimallySplitReductions.withCEqualTo(ISLSet.buildUniverse(dummyNode.getSpace()), 1);
      final ISLMap GEMap = EQMap.copy().intersectDomain(GEDomain);
      final PRDGEdge GEEdge = new PRDGEdge(dummyNode, readNode, GEMap);
      prdg.addEdge(GEEdge);
      final ISLSet LEDomain = OptimallySplitReductions.withCEqualTo(ISLSet.buildUniverse(dummyNode.getSpace()), (-1));
      final ISLMap LEMap = EQMap.copy().reverse().intersectRange(LEDomain);
      final PRDGEdge LEEdge = new PRDGEdge(readNode, dummyNode, LEMap);
      _xblockexpression = prdg.addEdge(LEEdge);
    }
    return _xblockexpression;
  }

  /**
   * Apply constraints that guarantee the peak is bounded.
   * for all i, R_RESULT[2*w(x)] -> _R_DUMMYi[x, x', c]
   * with addtl. constraint x_i-x'_i >= c
   */
  private static void setupPeakBoundingEdges(final PRDG prdg, final PRDGNode node, final PRDGNode dummyNode) {
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge edge) -> {
      return Boolean.valueOf((edge.isReductionEdge() && Objects.equal(edge.getDest(), node)));
    };
    final PRDGNode resultNode = (((PRDGEdge[])Conversions.unwrapArray(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function), PRDGEdge.class))[0]).getSource();
    final Function1<PRDGEdge, Boolean> _function_1 = (PRDGEdge edge) -> {
      return Boolean.valueOf((edge.isReductionEdge() && Objects.equal(edge.getDest(), node)));
    };
    final ISLMap writeMap = (((PRDGEdge[])Conversions.unwrapArray(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function_1), PRDGEdge.class))[0]).getMap().copy().reverse();
    int _dim = dummyNode.getDomain().dim(ISLDimType.isl_dim_out);
    int _divide = (_dim / 2);
    double _floor = Math.floor(_divide);
    final int halfDim = ((int) _floor);
    for (int i = 0; (i < halfDim); i++) {
      {
        final ISLAff distAff = ISLAff.buildVarOnDomain(dummyNode.getLocalSpace(), ISLDimType.isl_dim_out, i).sub(ISLAff.buildVarOnDomain(dummyNode.getLocalSpace(), ISLDimType.isl_dim_out, (i + halfDim)));
        final boolean distAffIsZero = ISLSet.buildEQSet(distAff.copy(), ISLAff.buildZero(dummyNode.getLocalSpace())).intersect(dummyNode.getDomain()).isPlainEqual(dummyNode.getDomain());
        if ((!distAffIsZero)) {
          final ISLAff cAff = ISLAff.buildVarOnDomain(dummyNode.getLocalSpace(), ISLDimType.isl_dim_out, (halfDim * 2));
          final ISLSet cDistConstraint = ISLSet.buildGESet(distAff, cAff);
          final ISLSet constrainedDomain = dummyNode.getDomain().intersect(cDistConstraint);
          final ISLMap iBoundedMap = ISLUtil.toMultiAff(OptimallySplitReductions.firstPointMap(dummyNode.getDomain())).add(ISLUtil.toMultiAff(OptimallySplitReductions.secondPointMap(dummyNode.getDomain()))).toMap().applyRange(writeMap.copy()).intersectDomain(constrainedDomain).reverse();
          final PRDGEdge iBoundedEdge = new PRDGEdge(resultNode, dummyNode, iBoundedMap);
          prdg.addEdge(iBoundedEdge);
        }
      }
    }
  }

  /**
   * Generates the map [x, x', c] -> [x]
   */
  private static ISLMap firstPointMap(final ISLSet productDomain) {
    int _dim = productDomain.dim(ISLDimType.isl_dim_out);
    int _divide = (_dim / 2);
    double _floor = Math.floor(_divide);
    final int halfDim = ((int) _floor);
    return productDomain.copy().identity().projectOut(ISLDimType.isl_dim_out, halfDim, halfDim).projectOut(ISLDimType.isl_dim_out, halfDim, 1);
  }

  /**
   * Generates the map [x, x', c] -> [x']
   */
  private static ISLMap secondPointMap(final ISLSet productDomain) {
    int _dim = productDomain.dim(ISLDimType.isl_dim_out);
    int _divide = (_dim / 2);
    double _floor = Math.floor(_divide);
    final int halfDim = ((int) _floor);
    return productDomain.copy().identity().projectOut(ISLDimType.isl_dim_out, 0, halfDim).projectOut(ISLDimType.isl_dim_out, halfDim, 1);
  }

  /**
   * Creates a set that constrains c (the last dimension of _R_DUMMY)
   * to be equal to an integer value
   */
  private static ISLSet withCEqualTo(final ISLSet productDomain, final int c) {
    final int dim = productDomain.dim(ISLDimType.isl_dim_out);
    return productDomain.copy().addConstraint(
      ISLConstraint.buildEquality(productDomain.getSpace().copy()).setCoefficient(ISLDimType.isl_dim_out, (dim - 1), (-1)).setConstant(c));
  }

  private static List<PRDGEdge> getReadEdges(final PRDG prdg, final PRDGNode node) {
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge edge) -> {
      return Boolean.valueOf((Objects.equal(edge.getSource(), node) && prdg.getNodes().contains(edge.getDest())));
    };
    return IterableExtensions.<PRDGEdge>toList(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function));
  }

  private static void split(final AlphaSystem sys, final ISLUnionMap timeMap, final PRDG prdg) {
    final Function1<PRDGNode, Boolean> _function = (PRDGNode node) -> {
      return Boolean.valueOf(node.isReductionNode());
    };
    Iterable<PRDGNode> _filter = IterableExtensions.<PRDGNode>filter(prdg.getNodes(), _function);
    for (final PRDGNode node : _filter) {
      {
        final Iterable<OptimallySplitReductions.DummyNode> dummyNodes = OptimallySplitReductions.correspondingDummyNodes(prdg, node);
        final Function1<OptimallySplitReductions.DummyNode, ISLMultiAff> _function_1 = (OptimallySplitReductions.DummyNode dummyNode) -> {
          final Function1<ISLMap, Boolean> _function_2 = (ISLMap map) -> {
            String _inputTupleName = map.getInputTupleName();
            String _name = dummyNode.getName();
            return Boolean.valueOf(Objects.equal(_inputTupleName, _name));
          };
          return ISLUtil.toMultiAff(IterableExtensions.<ISLMap>findFirst(timeMap.getMaps(), _function_2).copy().clearInputTupleName());
        };
        final Iterable<ISLMultiAff> maffs = IterableExtensions.<OptimallySplitReductions.DummyNode, ISLMultiAff>map(dummyNodes, _function_1);
        AlphaExpression _expr = node.getOriginEquation(sys).getExpr();
        final AbstractReduceExpression are = ((AbstractReduceExpression) _expr);
        final int nullspaceDim = ISLUtil.dimensionality(ISLUtil.nullSpace(are.getProjection().copy()));
        final Function1<ISLPoint, ISLMultiAff> _function_2 = (ISLPoint vector) -> {
          return ISLUtil.buildProjectionMaff(vector);
        };
        final Function2<ISLMultiAff, ISLMultiAff, ISLMultiAff> _function_3 = (ISLMultiAff m1, ISLMultiAff m2) -> {
          return m1.add(m2);
        };
        final ISLMultiAff projMaff = IterableExtensions.<ISLMultiAff>reduce(ListExtensions.<ISLPoint, ISLMultiAff>map(ISLUtil.getBasisVectors(ISLUtil.nullSpace(are.getProjection().copy())), _function_2), _function_3);
        final Iterable<ISLSet> splitPieces = SplitReduction.applyDominanceSplit(are, maffs);
        final Equation equation = node.getOriginEquation(sys);
        final Function1<ISLMultiAff, ISLMultiAff> _function_4 = (ISLMultiAff maff) -> {
          final Function1<ISLAff, ISLAff> _function_5 = (ISLAff aff) -> {
            ISLAff _xblockexpression = null;
            {
              int _dim = aff.dim(ISLDimType.isl_dim_in);
              int _minus = (_dim - 1);
              final Function1<Integer, ISLVal> _function_6 = (Integer i) -> {
                return aff.getCoefficientVal(ISLDimType.isl_dim_in, i);
              };
              final Function1<ISLVal, Boolean> _function_7 = (ISLVal a) -> {
                boolean _isZero = a.isZero();
                return Boolean.valueOf((!_isZero));
              };
              final Function2<ISLVal, ISLVal, ISLVal> _function_8 = (ISLVal a, ISLVal b) -> {
                return a.copy().abs().gcd(b.copy().abs());
              };
              final ISLVal factor = IterableExtensions.<ISLVal>reduce(IterableExtensions.<ISLVal>filter(IterableExtensions.<Integer, ISLVal>map(new IntegerRange(0, _minus), _function_6), _function_7), _function_8);
              ISLAff _xifexpression = null;
              if ((factor == null)) {
                _xifexpression = aff.copy();
              } else {
                _xifexpression = aff.copy().setConstant(0).scaleDown(factor);
              }
              _xblockexpression = _xifexpression;
            }
            return _xblockexpression;
          };
          return ISLUtil.convertToMultiAff(ListExtensions.<ISLAff, ISLAff>map(maff.getAffs(), _function_5));
        };
        final Iterable<ISLMultiAff> reducedMaffs = IterableExtensions.<ISLMultiAff, ISLMultiAff>map(maffs, _function_4);
        final Function1<ISLMultiAff, Iterable<ISLMultiAff>> _function_5 = (ISLMultiAff maff) -> {
          final ISLLocalSpace localSpace = maff.copy().toMap().getDomain().getSpace().toLocalSpace();
          final Function1<ISLAff, ISLMultiAff> _function_6 = (ISLAff aff) -> {
            ISLMultiAff _xblockexpression = null;
            {
              int _dim = aff.dim(ISLDimType.isl_dim_in);
              int _minus = (_dim - 1);
              final Function1<Integer, ISLAff> _function_7 = (Integer i) -> {
                return ISLAff.buildValOnDomain(localSpace.copy(), aff.getCoefficientVal(ISLDimType.isl_dim_in, (i).intValue())).negate();
              };
              final ISLMultiAff depMaff = ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(IterableExtensions.<Integer, ISLAff>map(new IntegerRange(0, _minus), _function_7)));
              _xblockexpression = projMaff.copy().pullback(depMaff.copy()).add(ISLMultiAff.buildIdentity(depMaff.getSpace().copy()));
            }
            return _xblockexpression;
          };
          final Function1<ISLMultiAff, Boolean> _function_7 = (ISLMultiAff dep) -> {
            boolean _isIdentity = dep.isIdentity();
            return Boolean.valueOf((!_isIdentity));
          };
          final Iterable<ISLMultiAff> reuseDeps = IterableExtensions.<ISLMultiAff>filter(ListExtensions.<ISLAff, ISLMultiAff>map(maff.getAffs(), _function_6), _function_7);
          return IterableExtensions.<ISLMultiAff>toList(reuseDeps).subList(0, Math.min(nullspaceDim, IterableExtensions.size(reuseDeps)));
        };
        final Iterable<Iterable<ISLMultiAff>> reuseDepsList = IterableExtensions.<ISLMultiAff, Iterable<ISLMultiAff>>map(reducedMaffs, _function_5);
        int _size = IterableExtensions.size(splitPieces);
        int _minus = (_size - 1);
        final Function1<Integer, ISLSet> _function_6 = (Integer i) -> {
          return ((ISLSet[])Conversions.unwrapArray(splitPieces, ISLSet.class))[(i).intValue()];
        };
        final Function1<Integer, Iterable<ISLMultiAff>> _function_7 = (Integer i) -> {
          return ((Iterable<ISLMultiAff>[])Conversions.unwrapArray(reuseDepsList, Iterable.class))[(i).intValue()];
        };
        final Map<ISLSet, Iterable<ISLMultiAff>> depsMap = IterableExtensions.<Integer, ISLSet, Iterable<ISLMultiAff>>toMap(new IntegerRange(0, _minus), _function_6, _function_7);
        OptimallySplitReductions.ReductionSerializer.apply(equation, depsMap);
      }
    }
  }

  /**
   * Gets the DummyNodes in a PRDG that correspond to a given real reduction node.
   */
  private static Iterable<OptimallySplitReductions.DummyNode> correspondingDummyNodes(final PRDG prdg, final PRDGNode reductionNode) {
    final Function1<PRDGNode, Boolean> _function = (PRDGNode node) -> {
      return Boolean.valueOf(((node instanceof OptimallySplitReductions.DummyNode) && Objects.equal(reductionNode.getName(), node.getName().substring(0, node.getName().lastIndexOf("_DUMMY")))));
    };
    final Function1<PRDGNode, OptimallySplitReductions.DummyNode> _function_1 = (PRDGNode node) -> {
      return ((OptimallySplitReductions.DummyNode) node);
    };
    return IterableExtensions.<PRDGNode, OptimallySplitReductions.DummyNode>map(IterableExtensions.<PRDGNode>filter(prdg.getNodes(), _function), _function_1);
  }

  /**
   * Gets the real reduction PRDGNode in a PRDG that corresponds to a given dummy node.
   */
  private static PRDGNode correspondingReductionNode(final PRDG prdg, final OptimallySplitReductions.DummyNode dummyNode) {
    final Function1<PRDGNode, Boolean> _function = (PRDGNode node) -> {
      return Boolean.valueOf(((!(node instanceof OptimallySplitReductions.DummyNode)) && Objects.equal(node.getName(), dummyNode.getName().substring(0, dummyNode.getName().lastIndexOf("_DUMMY")))));
    };
    return IterableExtensions.<PRDGNode>findFirst(prdg.getNodes(), _function);
  }

  /**
   * Converts the virtual schedule of the dummy nodes into a schedule on the corresponding reduction node
   * The tuple name is not present on the processed schedule
   */
  private static ISLMap processDummySchedule(final ISLSchedule schedule, final PRDGNode reductionNode, final PRDGNode dummyNode) {
    final ISLMap productMap = reductionNode.getDomain().identity().rangeProduct(reductionNode.getDomain().identity()).rangeProduct(ISLAff.buildZero(reductionNode.getLocalSpace()).toMultiAff().toMap()).flatten();
    final Function1<ISLMap, Boolean> _function = (ISLMap map) -> {
      String _inputTupleName = map.getInputTupleName();
      String _name = dummyNode.getName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, _name));
    };
    final ISLMap rawSchedule = IterableExtensions.<ISLMap>head(IterableExtensions.<ISLMap>filter(schedule.getMap().getMaps(), _function)).copy().clearInputTupleName();
    return productMap.applyRange(rawSchedule).<ISLMap>setInputTupleName(dummyNode.getName());
  }
}
