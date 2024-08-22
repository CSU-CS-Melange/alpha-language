package alpha.model.memorymapper;

import alpha.model.Variable;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SuppressWarnings("all")
public class ManualMemoryMapper implements MemoryMapper {
  private Map<String, MemoryMap> maps;

  public ManualMemoryMapper(final Map<String, String> maps, final Map<String, String> destinations) {
    HashMap<String, MemoryMap> _hashMap = new HashMap<String, MemoryMap>();
    this.maps = _hashMap;
    final BiConsumer<String, String> _function = (String name, String map) -> {
      String _get = destinations.get(name);
      ISLMap _iSLMap = ISLUtil.toISLMap(map);
      MemoryMap _memoryMap = new MemoryMap(_get, _iSLMap);
      this.maps.put(name, _memoryMap);
    };
    maps.forEach(_function);
  }

  @Override
  public ISLMap getMemoryMap(final Variable variable) {
    ISLMap _xifexpression = null;
    MemoryMap _get = this.maps.get(variable.getName());
    boolean _tripleNotEquals = (_get != null);
    if (_tripleNotEquals) {
      _xifexpression = this.maps.get(variable.getName()).getMap().copy();
    } else {
      _xifexpression = variable.getDomain().copy().identity();
    }
    return _xifexpression;
  }

  @Override
  public String getDestination(final Variable variable) {
    String _xifexpression = null;
    MemoryMap _get = this.maps.get(variable.getName());
    boolean _tripleNotEquals = (_get != null);
    if (_tripleNotEquals) {
      _xifexpression = this.maps.get(variable.getName()).getDestination();
    } else {
      _xifexpression = null;
    }
    return _xifexpression;
  }
}
