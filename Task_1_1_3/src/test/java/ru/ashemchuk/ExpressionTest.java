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

}