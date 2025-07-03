package alpha.model.scheduler

import alpha.model.AlphaSystem
import java.util.Stack
import alpha.model.DependenceExpression
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.StandardEquation
import alpha.model.VariableExpression
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import static extension alpha.model.util.ISLUtil.*
import fr.irisa.cairn.jnimap.isl.ISLDimType
import alpha.model.exception.CausalityViolationException
import alpha.model.util.ISLUtil.Dims
import fr.irisa.cairn.jnimap.isl.ISLMap

class ScheduleVerifier extends AbstractAlphaCompleteVisitor {
	var Iterable<ISLMap> maps
	var Stack<ISLMultiAff> dependenceMaffs
	var Stack<String> sourceNames
	var Stack<ISLSet> domains
	
	new(Scheduler scheduler) {
		maps = scheduler.getMaps.maps
		dependenceMaffs = new Stack()
		sourceNames = new Stack()
		domains = new Stack()
	}
	
	new(Iterable<ISLMap> maps) {
		this.maps = maps
		dependenceMaffs = new Stack()
		sourceNames = new Stack()
		domains = new Stack()
	}
	
	override void inStandardEquation(StandardEquation standardEquation) {	
		this.dependenceMaffs.push(ISLMultiAff.buildIdentity(standardEquation.variable.domain.copy.identity.space))
		this.domains.push(standardEquation.variable.domain.copy)
		this.sourceNames.push(standardEquation.variable.name)
	}

	override void outStandardEquation(StandardEquation standardEquation) {
		this.domains.pop
		this.sourceNames.pop
	}
	
	override void inDependenceExpression(DependenceExpression dependenceExpression) {
		dependenceMaffs.push(dependenceExpression.function.copy)
		domains.push(dependenceExpression.contextDomain.copy)
	}
	
	override void outDependenceExpression(DependenceExpression dependenceExpression) {
		dependenceMaffs.pop
		domains.pop
	}
	
	override void visitVariableExpression(VariableExpression ve) {
		if(ve.variable.isInput) return;
		
		val ISLMultiAff dependenceTS = getMaff(ve.variable.name)
		val ISLMultiAff readTS = dependenceTS.pullback(dependenceMaffs.peek.copy)
		var ISLMultiAff writeTS = getMaff(sourceNames.peek)
		
		// Need to add a dimension to the write timestamp if in a reduction body
		if(writeTS.dim(Dims.IN) != readTS.dim(ISLDimType.isl_dim_in)) {
			val extraDims = readTS.dim(Dims.IN) - writeTS.dim(Dims.IN)
			writeTS = writeTS.addDims(Dims.IN, extraDims)
		}
		
		verifyCausality(writeTS, readTS)
	}
	
	def protected void verifyCausality(ISLMultiAff writeTS, ISLMultiAff readTS) {
		val ISLSet domain = domains.peek.copy
		 
		val int TSDims = writeTS.getNbOutputs
		// Start with the empty set in the relevant space
		var coveredSet = ISLSet.buildEmpty(domain.getSpace.copy)
		for(var i = 0; i < TSDims; i++) {
			// The points where causality holds at dimension i, plus the ones that are already covered
			val causalitySet = coveredSet.copy.union(
				ISLSet.buildGESet(writeTS.getAff(i), readTS.getAff(i))
			)
			
			if(!domain.isSubset(causalitySet)) {
				throw new CausalityViolationException(dependenceMaffs.peek.copy.toMap, writeTS, 
					readTS, domain.copy.subtract(causalitySet.copy), i)
			}
			
			// We are no longer concerned with points where T_w_i > T_r_i (so they enter the covered set)
			coveredSet = coveredSet.union(
				ISLSet.buildGTSet(writeTS.getAff(i), readTS.getAff(i))
			)
		}
		
		if(!domain.isSubset(coveredSet)) {
			throw new CausalityViolationException(dependenceMaffs.peek.copy.toMap, writeTS, 
				readTS, domain.copy.subtract(coveredSet.copy), TSDims-1)
		}
	}
	
	def static void verify(AlphaSystem sys, Scheduler scheduler) {
		var verifier = new ScheduleVerifier(scheduler)
		verifier.accept(sys)
	}
	
	def protected ISLMultiAff getMaff(String name) {
		return maps.findFirst[inputTupleName == name]
			.copy.clearInputTupleName.toMultiAff
		
	}
}