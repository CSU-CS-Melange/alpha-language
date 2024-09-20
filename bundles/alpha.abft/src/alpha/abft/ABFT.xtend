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

class ABFT {
	
	static HashMap<String, AlphaRoot> loadOrGet = newHashMap
	
	def static void main(String[] args) {
		
		'star1d1r'.insertChecksumV1(#[3, 10])
		'star1d1r'.insertChecksumV2(#[3, 16])
				
	}
	
	
	def static insert_checksums(String systemName, int[] tileSizes) {
		systemName.insertChecksumV1(tileSizes)
		systemName.insertChecksumV2(tileSizes)
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
	
	def static insertChecksumV3(String systemName, int[] tileSizes) {
		val system =  loadSystem('''«systemName».alpha''', systemName)
		system.insertChecksumV3(tileSizes)
		
		/* Save the augemented system */
		system.save
	}
	
	def static insertChecksumV1(AlphaSystem system, int[] tileSizes) {
		system.insertChecksumV1(tileSizes, true)
	}
	def static insertChecksumV1(AlphaSystem system, int[] tileSizes, boolean renameSystem) {
		val systemBody = system.systemBodies.get(0)
		val outputVar = system.outputs.get(0)
		
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

		Normalize.apply(system)
		NormalizeReduction.apply(system)
		
		system
	}
	
	def static insertChecksumV2(AlphaSystem system, int[] tileSizes) {
		system.insertChecksumV2(tileSizes, true)
	}
	def static insertChecksumV2(AlphaSystem system, int[] tileSizes, boolean renameSystem) {
		val systemBody = system.systemBodies.get(0)
		val outputVar = system.outputs.get(0)
		
		/*
		 * Pattern match on convolutions
		 */
		val convolutionKernel = system.identify_convolution
		val radius = convolutionKernel.key
		val kernel = convolutionKernel.value
		
		val TT = tileSizes.get(0)
		
		/*
		 * Assert that tile sizes are valid given the radius, in other words
		 * the domains are hyper-trapezoidal and not hyper-triangular
		 */
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
		val kernelWVar = createVariable('WKernel', kernelDomain.copy)
		val combosWVar = createVariable('WCombos', kernelDomain.copy)
		val allWVar = createVariable('WAll', allWeightsDomain.copy)
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
			val TSs = (1..<tileSizes.size).map[i |
				tileSizes.get(i) - 2 * TT * radius
			]
			val _tileSizes = #[TT] + TSs 
			system.rename(_tileSizes, 'v2')
		}
		
		Normalize.apply(system)
		NormalizeReduction.apply(system)
		
		system
	}
	
	def static insertChecksumV3(AlphaSystem system, int[] tileSizes) {
		system.insertChecksumV3(tileSizes, true)
	}
	def static insertChecksumV3(AlphaSystem system, int[] tileSizes, boolean renameSystem) {
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
		val checksumDomain = outputVar.buildChecksumDomainV3(convolutionKernel, H, L, spaceTileDim)
		val weightsDomain = outputVar.buildWeightsDomainV3(convolutionKernel)
		val kernelDomain = outputVar.buildWeightsDomainV3(convolutionKernel, 2)
//		val C2Domain = checksumDomain.buildC2DomainV3(kernelDomain, true)
		
//		/*
//		 * Add checksum related variables
//		 */
		val IVar = createVariable('I', checksumDomain.copy)
		val WVar = createVariable('W', weightsDomain.copy)
		val CVar = createVariable('C1', checksumDomain.copy)
		val WExtVar = createVariable('WExt', kernelDomain.copy)
		val WiVar = createVariable('Wi', kernelDomain.copy)
		val CiVar = createVariable('C2', checksumDomain.copy)
		system.locals.addAll(#[IVar, WVar, WExtVar, WiVar, CVar, CiVar])
//		
//		/*
//		 * Add the respective equations
//		 */
		systemBody.addWeightsEquation(WVar, convolutionKernel)
		systemBody.addKernelWEquation(WExtVar, weightsDomain)
		systemBody.addWiEquationV3(WiVar, WExtVar, WVar, spaceTileDim)
		systemBody.addCEquationV3(CVar, outputVar, WVar, convolutionKernel, spaceTileDim, H, L)
		systemBody.addC2EquationV3(CiVar, outputVar, WVar, convolutionKernel, spaceTileDim, H, L)
//		systemBody.addV2C2Equation(C2Var, outputVar, WVar, allWVar, combosWVar, tileSizes, radius)
		systemBody.addIEquation(IVar, CVar, CiVar, WVar, false)
		
		if (renameSystem) {
			system.rename(#[H, L], 'v3')
		}
		
		Normalize.apply(system)
		NormalizeReduction.apply(system)
		
		system
	}
	
	def static addC2EquationV3(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, ConvolutionKernel convolutionKernel, int spaceTileDim, int H, int L) {
		val equ = createStandardEquation(CVar, createZeroExpression(CVar.domain.space))
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	def static addCEquationV3(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, ConvolutionKernel convolutionKernel, int spaceTileDim, int H, int L) {
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
	
	def static addWiEquationV3(SystemBody systemBody, Variable WiVar, Variable WExtVar, Variable WVar, int spaceTileDim) {
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
	
	def static buildChecksumDomainV3(Variable variable, ConvolutionKernel convolutionKernel, int H, int L, int spaceTileDim) {
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
	
	
	
	
	
	
	
	
	
	
	///////////////////////////
	//
	///////////////////////////
	
	
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
		
//		val binExpr = createBinaryExpression(BINARY_OP.MUL, createNegativeOneExpression(CVar.domain.space), reduceExpr)
		
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
			'''1<«TSlb»«ts» and «TSlb»«ts»+«TSub»<N'''
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