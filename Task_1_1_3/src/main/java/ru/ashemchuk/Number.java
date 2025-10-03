package ru.ashemchuk;

public class Number extends Expression {
    private final int num;

    public Number (int num) {
        this.num = num;
    }

    @Override
    public String toString () {
        return Integer.toString(num);
    }

    @Override
    public Expression derivative (String var) {
        return new Number(0);
    }

    @Override
    public Expression eval(String signification) {
        return null;
    }

    @Override
    public Expression simplify() {
        return null;
    }

}
