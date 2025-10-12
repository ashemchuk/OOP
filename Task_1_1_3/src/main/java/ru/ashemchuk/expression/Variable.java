package ru.ashemchuk.expression;

import ru.ashemchuk.parser.Signification;

/**
 * Represents a variable expression in mathematical formulas.
 * This class extends Expression to provide functionality for handling variables
 * that can be substituted with values during evaluation.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Constructs a new Variable expression with the specified name.
     *
     * @param name the name of the variable
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Returns the string representation of the variable.
     *
     * @return the name of the variable
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Computes the derivative of the variable with respect to the specified variable.
     * The derivative is 1 if the variable matches the differentiation variable,
     * otherwise it's 0.
     *
     * @param var the variable with respect to which to differentiate
     * @return a Number expression with value 1 if variables match, 0 otherwise
     */
    public Expression derivative(String var) {
        if (this.name.equals(var)) {
            return new ru.ashemchuk.expression.Number(1);
        }
        return new ru.ashemchuk.expression.Number(0);
    }

    /**
     * Evaluates the variable expression using the given signification.
     * If the variable is found in the signification, returns its numeric value.
     * Otherwise, returns the variable unchanged.
     *
     * @param signification the variable signification to use for evaluation
     * @return a Number if the variable has a value in signification, otherwise a Variable
     */
    public Expression eval(String signification) {
        Signification s = new Signification();
        s.parseSignification(signification);
        Integer value = s.getValue(name);
        if (value != null) {
            return new Number(value);
        }
        return new Variable(this.name);
    }

    /**
     * Simplifies the variable expression.
     * Since a variable is already in its simplest form, it returns itself.
     *
     * @return a new Variable with the same name
     */
    @Override
    public Expression simplify() {
        return new Variable(this.name);
    }

    /**
     * Returns the name of this variable.
     *
     * @return the variable name
     */
    public String getName() {
        return name;
    }
}