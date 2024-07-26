package alpha.model.factory;

import org.eclipse.emf.common.util.EList;

import alpha.model.AlphaExpression;
import alpha.model.ArgReduceExpression;
import alpha.model.AutoRestrictExpression;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.BooleanExpression;
import alpha.model.CaseExpression;
import alpha.model.DependenceExpression;
import alpha.model.ExternalFunction;
import alpha.model.ExternalMultiArgExpression;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.IntegerExpression;
import alpha.model.JNIDomain;
import alpha.model.JNIFunction;
import alpha.model.JNIPolynomial;
import alpha.model.ModelFactory;
import alpha.model.MultiArgExpression;
import alpha.model.PolynomialIndexExpression;
import alpha.model.REDUCTION_OP;
import alpha.model.RealExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.UNARY_OP;
import alpha.model.UnaryExpression;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.util.AlphaPrintingUtil;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;


/**
 * AlphaUserFactory provides a set of convenience methods for creating nodes in Alpha IR.
 * 
 * The bare factory generated from EMF does not give much control or content checks. All
 * nodes created in the Alpha transformations should use methods in this class.  
 * 
 * 
 * @author tyuki
 *
 */
public class AlphaUserFactory {
	private AlphaUserFactory() {}
	
	private static final ModelFactory fact = ModelFactory.eINSTANCE;
	
	private static void nullCheck(Object ... objects) {
		int count = 0;
		for (Object o : objects) {
			if (o == null) throw new RuntimeException(String.format("[AlphaUserFactory] %dth argument is null.", count));
			count++;
		}
	}
	
	public static JNIDomain createJNIDomain(ISLSet set) {
		nullCheck(set);
		
		JNIDomain dom = fact.createJNIDomain();
		dom.setISLSet(set);
		dom.setIslString(AlphaPrintingUtil.toShowString(set));//TODO
		return dom;
	}
	
	public static JNIDomain createJNIParameterDomain(ISLSet set) {
		nullCheck(set);
		
		JNIDomain dom = fact.createJNIDomain();
		dom.setISLSet(set);
		dom.setIslString(AlphaPrintingUtil.toShowStringParameterDomain(set));//TODO
		return dom;
	}
	
	public static JNIDomain createJNISystemBodyDomain(ISLSet set) {
		nullCheck(set);
		
		JNIDomain dom = fact.createJNIDomain();
		dom.setISLSet(set);
		dom.setIslString(AlphaPrintingUtil.toShowStringSystemBodyDomain(set));//TODO
		return dom;
	}
	
	public static  JNIPolynomial createJNIPolynomial(ISLPWQPolynomial pwqp) {
		nullCheck(pwqp);
		
		JNIPolynomial poly = fact.createJNIPolynomial();
		poly.setISLPWQPolynomial(pwqp);
		poly.setIslString(AlphaPrintingUtil.toShowString(pwqp));//TODO
		return poly;
	}
	
	public static  JNIFunction createJNIFunction(ISLMultiAff maff) {
		nullCheck(maff);
		
		JNIFunction fun = fact.createJNIFunction();
		fun.setISLMultiAff(maff);
		return fun;
	}
	
	public static Variable createVariable(String name, ISLSet domain) {
		nullCheck(name, domain);
		
		Variable v = fact.createVariable();
		v.setName(name);
		v.setDomainExpr(createJNIDomain(domain));

		return v;
	}
	
	public static StandardEquation createStandardEquation(Variable v, AlphaExpression expr) {
		nullCheck(v, expr);
		
		StandardEquation eq = fact.createStandardEquation();
		eq.setVariable(v);
		eq.setExpr(expr);
		
		return eq;
	}

	public static DependenceExpression createZeroExpression(ISLSpace space) {
		return createIntegerDependenceExpression(space, 0);
	}
	
	public static DependenceExpression createPositiveOneExpression(ISLSpace space) {
		return createIntegerDependenceExpression(space, 1);
	}
	
	public static DependenceExpression createNegativeOneExpression(ISLSpace space) {
		return createIntegerDependenceExpression(space, -1);
	}
	
	public static DependenceExpression createIntegerDependenceExpression(ISLSpace space, int value) {
		nullCheck(space);
		
		ISLMultiAff maff = ISLUtil.createConstantMaff(space);
		
		DependenceExpression de = createDependenceExpression(maff);
		IntegerExpression ie = createIntegerExpression(value);
		de.setExpr(ie);
		return de;
	}
	
	public static DependenceExpression createDependenceExpression(ISLMultiAff maff) {
		nullCheck(maff);
		
		DependenceExpression de = fact.createDependenceExpression();
		de.setFunctionExpr(createJNIFunction(maff));
		return de;
	}
	
	public static DependenceExpression createDependenceExpression(ISLMultiAff maff, AlphaExpression expr) {
		nullCheck(maff, expr);
		
		DependenceExpression de = createDependenceExpression(maff);
		de.setExpr(expr);
		return de;
	}
	
	public static VariableExpression createVariableExpression(Variable v) {
		nullCheck(v);
		
		VariableExpression ve = fact.createVariableExpression();
		ve.setVariable(v);
		return ve;
	}
	
	public static IntegerExpression createIntegerExpression(int v) {
		IntegerExpression ie = fact.createIntegerExpression();
		ie.setValue(v);
		return ie;
	}
	
	public static RealExpression createRealExpression(float v) {
		RealExpression re = fact.createRealExpression();
		re.setValue(v);
		return re;
	}
	
	public static BooleanExpression createBooleanExpression(boolean v) {
		BooleanExpression be = fact.createBooleanExpression();
		be.setValue(v);
		return be;
	}
	
	public static IndexExpression createIndexExpression(ISLMultiAff maff) {
		nullCheck(maff);
		
		IndexExpression ie = fact.createIndexExpression();
		ie.setFunctionExpr(createJNIFunction(maff));
		return ie;
	}
	
	public static PolynomialIndexExpression createPolynomialIndexExpression(ISLPWQPolynomial pwqp) {
		nullCheck(pwqp);
		
		PolynomialIndexExpression pie = fact.createPolynomialIndexExpression();
		pie.setPolynomialExpr(createJNIPolynomial(pwqp));
		return pie;
	}

	public static RestrictExpression createRestrictExpression(ISLSet dom) {
		nullCheck(dom);
		
		RestrictExpression re = fact.createRestrictExpression();		
		re.setDomainExpr(createJNIDomain(dom));
		return re;
	}
	
	public static RestrictExpression createRestrictExpression(ISLSet dom, AlphaExpression expr) {
		nullCheck(dom, expr);
	
		RestrictExpression re = createRestrictExpression(dom);
		re.setExpr(expr);		
		return re;
	}
	
	public static AutoRestrictExpression createAutoRestrictExpression() {
		AutoRestrictExpression are = fact.createAutoRestrictExpression();		
		
		return are;
	}
	
	public static AutoRestrictExpression createAutoRestrictExpression(AlphaExpression expr) {
		nullCheck(expr);
		
		AutoRestrictExpression are = fact.createAutoRestrictExpression();		
		are.setExpr(expr);
		
		return are;
	}

	public static BinaryExpression createBinaryExpression(BINARY_OP op) {
		nullCheck(op);
		
		BinaryExpression be = fact.createBinaryExpression();
		be.setOperator(op);		
		return be;
	}
	public static BinaryExpression createBinaryExpression(BINARY_OP op, AlphaExpression left, AlphaExpression right) {
		nullCheck(op, left, right);

		BinaryExpression be = createBinaryExpression(op);
		be.setLeft(left);
		be.setRight(right);
		return be;
	}
	
	public static UnaryExpression createUnaryExpression(UNARY_OP op) {
		nullCheck(op);

		UnaryExpression ue = fact.createUnaryExpression();
		ue.setOperator(op);		
		return ue;
	}
	public static UnaryExpression createUnaryExpression(UNARY_OP op, AlphaExpression expr) {
		nullCheck(op, expr);

		UnaryExpression ue = createUnaryExpression(op);
		ue.setExpr(expr);
		return ue;
	}
	
	public static MultiArgExpression createMultiArgExpression(REDUCTION_OP op) {
		nullCheck(op);

		MultiArgExpression mae = fact.createMultiArgExpression();
		mae.setOperator(op);
		return mae;
	}
	public static ExternalMultiArgExpression createExternalMultiArgExpression(ExternalFunction ef) {
		nullCheck(ef);

		ExternalMultiArgExpression mae = fact.createExternalMultiArgExpression();
		mae.setOperator(REDUCTION_OP.EX);
		mae.setExternalFunction(ef);
		return mae;
	}
	
	public static IfExpression createIfExpression() {
		return fact.createIfExpression();		
	}
	public static IfExpression createIfExpression(AlphaExpression condExpr, AlphaExpression thenExpr, AlphaExpression elseExpr) {
		nullCheck(condExpr, thenExpr, elseExpr);

		IfExpression ie = fact.createIfExpression();

		ie.setCondExpr(condExpr);
		ie.setThenExpr(thenExpr);
		ie.setElseExpr(elseExpr);
		
		return ie;
	}
	
	public static CaseExpression createCaseExpression() {
		return fact.createCaseExpression();		
	}
	public static CaseExpression createCaseExpression(String name) {
		nullCheck(name);

		CaseExpression ce = fact.createCaseExpression();
		ce.setName(name);
		return ce;
	}
	
	public static ReduceExpression createReduceExpression(REDUCTION_OP op, ISLMultiAff projection, AlphaExpression expr) {
		nullCheck(op, projection, expr);
		
		ReduceExpression re = fact.createReduceExpression();
		re.setOperator(op);
		re.setProjectionExpr(createJNIFunction(projection));
		re.setBody(expr);
		return re;
	}
	public static ArgReduceExpression createArgReduceExpression(REDUCTION_OP op, ISLMultiAff projection, AlphaExpression expr) {
		nullCheck(op, projection, expr);
		
		ArgReduceExpression re = fact.createArgReduceExpression();
		re.setOperator(op);
		re.setProjectionExpr(createJNIFunction(projection));
		re.setBody(expr);
		return re;
	}
	
	public static BinaryExpression createNegatedExpression(AlphaExpression ae) {
		nullCheck(ae);
		
		DependenceExpression de = createNegativeOneExpression(ae.getContextDomain().getSpace());

		BinaryExpression be = createBinaryExpression(BINARY_OP.MUL);
		be.setLeft(de);
		be.setRight(ae);
		
		return be;
	}
	
	/** Given the expression ae, return prod(1[], ae) */
	public static MultiArgExpression createIdentityProdExpression(AlphaExpression ae) {
		nullCheck(ae);
		
		DependenceExpression de = createPositiveOneExpression(ae.getContextDomain().getSpace());
		
		MultiArgExpression mae = createMultiArgExpression(REDUCTION_OP.PROD);
		EList<AlphaExpression> exprs = mae.getExprs();
		exprs.add(de);
		exprs.add(ae);
		
		return mae;
	}
}
