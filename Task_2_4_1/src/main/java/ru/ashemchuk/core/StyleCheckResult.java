package ru.ashemchuk.core;

public class StyleCheckResult {
    private final boolean success;
    private final int violationCount;

    public StyleCheckResult(boolean success, int violationCount) {
        this.success = success;
        this.violationCount = violationCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getViolationCount() {
        return violationCount;
    }
}