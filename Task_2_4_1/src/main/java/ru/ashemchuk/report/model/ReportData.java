package ru.ashemchuk.report.model;

import java.util.List;

public class ReportData {
    private List<GroupTasks> groups;

    public ReportData(List<GroupTasks> groups) {
        this.groups = groups;
    }

    public List<GroupTasks> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupTasks> groups) {
        this.groups = groups;
    }
}