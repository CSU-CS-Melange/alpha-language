package alpha.model.util;

import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * A collection of useful methods that neither Java nor Xtend had the sense to implement
 */
@SuppressWarnings("all")
public class JavaUtil {
  public static <T extends Object> Set<Set<T>> powerSet(final Collection<T> set) {
    boolean _isEmpty = set.isEmpty();
    if (_isEmpty) {
      Set<T> _set = IterableExtensions.<T>toSet(Collections.<T>unmodifiableList(CollectionLiterals.<T>newArrayList()));
      return IterableExtensions.<Set<T>>toSet(Collections.<Set<T>>unmodifiableList(CollectionLiterals.<Set<T>>newArrayList(_set)));
    }
    final List<T> list = IterableExtensions.<T>toList(set);
    final T t = list.get(0);
    final Set<Set<T>> recurseSet = JavaUtil.<T>powerSet(IterableExtensions.<T>toSet(list.subList(1, list.size())));
    final Function1<Set<T>, Set<T>> _function = (Set<T> subset) -> {
      List<T> _list = IterableExtensions.<T>toList(subset);
      return IterableExtensions.<T>toSet(Iterables.<T>concat(_list, Collections.<T>unmodifiableList(CollectionLiterals.<T>newArrayList(t))));
    };
    Iterable<Set<T>> _map = IterableExtensions.<Set<T>, Set<T>>map(recurseSet, _function);
    return IterableExtensions.<Set<T>>toSet(Iterables.<Set<T>>concat(_map, recurseSet));
  }
}
