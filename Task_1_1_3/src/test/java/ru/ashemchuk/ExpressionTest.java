package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.expression.Add;
import ru.ashemchuk.expression.Expression;
import ru.ashemchuk.expression.Mul;
import ru.ashemchuk.expression.Number;
import ru.ashemchuk.expression.Variable;

class ExpressionTest {

    @Test
    void testToString_Number () {
        ru.ashemchuk.expression.Number zero = new ru.ashemchuk.expression.Number(0);
        assertEquals("0", zero.toString());
        ru.ashemchuk.expression.Number negative = new ru.ashemchuk.expression.Number(-1);
        assertEquals("-1", negative.toString());
        ru.ashemchuk.expression.Number positive = new ru.ashemchuk.expression.Number(1);
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
        ru.ashemchuk.expression.Number n = new ru.ashemchuk.expression.Number(0);
        n.parseSignification(input);
    }

    @Test
    void testEval() {
        Expression e = new Add(new ru.ashemchuk.expression.Number(3), new Mul(new ru.ashemchuk.expression.Number(2), new Variable("x"))); // (3+(2*x))
        e.print();
        e.eval("x=10;y = 1").print();
        e.eval("x=10;y = 1").simplify().print();
    }

    @Test
    void testEquals() {
        assertEquals(new ru.ashemchuk.expression.Number(1), new ru.ashemchuk.expression.Number(1));
        assertNotEquals(new ru.ashemchuk.expression.Number(1), new ru.ashemchuk.expression.Number(0));

        assertEquals(new Variable("X"), new Variable("X"));
        assertEquals(new Variable("xyz"), new Variable("xyz"));
        assertNotEquals(new Variable("X"), new Variable("x"));
        assertNotEquals(new Variable("Xyz"), new Variable("x"));

        assertEquals(new Add(new ru.ashemchuk.expression.Number(1), new ru.ashemchuk.expression.Number(1)), new Add(new ru.ashemchuk.expression.Number(1), new ru.ashemchuk.expression.Number(1)));
        assertEquals(new Add(new Variable("x"), new ru.ashemchuk.expression.Number(1)), new Add(new Variable("x"), new ru.ashemchuk.expression.Number(1)));
        assertNotEquals(new Add(new Variable("x"), new ru.ashemchuk.expression.Number(1)), new Add(new Number(1), new Variable("x")));

    }
}