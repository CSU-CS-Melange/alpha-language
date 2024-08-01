package alpha.abft.codegen

import alpha.abft.codegen.util.AlphaSchedule
import alpha.abft.codegen.util.MemoryMap
import alpha.abft.codegen.util.StatementVisitor
import alpha.model.AlphaSystem
import alpha.model.CaseExpression
import alpha.model.RestrictExpression
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

import static alpha.commands.UtilityBase.GetEquation

import static extension alpha.abft.ABFT.buildParamStr
import static extension alpha.model.util.CommonExtensions.toArrayList
import static extension alpha.model.util.ISLUtil.toISLSchedule

class BenchmarkInstance {
	
	def static spatialIndexNames(AlphaSystem system) {
		val indexNames = system.outputs.get(0).domain.indexNames
		return (1..<indexNames.size).map[i | indexNames.get(i)]
	}
	
	def static String stmt(ISLUnionSet uset, String name) {
		val set = uset.sets.findFirst[s | s.tupleName == name]
		set.tupleName + '[' + set.indexNames.join(',') + ']' 
	}
	
	//////////////////////////////////////////////////////////////
	// Baseline configuration                                   //
	//////////////////////////////////////////////////////////////
	
	def static MemoryMap baselineMemoryMap(AlphaSystem system) {
		val outVar = system.outputs.get(0)
		val indexNames = outVar.domain.indexNames
		val spatialIndexStr = system.spatialIndexNames.join(',')
		val paramStr = outVar.buildParamStr
		
		val outMap = '''«paramStr»->{[t,«spatialIndexStr»]->[t mod 2,«spatialIndexStr»]}'''
		
		return (new MemoryMap(system))
			.setMemoryMap('Y', 'Y', outMap, indexNames)
			
	}
	
	def static AlphaSchedule baselineSchedule(AlphaSystem system) {
		val body = system.systemBodies.get(0)
		
		val ce = body.standardEquations.get(0).expr as CaseExpression
		val exprs = ce.exprs.map[e | e as RestrictExpression].map[expr]
		
		val space = ce.contextDomain.space
		val domain = exprs
			.map[contextDomain.copy]
			.fold(
				0->ISLSet.buildUniverse(space.copy).toUnionSet,
				[ret, d | 
					val i = ret.key
					val set = ret.value
					i+1 -> d.setTupleName('S' + i).toUnionSet.union(set)
				]
			).value
		
		val S0 = domain.stmt('S0')
		val S1 = domain.stmt('S1')
		val S2 = domain.stmt('S2')
		
		val islSchedule = ISLSchedule.buildFromString(domain.context, '''
			domain: "«domain.toString»"
			child:
			  sequence:
			  - filter: "{ «S0»}"
			  - filter: "{ «S1»; «S2» }"
			    child:
			      schedule: "[T,N]->[\
			      	{ «S1»->[t]; «S2»->[t] } \
			      ]"
		''')

		val exprStmtMap = newHashMap
		exprs.forEach[e | 
			val i = exprs.indexOf(e)
			exprStmtMap.put('S' + i, e)
		]

		new AlphaSchedule(islSchedule, exprStmtMap)
	}
	
	
	
	
	//////////////////////////////////////////////////////////////
	// ABFT v1 configuration                                    //
	//////////////////////////////////////////////////////////////
	
	def static MemoryMap v1MemoryMap(AlphaSystem system) {
		system.baselineMemoryMap
	}

	def static AlphaSchedule v1Schedule(AlphaSystem system, int[] tileSizes) {
		val body = system.systemBodies.get(0)
		val outVar = system.outputs.get(0)
		
		val exprs = StatementVisitor.apply(system)
		val exprStmtMap = newHashMap
				
		val space = system.parameterDomain.space
		val domain = exprs.entrySet.map[
			val variable = key
			val childExprs = value
			childExprs.fold(
				0->ISLSet.buildEmpty(space.copy).toUnionSet,
				[kv, expr | 
					val i = kv.key
					val set = kv.value
					val name = '''S_«variable.name»_«kv.key»'''
					exprStmtMap.put(name, expr)
					i+1 -> expr.contextDomain.copy
					           .setTupleName(name)
					           .toUnionSet
					           .union(set)
				]
			).value
		].reduce[d1,d2 | d1.union(d2)]
		
		domain.sets.forEach[d | println(d)]
		
		val Ws = domain.sets.filter[s | s.tupleName.contains('S_W_')]
			.map[s | s.tupleName + '[' + s.indexNames.join(',') + ']' ]
		
		val patch0 = domain.stmt('S_patch_0')
		val patch1 = domain.stmt('S_patch_1')
		val patch2 = domain.stmt('S_patch_2')
		val patchNR = domain.stmt('S_patch_NR_0')
		
		val C1 = domain.stmt('S_C1_0')
		val C2 = domain.stmt('S_C2_0')
		val I = domain.stmt('S_I_0')
		
		// Y has been tiled so that the first dimensions match those
		// of C1, C2, and I
		val Y0 = domain.stmt('S_Y_0')
		val Y1 = domain.stmt('S_Y_1')
		val Y2 = domain.stmt('S_Y_2')
		
		val paramStr = outVar.buildParamStr
		
		val TT = tileSizes.get(0)
		
		val islSchedule = '''
			domain: "«domain.toString»"
			child:
			  sequence:
			  - filter: "{ «Ws.join(';')» }"
			  - filter: "{ «patch0»; «patch1»; «patch2»; «patchNR» }"
			    child:
			      schedule: "«paramStr»->[\
			        { «patch0»->[w]; «patch1»->[w]; «patch2»->[w]; «patchNR»->[w]} \
			      ]"
			      child:
			        sequence:
			        - filter: "{ «patchNR» }"
			        - filter: "{ «patch0»; «patch1»; «patch2» }"
			  - filter: "{ «Y0» }"
			  - filter: "{ «I»; «C1»; «C2»; «Y1»; «Y2» }"
			    child:
			      schedule: "«paramStr»->[\
			      	{ «C1»->[tt]; «C2»->[tt-1]; «I»->[tt]; «Y1»->[t/«TT»]; «Y2»->[t/«TT»] }, \
			      	{ «C1»->[«TT»tt]; «C2»->[«TT»tt-«TT»]; «I»->[«TT»tt]; «Y1»->[t]; «Y2»->[t] } \
			      ]"
			      child:
			        sequence:
			        - filter: "{ «Y1»; «Y2» }"
			        - filter: "{ «C1» }"
			        - filter: "{ «C2» }"
			        - filter: "{ «I» }"
		'''.toISLSchedule
		
		new AlphaSchedule(islSchedule, exprStmtMap)
	}
	
	
	//////////////////////////////////////////////////////////////
	// ABFT v2 configuration                                    //
	//////////////////////////////////////////////////////////////
	
	def static MemoryMap v2MemoryMap(AlphaSystem system) {
//		val c2nrs = system.locals.filter[v | v.name.contains('C2_NR')].map[name]
//		
//		c2nrs.fold(system.baselineMemoryMap, [mm, name |
//			mm.setMemoryMap(name, 'C2_NR')
//		])
		system.baselineMemoryMap
	}
	
	def static AlphaSchedule v2Schedule(AlphaSystem system, int[] tileSizes) {
		val body = system.systemBodies.get(0)
		val outVar = system.outputs.get(0)
		
		val exprs = StatementVisitor.apply(system)
		val exprStmtMap = newHashMap
				
		val space = system.parameterDomain.space
		val domain = exprs.entrySet.map[
			val variable = key
			val childExprs = value
			childExprs.fold(
				0->ISLSet.buildEmpty(space.copy).toUnionSet,
				[kv, expr | 
					val i = kv.key
					val set = kv.value
					val name = '''S_«variable.name»_«kv.key»'''
					exprStmtMap.put(name, expr)
					i+1 -> expr.contextDomain.copy
					           .setTupleName(name)
					           .toUnionSet
					           .union(set)
				]
			).value
		].reduce[d1,d2 | d1.union(d2)]
		
		domain.sets.map[toString].sort.forEach[d | println(d)]
		
		val Ws = domain.sets.filter[s | s.tupleName.contains('S_W_')]
			.map[s | s.tupleName + '[' + s.indexNames.join(',') + ']' ]
		val kernelW = domain.stmt('S_kernelW_0')
		val combosW = domain.stmt('S_combosW_0')
		val allW0 = domain.stmt('S_allW_0')
		val allW1 = domain.stmt('S_allW_1')
		
		
		val C1 = domain.stmt('S_C1_0')
		val I = domain.stmt('S_I_0')
		val INR = domain.stmt('S_I_NR_0')
		
		val nbC2s = (GetEquation(body, 'C2').expr as CaseExpression).exprs.size
		val C2Xs = (0..<nbC2s).map[i | domain.stmt('S_C2_' + i)].toArrayList
		val C2NRXs = body.standardEquations
			.filter[eq | eq.variable.name.contains('C2_NR')]
			.map['S_' + name + '_0']
			.map[domain.stmt(it)].toArrayList

		// Y has been tiled so that the first dimensions match those
		// of C1, C2, and I
		val Y0 = domain.stmt('S_Y_0')
		val Y1 = domain.stmt('S_Y_1')
		val Y2 = domain.stmt('S_Y_2')
		
		val paramStr = outVar.buildParamStr
		
		val TT = tileSizes.get(0)
		
		// TODO - undo debug
		val C2XsStr = C2Xs.join(';')
		val C2NRXsStr = C2NRXs.join(';')
//		val C2XsStr = C2Xs.get(0)
//		val C2NRXsStr = C2NRXs.get(0)
		
		
		var islSchedule = '''
			domain: "«domain.toString»"
			child:
			  sequence:
			  - filter: "{ «Ws.join(';')» }"
			  - filter: "{ «kernelW» }"
			  - filter: "{ «combosW» }"
			  - filter: "{ «allW0» }"
			  - filter: "{ «allW1» }"
			  - filter: "{ «Y0» }"
			  - filter: "{ «Y1»; «Y2» }"
			  - filter: "{ «C1»}"
			  - filter: "{ «C2NRXsStr» }"
			  - filter: "{ «C2XsStr» }"
			  - filter: "{ «INR» }"
			  - filter: "{ «I» }"
		'''
		
		val band1 = #[
			'''«Y1»->[t/«TT»]''',
			'''«Y2»->[t/«TT»]''',
			'''«C1»->[tt]''',
			'''«C2Xs.map['''«it»->[tt]'''].join(';')»''',
			'''«C2NRXs.map['''«it»->[tt-1]'''].join(';')»''',
			'''«I»->[tt]; «INR»->[tt]'''
		].join(';')
		
		val band2 = #[
			'''«Y1»->[t]''',
			'''«Y2»->[t]''',
			'''«C1»->[«TT»tt]''',
			'''«C2Xs.map[c2x | '''«c2x»->[«TT»tt]'''].join(';')»''',
			'''«C2NRXs.map[c2nrx | '''«c2nrx»->[«TT»tt-w]'''].join(';')»''',
			'''«I»->[«TT»tt]; «INR»->[«TT»tt]'''
		].join(';')

		islSchedule = '''
			domain: "«domain.toString»"
			child:
			  sequence:
			  - filter: "{ «Ws.join(';')» }"
			  - filter: "{ «kernelW» }"
			  - filter: "{ «combosW» }"
			  - filter: "{ «allW0» }"
			  - filter: "{ «allW1» }"
			  - filter: "{ «Y0» }"
			  - filter: "{ «I»; «INR»; «C1»; «C2NRXsStr»; «C2XsStr»; «Y1»; «Y2» }"
			    child:
			      schedule: "«paramStr»->[\
«««			      	{ «band1» }, \
			      	{ «band2» } \
			      ]"
			      child:
			        sequence:
			        - filter: "{ «Y1»; «Y2» }"
			        - filter: "{ «C1» }"
			        - filter: "{ «C2NRXsStr» }"
			          child:
			            schedule: "«paramStr»->[\
			              { «C2NRXs.map['''«it»->[p]'''].join(';')» } \
			            ]"
			        - filter: "{ «C2XsStr» }"
			          child:
			            schedule: "«paramStr»->[\
			              { «C2Xs.map['''«it»->[p]'''].join(';')» } \
			            ]"
			        - filter: "{ «INR» }"
			        - filter: "{ «I» }"
		'''
		
		
		new AlphaSchedule(islSchedule.toISLSchedule, exprStmtMap)
	}
	
}