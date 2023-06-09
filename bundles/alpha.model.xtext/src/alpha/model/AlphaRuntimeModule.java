/*
 * generated by Xtext 2.22.0
 */
package alpha.model;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.parser.IAstFactory;
import org.eclipse.xtext.parser.antlr.IPartialParsingHelper;

import alpha.model.construction.AlphaCustomASTFactory;
import alpha.model.construction.AlphaCustomValueConverter;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class AlphaRuntimeModule extends AbstractAlphaRuntimeModule {
	
	@Override
	public Class<? extends IAstFactory> bindIAstFactory() {
		return AlphaCustomASTFactory.class;
	}
	
	@Override
	public Class<? extends IPartialParsingHelper> bindIPartialParserHelper() {
		return null;
	}
	
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return AlphaCustomValueConverter.class;
	}
}
