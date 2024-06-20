package alpha.codegen.tests.demandDriven

import alpha.codegen.BaseDataType
import alpha.codegen.ProgramPrinter
import alpha.codegen.demandDriven.WriteC
import alpha.commands.Utility
import alpha.loader.AlphaLoader
import java.io.FileWriter

/**
 * These are some manual tests which convert systems into C programs.
 * The programs are written to folders that already contain wrappers,
 * verification code, and a makefile.
 * 
 * To test if a program is correct:
 * 1. Run the "main" method here to generate all of the C files.
 * 2. Open a terminal on a system with "gcc" and "make" installed.
 * 3. Navigate to one of the programs to check.
 * 4. Run "make clean" to clean up any previous build files.
 * 5. Run "make verify-rand" to build the verification program.
 * 6. Run that verification program (which has a suffix of ".verify-rand").
 *    You will need to provide values for the parameters with this command.
 * 7. Look at the output and check that it says that all the tests passed.
 * 8. Repeat steps 3-7 for all programs.
 */
class WriteCManualTest {
	def static void main(String[] args) {
		val programs = #[
			"CopyInput",
			"LUDecomposition",
			"PrefixScan"
		]
		
		for (program : programs) {
			println("Writing program: " + program)
			val alphaFile = "resources/" + program + "/" + program + ".alpha"
			val root = AlphaLoader.loadAlpha(alphaFile)
			val system = Utility.GetSystem(root, program)
			
			// Convert the system using older AlphaZ compatibility.
			val cAST = WriteC.convert(system, BaseDataType.FLOAT, true)
			val cProgram = ProgramPrinter.print(cAST).toString
			
			// Write the C program out.
			val cFile = "resources/" + program + "/" + program + ".c"
			val writer = new FileWriter(cFile)
			writer.write(cProgram)
			writer.close()
		}
		println("Done")
	}
}
