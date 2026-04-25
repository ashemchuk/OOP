package ru.ashemchuk.dsl.model;

import groovy.lang.Closure;
import java.time.LocalDate;

public class Checkpoint {
    private LocalDate date;
    private String description;

    public void checkpoint(Closure<?> closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
