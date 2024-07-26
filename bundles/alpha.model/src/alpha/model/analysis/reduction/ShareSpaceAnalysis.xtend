package alpha.model.analysis.reduction

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaExpression
import alpha.model.AlphaExpressionVisitable
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryExpression
import alpha.model.CaseExpression
import alpha.model.ConstantExpression
import alpha.model.DependenceExpression
import alpha.model.IfExpression
import alpha.model.IndexExpression
import alpha.model.MultiArgExpression
import alpha.model.RestrictExpression
import alpha.model.UnaryExpression
import alpha.model.VariableExpression
import alpha.model.matrix.MatrixOperations
import alpha.model.util.AbstractAlphaExpressionVisitor
import alpha.model.util.AffineFunctionOperations
import alpha.model.util.AlphaUtil
import alpha.model.util.DomainOperations
import java.util.Arrays
import java.util.Comparator
import java.util.List
import java.util.Map
import java.util.TreeMap
import fr.irisa.cairn.jnimap.isl.ISLDimType

/**
 * Share space is the sub space spanned by set of operations that reuse the same value.
 * See Section 5.2 of the Simplifying Reductions paper for details.
 * 
 * This implementation is incomplete:
 *  - All variables (not just inputs) are assumed to have empty share space.
 *  It is not difficult to compute share space of other equations as necessary,
 *  but this is currently not implemented. 
 *  - The share space of a reduction is assumed to be empty. It does check
 *  if the body of a reduction has non-empty share space before projection,
 *  but does not perform the projection and issues a warning. This case
 *  should not be encountered unless the expression domain is unbounded.
 */
class ShareSpaceAnalysis extends AbstractAlphaExpressionVisitor {


	protected Map<AlphaExpression, long[][]> shareSpace;
	
	private new () { shareSpace = new TreeMap<AlphaExpression, long[][]>( new Comparator<AlphaExpression>() {
		
		override compare(AlphaExpression o1, AlphaExpression o2) {
			val idcmp = o1.expressionID.toString.compareTo(o2.expressionID.toString)
			if (idcmp != 0) return idcmp
			
			val eq1 = AlphaUtil.getContainerEquation(o1);
			val body1 = eq1.systemBody
			
			val eq2 = AlphaUtil.getContainerEquation(o2);
			val body2 = eq2.systemBody
			
			if (body1 != body2) throw new RuntimeException("Should not be comparing expressions across SystemBody.");
			
			return body1.equations.indexOf(eq1) - body1.equations.indexOf(eq2)
		}
		
	})}
	
	static def apply(AlphaExpression expr) {
		val SSA = new ShareSpaceAnalysis()
		SSA.accept(expr)
		return new ShareSpaceAnalysisResult( SSA.shareSpace );
	}
	
	private def warning(String message) {
		System.err.println("[ShareSpaceAnalysis] " + message)
	}
	
	def static isConstantInContext(AlphaExpression ae) {
		val dim = ae.contextDomain.dim(ISLDimType.isl_dim_out)
		
		val SSAR = apply(ae)
		val shareSpace = SSAR.getShareSpace(ae)
		
		shareSpace !== null && shareSpace.size == dim
	}
	
	//Expressions with Trivial Share Space
	 
	override outConstantExpression(ConstantExpression ce) {
		shareSpace.put(ce, null)
	}
	
	override outVariableExpression(VariableExpression ve) {
		shareSpace.put(ve, null)
	}
	
	//Expressions that simply inherit its child
	
	override outUnaryExpression(UnaryExpression ue) {
		shareSpace.put(ue, shareSpace.get(ue.expr))
	}
	
	override outRestrictExpression(RestrictExpression re) {
		shareSpace.put(re, shareSpace.get(re.expr))
	}
	
	override outAutoRestrictExpression(AutoRestrictExpression are) {
		shareSpace.put(are, shareSpace.get(are.expr))
	}
	
	//Expressions with multiple operands ( = intersection of its children)
	
	override outBinaryExpression(BinaryExpression be) {
		val SE = intersectShareSpaces(be.left, be.right)
		shareSpace.put(be, SE)
	}
	
	override outIfExpression(IfExpression ie) {
		val SE = intersectShareSpaces(ie.condExpr, ie.thenExpr, ie.elseExpr)
		shareSpace.put(ie, SE)
	}
	
	override outMultiArgExpression(MultiArgExpression mae) {
		val SE = intersectShareSpaces(mae.exprs)
		shareSpace.put(mae, SE)
	}
	
	override outCaseExpression(CaseExpression ce) {
		val SE = intersectShareSpaces(ce.exprs)
		shareSpace.put(ce, SE)
	}
	
	//Expressions with affine functions ( = kernel of the function becomes the share space)
	
	override outIndexExpression(IndexExpression ie) {
		val kernel = AffineFunctionOperations.computeKernel(ie.function)

		if (kernel.length == 0) {
			shareSpace.put(ie, null)
		} else {
			shareSpace.put(ie, MatrixOperations.transpose(kernel))
		}
	}
	
	override outDependenceExpression(DependenceExpression de) {
		//share space is expected to have at least one row if it is not null
		val SEp = shareSpace.get(de.expr)
		
		val f =	if (SEp === null) { de.function }
		else {
			//The definition in the paper when the child expression has a share space is:
			// if E=f@E', and SE' = ker(T), then SE = ker(f@T)
			//In the following, T is constructed from SE'. Since you just need some
			// T that exhibits the same kernel, T is computed by taking the kernel of the share space
			// by viewing it as a function (hence parameter identity needs to be added)
			val params = AlphaUtil.getParameterDomain(de).paramNames
			val indices = de.contextDomain.indexNames
			val maff = AffineFunctionOperations.constructAffineFunctionWithSpecifiedKernel(params, indices, SEp)
			maff.pullback(de.function)
		}
		
		val kernel = AffineFunctionOperations.computeKernel(f)
		if (kernel === null || kernel.length == 0) {
			shareSpace.put(de, null)
		} else {
			shareSpace.put(de, MatrixOperations.transpose(kernel))
		}
	}
	
	override outAbstractReduceExpression(AbstractReduceExpression are) {
		if (are.expressionDomain.dim(fr.irisa.cairn.jnimap.isl.ISLDimType.isl_dim_div) >0 ||
			are.expressionDomain.nbBasicSets > 1) {
			shareSpace.put(are, null)
			warning("Reduction body is not a single polyhedron or contains div dimensions. Share space is set to empty.")
			return;		
		}
		
		val SEp = shareSpace.get(are.body)
		val kerQ = MatrixOperations.transpose(DomainOperations.kernelOfLinearPart(are.body.expressionDomain));
		val intersection = MatrixOperations.kernelIntersection(SEp, kerQ)
		
		if (intersection !== null) {
			warning("Expression of a reduction has non-empty share space. This is only possible when the expression domain has non-empty linearlity space. The share space of the ReduceExpression is set to empty.")
		}
		
		shareSpace.put(are, null)
	}
	
	override defaultOut(AlphaExpressionVisitable expr) {
		throw new UnsupportedOperationException("[ShareSpaceAnalyais] Unsupported Expression: " + expr);
	}

	private def intersectShareSpaces(AlphaExpression ... exprs) {
		intersectShareSpaces(Arrays.asList(exprs))
	}
	private def intersectShareSpaces(List<AlphaExpression> exprs) {
		var long[][] SE = null
		
		for (expr : exprs) {
			SE = if (SE === null) shareSpace.get(expr)
			else {
				MatrixOperations.kernelIntersection(SE, shareSpace.get(expr))
			}
		}
		return SE
	}

}