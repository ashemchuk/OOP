package ru.ashemchuk.parser;

import ru.ashemchuk.expression.Add;
import ru.ashemchuk.expression.Div;
import ru.ashemchuk.expression.Expression;
import ru.ashemchuk.expression.Mul;
import ru.ashemchuk.expression.Number;
import ru.ashemchuk.expression.Sub;
import ru.ashemchuk.expression.Variable;

public class Parser {
    private final Tokenizer tokenizer;

    //FIXME: invalid input string handling
    public Parser (Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }
    /**
     * Expr :=  Monome |
     *          Monome +- Monome |
     *          Monome +- .... +- Monome
     * @return
     */
    public Expression parseExpression () {
        Expression acc_expr = parseMonome();
        while  (tokenizer.showNextToken().getType() == TokenType.OP && (
            tokenizer.showNextToken().getValue().equals("+") ||
                tokenizer.showNextToken().getValue().equals("-"))
        ) {
            Token op = tokenizer.getNextToken();
            Expression new_expr = parseAtom();
            if (op.getValue().equals("+")) {
                acc_expr = new Add(acc_expr, new_expr);
            }
            if (op.getValue().equals("-")) {
                acc_expr = new Sub(acc_expr, new_expr);
            }
        }
        return acc_expr;
    }

    /**
     * Monome :=    Atom |
     *              Atom * / Atom |
     *              Atom * / ... * / Atom
     * @return
     */
    public Expression parseMonome () {
        Expression acc_expr = parseAtom();
        while  (tokenizer.showNextToken().getType() == TokenType.OP && (
                    tokenizer.showNextToken().getValue().equals("*") ||
                    tokenizer.showNextToken().getValue().equals("/"))
        ) {
            Token op = tokenizer.getNextToken();
            Expression new_expr = parseAtom();
            if (op.getValue().equals("*")) {
                acc_expr = new Mul(acc_expr, new_expr);
            }
            if (op.getValue().equals("/")) {
                acc_expr = new Div(acc_expr, new_expr);
            }
        }
        return acc_expr;
    }

    /**
     * Atom = Number | Var |  (Expr)
     * @return
     */
    // FIXME: unary minus
    public Expression parseAtom () {
        Expression res = null;
        if (tokenizer.showNextToken().getType() == TokenType.OP &&
            tokenizer.showNextToken().getValue().equals("(")) { // (Expr)
            tokenizer.getNextToken(); // open bracket
            res = parseExpression();
            tokenizer.getNextToken(); // close bracket;
        } else {
            Token t = tokenizer.getNextToken();
            if (t.getType() == TokenType.NUM) { // Number
                res = new Number(Integer.parseInt(t.getValue()));
            }
            if (t.getType() == TokenType.VAR) { // Var
                res = new Variable(t.getValue());
            }
        }
        return  res;
    }



}
