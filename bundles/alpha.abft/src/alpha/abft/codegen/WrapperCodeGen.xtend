package alpha.abft.codegen

import alpha.abft.codegen.util.ISLASTNodeVisitor
import alpha.abft.codegen.util.MemoryMap
import alpha.model.AlphaSystem
import alpha.model.Variable
import fr.irisa.cairn.jnimap.isl.ISLASTBuild

import static extension alpha.abft.ABFT.buildParamStr
import static extension alpha.model.util.ISLUtil.toISLIdentifierList
import static extension alpha.model.util.ISLUtil.toISLSchedule

class WrapperCodeGen extends SystemCodeGen {
	
	val AlphaSystem v1System
	val AlphaSystem v2System
	
	def static generateWrapper(AlphaSystem baselineSystem, AlphaSystem v1System, AlphaSystem v2System, MemoryMap memoryMap) {
		val generator = new WrapperCodeGen(baselineSystem, v1System, v2System, memoryMap)
		generator.generate
	}
	
	new(AlphaSystem system, AlphaSystem v1System, AlphaSystem v2System, MemoryMap memoryMap) {
		super(system, memoryMap)
		this.v1System = v1System
		this.v2System = v2System
	}
	
	
	override generate() '''
		// «system.name»-wrapper.c
		
		#include<stdio.h>
		#include<stdlib.h>
		#include<math.h>
		#include<sys/time.h>
		
		#define max(x, y)   ((x)>(y) ? (x) : (y))
		#define min(x, y)   ((x)>(y) ? (y) : (x))
		#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))
		#define floord(n,d) (int)floor(((double)(n))/((double)(d)))
		#define mallocCheck(v,s,d) if ((v) == NULL) { printf("Failed to allocate memory for %s : size=%lu\n", "sizeof(d)*(s)", sizeof(d)*(s)); exit(-1); }

		// External system declarations
		«system.signature»;
		«v1System.signature»;
		«v2System.signature»;

		// Memory mapped targets
		«system.variables.reject[isLocal].map[memoryMap.getName(name) -> domain].map[memoryTargetMacro].join('\n')»
		
		// Memory access functions
		«system.variables.reject[isLocal].map[memoryMacro].join('\n')»
		
		
		
		«system.mainFunction»
		
	'''
	
	
	
	
	def mainFunction(AlphaSystem system) {
		
		val paramNames = system.parameterDomain.paramNames
		val paramInits = paramNames.map[P |
			val i = paramNames.indexOf(P)
			'''long «P» = atol(argv[«i+1»])'''
		]
		
		val code = '''
			int main(int argc, char **argv) 
			{
				if (argc < «paramNames.size + 1») {
					printf("usage: %s «paramNames.join(' ')»\n", argv[0]);
					return 1;
				}
				
				// Parse parameter sizes
				«paramInits.join(';\n')»;
				float threshold = atof(argv[«paramNames.size»]);
				«localMemoryAllocation»
				
				srand(0);
				
				«system.inputs.map[inputInitialization].join('\n')»
				«system.outputs.map[inputInitialization].join('\n')»
				
				// Baseline
				«system.call»;
				// ABFTv1
				«v1System.call»;
				// ABFTv2
				«v2System.call»;
				
				return 0;
			}
		'''
		code
	}
	
	def inputInitialization(Variable variable) {
		val stmtName = stmtPrefix + variable.name
		
		val domain = variable.domain.copy.setTupleName(stmtName)
		val indexNameStr = domain.indexNames.join(',')
		
		val paramStr = variable.buildParamStr
		
		val initSchedule = '''
			domain: "«domain.toString»"
			child:
			  schedule: "«paramStr»->[\
			    «domain.indexNames.map[i | '''{ «stmtName»[«indexNameStr»]->[«i»] }'''].join(', \\\n')» \
			  ]"
		'''.toISLSchedule
		
		val iterators = domain.indexNames.toISLIdentifierList
		val build = ISLASTBuild.buildFromContext(initSchedule.domain.copy.params)
			.setIterators(iterators.copy)
		
		val node = build.generate(initSchedule.copy)
		val codegenVisitor = new ISLASTNodeVisitor().genC(node)
		
		val code = '''
			// «variable.name» initialization
			#define «stmtName»(«indexNameStr») «variable.name»(«indexNameStr») = rand() % 100 + 1
			«codegenVisitor.toCode»
			#undef «stmtName»
		'''
		code
	}
	
	def call(AlphaSystem system) {
		system.call(#[])
	}
	
	def call(AlphaSystem system, String[] extraArgs) {
		val paramArgs = system.parameterDomain.paramNames
		val ioArgs = (system.inputs + system.outputs).map[name]
		'''«system.name»(«(paramArgs + ioArgs + extraArgs).join(', ')»)'''
	}
	
	override localMemoryAllocation() {
		(system.inputs + system.outputs).memoryAllocation
	}
	
}

