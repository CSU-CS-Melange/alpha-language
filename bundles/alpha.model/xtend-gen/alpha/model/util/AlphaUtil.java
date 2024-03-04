package alpha.model.util;

import alpha.model.AlphaConstant;
import alpha.model.AlphaExpression;
import alpha.model.AlphaNode;
import alpha.model.AlphaPackage;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.Equation;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLAffList;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLErrorException;
import fr.irisa.cairn.jnimap.isl.ISLFactory;
import fr.irisa.cairn.jnimap.isl.ISLIdentifier;
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList;
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.JNIISLTools;
import fr.irisa.cairn.jnimap.runtime.JNIObject;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.Functions.Function3;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Utility methods for analysis and transformation of Alpha programs.
 */
@SuppressWarnings("all")
public class AlphaUtil {
  /**
   * Given a name candidate, ensures that it does not conflict
   * with existing variables. If a variable is in conflict,
   * the specified String is appended until there is no conlict.
   */
  public static Function3<AlphaSystem, String, String, String> duplicateNameResolver() {
    final Function3<AlphaSystem, String, String, String> _function = (AlphaSystem s, String nameCandidate, String postfix) -> {
      String currentName = nameCandidate;
      while ((s.getVariable(currentName) != null)) {
        currentName = (currentName + postfix);
      }
      return currentName;
    };
    return _function;
  }

  /**
   * Given a name candidate, ensures that it does not conflict
   * with existing variables. If a variable is in conflict,
   * an integer is added to the end, where the value increases
   * until there is no conflict.
   */
  public static Function2<AlphaSystem, String, String> duplicateNameResolverWithCounter() {
    final Function2<AlphaSystem, String, String> _function = (AlphaSystem s, String nameCandidate) -> {
      String currentName = nameCandidate;
      int count = 2;
      while ((s.getVariable(currentName) != null)) {
        {
          currentName = (nameCandidate + Integer.valueOf(count));
          count++;
        }
      }
      return currentName;
    };
    return _function;
  }

  public static AlphaRoot getContainerRoot(final EObject node) {
    if ((node instanceof AlphaRoot)) {
      return ((AlphaRoot) node);
    }
    EObject _eContainer = node.eContainer();
    boolean _tripleEquals = (_eContainer == null);
    if (_tripleEquals) {
      return null;
    }
    return AlphaUtil.getContainerRoot(node.eContainer());
  }

  public static AlphaSystem getContainerSystem(final EObject node) {
    if ((node instanceof AlphaSystem)) {
      return ((AlphaSystem) node);
    }
    EObject _eContainer = node.eContainer();
    boolean _tripleEquals = (_eContainer == null);
    if (_tripleEquals) {
      return null;
    }
    return AlphaUtil.getContainerSystem(node.eContainer());
  }

  public static SystemBody getContainerSystemBody(final EObject node) {
    if ((node instanceof SystemBody)) {
      return ((SystemBody) node);
    }
    EObject _eContainer = node.eContainer();
    boolean _tripleEquals = (_eContainer == null);
    if (_tripleEquals) {
      return null;
    }
    return AlphaUtil.getContainerSystemBody(node.eContainer());
  }

  public static Equation getContainerEquation(final EObject node) {
    if ((node instanceof Equation)) {
      return ((Equation) node);
    }
    EObject _eContainer = node.eContainer();
    boolean _tripleEquals = (_eContainer == null);
    if (_tripleEquals) {
      return null;
    }
    return AlphaUtil.getContainerEquation(node.eContainer());
  }

  public static StandardEquation getStandardEquation(final Variable variable) {
    StandardEquation _xblockexpression = null;
    {
      StandardEquation ret = ((StandardEquation) null);
      final Function1<Equation, Boolean> _function = (Equation e) -> {
        return Boolean.valueOf((e instanceof StandardEquation));
      };
      final Function1<Equation, StandardEquation> _function_1 = (Equation it) -> {
        return ((StandardEquation) it);
      };
      final Function1<StandardEquation, Boolean> _function_2 = (StandardEquation e) -> {
        Variable _variable = e.getVariable();
        return Boolean.valueOf(Objects.equal(_variable, variable));
      };
      final Iterable<StandardEquation> equs = IterableExtensions.<StandardEquation>filter(IterableExtensions.<Equation, StandardEquation>map(IterableExtensions.<Equation>filter(AlphaUtil.getContainerSystemBody(variable).getEquations(), _function), _function_1), _function_2);
      int _size = IterableExtensions.size(equs);
      boolean _equals = (_size == 1);
      if (_equals) {
        ret = ((StandardEquation[])Conversions.unwrapArray(equs, StandardEquation.class))[0];
      }
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  /**
   * Selects an AlphaRoot that contains a given system name. The given system name may be
   * fully qualified or just the bare name. If the bare name cannot uniquely identify a
   * system, then it throws a RuntimeException.
   */
  public static AlphaRoot selectAlphaRoot(final List<AlphaRoot> roots, final String systemName) {
    boolean _contains = systemName.contains(".");
    if (_contains) {
      final IQualifiedNameProvider provider = new DefaultDeclarativeQualifiedNameProvider();
      final Function1<AlphaRoot, TreeIterator<EObject>> _function = (AlphaRoot r) -> {
        return r.eAllContents();
      };
      final Function1<AlphaSystem, Boolean> _function_1 = (AlphaSystem s) -> {
        return Boolean.valueOf(provider.getFullyQualifiedName(s).toString().contentEquals(systemName));
      };
      final List<AlphaSystem> matching = IterableExtensions.<AlphaSystem>toList(IterableExtensions.<AlphaSystem>filter(Iterables.<AlphaSystem>filter(ListExtensions.<AlphaRoot, TreeIterator<EObject>>map(roots, _function), AlphaSystem.class), _function_1));
      int _length = ((Object[])Conversions.unwrapArray(matching, Object.class)).length;
      boolean _greaterThan = (_length > 0);
      if (_greaterThan) {
        return AlphaUtil.getContainerRoot(IterableExtensions.<AlphaSystem>head(matching));
      }
    } else {
      final Function1<AlphaRoot, Iterator<AlphaSystem>> _function_2 = (AlphaRoot it) -> {
        final Function1<AlphaSystem, Boolean> _function_3 = (AlphaSystem s) -> {
          return Boolean.valueOf(s.getName().contentEquals(systemName));
        };
        return IteratorExtensions.<AlphaSystem>filter(Iterators.<AlphaSystem>filter(it.eAllContents(), AlphaSystem.class), _function_3);
      };
      final List<AlphaSystem> matching_1 = IteratorExtensions.<AlphaSystem>toList(IteratorExtensions.<AlphaRoot, AlphaSystem>flatMap(roots.iterator(), _function_2));
      int _size = matching_1.size();
      boolean _greaterThan_1 = (_size > 1);
      if (_greaterThan_1) {
        throw new RuntimeException(("There are multiple systems with the name: " + systemName));
      }
      int _size_1 = matching_1.size();
      boolean _greaterThan_2 = (_size_1 > 0);
      if (_greaterThan_2) {
        return AlphaUtil.getContainerRoot(IterableExtensions.<AlphaSystem>head(matching_1));
      }
    }
    throw new RuntimeException((("System " + systemName) + " was not found."));
  }

  public static ISLSet getParameterDomain(final EObject node) {
    final AlphaSystem system = AlphaUtil.getContainerSystem(node);
    if ((system == null)) {
      throw new RuntimeException("Node is not contained by an AlphaSystem.");
    }
    if (((system.getParameterDomain() == null) || (system.getParameterDomain() == null))) {
      throw new RuntimeException("The parameter domain of the container system is null.");
    }
    return system.getParameterDomain();
  }

  /**
   * Replaces all AlphaConstants in the given string with its integer values.
   * It is based on String.replaceAll, so it may fail on some inputs.
   * Currently the model is that the users pick good names for AlphaConstants to avoid this issue
   */
  public static String replaceAlphaConstants(final AlphaSystem system, final String jniString) {
    if (((system != null) && (system.eContainer() != null))) {
      String str = jniString;
      EObject _eContainer = system.eContainer();
      Iterable<AlphaConstant> _gatherAlphaConstants = AlphaUtil.gatherAlphaConstants(((AlphaVisitable) _eContainer));
      for (final AlphaConstant ac : _gatherAlphaConstants) {
        str = str.replaceAll(ac.getName(), ac.getValue().toString());
      }
      return str;
    }
    return jniString;
  }

  public static Iterable<AlphaConstant> getAlphaConstants(final AlphaSystem system) {
    if (((system != null) && (system.eContainer() != null))) {
      EObject _eContainer = system.eContainer();
      return AlphaUtil.gatherAlphaConstants(((AlphaVisitable) _eContainer));
    }
    return null;
  }

  private static Iterable<AlphaConstant> _gatherAlphaConstants(final AlphaPackage ap) {
    return Iterables.<AlphaConstant>filter(ap.getElements(), AlphaConstant.class);
  }

  private static Iterable<AlphaConstant> _gatherAlphaConstants(final AlphaRoot ar) {
    return Iterables.<AlphaConstant>filter(ar.getElements(), AlphaConstant.class);
  }

  protected static JNIObject _copy(final Void n) {
    return null;
  }

  protected static JNIObject _copy(final ISLMap map) {
    return map.copy();
  }

  protected static JNIObject _copy(final ISLSet set) {
    return set.copy();
  }

  protected static JNIObject _copy(final ISLMultiAff maff) {
    return maff.copy();
  }

  protected static JNIObject _copy(final ISLUnionMap umap) {
    return umap.copy();
  }

  /**
   * Method that adds parameter domain names and replaces AlphaConstants with its value.
   * Last step before passing the string to ISL.
   */
  public static String toContextFreeISLString(final AlphaSystem system, final String alphaDom) {
    String _xblockexpression = null;
    {
      final StringBuffer completed = new StringBuffer("[");
      completed.append(String.join(",", system.getParameterDomain().getParamNames()));
      completed.append("] -> ");
      completed.append(alphaDom);
      _xblockexpression = AlphaUtil.replaceAlphaConstants(system, completed.toString());
    }
    return _xblockexpression;
  }

  protected static ISLSet _getScalarDomain(final AlphaSystem system) {
    ISLSet _xblockexpression = null;
    {
      ISLSet jniset = ISLFactory.islSet(AlphaUtil.toContextFreeISLString(system, "{ [] : }"));
      final ISLSet pdom = system.getParameterDomain();
      _xblockexpression = jniset.intersectParams(pdom.copy());
    }
    return _xblockexpression;
  }

  protected static ISLSet _getScalarDomain(final AlphaExpression expr) {
    ISLSet _xblockexpression = null;
    {
      AlphaSystem _containerSystem = AlphaUtil.getContainerSystem(expr);
      boolean _tripleEquals = (_containerSystem == null);
      if (_tripleEquals) {
        return null;
      }
      _xblockexpression = AlphaUtil.getScalarDomain(AlphaUtil.getContainerSystem(expr));
    }
    return _xblockexpression;
  }

  /**
   * Helper function to obtain the additional indices due to while expressions when parsing polyhedral objects specified in ArrayNotation
   */
  public static List<String> getWhileIndexNames(final AlphaNode node) {
    List<String> _xblockexpression = null;
    {
      final AlphaSystem containerSystem = AlphaUtil.getContainerSystem(node);
      List<String> _xifexpression = null;
      ISLSet _whileDomain = containerSystem.getWhileDomain();
      boolean _tripleNotEquals = (_whileDomain != null);
      if (_tripleNotEquals) {
        _xifexpression = containerSystem.getWhileDomain().getIndexNames();
      } else {
        _xifexpression = new LinkedList<String>();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  public static <T extends Object> T callISLwithErrorHandling(final Supplier<T> r, final Consumer<String> f) {
    return AlphaUtil.<T>callISLwithErrorHandling(r, f, null);
  }

  public static <T extends Object> T callISLwithErrorHandling(final Supplier<T> r, final Consumer<String> f, final T defaultValue) {
    try {
      return JNIISLTools.<T>recordError(r);
    } catch (final Throwable _t) {
      if (_t instanceof ISLErrorException) {
        final ISLErrorException e = (ISLErrorException)_t;
        f.accept(e.islErrorMessage);
        return defaultValue;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  public static void callISLwithErrorHandling(final Runnable r, final Consumer<String> f) {
    try {
      JNIISLTools.recordError(r);
    } catch (final Throwable _t) {
      if (_t instanceof ISLErrorException) {
        final ISLErrorException e = (ISLErrorException)_t;
        f.accept(e.islErrorMessage);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  public static ISLSet renameIndices(final ISLSet set, final List<String> names) {
    final int n = set.getNbIndices();
    ISLSet res = set;
    int _length = ((Object[])Conversions.unwrapArray(names, Object.class)).length;
    boolean _greaterThan = (n > _length);
    if (_greaterThan) {
      throw new RuntimeException("Need n or more index names to rename n-d space.");
    }
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, n, true);
    for (final Integer i : _doubleDotLessThan) {
      res = res.setDimName(ISLDimType.isl_dim_set, (i).intValue(), names.get((i).intValue()));
    }
    return res;
  }

  public static ISLMap renameIndices(final ISLMap map, final List<String> names) {
    final int n = map.getNbInputs();
    ISLMap res = map;
    int _length = ((Object[])Conversions.unwrapArray(names, Object.class)).length;
    boolean _greaterThan = (n > _length);
    if (_greaterThan) {
      throw new RuntimeException("Need n or more index names to rename n-d space.");
    }
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, n, true);
    for (final Integer i : _doubleDotLessThan) {
      res = res.setDimName(ISLDimType.isl_dim_in, (i).intValue(), names.get((i).intValue()));
    }
    return res;
  }

  public static ISLMultiAff renameIndices(final ISLMultiAff maff, final List<String> names) {
    final int n = maff.getNbInputs();
    int _length = ((Object[])Conversions.unwrapArray(names, Object.class)).length;
    boolean _greaterThan = (n > _length);
    if (_greaterThan) {
      throw new RuntimeException("Need n or more index names to rename n-d space.");
    }
    ISLMultiAff res = maff;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, n, true);
    for (final Integer i : _doubleDotLessThan) {
      res = res.setDimName(ISLDimType.isl_dim_in, (i).intValue(), names.get((i).intValue()));
    }
    return res;
  }

  public static ISLPWQPolynomial renameIndices(final ISLPWQPolynomial pwqp, final List<String> names) {
    final int n = pwqp.dim(ISLDimType.isl_dim_in);
    int _length = ((Object[])Conversions.unwrapArray(names, Object.class)).length;
    boolean _greaterThan = (n > _length);
    if (_greaterThan) {
      throw new RuntimeException("Need n or more index names to rename n-d space.");
    }
    ISLPWQPolynomial res = pwqp;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, n, true);
    for (final Integer i : _doubleDotLessThan) {
      res = res.setDimName(ISLDimType.isl_dim_in, (i).intValue(), names.get((i).intValue()));
    }
    return res;
  }

  public static ISLQPolynomial renameIndices(final ISLQPolynomial qp, final List<String> names) {
    final int n = qp.dim(ISLDimType.isl_dim_in);
    int _length = ((Object[])Conversions.unwrapArray(names, Object.class)).length;
    boolean _greaterThan = (n > _length);
    if (_greaterThan) {
      throw new RuntimeException("Need n or more index names to rename n-d space.");
    }
    ISLQPolynomial res = qp;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, n, true);
    for (final Integer i : _doubleDotLessThan) {
      res = res.setDimName(ISLDimType.isl_dim_in, (i).intValue(), names.get((i).intValue()));
    }
    return res;
  }

  public static List<String> defaultDimNames(final int n) {
    return AlphaUtil.defaultDimNames(0, n);
  }

  public static List<String> defaultDimNames(final int offset, final int n) {
    final Function1<Integer, String> _function = (Integer i) -> {
      return ("i" + i);
    };
    return IterableExtensions.<String>toList(IterableExtensions.<Integer, String>map(new ExclusiveRange(offset, (offset + n), true), _function));
  }

  public static List<String> defaultDimNames(final ISLSet set) {
    return AlphaUtil.defaultDimNames(set.getNbIndices());
  }

  public static int[] parseIntArray(final String intVecStr) {
    int[] _xifexpression = null;
    boolean _contains = intVecStr.contains(",");
    if (_contains) {
      final ToIntFunction<String> _function = (String e) -> {
        return Integer.parseInt(e.trim());
      };
      _xifexpression = ((List<String>)Conversions.doWrapArray(intVecStr.replace("[", "").replace("]", "").trim().split("\\s*,\\s*"))).stream().mapToInt(_function).toArray();
    } else {
      final ToIntFunction<String> _function_1 = (String e) -> {
        return Integer.parseInt(e.trim());
      };
      _xifexpression = ((List<String>)Conversions.doWrapArray(intVecStr.replace("[", "").replace("]", "").trim().split("\\s+"))).stream().mapToInt(_function_1).toArray();
    }
    return _xifexpression;
  }

  public static List<Integer> parseIntVector(final String intVecStr) {
    return IterableExtensions.<Integer>toList(((Iterable<Integer>)Conversions.doWrapArray(AlphaUtil.parseIntArray(intVecStr))));
  }

  public static int numDims(final Variable variable) {
    return variable.getDomain().dim(ISLDimType.isl_dim_out);
  }

  public static List<String> indices(final Variable variable) {
    return IterableExtensions.<String>toList(variable.getDomain().getIndexNames());
  }

  public static List<String> indexNames(final ISLMultiAff maff) {
    return maff.getDomainSpace().getIndexNames();
  }

  public static List<String> paramNames(final ISLMultiAff maff) {
    return maff.getDomainSpace().getParamNames();
  }

  public static List<String> indexNames(final ISLSpace space) {
    return IterableExtensions.<String>toList(space.getIndexNames());
  }

  public static List<String> paramNames(final ISLSpace space) {
    return IterableExtensions.<String>toList(space.getParamNames());
  }

  public static Iterable<Variable> listAllReferencedVariables(final AlphaNode e) {
    final Function1<VariableExpression, Variable> _function = (VariableExpression it) -> {
      return it.getVariable();
    };
    return IterableExtensions.<VariableExpression, Variable>map(AlphaUtil.listAllVariableExpressions(e), _function);
  }

  public static Iterable<VariableExpression> listAllVariableExpressions(final AlphaNode e) {
    return Iterables.<VariableExpression>filter(AlphaUtil.listAllChildrenExpressions(e), VariableExpression.class);
  }

  public static Iterable<AlphaExpression> listChildrenExpressions(final AlphaNode e) {
    return Iterables.<AlphaExpression>filter(e.eContents(), AlphaExpression.class);
  }

  public static Iterable<AlphaExpression> listAllChildrenExpressions(final AlphaNode e) {
    return IteratorExtensions.<AlphaExpression>toIterable(Iterators.<AlphaExpression>filter(e.eAllContents(), AlphaExpression.class));
  }

  public static ISLIdentifierList toIdentifierList(final List<String> iterators, final ISLContext context) {
    ISLIdentifierList _xblockexpression = null;
    {
      ISLIdentifierList ret = ISLIdentifierList.build(context, iterators.size());
      int _size = iterators.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        ret = ret.insert((i).intValue(), ISLIdentifier.alloc(context, iterators.get((i).intValue())));
      }
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static ISLIdentifierList toIdentifierList(final List<ISLIdentifier> iterators) {
    ISLIdentifierList _xblockexpression = null;
    {
      int _size = iterators.size();
      boolean _equals = (_size == 0);
      if (_equals) {
        return null;
      }
      final ISLContext context = iterators.get(0).getContext();
      ISLIdentifierList ret = ISLIdentifierList.build(context, iterators.size());
      int _size_1 = iterators.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size_1, true);
      for (final Integer i : _doubleDotLessThan) {
        ret = ret.insert((i).intValue(), iterators.get((i).intValue()));
      }
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static ISLAffList[] lexmins(final ISLSet set) {
    final Function1<ISLBasicSet, ISLAffList> _function = (ISLBasicSet it) -> {
      return AlphaUtil.lexSwitch(it, false);
    };
    return ((ISLAffList[])Conversions.unwrapArray(ListExtensions.<ISLBasicSet, ISLAffList>map(set.getBasicSets(), _function), ISLAffList.class));
  }

  public static ISLAffList[] lexmaxes(final ISLSet set) {
    final Function1<ISLBasicSet, ISLAffList> _function = (ISLBasicSet it) -> {
      return AlphaUtil.lexSwitch(it, true);
    };
    return ((ISLAffList[])Conversions.unwrapArray(ListExtensions.<ISLBasicSet, ISLAffList>map(set.getBasicSets(), _function), ISLAffList.class));
  }

  public static ISLAffList lexSwitch(final ISLBasicSet set, final boolean max) {
    ISLAffList _xblockexpression = null;
    {
      final int dim = set.dim(ISLDimType.isl_dim_out);
      ISLAffList ret = ISLAffList.build(set.getContext(), dim);
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, dim, true);
      for (final Integer i : _doubleDotLessThan) {
        ret = ret.add(AlphaUtil.lexSwitch(set, (i).intValue(), max));
      }
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static ISLAff lexSwitch(final ISLBasicSet set, final int dim, final boolean max) {
    try {
      ISLAff _xblockexpression = null;
      {
        final ISLLocalSpace space = set.getSpace().toLocalSpace();
        ISLBasicSet mset = set.copy().moveDims(ISLDimType.isl_dim_param, 0, ISLDimType.isl_dim_out, dim, 1).moveDims(ISLDimType.isl_dim_out, 0, ISLDimType.isl_dim_param, 0, 1);
        ISLSet _xifexpression = null;
        if (max) {
          _xifexpression = mset.lexMax();
        } else {
          _xifexpression = mset.lexMin();
        }
        final ISLSet m = _xifexpression;
        int _nbBasicSets = m.getNbBasicSets();
        boolean _notEquals = (_nbBasicSets != 1);
        if (_notEquals) {
          throw new Exception("Unexpected number of basic sets");
        }
        mset = m.getBasicSetAt(0).dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, 1);
        final long[][] mat = DomainOperations.toISLEqualityMatrix(mset).toLongMatrix();
        final Function1<Long, Integer> _function = (Long it) -> {
          return Integer.valueOf(it.intValue());
        };
        final List<Integer> coeffs = ListExtensions.<Long, Integer>map(((List<Long>)Conversions.doWrapArray(mat[0])), _function);
        ISLAff aff = ISLAff.buildZero(space);
        int _dim = aff.dim(ISLDimType.isl_dim_param);
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _dim, true);
        for (final Integer i : _doubleDotLessThan) {
          aff = aff.setCoefficient(ISLDimType.isl_dim_param, (i).intValue(), (coeffs.get((i).intValue())).intValue());
        }
        int _size = coeffs.size();
        int _minus = (_size - 1);
        aff = aff.setConstant((coeffs.get(_minus)).intValue());
        aff = aff.negate();
        _xblockexpression = aff;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  private static Iterable<AlphaConstant> gatherAlphaConstants(final EObject ap) {
    if (ap instanceof AlphaPackage) {
      return _gatherAlphaConstants((AlphaPackage)ap);
    } else if (ap instanceof AlphaRoot) {
      return _gatherAlphaConstants((AlphaRoot)ap);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(ap).toString());
    }
  }

  public static JNIObject copy(final JNIObject map) {
    if (map instanceof ISLMap) {
      return _copy((ISLMap)map);
    } else if (map instanceof ISLSet) {
      return _copy((ISLSet)map);
    } else if (map instanceof ISLMultiAff) {
      return _copy((ISLMultiAff)map);
    } else if (map instanceof ISLUnionMap) {
      return _copy((ISLUnionMap)map);
    } else if (map == null) {
      return _copy((Void)null);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(map).toString());
    }
  }

  public static ISLSet getScalarDomain(final EObject system) {
    if (system instanceof AlphaSystem) {
      return _getScalarDomain((AlphaSystem)system);
    } else if (system instanceof AlphaExpression) {
      return _getScalarDomain((AlphaExpression)system);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(system).toString());
    }
  }
}
