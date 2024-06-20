package alpha.codegen.tests.demandDriven;

import alpha.codegen.BaseDataType;
import alpha.codegen.Program;
import alpha.codegen.ProgramPrinter;
import alpha.codegen.demandDriven.WriteC;
import alpha.commands.Utility;
import alpha.loader.AlphaLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import java.io.FileWriter;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
@SuppressWarnings("all")
public class WriteCManualTest {
  public static void main(final String[] args) {
    try {
      final List<String> programs = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("CopyInput", "LUDecomposition", "PrefixScan"));
      for (final String program : programs) {
        {
          InputOutput.<String>println(("Writing program: " + program));
          final String alphaFile = (((("resources/" + program) + "/") + program) + ".alpha");
          final AlphaRoot root = AlphaLoader.loadAlpha(alphaFile);
          final AlphaSystem system = Utility.GetSystem(root, program);
          final Program cAST = WriteC.convert(system, BaseDataType.FLOAT, true);
          final String cProgram = ProgramPrinter.print(cAST).toString();
          final String cFile = (((("resources/" + program) + "/") + program) + ".c");
          final FileWriter writer = new FileWriter(cFile);
          writer.write(cProgram);
          writer.close();
        }
      }
      InputOutput.<String>println("Done");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
