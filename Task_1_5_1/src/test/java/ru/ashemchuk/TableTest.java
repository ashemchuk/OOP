package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * test.
 */
public class TableTest {

    @Test
    void testBasicTable() {
        Table table = new Table.Builder()
            .addRow("Name", "Age", "City")
            .addRow("Alice", "25", "New York")
            .addRow("Bob", "30", "London")
            .build();

        String expected = """
            | Name | Age | City |
            | ------ | ------ | ------ |
            | Alice | 25 | New York |
            | Bob | 30 | London |""";
        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testTableWithAlignments() {
        Table table = new Table.Builder()
            .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_CENTER, Table.ALIGN_LEFT)
            .addRow("ID", "Name", "Value")
            .addRow("1", "Alice", "100")
            .addRow("2", "Bob", "200")
            .build();

        String expected = """
            | ID | Name | Value |
            | -----: | :---: | ------ |
            | 1 | Alice | 100 |
            | 2 | Bob | 200 |""";
        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testTableWithRowLimit() {
        Table table = new Table.Builder()
            .withRowLimit(2)
            .addRow("A", "B", "C")
            .addRow("1", "2", "3")
            .addRow("4", "5", "6")
            .addRow("7", "8", "9")
            .build();

        String expected = """
            | A | B | C |
            | ------ | ------ | ------ |
            | 1 | 2 | 3 |""";
        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testTableWithMixedContent() {
        Table table = new Table.Builder()
            .addRow("Text", "Bold", "Italic")
            .addRow("Normal",
                new Text.Builder().withContent("Bold text").withBold().build(),
                new Text.Builder().withContent("Italic text").withItalic().build())
            .build();

        String expected = """
            | Text | Bold | Italic |
            | ------ | ------ | ------ |
            | Normal | **Bold text** | *Italic text* |""";
        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testTableWithNumbers() {
        Table table = new Table.Builder()
            .addRow("Number", "Value")
            .addRow(1, 100)
            .addRow(2, 200.5)
            .build();

        String expected = """
            | Number | Value |
            | ------ | ------ |
            | 1 | 100 |
            | 2 | 200.5 |""";
        assertEquals(expected, table.toMarkdown());
    }

    @Test
    void testEmptyTable() {
        assertThrows(IllegalStateException.class, () -> new Table.Builder().build());
    }

    @Test
    void testInvalidAlignment() {
        assertThrows(IllegalArgumentException.class, () -> new Table.Builder()
            .withAlignments("invalid", "left")
            .addRow("A", "B")
            .build());
    }

    @Test
    void testTableWithDifferentColumnCounts() {
        Table.Builder builder = new Table.Builder()
            .addRow("A", "B", "C")
            .addRow("1", "2");

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testTableFromAssignment() {
        Table.Builder tableBuilder = new Table.Builder()
            .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
            .withRowLimit(8)
            .addRow("Index", "Random");

        for (int i = 1; i <= 5; i++) {
            final var value = (int) (Math.random() * 10);
            if (value > 5) {
                tableBuilder.addRow(i,
                    new Text.Builder()
                        .withContent(String.valueOf(value))
                        .withBold()
                        .build());
            } else {
                tableBuilder.addRow(i, String.valueOf(value));
            }
        }

        Table table = tableBuilder.build();
        assertNotNull(table);
        assertTrue(table.toMarkdown().contains("Index"));
        assertTrue(table.toMarkdown().contains("Random"));
    }
}