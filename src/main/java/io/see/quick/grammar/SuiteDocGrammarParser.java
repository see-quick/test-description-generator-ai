// Generated from /Users/morsak/Documents/Work/test-description-generator/src/main/resources/SuiteDocGrammar.g4 by ANTLR 4.13.1
package io.see.quick.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SuiteDocGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, WS=24, STRING=25, 
		NUMBER=26;
	public static final int
		RULE_suiteDocAnnotation = 0, RULE_suiteDocBody = 1, RULE_suiteDocAttribute = 2, 
		RULE_descriptionAttribute = 3, RULE_contactAttribute = 4, RULE_contactBody = 5, 
		RULE_beforeTestStepsAttribute = 6, RULE_afterTestStepsAttribute = 7, RULE_step = 8, 
		RULE_useCasesAttribute = 9, RULE_useCase = 10, RULE_tagsAttribute = 11, 
		RULE_testTag = 12;
	private static String[] makeRuleNames() {
		return new String[] {
			"suiteDocAnnotation", "suiteDocBody", "suiteDocAttribute", "descriptionAttribute", 
			"contactAttribute", "contactBody", "beforeTestStepsAttribute", "afterTestStepsAttribute", 
			"step", "useCasesAttribute", "useCase", "tagsAttribute", "testTag"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'@SuiteDoc'", "'('", "')'", "','", "'description'", "'='", "'@Desc'", 
			"'contact'", "'@Contact'", "'name'", "'email'", "'beforeTestSteps'", 
			"'{'", "'}'", "'afterTestSteps'", "'@Step'", "'value'", "'expected'", 
			"'useCases'", "'@UseCase'", "'id'", "'tags'", "'@TestTag'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"WS", "STRING", "NUMBER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SuiteDocGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SuiteDocGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SuiteDocAnnotationContext extends ParserRuleContext {
		public SuiteDocBodyContext suiteDocBody() {
			return getRuleContext(SuiteDocBodyContext.class,0);
		}
		public SuiteDocAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suiteDocAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterSuiteDocAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitSuiteDocAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitSuiteDocAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuiteDocAnnotationContext suiteDocAnnotation() throws RecognitionException {
		SuiteDocAnnotationContext _localctx = new SuiteDocAnnotationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_suiteDocAnnotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			match(T__0);
			setState(27);
			match(T__1);
			setState(28);
			suiteDocBody();
			setState(29);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SuiteDocBodyContext extends ParserRuleContext {
		public List<SuiteDocAttributeContext> suiteDocAttribute() {
			return getRuleContexts(SuiteDocAttributeContext.class);
		}
		public SuiteDocAttributeContext suiteDocAttribute(int i) {
			return getRuleContext(SuiteDocAttributeContext.class,i);
		}
		public SuiteDocBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suiteDocBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterSuiteDocBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitSuiteDocBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitSuiteDocBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuiteDocBodyContext suiteDocBody() throws RecognitionException {
		SuiteDocBodyContext _localctx = new SuiteDocBodyContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_suiteDocBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			suiteDocAttribute();
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(32);
				match(T__3);
				setState(33);
				suiteDocAttribute();
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SuiteDocAttributeContext extends ParserRuleContext {
		public DescriptionAttributeContext descriptionAttribute() {
			return getRuleContext(DescriptionAttributeContext.class,0);
		}
		public ContactAttributeContext contactAttribute() {
			return getRuleContext(ContactAttributeContext.class,0);
		}
		public BeforeTestStepsAttributeContext beforeTestStepsAttribute() {
			return getRuleContext(BeforeTestStepsAttributeContext.class,0);
		}
		public AfterTestStepsAttributeContext afterTestStepsAttribute() {
			return getRuleContext(AfterTestStepsAttributeContext.class,0);
		}
		public UseCasesAttributeContext useCasesAttribute() {
			return getRuleContext(UseCasesAttributeContext.class,0);
		}
		public TagsAttributeContext tagsAttribute() {
			return getRuleContext(TagsAttributeContext.class,0);
		}
		public SuiteDocAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suiteDocAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterSuiteDocAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitSuiteDocAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitSuiteDocAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuiteDocAttributeContext suiteDocAttribute() throws RecognitionException {
		SuiteDocAttributeContext _localctx = new SuiteDocAttributeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_suiteDocAttribute);
		try {
			setState(45);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(39);
				descriptionAttribute();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 2);
				{
				setState(40);
				contactAttribute();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 3);
				{
				setState(41);
				beforeTestStepsAttribute();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 4);
				{
				setState(42);
				afterTestStepsAttribute();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 5);
				{
				setState(43);
				useCasesAttribute();
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 6);
				{
				setState(44);
				tagsAttribute();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DescriptionAttributeContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SuiteDocGrammarParser.STRING, 0); }
		public DescriptionAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descriptionAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterDescriptionAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitDescriptionAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitDescriptionAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescriptionAttributeContext descriptionAttribute() throws RecognitionException {
		DescriptionAttributeContext _localctx = new DescriptionAttributeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_descriptionAttribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__4);
			setState(48);
			match(T__5);
			setState(49);
			match(T__6);
			setState(50);
			match(T__1);
			setState(51);
			match(STRING);
			setState(52);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ContactAttributeContext extends ParserRuleContext {
		public ContactBodyContext contactBody() {
			return getRuleContext(ContactBodyContext.class,0);
		}
		public ContactAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_contactAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterContactAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitContactAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitContactAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContactAttributeContext contactAttribute() throws RecognitionException {
		ContactAttributeContext _localctx = new ContactAttributeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_contactAttribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(T__7);
			setState(55);
			match(T__5);
			setState(56);
			match(T__8);
			setState(57);
			match(T__1);
			setState(58);
			contactBody();
			setState(59);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ContactBodyContext extends ParserRuleContext {
		public List<TerminalNode> STRING() { return getTokens(SuiteDocGrammarParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(SuiteDocGrammarParser.STRING, i);
		}
		public ContactBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_contactBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterContactBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitContactBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitContactBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContactBodyContext contactBody() throws RecognitionException {
		ContactBodyContext _localctx = new ContactBodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_contactBody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(T__9);
			setState(62);
			match(T__5);
			setState(63);
			match(STRING);
			setState(64);
			match(T__3);
			setState(65);
			match(T__10);
			setState(66);
			match(T__5);
			setState(67);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BeforeTestStepsAttributeContext extends ParserRuleContext {
		public List<StepContext> step() {
			return getRuleContexts(StepContext.class);
		}
		public StepContext step(int i) {
			return getRuleContext(StepContext.class,i);
		}
		public BeforeTestStepsAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_beforeTestStepsAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterBeforeTestStepsAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitBeforeTestStepsAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitBeforeTestStepsAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BeforeTestStepsAttributeContext beforeTestStepsAttribute() throws RecognitionException {
		BeforeTestStepsAttributeContext _localctx = new BeforeTestStepsAttributeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_beforeTestStepsAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(T__11);
			setState(70);
			match(T__5);
			setState(71);
			match(T__12);
			setState(72);
			step();
			setState(77);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(73);
				match(T__3);
				setState(74);
				step();
				}
				}
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(80);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AfterTestStepsAttributeContext extends ParserRuleContext {
		public List<StepContext> step() {
			return getRuleContexts(StepContext.class);
		}
		public StepContext step(int i) {
			return getRuleContext(StepContext.class,i);
		}
		public AfterTestStepsAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_afterTestStepsAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterAfterTestStepsAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitAfterTestStepsAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitAfterTestStepsAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AfterTestStepsAttributeContext afterTestStepsAttribute() throws RecognitionException {
		AfterTestStepsAttributeContext _localctx = new AfterTestStepsAttributeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_afterTestStepsAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(T__14);
			setState(83);
			match(T__5);
			setState(84);
			match(T__12);
			setState(85);
			step();
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(86);
				match(T__3);
				setState(87);
				step();
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(93);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StepContext extends ParserRuleContext {
		public List<TerminalNode> STRING() { return getTokens(SuiteDocGrammarParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(SuiteDocGrammarParser.STRING, i);
		}
		public StepContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_step; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterStep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitStep(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitStep(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StepContext step() throws RecognitionException {
		StepContext _localctx = new StepContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_step);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(T__15);
			setState(96);
			match(T__1);
			setState(97);
			match(T__16);
			setState(98);
			match(T__5);
			setState(99);
			match(STRING);
			setState(100);
			match(T__3);
			setState(101);
			match(T__17);
			setState(102);
			match(T__5);
			setState(103);
			match(STRING);
			setState(104);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UseCasesAttributeContext extends ParserRuleContext {
		public List<UseCaseContext> useCase() {
			return getRuleContexts(UseCaseContext.class);
		}
		public UseCaseContext useCase(int i) {
			return getRuleContext(UseCaseContext.class,i);
		}
		public UseCasesAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_useCasesAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterUseCasesAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitUseCasesAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitUseCasesAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UseCasesAttributeContext useCasesAttribute() throws RecognitionException {
		UseCasesAttributeContext _localctx = new UseCasesAttributeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_useCasesAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(T__18);
			setState(107);
			match(T__5);
			setState(108);
			match(T__12);
			setState(109);
			useCase();
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(110);
				match(T__3);
				setState(111);
				useCase();
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(117);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UseCaseContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SuiteDocGrammarParser.STRING, 0); }
		public UseCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_useCase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterUseCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitUseCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitUseCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UseCaseContext useCase() throws RecognitionException {
		UseCaseContext _localctx = new UseCaseContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_useCase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			match(T__19);
			setState(120);
			match(T__1);
			setState(121);
			match(T__20);
			setState(122);
			match(T__5);
			setState(123);
			match(STRING);
			setState(124);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TagsAttributeContext extends ParserRuleContext {
		public List<TestTagContext> testTag() {
			return getRuleContexts(TestTagContext.class);
		}
		public TestTagContext testTag(int i) {
			return getRuleContext(TestTagContext.class,i);
		}
		public TagsAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tagsAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterTagsAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitTagsAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitTagsAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TagsAttributeContext tagsAttribute() throws RecognitionException {
		TagsAttributeContext _localctx = new TagsAttributeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_tagsAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(T__21);
			setState(127);
			match(T__5);
			setState(128);
			match(T__12);
			setState(129);
			testTag();
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(130);
				match(T__3);
				setState(131);
				testTag();
				}
				}
				setState(136);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(137);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TestTagContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(SuiteDocGrammarParser.STRING, 0); }
		public TestTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_testTag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).enterTestTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SuiteDocGrammarListener ) ((SuiteDocGrammarListener)listener).exitTestTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SuiteDocGrammarVisitor ) return ((SuiteDocGrammarVisitor<? extends T>)visitor).visitTestTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestTagContext testTag() throws RecognitionException {
		TestTagContext _localctx = new TestTagContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_testTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(T__22);
			setState(140);
			match(T__1);
			setState(141);
			match(T__16);
			setState(142);
			match(T__5);
			setState(143);
			match(STRING);
			setState(144);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001a\u0093\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001#\b\u0001\n\u0001"+
		"\f\u0001&\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0003\u0002.\b\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0005\u0006L\b\u0006\n\u0006\f\u0006O\t\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0005\u0007Y\b\u0007\n\u0007\f\u0007\\\t\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0005\tq\b\t\n\t\f\tt\t\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u0085\b\u000b\n\u000b"+
		"\f\u000b\u0088\t\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0000\u0000\r\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u0000\u0000\u008f\u0000"+
		"\u001a\u0001\u0000\u0000\u0000\u0002\u001f\u0001\u0000\u0000\u0000\u0004"+
		"-\u0001\u0000\u0000\u0000\u0006/\u0001\u0000\u0000\u0000\b6\u0001\u0000"+
		"\u0000\u0000\n=\u0001\u0000\u0000\u0000\fE\u0001\u0000\u0000\u0000\u000e"+
		"R\u0001\u0000\u0000\u0000\u0010_\u0001\u0000\u0000\u0000\u0012j\u0001"+
		"\u0000\u0000\u0000\u0014w\u0001\u0000\u0000\u0000\u0016~\u0001\u0000\u0000"+
		"\u0000\u0018\u008b\u0001\u0000\u0000\u0000\u001a\u001b\u0005\u0001\u0000"+
		"\u0000\u001b\u001c\u0005\u0002\u0000\u0000\u001c\u001d\u0003\u0002\u0001"+
		"\u0000\u001d\u001e\u0005\u0003\u0000\u0000\u001e\u0001\u0001\u0000\u0000"+
		"\u0000\u001f$\u0003\u0004\u0002\u0000 !\u0005\u0004\u0000\u0000!#\u0003"+
		"\u0004\u0002\u0000\" \u0001\u0000\u0000\u0000#&\u0001\u0000\u0000\u0000"+
		"$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%\u0003\u0001\u0000"+
		"\u0000\u0000&$\u0001\u0000\u0000\u0000\'.\u0003\u0006\u0003\u0000(.\u0003"+
		"\b\u0004\u0000).\u0003\f\u0006\u0000*.\u0003\u000e\u0007\u0000+.\u0003"+
		"\u0012\t\u0000,.\u0003\u0016\u000b\u0000-\'\u0001\u0000\u0000\u0000-("+
		"\u0001\u0000\u0000\u0000-)\u0001\u0000\u0000\u0000-*\u0001\u0000\u0000"+
		"\u0000-+\u0001\u0000\u0000\u0000-,\u0001\u0000\u0000\u0000.\u0005\u0001"+
		"\u0000\u0000\u0000/0\u0005\u0005\u0000\u000001\u0005\u0006\u0000\u0000"+
		"12\u0005\u0007\u0000\u000023\u0005\u0002\u0000\u000034\u0005\u0019\u0000"+
		"\u000045\u0005\u0003\u0000\u00005\u0007\u0001\u0000\u0000\u000067\u0005"+
		"\b\u0000\u000078\u0005\u0006\u0000\u000089\u0005\t\u0000\u00009:\u0005"+
		"\u0002\u0000\u0000:;\u0003\n\u0005\u0000;<\u0005\u0003\u0000\u0000<\t"+
		"\u0001\u0000\u0000\u0000=>\u0005\n\u0000\u0000>?\u0005\u0006\u0000\u0000"+
		"?@\u0005\u0019\u0000\u0000@A\u0005\u0004\u0000\u0000AB\u0005\u000b\u0000"+
		"\u0000BC\u0005\u0006\u0000\u0000CD\u0005\u0019\u0000\u0000D\u000b\u0001"+
		"\u0000\u0000\u0000EF\u0005\f\u0000\u0000FG\u0005\u0006\u0000\u0000GH\u0005"+
		"\r\u0000\u0000HM\u0003\u0010\b\u0000IJ\u0005\u0004\u0000\u0000JL\u0003"+
		"\u0010\b\u0000KI\u0001\u0000\u0000\u0000LO\u0001\u0000\u0000\u0000MK\u0001"+
		"\u0000\u0000\u0000MN\u0001\u0000\u0000\u0000NP\u0001\u0000\u0000\u0000"+
		"OM\u0001\u0000\u0000\u0000PQ\u0005\u000e\u0000\u0000Q\r\u0001\u0000\u0000"+
		"\u0000RS\u0005\u000f\u0000\u0000ST\u0005\u0006\u0000\u0000TU\u0005\r\u0000"+
		"\u0000UZ\u0003\u0010\b\u0000VW\u0005\u0004\u0000\u0000WY\u0003\u0010\b"+
		"\u0000XV\u0001\u0000\u0000\u0000Y\\\u0001\u0000\u0000\u0000ZX\u0001\u0000"+
		"\u0000\u0000Z[\u0001\u0000\u0000\u0000[]\u0001\u0000\u0000\u0000\\Z\u0001"+
		"\u0000\u0000\u0000]^\u0005\u000e\u0000\u0000^\u000f\u0001\u0000\u0000"+
		"\u0000_`\u0005\u0010\u0000\u0000`a\u0005\u0002\u0000\u0000ab\u0005\u0011"+
		"\u0000\u0000bc\u0005\u0006\u0000\u0000cd\u0005\u0019\u0000\u0000de\u0005"+
		"\u0004\u0000\u0000ef\u0005\u0012\u0000\u0000fg\u0005\u0006\u0000\u0000"+
		"gh\u0005\u0019\u0000\u0000hi\u0005\u0003\u0000\u0000i\u0011\u0001\u0000"+
		"\u0000\u0000jk\u0005\u0013\u0000\u0000kl\u0005\u0006\u0000\u0000lm\u0005"+
		"\r\u0000\u0000mr\u0003\u0014\n\u0000no\u0005\u0004\u0000\u0000oq\u0003"+
		"\u0014\n\u0000pn\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000\u0000rp\u0001"+
		"\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000su\u0001\u0000\u0000\u0000"+
		"tr\u0001\u0000\u0000\u0000uv\u0005\u000e\u0000\u0000v\u0013\u0001\u0000"+
		"\u0000\u0000wx\u0005\u0014\u0000\u0000xy\u0005\u0002\u0000\u0000yz\u0005"+
		"\u0015\u0000\u0000z{\u0005\u0006\u0000\u0000{|\u0005\u0019\u0000\u0000"+
		"|}\u0005\u0003\u0000\u0000}\u0015\u0001\u0000\u0000\u0000~\u007f\u0005"+
		"\u0016\u0000\u0000\u007f\u0080\u0005\u0006\u0000\u0000\u0080\u0081\u0005"+
		"\r\u0000\u0000\u0081\u0086\u0003\u0018\f\u0000\u0082\u0083\u0005\u0004"+
		"\u0000\u0000\u0083\u0085\u0003\u0018\f\u0000\u0084\u0082\u0001\u0000\u0000"+
		"\u0000\u0085\u0088\u0001\u0000\u0000\u0000\u0086\u0084\u0001\u0000\u0000"+
		"\u0000\u0086\u0087\u0001\u0000\u0000\u0000\u0087\u0089\u0001\u0000\u0000"+
		"\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0089\u008a\u0005\u000e\u0000"+
		"\u0000\u008a\u0017\u0001\u0000\u0000\u0000\u008b\u008c\u0005\u0017\u0000"+
		"\u0000\u008c\u008d\u0005\u0002\u0000\u0000\u008d\u008e\u0005\u0011\u0000"+
		"\u0000\u008e\u008f\u0005\u0006\u0000\u0000\u008f\u0090\u0005\u0019\u0000"+
		"\u0000\u0090\u0091\u0005\u0003\u0000\u0000\u0091\u0019\u0001\u0000\u0000"+
		"\u0000\u0006$-MZr\u0086";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}