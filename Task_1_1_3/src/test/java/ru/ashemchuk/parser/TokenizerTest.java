package ru.ashemchuk.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TokenizerTest {

    @Test
    void getNextToken() {
        Tokenizer t = new Tokenizer("21 +   (3 * xxx)");
        Token num_21 = t.getNextToken();
        assertEquals(TokenType.NUM, num_21.getType());
        assertEquals("21", num_21.getValue());

        Token plus = t.getNextToken();
        assertEquals(TokenType.OP, plus.getType());
        assertEquals("+", plus.getValue());

        Token bracket = t.getNextToken();
        assertEquals(TokenType.OP, bracket.getType());
        assertEquals("(", bracket.getValue());

        Token num_3 = t.getNextToken();
        assertEquals(TokenType.NUM, num_3.getType());
        assertEquals("3", num_3.getValue());

        Token mul = t.getNextToken();
        assertEquals(TokenType.OP, mul.getType());
        assertEquals("*", mul.getValue());

        Token var = t.getNextToken();
        assertEquals(TokenType.VAR, var.getType());
        assertEquals("xxx", var.getValue());
    }

    @Test
    void showNextToken() {
        Tokenizer t = new Tokenizer("21 +   (3 * xxx)");
        Token t1 = t.showNextToken();
        Token t2 = t.showNextToken();
        assertEquals(t1.getType(), t2.getType());
        assertEquals(t1.getValue(), t2.getValue());

        Token num_21 = t.showNextToken();
        assertEquals(TokenType.NUM, num_21.getType());
        assertEquals("21", num_21.getValue());

        t.getNextToken(); // 21
        t.getNextToken(); // +
        t.getNextToken(); // (

        Token num_3 = t.showNextToken();
        assertEquals(TokenType.NUM, num_3.getType());
        assertEquals("3", num_3.getValue());

        Token num_3_get = t.getNextToken();
        assertEquals(TokenType.NUM, num_3_get.getType());
        assertEquals("3", num_3_get.getValue());
    }
}