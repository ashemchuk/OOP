package ru.ashemchuk.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class StyleChecker {
    private final long timeoutSeconds;
    private final int maxViolations;

    public StyleChecker(long timeoutSeconds, int maxViolations) {
        this.timeoutSeconds = timeoutSeconds;
        this.maxViolations = maxViolations;
    }

    public StyleChecker() {
        this(60, 0); // default: zero tolerance
    }

    /**
     * Run checkstyle and determine if style passes.
     * @param repoDir path to the repository root
     * @return true if style passes (violations <= maxViolations), false otherwise
     * @throws IOException if process fails or times out
     */
    public boolean checkStyle(Path repoDir) throws IOException {
        return checkStyleDetailed(repoDir).isSuccess();
    }

    /**
     * Run checkstyle and return detailed result.
     * @param repoDir path to the repository root
     * @return StyleCheckResult with success flag and violation count
     * @throws IOException if process fails or times out
     */
    public StyleCheckResult checkStyleDetailed(Path repoDir) throws IOException {
        String gradleCommand = determineGradleCommand(repoDir);
        ProcessBuilder pb = new ProcessBuilder(gradleCommand, "checkstyleMain");
        pb.directory(repoDir.toFile());
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            if (!finished) {
                process.destroy();
                throw new IOException("Checkstyle timed out after " + timeoutSeconds + " seconds");
            }
            int violations = countViolations(process);
            boolean success = violations <= maxViolations;
            return new StyleCheckResult(success, violations);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Checkstyle interrupted", e);
        }
    }

    private int countViolations(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            int count = 0;
            // Simple heuristic: count lines containing "[WARN]" or "violation" or "Checkstyle"
            Pattern violationPattern = Pattern.compile(".*\\[WARN\\].*|.*violation.*|.*Checkstyle.*", Pattern.CASE_INSENSITIVE);
            while ((line = reader.readLine()) != null) {
                if (violationPattern.matcher(line).matches()) {
                    count++;
                }
            }
            return count;
        }
    }

    private String determineGradleCommand(Path repoDir) {
        Path gradlew = repoDir.resolve("gradlew");
        if (Files.exists(gradlew) && Files.isExecutable(gradlew)) {
            return gradlew.toAbsolutePath().toString();
        }
        return "gradle";
    }
}