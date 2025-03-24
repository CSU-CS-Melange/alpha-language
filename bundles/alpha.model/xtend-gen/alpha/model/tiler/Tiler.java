package alpha.model.tiler;

import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Set;

@SuppressWarnings("all")
public interface Tiler {
  ISLMap getTileMap();

  Set<Integer> getTiledDims();

  ISLSet getApproximateOutset(final ISLSet domain);
}
