package ru.ashemchuk.expression;

public class Mul extends Expression {
    private final Expression term1;
    private final Expression term2;

    public Mul (Expression t1, Expression t2) {
        this.term1 = t1;
        this.term2 = t2;
    }

    @Override
    public Expression derivative(String var) {
        return new Add(new Mul(term1.derivative(var), term2), new Mul(term1, term2.derivative(var)));
    }

    @Override
    public Expression eval(String signification) {
        return new Mul(term1.eval(signification), term2.eval(signification));
    }

    @Override
    public Expression simplify() {
        if (term1.getClass() == Number.class && ((Number) term1).getNum() == 0 ||
            term2.getClass() == Number.class && ((Number) term2).getNum() == 0) {
                return new Number(0);
        }
        if (term1.getClass() == Number.class && ((Number) term1).getNum() == 1) {
            return term2.simplify();
        }
        if (term2.getClass() == Number.class && ((Number) term2).getNum() == 1) {
            return term1.simplify();
        }
        if (term1.getClass() == Number.class && term2.getClass() == Number.class) {
            return new Number(((Number) term1).getNum() * ((Number) term2).getNum());
        }
        // return this???
        return new Mul(term1.simplify(), term2.simplify()).simplify();
    }

    @Override
    public String toString() {
        return "(" + term1.toString() + "*" + term2.toString() + ")";
    }
}
