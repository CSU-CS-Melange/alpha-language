package alpha.model.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import alpha.model.services.AlphaGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalAlphaParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_WS", "RULE_BOOLEAN", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_ANY_OTHER", "'constant'", "'='", "'external'", "'('", "')'", "'import'", "'package'", "'{'", "'}'", "'affine'", "'define'", "'inputs'", "','", "'outputs'", "'locals'", "'over'", "'while'", "'.'", "'when'", "'else'", "'let'", "':'", "';'", "'fuzzy'", "'->'", "'['", "']'", "'[['", "']]'", "'.*'", "'{}'", "'+'", "'-'", "'*'", "'/'", "'%'", "'floor'", "'and'", "'or'", "'&'", "'|'", "'>='", "'>'", "'<'", "'<='", "'^'", "'with'", "'if'", "'then'", "'auto'", "'case'", "'@'", "'val'", "'reduce'", "'argreduce'", "'conv'", "'select'", "'from'", "'min'", "'max'", "'prod'", "'sum'", "'--'", "'xor'", "'!='", "'not'", "'as'", "'domain'", "'range'", "'complement'", "'affine-hull'", "'poly-hull'", "'reverse'", "'cross'", "'intersectRange'", "'subtractRange'"
    };
    public static final int T__50=50;
    public static final int RULE_BOOLEAN=7;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__59=59;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__55=55;
    public static final int T__12=12;
    public static final int T__56=56;
    public static final int T__13=13;
    public static final int T__57=57;
    public static final int T__14=14;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=4;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=5;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=9;
    public static final int T__23=23;
    public static final int T__67=67;
    public static final int T__24=24;
    public static final int T__68=68;
    public static final int T__25=25;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__20=20;
    public static final int T__64=64;
    public static final int T__21=21;
    public static final int T__65=65;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=8;
    public static final int RULE_SL_COMMENT=10;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__77=77;
    public static final int T__34=34;
    public static final int T__78=78;
    public static final int T__35=35;
    public static final int T__79=79;
    public static final int T__36=36;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__74=74;
    public static final int T__31=31;
    public static final int T__75=75;
    public static final int T__32=32;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int RULE_WS=6;
    public static final int RULE_ANY_OTHER=11;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__84=84;
    public static final int T__41=41;
    public static final int T__85=85;
    public static final int T__42=42;
    public static final int T__86=86;
    public static final int T__43=43;
    public static final int T__87=87;

    // delegates
    // delegators


        public InternalAlphaParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalAlphaParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalAlphaParser.tokenNames; }
    public String getGrammarFileName() { return "InternalAlpha.g"; }



     	private AlphaGrammarAccess grammarAccess;

        public InternalAlphaParser(TokenStream input, AlphaGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "AlphaRoot";
       	}

       	@Override
       	protected AlphaGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleAlphaRoot"
    // InternalAlpha.g:64:1: entryRuleAlphaRoot returns [EObject current=null] : iv_ruleAlphaRoot= ruleAlphaRoot EOF ;
    public final EObject entryRuleAlphaRoot() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaRoot = null;


        try {
            // InternalAlpha.g:64:50: (iv_ruleAlphaRoot= ruleAlphaRoot EOF )
            // InternalAlpha.g:65:2: iv_ruleAlphaRoot= ruleAlphaRoot EOF
            {
             newCompositeNode(grammarAccess.getAlphaRootRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaRoot=ruleAlphaRoot();

            state._fsp--;

             current =iv_ruleAlphaRoot; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaRoot"


    // $ANTLR start "ruleAlphaRoot"
    // InternalAlpha.g:71:1: ruleAlphaRoot returns [EObject current=null] : ( ( (lv_imports_0_0= ruleImports ) )* ( ( (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem ) ) )* ) ;
    public final EObject ruleAlphaRoot() throws RecognitionException {
        EObject current = null;

        EObject lv_imports_0_0 = null;

        EObject lv_elements_1_1 = null;

        EObject lv_elements_1_2 = null;

        EObject lv_elements_1_3 = null;

        EObject lv_elements_1_4 = null;



        	enterRule();

        try {
            // InternalAlpha.g:77:2: ( ( ( (lv_imports_0_0= ruleImports ) )* ( ( (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem ) ) )* ) )
            // InternalAlpha.g:78:2: ( ( (lv_imports_0_0= ruleImports ) )* ( ( (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem ) ) )* )
            {
            // InternalAlpha.g:78:2: ( ( (lv_imports_0_0= ruleImports ) )* ( ( (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem ) ) )* )
            // InternalAlpha.g:79:3: ( (lv_imports_0_0= ruleImports ) )* ( ( (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem ) ) )*
            {
            // InternalAlpha.g:79:3: ( (lv_imports_0_0= ruleImports ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==17) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalAlpha.g:80:4: (lv_imports_0_0= ruleImports )
            	    {
            	    // InternalAlpha.g:80:4: (lv_imports_0_0= ruleImports )
            	    // InternalAlpha.g:81:5: lv_imports_0_0= ruleImports
            	    {

            	    					newCompositeNode(grammarAccess.getAlphaRootAccess().getImportsImportsParserRuleCall_0_0());
            	    				
            	    pushFollow(FOLLOW_3);
            	    lv_imports_0_0=ruleImports();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getAlphaRootRule());
            	    					}
            	    					add(
            	    						current,
            	    						"imports",
            	    						lv_imports_0_0,
            	    						"alpha.model.Alpha.Imports");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // InternalAlpha.g:98:3: ( ( (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem ) ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==12||LA3_0==14||LA3_0==18||LA3_0==21) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalAlpha.g:99:4: ( (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem ) )
            	    {
            	    // InternalAlpha.g:99:4: ( (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem ) )
            	    // InternalAlpha.g:100:5: (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem )
            	    {
            	    // InternalAlpha.g:100:5: (lv_elements_1_1= ruleAlphaConstant | lv_elements_1_2= ruleExternalFunction | lv_elements_1_3= ruleAlphaPackage | lv_elements_1_4= ruleAlphaSystem )
            	    int alt2=4;
            	    switch ( input.LA(1) ) {
            	    case 12:
            	        {
            	        alt2=1;
            	        }
            	        break;
            	    case 14:
            	        {
            	        alt2=2;
            	        }
            	        break;
            	    case 18:
            	        {
            	        alt2=3;
            	        }
            	        break;
            	    case 21:
            	        {
            	        alt2=4;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 2, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt2) {
            	        case 1 :
            	            // InternalAlpha.g:101:6: lv_elements_1_1= ruleAlphaConstant
            	            {

            	            						newCompositeNode(grammarAccess.getAlphaRootAccess().getElementsAlphaConstantParserRuleCall_1_0_0());
            	            					
            	            pushFollow(FOLLOW_4);
            	            lv_elements_1_1=ruleAlphaConstant();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getAlphaRootRule());
            	            						}
            	            						add(
            	            							current,
            	            							"elements",
            	            							lv_elements_1_1,
            	            							"alpha.model.Alpha.AlphaConstant");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }
            	            break;
            	        case 2 :
            	            // InternalAlpha.g:117:6: lv_elements_1_2= ruleExternalFunction
            	            {

            	            						newCompositeNode(grammarAccess.getAlphaRootAccess().getElementsExternalFunctionParserRuleCall_1_0_1());
            	            					
            	            pushFollow(FOLLOW_4);
            	            lv_elements_1_2=ruleExternalFunction();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getAlphaRootRule());
            	            						}
            	            						add(
            	            							current,
            	            							"elements",
            	            							lv_elements_1_2,
            	            							"alpha.model.Alpha.ExternalFunction");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }
            	            break;
            	        case 3 :
            	            // InternalAlpha.g:133:6: lv_elements_1_3= ruleAlphaPackage
            	            {

            	            						newCompositeNode(grammarAccess.getAlphaRootAccess().getElementsAlphaPackageParserRuleCall_1_0_2());
            	            					
            	            pushFollow(FOLLOW_4);
            	            lv_elements_1_3=ruleAlphaPackage();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getAlphaRootRule());
            	            						}
            	            						add(
            	            							current,
            	            							"elements",
            	            							lv_elements_1_3,
            	            							"alpha.model.Alpha.AlphaPackage");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }
            	            break;
            	        case 4 :
            	            // InternalAlpha.g:149:6: lv_elements_1_4= ruleAlphaSystem
            	            {

            	            						newCompositeNode(grammarAccess.getAlphaRootAccess().getElementsAlphaSystemParserRuleCall_1_0_3());
            	            					
            	            pushFollow(FOLLOW_4);
            	            lv_elements_1_4=ruleAlphaSystem();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getAlphaRootRule());
            	            						}
            	            						add(
            	            							current,
            	            							"elements",
            	            							lv_elements_1_4,
            	            							"alpha.model.Alpha.AlphaSystem");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }
            	            break;

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaRoot"


    // $ANTLR start "entryRuleAlphaConstant"
    // InternalAlpha.g:171:1: entryRuleAlphaConstant returns [EObject current=null] : iv_ruleAlphaConstant= ruleAlphaConstant EOF ;
    public final EObject entryRuleAlphaConstant() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaConstant = null;


        try {
            // InternalAlpha.g:171:54: (iv_ruleAlphaConstant= ruleAlphaConstant EOF )
            // InternalAlpha.g:172:2: iv_ruleAlphaConstant= ruleAlphaConstant EOF
            {
             newCompositeNode(grammarAccess.getAlphaConstantRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaConstant=ruleAlphaConstant();

            state._fsp--;

             current =iv_ruleAlphaConstant; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaConstant"


    // $ANTLR start "ruleAlphaConstant"
    // InternalAlpha.g:178:1: ruleAlphaConstant returns [EObject current=null] : (otherlv_0= 'constant' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' ( (lv_value_3_0= RULE_INT ) ) ) ;
    public final EObject ruleAlphaConstant() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_value_3_0=null;


        	enterRule();

        try {
            // InternalAlpha.g:184:2: ( (otherlv_0= 'constant' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' ( (lv_value_3_0= RULE_INT ) ) ) )
            // InternalAlpha.g:185:2: (otherlv_0= 'constant' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' ( (lv_value_3_0= RULE_INT ) ) )
            {
            // InternalAlpha.g:185:2: (otherlv_0= 'constant' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' ( (lv_value_3_0= RULE_INT ) ) )
            // InternalAlpha.g:186:3: otherlv_0= 'constant' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '=' ( (lv_value_3_0= RULE_INT ) )
            {
            otherlv_0=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getAlphaConstantAccess().getConstantKeyword_0());
            		
            // InternalAlpha.g:190:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalAlpha.g:191:4: (lv_name_1_0= RULE_ID )
            {
            // InternalAlpha.g:191:4: (lv_name_1_0= RULE_ID )
            // InternalAlpha.g:192:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_6); 

            					newLeafNode(lv_name_1_0, grammarAccess.getAlphaConstantAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getAlphaConstantRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"alpha.model.Alpha.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_7); 

            			newLeafNode(otherlv_2, grammarAccess.getAlphaConstantAccess().getEqualsSignKeyword_2());
            		
            // InternalAlpha.g:212:3: ( (lv_value_3_0= RULE_INT ) )
            // InternalAlpha.g:213:4: (lv_value_3_0= RULE_INT )
            {
            // InternalAlpha.g:213:4: (lv_value_3_0= RULE_INT )
            // InternalAlpha.g:214:5: lv_value_3_0= RULE_INT
            {
            lv_value_3_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            					newLeafNode(lv_value_3_0, grammarAccess.getAlphaConstantAccess().getValueINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getAlphaConstantRule());
            					}
            					setWithLastConsumed(
            						current,
            						"value",
            						lv_value_3_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaConstant"


    // $ANTLR start "entryRuleExternalFunction"
    // InternalAlpha.g:234:1: entryRuleExternalFunction returns [EObject current=null] : iv_ruleExternalFunction= ruleExternalFunction EOF ;
    public final EObject entryRuleExternalFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalFunction = null;


        try {
            // InternalAlpha.g:234:57: (iv_ruleExternalFunction= ruleExternalFunction EOF )
            // InternalAlpha.g:235:2: iv_ruleExternalFunction= ruleExternalFunction EOF
            {
             newCompositeNode(grammarAccess.getExternalFunctionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternalFunction=ruleExternalFunction();

            state._fsp--;

             current =iv_ruleExternalFunction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternalFunction"


    // $ANTLR start "ruleExternalFunction"
    // InternalAlpha.g:241:1: ruleExternalFunction returns [EObject current=null] : (otherlv_0= 'external' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_cardinality_3_0= RULE_INT ) ) otherlv_4= ')' ) ;
    public final EObject ruleExternalFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token lv_cardinality_3_0=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalAlpha.g:247:2: ( (otherlv_0= 'external' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_cardinality_3_0= RULE_INT ) ) otherlv_4= ')' ) )
            // InternalAlpha.g:248:2: (otherlv_0= 'external' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_cardinality_3_0= RULE_INT ) ) otherlv_4= ')' )
            {
            // InternalAlpha.g:248:2: (otherlv_0= 'external' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_cardinality_3_0= RULE_INT ) ) otherlv_4= ')' )
            // InternalAlpha.g:249:3: otherlv_0= 'external' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '(' ( (lv_cardinality_3_0= RULE_INT ) ) otherlv_4= ')'
            {
            otherlv_0=(Token)match(input,14,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalFunctionAccess().getExternalKeyword_0());
            		
            // InternalAlpha.g:253:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalAlpha.g:254:4: (lv_name_1_0= RULE_ID )
            {
            // InternalAlpha.g:254:4: (lv_name_1_0= RULE_ID )
            // InternalAlpha.g:255:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_8); 

            					newLeafNode(lv_name_1_0, grammarAccess.getExternalFunctionAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalFunctionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"alpha.model.Alpha.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_2, grammarAccess.getExternalFunctionAccess().getLeftParenthesisKeyword_2());
            		
            // InternalAlpha.g:275:3: ( (lv_cardinality_3_0= RULE_INT ) )
            // InternalAlpha.g:276:4: (lv_cardinality_3_0= RULE_INT )
            {
            // InternalAlpha.g:276:4: (lv_cardinality_3_0= RULE_INT )
            // InternalAlpha.g:277:5: lv_cardinality_3_0= RULE_INT
            {
            lv_cardinality_3_0=(Token)match(input,RULE_INT,FOLLOW_9); 

            					newLeafNode(lv_cardinality_3_0, grammarAccess.getExternalFunctionAccess().getCardinalityINTTerminalRuleCall_3_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalFunctionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"cardinality",
            						lv_cardinality_3_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_4=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getExternalFunctionAccess().getRightParenthesisKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternalFunction"


    // $ANTLR start "entryRuleImports"
    // InternalAlpha.g:301:1: entryRuleImports returns [EObject current=null] : iv_ruleImports= ruleImports EOF ;
    public final EObject entryRuleImports() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImports = null;


        try {
            // InternalAlpha.g:301:48: (iv_ruleImports= ruleImports EOF )
            // InternalAlpha.g:302:2: iv_ruleImports= ruleImports EOF
            {
             newCompositeNode(grammarAccess.getImportsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleImports=ruleImports();

            state._fsp--;

             current =iv_ruleImports; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleImports"


    // $ANTLR start "ruleImports"
    // InternalAlpha.g:308:1: ruleImports returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) ) ;
    public final EObject ruleImports() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_importedNamespace_1_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:314:2: ( (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) ) )
            // InternalAlpha.g:315:2: (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) )
            {
            // InternalAlpha.g:315:2: (otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) ) )
            // InternalAlpha.g:316:3: otherlv_0= 'import' ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) )
            {
            otherlv_0=(Token)match(input,17,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getImportsAccess().getImportKeyword_0());
            		
            // InternalAlpha.g:320:3: ( (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard ) )
            // InternalAlpha.g:321:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            {
            // InternalAlpha.g:321:4: (lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard )
            // InternalAlpha.g:322:5: lv_importedNamespace_1_0= ruleQualifiedNameWithWildcard
            {

            					newCompositeNode(grammarAccess.getImportsAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_importedNamespace_1_0=ruleQualifiedNameWithWildcard();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getImportsRule());
            					}
            					set(
            						current,
            						"importedNamespace",
            						lv_importedNamespace_1_0,
            						"alpha.model.Alpha.QualifiedNameWithWildcard");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleImports"


    // $ANTLR start "entryRuleAlphaPackage"
    // InternalAlpha.g:343:1: entryRuleAlphaPackage returns [EObject current=null] : iv_ruleAlphaPackage= ruleAlphaPackage EOF ;
    public final EObject entryRuleAlphaPackage() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaPackage = null;


        try {
            // InternalAlpha.g:343:53: (iv_ruleAlphaPackage= ruleAlphaPackage EOF )
            // InternalAlpha.g:344:2: iv_ruleAlphaPackage= ruleAlphaPackage EOF
            {
             newCompositeNode(grammarAccess.getAlphaPackageRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaPackage=ruleAlphaPackage();

            state._fsp--;

             current =iv_ruleAlphaPackage; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaPackage"


    // $ANTLR start "ruleAlphaPackage"
    // InternalAlpha.g:350:1: ruleAlphaPackage returns [EObject current=null] : (otherlv_0= 'package' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem ) ) )* otherlv_4= '}' ) ;
    public final EObject ruleAlphaPackage() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_elements_3_1 = null;

        EObject lv_elements_3_2 = null;

        EObject lv_elements_3_3 = null;

        EObject lv_elements_3_4 = null;



        	enterRule();

        try {
            // InternalAlpha.g:356:2: ( (otherlv_0= 'package' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem ) ) )* otherlv_4= '}' ) )
            // InternalAlpha.g:357:2: (otherlv_0= 'package' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem ) ) )* otherlv_4= '}' )
            {
            // InternalAlpha.g:357:2: (otherlv_0= 'package' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem ) ) )* otherlv_4= '}' )
            // InternalAlpha.g:358:3: otherlv_0= 'package' ( (lv_name_1_0= ruleQualifiedName ) ) otherlv_2= '{' ( ( (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem ) ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,18,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getAlphaPackageAccess().getPackageKeyword_0());
            		
            // InternalAlpha.g:362:3: ( (lv_name_1_0= ruleQualifiedName ) )
            // InternalAlpha.g:363:4: (lv_name_1_0= ruleQualifiedName )
            {
            // InternalAlpha.g:363:4: (lv_name_1_0= ruleQualifiedName )
            // InternalAlpha.g:364:5: lv_name_1_0= ruleQualifiedName
            {

            					newCompositeNode(grammarAccess.getAlphaPackageAccess().getNameQualifiedNameParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_10);
            lv_name_1_0=ruleQualifiedName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAlphaPackageRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"alpha.model.Alpha.QualifiedName");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,19,FOLLOW_11); 

            			newLeafNode(otherlv_2, grammarAccess.getAlphaPackageAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalAlpha.g:385:3: ( ( (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem ) ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==12||LA5_0==14||LA5_0==18||LA5_0==21) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalAlpha.g:386:4: ( (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem ) )
            	    {
            	    // InternalAlpha.g:386:4: ( (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem ) )
            	    // InternalAlpha.g:387:5: (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem )
            	    {
            	    // InternalAlpha.g:387:5: (lv_elements_3_1= ruleAlphaConstant | lv_elements_3_2= ruleExternalFunction | lv_elements_3_3= ruleAlphaPackage | lv_elements_3_4= ruleAlphaSystem )
            	    int alt4=4;
            	    switch ( input.LA(1) ) {
            	    case 12:
            	        {
            	        alt4=1;
            	        }
            	        break;
            	    case 14:
            	        {
            	        alt4=2;
            	        }
            	        break;
            	    case 18:
            	        {
            	        alt4=3;
            	        }
            	        break;
            	    case 21:
            	        {
            	        alt4=4;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 4, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt4) {
            	        case 1 :
            	            // InternalAlpha.g:388:6: lv_elements_3_1= ruleAlphaConstant
            	            {

            	            						newCompositeNode(grammarAccess.getAlphaPackageAccess().getElementsAlphaConstantParserRuleCall_3_0_0());
            	            					
            	            pushFollow(FOLLOW_11);
            	            lv_elements_3_1=ruleAlphaConstant();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getAlphaPackageRule());
            	            						}
            	            						add(
            	            							current,
            	            							"elements",
            	            							lv_elements_3_1,
            	            							"alpha.model.Alpha.AlphaConstant");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }
            	            break;
            	        case 2 :
            	            // InternalAlpha.g:404:6: lv_elements_3_2= ruleExternalFunction
            	            {

            	            						newCompositeNode(grammarAccess.getAlphaPackageAccess().getElementsExternalFunctionParserRuleCall_3_0_1());
            	            					
            	            pushFollow(FOLLOW_11);
            	            lv_elements_3_2=ruleExternalFunction();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getAlphaPackageRule());
            	            						}
            	            						add(
            	            							current,
            	            							"elements",
            	            							lv_elements_3_2,
            	            							"alpha.model.Alpha.ExternalFunction");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }
            	            break;
            	        case 3 :
            	            // InternalAlpha.g:420:6: lv_elements_3_3= ruleAlphaPackage
            	            {

            	            						newCompositeNode(grammarAccess.getAlphaPackageAccess().getElementsAlphaPackageParserRuleCall_3_0_2());
            	            					
            	            pushFollow(FOLLOW_11);
            	            lv_elements_3_3=ruleAlphaPackage();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getAlphaPackageRule());
            	            						}
            	            						add(
            	            							current,
            	            							"elements",
            	            							lv_elements_3_3,
            	            							"alpha.model.Alpha.AlphaPackage");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }
            	            break;
            	        case 4 :
            	            // InternalAlpha.g:436:6: lv_elements_3_4= ruleAlphaSystem
            	            {

            	            						newCompositeNode(grammarAccess.getAlphaPackageAccess().getElementsAlphaSystemParserRuleCall_3_0_3());
            	            					
            	            pushFollow(FOLLOW_11);
            	            lv_elements_3_4=ruleAlphaSystem();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getAlphaPackageRule());
            	            						}
            	            						add(
            	            							current,
            	            							"elements",
            	            							lv_elements_3_4,
            	            							"alpha.model.Alpha.AlphaSystem");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }
            	            break;

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            otherlv_4=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getAlphaPackageAccess().getRightCurlyBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaPackage"


    // $ANTLR start "entryRuleAlphaSystem"
    // InternalAlpha.g:462:1: entryRuleAlphaSystem returns [EObject current=null] : iv_ruleAlphaSystem= ruleAlphaSystem EOF ;
    public final EObject entryRuleAlphaSystem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaSystem = null;


        try {
            // InternalAlpha.g:462:52: (iv_ruleAlphaSystem= ruleAlphaSystem EOF )
            // InternalAlpha.g:463:2: iv_ruleAlphaSystem= ruleAlphaSystem EOF
            {
             newCompositeNode(grammarAccess.getAlphaSystemRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaSystem=ruleAlphaSystem();

            state._fsp--;

             current =iv_ruleAlphaSystem; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaSystem"


    // $ANTLR start "ruleAlphaSystem"
    // InternalAlpha.g:469:1: ruleAlphaSystem returns [EObject current=null] : (otherlv_0= 'affine' ( (lv_name_1_0= ruleSystemName ) ) ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomain ) ) (otherlv_3= 'define' ( (lv_definedObjects_4_0= rulePolyhedralObject ) )+ )? (otherlv_5= 'inputs' ( ( (lv_inputs_6_0= ruleVariable ) ) | ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) ) | ( (lv_inputs_12_0= ruleFuzzyVariable ) ) | ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_18= 'outputs' ( ( (lv_outputs_19_0= ruleVariable ) ) | ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) ) | ( (lv_outputs_25_0= ruleFuzzyVariable ) ) | ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_31= 'locals' ( ( (lv_locals_32_0= ruleVariable ) ) | ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) ) | ( (lv_locals_38_0= ruleFuzzyVariable ) ) | ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_44= 'over' ( (lv_whileDomainExpr_45_0= ruleCalculatorExpression ) ) otherlv_46= 'while' otherlv_47= '(' ( (lv_testExpression_48_0= ruleAlphaExpression ) ) otherlv_49= ')' )? ( (lv_systemBodies_50_0= ruleSystemBody ) )* otherlv_51= '.' ) ;
    public final EObject ruleAlphaSystem() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token otherlv_27=null;
        Token otherlv_29=null;
        Token otherlv_31=null;
        Token otherlv_34=null;
        Token otherlv_36=null;
        Token otherlv_40=null;
        Token otherlv_42=null;
        Token otherlv_44=null;
        Token otherlv_46=null;
        Token otherlv_47=null;
        Token otherlv_49=null;
        Token otherlv_51=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_parameterDomainExpr_2_0 = null;

        EObject lv_definedObjects_4_0 = null;

        EObject lv_inputs_6_0 = null;

        EObject lv_inputs_7_0 = null;

        EObject lv_inputs_9_0 = null;

        EObject lv_inputs_11_0 = null;

        EObject lv_inputs_12_0 = null;

        EObject lv_inputs_13_0 = null;

        EObject lv_inputs_15_0 = null;

        EObject lv_inputs_17_0 = null;

        EObject lv_outputs_19_0 = null;

        EObject lv_outputs_20_0 = null;

        EObject lv_outputs_22_0 = null;

        EObject lv_outputs_24_0 = null;

        EObject lv_outputs_25_0 = null;

        EObject lv_outputs_26_0 = null;

        EObject lv_outputs_28_0 = null;

        EObject lv_outputs_30_0 = null;

        EObject lv_locals_32_0 = null;

        EObject lv_locals_33_0 = null;

        EObject lv_locals_35_0 = null;

        EObject lv_locals_37_0 = null;

        EObject lv_locals_38_0 = null;

        EObject lv_locals_39_0 = null;

        EObject lv_locals_41_0 = null;

        EObject lv_locals_43_0 = null;

        EObject lv_whileDomainExpr_45_0 = null;

        EObject lv_testExpression_48_0 = null;

        EObject lv_systemBodies_50_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:475:2: ( (otherlv_0= 'affine' ( (lv_name_1_0= ruleSystemName ) ) ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomain ) ) (otherlv_3= 'define' ( (lv_definedObjects_4_0= rulePolyhedralObject ) )+ )? (otherlv_5= 'inputs' ( ( (lv_inputs_6_0= ruleVariable ) ) | ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) ) | ( (lv_inputs_12_0= ruleFuzzyVariable ) ) | ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_18= 'outputs' ( ( (lv_outputs_19_0= ruleVariable ) ) | ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) ) | ( (lv_outputs_25_0= ruleFuzzyVariable ) ) | ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_31= 'locals' ( ( (lv_locals_32_0= ruleVariable ) ) | ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) ) | ( (lv_locals_38_0= ruleFuzzyVariable ) ) | ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_44= 'over' ( (lv_whileDomainExpr_45_0= ruleCalculatorExpression ) ) otherlv_46= 'while' otherlv_47= '(' ( (lv_testExpression_48_0= ruleAlphaExpression ) ) otherlv_49= ')' )? ( (lv_systemBodies_50_0= ruleSystemBody ) )* otherlv_51= '.' ) )
            // InternalAlpha.g:476:2: (otherlv_0= 'affine' ( (lv_name_1_0= ruleSystemName ) ) ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomain ) ) (otherlv_3= 'define' ( (lv_definedObjects_4_0= rulePolyhedralObject ) )+ )? (otherlv_5= 'inputs' ( ( (lv_inputs_6_0= ruleVariable ) ) | ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) ) | ( (lv_inputs_12_0= ruleFuzzyVariable ) ) | ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_18= 'outputs' ( ( (lv_outputs_19_0= ruleVariable ) ) | ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) ) | ( (lv_outputs_25_0= ruleFuzzyVariable ) ) | ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_31= 'locals' ( ( (lv_locals_32_0= ruleVariable ) ) | ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) ) | ( (lv_locals_38_0= ruleFuzzyVariable ) ) | ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_44= 'over' ( (lv_whileDomainExpr_45_0= ruleCalculatorExpression ) ) otherlv_46= 'while' otherlv_47= '(' ( (lv_testExpression_48_0= ruleAlphaExpression ) ) otherlv_49= ')' )? ( (lv_systemBodies_50_0= ruleSystemBody ) )* otherlv_51= '.' )
            {
            // InternalAlpha.g:476:2: (otherlv_0= 'affine' ( (lv_name_1_0= ruleSystemName ) ) ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomain ) ) (otherlv_3= 'define' ( (lv_definedObjects_4_0= rulePolyhedralObject ) )+ )? (otherlv_5= 'inputs' ( ( (lv_inputs_6_0= ruleVariable ) ) | ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) ) | ( (lv_inputs_12_0= ruleFuzzyVariable ) ) | ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_18= 'outputs' ( ( (lv_outputs_19_0= ruleVariable ) ) | ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) ) | ( (lv_outputs_25_0= ruleFuzzyVariable ) ) | ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_31= 'locals' ( ( (lv_locals_32_0= ruleVariable ) ) | ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) ) | ( (lv_locals_38_0= ruleFuzzyVariable ) ) | ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_44= 'over' ( (lv_whileDomainExpr_45_0= ruleCalculatorExpression ) ) otherlv_46= 'while' otherlv_47= '(' ( (lv_testExpression_48_0= ruleAlphaExpression ) ) otherlv_49= ')' )? ( (lv_systemBodies_50_0= ruleSystemBody ) )* otherlv_51= '.' )
            // InternalAlpha.g:477:3: otherlv_0= 'affine' ( (lv_name_1_0= ruleSystemName ) ) ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomain ) ) (otherlv_3= 'define' ( (lv_definedObjects_4_0= rulePolyhedralObject ) )+ )? (otherlv_5= 'inputs' ( ( (lv_inputs_6_0= ruleVariable ) ) | ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) ) | ( (lv_inputs_12_0= ruleFuzzyVariable ) ) | ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_18= 'outputs' ( ( (lv_outputs_19_0= ruleVariable ) ) | ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) ) | ( (lv_outputs_25_0= ruleFuzzyVariable ) ) | ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_31= 'locals' ( ( (lv_locals_32_0= ruleVariable ) ) | ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) ) | ( (lv_locals_38_0= ruleFuzzyVariable ) ) | ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) ) )+ )? (otherlv_44= 'over' ( (lv_whileDomainExpr_45_0= ruleCalculatorExpression ) ) otherlv_46= 'while' otherlv_47= '(' ( (lv_testExpression_48_0= ruleAlphaExpression ) ) otherlv_49= ')' )? ( (lv_systemBodies_50_0= ruleSystemBody ) )* otherlv_51= '.'
            {
            otherlv_0=(Token)match(input,21,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getAlphaSystemAccess().getAffineKeyword_0());
            		
            // InternalAlpha.g:481:3: ( (lv_name_1_0= ruleSystemName ) )
            // InternalAlpha.g:482:4: (lv_name_1_0= ruleSystemName )
            {
            // InternalAlpha.g:482:4: (lv_name_1_0= ruleSystemName )
            // InternalAlpha.g:483:5: lv_name_1_0= ruleSystemName
            {

            					newCompositeNode(grammarAccess.getAlphaSystemAccess().getNameSystemNameParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_12);
            lv_name_1_0=ruleSystemName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"alpha.model.Alpha.SystemName");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:500:3: ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomain ) )
            // InternalAlpha.g:501:4: (lv_parameterDomainExpr_2_0= ruleJNIParamDomain )
            {
            // InternalAlpha.g:501:4: (lv_parameterDomainExpr_2_0= ruleJNIParamDomain )
            // InternalAlpha.g:502:5: lv_parameterDomainExpr_2_0= ruleJNIParamDomain
            {

            					newCompositeNode(grammarAccess.getAlphaSystemAccess().getParameterDomainExprJNIParamDomainParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_13);
            lv_parameterDomainExpr_2_0=ruleJNIParamDomain();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
            					}
            					set(
            						current,
            						"parameterDomainExpr",
            						lv_parameterDomainExpr_2_0,
            						"alpha.model.Alpha.JNIParamDomain");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:519:3: (otherlv_3= 'define' ( (lv_definedObjects_4_0= rulePolyhedralObject ) )+ )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==22) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalAlpha.g:520:4: otherlv_3= 'define' ( (lv_definedObjects_4_0= rulePolyhedralObject ) )+
                    {
                    otherlv_3=(Token)match(input,22,FOLLOW_5); 

                    				newLeafNode(otherlv_3, grammarAccess.getAlphaSystemAccess().getDefineKeyword_3_0());
                    			
                    // InternalAlpha.g:524:4: ( (lv_definedObjects_4_0= rulePolyhedralObject ) )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==RULE_ID) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // InternalAlpha.g:525:5: (lv_definedObjects_4_0= rulePolyhedralObject )
                    	    {
                    	    // InternalAlpha.g:525:5: (lv_definedObjects_4_0= rulePolyhedralObject )
                    	    // InternalAlpha.g:526:6: lv_definedObjects_4_0= rulePolyhedralObject
                    	    {

                    	    						newCompositeNode(grammarAccess.getAlphaSystemAccess().getDefinedObjectsPolyhedralObjectParserRuleCall_3_1_0());
                    	    					
                    	    pushFollow(FOLLOW_14);
                    	    lv_definedObjects_4_0=rulePolyhedralObject();

                    	    state._fsp--;


                    	    						if (current==null) {
                    	    							current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    						}
                    	    						add(
                    	    							current,
                    	    							"definedObjects",
                    	    							lv_definedObjects_4_0,
                    	    							"alpha.model.Alpha.PolyhedralObject");
                    	    						afterParserOrEnumRuleCall();
                    	    					

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);


                    }
                    break;

            }

            // InternalAlpha.g:544:3: (otherlv_5= 'inputs' ( ( (lv_inputs_6_0= ruleVariable ) ) | ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) ) | ( (lv_inputs_12_0= ruleFuzzyVariable ) ) | ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) ) )+ )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==23) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalAlpha.g:545:4: otherlv_5= 'inputs' ( ( (lv_inputs_6_0= ruleVariable ) ) | ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) ) | ( (lv_inputs_12_0= ruleFuzzyVariable ) ) | ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) ) )+
                    {
                    otherlv_5=(Token)match(input,23,FOLLOW_15); 

                    				newLeafNode(otherlv_5, grammarAccess.getAlphaSystemAccess().getInputsKeyword_4_0());
                    			
                    // InternalAlpha.g:549:4: ( ( (lv_inputs_6_0= ruleVariable ) ) | ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) ) | ( (lv_inputs_12_0= ruleFuzzyVariable ) ) | ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) ) )+
                    int cnt10=0;
                    loop10:
                    do {
                        int alt10=5;
                        alt10 = dfa10.predict(input);
                        switch (alt10) {
                    	case 1 :
                    	    // InternalAlpha.g:550:5: ( (lv_inputs_6_0= ruleVariable ) )
                    	    {
                    	    // InternalAlpha.g:550:5: ( (lv_inputs_6_0= ruleVariable ) )
                    	    // InternalAlpha.g:551:6: (lv_inputs_6_0= ruleVariable )
                    	    {
                    	    // InternalAlpha.g:551:6: (lv_inputs_6_0= ruleVariable )
                    	    // InternalAlpha.g:552:7: lv_inputs_6_0= ruleVariable
                    	    {

                    	    							newCompositeNode(grammarAccess.getAlphaSystemAccess().getInputsVariableParserRuleCall_4_1_0_0());
                    	    						
                    	    pushFollow(FOLLOW_16);
                    	    lv_inputs_6_0=ruleVariable();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"inputs",
                    	    								lv_inputs_6_0,
                    	    								"alpha.model.Alpha.Variable");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalAlpha.g:570:5: ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) )
                    	    {
                    	    // InternalAlpha.g:570:5: ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) )
                    	    // InternalAlpha.g:571:6: ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) )
                    	    {
                    	    // InternalAlpha.g:571:6: ( (lv_inputs_7_0= ruleVariableNameOnly ) )
                    	    // InternalAlpha.g:572:7: (lv_inputs_7_0= ruleVariableNameOnly )
                    	    {
                    	    // InternalAlpha.g:572:7: (lv_inputs_7_0= ruleVariableNameOnly )
                    	    // InternalAlpha.g:573:8: lv_inputs_7_0= ruleVariableNameOnly
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getInputsVariableNameOnlyParserRuleCall_4_1_1_0_0());
                    	    							
                    	    pushFollow(FOLLOW_17);
                    	    lv_inputs_7_0=ruleVariableNameOnly();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"inputs",
                    	    									lv_inputs_7_0,
                    	    									"alpha.model.Alpha.VariableNameOnly");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }

                    	    otherlv_8=(Token)match(input,24,FOLLOW_5); 

                    	    						newLeafNode(otherlv_8, grammarAccess.getAlphaSystemAccess().getCommaKeyword_4_1_1_1());
                    	    					
                    	    // InternalAlpha.g:594:6: ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )*
                    	    loop8:
                    	    do {
                    	        int alt8=2;
                    	        int LA8_0 = input.LA(1);

                    	        if ( (LA8_0==RULE_ID) ) {
                    	            int LA8_1 = input.LA(2);

                    	            if ( (LA8_1==24) ) {
                    	                alt8=1;
                    	            }


                    	        }


                    	        switch (alt8) {
                    	    	case 1 :
                    	    	    // InternalAlpha.g:595:7: ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ','
                    	    	    {
                    	    	    // InternalAlpha.g:595:7: ( (lv_inputs_9_0= ruleVariableNameOnly ) )
                    	    	    // InternalAlpha.g:596:8: (lv_inputs_9_0= ruleVariableNameOnly )
                    	    	    {
                    	    	    // InternalAlpha.g:596:8: (lv_inputs_9_0= ruleVariableNameOnly )
                    	    	    // InternalAlpha.g:597:9: lv_inputs_9_0= ruleVariableNameOnly
                    	    	    {

                    	    	    									newCompositeNode(grammarAccess.getAlphaSystemAccess().getInputsVariableNameOnlyParserRuleCall_4_1_1_2_0_0());
                    	    	    								
                    	    	    pushFollow(FOLLOW_17);
                    	    	    lv_inputs_9_0=ruleVariableNameOnly();

                    	    	    state._fsp--;


                    	    	    									if (current==null) {
                    	    	    										current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    	    									}
                    	    	    									add(
                    	    	    										current,
                    	    	    										"inputs",
                    	    	    										lv_inputs_9_0,
                    	    	    										"alpha.model.Alpha.VariableNameOnly");
                    	    	    									afterParserOrEnumRuleCall();
                    	    	    								

                    	    	    }


                    	    	    }

                    	    	    otherlv_10=(Token)match(input,24,FOLLOW_5); 

                    	    	    							newLeafNode(otherlv_10, grammarAccess.getAlphaSystemAccess().getCommaKeyword_4_1_1_2_1());
                    	    	    						

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop8;
                    	        }
                    	    } while (true);

                    	    // InternalAlpha.g:619:6: ( (lv_inputs_11_0= ruleVariable ) )
                    	    // InternalAlpha.g:620:7: (lv_inputs_11_0= ruleVariable )
                    	    {
                    	    // InternalAlpha.g:620:7: (lv_inputs_11_0= ruleVariable )
                    	    // InternalAlpha.g:621:8: lv_inputs_11_0= ruleVariable
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getInputsVariableParserRuleCall_4_1_1_3_0());
                    	    							
                    	    pushFollow(FOLLOW_16);
                    	    lv_inputs_11_0=ruleVariable();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"inputs",
                    	    									lv_inputs_11_0,
                    	    									"alpha.model.Alpha.Variable");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // InternalAlpha.g:640:5: ( (lv_inputs_12_0= ruleFuzzyVariable ) )
                    	    {
                    	    // InternalAlpha.g:640:5: ( (lv_inputs_12_0= ruleFuzzyVariable ) )
                    	    // InternalAlpha.g:641:6: (lv_inputs_12_0= ruleFuzzyVariable )
                    	    {
                    	    // InternalAlpha.g:641:6: (lv_inputs_12_0= ruleFuzzyVariable )
                    	    // InternalAlpha.g:642:7: lv_inputs_12_0= ruleFuzzyVariable
                    	    {

                    	    							newCompositeNode(grammarAccess.getAlphaSystemAccess().getInputsFuzzyVariableParserRuleCall_4_1_2_0());
                    	    						
                    	    pushFollow(FOLLOW_16);
                    	    lv_inputs_12_0=ruleFuzzyVariable();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"inputs",
                    	    								lv_inputs_12_0,
                    	    								"alpha.model.Alpha.FuzzyVariable");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // InternalAlpha.g:660:5: ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) )
                    	    {
                    	    // InternalAlpha.g:660:5: ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) )
                    	    // InternalAlpha.g:661:6: ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) )
                    	    {
                    	    // InternalAlpha.g:661:6: ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) )
                    	    // InternalAlpha.g:662:7: (lv_inputs_13_0= ruleFuzzyVariableNameOnly )
                    	    {
                    	    // InternalAlpha.g:662:7: (lv_inputs_13_0= ruleFuzzyVariableNameOnly )
                    	    // InternalAlpha.g:663:8: lv_inputs_13_0= ruleFuzzyVariableNameOnly
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getInputsFuzzyVariableNameOnlyParserRuleCall_4_1_3_0_0());
                    	    							
                    	    pushFollow(FOLLOW_17);
                    	    lv_inputs_13_0=ruleFuzzyVariableNameOnly();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"inputs",
                    	    									lv_inputs_13_0,
                    	    									"alpha.model.Alpha.FuzzyVariableNameOnly");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }

                    	    otherlv_14=(Token)match(input,24,FOLLOW_15); 

                    	    						newLeafNode(otherlv_14, grammarAccess.getAlphaSystemAccess().getCommaKeyword_4_1_3_1());
                    	    					
                    	    // InternalAlpha.g:684:6: ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )*
                    	    loop9:
                    	    do {
                    	        int alt9=2;
                    	        int LA9_0 = input.LA(1);

                    	        if ( (LA9_0==RULE_ID) ) {
                    	            alt9=1;
                    	        }


                    	        switch (alt9) {
                    	    	case 1 :
                    	    	    // InternalAlpha.g:685:7: ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ','
                    	    	    {
                    	    	    // InternalAlpha.g:685:7: ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) )
                    	    	    // InternalAlpha.g:686:8: (lv_inputs_15_0= ruleFuzzyVariableNameOnly )
                    	    	    {
                    	    	    // InternalAlpha.g:686:8: (lv_inputs_15_0= ruleFuzzyVariableNameOnly )
                    	    	    // InternalAlpha.g:687:9: lv_inputs_15_0= ruleFuzzyVariableNameOnly
                    	    	    {

                    	    	    									newCompositeNode(grammarAccess.getAlphaSystemAccess().getInputsFuzzyVariableNameOnlyParserRuleCall_4_1_3_2_0_0());
                    	    	    								
                    	    	    pushFollow(FOLLOW_17);
                    	    	    lv_inputs_15_0=ruleFuzzyVariableNameOnly();

                    	    	    state._fsp--;


                    	    	    									if (current==null) {
                    	    	    										current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    	    									}
                    	    	    									add(
                    	    	    										current,
                    	    	    										"inputs",
                    	    	    										lv_inputs_15_0,
                    	    	    										"alpha.model.Alpha.FuzzyVariableNameOnly");
                    	    	    									afterParserOrEnumRuleCall();
                    	    	    								

                    	    	    }


                    	    	    }

                    	    	    otherlv_16=(Token)match(input,24,FOLLOW_15); 

                    	    	    							newLeafNode(otherlv_16, grammarAccess.getAlphaSystemAccess().getCommaKeyword_4_1_3_2_1());
                    	    	    						

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop9;
                    	        }
                    	    } while (true);

                    	    // InternalAlpha.g:709:6: ( (lv_inputs_17_0= ruleFuzzyVariable ) )
                    	    // InternalAlpha.g:710:7: (lv_inputs_17_0= ruleFuzzyVariable )
                    	    {
                    	    // InternalAlpha.g:710:7: (lv_inputs_17_0= ruleFuzzyVariable )
                    	    // InternalAlpha.g:711:8: lv_inputs_17_0= ruleFuzzyVariable
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getInputsFuzzyVariableParserRuleCall_4_1_3_3_0());
                    	    							
                    	    pushFollow(FOLLOW_16);
                    	    lv_inputs_17_0=ruleFuzzyVariable();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"inputs",
                    	    									lv_inputs_17_0,
                    	    									"alpha.model.Alpha.FuzzyVariable");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt10 >= 1 ) break loop10;
                                EarlyExitException eee =
                                    new EarlyExitException(10, input);
                                throw eee;
                        }
                        cnt10++;
                    } while (true);


                    }
                    break;

            }

            // InternalAlpha.g:731:3: (otherlv_18= 'outputs' ( ( (lv_outputs_19_0= ruleVariable ) ) | ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) ) | ( (lv_outputs_25_0= ruleFuzzyVariable ) ) | ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) ) )+ )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==25) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalAlpha.g:732:4: otherlv_18= 'outputs' ( ( (lv_outputs_19_0= ruleVariable ) ) | ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) ) | ( (lv_outputs_25_0= ruleFuzzyVariable ) ) | ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) ) )+
                    {
                    otherlv_18=(Token)match(input,25,FOLLOW_15); 

                    				newLeafNode(otherlv_18, grammarAccess.getAlphaSystemAccess().getOutputsKeyword_5_0());
                    			
                    // InternalAlpha.g:736:4: ( ( (lv_outputs_19_0= ruleVariable ) ) | ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) ) | ( (lv_outputs_25_0= ruleFuzzyVariable ) ) | ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) ) )+
                    int cnt14=0;
                    loop14:
                    do {
                        int alt14=5;
                        alt14 = dfa14.predict(input);
                        switch (alt14) {
                    	case 1 :
                    	    // InternalAlpha.g:737:5: ( (lv_outputs_19_0= ruleVariable ) )
                    	    {
                    	    // InternalAlpha.g:737:5: ( (lv_outputs_19_0= ruleVariable ) )
                    	    // InternalAlpha.g:738:6: (lv_outputs_19_0= ruleVariable )
                    	    {
                    	    // InternalAlpha.g:738:6: (lv_outputs_19_0= ruleVariable )
                    	    // InternalAlpha.g:739:7: lv_outputs_19_0= ruleVariable
                    	    {

                    	    							newCompositeNode(grammarAccess.getAlphaSystemAccess().getOutputsVariableParserRuleCall_5_1_0_0());
                    	    						
                    	    pushFollow(FOLLOW_18);
                    	    lv_outputs_19_0=ruleVariable();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"outputs",
                    	    								lv_outputs_19_0,
                    	    								"alpha.model.Alpha.Variable");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalAlpha.g:757:5: ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) )
                    	    {
                    	    // InternalAlpha.g:757:5: ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) )
                    	    // InternalAlpha.g:758:6: ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) )
                    	    {
                    	    // InternalAlpha.g:758:6: ( (lv_outputs_20_0= ruleVariableNameOnly ) )
                    	    // InternalAlpha.g:759:7: (lv_outputs_20_0= ruleVariableNameOnly )
                    	    {
                    	    // InternalAlpha.g:759:7: (lv_outputs_20_0= ruleVariableNameOnly )
                    	    // InternalAlpha.g:760:8: lv_outputs_20_0= ruleVariableNameOnly
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getOutputsVariableNameOnlyParserRuleCall_5_1_1_0_0());
                    	    							
                    	    pushFollow(FOLLOW_17);
                    	    lv_outputs_20_0=ruleVariableNameOnly();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"outputs",
                    	    									lv_outputs_20_0,
                    	    									"alpha.model.Alpha.VariableNameOnly");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }

                    	    otherlv_21=(Token)match(input,24,FOLLOW_5); 

                    	    						newLeafNode(otherlv_21, grammarAccess.getAlphaSystemAccess().getCommaKeyword_5_1_1_1());
                    	    					
                    	    // InternalAlpha.g:781:6: ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )*
                    	    loop12:
                    	    do {
                    	        int alt12=2;
                    	        int LA12_0 = input.LA(1);

                    	        if ( (LA12_0==RULE_ID) ) {
                    	            int LA12_1 = input.LA(2);

                    	            if ( (LA12_1==24) ) {
                    	                alt12=1;
                    	            }


                    	        }


                    	        switch (alt12) {
                    	    	case 1 :
                    	    	    // InternalAlpha.g:782:7: ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ','
                    	    	    {
                    	    	    // InternalAlpha.g:782:7: ( (lv_outputs_22_0= ruleVariableNameOnly ) )
                    	    	    // InternalAlpha.g:783:8: (lv_outputs_22_0= ruleVariableNameOnly )
                    	    	    {
                    	    	    // InternalAlpha.g:783:8: (lv_outputs_22_0= ruleVariableNameOnly )
                    	    	    // InternalAlpha.g:784:9: lv_outputs_22_0= ruleVariableNameOnly
                    	    	    {

                    	    	    									newCompositeNode(grammarAccess.getAlphaSystemAccess().getOutputsVariableNameOnlyParserRuleCall_5_1_1_2_0_0());
                    	    	    								
                    	    	    pushFollow(FOLLOW_17);
                    	    	    lv_outputs_22_0=ruleVariableNameOnly();

                    	    	    state._fsp--;


                    	    	    									if (current==null) {
                    	    	    										current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    	    									}
                    	    	    									add(
                    	    	    										current,
                    	    	    										"outputs",
                    	    	    										lv_outputs_22_0,
                    	    	    										"alpha.model.Alpha.VariableNameOnly");
                    	    	    									afterParserOrEnumRuleCall();
                    	    	    								

                    	    	    }


                    	    	    }

                    	    	    otherlv_23=(Token)match(input,24,FOLLOW_5); 

                    	    	    							newLeafNode(otherlv_23, grammarAccess.getAlphaSystemAccess().getCommaKeyword_5_1_1_2_1());
                    	    	    						

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop12;
                    	        }
                    	    } while (true);

                    	    // InternalAlpha.g:806:6: ( (lv_outputs_24_0= ruleVariable ) )
                    	    // InternalAlpha.g:807:7: (lv_outputs_24_0= ruleVariable )
                    	    {
                    	    // InternalAlpha.g:807:7: (lv_outputs_24_0= ruleVariable )
                    	    // InternalAlpha.g:808:8: lv_outputs_24_0= ruleVariable
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getOutputsVariableParserRuleCall_5_1_1_3_0());
                    	    							
                    	    pushFollow(FOLLOW_18);
                    	    lv_outputs_24_0=ruleVariable();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"outputs",
                    	    									lv_outputs_24_0,
                    	    									"alpha.model.Alpha.Variable");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // InternalAlpha.g:827:5: ( (lv_outputs_25_0= ruleFuzzyVariable ) )
                    	    {
                    	    // InternalAlpha.g:827:5: ( (lv_outputs_25_0= ruleFuzzyVariable ) )
                    	    // InternalAlpha.g:828:6: (lv_outputs_25_0= ruleFuzzyVariable )
                    	    {
                    	    // InternalAlpha.g:828:6: (lv_outputs_25_0= ruleFuzzyVariable )
                    	    // InternalAlpha.g:829:7: lv_outputs_25_0= ruleFuzzyVariable
                    	    {

                    	    							newCompositeNode(grammarAccess.getAlphaSystemAccess().getOutputsFuzzyVariableParserRuleCall_5_1_2_0());
                    	    						
                    	    pushFollow(FOLLOW_18);
                    	    lv_outputs_25_0=ruleFuzzyVariable();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"outputs",
                    	    								lv_outputs_25_0,
                    	    								"alpha.model.Alpha.FuzzyVariable");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // InternalAlpha.g:847:5: ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) )
                    	    {
                    	    // InternalAlpha.g:847:5: ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) )
                    	    // InternalAlpha.g:848:6: ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) )
                    	    {
                    	    // InternalAlpha.g:848:6: ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) )
                    	    // InternalAlpha.g:849:7: (lv_outputs_26_0= ruleFuzzyVariableNameOnly )
                    	    {
                    	    // InternalAlpha.g:849:7: (lv_outputs_26_0= ruleFuzzyVariableNameOnly )
                    	    // InternalAlpha.g:850:8: lv_outputs_26_0= ruleFuzzyVariableNameOnly
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getOutputsFuzzyVariableNameOnlyParserRuleCall_5_1_3_0_0());
                    	    							
                    	    pushFollow(FOLLOW_17);
                    	    lv_outputs_26_0=ruleFuzzyVariableNameOnly();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"outputs",
                    	    									lv_outputs_26_0,
                    	    									"alpha.model.Alpha.FuzzyVariableNameOnly");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }

                    	    otherlv_27=(Token)match(input,24,FOLLOW_15); 

                    	    						newLeafNode(otherlv_27, grammarAccess.getAlphaSystemAccess().getCommaKeyword_5_1_3_1());
                    	    					
                    	    // InternalAlpha.g:871:6: ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )*
                    	    loop13:
                    	    do {
                    	        int alt13=2;
                    	        int LA13_0 = input.LA(1);

                    	        if ( (LA13_0==RULE_ID) ) {
                    	            alt13=1;
                    	        }


                    	        switch (alt13) {
                    	    	case 1 :
                    	    	    // InternalAlpha.g:872:7: ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ','
                    	    	    {
                    	    	    // InternalAlpha.g:872:7: ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) )
                    	    	    // InternalAlpha.g:873:8: (lv_outputs_28_0= ruleFuzzyVariableNameOnly )
                    	    	    {
                    	    	    // InternalAlpha.g:873:8: (lv_outputs_28_0= ruleFuzzyVariableNameOnly )
                    	    	    // InternalAlpha.g:874:9: lv_outputs_28_0= ruleFuzzyVariableNameOnly
                    	    	    {

                    	    	    									newCompositeNode(grammarAccess.getAlphaSystemAccess().getOutputsFuzzyVariableNameOnlyParserRuleCall_5_1_3_2_0_0());
                    	    	    								
                    	    	    pushFollow(FOLLOW_17);
                    	    	    lv_outputs_28_0=ruleFuzzyVariableNameOnly();

                    	    	    state._fsp--;


                    	    	    									if (current==null) {
                    	    	    										current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    	    									}
                    	    	    									add(
                    	    	    										current,
                    	    	    										"outputs",
                    	    	    										lv_outputs_28_0,
                    	    	    										"alpha.model.Alpha.FuzzyVariableNameOnly");
                    	    	    									afterParserOrEnumRuleCall();
                    	    	    								

                    	    	    }


                    	    	    }

                    	    	    otherlv_29=(Token)match(input,24,FOLLOW_15); 

                    	    	    							newLeafNode(otherlv_29, grammarAccess.getAlphaSystemAccess().getCommaKeyword_5_1_3_2_1());
                    	    	    						

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop13;
                    	        }
                    	    } while (true);

                    	    // InternalAlpha.g:896:6: ( (lv_outputs_30_0= ruleFuzzyVariable ) )
                    	    // InternalAlpha.g:897:7: (lv_outputs_30_0= ruleFuzzyVariable )
                    	    {
                    	    // InternalAlpha.g:897:7: (lv_outputs_30_0= ruleFuzzyVariable )
                    	    // InternalAlpha.g:898:8: lv_outputs_30_0= ruleFuzzyVariable
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getOutputsFuzzyVariableParserRuleCall_5_1_3_3_0());
                    	    							
                    	    pushFollow(FOLLOW_18);
                    	    lv_outputs_30_0=ruleFuzzyVariable();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"outputs",
                    	    									lv_outputs_30_0,
                    	    									"alpha.model.Alpha.FuzzyVariable");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt14 >= 1 ) break loop14;
                                EarlyExitException eee =
                                    new EarlyExitException(14, input);
                                throw eee;
                        }
                        cnt14++;
                    } while (true);


                    }
                    break;

            }

            // InternalAlpha.g:918:3: (otherlv_31= 'locals' ( ( (lv_locals_32_0= ruleVariable ) ) | ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) ) | ( (lv_locals_38_0= ruleFuzzyVariable ) ) | ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) ) )+ )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==26) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalAlpha.g:919:4: otherlv_31= 'locals' ( ( (lv_locals_32_0= ruleVariable ) ) | ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) ) | ( (lv_locals_38_0= ruleFuzzyVariable ) ) | ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) ) )+
                    {
                    otherlv_31=(Token)match(input,26,FOLLOW_15); 

                    				newLeafNode(otherlv_31, grammarAccess.getAlphaSystemAccess().getLocalsKeyword_6_0());
                    			
                    // InternalAlpha.g:923:4: ( ( (lv_locals_32_0= ruleVariable ) ) | ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) ) | ( (lv_locals_38_0= ruleFuzzyVariable ) ) | ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) ) )+
                    int cnt18=0;
                    loop18:
                    do {
                        int alt18=5;
                        alt18 = dfa18.predict(input);
                        switch (alt18) {
                    	case 1 :
                    	    // InternalAlpha.g:924:5: ( (lv_locals_32_0= ruleVariable ) )
                    	    {
                    	    // InternalAlpha.g:924:5: ( (lv_locals_32_0= ruleVariable ) )
                    	    // InternalAlpha.g:925:6: (lv_locals_32_0= ruleVariable )
                    	    {
                    	    // InternalAlpha.g:925:6: (lv_locals_32_0= ruleVariable )
                    	    // InternalAlpha.g:926:7: lv_locals_32_0= ruleVariable
                    	    {

                    	    							newCompositeNode(grammarAccess.getAlphaSystemAccess().getLocalsVariableParserRuleCall_6_1_0_0());
                    	    						
                    	    pushFollow(FOLLOW_19);
                    	    lv_locals_32_0=ruleVariable();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"locals",
                    	    								lv_locals_32_0,
                    	    								"alpha.model.Alpha.Variable");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalAlpha.g:944:5: ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) )
                    	    {
                    	    // InternalAlpha.g:944:5: ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) )
                    	    // InternalAlpha.g:945:6: ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) )
                    	    {
                    	    // InternalAlpha.g:945:6: ( (lv_locals_33_0= ruleVariableNameOnly ) )
                    	    // InternalAlpha.g:946:7: (lv_locals_33_0= ruleVariableNameOnly )
                    	    {
                    	    // InternalAlpha.g:946:7: (lv_locals_33_0= ruleVariableNameOnly )
                    	    // InternalAlpha.g:947:8: lv_locals_33_0= ruleVariableNameOnly
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getLocalsVariableNameOnlyParserRuleCall_6_1_1_0_0());
                    	    							
                    	    pushFollow(FOLLOW_17);
                    	    lv_locals_33_0=ruleVariableNameOnly();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"locals",
                    	    									lv_locals_33_0,
                    	    									"alpha.model.Alpha.VariableNameOnly");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }

                    	    otherlv_34=(Token)match(input,24,FOLLOW_5); 

                    	    						newLeafNode(otherlv_34, grammarAccess.getAlphaSystemAccess().getCommaKeyword_6_1_1_1());
                    	    					
                    	    // InternalAlpha.g:968:6: ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )*
                    	    loop16:
                    	    do {
                    	        int alt16=2;
                    	        int LA16_0 = input.LA(1);

                    	        if ( (LA16_0==RULE_ID) ) {
                    	            int LA16_1 = input.LA(2);

                    	            if ( (LA16_1==24) ) {
                    	                alt16=1;
                    	            }


                    	        }


                    	        switch (alt16) {
                    	    	case 1 :
                    	    	    // InternalAlpha.g:969:7: ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ','
                    	    	    {
                    	    	    // InternalAlpha.g:969:7: ( (lv_locals_35_0= ruleVariableNameOnly ) )
                    	    	    // InternalAlpha.g:970:8: (lv_locals_35_0= ruleVariableNameOnly )
                    	    	    {
                    	    	    // InternalAlpha.g:970:8: (lv_locals_35_0= ruleVariableNameOnly )
                    	    	    // InternalAlpha.g:971:9: lv_locals_35_0= ruleVariableNameOnly
                    	    	    {

                    	    	    									newCompositeNode(grammarAccess.getAlphaSystemAccess().getLocalsVariableNameOnlyParserRuleCall_6_1_1_2_0_0());
                    	    	    								
                    	    	    pushFollow(FOLLOW_17);
                    	    	    lv_locals_35_0=ruleVariableNameOnly();

                    	    	    state._fsp--;


                    	    	    									if (current==null) {
                    	    	    										current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    	    									}
                    	    	    									add(
                    	    	    										current,
                    	    	    										"locals",
                    	    	    										lv_locals_35_0,
                    	    	    										"alpha.model.Alpha.VariableNameOnly");
                    	    	    									afterParserOrEnumRuleCall();
                    	    	    								

                    	    	    }


                    	    	    }

                    	    	    otherlv_36=(Token)match(input,24,FOLLOW_5); 

                    	    	    							newLeafNode(otherlv_36, grammarAccess.getAlphaSystemAccess().getCommaKeyword_6_1_1_2_1());
                    	    	    						

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop16;
                    	        }
                    	    } while (true);

                    	    // InternalAlpha.g:993:6: ( (lv_locals_37_0= ruleVariable ) )
                    	    // InternalAlpha.g:994:7: (lv_locals_37_0= ruleVariable )
                    	    {
                    	    // InternalAlpha.g:994:7: (lv_locals_37_0= ruleVariable )
                    	    // InternalAlpha.g:995:8: lv_locals_37_0= ruleVariable
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getLocalsVariableParserRuleCall_6_1_1_3_0());
                    	    							
                    	    pushFollow(FOLLOW_19);
                    	    lv_locals_37_0=ruleVariable();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"locals",
                    	    									lv_locals_37_0,
                    	    									"alpha.model.Alpha.Variable");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // InternalAlpha.g:1014:5: ( (lv_locals_38_0= ruleFuzzyVariable ) )
                    	    {
                    	    // InternalAlpha.g:1014:5: ( (lv_locals_38_0= ruleFuzzyVariable ) )
                    	    // InternalAlpha.g:1015:6: (lv_locals_38_0= ruleFuzzyVariable )
                    	    {
                    	    // InternalAlpha.g:1015:6: (lv_locals_38_0= ruleFuzzyVariable )
                    	    // InternalAlpha.g:1016:7: lv_locals_38_0= ruleFuzzyVariable
                    	    {

                    	    							newCompositeNode(grammarAccess.getAlphaSystemAccess().getLocalsFuzzyVariableParserRuleCall_6_1_2_0());
                    	    						
                    	    pushFollow(FOLLOW_19);
                    	    lv_locals_38_0=ruleFuzzyVariable();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"locals",
                    	    								lv_locals_38_0,
                    	    								"alpha.model.Alpha.FuzzyVariable");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // InternalAlpha.g:1034:5: ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) )
                    	    {
                    	    // InternalAlpha.g:1034:5: ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) )
                    	    // InternalAlpha.g:1035:6: ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) )
                    	    {
                    	    // InternalAlpha.g:1035:6: ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) )
                    	    // InternalAlpha.g:1036:7: (lv_locals_39_0= ruleFuzzyVariableNameOnly )
                    	    {
                    	    // InternalAlpha.g:1036:7: (lv_locals_39_0= ruleFuzzyVariableNameOnly )
                    	    // InternalAlpha.g:1037:8: lv_locals_39_0= ruleFuzzyVariableNameOnly
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getLocalsFuzzyVariableNameOnlyParserRuleCall_6_1_3_0_0());
                    	    							
                    	    pushFollow(FOLLOW_17);
                    	    lv_locals_39_0=ruleFuzzyVariableNameOnly();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"locals",
                    	    									lv_locals_39_0,
                    	    									"alpha.model.Alpha.FuzzyVariableNameOnly");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }

                    	    otherlv_40=(Token)match(input,24,FOLLOW_15); 

                    	    						newLeafNode(otherlv_40, grammarAccess.getAlphaSystemAccess().getCommaKeyword_6_1_3_1());
                    	    					
                    	    // InternalAlpha.g:1058:6: ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )*
                    	    loop17:
                    	    do {
                    	        int alt17=2;
                    	        int LA17_0 = input.LA(1);

                    	        if ( (LA17_0==RULE_ID) ) {
                    	            alt17=1;
                    	        }


                    	        switch (alt17) {
                    	    	case 1 :
                    	    	    // InternalAlpha.g:1059:7: ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ','
                    	    	    {
                    	    	    // InternalAlpha.g:1059:7: ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) )
                    	    	    // InternalAlpha.g:1060:8: (lv_locals_41_0= ruleFuzzyVariableNameOnly )
                    	    	    {
                    	    	    // InternalAlpha.g:1060:8: (lv_locals_41_0= ruleFuzzyVariableNameOnly )
                    	    	    // InternalAlpha.g:1061:9: lv_locals_41_0= ruleFuzzyVariableNameOnly
                    	    	    {

                    	    	    									newCompositeNode(grammarAccess.getAlphaSystemAccess().getLocalsFuzzyVariableNameOnlyParserRuleCall_6_1_3_2_0_0());
                    	    	    								
                    	    	    pushFollow(FOLLOW_17);
                    	    	    lv_locals_41_0=ruleFuzzyVariableNameOnly();

                    	    	    state._fsp--;


                    	    	    									if (current==null) {
                    	    	    										current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    	    									}
                    	    	    									add(
                    	    	    										current,
                    	    	    										"locals",
                    	    	    										lv_locals_41_0,
                    	    	    										"alpha.model.Alpha.FuzzyVariableNameOnly");
                    	    	    									afterParserOrEnumRuleCall();
                    	    	    								

                    	    	    }


                    	    	    }

                    	    	    otherlv_42=(Token)match(input,24,FOLLOW_15); 

                    	    	    							newLeafNode(otherlv_42, grammarAccess.getAlphaSystemAccess().getCommaKeyword_6_1_3_2_1());
                    	    	    						

                    	    	    }
                    	    	    break;

                    	    	default :
                    	    	    break loop17;
                    	        }
                    	    } while (true);

                    	    // InternalAlpha.g:1083:6: ( (lv_locals_43_0= ruleFuzzyVariable ) )
                    	    // InternalAlpha.g:1084:7: (lv_locals_43_0= ruleFuzzyVariable )
                    	    {
                    	    // InternalAlpha.g:1084:7: (lv_locals_43_0= ruleFuzzyVariable )
                    	    // InternalAlpha.g:1085:8: lv_locals_43_0= ruleFuzzyVariable
                    	    {

                    	    								newCompositeNode(grammarAccess.getAlphaSystemAccess().getLocalsFuzzyVariableParserRuleCall_6_1_3_3_0());
                    	    							
                    	    pushFollow(FOLLOW_19);
                    	    lv_locals_43_0=ruleFuzzyVariable();

                    	    state._fsp--;


                    	    								if (current==null) {
                    	    									current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    	    								}
                    	    								add(
                    	    									current,
                    	    									"locals",
                    	    									lv_locals_43_0,
                    	    									"alpha.model.Alpha.FuzzyVariable");
                    	    								afterParserOrEnumRuleCall();
                    	    							

                    	    }


                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt18 >= 1 ) break loop18;
                                EarlyExitException eee =
                                    new EarlyExitException(18, input);
                                throw eee;
                        }
                        cnt18++;
                    } while (true);


                    }
                    break;

            }

            // InternalAlpha.g:1105:3: (otherlv_44= 'over' ( (lv_whileDomainExpr_45_0= ruleCalculatorExpression ) ) otherlv_46= 'while' otherlv_47= '(' ( (lv_testExpression_48_0= ruleAlphaExpression ) ) otherlv_49= ')' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==27) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // InternalAlpha.g:1106:4: otherlv_44= 'over' ( (lv_whileDomainExpr_45_0= ruleCalculatorExpression ) ) otherlv_46= 'while' otherlv_47= '(' ( (lv_testExpression_48_0= ruleAlphaExpression ) ) otherlv_49= ')'
                    {
                    otherlv_44=(Token)match(input,27,FOLLOW_20); 

                    				newLeafNode(otherlv_44, grammarAccess.getAlphaSystemAccess().getOverKeyword_7_0());
                    			
                    // InternalAlpha.g:1110:4: ( (lv_whileDomainExpr_45_0= ruleCalculatorExpression ) )
                    // InternalAlpha.g:1111:5: (lv_whileDomainExpr_45_0= ruleCalculatorExpression )
                    {
                    // InternalAlpha.g:1111:5: (lv_whileDomainExpr_45_0= ruleCalculatorExpression )
                    // InternalAlpha.g:1112:6: lv_whileDomainExpr_45_0= ruleCalculatorExpression
                    {

                    						newCompositeNode(grammarAccess.getAlphaSystemAccess().getWhileDomainExprCalculatorExpressionParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_21);
                    lv_whileDomainExpr_45_0=ruleCalculatorExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    						}
                    						set(
                    							current,
                    							"whileDomainExpr",
                    							lv_whileDomainExpr_45_0,
                    							"alpha.model.Alpha.CalculatorExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_46=(Token)match(input,28,FOLLOW_8); 

                    				newLeafNode(otherlv_46, grammarAccess.getAlphaSystemAccess().getWhileKeyword_7_2());
                    			
                    otherlv_47=(Token)match(input,15,FOLLOW_22); 

                    				newLeafNode(otherlv_47, grammarAccess.getAlphaSystemAccess().getLeftParenthesisKeyword_7_3());
                    			
                    // InternalAlpha.g:1137:4: ( (lv_testExpression_48_0= ruleAlphaExpression ) )
                    // InternalAlpha.g:1138:5: (lv_testExpression_48_0= ruleAlphaExpression )
                    {
                    // InternalAlpha.g:1138:5: (lv_testExpression_48_0= ruleAlphaExpression )
                    // InternalAlpha.g:1139:6: lv_testExpression_48_0= ruleAlphaExpression
                    {

                    						newCompositeNode(grammarAccess.getAlphaSystemAccess().getTestExpressionAlphaExpressionParserRuleCall_7_4_0());
                    					
                    pushFollow(FOLLOW_9);
                    lv_testExpression_48_0=ruleAlphaExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
                    						}
                    						set(
                    							current,
                    							"testExpression",
                    							lv_testExpression_48_0,
                    							"alpha.model.Alpha.AlphaExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_49=(Token)match(input,16,FOLLOW_23); 

                    				newLeafNode(otherlv_49, grammarAccess.getAlphaSystemAccess().getRightParenthesisKeyword_7_5());
                    			

                    }
                    break;

            }

            // InternalAlpha.g:1161:3: ( (lv_systemBodies_50_0= ruleSystemBody ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>=30 && LA21_0<=32)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalAlpha.g:1162:4: (lv_systemBodies_50_0= ruleSystemBody )
            	    {
            	    // InternalAlpha.g:1162:4: (lv_systemBodies_50_0= ruleSystemBody )
            	    // InternalAlpha.g:1163:5: lv_systemBodies_50_0= ruleSystemBody
            	    {

            	    					newCompositeNode(grammarAccess.getAlphaSystemAccess().getSystemBodiesSystemBodyParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_23);
            	    lv_systemBodies_50_0=ruleSystemBody();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getAlphaSystemRule());
            	    					}
            	    					add(
            	    						current,
            	    						"systemBodies",
            	    						lv_systemBodies_50_0,
            	    						"alpha.model.Alpha.SystemBody");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            otherlv_51=(Token)match(input,29,FOLLOW_2); 

            			newLeafNode(otherlv_51, grammarAccess.getAlphaSystemAccess().getFullStopKeyword_9());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaSystem"


    // $ANTLR start "entryRuleSystemBody"
    // InternalAlpha.g:1188:1: entryRuleSystemBody returns [EObject current=null] : iv_ruleSystemBody= ruleSystemBody EOF ;
    public final EObject entryRuleSystemBody() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSystemBody = null;


        try {
            // InternalAlpha.g:1188:51: (iv_ruleSystemBody= ruleSystemBody EOF )
            // InternalAlpha.g:1189:2: iv_ruleSystemBody= ruleSystemBody EOF
            {
             newCompositeNode(grammarAccess.getSystemBodyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSystemBody=ruleSystemBody();

            state._fsp--;

             current =iv_ruleSystemBody; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSystemBody"


    // $ANTLR start "ruleSystemBody"
    // InternalAlpha.g:1195:1: ruleSystemBody returns [EObject current=null] : ( () ( (otherlv_1= 'when' ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) ) ) | otherlv_3= 'else' )? otherlv_4= 'let' ( (lv_equations_5_0= ruleEquation ) )* ) ;
    public final EObject ruleSystemBody() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        EObject lv_parameterDomainExpr_2_0 = null;

        EObject lv_equations_5_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1201:2: ( ( () ( (otherlv_1= 'when' ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) ) ) | otherlv_3= 'else' )? otherlv_4= 'let' ( (lv_equations_5_0= ruleEquation ) )* ) )
            // InternalAlpha.g:1202:2: ( () ( (otherlv_1= 'when' ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) ) ) | otherlv_3= 'else' )? otherlv_4= 'let' ( (lv_equations_5_0= ruleEquation ) )* )
            {
            // InternalAlpha.g:1202:2: ( () ( (otherlv_1= 'when' ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) ) ) | otherlv_3= 'else' )? otherlv_4= 'let' ( (lv_equations_5_0= ruleEquation ) )* )
            // InternalAlpha.g:1203:3: () ( (otherlv_1= 'when' ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) ) ) | otherlv_3= 'else' )? otherlv_4= 'let' ( (lv_equations_5_0= ruleEquation ) )*
            {
            // InternalAlpha.g:1203:3: ()
            // InternalAlpha.g:1204:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getSystemBodyAccess().getSystemBodyAction_0(),
            					current);
            			

            }

            // InternalAlpha.g:1210:3: ( (otherlv_1= 'when' ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) ) ) | otherlv_3= 'else' )?
            int alt22=3;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==30) ) {
                alt22=1;
            }
            else if ( (LA22_0==31) ) {
                alt22=2;
            }
            switch (alt22) {
                case 1 :
                    // InternalAlpha.g:1211:4: (otherlv_1= 'when' ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) ) )
                    {
                    // InternalAlpha.g:1211:4: (otherlv_1= 'when' ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) ) )
                    // InternalAlpha.g:1212:5: otherlv_1= 'when' ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) )
                    {
                    otherlv_1=(Token)match(input,30,FOLLOW_10); 

                    					newLeafNode(otherlv_1, grammarAccess.getSystemBodyAccess().getWhenKeyword_1_0_0());
                    				
                    // InternalAlpha.g:1216:5: ( (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation ) )
                    // InternalAlpha.g:1217:6: (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation )
                    {
                    // InternalAlpha.g:1217:6: (lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation )
                    // InternalAlpha.g:1218:7: lv_parameterDomainExpr_2_0= ruleJNIParamDomainInArrayNotation
                    {

                    							newCompositeNode(grammarAccess.getSystemBodyAccess().getParameterDomainExprJNIParamDomainInArrayNotationParserRuleCall_1_0_1_0());
                    						
                    pushFollow(FOLLOW_24);
                    lv_parameterDomainExpr_2_0=ruleJNIParamDomainInArrayNotation();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getSystemBodyRule());
                    							}
                    							set(
                    								current,
                    								"parameterDomainExpr",
                    								lv_parameterDomainExpr_2_0,
                    								"alpha.model.Alpha.JNIParamDomainInArrayNotation");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:1237:4: otherlv_3= 'else'
                    {
                    otherlv_3=(Token)match(input,31,FOLLOW_24); 

                    				newLeafNode(otherlv_3, grammarAccess.getSystemBodyAccess().getElseKeyword_1_1());
                    			

                    }
                    break;

            }

            otherlv_4=(Token)match(input,32,FOLLOW_25); 

            			newLeafNode(otherlv_4, grammarAccess.getSystemBodyAccess().getLetKeyword_2());
            		
            // InternalAlpha.g:1246:3: ( (lv_equations_5_0= ruleEquation ) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==RULE_ID||LA23_0==15||LA23_0==27||LA23_0==33||LA23_0==58) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalAlpha.g:1247:4: (lv_equations_5_0= ruleEquation )
            	    {
            	    // InternalAlpha.g:1247:4: (lv_equations_5_0= ruleEquation )
            	    // InternalAlpha.g:1248:5: lv_equations_5_0= ruleEquation
            	    {

            	    					newCompositeNode(grammarAccess.getSystemBodyAccess().getEquationsEquationParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_25);
            	    lv_equations_5_0=ruleEquation();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getSystemBodyRule());
            	    					}
            	    					add(
            	    						current,
            	    						"equations",
            	    						lv_equations_5_0,
            	    						"alpha.model.Alpha.Equation");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSystemBody"


    // $ANTLR start "entryRuleVariable"
    // InternalAlpha.g:1269:1: entryRuleVariable returns [EObject current=null] : iv_ruleVariable= ruleVariable EOF ;
    public final EObject entryRuleVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariable = null;


        try {
            // InternalAlpha.g:1269:49: (iv_ruleVariable= ruleVariable EOF )
            // InternalAlpha.g:1270:2: iv_ruleVariable= ruleVariable EOF
            {
             newCompositeNode(grammarAccess.getVariableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariable=ruleVariable();

            state._fsp--;

             current =iv_ruleVariable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariable"


    // $ANTLR start "ruleVariable"
    // InternalAlpha.g:1276:1: ruleVariable returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_domainExpr_2_0= ruleCalculatorExpression ) ) (otherlv_3= ';' )? ) ;
    public final EObject ruleVariable() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_domainExpr_2_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1282:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_domainExpr_2_0= ruleCalculatorExpression ) ) (otherlv_3= ';' )? ) )
            // InternalAlpha.g:1283:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_domainExpr_2_0= ruleCalculatorExpression ) ) (otherlv_3= ';' )? )
            {
            // InternalAlpha.g:1283:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_domainExpr_2_0= ruleCalculatorExpression ) ) (otherlv_3= ';' )? )
            // InternalAlpha.g:1284:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= ':' ( (lv_domainExpr_2_0= ruleCalculatorExpression ) ) (otherlv_3= ';' )?
            {
            // InternalAlpha.g:1284:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalAlpha.g:1285:4: (lv_name_0_0= RULE_ID )
            {
            // InternalAlpha.g:1285:4: (lv_name_0_0= RULE_ID )
            // InternalAlpha.g:1286:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_26); 

            					newLeafNode(lv_name_0_0, grammarAccess.getVariableAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariableRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"alpha.model.Alpha.ID");
            				

            }


            }

            otherlv_1=(Token)match(input,33,FOLLOW_20); 

            			newLeafNode(otherlv_1, grammarAccess.getVariableAccess().getColonKeyword_1());
            		
            // InternalAlpha.g:1306:3: ( (lv_domainExpr_2_0= ruleCalculatorExpression ) )
            // InternalAlpha.g:1307:4: (lv_domainExpr_2_0= ruleCalculatorExpression )
            {
            // InternalAlpha.g:1307:4: (lv_domainExpr_2_0= ruleCalculatorExpression )
            // InternalAlpha.g:1308:5: lv_domainExpr_2_0= ruleCalculatorExpression
            {

            					newCompositeNode(grammarAccess.getVariableAccess().getDomainExprCalculatorExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_27);
            lv_domainExpr_2_0=ruleCalculatorExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getVariableRule());
            					}
            					set(
            						current,
            						"domainExpr",
            						lv_domainExpr_2_0,
            						"alpha.model.Alpha.CalculatorExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:1325:3: (otherlv_3= ';' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==34) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalAlpha.g:1326:4: otherlv_3= ';'
                    {
                    otherlv_3=(Token)match(input,34,FOLLOW_2); 

                    				newLeafNode(otherlv_3, grammarAccess.getVariableAccess().getSemicolonKeyword_3());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariable"


    // $ANTLR start "entryRuleFuzzyVariable"
    // InternalAlpha.g:1335:1: entryRuleFuzzyVariable returns [EObject current=null] : iv_ruleFuzzyVariable= ruleFuzzyVariable EOF ;
    public final EObject entryRuleFuzzyVariable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFuzzyVariable = null;


        try {
            // InternalAlpha.g:1335:54: (iv_ruleFuzzyVariable= ruleFuzzyVariable EOF )
            // InternalAlpha.g:1336:2: iv_ruleFuzzyVariable= ruleFuzzyVariable EOF
            {
             newCompositeNode(grammarAccess.getFuzzyVariableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFuzzyVariable=ruleFuzzyVariable();

            state._fsp--;

             current =iv_ruleFuzzyVariable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFuzzyVariable"


    // $ANTLR start "ruleFuzzyVariable"
    // InternalAlpha.g:1342:1: ruleFuzzyVariable returns [EObject current=null] : (otherlv_0= 'fuzzy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_domainExpr_3_0= ruleCalculatorExpression ) ) otherlv_4= '->' ( (lv_rangeExpr_5_0= ruleCalculatorExpression ) ) (otherlv_6= ';' )? ) ;
    public final EObject ruleFuzzyVariable() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_domainExpr_3_0 = null;

        EObject lv_rangeExpr_5_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1348:2: ( (otherlv_0= 'fuzzy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_domainExpr_3_0= ruleCalculatorExpression ) ) otherlv_4= '->' ( (lv_rangeExpr_5_0= ruleCalculatorExpression ) ) (otherlv_6= ';' )? ) )
            // InternalAlpha.g:1349:2: (otherlv_0= 'fuzzy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_domainExpr_3_0= ruleCalculatorExpression ) ) otherlv_4= '->' ( (lv_rangeExpr_5_0= ruleCalculatorExpression ) ) (otherlv_6= ';' )? )
            {
            // InternalAlpha.g:1349:2: (otherlv_0= 'fuzzy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_domainExpr_3_0= ruleCalculatorExpression ) ) otherlv_4= '->' ( (lv_rangeExpr_5_0= ruleCalculatorExpression ) ) (otherlv_6= ';' )? )
            // InternalAlpha.g:1350:3: otherlv_0= 'fuzzy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= ':' ( (lv_domainExpr_3_0= ruleCalculatorExpression ) ) otherlv_4= '->' ( (lv_rangeExpr_5_0= ruleCalculatorExpression ) ) (otherlv_6= ';' )?
            {
            otherlv_0=(Token)match(input,35,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getFuzzyVariableAccess().getFuzzyKeyword_0());
            		
            // InternalAlpha.g:1354:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalAlpha.g:1355:4: (lv_name_1_0= RULE_ID )
            {
            // InternalAlpha.g:1355:4: (lv_name_1_0= RULE_ID )
            // InternalAlpha.g:1356:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_26); 

            					newLeafNode(lv_name_1_0, grammarAccess.getFuzzyVariableAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFuzzyVariableRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"alpha.model.Alpha.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,33,FOLLOW_20); 

            			newLeafNode(otherlv_2, grammarAccess.getFuzzyVariableAccess().getColonKeyword_2());
            		
            // InternalAlpha.g:1376:3: ( (lv_domainExpr_3_0= ruleCalculatorExpression ) )
            // InternalAlpha.g:1377:4: (lv_domainExpr_3_0= ruleCalculatorExpression )
            {
            // InternalAlpha.g:1377:4: (lv_domainExpr_3_0= ruleCalculatorExpression )
            // InternalAlpha.g:1378:5: lv_domainExpr_3_0= ruleCalculatorExpression
            {

            					newCompositeNode(grammarAccess.getFuzzyVariableAccess().getDomainExprCalculatorExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_28);
            lv_domainExpr_3_0=ruleCalculatorExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFuzzyVariableRule());
            					}
            					set(
            						current,
            						"domainExpr",
            						lv_domainExpr_3_0,
            						"alpha.model.Alpha.CalculatorExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,36,FOLLOW_20); 

            			newLeafNode(otherlv_4, grammarAccess.getFuzzyVariableAccess().getHyphenMinusGreaterThanSignKeyword_4());
            		
            // InternalAlpha.g:1399:3: ( (lv_rangeExpr_5_0= ruleCalculatorExpression ) )
            // InternalAlpha.g:1400:4: (lv_rangeExpr_5_0= ruleCalculatorExpression )
            {
            // InternalAlpha.g:1400:4: (lv_rangeExpr_5_0= ruleCalculatorExpression )
            // InternalAlpha.g:1401:5: lv_rangeExpr_5_0= ruleCalculatorExpression
            {

            					newCompositeNode(grammarAccess.getFuzzyVariableAccess().getRangeExprCalculatorExpressionParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_27);
            lv_rangeExpr_5_0=ruleCalculatorExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFuzzyVariableRule());
            					}
            					set(
            						current,
            						"rangeExpr",
            						lv_rangeExpr_5_0,
            						"alpha.model.Alpha.CalculatorExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:1418:3: (otherlv_6= ';' )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==34) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // InternalAlpha.g:1419:4: otherlv_6= ';'
                    {
                    otherlv_6=(Token)match(input,34,FOLLOW_2); 

                    				newLeafNode(otherlv_6, grammarAccess.getFuzzyVariableAccess().getSemicolonKeyword_6());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFuzzyVariable"


    // $ANTLR start "entryRuleVariableNameOnly"
    // InternalAlpha.g:1428:1: entryRuleVariableNameOnly returns [EObject current=null] : iv_ruleVariableNameOnly= ruleVariableNameOnly EOF ;
    public final EObject entryRuleVariableNameOnly() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableNameOnly = null;


        try {
            // InternalAlpha.g:1428:57: (iv_ruleVariableNameOnly= ruleVariableNameOnly EOF )
            // InternalAlpha.g:1429:2: iv_ruleVariableNameOnly= ruleVariableNameOnly EOF
            {
             newCompositeNode(grammarAccess.getVariableNameOnlyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariableNameOnly=ruleVariableNameOnly();

            state._fsp--;

             current =iv_ruleVariableNameOnly; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableNameOnly"


    // $ANTLR start "ruleVariableNameOnly"
    // InternalAlpha.g:1435:1: ruleVariableNameOnly returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleVariableNameOnly() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalAlpha.g:1441:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalAlpha.g:1442:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalAlpha.g:1442:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalAlpha.g:1443:3: (lv_name_0_0= RULE_ID )
            {
            // InternalAlpha.g:1443:3: (lv_name_0_0= RULE_ID )
            // InternalAlpha.g:1444:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getVariableNameOnlyAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getVariableNameOnlyRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"alpha.model.Alpha.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableNameOnly"


    // $ANTLR start "entryRuleFuzzyVariableNameOnly"
    // InternalAlpha.g:1463:1: entryRuleFuzzyVariableNameOnly returns [EObject current=null] : iv_ruleFuzzyVariableNameOnly= ruleFuzzyVariableNameOnly EOF ;
    public final EObject entryRuleFuzzyVariableNameOnly() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFuzzyVariableNameOnly = null;


        try {
            // InternalAlpha.g:1463:62: (iv_ruleFuzzyVariableNameOnly= ruleFuzzyVariableNameOnly EOF )
            // InternalAlpha.g:1464:2: iv_ruleFuzzyVariableNameOnly= ruleFuzzyVariableNameOnly EOF
            {
             newCompositeNode(grammarAccess.getFuzzyVariableNameOnlyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFuzzyVariableNameOnly=ruleFuzzyVariableNameOnly();

            state._fsp--;

             current =iv_ruleFuzzyVariableNameOnly; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFuzzyVariableNameOnly"


    // $ANTLR start "ruleFuzzyVariableNameOnly"
    // InternalAlpha.g:1470:1: ruleFuzzyVariableNameOnly returns [EObject current=null] : ( (lv_name_0_0= RULE_ID ) ) ;
    public final EObject ruleFuzzyVariableNameOnly() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;


        	enterRule();

        try {
            // InternalAlpha.g:1476:2: ( ( (lv_name_0_0= RULE_ID ) ) )
            // InternalAlpha.g:1477:2: ( (lv_name_0_0= RULE_ID ) )
            {
            // InternalAlpha.g:1477:2: ( (lv_name_0_0= RULE_ID ) )
            // InternalAlpha.g:1478:3: (lv_name_0_0= RULE_ID )
            {
            // InternalAlpha.g:1478:3: (lv_name_0_0= RULE_ID )
            // InternalAlpha.g:1479:4: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(lv_name_0_0, grammarAccess.getFuzzyVariableNameOnlyAccess().getNameIDTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getFuzzyVariableNameOnlyRule());
            				}
            				setWithLastConsumed(
            					current,
            					"name",
            					lv_name_0_0,
            					"alpha.model.Alpha.ID");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFuzzyVariableNameOnly"


    // $ANTLR start "entryRuleJNIDomain"
    // InternalAlpha.g:1498:1: entryRuleJNIDomain returns [EObject current=null] : iv_ruleJNIDomain= ruleJNIDomain EOF ;
    public final EObject entryRuleJNIDomain() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJNIDomain = null;


        try {
            // InternalAlpha.g:1498:50: (iv_ruleJNIDomain= ruleJNIDomain EOF )
            // InternalAlpha.g:1499:2: iv_ruleJNIDomain= ruleJNIDomain EOF
            {
             newCompositeNode(grammarAccess.getJNIDomainRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJNIDomain=ruleJNIDomain();

            state._fsp--;

             current =iv_ruleJNIDomain; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJNIDomain"


    // $ANTLR start "ruleJNIDomain"
    // InternalAlpha.g:1505:1: ruleJNIDomain returns [EObject current=null] : ( (lv_islString_0_0= ruleAISLSet ) ) ;
    public final EObject ruleJNIDomain() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_islString_0_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1511:2: ( ( (lv_islString_0_0= ruleAISLSet ) ) )
            // InternalAlpha.g:1512:2: ( (lv_islString_0_0= ruleAISLSet ) )
            {
            // InternalAlpha.g:1512:2: ( (lv_islString_0_0= ruleAISLSet ) )
            // InternalAlpha.g:1513:3: (lv_islString_0_0= ruleAISLSet )
            {
            // InternalAlpha.g:1513:3: (lv_islString_0_0= ruleAISLSet )
            // InternalAlpha.g:1514:4: lv_islString_0_0= ruleAISLSet
            {

            				newCompositeNode(grammarAccess.getJNIDomainAccess().getIslStringAISLSetParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_islString_0_0=ruleAISLSet();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getJNIDomainRule());
            				}
            				set(
            					current,
            					"islString",
            					lv_islString_0_0,
            					"alpha.model.Alpha.AISLSet");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJNIDomain"


    // $ANTLR start "entryRuleJNIDomainInArrayNotation"
    // InternalAlpha.g:1534:1: entryRuleJNIDomainInArrayNotation returns [EObject current=null] : iv_ruleJNIDomainInArrayNotation= ruleJNIDomainInArrayNotation EOF ;
    public final EObject entryRuleJNIDomainInArrayNotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJNIDomainInArrayNotation = null;


        try {
            // InternalAlpha.g:1534:65: (iv_ruleJNIDomainInArrayNotation= ruleJNIDomainInArrayNotation EOF )
            // InternalAlpha.g:1535:2: iv_ruleJNIDomainInArrayNotation= ruleJNIDomainInArrayNotation EOF
            {
             newCompositeNode(grammarAccess.getJNIDomainInArrayNotationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJNIDomainInArrayNotation=ruleJNIDomainInArrayNotation();

            state._fsp--;

             current =iv_ruleJNIDomainInArrayNotation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJNIDomainInArrayNotation"


    // $ANTLR start "ruleJNIDomainInArrayNotation"
    // InternalAlpha.g:1541:1: ruleJNIDomainInArrayNotation returns [EObject current=null] : (otherlv_0= '{' otherlv_1= ':' ( (lv_islString_2_0= ruleAISLString ) ) otherlv_3= '}' ) ;
    public final EObject ruleJNIDomainInArrayNotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_islString_2_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1547:2: ( (otherlv_0= '{' otherlv_1= ':' ( (lv_islString_2_0= ruleAISLString ) ) otherlv_3= '}' ) )
            // InternalAlpha.g:1548:2: (otherlv_0= '{' otherlv_1= ':' ( (lv_islString_2_0= ruleAISLString ) ) otherlv_3= '}' )
            {
            // InternalAlpha.g:1548:2: (otherlv_0= '{' otherlv_1= ':' ( (lv_islString_2_0= ruleAISLString ) ) otherlv_3= '}' )
            // InternalAlpha.g:1549:3: otherlv_0= '{' otherlv_1= ':' ( (lv_islString_2_0= ruleAISLString ) ) otherlv_3= '}'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_26); 

            			newLeafNode(otherlv_0, grammarAccess.getJNIDomainInArrayNotationAccess().getLeftCurlyBracketKeyword_0());
            		
            otherlv_1=(Token)match(input,33,FOLLOW_29); 

            			newLeafNode(otherlv_1, grammarAccess.getJNIDomainInArrayNotationAccess().getColonKeyword_1());
            		
            // InternalAlpha.g:1557:3: ( (lv_islString_2_0= ruleAISLString ) )
            // InternalAlpha.g:1558:4: (lv_islString_2_0= ruleAISLString )
            {
            // InternalAlpha.g:1558:4: (lv_islString_2_0= ruleAISLString )
            // InternalAlpha.g:1559:5: lv_islString_2_0= ruleAISLString
            {

            					newCompositeNode(grammarAccess.getJNIDomainInArrayNotationAccess().getIslStringAISLStringParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_30);
            lv_islString_2_0=ruleAISLString();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getJNIDomainInArrayNotationRule());
            					}
            					set(
            						current,
            						"islString",
            						lv_islString_2_0,
            						"alpha.model.Alpha.AISLString");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getJNIDomainInArrayNotationAccess().getRightCurlyBracketKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJNIDomainInArrayNotation"


    // $ANTLR start "entryRuleJNIParamDomain"
    // InternalAlpha.g:1584:1: entryRuleJNIParamDomain returns [EObject current=null] : iv_ruleJNIParamDomain= ruleJNIParamDomain EOF ;
    public final EObject entryRuleJNIParamDomain() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJNIParamDomain = null;


        try {
            // InternalAlpha.g:1584:55: (iv_ruleJNIParamDomain= ruleJNIParamDomain EOF )
            // InternalAlpha.g:1585:2: iv_ruleJNIParamDomain= ruleJNIParamDomain EOF
            {
             newCompositeNode(grammarAccess.getJNIParamDomainRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJNIParamDomain=ruleJNIParamDomain();

            state._fsp--;

             current =iv_ruleJNIParamDomain; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJNIParamDomain"


    // $ANTLR start "ruleJNIParamDomain"
    // InternalAlpha.g:1591:1: ruleJNIParamDomain returns [EObject current=null] : ( (lv_islString_0_0= ruleAParamDomain ) ) ;
    public final EObject ruleJNIParamDomain() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_islString_0_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1597:2: ( ( (lv_islString_0_0= ruleAParamDomain ) ) )
            // InternalAlpha.g:1598:2: ( (lv_islString_0_0= ruleAParamDomain ) )
            {
            // InternalAlpha.g:1598:2: ( (lv_islString_0_0= ruleAParamDomain ) )
            // InternalAlpha.g:1599:3: (lv_islString_0_0= ruleAParamDomain )
            {
            // InternalAlpha.g:1599:3: (lv_islString_0_0= ruleAParamDomain )
            // InternalAlpha.g:1600:4: lv_islString_0_0= ruleAParamDomain
            {

            				newCompositeNode(grammarAccess.getJNIParamDomainAccess().getIslStringAParamDomainParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_islString_0_0=ruleAParamDomain();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getJNIParamDomainRule());
            				}
            				set(
            					current,
            					"islString",
            					lv_islString_0_0,
            					"alpha.model.Alpha.AParamDomain");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJNIParamDomain"


    // $ANTLR start "entryRuleJNIParamDomainInArrayNotation"
    // InternalAlpha.g:1620:1: entryRuleJNIParamDomainInArrayNotation returns [EObject current=null] : iv_ruleJNIParamDomainInArrayNotation= ruleJNIParamDomainInArrayNotation EOF ;
    public final EObject entryRuleJNIParamDomainInArrayNotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJNIParamDomainInArrayNotation = null;


        try {
            // InternalAlpha.g:1620:70: (iv_ruleJNIParamDomainInArrayNotation= ruleJNIParamDomainInArrayNotation EOF )
            // InternalAlpha.g:1621:2: iv_ruleJNIParamDomainInArrayNotation= ruleJNIParamDomainInArrayNotation EOF
            {
             newCompositeNode(grammarAccess.getJNIParamDomainInArrayNotationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJNIParamDomainInArrayNotation=ruleJNIParamDomainInArrayNotation();

            state._fsp--;

             current =iv_ruleJNIParamDomainInArrayNotation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJNIParamDomainInArrayNotation"


    // $ANTLR start "ruleJNIParamDomainInArrayNotation"
    // InternalAlpha.g:1627:1: ruleJNIParamDomainInArrayNotation returns [EObject current=null] : ( (lv_islString_0_0= ruleAParamDomainInArrayNotation ) ) ;
    public final EObject ruleJNIParamDomainInArrayNotation() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_islString_0_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1633:2: ( ( (lv_islString_0_0= ruleAParamDomainInArrayNotation ) ) )
            // InternalAlpha.g:1634:2: ( (lv_islString_0_0= ruleAParamDomainInArrayNotation ) )
            {
            // InternalAlpha.g:1634:2: ( (lv_islString_0_0= ruleAParamDomainInArrayNotation ) )
            // InternalAlpha.g:1635:3: (lv_islString_0_0= ruleAParamDomainInArrayNotation )
            {
            // InternalAlpha.g:1635:3: (lv_islString_0_0= ruleAParamDomainInArrayNotation )
            // InternalAlpha.g:1636:4: lv_islString_0_0= ruleAParamDomainInArrayNotation
            {

            				newCompositeNode(grammarAccess.getJNIParamDomainInArrayNotationAccess().getIslStringAParamDomainInArrayNotationParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_islString_0_0=ruleAParamDomainInArrayNotation();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getJNIParamDomainInArrayNotationRule());
            				}
            				set(
            					current,
            					"islString",
            					lv_islString_0_0,
            					"alpha.model.Alpha.AParamDomainInArrayNotation");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJNIParamDomainInArrayNotation"


    // $ANTLR start "entryRuleJNIRelation"
    // InternalAlpha.g:1656:1: entryRuleJNIRelation returns [EObject current=null] : iv_ruleJNIRelation= ruleJNIRelation EOF ;
    public final EObject entryRuleJNIRelation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJNIRelation = null;


        try {
            // InternalAlpha.g:1656:52: (iv_ruleJNIRelation= ruleJNIRelation EOF )
            // InternalAlpha.g:1657:2: iv_ruleJNIRelation= ruleJNIRelation EOF
            {
             newCompositeNode(grammarAccess.getJNIRelationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJNIRelation=ruleJNIRelation();

            state._fsp--;

             current =iv_ruleJNIRelation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJNIRelation"


    // $ANTLR start "ruleJNIRelation"
    // InternalAlpha.g:1663:1: ruleJNIRelation returns [EObject current=null] : ( (lv_islString_0_0= ruleAISLRelation ) ) ;
    public final EObject ruleJNIRelation() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_islString_0_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1669:2: ( ( (lv_islString_0_0= ruleAISLRelation ) ) )
            // InternalAlpha.g:1670:2: ( (lv_islString_0_0= ruleAISLRelation ) )
            {
            // InternalAlpha.g:1670:2: ( (lv_islString_0_0= ruleAISLRelation ) )
            // InternalAlpha.g:1671:3: (lv_islString_0_0= ruleAISLRelation )
            {
            // InternalAlpha.g:1671:3: (lv_islString_0_0= ruleAISLRelation )
            // InternalAlpha.g:1672:4: lv_islString_0_0= ruleAISLRelation
            {

            				newCompositeNode(grammarAccess.getJNIRelationAccess().getIslStringAISLRelationParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_islString_0_0=ruleAISLRelation();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getJNIRelationRule());
            				}
            				set(
            					current,
            					"islString",
            					lv_islString_0_0,
            					"alpha.model.Alpha.AISLRelation");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJNIRelation"


    // $ANTLR start "entryRuleJNIFunction"
    // InternalAlpha.g:1692:1: entryRuleJNIFunction returns [EObject current=null] : iv_ruleJNIFunction= ruleJNIFunction EOF ;
    public final EObject entryRuleJNIFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJNIFunction = null;


        try {
            // InternalAlpha.g:1692:52: (iv_ruleJNIFunction= ruleJNIFunction EOF )
            // InternalAlpha.g:1693:2: iv_ruleJNIFunction= ruleJNIFunction EOF
            {
             newCompositeNode(grammarAccess.getJNIFunctionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJNIFunction=ruleJNIFunction();

            state._fsp--;

             current =iv_ruleJNIFunction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJNIFunction"


    // $ANTLR start "ruleJNIFunction"
    // InternalAlpha.g:1699:1: ruleJNIFunction returns [EObject current=null] : ( (lv_alphaFunction_0_0= ruleAlphaFunction ) ) ;
    public final EObject ruleJNIFunction() throws RecognitionException {
        EObject current = null;

        EObject lv_alphaFunction_0_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1705:2: ( ( (lv_alphaFunction_0_0= ruleAlphaFunction ) ) )
            // InternalAlpha.g:1706:2: ( (lv_alphaFunction_0_0= ruleAlphaFunction ) )
            {
            // InternalAlpha.g:1706:2: ( (lv_alphaFunction_0_0= ruleAlphaFunction ) )
            // InternalAlpha.g:1707:3: (lv_alphaFunction_0_0= ruleAlphaFunction )
            {
            // InternalAlpha.g:1707:3: (lv_alphaFunction_0_0= ruleAlphaFunction )
            // InternalAlpha.g:1708:4: lv_alphaFunction_0_0= ruleAlphaFunction
            {

            				newCompositeNode(grammarAccess.getJNIFunctionAccess().getAlphaFunctionAlphaFunctionParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_alphaFunction_0_0=ruleAlphaFunction();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getJNIFunctionRule());
            				}
            				set(
            					current,
            					"alphaFunction",
            					lv_alphaFunction_0_0,
            					"alpha.model.Alpha.AlphaFunction");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJNIFunction"


    // $ANTLR start "entryRuleJNIFunctionInArrayNotation"
    // InternalAlpha.g:1728:1: entryRuleJNIFunctionInArrayNotation returns [EObject current=null] : iv_ruleJNIFunctionInArrayNotation= ruleJNIFunctionInArrayNotation EOF ;
    public final EObject entryRuleJNIFunctionInArrayNotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJNIFunctionInArrayNotation = null;


        try {
            // InternalAlpha.g:1728:67: (iv_ruleJNIFunctionInArrayNotation= ruleJNIFunctionInArrayNotation EOF )
            // InternalAlpha.g:1729:2: iv_ruleJNIFunctionInArrayNotation= ruleJNIFunctionInArrayNotation EOF
            {
             newCompositeNode(grammarAccess.getJNIFunctionInArrayNotationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJNIFunctionInArrayNotation=ruleJNIFunctionInArrayNotation();

            state._fsp--;

             current =iv_ruleJNIFunctionInArrayNotation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJNIFunctionInArrayNotation"


    // $ANTLR start "ruleJNIFunctionInArrayNotation"
    // InternalAlpha.g:1735:1: ruleJNIFunctionInArrayNotation returns [EObject current=null] : (otherlv_0= '[' () ( ( (lv_arrayNotation_2_0= ruleAISLExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLExpression ) ) )* )? otherlv_5= ']' ) ;
    public final EObject ruleJNIFunctionInArrayNotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_arrayNotation_2_0 = null;

        AntlrDatatypeRuleToken lv_arrayNotation_4_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1741:2: ( (otherlv_0= '[' () ( ( (lv_arrayNotation_2_0= ruleAISLExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLExpression ) ) )* )? otherlv_5= ']' ) )
            // InternalAlpha.g:1742:2: (otherlv_0= '[' () ( ( (lv_arrayNotation_2_0= ruleAISLExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLExpression ) ) )* )? otherlv_5= ']' )
            {
            // InternalAlpha.g:1742:2: (otherlv_0= '[' () ( ( (lv_arrayNotation_2_0= ruleAISLExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLExpression ) ) )* )? otherlv_5= ']' )
            // InternalAlpha.g:1743:3: otherlv_0= '[' () ( ( (lv_arrayNotation_2_0= ruleAISLExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLExpression ) ) )* )? otherlv_5= ']'
            {
            otherlv_0=(Token)match(input,37,FOLLOW_31); 

            			newLeafNode(otherlv_0, grammarAccess.getJNIFunctionInArrayNotationAccess().getLeftSquareBracketKeyword_0());
            		
            // InternalAlpha.g:1747:3: ()
            // InternalAlpha.g:1748:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getJNIFunctionInArrayNotationAccess().getJNIFunctionInArrayNotationAction_1(),
            					current);
            			

            }

            // InternalAlpha.g:1754:3: ( ( (lv_arrayNotation_2_0= ruleAISLExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLExpression ) ) )* )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=RULE_ID && LA27_0<=RULE_WS)||LA27_0==13||(LA27_0>=15 && LA27_0<=16)||(LA27_0>=43 && LA27_0<=48)) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalAlpha.g:1755:4: ( (lv_arrayNotation_2_0= ruleAISLExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLExpression ) ) )*
                    {
                    // InternalAlpha.g:1755:4: ( (lv_arrayNotation_2_0= ruleAISLExpression ) )
                    // InternalAlpha.g:1756:5: (lv_arrayNotation_2_0= ruleAISLExpression )
                    {
                    // InternalAlpha.g:1756:5: (lv_arrayNotation_2_0= ruleAISLExpression )
                    // InternalAlpha.g:1757:6: lv_arrayNotation_2_0= ruleAISLExpression
                    {

                    						newCompositeNode(grammarAccess.getJNIFunctionInArrayNotationAccess().getArrayNotationAISLExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_32);
                    lv_arrayNotation_2_0=ruleAISLExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getJNIFunctionInArrayNotationRule());
                    						}
                    						add(
                    							current,
                    							"arrayNotation",
                    							lv_arrayNotation_2_0,
                    							"alpha.model.Alpha.AISLExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:1774:4: (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLExpression ) ) )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( (LA26_0==24) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // InternalAlpha.g:1775:5: otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,24,FOLLOW_33); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getJNIFunctionInArrayNotationAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalAlpha.g:1779:5: ( (lv_arrayNotation_4_0= ruleAISLExpression ) )
                    	    // InternalAlpha.g:1780:6: (lv_arrayNotation_4_0= ruleAISLExpression )
                    	    {
                    	    // InternalAlpha.g:1780:6: (lv_arrayNotation_4_0= ruleAISLExpression )
                    	    // InternalAlpha.g:1781:7: lv_arrayNotation_4_0= ruleAISLExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getJNIFunctionInArrayNotationAccess().getArrayNotationAISLExpressionParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_32);
                    	    lv_arrayNotation_4_0=ruleAISLExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getJNIFunctionInArrayNotationRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"arrayNotation",
                    	    								lv_arrayNotation_4_0,
                    	    								"alpha.model.Alpha.AISLExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop26;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,38,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getJNIFunctionInArrayNotationAccess().getRightSquareBracketKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJNIFunctionInArrayNotation"


    // $ANTLR start "entryRuleJNIPolynomial"
    // InternalAlpha.g:1808:1: entryRuleJNIPolynomial returns [EObject current=null] : iv_ruleJNIPolynomial= ruleJNIPolynomial EOF ;
    public final EObject entryRuleJNIPolynomial() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJNIPolynomial = null;


        try {
            // InternalAlpha.g:1808:54: (iv_ruleJNIPolynomial= ruleJNIPolynomial EOF )
            // InternalAlpha.g:1809:2: iv_ruleJNIPolynomial= ruleJNIPolynomial EOF
            {
             newCompositeNode(grammarAccess.getJNIPolynomialRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJNIPolynomial=ruleJNIPolynomial();

            state._fsp--;

             current =iv_ruleJNIPolynomial; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJNIPolynomial"


    // $ANTLR start "ruleJNIPolynomial"
    // InternalAlpha.g:1815:1: ruleJNIPolynomial returns [EObject current=null] : ( (lv_islString_0_0= ruleAISLPWQPolynomial ) ) ;
    public final EObject ruleJNIPolynomial() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_islString_0_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1821:2: ( ( (lv_islString_0_0= ruleAISLPWQPolynomial ) ) )
            // InternalAlpha.g:1822:2: ( (lv_islString_0_0= ruleAISLPWQPolynomial ) )
            {
            // InternalAlpha.g:1822:2: ( (lv_islString_0_0= ruleAISLPWQPolynomial ) )
            // InternalAlpha.g:1823:3: (lv_islString_0_0= ruleAISLPWQPolynomial )
            {
            // InternalAlpha.g:1823:3: (lv_islString_0_0= ruleAISLPWQPolynomial )
            // InternalAlpha.g:1824:4: lv_islString_0_0= ruleAISLPWQPolynomial
            {

            				newCompositeNode(grammarAccess.getJNIPolynomialAccess().getIslStringAISLPWQPolynomialParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_islString_0_0=ruleAISLPWQPolynomial();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getJNIPolynomialRule());
            				}
            				set(
            					current,
            					"islString",
            					lv_islString_0_0,
            					"alpha.model.Alpha.AISLPWQPolynomial");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJNIPolynomial"


    // $ANTLR start "entryRuleJNIPolynomialInArrayNotation"
    // InternalAlpha.g:1844:1: entryRuleJNIPolynomialInArrayNotation returns [EObject current=null] : iv_ruleJNIPolynomialInArrayNotation= ruleJNIPolynomialInArrayNotation EOF ;
    public final EObject entryRuleJNIPolynomialInArrayNotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJNIPolynomialInArrayNotation = null;


        try {
            // InternalAlpha.g:1844:69: (iv_ruleJNIPolynomialInArrayNotation= ruleJNIPolynomialInArrayNotation EOF )
            // InternalAlpha.g:1845:2: iv_ruleJNIPolynomialInArrayNotation= ruleJNIPolynomialInArrayNotation EOF
            {
             newCompositeNode(grammarAccess.getJNIPolynomialInArrayNotationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJNIPolynomialInArrayNotation=ruleJNIPolynomialInArrayNotation();

            state._fsp--;

             current =iv_ruleJNIPolynomialInArrayNotation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleJNIPolynomialInArrayNotation"


    // $ANTLR start "ruleJNIPolynomialInArrayNotation"
    // InternalAlpha.g:1851:1: ruleJNIPolynomialInArrayNotation returns [EObject current=null] : (otherlv_0= '{' ( (lv_arrayNotation_1_0= ruleAISLQPolynomialBody ) ) (otherlv_2= ';' ( (lv_arrayNotation_3_0= ruleAISLQPolynomialBody ) ) )* otherlv_4= '}' ) ;
    public final EObject ruleJNIPolynomialInArrayNotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_arrayNotation_1_0 = null;

        AntlrDatatypeRuleToken lv_arrayNotation_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1857:2: ( (otherlv_0= '{' ( (lv_arrayNotation_1_0= ruleAISLQPolynomialBody ) ) (otherlv_2= ';' ( (lv_arrayNotation_3_0= ruleAISLQPolynomialBody ) ) )* otherlv_4= '}' ) )
            // InternalAlpha.g:1858:2: (otherlv_0= '{' ( (lv_arrayNotation_1_0= ruleAISLQPolynomialBody ) ) (otherlv_2= ';' ( (lv_arrayNotation_3_0= ruleAISLQPolynomialBody ) ) )* otherlv_4= '}' )
            {
            // InternalAlpha.g:1858:2: (otherlv_0= '{' ( (lv_arrayNotation_1_0= ruleAISLQPolynomialBody ) ) (otherlv_2= ';' ( (lv_arrayNotation_3_0= ruleAISLQPolynomialBody ) ) )* otherlv_4= '}' )
            // InternalAlpha.g:1859:3: otherlv_0= '{' ( (lv_arrayNotation_1_0= ruleAISLQPolynomialBody ) ) (otherlv_2= ';' ( (lv_arrayNotation_3_0= ruleAISLQPolynomialBody ) ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_34); 

            			newLeafNode(otherlv_0, grammarAccess.getJNIPolynomialInArrayNotationAccess().getLeftCurlyBracketKeyword_0());
            		
            // InternalAlpha.g:1863:3: ( (lv_arrayNotation_1_0= ruleAISLQPolynomialBody ) )
            // InternalAlpha.g:1864:4: (lv_arrayNotation_1_0= ruleAISLQPolynomialBody )
            {
            // InternalAlpha.g:1864:4: (lv_arrayNotation_1_0= ruleAISLQPolynomialBody )
            // InternalAlpha.g:1865:5: lv_arrayNotation_1_0= ruleAISLQPolynomialBody
            {

            					newCompositeNode(grammarAccess.getJNIPolynomialInArrayNotationAccess().getArrayNotationAISLQPolynomialBodyParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_35);
            lv_arrayNotation_1_0=ruleAISLQPolynomialBody();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getJNIPolynomialInArrayNotationRule());
            					}
            					add(
            						current,
            						"arrayNotation",
            						lv_arrayNotation_1_0,
            						"alpha.model.Alpha.AISLQPolynomialBody");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:1882:3: (otherlv_2= ';' ( (lv_arrayNotation_3_0= ruleAISLQPolynomialBody ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==34) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // InternalAlpha.g:1883:4: otherlv_2= ';' ( (lv_arrayNotation_3_0= ruleAISLQPolynomialBody ) )
            	    {
            	    otherlv_2=(Token)match(input,34,FOLLOW_34); 

            	    				newLeafNode(otherlv_2, grammarAccess.getJNIPolynomialInArrayNotationAccess().getSemicolonKeyword_2_0());
            	    			
            	    // InternalAlpha.g:1887:4: ( (lv_arrayNotation_3_0= ruleAISLQPolynomialBody ) )
            	    // InternalAlpha.g:1888:5: (lv_arrayNotation_3_0= ruleAISLQPolynomialBody )
            	    {
            	    // InternalAlpha.g:1888:5: (lv_arrayNotation_3_0= ruleAISLQPolynomialBody )
            	    // InternalAlpha.g:1889:6: lv_arrayNotation_3_0= ruleAISLQPolynomialBody
            	    {

            	    						newCompositeNode(grammarAccess.getJNIPolynomialInArrayNotationAccess().getArrayNotationAISLQPolynomialBodyParserRuleCall_2_1_0());
            	    					
            	    pushFollow(FOLLOW_35);
            	    lv_arrayNotation_3_0=ruleAISLQPolynomialBody();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getJNIPolynomialInArrayNotationRule());
            	    						}
            	    						add(
            	    							current,
            	    							"arrayNotation",
            	    							lv_arrayNotation_3_0,
            	    							"alpha.model.Alpha.AISLQPolynomialBody");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            otherlv_4=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getJNIPolynomialInArrayNotationAccess().getRightCurlyBracketKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleJNIPolynomialInArrayNotation"


    // $ANTLR start "entryRuleFuzzyFunction"
    // InternalAlpha.g:1915:1: entryRuleFuzzyFunction returns [EObject current=null] : iv_ruleFuzzyFunction= ruleFuzzyFunction EOF ;
    public final EObject entryRuleFuzzyFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFuzzyFunction = null;


        try {
            // InternalAlpha.g:1915:54: (iv_ruleFuzzyFunction= ruleFuzzyFunction EOF )
            // InternalAlpha.g:1916:2: iv_ruleFuzzyFunction= ruleFuzzyFunction EOF
            {
             newCompositeNode(grammarAccess.getFuzzyFunctionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFuzzyFunction=ruleFuzzyFunction();

            state._fsp--;

             current =iv_ruleFuzzyFunction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFuzzyFunction"


    // $ANTLR start "ruleFuzzyFunction"
    // InternalAlpha.g:1922:1: ruleFuzzyFunction returns [EObject current=null] : (otherlv_0= '(' ( (lv_alphaString_1_0= ruleAISLWrappedBasicRelation ) ) (otherlv_2= ';' ( (lv_indirections_3_0= ruleFuzzyVariableUse ) ) )* otherlv_4= ')' ) ;
    public final EObject ruleFuzzyFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_alphaString_1_0 = null;

        EObject lv_indirections_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1928:2: ( (otherlv_0= '(' ( (lv_alphaString_1_0= ruleAISLWrappedBasicRelation ) ) (otherlv_2= ';' ( (lv_indirections_3_0= ruleFuzzyVariableUse ) ) )* otherlv_4= ')' ) )
            // InternalAlpha.g:1929:2: (otherlv_0= '(' ( (lv_alphaString_1_0= ruleAISLWrappedBasicRelation ) ) (otherlv_2= ';' ( (lv_indirections_3_0= ruleFuzzyVariableUse ) ) )* otherlv_4= ')' )
            {
            // InternalAlpha.g:1929:2: (otherlv_0= '(' ( (lv_alphaString_1_0= ruleAISLWrappedBasicRelation ) ) (otherlv_2= ';' ( (lv_indirections_3_0= ruleFuzzyVariableUse ) ) )* otherlv_4= ')' )
            // InternalAlpha.g:1930:3: otherlv_0= '(' ( (lv_alphaString_1_0= ruleAISLWrappedBasicRelation ) ) (otherlv_2= ';' ( (lv_indirections_3_0= ruleFuzzyVariableUse ) ) )* otherlv_4= ')'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_36); 

            			newLeafNode(otherlv_0, grammarAccess.getFuzzyFunctionAccess().getLeftParenthesisKeyword_0());
            		
            // InternalAlpha.g:1934:3: ( (lv_alphaString_1_0= ruleAISLWrappedBasicRelation ) )
            // InternalAlpha.g:1935:4: (lv_alphaString_1_0= ruleAISLWrappedBasicRelation )
            {
            // InternalAlpha.g:1935:4: (lv_alphaString_1_0= ruleAISLWrappedBasicRelation )
            // InternalAlpha.g:1936:5: lv_alphaString_1_0= ruleAISLWrappedBasicRelation
            {

            					newCompositeNode(grammarAccess.getFuzzyFunctionAccess().getAlphaStringAISLWrappedBasicRelationParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_37);
            lv_alphaString_1_0=ruleAISLWrappedBasicRelation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFuzzyFunctionRule());
            					}
            					set(
            						current,
            						"alphaString",
            						lv_alphaString_1_0,
            						"alpha.model.Alpha.AISLWrappedBasicRelation");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:1953:3: (otherlv_2= ';' ( (lv_indirections_3_0= ruleFuzzyVariableUse ) ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==34) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalAlpha.g:1954:4: otherlv_2= ';' ( (lv_indirections_3_0= ruleFuzzyVariableUse ) )
            	    {
            	    otherlv_2=(Token)match(input,34,FOLLOW_5); 

            	    				newLeafNode(otherlv_2, grammarAccess.getFuzzyFunctionAccess().getSemicolonKeyword_2_0());
            	    			
            	    // InternalAlpha.g:1958:4: ( (lv_indirections_3_0= ruleFuzzyVariableUse ) )
            	    // InternalAlpha.g:1959:5: (lv_indirections_3_0= ruleFuzzyVariableUse )
            	    {
            	    // InternalAlpha.g:1959:5: (lv_indirections_3_0= ruleFuzzyVariableUse )
            	    // InternalAlpha.g:1960:6: lv_indirections_3_0= ruleFuzzyVariableUse
            	    {

            	    						newCompositeNode(grammarAccess.getFuzzyFunctionAccess().getIndirectionsFuzzyVariableUseParserRuleCall_2_1_0());
            	    					
            	    pushFollow(FOLLOW_37);
            	    lv_indirections_3_0=ruleFuzzyVariableUse();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getFuzzyFunctionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"indirections",
            	    							lv_indirections_3_0,
            	    							"alpha.model.Alpha.FuzzyVariableUse");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);

            otherlv_4=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getFuzzyFunctionAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFuzzyFunction"


    // $ANTLR start "entryRuleFuzzyVariableUse"
    // InternalAlpha.g:1986:1: entryRuleFuzzyVariableUse returns [EObject current=null] : iv_ruleFuzzyVariableUse= ruleFuzzyVariableUse EOF ;
    public final EObject entryRuleFuzzyVariableUse() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFuzzyVariableUse = null;


        try {
            // InternalAlpha.g:1986:57: (iv_ruleFuzzyVariableUse= ruleFuzzyVariableUse EOF )
            // InternalAlpha.g:1987:2: iv_ruleFuzzyVariableUse= ruleFuzzyVariableUse EOF
            {
             newCompositeNode(grammarAccess.getFuzzyVariableUseRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFuzzyVariableUse=ruleFuzzyVariableUse();

            state._fsp--;

             current =iv_ruleFuzzyVariableUse; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFuzzyVariableUse"


    // $ANTLR start "ruleFuzzyVariableUse"
    // InternalAlpha.g:1993:1: ruleFuzzyVariableUse returns [EObject current=null] : (this_NestedFuzzyFunction_0= ruleNestedFuzzyFunction | this_AffineFuzzyVariableUse_1= ruleAffineFuzzyVariableUse ) ;
    public final EObject ruleFuzzyVariableUse() throws RecognitionException {
        EObject current = null;

        EObject this_NestedFuzzyFunction_0 = null;

        EObject this_AffineFuzzyVariableUse_1 = null;



        	enterRule();

        try {
            // InternalAlpha.g:1999:2: ( (this_NestedFuzzyFunction_0= ruleNestedFuzzyFunction | this_AffineFuzzyVariableUse_1= ruleAffineFuzzyVariableUse ) )
            // InternalAlpha.g:2000:2: (this_NestedFuzzyFunction_0= ruleNestedFuzzyFunction | this_AffineFuzzyVariableUse_1= ruleAffineFuzzyVariableUse )
            {
            // InternalAlpha.g:2000:2: (this_NestedFuzzyFunction_0= ruleNestedFuzzyFunction | this_AffineFuzzyVariableUse_1= ruleAffineFuzzyVariableUse )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RULE_ID) ) {
                int LA30_1 = input.LA(2);

                if ( (LA30_1==13) ) {
                    int LA30_2 = input.LA(3);

                    if ( (LA30_2==RULE_ID) ) {
                        int LA30_3 = input.LA(4);

                        if ( (LA30_3==19) ) {
                            alt30=1;
                        }
                        else if ( (LA30_3==37) ) {
                            alt30=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // InternalAlpha.g:2001:3: this_NestedFuzzyFunction_0= ruleNestedFuzzyFunction
                    {

                    			newCompositeNode(grammarAccess.getFuzzyVariableUseAccess().getNestedFuzzyFunctionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_NestedFuzzyFunction_0=ruleNestedFuzzyFunction();

                    state._fsp--;


                    			current = this_NestedFuzzyFunction_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:2010:3: this_AffineFuzzyVariableUse_1= ruleAffineFuzzyVariableUse
                    {

                    			newCompositeNode(grammarAccess.getFuzzyVariableUseAccess().getAffineFuzzyVariableUseParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_AffineFuzzyVariableUse_1=ruleAffineFuzzyVariableUse();

                    state._fsp--;


                    			current = this_AffineFuzzyVariableUse_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFuzzyVariableUse"


    // $ANTLR start "entryRuleNestedFuzzyFunction"
    // InternalAlpha.g:2022:1: entryRuleNestedFuzzyFunction returns [EObject current=null] : iv_ruleNestedFuzzyFunction= ruleNestedFuzzyFunction EOF ;
    public final EObject entryRuleNestedFuzzyFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNestedFuzzyFunction = null;


        try {
            // InternalAlpha.g:2022:60: (iv_ruleNestedFuzzyFunction= ruleNestedFuzzyFunction EOF )
            // InternalAlpha.g:2023:2: iv_ruleNestedFuzzyFunction= ruleNestedFuzzyFunction EOF
            {
             newCompositeNode(grammarAccess.getNestedFuzzyFunctionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNestedFuzzyFunction=ruleNestedFuzzyFunction();

            state._fsp--;

             current =iv_ruleNestedFuzzyFunction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNestedFuzzyFunction"


    // $ANTLR start "ruleNestedFuzzyFunction"
    // InternalAlpha.g:2029:1: ruleNestedFuzzyFunction returns [EObject current=null] : ( ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' ( (lv_alphaString_4_0= ruleAISLWrappedBasicRelation ) ) (otherlv_5= ';' ( (lv_indirections_6_0= ruleFuzzyVariableUse ) ) )* otherlv_7= '}' ) ;
    public final EObject ruleNestedFuzzyFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_fuzzyIndex_0_0 = null;

        AntlrDatatypeRuleToken lv_alphaString_4_0 = null;

        EObject lv_indirections_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2035:2: ( ( ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' ( (lv_alphaString_4_0= ruleAISLWrappedBasicRelation ) ) (otherlv_5= ';' ( (lv_indirections_6_0= ruleFuzzyVariableUse ) ) )* otherlv_7= '}' ) )
            // InternalAlpha.g:2036:2: ( ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' ( (lv_alphaString_4_0= ruleAISLWrappedBasicRelation ) ) (otherlv_5= ';' ( (lv_indirections_6_0= ruleFuzzyVariableUse ) ) )* otherlv_7= '}' )
            {
            // InternalAlpha.g:2036:2: ( ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' ( (lv_alphaString_4_0= ruleAISLWrappedBasicRelation ) ) (otherlv_5= ';' ( (lv_indirections_6_0= ruleFuzzyVariableUse ) ) )* otherlv_7= '}' )
            // InternalAlpha.g:2037:3: ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) otherlv_3= '{' ( (lv_alphaString_4_0= ruleAISLWrappedBasicRelation ) ) (otherlv_5= ';' ( (lv_indirections_6_0= ruleFuzzyVariableUse ) ) )* otherlv_7= '}'
            {
            // InternalAlpha.g:2037:3: ( (lv_fuzzyIndex_0_0= ruleIndexName ) )
            // InternalAlpha.g:2038:4: (lv_fuzzyIndex_0_0= ruleIndexName )
            {
            // InternalAlpha.g:2038:4: (lv_fuzzyIndex_0_0= ruleIndexName )
            // InternalAlpha.g:2039:5: lv_fuzzyIndex_0_0= ruleIndexName
            {

            					newCompositeNode(grammarAccess.getNestedFuzzyFunctionAccess().getFuzzyIndexIndexNameParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_6);
            lv_fuzzyIndex_0_0=ruleIndexName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getNestedFuzzyFunctionRule());
            					}
            					set(
            						current,
            						"fuzzyIndex",
            						lv_fuzzyIndex_0_0,
            						"alpha.model.Alpha.IndexName");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getNestedFuzzyFunctionAccess().getEqualsSignKeyword_1());
            		
            // InternalAlpha.g:2060:3: ( (otherlv_2= RULE_ID ) )
            // InternalAlpha.g:2061:4: (otherlv_2= RULE_ID )
            {
            // InternalAlpha.g:2061:4: (otherlv_2= RULE_ID )
            // InternalAlpha.g:2062:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNestedFuzzyFunctionRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_10); 

            					newLeafNode(otherlv_2, grammarAccess.getNestedFuzzyFunctionAccess().getFuzzyVariableFuzzyVariableCrossReference_2_0());
            				

            }


            }

            otherlv_3=(Token)match(input,19,FOLLOW_36); 

            			newLeafNode(otherlv_3, grammarAccess.getNestedFuzzyFunctionAccess().getLeftCurlyBracketKeyword_3());
            		
            // InternalAlpha.g:2077:3: ( (lv_alphaString_4_0= ruleAISLWrappedBasicRelation ) )
            // InternalAlpha.g:2078:4: (lv_alphaString_4_0= ruleAISLWrappedBasicRelation )
            {
            // InternalAlpha.g:2078:4: (lv_alphaString_4_0= ruleAISLWrappedBasicRelation )
            // InternalAlpha.g:2079:5: lv_alphaString_4_0= ruleAISLWrappedBasicRelation
            {

            					newCompositeNode(grammarAccess.getNestedFuzzyFunctionAccess().getAlphaStringAISLWrappedBasicRelationParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_35);
            lv_alphaString_4_0=ruleAISLWrappedBasicRelation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getNestedFuzzyFunctionRule());
            					}
            					set(
            						current,
            						"alphaString",
            						lv_alphaString_4_0,
            						"alpha.model.Alpha.AISLWrappedBasicRelation");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:2096:3: (otherlv_5= ';' ( (lv_indirections_6_0= ruleFuzzyVariableUse ) ) )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==34) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalAlpha.g:2097:4: otherlv_5= ';' ( (lv_indirections_6_0= ruleFuzzyVariableUse ) )
            	    {
            	    otherlv_5=(Token)match(input,34,FOLLOW_5); 

            	    				newLeafNode(otherlv_5, grammarAccess.getNestedFuzzyFunctionAccess().getSemicolonKeyword_5_0());
            	    			
            	    // InternalAlpha.g:2101:4: ( (lv_indirections_6_0= ruleFuzzyVariableUse ) )
            	    // InternalAlpha.g:2102:5: (lv_indirections_6_0= ruleFuzzyVariableUse )
            	    {
            	    // InternalAlpha.g:2102:5: (lv_indirections_6_0= ruleFuzzyVariableUse )
            	    // InternalAlpha.g:2103:6: lv_indirections_6_0= ruleFuzzyVariableUse
            	    {

            	    						newCompositeNode(grammarAccess.getNestedFuzzyFunctionAccess().getIndirectionsFuzzyVariableUseParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_35);
            	    lv_indirections_6_0=ruleFuzzyVariableUse();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getNestedFuzzyFunctionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"indirections",
            	    							lv_indirections_6_0,
            	    							"alpha.model.Alpha.FuzzyVariableUse");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            otherlv_7=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getNestedFuzzyFunctionAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNestedFuzzyFunction"


    // $ANTLR start "entryRuleAffineFuzzyVariableUse"
    // InternalAlpha.g:2129:1: entryRuleAffineFuzzyVariableUse returns [EObject current=null] : iv_ruleAffineFuzzyVariableUse= ruleAffineFuzzyVariableUse EOF ;
    public final EObject entryRuleAffineFuzzyVariableUse() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAffineFuzzyVariableUse = null;


        try {
            // InternalAlpha.g:2129:63: (iv_ruleAffineFuzzyVariableUse= ruleAffineFuzzyVariableUse EOF )
            // InternalAlpha.g:2130:2: iv_ruleAffineFuzzyVariableUse= ruleAffineFuzzyVariableUse EOF
            {
             newCompositeNode(grammarAccess.getAffineFuzzyVariableUseRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAffineFuzzyVariableUse=ruleAffineFuzzyVariableUse();

            state._fsp--;

             current =iv_ruleAffineFuzzyVariableUse; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAffineFuzzyVariableUse"


    // $ANTLR start "ruleAffineFuzzyVariableUse"
    // InternalAlpha.g:2136:1: ruleAffineFuzzyVariableUse returns [EObject current=null] : ( ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) ( (lv_useFunction_3_0= ruleJNIFunctionInArrayNotation ) ) ) ;
    public final EObject ruleAffineFuzzyVariableUse() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_fuzzyIndex_0_0 = null;

        EObject lv_useFunction_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2142:2: ( ( ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) ( (lv_useFunction_3_0= ruleJNIFunctionInArrayNotation ) ) ) )
            // InternalAlpha.g:2143:2: ( ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) ( (lv_useFunction_3_0= ruleJNIFunctionInArrayNotation ) ) )
            {
            // InternalAlpha.g:2143:2: ( ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) ( (lv_useFunction_3_0= ruleJNIFunctionInArrayNotation ) ) )
            // InternalAlpha.g:2144:3: ( (lv_fuzzyIndex_0_0= ruleIndexName ) ) otherlv_1= '=' ( (otherlv_2= RULE_ID ) ) ( (lv_useFunction_3_0= ruleJNIFunctionInArrayNotation ) )
            {
            // InternalAlpha.g:2144:3: ( (lv_fuzzyIndex_0_0= ruleIndexName ) )
            // InternalAlpha.g:2145:4: (lv_fuzzyIndex_0_0= ruleIndexName )
            {
            // InternalAlpha.g:2145:4: (lv_fuzzyIndex_0_0= ruleIndexName )
            // InternalAlpha.g:2146:5: lv_fuzzyIndex_0_0= ruleIndexName
            {

            					newCompositeNode(grammarAccess.getAffineFuzzyVariableUseAccess().getFuzzyIndexIndexNameParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_6);
            lv_fuzzyIndex_0_0=ruleIndexName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAffineFuzzyVariableUseRule());
            					}
            					set(
            						current,
            						"fuzzyIndex",
            						lv_fuzzyIndex_0_0,
            						"alpha.model.Alpha.IndexName");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getAffineFuzzyVariableUseAccess().getEqualsSignKeyword_1());
            		
            // InternalAlpha.g:2167:3: ( (otherlv_2= RULE_ID ) )
            // InternalAlpha.g:2168:4: (otherlv_2= RULE_ID )
            {
            // InternalAlpha.g:2168:4: (otherlv_2= RULE_ID )
            // InternalAlpha.g:2169:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getAffineFuzzyVariableUseRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_36); 

            					newLeafNode(otherlv_2, grammarAccess.getAffineFuzzyVariableUseAccess().getFuzzyVariableFuzzyVariableCrossReference_2_0());
            				

            }


            }

            // InternalAlpha.g:2180:3: ( (lv_useFunction_3_0= ruleJNIFunctionInArrayNotation ) )
            // InternalAlpha.g:2181:4: (lv_useFunction_3_0= ruleJNIFunctionInArrayNotation )
            {
            // InternalAlpha.g:2181:4: (lv_useFunction_3_0= ruleJNIFunctionInArrayNotation )
            // InternalAlpha.g:2182:5: lv_useFunction_3_0= ruleJNIFunctionInArrayNotation
            {

            					newCompositeNode(grammarAccess.getAffineFuzzyVariableUseAccess().getUseFunctionJNIFunctionInArrayNotationParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_useFunction_3_0=ruleJNIFunctionInArrayNotation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAffineFuzzyVariableUseRule());
            					}
            					set(
            						current,
            						"useFunction",
            						lv_useFunction_3_0,
            						"alpha.model.Alpha.JNIFunctionInArrayNotation");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAffineFuzzyVariableUse"


    // $ANTLR start "entryRuleFuzzyFunctionInArrayNotation"
    // InternalAlpha.g:2203:1: entryRuleFuzzyFunctionInArrayNotation returns [EObject current=null] : iv_ruleFuzzyFunctionInArrayNotation= ruleFuzzyFunctionInArrayNotation EOF ;
    public final EObject entryRuleFuzzyFunctionInArrayNotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFuzzyFunctionInArrayNotation = null;


        try {
            // InternalAlpha.g:2203:69: (iv_ruleFuzzyFunctionInArrayNotation= ruleFuzzyFunctionInArrayNotation EOF )
            // InternalAlpha.g:2204:2: iv_ruleFuzzyFunctionInArrayNotation= ruleFuzzyFunctionInArrayNotation EOF
            {
             newCompositeNode(grammarAccess.getFuzzyFunctionInArrayNotationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFuzzyFunctionInArrayNotation=ruleFuzzyFunctionInArrayNotation();

            state._fsp--;

             current =iv_ruleFuzzyFunctionInArrayNotation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFuzzyFunctionInArrayNotation"


    // $ANTLR start "ruleFuzzyFunctionInArrayNotation"
    // InternalAlpha.g:2210:1: ruleFuzzyFunctionInArrayNotation returns [EObject current=null] : (otherlv_0= '[[' () ( ( (lv_arrayNotation_2_0= ruleAISLFuzzyExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) ) )* )? otherlv_5= ']]' ) ;
    public final EObject ruleFuzzyFunctionInArrayNotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_arrayNotation_2_0 = null;

        AntlrDatatypeRuleToken lv_arrayNotation_4_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2216:2: ( (otherlv_0= '[[' () ( ( (lv_arrayNotation_2_0= ruleAISLFuzzyExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) ) )* )? otherlv_5= ']]' ) )
            // InternalAlpha.g:2217:2: (otherlv_0= '[[' () ( ( (lv_arrayNotation_2_0= ruleAISLFuzzyExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) ) )* )? otherlv_5= ']]' )
            {
            // InternalAlpha.g:2217:2: (otherlv_0= '[[' () ( ( (lv_arrayNotation_2_0= ruleAISLFuzzyExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) ) )* )? otherlv_5= ']]' )
            // InternalAlpha.g:2218:3: otherlv_0= '[[' () ( ( (lv_arrayNotation_2_0= ruleAISLFuzzyExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) ) )* )? otherlv_5= ']]'
            {
            otherlv_0=(Token)match(input,39,FOLLOW_38); 

            			newLeafNode(otherlv_0, grammarAccess.getFuzzyFunctionInArrayNotationAccess().getLeftSquareBracketLeftSquareBracketKeyword_0());
            		
            // InternalAlpha.g:2222:3: ()
            // InternalAlpha.g:2223:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getFuzzyFunctionInArrayNotationAccess().getFuzzyFunctionInArrayNotationAction_1(),
            					current);
            			

            }

            // InternalAlpha.g:2229:3: ( ( (lv_arrayNotation_2_0= ruleAISLFuzzyExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) ) )* )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( ((LA33_0>=RULE_ID && LA33_0<=RULE_WS)||LA33_0==13||(LA33_0>=37 && LA33_0<=38)||(LA33_0>=43 && LA33_0<=47)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalAlpha.g:2230:4: ( (lv_arrayNotation_2_0= ruleAISLFuzzyExpression ) ) (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) ) )*
                    {
                    // InternalAlpha.g:2230:4: ( (lv_arrayNotation_2_0= ruleAISLFuzzyExpression ) )
                    // InternalAlpha.g:2231:5: (lv_arrayNotation_2_0= ruleAISLFuzzyExpression )
                    {
                    // InternalAlpha.g:2231:5: (lv_arrayNotation_2_0= ruleAISLFuzzyExpression )
                    // InternalAlpha.g:2232:6: lv_arrayNotation_2_0= ruleAISLFuzzyExpression
                    {

                    						newCompositeNode(grammarAccess.getFuzzyFunctionInArrayNotationAccess().getArrayNotationAISLFuzzyExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_39);
                    lv_arrayNotation_2_0=ruleAISLFuzzyExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFuzzyFunctionInArrayNotationRule());
                    						}
                    						add(
                    							current,
                    							"arrayNotation",
                    							lv_arrayNotation_2_0,
                    							"alpha.model.Alpha.AISLFuzzyExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:2249:4: (otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) ) )*
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( (LA32_0==24) ) {
                            alt32=1;
                        }


                        switch (alt32) {
                    	case 1 :
                    	    // InternalAlpha.g:2250:5: otherlv_3= ',' ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) )
                    	    {
                    	    otherlv_3=(Token)match(input,24,FOLLOW_40); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getFuzzyFunctionInArrayNotationAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalAlpha.g:2254:5: ( (lv_arrayNotation_4_0= ruleAISLFuzzyExpression ) )
                    	    // InternalAlpha.g:2255:6: (lv_arrayNotation_4_0= ruleAISLFuzzyExpression )
                    	    {
                    	    // InternalAlpha.g:2255:6: (lv_arrayNotation_4_0= ruleAISLFuzzyExpression )
                    	    // InternalAlpha.g:2256:7: lv_arrayNotation_4_0= ruleAISLFuzzyExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getFuzzyFunctionInArrayNotationAccess().getArrayNotationAISLFuzzyExpressionParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_39);
                    	    lv_arrayNotation_4_0=ruleAISLFuzzyExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getFuzzyFunctionInArrayNotationRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"arrayNotation",
                    	    								lv_arrayNotation_4_0,
                    	    								"alpha.model.Alpha.AISLFuzzyExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop32;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_5=(Token)match(input,40,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getFuzzyFunctionInArrayNotationAccess().getRightSquareBracketRightSquareBracketKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFuzzyFunctionInArrayNotation"


    // $ANTLR start "entryRuleQualifiedName"
    // InternalAlpha.g:2283:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalAlpha.g:2283:53: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalAlpha.g:2284:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;

             current =iv_ruleQualifiedName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // InternalAlpha.g:2290:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalAlpha.g:2296:2: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // InternalAlpha.g:2297:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // InternalAlpha.g:2297:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // InternalAlpha.g:2298:3: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_41); 

            			current.merge(this_ID_0);
            		

            			newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0());
            		
            // InternalAlpha.g:2305:3: (kw= '.' this_ID_2= RULE_ID )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==29) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // InternalAlpha.g:2306:4: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,29,FOLLOW_5); 

            	    				current.merge(kw);
            	    				newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0());
            	    			
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_41); 

            	    				current.merge(this_ID_2);
            	    			

            	    				newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1());
            	    			

            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleQualifiedNameWithWildcard"
    // InternalAlpha.g:2323:1: entryRuleQualifiedNameWithWildcard returns [String current=null] : iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF ;
    public final String entryRuleQualifiedNameWithWildcard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildcard = null;


        try {
            // InternalAlpha.g:2323:65: (iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF )
            // InternalAlpha.g:2324:2: iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameWithWildcardRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedNameWithWildcard=ruleQualifiedNameWithWildcard();

            state._fsp--;

             current =iv_ruleQualifiedNameWithWildcard.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedNameWithWildcard"


    // $ANTLR start "ruleQualifiedNameWithWildcard"
    // InternalAlpha.g:2330:1: ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildcard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2336:2: ( (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? ) )
            // InternalAlpha.g:2337:2: (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? )
            {
            // InternalAlpha.g:2337:2: (this_QualifiedName_0= ruleQualifiedName (kw= '.*' )? )
            // InternalAlpha.g:2338:3: this_QualifiedName_0= ruleQualifiedName (kw= '.*' )?
            {

            			newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_42);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;


            			current.merge(this_QualifiedName_0);
            		

            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:2348:3: (kw= '.*' )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==41) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalAlpha.g:2349:4: kw= '.*'
                    {
                    kw=(Token)match(input,41,FOLLOW_2); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopAsteriskKeyword_1());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedNameWithWildcard"


    // $ANTLR start "entryRuleSystemName"
    // InternalAlpha.g:2359:1: entryRuleSystemName returns [String current=null] : iv_ruleSystemName= ruleSystemName EOF ;
    public final String entryRuleSystemName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSystemName = null;


        try {
            // InternalAlpha.g:2359:50: (iv_ruleSystemName= ruleSystemName EOF )
            // InternalAlpha.g:2360:2: iv_ruleSystemName= ruleSystemName EOF
            {
             newCompositeNode(grammarAccess.getSystemNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSystemName=ruleSystemName();

            state._fsp--;

             current =iv_ruleSystemName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSystemName"


    // $ANTLR start "ruleSystemName"
    // InternalAlpha.g:2366:1: ruleSystemName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleSystemName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;


        	enterRule();

        try {
            // InternalAlpha.g:2372:2: (this_ID_0= RULE_ID )
            // InternalAlpha.g:2373:2: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            		current.merge(this_ID_0);
            	

            		newLeafNode(this_ID_0, grammarAccess.getSystemNameAccess().getIDTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSystemName"


    // $ANTLR start "entryRuleIndexName"
    // InternalAlpha.g:2383:1: entryRuleIndexName returns [String current=null] : iv_ruleIndexName= ruleIndexName EOF ;
    public final String entryRuleIndexName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleIndexName = null;


        try {
            // InternalAlpha.g:2383:49: (iv_ruleIndexName= ruleIndexName EOF )
            // InternalAlpha.g:2384:2: iv_ruleIndexName= ruleIndexName EOF
            {
             newCompositeNode(grammarAccess.getIndexNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIndexName=ruleIndexName();

            state._fsp--;

             current =iv_ruleIndexName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIndexName"


    // $ANTLR start "ruleIndexName"
    // InternalAlpha.g:2390:1: ruleIndexName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleIndexName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;


        	enterRule();

        try {
            // InternalAlpha.g:2396:2: (this_ID_0= RULE_ID )
            // InternalAlpha.g:2397:2: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            		current.merge(this_ID_0);
            	

            		newLeafNode(this_ID_0, grammarAccess.getIndexNameAccess().getIDTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIndexName"


    // $ANTLR start "entryRuleAIndexList"
    // InternalAlpha.g:2407:1: entryRuleAIndexList returns [String current=null] : iv_ruleAIndexList= ruleAIndexList EOF ;
    public final String entryRuleAIndexList() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAIndexList = null;


        try {
            // InternalAlpha.g:2407:50: (iv_ruleAIndexList= ruleAIndexList EOF )
            // InternalAlpha.g:2408:2: iv_ruleAIndexList= ruleAIndexList EOF
            {
             newCompositeNode(grammarAccess.getAIndexListRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAIndexList=ruleAIndexList();

            state._fsp--;

             current =iv_ruleAIndexList.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAIndexList"


    // $ANTLR start "ruleAIndexList"
    // InternalAlpha.g:2414:1: ruleAIndexList returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IndexName_0= ruleIndexName (kw= ',' this_IndexName_2= ruleIndexName )* )? ;
    public final AntlrDatatypeRuleToken ruleAIndexList() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_IndexName_0 = null;

        AntlrDatatypeRuleToken this_IndexName_2 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2420:2: ( (this_IndexName_0= ruleIndexName (kw= ',' this_IndexName_2= ruleIndexName )* )? )
            // InternalAlpha.g:2421:2: (this_IndexName_0= ruleIndexName (kw= ',' this_IndexName_2= ruleIndexName )* )?
            {
            // InternalAlpha.g:2421:2: (this_IndexName_0= ruleIndexName (kw= ',' this_IndexName_2= ruleIndexName )* )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==RULE_ID) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalAlpha.g:2422:3: this_IndexName_0= ruleIndexName (kw= ',' this_IndexName_2= ruleIndexName )*
                    {

                    			newCompositeNode(grammarAccess.getAIndexListAccess().getIndexNameParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_43);
                    this_IndexName_0=ruleIndexName();

                    state._fsp--;


                    			current.merge(this_IndexName_0);
                    		

                    			afterParserOrEnumRuleCall();
                    		
                    // InternalAlpha.g:2432:3: (kw= ',' this_IndexName_2= ruleIndexName )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==24) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // InternalAlpha.g:2433:4: kw= ',' this_IndexName_2= ruleIndexName
                    	    {
                    	    kw=(Token)match(input,24,FOLLOW_5); 

                    	    				current.merge(kw);
                    	    				newLeafNode(kw, grammarAccess.getAIndexListAccess().getCommaKeyword_1_0());
                    	    			

                    	    				newCompositeNode(grammarAccess.getAIndexListAccess().getIndexNameParserRuleCall_1_1());
                    	    			
                    	    pushFollow(FOLLOW_43);
                    	    this_IndexName_2=ruleIndexName();

                    	    state._fsp--;


                    	    				current.merge(this_IndexName_2);
                    	    			

                    	    				afterParserOrEnumRuleCall();
                    	    			

                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAIndexList"


    // $ANTLR start "entryRuleAParamDomain"
    // InternalAlpha.g:2453:1: entryRuleAParamDomain returns [String current=null] : iv_ruleAParamDomain= ruleAParamDomain EOF ;
    public final String entryRuleAParamDomain() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAParamDomain = null;


        try {
            // InternalAlpha.g:2453:52: (iv_ruleAParamDomain= ruleAParamDomain EOF )
            // InternalAlpha.g:2454:2: iv_ruleAParamDomain= ruleAParamDomain EOF
            {
             newCompositeNode(grammarAccess.getAParamDomainRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAParamDomain=ruleAParamDomain();

            state._fsp--;

             current =iv_ruleAParamDomain.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAParamDomain"


    // $ANTLR start "ruleAParamDomain"
    // InternalAlpha.g:2460:1: ruleAParamDomain returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '[' this_AIndexList_1= ruleAIndexList kw= ']' kw= '->' )? kw= '{' kw= ':' this_AISLString_6= ruleAISLString kw= '}' ) ;
    public final AntlrDatatypeRuleToken ruleAParamDomain() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AIndexList_1 = null;

        AntlrDatatypeRuleToken this_AISLString_6 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2466:2: ( ( (kw= '[' this_AIndexList_1= ruleAIndexList kw= ']' kw= '->' )? kw= '{' kw= ':' this_AISLString_6= ruleAISLString kw= '}' ) )
            // InternalAlpha.g:2467:2: ( (kw= '[' this_AIndexList_1= ruleAIndexList kw= ']' kw= '->' )? kw= '{' kw= ':' this_AISLString_6= ruleAISLString kw= '}' )
            {
            // InternalAlpha.g:2467:2: ( (kw= '[' this_AIndexList_1= ruleAIndexList kw= ']' kw= '->' )? kw= '{' kw= ':' this_AISLString_6= ruleAISLString kw= '}' )
            // InternalAlpha.g:2468:3: (kw= '[' this_AIndexList_1= ruleAIndexList kw= ']' kw= '->' )? kw= '{' kw= ':' this_AISLString_6= ruleAISLString kw= '}'
            {
            // InternalAlpha.g:2468:3: (kw= '[' this_AIndexList_1= ruleAIndexList kw= ']' kw= '->' )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==37) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalAlpha.g:2469:4: kw= '[' this_AIndexList_1= ruleAIndexList kw= ']' kw= '->'
                    {
                    kw=(Token)match(input,37,FOLLOW_44); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAParamDomainAccess().getLeftSquareBracketKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getAParamDomainAccess().getAIndexListParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_45);
                    this_AIndexList_1=ruleAIndexList();

                    state._fsp--;


                    				current.merge(this_AIndexList_1);
                    			

                    				afterParserOrEnumRuleCall();
                    			
                    kw=(Token)match(input,38,FOLLOW_28); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAParamDomainAccess().getRightSquareBracketKeyword_0_2());
                    			
                    kw=(Token)match(input,36,FOLLOW_10); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAParamDomainAccess().getHyphenMinusGreaterThanSignKeyword_0_3());
                    			

                    }
                    break;

            }

            kw=(Token)match(input,19,FOLLOW_26); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAParamDomainAccess().getLeftCurlyBracketKeyword_1());
            		
            kw=(Token)match(input,33,FOLLOW_29); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAParamDomainAccess().getColonKeyword_2());
            		

            			newCompositeNode(grammarAccess.getAParamDomainAccess().getAISLStringParserRuleCall_3());
            		
            pushFollow(FOLLOW_30);
            this_AISLString_6=ruleAISLString();

            state._fsp--;


            			current.merge(this_AISLString_6);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,20,FOLLOW_2); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAParamDomainAccess().getRightCurlyBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAParamDomain"


    // $ANTLR start "entryRuleAParamDomainInArrayNotation"
    // InternalAlpha.g:2524:1: entryRuleAParamDomainInArrayNotation returns [String current=null] : iv_ruleAParamDomainInArrayNotation= ruleAParamDomainInArrayNotation EOF ;
    public final String entryRuleAParamDomainInArrayNotation() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAParamDomainInArrayNotation = null;


        try {
            // InternalAlpha.g:2524:67: (iv_ruleAParamDomainInArrayNotation= ruleAParamDomainInArrayNotation EOF )
            // InternalAlpha.g:2525:2: iv_ruleAParamDomainInArrayNotation= ruleAParamDomainInArrayNotation EOF
            {
             newCompositeNode(grammarAccess.getAParamDomainInArrayNotationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAParamDomainInArrayNotation=ruleAParamDomainInArrayNotation();

            state._fsp--;

             current =iv_ruleAParamDomainInArrayNotation.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAParamDomainInArrayNotation"


    // $ANTLR start "ruleAParamDomainInArrayNotation"
    // InternalAlpha.g:2531:1: ruleAParamDomainInArrayNotation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '{' kw= ':' this_AISLString_2= ruleAISLString kw= '}' ) ;
    public final AntlrDatatypeRuleToken ruleAParamDomainInArrayNotation() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AISLString_2 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2537:2: ( (kw= '{' kw= ':' this_AISLString_2= ruleAISLString kw= '}' ) )
            // InternalAlpha.g:2538:2: (kw= '{' kw= ':' this_AISLString_2= ruleAISLString kw= '}' )
            {
            // InternalAlpha.g:2538:2: (kw= '{' kw= ':' this_AISLString_2= ruleAISLString kw= '}' )
            // InternalAlpha.g:2539:3: kw= '{' kw= ':' this_AISLString_2= ruleAISLString kw= '}'
            {
            kw=(Token)match(input,19,FOLLOW_26); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAParamDomainInArrayNotationAccess().getLeftCurlyBracketKeyword_0());
            		
            kw=(Token)match(input,33,FOLLOW_29); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAParamDomainInArrayNotationAccess().getColonKeyword_1());
            		

            			newCompositeNode(grammarAccess.getAParamDomainInArrayNotationAccess().getAISLStringParserRuleCall_2());
            		
            pushFollow(FOLLOW_30);
            this_AISLString_2=ruleAISLString();

            state._fsp--;


            			current.merge(this_AISLString_2);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,20,FOLLOW_2); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAParamDomainInArrayNotationAccess().getRightCurlyBracketKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAParamDomainInArrayNotation"


    // $ANTLR start "entryRuleAISLSet"
    // InternalAlpha.g:2568:1: entryRuleAISLSet returns [String current=null] : iv_ruleAISLSet= ruleAISLSet EOF ;
    public final String entryRuleAISLSet() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLSet = null;


        try {
            // InternalAlpha.g:2568:47: (iv_ruleAISLSet= ruleAISLSet EOF )
            // InternalAlpha.g:2569:2: iv_ruleAISLSet= ruleAISLSet EOF
            {
             newCompositeNode(grammarAccess.getAISLSetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLSet=ruleAISLSet();

            state._fsp--;

             current =iv_ruleAISLSet.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLSet"


    // $ANTLR start "ruleAISLSet"
    // InternalAlpha.g:2575:1: ruleAISLSet returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '{' this_AISLBasicSet_1= ruleAISLBasicSet (kw= ';' this_AISLBasicSet_3= ruleAISLBasicSet )* kw= '}' ) | kw= '{}' ) ;
    public final AntlrDatatypeRuleToken ruleAISLSet() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AISLBasicSet_1 = null;

        AntlrDatatypeRuleToken this_AISLBasicSet_3 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2581:2: ( ( (kw= '{' this_AISLBasicSet_1= ruleAISLBasicSet (kw= ';' this_AISLBasicSet_3= ruleAISLBasicSet )* kw= '}' ) | kw= '{}' ) )
            // InternalAlpha.g:2582:2: ( (kw= '{' this_AISLBasicSet_1= ruleAISLBasicSet (kw= ';' this_AISLBasicSet_3= ruleAISLBasicSet )* kw= '}' ) | kw= '{}' )
            {
            // InternalAlpha.g:2582:2: ( (kw= '{' this_AISLBasicSet_1= ruleAISLBasicSet (kw= ';' this_AISLBasicSet_3= ruleAISLBasicSet )* kw= '}' ) | kw= '{}' )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==19) ) {
                alt40=1;
            }
            else if ( (LA40_0==42) ) {
                alt40=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // InternalAlpha.g:2583:3: (kw= '{' this_AISLBasicSet_1= ruleAISLBasicSet (kw= ';' this_AISLBasicSet_3= ruleAISLBasicSet )* kw= '}' )
                    {
                    // InternalAlpha.g:2583:3: (kw= '{' this_AISLBasicSet_1= ruleAISLBasicSet (kw= ';' this_AISLBasicSet_3= ruleAISLBasicSet )* kw= '}' )
                    // InternalAlpha.g:2584:4: kw= '{' this_AISLBasicSet_1= ruleAISLBasicSet (kw= ';' this_AISLBasicSet_3= ruleAISLBasicSet )* kw= '}'
                    {
                    kw=(Token)match(input,19,FOLLOW_36); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAISLSetAccess().getLeftCurlyBracketKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getAISLSetAccess().getAISLBasicSetParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_35);
                    this_AISLBasicSet_1=ruleAISLBasicSet();

                    state._fsp--;


                    				current.merge(this_AISLBasicSet_1);
                    			

                    				afterParserOrEnumRuleCall();
                    			
                    // InternalAlpha.g:2599:4: (kw= ';' this_AISLBasicSet_3= ruleAISLBasicSet )*
                    loop39:
                    do {
                        int alt39=2;
                        int LA39_0 = input.LA(1);

                        if ( (LA39_0==34) ) {
                            alt39=1;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // InternalAlpha.g:2600:5: kw= ';' this_AISLBasicSet_3= ruleAISLBasicSet
                    	    {
                    	    kw=(Token)match(input,34,FOLLOW_36); 

                    	    					current.merge(kw);
                    	    					newLeafNode(kw, grammarAccess.getAISLSetAccess().getSemicolonKeyword_0_2_0());
                    	    				

                    	    					newCompositeNode(grammarAccess.getAISLSetAccess().getAISLBasicSetParserRuleCall_0_2_1());
                    	    				
                    	    pushFollow(FOLLOW_35);
                    	    this_AISLBasicSet_3=ruleAISLBasicSet();

                    	    state._fsp--;


                    	    					current.merge(this_AISLBasicSet_3);
                    	    				

                    	    					afterParserOrEnumRuleCall();
                    	    				

                    	    }
                    	    break;

                    	default :
                    	    break loop39;
                        }
                    } while (true);

                    kw=(Token)match(input,20,FOLLOW_2); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAISLSetAccess().getRightCurlyBracketKeyword_0_3());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:2623:3: kw= '{}'
                    {
                    kw=(Token)match(input,42,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAISLSetAccess().getLeftCurlyBracketRightCurlyBracketKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLSet"


    // $ANTLR start "entryRuleAISLBasicSet"
    // InternalAlpha.g:2632:1: entryRuleAISLBasicSet returns [String current=null] : iv_ruleAISLBasicSet= ruleAISLBasicSet EOF ;
    public final String entryRuleAISLBasicSet() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLBasicSet = null;


        try {
            // InternalAlpha.g:2632:52: (iv_ruleAISLBasicSet= ruleAISLBasicSet EOF )
            // InternalAlpha.g:2633:2: iv_ruleAISLBasicSet= ruleAISLBasicSet EOF
            {
             newCompositeNode(grammarAccess.getAISLBasicSetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLBasicSet=ruleAISLBasicSet();

            state._fsp--;

             current =iv_ruleAISLBasicSet.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLBasicSet"


    // $ANTLR start "ruleAISLBasicSet"
    // InternalAlpha.g:2639:1: ruleAISLBasicSet returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_4= ruleAISLString ) ;
    public final AntlrDatatypeRuleToken ruleAISLBasicSet() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AISLExpressionList_1 = null;

        AntlrDatatypeRuleToken this_AISLString_4 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2645:2: ( (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_4= ruleAISLString ) )
            // InternalAlpha.g:2646:2: (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_4= ruleAISLString )
            {
            // InternalAlpha.g:2646:2: (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_4= ruleAISLString )
            // InternalAlpha.g:2647:3: kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_4= ruleAISLString
            {
            kw=(Token)match(input,37,FOLLOW_31); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLBasicSetAccess().getLeftSquareBracketKeyword_0());
            		

            			newCompositeNode(grammarAccess.getAISLBasicSetAccess().getAISLExpressionListParserRuleCall_1());
            		
            pushFollow(FOLLOW_45);
            this_AISLExpressionList_1=ruleAISLExpressionList();

            state._fsp--;


            			current.merge(this_AISLExpressionList_1);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,38,FOLLOW_26); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLBasicSetAccess().getRightSquareBracketKeyword_2());
            		
            kw=(Token)match(input,33,FOLLOW_46); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLBasicSetAccess().getColonKeyword_3());
            		

            			newCompositeNode(grammarAccess.getAISLBasicSetAccess().getAISLStringParserRuleCall_4());
            		
            pushFollow(FOLLOW_2);
            this_AISLString_4=ruleAISLString();

            state._fsp--;


            			current.merge(this_AISLString_4);
            		

            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLBasicSet"


    // $ANTLR start "entryRuleAISLRelation"
    // InternalAlpha.g:2686:1: entryRuleAISLRelation returns [String current=null] : iv_ruleAISLRelation= ruleAISLRelation EOF ;
    public final String entryRuleAISLRelation() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLRelation = null;


        try {
            // InternalAlpha.g:2686:52: (iv_ruleAISLRelation= ruleAISLRelation EOF )
            // InternalAlpha.g:2687:2: iv_ruleAISLRelation= ruleAISLRelation EOF
            {
             newCompositeNode(grammarAccess.getAISLRelationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLRelation=ruleAISLRelation();

            state._fsp--;

             current =iv_ruleAISLRelation.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLRelation"


    // $ANTLR start "ruleAISLRelation"
    // InternalAlpha.g:2693:1: ruleAISLRelation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '{' this_AISLBasicRelation_1= ruleAISLBasicRelation (kw= ';' this_AISLBasicRelation_3= ruleAISLBasicRelation )* kw= '}' ) ;
    public final AntlrDatatypeRuleToken ruleAISLRelation() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AISLBasicRelation_1 = null;

        AntlrDatatypeRuleToken this_AISLBasicRelation_3 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2699:2: ( (kw= '{' this_AISLBasicRelation_1= ruleAISLBasicRelation (kw= ';' this_AISLBasicRelation_3= ruleAISLBasicRelation )* kw= '}' ) )
            // InternalAlpha.g:2700:2: (kw= '{' this_AISLBasicRelation_1= ruleAISLBasicRelation (kw= ';' this_AISLBasicRelation_3= ruleAISLBasicRelation )* kw= '}' )
            {
            // InternalAlpha.g:2700:2: (kw= '{' this_AISLBasicRelation_1= ruleAISLBasicRelation (kw= ';' this_AISLBasicRelation_3= ruleAISLBasicRelation )* kw= '}' )
            // InternalAlpha.g:2701:3: kw= '{' this_AISLBasicRelation_1= ruleAISLBasicRelation (kw= ';' this_AISLBasicRelation_3= ruleAISLBasicRelation )* kw= '}'
            {
            kw=(Token)match(input,19,FOLLOW_36); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLRelationAccess().getLeftCurlyBracketKeyword_0());
            		

            			newCompositeNode(grammarAccess.getAISLRelationAccess().getAISLBasicRelationParserRuleCall_1());
            		
            pushFollow(FOLLOW_35);
            this_AISLBasicRelation_1=ruleAISLBasicRelation();

            state._fsp--;


            			current.merge(this_AISLBasicRelation_1);
            		

            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:2716:3: (kw= ';' this_AISLBasicRelation_3= ruleAISLBasicRelation )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==34) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalAlpha.g:2717:4: kw= ';' this_AISLBasicRelation_3= ruleAISLBasicRelation
            	    {
            	    kw=(Token)match(input,34,FOLLOW_36); 

            	    				current.merge(kw);
            	    				newLeafNode(kw, grammarAccess.getAISLRelationAccess().getSemicolonKeyword_2_0());
            	    			

            	    				newCompositeNode(grammarAccess.getAISLRelationAccess().getAISLBasicRelationParserRuleCall_2_1());
            	    			
            	    pushFollow(FOLLOW_35);
            	    this_AISLBasicRelation_3=ruleAISLBasicRelation();

            	    state._fsp--;


            	    				current.merge(this_AISLBasicRelation_3);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

            kw=(Token)match(input,20,FOLLOW_2); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLRelationAccess().getRightCurlyBracketKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLRelation"


    // $ANTLR start "entryRuleAISLQPolynomial"
    // InternalAlpha.g:2742:1: entryRuleAISLQPolynomial returns [String current=null] : iv_ruleAISLQPolynomial= ruleAISLQPolynomial EOF ;
    public final String entryRuleAISLQPolynomial() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLQPolynomial = null;


        try {
            // InternalAlpha.g:2742:55: (iv_ruleAISLQPolynomial= ruleAISLQPolynomial EOF )
            // InternalAlpha.g:2743:2: iv_ruleAISLQPolynomial= ruleAISLQPolynomial EOF
            {
             newCompositeNode(grammarAccess.getAISLQPolynomialRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLQPolynomial=ruleAISLQPolynomial();

            state._fsp--;

             current =iv_ruleAISLQPolynomial.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLQPolynomial"


    // $ANTLR start "ruleAISLQPolynomial"
    // InternalAlpha.g:2749:1: ruleAISLQPolynomial returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' this_AISLQPolynomialBody_4= ruleAISLQPolynomialBody ) ;
    public final AntlrDatatypeRuleToken ruleAISLQPolynomial() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AISLExpressionList_1 = null;

        AntlrDatatypeRuleToken this_AISLQPolynomialBody_4 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2755:2: ( (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' this_AISLQPolynomialBody_4= ruleAISLQPolynomialBody ) )
            // InternalAlpha.g:2756:2: (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' this_AISLQPolynomialBody_4= ruleAISLQPolynomialBody )
            {
            // InternalAlpha.g:2756:2: (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' this_AISLQPolynomialBody_4= ruleAISLQPolynomialBody )
            // InternalAlpha.g:2757:3: kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' this_AISLQPolynomialBody_4= ruleAISLQPolynomialBody
            {
            kw=(Token)match(input,37,FOLLOW_31); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLQPolynomialAccess().getLeftSquareBracketKeyword_0());
            		

            			newCompositeNode(grammarAccess.getAISLQPolynomialAccess().getAISLExpressionListParserRuleCall_1());
            		
            pushFollow(FOLLOW_45);
            this_AISLExpressionList_1=ruleAISLExpressionList();

            state._fsp--;


            			current.merge(this_AISLExpressionList_1);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,38,FOLLOW_28); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLQPolynomialAccess().getRightSquareBracketKeyword_2());
            		
            kw=(Token)match(input,36,FOLLOW_34); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLQPolynomialAccess().getHyphenMinusGreaterThanSignKeyword_3());
            		

            			newCompositeNode(grammarAccess.getAISLQPolynomialAccess().getAISLQPolynomialBodyParserRuleCall_4());
            		
            pushFollow(FOLLOW_2);
            this_AISLQPolynomialBody_4=ruleAISLQPolynomialBody();

            state._fsp--;


            			current.merge(this_AISLQPolynomialBody_4);
            		

            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLQPolynomial"


    // $ANTLR start "entryRuleAISLQPolynomialBody"
    // InternalAlpha.g:2796:1: entryRuleAISLQPolynomialBody returns [String current=null] : iv_ruleAISLQPolynomialBody= ruleAISLQPolynomialBody EOF ;
    public final String entryRuleAISLQPolynomialBody() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLQPolynomialBody = null;


        try {
            // InternalAlpha.g:2796:59: (iv_ruleAISLQPolynomialBody= ruleAISLQPolynomialBody EOF )
            // InternalAlpha.g:2797:2: iv_ruleAISLQPolynomialBody= ruleAISLQPolynomialBody EOF
            {
             newCompositeNode(grammarAccess.getAISLQPolynomialBodyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLQPolynomialBody=ruleAISLQPolynomialBody();

            state._fsp--;

             current =iv_ruleAISLQPolynomialBody.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLQPolynomialBody"


    // $ANTLR start "ruleAISLQPolynomialBody"
    // InternalAlpha.g:2803:1: ruleAISLQPolynomialBody returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_AISLPolynomialExpression_0= ruleAISLPolynomialExpression (kw= ':' this_AISLString_2= ruleAISLString )? ) ;
    public final AntlrDatatypeRuleToken ruleAISLQPolynomialBody() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AISLPolynomialExpression_0 = null;

        AntlrDatatypeRuleToken this_AISLString_2 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2809:2: ( (this_AISLPolynomialExpression_0= ruleAISLPolynomialExpression (kw= ':' this_AISLString_2= ruleAISLString )? ) )
            // InternalAlpha.g:2810:2: (this_AISLPolynomialExpression_0= ruleAISLPolynomialExpression (kw= ':' this_AISLString_2= ruleAISLString )? )
            {
            // InternalAlpha.g:2810:2: (this_AISLPolynomialExpression_0= ruleAISLPolynomialExpression (kw= ':' this_AISLString_2= ruleAISLString )? )
            // InternalAlpha.g:2811:3: this_AISLPolynomialExpression_0= ruleAISLPolynomialExpression (kw= ':' this_AISLString_2= ruleAISLString )?
            {

            			newCompositeNode(grammarAccess.getAISLQPolynomialBodyAccess().getAISLPolynomialExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_47);
            this_AISLPolynomialExpression_0=ruleAISLPolynomialExpression();

            state._fsp--;


            			current.merge(this_AISLPolynomialExpression_0);
            		

            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:2821:3: (kw= ':' this_AISLString_2= ruleAISLString )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==33) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalAlpha.g:2822:4: kw= ':' this_AISLString_2= ruleAISLString
                    {
                    kw=(Token)match(input,33,FOLLOW_46); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAISLQPolynomialBodyAccess().getColonKeyword_1_0());
                    			

                    				newCompositeNode(grammarAccess.getAISLQPolynomialBodyAccess().getAISLStringParserRuleCall_1_1());
                    			
                    pushFollow(FOLLOW_2);
                    this_AISLString_2=ruleAISLString();

                    state._fsp--;


                    				current.merge(this_AISLString_2);
                    			

                    				afterParserOrEnumRuleCall();
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLQPolynomialBody"


    // $ANTLR start "entryRuleAISLPWQPolynomial"
    // InternalAlpha.g:2842:1: entryRuleAISLPWQPolynomial returns [String current=null] : iv_ruleAISLPWQPolynomial= ruleAISLPWQPolynomial EOF ;
    public final String entryRuleAISLPWQPolynomial() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLPWQPolynomial = null;


        try {
            // InternalAlpha.g:2842:57: (iv_ruleAISLPWQPolynomial= ruleAISLPWQPolynomial EOF )
            // InternalAlpha.g:2843:2: iv_ruleAISLPWQPolynomial= ruleAISLPWQPolynomial EOF
            {
             newCompositeNode(grammarAccess.getAISLPWQPolynomialRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLPWQPolynomial=ruleAISLPWQPolynomial();

            state._fsp--;

             current =iv_ruleAISLPWQPolynomial.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLPWQPolynomial"


    // $ANTLR start "ruleAISLPWQPolynomial"
    // InternalAlpha.g:2849:1: ruleAISLPWQPolynomial returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '{' this_AISLQPolynomial_1= ruleAISLQPolynomial (kw= ';' this_AISLQPolynomial_3= ruleAISLQPolynomial )* kw= '}' ) ;
    public final AntlrDatatypeRuleToken ruleAISLPWQPolynomial() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AISLQPolynomial_1 = null;

        AntlrDatatypeRuleToken this_AISLQPolynomial_3 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2855:2: ( (kw= '{' this_AISLQPolynomial_1= ruleAISLQPolynomial (kw= ';' this_AISLQPolynomial_3= ruleAISLQPolynomial )* kw= '}' ) )
            // InternalAlpha.g:2856:2: (kw= '{' this_AISLQPolynomial_1= ruleAISLQPolynomial (kw= ';' this_AISLQPolynomial_3= ruleAISLQPolynomial )* kw= '}' )
            {
            // InternalAlpha.g:2856:2: (kw= '{' this_AISLQPolynomial_1= ruleAISLQPolynomial (kw= ';' this_AISLQPolynomial_3= ruleAISLQPolynomial )* kw= '}' )
            // InternalAlpha.g:2857:3: kw= '{' this_AISLQPolynomial_1= ruleAISLQPolynomial (kw= ';' this_AISLQPolynomial_3= ruleAISLQPolynomial )* kw= '}'
            {
            kw=(Token)match(input,19,FOLLOW_36); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLPWQPolynomialAccess().getLeftCurlyBracketKeyword_0());
            		

            			newCompositeNode(grammarAccess.getAISLPWQPolynomialAccess().getAISLQPolynomialParserRuleCall_1());
            		
            pushFollow(FOLLOW_35);
            this_AISLQPolynomial_1=ruleAISLQPolynomial();

            state._fsp--;


            			current.merge(this_AISLQPolynomial_1);
            		

            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:2872:3: (kw= ';' this_AISLQPolynomial_3= ruleAISLQPolynomial )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==34) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalAlpha.g:2873:4: kw= ';' this_AISLQPolynomial_3= ruleAISLQPolynomial
            	    {
            	    kw=(Token)match(input,34,FOLLOW_36); 

            	    				current.merge(kw);
            	    				newLeafNode(kw, grammarAccess.getAISLPWQPolynomialAccess().getSemicolonKeyword_2_0());
            	    			

            	    				newCompositeNode(grammarAccess.getAISLPWQPolynomialAccess().getAISLQPolynomialParserRuleCall_2_1());
            	    			
            	    pushFollow(FOLLOW_35);
            	    this_AISLQPolynomial_3=ruleAISLQPolynomial();

            	    state._fsp--;


            	    				current.merge(this_AISLQPolynomial_3);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

            kw=(Token)match(input,20,FOLLOW_2); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLPWQPolynomialAccess().getRightCurlyBracketKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLPWQPolynomial"


    // $ANTLR start "entryRuleAISLString"
    // InternalAlpha.g:2898:1: entryRuleAISLString returns [String current=null] : iv_ruleAISLString= ruleAISLString EOF ;
    public final String entryRuleAISLString() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLString = null;


        try {
            // InternalAlpha.g:2898:50: (iv_ruleAISLString= ruleAISLString EOF )
            // InternalAlpha.g:2899:2: iv_ruleAISLString= ruleAISLString EOF
            {
             newCompositeNode(grammarAccess.getAISLStringRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLString=ruleAISLString();

            state._fsp--;

             current =iv_ruleAISLString.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLString"


    // $ANTLR start "ruleAISLString"
    // InternalAlpha.g:2905:1: ruleAISLString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | kw= 'floor' | kw= 'and' | kw= 'or' | kw= '[' | kw= ']' | kw= '(' | kw= ')' | kw= ',' | kw= ':' | kw= '&' | kw= '|' | this_WS_18= RULE_WS | kw= '>=' | kw= '>' | kw= '=' | kw= '<' | kw= '<=' )* ;
    public final AntlrDatatypeRuleToken ruleAISLString() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_1=null;
        Token kw=null;
        Token this_WS_18=null;
        AntlrDatatypeRuleToken this_IndexName_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:2911:2: ( (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | kw= 'floor' | kw= 'and' | kw= 'or' | kw= '[' | kw= ']' | kw= '(' | kw= ')' | kw= ',' | kw= ':' | kw= '&' | kw= '|' | this_WS_18= RULE_WS | kw= '>=' | kw= '>' | kw= '=' | kw= '<' | kw= '<=' )* )
            // InternalAlpha.g:2912:2: (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | kw= 'floor' | kw= 'and' | kw= 'or' | kw= '[' | kw= ']' | kw= '(' | kw= ')' | kw= ',' | kw= ':' | kw= '&' | kw= '|' | this_WS_18= RULE_WS | kw= '>=' | kw= '>' | kw= '=' | kw= '<' | kw= '<=' )*
            {
            // InternalAlpha.g:2912:2: (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | kw= 'floor' | kw= 'and' | kw= 'or' | kw= '[' | kw= ']' | kw= '(' | kw= ')' | kw= ',' | kw= ':' | kw= '&' | kw= '|' | this_WS_18= RULE_WS | kw= '>=' | kw= '>' | kw= '=' | kw= '<' | kw= '<=' )*
            loop44:
            do {
                int alt44=25;
                switch ( input.LA(1) ) {
                case RULE_ID:
                    {
                    alt44=1;
                    }
                    break;
                case RULE_INT:
                    {
                    alt44=2;
                    }
                    break;
                case 43:
                    {
                    alt44=3;
                    }
                    break;
                case 44:
                    {
                    alt44=4;
                    }
                    break;
                case 45:
                    {
                    alt44=5;
                    }
                    break;
                case 46:
                    {
                    alt44=6;
                    }
                    break;
                case 47:
                    {
                    alt44=7;
                    }
                    break;
                case 48:
                    {
                    alt44=8;
                    }
                    break;
                case 49:
                    {
                    alt44=9;
                    }
                    break;
                case 50:
                    {
                    alt44=10;
                    }
                    break;
                case 37:
                    {
                    alt44=11;
                    }
                    break;
                case 38:
                    {
                    alt44=12;
                    }
                    break;
                case 15:
                    {
                    alt44=13;
                    }
                    break;
                case 16:
                    {
                    alt44=14;
                    }
                    break;
                case 24:
                    {
                    alt44=15;
                    }
                    break;
                case 33:
                    {
                    alt44=16;
                    }
                    break;
                case 51:
                    {
                    alt44=17;
                    }
                    break;
                case 52:
                    {
                    alt44=18;
                    }
                    break;
                case RULE_WS:
                    {
                    alt44=19;
                    }
                    break;
                case 53:
                    {
                    alt44=20;
                    }
                    break;
                case 54:
                    {
                    alt44=21;
                    }
                    break;
                case 13:
                    {
                    alt44=22;
                    }
                    break;
                case 55:
                    {
                    alt44=23;
                    }
                    break;
                case 56:
                    {
                    alt44=24;
                    }
                    break;

                }

                switch (alt44) {
            	case 1 :
            	    // InternalAlpha.g:2913:3: this_IndexName_0= ruleIndexName
            	    {

            	    			newCompositeNode(grammarAccess.getAISLStringAccess().getIndexNameParserRuleCall_0());
            	    		
            	    pushFollow(FOLLOW_48);
            	    this_IndexName_0=ruleIndexName();

            	    state._fsp--;


            	    			current.merge(this_IndexName_0);
            	    		

            	    			afterParserOrEnumRuleCall();
            	    		

            	    }
            	    break;
            	case 2 :
            	    // InternalAlpha.g:2924:3: this_INT_1= RULE_INT
            	    {
            	    this_INT_1=(Token)match(input,RULE_INT,FOLLOW_48); 

            	    			current.merge(this_INT_1);
            	    		

            	    			newLeafNode(this_INT_1, grammarAccess.getAISLStringAccess().getINTTerminalRuleCall_1());
            	    		

            	    }
            	    break;
            	case 3 :
            	    // InternalAlpha.g:2932:3: kw= '+'
            	    {
            	    kw=(Token)match(input,43,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getPlusSignKeyword_2());
            	    		

            	    }
            	    break;
            	case 4 :
            	    // InternalAlpha.g:2938:3: kw= '-'
            	    {
            	    kw=(Token)match(input,44,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getHyphenMinusKeyword_3());
            	    		

            	    }
            	    break;
            	case 5 :
            	    // InternalAlpha.g:2944:3: kw= '*'
            	    {
            	    kw=(Token)match(input,45,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getAsteriskKeyword_4());
            	    		

            	    }
            	    break;
            	case 6 :
            	    // InternalAlpha.g:2950:3: kw= '/'
            	    {
            	    kw=(Token)match(input,46,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getSolidusKeyword_5());
            	    		

            	    }
            	    break;
            	case 7 :
            	    // InternalAlpha.g:2956:3: kw= '%'
            	    {
            	    kw=(Token)match(input,47,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getPercentSignKeyword_6());
            	    		

            	    }
            	    break;
            	case 8 :
            	    // InternalAlpha.g:2962:3: kw= 'floor'
            	    {
            	    kw=(Token)match(input,48,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getFloorKeyword_7());
            	    		

            	    }
            	    break;
            	case 9 :
            	    // InternalAlpha.g:2968:3: kw= 'and'
            	    {
            	    kw=(Token)match(input,49,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getAndKeyword_8());
            	    		

            	    }
            	    break;
            	case 10 :
            	    // InternalAlpha.g:2974:3: kw= 'or'
            	    {
            	    kw=(Token)match(input,50,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getOrKeyword_9());
            	    		

            	    }
            	    break;
            	case 11 :
            	    // InternalAlpha.g:2980:3: kw= '['
            	    {
            	    kw=(Token)match(input,37,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getLeftSquareBracketKeyword_10());
            	    		

            	    }
            	    break;
            	case 12 :
            	    // InternalAlpha.g:2986:3: kw= ']'
            	    {
            	    kw=(Token)match(input,38,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getRightSquareBracketKeyword_11());
            	    		

            	    }
            	    break;
            	case 13 :
            	    // InternalAlpha.g:2992:3: kw= '('
            	    {
            	    kw=(Token)match(input,15,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getLeftParenthesisKeyword_12());
            	    		

            	    }
            	    break;
            	case 14 :
            	    // InternalAlpha.g:2998:3: kw= ')'
            	    {
            	    kw=(Token)match(input,16,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getRightParenthesisKeyword_13());
            	    		

            	    }
            	    break;
            	case 15 :
            	    // InternalAlpha.g:3004:3: kw= ','
            	    {
            	    kw=(Token)match(input,24,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getCommaKeyword_14());
            	    		

            	    }
            	    break;
            	case 16 :
            	    // InternalAlpha.g:3010:3: kw= ':'
            	    {
            	    kw=(Token)match(input,33,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getColonKeyword_15());
            	    		

            	    }
            	    break;
            	case 17 :
            	    // InternalAlpha.g:3016:3: kw= '&'
            	    {
            	    kw=(Token)match(input,51,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getAmpersandKeyword_16());
            	    		

            	    }
            	    break;
            	case 18 :
            	    // InternalAlpha.g:3022:3: kw= '|'
            	    {
            	    kw=(Token)match(input,52,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getVerticalLineKeyword_17());
            	    		

            	    }
            	    break;
            	case 19 :
            	    // InternalAlpha.g:3028:3: this_WS_18= RULE_WS
            	    {
            	    this_WS_18=(Token)match(input,RULE_WS,FOLLOW_48); 

            	    			current.merge(this_WS_18);
            	    		

            	    			newLeafNode(this_WS_18, grammarAccess.getAISLStringAccess().getWSTerminalRuleCall_18());
            	    		

            	    }
            	    break;
            	case 20 :
            	    // InternalAlpha.g:3036:3: kw= '>='
            	    {
            	    kw=(Token)match(input,53,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getGreaterThanSignEqualsSignKeyword_19());
            	    		

            	    }
            	    break;
            	case 21 :
            	    // InternalAlpha.g:3042:3: kw= '>'
            	    {
            	    kw=(Token)match(input,54,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getGreaterThanSignKeyword_20());
            	    		

            	    }
            	    break;
            	case 22 :
            	    // InternalAlpha.g:3048:3: kw= '='
            	    {
            	    kw=(Token)match(input,13,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getEqualsSignKeyword_21());
            	    		

            	    }
            	    break;
            	case 23 :
            	    // InternalAlpha.g:3054:3: kw= '<'
            	    {
            	    kw=(Token)match(input,55,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getLessThanSignKeyword_22());
            	    		

            	    }
            	    break;
            	case 24 :
            	    // InternalAlpha.g:3060:3: kw= '<='
            	    {
            	    kw=(Token)match(input,56,FOLLOW_48); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLStringAccess().getLessThanSignEqualsSignKeyword_23());
            	    		

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLString"


    // $ANTLR start "entryRuleAISLExpression"
    // InternalAlpha.g:3069:1: entryRuleAISLExpression returns [String current=null] : iv_ruleAISLExpression= ruleAISLExpression EOF ;
    public final String entryRuleAISLExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLExpression = null;


        try {
            // InternalAlpha.g:3069:54: (iv_ruleAISLExpression= ruleAISLExpression EOF )
            // InternalAlpha.g:3070:2: iv_ruleAISLExpression= ruleAISLExpression EOF
            {
             newCompositeNode(grammarAccess.getAISLExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLExpression=ruleAISLExpression();

            state._fsp--;

             current =iv_ruleAISLExpression.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLExpression"


    // $ANTLR start "ruleAISLExpression"
    // InternalAlpha.g:3076:1: ruleAISLExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '=' | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | kw= 'floor' | kw= '(' | kw= ')' | this_WS_11= RULE_WS )+ ;
    public final AntlrDatatypeRuleToken ruleAISLExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_1=null;
        Token kw=null;
        Token this_WS_11=null;
        AntlrDatatypeRuleToken this_IndexName_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3082:2: ( (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '=' | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | kw= 'floor' | kw= '(' | kw= ')' | this_WS_11= RULE_WS )+ )
            // InternalAlpha.g:3083:2: (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '=' | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | kw= 'floor' | kw= '(' | kw= ')' | this_WS_11= RULE_WS )+
            {
            // InternalAlpha.g:3083:2: (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '=' | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | kw= 'floor' | kw= '(' | kw= ')' | this_WS_11= RULE_WS )+
            int cnt45=0;
            loop45:
            do {
                int alt45=13;
                switch ( input.LA(1) ) {
                case RULE_ID:
                    {
                    alt45=1;
                    }
                    break;
                case RULE_INT:
                    {
                    alt45=2;
                    }
                    break;
                case 13:
                    {
                    alt45=3;
                    }
                    break;
                case 43:
                    {
                    alt45=4;
                    }
                    break;
                case 44:
                    {
                    alt45=5;
                    }
                    break;
                case 45:
                    {
                    alt45=6;
                    }
                    break;
                case 46:
                    {
                    alt45=7;
                    }
                    break;
                case 47:
                    {
                    alt45=8;
                    }
                    break;
                case 48:
                    {
                    alt45=9;
                    }
                    break;
                case 15:
                    {
                    alt45=10;
                    }
                    break;
                case 16:
                    {
                    alt45=11;
                    }
                    break;
                case RULE_WS:
                    {
                    alt45=12;
                    }
                    break;

                }

                switch (alt45) {
            	case 1 :
            	    // InternalAlpha.g:3084:3: this_IndexName_0= ruleIndexName
            	    {

            	    			newCompositeNode(grammarAccess.getAISLExpressionAccess().getIndexNameParserRuleCall_0());
            	    		
            	    pushFollow(FOLLOW_49);
            	    this_IndexName_0=ruleIndexName();

            	    state._fsp--;


            	    			current.merge(this_IndexName_0);
            	    		

            	    			afterParserOrEnumRuleCall();
            	    		

            	    }
            	    break;
            	case 2 :
            	    // InternalAlpha.g:3095:3: this_INT_1= RULE_INT
            	    {
            	    this_INT_1=(Token)match(input,RULE_INT,FOLLOW_49); 

            	    			current.merge(this_INT_1);
            	    		

            	    			newLeafNode(this_INT_1, grammarAccess.getAISLExpressionAccess().getINTTerminalRuleCall_1());
            	    		

            	    }
            	    break;
            	case 3 :
            	    // InternalAlpha.g:3103:3: kw= '='
            	    {
            	    kw=(Token)match(input,13,FOLLOW_49); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLExpressionAccess().getEqualsSignKeyword_2());
            	    		

            	    }
            	    break;
            	case 4 :
            	    // InternalAlpha.g:3109:3: kw= '+'
            	    {
            	    kw=(Token)match(input,43,FOLLOW_49); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLExpressionAccess().getPlusSignKeyword_3());
            	    		

            	    }
            	    break;
            	case 5 :
            	    // InternalAlpha.g:3115:3: kw= '-'
            	    {
            	    kw=(Token)match(input,44,FOLLOW_49); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLExpressionAccess().getHyphenMinusKeyword_4());
            	    		

            	    }
            	    break;
            	case 6 :
            	    // InternalAlpha.g:3121:3: kw= '*'
            	    {
            	    kw=(Token)match(input,45,FOLLOW_49); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLExpressionAccess().getAsteriskKeyword_5());
            	    		

            	    }
            	    break;
            	case 7 :
            	    // InternalAlpha.g:3127:3: kw= '/'
            	    {
            	    kw=(Token)match(input,46,FOLLOW_49); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLExpressionAccess().getSolidusKeyword_6());
            	    		

            	    }
            	    break;
            	case 8 :
            	    // InternalAlpha.g:3133:3: kw= '%'
            	    {
            	    kw=(Token)match(input,47,FOLLOW_49); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLExpressionAccess().getPercentSignKeyword_7());
            	    		

            	    }
            	    break;
            	case 9 :
            	    // InternalAlpha.g:3139:3: kw= 'floor'
            	    {
            	    kw=(Token)match(input,48,FOLLOW_49); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLExpressionAccess().getFloorKeyword_8());
            	    		

            	    }
            	    break;
            	case 10 :
            	    // InternalAlpha.g:3145:3: kw= '('
            	    {
            	    kw=(Token)match(input,15,FOLLOW_49); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLExpressionAccess().getLeftParenthesisKeyword_9());
            	    		

            	    }
            	    break;
            	case 11 :
            	    // InternalAlpha.g:3151:3: kw= ')'
            	    {
            	    kw=(Token)match(input,16,FOLLOW_49); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLExpressionAccess().getRightParenthesisKeyword_10());
            	    		

            	    }
            	    break;
            	case 12 :
            	    // InternalAlpha.g:3157:3: this_WS_11= RULE_WS
            	    {
            	    this_WS_11=(Token)match(input,RULE_WS,FOLLOW_49); 

            	    			current.merge(this_WS_11);
            	    		

            	    			newLeafNode(this_WS_11, grammarAccess.getAISLExpressionAccess().getWSTerminalRuleCall_11());
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt45 >= 1 ) break loop45;
                        EarlyExitException eee =
                            new EarlyExitException(45, input);
                        throw eee;
                }
                cnt45++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLExpression"


    // $ANTLR start "entryRuleAISLPolynomialExpression"
    // InternalAlpha.g:3168:1: entryRuleAISLPolynomialExpression returns [String current=null] : iv_ruleAISLPolynomialExpression= ruleAISLPolynomialExpression EOF ;
    public final String entryRuleAISLPolynomialExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLPolynomialExpression = null;


        try {
            // InternalAlpha.g:3168:64: (iv_ruleAISLPolynomialExpression= ruleAISLPolynomialExpression EOF )
            // InternalAlpha.g:3169:2: iv_ruleAISLPolynomialExpression= ruleAISLPolynomialExpression EOF
            {
             newCompositeNode(grammarAccess.getAISLPolynomialExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLPolynomialExpression=ruleAISLPolynomialExpression();

            state._fsp--;

             current =iv_ruleAISLPolynomialExpression.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLPolynomialExpression"


    // $ANTLR start "ruleAISLPolynomialExpression"
    // InternalAlpha.g:3175:1: ruleAISLPolynomialExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '(' | kw= ')' | kw= '^' )+ ;
    public final AntlrDatatypeRuleToken ruleAISLPolynomialExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_1=null;
        Token kw=null;
        AntlrDatatypeRuleToken this_IndexName_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3181:2: ( (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '(' | kw= ')' | kw= '^' )+ )
            // InternalAlpha.g:3182:2: (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '(' | kw= ')' | kw= '^' )+
            {
            // InternalAlpha.g:3182:2: (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '(' | kw= ')' | kw= '^' )+
            int cnt46=0;
            loop46:
            do {
                int alt46=10;
                switch ( input.LA(1) ) {
                case RULE_ID:
                    {
                    alt46=1;
                    }
                    break;
                case RULE_INT:
                    {
                    alt46=2;
                    }
                    break;
                case 43:
                    {
                    alt46=3;
                    }
                    break;
                case 44:
                    {
                    alt46=4;
                    }
                    break;
                case 45:
                    {
                    alt46=5;
                    }
                    break;
                case 46:
                    {
                    alt46=6;
                    }
                    break;
                case 15:
                    {
                    alt46=7;
                    }
                    break;
                case 16:
                    {
                    alt46=8;
                    }
                    break;
                case 57:
                    {
                    alt46=9;
                    }
                    break;

                }

                switch (alt46) {
            	case 1 :
            	    // InternalAlpha.g:3183:3: this_IndexName_0= ruleIndexName
            	    {

            	    			newCompositeNode(grammarAccess.getAISLPolynomialExpressionAccess().getIndexNameParserRuleCall_0());
            	    		
            	    pushFollow(FOLLOW_50);
            	    this_IndexName_0=ruleIndexName();

            	    state._fsp--;


            	    			current.merge(this_IndexName_0);
            	    		

            	    			afterParserOrEnumRuleCall();
            	    		

            	    }
            	    break;
            	case 2 :
            	    // InternalAlpha.g:3194:3: this_INT_1= RULE_INT
            	    {
            	    this_INT_1=(Token)match(input,RULE_INT,FOLLOW_50); 

            	    			current.merge(this_INT_1);
            	    		

            	    			newLeafNode(this_INT_1, grammarAccess.getAISLPolynomialExpressionAccess().getINTTerminalRuleCall_1());
            	    		

            	    }
            	    break;
            	case 3 :
            	    // InternalAlpha.g:3202:3: kw= '+'
            	    {
            	    kw=(Token)match(input,43,FOLLOW_50); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLPolynomialExpressionAccess().getPlusSignKeyword_2());
            	    		

            	    }
            	    break;
            	case 4 :
            	    // InternalAlpha.g:3208:3: kw= '-'
            	    {
            	    kw=(Token)match(input,44,FOLLOW_50); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLPolynomialExpressionAccess().getHyphenMinusKeyword_3());
            	    		

            	    }
            	    break;
            	case 5 :
            	    // InternalAlpha.g:3214:3: kw= '*'
            	    {
            	    kw=(Token)match(input,45,FOLLOW_50); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLPolynomialExpressionAccess().getAsteriskKeyword_4());
            	    		

            	    }
            	    break;
            	case 6 :
            	    // InternalAlpha.g:3220:3: kw= '/'
            	    {
            	    kw=(Token)match(input,46,FOLLOW_50); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLPolynomialExpressionAccess().getSolidusKeyword_5());
            	    		

            	    }
            	    break;
            	case 7 :
            	    // InternalAlpha.g:3226:3: kw= '('
            	    {
            	    kw=(Token)match(input,15,FOLLOW_50); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLPolynomialExpressionAccess().getLeftParenthesisKeyword_6());
            	    		

            	    }
            	    break;
            	case 8 :
            	    // InternalAlpha.g:3232:3: kw= ')'
            	    {
            	    kw=(Token)match(input,16,FOLLOW_50); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLPolynomialExpressionAccess().getRightParenthesisKeyword_7());
            	    		

            	    }
            	    break;
            	case 9 :
            	    // InternalAlpha.g:3238:3: kw= '^'
            	    {
            	    kw=(Token)match(input,57,FOLLOW_50); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLPolynomialExpressionAccess().getCircumflexAccentKeyword_8());
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt46 >= 1 ) break loop46;
                        EarlyExitException eee =
                            new EarlyExitException(46, input);
                        throw eee;
                }
                cnt46++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLPolynomialExpression"


    // $ANTLR start "entryRuleAISLExpressionLiteral"
    // InternalAlpha.g:3247:1: entryRuleAISLExpressionLiteral returns [String current=null] : iv_ruleAISLExpressionLiteral= ruleAISLExpressionLiteral EOF ;
    public final String entryRuleAISLExpressionLiteral() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLExpressionLiteral = null;


        try {
            // InternalAlpha.g:3247:61: (iv_ruleAISLExpressionLiteral= ruleAISLExpressionLiteral EOF )
            // InternalAlpha.g:3248:2: iv_ruleAISLExpressionLiteral= ruleAISLExpressionLiteral EOF
            {
             newCompositeNode(grammarAccess.getAISLExpressionLiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLExpressionLiteral=ruleAISLExpressionLiteral();

            state._fsp--;

             current =iv_ruleAISLExpressionLiteral.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLExpressionLiteral"


    // $ANTLR start "ruleAISLExpressionLiteral"
    // InternalAlpha.g:3254:1: ruleAISLExpressionLiteral returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? (this_IndexName_1= ruleIndexName | this_INT_2= RULE_INT | this_WS_3= RULE_WS )+ ) ;
    public final AntlrDatatypeRuleToken ruleAISLExpressionLiteral() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_WS_3=null;
        AntlrDatatypeRuleToken this_IndexName_1 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3260:2: ( ( (kw= '-' )? (this_IndexName_1= ruleIndexName | this_INT_2= RULE_INT | this_WS_3= RULE_WS )+ ) )
            // InternalAlpha.g:3261:2: ( (kw= '-' )? (this_IndexName_1= ruleIndexName | this_INT_2= RULE_INT | this_WS_3= RULE_WS )+ )
            {
            // InternalAlpha.g:3261:2: ( (kw= '-' )? (this_IndexName_1= ruleIndexName | this_INT_2= RULE_INT | this_WS_3= RULE_WS )+ )
            // InternalAlpha.g:3262:3: (kw= '-' )? (this_IndexName_1= ruleIndexName | this_INT_2= RULE_INT | this_WS_3= RULE_WS )+
            {
            // InternalAlpha.g:3262:3: (kw= '-' )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==44) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // InternalAlpha.g:3263:4: kw= '-'
                    {
                    kw=(Token)match(input,44,FOLLOW_51); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAISLExpressionLiteralAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            // InternalAlpha.g:3269:3: (this_IndexName_1= ruleIndexName | this_INT_2= RULE_INT | this_WS_3= RULE_WS )+
            int cnt48=0;
            loop48:
            do {
                int alt48=4;
                switch ( input.LA(1) ) {
                case RULE_ID:
                    {
                    alt48=1;
                    }
                    break;
                case RULE_INT:
                    {
                    alt48=2;
                    }
                    break;
                case RULE_WS:
                    {
                    alt48=3;
                    }
                    break;

                }

                switch (alt48) {
            	case 1 :
            	    // InternalAlpha.g:3270:4: this_IndexName_1= ruleIndexName
            	    {

            	    				newCompositeNode(grammarAccess.getAISLExpressionLiteralAccess().getIndexNameParserRuleCall_1_0());
            	    			
            	    pushFollow(FOLLOW_52);
            	    this_IndexName_1=ruleIndexName();

            	    state._fsp--;


            	    				current.merge(this_IndexName_1);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;
            	case 2 :
            	    // InternalAlpha.g:3281:4: this_INT_2= RULE_INT
            	    {
            	    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_52); 

            	    				current.merge(this_INT_2);
            	    			

            	    				newLeafNode(this_INT_2, grammarAccess.getAISLExpressionLiteralAccess().getINTTerminalRuleCall_1_1());
            	    			

            	    }
            	    break;
            	case 3 :
            	    // InternalAlpha.g:3289:4: this_WS_3= RULE_WS
            	    {
            	    this_WS_3=(Token)match(input,RULE_WS,FOLLOW_52); 

            	    				current.merge(this_WS_3);
            	    			

            	    				newLeafNode(this_WS_3, grammarAccess.getAISLExpressionLiteralAccess().getWSTerminalRuleCall_1_2());
            	    			

            	    }
            	    break;

            	default :
            	    if ( cnt48 >= 1 ) break loop48;
                        EarlyExitException eee =
                            new EarlyExitException(48, input);
                        throw eee;
                }
                cnt48++;
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLExpressionLiteral"


    // $ANTLR start "entryRuleAISLAdditiveOperator"
    // InternalAlpha.g:3301:1: entryRuleAISLAdditiveOperator returns [String current=null] : iv_ruleAISLAdditiveOperator= ruleAISLAdditiveOperator EOF ;
    public final String entryRuleAISLAdditiveOperator() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLAdditiveOperator = null;


        try {
            // InternalAlpha.g:3301:60: (iv_ruleAISLAdditiveOperator= ruleAISLAdditiveOperator EOF )
            // InternalAlpha.g:3302:2: iv_ruleAISLAdditiveOperator= ruleAISLAdditiveOperator EOF
            {
             newCompositeNode(grammarAccess.getAISLAdditiveOperatorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLAdditiveOperator=ruleAISLAdditiveOperator();

            state._fsp--;

             current =iv_ruleAISLAdditiveOperator.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLAdditiveOperator"


    // $ANTLR start "ruleAISLAdditiveOperator"
    // InternalAlpha.g:3308:1: ruleAISLAdditiveOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '+' | kw= '-' ) ;
    public final AntlrDatatypeRuleToken ruleAISLAdditiveOperator() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:3314:2: ( (kw= '+' | kw= '-' ) )
            // InternalAlpha.g:3315:2: (kw= '+' | kw= '-' )
            {
            // InternalAlpha.g:3315:2: (kw= '+' | kw= '-' )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==43) ) {
                alt49=1;
            }
            else if ( (LA49_0==44) ) {
                alt49=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // InternalAlpha.g:3316:3: kw= '+'
                    {
                    kw=(Token)match(input,43,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAISLAdditiveOperatorAccess().getPlusSignKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:3322:3: kw= '-'
                    {
                    kw=(Token)match(input,44,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAISLAdditiveOperatorAccess().getHyphenMinusKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLAdditiveOperator"


    // $ANTLR start "entryRuleAISLMultiplicativeOperator"
    // InternalAlpha.g:3331:1: entryRuleAISLMultiplicativeOperator returns [String current=null] : iv_ruleAISLMultiplicativeOperator= ruleAISLMultiplicativeOperator EOF ;
    public final String entryRuleAISLMultiplicativeOperator() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLMultiplicativeOperator = null;


        try {
            // InternalAlpha.g:3331:66: (iv_ruleAISLMultiplicativeOperator= ruleAISLMultiplicativeOperator EOF )
            // InternalAlpha.g:3332:2: iv_ruleAISLMultiplicativeOperator= ruleAISLMultiplicativeOperator EOF
            {
             newCompositeNode(grammarAccess.getAISLMultiplicativeOperatorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLMultiplicativeOperator=ruleAISLMultiplicativeOperator();

            state._fsp--;

             current =iv_ruleAISLMultiplicativeOperator.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLMultiplicativeOperator"


    // $ANTLR start "ruleAISLMultiplicativeOperator"
    // InternalAlpha.g:3338:1: ruleAISLMultiplicativeOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | kw= '/' | kw= '%' ) ;
    public final AntlrDatatypeRuleToken ruleAISLMultiplicativeOperator() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:3344:2: ( (kw= '*' | kw= '/' | kw= '%' ) )
            // InternalAlpha.g:3345:2: (kw= '*' | kw= '/' | kw= '%' )
            {
            // InternalAlpha.g:3345:2: (kw= '*' | kw= '/' | kw= '%' )
            int alt50=3;
            switch ( input.LA(1) ) {
            case 45:
                {
                alt50=1;
                }
                break;
            case 46:
                {
                alt50=2;
                }
                break;
            case 47:
                {
                alt50=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }

            switch (alt50) {
                case 1 :
                    // InternalAlpha.g:3346:3: kw= '*'
                    {
                    kw=(Token)match(input,45,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAISLMultiplicativeOperatorAccess().getAsteriskKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:3352:3: kw= '/'
                    {
                    kw=(Token)match(input,46,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAISLMultiplicativeOperatorAccess().getSolidusKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:3358:3: kw= '%'
                    {
                    kw=(Token)match(input,47,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAISLMultiplicativeOperatorAccess().getPercentSignKeyword_2());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLMultiplicativeOperator"


    // $ANTLR start "entryRuleAISLRelationalOperator"
    // InternalAlpha.g:3367:1: entryRuleAISLRelationalOperator returns [String current=null] : iv_ruleAISLRelationalOperator= ruleAISLRelationalOperator EOF ;
    public final String entryRuleAISLRelationalOperator() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLRelationalOperator = null;


        try {
            // InternalAlpha.g:3367:62: (iv_ruleAISLRelationalOperator= ruleAISLRelationalOperator EOF )
            // InternalAlpha.g:3368:2: iv_ruleAISLRelationalOperator= ruleAISLRelationalOperator EOF
            {
             newCompositeNode(grammarAccess.getAISLRelationalOperatorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLRelationalOperator=ruleAISLRelationalOperator();

            state._fsp--;

             current =iv_ruleAISLRelationalOperator.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLRelationalOperator"


    // $ANTLR start "ruleAISLRelationalOperator"
    // InternalAlpha.g:3374:1: ruleAISLRelationalOperator returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '=' ;
    public final AntlrDatatypeRuleToken ruleAISLRelationalOperator() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:3380:2: (kw= '=' )
            // InternalAlpha.g:3381:2: kw= '='
            {
            kw=(Token)match(input,13,FOLLOW_2); 

            		current.merge(kw);
            		newLeafNode(kw, grammarAccess.getAISLRelationalOperatorAccess().getEqualsSignKeyword());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLRelationalOperator"


    // $ANTLR start "entryRuleAISLExpressionList"
    // InternalAlpha.g:3389:1: entryRuleAISLExpressionList returns [String current=null] : iv_ruleAISLExpressionList= ruleAISLExpressionList EOF ;
    public final String entryRuleAISLExpressionList() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLExpressionList = null;


        try {
            // InternalAlpha.g:3389:58: (iv_ruleAISLExpressionList= ruleAISLExpressionList EOF )
            // InternalAlpha.g:3390:2: iv_ruleAISLExpressionList= ruleAISLExpressionList EOF
            {
             newCompositeNode(grammarAccess.getAISLExpressionListRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLExpressionList=ruleAISLExpressionList();

            state._fsp--;

             current =iv_ruleAISLExpressionList.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLExpressionList"


    // $ANTLR start "ruleAISLExpressionList"
    // InternalAlpha.g:3396:1: ruleAISLExpressionList returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_AISLExpression_0= ruleAISLExpression (kw= ',' this_AISLExpression_2= ruleAISLExpression )* )? ;
    public final AntlrDatatypeRuleToken ruleAISLExpressionList() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AISLExpression_0 = null;

        AntlrDatatypeRuleToken this_AISLExpression_2 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3402:2: ( (this_AISLExpression_0= ruleAISLExpression (kw= ',' this_AISLExpression_2= ruleAISLExpression )* )? )
            // InternalAlpha.g:3403:2: (this_AISLExpression_0= ruleAISLExpression (kw= ',' this_AISLExpression_2= ruleAISLExpression )* )?
            {
            // InternalAlpha.g:3403:2: (this_AISLExpression_0= ruleAISLExpression (kw= ',' this_AISLExpression_2= ruleAISLExpression )* )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=RULE_ID && LA52_0<=RULE_WS)||LA52_0==13||(LA52_0>=15 && LA52_0<=16)||(LA52_0>=43 && LA52_0<=48)) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalAlpha.g:3404:3: this_AISLExpression_0= ruleAISLExpression (kw= ',' this_AISLExpression_2= ruleAISLExpression )*
                    {

                    			newCompositeNode(grammarAccess.getAISLExpressionListAccess().getAISLExpressionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_43);
                    this_AISLExpression_0=ruleAISLExpression();

                    state._fsp--;


                    			current.merge(this_AISLExpression_0);
                    		

                    			afterParserOrEnumRuleCall();
                    		
                    // InternalAlpha.g:3414:3: (kw= ',' this_AISLExpression_2= ruleAISLExpression )*
                    loop51:
                    do {
                        int alt51=2;
                        int LA51_0 = input.LA(1);

                        if ( (LA51_0==24) ) {
                            alt51=1;
                        }


                        switch (alt51) {
                    	case 1 :
                    	    // InternalAlpha.g:3415:4: kw= ',' this_AISLExpression_2= ruleAISLExpression
                    	    {
                    	    kw=(Token)match(input,24,FOLLOW_33); 

                    	    				current.merge(kw);
                    	    				newLeafNode(kw, grammarAccess.getAISLExpressionListAccess().getCommaKeyword_1_0());
                    	    			

                    	    				newCompositeNode(grammarAccess.getAISLExpressionListAccess().getAISLExpressionParserRuleCall_1_1());
                    	    			
                    	    pushFollow(FOLLOW_43);
                    	    this_AISLExpression_2=ruleAISLExpression();

                    	    state._fsp--;


                    	    				current.merge(this_AISLExpression_2);
                    	    			

                    	    				afterParserOrEnumRuleCall();
                    	    			

                    	    }
                    	    break;

                    	default :
                    	    break loop51;
                        }
                    } while (true);


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLExpressionList"


    // $ANTLR start "entryRuleAlphaFunctionTerminalExpression"
    // InternalAlpha.g:3435:1: entryRuleAlphaFunctionTerminalExpression returns [EObject current=null] : iv_ruleAlphaFunctionTerminalExpression= ruleAlphaFunctionTerminalExpression EOF ;
    public final EObject entryRuleAlphaFunctionTerminalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaFunctionTerminalExpression = null;


        try {
            // InternalAlpha.g:3435:72: (iv_ruleAlphaFunctionTerminalExpression= ruleAlphaFunctionTerminalExpression EOF )
            // InternalAlpha.g:3436:2: iv_ruleAlphaFunctionTerminalExpression= ruleAlphaFunctionTerminalExpression EOF
            {
             newCompositeNode(grammarAccess.getAlphaFunctionTerminalExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaFunctionTerminalExpression=ruleAlphaFunctionTerminalExpression();

            state._fsp--;

             current =iv_ruleAlphaFunctionTerminalExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaFunctionTerminalExpression"


    // $ANTLR start "ruleAlphaFunctionTerminalExpression"
    // InternalAlpha.g:3442:1: ruleAlphaFunctionTerminalExpression returns [EObject current=null] : (this_AlphaFunctionLiteral_0= ruleAlphaFunctionLiteral | this_AlphaFunctionFloor_1= ruleAlphaFunctionFloor | (otherlv_2= '(' this_AlphaFunctionAdditiveBinaryExpression_3= ruleAlphaFunctionAdditiveBinaryExpression otherlv_4= ')' ) ) ;
    public final EObject ruleAlphaFunctionTerminalExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_AlphaFunctionLiteral_0 = null;

        EObject this_AlphaFunctionFloor_1 = null;

        EObject this_AlphaFunctionAdditiveBinaryExpression_3 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3448:2: ( (this_AlphaFunctionLiteral_0= ruleAlphaFunctionLiteral | this_AlphaFunctionFloor_1= ruleAlphaFunctionFloor | (otherlv_2= '(' this_AlphaFunctionAdditiveBinaryExpression_3= ruleAlphaFunctionAdditiveBinaryExpression otherlv_4= ')' ) ) )
            // InternalAlpha.g:3449:2: (this_AlphaFunctionLiteral_0= ruleAlphaFunctionLiteral | this_AlphaFunctionFloor_1= ruleAlphaFunctionFloor | (otherlv_2= '(' this_AlphaFunctionAdditiveBinaryExpression_3= ruleAlphaFunctionAdditiveBinaryExpression otherlv_4= ')' ) )
            {
            // InternalAlpha.g:3449:2: (this_AlphaFunctionLiteral_0= ruleAlphaFunctionLiteral | this_AlphaFunctionFloor_1= ruleAlphaFunctionFloor | (otherlv_2= '(' this_AlphaFunctionAdditiveBinaryExpression_3= ruleAlphaFunctionAdditiveBinaryExpression otherlv_4= ')' ) )
            int alt53=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
            case RULE_INT:
            case RULE_WS:
            case 44:
                {
                alt53=1;
                }
                break;
            case 48:
                {
                alt53=2;
                }
                break;
            case 15:
                {
                alt53=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }

            switch (alt53) {
                case 1 :
                    // InternalAlpha.g:3450:3: this_AlphaFunctionLiteral_0= ruleAlphaFunctionLiteral
                    {

                    			newCompositeNode(grammarAccess.getAlphaFunctionTerminalExpressionAccess().getAlphaFunctionLiteralParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_AlphaFunctionLiteral_0=ruleAlphaFunctionLiteral();

                    state._fsp--;


                    			current = this_AlphaFunctionLiteral_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:3459:3: this_AlphaFunctionFloor_1= ruleAlphaFunctionFloor
                    {

                    			newCompositeNode(grammarAccess.getAlphaFunctionTerminalExpressionAccess().getAlphaFunctionFloorParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_AlphaFunctionFloor_1=ruleAlphaFunctionFloor();

                    state._fsp--;


                    			current = this_AlphaFunctionFloor_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:3468:3: (otherlv_2= '(' this_AlphaFunctionAdditiveBinaryExpression_3= ruleAlphaFunctionAdditiveBinaryExpression otherlv_4= ')' )
                    {
                    // InternalAlpha.g:3468:3: (otherlv_2= '(' this_AlphaFunctionAdditiveBinaryExpression_3= ruleAlphaFunctionAdditiveBinaryExpression otherlv_4= ')' )
                    // InternalAlpha.g:3469:4: otherlv_2= '(' this_AlphaFunctionAdditiveBinaryExpression_3= ruleAlphaFunctionAdditiveBinaryExpression otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_53); 

                    				newLeafNode(otherlv_2, grammarAccess.getAlphaFunctionTerminalExpressionAccess().getLeftParenthesisKeyword_2_0());
                    			

                    				newCompositeNode(grammarAccess.getAlphaFunctionTerminalExpressionAccess().getAlphaFunctionAdditiveBinaryExpressionParserRuleCall_2_1());
                    			
                    pushFollow(FOLLOW_9);
                    this_AlphaFunctionAdditiveBinaryExpression_3=ruleAlphaFunctionAdditiveBinaryExpression();

                    state._fsp--;


                    				current = this_AlphaFunctionAdditiveBinaryExpression_3;
                    				afterParserOrEnumRuleCall();
                    			
                    otherlv_4=(Token)match(input,16,FOLLOW_2); 

                    				newLeafNode(otherlv_4, grammarAccess.getAlphaFunctionTerminalExpressionAccess().getRightParenthesisKeyword_2_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaFunctionTerminalExpression"


    // $ANTLR start "entryRuleAlphaFunctionAdditiveBinaryExpression"
    // InternalAlpha.g:3490:1: entryRuleAlphaFunctionAdditiveBinaryExpression returns [EObject current=null] : iv_ruleAlphaFunctionAdditiveBinaryExpression= ruleAlphaFunctionAdditiveBinaryExpression EOF ;
    public final EObject entryRuleAlphaFunctionAdditiveBinaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaFunctionAdditiveBinaryExpression = null;


        try {
            // InternalAlpha.g:3490:78: (iv_ruleAlphaFunctionAdditiveBinaryExpression= ruleAlphaFunctionAdditiveBinaryExpression EOF )
            // InternalAlpha.g:3491:2: iv_ruleAlphaFunctionAdditiveBinaryExpression= ruleAlphaFunctionAdditiveBinaryExpression EOF
            {
             newCompositeNode(grammarAccess.getAlphaFunctionAdditiveBinaryExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaFunctionAdditiveBinaryExpression=ruleAlphaFunctionAdditiveBinaryExpression();

            state._fsp--;

             current =iv_ruleAlphaFunctionAdditiveBinaryExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaFunctionAdditiveBinaryExpression"


    // $ANTLR start "ruleAlphaFunctionAdditiveBinaryExpression"
    // InternalAlpha.g:3497:1: ruleAlphaFunctionAdditiveBinaryExpression returns [EObject current=null] : (this_AlphaFunctionMultiplicativeBinaryExpression_0= ruleAlphaFunctionMultiplicativeBinaryExpression ( () ( (lv_operator_2_0= ruleAISLAdditiveOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression ) ) )* ) ;
    public final EObject ruleAlphaFunctionAdditiveBinaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_AlphaFunctionMultiplicativeBinaryExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3503:2: ( (this_AlphaFunctionMultiplicativeBinaryExpression_0= ruleAlphaFunctionMultiplicativeBinaryExpression ( () ( (lv_operator_2_0= ruleAISLAdditiveOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression ) ) )* ) )
            // InternalAlpha.g:3504:2: (this_AlphaFunctionMultiplicativeBinaryExpression_0= ruleAlphaFunctionMultiplicativeBinaryExpression ( () ( (lv_operator_2_0= ruleAISLAdditiveOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression ) ) )* )
            {
            // InternalAlpha.g:3504:2: (this_AlphaFunctionMultiplicativeBinaryExpression_0= ruleAlphaFunctionMultiplicativeBinaryExpression ( () ( (lv_operator_2_0= ruleAISLAdditiveOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression ) ) )* )
            // InternalAlpha.g:3505:3: this_AlphaFunctionMultiplicativeBinaryExpression_0= ruleAlphaFunctionMultiplicativeBinaryExpression ( () ( (lv_operator_2_0= ruleAISLAdditiveOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getAlphaFunctionAdditiveBinaryExpressionAccess().getAlphaFunctionMultiplicativeBinaryExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_54);
            this_AlphaFunctionMultiplicativeBinaryExpression_0=ruleAlphaFunctionMultiplicativeBinaryExpression();

            state._fsp--;


            			current = this_AlphaFunctionMultiplicativeBinaryExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:3513:3: ( () ( (lv_operator_2_0= ruleAISLAdditiveOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression ) ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=43 && LA54_0<=44)) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalAlpha.g:3514:4: () ( (lv_operator_2_0= ruleAISLAdditiveOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression ) )
            	    {
            	    // InternalAlpha.g:3514:4: ()
            	    // InternalAlpha.g:3515:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAlphaFunctionAdditiveBinaryExpressionAccess().getAlphaFunctionBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:3521:4: ( (lv_operator_2_0= ruleAISLAdditiveOperator ) )
            	    // InternalAlpha.g:3522:5: (lv_operator_2_0= ruleAISLAdditiveOperator )
            	    {
            	    // InternalAlpha.g:3522:5: (lv_operator_2_0= ruleAISLAdditiveOperator )
            	    // InternalAlpha.g:3523:6: lv_operator_2_0= ruleAISLAdditiveOperator
            	    {

            	    						newCompositeNode(grammarAccess.getAlphaFunctionAdditiveBinaryExpressionAccess().getOperatorAISLAdditiveOperatorParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_53);
            	    lv_operator_2_0=ruleAISLAdditiveOperator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAlphaFunctionAdditiveBinaryExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.AISLAdditiveOperator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:3540:4: ( (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression ) )
            	    // InternalAlpha.g:3541:5: (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression )
            	    {
            	    // InternalAlpha.g:3541:5: (lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression )
            	    // InternalAlpha.g:3542:6: lv_right_3_0= ruleAlphaFunctionMultiplicativeBinaryExpression
            	    {

            	    						newCompositeNode(grammarAccess.getAlphaFunctionAdditiveBinaryExpressionAccess().getRightAlphaFunctionMultiplicativeBinaryExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_54);
            	    lv_right_3_0=ruleAlphaFunctionMultiplicativeBinaryExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAlphaFunctionAdditiveBinaryExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.AlphaFunctionMultiplicativeBinaryExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaFunctionAdditiveBinaryExpression"


    // $ANTLR start "entryRuleAlphaFunctionMultiplicativeBinaryExpression"
    // InternalAlpha.g:3564:1: entryRuleAlphaFunctionMultiplicativeBinaryExpression returns [EObject current=null] : iv_ruleAlphaFunctionMultiplicativeBinaryExpression= ruleAlphaFunctionMultiplicativeBinaryExpression EOF ;
    public final EObject entryRuleAlphaFunctionMultiplicativeBinaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaFunctionMultiplicativeBinaryExpression = null;


        try {
            // InternalAlpha.g:3564:84: (iv_ruleAlphaFunctionMultiplicativeBinaryExpression= ruleAlphaFunctionMultiplicativeBinaryExpression EOF )
            // InternalAlpha.g:3565:2: iv_ruleAlphaFunctionMultiplicativeBinaryExpression= ruleAlphaFunctionMultiplicativeBinaryExpression EOF
            {
             newCompositeNode(grammarAccess.getAlphaFunctionMultiplicativeBinaryExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaFunctionMultiplicativeBinaryExpression=ruleAlphaFunctionMultiplicativeBinaryExpression();

            state._fsp--;

             current =iv_ruleAlphaFunctionMultiplicativeBinaryExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaFunctionMultiplicativeBinaryExpression"


    // $ANTLR start "ruleAlphaFunctionMultiplicativeBinaryExpression"
    // InternalAlpha.g:3571:1: ruleAlphaFunctionMultiplicativeBinaryExpression returns [EObject current=null] : (this_AlphaFunctionRelationalBinaryExpression_0= ruleAlphaFunctionRelationalBinaryExpression ( () ( (lv_operator_2_0= ruleAISLMultiplicativeOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression ) ) )* ) ;
    public final EObject ruleAlphaFunctionMultiplicativeBinaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_AlphaFunctionRelationalBinaryExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3577:2: ( (this_AlphaFunctionRelationalBinaryExpression_0= ruleAlphaFunctionRelationalBinaryExpression ( () ( (lv_operator_2_0= ruleAISLMultiplicativeOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression ) ) )* ) )
            // InternalAlpha.g:3578:2: (this_AlphaFunctionRelationalBinaryExpression_0= ruleAlphaFunctionRelationalBinaryExpression ( () ( (lv_operator_2_0= ruleAISLMultiplicativeOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression ) ) )* )
            {
            // InternalAlpha.g:3578:2: (this_AlphaFunctionRelationalBinaryExpression_0= ruleAlphaFunctionRelationalBinaryExpression ( () ( (lv_operator_2_0= ruleAISLMultiplicativeOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression ) ) )* )
            // InternalAlpha.g:3579:3: this_AlphaFunctionRelationalBinaryExpression_0= ruleAlphaFunctionRelationalBinaryExpression ( () ( (lv_operator_2_0= ruleAISLMultiplicativeOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getAlphaFunctionMultiplicativeBinaryExpressionAccess().getAlphaFunctionRelationalBinaryExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_55);
            this_AlphaFunctionRelationalBinaryExpression_0=ruleAlphaFunctionRelationalBinaryExpression();

            state._fsp--;


            			current = this_AlphaFunctionRelationalBinaryExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:3587:3: ( () ( (lv_operator_2_0= ruleAISLMultiplicativeOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression ) ) )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( ((LA55_0>=45 && LA55_0<=47)) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalAlpha.g:3588:4: () ( (lv_operator_2_0= ruleAISLMultiplicativeOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression ) )
            	    {
            	    // InternalAlpha.g:3588:4: ()
            	    // InternalAlpha.g:3589:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAlphaFunctionMultiplicativeBinaryExpressionAccess().getAlphaFunctionBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:3595:4: ( (lv_operator_2_0= ruleAISLMultiplicativeOperator ) )
            	    // InternalAlpha.g:3596:5: (lv_operator_2_0= ruleAISLMultiplicativeOperator )
            	    {
            	    // InternalAlpha.g:3596:5: (lv_operator_2_0= ruleAISLMultiplicativeOperator )
            	    // InternalAlpha.g:3597:6: lv_operator_2_0= ruleAISLMultiplicativeOperator
            	    {

            	    						newCompositeNode(grammarAccess.getAlphaFunctionMultiplicativeBinaryExpressionAccess().getOperatorAISLMultiplicativeOperatorParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_53);
            	    lv_operator_2_0=ruleAISLMultiplicativeOperator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAlphaFunctionMultiplicativeBinaryExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.AISLMultiplicativeOperator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:3614:4: ( (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression ) )
            	    // InternalAlpha.g:3615:5: (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression )
            	    {
            	    // InternalAlpha.g:3615:5: (lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression )
            	    // InternalAlpha.g:3616:6: lv_right_3_0= ruleAlphaFunctionRelationalBinaryExpression
            	    {

            	    						newCompositeNode(grammarAccess.getAlphaFunctionMultiplicativeBinaryExpressionAccess().getRightAlphaFunctionRelationalBinaryExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_55);
            	    lv_right_3_0=ruleAlphaFunctionRelationalBinaryExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAlphaFunctionMultiplicativeBinaryExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.AlphaFunctionRelationalBinaryExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaFunctionMultiplicativeBinaryExpression"


    // $ANTLR start "entryRuleAlphaFunctionRelationalBinaryExpression"
    // InternalAlpha.g:3638:1: entryRuleAlphaFunctionRelationalBinaryExpression returns [EObject current=null] : iv_ruleAlphaFunctionRelationalBinaryExpression= ruleAlphaFunctionRelationalBinaryExpression EOF ;
    public final EObject entryRuleAlphaFunctionRelationalBinaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaFunctionRelationalBinaryExpression = null;


        try {
            // InternalAlpha.g:3638:80: (iv_ruleAlphaFunctionRelationalBinaryExpression= ruleAlphaFunctionRelationalBinaryExpression EOF )
            // InternalAlpha.g:3639:2: iv_ruleAlphaFunctionRelationalBinaryExpression= ruleAlphaFunctionRelationalBinaryExpression EOF
            {
             newCompositeNode(grammarAccess.getAlphaFunctionRelationalBinaryExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaFunctionRelationalBinaryExpression=ruleAlphaFunctionRelationalBinaryExpression();

            state._fsp--;

             current =iv_ruleAlphaFunctionRelationalBinaryExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaFunctionRelationalBinaryExpression"


    // $ANTLR start "ruleAlphaFunctionRelationalBinaryExpression"
    // InternalAlpha.g:3645:1: ruleAlphaFunctionRelationalBinaryExpression returns [EObject current=null] : (this_AlphaFunctionTerminalExpression_0= ruleAlphaFunctionTerminalExpression ( () ( (lv_operator_2_0= ruleAISLRelationalOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionTerminalExpression ) ) )* ) ;
    public final EObject ruleAlphaFunctionRelationalBinaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_AlphaFunctionTerminalExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3651:2: ( (this_AlphaFunctionTerminalExpression_0= ruleAlphaFunctionTerminalExpression ( () ( (lv_operator_2_0= ruleAISLRelationalOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionTerminalExpression ) ) )* ) )
            // InternalAlpha.g:3652:2: (this_AlphaFunctionTerminalExpression_0= ruleAlphaFunctionTerminalExpression ( () ( (lv_operator_2_0= ruleAISLRelationalOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionTerminalExpression ) ) )* )
            {
            // InternalAlpha.g:3652:2: (this_AlphaFunctionTerminalExpression_0= ruleAlphaFunctionTerminalExpression ( () ( (lv_operator_2_0= ruleAISLRelationalOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionTerminalExpression ) ) )* )
            // InternalAlpha.g:3653:3: this_AlphaFunctionTerminalExpression_0= ruleAlphaFunctionTerminalExpression ( () ( (lv_operator_2_0= ruleAISLRelationalOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionTerminalExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getAlphaFunctionRelationalBinaryExpressionAccess().getAlphaFunctionTerminalExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_56);
            this_AlphaFunctionTerminalExpression_0=ruleAlphaFunctionTerminalExpression();

            state._fsp--;


            			current = this_AlphaFunctionTerminalExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:3661:3: ( () ( (lv_operator_2_0= ruleAISLRelationalOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionTerminalExpression ) ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==13) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalAlpha.g:3662:4: () ( (lv_operator_2_0= ruleAISLRelationalOperator ) ) ( (lv_right_3_0= ruleAlphaFunctionTerminalExpression ) )
            	    {
            	    // InternalAlpha.g:3662:4: ()
            	    // InternalAlpha.g:3663:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAlphaFunctionRelationalBinaryExpressionAccess().getAlphaFunctionBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:3669:4: ( (lv_operator_2_0= ruleAISLRelationalOperator ) )
            	    // InternalAlpha.g:3670:5: (lv_operator_2_0= ruleAISLRelationalOperator )
            	    {
            	    // InternalAlpha.g:3670:5: (lv_operator_2_0= ruleAISLRelationalOperator )
            	    // InternalAlpha.g:3671:6: lv_operator_2_0= ruleAISLRelationalOperator
            	    {

            	    						newCompositeNode(grammarAccess.getAlphaFunctionRelationalBinaryExpressionAccess().getOperatorAISLRelationalOperatorParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_53);
            	    lv_operator_2_0=ruleAISLRelationalOperator();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAlphaFunctionRelationalBinaryExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.AISLRelationalOperator");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:3688:4: ( (lv_right_3_0= ruleAlphaFunctionTerminalExpression ) )
            	    // InternalAlpha.g:3689:5: (lv_right_3_0= ruleAlphaFunctionTerminalExpression )
            	    {
            	    // InternalAlpha.g:3689:5: (lv_right_3_0= ruleAlphaFunctionTerminalExpression )
            	    // InternalAlpha.g:3690:6: lv_right_3_0= ruleAlphaFunctionTerminalExpression
            	    {

            	    						newCompositeNode(grammarAccess.getAlphaFunctionRelationalBinaryExpressionAccess().getRightAlphaFunctionTerminalExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_56);
            	    lv_right_3_0=ruleAlphaFunctionTerminalExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAlphaFunctionRelationalBinaryExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.AlphaFunctionTerminalExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaFunctionRelationalBinaryExpression"


    // $ANTLR start "entryRuleAlphaFunctionFloor"
    // InternalAlpha.g:3712:1: entryRuleAlphaFunctionFloor returns [EObject current=null] : iv_ruleAlphaFunctionFloor= ruleAlphaFunctionFloor EOF ;
    public final EObject entryRuleAlphaFunctionFloor() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaFunctionFloor = null;


        try {
            // InternalAlpha.g:3712:59: (iv_ruleAlphaFunctionFloor= ruleAlphaFunctionFloor EOF )
            // InternalAlpha.g:3713:2: iv_ruleAlphaFunctionFloor= ruleAlphaFunctionFloor EOF
            {
             newCompositeNode(grammarAccess.getAlphaFunctionFloorRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaFunctionFloor=ruleAlphaFunctionFloor();

            state._fsp--;

             current =iv_ruleAlphaFunctionFloor; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaFunctionFloor"


    // $ANTLR start "ruleAlphaFunctionFloor"
    // InternalAlpha.g:3719:1: ruleAlphaFunctionFloor returns [EObject current=null] : ( () otherlv_1= 'floor' otherlv_2= '(' ( (lv_expr_3_0= ruleAlphaFunctionExpression ) ) otherlv_4= ')' ) ;
    public final EObject ruleAlphaFunctionFloor() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_expr_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3725:2: ( ( () otherlv_1= 'floor' otherlv_2= '(' ( (lv_expr_3_0= ruleAlphaFunctionExpression ) ) otherlv_4= ')' ) )
            // InternalAlpha.g:3726:2: ( () otherlv_1= 'floor' otherlv_2= '(' ( (lv_expr_3_0= ruleAlphaFunctionExpression ) ) otherlv_4= ')' )
            {
            // InternalAlpha.g:3726:2: ( () otherlv_1= 'floor' otherlv_2= '(' ( (lv_expr_3_0= ruleAlphaFunctionExpression ) ) otherlv_4= ')' )
            // InternalAlpha.g:3727:3: () otherlv_1= 'floor' otherlv_2= '(' ( (lv_expr_3_0= ruleAlphaFunctionExpression ) ) otherlv_4= ')'
            {
            // InternalAlpha.g:3727:3: ()
            // InternalAlpha.g:3728:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getAlphaFunctionFloorAccess().getAlphaFunctionFloorAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,48,FOLLOW_8); 

            			newLeafNode(otherlv_1, grammarAccess.getAlphaFunctionFloorAccess().getFloorKeyword_1());
            		
            otherlv_2=(Token)match(input,15,FOLLOW_53); 

            			newLeafNode(otherlv_2, grammarAccess.getAlphaFunctionFloorAccess().getLeftParenthesisKeyword_2());
            		
            // InternalAlpha.g:3742:3: ( (lv_expr_3_0= ruleAlphaFunctionExpression ) )
            // InternalAlpha.g:3743:4: (lv_expr_3_0= ruleAlphaFunctionExpression )
            {
            // InternalAlpha.g:3743:4: (lv_expr_3_0= ruleAlphaFunctionExpression )
            // InternalAlpha.g:3744:5: lv_expr_3_0= ruleAlphaFunctionExpression
            {

            					newCompositeNode(grammarAccess.getAlphaFunctionFloorAccess().getExprAlphaFunctionExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_9);
            lv_expr_3_0=ruleAlphaFunctionExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAlphaFunctionFloorRule());
            					}
            					set(
            						current,
            						"expr",
            						lv_expr_3_0,
            						"alpha.model.Alpha.AlphaFunctionExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getAlphaFunctionFloorAccess().getRightParenthesisKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaFunctionFloor"


    // $ANTLR start "entryRuleAlphaFunctionLiteral"
    // InternalAlpha.g:3769:1: entryRuleAlphaFunctionLiteral returns [EObject current=null] : iv_ruleAlphaFunctionLiteral= ruleAlphaFunctionLiteral EOF ;
    public final EObject entryRuleAlphaFunctionLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaFunctionLiteral = null;


        try {
            // InternalAlpha.g:3769:61: (iv_ruleAlphaFunctionLiteral= ruleAlphaFunctionLiteral EOF )
            // InternalAlpha.g:3770:2: iv_ruleAlphaFunctionLiteral= ruleAlphaFunctionLiteral EOF
            {
             newCompositeNode(grammarAccess.getAlphaFunctionLiteralRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaFunctionLiteral=ruleAlphaFunctionLiteral();

            state._fsp--;

             current =iv_ruleAlphaFunctionLiteral; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaFunctionLiteral"


    // $ANTLR start "ruleAlphaFunctionLiteral"
    // InternalAlpha.g:3776:1: ruleAlphaFunctionLiteral returns [EObject current=null] : ( () ( (lv_value_1_0= ruleAISLExpressionLiteral ) ) ) ;
    public final EObject ruleAlphaFunctionLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3782:2: ( ( () ( (lv_value_1_0= ruleAISLExpressionLiteral ) ) ) )
            // InternalAlpha.g:3783:2: ( () ( (lv_value_1_0= ruleAISLExpressionLiteral ) ) )
            {
            // InternalAlpha.g:3783:2: ( () ( (lv_value_1_0= ruleAISLExpressionLiteral ) ) )
            // InternalAlpha.g:3784:3: () ( (lv_value_1_0= ruleAISLExpressionLiteral ) )
            {
            // InternalAlpha.g:3784:3: ()
            // InternalAlpha.g:3785:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getAlphaFunctionLiteralAccess().getAlphaFunctionLiteralAction_0(),
            					current);
            			

            }

            // InternalAlpha.g:3791:3: ( (lv_value_1_0= ruleAISLExpressionLiteral ) )
            // InternalAlpha.g:3792:4: (lv_value_1_0= ruleAISLExpressionLiteral )
            {
            // InternalAlpha.g:3792:4: (lv_value_1_0= ruleAISLExpressionLiteral )
            // InternalAlpha.g:3793:5: lv_value_1_0= ruleAISLExpressionLiteral
            {

            					newCompositeNode(grammarAccess.getAlphaFunctionLiteralAccess().getValueAISLExpressionLiteralParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_1_0=ruleAISLExpressionLiteral();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAlphaFunctionLiteralRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_1_0,
            						"alpha.model.Alpha.AISLExpressionLiteral");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaFunctionLiteral"


    // $ANTLR start "entryRuleAlphaFunctionExpression"
    // InternalAlpha.g:3814:1: entryRuleAlphaFunctionExpression returns [EObject current=null] : iv_ruleAlphaFunctionExpression= ruleAlphaFunctionExpression EOF ;
    public final EObject entryRuleAlphaFunctionExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaFunctionExpression = null;


        try {
            // InternalAlpha.g:3814:64: (iv_ruleAlphaFunctionExpression= ruleAlphaFunctionExpression EOF )
            // InternalAlpha.g:3815:2: iv_ruleAlphaFunctionExpression= ruleAlphaFunctionExpression EOF
            {
             newCompositeNode(grammarAccess.getAlphaFunctionExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaFunctionExpression=ruleAlphaFunctionExpression();

            state._fsp--;

             current =iv_ruleAlphaFunctionExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaFunctionExpression"


    // $ANTLR start "ruleAlphaFunctionExpression"
    // InternalAlpha.g:3821:1: ruleAlphaFunctionExpression returns [EObject current=null] : this_AlphaFunctionAdditiveBinaryExpression_0= ruleAlphaFunctionAdditiveBinaryExpression ;
    public final EObject ruleAlphaFunctionExpression() throws RecognitionException {
        EObject current = null;

        EObject this_AlphaFunctionAdditiveBinaryExpression_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3827:2: (this_AlphaFunctionAdditiveBinaryExpression_0= ruleAlphaFunctionAdditiveBinaryExpression )
            // InternalAlpha.g:3828:2: this_AlphaFunctionAdditiveBinaryExpression_0= ruleAlphaFunctionAdditiveBinaryExpression
            {

            		newCompositeNode(grammarAccess.getAlphaFunctionExpressionAccess().getAlphaFunctionAdditiveBinaryExpressionParserRuleCall());
            	
            pushFollow(FOLLOW_2);
            this_AlphaFunctionAdditiveBinaryExpression_0=ruleAlphaFunctionAdditiveBinaryExpression();

            state._fsp--;


            		current = this_AlphaFunctionAdditiveBinaryExpression_0;
            		afterParserOrEnumRuleCall();
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaFunctionExpression"


    // $ANTLR start "entryRuleAlphaFunction"
    // InternalAlpha.g:3839:1: entryRuleAlphaFunction returns [EObject current=null] : iv_ruleAlphaFunction= ruleAlphaFunction EOF ;
    public final EObject entryRuleAlphaFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaFunction = null;


        try {
            // InternalAlpha.g:3839:54: (iv_ruleAlphaFunction= ruleAlphaFunction EOF )
            // InternalAlpha.g:3840:2: iv_ruleAlphaFunction= ruleAlphaFunction EOF
            {
             newCompositeNode(grammarAccess.getAlphaFunctionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaFunction=ruleAlphaFunction();

            state._fsp--;

             current =iv_ruleAlphaFunction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaFunction"


    // $ANTLR start "ruleAlphaFunction"
    // InternalAlpha.g:3846:1: ruleAlphaFunction returns [EObject current=null] : (otherlv_0= '(' ( (lv_indexList_1_0= ruleAIndexList ) ) otherlv_2= '->' ( ( (lv_exprs_3_0= ruleAlphaFunctionExpression ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) ) )* )? otherlv_6= ')' ) ;
    public final EObject ruleAlphaFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_indexList_1_0 = null;

        EObject lv_exprs_3_0 = null;

        EObject lv_exprs_5_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3852:2: ( (otherlv_0= '(' ( (lv_indexList_1_0= ruleAIndexList ) ) otherlv_2= '->' ( ( (lv_exprs_3_0= ruleAlphaFunctionExpression ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) ) )* )? otherlv_6= ')' ) )
            // InternalAlpha.g:3853:2: (otherlv_0= '(' ( (lv_indexList_1_0= ruleAIndexList ) ) otherlv_2= '->' ( ( (lv_exprs_3_0= ruleAlphaFunctionExpression ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) ) )* )? otherlv_6= ')' )
            {
            // InternalAlpha.g:3853:2: (otherlv_0= '(' ( (lv_indexList_1_0= ruleAIndexList ) ) otherlv_2= '->' ( ( (lv_exprs_3_0= ruleAlphaFunctionExpression ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) ) )* )? otherlv_6= ')' )
            // InternalAlpha.g:3854:3: otherlv_0= '(' ( (lv_indexList_1_0= ruleAIndexList ) ) otherlv_2= '->' ( ( (lv_exprs_3_0= ruleAlphaFunctionExpression ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) ) )* )? otherlv_6= ')'
            {
            otherlv_0=(Token)match(input,15,FOLLOW_57); 

            			newLeafNode(otherlv_0, grammarAccess.getAlphaFunctionAccess().getLeftParenthesisKeyword_0());
            		
            // InternalAlpha.g:3858:3: ( (lv_indexList_1_0= ruleAIndexList ) )
            // InternalAlpha.g:3859:4: (lv_indexList_1_0= ruleAIndexList )
            {
            // InternalAlpha.g:3859:4: (lv_indexList_1_0= ruleAIndexList )
            // InternalAlpha.g:3860:5: lv_indexList_1_0= ruleAIndexList
            {

            					newCompositeNode(grammarAccess.getAlphaFunctionAccess().getIndexListAIndexListParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_28);
            lv_indexList_1_0=ruleAIndexList();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAlphaFunctionRule());
            					}
            					set(
            						current,
            						"indexList",
            						lv_indexList_1_0,
            						"alpha.model.Alpha.AIndexList");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,36,FOLLOW_58); 

            			newLeafNode(otherlv_2, grammarAccess.getAlphaFunctionAccess().getHyphenMinusGreaterThanSignKeyword_2());
            		
            // InternalAlpha.g:3881:3: ( ( (lv_exprs_3_0= ruleAlphaFunctionExpression ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) ) )* )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( ((LA58_0>=RULE_ID && LA58_0<=RULE_WS)||LA58_0==15||LA58_0==44||LA58_0==48) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // InternalAlpha.g:3882:4: ( (lv_exprs_3_0= ruleAlphaFunctionExpression ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) ) )*
                    {
                    // InternalAlpha.g:3882:4: ( (lv_exprs_3_0= ruleAlphaFunctionExpression ) )
                    // InternalAlpha.g:3883:5: (lv_exprs_3_0= ruleAlphaFunctionExpression )
                    {
                    // InternalAlpha.g:3883:5: (lv_exprs_3_0= ruleAlphaFunctionExpression )
                    // InternalAlpha.g:3884:6: lv_exprs_3_0= ruleAlphaFunctionExpression
                    {

                    						newCompositeNode(grammarAccess.getAlphaFunctionAccess().getExprsAlphaFunctionExpressionParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_59);
                    lv_exprs_3_0=ruleAlphaFunctionExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAlphaFunctionRule());
                    						}
                    						add(
                    							current,
                    							"exprs",
                    							lv_exprs_3_0,
                    							"alpha.model.Alpha.AlphaFunctionExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:3901:4: (otherlv_4= ',' ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) ) )*
                    loop57:
                    do {
                        int alt57=2;
                        int LA57_0 = input.LA(1);

                        if ( (LA57_0==24) ) {
                            alt57=1;
                        }


                        switch (alt57) {
                    	case 1 :
                    	    // InternalAlpha.g:3902:5: otherlv_4= ',' ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) )
                    	    {
                    	    otherlv_4=(Token)match(input,24,FOLLOW_53); 

                    	    					newLeafNode(otherlv_4, grammarAccess.getAlphaFunctionAccess().getCommaKeyword_3_1_0());
                    	    				
                    	    // InternalAlpha.g:3906:5: ( (lv_exprs_5_0= ruleAlphaFunctionExpression ) )
                    	    // InternalAlpha.g:3907:6: (lv_exprs_5_0= ruleAlphaFunctionExpression )
                    	    {
                    	    // InternalAlpha.g:3907:6: (lv_exprs_5_0= ruleAlphaFunctionExpression )
                    	    // InternalAlpha.g:3908:7: lv_exprs_5_0= ruleAlphaFunctionExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getAlphaFunctionAccess().getExprsAlphaFunctionExpressionParserRuleCall_3_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_59);
                    	    lv_exprs_5_0=ruleAlphaFunctionExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getAlphaFunctionRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"exprs",
                    	    								lv_exprs_5_0,
                    	    								"alpha.model.Alpha.AlphaFunctionExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop57;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getAlphaFunctionAccess().getRightParenthesisKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaFunction"


    // $ANTLR start "entryRuleAISLBasicRelation"
    // InternalAlpha.g:3935:1: entryRuleAISLBasicRelation returns [String current=null] : iv_ruleAISLBasicRelation= ruleAISLBasicRelation EOF ;
    public final String entryRuleAISLBasicRelation() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLBasicRelation = null;


        try {
            // InternalAlpha.g:3935:57: (iv_ruleAISLBasicRelation= ruleAISLBasicRelation EOF )
            // InternalAlpha.g:3936:2: iv_ruleAISLBasicRelation= ruleAISLBasicRelation EOF
            {
             newCompositeNode(grammarAccess.getAISLBasicRelationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLBasicRelation=ruleAISLBasicRelation();

            state._fsp--;

             current =iv_ruleAISLBasicRelation.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLBasicRelation"


    // $ANTLR start "ruleAISLBasicRelation"
    // InternalAlpha.g:3942:1: ruleAISLBasicRelation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_5= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_8= ruleAISLString ) ;
    public final AntlrDatatypeRuleToken ruleAISLBasicRelation() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AISLExpressionList_1 = null;

        AntlrDatatypeRuleToken this_AISLExpressionList_5 = null;

        AntlrDatatypeRuleToken this_AISLString_8 = null;



        	enterRule();

        try {
            // InternalAlpha.g:3948:2: ( (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_5= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_8= ruleAISLString ) )
            // InternalAlpha.g:3949:2: (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_5= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_8= ruleAISLString )
            {
            // InternalAlpha.g:3949:2: (kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_5= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_8= ruleAISLString )
            // InternalAlpha.g:3950:3: kw= '[' this_AISLExpressionList_1= ruleAISLExpressionList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_5= ruleAISLExpressionList kw= ']' kw= ':' this_AISLString_8= ruleAISLString
            {
            kw=(Token)match(input,37,FOLLOW_31); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLBasicRelationAccess().getLeftSquareBracketKeyword_0());
            		

            			newCompositeNode(grammarAccess.getAISLBasicRelationAccess().getAISLExpressionListParserRuleCall_1());
            		
            pushFollow(FOLLOW_45);
            this_AISLExpressionList_1=ruleAISLExpressionList();

            state._fsp--;


            			current.merge(this_AISLExpressionList_1);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,38,FOLLOW_28); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLBasicRelationAccess().getRightSquareBracketKeyword_2());
            		
            kw=(Token)match(input,36,FOLLOW_36); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLBasicRelationAccess().getHyphenMinusGreaterThanSignKeyword_3());
            		
            kw=(Token)match(input,37,FOLLOW_31); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLBasicRelationAccess().getLeftSquareBracketKeyword_4());
            		

            			newCompositeNode(grammarAccess.getAISLBasicRelationAccess().getAISLExpressionListParserRuleCall_5());
            		
            pushFollow(FOLLOW_45);
            this_AISLExpressionList_5=ruleAISLExpressionList();

            state._fsp--;


            			current.merge(this_AISLExpressionList_5);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,38,FOLLOW_26); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLBasicRelationAccess().getRightSquareBracketKeyword_6());
            		
            kw=(Token)match(input,33,FOLLOW_46); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLBasicRelationAccess().getColonKeyword_7());
            		

            			newCompositeNode(grammarAccess.getAISLBasicRelationAccess().getAISLStringParserRuleCall_8());
            		
            pushFollow(FOLLOW_2);
            this_AISLString_8=ruleAISLString();

            state._fsp--;


            			current.merge(this_AISLString_8);
            		

            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLBasicRelation"


    // $ANTLR start "entryRuleAISLWrappedBasicRelation"
    // InternalAlpha.g:4014:1: entryRuleAISLWrappedBasicRelation returns [String current=null] : iv_ruleAISLWrappedBasicRelation= ruleAISLWrappedBasicRelation EOF ;
    public final String entryRuleAISLWrappedBasicRelation() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLWrappedBasicRelation = null;


        try {
            // InternalAlpha.g:4014:64: (iv_ruleAISLWrappedBasicRelation= ruleAISLWrappedBasicRelation EOF )
            // InternalAlpha.g:4015:2: iv_ruleAISLWrappedBasicRelation= ruleAISLWrappedBasicRelation EOF
            {
             newCompositeNode(grammarAccess.getAISLWrappedBasicRelationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLWrappedBasicRelation=ruleAISLWrappedBasicRelation();

            state._fsp--;

             current =iv_ruleAISLWrappedBasicRelation.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLWrappedBasicRelation"


    // $ANTLR start "ruleAISLWrappedBasicRelation"
    // InternalAlpha.g:4021:1: ruleAISLWrappedBasicRelation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' kw= '[' this_AIndexList_2= ruleAIndexList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_6= ruleAISLExpressionList kw= ']' kw= ']' kw= '->' kw= '[' this_AISLExpressionList_11= ruleAISLExpressionList kw= ']' ) ;
    public final AntlrDatatypeRuleToken ruleAISLWrappedBasicRelation() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_AIndexList_2 = null;

        AntlrDatatypeRuleToken this_AISLExpressionList_6 = null;

        AntlrDatatypeRuleToken this_AISLExpressionList_11 = null;



        	enterRule();

        try {
            // InternalAlpha.g:4027:2: ( (kw= '[' kw= '[' this_AIndexList_2= ruleAIndexList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_6= ruleAISLExpressionList kw= ']' kw= ']' kw= '->' kw= '[' this_AISLExpressionList_11= ruleAISLExpressionList kw= ']' ) )
            // InternalAlpha.g:4028:2: (kw= '[' kw= '[' this_AIndexList_2= ruleAIndexList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_6= ruleAISLExpressionList kw= ']' kw= ']' kw= '->' kw= '[' this_AISLExpressionList_11= ruleAISLExpressionList kw= ']' )
            {
            // InternalAlpha.g:4028:2: (kw= '[' kw= '[' this_AIndexList_2= ruleAIndexList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_6= ruleAISLExpressionList kw= ']' kw= ']' kw= '->' kw= '[' this_AISLExpressionList_11= ruleAISLExpressionList kw= ']' )
            // InternalAlpha.g:4029:3: kw= '[' kw= '[' this_AIndexList_2= ruleAIndexList kw= ']' kw= '->' kw= '[' this_AISLExpressionList_6= ruleAISLExpressionList kw= ']' kw= ']' kw= '->' kw= '[' this_AISLExpressionList_11= ruleAISLExpressionList kw= ']'
            {
            kw=(Token)match(input,37,FOLLOW_36); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getLeftSquareBracketKeyword_0());
            		
            kw=(Token)match(input,37,FOLLOW_44); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getLeftSquareBracketKeyword_1());
            		

            			newCompositeNode(grammarAccess.getAISLWrappedBasicRelationAccess().getAIndexListParserRuleCall_2());
            		
            pushFollow(FOLLOW_45);
            this_AIndexList_2=ruleAIndexList();

            state._fsp--;


            			current.merge(this_AIndexList_2);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,38,FOLLOW_28); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getRightSquareBracketKeyword_3());
            		
            kw=(Token)match(input,36,FOLLOW_36); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getHyphenMinusGreaterThanSignKeyword_4());
            		
            kw=(Token)match(input,37,FOLLOW_31); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getLeftSquareBracketKeyword_5());
            		

            			newCompositeNode(grammarAccess.getAISLWrappedBasicRelationAccess().getAISLExpressionListParserRuleCall_6());
            		
            pushFollow(FOLLOW_45);
            this_AISLExpressionList_6=ruleAISLExpressionList();

            state._fsp--;


            			current.merge(this_AISLExpressionList_6);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,38,FOLLOW_45); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getRightSquareBracketKeyword_7());
            		
            kw=(Token)match(input,38,FOLLOW_28); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getRightSquareBracketKeyword_8());
            		
            kw=(Token)match(input,36,FOLLOW_36); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getHyphenMinusGreaterThanSignKeyword_9());
            		
            kw=(Token)match(input,37,FOLLOW_31); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getLeftSquareBracketKeyword_10());
            		

            			newCompositeNode(grammarAccess.getAISLWrappedBasicRelationAccess().getAISLExpressionListParserRuleCall_11());
            		
            pushFollow(FOLLOW_45);
            this_AISLExpressionList_11=ruleAISLExpressionList();

            state._fsp--;


            			current.merge(this_AISLExpressionList_11);
            		

            			afterParserOrEnumRuleCall();
            		
            kw=(Token)match(input,38,FOLLOW_2); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getAISLWrappedBasicRelationAccess().getRightSquareBracketKeyword_12());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLWrappedBasicRelation"


    // $ANTLR start "entryRuleAISLFuzzyExpression"
    // InternalAlpha.g:4113:1: entryRuleAISLFuzzyExpression returns [String current=null] : iv_ruleAISLFuzzyExpression= ruleAISLFuzzyExpression EOF ;
    public final String entryRuleAISLFuzzyExpression() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAISLFuzzyExpression = null;


        try {
            // InternalAlpha.g:4113:59: (iv_ruleAISLFuzzyExpression= ruleAISLFuzzyExpression EOF )
            // InternalAlpha.g:4114:2: iv_ruleAISLFuzzyExpression= ruleAISLFuzzyExpression EOF
            {
             newCompositeNode(grammarAccess.getAISLFuzzyExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAISLFuzzyExpression=ruleAISLFuzzyExpression();

            state._fsp--;

             current =iv_ruleAISLFuzzyExpression.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAISLFuzzyExpression"


    // $ANTLR start "ruleAISLFuzzyExpression"
    // InternalAlpha.g:4120:1: ruleAISLFuzzyExpression returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '[' | kw= ']' | kw= '=' | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | this_WS_10= RULE_WS )+ ;
    public final AntlrDatatypeRuleToken ruleAISLFuzzyExpression() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_1=null;
        Token kw=null;
        Token this_WS_10=null;
        AntlrDatatypeRuleToken this_IndexName_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:4126:2: ( (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '[' | kw= ']' | kw= '=' | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | this_WS_10= RULE_WS )+ )
            // InternalAlpha.g:4127:2: (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '[' | kw= ']' | kw= '=' | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | this_WS_10= RULE_WS )+
            {
            // InternalAlpha.g:4127:2: (this_IndexName_0= ruleIndexName | this_INT_1= RULE_INT | kw= '[' | kw= ']' | kw= '=' | kw= '+' | kw= '-' | kw= '*' | kw= '/' | kw= '%' | this_WS_10= RULE_WS )+
            int cnt59=0;
            loop59:
            do {
                int alt59=12;
                switch ( input.LA(1) ) {
                case RULE_ID:
                    {
                    alt59=1;
                    }
                    break;
                case RULE_INT:
                    {
                    alt59=2;
                    }
                    break;
                case 37:
                    {
                    alt59=3;
                    }
                    break;
                case 38:
                    {
                    alt59=4;
                    }
                    break;
                case 13:
                    {
                    alt59=5;
                    }
                    break;
                case 43:
                    {
                    alt59=6;
                    }
                    break;
                case 44:
                    {
                    alt59=7;
                    }
                    break;
                case 45:
                    {
                    alt59=8;
                    }
                    break;
                case 46:
                    {
                    alt59=9;
                    }
                    break;
                case 47:
                    {
                    alt59=10;
                    }
                    break;
                case RULE_WS:
                    {
                    alt59=11;
                    }
                    break;

                }

                switch (alt59) {
            	case 1 :
            	    // InternalAlpha.g:4128:3: this_IndexName_0= ruleIndexName
            	    {

            	    			newCompositeNode(grammarAccess.getAISLFuzzyExpressionAccess().getIndexNameParserRuleCall_0());
            	    		
            	    pushFollow(FOLLOW_60);
            	    this_IndexName_0=ruleIndexName();

            	    state._fsp--;


            	    			current.merge(this_IndexName_0);
            	    		

            	    			afterParserOrEnumRuleCall();
            	    		

            	    }
            	    break;
            	case 2 :
            	    // InternalAlpha.g:4139:3: this_INT_1= RULE_INT
            	    {
            	    this_INT_1=(Token)match(input,RULE_INT,FOLLOW_60); 

            	    			current.merge(this_INT_1);
            	    		

            	    			newLeafNode(this_INT_1, grammarAccess.getAISLFuzzyExpressionAccess().getINTTerminalRuleCall_1());
            	    		

            	    }
            	    break;
            	case 3 :
            	    // InternalAlpha.g:4147:3: kw= '['
            	    {
            	    kw=(Token)match(input,37,FOLLOW_60); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLFuzzyExpressionAccess().getLeftSquareBracketKeyword_2());
            	    		

            	    }
            	    break;
            	case 4 :
            	    // InternalAlpha.g:4153:3: kw= ']'
            	    {
            	    kw=(Token)match(input,38,FOLLOW_60); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLFuzzyExpressionAccess().getRightSquareBracketKeyword_3());
            	    		

            	    }
            	    break;
            	case 5 :
            	    // InternalAlpha.g:4159:3: kw= '='
            	    {
            	    kw=(Token)match(input,13,FOLLOW_60); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLFuzzyExpressionAccess().getEqualsSignKeyword_4());
            	    		

            	    }
            	    break;
            	case 6 :
            	    // InternalAlpha.g:4165:3: kw= '+'
            	    {
            	    kw=(Token)match(input,43,FOLLOW_60); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLFuzzyExpressionAccess().getPlusSignKeyword_5());
            	    		

            	    }
            	    break;
            	case 7 :
            	    // InternalAlpha.g:4171:3: kw= '-'
            	    {
            	    kw=(Token)match(input,44,FOLLOW_60); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLFuzzyExpressionAccess().getHyphenMinusKeyword_6());
            	    		

            	    }
            	    break;
            	case 8 :
            	    // InternalAlpha.g:4177:3: kw= '*'
            	    {
            	    kw=(Token)match(input,45,FOLLOW_60); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLFuzzyExpressionAccess().getAsteriskKeyword_7());
            	    		

            	    }
            	    break;
            	case 9 :
            	    // InternalAlpha.g:4183:3: kw= '/'
            	    {
            	    kw=(Token)match(input,46,FOLLOW_60); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLFuzzyExpressionAccess().getSolidusKeyword_8());
            	    		

            	    }
            	    break;
            	case 10 :
            	    // InternalAlpha.g:4189:3: kw= '%'
            	    {
            	    kw=(Token)match(input,47,FOLLOW_60); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getAISLFuzzyExpressionAccess().getPercentSignKeyword_9());
            	    		

            	    }
            	    break;
            	case 11 :
            	    // InternalAlpha.g:4195:3: this_WS_10= RULE_WS
            	    {
            	    this_WS_10=(Token)match(input,RULE_WS,FOLLOW_60); 

            	    			current.merge(this_WS_10);
            	    		

            	    			newLeafNode(this_WS_10, grammarAccess.getAISLFuzzyExpressionAccess().getWSTerminalRuleCall_10());
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt59 >= 1 ) break loop59;
                        EarlyExitException eee =
                            new EarlyExitException(59, input);
                        throw eee;
                }
                cnt59++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAISLFuzzyExpression"


    // $ANTLR start "entryRuleEquation"
    // InternalAlpha.g:4206:1: entryRuleEquation returns [EObject current=null] : iv_ruleEquation= ruleEquation EOF ;
    public final EObject entryRuleEquation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEquation = null;


        try {
            // InternalAlpha.g:4206:49: (iv_ruleEquation= ruleEquation EOF )
            // InternalAlpha.g:4207:2: iv_ruleEquation= ruleEquation EOF
            {
             newCompositeNode(grammarAccess.getEquationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEquation=ruleEquation();

            state._fsp--;

             current =iv_ruleEquation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEquation"


    // $ANTLR start "ruleEquation"
    // InternalAlpha.g:4213:1: ruleEquation returns [EObject current=null] : (this_StandardEquation_0= ruleStandardEquation | this_UseEquation_1= ruleUseEquation ) ;
    public final EObject ruleEquation() throws RecognitionException {
        EObject current = null;

        EObject this_StandardEquation_0 = null;

        EObject this_UseEquation_1 = null;



        	enterRule();

        try {
            // InternalAlpha.g:4219:2: ( (this_StandardEquation_0= ruleStandardEquation | this_UseEquation_1= ruleUseEquation ) )
            // InternalAlpha.g:4220:2: (this_StandardEquation_0= ruleStandardEquation | this_UseEquation_1= ruleUseEquation )
            {
            // InternalAlpha.g:4220:2: (this_StandardEquation_0= ruleStandardEquation | this_UseEquation_1= ruleUseEquation )
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==RULE_ID) ) {
                alt60=1;
            }
            else if ( (LA60_0==15||LA60_0==27||LA60_0==33||LA60_0==58) ) {
                alt60=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }
            switch (alt60) {
                case 1 :
                    // InternalAlpha.g:4221:3: this_StandardEquation_0= ruleStandardEquation
                    {

                    			newCompositeNode(grammarAccess.getEquationAccess().getStandardEquationParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_StandardEquation_0=ruleStandardEquation();

                    state._fsp--;


                    			current = this_StandardEquation_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:4230:3: this_UseEquation_1= ruleUseEquation
                    {

                    			newCompositeNode(grammarAccess.getEquationAccess().getUseEquationParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_UseEquation_1=ruleUseEquation();

                    state._fsp--;


                    			current = this_UseEquation_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEquation"


    // $ANTLR start "entryRuleStandardEquation"
    // InternalAlpha.g:4242:1: entryRuleStandardEquation returns [EObject current=null] : iv_ruleStandardEquation= ruleStandardEquation EOF ;
    public final EObject entryRuleStandardEquation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStandardEquation = null;


        try {
            // InternalAlpha.g:4242:57: (iv_ruleStandardEquation= ruleStandardEquation EOF )
            // InternalAlpha.g:4243:2: iv_ruleStandardEquation= ruleStandardEquation EOF
            {
             newCompositeNode(grammarAccess.getStandardEquationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStandardEquation=ruleStandardEquation();

            state._fsp--;

             current =iv_ruleStandardEquation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStandardEquation"


    // $ANTLR start "ruleStandardEquation"
    // InternalAlpha.g:4249:1: ruleStandardEquation returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_indexNames_2_0= ruleIndexName ) ) (otherlv_3= ',' ( (lv_indexNames_4_0= ruleIndexName ) ) )* otherlv_5= ']' )? otherlv_6= '=' ( (lv_expr_7_0= ruleAlphaExpression ) ) otherlv_8= ';' ) ;
    public final EObject ruleStandardEquation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_indexNames_2_0 = null;

        AntlrDatatypeRuleToken lv_indexNames_4_0 = null;

        EObject lv_expr_7_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:4255:2: ( ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_indexNames_2_0= ruleIndexName ) ) (otherlv_3= ',' ( (lv_indexNames_4_0= ruleIndexName ) ) )* otherlv_5= ']' )? otherlv_6= '=' ( (lv_expr_7_0= ruleAlphaExpression ) ) otherlv_8= ';' ) )
            // InternalAlpha.g:4256:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_indexNames_2_0= ruleIndexName ) ) (otherlv_3= ',' ( (lv_indexNames_4_0= ruleIndexName ) ) )* otherlv_5= ']' )? otherlv_6= '=' ( (lv_expr_7_0= ruleAlphaExpression ) ) otherlv_8= ';' )
            {
            // InternalAlpha.g:4256:2: ( ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_indexNames_2_0= ruleIndexName ) ) (otherlv_3= ',' ( (lv_indexNames_4_0= ruleIndexName ) ) )* otherlv_5= ']' )? otherlv_6= '=' ( (lv_expr_7_0= ruleAlphaExpression ) ) otherlv_8= ';' )
            // InternalAlpha.g:4257:3: ( (otherlv_0= RULE_ID ) ) (otherlv_1= '[' ( (lv_indexNames_2_0= ruleIndexName ) ) (otherlv_3= ',' ( (lv_indexNames_4_0= ruleIndexName ) ) )* otherlv_5= ']' )? otherlv_6= '=' ( (lv_expr_7_0= ruleAlphaExpression ) ) otherlv_8= ';'
            {
            // InternalAlpha.g:4257:3: ( (otherlv_0= RULE_ID ) )
            // InternalAlpha.g:4258:4: (otherlv_0= RULE_ID )
            {
            // InternalAlpha.g:4258:4: (otherlv_0= RULE_ID )
            // InternalAlpha.g:4259:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStandardEquationRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_61); 

            					newLeafNode(otherlv_0, grammarAccess.getStandardEquationAccess().getVariableVariableCrossReference_0_0());
            				

            }


            }

            // InternalAlpha.g:4270:3: (otherlv_1= '[' ( (lv_indexNames_2_0= ruleIndexName ) ) (otherlv_3= ',' ( (lv_indexNames_4_0= ruleIndexName ) ) )* otherlv_5= ']' )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==37) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalAlpha.g:4271:4: otherlv_1= '[' ( (lv_indexNames_2_0= ruleIndexName ) ) (otherlv_3= ',' ( (lv_indexNames_4_0= ruleIndexName ) ) )* otherlv_5= ']'
                    {
                    otherlv_1=(Token)match(input,37,FOLLOW_5); 

                    				newLeafNode(otherlv_1, grammarAccess.getStandardEquationAccess().getLeftSquareBracketKeyword_1_0());
                    			
                    // InternalAlpha.g:4275:4: ( (lv_indexNames_2_0= ruleIndexName ) )
                    // InternalAlpha.g:4276:5: (lv_indexNames_2_0= ruleIndexName )
                    {
                    // InternalAlpha.g:4276:5: (lv_indexNames_2_0= ruleIndexName )
                    // InternalAlpha.g:4277:6: lv_indexNames_2_0= ruleIndexName
                    {

                    						newCompositeNode(grammarAccess.getStandardEquationAccess().getIndexNamesIndexNameParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_32);
                    lv_indexNames_2_0=ruleIndexName();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getStandardEquationRule());
                    						}
                    						add(
                    							current,
                    							"indexNames",
                    							lv_indexNames_2_0,
                    							"alpha.model.Alpha.IndexName");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:4294:4: (otherlv_3= ',' ( (lv_indexNames_4_0= ruleIndexName ) ) )*
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( (LA61_0==24) ) {
                            alt61=1;
                        }


                        switch (alt61) {
                    	case 1 :
                    	    // InternalAlpha.g:4295:5: otherlv_3= ',' ( (lv_indexNames_4_0= ruleIndexName ) )
                    	    {
                    	    otherlv_3=(Token)match(input,24,FOLLOW_5); 

                    	    					newLeafNode(otherlv_3, grammarAccess.getStandardEquationAccess().getCommaKeyword_1_2_0());
                    	    				
                    	    // InternalAlpha.g:4299:5: ( (lv_indexNames_4_0= ruleIndexName ) )
                    	    // InternalAlpha.g:4300:6: (lv_indexNames_4_0= ruleIndexName )
                    	    {
                    	    // InternalAlpha.g:4300:6: (lv_indexNames_4_0= ruleIndexName )
                    	    // InternalAlpha.g:4301:7: lv_indexNames_4_0= ruleIndexName
                    	    {

                    	    							newCompositeNode(grammarAccess.getStandardEquationAccess().getIndexNamesIndexNameParserRuleCall_1_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_32);
                    	    lv_indexNames_4_0=ruleIndexName();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStandardEquationRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"indexNames",
                    	    								lv_indexNames_4_0,
                    	    								"alpha.model.Alpha.IndexName");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop61;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,38,FOLLOW_6); 

                    				newLeafNode(otherlv_5, grammarAccess.getStandardEquationAccess().getRightSquareBracketKeyword_1_3());
                    			

                    }
                    break;

            }

            otherlv_6=(Token)match(input,13,FOLLOW_22); 

            			newLeafNode(otherlv_6, grammarAccess.getStandardEquationAccess().getEqualsSignKeyword_2());
            		
            // InternalAlpha.g:4328:3: ( (lv_expr_7_0= ruleAlphaExpression ) )
            // InternalAlpha.g:4329:4: (lv_expr_7_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:4329:4: (lv_expr_7_0= ruleAlphaExpression )
            // InternalAlpha.g:4330:5: lv_expr_7_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getStandardEquationAccess().getExprAlphaExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_62);
            lv_expr_7_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getStandardEquationRule());
            					}
            					set(
            						current,
            						"expr",
            						lv_expr_7_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_8=(Token)match(input,34,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getStandardEquationAccess().getSemicolonKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStandardEquation"


    // $ANTLR start "entryRuleUseEquation"
    // InternalAlpha.g:4355:1: entryRuleUseEquation returns [EObject current=null] : iv_ruleUseEquation= ruleUseEquation EOF ;
    public final EObject entryRuleUseEquation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUseEquation = null;


        try {
            // InternalAlpha.g:4355:52: (iv_ruleUseEquation= ruleUseEquation EOF )
            // InternalAlpha.g:4356:2: iv_ruleUseEquation= ruleUseEquation EOF
            {
             newCompositeNode(grammarAccess.getUseEquationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUseEquation=ruleUseEquation();

            state._fsp--;

             current =iv_ruleUseEquation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUseEquation"


    // $ANTLR start "ruleUseEquation"
    // InternalAlpha.g:4362:1: ruleUseEquation returns [EObject current=null] : ( ( (otherlv_0= 'over' ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) ) )? (otherlv_2= 'with' (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )? )? otherlv_8= ':' )? otherlv_9= '(' ( ( (lv_outputExprs_10_0= ruleAlphaExpression ) ) (otherlv_11= ',' ( (lv_outputExprs_12_0= ruleAlphaExpression ) ) )* )? otherlv_13= ')' otherlv_14= '=' ( ( ruleQualifiedName ) ) ( (lv_callParamsExpr_16_0= ruleJNIFunctionInArrayNotation ) ) otherlv_17= '(' ( ( (lv_inputExprs_18_0= ruleAlphaExpression ) ) (otherlv_19= ',' ( (lv_inputExprs_20_0= ruleAlphaExpression ) ) )* )? otherlv_21= ')' otherlv_22= ';' ) ;
    public final EObject ruleUseEquation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_17=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        EObject lv_instantiationDomainExpr_1_0 = null;

        AntlrDatatypeRuleToken lv_subsystemDims_4_0 = null;

        AntlrDatatypeRuleToken lv_subsystemDims_6_0 = null;

        EObject lv_outputExprs_10_0 = null;

        EObject lv_outputExprs_12_0 = null;

        EObject lv_callParamsExpr_16_0 = null;

        EObject lv_inputExprs_18_0 = null;

        EObject lv_inputExprs_20_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:4368:2: ( ( ( (otherlv_0= 'over' ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) ) )? (otherlv_2= 'with' (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )? )? otherlv_8= ':' )? otherlv_9= '(' ( ( (lv_outputExprs_10_0= ruleAlphaExpression ) ) (otherlv_11= ',' ( (lv_outputExprs_12_0= ruleAlphaExpression ) ) )* )? otherlv_13= ')' otherlv_14= '=' ( ( ruleQualifiedName ) ) ( (lv_callParamsExpr_16_0= ruleJNIFunctionInArrayNotation ) ) otherlv_17= '(' ( ( (lv_inputExprs_18_0= ruleAlphaExpression ) ) (otherlv_19= ',' ( (lv_inputExprs_20_0= ruleAlphaExpression ) ) )* )? otherlv_21= ')' otherlv_22= ';' ) )
            // InternalAlpha.g:4369:2: ( ( (otherlv_0= 'over' ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) ) )? (otherlv_2= 'with' (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )? )? otherlv_8= ':' )? otherlv_9= '(' ( ( (lv_outputExprs_10_0= ruleAlphaExpression ) ) (otherlv_11= ',' ( (lv_outputExprs_12_0= ruleAlphaExpression ) ) )* )? otherlv_13= ')' otherlv_14= '=' ( ( ruleQualifiedName ) ) ( (lv_callParamsExpr_16_0= ruleJNIFunctionInArrayNotation ) ) otherlv_17= '(' ( ( (lv_inputExprs_18_0= ruleAlphaExpression ) ) (otherlv_19= ',' ( (lv_inputExprs_20_0= ruleAlphaExpression ) ) )* )? otherlv_21= ')' otherlv_22= ';' )
            {
            // InternalAlpha.g:4369:2: ( ( (otherlv_0= 'over' ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) ) )? (otherlv_2= 'with' (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )? )? otherlv_8= ':' )? otherlv_9= '(' ( ( (lv_outputExprs_10_0= ruleAlphaExpression ) ) (otherlv_11= ',' ( (lv_outputExprs_12_0= ruleAlphaExpression ) ) )* )? otherlv_13= ')' otherlv_14= '=' ( ( ruleQualifiedName ) ) ( (lv_callParamsExpr_16_0= ruleJNIFunctionInArrayNotation ) ) otherlv_17= '(' ( ( (lv_inputExprs_18_0= ruleAlphaExpression ) ) (otherlv_19= ',' ( (lv_inputExprs_20_0= ruleAlphaExpression ) ) )* )? otherlv_21= ')' otherlv_22= ';' )
            // InternalAlpha.g:4370:3: ( (otherlv_0= 'over' ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) ) )? (otherlv_2= 'with' (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )? )? otherlv_8= ':' )? otherlv_9= '(' ( ( (lv_outputExprs_10_0= ruleAlphaExpression ) ) (otherlv_11= ',' ( (lv_outputExprs_12_0= ruleAlphaExpression ) ) )* )? otherlv_13= ')' otherlv_14= '=' ( ( ruleQualifiedName ) ) ( (lv_callParamsExpr_16_0= ruleJNIFunctionInArrayNotation ) ) otherlv_17= '(' ( ( (lv_inputExprs_18_0= ruleAlphaExpression ) ) (otherlv_19= ',' ( (lv_inputExprs_20_0= ruleAlphaExpression ) ) )* )? otherlv_21= ')' otherlv_22= ';'
            {
            // InternalAlpha.g:4370:3: ( (otherlv_0= 'over' ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) ) )? (otherlv_2= 'with' (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )? )? otherlv_8= ':' )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==27||LA67_0==33||LA67_0==58) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalAlpha.g:4371:4: (otherlv_0= 'over' ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) ) )? (otherlv_2= 'with' (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )? )? otherlv_8= ':'
                    {
                    // InternalAlpha.g:4371:4: (otherlv_0= 'over' ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) ) )?
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( (LA63_0==27) ) {
                        alt63=1;
                    }
                    switch (alt63) {
                        case 1 :
                            // InternalAlpha.g:4372:5: otherlv_0= 'over' ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) )
                            {
                            otherlv_0=(Token)match(input,27,FOLLOW_20); 

                            					newLeafNode(otherlv_0, grammarAccess.getUseEquationAccess().getOverKeyword_0_0_0());
                            				
                            // InternalAlpha.g:4376:5: ( (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression ) )
                            // InternalAlpha.g:4377:6: (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression )
                            {
                            // InternalAlpha.g:4377:6: (lv_instantiationDomainExpr_1_0= ruleCalculatorExpression )
                            // InternalAlpha.g:4378:7: lv_instantiationDomainExpr_1_0= ruleCalculatorExpression
                            {

                            							newCompositeNode(grammarAccess.getUseEquationAccess().getInstantiationDomainExprCalculatorExpressionParserRuleCall_0_0_1_0());
                            						
                            pushFollow(FOLLOW_63);
                            lv_instantiationDomainExpr_1_0=ruleCalculatorExpression();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getUseEquationRule());
                            							}
                            							set(
                            								current,
                            								"instantiationDomainExpr",
                            								lv_instantiationDomainExpr_1_0,
                            								"alpha.model.Alpha.CalculatorExpression");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }


                            }
                            break;

                    }

                    // InternalAlpha.g:4396:4: (otherlv_2= 'with' (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )? )?
                    int alt66=2;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==58) ) {
                        alt66=1;
                    }
                    switch (alt66) {
                        case 1 :
                            // InternalAlpha.g:4397:5: otherlv_2= 'with' (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )?
                            {
                            otherlv_2=(Token)match(input,58,FOLLOW_64); 

                            					newLeafNode(otherlv_2, grammarAccess.getUseEquationAccess().getWithKeyword_0_1_0());
                            				
                            // InternalAlpha.g:4401:5: (otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']' )?
                            int alt65=2;
                            int LA65_0 = input.LA(1);

                            if ( (LA65_0==37) ) {
                                alt65=1;
                            }
                            switch (alt65) {
                                case 1 :
                                    // InternalAlpha.g:4402:6: otherlv_3= '[' ( (lv_subsystemDims_4_0= ruleIndexName ) ) (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )* otherlv_7= ']'
                                    {
                                    otherlv_3=(Token)match(input,37,FOLLOW_5); 

                                    						newLeafNode(otherlv_3, grammarAccess.getUseEquationAccess().getLeftSquareBracketKeyword_0_1_1_0());
                                    					
                                    // InternalAlpha.g:4406:6: ( (lv_subsystemDims_4_0= ruleIndexName ) )
                                    // InternalAlpha.g:4407:7: (lv_subsystemDims_4_0= ruleIndexName )
                                    {
                                    // InternalAlpha.g:4407:7: (lv_subsystemDims_4_0= ruleIndexName )
                                    // InternalAlpha.g:4408:8: lv_subsystemDims_4_0= ruleIndexName
                                    {

                                    								newCompositeNode(grammarAccess.getUseEquationAccess().getSubsystemDimsIndexNameParserRuleCall_0_1_1_1_0());
                                    							
                                    pushFollow(FOLLOW_32);
                                    lv_subsystemDims_4_0=ruleIndexName();

                                    state._fsp--;


                                    								if (current==null) {
                                    									current = createModelElementForParent(grammarAccess.getUseEquationRule());
                                    								}
                                    								add(
                                    									current,
                                    									"subsystemDims",
                                    									lv_subsystemDims_4_0,
                                    									"alpha.model.Alpha.IndexName");
                                    								afterParserOrEnumRuleCall();
                                    							

                                    }


                                    }

                                    // InternalAlpha.g:4425:6: (otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) ) )*
                                    loop64:
                                    do {
                                        int alt64=2;
                                        int LA64_0 = input.LA(1);

                                        if ( (LA64_0==24) ) {
                                            alt64=1;
                                        }


                                        switch (alt64) {
                                    	case 1 :
                                    	    // InternalAlpha.g:4426:7: otherlv_5= ',' ( (lv_subsystemDims_6_0= ruleIndexName ) )
                                    	    {
                                    	    otherlv_5=(Token)match(input,24,FOLLOW_5); 

                                    	    							newLeafNode(otherlv_5, grammarAccess.getUseEquationAccess().getCommaKeyword_0_1_1_2_0());
                                    	    						
                                    	    // InternalAlpha.g:4430:7: ( (lv_subsystemDims_6_0= ruleIndexName ) )
                                    	    // InternalAlpha.g:4431:8: (lv_subsystemDims_6_0= ruleIndexName )
                                    	    {
                                    	    // InternalAlpha.g:4431:8: (lv_subsystemDims_6_0= ruleIndexName )
                                    	    // InternalAlpha.g:4432:9: lv_subsystemDims_6_0= ruleIndexName
                                    	    {

                                    	    									newCompositeNode(grammarAccess.getUseEquationAccess().getSubsystemDimsIndexNameParserRuleCall_0_1_1_2_1_0());
                                    	    								
                                    	    pushFollow(FOLLOW_32);
                                    	    lv_subsystemDims_6_0=ruleIndexName();

                                    	    state._fsp--;


                                    	    									if (current==null) {
                                    	    										current = createModelElementForParent(grammarAccess.getUseEquationRule());
                                    	    									}
                                    	    									add(
                                    	    										current,
                                    	    										"subsystemDims",
                                    	    										lv_subsystemDims_6_0,
                                    	    										"alpha.model.Alpha.IndexName");
                                    	    									afterParserOrEnumRuleCall();
                                    	    								

                                    	    }


                                    	    }


                                    	    }
                                    	    break;

                                    	default :
                                    	    break loop64;
                                        }
                                    } while (true);

                                    otherlv_7=(Token)match(input,38,FOLLOW_26); 

                                    						newLeafNode(otherlv_7, grammarAccess.getUseEquationAccess().getRightSquareBracketKeyword_0_1_1_3());
                                    					

                                    }
                                    break;

                            }


                            }
                            break;

                    }

                    otherlv_8=(Token)match(input,33,FOLLOW_8); 

                    				newLeafNode(otherlv_8, grammarAccess.getUseEquationAccess().getColonKeyword_0_2());
                    			

                    }
                    break;

            }

            otherlv_9=(Token)match(input,15,FOLLOW_65); 

            			newLeafNode(otherlv_9, grammarAccess.getUseEquationAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:4465:3: ( ( (lv_outputExprs_10_0= ruleAlphaExpression ) ) (otherlv_11= ',' ( (lv_outputExprs_12_0= ruleAlphaExpression ) ) )* )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( ((LA69_0>=RULE_ID && LA69_0<=RULE_INT)||LA69_0==RULE_BOOLEAN||LA69_0==15||LA69_0==19||LA69_0==39||(LA69_0>=42 && LA69_0<=45)||(LA69_0>=49 && LA69_0<=50)||LA69_0==59||(LA69_0>=61 && LA69_0<=62)||(LA69_0>=64 && LA69_0<=68)||(LA69_0>=70 && LA69_0<=75)||LA69_0==77) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalAlpha.g:4466:4: ( (lv_outputExprs_10_0= ruleAlphaExpression ) ) (otherlv_11= ',' ( (lv_outputExprs_12_0= ruleAlphaExpression ) ) )*
                    {
                    // InternalAlpha.g:4466:4: ( (lv_outputExprs_10_0= ruleAlphaExpression ) )
                    // InternalAlpha.g:4467:5: (lv_outputExprs_10_0= ruleAlphaExpression )
                    {
                    // InternalAlpha.g:4467:5: (lv_outputExprs_10_0= ruleAlphaExpression )
                    // InternalAlpha.g:4468:6: lv_outputExprs_10_0= ruleAlphaExpression
                    {

                    						newCompositeNode(grammarAccess.getUseEquationAccess().getOutputExprsAlphaExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_59);
                    lv_outputExprs_10_0=ruleAlphaExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getUseEquationRule());
                    						}
                    						add(
                    							current,
                    							"outputExprs",
                    							lv_outputExprs_10_0,
                    							"alpha.model.Alpha.AlphaExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:4485:4: (otherlv_11= ',' ( (lv_outputExprs_12_0= ruleAlphaExpression ) ) )*
                    loop68:
                    do {
                        int alt68=2;
                        int LA68_0 = input.LA(1);

                        if ( (LA68_0==24) ) {
                            alt68=1;
                        }


                        switch (alt68) {
                    	case 1 :
                    	    // InternalAlpha.g:4486:5: otherlv_11= ',' ( (lv_outputExprs_12_0= ruleAlphaExpression ) )
                    	    {
                    	    otherlv_11=(Token)match(input,24,FOLLOW_22); 

                    	    					newLeafNode(otherlv_11, grammarAccess.getUseEquationAccess().getCommaKeyword_2_1_0());
                    	    				
                    	    // InternalAlpha.g:4490:5: ( (lv_outputExprs_12_0= ruleAlphaExpression ) )
                    	    // InternalAlpha.g:4491:6: (lv_outputExprs_12_0= ruleAlphaExpression )
                    	    {
                    	    // InternalAlpha.g:4491:6: (lv_outputExprs_12_0= ruleAlphaExpression )
                    	    // InternalAlpha.g:4492:7: lv_outputExprs_12_0= ruleAlphaExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getUseEquationAccess().getOutputExprsAlphaExpressionParserRuleCall_2_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_59);
                    	    lv_outputExprs_12_0=ruleAlphaExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getUseEquationRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"outputExprs",
                    	    								lv_outputExprs_12_0,
                    	    								"alpha.model.Alpha.AlphaExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop68;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_13=(Token)match(input,16,FOLLOW_6); 

            			newLeafNode(otherlv_13, grammarAccess.getUseEquationAccess().getRightParenthesisKeyword_3());
            		
            otherlv_14=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_14, grammarAccess.getUseEquationAccess().getEqualsSignKeyword_4());
            		
            // InternalAlpha.g:4519:3: ( ( ruleQualifiedName ) )
            // InternalAlpha.g:4520:4: ( ruleQualifiedName )
            {
            // InternalAlpha.g:4520:4: ( ruleQualifiedName )
            // InternalAlpha.g:4521:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getUseEquationRule());
            					}
            				

            					newCompositeNode(grammarAccess.getUseEquationAccess().getSystemAlphaSystemCrossReference_5_0());
            				
            pushFollow(FOLLOW_36);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:4535:3: ( (lv_callParamsExpr_16_0= ruleJNIFunctionInArrayNotation ) )
            // InternalAlpha.g:4536:4: (lv_callParamsExpr_16_0= ruleJNIFunctionInArrayNotation )
            {
            // InternalAlpha.g:4536:4: (lv_callParamsExpr_16_0= ruleJNIFunctionInArrayNotation )
            // InternalAlpha.g:4537:5: lv_callParamsExpr_16_0= ruleJNIFunctionInArrayNotation
            {

            					newCompositeNode(grammarAccess.getUseEquationAccess().getCallParamsExprJNIFunctionInArrayNotationParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_8);
            lv_callParamsExpr_16_0=ruleJNIFunctionInArrayNotation();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUseEquationRule());
            					}
            					set(
            						current,
            						"callParamsExpr",
            						lv_callParamsExpr_16_0,
            						"alpha.model.Alpha.JNIFunctionInArrayNotation");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_17=(Token)match(input,15,FOLLOW_65); 

            			newLeafNode(otherlv_17, grammarAccess.getUseEquationAccess().getLeftParenthesisKeyword_7());
            		
            // InternalAlpha.g:4558:3: ( ( (lv_inputExprs_18_0= ruleAlphaExpression ) ) (otherlv_19= ',' ( (lv_inputExprs_20_0= ruleAlphaExpression ) ) )* )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( ((LA71_0>=RULE_ID && LA71_0<=RULE_INT)||LA71_0==RULE_BOOLEAN||LA71_0==15||LA71_0==19||LA71_0==39||(LA71_0>=42 && LA71_0<=45)||(LA71_0>=49 && LA71_0<=50)||LA71_0==59||(LA71_0>=61 && LA71_0<=62)||(LA71_0>=64 && LA71_0<=68)||(LA71_0>=70 && LA71_0<=75)||LA71_0==77) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalAlpha.g:4559:4: ( (lv_inputExprs_18_0= ruleAlphaExpression ) ) (otherlv_19= ',' ( (lv_inputExprs_20_0= ruleAlphaExpression ) ) )*
                    {
                    // InternalAlpha.g:4559:4: ( (lv_inputExprs_18_0= ruleAlphaExpression ) )
                    // InternalAlpha.g:4560:5: (lv_inputExprs_18_0= ruleAlphaExpression )
                    {
                    // InternalAlpha.g:4560:5: (lv_inputExprs_18_0= ruleAlphaExpression )
                    // InternalAlpha.g:4561:6: lv_inputExprs_18_0= ruleAlphaExpression
                    {

                    						newCompositeNode(grammarAccess.getUseEquationAccess().getInputExprsAlphaExpressionParserRuleCall_8_0_0());
                    					
                    pushFollow(FOLLOW_59);
                    lv_inputExprs_18_0=ruleAlphaExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getUseEquationRule());
                    						}
                    						add(
                    							current,
                    							"inputExprs",
                    							lv_inputExprs_18_0,
                    							"alpha.model.Alpha.AlphaExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:4578:4: (otherlv_19= ',' ( (lv_inputExprs_20_0= ruleAlphaExpression ) ) )*
                    loop70:
                    do {
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( (LA70_0==24) ) {
                            alt70=1;
                        }


                        switch (alt70) {
                    	case 1 :
                    	    // InternalAlpha.g:4579:5: otherlv_19= ',' ( (lv_inputExprs_20_0= ruleAlphaExpression ) )
                    	    {
                    	    otherlv_19=(Token)match(input,24,FOLLOW_22); 

                    	    					newLeafNode(otherlv_19, grammarAccess.getUseEquationAccess().getCommaKeyword_8_1_0());
                    	    				
                    	    // InternalAlpha.g:4583:5: ( (lv_inputExprs_20_0= ruleAlphaExpression ) )
                    	    // InternalAlpha.g:4584:6: (lv_inputExprs_20_0= ruleAlphaExpression )
                    	    {
                    	    // InternalAlpha.g:4584:6: (lv_inputExprs_20_0= ruleAlphaExpression )
                    	    // InternalAlpha.g:4585:7: lv_inputExprs_20_0= ruleAlphaExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getUseEquationAccess().getInputExprsAlphaExpressionParserRuleCall_8_1_1_0());
                    	    						
                    	    pushFollow(FOLLOW_59);
                    	    lv_inputExprs_20_0=ruleAlphaExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getUseEquationRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"inputExprs",
                    	    								lv_inputExprs_20_0,
                    	    								"alpha.model.Alpha.AlphaExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop70;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_21=(Token)match(input,16,FOLLOW_62); 

            			newLeafNode(otherlv_21, grammarAccess.getUseEquationAccess().getRightParenthesisKeyword_9());
            		
            otherlv_22=(Token)match(input,34,FOLLOW_2); 

            			newLeafNode(otherlv_22, grammarAccess.getUseEquationAccess().getSemicolonKeyword_10());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUseEquation"


    // $ANTLR start "entryRuleAlphaExpression"
    // InternalAlpha.g:4616:1: entryRuleAlphaExpression returns [EObject current=null] : iv_ruleAlphaExpression= ruleAlphaExpression EOF ;
    public final EObject entryRuleAlphaExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaExpression = null;


        try {
            // InternalAlpha.g:4616:56: (iv_ruleAlphaExpression= ruleAlphaExpression EOF )
            // InternalAlpha.g:4617:2: iv_ruleAlphaExpression= ruleAlphaExpression EOF
            {
             newCompositeNode(grammarAccess.getAlphaExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaExpression=ruleAlphaExpression();

            state._fsp--;

             current =iv_ruleAlphaExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaExpression"


    // $ANTLR start "ruleAlphaExpression"
    // InternalAlpha.g:4623:1: ruleAlphaExpression returns [EObject current=null] : (this_IfExpression_0= ruleIfExpression | this_RestrictExpression_1= ruleRestrictExpression | this_AutoRestrictExpression_2= ruleAutoRestrictExpression | this_OrExpression_3= ruleOrExpression ) ;
    public final EObject ruleAlphaExpression() throws RecognitionException {
        EObject current = null;

        EObject this_IfExpression_0 = null;

        EObject this_RestrictExpression_1 = null;

        EObject this_AutoRestrictExpression_2 = null;

        EObject this_OrExpression_3 = null;



        	enterRule();

        try {
            // InternalAlpha.g:4629:2: ( (this_IfExpression_0= ruleIfExpression | this_RestrictExpression_1= ruleRestrictExpression | this_AutoRestrictExpression_2= ruleAutoRestrictExpression | this_OrExpression_3= ruleOrExpression ) )
            // InternalAlpha.g:4630:2: (this_IfExpression_0= ruleIfExpression | this_RestrictExpression_1= ruleRestrictExpression | this_AutoRestrictExpression_2= ruleAutoRestrictExpression | this_OrExpression_3= ruleOrExpression )
            {
            // InternalAlpha.g:4630:2: (this_IfExpression_0= ruleIfExpression | this_RestrictExpression_1= ruleRestrictExpression | this_AutoRestrictExpression_2= ruleAutoRestrictExpression | this_OrExpression_3= ruleOrExpression )
            int alt72=4;
            switch ( input.LA(1) ) {
            case 59:
                {
                alt72=1;
                }
                break;
            case 19:
            case 42:
                {
                alt72=2;
                }
                break;
            case 61:
                {
                alt72=3;
                }
                break;
            case RULE_ID:
            case RULE_INT:
            case RULE_BOOLEAN:
            case 15:
            case 39:
            case 43:
            case 44:
            case 45:
            case 49:
            case 50:
            case 62:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 77:
                {
                alt72=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // InternalAlpha.g:4631:3: this_IfExpression_0= ruleIfExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaExpressionAccess().getIfExpressionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_IfExpression_0=ruleIfExpression();

                    state._fsp--;


                    			current = this_IfExpression_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:4640:3: this_RestrictExpression_1= ruleRestrictExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaExpressionAccess().getRestrictExpressionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_RestrictExpression_1=ruleRestrictExpression();

                    state._fsp--;


                    			current = this_RestrictExpression_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:4649:3: this_AutoRestrictExpression_2= ruleAutoRestrictExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaExpressionAccess().getAutoRestrictExpressionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_AutoRestrictExpression_2=ruleAutoRestrictExpression();

                    state._fsp--;


                    			current = this_AutoRestrictExpression_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalAlpha.g:4658:3: this_OrExpression_3= ruleOrExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaExpressionAccess().getOrExpressionParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_OrExpression_3=ruleOrExpression();

                    state._fsp--;


                    			current = this_OrExpression_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaExpression"


    // $ANTLR start "entryRuleAlphaTerminalExpression"
    // InternalAlpha.g:4670:1: entryRuleAlphaTerminalExpression returns [EObject current=null] : iv_ruleAlphaTerminalExpression= ruleAlphaTerminalExpression EOF ;
    public final EObject entryRuleAlphaTerminalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaTerminalExpression = null;


        try {
            // InternalAlpha.g:4670:64: (iv_ruleAlphaTerminalExpression= ruleAlphaTerminalExpression EOF )
            // InternalAlpha.g:4671:2: iv_ruleAlphaTerminalExpression= ruleAlphaTerminalExpression EOF
            {
             newCompositeNode(grammarAccess.getAlphaTerminalExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaTerminalExpression=ruleAlphaTerminalExpression();

            state._fsp--;

             current =iv_ruleAlphaTerminalExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaTerminalExpression"


    // $ANTLR start "ruleAlphaTerminalExpression"
    // InternalAlpha.g:4677:1: ruleAlphaTerminalExpression returns [EObject current=null] : ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_ConstantExpression_4= ruleConstantExpression | this_VariableExpression_5= ruleVariableExpression | this_DependenceExpression_6= ruleDependenceExpression | this_FuzzyDependenceExpression_7= ruleFuzzyDependenceExpression | this_IndexExpression_8= ruleIndexExpression | this_PolynomialIndexExpression_9= rulePolynomialIndexExpression | this_FuzzyIndexExpression_10= ruleFuzzyIndexExpression | this_Reductions_11= ruleReductions | this_ConvolutionExpression_12= ruleConvolutionExpression | this_MultiArgExpression_13= ruleMultiArgExpression | this_ExternalMultiArgExpression_14= ruleExternalMultiArgExpression | this_SelectExpression_15= ruleSelectExpression ) ;
    public final EObject ruleAlphaTerminalExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_AlphaExpression_1 = null;

        EObject this_CaseExpression_3 = null;

        EObject this_ConstantExpression_4 = null;

        EObject this_VariableExpression_5 = null;

        EObject this_DependenceExpression_6 = null;

        EObject this_FuzzyDependenceExpression_7 = null;

        EObject this_IndexExpression_8 = null;

        EObject this_PolynomialIndexExpression_9 = null;

        EObject this_FuzzyIndexExpression_10 = null;

        EObject this_Reductions_11 = null;

        EObject this_ConvolutionExpression_12 = null;

        EObject this_MultiArgExpression_13 = null;

        EObject this_ExternalMultiArgExpression_14 = null;

        EObject this_SelectExpression_15 = null;



        	enterRule();

        try {
            // InternalAlpha.g:4683:2: ( ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_ConstantExpression_4= ruleConstantExpression | this_VariableExpression_5= ruleVariableExpression | this_DependenceExpression_6= ruleDependenceExpression | this_FuzzyDependenceExpression_7= ruleFuzzyDependenceExpression | this_IndexExpression_8= ruleIndexExpression | this_PolynomialIndexExpression_9= rulePolynomialIndexExpression | this_FuzzyIndexExpression_10= ruleFuzzyIndexExpression | this_Reductions_11= ruleReductions | this_ConvolutionExpression_12= ruleConvolutionExpression | this_MultiArgExpression_13= ruleMultiArgExpression | this_ExternalMultiArgExpression_14= ruleExternalMultiArgExpression | this_SelectExpression_15= ruleSelectExpression ) )
            // InternalAlpha.g:4684:2: ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_ConstantExpression_4= ruleConstantExpression | this_VariableExpression_5= ruleVariableExpression | this_DependenceExpression_6= ruleDependenceExpression | this_FuzzyDependenceExpression_7= ruleFuzzyDependenceExpression | this_IndexExpression_8= ruleIndexExpression | this_PolynomialIndexExpression_9= rulePolynomialIndexExpression | this_FuzzyIndexExpression_10= ruleFuzzyIndexExpression | this_Reductions_11= ruleReductions | this_ConvolutionExpression_12= ruleConvolutionExpression | this_MultiArgExpression_13= ruleMultiArgExpression | this_ExternalMultiArgExpression_14= ruleExternalMultiArgExpression | this_SelectExpression_15= ruleSelectExpression )
            {
            // InternalAlpha.g:4684:2: ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_ConstantExpression_4= ruleConstantExpression | this_VariableExpression_5= ruleVariableExpression | this_DependenceExpression_6= ruleDependenceExpression | this_FuzzyDependenceExpression_7= ruleFuzzyDependenceExpression | this_IndexExpression_8= ruleIndexExpression | this_PolynomialIndexExpression_9= rulePolynomialIndexExpression | this_FuzzyIndexExpression_10= ruleFuzzyIndexExpression | this_Reductions_11= ruleReductions | this_ConvolutionExpression_12= ruleConvolutionExpression | this_MultiArgExpression_13= ruleMultiArgExpression | this_ExternalMultiArgExpression_14= ruleExternalMultiArgExpression | this_SelectExpression_15= ruleSelectExpression )
            int alt73=14;
            alt73 = dfa73.predict(input);
            switch (alt73) {
                case 1 :
                    // InternalAlpha.g:4685:3: (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' )
                    {
                    // InternalAlpha.g:4685:3: (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' )
                    // InternalAlpha.g:4686:4: otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')'
                    {
                    otherlv_0=(Token)match(input,15,FOLLOW_22); 

                    				newLeafNode(otherlv_0, grammarAccess.getAlphaTerminalExpressionAccess().getLeftParenthesisKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getAlphaExpressionParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_9);
                    this_AlphaExpression_1=ruleAlphaExpression();

                    state._fsp--;


                    				current = this_AlphaExpression_1;
                    				afterParserOrEnumRuleCall();
                    			
                    otherlv_2=(Token)match(input,16,FOLLOW_2); 

                    				newLeafNode(otherlv_2, grammarAccess.getAlphaTerminalExpressionAccess().getRightParenthesisKeyword_0_2());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:4704:3: this_CaseExpression_3= ruleCaseExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getCaseExpressionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_CaseExpression_3=ruleCaseExpression();

                    state._fsp--;


                    			current = this_CaseExpression_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:4713:3: this_ConstantExpression_4= ruleConstantExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getConstantExpressionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_ConstantExpression_4=ruleConstantExpression();

                    state._fsp--;


                    			current = this_ConstantExpression_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalAlpha.g:4722:3: this_VariableExpression_5= ruleVariableExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getVariableExpressionParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_VariableExpression_5=ruleVariableExpression();

                    state._fsp--;


                    			current = this_VariableExpression_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalAlpha.g:4731:3: this_DependenceExpression_6= ruleDependenceExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getDependenceExpressionParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_DependenceExpression_6=ruleDependenceExpression();

                    state._fsp--;


                    			current = this_DependenceExpression_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalAlpha.g:4740:3: this_FuzzyDependenceExpression_7= ruleFuzzyDependenceExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getFuzzyDependenceExpressionParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_FuzzyDependenceExpression_7=ruleFuzzyDependenceExpression();

                    state._fsp--;


                    			current = this_FuzzyDependenceExpression_7;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 7 :
                    // InternalAlpha.g:4749:3: this_IndexExpression_8= ruleIndexExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getIndexExpressionParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_IndexExpression_8=ruleIndexExpression();

                    state._fsp--;


                    			current = this_IndexExpression_8;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 8 :
                    // InternalAlpha.g:4758:3: this_PolynomialIndexExpression_9= rulePolynomialIndexExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getPolynomialIndexExpressionParserRuleCall_7());
                    		
                    pushFollow(FOLLOW_2);
                    this_PolynomialIndexExpression_9=rulePolynomialIndexExpression();

                    state._fsp--;


                    			current = this_PolynomialIndexExpression_9;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 9 :
                    // InternalAlpha.g:4767:3: this_FuzzyIndexExpression_10= ruleFuzzyIndexExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getFuzzyIndexExpressionParserRuleCall_8());
                    		
                    pushFollow(FOLLOW_2);
                    this_FuzzyIndexExpression_10=ruleFuzzyIndexExpression();

                    state._fsp--;


                    			current = this_FuzzyIndexExpression_10;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 10 :
                    // InternalAlpha.g:4776:3: this_Reductions_11= ruleReductions
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getReductionsParserRuleCall_9());
                    		
                    pushFollow(FOLLOW_2);
                    this_Reductions_11=ruleReductions();

                    state._fsp--;


                    			current = this_Reductions_11;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 11 :
                    // InternalAlpha.g:4785:3: this_ConvolutionExpression_12= ruleConvolutionExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getConvolutionExpressionParserRuleCall_10());
                    		
                    pushFollow(FOLLOW_2);
                    this_ConvolutionExpression_12=ruleConvolutionExpression();

                    state._fsp--;


                    			current = this_ConvolutionExpression_12;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 12 :
                    // InternalAlpha.g:4794:3: this_MultiArgExpression_13= ruleMultiArgExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getMultiArgExpressionParserRuleCall_11());
                    		
                    pushFollow(FOLLOW_2);
                    this_MultiArgExpression_13=ruleMultiArgExpression();

                    state._fsp--;


                    			current = this_MultiArgExpression_13;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 13 :
                    // InternalAlpha.g:4803:3: this_ExternalMultiArgExpression_14= ruleExternalMultiArgExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getExternalMultiArgExpressionParserRuleCall_12());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExternalMultiArgExpression_14=ruleExternalMultiArgExpression();

                    state._fsp--;


                    			current = this_ExternalMultiArgExpression_14;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 14 :
                    // InternalAlpha.g:4812:3: this_SelectExpression_15= ruleSelectExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaTerminalExpressionAccess().getSelectExpressionParserRuleCall_13());
                    		
                    pushFollow(FOLLOW_2);
                    this_SelectExpression_15=ruleSelectExpression();

                    state._fsp--;


                    			current = this_SelectExpression_15;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaTerminalExpression"


    // $ANTLR start "entryRuleAlphaUnaryTerminalExpression"
    // InternalAlpha.g:4824:1: entryRuleAlphaUnaryTerminalExpression returns [EObject current=null] : iv_ruleAlphaUnaryTerminalExpression= ruleAlphaUnaryTerminalExpression EOF ;
    public final EObject entryRuleAlphaUnaryTerminalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlphaUnaryTerminalExpression = null;


        try {
            // InternalAlpha.g:4824:69: (iv_ruleAlphaUnaryTerminalExpression= ruleAlphaUnaryTerminalExpression EOF )
            // InternalAlpha.g:4825:2: iv_ruleAlphaUnaryTerminalExpression= ruleAlphaUnaryTerminalExpression EOF
            {
             newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlphaUnaryTerminalExpression=ruleAlphaUnaryTerminalExpression();

            state._fsp--;

             current =iv_ruleAlphaUnaryTerminalExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlphaUnaryTerminalExpression"


    // $ANTLR start "ruleAlphaUnaryTerminalExpression"
    // InternalAlpha.g:4831:1: ruleAlphaUnaryTerminalExpression returns [EObject current=null] : ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_VariableExpression_4= ruleVariableExpression | this_IndexExpression_5= ruleIndexExpression | this_PolynomialIndexExpression_6= rulePolynomialIndexExpression | this_FuzzyIndexExpression_7= ruleFuzzyIndexExpression | this_Reductions_8= ruleReductions | this_ConvolutionExpression_9= ruleConvolutionExpression | this_MultiArgExpression_10= ruleMultiArgExpression | this_ExternalMultiArgExpression_11= ruleExternalMultiArgExpression | this_SelectExpression_12= ruleSelectExpression ) ;
    public final EObject ruleAlphaUnaryTerminalExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_AlphaExpression_1 = null;

        EObject this_CaseExpression_3 = null;

        EObject this_VariableExpression_4 = null;

        EObject this_IndexExpression_5 = null;

        EObject this_PolynomialIndexExpression_6 = null;

        EObject this_FuzzyIndexExpression_7 = null;

        EObject this_Reductions_8 = null;

        EObject this_ConvolutionExpression_9 = null;

        EObject this_MultiArgExpression_10 = null;

        EObject this_ExternalMultiArgExpression_11 = null;

        EObject this_SelectExpression_12 = null;



        	enterRule();

        try {
            // InternalAlpha.g:4837:2: ( ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_VariableExpression_4= ruleVariableExpression | this_IndexExpression_5= ruleIndexExpression | this_PolynomialIndexExpression_6= rulePolynomialIndexExpression | this_FuzzyIndexExpression_7= ruleFuzzyIndexExpression | this_Reductions_8= ruleReductions | this_ConvolutionExpression_9= ruleConvolutionExpression | this_MultiArgExpression_10= ruleMultiArgExpression | this_ExternalMultiArgExpression_11= ruleExternalMultiArgExpression | this_SelectExpression_12= ruleSelectExpression ) )
            // InternalAlpha.g:4838:2: ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_VariableExpression_4= ruleVariableExpression | this_IndexExpression_5= ruleIndexExpression | this_PolynomialIndexExpression_6= rulePolynomialIndexExpression | this_FuzzyIndexExpression_7= ruleFuzzyIndexExpression | this_Reductions_8= ruleReductions | this_ConvolutionExpression_9= ruleConvolutionExpression | this_MultiArgExpression_10= ruleMultiArgExpression | this_ExternalMultiArgExpression_11= ruleExternalMultiArgExpression | this_SelectExpression_12= ruleSelectExpression )
            {
            // InternalAlpha.g:4838:2: ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_VariableExpression_4= ruleVariableExpression | this_IndexExpression_5= ruleIndexExpression | this_PolynomialIndexExpression_6= rulePolynomialIndexExpression | this_FuzzyIndexExpression_7= ruleFuzzyIndexExpression | this_Reductions_8= ruleReductions | this_ConvolutionExpression_9= ruleConvolutionExpression | this_MultiArgExpression_10= ruleMultiArgExpression | this_ExternalMultiArgExpression_11= ruleExternalMultiArgExpression | this_SelectExpression_12= ruleSelectExpression )
            int alt74=11;
            alt74 = dfa74.predict(input);
            switch (alt74) {
                case 1 :
                    // InternalAlpha.g:4839:3: (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' )
                    {
                    // InternalAlpha.g:4839:3: (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' )
                    // InternalAlpha.g:4840:4: otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')'
                    {
                    otherlv_0=(Token)match(input,15,FOLLOW_22); 

                    				newLeafNode(otherlv_0, grammarAccess.getAlphaUnaryTerminalExpressionAccess().getLeftParenthesisKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getAlphaExpressionParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_9);
                    this_AlphaExpression_1=ruleAlphaExpression();

                    state._fsp--;


                    				current = this_AlphaExpression_1;
                    				afterParserOrEnumRuleCall();
                    			
                    otherlv_2=(Token)match(input,16,FOLLOW_2); 

                    				newLeafNode(otherlv_2, grammarAccess.getAlphaUnaryTerminalExpressionAccess().getRightParenthesisKeyword_0_2());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:4858:3: this_CaseExpression_3= ruleCaseExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getCaseExpressionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_CaseExpression_3=ruleCaseExpression();

                    state._fsp--;


                    			current = this_CaseExpression_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:4867:3: this_VariableExpression_4= ruleVariableExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getVariableExpressionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_VariableExpression_4=ruleVariableExpression();

                    state._fsp--;


                    			current = this_VariableExpression_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalAlpha.g:4876:3: this_IndexExpression_5= ruleIndexExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getIndexExpressionParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_IndexExpression_5=ruleIndexExpression();

                    state._fsp--;


                    			current = this_IndexExpression_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalAlpha.g:4885:3: this_PolynomialIndexExpression_6= rulePolynomialIndexExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getPolynomialIndexExpressionParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_PolynomialIndexExpression_6=rulePolynomialIndexExpression();

                    state._fsp--;


                    			current = this_PolynomialIndexExpression_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalAlpha.g:4894:3: this_FuzzyIndexExpression_7= ruleFuzzyIndexExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getFuzzyIndexExpressionParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_FuzzyIndexExpression_7=ruleFuzzyIndexExpression();

                    state._fsp--;


                    			current = this_FuzzyIndexExpression_7;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 7 :
                    // InternalAlpha.g:4903:3: this_Reductions_8= ruleReductions
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getReductionsParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_Reductions_8=ruleReductions();

                    state._fsp--;


                    			current = this_Reductions_8;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 8 :
                    // InternalAlpha.g:4912:3: this_ConvolutionExpression_9= ruleConvolutionExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getConvolutionExpressionParserRuleCall_7());
                    		
                    pushFollow(FOLLOW_2);
                    this_ConvolutionExpression_9=ruleConvolutionExpression();

                    state._fsp--;


                    			current = this_ConvolutionExpression_9;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 9 :
                    // InternalAlpha.g:4921:3: this_MultiArgExpression_10= ruleMultiArgExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getMultiArgExpressionParserRuleCall_8());
                    		
                    pushFollow(FOLLOW_2);
                    this_MultiArgExpression_10=ruleMultiArgExpression();

                    state._fsp--;


                    			current = this_MultiArgExpression_10;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 10 :
                    // InternalAlpha.g:4930:3: this_ExternalMultiArgExpression_11= ruleExternalMultiArgExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getExternalMultiArgExpressionParserRuleCall_9());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExternalMultiArgExpression_11=ruleExternalMultiArgExpression();

                    state._fsp--;


                    			current = this_ExternalMultiArgExpression_11;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 11 :
                    // InternalAlpha.g:4939:3: this_SelectExpression_12= ruleSelectExpression
                    {

                    			newCompositeNode(grammarAccess.getAlphaUnaryTerminalExpressionAccess().getSelectExpressionParserRuleCall_10());
                    		
                    pushFollow(FOLLOW_2);
                    this_SelectExpression_12=ruleSelectExpression();

                    state._fsp--;


                    			current = this_SelectExpression_12;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlphaUnaryTerminalExpression"


    // $ANTLR start "entryRuleReductions"
    // InternalAlpha.g:4951:1: entryRuleReductions returns [EObject current=null] : iv_ruleReductions= ruleReductions EOF ;
    public final EObject entryRuleReductions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReductions = null;


        try {
            // InternalAlpha.g:4951:51: (iv_ruleReductions= ruleReductions EOF )
            // InternalAlpha.g:4952:2: iv_ruleReductions= ruleReductions EOF
            {
             newCompositeNode(grammarAccess.getReductionsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReductions=ruleReductions();

            state._fsp--;

             current =iv_ruleReductions; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReductions"


    // $ANTLR start "ruleReductions"
    // InternalAlpha.g:4958:1: ruleReductions returns [EObject current=null] : (this_ReduceExpression_0= ruleReduceExpression | this_ArgReduceExpression_1= ruleArgReduceExpression | this_ExternalReduceExpression_2= ruleExternalReduceExpression | this_ExternalArgReduceExpression_3= ruleExternalArgReduceExpression | this_FuzzyReduceExpression_4= ruleFuzzyReduceExpression | this_FuzzyArgReduceExpression_5= ruleFuzzyArgReduceExpression | this_ExternalFuzzyReduceExpression_6= ruleExternalFuzzyReduceExpression | this_ExternalFuzzyArgReduceExpression_7= ruleExternalFuzzyArgReduceExpression ) ;
    public final EObject ruleReductions() throws RecognitionException {
        EObject current = null;

        EObject this_ReduceExpression_0 = null;

        EObject this_ArgReduceExpression_1 = null;

        EObject this_ExternalReduceExpression_2 = null;

        EObject this_ExternalArgReduceExpression_3 = null;

        EObject this_FuzzyReduceExpression_4 = null;

        EObject this_FuzzyArgReduceExpression_5 = null;

        EObject this_ExternalFuzzyReduceExpression_6 = null;

        EObject this_ExternalFuzzyArgReduceExpression_7 = null;



        	enterRule();

        try {
            // InternalAlpha.g:4964:2: ( (this_ReduceExpression_0= ruleReduceExpression | this_ArgReduceExpression_1= ruleArgReduceExpression | this_ExternalReduceExpression_2= ruleExternalReduceExpression | this_ExternalArgReduceExpression_3= ruleExternalArgReduceExpression | this_FuzzyReduceExpression_4= ruleFuzzyReduceExpression | this_FuzzyArgReduceExpression_5= ruleFuzzyArgReduceExpression | this_ExternalFuzzyReduceExpression_6= ruleExternalFuzzyReduceExpression | this_ExternalFuzzyArgReduceExpression_7= ruleExternalFuzzyArgReduceExpression ) )
            // InternalAlpha.g:4965:2: (this_ReduceExpression_0= ruleReduceExpression | this_ArgReduceExpression_1= ruleArgReduceExpression | this_ExternalReduceExpression_2= ruleExternalReduceExpression | this_ExternalArgReduceExpression_3= ruleExternalArgReduceExpression | this_FuzzyReduceExpression_4= ruleFuzzyReduceExpression | this_FuzzyArgReduceExpression_5= ruleFuzzyArgReduceExpression | this_ExternalFuzzyReduceExpression_6= ruleExternalFuzzyReduceExpression | this_ExternalFuzzyArgReduceExpression_7= ruleExternalFuzzyArgReduceExpression )
            {
            // InternalAlpha.g:4965:2: (this_ReduceExpression_0= ruleReduceExpression | this_ArgReduceExpression_1= ruleArgReduceExpression | this_ExternalReduceExpression_2= ruleExternalReduceExpression | this_ExternalArgReduceExpression_3= ruleExternalArgReduceExpression | this_FuzzyReduceExpression_4= ruleFuzzyReduceExpression | this_FuzzyArgReduceExpression_5= ruleFuzzyArgReduceExpression | this_ExternalFuzzyReduceExpression_6= ruleExternalFuzzyReduceExpression | this_ExternalFuzzyArgReduceExpression_7= ruleExternalFuzzyArgReduceExpression )
            int alt75=8;
            alt75 = dfa75.predict(input);
            switch (alt75) {
                case 1 :
                    // InternalAlpha.g:4966:3: this_ReduceExpression_0= ruleReduceExpression
                    {

                    			newCompositeNode(grammarAccess.getReductionsAccess().getReduceExpressionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ReduceExpression_0=ruleReduceExpression();

                    state._fsp--;


                    			current = this_ReduceExpression_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:4975:3: this_ArgReduceExpression_1= ruleArgReduceExpression
                    {

                    			newCompositeNode(grammarAccess.getReductionsAccess().getArgReduceExpressionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_ArgReduceExpression_1=ruleArgReduceExpression();

                    state._fsp--;


                    			current = this_ArgReduceExpression_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:4984:3: this_ExternalReduceExpression_2= ruleExternalReduceExpression
                    {

                    			newCompositeNode(grammarAccess.getReductionsAccess().getExternalReduceExpressionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExternalReduceExpression_2=ruleExternalReduceExpression();

                    state._fsp--;


                    			current = this_ExternalReduceExpression_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalAlpha.g:4993:3: this_ExternalArgReduceExpression_3= ruleExternalArgReduceExpression
                    {

                    			newCompositeNode(grammarAccess.getReductionsAccess().getExternalArgReduceExpressionParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExternalArgReduceExpression_3=ruleExternalArgReduceExpression();

                    state._fsp--;


                    			current = this_ExternalArgReduceExpression_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalAlpha.g:5002:3: this_FuzzyReduceExpression_4= ruleFuzzyReduceExpression
                    {

                    			newCompositeNode(grammarAccess.getReductionsAccess().getFuzzyReduceExpressionParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_FuzzyReduceExpression_4=ruleFuzzyReduceExpression();

                    state._fsp--;


                    			current = this_FuzzyReduceExpression_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalAlpha.g:5011:3: this_FuzzyArgReduceExpression_5= ruleFuzzyArgReduceExpression
                    {

                    			newCompositeNode(grammarAccess.getReductionsAccess().getFuzzyArgReduceExpressionParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_FuzzyArgReduceExpression_5=ruleFuzzyArgReduceExpression();

                    state._fsp--;


                    			current = this_FuzzyArgReduceExpression_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 7 :
                    // InternalAlpha.g:5020:3: this_ExternalFuzzyReduceExpression_6= ruleExternalFuzzyReduceExpression
                    {

                    			newCompositeNode(grammarAccess.getReductionsAccess().getExternalFuzzyReduceExpressionParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExternalFuzzyReduceExpression_6=ruleExternalFuzzyReduceExpression();

                    state._fsp--;


                    			current = this_ExternalFuzzyReduceExpression_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 8 :
                    // InternalAlpha.g:5029:3: this_ExternalFuzzyArgReduceExpression_7= ruleExternalFuzzyArgReduceExpression
                    {

                    			newCompositeNode(grammarAccess.getReductionsAccess().getExternalFuzzyArgReduceExpressionParserRuleCall_7());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExternalFuzzyArgReduceExpression_7=ruleExternalFuzzyArgReduceExpression();

                    state._fsp--;


                    			current = this_ExternalFuzzyArgReduceExpression_7;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReductions"


    // $ANTLR start "entryRuleIfExpression"
    // InternalAlpha.g:5041:1: entryRuleIfExpression returns [EObject current=null] : iv_ruleIfExpression= ruleIfExpression EOF ;
    public final EObject entryRuleIfExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIfExpression = null;


        try {
            // InternalAlpha.g:5041:53: (iv_ruleIfExpression= ruleIfExpression EOF )
            // InternalAlpha.g:5042:2: iv_ruleIfExpression= ruleIfExpression EOF
            {
             newCompositeNode(grammarAccess.getIfExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIfExpression=ruleIfExpression();

            state._fsp--;

             current =iv_ruleIfExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIfExpression"


    // $ANTLR start "ruleIfExpression"
    // InternalAlpha.g:5048:1: ruleIfExpression returns [EObject current=null] : (otherlv_0= 'if' ( (lv_condExpr_1_0= ruleAlphaExpression ) ) otherlv_2= 'then' ( (lv_thenExpr_3_0= ruleAlphaExpression ) ) otherlv_4= 'else' ( (lv_elseExpr_5_0= ruleAlphaExpression ) ) ) ;
    public final EObject ruleIfExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_condExpr_1_0 = null;

        EObject lv_thenExpr_3_0 = null;

        EObject lv_elseExpr_5_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5054:2: ( (otherlv_0= 'if' ( (lv_condExpr_1_0= ruleAlphaExpression ) ) otherlv_2= 'then' ( (lv_thenExpr_3_0= ruleAlphaExpression ) ) otherlv_4= 'else' ( (lv_elseExpr_5_0= ruleAlphaExpression ) ) ) )
            // InternalAlpha.g:5055:2: (otherlv_0= 'if' ( (lv_condExpr_1_0= ruleAlphaExpression ) ) otherlv_2= 'then' ( (lv_thenExpr_3_0= ruleAlphaExpression ) ) otherlv_4= 'else' ( (lv_elseExpr_5_0= ruleAlphaExpression ) ) )
            {
            // InternalAlpha.g:5055:2: (otherlv_0= 'if' ( (lv_condExpr_1_0= ruleAlphaExpression ) ) otherlv_2= 'then' ( (lv_thenExpr_3_0= ruleAlphaExpression ) ) otherlv_4= 'else' ( (lv_elseExpr_5_0= ruleAlphaExpression ) ) )
            // InternalAlpha.g:5056:3: otherlv_0= 'if' ( (lv_condExpr_1_0= ruleAlphaExpression ) ) otherlv_2= 'then' ( (lv_thenExpr_3_0= ruleAlphaExpression ) ) otherlv_4= 'else' ( (lv_elseExpr_5_0= ruleAlphaExpression ) )
            {
            otherlv_0=(Token)match(input,59,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getIfExpressionAccess().getIfKeyword_0());
            		
            // InternalAlpha.g:5060:3: ( (lv_condExpr_1_0= ruleAlphaExpression ) )
            // InternalAlpha.g:5061:4: (lv_condExpr_1_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:5061:4: (lv_condExpr_1_0= ruleAlphaExpression )
            // InternalAlpha.g:5062:5: lv_condExpr_1_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getIfExpressionAccess().getCondExprAlphaExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_66);
            lv_condExpr_1_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getIfExpressionRule());
            					}
            					set(
            						current,
            						"condExpr",
            						lv_condExpr_1_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,60,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getIfExpressionAccess().getThenKeyword_2());
            		
            // InternalAlpha.g:5083:3: ( (lv_thenExpr_3_0= ruleAlphaExpression ) )
            // InternalAlpha.g:5084:4: (lv_thenExpr_3_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:5084:4: (lv_thenExpr_3_0= ruleAlphaExpression )
            // InternalAlpha.g:5085:5: lv_thenExpr_3_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getIfExpressionAccess().getThenExprAlphaExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_67);
            lv_thenExpr_3_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getIfExpressionRule());
            					}
            					set(
            						current,
            						"thenExpr",
            						lv_thenExpr_3_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,31,FOLLOW_22); 

            			newLeafNode(otherlv_4, grammarAccess.getIfExpressionAccess().getElseKeyword_4());
            		
            // InternalAlpha.g:5106:3: ( (lv_elseExpr_5_0= ruleAlphaExpression ) )
            // InternalAlpha.g:5107:4: (lv_elseExpr_5_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:5107:4: (lv_elseExpr_5_0= ruleAlphaExpression )
            // InternalAlpha.g:5108:5: lv_elseExpr_5_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getIfExpressionAccess().getElseExprAlphaExpressionParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_2);
            lv_elseExpr_5_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getIfExpressionRule());
            					}
            					set(
            						current,
            						"elseExpr",
            						lv_elseExpr_5_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIfExpression"


    // $ANTLR start "entryRuleRestrictExpression"
    // InternalAlpha.g:5129:1: entryRuleRestrictExpression returns [EObject current=null] : iv_ruleRestrictExpression= ruleRestrictExpression EOF ;
    public final EObject entryRuleRestrictExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRestrictExpression = null;


        try {
            // InternalAlpha.g:5129:59: (iv_ruleRestrictExpression= ruleRestrictExpression EOF )
            // InternalAlpha.g:5130:2: iv_ruleRestrictExpression= ruleRestrictExpression EOF
            {
             newCompositeNode(grammarAccess.getRestrictExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRestrictExpression=ruleRestrictExpression();

            state._fsp--;

             current =iv_ruleRestrictExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRestrictExpression"


    // $ANTLR start "ruleRestrictExpression"
    // InternalAlpha.g:5136:1: ruleRestrictExpression returns [EObject current=null] : ( ( ( ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) ) ) otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) ) | (otherlv_3= '{' ( (lv_domainExpr_4_0= ruleCalculatorExpression ) ) otherlv_5= '}' otherlv_6= ':' ( (lv_expr_7_0= ruleAlphaExpression ) ) ) ) ;
    public final EObject ruleRestrictExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        EObject lv_domainExpr_0_1 = null;

        EObject lv_domainExpr_0_2 = null;

        EObject lv_expr_2_0 = null;

        EObject lv_domainExpr_4_0 = null;

        EObject lv_expr_7_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5142:2: ( ( ( ( ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) ) ) otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) ) | (otherlv_3= '{' ( (lv_domainExpr_4_0= ruleCalculatorExpression ) ) otherlv_5= '}' otherlv_6= ':' ( (lv_expr_7_0= ruleAlphaExpression ) ) ) ) )
            // InternalAlpha.g:5143:2: ( ( ( ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) ) ) otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) ) | (otherlv_3= '{' ( (lv_domainExpr_4_0= ruleCalculatorExpression ) ) otherlv_5= '}' otherlv_6= ':' ( (lv_expr_7_0= ruleAlphaExpression ) ) ) )
            {
            // InternalAlpha.g:5143:2: ( ( ( ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) ) ) otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) ) | (otherlv_3= '{' ( (lv_domainExpr_4_0= ruleCalculatorExpression ) ) otherlv_5= '}' otherlv_6= ':' ( (lv_expr_7_0= ruleAlphaExpression ) ) ) )
            int alt77=2;
            alt77 = dfa77.predict(input);
            switch (alt77) {
                case 1 :
                    // InternalAlpha.g:5144:3: ( ( ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) ) ) otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) )
                    {
                    // InternalAlpha.g:5144:3: ( ( ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) ) ) otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) )
                    // InternalAlpha.g:5145:4: ( ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) ) ) otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) )
                    {
                    // InternalAlpha.g:5145:4: ( ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) ) )
                    // InternalAlpha.g:5146:5: ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) )
                    {
                    // InternalAlpha.g:5146:5: ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) )
                    // InternalAlpha.g:5147:6: (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation )
                    {
                    // InternalAlpha.g:5147:6: (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation )
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==19) ) {
                        int LA76_1 = input.LA(2);

                        if ( (LA76_1==33) ) {
                            alt76=2;
                        }
                        else if ( (LA76_1==37) ) {
                            alt76=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 76, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA76_0==42) ) {
                        alt76=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 76, 0, input);

                        throw nvae;
                    }
                    switch (alt76) {
                        case 1 :
                            // InternalAlpha.g:5148:7: lv_domainExpr_0_1= ruleJNIDomain
                            {

                            							newCompositeNode(grammarAccess.getRestrictExpressionAccess().getDomainExprJNIDomainParserRuleCall_0_0_0_0());
                            						
                            pushFollow(FOLLOW_26);
                            lv_domainExpr_0_1=ruleJNIDomain();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getRestrictExpressionRule());
                            							}
                            							set(
                            								current,
                            								"domainExpr",
                            								lv_domainExpr_0_1,
                            								"alpha.model.Alpha.JNIDomain");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;
                        case 2 :
                            // InternalAlpha.g:5164:7: lv_domainExpr_0_2= ruleJNIDomainInArrayNotation
                            {

                            							newCompositeNode(grammarAccess.getRestrictExpressionAccess().getDomainExprJNIDomainInArrayNotationParserRuleCall_0_0_0_1());
                            						
                            pushFollow(FOLLOW_26);
                            lv_domainExpr_0_2=ruleJNIDomainInArrayNotation();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getRestrictExpressionRule());
                            							}
                            							set(
                            								current,
                            								"domainExpr",
                            								lv_domainExpr_0_2,
                            								"alpha.model.Alpha.JNIDomainInArrayNotation");
                            							afterParserOrEnumRuleCall();
                            						

                            }
                            break;

                    }


                    }


                    }

                    otherlv_1=(Token)match(input,33,FOLLOW_22); 

                    				newLeafNode(otherlv_1, grammarAccess.getRestrictExpressionAccess().getColonKeyword_0_1());
                    			
                    // InternalAlpha.g:5186:4: ( (lv_expr_2_0= ruleAlphaExpression ) )
                    // InternalAlpha.g:5187:5: (lv_expr_2_0= ruleAlphaExpression )
                    {
                    // InternalAlpha.g:5187:5: (lv_expr_2_0= ruleAlphaExpression )
                    // InternalAlpha.g:5188:6: lv_expr_2_0= ruleAlphaExpression
                    {

                    						newCompositeNode(grammarAccess.getRestrictExpressionAccess().getExprAlphaExpressionParserRuleCall_0_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_expr_2_0=ruleAlphaExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRestrictExpressionRule());
                    						}
                    						set(
                    							current,
                    							"expr",
                    							lv_expr_2_0,
                    							"alpha.model.Alpha.AlphaExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:5207:3: (otherlv_3= '{' ( (lv_domainExpr_4_0= ruleCalculatorExpression ) ) otherlv_5= '}' otherlv_6= ':' ( (lv_expr_7_0= ruleAlphaExpression ) ) )
                    {
                    // InternalAlpha.g:5207:3: (otherlv_3= '{' ( (lv_domainExpr_4_0= ruleCalculatorExpression ) ) otherlv_5= '}' otherlv_6= ':' ( (lv_expr_7_0= ruleAlphaExpression ) ) )
                    // InternalAlpha.g:5208:4: otherlv_3= '{' ( (lv_domainExpr_4_0= ruleCalculatorExpression ) ) otherlv_5= '}' otherlv_6= ':' ( (lv_expr_7_0= ruleAlphaExpression ) )
                    {
                    otherlv_3=(Token)match(input,19,FOLLOW_20); 

                    				newLeafNode(otherlv_3, grammarAccess.getRestrictExpressionAccess().getLeftCurlyBracketKeyword_1_0());
                    			
                    // InternalAlpha.g:5212:4: ( (lv_domainExpr_4_0= ruleCalculatorExpression ) )
                    // InternalAlpha.g:5213:5: (lv_domainExpr_4_0= ruleCalculatorExpression )
                    {
                    // InternalAlpha.g:5213:5: (lv_domainExpr_4_0= ruleCalculatorExpression )
                    // InternalAlpha.g:5214:6: lv_domainExpr_4_0= ruleCalculatorExpression
                    {

                    						newCompositeNode(grammarAccess.getRestrictExpressionAccess().getDomainExprCalculatorExpressionParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_30);
                    lv_domainExpr_4_0=ruleCalculatorExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRestrictExpressionRule());
                    						}
                    						set(
                    							current,
                    							"domainExpr",
                    							lv_domainExpr_4_0,
                    							"alpha.model.Alpha.CalculatorExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_5=(Token)match(input,20,FOLLOW_26); 

                    				newLeafNode(otherlv_5, grammarAccess.getRestrictExpressionAccess().getRightCurlyBracketKeyword_1_2());
                    			
                    otherlv_6=(Token)match(input,33,FOLLOW_22); 

                    				newLeafNode(otherlv_6, grammarAccess.getRestrictExpressionAccess().getColonKeyword_1_3());
                    			
                    // InternalAlpha.g:5239:4: ( (lv_expr_7_0= ruleAlphaExpression ) )
                    // InternalAlpha.g:5240:5: (lv_expr_7_0= ruleAlphaExpression )
                    {
                    // InternalAlpha.g:5240:5: (lv_expr_7_0= ruleAlphaExpression )
                    // InternalAlpha.g:5241:6: lv_expr_7_0= ruleAlphaExpression
                    {

                    						newCompositeNode(grammarAccess.getRestrictExpressionAccess().getExprAlphaExpressionParserRuleCall_1_4_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_expr_7_0=ruleAlphaExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRestrictExpressionRule());
                    						}
                    						set(
                    							current,
                    							"expr",
                    							lv_expr_7_0,
                    							"alpha.model.Alpha.AlphaExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRestrictExpression"


    // $ANTLR start "entryRuleAutoRestrictExpression"
    // InternalAlpha.g:5263:1: entryRuleAutoRestrictExpression returns [EObject current=null] : iv_ruleAutoRestrictExpression= ruleAutoRestrictExpression EOF ;
    public final EObject entryRuleAutoRestrictExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAutoRestrictExpression = null;


        try {
            // InternalAlpha.g:5263:63: (iv_ruleAutoRestrictExpression= ruleAutoRestrictExpression EOF )
            // InternalAlpha.g:5264:2: iv_ruleAutoRestrictExpression= ruleAutoRestrictExpression EOF
            {
             newCompositeNode(grammarAccess.getAutoRestrictExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAutoRestrictExpression=ruleAutoRestrictExpression();

            state._fsp--;

             current =iv_ruleAutoRestrictExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAutoRestrictExpression"


    // $ANTLR start "ruleAutoRestrictExpression"
    // InternalAlpha.g:5270:1: ruleAutoRestrictExpression returns [EObject current=null] : (otherlv_0= 'auto' otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) ) ;
    public final EObject ruleAutoRestrictExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        EObject lv_expr_2_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5276:2: ( (otherlv_0= 'auto' otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) ) )
            // InternalAlpha.g:5277:2: (otherlv_0= 'auto' otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) )
            {
            // InternalAlpha.g:5277:2: (otherlv_0= 'auto' otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) )
            // InternalAlpha.g:5278:3: otherlv_0= 'auto' otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) )
            {
            otherlv_0=(Token)match(input,61,FOLLOW_26); 

            			newLeafNode(otherlv_0, grammarAccess.getAutoRestrictExpressionAccess().getAutoKeyword_0());
            		
            otherlv_1=(Token)match(input,33,FOLLOW_22); 

            			newLeafNode(otherlv_1, grammarAccess.getAutoRestrictExpressionAccess().getColonKeyword_1());
            		
            // InternalAlpha.g:5286:3: ( (lv_expr_2_0= ruleAlphaExpression ) )
            // InternalAlpha.g:5287:4: (lv_expr_2_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:5287:4: (lv_expr_2_0= ruleAlphaExpression )
            // InternalAlpha.g:5288:5: lv_expr_2_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getAutoRestrictExpressionAccess().getExprAlphaExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_expr_2_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getAutoRestrictExpressionRule());
            					}
            					set(
            						current,
            						"expr",
            						lv_expr_2_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAutoRestrictExpression"


    // $ANTLR start "entryRuleCaseExpression"
    // InternalAlpha.g:5309:1: entryRuleCaseExpression returns [EObject current=null] : iv_ruleCaseExpression= ruleCaseExpression EOF ;
    public final EObject entryRuleCaseExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCaseExpression = null;


        try {
            // InternalAlpha.g:5309:55: (iv_ruleCaseExpression= ruleCaseExpression EOF )
            // InternalAlpha.g:5310:2: iv_ruleCaseExpression= ruleCaseExpression EOF
            {
             newCompositeNode(grammarAccess.getCaseExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCaseExpression=ruleCaseExpression();

            state._fsp--;

             current =iv_ruleCaseExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCaseExpression"


    // $ANTLR start "ruleCaseExpression"
    // InternalAlpha.g:5316:1: ruleCaseExpression returns [EObject current=null] : (otherlv_0= 'case' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '{' ( ( (lv_exprs_3_0= ruleAlphaExpression ) ) otherlv_4= ';' )+ otherlv_5= '}' ) ;
    public final EObject ruleCaseExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        EObject lv_exprs_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5322:2: ( (otherlv_0= 'case' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '{' ( ( (lv_exprs_3_0= ruleAlphaExpression ) ) otherlv_4= ';' )+ otherlv_5= '}' ) )
            // InternalAlpha.g:5323:2: (otherlv_0= 'case' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '{' ( ( (lv_exprs_3_0= ruleAlphaExpression ) ) otherlv_4= ';' )+ otherlv_5= '}' )
            {
            // InternalAlpha.g:5323:2: (otherlv_0= 'case' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '{' ( ( (lv_exprs_3_0= ruleAlphaExpression ) ) otherlv_4= ';' )+ otherlv_5= '}' )
            // InternalAlpha.g:5324:3: otherlv_0= 'case' ( (lv_name_1_0= RULE_ID ) )? otherlv_2= '{' ( ( (lv_exprs_3_0= ruleAlphaExpression ) ) otherlv_4= ';' )+ otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,62,FOLLOW_68); 

            			newLeafNode(otherlv_0, grammarAccess.getCaseExpressionAccess().getCaseKeyword_0());
            		
            // InternalAlpha.g:5328:3: ( (lv_name_1_0= RULE_ID ) )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==RULE_ID) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalAlpha.g:5329:4: (lv_name_1_0= RULE_ID )
                    {
                    // InternalAlpha.g:5329:4: (lv_name_1_0= RULE_ID )
                    // InternalAlpha.g:5330:5: lv_name_1_0= RULE_ID
                    {
                    lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_10); 

                    					newLeafNode(lv_name_1_0, grammarAccess.getCaseExpressionAccess().getNameIDTerminalRuleCall_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getCaseExpressionRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"name",
                    						lv_name_1_0,
                    						"alpha.model.Alpha.ID");
                    				

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,19,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getCaseExpressionAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalAlpha.g:5350:3: ( ( (lv_exprs_3_0= ruleAlphaExpression ) ) otherlv_4= ';' )+
            int cnt79=0;
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( ((LA79_0>=RULE_ID && LA79_0<=RULE_INT)||LA79_0==RULE_BOOLEAN||LA79_0==15||LA79_0==19||LA79_0==39||(LA79_0>=42 && LA79_0<=45)||(LA79_0>=49 && LA79_0<=50)||LA79_0==59||(LA79_0>=61 && LA79_0<=62)||(LA79_0>=64 && LA79_0<=68)||(LA79_0>=70 && LA79_0<=75)||LA79_0==77) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // InternalAlpha.g:5351:4: ( (lv_exprs_3_0= ruleAlphaExpression ) ) otherlv_4= ';'
            	    {
            	    // InternalAlpha.g:5351:4: ( (lv_exprs_3_0= ruleAlphaExpression ) )
            	    // InternalAlpha.g:5352:5: (lv_exprs_3_0= ruleAlphaExpression )
            	    {
            	    // InternalAlpha.g:5352:5: (lv_exprs_3_0= ruleAlphaExpression )
            	    // InternalAlpha.g:5353:6: lv_exprs_3_0= ruleAlphaExpression
            	    {

            	    						newCompositeNode(grammarAccess.getCaseExpressionAccess().getExprsAlphaExpressionParserRuleCall_3_0_0());
            	    					
            	    pushFollow(FOLLOW_62);
            	    lv_exprs_3_0=ruleAlphaExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCaseExpressionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"exprs",
            	    							lv_exprs_3_0,
            	    							"alpha.model.Alpha.AlphaExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    otherlv_4=(Token)match(input,34,FOLLOW_69); 

            	    				newLeafNode(otherlv_4, grammarAccess.getCaseExpressionAccess().getSemicolonKeyword_3_1());
            	    			

            	    }
            	    break;

            	default :
            	    if ( cnt79 >= 1 ) break loop79;
                        EarlyExitException eee =
                            new EarlyExitException(79, input);
                        throw eee;
                }
                cnt79++;
            } while (true);

            otherlv_5=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getCaseExpressionAccess().getRightCurlyBracketKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCaseExpression"


    // $ANTLR start "entryRuleDependenceExpression"
    // InternalAlpha.g:5383:1: entryRuleDependenceExpression returns [EObject current=null] : iv_ruleDependenceExpression= ruleDependenceExpression EOF ;
    public final EObject entryRuleDependenceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDependenceExpression = null;


        try {
            // InternalAlpha.g:5383:61: (iv_ruleDependenceExpression= ruleDependenceExpression EOF )
            // InternalAlpha.g:5384:2: iv_ruleDependenceExpression= ruleDependenceExpression EOF
            {
             newCompositeNode(grammarAccess.getDependenceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDependenceExpression=ruleDependenceExpression();

            state._fsp--;

             current =iv_ruleDependenceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDependenceExpression"


    // $ANTLR start "ruleDependenceExpression"
    // InternalAlpha.g:5390:1: ruleDependenceExpression returns [EObject current=null] : ( ( ( (lv_functionExpr_0_0= ruleJNIFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) ) | ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation ) ) ) | ( ( (lv_expr_5_0= ruleConstantExpression ) ) ( (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation ) ) ) ) ;
    public final EObject ruleDependenceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_functionExpr_0_0 = null;

        EObject lv_expr_2_0 = null;

        EObject lv_expr_3_0 = null;

        EObject lv_functionExpr_4_0 = null;

        EObject lv_expr_5_0 = null;

        EObject lv_functionExpr_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5396:2: ( ( ( ( (lv_functionExpr_0_0= ruleJNIFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) ) | ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation ) ) ) | ( ( (lv_expr_5_0= ruleConstantExpression ) ) ( (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation ) ) ) ) )
            // InternalAlpha.g:5397:2: ( ( ( (lv_functionExpr_0_0= ruleJNIFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) ) | ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation ) ) ) | ( ( (lv_expr_5_0= ruleConstantExpression ) ) ( (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation ) ) ) )
            {
            // InternalAlpha.g:5397:2: ( ( ( (lv_functionExpr_0_0= ruleJNIFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) ) | ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation ) ) ) | ( ( (lv_expr_5_0= ruleConstantExpression ) ) ( (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation ) ) ) )
            int alt80=3;
            switch ( input.LA(1) ) {
            case 15:
                {
                alt80=1;
                }
                break;
            case RULE_ID:
                {
                alt80=2;
                }
                break;
            case RULE_INT:
            case RULE_BOOLEAN:
            case 44:
                {
                alt80=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // InternalAlpha.g:5398:3: ( ( (lv_functionExpr_0_0= ruleJNIFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) )
                    {
                    // InternalAlpha.g:5398:3: ( ( (lv_functionExpr_0_0= ruleJNIFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) )
                    // InternalAlpha.g:5399:4: ( (lv_functionExpr_0_0= ruleJNIFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) )
                    {
                    // InternalAlpha.g:5399:4: ( (lv_functionExpr_0_0= ruleJNIFunction ) )
                    // InternalAlpha.g:5400:5: (lv_functionExpr_0_0= ruleJNIFunction )
                    {
                    // InternalAlpha.g:5400:5: (lv_functionExpr_0_0= ruleJNIFunction )
                    // InternalAlpha.g:5401:6: lv_functionExpr_0_0= ruleJNIFunction
                    {

                    						newCompositeNode(grammarAccess.getDependenceExpressionAccess().getFunctionExprJNIFunctionParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_70);
                    lv_functionExpr_0_0=ruleJNIFunction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"functionExpr",
                    							lv_functionExpr_0_0,
                    							"alpha.model.Alpha.JNIFunction");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,63,FOLLOW_22); 

                    				newLeafNode(otherlv_1, grammarAccess.getDependenceExpressionAccess().getCommercialAtKeyword_0_1());
                    			
                    // InternalAlpha.g:5422:4: ( (lv_expr_2_0= ruleAlphaTerminalExpression ) )
                    // InternalAlpha.g:5423:5: (lv_expr_2_0= ruleAlphaTerminalExpression )
                    {
                    // InternalAlpha.g:5423:5: (lv_expr_2_0= ruleAlphaTerminalExpression )
                    // InternalAlpha.g:5424:6: lv_expr_2_0= ruleAlphaTerminalExpression
                    {

                    						newCompositeNode(grammarAccess.getDependenceExpressionAccess().getExprAlphaTerminalExpressionParserRuleCall_0_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_expr_2_0=ruleAlphaTerminalExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"expr",
                    							lv_expr_2_0,
                    							"alpha.model.Alpha.AlphaTerminalExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:5443:3: ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation ) ) )
                    {
                    // InternalAlpha.g:5443:3: ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation ) ) )
                    // InternalAlpha.g:5444:4: ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation ) )
                    {
                    // InternalAlpha.g:5444:4: ( (lv_expr_3_0= ruleVariableExpression ) )
                    // InternalAlpha.g:5445:5: (lv_expr_3_0= ruleVariableExpression )
                    {
                    // InternalAlpha.g:5445:5: (lv_expr_3_0= ruleVariableExpression )
                    // InternalAlpha.g:5446:6: lv_expr_3_0= ruleVariableExpression
                    {

                    						newCompositeNode(grammarAccess.getDependenceExpressionAccess().getExprVariableExpressionParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_36);
                    lv_expr_3_0=ruleVariableExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"expr",
                    							lv_expr_3_0,
                    							"alpha.model.Alpha.VariableExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:5463:4: ( (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation ) )
                    // InternalAlpha.g:5464:5: (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation )
                    {
                    // InternalAlpha.g:5464:5: (lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation )
                    // InternalAlpha.g:5465:6: lv_functionExpr_4_0= ruleJNIFunctionInArrayNotation
                    {

                    						newCompositeNode(grammarAccess.getDependenceExpressionAccess().getFunctionExprJNIFunctionInArrayNotationParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_functionExpr_4_0=ruleJNIFunctionInArrayNotation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"functionExpr",
                    							lv_functionExpr_4_0,
                    							"alpha.model.Alpha.JNIFunctionInArrayNotation");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalAlpha.g:5484:3: ( ( (lv_expr_5_0= ruleConstantExpression ) ) ( (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation ) ) )
                    {
                    // InternalAlpha.g:5484:3: ( ( (lv_expr_5_0= ruleConstantExpression ) ) ( (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation ) ) )
                    // InternalAlpha.g:5485:4: ( (lv_expr_5_0= ruleConstantExpression ) ) ( (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation ) )
                    {
                    // InternalAlpha.g:5485:4: ( (lv_expr_5_0= ruleConstantExpression ) )
                    // InternalAlpha.g:5486:5: (lv_expr_5_0= ruleConstantExpression )
                    {
                    // InternalAlpha.g:5486:5: (lv_expr_5_0= ruleConstantExpression )
                    // InternalAlpha.g:5487:6: lv_expr_5_0= ruleConstantExpression
                    {

                    						newCompositeNode(grammarAccess.getDependenceExpressionAccess().getExprConstantExpressionParserRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_36);
                    lv_expr_5_0=ruleConstantExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"expr",
                    							lv_expr_5_0,
                    							"alpha.model.Alpha.ConstantExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:5504:4: ( (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation ) )
                    // InternalAlpha.g:5505:5: (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation )
                    {
                    // InternalAlpha.g:5505:5: (lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation )
                    // InternalAlpha.g:5506:6: lv_functionExpr_6_0= ruleJNIFunctionInArrayNotation
                    {

                    						newCompositeNode(grammarAccess.getDependenceExpressionAccess().getFunctionExprJNIFunctionInArrayNotationParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_functionExpr_6_0=ruleJNIFunctionInArrayNotation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"functionExpr",
                    							lv_functionExpr_6_0,
                    							"alpha.model.Alpha.JNIFunctionInArrayNotation");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDependenceExpression"


    // $ANTLR start "entryRuleFuzzyDependenceExpression"
    // InternalAlpha.g:5528:1: entryRuleFuzzyDependenceExpression returns [EObject current=null] : iv_ruleFuzzyDependenceExpression= ruleFuzzyDependenceExpression EOF ;
    public final EObject entryRuleFuzzyDependenceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFuzzyDependenceExpression = null;


        try {
            // InternalAlpha.g:5528:66: (iv_ruleFuzzyDependenceExpression= ruleFuzzyDependenceExpression EOF )
            // InternalAlpha.g:5529:2: iv_ruleFuzzyDependenceExpression= ruleFuzzyDependenceExpression EOF
            {
             newCompositeNode(grammarAccess.getFuzzyDependenceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFuzzyDependenceExpression=ruleFuzzyDependenceExpression();

            state._fsp--;

             current =iv_ruleFuzzyDependenceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFuzzyDependenceExpression"


    // $ANTLR start "ruleFuzzyDependenceExpression"
    // InternalAlpha.g:5535:1: ruleFuzzyDependenceExpression returns [EObject current=null] : ( ( ( (lv_fuzzyFunction_0_0= ruleFuzzyFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) ) | ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation ) ) ) ) ;
    public final EObject ruleFuzzyDependenceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_fuzzyFunction_0_0 = null;

        EObject lv_expr_2_0 = null;

        EObject lv_expr_3_0 = null;

        EObject lv_fuzzyFunction_4_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5541:2: ( ( ( ( (lv_fuzzyFunction_0_0= ruleFuzzyFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) ) | ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation ) ) ) ) )
            // InternalAlpha.g:5542:2: ( ( ( (lv_fuzzyFunction_0_0= ruleFuzzyFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) ) | ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation ) ) ) )
            {
            // InternalAlpha.g:5542:2: ( ( ( (lv_fuzzyFunction_0_0= ruleFuzzyFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) ) | ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation ) ) ) )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==15) ) {
                alt81=1;
            }
            else if ( (LA81_0==RULE_ID) ) {
                alt81=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }
            switch (alt81) {
                case 1 :
                    // InternalAlpha.g:5543:3: ( ( (lv_fuzzyFunction_0_0= ruleFuzzyFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) )
                    {
                    // InternalAlpha.g:5543:3: ( ( (lv_fuzzyFunction_0_0= ruleFuzzyFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) ) )
                    // InternalAlpha.g:5544:4: ( (lv_fuzzyFunction_0_0= ruleFuzzyFunction ) ) otherlv_1= '@' ( (lv_expr_2_0= ruleAlphaTerminalExpression ) )
                    {
                    // InternalAlpha.g:5544:4: ( (lv_fuzzyFunction_0_0= ruleFuzzyFunction ) )
                    // InternalAlpha.g:5545:5: (lv_fuzzyFunction_0_0= ruleFuzzyFunction )
                    {
                    // InternalAlpha.g:5545:5: (lv_fuzzyFunction_0_0= ruleFuzzyFunction )
                    // InternalAlpha.g:5546:6: lv_fuzzyFunction_0_0= ruleFuzzyFunction
                    {

                    						newCompositeNode(grammarAccess.getFuzzyDependenceExpressionAccess().getFuzzyFunctionFuzzyFunctionParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_70);
                    lv_fuzzyFunction_0_0=ruleFuzzyFunction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFuzzyDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"fuzzyFunction",
                    							lv_fuzzyFunction_0_0,
                    							"alpha.model.Alpha.FuzzyFunction");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,63,FOLLOW_22); 

                    				newLeafNode(otherlv_1, grammarAccess.getFuzzyDependenceExpressionAccess().getCommercialAtKeyword_0_1());
                    			
                    // InternalAlpha.g:5567:4: ( (lv_expr_2_0= ruleAlphaTerminalExpression ) )
                    // InternalAlpha.g:5568:5: (lv_expr_2_0= ruleAlphaTerminalExpression )
                    {
                    // InternalAlpha.g:5568:5: (lv_expr_2_0= ruleAlphaTerminalExpression )
                    // InternalAlpha.g:5569:6: lv_expr_2_0= ruleAlphaTerminalExpression
                    {

                    						newCompositeNode(grammarAccess.getFuzzyDependenceExpressionAccess().getExprAlphaTerminalExpressionParserRuleCall_0_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_expr_2_0=ruleAlphaTerminalExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFuzzyDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"expr",
                    							lv_expr_2_0,
                    							"alpha.model.Alpha.AlphaTerminalExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:5588:3: ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation ) ) )
                    {
                    // InternalAlpha.g:5588:3: ( ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation ) ) )
                    // InternalAlpha.g:5589:4: ( (lv_expr_3_0= ruleVariableExpression ) ) ( (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation ) )
                    {
                    // InternalAlpha.g:5589:4: ( (lv_expr_3_0= ruleVariableExpression ) )
                    // InternalAlpha.g:5590:5: (lv_expr_3_0= ruleVariableExpression )
                    {
                    // InternalAlpha.g:5590:5: (lv_expr_3_0= ruleVariableExpression )
                    // InternalAlpha.g:5591:6: lv_expr_3_0= ruleVariableExpression
                    {

                    						newCompositeNode(grammarAccess.getFuzzyDependenceExpressionAccess().getExprVariableExpressionParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_71);
                    lv_expr_3_0=ruleVariableExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFuzzyDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"expr",
                    							lv_expr_3_0,
                    							"alpha.model.Alpha.VariableExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:5608:4: ( (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation ) )
                    // InternalAlpha.g:5609:5: (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation )
                    {
                    // InternalAlpha.g:5609:5: (lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation )
                    // InternalAlpha.g:5610:6: lv_fuzzyFunction_4_0= ruleFuzzyFunctionInArrayNotation
                    {

                    						newCompositeNode(grammarAccess.getFuzzyDependenceExpressionAccess().getFuzzyFunctionFuzzyFunctionInArrayNotationParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_fuzzyFunction_4_0=ruleFuzzyFunctionInArrayNotation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFuzzyDependenceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"fuzzyFunction",
                    							lv_fuzzyFunction_4_0,
                    							"alpha.model.Alpha.FuzzyFunctionInArrayNotation");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFuzzyDependenceExpression"


    // $ANTLR start "entryRuleIndexExpression"
    // InternalAlpha.g:5632:1: entryRuleIndexExpression returns [EObject current=null] : iv_ruleIndexExpression= ruleIndexExpression EOF ;
    public final EObject entryRuleIndexExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIndexExpression = null;


        try {
            // InternalAlpha.g:5632:56: (iv_ruleIndexExpression= ruleIndexExpression EOF )
            // InternalAlpha.g:5633:2: iv_ruleIndexExpression= ruleIndexExpression EOF
            {
             newCompositeNode(grammarAccess.getIndexExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIndexExpression=ruleIndexExpression();

            state._fsp--;

             current =iv_ruleIndexExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIndexExpression"


    // $ANTLR start "ruleIndexExpression"
    // InternalAlpha.g:5639:1: ruleIndexExpression returns [EObject current=null] : ( (otherlv_0= 'val' ( (lv_functionExpr_1_0= ruleJNIFunction ) ) ) | (otherlv_2= 'val' ( (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation ) ) ) ) ;
    public final EObject ruleIndexExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_functionExpr_1_0 = null;

        EObject lv_functionExpr_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5645:2: ( ( (otherlv_0= 'val' ( (lv_functionExpr_1_0= ruleJNIFunction ) ) ) | (otherlv_2= 'val' ( (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation ) ) ) ) )
            // InternalAlpha.g:5646:2: ( (otherlv_0= 'val' ( (lv_functionExpr_1_0= ruleJNIFunction ) ) ) | (otherlv_2= 'val' ( (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation ) ) ) )
            {
            // InternalAlpha.g:5646:2: ( (otherlv_0= 'val' ( (lv_functionExpr_1_0= ruleJNIFunction ) ) ) | (otherlv_2= 'val' ( (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation ) ) ) )
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==64) ) {
                int LA82_1 = input.LA(2);

                if ( (LA82_1==37) ) {
                    alt82=2;
                }
                else if ( (LA82_1==15) ) {
                    alt82=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 82, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }
            switch (alt82) {
                case 1 :
                    // InternalAlpha.g:5647:3: (otherlv_0= 'val' ( (lv_functionExpr_1_0= ruleJNIFunction ) ) )
                    {
                    // InternalAlpha.g:5647:3: (otherlv_0= 'val' ( (lv_functionExpr_1_0= ruleJNIFunction ) ) )
                    // InternalAlpha.g:5648:4: otherlv_0= 'val' ( (lv_functionExpr_1_0= ruleJNIFunction ) )
                    {
                    otherlv_0=(Token)match(input,64,FOLLOW_8); 

                    				newLeafNode(otherlv_0, grammarAccess.getIndexExpressionAccess().getValKeyword_0_0());
                    			
                    // InternalAlpha.g:5652:4: ( (lv_functionExpr_1_0= ruleJNIFunction ) )
                    // InternalAlpha.g:5653:5: (lv_functionExpr_1_0= ruleJNIFunction )
                    {
                    // InternalAlpha.g:5653:5: (lv_functionExpr_1_0= ruleJNIFunction )
                    // InternalAlpha.g:5654:6: lv_functionExpr_1_0= ruleJNIFunction
                    {

                    						newCompositeNode(grammarAccess.getIndexExpressionAccess().getFunctionExprJNIFunctionParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_functionExpr_1_0=ruleJNIFunction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIndexExpressionRule());
                    						}
                    						set(
                    							current,
                    							"functionExpr",
                    							lv_functionExpr_1_0,
                    							"alpha.model.Alpha.JNIFunction");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:5673:3: (otherlv_2= 'val' ( (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation ) ) )
                    {
                    // InternalAlpha.g:5673:3: (otherlv_2= 'val' ( (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation ) ) )
                    // InternalAlpha.g:5674:4: otherlv_2= 'val' ( (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation ) )
                    {
                    otherlv_2=(Token)match(input,64,FOLLOW_36); 

                    				newLeafNode(otherlv_2, grammarAccess.getIndexExpressionAccess().getValKeyword_1_0());
                    			
                    // InternalAlpha.g:5678:4: ( (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation ) )
                    // InternalAlpha.g:5679:5: (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation )
                    {
                    // InternalAlpha.g:5679:5: (lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation )
                    // InternalAlpha.g:5680:6: lv_functionExpr_3_0= ruleJNIFunctionInArrayNotation
                    {

                    						newCompositeNode(grammarAccess.getIndexExpressionAccess().getFunctionExprJNIFunctionInArrayNotationParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_functionExpr_3_0=ruleJNIFunctionInArrayNotation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIndexExpressionRule());
                    						}
                    						set(
                    							current,
                    							"functionExpr",
                    							lv_functionExpr_3_0,
                    							"alpha.model.Alpha.JNIFunctionInArrayNotation");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIndexExpression"


    // $ANTLR start "entryRulePolynomialIndexExpression"
    // InternalAlpha.g:5702:1: entryRulePolynomialIndexExpression returns [EObject current=null] : iv_rulePolynomialIndexExpression= rulePolynomialIndexExpression EOF ;
    public final EObject entryRulePolynomialIndexExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePolynomialIndexExpression = null;


        try {
            // InternalAlpha.g:5702:66: (iv_rulePolynomialIndexExpression= rulePolynomialIndexExpression EOF )
            // InternalAlpha.g:5703:2: iv_rulePolynomialIndexExpression= rulePolynomialIndexExpression EOF
            {
             newCompositeNode(grammarAccess.getPolynomialIndexExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePolynomialIndexExpression=rulePolynomialIndexExpression();

            state._fsp--;

             current =iv_rulePolynomialIndexExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePolynomialIndexExpression"


    // $ANTLR start "rulePolynomialIndexExpression"
    // InternalAlpha.g:5709:1: rulePolynomialIndexExpression returns [EObject current=null] : ( (otherlv_0= 'val' ( (lv_polynomialExpr_1_0= ruleJNIPolynomial ) ) ) | (otherlv_2= 'val' ( (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation ) ) ) ) ;
    public final EObject rulePolynomialIndexExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_polynomialExpr_1_0 = null;

        EObject lv_polynomialExpr_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5715:2: ( ( (otherlv_0= 'val' ( (lv_polynomialExpr_1_0= ruleJNIPolynomial ) ) ) | (otherlv_2= 'val' ( (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation ) ) ) ) )
            // InternalAlpha.g:5716:2: ( (otherlv_0= 'val' ( (lv_polynomialExpr_1_0= ruleJNIPolynomial ) ) ) | (otherlv_2= 'val' ( (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation ) ) ) )
            {
            // InternalAlpha.g:5716:2: ( (otherlv_0= 'val' ( (lv_polynomialExpr_1_0= ruleJNIPolynomial ) ) ) | (otherlv_2= 'val' ( (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation ) ) ) )
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==64) ) {
                int LA83_1 = input.LA(2);

                if ( (LA83_1==19) ) {
                    int LA83_2 = input.LA(3);

                    if ( (LA83_2==37) ) {
                        alt83=1;
                    }
                    else if ( ((LA83_2>=RULE_ID && LA83_2<=RULE_INT)||(LA83_2>=15 && LA83_2<=16)||(LA83_2>=43 && LA83_2<=46)||LA83_2==57) ) {
                        alt83=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 83, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 83, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }
            switch (alt83) {
                case 1 :
                    // InternalAlpha.g:5717:3: (otherlv_0= 'val' ( (lv_polynomialExpr_1_0= ruleJNIPolynomial ) ) )
                    {
                    // InternalAlpha.g:5717:3: (otherlv_0= 'val' ( (lv_polynomialExpr_1_0= ruleJNIPolynomial ) ) )
                    // InternalAlpha.g:5718:4: otherlv_0= 'val' ( (lv_polynomialExpr_1_0= ruleJNIPolynomial ) )
                    {
                    otherlv_0=(Token)match(input,64,FOLLOW_10); 

                    				newLeafNode(otherlv_0, grammarAccess.getPolynomialIndexExpressionAccess().getValKeyword_0_0());
                    			
                    // InternalAlpha.g:5722:4: ( (lv_polynomialExpr_1_0= ruleJNIPolynomial ) )
                    // InternalAlpha.g:5723:5: (lv_polynomialExpr_1_0= ruleJNIPolynomial )
                    {
                    // InternalAlpha.g:5723:5: (lv_polynomialExpr_1_0= ruleJNIPolynomial )
                    // InternalAlpha.g:5724:6: lv_polynomialExpr_1_0= ruleJNIPolynomial
                    {

                    						newCompositeNode(grammarAccess.getPolynomialIndexExpressionAccess().getPolynomialExprJNIPolynomialParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_polynomialExpr_1_0=ruleJNIPolynomial();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPolynomialIndexExpressionRule());
                    						}
                    						set(
                    							current,
                    							"polynomialExpr",
                    							lv_polynomialExpr_1_0,
                    							"alpha.model.Alpha.JNIPolynomial");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:5743:3: (otherlv_2= 'val' ( (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation ) ) )
                    {
                    // InternalAlpha.g:5743:3: (otherlv_2= 'val' ( (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation ) ) )
                    // InternalAlpha.g:5744:4: otherlv_2= 'val' ( (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation ) )
                    {
                    otherlv_2=(Token)match(input,64,FOLLOW_10); 

                    				newLeafNode(otherlv_2, grammarAccess.getPolynomialIndexExpressionAccess().getValKeyword_1_0());
                    			
                    // InternalAlpha.g:5748:4: ( (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation ) )
                    // InternalAlpha.g:5749:5: (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation )
                    {
                    // InternalAlpha.g:5749:5: (lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation )
                    // InternalAlpha.g:5750:6: lv_polynomialExpr_3_0= ruleJNIPolynomialInArrayNotation
                    {

                    						newCompositeNode(grammarAccess.getPolynomialIndexExpressionAccess().getPolynomialExprJNIPolynomialInArrayNotationParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_polynomialExpr_3_0=ruleJNIPolynomialInArrayNotation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPolynomialIndexExpressionRule());
                    						}
                    						set(
                    							current,
                    							"polynomialExpr",
                    							lv_polynomialExpr_3_0,
                    							"alpha.model.Alpha.JNIPolynomialInArrayNotation");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePolynomialIndexExpression"


    // $ANTLR start "entryRuleFuzzyIndexExpression"
    // InternalAlpha.g:5772:1: entryRuleFuzzyIndexExpression returns [EObject current=null] : iv_ruleFuzzyIndexExpression= ruleFuzzyIndexExpression EOF ;
    public final EObject entryRuleFuzzyIndexExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFuzzyIndexExpression = null;


        try {
            // InternalAlpha.g:5772:61: (iv_ruleFuzzyIndexExpression= ruleFuzzyIndexExpression EOF )
            // InternalAlpha.g:5773:2: iv_ruleFuzzyIndexExpression= ruleFuzzyIndexExpression EOF
            {
             newCompositeNode(grammarAccess.getFuzzyIndexExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFuzzyIndexExpression=ruleFuzzyIndexExpression();

            state._fsp--;

             current =iv_ruleFuzzyIndexExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFuzzyIndexExpression"


    // $ANTLR start "ruleFuzzyIndexExpression"
    // InternalAlpha.g:5779:1: ruleFuzzyIndexExpression returns [EObject current=null] : ( (otherlv_0= 'val' ( (lv_fuzzyFunction_1_0= ruleFuzzyFunction ) ) ) | ( (lv_fuzzyFunction_2_0= ruleFuzzyFunctionInArrayNotation ) ) ) ;
    public final EObject ruleFuzzyIndexExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_fuzzyFunction_1_0 = null;

        EObject lv_fuzzyFunction_2_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5785:2: ( ( (otherlv_0= 'val' ( (lv_fuzzyFunction_1_0= ruleFuzzyFunction ) ) ) | ( (lv_fuzzyFunction_2_0= ruleFuzzyFunctionInArrayNotation ) ) ) )
            // InternalAlpha.g:5786:2: ( (otherlv_0= 'val' ( (lv_fuzzyFunction_1_0= ruleFuzzyFunction ) ) ) | ( (lv_fuzzyFunction_2_0= ruleFuzzyFunctionInArrayNotation ) ) )
            {
            // InternalAlpha.g:5786:2: ( (otherlv_0= 'val' ( (lv_fuzzyFunction_1_0= ruleFuzzyFunction ) ) ) | ( (lv_fuzzyFunction_2_0= ruleFuzzyFunctionInArrayNotation ) ) )
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==64) ) {
                alt84=1;
            }
            else if ( (LA84_0==39) ) {
                alt84=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }
            switch (alt84) {
                case 1 :
                    // InternalAlpha.g:5787:3: (otherlv_0= 'val' ( (lv_fuzzyFunction_1_0= ruleFuzzyFunction ) ) )
                    {
                    // InternalAlpha.g:5787:3: (otherlv_0= 'val' ( (lv_fuzzyFunction_1_0= ruleFuzzyFunction ) ) )
                    // InternalAlpha.g:5788:4: otherlv_0= 'val' ( (lv_fuzzyFunction_1_0= ruleFuzzyFunction ) )
                    {
                    otherlv_0=(Token)match(input,64,FOLLOW_8); 

                    				newLeafNode(otherlv_0, grammarAccess.getFuzzyIndexExpressionAccess().getValKeyword_0_0());
                    			
                    // InternalAlpha.g:5792:4: ( (lv_fuzzyFunction_1_0= ruleFuzzyFunction ) )
                    // InternalAlpha.g:5793:5: (lv_fuzzyFunction_1_0= ruleFuzzyFunction )
                    {
                    // InternalAlpha.g:5793:5: (lv_fuzzyFunction_1_0= ruleFuzzyFunction )
                    // InternalAlpha.g:5794:6: lv_fuzzyFunction_1_0= ruleFuzzyFunction
                    {

                    						newCompositeNode(grammarAccess.getFuzzyIndexExpressionAccess().getFuzzyFunctionFuzzyFunctionParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_fuzzyFunction_1_0=ruleFuzzyFunction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getFuzzyIndexExpressionRule());
                    						}
                    						set(
                    							current,
                    							"fuzzyFunction",
                    							lv_fuzzyFunction_1_0,
                    							"alpha.model.Alpha.FuzzyFunction");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:5813:3: ( (lv_fuzzyFunction_2_0= ruleFuzzyFunctionInArrayNotation ) )
                    {
                    // InternalAlpha.g:5813:3: ( (lv_fuzzyFunction_2_0= ruleFuzzyFunctionInArrayNotation ) )
                    // InternalAlpha.g:5814:4: (lv_fuzzyFunction_2_0= ruleFuzzyFunctionInArrayNotation )
                    {
                    // InternalAlpha.g:5814:4: (lv_fuzzyFunction_2_0= ruleFuzzyFunctionInArrayNotation )
                    // InternalAlpha.g:5815:5: lv_fuzzyFunction_2_0= ruleFuzzyFunctionInArrayNotation
                    {

                    					newCompositeNode(grammarAccess.getFuzzyIndexExpressionAccess().getFuzzyFunctionFuzzyFunctionInArrayNotationParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_fuzzyFunction_2_0=ruleFuzzyFunctionInArrayNotation();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getFuzzyIndexExpressionRule());
                    					}
                    					set(
                    						current,
                    						"fuzzyFunction",
                    						lv_fuzzyFunction_2_0,
                    						"alpha.model.Alpha.FuzzyFunctionInArrayNotation");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFuzzyIndexExpression"


    // $ANTLR start "entryRuleReduceExpression"
    // InternalAlpha.g:5836:1: entryRuleReduceExpression returns [EObject current=null] : iv_ruleReduceExpression= ruleReduceExpression EOF ;
    public final EObject entryRuleReduceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleReduceExpression = null;


        try {
            // InternalAlpha.g:5836:57: (iv_ruleReduceExpression= ruleReduceExpression EOF )
            // InternalAlpha.g:5837:2: iv_ruleReduceExpression= ruleReduceExpression EOF
            {
             newCompositeNode(grammarAccess.getReduceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleReduceExpression=ruleReduceExpression();

            state._fsp--;

             current =iv_ruleReduceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleReduceExpression"


    // $ANTLR start "ruleReduceExpression"
    // InternalAlpha.g:5843:1: ruleReduceExpression returns [EObject current=null] : (otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) ;
    public final EObject ruleReduceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_projectionExpr_4_1 = null;

        EObject lv_projectionExpr_4_2 = null;

        EObject lv_body_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5849:2: ( (otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) )
            // InternalAlpha.g:5850:2: (otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            {
            // InternalAlpha.g:5850:2: (otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            // InternalAlpha.g:5851:3: otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,65,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getReduceExpressionAccess().getReduceKeyword_0());
            		
            otherlv_1=(Token)match(input,15,FOLLOW_72); 

            			newLeafNode(otherlv_1, grammarAccess.getReduceExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:5859:3: ( (lv_operator_2_0= ruleAREDUCTION_OP ) )
            // InternalAlpha.g:5860:4: (lv_operator_2_0= ruleAREDUCTION_OP )
            {
            // InternalAlpha.g:5860:4: (lv_operator_2_0= ruleAREDUCTION_OP )
            // InternalAlpha.g:5861:5: lv_operator_2_0= ruleAREDUCTION_OP
            {

            					newCompositeNode(grammarAccess.getReduceExpressionAccess().getOperatorAREDUCTION_OPParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_17);
            lv_operator_2_0=ruleAREDUCTION_OP();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReduceExpressionRule());
            					}
            					set(
            						current,
            						"operator",
            						lv_operator_2_0,
            						"alpha.model.Alpha.AREDUCTION_OP");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_73); 

            			newLeafNode(otherlv_3, grammarAccess.getReduceExpressionAccess().getCommaKeyword_3());
            		
            // InternalAlpha.g:5882:3: ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) )
            // InternalAlpha.g:5883:4: ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) )
            {
            // InternalAlpha.g:5883:4: ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) )
            // InternalAlpha.g:5884:5: (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation )
            {
            // InternalAlpha.g:5884:5: (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation )
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==15) ) {
                alt85=1;
            }
            else if ( (LA85_0==37) ) {
                alt85=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }
            switch (alt85) {
                case 1 :
                    // InternalAlpha.g:5885:6: lv_projectionExpr_4_1= ruleJNIFunction
                    {

                    						newCompositeNode(grammarAccess.getReduceExpressionAccess().getProjectionExprJNIFunctionParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_projectionExpr_4_1=ruleJNIFunction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getReduceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"projectionExpr",
                    							lv_projectionExpr_4_1,
                    							"alpha.model.Alpha.JNIFunction");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:5901:6: lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation
                    {

                    						newCompositeNode(grammarAccess.getReduceExpressionAccess().getProjectionExprJNIFunctionInArrayNotationParserRuleCall_4_0_1());
                    					
                    pushFollow(FOLLOW_17);
                    lv_projectionExpr_4_2=ruleJNIFunctionInArrayNotation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getReduceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"projectionExpr",
                    							lv_projectionExpr_4_2,
                    							"alpha.model.Alpha.JNIFunctionInArrayNotation");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;

            }


            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getReduceExpressionAccess().getCommaKeyword_5());
            		
            // InternalAlpha.g:5923:3: ( (lv_body_6_0= ruleAlphaExpression ) )
            // InternalAlpha.g:5924:4: (lv_body_6_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:5924:4: (lv_body_6_0= ruleAlphaExpression )
            // InternalAlpha.g:5925:5: lv_body_6_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getReduceExpressionAccess().getBodyAlphaExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_body_6_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getReduceExpressionRule());
            					}
            					set(
            						current,
            						"body",
            						lv_body_6_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getReduceExpressionAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReduceExpression"


    // $ANTLR start "entryRuleExternalReduceExpression"
    // InternalAlpha.g:5950:1: entryRuleExternalReduceExpression returns [EObject current=null] : iv_ruleExternalReduceExpression= ruleExternalReduceExpression EOF ;
    public final EObject entryRuleExternalReduceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalReduceExpression = null;


        try {
            // InternalAlpha.g:5950:65: (iv_ruleExternalReduceExpression= ruleExternalReduceExpression EOF )
            // InternalAlpha.g:5951:2: iv_ruleExternalReduceExpression= ruleExternalReduceExpression EOF
            {
             newCompositeNode(grammarAccess.getExternalReduceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternalReduceExpression=ruleExternalReduceExpression();

            state._fsp--;

             current =iv_ruleExternalReduceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternalReduceExpression"


    // $ANTLR start "ruleExternalReduceExpression"
    // InternalAlpha.g:5957:1: ruleExternalReduceExpression returns [EObject current=null] : (otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) ;
    public final EObject ruleExternalReduceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_projectionExpr_4_1 = null;

        EObject lv_projectionExpr_4_2 = null;

        EObject lv_body_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:5963:2: ( (otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) )
            // InternalAlpha.g:5964:2: (otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            {
            // InternalAlpha.g:5964:2: (otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            // InternalAlpha.g:5965:3: otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,65,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalReduceExpressionAccess().getReduceKeyword_0());
            		
            otherlv_1=(Token)match(input,15,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getExternalReduceExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:5973:3: ( ( ruleQualifiedName ) )
            // InternalAlpha.g:5974:4: ( ruleQualifiedName )
            {
            // InternalAlpha.g:5974:4: ( ruleQualifiedName )
            // InternalAlpha.g:5975:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalReduceExpressionRule());
            					}
            				

            					newCompositeNode(grammarAccess.getExternalReduceExpressionAccess().getExternalFunctionExternalFunctionCrossReference_2_0());
            				
            pushFollow(FOLLOW_17);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_73); 

            			newLeafNode(otherlv_3, grammarAccess.getExternalReduceExpressionAccess().getCommaKeyword_3());
            		
            // InternalAlpha.g:5993:3: ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) )
            // InternalAlpha.g:5994:4: ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) )
            {
            // InternalAlpha.g:5994:4: ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) )
            // InternalAlpha.g:5995:5: (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation )
            {
            // InternalAlpha.g:5995:5: (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation )
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==15) ) {
                alt86=1;
            }
            else if ( (LA86_0==37) ) {
                alt86=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }
            switch (alt86) {
                case 1 :
                    // InternalAlpha.g:5996:6: lv_projectionExpr_4_1= ruleJNIFunction
                    {

                    						newCompositeNode(grammarAccess.getExternalReduceExpressionAccess().getProjectionExprJNIFunctionParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_projectionExpr_4_1=ruleJNIFunction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getExternalReduceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"projectionExpr",
                    							lv_projectionExpr_4_1,
                    							"alpha.model.Alpha.JNIFunction");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:6012:6: lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation
                    {

                    						newCompositeNode(grammarAccess.getExternalReduceExpressionAccess().getProjectionExprJNIFunctionInArrayNotationParserRuleCall_4_0_1());
                    					
                    pushFollow(FOLLOW_17);
                    lv_projectionExpr_4_2=ruleJNIFunctionInArrayNotation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getExternalReduceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"projectionExpr",
                    							lv_projectionExpr_4_2,
                    							"alpha.model.Alpha.JNIFunctionInArrayNotation");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;

            }


            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalReduceExpressionAccess().getCommaKeyword_5());
            		
            // InternalAlpha.g:6034:3: ( (lv_body_6_0= ruleAlphaExpression ) )
            // InternalAlpha.g:6035:4: (lv_body_6_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:6035:4: (lv_body_6_0= ruleAlphaExpression )
            // InternalAlpha.g:6036:5: lv_body_6_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getExternalReduceExpressionAccess().getBodyAlphaExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_body_6_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExternalReduceExpressionRule());
            					}
            					set(
            						current,
            						"body",
            						lv_body_6_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getExternalReduceExpressionAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternalReduceExpression"


    // $ANTLR start "entryRuleArgReduceExpression"
    // InternalAlpha.g:6061:1: entryRuleArgReduceExpression returns [EObject current=null] : iv_ruleArgReduceExpression= ruleArgReduceExpression EOF ;
    public final EObject entryRuleArgReduceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgReduceExpression = null;


        try {
            // InternalAlpha.g:6061:60: (iv_ruleArgReduceExpression= ruleArgReduceExpression EOF )
            // InternalAlpha.g:6062:2: iv_ruleArgReduceExpression= ruleArgReduceExpression EOF
            {
             newCompositeNode(grammarAccess.getArgReduceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleArgReduceExpression=ruleArgReduceExpression();

            state._fsp--;

             current =iv_ruleArgReduceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArgReduceExpression"


    // $ANTLR start "ruleArgReduceExpression"
    // InternalAlpha.g:6068:1: ruleArgReduceExpression returns [EObject current=null] : (otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) ;
    public final EObject ruleArgReduceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_projectionExpr_4_1 = null;

        EObject lv_projectionExpr_4_2 = null;

        EObject lv_body_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6074:2: ( (otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) )
            // InternalAlpha.g:6075:2: (otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            {
            // InternalAlpha.g:6075:2: (otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            // InternalAlpha.g:6076:3: otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,66,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getArgReduceExpressionAccess().getArgreduceKeyword_0());
            		
            otherlv_1=(Token)match(input,15,FOLLOW_72); 

            			newLeafNode(otherlv_1, grammarAccess.getArgReduceExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:6084:3: ( (lv_operator_2_0= ruleAREDUCTION_OP ) )
            // InternalAlpha.g:6085:4: (lv_operator_2_0= ruleAREDUCTION_OP )
            {
            // InternalAlpha.g:6085:4: (lv_operator_2_0= ruleAREDUCTION_OP )
            // InternalAlpha.g:6086:5: lv_operator_2_0= ruleAREDUCTION_OP
            {

            					newCompositeNode(grammarAccess.getArgReduceExpressionAccess().getOperatorAREDUCTION_OPParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_17);
            lv_operator_2_0=ruleAREDUCTION_OP();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getArgReduceExpressionRule());
            					}
            					set(
            						current,
            						"operator",
            						lv_operator_2_0,
            						"alpha.model.Alpha.AREDUCTION_OP");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_73); 

            			newLeafNode(otherlv_3, grammarAccess.getArgReduceExpressionAccess().getCommaKeyword_3());
            		
            // InternalAlpha.g:6107:3: ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) )
            // InternalAlpha.g:6108:4: ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) )
            {
            // InternalAlpha.g:6108:4: ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) )
            // InternalAlpha.g:6109:5: (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation )
            {
            // InternalAlpha.g:6109:5: (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation )
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==15) ) {
                alt87=1;
            }
            else if ( (LA87_0==37) ) {
                alt87=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;
            }
            switch (alt87) {
                case 1 :
                    // InternalAlpha.g:6110:6: lv_projectionExpr_4_1= ruleJNIFunction
                    {

                    						newCompositeNode(grammarAccess.getArgReduceExpressionAccess().getProjectionExprJNIFunctionParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_projectionExpr_4_1=ruleJNIFunction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getArgReduceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"projectionExpr",
                    							lv_projectionExpr_4_1,
                    							"alpha.model.Alpha.JNIFunction");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:6126:6: lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation
                    {

                    						newCompositeNode(grammarAccess.getArgReduceExpressionAccess().getProjectionExprJNIFunctionInArrayNotationParserRuleCall_4_0_1());
                    					
                    pushFollow(FOLLOW_17);
                    lv_projectionExpr_4_2=ruleJNIFunctionInArrayNotation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getArgReduceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"projectionExpr",
                    							lv_projectionExpr_4_2,
                    							"alpha.model.Alpha.JNIFunctionInArrayNotation");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;

            }


            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getArgReduceExpressionAccess().getCommaKeyword_5());
            		
            // InternalAlpha.g:6148:3: ( (lv_body_6_0= ruleAlphaExpression ) )
            // InternalAlpha.g:6149:4: (lv_body_6_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:6149:4: (lv_body_6_0= ruleAlphaExpression )
            // InternalAlpha.g:6150:5: lv_body_6_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getArgReduceExpressionAccess().getBodyAlphaExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_body_6_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getArgReduceExpressionRule());
            					}
            					set(
            						current,
            						"body",
            						lv_body_6_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getArgReduceExpressionAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArgReduceExpression"


    // $ANTLR start "entryRuleExternalArgReduceExpression"
    // InternalAlpha.g:6175:1: entryRuleExternalArgReduceExpression returns [EObject current=null] : iv_ruleExternalArgReduceExpression= ruleExternalArgReduceExpression EOF ;
    public final EObject entryRuleExternalArgReduceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalArgReduceExpression = null;


        try {
            // InternalAlpha.g:6175:68: (iv_ruleExternalArgReduceExpression= ruleExternalArgReduceExpression EOF )
            // InternalAlpha.g:6176:2: iv_ruleExternalArgReduceExpression= ruleExternalArgReduceExpression EOF
            {
             newCompositeNode(grammarAccess.getExternalArgReduceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternalArgReduceExpression=ruleExternalArgReduceExpression();

            state._fsp--;

             current =iv_ruleExternalArgReduceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternalArgReduceExpression"


    // $ANTLR start "ruleExternalArgReduceExpression"
    // InternalAlpha.g:6182:1: ruleExternalArgReduceExpression returns [EObject current=null] : (otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) ;
    public final EObject ruleExternalArgReduceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_projectionExpr_4_1 = null;

        EObject lv_projectionExpr_4_2 = null;

        EObject lv_body_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6188:2: ( (otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) )
            // InternalAlpha.g:6189:2: (otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            {
            // InternalAlpha.g:6189:2: (otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            // InternalAlpha.g:6190:3: otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,66,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalArgReduceExpressionAccess().getArgreduceKeyword_0());
            		
            otherlv_1=(Token)match(input,15,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getExternalArgReduceExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:6198:3: ( ( ruleQualifiedName ) )
            // InternalAlpha.g:6199:4: ( ruleQualifiedName )
            {
            // InternalAlpha.g:6199:4: ( ruleQualifiedName )
            // InternalAlpha.g:6200:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalArgReduceExpressionRule());
            					}
            				

            					newCompositeNode(grammarAccess.getExternalArgReduceExpressionAccess().getExternalFunctionExternalFunctionCrossReference_2_0());
            				
            pushFollow(FOLLOW_17);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_73); 

            			newLeafNode(otherlv_3, grammarAccess.getExternalArgReduceExpressionAccess().getCommaKeyword_3());
            		
            // InternalAlpha.g:6218:3: ( ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) ) )
            // InternalAlpha.g:6219:4: ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) )
            {
            // InternalAlpha.g:6219:4: ( (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation ) )
            // InternalAlpha.g:6220:5: (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation )
            {
            // InternalAlpha.g:6220:5: (lv_projectionExpr_4_1= ruleJNIFunction | lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==15) ) {
                alt88=1;
            }
            else if ( (LA88_0==37) ) {
                alt88=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // InternalAlpha.g:6221:6: lv_projectionExpr_4_1= ruleJNIFunction
                    {

                    						newCompositeNode(grammarAccess.getExternalArgReduceExpressionAccess().getProjectionExprJNIFunctionParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_projectionExpr_4_1=ruleJNIFunction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getExternalArgReduceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"projectionExpr",
                    							lv_projectionExpr_4_1,
                    							"alpha.model.Alpha.JNIFunction");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:6237:6: lv_projectionExpr_4_2= ruleJNIFunctionInArrayNotation
                    {

                    						newCompositeNode(grammarAccess.getExternalArgReduceExpressionAccess().getProjectionExprJNIFunctionInArrayNotationParserRuleCall_4_0_1());
                    					
                    pushFollow(FOLLOW_17);
                    lv_projectionExpr_4_2=ruleJNIFunctionInArrayNotation();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getExternalArgReduceExpressionRule());
                    						}
                    						set(
                    							current,
                    							"projectionExpr",
                    							lv_projectionExpr_4_2,
                    							"alpha.model.Alpha.JNIFunctionInArrayNotation");
                    						afterParserOrEnumRuleCall();
                    					

                    }
                    break;

            }


            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalArgReduceExpressionAccess().getCommaKeyword_5());
            		
            // InternalAlpha.g:6259:3: ( (lv_body_6_0= ruleAlphaExpression ) )
            // InternalAlpha.g:6260:4: (lv_body_6_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:6260:4: (lv_body_6_0= ruleAlphaExpression )
            // InternalAlpha.g:6261:5: lv_body_6_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getExternalArgReduceExpressionAccess().getBodyAlphaExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_body_6_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExternalArgReduceExpressionRule());
            					}
            					set(
            						current,
            						"body",
            						lv_body_6_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getExternalArgReduceExpressionAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternalArgReduceExpression"


    // $ANTLR start "entryRuleFuzzyReduceExpression"
    // InternalAlpha.g:6286:1: entryRuleFuzzyReduceExpression returns [EObject current=null] : iv_ruleFuzzyReduceExpression= ruleFuzzyReduceExpression EOF ;
    public final EObject entryRuleFuzzyReduceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFuzzyReduceExpression = null;


        try {
            // InternalAlpha.g:6286:62: (iv_ruleFuzzyReduceExpression= ruleFuzzyReduceExpression EOF )
            // InternalAlpha.g:6287:2: iv_ruleFuzzyReduceExpression= ruleFuzzyReduceExpression EOF
            {
             newCompositeNode(grammarAccess.getFuzzyReduceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFuzzyReduceExpression=ruleFuzzyReduceExpression();

            state._fsp--;

             current =iv_ruleFuzzyReduceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFuzzyReduceExpression"


    // $ANTLR start "ruleFuzzyReduceExpression"
    // InternalAlpha.g:6293:1: ruleFuzzyReduceExpression returns [EObject current=null] : (otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) ;
    public final EObject ruleFuzzyReduceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_projectionFunction_4_0 = null;

        EObject lv_body_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6299:2: ( (otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) )
            // InternalAlpha.g:6300:2: (otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            {
            // InternalAlpha.g:6300:2: (otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            // InternalAlpha.g:6301:3: otherlv_0= 'reduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,65,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getFuzzyReduceExpressionAccess().getReduceKeyword_0());
            		
            otherlv_1=(Token)match(input,15,FOLLOW_72); 

            			newLeafNode(otherlv_1, grammarAccess.getFuzzyReduceExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:6309:3: ( (lv_operator_2_0= ruleAREDUCTION_OP ) )
            // InternalAlpha.g:6310:4: (lv_operator_2_0= ruleAREDUCTION_OP )
            {
            // InternalAlpha.g:6310:4: (lv_operator_2_0= ruleAREDUCTION_OP )
            // InternalAlpha.g:6311:5: lv_operator_2_0= ruleAREDUCTION_OP
            {

            					newCompositeNode(grammarAccess.getFuzzyReduceExpressionAccess().getOperatorAREDUCTION_OPParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_17);
            lv_operator_2_0=ruleAREDUCTION_OP();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFuzzyReduceExpressionRule());
            					}
            					set(
            						current,
            						"operator",
            						lv_operator_2_0,
            						"alpha.model.Alpha.AREDUCTION_OP");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_8); 

            			newLeafNode(otherlv_3, grammarAccess.getFuzzyReduceExpressionAccess().getCommaKeyword_3());
            		
            // InternalAlpha.g:6332:3: ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) )
            // InternalAlpha.g:6333:4: (lv_projectionFunction_4_0= ruleFuzzyFunction )
            {
            // InternalAlpha.g:6333:4: (lv_projectionFunction_4_0= ruleFuzzyFunction )
            // InternalAlpha.g:6334:5: lv_projectionFunction_4_0= ruleFuzzyFunction
            {

            					newCompositeNode(grammarAccess.getFuzzyReduceExpressionAccess().getProjectionFunctionFuzzyFunctionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_17);
            lv_projectionFunction_4_0=ruleFuzzyFunction();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFuzzyReduceExpressionRule());
            					}
            					set(
            						current,
            						"projectionFunction",
            						lv_projectionFunction_4_0,
            						"alpha.model.Alpha.FuzzyFunction");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getFuzzyReduceExpressionAccess().getCommaKeyword_5());
            		
            // InternalAlpha.g:6355:3: ( (lv_body_6_0= ruleAlphaExpression ) )
            // InternalAlpha.g:6356:4: (lv_body_6_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:6356:4: (lv_body_6_0= ruleAlphaExpression )
            // InternalAlpha.g:6357:5: lv_body_6_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getFuzzyReduceExpressionAccess().getBodyAlphaExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_body_6_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFuzzyReduceExpressionRule());
            					}
            					set(
            						current,
            						"body",
            						lv_body_6_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getFuzzyReduceExpressionAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFuzzyReduceExpression"


    // $ANTLR start "entryRuleExternalFuzzyReduceExpression"
    // InternalAlpha.g:6382:1: entryRuleExternalFuzzyReduceExpression returns [EObject current=null] : iv_ruleExternalFuzzyReduceExpression= ruleExternalFuzzyReduceExpression EOF ;
    public final EObject entryRuleExternalFuzzyReduceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalFuzzyReduceExpression = null;


        try {
            // InternalAlpha.g:6382:70: (iv_ruleExternalFuzzyReduceExpression= ruleExternalFuzzyReduceExpression EOF )
            // InternalAlpha.g:6383:2: iv_ruleExternalFuzzyReduceExpression= ruleExternalFuzzyReduceExpression EOF
            {
             newCompositeNode(grammarAccess.getExternalFuzzyReduceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternalFuzzyReduceExpression=ruleExternalFuzzyReduceExpression();

            state._fsp--;

             current =iv_ruleExternalFuzzyReduceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternalFuzzyReduceExpression"


    // $ANTLR start "ruleExternalFuzzyReduceExpression"
    // InternalAlpha.g:6389:1: ruleExternalFuzzyReduceExpression returns [EObject current=null] : (otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) ;
    public final EObject ruleExternalFuzzyReduceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_projectionFunction_4_0 = null;

        EObject lv_body_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6395:2: ( (otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) )
            // InternalAlpha.g:6396:2: (otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            {
            // InternalAlpha.g:6396:2: (otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            // InternalAlpha.g:6397:3: otherlv_0= 'reduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,65,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalFuzzyReduceExpressionAccess().getReduceKeyword_0());
            		
            otherlv_1=(Token)match(input,15,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getExternalFuzzyReduceExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:6405:3: ( ( ruleQualifiedName ) )
            // InternalAlpha.g:6406:4: ( ruleQualifiedName )
            {
            // InternalAlpha.g:6406:4: ( ruleQualifiedName )
            // InternalAlpha.g:6407:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalFuzzyReduceExpressionRule());
            					}
            				

            					newCompositeNode(grammarAccess.getExternalFuzzyReduceExpressionAccess().getExternalFunctionExternalFunctionCrossReference_2_0());
            				
            pushFollow(FOLLOW_17);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_8); 

            			newLeafNode(otherlv_3, grammarAccess.getExternalFuzzyReduceExpressionAccess().getCommaKeyword_3());
            		
            // InternalAlpha.g:6425:3: ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) )
            // InternalAlpha.g:6426:4: (lv_projectionFunction_4_0= ruleFuzzyFunction )
            {
            // InternalAlpha.g:6426:4: (lv_projectionFunction_4_0= ruleFuzzyFunction )
            // InternalAlpha.g:6427:5: lv_projectionFunction_4_0= ruleFuzzyFunction
            {

            					newCompositeNode(grammarAccess.getExternalFuzzyReduceExpressionAccess().getProjectionFunctionFuzzyFunctionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_17);
            lv_projectionFunction_4_0=ruleFuzzyFunction();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExternalFuzzyReduceExpressionRule());
            					}
            					set(
            						current,
            						"projectionFunction",
            						lv_projectionFunction_4_0,
            						"alpha.model.Alpha.FuzzyFunction");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalFuzzyReduceExpressionAccess().getCommaKeyword_5());
            		
            // InternalAlpha.g:6448:3: ( (lv_body_6_0= ruleAlphaExpression ) )
            // InternalAlpha.g:6449:4: (lv_body_6_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:6449:4: (lv_body_6_0= ruleAlphaExpression )
            // InternalAlpha.g:6450:5: lv_body_6_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getExternalFuzzyReduceExpressionAccess().getBodyAlphaExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_body_6_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExternalFuzzyReduceExpressionRule());
            					}
            					set(
            						current,
            						"body",
            						lv_body_6_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getExternalFuzzyReduceExpressionAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternalFuzzyReduceExpression"


    // $ANTLR start "entryRuleFuzzyArgReduceExpression"
    // InternalAlpha.g:6475:1: entryRuleFuzzyArgReduceExpression returns [EObject current=null] : iv_ruleFuzzyArgReduceExpression= ruleFuzzyArgReduceExpression EOF ;
    public final EObject entryRuleFuzzyArgReduceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFuzzyArgReduceExpression = null;


        try {
            // InternalAlpha.g:6475:65: (iv_ruleFuzzyArgReduceExpression= ruleFuzzyArgReduceExpression EOF )
            // InternalAlpha.g:6476:2: iv_ruleFuzzyArgReduceExpression= ruleFuzzyArgReduceExpression EOF
            {
             newCompositeNode(grammarAccess.getFuzzyArgReduceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFuzzyArgReduceExpression=ruleFuzzyArgReduceExpression();

            state._fsp--;

             current =iv_ruleFuzzyArgReduceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFuzzyArgReduceExpression"


    // $ANTLR start "ruleFuzzyArgReduceExpression"
    // InternalAlpha.g:6482:1: ruleFuzzyArgReduceExpression returns [EObject current=null] : (otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) ;
    public final EObject ruleFuzzyArgReduceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_projectionFunction_4_0 = null;

        EObject lv_body_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6488:2: ( (otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) )
            // InternalAlpha.g:6489:2: (otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            {
            // InternalAlpha.g:6489:2: (otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            // InternalAlpha.g:6490:3: otherlv_0= 'argreduce' otherlv_1= '(' ( (lv_operator_2_0= ruleAREDUCTION_OP ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,66,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getFuzzyArgReduceExpressionAccess().getArgreduceKeyword_0());
            		
            otherlv_1=(Token)match(input,15,FOLLOW_72); 

            			newLeafNode(otherlv_1, grammarAccess.getFuzzyArgReduceExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:6498:3: ( (lv_operator_2_0= ruleAREDUCTION_OP ) )
            // InternalAlpha.g:6499:4: (lv_operator_2_0= ruleAREDUCTION_OP )
            {
            // InternalAlpha.g:6499:4: (lv_operator_2_0= ruleAREDUCTION_OP )
            // InternalAlpha.g:6500:5: lv_operator_2_0= ruleAREDUCTION_OP
            {

            					newCompositeNode(grammarAccess.getFuzzyArgReduceExpressionAccess().getOperatorAREDUCTION_OPParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_17);
            lv_operator_2_0=ruleAREDUCTION_OP();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFuzzyArgReduceExpressionRule());
            					}
            					set(
            						current,
            						"operator",
            						lv_operator_2_0,
            						"alpha.model.Alpha.AREDUCTION_OP");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_8); 

            			newLeafNode(otherlv_3, grammarAccess.getFuzzyArgReduceExpressionAccess().getCommaKeyword_3());
            		
            // InternalAlpha.g:6521:3: ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) )
            // InternalAlpha.g:6522:4: (lv_projectionFunction_4_0= ruleFuzzyFunction )
            {
            // InternalAlpha.g:6522:4: (lv_projectionFunction_4_0= ruleFuzzyFunction )
            // InternalAlpha.g:6523:5: lv_projectionFunction_4_0= ruleFuzzyFunction
            {

            					newCompositeNode(grammarAccess.getFuzzyArgReduceExpressionAccess().getProjectionFunctionFuzzyFunctionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_17);
            lv_projectionFunction_4_0=ruleFuzzyFunction();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFuzzyArgReduceExpressionRule());
            					}
            					set(
            						current,
            						"projectionFunction",
            						lv_projectionFunction_4_0,
            						"alpha.model.Alpha.FuzzyFunction");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getFuzzyArgReduceExpressionAccess().getCommaKeyword_5());
            		
            // InternalAlpha.g:6544:3: ( (lv_body_6_0= ruleAlphaExpression ) )
            // InternalAlpha.g:6545:4: (lv_body_6_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:6545:4: (lv_body_6_0= ruleAlphaExpression )
            // InternalAlpha.g:6546:5: lv_body_6_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getFuzzyArgReduceExpressionAccess().getBodyAlphaExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_body_6_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getFuzzyArgReduceExpressionRule());
            					}
            					set(
            						current,
            						"body",
            						lv_body_6_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getFuzzyArgReduceExpressionAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFuzzyArgReduceExpression"


    // $ANTLR start "entryRuleExternalFuzzyArgReduceExpression"
    // InternalAlpha.g:6571:1: entryRuleExternalFuzzyArgReduceExpression returns [EObject current=null] : iv_ruleExternalFuzzyArgReduceExpression= ruleExternalFuzzyArgReduceExpression EOF ;
    public final EObject entryRuleExternalFuzzyArgReduceExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalFuzzyArgReduceExpression = null;


        try {
            // InternalAlpha.g:6571:73: (iv_ruleExternalFuzzyArgReduceExpression= ruleExternalFuzzyArgReduceExpression EOF )
            // InternalAlpha.g:6572:2: iv_ruleExternalFuzzyArgReduceExpression= ruleExternalFuzzyArgReduceExpression EOF
            {
             newCompositeNode(grammarAccess.getExternalFuzzyArgReduceExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternalFuzzyArgReduceExpression=ruleExternalFuzzyArgReduceExpression();

            state._fsp--;

             current =iv_ruleExternalFuzzyArgReduceExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternalFuzzyArgReduceExpression"


    // $ANTLR start "ruleExternalFuzzyArgReduceExpression"
    // InternalAlpha.g:6578:1: ruleExternalFuzzyArgReduceExpression returns [EObject current=null] : (otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) ;
    public final EObject ruleExternalFuzzyArgReduceExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_projectionFunction_4_0 = null;

        EObject lv_body_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6584:2: ( (otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) )
            // InternalAlpha.g:6585:2: (otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            {
            // InternalAlpha.g:6585:2: (otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            // InternalAlpha.g:6586:3: otherlv_0= 'argreduce' otherlv_1= '(' ( ( ruleQualifiedName ) ) otherlv_3= ',' ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) ) otherlv_5= ',' ( (lv_body_6_0= ruleAlphaExpression ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,66,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getExternalFuzzyArgReduceExpressionAccess().getArgreduceKeyword_0());
            		
            otherlv_1=(Token)match(input,15,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getExternalFuzzyArgReduceExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:6594:3: ( ( ruleQualifiedName ) )
            // InternalAlpha.g:6595:4: ( ruleQualifiedName )
            {
            // InternalAlpha.g:6595:4: ( ruleQualifiedName )
            // InternalAlpha.g:6596:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalFuzzyArgReduceExpressionRule());
            					}
            				

            					newCompositeNode(grammarAccess.getExternalFuzzyArgReduceExpressionAccess().getExternalFunctionExternalFunctionCrossReference_2_0());
            				
            pushFollow(FOLLOW_17);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_8); 

            			newLeafNode(otherlv_3, grammarAccess.getExternalFuzzyArgReduceExpressionAccess().getCommaKeyword_3());
            		
            // InternalAlpha.g:6614:3: ( (lv_projectionFunction_4_0= ruleFuzzyFunction ) )
            // InternalAlpha.g:6615:4: (lv_projectionFunction_4_0= ruleFuzzyFunction )
            {
            // InternalAlpha.g:6615:4: (lv_projectionFunction_4_0= ruleFuzzyFunction )
            // InternalAlpha.g:6616:5: lv_projectionFunction_4_0= ruleFuzzyFunction
            {

            					newCompositeNode(grammarAccess.getExternalFuzzyArgReduceExpressionAccess().getProjectionFunctionFuzzyFunctionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_17);
            lv_projectionFunction_4_0=ruleFuzzyFunction();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExternalFuzzyArgReduceExpressionRule());
            					}
            					set(
            						current,
            						"projectionFunction",
            						lv_projectionFunction_4_0,
            						"alpha.model.Alpha.FuzzyFunction");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalFuzzyArgReduceExpressionAccess().getCommaKeyword_5());
            		
            // InternalAlpha.g:6637:3: ( (lv_body_6_0= ruleAlphaExpression ) )
            // InternalAlpha.g:6638:4: (lv_body_6_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:6638:4: (lv_body_6_0= ruleAlphaExpression )
            // InternalAlpha.g:6639:5: lv_body_6_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getExternalFuzzyArgReduceExpressionAccess().getBodyAlphaExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_body_6_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExternalFuzzyArgReduceExpressionRule());
            					}
            					set(
            						current,
            						"body",
            						lv_body_6_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getExternalFuzzyArgReduceExpressionAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternalFuzzyArgReduceExpression"


    // $ANTLR start "entryRuleConvolutionExpression"
    // InternalAlpha.g:6664:1: entryRuleConvolutionExpression returns [EObject current=null] : iv_ruleConvolutionExpression= ruleConvolutionExpression EOF ;
    public final EObject entryRuleConvolutionExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConvolutionExpression = null;


        try {
            // InternalAlpha.g:6664:62: (iv_ruleConvolutionExpression= ruleConvolutionExpression EOF )
            // InternalAlpha.g:6665:2: iv_ruleConvolutionExpression= ruleConvolutionExpression EOF
            {
             newCompositeNode(grammarAccess.getConvolutionExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConvolutionExpression=ruleConvolutionExpression();

            state._fsp--;

             current =iv_ruleConvolutionExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConvolutionExpression"


    // $ANTLR start "ruleConvolutionExpression"
    // InternalAlpha.g:6671:1: ruleConvolutionExpression returns [EObject current=null] : (otherlv_0= 'conv' otherlv_1= '(' ( (lv_kernelDomainExpr_2_0= ruleCalculatorExpression ) ) otherlv_3= ',' ( (lv_kernelExpression_4_0= ruleAlphaExpression ) ) otherlv_5= ',' ( (lv_dataExpression_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) ;
    public final EObject ruleConvolutionExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_kernelDomainExpr_2_0 = null;

        EObject lv_kernelExpression_4_0 = null;

        EObject lv_dataExpression_6_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6677:2: ( (otherlv_0= 'conv' otherlv_1= '(' ( (lv_kernelDomainExpr_2_0= ruleCalculatorExpression ) ) otherlv_3= ',' ( (lv_kernelExpression_4_0= ruleAlphaExpression ) ) otherlv_5= ',' ( (lv_dataExpression_6_0= ruleAlphaExpression ) ) otherlv_7= ')' ) )
            // InternalAlpha.g:6678:2: (otherlv_0= 'conv' otherlv_1= '(' ( (lv_kernelDomainExpr_2_0= ruleCalculatorExpression ) ) otherlv_3= ',' ( (lv_kernelExpression_4_0= ruleAlphaExpression ) ) otherlv_5= ',' ( (lv_dataExpression_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            {
            // InternalAlpha.g:6678:2: (otherlv_0= 'conv' otherlv_1= '(' ( (lv_kernelDomainExpr_2_0= ruleCalculatorExpression ) ) otherlv_3= ',' ( (lv_kernelExpression_4_0= ruleAlphaExpression ) ) otherlv_5= ',' ( (lv_dataExpression_6_0= ruleAlphaExpression ) ) otherlv_7= ')' )
            // InternalAlpha.g:6679:3: otherlv_0= 'conv' otherlv_1= '(' ( (lv_kernelDomainExpr_2_0= ruleCalculatorExpression ) ) otherlv_3= ',' ( (lv_kernelExpression_4_0= ruleAlphaExpression ) ) otherlv_5= ',' ( (lv_dataExpression_6_0= ruleAlphaExpression ) ) otherlv_7= ')'
            {
            otherlv_0=(Token)match(input,67,FOLLOW_8); 

            			newLeafNode(otherlv_0, grammarAccess.getConvolutionExpressionAccess().getConvKeyword_0());
            		
            otherlv_1=(Token)match(input,15,FOLLOW_20); 

            			newLeafNode(otherlv_1, grammarAccess.getConvolutionExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:6687:3: ( (lv_kernelDomainExpr_2_0= ruleCalculatorExpression ) )
            // InternalAlpha.g:6688:4: (lv_kernelDomainExpr_2_0= ruleCalculatorExpression )
            {
            // InternalAlpha.g:6688:4: (lv_kernelDomainExpr_2_0= ruleCalculatorExpression )
            // InternalAlpha.g:6689:5: lv_kernelDomainExpr_2_0= ruleCalculatorExpression
            {

            					newCompositeNode(grammarAccess.getConvolutionExpressionAccess().getKernelDomainExprCalculatorExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_17);
            lv_kernelDomainExpr_2_0=ruleCalculatorExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConvolutionExpressionRule());
            					}
            					set(
            						current,
            						"kernelDomainExpr",
            						lv_kernelDomainExpr_2_0,
            						"alpha.model.Alpha.CalculatorExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_3, grammarAccess.getConvolutionExpressionAccess().getCommaKeyword_3());
            		
            // InternalAlpha.g:6710:3: ( (lv_kernelExpression_4_0= ruleAlphaExpression ) )
            // InternalAlpha.g:6711:4: (lv_kernelExpression_4_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:6711:4: (lv_kernelExpression_4_0= ruleAlphaExpression )
            // InternalAlpha.g:6712:5: lv_kernelExpression_4_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getConvolutionExpressionAccess().getKernelExpressionAlphaExpressionParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_17);
            lv_kernelExpression_4_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConvolutionExpressionRule());
            					}
            					set(
            						current,
            						"kernelExpression",
            						lv_kernelExpression_4_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_22); 

            			newLeafNode(otherlv_5, grammarAccess.getConvolutionExpressionAccess().getCommaKeyword_5());
            		
            // InternalAlpha.g:6733:3: ( (lv_dataExpression_6_0= ruleAlphaExpression ) )
            // InternalAlpha.g:6734:4: (lv_dataExpression_6_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:6734:4: (lv_dataExpression_6_0= ruleAlphaExpression )
            // InternalAlpha.g:6735:5: lv_dataExpression_6_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getConvolutionExpressionAccess().getDataExpressionAlphaExpressionParserRuleCall_6_0());
            				
            pushFollow(FOLLOW_9);
            lv_dataExpression_6_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConvolutionExpressionRule());
            					}
            					set(
            						current,
            						"dataExpression",
            						lv_dataExpression_6_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_7=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getConvolutionExpressionAccess().getRightParenthesisKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConvolutionExpression"


    // $ANTLR start "entryRuleSelectExpression"
    // InternalAlpha.g:6760:1: entryRuleSelectExpression returns [EObject current=null] : iv_ruleSelectExpression= ruleSelectExpression EOF ;
    public final EObject entryRuleSelectExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSelectExpression = null;


        try {
            // InternalAlpha.g:6760:57: (iv_ruleSelectExpression= ruleSelectExpression EOF )
            // InternalAlpha.g:6761:2: iv_ruleSelectExpression= ruleSelectExpression EOF
            {
             newCompositeNode(grammarAccess.getSelectExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSelectExpression=ruleSelectExpression();

            state._fsp--;

             current =iv_ruleSelectExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSelectExpression"


    // $ANTLR start "ruleSelectExpression"
    // InternalAlpha.g:6767:1: ruleSelectExpression returns [EObject current=null] : (otherlv_0= 'select' ( (lv_relationExpr_1_0= ruleCalculatorExpression ) ) otherlv_2= 'from' ( (lv_expr_3_0= ruleAlphaTerminalExpression ) ) ) ;
    public final EObject ruleSelectExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject lv_relationExpr_1_0 = null;

        EObject lv_expr_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6773:2: ( (otherlv_0= 'select' ( (lv_relationExpr_1_0= ruleCalculatorExpression ) ) otherlv_2= 'from' ( (lv_expr_3_0= ruleAlphaTerminalExpression ) ) ) )
            // InternalAlpha.g:6774:2: (otherlv_0= 'select' ( (lv_relationExpr_1_0= ruleCalculatorExpression ) ) otherlv_2= 'from' ( (lv_expr_3_0= ruleAlphaTerminalExpression ) ) )
            {
            // InternalAlpha.g:6774:2: (otherlv_0= 'select' ( (lv_relationExpr_1_0= ruleCalculatorExpression ) ) otherlv_2= 'from' ( (lv_expr_3_0= ruleAlphaTerminalExpression ) ) )
            // InternalAlpha.g:6775:3: otherlv_0= 'select' ( (lv_relationExpr_1_0= ruleCalculatorExpression ) ) otherlv_2= 'from' ( (lv_expr_3_0= ruleAlphaTerminalExpression ) )
            {
            otherlv_0=(Token)match(input,68,FOLLOW_20); 

            			newLeafNode(otherlv_0, grammarAccess.getSelectExpressionAccess().getSelectKeyword_0());
            		
            // InternalAlpha.g:6779:3: ( (lv_relationExpr_1_0= ruleCalculatorExpression ) )
            // InternalAlpha.g:6780:4: (lv_relationExpr_1_0= ruleCalculatorExpression )
            {
            // InternalAlpha.g:6780:4: (lv_relationExpr_1_0= ruleCalculatorExpression )
            // InternalAlpha.g:6781:5: lv_relationExpr_1_0= ruleCalculatorExpression
            {

            					newCompositeNode(grammarAccess.getSelectExpressionAccess().getRelationExprCalculatorExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_74);
            lv_relationExpr_1_0=ruleCalculatorExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSelectExpressionRule());
            					}
            					set(
            						current,
            						"relationExpr",
            						lv_relationExpr_1_0,
            						"alpha.model.Alpha.CalculatorExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,69,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getSelectExpressionAccess().getFromKeyword_2());
            		
            // InternalAlpha.g:6802:3: ( (lv_expr_3_0= ruleAlphaTerminalExpression ) )
            // InternalAlpha.g:6803:4: (lv_expr_3_0= ruleAlphaTerminalExpression )
            {
            // InternalAlpha.g:6803:4: (lv_expr_3_0= ruleAlphaTerminalExpression )
            // InternalAlpha.g:6804:5: lv_expr_3_0= ruleAlphaTerminalExpression
            {

            					newCompositeNode(grammarAccess.getSelectExpressionAccess().getExprAlphaTerminalExpressionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_expr_3_0=ruleAlphaTerminalExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSelectExpressionRule());
            					}
            					set(
            						current,
            						"expr",
            						lv_expr_3_0,
            						"alpha.model.Alpha.AlphaTerminalExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSelectExpression"


    // $ANTLR start "entryRuleOrExpression"
    // InternalAlpha.g:6825:1: entryRuleOrExpression returns [EObject current=null] : iv_ruleOrExpression= ruleOrExpression EOF ;
    public final EObject entryRuleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOrExpression = null;


        try {
            // InternalAlpha.g:6825:53: (iv_ruleOrExpression= ruleOrExpression EOF )
            // InternalAlpha.g:6826:2: iv_ruleOrExpression= ruleOrExpression EOF
            {
             newCompositeNode(grammarAccess.getOrExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOrExpression=ruleOrExpression();

            state._fsp--;

             current =iv_ruleOrExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOrExpression"


    // $ANTLR start "ruleOrExpression"
    // InternalAlpha.g:6832:1: ruleOrExpression returns [EObject current=null] : (this_AndExpression_0= ruleAndExpression ( () ( (lv_operator_2_0= ruleAOrOP ) ) ( (lv_right_3_0= ruleAndExpression ) ) )* ) ;
    public final EObject ruleOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_AndExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6838:2: ( (this_AndExpression_0= ruleAndExpression ( () ( (lv_operator_2_0= ruleAOrOP ) ) ( (lv_right_3_0= ruleAndExpression ) ) )* ) )
            // InternalAlpha.g:6839:2: (this_AndExpression_0= ruleAndExpression ( () ( (lv_operator_2_0= ruleAOrOP ) ) ( (lv_right_3_0= ruleAndExpression ) ) )* )
            {
            // InternalAlpha.g:6839:2: (this_AndExpression_0= ruleAndExpression ( () ( (lv_operator_2_0= ruleAOrOP ) ) ( (lv_right_3_0= ruleAndExpression ) ) )* )
            // InternalAlpha.g:6840:3: this_AndExpression_0= ruleAndExpression ( () ( (lv_operator_2_0= ruleAOrOP ) ) ( (lv_right_3_0= ruleAndExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getOrExpressionAccess().getAndExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_75);
            this_AndExpression_0=ruleAndExpression();

            state._fsp--;


            			current = this_AndExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:6848:3: ( () ( (lv_operator_2_0= ruleAOrOP ) ) ( (lv_right_3_0= ruleAndExpression ) ) )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==50||LA89_0==75) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // InternalAlpha.g:6849:4: () ( (lv_operator_2_0= ruleAOrOP ) ) ( (lv_right_3_0= ruleAndExpression ) )
            	    {
            	    // InternalAlpha.g:6849:4: ()
            	    // InternalAlpha.g:6850:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getOrExpressionAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:6856:4: ( (lv_operator_2_0= ruleAOrOP ) )
            	    // InternalAlpha.g:6857:5: (lv_operator_2_0= ruleAOrOP )
            	    {
            	    // InternalAlpha.g:6857:5: (lv_operator_2_0= ruleAOrOP )
            	    // InternalAlpha.g:6858:6: lv_operator_2_0= ruleAOrOP
            	    {

            	    						newCompositeNode(grammarAccess.getOrExpressionAccess().getOperatorAOrOPParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
            	    lv_operator_2_0=ruleAOrOP();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getOrExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.AOrOP");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:6875:4: ( (lv_right_3_0= ruleAndExpression ) )
            	    // InternalAlpha.g:6876:5: (lv_right_3_0= ruleAndExpression )
            	    {
            	    // InternalAlpha.g:6876:5: (lv_right_3_0= ruleAndExpression )
            	    // InternalAlpha.g:6877:6: lv_right_3_0= ruleAndExpression
            	    {

            	    						newCompositeNode(grammarAccess.getOrExpressionAccess().getRightAndExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_75);
            	    lv_right_3_0=ruleAndExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getOrExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.AndExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrExpression"


    // $ANTLR start "entryRuleAndExpression"
    // InternalAlpha.g:6899:1: entryRuleAndExpression returns [EObject current=null] : iv_ruleAndExpression= ruleAndExpression EOF ;
    public final EObject entryRuleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAndExpression = null;


        try {
            // InternalAlpha.g:6899:54: (iv_ruleAndExpression= ruleAndExpression EOF )
            // InternalAlpha.g:6900:2: iv_ruleAndExpression= ruleAndExpression EOF
            {
             newCompositeNode(grammarAccess.getAndExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAndExpression=ruleAndExpression();

            state._fsp--;

             current =iv_ruleAndExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAndExpression"


    // $ANTLR start "ruleAndExpression"
    // InternalAlpha.g:6906:1: ruleAndExpression returns [EObject current=null] : (this_RelationalExpression_0= ruleRelationalExpression ( () ( (lv_operator_2_0= ruleAAndOP ) ) ( (lv_right_3_0= ruleRelationalExpression ) ) )* ) ;
    public final EObject ruleAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_RelationalExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6912:2: ( (this_RelationalExpression_0= ruleRelationalExpression ( () ( (lv_operator_2_0= ruleAAndOP ) ) ( (lv_right_3_0= ruleRelationalExpression ) ) )* ) )
            // InternalAlpha.g:6913:2: (this_RelationalExpression_0= ruleRelationalExpression ( () ( (lv_operator_2_0= ruleAAndOP ) ) ( (lv_right_3_0= ruleRelationalExpression ) ) )* )
            {
            // InternalAlpha.g:6913:2: (this_RelationalExpression_0= ruleRelationalExpression ( () ( (lv_operator_2_0= ruleAAndOP ) ) ( (lv_right_3_0= ruleRelationalExpression ) ) )* )
            // InternalAlpha.g:6914:3: this_RelationalExpression_0= ruleRelationalExpression ( () ( (lv_operator_2_0= ruleAAndOP ) ) ( (lv_right_3_0= ruleRelationalExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getAndExpressionAccess().getRelationalExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_76);
            this_RelationalExpression_0=ruleRelationalExpression();

            state._fsp--;


            			current = this_RelationalExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:6922:3: ( () ( (lv_operator_2_0= ruleAAndOP ) ) ( (lv_right_3_0= ruleRelationalExpression ) ) )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==49) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // InternalAlpha.g:6923:4: () ( (lv_operator_2_0= ruleAAndOP ) ) ( (lv_right_3_0= ruleRelationalExpression ) )
            	    {
            	    // InternalAlpha.g:6923:4: ()
            	    // InternalAlpha.g:6924:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAndExpressionAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:6930:4: ( (lv_operator_2_0= ruleAAndOP ) )
            	    // InternalAlpha.g:6931:5: (lv_operator_2_0= ruleAAndOP )
            	    {
            	    // InternalAlpha.g:6931:5: (lv_operator_2_0= ruleAAndOP )
            	    // InternalAlpha.g:6932:6: lv_operator_2_0= ruleAAndOP
            	    {

            	    						newCompositeNode(grammarAccess.getAndExpressionAccess().getOperatorAAndOPParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
            	    lv_operator_2_0=ruleAAndOP();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAndExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.AAndOP");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:6949:4: ( (lv_right_3_0= ruleRelationalExpression ) )
            	    // InternalAlpha.g:6950:5: (lv_right_3_0= ruleRelationalExpression )
            	    {
            	    // InternalAlpha.g:6950:5: (lv_right_3_0= ruleRelationalExpression )
            	    // InternalAlpha.g:6951:6: lv_right_3_0= ruleRelationalExpression
            	    {

            	    						newCompositeNode(grammarAccess.getAndExpressionAccess().getRightRelationalExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_76);
            	    lv_right_3_0=ruleRelationalExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAndExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.RelationalExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAndExpression"


    // $ANTLR start "entryRuleRelationalExpression"
    // InternalAlpha.g:6973:1: entryRuleRelationalExpression returns [EObject current=null] : iv_ruleRelationalExpression= ruleRelationalExpression EOF ;
    public final EObject entryRuleRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelationalExpression = null;


        try {
            // InternalAlpha.g:6973:61: (iv_ruleRelationalExpression= ruleRelationalExpression EOF )
            // InternalAlpha.g:6974:2: iv_ruleRelationalExpression= ruleRelationalExpression EOF
            {
             newCompositeNode(grammarAccess.getRelationalExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRelationalExpression=ruleRelationalExpression();

            state._fsp--;

             current =iv_ruleRelationalExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRelationalExpression"


    // $ANTLR start "ruleRelationalExpression"
    // InternalAlpha.g:6980:1: ruleRelationalExpression returns [EObject current=null] : (this_AdditiveExpression_0= ruleAdditiveExpression ( () ( (lv_operator_2_0= ruleARelationalOP ) ) ( (lv_right_3_0= ruleAdditiveExpression ) ) )* ) ;
    public final EObject ruleRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject this_AdditiveExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:6986:2: ( (this_AdditiveExpression_0= ruleAdditiveExpression ( () ( (lv_operator_2_0= ruleARelationalOP ) ) ( (lv_right_3_0= ruleAdditiveExpression ) ) )* ) )
            // InternalAlpha.g:6987:2: (this_AdditiveExpression_0= ruleAdditiveExpression ( () ( (lv_operator_2_0= ruleARelationalOP ) ) ( (lv_right_3_0= ruleAdditiveExpression ) ) )* )
            {
            // InternalAlpha.g:6987:2: (this_AdditiveExpression_0= ruleAdditiveExpression ( () ( (lv_operator_2_0= ruleARelationalOP ) ) ( (lv_right_3_0= ruleAdditiveExpression ) ) )* )
            // InternalAlpha.g:6988:3: this_AdditiveExpression_0= ruleAdditiveExpression ( () ( (lv_operator_2_0= ruleARelationalOP ) ) ( (lv_right_3_0= ruleAdditiveExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getRelationalExpressionAccess().getAdditiveExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_77);
            this_AdditiveExpression_0=ruleAdditiveExpression();

            state._fsp--;


            			current = this_AdditiveExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:6996:3: ( () ( (lv_operator_2_0= ruleARelationalOP ) ) ( (lv_right_3_0= ruleAdditiveExpression ) ) )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==13||(LA91_0>=53 && LA91_0<=56)||LA91_0==76) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // InternalAlpha.g:6997:4: () ( (lv_operator_2_0= ruleARelationalOP ) ) ( (lv_right_3_0= ruleAdditiveExpression ) )
            	    {
            	    // InternalAlpha.g:6997:4: ()
            	    // InternalAlpha.g:6998:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getRelationalExpressionAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:7004:4: ( (lv_operator_2_0= ruleARelationalOP ) )
            	    // InternalAlpha.g:7005:5: (lv_operator_2_0= ruleARelationalOP )
            	    {
            	    // InternalAlpha.g:7005:5: (lv_operator_2_0= ruleARelationalOP )
            	    // InternalAlpha.g:7006:6: lv_operator_2_0= ruleARelationalOP
            	    {

            	    						newCompositeNode(grammarAccess.getRelationalExpressionAccess().getOperatorARelationalOPParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
            	    lv_operator_2_0=ruleARelationalOP();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.ARelationalOP");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:7023:4: ( (lv_right_3_0= ruleAdditiveExpression ) )
            	    // InternalAlpha.g:7024:5: (lv_right_3_0= ruleAdditiveExpression )
            	    {
            	    // InternalAlpha.g:7024:5: (lv_right_3_0= ruleAdditiveExpression )
            	    // InternalAlpha.g:7025:6: lv_right_3_0= ruleAdditiveExpression
            	    {

            	    						newCompositeNode(grammarAccess.getRelationalExpressionAccess().getRightAdditiveExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_77);
            	    lv_right_3_0=ruleAdditiveExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getRelationalExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.AdditiveExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop91;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRelationalExpression"


    // $ANTLR start "entryRuleAdditiveExpression"
    // InternalAlpha.g:7047:1: entryRuleAdditiveExpression returns [EObject current=null] : iv_ruleAdditiveExpression= ruleAdditiveExpression EOF ;
    public final EObject entryRuleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAdditiveExpression = null;


        try {
            // InternalAlpha.g:7047:59: (iv_ruleAdditiveExpression= ruleAdditiveExpression EOF )
            // InternalAlpha.g:7048:2: iv_ruleAdditiveExpression= ruleAdditiveExpression EOF
            {
             newCompositeNode(grammarAccess.getAdditiveExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAdditiveExpression=ruleAdditiveExpression();

            state._fsp--;

             current =iv_ruleAdditiveExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAdditiveExpression"


    // $ANTLR start "ruleAdditiveExpression"
    // InternalAlpha.g:7054:1: ruleAdditiveExpression returns [EObject current=null] : (this_MultiplicativeExpression_0= ruleMultiplicativeExpression ( () ( (lv_operator_2_0= ruleAAdditiveOP ) ) ( (lv_right_3_0= ruleMultiplicativeExpression ) ) )* ) ;
    public final EObject ruleAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject this_MultiplicativeExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7060:2: ( (this_MultiplicativeExpression_0= ruleMultiplicativeExpression ( () ( (lv_operator_2_0= ruleAAdditiveOP ) ) ( (lv_right_3_0= ruleMultiplicativeExpression ) ) )* ) )
            // InternalAlpha.g:7061:2: (this_MultiplicativeExpression_0= ruleMultiplicativeExpression ( () ( (lv_operator_2_0= ruleAAdditiveOP ) ) ( (lv_right_3_0= ruleMultiplicativeExpression ) ) )* )
            {
            // InternalAlpha.g:7061:2: (this_MultiplicativeExpression_0= ruleMultiplicativeExpression ( () ( (lv_operator_2_0= ruleAAdditiveOP ) ) ( (lv_right_3_0= ruleMultiplicativeExpression ) ) )* )
            // InternalAlpha.g:7062:3: this_MultiplicativeExpression_0= ruleMultiplicativeExpression ( () ( (lv_operator_2_0= ruleAAdditiveOP ) ) ( (lv_right_3_0= ruleMultiplicativeExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getMultiplicativeExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_54);
            this_MultiplicativeExpression_0=ruleMultiplicativeExpression();

            state._fsp--;


            			current = this_MultiplicativeExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:7070:3: ( () ( (lv_operator_2_0= ruleAAdditiveOP ) ) ( (lv_right_3_0= ruleMultiplicativeExpression ) ) )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( ((LA92_0>=43 && LA92_0<=44)) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // InternalAlpha.g:7071:4: () ( (lv_operator_2_0= ruleAAdditiveOP ) ) ( (lv_right_3_0= ruleMultiplicativeExpression ) )
            	    {
            	    // InternalAlpha.g:7071:4: ()
            	    // InternalAlpha.g:7072:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getAdditiveExpressionAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:7078:4: ( (lv_operator_2_0= ruleAAdditiveOP ) )
            	    // InternalAlpha.g:7079:5: (lv_operator_2_0= ruleAAdditiveOP )
            	    {
            	    // InternalAlpha.g:7079:5: (lv_operator_2_0= ruleAAdditiveOP )
            	    // InternalAlpha.g:7080:6: lv_operator_2_0= ruleAAdditiveOP
            	    {

            	    						newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getOperatorAAdditiveOPParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
            	    lv_operator_2_0=ruleAAdditiveOP();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAdditiveExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.AAdditiveOP");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:7097:4: ( (lv_right_3_0= ruleMultiplicativeExpression ) )
            	    // InternalAlpha.g:7098:5: (lv_right_3_0= ruleMultiplicativeExpression )
            	    {
            	    // InternalAlpha.g:7098:5: (lv_right_3_0= ruleMultiplicativeExpression )
            	    // InternalAlpha.g:7099:6: lv_right_3_0= ruleMultiplicativeExpression
            	    {

            	    						newCompositeNode(grammarAccess.getAdditiveExpressionAccess().getRightMultiplicativeExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_54);
            	    lv_right_3_0=ruleMultiplicativeExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getAdditiveExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.MultiplicativeExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop92;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAdditiveExpression"


    // $ANTLR start "entryRuleMultiplicativeExpression"
    // InternalAlpha.g:7121:1: entryRuleMultiplicativeExpression returns [EObject current=null] : iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF ;
    public final EObject entryRuleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplicativeExpression = null;


        try {
            // InternalAlpha.g:7121:65: (iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF )
            // InternalAlpha.g:7122:2: iv_ruleMultiplicativeExpression= ruleMultiplicativeExpression EOF
            {
             newCompositeNode(grammarAccess.getMultiplicativeExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMultiplicativeExpression=ruleMultiplicativeExpression();

            state._fsp--;

             current =iv_ruleMultiplicativeExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMultiplicativeExpression"


    // $ANTLR start "ruleMultiplicativeExpression"
    // InternalAlpha.g:7128:1: ruleMultiplicativeExpression returns [EObject current=null] : (this_MinMaxExpression_0= ruleMinMaxExpression ( () ( (lv_operator_2_0= ruleAMultiplicativeOP ) ) ( (lv_right_3_0= ruleMinMaxExpression ) ) )* ) ;
    public final EObject ruleMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_MinMaxExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7134:2: ( (this_MinMaxExpression_0= ruleMinMaxExpression ( () ( (lv_operator_2_0= ruleAMultiplicativeOP ) ) ( (lv_right_3_0= ruleMinMaxExpression ) ) )* ) )
            // InternalAlpha.g:7135:2: (this_MinMaxExpression_0= ruleMinMaxExpression ( () ( (lv_operator_2_0= ruleAMultiplicativeOP ) ) ( (lv_right_3_0= ruleMinMaxExpression ) ) )* )
            {
            // InternalAlpha.g:7135:2: (this_MinMaxExpression_0= ruleMinMaxExpression ( () ( (lv_operator_2_0= ruleAMultiplicativeOP ) ) ( (lv_right_3_0= ruleMinMaxExpression ) ) )* )
            // InternalAlpha.g:7136:3: this_MinMaxExpression_0= ruleMinMaxExpression ( () ( (lv_operator_2_0= ruleAMultiplicativeOP ) ) ( (lv_right_3_0= ruleMinMaxExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getMinMaxExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_78);
            this_MinMaxExpression_0=ruleMinMaxExpression();

            state._fsp--;


            			current = this_MinMaxExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:7144:3: ( () ( (lv_operator_2_0= ruleAMultiplicativeOP ) ) ( (lv_right_3_0= ruleMinMaxExpression ) ) )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( ((LA93_0>=45 && LA93_0<=46)) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // InternalAlpha.g:7145:4: () ( (lv_operator_2_0= ruleAMultiplicativeOP ) ) ( (lv_right_3_0= ruleMinMaxExpression ) )
            	    {
            	    // InternalAlpha.g:7145:4: ()
            	    // InternalAlpha.g:7146:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getMultiplicativeExpressionAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:7152:4: ( (lv_operator_2_0= ruleAMultiplicativeOP ) )
            	    // InternalAlpha.g:7153:5: (lv_operator_2_0= ruleAMultiplicativeOP )
            	    {
            	    // InternalAlpha.g:7153:5: (lv_operator_2_0= ruleAMultiplicativeOP )
            	    // InternalAlpha.g:7154:6: lv_operator_2_0= ruleAMultiplicativeOP
            	    {

            	    						newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getOperatorAMultiplicativeOPParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
            	    lv_operator_2_0=ruleAMultiplicativeOP();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMultiplicativeExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.AMultiplicativeOP");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:7171:4: ( (lv_right_3_0= ruleMinMaxExpression ) )
            	    // InternalAlpha.g:7172:5: (lv_right_3_0= ruleMinMaxExpression )
            	    {
            	    // InternalAlpha.g:7172:5: (lv_right_3_0= ruleMinMaxExpression )
            	    // InternalAlpha.g:7173:6: lv_right_3_0= ruleMinMaxExpression
            	    {

            	    						newCompositeNode(grammarAccess.getMultiplicativeExpressionAccess().getRightMinMaxExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_78);
            	    lv_right_3_0=ruleMinMaxExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMultiplicativeExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.MinMaxExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMultiplicativeExpression"


    // $ANTLR start "entryRuleMinMaxExpression"
    // InternalAlpha.g:7195:1: entryRuleMinMaxExpression returns [EObject current=null] : iv_ruleMinMaxExpression= ruleMinMaxExpression EOF ;
    public final EObject entryRuleMinMaxExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMinMaxExpression = null;


        try {
            // InternalAlpha.g:7195:57: (iv_ruleMinMaxExpression= ruleMinMaxExpression EOF )
            // InternalAlpha.g:7196:2: iv_ruleMinMaxExpression= ruleMinMaxExpression EOF
            {
             newCompositeNode(grammarAccess.getMinMaxExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMinMaxExpression=ruleMinMaxExpression();

            state._fsp--;

             current =iv_ruleMinMaxExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMinMaxExpression"


    // $ANTLR start "ruleMinMaxExpression"
    // InternalAlpha.g:7202:1: ruleMinMaxExpression returns [EObject current=null] : (this_UnaryOrTerminalExpression_0= ruleUnaryOrTerminalExpression ( () ( (lv_operator_2_0= ruleAMINMAX_OP ) ) ( (lv_right_3_0= ruleUnaryOrTerminalExpression ) ) )* ) ;
    public final EObject ruleMinMaxExpression() throws RecognitionException {
        EObject current = null;

        EObject this_UnaryOrTerminalExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7208:2: ( (this_UnaryOrTerminalExpression_0= ruleUnaryOrTerminalExpression ( () ( (lv_operator_2_0= ruleAMINMAX_OP ) ) ( (lv_right_3_0= ruleUnaryOrTerminalExpression ) ) )* ) )
            // InternalAlpha.g:7209:2: (this_UnaryOrTerminalExpression_0= ruleUnaryOrTerminalExpression ( () ( (lv_operator_2_0= ruleAMINMAX_OP ) ) ( (lv_right_3_0= ruleUnaryOrTerminalExpression ) ) )* )
            {
            // InternalAlpha.g:7209:2: (this_UnaryOrTerminalExpression_0= ruleUnaryOrTerminalExpression ( () ( (lv_operator_2_0= ruleAMINMAX_OP ) ) ( (lv_right_3_0= ruleUnaryOrTerminalExpression ) ) )* )
            // InternalAlpha.g:7210:3: this_UnaryOrTerminalExpression_0= ruleUnaryOrTerminalExpression ( () ( (lv_operator_2_0= ruleAMINMAX_OP ) ) ( (lv_right_3_0= ruleUnaryOrTerminalExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getMinMaxExpressionAccess().getUnaryOrTerminalExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_79);
            this_UnaryOrTerminalExpression_0=ruleUnaryOrTerminalExpression();

            state._fsp--;


            			current = this_UnaryOrTerminalExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:7218:3: ( () ( (lv_operator_2_0= ruleAMINMAX_OP ) ) ( (lv_right_3_0= ruleUnaryOrTerminalExpression ) ) )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( ((LA94_0>=70 && LA94_0<=71)) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // InternalAlpha.g:7219:4: () ( (lv_operator_2_0= ruleAMINMAX_OP ) ) ( (lv_right_3_0= ruleUnaryOrTerminalExpression ) )
            	    {
            	    // InternalAlpha.g:7219:4: ()
            	    // InternalAlpha.g:7220:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getMinMaxExpressionAccess().getBinaryExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:7226:4: ( (lv_operator_2_0= ruleAMINMAX_OP ) )
            	    // InternalAlpha.g:7227:5: (lv_operator_2_0= ruleAMINMAX_OP )
            	    {
            	    // InternalAlpha.g:7227:5: (lv_operator_2_0= ruleAMINMAX_OP )
            	    // InternalAlpha.g:7228:6: lv_operator_2_0= ruleAMINMAX_OP
            	    {

            	    						newCompositeNode(grammarAccess.getMinMaxExpressionAccess().getOperatorAMINMAX_OPParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
            	    lv_operator_2_0=ruleAMINMAX_OP();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMinMaxExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.AMINMAX_OP");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:7245:4: ( (lv_right_3_0= ruleUnaryOrTerminalExpression ) )
            	    // InternalAlpha.g:7246:5: (lv_right_3_0= ruleUnaryOrTerminalExpression )
            	    {
            	    // InternalAlpha.g:7246:5: (lv_right_3_0= ruleUnaryOrTerminalExpression )
            	    // InternalAlpha.g:7247:6: lv_right_3_0= ruleUnaryOrTerminalExpression
            	    {

            	    						newCompositeNode(grammarAccess.getMinMaxExpressionAccess().getRightUnaryOrTerminalExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_79);
            	    lv_right_3_0=ruleUnaryOrTerminalExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMinMaxExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.UnaryOrTerminalExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMinMaxExpression"


    // $ANTLR start "entryRuleMultiArgExpression"
    // InternalAlpha.g:7269:1: entryRuleMultiArgExpression returns [EObject current=null] : iv_ruleMultiArgExpression= ruleMultiArgExpression EOF ;
    public final EObject entryRuleMultiArgExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiArgExpression = null;


        try {
            // InternalAlpha.g:7269:59: (iv_ruleMultiArgExpression= ruleMultiArgExpression EOF )
            // InternalAlpha.g:7270:2: iv_ruleMultiArgExpression= ruleMultiArgExpression EOF
            {
             newCompositeNode(grammarAccess.getMultiArgExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMultiArgExpression=ruleMultiArgExpression();

            state._fsp--;

             current =iv_ruleMultiArgExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMultiArgExpression"


    // $ANTLR start "ruleMultiArgExpression"
    // InternalAlpha.g:7276:1: ruleMultiArgExpression returns [EObject current=null] : ( ( (lv_operator_0_0= ruleAREDUCTION_OP ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')' ) ;
    public final EObject ruleMultiArgExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_operator_0_0 = null;

        EObject lv_exprs_2_0 = null;

        EObject lv_exprs_4_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7282:2: ( ( ( (lv_operator_0_0= ruleAREDUCTION_OP ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')' ) )
            // InternalAlpha.g:7283:2: ( ( (lv_operator_0_0= ruleAREDUCTION_OP ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')' )
            {
            // InternalAlpha.g:7283:2: ( ( (lv_operator_0_0= ruleAREDUCTION_OP ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')' )
            // InternalAlpha.g:7284:3: ( (lv_operator_0_0= ruleAREDUCTION_OP ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')'
            {
            // InternalAlpha.g:7284:3: ( (lv_operator_0_0= ruleAREDUCTION_OP ) )
            // InternalAlpha.g:7285:4: (lv_operator_0_0= ruleAREDUCTION_OP )
            {
            // InternalAlpha.g:7285:4: (lv_operator_0_0= ruleAREDUCTION_OP )
            // InternalAlpha.g:7286:5: lv_operator_0_0= ruleAREDUCTION_OP
            {

            					newCompositeNode(grammarAccess.getMultiArgExpressionAccess().getOperatorAREDUCTION_OPParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_8);
            lv_operator_0_0=ruleAREDUCTION_OP();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMultiArgExpressionRule());
            					}
            					set(
            						current,
            						"operator",
            						lv_operator_0_0,
            						"alpha.model.Alpha.AREDUCTION_OP");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,15,FOLLOW_22); 

            			newLeafNode(otherlv_1, grammarAccess.getMultiArgExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:7307:3: ( (lv_exprs_2_0= ruleAlphaExpression ) )
            // InternalAlpha.g:7308:4: (lv_exprs_2_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:7308:4: (lv_exprs_2_0= ruleAlphaExpression )
            // InternalAlpha.g:7309:5: lv_exprs_2_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getMultiArgExpressionAccess().getExprsAlphaExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_59);
            lv_exprs_2_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMultiArgExpressionRule());
            					}
            					add(
            						current,
            						"exprs",
            						lv_exprs_2_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:7326:3: (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( (LA95_0==24) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // InternalAlpha.g:7327:4: otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,24,FOLLOW_22); 

            	    				newLeafNode(otherlv_3, grammarAccess.getMultiArgExpressionAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalAlpha.g:7331:4: ( (lv_exprs_4_0= ruleAlphaExpression ) )
            	    // InternalAlpha.g:7332:5: (lv_exprs_4_0= ruleAlphaExpression )
            	    {
            	    // InternalAlpha.g:7332:5: (lv_exprs_4_0= ruleAlphaExpression )
            	    // InternalAlpha.g:7333:6: lv_exprs_4_0= ruleAlphaExpression
            	    {

            	    						newCompositeNode(grammarAccess.getMultiArgExpressionAccess().getExprsAlphaExpressionParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_59);
            	    lv_exprs_4_0=ruleAlphaExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMultiArgExpressionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"exprs",
            	    							lv_exprs_4_0,
            	    							"alpha.model.Alpha.AlphaExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop95;
                }
            } while (true);

            otherlv_5=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getMultiArgExpressionAccess().getRightParenthesisKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMultiArgExpression"


    // $ANTLR start "entryRuleExternalMultiArgExpression"
    // InternalAlpha.g:7359:1: entryRuleExternalMultiArgExpression returns [EObject current=null] : iv_ruleExternalMultiArgExpression= ruleExternalMultiArgExpression EOF ;
    public final EObject entryRuleExternalMultiArgExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExternalMultiArgExpression = null;


        try {
            // InternalAlpha.g:7359:67: (iv_ruleExternalMultiArgExpression= ruleExternalMultiArgExpression EOF )
            // InternalAlpha.g:7360:2: iv_ruleExternalMultiArgExpression= ruleExternalMultiArgExpression EOF
            {
             newCompositeNode(grammarAccess.getExternalMultiArgExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExternalMultiArgExpression=ruleExternalMultiArgExpression();

            state._fsp--;

             current =iv_ruleExternalMultiArgExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExternalMultiArgExpression"


    // $ANTLR start "ruleExternalMultiArgExpression"
    // InternalAlpha.g:7366:1: ruleExternalMultiArgExpression returns [EObject current=null] : ( ( ( ruleQualifiedName ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')' ) ;
    public final EObject ruleExternalMultiArgExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_exprs_2_0 = null;

        EObject lv_exprs_4_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7372:2: ( ( ( ( ruleQualifiedName ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')' ) )
            // InternalAlpha.g:7373:2: ( ( ( ruleQualifiedName ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')' )
            {
            // InternalAlpha.g:7373:2: ( ( ( ruleQualifiedName ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')' )
            // InternalAlpha.g:7374:3: ( ( ruleQualifiedName ) ) otherlv_1= '(' ( (lv_exprs_2_0= ruleAlphaExpression ) ) (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )* otherlv_5= ')'
            {
            // InternalAlpha.g:7374:3: ( ( ruleQualifiedName ) )
            // InternalAlpha.g:7375:4: ( ruleQualifiedName )
            {
            // InternalAlpha.g:7375:4: ( ruleQualifiedName )
            // InternalAlpha.g:7376:5: ruleQualifiedName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExternalMultiArgExpressionRule());
            					}
            				

            					newCompositeNode(grammarAccess.getExternalMultiArgExpressionAccess().getExternalFunctionExternalFunctionCrossReference_0_0());
            				
            pushFollow(FOLLOW_8);
            ruleQualifiedName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,15,FOLLOW_22); 

            			newLeafNode(otherlv_1, grammarAccess.getExternalMultiArgExpressionAccess().getLeftParenthesisKeyword_1());
            		
            // InternalAlpha.g:7394:3: ( (lv_exprs_2_0= ruleAlphaExpression ) )
            // InternalAlpha.g:7395:4: (lv_exprs_2_0= ruleAlphaExpression )
            {
            // InternalAlpha.g:7395:4: (lv_exprs_2_0= ruleAlphaExpression )
            // InternalAlpha.g:7396:5: lv_exprs_2_0= ruleAlphaExpression
            {

            					newCompositeNode(grammarAccess.getExternalMultiArgExpressionAccess().getExprsAlphaExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_59);
            lv_exprs_2_0=ruleAlphaExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExternalMultiArgExpressionRule());
            					}
            					add(
            						current,
            						"exprs",
            						lv_exprs_2_0,
            						"alpha.model.Alpha.AlphaExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:7413:3: (otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) ) )*
            loop96:
            do {
                int alt96=2;
                int LA96_0 = input.LA(1);

                if ( (LA96_0==24) ) {
                    alt96=1;
                }


                switch (alt96) {
            	case 1 :
            	    // InternalAlpha.g:7414:4: otherlv_3= ',' ( (lv_exprs_4_0= ruleAlphaExpression ) )
            	    {
            	    otherlv_3=(Token)match(input,24,FOLLOW_22); 

            	    				newLeafNode(otherlv_3, grammarAccess.getExternalMultiArgExpressionAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalAlpha.g:7418:4: ( (lv_exprs_4_0= ruleAlphaExpression ) )
            	    // InternalAlpha.g:7419:5: (lv_exprs_4_0= ruleAlphaExpression )
            	    {
            	    // InternalAlpha.g:7419:5: (lv_exprs_4_0= ruleAlphaExpression )
            	    // InternalAlpha.g:7420:6: lv_exprs_4_0= ruleAlphaExpression
            	    {

            	    						newCompositeNode(grammarAccess.getExternalMultiArgExpressionAccess().getExprsAlphaExpressionParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_59);
            	    lv_exprs_4_0=ruleAlphaExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getExternalMultiArgExpressionRule());
            	    						}
            	    						add(
            	    							current,
            	    							"exprs",
            	    							lv_exprs_4_0,
            	    							"alpha.model.Alpha.AlphaExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop96;
                }
            } while (true);

            otherlv_5=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getExternalMultiArgExpressionAccess().getRightParenthesisKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExternalMultiArgExpression"


    // $ANTLR start "entryRuleUnaryOrTerminalExpression"
    // InternalAlpha.g:7446:1: entryRuleUnaryOrTerminalExpression returns [EObject current=null] : iv_ruleUnaryOrTerminalExpression= ruleUnaryOrTerminalExpression EOF ;
    public final EObject entryRuleUnaryOrTerminalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryOrTerminalExpression = null;


        try {
            // InternalAlpha.g:7446:66: (iv_ruleUnaryOrTerminalExpression= ruleUnaryOrTerminalExpression EOF )
            // InternalAlpha.g:7447:2: iv_ruleUnaryOrTerminalExpression= ruleUnaryOrTerminalExpression EOF
            {
             newCompositeNode(grammarAccess.getUnaryOrTerminalExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUnaryOrTerminalExpression=ruleUnaryOrTerminalExpression();

            state._fsp--;

             current =iv_ruleUnaryOrTerminalExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnaryOrTerminalExpression"


    // $ANTLR start "ruleUnaryOrTerminalExpression"
    // InternalAlpha.g:7453:1: ruleUnaryOrTerminalExpression returns [EObject current=null] : (this_UnaryExpression_0= ruleUnaryExpression | this_AlphaTerminalExpression_1= ruleAlphaTerminalExpression ) ;
    public final EObject ruleUnaryOrTerminalExpression() throws RecognitionException {
        EObject current = null;

        EObject this_UnaryExpression_0 = null;

        EObject this_AlphaTerminalExpression_1 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7459:2: ( (this_UnaryExpression_0= ruleUnaryExpression | this_AlphaTerminalExpression_1= ruleAlphaTerminalExpression ) )
            // InternalAlpha.g:7460:2: (this_UnaryExpression_0= ruleUnaryExpression | this_AlphaTerminalExpression_1= ruleAlphaTerminalExpression )
            {
            // InternalAlpha.g:7460:2: (this_UnaryExpression_0= ruleUnaryExpression | this_AlphaTerminalExpression_1= ruleAlphaTerminalExpression )
            int alt97=2;
            switch ( input.LA(1) ) {
            case 77:
                {
                alt97=1;
                }
                break;
            case 44:
                {
                int LA97_2 = input.LA(2);

                if ( (LA97_2==RULE_INT) ) {
                    alt97=2;
                }
                else if ( (LA97_2==RULE_ID||LA97_2==15||LA97_2==39||LA97_2==43||LA97_2==45||(LA97_2>=49 && LA97_2<=50)||LA97_2==62||(LA97_2>=64 && LA97_2<=68)||(LA97_2>=70 && LA97_2<=75)) ) {
                    alt97=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 97, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_ID:
            case RULE_INT:
            case RULE_BOOLEAN:
            case 15:
            case 39:
            case 43:
            case 45:
            case 49:
            case 50:
            case 62:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
                {
                alt97=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }

            switch (alt97) {
                case 1 :
                    // InternalAlpha.g:7461:3: this_UnaryExpression_0= ruleUnaryExpression
                    {

                    			newCompositeNode(grammarAccess.getUnaryOrTerminalExpressionAccess().getUnaryExpressionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_UnaryExpression_0=ruleUnaryExpression();

                    state._fsp--;


                    			current = this_UnaryExpression_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:7470:3: this_AlphaTerminalExpression_1= ruleAlphaTerminalExpression
                    {

                    			newCompositeNode(grammarAccess.getUnaryOrTerminalExpressionAccess().getAlphaTerminalExpressionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_AlphaTerminalExpression_1=ruleAlphaTerminalExpression();

                    state._fsp--;


                    			current = this_AlphaTerminalExpression_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnaryOrTerminalExpression"


    // $ANTLR start "entryRuleUnaryExpression"
    // InternalAlpha.g:7482:1: entryRuleUnaryExpression returns [EObject current=null] : iv_ruleUnaryExpression= ruleUnaryExpression EOF ;
    public final EObject entryRuleUnaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryExpression = null;


        try {
            // InternalAlpha.g:7482:56: (iv_ruleUnaryExpression= ruleUnaryExpression EOF )
            // InternalAlpha.g:7483:2: iv_ruleUnaryExpression= ruleUnaryExpression EOF
            {
             newCompositeNode(grammarAccess.getUnaryExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUnaryExpression=ruleUnaryExpression();

            state._fsp--;

             current =iv_ruleUnaryExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnaryExpression"


    // $ANTLR start "ruleUnaryExpression"
    // InternalAlpha.g:7489:1: ruleUnaryExpression returns [EObject current=null] : ( ( (lv_operator_0_0= ruleAUnaryOP ) ) ( (lv_expr_1_0= ruleAlphaUnaryTerminalExpression ) ) ) ;
    public final EObject ruleUnaryExpression() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_operator_0_0 = null;

        EObject lv_expr_1_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7495:2: ( ( ( (lv_operator_0_0= ruleAUnaryOP ) ) ( (lv_expr_1_0= ruleAlphaUnaryTerminalExpression ) ) ) )
            // InternalAlpha.g:7496:2: ( ( (lv_operator_0_0= ruleAUnaryOP ) ) ( (lv_expr_1_0= ruleAlphaUnaryTerminalExpression ) ) )
            {
            // InternalAlpha.g:7496:2: ( ( (lv_operator_0_0= ruleAUnaryOP ) ) ( (lv_expr_1_0= ruleAlphaUnaryTerminalExpression ) ) )
            // InternalAlpha.g:7497:3: ( (lv_operator_0_0= ruleAUnaryOP ) ) ( (lv_expr_1_0= ruleAlphaUnaryTerminalExpression ) )
            {
            // InternalAlpha.g:7497:3: ( (lv_operator_0_0= ruleAUnaryOP ) )
            // InternalAlpha.g:7498:4: (lv_operator_0_0= ruleAUnaryOP )
            {
            // InternalAlpha.g:7498:4: (lv_operator_0_0= ruleAUnaryOP )
            // InternalAlpha.g:7499:5: lv_operator_0_0= ruleAUnaryOP
            {

            					newCompositeNode(grammarAccess.getUnaryExpressionAccess().getOperatorAUnaryOPParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_22);
            lv_operator_0_0=ruleAUnaryOP();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUnaryExpressionRule());
            					}
            					set(
            						current,
            						"operator",
            						lv_operator_0_0,
            						"alpha.model.Alpha.AUnaryOP");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:7516:3: ( (lv_expr_1_0= ruleAlphaUnaryTerminalExpression ) )
            // InternalAlpha.g:7517:4: (lv_expr_1_0= ruleAlphaUnaryTerminalExpression )
            {
            // InternalAlpha.g:7517:4: (lv_expr_1_0= ruleAlphaUnaryTerminalExpression )
            // InternalAlpha.g:7518:5: lv_expr_1_0= ruleAlphaUnaryTerminalExpression
            {

            					newCompositeNode(grammarAccess.getUnaryExpressionAccess().getExprAlphaUnaryTerminalExpressionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_expr_1_0=ruleAlphaUnaryTerminalExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUnaryExpressionRule());
            					}
            					set(
            						current,
            						"expr",
            						lv_expr_1_0,
            						"alpha.model.Alpha.AlphaUnaryTerminalExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnaryExpression"


    // $ANTLR start "entryRuleVariableExpression"
    // InternalAlpha.g:7539:1: entryRuleVariableExpression returns [EObject current=null] : iv_ruleVariableExpression= ruleVariableExpression EOF ;
    public final EObject entryRuleVariableExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableExpression = null;


        try {
            // InternalAlpha.g:7539:59: (iv_ruleVariableExpression= ruleVariableExpression EOF )
            // InternalAlpha.g:7540:2: iv_ruleVariableExpression= ruleVariableExpression EOF
            {
             newCompositeNode(grammarAccess.getVariableExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariableExpression=ruleVariableExpression();

            state._fsp--;

             current =iv_ruleVariableExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableExpression"


    // $ANTLR start "ruleVariableExpression"
    // InternalAlpha.g:7546:1: ruleVariableExpression returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleVariableExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalAlpha.g:7552:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalAlpha.g:7553:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalAlpha.g:7553:2: ( (otherlv_0= RULE_ID ) )
            // InternalAlpha.g:7554:3: (otherlv_0= RULE_ID )
            {
            // InternalAlpha.g:7554:3: (otherlv_0= RULE_ID )
            // InternalAlpha.g:7555:4: otherlv_0= RULE_ID
            {

            				if (current==null) {
            					current = createModelElement(grammarAccess.getVariableExpressionRule());
            				}
            			
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(otherlv_0, grammarAccess.getVariableExpressionAccess().getVariableVariableCrossReference_0());
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableExpression"


    // $ANTLR start "entryRuleConstantExpression"
    // InternalAlpha.g:7569:1: entryRuleConstantExpression returns [EObject current=null] : iv_ruleConstantExpression= ruleConstantExpression EOF ;
    public final EObject entryRuleConstantExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstantExpression = null;


        try {
            // InternalAlpha.g:7569:59: (iv_ruleConstantExpression= ruleConstantExpression EOF )
            // InternalAlpha.g:7570:2: iv_ruleConstantExpression= ruleConstantExpression EOF
            {
             newCompositeNode(grammarAccess.getConstantExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConstantExpression=ruleConstantExpression();

            state._fsp--;

             current =iv_ruleConstantExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConstantExpression"


    // $ANTLR start "ruleConstantExpression"
    // InternalAlpha.g:7576:1: ruleConstantExpression returns [EObject current=null] : (this_BooleanExpression_0= ruleBooleanExpression | this_RealExpression_1= ruleRealExpression | this_IntegerExpression_2= ruleIntegerExpression ) ;
    public final EObject ruleConstantExpression() throws RecognitionException {
        EObject current = null;

        EObject this_BooleanExpression_0 = null;

        EObject this_RealExpression_1 = null;

        EObject this_IntegerExpression_2 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7582:2: ( (this_BooleanExpression_0= ruleBooleanExpression | this_RealExpression_1= ruleRealExpression | this_IntegerExpression_2= ruleIntegerExpression ) )
            // InternalAlpha.g:7583:2: (this_BooleanExpression_0= ruleBooleanExpression | this_RealExpression_1= ruleRealExpression | this_IntegerExpression_2= ruleIntegerExpression )
            {
            // InternalAlpha.g:7583:2: (this_BooleanExpression_0= ruleBooleanExpression | this_RealExpression_1= ruleRealExpression | this_IntegerExpression_2= ruleIntegerExpression )
            int alt98=3;
            switch ( input.LA(1) ) {
            case RULE_BOOLEAN:
                {
                alt98=1;
                }
                break;
            case 44:
                {
                int LA98_2 = input.LA(2);

                if ( (LA98_2==RULE_INT) ) {
                    int LA98_3 = input.LA(3);

                    if ( (LA98_3==29) ) {
                        alt98=2;
                    }
                    else if ( (LA98_3==EOF||LA98_3==13||LA98_3==16||LA98_3==24||LA98_3==31||LA98_3==34||LA98_3==37||(LA98_3>=43 && LA98_3<=46)||(LA98_3>=49 && LA98_3<=50)||(LA98_3>=53 && LA98_3<=56)||LA98_3==60||(LA98_3>=70 && LA98_3<=71)||(LA98_3>=75 && LA98_3<=76)) ) {
                        alt98=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 98, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 98, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
                {
                int LA98_3 = input.LA(2);

                if ( (LA98_3==29) ) {
                    alt98=2;
                }
                else if ( (LA98_3==EOF||LA98_3==13||LA98_3==16||LA98_3==24||LA98_3==31||LA98_3==34||LA98_3==37||(LA98_3>=43 && LA98_3<=46)||(LA98_3>=49 && LA98_3<=50)||(LA98_3>=53 && LA98_3<=56)||LA98_3==60||(LA98_3>=70 && LA98_3<=71)||(LA98_3>=75 && LA98_3<=76)) ) {
                    alt98=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 98, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }

            switch (alt98) {
                case 1 :
                    // InternalAlpha.g:7584:3: this_BooleanExpression_0= ruleBooleanExpression
                    {

                    			newCompositeNode(grammarAccess.getConstantExpressionAccess().getBooleanExpressionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_BooleanExpression_0=ruleBooleanExpression();

                    state._fsp--;


                    			current = this_BooleanExpression_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:7593:3: this_RealExpression_1= ruleRealExpression
                    {

                    			newCompositeNode(grammarAccess.getConstantExpressionAccess().getRealExpressionParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_RealExpression_1=ruleRealExpression();

                    state._fsp--;


                    			current = this_RealExpression_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:7602:3: this_IntegerExpression_2= ruleIntegerExpression
                    {

                    			newCompositeNode(grammarAccess.getConstantExpressionAccess().getIntegerExpressionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_IntegerExpression_2=ruleIntegerExpression();

                    state._fsp--;


                    			current = this_IntegerExpression_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConstantExpression"


    // $ANTLR start "entryRuleBooleanExpression"
    // InternalAlpha.g:7614:1: entryRuleBooleanExpression returns [EObject current=null] : iv_ruleBooleanExpression= ruleBooleanExpression EOF ;
    public final EObject entryRuleBooleanExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBooleanExpression = null;


        try {
            // InternalAlpha.g:7614:58: (iv_ruleBooleanExpression= ruleBooleanExpression EOF )
            // InternalAlpha.g:7615:2: iv_ruleBooleanExpression= ruleBooleanExpression EOF
            {
             newCompositeNode(grammarAccess.getBooleanExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBooleanExpression=ruleBooleanExpression();

            state._fsp--;

             current =iv_ruleBooleanExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBooleanExpression"


    // $ANTLR start "ruleBooleanExpression"
    // InternalAlpha.g:7621:1: ruleBooleanExpression returns [EObject current=null] : ( (lv_value_0_0= RULE_BOOLEAN ) ) ;
    public final EObject ruleBooleanExpression() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;


        	enterRule();

        try {
            // InternalAlpha.g:7627:2: ( ( (lv_value_0_0= RULE_BOOLEAN ) ) )
            // InternalAlpha.g:7628:2: ( (lv_value_0_0= RULE_BOOLEAN ) )
            {
            // InternalAlpha.g:7628:2: ( (lv_value_0_0= RULE_BOOLEAN ) )
            // InternalAlpha.g:7629:3: (lv_value_0_0= RULE_BOOLEAN )
            {
            // InternalAlpha.g:7629:3: (lv_value_0_0= RULE_BOOLEAN )
            // InternalAlpha.g:7630:4: lv_value_0_0= RULE_BOOLEAN
            {
            lv_value_0_0=(Token)match(input,RULE_BOOLEAN,FOLLOW_2); 

            				newLeafNode(lv_value_0_0, grammarAccess.getBooleanExpressionAccess().getValueBOOLEANTerminalRuleCall_0());
            			

            				if (current==null) {
            					current = createModelElement(grammarAccess.getBooleanExpressionRule());
            				}
            				setWithLastConsumed(
            					current,
            					"value",
            					lv_value_0_0,
            					"alpha.model.Alpha.BOOLEAN");
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBooleanExpression"


    // $ANTLR start "entryRuleIntegerExpression"
    // InternalAlpha.g:7649:1: entryRuleIntegerExpression returns [EObject current=null] : iv_ruleIntegerExpression= ruleIntegerExpression EOF ;
    public final EObject entryRuleIntegerExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIntegerExpression = null;


        try {
            // InternalAlpha.g:7649:58: (iv_ruleIntegerExpression= ruleIntegerExpression EOF )
            // InternalAlpha.g:7650:2: iv_ruleIntegerExpression= ruleIntegerExpression EOF
            {
             newCompositeNode(grammarAccess.getIntegerExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIntegerExpression=ruleIntegerExpression();

            state._fsp--;

             current =iv_ruleIntegerExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIntegerExpression"


    // $ANTLR start "ruleIntegerExpression"
    // InternalAlpha.g:7656:1: ruleIntegerExpression returns [EObject current=null] : ( (lv_value_0_0= ruleSINT ) ) ;
    public final EObject ruleIntegerExpression() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7662:2: ( ( (lv_value_0_0= ruleSINT ) ) )
            // InternalAlpha.g:7663:2: ( (lv_value_0_0= ruleSINT ) )
            {
            // InternalAlpha.g:7663:2: ( (lv_value_0_0= ruleSINT ) )
            // InternalAlpha.g:7664:3: (lv_value_0_0= ruleSINT )
            {
            // InternalAlpha.g:7664:3: (lv_value_0_0= ruleSINT )
            // InternalAlpha.g:7665:4: lv_value_0_0= ruleSINT
            {

            				newCompositeNode(grammarAccess.getIntegerExpressionAccess().getValueSINTParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleSINT();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getIntegerExpressionRule());
            				}
            				set(
            					current,
            					"value",
            					lv_value_0_0,
            					"alpha.model.Alpha.SINT");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIntegerExpression"


    // $ANTLR start "entryRuleRealExpression"
    // InternalAlpha.g:7685:1: entryRuleRealExpression returns [EObject current=null] : iv_ruleRealExpression= ruleRealExpression EOF ;
    public final EObject entryRuleRealExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRealExpression = null;


        try {
            // InternalAlpha.g:7685:55: (iv_ruleRealExpression= ruleRealExpression EOF )
            // InternalAlpha.g:7686:2: iv_ruleRealExpression= ruleRealExpression EOF
            {
             newCompositeNode(grammarAccess.getRealExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRealExpression=ruleRealExpression();

            state._fsp--;

             current =iv_ruleRealExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRealExpression"


    // $ANTLR start "ruleRealExpression"
    // InternalAlpha.g:7692:1: ruleRealExpression returns [EObject current=null] : ( (lv_value_0_0= ruleFLOAT ) ) ;
    public final EObject ruleRealExpression() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:7698:2: ( ( (lv_value_0_0= ruleFLOAT ) ) )
            // InternalAlpha.g:7699:2: ( (lv_value_0_0= ruleFLOAT ) )
            {
            // InternalAlpha.g:7699:2: ( (lv_value_0_0= ruleFLOAT ) )
            // InternalAlpha.g:7700:3: (lv_value_0_0= ruleFLOAT )
            {
            // InternalAlpha.g:7700:3: (lv_value_0_0= ruleFLOAT )
            // InternalAlpha.g:7701:4: lv_value_0_0= ruleFLOAT
            {

            				newCompositeNode(grammarAccess.getRealExpressionAccess().getValueFLOATParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_value_0_0=ruleFLOAT();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getRealExpressionRule());
            				}
            				set(
            					current,
            					"value",
            					lv_value_0_0,
            					"alpha.model.Alpha.FLOAT");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRealExpression"


    // $ANTLR start "entryRuleAREDUCTION_OP"
    // InternalAlpha.g:7721:1: entryRuleAREDUCTION_OP returns [String current=null] : iv_ruleAREDUCTION_OP= ruleAREDUCTION_OP EOF ;
    public final String entryRuleAREDUCTION_OP() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAREDUCTION_OP = null;


        try {
            // InternalAlpha.g:7721:53: (iv_ruleAREDUCTION_OP= ruleAREDUCTION_OP EOF )
            // InternalAlpha.g:7722:2: iv_ruleAREDUCTION_OP= ruleAREDUCTION_OP EOF
            {
             newCompositeNode(grammarAccess.getAREDUCTION_OPRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAREDUCTION_OP=ruleAREDUCTION_OP();

            state._fsp--;

             current =iv_ruleAREDUCTION_OP.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAREDUCTION_OP"


    // $ANTLR start "ruleAREDUCTION_OP"
    // InternalAlpha.g:7728:1: ruleAREDUCTION_OP returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'min' | kw= 'max' | kw= 'prod' | kw= 'sum' | kw= '--' | kw= 'and' | kw= 'or' | kw= 'xor' | kw= '+' | kw= '*' ) ;
    public final AntlrDatatypeRuleToken ruleAREDUCTION_OP() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:7734:2: ( (kw= 'min' | kw= 'max' | kw= 'prod' | kw= 'sum' | kw= '--' | kw= 'and' | kw= 'or' | kw= 'xor' | kw= '+' | kw= '*' ) )
            // InternalAlpha.g:7735:2: (kw= 'min' | kw= 'max' | kw= 'prod' | kw= 'sum' | kw= '--' | kw= 'and' | kw= 'or' | kw= 'xor' | kw= '+' | kw= '*' )
            {
            // InternalAlpha.g:7735:2: (kw= 'min' | kw= 'max' | kw= 'prod' | kw= 'sum' | kw= '--' | kw= 'and' | kw= 'or' | kw= 'xor' | kw= '+' | kw= '*' )
            int alt99=10;
            switch ( input.LA(1) ) {
            case 70:
                {
                alt99=1;
                }
                break;
            case 71:
                {
                alt99=2;
                }
                break;
            case 72:
                {
                alt99=3;
                }
                break;
            case 73:
                {
                alt99=4;
                }
                break;
            case 74:
                {
                alt99=5;
                }
                break;
            case 49:
                {
                alt99=6;
                }
                break;
            case 50:
                {
                alt99=7;
                }
                break;
            case 75:
                {
                alt99=8;
                }
                break;
            case 43:
                {
                alt99=9;
                }
                break;
            case 45:
                {
                alt99=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }

            switch (alt99) {
                case 1 :
                    // InternalAlpha.g:7736:3: kw= 'min'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getMinKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:7742:3: kw= 'max'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getMaxKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:7748:3: kw= 'prod'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getProdKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalAlpha.g:7754:3: kw= 'sum'
                    {
                    kw=(Token)match(input,73,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getSumKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalAlpha.g:7760:3: kw= '--'
                    {
                    kw=(Token)match(input,74,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getHyphenMinusHyphenMinusKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalAlpha.g:7766:3: kw= 'and'
                    {
                    kw=(Token)match(input,49,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getAndKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalAlpha.g:7772:3: kw= 'or'
                    {
                    kw=(Token)match(input,50,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getOrKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalAlpha.g:7778:3: kw= 'xor'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getXorKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalAlpha.g:7784:3: kw= '+'
                    {
                    kw=(Token)match(input,43,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getPlusSignKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalAlpha.g:7790:3: kw= '*'
                    {
                    kw=(Token)match(input,45,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAREDUCTION_OPAccess().getAsteriskKeyword_9());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAREDUCTION_OP"


    // $ANTLR start "entryRuleAOrOP"
    // InternalAlpha.g:7799:1: entryRuleAOrOP returns [String current=null] : iv_ruleAOrOP= ruleAOrOP EOF ;
    public final String entryRuleAOrOP() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAOrOP = null;


        try {
            // InternalAlpha.g:7799:45: (iv_ruleAOrOP= ruleAOrOP EOF )
            // InternalAlpha.g:7800:2: iv_ruleAOrOP= ruleAOrOP EOF
            {
             newCompositeNode(grammarAccess.getAOrOPRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAOrOP=ruleAOrOP();

            state._fsp--;

             current =iv_ruleAOrOP.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAOrOP"


    // $ANTLR start "ruleAOrOP"
    // InternalAlpha.g:7806:1: ruleAOrOP returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'or' | kw= 'xor' ) ;
    public final AntlrDatatypeRuleToken ruleAOrOP() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:7812:2: ( (kw= 'or' | kw= 'xor' ) )
            // InternalAlpha.g:7813:2: (kw= 'or' | kw= 'xor' )
            {
            // InternalAlpha.g:7813:2: (kw= 'or' | kw= 'xor' )
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==50) ) {
                alt100=1;
            }
            else if ( (LA100_0==75) ) {
                alt100=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }
            switch (alt100) {
                case 1 :
                    // InternalAlpha.g:7814:3: kw= 'or'
                    {
                    kw=(Token)match(input,50,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAOrOPAccess().getOrKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:7820:3: kw= 'xor'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAOrOPAccess().getXorKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAOrOP"


    // $ANTLR start "entryRuleAAndOP"
    // InternalAlpha.g:7829:1: entryRuleAAndOP returns [String current=null] : iv_ruleAAndOP= ruleAAndOP EOF ;
    public final String entryRuleAAndOP() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAAndOP = null;


        try {
            // InternalAlpha.g:7829:46: (iv_ruleAAndOP= ruleAAndOP EOF )
            // InternalAlpha.g:7830:2: iv_ruleAAndOP= ruleAAndOP EOF
            {
             newCompositeNode(grammarAccess.getAAndOPRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAAndOP=ruleAAndOP();

            state._fsp--;

             current =iv_ruleAAndOP.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAAndOP"


    // $ANTLR start "ruleAAndOP"
    // InternalAlpha.g:7836:1: ruleAAndOP returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'and' ;
    public final AntlrDatatypeRuleToken ruleAAndOP() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:7842:2: (kw= 'and' )
            // InternalAlpha.g:7843:2: kw= 'and'
            {
            kw=(Token)match(input,49,FOLLOW_2); 

            		current.merge(kw);
            		newLeafNode(kw, grammarAccess.getAAndOPAccess().getAndKeyword());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAAndOP"


    // $ANTLR start "entryRuleARelationalOP"
    // InternalAlpha.g:7851:1: entryRuleARelationalOP returns [String current=null] : iv_ruleARelationalOP= ruleARelationalOP EOF ;
    public final String entryRuleARelationalOP() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleARelationalOP = null;


        try {
            // InternalAlpha.g:7851:53: (iv_ruleARelationalOP= ruleARelationalOP EOF )
            // InternalAlpha.g:7852:2: iv_ruleARelationalOP= ruleARelationalOP EOF
            {
             newCompositeNode(grammarAccess.getARelationalOPRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleARelationalOP=ruleARelationalOP();

            state._fsp--;

             current =iv_ruleARelationalOP.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleARelationalOP"


    // $ANTLR start "ruleARelationalOP"
    // InternalAlpha.g:7858:1: ruleARelationalOP returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '=' | kw= '!=' | kw= '>=' | kw= '>' | kw= '<' | kw= '<=' ) ;
    public final AntlrDatatypeRuleToken ruleARelationalOP() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:7864:2: ( (kw= '=' | kw= '!=' | kw= '>=' | kw= '>' | kw= '<' | kw= '<=' ) )
            // InternalAlpha.g:7865:2: (kw= '=' | kw= '!=' | kw= '>=' | kw= '>' | kw= '<' | kw= '<=' )
            {
            // InternalAlpha.g:7865:2: (kw= '=' | kw= '!=' | kw= '>=' | kw= '>' | kw= '<' | kw= '<=' )
            int alt101=6;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt101=1;
                }
                break;
            case 76:
                {
                alt101=2;
                }
                break;
            case 53:
                {
                alt101=3;
                }
                break;
            case 54:
                {
                alt101=4;
                }
                break;
            case 55:
                {
                alt101=5;
                }
                break;
            case 56:
                {
                alt101=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }

            switch (alt101) {
                case 1 :
                    // InternalAlpha.g:7866:3: kw= '='
                    {
                    kw=(Token)match(input,13,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getARelationalOPAccess().getEqualsSignKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:7872:3: kw= '!='
                    {
                    kw=(Token)match(input,76,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getARelationalOPAccess().getExclamationMarkEqualsSignKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:7878:3: kw= '>='
                    {
                    kw=(Token)match(input,53,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getARelationalOPAccess().getGreaterThanSignEqualsSignKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalAlpha.g:7884:3: kw= '>'
                    {
                    kw=(Token)match(input,54,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getARelationalOPAccess().getGreaterThanSignKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalAlpha.g:7890:3: kw= '<'
                    {
                    kw=(Token)match(input,55,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getARelationalOPAccess().getLessThanSignKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalAlpha.g:7896:3: kw= '<='
                    {
                    kw=(Token)match(input,56,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getARelationalOPAccess().getLessThanSignEqualsSignKeyword_5());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleARelationalOP"


    // $ANTLR start "entryRuleAAdditiveOP"
    // InternalAlpha.g:7905:1: entryRuleAAdditiveOP returns [String current=null] : iv_ruleAAdditiveOP= ruleAAdditiveOP EOF ;
    public final String entryRuleAAdditiveOP() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAAdditiveOP = null;


        try {
            // InternalAlpha.g:7905:51: (iv_ruleAAdditiveOP= ruleAAdditiveOP EOF )
            // InternalAlpha.g:7906:2: iv_ruleAAdditiveOP= ruleAAdditiveOP EOF
            {
             newCompositeNode(grammarAccess.getAAdditiveOPRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAAdditiveOP=ruleAAdditiveOP();

            state._fsp--;

             current =iv_ruleAAdditiveOP.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAAdditiveOP"


    // $ANTLR start "ruleAAdditiveOP"
    // InternalAlpha.g:7912:1: ruleAAdditiveOP returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '+' | kw= '-' ) ;
    public final AntlrDatatypeRuleToken ruleAAdditiveOP() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:7918:2: ( (kw= '+' | kw= '-' ) )
            // InternalAlpha.g:7919:2: (kw= '+' | kw= '-' )
            {
            // InternalAlpha.g:7919:2: (kw= '+' | kw= '-' )
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==43) ) {
                alt102=1;
            }
            else if ( (LA102_0==44) ) {
                alt102=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }
            switch (alt102) {
                case 1 :
                    // InternalAlpha.g:7920:3: kw= '+'
                    {
                    kw=(Token)match(input,43,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAAdditiveOPAccess().getPlusSignKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:7926:3: kw= '-'
                    {
                    kw=(Token)match(input,44,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAAdditiveOPAccess().getHyphenMinusKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAAdditiveOP"


    // $ANTLR start "entryRuleAMultiplicativeOP"
    // InternalAlpha.g:7935:1: entryRuleAMultiplicativeOP returns [String current=null] : iv_ruleAMultiplicativeOP= ruleAMultiplicativeOP EOF ;
    public final String entryRuleAMultiplicativeOP() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAMultiplicativeOP = null;


        try {
            // InternalAlpha.g:7935:57: (iv_ruleAMultiplicativeOP= ruleAMultiplicativeOP EOF )
            // InternalAlpha.g:7936:2: iv_ruleAMultiplicativeOP= ruleAMultiplicativeOP EOF
            {
             newCompositeNode(grammarAccess.getAMultiplicativeOPRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAMultiplicativeOP=ruleAMultiplicativeOP();

            state._fsp--;

             current =iv_ruleAMultiplicativeOP.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAMultiplicativeOP"


    // $ANTLR start "ruleAMultiplicativeOP"
    // InternalAlpha.g:7942:1: ruleAMultiplicativeOP returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | kw= '/' ) ;
    public final AntlrDatatypeRuleToken ruleAMultiplicativeOP() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:7948:2: ( (kw= '*' | kw= '/' ) )
            // InternalAlpha.g:7949:2: (kw= '*' | kw= '/' )
            {
            // InternalAlpha.g:7949:2: (kw= '*' | kw= '/' )
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==45) ) {
                alt103=1;
            }
            else if ( (LA103_0==46) ) {
                alt103=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }
            switch (alt103) {
                case 1 :
                    // InternalAlpha.g:7950:3: kw= '*'
                    {
                    kw=(Token)match(input,45,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAMultiplicativeOPAccess().getAsteriskKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:7956:3: kw= '/'
                    {
                    kw=(Token)match(input,46,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAMultiplicativeOPAccess().getSolidusKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAMultiplicativeOP"


    // $ANTLR start "entryRuleAMINMAX_OP"
    // InternalAlpha.g:7965:1: entryRuleAMINMAX_OP returns [String current=null] : iv_ruleAMINMAX_OP= ruleAMINMAX_OP EOF ;
    public final String entryRuleAMINMAX_OP() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAMINMAX_OP = null;


        try {
            // InternalAlpha.g:7965:50: (iv_ruleAMINMAX_OP= ruleAMINMAX_OP EOF )
            // InternalAlpha.g:7966:2: iv_ruleAMINMAX_OP= ruleAMINMAX_OP EOF
            {
             newCompositeNode(grammarAccess.getAMINMAX_OPRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAMINMAX_OP=ruleAMINMAX_OP();

            state._fsp--;

             current =iv_ruleAMINMAX_OP.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAMINMAX_OP"


    // $ANTLR start "ruleAMINMAX_OP"
    // InternalAlpha.g:7972:1: ruleAMINMAX_OP returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'min' | kw= 'max' ) ;
    public final AntlrDatatypeRuleToken ruleAMINMAX_OP() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:7978:2: ( (kw= 'min' | kw= 'max' ) )
            // InternalAlpha.g:7979:2: (kw= 'min' | kw= 'max' )
            {
            // InternalAlpha.g:7979:2: (kw= 'min' | kw= 'max' )
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==70) ) {
                alt104=1;
            }
            else if ( (LA104_0==71) ) {
                alt104=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }
            switch (alt104) {
                case 1 :
                    // InternalAlpha.g:7980:3: kw= 'min'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAMINMAX_OPAccess().getMinKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:7986:3: kw= 'max'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAMINMAX_OPAccess().getMaxKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAMINMAX_OP"


    // $ANTLR start "entryRuleAUnaryOP"
    // InternalAlpha.g:7995:1: entryRuleAUnaryOP returns [String current=null] : iv_ruleAUnaryOP= ruleAUnaryOP EOF ;
    public final String entryRuleAUnaryOP() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAUnaryOP = null;


        try {
            // InternalAlpha.g:7995:48: (iv_ruleAUnaryOP= ruleAUnaryOP EOF )
            // InternalAlpha.g:7996:2: iv_ruleAUnaryOP= ruleAUnaryOP EOF
            {
             newCompositeNode(grammarAccess.getAUnaryOPRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAUnaryOP=ruleAUnaryOP();

            state._fsp--;

             current =iv_ruleAUnaryOP.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAUnaryOP"


    // $ANTLR start "ruleAUnaryOP"
    // InternalAlpha.g:8002:1: ruleAUnaryOP returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'not' | kw= '-' ) ;
    public final AntlrDatatypeRuleToken ruleAUnaryOP() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:8008:2: ( (kw= 'not' | kw= '-' ) )
            // InternalAlpha.g:8009:2: (kw= 'not' | kw= '-' )
            {
            // InternalAlpha.g:8009:2: (kw= 'not' | kw= '-' )
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==77) ) {
                alt105=1;
            }
            else if ( (LA105_0==44) ) {
                alt105=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 105, 0, input);

                throw nvae;
            }
            switch (alt105) {
                case 1 :
                    // InternalAlpha.g:8010:3: kw= 'not'
                    {
                    kw=(Token)match(input,77,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAUnaryOPAccess().getNotKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:8016:3: kw= '-'
                    {
                    kw=(Token)match(input,44,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAUnaryOPAccess().getHyphenMinusKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAUnaryOP"


    // $ANTLR start "entryRulePolyhedralObject"
    // InternalAlpha.g:8025:1: entryRulePolyhedralObject returns [EObject current=null] : iv_rulePolyhedralObject= rulePolyhedralObject EOF ;
    public final EObject entryRulePolyhedralObject() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePolyhedralObject = null;


        try {
            // InternalAlpha.g:8025:57: (iv_rulePolyhedralObject= rulePolyhedralObject EOF )
            // InternalAlpha.g:8026:2: iv_rulePolyhedralObject= rulePolyhedralObject EOF
            {
             newCompositeNode(grammarAccess.getPolyhedralObjectRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePolyhedralObject=rulePolyhedralObject();

            state._fsp--;

             current =iv_rulePolyhedralObject; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePolyhedralObject"


    // $ANTLR start "rulePolyhedralObject"
    // InternalAlpha.g:8032:1: rulePolyhedralObject returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleCalculatorExpression ) ) ) ;
    public final EObject rulePolyhedralObject() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        EObject lv_expr_2_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:8038:2: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleCalculatorExpression ) ) ) )
            // InternalAlpha.g:8039:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleCalculatorExpression ) ) )
            {
            // InternalAlpha.g:8039:2: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleCalculatorExpression ) ) )
            // InternalAlpha.g:8040:3: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_expr_2_0= ruleCalculatorExpression ) )
            {
            // InternalAlpha.g:8040:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalAlpha.g:8041:4: (lv_name_0_0= RULE_ID )
            {
            // InternalAlpha.g:8041:4: (lv_name_0_0= RULE_ID )
            // InternalAlpha.g:8042:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_6); 

            					newLeafNode(lv_name_0_0, grammarAccess.getPolyhedralObjectAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPolyhedralObjectRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"alpha.model.Alpha.ID");
            				

            }


            }

            otherlv_1=(Token)match(input,13,FOLLOW_20); 

            			newLeafNode(otherlv_1, grammarAccess.getPolyhedralObjectAccess().getEqualsSignKeyword_1());
            		
            // InternalAlpha.g:8062:3: ( (lv_expr_2_0= ruleCalculatorExpression ) )
            // InternalAlpha.g:8063:4: (lv_expr_2_0= ruleCalculatorExpression )
            {
            // InternalAlpha.g:8063:4: (lv_expr_2_0= ruleCalculatorExpression )
            // InternalAlpha.g:8064:5: lv_expr_2_0= ruleCalculatorExpression
            {

            					newCompositeNode(grammarAccess.getPolyhedralObjectAccess().getExprCalculatorExpressionParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_expr_2_0=ruleCalculatorExpression();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPolyhedralObjectRule());
            					}
            					set(
            						current,
            						"expr",
            						lv_expr_2_0,
            						"alpha.model.Alpha.CalculatorExpression");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePolyhedralObject"


    // $ANTLR start "entryRuleCalculatorExpression"
    // InternalAlpha.g:8085:1: entryRuleCalculatorExpression returns [EObject current=null] : iv_ruleCalculatorExpression= ruleCalculatorExpression EOF ;
    public final EObject entryRuleCalculatorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCalculatorExpression = null;


        try {
            // InternalAlpha.g:8085:61: (iv_ruleCalculatorExpression= ruleCalculatorExpression EOF )
            // InternalAlpha.g:8086:2: iv_ruleCalculatorExpression= ruleCalculatorExpression EOF
            {
             newCompositeNode(grammarAccess.getCalculatorExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCalculatorExpression=ruleCalculatorExpression();

            state._fsp--;

             current =iv_ruleCalculatorExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCalculatorExpression"


    // $ANTLR start "ruleCalculatorExpression"
    // InternalAlpha.g:8092:1: ruleCalculatorExpression returns [EObject current=null] : (this_UnaryOrTerminalCalculatorExpression_0= ruleUnaryOrTerminalCalculatorExpression ( () ( (lv_operator_2_0= ruleABinaryCalcOp ) ) ( (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression ) ) )* ) ;
    public final EObject ruleCalculatorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_UnaryOrTerminalCalculatorExpression_0 = null;

        AntlrDatatypeRuleToken lv_operator_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:8098:2: ( (this_UnaryOrTerminalCalculatorExpression_0= ruleUnaryOrTerminalCalculatorExpression ( () ( (lv_operator_2_0= ruleABinaryCalcOp ) ) ( (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression ) ) )* ) )
            // InternalAlpha.g:8099:2: (this_UnaryOrTerminalCalculatorExpression_0= ruleUnaryOrTerminalCalculatorExpression ( () ( (lv_operator_2_0= ruleABinaryCalcOp ) ) ( (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression ) ) )* )
            {
            // InternalAlpha.g:8099:2: (this_UnaryOrTerminalCalculatorExpression_0= ruleUnaryOrTerminalCalculatorExpression ( () ( (lv_operator_2_0= ruleABinaryCalcOp ) ) ( (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression ) ) )* )
            // InternalAlpha.g:8100:3: this_UnaryOrTerminalCalculatorExpression_0= ruleUnaryOrTerminalCalculatorExpression ( () ( (lv_operator_2_0= ruleABinaryCalcOp ) ) ( (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression ) ) )*
            {

            			newCompositeNode(grammarAccess.getCalculatorExpressionAccess().getUnaryOrTerminalCalculatorExpressionParserRuleCall_0());
            		
            pushFollow(FOLLOW_80);
            this_UnaryOrTerminalCalculatorExpression_0=ruleUnaryOrTerminalCalculatorExpression();

            state._fsp--;


            			current = this_UnaryOrTerminalCalculatorExpression_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalAlpha.g:8108:3: ( () ( (lv_operator_2_0= ruleABinaryCalcOp ) ) ( (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression ) ) )*
            loop106:
            do {
                int alt106=2;
                int LA106_0 = input.LA(1);

                if ( ((LA106_0>=43 && LA106_0<=45)||LA106_0==63||(LA106_0>=85 && LA106_0<=87)) ) {
                    alt106=1;
                }


                switch (alt106) {
            	case 1 :
            	    // InternalAlpha.g:8109:4: () ( (lv_operator_2_0= ruleABinaryCalcOp ) ) ( (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression ) )
            	    {
            	    // InternalAlpha.g:8109:4: ()
            	    // InternalAlpha.g:8110:5: 
            	    {

            	    					current = forceCreateModelElementAndSet(
            	    						grammarAccess.getCalculatorExpressionAccess().getBinaryCalculatorExpressionLeftAction_1_0(),
            	    						current);
            	    				

            	    }

            	    // InternalAlpha.g:8116:4: ( (lv_operator_2_0= ruleABinaryCalcOp ) )
            	    // InternalAlpha.g:8117:5: (lv_operator_2_0= ruleABinaryCalcOp )
            	    {
            	    // InternalAlpha.g:8117:5: (lv_operator_2_0= ruleABinaryCalcOp )
            	    // InternalAlpha.g:8118:6: lv_operator_2_0= ruleABinaryCalcOp
            	    {

            	    						newCompositeNode(grammarAccess.getCalculatorExpressionAccess().getOperatorABinaryCalcOpParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_20);
            	    lv_operator_2_0=ruleABinaryCalcOp();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCalculatorExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"operator",
            	    							lv_operator_2_0,
            	    							"alpha.model.Alpha.ABinaryCalcOp");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalAlpha.g:8135:4: ( (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression ) )
            	    // InternalAlpha.g:8136:5: (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression )
            	    {
            	    // InternalAlpha.g:8136:5: (lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression )
            	    // InternalAlpha.g:8137:6: lv_right_3_0= ruleUnaryOrTerminalCalculatorExpression
            	    {

            	    						newCompositeNode(grammarAccess.getCalculatorExpressionAccess().getRightUnaryOrTerminalCalculatorExpressionParserRuleCall_1_2_0());
            	    					
            	    pushFollow(FOLLOW_80);
            	    lv_right_3_0=ruleUnaryOrTerminalCalculatorExpression();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCalculatorExpressionRule());
            	    						}
            	    						set(
            	    							current,
            	    							"right",
            	    							lv_right_3_0,
            	    							"alpha.model.Alpha.UnaryOrTerminalCalculatorExpression");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop106;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCalculatorExpression"


    // $ANTLR start "entryRuleUnaryOrTerminalCalculatorExpression"
    // InternalAlpha.g:8159:1: entryRuleUnaryOrTerminalCalculatorExpression returns [EObject current=null] : iv_ruleUnaryOrTerminalCalculatorExpression= ruleUnaryOrTerminalCalculatorExpression EOF ;
    public final EObject entryRuleUnaryOrTerminalCalculatorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryOrTerminalCalculatorExpression = null;


        try {
            // InternalAlpha.g:8159:76: (iv_ruleUnaryOrTerminalCalculatorExpression= ruleUnaryOrTerminalCalculatorExpression EOF )
            // InternalAlpha.g:8160:2: iv_ruleUnaryOrTerminalCalculatorExpression= ruleUnaryOrTerminalCalculatorExpression EOF
            {
             newCompositeNode(grammarAccess.getUnaryOrTerminalCalculatorExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUnaryOrTerminalCalculatorExpression=ruleUnaryOrTerminalCalculatorExpression();

            state._fsp--;

             current =iv_ruleUnaryOrTerminalCalculatorExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnaryOrTerminalCalculatorExpression"


    // $ANTLR start "ruleUnaryOrTerminalCalculatorExpression"
    // InternalAlpha.g:8166:1: ruleUnaryOrTerminalCalculatorExpression returns [EObject current=null] : (this_UnaryCalculatorExpression_0= ruleUnaryCalculatorExpression | this_CalculatorExpressionTerminal_1= ruleCalculatorExpressionTerminal ) ;
    public final EObject ruleUnaryOrTerminalCalculatorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_UnaryCalculatorExpression_0 = null;

        EObject this_CalculatorExpressionTerminal_1 = null;



        	enterRule();

        try {
            // InternalAlpha.g:8172:2: ( (this_UnaryCalculatorExpression_0= ruleUnaryCalculatorExpression | this_CalculatorExpressionTerminal_1= ruleCalculatorExpressionTerminal ) )
            // InternalAlpha.g:8173:2: (this_UnaryCalculatorExpression_0= ruleUnaryCalculatorExpression | this_CalculatorExpressionTerminal_1= ruleCalculatorExpressionTerminal )
            {
            // InternalAlpha.g:8173:2: (this_UnaryCalculatorExpression_0= ruleUnaryCalculatorExpression | this_CalculatorExpressionTerminal_1= ruleCalculatorExpressionTerminal )
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( ((LA107_0>=79 && LA107_0<=84)) ) {
                alt107=1;
            }
            else if ( (LA107_0==RULE_ID||LA107_0==15||LA107_0==19||LA107_0==37||LA107_0==42) ) {
                alt107=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }
            switch (alt107) {
                case 1 :
                    // InternalAlpha.g:8174:3: this_UnaryCalculatorExpression_0= ruleUnaryCalculatorExpression
                    {

                    			newCompositeNode(grammarAccess.getUnaryOrTerminalCalculatorExpressionAccess().getUnaryCalculatorExpressionParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_UnaryCalculatorExpression_0=ruleUnaryCalculatorExpression();

                    state._fsp--;


                    			current = this_UnaryCalculatorExpression_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:8183:3: this_CalculatorExpressionTerminal_1= ruleCalculatorExpressionTerminal
                    {

                    			newCompositeNode(grammarAccess.getUnaryOrTerminalCalculatorExpressionAccess().getCalculatorExpressionTerminalParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_CalculatorExpressionTerminal_1=ruleCalculatorExpressionTerminal();

                    state._fsp--;


                    			current = this_CalculatorExpressionTerminal_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnaryOrTerminalCalculatorExpression"


    // $ANTLR start "entryRuleCalculatorExpressionTerminal"
    // InternalAlpha.g:8195:1: entryRuleCalculatorExpressionTerminal returns [EObject current=null] : iv_ruleCalculatorExpressionTerminal= ruleCalculatorExpressionTerminal EOF ;
    public final EObject entryRuleCalculatorExpressionTerminal() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCalculatorExpressionTerminal = null;


        try {
            // InternalAlpha.g:8195:69: (iv_ruleCalculatorExpressionTerminal= ruleCalculatorExpressionTerminal EOF )
            // InternalAlpha.g:8196:2: iv_ruleCalculatorExpressionTerminal= ruleCalculatorExpressionTerminal EOF
            {
             newCompositeNode(grammarAccess.getCalculatorExpressionTerminalRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCalculatorExpressionTerminal=ruleCalculatorExpressionTerminal();

            state._fsp--;

             current =iv_ruleCalculatorExpressionTerminal; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCalculatorExpressionTerminal"


    // $ANTLR start "ruleCalculatorExpressionTerminal"
    // InternalAlpha.g:8202:1: ruleCalculatorExpressionTerminal returns [EObject current=null] : (this_JNIDomain_0= ruleJNIDomain | this_JNIRelation_1= ruleJNIRelation | this_JNIFunction_2= ruleJNIFunction | this_DefinedObject_3= ruleDefinedObject | this_RectangularDomain_4= ruleRectangularDomain | this_VariableDomain_5= ruleVariableDomain | (otherlv_6= '(' this_CalculatorExpression_7= ruleCalculatorExpression otherlv_8= ')' ) ) ;
    public final EObject ruleCalculatorExpressionTerminal() throws RecognitionException {
        EObject current = null;

        Token otherlv_6=null;
        Token otherlv_8=null;
        EObject this_JNIDomain_0 = null;

        EObject this_JNIRelation_1 = null;

        EObject this_JNIFunction_2 = null;

        EObject this_DefinedObject_3 = null;

        EObject this_RectangularDomain_4 = null;

        EObject this_VariableDomain_5 = null;

        EObject this_CalculatorExpression_7 = null;



        	enterRule();

        try {
            // InternalAlpha.g:8208:2: ( (this_JNIDomain_0= ruleJNIDomain | this_JNIRelation_1= ruleJNIRelation | this_JNIFunction_2= ruleJNIFunction | this_DefinedObject_3= ruleDefinedObject | this_RectangularDomain_4= ruleRectangularDomain | this_VariableDomain_5= ruleVariableDomain | (otherlv_6= '(' this_CalculatorExpression_7= ruleCalculatorExpression otherlv_8= ')' ) ) )
            // InternalAlpha.g:8209:2: (this_JNIDomain_0= ruleJNIDomain | this_JNIRelation_1= ruleJNIRelation | this_JNIFunction_2= ruleJNIFunction | this_DefinedObject_3= ruleDefinedObject | this_RectangularDomain_4= ruleRectangularDomain | this_VariableDomain_5= ruleVariableDomain | (otherlv_6= '(' this_CalculatorExpression_7= ruleCalculatorExpression otherlv_8= ')' ) )
            {
            // InternalAlpha.g:8209:2: (this_JNIDomain_0= ruleJNIDomain | this_JNIRelation_1= ruleJNIRelation | this_JNIFunction_2= ruleJNIFunction | this_DefinedObject_3= ruleDefinedObject | this_RectangularDomain_4= ruleRectangularDomain | this_VariableDomain_5= ruleVariableDomain | (otherlv_6= '(' this_CalculatorExpression_7= ruleCalculatorExpression otherlv_8= ')' ) )
            int alt108=7;
            alt108 = dfa108.predict(input);
            switch (alt108) {
                case 1 :
                    // InternalAlpha.g:8210:3: this_JNIDomain_0= ruleJNIDomain
                    {

                    			newCompositeNode(grammarAccess.getCalculatorExpressionTerminalAccess().getJNIDomainParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_JNIDomain_0=ruleJNIDomain();

                    state._fsp--;


                    			current = this_JNIDomain_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:8219:3: this_JNIRelation_1= ruleJNIRelation
                    {

                    			newCompositeNode(grammarAccess.getCalculatorExpressionTerminalAccess().getJNIRelationParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_JNIRelation_1=ruleJNIRelation();

                    state._fsp--;


                    			current = this_JNIRelation_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:8228:3: this_JNIFunction_2= ruleJNIFunction
                    {

                    			newCompositeNode(grammarAccess.getCalculatorExpressionTerminalAccess().getJNIFunctionParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_JNIFunction_2=ruleJNIFunction();

                    state._fsp--;


                    			current = this_JNIFunction_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalAlpha.g:8237:3: this_DefinedObject_3= ruleDefinedObject
                    {

                    			newCompositeNode(grammarAccess.getCalculatorExpressionTerminalAccess().getDefinedObjectParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_DefinedObject_3=ruleDefinedObject();

                    state._fsp--;


                    			current = this_DefinedObject_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalAlpha.g:8246:3: this_RectangularDomain_4= ruleRectangularDomain
                    {

                    			newCompositeNode(grammarAccess.getCalculatorExpressionTerminalAccess().getRectangularDomainParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_RectangularDomain_4=ruleRectangularDomain();

                    state._fsp--;


                    			current = this_RectangularDomain_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalAlpha.g:8255:3: this_VariableDomain_5= ruleVariableDomain
                    {

                    			newCompositeNode(grammarAccess.getCalculatorExpressionTerminalAccess().getVariableDomainParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_VariableDomain_5=ruleVariableDomain();

                    state._fsp--;


                    			current = this_VariableDomain_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 7 :
                    // InternalAlpha.g:8264:3: (otherlv_6= '(' this_CalculatorExpression_7= ruleCalculatorExpression otherlv_8= ')' )
                    {
                    // InternalAlpha.g:8264:3: (otherlv_6= '(' this_CalculatorExpression_7= ruleCalculatorExpression otherlv_8= ')' )
                    // InternalAlpha.g:8265:4: otherlv_6= '(' this_CalculatorExpression_7= ruleCalculatorExpression otherlv_8= ')'
                    {
                    otherlv_6=(Token)match(input,15,FOLLOW_20); 

                    				newLeafNode(otherlv_6, grammarAccess.getCalculatorExpressionTerminalAccess().getLeftParenthesisKeyword_6_0());
                    			

                    				newCompositeNode(grammarAccess.getCalculatorExpressionTerminalAccess().getCalculatorExpressionParserRuleCall_6_1());
                    			
                    pushFollow(FOLLOW_9);
                    this_CalculatorExpression_7=ruleCalculatorExpression();

                    state._fsp--;


                    				current = this_CalculatorExpression_7;
                    				afterParserOrEnumRuleCall();
                    			
                    otherlv_8=(Token)match(input,16,FOLLOW_2); 

                    				newLeafNode(otherlv_8, grammarAccess.getCalculatorExpressionTerminalAccess().getRightParenthesisKeyword_6_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCalculatorExpressionTerminal"


    // $ANTLR start "entryRuleUnaryCalculatorExpression"
    // InternalAlpha.g:8286:1: entryRuleUnaryCalculatorExpression returns [EObject current=null] : iv_ruleUnaryCalculatorExpression= ruleUnaryCalculatorExpression EOF ;
    public final EObject entryRuleUnaryCalculatorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnaryCalculatorExpression = null;


        try {
            // InternalAlpha.g:8286:66: (iv_ruleUnaryCalculatorExpression= ruleUnaryCalculatorExpression EOF )
            // InternalAlpha.g:8287:2: iv_ruleUnaryCalculatorExpression= ruleUnaryCalculatorExpression EOF
            {
             newCompositeNode(grammarAccess.getUnaryCalculatorExpressionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUnaryCalculatorExpression=ruleUnaryCalculatorExpression();

            state._fsp--;

             current =iv_ruleUnaryCalculatorExpression; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnaryCalculatorExpression"


    // $ANTLR start "ruleUnaryCalculatorExpression"
    // InternalAlpha.g:8293:1: ruleUnaryCalculatorExpression returns [EObject current=null] : ( ( (lv_operator_0_0= ruleAUnaryCalcOp ) ) ( (lv_expr_1_0= ruleCalculatorExpressionTerminal ) ) ) ;
    public final EObject ruleUnaryCalculatorExpression() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_operator_0_0 = null;

        EObject lv_expr_1_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:8299:2: ( ( ( (lv_operator_0_0= ruleAUnaryCalcOp ) ) ( (lv_expr_1_0= ruleCalculatorExpressionTerminal ) ) ) )
            // InternalAlpha.g:8300:2: ( ( (lv_operator_0_0= ruleAUnaryCalcOp ) ) ( (lv_expr_1_0= ruleCalculatorExpressionTerminal ) ) )
            {
            // InternalAlpha.g:8300:2: ( ( (lv_operator_0_0= ruleAUnaryCalcOp ) ) ( (lv_expr_1_0= ruleCalculatorExpressionTerminal ) ) )
            // InternalAlpha.g:8301:3: ( (lv_operator_0_0= ruleAUnaryCalcOp ) ) ( (lv_expr_1_0= ruleCalculatorExpressionTerminal ) )
            {
            // InternalAlpha.g:8301:3: ( (lv_operator_0_0= ruleAUnaryCalcOp ) )
            // InternalAlpha.g:8302:4: (lv_operator_0_0= ruleAUnaryCalcOp )
            {
            // InternalAlpha.g:8302:4: (lv_operator_0_0= ruleAUnaryCalcOp )
            // InternalAlpha.g:8303:5: lv_operator_0_0= ruleAUnaryCalcOp
            {

            					newCompositeNode(grammarAccess.getUnaryCalculatorExpressionAccess().getOperatorAUnaryCalcOpParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_20);
            lv_operator_0_0=ruleAUnaryCalcOp();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUnaryCalculatorExpressionRule());
            					}
            					set(
            						current,
            						"operator",
            						lv_operator_0_0,
            						"alpha.model.Alpha.AUnaryCalcOp");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalAlpha.g:8320:3: ( (lv_expr_1_0= ruleCalculatorExpressionTerminal ) )
            // InternalAlpha.g:8321:4: (lv_expr_1_0= ruleCalculatorExpressionTerminal )
            {
            // InternalAlpha.g:8321:4: (lv_expr_1_0= ruleCalculatorExpressionTerminal )
            // InternalAlpha.g:8322:5: lv_expr_1_0= ruleCalculatorExpressionTerminal
            {

            					newCompositeNode(grammarAccess.getUnaryCalculatorExpressionAccess().getExprCalculatorExpressionTerminalParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_expr_1_0=ruleCalculatorExpressionTerminal();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getUnaryCalculatorExpressionRule());
            					}
            					set(
            						current,
            						"expr",
            						lv_expr_1_0,
            						"alpha.model.Alpha.CalculatorExpressionTerminal");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnaryCalculatorExpression"


    // $ANTLR start "entryRuleVariableDomain"
    // InternalAlpha.g:8343:1: entryRuleVariableDomain returns [EObject current=null] : iv_ruleVariableDomain= ruleVariableDomain EOF ;
    public final EObject entryRuleVariableDomain() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableDomain = null;


        try {
            // InternalAlpha.g:8343:55: (iv_ruleVariableDomain= ruleVariableDomain EOF )
            // InternalAlpha.g:8344:2: iv_ruleVariableDomain= ruleVariableDomain EOF
            {
             newCompositeNode(grammarAccess.getVariableDomainRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVariableDomain=ruleVariableDomain();

            state._fsp--;

             current =iv_ruleVariableDomain; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableDomain"


    // $ANTLR start "ruleVariableDomain"
    // InternalAlpha.g:8350:1: ruleVariableDomain returns [EObject current=null] : (otherlv_0= '{' ( (otherlv_1= RULE_ID ) ) otherlv_2= '}' ) ;
    public final EObject ruleVariableDomain() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalAlpha.g:8356:2: ( (otherlv_0= '{' ( (otherlv_1= RULE_ID ) ) otherlv_2= '}' ) )
            // InternalAlpha.g:8357:2: (otherlv_0= '{' ( (otherlv_1= RULE_ID ) ) otherlv_2= '}' )
            {
            // InternalAlpha.g:8357:2: (otherlv_0= '{' ( (otherlv_1= RULE_ID ) ) otherlv_2= '}' )
            // InternalAlpha.g:8358:3: otherlv_0= '{' ( (otherlv_1= RULE_ID ) ) otherlv_2= '}'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getVariableDomainAccess().getLeftCurlyBracketKeyword_0());
            		
            // InternalAlpha.g:8362:3: ( (otherlv_1= RULE_ID ) )
            // InternalAlpha.g:8363:4: (otherlv_1= RULE_ID )
            {
            // InternalAlpha.g:8363:4: (otherlv_1= RULE_ID )
            // InternalAlpha.g:8364:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVariableDomainRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_30); 

            					newLeafNode(otherlv_1, grammarAccess.getVariableDomainAccess().getVariableVariableCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,20,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getVariableDomainAccess().getRightCurlyBracketKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableDomain"


    // $ANTLR start "entryRuleRectangularDomain"
    // InternalAlpha.g:8383:1: entryRuleRectangularDomain returns [EObject current=null] : iv_ruleRectangularDomain= ruleRectangularDomain EOF ;
    public final EObject entryRuleRectangularDomain() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRectangularDomain = null;


        try {
            // InternalAlpha.g:8383:58: (iv_ruleRectangularDomain= ruleRectangularDomain EOF )
            // InternalAlpha.g:8384:2: iv_ruleRectangularDomain= ruleRectangularDomain EOF
            {
             newCompositeNode(grammarAccess.getRectangularDomainRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRectangularDomain=ruleRectangularDomain();

            state._fsp--;

             current =iv_ruleRectangularDomain; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRectangularDomain"


    // $ANTLR start "ruleRectangularDomain"
    // InternalAlpha.g:8390:1: ruleRectangularDomain returns [EObject current=null] : ( (otherlv_0= '[' ( (lv_upperBounds_1_0= ruleAISLExpression ) ) (otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) ) )* otherlv_4= ']' (otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']' )? ) | (otherlv_11= '[' ( (lv_lowerBounds_12_0= ruleAISLExpression ) ) otherlv_13= ':' ( (lv_upperBounds_14_0= ruleAISLExpression ) ) (otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) ) )* otherlv_19= ']' (otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']' )? ) ) ;
    public final EObject ruleRectangularDomain() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        AntlrDatatypeRuleToken lv_upperBounds_1_0 = null;

        AntlrDatatypeRuleToken lv_upperBounds_3_0 = null;

        AntlrDatatypeRuleToken lv_indexNames_7_0 = null;

        AntlrDatatypeRuleToken lv_indexNames_9_0 = null;

        AntlrDatatypeRuleToken lv_lowerBounds_12_0 = null;

        AntlrDatatypeRuleToken lv_upperBounds_14_0 = null;

        AntlrDatatypeRuleToken lv_lowerBounds_16_0 = null;

        AntlrDatatypeRuleToken lv_upperBounds_18_0 = null;

        AntlrDatatypeRuleToken lv_indexNames_22_0 = null;

        AntlrDatatypeRuleToken lv_indexNames_24_0 = null;



        	enterRule();

        try {
            // InternalAlpha.g:8396:2: ( ( (otherlv_0= '[' ( (lv_upperBounds_1_0= ruleAISLExpression ) ) (otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) ) )* otherlv_4= ']' (otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']' )? ) | (otherlv_11= '[' ( (lv_lowerBounds_12_0= ruleAISLExpression ) ) otherlv_13= ':' ( (lv_upperBounds_14_0= ruleAISLExpression ) ) (otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) ) )* otherlv_19= ']' (otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']' )? ) ) )
            // InternalAlpha.g:8397:2: ( (otherlv_0= '[' ( (lv_upperBounds_1_0= ruleAISLExpression ) ) (otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) ) )* otherlv_4= ']' (otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']' )? ) | (otherlv_11= '[' ( (lv_lowerBounds_12_0= ruleAISLExpression ) ) otherlv_13= ':' ( (lv_upperBounds_14_0= ruleAISLExpression ) ) (otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) ) )* otherlv_19= ']' (otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']' )? ) )
            {
            // InternalAlpha.g:8397:2: ( (otherlv_0= '[' ( (lv_upperBounds_1_0= ruleAISLExpression ) ) (otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) ) )* otherlv_4= ']' (otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']' )? ) | (otherlv_11= '[' ( (lv_lowerBounds_12_0= ruleAISLExpression ) ) otherlv_13= ':' ( (lv_upperBounds_14_0= ruleAISLExpression ) ) (otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) ) )* otherlv_19= ']' (otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']' )? ) )
            int alt115=2;
            alt115 = dfa115.predict(input);
            switch (alt115) {
                case 1 :
                    // InternalAlpha.g:8398:3: (otherlv_0= '[' ( (lv_upperBounds_1_0= ruleAISLExpression ) ) (otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) ) )* otherlv_4= ']' (otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']' )? )
                    {
                    // InternalAlpha.g:8398:3: (otherlv_0= '[' ( (lv_upperBounds_1_0= ruleAISLExpression ) ) (otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) ) )* otherlv_4= ']' (otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']' )? )
                    // InternalAlpha.g:8399:4: otherlv_0= '[' ( (lv_upperBounds_1_0= ruleAISLExpression ) ) (otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) ) )* otherlv_4= ']' (otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']' )?
                    {
                    otherlv_0=(Token)match(input,37,FOLLOW_33); 

                    				newLeafNode(otherlv_0, grammarAccess.getRectangularDomainAccess().getLeftSquareBracketKeyword_0_0());
                    			
                    // InternalAlpha.g:8403:4: ( (lv_upperBounds_1_0= ruleAISLExpression ) )
                    // InternalAlpha.g:8404:5: (lv_upperBounds_1_0= ruleAISLExpression )
                    {
                    // InternalAlpha.g:8404:5: (lv_upperBounds_1_0= ruleAISLExpression )
                    // InternalAlpha.g:8405:6: lv_upperBounds_1_0= ruleAISLExpression
                    {

                    						newCompositeNode(grammarAccess.getRectangularDomainAccess().getUpperBoundsAISLExpressionParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_32);
                    lv_upperBounds_1_0=ruleAISLExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                    						}
                    						add(
                    							current,
                    							"upperBounds",
                    							lv_upperBounds_1_0,
                    							"alpha.model.Alpha.AISLExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:8422:4: (otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) ) )*
                    loop109:
                    do {
                        int alt109=2;
                        int LA109_0 = input.LA(1);

                        if ( (LA109_0==24) ) {
                            alt109=1;
                        }


                        switch (alt109) {
                    	case 1 :
                    	    // InternalAlpha.g:8423:5: otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) )
                    	    {
                    	    otherlv_2=(Token)match(input,24,FOLLOW_33); 

                    	    					newLeafNode(otherlv_2, grammarAccess.getRectangularDomainAccess().getCommaKeyword_0_2_0());
                    	    				
                    	    // InternalAlpha.g:8427:5: ( (lv_upperBounds_3_0= ruleAISLExpression ) )
                    	    // InternalAlpha.g:8428:6: (lv_upperBounds_3_0= ruleAISLExpression )
                    	    {
                    	    // InternalAlpha.g:8428:6: (lv_upperBounds_3_0= ruleAISLExpression )
                    	    // InternalAlpha.g:8429:7: lv_upperBounds_3_0= ruleAISLExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getRectangularDomainAccess().getUpperBoundsAISLExpressionParserRuleCall_0_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_32);
                    	    lv_upperBounds_3_0=ruleAISLExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"upperBounds",
                    	    								lv_upperBounds_3_0,
                    	    								"alpha.model.Alpha.AISLExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop109;
                        }
                    } while (true);

                    otherlv_4=(Token)match(input,38,FOLLOW_81); 

                    				newLeafNode(otherlv_4, grammarAccess.getRectangularDomainAccess().getRightSquareBracketKeyword_0_3());
                    			
                    // InternalAlpha.g:8451:4: (otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']' )?
                    int alt111=2;
                    int LA111_0 = input.LA(1);

                    if ( (LA111_0==78) ) {
                        alt111=1;
                    }
                    switch (alt111) {
                        case 1 :
                            // InternalAlpha.g:8452:5: otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']'
                            {
                            otherlv_5=(Token)match(input,78,FOLLOW_36); 

                            					newLeafNode(otherlv_5, grammarAccess.getRectangularDomainAccess().getAsKeyword_0_4_0());
                            				
                            otherlv_6=(Token)match(input,37,FOLLOW_5); 

                            					newLeafNode(otherlv_6, grammarAccess.getRectangularDomainAccess().getLeftSquareBracketKeyword_0_4_1());
                            				
                            // InternalAlpha.g:8460:5: ( (lv_indexNames_7_0= ruleIndexName ) )
                            // InternalAlpha.g:8461:6: (lv_indexNames_7_0= ruleIndexName )
                            {
                            // InternalAlpha.g:8461:6: (lv_indexNames_7_0= ruleIndexName )
                            // InternalAlpha.g:8462:7: lv_indexNames_7_0= ruleIndexName
                            {

                            							newCompositeNode(grammarAccess.getRectangularDomainAccess().getIndexNamesIndexNameParserRuleCall_0_4_2_0());
                            						
                            pushFollow(FOLLOW_32);
                            lv_indexNames_7_0=ruleIndexName();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                            							}
                            							add(
                            								current,
                            								"indexNames",
                            								lv_indexNames_7_0,
                            								"alpha.model.Alpha.IndexName");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            // InternalAlpha.g:8479:5: (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )*
                            loop110:
                            do {
                                int alt110=2;
                                int LA110_0 = input.LA(1);

                                if ( (LA110_0==24) ) {
                                    alt110=1;
                                }


                                switch (alt110) {
                            	case 1 :
                            	    // InternalAlpha.g:8480:6: otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) )
                            	    {
                            	    otherlv_8=(Token)match(input,24,FOLLOW_5); 

                            	    						newLeafNode(otherlv_8, grammarAccess.getRectangularDomainAccess().getCommaKeyword_0_4_3_0());
                            	    					
                            	    // InternalAlpha.g:8484:6: ( (lv_indexNames_9_0= ruleIndexName ) )
                            	    // InternalAlpha.g:8485:7: (lv_indexNames_9_0= ruleIndexName )
                            	    {
                            	    // InternalAlpha.g:8485:7: (lv_indexNames_9_0= ruleIndexName )
                            	    // InternalAlpha.g:8486:8: lv_indexNames_9_0= ruleIndexName
                            	    {

                            	    								newCompositeNode(grammarAccess.getRectangularDomainAccess().getIndexNamesIndexNameParserRuleCall_0_4_3_1_0());
                            	    							
                            	    pushFollow(FOLLOW_32);
                            	    lv_indexNames_9_0=ruleIndexName();

                            	    state._fsp--;


                            	    								if (current==null) {
                            	    									current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                            	    								}
                            	    								add(
                            	    									current,
                            	    									"indexNames",
                            	    									lv_indexNames_9_0,
                            	    									"alpha.model.Alpha.IndexName");
                            	    								afterParserOrEnumRuleCall();
                            	    							

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop110;
                                }
                            } while (true);

                            otherlv_10=(Token)match(input,38,FOLLOW_2); 

                            					newLeafNode(otherlv_10, grammarAccess.getRectangularDomainAccess().getRightSquareBracketKeyword_0_4_4());
                            				

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalAlpha.g:8511:3: (otherlv_11= '[' ( (lv_lowerBounds_12_0= ruleAISLExpression ) ) otherlv_13= ':' ( (lv_upperBounds_14_0= ruleAISLExpression ) ) (otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) ) )* otherlv_19= ']' (otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']' )? )
                    {
                    // InternalAlpha.g:8511:3: (otherlv_11= '[' ( (lv_lowerBounds_12_0= ruleAISLExpression ) ) otherlv_13= ':' ( (lv_upperBounds_14_0= ruleAISLExpression ) ) (otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) ) )* otherlv_19= ']' (otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']' )? )
                    // InternalAlpha.g:8512:4: otherlv_11= '[' ( (lv_lowerBounds_12_0= ruleAISLExpression ) ) otherlv_13= ':' ( (lv_upperBounds_14_0= ruleAISLExpression ) ) (otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) ) )* otherlv_19= ']' (otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']' )?
                    {
                    otherlv_11=(Token)match(input,37,FOLLOW_33); 

                    				newLeafNode(otherlv_11, grammarAccess.getRectangularDomainAccess().getLeftSquareBracketKeyword_1_0());
                    			
                    // InternalAlpha.g:8516:4: ( (lv_lowerBounds_12_0= ruleAISLExpression ) )
                    // InternalAlpha.g:8517:5: (lv_lowerBounds_12_0= ruleAISLExpression )
                    {
                    // InternalAlpha.g:8517:5: (lv_lowerBounds_12_0= ruleAISLExpression )
                    // InternalAlpha.g:8518:6: lv_lowerBounds_12_0= ruleAISLExpression
                    {

                    						newCompositeNode(grammarAccess.getRectangularDomainAccess().getLowerBoundsAISLExpressionParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_lowerBounds_12_0=ruleAISLExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                    						}
                    						add(
                    							current,
                    							"lowerBounds",
                    							lv_lowerBounds_12_0,
                    							"alpha.model.Alpha.AISLExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_13=(Token)match(input,33,FOLLOW_33); 

                    				newLeafNode(otherlv_13, grammarAccess.getRectangularDomainAccess().getColonKeyword_1_2());
                    			
                    // InternalAlpha.g:8539:4: ( (lv_upperBounds_14_0= ruleAISLExpression ) )
                    // InternalAlpha.g:8540:5: (lv_upperBounds_14_0= ruleAISLExpression )
                    {
                    // InternalAlpha.g:8540:5: (lv_upperBounds_14_0= ruleAISLExpression )
                    // InternalAlpha.g:8541:6: lv_upperBounds_14_0= ruleAISLExpression
                    {

                    						newCompositeNode(grammarAccess.getRectangularDomainAccess().getUpperBoundsAISLExpressionParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_32);
                    lv_upperBounds_14_0=ruleAISLExpression();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                    						}
                    						add(
                    							current,
                    							"upperBounds",
                    							lv_upperBounds_14_0,
                    							"alpha.model.Alpha.AISLExpression");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalAlpha.g:8558:4: (otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) ) )*
                    loop112:
                    do {
                        int alt112=2;
                        int LA112_0 = input.LA(1);

                        if ( (LA112_0==24) ) {
                            alt112=1;
                        }


                        switch (alt112) {
                    	case 1 :
                    	    // InternalAlpha.g:8559:5: otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) )
                    	    {
                    	    otherlv_15=(Token)match(input,24,FOLLOW_33); 

                    	    					newLeafNode(otherlv_15, grammarAccess.getRectangularDomainAccess().getCommaKeyword_1_4_0());
                    	    				
                    	    // InternalAlpha.g:8563:5: ( (lv_lowerBounds_16_0= ruleAISLExpression ) )
                    	    // InternalAlpha.g:8564:6: (lv_lowerBounds_16_0= ruleAISLExpression )
                    	    {
                    	    // InternalAlpha.g:8564:6: (lv_lowerBounds_16_0= ruleAISLExpression )
                    	    // InternalAlpha.g:8565:7: lv_lowerBounds_16_0= ruleAISLExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getRectangularDomainAccess().getLowerBoundsAISLExpressionParserRuleCall_1_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_lowerBounds_16_0=ruleAISLExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"lowerBounds",
                    	    								lv_lowerBounds_16_0,
                    	    								"alpha.model.Alpha.AISLExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }

                    	    otherlv_17=(Token)match(input,33,FOLLOW_33); 

                    	    					newLeafNode(otherlv_17, grammarAccess.getRectangularDomainAccess().getColonKeyword_1_4_2());
                    	    				
                    	    // InternalAlpha.g:8586:5: ( (lv_upperBounds_18_0= ruleAISLExpression ) )
                    	    // InternalAlpha.g:8587:6: (lv_upperBounds_18_0= ruleAISLExpression )
                    	    {
                    	    // InternalAlpha.g:8587:6: (lv_upperBounds_18_0= ruleAISLExpression )
                    	    // InternalAlpha.g:8588:7: lv_upperBounds_18_0= ruleAISLExpression
                    	    {

                    	    							newCompositeNode(grammarAccess.getRectangularDomainAccess().getUpperBoundsAISLExpressionParserRuleCall_1_4_3_0());
                    	    						
                    	    pushFollow(FOLLOW_32);
                    	    lv_upperBounds_18_0=ruleAISLExpression();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"upperBounds",
                    	    								lv_upperBounds_18_0,
                    	    								"alpha.model.Alpha.AISLExpression");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop112;
                        }
                    } while (true);

                    otherlv_19=(Token)match(input,38,FOLLOW_81); 

                    				newLeafNode(otherlv_19, grammarAccess.getRectangularDomainAccess().getRightSquareBracketKeyword_1_5());
                    			
                    // InternalAlpha.g:8610:4: (otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']' )?
                    int alt114=2;
                    int LA114_0 = input.LA(1);

                    if ( (LA114_0==78) ) {
                        alt114=1;
                    }
                    switch (alt114) {
                        case 1 :
                            // InternalAlpha.g:8611:5: otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']'
                            {
                            otherlv_20=(Token)match(input,78,FOLLOW_36); 

                            					newLeafNode(otherlv_20, grammarAccess.getRectangularDomainAccess().getAsKeyword_1_6_0());
                            				
                            otherlv_21=(Token)match(input,37,FOLLOW_5); 

                            					newLeafNode(otherlv_21, grammarAccess.getRectangularDomainAccess().getLeftSquareBracketKeyword_1_6_1());
                            				
                            // InternalAlpha.g:8619:5: ( (lv_indexNames_22_0= ruleIndexName ) )
                            // InternalAlpha.g:8620:6: (lv_indexNames_22_0= ruleIndexName )
                            {
                            // InternalAlpha.g:8620:6: (lv_indexNames_22_0= ruleIndexName )
                            // InternalAlpha.g:8621:7: lv_indexNames_22_0= ruleIndexName
                            {

                            							newCompositeNode(grammarAccess.getRectangularDomainAccess().getIndexNamesIndexNameParserRuleCall_1_6_2_0());
                            						
                            pushFollow(FOLLOW_32);
                            lv_indexNames_22_0=ruleIndexName();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                            							}
                            							add(
                            								current,
                            								"indexNames",
                            								lv_indexNames_22_0,
                            								"alpha.model.Alpha.IndexName");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            // InternalAlpha.g:8638:5: (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )*
                            loop113:
                            do {
                                int alt113=2;
                                int LA113_0 = input.LA(1);

                                if ( (LA113_0==24) ) {
                                    alt113=1;
                                }


                                switch (alt113) {
                            	case 1 :
                            	    // InternalAlpha.g:8639:6: otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) )
                            	    {
                            	    otherlv_23=(Token)match(input,24,FOLLOW_5); 

                            	    						newLeafNode(otherlv_23, grammarAccess.getRectangularDomainAccess().getCommaKeyword_1_6_3_0());
                            	    					
                            	    // InternalAlpha.g:8643:6: ( (lv_indexNames_24_0= ruleIndexName ) )
                            	    // InternalAlpha.g:8644:7: (lv_indexNames_24_0= ruleIndexName )
                            	    {
                            	    // InternalAlpha.g:8644:7: (lv_indexNames_24_0= ruleIndexName )
                            	    // InternalAlpha.g:8645:8: lv_indexNames_24_0= ruleIndexName
                            	    {

                            	    								newCompositeNode(grammarAccess.getRectangularDomainAccess().getIndexNamesIndexNameParserRuleCall_1_6_3_1_0());
                            	    							
                            	    pushFollow(FOLLOW_32);
                            	    lv_indexNames_24_0=ruleIndexName();

                            	    state._fsp--;


                            	    								if (current==null) {
                            	    									current = createModelElementForParent(grammarAccess.getRectangularDomainRule());
                            	    								}
                            	    								add(
                            	    									current,
                            	    									"indexNames",
                            	    									lv_indexNames_24_0,
                            	    									"alpha.model.Alpha.IndexName");
                            	    								afterParserOrEnumRuleCall();
                            	    							

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop113;
                                }
                            } while (true);

                            otherlv_25=(Token)match(input,38,FOLLOW_2); 

                            					newLeafNode(otherlv_25, grammarAccess.getRectangularDomainAccess().getRightSquareBracketKeyword_1_6_4());
                            				

                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRectangularDomain"


    // $ANTLR start "entryRuleDefinedObject"
    // InternalAlpha.g:8673:1: entryRuleDefinedObject returns [EObject current=null] : iv_ruleDefinedObject= ruleDefinedObject EOF ;
    public final EObject entryRuleDefinedObject() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinedObject = null;


        try {
            // InternalAlpha.g:8673:54: (iv_ruleDefinedObject= ruleDefinedObject EOF )
            // InternalAlpha.g:8674:2: iv_ruleDefinedObject= ruleDefinedObject EOF
            {
             newCompositeNode(grammarAccess.getDefinedObjectRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDefinedObject=ruleDefinedObject();

            state._fsp--;

             current =iv_ruleDefinedObject; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDefinedObject"


    // $ANTLR start "ruleDefinedObject"
    // InternalAlpha.g:8680:1: ruleDefinedObject returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleDefinedObject() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalAlpha.g:8686:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalAlpha.g:8687:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalAlpha.g:8687:2: ( (otherlv_0= RULE_ID ) )
            // InternalAlpha.g:8688:3: (otherlv_0= RULE_ID )
            {
            // InternalAlpha.g:8688:3: (otherlv_0= RULE_ID )
            // InternalAlpha.g:8689:4: otherlv_0= RULE_ID
            {

            				if (current==null) {
            					current = createModelElement(grammarAccess.getDefinedObjectRule());
            				}
            			
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(otherlv_0, grammarAccess.getDefinedObjectAccess().getObjectPolyhedralObjectCrossReference_0());
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDefinedObject"


    // $ANTLR start "entryRuleAUnaryCalcOp"
    // InternalAlpha.g:8703:1: entryRuleAUnaryCalcOp returns [String current=null] : iv_ruleAUnaryCalcOp= ruleAUnaryCalcOp EOF ;
    public final String entryRuleAUnaryCalcOp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAUnaryCalcOp = null;


        try {
            // InternalAlpha.g:8703:52: (iv_ruleAUnaryCalcOp= ruleAUnaryCalcOp EOF )
            // InternalAlpha.g:8704:2: iv_ruleAUnaryCalcOp= ruleAUnaryCalcOp EOF
            {
             newCompositeNode(grammarAccess.getAUnaryCalcOpRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAUnaryCalcOp=ruleAUnaryCalcOp();

            state._fsp--;

             current =iv_ruleAUnaryCalcOp.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAUnaryCalcOp"


    // $ANTLR start "ruleAUnaryCalcOp"
    // InternalAlpha.g:8710:1: ruleAUnaryCalcOp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'domain' | kw= 'range' | kw= 'complement' | kw= 'affine-hull' | kw= 'poly-hull' | kw= 'reverse' ) ;
    public final AntlrDatatypeRuleToken ruleAUnaryCalcOp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:8716:2: ( (kw= 'domain' | kw= 'range' | kw= 'complement' | kw= 'affine-hull' | kw= 'poly-hull' | kw= 'reverse' ) )
            // InternalAlpha.g:8717:2: (kw= 'domain' | kw= 'range' | kw= 'complement' | kw= 'affine-hull' | kw= 'poly-hull' | kw= 'reverse' )
            {
            // InternalAlpha.g:8717:2: (kw= 'domain' | kw= 'range' | kw= 'complement' | kw= 'affine-hull' | kw= 'poly-hull' | kw= 'reverse' )
            int alt116=6;
            switch ( input.LA(1) ) {
            case 79:
                {
                alt116=1;
                }
                break;
            case 80:
                {
                alt116=2;
                }
                break;
            case 81:
                {
                alt116=3;
                }
                break;
            case 82:
                {
                alt116=4;
                }
                break;
            case 83:
                {
                alt116=5;
                }
                break;
            case 84:
                {
                alt116=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }

            switch (alt116) {
                case 1 :
                    // InternalAlpha.g:8718:3: kw= 'domain'
                    {
                    kw=(Token)match(input,79,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAUnaryCalcOpAccess().getDomainKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:8724:3: kw= 'range'
                    {
                    kw=(Token)match(input,80,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAUnaryCalcOpAccess().getRangeKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:8730:3: kw= 'complement'
                    {
                    kw=(Token)match(input,81,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAUnaryCalcOpAccess().getComplementKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalAlpha.g:8736:3: kw= 'affine-hull'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAUnaryCalcOpAccess().getAffineHullKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalAlpha.g:8742:3: kw= 'poly-hull'
                    {
                    kw=(Token)match(input,83,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAUnaryCalcOpAccess().getPolyHullKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalAlpha.g:8748:3: kw= 'reverse'
                    {
                    kw=(Token)match(input,84,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAUnaryCalcOpAccess().getReverseKeyword_5());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAUnaryCalcOp"


    // $ANTLR start "entryRuleABinaryCalcOp"
    // InternalAlpha.g:8757:1: entryRuleABinaryCalcOp returns [String current=null] : iv_ruleABinaryCalcOp= ruleABinaryCalcOp EOF ;
    public final String entryRuleABinaryCalcOp() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleABinaryCalcOp = null;


        try {
            // InternalAlpha.g:8757:53: (iv_ruleABinaryCalcOp= ruleABinaryCalcOp EOF )
            // InternalAlpha.g:8758:2: iv_ruleABinaryCalcOp= ruleABinaryCalcOp EOF
            {
             newCompositeNode(grammarAccess.getABinaryCalcOpRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleABinaryCalcOp=ruleABinaryCalcOp();

            state._fsp--;

             current =iv_ruleABinaryCalcOp.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleABinaryCalcOp"


    // $ANTLR start "ruleABinaryCalcOp"
    // InternalAlpha.g:8764:1: ruleABinaryCalcOp returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'cross' | kw= '+' | kw= '-' | kw= '*' | kw= '@' | kw= 'intersectRange' | kw= 'subtractRange' ) ;
    public final AntlrDatatypeRuleToken ruleABinaryCalcOp() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalAlpha.g:8770:2: ( (kw= 'cross' | kw= '+' | kw= '-' | kw= '*' | kw= '@' | kw= 'intersectRange' | kw= 'subtractRange' ) )
            // InternalAlpha.g:8771:2: (kw= 'cross' | kw= '+' | kw= '-' | kw= '*' | kw= '@' | kw= 'intersectRange' | kw= 'subtractRange' )
            {
            // InternalAlpha.g:8771:2: (kw= 'cross' | kw= '+' | kw= '-' | kw= '*' | kw= '@' | kw= 'intersectRange' | kw= 'subtractRange' )
            int alt117=7;
            switch ( input.LA(1) ) {
            case 85:
                {
                alt117=1;
                }
                break;
            case 43:
                {
                alt117=2;
                }
                break;
            case 44:
                {
                alt117=3;
                }
                break;
            case 45:
                {
                alt117=4;
                }
                break;
            case 63:
                {
                alt117=5;
                }
                break;
            case 86:
                {
                alt117=6;
                }
                break;
            case 87:
                {
                alt117=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 117, 0, input);

                throw nvae;
            }

            switch (alt117) {
                case 1 :
                    // InternalAlpha.g:8772:3: kw= 'cross'
                    {
                    kw=(Token)match(input,85,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getABinaryCalcOpAccess().getCrossKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalAlpha.g:8778:3: kw= '+'
                    {
                    kw=(Token)match(input,43,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getABinaryCalcOpAccess().getPlusSignKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalAlpha.g:8784:3: kw= '-'
                    {
                    kw=(Token)match(input,44,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getABinaryCalcOpAccess().getHyphenMinusKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalAlpha.g:8790:3: kw= '*'
                    {
                    kw=(Token)match(input,45,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getABinaryCalcOpAccess().getAsteriskKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalAlpha.g:8796:3: kw= '@'
                    {
                    kw=(Token)match(input,63,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getABinaryCalcOpAccess().getCommercialAtKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalAlpha.g:8802:3: kw= 'intersectRange'
                    {
                    kw=(Token)match(input,86,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getABinaryCalcOpAccess().getIntersectRangeKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalAlpha.g:8808:3: kw= 'subtractRange'
                    {
                    kw=(Token)match(input,87,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getABinaryCalcOpAccess().getSubtractRangeKeyword_6());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleABinaryCalcOp"


    // $ANTLR start "entryRuleFLOAT"
    // InternalAlpha.g:8817:1: entryRuleFLOAT returns [String current=null] : iv_ruleFLOAT= ruleFLOAT EOF ;
    public final String entryRuleFLOAT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFLOAT = null;


        try {
            // InternalAlpha.g:8817:45: (iv_ruleFLOAT= ruleFLOAT EOF )
            // InternalAlpha.g:8818:2: iv_ruleFLOAT= ruleFLOAT EOF
            {
             newCompositeNode(grammarAccess.getFLOATRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFLOAT=ruleFLOAT();

            state._fsp--;

             current =iv_ruleFLOAT.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFLOAT"


    // $ANTLR start "ruleFLOAT"
    // InternalAlpha.g:8824:1: ruleFLOAT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT kw= '.' this_INT_3= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleFLOAT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;
        Token this_INT_3=null;


        	enterRule();

        try {
            // InternalAlpha.g:8830:2: ( ( (kw= '-' )? this_INT_1= RULE_INT kw= '.' this_INT_3= RULE_INT ) )
            // InternalAlpha.g:8831:2: ( (kw= '-' )? this_INT_1= RULE_INT kw= '.' this_INT_3= RULE_INT )
            {
            // InternalAlpha.g:8831:2: ( (kw= '-' )? this_INT_1= RULE_INT kw= '.' this_INT_3= RULE_INT )
            // InternalAlpha.g:8832:3: (kw= '-' )? this_INT_1= RULE_INT kw= '.' this_INT_3= RULE_INT
            {
            // InternalAlpha.g:8832:3: (kw= '-' )?
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==44) ) {
                alt118=1;
            }
            switch (alt118) {
                case 1 :
                    // InternalAlpha.g:8833:4: kw= '-'
                    {
                    kw=(Token)match(input,44,FOLLOW_7); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getFLOATAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_82); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getFLOATAccess().getINTTerminalRuleCall_1());
            		
            kw=(Token)match(input,29,FOLLOW_7); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getFLOATAccess().getFullStopKeyword_2());
            		
            this_INT_3=(Token)match(input,RULE_INT,FOLLOW_2); 

            			current.merge(this_INT_3);
            		

            			newLeafNode(this_INT_3, grammarAccess.getFLOATAccess().getINTTerminalRuleCall_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFLOAT"


    // $ANTLR start "entryRuleSINT"
    // InternalAlpha.g:8862:1: entryRuleSINT returns [String current=null] : iv_ruleSINT= ruleSINT EOF ;
    public final String entryRuleSINT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSINT = null;


        try {
            // InternalAlpha.g:8862:44: (iv_ruleSINT= ruleSINT EOF )
            // InternalAlpha.g:8863:2: iv_ruleSINT= ruleSINT EOF
            {
             newCompositeNode(grammarAccess.getSINTRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSINT=ruleSINT();

            state._fsp--;

             current =iv_ruleSINT.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSINT"


    // $ANTLR start "ruleSINT"
    // InternalAlpha.g:8869:1: ruleSINT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_INT_1= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleSINT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_1=null;


        	enterRule();

        try {
            // InternalAlpha.g:8875:2: ( ( (kw= '-' )? this_INT_1= RULE_INT ) )
            // InternalAlpha.g:8876:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            {
            // InternalAlpha.g:8876:2: ( (kw= '-' )? this_INT_1= RULE_INT )
            // InternalAlpha.g:8877:3: (kw= '-' )? this_INT_1= RULE_INT
            {
            // InternalAlpha.g:8877:3: (kw= '-' )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==44) ) {
                alt119=1;
            }
            switch (alt119) {
                case 1 :
                    // InternalAlpha.g:8878:4: kw= '-'
                    {
                    kw=(Token)match(input,44,FOLLOW_7); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getSINTAccess().getHyphenMinusKeyword_0());
                    			

                    }
                    break;

            }

            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_2); 

            			current.merge(this_INT_1);
            		

            			newLeafNode(this_INT_1, grammarAccess.getSINTAccess().getINTTerminalRuleCall_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSINT"

    // Delegated rules


    protected DFA10 dfa10 = new DFA10(this);
    protected DFA14 dfa14 = new DFA14(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA73 dfa73 = new DFA73(this);
    protected DFA74 dfa74 = new DFA74(this);
    protected DFA75 dfa75 = new DFA75(this);
    protected DFA77 dfa77 = new DFA77(this);
    protected DFA108 dfa108 = new DFA108(this);
    protected DFA115 dfa115 = new DFA115(this);
    static final String dfa_1s = "\12\uffff";
    static final String dfa_2s = "\1\4\1\uffff\1\30\1\uffff\1\4\1\uffff\1\30\1\uffff\1\4\1\uffff";
    static final String dfa_3s = "\1\43\1\uffff\1\41\1\uffff\1\43\1\uffff\1\41\1\uffff\1\43\1\uffff";
    static final String dfa_4s = "\1\uffff\1\5\1\uffff\1\3\1\uffff\1\1\1\uffff\1\4\1\uffff\1\2";
    static final String dfa_5s = "\12\uffff}>";
    static final String[] dfa_6s = {
            "\1\2\24\uffff\3\1\1\uffff\4\1\2\uffff\1\3",
            "",
            "\1\4\10\uffff\1\5",
            "",
            "\1\6\36\uffff\1\7",
            "",
            "\1\10\10\uffff\1\11",
            "",
            "\1\6\36\uffff\1\7",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "()+ loopback of 549:4: ( ( (lv_inputs_6_0= ruleVariable ) ) | ( ( (lv_inputs_7_0= ruleVariableNameOnly ) ) otherlv_8= ',' ( ( (lv_inputs_9_0= ruleVariableNameOnly ) ) otherlv_10= ',' )* ( (lv_inputs_11_0= ruleVariable ) ) ) | ( (lv_inputs_12_0= ruleFuzzyVariable ) ) | ( ( (lv_inputs_13_0= ruleFuzzyVariableNameOnly ) ) otherlv_14= ',' ( ( (lv_inputs_15_0= ruleFuzzyVariableNameOnly ) ) otherlv_16= ',' )* ( (lv_inputs_17_0= ruleFuzzyVariable ) ) ) )+";
        }
    }
    static final String dfa_7s = "\1\4\1\uffff\1\30\2\uffff\1\4\1\30\1\uffff\1\4\1\uffff";
    static final String dfa_8s = "\1\43\1\uffff\1\41\2\uffff\1\43\1\41\1\uffff\1\43\1\uffff";
    static final String dfa_9s = "\1\uffff\1\5\1\uffff\1\3\1\1\2\uffff\1\4\1\uffff\1\2";
    static final String[] dfa_10s = {
            "\1\2\25\uffff\2\1\1\uffff\4\1\2\uffff\1\3",
            "",
            "\1\5\10\uffff\1\4",
            "",
            "",
            "\1\6\36\uffff\1\7",
            "\1\10\10\uffff\1\11",
            "",
            "\1\6\36\uffff\1\7",
            ""
    };
    static final char[] dfa_7 = DFA.unpackEncodedStringToUnsignedChars(dfa_7s);
    static final char[] dfa_8 = DFA.unpackEncodedStringToUnsignedChars(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final short[][] dfa_10 = unpackEncodedStringArray(dfa_10s);

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_7;
            this.max = dfa_8;
            this.accept = dfa_9;
            this.special = dfa_5;
            this.transition = dfa_10;
        }
        public String getDescription() {
            return "()+ loopback of 736:4: ( ( (lv_outputs_19_0= ruleVariable ) ) | ( ( (lv_outputs_20_0= ruleVariableNameOnly ) ) otherlv_21= ',' ( ( (lv_outputs_22_0= ruleVariableNameOnly ) ) otherlv_23= ',' )* ( (lv_outputs_24_0= ruleVariable ) ) ) | ( (lv_outputs_25_0= ruleFuzzyVariable ) ) | ( ( (lv_outputs_26_0= ruleFuzzyVariableNameOnly ) ) otherlv_27= ',' ( ( (lv_outputs_28_0= ruleFuzzyVariableNameOnly ) ) otherlv_29= ',' )* ( (lv_outputs_30_0= ruleFuzzyVariable ) ) ) )+";
        }
    }
    static final String[] dfa_11s = {
            "\1\2\26\uffff\1\1\1\uffff\4\1\2\uffff\1\3",
            "",
            "\1\5\10\uffff\1\4",
            "",
            "",
            "\1\6\36\uffff\1\7",
            "\1\10\10\uffff\1\11",
            "",
            "\1\6\36\uffff\1\7",
            ""
    };
    static final short[][] dfa_11 = unpackEncodedStringArray(dfa_11s);

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_7;
            this.max = dfa_8;
            this.accept = dfa_9;
            this.special = dfa_5;
            this.transition = dfa_11;
        }
        public String getDescription() {
            return "()+ loopback of 923:4: ( ( (lv_locals_32_0= ruleVariable ) ) | ( ( (lv_locals_33_0= ruleVariableNameOnly ) ) otherlv_34= ',' ( ( (lv_locals_35_0= ruleVariableNameOnly ) ) otherlv_36= ',' )* ( (lv_locals_37_0= ruleVariable ) ) ) | ( (lv_locals_38_0= ruleFuzzyVariable ) ) | ( ( (lv_locals_39_0= ruleFuzzyVariableNameOnly ) ) otherlv_40= ',' ( ( (lv_locals_41_0= ruleFuzzyVariableNameOnly ) ) otherlv_42= ',' )* ( (lv_locals_43_0= ruleFuzzyVariable ) ) ) )+";
        }
    }
    static final String dfa_12s = "\31\uffff";
    static final String dfa_13s = "\3\uffff\1\21\1\uffff\1\21\1\23\21\uffff\1\21";
    static final String dfa_14s = "\2\4\1\uffff\1\15\1\5\2\15\1\17\5\uffff\1\15\4\uffff\1\5\4\uffff\1\4\1\15";
    static final String dfa_15s = "\1\113\1\115\1\uffff\1\114\1\5\2\114\1\45\5\uffff\1\114\4\uffff\1\5\4\uffff\1\45\1\114";
    static final String dfa_16s = "\2\uffff\1\2\5\uffff\1\11\1\12\1\13\1\14\1\16\1\uffff\1\5\1\1\1\6\1\3\1\uffff\1\4\1\15\1\7\1\10\2\uffff";
    static final String dfa_17s = "\31\uffff}>";
    static final String[] dfa_18s = {
            "\1\6\1\5\1\uffff\1\3\7\uffff\1\1\27\uffff\1\10\3\uffff\1\13\1\4\1\13\3\uffff\2\13\13\uffff\1\2\1\uffff\1\7\2\11\1\12\1\14\1\uffff\6\13",
            "\1\15\1\17\1\uffff\1\17\7\uffff\1\17\3\uffff\1\17\20\uffff\1\16\1\20\1\uffff\1\17\2\uffff\4\17\3\uffff\2\17\10\uffff\1\17\1\uffff\2\17\1\uffff\5\17\1\uffff\6\17\1\uffff\1\17",
            "",
            "\1\21\2\uffff\1\21\7\uffff\1\21\6\uffff\1\21\2\uffff\1\21\2\uffff\1\16\5\uffff\4\21\2\uffff\2\21\2\uffff\4\21\3\uffff\1\21\11\uffff\2\21\3\uffff\2\21",
            "\1\5",
            "\1\21\2\uffff\1\21\7\uffff\1\21\4\uffff\1\22\1\uffff\1\21\2\uffff\1\21\2\uffff\1\16\5\uffff\4\21\2\uffff\2\21\2\uffff\4\21\3\uffff\1\21\11\uffff\2\21\3\uffff\2\21",
            "\1\23\1\uffff\1\24\1\23\7\uffff\1\23\4\uffff\1\24\1\uffff\1\23\2\uffff\1\23\2\uffff\1\16\1\uffff\1\20\3\uffff\4\23\2\uffff\2\23\2\uffff\4\23\3\uffff\1\23\11\uffff\2\23\3\uffff\2\23",
            "\1\27\3\uffff\1\26\21\uffff\1\25",
            "",
            "",
            "",
            "",
            "",
            "\1\17\1\uffff\2\17\7\uffff\1\16\4\uffff\1\17\6\uffff\1\16\1\17\1\uffff\1\17\3\uffff\4\17\2\uffff\2\17\2\uffff\4\17\15\uffff\2\17\3\uffff\2\17",
            "",
            "",
            "",
            "",
            "\1\30",
            "",
            "",
            "",
            "",
            "\1\25\37\uffff\1\25\1\10",
            "\1\21\2\uffff\1\21\7\uffff\1\21\6\uffff\1\21\2\uffff\1\21\2\uffff\1\16\5\uffff\4\21\2\uffff\2\21\2\uffff\4\21\3\uffff\1\21\11\uffff\2\21\3\uffff\2\21"
    };

    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final char[] dfa_14 = DFA.unpackEncodedStringToUnsignedChars(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[][] dfa_18 = unpackEncodedStringArray(dfa_18s);

    class DFA73 extends DFA {

        public DFA73(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 73;
            this.eot = dfa_12;
            this.eof = dfa_13;
            this.min = dfa_14;
            this.max = dfa_15;
            this.accept = dfa_16;
            this.special = dfa_17;
            this.transition = dfa_18;
        }
        public String getDescription() {
            return "4684:2: ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_ConstantExpression_4= ruleConstantExpression | this_VariableExpression_5= ruleVariableExpression | this_DependenceExpression_6= ruleDependenceExpression | this_FuzzyDependenceExpression_7= ruleFuzzyDependenceExpression | this_IndexExpression_8= ruleIndexExpression | this_PolynomialIndexExpression_9= rulePolynomialIndexExpression | this_FuzzyIndexExpression_10= ruleFuzzyIndexExpression | this_Reductions_11= ruleReductions | this_ConvolutionExpression_12= ruleConvolutionExpression | this_MultiArgExpression_13= ruleMultiArgExpression | this_ExternalMultiArgExpression_14= ruleExternalMultiArgExpression | this_SelectExpression_15= ruleSelectExpression )";
        }
    }
    static final String dfa_19s = "\17\uffff";
    static final String dfa_20s = "\3\uffff\1\12\13\uffff";
    static final String dfa_21s = "\1\4\2\uffff\1\15\1\17\10\uffff\1\4\1\uffff";
    static final String dfa_22s = "\1\113\2\uffff\1\114\1\45\10\uffff\1\45\1\uffff";
    static final String dfa_23s = "\1\uffff\1\1\1\2\2\uffff\1\6\1\7\1\10\1\11\1\13\1\3\1\12\1\4\1\uffff\1\5";
    static final String dfa_24s = "\17\uffff}>";
    static final String[] dfa_25s = {
            "\1\3\12\uffff\1\1\27\uffff\1\5\3\uffff\1\10\1\uffff\1\10\3\uffff\2\10\13\uffff\1\2\1\uffff\1\4\2\6\1\7\1\11\1\uffff\6\10",
            "",
            "",
            "\1\12\1\uffff\1\13\1\12\7\uffff\1\12\4\uffff\1\13\1\uffff\1\12\2\uffff\1\12\10\uffff\4\12\2\uffff\2\12\2\uffff\4\12\3\uffff\1\12\11\uffff\2\12\3\uffff\2\12",
            "\1\15\3\uffff\1\16\21\uffff\1\14",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\14\37\uffff\1\14\1\5",
            ""
    };

    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final char[] dfa_21 = DFA.unpackEncodedStringToUnsignedChars(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[][] dfa_25 = unpackEncodedStringArray(dfa_25s);

    class DFA74 extends DFA {

        public DFA74(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 74;
            this.eot = dfa_19;
            this.eof = dfa_20;
            this.min = dfa_21;
            this.max = dfa_22;
            this.accept = dfa_23;
            this.special = dfa_24;
            this.transition = dfa_25;
        }
        public String getDescription() {
            return "4838:2: ( (otherlv_0= '(' this_AlphaExpression_1= ruleAlphaExpression otherlv_2= ')' ) | this_CaseExpression_3= ruleCaseExpression | this_VariableExpression_4= ruleVariableExpression | this_IndexExpression_5= ruleIndexExpression | this_PolynomialIndexExpression_6= rulePolynomialIndexExpression | this_FuzzyIndexExpression_7= ruleFuzzyIndexExpression | this_Reductions_8= ruleReductions | this_ConvolutionExpression_9= ruleConvolutionExpression | this_MultiArgExpression_10= ruleMultiArgExpression | this_ExternalMultiArgExpression_11= ruleExternalMultiArgExpression | this_SelectExpression_12= ruleSelectExpression )";
        }
    }
    static final String dfa_26s = "\57\uffff";
    static final String dfa_27s = "\1\101\2\17\2\4\26\30\1\17\1\4\2\17\1\4\1\17\1\4\1\uffff\1\30\1\4\1\uffff\1\4\1\uffff\1\30\1\4\5\uffff";
    static final String dfa_28s = "\1\102\2\17\2\113\12\30\1\35\12\30\1\35\1\45\1\4\2\45\1\4\2\45\1\uffff\1\35\1\45\1\uffff\1\45\1\uffff\1\35\1\45\5\uffff";
    static final String dfa_29s = "\42\uffff\1\1\2\uffff\1\3\1\uffff\1\2\2\uffff\1\4\1\5\1\7\1\6\1\10";
    static final String dfa_30s = "\57\uffff}>";
    static final String[] dfa_31s = {
            "\1\1\1\2",
            "\1\3",
            "\1\4",
            "\1\17\46\uffff\1\15\1\uffff\1\16\3\uffff\1\12\1\13\23\uffff\1\5\1\6\1\7\1\10\1\11\1\14",
            "\1\32\46\uffff\1\30\1\uffff\1\31\3\uffff\1\25\1\26\23\uffff\1\20\1\21\1\22\1\23\1\24\1\27",
            "\1\33",
            "\1\33",
            "\1\33",
            "\1\33",
            "\1\33",
            "\1\33",
            "\1\33",
            "\1\33",
            "\1\33",
            "\1\33",
            "\1\35\4\uffff\1\34",
            "\1\36",
            "\1\36",
            "\1\36",
            "\1\36",
            "\1\36",
            "\1\36",
            "\1\36",
            "\1\36",
            "\1\36",
            "\1\36",
            "\1\40\4\uffff\1\37",
            "\1\41\25\uffff\1\42",
            "\1\43",
            "\1\44\25\uffff\1\45",
            "\1\46\25\uffff\1\47",
            "\1\50",
            "\1\51\25\uffff\1\52",
            "\1\42\37\uffff\1\42\1\53",
            "",
            "\1\35\4\uffff\1\34",
            "\1\45\37\uffff\1\45\1\54",
            "",
            "\1\47\37\uffff\1\47\1\55",
            "",
            "\1\40\4\uffff\1\37",
            "\1\52\37\uffff\1\52\1\56",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final char[] dfa_27 = DFA.unpackEncodedStringToUnsignedChars(dfa_27s);
    static final char[] dfa_28 = DFA.unpackEncodedStringToUnsignedChars(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final short[][] dfa_31 = unpackEncodedStringArray(dfa_31s);

    class DFA75 extends DFA {

        public DFA75(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 75;
            this.eot = dfa_26;
            this.eof = dfa_26;
            this.min = dfa_27;
            this.max = dfa_28;
            this.accept = dfa_29;
            this.special = dfa_30;
            this.transition = dfa_31;
        }
        public String getDescription() {
            return "4965:2: (this_ReduceExpression_0= ruleReduceExpression | this_ArgReduceExpression_1= ruleArgReduceExpression | this_ExternalReduceExpression_2= ruleExternalReduceExpression | this_ExternalArgReduceExpression_3= ruleExternalArgReduceExpression | this_FuzzyReduceExpression_4= ruleFuzzyReduceExpression | this_FuzzyArgReduceExpression_5= ruleFuzzyArgReduceExpression | this_ExternalFuzzyReduceExpression_6= ruleExternalFuzzyReduceExpression | this_ExternalFuzzyArgReduceExpression_7= ruleExternalFuzzyArgReduceExpression )";
        }
    }
    static final String dfa_32s = "\37\uffff";
    static final String dfa_33s = "\1\23\1\4\2\uffff\16\4\1\24\14\4";
    static final String dfa_34s = "\1\52\1\124\2\uffff\16\60\1\127\14\60";
    static final String dfa_35s = "\2\uffff\1\1\1\2\33\uffff";
    static final String dfa_36s = "\37\uffff}>";
    static final String[] dfa_37s = {
            "\1\1\26\uffff\1\2",
            "\1\3\12\uffff\1\3\3\uffff\1\3\15\uffff\1\2\3\uffff\1\4\4\uffff\1\3\44\uffff\6\3",
            "",
            "",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\25\uffff\1\2\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\5\1\6\1\20\6\uffff\1\7\1\uffff\1\16\1\17\7\uffff\1\21\10\uffff\1\3\4\uffff\1\22\4\uffff\1\10\1\11\1\12\1\13\1\14\1\15",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\32\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\3\14\uffff\1\2\11\uffff\3\3\21\uffff\1\3\16\uffff\1\3\6\uffff\3\3",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33",
            "\1\23\1\24\1\36\6\uffff\1\25\1\uffff\1\34\1\35\7\uffff\1\21\15\uffff\1\22\4\uffff\1\26\1\27\1\30\1\31\1\32\1\33"
    };

    static final short[] dfa_32 = DFA.unpackEncodedString(dfa_32s);
    static final char[] dfa_33 = DFA.unpackEncodedStringToUnsignedChars(dfa_33s);
    static final char[] dfa_34 = DFA.unpackEncodedStringToUnsignedChars(dfa_34s);
    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final short[] dfa_36 = DFA.unpackEncodedString(dfa_36s);
    static final short[][] dfa_37 = unpackEncodedStringArray(dfa_37s);

    class DFA77 extends DFA {

        public DFA77(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 77;
            this.eot = dfa_32;
            this.eof = dfa_32;
            this.min = dfa_33;
            this.max = dfa_34;
            this.accept = dfa_35;
            this.special = dfa_36;
            this.transition = dfa_37;
        }
        public String getDescription() {
            return "5143:2: ( ( ( ( (lv_domainExpr_0_1= ruleJNIDomain | lv_domainExpr_0_2= ruleJNIDomainInArrayNotation ) ) ) otherlv_1= ':' ( (lv_expr_2_0= ruleAlphaExpression ) ) ) | (otherlv_3= '{' ( (lv_domainExpr_4_0= ruleCalculatorExpression ) ) otherlv_5= '}' otherlv_6= ':' ( (lv_expr_7_0= ruleAlphaExpression ) ) ) )";
        }
    }
    static final String dfa_38s = "\46\uffff";
    static final String dfa_39s = "\2\4\1\uffff\1\4\3\uffff\1\4\1\20\2\uffff\14\4\1\41\1\4\1\uffff\14\4";
    static final String dfa_40s = "\1\52\1\45\1\uffff\1\124\3\uffff\1\60\1\127\2\uffff\14\60\1\44\1\60\1\uffff\14\60";
    static final String dfa_41s = "\2\uffff\1\1\1\uffff\1\4\1\5\1\6\2\uffff\1\3\1\7\16\uffff\1\2\14\uffff";
    static final String dfa_42s = "\46\uffff}>";
    static final String[] dfa_43s = {
            "\1\4\12\uffff\1\3\3\uffff\1\1\21\uffff\1\5\4\uffff\1\2",
            "\1\6\40\uffff\1\7",
            "",
            "\1\10\12\uffff\1\12\3\uffff\1\12\20\uffff\1\11\1\12\4\uffff\1\12\44\uffff\6\12",
            "",
            "",
            "",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\25\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\12\7\uffff\1\11\13\uffff\1\11\6\uffff\3\12\21\uffff\1\12\25\uffff\3\12",
            "",
            "",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\13\1\14\1\26\6\uffff\1\15\1\uffff\1\24\1\25\7\uffff\1\30\15\uffff\1\27\4\uffff\1\16\1\17\1\20\1\21\1\22\1\23",
            "\1\2\2\uffff\1\31",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\32\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42",
            "\1\32\1\33\1\45\6\uffff\1\34\1\uffff\1\43\1\44\7\uffff\1\30\15\uffff\1\27\4\uffff\1\35\1\36\1\37\1\40\1\41\1\42"
    };

    static final short[] dfa_38 = DFA.unpackEncodedString(dfa_38s);
    static final char[] dfa_39 = DFA.unpackEncodedStringToUnsignedChars(dfa_39s);
    static final char[] dfa_40 = DFA.unpackEncodedStringToUnsignedChars(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[] dfa_42 = DFA.unpackEncodedString(dfa_42s);
    static final short[][] dfa_43 = unpackEncodedStringArray(dfa_43s);

    class DFA108 extends DFA {

        public DFA108(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 108;
            this.eot = dfa_38;
            this.eof = dfa_38;
            this.min = dfa_39;
            this.max = dfa_40;
            this.accept = dfa_41;
            this.special = dfa_42;
            this.transition = dfa_43;
        }
        public String getDescription() {
            return "8209:2: (this_JNIDomain_0= ruleJNIDomain | this_JNIRelation_1= ruleJNIRelation | this_JNIFunction_2= ruleJNIFunction | this_DefinedObject_3= ruleDefinedObject | this_RectangularDomain_4= ruleRectangularDomain | this_VariableDomain_5= ruleVariableDomain | (otherlv_6= '(' this_CalculatorExpression_7= ruleCalculatorExpression otherlv_8= ')' ) )";
        }
    }
    static final String dfa_44s = "\20\uffff";
    static final String dfa_45s = "\1\45\15\4\2\uffff";
    static final String dfa_46s = "\1\45\15\60\2\uffff";
    static final String dfa_47s = "\16\uffff\1\1\1\2";
    static final String dfa_48s = "\20\uffff}>";
    static final String[] dfa_49s = {
            "\1\1",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\32\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "\1\2\1\3\1\15\6\uffff\1\4\1\uffff\1\13\1\14\7\uffff\1\16\10\uffff\1\17\4\uffff\1\16\4\uffff\1\5\1\6\1\7\1\10\1\11\1\12",
            "",
            ""
    };

    static final short[] dfa_44 = DFA.unpackEncodedString(dfa_44s);
    static final char[] dfa_45 = DFA.unpackEncodedStringToUnsignedChars(dfa_45s);
    static final char[] dfa_46 = DFA.unpackEncodedStringToUnsignedChars(dfa_46s);
    static final short[] dfa_47 = DFA.unpackEncodedString(dfa_47s);
    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final short[][] dfa_49 = unpackEncodedStringArray(dfa_49s);

    class DFA115 extends DFA {

        public DFA115(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 115;
            this.eot = dfa_44;
            this.eof = dfa_44;
            this.min = dfa_45;
            this.max = dfa_46;
            this.accept = dfa_47;
            this.special = dfa_48;
            this.transition = dfa_49;
        }
        public String getDescription() {
            return "8397:2: ( (otherlv_0= '[' ( (lv_upperBounds_1_0= ruleAISLExpression ) ) (otherlv_2= ',' ( (lv_upperBounds_3_0= ruleAISLExpression ) ) )* otherlv_4= ']' (otherlv_5= 'as' otherlv_6= '[' ( (lv_indexNames_7_0= ruleIndexName ) ) (otherlv_8= ',' ( (lv_indexNames_9_0= ruleIndexName ) ) )* otherlv_10= ']' )? ) | (otherlv_11= '[' ( (lv_lowerBounds_12_0= ruleAISLExpression ) ) otherlv_13= ':' ( (lv_upperBounds_14_0= ruleAISLExpression ) ) (otherlv_15= ',' ( (lv_lowerBounds_16_0= ruleAISLExpression ) ) otherlv_17= ':' ( (lv_upperBounds_18_0= ruleAISLExpression ) ) )* otherlv_19= ']' (otherlv_20= 'as' otherlv_21= '[' ( (lv_indexNames_22_0= ruleIndexName ) ) (otherlv_23= ',' ( (lv_indexNames_24_0= ruleIndexName ) ) )* otherlv_25= ']' )? ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000265002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000245002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000345000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000002000080000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00000001EEC00000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x00000001EE800010L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000800000010L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x00000009EE000010L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x00000009EC000010L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x00000009E8000010L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000042000088010L,0x00000000001F8000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x68063C80000880B0L,0x0000000000002FDFL});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x00000001E0000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0400000208008012L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x01FFF8620111A070L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0001F8400001A070L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000004001000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0001F8000001A070L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0200780000018030L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000400100000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000F96000002070L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000010001000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000F86000002070L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000004000000010L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x01FFF8620101A070L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x01FFF8620101A072L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0001F8000001A072L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0200780000018032L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000070L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000072L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0001100000008070L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000180000000002L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000E00000000002L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000001000000010L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0001100000018070L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000001010000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000F86000002072L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000002000002000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0400000200000000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000002200000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x68063C80000980B0L,0x0000000000002FDFL});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000080010L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x68063C80001880B0L,0x0000000000002FDFL});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000008000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0006280000000000L,0x0000000000000FC0L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000002000008000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0004000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0002000000000002L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x01E0000000002002L,0x0000000000001000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000600000000002L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000000002L,0x00000000000000C0L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x8000380000000002L,0x0000000000E00000L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000020000000L});

}