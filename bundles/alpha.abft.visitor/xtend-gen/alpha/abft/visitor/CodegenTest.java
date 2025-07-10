package alpha.abft.visitor;

import alpha.codegen.BaseDataType;
import alpha.codegen.Program;
import alpha.codegen.ProgramPrinter;
import alpha.codegen.demandDriven.WriteC;
import alpha.codegen.scheduledC.ScheduledC;
import alpha.model.AlphaModelLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.memorymapper.IdentityMemoryMapper;
import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGGenerator;
import alpha.model.scheduler.FoutrierScheduler;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.reduction.NormalizeReduction;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class CodegenTest {
  public static void main(final String[] args) {
    try {
      final String file = "resources/auto/matmult_aabft.alpha";
      final AlphaRoot root = AlphaModelLoader.loadModel(file);
      AlphaSystem system = root.getSystems().get(0);
      Program s_code = null;
      Program d_code = null;
      boolean inlineKeyword = false;
      boolean inlineCode = false;
      BaseDataType base_type = BaseDataType.FLOAT;
      Normalize.apply(system);
      NormalizeReduction.apply(system);
      final PRDG prdg = PRDGGenerator.apply(system);
      FoutrierScheduler scheduler = new FoutrierScheduler(prdg);
      IdentityMemoryMapper mapper = new IdentityMemoryMapper();
      s_code = ScheduledC.convert(system, base_type, scheduler, null, mapper, true, inlineKeyword, inlineCode);
      d_code = WriteC.convert(system, BaseDataType.FLOAT, true);
      InputOutput.<Iterable<String>>println(prdg.show());
      InputOutput.<CharSequence>println(ProgramPrinter.print(s_code));
      InputOutput.<String>println("-------------");
      InputOutput.<CharSequence>println(ProgramPrinter.print(d_code));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
