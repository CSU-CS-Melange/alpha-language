package alpha.abft

import alpha.abft.analysis.ConvolutionDetector
import alpha.abft.util.ConvolutionKernel
import alpha.loader.AlphaLoader
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaModelSaver
import alpha.model.AlphaRoot
import alpha.model.AlphaSystem
import alpha.model.AlphaVisitable
import alpha.model.BINARY_OP
import alpha.model.DependenceExpression
import alpha.model.REDUCTION_OP
import alpha.model.RestrictExpression
import alpha.model.SystemBody
import alpha.model.Variable
import alpha.model.transformation.Normalize
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.util.AShow
import alpha.model.util.AlphaUtil
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLVal
import java.util.HashMap
import java.util.List
import java.util.Map

import static alpha.commands.UtilityBase.GetSystem
import static alpha.model.factory.AlphaUserFactory.*

import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.AlphaUtil.getContainerRoot
import static extension alpha.model.util.CommonExtensions.permutations
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.createConstantMaff
import static extension alpha.model.util.ISLUtil.toISLMultiAff
import static extension alpha.model.util.ISLUtil.toISLSet
import alpha.model.AlphaExpression

class ABFTv1 {
	
	static HashMap<String, AlphaRoot> loadOrGet = newHashMap
	
	
	def static addC1Equation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, int[] tileSizes, int radius) {
		val CIndexNames = CVar.domain.indexNames
		val spatialIndexNames = WVar.domain.indexNames
		val spatialContext = buildSpatialContext(tileSizes, radius, CIndexNames, spatialIndexNames)
		systemBody.addC1Equation(CVar, stencilVar, WVar, tileSizes, radius, spatialContext)
	}
	
	def static addC1Equation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, int[] tileSizes, int radius, List<Pair<Pair<String,String>, Pair<Integer,Integer>>> spatialContext) {
		val CIndexNames = CVar.domain.indexNames
		val paramStr = CVar.buildParamStr
		
		val spatialIndexNames = WVar.domain.indexNames
		val bodyIndices = CIndexNames + spatialIndexNames
		val bodyIndexStr = '''[«bodyIndices.join(',')»]'''
		
		val projection = '''«paramStr»->{«bodyIndexStr»->[«CIndexNames.join(',')»]}'''.toString.toISLMultiAff
		
		val constraints = spatialContext.map[
			val ts = key.key
			val s = key.value
			val coeff = value.key
			val TS = value.value
			'''«coeff»«ts»<=«s»<«coeff»«ts»+«TS»'''
		].join(' and ')
		val bodyDom = '''«paramStr»->{«bodyIndexStr» : «constraints»}'''.toString.toISLSet
		
		val TT = tileSizes.get(0)
		val tt = CIndexNames.get(0)
		val stencilVarMaffRangeStr = '''«TT»«tt»,«spatialIndexNames.join(',')»'''
		val stencilVarMaff = '''«paramStr»->{«bodyIndexStr»->[«stencilVarMaffRangeStr»]}'''.toString.toISLMultiAff
		val stencilVarDepExpr = createDependenceExpression(stencilVarMaff)
		stencilVarDepExpr.expr = createVariableExpression(stencilVar)
		
		val restrictExpr = createRestrictExpression(bodyDom)
		restrictExpr.expr = stencilVarDepExpr
		
		val reduceExpr = createReduceExpression(REDUCTION_OP.SUM, projection, restrictExpr)
		
//		val binExpr = createBinaryExpression(BINARY_OP.MUL, createNegativeOneExpression(CVar.domain.space), reduceExpr)
		
		val equ = createStandardEquation(CVar, reduceExpr)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	
	def static addIEquation(SystemBody systemBody, Variable IVar, Variable C1Var, Variable C2Var, Variable WVar) {
		val C2Expr = C2Var.createIdentityDepExpr
		systemBody.addIEquation(IVar, C1Var, C2Var, WVar, C2Expr)
	}
	
	def static addIEquation(SystemBody systemBody, Variable IVar, Variable C1Var, Variable C2Var, Variable WVar, AlphaExpression C2Expr) {
		
		val subExpr = createBinaryExpression(BINARY_OP.SUB)
		subExpr.left = C2Expr
		subExpr.right = C1Var.createIdentityDepExpr
		
		val divExpr = createBinaryExpression(BINARY_OP.DIV)
		divExpr.left = subExpr
		divExpr.right = C1Var.createIdentityDepExpr
		
		val equ = createStandardEquation(IVar, divExpr)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	
	def static addKernelWEquation(SystemBody systemBody, Variable variable, ISLSet oneDomain) {
		val space = variable.domain.space
		
		val oneBranch = createRestrictExpression(oneDomain.copy)
		oneBranch.expr = createPositiveOneExpression(space)
		
		val defaultBranch = createAutoRestrictExpression
		defaultBranch.expr = createZeroExpression(space)
		
		val ce = createCaseExpression
		ce.exprs += oneBranch
		ce.exprs += defaultBranch
		
		val equ = createStandardEquation(variable, ce)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	//////////////////////////////////////////////////
	// Alpha builder helper functions               //
	//////////////////////////////////////////////////
	
	def static createIdentityDepExpr(Variable variable) {
		val indexNames = variable.domain.indexNames
		val paramStr = variable.domain.space.buildParamStr
		val maff = '''«paramStr»->{[«indexNames.join(',')»]->[«indexNames.join(',')»]}'''.toString.toISLMultiAff
		val de = createDependenceExpression(maff)
		de.expr = createVariableExpression(variable)
		de
	}
	
	def static DependenceExpression createRealDepExpr(ISLSpace space, float value) {
		createDependenceExpression(space.createConstantMaff, createRealExpression(value))
	}
	
	
	//////////////////////////////////////////////////
	// Misc helper functions                        //
	//////////////////////////////////////////////////
	
	
	def static buildSpatialContext(int[] tileSizes, int radius, String[] CIndexNames, String[] spatialIndexNames) {
		tileSizes.buildSpatialContext(radius, CIndexNames, spatialIndexNames, 0)
	}
	
	def static buildSpatialContext(int[] tileSizes, int radius, String[] CIndexNames, String[] spatialIndexNames, int tileSizeOffset) {
		val TT = tileSizes.get(0)
		val spatialTileIndexNames = (1..<tileSizes.size).map[i | CIndexNames.get(i)]
		val spatialTileSizes = tileSizes.spatialSizes
		val spatialTileCoeffs = spatialTileSizes.map[TS | TS - tileSizeOffset]
		val spatialNames = spatialTileIndexNames.zipWith(spatialIndexNames)
		val spatialSizes = spatialTileCoeffs.zipWith(spatialTileSizes)
		spatialNames.zipWith(spatialSizes)
	}
	
	def static buildParamStr(Variable variable) {
		variable.domain.space.buildParamStr
	}
	def static buildParamStr(ISLSpace space) {
		val paramStr = '''[«space.paramNames.join(',')»]'''
		paramStr		
	}
	
	
	/**
	 * Hardcoded for now, but this is where convolution detection should happen
	 * Returns the pair (convolution radius)->(map of the convolution kernel)
	 */
	def static identify_convolution(AlphaSystem system) {
		switch (system.name.split('_').get(0)) {
			case 'star1d1r' : 1 -> #{
										#[-1]->0.3332,
										#[ 0]->0.3333,
										#[ 1]->0.33
									}
			case 'star2d1r' : 1 -> #{
										#[ 0, 0]->0.5002,
										#[-1, 0]->0.1247,
										#[ 1, 0]->0.1249,
										#[ 0,-1]->0.1250,
										#[ 0, 1]->0.1251
									}
			case 'star3d1r' : 1 -> #{
										#[ 0, 0, 0]->0.2500,
										#[-1, 0, 0]->0.1248,
										#[ 1, 0, 0]->0.1249,
										#[ 0,-1, 0]->0.1250,
										#[ 0, 1, 0]->0.1250,
										#[ 0, 0,-1]->0.1251,
										#[ 0, 0, 1]->0.1252
									}
		}
	}
	
	def static rename(AlphaSystem system, int[] tileSizes, String vX) {
		val H = tileSizes.get(0)
		val Ls = tileSizes.spatialSizes
		system.name = '''«system.name»_abft_«vX»_«H»_«Ls.join('_')»'''.toString
	}
	
	def static void save(AlphaSystem system) {
		val file = '''resources/generated/«system.name».alpha'''
		
		AlphaModelSaver.ASave(system.getContainerRoot, file)
		println('Saved to file ' + file)
	}
	
	def static spatialSizes(int[] tileSizes) {
		(1..<tileSizes.size).map[i | tileSizes.get(i)]
	}
	
	def static loadSystem(String file, String systemName) {
		var root = loadOrGet.get(file)
		if (root === null) {
			root = AlphaLoader.loadAlpha('resources/inputs/' + file)
			loadOrGet.put(file, root)
		}
		
		val system = GetSystem(root.copyAE, systemName)
		return system
	}
	
	def static pprint(AlphaVisitable av, String msg) {
		println(msg + ':')
		println(AShow.print(av))
	}
	
}