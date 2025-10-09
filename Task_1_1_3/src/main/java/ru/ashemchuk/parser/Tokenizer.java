package ru.ashemchuk.parser;

public class Tokenizer {
    private final String expression;
    private int position;

    public Tokenizer (String expression) {
        this.expression = expression;
        this.position = 0;
    }

    public Token getNextToken() {
        Token t = new Token();
        while (Character.isWhitespace(expression.charAt(position))) {
            position++;
        }
        if (position >= expression.length()) {
            t.setType(TokenType.NOT_TOKEN);
            return t;
        }

        if (isOperator(expression.charAt(position))) {
            t.setType(TokenType.OP);
            t.setValue(Character.toString(expression.charAt(position)));
            position++;
            return t;
        }

        int pos_begin = position;
        while (Character.isAlphabetic(expression.charAt(position))) {
            position++;
        }
        if (position != pos_begin) {
            t.setType(TokenType.VAR);
            t.setValue(expression.substring(pos_begin, position));
            return t;
        }

        // here pos_begin == position
        while (Character.isDigit(expression.charAt(position))) {
            position++;
        }
        if (position != pos_begin) {
            t.setType(TokenType.NUM);
            t.setValue(expression.substring(pos_begin, position));
        }
        return t;
    }

    private boolean isOperator (char a) {
        return a == '(' || a == ')' || a == '+' || a == '-' || a == '*' || a == '/';
    }

    public Token showNextToken() {
        int old_pos = position;
        Token t = getNextToken();
        position = old_pos;
        return t;
    }
}
