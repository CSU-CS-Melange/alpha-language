package scratch

import static extension alpha.codegen.util.CodegenUtil.*
import static extension alpha.codegen.util.MemoryUtils.*
import alpha.loader.AlphaLoader
import alpha.model.util.AShow
import alpha.codegen.constructor.WriteCProgram
import alpha.codegen.show.WriteC
import java.io.FileWriter
import java.io.File
import fr.irisa.cairn.jnimap.isl.ISLSet
import alpha.codegen.polynomial.ConstructPolynomial
import alpha.codegen.polynomial.PolynomialPrinter
import alpha.codegen.Polynomial
import alpha.model.transformation.Normalize

class Scratch {
	
	def static void main(String[] args) {
		val root = AlphaLoader.loadAlpha('resources/reduceExample.alpha')
		val system = root.getSystem('reduceExample')
		val tm = AlphaLoader.loadTargetMapping('resources/reduceExample.tm')
		val program = WriteCProgram.build(system, tm)
		println(WriteC.print(program))
	}
	
	
	def static void old2(String[] args) {
		
		val root = AlphaLoader.loadAlpha('resources/star1d1r.alpha')
		//val root = AlphaLoader.loadAlpha('resources/ex.alpha')
		val tm = AlphaLoader.loadTargetMapping('resources/star1d1r.tm')
		
		for (system : root.systems) {
			//println(AShow.print(system))
			Normalize.apply(system)
			println(AShow.print(system))
			
			val program = WriteCProgram.build(system, tm)
			
			println(WriteC.print(program))
		}
		
	}
	
	
	
	
	
	def static void old(String[] args) {
		
		val domain = '[N,M]->{[i,j]: 0<=i<=j<N }'.toSet
		val map = '[N,M]->{[i,j]->[i,j]}'.toMap
		val M = "[N,M,i,j]->{[i',j']: 0<=i'<=j'<N and 0<=i<=j<N and i'<=i and j'<=j}".toSet
		
		val allocationDomain = domain.appli(map)
		
		/*
		 * given a mapping, construct the CString for the cardinality
		 * and the indexing expression to access the mapping element
		 */
		 
		 
		 
		 
		
		val polynomial = ConstructPolynomial.toPolynomial(allocationDomain.card)
		
		val P = ConstructPolynomial.toPolynomial(M.card)
		println(PolynomialPrinter.print(P, '...'))
		
		try (val fw = new FileWriter(new File('''/Users/lw/tmp/scratch/main.c'''), false)) {
			val lines = '''
				#include<stdio.h>
				#include<stdlib.h>
				
				int main(int argc, char** argv) {
					
					long N = atoi(argv[0]);
					long M = atoi(argv[1]);
					
					«polynomial.toCString('X')»
					float* X = (float*)malloc(sizeof(float) * _card_X);
					
					#define X(«domain.indexNames.join(',')») X[«linearAccessFunction(domain)»]
				}
			'''
			println(lines)
			fw.write(lines)
    		fw.close();
		}
		
		
	}
	
	def static String linearAccessFunction(ISLSet set) {
		
		
		println
		'...'
	}
	
	
	
	
	
	
}