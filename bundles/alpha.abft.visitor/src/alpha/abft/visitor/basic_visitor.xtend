package alpha.abft.visitor

import alpha.model.AlphaSystem
import alpha.model.AlphaInternalStateConstructor
import alpha.model.AlphaModelSaver
import alpha.model.util.Show
import alpha.model.util.AShow
import alpha.model.transformation.AABFT
import alpha.model.transformation.Normalize
import alpha.model.transformation.reduction.ReductionComposition
import alpha.loader.AlphaLoader
import java.util.Scanner
import java.io.File


class basic_visitor {
	def static void main(String[] args) {
		val in_dir   = 'resources/base/'
		val out_dir  = 'resources/auto/'
		
		// Prompt user for input file
		val scanner = new Scanner(System.in)
		print("Enter input alpha file in \'" + in_dir + "\': ")
		var sys_name = scanner.nextLine()
		scanner.close()
		
		if(!sys_name.endsWith('.alpha')){
			sys_name = sys_name.concat('.alpha')
		}
		
		// Define input/output files		
		val in_file = in_dir + sys_name
		val out_file = out_dir + sys_name
		
		// Check if input file exists
		val file = new File(in_file)
		if(!file.exists() || file.isDirectory()){
			println("ERROR:  \'" + in_file + "\' does not exist. Exiting...")
			System.exit(1)
		}
		else{
			println("Reading \'" + in_file + "\'")
		}
		println()
			 
		val root = AlphaLoader.loadAlpha(in_file)
				
		val AlphaSystem system	= root.systems.get(0)
		
		println(Show.print(system))
		println("---------------------------------")
		
		// Apply ABFT using visitor framework
		AABFT.apply(system)
		
		// Update system name
		system.name = system.name + '_aabft'
		
		// Normalize the system
		Normalize.apply(system)
		ReductionComposition.apply(system)
		AlphaInternalStateConstructor.recomputeContextDomain(system)		

//		println("---------------------------------")
//		println(Show.print(system))

		println("-------------------\nNormalized system:\n")	
		println(AShow.print(system))
		
		// Save new alpha program
		println("Model saved to \'" + out_file + "\'")
//		AlphaModelSaver.writeToFile(out_file, AShow.print(system))
		AlphaModelSaver.ASave(root, out_file)
		println("Done")
		
	}
}