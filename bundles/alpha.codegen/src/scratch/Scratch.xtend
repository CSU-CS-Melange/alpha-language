package scratch

import alpha.codegen.constructor.WriteCProgram
import alpha.codegen.polynomial.PolynomialPrinter
import alpha.codegen.show.WriteC
import alpha.codegen.util.MemoryUtils
import alpha.loader.AlphaLoader
import alpha.model.transformation.Normalize
import alpha.model.util.AShow
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT

import static extension alpha.codegen.util.CodegenUtil.*
import static extension alpha.codegen.util.MemoryUtils.*
import fr.irisa.cairn.jnimap.isl.ISLPoint
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLVal
import alpha.codegen.factory.Factory

class Scratch {
	
	def static ISLSet set(String str) {
		return ISLSet.buildFromString(ISLContext.instance, str)
	}
	
	def static ISLVal value(long value) {
		return ISLVal.buildFromLong(ISLContext.instance, value)
	}
	
	def static void main(String[] args) {
		val root = AlphaLoader.loadAlpha('resources/reduceExample.alpha')
		val system = root.getSystem('reduceExample')
		val tm = AlphaLoader.loadTargetMapping('resources/reduceExample.tm')
		val program = WriteCProgram.build(system, tm)
		println(WriteC.print(program))
	}
	
	def static void main2(String[] args) {
		val domain = ISLSet.buildFromString(ISLContext.instance, "[N] -> {[i]: 0<=i<=N}")
		val rank = domain.copy.rank
		println(rank)
//		println(domain.copy.card.coalesce)
		
		val poly = Factory.createPolynomial(rank.copy.coalesce)
		val printed = PolynomialPrinter.print(poly)
		println(printed)
		val printed2 = PolynomialPrinter.printMinusOne(poly)
		println(printed2)
		println()
		println("ISL C printing:")
		println(ISLPWQPolynomial._toString(rank.copy, ISL_FORMAT.C.ordinal))
		println()
		println("Out of Bounds Test")
		val point = ISLPoint.buildZero(rank.domainSpace)
			.setCoordinate(ISLDimType.isl_dim_param, 0, value(-1))
			.setCoordinate(ISLDimType.isl_dim_param, 1, value(-2))
		println(point)
		println(rank.eval(point))
		
//		println()
//		println("Pieces:")
//		domain.copy.card.pieces.forEach[println(it.qp)]
//		
//		println()
//		println("Gist:")
//		val gistDom = ISLSet.buildFromString(ISLContext.instance, "[N,I,J] -> {: 0<=J<=I<N}")
//		println(domain.copy.card.gist(gistDom))
//		
//		println()
//		println("Minus one:")
//		val minusOne = ISLSet.buildFromString(ISLContext.instance, "[N,I,J] -> { [i,j] : 0<=j<=i<N and 0<=J<=I<N and i=I and j=J }").card.neg
//		println(minusOne)
//		
//		println()
//		println("Sub:")
//		val sub = domain.copy.card.add(minusOne.copy)
//		println(sub)
//		val one = ISLSet.buildFromString(ISLContext.instance, "[N,I,J] -> {[i,j] : 0<=j<=i<N and 0<=J<=I<N and i=I and j=J}")
//		println(one.copy.card)
//		println()
//		
//		val sub = domain.card.sub(one.card) 
//		println(sub)
	}
	
	def static void main3(String[] args) {
		val domain = ISLSet.buildFromString(ISLContext.instance, "[N] -> {[i,j]: 0<=j<=2i<=N}")
		val rank = MemoryUtils.rank(domain)
		println("Cardinality:")
		println(rank)
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
	
	
	
	
	
	def static void old3(String[] args) {
		
		val domain = '[N,M]->{[i,j]: 0<=i<=j<N }'.toSet
		val map = '[N,M]->{[i,j]->[i,j]}'.toMap
		val M = "[N,M,i,j]->{[i',j']: 0<=i'<=j'<N and 0<=i<=j<N and i'<=i and j'<=j}".toSet
		
		val allocationDomain = domain.appli(map)
		
		/*
		 * given a mapping, construct the CString for the cardinality
		 * and the indexing expression to access the mapping element
		 */
		 
		 
		 
		 
		
		val polynomial = Factory.createPolynomial(allocationDomain.card)
		
		val P = Factory.createPolynomial(M.card)
		println(PolynomialPrinter.print(P, '...'))
		
//		try (val fw = new FileWriter(new File('''/Users/lw/tmp/scratch/main.c'''), false)) {
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
//			fw.write(lines)
//    		fw.close();
//		}
		
		
	}
	
	def static String linearAccessFunction(ISLSet set) {
		
		
		println
		return '...'
	}
	
	
	
	
	
	
}