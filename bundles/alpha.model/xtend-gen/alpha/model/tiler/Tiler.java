package alpha.model.tiler;

import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Set;

@SuppressWarnings("all")
public interface Tiler {
  ISLMap getTileMap();

  /**
   * Returns a map that takes in the indices of a tile, and outputs
   * the corresponding region in spacetime
   */
  ISLMap getUntileMap();

  Set<Integer> getTiledDims();

  ISLSet getApproximateOutset(final ISLSet domain);
}
