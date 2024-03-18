package alpha.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
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

  /**
   * Matches up the elements of the two iterables according to their index.
   * This is returned as an ArrayList of key->value pairs
   * where the keys are from the first iterable and values are from the second.
   * If they have differing lengths, the remaining elements from the longer
   * will be ignored.
   */
  public static <T1 extends Object, T2 extends Object> ArrayList<Pair<T1, T2>> zipWith(final Iterable<T1> first, final Iterable<T2> second) {
    final int elementCount = Math.min(IterableExtensions.size(first), IterableExtensions.size(second));
    final Function1<Integer, Pair<T1, T2>> _function = (Integer idx) -> {
      T1 _get = ((T1[])Conversions.unwrapArray(first, Object.class))[(idx).intValue()];
      T2 _get_1 = ((T2[])Conversions.unwrapArray(second, Object.class))[(idx).intValue()];
      return Pair.<T1, T2>of(_get, _get_1);
    };
    return CommonExtensions.<Pair<T1, T2>>toArrayList(IterableExtensions.<Integer, Pair<T1, T2>>map(new ExclusiveRange(0, elementCount, true), _function));
  }

  /**
   * Returns an iterable (not an ArrayList) of all permutations of the given elements,
   * where each permutation has exactly <code>count</code> elements.
   */
  public static <T extends Object> Iterable<ArrayList<T>> permutations(final Iterable<T> elements, final int count) {
    final int nbElements = IterableExtensions.size(elements);
    final int combinations = Double.valueOf(Math.pow(nbElements, count)).intValue();
    final Function1<Integer, ArrayList<T>> _function = (Integer value) -> {
      return CommonExtensions.<T>permutationToElements(elements, count, (value).intValue());
    };
    return IterableExtensions.<Integer, ArrayList<T>>map(new ExclusiveRange(0, combinations, true), _function);
  }

  private static <T extends Object> ArrayList<T> permutationToElements(final Iterable<T> elements, final int count, final int value) {
    final int nbElements = IterableExtensions.size(elements);
    final Function1<Integer, Integer> _function = (Integer listIdx) -> {
      return Integer.valueOf(Double.valueOf(Math.pow(nbElements, (listIdx).intValue())).intValue());
    };
    final Function1<Integer, Integer> _function_1 = (Integer divisor) -> {
      return Integer.valueOf(Integer.divideUnsigned(value, (divisor).intValue()));
    };
    final Function1<Integer, Integer> _function_2 = (Integer dividend) -> {
      return Integer.valueOf(Integer.remainderUnsigned((dividend).intValue(), nbElements));
    };
    final Function1<Integer, T> _function_3 = (Integer remainder) -> {
      return ((T[])Conversions.unwrapArray(elements, Object.class))[remainder.intValue()];
    };
    return CommonExtensions.<T>toArrayList(IterableExtensions.<Integer, T>map(IterableExtensions.<Integer, Integer>map(IterableExtensions.<Integer, Integer>map(IterableExtensions.<Integer, Integer>map(new ExclusiveRange(0, count, true), _function), _function_1), _function_2), _function_3));
  }
}
