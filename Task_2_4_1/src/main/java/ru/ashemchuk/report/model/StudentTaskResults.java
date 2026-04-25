package ru.ashemchuk.report.model;

public class StudentTaskResults {
    private String studentName;
    private boolean buildSuccess;
    private boolean docsSuccess;
    private boolean codeStyleSuccess;
    private int styleViolations;
    private TestsResult testsResult;
    private double extraScore;
    private double totalScore;

    public StudentTaskResults() {}

    public StudentTaskResults(String studentName, boolean buildSuccess, boolean docsSuccess,
                              boolean codeStyleSuccess, TestsResult testsResult,
                              double extraScore, double totalScore) {
        this(studentName, buildSuccess, docsSuccess, codeStyleSuccess, 0, testsResult, extraScore, totalScore);
    }

    public StudentTaskResults(String studentName, boolean buildSuccess, boolean docsSuccess,
                              boolean codeStyleSuccess, int styleViolations, TestsResult testsResult,
                              double extraScore, double totalScore) {
        this.studentName = studentName;
        this.buildSuccess = buildSuccess;
        this.docsSuccess = docsSuccess;
        this.codeStyleSuccess = codeStyleSuccess;
        this.styleViolations = styleViolations;
        this.testsResult = testsResult;
        this.extraScore = extraScore;
        this.totalScore = totalScore;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isBuildSuccess() {
        return buildSuccess;
    }

    public void setBuildSuccess(boolean buildSuccess) {
        this.buildSuccess = buildSuccess;
    }

    public boolean isDocsSuccess() {
        return docsSuccess;
    }

    public void setDocsSuccess(boolean docsSuccess) {
        this.docsSuccess = docsSuccess;
    }

    public boolean isCodeStyleSuccess() {
        return codeStyleSuccess;
    }

    public void setCodeStyleSuccess(boolean codeStyleSuccess) {
        this.codeStyleSuccess = codeStyleSuccess;
    }

    public TestsResult getTestsResult() {
        return testsResult;
    }

    public void setTestsResult(TestsResult testsResult) {
        this.testsResult = testsResult;
    }

    public double getExtraScore() {
        return extraScore;
    }

    public void setExtraScore(double extraScore) {
        this.extraScore = extraScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}
