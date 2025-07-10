package alpha.abft.visitor

import alpha.model.AlphaSystem
import alpha.model.util.Show
import alpha.loader.AlphaLoader
//import java.util.Scanner
import java.io.File
import alpha.codegen.demandDriven.WriteC
import alpha.codegen.BaseDataType
import alpha.codegen.ProgramPrinter
import java.io.FileWriter

class external_writer {
	
	static def void main(String[] args){
		val in_dir		= 'resources/external/'
		val out_dir		= in_dir + 'written/'
		
//		// Prompt user for input file
//		val scanner = new Scanner(System.in)
//		print("Enter input alpha file in \'" + in_dir + "\': ")
//		var sys_name = scanner.nextLine()
//		scanner.close()
//		
//		if(!sys_name.endsWith('.alpha')){
//			sys_name = sys_name.concat('.alpha')
//		}
		
		// Define input/output files		
		val in_file = in_dir + 'ext2.alpha'
		
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
		
		val out_file = out_dir + 'ext.c'
		
		println(Show.print(system))
		println("---------------------------------")
		
				// C-code Writer testing
		val program = WriteC.convert(system, BaseDataType.FLOAT, true)
		
		val code = ProgramPrinter.print(program).toString
 
		println(code)
		
		try{
			val File f = new File(out_file)
			val FileWriter fw = new FileWriter(f, false)
			fw.write(code)
			fw.close()
		}
		catch(Exception e){
			println(e)
		}
		
		

//		system.runOSR	
	}
}