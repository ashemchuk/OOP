package ru.ashemchuk.expression;

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
        return new Mul(new Number(0), new Variable(var));
    }

    @Override
    public Expression eval(String signification) {
        return new Number(this.num);
    }

    @Override
    public Expression simplify() {
        return new Number(this.num);
    }

    public int getNum () {
        return this.num;
    }


}
