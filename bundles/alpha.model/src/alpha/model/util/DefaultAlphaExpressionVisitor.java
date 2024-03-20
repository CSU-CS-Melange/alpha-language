package alpha.model.util;

import java.util.LinkedList;
import java.util.List;

import alpha.model.AbstractFuzzyReduceExpression;
import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaExpressionVisitor;
import alpha.model.AlphaVisitable;
import alpha.model.ArgReduceExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.BooleanExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.ConvolutionExpression;
import alpha.model.DependenceExpression;
import alpha.model.ExternalArgReduceExpression;
import alpha.model.ExternalFuzzyArgReduceExpression;
import alpha.model.ExternalFuzzyReduceExpression;
import alpha.model.ExternalMultiArgExpression;
import alpha.model.ExternalReduceExpression;
import alpha.model.FuzzyArgReduceExpression;
import alpha.model.FuzzyDependenceExpression;
import alpha.model.FuzzyIndexExpression;
import alpha.model.FuzzyReduceExpression;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.IntegerExpression;
import alpha.model.MultiArgExpression;
import alpha.model.PolynomialIndexExpression;
import alpha.model.RealExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.SelectExpression;
import alpha.model.StandardEquation;
import alpha.model.UnaryExpression;
import alpha.model.UseEquation;
import alpha.model.VariableExpression;

public interface DefaultAlphaExpressionVisitor extends AlphaExpressionVisitor {

	default void defaultIn(AlphaExpressionVisitable expr) {}
	default void defaultOut(AlphaExpressionVisitable expr) {}
	
	/**
	 * Visit only the AlphaExpressions of any AlphaVisitable.
	 * 
	 * @param node
	 */
	default void visit(AlphaVisitable node) {
		node.accept(new AbstractAlphaVisitor() {
			@Override
			public void visitStandardEquation(StandardEquation se) {
				se.getExpr().accept(DefaultAlphaExpressionVisitor.this);
			}
			
			@Override
			public void visitUseEquation(UseEquation ue) {
				//copy to avoid concurrent mods
				List<AlphaExpression> copy = new LinkedList<>();
				copy.addAll(ue.getInputExprs());
				copy.addAll(ue.getOutputExprs());
				copy.forEach(a->a.accept(DefaultAlphaExpressionVisitor.this));
			}
		});
	}
	
	/*
	 * Helper to avoid repeating null check all over the place.
	 */
	default void accept(AlphaExpressionVisitable expr) {
		if (expr != null) expr.accept(this);
	}
	
	@Override
	default void visitAlphaExpression(AlphaExpression ae) {
		throw new UnsupportedOperationException("visitAlphaExpression should never be called.");
	}
	
	@Override
	default void visitRestrictExpression(RestrictExpression re) {
		inRestrictExpression(re);
		accept(re.getExpr());
		outRestrictExpression(re);
	}

	@Override
	default void visitAutoRestrictExpression(AutoRestrictExpression are) {
		inAutoRestrictExpression(are);
		accept(are.getExpr());
		outAutoRestrictExpression(are);
	}

	@Override
	default void visitCaseExpression(CaseExpression ce) {
		inCaseExpression(ce);
		if (ce.getExprs() != null) {
			//copy to avoid concurrent mods
			List<AlphaExpression> copy = new LinkedList<>(ce.getExprs());
			copy.forEach(a->accept(a));
		}
		outCaseExpression(ce);
	}

	@Override
	default void visitIfExpression(IfExpression ie) {
		inIfExpression(ie);
		accept(ie.getCondExpr());
		accept(ie.getThenExpr());
		accept(ie.getElseExpr());
		outIfExpression(ie);
	}

	@Override
	default void visitDependenceExpression(DependenceExpression de) {
		inDependenceExpression(de);
		accept(de.getExpr());
		outDependenceExpression(de);
	}

	@Override
	default void visitFuzzyDependenceExpression(FuzzyDependenceExpression fde) {
		inFuzzyDependenceExpression(fde);
		accept(fde.getExpr());
		outFuzzyDependenceExpression(fde);
	}
	
	@Override
	default void visitAbstractReduceExpression(AbstractReduceExpression are) {
		if (are instanceof ExternalReduceExpression)
			inExternalReduceExpression((ExternalReduceExpression)are);
		else if (are instanceof ExternalArgReduceExpression)
			inExternalArgReduceExpression((ExternalArgReduceExpression)are);
		else if (are instanceof ReduceExpression)
			inReduceExpression((ReduceExpression)are);
		else if (are instanceof ArgReduceExpression)
			inArgReduceExpression((ArgReduceExpression)are);
		else
			inAbstractReduceExpression(are);
		
		accept(are.getBody());

		if (are instanceof ExternalReduceExpression)
			outExternalReduceExpression((ExternalReduceExpression)are);
		else if (are instanceof ExternalArgReduceExpression)
			outExternalArgReduceExpression((ExternalArgReduceExpression)are);
		else if (are instanceof ReduceExpression)
			outReduceExpression((ReduceExpression)are);
		else if (are instanceof ArgReduceExpression)
			outArgReduceExpression((ArgReduceExpression)are);
		else
			outAbstractReduceExpression(are);
	}
	
	@Override
	default void visitAbstractFuzzyReduceExpression(AbstractFuzzyReduceExpression afre) {
		if (afre instanceof ExternalReduceExpression)
			inExternalFuzzyReduceExpression((ExternalFuzzyReduceExpression)afre);
		else if (afre instanceof ExternalArgReduceExpression)
			inExternalFuzzyArgReduceExpression((ExternalFuzzyArgReduceExpression)afre);
		else if (afre instanceof FuzzyReduceExpression)
			inFuzzyReduceExpression((FuzzyReduceExpression)afre);
		else if (afre instanceof FuzzyArgReduceExpression)
			inFuzzyArgReduceExpression((FuzzyArgReduceExpression)afre);
		else
			inAbstractFuzzyReduceExpression(afre);
		
		accept(afre.getBody());

		if (afre instanceof ExternalReduceExpression)
			outExternalFuzzyReduceExpression((ExternalFuzzyReduceExpression)afre);
		else if (afre instanceof ExternalArgReduceExpression)
			outExternalFuzzyArgReduceExpression((ExternalFuzzyArgReduceExpression)afre);
		else if (afre instanceof FuzzyReduceExpression)
			outFuzzyReduceExpression((FuzzyReduceExpression)afre);
		else if (afre instanceof FuzzyArgReduceExpression)
			outFuzzyArgReduceExpression((FuzzyArgReduceExpression)afre);
		else
			outAbstractFuzzyReduceExpression(afre);
	}
	
	@Override
	default void visitConvolutionExpression(ConvolutionExpression ce) {
		inConvolutionExpression(ce);
		accept(ce.getKernelExpression());
		accept(ce.getDataExpression());
		outConvolutionExpression(ce);
	}
	
	@Override
	default void visitUnaryExpression(UnaryExpression ue) {
		inUnaryExpression(ue);
		accept(ue.getExpr());
		outUnaryExpression(ue);
	}

	@Override
	default void visitBinaryExpression(BinaryExpression be) {
		inBinaryExpression(be);
		accept(be.getLeft());
		accept(be.getRight());
		outBinaryExpression(be);
	}

	@Override
	default void visitMultiArgExpression(MultiArgExpression mae) {
		if (mae instanceof ExternalMultiArgExpression)
			inExternalMultiArgExpression((ExternalMultiArgExpression)mae);
		else
			inMultiArgExpression(mae);
		
		if (mae.getExprs() != null) {
			//copy to avoid concurrent mods
			List<AlphaExpression> copy = new LinkedList<>(mae.getExprs());
			copy.forEach(a->accept(a));
		}
		
		if (mae instanceof ExternalMultiArgExpression)
			outExternalMultiArgExpression((ExternalMultiArgExpression)mae);
		else
			outMultiArgExpression(mae);
	}
	
	@Override
	default void visitSelectExpression(SelectExpression se) {
		inSelectExpression(se);
		accept(se.getExpr());
		outSelectExpression(se);
	}

	@Override
	default void visitIndexExpression(IndexExpression ie) {
		inIndexExpression(ie);
		outIndexExpression(ie);
	}
	
	@Override
	default void visitPolynomialIndexExpression(PolynomialIndexExpression pie) {
		inPolynomialIndexExpression(pie);
		outPolynomialIndexExpression(pie);
	}

	@Override
	default void visitFuzzyIndexExpression(FuzzyIndexExpression fie) {
		inFuzzyIndexExpression(fie);
		outFuzzyIndexExpression(fie);
	}


	@Override
	default void visitVariableExpression(VariableExpression ve) {
		inVariableExpression(ve);
		outVariableExpression(ve);
	}

	@Override
	default void visitIntegerExpression(IntegerExpression ie) {
		inIntegerExpression(ie);
		outIntegerExpression(ie);
	}

	@Override
	default void visitRealExpression(RealExpression re) {
		inRealExpression(re);
		outRealExpression(re);
	}

	@Override
	default void visitBooleanExpression(BooleanExpression be) {
		inBooleanExpression(be);
		outBooleanExpression(be);
	}
	
	@Override
	default void inAlphaExpression(AlphaExpression ae) {
		defaultIn(ae);
	}

	@Override
	default void inRestrictExpression(RestrictExpression re) {
		inAlphaExpression(re);
	}

	@Override
	default void inAutoRestrictExpression(AutoRestrictExpression are) {
		inAlphaExpression(are);
	}

	@Override
	default void inCaseExpression(CaseExpression ce) {
		inAlphaExpression(ce);
	}

	@Override
	default void inIfExpression(IfExpression ie) {
		inAlphaExpression(ie);
	}

	@Override
	default void inDependenceExpression(DependenceExpression de) {
		inAlphaExpression(de);
	}

	@Override
	default void inFuzzyDependenceExpression(FuzzyDependenceExpression fde) {
		inAlphaExpression(fde);
	}

	@Override
	default void inAbstractReduceExpression(AbstractReduceExpression are) {
		inAlphaExpression(are);
	}
	
	@Override
	default void inReduceExpression(ReduceExpression re) {
		inAbstractReduceExpression(re);
	}

	@Override
	default void inArgReduceExpression(ArgReduceExpression are) {
		inAbstractReduceExpression(are);
	}

	@Override
	default void inAbstractFuzzyReduceExpression(AbstractFuzzyReduceExpression afre) {
		inAlphaExpression(afre);
	}
	
	@Override
	default void inFuzzyReduceExpression(FuzzyReduceExpression fre) {
		inAbstractFuzzyReduceExpression(fre);
	}

	@Override
	default void inFuzzyArgReduceExpression(FuzzyArgReduceExpression afre) {
		inAbstractFuzzyReduceExpression(afre);
	}


	@Override
	default void inConvolutionExpression(ConvolutionExpression ce) {
		inAlphaExpression(ce);
	}

	@Override
	default void inUnaryExpression(UnaryExpression ue) {
		inAlphaExpression(ue);
	}

	@Override
	default void inBinaryExpression(BinaryExpression be) {
		inAlphaExpression(be);
	}

	@Override
	default void inMultiArgExpression(MultiArgExpression mae) {
		inAlphaExpression(mae);
	}

	@Override
	default void inSelectExpression(SelectExpression se) {
		inAlphaExpression(se);
	}

	@Override
	default void inIndexExpression(IndexExpression ie) {
		inAlphaExpression(ie);
	}

	@Override
	default void inPolynomialIndexExpression(PolynomialIndexExpression pie) {
		inAlphaExpression(pie);
	}
	
	@Override
	default void inFuzzyIndexExpression(FuzzyIndexExpression fie) {
		inAlphaExpression(fie);
	}

	@Override
	default void inVariableExpression(VariableExpression ve) {
		inAlphaExpression(ve);
	}

	@Override
	default void inConstantExpression(ConstantExpression ce) {
		inAlphaExpression(ce);
	}

	@Override
	default void inIntegerExpression(IntegerExpression ie) {
		inConstantExpression(ie);
	}

	@Override
	default void inRealExpression(RealExpression re) {
		inConstantExpression(re);
	}

	@Override
	default void inBooleanExpression(BooleanExpression be) {
		inConstantExpression(be);
	}

	@Override
	default void inExternalReduceExpression(ExternalReduceExpression ere) {
		inReduceExpression(ere);
	}

	@Override
	default void inExternalArgReduceExpression(ExternalArgReduceExpression eare) {
		inArgReduceExpression(eare);
	}

	@Override
	default void inExternalMultiArgExpression(ExternalMultiArgExpression emae) {
		inMultiArgExpression(emae);
	}

	@Override
	default void inExternalFuzzyReduceExpression(ExternalFuzzyReduceExpression efre) {
		inFuzzyReduceExpression(efre);
	}

	@Override
	default void inExternalFuzzyArgReduceExpression(ExternalFuzzyArgReduceExpression efare) {
		inFuzzyArgReduceExpression(efare);
	}

	@Override
	default void outAlphaExpression(AlphaExpression ae) {
		defaultOut(ae);
	}

	@Override
	default void outRestrictExpression(RestrictExpression re) {
		outAlphaExpression(re);
	}

	@Override
	default void outAutoRestrictExpression(AutoRestrictExpression are) {
		outAlphaExpression(are);
	}

	@Override
	default void outCaseExpression(CaseExpression ce) {
		outAlphaExpression(ce);
	}

	@Override
	default void outIfExpression(IfExpression ie) {
		outAlphaExpression(ie);
	}

	@Override
	default void outDependenceExpression(DependenceExpression de) {
		outAlphaExpression(de);
	}
	
	@Override
	default void outFuzzyDependenceExpression(FuzzyDependenceExpression fde) {
		outAlphaExpression(fde);
	}
	
	@Override
	default void outAbstractReduceExpression(AbstractReduceExpression are) {
		outAlphaExpression(are);
	}

	@Override
	default void outReduceExpression(ReduceExpression re) {
		outAbstractReduceExpression(re);
	}

	@Override
	default void outArgReduceExpression(ArgReduceExpression are) {
		outAbstractReduceExpression(are);
	}
	
	@Override
	default void outAbstractFuzzyReduceExpression(AbstractFuzzyReduceExpression afre) {
		outAlphaExpression(afre);
	}

	@Override
	default void outFuzzyReduceExpression(FuzzyReduceExpression fre) {
		outAbstractFuzzyReduceExpression(fre);
	}

	@Override
	default void outFuzzyArgReduceExpression(FuzzyArgReduceExpression afre) {
		outAbstractFuzzyReduceExpression(afre);
	}

	@Override
	default void outConvolutionExpression(ConvolutionExpression ce) {
		outAlphaExpression(ce);
	}

	@Override
	default void outUnaryExpression(UnaryExpression ue) {
		outAlphaExpression(ue);
	}

	@Override
	default void outBinaryExpression(BinaryExpression be) {
		outAlphaExpression(be);
	}

	@Override
	default void outMultiArgExpression(MultiArgExpression mae) {
		outAlphaExpression(mae);
	}

	@Override
	default void outSelectExpression(SelectExpression se) {
		outAlphaExpression(se);
	}

	@Override
	default void outIndexExpression(IndexExpression ie) {
		outAlphaExpression(ie);
	}

	@Override
	default void outPolynomialIndexExpression(PolynomialIndexExpression pie) {
		outAlphaExpression(pie);
	}
	
	@Override
	default void outFuzzyIndexExpression(FuzzyIndexExpression fie) {
		outAlphaExpression(fie);
	}

	@Override
	default void outVariableExpression(VariableExpression ve) {
		outAlphaExpression(ve);
	}

	@Override
	default void outConstantExpression(ConstantExpression ce) {
		outAlphaExpression(ce);
	}

	@Override
	default void outIntegerExpression(IntegerExpression ie) {
		outConstantExpression(ie);
	}

	@Override
	default void outRealExpression(RealExpression re) {
		outConstantExpression(re);
	}

	@Override
	default void outBooleanExpression(BooleanExpression be) {
		outConstantExpression(be);
	}

	@Override
	default void outExternalReduceExpression(ExternalReduceExpression ere) {
		outReduceExpression(ere);
	}

	@Override
	default void outExternalArgReduceExpression(ExternalArgReduceExpression eare) {
		outArgReduceExpression(eare);
	}

	@Override
	default void outExternalMultiArgExpression(ExternalMultiArgExpression emae) {
		outMultiArgExpression(emae);
	}

	@Override
	default void outExternalFuzzyReduceExpression(ExternalFuzzyReduceExpression efre) {
		outFuzzyReduceExpression(efre);
	}

	@Override
	default void outExternalFuzzyArgReduceExpression(ExternalFuzzyArgReduceExpression efare) {
		outFuzzyArgReduceExpression(efare);
	}
}
