package ru.ashemchuk.expression;

/**
 * Represents a subtraction operation between two expressions.
 * This class extends Expression to provide functionality for subtracting two terms.
 */
public class Sub extends Expression {
    private final Expression term1;
    private final Expression term2;

    /**
     * Constructs a new Sub expression with the specified terms.
     *
     * @param t1 the minuend (left operand) of the subtraction
     * @param t2 the subtrahend (right operand) of the subtraction
     */
    public Sub(Expression t1, Expression t2) {
        this.term1 = t1;
        this.term2 = t2;
    }

    /**
     * Computes the derivative of the subtraction expression.
     * The derivative of a difference is the difference of the derivatives.
     *
     * @param var the variable with respect to which to differentiate
     * @return a new Sub expression representing the derivative
     */
    @Override
    public Expression derivative(String var) {
        return new Sub(term1.derivative(var), term2.derivative(var));
    }

    /**
     * Evaluates the subtraction expression by evaluating both terms with the given signification.
     *
     * @param signification the variable signification to use for evaluation
     * @return a new Sub expression with evaluated terms
     */
    @Override
    public Expression eval(String signification) {
        return new Sub(term1.eval(signification), term2.eval(signification));
    }

    /**
     * Simplifies the subtraction expression by applying mathematical rules.
     * If both terms are equal, returns zero.
     * If both terms are numbers, returns their difference.
     * Otherwise, returns a new Sub expression with simplified terms.
     *
     * @return a simplified expression according to subtraction rules
     */
    @Override
    public Expression simplify() {
        Expression simple1 = term1.simplify();
        Expression simple2 = term2.simplify();

        if (simple1.equals(simple2)) {
            return new Number(0);
        }
        if (simple1 instanceof Number && simple2 instanceof Number) {
            return new Number(((Number) simple1).getNum() - ((Number) simple2).getNum());
        }
        return new Sub(simple1, simple2);
    }

    /**
     * Returns a string representation of the subtraction expression.
     * The terms are enclosed in parentheses and separated by a minus sign.
     *
     * @return a string representation of the form "(term1 - term2)"
     */
    @Override
    public String toString() {
        return "(" + term1.toString() + "-" + term2.toString() + ")";
    }

    /**
     * Returns the left term (minuend) of the subtraction expression.
     *
     * @return the minuend (left operand)
     */
    public Expression getLeft() {
        return term1;
    }

    /**
     * Returns the right term (subtrahend) of the subtraction expression.
     *
     * @return the subtrahend (right operand)
     */
    public Expression getRight() {
        return term2;
    }
}