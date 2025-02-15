package alpha.model.scheduler;

import alpha.model.AlphaSystem;
import alpha.model.DependenceExpression;
import alpha.model.StandardEquation;
import alpha.model.VariableExpression;
import alpha.model.exception.CausalityViolationException;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Stack;

@SuppressWarnings("all")
public class ScheduleVerifier extends AbstractAlphaCompleteVisitor {
  private Scheduler scheduler;

  private Stack<ISLMultiAff> dependenceMaffs;

  private Stack<String> sourceNames;

  private Stack<ISLSet> domains;

  public ScheduleVerifier(final Scheduler scheduler) {
    this.scheduler = scheduler;
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
    final ISLMultiAff dependenceTimestampMaff = ISLUtil.toMultiAff(this.scheduler.getScheduleMap(ve.getVariable().getName()).copy().clearInputTupleName());
    final ISLMultiAff readTimestampMaff = dependenceTimestampMaff.pullback(this.dependenceMaffs.peek().copy());
    ISLMultiAff writeTimestampMaff = ISLUtil.toMultiAff(this.scheduler.getScheduleMap(this.sourceNames.peek()).copy().clearInputTupleName());
    int _dim = writeTimestampMaff.dim(ISLDimType.isl_dim_in);
    int _dim_1 = readTimestampMaff.dim(ISLDimType.isl_dim_in);
    boolean _notEquals = (_dim != _dim_1);
    if (_notEquals) {
      int _dim_2 = readTimestampMaff.dim(ISLDimType.isl_dim_in);
      int _dim_3 = writeTimestampMaff.dim(ISLDimType.isl_dim_in);
      int _minus = (_dim_2 - _dim_3);
      writeTimestampMaff = writeTimestampMaff.addDims(
        ISLDimType.isl_dim_in, _minus);
    }
    this.verifyCausality(writeTimestampMaff, readTimestampMaff);
  }

  protected void verifyCausality(final ISLMultiAff writeTimestampMaff, final ISLMultiAff readTimestampMaff) {
    final ISLSet domain = this.domains.peek().copy();
    final int timestampDims = writeTimestampMaff.getNbOutputs();
    ISLSet coveredSet = ISLSet.buildEmpty(domain.getSpace().copy());
    for (int i = 0; (i < timestampDims); i++) {
      {
        final ISLSet causalitySet = coveredSet.copy().union(
          ISLSet.buildGESet(writeTimestampMaff.getAff(i), readTimestampMaff.getAff(i)));
        boolean _isSubset = domain.isSubset(causalitySet);
        boolean _not = (!_isSubset);
        if (_not) {
          ISLMultiAff _peek = this.dependenceMaffs.peek();
          ISLSet _subtract = domain.copy().subtract(causalitySet.copy());
          throw new CausalityViolationException(_peek, writeTimestampMaff, readTimestampMaff, _subtract, i);
        }
        coveredSet = coveredSet.union(
          ISLSet.buildGTSet(writeTimestampMaff.getAff(i), readTimestampMaff.getAff(i)));
      }
    }
  }

  public static void verify(final AlphaSystem sys, final Scheduler scheduler) {
    ScheduleVerifier verifier = new ScheduleVerifier(scheduler);
    verifier.accept(sys);
  }
}
