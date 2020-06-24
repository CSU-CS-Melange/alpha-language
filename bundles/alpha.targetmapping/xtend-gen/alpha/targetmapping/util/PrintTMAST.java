package alpha.targetmapping.util;

import alpha.model.AlphaScheduleTarget;
import alpha.targetmapping.BandExpression;
import alpha.targetmapping.BandPiece;
import alpha.targetmapping.ContextExpression;
import alpha.targetmapping.ExtensionExpression;
import alpha.targetmapping.ExtensionTarget;
import alpha.targetmapping.FilterExpression;
import alpha.targetmapping.GuardExpression;
import alpha.targetmapping.IsolateSpecification;
import alpha.targetmapping.LoopTypeSpecification;
import alpha.targetmapping.MarkExpression;
import alpha.targetmapping.ScheduleTargetRestrictDomain;
import alpha.targetmapping.TargetMapping;
import alpha.targetmapping.TargetMappingForSystemBody;
import alpha.targetmapping.TargetMappingVisitable;
import alpha.targetmapping.util.AbstractTargetMappingVisitor;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class PrintTMAST extends AbstractTargetMappingVisitor {
  private String indent = "";
  
  protected static final String INDENT_WITH_SIBILING = "   |";
  
  protected static final String INDENT_LAST_CHILD = "    ";
  
  protected StringBuffer _output = new StringBuffer();
  
  protected int depth = 0;
  
  public static String print(final TargetMappingVisitable node) {
    final PrintTMAST printer = new PrintTMAST();
    node.accept(printer);
    return printer._output.toString();
  }
  
  protected StringBuffer printInt(final String prefix, final long v) {
    String _plus = (Long.valueOf(v) + "");
    return this.printStr(prefix, _plus);
  }
  
  protected StringBuffer printReal(final String prefix, final double v) {
    String _plus = (Double.valueOf(v) + "");
    return this.printStr(prefix, _plus);
  }
  
  protected StringBuffer printStr(final Object... objs) {
    StringBuffer _xblockexpression = null;
    {
      this._output.append(this.indent);
      final Consumer<Object> _function = (Object o) -> {
        this._output.append(o);
      };
      ((List<Object>)Conversions.doWrapArray(objs)).forEach(_function);
      _xblockexpression = this._output.append("\n");
    }
    return _xblockexpression;
  }
  
  @Override
  public void defaultIn(final TargetMappingVisitable tmv) {
    this.printStr("_", tmv.eClass().getName());
    String _indent = this.indent;
    this.indent = (_indent + PrintTMAST.INDENT_WITH_SIBILING);
  }
  
  @Override
  public void defaultOut(final TargetMappingVisitable tmv) {
    int _length = this.indent.length();
    int _length_1 = PrintTMAST.INDENT_WITH_SIBILING.length();
    int _minus = (_length - _length_1);
    this.indent = this.indent.substring(0, _minus);
  }
  
  @Override
  public void inTargetMapping(final TargetMapping tm) {
    this.defaultIn(tm);
    this.printStr("+--", "targetSystem:", tm.getTargetSystem().getName());
  }
  
  @Override
  public void inTargetMappingForSystemBody(final TargetMappingForSystemBody tm) {
    this.defaultIn(tm);
    this.printStr("+--", "targetBody:", Integer.valueOf(tm.getTargetBody().getSystem().getSystemBodies().indexOf(tm.getTargetBody())));
  }
  
  @Override
  public void inContextExpression(final ContextExpression ce) {
    this.defaultIn(ce);
    this.printStr("+--", ce.getContextDomain());
  }
  
  @Override
  public void inFilterExpression(final FilterExpression fe) {
    this.defaultIn(fe);
    EList<ScheduleTargetRestrictDomain> _filterDomains = fe.getFilterDomains();
    for (final ScheduleTargetRestrictDomain fd : _filterDomains) {
      boolean _plainIsUniverse = fd.getRestrictDomain().plainIsUniverse();
      if (_plainIsUniverse) {
        this.printStr("+--", fd.getScheduleTarget().getName());
      } else {
        this.printStr("+--", fd.getScheduleTarget().getName(), ":", fd.getRestrictDomain());
      }
    }
  }
  
  @Override
  public void inGuardExpression(final GuardExpression ge) {
    this.defaultIn(ge);
    this.printStr("+--", ge.getGuardDomain());
  }
  
  @Override
  public void inMarkExpression(final MarkExpression me) {
    this.defaultIn(me);
    this.printStr("+--", me.getIdentifier());
  }
  
  @Override
  public void inBandExpression(final BandExpression be) {
    this.defaultIn(be);
    boolean _isTile = be.isTile();
    if (_isTile) {
      this.printStr("+--", "tile");
    }
    boolean _isParallel = be.isParallel();
    if (_isParallel) {
      this.printStr("+--", "parallel");
    }
    EList<LoopTypeSpecification> _loopTypeSpecifications = be.getLoopTypeSpecifications();
    for (final LoopTypeSpecification lts : _loopTypeSpecifications) {
      this.printStr("+--", lts.getLoopType().getName(), ":", Integer.valueOf(lts.getDimension()));
    }
    EList<BandPiece> _bandPieces = be.getBandPieces();
    for (final BandPiece bp : _bandPieces) {
      boolean _plainIsUniverse = bp.getPieceDomain().getRestrictDomain().plainIsUniverse();
      if (_plainIsUniverse) {
        this.printStr("+--", bp.getPieceDomain().getScheduleTarget().getName(), "@", bp.getPartialSchedule());
      } else {
        this.printStr("+--", bp.getPieceDomain().getScheduleTarget().getName(), ":", bp.getPieceDomain().getRestrictDomain(), "@", bp.getPartialSchedule());
      }
    }
    IsolateSpecification _isolateSpecification = be.getIsolateSpecification();
    boolean _tripleNotEquals = (_isolateSpecification != null);
    if (_tripleNotEquals) {
      this.printStr("+--", "isolate", be.getIsolateSpecification().getIsolateDomain());
      EList<LoopTypeSpecification> _loopTypeSpecifications_1 = be.getIsolateSpecification().getLoopTypeSpecifications();
      for (final LoopTypeSpecification lts_1 : _loopTypeSpecifications_1) {
        this.printStr("   +--", lts_1.getLoopType().getName(), ":", Integer.valueOf(lts_1.getDimension()));
      }
    }
  }
  
  @Override
  public void inExtensionExpression(final ExtensionExpression ee) {
    this.defaultIn(ee);
    EList<ExtensionTarget> _extensionTargets = ee.getExtensionTargets();
    for (final ExtensionTarget et : _extensionTargets) {
      {
        String _xifexpression = null;
        AlphaScheduleTarget _source = et.getSource();
        boolean _tripleNotEquals = (_source != null);
        if (_tripleNotEquals) {
          _xifexpression = et.getSource().getName();
        } else {
          _xifexpression = "[]";
        }
        final String srcname = _xifexpression;
        this.printStr("+--", srcname, "->", et.getName(), ":", et.getExtensionMap());
      }
    }
  }
}
