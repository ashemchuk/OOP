package ru.ashemchuk.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class RepositoryManager {
    private final Path baseDir;

    public RepositoryManager(Path baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * Clone or update repository for given student.
     * @param repoUrl URL of the repository
     * @param nickname GitHub nickname (used for directory name)
     * @return Path to the cloned repository directory
     * @throws IOException if cloning fails
     */
    public Path ensureRepository(String repoUrl, String nickname) throws IOException {
        Path repoDir = baseDir.resolve(nickname);
        if (Files.exists(repoDir)) {
            // Already cloned, maybe pull updates? For simplicity, we assume it's up-to-date.
            // Could run git pull but we'll skip for now.
            return repoDir;
        }
        // Clone the repository
        ProcessBuilder pb = new ProcessBuilder("git", "clone", repoUrl, repoDir.toString());
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            boolean finished = process.waitFor(60, TimeUnit.SECONDS);
            if (!finished) {
                process.destroy();
                throw new IOException("Git clone timed out");
            }
            int exitCode = process.exitValue();
            if (exitCode != 0) {
                throw new IOException("Git clone failed with exit code " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Git clone interrupted", e);
        }
        return repoDir;
    }

    /**
     * Checkout the default branch (master/main).
     */
    public void checkoutDefaultBranch(Path repoDir) throws IOException {
        // Determine default branch: try master, then main
        String[] branches = {"master", "main"};
        for (String branch : branches) {
            if (branchExists(repoDir, branch)) {
                checkoutBranch(repoDir, branch);
                return;
            }
        }
        throw new IOException("Neither master nor main branch found");
    }

    private boolean branchExists(Path repoDir, String branch) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("git", "show-ref", "--verify", "refs/heads/" + branch);
        pb.directory(repoDir.toFile());
        try {
            Process process = pb.start();
            return process.waitFor(5, TimeUnit.SECONDS) && process.exitValue() == 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while checking branch", e);
        }
    }

    private void checkoutBranch(Path repoDir, String branch) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("git", "checkout", branch);
        pb.directory(repoDir.toFile());
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished) {
                process.destroy();
                throw new IOException("Git checkout timed out");
            }
            if (process.exitValue() != 0) {
                throw new IOException("Git checkout failed");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Git checkout interrupted", e);
        }
    }
}