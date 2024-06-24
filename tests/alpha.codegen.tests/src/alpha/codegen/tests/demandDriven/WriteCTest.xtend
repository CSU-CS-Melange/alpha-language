package alpha.codegen.tests.demandDriven

import alpha.codegen.BaseDataType
import alpha.codegen.ProgramPrinter
import alpha.codegen.demandDriven.WriteC
import alpha.model.AlphaModelLoader
import org.junit.Test

import static org.junit.Assert.*

import static extension alpha.commands.UtilityBase.*

class WriteCTest {
	/** The path to the Alpha file for these unit tests. */
	static val alphaFile = "resources/alpha.codegen.tests.demandDriven/WriteCTest.alpha";
	
	/** Gets the desired system for these unit tests. */
	static def getSystem(String system) {
		return AlphaModelLoader.loadModel(alphaFile)
			.GetSystem(system)
	}
	
	@Test
	def void convertSystem_noParameters() {
		val system = getSystem("NoParameters")
		val program = WriteC.convert(system, BaseDataType.FLOAT, false)
		val code = ProgramPrinter.print(program)
		assertNotNull(code)
		assertNotEquals("", code)
	}
	
	@Test
	def void convertSystem_noParameterConstraints() {
		val system = getSystem("NoParameterConstraints")
		val program = WriteC.convert(system, BaseDataType.FLOAT, false)
		val code = ProgramPrinter.print(program)
		assertNotNull(code)
		assertNotEquals("", code)
	}
}
