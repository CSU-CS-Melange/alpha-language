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

class ABFTv3 extends ABFTv1 {
	
	def static void main(String[] args) {
		
		'star3d2t1r'.insertChecksum(#[16, 10])
				
	}
	
	def static insertChecksum(String systemName, int[] tileSizes) {
		val system =  loadSystem('''«systemName».alpha''', systemName)
		system.insertChecksum(tileSizes)
		
		/* Save the augemented system */
		system.save
	}
	
	def static insertChecksum(AlphaSystem system, int[] tileSizes) {
		system.insertChecksum(tileSizes, true)
	}
	def static insertChecksum(AlphaSystem system, int[] tileSizes, boolean renameSystem) {
		val systemBody = system.systemBodies.get(0)
		val outputVar = system.outputs.get(0)
		
		val H = tileSizes.get(0)
		val L = tileSizes.get(1)
		
		/*
		 * Pattern match on convolutions
		 */
		val convolutionKernel = ConvolutionDetector.apply(system)?.get(0)

		/* Pick one of the dimensions to tile */
		val spaceTileDim = 1

		/*
		 * Construct checksum related variable domains
		 */
		val checksumCDomain = outputVar.buildChecksumDomain(convolutionKernel, #[0->H, spaceTileDim->L])
		val checksumCiDomain = outputVar.buildChecksumDomain(convolutionKernel, #[spaceTileDim->L])
		val weightsDomain = outputVar.buildWeightsDomainV3(convolutionKernel)
		val kernelDomain = outputVar.buildWeightsDomainV3(convolutionKernel, 2)
//		val C2Domain = checksumDomain.buildC2DomainV3(kernelDomain, true)
		
//		/*
//		 * Add checksum related variables
//		 */
		val IVar = createVariable('I', checksumCDomain.copy)
		val WVar = createVariable('W', weightsDomain.copy)
		val CVar = createVariable('C1', checksumCDomain.copy)
		val WExtVar = createVariable('WExt', kernelDomain.copy)
		val WiVar = createVariable('Wi', kernelDomain.copy)
		val CiVar = createVariable('C2', checksumCiDomain.copy)
		system.locals.addAll(#[IVar, WVar, WExtVar, WiVar, CVar, CiVar])
//		
//		/*
//		 * Add the respective equations
//		 */
		systemBody.addWeightsEquation(WVar, convolutionKernel)
		systemBody.addKernelWEquation(WExtVar, weightsDomain)
		systemBody.addWiEquation(WiVar, WExtVar, WVar, spaceTileDim)
		systemBody.addCEquation(CVar, outputVar, WVar, convolutionKernel, spaceTileDim, H, L)
		systemBody.addC2Equation(CiVar, outputVar, WVar, convolutionKernel, spaceTileDim, H, L)
//		systemBody.addV2C2Equation(C2Var, outputVar, WVar, allWVar, combosWVar, tileSizes, radius)
		systemBody.addIEquation(IVar, CVar, CiVar, WVar, H)
		
		if (renameSystem) {
			system.rename(#[H, L], 'v3')
		}
		
		Normalize.apply(system)
		NormalizeReduction.apply(system)
		
		system
	}
	
	def static addIEquation(SystemBody systemBody, Variable IVar, Variable C1Var, Variable C2Var, Variable WVar, int H) {
		val indexNames = IVar.domain.indexNames
		val spaceIdxStr = (1..<indexNames.size).map[i | indexNames.get(i)].join(',')
		
		val paramStr = C2Var.buildParamStr
		
		val C2Maff = '''«paramStr»->{[tt,«spaceIdxStr»]->[«H»tt,«spaceIdxStr»]}'''.toString.toISLMultiAff
		
		val C2Expr = createDependenceExpression(C2Maff, createVariableExpression(C2Var))
		
		systemBody.addIEquation(IVar, C1Var, C2Var, WVar, C2Expr)
	}
	
	def static addC2Equation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, ConvolutionKernel convolutionKernel, int spaceTileDim, int H, int L) {
		val equ = createStandardEquation(CVar, createZeroExpression(CVar.domain.space))
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	def static addCEquation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, ConvolutionKernel convolutionKernel, int spaceTileDim, int H, int L) {
		val CIndexNames = CVar.domain.indexNames
		val stencilIndexNames = stencilVar.domain.indexNames
		val paramStr = CVar.buildParamStr
		
		
		val bodyIndices = CIndexNames + #[stencilIndexNames.get(spaceTileDim)]
		val bodyIndexStr = '''[«bodyIndices.join(',')»]'''
		
		val projection = '''«paramStr»->{«bodyIndexStr»->[«CIndexNames.join(',')»]}'''.toString.toISLMultiAff
		// 10ti<=i<10ti+10 «»
		val ti = CIndexNames.get(spaceTileDim)
		val i = stencilIndexNames.get(spaceTileDim)
		val bodyDom =  '''«paramStr»->{«bodyIndexStr» : «L»«ti»<=«i»<«L»«ti»+«L»}'''.toString.toISLSet
		
		val varAcc = (#[H + CIndexNames.get(0)] + (1..<stencilIndexNames.size).map[stencilIndexNames.get(it)]).join(',')
		val stencilVarMaff = '''«paramStr»->{«bodyIndexStr»->[«varAcc»]}'''.toString.toISLMultiAff
		
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
	
	def static addWiEquation(SystemBody systemBody, Variable WiVar, Variable WExtVar, Variable WVar, int spaceTileDim) {
		val paramStr = WiVar.buildParamStr
		val indexNames = WiVar.domain.indexNames
		val pqrName = WiVar.domain.getKernelNamesV3.get(spaceTileDim)
		
		val domainStr = (indexNames + #[pqrName]).join(',')
		val rangeStr = WiVar.domain.indexNames.join(',')
		val projection = '''«paramStr»->{[«domainStr»]->[«rangeStr»]}'''.toString.toISLMultiAff
		
		// WExt[t,i-p,j,k] * W[t,p,0,0]
		val WExtMaffStr = indexNames.map[n | if (indexNames.indexOf(n) == spaceTileDim) '''«n»-«pqrName»''' else n].join(',')
		val WExtMaff = '''«paramStr»->{[«domainStr»]->[«WExtMaffStr»]}'''.toString.toISLMultiAff
		val WExtDepExpr = createDependenceExpression(WExtMaff, createVariableExpression(WExtVar))
		
//		val WMaffRange = indexNames.map[n | if (indexNames.indexOf(n) == spaceTileDim) pqrName else 0].join(',')
		val WMaffRange = indexNames.map[n | switch (indexNames.indexOf(n)) {
			case 0 : n
			case spaceTileDim : pqrName
			default : 0
		}].join(',')
		val WMaff = '''«paramStr»->{[«domainStr»]->[«WMaffRange»]}'''.toString.toISLMultiAff
		val WDepExpr = createDependenceExpression(WMaff, createVariableExpression(WVar))
		
		val be = createBinaryExpression(BINARY_OP.MUL)
		be.left = WExtDepExpr
		be.right = WDepExpr
		
		val reduceExpr = createReduceExpression(REDUCTION_OP.SUM, projection, be)
		
		val equ = createStandardEquation(WiVar, reduceExpr)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	def static getKernelNamesV3(ISLSet kernelDomain) {
		val nbKernel = kernelDomain.dim(ISLDimType.isl_dim_out)
		(0..<nbKernel).map[i | #['h', 'p', 'q', 'r'].get(i)] 
	}
	
	def static addWeightsEquation(SystemBody systemBody, Variable variable, ConvolutionKernel convolutionKernel) {
		val indexNames = variable.domain.indexNames
		val space = variable.domain.space
		val ce = createCaseExpression;
		
		ce.exprs += convolutionKernel.kernel.entrySet.map[			
			val maff = key
			val coeff = value
			val indexValues = maff.affs.map[getConstant.intValue].toList
			space.createWeightRestrictBranchV3(indexNames, indexValues, coeff.floatValue)
		]
		
		/*
		 * add auto restrict of zeros if branch domains don't fully overlap the weights variable domain
		 */
		val branchDoms = ce.exprs.map[e | e as RestrictExpression]
			.map[re | re.restrictDomain].reduce[d1,d2 | d1.copy.union(d2.copy)]
		val WVarDom = variable.domain.copy
		if (!(WVarDom.subtract(branchDoms).isEmpty)) {
			val defaultBranch = createAutoRestrictExpression
			defaultBranch.expr = createZeroExpression(space)
			ce.exprs += defaultBranch
		}
		
		val equ = createStandardEquation(variable, ce)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	def static RestrictExpression createWeightRestrictBranchV3(ISLSpace space, String[] indexNames, Integer[] indexValues, float coeff) {
		space.createRealDepExpr(coeff)
		val paramStr = space.buildParamStr
		val indexStr = '''[«indexNames.join(',')»]'''
		
		val constraints = indexNames.zipWith(indexValues)
			.map['''«key»=«value»''']
			.join(' and ')
		
		val restrictDomain = '''«paramStr»->{«indexStr» : «constraints»}'''.toString.toISLSet
		
		val re = createRestrictExpression(restrictDomain)
		re.expr = space.createRealDepExpr(coeff)
		re
	}
	
	def static buildWeightsDomainV3(Variable variable, ConvolutionKernel convolutionKernel) {
		variable.buildWeightsDomainV3(convolutionKernel, 1)
	}
	def static buildWeightsDomainV3(Variable variable, ConvolutionKernel convolutionKernel, int scale) {
		val indexNames = variable.domain.indexNames
		val spaceIndexNames = (1..<indexNames.size).map[i | indexNames.get(i)]
		val radius = convolutionKernel.radius
		val timeDepth = convolutionKernel.timeDepth
		
		val t = indexNames.get(0)
		
		val spaceConstraints = '''-«radius * scale»<=«spaceIndexNames.join(',')»<=«radius * scale»'''
		val timeConstraints = '''-«timeDepth»<=«t»<0'''
		
		val setStr = '''«variable.buildParamStr»->{[«indexNames.join(',')»] : «timeConstraints» and «spaceConstraints»}'''.toString
		setStr.toISLSet

	}
	
	def static buildChecksumDomain(Variable variable, ConvolutionKernel convolutionKernel, Pair<Integer, Integer>[] tileSizePairs) {// ,int[] tileSizes, int[] tileDims) {
		
		// tileSizePairs is a list of pairs whose key is the tileDim and value is the tileSize 
		
		val domain = variable.domain.copy.intersect(convolutionKernel.domain.copy)
		val sizeVals = tileSizePairs.map[value].map[s | ISLVal.buildRationalValue(ISLContext.instance, s, 1)]
		
		// rename any tiled dim with the prefix 't'
		val indexNames = (0..<domain.indexNames.size).map[i | 
			val name = domain.indexNames.get(i);
			if (tileSizePairs.map[key].contains(i)) 't' + name else domain.indexNames.get(i)
		].toList
		
		/* We want to effectively tile some dimensions in the domain.
		 * This can be done by simply multiplying the coefficient all of constraints involving the
		 * dim to-be-tiled with the desired tile size, and then treating this new dim as a tile dim. 
		 */
		val involvesDim = [ISLConstraint c, int i | c.involvesDims(ISLDimType.isl_dim_out, i, 1)]
		val involvesAny = [ISLConstraint c | tileSizePairs.map[key].map[i | involvesDim.apply(c, i)].reduce[v1, v2 | v1 || v2]]
		
		val tiledDomain = domain.basicSets.map[bs |
			val tiledConstraints = tileSizePairs.map[p | 
				val tileDim = p.key
				val tileSize = ISLVal.buildRationalValue(ISLContext.instance, p.value, 1)
				bs.constraints.filter[c | involvesDim.apply(c, tileDim)].map[c |
					val coeffVal = c.getCoefficientVal(ISLDimType.isl_dim_out, 0)
					c.copy.setCoefficient(ISLDimType.isl_dim_out, 0, coeffVal.mul(tileSize.copy))
				]
			].flatten
			
			val remainingConstraints = bs.constraints.reject[c | involvesAny.apply(c)]
			(tiledConstraints + remainingConstraints)
				.map[toBasicSet].reduce[b1, b2 | b1.intersect(b2)].toSet
		].reduce[s1, s2 | s1.union(s2)]
		
		AlphaUtil.renameIndices(tiledDomain, indexNames)
	}
	
	def static buildChecksumDomain(Variable variable, ConvolutionKernel convolutionKernel, int H, int L, int spaceTileDim) {
		val domain = variable.domain.copy.intersect(convolutionKernel.domain.copy)
		
		val HVal = ISLVal.buildRationalValue(ISLContext.instance, H, 1)
		val LVal = ISLVal.buildRationalValue(ISLContext.instance, L, 1)
		
		// rename the time and spaceTileDim with the prefix 't'
		val indexNames = (0..<domain.indexNames.size).map[i | 
			val name = domain.indexNames.get(i);
			if (i == 0 || i == spaceTileDim) 't' + name else domain.indexNames.get(i)
		].toList
		
		/* We want to effectively tile some dimensions in the domain.
		 * This can be done by simply multiplying the coefficient all of constraints involving the
		 * dim to-be-tiled with the desired tile size, and then treating this new dim as a tile dim. 
		 */
		val involvesTime = [ISLConstraint c | c.involvesDims(ISLDimType.isl_dim_out, 0, 1)]
		val involvesSpace = [ISLConstraint c, int i | c.involvesDims(ISLDimType.isl_dim_out, i, 1)]
		val involvesEither = [ISLConstraint c, int i | involvesTime.apply(c) || involvesSpace.apply(c, i)]
		
		val tiledDomain = domain.basicSets.map[bs |
			val timeConstraints = bs.constraints.filter[c | involvesTime.apply(c)].map[c | 
				val coeffVal = c.getCoefficientVal(ISLDimType.isl_dim_out, 0)
				c.copy.setCoefficient(ISLDimType.isl_dim_out, 0, coeffVal.mul(HVal.copy))
			]
			val spaceConstraints = bs.constraints.filter[c | involvesSpace.apply(c, spaceTileDim)].map[c | 
				val coeffVal = c.getCoefficientVal(ISLDimType.isl_dim_out, spaceTileDim)
				c.copy.setCoefficient(ISLDimType.isl_dim_out, spaceTileDim, coeffVal.mul(HVal.copy))
			]
			val remainingConstraints = bs.constraints.reject[c | involvesEither.apply(c, spaceTileDim)]
			(timeConstraints + spaceConstraints + remainingConstraints)
				.map[toBasicSet].reduce[b1, b2 | b1.intersect(b2)].toSet
		].reduce[s1, s2 | s1.union(s2)]
		
		AlphaUtil.renameIndices(tiledDomain, indexNames)
	}
	
	
}