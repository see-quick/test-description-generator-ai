// Generated from /Users/morsak/Documents/Work/test-description-generator/src/main/resources/TestDocGrammar.g4 by ANTLR 4.13.1
package io.see.quick.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TestDocGrammarParser}.
 */
public interface TestDocGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#testDocAnnotation}.
	 * @param ctx the parse tree
	 */
	void enterTestDocAnnotation(TestDocGrammarParser.TestDocAnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#testDocAnnotation}.
	 * @param ctx the parse tree
	 */
	void exitTestDocAnnotation(TestDocGrammarParser.TestDocAnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#testDocBody}.
	 * @param ctx the parse tree
	 */
	void enterTestDocBody(TestDocGrammarParser.TestDocBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#testDocBody}.
	 * @param ctx the parse tree
	 */
	void exitTestDocBody(TestDocGrammarParser.TestDocBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#testDocAttribute}.
	 * @param ctx the parse tree
	 */
	void enterTestDocAttribute(TestDocGrammarParser.TestDocAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#testDocAttribute}.
	 * @param ctx the parse tree
	 */
	void exitTestDocAttribute(TestDocGrammarParser.TestDocAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#descriptionAttribute}.
	 * @param ctx the parse tree
	 */
	void enterDescriptionAttribute(TestDocGrammarParser.DescriptionAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#descriptionAttribute}.
	 * @param ctx the parse tree
	 */
	void exitDescriptionAttribute(TestDocGrammarParser.DescriptionAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#contactAttribute}.
	 * @param ctx the parse tree
	 */
	void enterContactAttribute(TestDocGrammarParser.ContactAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#contactAttribute}.
	 * @param ctx the parse tree
	 */
	void exitContactAttribute(TestDocGrammarParser.ContactAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#contactBody}.
	 * @param ctx the parse tree
	 */
	void enterContactBody(TestDocGrammarParser.ContactBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#contactBody}.
	 * @param ctx the parse tree
	 */
	void exitContactBody(TestDocGrammarParser.ContactBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#stepsAttribute}.
	 * @param ctx the parse tree
	 */
	void enterStepsAttribute(TestDocGrammarParser.StepsAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#stepsAttribute}.
	 * @param ctx the parse tree
	 */
	void exitStepsAttribute(TestDocGrammarParser.StepsAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#step}.
	 * @param ctx the parse tree
	 */
	void enterStep(TestDocGrammarParser.StepContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#step}.
	 * @param ctx the parse tree
	 */
	void exitStep(TestDocGrammarParser.StepContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#useCasesAttribute}.
	 * @param ctx the parse tree
	 */
	void enterUseCasesAttribute(TestDocGrammarParser.UseCasesAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#useCasesAttribute}.
	 * @param ctx the parse tree
	 */
	void exitUseCasesAttribute(TestDocGrammarParser.UseCasesAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#useCase}.
	 * @param ctx the parse tree
	 */
	void enterUseCase(TestDocGrammarParser.UseCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#useCase}.
	 * @param ctx the parse tree
	 */
	void exitUseCase(TestDocGrammarParser.UseCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#tagsAttribute}.
	 * @param ctx the parse tree
	 */
	void enterTagsAttribute(TestDocGrammarParser.TagsAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#tagsAttribute}.
	 * @param ctx the parse tree
	 */
	void exitTagsAttribute(TestDocGrammarParser.TagsAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link TestDocGrammarParser#testTag}.
	 * @param ctx the parse tree
	 */
	void enterTestTag(TestDocGrammarParser.TestTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link TestDocGrammarParser#testTag}.
	 * @param ctx the parse tree
	 */
	void exitTestTag(TestDocGrammarParser.TestTagContext ctx);
}