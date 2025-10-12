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
        Expression simple1 = term1.simplify();
        Expression simple2 = term2.simplify();

        if (simple1 instanceof Number && simple2 instanceof Number) {
            return new Number(((Number) simple1).getNum() / ((Number) simple2).getNum());
        }
        // FIXME: not new
        if (simple2 instanceof Number && ((Number) simple2).getNum() == 1) {
            return simple1;
        }
        if (simple1 instanceof Number && ((Number) simple1).getNum() == 0) {
            return  new Number(0);
        }
        if (simple1.equals(simple2)) {
            return new Number(1);
        }
        return new Div(simple1.simplify(), simple2.simplify()).simplify();
    }

    @Override
    public String toString() {
        return "(" + term1.toString() + "/" + term2.toString() + ")";
    }

    public Expression getLeft() {
        return term1;
    }
    public Expression getRight() {
        return term2;
    }
}
