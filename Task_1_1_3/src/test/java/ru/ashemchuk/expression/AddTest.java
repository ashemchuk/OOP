package ru.ashemchuk.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    void testToStringAdd() {
        Expression add1 = new Add(new Number(1), new Number(2));
        assertEquals("(1+2)", add1.toString());

        Expression add2 = new Add(new Variable("x"), new Variable("y"));
        assertEquals("(x+y)", add2.toString());

        Expression add3 = new Add(new Add(new Number(1), new Number(2)), new Number(3));
        assertEquals("((1+2)+3)", add3.toString());
    }

    @Test
    void testAddDerivative() {
        // d(x+y)/dx = 1 + 0
        Expression add = new Add(new Variable("x"), new Variable("y"));
        Expression derivative = add.derivative("x");
        assertEquals("(1+0)", derivative.toString());
    }

    @Test
    void testAddSimplify() {
        // 0 + x = x
        Expression add1 = new Add(new Number(0), new Variable("x"));
        assertEquals("x", add1.simplify().toString());

        // x + 0 = x
        Expression add2 = new Add(new Variable("x"), new Number(0));
        assertEquals("x", add2.simplify().toString());

        // 2 + 3 = 5
        Expression add3 = new Add(new Number(2), new Number(3));
        assertEquals("5", add3.simplify().toString());

        // x + y
        Expression add4 = new Add(new Variable("x"), new Variable("y"));
        assertEquals("(x+y)", add4.simplify().toString());
    }

    @Test
    void testEvalAdd() {
        Expression add1 = new Add(new Variable("x"), new Number(5));
        Expression result1 = add1.eval("x=3");
        assertEquals("(3+5)", result1.toString());

        Expression add2 = new Add(new Variable("a"), new Variable("b"));
        Expression result2 = add2.eval("a=2; b=7");
        assertEquals("(2+7)", result2.toString());

        // Неопределенная переменная
        Expression add3 = new Add(new Variable("x"), new Variable("y"));
        Expression result3 = add3.eval("x=5");
        assertEquals("(5+y)", result3.toString());
    }
}