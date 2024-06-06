// Generated from /Users/morsak/Documents/Work/test-description-generator/src/main/resources/SuiteDocGrammar.g4 by ANTLR 4.13.1
package io.see.quick.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SuiteDocGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SuiteDocGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#suiteDocAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuiteDocAnnotation(SuiteDocGrammarParser.SuiteDocAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#suiteDocBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuiteDocBody(SuiteDocGrammarParser.SuiteDocBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#suiteDocAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuiteDocAttribute(SuiteDocGrammarParser.SuiteDocAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#descriptionAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDescriptionAttribute(SuiteDocGrammarParser.DescriptionAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#contactAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContactAttribute(SuiteDocGrammarParser.ContactAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#contactBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContactBody(SuiteDocGrammarParser.ContactBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#beforeTestStepsAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBeforeTestStepsAttribute(SuiteDocGrammarParser.BeforeTestStepsAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#afterTestStepsAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAfterTestStepsAttribute(SuiteDocGrammarParser.AfterTestStepsAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#step}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStep(SuiteDocGrammarParser.StepContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#useCasesAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUseCasesAttribute(SuiteDocGrammarParser.UseCasesAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#useCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUseCase(SuiteDocGrammarParser.UseCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#tagsAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagsAttribute(SuiteDocGrammarParser.TagsAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SuiteDocGrammarParser#testTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestTag(SuiteDocGrammarParser.TestTagContext ctx);
}