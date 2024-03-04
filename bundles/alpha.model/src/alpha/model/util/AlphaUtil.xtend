package alpha.model.util

import alpha.model.AlphaConstant
import alpha.model.AlphaExpression
import alpha.model.AlphaNode
import alpha.model.AlphaPackage
import alpha.model.AlphaRoot
import alpha.model.AlphaSystem
import alpha.model.AlphaVisitable
import alpha.model.Equation
import alpha.model.SystemBody
import fr.irisa.cairn.jnimap.isl.ISLErrorException
import fr.irisa.cairn.jnimap.isl.ISLFactory
import fr.irisa.cairn.jnimap.isl.JNIISLTools
import java.util.LinkedList
import java.util.List
import java.util.function.Consumer
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider
import org.eclipse.xtext.naming.IQualifiedNameProvider
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import alpha.model.Variable
import alpha.model.VariableExpression
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList
import fr.irisa.cairn.jnimap.isl.ISLIdentifier
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import alpha.model.matrix.MatrixOperations
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLVal
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT
import fr.irisa.cairn.jnimap.isl.ISLAffList
import alpha.model.StandardEquation
import fr.irisa.cairn.jnimap.isl.ISLSpace

/**
 * Utility methods for analysis and transformation of Alpha programs.
 * 
 */
class AlphaUtil {
	
	
	/**
	 * Given a name candidate, ensures that it does not conflict
	 * with existing variables. If a variable is in conflict,
	 * the specified String is appended until there is no conlict.
	 */
	static def duplicateNameResolver() {
		return [AlphaSystem s, String nameCandidate, String postfix|
			var currentName = nameCandidate;
			
			while (s.getVariable(currentName) !== null) {
				currentName = currentName + postfix
			}
			
			return currentName
		];
	}
	
	/**
	 * Given a name candidate, ensures that it does not conflict
	 * with existing variables. If a variable is in conflict,
	 * an integer is added to the end, where the value increases
	 * until there is no conflict.
	 */
	static def duplicateNameResolverWithCounter() {
		return [AlphaSystem s, String nameCandidate|
			var currentName = nameCandidate;
			
			var count = 2;
			while (s.getVariable(currentName) !== null) {
				currentName = nameCandidate + count
				count++;
			}
			
			return currentName
		];
	} 

	static def AlphaRoot getContainerRoot(EObject node) {
		if (node instanceof AlphaRoot)
			return node as AlphaRoot
		
		if (node.eContainer() === null)
			return null
		
		return AlphaUtil.getContainerRoot(node.eContainer())
	}

	static def AlphaSystem getContainerSystem(EObject node) {
		if (node instanceof AlphaSystem)
			return node as AlphaSystem
		
		if (node.eContainer() === null)
			return null
		
		return AlphaUtil.getContainerSystem(node.eContainer())
	}
	
	static def SystemBody getContainerSystemBody(EObject node) {
		if (node instanceof SystemBody)
			return node as SystemBody
		
		if (node.eContainer() === null)
			return null
		
		return AlphaUtil.getContainerSystemBody(node.eContainer())
	}
	
	static def Equation getContainerEquation(EObject node) {
		if (node instanceof Equation)
			return node as Equation
		
		if (node.eContainer() === null)
			return null
		
		return AlphaUtil.getContainerEquation(node.eContainer())
	}
	
	static def StandardEquation getStandardEquation(Variable variable) {
		var ret = null as StandardEquation
		val equs = variable.getContainerSystemBody.equations.filter[e | e instanceof StandardEquation]
		                                                    .map[it as StandardEquation]
		                                                    .filter[e | e.variable == variable]
		if (equs.size == 1)
			ret = equs.get(0)
		ret
	}
	/**
	 * Selects an AlphaRoot that contains a given system name. The given system name may be
	 * fully qualified or just the bare name. If the bare name cannot uniquely identify a 
	 * system, then it throws a RuntimeException.
	 * 
	 */
	static def AlphaRoot selectAlphaRoot(List<AlphaRoot> roots, String systemName) {
		//qualified name
		if (systemName.contains('.')) {
			val IQualifiedNameProvider provider = new DefaultDeclarativeQualifiedNameProvider;
			val matching = roots.map[r|r.eAllContents].filter(AlphaSystem).filter[s|provider.getFullyQualifiedName(s).toString.contentEquals(systemName)].toList
			if (matching.length>0) return getContainerRoot(matching.head)
		//just the system name
		} else {
			val matching = roots.iterator.flatMap[eAllContents.filter(AlphaSystem).filter[s|s.name.contentEquals(systemName)]].toList
			if (matching.size>1) throw new RuntimeException("There are multiple systems with the name: " + systemName);
			if (matching.size>0) return getContainerRoot(matching.head)
		}
		
		throw new RuntimeException("System " + systemName + " was not found.");
	}

	

	static def ISLSet getParameterDomain(EObject node) {

		val system = AlphaUtil.getContainerSystem(node);
		if (system === null) {
			throw new RuntimeException("Node is not contained by an AlphaSystem.");
		}

		if (system.getParameterDomain() === null || system.getParameterDomain() === null) {
			throw new RuntimeException("The parameter domain of the container system is null.");
		}

		return system.getParameterDomain();
	}

	/**
	 * Replaces all AlphaConstants in the given string with its integer values.
	 * It is based on String.replaceAll, so it may fail on some inputs.
	 * Currently the model is that the users pick good names for AlphaConstants to avoid this issue
	 */
	static def String replaceAlphaConstants(AlphaSystem system, String jniString) {
		if (system !== null && system.eContainer !== null) {
			var str = jniString
			for (ac : gatherAlphaConstants(system.eContainer as AlphaVisitable)) {
				str = str.replaceAll(ac.name, ac.value.toString);
			}
			return str
		} 
		
		
		return jniString
	}
	
	static def getAlphaConstants(AlphaSystem system) {
		if (system !== null && system.eContainer !== null) {
			return gatherAlphaConstants(system.eContainer as AlphaVisitable)
		}
	}
	
	private static def dispatch gatherAlphaConstants(AlphaPackage ap) {
		ap.elements.filter(AlphaConstant)
	}
	
	private static def dispatch gatherAlphaConstants(AlphaRoot ar) {
		ar.elements.filter(AlphaConstant)
	}
	

	//Void is for null in Xtend dispatch
	static def dispatch copy(Void n) {
		null
	}
	static def dispatch copy(ISLMap map) {
		map.copy
	}
	static def dispatch copy(ISLSet set) {
		set.copy
	}
	static def dispatch copy(ISLMultiAff maff) {
		maff.copy
	}
	static def dispatch copy(ISLUnionMap umap) {
		umap.copy
	}
	
	/**
	 * Method that adds parameter domain names and replaces AlphaConstants with its value.
	 * Last step before passing the string to ISL.
	 */
	static def String toContextFreeISLString(AlphaSystem system, String alphaDom) {
			val completed = new StringBuffer("[");
			completed.append(String.join(",", system.parameterDomain.getParamNames()));
			completed.append("] -> ");

			completed.append(alphaDom);
			
			AlphaUtil.replaceAlphaConstants(system, completed.toString())
	}
	
	static def dispatch ISLSet getScalarDomain(AlphaSystem system) {
		var jniset = ISLFactory.islSet(AlphaUtil.toContextFreeISLString(system, "{ [] : }"));
		val pdom = system.parameterDomain
		
		jniset.intersectParams(pdom.copy());
	}
	static def dispatch ISLSet getScalarDomain(AlphaExpression expr) {
		if (expr.containerSystem === null) return null
		expr.containerSystem.scalarDomain
	}
	
	

	/**
	 * Helper function to obtain the additional indices due to while expressions when parsing polyhedral objects specified in ArrayNotation
	 * */
	static def List<String> getWhileIndexNames(AlphaNode node) {
		val containerSystem = AlphaUtil.getContainerSystem(node)
		if (containerSystem.whileDomain !== null) containerSystem.whileDomain.indexNames
		else new LinkedList;
	}
	
	static def <T> T callISLwithErrorHandling(Supplier<T> r, Consumer<String> f) {
		return callISLwithErrorHandling(r, f, null);
	}
	static def <T> T callISLwithErrorHandling(Supplier<T> r, Consumer<String> f, T defaultValue) {
		try {
			return JNIISLTools.<T>recordError(r);
		} catch (ISLErrorException e) {
			f.accept(e.islErrorMessage);
			return defaultValue;
		}
	}
	
	static def void callISLwithErrorHandling(Runnable r, Consumer<String> f) {
		try {
			JNIISLTools.recordError(r);
		} catch (ISLErrorException e) {
			f.accept(e.islErrorMessage);
		}
	}
	
	static def renameIndices(ISLSet set, List<String> names) {
		val n = set.getNbIndices()
		var res = set;
		if (n > names.length) throw new RuntimeException("Need n or more index names to rename n-d space.");
		for (i : 0..<n) {
			res = res.setDimName(ISLDimType.isl_dim_set, i, names.get(i))
		}
			
		return res
	}
	static def renameIndices(ISLMap map, List<String> names) {
		val n = map.getNbInputs
		var res = map;
		if (n > names.length) throw new RuntimeException("Need n or more index names to rename n-d space.");
		for (i : 0..<n) {
			res = res.setDimName(ISLDimType.isl_dim_in, i, names.get(i))
		}
			
		return res
	}
	static def renameIndices(ISLMultiAff maff, List<String> names) {
		val n = maff.getNbInputs
		if (n > names.length) throw new RuntimeException("Need n or more index names to rename n-d space.");
		var res = maff;
		for (i : 0..<n) {
			res = res.setDimName(ISLDimType.isl_dim_in, i, names.get(i))
		}
			
		return res
	}
	static def renameIndices(ISLPWQPolynomial pwqp, List<String> names) {
		val n = pwqp.dim(ISLDimType.isl_dim_in)
		if (n > names.length) throw new RuntimeException("Need n or more index names to rename n-d space.");
		var res = pwqp;
		for (i : 0..<n) {
			res = res.setDimName(ISLDimType.isl_dim_in, i, names.get(i))
		}
			
		return res
	}
	static def renameIndices(ISLQPolynomial qp, List<String> names) {
		val n = qp.dim(ISLDimType.isl_dim_in)
		if (n > names.length) throw new RuntimeException("Need n or more index names to rename n-d space.");
		var res = qp;
		for (i : 0..<n) {
			res = res.setDimName(ISLDimType.isl_dim_in, i, names.get(i))
		}
			
		return res
	}
	
	static def defaultDimNames(int n) {
		defaultDimNames(0, n)
	}
	
	static def defaultDimNames(int offset, int n) {
		(offset..<offset+n).map[i|"i"+i].toList
	}
	
	static def defaultDimNames(ISLSet set) {
		defaultDimNames(set.nbIndices)
	}
	
	static def parseIntArray(String intVecStr) {
		if (intVecStr.contains(","))
			intVecStr.replace('[', '').replace(']', '').trim().split("\\s*,\\s*").stream.mapToInt[e|Integer.parseInt(e.trim)].toArray
		else
			intVecStr.replace('[', '').replace(']', '').trim().split("\\s+").stream.mapToInt[e|Integer.parseInt(e.trim)].toArray
	}
	
	static def parseIntVector(String intVecStr) {
		return parseIntArray(intVecStr).toList
	}
	
	static def numDims(Variable variable) {
		variable.domain.dim(ISLDimType.isl_dim_out)
	}
	
	static def List<String> indices(Variable variable) {
		variable.domain.indexNames.toList
	}
	
	static def List<String> indexNames(ISLMultiAff maff) {
		maff.getDomainSpace.indexNames
	}
	
	static def List<String> paramNames(ISLMultiAff maff) {
		maff.getDomainSpace.paramNames
	}
	
	static def List<String> indexNames(ISLSpace space) {
		space.indexNames.toList
	}

	static def List<String> paramNames(ISLSpace space) {
		space.paramNames.toList
	}

	
	def static Iterable<Variable> listAllReferencedVariables(AlphaNode e) {
		listAllVariableExpressions(e).map[it.variable]
	}

	def static Iterable<VariableExpression> listAllVariableExpressions(AlphaNode e) {
		e.listAllChildrenExpressions.filter(VariableExpression)
	}
	
	def static Iterable<AlphaExpression> listChildrenExpressions(AlphaNode e) {
		e.eContents.filter(AlphaExpression)
	}

	def static Iterable<AlphaExpression> listAllChildrenExpressions(AlphaNode e) {
		e.eAllContents.filter(AlphaExpression).toIterable
	}

	def static ISLIdentifierList toIdentifierList(List<String> iterators, ISLContext context) {
		var ret = ISLIdentifierList.build(context, iterators.size)
		for (i : 0..<iterators.size) {
			ret = ret.insert(i, ISLIdentifier.alloc(context, iterators.get(i)))
		}
		ret
	}
	
	def static ISLIdentifierList toIdentifierList(List<ISLIdentifier> iterators) {
		if (iterators.size == 0) {
			return null
		}
		val context = iterators.get(0).context
		var ret = ISLIdentifierList.build(context, iterators.size)
		for (i : 0..<iterators.size) {
			ret = ret.insert(i, iterators.get(i))
		}
		ret
	}
	


	
	def static ISLAffList[] lexmins(ISLSet set) {
		set.basicSets.map[lexSwitch(false)]
	}
	
	def static ISLAffList[] lexmaxes(ISLSet set) {
		set.basicSets.map[lexSwitch(true)]
	}
	
	def static ISLAffList lexSwitch(ISLBasicSet set, boolean max) {
		val dim = set.dim(ISLDimType.isl_dim_out)
		var ret = ISLAffList.build(set.context, dim)
		for (i : 0..<dim) {
			ret = ret.add(set.lexSwitch(i, max))
		}
		ret
	}

	def static ISLAff lexSwitch(ISLBasicSet set, int dim, boolean max) {
		val space = set.space.toLocalSpace
		var mset = set.copy.moveDims(ISLDimType.isl_dim_param, 0, ISLDimType.isl_dim_out, dim, 1)
						   .moveDims(ISLDimType.isl_dim_out, 0, ISLDimType.isl_dim_param, 0, 1)
		val m = if (max) mset.lexMax else mset.lexMin
		if (m.getNbBasicSets != 1) {
			throw new Exception("Unexpected number of basic sets")
		}
		mset = m.getBasicSetAt(0).dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, 1)
		val mat = DomainOperations.toISLEqualityMatrix(mset).toLongMatrix
		
		val coeffs = mat.get(0).map[it.intValue]
		
		var aff = ISLAff.buildZero(space)
		for (i : 0..<aff.dim(ISLDimType.isl_dim_param))
			aff = aff.setCoefficient(ISLDimType.isl_dim_param, i, coeffs.get(i))
		aff = aff.setConstant(coeffs.get(coeffs.size - 1))
		aff = aff.negate
		
		aff 
	}
	
	
	
	
	
	
	
	
	
	
	
	
}