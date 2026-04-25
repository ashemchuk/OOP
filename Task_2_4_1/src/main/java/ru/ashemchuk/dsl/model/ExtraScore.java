package ru.ashemchuk.dsl.model;

import groovy.lang.Closure;

public class ExtraScore {
    private String studentNicknameGH;
    private String taskId;
    private double score;

    public void extraScore(Closure<?> closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }
    public String getStudentNicknameGH() {
        return studentNicknameGH;
    }

    public void setStudentNicknameGH(String studentNicknameGH) {
        this.studentNicknameGH = studentNicknameGH;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public double getExtraScore() {
        return score;
    }

    public void setExtraScore(double score) {
        this.score = score;
    }
}
