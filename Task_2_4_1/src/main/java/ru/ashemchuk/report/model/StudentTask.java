package ru.ashemchuk.report.model;

import java.util.List;

public class StudentTask {
    private String taskName;
    private List<StudentTaskResults> studentTaskResults;

    public StudentTask() {}

    public StudentTask(String taskName, List<StudentTaskResults> studentTaskResults) {
        this.taskName = taskName;
        this.studentTaskResults = studentTaskResults;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<StudentTaskResults> getStudentTaskResults() {
        return studentTaskResults;
    }

    public void setStudentTaskResults(List<StudentTaskResults> studentTaskResults) {
        this.studentTaskResults = studentTaskResults;
    }
}
