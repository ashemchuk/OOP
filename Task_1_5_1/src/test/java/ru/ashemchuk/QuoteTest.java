package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test.
 */
public class QuoteTest {

    @Test
    void testSingleLineQuote() {
        Quote quote = new Quote.Builder()
            .addLine("This is a quote")
            .build();

        assertEquals("> This is a quote", quote.toMarkdown());
    }

    @Test
    void testMultiLineQuote() {
        Quote quote = new Quote.Builder()
            .addLine("First line")
            .addLine("Second line")
            .build();

        String expected = "> First line\n> Second line";
        assertEquals(expected, quote.toMarkdown());
    }

    @Test
    void testQuoteWithElement() {
        Quote quote = new Quote.Builder()
            .addLine(new Text.Builder().withContent("Bold quote").withBold().build())
            .addLine("Normal text")
            .build();

        String expected = "> **Bold quote**\n> Normal text";
        assertEquals(expected, quote.toMarkdown());
    }

    @Test
    void testEmptyQuote() {
        assertThrows(IllegalStateException.class, () -> new Quote.Builder().build());
    }

    @Test
    void testQuoteWithMultiLineElement() {
        Quote quote = new Quote.Builder()
            .addLine("Line 1\nLine 2")
            .build();

        String expected = "> Line 1\n> Line 2";
        assertEquals(expected, quote.toMarkdown());
    }
}