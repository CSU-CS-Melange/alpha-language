package alpha.model.tests.scheduling;

import alpha.model.AlphaModelLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.Variable;
import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGEdge;
import alpha.model.prdg.PRDGGenerator;
import alpha.model.prdg.PRDGNode;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class PRDGTest {
  /**
   * Whether the dependence edge from a reduction's result variable to its body is flipped
   * If 'true', the edge is flipped, to conform to old AlphaZ specifications
   * If this is ever changed, this value can simply be set to false to maintain test correctness
   */
  private final boolean RESULT_BODY_EDGE_FLIPPED = true;

  private PRDG prdg;

  private AlphaSystem sys;

  public PRDG readFile(final String file) {
    try {
      PRDG _xblockexpression = null;
      {
        AlphaRoot root = AlphaModelLoader.loadModel(file);
        this.sys = root.getSystems().get(0);
        _xblockexpression = this.prdg = PRDGGenerator.apply(this.sys);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public void assertEdgeDomsInVariable(final Iterable<ISLSet> outgoingDoms, final Variable variable) {
    final Consumer<ISLSet> _function = (ISLSet dom) -> {
      Assert.assertTrue(dom.copy().isSubset(variable.getDomain().copy()));
    };
    outgoingDoms.forEach(_function);
  }

  public void assertEdgeDomsPartitionVariables() {
    EList<Variable> _variables = this.sys.getVariables();
    for (final Variable variable : _variables) {
      {
        final Function1<PRDGEdge, Boolean> _function = (PRDGEdge edge) -> {
          String _name = edge.getSource().getName();
          String _name_1 = variable.getName();
          return Boolean.valueOf(Objects.equal(_name, _name_1));
        };
        final Iterable<PRDGEdge> outgoing = IterableExtensions.<PRDGEdge>filter(this.prdg.getEdges(), _function);
        final Function1<PRDGEdge, ISLSet> _function_1 = (PRDGEdge edge) -> {
          return edge.getDomain().copy();
        };
        final Iterable<ISLSet> outgoingDoms = IterableExtensions.<PRDGEdge, ISLSet>map(outgoing, _function_1);
        this.assertEdgeDomsInVariable(outgoingDoms, variable);
      }
    }
  }

  public void assertEdgeRangesInVariables() {
    EList<Variable> _variables = this.sys.getVariables();
    for (final Variable variable : _variables) {
      {
        final Function1<PRDGEdge, Boolean> _function = (PRDGEdge edge) -> {
          String _name = edge.getDest().getName();
          String _name_1 = variable.getName();
          return Boolean.valueOf(Objects.equal(_name, _name_1));
        };
        final Iterable<PRDGEdge> incoming = IterableExtensions.<PRDGEdge>filter(this.prdg.getEdges(), _function);
        final Function1<PRDGEdge, ISLSet> _function_1 = (PRDGEdge edge) -> {
          return edge.getDomain().apply(edge.getFunction().toMap());
        };
        final Iterable<ISLSet> incomingDoms = IterableExtensions.<PRDGEdge, ISLSet>map(incoming, _function_1);
        this.assertEdgeDomsInVariable(incomingDoms, variable);
      }
    }
  }

  public void assertEdgeDimensionsCorrect() {
    Set<PRDGEdge> _edges = this.prdg.getEdges();
    for (final PRDGEdge edge : _edges) {
      {
        Variable sourceVar = this.sys.getVariable(edge.getSource().getName());
        Variable destVar = this.sys.getVariable(edge.getDest().getName());
        if ((edge.isReductionEdge() && this.RESULT_BODY_EDGE_FLIPPED)) {
          final Variable temp = sourceVar;
          sourceVar = destVar;
          destVar = temp;
        }
        if ((sourceVar != null)) {
          final int nSourceParams = sourceVar.getDomain().dim(ISLDimType.isl_dim_param);
          int _dim = sourceVar.getDomain().dim(ISLDimType.isl_dim_all);
          final int nSourceIdxs = (_dim - nSourceParams);
          final int nEdgeDomParams = edge.getDomain().dim(ISLDimType.isl_dim_param);
          int _dim_1 = edge.getDomain().dim(ISLDimType.isl_dim_all);
          final int nEdgeDomIdxs = (_dim_1 - nEdgeDomParams);
          final int nEdgeFunParams = edge.getFunction().dim(ISLDimType.isl_dim_param);
          final int nEdgeFunIn = edge.getFunction().dim(ISLDimType.isl_dim_in);
          Assert.assertEquals(nSourceParams, nEdgeDomParams);
          Assert.assertEquals(nSourceIdxs, nEdgeDomIdxs);
          Assert.assertEquals(nSourceParams, nEdgeFunParams);
          Assert.assertEquals(nSourceIdxs, nEdgeFunIn);
        }
        if ((destVar != null)) {
          final int nDestParams = destVar.getDomain().dim(ISLDimType.isl_dim_param);
          int _dim_2 = destVar.getDomain().dim(ISLDimType.isl_dim_all);
          final int nDestIdxs = (_dim_2 - nDestParams);
          final int nEdgeFunParams_1 = edge.getFunction().dim(ISLDimType.isl_dim_param);
          final int nEdgeFunOut = edge.getFunction().dim(ISLDimType.isl_dim_out);
          Assert.assertEquals(nDestParams, nEdgeFunParams_1);
          Assert.assertEquals(nDestIdxs, nEdgeFunOut);
        }
      }
    }
  }

  public void assertNodeDomainsCorrect() {
    Set<PRDGNode> _nodes = this.prdg.getNodes();
    for (final PRDGNode node : _nodes) {
      {
        Variable variable = this.sys.getVariable(node.getName());
        if ((variable != null)) {
          Assert.assertTrue(variable.getDomain().copy().isSubset(node.getDomain().copy()));
          Assert.assertTrue(node.getDomain().copy().isSubset(variable.getDomain().copy()));
        }
      }
    }
  }

  public void assertNodesComplete() {
    EList<Variable> _variables = this.sys.getVariables();
    for (final Variable variable : _variables) {
      Boolean _isInput = variable.isInput();
      boolean _not = (!(_isInput).booleanValue());
      if (_not) {
        final Function1<PRDGNode, Boolean> _function = (PRDGNode node) -> {
          String _name = node.getName();
          String _name_1 = variable.getName();
          return Boolean.valueOf(Objects.equal(_name, _name_1));
        };
        Assert.assertFalse(IterableExtensions.isEmpty(IterableExtensions.<PRDGNode>filter(this.prdg.getNodes(), _function)));
      }
    }
  }

  public void assertPRDGValid() {
    this.assertEdgeDimensionsCorrect();
    this.assertEdgeDomsPartitionVariables();
    this.assertEdgeRangesInVariables();
    this.assertNodeDomainsCorrect();
    this.assertNodesComplete();
  }

  @Test
  public void testDot() {
    this.readFile("resources/src-valid/basic/matmult.unit/dot.alpha");
    this.assertPRDGValid();
  }

  @Test
  public void testBPMax() {
    this.readFile("resources/src-valid/kernels/bpmax.alpha");
    this.assertPRDGValid();
  }

  @Test
  public void testCholesky() {
    this.readFile("resources/src-valid/kernels/cholesky.alpha");
    this.assertPRDGValid();
  }
}
