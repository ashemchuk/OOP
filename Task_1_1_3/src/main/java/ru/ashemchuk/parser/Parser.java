package ru.ashemchuk.parser;

import ru.ashemchuk.Expression;

public class Parser {
    private Tokenizer tokenizer;

    public Parser (Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }
    /**
     * Expr :=  Monome |
     *          Monome +- Monome |
     *          Monome +- .... +- Monome
     * @return
     */
//    public Expression parseExpression () {
//
//    }
}
