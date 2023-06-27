package alpha.louis.dev

import alpha.model.AlphaModelLoader
import alpha.model.util.Show

class EntryPoint {
	
	def static void main(String[] args) {
		
		var root = AlphaModelLoader.loadModel("resources/matmult.alpha")
		println(Show.print(root))
		
	}
}