package alpha.abft.codegen

import alpha.model.AlphaSystem

class Makefile {
	
	def static generateMakefile(AlphaSystem inputSystem, AlphaSystem v1System, AlphaSystem v2System) '''
		all: bin/«inputSystem.name» bin/«v1System.name» bin/«v2System.name»
		
		mkdir:
			mkdir -p bin
		
		bin/«inputSystem.name»: src/«inputSystem.name».c mkdir
			gcc -o $@ $<
		
		bin/«v1System.name»: src/«v1System.name».c mkdir
			gcc -o $@ $<
			
		bin/«v2System.name»: src/«v2System.name».c mkdir
			gcc -o $@ $<
		
		clean:
			rm -f bin/*
	'''
	
	
}