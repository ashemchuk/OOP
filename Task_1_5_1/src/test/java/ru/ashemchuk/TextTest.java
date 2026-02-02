package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test.
 */
public class TextTest {

    @Test
    void testPlainText() {
        Text text = new Text.Builder()
            .withContent("Hello World")
            .build();
        assertEquals("Hello World", text.toMarkdown());
    }

    @Test
    void testBoldText() {
        Text text = new Text.Builder()
            .withContent("Hello")
            .withBold()
            .build();
        assertEquals("**Hello**", text.toMarkdown());
    }

    @Test
    void testItalicText() {
        Text text = new Text.Builder()
            .withContent("Hello")
            .withItalic()
            .build();
        assertEquals("*Hello*", text.toMarkdown());
    }

    @Test
    void testCodeText() {
        Text text = new Text.Builder()
            .withContent("code")
            .withCode()
            .build();
        assertEquals("`code`", text.toMarkdown());
    }

    @Test
    void testStrikeText() {
        Text text = new Text.Builder()
            .withContent("text")
            .withStrike()
            .build();
        assertEquals("~~text~~", text.toMarkdown());
    }

    @Test
    void testCombinedFormatting() {
        Text text = new Text.Builder()
            .withContent("Hello")
            .withBold()
            .withItalic()
            .build();
        assertEquals("***Hello***", text.toMarkdown());
    }

    @Test
    void testNullContent() {
        assertThrows(IllegalStateException.class, () -> new Text.Builder().build());
    }

    @Test
    void testEmptyContent() {
        Text text = new Text.Builder()
            .withContent("")
            .build();
        assertEquals("", text.toMarkdown());
    }
}