package alpha.abft.visitor;

import alpha.model.AlphaModelLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.transformation.AABFT;
import alpha.model.util.Show;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class basic_visitor {
  public static void main(final String[] args) {
    try {
      final AlphaRoot root = AlphaModelLoader.loadModel("resources/matmult.alpha");
      final AlphaSystem system = root.getSystems().get(0);
      InputOutput.<String>println(Show.<AlphaSystem>print(system));
      AABFT.apply(system);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
