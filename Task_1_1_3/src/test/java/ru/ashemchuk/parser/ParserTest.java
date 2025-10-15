package ru.ashemchuk.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.expression.Add;
import ru.ashemchuk.expression.Div;
import ru.ashemchuk.expression.Expression;
import ru.ashemchuk.expression.Mul;
import ru.ashemchuk.expression.Number;
import ru.ashemchuk.expression.Sub;
import ru.ashemchuk.expression.Variable;

class ParserTest {

    @Test
    void testParseSimpleExpression() {
        Tokenizer tokenizer = new Tokenizer("x + y");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Add.class, expr);
    }

    @Test
    void testParseExpressionWithNumbers() {
        Tokenizer tokenizer = new Tokenizer("5 + 3");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Add.class, expr);
    }

    @Test
    void testParseMultiplication() {
        Tokenizer tokenizer = new Tokenizer("x * y");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Mul.class, expr);
    }

    @Test
    void testParseDivision() {
        Tokenizer tokenizer = new Tokenizer("x / y");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Div.class, expr);
    }

    @Test
    void testParseSubtraction() {
        Tokenizer tokenizer = new Tokenizer("x - y");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Sub.class, expr);
    }

    @Test
    void testParseComplexExpression() {
        Tokenizer tokenizer = new Tokenizer("x + y * z");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Add.class, expr);

        Add addExpr = (Add) expr;
        assertInstanceOf(Variable.class, addExpr.getLeft());
        assertInstanceOf(Mul.class, addExpr.getRight());
    }

    @Test
    void testParseExpressionWithParentheses() {
        Tokenizer tokenizer = new Tokenizer("(x + y) * z");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Mul.class, expr);

        Mul mulExpr = (Mul) expr;
        assertInstanceOf(Add.class, mulExpr.getLeft());
        assertInstanceOf(Variable.class, mulExpr.getRight());
    }

    @Test
    void testParseNumber() {
        Tokenizer tokenizer = new Tokenizer("42");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Number.class, expr);
        assertEquals(42, ((Number) expr).getNum());
    }

    @Test
    void testParseVariable() {
        Tokenizer tokenizer = new Tokenizer("variableName");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Variable.class, expr);
        assertEquals("variableName", ((Variable) expr).getName());
    }

    @Test
    void testParseMixedExpression() {
        Tokenizer tokenizer = new Tokenizer("a + b * c - d / e");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertInstanceOf(Sub.class, expr);

        Sub subExpr = (Sub) expr;
        assertInstanceOf(Add.class, subExpr.getLeft());
        assertInstanceOf(Div.class, subExpr.getRight());
    }

    @Test
    void testParserInvalidInput() {
        Tokenizer tokenizer = new Tokenizer("a + )");
        Parser parser = new Parser(tokenizer);

        Expression expr = parser.parseExpression();
        assertNull(expr);
    }
}