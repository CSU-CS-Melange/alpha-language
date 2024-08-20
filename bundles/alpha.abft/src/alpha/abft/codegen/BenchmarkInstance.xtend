package alpha.abft.codegen

import alpha.abft.codegen.util.MemoryMap
import alpha.model.AlphaSystem
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

import static extension alpha.abft.ABFT.buildParamStr
import alpha.model.CaseExpression
import alpha.abft.analysis.ConvolutionDetector
import alpha.model.Variable

class BenchmarkInstance {
	
	
	//////////////////////////////////////////////////////////////
	// Memory maps                                              //
	//////////////////////////////////////////////////////////////
	
	def static MemoryMap baselineMemoryMap(AlphaSystem system) {
		system.setModMemoryMap('Y', 'Y')
	}
	
	def static MemoryMap v1MemoryMap(AlphaSystem system) {
		system.baselineMemoryMap
	}
	
	def static MemoryMap v2MemoryMap(AlphaSystem system) {
		system.locals.filter[name.startsWith('C2_NR')].fold(
			system.baselineMemoryMap, 
			[mm, name |mm.setMemoryMap(name, 'C2')]
		)
	}
	
	def static MemoryMap v3MemoryMap(AlphaSystem system) {
//		system.locals.filter[name.startsWith('C2_NR')].fold(
//			system.baselineMemoryMap, 
//			[mm, name |mm.setMemoryMap(name, 'C2')]
//		)
//		new MemoryMap(system)
		system.setModMemoryMap('Y')
//			.setMemoryMap('C2_NR', 'C2')
			.setMemoryMap('C2_NR2', 'C2')
			.setMemoryMap('C2_NR4', 'C2')
	}
	
	
	//////////////////////////////////////////////////////////////
	// Schedules                                                //
	//////////////////////////////////////////////////////////////
	
	def static baselineSchedule(AlphaSystem system) {
		val yCaseBranches = (system.systemBodies.get(0).standardEquations.findFirst[variable.name == 'Y'].expr as CaseExpression).exprs
		
		val yStmts = (0..<yCaseBranches.size).map[i | "Y_cb" + i + "'"]
		
		'''
			domain: "domain'"
			child:
			  sequence:
			  - filter: "{ «yStmts.join('; ')» }"
			    child:
			      schedule: "params'->[\
			      	{ «yStmts.join('->[t]; ')»->[t] } \
			      ]"
«««			      options: "{ atomic[x] }"
		'''
	}
	
	def static v1Schedule(AlphaSystem system, int[] tileSizes) {
		val TT = tileSizes.get(0)
		val TSs = (1..<tileSizes.size).map[i | tileSizes.get(i)].toList
		
		val yCaseBranches = (system.systemBodies.get(0).standardEquations.findFirst[variable.name == 'Y'].expr as CaseExpression).exprs
		val yStmts = (0..<yCaseBranches.size).map[i | "Y_cb" + i + "'"]
		
		'''
			domain: "domain'"
			child:
			  sequence:
			  - filter: "{ W' }"
			  - filter: "{ patch'; patch_NR' }"
			    child:
			      schedule: "params'->[\
			        { patch'->[w]; patch_NR'->[w]} \
			      ]"
			  - filter: "{ I'; C1'; C2'; «yStmts.join('; ')» }"
			    child:
			      schedule: "params'->[\
			      	{ C1'->[tt]; C2'->[tt-1]; I'->[tt]; «yStmts.join('''->[t/«TT»]; ''')»->[t/«TT»] }, \
			      	{ C1'->[«TT»tt]; C2'->[«TT»tt-«TT»]; I'->[«TT»tt]; «yStmts.join('''->[t]; ''')»->[t] } \
			      ]"
			      child:
			        sequence:
			        - filter: "{ «yStmts.join('; ')» }"
			        - filter: "{ C1'; C2' }"
«««			        - filter: "{ Y'; C1'; C2' }"
«««			          child:
«««			            schedule: "params'->[\
«««			              «IF TSs.size == 1»
«««			              { C1'->[i]; C2'->[«TSs.get(0)»ti+p]; Y'->[i] } \
«««			              «ELSEIF TSs.size == 2»
«««			              { C1'->[i]; C2'->[«TSs.get(0)»ti+p]; Y'->[i] }, \
«««			              { C1'->[j]; C2'->[«TSs.get(1)»tj+q]; Y'->[j] } \
«««			              «ELSEIF TSs.size == 3»
«««			              { C1'->[i]; C2'->[«TSs.get(0)»ti+p]; Y'->[i] }, \
«««			              { C1'->[j]; C2'->[«TSs.get(1)»tj+q]; Y'->[j] }, \
«««			              { C1'->[k]; C2'->[«TSs.get(2)»tk+r]; Y'->[k] } \
«««			              «ENDIF»
«««			            ]"
«««			            child:
«««			              sequence:
«««			              - filter: "{ C1'; C2' }"
«««			              - filter: "{ Y' }"
			        - filter: "{ I' }"
		'''
	}
		
	def static v2Schedule(AlphaSystem system, int TT) {
		val c2nrs = system.locals.filter[name.startsWith('C2_NR')].map[name + "'"]
		
		val yCaseBranches = (system.systemBodies.get(0).standardEquations.findFirst[variable.name == 'Y'].expr as CaseExpression).exprs
		val yStmts = (0..<yCaseBranches.size).map[i | "Y_cb" + i + "'"]
		
		val c2CaseBranches = (system.systemBodies.get(0).standardEquations.findFirst[variable.name == 'C2'].expr as CaseExpression).exprs
		val c2cbes = (0..<c2CaseBranches.size).map[i | "C2_cb" + i + "'"]
		'''
		domain: "domain'"
		child:
		  sequence:
		  - filter: "{ W' }"
		  - filter: "{ WKernel' }"
		  - filter: "{ WCombos' }"
		  - filter: "{ WAll' }"
		    child:
		      schedule: "params'->[\
		        { WAll'->[w] } \
		      ]"
		  - filter: "{ \
		      «yStmts.join('; \\\n')»; \
		      C1'; \
		      «c2nrs.join('; \\\n')»; \
		      «c2cbes.join('; \\\n')»; \
		      I_NR'; \
		      I' \
		    }"
		    child:
		      schedule: "params'->[\
		        { \
		          «yStmts.join('->[t]; \\\n')»->[t]; \
		          C1'->[«TT»tt]; \
		          «c2cbes.map[c2cbe | '''«c2cbe»->[«TT»tt]'''].join('; \\\n')»; \
		          «c2nrs.map[c2nr | '''«c2nr»->[«TT»tt-w]'''].join('; \\\n')»; \
		          I'->[«TT»tt]; \
		          I_NR'->[«TT»tt] \
		        } \
		      ]"
		      child:
		        sequence:
		        - filter: "{ «yStmts.join('; ')» }"
		        - filter: "{ C1'; «c2nrs.join('; ')» }"
		        - filter: "{ «c2cbes.join('; ')» }"
		        - filter: "{ I_NR' }"
		        - filter: "{ I' }"
		'''
	}

	def static v3Schedule(AlphaSystem system, int[] tileSizes) {
		val H = tileSizes.get(0)
		val L = tileSizes.get(1)
		
		val yCaseBranches = (system.systemBodies.get(0).standardEquations.findFirst[variable.name == 'Y'].expr as CaseExpression).exprs
		val yStmts = (0..<yCaseBranches.size).map[i | "Y_cb" + i + "'"]
		
		'''
			domain: "domain'"
			child:
			  sequence:
			  - filter: "{ W' }"
			  - filter: "{ WExt' }"
			  - filter: "{ Wi' }"
			  - filter: "{ C1'; C2'; C2_NR'; C2_NR2'; C2_NR3'; C2_NR4'; I'; «yStmts.join('; ')» }"
			    child:
			      schedule: "params'->[\
			        { \
			          C1'->[«H»tt]; \
			          C2'->[t]; \
			          C2_NR'->[t]; \
			          C2_NR2'->[t]; \
			          C2_NR3'->[t]; \
			          C2_NR4'->[t]; \
			          I'->[«H»tt]; \
			          «yStmts.join('''->[t]; ''')»->[t] \
			        } \
			      ]"
			      child:
			        sequence:
			        - filter: "{ «yStmts.join('; ')» }"
			        - filter: "{ C1' }"
			        - filter: "{ C2_NR'; C2_NR2'; C2_NR3'; C2_NR4' }"
			        - filter: "{ C2' }"
			        - filter: "{ I' }"
		'''
	}

	
	//////////////////////////////////////////////////////////////
	// Misc helper functions                                    //
	//////////////////////////////////////////////////////////////

	def static getStencilVarModFactor(AlphaSystem system) {
		val convolutionKernel = ConvolutionDetector.apply(system).get(0)
		val timeDepth = convolutionKernel.timeDepth
		timeDepth + 1
	}

	/* Returns the memory map for time modulo accessing */
	def static setModMemoryMap(AlphaSystem system, String variableName) {
		system.setModMemoryMap(variableName, variableName)
	}
	
	def static setModMemoryMap(AlphaSystem system, String variableName, String rhsMemoryName) {
		(new MemoryMap(system)).setModMemoryMap(system, variableName, rhsMemoryName)
	}
	
	def static setModMemoryMap(MemoryMap mm, AlphaSystem system, String variableName) {
		mm.setModMemoryMap(system, variableName, variableName)
	}
	
	def static setModMemoryMap(MemoryMap mm, AlphaSystem system, String variableName, String rhsMemoryName) {
		val variable = system.variables.findFirst[name == variableName]
		if (variable === null)
			throw new Exception('Variable "' + variableName + '" does not exist.')
		val indexNames = variable.domain.indexNames
		val spatialIndexStr = (1..<indexNames.size).map[i | indexNames.get(i)].join(',')
		val paramStr = variable.buildParamStr
		
		val modFactor = system.stencilVarModFactor
		
		val map = '''«paramStr»->{[t,«spatialIndexStr»]->[t mod «modFactor»,«spatialIndexStr»]}'''
		
		return mm.setMemoryMap(variable.name, rhsMemoryName, map, variable.domain.indexNames)
	}
	
	
	
}