package alpha.model.util;

import alpha.model.AlphaConstant;
import alpha.model.AlphaExpression;
import alpha.model.AlphaNode;
import alpha.model.AlphaPackage;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitable;
import alpha.model.Equation;
import alpha.model.SystemBody;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLErrorException;
import fr.irisa.cairn.jnimap.isl.ISLFactory;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;
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

  /**
   * Renames all the indices of the given set to the default names,
   * replacing any which may be present.
   */
  public static ISLSet renameIndices(final ISLSet set) {
    return AlphaUtil.renameIndices(set, AlphaUtil.defaultDimNames(set));
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

  public static ISLMultiAff renameInputs(final ISLMultiAff maff) {
    return AlphaUtil.renameInputs(maff, AlphaUtil.defaultInputNames(maff));
  }

  public static ISLMultiAff renameOutputs(final ISLMultiAff maff) {
    return AlphaUtil.renameOutputs(maff, AlphaUtil.defaultOutputNames(maff));
  }

  public static ISLMultiAff renameInputs(final ISLMultiAff maff, final List<String> names) {
    final int nbDims = maff.getNbInputs();
    int _length = ((Object[])Conversions.unwrapArray(names, Object.class)).length;
    boolean _greaterThan = (nbDims > _length);
    if (_greaterThan) {
      throw new RuntimeException("Need n or more index names to rename n-d space.");
    }
    final Function2<ISLMultiAff, Integer, ISLMultiAff> _function = (ISLMultiAff _maff, Integer dim) -> {
      return _maff.setDimName(ISLDimType.isl_dim_in, (dim).intValue(), names.get((dim).intValue()));
    };
    return IterableExtensions.<Integer, ISLMultiAff>fold(new ExclusiveRange(0, nbDims, true), maff, _function);
  }

  public static ISLMultiAff renameOutputs(final ISLMultiAff maff, final List<String> names) {
    final int nbDims = maff.getNbOutputs();
    int _length = ((Object[])Conversions.unwrapArray(names, Object.class)).length;
    boolean _greaterThan = (nbDims > _length);
    if (_greaterThan) {
      throw new RuntimeException("Need n or more index names to rename n-d space.");
    }
    final Function2<ISLMultiAff, Integer, ISLMultiAff> _function = (ISLMultiAff _maff, Integer dim) -> {
      return _maff.setDimName(ISLDimType.isl_dim_out, (dim).intValue(), names.get((dim).intValue()));
    };
    return IterableExtensions.<Integer, ISLMultiAff>fold(new ExclusiveRange(0, nbDims, true), maff, _function);
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

  public static List<String> defaultInputNames(final ISLMultiAff maff) {
    return AlphaUtil.defaultDimNames(maff.getNbInputs());
  }

  public static List<String> defaultOutputNames(final ISLMultiAff maff) {
    final Function1<String, String> _function = (String s) -> {
      return (s + "\'");
    };
    return ListExtensions.<String, String>map(AlphaUtil.defaultDimNames(maff.getNbInputs()), _function);
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
