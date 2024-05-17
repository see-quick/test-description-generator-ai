package io.see.quick;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
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
//        System.out.println("Calling OpenAI");
//
//        // TODO: add a prompt specific stuff... i.e, grammar OUTPUT etc.
//        String prompt = "Generate a TestDoc annotation based on the following method signature and EBNF grammar:\n" +
//            "Method Signature:\n" + codeSnippet + "\n\n" +
//            "EBNF Grammar:\n" + EBNFGrammarOfTestMethod + "\n" +
//            "Pattern Format you should follow:\n" + exampleENBFGrammarOfTestMethod + "\n" +
//            "Generate a TestDoc annotation that fits the method based on the EBNF and the example provided.";
//
//        System.out.println("INPUT prompt:\n");
//        System.out.println(prompt);
//
//        System.out.println("Prompt length is: " + prompt.length());
//
//        // Create the list of messages for the chat request
//        List<ChatMessage> messages = Arrays.asList(
//            new ChatMessage("system", "You are a helpful assistant."),
//            new ChatMessage("user", prompt)
//        );
//
//        ChatCompletionRequest request = ChatCompletionRequest.builder()
//            .model("gpt-4o") // gpt-3.5-turbo, text-embedding-3-large, babbage-002, davinci-002
//            .maxTokens(500) // Adjust token limit based on expected length of the output
//            .messages(messages)
//            .build();
//
//        ChatCompletionResult completionResult = service.createChatCompletion(request);
//
//        return completionResult.getChoices().get(0).getMessage().getContent().trim();

        return
            "@TestDoc(\n" +
                "    description = @Desc(\"Test ensuring that NetworkPolicy generation is properly influenced by environment variables.\"),\n" +
                "    contact = @Contact(name = \"Test Author\", email = \"test.author@example.com\"),\n" +
                "    steps = {\n" +
                "        @Step(value = \"Set environment variable STRIMZI_NETWORK_POLICY_GENERATION to false\", expected = \"The NetworkPolicy generation is disabled.\"),\n" +
                "        @Step(value = \"Deploy the Cluster Operator with the modified environment variable\", expected = \"Cluster Operator is deployed successfully.\"),\n" +
                "        @Step(value = \"Create KafkaNodePool resources\", expected = \"KafkaNodePool resources are created.\"),\n" +
                "        @Step(value = \"Deploy Kafka cluster with Cruise Control\", expected = \"Kafka cluster with Cruise Control is deployed.\"),\n" +
                "        @Step(value = \"Deploy Kafka Connect instance\", expected = \"Kafka Connect instance is deployed.\"),\n" +
                "        @Step(value = \"Verify that no NetworkPolicies are generated by Strimzi\", expected = \"No NetworkPolicies are generated by Strimzi.\")\n" +
                "    },\n" +
                "    useCases = {\n" +
                "        @UseCase(id = \"core-network-policy-generation\")\n" +
                "    },\n" +
                "    tags = {\n" +
                "        @TestTag(value = \"network-policy\"),\n" +
                "        @TestTag(value = \"environment-variable\")\n" +
                "    }\n" +
                ")";
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
        public void visit(MethodDeclaration n, Void arg) {
            if (isTestMethod(n)) {
                // 1st part we input method part into OpenAI API request
                final String response = generateDocumentation(n.toString());

                // 2nd part we receive response from OpenAI API
                System.out.println("OUTPUT (length: " + response.length() + ")");
                System.out.println(response);

                // here should be parser
                ParseTree tree = parseResponse(response);
                System.out.println(tree.toStringTree());

                addCustomAnnotations(n);
            }
            super.visit(n, arg);
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

        private void addCustomAnnotations(MethodDeclaration n) {
//            TestDocDetails details = TestDocDetails.fetchDocumentationDetails(n.getNameAsString());

            // Check if @TestDoc annotation already exists
//            Optional<AnnotationExpr> existingAnnotation = n.getAnnotationByName("TestDoc");
//            // Remove the existing @TestDoc annotation
//            existingAnnotation.ifPresent(n.getAnnotations()::remove);
//
//            NormalAnnotationExpr testDocAnnotation = new NormalAnnotationExpr();
//            testDocAnnotation.setName("TestDoc");
//
//            // Create @Desc annotation for the description
//            NormalAnnotationExpr descAnnotation = new NormalAnnotationExpr();
//            descAnnotation.setName("Desc");
//            descAnnotation.addPair("value", new StringLiteralExpr(details.getDescription()));
//
//            testDocAnnotation.addPair("description", descAnnotation);
//
//            ArrayInitializerExpr stepsArray = new ArrayInitializerExpr();
//            stepsArray.setValues(NodeList.nodeList(
//                Arrays.stream(details.getSteps()).map(step -> createStep(step, "Expected result for " + step)).toArray(NormalAnnotationExpr[]::new)
//            ));
//
//            testDocAnnotation.addPair("steps", stepsArray);
//
//            ArrayInitializerExpr useCasesArray = new ArrayInitializerExpr();
//            useCasesArray.setValues(NodeList.nodeList(
//                Arrays.stream(details.getUseCases()).map(this::createUseCase).toArray(NormalAnnotationExpr[]::new)
//            ));
//
//            testDocAnnotation.addPair("useCases", useCasesArray);
//
//            ArrayInitializerExpr tagsArray = new ArrayInitializerExpr();
//            tagsArray.setValues(NodeList.nodeList(
//                Arrays.stream(details.getTags()).map(this::createTag).toArray(NormalAnnotationExpr[]::new)
//            ));
//
//            testDocAnnotation.addPair("tags", tagsArray);
//
//            n.addAnnotation(testDocAnnotation);
        }


//        private void addCustomAnnotations(MethodDeclaration n) {
//            // Check if @TestDoc annotation already exists
//            Optional<AnnotationExpr> existingAnnotation = n.getAnnotationByName("TestDoc");
//            // Remove the existing @TestDoc annotation
//            existingAnnotation.ifPresent(annotationExpr -> n.getAnnotations().remove(annotationExpr));
//
//            NormalAnnotationExpr testDocAnnotation = new NormalAnnotationExpr();
//            testDocAnnotation.setName("TestDoc");
//
//            // Create @Desc annotation for the description
//            NormalAnnotationExpr descAnnotation = new NormalAnnotationExpr();
//            descAnnotation.setName("Desc");
//            descAnnotation.addPair("value", new StringLiteralExpr("This test case verifies common behaviour of Pod Security profiles."));
//
//            // Add @Desc annotation to the TestDoc annotation
//            testDocAnnotation.addPair("description", descAnnotation);
//
//            ArrayInitializerExpr stepsArray = new ArrayInitializerExpr();
//            stepsArray.setValues(NodeList.nodeList(
//                createStep("Add restricted security profile to the namespace containing all resources, by applying according label", "Namespace is modified"),
//                createStep("Deploy 3 Kafka Clusters, of which 2 will serve as targets and one as a source and for other purposes", "Kafka clusters are deployed"),
//                createStep("Deploy all additional Operands which are to be tested, i.e., KafkaMirrorMaker, KafkaMirrorMaker2, KafkaBridge, KafkaConnect, KafkaConnector.", "All components are deployed targeting respective Kafka Clusters"),
//                createStep("Deploy producer which will produce data into Topic residing in Kafka Cluster serving as Source for KafkaMirrorMakers and is targeted by other Operands", "Messages are sent into KafkaTopic"),
//                createStep("Verify that containers such as Kafka, ZooKeeper, Entity Operator, KafkaBridge has properly set .securityContext", "All containers and Pods have expected properties"),
//                createStep("Verify KafkaConnect and KafkaConnector are working by checking presence of Data in file targeted by FileSink KafkaConnector", "Data are present here"),
//                createStep("Verify Kafka and MirrorMakers by Deploy kafka Consumers in respective target Kafka Clusters targeting KafkaTopics created by mirroring", "Data are present here"),
//                createStep("Deploy Kafka consumer with invalid configuration regarding, i.e., without pod security profile", "Consumer is unable to communicate correctly with Kafka")
//            ));
//
//            testDocAnnotation.addPair("steps", stepsArray);
//
//            // Set the use cases
//            ArrayInitializerExpr useCasesArray = new ArrayInitializerExpr();
//            useCasesArray.setValues(NodeList.nodeList(
//                createUseCase("core"),
//                createUseCase("core+"),
//                createUseCase("core+++")
//            ));
//            testDocAnnotation.addPair("useCases", useCasesArray);
//
//            // Set the tags
//            ArrayInitializerExpr tagsArray = new ArrayInitializerExpr();
//            tagsArray.setValues(NodeList.nodeList(
//                createTag("default"),
//                createTag("regression")
//            ));
//            testDocAnnotation.addPair("tags", tagsArray);
//
//            n.addAnnotation(testDocAnnotation);
//        }

        private NormalAnnotationExpr createStep(String value, String expected) {
            NormalAnnotationExpr stepAnnotation = new NormalAnnotationExpr();
            stepAnnotation.setName("Step");
            stepAnnotation.addPair("value", new StringLiteralExpr(value));
            stepAnnotation.addPair("expected", new StringLiteralExpr(expected));
            return stepAnnotation;
        }

        private NormalAnnotationExpr createUseCase(String id) {
            NormalAnnotationExpr useCaseAnnotation = new NormalAnnotationExpr();
            useCaseAnnotation.setName("UseCase");
            useCaseAnnotation.addPair("id", new StringLiteralExpr(id));
            return useCaseAnnotation;
        }

        private NormalAnnotationExpr createTag(String value) {
            NormalAnnotationExpr tagAnnotation = new NormalAnnotationExpr();
            tagAnnotation.setName("TestTag");
            tagAnnotation.addPair("value", new StringLiteralExpr(value));
            return tagAnnotation;
        }

        private boolean isTestMethod(MethodDeclaration n) {
            return n.getAnnotations().stream().anyMatch(annotation -> annotation.getNameAsString().endsWith("Test"));
        }
    }
}
