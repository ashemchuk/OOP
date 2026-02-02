package ru.ashemchuk;

import java.util.ArrayList;

/**
 * Represents a list (ordered or unordered) in Markdown.
 * Lists can contain any {@link Element} as items.
 */
public class List extends Element {
    private final java.util.List<Element> items;
    private final boolean ordered;

    private List(java.util.List<Element> items, boolean ordered) {
        this.items = items;
        this.ordered = ordered;
    }

    /**
     * Converts this list to its Markdown representation.
     *
     * @return a Markdown-formatted list string
     */
    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (ordered) {
                sb.append(i + 1).append(". ");
            } else {
                sb.append("- ");
            }
            sb.append(items.get(i).toMarkdown());
            if (i < items.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * A builder class for constructing {@link List} instances.
     */
    public static class Builder extends AbstractBuilder<List> {
        private final java.util.List<Element> items = new ArrayList<>();
        private boolean ordered = false;

        /**
         * Makes the list ordered (numbered).
         *
         * @return this builder instance for method chaining
         */
        public Builder ordered() {
            this.ordered = true;
            return this;
        }

        /**
         * Makes the list unordered (bulleted).
         *
         * @return this builder instance for method chaining
         */
        public Builder unordered() {
            this.ordered = false;
            return this;
        }

        /**
         * Adds a text item to the list.
         *
         * @param text the text content of the item
         * @return this builder instance for method chaining
         */
        public Builder addItem(String text) {
            items.add(new Text.Builder().withContent(text).build());
            return this;
        }

        /**
         * Adds any {@link Element} as an item to the list.
         *
         * @param element the element to add
         * @return this builder instance for method chaining
         */
        public Builder addItem(Element element) {
            items.add(element);
            return this;
        }

        /**
         * Builds a {@link List} instance.
         *
         * @return a new {@link List} with the configured items and ordering
         * @throws IllegalStateException if no items have been added
         */
        @Override
        public List build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("List must have at least one item");
            }
            return new List(new ArrayList<>(items), ordered);
        }
    }
}