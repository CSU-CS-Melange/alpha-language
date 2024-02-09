package alpha.model.util

import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLBasicSet
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.LinkedList
import java.util.List

import static extension alpha.model.util.AlphaUtil.renameIndices

class AlphaPrintingUtil {
	
	/**
	 * ISLMultiAff to Alpha functions
	 */
	static def String toShowString(ISLMultiAff maff) {
		if (maff === null) return null;
		val lhs = maff.domainSpace.getIndexNames.join(",")
		val rhs = maff.affs.join(",", [a|a.toAlphaString]);
		
		//due to a problem in Xtext parser, RHS starting with '-' need a space
		val offset = if (rhs.startsWith("-")) " " else ""
		
		return '''(«lhs»->«offset»«rhs»)'''
	}
	static def String toAShowString(ISLMultiAff maff) {
		if (maff === null) return null;
		val rhs = maff.affs.join(",", [a|a.toAlphaString]);
		
		return '''[«rhs»]'''
	}
	static def String toAShowString(ISLMultiAff maff, List<String> context) {
		if (maff === null) return null;
		return toAShowString(maff.renameIndices(context))
	}
	
	/**
	 * JNIISLAff to AlphaString
	 * 
	 * The JNIISLAff corresponds to an affine function with 1D output. The space defines the 
	 * index names, and the output expression is defined as a list of coef*name/denom with 
	 * an additional denominator that applies to the entire expression.
	 * 
	 * This method applies the following order:
	 *   - constant is always at the end
	 *   - positive values first
	 *   - among positive/negative values, the order is parameters, indices, divs
	 * 
	 */
	static def String toAlphaString(ISLAff aff) {
		val commonD = aff.denominator

		val posList = new LinkedList<String>
		val negList = new LinkedList<String>
		
		toAlphaStringHelper(aff, ISLDimType.isl_dim_param, commonD, posList, negList)
		toAlphaStringHelper(aff, ISLDimType.isl_dim_in, commonD, posList, negList)
		toAlphaStringHelperForDiv(aff, commonD, posList, negList)
		
		return constructExpressionString(aff, posList, negList)
	}
	
	/*
	 * Helper for printAff that collects positive/negative values of a given dim type
	 */
	private static def void toAlphaStringHelper(ISLAff aff, ISLDimType dimType, long commonD, List<String> posList, List<String> negList) {
		
		val n = aff.dim(dimType)
		val names = aff.getDimNames(dimType)

		for (i : 0..< n) {
			val coefficient = aff.getCoefficientVal(dimType, i)
			val coef = (coefficient.numerator * commonD) / coefficient.denominator
			
			if (coef>1) posList.add('''«coef»«names.get(i)»''')
			else if (coef==1) posList.add(names.get(i))
			else if (coef<-1) negList.add('''«coef»«names.get(i)»''')
			else if (coef==-1) negList.add('''-«names.get(i)»''')
		}
	}

	private static def void toAlphaStringHelperForDiv(ISLAff aff, long commonD, List<String> posList, List<String> negList) {
		
		val n = aff.dim(ISLDimType.isl_dim_div)

		for (i : 0..< n) {
			val coefficient = aff.getCoefficientVal(ISLDimType.isl_dim_div, i)
			val coef = (coefficient.numerator * commonD) / coefficient.denominator
			
			val div = aff.getDiv(i)
			val commonDdiv = div.denominator
			val posListDiv = new LinkedList<String>
			val negListDiv = new LinkedList<String>
			
			toAlphaStringHelper(div, ISLDimType.isl_dim_in, commonDdiv, posListDiv, negListDiv)
			
			val divStr = constructExpressionString(div, posListDiv, negListDiv)
			if (coef>1) posList.add('''«coef»*floor(«divStr»)''')
			else if (coef==1) posList.add('''floor(«divStr»)''')
			else if (coef<-1) negList.add('''«coef»*floor(«divStr»)''')
			else if (coef==-1) negList.add('''-floor(«divStr»)''')
		}
	}
	
	private static def String constructExpressionString(ISLAff aff, List<String> posList, List<String> negList) {
		val commonD = aff.denominator
		
		val constant = aff.constantVal
		val cstVal = (constant.numerator * commonD) / constant.denominator
		
		val pos = posList.join("+")
		val neg = negList.join("")
		val cst = if (cstVal == 0) "" else if (cstVal > 0 && posList.length + negList.length > 0) "+"+cstVal else cstVal
	
		//when everything is 0, then the output is 0; otherwise 0 is not printed
		if (posList.length + negList.length == 0 && cstVal == 0) '''0'''
		else if (commonD != 1) '''(«pos»«neg»«cst»)/«commonD»'''
		else '''«pos»«neg»«cst»'''
	}
	
	/**
	 * ISLSet to AlphaString
	 * 
	 * For sets, ISL string without the parameter part is the AlphaString.
	 */
	static def String toShowString(ISLSet set) {
		toShowString(set, null)
	}
	static def String toShowString(ISLSet set, ISLSet paramDom) {
		toShowString(set, paramDom, null)
	}
	static def String toShowString(ISLSet set, ISLSet paramDom, List<String> names) {
		if (set.getNbIndices == 0 && set.plainIsUniverse)
			return "{}"
			
		val ISLSet setRenamed = if (names !== null) set.renameIndices(names) 
								else if (set.indexNames === null) set.renameIndices(AlphaUtil.defaultDimNames(set))
								else set
		val setGisted = if (paramDom !== null && paramDom.isParamSet)
							setRenamed.gist(paramDom.copy.addDims(ISLDimType.isl_dim_set, setRenamed.nbIndices))
						else setRenamed

		val bsets = setGisted.collectBasicSets
		val out = "{"+setGisted.indexNames+":"+bsets.join(" or ")+"}";
		
		return out
	}
	static def String toAShowString(ISLSet set) {
		toAShowString(set, null)
	}
	static def String toAShowString(ISLSet set, ISLSet paramDom) {
		return toAShowString(set, paramDom, null)
	}
	static def String toAShowString(ISLSet set, ISLSet paramDom, List<String> names) {
		val ISLSet setRenamed = if (names !== null) set.renameIndices(names) else set
		val setGisted = if (paramDom !== null && paramDom.isParamSet) setRenamed.gist(paramDom.copy.addDims(ISLDimType.isl_dim_set, setRenamed.nbIndices)) else setRenamed
		
		val bsets = setGisted.collectBasicSets
		val out = "{:"+bsets.join(" or ")+"}";
		return out
	}
	static def collectBasicSets(ISLSet set) {
		set.basicSets.map[c|c.toString.extractConstraints]
	}
	static def collectConstraints(ISLSet set) {
		set.basicSets.map[collectConstraints]
	}
	static def collectConstraints(ISLBasicSet bset) {
		bset.constraints.map[c|c.toString.extractConstraints]		
	}
	
	static def String toShowStringParameterDomain(ISLSet set) {
		set.toString
	}
	static def String toShowStringSystemBodyDomain(ISLSet set) {
		set.toString.removeParameters
	}
	
	/**
	 * ISLMap to Alpha string
	 * 
	 */
	static def toShowString(ISLMap map) {
		"{"+map.collectBasicMaps.join("; ") + "}";
	}
	static def collectBasicMaps(ISLMap map) {
		map.basicMaps.map[c|c.toString.replaceFirst("\\[.*\\]\\s->\\s*\\{", "").replaceFirst("\\}", "")]
	}
	
	/**
	 * ISLPWQPolynomial to Alpha string
	 * 
	 */
	static def toShowString(ISLPWQPolynomial poly) {
		poly.toString.removeParameters
	}
	static def toAShowString(ISLPWQPolynomial poly, List<String> context) {
		toAShowString(poly.renameIndices(context))
	}
	private static def toAShowString(ISLPWQPolynomial poly) {
		poly.toString.removeParameters.replaceAll("\\[.*\\]\\s*->\\s*", "")
	}
	
	/**
	 * ISLQPolynomial to Alpha string
	 * 
	 */
	static def toShowString(ISLQPolynomial poly) {
		poly.toString.removeParameters
	}
	static def toAShowString(ISLQPolynomial poly, List<String> context) {
		toAShowString(poly.renameIndices(context))
	}
	private static def toAShowString(ISLQPolynomial poly) {
		poly.toString.removeParameters.replaceAll("\\[.*\\]\\s*->\\s*", "")
	}
	
	
	/**
	 * Legacy Alpha Syntax
	 * 
	 */
	static def String toLegacyAlphaString(ISLSet set) {
		toLegacyAlphaString(set, null)
	}
	static def String toLegacyAlphaString(ISLSet set, ISLSet paramDom) {
		toLegacyAlphaString(set, paramDom, null)
	}
	static def String toLegacyAlphaString(ISLSet set, ISLSet paramDom, List<String> names) {
		if (set.getNbIndices == 0)
			return "{|}"
		
		val ISLSet setRenamed = if (names !== null) set.renameIndices(names) else set
		val setGisted = if (paramDom !== null && paramDom.isParamSet) setRenamed.gist(paramDom.copy.addDims(ISLDimType.isl_dim_set, setRenamed.nbIndices)) else setRenamed

		setGisted.basicSets.join("||", [bs|bs.toLegacyAlphaString]);
	}
	private static def String toLegacyAlphaString(ISLBasicSet bset) {
		'''{ «bset.indexNames.join(",")» | «bset.collectConstraints.map([s|s.replace(" = ", " == ")]).join(" && ")» }'''
	}
	static def String toLegacyAlphaStringParameterDomain(ISLSet set) {
		if (set.nbBasicSets != 1) throw new RuntimeException("Parameter domain is assumed to be a single polyhedron.");
		val paramNames = set.paramNames.join(",")
		'''{ «paramNames» | «set.basicSets.get(0).collectConstraints.map([s|s.replace(" = ", " == ")]).join(" && ")» }'''
	}
	
	/**
	 * Helper
	 */
	private static def String extractConstraints(String str) {
		str.replaceFirst("(\\[.*\\]\\s->\\s*)?\\{", "").replaceAll("(\\[[^\\[\\]]*\\])?\\s*:\\s*", "").replaceFirst("\\}", "")
	}
	
	private static def String removeParameters(String str) {
		//trying to just replace '[..] ->' with '' somehow removes '{' too
		// below is a workaround, but this should be revisited FIXME
		str.replaceFirst("\\[.*\\]\\s*->\\s*\\{", "{")
	}
}