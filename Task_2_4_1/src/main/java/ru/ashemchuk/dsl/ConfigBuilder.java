package ru.ashemchuk.dsl;

import groovy.lang.Closure;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.ashemchuk.dsl.model.Assignment;
import ru.ashemchuk.dsl.model.Checkpoint;
import ru.ashemchuk.dsl.model.Config;
import ru.ashemchuk.dsl.model.ExtraScore;
import ru.ashemchuk.dsl.model.Group;
import ru.ashemchuk.dsl.model.Student;
import ru.ashemchuk.dsl.model.Task;

public class ConfigBuilder {
    List<Task> tasks;
    List<Group> groups;
    List<Assignment> assignments;
    List<Checkpoint> checkpoints;
    List<ExtraScore> extraScores;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public void groups(Closure<?> closure){
        this.groups = new ArrayList<>();
        closure.setDelegate(this);
        closure.call();
    }
    public void group(Closure<?> closure){
        Group group = new Group();
        closure.setDelegate(group);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        this.groups.add(group);
    }
    public void tasks(Closure<?> closure){
        this.tasks = new ArrayList<>();
        closure.setDelegate(this);
        closure.call();
    }

    public void task(Closure<?> closure){
        Task task = new Task();
        closure.setDelegate(task);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        this.tasks.add(task);
    }

    public void assignments(Closure<?> closure){
        this.assignments = new ArrayList<>();
        closure.setDelegate(this);
        closure.call();
    }

    public void assignment(Closure<?> closure){
        Assignment assignment = new Assignment();
        closure.setDelegate(assignment);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        this.assignments.add(assignment);
    }

    public void checkpoints(Closure<?> closure){
        this.checkpoints = new ArrayList<>();
        closure.setDelegate(this);
        closure.call();
    }

    public void checkpoint(Closure<?> closure){
        Checkpoint checkpoint = new Checkpoint();
        closure.setDelegate(checkpoint);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        this.checkpoints.add(checkpoint);
    }
    public void extraScores(Closure<?> closure){
        this.extraScores = new ArrayList<>();
        closure.setDelegate(this);
        closure.call();
    }

    public void extraScore(Closure<?> closure){
        ExtraScore extraScore = new ExtraScore();
        closure.setDelegate(extraScore);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        this.extraScores.add(extraScore);
    }

    public LocalDate date(String date) {
        return LocalDate.parse(
            date, DATE_FORMATTER);
    }

    public Config build() {
        resolveAssignments();
        return new Config(tasks, groups, assignments, checkpoints, extraScores);
    }

    private void resolveAssignments() {
        if (assignments == null) return;
        // Build maps for quick lookup
        Map<String, Task> taskMap = tasks.stream()
            .collect(Collectors.toMap(Task::getId, t -> t));
        Map<String, Student> studentMap = new HashMap<>();
        for (Group group : groups) {
            if (group.getStudents() != null) {
                for (Student student : group.getStudents()) {
                    studentMap.put(student.getNicknameGH(), student);
                }
            }
        }
        for (Assignment assignment : assignments) {
            Task task = taskMap.get(assignment.getTaskId());
            Student student = studentMap.get(assignment.getStudentNicknameGH());
            assignment.setTask(task);
            assignment.setStudent(student);
        }
    }
}
