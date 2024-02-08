package alpha.model.util;

import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicMap;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLContext;

@SuppressWarnings("all")
public class ISLUtil {
  /**
   * Creates an ISLBasicSet from a string
   */
  public static ISLBasicSet toISLBasicSet(final String descriptor) {
    return ISLBasicSet.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Creates an ISLBasicMap from a string
   */
  public static ISLBasicMap toISLBasicMap(final String descriptor) {
    return ISLBasicMap.buildFromString(ISLContext.getInstance(), descriptor);
  }

  /**
   * Creates an ISLBasicSet from a string
   */
  public static ISLAff toISLAff(final String descriptor) {
    return ISLAff.buildFromString(ISLContext.getInstance(), descriptor);
  }
}
