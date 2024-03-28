package alpha.targetmapping.scratch

import fr.irisa.cairn.jnimap.isl.ISLSet
import alpha.model.Variable
import java.util.List
import java.util.ArrayList
import java.util.Map
import java.util.HashMap
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLSchedule

class PRDG {
	
	List<PRDGEdge> edges
	Map<Variable, PRDGNode> nodes
	
	new() {
		edges = new ArrayList<PRDGEdge>
		nodes = new HashMap<Variable, PRDGNode>
	}
	
	def addNode(Variable variable) {
		addNode(new PRDGNode(variable))
	}
	
	def addNode(PRDGNode node) {
		nodes.put(node.variable, node)
	}
	
	def getNode(Variable variable) {
		nodes.get(variable)
	}
	
	def addEdge(PRDGEdge edge) {
		edges.add(edge)
	}
	
	def ISLUnionSet getDomain() {
		nodes.values.map[it.domain.copy.toUnionSet].reduce(v0,v1|v0.copy.union(v1))
	}
	
	def ISLUnionMap getDependencies() {
		edges.map[it.map.copy.toUnionMap].reduce(v0,v1|v0.copy.union(v1))
	}
	
	def ISLSchedule computeSchedule() {
		computeSchedule(0)
	}	
	
	def ISLSchedule computeSchedule(int algo) {
		ISLSchedule.computeSchedule(domain, dependencies, algo)
	}
	
	
	
	def String prettyPrint() '''
		nodes = [
		  «nodes.values.join(',\n')»
		],
		edges = [
		  «edges.join(',\n')»
		]
	'''
}

class PRDGNode {
	Variable variable
	ISLSet domain
	
	new(Variable v) {
		variable = v
		domain = v.domain.getBasicSets.map[it.setTupleName(v.name).toSet].reduce(d0,d1|d0.copy.union(d1))
	}
	
	def Variable getVariable() {
		variable
	}
	
	def ISLSet getDomain() {
		domain
	}
	
	override String toString() {
		'''«variable.name»: «domain»'''
	}
}

class PRDGEdge {
	PRDGNode consumer
	PRDGNode producer
	ISLMap map
	
	new(PRDGNode c, PRDGNode p, ISLMultiAff ma, ISLSet d) {
		consumer = c
		producer = p
		val domain = d.getBasicSets.map[it.setTupleName(c.variable.name).toSet].reduce(d0,d1|d0.copy.union(d1))
		map = ma.toBasicMap
				.setTupleName(ISLDimType.isl_dim_in, c.variable.name)
				.setTupleName(ISLDimType.isl_dim_out, p.variable.name)
				.toMap
				.intersectDomain(domain)
	}
	
	def PRDGNode getConsumer() {
		consumer
	}
	
	def PRDGNode getProducer() {
		producer
	}
	
	def ISLMap getMap() {
		map
	}
	
	override String toString() {
		'''«map»'''
	}
}