package alpha.model.memorymapper;

import alpha.model.Variable;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SuppressWarnings("all")
public class ManualMemoryMapper implements MemoryMapper {
  private Map<String, ISLMap> maps;

  public ManualMemoryMapper(final Map<String, String> maps) {
    HashMap<String, ISLMap> _hashMap = new HashMap<String, ISLMap>();
    this.maps = _hashMap;
    final BiConsumer<String, String> _function = (String name, String map) -> {
      this.maps.put(name, ISLUtil.toISLMap(map));
    };
    maps.forEach(_function);
  }

  @Override
  public ISLMap getMemoryMap(final Variable variable) {
    return this.maps.get(variable.getName()).copy();
  }
}
