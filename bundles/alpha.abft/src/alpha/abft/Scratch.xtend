package alpha.abft

import alpha.codegen.isl.PolynomialConverter
import alpha.loader.AlphaLoader
import alpha.model.AlphaExpression
import alpha.model.AlphaSystem
import alpha.model.AlphaVisitable
import alpha.model.ReduceExpression
import alpha.model.SystemBody
import alpha.model.Variable
import alpha.model.util.AShow
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial

import static extension alpha.codegen.ProgramPrinter.printExpr
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.transformation.Normalize

class Scratch {
	
	static boolean verbose = true
	
	def static void main(String[] args) {
		val root = AlphaLoader.loadAlpha('resources/scratch/star3d1r.alpha')
		val system = root.systems.get(0)
		system.pprint('input:')
		
		Normalize.apply(system)
		NormalizeReduction.apply(system)
		system.pprint('input:')
//		println(system.computeComplexity)
	}
	
	def static computeComplexity(AlphaSystem system) {
		
		val systemBody = system.systemBodies.get(0)
		
		val stencilVar = system.variables.findFirst[name == 'Y']
		
		val stencilVarPoints = systemBody.computeComplexity(stencilVar)
		
		val checksumPoints = systemBody.standardEquations
			.map[systemBody.computeComplexity(variable)]
			.reduce[p1,p2 | p1.add(p2)]
		val versionStr = 'v3'
		
		val stencilVarCard = PolynomialConverter.convert(stencilVarPoints).printExpr.toString
			.replaceAll('([-]*[0-9][0-9]*)', '((double)$1)')
		val checksumsCard = PolynomialConverter.convert(checksumPoints).printExpr.toString
			.replaceAll('([-]*[0-9][0-9]*)', '((double)$1)')
		
		'''
			#undef ceild
			#undef floord
			#define ceild(n,d)  (double)ceil(((double)(n))/((double)(d)))
			#define floord(n,d) (double)floor(((double)(n))/((double)(d)))
			
			double «stencilVar.name»_card = «stencilVarCard»;
			double total_card = «checksumsCard»;
			double expected_overhead = total_card / «stencilVar.name»_card;
			printf("«versionStr»(«stencilVar.name»,C,overhead): %0.0lf,%0.0lf,%0.2lf\n", «stencilVar.name»_card, total_card, expected_overhead);
			
			#undef ceild
			#undef floord
			#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
			#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
		'''
	}
	
	def static ISLPWQPolynomial computeComplexity(SystemBody body, Variable variable) {
		val eq = body.standardEquations.findFirst[e | e.variable == variable]
		computeComplexity(body, variable, eq.expr)
	}
	def static dispatch ISLPWQPolynomial computeComplexity(SystemBody body, Variable variable, ReduceExpression re) {
		computeComplexity(body, variable, re.body)
	}
	def static dispatch ISLPWQPolynomial computeComplexity(SystemBody body, Variable variable, AlphaExpression ae) {
		BarvinokBindings.card(ae.contextDomain)
	}
	
	def static pprint(AlphaVisitable av, String msg) {
		if (verbose) {
			println(msg)
			println(AShow.print(av))
		}
	}
}