package alpha.model.tests.transformation.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaModelLoader
import alpha.model.AlphaSystem
import alpha.model.AlphaVisitable
import alpha.model.DependenceExpression
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.UniquenessAndCompletenessCheck
import alpha.model.Variable
import alpha.model.VariableExpression
import alpha.model.transformation.Normalize
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.transformation.reduction.SerializeReduction
import alpha.model.util.AbstractAlphaCompleteVisitor
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import java.util.Set
import org.junit.Test

import static org.junit.Assert.*

import static extension alpha.model.util.AlphaUtil.*

class SerializeReductionTest {
	protected static class ReadSetComputer extends AbstractAlphaCompleteVisitor {
		protected var ISLUnionSet readUSet
		
		new() {
			readUSet = null
		}
		
		def static compute(AlphaVisitable an) {
			val comp = new ReadSetComputer()
			an.accept(comp)
			return comp.readUSet
		}
		
		override outDependenceExpression(DependenceExpression de) {
			val readSet = de.getContextDomain.copy.apply(de.getFunction.copy.toMap)
				.setTupleName((de.expr as VariableExpression).variable.name).toUnionSet
				
			if(readUSet === null) readUSet = readSet
			else readUSet = readUSet.union(readSet)
		}
	}
	
	protected static class ReductionBoundednessChecker extends AbstractAlphaCompleteVisitor {
		def static apply(AlphaVisitable an) {
			val comp = new ReductionBoundednessChecker()
			an.accept(comp)
		}
		
		override outReduceExpression(ReduceExpression are) {
			val domain = are.body.getContextDomain.copy()
			(0..domain.dim(ISLDimType.isl_dim_set)-1).forEach[ i | 
				assertTrue(domain.hasUpperBound(ISLDimType.isl_dim_set, i))
				assertTrue(domain.hasLowerBound(ISLDimType.isl_dim_set, i))
			]
		}
	}
	
	var AlphaSystem sys
	
	def void readFileAndSerialize(String file) {
		sys = AlphaModelLoader.loadModel(file).systems.get(0)
		val Set<String> variableNames = sys.getVariables.map[variable | variable.name].toSet
		
		NormalizeReduction.apply(sys)
		val Iterable<Variable> reductionVariables = sys.getVariables.filter[variable | !variableNames.contains(variable.name)]
		
		for(v : reductionVariables) {
			autoSerializeAndAssert(v.getStandardEquation.getExpr as AbstractReduceExpression)
		}
	}
	
	def void autoSerializeAndAssert(AbstractReduceExpression are) {
		val ISLSet originalDomain = are.body.getContextDomain.copy 
		val ISLUnionSet originalReadSet = ReadSetComputer.compute(are.getContainerEquation)
		
		val Variable writeVar = (are.getContainerEquation as StandardEquation).variable
		val Set<String> variableNames = sys.getVariables.map[variable | variable.name].toSet
		
		SerializeReduction.applyAuto(are)
		Normalize.apply(sys)
		
		val Variable newVariable = sys.getVariables.findFirst[variable | !variableNames.contains(variable.name)]
		
		val StandardEquation writeSE = writeVar.getStandardEquation
		val StandardEquation newSE = newVariable.getStandardEquation
		
		assertReductionDomainUnchanged(originalDomain, newSE)
		assertReadSetUnchanged(originalReadSet, newSE)
		assertAllPointsReduced(originalDomain, newSE, writeSE)
		assertAllReductionsBounded
		assertUniqueAndComplete
	}
	
	def assertReductionDomainUnchanged(ISLSet originalDomain, StandardEquation newSE) {
		assertTrue(originalDomain.copy.isPlainEqual(newSE.expr.contextDomain.copy))
	}
	
	def assertReadSetUnchanged(ISLUnionSet originalReadSet, StandardEquation newSE) {
		val readUSet = ReadSetComputer.compute(newSE).coalesce
		
		assertTrue(originalReadSet.copy.isSubset(readUSet.copy))
		readUSet.copy.getSets.filter[ set | set.getTupleName != newSE.variable.name].forEach[ set | 
			assertTrue(set.copy.toUnionSet.isSubset(originalReadSet.copy))
		]
	}
	
	def assertAllPointsReduced(ISLSet originalDomain, StandardEquation newSE, StandardEquation writeSE) {
		val resultSet = ReadSetComputer.compute(writeSE).getSets
			.findFirst[ set | set.getTupleName == newSE.variable.name]
			
		val nonResultSet = ReadSetComputer.compute(newSE).getSets
			.findFirst[ set | set.getTupleName == newSE.variable.name]
			
		val totalSet = resultSet.union(nonResultSet)
		assertTrue(originalDomain.isPlainEqual(totalSet.coalesce.clearTupleName))
	}
	
	def assertAllReductionsBounded() {
		ReductionBoundednessChecker.apply(sys)
	}
	
	def assertUniqueAndComplete() {
		assertTrue(UniquenessAndCompletenessCheck.check(sys.getContainerRoot).isEmpty)
	}
	
	def StandardEquation getStandardEquation(Variable v) {
		sys.systemBodies.map[ body | body.getStandardEquation(v)]
			.findFirst[ se | se !== null]
	}
	
	@Test
	def void testOSP() {
		readFileAndSerialize("resources/src-valid/kernels/osp.alpha")
	} 
	
	@Test
	def void testBPMax() {
		readFileAndSerialize("resources/src-valid/kernels/bpmax.alpha")
	}
	
	@Test
	def void testCholesky() {
		readFileAndSerialize("resources/src-valid/kernels/cholesky.alpha")
	}
}