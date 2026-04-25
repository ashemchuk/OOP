package ru.ashemchuk.dsl.model;

import java.util.List;

public class Config {
    List<Task> tasks;
    List<Group> groups;
    List<Assignment> assignments;
    List<Checkpoint> checkpoints;
    List<ExtraScore> extraScores;



    public Config(List<Task> tasks, List<Group> groups, List<Assignment> assignments, List<Checkpoint> checkpoints, List<ExtraScore> extraScores) {
        this.tasks = tasks;
        this.groups = groups;
        this.assignments = assignments;
        this.checkpoints = checkpoints;
        this.extraScores = extraScores;
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(List<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public List<ExtraScore> getExtraScores() {
        return extraScores;
    }

    public void setExtraScores(List<ExtraScore> extraScores) {
        this.extraScores = extraScores;
    }
}