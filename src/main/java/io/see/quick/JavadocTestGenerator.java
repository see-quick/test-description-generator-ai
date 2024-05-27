package io.see.quick;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import io.see.quick.grammar.TestDocGrammarLexer;
import io.see.quick.grammar.TestDocGrammarParser;
import io.see.quick.utils.GitUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavadocTestGenerator {

    private static final OpenAiService service = new OpenAiService(System.getenv("OPEN_AI_API_KEY"), Duration.ofMinutes(30));

    /**
     * Regex pattern to match `@TestDoc` annotations with nested parentheses.
     *
     * <p>
     * The pattern works as follows:
     * <ul>
     *   <li><code>@TestDoc\\(</code>: Matches the literal `@TestDoc(`.</li>
     *   <li><code>(?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*</code>: A non-capturing group that matches:
     *     <ul>
     *       <li><code>[^()]*</code>: Any sequence of characters that are not parentheses.</li>
     *       <li><code>\\((?:[^()]*|\\([^()]*\\))*\\)</code>: Nested parentheses, allowing for any content inside them, which can include other nested parentheses.</li>
     *     </ul>
     *   </li>
     *   <li><code>\\)</code>: Matches the closing parenthesis of the `@TestDoc` annotation.</li>
     * </ul>
     * </p>
     */
    private static final String TEST_DOC_PATTERN = "@TestDoc\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\)";

    private static final String EBNFGrammarOfTestMethod = """
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
                        @TestTag(value = "regression")
                }
            )
        """ ;

    private static String generateDocumentation(final String codeSnippet, final String possibleAuthor,
                                                final String possibleAuthorsEmail) {
        final String prompt = "Generate a Java annotation using the `@TestDoc` format based on the provided method signature and EBNF grammar. " +
            "The annotation should document the test method's purpose, steps, use cases, and tags in a structured way that aligns with Java syntax rules. \n:\n" +
            "Method Signature:\n" + codeSnippet + "\n\n" +
            "EBNF Grammar:\n" + EBNFGrammarOfTestMethod + "\n" +
            "Include the possible author as @Contact: " + possibleAuthor + " (" + possibleAuthorsEmail + ")\n" +
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
            CompilationUnit cu = StaticJavaParser.parse(new File(inputFilePath));
            LexicalPreservingPrinter.setup(cu);
            // Now modifications to cu will preserve the original formatting

            cu.accept(new MethodVisitor(inputFilePath), null);

            // Use LexicalPreservingPrinter to convert the CompilationUnit back to string
            String result = LexicalPreservingPrinter.print(cu);

            // Apply the post-processing to format the @TestDoc annotation
            String formattedResult = formatTestDocAnnotation(result);

            try (FileWriter writer = new FileWriter(outputFilePath)) {
                writer.write(formattedResult);
            }

            System.out.println("Javadoc comments added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
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
                final String response = generateDocumentation(methodDeclaration.toString(), authorDetails[0], authorDetails[1]);

                // 2nd part we receive response from OpenAI API
                System.out.println("OUTPUT (length: " + response.length() + ")");
                System.out.println(response);

                // 3rd parse the out from OpenAI API to correct the scheme (TODO: we should make a loop or re-try here...because sometimes happens that GPT does not return test tags etc.)
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

    private static String formatTestDocAnnotation(String code) {
        Pattern pattern = Pattern.compile(TEST_DOC_PATTERN, Pattern.DOTALL);
        Matcher m = pattern.matcher(code);

        StringBuffer sb = new StringBuffer(); // Use StringBuffer to hold the modified string

        while (m.find()) {
            String foundAnnotation = m.group(0);

            // Start the reformatting process
            String formattedAnnotation = "@TestDoc(\n";

            // Description formatting
            formattedAnnotation += formatDescription(foundAnnotation);

            // Contact formatting, ensuring it remains on one line
            formattedAnnotation += formatContact(foundAnnotation);

            // Steps formatting with expected results on the same line
            formattedAnnotation += formatSteps(foundAnnotation);

            // UseCases formatting, one line each
            formattedAnnotation += formatSection(foundAnnotation, "useCases", ",\n");

            // Tags formatting, one line each
            formattedAnnotation += formatSection(foundAnnotation, "tags", "\n");

            formattedAnnotation += "\t)"; // Close the TestDoc annotation

            // Replace the original annotation in the code with the formatted version
            m.appendReplacement(sb, Matcher.quoteReplacement(formattedAnnotation));
        }
        m.appendTail(sb); // Add the remainder of the input sequence

        return sb.toString();
    }

    private static String formatDescription(String text) {
        String regex = "description = (@Desc\\(\".*?\"\\))";
        Pattern p = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = p.matcher(text);

        if (m.find()) {
            String content = m.group(1).trim();  // Capture the content including the @Desc annotation
            // Properly format and return the description line
            return "\t\tdescription = " + content + ",\n";
        }
        return "";  // Return empty if no match is found
    }

    private static String formatSection(String text, String key, String endDelimiter) {
        String regex = key + " = \\{(.*?)\\}";
        Pattern p = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = p.matcher(text);
        if (m.find()) {
            String content = m.group(1).trim();
            content = Arrays.stream(content.split(","))
                .map(String::trim)
                .collect(Collectors.joining(",\n\t\t\t"));
            return "\t\t" + key + " = {\n\t\t\t" + content + "\n\t\t}" + endDelimiter;
        }
        return "";
    }

    private static String formatContact(String text) {
        // contact = @Contact(name = "Test Author", email = "test.author@example.com")
        String regex = "contact\\s*=\\s*@Contact\\(name\\s*=\\s*\"(.+?)\",\\s*email\\s*=\\s*\"(.+?)\"\\)";
        Pattern p = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = p.matcher(text);
        if (m.find()) {
            String name = m.group(1).replaceAll("\n", "").replaceAll("\\s+", " ");
            String email = m.group(2).replaceAll("\n", "").replaceAll("\\s+", " ");
            return "\t\tcontact = @Contact(name = \"" + name + "\", email = \"" + email + "\"),\n";  // Correct indentation and remove excess spacing
        }
        return "";
    }

    private static String formatSteps(String text) {
        String regex = "steps = \\{([^}]*)\\}";
        Pattern p = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = p.matcher(text);
        if (m.find()) {
            String steps = m.group(1).trim();
            // Split steps properly, handle comma after `expected` value
            steps = Arrays.stream(steps.split("@Step"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> "@Step" + s.trim())
                .collect(Collectors.joining("\n\t\t\t"));  // Join steps with a comma and correct formatting

            return "\t\tsteps = {\n\t\t\t" + steps + "\n\t\t},\n";
        }
        return "";
    }

}
