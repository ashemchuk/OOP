package ru.ashemchuk.expression;

/**
 * Represents an addition operation between two expressions.
 * This class extends Expression to provide functionality for adding two terms.
 */
public class Add extends Expression {
    private final Expression term1;
    private final Expression term2;

    /**
     * Constructs a new Add expression with the specified terms.
     *
     * @param t1 the first term (left operand) of the addition
     * @param t2 the second term (right operand) of the addition
     */
    public Add(Expression t1, Expression t2) {
        this.term1 = t1;
        this.term2 = t2;
    }

    /**
     * Returns a string representation of the addition expression.
     * The terms are enclosed in parentheses and separated by a plus sign.
     *
     * @return a string representation of the form "(term1 + term2)"
     */
    @Override
    public String toString() {
        return "(" + term1.toString() + "+" + term2.toString() + ")";
    }

    /**
     * Computes the derivative of the addition expression with respect to the specified variable.
     * The derivative of a sum is the sum of the derivatives of its terms.
     *
     * @param var the variable with respect to which to differentiate
     * @return a new Add expression representing the derivative
     */
    public Expression derivative(String var) {
        return new Add(term1.derivative(var), term2.derivative(var));
    }

    /**
     * Evaluates the addition expression by evaluating both terms with the given signification.
     *
     * @param signification the variable signification to use for evaluation
     * @return a new Add expression with evaluated terms
     */
    @Override
    public Expression eval(String signification) {
        return new Add(term1.eval(signification), term2.eval(signification));
    }

    /**
     * Simplifies the addition expression by applying mathematical rules.
     * If both terms are numbers, they are added together.
     * If one term is zero, the other term is returned.
     * Otherwise, returns a new Add expression with simplified terms.
     *
     * @return a simplified expression according to addition rules
     */
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
        if (simple2 instanceof Number && ((Number) simple2).getNum() == 0) {
            return simple1;
        }

        return new Add(simple1, simple2);
    }

    /**
     * Returns the left term of the addition expression.
     *
     * @return the first term (left operand)
     */
    public Expression getLeft() {
        return term1;
    }

    /**
     * Returns the right term of the addition expression.
     *
     * @return the second term (right operand)
     */
    public Expression getRight() {
        return term2;
    }
}