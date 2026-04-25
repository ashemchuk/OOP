package ru.ashemchuk.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class BuildRunner {
    private final long timeoutSeconds;

    public BuildRunner(long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public BuildRunner() {
        this(120); // default 2 minutes
    }

    /**
     * Run build in the given repository directory.
     * @param repoDir path to the repository root
     * @return true if build succeeded (exit code 0), false otherwise
     * @throws IOException if process fails to start or times out
     */
    public boolean runBuild(Path repoDir) throws IOException {
        String gradleCommand = determineGradleCommand(repoDir);
        ProcessBuilder pb = new ProcessBuilder(gradleCommand, "build");
        pb.directory(repoDir.toFile());
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            if (!finished) {
                process.destroy();
                throw new IOException("Build timed out after " + timeoutSeconds + " seconds");
            }
            return process.exitValue() == 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Build interrupted", e);
        }
    }

    private String determineGradleCommand(Path repoDir) {
        Path gradlew = repoDir.resolve("gradlew");
        if (Files.exists(gradlew) && Files.isExecutable(gradlew)) {
            return gradlew.toAbsolutePath().toString();
        }
        // fallback to system gradle
        return "gradle";
    }
}