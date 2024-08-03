package alpha.abft.codegen

import alpha.abft.codegen.util.MemoryMap
import alpha.model.AlphaSystem
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

import static extension alpha.abft.ABFT.buildParamStr

class BenchmarkInstance {
	
	
	//////////////////////////////////////////////////////////////
	// Memory maps                                              //
	//////////////////////////////////////////////////////////////
	
	def static MemoryMap baselineMemoryMap(AlphaSystem system) {
		system.yMod2MemoryMap
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
	
	
	
	//////////////////////////////////////////////////////////////
	// Schedules                                                //
	//////////////////////////////////////////////////////////////
	
	def static baselineSchedule() '''
		domain: "domain'"
		child:
		  sequence:
		  - filter: "{ Y' }"
		    child:
		      schedule: "params'->[\
		      	{ Y'->[t] } \
		      ]"
	'''
	
	def static v1Schedule(int TT) '''
		domain: "domain'"
		child:
		  sequence:
		  - filter: "{ W' }"
		  - filter: "{ patch'; patch_NR' }"
		    child:
		      schedule: "params'->[\
		        { patch'->[w]; patch_NR'->[w]} \
		      ]"
		  - filter: "{ I'; C1'; C2'; Y' }"
		    child:
		      schedule: "params'->[\
		      	{ C1'->[tt]; C2'->[tt-1]; I'->[tt]; Y'->[t/«TT»] }, \
		      	{ C1'->[«TT»tt]; C2'->[«TT»tt-«TT»]; I'->[«TT»tt]; Y'->[t] } \
		      ]"
		      child:
		        sequence:
		        - filter: "{ Y' }"
		        - filter: "{ C1' }"
		        - filter: "{ C2' }"
		        - filter: "{ I' }"
	'''
	
	def static v2Schedule(AlphaSystem system, int TT) {
		val c2nrs = system.locals.filter[name.startsWith('C2_NR')].map[name + "'"]
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
		      Y'; \
		      C1'; \
		      «c2nrs.join('; \\\n')»; \
		      C2'; \
		      I_NR'; \
		      I' \
		    }"
		    child:
		      schedule: "params'->[\
		        { \
		          Y'->[t]; \
		          C1'->[«TT»tt]; \
		          C2'->[«TT»tt]; \
		          «c2nrs.map[c2nr | '''«c2nr»->[«TT»tt-w]'''].join('; \\\n')»; \
		          I'->[«TT»tt]; \
		          I_NR'->[«TT»tt] \
		        } \
		      ]"
		      child:
		        sequence:
		        - filter: "{ Y' }"
		        - filter: "{ C1' }"
		        - filter: "{ \
		            «c2nrs.join('; \\\n')» \
		          }"
		        - filter: "{ C2' }"
		        - filter: "{ I_NR' }"
		        - filter: "{ I' }"
		'''
	}




	
	//////////////////////////////////////////////////////////////
	// Misc helper functions                                    //
	//////////////////////////////////////////////////////////////

	/* Returns the memory map for Y%2 accessing */
	def static yMod2MemoryMap(AlphaSystem system) {
		val outVar = system.outputs.get(0)
		val indexNames = system.outputs.get(0).domain.indexNames
		val spatialIndexStr = (1..<indexNames.size).map[i | indexNames.get(i)].join(',')
		val paramStr = outVar.buildParamStr
		
		val outMap = '''«paramStr»->{[t,«spatialIndexStr»]->[t mod 2,«spatialIndexStr»]}'''
		
		return (new MemoryMap(system))
			.setMemoryMap('Y', 'Y', outMap, outVar.domain.indexNames)
	}
	
	
	
}