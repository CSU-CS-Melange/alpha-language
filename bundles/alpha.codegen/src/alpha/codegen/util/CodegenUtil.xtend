package alpha.codegen.util

import alpha.codegen.BaseVariable
import alpha.codegen.CodegenFactory
import alpha.codegen.DataType
import alpha.codegen.GlobalMacro
import alpha.codegen.GlobalMemoryMacro
import alpha.codegen.Include
import alpha.codegen.StatementMacro
import alpha.model.AlphaSystem
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.List
import java.util.regex.Pattern

class CodegenUtil {
	
	static CodegenFactory factory = CodegenFactory.eINSTANCE
	
	def static Include toInclude(String s) {
		val ret = factory.createInclude
		ret.name = s
		ret
	}
	
	def static GlobalMacro globalMacro(String left, String right) {
		val ret = factory.createGlobalMacro
		ret.left = left
		ret.right = right
		ret
	}
	
	def static GlobalMemoryMacro globalMemoryMacro(String left, String right) {
		val ret = factory.createGlobalMemoryMacro
		ret.left = left
		ret.right = right
		ret
	}
	
	def static StatementMacro statementMacro(String left, String right) {
		val ret = factory.createStatementMacro
		ret.left = left
		ret.right = right
		ret
	}
	
	def static BaseVariable baseVariable(String name, DataType type) {
		val cv = factory.createBaseVariable
		cv.name = name
		cv.elemType = type
		cv
	}
	
	def static List<BaseVariable> paramScalarVariables(AlphaSystem s) {
		s.parameterDomain.paramNames.map[baseVariable(it, DataType.LONG)]
	}
	
	def static List<BaseVariable> indexScalarVariables(Variable v) {
		v.domain.indexNames.map[baseVariable(it, DataType.LONG)]
	}
	
	def static parseSetSizes(String s) {
		val params = Pattern.compile("(\\[)(.*?)(\\])");
		var m = params.matcher(s);
		m.find()
		val numParams = m.group(2).replaceAll('[^,]', '').length + 1
		m.find()
		val numIndices = m.group(2).replaceAll('[^,]', '').length + 1
		
		numParams -> numIndices
	}
	
	def static parseMapSizes(String s) {
		val params = Pattern.compile("(\\[)(.*?)(\\])");
		var m = params.matcher(s);
		m.find()
		val numParams = m.group(2).replaceAll('[^,]', '').length + 1
		m.find()
		val numIn = m.group(2).replaceAll('[^,]', '').length + 1
		m.find()
		val numOut = m.group(2).replaceAll('[^,]', '').length + 1
		
		numParams -> (numIn -> numOut)
	}
	
	def static ISLSet toSet(String s) {
		val sizes = s.parseSetSizes
		val l = sizes.key
		val r = sizes.value
		val ctx = ISLSpace.allocSetSpace(l, r).context
		ISLSet.buildFromString(ctx, s)
	}
	
	def static ISLMap toMap(String s) {
		val sizes = s.parseMapSizes
		val l = sizes.key
		val m = sizes.value.key
		val r = sizes.value.value
		val dSpace = ISLSpace.allocSetSpace(l, m)
		val rSpace = ISLSpace.allocSetSpace(l, r)
		val ctx = ISLSpace.buildMapSpaceFromDomainAndRange(dSpace, rSpace).context
		ISLMap.buildFromString(ctx, s)
	}
	
	def static List<String> names(ISLSet set) {
		set.names(ISLDimType.isl_dim_out)
	}
	
	def static List<String> names(ISLSet set, ISLDimType dim_type) {
		val dim = set.dim(dim_type)
		(0..<dim).map[set.getDimName(dim_type, it)].toList
	}
	
	// apply + rename indices
	def static ISLSet appli(ISLSet s, ISLMap m) {
		s.copy.apply(m.copy).renameIndices(s.names)
	}
	
	static def DataType dataType(Variable variable) {
		DataType.FLOAT
	}
	
}