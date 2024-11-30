package alpha.model.tests.scheduling

import alpha.model.prdg.PRDG
import alpha.model.AlphaModelLoader
import org.junit.Test
import static org.junit.Assert.*
import alpha.model.prdg.PRDGNode
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLContext
import alpha.model.prdg.PRDGEdge
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import alpha.model.prdg.PRDGGenerator
import java.util.List
import java.util.Set
import java.util.HashSet
import java.util.Arrays
import alpha.model.AlphaSystem
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.isl.ISLDimType
import java.util.HashMap
import java.util.ArrayList

class PRDGTest {
	/*Whether the dependence edge from a reduction's result variable to its body is flipped
	If 'true', the edge is flipped, to conform to old AlphaZ specifications
	If this is ever changed, this value can simply be set to false to maintain test correctness*/
	val boolean RESULT_BODY_EDGE_FLIPPED = true
	var PRDG prdg
	var AlphaSystem sys
	
	def readFile(String file) {
		var root = AlphaModelLoader.loadModel(file)
		sys = root.systems.get(0)
		prdg = PRDGGenerator.apply(sys)
	}
	
	def void assertEdgeDomsInVariable(Iterable<ISLSet> outgoingDoms, Variable variable) {
		outgoingDoms.forEach[dom | assertTrue(dom.copy.isSubset(variable.getDomain.copy))]
	}

	def void assertEdgeDomsPartitionVariables() {
		for(Variable variable : sys.getVariables) {
			val Iterable<PRDGEdge> outgoing = prdg.getEdges.filter[edge | edge.getSource.getName == variable.getName]
			val Iterable<ISLSet> outgoingDoms = outgoing.map[edge | edge.getDomain.copy]
			
			assertEdgeDomsInVariable(outgoingDoms, variable)
		}
	}
	
	def void assertEdgeRangesInVariables() {
		for(Variable variable : sys.getVariables) {
			val Iterable<PRDGEdge> incoming = prdg.getEdges.filter[edge | edge.getDest.getName == variable.getName]
			val Iterable<ISLSet> incomingDoms = incoming.map[edge | edge.getDomain.apply(edge.getFunction.toMap)]
			
			assertEdgeDomsInVariable(incomingDoms, variable)
		}
	}
	
	def void assertEdgeDimensionsCorrect() {
		for(PRDGEdge edge : prdg.getEdges) {
			var Variable sourceVar = sys.getVariable(edge.getSource.getName)
			var Variable destVar = sys.getVariable(edge.getDest.getName)
			
			
			if(edge.isReductionEdge && RESULT_BODY_EDGE_FLIPPED) {
				val Variable temp = sourceVar
				sourceVar = destVar
				destVar = temp
			}
				
			if(sourceVar !== null) {
				val int nSourceParams = sourceVar.getDomain.dim(ISLDimType.isl_dim_param)
				val int nSourceIdxs = sourceVar.getDomain.dim(ISLDimType.isl_dim_all) - nSourceParams
				val int nEdgeDomParams = edge.getDomain.dim(ISLDimType.isl_dim_param)
				val int nEdgeDomIdxs = edge.getDomain.dim(ISLDimType.isl_dim_all) - nEdgeDomParams
				val int nEdgeFunParams = edge.getFunction.dim(ISLDimType.isl_dim_param)
				val int nEdgeFunIn = edge.getFunction.dim(ISLDimType.isl_dim_in)
				
				assertEquals(nSourceParams, nEdgeDomParams) 
				assertEquals(nSourceIdxs, nEdgeDomIdxs) 
				assertEquals(nSourceParams, nEdgeFunParams)
				assertEquals(nSourceIdxs, nEdgeFunIn)
			}
			if(destVar !== null) {
				val int nDestParams = destVar.getDomain.dim(ISLDimType.isl_dim_param)
				val int nDestIdxs = destVar.getDomain.dim(ISLDimType.isl_dim_all) - nDestParams
				val int nEdgeFunParams = edge.getFunction.dim(ISLDimType.isl_dim_param)
				val int nEdgeFunOut = edge.getFunction.dim(ISLDimType.isl_dim_out)
				
				assertEquals(nDestParams, nEdgeFunParams)
				assertEquals(nDestIdxs, nEdgeFunOut)
				 
			}
		}
	}
	
	def void assertNodeDomainsCorrect() {
		for(PRDGNode node : prdg.getNodes) {
			var Variable variable = sys.getVariable(node.getName)
			if(variable !== null) {
				assertTrue(variable.getDomain.copy.isSubset(node.getDomain.copy))
				assertTrue(node.getDomain.copy.isSubset(variable.getDomain.copy))
			}
		}
	}
	
	def void assertNodesComplete() {
		for(Variable variable : sys.getVariables()) {
			if(!variable.isInput) {
				assertFalse(prdg.getNodes.filter[node | node.name == variable.name].isEmpty)
			}
		}
	}
	
	def void assertPRDGValid() {
		/*
		 * Can't validate that edge functions match dependence functions without a visitor
		 * Which would be redundant, since that's how they're generated
		 * So that goes unchecked
		 */
		assertEdgeDimensionsCorrect
		assertEdgeDomsPartitionVariables
		assertEdgeRangesInVariables
		assertNodeDomainsCorrect
		assertNodesComplete
	}
	
	@Test
	def void testDot() {
		readFile("resources/src-valid/basic/matmult.unit/dot.alpha")
		assertPRDGValid
	} 
	
	@Test
	def void testBPMax() {
		readFile("resources/src-valid/kernels/bpmax.alpha")
		assertPRDGValid
	}
	
	@Test
	def void testCholesky() {
		readFile("resources/src-valid/kernels/cholesky.alpha")
		assertPRDGValid
	}
}