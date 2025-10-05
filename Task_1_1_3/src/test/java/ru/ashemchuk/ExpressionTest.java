package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void testToString_Number () {
        Number zero = new Number(0);
        assertEquals("0", zero.toString());
        Number negative = new Number(-1);
        assertEquals("-1", negative.toString());
        Number positive = new Number(1);
        assertEquals("1", positive.toString());
    }

    @Test
    void testToString_Variable () {
        Variable singleLetter = new Variable("x");
        assertEquals("x", singleLetter.toString());
        Variable multiLetter = new Variable("variable");
        assertEquals("variable", multiLetter.toString());
    }

    @Test
    void testParseSignification() {
        String input = "x = 11;    j = 1; multiple = 0; x=2";
        Number n = new Number(0);
        n.parseSignification(input);
    }

    @Test
    void testEval() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x"))); // (3+(2*x))
        e.print();
        e.eval("x=10;y = 1").print();
        e.eval("x=10;y = 1").simplify().print();
    }
}