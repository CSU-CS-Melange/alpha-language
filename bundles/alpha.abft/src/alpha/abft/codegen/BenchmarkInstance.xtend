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
	
	def static v1Schedule(int[] tileSizes) {
		val TT = tileSizes.get(0)
		val TSs = (1..<tileSizes.size).map[i | tileSizes.get(i)].toList
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
			  - filter: "{ I'; C1'; C2'; Y' }"
			    child:
			      schedule: "params'->[\
			      	{ C1'->[tt]; C2'->[tt-1]; I'->[tt]; Y'->[t/«TT»] }, \
			      	{ C1'->[«TT»tt]; C2'->[«TT»tt-«TT»]; I'->[«TT»tt]; Y'->[t] } \
			      ]"
			      child:
			        sequence:
			        - filter: "{ Y' }"
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
		        - filter: "{ C1'; «c2nrs.join('; ')» }"
		        - filter: "{ C2' }"
		        - filter: "{ I_NR' }"
		        - filter: "{ I' }"
		'''
	}

/*

tt,ti,tj,tk,p,q,r,w

C2_NR w
C2_NR2 w
C2_NR4 w
C2_NR5 w
C2_NR10 w
C2_NR11 w
C2_NR13 w
C2_NR14 w

C2_NR3 w,i
C2_NR6 w,i
C2_NR7 w,j
C2_NR8 w,j
C2_NR12 w,i
C2_NR15 w,i
C2_NR16 w,j
C2_NR17 w,j
C2_NR19 w,k
C2_NR20 w,k
C2_NR22 w,k
C2_NR23 w,k

 C2_NR9 w,i,j
C2_NR18 w,i,j
C2_NR21 w,i,k
C2_NR24 w,i,k
C2_NR25 w,j,k
C2_NR26 w,j,k

C2_NR27 w,i,j,k


  C2_NR'->[4ti+p+w,4tj+q+w,      4tk+r+w]
 C2_NR2'->[4ti+p-w+19,4tj+q+w,   4tk+r+w]
 C2_NR5'->[4ti+p-w+19,4tj+q-w+19,4tk+r+w]
 C2_NR2'->[4ti+p-w+19,4tj+q+w,   4tk+r+w]
C2_NR10'->[4ti+p+w,4tj+q+w,4tk+r-w+19]
C2_NR13'->[4ti+p+w,4tj+q-w+19,4tk+r-w+19]
C2_NR11'->[4ti+p-w+19,4tj+q+w,4tk+r-w+19]
C2_NR14'->[4ti+p-w+19,4tj+q-w+19,4tk+r-w+19]

 C2_NR3'->[i,4tj+q+w,4tk+r+w]
 C2_NR6'->[i,4tj+q-w+19,4tk+r+w]
C2_NR12'->[i,4tj+q+w,4tk+r-w+19]
C2_NR15'->[i,4tj+q-w+19,4tk+r-w+19]
 C2_NR7'->[4ti+p+w,j,4tk+r+w]
C2_NR16'->[4ti+p+w,j,4tk+r-w+19]
C2_NR19'->[4ti+p+w,4tj+q+w,k]
C2_NR22'->[4ti+p+w,4tj+q-w+19,k]
 C2_NR8'->[4ti+p-w+19,j,4tk+r+w]
C2_NR17'->[4ti+p-w+19,j,4tk+r-w+19]
C2_NR20'->[4ti+p-w+19,4tj+q+w,k]
C2_NR23'->[4ti+p-w+19,4tj+q-w+19,k]

 C2_NR9'->[i,j,4tk+r+w]
C2_NR18'->[i,j,4tk+r-w+19]
C2_NR21'->[i,4tj+q+w,k]
C2_NR24'->[i,4tj+q-w+19,k]
C2_NR25'->[4ti+p+w,j,k]
C2_NR26'->[4ti+p-w+19,j,k]

C2_NR27 Y[i,j,k]
 */


	
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