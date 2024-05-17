// Generated from /Users/morsak/Documents/Work/test-description-generator/src/main/resources/TestDocGrammar.g4 by ANTLR 4.13.1
package io.see.quick.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TestDocGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TestDocGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#testDocAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestDocAnnotation(TestDocGrammarParser.TestDocAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#testDocBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestDocBody(TestDocGrammarParser.TestDocBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#testDocAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestDocAttribute(TestDocGrammarParser.TestDocAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#descriptionAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescriptionAttribute(TestDocGrammarParser.DescriptionAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#contactAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContactAttribute(TestDocGrammarParser.ContactAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#contactBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContactBody(TestDocGrammarParser.ContactBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#stepsAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStepsAttribute(TestDocGrammarParser.StepsAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#step}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStep(TestDocGrammarParser.StepContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#useCasesAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUseCasesAttribute(TestDocGrammarParser.UseCasesAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#useCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUseCase(TestDocGrammarParser.UseCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#tagsAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagsAttribute(TestDocGrammarParser.TagsAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link TestDocGrammarParser#testTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestTag(TestDocGrammarParser.TestTagContext ctx);
}