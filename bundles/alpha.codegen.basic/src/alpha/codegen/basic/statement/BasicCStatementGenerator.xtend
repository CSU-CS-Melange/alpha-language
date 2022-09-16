package alpha.codegen.basic.statement

import alpha.codegen.basic.targetMapping.BasicAffineSchedulePiece
import alpha.codegen.basic.targetMapping.BasicTargetMapping
import alpha.model.AbstractReduceExpression
import alpha.model.AlphaExpression
import alpha.model.ArgReduceExpression
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryExpression
import alpha.model.BooleanExpression
import alpha.model.CaseExpression
import alpha.model.ConstantExpression
import alpha.model.DependenceExpression
import alpha.model.ExternalArgReduceExpression
import alpha.model.ExternalMultiArgExpression
import alpha.model.ExternalReduceExpression
import alpha.model.FuzzyDependenceExpression
import alpha.model.IfExpression
import alpha.model.IndexExpression
import alpha.model.IntegerExpression
import alpha.model.MultiArgExpression
import alpha.model.REDUCTION_OP
import alpha.model.RealExpression
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.UnaryExpression
import alpha.model.Variable
import alpha.model.VariableExpression
import alpha.model.util.AlphaPrintingUtil
import alpha.model.util.ModelSwitch
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.HashMap
import java.util.Map

/**
 * Basic statement generator for C.
 * 
 * This class is far from finished. The code was mostly copied from
 * Show implementation, and minimal tweaks were made to handle simple
 * cases to illustrate the flow.
 * 
 * The role of statement generators is to convert Alpha expressions
 * to backend code (C in this class). A few things to be careful are:
 *  - differences in how operators are implemented; some Alpha OPs may require libraries in C
 *  - correspondence between loop iterators and generated code
 *  - Alpha specific expressions like reductions
 * None of the above are currently handled properly in this implementation.
 */
class BasicCStatementGenerator extends ModelSwitch<CharSequence> {

	protected BasicTargetMapping targetMapping;
	protected ISLSet parameterContext = null;
	protected ISLSet statementContext = null;
	
	new(BasicTargetMapping targetMapping) {
		this.targetMapping = targetMapping
		this.parameterContext = targetMapping.systemBody.parameterDomain
	}
	
	static protected def memAccess(Variable v) {
		'''«v.name»_mem'''
	}
	
	static protected def String printMemAccessFunction(ISLMultiAff maff) {
		if (maff === null) return null;
		val rhs = maff.affs.join(",", [a|AlphaPrintingUtil.toAlphaString(a)]);
		
		return '''(«rhs»)'''
	}
	
	def Map<BasicAffineSchedulePiece, CharSequence> generate() {
		
		var ret = new HashMap<BasicAffineSchedulePiece, CharSequence>();
		
		for (eq : targetMapping.getSystemBody().getStandardEquations()) {
			for (piece : targetMapping.getSchedulePieces(eq)) {
				statementContext = piece.domain
				ret.put(piece, doSwitch(eq.expr))
			}
			
		}
		
		return ret;
	}
	


	
	protected def printFunction(ISLMultiAff f) {
		AlphaPrintingUtil.toAShowString(f)
	}

	/* AlphaExpression */
	
	/* override */ def caseIfExpression(IfExpression ie) {
		'''if «ie.condExpr.doSwitch» then «ie.thenExpr.doSwitch» else «ie.elseExpr.doSwitch»'''
	}
	
	/* override */ def caseRestrictExpression(RestrictExpression re) {
		'''«re.expr.doSwitch»'''
	}
	
	/* override */ def caseAutoRestrictExpression(AutoRestrictExpression are) {
		'''«are.expr.doSwitch»'''
	}
	
	/* override */ def caseCaseExpression(CaseExpression ce) {
		
		var onlyExpr = null as AlphaExpression
		for (expr : ce.exprs) {
			if (!expr.contextDomain.isDisjoint(statementContext)) {
				if (onlyExpr === null) {
					onlyExpr = expr
				} else {
					throw new RuntimeException("Case Expression with branches are not supported.");
				}
			}
		}
		
		if (onlyExpr === null) throw new RuntimeException("Unexpected case.");
		
		'''«onlyExpr.doSwitch»'''
	}

	/* override */ 	def caseDependenceExpression(DependenceExpression de) {
		if (de.expr instanceof ConstantExpression || de.expr instanceof VariableExpression) {
			'''«de.expr.doSwitch»«de.function.printMemAccessFunction»'''
		} else {
			throw new RuntimeException("Expecting Normalized Alpha.");
		}
	}
	
	/* override */ def caseIndexExpression(IndexExpression ie) {
		'''val«ie.function.printFunction»'''
	}
	
	/* override */ def caseReduceExpression(ReduceExpression re) {
		re.printAbstractReduceExpression
	}
	
	/* override */ def caseExternalReduceExpression(ExternalReduceExpression ere) {
		ere.printAbstractReduceExpression
	}
	
	/* override */ def caseArgReduceExpression(ArgReduceExpression re) {
		re.printAbstractReduceExpression
	}
	
	/* override */ def caseExternalArgReduceExpression(ExternalArgReduceExpression ere) {
		ere.printAbstractReduceExpression
	}
	
	
	protected def dispatch printReduceExpression(ReduceExpression re, CharSequence proj, CharSequence body) {
		'''reduce(«re.operator.printReductionOP», «proj», «body»)'''
	}
	protected def dispatch printReduceExpression(ExternalReduceExpression ere, CharSequence proj, CharSequence body) {
		'''reduce(«ere.externalFunction.name», «proj», «body»)'''
	}
	protected def dispatch printReduceExpression(ArgReduceExpression are, CharSequence proj, CharSequence body) {
		'''argreduce(«are.operator.printReductionOP», «proj», «body»)'''
	}
	protected def dispatch printReduceExpression(ExternalArgReduceExpression aere, CharSequence proj, CharSequence body) {
		'''argreduce(«aere.externalFunction.name», «proj», «body»)'''
	}
	
	protected def printProjectionFunction(ISLMultiAff maff) {
		AlphaPrintingUtil.toShowString(maff)
	}
	
	protected def printReductionBody(AlphaExpression expr) {
		doSwitch(expr)
	}
	protected def printReductionOP(REDUCTION_OP op) {
		switch (op) {
			case SUM: {
				return "+"
			}
			case PROD: {
				return "*"
			}
			default: {
				return op.literal	
			}
		}	
	}
	
	protected def String printAbstractReduceExpression(AbstractReduceExpression are) {
		val proj = are.projection.printProjectionFunction
		val body = are.body.printReductionBody
		
		are.printReduceExpression(proj, body).toString
	}
	
	/* override */ def caseBinaryExpression(BinaryExpression be) {
		'''(«be.left.doSwitch» «be.operator» «be.right.doSwitch»)'''
	}
	
	/* override */ def caseMultiArgExpression(MultiArgExpression  mae) {
		'''«mae.operator»(«mae.exprs.map[doSwitch].join(", ")»)'''
	}
	
	/* override */ def caseExternalMultiArgExpression(ExternalMultiArgExpression  emae) {
		'''«emae.externalFunction.name»(«emae.exprs.map[doSwitch].join(", ")»)'''
	}
	
	/* override */ def caseUnaryExpression(UnaryExpression ue) {
		if ((ue.expr instanceof ConstantExpression) || 
			(ue.expr instanceof DependenceExpression) || 
			(ue.expr instanceof FuzzyDependenceExpression)
		)  {
			'''«ue.operator» («ue.expr.doSwitch»)'''
		} else {
			'''«ue.operator» «ue.expr.doSwitch»'''
		}
	}
	
	/* override */ def caseVariableExpression(VariableExpression ve) {
		ve.variable.memAccess
	}
	
	/* override */ def caseBooleanExpression(BooleanExpression be) {
		be.value.toString
	}
	
	/* override */ def caseIntegerExpression(IntegerExpression ie) {
		ie.value.toString
	}
	
	/* override */ def caseRealExpression(RealExpression re) {
		re.value.toString
	}
	
}