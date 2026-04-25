package ru.ashemchuk.dsl.model;

import groovy.lang.Closure;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private List<Student> students;

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void students(Closure<?> closure) {
        this.students = new ArrayList<Student>();
        closure.setDelegate(this);
        closure.call();
    }
    public void student(Closure<?> closure) {
        var student = new Student();
        closure.setDelegate(student);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        this.students.add(student);
    }
}
