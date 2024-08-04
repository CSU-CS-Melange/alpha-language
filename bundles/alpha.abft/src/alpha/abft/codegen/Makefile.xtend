package alpha.abft.codegen

import alpha.model.AlphaSystem
import java.util.List

class Makefile {
	
	def static generateMakefile(AlphaSystem system, List<List<Integer>> sizes) {
	
		val sizeSuffixes = sizes.map[join('_')]
		val name = system.name
		
		val v1DepTargets = sizeSuffixes.map[s | '''«system.name»_abft_v1_«s»''']
		val v1Targets = v1DepTargets.map[s | '''
			«s».o: src/«s».c src/time.o mkdir
				gcc -c -o src/$@ $<
		''']
		
		val v2DepTargets = sizeSuffixes.map[s | '''«system.name»_abft_v2_«s»''']
		val v2Targets = v2DepTargets.map[s | '''
			«s».o: src/«s».c src/time.o mkdir
				gcc -c -o src/$@ $<
		''']
		
	
		val content = '''
			all: bin/«name»
			
			mkdir:
				mkdir -p bin
				
			src/time.o: src/time.c
				gcc -c -o $@ $<
			
			«name».o: src/«name».c mkdir
				gcc -c -o src/$@ $<
			
			«v1Targets.join('\n\n')»
			
			«v2Targets.join('\n\n')»
			
			bin/«name»: src/«name»-wrapper.c src/«name».o «(v1DepTargets + v2DepTargets).map[t | 'src/' + t + '.o'].join(' ')» src/time.o mkdir
				gcc -o $@ src/«name»-wrapper.c src/«name».o «(v1DepTargets + v2DepTargets).map[t | 'src/' + t + '.o'].join(' ')» src/time.o
			
			clean:
				rm -f src/*.o bin/*
		'''
		
		content
	}
	
}