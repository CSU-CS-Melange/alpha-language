package alpha.targetmapping.util

import fr.irisa.cairn.jnimap.isl.ISLIdentifierList
import fr.irisa.cairn.jnimap.isl.ISLIdentifier
import fr.irisa.cairn.jnimap.isl.ISLContext
import java.util.List

class ISLUtils {
	
	
	def static ISLIdentifierList toIdentifierList(List<String> iterators, ISLContext context) {
		var ret = ISLIdentifierList.build(context, iterators.size)
		for (i : 0..<iterators.size) {
			ret = ret.insert(i, ISLIdentifier.alloc(context, iterators.get(i)))
		}
		ret
	}
}