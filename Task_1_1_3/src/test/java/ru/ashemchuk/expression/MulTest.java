package ru.ashemchuk.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void testMulSimplify() {
        // 0 * x = 0
        Expression mul1 = new Mul(new Number(0), new Variable("x"));
        assertEquals("0", mul1.simplify().toString());

        // x * 0 = 0
        Expression mul2 = new Mul(new Variable("x"), new Number(0));
        assertEquals("0", mul2.simplify().toString());

        // 1 * x = x
        Expression mul3 = new Mul(new Number(1), new Variable("x"));
        assertEquals("x", mul3.simplify().toString());

        // x * 1 = x
        Expression mul4 = new Mul(new Variable("x"), new Number(1));
        assertEquals("x", mul4.simplify().toString());

        // 2 * 3 = 6
        Expression mul5 = new Mul(new Number(2), new Number(3));
        assertEquals("6", mul5.simplify().toString());
    }

    @Test
    void testMulDerivative() {
        // d(x*y)/dx = 1*y + x*0
        Expression mul = new Mul(new Variable("x"), new Variable("y"));
        Expression derivative = mul.derivative("x");
        assertEquals("((1*y)+(x*0))", derivative.toString());
    }

    @Test
    void testToStringMul() {
        Expression mul1 = new Mul(new Number(2), new Number(3));
        assertEquals("(2*3)", mul1.toString());

        Expression mul2 = new Mul(new Variable("x"), new Variable("y"));
        assertEquals("(x*y)", mul2.toString());

        Expression mul3 = new Mul(new Mul(new Number(2), new Number(3)), new Number(4));
        assertEquals("((2*3)*4)", mul3.toString());
    }

    @Test
    void testEvalMul() {
        Expression mul1 = new Mul(new Variable("x"), new Number(3));
        Expression result1 = mul1.eval("x=4");
        assertEquals("(4*3)", result1.toString());

        Expression mul2 = new Mul(new Variable("a"), new Variable("b"));
        Expression result2 = mul2.eval("a=5; b=6");
        assertEquals("(5*6)", result2.toString());

        Expression mul3 = new Mul(new Variable("x"), new Number(0));
        Expression result3 = mul3.eval("x=100");
        assertEquals("(100*0)", result3.toString());
    }
}