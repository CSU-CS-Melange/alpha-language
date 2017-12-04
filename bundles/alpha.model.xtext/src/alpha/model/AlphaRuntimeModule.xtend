/*
 * generated by Xtext 2.13.0
 */
package alpha.model

import alpha.model.construction.AlphaCustomASTFactory
import alpha.model.construction.AlphaCustomValueConverter

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class AlphaRuntimeModule extends AbstractAlphaRuntimeModule {
	
	override bindIAstFactory() {
		return AlphaCustomASTFactory
	}
	
	override bindIPartialParserHelper() {
		return null;
	}
	
	override bindIValueConverterService() {
		return AlphaCustomValueConverter
	}
}
