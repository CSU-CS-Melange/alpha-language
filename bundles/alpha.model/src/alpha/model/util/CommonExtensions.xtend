package alpha.model.util

import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.Map
import java.util.List

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
	
	/**
	 * Matches up the elements of the two iterables according to their index.
	 * This is returned as an ArrayList of key->value pairs
	 * where the keys are from the first iterable and values are from the second.
	 * If they have differing lengths, the remaining elements from the longer
	 * will be ignored.
	 */
	def static <T1, T2> zipWith(Iterable<T1> first, Iterable<T2> second) {
		val elementCount = Math.min(first.size, second.size)
		return (0 ..< elementCount)
			.map[idx | first.get(idx) -> second.get(idx)]
			.toArrayList
	}
	
	/**
	 * Returns an iterable (not an ArrayList) of all permutations of the given elements,
	 * where each permutation has exactly <code>count</code> elements.
	 */
	def static <T> permutations(Iterable<T> elements, int count) {
		val nbElements = elements.size
		val combinations = Math.pow(nbElements, count).intValue
		return (0 ..< combinations).map[value | permutationToElements(elements, count, value)]
	}
	
	private static def <T> permutationToElements(Iterable<T> elements, int count, int value) {
		val nbElements = elements.size
		return (0 ..< count)
			.map[listIdx | Math.pow(nbElements, listIdx).intValue]
			.map[divisor | Integer.divideUnsigned(value, divisor)]
			.map[dividend | Integer.remainderUnsigned(dividend, nbElements)]
			.map[remainder | elements.get(remainder.intValue)]
			.toArrayList
	}
	
	/**
	 * Returns the dot product between two arrays of the same size
	 */
	def static dot(long[] v1, long[] v2) {
		if (v1.size != v2.size)
			throw new Exception('dot product operands must be of same size')
		var sum = 0l
		for (i : 0..<v1.size) {
			sum += v1.get(i) * v2.get(i)
		}
		return sum
	}
}