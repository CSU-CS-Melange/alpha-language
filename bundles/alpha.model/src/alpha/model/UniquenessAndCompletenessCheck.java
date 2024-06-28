package alpha.model;

import static alpha.model.util.AlphaUtil.callISLwithErrorHandling;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;

import alpha.model.issue.AlphaIssue;
import alpha.model.issue.AlphaIssueFactory;
import alpha.model.issue.UnexpectedISLErrorIssue;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaExpressionUtil;
import alpha.model.util.AlphaUtil;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;

/**
 * Uniqueness and Completeness check is a basic analysis of Alpha programs to
 * ensure that all computations are defined once and only once.
 * 
 * Assumes that the uniqueness at the syntactic level (duplicate equations, etc.)
 * are checked by NameUniquenessChecker.
 * 
 * The properties to be checked are:
 *   - expression domain of the root expression should cover the variable domain
 *   - branches of case expressions should have disjoint domains
 *   - system bodies have disjoint parameter domains
 *   - system bodies should cover the entire parameter domain
 *   - system bodies should not be empty
 *   - use equations have disjoint definitions for its outputs
 *   - use equations should not partially define a variable
 * 
 * @author tyuki
 *
 */
public class UniquenessAndCompletenessCheck extends AbstractAlphaCompleteVisitor {
	
	private List<AlphaIssue> issues = new LinkedList<>();
	private Map<Variable, List<VariableExpression>> useDefs;

	public static List<AlphaIssue> check(List<AlphaRoot> roots) {
		UniquenessAndCompletenessCheck checker = new UniquenessAndCompletenessCheck();
		
		for (AlphaRoot root : roots ) {
			checker.accept(root);
		}
		
		return checker.issues;
	}

	public static List<AlphaIssue> check(AlphaRoot root) {
		return check(Arrays.asList(root));
	}
	
	@Override
	public void inAlphaSystem(AlphaSystem system) {
		checkSystemBodyConsistency(system);
		
		super.inAlphaSystem(system);
	}
	
	private void checkSystemBodyConsistency(AlphaSystem system) {
		
		ISLSet unionBodies = null;
		ISLSet intersections = null;
		for (SystemBody body : system.getSystemBodies()) {
			//If the SystemBodyDomain is not defined, it failed in DomainCalculator already 
			if (body.getParameterDomainExpr() == null) return;
			
			if (unionBodies == null) {
				unionBodies = body.getParameterDomain();
			} else {
				if (!unionBodies.isDisjoint(body.getParameterDomain())) {
					ISLSet intersection = unionBodies.copy().intersect(body.getParameterDomain());
					if (intersections == null) intersections = intersection;
					else intersections = intersections.union(intersection);
				}
				unionBodies = unionBodies.union(body.getParameterDomain());
			}
			if (body.getParameterDomain().isEmpty()) {
				issues.add(AlphaIssueFactory.emptySystemBody(body));
			}
		}
		
		if (intersections != null) {
			for (SystemBody body : system.getSystemBodies()) {
				issues.add(AlphaIssueFactory.overlappingSystemBodies(body, intersections.copy()));
			}
		}
		
		if (unionBodies != null && !system.getParameterDomain().isEqual(unionBodies)) {
			issues.add(AlphaIssueFactory.incompleteSystem(system, system.getParameterDomain().subtract(unionBodies)));
		}
		

	}
	
	@Override
	public void inSystemBody(SystemBody sysBody) {
		//initialize book-keeping records for UseEquation checks
		useDefs = new HashMap<Variable, List<VariableExpression>>(); 
	
		super.inSystemBody(sysBody);
	}
	
	@Override
	public void outSystemBody(SystemBody sysBody) {
		//check if use equations have disjoint definitions
		for (Variable v : useDefs.keySet()) {
			List<VariableExpression> vexprs = useDefs.get(v);
			
			ISLSet union = null;
			ISLSet intersections = null;
			
			for (VariableExpression vexpr : vexprs) {
				//if context domain is not defined, there were earlier issues
				if (vexpr.getContextDomain() == null) break;
				if (union == null) {
					union = vexpr.getContextDomain();
				} else {
					if (!union.isDisjoint(vexpr.getContextDomain())) {
						ISLSet intersection = vexpr.getContextDomain().intersect(union.copy());
						if (intersections == null) intersections = intersection;
						else intersections = intersections.union(intersection);
					}
					union = union.union(vexpr.getContextDomain());
				}
			}
			if (union == null) continue;
			
			//check for disjoint
			if (intersections != null) {
				for (VariableExpression vexpr : vexprs) {
					AlphaExpression expr = findAncestorOutputExpression(vexpr);
					issues.add(AlphaIssueFactory.overlappingUseEquations(expr, intersections.copy()));
				}
			}
			
			//check for incomplete definition
			ISLSet vDom = v.getDomain();
			if (sysBody.getParameterDomain() != null) vDom = vDom.intersectParams(sysBody.getParameterDomain());
			
			if (!union.isEqual(vDom)) {
				ISLSet diff = v.getDomain().subtract(union);
				for (VariableExpression vexpr : vexprs) {
					AlphaExpression expr = findAncestorOutputExpression(vexpr);
					issues.add(AlphaIssueFactory.incompleteUseEquation(v, expr, diff.copy()));
				}
			}
		}
		
		super.outSystemBody(sysBody);
	}
	
	private AlphaExpression findAncestorOutputExpression(VariableExpression vexpr) {
		UseEquation eq  = (UseEquation) AlphaUtil.getContainerEquation(vexpr);
		for (AlphaExpression expr : eq.getOutputExprs()) {
			if (EcoreUtil.isAncestor(expr, vexpr)) {
				return expr;
			}
		}
		
		throw new RuntimeException("Ancestor of a VariableExpression was not found in the container equation. The model is in an inconsistent state.");
	}
	
	@Override
	public void inStandardEquation(StandardEquation se) {
		ISLSet defDom = se.getExpr().getExpressionDomain();
		ISLSet varDom = se.getVariable().getDomain();
		if (defDom == null || varDom == null) return;
		//This case is already checked at ContextDomainCalculator
		if (!varDom.getSpace().isEqual(defDom.getSpace())) return;
		//This case is already checked at JNIDomainCalculator
		if (se.getSystemBody().getParameterDomainExpr() == null) return;

		callISLwithErrorHandling(()->{
			ISLSet varDomContext = varDom.intersectParams(se.getSystemBody().getParameterDomain());
			ISLSet undefDom = varDomContext.copy().subtract(defDom);
			
			if (!undefDom.isEmpty()) {
				ISLSet systemParam = AlphaUtil.getContainerSystem(se).getParameterDomain();
				ISLSet undefDomParam = undefDom.copy().params().gist(systemParam);
				ISLSet undefDomGist = undefDom.gist(varDomContext);

				issues.add(AlphaIssueFactory.incompleteEquation(se, undefDomGist, undefDomParam));
			}
		}, (err)->{issues.add(new UnexpectedISLErrorIssue(err, se, null));});
		
	}
	
	@Override
	public void inCaseExpression(CaseExpression ce) {
		if (AlphaExpressionUtil.testNonNullContextDomain(AlphaExpressionUtil.getChildrenOfType(ce, AlphaExpression.class))) {
			
			ISLSet childrenDomain = null;
			
			for (AlphaExpression expr : ce.getExprs()) {
				if (childrenDomain == null) {
					childrenDomain = expr.getContextDomain();
				} else {
					if (!childrenDomain.isDisjoint(expr.getContextDomain())) {
						
						issues.add(AlphaIssueFactory.overlappingCaseBranch(expr, expr.getContextDomain().intersect(childrenDomain.copy())));
					}
					childrenDomain = childrenDomain.union(expr.getContextDomain());
				}
			}
		}
	}
	
	@Override
	public void inUseEquation(UseEquation ue) {
		//check for simple infinite recursion
		if (ue.getSystem().equals(ue.getSystemBody().getSystem())) {
			ISLMultiAff callParams = ue.getCallParams();
			int nbParams = callParams.getSpace().getNbParams();
			callParams = callParams.moveDims(ISLDimType.isl_dim_in, 0, ISLDimType.isl_dim_param, 0, nbParams);
			
			if (callParams.isIdentity()) {
				issues.add(AlphaIssueFactory.infinitelyRecursiveUseEquation(ue));
			}
		}
		
		//book keeping for output consistency check
		for(AlphaExpression expr : ue.getOutputExprs()) {
			for (VariableExpression v : EcoreUtil2.getAllContentsOfType(expr, VariableExpression.class)) {
				if (!useDefs.containsKey(v.getVariable())) {
					useDefs.put(v.getVariable(), new LinkedList<>());
				}
				useDefs.get(v.getVariable()).add(v);
			}
			
			//don't like the copy paste here, but getAllContents misses the case when the top-level expression is a VariableExpression
			// using getAllContents at UseEquation causes AlphaExpressions for inputs to be mixed 
			if (expr instanceof VariableExpression) {
				VariableExpression v = (VariableExpression)expr;
				if (!useDefs.containsKey(v.getVariable())) {
					useDefs.put(v.getVariable(), new LinkedList<>());
				}
				useDefs.get(v.getVariable()).add(v);
			}
		}
		
		super.inUseEquation(ue);
	}
	
	@Override
	public void inReduceExpression(ReduceExpression re) {
		ISLSet dom = re.getBody().getZ__internal_cache_contextDom().copy().convexHull().toSet();
		
		for(int i = 0; i < dom.dim(ISLDimType.isl_dim_out); i++) {
			if(!dom.hasUpperBound(ISLDimType.isl_dim_out, i) || !dom.hasLowerBound(ISLDimType.isl_dim_out, i)) {
				issues.add(AlphaIssueFactory.unboundedReductionBody(re));
			}
			dom = dom.eliminate(ISLDimType.isl_dim_out, i, 1);
		}
		
		super.inReduceExpression(re);
	}
}
