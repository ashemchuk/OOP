package ru.ashemchuk;

public class Variable extends Expression {
    private final String name;

    public Variable (String name) {
        this.name = name;
    }

    @Override
    public String toString () {
        return name;
    }

    public Expression derivative (String var) {
        if (this.name.equals(var)) {
            return new Number(1);
        }
        return new Number(0);
    }

    public Expression eval (String signification) {
        return null;
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
