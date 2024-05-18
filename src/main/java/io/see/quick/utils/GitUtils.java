package io.see.quick.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class GitUtils {

    private static final Map<String, String> authorEmailMap = new HashMap<>();

    static {
        // Pre-populate the map with author names and emails
        authorEmailMap.put("see-quick", "maros.orsak159@gmail.com");

        // TODO: other mails
        authorEmailMap.put("gandalf", "wizard@greytower.com");
        authorEmailMap.put("frodo", "ringbearer@shiremail.com");
        authorEmailMap.put("legolas", "elfprince@mirkwood.net");
        authorEmailMap.put("gimli", "axe.master@lonelymountain.org");
        authorEmailMap.put("aragorn", "strider@rangerpath.com");
        authorEmailMap.put("sauron", "the.eye@mordor.gov");
    }

    public static String getAuthor(String filePath, int lineNumber) {
        try {
            Process process = Runtime.getRuntime().exec(
                "git blame -L " + lineNumber + "," + lineNumber + " -- " + filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                // Extract the author from the git blame output
                int start = line.indexOf('(');
                int end = line.indexOf('2', start); // Assuming the date starts with '20'
                return line.substring(start + 1, end).trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown Author";
    }

    public static String getAuthorEmailByName(String authorName) {
        return authorEmailMap.getOrDefault(authorName, "email_not_found@example.com");
    }
}
