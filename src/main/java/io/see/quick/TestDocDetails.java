package io.see.quick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDocDetails {

    private String description;
    private String contactName;
    private String contactEmail;
    private List<StepDetail> steps;
    private List<String> useCases;
    private List<String> tags;

    // Constructors
    public TestDocDetails(String description, String contactName, String contactEmail, List<StepDetail> steps, List<String> useCases, List<String> tags) {
        this.description = description;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.steps = steps;
        this.useCases = useCases;
        this.tags = tags;
    }

    // Parsing method to create an instance from a string
    public static TestDocDetails fromString(String annotationString) {
        String description = parseDescription(annotationString);
        String contactName = parseContactDetail(annotationString, "name");
        String contactEmail = parseContactDetail(annotationString, "email");
        List<StepDetail> steps = parseSteps(annotationString);
        List<String> useCases = parseUseCases(annotationString);
        List<String> tags = parseTags(annotationString);

        return new TestDocDetails(description, contactName, contactEmail, steps, useCases, tags);
    }

    private static String parseDescription(String annotationString) {
        return parseBetween(parseBetween(annotationString, "description = @Desc(", "),"), "\"", "\"");
    }

    private static String parseContactDetail(String annotationString, String detail) {
        String contactStr = parseBetween(annotationString, "contact = @Contact(", "),");
        return parseBetween(contactStr, detail + " = \"", "\"");
    }

    private static List<StepDetail> parseSteps(String annotationString) {
        List<StepDetail> steps = new ArrayList<>();
        String stepsStr = parseBetween(parseBetween(annotationString, "steps = {", "}"), "@Step(", ")");
        Arrays.stream(stepsStr.split("@Step"))
            .filter(step -> !step.trim().isEmpty())
            .forEach(step -> {
                String value = parseBetween(step, "value = \"", "\",");
                String expected = parseBetween(step, "expected = \"", "\"");
                steps.add(new StepDetail(value, expected));
            });
        return steps;
    }

    private static List<String> parseUseCases(String annotationString) {
        return parseRepeatedValues(annotationString, "@UseCase(id = \"", "\")");
    }

    private static List<String> parseTags(String annotationString) {
        return parseRepeatedValues(annotationString, "@TestTag(value = \"", "\")");
    }

    private static List<String> parseRepeatedValues(String annotationString, String startDelimiter, String endDelimiter) {
        List<String> values = new ArrayList<>();
        int startIdx = annotationString.indexOf(startDelimiter);
        while (startIdx != -1) {
            int endIdx = annotationString.indexOf(endDelimiter, startIdx + startDelimiter.length());
            if (endIdx == -1) break; // Prevents parsing errors if the closing delimiter is missing
            String content = annotationString.substring(startIdx + startDelimiter.length(), endIdx).trim();
            values.add(content);
            startIdx = annotationString.indexOf(startDelimiter, endIdx + endDelimiter.length());
        }
        return values;
    }

    private static String parseBetween(String source, String start, String end) {
        int startIndex = source.indexOf(start);
        if (startIndex == -1) {
            return "Start delimiter not found";  // or throw new IllegalArgumentException("Start delimiter not found");
        }
        startIndex += start.length();

        int endIndex = source.indexOf(end, startIndex);
        if (endIndex == -1) {
            return "End delimiter not found";  // or throw new IllegalArgumentException("End delimiter not found");
        }

        return source.substring(startIndex, endIndex);
    }


    // Nested class for step details
    public static class StepDetail {
        private String value;
        private String expected;

        public StepDetail(String value, String expected) {
            this.value = value;
            this.expected = expected;
        }

        public String getValue() {
            return value;
        }

        public String getExpected() {
            return expected;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setExpected(String expected) {
            this.expected = expected;
        }
    }

    public String getDescription() {
        return description;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public List<StepDetail> getSteps() {
        return steps;
    }

    public List<String> getUseCases() {
        return useCases;
    }

    public List<String> getTags() {
        return tags;
    }

    // Existing nested classes and other methods
}
