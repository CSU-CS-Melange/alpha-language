package alpha.abft.codegen.util

import alpha.model.AlphaSystem
import alpha.model.Variable
import alpha.model.util.AlphaUtil
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.HashMap
import java.util.List

import static extension alpha.model.util.ISLUtil.toISLMap

class MemoryMap {
	
	val AlphaSystem system
	
	val HashMap<String, String> memoryMapNames = newHashMap
	
	val HashMap<String, ISLMap> memoryMap = newHashMap
	
	val HashMap<String, ISLSet> variableDomains = newHashMap
	
	val HashMap<String, List<String>> mappedIndexNames = newHashMap
	
	
	def uniqueTargets() {
		val processed = newHashSet
		val targets = newLinkedList
		val x = memoryMapNames.entrySet.toList.toString
		system.variables.forEach[v | 
			val name = v.name
			val mappedName = getName(v.name)
			if (!(processed.contains(mappedName))) {
				processed.add(mappedName)
				targets.add(name)
			}
		]
		targets.map[t | getName(t)->getRange(t)]
	}
	
	new(AlphaSystem system) {
		this.system = system
	}
	
	
	def String getName(Variable variable) {
		getName(variable.name)
	}
	def String getName(String name) {
		memoryMapNames.get(name) ?: name
	}
	
	def ISLMap getMap(Variable variable) {
		getMap(variable.name)
	}
	def ISLMap getMap(String name) {
		memoryMap.get(name)?.copy ?: system.variables.findFirst[v | v.name == name]?.domain.copy.toIdentityMap
	}
	
	def ISLSet getDomain(Variable variable) {
		getDomain(variable.name)
	}
	def ISLSet getDomain(String name) {
		variableDomains.get(name)
	}
	
	def ISLSet getRange(Variable variable) {
		getRange(variable.name)
	}
	def ISLSet getRange(String name) {
		val domain = getDomain(name)
		if (domain === null)
			return system.variables.findFirst[v | v.name == name]?.domain.copy
		val indexNames = mappedIndexNames.get(name)
		
		val mappedDomain = domain.copy.apply(getMap(name))
		
		AlphaUtil.renameIndices(mappedDomain, indexNames)
	}

	def String[] getIndexNames(Variable variable) {
		
	}

	def MemoryMap setMemoryMap(String name, String mappedName, String map, String[] indexNames) {
		val domain = system.variables.findFirst[v | v.name == name]?.domain
		setMemoryMap(name, mappedName, map.toISLMap, domain, indexNames)
	}
	def MemoryMap setMemoryMap(String name, String mappedName, ISLMap map, ISLSet domain, String[] indexNames) {
		memoryMapNames.put(name, mappedName)
		memoryMap.put(name, map)
		variableDomains.put(name, domain)
		mappedIndexNames.put(name, indexNames)
		return this
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}