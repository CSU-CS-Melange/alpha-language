package alpha.model.scheduler;

import alpha.model.AlphaSystem;
import alpha.model.DependenceExpression;
import alpha.model.StandardEquation;
import alpha.model.VariableExpression;
import alpha.model.exception.CausalityViolationException;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Stack;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ScheduleVerifier extends AbstractAlphaCompleteVisitor {
  private Iterable<ISLMap> maps;

  private Stack<ISLMultiAff> dependenceMaffs;

  private Stack<String> sourceNames;

  private Stack<ISLSet> domains;

  public ScheduleVerifier(final Scheduler scheduler) {
    this.maps = scheduler.getMaps().getMaps();
    Stack<ISLMultiAff> _stack = new Stack<ISLMultiAff>();
    this.dependenceMaffs = _stack;
    Stack<String> _stack_1 = new Stack<String>();
    this.sourceNames = _stack_1;
    Stack<ISLSet> _stack_2 = new Stack<ISLSet>();
    this.domains = _stack_2;
  }

  public ScheduleVerifier(final Iterable<ISLMap> maps) {
    this.maps = maps;
    Stack<ISLMultiAff> _stack = new Stack<ISLMultiAff>();
    this.dependenceMaffs = _stack;
    Stack<String> _stack_1 = new Stack<String>();
    this.sourceNames = _stack_1;
    Stack<ISLSet> _stack_2 = new Stack<ISLSet>();
    this.domains = _stack_2;
  }

  @Override
  public void inStandardEquation(final StandardEquation standardEquation) {
    this.dependenceMaffs.push(ISLMultiAff.buildIdentity(standardEquation.getVariable().getDomain().copy().identity().getSpace()));
    this.domains.push(standardEquation.getVariable().getDomain().copy());
    this.sourceNames.push(standardEquation.getVariable().getName());
  }

  @Override
  public void outStandardEquation(final StandardEquation standardEquation) {
    this.domains.pop();
    this.sourceNames.pop();
  }

  @Override
  public void inDependenceExpression(final DependenceExpression dependenceExpression) {
    this.dependenceMaffs.push(dependenceExpression.getFunction().copy());
    this.domains.push(dependenceExpression.getContextDomain().copy());
  }

  @Override
  public void outDependenceExpression(final DependenceExpression dependenceExpression) {
    this.dependenceMaffs.pop();
    this.domains.pop();
  }

  @Override
  public void visitVariableExpression(final VariableExpression ve) {
    Boolean _isInput = ve.getVariable().isInput();
    if ((_isInput).booleanValue()) {
      return;
    }
    final ISLMultiAff dependenceTS = this.getMaff(ve.getVariable().getName());
    final ISLMultiAff readTS = dependenceTS.pullback(this.dependenceMaffs.peek().copy());
    ISLMultiAff writeTS = this.getMaff(this.sourceNames.peek());
    int _dim = writeTS.dim(ISLUtil.Dims.IN);
    int _dim_1 = readTS.dim(ISLDimType.isl_dim_in);
    boolean _notEquals = (_dim != _dim_1);
    if (_notEquals) {
      int _dim_2 = readTS.dim(ISLUtil.Dims.IN);
      int _dim_3 = writeTS.dim(ISLUtil.Dims.IN);
      final int extraDims = (_dim_2 - _dim_3);
      writeTS = writeTS.addDims(ISLUtil.Dims.IN, extraDims);
    }
    this.verifyCausality(writeTS, readTS);
  }

  protected void verifyCausality(final ISLMultiAff writeTS, final ISLMultiAff readTS) {
    final ISLSet domain = this.domains.peek().copy();
    final int TSDims = writeTS.getNbOutputs();
    ISLSet coveredSet = ISLSet.buildEmpty(domain.getSpace().copy());
    for (int i = 0; (i < TSDims); i++) {
      {
        final ISLSet causalitySet = coveredSet.copy().union(
          ISLSet.buildGESet(writeTS.getAff(i), readTS.getAff(i)));
        boolean _isSubset = domain.isSubset(causalitySet);
        boolean _not = (!_isSubset);
        if (_not) {
          ISLMap _map = this.dependenceMaffs.peek().copy().toMap();
          ISLSet _subtract = domain.copy().subtract(causalitySet.copy());
          throw new CausalityViolationException(_map, writeTS, readTS, _subtract, i);
        }
        coveredSet = coveredSet.union(
          ISLSet.buildGTSet(writeTS.getAff(i), readTS.getAff(i)));
      }
    }
    boolean _isSubset = domain.isSubset(coveredSet);
    boolean _not = (!_isSubset);
    if (_not) {
      ISLMap _map = this.dependenceMaffs.peek().copy().toMap();
      ISLSet _subtract = domain.copy().subtract(coveredSet.copy());
      throw new CausalityViolationException(_map, writeTS, readTS, _subtract, (TSDims - 1));
    }
  }

  public static void verify(final AlphaSystem sys, final Scheduler scheduler) {
    ScheduleVerifier verifier = new ScheduleVerifier(scheduler);
    verifier.accept(sys);
  }

  protected ISLMultiAff getMaff(final String name) {
    final Function1<ISLMap, Boolean> _function = (ISLMap it) -> {
      String _inputTupleName = it.getInputTupleName();
      return Boolean.valueOf(Objects.equal(_inputTupleName, name));
    };
    return ISLUtil.toMultiAff(IterableExtensions.<ISLMap>findFirst(this.maps, _function).copy().clearInputTupleName());
  }
}
