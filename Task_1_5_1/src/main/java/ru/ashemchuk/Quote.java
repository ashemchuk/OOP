package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a blockquote in Markdown.
 * Blockquotes can contain multiple lines of text or other elements.
 */
public class Quote extends Element {
    private final List<Element> lines;

    private Quote(List<Element> lines) {
        this.lines = lines;
    }

    /**
     * Converts this quote to its Markdown representation.
     * Each line is prefixed with "> ".
     *
     * @return a Markdown-formatted blockquote string
     */
    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).toMarkdown().split("\n");
            for (String part : parts) {
                sb.append("> ").append(part);
                // If this is not the last part, add a line break
                if (i < lines.size() - 1 || !part.equals(parts[parts.length - 1])) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString().trim();
    }

    /**
     * A builder class for constructing {@link Quote} instances.
     */
    public static class Builder extends AbstractBuilder<Quote> {
        private final List<Element> lines = new ArrayList<>();

        /**
         * Adds a text line to the quote.
         *
         * @param text the text content of the line
         * @return this builder instance for method chaining
         */
        public Builder addLine(String text) {
            lines.add(new Text.Builder().withContent(text).build());
            return this;
        }

        /**
         * Adds any {@link Element} as a line to the quote.
         *
         * @param element the element to add
         * @return this builder instance for method chaining
         */
        public Builder addLine(Element element) {
            lines.add(element);
            return this;
        }

        /**
         * Builds a {@link Quote} instance.
         *
         * @return a new {@link Quote} with the configured lines
         * @throws IllegalStateException if no lines have been added
         */
        @Override
        public Quote build() {
            if (lines.isEmpty()) {
                throw new IllegalStateException("Quote must have at least one line");
            }
            return new Quote(new ArrayList<>(lines));
        }
    }
}