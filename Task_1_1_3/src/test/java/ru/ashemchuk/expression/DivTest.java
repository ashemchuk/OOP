package ru.ashemchuk.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void testDivSimplify() {
        // 0 / x = 0
        Expression div1 = new Div(new Number(0), new Variable("x"));
        assertEquals("0", div1.simplify().toString());

        // x / 1 = x
        Expression div2 = new Div(new Variable("x"), new Number(1));
        assertEquals("x", div2.simplify().toString());

        // 6 / 2 = 3
        Expression div3 = new Div(new Number(6), new Number(2));
        assertEquals("3", div3.simplify().toString());

        // x / x = 1
        Expression div4 = new Div(new Variable("x"), new Variable("x"));
        assertEquals("1", div4.simplify().toString());
    }

    @Test
    void testToStringDiv() {
        Expression div1 = new Div(new Number(6), new Number(2));
        assertEquals("(6/2)", div1.toString());

        Expression div2 = new Div(new Variable("a"), new Variable("b"));
        assertEquals("(a/b)", div2.toString());

        Expression div3 = new Div(new Div(new Number(12), new Number(3)), new Number(2));
        assertEquals("((12/3)/2)", div3.toString());
    }

    @Test
    void testEvalDiv() {
        Expression div1 = new Div(new Variable("x"), new Number(2));
        Expression result1 = div1.eval("x=10");
        assertEquals("(10/2)", result1.toString());

        Expression div2 = new Div(new Variable("a"), new Variable("b"));
        Expression result2 = div2.eval("a=15; b=3");
        assertEquals("(15/3)", result2.toString());

        Expression div3 = new Div(new Variable("x"), new Variable("x"));
        Expression result3 = div3.eval("x=7");
        assertEquals("(7/7)", result3.toString());
    }

    @Test
    void testDivDerivative() {
        // d(x/y)/dx = (1*y - x*0) / (y*y) = y / (y*y)
        Expression div1 = new Div(new Variable("x"), new Variable("y"));
        Expression derivative1 = div1.derivative("x");
        assertEquals("(((1*y)-(x*0))/(0*0))", derivative1.toString());
    }
}