package alpha.abft.codegen.util;

import alpha.model.AlphaSystem;
import alpha.model.Variable;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class MemoryMap {
  private final AlphaSystem system;

  private final HashMap<String, String> memoryMapNames = CollectionLiterals.<String, String>newHashMap();

  private final HashMap<String, ISLMap> memoryMap = CollectionLiterals.<String, ISLMap>newHashMap();

  private final HashMap<String, ISLSet> variableDomains = CollectionLiterals.<String, ISLSet>newHashMap();

  private final HashMap<String, List<String>> mappedIndexNames = CollectionLiterals.<String, List<String>>newHashMap();

  public List<Pair<String, ISLSet>> uniqueTargets() {
    List<Pair<String, ISLSet>> _xblockexpression = null;
    {
      final HashSet<String> processed = CollectionLiterals.<String>newHashSet();
      final LinkedList<String> targets = CollectionLiterals.<String>newLinkedList();
      final String x = IterableExtensions.<Map.Entry<String, String>>toList(this.memoryMapNames.entrySet()).toString();
      final Consumer<Variable> _function = (Variable v) -> {
        final String name = v.getName();
        final String mappedName = this.getName(v.getName());
        boolean _contains = processed.contains(mappedName);
        boolean _not = (!_contains);
        if (_not) {
          processed.add(mappedName);
          targets.add(name);
        }
      };
      this.system.getVariables().forEach(_function);
      final Function1<String, Pair<String, ISLSet>> _function_1 = (String t) -> {
        String _name = this.getName(t);
        ISLSet _range = this.getRange(t);
        return Pair.<String, ISLSet>of(_name, _range);
      };
      _xblockexpression = ListExtensions.<String, Pair<String, ISLSet>>map(targets, _function_1);
    }
    return _xblockexpression;
  }

  public MemoryMap(final AlphaSystem system) {
    this.system = system;
  }

  public String getName(final Variable variable) {
    return this.getName(variable.getName());
  }

  public String getName(final String name) {
    String _elvis = null;
    String _get = this.memoryMapNames.get(name);
    if (_get != null) {
      _elvis = _get;
    } else {
      _elvis = name;
    }
    return _elvis;
  }

  public ISLMap getMap(final Variable variable) {
    return this.getMap(variable.getName());
  }

  public ISLMap getMap(final String name) {
    ISLMap _elvis = null;
    ISLMap _get = this.memoryMap.get(name);
    ISLMap _copy = null;
    if (_get!=null) {
      _copy=_get.copy();
    }
    if (_copy != null) {
      _elvis = _copy;
    } else {
      final Function1<Variable, Boolean> _function = (Variable v) -> {
        String _name = v.getName();
        return Boolean.valueOf(Objects.equal(_name, name));
      };
      Variable _findFirst = IterableExtensions.<Variable>findFirst(this.system.getVariables(), _function);
      ISLSet _domain = null;
      if (_findFirst!=null) {
        _domain=_findFirst.getDomain();
      }
      ISLMap _identityMap = _domain.copy().toIdentityMap();
      _elvis = _identityMap;
    }
    return _elvis;
  }

  public ISLSet getDomain(final Variable variable) {
    return this.getDomain(variable.getName());
  }

  public ISLSet getDomain(final String name) {
    return this.variableDomains.get(name);
  }

  public ISLSet getRange(final Variable variable) {
    return this.getRange(variable.getName());
  }

  public ISLSet getRange(final String name) {
    ISLSet _xblockexpression = null;
    {
      final ISLSet domain = this.getDomain(name);
      if ((domain == null)) {
        final Function1<Variable, Boolean> _function = (Variable v) -> {
          String _name = v.getName();
          return Boolean.valueOf(Objects.equal(_name, name));
        };
        Variable _findFirst = IterableExtensions.<Variable>findFirst(this.system.getVariables(), _function);
        ISLSet _domain = null;
        if (_findFirst!=null) {
          _domain=_findFirst.getDomain();
        }
        return _domain.copy();
      }
      final List<String> indexNames = this.mappedIndexNames.get(name);
      final ISLSet mappedDomain = domain.copy().apply(this.getMap(name));
      ISLSet _xifexpression = null;
      int _size = indexNames.size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _xifexpression = AlphaUtil.renameIndices(mappedDomain, indexNames);
      } else {
        _xifexpression = mappedDomain;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  public String[] getIndexNames(final Variable variable) {
    return null;
  }

  public MemoryMap setMemoryMap(final String name, final String mappedName) {
    MemoryMap _xblockexpression = null;
    {
      final Function1<Variable, Boolean> _function = (Variable v) -> {
        String _name = v.getName();
        return Boolean.valueOf(Objects.equal(_name, name));
      };
      Variable _findFirst = IterableExtensions.<Variable>findFirst(this.system.getVariables(), _function);
      ISLSet _domain = null;
      if (_findFirst!=null) {
        _domain=_findFirst.getDomain();
      }
      final ISLSet domain = _domain;
      final ISLMap map = domain.copy().toIdentityMap();
      _xblockexpression = this.setMemoryMap(name, mappedName, map, domain, new String[] {});
    }
    return _xblockexpression;
  }

  public MemoryMap setMemoryMap(final Variable variable, final String mappedName) {
    MemoryMap _xblockexpression = null;
    {
      final ISLSet domain = variable.getDomain();
      final ISLMap map = domain.copy().toIdentityMap();
      _xblockexpression = this.setMemoryMap(variable.getName(), mappedName, map, domain, new String[] {});
    }
    return _xblockexpression;
  }

  public MemoryMap setMemoryMap(final String name, final String mappedName, final String map, final String[] indexNames) {
    MemoryMap _xblockexpression = null;
    {
      final Function1<Variable, Boolean> _function = (Variable v) -> {
        String _name = v.getName();
        return Boolean.valueOf(Objects.equal(_name, name));
      };
      Variable _findFirst = IterableExtensions.<Variable>findFirst(this.system.getVariables(), _function);
      ISLSet _domain = null;
      if (_findFirst!=null) {
        _domain=_findFirst.getDomain();
      }
      final ISLSet domain = _domain;
      _xblockexpression = this.setMemoryMap(name, mappedName, ISLUtil.toISLMap(map), domain, indexNames);
    }
    return _xblockexpression;
  }

  public MemoryMap setMemoryMap(final String name, final String mappedName, final ISLMap map, final ISLSet domain, final String[] indexNames) {
    this.memoryMapNames.put(name, mappedName);
    this.memoryMap.put(name, map);
    this.variableDomains.put(name, domain);
    this.mappedIndexNames.put(name, ((List<String>)Conversions.doWrapArray(indexNames)));
    return this;
  }

  public boolean isEmpty() {
    int _size = this.memoryMapNames.size();
    return (_size == 0);
  }

  public boolean isNonEmpty() {
    boolean _isEmpty = this.isEmpty();
    return (!_isEmpty);
  }

  @Override
  public String toString() {
    String _xifexpression = null;
    boolean _isNonEmpty = this.isNonEmpty();
    if (_isNonEmpty) {
      final Function1<Map.Entry<String, String>, String> _function = (Map.Entry<String, String> it) -> {
        String _xblockexpression = null;
        {
          final String name = it.getKey();
          final String mappedName = it.getValue();
          final ISLMap map = this.getMap(name);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(name);
          _builder.append(" -> ");
          _builder.append(mappedName);
          _builder.append(" by ");
          _builder.append(map);
          _xblockexpression = _builder.toString();
        }
        return _xblockexpression;
      };
      _xifexpression = IterableExtensions.join(IterableExtensions.<Map.Entry<String, String>, String>map(this.memoryMapNames.entrySet(), _function), "\n");
    } else {
      _xifexpression = "None";
    }
    return _xifexpression;
  }
}
