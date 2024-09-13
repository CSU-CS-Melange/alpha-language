package louis.hook

import alpha.loader.AlphaLoader
import alpha.model.AlphaCompleteVisitable
import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaModelSaver
import alpha.model.AlphaSystem
import alpha.model.ReduceExpression
import alpha.model.StandardEquation
import alpha.model.transformation.automation.OptimalSimplifyingReductions
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.model.util.AShow
import org.eclipse.emf.ecore.util.EcoreUtil

class Lock {
	
	
	def static void main(String[] args) {
		
		val system = 'fractal.alpha'.loadSystem('fractal2d')
	
		system.pprint('Input program')
		
		val optSystems = system.runOSR(false)
		val optSystem = optSystems.get(1)
		
		optSystem.pprint('SR')
		
	}
	
	
	def static AlphaSystem[] runOSR(AlphaSystem system, boolean saveToFile) {		
		val limit = 0
		val targetComplexity = 1
		val verbose = true
		val doSplitting = true
		val osr = OptimalSimplifyingReductions.apply(system, limit, targetComplexity, doSplitting, verbose)
		osr.printSummary(saveToFile)
		
		osr.optimizations.get(targetComplexity).map[root.systems.get(0)]
	}
	
	def static AlphaSystem loadSystem(String filename, String systemName) {
		val root = AlphaLoader.loadAlpha('resources/' + filename)
		if (root === null) {
			throw new Exception('Failed to load example, double check spelling')
		}
		
		root.getSystem(systemName)
	}
	
	def static void printSummary(OptimalSimplifyingReductions osr, boolean saveToFile) {
		val optimizations = osr.optimizations
		
		val foundOptimizations = optimizations.keySet
			.reject[k | optimizations.get(k).size == 0]
			.map[k | k -> optimizations.get(k)]
			.toList
		foundOptimizations.forEach[keyOpts | 
			val opts = keyOpts.value
			opts.forEach[
				val s = root.systems.get(0)
//				SubstituteAll.apply(s, true)
//				Normalize.apply(s)
				NormalizeReduction.apply(s)
				val idx = opts.indexOf(it)
				val retString = '''
				// simplification v«idx»
				«showSteps»
				«AShow.print(s)»
				'''
				println(retString)
				if (saveToFile)
					AlphaModelSaver.writeToFile('resources/opt/v' + idx + '.alpha', retString)
			]
		]
		
		println
		foundOptimizations.forEach[keyOpts | 
			val key = keyOpts.key
			val opts = keyOpts.value
			println('Number of ' + key + 'D optimizations: ' + opts.size)
		]
	}
	
	def static reductionsNotNormalized(AlphaSystem s) {
		EcoreUtil.getAllContents(#[s])
			.filter[av | av instanceof ReduceExpression]
			.map[it as ReduceExpression]
			.reject[re | re.eContainer instanceof StandardEquation]
			.size > 0
	}

	def static pprint(AlphaCompleteVisitable av, String str) {
		println(str + ':')
		println(AShow.print(av))
	}
	def static pprint(AlphaExpressionVisitable aev, String str) {
		println(str + ':')
		println(AShow.print(aev))
	}
	
}