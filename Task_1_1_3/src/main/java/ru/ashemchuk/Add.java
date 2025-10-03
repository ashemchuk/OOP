package ru.ashemchuk;

public class Add extends Expression {
    private final Expression term1;
    private final Expression term2;

    public Add (Expression t1, Expression t2) {
        this.term1 = t1;
        this.term2 = t2;
    }

    @Override
    public String toString () {
        return "(" + term1.toString() + "+" + term2.toString() + ")";
    }

    public Expression derivative (String var) {
        return new Add(term1.derivative(var), term2.derivative(var));
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
