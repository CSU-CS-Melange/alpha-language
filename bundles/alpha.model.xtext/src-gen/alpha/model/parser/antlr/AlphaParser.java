/*
 * generated by Xtext 2.22.0
 */
package alpha.model.parser.antlr;

import alpha.model.parser.antlr.internal.InternalAlphaParser;
import alpha.model.services.AlphaGrammarAccess;
import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class AlphaParser extends AbstractAntlrParser {

	@Inject
	private AlphaGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalAlphaParser createParser(XtextTokenStream stream) {
		return new InternalAlphaParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "AlphaRoot";
	}

	public AlphaGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(AlphaGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
