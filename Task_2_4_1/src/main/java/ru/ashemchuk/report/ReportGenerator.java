package ru.ashemchuk.report;

import ru.ashemchuk.dsl.model.Config;
import ru.ashemchuk.dsl.model.Group;
import ru.ashemchuk.dsl.model.Student;
import ru.ashemchuk.dsl.model.Task;
import ru.ashemchuk.dsl.model.Assignment;
import ru.ashemchuk.dsl.model.ExtraScore;
import ru.ashemchuk.core.*;
import ru.ashemchuk.report.model.*;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReportGenerator {
    private final Config config;
    private final Path workspaceDir;
    private final RepositoryManager repoManager;
    private final BuildRunner buildRunner;
    private final DocsGenerator docsGenerator;
    private final StyleChecker styleChecker;
    private final TestRunner testRunner;
    private final ScoringEngine scoringEngine;

    public ReportGenerator(Config config, Path workspaceDir) {
        this.config = config;
        this.workspaceDir = workspaceDir;
        this.repoManager = new RepositoryManager(workspaceDir);
        this.buildRunner = new BuildRunner();
        this.docsGenerator = new DocsGenerator();
        this.styleChecker = new StyleChecker();
        this.testRunner = new TestRunner();
        this.scoringEngine = new ScoringEngine();
    }

    public ReportData generate() throws IOException {
        List<GroupTasks> groups = new ArrayList<>();

        // Map extra scores by (studentNicknameGH, taskId)
        Map<String, Map<String, Double>> extraScoreMap = new HashMap<>();
        for (ExtraScore extra : config.getExtraScores()) {
            extraScoreMap
                .computeIfAbsent(extra.getStudentNicknameGH(), k -> new HashMap<>())
                .put(extra.getTaskId(), extra.getExtraScore());
        }

        // Process each group
        for (Group group : config.getGroups()) {
            List<StudentTask> tasks = new ArrayList<>();
            // For each task defined in config (or only assigned tasks?)
            // We'll process assignments that belong to this group's students
            for (Task task : config.getTasks()) {
                List<StudentTaskResults> studentResults = new ArrayList<>();
                // Find assignments for this task
                for (Assignment assignment : config.getAssignments()) {
                    if (!assignment.getTask().getId().equals(task.getId())) {
                        continue;
                    }
                    Student student = assignment.getStudent();
                    // Check if student belongs to this group
                    if (!group.getStudents().contains(student)) {
                        continue;
                    }
                    // Evaluate student's repository for this task
                    StudentTaskResults results = evaluateStudentTask(student, task, extraScoreMap);
                    studentResults.add(results);
                }
                if (!studentResults.isEmpty()) {
                    tasks.add(new StudentTask(task.getName(), studentResults));
                }
            }
            if (!tasks.isEmpty()) {
                groups.add(new GroupTasks(group.getName(), tasks));
            }
        }
        return new ReportData(groups);
    }

    private StudentTaskResults evaluateStudentTask(Student student, Task task,
                                                   Map<String, Map<String, Double>> extraScoreMap) throws IOException {
        // Clone repository
        Path repoDir = repoManager.ensureRepository(student.getRepoURL(), student.getNicknameGH());
        repoManager.checkoutDefaultBranch(repoDir);

        // Determine project directory: task subdirectory inside repo
        Path projectDir = repoDir.resolve(task.getId());
        boolean subdirExists = java.nio.file.Files.exists(projectDir) && java.nio.file.Files.isDirectory(projectDir);
        if (!subdirExists) {
            System.err.println("WARNING: Task subdirectory " + task.getId() + " not found in " + repoDir + ", using repository root");
            projectDir = repoDir;
        } else {
            System.out.println("Using task subdirectory: " + projectDir);
        }

        // Run build
        System.out.println("Running build for " + student.getNicknameGH() + " task " + task.getId() + " in " + projectDir);
        boolean buildSuccess = buildRunner.runBuild(projectDir);
        System.out.println("Build success: " + buildSuccess);
        boolean docsSuccess = false;
        boolean styleSuccess = false;
        TestRunner.TestResults testResults = new TestRunner.TestResults(0, 0, 0);

        if (buildSuccess) {
            System.out.println("Generating documentation...");
            docsSuccess = docsGenerator.generateDocs(projectDir);
            System.out.println("Docs success: " + docsSuccess);
            System.out.println("Checking style...");
            styleSuccess = styleChecker.checkStyle(projectDir);
            System.out.println("Style success: " + styleSuccess);
            System.out.println("Running tests...");
            testResults = testRunner.runTests(projectDir);
            System.out.println("Test results: " + testResults.passed + " passed, " + testResults.failed + " failed, " + testResults.skipped + " skipped");
        }

        // Determine extra score
        double extraScore = extraScoreMap
            .getOrDefault(student.getNicknameGH(), Map.of())
            .getOrDefault(task.getId(), 0.0);
        System.out.println("Extra score: " + extraScore);

        // Compute total score (submission date unknown, assume on time)
        double totalScore = scoringEngine.computeScore(task, buildSuccess, docsSuccess, styleSuccess,
                                                       testResults, extraScore, null);
        System.out.println("Total score: " + totalScore);

        // Convert test results to TestsResult
        TestsResult testsResult = new TestsResult(testResults.passed, testResults.failed, testResults.skipped);

        return new StudentTaskResults(student.getName(), buildSuccess, docsSuccess, styleSuccess,
                                      testsResult, extraScore, totalScore);
    }
}