package alpha.model.transformation

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaCompleteVisitable
import alpha.model.AlphaExpression
import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaVisitable
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryExpression
import alpha.model.CaseExpression
import alpha.model.ConvolutionExpression
import alpha.model.DependenceExpression
import alpha.model.IfExpression
import alpha.model.IndexExpression
import alpha.model.ModelPackage
import alpha.model.MultiArgExpression
import alpha.model.RestrictExpression
import alpha.model.UnaryExpression
import alpha.model.factory.AlphaUserFactory
import alpha.model.util.AbstractAlphaCompleteVisitor
import alpha.model.util.AlphaExpressionUtil
import alpha.model.util.AlphaUtil
import alpha.model.util.Show
import java.util.ArrayList
import java.util.LinkedList
import java.util.function.Predicate
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil

import static alpha.model.factory.AlphaUserFactory.createBinaryExpression
import static alpha.model.factory.AlphaUserFactory.createCaseExpression
import static alpha.model.factory.AlphaUserFactory.createDependenceExpression
import static alpha.model.factory.AlphaUserFactory.createIfExpression
import static alpha.model.factory.AlphaUserFactory.createJNIDomain
import static alpha.model.factory.AlphaUserFactory.createJNIFunction
import static alpha.model.factory.AlphaUserFactory.createRestrictExpression
import static alpha.model.factory.AlphaUserFactory.createUnaryExpression
import static alpha.model.factory.AlphaUserFactory.createVariableExpression

import static extension alpha.model.util.ISLUtil.isNoneToNone
import alpha.model.VariableExpression
import alpha.model.util.ISLUtil

/**
 * Normalization of Alpha programs.
 * 
 * Each StandardEquation in an Alpha program should satisfy the following in its normal form:
 *   - the parent of CaseExpression should be StandardEquation or ReduceExpression
 *   - the parent of RestrictExpression should be StandarEquation, ReduceExpression, or CaseExpression
 *   - the parent of VariableExpression should be DependenceExpression
 *   - the child of DependenceExpression should be VariableExpression or ConstantExpression
 *  
 * The same applies to each input expression in an UseEquation.
 * 
 * The normalization rules defined and implemented here should transform any input Alpha program into its normal form.
 * The rules have mainly two objectives:
 *   - DependenceExpressions are propagated downwards
 *   - RestrictExpressions are propagated upwards (except when its child is a CaseExpression)
 * 
 * Due to the direction of propagation, most rules for dependences matches with dependence as the parent, 
 * but many rules involving restrict matches with other expressions as parents (e.g., binOp or unaryOp).
 * 
 * Note that the implementation in this class may not be complete yet, especially w.r.t. the new expressions.
 * In addition, this implementation do not use term-rewriting, and relies on bottom-up traversal
 * of the AST with occasional repeated traversal of sub-trees. This may create situations where multiple
 * calls are required to reach the normal form (which is a bug and should be fixed). Some of the rules 
 * include revisiting of the sub-trees, which is probably not done optimally. It is tricky to avoid
 * concurrent modifications and violations of containment in EMF. In some cases, some nodes in the AST
 * that are no longer valid due to expressions moving around are visited, which is caught by the validity check
 * at the beginning of outXXX methods for each expression.
 * 
 * 
 * There is also a two-level notion of the normal form resulting from the extensions to Alpha
 * The extensions are mostly aimed at improving the readability of Alpha programs by adding short-hands for
 * describing polyhedral objects and adding structure. The "shallow" normalize, which is the default behavior,
 * keeps most of the extended syntax elements intact. Specifically: 
 *  - uses of polyhedral objects defined at the top of a system are preserved
 *  - CalculatorExpressions in equations are also kept  
 *  - named CaseExpressions are not flattened
 *  - AutoRestrictExpressions are preserved
 * 
 * The "deep" normalize will remove the extended syntax by replacing polyhedral objects and auto keywords
 *  with explicit (and self-contained) polyhedral domains/functions. CaseExpressions are completely flattened
 *  and names are removed. This form is suited for compatibility with AlphaZ@CSU and 
 *  for back-end processing (e.g., code generator) where readability is not important.  
 * 
 */
class Normalize extends AbstractAlphaCompleteVisitor {

	/*
	 *  Rules collected from AlphaZ implementation using Tom/Gom
	 *  Removal/creation of identity functions and empty case branches were
	 *  implemented separately.
	 * 
	 *  debug("rule1", "dep@(A op B) -> dep@A op dep@B", "");
	 *  debug("rule2a", "((dom : A) op B) -> dom : (A op B)", "");
	 *  debug("rule2b", "(A op (dom : B)) -> dom : (A op B)", "");
	 *  debug("rule3", "depA@depB@E -> (depB o depA)@E", "");
	 *  debug("rule4", "domA : domB : E -> (domA intersection domB)@E", "");	
	 *  debug("rule5", "dep@(dom : E)-> dom.preimage(dep) : (dep@E)", "");
	 *  debug("rule6", "dep@(op E)-> op (dep@E)", "");
	 *  debug("rule7", "(op dom : E)-> dom : (op E)", "");	
	 *  debug("rule8a", "dep @ f(op, E)-> f(op, dep@E)", "");
	 *  debug("rule8b", "dep @ exf(op, E)-> exf(op, dep@E)", "");
	 *  debug("rule9", "dep@(val(f) -> val(f o dep)", "");
	 * 	debug("rule10", "case E1; case E2 esac; E3; esac -> case E1; E2; E3; esac", "");
	 *	debug("rule11a", "case E1; esac op E2 -> case E1 op E2; esac", "");
	 *	debug("rule11b", "E1 op case E2; esac -> case E1 op E2; esac", "");
	 *	debug("rule12", "op case E; esac -> case op E; esac", "");
	 *	debug("rule13", "f(op, case E; esac) -> case f(op, E) esac;", "");
	 *  debug("rule14", "dep case E; esac -> case dep E; esac", "");
	 *	debug("rule15", "dom : case E; esac -> case dom : E; esac", "");
	 *	debug("rule16", "dep @ if (cond, then, else) -> if(dep@cond, dep@then, dep@else) ", "");
	 *	debug("rule17a", "if(dom : cond, then, else) -> dom : if (cond, then, else)", "");
	 *	debug("rule17b", "if(cond, dom : then, else) -> dom : if (cond, then, else)", "");
	 *	debug("rule17c", "if(cond, then, dom : else) -> dom : if (cond, then, else)", "");
	 * 	debug("rule18a", "if(case E; esac, then, else) -> esac if (E, then, else); case", "");
	 *	debug("rule18b", "if(cond, case E; esac, else) -> esac if (cond, E, else); case", "");
	 *	debug("rule18c", "if(cond, then, case E; esac) -> esac if (cond, then, E); case", "");
	 *	debug("rule19", "exFunc(op, dom : E) -> dom : exFunc(op, E)", "");
	 * 
	 * New Rule:
	 *   reduce(op1, f1, D : reduce(op2, f2, E)) -> reduce(op1, f1, reduce(op2, f2, f2^-1(D) : E))
	 *      - this rule was added to expose nested reductions without restrict in between; 
	 *        restrict cannot be pushed out from reductions in general, and restrict are 
	 *        usually not pushed downwards. This rule is an exception.
	 * 
	 * Rules for New Syntax:
	 *   D : auto : E -> auto : E
	 *   auto : auto : E -> auto : E
	 *   auto : D : E -> auto : E
	 *   f @ conv (kernel, weight, data) -> conv(kernel, f' @ weight, f' @ data) (only when f is bijective)
	 * 
	 * Additional Rules:
	 *  - remove restrict expression when it is redundant (expression domain is unchanged by the restrict)
	 *  - remove branches of case expressions that have empty context domain
	 *  - replaces case with its child if it only has a branch
	 *  - inserts identity dependence expression where implied
	 */

	final boolean DEEP;
	
	public static boolean DEBUG = false
	
	protected def debug(String ruleID, String rule) {
		if (DEBUG) println(ruleID + ": " + rule)
	}
	
	protected def debug(AlphaExpression expr) {
		if (DEBUG) println(Show.print(AlphaUtil.getContainerSystem(expr))); 
//		if (DEBUG) println(PrintAST.print(AlphaUtil.getContainerSystem(expr))); 
	}
	
	/**
	 * Default constructor uses shallow normalize
	 */
	protected new() {
		DEEP = false;
	}
	protected new(boolean isDeepNormalize) {
		DEEP = isDeepNormalize;
	}

	static def void apply(AlphaCompleteVisitable acv) {
		apply(acv, false)
		AlphaInternalStateConstructor.recomputeContextDomain(acv)
	}
	static def void apply(AlphaCompleteVisitable acv, boolean isDeepNormalize) {
		if (acv instanceof AlphaVisitable) {
			apply(acv as AlphaVisitable, isDeepNormalize)
		} else if (acv instanceof AlphaExpressionVisitable) {
			apply(acv as AlphaExpressionVisitable, isDeepNormalize)
		} else {
			throw new RuntimeException("This should be unreachable code. Got: " + acv);
		}
	}
	
	static def void apply(AlphaVisitable av) { apply(av, false) }
	static def void apply(AlphaVisitable av, boolean isDeepNormalize) {
		val visitor = new Normalize(isDeepNormalize);
		av.accept(visitor);
	}
	static def void apply(AlphaExpressionVisitable aev) { apply(aev, false) }
	static def void apply(AlphaExpressionVisitable aev, boolean isDeepNormalize) {
		val visitor = new Normalize(isDeepNormalize);
		aev.accept(visitor);
	}
	
	private def dispatch reapply(AlphaVisitable av) {
		this.accept(av)
	}
	private def dispatch reapply(AlphaExpressionVisitable aev) {
		this.accept(aev)
	}
	
	private def invalidState(EObject obj) {
		obj.eContainer === null
	}
	
	override outDependenceExpression(DependenceExpression de) {
		if (invalidState(de)) return;
		
		// xtend dispatch to match based on child expression type
		dependenceExpressionRules(de, de.expr);

		// f @ E = E if f = I
		// Note: checking if a function is the identity function fails
		// if the function has no inputs or outputs, which may occur in dependence raising.
		// This is caused because the "isIdentity" function tries to build an identity function
		// from the dependence function's space, but can't do so if there are no inputs or outputs.
		if (de.function.isNoneToNone || de.function.isIdentity) {
			// VariableExpressions must have a DependenceExpression parent
			if (de.expr instanceof VariableExpression) return;
			
			debug("identity", "f @ E = E if f = I");
			EcoreUtil.replace(de, de.expr);
		}
	}

	// f1 @ f2 @ E -> (f2 o f1) @ E 
	protected def dispatch dependenceExpressionRules(DependenceExpression de, DependenceExpression innerDep) {
		debug("composition", "f1 @ f2 @ E -> (f2 o f1) @ E");
		
		val composedF = innerDep.function.pullback(de.function)
		de.functionExpr = createJNIFunction(composedF)
		EcoreUtil.replace(de.expr, innerDep.expr);
		
		debug(de);
	}

	// f1 @ val(f2) -> val(f2 o f1)
	protected def dispatch dependenceExpressionRules(DependenceExpression de, IndexExpression innerIE) {
		debug("composition-val", "f1 @ val(f2) -> val(f2 o f1)");
		
		val composedF = innerIE.function.pullback(de.function)
		innerIE.functionExpr = createJNIFunction(composedF)
		EcoreUtil.replace(de, innerIE);
		
		debug(innerIE);
	}

	// f @ D : E -> f-1(D) : E
	protected def dispatch dependenceExpressionRules(DependenceExpression de, RestrictExpression innerRE) {
		debug("preimage", "f @ D : E -> f-1(D) : f@E");
		
		val preimage = innerRE.restrictDomain.preimage(de.function)
		innerRE.domainExpr = createJNIDomain(preimage)
		innerRE.expr = createDependenceExpression(de.function, innerRE.expr)
		EcoreUtil.replace(de, innerRE);
	
		debug(innerRE);
		// the updated expression must be revisited 
		// it also needs its domains recomputed for this rule
		AlphaInternalStateConstructor.recomputeContextDomain(innerRE);
		reapply(innerRE);
	}

	// f @ (A op B) -> (f @ A op f @ B)
	protected def dispatch dependenceExpressionRules(DependenceExpression de, BinaryExpression binExpr) {
		debug("push-dep BinExpr", "f @ (A op B) -> (f @ A op f @ B)");
		EcoreUtil.replace(de, binExpr);

		val newL = createDependenceExpression(de.function, binExpr.left)
		val newR = createDependenceExpression(de.function, binExpr.right)

		binExpr.left = newL
		binExpr.right = newR

		debug(binExpr);
		// the updated expression must be revisited 
		AlphaInternalStateConstructor.recomputeContextDomain(binExpr);
		reapply(binExpr);
	}

	// f @ (op E) -> (op f@E)
	protected def dispatch dependenceExpressionRules(DependenceExpression de, UnaryExpression ue) {
		debug("push-dep UnaryExpr", "f @ (op E) -> (op f@E)");
		EcoreUtil.replace(de, ue)
		val newExpr = createDependenceExpression(de.function, ue.expr)
		ue.expr = newExpr

		debug(ue);
		// the updated expression must be revisited
		
		AlphaInternalStateConstructor.recomputeContextDomain(ue); 
		reapply(ue);
	}

	// f @ op(E1, E2, ...) -> op(f@E1, f@E2, ...)
	// where f can be pre-defined ops or external function
	protected def dispatch dependenceExpressionRules(DependenceExpression de, MultiArgExpression mae) {
		debug("push-dep MAExpr", "f @ (op E) -> (op f@E)");
		propagateDownwards(de, mae)

		debug(mae);
		// the updated expression must be revisited 
		reapply(mae);
	}

	// f @ case {E1; E2; ...} -> case {f@E1; f@E2; ...}
	protected def dispatch dependenceExpressionRules(DependenceExpression de, CaseExpression ce) {
		debug("push-dep CaseExpr", "f @ (op E) -> (op f@E)");
		propagateDownwards(de, ce)

		debug(ce);
		// the updated expression must be revisited 
		AlphaInternalStateConstructor.recomputeContextDomain(ce);
		reapply(ce);
	}
	
		
	// f @ if (E1, E2, E3) -> if (f@E1, f@E2, f@E3)
	protected def dispatch dependenceExpressionRules(DependenceExpression de, IfExpression ie) {
		debug("push-dep IfExpr", "f @ if (E1, E2, E3) -> if (f@E1, f@E2, f@E3)");
		EcoreUtil.replace(de, ie);
		
		val newC = createDependenceExpression(de.function, ie.condExpr)
		val newT = createDependenceExpression(de.function, ie.thenExpr)
		val newE = createDependenceExpression(de.function, ie.elseExpr)

		ie.condExpr = newC;
		ie.thenExpr = newT;
		ie.elseExpr = newE;
		
		debug(ie);
		// the updated expression must be revisited 
		AlphaInternalStateConstructor.recomputeContextDomain(ie);
		reapply(ie);
	}

	// f @ conv (kernel, weight, data) -> conv(kernel, f' @ weight, f' @ data)
	//   f' = f extended with kernel domain dims
	protected def dispatch dependenceExpressionRules(DependenceExpression de, ConvolutionExpression ce) {
		if (!de.function.toMap.isBijective) return "";
		
		debug("push-dep ConvExpr", "f @ conv (kernel, weight, data) -> conv(kernel, f' @ weight, f' @ data)");
		EcoreUtil.replace(de, ce);
		
		val newMaff = AlphaExpressionUtil.extendMultiAffWithIdentityDimensions(de.function, ce.kernelDomain.nbIndices)

		val newKernelExpr = createDependenceExpression(newMaff.copy, ce.kernelExpression)
		val newDataExpr = createDependenceExpression(newMaff, ce.dataExpression)
		
		ce.kernelExpression = newKernelExpr
		ce.dataExpression = newDataExpr
		
		debug(ce);
		
		// the updated expression must be revisited 
		AlphaInternalStateConstructor.recomputeContextDomain(ce);
		reapply(ce);
	}
	
	protected def dispatch dependenceExpressionRules(DependenceExpression de, AlphaExpression expr) {
		// Nothing when there is no matching rule
	}

	static def childOfCaseExpression(AlphaExpression expr) {
		expr.eContainer instanceof CaseExpression
	}

	override outRestrictExpression(RestrictExpression re) {
		if (invalidState(re)) return;
		
		restrictExpressionRules(re, re.expr)
		
		if (invalidState(re)) return;

		// D : E -> E if expression domain ofD : E and E are the same (i.e., restrict has no effect)
		if (re.expressionDomain.isEqual(re.expr.expressionDomain) && !re.childOfCaseExpression) {
			debug("redundant restrict", "D : E -> E");
			EcoreUtil.replace(re, re.expr);
		}
	}

	// D1 : D2 : E -> (D1 and D2) : E
	protected def dispatch restrictExpressionRules(RestrictExpression re, RestrictExpression innerRE) {
		debug("merge restrict", "D1 : D2 : E -> (D1 and D2) : E");
		val intersection = re.restrictDomain.intersect(innerRE.restrictDomain)
		re.domainExpr = createJNIDomain(intersection)
		EcoreUtil.replace(re.expr, innerRE.expr);
		
		debug(re);
	}
	
	// D : auto : E -> auto : E
	protected def dispatch restrictExpressionRules(RestrictExpression re, AutoRestrictExpression are) {
		debug("merge auto-restrict", "D : auto : E -> auto : E");
		EcoreUtil.replace(re, are);
		
		debug(are);
	}

	// D : case { E1; E2; ... } -> case { D:E1; D:E2; ... }
	protected def dispatch restrictExpressionRules(RestrictExpression re, CaseExpression ce) {
		debug("push restrict", "D : case { E1; E2; ... } -> case { D:E1; D:E2; ... }");
		propagateDownwards(re, ce)
		
		debug(ce);
		// the updated expression must be revisited 
		AlphaInternalStateConstructor.recomputeContextDomain(ce);
		reapply(ce);
	}
	
	// reduce (op1, f1, D : reduce(op2, f2, E)) -> reduce(op1, f1, reduce(op2, f2, f2^-1(D) : E))
	protected def dispatch restrictExpressionRules(RestrictExpression re, AbstractReduceExpression are) {
		if (re.eContainer instanceof AbstractReduceExpression) {
			debug("push restrict", "reduce (op1, f1, D : reduce(op2, f2, E)) -> reduce(op1, f1, reduce(op2, f2, f2^-1(D) : E))");
			val preimage = re.restrictDomain.preimage(are.projection)
			val restrictExpr = AlphaUserFactory.createRestrictExpression(preimage, are.body)
			are.body = restrictExpr
			EcoreUtil.replace(re, are)
			
			debug(are);
			// the updated expression must be revisited 
			AlphaInternalStateConstructor.recomputeContextDomain(are);
			reapply(are);
		}
	}
	protected def dispatch restrictExpressionRules(RestrictExpression re, AlphaExpression expr) {
		// Nothing if there is no matching rule
	}

	override outBinaryExpression(BinaryExpression be) {
		if (invalidState(be)) return;

		binaryExpressionRules(be, be.left, be.right)
	}

	// case { L1; L2; ... } op case { R1; R2; ... } -> case { E1; E2; ... }
	// where E1, E2, etc. are the set of combos from left and right branches
	protected def dispatch binaryExpressionRules(BinaryExpression be, CaseExpression ceLeft, CaseExpression ceRight) {
		val origContainer = be.eContainer  as AlphaCompleteVisitable
		
		// compute the subdomains in which the branches of the left and right case expressions overlap
		val newBranchDomains = ceLeft.exprs.map[contextDomain]
			.map[l | ceRight.exprs.map[contextDomain].map[r | l.copy.intersect(r)]]
			.flatten
			.reject[empty]
		
		// create new restrictExprs for each new branch
		val emptyRestrictExprs = newBranchDomains.map[domain | createRestrictExpression(domain)]
		
		// set the restrictExpr.expr depending on whether or not original cases overlap
		val newCE = createCaseExpression
		newCE.exprs += emptyRestrictExprs.map[re | 
			var left = EcoreUtil.copy(ceLeft.exprs.findFirst[e | re.restrictDomain.isSubset(e.expressionDomain)])
			var right = EcoreUtil.copy(ceRight.exprs.findFirst[e | re.restrictDomain.isSubset(e.expressionDomain)])
			
			if (left instanceof RestrictExpression)
				left = left.expr
			if (right instanceof RestrictExpression)
				right = right.expr
			
			if (left !== null && right !== null) {
				re.expr = createBinaryExpression(be.operator, left, right)
			} else {
				re.expr = if (left !== null) left else right
			}
			re
		]
		
		EcoreUtil.replace(be, newCE)
		AlphaInternalStateConstructor.recomputeContextDomain(newCE)
		
		reapply(origContainer)
	}
	
	protected def dispatch binaryExpressionRules(BinaryExpression be, AlphaExpression aeLeft, CaseExpression ceRight) {
		val origContainer = be.eContainer as AlphaCompleteVisitable
		binaryExpressionRules(be, ceRight)
		if (origContainer != be.eContainer) {
			reapply(origContainer)
		}
	}
	
	protected def dispatch binaryExpressionRules(BinaryExpression be, AlphaExpression aeLeft, AlphaExpression aeRight) {
		val origContainer = be.eContainer  as AlphaCompleteVisitable 
		
		binaryExpressionRules(be, aeLeft)
		
		if (origContainer != be.eContainer) {
			reapply(origContainer)
		}
	}

	// (D : A op B) -> D : (A op B)
	// and
	// (A op D : B) -> D : (A op B)
	protected def dispatch binaryExpressionRules(BinaryExpression be, RestrictExpression re) {
		debug("pull-restrict BinExpr", "(D : A op B) -> D : (A op B) || (A op D : B) -> D : (A op B)");
		
		EcoreUtil.replace(re, re.expr);
		EcoreUtil.replace(be, re);
		re.expr = be
		
		debug(be);
		
		//moving restrict expression changes expression domain
		AlphaInternalStateConstructor.recomputeContextDomain(be);
	}
	
	// case { E1; E2; ... } op E -> case { E1 op E; E2 op E; ... }
	// and
	// E op case { E1; E2; ... } -> case { E op E1; E op E2; ... }
	protected def dispatch binaryExpressionRules(BinaryExpression be, CaseExpression ce) {
		val isLeft = ce.eContainmentFeature == ModelPackage.Literals.BINARY_EXPRESSION__LEFT
		
		if (DEBUG && isLeft)
			debug("pull-case BinExpr", "case { E1; E2; ... } op E -> case { E1 op E; E2 op E; ... }")
		else
			debug("pull-case BinExpr", "E op case { E1; E2; ... } -> case { E op E1; E op E2; ... }")
			
		val newCE = createCaseExpression();
		EcoreUtil.replace(be, newCE)

		val children = new ArrayList<AlphaExpression>(ce.exprs.size)
		ce.exprs.forEach[e|children.add(e)]
		
		if (isLeft)
			children.forEach[e|newCE.exprs.add(createBinaryExpression(be.operator, e, EcoreUtil.copy(be.right)))]
		else
			children.forEach[e|newCE.exprs.add(createBinaryExpression(be.operator, EcoreUtil.copy(be.left), e))]
			
		debug(newCE);
		
		// the updated expression must be revisited 
		//  some expressions are created afresh, and require context domain to be calculated
		AlphaInternalStateConstructor.recomputeContextDomain(newCE);
	}

	protected def dispatch binaryExpressionRules(BinaryExpression be, AlphaExpression expr) {
		// Nothing if there is no matching rule
	}

	override outUnaryExpression(UnaryExpression ue) {
		if (invalidState(ue)) return;
		
		unaryExpressionRules(ue, ue.expr)
	}

	// (op D : E) -> D : (op E)
	protected def dispatch unaryExpressionRules(UnaryExpression ue, RestrictExpression innerRE) {
		debug("pull-restrict UnaryExpr", "(op D : E) -> D : (op E)")
		val E = innerRE.expr
		EcoreUtil.replace(ue, innerRE)
		innerRE.expr = ue
		ue.expr = E
		
		debug(ue);
	}
	
	//op case { E1; E2; ... }-> case { op E1; op E2; ... }
	protected def dispatch unaryExpressionRules(UnaryExpression ue, CaseExpression ce) {
		debug("pull-case UnaryExpr", "op case { E1; E2; ... }-> case { op E1; op E2; ... }")
		propagateDownwards(ue, ce)
		
		debug(ce);
		
		// the updated expression must be revisited 
		AlphaInternalStateConstructor.recomputeContextDomain(ce)
		reapply(ce);
	}

	protected def dispatch unaryExpressionRules(UnaryExpression ue, AlphaExpression expr) {
		// Nothing if there is no matching rule
	}
	
	override outMultiArgExpression(MultiArgExpression mae) {	
		if (invalidState(mae)) return;
		
		//originally rule 13
		// f(op, case { E1; E2; ... }) -> case { f(op, E1); f(op, E2); ...}
		for (var i=0; i < mae.exprs.size; i++) {
			val nextExpr = multiArgExpressionRules(mae, mae.exprs.get(i))
			if (nextExpr !== null) {
				reapply(nextExpr);
				return;
			}
		}
		
		//originally rule 19
		//  f(op, D : E) -> D : f(op, E)
		//extended to propagate all restrict expressions in the operand all at once
		//  f(op, D1 : E1, D2 : E2) -> (D1 and D2 ...) : f(op, E1, E2, ...)
		if (mae.exprs.exists[e|e instanceof RestrictExpression]) {
			debug("pull-restrict MAExpr", "f(op, D1 : E1, D2 : E2) -> (D1 and D2 ...) : f(op, E1, E2, ...)")
			var intersection = mae.exprs.filter(RestrictExpression).map[e|e.restrictDomain].reduce[d1, d2|d1.intersect(d2)]
			val hoistedRE = createRestrictExpression(intersection)
			EcoreUtil.replace(mae, hoistedRE)
			hoistedRE.expr = mae
			mae.exprs.filter(RestrictExpression).forEach[e|EcoreUtil.replace(e, e.expr)]
			
			debug(hoistedRE);
			
			AlphaInternalStateConstructor.recomputeContextDomain(hoistedRE)
			reapply(hoistedRE);
			return;
		}
	}
	
	// f(op, case { E1; E2; ... }) -> case { f(op, E1); f(op, E2); ...}
	protected def dispatch multiArgExpressionRules(MultiArgExpression mae, CaseExpression ce) {
		val index = mae.exprs.indexOf(ce)
		if (index <= -1) throw new RuntimeException("Unexpected case at normalize rule for multi-arg expressions. The child index cannot be found. ");
		
		debug("pull-case MAExpr", "f(op, case { E1; E2; ... }) -> case { f(op, E1); f(op, E2); ...}")
		
		val children = new ArrayList<AlphaExpression>(ce.exprs.size)
		ce.exprs.forEach[e|children.add(e)]
		ce.exprs.clear
		
		//each branch of the case is replaced with the multi-arg expression
		// where the operand corresponding to case is replaced with the case branch
		children.forEach[e|
			val newMAE = EcoreUtil.copy(mae)
			EcoreUtil.replace(newMAE.exprs.get(index), e) 
			ce.exprs.add(newMAE)
		]
		EcoreUtil.replace(mae, ce)
		
		debug(ce);
		
		AlphaInternalStateConstructor.recomputeContextDomain(ce)
		return ce;
	}

	protected def dispatch multiArgExpressionRules(MultiArgExpression mae, AlphaExpression expr) {
		// Nothing if there is no matching rule
		return null
	}

	override outCaseExpression(CaseExpression ce) {
		if (invalidState(ce)) return;
		
		//originally rule 10
		//  case { E1; case { E2; E3; ...}; E4 ...} -> case{ E1; E2; E3; ...; E4; ... }
		//  i.e., flattening case expressions
		//extended in two ways:
		// - flatten all case expressions at once
		// - do not flatten named case for shallow normalize
		val Predicate<AlphaExpression> canFlatten = [e|e instanceof CaseExpression && (DEEP || !(e as CaseExpression).named)]
		if (ce.exprs.exists(canFlatten)) {
			debug("flatten-case", "case { E1; case { E2; E3; ...}; E4 ...} -> case{ E1; E2; E3; ...; E4; ... }")
			val children = new LinkedList<AlphaExpression>()
			ce.exprs.forEach[e|
				if (canFlatten.test(e)) {
					for (AlphaExpression expr : (e as CaseExpression).exprs) {
						children.add(if (expr instanceof AutoRestrictExpression) LiftAutoRestrict.apply(expr) else expr);
					}
					children.addAll((e as CaseExpression).exprs)	
				} else {
					children.add(e)
				}
			]
			ce.exprs.clear
			ce.exprs.addAll(children)
			
			debug(ce);
		}
		
		//removing empty case branches
		val emptyExprs = new LinkedList<AlphaExpression>
		ce.exprs.forEach[e| if (e.contextDomain.isEmpty) emptyExprs.add(e)]
		ce.exprs.removeAll(emptyExprs)
		
		//remove case if it has single branch
		if (ce.exprs.size == 1) {
			EcoreUtil.replace(ce, ce.exprs.get(0))
		}
		
	}
	
	override outIfExpression(IfExpression ie) {
		if (invalidState(ie)) return;
		
		val rule = [AlphaExpression e|
			val nextExpr = ifExpressionRules(ie, e)
			if (nextExpr !== null) {
				reapply(nextExpr)
				return true;
			}
			return false;
		]
		
		if (rule.apply(ie.condExpr)) return;
		if (rule.apply(ie.thenExpr)) return;
		if (rule.apply(ie.elseExpr)) return;
	}

	//All of the three variations:
	//  if (D : E1, E2, E3) -> D : if (E1, E2, E3)
	//  if (E1, D : E2, E3) -> D : if (E1, E2, E3)
	//  if (E1, E2, D : E3) -> D : if (E1, E2, E3) 
	protected def dispatch ifExpressionRules(IfExpression ie, RestrictExpression re) {
		
		if (DEBUG) {
			val isCond = re.eContainmentFeature == ModelPackage.Literals.IF_EXPRESSION__COND_EXPR
			val isThen = re.eContainmentFeature == ModelPackage.Literals.IF_EXPRESSION__THEN_EXPR
			val isElse = re.eContainmentFeature == ModelPackage.Literals.IF_EXPRESSION__ELSE_EXPR
			
			if (isCond)
				debug("pull-restrict IfExpr", "if (D : E1, E2, E3) -> D : if (E1, E2, E3)")
			else if (isThen)
				debug("pull-restrict IfExpr", "if (E1, D : E2, E3) -> D : if (E1, E2, E3)")
			else if (isElse)
				debug("pull-restrict IfExpr", "if (E1, E2, D : E3) -> D : if (E1, E2, E3)")
		}
		
		EcoreUtil.replace(re, re.expr);
		EcoreUtil.replace(ie, re);
		re.expr = ie
		
		debug(re);
		
		//moving restrict expression changes expression domain
		AlphaInternalStateConstructor.recomputeContextDomain(re);
		
		return re;
	}
	
	//All of the three variations:
	//  if (case { E1; E2; ... }, E10, E11) -> case { if (E1, E10, E11); if (E2, E10, E11); ... }
	//  if (E10, case { E1; E2; ... }, E11) -> case { if (E10, E1, E11); if (E10, E2, E11); ... }
	//  if (E10, E11, case { E1; E2; ... }) -> case { if (E10, E11, E1); if (E10, E11, E2); ... }
	protected def dispatch ifExpressionRules(IfExpression ie, CaseExpression ce) {
		val isCond = ce.eContainmentFeature == ModelPackage.Literals.IF_EXPRESSION__COND_EXPR
		val isThen = ce.eContainmentFeature == ModelPackage.Literals.IF_EXPRESSION__THEN_EXPR
		val isElse = ce.eContainmentFeature == ModelPackage.Literals.IF_EXPRESSION__ELSE_EXPR
		
		if (DEBUG) {
			if (isCond)
				debug("pull-case IfExpr", "if (case { E1; E2; ... }, E10, E11) -> case { if (E1, E10, E11); if (E2, E10, E11); ... }")
			else if (isThen)
				debug("pull-case IfExpr", "if (E10, case { E1; E2; ... }, E11) -> case { if (E10, E1, E11); if (E10, E2, E11); ... }")
			else if (isElse)
				debug("pull-case IfExpr", "if (E10, E11, case { E1; E2; ... }) -> case { if (E10, E11, E1); if (E10, E11, E2); ... }")
		}
		
		val newCE = createCaseExpression();
		EcoreUtil.replace(ie, newCE)

		val children = new ArrayList<AlphaExpression>(ce.exprs.size)
		ce.exprs.forEach[e|children.add(e)]
				
		if (isCond)
			children.forEach[e|newCE.exprs.add(createIfExpression(e, EcoreUtil.copy(ie.thenExpr), EcoreUtil.copy(ie.elseExpr)))]
		else if (isThen)
			children.forEach[e|newCE.exprs.add(createIfExpression(EcoreUtil.copy(ie.condExpr), e, EcoreUtil.copy(ie.elseExpr)))]
		else if (isElse)
			children.forEach[e|newCE.exprs.add(createIfExpression(EcoreUtil.copy(ie.condExpr), EcoreUtil.copy(ie.thenExpr), e))]
			
		debug(newCE);

		// the updated expression must be revisited 
		//  some expressions are created afresh, and require context domain to be calculated
		AlphaInternalStateConstructor.recomputeContextDomain(newCE);
		
		return newCE;
	}
	
	protected def dispatch ifExpressionRules(IfExpression ie, AlphaExpression expr) {
		//Nothing if there is no matching rule
		return null;
	}
	
	override outAutoRestrictExpression(AutoRestrictExpression are) {
	 	//auto : D : E -> auto : E
		if (are.expr instanceof RestrictExpression)
			EcoreUtil.replace(are.expr, (are.expr as RestrictExpression).expr);
		//auto : auto : E -> auto : E
		if (are.expr instanceof AutoRestrictExpression)
			EcoreUtil.replace(are.expr, (are.expr as AutoRestrictExpression).expr);
	}
	
	/**
	 * Helper function to propagate dependence/restrict expressions downwards.
	 * 
	 * It is a recurring pattern for several expressions to take the function/domain
	 * in the outer dependence/restrict surrounding case/multi-arg expressions and
	 * apply them to all of their children. This method centralizes this operation
	 * to avoid redundant code.
	 * 
	 */
	private def propagateDownwards(AlphaExpression outer, AlphaExpression inner) {
		EcoreUtil.replace(outer, inner);

		val exprsEList = getChildrenEList(inner)
		val children = new ArrayList<AlphaExpression>(exprsEList.size)
		
		// keep the children to a non-containment list to avoid concurrent modification exception
		exprsEList.forEach[e|children.add(e)]
		exprsEList.clear

		children.forEach[e|exprsEList.add(wrapExpression(outer, e))]
	}
	
	private def dispatch getChildrenEList(CaseExpression ce) {
		ce.exprs
	}
	private def dispatch getChildrenEList(MultiArgExpression mae) {
		mae.exprs
	}
	
	private def dispatch wrapExpression(RestrictExpression wrapper, AlphaExpression expr) {
		return createRestrictExpression(wrapper.restrictDomain, expr)
	}
	private def dispatch wrapExpression(DependenceExpression wrapper, AlphaExpression expr) {
		return createDependenceExpression(wrapper.function, expr)
	}
	private def dispatch wrapExpression(UnaryExpression wrapper, AlphaExpression expr) {
		return createUnaryExpression(wrapper.operator, expr)
	}
	
	override outVariableExpression(VariableExpression ve) {
		// Every VariableExpression must have a DependenceExpression as a parent
		if(!(ve.eContainer instanceof DependenceExpression)) {
			debug("implicit DepExpr", "V -> I @ V")
			val identityMaff = ISLUtil.toMultiAff(ve.getVariable.getDomain.copy.toIdentityMap)
			
			var newVe = createVariableExpression(ve.getVariable)
			newVe.setContextDomain(ve.getContextDomain.copy)
			newVe.setExpressionDomain(ve.getExpressionDomain.copy)
			
			var de = createDependenceExpression(identityMaff, newVe)
			de.setContextDomain(ve.getContextDomain.copy)
			de.setExpressionDomain(ve.getExpressionDomain.copy)
			
			EcoreUtil.replace(ve, de)
		}
	}
}
