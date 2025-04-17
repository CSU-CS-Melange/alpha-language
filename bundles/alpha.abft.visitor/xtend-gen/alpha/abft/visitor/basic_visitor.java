package alpha.abft.visitor;

import alpha.loader.AlphaLoader;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaModelSaver;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.transformation.AABFT;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.reduction.ReductionComposition;
import alpha.model.util.AShow;
import alpha.model.util.Show;
import java.io.File;
import java.util.Scanner;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class basic_visitor {
  public static void main(final String[] args) {
    try {
      final String in_dir = "resources/base/";
      final String out_dir = "resources/auto/";
      final Scanner scanner = new Scanner(System.in);
      InputOutput.<String>print((("Enter input alpha file in \'" + in_dir) + "\': "));
      String sys_name = scanner.nextLine();
      scanner.close();
      boolean _endsWith = sys_name.endsWith(".alpha");
      boolean _not = (!_endsWith);
      if (_not) {
        sys_name = sys_name.concat(".alpha");
      }
      final String in_file = (in_dir + sys_name);
      final String out_file = (out_dir + sys_name);
      final File file = new File(in_file);
      if (((!file.exists()) || file.isDirectory())) {
        InputOutput.<String>println((("ERROR:  \'" + in_file) + "\' does not exist. Exiting..."));
        System.exit(1);
      } else {
        InputOutput.<String>println((("Reading \'" + in_file) + "\'"));
      }
      InputOutput.println();
      final AlphaRoot root = AlphaLoader.loadAlpha(in_file);
      final AlphaSystem system = root.getSystems().get(0);
      InputOutput.<String>println(Show.<AlphaSystem>print(system));
      InputOutput.<String>println("---------------------------------");
      AABFT.apply(system);
      String _name = system.getName();
      String _plus = (_name + "_aabft");
      system.setName(_plus);
      Normalize.apply(system);
      ReductionComposition.apply(system);
      AlphaInternalStateConstructor.recomputeContextDomain(system);
      InputOutput.<String>println("-------------------\nNormalized system:\n");
      InputOutput.<String>println(AShow.print(system));
      InputOutput.<String>println((("Model saved to \'" + out_file) + "\'"));
      AlphaModelSaver.ASave(root, out_file);
      InputOutput.<String>println("Done");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
