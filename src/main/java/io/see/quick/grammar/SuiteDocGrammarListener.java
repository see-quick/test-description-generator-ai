// Generated from /Users/morsak/Documents/Work/test-description-generator/src/main/resources/SuiteDocGrammar.g4 by ANTLR 4.13.1
package io.see.quick.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SuiteDocGrammarParser}.
 */
public interface SuiteDocGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#suiteDocAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterSuiteDocAnnotation(SuiteDocGrammarParser.SuiteDocAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#suiteDocAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitSuiteDocAnnotation(SuiteDocGrammarParser.SuiteDocAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#suiteDocBody}.
	 * @param ctx the parse tree
	 */
	void enterSuiteDocBody(SuiteDocGrammarParser.SuiteDocBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#suiteDocBody}.
	 * @param ctx the parse tree
	 */
	void exitSuiteDocBody(SuiteDocGrammarParser.SuiteDocBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#suiteDocAttribute}.
	 * @param ctx the parse tree
	 */
	void enterSuiteDocAttribute(SuiteDocGrammarParser.SuiteDocAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#suiteDocAttribute}.
	 * @param ctx the parse tree
	 */
	void exitSuiteDocAttribute(SuiteDocGrammarParser.SuiteDocAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#descriptionAttribute}.
	 * @param ctx the parse tree
	 */
	void enterDescriptionAttribute(SuiteDocGrammarParser.DescriptionAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#descriptionAttribute}.
	 * @param ctx the parse tree
	 */
	void exitDescriptionAttribute(SuiteDocGrammarParser.DescriptionAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#contactAttribute}.
	 * @param ctx the parse tree
	 */
	void enterContactAttribute(SuiteDocGrammarParser.ContactAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#contactAttribute}.
	 * @param ctx the parse tree
	 */
	void exitContactAttribute(SuiteDocGrammarParser.ContactAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#contactBody}.
	 * @param ctx the parse tree
	 */
	void enterContactBody(SuiteDocGrammarParser.ContactBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#contactBody}.
	 * @param ctx the parse tree
	 */
	void exitContactBody(SuiteDocGrammarParser.ContactBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#beforeTestStepsAttribute}.
	 * @param ctx the parse tree
	 */
	void enterBeforeTestStepsAttribute(SuiteDocGrammarParser.BeforeTestStepsAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#beforeTestStepsAttribute}.
	 * @param ctx the parse tree
	 */
	void exitBeforeTestStepsAttribute(SuiteDocGrammarParser.BeforeTestStepsAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#afterTestStepsAttribute}.
	 * @param ctx the parse tree
	 */
	void enterAfterTestStepsAttribute(SuiteDocGrammarParser.AfterTestStepsAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#afterTestStepsAttribute}.
	 * @param ctx the parse tree
	 */
	void exitAfterTestStepsAttribute(SuiteDocGrammarParser.AfterTestStepsAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#step}.
	 * @param ctx the parse tree
	 */
	void enterStep(SuiteDocGrammarParser.StepContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#step}.
	 * @param ctx the parse tree
	 */
	void exitStep(SuiteDocGrammarParser.StepContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#useCasesAttribute}.
	 * @param ctx the parse tree
	 */
	void enterUseCasesAttribute(SuiteDocGrammarParser.UseCasesAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#useCasesAttribute}.
	 * @param ctx the parse tree
	 */
	void exitUseCasesAttribute(SuiteDocGrammarParser.UseCasesAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#useCase}.
	 * @param ctx the parse tree
	 */
	void enterUseCase(SuiteDocGrammarParser.UseCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#useCase}.
	 * @param ctx the parse tree
	 */
	void exitUseCase(SuiteDocGrammarParser.UseCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#tagsAttribute}.
	 * @param ctx the parse tree
	 */
	void enterTagsAttribute(SuiteDocGrammarParser.TagsAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#tagsAttribute}.
	 * @param ctx the parse tree
	 */
	void exitTagsAttribute(SuiteDocGrammarParser.TagsAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SuiteDocGrammarParser#testTag}.
	 * @param ctx the parse tree
	 */
	void enterTestTag(SuiteDocGrammarParser.TestTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link SuiteDocGrammarParser#testTag}.
	 * @param ctx the parse tree
	 */
	void exitTestTag(SuiteDocGrammarParser.TestTagContext ctx);
}