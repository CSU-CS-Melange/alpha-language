package alpha.model.memorymapper;

import alpha.model.Variable;
import fr.irisa.cairn.jnimap.isl.ISLMap;

@SuppressWarnings("all")
public class IdentityMemoryMapper implements MemoryMapper {
  @Override
  public ISLMap getMemoryMap(final Variable variable) {
    return variable.getDomain().copy().identity();
  }

  @Override
  public String getDestination(final Variable variable) {
    return variable.getName();
  }
}
