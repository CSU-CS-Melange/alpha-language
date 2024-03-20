package alpha.model.util

import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
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
}