// Generated from /Users/morsak/Documents/Work/test-description-generator/src/main/resources/TestDocGrammar.g4 by ANTLR 4.13.1
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
public class TestDocGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, LETTERS=23, DIGITS=24, 
		SPECIAL_CHARACTERS=25, WS=26, STRING=27;
	public static final int
		RULE_testDocAnnotation = 0, RULE_testDocBody = 1, RULE_testDocAttribute = 2, 
		RULE_descriptionAttribute = 3, RULE_contactAttribute = 4, RULE_contactBody = 5, 
		RULE_stepsAttribute = 6, RULE_step = 7, RULE_useCasesAttribute = 8, RULE_useCase = 9, 
		RULE_tagsAttribute = 10, RULE_testTag = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"testDocAnnotation", "testDocBody", "testDocAttribute", "descriptionAttribute", 
			"contactAttribute", "contactBody", "stepsAttribute", "step", "useCasesAttribute", 
			"useCase", "tagsAttribute", "testTag"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'@TestDoc'", "'('", "')'", "','", "'description'", "'='", "'@Desc'", 
			"'contact'", "'@Contact'", "'name'", "'email'", "'steps'", "'{'", "'}'", 
			"'@Step'", "'value'", "'expected'", "'useCases'", "'@UseCase'", "'id'", 
			"'tags'", "'@TestTag'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, "LETTERS", 
			"DIGITS", "SPECIAL_CHARACTERS", "WS", "STRING"
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
	public String getGrammarFileName() { return "TestDocGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TestDocGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TestDocAnnotationContext extends ParserRuleContext {
		public TestDocBodyContext testDocBody() {
			return getRuleContext(TestDocBodyContext.class,0);
		}
		public TestDocAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_testDocAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterTestDocAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitTestDocAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitTestDocAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestDocAnnotationContext testDocAnnotation() throws RecognitionException {
		TestDocAnnotationContext _localctx = new TestDocAnnotationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_testDocAnnotation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(T__0);
			setState(25);
			match(T__1);
			setState(26);
			testDocBody();
			setState(27);
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
	public static class TestDocBodyContext extends ParserRuleContext {
		public List<TestDocAttributeContext> testDocAttribute() {
			return getRuleContexts(TestDocAttributeContext.class);
		}
		public TestDocAttributeContext testDocAttribute(int i) {
			return getRuleContext(TestDocAttributeContext.class,i);
		}
		public TestDocBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_testDocBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterTestDocBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitTestDocBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitTestDocBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestDocBodyContext testDocBody() throws RecognitionException {
		TestDocBodyContext _localctx = new TestDocBodyContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_testDocBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			testDocAttribute();
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(30);
				match(T__3);
				setState(31);
				testDocAttribute();
				}
				}
				setState(36);
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
	public static class TestDocAttributeContext extends ParserRuleContext {
		public DescriptionAttributeContext descriptionAttribute() {
			return getRuleContext(DescriptionAttributeContext.class,0);
		}
		public ContactAttributeContext contactAttribute() {
			return getRuleContext(ContactAttributeContext.class,0);
		}
		public StepsAttributeContext stepsAttribute() {
			return getRuleContext(StepsAttributeContext.class,0);
		}
		public UseCasesAttributeContext useCasesAttribute() {
			return getRuleContext(UseCasesAttributeContext.class,0);
		}
		public TagsAttributeContext tagsAttribute() {
			return getRuleContext(TagsAttributeContext.class,0);
		}
		public TestDocAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_testDocAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterTestDocAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitTestDocAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitTestDocAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestDocAttributeContext testDocAttribute() throws RecognitionException {
		TestDocAttributeContext _localctx = new TestDocAttributeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_testDocAttribute);
		try {
			setState(42);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(37);
				descriptionAttribute();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 2);
				{
				setState(38);
				contactAttribute();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 3);
				{
				setState(39);
				stepsAttribute();
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 4);
				{
				setState(40);
				useCasesAttribute();
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 5);
				{
				setState(41);
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
		public TerminalNode STRING() { return getToken(TestDocGrammarParser.STRING, 0); }
		public DescriptionAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_descriptionAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterDescriptionAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitDescriptionAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitDescriptionAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescriptionAttributeContext descriptionAttribute() throws RecognitionException {
		DescriptionAttributeContext _localctx = new DescriptionAttributeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_descriptionAttribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(T__4);
			setState(45);
			match(T__5);
			setState(46);
			match(T__6);
			setState(47);
			match(T__1);
			setState(48);
			match(STRING);
			setState(49);
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
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterContactAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitContactAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitContactAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContactAttributeContext contactAttribute() throws RecognitionException {
		ContactAttributeContext _localctx = new ContactAttributeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_contactAttribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(T__7);
			setState(52);
			match(T__5);
			setState(53);
			match(T__8);
			setState(54);
			match(T__1);
			setState(55);
			contactBody();
			setState(56);
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
		public List<TerminalNode> STRING() { return getTokens(TestDocGrammarParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(TestDocGrammarParser.STRING, i);
		}
		public ContactBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_contactBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterContactBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitContactBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitContactBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContactBodyContext contactBody() throws RecognitionException {
		ContactBodyContext _localctx = new ContactBodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_contactBody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(T__9);
			setState(59);
			match(T__5);
			setState(60);
			match(STRING);
			setState(61);
			match(T__3);
			setState(62);
			match(T__10);
			setState(63);
			match(T__5);
			setState(64);
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
	public static class StepsAttributeContext extends ParserRuleContext {
		public List<StepContext> step() {
			return getRuleContexts(StepContext.class);
		}
		public StepContext step(int i) {
			return getRuleContext(StepContext.class,i);
		}
		public StepsAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stepsAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterStepsAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitStepsAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitStepsAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StepsAttributeContext stepsAttribute() throws RecognitionException {
		StepsAttributeContext _localctx = new StepsAttributeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_stepsAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(T__11);
			setState(67);
			match(T__5);
			setState(68);
			match(T__12);
			setState(69);
			step();
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(70);
				match(T__3);
				setState(71);
				step();
				}
				}
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(77);
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
		public List<TerminalNode> STRING() { return getTokens(TestDocGrammarParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(TestDocGrammarParser.STRING, i);
		}
		public StepContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_step; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterStep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitStep(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitStep(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StepContext step() throws RecognitionException {
		StepContext _localctx = new StepContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_step);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(T__14);
			setState(80);
			match(T__1);
			setState(81);
			match(T__15);
			setState(82);
			match(T__5);
			setState(83);
			match(STRING);
			setState(84);
			match(T__3);
			setState(85);
			match(T__16);
			setState(86);
			match(T__5);
			setState(87);
			match(STRING);
			setState(88);
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
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterUseCasesAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitUseCasesAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitUseCasesAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UseCasesAttributeContext useCasesAttribute() throws RecognitionException {
		UseCasesAttributeContext _localctx = new UseCasesAttributeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_useCasesAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(T__17);
			setState(91);
			match(T__5);
			setState(92);
			match(T__12);
			setState(93);
			useCase();
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(94);
				match(T__3);
				setState(95);
				useCase();
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(101);
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
		public TerminalNode STRING() { return getToken(TestDocGrammarParser.STRING, 0); }
		public UseCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_useCase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterUseCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitUseCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitUseCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UseCaseContext useCase() throws RecognitionException {
		UseCaseContext _localctx = new UseCaseContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_useCase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(T__18);
			setState(104);
			match(T__1);
			setState(105);
			match(T__19);
			setState(106);
			match(T__5);
			setState(107);
			match(STRING);
			setState(108);
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
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterTagsAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitTagsAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitTagsAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TagsAttributeContext tagsAttribute() throws RecognitionException {
		TagsAttributeContext _localctx = new TagsAttributeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_tagsAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(T__20);
			setState(111);
			match(T__5);
			setState(112);
			match(T__12);
			setState(113);
			testTag();
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(114);
				match(T__3);
				setState(115);
				testTag();
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121);
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
		public TerminalNode STRING() { return getToken(TestDocGrammarParser.STRING, 0); }
		public TestTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_testTag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).enterTestTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TestDocGrammarListener ) ((TestDocGrammarListener)listener).exitTestTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestDocGrammarVisitor ) return ((TestDocGrammarVisitor<? extends T>)visitor).visitTestTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestTagContext testTag() throws RecognitionException {
		TestTagContext _localctx = new TestTagContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_testTag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(T__21);
			setState(124);
			match(T__1);
			setState(125);
			match(T__15);
			setState(126);
			match(T__5);
			setState(127);
			match(STRING);
			setState(128);
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
		"\u0004\u0001\u001b\u0083\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0005\u0001!\b\u0001\n\u0001\f\u0001$\t\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"+\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006"+
		"I\b\u0006\n\u0006\f\u0006L\t\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0005\ba\b\b\n\b\f\bd\t\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0005\nu\b\n\n\n\f\nx\t\n\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0000\u0000\f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0000\u0000~\u0000\u0018\u0001\u0000\u0000\u0000\u0002"+
		"\u001d\u0001\u0000\u0000\u0000\u0004*\u0001\u0000\u0000\u0000\u0006,\u0001"+
		"\u0000\u0000\u0000\b3\u0001\u0000\u0000\u0000\n:\u0001\u0000\u0000\u0000"+
		"\fB\u0001\u0000\u0000\u0000\u000eO\u0001\u0000\u0000\u0000\u0010Z\u0001"+
		"\u0000\u0000\u0000\u0012g\u0001\u0000\u0000\u0000\u0014n\u0001\u0000\u0000"+
		"\u0000\u0016{\u0001\u0000\u0000\u0000\u0018\u0019\u0005\u0001\u0000\u0000"+
		"\u0019\u001a\u0005\u0002\u0000\u0000\u001a\u001b\u0003\u0002\u0001\u0000"+
		"\u001b\u001c\u0005\u0003\u0000\u0000\u001c\u0001\u0001\u0000\u0000\u0000"+
		"\u001d\"\u0003\u0004\u0002\u0000\u001e\u001f\u0005\u0004\u0000\u0000\u001f"+
		"!\u0003\u0004\u0002\u0000 \u001e\u0001\u0000\u0000\u0000!$\u0001\u0000"+
		"\u0000\u0000\" \u0001\u0000\u0000\u0000\"#\u0001\u0000\u0000\u0000#\u0003"+
		"\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000\u0000%+\u0003\u0006\u0003"+
		"\u0000&+\u0003\b\u0004\u0000\'+\u0003\f\u0006\u0000(+\u0003\u0010\b\u0000"+
		")+\u0003\u0014\n\u0000*%\u0001\u0000\u0000\u0000*&\u0001\u0000\u0000\u0000"+
		"*\'\u0001\u0000\u0000\u0000*(\u0001\u0000\u0000\u0000*)\u0001\u0000\u0000"+
		"\u0000+\u0005\u0001\u0000\u0000\u0000,-\u0005\u0005\u0000\u0000-.\u0005"+
		"\u0006\u0000\u0000./\u0005\u0007\u0000\u0000/0\u0005\u0002\u0000\u0000"+
		"01\u0005\u001b\u0000\u000012\u0005\u0003\u0000\u00002\u0007\u0001\u0000"+
		"\u0000\u000034\u0005\b\u0000\u000045\u0005\u0006\u0000\u000056\u0005\t"+
		"\u0000\u000067\u0005\u0002\u0000\u000078\u0003\n\u0005\u000089\u0005\u0003"+
		"\u0000\u00009\t\u0001\u0000\u0000\u0000:;\u0005\n\u0000\u0000;<\u0005"+
		"\u0006\u0000\u0000<=\u0005\u001b\u0000\u0000=>\u0005\u0004\u0000\u0000"+
		">?\u0005\u000b\u0000\u0000?@\u0005\u0006\u0000\u0000@A\u0005\u001b\u0000"+
		"\u0000A\u000b\u0001\u0000\u0000\u0000BC\u0005\f\u0000\u0000CD\u0005\u0006"+
		"\u0000\u0000DE\u0005\r\u0000\u0000EJ\u0003\u000e\u0007\u0000FG\u0005\u0004"+
		"\u0000\u0000GI\u0003\u000e\u0007\u0000HF\u0001\u0000\u0000\u0000IL\u0001"+
		"\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000JK\u0001\u0000\u0000\u0000"+
		"KM\u0001\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000MN\u0005\u000e\u0000"+
		"\u0000N\r\u0001\u0000\u0000\u0000OP\u0005\u000f\u0000\u0000PQ\u0005\u0002"+
		"\u0000\u0000QR\u0005\u0010\u0000\u0000RS\u0005\u0006\u0000\u0000ST\u0005"+
		"\u001b\u0000\u0000TU\u0005\u0004\u0000\u0000UV\u0005\u0011\u0000\u0000"+
		"VW\u0005\u0006\u0000\u0000WX\u0005\u001b\u0000\u0000XY\u0005\u0003\u0000"+
		"\u0000Y\u000f\u0001\u0000\u0000\u0000Z[\u0005\u0012\u0000\u0000[\\\u0005"+
		"\u0006\u0000\u0000\\]\u0005\r\u0000\u0000]b\u0003\u0012\t\u0000^_\u0005"+
		"\u0004\u0000\u0000_a\u0003\u0012\t\u0000`^\u0001\u0000\u0000\u0000ad\u0001"+
		"\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000"+
		"ce\u0001\u0000\u0000\u0000db\u0001\u0000\u0000\u0000ef\u0005\u000e\u0000"+
		"\u0000f\u0011\u0001\u0000\u0000\u0000gh\u0005\u0013\u0000\u0000hi\u0005"+
		"\u0002\u0000\u0000ij\u0005\u0014\u0000\u0000jk\u0005\u0006\u0000\u0000"+
		"kl\u0005\u001b\u0000\u0000lm\u0005\u0003\u0000\u0000m\u0013\u0001\u0000"+
		"\u0000\u0000no\u0005\u0015\u0000\u0000op\u0005\u0006\u0000\u0000pq\u0005"+
		"\r\u0000\u0000qv\u0003\u0016\u000b\u0000rs\u0005\u0004\u0000\u0000su\u0003"+
		"\u0016\u000b\u0000tr\u0001\u0000\u0000\u0000ux\u0001\u0000\u0000\u0000"+
		"vt\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000wy\u0001\u0000\u0000"+
		"\u0000xv\u0001\u0000\u0000\u0000yz\u0005\u000e\u0000\u0000z\u0015\u0001"+
		"\u0000\u0000\u0000{|\u0005\u0016\u0000\u0000|}\u0005\u0002\u0000\u0000"+
		"}~\u0005\u0010\u0000\u0000~\u007f\u0005\u0006\u0000\u0000\u007f\u0080"+
		"\u0005\u001b\u0000\u0000\u0080\u0081\u0005\u0003\u0000\u0000\u0081\u0017"+
		"\u0001\u0000\u0000\u0000\u0005\"*Jbv";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}