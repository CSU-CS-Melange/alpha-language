package alpha.targetmapping.scratch

import static extension alpha.targetmapping.util.ISLUtils.*
import alpha.model.AlphaModelLoader
import alpha.model.util.AShow
import fr.irisa.cairn.jnimap.isl.ISLASTBuild

class Scratch {
	
	def static void main(String[] args) {
		
		val root = AlphaModelLoader.loadModel('resources/star1d1r.alpha')
		val system = root.systems.get(0)
		val body = system.systemBodies.get(0)
		
		println(AShow.print(system))
		
		val prdg = PRDGBuilder.build(system)
		val schedule = prdg.computeSchedule
		
		println(prdg.prettyPrint)
		
		
	
		val build = ISLASTBuild.buildFromContext(schedule.domain.copy.params)	
		val node = build.generate(schedule.copy)
		
		println('''
			/*
			   «schedule.root»
			 */
			«node.toCString»
		''')
		
	}
}