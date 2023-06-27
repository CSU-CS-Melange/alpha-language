package alpha.louis.dev;

import alpha.model.AlphaModelLoader;
import alpha.model.AlphaRoot;
import alpha.model.util.Show;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class EntryPoint {
  public static void main(final String[] args) {
    try {
      AlphaRoot root = AlphaModelLoader.loadModel("resources/matmult.alpha");
      InputOutput.<String>println(Show.<AlphaRoot>print(root));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
