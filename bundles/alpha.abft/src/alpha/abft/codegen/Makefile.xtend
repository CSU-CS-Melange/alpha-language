package alpha.abft.codegen

import alpha.model.AlphaSystem

class Makefile {
	
	def static generateMakefile(AlphaSystem inputSystem, AlphaSystem v1System, AlphaSystem v2System) '''
		all: baseline v1 v2
		
		baseline: «inputSystem.name».c
			gcc -o $@ $^
		
		v1: «v1System.name».c
			gcc -o $@ $^
			
		v2: «v2System.name».c
			gcc -o $@ $^
		
		clean:
			rm -f «inputSystem.name» «v1System.name» «v2System.name»
	'''
	
	
}