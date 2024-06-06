package io.see.quick.visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import io.see.quick.grammar.SuiteDocGrammarBaseVisitor;
import io.see.quick.grammar.SuiteDocGrammarParser;

import java.util.List;

public class SuiteDocAnnotationApplierVisitor extends SuiteDocGrammarBaseVisitor<Expression> {
    private ClassOrInterfaceDeclaration clazz;

    public SuiteDocAnnotationApplierVisitor(ClassOrInterfaceDeclaration clazz) {
        this.clazz = clazz;
    }

    @Override
    public Expression visitSuiteDocAnnotation(SuiteDocGrammarParser.SuiteDocAnnotationContext ctx) {
        NormalAnnotationExpr suiteDocAnnotation = new NormalAnnotationExpr();
        suiteDocAnnotation.setName("SuiteDoc");
        NodeList<MemberValuePair> pairs = new NodeList<>();

        ctx.suiteDocBody().suiteDocAttribute().forEach(attr -> {
            if (attr.descriptionAttribute() != null) {
                pairs.add(new MemberValuePair("description", visitDescriptionAttribute(attr.descriptionAttribute())));
            } else if (attr.contactAttribute() != null) {
                pairs.add(new MemberValuePair("contact", visitContactAttribute(attr.contactAttribute())));
            } else if (attr.beforeTestStepsAttribute() != null) {
                pairs.add(new MemberValuePair("beforeTestSteps", visitBeforeTestStepsAttribute(attr.beforeTestStepsAttribute())));
            } else if (attr.afterTestStepsAttribute() != null) {
                pairs.add(new MemberValuePair("afterTestSteps", visitAfterTestStepsAttribute(attr.afterTestStepsAttribute())));
            } else if (attr.useCasesAttribute() != null) {
                pairs.add(new MemberValuePair("useCases", visitUseCasesAttribute(attr.useCasesAttribute())));
            } else if (attr.tagsAttribute() != null) {
                pairs.add(new MemberValuePair("tags", visitTagsAttribute(attr.tagsAttribute())));
            }
        });

        suiteDocAnnotation.setPairs(pairs);
        clazz.addAnnotation(suiteDocAnnotation);
        return null;
    }

    @Override
    public Expression visitDescriptionAttribute(SuiteDocGrammarParser.DescriptionAttributeContext ctx) {
        return new SingleMemberAnnotationExpr(new Name("Desc"), new StringLiteralExpr(ctx.STRING().getText().replace("\"", "")));
    }

    @Override
    public Expression visitContactAttribute(SuiteDocGrammarParser.ContactAttributeContext ctx) {
        String name = ctx.contactBody().getChild(2).getText().replace("\"", "");
        String email = ctx.contactBody().getChild(6).getText().replace("\"", "");
        NormalAnnotationExpr contactAnnotation = new NormalAnnotationExpr(new Name("Contact"), new NodeList<>(
            new MemberValuePair("name", new StringLiteralExpr(name)),
            new MemberValuePair("email", new StringLiteralExpr(email))
        ));
        return contactAnnotation;
    }

    @Override
    public Expression visitBeforeTestStepsAttribute(SuiteDocGrammarParser.BeforeTestStepsAttributeContext ctx) {
        return processSteps(ctx.step());
    }

    @Override
    public Expression visitAfterTestStepsAttribute(SuiteDocGrammarParser.AfterTestStepsAttributeContext ctx) {
        return processSteps(ctx.step());
    }

    private Expression processSteps(List<SuiteDocGrammarParser.StepContext> stepContexts) {
        NodeList<Expression> steps = new NodeList<>();
        stepContexts.forEach(step -> {
            String value = step.getChild(4).getText().replace("\"", "");
            String expected = step.getChild(8).getText().replace("\"", "");
            steps.add(new NormalAnnotationExpr(new Name("Step"), new NodeList<>(
                new MemberValuePair("value", new StringLiteralExpr(value)),
                new MemberValuePair("expected", new StringLiteralExpr(expected))
            )));
        });
        return new ArrayInitializerExpr(steps);
    }

    @Override
    public Expression visitUseCasesAttribute(SuiteDocGrammarParser.UseCasesAttributeContext ctx) {
        NodeList<Expression> useCases = new NodeList<>();
        ctx.useCase().forEach(useCase -> {
            String id = useCase.getChild(4).getText().replace("\"", ""); // assuming id is directly a STRING
            useCases.add(new NormalAnnotationExpr(new Name("UseCase"), new NodeList<>(new MemberValuePair("id", new StringLiteralExpr(id)))));
        });
        return new ArrayInitializerExpr(useCases);
    }

    @Override
    public Expression visitTagsAttribute(SuiteDocGrammarParser.TagsAttributeContext ctx) {
        NodeList<Expression> tags = new NodeList<>();
        ctx.testTag().forEach(tag -> {
            String value = tag.getChild(4).getText().replace("\"", ""); // assuming value is directly a STRING
            tags.add(new NormalAnnotationExpr(new Name("TestTag"), new NodeList<>(new MemberValuePair("value", new StringLiteralExpr(value)))));
        });
        return new ArrayInitializerExpr(tags);
    }
}
