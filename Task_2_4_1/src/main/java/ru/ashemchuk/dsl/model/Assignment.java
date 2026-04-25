package ru.ashemchuk.dsl.model;

import groovy.lang.Closure;

public class Assignment {
    private String taskId;
    private String studentNicknameGH;
    private Task task;
    private Student student;

    public void assignment(Closure<?> closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStudentNicknameGH() {
        return studentNicknameGH;
    }

    public void setStudentNicknameGH(String studentNicknameGH) {
        this.studentNicknameGH = studentNicknameGH;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // Helper to resolve references after config is loaded
    public void resolve(Task task, Student student) {
        this.task = task;
        this.student = student;
    }
}
