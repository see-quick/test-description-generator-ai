package io.see.quick.format;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestDocFormat {

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
    private static final String FOUR_SPACES = " ".repeat(4);
    private static final String EIGHT_SPACES = " ".repeat(8);
    private static final String TWELVE_SPACES = " ".repeat(12);

    private TestDocFormat() {}

    public static String formatTestDoc(final String code) {
        return formatTestDocAnnotation(code);
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

            formattedAnnotation += FOUR_SPACES + ")"; // Close the TestDoc annotation

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
            return EIGHT_SPACES + "description = " + content + ",\n";
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
                .collect(Collectors.joining(",\n" + TWELVE_SPACES));
            return EIGHT_SPACES + key + " = {\n" + TWELVE_SPACES + content + "\n" + EIGHT_SPACES + "}" + endDelimiter;
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
            return EIGHT_SPACES + "contact = @Contact(name = \"" + name + "\", email = \"" + email + "\"),\n";  // Correct indentation and remove excess spacing
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
                .collect(Collectors.joining("\n" + TWELVE_SPACES));  // Join steps with a comma and correct formatting

            return EIGHT_SPACES + "steps = {\n" + TWELVE_SPACES + steps + "\n" + EIGHT_SPACES + "},\n";
        }
        return "";
    }

}
