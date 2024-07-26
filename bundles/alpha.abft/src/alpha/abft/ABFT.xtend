package alpha.abft

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
import alpha.model.util.AShow
import alpha.model.util.AlphaUtil
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.HashMap
import java.util.List
import java.util.Map

import static alpha.commands.UtilityBase.GetSystem
import static alpha.model.factory.AlphaUserFactory.*

import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.AlphaUtil.getContainerRoot
import static extension alpha.model.util.CommonExtensions.permutations
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.createConstantMaff
import static extension alpha.model.util.ISLUtil.toISLMultiAff
import static extension alpha.model.util.ISLUtil.toISLSet

class ABFT {
	
	static HashMap<String, AlphaRoot> loadOrGet = newHashMap
	
	def static void main(String[] args) {
		
		'star1d1r'.insert_checksums(#[10, 101])
				
	}
	
	
	def static insert_checksums(String systemName, int[] tileSizes) {
		systemName.insertChecksumV1(tileSizes)
		systemName.insertChecksumV2(tileSizes)
	}
	
	def static assertAssumptions(AlphaSystem system, int[] tileSizes) {
		/*
		 * Simplifying assumptions for benchmarking, to do this truly generally, some
		 * of the logic below would need to be tweaked
		 */
		if (system.systemBodies.size > 1)
			throw new Exception('Only systems with a single body are currently handled')
		if (system.inputs.size > 1)
			throw new Exception('Only systems with a single input variable are currently handled')
		if (system.outputs.size > 1)
			throw new Exception('Only systems with a single output variable are currently handled')
		if (system.parameterDomain.paramNames.toString != '[T, N]')
			throw new Exception('System parameters must be "[T, N]"')
		
		val outputVar = system.outputs.get(0)
		val nbDims = outputVar.domain.dim(ISLDimType.isl_dim_out)
		if (nbDims != tileSizes.size) {
			val ndStr = '''«nbDims» dim«if (nbDims > 1) 's'»'''
			val ntsStr = '''«tileSizes.size» size«if (nbDims > 1) 's were' else 'was'»'''
			throw new Exception('''Output variable «outputVar.name» has «ndStr» but «ntsStr» specified.alpha''')
		}
	}
	
	def static insertChecksumV1(String systemName, int[] tileSizes) {
		val system =  loadSystem('''«systemName».alpha''', systemName)
		system.insertChecksumV1(tileSizes)
		
		/* Save the augemented system */
		system.save
	}
	
	def static insertChecksumV2(String systemName, int[] tileSizes) {
		val system =  loadSystem('''«systemName».alpha''', systemName)
		system.insertChecksumV2(tileSizes)
		
		/* Save the augemented system */
		system.save
	}
	
	def static insertChecksumV1(AlphaSystem system, int[] tileSizes) {
		system.insertChecksumV1(tileSizes, true)
	}
	def static insertChecksumV1(AlphaSystem system, int[] tileSizes, boolean renameSystem) {
		val systemBody = system.systemBodies.get(0)
		val outputVar = system.outputs.get(0)
		
		system.assertAssumptions(tileSizes)
		
		/*
		 * Pattern match on convolutions
		 */
		val convolutionKernel = system.identify_convolution
		val radius = convolutionKernel.key
		val kernel = convolutionKernel.value
		
//		/*
//		 * Move output variable to local
//		 */ 
//		system.outputs.remove(outputVar)
//		system.locals.add(outputVar)
		
		/*
		 * Construct checksum related variable domains
		 */
		val checksumDomain = outputVar.buildChecksumDomain(tileSizes, radius, false)
		val weightsDomain = outputVar.buildWeightsDomain(radius)
		val kernelDomain = outputVar.buildWeightsDomain(2*radius)
		val patchDomain = weightsDomain.buildPatchDomain(tileSizes, radius)
		val C2Domain = checksumDomain.buildC2Domain(kernelDomain, false)
		
		/*
		 * Add checksum related variables
		 */
		val IVar = createVariable('I', checksumDomain.copy)
		val WVar = createVariable('W', weightsDomain.copy)
		val patchVar = createVariable('patch', patchDomain.copy)
		val C1Var = createVariable('C1', checksumDomain.copy)
		val C2Var = createVariable('C2', C2Domain.copy)
//		system.outputs.add(IVar)
		system.locals.addAll(#[WVar, C1Var, C2Var, IVar, patchVar])
		
		/*
		 * Add the respective equations
		 */
		systemBody.addWeightsEquation(WVar, kernel)
		systemBody.addPatchEquation(patchVar, WVar, tileSizes)
		systemBody.addC1Equation(C1Var, outputVar, WVar, tileSizes, radius, false)
		systemBody.addV1C2Equation(C2Var, outputVar, WVar, patchVar, tileSizes, radius)
		systemBody.addIEquation(IVar, C1Var, C2Var, WVar, false)
		
		if (renameSystem) {
			system.rename(tileSizes, 'v1')
		}
	}
	
	def static insertChecksumV2(AlphaSystem system, int[] tileSizes) {
		system.insertChecksumV2(tileSizes, true)
	}
	def static insertChecksumV2(AlphaSystem system, int[] tileSizes, boolean renameSystem) {
		val systemBody = system.systemBodies.get(0)
		val outputVar = system.outputs.get(0)
		
		system.assertAssumptions(tileSizes)
		
		/*
		 * Pattern match on convolutions
		 */
		val convolutionKernel = system.identify_convolution
		val radius = convolutionKernel.key
		val kernel = convolutionKernel.value
		
		/*
		 * Assert that tile sizes are valid given the radius, in other words
		 * the domains are hyper-trapezoidal and not hyper-triangular
		 */
		val TT = tileSizes.get(0)
		tileSizes.spatialSizes.forEach[TS | 
			if (2 * radius * TT >= TS) {
				val max_tt = (TS / 2 / radius).intValue				
				throw new Exception('''Invalid tile sizes for V2, [«tileSizes.join(',')»]. Given a radius of «radius» and spatial tile size «TS», the maximum time tile size can be «max_tt»''')
			}
		]
		
//		/*
//		 * Move output variable to local
//		 */ 
//		system.outputs.remove(outputVar)
//		system.locals.add(outputVar)
		
		/*
		 * Construct checksum related variable domains
		 */
		val checksumDomain = outputVar.buildChecksumDomain(tileSizes, radius, true)
		val weightsDomain = outputVar.buildWeightsDomain(radius)
		val kernelDomain = outputVar.buildWeightsDomain(2*radius)
		val allWeightsDomain = outputVar.buildAllWeightsDomain(TT)
		val C2Domain = checksumDomain.buildC2Domain(kernelDomain, true)
		
		/*
		 * Add checksum related variables
		 */
		val IVar = createVariable('I', checksumDomain.copy)
		val WVar = createVariable('W', weightsDomain.copy)
		val C1Var = createVariable('C1', checksumDomain.copy)
		val C2Var = createVariable('C2', C2Domain.copy)
		val kernelWVar = createVariable('kernelW', kernelDomain.copy)
		val combosWVar = createVariable('combosW', kernelDomain.copy)
		val allWVar = createVariable('allW', allWeightsDomain.copy)
//		system.outputs.add(IVar)
		system.locals.addAll(#[WVar, C1Var, C2Var, IVar, kernelWVar, combosWVar, allWVar])
		
		/*
		 * Add the respective equations
		 */
		systemBody.addWeightsEquation(WVar, kernel)
		systemBody.addKernelWEquation(kernelWVar, weightsDomain)
		systemBody.addCombosWEquation(combosWVar, kernelWVar, WVar)
		systemBody.addAllWEquation(allWVar, combosWVar)
		systemBody.addC1Equation(C1Var, outputVar, WVar, tileSizes, radius, true)
		systemBody.addV2C2Equation(C2Var, outputVar, WVar, allWVar, combosWVar, tileSizes, radius)
		systemBody.addIEquation(IVar, C1Var, C2Var, WVar, true)
		
		if (renameSystem) {
			system.rename(tileSizes, 'v2')
		}
	}
	
	def static addPatchEquation(SystemBody systemBody, Variable patchVar, Variable WVar, int[] tileSizes) {
		val paramStr = patchVar.buildParamStr
		val indexNames = patchVar.domain.indexNames
		val indexNamesStr = indexNames.join(',')
		val spatialIndexNames = WVar.domain.indexNames
		val TS = tileSizes.spatialSizes
		
		// reduce expression
		val pqrNames = getKernelNames(WVar.domain)
		val bodyDomainStr = (indexNames + pqrNames).join(',')
		val projection = '''«paramStr»->{[«bodyDomainStr»]->[«indexNamesStr»]}'''.toString.toISLMultiAff
		
		val patchAccsStr = spatialIndexNames.zipWith(pqrNames).map['''«key»-«value»'''].join(',')
		val patchMaff = '''«paramStr»->{[«bodyDomainStr»]->[w-1,«patchAccsStr»]}'''.toString.toISLMultiAff
		val patchDepExpr = createDependenceExpression(patchMaff, createVariableExpression(patchVar))
		
		val WMaff = '''«paramStr»->{[«bodyDomainStr»]->[«pqrNames.join(',')»]}'''.toString.toISLMultiAff
		val WDepExpr = createDependenceExpression(WMaff, createVariableExpression(WVar))
				
		val be = createBinaryExpression(BINARY_OP.MUL, patchDepExpr, WDepExpr)
		val reduceExpr = createReduceExpression(REDUCTION_OP.SUM, projection, be)
		
		val posWBranchDom = '''«paramStr»->{[«indexNamesStr»] : w>0}'''.toString.toISLSet
		val posWBranch = createRestrictExpression(posWBranchDom.copy, reduceExpr)
		
		// constant expression
		val constraints = spatialIndexNames.zipWith(TS).map[
			'''0<=«key»<«value»'''
		].join(' and ')
		val zeroWBranchDom = '''«paramStr»->{[«indexNamesStr»] : w=0 and «constraints»}'''.toString.toISLSet 
		val zeroWBranch = createRestrictExpression(zeroWBranchDom.copy)
		zeroWBranch.expr = createPositiveOneExpression(zeroWBranchDom.space)
		
		// auto zero expression
		val defaultBranch = createAutoRestrictExpression(createZeroExpression(zeroWBranchDom.space))
		
		// case expression
		val ce = createCaseExpression
		ce.exprs += posWBranch
		ce.exprs += zeroWBranch
		ce.exprs += defaultBranch
		
		val equ = createStandardEquation(patchVar, ce)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	def static addIEquation(SystemBody systemBody, Variable IVar, Variable C1Var, Variable C2Var, Variable WVar, boolean forV2) {
		val C1IndexNames = C1Var.domain.indexNames
		val C2IndexNames = C2Var.domain.indexNames
		
		val paramStr = C2Var.buildParamStr
		
		val projection = '''«paramStr»->{[«C2IndexNames.join(',')»]->[«C1IndexNames.join(',')»]}'''.toString.toISLMultiAff
		val reduceExpr = createReduceExpression(REDUCTION_OP.SUM, projection, C2Var.createIdentityDepExpr)
		
		val subExpr = createBinaryExpression(BINARY_OP.SUB)
		subExpr.left = if (forV2) reduceExpr else C2Var.createIdentityDepExpr
		subExpr.right = C1Var.createIdentityDepExpr
		
		val divExpr = createBinaryExpression(BINARY_OP.DIV)
		divExpr.left = subExpr
		divExpr.right = C1Var.createIdentityDepExpr
		
		val equ = createStandardEquation(IVar, divExpr)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	def static createIdentityDepExpr(Variable variable) {
		val indexNames = variable.domain.indexNames
		val paramStr = variable.domain.space.buildParamStr
		val maff = '''«paramStr»->{[«indexNames.join(',')»]->[«indexNames.join(',')»]}'''.toString.toISLMultiAff
		val de = createDependenceExpression(maff)
		de.expr = createVariableExpression(variable)
		de
	}
	
	def static addV1C2Equation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, Variable patchVar, int[] tileSizes, int radius) {
		val CIndexNames = CVar.domain.indexNames
		val paramStr = CVar.buildParamStr
		val pqrNames = getKernelNames(WVar.domain)
		val bodyIndexStr = (CIndexNames + pqrNames).join(',')
		
		val projection = '''«paramStr»->{[«bodyIndexStr»]->[«CIndexNames.join(',')»]}'''.toString.toISLMultiAff
		
		// patch var expression
		val tt = CIndexNames.get(0)
		val TT = tileSizes.get(0)
		val patchMaff = '''«paramStr»->{[«bodyIndexStr»]->[«TT»,«pqrNames.join(',')»]}'''.toString.toISLMultiAff
		val patchDepExpr = createDependenceExpression(patchMaff, createVariableExpression(patchVar))
		
		// stencil var expression
		val spatialTileNames = (1..<CIndexNames.size).map[i | CIndexNames.get(i)]
		val spatialAccStr = tileSizes.spatialSizes.zipWith(spatialTileNames).zipWith(pqrNames).map[
			val TS = key.key
			val ts = key.value
			val pqrName = value
			'''«TS»«ts»+«pqrName»'''
		].join(',')
		val stencilVarAccStr = '''«TT»«tt»-«TT»,«spatialAccStr»'''
		val stencilVarMaff = '''«paramStr»->{[«bodyIndexStr»]->[«stencilVarAccStr»]}'''.toString.toISLMultiAff
		val stencilVarDepExpr = createDependenceExpression(stencilVarMaff, createVariableExpression(stencilVar))
		
		val be = createBinaryExpression(BINARY_OP.MUL, patchDepExpr, stencilVarDepExpr)
		
		val reduceExpr = createReduceExpression(REDUCTION_OP.SUM, projection, be)

		val equ = createStandardEquation(CVar, reduceExpr)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	def static addV2C2Equation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, Variable allWVar, Variable combosWVar, int[] tileSizes, int radius) {
		val CIndexNames = CVar.domain.indexNames
		val paramStr = CVar.buildParamStr
		val spatialIndexNames = WVar.domain.indexNames
		
		val timeContext = CIndexNames.get(0) -> tileSizes.get(0)
		val spatialContext = buildSpatialContext(tileSizes, radius, CIndexNames, spatialIndexNames, true)
		val pqrNames = getKernelNames(WVar.domain)
		
		val ce = createCaseExpression
		
		#[-1,1,0].permutations(pqrNames.size).forEach[pqrOrthant |	
			ce.exprs += createC2ReductionBranch(paramStr, stencilVar, allWVar, CIndexNames, spatialIndexNames, pqrNames, pqrOrthant, spatialContext, timeContext)
		]
		
		val combosWMaff = '''«paramStr»->{[«CIndexNames.join(',')»]->[«pqrNames.join(',')»]}'''.toString.toISLMultiAff
		val combosWDepExpr = createDependenceExpression(combosWMaff)
		combosWDepExpr.expr = createVariableExpression(combosWVar)
		
		val be = createBinaryExpression(BINARY_OP.MUL)
		be.left = combosWDepExpr
		be.right = ce
		
		val equ = createStandardEquation(CVar, be)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	def static createC2ReductionBranch(String paramStr, Variable stencilVar, Variable allWVar, String[] CIndexNames, String[] spatialIndexNames, String[] pqrNames, Integer[] pqrOrthant, List<Pair<Pair<String,String>,Pair<Integer,Integer>>> spatialContext, Pair<String,Integer> timeContext) {
		
		val accumulationDims = spatialIndexNames.zipWith(pqrOrthant).filter[value == 0].map[key]
		val domainIndexNames = CIndexNames + #['w'] + accumulationDims
		val projectionDomainStr = '''[«domainIndexNames.join(',')»]'''
		val projectionRangeStr = '''[«CIndexNames.join(',')»]'''
		val projection = '''«paramStr»->{«projectionDomainStr»->«projectionRangeStr»}'''.toString.toISLMultiAff
	
		val tt = timeContext.key
		val TT = timeContext.value
		val wRangeConstraint = if (pqrOrthant.map[v | v==0].reduce[v1,v2 | v1&&v2]) {
			'''w=«TT»'''
		} else {
			'''1<=w<=«TT»'''
		}
		
		// create the stencil variable depExpr inside the reduction		
		val timeExpr = #['''«TT»«tt»-w''']
		val pqrContext = pqrNames.zipWith(pqrOrthant)
		val spaceExprs = pqrContext.zipWith(spatialContext).map[
			val pqrc = key
			val pqrName = pqrc.key
			val pqrValue = pqrc.value
			val sc = value
			
			val ts = sc.key.key
			val s = sc.key.value
			val coeff = sc.value.key
			val TS = sc.value.value
			
			switch (pqrValue) {
				case pqrValue < 0 : '''«coeff»«ts»+«pqrName»+w''' 
				case pqrValue > 0 : '''«coeff»«ts»+«pqrName»+w+«TS»-1-2w'''
				case pqrValue == 0 : '''«s»'''
			}
			
		]
		val rangeExprs = timeExpr + spaceExprs
		val stencilVarMaff = '''«paramStr»->{[«domainIndexNames.join(',')»]->[«rangeExprs.join(',')»]}'''.toString.toISLMultiAff
		val stencilVarDepExpr = createDependenceExpression(stencilVarMaff)
		stencilVarDepExpr.expr = createVariableExpression(stencilVar)
		
		// allW[w]
		val allWVarMaff = '''«paramStr»->{[«domainIndexNames.join(',')»]->[w]}'''.toString.toISLMultiAff
		val allWVarDepExpr = createDependenceExpression(allWVarMaff)
		allWVarDepExpr.expr = createVariableExpression(allWVar)
		
		// allW[w] * Y[...]
		val be = createBinaryExpression(BINARY_OP.MUL)
		be.left = allWVarDepExpr
		be.right = stencilVarDepExpr
		
		val constraints = spatialContext.filter[accumulationDims.contains(key.value)].map[
			val ts = key.key
			val s = key.value
			val coeff = value.key
			val TS = value.value
			'''«coeff»«ts»+w<=«s»<«coeff»«ts»+«TS»-w'''
		].toList
		constraints.add(0, wRangeConstraint)
		val bodyDomainStr = '''«paramStr»->{«projectionDomainStr» : «constraints.join(' and ')»}'''.toString
		val bodyDomain = bodyDomainStr.toISLSet
		
		val body = createRestrictExpression(bodyDomain.copy)
		body.expr = be
		
		val reduceExpr = createReduceExpression(REDUCTION_OP.SUM, projection, body)
		
		val branchDom = createC2BranchDomain(paramStr, CIndexNames, pqrNames, pqrOrthant)
		val branch = createRestrictExpression(branchDom)
		branch.expr = reduceExpr
		
		branch
	}
	
	
	
	
	def static createC2BranchDomain(String paramStr, String[] domainIndexNames, String[] pqrNames, Integer[] pqrOrthant) {
		
		val constraints = pqrNames.zipWith(pqrOrthant).map[
			switch (value) {
				case value < 0: '''«key»<0'''
				case value > 0: '''«key»>0'''
				case value == 0: '''«key»=0'''
			}
		].join(' and ')
		
		val set = '''«paramStr»->{[«domainIndexNames.join(',')»] : «constraints»}'''.toString.toISLSet
		
		set
	}
	
	def static buildSpatialContext(int[] tileSizes, int radius, String[] CIndexNames, String[] spatialIndexNames, boolean forV2) {
		val TT = tileSizes.get(0)
		val spatialTileIndexNames = (1..<tileSizes.size).map[i | CIndexNames.get(i)]
		val spatialTileSizes = tileSizes.spatialSizes
		val spatialTileCoeffs = spatialTileSizes.map[TS | if (forV2) TS - 2*radius*TT else TS]
		val spatialNames = spatialTileIndexNames.zipWith(spatialIndexNames)
		val spatialSizes = spatialTileCoeffs.zipWith(spatialTileSizes)
		spatialNames.zipWith(spatialSizes)
	}
	
	def static addC1Equation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, int[] tileSizes, int radius, boolean forV2) {
		val CIndexNames = CVar.domain.indexNames
		val paramStr = CVar.buildParamStr
		
		val spatialIndexNames = WVar.domain.indexNames
		val bodyIndices = CIndexNames + spatialIndexNames
		val bodyIndexStr = '''[«bodyIndices.join(',')»]'''
		
		val projection = '''«paramStr»->{«bodyIndexStr»->[«CIndexNames.join(',')»]}'''.toString.toISLMultiAff
		
		val constraints = buildSpatialContext(tileSizes, radius, CIndexNames, spatialIndexNames, forV2).map[
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
		
		val equ = createStandardEquation(CVar, reduceExpr)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	
	
	def static addAllWEquation(SystemBody systemBody, Variable allWVar, Variable combosWVar) {
		val paramStr = allWVar.buildParamStr
		val space = allWVar.domain.space
		
		val oneBranchDom = '''«paramStr»->{[w] : w=1}'''.toString.toISLSet
		val oneBranch = createRestrictExpression(oneBranchDom)
		oneBranch.expr = createPositiveOneExpression(space)
		
		val allWMaff = '''«paramStr»->{[w]->[w-1]}'''.toString.toISLMultiAff
		val allWDepExpr = createDependenceExpression(allWMaff)
		allWDepExpr.expr = createVariableExpression(allWVar)
		
		val nbDim = combosWVar.domain.space.dim(ISLDimType.isl_dim_out)
		val combosWRange = (0..<nbDim).map[0].join(',')
		val combosWMaff = '''«paramStr»->{[w]->[«combosWRange»]}'''.toString.toISLMultiAff
		val combosWDepExpr = createDependenceExpression(combosWMaff)
		combosWDepExpr.expr = createVariableExpression(combosWVar)
		
		val be = createBinaryExpression(BINARY_OP.MUL)
		be.left = allWDepExpr
		be.right = combosWDepExpr
		
		val defaultBranchDom = '''«paramStr»->{[w] : w>1}'''.toString.toISLSet
		val defaultBranch = createRestrictExpression(defaultBranchDom)
		defaultBranch.expr = be
		
		val ce = createCaseExpression
		ce.exprs += oneBranch
		ce.exprs += defaultBranch
		
		val equ = createStandardEquation(allWVar, ce)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	def static addCombosWEquation(SystemBody systemBody, Variable combosWVar, Variable kernelWVar, Variable WVar) {
		val paramStr = combosWVar.buildParamStr
		val indexNames = combosWVar.domain.indexNames
		val kernelNames = kernelWVar.domain.getKernelNames
		
		val domainStr = (indexNames + kernelNames).join(',')
		val rangeStr = combosWVar.domain.indexNames.join(',')
		val projection = '''«paramStr»->{[«domainStr»]->[«rangeStr»]}'''.toString.toISLMultiAff
		
		
		val kernelWMaffRangeStr = indexNames.zipWith(kernelNames).map['''«key»-«value»'''].join(',')
		val kernelWMaff = '''«paramStr»->{[«domainStr»]->[«kernelWMaffRangeStr»]}'''.toString.toISLMultiAff
		val kernelWDepExpr = createDependenceExpression(kernelWMaff)
		kernelWDepExpr.expr = createVariableExpression(kernelWVar)
		
		val WMaffRangeStr = kernelNames.join(',')
		val WMaff = '''«paramStr»->{[«domainStr»]->[«WMaffRangeStr»]}'''.toString.toISLMultiAff
		val WDepExpr = createDependenceExpression(WMaff)
		WDepExpr.expr = createVariableExpression(WVar)
		
		val be = createBinaryExpression(BINARY_OP.MUL)
		be.left = kernelWDepExpr
		be.right = WDepExpr
		
		val reduceExpr = createReduceExpression(REDUCTION_OP.SUM, projection, be)
		
		val equ = createStandardEquation(combosWVar, reduceExpr)
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
	
	
	def static addWeightsEquation(SystemBody systemBody, Variable variable, Map<List<Integer>, Double> kernel) {
		val indexNames = variable.domain.indexNames
		val space = variable.domain.space
		val ce = createCaseExpression
		
		
		ce.exprs += kernel.keySet.map[indexValues | 
			val coeff = kernel.get(indexValues)
			space.createWeightRestrictBranch(indexNames, indexValues, coeff.floatValue)
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
	
	def static RestrictExpression createWeightRestrictBranch(ISLSpace space, String[] indexNames, Integer[] indexValues, float coeff) {
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
	
	def static DependenceExpression createRealDepExpr(ISLSpace space, float value) {
		createDependenceExpression(space.createConstantMaff, createRealExpression(value))
	}
	
	def static getKernelNames(ISLSet kernelDomain) {
		val nbKernel = kernelDomain.dim(ISLDimType.isl_dim_out)
		(0..<nbKernel).map[i | #['p', 'q', 'r'].get(i)] 
	}
	
	def static buildC2Domain(ISLSet baseDomain, ISLSet kernelDomain, boolean forV2) {
		if (!forV2) {
			return baseDomain.copy
		}
		val nbKernel = kernelDomain.dim(ISLDimType.isl_dim_out)
		val nbBase = baseDomain.dim(ISLDimType.isl_dim_out)
		
		val kernelNames = kernelDomain.getKernelNames
		val baseNames = baseDomain.indexNames
		val combinedNames = (baseNames + kernelNames).toList
		
		val extBaseDomain = AlphaUtil.renameIndices(
			baseDomain.copy.insertDims(ISLDimType.isl_dim_out, nbBase, nbKernel),
			combinedNames
		)
		val extKernelDomain = AlphaUtil.renameIndices(
			kernelDomain.copy.insertDims(ISLDimType.isl_dim_out, 0, nbBase),
			combinedNames
		)
		
		extBaseDomain.intersect(extKernelDomain)
	}
	
	def static buildAllWeightsDomain(Variable variable, int TT) {
		val setStr = '''«variable.buildParamStr»->{[w] : 1<=w<=«TT»}'''.toString
		setStr.toISLSet
	}
	
	def static buildPatchDomain(ISLSet WDomain, int[] tileSizes, int radius) {
		val indexNames = WDomain.indexNames
		val paramStr = WDomain.space.buildParamStr
		
		val TT = tileSizes.get(0)
		val TS = tileSizes.spatialSizes
		
		val constraints = indexNames.zipWith(TS).map[
			'''-«radius»-«radius»w<=«key»<«value»+«radius»+«radius»w'''
		].join(' and ')
		val setStr = '''«paramStr»->{[w,«indexNames.join(',')»] : 0<=w<=«TT» and «constraints»}'''.toString
		setStr.toISLSet
		
	}
	
	def static buildWeightsDomain(Variable variable, int radius) {
		val indexNames = variable.domain.indexNames
		val spaceIndexNames = (1..<indexNames.size).map[i | indexNames.get(i)]
		
		val indexStr = '''[«spaceIndexNames.join(',')»]'''
		
		val constraints = '''-«radius»<=«spaceIndexNames.join(',')»<=«radius»'''
		
		val setStr = '''«variable.buildParamStr»->{«indexStr» : «constraints»}'''.toString
		setStr.toISLSet

	}
	
	def static buildChecksumDomain(Variable variable, int[] tileSizes, int radius, boolean forV2) {
		val indexNames = variable.domain.indexNames.map[i | 't' + i]
		val indexStr = '''[«indexNames.join(',')»]'''
		
		val TT = tileSizes.get(0)
		val tt = indexNames.get(0)
		
		val timeConstraints = '''«TT»<«TT»«tt» and «TT»«tt»+«TT»<=T'''

		val spaceIndexNames = (1..<indexNames.size).map[i | indexNames.get(i)]
		val spaceTileSizes = tileSizes.spatialSizes
		val spaceConstraints = spaceTileSizes.zipWith(spaceIndexNames).map[
			val TSlb = if (forV2) key - 2*radius*TT else key
			val TSub = key
			val ts = value
			'''0<«TSlb»«ts» and «TSlb»«ts»+«TSub»<N'''
		].join(' and ')
		
		val setStr = '''«variable.buildParamStr»->{«indexStr» : «timeConstraints» and «spaceConstraints»}'''.toString
		setStr.toISLSet
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
		switch (system.name) {
			case 'star1d1r' : 1 -> #{
										#[-1]->0.3332,
										#[ 0]->0.3333,
										#[ 1]->0.1335
									}
			case 'star2d1r' : 1 -> #{
										#[ 0, 0]->0.5002,
										#[-1, 0]->0.1048,
										#[ 1, 0]->0.1249,
										#[ 0,-1]->0.1250,
										#[ 0, 1]->0.1251
									}
			case 'star3d1r' : 1 -> #{
										#[ 0, 0, 0]->0.2500,
										#[-1, 0, 0]->0.1248,
										#[ 1, 0, 0]->0.1049,
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