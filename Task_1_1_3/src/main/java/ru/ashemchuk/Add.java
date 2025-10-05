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
    public Expression eval (String signification) {
        return new Add(term1.eval(signification), term2.eval(signification));
    }

    @Override
    public Expression simplify() {
        if (term1.getClass() == Number.class && term2.getClass() == Number.class) {
            return new Number(((Number) term1).getNum() + ((Number) term2).getNum());
        }
//        if (term1.getClass() == Number.class && ((Number) term1).getNum() == 0) {
//            return term2;
//        }

        // new??
        return new Add (term1.simplify(), term2.simplify()).simplify();
    }
}
