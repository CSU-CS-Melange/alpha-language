package alpha.codegen.isl

import fr.irisa.cairn.jnimap.isl.ISLASTBuild
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLIdentifier
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet

import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension fr.irisa.cairn.jnimap.isl.ISLMultiAff.buildIdentity

/** Generates the loop statements that iterate through the points in a given domain. */
class LoopGenerator {
	/**
	 * Generates the loop statements that iterate through the points in the given domain
	 * in lexicographic order (i.e., scheduled by the identity function).
	 */
	static def generateLoops(String macroName, ISLSet domain) {
		val identity = domain.space
			.addDims(ISLDimType.isl_dim_in, domain.nbIndices)
			.buildIdentity
			.toMap
		
		return generateLoops(macroName, domain, identity)
	}
	
	/**
	 * Generates the loop statements that iterate through the points in the given domain
	 * according to the timestamps indicated by the given map.
	 */
	static def generateLoops(String macroName, ISLSet domain, ISLMap timestamps) {
		// The context needed by the AST builder is simply the parameter domain.
		val context = domain.copy.params
		
		// The schedule is the timestamp function intersected with the domain,
		// labeled with whatever macro we want to use, and converted into a union map.
		val schedule = timestamps.copy
			.intersectDomain(domain.copy)
			.setTupleName(ISLDimType.isl_dim_in, macroName)
			.toUnionMap
			
		// Use the index names from the domain as the loop variables. 
		val ids = domain.indexNames.map[ISLIdentifier.alloc(ISLContext.instance, it)].toArrayList
		val idList = ids.fold(ISLIdentifierList.build(ISLContext.instance, 0), [list, id | list.add(id)])
		
		return ISLASTBuild
			.buildFromContext(context)
			.setIterators(idList)
			.generate(schedule)
	}
}