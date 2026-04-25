package ru.ashemchuk.report.model;

public class TestsResult {
    private int passed;
    private int failed;
    private int skipped;

    public TestsResult() {}

    public TestsResult(int passed, int failed, int skipped) {
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }
}
