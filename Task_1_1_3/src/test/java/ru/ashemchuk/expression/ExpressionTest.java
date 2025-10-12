package ru.ashemchuk.expression;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExpressionTest {
    @Test
    void testEquals() {
        assertEquals(new ru.ashemchuk.expression.Number(1), new ru.ashemchuk.expression.Number(1));
        assertNotEquals(new ru.ashemchuk.expression.Number(1), new ru.ashemchuk.expression.Number(0));

        assertEquals(new Variable("X"), new Variable("X"));
        assertEquals(new Variable("xyz"), new Variable("xyz"));
        assertNotEquals(new Variable("X"), new Variable("x"));
        assertNotEquals(new Variable("Xyz"), new Variable("x"));

        assertEquals(
            new Add(new ru.ashemchuk.expression.Number(1), new ru.ashemchuk.expression.Number(1)),
            new Add(new ru.ashemchuk.expression.Number(1), new ru.ashemchuk.expression.Number(1))
        );
        assertEquals(
            new Add(new Variable("x"), new ru.ashemchuk.expression.Number(1)),
            new Add(new Variable("x"), new ru.ashemchuk.expression.Number(1))
        );
        assertNotEquals(
            new Add(new Variable("x"), new ru.ashemchuk.expression.Number(1)),
            new Add(new Number(1), new Variable("x")));

    }

    @Test
    void testComplexExpressionSimplify() {
        // (x + 0) * (y * 1) - (z / z) = x * y - 1
        Expression complex = new Sub(
            new Mul(
                new Add(new Variable("x"), new Number(0)),
                new Mul(new Variable("y"), new Number(1))
            ),
            new Div(new Variable("z"), new Variable("z"))
        );
        assertEquals("((x*y)-1)", complex.simplify().toString());
    }

    @Test
    void testComplexExpressionEval() {
        // (x + y) * 2, x=3, y=4
        Expression expr = new Mul(
            new Add(new Variable("x"), new Variable("y")),
            new Number(2)
        );

        Expression result = expr.eval("x=3; y=4");
        Expression simplified = result.simplify();

        assertEquals("14", simplified.toString());
    }

    @Test
    void testGetLeftAndRight() {
        Add add = new Add(new Variable("x"), new Number(5));
        assertEquals("x", add.getLeft().toString());
        assertEquals("5", add.getRight().toString());

        Mul mul = new Mul(new Number(2), new Variable("y"));
        assertEquals("2", mul.getLeft().toString());
        assertEquals("y", mul.getRight().toString());

        Sub sub = new Sub(new Variable("a"), new Variable("b"));
        assertEquals("a", sub.getLeft().toString());
        assertEquals("b", sub.getRight().toString());

        Div div = new Div(new Number(10), new Number(2));
        assertEquals("10", div.getLeft().toString());
        assertEquals("2", div.getRight().toString());
    }

    @Test
    void testNestedExpressions() {
        // (x + 1) * (y - 2)
        Expression nested = new Mul(
            new Add(new Variable("x"), new Number(1)),
            new Sub(new Variable("y"), new Number(2))
        );

        assertEquals("((x+1)*(y-2))", nested.toString());

        Expression evaluated = nested.eval("x=2; y=5");
        Expression simplified = evaluated.simplify();
        assertEquals("9", simplified.toString());
    }

    @Test
    void testExpressionWithAllOperations() {
        // ((x + y) * (z - 1)) / 2
        Expression complex = new Div(
            new Mul(
                new Add(new Variable("x"), new Variable("y")),
                new Sub(new Variable("z"), new Number(1))
            ),
            new Number(2)
        );

        Expression result = complex.eval("x=1; y=2; z=4");
        Expression simplified = result.simplify();

        assertEquals("4", simplified.toString());
    }

    @Test
    void testToStringComplexExpressions() {
        // (x + y) * (a - b)
        Expression expr1 = new Mul(
            new Add(new Variable("x"), new Variable("y")),
            new Sub(new Variable("a"), new Variable("b"))
        );
        assertEquals("((x+y)*(a-b))", expr1.toString());

        // (x * y) + (a / b)
        Expression expr2 = new Add(
            new Mul(new Variable("x"), new Variable("y")),
            new Div(new Variable("a"), new Variable("b"))
        );
        assertEquals("((x*y)+(a/b))", expr2.toString());

        // ((x + 1) * 2) / (y - 3)
        Expression expr3 = new Div(
            new Mul(
                new Add(new Variable("x"), new Number(1)),
                new Number(2)
            ),
            new Sub(new Variable("y"), new Number(3))
        );
        assertEquals("(((x+1)*2)/(y-3))", expr3.toString());
    }

    @Test
    void testEvalComplexExpressions() {
        // (x + y) * z
        Expression expr1 = new Mul(
            new Add(new Variable("x"), new Variable("y")),
            new Variable("z")
        );
        Expression result1 = expr1.eval("x=2; y=3; z=4");
        assertEquals("((2+3)*4)", result1.toString());

        // (a * b) - (c / d)
        Expression expr2 = new Sub(
            new Mul(new Variable("a"), new Variable("b")),
            new Div(new Variable("c"), new Variable("d"))
        );
        Expression result2 = expr2.eval("a=3; b=4; c=10; d=2");
        assertEquals("((3*4)-(10/2))", result2.toString());

        // ((x + 1) * 2) / y
        Expression expr3 = new Div(
            new Mul(
                new Add(new Variable("x"), new Number(1)),
                new Number(2)
            ),
            new Variable("y")
        );
        Expression result3 = expr3.eval("x=4; y=2");
        assertEquals("(((4+1)*2)/2)", result3.toString());
    }

    @Test
    void testEvalWithPartialSignification() {
        // x + y, only x is defined
        Expression expr1 = new Add(new Variable("x"), new Variable("y"));
        Expression result1 = expr1.eval("x=5");
        assertEquals("(5+y)", result1.toString());

        // a * b + c, a and c are defined
        Expression expr2 = new Add(
            new Mul(new Variable("a"), new Variable("b")),
            new Variable("c")
        );
        Expression result2 = expr2.eval("a=2; c=10");
        assertEquals("((2*b)+10)", result2.toString());

        // all aren't defined
        Expression expr3 = new Add(new Variable("p"), new Variable("q"));
        Expression result3 = expr3.eval("x=1; y=2");
        assertEquals("(p+q)", result3.toString());
    }

    @Test
    void testEvalWithEmptySignification() {
        Expression expr = new Add(new Variable("x"), new Number(1));
        Expression result = expr.eval("");
        assertEquals("(x+1)", result.toString());

        Expression expr2 = new Mul(new Variable("a"), new Variable("b"));
        Expression result2 = expr2.eval("   ");
        assertEquals("(a*b)", result2.toString());
    }

    @Test
    void testEvalWithInvalidSignification() {
        Expression expr = new Add(new Variable("x"), new Number(1));

        // incorrect signification
        Expression result1 = expr.eval("x=abc; y=2");
        assertEquals("(x+1)", result1.toString());

        Expression result2 = expr.eval("=5; x=3");
        assertEquals("(3+1)", result2.toString());

        Expression result3 = expr.eval("x; y=2");
        assertEquals("(x+1)", result3.toString());
    }

    @Test
    void testEvalAndSimplifyCombination() {
        // (x + 2) * 3
        // x=4 -> (4+2)*3 -> 6*3 -> 18
        Expression expr1 = new Mul(
            new Add(new Variable("x"), new Number(2)),
            new Number(3)
        );
        Expression result1 = expr1.eval("x=4").simplify();
        assertEquals("18", result1.toString());

        // (a * b) / a
        // a=5, b=3 -> (5*3)/5 -> 15/5 -> 3
        Expression expr2 = new Div(
            new Mul(new Variable("a"), new Variable("b")),
            new Variable("a")
        );
        Expression result2 = expr2.eval("a=5; b=3").simplify();
        assertEquals("3", result2.toString());

        // (x + y) - x
        // x=2, y=7 -> (2+7)-2 -> 9-2 -> 7
        Expression expr3 = new Sub(
            new Add(new Variable("x"), new Variable("y")),
            new Variable("x")
        );
        Expression result3 = expr3.eval("x=2; y=7").simplify();
        assertEquals("7", result3.toString());
    }
}