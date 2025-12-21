package ru.ashemchuk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a code block element in Markdown.
 * Code blocks are used to display preformatted code with optional language specification.
 */
public class CodeBlock extends Element {
    private final String language;
    private final String code;

    private CodeBlock(String language, String code) {
        this.language = language;
        this.code = code;
    }

    /**
     * Converts this code block to its Markdown representation.
     *
     * @return a Markdown-formatted code block string
     */
    @Override
    public String toMarkdown() {
        return "```" + (language != null ? language : "") + "\n" + code + "\n```";
    }

    /**
     * A builder class for constructing {@link CodeBlock} instances.
     * Provides methods to set the language and add code lines.
     */
    public static class Builder extends AbstractBuilder<CodeBlock> {
        private String language;
        private final List<String> lines = new ArrayList<>();

        /**
         * Sets the programming language for the code block.
         *
         * @param language the language identifier (e.g., "java", "python")
         * @return this builder instance for method chaining
         */
        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        /**
         * Adds a single line of code to the code block.
         *
         * @param line the line of code to add
         * @return this builder instance for method chaining
         */
        public Builder addLine(String line) {
            lines.add(line);
            return this;
        }

        /**
         * Adds multiple lines of code from a multiline string.
         * Clears any previously added lines before adding new ones.
         *
         * @param code the multiline code string
         * @return this builder instance for method chaining
         */
        public Builder addMultiline(String code) {
            lines.clear();
            String[] codeLines = code.split("\n");
            Collections.addAll(lines, codeLines);
            return this;
        }

        /**
         * Builds a {@link CodeBlock} instance.
         *
         * @return a new {@link CodeBlock} with the configured properties
         * @throws IllegalStateException if no code lines have been added
         */
        @Override
        public CodeBlock build() {
            if (lines.isEmpty()) {
                throw new IllegalStateException("Code block cannot be empty");
            }
            StringBuilder codeBuilder = new StringBuilder();
            for (int i = 0; i < lines.size(); i++) {
                codeBuilder.append(lines.get(i));
                if (i < lines.size() - 1) {
                    codeBuilder.append("\n");
                }
            }
            return new CodeBlock(language, codeBuilder.toString());
        }
    }
}