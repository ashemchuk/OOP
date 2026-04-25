package ru.ashemchuk;

import java.io.IOException;
import java.nio.file.Path;
import ru.ashemchuk.dsl.ConfigLoader;
import ru.ashemchuk.report.ReportGenerator;
import ru.ashemchuk.report.ReportRenderer;

public class Main {
    public static void main(String[] args) throws IOException {
        // Load configuration
        ConfigLoader configLoader = new ConfigLoader();
        var config = configLoader.loadConfig("config.groovy");

        // Create a temporary workspace directory for cloning repositories
        Path workspaceDir = Path.of(System.getProperty("user.dir"), "workspace");
        // Ensure directory exists
        workspaceDir.toFile().mkdirs();

        // Generate report data
        var reportGenerator = new ReportGenerator(config, workspaceDir);
        var reportData = reportGenerator.generate();

        // Render HTML report
        var reportRenderer = new ReportRenderer();
        reportRenderer.render("report.groovy", reportData);
    }
}