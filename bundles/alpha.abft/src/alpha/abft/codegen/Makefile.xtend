package alpha.abft.codegen

import alpha.model.AlphaSystem
import java.util.List

class Makefile {
	
	def static generateMakefile(AlphaSystem system, AlphaSystem systemV1, AlphaSystem systemV2, int[] tileSizes) {
		
		 
		
		val name = '''«system.name».«tileSizes.join('.')»'''
	
		val content = '''
			FLAGS := -O3 -lm
			all: bin/«name» bin/«name».inj
			
			mkbin:
				mkdir -p bin
			
			src/time.o: src/time.c mkbin
				gcc -c $(FLAGS) -o $@ $<
			
			src/«system.name».o: src/«system.name».c mkbin
				gcc -c $(FLAGS) -o $@ $<


			src/«systemV1.name».o: src/«systemV1.name».c mkbin
				gcc -c $(FLAGS) -o $@ $<	
			
			src/«systemV2.name».o: src/«systemV2.name».c mkbin
				gcc -c $(FLAGS) -o $@ $<			
			
			bin/«name»: src/«system.name»-wrapper.c src/«system.name».o src/«systemV1.name».o src/«systemV2.name».o src/time.o
				gcc $(FLAGS) -o $@ $^


			src/«systemV1.name».inj.o: src/«systemV1.name».c mkbin
				gcc -c $(FLAGS) -o $@ $< -DERROR_INJECTION			
			
			src/«systemV2.name».inj.o: src/«systemV2.name».c mkbin
				gcc -c $(FLAGS) -o $@ $<  -DERROR_INJECTION
			
			bin/«name».inj: src/«system.name»-wrapper.c src/«system.name».o src/«systemV1.name».inj.o src/«systemV2.name».inj.o src/time.o
				gcc $(FLAGS) -o $@ $^ -DERROR_INJECTION

			
			clean:
				rm -f src/*.o bin/*
		'''
		
		content
	}
	
}