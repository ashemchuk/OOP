package ru.ashemchuk.report;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.ashemchuk.report.model.ReportData;

public class ReportRenderer {
    public void render(String filePath) throws IOException {
        render(filePath, null);
    }

    public void render(String filePath, ReportData data) throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IOException("Config path is empty");
        }

        Path configPath = Path.of(filePath).toAbsolutePath().normalize();
        render(configPath, data);
    }

    void render(Path configPath, ReportData data) throws IOException {
        if (configPath == null) {
            throw new IOException("Config path is null");
        }
        if (!Files.exists(configPath)) {
            throw new IOException("Config file does not exist: " + configPath);
        }

        var config = new CompilerConfiguration();
        config.setScriptBaseClass("groovy.util.DelegatingScript");

        var shell = new GroovyShell(this.getClass().getClassLoader(), new Binding(), config);
        var script = (groovy.util.DelegatingScript) shell.parse(new File(configPath.toString()));

        Path baseDir = configPath.getParent();
        if (baseDir == null) {
            baseDir = Path.of(System.getProperty("user.dir"));
        }

        var builder = new HTMLBuilder();
        if (data != null) {
            builder.setReportData(data);
        }
        script.setDelegate(builder);

        script.run();
    }
}
