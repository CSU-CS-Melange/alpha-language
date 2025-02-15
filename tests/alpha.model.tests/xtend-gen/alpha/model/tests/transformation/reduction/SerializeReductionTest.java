package alpha.model.tests.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaModelLoader;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UniquenessAndCompletenessCheck;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.reduction.NormalizeReduction;
import alpha.model.transformation.reduction.SerializeReduction;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class SerializeReductionTest {
  protected static class ReadSetComputer extends AbstractAlphaCompleteVisitor {
    protected ISLUnionSet readUSet;

    public ReadSetComputer() {
      this.readUSet = null;
    }

    public static ISLUnionSet compute(final AlphaVisitable an) {
      final SerializeReductionTest.ReadSetComputer comp = new SerializeReductionTest.ReadSetComputer();
      an.accept(comp);
      return comp.readUSet;
    }

    @Override
    public void outDependenceExpression(final DependenceExpression de) {
      AlphaExpression _expr = de.getExpr();
      final ISLUnionSet readSet = de.getContextDomain().copy().apply(de.getFunction().copy().toMap()).setTupleName(((VariableExpression) _expr).getVariable().getName()).toUnionSet();
      if ((this.readUSet == null)) {
        this.readUSet = readSet;
      } else {
        this.readUSet = this.readUSet.union(readSet);
      }
    }
  }

  protected static class ReductionBoundednessChecker extends AbstractAlphaCompleteVisitor {
    public static void apply(final AlphaVisitable an) {
      final SerializeReductionTest.ReductionBoundednessChecker comp = new SerializeReductionTest.ReductionBoundednessChecker();
      an.accept(comp);
    }

    @Override
    public void outReduceExpression(final ReduceExpression are) {
      final ISLSet domain = are.getBody().getContextDomain().copy();
      int _dim = domain.dim(ISLDimType.isl_dim_set);
      int _minus = (_dim - 1);
      final Consumer<Integer> _function = (Integer i) -> {
        Assert.assertTrue(domain.hasUpperBound(ISLDimType.isl_dim_set, (i).intValue()));
        Assert.assertTrue(domain.hasLowerBound(ISLDimType.isl_dim_set, (i).intValue()));
      };
      new IntegerRange(0, _minus).forEach(_function);
    }
  }

  private AlphaSystem sys;

  public void readFileAndSerialize(final String file) {
    try {
      this.sys = AlphaModelLoader.loadModel(file).getSystems().get(0);
      final Function1<Variable, String> _function = (Variable variable) -> {
        return variable.getName();
      };
      final Set<String> variableNames = IterableExtensions.<String>toSet(ListExtensions.<Variable, String>map(this.sys.getVariables(), _function));
      NormalizeReduction.apply(this.sys);
      final Function1<Variable, Boolean> _function_1 = (Variable variable) -> {
        boolean _contains = variableNames.contains(variable.getName());
        return Boolean.valueOf((!_contains));
      };
      final Iterable<Variable> reductionVariables = IterableExtensions.<Variable>filter(this.sys.getVariables(), _function_1);
      for (final Variable v : reductionVariables) {
        AlphaExpression _expr = this.getStandardEquation(v).getExpr();
        this.autoSerializeAndAssert(((AbstractReduceExpression) _expr));
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public void autoSerializeAndAssert(final AbstractReduceExpression are) {
    final ISLSet originalDomain = are.getBody().getContextDomain().copy();
    final ISLUnionSet originalReadSet = SerializeReductionTest.ReadSetComputer.compute(AlphaUtil.getContainerEquation(are));
    Equation _containerEquation = AlphaUtil.getContainerEquation(are);
    final Variable writeVar = ((StandardEquation) _containerEquation).getVariable();
    final Function1<Variable, String> _function = (Variable variable) -> {
      return variable.getName();
    };
    final Set<String> variableNames = IterableExtensions.<String>toSet(ListExtensions.<Variable, String>map(this.sys.getVariables(), _function));
    SerializeReduction.applyAuto(are);
    Normalize.apply(this.sys);
    final Function1<Variable, Boolean> _function_1 = (Variable variable) -> {
      boolean _contains = variableNames.contains(variable.getName());
      return Boolean.valueOf((!_contains));
    };
    final Variable newVariable = IterableExtensions.<Variable>findFirst(this.sys.getVariables(), _function_1);
    final StandardEquation writeSE = this.getStandardEquation(writeVar);
    final StandardEquation newSE = this.getStandardEquation(newVariable);
    this.assertReductionDomainUnchanged(originalDomain, newSE);
    this.assertReadSetUnchanged(originalReadSet, newSE);
    this.assertAllPointsReduced(originalDomain, newSE, writeSE);
    this.assertAllReductionsBounded();
    this.assertUniqueAndComplete();
  }

  public void assertReductionDomainUnchanged(final ISLSet originalDomain, final StandardEquation newSE) {
    Assert.assertTrue(originalDomain.copy().isPlainEqual(newSE.getExpr().getContextDomain().copy()));
  }

  public void assertReadSetUnchanged(final ISLUnionSet originalReadSet, final StandardEquation newSE) {
    final ISLUnionSet readUSet = SerializeReductionTest.ReadSetComputer.compute(newSE).coalesce();
    Assert.assertTrue(originalReadSet.copy().isSubset(readUSet.copy()));
    final Function1<ISLSet, Boolean> _function = (ISLSet set) -> {
      String _tupleName = set.getTupleName();
      String _name = newSE.getVariable().getName();
      return Boolean.valueOf((!Objects.equal(_tupleName, _name)));
    };
    final Consumer<ISLSet> _function_1 = (ISLSet set) -> {
      Assert.assertTrue(set.copy().toUnionSet().isSubset(originalReadSet.copy()));
    };
    IterableExtensions.<ISLSet>filter(readUSet.copy().getSets(), _function).forEach(_function_1);
  }

  public void assertAllPointsReduced(final ISLSet originalDomain, final StandardEquation newSE, final StandardEquation writeSE) {
    final Function1<ISLSet, Boolean> _function = (ISLSet set) -> {
      String _tupleName = set.getTupleName();
      String _name = newSE.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_tupleName, _name));
    };
    final ISLSet resultSet = IterableExtensions.<ISLSet>findFirst(SerializeReductionTest.ReadSetComputer.compute(writeSE).getSets(), _function);
    final Function1<ISLSet, Boolean> _function_1 = (ISLSet set) -> {
      String _tupleName = set.getTupleName();
      String _name = newSE.getVariable().getName();
      return Boolean.valueOf(Objects.equal(_tupleName, _name));
    };
    final ISLSet nonResultSet = IterableExtensions.<ISLSet>findFirst(SerializeReductionTest.ReadSetComputer.compute(newSE).getSets(), _function_1);
    final ISLSet totalSet = resultSet.union(nonResultSet);
    Assert.assertTrue(originalDomain.isPlainEqual(totalSet.coalesce().clearTupleName()));
  }

  public void assertAllReductionsBounded() {
    SerializeReductionTest.ReductionBoundednessChecker.apply(this.sys);
  }

  public void assertUniqueAndComplete() {
    Assert.assertTrue(UniquenessAndCompletenessCheck.check(AlphaUtil.getContainerRoot(this.sys)).isEmpty());
  }

  public StandardEquation getStandardEquation(final Variable v) {
    final Function1<SystemBody, StandardEquation> _function = (SystemBody body) -> {
      return body.getStandardEquation(v);
    };
    final Function1<StandardEquation, Boolean> _function_1 = (StandardEquation se) -> {
      return Boolean.valueOf((se != null));
    };
    return IterableExtensions.<StandardEquation>findFirst(ListExtensions.<SystemBody, StandardEquation>map(this.sys.getSystemBodies(), _function), _function_1);
  }

  @Test
  public void testOSP() {
    this.readFileAndSerialize("resources/src-valid/kernels/osp.alpha");
  }

  @Test
  public void testBPMax() {
    this.readFileAndSerialize("resources/src-valid/kernels/bpmax.alpha");
  }

  @Test
  public void testCholesky() {
    this.readFileAndSerialize("resources/src-valid/kernels/cholesky.alpha");
  }
}
