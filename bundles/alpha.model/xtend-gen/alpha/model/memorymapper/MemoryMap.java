package alpha.model.memorymapper;

import fr.irisa.cairn.jnimap.isl.ISLMap;

@SuppressWarnings("all")
public class MemoryMap {
  private final String destination;

  private final ISLMap map;

  public MemoryMap(final String dest, final ISLMap map) {
    this.destination = dest;
    this.map = map;
  }

  public ISLMap getMap() {
    return this.map;
  }

  public String getDestination() {
    return this.destination;
  }
}
