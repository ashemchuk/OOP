package ru.ashemchuk.expression;

/**
 * Abstract base class representing a mathematical expression.
 * Provides the foundation for various types of mathematical expressions
 * and defines common operations that all expressions must implement.
 */
public abstract class Expression {

    /**
     * Computes the derivative of this expression with respect to the specified variable.
     *
     * @param var the variable with respect to which to differentiate
     * @return a new Expression representing the derivative
     */
    public abstract Expression derivative(String var);

    /**
     * Evaluates the expression using the given variable signification.
     *
     * @param signification the variable signification to use for evaluation
     * @return a new Expression resulting from the evaluation
     */
    public abstract Expression eval(String signification);

    /**
     * Simplifies the expression by applying mathematical rules and identities.
     *
     * @return a simplified version of the expression
     */
    public abstract Expression simplify();

    /**
     * Returns a string representation of the expression.
     * The representation should clearly show the structure of the expression.
     *
     * @return a string representation of the expression
     */
    @Override
    public abstract String toString();

    /**
     * Compares this expression with another object for equality.
     * Two expressions are considered equal if their string representations are identical.
     *
     * @param o the object to compare with
     * @return true if the string representations are equal, false otherwise
     */
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    /**
     * Prints the string representation of this expression to the standard output.
     * This is a convenience method for debugging and display purposes.
     */
    public void print() {
        System.out.println(this);
    }
}