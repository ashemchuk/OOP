package ru.ashemchuk.report;

import groovy.xml.MarkupBuilder;
import ru.ashemchuk.report.model.GroupTasks;
import ru.ashemchuk.report.model.ReportData;
import ru.ashemchuk.report.model.StudentTask;
import ru.ashemchuk.report.model.StudentTaskResults;
import java.io.PrintWriter;
import java.util.List;

public class HTMLBuilder extends MarkupBuilder {
    private ReportData reportData;

    public HTMLBuilder() {
        super(new PrintWriter(System.out)); // output to standard output
    }

    public void setReportData(ReportData reportData) {
        this.reportData = reportData;
    }

    public List<GroupTasks> reportByGroup() {
        if (reportData == null) {
            return List.of();
        }
        return reportData.getGroups();
    }

    public List<StudentTask> reportByStudent(GroupTasks group) {
        if (group == null) {
            return List.of();
        }
        return group.getTasks();
    }

    public List<StudentTaskResults> reportByTask(StudentTask task) {
        if (task == null) {
            return List.of();
        }
        return task.getStudentTaskResults();
    }

    // Helper method to format boolean as "+" or "-"
    public String formatBoolean(boolean value) {
        return value ? "+" : "-";
    }

    // Helper method to format test results as "passed/failed/skipped"
    public String formatTestResults(StudentTaskResults result) {
        if (result.getTestsResult() == null) {
            return "0/0/0";
        }
        var tr = result.getTestsResult();
        return tr.getPassed() + "/" + tr.getFailed() + "/" + tr.getSkipped();
    }
}
