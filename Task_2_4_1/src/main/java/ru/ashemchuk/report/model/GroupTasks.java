package ru.ashemchuk.report.model;

import java.util.List;

public class GroupTasks {
    private String groupName;
    private List<StudentTask> tasks;

    public GroupTasks() {}

    public GroupTasks(String groupName, List<StudentTask> tasks) {
        this.groupName = groupName;
        this.tasks = tasks;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<StudentTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<StudentTask> tasks) {
        this.tasks = tasks;
    }
}
