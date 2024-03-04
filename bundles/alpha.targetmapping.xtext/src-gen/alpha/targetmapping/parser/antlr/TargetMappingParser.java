/*
 * generated by Xtext 2.27.0
 */
package alpha.targetmapping.parser.antlr;

import alpha.targetmapping.parser.antlr.internal.InternalTargetMappingParser;
import alpha.targetmapping.services.TargetMappingGrammarAccess;
import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class TargetMappingParser extends AbstractAntlrParser {

	@Inject
	private TargetMappingGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalTargetMappingParser createParser(XtextTokenStream stream) {
		return new InternalTargetMappingParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "TargetMapping";
	}

	public TargetMappingGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(TargetMappingGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
