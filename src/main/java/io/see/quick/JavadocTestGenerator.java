package io.see.quick;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import io.see.quick.grammar.TestDocGrammarLexer;
import io.see.quick.grammar.TestDocGrammarParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JavadocTestGenerator {

    private static OpenAiService service = new OpenAiService(System.getenv("OPEN_AI_API_KEY"));

    private static String EBNFGrammarOfTestMethod = """
            TestDocAnnotation ::= '@TestDoc' '(' TestDocBody ')'
            TestDocBody ::= TestDocAttribute { ',' TestDocAttribute }
            TestDocAttribute ::= DescriptionAttribute
                               | ContactAttribute
                               | StepsAttribute
                               | UseCasesAttribute
                               | TagsAttribute
            DescriptionAttribute ::= 'description' '=' '@Desc' '(' String ')'
            ContactAttribute ::= 'contact' '=' '@Contact' '(' ContactBody ')'
            ContactBody ::= 'name' '=' String ',' 'email' '=' String
            StepsAttribute ::= 'steps' '=' '{' Step { ',' Step } '}'
            Step ::= '@Step' '(' 'value' '=' String ',' 'expected' '=' String ')'
            UseCasesAttribute ::= 'useCases' '=' '{' UseCase { ',' UseCase } '}'
            UseCase ::= '@UseCase' '(' 'id' '=' String ')'
            TagsAttribute ::= 'tags' '=' '{' TestTag { ',' TestTag } '}'
            TestTag ::= '@TestTag' '(' 'value' '=' String ')'
            String ::= '"' characters '"'
            characters ::= character { character }
            character ::= letter | digit | special_characters
            letter ::= 'A' | 'B' | ... | 'a' | 'b' | ... | 'z'
            digit ::= '0' | '1' | ... | '9'
            special_characters ::= space | punctuation | ...
            space ::= ' '
            punctuation ::= '.' | ',' | '!' | ...
        """;

    private static final String exampleENBFGrammarOfTestMethod = """
            @TestDoc(
                description = @Desc("Test checking that the application works as expected."),
                contact = @Contact(name = "Jakub Stejskal", email = "ja@kub.io"),
                steps = {
                        @Step(value = "Create object instance", expected = "Instance of an object is created"),
                        @Step(value = "Do a magic trick", expected = "Magic trick is done with success"),
                        @Step(value = "Clean up the test case", expected = "Everything is cleared"),
                        @Step(value = "Do a magic cleanup check", expected = "Everything magically work")
                },
                useCases = {
                        @UseCase(id = "core"),
                        @UseCase(id = "core+"),
                        @UseCase(id = "core+++")
                },
                tags = {
                        @TestTag(value = "default"),
                        @TestTag(value = "regression"),
                }
        """ ;

    private static String generateDocumentation(String codeSnippet) {
        final String prompt = "Generate a Java annotation using the `@TestDoc` format based on the provided method signature and EBNF grammar. " +
            "The annotation should document the test method's purpose, steps, use cases, and tags in a structured way that aligns with Java syntax rules. \n:\n" +
            "Method Signature:\n" + codeSnippet + "\n\n" +
            "EBNF Grammar:\n" + EBNFGrammarOfTestMethod + "\n" +
            "Pattern Format you should follow:\n" + exampleENBFGrammarOfTestMethod + "\n" +
            "Generate ONLY that @TestDoc scheme nothing else!" +
            "Generate it with TEXT only! not ```java code``` Thanks! Much love!";

        System.out.println("INPUT prompt:\n");
        System.out.println(prompt);

        System.out.println("Prompt length is: " + prompt.length());

        // Create the list of messages for the chat request
        List<ChatMessage> messages = Arrays.asList(
            new ChatMessage("system", "You are a helpful assistant."),
            new ChatMessage("user", prompt)
        );

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            .model("gpt-4o") // gpt-3.5-turbo, text-embedding-3-large, babbage-002, davinci-002
            .maxTokens(500) // Adjust token limit based on expected length of the output
            .messages(messages)
            .build();

        ChatCompletionResult completionResult = service.createChatCompletion(request);

        return completionResult.getChoices().get(0).getMessage().getContent().trim();
//        return
//            "@TestDoc(\n" +
//                "    description = @Desc(\"Test ensuring that NetworkPolicy generation is properly influenced by environment variables.\"),\n" +
//                "    contact = @Contact(name = \"Test Author\", email = \"test.author@example.com\"),\n" +
//                "    steps = {\n" +
//                "        @Step(value = \"Set environment variable STRIMZI_NETWORK_POLICY_GENERATION to false\", expected = \"The NetworkPolicy generation is disabled.\"),\n" +
//                "        @Step(value = \"Deploy the Cluster Operator with the modified environment variable\", expected = \"Cluster Operator is deployed successfully.\"),\n" +
//                "        @Step(value = \"Create KafkaNodePool resources\", expected = \"KafkaNodePool resources are created.\"),\n" +
//                "        @Step(value = \"Deploy Kafka cluster with Cruise Control\", expected = \"Kafka cluster with Cruise Control is deployed.\"),\n" +
//                "        @Step(value = \"Deploy Kafka Connect instance\", expected = \"Kafka Connect instance is deployed.\"),\n" +
//                "        @Step(value = \"Verify that no NetworkPolicies are generated by Strimzi\", expected = \"No NetworkPolicies are generated by Strimzi.\")\n" +
//                "    },\n" +
//                "    useCases = {\n" +
//                "        @UseCase(id = \"core-network-policy-generation\")\n" +
//                "    },\n" +
//                "    tags = {\n" +
//                "        @TestTag(value = \"network-policy\"),\n" +
//                "        @TestTag(value = \"environment-variable\")\n" +
//                "    }\n" +
//                ")";
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java JavadocTestGenerator <input-file> <output-file>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            JavaParser javaParser = new JavaParser();
            CompilationUnit cu = javaParser.parse(new File(inputFilePath)).getResult().get();
            cu.accept(new MethodVisitor(), null);

            try (FileWriter writer = new FileWriter(outputFilePath)) {
                writer.write(cu.toString());
            }

            System.out.println("Javadoc comments added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration methodDeclaration, Void arg) {
            if (isTestMethod(methodDeclaration)) {
                // 1st part we input method part into OpenAI API request
                final String response = generateDocumentation(methodDeclaration.toString());

                // 2nd part we receive response from OpenAI API
                System.out.println("OUTPUT (length: " + response.length() + ")");
                System.out.println(response);

                // 3rd parse the out from OpenAI API to correct the scheme
                ParseTree tree = parseResponse(response);

                // 4th go with visitor and build annotation
                AnnotationApplierVisitor visitor = new AnnotationApplierVisitor(methodDeclaration);
                visitor.visit(tree); // Apply the annotations to the method based on the parse tree (// This line applies the parsed annotations to the method)

                System.out.println(tree.toStringTree());
            }
            super.visit(methodDeclaration, arg);
        }

        public static ParseTree parseResponse(String response) {
            try {
                // Replace ANTLRInputStream with CharStreams for newer ANTLR versions
                CharStream inputStream = CharStreams.fromString(response);
                TestDocGrammarLexer lexer = new TestDocGrammarLexer(inputStream);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                TestDocGrammarParser parser = new TestDocGrammarParser(tokens);

                // Add error handling
                parser.removeErrorListeners();  // Remove default console error listener
                parser.addErrorListener(new BaseErrorListener() {
                    @Override
                    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                            int line, int charPositionInLine, String msg, RecognitionException e) {
                        System.err.println("Syntax error at line " + line + ":" + charPositionInLine + ": " + msg);
                        throw new IllegalStateException("Failed to parse at line " + line + " due to " + msg, e);
                    }
                });

                // Parse the input using the starting rule.
                return parser.testDocAnnotation(); // Assuming 'testDocAnnotation' is your starting grammar rule
            } catch (Exception e) {
                System.err.println("Error parsing response: " + e.getMessage());
                throw new RuntimeException(e);  // or handle more gracefully
            }
        }

        private boolean isTestMethod(MethodDeclaration n) {
            return n.getAnnotations().stream().anyMatch(annotation -> annotation.getNameAsString().endsWith("Test"));
        }
    }
}
