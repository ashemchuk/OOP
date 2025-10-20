package ru.ashemchuk.parser;

/**
 * Enumeration representing the different types of tokens that can be identified
 * during the tokenization of mathematical expressions.
 */
public enum TokenType {
    /**
     * Represents a numeric constant token
     */
    NUM,

    /**
     * Represents a variable identifier token
     */
    VAR,

    /**
     * Represents an operator token (+, -, *, /, (, ))
     */
    OP,

    /**
     * Represents the absence of a valid token or end of input
     */
    NOT_TOKEN
}