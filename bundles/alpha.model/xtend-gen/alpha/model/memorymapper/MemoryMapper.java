package alpha.model.memorymapper;

import alpha.model.Variable;
import fr.irisa.cairn.jnimap.isl.ISLMap;

@SuppressWarnings("all")
public interface MemoryMapper {
  ISLMap getMemoryMap(final Variable variable);

  String getDestination(final Variable variable);
}
