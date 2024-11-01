package alpha.model.prdg

import fr.irisa.cairn.jnimap.polylib.PolyLibMatrix
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron
import fr.irisa.cairn.jnimap.polylib.PolylibPrettyPrinter
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

import static alpha.model.util.AffineFunctionOperations.*
import static alpha.model.util.ISLUtil.*

class FeasibleSpace {
 	@Accessors(PUBLIC_GETTER)
	var HashMap<String, HashMap<String, String>> variables 
 	@Accessors(PUBLIC_GETTER)
 	var HashMap<String, List<String>> variableIndices
 	@Accessors(PUBLIC_GETTER)
 	var HashMap<String, Integer> indexMappings
 	@Accessors(PUBLIC_GETTER)
 	var HashMap<String, Pair<Pair<String, Pair<PolyLibPolyhedron, PolyLibMatrix>>, List<Pair<String, PolyLibMatrix>>>> mappings
 	var PolyLibPolyhedron space
 	//WE PUT PARAMS LAST IN THE POLYLIBMATRIX!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 	new(PRDG prdg) {
 		variables = newHashMap
		variableIndices = newHashMap
		indexMappings = newHashMap
		mappings = newHashMap

		var numberDimensions = prdg.nodes
			.filter[node | !(node.name.contains("_body") || node.name.contains("_result"))]
			.fold(0, [dims, node | dims + node.domain.copy.nbIndices + node.domain.copy.nbParams + 1])

		indexMappings = prdg.nodes
			.filter[node | !(node.name.contains("_body") || node.name.contains("_result"))]
			.map[node | new Pair(node.name, node.domain.copy.nbIndices + node.domain.copy.nbParams + 1)]
			.fold(new Pair(newHashMap(), 0), [map, dep | 
				map.key.put(dep.key, map.value); 
				new Pair(map.key, map.value + dep.value)
			]).key
		println("Number of dimensions: " + numberDimensions)
		println("Variables: " + indexMappings)
		space = PolyLibPolyhedron.buildUniversePolyedron(numberDimensions)
		println("Edges: " + prdg.edges)
		var dependences = prdg.edges.filter[edge | edge.source.name.contains("_body")]
			.flatMap[edge | Dependence.buildReductionDependence(edge, 
				prdg.edges.filter(x | x.dest.name == prdg.edges.findFirst[n | n.dest.name == edge.source.name].source.name).toList,
				prdg.edges.filter(x | x.dest.name == edge.source.name).toList)].toList
		
		dependences = prdg.edges.filter[edge | !isReductionEdge(edge)]
			.fold(dependences, [dependenceList, edge | dependenceList.add(Dependence.buildNormalDependence(edge)); dependenceList])

		println("Space Dims: ")
		space = dependences.fold(space, [intermediateSpace, dependence | 
			intermediateSpace.domainAddConstraints(dependence.buildConstraint(intermediateSpace, indexMappings), 10)
		])
		
		println("Self Dependences: " + dependences)
		space = space.simplify(10)
		println("Rays: ")
		println(PolylibPrettyPrinter.asString(space.builRaysVertices))
		space.print
 	}

	def private static isReductionEdge(PRDGEdge edge) {
		(edge.source.name.contains("_body") || edge.source.name.contains("_result")
			|| edge.dest.name.contains("_body") || edge.dest.name.contains("_result"))
	}
 	def PolyLibPolyhedron getSpace() {
 		space
 	}

}

class Dependence {
	val PolyLibMatrix sourceMapping
	@Accessors(PUBLIC_GETTER)
	val PolyLibMatrix destinationMapping
	@Accessors(PUBLIC_GETTER)
	val PolyLibMatrix domain
	@Accessors(PUBLIC_GETTER)
	var String source
	@Accessors(PUBLIC_GETTER)
	var String destination
	
	new(String src, String dest, PolyLibMatrix srcMapping, PolyLibMatrix destMapping, PolyLibMatrix dom) {
		source = src
		destination = dest
		sourceMapping = srcMapping
		destinationMapping = destMapping
		domain = dom
	}
	
	override toString() {
		source + " -> " + destination + "\nSource Mapping:\n" + sourceMapping + "\nDestination Mapping:\n" 
		+ destinationMapping + "\nOver Domain:\n" + domain + "\n"
	}
	
	def static Dependence buildNormalDependence(PRDGEdge edge) {
		println("Edge Function: " + edge.function.copy)
		var dom = edge.domain.copy
		dom = dom.moveIndicesToParameters
		var sourceMap = PolyLibMatrix.createFromLongMatrix(toMatrix(toMultiAff(edge.domain.toIdentityMap)).toArray)
		var domain = PolyLibMatrix.createFromLongMatrix(dom.basicSets.get(0).toPolyLibArray)
		var destMap = PolyLibMatrix.createFromLongMatrix(toMatrix(edge.function.copy).toArray)
		println("As PolyLib: \n" + destMap)
		new Dependence(edge.source.name, edge.dest.name, sourceMap, destMap, domain)
	}
	
	def static List<Dependence> buildReductionDependence(PRDGEdge sourceEdge, List<PRDGEdge> reductions, List<PRDGEdge> targets) {
		var dom = sourceEdge.domain.copy
		dom = dom.moveIndicesToParameters
		println("Domain: " + sourceEdge.domain.copy)
		println("Dom: " + dom)
		val domain = PolyLibMatrix.createFromLongMatrix(dom.copy.basicSets.get(0).toPolyLibArray)
		reductions.flatMap[edge | targets.filter[target | target.source == edge.dest]
					.map[target | new Dependence(sourceEdge.dest.name, edge.source.name, 
						PolyLibMatrix.createFromLongMatrix(toMatrix(edge.function.copy.pullback(target.function.copy)).toArray),
						PolyLibMatrix.createFromLongMatrix(toMatrix(sourceEdge.function.copy).toArray),
						domain)]].toList
	}
	
	def PolyLibMatrix buildConstraint(PolyLibPolyhedron space, HashMap<String, Integer> indexMappings) {
		println(this.source + " -> " + this.destination)
		println("Domain:\n" + domain)
		val rays = PolyLibPolyhedron.buildFromConstraints(domain, 10).builRaysVertices
		println("Rays:\n" + PolylibPrettyPrinter.asString(rays)) 
		var long[][] scheduleConstraint = (0..<rays.nbRows).fold(new ArrayList(),
			[constraintMatrix, row | 
				constraintMatrix.add(buildSingleConstraint(rays, row, space.dimension, indexMappings.get(source)));
				constraintMatrix.add(buildIntervariableConstraint(rays, row, space.dimension, indexMappings)); 
				constraintMatrix])

		println("Constraint: ")
		scheduleConstraint.forEach[row | row.forEach[item | print(item + " ")] println()]
		PolyLibMatrix.createFromLongMatrix(scheduleConstraint)
	}
	
	def long[] buildIntervariableConstraint(PolyLibMatrix rays, Integer row, Integer size, HashMap<String, Integer> indices) {
		var long[] output = new ArrayList(Collections.nCopies(size + 2, 0L))
		output.set(0, 1L)
		println("Row: " + row)
		println("Rays: \n" + rays)
		println("Source Map: \n" + sourceMapping)
		println("Dest Map: \n" + destinationMapping)
		
		output = (0..<sourceMapping.nbRows).fold(output, [current, index |
			var loc = indices.get(source) + index + 1
			current.set(loc, (0..<sourceMapping.nbColumns).fold(0L, [value, column |
				value + sourceMapping.getAt(index, column) * rays.getAt(row, column + 1)
			]))
			current
		])
		output.set(indices.get(source) + sourceMapping.nbRows + 1, rays.getAt(row, rays.nbColumns - 1))
		println("Indices: " + indices.get(destination))
		output = (0..<destinationMapping.nbRows).fold(output, [current, index |
			var loc = indices.get(destination) + index + 1
			current.set(loc, (0..<destinationMapping.nbColumns)
				.fold(current.get(loc), [value, column |
					value - destinationMapping.getAt(index, column) * rays.getAt(row, column + 1)
			]))
			current
		])
		var constLoc = indices.get(destination) + destinationMapping.nbRows + 1
		output.set(constLoc, output.get(constLoc) - rays.getAt(row, rays.nbColumns - 1))
		output.set(output.size - 1, -rays.getAt(row, rays.nbColumns - 1))
		println("Output: ")
		output.forEach[x | print(x + ", ")]
		println()
		output
	}
	
	def long[] buildSingleConstraint(PolyLibMatrix rays, Integer row, Integer size, Integer varOneIndex) {
		var long[] output = new ArrayList(Collections.nCopies(size + 2, 0L))
		output.set(0, 1L)
		println("SELF")
		println("Row: " + row)
		println("Rays: \n" + rays)
		println("Map: \n" + sourceMapping)
		println("columns: " + sourceMapping.nbRows)
		output = (0..<sourceMapping.nbRows).fold(output, [current, index |
			var loc = varOneIndex + index + 1
//			var loc = varOneIndex + sourceMapping.nbRows - index
			current.set(loc, (0..<sourceMapping.nbColumns).fold(0L, [value, column |
				value + sourceMapping.getAt(index, column) * rays.getAt(row, column + 1)
			]))
			current
		])
		output.set(varOneIndex + sourceMapping.nbRows + 1, rays.getAt(row, rays.nbColumns - 1))
		println("Output: ")
		output.forEach[x | print(x + ", ")]
		println()
		output
	}
}
