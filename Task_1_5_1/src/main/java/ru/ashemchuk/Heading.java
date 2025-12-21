package ru.ashemchuk;

/**
 * Represents a heading element in Markdown.
 * Headings range from level 1 (largest) to level 6 (smallest).
 */
public class Heading extends Element {
    private final String text;
    private final int level;

    private Heading(String text, int level) {
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("Heading level must be between 1 and 6");
        }
        this.text = text;
        this.level = level;
    }

    /**
     * Converts this heading to its Markdown representation.
     *
     * @return a Markdown-formatted heading string
     */
    @Override
    public String toMarkdown() {
        return "#".repeat(level) + " " + text;
    }

    /**
     * A builder class for constructing {@link Heading} instances.
     */
    public static class Builder extends AbstractBuilder<Heading> {
        private String text;
        private int level = 1;

        /**
         * Sets the heading text.
         *
         * @param text the heading text
         * @return this builder instance for method chaining
         */
        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the heading level (1-6).
         *
         * @param level the heading level
         * @return this builder instance for method chaining
         */
        public Builder withLevel(int level) {
            this.level = level;
            return this;
        }

        /**
         * Builds a {@link Heading} instance.
         *
         * @return a new {@link Heading} with the configured text and level
         * @throws IllegalStateException if text is null or empty
         */
        @Override
        public Heading build() {
            if (text == null || text.trim().isEmpty()) {
                throw new IllegalStateException("Heading text cannot be empty");
            }
            return new Heading(text, level);
        }
    }
}