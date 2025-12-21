package ru.ashemchuk;

/**
 * Represents a hyperlink element in Markdown.
 * Links consist of display text, a URL, and an optional title.
 */
public class Link extends Element {
    private final String text;
    private final String url;
    private final String title;

    private Link(String text, String url, String title) {
        this.text = text;
        this.url = url;
        this.title = title;
    }

    /**
     * Converts this link to its Markdown representation.
     *
     * @return a Markdown-formatted link string
     */
    @Override
    public String toMarkdown() {
        String linkText = (text != null && !text.trim().isEmpty()) ? text : url;
        if (title != null && !title.trim().isEmpty()) {
            return "[" + linkText + "](" + url + " \"" + title + "\")";
        }
        return "[" + linkText + "](" + url + ")";
    }

    /**
     * A builder class for constructing {@link Link} instances.
     */
    public static class Builder extends AbstractBuilder<Link> {
        private String text;
        private String url;
        private String title;

        /**
         * Sets the display text for the link.
         *
         * @param text the link text
         * @return this builder instance for method chaining
         */
        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Sets the URL for the link.
         *
         * @param url the link URL
         * @return this builder instance for method chaining
         */
        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Sets the optional link title.
         *
         * @param title the link title
         * @return this builder instance for method chaining
         */
        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Builds a {@link Link} instance.
         *
         * @return a new {@link Link} with the configured properties
         * @throws IllegalStateException if URL is null or empty
         */
        @Override
        public Link build() {
            if (url == null || url.trim().isEmpty()) {
                throw new IllegalStateException("Link URL cannot be empty");
            }
            return new Link(text, url, title);
        }
    }
}