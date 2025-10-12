package ru.ashemchuk.expression;

/**
 * Represents a division operation between two expressions.
 * This class extends Expression to provide functionality for dividing two terms.
 */
public class Div extends Expression {
    private final Expression term1;
    private final Expression term2;

    /**
     * Constructs a new Div expression with the specified terms.
     *
     * @param t1 the numerator (dividend) of the division
     * @param t2 the denominator (divisor) of the division
     */
    public Div(Expression t1, Expression t2) {
        this.term1 = t1;
        this.term2 = t2;
    }

    /**
     * Computes the derivative of the division expression using the quotient rule.
     * The quotient rule states: (f/g)' = (f'g - fg') / (g^2)
     *
     * @param var the variable with respect to which to differentiate
     * @return a new Div expression representing the derivative
     */
    @Override
    public Expression derivative(String var) {
        return new Div(
            new Sub(new Mul(term1.derivative(var), term2), new Mul(term1, term2.derivative(var))),
            new Mul(term2.derivative(var), term2.derivative(var)));
    }

    /**
     * Evaluates the division expression by evaluating both terms with the given signification.
     *
     * @param signification the variable signification to use for evaluation
     * @return a new Div expression with evaluated terms
     */
    @Override
    public Expression eval(String signification) {
        return new Div(term1.eval(signification), term2.eval(signification));
    }

    /**
     * Simplifies the division expression by applying mathematical rules.
     * If both terms are numbers, they are divided.
     * If the denominator is 1, the numerator is returned.
     * If the numerator is 0, returns 0.
     * If both terms are equal, returns 1.
     * Otherwise, returns a simplified Div expression.
     *
     * @return a simplified expression according to division rules
     */
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
            return new Number(0);
        }
        if (simple1.equals(simple2)) {
            return new Number(1);
        }
        return new Div(simple1.simplify(), simple2.simplify()).simplify();
    }

    /**
     * Returns a string representation of the division expression.
     * The terms are enclosed in parentheses and separated by a division sign.
     *
     * @return a string representation of the form "(term1 / term2)"
     */
    @Override
    public String toString() {
        return "(" + term1.toString() + "/" + term2.toString() + ")";
    }

    /**
     * Returns the left term (numerator) of the division expression.
     *
     * @return the numerator (dividend)
     */
    public Expression getLeft() {
        return term1;
    }

    /**
     * Returns the right term (denominator) of the division expression.
     *
     * @return the denominator (divisor)
     */
    public Expression getRight() {
        return term2;
    }
}