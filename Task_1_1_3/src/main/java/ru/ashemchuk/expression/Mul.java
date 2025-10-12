package ru.ashemchuk.expression;

/**
 * Represents a multiplication operation between two expressions.
 * This class extends Expression to provide functionality for multiplying two terms.
 */
public class Mul extends Expression {
    private final Expression term1;
    private final Expression term2;

    /**
     * Constructs a new Mul expression with the specified terms.
     *
     * @param t1 the first factor (left operand) of the multiplication
     * @param t2 the second factor (right operand) of the multiplication
     */
    public Mul(Expression t1, Expression t2) {
        this.term1 = t1;
        this.term2 = t2;
    }

    /**
     * Computes the derivative of the multiplication expression using the product rule.
     * The product rule states: (fg)' = f'g + fg'
     *
     * @param var the variable with respect to which to differentiate
     * @return a new Add expression representing the derivative according to the product rule
     */
    @Override
    public Expression derivative(String var) {
        return new Add(new Mul(term1.derivative(var), term2),
            new Mul(term1, term2.derivative(var)));
    }

    /**
     * Evaluates the multiplication expression by evaluating both terms with the given signification.
     *
     * @param signification the variable signification to use for evaluation
     * @return a new Mul expression with evaluated terms
     */
    @Override
    public Expression eval(String signification) {
        return new Mul(term1.eval(signification), term2.eval(signification));
    }

    /**
     * Simplifies the multiplication expression by applying mathematical rules.
     * If either term is zero, returns zero.
     * If one term is one, returns the other term.
     * If both terms are numbers, returns their product.
     * Otherwise, returns a new Mul expression with simplified terms.
     *
     * @return a simplified expression according to multiplication rules
     */
    @Override
    public Expression simplify() {
        Expression simple1 = term1.simplify();
        Expression simple2 = term2.simplify();
        if (simple1 instanceof Number && ((Number) simple1).getNum() == 0 ||
            simple2 instanceof Number && ((Number) simple2).getNum() == 0) {
            return new Number(0);
        }
        // FIXME new
        if (simple1 instanceof Number && ((Number) simple1).getNum() == 1) {
            return simple2.simplify();
        }
        // FIXME new
        if (simple2 instanceof Number && ((Number) simple2).getNum() == 1) {
            return simple1.simplify();
        }
        if (simple1 instanceof Number && simple2 instanceof Number) {
            return new Number(((Number) simple1).getNum() * ((Number) simple2).getNum());
        }
        return new Mul(simple1, simple2);
    }

    /**
     * Returns a string representation of the multiplication expression.
     * The terms are enclosed in parentheses and separated by a multiplication sign.
     *
     * @return a string representation of the form "(term1 * term2)"
     */
    @Override
    public String toString() {
        return "(" + term1.toString() + "*" + term2.toString() + ")";
    }

    /**
     * Returns the left term of the multiplication expression.
     *
     * @return the first factor (left operand)
     */
    public Expression getLeft() {
        return term1;
    }

    /**
     * Returns the right term of the multiplication expression.
     *
     * @return the second factor (right operand)
     */
    public Expression getRight() {
        return term2;
    }
}