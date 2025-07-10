package alpha.model.memorymapper;

import alpha.model.Variable;
import fr.irisa.cairn.jnimap.isl.ISLMap;

@SuppressWarnings("all")
public class IdentityMemoryMapper implements MemoryMapper {
  public ISLMap getMemoryMap(final Variable variable) {
    return variable.getDomain().copy().identity();
  }

  public String getDestination(final Variable variable) {
    return variable.getName();
  }
}
