package alpha.model.prdg

import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.AlphaSystem
import alpha.model.DependenceExpression

class PRDGGenerator extends AbstractAlphaCompleteVisitor {
	
	static def void apply(AlphaSystem system) {
		val prdg = new PRDGGenerator()
		system.accept(prdg)
	}
	
	override void visitDependenceExpression(DependenceExpression de) {
		de.getFunction()
	}
	
}