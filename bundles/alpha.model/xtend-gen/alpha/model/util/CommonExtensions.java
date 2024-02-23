package alpha.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class CommonExtensions {
  /**
   * Wraps a collection into an array list so its values are only computed once.
   * Intended for use with Xtend's iterable/map extensions, which can recompute values
   * each time they're accessed due to lazy evaluation.
   */
  public static <T extends Object> ArrayList<T> toArrayList(final Collection<T> collection) {
    return new ArrayList<T>(collection);
  }

  /**
   * Wraps an iterable into an array list so its values are only computed once.
   * Intended for use with Xtend's iterable/map extensions, which can recompute values
   * each time they're accessed due to lazy evaluation.
   */
  public static <T extends Object> ArrayList<T> toArrayList(final Iterable<T> iterable) {
    List<T> _list = IterableExtensions.<T>toList(iterable);
    return new ArrayList<T>(_list);
  }

  /**
   * Wraps a map into a hash map so its values are only computed once.
   * Intended for use with Xtend's iterable/map extensions, which can recompute values
   * each time they're accessed due to lazy evaluation.
   */
  public static <K extends Object, V extends Object> HashMap<K, V> toHashMap(final Map<K, V> map) {
    return new HashMap<K, V>(map);
  }
}
