package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a table in Markdown.
 * Tables support alignment (left, center, right) and optional row limiting.
 */
public class Table extends Element {
    /** Constant for left alignment. */
    public static final String ALIGN_LEFT = "left";
    /** Constant for center alignment. */
    public static final String ALIGN_CENTER = "center";
    /** Constant for right alignment. */
    public static final String ALIGN_RIGHT = "right";

    private final List<String> alignments;
    private final List<List<Element>> rows;
    private final Integer rowLimit;

    private Table(List<String> alignments, List<List<Element>> rows, Integer rowLimit) {
        this.alignments = alignments;
        this.rows = rows;
        this.rowLimit = rowLimit;
    }

    /**
     * Converts this table to its Markdown representation.
     *
     * @return a Markdown-formatted table string
     */
    @Override
    public String toMarkdown() {
        if (rows.isEmpty()) {
            return "";
        }

        // Apply row limit if set
        List<List<Element>> rowsToRender = rows;
        if (rowLimit != null && rowLimit < rows.size()) {
            rowsToRender = rows.subList(0, rowLimit);
        }

        StringBuilder sb = new StringBuilder();

        // Header row
        List<Element> headerRow = rowsToRender.get(0);
        sb.append("|");
        for (Element element : headerRow) {
            sb.append(" ").append(element.toMarkdown()).append(" |");
        }
        sb.append("\n");

        // Separator row
        sb.append("|");
        for (int i = 0; i < headerRow.size(); i++) {
            String alignment = i < alignments.size() ? alignments.get(i) : ALIGN_LEFT;
            switch (alignment) {
                case ALIGN_RIGHT:
                    sb.append(" -----: |");
                    break;
                case ALIGN_CENTER:
                    sb.append(" :---: |");
                    break;
                case ALIGN_LEFT:
                default:
                    sb.append(" ------ |");
                    break;
            }
        }
        sb.append("\n");

        // Data rows
        for (int row = 1; row < rowsToRender.size(); row++) {
            sb.append("|");
            List<Element> currentRow = rowsToRender.get(row);
            for (Element element : currentRow) {
                sb.append(" ").append(element.toMarkdown()).append(" |");
            }
            if (row < rowsToRender.size() - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * A builder class for constructing {@link Table} instances.
     */
    public static class Builder extends AbstractBuilder<Table> {
        private final List<String> alignments = new ArrayList<>();
        private final List<List<Element>> rows = new ArrayList<>();
        private Integer rowLimit;

        /**
         * Sets the column alignments for the table.
         *
         * @param alignments the alignment values (must be {@link #ALIGN_LEFT},
         * {@link #ALIGN_CENTER}, or {@link #ALIGN_RIGHT})
         * @return this builder instance for method chaining
         * @throws IllegalArgumentException if an invalid alignment is provided
         */
        public Builder withAlignments(String... alignments) {
            this.alignments.clear();
            for (String alignment : alignments) {
                if (!alignment.equals(ALIGN_LEFT)
                    && !alignment.equals(ALIGN_CENTER)
                    && !alignment.equals(ALIGN_RIGHT)) {
                    throw new IllegalArgumentException("Invalid alignment: " + alignment);
                }
                this.alignments.add(alignment);
            }
            return this;
        }

        /**
         * Sets a limit on the number of rows to render.
         *
         * @param rowLimit the maximum number of rows to include in the output
         * @return this builder instance for method chaining
         */
        public Builder withRowLimit(int rowLimit) {
            this.rowLimit = rowLimit;
            return this;
        }

        /**
         * Adds a row to the table.
         * Cells can be {@link Element}, {@link String}, or {@link Number} instances.
         *
         * @param cells the cells of the row
         * @return this builder instance for method chaining
         * @throws IllegalArgumentException if an unsupported cell type is provided
         */
        public Builder addRow(Object... cells) {
            List<Element> row = new ArrayList<>();
            for (Object cell : cells) {
                if (cell instanceof Element) {
                    row.add((Element) cell);
                } else if (cell instanceof String) {
                    row.add(new Text.Builder().withContent((String) cell).build());
                } else if (cell instanceof Number) {
                    row.add(new Text.Builder().withContent(cell.toString()).build());
                } else {
                    throw new IllegalArgumentException("Unsupported cell type: " + cell.getClass());
                }
            }
            rows.add(row);
            return this;
        }

        /**
         * Builds a {@link Table} instance.
         *
         * @return a new {@link Table} with the configured rows and alignments
         * @throws IllegalStateException if no rows have been added
         * @throws IllegalStateException if rows have inconsistent column counts
         */
        @Override
        public Table build() {
            if (rows.isEmpty()) {
                throw new IllegalStateException("Table must have at least one row");
            }

            // Validate that all rows have same number of columns
            int columnCount = rows.get(0).size();
            for (int i = 1; i < rows.size(); i++) {
                if (rows.get(i).size() != columnCount) {
                    throw new IllegalStateException(
                        "All rows must have the same number of columns");
                }
            }

            return new Table(new ArrayList<>(alignments), new ArrayList<>(rows), rowLimit);
        }
    }
}