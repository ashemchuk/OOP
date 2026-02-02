package ru.ashemchuk;

/**
 * Abstract base class for all Markdown elements.
 * Provides common functionality for converting to Markdown and equality comparisons.
 */
public abstract class Element {

    /**
     * Converts the element to its Markdown representation.
     *
     * @return the Markdown string representation of this element
     */
    public abstract String toMarkdown();

    /**
     * Returns the Markdown representation of this element.
     *
     * @return the result of {@link #toMarkdown()}
     */
    @Override
    public String toString() {
        return this.toMarkdown();
    }

    /**
     * Compares this element with another object for equality.
     * Two elements are considered equal if their Markdown representations are identical.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Element)) {
            return false;
        }
        return this.toMarkdown().equals(((Element) obj).toMarkdown());
    }

    /**
     * Returns a hash code based on the Markdown representation.
     *
     * @return the hash code of the Markdown string
     */
    @Override
    public int hashCode() {
        return this.toMarkdown().hashCode();
    }

    /**
     * Abstract builder class for constructing {@link Element} instances.
     *
     * @param <T> the type of element being built
     */
    public abstract static class AbstractBuilder<T extends Element> {

        /**
         * Builds the element instance.
         *
         * @return a new instance of the element
         */
        public abstract T build();
    }
}