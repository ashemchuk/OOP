package ru.ashemchuk.expression;

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
    public Expression eval (String signification) {
        return new Add(term1.eval(signification), term2.eval(signification));
    }

    @Override
    public Expression simplify() {
        Expression simple1 = term1.simplify();
        Expression simple2 = term2.simplify();

        if (simple1 instanceof Number && simple2 instanceof Number) {
            return new Number(((Number) simple1).getNum() + ((Number) simple2).getNum());
        }
        // FIXME: new
        if (simple1 instanceof Number && ((Number) simple1).getNum() == 0) {
            return simple2;
        }
        // FIXME: new
        if (simple2 instanceof  Number && ((Number) simple2).getNum() == 0) {
            return simple1;
        }

        return new Add (simple1, simple2);
    }

    public Expression getLeft() {
        return term1;
    }
    public Expression getRight() {
        return term2;
    }
}
