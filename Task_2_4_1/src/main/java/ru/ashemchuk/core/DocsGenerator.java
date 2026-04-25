package ru.ashemchuk.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class DocsGenerator {
    private final long timeoutSeconds;

    public DocsGenerator(long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public DocsGenerator() {
        this(60); // default 1 minute
    }

    /**
     * Generate Javadoc documentation.
     * @param repoDir path to the repository root
     * @return true if javadoc task succeeded (exit code 0), false otherwise
     * @throws IOException if process fails to start or times out
     */
    public boolean generateDocs(Path repoDir) throws IOException {
        String gradleCommand = determineGradleCommand(repoDir);
        ProcessBuilder pb = new ProcessBuilder(gradleCommand, "javadoc");
        pb.directory(repoDir.toFile());
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            if (!finished) {
                process.destroy();
                throw new IOException("Javadoc generation timed out after " + timeoutSeconds + " seconds");
            }
            return process.exitValue() == 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Javadoc generation interrupted", e);
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