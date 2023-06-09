/*
 * generated by Xtext 2.15.0
 */
package alpha.commands.model

import fr.irisa.cairn.xtend.protectedregion.ProtectedRegionResolverAsPostProcessor
import org.eclipse.xtext.generator.IFilePostProcessor
import org.eclipse.xtext.naming.IQualifiedNameProvider
import alpha.commands.model.scoping.CommandsQualifiedNameProvider

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class CommandsRuntimeModule extends AbstractCommandsRuntimeModule {
	
	def Class<? extends IFilePostProcessor> bindPostProcessor() {
		return ProtectedRegionResolverAsPostProcessor;
	}
	
	override Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return CommandsQualifiedNameProvider
	}
}
