package ru.ashemchuk;

/**
 * Represents plain text or formatted text in Markdown.
 * Supports bold, italic, code, and strikethrough formatting.
 */
public class Text extends Element {
    private final String content;

    private Text(String content) {
        this.content = content;
    }

    /**
     * Returns the content as a Markdown string.
     *
     * @return the text content, possibly with Markdown formatting
     */
    @Override
    public String toMarkdown() {
        return content;
    }

    /**
     * A builder class for constructing {@link Text} instances with optional formatting.
     */
    public static class Builder extends AbstractBuilder<Text> {
        private String content;

        /**
         * Sets the text content.
         *
         * @param content the text content
         * @return this builder instance for method chaining
         */
        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * Applies bold formatting to the text.
         *
         * @return this builder instance for method chaining
         */
        public Builder withBold() {
            content = "**" + content + "**";
            return this;
        }

        /**
         * Applies italic formatting to the text.
         *
         * @return this builder instance for method chaining
         */
        public Builder withItalic() {
            content = "*" + content + "*";
            return this;
        }

        /**
         * Applies inline code formatting to the text.
         *
         * @return this builder instance for method chaining
         */
        public Builder withCode() {
            content = "`" + content + "`";
            return this;
        }

        /**
         * Applies strikethrough formatting to the text.
         *
         * @return this builder instance for method chaining
         */
        public Builder withStrike() {
            content = "~~" + content + "~~";
            return this;
        }

        /**
         * Builds a {@link Text} instance.
         *
         * @return a new {@link Text} with the configured content and formatting
         * @throws IllegalStateException if content is not set
         */
        @Override
        public Text build() {
            if (content == null) {
                throw new IllegalStateException("Text's content isn't set");
            }
            return new Text(content);
        }
    }
}