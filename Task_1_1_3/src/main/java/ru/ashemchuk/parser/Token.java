package ru.ashemchuk.parser;

/**
 * Represents a token in the mathematical expression parsing process.
 * A token consists of a type and a string value, and is used by the tokenizer
 * to break down input strings into meaningful components.
 */
public class Token {
    private TokenType type;
    private String value;

    /**
     * Constructs a new empty Token with null type and value.
     */
    public Token() {
        this.type = null;
        this.value = null;
    }

    /**
     * Returns the type of this token.
     *
     * @return the TokenType of this token
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Sets the type of this token.
     *
     * @param type the TokenType to set
     */
    public void setType(TokenType type) {
        this.type = type;
    }

    /**
     * Returns the string value of this token.
     *
     * @return the string value of this token
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the string value of this token.
     *
     * @param value the string value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}