package ru.ashemchuk.expression;

/**
 * Represents a numeric constant expression.
 * This class extends Expression to provide functionality for handling numerical values.
 */
public class Number extends Expression {
    private final int num;

    /**
     * Constructs a new Number expression with the specified integer value.
     *
     * @param num the integer value to be represented
     */
    public Number(int num) {
        this.num = num;
    }

    /**
     * Returns the string representation of the numeric value.
     *
     * @return the string representation of the stored number
     */
    @Override
    public String toString() {
        return Integer.toString(num);
    }

    /**
     * Computes the derivative of the number with respect to the specified variable.
     * The derivative of a constant is always zero.
     *
     * @param var the variable with respect to which to differentiate
     * @return a multiplication expression representing zero times the variable
     */
    @Override
    public Expression derivative(String var) {
        return new Mul(new Number(0), new Variable(var));
    }

    /**
     * Evaluates the number expression.
     * Since a number is a constant, it returns itself unchanged.
     *
     * @param signification the variable signification to use for evaluation
     * @return a new Number with the same value
     */
    @Override
    public Expression eval(String signification) {
        return new Number(this.num);
    }

    /**
     * Simplifies the number expression.
     * Since a number is already in its simplest form, it returns itself.
     *
     * @return a new Number with the same value
     */
    @Override
    public Expression simplify() {
        return new Number(this.num);
    }

    /**
     * Returns the numeric value stored in this expression.
     *
     * @return the integer value of this number
     */
    public int getNum() {
        return this.num;
    }
}