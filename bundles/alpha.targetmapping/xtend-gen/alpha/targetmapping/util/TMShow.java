package alpha.targetmapping.util;

import alpha.model.util.AlphaPrintingUtil;
import alpha.targetmapping.BandExpression;
import alpha.targetmapping.BandPiece;
import alpha.targetmapping.ContextExpression;
import alpha.targetmapping.ExtensionExpression;
import alpha.targetmapping.ExtensionTarget;
import alpha.targetmapping.FilterExpression;
import alpha.targetmapping.FullTileSpecification;
import alpha.targetmapping.GuardExpression;
import alpha.targetmapping.IsolateSpecification;
import alpha.targetmapping.LoopTypeSpecification;
import alpha.targetmapping.MarkExpression;
import alpha.targetmapping.PointLoopSpecification;
import alpha.targetmapping.ScheduleTargetRestrictDomain;
import alpha.targetmapping.ScheduleTreeExpression;
import alpha.targetmapping.SequenceExpression;
import alpha.targetmapping.SetExpression;
import alpha.targetmapping.TILING_TYPE;
import alpha.targetmapping.TargetMapping;
import alpha.targetmapping.TargetMappingForSystemBody;
import alpha.targetmapping.TargetMappingNode;
import alpha.targetmapping.TileBandExpression;
import alpha.targetmapping.TileLoopSpecification;
import alpha.targetmapping.TileSizeSpecification;
import alpha.targetmapping.TilingSpecification;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Prints the TargetMapping in Show notation. The show notation
 * is mostly context-free and each node can be printed on its own.
 * The only exception is when printing the domains, the parameter
 * domain of the enclosing system is used to simplify the constraints.
 */
@SuppressWarnings("all")
public class TMShow extends TargetmappingSwitch<CharSequence> {
  protected ISLSet parameterContext = null;

  public static <T extends TargetMappingNode> String print(final T tmn) {
    String _xblockexpression = null;
    {
      final TMShow show = new TMShow();
      show.parameterContext = TargetMappingUtil.getTargetSystem(tmn).getParameterDomain();
      _xblockexpression = show.doSwitch(tmn).toString();
    }
    return _xblockexpression;
  }

  protected String printDomain(final ContextExpression ce) {
    return AlphaPrintingUtil.toAShowString(ce.getContextDomain(), this.parameterContext);
  }

  protected String printDomain(final ScheduleTargetRestrictDomain strd) {
    return AlphaPrintingUtil.toShowString(strd.getRestrictDomain(), this.parameterContext);
  }

  protected String printDomain(final GuardExpression ge) {
    return AlphaPrintingUtil.toShowString(ge.getGuardDomain(), this.parameterContext);
  }

  protected String printDomain(final IsolateSpecification is) {
    return AlphaPrintingUtil.toShowString(is.getIsolateDomain(), this.parameterContext);
  }

  protected String printSchedule(final BandPiece bp) {
    return AlphaPrintingUtil.toShowString(bp.getPartialSchedule());
  }

  protected CharSequence printSchedule(final TilingSpecification ts) {
    CharSequence _xifexpression = null;
    boolean _isIdentity = ts.getLoopSchedule().isIdentity();
    if (_isIdentity) {
      StringConcatenation _builder = new StringConcatenation();
      _xifexpression = _builder;
    } else {
      _xifexpression = AlphaPrintingUtil.toShowString(ts.getLoopSchedule());
    }
    return _xifexpression;
  }

  protected String printExtensionMap(final ISLMap map) {
    return AlphaPrintingUtil.toShowString(map);
  }

  /**
   * All the printDimNames methods are to be overridden by AShow.
   * Context is not used for Show.
   */
  protected CharSequence printDimNames(final ScheduleTargetRestrictDomain strd) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }

  protected CharSequence printDimNames(final BandExpression be) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }

  protected CharSequence printDimNames(final TileBandExpression tbe) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }

  protected CharSequence printDimNames(final ExtensionTarget et) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }

  @Override
  public CharSequence caseTargetMappingNode(final TargetMappingNode object) {
    return super.caseTargetMappingNode(object);
  }

  /**
   * override
   */
  public CharSequence caseTargetMapping(final TargetMapping object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("target ");
    String _fullyQualifiedName = object.getTargetSystem().getFullyQualifiedName();
    _builder.append(_fullyQualifiedName);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    final Function1<TargetMappingForSystemBody, CharSequence> _function = (TargetMappingForSystemBody it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<TargetMappingForSystemBody>join(object.getSystemBodyTMs(), "\n\n", _function);
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseTargetMappingForSystemBody(final TargetMappingForSystemBody object) {
    int _size = object.getTargetMapping().getSystemBodyTMs().size();
    boolean _equals = (_size == 1);
    if (_equals) {
      return this.doSwitch(object.getScheduleTreeRoot());
    } else {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("body ");
      int _indexOf = object.getTargetMapping().getSystemBodyTMs().indexOf(object);
      _builder.append(_indexOf);
      _builder.append(":");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      CharSequence _doSwitch = this.doSwitch(object.getScheduleTreeRoot());
      _builder.append(_doSwitch, "\t");
      _builder.newLineIfNotEmpty();
      return _builder.toString();
    }
  }

  /**
   * override
   */
  public CharSequence caseContextExpression(final ContextExpression object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("context ");
    String _printDomain = this.printDomain(object);
    _builder.append(_printDomain);
    _builder.newLineIfNotEmpty();
    CharSequence _doSwitch = this.doSwitch(object.getChild());
    _builder.append(_doSwitch);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseSetExpression(final SetExpression object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("set {");
    _builder.newLine();
    _builder.append("\t");
    final Function1<ScheduleTreeExpression, CharSequence> _function = (ScheduleTreeExpression it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<ScheduleTreeExpression>join(object.getChildren(), "", ";\n", ";", _function);
    _builder.append(_join, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseSequenceExpression(final SequenceExpression object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("seq {");
    _builder.newLine();
    _builder.append("\t");
    final Function1<ScheduleTreeExpression, CharSequence> _function = (ScheduleTreeExpression it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<ScheduleTreeExpression>join(object.getChildren(), "", ";\n", ";", _function);
    _builder.append(_join, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseFilterExpression(final FilterExpression object) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<ScheduleTargetRestrictDomain, CharSequence> _function = (ScheduleTargetRestrictDomain it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<ScheduleTargetRestrictDomain>join(object.getFilterDomains(), ", ", _function);
    _builder.append(_join);
    {
      ScheduleTreeExpression _child = object.getChild();
      boolean _tripleNotEquals = (_child != null);
      if (_tripleNotEquals) {
        _builder.append(" : ");
        CharSequence _doSwitch = this.doSwitch(object.getChild());
        _builder.append(_doSwitch);
      }
    }
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseScheduleTargetRestrictDomain(final ScheduleTargetRestrictDomain object) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = object.getScheduleTarget().getName();
    _builder.append(_name);
    CharSequence _printDimNames = this.printDimNames(object);
    _builder.append(_printDimNames);
    String _printDomain = this.printDomain(object);
    _builder.append(_printDomain);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseGuardExpression(final GuardExpression object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if ");
    String _printDomain = this.printDomain(object);
    _builder.append(_printDomain);
    _builder.append(" ");
    CharSequence _doSwitch = this.doSwitch(object.getChild());
    _builder.append(_doSwitch);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseMarkExpression(final MarkExpression object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("mark(");
    String _identifier = object.getIdentifier();
    _builder.append(_identifier);
    _builder.append(") ");
    CharSequence _doSwitch = this.doSwitch(object.getChild());
    _builder.append(_doSwitch);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseBandExpression(final BandExpression object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("band");
    CharSequence _printDimNames = this.printDimNames(object);
    _builder.append(_printDimNames);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    final Function1<BandPiece, CharSequence> _function = (BandPiece it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<BandPiece>join(object.getBandPieces(), "\n", _function);
    _builder.append(_join, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    final Function1<LoopTypeSpecification, CharSequence> _function_1 = (LoopTypeSpecification it) -> {
      return this.doSwitch(it);
    };
    String _join_1 = IterableExtensions.<LoopTypeSpecification>join(object.getLoopTypeSpecifications(), "\n", _function_1);
    _builder.append(_join_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    {
      IsolateSpecification _isolateSpecification = object.getIsolateSpecification();
      boolean _tripleNotEquals = (_isolateSpecification != null);
      if (_tripleNotEquals) {
        CharSequence _doSwitch = this.doSwitch(object.getIsolateSpecification());
        _builder.append(_doSwitch, "\t");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    {
      ScheduleTreeExpression _child = object.getChild();
      boolean _tripleNotEquals_1 = (_child != null);
      if (_tripleNotEquals_1) {
        _builder.append(" + ");
        CharSequence _doSwitch_1 = this.doSwitch(object.getChild());
        _builder.append(_doSwitch_1);
      }
    }
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseBandPiece(final BandPiece object) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _doSwitch = this.doSwitch(object.getPieceDomain());
    _builder.append(_doSwitch);
    _builder.append(" : ");
    String _printSchedule = this.printSchedule(object);
    _builder.append(_printSchedule);
    return _builder;
  }

  @Override
  public CharSequence caseLoopTypeSpecification(final LoopTypeSpecification object) {
    StringConcatenation _builder = new StringConcatenation();
    String _unparseString = object.unparseString();
    _builder.append(_unparseString);
    _builder.append("(");
    int _dimension = object.getDimension();
    _builder.append(_dimension);
    _builder.append(")");
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseIsolateSpecification(final IsolateSpecification object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("isolate (");
    String _printDomain = this.printDomain(object);
    _builder.append(_printDomain);
    final Function1<LoopTypeSpecification, CharSequence> _function = (LoopTypeSpecification it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<LoopTypeSpecification>join(object.getLoopTypeSpecifications(), " ", ", ", "", _function);
    _builder.append(_join);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseTileBandExpression(final TileBandExpression object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("tile-band");
    CharSequence _printTilingType = this.printTilingType(object);
    _builder.append(_printTilingType);
    CharSequence _printDimNames = this.printDimNames(object);
    _builder.append(_printDimNames);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    final Function1<BandPiece, CharSequence> _function = (BandPiece it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<BandPiece>join(object.getBandPieces(), "\n", _function);
    _builder.append(_join, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _doSwitch = this.doSwitch(object.getTilingSpecification());
    _builder.append(_doSwitch, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }

  private CharSequence printTilingType(final TileBandExpression tbe) {
    CharSequence _xifexpression = null;
    TILING_TYPE _tilingType = tbe.getTilingType();
    boolean _equals = Objects.equal(_tilingType, TILING_TYPE.FIXED_SIZE);
    if (_equals) {
      _xifexpression = "";
    } else {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(" ");
      _builder.append("(");
      String _literal = tbe.getTilingType().getLiteral();
      _builder.append(_literal, " ");
      _builder.append(")");
      _xifexpression = _builder;
    }
    return _xifexpression;
  }

  /**
   * override
   */
  public CharSequence caseTileLoopSpecification(final TileLoopSpecification object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("tile");
    {
      boolean _isParallel = object.isParallel();
      if (_isParallel) {
        _builder.append(" parallel");
      }
    }
    _builder.append(" (");
    final Function1<TileSizeSpecification, CharSequence> _function = (TileSizeSpecification it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<TileSizeSpecification>join(object.getTileSizeSpecifications(), ",", _function);
    _builder.append(_join);
    _builder.append(") ");
    CharSequence _printSchedule = this.printSchedule(object);
    _builder.append(_printSchedule);
    _builder.newLineIfNotEmpty();
    CharSequence _doSwitch = this.doSwitch(object.getTilingSpecification());
    _builder.append(_doSwitch);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  @Override
  public CharSequence caseTileSizeSpecification(final TileSizeSpecification object) {
    return object.unparseString();
  }

  /**
   * override
   */
  public CharSequence casePointLoopSpecification(final PointLoopSpecification object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("point ");
    CharSequence _printSchedule = this.printSchedule(object);
    _builder.append(_printSchedule);
    _builder.append(" ");
    final Function1<LoopTypeSpecification, CharSequence> _function = (LoopTypeSpecification it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<LoopTypeSpecification>join(object.getLoopTypeSpecifications(), " ", _function);
    _builder.append(_join);
    {
      FullTileSpecification _fullTileSpecification = object.getFullTileSpecification();
      boolean _tripleNotEquals = (_fullTileSpecification != null);
      if (_tripleNotEquals) {
        CharSequence _doSwitch = this.doSwitch(object.getFullTileSpecification());
        _builder.append(_doSwitch);
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseFullTileSpecification(final FullTileSpecification object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("full-tile ");
    {
      int _length = ((Object[])Conversions.unwrapArray(object.getLoopTypeSpecifications(), Object.class)).length;
      boolean _greaterThan = (_length > 0);
      if (_greaterThan) {
        _builder.append("(");
        final Function1<LoopTypeSpecification, CharSequence> _function = (LoopTypeSpecification it) -> {
          return this.doSwitch(it);
        };
        String _join = IterableExtensions.<LoopTypeSpecification>join(object.getLoopTypeSpecifications(), " ", _function);
        _builder.append(_join);
        _builder.append(")");
      }
    }
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseExtensionExpression(final ExtensionExpression object) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("extend (");
    final Function1<ExtensionTarget, CharSequence> _function = (ExtensionTarget it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.<ExtensionTarget>join(object.getExtensionTargets(), ", ", _function);
    _builder.append(_join);
    _builder.append(") ");
    CharSequence _doSwitch = this.doSwitch(object.getChild());
    _builder.append(_doSwitch);
    return _builder;
  }

  /**
   * override
   */
  public CharSequence caseExtensionTarget(final ExtensionTarget object) {
    StringConcatenation _builder = new StringConcatenation();
    String _printExtensionMap = this.printExtensionMap(object.getExtensionMap());
    _builder.append(_printExtensionMap);
    _builder.append(" as ");
    String _name = object.getName();
    _builder.append(_name);
    CharSequence _printDimNames = this.printDimNames(object);
    _builder.append(_printDimNames);
    return _builder;
  }
}
