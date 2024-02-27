package alpha.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

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

  /**
   * Maps a list of items by their index.
   */
  public static <T extends Object> HashMap<Integer, T> toIndexHashMap(final List<T> items) {
    int _size = items.size();
    final HashMap<Integer, T> map = new HashMap<Integer, T>(_size);
    Iterable<Pair<Integer, T>> _indexed = IterableExtensions.<T>indexed(items);
    for (final Pair<Integer, T> indexedItem : _indexed) {
      map.put(indexedItem.getKey(), indexedItem.getValue());
    }
    return map;
  }

  /**
   * Splits a list of items into two lists based on a given predicate.
   * The lists are returned as a key->value pair,
   * where the key is the list of items where the predicate returned <code>true</code>
   * and the value is the list of items where the predicate returned <code>false</code>.
   */
  public static <T extends Object> Pair<ArrayList<T>, ArrayList<T>> splitBy(final List<T> items, final Function1<? super T, ? extends Boolean> predicate) {
    final ArrayList<T> isTrue = CommonExtensions.<T>toArrayList(IterableExtensions.<T>filter(items, ((Function1<? super T, Boolean>)predicate)));
    final Function1<T, Boolean> _function = (T item) -> {
      return Boolean.valueOf(isTrue.contains(item));
    };
    final ArrayList<T> isFalse = CommonExtensions.<T>toArrayList(IterableExtensions.<T>reject(items, _function));
    return Pair.<ArrayList<T>, ArrayList<T>>of(isTrue, isFalse);
  }
}
