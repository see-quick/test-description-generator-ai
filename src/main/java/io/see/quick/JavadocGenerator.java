package io.see.quick;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import io.see.quick.format.SuiteDocFormat;
import io.see.quick.format.TestDocFormat;
import io.see.quick.grammar.SuiteDocGrammarLexer;
import io.see.quick.grammar.SuiteDocGrammarParser;
import io.see.quick.grammar.TestDocGrammarLexer;
import io.see.quick.grammar.TestDocGrammarParser;
import io.see.quick.utils.GitUtils;
import io.see.quick.visitors.TestDocAnnotationApplierVisitor;
import io.see.quick.visitors.SuiteDocAnnotationApplierVisitor;
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
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class JavadocGenerator {

    private static final OpenAiService service = new OpenAiService(System.getenv("OPEN_AI_API_KEY"), Duration.ofMinutes(30));

    private static final String EBNF_GRAMMAR_OF_TEST_METHOD = """
            // Lexer rules
            WS              : [ \\t\\r\\n]+ -> skip;
            STRING          : '"' (~["\\\\])* '"';
            NUMBER          : [0-9]+;
            // Parser rules
            testDocAnnotation : '@TestDoc' '(' testDocBody ')';
            testDocBody       : testDocAttribute ( ',' testDocAttribute )* ;
            testDocAttribute  : descriptionAttribute
                              | contactAttribute
                              | stepsAttribute
                              | useCasesAttribute
                              | tagsAttribute
                              ;
            descriptionAttribute : 'description' '=' '@Desc' '(' STRING ')';
            contactAttribute     : 'contact' '=' '@Contact' '(' contactBody ')';
            contactBody          : 'name' '=' STRING ',' 'email' '=' STRING;
            stepsAttribute       : 'steps' '=' '{' step ( ',' step )* '}';
            step                 : '@Step' '(' 'value' '=' STRING ',' 'expected' '=' STRING ')';
            useCasesAttribute    : 'useCases' '=' '{' useCase ( ',' useCase )* '}';
            useCase              : '@UseCase' '(' 'id' '=' STRING ')';
            tagsAttribute        : 'tags' '=' '{' testTag ( ',' testTag )* '}';
            testTag              : '@TestTag' '(' 'value' '=' STRING ')';
        """;

    private static final String EXAMPLE_EBNF_GRAMMAR_OF_TEST_METHOD = """
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
                        @TestTag(value = "regression")
                }
            )
        """ ;

    private static final String EBNF_GRAMMAR_OF_CLASS = """
            // Lexer rules
            WS              : [ \\t\\r\\n]+ -> skip;  // Ignore whitespace, tabs, carriage returns, and newlines
            STRING          : '"' (~["\\\\])* '"';   // Matches quoted strings correctly handling all characters
            NUMBER          : [0-9]+;              // For numbers, if needed
                    
            // Parser rules
            suiteDocAnnotation : '@SuiteDoc' '(' suiteDocBody ')';
            suiteDocBody       : suiteDocAttribute ( ',' suiteDocAttribute )* ;
            suiteDocAttribute  : descriptionAttribute
                              | contactAttribute
                              | beforeTestStepsAttribute
                              | afterTestStepsAttribute
                              | useCasesAttribute
                              | tagsAttribute
                              ;
                    
            descriptionAttribute     : 'description' '=' '@Desc' '(' STRING ')';
            contactAttribute         : 'contact' '=' '@Contact' '(' contactBody ')';
            contactBody              : 'name' '=' STRING ',' 'email' '=' STRING;
            beforeTestStepsAttribute : 'beforeTestSteps' '=' '{' step ( ',' step )* '}';
            afterTestStepsAttribute  : 'afterTestSteps' '=' '{' step ( ',' step )* '}';
            step                     : '@Step' '(' 'value' '=' STRING ',' 'expected' '=' STRING ')';
            useCasesAttribute        : 'useCases' '=' '{' useCase ( ',' useCase )* '}';
            useCase                  : '@UseCase' '(' 'id' '=' STRING ')';
            tagsAttribute            : 'tags' '=' '{' testTag ( ',' testTag )* '}';
            testTag                  : '@TestTag' '(' 'value' '=' STRING ')';
        """;

    private static final String EXAMPLE_OF_EBNF_GRAMMAR_OF_CLASS = """
         @SuiteDoc(
                description = @Desc("My test suite containing various tests"),
                contact = @Contact(name = "Jakub Stejskal", email = "ja@kub.io"),
                beforeTestSteps = {
                        @Step(value = "Deploy uber operator across all namespaces, with custom configuration", expected = "Uber operator is deployed"),
                        @Step(value = "Deploy management Pod for accessing all other Pods", expected = "Management Pod is deployed")
                },
                afterTestSteps = {
                        @Step(value = "Delete management Pod", expected = "Management Pod is deleted"),
                        @Step(value = "Delete uber operator", expected = "Uber operator is deleted")
                },
                useCases = {
                        @UseCase(id = "core")
                },
                tags = {
                        @TestTag(value = "regression"),
                        @TestTag(value = "clients")
                }
            )
        """;

    private static String generateDocumentation(final MethodDeclaration methodDeclaration, final String possibleAuthor,
                                                final String possibleAuthorsEmail) {
        final String codeSnippet = methodDeclaration.toString();
        final String javadoc = methodDeclaration.getJavadocComment().isPresent() ? methodDeclaration.getJavadocComment().toString() : "";
        final String prompt = "Generate a Java annotation using the `@TestDoc` format based on the provided method signature and EBNF grammar. " +
            "The annotation should document the test method's purpose, steps, use cases, and tags in a structured way that aligns with Java syntax rules. \n:\n" +
            "Method Signature:\n" + codeSnippet + "\n\n" +
            "EBNF Grammar:\n" + EBNF_GRAMMAR_OF_TEST_METHOD + "\n" +
            "Include the possible author as @Contact: " + possibleAuthor + " (" + possibleAuthorsEmail + ")\n" +
            "Include at least two @TestTag inside tags\n" +
            "Pattern Format you should follow:\n" + EXAMPLE_EBNF_GRAMMAR_OF_TEST_METHOD + "\n" +
            "And if Javadoc exist to this method use that as inspiration:" + javadoc +
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

    private static String generateDocumentationForSuite(final ClassOrInterfaceDeclaration classOrInterfaceDeclaration, final String possibleAuthor,
                                                final String possibleAuthorsEmail) {
        final String codeSnippet = classOrInterfaceDeclaration.toString();
        final String javadoc = classOrInterfaceDeclaration.getJavadocComment().isPresent() ? classOrInterfaceDeclaration.getJavadocComment().toString() : "";
        final String prompt = "Generate a Java annotation using the `@SuiteDoc` format based on the provided class signature and EBNF grammar. " +
            "The annotation should document the class method's purpose, steps, use cases, and tags in a structured way that aligns with Java syntax rules. \n:\n" +
            "Class Signature:\n" + codeSnippet + "\n\n" +
            "EBNF Grammar:\n" + EBNF_GRAMMAR_OF_CLASS + "\n" +
            "Include the possible author as @Contact: " + possibleAuthor + " (" + possibleAuthorsEmail + ")\n" +
            "Include at least two @TestTag inside tags\n" +
            "Pattern Format you should follow:\n" + EXAMPLE_OF_EBNF_GRAMMAR_OF_CLASS + "\n" +
            "And if Javadoc exist to this method use that as inspiration:" + javadoc +
            "Generate ONLY that @SuiteDoc scheme nothing else!" +
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
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java JavadocTestGenerator <input-file> <output-file>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));
            LexicalPreservingPrinter.setup(cu);
            // Now modifications to cu will preserve the original formatting

            cu.accept(new MethodVisitor(inputFilePath), null);
            cu.accept(new ClassVisitor(inputFilePath), null);

            // Use LexicalPreservingPrinter to convert the CompilationUnit back to string
            String result = LexicalPreservingPrinter.print(cu);

            // Apply the post-processing to format the @TestDoc annotation
            String formattedResult = TestDocFormat.formatTestDoc(result);
            formattedResult = SuiteDocFormat.formatSuiteDoc(formattedResult);

            try (FileWriter writer = new FileWriter(outputFilePath)) {
                writer.write(formattedResult);
            }

            System.out.println("Javadoc comments added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClassVisitor extends VoidVisitorAdapter<Void> {

        private final String filePath;

        public ClassVisitor(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, Void arg) {
            System.out.println("we have been here...." + classOrInterfaceDeclaration.getName());
            System.out.println(classOrInterfaceDeclaration.getJavadoc());

            int lineNumber = classOrInterfaceDeclaration.getBegin().map(Pos -> Pos.line).orElse(-1);
            String[] authorDetails = GitUtils.getAuthorAndEmail(filePath, lineNumber);

            // Now use the author info to modify the @Contact annotation
            System.out.println("Author of class " + classOrInterfaceDeclaration.getName() + ": " + authorDetails[0]);
            // Remove existing @TestDoc annotation if it exists

            classOrInterfaceDeclaration.getAnnotations().removeIf(a -> a.getName().asString().equals("SuiteDoc"));

            // 1st part we input class part into OpenAI API request
            final String response = generateDocumentationForSuite(classOrInterfaceDeclaration, authorDetails[0], authorDetails[1]);

            // 2nd part we receive response from OpenAI API
            System.out.println("OUTPUT (length: " + response.length() + ")");
            System.out.println(response);

            // 3rd parse the out from OpenAI API to correct the scheme
            ParseTree tree = parseSuiteResponse(response);

            // 4th go with visitor and build annotation
            SuiteDocAnnotationApplierVisitor visitor = new SuiteDocAnnotationApplierVisitor(classOrInterfaceDeclaration);
            visitor.visit(tree); // Apply the annotations to the method based on the parse tree (// This line applies the parsed annotations to the method)

            System.out.println(tree.toStringTree());

            super.visit(classOrInterfaceDeclaration, arg);
        }

        public static ParseTree parseSuiteResponse(String response) {
            try {
                CharStream inputStream = CharStreams.fromString(response);
                SuiteDocGrammarLexer lexer = new SuiteDocGrammarLexer(inputStream);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                SuiteDocGrammarParser parser = new SuiteDocGrammarParser(tokens);

                parser.removeErrorListeners();
                parser.addErrorListener(new BaseErrorListener() {
                    @Override
                    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                            int line, int charPositionInLine, String msg, RecognitionException e) {
                        System.err.println("Syntax error at line " + line + ":" + charPositionInLine + ": " + msg);
                        throw new IllegalStateException("Failed to parse at line " + line + " due to " + msg, e);
                    }
                });

                // Parse the input using the starting rule for the SuiteDoc annotation.
                return parser.suiteDocAnnotation();
            } catch (Exception e) {
                System.err.println("Error parsing response: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        private final String filePath;

        public MethodVisitor(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void visit(MethodDeclaration methodDeclaration, Void arg) {
            if (isTestMethod(methodDeclaration)) {
                int lineNumber = methodDeclaration.getBegin().map(Pos -> Pos.line).orElse(-1);
                String[] authorDetails = GitUtils.getAuthorAndEmail(filePath, lineNumber);

                // Now use the author info to modify the @Contact annotation
                System.out.println("Author of method " + methodDeclaration.getName() + ": " + authorDetails[0]);

                // Remove existing @TestDoc annotation if it exists
                methodDeclaration.getAnnotations().removeIf(a -> a.getName().asString().equals("TestDoc"));

                // 1st part we input method part into OpenAI API request
                final String response = generateDocumentation(methodDeclaration, authorDetails[0], authorDetails[1]);

                // 2nd part we receive response from OpenAI API
                System.out.println("OUTPUT (length: " + response.length() + ")");
                System.out.println(response);

                // 3rd parse the out from OpenAI API to correct the scheme
                ParseTree tree = parseResponse(response);

                // 4th go with visitor and build annotation
                TestDocAnnotationApplierVisitor visitor = new TestDocAnnotationApplierVisitor(methodDeclaration);
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
