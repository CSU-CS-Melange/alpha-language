package alpha.targetmapping.scratch;

import alpha.model.AlphaModelLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.SystemBody;
import alpha.model.util.AShow;
import fr.irisa.cairn.jnimap.isl.ISLASTBuild;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLScheduleNode;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Scratch {
  public static void main(final String[] args) {
    try {
      final AlphaRoot root = AlphaModelLoader.loadModel("resources/star1d1r.alpha");
      final AlphaSystem system = root.getSystems().get(0);
      final SystemBody body = system.getSystemBodies().get(0);
      InputOutput.<String>println(AShow.print(system));
      final PRDG prdg = PRDGBuilder.build(system);
      final ISLSchedule schedule = prdg.computeSchedule();
      InputOutput.<String>println(prdg.prettyPrint());
      final ISLASTBuild build = ISLASTBuild.buildFromContext(schedule.getDomain().copy().params());
      final ISLASTNode node = build.generate(schedule.copy());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("   ");
      ISLScheduleNode _root = schedule.getRoot();
      _builder.append(_root, "   ");
      _builder.newLineIfNotEmpty();
      _builder.append(" ");
      _builder.append("*/");
      _builder.newLine();
      String _cString = node.toCString();
      _builder.append(_cString);
      _builder.newLineIfNotEmpty();
      InputOutput.<String>println(_builder.toString());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
