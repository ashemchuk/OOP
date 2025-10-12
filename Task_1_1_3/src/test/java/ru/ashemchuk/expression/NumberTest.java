package ru.ashemchuk.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void testToStringNumber() {
        ru.ashemchuk.expression.Number zero = new ru.ashemchuk.expression.Number(0);
        assertEquals("0", zero.toString());
        ru.ashemchuk.expression.Number negative = new ru.ashemchuk.expression.Number(-1);
        assertEquals("-1", negative.toString());
        ru.ashemchuk.expression.Number positive = new ru.ashemchuk.expression.Number(1);
        assertEquals("1", positive.toString());
    }

    @Test
    void testNumberDerivative() {
        Number five = new Number(5);

        // d(5)/dx = 0*x
        assertEquals("(0*x)", five.derivative("x").toString());
    }

    @Test
    void testNumberEval() {
        Number seven = new Number(7);

        Expression result = seven.eval("x=5; y=10");
        assertEquals("7", result.toString());
    }
}