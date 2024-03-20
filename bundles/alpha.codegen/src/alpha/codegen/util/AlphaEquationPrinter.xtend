package alpha.codegen.util

import alpha.codegen.Program
import alpha.model.AlphaExpression
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryExpression
import alpha.model.BooleanExpression
import alpha.model.CaseExpression
import alpha.model.ConstantExpression
import alpha.model.ConvolutionExpression
import alpha.model.DependenceExpression
import alpha.model.FuzzyDependenceExpression
import alpha.model.FuzzyIndexExpression
import alpha.model.FuzzyReduceExpression
import alpha.model.IfExpression
import alpha.model.IndexExpression
import alpha.model.IntegerExpression
import alpha.model.MultiArgExpression
import alpha.model.PolynomialIndexExpression
import alpha.model.RealExpression
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.SelectExpression
import alpha.model.StandardEquation
import alpha.model.UnaryExpression
import alpha.model.VariableExpression
import alpha.model.util.ModelSwitch

import static extension alpha.codegen.factory.Factory.*
import static extension alpha.codegen.util.AlphaOp.*
import static extension alpha.codegen.util.CodegenUtil.*
import static extension alpha.codegen.util.ISLPrintingUtils.*
import alpha.codegen.BaseVariable
import java.util.ArrayList
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLASTBuild
import fr.irisa.cairn.jnimap.isl.ISLASTNode
import alpha.codegen.DataType

/**
 * Prints an Alpha expression as C code, intended to be used by the
 * pretty printers in alpha.codegen.show. Each alpha.model.Variable is
 * associated with an alpha.codegen.ArrayVariable in the program and
 * the codegen variables must be used at VariableExpression nodes.
 * Assumes all expressions have been normalized.
 * 
 *  @author lnarmour
 * 
 */
class AlphaEquationPrinter extends ModelSwitch<CharSequence> {
	
	Program program
	String lhs
	
	static def printStandardEquation(StandardEquation equation, Program program) {
		val show = new AlphaEquationPrinter(equation, program);
		show.doSwitch(equation).toString()
	}
	
	static def printExpression(AlphaExpression expression, Program program) {
		val show = new AlphaEquationPrinter(program)
		show.doSwitch(expression).toString()
	}
	
	protected new(Program program) {
		lhs = null
		this.program = program
	}
	
	new(StandardEquation equ, Program program) {
		lhs = program.getGlobalVariable(equ.variable).identityAccess
		this.program = program
	}
	
	def static String fault(String msg) {
		throw new Exception('''«msg» CString expression printer not yet supported''')
	}
	
	def static String fault(AlphaExpression ae) {
		fault(ae.class.name)
	}
	
	def caseStandardEquation(StandardEquation se) {
		seRules(se, se.expr)
	}
	
	def dispatch seRules(StandardEquation se, CaseExpression ce) {
		'''«ce.doSwitch»'''
	}
	
	def dispatch seRules(StandardEquation se, RestrictExpression re) '''
		if («re.restrictDomain.indexConstraintsToConditionals») {
		  «lhs» =  «re.expr.doSwitch»;
		}'''
	
	def dispatch seRules(StandardEquation se, AlphaExpression ae) {
		'''«lhs» = «ae.doSwitch»''';
	}
	

	def caseCaseExpression(CaseExpression ce) {
		'''«ce.exprs.map[expr | ce.ceRules(expr)].join(' else ')»'''
	}
	
	def dispatch ceRules(CaseExpression ce, RestrictExpression re) '''
		if («re.restrictDomain.indexConstraintsToConditionals») {
		  «lhs» = «re.expr.doSwitch»;
		}'''
	
	
	def dispatch ceRules(CaseExpression ce, AutoRestrictExpression re) '''
		if («re.inferredDomain.indexConstraintsToConditionals») {
		  «lhs» = «re.expr.doSwitch»;
		}'''
	
	def dispatch ceRules(CaseExpression ce, AlphaExpression ae) '''
		{
		  «lhs» = «ae.doSwitch»;
		}'''
		
	def caseIfExpression(IfExpression ie) {
		fault(ie)
	}
	
	def caseDependenceExpression(DependenceExpression de) {
		depExprRules(de, de.expr)
	}
	
	def dispatch depExprRules(DependenceExpression de, ConstantExpression ce) {
		'''«de.expr.doSwitch»«de.function.toCString»'''
	}
	
	def dispatch depExprRules(DependenceExpression de, VariableExpression ve) {
		if (ve.variable.isOutput)
			'''«de.expr.doSwitch»«de.function.moveParamsToArgs.toCString»'''
		else
			'''«de.expr.doSwitch»«de.function.toCString»'''
	}
	
	def dispatch depExprRules(DependenceExpression de, AlphaExpression ae) {
		fault(de)
	}
	
	def caseFuzzyDependenceExpression(FuzzyDependenceExpression fde) {
		fault(fde)
	}
	
	def caseReduceExpression(ReduceExpression re) {
		// Emit a call to the function.
		val reduceFunctionName = program.reduceFunctions.get(re).name
		val arguments = #[re.contextDomain.paramNames, re.contextDomain.indexNames]
			.flatten
			.join(',')
		return '''«reduceFunctionName»(«arguments»);'''
	}
	
	def caseRestrictExpression(RestrictExpression re) {
		'''«re.expr.doSwitch»'''
	}
	
	def caseFuzzyReduceExpression(FuzzyReduceExpression fre) {
		fault(fre)
	}
	
	def caseConvolutionExpression(ConvolutionExpression ce) {
		fault(ce)
	}
	
	def caseUnaryExpression(UnaryExpression ue) {
		'''(«ue.cOperator»«ue.expr.doSwitch»)'''
	}
	
	def caseBinaryExpression(BinaryExpression be) {
		'''(«be.left.doSwitch») «be.cOperator» («be.right.doSwitch»)'''
	}
	
	def caseMultiArgExpression(MultiArgExpression mae) {
		'''(«mae.exprs.map['''(«doSwitch»)'''].join(mae.cOperator.toString)»)'''
	}
	
	def caseSelectExpression(SelectExpression se) {
		fault(se)
	}
	
	def caseIndexExpression(IndexExpression ie) {
		fault(ie)
	}
	
	def casePolynomialIndexExpression(PolynomialIndexExpression pie) {
		fault(pie)
	}
	
	def caseFuzzyIndexExpression(FuzzyIndexExpression fie) {
		fault(fie)
	}
	
	def caseVariableExpression(VariableExpression ve) {
		'''«program.getGlobalVariable(ve.variable).readName»'''
	}
	
	def caseIntegerExpression(IntegerExpression ie) {
		'''«ie.value»'''
	}
	
	def caseRealExpression(RealExpression re) {
		'''«re.value»'''
	}
	
	def caseBooleanExpression(BooleanExpression be) {
		'''«if (be.value) 1 else 0»'''
	}
	

	
}