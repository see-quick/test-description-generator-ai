package io.see.quick.format;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SuiteDocFormat {

    /**
     * Regex pattern to match `@SuiteDoc` annotations with nested parentheses.
     */
    private static final String SUITE_DOC_PATTERN = "@SuiteDoc\\((?:[^()]*|\\((?:[^()]*|\\([^()]*\\))*\\))*\\)";
    private static final String FOUR_SPACES = "    ";
    private static final String EIGHT_SPACES = "        ";
    private static final String TWELVE_SPACES = "            ";

    private SuiteDocFormat() {}

    public static String formatSuiteDoc(final String code) {
        return formatSuiteDocAnnotation(code);
    }

    private static String formatSuiteDocAnnotation(String code) {
        Pattern pattern = Pattern.compile(SUITE_DOC_PATTERN, Pattern.DOTALL);
        Matcher m = pattern.matcher(code);

        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            String foundAnnotation = m.group(0);

            // Start the reformatting process
            String formattedAnnotation = "@SuiteDoc(\n";

            // Description formatting
            formattedAnnotation += formatDescription(foundAnnotation);

            // Contact formatting, ensuring it remains on one line
            formattedAnnotation += formatContact(foundAnnotation);

            // Before and After Test Steps formatting
            formattedAnnotation += formatSteps(foundAnnotation, "beforeTestSteps");
            formattedAnnotation += formatSteps(foundAnnotation, "afterTestSteps");

            // UseCases formatting, one line each
            formattedAnnotation += formatSection(foundAnnotation, "useCases", ",\n");

            // Tags formatting, one line each
            formattedAnnotation += formatSection(foundAnnotation, "tags", "\n");

            formattedAnnotation += ")"; // Close the SuiteDoc annotation

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
            return FOUR_SPACES + "description = " + content + ",\n";
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
                .collect(Collectors.joining(",\n" + EIGHT_SPACES));
            return FOUR_SPACES + key + " = {\n" + EIGHT_SPACES + content + "\n" + FOUR_SPACES + "}" + endDelimiter;
        }
        return "";
    }

    private static String formatContact(String text) {
        String regex = "contact\\s*=\\s*@Contact\\(name\\s*=\\s*\"(.+?)\",\\s*email\\s*=\\s*\"(.+?)\"\\)";
        Pattern p = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = p.matcher(text);
        if (m.find()) {
            String name = m.group(1).replaceAll("\n", "").replaceAll("\\s+", " ");
            String email = m.group(2).replaceAll("\n", "").replaceAll("\\s+", " ");
            return FOUR_SPACES + "contact = @Contact(name = \"" + name + "\", email = \"" + email + "\"),\n";
        }
        return "";
    }

    private static String formatSteps(String text, String key) {
        String regex = key + " = \\{([^}]*)\\}";
        Pattern p = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = p.matcher(text);
        if (m.find()) {
            String steps = m.group(1).trim();
            steps = Arrays.stream(steps.split("@Step"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> "@Step" + s.trim())
                .collect(Collectors.joining("\n" + EIGHT_SPACES));  // Format steps with proper indentation

            return FOUR_SPACES + key + " = {\n" + EIGHT_SPACES + steps + "\n" + FOUR_SPACES + "},\n";
        }
        return "";
    }
}
