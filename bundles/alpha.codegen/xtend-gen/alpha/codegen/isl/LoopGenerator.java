package alpha.codegen.isl;

import alpha.model.util.CommonExtensions;
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
    return ISLASTBuild.buildFromContext(parameterDomain).generate(schedule);
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
