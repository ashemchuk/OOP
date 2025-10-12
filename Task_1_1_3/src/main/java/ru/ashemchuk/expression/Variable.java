package ru.ashemchuk.expression;

import ru.ashemchuk.parser.Signification;

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
            return new ru.ashemchuk.expression.Number(1);
        }
        return new ru.ashemchuk.expression.Number(0);
    }

    public Expression eval (String signification) {
        Signification s = new Signification();
        s.parseSignification(signification);
        Integer value = s.getValue(name);
        if (value != null) {
            return new Number(value);
        }
        return new Variable(this.name);
    }

    @Override
    public Expression simplify() {
        return new Variable(this.name);
    }

    public String getName() {
        return name;
    }
}
