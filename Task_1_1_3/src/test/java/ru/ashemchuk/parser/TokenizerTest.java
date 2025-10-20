package ru.ashemchuk.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TokenizerTest {

    @Test
    void getNextToken() {
        Tokenizer t = new Tokenizer("21 +   (3 * xxx) ");
        Token num21 = t.getNextToken();
        assertEquals(TokenType.NUM, num21.getType());
        assertEquals("21", num21.getValue());

        Token plus = t.getNextToken();
        assertEquals(TokenType.OP, plus.getType());
        assertEquals("+", plus.getValue());

        Token bracket = t.getNextToken();
        assertEquals(TokenType.OP, bracket.getType());
        assertEquals("(", bracket.getValue());

        Token num3 = t.getNextToken();
        assertEquals(TokenType.NUM, num3.getType());
        assertEquals("3", num3.getValue());

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

        Token num21 = t.showNextToken();
        assertEquals(TokenType.NUM, num21.getType());
        assertEquals("21", num21.getValue());

        t.getNextToken(); // 21
        t.getNextToken(); // +
        t.getNextToken(); // (

        Token num3 = t.showNextToken();
        assertEquals(TokenType.NUM, num3.getType());
        assertEquals("3", num3.getValue());

        Token num3Get = t.getNextToken();
        assertEquals(TokenType.NUM, num3Get.getType());
        assertEquals("3", num3Get.getValue());
    }


    @Test
    void testTokenCreation() {
        Token token = new Token();
        token.setType(TokenType.VAR);
        token.setValue("test");

        assertEquals(TokenType.VAR, token.getType());
        assertEquals("test", token.getValue());
    }

    @Test
    void testTokenizerOperators() {
        Tokenizer tokenizer = new Tokenizer("()+ - */");

        assertEquals("(", tokenizer.getNextToken().getValue());
        assertEquals(")", tokenizer.getNextToken().getValue());
        assertEquals("+", tokenizer.getNextToken().getValue());
        assertEquals("-", tokenizer.getNextToken().getValue());
        assertEquals("*", tokenizer.getNextToken().getValue());
        assertEquals("/", tokenizer.getNextToken().getValue());
    }

    @Test
    void testTokenizerEndOfString() {
        Tokenizer tokenizer = new Tokenizer("x");

        tokenizer.getNextToken(); // get "x"
        Token eosToken = tokenizer.getNextToken();
        assertEquals(TokenType.NOT_TOKEN, eosToken.getType());
    }
}