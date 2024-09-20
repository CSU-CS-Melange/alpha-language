package alpha.abft

import alpha.abft.analysis.ConvolutionDetector
import alpha.abft.util.ConvolutionKernel
import alpha.model.AlphaExpression
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaSystem
import alpha.model.BINARY_OP
import alpha.model.REDUCTION_OP
import alpha.model.RestrictExpression
import alpha.model.SystemBody
import alpha.model.Variable
import alpha.model.transformation.Normalize
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.util.AlphaUtil
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLVal
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT

import static alpha.model.factory.AlphaUserFactory.*

import static extension alpha.model.util.AlphaUtil.copyAE
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.CommonExtensions.zipWith
import static extension alpha.model.util.ISLUtil.toISLMultiAff
import static extension alpha.model.util.ISLUtil.toISLSet

class ABFTv3 extends ABFTv1 {
	
	def static void main(String[] args) {
		
		'star3d1r'.insertChecksum(#[5, 50])
//		'star1d1r'.insertChecksum(#[3, 10])
				
	}
	
	def static insertChecksum(String systemName, int[] tileSizes) {
		val system =  loadSystem('''«systemName».alpha''', systemName)
		system.insertChecksum(tileSizes)
		
		system.pprint('ABFT augmented system:')
		
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
//			.intersect('''[T,N]->{[1,1]}'''.toString.toISLSet)
		val checksumCiDomain = outputVar.buildChecksumDomain(convolutionKernel, #[spaceTileDim->L])
//			.intersect('''[T,N]->{[16,1]}'''.toString.toISLSet)
		
		val weightsDomain = outputVar.buildWeightsDomainV3(convolutionKernel)
		val kernelDomain = outputVar.buildWeightsDomainV3(convolutionKernel, 2)
		
		/*
		 * Add checksum related variables
		 */
		val IVar = createVariable('I', checksumCDomain.copy)
		val WVar = createVariable('W', weightsDomain.copy)
		val CVar = createVariable('C1', checksumCDomain.copy)
		val WExtVar = createVariable('WExt', kernelDomain.copy)
		val WiVar = createVariable('Wi', kernelDomain.copy)
		val CiVar = createVariable('C2', checksumCiDomain.copy)
		system.locals.addAll(#[IVar, WVar, WExtVar, WiVar, CVar, CiVar])
		
		/*
		 * Add the respective equations
		 */
		systemBody.addWeightsEquation(WVar, convolutionKernel)
		systemBody.addKernelWEquation(WExtVar, weightsDomain)
		systemBody.addWiEquation(WiVar, WExtVar, WVar, spaceTileDim)
		systemBody.addCEquation(CVar, outputVar, WVar, convolutionKernel, spaceTileDim, H, L)
		systemBody.addCiEquation(CiVar, outputVar, WVar, WiVar, convolutionKernel, spaceTileDim, H, L)
		systemBody.addIEquation(IVar, CVar, CiVar, WVar, H)
		
		system.save
		
		if (renameSystem) {
			system.rename(#[H, L], 'v3')
		}
		
		Normalize.apply(system)
		NormalizeReduction.apply(system)
		
		system
	}
	
	def static addCiEquation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, Variable WiVar, ConvolutionKernel convolutionKernel, int spaceTileDim, int H, int L) {
		
		val ce = createCaseExpression
		
		// recursive case branch
		val centerDomain = ABFTv3.createCiCenterDomain(CVar, convolutionKernel, spaceTileDim)
		val CiBoundaryExpr = ABFTv3.createCiCenterExpr(CVar, WVar, WiVar, stencilVar, spaceTileDim, H, L)
		val CiStencilReduceExpr = createCiStencilReduceExpr(CVar, WVar, WiVar, spaceTileDim)
		val be = createBinaryExpression(BINARY_OP.ADD, CiBoundaryExpr, CiStencilReduceExpr)
		val re = createRestrictExpression(centerDomain, be)

		
		// boudnary branch reduction
		val contextIndexNames = CVar.domain.indexNames + #[stencilVar.domain.indexNames.get(spaceTileDim)]
		val stencilVarExpr = createStencilVarExpr(stencilVar, contextIndexNames)
		val reduceExpr = createChecksumReduceExpression(CVar, stencilVar, spaceTileDim, H, L, stencilVarExpr)
		val boundaryDomain = createCiBoundaryDomain(CVar, convolutionKernel, spaceTileDim)
		val re2 = createRestrictExpression(boundaryDomain, reduceExpr)
		
		ce.exprs += re
		ce.exprs += re2
		
		val equ = createStandardEquation(CVar, ce)
		
		systemBody.equations += equ
		
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
		
	}
	
	def static createCiCenterExpr(Variable CVar, Variable WVar, Variable WiVar, Variable stencilVar, int spaceTileDim, int H, int L) {
		/*  C[t-1,ti,j,k] * (Wi[-1,0,0,0] - W[-1,0,0,0]
		 *  + reduce(+, [p], {:p<0} : W(h,p,0,0) * (Y[t+h,10ti+p,j,k] - Y[t+h,10ti+10+p,j,k]))
		 *  - reduce(+, [p], {:p>0} : W(h,p,0,0) * (Y[t+h,10ti-1+p,j,k] - Y[t+h,10ti+10-1+p,j,k]))
		 * 
		 *  or
		 * 
		 * C[t-1,ti,j,k] * (Wi[-1,0,0,0] - W[-1,0,0,0]
		 *  + reduce(+, [p], {:p<0} : W(h,p,0,0) * (Y[t+h,10ti+p,j,k] - Y[t+h,10ti+10+p,j,k]))
		 *  + reduce(+, [p], {:p>0} : W(h,p,0,0) * (Y[t+h,10ti+10-1+p,j,k] + Y[t+h,10ti-1+p,j,k]))
		 */
		val indexNames = CVar.domain.indexNames
		val paramStr = CVar.buildParamStr
		val kernelNames = WVar.domain.getKernelNames.toList
		
		////////////////////////////////////////////////////////////
		//  C[t-1,ti-1,j,k] * (Wi[h,0,0,0] - W[h,0,0,0]
		////////////////////////////////////////////////////////////

		val CWExpr = {
			val CMaff = '''«paramStr»->{[«indexNames.join(',')»]->[«indexNames.map[n | 
				if (n=='t') 
					't-1'
				else 
					n
			].join(',')»]}'''.toString.toISLMultiAff
			val CDepExpr = createDependenceExpression(CMaff, createVariableExpression(CVar))
			
			val WxMaff = '''«paramStr»->{[«kernelNames.join(',')»]->[«kernelNames.map[n | if (n == 'h') -1 else 0].join(',')»]}'''.toString.toISLMultiAff 
			val WiDepExpr = createDependenceExpression(WxMaff.copy, createVariableExpression(WiVar))
			val WDepExpr = createDependenceExpression(WxMaff.copy, createVariableExpression(WVar))
			val WBinExpr = createBinaryExpression(BINARY_OP.SUB, WiDepExpr, WDepExpr)
			
			createBinaryExpression(BINARY_OP.MUL, CDepExpr, WBinExpr)
		}
		
		// Everything below goes inside the reduceExpression with the bodyIndexNames
		
		val pqrName = kernelNames.get(spaceTileDim)
		val bodyIndexNames = (indexNames + #['h', kernelNames.get(spaceTileDim)])
		val bodyStr = bodyIndexNames.join(',')
		val projection = '''«paramStr»->{[«bodyStr»]->[«indexNames.join(',')»]}'''.toString		
		
		////////////////////////////////////////////////////////////
		//  W(h,p,0,0)
		////////////////////////////////////////////////////////////
		
		val WMaff = '''«paramStr»->{[«bodyStr»]->[«kernelNames.map[n | if (#[0,spaceTileDim].contains(kernelNames.indexOf(n))) n else 0].join(',')»]}'''.toString.toISLMultiAff
		val WExpr = createDependenceExpression(WMaff, createVariableExpression(WVar))
		
		////////////////////////////////////////////////////////////
		//  Y[t+h,10ti+p,j,k]
		//  Y[t+h,10ti+10+p,j,k]		
		////////////////////////////////////////////////////////////
		
		val stencilVarExprL1 = stencilVar.createStencilVarCiExpr(indexNames, bodyIndexNames, kernelNames, spaceTileDim, L)
		val stencilVarExprL2 = stencilVar.createStencilVarCiExpr(indexNames, bodyIndexNames, kernelNames, spaceTileDim, L, L)
		
		////////////////////////////////////////////////////////////
		//  Y[t+h,10ti-1+p,j,k]
		//  Y[t+h,10ti+10-1+p,j,k]
		////////////////////////////////////////////////////////////
		val stencilVarExprR1 = stencilVar.createStencilVarCiExpr(indexNames, bodyIndexNames, kernelNames, spaceTileDim, L, -1)
		val stencilVarExprR2 = stencilVar.createStencilVarCiExpr(indexNames, bodyIndexNames, kernelNames, spaceTileDim, L, L-1)
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		//  reduce(+, [p], {:p<0} : W(h,p,0,0) * (Y[t+h,10ti+p,j,k] - Y[t+h,10ti+10-1+p,j,k]))
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		val negReduceExpr = {
			val domain = '''«paramStr»->{[«bodyIndexNames.join(',')»] : «pqrName»<0}'''.toString.toISLSet
			val stencilVarDiffExpr = createBinaryExpression(BINARY_OP.SUB, stencilVarExprL1, stencilVarExprL2)
			val WYBinExpr = createBinaryExpression(BINARY_OP.MUL, WExpr.copyAE, stencilVarDiffExpr)
			val body = createRestrictExpression(domain, WYBinExpr)
			createReduceExpression(REDUCTION_OP.SUM, projection.toISLMultiAff, body)
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		//  reduce(+, [p], {:p>0} : W(h,p,0,0) * (Y[t+h,10ti+10-1+p,j,k] - Y[t+h,10ti+p,j,k]))
		/////////////////////////////////////////////////////////////////////////////////////////////////
		
		val posReduceExpr = {
			val domain = '''«paramStr»->{[«bodyIndexNames.join(',')»] : «pqrName»>0}'''.toString.toISLSet 
			val stencilVarDiffExpr = createBinaryExpression(BINARY_OP.SUB, stencilVarExprR2, stencilVarExprR1)
			val WYBinExpr = createBinaryExpression(BINARY_OP.MUL, WExpr.copyAE, stencilVarDiffExpr)
			val body = createRestrictExpression(domain, WYBinExpr)
			createReduceExpression(REDUCTION_OP.SUM, projection.toISLMultiAff, body)
		}
		
		////////////////////////////////////////////////////////////
		//  negReduce(...) - posReduce(...)
		////////////////////////////////////////////////////////////
		
		val diffReduceExpr = createBinaryExpression(BINARY_OP.ADD, negReduceExpr, posReduceExpr)
		
		////////////////////////////////////////////////////////////
		//  CWExpr + diffReduceExpr
		////////////////////////////////////////////////////////////
		
		createBinaryExpression(BINARY_OP.ADD, CWExpr, diffReduceExpr)
	}
	
	def static createStencilVarCiExpr(Variable stencilVar, String[] indexNames, String[] bodyIndexNames, String[] kernelNames, int spaceTileDim, int L) {
		stencilVar.createStencilVarCiExpr(indexNames, bodyIndexNames, kernelNames, spaceTileDim, L, 0)
	}
	def static createStencilVarCiExpr(Variable stencilVar, String[] indexNames, String[] bodyIndexNames, String[] kernelNames, int spaceTileDim, int L, int offset) {
			val varAcc = (0..<indexNames.size).map[i |
				val cIndexName = indexNames.get(i)
				val pqrName = kernelNames.get(i)
				switch (i) {
					case 0 : '''«cIndexName»+«pqrName»'''
					case spaceTileDim : '''«L»«cIndexName»+«if (offset != 0) offset + '+'»«pqrName»'''
					default : cIndexName
				}
			].join(',')
			val stencilVarMaff = '''«stencilVar.buildParamStr»->{[«bodyIndexNames.join(',')»]->[«varAcc»]}'''.toString.toISLMultiAff
			createDependenceExpression(stencilVarMaff, createVariableExpression(stencilVar))			
		}
	
	def static createCiStencilReduceExpr(Variable CVar, Variable WVar, Variable WiVar, int spaceTileDim) {
		val paramStr = CVar.buildParamStr
		val indexNames = CVar.domain.indexNames
		val kernelNames = WVar.domain.getKernelNames.toList
		val pqrExprs = kernelNames.map[e | if (kernelNames.indexOf(e)==spaceTileDim) 0 else e]
		val pqrStr = pqrExprs.join(',')
		
		
		/* 
		 * if tileDim is 0		->		W[h,0,q,r] * Ci[t-h,ti-1,j+q,k+r]
		 * if tileDim is 1		->		W[h,p,0,r] * Ci[t-h,i+p,tj-1,k+r]
		 * if tileDim is 2		->		W[h,p,q,0] * Ci[t-h,i+p,j+q,tk-1]
		 */
		val domainStr = (indexNames + pqrExprs.reject[e | e == 0]).join(',')
		val rangeStr = CVar.domain.indexNames.join(',')
		val projection = '''«paramStr»->{[«domainStr»]->[«rangeStr»]}'''.toString
		
		val WMaff = '''«paramStr»->{[«domainStr»]->[«pqrStr»]}'''.toString.toISLMultiAff
		val WDepExpr = createDependenceExpression(WMaff, createVariableExpression(WVar))
		
		val CiAcc = indexNames.zipWith(pqrExprs).map[
			val indexName = key
			val pqrExpr = value
			if (indexNames.indexOf(indexName) == spaceTileDim)
				'''«indexName»'''
			else
				'''«indexName»+«pqrExpr»'''
		].join(',')
		val CiMaff = '''«paramStr»->{[«domainStr»]->[«CiAcc»]}'''.toString.toISLMultiAff
		val CiDepExpr = createDependenceExpression(CiMaff, createVariableExpression(CVar))
		
//		val zero = createZeroExpression(space)
		val body = createBinaryExpression(BINARY_OP.MUL, WDepExpr, CiDepExpr)
		
		createReduceExpression(REDUCTION_OP.SUM, projection.toISLMultiAff, body)
	}
	
	def static createCiBoundaryDomain(Variable CVar, ConvolutionKernel convolutionKernel, int spaceTileDim) {
//		// 1D
//		'''[T,N]->{[t,ti] : t<3 }'''.toString.toISLSet
		// 3D  
//		'''[T,N]->{[t,ti,j,k] : t <= 3 or j<3 or j>N-3 or k<3 or k>N-3 }'''.toString.toISLSet
		val universe = ISLSet.buildUniverse(CVar.domain.space.copy)
		val centerDomainStr = CVar.createCiCenterDomain(convolutionKernel, spaceTileDim).toString
		
		val centerDomain = ISLSet.buildFromString(ISLContext.instance, centerDomainStr)
		
		val paramStr = CVar.buildParamStr
		val indexNames = CVar.domain.indexNames
		
		val constraints = centerDomain.copy.basicSets.flatMap[getConstraints]
			.map[aff]
			.map[negate]
			.map[aff | aff.toString(ISL_FORMAT.C) + ' > 0']
			.join(' or ')
			
		'''«paramStr»->{[«indexNames.join(',')»] : «constraints» }'''.toString.toISLSet
//			.map[toInequalityConstraint]
//			.map[toBasicSet]
//			.map[toSet]
//			.map[s | 
//				println(s)
//				s
//			].toArrayList
//			.reduce[s1, s2 | s1.union(s2)]
//			.removeRedundancies
//			.coalesce
			
//		universe.subtract(centerDomain)
	}
	
	def static createCiCenterDomain(Variable CVar, ConvolutionKernel convolutionKernel, int spaceTileDim) {
		val nbDims = CVar.domain.dim(ISLDimType.isl_dim_out)
		
		val radius = ISLVal.buildRationalValue(ISLContext.instance, convolutionKernel.radius, 1)
		val timeDepth = ISLVal.buildRationalValue(ISLContext.instance, convolutionKernel.timeDepth, 1)
		val oneVal = ISLVal.buildRationalValue(ISLContext.instance, 1, 1)

		val involvesTime = [ISLConstraint c | c.involvesDims(ISLDimType.isl_dim_out, 0, 1)]
		val isLB = [ISLConstraint c, int dim | c.isLowerBound(ISLDimType.isl_dim_out, dim)]
		val isUB = [ISLConstraint c, int dim | c.isUpperBound(ISLDimType.isl_dim_out, dim)]
	
		// tighten domain by radius along each dimension
		val domain = CVar.domain.basicSets.map[
				val timeConstraints = constraints.filter[c | involvesTime.apply(c) && isLB.apply(c, 0)].map[c |
					val coeff = c.getCoefficientVal(ISLDimType.isl_dim_out, 0)
					val const = c.getConstantVal
					c.setConstant(const.sub(coeff.mul(timeDepth.copy)))
				].toArrayList;
				val spaceConstraints = constraints.reject[c | involvesTime.apply(c)].map[c | 
					val const = c.getConstantVal
					c.setConstant(const.sub(radius.copy).sub(oneVal.copy))
				].toArrayList;
				(timeConstraints + spaceConstraints).map[toBasicSet].reduce[b1, b2 | b1.intersect(b2)]
					.dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, nbDims)
					.toSet
			]
			.reduce[s1, s2 | s1.union(s2)]
			.dropConstraintsInvolvingDims(ISLDimType.isl_dim_out, spaceTileDim, 1)
		
		
		domain
	}
	
	def static addWiEquation(SystemBody systemBody, Variable WiVar, Variable WExtVar, Variable WVar, int spaceTileDim) {
		val paramStr = WiVar.buildParamStr
		val indexNames = WiVar.domain.indexNames
		val pqrName = WiVar.domain.getKernelNames.get(spaceTileDim)
		
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
	def static getKernelNames(ISLSet kernelDomain) {
		val nbKernel = kernelDomain.dim(ISLDimType.isl_dim_out)
		(0..<nbKernel).map[i | #['h', 'p', 'q', 'r'].get(i)] 
	}
	
//	def static addCiEquation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, ConvolutionKernel convolutionKernel, int spaceTileDim, int H, int L) {
//		
//		val ce = createCaseExpression
//		
//		// base branch reduction
//		val contextIndexNames = CVar.domain.indexNames + #[stencilVar.domain.indexNames.get(spaceTileDim)]
//		val stencilVarExpr = createStencilVarExpr(stencilVar, contextIndexNames)
//		val reduceExpr = createChecksumReduceExpression(CVar, stencilVar, spaceTileDim, H, L, stencilVarExpr)
//		val boundaryDomain = createCiBoundaryDomain(convolutionKernel)
//		val re = createRestrictExpression(boundaryDomain, reduceExpr)
//		
//		// recursive case branch
//		val autoRe = createAutoRestrictExpression(createZeroExpression(convolutionKernel.domain.space))
//		
//		ce.exprs += re
//		ce.exprs += autoRe
//		
//		val equ = createStandardEquation(CVar, ce)
//		
//		systemBody.equations += equ
//		AlphaInternalStateConstructor.recomputeContextDomain(equ)
//	}
	
	def static addCEquation(SystemBody systemBody, Variable CVar, Variable stencilVar, Variable WVar, ConvolutionKernel convolutionKernel, int spaceTileDim, int H, int L) {
		val contextIndexNames = CVar.domain.indexNames + #[stencilVar.domain.indexNames.get(spaceTileDim)]
		val stencilVarExpr = createStencilVarExpr(stencilVar, contextIndexNames, H)
		val reduceExpr = createChecksumReduceExpression(CVar, stencilVar, spaceTileDim, H, L, stencilVarExpr)
		
		val equ = createStandardEquation(CVar, reduceExpr)
		systemBody.equations += equ
		AlphaInternalStateConstructor.recomputeContextDomain(equ)
	}
	
	def static createStencilVarExpr(Variable stencilVar, String[] contextIndexNames) {
		val stencilIndexNames = stencilVar.domain.indexNames
		val varAcc = (#[contextIndexNames.get(0)] + (1..<stencilIndexNames.size).map[stencilIndexNames.get(it)]).join(',')
		val stencilVarMaff = '''«stencilVar.buildParamStr»->{[«contextIndexNames.join(',')»]->[«varAcc»]}'''.toString
		createDependenceExpression(stencilVarMaff.toISLMultiAff, createVariableExpression(stencilVar))
	}
	
	def static createStencilVarExpr(Variable stencilVar, String[] contextIndexNames, int H) {
		val stencilIndexNames = stencilVar.domain.indexNames
		val varAcc = (#[H + contextIndexNames.get(0)] + (1..<stencilIndexNames.size).map[stencilIndexNames.get(it)]).join(',')
		val stencilVarMaff = '''«stencilVar.buildParamStr»->{[«contextIndexNames.join(',')»]->[«varAcc»]}'''.toString.toISLMultiAff
		createDependenceExpression(stencilVarMaff, createVariableExpression(stencilVar))
	}
	
	def static createChecksumReduceExpression(Variable CVar, Variable stencilVar, int spaceTileDim, int H, int L, AlphaExpression stencilVarExpr) {
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
		
		val restrictExpr = createRestrictExpression(bodyDom)
		restrictExpr.expr = stencilVarExpr
		
		createReduceExpression(REDUCTION_OP.SUM, projection, restrictExpr)
	}
	
	def static addIEquation(SystemBody systemBody, Variable IVar, Variable C1Var, Variable C2Var, Variable WVar, int H) {
		val indexNames = IVar.domain.indexNames
		val spaceIdxStr = (1..<indexNames.size).map[i | indexNames.get(i)].join(',')
		
		val paramStr = C2Var.buildParamStr
		
		val C2Maff = '''«paramStr»->{[tt,«spaceIdxStr»]->[«H»tt,«spaceIdxStr»]}'''.toString.toISLMultiAff
		
		val C2Expr = createDependenceExpression(C2Maff, createVariableExpression(C2Var))
		
		systemBody.addIEquation(IVar, C1Var, C2Var, WVar, C2Expr)
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
	
	def static buildChecksumDomain(Variable variable, ConvolutionKernel convolutionKernel, Pair<Integer, Integer>[] tileSizePairs) {
		
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
					val coeffVal = c.getCoefficientVal(ISLDimType.isl_dim_out, tileDim)
					val tc = c.copy.setCoefficient(ISLDimType.isl_dim_out, tileDim, coeffVal.mul(tileSize.copy))
					if (!c.isUpperBound(ISLDimType.isl_dim_out, tileDim))
						return tc
					val const = tc.getConstantVal
					tc.setConstant(const.sub(tileSize.copy))
				]
			].flatten.toArrayList
			
			val remainingConstraints = bs.constraints.reject[c | involvesAny.apply(c)].toArrayList;
			(tiledConstraints + remainingConstraints)
				.map[toBasicSet].reduce[b1, b2 | b1.intersect(b2)].toSet
		].reduce[s1, s2 | s1.union(s2)]
		
		AlphaUtil.renameIndices(tiledDomain, indexNames)
	}
	
//	def static buildChecksumDomain(Variable variable, ConvolutionKernel convolutionKernel, int H, int L, int spaceTileDim) {
//		val domain = variable.domain.copy.intersect(convolutionKernel.domain.copy)
//		
//		val HVal = ISLVal.buildRationalValue(ISLContext.instance, H, 1)
//		val LVal = ISLVal.buildRationalValue(ISLContext.instance, L, 1)
//		
//		// rename the time and spaceTileDim with the prefix 't'
//		val indexNames = (0..<domain.indexNames.size).map[i | 
//			val name = domain.indexNames.get(i);
//			if (i == 0 || i == spaceTileDim) 't' + name else domain.indexNames.get(i)
//		].toList
//		
//		/* We want to effectively tile some dimensions in the domain.
//		 * This can be done by simply multiplying the coefficient all of constraints involving the
//		 * dim to-be-tiled with the desired tile size, and then treating this new dim as a tile dim. 
//		 */
//		val involvesTime = [ISLConstraint c | c.involvesDims(ISLDimType.isl_dim_out, 0, 1)]
//		val involvesSpace = [ISLConstraint c, int i | c.involvesDims(ISLDimType.isl_dim_out, i, 1)]
//		val involvesEither = [ISLConstraint c, int i | involvesTime.apply(c) || involvesSpace.apply(c, i)]
//		
//		val tiledDomain = domain.basicSets.map[bs |
//			val timeConstraints = bs.constraints.filter[c | involvesTime.apply(c)].map[c | 
//				val coeffVal = c.getCoefficientVal(ISLDimType.isl_dim_out, 0)
//				c.copy.setCoefficient(ISLDimType.isl_dim_out, 0, coeffVal.mul(HVal.copy))
//			]
//			val spaceConstraints = bs.constraints.filter[c | involvesSpace.apply(c, spaceTileDim)].map[c | 
//				val coeffVal = c.getCoefficientVal(ISLDimType.isl_dim_out, spaceTileDim)
//				c.copy.setCoefficient(ISLDimType.isl_dim_out, spaceTileDim, coeffVal.mul(HVal.copy))
//			]
//			val remainingConstraints = bs.constraints.reject[c | involvesEither.apply(c, spaceTileDim)]
//			(timeConstraints + spaceConstraints + remainingConstraints)
//				.map[toBasicSet].reduce[b1, b2 | b1.intersect(b2)].toSet
//		].reduce[s1, s2 | s1.union(s2)]
//		
//		AlphaUtil.renameIndices(tiledDomain, indexNames)
//	}
	
	
}