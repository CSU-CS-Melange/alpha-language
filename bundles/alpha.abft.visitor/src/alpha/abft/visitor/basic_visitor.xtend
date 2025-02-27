package alpha.abft.visitor

import alpha.model.AlphaModelLoader
import alpha.model.AlphaRoot
import alpha.model.util.Show
import alpha.model.AlphaSystem
import alpha.model.prdg.PRDG
import alpha.model.prdg.PRDGGenerator
import alpha.model.transformation.AABFT

class basic_visitor {
	def static void main(String[] args) {
		val AlphaRoot root = AlphaModelLoader.loadModel("resources/matmult.alpha")
//		println(Show.print(root)) 

//		println("---------------------------------")		
		val AlphaSystem system = root.systems.get(0)
		
		println(Show.print(system))
		
		AABFT.apply(system)
		
		

		
		
	}
}