package ru.ashemchuk;

/**
 * Represents an image element in Markdown.
 * Images consist of alternative text, a URL, and an optional title.
 */
public class Image extends Element {
    private final String altText;
    private final String url;
    private final String title;

    private Image(String altText, String url, String title) {
        this.altText = altText;
        this.url = url;
        this.title = title;
    }

    /**
     * Converts this image to its Markdown representation.
     *
     * @return a Markdown-formatted image string
     */
    @Override
    public String toMarkdown() {
        String displayAltText = (altText != null && !altText.trim().isEmpty()) ? altText : "";
        if (title != null && !title.trim().isEmpty()) {
            return "![" + displayAltText + "](" + url + " \"" + title + "\")";
        }
        return "![" + displayAltText + "](" + url + ")";
    }

    /**
     * A builder class for constructing {@link Image} instances.
     */
    public static class Builder extends AbstractBuilder<Image> {
        private String altText;
        private String url;
        private String title;

        /**
         * Sets the alternative text for the image.
         *
         * @param altText the alternative text
         * @return this builder instance for method chaining
         */
        public Builder withAltText(String altText) {
            this.altText = altText;
            return this;
        }

        /**
         * Sets the image URL.
         *
         * @param url the image URL
         * @return this builder instance for method chaining
         */
        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Sets the optional image title.
         *
         * @param title the image title
         * @return this builder instance for method chaining
         */
        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Builds an {@link Image} instance.
         *
         * @return a new {@link Image} with the configured properties
         * @throws IllegalStateException if URL is null or empty
         */
        @Override
        public Image build() {
            if (url == null || url.trim().isEmpty()) {
                throw new IllegalStateException("Image URL cannot be empty");
            }
            return new Image(altText, url, title);
        }
    }
}