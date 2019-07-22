package alpha.model;

import static alpha.model.util.AlphaUtil.callISLwithErrorHandling;

import java.util.LinkedList;
import java.util.List;

import alpha.model.issue.AlphaIssue;
import alpha.model.issue.AlphaIssueFactory;
import alpha.model.issue.UnexpectedISLErrorIssue;
import alpha.model.util.AbstractAlphaExpressionVisitor;
import alpha.model.util.AlphaExpressionUtil;
import alpha.model.util.AlphaUtil;
import fr.irisa.cairn.jnimap.isl.ISLSet;

/**
 * Computes the expression domain for AlphaExpressions. The expression domains
 * define the set where the given expression can be evaluated. It starts from 
 * the leafs of the expression tree:
 *   - ConstantExpression -> defined over 0-dim universe
 *   - IndexExpression -> defined over the (universe) domain of the index function
 *   - VariableExpression -> defined over the variable domain 
 * 
 *  Then the expression domain is computed bottom-up with notably the following:
 *   - DependenceExpression takes the pre-image of its child
 *   - CaseExpression takes union of its children
 *   - Binary/Multi-Arg operations take the intersection of its children
 *   - ReduceExpression takes the image of its body by the projection
 * 
 * @author tyuki
 *
 */
public class ExpressionDomainCalculator extends AbstractAlphaExpressionVisitor {
	
	private List<AlphaIssue> issues = new LinkedList<>();
	
	/**
	 * Recomputes the expression domain for all AlphaExpressions in the sub-tree rooted 
	 * by the given node. 
	 * 
	 * This method is a dispatcher for two possible instances of AlphaCompleteVisitable
	 * 
	 * @param expr
	 * @return
	 */
	public static List<AlphaIssue> calculate(AlphaCompleteVisitable expr) {
		if (expr instanceof AlphaVisitable) {
			return calculate((AlphaVisitable)expr);
		} else if (expr instanceof AlphaExpressionVisitable) {
			return calculate((AlphaExpressionVisitable)expr);
		}
		
		throw new RuntimeException("This should be unreachable code. Got: " + expr);
	}
	
	/**
	 * ExpressionDomainCalculator is a AlphaExpressionVisitor. 
	 * 
	 * @param node
	 * @return
	 */
	protected static List<AlphaIssue> calculate(AlphaVisitable node) {
		ExpressionDomainCalculator calc = new ExpressionDomainCalculator();
		
		calc.visit(node);
		
		return calc.issues;
	}
	
	protected static List<AlphaIssue> calculate(AlphaExpressionVisitable expr) {
		ExpressionDomainCalculator calc = new ExpressionDomainCalculator();
		
		expr.accept(calc);
		
		return calc.issues;
	}
	
	private void registerIssue(String islErr, AlphaNode node) {
		issues.add(new UnexpectedISLErrorIssue(islErr, node.eContainer(), node.eContainingFeature()));
	}
	
	private boolean checkCalcExprType(CalculatorExpression cexpr, POLY_OBJECT_TYPE expected) {
		if (cexpr.getType() != expected) {
			issues.add(AlphaIssueFactory.unmatchedCalcExprType(cexpr, expected));
			return false;
		}
		
		return true;
	}
	
	@Override
	public void outBinaryExpression(BinaryExpression be) {
		//Expression domain of a binary expression is the intersection of the two
		runISLoperations(be, ()->{
				be.setExpressionDomain(be.getLeft().getExpressionDomain().intersect(be.getRight().getExpressionDomain()));
			});
	}
	
	@Override
	public void outCaseExpression(CaseExpression ce) {
		runISLoperations(ce, ()->{
				ISLSet union = ce.getExprs().stream().map(a->a.getExpressionDomain())
						.reduce((a,b)->a.union(b)).get();
					ce.setExpressionDomain(union);
			});
	}
	
	@Override
	public void outConstantExpression(ConstantExpression ce) {
		ce.setExpressionDomain(AlphaUtil.getScalarDomain(ce));
	}
	
	@Override
	public void outDependenceExpression(DependenceExpression de) {
		if (de.getFunction() != null &&  de.getExpr().getExpressionDomain() != null) {
			if (de.getFunction().getNbOutputs() != de.getExpr().getExpressionDomain().getNbIndices()) {
				issues.add(AlphaIssueFactory.incompatibleContextAndExpressionDomain(de));
				return;
			}
			runISLoperations(de, ()->{
				ISLSet set = de.getExpr().getExpressionDomain().preimage(de.getFunction());
				de.setExpressionDomain(set);
			});
		}
	}
	
	@Override
	public void outIfExpression(IfExpression ie) {
		runISLoperations(ie, ()->{
			ISLSet set = ie.getCondExpr().getExpressionDomain();
			set = set.intersect(ie.getThenExpr().getExpressionDomain());
			set = set.intersect(ie.getElseExpr().getExpressionDomain());
			ie.setExpressionDomain(set);
		});
	}
	
	@Override
	public void outIndexExpression(IndexExpression ie) {
		if (ie.getFunction() != null)
			runISLoperations(ie, ()->{
				ie.setExpressionDomain(ISLSet.buildUniverse(ie.getFunction().getDomainSpace()));
			});
	}
	
	@Override
	public void outPolynomialIndexExpression(PolynomialIndexExpression pie) {
		if (pie.getPolynomial() != null)
			runISLoperations(pie, ()->{
				pie.setExpressionDomain(ISLSet.buildUniverse(pie.getPolynomial().getDomainSpace()));
			});
	}
	
	@Override
	public void outFuzzyIndexExpression(FuzzyIndexExpression fie) {
		if (fie.getDependenceRelation() != null)
			runISLoperations(fie, ()->{
				fie.setExpressionDomain(ISLSet.buildUniverse(fie.getDependenceRelation().getDomain().getSpace()));
			});
	}
	
	@Override
	public void outMultiArgExpression(MultiArgExpression mae) {
		runISLoperations(mae, ()->{
				ISLSet intersection = mae.getExprs().stream().map(e->e.getExpressionDomain())
				.reduce((a,b)->a.intersect(b)).get();
				mae.setExpressionDomain(intersection);
		});
	}
	
	@Override
	public void outAbstractReduceExpression(AbstractReduceExpression re) {
		runISLoperations(re, ()->{
			ISLSet proj = re.getBody().getExpressionDomain().apply(re.getProjection().toMap());
			re.setExpressionDomain(proj);
		});
	}
	
	@Override
	public void outConvolutionExpression(ConvolutionExpression ce) {
		if (ce.getKernelDomain() != null && ce.getKernelDomain().getNbBasicSets() != 1)
			throw new RuntimeException("Only a single basic set is allowed for convolution kernels.");

		runISLoperations(ce, ()->{
			ISLSet kernelExprDom = AlphaExpressionUtil.preimageByConvolutionDependences(
					ce.getKernelDomain().getBasicSetAt(0), 
					ce.getKernelExpression().getExpressionDomain());
			ISLSet dataExprDom = AlphaExpressionUtil.preimageByConvolutionDependences(
					ce.getKernelDomain().getBasicSetAt(0), 
					ce.getDataExpression().getExpressionDomain());
			ce.setExpressionDomain(kernelExprDom.intersect(dataExprDom));
		});


	}
	
	@Override
	public void outRestrictExpression(RestrictExpression re) {
		if (checkCalcExprType(re.getDomainExpr(), POLY_OBJECT_TYPE.SET) && re.getRestrictDomain() != null)
			runISLoperations(re, ()->{
				re.setExpressionDomain(re.getExpr().getExpressionDomain().intersect(re.getRestrictDomain()));
			});
	}

	@Override
	public void outUnaryExpression(UnaryExpression ue) {
		runISLoperations(ue, ()->{
			ue.setExpressionDomain(ue.getExpr().getExpressionDomain());
		});
	}
	
	@Override
	public void outVariableExpression(VariableExpression ve) {
		if (ve.getVariable().getDomain() != null)
			ve.setExpressionDomain(ve.getVariable().getDomain());
	}

	@Override
	public void outSelectExpression(SelectExpression se) {
		if (se.getSelectRelation() != null)
			runISLoperations(se, ()->{
				ISLSet set = se.getExpr().getExpressionDomain().apply(se.getSelectRelation().reverse());
				se.setExpressionDomain(set);
			});
	}

	@Override
	public void outAutoRestrictExpression(AutoRestrictExpression are) {
		runISLoperations(are, ()->{
			ISLSet set = are.getExpr().getExpressionDomain();
			are.setExpressionDomain(set);
		});
	}
//	void outConvolutionExpression(ConvolutionExpression ce);
	
	
	private void runISLoperations(AlphaExpression expr, Runnable r) {
		if (AlphaExpressionUtil.testNonNullExpressionDomain(AlphaExpressionUtil.getChildrenOfType(expr, AlphaExpression.class))) 
				callISLwithErrorHandling(r, (err)->registerIssue(err, expr));
	}
}
