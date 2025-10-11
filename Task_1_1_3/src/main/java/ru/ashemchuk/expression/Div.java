package ru.ashemchuk.expression;

public class Div extends Expression {
    private final Expression term1;
    private final Expression term2;

    public Div (Expression t1, Expression t2) {
        this.term1 = t1;
        this.term2 = t2;
    }

    @Override
    public Expression derivative(String var) {
        return new Div(
            new Sub(
                new Mul(term1.derivative(var), term2), new Mul(term1, term2.derivative(var))),
            new Mul(term2.derivative(var), term2.derivative(var))
        );
    }

    @Override
    public Expression eval(String signification) {
        return new Div(term1.eval(signification), term2.eval(signification));
    }

    @Override
    public Expression simplify() {
        if (term1 instanceof Number && term2 instanceof Number) {
            return new Number(((Number) term1).getNum() / ((Number) term2).getNum());
        }
        return new Div(term1.simplify(), term2.simplify()).simplify();
    }

    @Override
    public String toString() {
        return "(" + term1.toString() + "/" + term2.toString() + ")";
    }
}
