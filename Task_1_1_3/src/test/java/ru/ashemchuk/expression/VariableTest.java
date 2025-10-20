package ru.ashemchuk.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VariableTest {
    @Test
    void testToStringVariable() {
        Variable singleLetter = new Variable("x");
        assertEquals("x", singleLetter.toString());
        Variable multiLetter = new Variable("variable");
        assertEquals("variable", multiLetter.toString());
    }

    @Test
    void testVariableDerivative() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");

        // d(x)/dx = 1
        assertEquals("1", x.derivative("x").toString());

        // d(y)/dx = 0
        assertEquals("0", y.derivative("x").toString());

        // d(x)/dy = 0
        assertEquals("0", x.derivative("y").toString());
    }

    @Test
    void testVariableEval() {
        Variable x = new Variable("x");

        // x, x=5
        Expression result1 = x.eval("x=5");
        assertEquals("5", result1.toString());

        // x, y=10
        Expression result2 = x.eval("y=10");
        assertEquals("x", result2.toString());

        // x, x=3; y=7
        Expression result3 = x.eval("x=3; y=7");
        assertEquals("3", result3.toString());
    }
}