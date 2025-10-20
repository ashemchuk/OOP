package ru.ashemchuk.parser;

/**
 * Tokenizes mathematical expressions by breaking them down into individual tokens.
 * This class processes input strings and converts them into a sequence of tokens
 * that can be used by the parser to build expression trees.
 */
public class Tokenizer {
    private final String expression;
    private int position;

    /**
     * Constructs a new Tokenizer for the specified expression.
     *
     * @param expression the mathematical expression string to tokenize
     */
    public Tokenizer(String expression) {
        this.expression = expression;
        this.position = 0;
    }

    /**
     * Retrieves the next token from the expression string.
     * Skips whitespace and returns the next valid token (operator, variable, or number).
     * Returns NOT_TOKEN type if the end of expression is reached.
     *
     * @return the next Token in the expression
     */
    public Token getNextToken() {
        Token t = new Token();
        while (position < expression.length()
            && Character.isWhitespace(expression.charAt(position))) {
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

        int posBegin = position;
        while (position < expression.length()
            && Character.isAlphabetic(expression.charAt(position))) {
            position++;
        }
        if (position != posBegin) {
            t.setType(TokenType.VAR);
            t.setValue(expression.substring(posBegin, position));
            return t;
        }

        // here posBegin == position
        while (position < expression.length() && Character.isDigit(expression.charAt(position))) {
            position++;
        }
        if (position != posBegin) {
            t.setType(TokenType.NUM);
            t.setValue(expression.substring(posBegin, position));
        }
        return t;
    }

    /**
     * Checks if a character is a valid mathematical operator.
     *
     * @param a the character to check
     * @return true if the character is an operator, false otherwise
     */
    private boolean isOperator(char a) {
        return a == '(' || a == ')' || a == '+' || a == '-' || a == '*' || a == '/';
    }

    /**
     * Peeks at the next token without consuming it.
     * The internal position is restored after examining the next token.
     *
     * @return the next Token in the expression without advancing the position
     */
    public Token showNextToken() {
        int oldPos = position;
        Token t = getNextToken();
        position = oldPos;
        return t;
    }
}