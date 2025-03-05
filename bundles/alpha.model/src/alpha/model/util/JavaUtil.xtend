package alpha.model.util

import java.util.Set
import java.util.HashSet
import java.util.Collection

/**
 * A collection of useful methods that neither Java nor Xtend had the sense to implement
 */
class JavaUtil {
	static def <T> Set<Set<T>> powerSet(Collection<T> set) {
		if(set.empty)
			return #[#[].toSet].toSet
		
		val list = set.toList
		val t = list.get(0)
		val recurseSet = powerSet(list.subList(1, list.size).toSet)
		
		return (recurseSet.map[ Set<T> subset |
			(subset.toList + #[t]).toSet
		] + recurseSet).toSet
	}
}