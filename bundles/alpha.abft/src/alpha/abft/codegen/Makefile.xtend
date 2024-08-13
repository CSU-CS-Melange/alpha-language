package alpha.abft.codegen

import alpha.model.AlphaSystem
import java.util.List

import static alpha.abft.codegen.SystemCodeGen.ERROR_INJECTION
import static alpha.abft.codegen.SystemCodeGen.TIMING
import static alpha.abft.codegen.SystemCodeGen.NOISE

class Makefile {
	
	
	
	def static generateMakefile(AlphaSystem system, AlphaSystem systemV1, AlphaSystem systemV2, int[] tileSizes) {
		
		 
		
		val name = '''«system.name».«tileSizes.join('.')»'''
	
		val content = '''
			FLAGS := -O3 -lm
			all: bin/«name».time bin/«name».inj
			
			mkbin:
				mkdir -p bin
			
			src/time.o: src/time.c mkbin
				gcc -c $(FLAGS) -o $@ $<


			
			src/«system.name».time.o: src/«system.name».c mkbin
				gcc -c $(FLAGS) -fno-tree-vectorize  -o $@ $< -D«TIMING»

			src/«systemV1.name».time.o: src/«systemV1.name».c mkbin
				gcc -c $(FLAGS) -fno-tree-vectorize -o $@ $< -D«TIMING»
			
			src/«systemV2.name».time.o: src/«systemV2.name».c mkbin
				gcc -c $(FLAGS) -fno-tree-vectorize -o $@ $< -D«TIMING»
			
			bin/«name».time: src/«system.name»-wrapper.c src/«system.name».time.o src/«systemV1.name».time.o src/«systemV2.name».time.o src/time.o
				gcc $(FLAGS) -fno-tree-vectorize -o $@ $^ -D«TIMING»



			src/«systemV1.name».inj.o: src/«systemV1.name».c mkbin
				gcc -c $(FLAGS) -o $@ $< -D«ERROR_INJECTION»
			
			src/«systemV2.name».inj.o: src/«systemV2.name».c mkbin
				gcc -c $(FLAGS) -o $@ $<  -D«ERROR_INJECTION»
			
			bin/«name».inj: src/«system.name»-wrapper.c src/«system.name».o src/«systemV1.name».inj.o src/«systemV2.name».inj.o src/time.o
				gcc $(FLAGS) -o $@ $^ -D«ERROR_INJECTION»



			clean:
				rm -f src/*.o bin/*
		'''
		
		content
	}
	
}