/*
 * generated by Xtext 2.18.0.M3
 */
package alpha.commands.model.parser.antlr;

import alpha.commands.model.parser.antlr.internal.InternalCommandsParser;
import alpha.commands.model.services.CommandsGrammarAccess;
import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class CommandsParser extends AbstractAntlrParser {

	@Inject
	private CommandsGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalCommandsParser createParser(XtextTokenStream stream) {
		return new InternalCommandsParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "AlphaCommandsRoot";
	}

	public CommandsGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(CommandsGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
