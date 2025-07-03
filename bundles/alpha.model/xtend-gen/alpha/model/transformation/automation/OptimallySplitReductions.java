package alpha.model.transformation.automation;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.ReduceExpression;
import alpha.model.Variable;
import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGEdge;
import alpha.model.prdg.PRDGGenerator;
import alpha.model.prdg.PRDGNode;
import alpha.model.scheduler.FoutrierScheduler;
import alpha.model.scheduler.Scheduler;
import alpha.model.transformation.reduction.NormalizeReduction;
import alpha.model.transformation.reduction.SerializeReduction;
import alpha.model.transformation.reduction.SplitReduction;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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

    protected Set<Variable> newVariables;

    public static void apply(final AlphaVisitable expr, final Map<ISLSet, Iterable<ISLMultiAff>> reuseDepMap) {
      final OptimallySplitReductions.ReductionSerializer serializer = new OptimallySplitReductions.ReductionSerializer(reuseDepMap);
      expr.accept(serializer);
    }

    public ReductionSerializer(final Map<ISLSet, Iterable<ISLMultiAff>> reuseDepMap) {
      this.reuseDepMap = reuseDepMap;
      HashSet<Variable> _hashSet = new HashSet<Variable>();
      this.newVariables = _hashSet;
    }

    @Override
    public void outReduceExpression(final ReduceExpression reduceExpression) {
      final ISLSet domain = reduceExpression.getBody().getContextDomain();
      final Function1<ISLSet, Boolean> _function = (ISLSet key) -> {
        return Boolean.valueOf(domain.copy().isSubset(key.copy()));
      };
      final ISLSet superDomain = IterableExtensions.<ISLSet>findFirst(this.reuseDepMap.keySet(), _function);
      final Iterable<ISLMultiAff> reuseDeps = this.reuseDepMap.get(superDomain);
      for (final ISLMultiAff dep : reuseDeps) {
        this.newVariables.add(SerializeReduction.applyOneShot(reduceExpression, dep));
      }
    }
  }

  /**
   * Applies the optimal splitting process to a system.
   */
  public static void apply(final AlphaSystem sys) {
    NormalizeReduction.apply(sys);
    final PRDG prdg = PRDGGenerator.apply(sys);
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge it) -> {
      return Boolean.valueOf((it.getSource().isReductionNode() && (!it.getDest().isReductionNode())));
    };
    final Iterable<PRDGEdge> reductionEdges = IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function);
    final Function1<PRDGEdge, Boolean> _function_1 = (PRDGEdge it) -> {
      final Function1<PRDGEdge, Boolean> _function_2 = (PRDGEdge other) -> {
        return Boolean.valueOf(((!Objects.equal(it, other)) && Objects.equal(it.getSource(), other.getSource())));
      };
      return Boolean.valueOf(IterableExtensions.<PRDGEdge>exists(reductionEdges, _function_2));
    };
    final Iterable<PRDGEdge> splittableEdges = IterableExtensions.<PRDGEdge>filter(reductionEdges, _function_1);
    boolean _isEmpty = IterableExtensions.isEmpty(splittableEdges);
    if (_isEmpty) {
      return;
    }
    final PRDG extendedPrdg = OptimallySplitReductions.extendPRDG(prdg, splittableEdges);
    final FoutrierScheduler scheduler = new FoutrierScheduler(extendedPrdg);
    OptimallySplitReductions.split(sys, scheduler, extendedPrdg);
  }

  private static PRDG extendPRDG(final PRDG prdg, final Iterable<PRDGEdge> splittableEdges) {
    final Function1<PRDGEdge, PRDGNode> _function = (PRDGEdge it) -> {
      return it.getSource();
    };
    final Iterable<PRDGNode> reductionBodyNodes = IterableExtensions.<PRDGEdge, PRDGNode>map(splittableEdges, _function);
    final Function1<PRDGNode, Iterable<OptimallySplitReductions.DummyNode>> _function_1 = (PRDGNode it) -> {
      return OptimallySplitReductions.setupDummyNodes(prdg, it);
    };
    final Iterable<Iterable<OptimallySplitReductions.DummyNode>> dummyNodes = IterableExtensions.<PRDGNode, Iterable<OptimallySplitReductions.DummyNode>>map(reductionBodyNodes, _function_1);
    final Function1<Iterable<OptimallySplitReductions.DummyNode>, Iterable<PRDGEdge>> _function_2 = (Iterable<OptimallySplitReductions.DummyNode> it) -> {
      return OptimallySplitReductions.setupDummyEdges(prdg, it);
    };
    final Iterable<Iterable<PRDGEdge>> dummyEdges = IterableExtensions.<Iterable<OptimallySplitReductions.DummyNode>, Iterable<PRDGEdge>>map(dummyNodes, _function_2);
    final PRDG extendedPrdg = new PRDG();
    Set<PRDGNode> _nodes = prdg.getNodes();
    Iterable<OptimallySplitReductions.DummyNode> _flatten = Iterables.<OptimallySplitReductions.DummyNode>concat(dummyNodes);
    extendedPrdg.setNodes(IterableExtensions.<PRDGNode>toSet(Iterables.<PRDGNode>concat(_nodes, _flatten)));
    Set<PRDGEdge> _edges = prdg.getEdges();
    Iterable<PRDGEdge> _flatten_1 = Iterables.<PRDGEdge>concat(dummyEdges);
    extendedPrdg.setEdges(IterableExtensions.<PRDGEdge>toSet(Iterables.<PRDGEdge>concat(_edges, _flatten_1)));
    return extendedPrdg;
  }

  private static Iterable<OptimallySplitReductions.DummyNode> setupDummyNodes(final PRDG prdg, final PRDGNode node) {
    final ISLSet productDomain = node.getDomain().flatProduct(node.getDomain()).flatProduct(ISLUtil.toISLSet("[]->{[c] : }"));
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge it) -> {
      return Boolean.valueOf((it.isReductionEdge() && Objects.equal(it.getDest(), node)));
    };
    final ISLMap writeMap = IterableExtensions.<PRDGEdge>findFirst(prdg.getEdges(), _function).getMap().copy().reverse();
    final ISLMap firstWriteMap = OptimallySplitReductions.firstPointMap(productDomain).applyRange(writeMap.copy());
    final ISLMap secondWriteMap = OptimallySplitReductions.secondPointMap(productDomain).applyRange(writeMap.copy());
    final ISLSet writeLexEQSet = ISLUtil.buildLexEQSet(
      ISLUtil.toMultiAff(firstWriteMap), 
      ISLUtil.toMultiAff(secondWriteMap));
    final ISLSet domain = productDomain.intersect(writeLexEQSet).simplify();
    final Iterable<PRDGEdge> dependenceEdges = OptimallySplitReductions.getReadEdges(prdg, node);
    int _size = IterableExtensions.size(dependenceEdges);
    final Function1<Integer, OptimallySplitReductions.DummyNode> _function_1 = (Integer i) -> {
      String _name = node.getName();
      String _plus = (_name + "_DUMMY");
      String _plus_1 = (_plus + i);
      return new OptimallySplitReductions.DummyNode(_plus_1, domain);
    };
    final Iterable<OptimallySplitReductions.DummyNode> dummyNodes = IterableExtensions.<Integer, OptimallySplitReductions.DummyNode>map(new ExclusiveRange(0, _size, true), _function_1);
    return dummyNodes;
  }

  /**
   * Sets up the dummy edges using the techniques outlined in the paper (see wiki)
   */
  private static Iterable<PRDGEdge> setupDummyEdges(final PRDG prdg, final Iterable<OptimallySplitReductions.DummyNode> dummyNodes) {
    Iterable<PRDGEdge> _xblockexpression = null;
    {
      final Function1<OptimallySplitReductions.DummyNode, Boolean> _function = (OptimallySplitReductions.DummyNode it) -> {
        return Boolean.valueOf(true);
      };
      final PRDGNode reductionNode = OptimallySplitReductions.correspondingReductionNode(IterableExtensions.<OptimallySplitReductions.DummyNode>findFirst(dummyNodes, _function), prdg);
      final Iterable<PRDGEdge> readEdges = OptimallySplitReductions.getReadEdges(prdg, reductionNode);
      int _size = IterableExtensions.size(readEdges);
      final Function1<Integer, Iterable<PRDGEdge>> _function_1 = (Integer i) -> {
        Iterable<PRDGEdge> _setupScheduleMatchingEdges = OptimallySplitReductions.setupScheduleMatchingEdges(prdg, ((PRDGEdge[])Conversions.unwrapArray(readEdges, PRDGEdge.class))[(i).intValue()], ((PRDGNode[])Conversions.unwrapArray(dummyNodes, PRDGNode.class))[(i).intValue()]);
        Iterable<PRDGEdge> _setupPeakBoundingEdges = OptimallySplitReductions.setupPeakBoundingEdges(prdg, reductionNode, ((PRDGNode[])Conversions.unwrapArray(dummyNodes, PRDGNode.class))[(i).intValue()]);
        return Iterables.<PRDGEdge>concat(_setupScheduleMatchingEdges, _setupPeakBoundingEdges);
      };
      _xblockexpression = IterableExtensions.<Integer, PRDGEdge>flatMap(new ExclusiveRange(0, _size, true), _function_1);
    }
    return _xblockexpression;
  }

  /**
   * Force the dummy node to have a schedule corresponding to the real one
   * i.e. the lambdas for each dimension in _R_DUMMYi should be the same as
   * the corresponding dimension in R
   */
  private static Iterable<PRDGEdge> setupScheduleMatchingEdges(final PRDG prdg, final PRDGEdge readEdge, final PRDGNode dummyNode) {
    final PRDGNode readNode = readEdge.getDest();
    final ISLMap EQMap = ISLUtil.toMultiAff(OptimallySplitReductions.firstPointMap(dummyNode.getDomain())).add(ISLUtil.toMultiAff(OptimallySplitReductions.secondPointMap(dummyNode.getDomain()))).toMap().applyRange(readEdge.getMap());
    final ISLSet GEDomain = OptimallySplitReductions.withCEqualTo(EQMap.getDomain(), 1);
    final ISLMap GEMap = EQMap.copy().intersectDomain(GEDomain);
    final PRDGEdge GEEdge = new PRDGEdge(dummyNode, readNode, GEMap);
    final ISLSet LEDomain = OptimallySplitReductions.withCEqualTo(EQMap.getDomain(), (-1));
    final ISLMap LEMap = EQMap.copy().reverse().intersectRange(LEDomain);
    final PRDGEdge LEEdge = new PRDGEdge(readNode, dummyNode, LEMap);
    return Collections.<PRDGEdge>unmodifiableList(CollectionLiterals.<PRDGEdge>newArrayList(LEEdge, GEEdge));
  }

  /**
   * Apply constraints that guarantee the peak is bounded.
   * for all i, R_RESULT[2*w(x)] -> _R_DUMMYi[x, x', c]
   * with addtl. constraint x_i-x'_i >= c
   */
  private static Iterable<PRDGEdge> setupPeakBoundingEdges(final PRDG prdg, final PRDGNode node, final PRDGNode dummyNode) {
    Iterable<PRDGEdge> _xblockexpression = null;
    {
      final ISLSet domain = dummyNode.getDomain();
      final Function1<PRDGEdge, Boolean> _function = (PRDGEdge it) -> {
        return Boolean.valueOf((it.isReductionEdge() && Objects.equal(it.getDest(), node)));
      };
      final PRDGEdge writeEdge = IterableExtensions.<PRDGEdge>findFirst(prdg.getEdges(), _function);
      final ISLMap writeMap = writeEdge.getMap().copy().reverse();
      int _dim = domain.dim(ISLUtil.Dims.OUT);
      int _divide = (_dim / 2);
      double _floor = Math.floor(_divide);
      final int halfDim = ((int) _floor);
      final ISLAff cAff = ISLAff.buildVarOnDomain(dummyNode.getLocalSpace(), ISLUtil.Dims.OUT, (halfDim * 2));
      final ISLMap firstPlusSecondPoint = ISLUtil.toMultiAff(OptimallySplitReductions.firstPointMap(domain)).add(ISLUtil.toMultiAff(OptimallySplitReductions.secondPointMap(domain))).toMap().applyRange(writeMap.copy());
      final Function1<Integer, ISLAff> _function_1 = (Integer i) -> {
        return ISLAff.buildVarOnDomain(dummyNode.getLocalSpace(), ISLUtil.Dims.OUT, (i).intValue()).sub(ISLAff.buildVarOnDomain(dummyNode.getLocalSpace(), ISLUtil.Dims.OUT, ((i).intValue() + halfDim)));
      };
      final Iterable<ISLAff> diffAffs = IterableExtensions.<Integer, ISLAff>map(new ExclusiveRange(0, halfDim, true), _function_1);
      final Function1<ISLAff, Boolean> _function_2 = (ISLAff it) -> {
        return Boolean.valueOf(domain.copy().apply(it.copy().toMultiAff().toMap()).isSingleton());
      };
      final Function1<ISLAff, ISLSet> _function_3 = (ISLAff it) -> {
        return ISLSet.buildGESet(it, cAff.copy());
      };
      final Function1<ISLSet, ISLSet> _function_4 = (ISLSet it) -> {
        return it.intersect(domain.copy());
      };
      final Function1<ISLSet, ISLMap> _function_5 = (ISLSet it) -> {
        return firstPlusSecondPoint.copy().intersectDomain(it);
      };
      final Function1<ISLMap, ISLMap> _function_6 = (ISLMap it) -> {
        return it.reverse();
      };
      final Function1<ISLMap, PRDGEdge> _function_7 = (ISLMap it) -> {
        PRDGNode _source = writeEdge.getSource();
        return new PRDGEdge(_source, dummyNode, it);
      };
      _xblockexpression = IterableExtensions.<ISLMap, PRDGEdge>map(IterableExtensions.<ISLMap, ISLMap>map(IterableExtensions.<ISLSet, ISLMap>map(IterableExtensions.<ISLSet, ISLSet>map(IterableExtensions.<ISLAff, ISLSet>map(IterableExtensions.<ISLAff>filter(diffAffs, _function_2), _function_3), _function_4), _function_5), _function_6), _function_7);
    }
    return _xblockexpression;
  }

  /**
   * Generates the map [x, x', c] -> [x]
   */
  private static ISLMap firstPointMap(final ISLSet productDomain) {
    int _dim = productDomain.dim(ISLUtil.Dims.OUT);
    int _divide = (_dim / 2);
    double _floor = Math.floor(_divide);
    final int halfDim = ((int) _floor);
    return productDomain.copy().identity().projectOut(ISLUtil.Dims.OUT, halfDim, halfDim).projectOut(ISLUtil.Dims.OUT, halfDim, 1);
  }

  /**
   * Generates the map [x, x', c] -> [x']
   */
  private static ISLMap secondPointMap(final ISLSet productDomain) {
    int _dim = productDomain.dim(ISLDimType.isl_dim_out);
    int _divide = (_dim / 2);
    double _floor = Math.floor(_divide);
    final int halfDim = ((int) _floor);
    return productDomain.copy().identity().projectOut(ISLUtil.Dims.OUT, 0, halfDim).projectOut(ISLUtil.Dims.OUT, halfDim, 1);
  }

  /**
   * Creates a set that constrains c (the last dimension of _R_DUMMY)
   * to be equal to an integer value
   */
  private static ISLSet withCEqualTo(final ISLSet productDomain, final int c) {
    final int dim = productDomain.dim(ISLUtil.Dims.OUT);
    final ISLConstraint cConstraint = ISLConstraint.buildEquality(productDomain.getSpace().copy()).setCoefficient(ISLUtil.Dims.OUT, (dim - 1), (-1)).setConstant(c);
    return productDomain.copy().addConstraint(cConstraint);
  }

  private static Iterable<PRDGEdge> getReadEdges(final PRDG prdg, final PRDGNode node) {
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge it) -> {
      PRDGNode _source = it.getSource();
      return Boolean.valueOf(Objects.equal(_source, node));
    };
    final Function1<PRDGEdge, Boolean> _function_1 = (PRDGEdge it) -> {
      return Boolean.valueOf(prdg.getNodes().contains(it.getDest()));
    };
    return IterableExtensions.<PRDGEdge>filter(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function), _function_1);
  }

  /**
   * Splits every reduction in a system according to read function dominance.
   */
  private static Object split(final AlphaSystem sys, final Scheduler scheduler, final PRDG prdg) {
    Object _xblockexpression = null;
    {
      final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
        PRDGNode _node = prdg.getNode(it.getInputTupleName());
        return Boolean.valueOf((_node instanceof OptimallySplitReductions.DummyNode));
      };
      final ISLUnionMap realScheduleMaps = ISLUtil.convertToUnionMap(IterableExtensions.<ISLMap>toList(IterableExtensions.<ISLMap>reject(scheduler.getMaps().getMaps(), _function)));
      final int nTimeDims = ISLUtil.countTimeDimensions(sys, realScheduleMaps);
      final Function1<PRDGNode, Boolean> _function_1 = (PRDGNode it) -> {
        return Boolean.valueOf(it.isReductionNode());
      };
      final Function1<PRDGNode, Boolean> _function_2 = (PRDGNode it) -> {
        return Boolean.valueOf(OptimallySplitReductions.hasMultipleDependences(it, prdg));
      };
      final Consumer<PRDGNode> _function_3 = (PRDGNode it) -> {
        OptimallySplitReductions.splitNode(it, sys, scheduler, prdg, nTimeDims);
      };
      IterableExtensions.<PRDGNode>filter(IterableExtensions.<PRDGNode>filter(prdg.getNodes(), _function_1), _function_2).forEach(_function_3);
      _xblockexpression = null;
    }
    return _xblockexpression;
  }

  /**
   * Splits a single reduction such that each piece is dominated by one read function.
   */
  private static void splitNode(final PRDGNode node, final AlphaSystem sys, final Scheduler scheduler, final PRDG prdg, final int nTimeDims) {
    AlphaExpression _expr = node.getOriginEquation(sys).getExpr();
    final AbstractReduceExpression are = ((AbstractReduceExpression) _expr);
    final Function1<OptimallySplitReductions.DummyNode, ISLMap> _function = (OptimallySplitReductions.DummyNode it) -> {
      return OptimallySplitReductions.processDummySchedule(it, node, scheduler);
    };
    final Function1<ISLMap, List<ISLAff>> _function_1 = (ISLMap it) -> {
      return ISLUtil.toMultiAff(it).getAffs().subList(0, nTimeDims);
    };
    final Function1<List<ISLAff>, Iterable<ISLAff>> _function_2 = (List<ISLAff> it) -> {
      return OptimallySplitReductions.reduceAffs(it);
    };
    final Function1<Iterable<ISLAff>, ISLMultiAff> _function_3 = (Iterable<ISLAff> it) -> {
      return ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(it));
    };
    final Iterable<ISLMultiAff> timeMaffs = IterableExtensions.<Iterable<ISLAff>, ISLMultiAff>map(IterableExtensions.<List<ISLAff>, Iterable<ISLAff>>map(IterableExtensions.<ISLMap, List<ISLAff>>map(IterableExtensions.<OptimallySplitReductions.DummyNode, ISLMap>map(OptimallySplitReductions.correspondingDummyNodes(prdg, node), _function), _function_1), _function_2), _function_3);
    SplitReduction.applyDominanceSplit(are, timeMaffs);
  }

  /**
   * Returns whether the given node has multiple dependences.
   * If not, it should not be split.
   */
  private static boolean hasMultipleDependences(final PRDGNode node, final PRDG prdg) {
    final Function1<PRDGEdge, Boolean> _function = (PRDGEdge it) -> {
      PRDGNode _source = it.getSource();
      return Boolean.valueOf(Objects.equal(_source, node));
    };
    int _size = IterableExtensions.size(IterableExtensions.<PRDGEdge>filter(prdg.getEdges(), _function));
    return (_size > 1);
  }

  /**
   * Gets the DummyNodes in a PRDG that correspond to a given real reduction node.
   */
  private static Iterable<OptimallySplitReductions.DummyNode> correspondingDummyNodes(final PRDG prdg, final PRDGNode reductionNode) {
    final Function1<PRDGNode, Boolean> _function = (PRDGNode it) -> {
      return Boolean.valueOf((it instanceof OptimallySplitReductions.DummyNode));
    };
    final Function1<PRDGNode, Boolean> _function_1 = (PRDGNode it) -> {
      String _name = reductionNode.getName();
      String _substring = it.getName().substring(0, it.getName().lastIndexOf("_DUMMY"));
      return Boolean.valueOf(Objects.equal(_name, _substring));
    };
    final Function1<PRDGNode, OptimallySplitReductions.DummyNode> _function_2 = (PRDGNode it) -> {
      return ((OptimallySplitReductions.DummyNode) it);
    };
    return IterableExtensions.<PRDGNode, OptimallySplitReductions.DummyNode>map(IterableExtensions.<PRDGNode>filter(IterableExtensions.<PRDGNode>filter(prdg.getNodes(), _function), _function_1), _function_2);
  }

  /**
   * Gets the real reduction PRDGNode in a PRDG that corresponds to a given dummy node.
   */
  private static PRDGNode correspondingReductionNode(final OptimallySplitReductions.DummyNode dummyNode, final PRDG prdg) {
    final Function1<PRDGNode, Boolean> _function = (PRDGNode it) -> {
      return Boolean.valueOf((it instanceof OptimallySplitReductions.DummyNode));
    };
    final Function1<PRDGNode, Boolean> _function_1 = (PRDGNode it) -> {
      String _name = it.getName();
      String _substring = dummyNode.getName().substring(0, dummyNode.getName().lastIndexOf("_DUMMY"));
      return Boolean.valueOf(Objects.equal(_name, _substring));
    };
    return IterableExtensions.<PRDGNode>findFirst(IterableExtensions.<PRDGNode>reject(prdg.getNodes(), _function), _function_1);
  }

  /**
   * Converts the virtual schedule of the dummy nodes into a schedule on the corresponding reduction node
   * The tuple name is not present on the processed schedule
   */
  private static ISLMap processDummySchedule(final PRDGNode dummyNode, final PRDGNode reductionNode, final Scheduler scheduler) {
    final ISLMap identMaff = reductionNode.getDomain().identity();
    final ISLMap productMap = identMaff.copy().rangeProduct(identMaff.copy()).rangeProduct(ISLAff.buildZero(reductionNode.getLocalSpace()).toMultiAff().toMap()).flatten();
    final ISLMap rawSchedule = scheduler.getAnonymousMap(dummyNode.getName());
    return productMap.applyRange(rawSchedule);
  }

  /**
   * Scales a list of affs down by their common scalar factor.
   */
  private static Iterable<ISLAff> reduceAffs(final Iterable<ISLAff> affs) {
    final Function1<ISLAff, Iterable<ISLVal>> _function = (ISLAff it) -> {
      int _dim = it.dim(ISLUtil.Dims.IN);
      final Function1<Integer, ISLVal> _function_1 = (Integer i) -> {
        return it.getCoefficientVal(ISLUtil.Dims.IN, (i).intValue());
      };
      return IterableExtensions.<Integer, ISLVal>map(new ExclusiveRange(0, _dim, true), _function_1);
    };
    final Iterable<ISLVal> coeffs = IterableExtensions.<ISLAff, ISLVal>flatMap(affs, _function);
    final Function1<ISLVal, Boolean> _function_1 = (ISLVal it) -> {
      return Boolean.valueOf(Objects.equal(it, Integer.valueOf(0)));
    };
    final Function1<ISLVal, ISLVal> _function_2 = (ISLVal it) -> {
      return it.abs();
    };
    final Function2<ISLVal, ISLVal, ISLVal> _function_3 = (ISLVal a, ISLVal b) -> {
      return a.gcd(b);
    };
    final ISLVal gcd = IterableExtensions.<ISLVal>reduce(IterableExtensions.<ISLVal, ISLVal>map(IterableExtensions.<ISLVal>reject(coeffs, _function_1), _function_2), _function_3);
    final Function1<ISLAff, ISLAff> _function_4 = (ISLAff it) -> {
      return it.copy().setConstant(0).scaleDown(gcd);
    };
    return IterableExtensions.<ISLAff, ISLAff>map(affs, _function_4);
  }
}
