package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test.
 */
public class HeadingTest {

    @Test
    void testHeadingLevel1() {
        Heading heading = new Heading.Builder()
            .withText("Title")
            .withLevel(1)
            .build();
        assertEquals("# Title", heading.toMarkdown());
    }

    @Test
    void testHeadingLevel3() {
        Heading heading = new Heading.Builder()
            .withText("Chapter 1")
            .withLevel(3)
            .build();
        assertEquals("### Chapter 1", heading.toMarkdown());
    }

    @Test
    void testHeadingDefaultLevel() {
        Heading heading = new Heading.Builder()
            .withText("Title")
            .build();
        assertEquals("# Title", heading.toMarkdown());
    }

    @Test
    void testHeadingInvalidLevel() {
        assertThrows(IllegalArgumentException.class, () -> new Heading.Builder()
            .withText("Title")
            .withLevel(0)
            .build());

        assertThrows(IllegalArgumentException.class, () -> new Heading.Builder()
            .withText("Title")
            .withLevel(7)
            .build());
    }

    @Test
    void testEmptyText() {
        assertThrows(IllegalStateException.class, () -> new Heading.Builder()
            .withText("")
            .build());

        assertThrows(IllegalStateException.class, () -> new Heading.Builder()
            .withText("   ")
            .build());
    }

    @Test
    void testNullText() {
        assertThrows(IllegalStateException.class, () -> new Heading.Builder().build());
    }

}