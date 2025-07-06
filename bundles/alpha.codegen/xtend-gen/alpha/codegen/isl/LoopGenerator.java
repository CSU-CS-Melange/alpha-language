package alpha.codegen.isl;

import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLASTBuild;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLIdentifier;
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Generates the loop statements that iterate through the points in a given domain.
 */
@SuppressWarnings("all")
public class LoopGenerator {
  /**
   * Generates the ISL AST node representing the loop statements that
   * iterate through the points in the given domain in lexicographic order
   * (i.e., scheduled by the identity function).
   */
  public static ISLASTNode generateLoops(final String macroName, final ISLSet domain) {
    final ISLMap identity = ISLMultiAff.buildIdentity(domain.getSpace().addDims(ISLDimType.isl_dim_in, domain.getNbIndices())).toMap();
    return LoopGenerator.generateLoops(macroName, domain, identity);
  }

  /**
   * Generates the ISL AST node representing the loop statements that
   * iterate through the points in the given domain according to the
   * timestamps indicated by the given map.
   */
  public static ISLASTNode generateLoops(final String macroName, final ISLSet domain, final ISLMap timestamps) {
    final ISLSet context = domain.copy().params();
    final ISLUnionMap schedule = timestamps.copy().intersectDomain(domain.copy()).setTupleName(ISLDimType.isl_dim_in, macroName).toUnionMap();
    final Function1<String, ISLIdentifier> _function = (String it) -> {
      return ISLIdentifier.alloc(ISLContext.getInstance(), it);
    };
    final ArrayList<ISLIdentifier> ids = CommonExtensions.<ISLIdentifier>toArrayList(ListExtensions.<String, ISLIdentifier>map(domain.getIndexNames(), _function));
    final Function2<ISLIdentifierList, ISLIdentifier, ISLIdentifierList> _function_1 = (ISLIdentifierList list, ISLIdentifier id) -> {
      return list.add(id);
    };
    final ISLIdentifierList idList = IterableExtensions.<ISLIdentifier, ISLIdentifierList>fold(ids, ISLIdentifierList.build(ISLContext.getInstance(), 0), _function_1);
    return ISLASTBuild.buildFromContext(context).setIterators(idList).generate(schedule);
  }

  /**
   * Generates the ISL AST node representing the loop statements that
   * iterate through the points in the given domain according to the
   * given domain for the parameters and a union map representing the schedule.
   */
  public static ISLASTNode generateLoops(final ISLSet parameterDomain, final ISLUnionMap schedule) {
    final ISLSet range = schedule.getRange().getSets().get(0);
    Iterable<String> _xifexpression = null;
    List<String> _indexNames = range.getIndexNames();
    boolean _tripleNotEquals = (_indexNames != null);
    if (_tripleNotEquals) {
      _xifexpression = range.getIndexNames();
    } else {
      int _dim = range.dim(ISLUtil.Dims.SET);
      final Function1<Integer, String> _function = (Integer it) -> {
        return ("c" + it);
      };
      _xifexpression = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _dim, true), _function);
    }
    final Iterable<String> indexNames = _xifexpression;
    final Function1<String, ISLIdentifier> _function_1 = (String it) -> {
      return ISLIdentifier.alloc(ISLContext.getInstance(), it);
    };
    final ArrayList<ISLIdentifier> ids = CommonExtensions.<ISLIdentifier>toArrayList(IterableExtensions.<String, ISLIdentifier>map(indexNames, _function_1));
    final Function2<ISLIdentifierList, ISLIdentifier, ISLIdentifierList> _function_2 = (ISLIdentifierList list, ISLIdentifier id) -> {
      return list.add(id);
    };
    final ISLIdentifierList idList = IterableExtensions.<ISLIdentifier, ISLIdentifierList>fold(ids, ISLIdentifierList.build(ISLContext.getInstance(), 0), _function_2);
    return ISLASTBuild.buildFromContext(parameterDomain).setIterators(idList).generate(schedule);
  }

  /**
   * Generates the ISL AST node representing the loop statements that
   * iterate through the points in the given domain according to the
   * given domain for the parameters and a union map representing the schedule.
   */
  public static ISLASTNode generateLoops(final ISLSet domain, final ISLSchedule schedule) {
    final Function1<String, ISLIdentifier> _function = (String it) -> {
      return ISLIdentifier.alloc(ISLContext.getInstance(), it);
    };
    final ArrayList<ISLIdentifier> ids = CommonExtensions.<ISLIdentifier>toArrayList(ListExtensions.<String, ISLIdentifier>map(domain.getIndexNames(), _function));
    final Function2<ISLIdentifierList, ISLIdentifier, ISLIdentifierList> _function_1 = (ISLIdentifierList list, ISLIdentifier id) -> {
      return list.add(id);
    };
    final ISLIdentifierList idList = IterableExtensions.<ISLIdentifier, ISLIdentifierList>fold(ids, ISLIdentifierList.build(ISLContext.getInstance(), 0), _function_1);
    return ISLASTBuild.buildFromContext(domain.copy().params()).setIterators(idList).generate(schedule);
  }
}
