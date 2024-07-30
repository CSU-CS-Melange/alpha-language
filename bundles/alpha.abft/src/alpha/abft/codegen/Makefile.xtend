package alpha.abft.codegen

import alpha.model.AlphaSystem

class Makefile {
	
	def static generateMakefile(AlphaSystem system) '''
		all: system
	'''
	
}