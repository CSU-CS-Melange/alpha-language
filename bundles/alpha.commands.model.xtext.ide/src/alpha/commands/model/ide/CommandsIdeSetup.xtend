/*
 * generated by Xtext 2.15.0
 */
package alpha.commands.model.ide

import alpha.commands.model.CommandsRuntimeModule
import alpha.commands.model.CommandsStandaloneSetup
import com.google.inject.Guice
import org.eclipse.xtext.util.Modules2

/**
 * Initialization support for running Xtext languages as language servers.
 */
class CommandsIdeSetup extends CommandsStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new CommandsRuntimeModule, new CommandsIdeModule))
	}
	
}