package alpha.model.scheduler

import alpha.model.AlphaSystem
import java.util.Stack
import alpha.model.DependenceExpression
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.StandardEquation
import alpha.model.VariableExpression
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import alpha.model.util.ISLUtil
import fr.irisa.cairn.jnimap.isl.ISLDimType
import alpha.model.exception.CausalityViolationException

class ScheduleVerifier extends AbstractAlphaCompleteVisitor {
	var Scheduler scheduler
	var Stack<ISLMultiAff> dependenceMaffs
	var Stack<String> sourceNames
	var Stack<ISLSet> domains
	
	new(Scheduler scheduler) {
		this.scheduler = scheduler
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
		
		val ISLMultiAff dependenceTimestampMaff = ISLUtil.toMultiAff(scheduler.getScheduleMap(ve.variable.name).copy.clearInputTupleName)
		val ISLMultiAff readTimestampMaff = dependenceTimestampMaff.pullback(dependenceMaffs.peek.copy)
		var ISLMultiAff writeTimestampMaff = ISLUtil.toMultiAff(scheduler.getScheduleMap(sourceNames.peek).copy.clearInputTupleName)
		
		// Need to add a dimension to the write timestamp if in a reduction body
		if(writeTimestampMaff.dim(ISLDimType.isl_dim_in) != readTimestampMaff.dim(ISLDimType.isl_dim_in)) {
			writeTimestampMaff = writeTimestampMaff.addDims(
				ISLDimType.isl_dim_in, 
				readTimestampMaff.dim(ISLDimType.isl_dim_in) - writeTimestampMaff.dim(ISLDimType.isl_dim_in)
			)
		}
		
		verifyCausality(writeTimestampMaff, readTimestampMaff)
	}
	
	def protected void verifyCausality(ISLMultiAff writeTimestampMaff, ISLMultiAff readTimestampMaff) {
		val ISLSet domain = domains.peek.copy
		 
		val int timestampDims = writeTimestampMaff.getNbOutputs
		// Start with the empty set in the relevant space
		var coveredSet = ISLSet.buildEmpty(domain.getSpace.copy)
		for(var i = 0; i < timestampDims; i++) {
			// The points where causality holds at dimension i, plus the ones that are already covered
			val causalitySet = coveredSet.copy.union(
				ISLSet.buildGESet(writeTimestampMaff.getAff(i), readTimestampMaff.getAff(i))
			)
			
			if(!domain.isSubset(causalitySet)) {
				throw new CausalityViolationException(dependenceMaffs.peek, writeTimestampMaff, 
					readTimestampMaff, domain.copy.subtract(causalitySet.copy), i)
			}
			
			// We are no longer concerned with points where T_w_i > T_r_i (so they enter the covered set)
			coveredSet = coveredSet.union(
				ISLSet.buildGTSet(writeTimestampMaff.getAff(i), readTimestampMaff.getAff(i))
			)
		}
	}
	
	def static void verify(AlphaSystem sys, Scheduler scheduler) {
		var verifier = new ScheduleVerifier(scheduler)
		verifier.accept(sys)
	}
}