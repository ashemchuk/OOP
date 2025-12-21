package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test.
 */
public class ListTest {

    @Test
    void testUnorderedList() {
        List list = new List.Builder()
            .unordered()
            .addItem("First")
            .addItem("Second")
            .addItem("Third")
            .build();

        String expected = "- First\n- Second\n- Third";
        assertEquals(expected, list.toMarkdown());
    }

    @Test
    void testOrderedList() {
        List list = new List.Builder()
            .ordered()
            .addItem("First")
            .addItem("Second")
            .addItem("Third")
            .build();

        String expected = "1. First\n2. Second\n3. Third";
        assertEquals(expected, list.toMarkdown());
    }

    @Test
    void testListWithElements() {
        List list = new List.Builder()
            .unordered()
            .addItem(new Text.Builder().withContent("Bold text").withBold().build())
            .addItem("Plain text")
            .build();

        String expected = "- **Bold text**\n- Plain text";
        assertEquals(expected, list.toMarkdown());
    }

    @Test
    void testEmptyList() {
        assertThrows(IllegalStateException.class, () -> new List.Builder().build());
    }

    @Test
    void testSingleItemList() {
        List list = new List.Builder()
            .unordered()
            .addItem("Single item")
            .build();

        assertEquals("- Single item", list.toMarkdown());
    }

    @Test
    void testOrderChange() {
        List.Builder builder = new List.Builder()
            .ordered()
            .addItem("First");

        builder.unordered()
            .addItem("Second");

        List list = builder.build();

        assertEquals("- First\n- Second", list.toMarkdown());
    }
}