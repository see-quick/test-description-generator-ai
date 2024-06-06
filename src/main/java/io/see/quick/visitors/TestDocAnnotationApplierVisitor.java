package io.see.quick.visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import io.see.quick.grammar.TestDocGrammarBaseVisitor;
import io.see.quick.grammar.TestDocGrammarParser;

public class TestDocAnnotationApplierVisitor extends TestDocGrammarBaseVisitor<Expression> {
    private MethodDeclaration method;

    public TestDocAnnotationApplierVisitor(MethodDeclaration method) {
        this.method = method;
    }

    @Override
    public Expression visitTestDocAnnotation(TestDocGrammarParser.TestDocAnnotationContext ctx) {
        NormalAnnotationExpr testDocAnnotation = new NormalAnnotationExpr();
        testDocAnnotation.setName("TestDoc");
        NodeList<MemberValuePair> pairs = new NodeList<>();

        ctx.testDocBody().testDocAttribute().forEach(attr -> {
            if (attr.descriptionAttribute() != null) {
                pairs.add(new MemberValuePair("description", visitDescriptionAttribute(attr.descriptionAttribute())));
            } else if (attr.contactAttribute() != null) {
                pairs.add(new MemberValuePair("contact", visitContactAttribute(attr.contactAttribute())));
            } else if (attr.stepsAttribute() != null) {
                pairs.add(new MemberValuePair("steps", visitStepsAttribute(attr.stepsAttribute())));
            } else if (attr.useCasesAttribute() != null) {
                pairs.add(new MemberValuePair("useCases", visitUseCasesAttribute(attr.useCasesAttribute())));
            } else if (attr.tagsAttribute() != null) {
                pairs.add(new MemberValuePair("tags", visitTagsAttribute(attr.tagsAttribute())));
            }
        });

        testDocAnnotation.setPairs(pairs);
        method.addAnnotation(testDocAnnotation);
        return null;
    }

    @Override
    public Expression visitDescriptionAttribute(TestDocGrammarParser.DescriptionAttributeContext ctx) {
        return new SingleMemberAnnotationExpr(new Name("Desc"), new StringLiteralExpr(ctx.STRING().getText().replace("\"", "")));
    }

    @Override
    public Expression visitContactAttribute(TestDocGrammarParser.ContactAttributeContext ctx) {
        String name = ctx.contactBody().getChild(2).getText().replace("\"", "");
        String email = ctx.contactBody().getChild(6).getText().replace("\"", "");
        NormalAnnotationExpr contactAnnotation = new NormalAnnotationExpr(new Name("Contact"), new NodeList<>(
            new MemberValuePair("name", new StringLiteralExpr(name)),
            new MemberValuePair("email", new StringLiteralExpr(email))
        ));
        return contactAnnotation;
    }

    @Override
    public Expression visitStepsAttribute(TestDocGrammarParser.StepsAttributeContext ctx) {
        NodeList<Expression> steps = new NodeList<>();
        ctx.step().forEach(step -> {
            // Assuming STRING for value is the third child (index 4), expected is the seventh child (index 8)
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
    public Expression visitUseCasesAttribute(TestDocGrammarParser.UseCasesAttributeContext ctx) {
        NodeList<Expression> useCases = new NodeList<>();
        ctx.useCase().forEach(useCase -> {
            String id = useCase.getChild(4).getText().replace("\"", ""); // assuming id is directly a STRING
            useCases.add(new NormalAnnotationExpr(new Name("UseCase"), new NodeList<>(new MemberValuePair("id", new StringLiteralExpr(id)))));
        });
        return new ArrayInitializerExpr(useCases);
    }

    @Override
    public Expression visitTagsAttribute(TestDocGrammarParser.TagsAttributeContext ctx) {
        NodeList<Expression> tags = new NodeList<>();
        ctx.testTag().forEach(tag -> {
            String value = tag.getChild(4).getText().replace("\"", ""); // assuming value is directly a STRING
            tags.add(new NormalAnnotationExpr(new Name("TestTag"), new NodeList<>(new MemberValuePair("value", new StringLiteralExpr(value)))));
        });
        return new ArrayInitializerExpr(tags);
    }
}