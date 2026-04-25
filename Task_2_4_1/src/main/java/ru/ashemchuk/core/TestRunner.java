package ru.ashemchuk.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestRunner {
    private final long timeoutSeconds;

    public TestRunner(long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public TestRunner() {
        this(180); // default 3 minutes
    }

    public static class TestResults {
        public final int passed;
        public final int failed;
        public final int skipped;

        public TestResults(int passed, int failed, int skipped) {
            this.passed = passed;
            this.failed = failed;
            this.skipped = skipped;
        }
    }

    /**
     * Run tests and collect results.
     * @param repoDir path to the repository root
     * @return TestResults with counts
     * @throws IOException if process fails or times out
     */
    public TestResults runTests(Path repoDir) throws IOException {
        String gradleCommand = determineGradleCommand(repoDir);
        ProcessBuilder pb = new ProcessBuilder(gradleCommand, "test");
        pb.directory(repoDir.toFile());
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            if (!finished) {
                process.destroy();
                throw new IOException("Test execution timed out after " + timeoutSeconds + " seconds");
            }
            // Parse test results from XML
            return parseTestResults(repoDir);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Test execution interrupted", e);
        }
    }

    private TestResults parseTestResults(Path repoDir) throws IOException {
        Path testResultsDir = repoDir.resolve("build/test-results/test");
        if (!Files.exists(testResultsDir)) {
            // No test results generated (maybe no tests)
            return new TestResults(0, 0, 0);
        }
        int[] totalPassed = new int[1];
        int[] totalFailed = new int[1];
        int[] totalSkipped = new int[1];
        try {
            Files.list(testResultsDir)
                 .filter(p -> p.getFileName().toString().endsWith(".xml"))
                 .forEach(xmlFile -> {
                     try {
                         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                         DocumentBuilder builder = factory.newDocumentBuilder();
                         Document doc = builder.parse(xmlFile.toFile());
                         NodeList testcases = doc.getElementsByTagName("testcase");
                         for (int i = 0; i < testcases.getLength(); i++) {
                             Element testcase = (Element) testcases.item(i);
                             if (testcase.getElementsByTagName("failure").getLength() > 0) {
                                 totalFailed[0]++;
                             } else if (testcase.getElementsByTagName("skipped").getLength() > 0) {
                                 totalSkipped[0]++;
                             } else {
                                 totalPassed[0]++;
                             }
                         }
                     } catch (ParserConfigurationException | SAXException | IOException e) {
                         // ignore malformed XML
                     }
                 });
        } catch (IOException e) {
            // ignore
        }
        return new TestResults(totalPassed[0], totalFailed[0], totalSkipped[0]);
    }

    private String determineGradleCommand(Path repoDir) {
        Path gradlew = repoDir.resolve("gradlew");
        if (Files.exists(gradlew) && Files.isExecutable(gradlew)) {
            return gradlew.toAbsolutePath().toString();
        }
        return "gradle";
    }
}