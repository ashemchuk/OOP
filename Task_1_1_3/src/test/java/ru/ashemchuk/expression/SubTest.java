package ru.ashemchuk.expression;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SubTest {
    @Test
    void testSubSimplify() {
        // x - x = 0
        Expression sub1 = new Sub(new Variable("x"), new Variable("x"));
        assertEquals("0", sub1.simplify().toString());

        // 5 - 3 = 2
        Expression sub2 = new Sub(new Number(5), new Number(3));
        assertEquals("2", sub2.simplify().toString());

        // x - y
        Expression sub3 = new Sub(new Variable("x"), new Variable("y"));
        assertEquals("(x-y)", sub3.simplify().toString());
    }
    @Test
    void testSubDerivative() {
        // d(x-y)/dx = 1 - 0
        Expression sub = new Sub(new Variable("x"), new Variable("y"));
        Expression derivative = sub.derivative("x");
        assertEquals("(1-0)", derivative.toString());
    }
    @Test
    void testToStringSub() {
        Expression sub1 = new Sub(new Number(5), new Number(3));
        assertEquals("(5-3)", sub1.toString());

        Expression sub2 = new Sub(new Variable("a"), new Variable("b"));
        assertEquals("(a-b)", sub2.toString());

        Expression sub3 = new Sub(new Sub(new Number(10), new Number(2)), new Number(1));
        assertEquals("((10-2)-1)", sub3.toString());
    }


    @Test
    void testEvalSub() {
        Expression sub1 = new Sub(new Variable("x"), new Number(2));
        Expression result1 = sub1.eval("x=8");
        assertEquals("(8-2)", result1.toString());

        Expression sub2 = new Sub(new Variable("a"), new Variable("b"));
        Expression result2 = sub2.eval("a=10; b=3");
        assertEquals("(10-3)", result2.toString());

        Expression sub3 = new Sub(new Variable("x"), new Variable("y"));
        Expression result3 = sub3.eval("x=2; y=5");
        assertEquals("(2-5)", result3.toString());
    }

}