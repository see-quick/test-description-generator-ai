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

    public static String getAuthorEmail(String filePath, int lineNumber) {
        try {
            Process process = Runtime.getRuntime().exec(
                "git blame -e -L " + lineNumber + "," + lineNumber + " -- " + filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                // The email is typically enclosed in <>, extract it
                int emailStart = line.indexOf('<');
                int emailEnd = line.indexOf('>', emailStart);
                if (emailStart != -1 && emailEnd != -1) {
                    return line.substring(emailStart + 1, emailEnd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "email_not_found";
    }

    public static String[] getAuthorAndEmail(String filePath, int lineNumber) {
        String[] authorDetails = new String[2]; // Array to hold author name and email
        try {
            Process process = Runtime.getRuntime().exec(
                "git blame --line-porcelain -L " + lineNumber + "," + lineNumber + " -- " + filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("author ")) {
                    authorDetails[0] = line.substring(7).trim(); // Extract author name
                } else if (line.startsWith("author-mail ")) {
                    authorDetails[1] = line.substring(12).trim().replace("<", "").replace(">", ""); // Extract author email
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Unknown Author", "email_not_found"};
        }
        return authorDetails;
    }

    public static String[] getMostFrequentSigner(String filePath, int startLine, int endLine) {
        String[] mostFrequentSigner = new String[2]; // Array to hold the most frequent signer name and email
        Map<String, Integer> signerCounts = new HashMap<>();
        try {
            // Construct the command
            String command = String.format("git log -L %d,%d:%s --pretty=fuller", startLine, endLine, filePath);
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", command + " | grep 'Signed-off-by'");
            processBuilder.redirectErrorStream(true);  // Redirect errors to the output stream
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Signed-off-by")) {
                    String signer = line.substring(line.indexOf("Signed-off-by:") + "Signed-off-by:".length()).trim();
                    signerCounts.put(signer, signerCounts.getOrDefault(signer, 0) + 1);
                }
            }
            process.waitFor(); // Wait for the process to complete
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Unknown Signer", "email_not_found"};
        }

        // Determine the most frequent signer
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : signerCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentSigner[0] = entry.getKey().split("<")[0].trim(); // Extract name
                mostFrequentSigner[1] = entry.getKey().split("<")[1].replace(">", "").trim(); // Extract email
                maxCount = entry.getValue();
            }
        }

        return mostFrequentSigner;
    }

    public static String getAuthorEmailByName(String authorName) {
        return authorEmailMap.getOrDefault(authorName, "email_not_found@example.com");
    }
}
