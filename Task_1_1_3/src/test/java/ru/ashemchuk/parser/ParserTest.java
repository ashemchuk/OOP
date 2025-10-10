package ru.ashemchuk.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.expression.*;
import ru.ashemchuk.expression.Number;

class ParserTest {

    @Test
    void parseExpression() {
        // mul in brackets
        String exp_str = "2+(31* x) -( 4 * x )+ y";
        Tokenizer t = new Tokenizer(exp_str);
        Parser p = new Parser(t);
        Expression res = p.parseExpression();
        assertEquals(new Add(new Sub(new Add(new Number(2), new Mul(new Number(31), new Variable("x"))), new Mul(new Number(4), new Variable("x"))), new Variable("y")), res);
    }
}