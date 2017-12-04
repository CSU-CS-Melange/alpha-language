/**
 * generated by Xtext 2.13.0
 */
package alpha.model.ide;

import alpha.model.AlphaRuntimeModule;
import alpha.model.AlphaStandaloneSetup;
import alpha.model.ide.AlphaIdeModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
@SuppressWarnings("all")
public class AlphaIdeSetup extends AlphaStandaloneSetup {
  @Override
  public Injector createInjector() {
    AlphaRuntimeModule _alphaRuntimeModule = new AlphaRuntimeModule();
    AlphaIdeModule _alphaIdeModule = new AlphaIdeModule();
    return Guice.createInjector(Modules2.mixin(_alphaRuntimeModule, _alphaIdeModule));
  }
}
