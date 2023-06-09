package alpha.model.util

import alpha.model.AlphaConstant
import alpha.model.AlphaExpression
import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaSystem
import alpha.model.AlphaVisitable
import alpha.model.ArgReduceExpression
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryExpression
import alpha.model.ConstantExpression
import alpha.model.ConvolutionExpression
import alpha.model.DependenceExpression
import alpha.model.ExternalArgReduceExpression
import alpha.model.ExternalFunction
import alpha.model.ExternalMultiArgExpression
import alpha.model.ExternalReduceExpression
import alpha.model.FuzzyDependenceExpression
import alpha.model.FuzzyIndexExpression
import alpha.model.FuzzyVariable
import alpha.model.IndexExpression
import alpha.model.PolynomialIndexExpression
import alpha.model.MultiArgExpression
import alpha.model.PolyhedralObject
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.SelectExpression
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.UnaryExpression
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.VariableExpression
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.EObject

/**
 * PrintAST is a dump of the Alpha program, mainly used for debugging.
 * 
 */
class PrintAST extends AbstractAlphaCompleteVisitor {
	
	String indent = "";
	protected static final String INDENT_WITH_SIBILING = "   |";
	protected static final String INDENT_LAST_CHILD = "    ";
	protected StringBuffer _output = new StringBuffer;
	
	protected List<Integer> nodeIdTracker = new LinkedList; 
	protected int depth = 0; 
	
	static def dispatch String print(AlphaVisitable node) {
		val printer = new PrintAST
		node.accept(printer);
		return printer._output.toString();
	}
	static def dispatch String print(AlphaExpressionVisitable node) {
		val printer = new PrintAST
		node.accept(printer);
		return printer._output.toString();
	}
	
	protected def printInt(String prefix, long v) {
		printStr(prefix, v+"");
	}
	
	protected def printReal(String prefix, double v) {
		printStr(prefix, v+"");
	}
	
	protected def printStr(Object... objs) {
		_output.append(indent)
		objs.forEach[o|_output.append(o)]
		_output.append("\n") 
	}
	
//	protected def nodeTrackIn() {
//		//depth starts from 1 when starting from system
//		depth++;
//		if (nodeIdTracker.size() < depth) {
//			nodeIdTracker.add(0);
//		} else {
//			nodeIdTracker.set(depth-1, nodeIdTracker.get(depth-1)+1);	
//		}
//		printId();
//	}
//	
//	protected def nodeTrackOut() {
//		depth--;
//		if (nodeIdTracker.size() > depth+1) {
//			nodeIdTracker.set(depth+1, -1);
//		}
//	}
	
	protected def printId() {
		val id = new StringBuffer();
		for (i : 0 ..< depth) {
			if (id.length() > 0) {
				id.append(",");
			}
			id.append(nodeIdTracker.get(i));
		}
		_output.append(indent + "nodeId = ("+id+")\n"); 
	}

	override defaultIn(AlphaVisitable av) {
		defaultInImpl(av);
	}
	
	override defaultIn(AlphaExpressionVisitable aev) {
		defaultInImpl(aev);
	}

	private dispatch def defaultInImpl(EObject obj) {
		printStr("_", obj.eClass.name)
		indent += INDENT_WITH_SIBILING;
	}
	
	private dispatch def defaultInImpl(AlphaExpression obj) {
		printStr("_", obj.eClass.name + " ID:" + obj.expressionID)
		indent += INDENT_WITH_SIBILING;
	}
	
	override defaultOut(AlphaVisitable av) {
		_defaultOut(av);
	}
	
	override defaultOut(AlphaExpressionVisitable aev) {
		_defaultOut(aev);
	}

	private def _defaultOut(EObject obj) {
		indent = indent.substring(0, indent.length() - INDENT_WITH_SIBILING.length());
	}
	
	override inAlphaSystem(AlphaSystem system) {
		defaultIn(system);
		printStr("_", system.getName());
	}
	
	override outAlphaSystem(AlphaSystem system) {
		defaultOut(system);
	}
	
	override inSystemBody(SystemBody sysBody) {
		defaultIn(sysBody);
		if (sysBody.parameterDomainExpr !== null) {
			printStr("_", sysBody.parameterDomain);
		} else {
			printStr("_", "else");
		}
	}
	
	override outSystemBody(SystemBody sysBody) {
		defaultOut(sysBody);
	}

	override inExternalFunction(ExternalFunction ef) {
		defaultIn(ef);
		printStr("+--", ef.name, "(", ef.cardinality+")")
	}
	
	override visitAlphaConstant(AlphaConstant ac) {
		defaultIn(ac)
		printStr("+--", ac.name, "=", ac.value+"")
	}
	
	override outVariable(Variable v) {
		defaultIn(v);
		printStr("+-- ", v.getName());
		printStr("+-- ", v.domain);
		defaultOut(v);
	}
	
	override outFuzzyVariable(FuzzyVariable v) {
		defaultIn(v);
		printStr("+-- ", v.getName());
		printStr("+-- ", v.domain);
		printStr("+-- ", v.range);
		defaultOut(v);
	}
	
	override visitPolyhedralObject(PolyhedralObject pobj) {
		defaultIn(pobj);
		printStr("+-- ", pobj.getName());
		printStr("+-- ", pobj.type.name());
		printStr("+-- ", pobj.ISLObject);
		defaultOut(pobj);
	}
	
	override inStandardEquation(StandardEquation se) {
		defaultIn(se)
		printStr("+-- ", se.getVariable().getName())
	}
	
	override inUseEquation(UseEquation ue) {
		defaultIn(ue)
		printStr("+-- ", ue.system.name)
		if (ue.instantiationDomain !== null)
			printStr("+-- ", ue.instantiationDomain)
	}
	
	override inAlphaExpression(AlphaExpression ae) {
		defaultIn(ae)
		printStr("+-- ", "expDomain: ", ae.expressionDomain)
		printStr("+-- ", "ctxDomain: ",  ae.contextDomain)
	}
	
	override outAlphaExpression(AlphaExpression ae) {
		defaultOut(ae)
	}
	
	override inRestrictExpression(RestrictExpression re) {
		inAlphaExpression(re)
		printStr("+-- ", re.domainExpr.ISLObject);
	}
	
	override inAutoRestrictExpression(AutoRestrictExpression are) {
		inAlphaExpression(are)
		if (are.inferredDomain !== null)
			printStr("+-- ", are.inferredDomain);
	}
	
	override inDependenceExpression(DependenceExpression de) {
		inAlphaExpression(de)
		printStr("+-- ", de.function + "; " + AlphaPrintingUtil.toShowString(de.function));
	}
	
	override inFuzzyDependenceExpression(FuzzyDependenceExpression fde) {
		inAlphaExpression(fde)
		printStr("+-- ", fde.dependenceRelation);
		printStr("+-- ", fde.dependenceRelation.getDomain());
	}
	
	override inVariableExpression(VariableExpression ve) {
		inAlphaExpression(ve)
		printStr("+-- ", ve.variable.name);
	}
	
	override inIndexExpression(IndexExpression ie) {
		inAlphaExpression(ie)
		printStr("+-- ", ie.function);
	}
	
	override inPolynomialIndexExpression(PolynomialIndexExpression pie) {
		inAlphaExpression(pie)
		printStr("+-- ", pie.polynomial);
	}
	
	override inFuzzyIndexExpression(FuzzyIndexExpression fie) {
		inAlphaExpression(fie)
		printStr("+-- ", fie.dependenceRelation);
	}
	
	override inConstantExpression(ConstantExpression ce) {
		inAlphaExpression(ce)
		printStr("+-- ", ce.valueString);
	}
	
	override inUnaryExpression(UnaryExpression ue) {
		inAlphaExpression(ue)
		printStr("+-- ", ue.operator);
	}
	
	override inBinaryExpression(BinaryExpression be) {
		inAlphaExpression(be)
		printStr("+-- ", be.operator);
	}
	
	override inReduceExpression(ReduceExpression re) {
		inAlphaExpression(re)
		printStr("+-- ", re.operator);
		printStr("+-- ", re.projection);
	}
	
	override inExternalReduceExpression(ExternalReduceExpression ere) {
		inAlphaExpression(ere)
		printStr("+-- ", ere.externalFunction.name);
		printStr("+-- ", ere.projection);
	}
	
	override inArgReduceExpression(ArgReduceExpression are) {
		inAlphaExpression(are)
		printStr("+-- ", are.operator);
		printStr("+-- ", are.projection);
	}
	
	override inExternalArgReduceExpression(ExternalArgReduceExpression eare) {
		inAlphaExpression(eare)
		printStr("+-- ", eare.operator);
		printStr("+-- ", eare.projection);
	}
	
	override inConvolutionExpression(ConvolutionExpression ce) {
		inAlphaExpression(ce)
		printStr("+-- ", ce.kernelDomain);
	}
	
	override inMultiArgExpression(MultiArgExpression mae) {
		inAlphaExpression(mae)
		printStr("+-- ", mae.operator);
	}
	
	override inExternalMultiArgExpression(ExternalMultiArgExpression emae) {
		inAlphaExpression(emae)
		printStr("+-- ", emae.externalFunction.name);
	}
	
	override inSelectExpression(SelectExpression se) {
		inAlphaExpression(se)
		printStr("+-- ", se.selectRelation);
	}
	 
	
//	override visitRestrictExpression(RestrictExpression re) {
//		defaultIn(re);
//		nodeTrackIn()
//		super.visitRestrictExpression(re)
//		nodeTrackOut()
//		defaultOut(re);
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}