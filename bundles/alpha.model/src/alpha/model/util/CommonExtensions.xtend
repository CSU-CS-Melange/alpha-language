package alpha.model.util

import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.List
import java.util.Map

class CommonExtensions {
	/**
	 * Wraps a collection into an array list so its values are only computed once.
	 * Intended for use with Xtend's iterable/map extensions, which can recompute values
	 * each time they're accessed due to lazy evaluation.
	 */
	def static <T> toArrayList(Collection<T> collection) {
		return new ArrayList<T>(collection)
	}
	
	/**
	 * Wraps an iterable into an array list so its values are only computed once.
	 * Intended for use with Xtend's iterable/map extensions, which can recompute values
	 * each time they're accessed due to lazy evaluation.
	 */
	def static <T> toArrayList(Iterable<T> iterable) {
		return new ArrayList<T>(iterable.toList)
	}
	
	/**
	 * Wraps a map into a hash map so its values are only computed once.
	 * Intended for use with Xtend's iterable/map extensions, which can recompute values
	 * each time they're accessed due to lazy evaluation.
	 */
	def static <K,V> toHashMap(Map<K,V> map) {
		return new HashMap<K,V>(map)
	}
	
	/**
	 * Maps a list of items by their index.
	 */
	def static <T> toIndexHashMap(List<T> items) {
		val map = new HashMap<Integer, T>(items.size)
		for (indexedItem : items.indexed) {
			map.put(indexedItem.key, indexedItem.value)
		}
		return map
	}
	
	/**
	 * Splits a list of items into two lists based on a given predicate.
	 * The lists are returned as a key->value pair,
	 * where the key is the list of items where the predicate returned <code>true</code>
	 * and the value is the list of items where the predicate returned <code>false</code>.
	 */
	def static <T> splitBy(List<T> items, (T)=>boolean predicate) {
		val isTrue = items.filter(predicate).toArrayList
		val isFalse = items.reject[item | isTrue.contains(item)].toArrayList
		return isTrue -> isFalse
	}
}