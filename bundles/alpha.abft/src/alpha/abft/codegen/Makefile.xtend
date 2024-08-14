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
				gcc -c $(FLAGS) -o $@ $< -D«TIMING»
			«IF systemV1 !== null»
			src/«systemV1.name».time.o: src/«systemV1.name».c mkbin
				gcc -c $(FLAGS) -o $@ $< -D«TIMING»
			«ENDIF»
			«IF systemV2 !== null»
			src/«systemV2.name».time.o: src/«systemV2.name».c mkbin
				gcc -c $(FLAGS) -o $@ $< -D«TIMING»
			«ENDIF»
			
			bin/«name».time: src/«system.name»-wrapper.c src/«system.name».time.o src/time.o«if (systemV1 !== null) ''' src/«systemV1.name».time.o'''»«if (systemV2 !== null) ''' src/«systemV2.name».time.o'''»
				gcc $(FLAGS) -o $@ $^ -D«TIMING»


			«IF systemV1 !== null»
			src/«systemV1.name».inj.o: src/«systemV1.name».c mkbin
				gcc -c $(FLAGS) -o $@ $< -D«ERROR_INJECTION»
			«ENDIF»
			«IF systemV2 !== null»
			src/«systemV2.name».inj.o: src/«systemV2.name».c mkbin
				gcc -c $(FLAGS) -o $@ $<  -D«ERROR_INJECTION»
			«ENDIF»
			bin/«name».inj: src/«system.name»-wrapper.c src/«system.name».o src/time.o«if (systemV1 !== null) ''' src/«systemV1.name».inj.o'''»«if (systemV2 !== null) ''' src/«systemV2.name».inj.o'''»
				gcc $(FLAGS) -o $@ $^ -D«ERROR_INJECTION»



			clean:
				rm -f src/*.o bin/*
		'''
		
		content
	}
	
}