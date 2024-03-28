package alpha.targetmapping.util;

import alpha.model.AlphaSystem;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.targetmapping.DataType;
import alpha.targetmapping.TargetMapping;
import alpha.targetmapping.TargetMappingForSystemBody;
import alpha.targetmapping.TargetMappingNode;
import fr.irisa.cairn.jnimap.isl.ISLASTLoopType;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLAffList;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class TargetMappingUtil {
  public static Function<Integer, String> DEFAULT_SCHEDULE_DIMENSION_NAME_PROVIDER = ((Function<Integer, String>) (Integer x) -> {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("i");
    _builder.append(x);
    return _builder.toString();
  });

  public static TargetMapping getContainerTM(final TargetMappingNode node) {
    if ((node instanceof TargetMapping)) {
      return ((TargetMapping) node);
    }
    EObject _eContainer = node.eContainer();
    boolean _tripleEquals = (_eContainer == null);
    if (_tripleEquals) {
      return null;
    }
    EObject _eContainer_1 = node.eContainer();
    return TargetMappingUtil.getContainerTM(((TargetMappingNode) _eContainer_1));
  }

  public static TargetMappingForSystemBody getContainerTMforSystemBody(final TargetMappingNode node) {
    if ((node instanceof TargetMappingForSystemBody)) {
      return ((TargetMappingForSystemBody) node);
    }
    EObject _eContainer = node.eContainer();
    boolean _tripleEquals = (_eContainer == null);
    if (_tripleEquals) {
      return null;
    }
    EObject _eContainer_1 = node.eContainer();
    return TargetMappingUtil.getContainerTMforSystemBody(((TargetMappingNode) _eContainer_1));
  }

  public static AlphaSystem getTargetSystem(final TargetMappingNode node) {
    final TargetMapping tm = TargetMappingUtil.getContainerTM(node);
    if ((tm == null)) {
      return null;
    }
    return tm.getTargetSystem();
  }

  public static SystemBody getTargetSystemBody(final TargetMappingNode node) {
    final TargetMappingForSystemBody tm = TargetMappingUtil.getContainerTMforSystemBody(node);
    if ((tm == null)) {
      return null;
    }
    return tm.getTargetBody();
  }

  public static String toString(final ISLASTLoopType value) {
    int _value = value.getValue();
    switch (_value) {
      case ISLASTLoopType.ISL_AST_LOOP_DEFAULT:
        return "default";
      case ISLASTLoopType.ISL_AST_LOOP_ATOMIC:
        return "atomic";
      case ISLASTLoopType.ISL_AST_LOOP_UNROLL:
        return "unroll";
      case ISLASTLoopType.ISL_AST_LOOP_SEPARATE:
        return "separate";
      default:
        throw new IllegalArgumentException();
    }
  }

  /**
   * Returns a mult-dimensional affine function expressing the transformation applied to express the tile loop schedule.
   * Each dimension of the returned function has the form (i -> i - i%ts)
   * 
   * Input ISLSpace is not consumed.
   */
  public static ISLMultiAff tileLoopTransformation(final ISLSpace space, final List<Integer> tileSizes) {
    final BiFunction<ISLLocalSpace, Integer, ISLAff> _function = (ISLLocalSpace ls, Integer dim) -> {
      ISLAff _xblockexpression = null;
      {
        final ISLAff divisor = ISLAff.buildZero(ls.copy()).addConstant((tileSizes.get((dim).intValue())).intValue());
        ISLAff aff = ISLAff.buildZero(ls);
        _xblockexpression = aff.addCoefficient(ISLDimType.isl_dim_in, (dim).intValue(), 1).div(divisor);
      }
      return _xblockexpression;
    };
    return TargetMappingUtil.tilingTransformationHelper(_function, space, tileSizes);
  }

  /**
   * Returns a mult-dimensional affine function expressing the transformation applied to express the point loop schedule.
   * Each dimension of the returned function has the form (i -> i%ts)
   * 
   * Input ISLSpace is not consumed.
   */
  public static ISLMultiAff pointLoopTransformation(final ISLSpace space, final List<Integer> tileSizes) {
    final BiFunction<ISLLocalSpace, Integer, ISLAff> _function = (ISLLocalSpace ls, Integer dim) -> {
      ISLAff _xblockexpression = null;
      {
        ISLAff aff = ISLAff.buildZero(ls);
        _xblockexpression = aff.addCoefficient(ISLDimType.isl_dim_in, (dim).intValue(), 1).mod((tileSizes.get((dim).intValue())).intValue());
      }
      return _xblockexpression;
    };
    return TargetMappingUtil.tilingTransformationHelper(_function, space, tileSizes);
  }

  /**
   * Helper to avoid replicating all the setup code for construction tile and point loop functions.
   */
  private static ISLMultiAff tilingTransformationHelper(final BiFunction<ISLLocalSpace, Integer, ISLAff> f, final ISLSpace space, final List<Integer> tileSizes) {
    int _nbOutputs = space.getNbOutputs();
    int _size = tileSizes.size();
    boolean _notEquals = (_nbOutputs != _size);
    if (_notEquals) {
      String _format = String.format("Space and tile sizes do not match: %s %s", space, tileSizes);
      throw new RuntimeException(_format);
    }
    ISLSpace _xifexpression = null;
    boolean _isSetSpace = space.isSetSpace();
    if (_isSetSpace) {
      _xifexpression = space.copy();
    } else {
      _xifexpression = space.copy().domain();
    }
    final ISLSpace domainSpace = _xifexpression;
    final ISLLocalSpace localSpace = domainSpace.copy().toLocalSpace();
    ISLAffList affList = ISLAffList.build(ISLContext.getInstance(), tileSizes.size());
    int _size_1 = tileSizes.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size_1, true);
    for (final Integer dim : _doubleDotLessThan) {
      {
        final ISLAff aff = f.apply(localSpace.copy(), dim);
        affList = affList.add(aff);
      }
    }
    final ISLSpace affSpace = domainSpace.toMapSpaceFromSetSpace();
    return ISLMultiAff.buildFromAffList(affSpace, affList);
  }

  public static DataType dataType(final Variable variable) {
    return DataType.FLOAT;
  }
}
