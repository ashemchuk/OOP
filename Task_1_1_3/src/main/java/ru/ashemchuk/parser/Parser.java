package ru.ashemchuk.parser;

import ru.ashemchuk.expression.Add;
import ru.ashemchuk.expression.Div;
import ru.ashemchuk.expression.Expression;
import ru.ashemchuk.expression.Mul;
import ru.ashemchuk.expression.Number;
import ru.ashemchuk.expression.Sub;
import ru.ashemchuk.expression.Variable;

/**
 * Parser for mathematical expressions that converts tokens into expression trees.
 * Implements a recursive descent parser to build abstract syntax trees from token streams.
 * Returns null if parsing fails.
 */
public class Parser {
    private final Tokenizer tokenizer;

    /**
     * Constructs a new Parser with the specified tokenizer.
     *
     * @param tokenizer the tokenizer that provides the token stream to parse
     */
    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Parses an expression according to the grammar:
     * Expr := Monome | Monome +- Monome | Monome +- .... +- Monome
     * Handles addition and subtraction operations with left associativity.
     *
     * @return the parsed expression tree or null if parsing fails
     */
    public Expression parseExpression() {
        Expression accExpr = parseMonome();
        if (accExpr == null) {
            return null;
        }
        while (tokenizer.showNextToken().getType() == TokenType.OP
            && (tokenizer.showNextToken().getValue().equals("+")
            || tokenizer.showNextToken().getValue().equals("-"))) {
            Token op = tokenizer.getNextToken();
            Expression newExpr = parseMonome();
            if (newExpr == null) {
                return null;
            }
            if (op.getValue().equals("+")) {
                accExpr = new Add(accExpr, newExpr);
            }
            if (op.getValue().equals("-")) {
                accExpr = new Sub(accExpr, newExpr);
            }
        }
        return accExpr;
    }

    /**
     * Parses a monome according to the grammar:
     * Monome := Atom | Atom * / Atom | Atom * / ... * / Atom
     * Handles multiplication and division operations with left associativity.
     *
     * @return the parsed monome expression tree or null if parsing fails
     */
    public Expression parseMonome() {
        Expression accExpr = parseAtom();
        if (accExpr == null) {
            return null;
        }
        while (tokenizer.showNextToken().getType() == TokenType.OP
            && (tokenizer.showNextToken().getValue().equals("*")
            || tokenizer.showNextToken().getValue().equals("/"))) {
            Token op = tokenizer.getNextToken();
            Expression newExpr = parseAtom();
            if (newExpr == null) {
                return null;
            }
            if (op.getValue().equals("*")) {
                accExpr = new Mul(accExpr, newExpr);
            }
            if (op.getValue().equals("/")) {
                accExpr = new Div(accExpr, newExpr);
            }
        }
        return accExpr;
    }

    /**
     * Parses an atom according to the grammar:
     * Atom = Number | Var | (Expr)
     * Handles numbers, variables, and parenthesized expressions.
     *
     * @return the parsed atom expression or null if parsing fails
     */
    // FIXME: unary minus
    public Expression parseAtom() {
        Expression res = null;
        if (tokenizer.showNextToken().getType() == TokenType.OP
            && tokenizer.showNextToken().getValue().equals("(")) {
            tokenizer.getNextToken(); // consume open bracket
            res = parseExpression();
            if (res == null) {
                return null;
            }
            Token t = tokenizer.getNextToken(); // consume close bracket
            if (t.getType() != TokenType.OP || !t.getValue().equals(")")) {
                return null; // missing closing parenthesis
            }
        } else {
            Token t = tokenizer.getNextToken();
            if (t.getType() == TokenType.NUM) {
                res = new Number(Integer.parseInt(t.getValue()));
            } else if (t.getType() == TokenType.VAR) {
                res = new Variable(t.getValue());
            }
            // if token is neither NUM nor VAR, res remains null
        }
        return res;
    }
}