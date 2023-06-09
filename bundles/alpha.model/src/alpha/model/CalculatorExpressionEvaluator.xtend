package alpha.model

import alpha.model.exception.OutOfContextArrayNotationException
import alpha.model.issue.AlphaIssue.TYPE
import alpha.model.issue.CalculatorExpressionIssue
import alpha.model.util.AlphaUtil
import alpha.model.util.DefaultCalculatorExpressionVisitor
import fr.irisa.cairn.jnimap.isl.ISLFactory
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.runtime.JNIObject
import java.util.ArrayList
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.impl.EObjectImpl

import static alpha.model.util.AlphaUtil.callISLwithErrorHandling
import static alpha.model.util.AlphaUtil.getParameterDomain
import org.eclipse.emf.ecore.EObject

/**
 * This class is responsible for constructing ISL objects for:<ul>
 *   <li>{@link JNIDomain}</li>
 *   <li>{@link JNIFunction}</li>
 *   <li>{@link JNIRelation}</li> 
 * </ul>
 * which also involves evaluating operations over these objects.
 * 
 * The evaluation is done by first converting the textual specification to ISL objects, 
 * and then calling the appropriate ISL functions. The list of index names must be provided
 * as context for textual specification in ArrayNotation.
 * 
 */
class CalculatorExpressionEvaluator extends EObjectImpl implements DefaultCalculatorExpressionVisitor {

	protected List<CalculatorExpressionIssue> issues = new LinkedList;

	protected List<String> indexNameContext;

	protected new(List<String> indexNameContext) {
		this.indexNameContext = indexNameContext;
	}

	static def List<CalculatorExpressionIssue> calculate(CalculatorExpression expr) {
		return calculate(expr, null)
	}

	static def List<CalculatorExpressionIssue> calculate(CalculatorExpression expr,
		List<String> indexNameContext) {
		
		val calc = new CalculatorExpressionEvaluator(indexNameContext);
		
		calc.testSystemConsistency(expr)
		if (calc.issues.size > 0) return calc.issues

		expr.accept(calc);

		return calc.issues
	}
	
	/**
	 * This method allows this class to be reused by another model that 
	 * extends the main alpha language. (e.g., TargetMapping)
	 * 
	 * The CalculatorExpression does not necessarily have to be contained by
	 * an AlphaSystem, if the key information (parameter domain) may be obtained.
	 * This can be achieved by overriding this method.
	 * 
	 */
	protected def getReferredSystem(CalculatorExpression expr) {
		return AlphaUtil.getContainerSystem(expr);
	}

	protected def testSystemConsistency(CalculatorExpression expr) {
		val system = getReferredSystem(expr);
		if (system === null) {
			registerIssue("CalculatorExpression is not contained by an AlphaSystem.", expr);
			return;
		}
		val params = system.parameterDomain
		if (params === null) {
			registerIssue("Container system does not have a valid parameter domain.", system.parameterDomainExpr);
			return;
		}
	}

	protected def registerIssue(String msg, AlphaNode node) {
		issues.add(new CalculatorExpressionIssue(TYPE.ERROR, msg, node.eContainer, node.eContainingFeature));
	}

	override visitUnaryCalculatorExpression(UnaryCalculatorExpression expr) {
		// depth first; visit the children first
		DefaultCalculatorExpressionVisitor.super.visitUnaryCalculatorExpression(expr);

		if (expr.getExpr().getISLObject() === null) {
			// silent error since the root cause should already by registered in its child
			return;
		}

		val obj = expr.expr.ISLObject
		
		try {
			val res = callISLwithErrorHandling(
					[|evaluateUnaryOperation(expr.operator, obj)],
					[err|registerIssue("Unary operation '" + expr.getOperator() + "' is undefined for " + expr.expr.type, expr)]);
			expr.z__internal_cache_islObject = res;
		} catch (UnsupportedOperationException uoe) {
			registerIssue("Unary operation '" + expr.getOperator() + "' is undefined for " + expr.expr.type, expr.expr);
		}
	}

	private dispatch def evaluateUnaryOperation(CALCULATOR_UNARY_OP op, ISLSet set) {
		switch (op) {
			case COMPLEMENT: {
				return set.complement()
			}
			case POLYHEDRAL_HULL: {
				return set.polyhedralHull().toSet()
			}
			case AFFINE_HULL: {
				return set.affineHull().toSet()
			}
			default: {
				throw new UnsupportedOperationException();
			}
		}
	}

	private dispatch def evaluateUnaryOperation(CALCULATOR_UNARY_OP op, ISLMap map) {
		switch (op) {
			case AFFINE_HULL: {
				return map.affineHull().toMap()
			}
			case POLYHEDRAL_HULL: {
				return map.polyhedralHull().toMap()
			}
			case GET_DOMAIN: {
				return map.getDomain()
			}
			case GET_RANGE: {
				return map.getRange()
			}
			case COMPLEMENT: {
				return map.complement()
			}
			case REVERSE: {
				return map.reverse()
			}
		}
	}

	// None of the unary operators currently in the language makes sense for functions
	private dispatch def evaluateUnaryOperation(CALCULATOR_UNARY_OP op, ISLMultiAff fun) {
		throw new UnsupportedOperationException();
	}

	override visitBinaryCalculatorExpression(BinaryCalculatorExpression expr) {
		// depth first; visit the children first
		DefaultCalculatorExpressionVisitor.super.visitBinaryCalculatorExpression(expr);

		if (expr.getLeft() === null || expr.getRight() === null || expr.getLeft().getISLObject() === null ||
			expr.getRight().getISLObject() === null) {
			// silent error since the root cause should already by registered in its child
			return;
		}

		val left = expr.left.ISLObject
		val right = expr.right.ISLObject
		
		
		try {
			val res = callISLwithErrorHandling(
					[|evaluateBinaryOperation(expr.operator, left, right)],
					[err|registerIssue("Operation " + expr.getOperator() + "failed: " + err, expr)]);
			expr.z__internal_cache_islObject = res;
		} catch (UnsupportedOperationException uoe) {
			registerIssue("Binary operation '" + expr.getOperator() + "' is undefined for " + expr.left.type + " -> " + expr.right.type, expr);
		}
	}

	private dispatch def evaluateBinaryOperation(CALCULATOR_BINARY_OP op, ISLSet left, ISLSet right) {
		switch (op) {
			case INTERSECT: {
				return left.intersect(right)
			}
			case UNION: {
				return left.union(right)
			}
			case CROSS_PRODUCT: {
				return left.flatProduct(right)
			}
			case SET_DIFFERENCE: {
				return left.subtract(right)
			}
			default: {
				throw new UnsupportedOperationException();
			}
		}
	}

	// Although some of the operations (like intersect) may be defined for Set -> Map, they are all undefined to be consistent with iscc
	private dispatch def evaluateBinaryOperation(CALCULATOR_BINARY_OP op, ISLSet left, ISLMap right) {
		throw new UnsupportedOperationException();
	}

	private dispatch def evaluateBinaryOperation(CALCULATOR_BINARY_OP op, ISLSet left, ISLMultiAff right) {
		throw new UnsupportedOperationException();
	}

	private dispatch def evaluateBinaryOperation(CALCULATOR_BINARY_OP op, ISLMap left, ISLSet right) {
		switch (op) {
			case INTERSECT: {
				return left.intersectDomain(right)
			}
			case SET_DIFFERENCE: {
				return left.subtractDomain(right)
			}
			case JOIN: {
				return right.apply(left)
			}
			case INTERSECT_RANGE: {
				return left.intersectRange(right)
			}
			case SUBTRACT_RANGE: {
				return left.subtractRange(right)
			}
			default: {
				throw new UnsupportedOperationException();
			}
		}

	}

	private dispatch def evaluateBinaryOperation(CALCULATOR_BINARY_OP op, ISLMap left, ISLMap right) {
		switch (op) {
			case INTERSECT: {
				return left.intersect(right)
			}
			case UNION: {
				return left.union(right)
			}
			case CROSS_PRODUCT: {
				return left.flatProduct(right)
			}
			case SET_DIFFERENCE: {
				return left.subtract(right)
			}
			case JOIN: {
				return right.applyDomain(left)
			}
			default: {
				throw new UnsupportedOperationException();
			}
		}
	}

	// Most operations involving functions are performed by first converting functions to maps 
	private dispatch def JNIObject evaluateBinaryOperation(CALCULATOR_BINARY_OP op, ISLMap left,
		ISLMultiAff right) {
		return evaluateBinaryOperation(op, left, right.toMap);
	}

	private dispatch def JNIObject evaluateBinaryOperation(CALCULATOR_BINARY_OP op, ISLMultiAff left,
		ISLSet right) {
		return evaluateBinaryOperation(op, left.toMap, right);
	}

	private dispatch def JNIObject evaluateBinaryOperation(CALCULATOR_BINARY_OP op, ISLMultiAff left,
		ISLMap right) {
		return evaluateBinaryOperation(op, left.toMap, right);
	}

	// Fun -> Fun do have its own definition; other operations involve domain/range and do not make sense for functions
	private dispatch def evaluateBinaryOperation(CALCULATOR_BINARY_OP op, ISLMultiAff left, ISLMultiAff right) {
		switch (op) {
			case CROSS_PRODUCT: {
				return left.flatRangeProduct(right)
			}
			case JOIN: {
				return right.pullback(left)
			}
			default: {
				throw new UnsupportedOperationException();
			}
		}
	}
	
	/**
	 * Parsing domains in Alpha to ISLSets
	 * 
	 * ArrayNotation requires the index names to be inferred from the context. Once the constraints excluding parameters 
	 * are computed (or it is what should be specified for AShow notation) the set is parsed with ISL.
	 * 
	 */
	override visitJNIDomain(JNIDomain jniDomain) {
		try {
			var jniset = parseDomain(getReferredSystem(jniDomain), parseJNIDomain(jniDomain));
			jniDomain.setISLSet(jniset);
		} catch (RuntimeException re) {
			val msg = if (re.message === null) re.class.name else re.message
			registerIssue(msg, jniDomain);
		}
	}
	
	/**
	 * Parses a domain in the context of the given system.
	 * This is what gets eventually called to construct an ISLSet from JNIDomain.
	 */
	static def parseDomain(AlphaSystem system, String domainStr) {
			var jniset = ISLFactory.islSet(AlphaUtil.toContextFreeISLString(system, domainStr));
			val pdom = getParameterDomain(system);
			
			return jniset.intersectParams(pdom.copy());
	}

	private def dispatch parseJNIDomain(JNIDomain jniDomain) {
		jniDomain.getIslString();
	}

	private def dispatch parseJNIDomain(JNIDomainInArrayNotation jniDomain) {
		if (indexNameContext === null) throw new OutOfContextArrayNotationException("Empty context found when trying to parse JNIDomain: " + jniDomain.islString);
		
		parseJNIDomain(jniDomain, indexNameContext)
	}
	/**
	 * This static method exposes the parsing of JNIDomain.islString using index name context.
	 * External classes should call this method to have a consistent path to go from
	 * JNIDomain to ISLSet. 
	 */
	static def parseJNIDomain(JNIDomainInArrayNotation jniDomain, List<String> context) {
		String.format("{ [%s] : %s }", (context).join(","), jniDomain.getIslString());
	}

	/**
	 * Parsing relations in Alpha as ISLMaps
	 * 
	 * There is no ArrayNotation for relations, and thus that only preprocessing is to add parameter names.
	 */
	override visitJNIRelation(JNIRelation jniRelation) {
		try {
			var jnimap = parseRelation(getReferredSystem(jniRelation), jniRelation.islString);
			jniRelation.setISLMap(jnimap);
		} catch (RuntimeException re) {
			val msg = if(re.message === null) re.class.name else re.message
			registerIssue(msg, jniRelation);
		}
	}

	/**
	 * Public method to expose how relations are parsed to external classes.
	 * 
	 */	
	static def parseRelation(AlphaSystem system, String relationStr) {
		val pdom = getParameterDomain(system);
		var jnimap = ISLFactory.islMap(AlphaUtil.toContextFreeISLString(system, relationStr));

		return jnimap.intersectParams(pdom.copy());
	}

	/**
	 * Parsing functions in Alpha as JNIISLMultiAffs
	 * 
	 * The functions in Alpha are written in two formats that are both different from ISL syntax.
	 * The Show notation is only a different way to write ISLMAffs, and are parsed after simple conversion.
	 * The ArrayNotation requires the index names to be inferred from the context.
	 * 
	 * Furthermore, ArrayNotation is used for projection functions in reductions, but with a different semantics.
	 * 
	 */
	override visitJNIFunction(JNIFunction jniFunction) {
		parseJNIFunction(jniFunction);
	}

	/**
	 * Parsing Alpha functions in Show notation
	 * 
	 * Functions of the form (i,j->i+j) are converted to ISL syntax: { [i,j]->[i+j] }
	 */
	protected def dispatch void parseJNIFunction(JNIFunction jniFunction) {
		try {
			val indexNames = if (jniFunction.alphaFunction.indexList !== null) jniFunction.alphaFunction.indexList else ""
			val expr = jniFunction.alphaFunction.exprs.join(",", [e|e.ISLString])
			val jnimaff = parseAffineFunction(getReferredSystem(jniFunction), indexNames, expr);
			jniFunction.setISLMultiAff(jnimaff);
		} catch (RuntimeException re) {
			val msg = if(re.message === null) re.class.name else re.message
			registerIssue(msg, jniFunction);
		}
	}
	
	/**
	 * Parses an affine function in the context of the given system.
	 * This method is exposed for use in scripts.
	 */
	static def  parseAffineFunction(AlphaSystem system, String fStr) {
		var str = fStr
		str = str.replace('(', ' ')
		str = str.replace(')', ' ')
		val splitStr = str.split("->")
		if (splitStr.size != 2)
			throw new IllegalArgumentException("Input does not match the format for AffineFunctions: " + fStr + " expecting \"(<index names> -> <affine expressions>)\"");
		parseAffineFunction(system, splitStr.get(0), splitStr.get(1));
	}
	static def parseAffineFunction(AlphaSystem system, List<String> lhs, List<String> rhs) {
		return parseAffineFunction(system, lhs.join(","), rhs.join(","));
	}
	
	/**
	 * Method responsible for parsing affine functions.
	 * All parsing of affine functions, expect for dependences in ArrayNotation, are done through this method.
	 */
	private static def parseAffineFunction(AlphaSystem system, String lhsStr, String rhsStr) {
			val completed = new StringBuffer("{ [");
			completed.append(lhsStr);
			completed.append("] -> [");
			completed.append(rhsStr);
			completed.append("] }");

			return ISLFactory.islMultiAff(AlphaUtil.toContextFreeISLString(system, completed.toString()));
	}

	/**
	 * Parsing Alpha functions in ArrayNotation
	 * 
	 * This function uses another dispatch to select either
	 *   parseJNIFunctionAsProjection, or
	 *   parseJNIFunctionAsFunction
	 * depending on its parent node.
	 */
	protected def dispatch void parseJNIFunction(JNIFunctionInArrayNotation jniFunction) {

		try {
			val jnimaff = ISLFactory.islMultiAff(
				AlphaUtil.toContextFreeISLString(getReferredSystem(jniFunction),
					jniFunction.parseJNIFunctionInContext(jniFunction.eContainer).toString
				));
			jniFunction.setISLMultiAff(jnimaff);
		} catch (RuntimeException re) {
			val msg = if(re.message === null) re.class.name else re.message
			registerIssue(msg, jniFunction);
		}
	}

	/**
	 * ArrayNotation is parsed as projection. In this case, the additional indices expressed are treated as the canonical projection dimensions.
	 *   For example, reduce(op, [x,y], ...) in the context [i,j] gives (i,j,x,y->i,j) as the projection function.
	 * 
	 */
	static def parseJNIFunctionAsProjection(JNIFunctionInArrayNotation jniFunction, List<String> context) {
		val funStr = new StringBuffer("{ [");
		funStr.append((context + jniFunction.arrayNotation).join(","))
		funStr.append("] -> [");
		funStr.append((context).join(","))
		funStr.append("] }");
		return funStr;
	}
	protected def parseJNIFunctionAsProjection(JNIFunctionInArrayNotation jniFunction) {
		if (indexNameContext === null)
			throw new OutOfContextArrayNotationException(String.format("ArrayNotation [%s] does not have the necessary context (index names) to be interpreted.", jniFunction.arrayNotation.join(",")));
		
		parseJNIFunctionAsProjection(jniFunction, indexNameContext);
	}

	/**
	 * ArrayNotation is parsed as function. The indexing expression simply becomes the RHS of ISLMAff, while the LHS is determined by the context.
	 * 
	 */
	static def parseJNIFunctionAsFunction(JNIFunctionInArrayNotation jniFunction, List<String> context) {
		val funStr = new StringBuffer("{ [");
		funStr.append((context).join(","))
		funStr.append("] -> [");
		funStr.append((jniFunction.arrayNotation).join(","))
		funStr.append("] }");
		return funStr;
	}
	
	protected def parseJNIFunctionAsFunction(JNIFunctionInArrayNotation jniFunction) {
		if (indexNameContext === null)
			throw new OutOfContextArrayNotationException(String.format("ArrayNotation [%s] does not have the necessary context (index names) to be interpreted.", jniFunction.arrayNotation.join(",")));

		parseJNIFunctionAsFunction(jniFunction, indexNameContext);
	}

	protected def dispatch parseJNIFunctionInContext(JNIFunctionInArrayNotation jniFunction, EObject parent) {
		throw new UnsupportedOperationException
	}

	protected def dispatch parseJNIFunctionInContext(JNIFunctionInArrayNotation jniFunction, ReduceExpression parent) {
		return parseJNIFunctionAsProjection(jniFunction);
	}

	protected def dispatch parseJNIFunctionInContext(JNIFunctionInArrayNotation jniFunction, ArgReduceExpression parent) {
		return parseJNIFunctionAsProjection(jniFunction);
	}

	protected def dispatch parseJNIFunctionInContext(JNIFunctionInArrayNotation jniFunction, UseEquation parent) {
		return parseJNIFunctionAsFunction(jniFunction);
	}

	protected def dispatch parseJNIFunctionInContext(JNIFunctionInArrayNotation jniFunction, DependenceExpression parent) {
		return parseJNIFunctionAsFunction(jniFunction);
	}

	protected def dispatch parseJNIFunctionInContext(JNIFunctionInArrayNotation jniFunction, IndexExpression parent) {
		return parseJNIFunctionAsFunction(jniFunction);
	}
	
	protected def dispatch parseJNIFunctionInContext(JNIFunctionInArrayNotation jniFunction, AffineFuzzyVariableUse parent) {
		return parseJNIFunctionAsFunction(jniFunction);
	}
	
	
	override visitJNIPolynomial(JNIPolynomial jniPolynomial) {
		try {
			var jniPWQP = parsePolynomial(getReferredSystem(jniPolynomial), parseJNIPolynomial(jniPolynomial));
			jniPolynomial.ISLPWQPolynomial = jniPWQP
		} catch (RuntimeException re) {
			val msg = if (re.message === null) re.class.name else re.message
			registerIssue(msg, jniPolynomial);
		}
	}
	
	/**
	 * Parses a polynomial in the context of the given system.
	 */
	static def parsePolynomial(AlphaSystem system, String pwqpStr) {
			var jniPWQP = ISLFactory.islPWQPolynomial(AlphaUtil.toContextFreeISLString(system,pwqpStr));
			val pdom = getParameterDomain(system);
			
			return jniPWQP.intersectParams(pdom.copy());
	}

	/**
	 * Dispatch method to construct the input islString for both Show and AShow notation.
	 * 
	 * The output of this method does not have the parameter part. It is added later at parsePolynomial.
	 */
	private def dispatch parseJNIPolynomial(JNIPolynomial jniPolynomial) {
		jniPolynomial.getIslString();
	}

	private def dispatch parseJNIPolynomial(JNIPolynomialInArrayNotation jniPolynomial) {
		if (indexNameContext === null) throw new OutOfContextArrayNotationException("Empty context found when trying to parse JNIPolynomial: " + jniPolynomial.islString);
		
		val contextString = String.format("[%s]->", indexNameContext.join(","))
		val pwqpStr = new StringBuffer("{ ");
		pwqpStr.append(jniPolynomial.arrayNotation.map[s|contextString + s].join("; "))
		pwqpStr.append(" }");
		
		return pwqpStr.toString
	}
	
	override visitVariableDomain(VariableDomain vdom) {
		// try to evaluate the variable to detect cycles
		if (vdom.getVariable() !== null)
			vdom.getVariable().getDomain();
	}

	override void visitRectangularDomain(RectangularDomain rdom) {
		val dim = rdom.getUpperBounds().size();

		val dimNames = new ArrayList(dim);
		if (rdom.getIndexNames() !== null && rdom.getIndexNames().size() == dim) {
			dimNames.addAll(rdom.getIndexNames());
		} else {
			for (d : 0 ..< dim) {
				dimNames.add("i" + d);
			}
			if (rdom.upperBounds !== null && rdom.getIndexNames().size() > 0) {
				issues.add(
					new CalculatorExpressionIssue(
						TYPE.WARNING,
						"Length of the index names do not match the domain. Specified names are unused.",
						rdom,
						ModelPackage.Literals.RECTANGULAR_DOMAIN__INDEX_NAMES
					)
				);
			}
		}

		try {
			val pdom = getParameterDomain(rdom);

			val completed = new StringBuffer("[");
			completed.append(String.join(",", pdom.getParamNames()));
			completed.append("] -> { [");
			completed.append(String.join(",", dimNames));
			completed.append("] :");
			for (d : 0 ..< dim) {
				if(d > 0) completed.append(" && ");
				val lb = if (rdom.upperBounds.length == rdom.lowerBounds.length) rdom.lowerBounds.get(d) else "0"
				completed.append(lb + "<=" + dimNames.get(d) + "<" + rdom.upperBounds.get(d));
			}
			completed.append("}");

			var jniset = ISLFactory.islSet(completed.toString());
			jniset = jniset.intersectParams(pdom.copy());
			rdom.setISLSet(jniset);
		} catch (RuntimeException re) {
			registerIssue(re.message, rdom);
		}
	}

	override visitDefinedObject(DefinedObject dobj) {
		// try to evalute the object to detect cycles
		if (dobj !== null)
			dobj.getISLObject();
	}
}
