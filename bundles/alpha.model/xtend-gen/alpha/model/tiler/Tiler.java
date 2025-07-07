package alpha.model.tiler;

import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.Set;

@SuppressWarnings("all")
public interface Tiler {
  ISLUnionMap tileSchedule(final ISLUnionMap maps);

  ISLMap tileSchedule(final ISLMap map);

  /**
   * Returns a map that takes in the indices of a tile, and outputs
   * the corresponding region in spacetime
   */
  ISLMap getUntileMap();

  Set<Integer> getTiledDims();

  ISLSet getApproximateOutset(final ISLUnionSet domains);

  boolean fixedTileSizes();

  int getTileSize(final int dim);
}
